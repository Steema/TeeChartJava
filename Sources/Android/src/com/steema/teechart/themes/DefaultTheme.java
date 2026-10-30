package com.steema.teechart.themes;

import com.steema.teechart.drawing.Color;
import com.steema.teechart.drawing.DashStyle;
import com.steema.teechart.BevelStyle;
import com.steema.teechart.IBaseChart;
import com.steema.teechart.drawing.ChartFont;
import com.steema.teechart.drawing.Gradient;
import com.steema.teechart.drawing.GradientDirection;
import com.steema.teechart.drawing.SystemColor;
import com.steema.teechart.Wall;
import com.steema.teechart.styles.Series;
import com.steema.teechart.axis.Axis;

/**
 *
 * <p>Title: DefaultTheme class</p>
 *
 * <p>Description: Summary description for DefaultTheme.</p>
 *
 * <p>Copyright (c) 2005-2019 by Steema Software SL. All Rights Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 *
 */
public class DefaultTheme extends Theme {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public DefaultTheme(IBaseChart c) {
        super(c);
    }

    public void apply() {
        
        //Panel Settings
        chart.getPanel().getBevel().setInner(BevelStyle.NONE);
        chart.getPanel().getBevel().setOuter( BevelStyle.RAISED);
        chart.getPanel().getBevel().setWidth( 1);        
        
        chart.getPanel().setBorderRound( 0);
        chart.getPanel().setColor(SystemColor.getControlColor());       

        chart.getPanel().getPen().setVisible( false);
        chart.getPanel().getPen().setColor(Color.BLACK);
        chart.getPanel().getPen().setStyle(DashStyle.SOLID);
        chart.getPanel().getPen().setWidth(1);
        
        chart.getPanel().getShadow().setSize(0);
        chart.getPanel().getShadow().setVisible(false);
        chart.getPanel().getShadow().setColor(Color.DARK_GRAY);

        resetGradient(chart.getPanel().getGradient());

        
        //Legend Settings
        chart.getLegend().getDividingLines().setVisible( false);
        chart.getLegend().getDividingLines().setColor(Color.BLACK);
        
        chart.getLegend().getFont().setName("Arial");
        chart.getLegend().getFont().setColor(Color.BLACK);
        chart.getLegend().getFont().setSize(8);
       
        chart.getLegend().getPen().setVisible(true);
        chart.getLegend().getPen().setWidth(1);
        chart.getLegend().getPen().setStyle(DashStyle.SOLID);
        
        chart.getLegend().getShadow().setHeight( 3);
        chart.getLegend().getShadow().setWidth(3);
        chart.getLegend().getShadow().setTransparency( 0);        
        chart.getLegend().getShadow().setColor(Color.BLACK);
        
        chart.getLegend().getSymbol().setDefaultPen(true);
        chart.getLegend().getSymbol().getPen().setVisible(false);
        chart.getLegend().getSymbol().setSquared(false);
        
        chart.getLegend().setTransparent(false);
        
        resetGradient(chart.getLegend().getGradient());


        //Walls Settings
        changeWall(chart.getWalls().getLeft(), Color.LIGHT_YELLOW);
        changeWall(chart.getWalls().getRight(), Color.SILVER);
        changeWall(chart.getWalls().getBack(), Color.SILVER);
        changeWall(chart.getWalls().getBottom(), Color.WHITE);

        chart.getWalls().getBack().setTransparent(true);
        
        
        //Axes Settings
        for (int t = 0; t < chart.getAxes().getCount(); ++t) {
            changeAxis(chart.getAxes().getAxis(t));
        }
        
//        chart.getAxes().getBottom().getGrid().setVisible(false);
//        chart.getAxes().getTop().getGrid().setVisible(false);
        
       
        //Series Settings
        for (int t = 0; t < chart.getSeriesCount(); ++t) {
            changeSeries(chart.getSeries(t));
        }
        
        
        //Header Settings      
        chart.getHeader().setColor(Color.SILVER);
        chart.getHeader().setTransparency(0);
        
        chart.getHeader().getFont().setBold(false);
        chart.getHeader().getFont().setColor(Color.BLUE);
        chart.getHeader().getFont().setName("Arial");
        chart.getHeader().getFont().setSize(8);
        
        resetGradient(chart.getHeader().getGradient());
        
        chart.getHeader().getPen().setColor(Color.BLACK);        
        chart.getHeader().getPen().setVisible(false);
        chart.getHeader().getPen().setWidth(1);
        
        chart.getHeader().getShadow().setSize(0);
        chart.getHeader().getShadow().setTransparency(0);
        
        chart.getAspect().setSmoothingMode(true);
       

        //ColorPalette Settings
        ColorPalettes.applyPalette(chart, Theme.OperaPalette);
    }

    /**
     * Gets descriptive text.
     *
     * @return String
     */
    public String getDescription() {
        return "TeeChart default";
    }


    public void resetGradient(Gradient chartGradient) {        
        chartGradient.setVisible( false);        
        chartGradient.setDirection( GradientDirection.VERTICAL);
        chartGradient.setStartColor( Color.WHITE);
        chartGradient.setEndColor( Color.YELLOW);
        chartGradient.setMiddleColor( Color.EMPTY);
    }

    public void changeWall(Wall chartWall, Color aColor) {
        chartWall.setApplyDark(true);
        chartWall.setColor(aColor);        
        chartWall.setSize(0);
        
        resetGradient(chartWall.getGradient());
        
        chartWall.getPen().setVisible( true);
        chartWall.getPen().setColor( Color.BLACK);
        chartWall.getPen().setWidth( 1);
        chartWall.getPen().setStyle( DashStyle.SOLID);
    }

    public void changeSeries(Series chartSeries) {
        chartSeries.getMarks().setTransparent(false);
        
        resetGradient(chartSeries.getMarks().getGradient());
        
        chartSeries.getMarks().getFont().setName("Arial");
        chartSeries.getMarks().getFont().setSize(8);
        chartSeries.getMarks().getFont().setColor(Color.BLACK);
        
        chartSeries.getMarks().getArrow().setColor(Color.WHITE);
    }

    public void changeAxis(Axis chartAxis) {
        chartAxis.getAxisPen().setWidth(2);
        chartAxis.getAxisPen().setColor(Color.BLACK);
        
        chartAxis.getGrid().setVisible(true);
        chartAxis.getGrid().setColor(Color.GRAY);
        chartAxis.getGrid().setStyle(DashStyle.DOT);
        chartAxis.getGrid().setCentered(false);
        chartAxis.getGrid().setWidth(1);
        
        ChartFont f=chartAxis.getLabels().getFont();
        f.setName( "Arial");
        f.setSize( 8);
        f.setColor( Color.BLACK);
        f.setBold(false);
        
        chartAxis.getMinorGrid().setVisible( false);
        chartAxis.getMinorGrid().setColor(Color.BLACK);
        
        chartAxis.getTicks().setColor(Color.DARK_GRAY);
        chartAxis.getTicks().setLength( 2);
        
        chartAxis.setMinorTickCount(3);
        chartAxis.getMinorTicks().setLength(2);                        
        chartAxis.getMinorTicks().setVisible(true);        
        
        chartAxis.getTicksInner().setVisible( true);        
        chartAxis.getTicksInner().setLength( 0);
        chartAxis.getTicksInner().setColor(Color.BLACK);
        
        chartAxis.getTitle().getFont().setName( "Arial");
        chartAxis.getTitle().getFont().setColor(Color.BLACK);
    }

	public void doChangeSeries(Series series) {
		// TODO Auto-generated method stub
	}
}

