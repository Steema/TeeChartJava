package com.steema.teechart.legend;

import com.steema.teechart.misc.Enum;

/**
 *
 * <p>Title: LegendSymbolPosition class.</p>
 *
 * <p>Description: Describes the position of the Legend's color rectangles.</p>
 *
 * <p>Copyright (c) 2005-2019 by Steema Software SL. All Rights
 * Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 *
 */
final public class LegendSymbolPosition extends Enum {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
     * Defines the left positioning of the Legend's color rectangles.
     */
    public static final LegendSymbolPosition LEFT = new LegendSymbolPosition();

    /**
     * Defines the right positioning of the Legend's color rectangles.
     */
    public static final LegendSymbolPosition RIGHT = new LegendSymbolPosition();

    private LegendSymbolPosition() {
        super();
    }
}

