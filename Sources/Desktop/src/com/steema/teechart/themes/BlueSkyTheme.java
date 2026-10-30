package com.steema.teechart.themes;

import com.steema.teechart.drawing.Color;
import com.steema.teechart.drawing.DashStyle;
import com.steema.teechart.BevelStyle;
import com.steema.teechart.IBaseChart;
import com.steema.teechart.drawing.ChartFont;
import com.steema.teechart.drawing.Gradient;
import com.steema.teechart.drawing.GradientDirection;
import com.steema.teechart.Wall;
import com.steema.teechart.axis.Axis;

/**
 *
 * <p>Title: BlueSkyTheme class</p>
 *
 * <p>Description: Summary description for BlueSkyTheme.</p>
 *
 * <p>Copyright (c) 2005-2019 by Steema Software SL. All Rights Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 *
 */
@SuppressWarnings("serial")
public class BlueSkyTheme extends DefaultTheme {

	private static final long serialVersionUID = 1L;
	
    public BlueSkyTheme(IBaseChart c) {
        super(c);
    }
    
    public BlueSkyTheme() {}

    public void apply() {
        chart.getLegend().getShadow().setVisible(true);
        chart.getPanel().getShadow().setColor(Color.BLACK);        
        
        chart.getPanel().getBevel().setInner(BevelStyle.LOWERED);        
        chart.getPanel().getBevel().setOuter( BevelStyle.NONE);        
        chart.getPanel().getBevel().setWidth(2);
        
        changeWall(chart.getWalls().getBack(),  Color.fromArgb(0xFFFCECCF));
        changeWall(chart.getWalls().getBottom(), Color.fromArgb(0xFF038CFC));        
        changeWall(chart.getWalls().getLeft(), Color.fromArgb(0xFF8080FF));
        
        resetGradient(chart.getPanel().getGradient());
        
        chart.getLegend().getDividingLines().setColor(Color.SILVER);
        chart.getLegend().getDividingLines().setVisible(true);
        chart.getLegend().getFont().setColor(Color.fromArgb(0xFF000064));
        chart.getLegend().getGradient().setDirection(GradientDirection.HORIZONTAL);
        chart.getLegend().getGradient().setEndColor(Color.fromArgb(0xFFFFDBCE));
        chart.getLegend().getGradient().setMiddleColor(Color.fromArgb(0xFFE9E6E0));
        chart.getLegend().getGradient().setStartColor(Color.fromArgb(0xFFEAF3FF));
        chart.getLegend().getGradient().setVisible(true);
        chart.getLegend().getShadow().setHeight(5);
        chart.getLegend().getShadow().setWidth(4);
        chart.getLegend().getShadow().setTransparency(50);
        chart.getLegend().getSymbol().setSquared(true);
        
        chart.getHeader().setColor(Color.BLACK);
        chart.getHeader().setTransparency(70);
        chart.getHeader().getFont().setColor(Color.NAVY);
        chart.getHeader().getFont().setSize(12);
        chart.getHeader().getPen().setVisible(true);
        chart.getHeader().getPen().setWidth(2);
        chart.getHeader().getPen().setColor(Color.fromArgb(0xFFFBDD99));
        chart.getHeader().getGradient().setEndColor(Color.BLACK);
        chart.getHeader().getGradient().setMiddleColor(Color.fromArgb(0xFF400080));
        chart.getHeader().getGradient().setStartColor(Color.WHITE);
        chart.getHeader().getGradient().setVisible(true);
        chart.getHeader().getShadow().setSize(4);
        chart.getHeader().getShadow().setTransparency(70);

        for (int t = 0; t < chart.getAxes().getCount(); ++t) {
            changeAxis(chart.getAxes().getAxis(t));
        }       

        ColorPalettes.applyPalette(chart, Theme.PastelsPalette);
        //ColorPalettes.applyPalette(chart, 1);
    }

    /**
     * Gets descriptive text.
     *
     * @return String
     */
    public String getDescription() {
        return "Blues";
    }

    public void resetGradient(Gradient chartGradient) {
        chartGradient.setVisible(true);
        chartGradient.setStartColor(Color.fromArgb(0xFF2003A5));
        chartGradient.setEndColor( Color.WHITE);
        chartGradient.setMiddleColor(Color.fromArgb(0xFF80FFFF));
    }

    public void changeWall(Wall chartWall, Color aColor) {
        chartWall.getPen().setVisible(false);
        chartWall.setColor(aColor);
        chartWall.setApplyDark(true);
        chartWall.setSize(5);
    }   

    public void changeAxis(Axis chartAxis) {
        chartAxis.getAxisPen().setWidth(1);
        chartAxis.getAxisPen().setColor(Color.NAVY);
        
        chartAxis.getGrid().setColor(Color.fromArgb(0xFFB9B9FF));
        chartAxis.getGrid().setStyle(DashStyle.DOT);
        
        ChartFont f=chartAxis.getLabels().getFont();
        f.setName("Tahoma");
        f.setBold(true);
        f.setColor(Color.NAVY);
        
        chartAxis.getMinorGrid().setVisible(false);
        chartAxis.getMinorGrid().setColor(Color.fromArgb(0xFFE5E5E5));        
        chartAxis.setMinorTickCount(7);
        
        chartAxis.getTicks().setLength(5);
        
        chartAxis.getGrid().setColor(Color.BLUE);
        chartAxis.getGrid().setStyle(DashStyle.DOT);
    }
}

