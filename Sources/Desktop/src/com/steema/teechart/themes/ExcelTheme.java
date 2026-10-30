package com.steema.teechart.themes;

import com.steema.teechart.IBaseChart;
import com.steema.teechart.BevelStyle;
import com.steema.teechart.drawing.Color;
import com.steema.teechart.styles.Series;
import com.steema.teechart.drawing.DashStyle;
import com.steema.teechart.axis.Axis;
import com.steema.teechart.Wall;

/**
 *
 * <p>Title: ExcelTheme class</p>
 *
 * <p>Description: Summary description for ExcelTheme.</p>
 *
 * <p>Copyright (c) 2005-2019 by Steema Software SL. All Rights Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 *
 */
public class ExcelTheme extends DefaultTheme {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;


    public ExcelTheme(IBaseChart c) {
        super(c);
    }
        
    public ExcelTheme() {}

    /**
     * Gets descriptive text.
     *
     * @return String
     */
    public String getDescription() {
        return "Microsoft Excel";
    }

    /**
     * Applies Excel-like visual formatting properties to Chart parameter.
     */
    public void apply() {
        super.apply();
        chart.getPanel().getBevel().setInner(BevelStyle.NONE);
        chart.getPanel().getBevel().setOuter(BevelStyle.NONE);
        chart.getPanel().setBorderRound(0);
        chart.getPanel().getPen().setVisible(true);
        chart.getPanel().getPen().setWidth(1);
        chart.getPanel().getPen().setStyle(DashStyle.SOLID);
        chart.getPanel().getPen().setColor(Color.BLACK);

        chart.getPanel().setColor(Color.WHITE);
        chart.getPanel().getGradient().setVisible(false);

        chart.getLegend().getShadow().setHeight(0);
        chart.getLegend().getShadow().setWidth(0);
        chart.getLegend().getDividingLines().setVisible(false);
        chart.getLegend().getFont().setName("Arial");
        chart.getLegend().getFont().setSize(10);
        chart.getLegend().getPen().setColor(Color.BLACK);
        chart.getLegend().getPen().setWidth(1);
        chart.getLegend().getPen().setStyle(DashStyle.SOLID);
        chart.getLegend().getPen().setVisible(false);
        chart.getLegend().setTransparent(false);
        chart.getLegend().getGradient().setVisible(false);

        doChangeWall(chart.getWalls().getLeft(), Color.SILVER);
        doChangeWall(chart.getWalls().getRight(), Color.SILVER);
        doChangeWall(chart.getWalls().getBack(), Color.SILVER);
        doChangeWall(chart.getWalls().getBottom(), Color.DARK_GRAY);

        chart.getWalls().getBack().setTransparent(false);

        for (int t = 0; t < chart.getAxes().getCount(); ++t) {
            doChangeAxis(chart.getAxes().getAxis(t));
        }

        chart.getAxes().getTop().getGrid().setVisible(false);
        chart.getAxes().getBottom().getGrid().setVisible(false);
        chart.getAxes().getBottom().getGrid().setCentered(true);

        for (int t = 0; t < chart.getSeriesCount(); ++t) {
            doChangeSeries(chart.getSeries(t));
        }

        chart.getHeader().getFont().setName("Arial");
        chart.getHeader().getFont().setSize(10);
        chart.getHeader().getFont().setColor(Color.BLACK);

        ColorPalettes.applyPalette(chart, 2);
    }

    static private void doChangeWall(Wall chartWall, Color aColor) {
        chartWall.getPen().setVisible(true);
        chartWall.getPen().setColor(Color.DARK_GRAY);
        chartWall.getPen().setWidth(1);
        chartWall.getPen().setStyle(DashStyle.SOLID);
        chartWall.getGradient().setVisible(false);
        chartWall.setColor(aColor);
        chartWall.setApplyDark(false);
    }

    static private void doChangeAxis(Axis chartAxis) {
        chartAxis.getGrid().setWidth(1);
        chartAxis.getGrid().setVisible(true);
        chartAxis.getGrid().setColor(Color.BLACK);
        chartAxis.getGrid().setStyle(DashStyle.SOLID);
        chartAxis.getGrid().setCentered(false);

        chartAxis.getTicks().setColor(Color.BLACK);

        chartAxis.getMinorTicks().setVisible(false);
        chartAxis.getTicksInner().setVisible(false);

        chartAxis.getLabels().getFont().setName("Arial");
        chartAxis.getLabels().getFont().setSize(10);
    }


    public void doChangeSeries(Series chartSeries) {
        chartSeries.getMarks().getGradient().setVisible(false);
        chartSeries.getMarks().setTransparent(true);
        chartSeries.getMarks().getFont().setName("Arial");
        chartSeries.getMarks().getFont().setSize(10);
        chartSeries.getMarks().getArrow().setColor(Color.WHITE);
    }

}

