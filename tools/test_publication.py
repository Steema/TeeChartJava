from pathlib import Path
import html
import json
import os
import subprocess
import sys
import tempfile
import unittest
from types import SimpleNamespace
from unittest.mock import patch

from check_publication import JARS, findings
from credential_rules import REVIEWED_UI_LABELS, UI_LABEL_DIRECTORY, is_reviewed_ui_label, secret_locations


class PublicationTests(unittest.TestCase):
    def setUp(self):
        self.temp = tempfile.TemporaryDirectory(prefix='teechart-public-check-')
        self.addCleanup(self.temp.cleanup)
        self.root = Path(self.temp.name)

    def test_short_passwords_are_not_missed(self):
        text = 'store' + 'Password' + " 'unit-test-value'\n"
        (self.root / 'build.gradle').write_text(text)
        self.assertTrue(any(rule == 'literal-password' for _, rule in findings(self.root)))

    def test_extensionless_keystores_are_detected(self):
        (self.root / 'signing-data').write_bytes(bytes.fromhex('feedfeed00000002'))
        self.assertTrue(any(rule == 'java-keystore-magic' for _, rule in findings(self.root)))

    def test_dotenv_files_are_not_hidden_by_gitignore(self):
        for name in ('.env', '.env.local'):
            (self.root / name).write_text('local environment fixture')
        self.assertEqual(2, len(findings(self.root)))
        self.assertTrue(all(rule == 'private-local-or-unapproved-binary-file' for _, rule in findings(self.root)))

    def test_unapproved_jars_are_rejected(self):
        (self.root / 'unreviewed.jar').write_bytes(b'not a reviewed dependency')
        self.assertTrue(any(rule == 'unapproved-jar-or-checksum' for _, rule in findings(self.root)))

    def test_nested_repositories_are_rejected(self):
        (self.root / 'Sources/.git').mkdir(parents=True)
        self.assertTrue(any(rule == 'private-or-nested-directory' for _, rule in findings(self.root)))

    def test_missing_release_clearance_blocks_publication(self):
        self.assertEqual([], findings(self.root))
        self.assertTrue(findings(self.root, release=True))

    def test_json_and_yaml_quoted_keys_are_detected(self):
        for key in ('pass' + 'word', 'client' + '_secret', 'db' + 'Password', 'api' + 'Key'):
            with self.subTest(key=key):
                data = json.dumps({key: 'unit-test-value'}).encode('utf-8')
                self.assertTrue(secret_locations(data, '.json'))

    def test_xml_elements_and_properties_are_detected(self):
        name = 'pass' + 'word'
        samples = [
            '<{0}>unit-test-value</{0}>'.format(name),
            '<property name="{}" value="unit-test-value" />'.format(name),
            '<property value="unit-test-value" name="{}" />'.format(name),
        ]
        for text in samples:
            with self.subTest(text=text):
                self.assertTrue(secret_locations(text.encode('utf-8'), '.xml'))

    def test_unquoted_config_values_are_detected(self):
        for suffix, separator in (('.properties', '='), ('.ini', '='), ('.env', '='), ('.yaml', ':')):
            data = ('DB_' + 'PASSWORD' + separator + 'unit-test-value').encode('utf-8')
            with self.subTest(suffix=suffix):
                self.assertTrue(secret_locations(data, suffix))

    def test_config_environment_references_are_not_literal_credentials(self):
        text = 'db_' + 'password' + '=${DB_CREDENTIAL_FROM_ENV}'
        self.assertEqual([], secret_locations(text.encode('utf-8'), '.properties'))

    def test_utf16_and_utf32_credentials_are_detected(self):
        text = json.dumps({'client' + '_secret': 'unit-test-value'})
        for encoding in ('utf-16', 'utf-32'):
            with self.subTest(encoding=encoding):
                self.assertTrue(secret_locations(text.encode(encoding), '.json'))

    def test_html_escaped_credentials_are_detected(self):
        name = 'pass' + 'word'
        text = '<{0}>unit-test-value</{0}>'.format(name)
        self.assertTrue(secret_locations(html.escape(text).encode('utf-8'), '.html'))

    def test_jdbc_documentation_credentials_are_detected(self):
        text = 'DriverManager.' + 'getConnection(url, "unit-test-user", "unit-test-value")'
        self.assertTrue(secret_locations(html.escape(text).encode('utf-8'), '.html'))

    def test_similar_noncredential_keys_are_not_flagged(self):
        text = json.dumps({'password_policy': 'required', 'token_count': 4})
        self.assertEqual([], secret_locations(text.encode('utf-8'), '.json'))

    def test_tracked_build_output_is_not_hidden_by_directory_skipping(self):
        (self.root / '.git').mkdir()
        with patch('check_publication.subprocess.run', return_value=SimpleNamespace(stdout=b'build/unexpected.txt\0')):
            self.assertTrue(any(rule == 'tracked-generated-output' for _, rule in findings(self.root)))

    def test_external_eclipse_jars_cannot_be_force_added(self):
        (self.root / '.git').mkdir()
        with patch('check_publication.subprocess.run', return_value=SimpleNamespace(stdout=b'Sources/SWT/libs/example.jar\0')):
            self.assertTrue(any(rule == 'external-eclipse-dependency-must-not-be-bundled' for _, rule in findings(self.root)))

    def test_dependency_recipe_matches_the_hygiene_checksums(self):
        root = Path(__file__).resolve().parents[1]
        recipe = json.loads((root / 'Sources/SWT/dependencies.json').read_text(encoding='utf-8'))
        self.assertEqual(3, len(recipe['dependencies']))
        for dependency in recipe['dependencies']:
            self.assertEqual(JARS['Sources/SWT/libs/' + dependency['file']], dependency['sha256'])
            self.assertTrue(dependency['url'].startswith('https://archive.eclipse.org/'))
            self.assertTrue(dependency['source_url'].startswith('https://archive.eclipse.org/'))
            self.assertEqual(64, len(dependency['source_sha256']))

    @unittest.skipUnless(os.name == 'nt', 'Windows PowerShell dependency check')
    def test_dependency_fetch_refuses_to_overwrite_unknown_existing_binary(self):
        root = Path(__file__).resolve().parents[1]
        recipe = json.loads((root / 'Sources/SWT/dependencies.json').read_text(encoding='utf-8'))
        path = self.root / recipe['dependencies'][0]['file']
        path.write_bytes(b'unreviewed-binary-fixture')
        result = subprocess.run(['powershell.exe', '-NoProfile', '-ExecutionPolicy', 'Bypass', '-File',
                                 str(root / 'Sources/SWT/GetDependencies.ps1'), '-LibraryDirectory', str(self.root)],
                                capture_output=True)
        self.assertNotEqual(0, result.returncode)
        self.assertEqual(b'unreviewed-binary-fixture', path.read_bytes())

    def test_secret_values_are_never_printed(self):
        value = 'unit-test-sensitive-value'
        (self.root / 'config.json').write_text(json.dumps({'pass' + 'word': value}))
        result = subprocess.run(
            [sys.executable, str(Path(__file__).with_name('check_publication.py')), '--root', str(self.root)],
            capture_output=True, text=True)
        self.assertNotEqual(0, result.returncode)
        self.assertNotIn(value, result.stdout + result.stderr)

    def catalog(self, name='languages_en.properties', newline=b'\n'):
        line = REVIEWED_UI_LABELS[name][0]
        labels = {'languages_ca.properties': b'Contrasenya',
                  'languages_es.properties': b'Contrase\\u00f1a'}
        data = newline * (line - 1) + b'Pass' + b'word=' + labels.get(name, b'Password') + newline
        return UI_LABEL_DIRECTORY + '/' + name, data, line

    def test_ui_label_exceptions_require_exact_path_line_rule_and_fingerprint(self):
        for name in REVIEWED_UI_LABELS:
            with self.subTest(name=name):
                relative, data, line = self.catalog(name)
                rule = 'literal-config-credential'
                self.assertTrue(is_reviewed_ui_label(relative, data, rule, line))
                self.assertFalse(is_reviewed_ui_label('other/' + relative, data, rule, line))
                self.assertFalse(is_reviewed_ui_label(relative, data, rule, line + 1))
                self.assertFalse(is_reviewed_ui_label(relative, data, 'literal-password', line))
                changed = data.rstrip(b'\n') + b'-changed\n'
                self.assertFalse(is_reviewed_ui_label(relative, changed, rule, line))

    def test_new_credentials_in_reviewed_translation_files_are_detected(self):
        relative, data, line = self.catalog()
        path = self.root / relative
        path.parent.mkdir(parents=True)
        path.write_bytes(data + b'db_' + b'password=unit-test-value\n')
        self.assertEqual([(relative + ':' + str(line + 1), 'literal-config-credential')], findings(self.root))
        path.write_bytes(b'\n' * (line - 1) + b'Pass' + b'word=' + b'unit-test-value\n')
        self.assertEqual([(relative + ':' + str(line), 'literal-config-credential')], findings(self.root))

    def test_unreviewed_translation_files_have_no_blanket_exception(self):
        relative, data, line = self.catalog()
        for name in (relative.replace('languages_en.', 'languages_new.'), 'config.properties'):
            path = self.root / name
            path.parent.mkdir(parents=True, exist_ok=True)
            path.write_bytes(data)
        self.assertEqual(2, len(findings(self.root)))

    def test_reviewed_labels_survive_crlf_or_lf_checkout(self):
        for newline in (b'\n', b'\r\n'):
            relative, data, _ = self.catalog(newline=newline)
            path = self.root / relative
            path.parent.mkdir(parents=True, exist_ok=True)
            path.write_bytes(data)
            self.assertEqual([], findings(self.root))


if __name__ == '__main__':
    unittest.main()
