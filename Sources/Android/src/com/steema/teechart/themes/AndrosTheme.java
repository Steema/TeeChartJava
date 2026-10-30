package com.steema.teechart.themes;

import com.steema.teechart.drawing.Color;
import com.steema.teechart.drawing.DashStyle;
import com.steema.teechart.IBaseChart;
import com.steema.teechart.styles.Series;
import com.steema.teechart.axis.Axis;
import com.steema.teechart.drawing.StringAlignment;

/**
 *
 * <p>Title: AndrosTheme Theme class</p>
 *
 * <p>Description: Summary description for AndrosTheme.</p>
 *
 * <p>Copyright (c) 2005-2019 by Steema Software SL. All Rights Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 *
 */
@SuppressWarnings("serial")
public class AndrosTheme extends OperaTheme {

    private static final long serialVersionUID = 1L;

    public AndrosTheme(IBaseChart c) {
        super(c);
    }

    public void apply() {
        super.apply();

        ColorPalettes.applyPalette(chart, Theme.SeawashPalette);

        chart.setCancelMouse(false);
        chart.setCustomChartRect(false);
        
        chart.getAspect().setView3D(false);
        
        chart.getPanel().getBrush().getGradient().setVisible(false);
        chart.getPanel().getBrush().setColor(Color.fromArgb(29, 56, 109));
        
        chart.getHeader().getFont().setSize(17);
        chart.getHeader().getFont().setName("Verdana");
        chart.getHeader().getFont().getBrush().setColor(Color.fromArgb(192, 192, 192));
        chart.getHeader().setAlignment(StringAlignment.FAR);
        
        chart.getLegend().getFont().setSize(13);
        chart.getLegend().getFont().setName("Verdana");
        chart.getLegend().getFont().getBrush().setColor(Color.fromArgb(192, 192, 192));
        chart.getLegend().getShadow().setVisible(false);
        chart.getLegend().setTransparent(true);
        chart.getLegend().getPen().setVisible(false);
        
        chart.getWalls().getBack().getBrush().setVisible(false);
        chart.getWalls().getBack().setVisible(false);
        chart.getWalls().getBack().setTransparent(true);
        
        for (int t = 0; t < chart.getSeriesCount(); ++t) {
            doChangeSeries(chart.getSeries(t));
        }

        for (int t = 0; t < chart.getAxes().getCount(); ++t) {
            changeAxis(chart.getAxes().getAxis(t));
        }
        
        chart.getAxes().getBottom().getAxisPen().setVisible(true);
        chart.getAxes().getBottom().getMinorTicks().setVisible(true);
        chart.getAxes().getBottom().getTicks().setVisible(true);
        
        chart.getAxes().getLeft().getGrid().setVisible(true);
    }

    /**
     * Gets descriptive text.
     *
     * @return String
     */
    public String getDescription() {
        return "Andros";
    }

    public void changeAxis(Axis chartAxis) {
        chartAxis.getTitle().getFont().setSize(15);
        chartAxis.getTitle().getFont().setName("Verdana");
        chartAxis.getTitle().getFont().getBrush().setColor(Color.fromArgb(192, 192, 192));
        
        chartAxis.getLabels().getFont().setName("Verdana");
        chartAxis.getLabels().getFont().setSize(13);
        chartAxis.getLabels().getFont().getBrush().setColor(Color.fromArgb(192, 192, 192));
        
        chartAxis.getAxisPen().setVisible(false);
        chartAxis.getAxisPen().setWidth(0);
        chartAxis.getAxisPen().setColor(Color.fromArgb(128, 128, 128));
        
        chartAxis.getGrid().setColor(Color.fromArgb(128, 128, 128));
        chartAxis.getGrid().setStyle(DashStyle.SOLID);
        chartAxis.getGrid().setDrawEvery(2);
        chartAxis.getGrid().setVisible(false);
        
        chartAxis.getMinorTicks().setVisible(false);
        chartAxis.getTicks().setVisible(false);
    }

    public void doChangeSeries(Series series) {
        series.getMarks().getFont().setName("Verdana");
        series.getMarks().getFont().setColor(Color.WHITE);
    }
}

