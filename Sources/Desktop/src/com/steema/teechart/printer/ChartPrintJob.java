package com.steema.teechart.printer;

import com.steema.teechart.Rectangle;
import com.steema.teechart.IBaseChart;

/**
 *
 * <p>Title: ChartPrintJob class</p>
 *
 * <p>Description: Class provides access to the Chart at print time.</p>
 *
 * <p>Copyright (c) 2005-2019 by Steema Software SL. All Rights Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 *
 */
public class ChartPrintJob {

    private Rectangle chartRect;
    private IBaseChart chart;

    public ChartPrintJob(IBaseChart c, Rectangle r) {
        chart = c;
        chartRect = r;
    }

    public IBaseChart getChart() {
        return chart;
    }

    public Rectangle getChartRect() {
        return chartRect;
    }
}


