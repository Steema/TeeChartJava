package com.steema.teechart.tools;

import com.steema.teechart.misc.Enum;

/**
 *
 * <p>Title: DragPointStyle class</p>
 *
 * <p>Description: Describes the possible values of the DragPoint.Style
 * method.</p>
 *
 * <p>Copyright (c) 2005-2019 by Steema Software SL. All Rights Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 *
 *
 * @see com.steema.teechart.tools.DragPoint#getStyle
 */
final public class DragPointStyle extends Enum {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
     * Allows user to drag points horizontally only.
     */
    public static final DragPointStyle X = new DragPointStyle(0);
    /**
     * Allows user to drag points vertically only.
     */
    public static final DragPointStyle Y = new DragPointStyle(1);
    /**
     * Allows user to drag points both horizontally and vertically.
     */
    public static final DragPointStyle BOTH = new DragPointStyle(2);

    private DragPointStyle(int value) {
        super(value);
    }
}

