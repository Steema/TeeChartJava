package com.steema.teechart.misc;

/**
 * <p>Title: TeeChart for Java</p>
 *
 * <p>Description: TeeChart for Java</p>
 *
 * <p>Copyright: Copyright (c) 2004-2019 by Steema Software SL. All Rights
 * Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 *
 * @author not attributable
 * @version 1.1
 */
public class StringBuilder {

    private String text = "";

    public StringBuilder() {}

    public StringBuilder(int capacity) {}

    public StringBuilder(String value) {
        text = value;
    }

    public void append(String value) {
        text += value;
    }

    public String toString() {
        return text;
    }

    public int length() {
        return text.length();
    }

    public void delete(int start, int end) {
        text = text.substring(0, start) +
               text.substring(start + end, text.length()-(end-start));
    }
}
