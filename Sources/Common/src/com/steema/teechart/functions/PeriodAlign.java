package com.steema.teechart.functions;

import com.steema.teechart.misc.Enum;

/**
 *
 * <p>Title: PeriodAlign class</p>
 *
 * <p>Description: Descibes how a function is aligned with respect to the
 * source series.</p>
 *
 * <p>Copyright (c) 2005-2019 by Steema Software SL. All Rights
 * Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 */
final public class PeriodAlign extends Enum {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
     * Aligns the function with the first point of the function period.
     */
    public static final PeriodAlign FIRST = new PeriodAlign(0);
    /**
     * Aligns the function with the centre point of the function period.
     */
    public static final PeriodAlign CENTER = new PeriodAlign(1);
    /**
     * Aligns the function with the last point of the function period.
     */
    public static final PeriodAlign LAST = new PeriodAlign(2);

    private PeriodAlign(int value) {
        super(value);
    }
}

