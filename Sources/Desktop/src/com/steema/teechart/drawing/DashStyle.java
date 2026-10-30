package com.steema.teechart.drawing;

import com.steema.teechart.misc.Enum;

/**
 * <p>Title: DashStyle class</p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright (c) 2005-2019 by Steema Software SL. All Rights Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 *
 */
final public class DashStyle extends Enum {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final DashStyle SOLID = new DashStyle(0);
    public static final DashStyle DOT = new DashStyle(1);
    public static final DashStyle DASH = new DashStyle(2);
    public static final DashStyle DASHDOT = new DashStyle(3);
    public static final DashStyle DASHDOTDOT = new DashStyle(4);

    private DashStyle(int value) {
        super(value);
        dashWidth = 1;
    }

    static public DashStyle fromValue(int value) {
        switch (value) {
        case 0: return DashStyle.SOLID;
        case 1: return DashStyle.DOT;
        case 2: return DashStyle.DASH;
        case 3: return DashStyle.DASHDOT;
        default: return DashStyle.DASHDOTDOT;
        }
    }

    private int dashWidth;
    public int getDashWidth() {
        return dashWidth;
    }

    public void setDashWidth(int value) {
        dashWidth = value;
    }
    /**
     * Returns an array of values that determines the number of filled and
     * non-filled pixels of the Pen dash.
     *
     * @return float[]
     */
    public float[] getDash() {
        switch (getValue()) {
        case 1:
            return new float[] {dashWidth, dashWidth};
        case 2:
            return new float[] {dashWidth * 3, dashWidth};
        case 3:
            return new float[] {dashWidth * 3, dashWidth, dashWidth, dashWidth};
        case 4:
            return new float[] {dashWidth * 3, dashWidth, dashWidth, dashWidth, dashWidth, dashWidth};
        default:
            return new float[] {1};
        }
    }
}

