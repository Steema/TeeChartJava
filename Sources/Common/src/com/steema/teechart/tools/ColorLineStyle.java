package com.steema.teechart.tools;

import com.steema.teechart.misc.Enum;

/**
 *
 * <p>Title: ColorLineStyle class</p>
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
final public class ColorLineStyle extends Enum {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static final ColorLineStyle CUSTOM = new ColorLineStyle(0);

    public static final ColorLineStyle MAXIMUM = new ColorLineStyle(1);

    public static final ColorLineStyle CENTER = new ColorLineStyle(2);

    public static final ColorLineStyle MINIMUM = new ColorLineStyle(3);

    private ColorLineStyle(int value) {
        super(value);
    }
};

