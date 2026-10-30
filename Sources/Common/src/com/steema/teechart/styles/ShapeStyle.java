package com.steema.teechart.styles;

import com.steema.teechart.misc.Enum;

/**
 *
 * <p>Title: ShapeStyle class</p>
 *
 * <p>Description: Describes the possible values of the Shape.Style method.</p>
 *
 * <p>Copyright (c) 2005-2019 by Steema Software SL. All Rights Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 *
 *
 * @see com.steema.teechart.styles.Shape#getStyle
 */
final public class ShapeStyle extends Enum {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
     * The shape is a rectangle.
     */
    public static final ShapeStyle RECTANGLE = new ShapeStyle(0);
    /**
     * The shape is a circle.
     */
    public static final ShapeStyle CIRCLE = new ShapeStyle(1);
    /**
     * The shape is a vertical line.
     */
    public static final ShapeStyle VERTLINE = new ShapeStyle(2);
    /**
     * The shape is a horizontal line.
     */
    public static final ShapeStyle HORIZLINE = new ShapeStyle(3);
    /**
     * The shape is a triangle.
     */
    public static final ShapeStyle TRIANGLE = new ShapeStyle(4);
    /**
     * The shape is an inverted triangle.
     */
    public static final ShapeStyle INVERTTRIANGLE = new ShapeStyle(5);
    /**
     * The shape is a line.
     */
    public static final ShapeStyle LINE = new ShapeStyle(6);
    /**
     * The shape is a diamond.
     */
    public static final ShapeStyle DIAMOND = new ShapeStyle(7);
    /**
     * The shape is a cube.
     */
    public static final ShapeStyle CUBE = new ShapeStyle(8);
    /**
     * The shape is a cross.
     */
    public static final ShapeStyle CROSS = new ShapeStyle(9);
    /**
     * The shape is a diagonal cross.
     */
    public static final ShapeStyle DIAGCROSS = new ShapeStyle(10);
    /**
     * The shape is a star.
     */
    public static final ShapeStyle STAR = new ShapeStyle(11);
    /**
     * The shape is a pyramid.
     */
    public static final ShapeStyle PYRAMID = new ShapeStyle(12);
    /**
     * The shape is an inverted pyramid.
     */
    public static final ShapeStyle INVERTPYRAMID = new ShapeStyle(13);

    private ShapeStyle(int value) {
        super(value);
    }
}

