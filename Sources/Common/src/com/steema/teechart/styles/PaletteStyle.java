package com.steema.teechart.styles;

import com.steema.teechart.misc.Enum;

/**
 *
 * <p>Title: PaletteStyle class</p>
 *
 * <p>Description: Describes the possible values of
 * Custom3DPalette.PaletteStyle.</p>
 *
 * <p>Copyright (c) 2005-2019 by Steema Software SL. All Rights Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 *
 *
 * @see com.steema.teechart.styles.Custom3DPalette#getPaletteStyle
 */
final public class PaletteStyle extends Enum {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
     * Defines a pale palette.
     */
    public static final PaletteStyle PALE = new PaletteStyle(0);
    /**
     * Defines a strong palette.
     */
    public static final PaletteStyle STRONG = new PaletteStyle(1);
    /**
     * Defines a grayscale palette.
     */
    public static final PaletteStyle GRAYSCALE = new PaletteStyle(2);
    /**
     * Defines an inverted grayscale palette.
     */
    public static final PaletteStyle INVERTED_GRAYSCALE = new PaletteStyle(3);
    /**
     * Defines a rainbow palette.
     */
    public static final PaletteStyle RAINBOW = new PaletteStyle(4);

    private PaletteStyle(int value) {
        super(value);
    }
}


