# TeeChart Java sources

| Variant | Source roots | Build entry points |
| --- | --- | --- |
| Swing | `Common/src` + `Desktop/src` + `Swing/src` | Eclipse: `Swing`; NetBeans/Ant: `Swing/NetBeans` |
| SWT | `Common/src` + `Desktop/src` + `SWT/src` | Eclipse: `SWT/src`; Windows: `SWT/build.bat` |
| Android | `Common/src` + `Android/src` | Root Gradle project; legacy Eclipse/Ant metadata in `Android` |

`source-layout.properties` is the source-root manifest. Shared sources compile
separately against each platform's types; they do not form a common binary JAR.
Every relative Java/resource path must occur exactly once per composed variant.
Do not add local copies to override shared files.

The original extraction moved only identical files: 371 Java files / 180 resources
to Common and 86 Java files / 33 resources to Desktop. It removed 828 Java copies
and 393 resource copies without merging divergent platform behavior.

See [../BUILDING.md](../BUILDING.md) and [tools/README.md](tools/README.md). Eclipse
links, NetBeans resource copying, Gradle duplicate verification and the optional
Windows/Python staging tools compose the same roots. Compiler/SDK versions have
not been modernized.

Historical platforms, private distribution/licensing utilities and signing
material are deliberately not included. This directory is part of the single
monorepo, not a nested Git repository.

First-party licensing is described in [../LICENSING.md](../LICENSING.md).
Three library implementations have explicit publication replacements; all other
Java/resource bytes retain the original baseline. See
[../SOURCE_REPLACEMENTS.md](../SOURCE_REPLACEMENTS.md). Existing notices on
retained third-party material are preserved.
