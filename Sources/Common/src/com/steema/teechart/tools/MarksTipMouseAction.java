package com.steema.teechart.tools;

import com.steema.teechart.misc.Enum;

/**
 *
 * <p>Title: MarksTipMouseAction class</p>
 *
 * <p>Description: Describes the possible values of the MarksTip.MouseAction
 * method.</p>
 *
 * <p>Copyright (c) 2005-2019 by Steema Software SL. All Rights Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 *
 * @see com.steema.teechart.tools.MarksTip#getMouseAction
 */
final public class MarksTipMouseAction extends Enum {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
     * Tips will be displayed when moving the mouse over a series point.
     */
    public static final MarksTipMouseAction MOVE = new MarksTipMouseAction(0);
    /**
     * Tips will be displayed when clicking a series point.
     */
    public static final MarksTipMouseAction CLICK = new MarksTipMouseAction(1);

    private MarksTipMouseAction(int value) {
        super(value);
    }
}


