package com.steema.teechart.styles;

import com.steema.teechart.misc.Enum;

/**
 *
 * <p>Title: BarStyle class</p>
 *
 * <p>Description: Describes the possible values of the CustomBar setBarStyle
 * method</p>
 *
 * <p>Copyright (c) 2005-2019 by Steema Software SL. All Rights Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 *
 * <p>Example:
 * <pre><font face="Courier" size="4">
 * barSeries = new Bar(myChart.getChart());
 * barSeries.setBarStyle(BarStyle.CONE);
 * barSeries.getMarks().setVisible(true);
 * barSeries.getMarks().setStyle(MarksStyle.VALUE);
 * barSeries.fillSampleValues(5);
 * barSeries.setConePercent(30);
 * </font></pre></p>
 *
 */
final public class BarStyle extends Enum {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
     * Defines a rectangle shape for each bar series point.
     */
    public static final BarStyle RECTANGLE = new BarStyle(0);

    /**
     * Defines a pyramid shape for each bar series point.
     */
    public static final BarStyle PYRAMID = new BarStyle(1);

    /**
     * Defines an inverted pyramid shape for each bar series point.
     */
    public static final BarStyle INVPYRAMID = new BarStyle(2);

    /**
     * Defines a cylinder shape for each bar series point.
     */
    public static final BarStyle CYLINDER = new BarStyle(3);

    /**
     * Defines an ellipse shape for each bar series point.
     */
    public static final BarStyle ELLIPSE = new BarStyle(4);

    /**
     * Defines an arrow shape for each bar series point.
     */
    public static final BarStyle ARROW = new BarStyle(5);

    /**
     * Defines a rectangle shape with a gradient for each bar series point.
     */
    public static final BarStyle RECTGRADIENT = new BarStyle(6);

    /**
     * Defines a cone shape for each bar series point.
     */
    public static final BarStyle CONE = new BarStyle(7);

    /**
     * Defines an inverted arrow shape for each bar series point.
     */
    public static final BarStyle INVARROW = new BarStyle(8);

    /**
     * Defines an inverted cone shape for each bar series point.
     */
    public static final BarStyle INVCONE = new BarStyle(9);
    
    /**
     * Defines a round rectangle for each bar series point.
     */
    public static BarStyle ROUNDRECTANGLE = new BarStyle(10);

    private BarStyle(int value) {
        super(value);
    }

    static public BarStyle fromValue(int value) {
        switch (value) {
        case 0:
            return RECTANGLE;
        case 1:
            return PYRAMID;
        case 2:
            return INVPYRAMID;
        case 3:
            return CYLINDER;
        case 4:
            return ELLIPSE;
        case 5:
            return ARROW;
        case 6:
            return RECTGRADIENT;
        case 7:
            return CONE;
        case 8:
            return INVARROW;
        case 9:
            return INVCONE;
        default:
            return ROUNDRECTANGLE;
        }
    }
}

