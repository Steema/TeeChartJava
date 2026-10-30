package com.steema.teechart.tools;

import com.steema.teechart.Rectangle;
import com.steema.teechart.drawing.Point;
import com.steema.teechart.styles.ISeries;
import com.steema.teechart.styles.Callout;

/**
 *
 * <p>Title: AnnotationCallout class</p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright (c) 2005-2019 by Steema Software SL. All Rights Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 *
 */
public class AnnotationCallout extends Callout {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int x = 0;
    private int y = 0;
    private int z = 0;

    public AnnotationCallout(ISeries s) {
        super(s);
        getArrow().setVisible( false);
        setVisible( false);
    }

    protected Point closerPoint(Rectangle r, Point p) {
        int tmpX, tmpY;

        if (p.x > r.getRight()) {
            tmpX = r.getRight();
        } else
        if (p.x < r.getLeft()) {
            tmpX = r.getLeft();
        } else {
            tmpX = (r.getLeft() + r.getRight()) / 2;
        }

        if (p.y > r.getBottom()) {
            tmpY = r.getBottom();
        } else
        if (p.y < r.getTop()) {
            tmpY = r.getTop();
        } else {
            tmpY = (r.getTop() + r.getBottom()) / 2;
        }

        return new Point(tmpX, tmpY);
    }

    /**
     * The X pixel coordinate of the ending point of the annotation callout
     * line.
     *
     * @return int
     */
    public int getXPosition() {
        return x;
    }

    /**
     * Sets the X pixel coordinate of the ending point of the annotation callout
     * line.
     *
     * @param value int
     */
    public void setXPosition(int value) {
        if (x != value) {
            x = value;
            invalidate();
        }
    }

    /**
     * The Y pixel coordinate of the ending point of the annotation callout
     * line.
     *
     * @return int
     */
    public int getYPosition() {
        return y;
    }

    /**
     * Sets the Y pixel coordinate of the ending point of the annotation callout
     * line.
     *
     * @param value int
     */
    public void setYPosition(int value) {
        if (y != value) {
            y = value;
            invalidate();
        }
    }

    /**
     * The Z pixel coordinate of the ending point of the annotation callout
     * line.
     *
     * @return int
     */
    public int getZPosition() {
        return z;
    }

    /**
     * Sets the Z pixel coordinate of the ending point of the annotation callout
     * line.
     *
     * @param value int
     */
    public void setZPosition(int value) {
        if (z != value) {
            z = value;
            invalidate();
        }
    }
}

