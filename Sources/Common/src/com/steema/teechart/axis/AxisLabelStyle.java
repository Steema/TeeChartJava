package com.steema.teechart.axis;

import com.steema.teechart.misc.Enum;

/**
 * Defines the possible values of Axis.getLabels().getStyle()
 *
 * <p>Title: AxisLabelStyle class</p>
 *
 * <p>Description: Defines the possible values of Axis.getLabels().getStyle()</p>
 *
 * <p>Copyright (c) 2005-2019 by Steema Software SL. All Rights Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 *
 */
final public class AxisLabelStyle extends Enum {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
     * Chooses the Style automatically
     */
    final public static AxisLabelStyle AUTO = new AxisLabelStyle(0);

    /**
     * No label.&nbsp;This will trigger the event with empty strings
     */
    final public static AxisLabelStyle NONE = new AxisLabelStyle(1);

    /**
     * Axis labeling is based on axis Minimum and Maximum properties.
     */
    final public static AxisLabelStyle VALUE = new AxisLabelStyle(2);

    /**
     * Each Series point will have a Label using SeriesMarks style.
     */
    final public static AxisLabelStyle MARK = new AxisLabelStyle(3);

    /**
     * Each Series point will have a Label using Series.XLabels strings
     */
    final public static AxisLabelStyle TEXT = new AxisLabelStyle(4);

    private AxisLabelStyle(int value) {
        super(value);
    }

    static public AxisLabelStyle fromValue(int value) {
        switch (value) {
        case 0:
            return AUTO;
        case 1:
            return NONE;
        case 2:
            return VALUE;
        case 3:
            return MARK;
        default:
            return TEXT;
        }
    }
}

