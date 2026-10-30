package com.steema.teechart.styles;

import java.util.ArrayList;
import com.steema.teechart.drawing.Color;

/**
 *
 * <p>Title: ColorList class</p>
 *
 * <p>Description: The ColorList class is manipulated via Series.Colors</p>
 *
 * <p>Copyright (c) 2005-2019 by Steema Software SL. All Rights
 * Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 *
 */
public final class ColorList extends ArrayList {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ColorList(int capacity) {
        super(capacity);
    }

    public int getCount() {
        return size();
    }

    public Color getColor(int index) {
        return (index < getCount()) ? (Color) get(index) : Color.EMPTY;
    }

    public void setColor(int index, Color value) {
        while (getCount() <= index) {
            add(Color.EMPTY);
        }
        set(index, value);
    }

    public void removeRange(int startIndex, int count) {
        super.removeRange(startIndex, startIndex + count - 1);
    }

    void exchange(int a, int b) {
        Color c = getColor(a);
        setColor(a, getColor(b));
        setColor(b, c);
    }
}

