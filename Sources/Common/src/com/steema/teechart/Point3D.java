package com.steema.teechart;

/**
 *
 * <p>Title: Point3D class</p>
 *
 * <p>Description: XYZ Point holder</p>
 *
 * <p>Copyright (c) 2005-2019 by Steema Software SL. All Rights Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 *
 */
public class Point3D {

    public int x;
    public int y;
    public int z;

    public Point3D() {}

    public Point3D(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    /**
     * The X location in pixels.
     *
     * @return int
     */
    public int getX() {
        return x;
    }

    /**
     * Sets the X location in pixels.
     *
     * @param value int
     */
    public void setX(int value) {
        x = value;
    }

    /**
     * The Y location in pixels.
     *
     * @return int
     */
    public int getY() {
        return y;
    }

    /**
     * Sets the Y location in pixels.
     *
     * @param value int
     */
    public void setY(int value) {
        y = value;
    }

    /**
     * The Z location in pixels.
     *
     * @return int
     */
    public int getZ() {
        return z;
    }

    /**
     * Sets the Z location in pixels.
     *
     * @param value int
     */
    public void setZ(int value) {
        z = value;
    }
}

