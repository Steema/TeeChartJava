/*
 * Copyright 2026 Steema Software SL
 * SPDX-License-Identifier: Apache-2.0
 */
package features.utils;

import java.awt.Color;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;

/**
 * Demo source viewer with a full-document lexical pass after each edit. Java
 * comments and quoted literals are recognized; the legacy SQL-style double-dash
 * comments and mathematical function names remain supported for existing demos.
 */
public class JavaSyntaxDocument extends DefaultStyledDocument {
    private static final long serialVersionUID = 1L;

    public final String[] JavaKeywords = (
            "ABSTRACT CONTINUE FOR NEW SWITCH ASSERT DEFAULT GOTO PACKAGE SYNCHRONIZED " +
            "BOOLEAN DO IF PRIVATE THIS BREAK DOUBLE IMPLEMENTS PROTECTED THROW " +
            "BYTE ELSE IMPORT PUBLIC THROWS CASE ENUM INSTANCEOF RETURN TRANSIENT " +
            "CATCH EXTENDS INT SHORT TRY CHAR FINAL INTERFACE STATIC VOID CLASS " +
            "FINALLY LONG STRICTFP VOLATILE CONST FLOAT NATIVE SUPER WHILE NULL").split(" ");
    public final String[] JavaFunctions = (
            "ABS ACOS ATAN ATAN2 CEIL COS COSH EXP FLOOR LN LOG MOD POWER ROUND SIGN " +
            "SIN SINH SQRT TAN TANH").split(" ");

    private final Set<String> keywords = new HashSet<String>(Arrays.asList(JavaKeywords));
    private final Set<String> functions = new HashSet<String>(Arrays.asList(JavaFunctions));
    private final AttributeSet plain = new SimpleAttributeSet();
    private final AttributeSet keyword = style(new Color(0, 0, 153), true);
    private final AttributeSet function = style(new Color(100, 0, 200), false);
    private final AttributeSet string = style(new Color(153, 0, 107), false);
    private final AttributeSet character = style(Color.gray, false);
    private final AttributeSet blockComment = style(new Color(115, 115, 115), false);
    private final AttributeSet lineComment = style(new Color(0, 111, 0), false);

    public JavaSyntaxDocument() {
    }

    public void insertString(int offset, String text, AttributeSet attributes) throws BadLocationException {
        writeLock();
        try {
            super.insertString(offset, text, plain);
            recolor();
        } finally {
            writeUnlock();
        }
    }

    public void remove(int offset, int length) throws BadLocationException {
        writeLock();
        try {
            super.remove(offset, length);
            recolor();
        } finally {
            writeUnlock();
        }
    }

    private static AttributeSet style(Color color, boolean bold) {
        SimpleAttributeSet attributes = new SimpleAttributeSet();
        StyleConstants.setForeground(attributes, color);
        StyleConstants.setBold(attributes, bold);
        return attributes;
    }

    private void recolor() throws BadLocationException {
        String text = getText(0, getLength());
        setCharacterAttributes(0, text.length(), plain, true);
        int cursor = 0;
        while (cursor < text.length()) {
            int start = cursor;
            AttributeSet attributes = null;
            char current = text.charAt(cursor++);
            if (current == '/' && cursor < text.length() && text.charAt(cursor) == '*') {
                int close = text.indexOf("*/", cursor + 1);
                cursor = close < 0 ? text.length() : close + 2;
                attributes = blockComment;
            } else if ((current == '/' || current == '-') && cursor < text.length()
                    && text.charAt(cursor) == current) {
                while (cursor < text.length() && text.charAt(cursor) != '\n' && text.charAt(cursor) != '\r') {
                    cursor++;
                }
                attributes = lineComment;
            } else if (current == '"' || current == '\'') {
                while (cursor < text.length()) {
                    char next = text.charAt(cursor++);
                    if (next == '\\' && cursor < text.length()) {
                        cursor++;
                    } else if (next == current) {
                        break;
                    }
                }
                attributes = current == '"' ? string : character;
            } else if (Character.isJavaIdentifierStart(current)) {
                while (cursor < text.length() && Character.isJavaIdentifierPart(text.charAt(cursor))) {
                    cursor++;
                }
                String word = text.substring(start, cursor).toUpperCase(Locale.ENGLISH);
                attributes = keywords.contains(word) ? keyword : functions.contains(word) ? function : null;
            }
            if (attributes != null) {
                setCharacterAttributes(start, cursor - start, attributes, true);
            }
        }
    }
}
