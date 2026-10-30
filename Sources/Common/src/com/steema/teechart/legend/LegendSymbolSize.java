package com.steema.teechart.legend;

import com.steema.teechart.misc.Enum;

/**
 *
 * <p>Title: LegendSymbolSize class.</p>
 *
 * <p>Description: Describes the units in which to define the size of the
 * Legend's color rectangles.</p>
 *
 * <p>Copyright (c) 2005-2019 by Steema Software SL. All Rights Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 *
 */
final public class LegendSymbolSize extends Enum {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
     * Defines the units as a Percentage of the Legend width.
     */
    public static final LegendSymbolSize PERCENT = new LegendSymbolSize();

    /**
     * Defines the units as Pixels.
     */
    public static final LegendSymbolSize PIXELS = new LegendSymbolSize();

    private LegendSymbolSize() {
        super();
    }
}

