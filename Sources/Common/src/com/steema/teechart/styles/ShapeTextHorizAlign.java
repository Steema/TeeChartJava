package com.steema.teechart.styles;

import com.steema.teechart.misc.Enum;

/**
 *
 * <p>Title: ShapeTextHorizAlign class</p>
 *
 * <p>Description: Describes the possible values of the Shape.HorizAlignment
 * method.</p>
 *
 * <p>Copyright (c) 2005-2019 by Steema Software SL. All Rights Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 *
 * @see com.steema.teechart.styles.Shape#getHorizAlignment
 */
final public class ShapeTextHorizAlign extends Enum {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
     * Text is horizontally aligned to the left of the shape.
     */
    public static final ShapeTextHorizAlign LEFT = new ShapeTextHorizAlign(0);
    /**
     * Text is horizontally aligned to the centre of the shape.
     */
    public static final ShapeTextHorizAlign CENTER = new ShapeTextHorizAlign(1);
    /**
     * Text is horizontally aligned to the right of the shape.
     */
    public static final ShapeTextHorizAlign RIGHT = new ShapeTextHorizAlign(2);

    private ShapeTextHorizAlign(int value) {
        super(value);
    }
}

