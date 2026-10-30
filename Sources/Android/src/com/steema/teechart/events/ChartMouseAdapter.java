/*
 * ChartMouseAdapter.java
 *
 */

package com.steema.teechart.events;

/**
 * An abstract adapter class for receiving chart click events.
 * The methods in this class are empty. This class exists as
 * convenience for creating listener objects.
 * <P>
 * Extend this class to create a <code>ChartClick</code> listener
 * and override the methods for the events of interest. (If you implement the
 * <code>ChartMouseListener</code> interface, you have to define all of
 * the methods in it. This abstract class defines null methods for them
 * all, so you can only have to define methods for events you care about.)
 * <P>
 * Create a listener object using the extended class and then register it with
 * a component using the component's <code>addChartMouseListener</code>
 * method.
 * 
 * 
 * 
 * @see ChartMouseListener
 * @since 1.0
 */
public abstract class ChartMouseAdapter implements ChartMouseListener {
    
    /**
     * Invoked when the mouse button has been clicked (pressed and released) on an Axis
     */
    public void axesClicked(ChartMouseEvent e) {};    

    /**
     * Invoked when the mouse button has been clicked (pressed and released) on the Background
     */
    public boolean backgroundClicked(ChartMouseEvent e) {
        return true;
    };
    
    /**
     * Invoked when the mouse button has been clicked (pressed and released) on a Legend
     */
    public void legendClicked(ChartMouseEvent e) {};        
    
    /**
     * Invoked when the mouse button has been clicked (pressed and released) on an Title
     */
    public void titleClicked(ChartMouseEvent e) {};

    /**
     * Invoked when the mouse button has been released
     */
    public boolean mouseUp(ChartMouseEvent e) {
        return true;
    };

    /**
     * Invoked when the mouse button has been released
     */
    public boolean mouseMove(ChartMouseEvent e) {
        return true;
    };
}

