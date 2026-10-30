package com.steema.teechart.tools;

import com.steema.teechart.misc.Enum;

/**
 *
 * <p>Title: DrawLineStyle class</p>
 *
 * <p>Description: LineStyle for DrawLineItem.Style</p>
 *
 * <p>Copyright (c) 2005-2019 by Steema Software SL. All Rights Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 *
 * @see com.steema.teechart.tools.DrawLineItem#style
 */
final public class DrawLineStyle extends Enum {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
     * Plots a single line between start and end points
     */
    public static final DrawLineStyle LINE = new DrawLineStyle(0);
    /**
     * Plots 2 horizontal lines along top and bottom of the bounding rectangle
     * expressed by start and end points
     */
    public static final DrawLineStyle HORIZPARALLEL = new DrawLineStyle(1);
    /**
     * Plots 2 vertical lines along left and right of the bounding rectangle
     * expressed by start and end points
     */
    public static final DrawLineStyle VERTPARALLEL = new DrawLineStyle(2);

     public static final DrawLineStyle RECTANGLE = new DrawLineStyle(3);

      public static final DrawLineStyle ELLIPSE = new DrawLineStyle(4);

    private DrawLineStyle(int value) {
        super(value);
    }
}

