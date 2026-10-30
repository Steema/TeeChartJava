package com.steema.teechart.styles;

import com.steema.teechart.misc.Enum;

/**
 *
 * <p>Title: CustomStack class</p>
 *
 * <p>Description: Describes the possible values of CustomPoint.Stacked</p>
 *
 * <p>Copyright (c) 2005-2019 by Steema Software SL. All Rights
 * Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 *
 * <p>Example:
 * <pre><font face="Courier" size="4">
 * lineSeries.setStacked( CustomStack.Overlap );
 * </font></pre></p>
 *
 */
final public class CustomStack extends Enum {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
     * Series will be drawn one behind the other using different "z" depths.
     */
    public static final CustomStack NONE = new CustomStack(0);
    /**
     * Series will be drawn one over the other using the same "z" depth.
     */
    public static final CustomStack OVERLAP = new CustomStack(1);
    /**
     * Draws each series on top of the previous one by summing the values.
     */
    public static final CustomStack STACK = new CustomStack(2);
    /**
     * Adjusts each individual series to a common 0..100 axis scale.
     */
    public static final CustomStack STACK100 = new CustomStack(3);

    private CustomStack(int value) {
        super(value);
    }

    static public CustomStack fromInt(int value) {
        switch (value) {
        case 0:
            return NONE;
        case 1:
            return OVERLAP;
        case 2:
            return STACK;
        default:
            return STACK100;
        }
    }
}

