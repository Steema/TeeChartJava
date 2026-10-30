package com.steema.teechart.styles;

import com.steema.teechart.misc.Enum;

public class GaugePointerStyles extends Enum {
/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

protected static int upperBound=0;

    /**
     * Defines the pointer as a rectangle.
     */
    public static final GaugePointerStyles RECTANGLE = new GaugePointerStyles(0);
    /**
     * Defines the pointer as a circle.
     */
    public static final GaugePointerStyles CIRCLE = new GaugePointerStyles(1);
    /**
     * Defines the pointer as a triangle.
     */
    public static final GaugePointerStyles TRIANGLE = new GaugePointerStyles(2);
    /**
     * Defines the pointer as a down pointing triangle.
     */
    public static final GaugePointerStyles DOWNTRIANGLE = new GaugePointerStyles(3);
    /**
     * Defines the pointer as a cross.
     */
    public static final GaugePointerStyles CROSS = new GaugePointerStyles(4);
    /**
     * Defines the pointer as a diagonal cross.
     */
    public static final GaugePointerStyles DIAGCROSS = new GaugePointerStyles(5);
    /**
     * Defines the pointer as a star.
     */
    public static final GaugePointerStyles STAR = new GaugePointerStyles(6);
    /**
     * Defines the pointer as a diamond.
     */
    public static final GaugePointerStyles DIAMOND = new GaugePointerStyles(7);
    /**
     * Defines the pointer as a small dot.
     */
    public static final GaugePointerStyles SMALLDOT = new GaugePointerStyles(8);
    /**
     * Defines the pointer as no shape.
     */
    public static final GaugePointerStyles NOTHING = new GaugePointerStyles(9);
    /**
     * Defines the pointer as a left triangle.
     */
    public static final GaugePointerStyles LEFTTRIANGLE = new GaugePointerStyles(10);
    /**
     * Defines the pointer as a right triangle.
     */
    public static final GaugePointerStyles RIGHTTRIANGLE = new GaugePointerStyles(11);
    /**
     * Defines the pointer as a graded sphere.
     */
    public static final GaugePointerStyles SPHERE = new GaugePointerStyles(12);
    /**
     * Defines the pointer as a polished sphere.
     */
    public static final GaugePointerStyles POLISHEDSPHERE = new GaugePointerStyles(13);
    /**
     * Defines the pointer as a hexagon.
     */
    public static final GaugePointerStyles HEXAGON = new GaugePointerStyles(14);
    /**
     * Defines the pointer as a hand.
     */
    public static final GaugePointerStyles HAND = new GaugePointerStyles(15);
    /**
     * Defines the pointer as a center.
     */
    public static final GaugePointerStyles CENTER = new GaugePointerStyles(16);
    /**
     * Defines the pointer as an axis tick.
     */
    public static final GaugePointerStyles TICK = new GaugePointerStyles(17);
    /**
     * Defines the pointer as a minor (axis) tick.
     */
    public static final GaugePointerStyles MINORTICK = new GaugePointerStyles(18);
    /**
     * Defines the pointer as a colorline.
     */
    public static final GaugePointerStyles COLORLINE = new GaugePointerStyles(19);

    private GaugePointerStyles(int value) {
        super(value);
        upperBound++;
    }

    public static int size() { return upperBound; }

    static public GaugePointerStyles fromInt(int value) {
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
         case 14: return HEXAGON;
         case 15: return HAND;
         case 16: return CENTER;
         case 17: return TICK;
         case 18: return MINORTICK;
         default: return COLORLINE;
        }
    }
}
