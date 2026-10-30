package com.steema.teechart.tools;

import com.steema.teechart.misc.Enum;

/**
 *
 * <p>Title: PieToolStyle class</p>
 *
 * <p>Description: Describes the possible values of the PieTool.Style
 * method.</p>
 *
 * <p>Copyright (c) 2005-2019 by Steema Software SL. All Rights Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 *
 * @see com.steema.teechart.tools.PieTool#getStyle
 */
final public class PieToolStyle extends Enum {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
     * Draws a border around the focused pie slice.
     */
    public static final PieToolStyle FOCUS = new PieToolStyle(0);
    /**
     * Explodes and un-explodes the focused slice.
     */
    public static final PieToolStyle EXPLODE = new PieToolStyle(1);

    private PieToolStyle(int value) {
        super(value);
    }

}

