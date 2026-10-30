package com.steema.teechart.styles;

import com.steema.teechart.misc.Enum;

/**
 *
 * <p>Title: PointerStyle class</p>
 *
 * <p>Description: Describes the pointer style of a series pointer.</p>
 *
 * <p>Copyright (c) 2005-2019 by Steema Software SL. All Rights Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 */
public class PointerStyle extends Enum {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static int upperBound=0;

    /**
     * Defines the pointer as a rectangle.
     */
    public static final PointerStyle RECTANGLE = new PointerStyle(0);
    /**
     * Defines the pointer as a circle.
     */
    public static final PointerStyle CIRCLE = new PointerStyle(1);
    /**
     * Defines the pointer as a triangle.
     */
    public static final PointerStyle TRIANGLE = new PointerStyle(2);
    /**
     * Defines the pointer as a down pointing triangle.
     */
    public static final PointerStyle DOWNTRIANGLE = new PointerStyle(3);
    /**
     * Defines the pointer as a cross.
     */
    public static final PointerStyle CROSS = new PointerStyle(4);
    /**
     * Defines the pointer as a diagonal cross.
     */
    public static final PointerStyle DIAGCROSS = new PointerStyle(5);
    /**
     * Defines the pointer as a star.
     */
    public static final PointerStyle STAR = new PointerStyle(6);
    /**
     * Defines the pointer as a diamond.
     */
    public static final PointerStyle DIAMOND = new PointerStyle(7);
    /**
     * Defines the pointer as a small dot.
     */
    public static final PointerStyle SMALLDOT = new PointerStyle(8);
    /**
     * Defines the pointer as no shape.
     */
    public static final PointerStyle NOTHING = new PointerStyle(9);
    /**
     * Defines the pointer as a left triangle.
     */
    public static final PointerStyle LEFTTRIANGLE = new PointerStyle(10);
    /**
     * Defines the pointer as a right triangle.
     */
    public static final PointerStyle RIGHTTRIANGLE = new PointerStyle(11);
    /**
     * Defines the pointer as a graded sphere.
     */
    public static final PointerStyle SPHERE = new PointerStyle(12);
    /**
     * Defines the pointer as a polished sphere.
     */
    public static final PointerStyle POLISHEDSPHERE = new PointerStyle(13);
    /**
     * Defines the pointer as a hexagon.
     */
    public static final PointerStyle HEXAGON = new PointerStyle(14);

    private PointerStyle(int value) {
        super(value);
        upperBound++;
    }

    public static int size() { return upperBound; }

    static public PointerStyle fromInt(int value) {
        switch(value) {
         case 0: return RECTANGLE;
         case 1: return CIRCLE;
         case 2: return TRIANGLE;
         case 3: return DOWNTRIANGLE;
         case 4: return CROSS;
         case 5: return DIAGCROSS;
         case 6: return STAR;
         case 7: return DIAMOND;
         case 8: return SMALLDOT;
         case 9: return NOTHING;
         case 10: return LEFTTRIANGLE;
         case 11: return RIGHTTRIANGLE;
         case 12: return SPHERE;
         case 13: return POLISHEDSPHERE;
         default: return HEXAGON;
        }
    }
}

