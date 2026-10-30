package com.steema.teechart.themes;

import com.steema.teechart.languages.Language;

import com.steema.teechart.drawing.Color;
import com.steema.teechart.BevelStyle;
import com.steema.teechart.styles.Series;
import com.steema.teechart.IBaseChart;

/**
 * <p>Title: Business Theme class</p>
 *
 * <p>Description: TeeChart for Java</p>
 *
 * <p>Copyright (c) 2004-2019 by Steema Software SL. All Rights Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 *
 */
@SuppressWarnings("serial")
public class BusinessTheme extends DefaultTheme {

	private static final long serialVersionUID = 1L;
    /**
     * Description of Classic theme
     *
     * @param c IBaseChart
     */
    public BusinessTheme(IBaseChart c) {
        super(c);
    }

    public String toString() {
        return Language.getString("BusinessTheme");
    }

    public void apply() {
        super.apply();
        
        chart.getPanel().getBevel().setOuter(BevelStyle.NONE);        
        chart.getPanel().setBorderRound(10);
        
        chart.getPanel().getPen().setVisible(true);
        chart.getPanel().getPen().setWidth(6);
        chart.getPanel().getPen().setColor(Color.NAVY);
        chart.getPanel().getGradient().setVisible(true);
        chart.getPanel().getGradient().setEndColor(Color.DARK_GRAY);
        
        chart.getLegend().getShadow().setSize(3);
        chart.getLegend().getGradient().setVisible(true);
        
        chart.getWalls().getLeft().setColor(Color.fromArgb(255, 255, 128));
        chart.getWalls().getRight().setColor(Color.SILVER);
        chart.getWalls().getBack().setColor(Color.SILVER);
        chart.getWalls().getBottom().setColor(Color.WHITE);
        
        for (int t = 0; t < chart.getSeriesCount(); ++t) {
            doChangeSeries(chart.getSeries(t));
        }

        ColorPalettes.applyPalette(chart, Theme.VictorianPalette);
    }

    public void doChangeSeries(Series series) {
        series.getMarks().getGradient().setVisible(true);
        series.getMarks().getGradient().setStartColor(Color.SILVER);
    }
}

