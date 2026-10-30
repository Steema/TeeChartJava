package com.steema.teechart.themes;

import com.steema.teechart.TeeBase;
import com.steema.teechart.IBaseChart;
import com.steema.teechart.drawing.Color;
import com.steema.teechart.styles.Series;

/**
 *
 * <p>Title: Theme class</p>
 *
 * <p>Description: Summary description for Theme.</p>
 *
 * <p>Copyright (c) 2005-2019 by Steema Software SL. All Rights Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 *
 */
public abstract class Theme extends TeeBase {
	
	private static final long serialVersionUID = 1L;

    public Theme() {
        this((IBaseChart)null);
    }

    public Theme(IBaseChart c) {
          super(c);
        if (c!=null){
          c.setInternalTheme(this);
        }
    }

    public abstract void apply();

    /**
     * Gets descriptive text.
     *
     * @return String
     */
    public String getDescription() {
        return "Default";
    }

    public Theme themeSelector(IBaseChart custom) {
        return null;
    }

    private class ThemeSelectorDelegate {
        public Theme invoke(IBaseChart chart) {
            return null;
        }
    }
    
    public abstract void doChangeSeries(Series series);

    public ThemeSelectorDelegate themeSelectorHook = null;
    public ThemeSelectorDelegate newChartHook = null;
    
    public final static Color[] DefaultPalette = {
                                                 Color.RED,
                                                 Color.GREEN,
                                                 Color.YELLOW,
                                                 Color.BLUE,
                                                 Color.WHITE,
                                                 Color.GRAY,
                                                 Color.FUCHSIA,
                                                 Color.TEAL,
                                                 Color.NAVY,
                                                 Color.MAROON,
                                                 Color.LIME,
                                                 Color.OLIVE,
                                                 Color.PURPLE,
                                                 Color.SILVER,
                                                 Color.AQUA,
                                                 Color.BLACK,
                                                 Color.GREEN_YELLOW,
                                                 Color.SKY_BLUE,
                                                 Color.BISQUE,
                                                 Color.INDIGO
    };
    
    public final static Color[] OperaPalette = {
                                               Color.fromArgb(0xFF4266A3), //blue
                                               Color.fromArgb(0xFFF39C35), //orange
                                               Color.fromArgb(0xFFF14C14), //red
                                               Color.fromArgb(0xFF4E97A8), //blue
                                               Color.fromArgb(0xFF2B406B), //blue
                                               Color.fromArgb(0xFF1D7B63), //blue-green dark
                                               Color.fromArgb(0xFFB3080E), //dark red
                                               Color.fromArgb(0xFFF2C05D), //pale orange
                                               Color.fromArgb(0xFF5DB79E), //blue-green
                                               Color.fromArgb(0xFF707070), //grey
                                               Color.fromArgb(0xFFF3EA8D), //yellow
                                               Color.fromArgb(0xFFB4B4B4) //grey
    };
    
    public final static Color[] LookoutPalette = {
        Color.fromArgb(119, 153, 214), //blue
        Color.fromArgb(255, 207, 104), //orange
        Color.fromArgb(255, 127, 71), //red
        Color.fromArgb(129, 202, 219), //blue
        Color.fromArgb(94, 115, 158), //blue
        Color.fromArgb(80, 174, 150), //blue-green dark
        Color.fromArgb(230, 59, 65), //dark red
        Color.fromArgb(255, 243, 144), //pale orange
        Color.fromArgb(144, 234, 209), //blue-green
        Color.fromArgb(163, 163, 163), //grey
        Color.fromArgb(255, 255, 192), //yellow
        Color.fromArgb(231, 231, 231) //grey
    };
        
    public final static Color[] SeawashPalette = {
        Color.fromArgb(0xFFDC5C05),
        Color.fromArgb(0xFFFFAC00),
        Color.fromArgb(0xFF6EC5B8),
        Color.fromArgb(0xFFE8D0A9),
        Color.fromArgb(0xFF978B7D),
        Color.fromArgb(0xFFC7BAA7),
        Color.fromArgb(0xFFC1DAD6),
        Color.fromArgb(0xFFFFC99F),
        Color.fromArgb(0xFFACD1E9),
        Color.fromArgb(0xFF6D929B),
        Color.fromArgb(0xFFD3E397),
        Color.fromArgb(0xFFFFF5C3)
    };
            
    public final static Color[] OnBlackPalette = {
                                               Color.fromArgb(200, 230, 90),
                                               Color.fromArgb(90, 150, 220),
                                               Color.fromArgb(230, 90, 40),
                                               Color.fromArgb(230, 160, 15)
    };

    public final static Color[] MetroPalette = {
                                                Color.fromArgb(0xFFA200FF),
                                                Color.fromArgb(0xFFFF0097),
                                                Color.fromArgb(0xFF00ABA9),
                                                Color.fromArgb(0xFF8CBF26),
                                                Color.fromArgb(0xFFA05000),
                                                Color.fromArgb(0xFFE671B8),
                                                Color.fromArgb(0xFFF09609),
                                                Color.fromArgb(0xFF1BA1E2),
                                                Color.fromArgb(0xFFE51400),
                                                Color.fromArgb(0xFF339933)
    };
        
    public final static Color[] iOSPalette = {
                                            Color.fromArgb(0xFF48CCFD),
                                            Color.fromArgb(0xFF86CD4D),
                                            Color.fromArgb(0xFF9A9A9A),
                                            Color.fromArgb(0xFFF7D284),
                                            Color.fromArgb(0xFF465F98),
                                            Color.fromArgb(0xFFA65F41),
                                            Color.fromArgb(0xFF9C7B48),
                                            Color.fromArgb(0xFF8C61D2),
                                            Color.fromArgb(0xFF758EAC),
                                            Color.fromArgb(0xFFFF8904),
                                            Color.fromArgb(0xFFE2393E),
                                            Color.fromArgb(0xFFB5D197)
    };
    
    public final static Color[] AndroidPalette = {
                                                Color.fromArgb(0xFF33B5E5),
                                                Color.fromArgb(0xFFAA66CC),
                                                Color.fromArgb(0xFF99CC00),
                                                Color.fromArgb(0xFFFFBB33),
                                                Color.fromArgb(0xFFFF4444),
                                                Color.fromArgb(0xFF0099CC),
                                                Color.fromArgb(0xFF9933CC),
                                                Color.fromArgb(0xFF669900),
                                                Color.fromArgb(0xFFFF8800),
                                                Color.fromArgb(0xFFCC0000),
                                                Color.fromArgb(0xFF88C4E5),
                                                Color.fromArgb(0xFFCFB1DE),
                                                Color.fromArgb(0xFFCCFF00),
                                                Color.fromArgb(0xFFFFEC91),
                                                Color.fromArgb(0xFFFFBBBB)
    };
    
    public final static Color[] CoolPalette = {
                                               Color.fromArgb(0xFF2B406B), //dark blue
                                               Color.fromArgb(0xFF3B548C), //blue
                                               Color.fromArgb(0xFF4466A3), //blue
                                               Color.fromArgb(0xFF4E97A8), //blue
                                               Color.fromArgb(0xFF5DB79E), //blue-green
                                               Color.fromArgb(0xFF41A08A), //blue-green
                                               Color.fromArgb(0xFF2B927D), //blue-green
                                               Color.fromArgb(0xFF1D7B63) //blue-green dark
    };
    
    public final static Color[] WarmPalette = {
                                               Color.fromArgb(0xFFF3EA8D), //yellow
                                               Color.fromArgb(0xFFF2C05D), //pale orange
                                               Color.fromArgb(0xFFF39C35), //orange
                                               Color.fromArgb(0xFFF5811C), //orange
                                               Color.fromArgb(0xFFF66B15), //dark orange
                                               Color.fromArgb(0xFFF14C14), // red orange
                                               Color.fromArgb(0xFFE6180A), // red
                                               Color.fromArgb(0xFFB3080E) //dark red
    };

    public final static Color[] MacOSPalette = {
                                               Color.fromArgb(0xFFFFFFFF),
                                               Color.fromArgb(0xFFFCF305),
                                               Color.fromArgb(0xFFFF6402),
                                               Color.fromArgb(0xFFDD0806),
                                               Color.fromArgb(0xFFF20884),
                                               Color.fromArgb(0xFF4600A5),
                                               Color.fromArgb(0xFF0000D4),
                                               Color.fromArgb(0xFF02ABEA),
                                               Color.fromArgb(0xFF1FB714),
                                               Color.fromArgb(0xFF006411),
                                               Color.fromArgb(0xFF562C05),
                                               Color.fromArgb(0xFF90713A),
                                               Color.fromArgb(0xFFC0C0C0),
                                               Color.fromArgb(0xFF808080),
                                               Color.fromArgb(0xFF404040),
                                               Color.fromArgb(0xFF000000)
    };
    
    public final static Color[] WindowsVistaPalette = {
                                               Color.fromArgb(0xFF001FD2),
                                               Color.fromArgb(0xFFE00201),
                                               Color.fromArgb(0xFF1E6602),
                                               Color.fromArgb(0xFFE8CD7E),
                                               Color.fromArgb(0xFFAFABAC),
                                               Color.fromArgb(0xFFA4D0D9),
                                               Color.fromArgb(0xFF3D3B3C),
                                               Color.fromArgb(0xFF95DD31),
                                               Color.fromArgb(0xFF9E0001),
                                               Color.fromArgb(0xFFDCF774),
                                               Color.fromArgb(0xFF45FDFD),
                                               Color.fromArgb(0xFFD18E74),
                                               Color.fromArgb(0xFFA0D891),
                                               Color.fromArgb(0xFFD57A65),
                                               Color.fromArgb(0xFF9695D9)
    };    

    public final static Color[] ExcelPalette = {
                                               Color.fromArgb(0xFFFF9999),
                                               Color.fromArgb(0xFF663399),
                                               Color.fromArgb(0xFFCCFFFF),
                                               Color.fromArgb(0xFFFFFFCC),
                                               Color.fromArgb(0xFF660066),
                                               Color.fromArgb(0xFF8080FF),
                                               Color.fromArgb(0xFFCC6600),
                                               Color.fromArgb(0xFFFFCCCC),
                                               Color.fromArgb(0xFF800000),
                                               Color.fromArgb(0xFFFF00FF),
                                               Color.fromArgb(0xFF00FFFF),
                                               Color.fromArgb(0xFFFFFF00),
                                               Color.fromArgb(0xFF800080),
                                               Color.fromArgb(0xFF000080),
                                               Color.fromArgb(0xFF808000),
                                               Color.fromArgb(0xFFFF0000),
                                               Color.fromArgb(0xFFFFCC00),
                                               Color.fromArgb(0xFFFFFFCC),
                                               Color.fromArgb(0xFFCCFFCC),
                                               Color.fromArgb(0xFF00FFFF),
                                               Color.fromArgb(0xFFFFCC99),
                                               Color.fromArgb(0xFFCC99FF)
    };

    public final static Color[] VictorianPalette = {

            Color.fromArgb(0xFFA1A55D),
            Color.fromArgb(0xFF3153C4),
            Color.fromArgb(0xFF0996E7),
            Color.fromArgb(0xFF4AE8F6),
            Color.fromArgb(0xFFA7A2B1),
            Color.fromArgb(0xFF84A7C9),
            Color.fromArgb(0xFF51798C),
            Color.fromArgb(0xFFB7CDD8),
            Color.fromArgb(0xFF536508),
            Color.fromArgb(0xFF7BD8F7),
            Color.fromArgb(0xFF846401)
    };

    public final static Color[] PastelsPalette = {

                                                 Color.fromArgb(0xFFFFFFCC),
                                                 Color.fromArgb(0xFFCCFFFF),
                                                 Color.fromArgb(0xFFFFCCCC),
                                                 Color.fromArgb(0xFFCCCC00),
                                                 Color.fromArgb(0xFFCCCCCC),
                                                 Color.fromArgb(0xFF999900),
                                                 Color.fromArgb(0xFF999999),
                                                 Color.fromArgb(0xFFCCCCFF),
                                                 Color.fromArgb(0xFF66CCFF),
                                                 Color.fromArgb(0xFFFFCCCC),
                                                 Color.fromArgb(0xFF9999FF),
                                                 Color.fromArgb(0xFF99FFFF),
                                                 Color.fromArgb(0xFFFFCC99),
                                                 Color.fromArgb(0xFFCCFFCC)
    };

    public final static Color[] SolidPalette = {

                                               Color.fromArgb(0xFFFF0000),
                                               Color.fromArgb(0xFF0000FF),
                                               Color.fromArgb(0xFF00FF00),
                                               Color.fromArgb(0xFF00CCFF),
                                               Color.fromArgb(0xFF404040),
                                               Color.fromArgb(0xFF00FFFF),
                                               Color.fromArgb(0xFFC000FF),
                                               Color.fromArgb(0xFFFFFFFF)
    };

    public final static Color[] ClassicPalette = {

                                                 Color.fromArgb(0xFFFF0000),
                                                 Color.fromArgb(0xFF00FF00),
                                                 Color.fromArgb(0xFFFFFF00),
                                                 Color.fromArgb(0xFF0000FF),
                                                 Color.fromArgb(0xFFFF00FF),
                                                 Color.fromArgb(0xFF00FFFF),
                                                 Color.fromArgb(0xFF800000),
                                                 Color.fromArgb(0xFF008000),
                                                 Color.fromArgb(0xFF808000),
                                                 Color.fromArgb(0xFF000080),
                                                 Color.fromArgb(0xFF800080),
                                                 Color.fromArgb(0xFF008080)
    };

    public final static Color[] WebPalette = {

                                             Color.fromArgb(0xFF00A5FF),
                                             Color.fromArgb(0xFFCE0000),
                                             Color.fromArgb(0xFF00CE00),
                                             Color.fromArgb(0xFF40FFFF),
                                             Color.fromArgb(0xFFFFFF40),
                                             Color.fromArgb(0xFFFF40FF),
                                             Color.fromArgb(0xFF0040FF),
                                             Color.fromArgb(0xFFA58080),
                                             Color.fromArgb(0xFF408080)
    };

    public final static Color[] ModernPalette = {

                                                Color.fromArgb(0xFF6699FF),
                                                Color.fromArgb(0xFF6666FF),
                                                Color.fromArgb(0xFFFFCC99),
                                                Color.fromArgb(0xFF669966),
                                                Color.fromArgb(0xFF99CCCC),
                                                Color.fromArgb(0xFFCC6699),
                                                Color.fromArgb(0xFF6666CC),
                                                Color.fromArgb(0xFF99CCFF),
                                                Color.fromArgb(0xFFFF6699),
                                                Color.fromArgb(0xFFCCCCCC),
                                                Color.fromArgb(0xFFCCFF66),
                                                Color.fromArgb(0xFFFF9966),
                                                Color.fromArgb(0xFF996699),
                                                Color.fromArgb(0xFFFFCCCC)
    };

    public final static Color[] RainbowPalette = {

                                                 Color.fromArgb(0xFF000099),
                                                 Color.fromArgb(0xFF0000C3),
                                                 Color.fromArgb(0xFF0000EE),
                                                 Color.fromArgb(0xFF001AFF),
                                                 Color.fromArgb(0xFF0046FF),
                                                 Color.fromArgb(0xFF0073FF),
                                                 Color.fromArgb(0xFF009FFF),
                                                 Color.fromArgb(0xFF00CBFF),
                                                 Color.fromArgb(0xFF00F7FF),
                                                 Color.fromArgb(0xFF08F4E3),
                                                 Color.fromArgb(0xFF11E7C3),
                                                 Color.fromArgb(0xFF1BDAA3),
                                                 Color.fromArgb(0xFF25CD83),
                                                 Color.fromArgb(0xFF2EC063),
                                                 Color.fromArgb(0xFF38B342),
                                                 Color.fromArgb(0xFF42A622),
                                                 Color.fromArgb(0xFF4B9A02),
                                                 Color.fromArgb(0xFF6A870C),
                                                 Color.fromArgb(0xFF8A751A),
                                                 Color.fromArgb(0xFFAA6328),
                                                 Color.fromArgb(0xFFCB5036),
                                                 Color.fromArgb(0xFFEB3E44),
                                                 Color.fromArgb(0xFFFF2A61),
                                                 Color.fromArgb(0xFFFF1596),
                                                 Color.fromArgb(0xFFFF00CC)
    };

    public final static Color[] WindowsXPPalette = {

            Color.fromArgb(0xFFFE9B82),
            Color.fromArgb(0xFF24D1FC),
            Color.fromArgb(0xFF0DBC7C),
            Color.fromArgb(0xFF2F85FD),
            Color.fromArgb(0xFFFCFEFD),
            Color.fromArgb(0xFF214EE2),
            Color.fromArgb(0xFFD63829),
            Color.fromArgb(0xFF0094B7),
            Color.fromArgb(0xFF00865A),
            Color.fromArgb(0xFF0046D2),
            Color.fromArgb(0xFFFAE5D3),
            Color.fromArgb(0xFFD8D8D8),
            Color.fromArgb(0xFF7B715F)
    };
    
    public final static Color[] GrayscalePalette = {

            Color.fromArgb(0xFFF0F0F0),
            Color.fromArgb(0xFFE0E0E0),
            Color.fromArgb(0xFFD0D0D0),
            Color.fromArgb(0xFFC0C0C0),
            Color.fromArgb(0xFFB0B0B0),
            Color.fromArgb(0xFFA0A0A0),
            Color.fromArgb(0xFF909090),
            Color.fromArgb(0xFF808080),
            Color.fromArgb(0xFF707070),
            Color.fromArgb(0xFF606060),
            Color.fromArgb(0xFF505050),
            Color.fromArgb(0xFF404040),
            Color.fromArgb(0xFF303030),
            Color.fromArgb(0xFF202020),
            Color.fromArgb(0xFF101010)
    };    

}

