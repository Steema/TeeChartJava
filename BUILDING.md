# Building TeeChart Java

This is a legacy-toolchain snapshot, not a Java/Gradle/SDK modernization. A
successful build is not a complete runtime or security validation.

## Layout verification

Python 3.8+ is optional for these checks, not a dependency of the IDE builds:

```text
python Sources/tools/source_layout.py verify
python -m unittest discover -s Sources/tools -p "test_*.py" -v
python -m unittest discover -s tools -p "test_*.py" -v
python tools/run_compatibility_tests.py
```

## Swing

Open `Sources/Swing` in Eclipse, or `Sources/Swing/NetBeans` in NetBeans. Both
include Common, Desktop and platform-specific roots. NetBeans/Ant retains its
Java 1.6 source/target selections; use a compatible compiler, such as JDK 8.

```text
ant -f Sources/Swing/NetBeans/build.xml jar
```

The Ant project requires its normal NetBeans Ant task support. A compiler-only
Windows check without NetBeans can stage the same roots:

```powershell
powershell.exe -NoProfile -ExecutionPolicy Bypass -File Sources/tools/StageSources.ps1 -Variant Swing -OutputDirectory Sources/build/check-swing/src -ResourceOutputDirectory Sources/build/check-swing/classes
& "$env:JAVA_HOME\bin\javac.exe" -source 1.6 -target 1.6 -d Sources/build/check-swing/classes '@Sources/build/check-swing/src/javac.args'
```

Choose fresh/empty staging and resource output directories for this check. The
stager refuses to overwrite existing resource output, even when `-Replace` is
used for an owned source staging directory.

## SWT

On Windows with JDK 8, `build.bat` obtains the exact legacy dependencies from the
official Eclipse archive, verifies SHA-256 hashes and builds the library:

```text
Sources\SWT\build.bat -source 1.6 -target 1.6
```

For Eclipse, first run:

```text
powershell.exe -NoProfile -ExecutionPolicy Bypass -File Sources/SWT/GetDependencies.ps1
```

Then open `Sources/SWT/src`. Add `-IncludeSources` to obtain the matching source
bundles. Downloads go to ignored local directories, not the Git file set.
Existing files with unexpected checksums are rejected, never silently overwritten.

Output: `Sources/SWT/build/TeeChart.SWT.jar`. Dependencies target Windows x86-64;
they are not portable native binaries for other operating systems. See
`Sources/SWT/dependencies.json` and `THIRD_PARTY.md` for source URLs and terms.
SWT also retains its
pre-existing dependency on `sun.awt.image.IntegerComponentRaster`; a strict
`--release` API surface is not supported by that code.

## Android library

Use **JDK 11**, Gradle 5.4.1 (wrapper), plugin 3.5.0, SDK Platform 29 and Build-Tools
29.0.0. Configure your SDK path locally; do not commit `local.properties`.

```powershell
$env:ANDROID_HOME = Join-Path $env:LOCALAPPDATA 'Android\Sdk'
$env:ANDROID_SDK_ROOT = $env:ANDROID_HOME
.\gradlew.bat :Sources:Android:assembleDebug :Sources:Android:assembleRelease --no-daemon --console=plain
```

Gradle can install missing SDK packages only when the appropriate licenses have
already been accepted. Outputs are in `Sources/Android/build/outputs/aar`.

Current Android Studio's bundled JVM is not this legacy Gradle JVM. IDE sync and
running the app on devices/emulators are separate validation tasks.

## Separate AndroidDemo project

`Demos/AndroidDemo` retains Gradle 5.6.4 / plugin 3.6.4 and the app's existing SDK
26 settings. Its composed library uses SDK 29, as in the parent library build.
Its settings include `../../Sources/Android` as a source-project dependency.
Release signing is not configured. Obtain/configure signing material privately
if you intend to distribute an application; never commit it.

The legacy support-library dependencies and standalone app build need separate
validation; building this app is not required to validate the root library build.

## JDBC example

The Swing JDBC example uses an in-memory H2 database with local sample data; it
does not contact an external database. To run it, obtain H2 2.2.224 from Maven
Central and place `h2-2.2.224.jar` in `Demos/Swing/Features/lib/`. The driver is
not bundled; review its license and notices before redistribution. Compilation
and CI do not connect to any database.
