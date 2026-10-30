package com.steema.teechart.legend;

import com.steema.teechart.misc.Enum;

/**
 *
 * <p>Title: LegendStyle class.</p>
 *
 * <p>Description: Describes the possible values of Legend.LegendStyle.</p>
 *
 * <p>Copyright (c) 2005-2019 by Steema Software SL. All Rights Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 *
 */
final public class LegendStyle extends Enum {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
     * Legend draws Series Titles if there's more than one Series in the chart.
     */
    public static final LegendStyle AUTO = new LegendStyle(0);
    /**
     * Legend draws the Series Titles even if there's only one Series in the
     * chart.
     */
    public static final LegendStyle SERIES = new LegendStyle(1);
    /**
     * Legend draws the first Active Series' values.
     */
    public static final LegendStyle VALUES = new LegendStyle(2);
    /**
     * Legend draws the Last Value of each Active Series (similar to Series).
     */
    public static final LegendStyle LASTVALUES = new LegendStyle(3);
    /*
    * Legend draws a number of the Palette entries defined by Legend.MaxNumRows.
    */
    public static final LegendStyle PALETTE = new LegendStyle(4);

    private LegendStyle(int value) {
        super(value);
    }

    static public LegendStyle fromValue(int value) {
        switch (value) {
        case 0:
            return AUTO;
        case 1:
            return SERIES;
        case 2:
            return VALUES;
        case 3:
            return LASTVALUES;
        case 4:
            return PALETTE;
        default:
            return AUTO;
        }
    }
}

