"""Conservative credential heuristics. Only rule names and line numbers leave this module."""

import hashlib
import html
import re


NAME = (r'(?:(?:[a-z0-9]+[_-])*(?:password|passwd|pwd|secret|token|api[_-]?key|'
        r'access[_-]?(?:token|key)|refresh[_-]?token|client[_-]?secret|private[_-]?key)|'
        r'keyPassword|storePassword|dbPassword|clientSecret|accessToken|refreshToken|privateKey)')
RULES = {
    'literal-password': re.compile(
        r'''(?i)(?:\b''' + NAME + r'''\b\s*=\s*|\b(?:keyPassword|storePassword)\b\s+)["'][^"'\r\n]+["']'''),
    'quoted-credential-property': re.compile(
        r'''(?i)(["'])''' + NAME + r'''\1\s*:\s*(["'])[^"'\r\n]+\2'''),
    'literal-password-setter': re.compile(r'''(?i)\bsetPassword\s*\(\s*["'][^"'\r\n]+["']'''),
    'store-password-command': re.compile(r'''(?i)-storepass\s+[^\s"']+'''),
    'private-key-header': re.compile(r'-----BEGIN (?:RSA |EC |OPENSSH |DSA |ENCRYPTED )?PRIVATE KEY-----'),
    'credential-in-url': re.compile(r'''(?i)\b(?:https?|ftp|sftp)://[^/\s:"']+:[^/@\s"']+@'''),
    'inline-jdbc-password': re.compile(r'''(?i)jdbc:[^\r\n"']*(?:password|pwd)=[^;\s"']+'''),
    'jdbc-literal-credentials': re.compile(
        r'''(?is)\bDriverManager\s*\.\s*getConnection\s*\(.{0,300}?,\s*"[^"\r\n]+"\s*,\s*"[^"\r\n]+"\s*\)'''),
    'xml-credential-element': re.compile(
        r'(?i)<(?P<name>' + NAME + r')\b[^>]*>\s*[^<\s][^<\r\n]*</(?P=name)\s*>'),
    'xml-credential-property': re.compile(
        r'''(?is)<property\b(?=[^>]{0,500}\bname\s*=\s*(["'])''' + NAME +
        r'''\1)(?=[^>]{0,500}\bvalue\s*=\s*(["'])[^"'\r\n]+\2)[^>]*>'''),
}
CONFIG_VALUE = re.compile(r'(?im)^[ \t]*' + NAME + r'[ \t]*[=:][ \t]*([^\r\n]+)')
REFERENCE = re.compile(r'^(?:\$\{[^}]+\}|\$env:[a-z0-9_]+|%[a-z0-9_]+%)$', re.I)

# Reviewed installer UI labels, not authentication values. Match only these
# exact Common catalog paths, line numbers and raw line SHA-256 fingerprints.
# New/changed lines and other credential rules are never exempted. Ignore only
# CRLF versus LF differences so the review survives Git checkout normalization.
UI_LABEL_DIRECTORY = 'Sources/Common/src/com/steema/teechart/languages'
REVIEWED_UI_LABELS = {
    'languages.properties': (1502, '03dfe83e61bc872d61f085b93c8c175015aeed9f3a2ab0716a415675ef4314ac'),
    'languages_ca.properties': (1131, '53da0c2ce9f4f59e1c8aa4a7ff727ed1233bbda95a221ebf438688a822b65116'),
    'languages_de.properties': (1816, '03dfe83e61bc872d61f085b93c8c175015aeed9f3a2ab0716a415675ef4314ac'),
    'languages_en.properties': (1832, '03dfe83e61bc872d61f085b93c8c175015aeed9f3a2ab0716a415675ef4314ac'),
    'languages_es.properties': (1816, '3847dcee5d8d4221b93ccdd768c463ed26f6d4dd8109bc62c594d5afe692113c'),
    'languages_fr.properties': (1816, '03dfe83e61bc872d61f085b93c8c175015aeed9f3a2ab0716a415675ef4314ac'),
    'languages_id.properties': (1815, '03dfe83e61bc872d61f085b93c8c175015aeed9f3a2ab0716a415675ef4314ac'),
    'languages_it.properties': (1816, '03dfe83e61bc872d61f085b93c8c175015aeed9f3a2ab0716a415675ef4314ac'),
    'languages_ja_JP.properties': (1544, '03dfe83e61bc872d61f085b93c8c175015aeed9f3a2ab0716a415675ef4314ac'),
    'languages_nl.properties': (2548, '03dfe83e61bc872d61f085b93c8c175015aeed9f3a2ab0716a415675ef4314ac'),
    'languages_no.properties': (1815, '03dfe83e61bc872d61f085b93c8c175015aeed9f3a2ab0716a415675ef4314ac'),
    'languages_pt.properties': (1815, '03dfe83e61bc872d61f085b93c8c175015aeed9f3a2ab0716a415675ef4314ac'),
    'languages_ru.properties': (1815, '03dfe83e61bc872d61f085b93c8c175015aeed9f3a2ab0716a415675ef4314ac'),
    'languages_sk.properties': (1815, '03dfe83e61bc872d61f085b93c8c175015aeed9f3a2ab0716a415675ef4314ac'),
    'languages_sl.properties': (1816, '03dfe83e61bc872d61f085b93c8c175015aeed9f3a2ab0716a415675ef4314ac'),
    'languages_sv.properties': (1815, '03dfe83e61bc872d61f085b93c8c175015aeed9f3a2ab0716a415675ef4314ac'),
    'languages_tr.properties': (1816, '03dfe83e61bc872d61f085b93c8c175015aeed9f3a2ab0716a415675ef4314ac'),
    'languages_uk.properties': (1816, '03dfe83e61bc872d61f085b93c8c175015aeed9f3a2ab0716a415675ef4314ac'),
    'languages_zh_CN.properties': (1815, '03dfe83e61bc872d61f085b93c8c175015aeed9f3a2ab0716a415675ef4314ac'),
    'languages_zh_HK.properties': (1815, '03dfe83e61bc872d61f085b93c8c175015aeed9f3a2ab0716a415675ef4314ac'),
}


def is_reviewed_ui_label(relative, data, rule, line):
    directory, _, name = relative.rpartition('/')
    if rule != 'literal-config-credential' or directory != UI_LABEL_DIRECTORY:
        return False
    reviewed = REVIEWED_UI_LABELS.get(name)
    if reviewed is None or line != reviewed[0]:
        return False
    lines = data.split(b'\n')
    return (0 < line <= len(lines)
            and hashlib.sha256(lines[line - 1].rstrip(b'\r')).hexdigest() == reviewed[1])


def decode_text(data):
    if data.startswith((b'\xff\xfe\x00\x00', b'\x00\x00\xfe\xff')):
        return data.decode('utf-32', errors='replace')
    if data.startswith((b'\xff\xfe', b'\xfe\xff')):
        return data.decode('utf-16', errors='replace')
    if b'\0' in data[:4096]:
        return None
    return data.decode('utf-8-sig', errors='replace')


def secret_locations(data, suffix):
    text = decode_text(data)
    if text is None:
        return []
    views = [text]
    if suffix.lower() in {'.html', '.htm'}:
        decoded = html.unescape(text)
        views += [decoded, re.sub(r'<[^>]*>', ' ', decoded)]
    result = set()
    for view in views:
        for rule, expression in RULES.items():
            for match in expression.finditer(view):
                result.add((rule, view.count('\n', 0, match.start()) + 1))
        if suffix.lower() in {'.properties', '.ini', '.env', '.yml', '.yaml'}:
            for match in CONFIG_VALUE.finditer(view):
                value = match.group(1).split(' #', 1)[0].strip().strip('"\'')
                if value and not REFERENCE.fullmatch(value):
                    result.add(('literal-config-credential', view.count('\n', 0, match.start()) + 1))
    return sorted(result, key=lambda item: (item[1], item[0]))
