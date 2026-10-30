package com.steema.teechart.styles;

import com.steema.teechart.misc.Enum;

/**
 *
 * <p>Title: ErrorWidthUnit class</p>
 *
 * <p>Description: Describes the possible values of
 * CustomError.ErrorWidthUnit.</p>
 *
 * <p>Copyright (c)
 *
 *
 *
 *
 *
 *
 *
 * 2005-2013 by Steema Software SL. All Rights
 * Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 */
final public class ErrorWidthUnit extends Enum {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
     * Defines the error width units as a percentage of the chart size.
     */
    public static final ErrorWidthUnit PERCENT = new ErrorWidthUnit(0);
    /**
     * Defines the error width units in pixels.
     */
    public static final ErrorWidthUnit PIXELS = new ErrorWidthUnit(1);

    private ErrorWidthUnit(int value) {
        super(value);
    }
}

