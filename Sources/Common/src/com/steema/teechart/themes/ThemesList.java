package com.steema.teechart.themes;

import com.steema.teechart.IBaseChart;

/**
 *
 * <p>Title: </p>
 *
 * <p>Description: ThemesList is a collection of Theme objects.</p>
 *
 * <p>Copyright (c) 2005-2019 by Steema Software SL. All Rights Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 *
 * <p>Example:
 * <pre><font face="Courier" size="4">
 * ThemesList.applyTheme(myChart.getChart(), new
 * ExcelTheme(myChart.getChart()));
 * </font></pre>
 * </p>
 *
 * @see com.steema.teechart.themes.Theme
 */
public final class ThemesList {

    /**
     * Returns the number of registered Chart Theme classes.
     *
     * @return int
     */
    public static int size() {
        return 14;
    }

    /**
     * Returns index'th Theme class in list.
     *
     * @param index int
     * @return Class
     */
    public static Class getTheme(int index) {
        switch (index) {
        case 0:
            return OperaTheme.class;
        case 1:
            return BlackIsBackTheme.class;                        
        case 2:
            return DefaultTheme.class;
        case 3:
            return ExcelTheme.class;
        case 4:
            return ClassicTheme.class;            
        case 5:
            return XPTheme.class;                                    
        case 6:
            return WebTheme.class;                        
        case 7:
            return BusinessTheme.class;
        case 8:
            return BlueSkyTheme.class;
        case 9:
            return GrayscaleTheme.class;
        case 10:
            return FlatTheme.class;
        case 11:
            return LookoutTheme.class;
        case 12:
            return AndrosTheme.class;
        case 13:
            return ReportTheme.class;
        default:
            return null;
        }
    }

    /**
     * Creates a new instance of index'th Theme and applies it to chart.
     *
     * @param chart IBaseChart
     * @param index int
     */
    public static void applyTheme(IBaseChart chart, int index) {
        switch (index) {
        case 0:
            applyTheme(chart, new OperaTheme(chart));
            break;
        case 1:
            applyTheme(chart, new BlackIsBackTheme(chart));
            break;            
        case 2:
            applyTheme(chart, new DefaultTheme(chart));
            break;
        case 3:
            applyTheme(chart, new ExcelTheme(chart));
            break;
        case 4:
            applyTheme(chart, new ClassicTheme(chart));
            break;
        case 5:
            applyTheme(chart, new XPTheme(chart));
            break;
        case 6:
            applyTheme(chart, new WebTheme(chart));
            break;
        case 7:
            applyTheme(chart, new BusinessTheme(chart));
            break;
        case 8:
            applyTheme(chart, new BlueSkyTheme(chart));
            break;
        case 9:
            applyTheme(chart, new GrayscaleTheme(chart));
            break;
        case 10:
            applyTheme(chart, new FlatTheme(chart));
            break;
        case 11:
            applyTheme(chart, new LookoutTheme(chart));
            break;
        case 12:
            applyTheme(chart, new AndrosTheme(chart));
            break;
        case 13:
            applyTheme(chart, new ReportTheme(chart));
            break;
        default:
            break;
        }
    }

    /**
     * Applies Theme to Chart. Color palette is determined by the Theme class.
     *
     * @param chart IBaseChart
     * @param theme Theme
     */
    public static void applyTheme(IBaseChart chart, Theme theme) {
        applyTheme(chart, theme, -1);
    }

    /**
     * Applies Theme to Chart and sets the Chart palette (if paletteIndex is
     * different than -1).
     *
     * @param chart IBaseChart
     * @param theme Theme
     * @param paletteIndex int
     */
    public static void applyTheme(IBaseChart chart, Theme theme,
                             int paletteIndex) {
        theme.apply();

        if (paletteIndex != -1) {
            ColorPalettes.applyPalette(chart, paletteIndex);
        }
    }

    /**
     * Returns the textual description of index'th Theme in list.
     *
     * @param index int
     * @return String
     * @see com.steema.teechart.themes.Theme
     */
    public static String getThemeDescription(int index) {
        switch (index) {
        case 0:
            return "Opera";
        case 1:
            return "BlackIsBack";            
        case 2:
            return "TeeChart";
        case 3:
            return "Excel";
        case 4:
            return "Classic";
        case 5:
            return "XP";
        case 6:
            return "Web";
        case 7:
            return "Business";
        case 8:
            return "Blues";
        case 9:
            return "Grayscale";
        case 10:
            return "Flat";
        case 11:
            return "Lookout";
        case 12:
            return "Andros";
        case 13:
            return "Report";
        default:
            return "";
        }
    }
}

