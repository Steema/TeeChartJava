package com.steema.teechart.themes;

import com.steema.teechart.drawing.Color;
import com.steema.teechart.drawing.DashStyle;
import com.steema.teechart.IBaseChart;
import com.steema.teechart.drawing.Gradient;
import com.steema.teechart.Wall;
import com.steema.teechart.styles.Series;
import com.steema.teechart.axis.Axis;

/**
 *
 * <p>Title: BlackIsBack Theme class</p>
 *
 * <p>Description: Summary description for BlackIsBackTheme.</p>
 *
 * <p>Copyright (c) 2005-2019 by Steema Software SL. All Rights Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 *
 */
@SuppressWarnings("serial")
public class BlackIsBackTheme extends OperaTheme {

	private static final long serialVersionUID = 1L;
	
    public BlackIsBackTheme(IBaseChart c) {
        super(c);
    }

    public void apply() {        
        resetGradient(chart.getPanel().getGradient());
        
        chart.getPanel().getGradient().setEndColor(Color.fromArgb(0xFF464646));
        
        
        changeWall(chart.getWalls().getBack());
        changeWall(chart.getWalls().getBottom());        
        changeWall(chart.getWalls().getLeft());        
        

        chart.getLegend().getFont().setColor(Color.fromArgb(0xFFFFFFFF));
        
        resetGradient(chart.getLegend().getGradient());
                
        chart.getLegend().getShadow().setWidth(0);
                
        chart.getLegend().getPen().setVisible(false);
        
        
        chart.getHeader().getFont().setColor(Color.fromArgb(0xFFFFFFFF));
                

        for (int t = 0; t < chart.getAxes().getCount(); ++t) {
            changeAxis(chart.getAxes().getAxis(t));
        }      
        
        
        for (int t = 0; t < chart.getSeriesCount(); ++t) {
            doChangeSeries(chart.getSeries(t));
        }


        ColorPalettes.applyPalette(chart, Theme.OnBlackPalette);
    }

    /**
     * Gets descriptive text.
     *
     * @return String
     */
    public String getDescription() {
        return "BlackIsBack";
    }

    public void resetGradient(Gradient chartGradient) {
        chartGradient.setVisible(true);
        chartGradient.setStartColor(Color.fromArgb(0xFF464646));
        chartGradient.setEndColor( Color.fromArgb(0xFF787878));
        chartGradient.setMiddleColor(Color.fromArgb(0x464646));
    }

    public void changeWall(Wall chartWall) {
        chartWall.getPen().setVisible(false);
        
        resetGradient(chartWall.getGradient());
    }   

    public void changeAxis(Axis chartAxis) {
        chartAxis.getAxisPen().setColor(Color.fromArgb(0xFF828282));
        
        chartAxis.getGrid().setColor(Color.fromArgb(0xFF828282));
        chartAxis.getGrid().setStyle(DashStyle.SOLID);
        
        chartAxis.getLabels().getFont().setColor(Color.fromArgb(0xFFFFFFFF));
                
        chartAxis.getTicks().setColor(Color.fromArgb(0xFF828282));
        
        chartAxis.getMinorTicks().setVisible(false);
    }
    
    public void doChangeSeries(Series series) {
        series.getMarks().getFont().setColor(Color.fromArgb(0xFFFFFFFF));
        series.getMarks().setTransparent(true);
    }    
}

