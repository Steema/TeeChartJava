package com.steema.teechart.exports;

import com.steema.teechart.drawing.ChartFont;
import java.util.ArrayList;
import java.util.Iterator;

/**
 *
 * <p>Title: PDFFonts class</p>
 *
 * <p>Description: Stores all used fonts</p>
 *
 * <p>Copyright (c) 2005-2019 by Steema Software SL. All Rights
 * Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 *
 */
public class PDFFonts {

    ArrayList m_fonts = new ArrayList();

    public int getCount() {
        return m_fonts.size();
    }

    public PDFFontEnumerator get(int index) {
        return (PDFFontEnumerator) m_fonts.get(index);
    }

    /**
     * Add new font to the list
     *
     * @param font ChartFont
     * @return int
     */
    public int add(ChartFont font) {
        PDFFontEnumerator m_font = new PDFFontEnumerator(this, font);
        m_fonts.add(m_font);
        return m_fonts.size() - 1;
    }

    /**
     * Select the font in font list. Returns the index of selected font,
     * starting from 0<br>
     *
     * @param font ChartFont
     * @return int
     */
    public int findFont(ChartFont font) {
        String font_name = PDFFontEnumerator.getPDFFontName(font);

        for (int i = 0; i < getCount(); i++) {
            if (PDFFontEnumerator.getPDFFontName(((ChartFont) m_fonts.get(i))).
                equals(font_name)) {
                return i;
            }
        }
        return add(font);
    }

    public Iterator getEnumerator() {
        return m_fonts.iterator();
    }
}

