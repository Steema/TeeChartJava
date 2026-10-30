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
 * <p>Title: Grayscale Theme class</p>
 *
 * <p>Description: TeeChart for Java</p>
 *
 * <p>Copyright (c) 2004-2019 by Steema Software SL. All Rights Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 *
 */
public class GrayscaleTheme extends DefaultTheme {

    /**
     * Description of Classic theme
     *
     * @param c IBaseChart
     */
    public GrayscaleTheme(IBaseChart c) {
        super(c);
    }
    
    public GrayscaleTheme() {}

    public String toString() {
        return Language.getString("GrayscaleTheme");
    }

    public void apply() {
        super.apply();

        chart.getPanel().getBevel().setOuter(BevelStyle.NONE);

        chart.getPanel().getPen().setVisible(true);
        chart.getPanel().getPen().setColor(Color.WHITE);

        chart.getLegend().getShadow().setSize(0);
        chart.getLegend().getDividingLines().setVisible(true);
        chart.getLegend().getFont().setSize(10);
        chart.getLegend().setTransparent(true);

        doChangeWall(chart.getWalls().getLeft());
        doChangeWall(chart.getWalls().getRight());
        doChangeWall(chart.getWalls().getBack());
        doChangeWall(chart.getWalls().getBottom());

        chart.getWalls().getBack().setTransparent(false);
        
        for (int t = 0; t < chart.getAxes().getCount(); ++t) {
            doChangeAxis(chart.getAxes().getAxis(t));
        }
        
        for (int t = 0; t < chart.getSeriesCount(); ++t) {
            doChangeSeries(chart.getSeries(t));
        }
        
        chart.getHeader().getGradient().setVisible(true);
        chart.getHeader().getGradient().setEndColor(Color.GRAY);

        chart.getHeader().getFont().setSize(12);
        chart.getHeader().getFont().setColor(Color.BLACK);
        
        chart.getHeader().setColor(Color.WHITE);

        ColorPalettes.applyPalette(chart, Theme.GrayscalePalette);
    }

    private void doChangeWall(Wall wall) {
        wall.setColor(Color.WHITE);
        wall.setApplyDark(false);
        wall.setSize(8);
    }

    private void doChangeAxis(Axis axis) {
        axis.getAxisPen().setWidth(1);
        
        axis.getGrid().setColor(Color.BLACK);
        axis.getGrid().setStyle(DashStyle.SOLID);

        axis.getMinorTicks().setVisible(false);
        axis.getTicksInner().setVisible(false);
    }


    public void doChangeSeries(Series series) {
        series.getMarks().setTransparent(true);
        series.getMarks().getFont().setSize(10);
        series.getMarks().getArrow().setColor(Color.BLACK);
    }
}

