package com.steema.teechart.styles;

import com.steema.teechart.misc.Enum;

/**
 *
 * <p>Title: ShapeXYStyle class</p>
 *
 * <p>Description: Describes the possible values of the Shape.XYStyle
 * method.</p>
 *
 * <p>Copyright (c) 2005-2019 by Steema Software SL. All Rights Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 *
 *
 * @see com.steema.teechart.styles.Shape#getXYStyle
 */
final public class ShapeXYStyle extends Enum {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
     * Position relative to Chart Panel. 0,0 is Panel Left, Top.
     */
    public static final ShapeXYStyle PIXELS = new ShapeXYStyle(0);
    /**
     * Position in Axis units.
     */
    public static final ShapeXYStyle AXIS = new ShapeXYStyle(1);
    /**
     * Use Left,Top (X0,Y0) to set the Axis origin in Axis units. Right,
     * Bottom sets width and height in Pixels.
     */
    public static final ShapeXYStyle AXISORIGIN = new ShapeXYStyle(2);

    private ShapeXYStyle(int value) {
        super(value);
    }
}

