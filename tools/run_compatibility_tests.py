"""Compile and run dependency-free Java compatibility tests (JDK 8)."""

import argparse
import os
from pathlib import Path
import subprocess
import tempfile

SOURCES = (
    'Sources/Common/src/com/steema/teechart/events/EventListenerList.java',
    'Sources/Swing/src/com/steema/teechart/events/FrameworkMouseEvent.java',
    'Sources/Desktop/src/com/steema/teechart/misc/PixelFormat.java',
    'Demos/Swing/Features/src/features/utils/JavaSyntaxDocument.java',
    'Sources/Desktop/src/com/steema/teechart/drawing/Point.java',
)


def java_tool(java_home, name):
    return str(Path(java_home) / 'bin' / (name + ('.exe' if os.name == 'nt' else ''))) if java_home else name


def compile_sources(root, output, java_home, harness=None):
    harness = harness or root / 'tests/compatibility/CompatibilityChecks.java'
    output.mkdir(parents=True, exist_ok=True)
    subprocess.run([java_tool(java_home, 'javac'), '-source', '1.6', '-target', '1.6',
                    '-d', str(output)] + [str(root / name) for name in SOURCES] + [str(harness)], check=True)


def main():
    parser = argparse.ArgumentParser(description=__doc__)
    parser.add_argument('--java-home', default=os.environ.get('JAVA_HOME'))
    args = parser.parse_args()
    root = Path(__file__).resolve().parents[1]
    build = root / 'Sources/build'
    build.mkdir(exist_ok=True)
    with tempfile.TemporaryDirectory(prefix='compatibility-', dir=str(build)) as directory:
        output = Path(directory)
        compile_sources(root, output, args.java_home)
        subprocess.run([java_tool(args.java_home, 'java'), '-Djava.awt.headless=true',
                        '-cp', str(output), 'CompatibilityChecks'], check=True)


if __name__ == '__main__':
    main()
