package com.steema.teechart;

import com.steema.teechart.misc.Enum;

/**
 *
 * <p>Title: TextShapeStyle class</p>
 *
 * <p>Description: Defines the style of text-containing shapes.</p>
 *
 * <p>Copyright (c) 2005-2019 by Steema Software SL. All Rights
 * Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 *
 */
final public class TextShapeStyle extends Enum {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
     * Defines a Rectangular shape.
     */
    public static final TextShapeStyle RECTANGLE = new TextShapeStyle(0);
    /**
     * Defines a Rounded Rectangular shape.
     */
    public static final TextShapeStyle ROUNDRECTANGLE = new TextShapeStyle(1);

    private TextShapeStyle(int value) {
        super(value);
    }
}

