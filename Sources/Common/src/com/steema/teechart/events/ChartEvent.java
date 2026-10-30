/*
 * ChartEvent.java
 *
 * Created on 15 june 2007, 1:52
 *
 */

package com.steema.teechart.events;

/**
 * <p>Title: TeeChart for Java</p>
 *
 * <p>Description: TeeChart for Java</p>
 *
 * <p>Copyright  (c) 2004-2019 by Steema Software SL. All Rights Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 *
 */
public class ChartEvent {

	private Object source;
	private int id;
	
    public static final int CHART_FIRST    = 54874545; // AWTEvent.RESERVED_ID_MAX + 1;
    public static final int CHART_ADDED    = CHART_FIRST;
    public static final int CHART_SCROLLED = CHART_FIRST + 1;
    public static final int CHART_UNZOOMED = CHART_FIRST + 2;
    public static final int CHART_ZOOMED   = CHART_FIRST + 3;
    public static final int CHART_LAST     = CHART_FIRST + 3;


    /**
     * Creates a new instance of ChartEvent
     */
    public ChartEvent(Object s, int i) {
    	source=s;
    	id=i;
        //super(source, id);
    }


    public int getID() {
    	return id;
    }
    
	public Object getSource() {
		return source;
	}

}

