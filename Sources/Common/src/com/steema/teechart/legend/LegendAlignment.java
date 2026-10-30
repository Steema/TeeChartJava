package com.steema.teechart.legend;

import com.steema.teechart.misc.Enum;

/**
 *
 * <p>Title: Legend.Alignment class</p>
 *
 * <p>Description: Describes the possible values of Legend.Alignment</p>
 *
 * <p>Copyright (c) 2005-2019 by Steema Software SL. All Rights
 * Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 *
 */
final public class LegendAlignment extends Enum {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
     * Aligns the legend to the left of the TChart.
     */
    public static final LegendAlignment LEFT = new LegendAlignment(0);

    /**
     * Aligns the legend to the right of the TChart.
     */
    public static final LegendAlignment RIGHT = new LegendAlignment(1);

    /**
     * Aligns the legend to the top of the TChart.
     */
    public static final LegendAlignment TOP = new LegendAlignment(2);

    /**
     * Aligns the legend to the bottom of the TChart.
     */
    public static final LegendAlignment BOTTOM = new LegendAlignment(3);

    private LegendAlignment(int value) {
        super(value);
    }

    static public LegendAlignment fromValue(int value) {
        switch (value) {
        case 0: return LegendAlignment.LEFT;
        case 1: return LegendAlignment.RIGHT;
        case 2: return LegendAlignment.TOP;
        default: return LegendAlignment.BOTTOM;
        }
    }
}

