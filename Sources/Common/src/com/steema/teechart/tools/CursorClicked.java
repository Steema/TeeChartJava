package com.steema.teechart.tools;

import com.steema.teechart.misc.Enum;

/**
 *
 * <p>Title: CursorClicked class</p>
 *
 * <p>Description: Describes the possible return values of the
 * CursorTool.clicked method.</p>
 *
 * <p>Copyright (c) 2005-2019 by Steema Software SL. All Rights Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 *
 * @see com.steema.teechart.tools.CursorTool#clicked
 */
final public class CursorClicked extends Enum {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
     * The Cursor is not on a cursortool.
     */
    public static final CursorClicked NONE = new CursorClicked(0);
    /**
     * The Cursor is on the horizontal line of the cursortool.
     */
    public static final CursorClicked HORIZONTAL = new CursorClicked(1);
    /**
     * The Cursor is on the vertical line of the cursortool.
     */
    public static final CursorClicked VERTICAL = new CursorClicked(2);
    /**
     * The Cursor on the horizontal and vertical line of the cursortool.
     */
    public static final CursorClicked BOTH = new CursorClicked(3);

    private CursorClicked(int value) {
        super(value);
    }
};

