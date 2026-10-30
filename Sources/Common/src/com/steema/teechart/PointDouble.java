/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.steema.teechart;

import com.steema.teechart.drawing.Point;

/**
 *
 * @author Administrator
 */
public class PointDouble {

    public double x;
    public double y;

        /**
     * Constructs and initializes a point at the origin 
     * (0,&nbsp;0) of the coordinate space. 
     * @since       1.1
     */
    public PointDouble() {
	    this(0, 0);
    }

    public PointDouble(final double x, final double y) {
        this.x = x;
        this.y = y;
    }

    /**
     * The X location in pixels.
     *
     * @return double
     */
    public double getX() {
        return x;
    }

    /**
     * Sets the X location in pixels.
     *
     * @param value double
     */
    public void setX(final double value) {
        x = value;
    }

    /**
     * The Y location in pixels.
     *
     * @return double
     */
    public double getY() {
        return y;
    }

    /**
     * Sets the Y location in pixels.
     *
     * @param value double
     */
    public void setY(final double value) {
        y = value;
    }
    
    /*
     * convert PointDouble to rounded value Point
     */
    public static Point round(final PointDouble value)
    {
      return new Point((int)value.x, (int)value.y);
      
      // return new Point(Math.round((float)value.x), Math.round((float)value.y));
    }
    
    /*
     * convert PointDouble[] to rounded value Point[]
     */
    public static Point[] round(final PointDouble[] value)
    {
    	return round(value, value.length);
    }
    /*
     * convert PointDouble[] to rounded value Point[]
     */
    public static Point[] round(final PointDouble[] value, int size)
    {
      final Point[] result = new Point[size];
      
      for (int i = 0; i < size; i++)
        result[i] = PointDouble.round(value[i]);

      return result;
    }
}
