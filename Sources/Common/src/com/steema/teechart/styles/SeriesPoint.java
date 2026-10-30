package com.steema.teechart.styles;

import com.steema.teechart.drawing.Color;

/**
 *
 * <p>Title: SeriesPoint class</p>
 *
 * <p>Description: Class provides access to Point characteristics</p>
 *
 * <p>Copyright (c) 2005-2019 by Steema Software SL. All Rights
 * Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 *
 */
public class SeriesPoint {
    protected Series series;
    protected int index;

    public SeriesPoint() {}

    public SeriesPoint(Series s, int i) {
        index = i;
        series = s;
    }

    /**
     * Indexed Point Color
     *
     * @return Color
     */
    public Color getColor() {
        return series.getColors().getColor(index);
    }

    /**
     * Indexed Point Color
     *
     * @param value Color
     */
    public void setColor(Color value) {
        series.getColors().setColor(index, value);
        series.invalidate();
    }

    /**
     * Indexed Point Label
     *
     * @return String
     */
    public String getLabel() {
        return series.getLabels().getString(index);
    }

    /**
     * Indexed Point Label
     *
     * @param value String
     */
    public void setLabel(String value) {
        series.getLabels().setString(index, value);
        series.invalidate();
    }
}

