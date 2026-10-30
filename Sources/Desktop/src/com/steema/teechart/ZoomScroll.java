package com.steema.teechart;

import com.steema.teechart.drawing.Point;


/**
 *
 * <p>Title: ZoomScroll class</p>
 *
 * <p>Description: Internal use. Zoom and scroll support.</p>
 *
 * <p>Copyright (c) 2005-2019 by Steema Software SL. All Rights Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 *
 */
public class ZoomScroll extends TeeBase {
	
  private static final long serialVersionUID = 1L;

  public class ZoomSnapshot
  {
    public double[] AxesMinMax;
    public int CustomCount;
    public double[][] CustomMinMax;
  }
    /**
     * Determines if the user is currently zooming or scrolling contents
     * by mouse dragging.
     */
    transient private boolean active;
    /**
     * Creates a new ZoomScroll class that is asociated to chart parameter.
     *
     * @param value IBaseChart
     */
    public ZoomScroll(final IBaseChart value) {
        super(value);
    }

    /**
     * Returns the active state of Chart Zoom and Scroll.<br>
     * In other words, during the act of zooming or scrolling returns true.<br>
     * Default value: false
     *
     * @return boolean
     */
    public boolean getActive() {
        return active;
    }

    /**
     * Sets the active state of Chart Zoom and Scroll.<br>
     * Default value: false
     *
     * @param value boolean
     */
    public void setActive(final boolean value) {
        active = value;
    }

    /**
     * Makes sure x0 and y0 coordinates are lower than x1 and y1 respectively.
     */
    public void check() {
        int tmp;

        if (x0 > x1) {
            tmp = x0;
            x0 = x1;
            x1 = tmp;
        }

        if (y0 > y1) {
            tmp = y0;
            y0 = y1;
            y1 = tmp;
        }
    }

    /**
     * Initializes a Zoom or Scroll operation starting at Point p coordinates.
     *
     * @param p Point
     */
    public final void activate(final Point p) {
        activate(p.x, p.y);
    }

    /**
     * Internal use. Starts a Zoom or Scroll from the co-ordinates x and y.
     *
     * @param x int
     * @param y int
     */
    public void activate(final int x, final int y) {
        x0 = x;
        y0 = y;
        x1 = x;
        y1 = y;
        active = true;
    }
    

    /**
     * Starting horizontal coordinate.
     */
    transient public int x0;

    /**
     * Starting vertical coordinate.
     */
    transient public int y0;

    /**
     * Ending horizontal coordinate.
     */
    transient public int x1;

    /**
     * Ending vertical coordinate.
     */
    transient public int y1;
}

