package com.steema.teechart.axis;

import com.steema.teechart.TextShape;
import com.steema.teechart.IBaseChart;

/**
 * Custom label
 *
 * <p>Title: AxisLabelItem class</p>
 *
 * <p>Description: Custom label</p>
 *
 * <p>Copyright (c) 2005-2019 by Steema Software SL. All Rights Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 *
 */
public class AxisLabelItem extends TextShape {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private double value;

    protected AxisLabelsItems iAxisLabelsItems;

    public AxisLabelItem(IBaseChart c) {
        super(c);
    }

    /**
     * Refreshes Label
     */
    public void repaint() {
        iAxisLabelsItems.iAxis.chart.invalidate();
    }

    public void setValue(double v) {
        if (v != value) {
            value = v;
            repaint();
        }
    }

    /**
     * Determines the Axis Value of the Label.
     *
     * @return double
     */
    public double getValue() {
        return value;
    }
}

