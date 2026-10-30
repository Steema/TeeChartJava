package com.steema.teechart.drawing;

import java.io.Serializable;
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
public class Color extends android.graphics.Color implements Serializable {

    private static final long serialVersionUID = 618526816881161077L;

    private boolean empty;
    private boolean none;

	private int color;

    private Color(boolean empty, boolean none) {
        this.empty = empty;
        this.none = none;
    }

    public Color() {
        this(true, false);
    }

    public Color(int r, int g, int b, int alpha) {
        super();
        color = android.graphics.Color.argb(alpha, r, g, b);
    }

    public Color(int r, int g, int b) {
        this(r, g, b, 255);
    }

    public Color(final int value) {
        super();
        color = android.graphics.Color.argb(255, red(value), green(value), blue(value));
    }
    
    public Color(final Color value) {
        this(value.color);
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

    static public final Color EMPTY = new Color(true, false);

    static public final Color transparent = new Color(false, true);
    static public final Color TRANSPARENT = transparent;

    static public Color fromArgb(int value) {
        return new Color(value);
    }

    static public Color fromArgb(int value, final Color c) {
        return new Color(c.getRed(), c.getGreen(), c.getBlue(), value);
    }

    static public Color fromArgb(int a, int r, int g, int b) {
        return new Color(r, g, b, a);
    }
    
    static public Color fromCode(final String nm) {
    	return new Color(android.graphics.Color.parseColor(nm));
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
        final Random r = new Random();
        return new Color((int) (255 * r.nextFloat()), (int) (255 * r.nextFloat()),
                         (int) (255 * r.nextFloat()));
    }

    /**
     * The color black.  In the default sRGB space.
     */
    static public final Color black =  new Color(android.graphics.Color.BLACK);

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
     * The color yellow.  In the default sRGB space.
     */
    static public final Color yellow =  new Color(android.graphics.Color.YELLOW);

    /**
     * The color yellow.  In the default sRGB space.
     */
    static public final Color YELLOW = yellow;

    /**
     * The color red.  In the default sRGB space.
     */
    static public final Color red =  new Color(android.graphics.Color.RED);

    /**
     * The color red.  In the default sRGB space.
     */
    static public final Color RED = red;

    /**
     * The color green.  In the default sRGB space.
     */
    static public final Color green =  new Color(android.graphics.Color.GREEN);

    /**
     * The color green.  In the default sRGB space.
     */
    static public final Color GREEN = green;

    /**
     * The color blue.  In the default sRGB space.
     */
    static public final Color blue =  new Color(android.graphics.Color.BLUE);

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
    static public final Color gray =  new Color(android.graphics.Color.GRAY);

    /**
     * The color gray.  In the default sRGB space.
     */
    static public final Color GRAY = gray;

    /**
     * The color white.  In the default sRGB space.
     */
    static public final Color white =  new Color(android.graphics.Color.WHITE);

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
    static public final Color pink =  new Color(200,200,0,255);

    /**
     * The color pink.  In the default sRGB space.
     */
    static public final Color PINK = pink;

    /**
     * The color orange.  In the default sRGB space.
     */
    static public final Color orange =  new Color(200,0,0,255);

    /**
     * The color orange.  In the default sRGB space.
     */
    static public final Color ORANGE = orange;

    /**
     * The color magenta.  In the default sRGB space.
     */
    static public final Color magenta = new Color(android.graphics.Color.MAGENTA);

    /**
     * The color magenta.  In the default sRGB space.
     */
    static public final Color MAGENTA = magenta;

    /**
     * The color cyan.  In the default sRGB space.
     */
    static public final Color cyan = new Color(android.graphics.Color.CYAN);

    /**
     * The color cyan.  In the default sRGB space.
     */
    static public final Color CYAN = cyan;

    /**
     * The color light gray.  In the default sRGB space.
     */
	public static final Color lightGray =  new Color(android.graphics.Color.LTGRAY);
	
    /**
     * The color light gray.  In the default sRGB space.
     */
	public static final Color LIGHT_GRAY = lightGray;

    /**
     * Returns Color with transparency percentage applied.
     *
     * @param value int
     * @return Color with transparency percentage applied.
     */
    public Color transparentColor(final int value) {
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
     * @see #getAlpha()
     */
    static public int transparencyOf(Color value) {
        final int a = value.getAlpha();
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
    public Color applyDark(final int howMuch) {
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
    public Color applyBright(final int howMuch) {
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

	public int getRGB() {
		return color;
	}

	public int getAlpha() {
		return android.graphics.Color.alpha(color);
	}

	public int getRed() {
		return android.graphics.Color.red(color);
	}

	public int getGreen() {
		return android.graphics.Color.green(color);
	}

	public int getBlue() {
		return android.graphics.Color.blue(color);
	}
}

