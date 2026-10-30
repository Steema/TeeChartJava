package com.steema.teechart.themes;

import com.steema.teechart.languages.Language;

import com.steema.teechart.Wall;
import com.steema.teechart.drawing.Color;
import com.steema.teechart.IBaseChart;
import com.steema.teechart.drawing.GradientDirection;

/**
 * <p>Title: XP Theme class</p>
 *
 * <p>Description: TeeChart for Java</p>
 *
 * <p>Copyright (c) 2004-2019 by Steema Software SL. All Rights Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 *
 */
public class XPTheme extends DefaultTheme {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
     * Description of Classic theme
     *
     * @param c IBaseChart
     */
    public XPTheme(IBaseChart c) {
        super(c);
    }

    public String toString() {
        return Language.getString("XPTheme");
    }

    public void apply() {
        super.apply();

        chart.getPanel().getPen().setWidth(3);
        chart.getPanel().getPen().setColor(Color.fromArgb(41, 122, 223));

        chart.getPanel().setBorderRound(0);
        
        chart.getLegend().getShadow().setVisible(true);
        chart.getLegend().getShadow().setSize(5);
        chart.getLegend().getShadow().setColor(Color.BLACK);
        
        chart.getPanel().setColor(Color.WHITE);
        
        chart.getPanel().getGradient().setEndColor(Color.fromArgb(177, 177, 177));
        chart.getPanel().getGradient().setDirection(GradientDirection.BACKDIAGONAL);

        doChangeWall(chart.getWalls().getLeft());
        doChangeWall(chart.getWalls().getRight());
        doChangeWall(chart.getWalls().getBack());
        doChangeWall(chart.getWalls().getBottom());

        chart.getWalls().getBack().setTransparent(false);        

        ColorPalettes.applyPalette(chart, Theme.WindowsXPPalette);
    }

    private void doChangeWall(Wall wall) {
        wall.getGradient().setVisible(true);
        wall.getGradient().setStartColor(Theme.WindowsXPPalette[2]);
        wall.getGradient().setEndColor(Theme.WindowsXPPalette[1]);
    }
}

