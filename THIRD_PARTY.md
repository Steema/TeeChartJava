# Third-party components

The root Apache-2.0 license covers first-party material, not arbitrary external
dependencies. Existing upstream notices are retained.

| Component | Location / distribution | Terms |
| --- | --- | --- |
| Android Open Source Project color-picker example, copyright 2007 | Both Android demo `ColorPickerDialog.java` files, included as source | Apache-2.0; original headers retained, full text in `LICENSES/Apache-2.0.txt`. |
| Gradle wrapper | Root 5.4.1 and standalone AndroidDemo 5.6.4 wrappers, included | Apache-2.0 with upstream notices in `LICENSES/Gradle-*-LICENSE.txt` and `Gradle-*-NOTICE.txt`. Official wrapper/distribution checksums are pinned. |
| Draw2D 3.9.0.201308190730 | External Eclipse dependency; not committed | EPL-1.0 as stated in `about.html`. |
| JFace 3.9.1.v20130725-1141 | External Eclipse dependency; not committed | EPL-1.0 as stated in `about.html`. |
| SWT Windows x86-64 3.102.1.v20140206-1358 | External Eclipse dependency; not committed | EPL-1.0 and bundled terms including Mozilla binding MPL-1.1, IJG JPEG and PuTTY notices. Preserve applicable `about.html` / `about_files` content when redistributing it. |
| Android support libraries / build plugins | External Gradle dependencies | Their own upstream terms; not re-licensed by this repository. |

## Exact Eclipse binaries and sources

[`Sources/SWT/dependencies.json`](Sources/SWT/dependencies.json) lists exact
official HTTPS archive URLs and SHA-256 hashes for all three binaries **and their
matching source bundles**. `GetDependencies.ps1` verifies existing/downloaded
files and refuses mismatches. `-IncludeSources` also downloads the pinned source
bundles into ignored build output. See [BUILDING.md](BUILDING.md).

The downloaded official archives were compared with the legacy dependencies:
all uncompressed entries, including classes and notices, are identical. JFace
and SWT ZIP compression differs, so their whole-file hashes are intentionally
those of the official downloads, not the old repacked JARs. Native SWT still
targets Windows x86-64; no dependency versions were upgraded.

If you redistribute external Eclipse binaries, comply with their licenses and
source-availability obligations yourself; this source repository does not ship
those binaries or a new license grant for them. An EPL-1.0 reference text is in
`LICENSES/EPL-1.0.html`.

## Excluded legacy implementations and binaries

The old Sun-noticed listener/mouse code, VNCj/GPL pixel implementation and adapted
syntax scanner are excluded, not re-licensed. Their replacements are documented
in [SOURCE_REPLACEMENTS.md](SOURCE_REPLACEMENTS.md).

Microsoft SQL Server drivers, Swing Layout 1.0.4, prebuilt TeeChart JARs, RetroGuard,
DocCheck and private distribution dependencies are also not bundled. Optional
drivers must be obtained and used under their own terms.
