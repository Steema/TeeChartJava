package com.steema.teechart.themes;

import com.steema.teechart.languages.Language;

import com.steema.teechart.drawing.Color;
import com.steema.teechart.axis.Axis;
import com.steema.teechart.drawing.DashStyle;
import com.steema.teechart.styles.Series;
import com.steema.teechart.IBaseChart;
import com.steema.teechart.drawing.GradientDirection;

/**
 * <p>Title: Opera Theme class</p>
 *
 * <p>Description: TeeChart for Java</p>
 *
 * <p>Copyright (c) 2004-2019 by Steema Software SL. All Rights Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 *
 */
public class OperaTheme extends DefaultTheme {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
     * Description of Classic theme
     *
     * @param c IBaseChart
     */
    public OperaTheme(IBaseChart c) {
        super(c);
    }
    
    public OperaTheme() {
    }

    public String toString() {
        return Language.getString("OperaTheme");
    }

    public void apply() {
        super.apply();

        chart.getPanel().getGradient().setVisible(true);        
        chart.getPanel().getGradient().setEndColor(Color.fromArgb(0xFFFFFFFF));
        chart.getPanel().getGradient().setMiddleColor(Color.fromArgb(0xFFEAEAEA));        
        chart.getPanel().getGradient().setStartColor(Color.fromArgb(0xFFEAEAEA));

        chart.getLegend().getFont().setName("Verdana");
        chart.getLegend().getSymbol().getPen().setVisible(true);

        chart.getWalls().getBack().setTransparent(false);
        chart.getWalls().getBack().getGradient().setDirection(GradientDirection.VERTICAL);
        chart.getWalls().getBack().getGradient().setEndColor(Color.fromArgb(0xFFFFFFFF));
        chart.getWalls().getBack().getGradient().setMiddleColor(Color.EMPTY);             
        chart.getWalls().getBack().getGradient().setStartColor(Color.fromArgb(0xFFEAEAEA));               
        chart.getWalls().getBack().getGradient().setVisible(true);               
        
        chart.getWalls().getRight().setColor(Color.fromArgb(0xFFC0C0C0));
        
        for (int t = 0; t < chart.getAxes().getCount(); ++t) {
            doChangeAxis(chart.getAxes().getAxis(t));
        }

        for (int t = 0; t < chart.getSeriesCount(); ++t) {
            doChangeSeries(chart.getSeries(t));
        }
        
        chart.getHeader().getFont().setName("Verdana");
        chart.getHeader().getFont().setColor(Color.fromArgb(0xFF000080));
        chart.getHeader().getPen().setVisible(true);        
        
        chart.getAspect().setSmoothingMode(true);

    }

    private void doChangeAxis(Axis axis) {
        axis.getAxisPen().setColor(Color.fromArgb(0xFFA9A9A9));
        
        axis.getGrid().setColor(Color.fromArgb(0xFF404040));
        axis.getGrid().setStyle(DashStyle.DASH);
        
        axis.getLabels().getFont().setName("Verdana");
        
        axis.getTicksInner().setColor(Color.fromArgb(0xFFA9A9A9));        
        axis.getTicks().setLength(4);

        axis.getTitle().getFont().setName("Verdana");
    }

    public void doChangeSeries(Series series) {
        series.getMarks().getFont().setName("Verdana");
    }
}

