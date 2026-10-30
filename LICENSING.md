# License scope

**Steema-owned code, documentation, translations and resources in this repository
are licensed under Apache-2.0; see [LICENSE](LICENSE) and [NOTICE](NOTICE).**
The copyright owner authorized this release policy and confirmed its authority
over the first-party material on 2026-10-30. This includes the supplied geographic
data, images, icons, tutorials and translations, based on that owner confirmation.

Apache-2.0 permits free commercial and closed-source use subject to its terms,
including notice requirements. It includes a contributor patent grant but no
trademark grant. Older first-party “All Rights Reserved” headers identify their
copyright owners; the root license grants rights for those Steema-owned files in
this repository. It does not change licenses on separately supplied third-party
material or on copies in the old private repositories.

## Third-party scope and replacements

- Android Open Source Project color-picker examples retain their Apache-2.0
  copyright/license notices.
- The Gradle wrapper binaries retain their separate upstream Apache-2.0 terms
  and notices in `LICENSES/Gradle-*-LICENSE.txt` / `Gradle-*-NOTICE.txt`.
- Eclipse binaries are **not distributed in this repository**. They are external
  dependencies obtained from the official archive by a checksum-pinned recipe.
  Their EPL and bundled third-party terms are not replaced by Apache-2.0.
- The old Sun-noticed listener/mouse implementations, VNCj pixel implementation
  and adapted syntax scanner are **not included**. New implementations retain
  the public APIs. This is replacement, not re-licensing of the old files. See
  [SOURCE_REPLACEMENTS.md](SOURCE_REPLACEMENTS.md) for provenance and tests.

See [THIRD_PARTY.md](THIRD_PARTY.md) for exact components and source links. If new
third-party material is added, review its actual terms before inclusion; the
root license cannot grant rights owned by somebody else.

## Publication and security

`PUBLICATION_STATUS.json` records technical release clearance, not a guarantee of
legal or security completeness. Re-run checks after changing the snapshot.
The local preparation hook blocks pushing until a GitHub destination and push
approval are explicitly selected; it is not inherited automatically by clones.

Private histories, signing material and activation tools are not included.
Removing a credential from a snapshot does not revoke it or sanitize old copies.
Previously exposed credentials need owner-led revocation/rotation; signing-key
replacement must follow the relevant platform process.
