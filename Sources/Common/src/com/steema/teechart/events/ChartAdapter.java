/*
 * ChartAdapter.java
 *
 */

package com.steema.teechart.events;

/**
 * An abstract adapter class for receiving chart click events.
 * The methods in this class are empty. This class exists as
 * convenience for creating listener objects.
 * <P>
 * Extend this class to create a <code>Chart</code> listener
 * and override the methods for the events of interest. (If you implement the
 * <code>ChartListener</code> interface, you have to define all of
 * the methods in it. This abstract class defines null methods for them
 * all, so you can only have to define methods for events you care about.)
 * <P>
 * Create a listener object using the extended class and then register it with
 * a component using the component's <code>addChartListener</code>
 * method.
 * 
 * 
 * 
 * @see ChartListener
 * @since 1.0
 */
public abstract class ChartAdapter implements ChartListener {
    
    /**
     * Invoked when a series has been added to the chart
     */
    public void seriesAdded(ChartEvent e) {};    

    /**
     * Invoked when a tool has been added to the chart
     */
    public void toolAdded(ChartEvent e) {};           
}

