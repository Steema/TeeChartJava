package com.steema.teechart.misc;

import com.steema.teechart.drawing.Point;

/**
 * <p>Title: MathUtils class</p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright (c) 2005-2019 by Steema Software SL. All Rights
 * Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 *
 */
public class MathUtils {

    static public final double PISTEP = Math.PI / 180.0;

    static public final double sqr(double x) {
        return x * x;
    }

    /**
     * Note: This function has been added to MathUtils because J2ME Math
     * class does not include it.
     *
     * Returns Math.log of value parameter.
     *
     * @param value double
     * @return double
     */
    public static double log(double value) {
        return Math.log(value);
    }

    static public double log(double x, double numBase) {
        return Math.log(x) / Math.log(numBase);
    }

    // Note: This function is implemented here instead of using Math.round
    // because J2ME Math class does not include it (as far as in CLDC 1.1)

    /**
     * Returns the integer nearest to "value" parameter
     *
     * @param value double
     * @return int
     */
    public static int round(double value) {
        return (int) Math.floor(value + 0.5d);
    }

    static public Point pointAtDistance(Point aFrom, Point aTo, int aDist) {

        final Point res = new Point(aTo.x, aTo.y);

        if (aFrom.x != aTo.x) {
            double tmp = MathUtils.atan2((aTo.y - aFrom.y), (aTo.x - aFrom.x));

            double tmpSin = Math.sin(tmp);
            double tmpCos = Math.cos(tmp);

            res.x -= MathUtils.round(aDist * tmpCos);
            res.y -= MathUtils.round(aDist * tmpSin);
        } else {
            if (aTo.y < aFrom.y) {
                res.y += aDist;
            } else {
                res.y -= aDist;
            }
        }

        return res;
    }

    static public double calcDistance(Point p, int x0, int y0, int x1, int y1) {

        double dx, dy;

        if ((x1 == x0) && (y1 == y0)) {

            dx = p.x - x0;
            dy = p.y - y0;

            return Math.sqrt(dx * dx + dy * dy);

        } else {

            dx = x1 - x0;
            dy = y1 - y0;

            double tmpResult;
            tmpResult = ((p.x - x0) * dx + (p.y - y0) * dy) /
                        (dx * dx + dy * dy);

            if (tmpResult < 0) {
                dx = p.x - x0;
                dy = p.y - y0;
            } else if (tmpResult > 1) {
                dx = p.x - x1;
                dy = p.y - y1;
            } else {
                dx = p.x - (x0 + tmpResult * dx);
                dy = p.y - (y0 + tmpResult * dy);
            }

            return Math.sqrt(dx * dx + dy * dy);
        }
    }

    static public boolean pointInLineTolerance(Point p, int x0, int y0, int x1,
                                               int y1, int tolerance) {
        if (((p.x == x0) && (p.y == y0)) || ((p.x == x1) && (p.y == y1))) {
            return true;
        } else {
            return Math.abs(calcDistance(p, x0, y0, x1, y1)) <= tolerance++;
        }
    }

    /**
     * Note: This code has been copied from Java JDK Double.compare method,
     * because J2ME Double class does not include it.
     *
     * Compares Double d1 with d2. Returns 0 if both are equal.
     * Returns -1 if d1 is lower than d2, and 1 if d1 is bigger than d2.
     *
     * @param d1 double
     * @param d2 double
     * @return int
     */
    public static int compareDoubles(double d1, double d2) {
        if (d1 < d2) {
            return -1; // Neither val is NaN, thisVal is smaller
        }
        if (d1 > d2) {
            return 1; // Neither val is NaN, thisVal is larger
        }

        long thisBits = Double.doubleToLongBits(d1);
        long anotherBits = Double.doubleToLongBits(d2);

        return (thisBits == anotherBits ? 0 : // Values are equal
                (thisBits < anotherBits ? -1 : // (-0.0, 0.0) or (!NaN, NaN)
                 1)); // (0.0, -0.0) or (NaN, !NaN)
    }

    /**
     * Note: This method has been implemented here in MathUtils class
     * because J2ME Math class does not include it.
     *
     * Returns Math.atan2.
     *
     * @param y double
     * @param x double
     * @return double
     */
    public static double atan2(double y, double x) {
        return Math.atan2(y,x);
    }

    /**
     * Note: This method has been implemented here in MathUtils class
     * because J2ME Math class does not include it.
     *
     * Returns Math.pow.
     *
     * @param a double
     * @param b double
     * @return double
     */
    public static double pow(double a, double b) {
        return Math.pow(a,b);
    }

    /**
     * Note: This method has been implemented here in MathUtils class
     * because J2ME Math class does not include it.
     *
     * Returns Math.exp.
     *
     * @param a double
     * @return double
     */
    public static double exp(double a) {
        return Math.exp(a);
    }
    
    /**
     * Note: This method has been implemented here in MathUtils class
     * because J2ME Math class does not include it.
     *
     * Truncate a number to the next nearest integer towards 0
     *
     * @param d double
     * @return double
     */
    public static double trunc(double d) {
        return (double)((long)d);
    }

    public static Point calcPoint(double angle, Point center, double radiusX,
                            double radiusY) {

        return new Point(center.x + round(radiusX * Math.cos(angle)),
                center.y - round(radiusY * Math.sin(angle)));
    }
}

