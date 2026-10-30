package com.steema.teechart.themes;

import com.steema.teechart.IBaseChart;
import com.steema.teechart.drawing.Color;

/**
 *
 * <p>Title: ColorPalettes class</p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright (c) 2005-2019 by Steema Software SL. All Rights Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 *
 * <p>Example:
 * <pre><font face="Courier" size="4">
 * ColorPalettes.applyPalette(myChart.getChart(), index);
 * </font></pre></p>
 *
 */
public class ColorPalettes {

    public static final Color[][] Palettes = {
        Theme.DefaultPalette,
        Theme.OperaPalette,
        Theme.ExcelPalette,
        Theme.VictorianPalette,
        Theme.PastelsPalette,
        Theme.SolidPalette,
        Theme.ClassicPalette,
        Theme.WebPalette,
        Theme.ModernPalette,
        Theme.RainbowPalette,
        Theme.WindowsXPPalette,
        Theme.MacOSPalette,
        Theme.WindowsVistaPalette,
        Theme.CoolPalette,
        Theme.WarmPalette,
        Theme.GrayscalePalette,
        Theme.OnBlackPalette,
        Theme.MetroPalette,
        Theme.iOSPalette,
        Theme.AndroidPalette,
        Theme.LookoutPalette,
        Theme.SeawashPalette
    };

    public static final String[] PaletteNames = {
       "TeeChart", //"Default"       
       "Opera",
       "Excel",
       "Victorian",
       "Pastels",
       "Solid",
       "Classic",
       "Web",
       "Modern",
       "Rainbow",
       "Windows XP",       
       "MacOS",
       "Windows Vista",       
       "Cool",
       "Warm",
       "Grayscale",
       "OnBlack",
       "Metro",
       "iOS",
       "Android",
       "Lookout",
       "Seawash"
    };

    public static void applyPalette(IBaseChart custom, int index) {
        applyPalette(custom, Palettes[index]);
    }

    public static void applyPalette(IBaseChart custom, Color[] palette) {
        custom.getGraphics3D().setColorPalette(palette);
        custom.invalidate();
    }
}

