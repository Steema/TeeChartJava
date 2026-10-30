package com.steema.teechart.styles;

import com.steema.teechart.misc.Enum;

/**
 *
 * <p>Title: VerticalAxis class</p>
 *
 * <p>Description: Describes the possible values of Series.VertAxis method.</p>
 *
 * <p>Copyright (c) 2005-2019 by Steema Software SL. All Rights Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 *
 * @see com.steema.teechart.styles.Series#getVertAxis
 */
final public class VerticalAxis extends Enum {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
     * Associates the series with the left axis.
     */
    public static final VerticalAxis LEFT = new VerticalAxis(0);
    /**
     * Associates the series with the right axis.
     */
    public static final VerticalAxis RIGHT = new VerticalAxis(1);
    /**
     * Associates the series with both the left and right axis.
     */
    public static final VerticalAxis BOTH = new VerticalAxis(2);
    /**
     * Associates the series with a custom axis.
     */
    public static final VerticalAxis CUSTOM = new VerticalAxis(3);

    private VerticalAxis(int value) {
        super(value);
    }

    static public VerticalAxis fromInt(int value) {
        switch (value) {
        case 0:
            return LEFT;
        case 1:
            return RIGHT;
        case 2:
            return BOTH;
        default:
            return CUSTOM;

        }
    }
}

