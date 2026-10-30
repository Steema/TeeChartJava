/*
 * ChartPaintAdapter.java
 *
 * <p>Copyright (c) 2005-2019 by Steema Software SL. All Rights Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 *
 */

package com.steema.teechart.events;

/**
 * An abstract adapter class for receiving chart paint events.
 * The methods in this class are empty. This class exists as
 * convenience for creating listener objects.
 * <P>
 * Extend this class to create a <code>ChartPaint</code> listener
 * and override the methods for the events of interest. (If you implement the
 * <code>ChartPaintListener</code> interface, you have to define all of
 * the methods in it. This abstract class defines null methods for them
 * all, so you can only have to define methods for events you care about.)
 * <P>
 * Create a listener object using the extended class and then register it with
 * a component using the component's <code>addChartPaintListener</code>
 * method.
 *
 *
 *
 *
 * @see ChartPaintListener
 * @since 1.0
 */
public abstract class ChartPaintAdapter implements ChartPaintListener {

    /**
     * Invoked before Axes are drawn
     */
    public void axesPainting(ChartDrawEvent e) {};

    /**
     * Invoked after Chart is drawn
     */
    public void chartPainted(ChartDrawEvent e) {};

    /**
     * Invoked before Chart is drawn
     */
    public void chartPainting(ChartDrawEvent e) {};

    /**
     * Invoked after Series are drawn
     */
    public void seriesPainted(ChartDrawEvent e) {};

    /**
     * Invoked before Series are drawn
     */
    public void seriesPainting(ChartDrawEvent e) {};
}

