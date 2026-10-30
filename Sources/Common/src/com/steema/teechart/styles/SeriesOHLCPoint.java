package com.steema.teechart.styles;

/**
 *
 * <p>Title: SeriesOHLCPoint class</p>
 *
 * <p>Description: Class provides access to XYZ Point characteristics</p>
 *
 * <p>Copyright (c) 2005-2019 by Steema Software SL. All Rights
 * Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 *
 */
public class SeriesOHLCPoint extends SeriesPoint {
    private OHLC ohlc;

    public SeriesOHLCPoint() {}

    public SeriesOHLCPoint(OHLC s, int i) {
        index = i;
        series=s;
        ohlc = s;
    }

    /**
     * Indexed Point Open value.<br>
     * You can access Open values in the same way you can access X or Y values.
     *
     * @return double
     */
    public double getOpen() {
        return ohlc.getOpenValues().value[index];
    }

    /**
     * Indexed Point Open value.<br>
     *
     * @param value double
     */
    public void setOpen(double value) {
        ohlc.getOpenValues().value[index] = value;
        ohlc.invalidate();
    }

    /**
     * Indexed Point High value.<br>
     * You can access High values in the same way you can access X or Y values.
     *
     * @return double
     */
    public double getHigh() {
        return ohlc.getHighValues().value[index];
    }

    /**
     * Indexed Point High value.<br>
     *
     * @param value double
     */
    public void setHigh(double value) {
        ohlc.getHighValues().value[index] = value;
        ohlc.invalidate();
    }

    /**
     * Indexed Point Low value.<br>
     * You can access Low values in the same way you can access X or Y values.
     *
     * @return double
     */
    public double getLow() {
        return ohlc.getLowValues().value[index];
    }

    /**
     * Indexed Point Low value.<br>
     *
     * @param value double
     */
    public void setLow(double value) {
        ohlc.getLowValues().value[index] = value;
        ohlc.invalidate();
    }

    /**
     * Indexed Point Close value.<br>
     * You can access Close values in the same way you can access X or Y values.
     *
     * @return double
     */
    public double getClose() {
        return ohlc.getCloseValues().value[index];
    }

    /**
     * Indexed Point Close value.<br>
     *
     * @param value double
     */
    public void setClose(double value) {
        ohlc.getCloseValues().value[index] = value;
        ohlc.invalidate();
    }

    /**
     * Indexed Point Date value.<br>
     * You can access Date values in the same way you can access X or Y values.
     *
     * @return double
     */
    public double getDate() {
        return ohlc.getDateValues().value[index];
    }

    /**
     * Indexed Point Date value.<br>
     *
     * @param value double
     */
    public void setDate(double value) {
        ohlc.getDateValues().value[index] = value;
        ohlc.invalidate();
    }
}

