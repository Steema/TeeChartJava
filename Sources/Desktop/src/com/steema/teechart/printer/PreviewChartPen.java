package com.steema.teechart.printer;

import com.steema.teechart.drawing.ChartPen;
import com.steema.teechart.drawing.DashStyle;

/**
 * <p>Title: TeeChart for Java</p>
 *
 * <p>Description: TeeChart for Java</p>
 *
 * <p>Copyright (c) 2004-2019 by Steema Software SL. All Rights
 * Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 *
 */
public class PreviewChartPen extends ChartPen {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public PreviewChartPen(ChartPen source) {
        super(source);
    }

    public DashStyle getStyle() {
        return super.getStyle();
    }
}

