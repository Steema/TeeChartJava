package com.steema.teechart.functions;

import com.steema.teechart.misc.Enum;

/**
 *
 * <p>Title: TrendStyle class</p>
 *
 * <p>Description: Defines different trend styles.</p>
 *
 * <p>Copyright (c) 2005-2019 by Steema Software SL. All Rights
 * Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 */
final public class TrendStyle extends Enum {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
     * Normal linear fit.
     */
    public static final TrendStyle NORMAL = new TrendStyle(0);
    /**
     * Logarithmic fit.
     */
    public static final TrendStyle LOGARITHMIC = new TrendStyle(1);
    /**
     * Exponential fit.
     */
    public static final TrendStyle EXPONENTIAL = new TrendStyle(2);

    private TrendStyle(int value) {
        super(value);
    }
}

