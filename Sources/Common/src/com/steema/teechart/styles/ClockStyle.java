package com.steema.teechart.styles;

import com.steema.teechart.misc.Enum;

/**
 *
 * <p>Title: ClockStyle class</p>
 *
 * <p>Description: Describes the possible values of Clock.Style.</p>
 *
 * <p>Copyright (c) 2005-2019 by Steema Software SL. All Rights Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 */
final public class ClockStyle extends Enum {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
     * Defines clock figures as Decimal numerals.
     */
    public static final ClockStyle DECIMAL = new ClockStyle();

    /**
     * Defines clock figures as Roman numerals.
     */
    public static final ClockStyle ROMAN = new ClockStyle();

    private ClockStyle() {
        super();
    }
}

