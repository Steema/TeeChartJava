package com.steema.teechart.tools;

import com.steema.teechart.misc.Enum;

/**
 *
 * <p>Title: CursorToolStyle class</p>
 *
 * <p>Description: Describes the possible values of the CursorTool.Style
 * method.</p>
 *
 * <p>Copyright (c) 2005-2019 by Steema Software SL. All Rights Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 *
 *
 * @see com.steema.teechart.tools.CursorTool#getStyle
 */
final public class CursorToolStyle extends Enum {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
     * Shows a horizontal line on top of the chart's series.
     */
    public static final CursorToolStyle HORIZONTAL = new CursorToolStyle(0);
    /**
     * Shows a vertical line on top of the chart's series.
     */
    public static final CursorToolStyle VERTICAL = new CursorToolStyle(1);
    /**
     * Shows both a horizontal line and a vertical line on top of the chart's
     * series.
     */
    public static final CursorToolStyle BOTH = new CursorToolStyle(2);

    private CursorToolStyle(int value) {
        super(value);
    }
};

