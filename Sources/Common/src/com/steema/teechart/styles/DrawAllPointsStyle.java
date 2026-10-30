package com.steema.teechart.styles;

import com.steema.teechart.misc.Enum;

/**
 *
 * <p>Title: DrawAllPointsStyle class</p>
 *
 * <p>Description: DrawAllPointsStyle property allows you to choose the method which will be used for displaying repeated points at same horizontal pixel.</p>
 *
 * <p>Copyright (c) 2005-2019 by Steema Software SL. All Rights Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 */
public class DrawAllPointsStyle extends Enum {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static int upperBound=0;

    /**
     * When the series has several points that share the same X pixel position, but with different Y position, it will only display the first point.
     */
    public static final DrawAllPointsStyle FIRST = new DrawAllPointsStyle(0);
    /**
     * Plots both the minimum and maximum Y that map to each X pixel.
     */
    public static final DrawAllPointsStyle MINMAX = new DrawAllPointsStyle(1);

    private DrawAllPointsStyle(int value) {
        super(value);
        upperBound++;
    }

    public static int size() { return upperBound; }

    static public DrawAllPointsStyle fromInt(int value) {
        switch(value) {
         case 0: return FIRST;
         default: return MINMAX;
        }
    }
}

