/*
 * LegendAdapter.java
 *
 */

package com.steema.teechart.legend;

import com.steema.teechart.Rectangle;

/**
 * <p>Title: TeeChart for Java</p>
 *
 * <p>Description: TeeChart for Java</p>
 *
 * <p>Copyright  (c) 2004-2019 by Steema Software SL. All Rights Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 *
 */
public class LegendAdapter implements LegendResolver {

    /** Creates a new instance of LegendAdapter */
    public LegendAdapter() {}

    public Rectangle getBounds(Legend legend, Rectangle rectangle) {
        return rectangle;
    };

    public LegendItemCoordinates getItemCoordinates(Legend legend, LegendItemCoordinates coordinates) {
        return coordinates;
    };

    public String getItemText(Legend legend, LegendStyle legendStyle, int index, String text) {
        return text;
    };
}

