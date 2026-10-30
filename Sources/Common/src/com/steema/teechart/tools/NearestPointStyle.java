package com.steema.teechart.tools;

import com.steema.teechart.misc.Enum;

/**
 *
 * <p>Title: NearestPointStyle class</p>
 *
 * <p>Description: Describes the possible values of the NearestPoint.Style
 * method.</p>
 *
 * <p>Copyright (c) 2005-2019 by Steema Software SL. All Rights Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 *
 *
 * @see com.steema.teechart.tools.NearestPoint#getStyle
 */
final public class NearestPointStyle extends Enum {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
     * No shape is drawn.
     */
    public static final NearestPointStyle NONE = new NearestPointStyle(0);
    /**
     * Shape is a circle.
     */
    public static final NearestPointStyle CIRCLE = new NearestPointStyle(1);
    /**
     * Shape is a rectangle.
     */
    public static final NearestPointStyle RECTANGLE = new NearestPointStyle(2);
    /**
     * Shape is a diamond.
     */
    public static final NearestPointStyle DIAMOND = new NearestPointStyle(3);

    private NearestPointStyle(int value) {
        super(value);
    }
};

