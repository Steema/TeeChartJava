package com.steema.teechart;

import com.steema.teechart.drawing.Point;

/**
 *
 * <p>Title: Rectangle class</p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright (c) 2005-2019 by Steema Software SL. All Rights Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 *
 */
final public class Rectangle extends java.awt.Rectangle {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	static public final Rectangle EMPTY = new Rectangle();

    public Rectangle(Point location, Dimension size) {
        super(location.x, location.y, size.width, size.height);
    }

    public Rectangle() {
        super(0, 0, 0, 0);
    }

    public Rectangle(int x, int y, int width, int height) {
        super(x, y, width, height);
    }

    public Rectangle(Rectangle r) {
        super(r.x, r.y, r.width, r.height);
    }
    
    public Rectangle(java.awt.Rectangle r) {
    	super(r.x, r.y, r.width, r.height);
	}

/**
 * Returns the center point of the rectangle
 * 
 * @return
 */
    public Point center() {
        return new Point(x + (width / 2), y + (height / 2));
    }

    public Rectangle copy() {
        return new Rectangle(x, y, width, height);
    }

    /**
     * Gets rectangle Left coordinate.
     *
     * @return int
     */
    public int getLeft() {
        return x;
    }

    /**
     * Sets rectangle Left coordinate.
     *
     */
    public void setLeft(int value) {
        width += x - value;
        x = value;
    }

    /**
     * Gets rectangle Right coordinate.
     *
     * @return int
     */
    public int getRight() {
        return x + width;
    }


    /**
     * Sets rectangle Right coordinate.
     *
     */
    public void setRight(int value) {
        width = value - x;
    }


    /**
     * Gets rectangle Top coordinate.
     *
     * @return int
     */
    public int getTop() {
        return y;
    }

    /**
     * Sets rectangle Top coordinate.
     *
     */
    public void setTop(int value) {
        height += y - value;
        y = value;
    }

    /**
     * Gets rectangle Bottom coordinate.
     *
     * @return int
     */
    public int getBottom() {
        return y + height;
    }

    /**
     * Sets rectangle Bottom coordinate.
     *
     */
    public void setBottom(int value) {
        height = value - y;
    }

    static public Rectangle fromLTRB(int left, int top, int right, int bottom) {
        if (left > right) {
            int tmp = right;
            right = left;
            left = tmp;
        }

        if (top > bottom) {
            int tmp = bottom;
            bottom = top;
            top = tmp;
        }
        
        return new Rectangle(left, top, right - left, bottom - top);
    }

    public void inflate(int horizontal, int vertical) {
        
        x += -horizontal;
        y += -vertical;
        width += (horizontal * 2);
        height += (vertical * 2);
    }

    public void intersect(Rectangle value) {
        intersect(this, value, this);
    }
/**
 * 
 * 
 * @param w
 * @param h
 */
    public void offset(int w, int h) {
        x += w;
        y += h;
    }

    public Point getLocation() {
    	return new Point(x,y);
    }
}

