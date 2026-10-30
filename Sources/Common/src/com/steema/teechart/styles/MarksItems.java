package com.steema.teechart.styles;

import java.util.ArrayList;

import com.steema.teechart.TextShape;
import com.steema.teechart.drawing.Color;

/**
 *
 * <p>Title: MarksItems class</p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright (c) 2005-2019 by Steema Software SL. All Rights Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 *
 */
public class MarksItems extends ArrayList {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected TextShape iMarks;

    // Will be used when saving the items for design time
    protected boolean iLoadingCustom = false;

    public MarksItems(SeriesMarks s) {
        super();
        iMarks = s;
    }

    /**
     * Returns the formatting object for the Index'th mark.
     *
     * @param index int
     * @return MarksItem
     */
    public MarksItem getItem(int index) {

        while (index >= size()) {
            add(null);
        }

        if (super.get(index) == null) {
            MarksItem tmp = new MarksItem(iMarks.chart);

            tmp.getShadow().setSize(1);
            tmp.getShadow().setColor(Color.GRAY);

            super.set(index, tmp);
        }

        return (MarksItem)super.get(index);
    }

    public void clear() {
        super.clear();
        iMarks.invalidate();
    }
}

