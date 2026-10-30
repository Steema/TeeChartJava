package com.steema.teechart.printer;

import com.steema.teechart.TeeBase;
import com.steema.teechart.IBaseChart;

/**
 *
 * <p>Title: PrintMargins class</p>
 *
 * <p>Description: Access to each Page Print Margin</p>
 *
 * <p>Copyright (c) 2005-2019 by Steema Software SL. All Rights
 * Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 *
 */
public class PrintMargins extends TeeBase {
	
	private static final long serialVersionUID = 1L;

    private int left, top, right, bottom;

    /**
     * Sets Left, Top, Right and Bottom Margins expressed as a percentage of
     * the paper dimendisions or as standard 1/100 inch printer units.
     *
     * @see PrintMarginUnits class PrintMarginUnits
     * @see Printer#getProportional
     *
     * @param l int
     * @param t int
     * @param r int
     * @param b int
     */
    public PrintMargins(IBaseChart chart, int l, int t, int r, int b) {
        super(chart);
        left = l;
        top = t;
        right = r;
        bottom = b;
    }

    /**
     * Left margin as percentage of paper dimensions.
     *
     * @return int
     */
    public int getLeft() {
        return left;
    }

    /**
     * Sets Left margin as percentage of paper dimensions.
     *
     * @param value int
     */
    public void setLeft(int value) {
        left = value;
    }

    /**
     * Top margin as percentage of paper dimensions.
     *
     * @return int
     */
    public int getTop() {
        return top;
    }

    /**
     * Sets Top margin as percentage of paper dimensions.
     *
     * @param value int
     */
    public void setTop(int value) {
        top = value;
    }

    /**
     * Right margin as percentage of paper dimensions.
     *
     * @return int
     */
    public int getRight() {
        return right;
    }

    /**
     * Sets Right margin as percentage of paper dimensions.
     *
     * @param value int
     */
    public void setRight(int value) {
        right = value;
    }

    /**
     * Bottom margin as percentage of paper dimensions.
     *
     * @return int
     */
    public int getBottom() {
        return bottom;
    }

    /**
     * Sets Bottom margin as percentage of paper dimensions.
     *
     * @param value int
     */
    public void setBottom(int value) {
        bottom = value;
    }

    public double getHeight() { return bottom-top+1; }

    public double getWidth() { return right-left+1; }
}


