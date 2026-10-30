package com.steema.teechart.tools;

import com.steema.teechart.misc.Enum;

/**
 *
 * <p>Title: RotateStyle class</p>
 *
 * <p>Description: Describes the possible values of the Rotate.Style method.</p>
 *
 * <p>Copyright (c) 2005-2019 by Steema Software SL. All Rights Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 *
 *
 * @see com.steema.teechart.tools.Rotate#getStyle
 */
final public class RotateStyle extends Enum {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
     * Elevates and Rotates the Chart series.
     */
    public static final RotateStyle ALL = new RotateStyle(0);
    /**
     * Rotates the Chart series.
     */
    public static final RotateStyle ROTATION = new RotateStyle(1);
    /**
     * Elevates the Chart series.
     */
    public static final RotateStyle ELEVATION = new RotateStyle(2);

    private RotateStyle(int value) {
        super(value);
    }
}

