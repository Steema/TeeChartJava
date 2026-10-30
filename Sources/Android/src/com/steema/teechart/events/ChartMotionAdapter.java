/*
 * ChartMotionAdapter.java
 *
 */

package com.steema.teechart.events;

/**
 * An abstract adapter class for receiving chart motion events.
 * The methods in this class are empty. This class exists as
 * convenience for creating listener objects.
 * <P>
 * Extend this class to create a <code>ChartClick</code> listener
 * and override the methods for the events of interest. (If you implement the
 * <code>ChartMotionListener</code> interface, you have to define all of
 * the methods in it. This abstract class defines null methods for them
 * all, so you can only have to define methods for events you care about.)
 * <P>
 * Create a listener object using the extended class and then register it with
 * a component using the component's <code>addChartMotionListener</code>
 * method.
 *
 *
 * @see ChartMotionListener
 * @since 1.0
 */
public abstract class ChartMotionAdapter implements ChartMotionListener {
    
    /**
     * Invoked when the chart has been scrolled
     */
    public void scrolled(ChartEvent e) {};    

    /**
     * Invoked when the chart has been zoomed
     */
    public void zoomed(ChartEvent e) {};    
    
    /**
     * Invoked when the chart has been unzoomed
     */
    public void unzoomed(ChartEvent e) {};   
}

