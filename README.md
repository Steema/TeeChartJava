# TeeChart Java

Charting library and examples under **Apache-2.0 for Steema-owned material**.
Free commercial and closed-source use is permitted under the license terms.

TeeChart charting library and examples for **Swing, SWT and Android**, in one
repository. This candidate preserves the library's existing Java packages, APIs,
source/target settings and shared-source composition.

```text
Sources/
  Common/src/        # Compiled separately for every platform
  Desktop/src/       # Shared by Swing and SWT
  Swing/             # Eclipse and NetBeans/Ant
  SWT/               # Eclipse and Windows command-line build
  Android/           # Android library, Gradle and legacy Eclipse/Ant metadata
Demos/               # Desktop and Android examples
Docs/                # Tutorials and images
```

This is not a platform-independent `core.jar`. Each variant compiles its composed
source roots against its own platform's types. The manifest is
[`Sources/source-layout.properties`](Sources/source-layout.properties).

## Build and verify

See [BUILDING.md](BUILDING.md) for toolchain requirements and commands. No IDE
workspace, private signing key, activation utility or prebuilt TeeChart JAR is
required for the public library builds. Optional layout checks use Python:

```text
python Sources/tools/source_layout.py verify
python -m unittest discover -s Sources/tools -p "test_*.py" -v
python tools/check_publication.py
```

The root Gradle project builds the Android library and original Features demo.
`Demos/AndroidDemo` retains its separate legacy Gradle project and now references
the source library rather than a bundled TeeChart JAR. This does not migrate it
to AndroidX or update its Java/SDK levels.

## Publication scope

Private Git histories, archives/manual issue reproductions, distribution and
activation tools, signing material, personal IDE state and build outputs are not
part of this candidate. Bundled Microsoft JDBC and Swing Layout binaries are
omitted pending redistribution review. JDBC examples require a separately
obtained compatible driver and explicit credentials via environment variables.

## License and compatibility

See [LICENSE](LICENSE), [LICENSING.md](LICENSING.md) and
[THIRD_PARTY.md](THIRD_PARTY.md) for the first-party license and separate upstream
terms. Eclipse dependencies are downloaded from official archives with pinned
checksums, not bundled in Git. The old licensing-uncertain implementations have
been replaced without changing public APIs; see
[SOURCE_REPLACEMENTS.md](SOURCE_REPLACEMENTS.md).

This is a legacy-toolchain release, not a complete security modernization or a
claim of exhaustive runtime validation. Known build/runtime limits are described
in [BUILDING.md](BUILDING.md) and [SECURITY.md](SECURITY.md).
