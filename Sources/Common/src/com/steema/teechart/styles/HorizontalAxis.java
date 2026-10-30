package com.steema.teechart.styles;

import com.steema.teechart.misc.Enum;

/**
 *
 * <p>Title: HorizontalAxis class</p>
 *
 * <p>Description: Describes the possible values of Series.HorizAxis.</p>
 *
 * <p>Copyright (c) 2005-2019 by Steema Software SL. All Rights
 * Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 *
 * @see com.steema.teechart.styles.Series#getHorizAxis
 */
final public class HorizontalAxis extends Enum {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
     * Associates the series with the top axis.
     */
    public static final HorizontalAxis TOP = new HorizontalAxis(0);
    /**
     * Associates the series with the bottom axis.
     */
    public static final HorizontalAxis BOTTOM = new HorizontalAxis(1);
    /**
     * Associates the series with both the top and bottom axes.
     */
    public static final HorizontalAxis BOTH = new HorizontalAxis(2);
    /**
     * Associates the series with a custom axis.
     */
    public static final HorizontalAxis CUSTOM = new HorizontalAxis(3);

    private HorizontalAxis(int value) {
        super(value);
    }

    static public HorizontalAxis fromInt(int value) {
        switch (value) {
        case 0:
            return TOP;
        case 1:
            return BOTTOM;
        case 2:
            return BOTH;
        default:
            return CUSTOM;

        }
    }
}

