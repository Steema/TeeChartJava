from pathlib import Path
import tempfile
import unittest

import source_layout as layout


class OutputAliasTests(unittest.TestCase):
    def test_source_alias_is_resolved_before_overlap_checks(self):
        with tempfile.TemporaryDirectory(prefix="teechart-output-alias-") as directory:
            base = Path(directory)
            source = base / "Common/src"
            source.mkdir(parents=True)
            (base / "Common/alias").mkdir()
            alias = base / "Common/alias/../src"
            for output in (source, source / "nested", base):
                with self.subTest(output=output):
                    with self.assertRaises(layout.LayoutError):
                        layout.check_output(output, [alias])
            self.assertFalse((source / "nested").exists())


if __name__ == "__main__":
    unittest.main()
