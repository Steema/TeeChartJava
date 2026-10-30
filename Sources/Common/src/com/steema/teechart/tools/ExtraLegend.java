package com.steema.teechart.tools;

import com.steema.teechart.events.ChartDrawEvent;
import com.steema.teechart.languages.Language;

import com.steema.teechart.Rectangle;
import com.steema.teechart.legend.Legend;
import com.steema.teechart.IBaseChart;
import com.steema.teechart.legend.LegendStyle;

/**
 *
 * <p>Title: ExtraLegend class</p>
 *
 * <p>Description: ExtraLegend tool.</p>
 *
 * <p>Copyright (c) 2005-2019 by Steema Software SL. All Rights Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 *
 */
public class ExtraLegend extends ToolSeries {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Legend legend;

    public ExtraLegend() {
        this((IBaseChart)null);
    }

    public ExtraLegend(IBaseChart c) {
        super(c);
    }

    /**
     * Defines the Legend characteristics.
     *
     * @return Legend
     */
    public Legend getLegend() {
        if (legend == null) {
            legend = new Legend(chart);
            legend.setCustomPosition(true);
            legend.setLegendStyle(LegendStyle.VALUES);
        }
        return legend;
    }

    /**
     * Defines the Legend characteristics.
     *
     * @param value Legend
     */
    public void setLegend(Legend value) {
        legend.assign(value);
    }

    public void chartEvent(ChartDrawEvent ce) {
        super.chartEvent(ce);
        if ((ce.getID()==ChartDrawEvent.PAINTED) &&
            (ce.getDrawPart()==ChartDrawEvent.CHART)) {
            if (chart != null && getSeries() != null) {
                getLegend().setSeries(getSeries());
                if (getLegend().getVisible()) {
                    drawExtraLegend();
                }
            }
        }
    }

    private void drawExtraLegend() {
        Rectangle rect = chart.getChartRect();
        Legend tmp = chart.getLegend();
        chart.setLegend(getLegend());
        rect = chart.doDrawLegend(chart.getGraphics3D(), rect);
        chart.setLegend(tmp);
    }

    /**
     * Gets descriptive text.
     *
     * @return String
     */
    public String getDescription() {
        return Language.getString("ExtraLegendTool");
    }

    /**
     * Gets detailed descriptive text.
     *
     * @return String
     */
    public String getSummary() {
        return Language.getString("ExtraLegendSummary");
    }
}

