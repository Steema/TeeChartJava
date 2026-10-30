package com.steema.teechart.styles;

import com.steema.teechart.misc.Enum;

/**
 *
 * <p>Title: ShapeTextVertAlign class</p>
 *
 * <p>Description: Describes the possible values of the Shape.VertAlignment
 * method.</p>
 *
 * <p>Copyright (c) 2005-2019 by Steema Software SL. All Rights Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 *
 * @see com.steema.teechart.styles.Shape#getVertAlignment
 */
final public class ShapeTextVertAlign extends Enum {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
     * Text is vertically aligned to the top of the shape.
     */
    public static final ShapeTextVertAlign TOP = new ShapeTextVertAlign(0);
    /**
     * Text is vertically aligned to the centre of the shape.
     */
    public static final ShapeTextVertAlign CENTER = new ShapeTextVertAlign(1);
    /**
     * Text is vertically aligned to the bottom of the shape.
     */
    public static final ShapeTextVertAlign BOTTOM = new ShapeTextVertAlign(2);

    private ShapeTextVertAlign(int value) {
        super(value);
    }
}

