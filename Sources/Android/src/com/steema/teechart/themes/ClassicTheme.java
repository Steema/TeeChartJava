package com.steema.teechart.themes;

import com.steema.teechart.languages.Language;

import com.steema.teechart.Wall;
import com.steema.teechart.drawing.Color;
import com.steema.teechart.axis.Axis;
import com.steema.teechart.drawing.DashStyle;
import com.steema.teechart.BevelStyle;
import com.steema.teechart.styles.Series;
import com.steema.teechart.IBaseChart;

/**
 * <p>Title: Classic Theme class</p>
 *
 * <p>Description: TeeChart for Java</p>
 *
 * <p>Copyright (c) 2004-2019 by Steema Software SL. All Rights Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 *
 */
public class ClassicTheme extends DefaultTheme {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	/**
     * Description of Classic theme
     *
     * @param c IBaseChart
     */
    public ClassicTheme(IBaseChart c) {
        super(c);
    }

    public String toString() {
        return Language.getString("ClassicTheme");
    }

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

        chart.getLegend().getShadow().setSize(0);
        chart.getLegend().getDividingLines().setVisible(false);
        chart.getLegend().getFont().setName("Times New Roman");
        chart.getLegend().getFont().setSize(10);
        chart.getLegend().setTransparent(true);
        chart.getLegend().getPen().setVisible(false);
        chart.getLegend().getGradient().setVisible(false);
        chart.getLegend().getSymbol().setDefaultPen(false);
        chart.getLegend().getSymbol().getPen().setVisible(false);

        doChangeWall(chart.getWalls().getLeft());
        doChangeWall(chart.getWalls().getRight());
        doChangeWall(chart.getWalls().getBack());
        doChangeWall(chart.getWalls().getBottom());

        chart.getWalls().getBack().setTransparent(false);

        for (int t = 0; t < chart.getAxes().getCount(); ++t) {
            doChangeAxis(chart.getAxes().getAxis(t));
        }

        chart.getAxes().getBottom().getGrid().setCentered(true);

        for (int t = 0; t < chart.getSeriesCount(); ++t) {
            doChangeSeries(chart.getSeries(t));
        }

        chart.getHeader().getFont().setName("Times New Roman");
        chart.getHeader().getFont().setSize(12);
        chart.getHeader().getFont().setColor(Color.BLACK);
        chart.getHeader().getFont().setBold(false);

        ColorPalettes.applyPalette(chart, 6);
    }

    private void doChangeWall(Wall wall) {
        wall.getPen().setVisible(true);
        wall.getPen().setColor(Color.BLACK);
        wall.getPen().setWidth(1);
        wall.getPen().setStyle(DashStyle.SOLID);
        wall.getGradient().setVisible(false);
        wall.setColor(Color.WHITE);
        wall.setApplyDark(false);
        wall.setSize(8);
    }

    private void doChangeAxis(Axis axis) {
        axis.getAxisPen().setWidth(1);
        axis.getGrid().setVisible(true);
        axis.getGrid().setColor(Color.BLACK);
        axis.getGrid().setStyle(DashStyle.SOLID);

        axis.getTicks().setColor(Color.BLACK);

        axis.getMinorTicks().setVisible(false);
        axis.getTicksInner().setVisible(false);

        axis.getLabels().getFont().setName("Times New Roman");
        axis.getLabels().getFont().setSize(10);

        axis.getTitle().getFont().setName("Times New Roman");
    }


    public void doChangeSeries(Series series) {
        series.getMarks().getGradient().setVisible(false);
        series.getMarks().setTransparent(true);
        series.getMarks().getFont().setName("Times New Roman");
        series.getMarks().getFont().setSize(10);
        series.getMarks().getArrow().setColor(Color.BLACK);
    }
}

