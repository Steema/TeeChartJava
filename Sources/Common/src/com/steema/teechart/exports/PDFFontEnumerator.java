package com.steema.teechart.exports;

import com.steema.teechart.exports.PDFFonts;
import com.steema.teechart.drawing.ChartFont;
import java.util.Iterator;

/**
 *
 * <p>Title: PDFFontEnumerator class</p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright (c) 2005-2019 by Steema Software SL. All Rights
 * Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 *
 */
public class PDFFontEnumerator implements Iterator {

    private PDFFonts fonts;
    private String m_fontname;
    private int m_index;

    private int position = -1;

    public void reset() {
        position = -1;
    }

    public Object getCurrent() {
        return fonts.m_fonts.get(position);
    }

    public boolean hasNext() {
        return position < fonts.m_fonts.size();
    }

    public Object next() {
        if (hasNext()) {
            position++;
            return getCurrent();
        } else {
            return null;
        }
    }

    /** @todo throw UnsupportedOperationException */
    public void remove() {
    }

    public PDFFontEnumerator(PDFFonts fonts, ChartFont f) {
        this.fonts = fonts;
        m_fontname = getPDFFontName(f);
        m_index = fonts.getCount();
    }

    public String getPDFFontName() {
        return m_fontname;
    }

    public String getDictFontName() {
        return "/F" + Integer.toString(m_index);
    }

    /**
     * Constructs PDF readable name (check for syntax)
     *
     * @param font ChartFont
     * @return String
     */
    static String getPDFFontName(ChartFont font) {
        String res = font.getName();
        if ((font.getBold()) && (!font.getItalic())) {
            res += ",Bold";
        } else if ((font.getItalic()) && (!font.getBold())) {
            res += ",Italic";
        } else if ((font.getBold()) && (font.getItalic())) {
            res += ",BoldItalic";
        }
        res = res.replaceAll(" ", "");
        return res;
    }
}

