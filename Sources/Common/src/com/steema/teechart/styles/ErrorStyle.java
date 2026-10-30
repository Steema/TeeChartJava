package com.steema.teechart.styles;

import com.steema.teechart.misc.Enum;

/**
 *
 * <p>Title: ErrorStyle class</p>
 *
 * <p>Description: Describes the possible values of the CustomError.ErrorStyle.</p>
 *
 * <p>Copyright (c) 2005-2019 by Steema Software SL. All Rights
 * Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 *
 *
 * @see com.steema.teechart.styles.CustomError#getErrorStyle
 */
final public class ErrorStyle extends Enum {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
     * Crossbar of 'T' error displacement indicator displayed on the left.
     */
    public static final ErrorStyle LEFT = new ErrorStyle(0);
    /**
     * Crossbar of 'T' error displacement indicator displayed on the right.
     */
    public static final ErrorStyle RIGHT = new ErrorStyle(1);
    /**
     * Crossbar of 'T' error displacement indicator displayed on the left and
     * right.
     */
    public static final ErrorStyle LEFTRIGHT = new ErrorStyle(2);
    /**
     * Crossbar of 'T' error displacement indicator displayed on the top.
     */
    public static final ErrorStyle TOP = new ErrorStyle(3);
    /**
     * Crossbar of 'T' error displacement indicator displayed on the bottom.
     */
    public static final ErrorStyle BOTTOM = new ErrorStyle(4);
    /**
     * Crossbar of 'T' error displacement indicator displayed on the top and
     * bottom.
     */
    public static final ErrorStyle TOPBOTTOM = new ErrorStyle(5);

    private ErrorStyle(int value) {
        super(value);
    }
}

