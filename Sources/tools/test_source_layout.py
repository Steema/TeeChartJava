import json
from pathlib import Path
import re
import shutil
import subprocess
import tempfile
import unittest
import xml.etree.ElementTree as ET
import zlib

import source_layout as layout


class SourceLayoutTests(unittest.TestCase):
    def setUp(self):
        self.temp = tempfile.TemporaryDirectory(prefix="teechart-layout-")
        self.addCleanup(self.temp.cleanup)
        self.base = Path(self.temp.name)
        self.common = self.base / "Common/src"
        self.local = self.base / "Android/src"
        for root in (self.common, self.local):
            (root / "com/example").mkdir(parents=True)
        (self.common / "com/example/Shared.java").write_bytes(b"class Shared {}\r\n")
        (self.common / "com/example/image.png").write_bytes(b"\x89PNG\r\n\x00")
        (self.local / "com/example/Local.java").write_bytes(b"class Local {}\n")

    def test_materialization_preserves_source_and_resource_bytes(self):
        output = self.base / "output with spaces"
        layout.materialize([self.common, self.local], output)
        for name, source in layout.source_files([self.common, self.local]).items():
            self.assertEqual(source.read_bytes(), (output / name).read_bytes())

    def test_identical_duplicates_are_rejected_before_writing(self):
        (self.local / "com/example/Shared.java").write_bytes(b"class Shared {}\r\n")
        output = self.base / "output"
        with self.assertRaisesRegex(layout.LayoutError, "Duplicate"):
            layout.materialize([self.common, self.local], output)
        self.assertFalse(output.exists())

    def test_case_collisions_are_rejected_on_every_platform(self):
        (self.local / "com/example/shared.java").write_bytes(b"class Other {}\n")
        with self.assertRaisesRegex(layout.LayoutError, "Duplicate"):
            layout.source_files([self.common, self.local])

    def test_output_cannot_overwrite_sources_or_existing_work(self):
        for output in (self.common, self.common / "nested", self.base):
            with self.assertRaises(layout.LayoutError):
                layout.materialize([self.common, self.local], output)
        output = self.base / "existing"
        output.mkdir()
        (output / "keep.txt").write_text("keep")
        with self.assertRaisesRegex(layout.LayoutError, "new or empty"):
            layout.materialize([self.common, self.local], output)
        self.assertEqual("keep", (output / "keep.txt").read_text())

    def test_filelist_quotes_paths_and_excludes_resources(self):
        output = self.base / "sources.args"
        layout.write_filelist([self.common, self.local], output)
        names = output.read_text(encoding="utf-8").splitlines()
        self.assertEqual(2, len(names))
        self.assertTrue(all(name.startswith('"') and name.endswith('.java"') for name in names))

    def test_metadata_and_compiler_outputs_are_not_source_inputs(self):
        (self.local / ".classpath").write_text("metadata")
        (self.local / "filelist.txt").write_text("stale list")
        (self.local / "com/example/Local.class").write_bytes(b"class bytes")
        (self.local / "META-INF").mkdir()
        (self.local / "META-INF/MANIFEST.MF").write_text("Manifest-Version: 1.0\n")
        names = layout.source_files([self.common, self.local])
        self.assertEqual(4, len(names))
        self.assertIn("META-INF/MANIFEST.MF", names)

    def test_snapshot_detects_a_changed_resource(self):
        roots = {variant: [self.common, self.local] for variant in layout.VARIANTS}
        baseline = json.loads(json.dumps(layout.snapshot(roots)))
        layout.check_baseline(roots, baseline)
        (self.common / "com/example/image.png").write_bytes(b"changed")
        with self.assertRaisesRegex(layout.LayoutError, "image.png"):
            layout.check_baseline(roots, baseline)

    def test_archive_roots_are_never_accepted(self):
        (self.base / "Archive/src").mkdir(parents=True)
        (self.base / "source-layout.properties").write_text(
            "\n".join(v + ".source.roots=Archive/src" for v in layout.VARIANTS))
        with self.assertRaisesRegex(layout.LayoutError, "Invalid source root"):
            layout.load_layout(self.base)

    def test_repository_root_cannot_be_a_source_root(self):
        (self.base / "source-layout.properties").write_text(
            "\n".join(v + ".source.roots=." for v in layout.VARIANTS))
        with self.assertRaisesRegex(layout.LayoutError, "Invalid source root"):
            layout.load_layout(self.base)

    def run_powershell_stage(self, output, *options, source_roots="Common/src;Android/src"):
        tools = self.base / "tools"
        tools.mkdir(exist_ok=True)
        shutil.copyfile(layout.SOURCE_DIR / "tools/StageSources.ps1", tools / "StageSources.ps1")
        (self.base / "source-layout.properties").write_text(
            "\n".join(v + ".source.roots=" + source_roots for v in layout.VARIANTS))
        return subprocess.run(
            ["powershell.exe", "-NoProfile", "-ExecutionPolicy", "Bypass", "-File",
             str(tools / "StageSources.ps1"), "-Variant", "Android",
             "-OutputDirectory", str(output)] + list(options),
            capture_output=True, text=True, encoding="utf-8", errors="replace")

    @unittest.skipUnless(shutil.which("powershell.exe"), "Windows PowerShell is not installed")
    def test_windows_staging_matches_python_and_preserves_resources(self):
        output = self.base / "output with spaces"
        resources = self.base / "resource output"
        result = self.run_powershell_stage(output, "-ResourceOutputDirectory", str(resources))
        self.assertEqual(0, result.returncode, result.stderr)
        expected = layout.source_files([self.common, self.local])
        for name, source in expected.items():
            self.assertEqual(source.read_bytes(), (output / name).read_bytes())
        self.assertEqual(b"\x89PNG\r\n\x00", (resources / "com/example/image.png").read_bytes())
        self.assertFalse((resources / "com/example/Shared.java").exists())
        self.assertEqual(2, len((output / "javac.args").read_text(encoding="utf-8").splitlines()))

    @unittest.skipUnless(shutil.which("powershell.exe"), "Windows PowerShell is not installed")
    def test_windows_staging_rejects_duplicates_before_writing(self):
        (self.local / "com/example/shared.java").write_bytes(b"class Other {}\n")
        output = self.base / "output"
        result = self.run_powershell_stage(output)
        self.assertNotEqual(0, result.returncode)
        self.assertIn("Duplicate", result.stderr)
        self.assertFalse(output.exists())

    @unittest.skipUnless(shutil.which("powershell.exe"), "Windows PowerShell is not installed")
    def test_windows_staging_never_replaces_unowned_directories(self):
        output = self.base / "existing"
        output.mkdir()
        (output / "keep.txt").write_text("keep")
        result = self.run_powershell_stage(output, "-Replace")
        self.assertNotEqual(0, result.returncode)
        self.assertEqual("keep", (output / "keep.txt").read_text())

    @unittest.skipUnless(shutil.which("powershell.exe"), "Windows PowerShell is not installed")
    def test_windows_staging_rebuilds_owned_outputs_without_stale_files(self):
        output = self.base / "output"
        result = self.run_powershell_stage(output)
        self.assertEqual(0, result.returncode, result.stderr)
        (output / "stale.txt").write_text("stale")
        result = self.run_powershell_stage(output, "-Replace")
        self.assertEqual(0, result.returncode, result.stderr)
        self.assertFalse((output / "stale.txt").exists())

    @unittest.skipUnless(shutil.which("powershell.exe"), "Windows PowerShell is not installed")
    def test_windows_staging_rejects_archives_and_repository_wide_roots(self):
        (self.base / "Archive/src").mkdir(parents=True)
        for roots in ("Archive/src", "."):
            output = self.base / "output"
            result = self.run_powershell_stage(output, source_roots=roots)
            self.assertNotEqual(0, result.returncode)
            self.assertFalse(output.exists())


class ProjectConfigurationTests(unittest.TestCase):
    @staticmethod
    def properties(path):
        return dict(line.split("=", 1) for line in path.read_text(encoding="utf-8").splitlines()
                    if line and not line.startswith("#") and "=" in line)

    def test_eclipse_projects_use_the_declared_source_roots(self):
        declared = layout.load_layout()
        for variant, project in (("Swing", "Swing"), ("SWT", "SWT/src"), ("Android", "Android")):
            base = layout.SOURCE_DIR / project
            links = {}
            for link in ET.parse(base / ".project").findall("./linkedResources/link"):
                uri = link.findtext("locationURI")
                variable, relative = uri.split("/", 1)
                levels = int(variable.split("-")[1])
                parent = base
                for _ in range(levels):
                    parent = parent.parent
                links[link.findtext("name")] = (parent / relative).resolve()
            actual = []
            for entry in ET.parse(base / ".classpath").getroot():
                if entry.get("kind") == "src":
                    name = entry.get("path")
                    if name == "gen":
                        continue
                    actual.append(links.get(name, (base / name).resolve()))
            self.assertEqual(set(declared[variant]), set(actual), variant)

    def test_current_layout_has_no_duplicate_inputs(self):
        for roots in layout.load_layout().values():
            self.assertTrue(layout.source_files(roots))

    def test_netbeans_source_roots_and_resource_copy_match_the_layout(self):
        base = layout.SOURCE_DIR / "Swing/NetBeans"
        properties = self.properties(base / "nbproject/project.properties")

        def expand(value):
            while "${" in value:
                value = re.sub(r"\$\{([^}]+)\}", lambda m: properties[m.group(1)], value)
            return value

        project = ET.parse(base / "nbproject/project.xml")
        namespace = {"j": "http://www.netbeans.org/ns/j2se-project/3"}
        names = [root.get("id") for root in project.findall(".//j:source-roots/j:root", namespace)]
        roots = {(base / expand(properties[name])).resolve() for name in names}
        self.assertEqual(set(layout.load_layout()["Swing"]), roots)
        build = ET.parse(base / "nbproject/build-impl.xml")
        resource_roots = {entry.get("dir") for entry in build.findall("./target[@name='-do-compile']/copy/fileset")}
        self.assertEqual({"${" + name + "}" for name in names}, resource_roots)
        for attribute in build.findall(".//macrodef[@name='javac']/attribute[@name='srcdir']"):
            self.assertEqual("${src.path}", attribute.get("default"))

    def test_netbeans_generated_file_metadata_is_in_sync(self):
        base = layout.SOURCE_DIR / "Swing/NetBeans"
        properties = self.properties(base / "nbproject/genfiles.properties")

        def crc(path):
            content = path.read_bytes().replace(b"\r\n", b"\n").replace(b"\r", b"\n")
            return format(zlib.crc32(content), "08x")

        for name in ("build.xml", "nbproject/build-impl.xml"):
            self.assertEqual(crc(base / "nbproject/project.xml"), properties[name + ".data.CRC32"])
            self.assertEqual(crc(base / name), properties[name + ".script.CRC32"])

    def test_legacy_android_ant_uses_the_android_source_roots(self):
        base = layout.SOURCE_DIR / "Android"
        build = ET.parse(base / "custom_rules.xml")
        roots = {(base / entry.get("dir")).resolve()
                 for entry in build.findall("./target[@name='-pre-build']/copy/fileset")}
        self.assertEqual(set(layout.load_layout()["Android"]), roots)
        self.assertEqual("build/ant-src", self.properties(base / "ant.properties")["source.dir"])

    def test_gradle_compiles_and_packages_the_declared_android_roots(self):
        build = (layout.SOURCE_DIR / "Android/build.gradle").read_text(encoding="utf-8")
        self.assertIn("file('../source-layout.properties')", build)
        self.assertIn("getProperty('Android.source.roots')", build)
        self.assertIn("java.srcDirs = librarySourceDirs", build)
        self.assertIn("resources.srcDirs = librarySourceDirs", build)
        self.assertIn("it.name == 'preBuild'", build)
        self.assertIn("dependsOn verifySourceLayout", build)

    def test_android_demo_eclipse_links_resolve_to_both_library_roots(self):
        base = layout.SOURCE_DIR.parent / "Demos/Android"
        if not base.is_dir():
            self.skipTest("The independent Demos repository is not present")
        project = ET.parse(base / ".project")
        links = {}
        for link in project.findall("./linkedResources/link"):
            variable, relative = link.findtext("locationURI").split("/", 1)
            parent = base
            for _ in range(int(variable.split("-")[1])):
                parent = parent.parent
            links[link.findtext("name")] = (parent / relative).resolve()
        classpath = ET.parse(base / ".classpath")
        used_links = {entry.get("path") for entry in classpath.getroot()
                      if entry.get("kind") == "src" and entry.get("path") in links}
        self.assertEqual(set(layout.load_layout()["Android"]), {links[name] for name in used_links})

    def test_build_number_sources_remain_platform_specific(self):
        # Recompile's explicit version update still edits these three files.
        for variant in layout.VARIANTS:
            self.assertTrue((layout.SOURCE_DIR / variant / "src/com/steema/teechart/misc/Utils.java").is_file())


if __name__ == "__main__":
    unittest.main()
