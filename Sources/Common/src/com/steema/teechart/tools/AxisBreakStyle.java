package com.steema.teechart.tools;

import com.steema.teechart.misc.Enum;

/**
 *
 * <p>Title: AxisBreakStyle</p>
 *
 * <p>Description: Sets the style of the AxisBreakTool delimiting lines.</p>
 *
 * <p>Copyright (c) 2005-2019 by Steema Software SL. All Rights Reserved.</p>
 *
 * <p>Company: Steema Software</p>
 *
 *
 * @see com.steema.teechart.tools.AxisBreak#getStyle
 */
public final class AxisBreakStyle extends Enum {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/*
    * Draws the break as small Zig Zag.
    */
    public static final AxisBreakStyle SMALLZIGZAG = new AxisBreakStyle(0);
    /*
    * Draws the break as Zig Zag.
    */
    public static final AxisBreakStyle ZIGZAG = new AxisBreakStyle(1);
    /*
    * Draws the break as Line.
    */
    public static final AxisBreakStyle LINE = new AxisBreakStyle(2);
    /*
    * Draws the break as Transparent.
    */
    public static final AxisBreakStyle NONE = new AxisBreakStyle(3);

    private AxisBreakStyle(int value) {
        super(value);
    }
}
