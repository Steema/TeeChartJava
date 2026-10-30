package com.steema.teechart.themes;

import com.steema.teechart.BevelStyle;
import com.steema.teechart.IBaseChart;
import com.steema.teechart.Wall;
import com.steema.teechart.Walls;
import com.steema.teechart.axis.Axis;
import com.steema.teechart.drawing.Color;
import com.steema.teechart.drawing.DashStyle;
import com.steema.teechart.drawing.GradientDirection;
import com.steema.teechart.drawing.StringAlignment;
import com.steema.teechart.languages.Language;
import com.steema.teechart.styles.ArrowHeadStyle;
import com.steema.teechart.styles.Series;

/**
 * <p>Title: Flat Theme class</p>
 *
 * <p>Description: TeeChart for Java</p>
 *
 * <p>Copyright (c) 2004-2019 by Steema Software SL. All Rights Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 *
 */
public class FlatTheme extends DefaultTheme {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
     * Description of Flat theme
     *
     * @param c IBaseChart
     */
    public FlatTheme(IBaseChart c) {
        super(c);
    }

    public String toString() {
        return Language.getString("FlatTheme");
    }

    public void apply() {
        super.apply();

        chart.getPanel().getGradient().setVisible(false);
        chart.getPanel().setBevelOuter(BevelStyle.NONE);
        chart.getPanel().setBevelWidth(2);
        chart.getPanel().setColor(Color.WHITE);
        
        doChangeWall(chart.getWalls().getBack());
        doChangeWall(chart.getWalls().getBottom());
        doChangeWall(chart.getWalls().getLeft());
        doChangeWall(chart.getWalls().getRight());
        
        chart.getLegend().getFont().setSize(19);
        chart.getLegend().getFont().setName("Roboto Th");
        chart.getLegend().getPen().setVisible(false);
        chart.getLegend().getShadow().setVisible(false);
        chart.getLegend().getSymbol().getPen().setVisible(false);
        
        chart.getHeader().setAlignment(StringAlignment.NEAR);
        chart.getHeader().getFont().setColor(Color.BLACK);
        chart.getHeader().getFont().setSize(47);
        chart.getHeader().getFont().setName("Roboto Th");
        chart.getHeader().getFont().getShadow().setColor(Color.GRAY);
        chart.getHeader().getFont().getShadow().setHorizSize(1);
        chart.getHeader().getFont().getShadow().setSmoothBlur(2);
        chart.getHeader().getFont().getShadow().setVertSize(1);
            
        chart.getAspect().setView3D(false);
        
        for (int t = 0; t < chart.getAxes().getCount(); ++t) {
            doChangeAxis(chart.getAxes().getAxis(t));
        }
        
        for (int t = 0; t < chart.getSeriesCount(); ++t) {
            doChangeSeries(chart.getSeries(t));
        }
            
        ColorPalettes.applyPalette(chart, Theme.MetroPalette);
    }

    private void doChangeAxis(Axis axis) {        
        axis.getTitle().getFont().setSize(13);
        axis.getTitle().getFont().setName("Roboto Th");
        
        axis.getGrid().setColor(Color.fromArgb(14540253));
        
        if (axis != chart.getAxes().getLeft()) {
            axis.getGrid().setVisible(false);
        }
        
        axis.getLabels().getFont().setColor(Color.GRAY);
        axis.getLabels().getFont().setSize(24);
        axis.getLabels().getFont().setName("Roboto Th");
        
        axis.getAxisPen().setWidth(0);
        axis.getAxisPen().setVisible(false);
        
        axis.getMinorTicks().setVisible(false);
        axis.getTicksInner().setVisible(false);
        
        axis.getTicks().setLength(7);
        axis.getTicks().setColor(Color.fromArgb(8355712));
    }

    public void doChangeSeries(Series series) {
        series.setColorEach(true);
        
        series.getMarks().getFont().setName("Roboto Th");
        series.getMarks().getFont().setSize(24);
        series.getMarks().setTransparent(true);
        series.getMarks().getArrow().setVisible(false);
        series.getMarks().getCallout().getArrow().setVisible(false);
        series.getMarks().getCallout().setArrowHead(ArrowHeadStyle.LINE);
        series.getMarks().getCallout().setDistance(8);
        series.getMarks().getCallout().setLength(0);
    }

    private void doChangeWall(Wall wall) {
        wall.setVisible(false);
        wall.setColor(Color.WHITE);
        wall.getPen().setColor(Color.GRAY);
    }
}

