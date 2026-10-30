package com.steema.teechart;

import com.steema.teechart.misc.Enum;

/**
 *
 * <p>Title: ZoomDirections class</p>
 *
 * <p>Description: Describes the possible values of Zoom.Direction method.</p>
 *
 * <p>Copyright (c) 2005-2019 by Steema Software SL. All Rights
 * Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 *
 */
final public class ZoomDirections extends Enum {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
     * Allows only Horizontal Zooming.
     */
    public static final ZoomDirections HORIZONTAL = new ZoomDirections(0);
    /**
     * Allows only Vertical Zooming.
     */
    public static final ZoomDirections VERTICAL = new ZoomDirections(1);
    /**
     * Allows both Horizontal and Vertical Zooming.
     */
    public static final ZoomDirections BOTH = new ZoomDirections(2);

    private ZoomDirections(int value) {
        super(value);
    }
}

