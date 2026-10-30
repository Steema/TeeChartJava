"""Publication hygiene checks; output contains locations, never secret values."""

import argparse
import hashlib
import json
import os
from pathlib import Path
import subprocess

from credential_rules import decode_text, is_reviewed_ui_label, secret_locations

SKIP = {'.git', '.gradle', '__pycache__', 'build', 'bin', 'gen', 'dist', 'classes'}
PRIVATE_DIRS = {'Archive', 'Recompile', 'Installers', 'Publication', 'signkeys', 'keystore',
                'TeeChart.Installer', 'TeeChart.GetActivationKeys', 'TeeChart.PhoneKey', '.idea', '.svn', '.hg'}
PRIVATE_SUFFIXES = {'.jks', '.keystore', '.p12', '.pfx', '.pem', '.key', '.iml', '.ipr', '.iws',
                    '.exe', '.dll', '.class', '.dcu', '.apk', '.aar', '.zip', '.pyc'}
JARS = {
    'gradle/wrapper/gradle-wrapper.jar': '3dc39ad650d40f6c029bd8ff605c6d95865d657dbfdeacdb079db0ddfffedf9f',
    'Demos/AndroidDemo/gradle/wrapper/gradle-wrapper.jar': '3dc39ad650d40f6c029bd8ff605c6d95865d657dbfdeacdb079db0ddfffedf9f',
    'Sources/SWT/libs/org.eclipse.draw2d_3.9.0.201308190730.jar': '4333ab0075a128ad4acc5cea174c82cfd290f5fc1e0678bb4ed6764cd7632959',
    'Sources/SWT/libs/org.eclipse.jface_3.9.1.v20130725-1141.jar': 'f1abe40941e26d158b3be1be9219dd2321566caddc7da1509b93b1a3825b99b7',
    'Sources/SWT/libs/org.eclipse.swt.win32.win32.x86_64_3.102.1.v20140206-1358.jar': '13929676395354ab5b1cd87e5447092960a61c10a7ae5f8f8ca82468d52be12d',
}


def is_link(path):
    return path.is_symlink() or bool(getattr(path.lstat(), 'st_file_attributes', 0) & 0x400)


def findings(root, release=False):
    problems = []
    if (root / '.git').exists():
        tracked = subprocess.run(['git', 'ls-files', '-z'], cwd=root, stdout=subprocess.PIPE,
                                 stderr=subprocess.PIPE, check=True).stdout.decode('utf-8')
        for name in filter(None, tracked.split('\0')):
            if name.startswith('Sources/SWT/libs/') and name.endswith('.jar'):
                problems.append((name, 'external-eclipse-dependency-must-not-be-bundled'))
            if any(part.lower() in SKIP - {'.git'} for part in Path(name).parts):
                problems.append((name, 'tracked-generated-output'))
    for directory, dirs, names in os.walk(root):
        parent = Path(directory)
        blocked_dirs = set()
        for name in dirs:
            if name.lower() in {item.lower() for item in PRIVATE_DIRS} or (name == '.git' and parent != root):
                problems.append(((parent / name).relative_to(root).as_posix(), 'private-or-nested-directory'))
                blocked_dirs.add(name)
            elif name != '.git' and is_link(parent / name):
                problems.append(((parent / name).relative_to(root).as_posix(), 'symlink-requires-review'))
                blocked_dirs.add(name)
        dirs[:] = [name for name in dirs if name.lower() not in SKIP and name not in blocked_dirs]
        for name in names:
            path = parent / name
            relative = path.relative_to(root).as_posix()
            if is_link(path):
                problems.append((relative, 'symlink-requires-review'))
                continue
            if (path.suffix.lower() in PRIVATE_SUFFIXES or name in {'local.properties', '.env'}
                    or name.startswith('.env.')
                    or name in {'.git', 'id_rsa', 'id_ed25519'} or 'key store' in name.lower()):
                problems.append((relative, 'private-local-or-unapproved-binary-file'))
                continue
            if path.suffix.lower() == '.jar':
                if JARS.get(relative) != hashlib.sha256(path.read_bytes()).hexdigest():
                    problems.append((relative, 'unapproved-jar-or-checksum'))
                continue
            if path.stat().st_size > 5_000_000:
                problems.append((relative, 'large-file-requires-manual-review'))
                continue
            data = path.read_bytes()
            if data.startswith(bytes.fromhex('feedfeed')):
                problems.append((relative, 'java-keystore-magic'))
            for rule, line in secret_locations(data, path.suffix):
                if not is_reviewed_ui_label(relative, data, rule, line):
                    problems.append((relative + ':' + str(line), rule))
            text = decode_text(data)
            if text is None:
                continue
            if release and path.suffix == '.java' and 'SUN PROPRIETARY/CONFIDENTIAL' in text:
                problems.append((relative, 'unresolved-proprietary-source-notice'))
            if release and relative == 'Sources/Desktop/src/com/steema/teechart/misc/PixelFormat.java' and 'GNU General Public License' in text:
                problems.append((relative, 'GPL-compatibility-review-pending'))
    if release:
        status = root / 'PUBLICATION_STATUS.json'
        state = json.loads(status.read_text(encoding='utf-8')) if status.is_file() else {}
        if not state.get('ready_for_publication') or not state.get('license_applied') or state.get('pending'):
            problems.append(('PUBLICATION_STATUS.json', 'copyright-owner-release-clearance-pending'))
        if not (root / 'LICENSE').is_file():
            problems.append(('LICENSE', 'approved-project-license-missing'))
    return problems


def main():
    parser = argparse.ArgumentParser(description=__doc__)
    parser.add_argument('--root', type=Path, default=Path(__file__).resolve().parents[1])
    parser.add_argument('--release', action='store_true', help='Also enforce copyright-owner release clearance')
    args = parser.parse_args()
    problems = findings(args.root.resolve(), args.release)
    for path, rule in problems:
        print(path + ': ' + rule)
    if problems:
        print('FAILED: {} publication issue(s); no values printed'.format(len(problems)))
        return 1
    print('Hygiene checks passed. This is not proof of secret absence or license clearance.')
    if not args.release:
        print('Publication remains subject to LICENSING.md and the --release gate.')
    return 0


if __name__ == '__main__':
    raise SystemExit(main())
