# Security and credential handling

Do not attach private keys, keystores, tokens, customer data or credentials to
public issues or pull requests. Use the copyright owner's established private
security/support channel; a dedicated disclosure address has not yet been
approved for this draft.

This legacy snapshot has not undergone a complete vulnerability audit. Its old
toolchain/dependencies and XML/serialization/network functionality need an
independent security assessment before security-sensitive production use.

Run `python tools/check_publication.py` and an independent secret scanner before
committing. The built-in checks are deliberately conservative but cannot prove
the absence of secrets. Generic scanners may miss short passwords and binary
keystores. Review code, files and history separately.

The 20 reviewed `Password` UI labels in the Common translation catalogs have
exceptions pinned to exact paths, line numbers and line SHA-256 fingerprints in
`tools/credential_rules.py`. This is not an exemption for `.properties` files:
changed labels, new entries and other credential rules still require review.

If a real credential was exposed, removing it from Git is insufficient: arrange
owner-led revocation/rotation and assess all previous copies. Signing-key
replacement may affect application updates and must follow the platform's
supported process.
