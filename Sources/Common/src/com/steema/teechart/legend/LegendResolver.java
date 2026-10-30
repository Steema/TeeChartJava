/*
 * LegendResolver.java
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
public interface LegendResolver {
    Rectangle getBounds(Legend legend, Rectangle rectangle); //TODO Check: is this on total legend, or only item?

    LegendItemCoordinates getItemCoordinates(Legend legend, LegendItemCoordinates coordinates);

    String getItemText(Legend legend, LegendStyle legendStyle, int index, String text);
}

