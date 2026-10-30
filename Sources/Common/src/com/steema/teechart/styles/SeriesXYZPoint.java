package com.steema.teechart.styles;

/**
 *
 * <p>Title: SeriesXYZPoint class</p>
 *
 * <p>Description: Class provides access to XYZ Point characteristics</p>
 *
 * <p>Copyright (c) 2005-2019 by Steema Software SL. All Rights
 * Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 *
 */
public class SeriesXYZPoint extends SeriesXYPoint {

    protected Custom3D custom3D;

    public SeriesXYZPoint() {}

    public SeriesXYZPoint(Custom3D s, int i) {
        index = i;
        custom3D = s;
        series = s;
    }

    /**
     * Indexed Point Z value
     *
     * @return double
     */
    public double getZ() {
        return custom3D.getZValues().value[index];
    }

    /**
     * Indexed Point Z value
     *
     * @param value double
     */
    public void setZ(double value) {
        custom3D.getZValues().value[index] = value;
        custom3D.invalidate();
    }
}



