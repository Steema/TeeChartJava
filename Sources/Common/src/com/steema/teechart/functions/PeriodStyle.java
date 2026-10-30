package com.steema.teechart.functions;

import com.steema.teechart.misc.Enum;

/**
 *
 * <p>Title: PeriodStyle class</p>
 *
 * <p>Description: Descibes function period as a number of points or as a
 * range of points.</p>
 *
 * <p>Copyright (c) 2005-2019 by Steema Software SL. All Rights
 * Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 */
final public class PeriodStyle extends Enum {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
     * Defines function period as a number of points.
     */
    public static final PeriodStyle NUMPOINTS = new PeriodStyle(0);
    /**
     * Defines function period as a range of points.
     */
    public static final PeriodStyle RANGE = new PeriodStyle(1);

    private PeriodStyle(int value) {
        super(value);
    }
};

