package com.steema.teechart.axis;

import com.steema.teechart.drawing.ChartPen;
import com.steema.teechart.drawing.Color;
import com.steema.teechart.IBaseChart;

/**
 *
 * <p>Title: TicksPen class</p>
 *
 * <p>Description: Determines the kind of Pen used to draw Axis marks along
 * the Axis line.</p>
 *
 * <p>Copyright (c) 2005-2019 by Steema Software SL. All Rights
 * Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 *
 */
public class TicksPen extends ChartPen {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected int length;
    transient protected int defaultLength;

    public TicksPen(IBaseChart c) {
        super(c);
        setDefaultColor(Color.DARK_GRAY);
    }

    private boolean shouldSerializeLength() {
        return length != defaultLength;
    }

    /**
     * Length of Axis Ticks in pixels.
     *
     * @return int
     */
    public int getLength() {
        return length;
    }

    /**
     * Sets the length of Axis Ticks in pixels.
     *
     * @param value int
     */
    public void setLength(int value) {
        length = setIntegerProperty(length, value);
    }
}

