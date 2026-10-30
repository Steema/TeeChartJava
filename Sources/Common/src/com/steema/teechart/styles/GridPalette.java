package com.steema.teechart.styles;

import com.steema.teechart.drawing.Color;

/**
 *
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright (c) 2005-2019 by Steema Software SL. All Rights
 * Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 *
 */
public final class GridPalette {
    /**
     * Used in Custom3DPalette series to store the palette values.
     */
    public double upToValue;
    public Color color;

    public GridPalette(double aValue, Color aColor) {
        upToValue = aValue;
        color = aColor;
    }
}


