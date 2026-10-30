package com.steema.teechart;

import java.io.Serializable;

import com.steema.teechart.drawing.Point;

/**
 * <p>Title: Rectangle class</p>
 * <p/>
 * <p>Description: </p>
 * <p/>
 * <p>Copyright (c) 2005-2019 by Steema Software SL. All Rights Reserved.</p>
 * <p/>
 * <p>Company: Steema Software SL</p>
 */
final public class Rectangle implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    static public final Rectangle EMPTY = new Rectangle();

    public int x;
    public int y;
    public int width;
    public int height;

    public Rectangle(Point location, Dimension size) {
        x = location.x;
        y = location.y;
        width = size.width;
        height = size.height;
    }

    public Rectangle() {
    }

    public Rectangle(int ax, int ay, int awidth, int aheight) {
        x = ax;
        y = ay;
        width = awidth;
        height = aheight;
    }

    public Rectangle(Rectangle r) {
        this(r.x, r.y, r.width, r.height);
    }

    /**
     * Returns the center point of the rectangle
     *
     * @return Point
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
     * @param w
     * @param h
     */
    public void offset(int w, int h) {
        x += w;
        y += h;
    }

    public Point getLocation() {
        return new Point(x, y);
    }

    public void grow(final int i, final int j) {
        x -= i;
        y -= j;
        width += 2 * i;
        height += 2 * j;
    }

    public boolean contains(final int x2, final int y2) {
        return (x2 >= x) && (y2 >= y) && (x2 <= x + width) && (y2 <= y + height);
    }

    public boolean contains(final Point pos) {
        return contains(pos.x, pos.y);
    }

    public void add(final Rectangle r) {
        union(r);
    }

    public void union(final Rectangle r) {
        // TODO Auto-generated method stub
    }

    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
    }

    public static void intersect(final Rectangle r1, final Rectangle r2,
                                 Rectangle dest) {
        dest.x = Math.max(r1.x, r2.x);
        dest.y = Math.max(r1.y, r2.y);
        int x2 = Math.min(r1.x + r1.width, r2.x + r2.width);
        int y2 = Math.min(r1.y + r1.height, r2.y + r2.height);
        dest.width = x2 - dest.x;
        dest.height = y2 - dest.y;
    }

    public boolean isEmpty() {
        return (width <= 0) || (height <= 0);
    }

    public boolean intersects(final Rectangle r) {
        return (x + width >= r.x &&
                y + height >= r.y &&
                x <= r.x + r.width &&
                y <= r.y + r.height);
    }
}

