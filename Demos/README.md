# TeeChart Java examples

| Directory | Purpose |
| --- | --- |
| `Swing/Features` | Swing Features, NetBeans/Ant |
| `Swing/StandardSeriesDemo` | Standard-series demo, NetBeans/Ant |
| `Swing/CandleScrollerRealTime` | Real-time candle example, NetBeans/Ant |
| `Swing`, `AWT` | Additional desktop examples |
| `SWT/Features` | SWT Features, Eclipse project in `src` |
| `AndroidDemo` | Separate Gradle 5.6.4 / plugin 3.6.4 app; source-library dependency |

These directories belong to the same repository as `../Sources`. Keep that
relative layout. Historical projects, customer issue reproductions, build
outputs, personal IDE state and signing keys are not included.

The Swing JDBC example uses H2 in-memory sample data and makes no network
connection. To run it, obtain H2 2.2.224 from Maven Central and place
`h2-2.2.224.jar` in `Demos/Swing/Features/lib/` (the third-party driver is not
bundled). No database server or credentials are needed.

See [../BUILDING.md](../BUILDING.md) for commands and known legacy-toolchain limits.
See [../LICENSING.md](../LICENSING.md) for first-party license scope and separate
third-party terms. The Swing syntax-highlighter implementation is a documented
publication replacement.
