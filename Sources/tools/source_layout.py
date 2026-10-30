"""Inspect or materialize TeeChart source sets; no third-party dependencies.

Python is only needed for these optional verification tools, not IDE builds or
Recompile. Shared sources are compiled separately for each platform, not as a
platform-independent JAR.
"""

import argparse
import hashlib
import json
from pathlib import Path
import shutil
import sys


SOURCE_DIR = Path(__file__).resolve().parent.parent
VARIANTS = ("Swing", "SWT", "Android")


class LayoutError(ValueError):
    pass


def load_layout(base=SOURCE_DIR):
    base = Path(base).resolve()
    properties = {}
    for line in (base / "source-layout.properties").read_text(encoding="utf-8").splitlines():
        line = line.strip()
        if not line or line.startswith(("#", "!")):
            continue
        key, separator, value = line.partition("=")
        key = key.strip()
        if not separator or key in properties:
            raise LayoutError("Invalid or duplicate layout property: " + key)
        properties[key] = value.strip()
    result = {}
    for variant in VARIANTS:
        key = variant + ".source.roots"
        if not properties.get(key):
            raise LayoutError("Missing layout property: " + key)
        roots = []
        for value in properties[key].split(";"):
            relative = Path(value)
            root = (base / relative).resolve()
            if relative.is_absolute() or not value or root == base or "archive" in (p.lower() for p in relative.parts):
                raise LayoutError("Invalid source root: " + value)
            try:
                root.relative_to(base)
            except ValueError:
                raise LayoutError("Source root escapes the repository: " + value)
            if not root.is_dir() or root in roots:
                raise LayoutError("Missing or repeated source root: " + value)
            roots.append(root)
        result[variant] = roots
    return result


def is_source_input(relative):
    """Exclude Eclipse metadata, stale file lists and compiler outputs."""
    return relative.suffix.lower() != ".class" and (
        relative.suffix == ".java" or relative.parts[0] in ("com", "META-INF")
    )


def source_files(roots):
    result = {}
    seen = {}
    for root in roots:
        for path in sorted(root.rglob("*")):
            if not path.is_file():
                continue
            relative = path.relative_to(root)
            if not is_source_input(relative):
                continue
            key = relative.as_posix()
            folded = key.casefold()
            if folded in seen:
                raise LayoutError("Duplicate source/resource: {} ({} and {})".format(key, seen[folded], path))
            seen[folded] = path
            result[key] = path
    return dict(sorted(result.items()))


def snapshot(layout):
    return {
        variant: {name: hashlib.sha256(path.read_bytes()).hexdigest()
                  for name, path in source_files(roots).items()}
        for variant, roots in layout.items()
    }


def check_baseline(layout, baseline):
    current = snapshot(layout)
    if current != baseline:
        changes = []
        for variant in VARIANTS:
            before, after = baseline.get(variant, {}), current[variant]
            for name in sorted(before.keys() | after.keys()):
                if before.get(name) != after.get(name):
                    changes.append(variant + "/" + name)
        raise LayoutError("Source set differs from baseline:\n" + "\n".join(changes))


def check_output(output, roots):
    output = Path(output).resolve()
    for root in roots:
        for parent, child in ((root, output), (output, root)):
            try:
                child.relative_to(parent)
            except ValueError:
                continue
            raise LayoutError("Output overlaps a source root: " + str(output))
    return output


def materialize(roots, output):
    files = source_files(roots)  # Check collisions before writing anything.
    output = check_output(output, roots)
    if output.exists() and (not output.is_dir() or any(output.iterdir())):
        raise LayoutError("Output must be a new or empty directory: " + str(output))
    output.mkdir(parents=True, exist_ok=True)
    for name, source in files.items():
        target = output / name
        target.parent.mkdir(parents=True, exist_ok=True)
        shutil.copyfile(source, target)


def write_filelist(roots, output):
    files = source_files(roots)
    output = check_output(output, roots)
    output.parent.mkdir(parents=True, exist_ok=True)
    names = ['"' + path.as_posix().replace('"', '\\"') + '"'
             for name, path in files.items() if name.endswith(".java")]
    output.write_text("\n".join(names) + "\n", encoding="utf-8")


def main():
    parser = argparse.ArgumentParser(description=__doc__)
    parser.add_argument("command", choices=("verify", "snapshot", "filelist", "materialize"))
    parser.add_argument("--variant", choices=VARIANTS)
    parser.add_argument("--output", type=Path)
    parser.add_argument("--baseline", type=Path, help="Snapshot JSON to compare byte-for-byte")
    args = parser.parse_args()
    if args.command in ("filelist", "materialize") and not args.variant:
        parser.error("--variant is required for " + args.command)
    if args.command != "verify" and args.output is None:
        parser.error("--output is required for " + args.command)
    try:
        layout = load_layout()
        for variant, roots in layout.items():
            files = source_files(roots)
            java = sum(name.endswith(".java") for name in files)
            print("{}: {} Java files, {} resources, no duplicate paths".format(variant, java, len(files) - java))
        if args.baseline:
            check_baseline(layout, json.loads(args.baseline.read_text(encoding="utf-8")))
            print("Baseline matches: all source and resource bytes are unchanged.")
        if args.command == "snapshot":
            output = check_output(args.output, [root for roots in layout.values() for root in roots])
            output.parent.mkdir(parents=True, exist_ok=True)
            output.write_text(json.dumps(snapshot(layout), indent=2) + "\n", encoding="utf-8")
        elif args.command == "materialize":
            materialize(layout[args.variant], args.output)
        elif args.command == "filelist":
            write_filelist(layout[args.variant], args.output)
    except (LayoutError, OSError) as error:
        print(str(error), file=sys.stderr)
        return 1
    return 0


if __name__ == "__main__":
    sys.exit(main())
