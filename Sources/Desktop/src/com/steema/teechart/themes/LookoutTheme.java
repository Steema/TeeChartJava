package com.steema.teechart.themes;

import com.steema.teechart.drawing.Color;
import com.steema.teechart.drawing.DashStyle;
import com.steema.teechart.IBaseChart;
import com.steema.teechart.styles.Series;
import com.steema.teechart.axis.Axis;
import com.steema.teechart.drawing.GradientDirection;

/**
 *
 * <p>Title: LookoutTheme Theme class</p>
 *
 * <p>Description: Summary description for LookoutTheme.</p>
 *
 * <p>Copyright (c) 2005-2019 by Steema Software SL. All Rights Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 *
 */
@SuppressWarnings("serial")
public class LookoutTheme extends OperaTheme {

    private static final long serialVersionUID = 1L;

    public LookoutTheme(IBaseChart c) {
        super(c);
    }
    
    public LookoutTheme() {}

    public void apply() {
        ColorPalettes.applyPalette(chart, Theme.LookoutPalette);

        chart.setCancelMouse(false);
        chart.setCustomChartRect(false);

        chart.getAspect().setOrthogonal(false);
        chart.getAspect().setView3D(false);

        chart.getPanel().setColor(Color.fromArgb(0xFFFFFFFF));
        chart.getPanel().getGradient().setEndColor(Color.fromArgb(0xFFFFFFFF));
        chart.getPanel().getGradient().setMiddleColor(Color.fromArgb(0xFFEAEAEA));
        chart.getPanel().getGradient().setStartColor(Color.fromArgb(0xFFEAEAEA));
        chart.getPanel().getGradient().setVisible(false);
        
        chart.getHeader().getFont().setColor(Color.fromArgb(0xFF000080));
        chart.getHeader().getFont().getBrush().setColor(Color.fromArgb(128, 128, 128));
        chart.getHeader().getFont().setName("Verdana");
        chart.getHeader().getFont().setSize(17);
        chart.getHeader().getPen().setVisible(true);

        chart.getLegend().getFont().setName("Verdana");
        chart.getLegend().getFont().setSize(13);
        chart.getLegend().getFont().getBrush().setColor(Color.fromArgb(0xFF404040));
        chart.getLegend().getSymbol().getPen().setVisible(true);
        chart.getLegend().getShadow().setVisible(false);
        chart.getLegend().setTransparent(true);
        chart.getLegend().getPen().setVisible(false);

        chart.getWalls().getBack().getGradient().setDirection(GradientDirection.VERTICAL);
        chart.getWalls().getBack().getGradient().setEndColor(Color.fromArgb(0xFFFFFFFF));
        chart.getWalls().getBack().getGradient().getMiddleColor().setEmpty(true);
        chart.getWalls().getBack().getGradient().setStartColor(Color.fromArgb(0xFFEAEAEA));
        chart.getWalls().getBack().getGradient().setVisible(true);
        chart.getWalls().getBack().getBrush().setVisible(false);
        chart.getWalls().getBack().setVisible(false);
        chart.getWalls().getBack().setTransparent(true);

        chart.getWalls().getRight().setColor(Color.fromArgb(0xFFC0C0C0));

        for (int t = 0; t < chart.getSeriesCount(); ++t) {
            doChangeSeries(chart.getSeries(t));
        }

        for (int t = 0; t < chart.getAxes().getCount(); ++t) {
            changeAxis(chart.getAxes().getAxis(t));
        }

        chart.getAxes().getBottom().getGrid().setVisible(false);
        chart.getAxes().getBottom().getAxisPen().setVisible(true);
        chart.getAxes().getBottom().getMinorTicks().setVisible(true);
        chart.getAxes().getBottom().getTicks().setVisible(true);
    }

    /**
     * Gets descriptive text.
     *
     * @return String
     */
    public String getDescription() {
        return "Lookout";
    }

    public void changeAxis(Axis chartAxis) {
        chartAxis.getAxisPen().setColor(Color.fromArgb(0xFF404040));
        chartAxis.getAxisPen().setVisible(false);

        chartAxis.getGrid().setColor(Color.fromArgb(0xFFA9A9A9));
        chartAxis.getGrid().setStyle(DashStyle.SOLID);
        chartAxis.getGrid().setDrawEvery(2);

        chartAxis.getLabels().getFont().setName("Verdana");
        chartAxis.getLabels().getFont().setColor(Color.fromArgb(0xFFFFFFFF));
        chartAxis.getLabels().getFont().getBrush().setColor(Color.fromArgb(0xff404040));
        chartAxis.getLabels().getFont().setSize(13);

        chartAxis.getTicks().setColor(Color.fromArgb(0xFFA9A9A9));
        chartAxis.getTicks().setLength(4);
        chartAxis.getTicks().setVisible(false);

        chartAxis.getMinorTicks().setVisible(false);

        chartAxis.getTitle().getFont().setName("Verdana");
        chartAxis.getTitle().getFont().setSize(15);
        chartAxis.getTitle().getFont().getBrush().setColor(Color.fromArgb(0xff404040));
    }

    public void doChangeSeries(Series series) {
        series.getMarks().getFont().setName("Verdana");

        series.getMarks().getArrow().setVisible(false);
        series.getMarks().setArrowLength(8);
        series.getMarks().getShadow().setVisible(false);

        if (!series.getColorEach()) {
            series.getMarks().setFollowSeriesColor(true);
        }
    }
}

