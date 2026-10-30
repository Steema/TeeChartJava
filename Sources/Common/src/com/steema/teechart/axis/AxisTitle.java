package com.steema.teechart.axis;

import com.steema.teechart.IBaseChart;
import com.steema.teechart.TextShape;

/**
 *
 * <p>Title: AxisTitle class</p>
 *
 * <p>Description: Axis Title characteristics</p>
 *
 * <p>Copyright (c) 2005-2019 by Steema Software SL. All Rights
 * Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 *
 */
public final class AxisTitle extends TextShape {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int iDefaultAngle;
    private int angle;
    private int customSize;

    public AxisTitle(IBaseChart c) {
        super(c);
    }

    void setInitialAngle(int a) {
        angle = a;
        iDefaultAngle = angle;
    }

    /**
     * Rotation in degrees applied to each Axis Label. <br>
     * The Axis will use this to draw its Title.
     *
     * @return int
     */
    public int getAngle() {
        return angle;
    }

    /**
     * Specifies the rotation in degrees applied to each Axis Label. <br>
     *
     * @param value int
     */
    public void setAngle(int value) {
        angle = setIntegerProperty(angle, value % 360);
    }

    /**
     * The string of text used to register near each Chart Axis.<br>
     * When empty, no Title is displayed. Use Angle and Font to control Axis
     * Title formatting. <br>
     * Default value: ""
     *
     * @return String
     */
    public String getCaption() {
        return getText();
    }

    /**
     * Defines the string of text used to draw near to each Chart Axis.<br>
     * Default value: ""
     *
     * @param value String
     */
    public void setCaption(String value) {
        if (value != null) {
            setText(value);
        }
    }

    //private boolean shouldSerializeAngle() {
    //    return angle != iDefaultAngle;
    //}

    /**
     * Changes the spacing between the axis/labels and the outer panel edge.<br>
     * Default valure: 0
     *
     * @return int
     */
    public int getCustomSize() {
        return customSize;
    }

    /**
     * Sets the spacing between the axis/labels and the outer panel edge.<br>
     * Default valure: 0
     *
     * @param value int
     */
    public void setCustomSize(int value) {
        customSize = setIntegerProperty(customSize, value);
    }
}

