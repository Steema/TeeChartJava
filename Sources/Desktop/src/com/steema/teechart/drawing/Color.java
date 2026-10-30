package com.steema.teechart.drawing;

import java.util.Random;
import com.steema.teechart.misc.Utils;

/**
 * <p>Title: Color class</p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright (c) 2005-2019 by Steema Software SL. All Rights Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 *
 */
final public class Color extends java.awt.Color {

    private static final long serialVersionUID = 618526816881161077L;

    private boolean empty;
    private boolean none;

    private Color(boolean empty,boolean none) {
        super(0,0,0,0);
        this.empty = empty;
        this.none = none;
    }

    public Color() {
        super(0);
    }

    public Color(int r, int g, int b, int alpha) {
        super(r, g, b, alpha);
    }

    public Color(int r, int g, int b) {
        super(r, g, b, 255);
    }

    public Color(int value) {
        super(value);
    }
    
    public java.awt.Color toAWT() {
        return new java.awt.Color(getRGB());
    }

    public Color(java.awt.Color value) {
        super(value.getRGB());
    }

    public boolean isEmpty() {
        return empty;
    }

    /**
     * Returns true when the Color equals to EMPTY color. <br>
     * EMPTY Color means objects will not fill drawing elements.
     *
     * @return Color
     */
    public boolean getEmpty() {
        return empty;
    }

    public void setEmpty(boolean value) {
        empty = value;
    }

    public boolean isNull() {
      return none;
    }

    public boolean getNone() {
        return none;
    }

    public void setNone(boolean value) {
        none = value;
    }

    static public final Color EMPTY = new Color(true,false);

    static public final Color transparent = new Color(false,true);
    static public final Color TRANSPARENT = transparent;

    static public Color fromArgb(int value) {
        return new Color(value);
    }

    static public Color fromArgb(int value, Color c) {
        return new Color(c.getRed(), c.getGreen(), c.getBlue(), value);
    }

    static public Color fromArgb(int a, int r, int g, int b) {
        return new Color(r, g, b, a);
    }
    
    static public Color fromCode(String nm) {
        return new Color(Color.decode(nm).getRed(),
                         Color.decode(nm).getGreen(),Color.decode(nm).getBlue(),Color.decode(nm).getAlpha());
    }

    static public Color fromArgb(int r, int g, int b) {
        return new Color(r, g, b);
    }

    /**
     * Creates a new Color instance with random RED, GREEN and BLUE color
     * components.
     *
     *
     *
     *
     * @return Color
     */
    static public Color random() {
        Random r = new Random();
        return new Color((int) (255 * r.nextFloat()), (int) (255 * r.nextFloat()),
                         (int) (255 * r.nextFloat()));
    }

    /**
     * The color black.  In the default sRGB space.
     */
    static public final Color black = new Color(java.awt.Color.black);

    /**
     * The color black.  In the default sRGB space.
     */
    static public final Color BLACK = black;

    /**
     * The color dark gray.  In the default sRGB space.
     */
    static public final Color darkGray = new Color(169, 169, 169);

    /**
     * The color dark gray.  In the default sRGB space.
     */
    static public final Color DARK_GRAY = darkGray;
    
    /**
     * The color light gray.  In the default sRGB space.
     */
    static public final Color lightGray = new Color(java.awt.Color.lightGray);

    /**
     * The color light gray.  In the default sRGB space.
     */
    static public final Color LIGHT_GRAY = lightGray;

    /**
     * The color yellow.  In the default sRGB space.
     */
    static public final Color yellow = new Color(java.awt.Color.yellow);

    /**
     * The color yellow.  In the default sRGB space.
     */
    static public final Color YELLOW = yellow;

    /**
     * The color red.  In the default sRGB space.
     */
    static public final Color red = new Color(java.awt.Color.red);

    /**
     * The color red.  In the default sRGB space.
     */
    static public final Color RED = red;

    /**
     * The color green.  In the default sRGB space.
     */
    static public final Color green = new Color(java.awt.Color.green);

    /**
     * The color green.  In the default sRGB space.
     */
    static public final Color GREEN = green;

    /**
     * The color blue.  In the default sRGB space.
     */
    static public final Color blue = new Color(java.awt.Color.blue);

    /**
     * The color blue.  In the default sRGB space.
     */
    static public final Color BLUE = blue;

    /**
     * The color silver.  In the default sRGB space.
     */
    static public final Color silver = new Color(192, 192, 192);

    /**
     * The color silver.  In the default sRGB space.
     */
    static public final Color SILVER = silver;

    /**
     * The color gray.  In the default sRGB space.
     */
    static public final Color gray = new Color(java.awt.Color.gray);

    /**
     * The color gray.  In the default sRGB space.
     */
    static public final Color GRAY = gray;

    /**
     * The color white.  In the default sRGB space.
     */
    static public final Color white = new Color(java.awt.Color.white);

    /**
     * The color white.  In the default sRGB space.
     */
    static public final Color WHITE = white;

    /**
     * The color white smoke.  In the default sRGB space.
     */
    static public final Color whiteSmoke = new Color(245, 245, 245);

    /**
     * The color white smoke.  In the default sRGB space.
     */
    static public final Color WHITE_SMOKE = whiteSmoke;

    /**
     * The color navy.  In the default sRGB space.
     */
    static public final Color navy = new Color(0, 0, 128);

    /**
     * The color navy.  In the default sRGB space.
     */
    static public final Color NAVY = navy;

    /**
     * The color gold.  In the default sRGB space.
     */
    static public final Color gold = new Color(255, 215, 0);

    /**
     * The color gold.  In the default sRGB space.
     */
    static public final Color GOLD = gold;

    /**
     * The color light yellow.  In the default sRGB space.
     */
    static public final Color lightYellow = new Color(255, 255, 224);

    /**
     * The color light yellow.  In the default sRGB space.
     */
    static public final Color LIGHT_YELLOW = lightYellow;

    /**
     * The color fuchsia.  In the default sRGB space.
     */
    static public final Color fuchsia = new Color(255, 0, 255);

    /**
     * The color fuchsia.  In the default sRGB space.
     */
    static public final Color FUCHSIA = fuchsia;

    /**
     * The color teal.  In the default sRGB space.
     */
    static public final Color teal = new Color(0, 128, 128);

    /**
     * The color teal.  In the default sRGB space.
     */
    static public final Color TEAL = teal;

    /**
     * The color maroon.  In the default sRGB space.
     */
    static public final Color maroon = new Color(128, 0, 0);

    /**
     * The color maroon.  In the default sRGB space.
     */
    static public final Color MAROON = maroon;

    /**
     * The color lime.  In the default sRGB space.
     */
    static public final Color lime = new Color(0, 255, 0);

    /**
     * The color lime.  In the default sRGB space.
     */
    static public final Color LIME = lime;

    /**
     * The color olive.  In the default sRGB space.
     */
    static public final Color olive = new Color(128, 128, 0);

    /**
     * The color olive.  In the default sRGB space.
     */
    static public final Color OLIVE = olive;

    /**
     * The color purple.  In the default sRGB space.
     */
    static public final Color purple = new Color(128, 0, 128);

    /**
     * The color purple.  In the default sRGB space.
     */
    static public final Color PURPLE = purple;

    /**
     * The color aqua.  In the default sRGB space.
     */
    static public final Color aqua = new Color(0, 255, 255);

    /**
     * The color aqua.  In the default sRGB space.
     */
    static public final Color AQUA = aqua;

    /**
     * The color green yellow.  In the default sRGB space.
     */
    static public final Color greenYellow = new Color(173, 255, 47);

    /**
     * The color green yellow.  In the default sRGB space.
     */
    static public final Color GREEN_YELLOW = greenYellow;

    /**
     * The color sky blue.  In the default sRGB space.
     */
    static public final Color skyBlue = new Color(135, 206, 235);

    /**
     * The color sky blue.  In the default sRGB space.
     */
    static public final Color SKY_BLUE = skyBlue;

    /**
     * The color bisque.  In the default sRGB space.
     */
    static public final Color bisque = new Color(255, 228, 196);

    /**
     * The color bisque.  In the default sRGB space.
     */
    static public final Color BISQUE = bisque;

    /**
     * The color indigo.  In the default sRGB space.
     */
    static public final Color indigo = new Color(75, 0, 130);

    /**
     * The color indigo.  In the default sRGB space.
     */
    static public final Color INDIGO = indigo;

    /**
     * The color pink.  In the default sRGB space.
     */
    static public final Color pink = new Color(java.awt.Color.pink);

    /**
     * The color pink.  In the default sRGB space.
     */
    static public final Color PINK = pink;

    /**
     * The color orange.  In the default sRGB space.
     */
    static public final Color orange = new Color(java.awt.Color.orange);

    /**
     * The color orange.  In the default sRGB space.
     */
    static public final Color ORANGE = orange;

    /**
     * The color magenta.  In the default sRGB space.
     */
    static public final Color magenta = new Color(java.awt.Color.magenta);

    /**
     * The color magenta.  In the default sRGB space.
     */
    static public final Color MAGENTA = magenta;

    /**
     * The color cyan.  In the default sRGB space.
     */
    static public final Color cyan = new Color(java.awt.Color.cyan);

    /**
     * The color cyan.  In the default sRGB space.
     */
    static public final Color CYAN = cyan;

    /**
     * Returns Color with transparency percentage applied.
     *
     * @param value int
     * @return Color with transparency percentage applied.
     */
    public Color transparentColor(int value) {
        return new Color(getRed(), getGreen(), getBlue(),
                         Utils.round(((100 - value) * 2.55)));
    }

    /**
     * Returns an integer from 0 to 100 that is the percent of transparency
     * of this color.
     *
     * @param value Color
     * @return int
     * @see #transparentColor
     * @see java.awt.Color#getAlpha
     */
    static public int transparencyOf(Color value) {
        int a = value.getAlpha();
        return (a == 255) ? 0 : (a == 0) ? 100 : (int) (0.39216 * (255 - a));
    }

    /**
     * Converts the Color parameter to a darker color.<br> The HowMuch
     * parameter indicates the quantity of dark increment.<br>
     * It is used by Bar Series and Pie Series to calculate the right color t
     * to draw Bar sides and Pie 3D zones.
     *
     * @param howMuch int
     * @return Color
     */
    public Color applyDark(int howMuch) {
        int r = getRed();
        if (r > howMuch) {
            r -= howMuch;
        } else {
            r = 0;
        }

        int g = getGreen();
        if (g > howMuch) {
            g -= howMuch;
        } else {
            g = 0;
        }

        int b = getBlue();
        if (b > howMuch) {
            b -= howMuch;
        } else {
            b = 0;
        }

        return new Color(r, g, b, getAlpha());
    }

    /**
     * Converts the Color parameter to a brighter color.<br>
     * The HowMuch parameter indicates the quantity of bright increment.<br>
     * It is used by Styles.Bar Series and Styles.Pie Series to calculate the
     * right color to draw Bar sides and Pie 3D zones.
     *
     * @param howMuch int
     * @return Color
     */
    public Color applyBright(int howMuch) {
        int r = getRed();
        if ((r + howMuch) < 256) {
            r += howMuch;
        } else {
            r = 255;
        }

        int g = getGreen();
        if ((g + howMuch) < 256) {
            g += howMuch;
        } else {
            g = 255;
        }

        int b = getBlue();
        if ((b + howMuch) < 256) {
            b += howMuch;
        } else {
            b = 255;
        }

        return new Color(r, g, b, getAlpha());
    }
}

