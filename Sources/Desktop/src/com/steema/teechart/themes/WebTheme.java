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
 * <p>Title: Web Theme class</p>
 *
 * <p>Description: TeeChart for Java</p>
 *
 * <p>Copyright (c) 2004-2019 by Steema Software SL. All Rights Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 *
 */
public class WebTheme extends DefaultTheme {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
     * Description of Classic theme
     *
     * @param c IBaseChart
     */
    public WebTheme(IBaseChart c) {
        super(c);
    }
    
    public WebTheme() {}

    public String toString() {
        return Language.getString("WebTheme");
    }
    
    private Color darkSilver = Color.fromArgb(196, 196, 196);
    
    public void apply() {
        super.apply();
        
        chart.getPanel().getBevel().setOuter(BevelStyle.NONE);
        chart.getPanel().getPen().setVisible(true);        
        chart.getPanel().setColor(darkSilver);
        
        chart.getLegend().getShadow().setColor(Color.DARK_GRAY);
        chart.getLegend().getFont().setName("Lucida Console");
        chart.getLegend().getFont().setSize(9);
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

        chart.getHeader().getFont().setName("Lucida Console");
        chart.getHeader().getFont().setSize(10);
        chart.getHeader().getFont().setColor(Color.BLACK);
        chart.getHeader().getFont().setBold(true);

        ColorPalettes.applyPalette(chart, Theme.WebPalette);
    }

    private void doChangeWall(Wall wall) {
        wall.getPen().setColor(Color.WHITE);
    }

    private void doChangeAxis(Axis axis) {                
        axis.getGrid().setVisible(true);
        axis.getGrid().setColor(darkSilver);
        axis.getGrid().setStyle(DashStyle.SOLID);

        axis.getTicks().setColor(Color.BLACK);

        axis.getMinorTicks().setLength(-3);
        axis.getTicks().setLength(0);
        axis.getTicksInner().setLength(6);

        axis.getLabels().getFont().setName("Lucida Console");
        axis.getLabels().getFont().setSize(10);
    }

    public void doChangeSeries(Series series) {
        series.getMarks().getGradient().setVisible(true);
        series.getMarks().getGradient().setStartColor(Color.SILVER);
        series.getMarks().getFont().setName("Lucida Console");
    }
}

