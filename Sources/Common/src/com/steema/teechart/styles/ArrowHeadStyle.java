package com.steema.teechart.styles;

import com.steema.teechart.misc.Enum;

/**
 *
 * <p>Title: ArrowHeadStyle class</p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright (c) 2005-2019 by Steema Software SL. All Rights Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 */
final public class ArrowHeadStyle extends Enum {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
     * Do not display an arrow head.
     */
    public static final ArrowHeadStyle NONE = new ArrowHeadStyle(0);

    /**
     * Display an arrow head made with two lines.
     */
    public static final ArrowHeadStyle LINE = new ArrowHeadStyle(1);

    /**
     * Display a filled arrow head.
     */
    public static final ArrowHeadStyle SOLID = new ArrowHeadStyle(2);

    private ArrowHeadStyle(int value) {
        super(value);
    }

    static public ArrowHeadStyle fromValue(int value) {
        switch (value) {
        case 0:
            return NONE;
        case 1:
            return LINE;
        default:
            return SOLID;
        }
    }
}

