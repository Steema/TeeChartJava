# Shared-source verification

These optional tools use only the Python standard library (Python 3.8+):

```text
python Sources/tools/source_layout.py verify
python -m unittest discover -s Sources/tools -p "test_*.py" -v
```

They reject duplicate relative paths, including case collisions and identical
copies, and check the supported IDE source-root configuration. Tests exercise
Windows staging when Windows PowerShell is available.

From `Sources`, additional commands create snapshots, quoted javac argument files
or new flattened trees without changing source files:

```text
python tools/source_layout.py snapshot --output build/before.json
python tools/source_layout.py verify --baseline build/before.json
python tools/source_layout.py filelist --variant Swing --output build/swing.args
python tools/source_layout.py materialize --variant SWT --output build/inspect-swt
```

`StageSources.ps1` is compatible with Windows PowerShell 5.1 and is used by
`SWT/build.bat`. It reads the same manifest, detects collisions before writing,
and can copy resources separately. `-Replace` only rebuilds an owned staging
directory with a matching ownership marker. Output paths must be outside source
roots. Python is not required by the IDE builds or Windows staging.

See [../../BUILDING.md](../../BUILDING.md) for the unchanged compiler/SDK
requirements. Build correctness does not imply licensing/publication clearance.
