package com.steema.teechart.axis;

import com.steema.teechart.drawing.ChartPen;
import com.steema.teechart.drawing.Color;
import com.steema.teechart.IBaseChart;

/**
 *
 * <p>Title: AxisLinePen class</p>
 *
 * <p>Description: Determines the pen used to draw the axis lines</p>
 *
 * <p>Copyright (c) 2005-2019 by Steema Software SL. All Rights Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 *
 */
public final class AxisLinePen extends ChartPen {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public AxisLinePen(IBaseChart c) {
        super(c, Color.fromArgb(64, 64, 64), 2);
    }
}


