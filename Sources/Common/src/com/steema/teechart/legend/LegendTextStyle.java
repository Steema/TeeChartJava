package com.steema.teechart.legend;

import com.steema.teechart.misc.Enum;

/**
 *
 * <p>Title: LegendTextStyle class.</p>
 *
 * <p>Description: Describes the possible values of the Legend.TextStyle</p>
 *
 * <p>Copyright (c) 2005-2019 by Steema Software SL. All Rights
 * Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 */
final public class LegendTextStyle extends Enum {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
     * Legend Text is defined as the series point labels.
     */
    public static final LegendTextStyle PLAIN = new LegendTextStyle(0);
    /**
     * Legend Text is defined as the series values on the left of the series
     * point labels.
     */
    public static final LegendTextStyle LEFTVALUE = new LegendTextStyle(1);
    /**
     * Legend Text is defined as the series values on the right of the series
     * point labels.
     */
    public static final LegendTextStyle RIGHTVALUE = new LegendTextStyle(2);
    /**
     * Legend Text is defined as the series values as a percentage of the total
     * of series values on the left of the series point labels.
     */
    public static final LegendTextStyle LEFTPERCENT = new LegendTextStyle(3);
    /**
     * Legend Text is defined as the series values as a percentage of the total
     * of series values on the right of the series point labels.
     */
    public static final LegendTextStyle RIGHTPERCENT = new LegendTextStyle(4);
    /**
     * Legend Text is defined as the xvalues of the series.
     */
    public static final LegendTextStyle XVALUE = new LegendTextStyle(5);
    /**
     * Legend Text is defined as the series value.
     */
    public static final LegendTextStyle VALUE = new LegendTextStyle(6);
    /**
     * Legend Text is defined as the series values as a percentage of the total
     * of series values
     */
    public static final LegendTextStyle PERCENT = new LegendTextStyle(7);
    /**
     * Legend Text is defined as xvalues of the series and the series values.
     */
    public static final LegendTextStyle XANDVALUE = new LegendTextStyle(8);
    /**
     * Legend Text is defined as xvalues of the series and the series values as
     * a percentage of the total of series values.
     */
    public static final LegendTextStyle XANDPERCENT = new LegendTextStyle(9);

/*    private LegendTextStyle() {
        super();
    }*/

    private LegendTextStyle(int value) {
        super(value);
    }

    static public LegendTextStyle fromValue(int value) {
        switch (value) {
        case 0:
            return PLAIN;
        case 1:
            return LEFTVALUE;
        case 2:
            return RIGHTVALUE;
        case 3:
            return LEFTPERCENT;
        case 4:
            return RIGHTPERCENT;
        case 5:
            return XVALUE;
        case 6:
            return VALUE;
        case 7:
            return PERCENT;
        case 8:
            return XANDVALUE;
        case 9:
            return XANDPERCENT;
        default:
            return LEFTVALUE;
        }
    }
}

