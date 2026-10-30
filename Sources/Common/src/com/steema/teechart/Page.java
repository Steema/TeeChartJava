package com.steema.teechart;

import com.steema.teechart.events.ChangeEvent;
import com.steema.teechart.events.ChangeListener;
import com.steema.teechart.events.EventListenerList;


/**
 *
 * <p>Title: Page class.</p>
 *
 * <p>Description: Chart paging characteristics</p>
 *
 * <p>Copyright (c) 2005-2019 by Steema Software SL. All Rights Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 *
 */
public class Page extends TeeBase {

	private static final long serialVersionUID = 1L;
	
    private int maxPoints = 0;
    private int current = 1;
    private boolean scaleLast = true;
    private boolean autoScale;
    
    protected transient ChangeEvent changeEvent;

    /** A list of event listeners for this component. */
    transient protected EventListenerList listenerList;

    public Page(Chart c) {
        super(c);
        readResolve();
    }

    protected Object readResolve() {
        listenerList = new EventListenerList();
        return this;
    }

    /**
     * Adds a <code>ChangeListener</code> to the button.
     * @param l the listener to be added
     */
    public void addChangeListener(ChangeListener l) {
        listenerList.add(ChangeListener.class, l);
    }

    /**
     * Removes a ChangeListener from the button.
     * @param l the listener to be removed
     */
    public void removeChangeListener(ChangeListener l) {
        listenerList.remove(ChangeListener.class, l);
    }

    /**
     * Returns an array of all the <code>ChangeListener</code>s added
     * to this PageNavigator with addChangeListener().
     *
     * @return all of the <code>ChangeListener</code>s added or an empty
     *         array if no listeners have been added
     * @since 1.4
     */
    public ChangeListener[] getChangeListeners() {
        return (ChangeListener[])(listenerList.getListeners(
            ChangeListener.class));
    }

    /**
     * Notifies all listeners that have registered interest for
     * notification on this event type.  The event instance
     * is lazily created.
     * @see EventListenerList
     */
    protected void fireStateChanged() {
        // Guaranteed to return a non-null array
        Object[] listeners = listenerList.getListenerList();
        // Process the listeners last to first, notifying
        // those that are interested in this event
        for (int i = listeners.length-2; i>=0; i-=2) {
            if (listeners[i]==ChangeListener.class) {
                // Lazily create the event:
                if (changeEvent == null)
                    changeEvent = new ChangeEvent(this);
                ((ChangeListener)listeners[i+1]).stateChanged(changeEvent);
            }
        }
    }

    /**
     * Gets the number of pages, according to MaxPointsPerPage property.<br>
     * TChart.Page.MaxPointsPerPage must be greater than zero to activate auto
     * paging. TChart.Page.Current determines the current visible page.
     *
     * @return number of Chart pages
     */
    public int getCount() {
        return chart.getNumPages();
    }

    /**
     * The current page number.<br><br>
     *
     * Run-time only.
     * TChart.Page.Current determines the current visible points of Series in
     * a Chart with MaxPointsPerPage greater than zero.
     * When TChart.Page.MaxPointsPerPage is greater than zero, TeeChart
     * internally divides all Series points in "pages".
     * Each page is assigned a different initial and ending X coordinates.
     * TChart.Page.NumPages returns the total number of pages. It equals the
     * total number of Series points divided by MaxPointsPerPage.
     * TChart.Page.Previous and TChart.Page.Next decrement or increment
     * Current respectively.
     *
     * @return current page number
     */
    public int getCurrent() {
        return current;
    }

    /**
     * Sets the current page number.
     *
     * @param value int
     */
    public void setCurrent(int value) {
        if ((value >= 0) && (value <= getCount())) {
            current = setIntegerProperty(current, value);
            fireStateChanged();
        }
    }

    /**
     * The number of points displayed per page.<br>
     * Default value: 0
     *
     * @return maximum number of points
     */
    public int getMaxPointsPerPage() {
        return maxPoints;
    }

    /**
     * Sets the number of points displayed per page.<br>
     * MaxPointsPerPage controls "TeeChart AutoPaging".
     * Setting it to a number greater than zero makes TeeChart internally
     * divide Series points in Pages. You can then navigate across Chart pages
     * by using Chart.Page and Chart.NumPages.
     * For each Page, TeeChart will automatically calculate and display the
     * corresponding Series points. The last page can have fewer points than
     * other pages. You can use the Chart.ScaleLastPage to control if last
     * page will be "stretched" or not.<br>
     * Default value: 0
     *
     * <p>Example:
     * <pre><font face="Courier" size="4">
     * myChart.getPage().setMaxPointsPerPage(6);
     * </font></pre></p>
     *
     * @see Page#getMaxPointsPerPage
     * @param value int
     */
    public void setMaxPointsPerPage(int value) {
        maxPoints = setIntegerProperty(maxPoints, value);
    }

    /**
     * Determines how the last Chart page will be displayed.<br>
     * It only has effect when TChart.MaxPointsPerPage is greater than zero.
     * When true, the last Chart page will have the same horizontal scaling as
     * the other pages. When false, the last Chart page scaling will be
     * adjusted based on the number of visible points on that last page.<br>
     * Default value: true
     *
     * @return boolean
     */
    public boolean getScaleLastPage() {
        return scaleLast;
    }

    /**
     * Determines how the last Chart page will be displayed.<br>
     * Default value: true
     *
     * @see Page#getScaleLastPage
     * @param value boolean
     */
    public void setScaleLastPage(boolean value) {
        scaleLast = setBooleanProperty(scaleLast, value);
    }

    /**
     * Moves to next page ( Current + 1 )<br>
     * When MaxPointsPerPage is greater than Zero, TeeChart automatically
     * divides point values in Pages. Calling Next is the same as
     * Page = Page + 1. The NumPages chart property returns the total number of
     * pages.
     *
     */
    public void next() {
        if ((maxPoints > 0) && (current < getCount())) {
            setCurrent(current + 1);
        }
    }

    /**
     * Moves to previous page ( Current - 1 )<br>
     * When MaxPointsPerPage is greater than Zero, TeeChart automatically
     * divides point values in Pages. Calling Previous is the same as
     * Page = Page - 1 (Go back a page). The Count property returns the total
     * number of pages.
     *
     */
    public void previous() {
        if ((maxPoints > 0) && (current > 1)) {
            setCurrent(current - 1);
        }
    }
    
    public void setAutoScale(boolean value)
    {
        autoScale = value;
        invalidate();
    }
    
    public boolean getAutoScale()
    {
        return autoScale;
    }
    
    public int getFirstValueIndex()
    {
        return ((this.current -1 ) * this.getMaxPointsPerPage());
    }
}

