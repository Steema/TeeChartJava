# Publication replacements

Four legacy files had unclear or incompatible third-party licensing. They have
been replaced in this repository; this is **not** a new license on the old code.
The original files/histories are retained privately and are not distributed here.

The new implementations were written for the observable API/interoperability
contracts below. The audit inspected the legacy API and behavior; this was not
an isolated clean-room exercise. The replacement implementations do not
intentionally reuse the legacy algorithms or explanatory text. Standard API
signatures, language keywords, format constants and wire-field ordering are
retained where necessary for compatibility.

| Class | New implementation and retained contract |
| --- | --- |
| `com.steema.teechart.events.EventListenerList` | Registration edits use standard collection operations and publish new pair arrays. Retains the protected array field, exact-type matching, reverse retrieval/removal order, null-listener behavior, serialVersionUID 1 and type-name/listener/null serialization protocol. Non-serializable listeners are omitted. |
| Swing `FrameworkMouseEvent` | Thin AWT constructor adapter with covariant TeeChart `Point`, writable `widget`, serialVersionUID 1 and existing constructor signatures. Absolute-coordinate arguments remain ignored, deliberately retaining legacy behavior. |
| Desktop `misc.PixelFormat` | Explicit 13-byte network representation and per-channel bit-mask calculations. Retains public fields, presets, constructors, model accessors, pixel translation and diagnostic labels. The wire format intentionally does not add RFB padding bytes. |
| Demo `features.utils.JavaSyntaxDocument` | Full-document lexical pass using standard Swing styling. Retains the public vocabulary arrays and document API. The old package-private scanner/expanding-array implementation is no longer used or included. |

## Validation

The private preparation checks compare public/protected signatures and JVM
descriptors of all four classes with the originals, exercise legacy library
behavior in both builds, and deserialize listener streams in both directions.
The public regression suite is independent of the private source tree:

```text
python tools/run_compatibility_tests.py
```

Use JDK 8 via `JAVA_HOME` (or `--java-home`). The suite checks listener ordering,
serialization, wire round-trips, pixel quantization, mouse adapters and document
restyling across edits. It does not claim every possible external subclass or
serialized object graph has been exhaustively tested.

The replacement highlighter deliberately adds Java `//` comments and escaped
quotes, while retaining legacy `--` comments and mathematical function names.
It restyles the whole document after edits rather than maintaining the old
incremental scanner. This is simpler and correct across multiline edits but can
cost more time for large documents; it is a demo source viewer, not a compiler.
