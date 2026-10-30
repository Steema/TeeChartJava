/*
 * SeriesMouseAdapter.java
 *
 * <p>Copyright (c) 2005-2019 by Steema Software SL. All Rights Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 *
 */

package com.steema.teechart.events;

/**
 * An abstract adapter class for receiving series events.
 * The methods in this class are empty. This class exists as
 * convenience for creating listener objects.
 * <P>
 * Series events let you track when a series is clicked.
 * <P>
 * Extend this class to create a <code>SeriesMouseEvent</code> listener
 * and override the methods for the events of interest. (If you implement the
 * <code>SeriesMouseListener</code> interface, you have to define all of
 * the methods in it. This abstract class defines null methods for them
 * all, so you can only have to define methods for events you care about.)
 * <P>
 * Create a listener object using the extended class and then register it with
 * a component using the component's <code>addSeriesMouseListener</code>
 * method. When a series is clicked (pressed and released), the relevant
 * method in the listener object is invoked and the <code>SeriesMouseEvent</code> is
 * passed to it.
 *
 *
 *
 * @see SeriesMouseEvent
 * @see SeriesMouseListenerer
 * @since 1.0
 */
public abstract class SeriesMouseAdapter implements SeriesMouseListener {
    /**
     * Invoked when a Series is clicked.
     */
    public void seriesClicked(SeriesMouseEvent e) {};

    /**
     * Invoked when the mouse pointer enters the Series.
     */
    public void seriesEntered(SeriesMouseEvent e) {};

    /**
     * Invoked when the mouse pointer leaves the Series.
     */
    public void seriesExited(SeriesMouseEvent e) {};
}

