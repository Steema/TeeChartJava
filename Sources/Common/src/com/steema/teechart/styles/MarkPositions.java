package com.steema.teechart.styles;

import java.util.ArrayList;

/**
 *
 * <p>Title: MarkPositions class</p>
 *
 * <p>Description: Series Marks' Positions.</p>
 *
 * <p>Copyright (c) 2005-2019 by Steema Software SL. All Rights
 * Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 *
 */
public class MarkPositions extends ArrayList {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public MarkPositions() {
        super();
    }

    public SeriesMarksPosition getPosition(int index) {
        return (index < size()) ? (SeriesMarksPosition) get(index) : null;
    }

    public void setPosition(int index, SeriesMarksPosition value) {
        set(index, value);
    }

    public void removeRange(int startIndex, int count) {
        super.removeRange(startIndex, startIndex + count - 1);
    }

    /**
     * Checks for Custom Marks.
     *
     * @return boolean true if Custom exists
     */
    public boolean existCustom() {
        for (int t = 0; t < size(); t++) {
            SeriesMarksPosition m = getPosition(t);
            if ((m != null) && m.custom) {
                return true;
            }
        }
        return false;
    }
}


