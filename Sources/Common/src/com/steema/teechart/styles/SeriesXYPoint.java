package com.steema.teechart.styles;

/**
 *
 * <p>Title: SeriesXYPoint class</p>
 *
 * <p>Description: Class provides access to XY Point characteristics</p>
 *
 * <p>Copyright (c) 2005-2019 by Steema Software SL. All Rights
 * Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 *
 */
public class SeriesXYPoint extends SeriesPoint {
    public SeriesXYPoint() {}

    public SeriesXYPoint(Series s, int i) {
        index = i;
        series = s;
    }

    /**
     * Indexed Point x value.<br><br>
     * example<br><br>
     * line1.Points[3].x=50;<br><br>
     * Sets the 2nd X value of the Line Series to 50
     *
     * @return double
     */
    public double getX() {
        return series.getXValues().value[index];
    }

    /**
     * Indexed Point x value.<br><br>
     *
     * @param value double
     */
    public void setX(double value) {
        series.getXValues().value[index] = value;
        series.invalidate();
    }

    /**
     * Indexed Point Y value.
     *
     * @return double
     */
    public double getY() {
        return series.getYValues().value[index];
    }

    /**
     * Indexed Point Y value.
     *
     * @param value double
     */
    public void setY(double value) {
        series.getYValues().value[index] = value;
        series.invalidate();
    }
}

