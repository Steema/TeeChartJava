package com.steema.teechart.themes;

import com.steema.teechart.BevelStyle;
import com.steema.teechart.IBaseChart;
import com.steema.teechart.axis.Axis;
import com.steema.teechart.drawing.Color;
import com.steema.teechart.drawing.DashStyle;
import com.steema.teechart.drawing.GradientDirection;
import com.steema.teechart.styles.Series;

/**
 * Created by abde on 29/02/16.
 */
public abstract class TeeChartTheme extends DefaultTheme {

    private static final long serialVersionUID = 1L;

    public TeeChartTheme(IBaseChart c) {
        super(c);
    }

    public void apply() {
        ColorPalettes.applyPalette(chart, Theme.LookoutPalette);
        chart.getHeader().getFont().setSize(15);
        chart.getHeader().getGradient().setDirection(GradientDirection.VERTICAL);
        chart.getHeader().getGradient().setEndColor(Color.YELLOW);
        chart.getHeader().getGradient().setMiddleColor(Color.EMPTY);
        chart.getHeader().getGradient().setStartColor(Color.WHITE);
        chart.getHeader().getPen().setColor(Color.BLACK);
        chart.getHeader().getPen().setVisible(false);
        chart.getHeader().getPen().setWidth(1);
        chart.getHeader().getShadow().setSize(0);
        chart.getHeader().getShadow().setTransparency(0);
        chart.getHeader().setTransparency(0);

        chart.getLegend().getDividingLines().setColor(Color.BLACK);
        chart.getLegend().getFont().setColor(Color.BLACK);
        chart.getLegend().getFont().setName("Arial");
        chart.getLegend().getFont().setSize(15);
        chart.getLegend().getGradient().setDirection(GradientDirection.VERTICAL);
        chart.getLegend().getGradient().setEndColor(Color.YELLOW);
        chart.getLegend().getGradient().setMiddleColor(Color.EMPTY);
        chart.getLegend().getGradient().setStartColor(Color.WHITE);
        chart.getLegend().getGradient().setVisible(false);
        chart.getLegend().getPen().setColor(Color.BLACK);
        chart.getLegend().getPen().setStyle(DashStyle.SOLID);
        chart.getLegend().getPen().setVisible(true);
        chart.getLegend().getPen().setWidth(1);
        chart.getLegend().getShadow().setColor(Color.BLACK);
        chart.getLegend().getShadow().setHeight(3);
        chart.getLegend().getShadow().setTransparency(0);
        chart.getLegend().getShadow().setWidth(3);
        chart.getLegend().getSymbol().setDefaultPen(true);
        chart.getLegend().getSymbol().getPen().setVisible(true);
        chart.getLegend().getSymbol().setSquared(false);
        chart.getLegend().setTransparent(false);

        chart.getPanel().getBevel().setInner(BevelStyle.NONE);
        chart.getPanel().getBevel().setOuter(BevelStyle.RAISED);
        chart.getPanel().getBevel().setWidth(1);
        chart.getPanel().setBorderRound(0);
        chart.getPanel().setColor(Color.BLACK);
        chart.getPanel().getGradient().setDirection(GradientDirection.VERTICAL);
        chart.getPanel().getGradient().setEndColor(Color.YELLOW);
        chart.getPanel().getGradient().setMiddleColor(Color.EMPTY);
        chart.getPanel().getGradient().setStartColor(Color.WHITE);
        chart.getPanel().getGradient().setVisible(false);
        chart.getPanel().getPen().setColor(Color.BLACK);
        chart.getPanel().getPen().setStyle(DashStyle.SOLID);
        chart.getPanel().getPen().setVisible(false);
        chart.getPanel().setWidth(1);
        chart.getPanel().getShadow().setColor(Color.DARK_GRAY);
        chart.getPanel().getShadow().setSize(0);
        chart.getPanel().getShadow().setVisible(false);

        chart.getWalls().getBack().setApplyDark(true);
        chart.getWalls().getBack().getGradient().setEndColor(Color.YELLOW);
        chart.getWalls().getBack().getGradient().setMiddleColor(Color.EMPTY);
        chart.getWalls().getBack().getGradient().setStartColor(Color.WHITE);
        chart.getWalls().getBack().getGradient().setVisible(false);
        chart.getWalls().getBack().getPen().setColor(Color.BLACK);
        chart.getWalls().getBack().getPen().setStyle(DashStyle.SOLID);
        chart.getWalls().getBack().getPen().setVisible(true);
        chart.getWalls().getBack().getPen().setWidth(1);
        chart.getWalls().getBack().getGradient().setDirection(GradientDirection.VERTICAL);
        chart.getWalls().getBack().setColor(Color.SILVER);
        chart.getWalls().getBack().setTransparent(true);
        chart.getWalls().getBack().setSize(0);


        chart.getWalls().getBottom().setApplyDark(true);
        chart.getWalls().getBottom().setColor(Color.WHITE);
        chart.getWalls().getBottom().getGradient().setEndColor(Color.YELLOW);
        chart.getWalls().getBottom().getGradient().setMiddleColor(Color.EMPTY);
        chart.getWalls().getBottom().getGradient().setStartColor(Color.WHITE);
        chart.getWalls().getBottom().getGradient().setVisible(false);
        chart.getWalls().getBottom().getPen().setColor(Color.BLACK);
        chart.getWalls().getBottom().getPen().setStyle(DashStyle.SOLID);
        chart.getWalls().getBottom().getPen().setVisible(true);
        chart.getWalls().getBottom().getPen().setWidth(1);


        chart.getWalls().getLeft().setApplyDark(true);
        chart.getWalls().getLeft().setColor(Color.BISQUE);
        chart.getWalls().getLeft().getGradient().setEndColor(Color.YELLOW);
        chart.getWalls().getLeft().getGradient().setMiddleColor(Color.EMPTY);
        chart.getWalls().getLeft().getGradient().setStartColor(Color.WHITE);
        chart.getWalls().getLeft().getGradient().setVisible(false);
        chart.getWalls().getLeft().getPen().setColor(Color.BLACK);
        chart.getWalls().getLeft().getPen().setStyle(DashStyle.SOLID);
        chart.getWalls().getLeft().getPen().setVisible(true);
        chart.getWalls().getLeft().getPen().setWidth(1);


        chart.getWalls().getRight().setApplyDark(true);
        chart.getWalls().getRight().setColor(Color.SILVER);
        chart.getWalls().getRight().getGradient().setEndColor(Color.YELLOW);
        chart.getWalls().getRight().getGradient().setMiddleColor(Color.EMPTY);
        chart.getWalls().getRight().getGradient().setStartColor(Color.WHITE);
        chart.getWalls().getRight().getGradient().setVisible(false);
        chart.getWalls().getRight().getPen().setColor(Color.BLACK);
        chart.getWalls().getRight().getPen().setStyle(DashStyle.SOLID);
        chart.getWalls().getRight().getPen().setVisible(true);
        chart.getWalls().getRight().getPen().setWidth(1);

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
        chartAxis.getLabels().getFont().setSize(15);
        chartAxis.getTitle().getFont().setSize(15);
        chartAxis.getTitle().getFont().setName("Arial");
        chartAxis.getTitle().getFont().setBold(false);
        chartAxis.getTitle().getFont().setItalic(false);
        chartAxis.getTitle().getFont().setColor(Color.BLACK);

    }

    public void doChangeSeries(Series series) {
        series.getMarks().getArrow().setColor(Color.WHITE);
        series.getMarks().getFont().setColor(Color.BLACK);
        series.getMarks().getFont().setName("Arial");
        series.getMarks().setBackColor(Color.WHITE);
        series.getMarks().getFont().setSize(15);
        series.getMarks().getGradient().setDirection(GradientDirection.VERTICAL);
        series.getMarks().getGradient().setEndColor(Color.YELLOW);
        series.getMarks().getGradient().setMiddleColor(Color.EMPTY);
        series.getMarks().getGradient().setStartColor(Color.WHITE);
        series.getMarks().getGradient().setVisible(false);
        series.getMarks().setTransparent(false);

        /*series.getMarks().getFont().setName("Verdana");

        series.getMarks().getArrow().setVisible(false);
        series.getMarks().setArrowLength(8);
        series.getMarks().getShadow().setVisible(false);

        if (!series.getColorEach()) {
            series.getMarks().setFollowSeriesColor(true);
        }*/
    }
}

