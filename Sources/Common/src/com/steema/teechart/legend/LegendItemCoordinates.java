package com.steema.teechart.legend;

/**
 *
 * <p>Title: LegendItemCoordinates class</p>
 *
 * <p>Description: Legend Position.</p>
 *
 * <p>Copyright (c) 2005-2019 by Steema Software SL. All Rights
 * Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 *
 *
 *
 *
 */
public class LegendItemCoordinates {

    public int xColor;
    private int idx;
    private int x, y;

    public LegendItemCoordinates(int index, int x, int y, int xColor) {
        super();
        this.idx = index;
        this.x = x;
        this.y = y;
        this.xColor = xColor;
    }

    public LegendItemCoordinates(LegendItemCoordinates c) {
        super();
        this.idx = c.getIndex();
        this.x = c.getX();
        this.y = c.getY();
        this.xColor = c.getXColor();
    }

    /**
     * The Index of the Legend item.
     *
     * @return int
     */
    public int getIndex() {
        return idx;
    }
    /**
     * The specific X coordinate of the Legend item.
     *
     * @return int
     */
    public int getX() {
        return x;
    }

    /**
     * Sts the specific X coordinate of the Legend item.
     *
     * @param value int
     */
    public void setX(int value) {
        x = value;
    }
    /**
     * The specific Y coordinate of the Legend item.
     *
     * @return int
     */
    public int getY() {
        return y;
    }

    /**
     * Sets the specific Y coordinate of the Legend item.
     *
     * @param value int
     */
    public void setY(int value) {
        y = value;
    }
    /**
     * The specific XColor coordinate of the Legend item.
     *
     * @return int
     */
    public int getXColor() {
        return xColor;
    }

    /**
     * Sets the specific XColor coordinate of the Legend item.
     *
     * @param value int
     */
    public void setXColor(int value) {
        xColor = value;
    }
}

