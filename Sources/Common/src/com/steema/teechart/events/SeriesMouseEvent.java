package com.steema.teechart.events;

import com.steema.teechart.styles.ISeries;

import com.steema.teechart.drawing.Point;
import com.steema.teechart.events.FrameworkMouseEvent;

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
public class SeriesMouseEvent {

    public static final int SERIES_FIRST   = 982383862; // AWTEvent.RESERVED_ID_MAX + 1;
    public static final int SERIES_CLICKED = SERIES_FIRST;
    public static final int SERIES_ENTERED = SERIES_FIRST + 1;
    public static final int SERIES_EXITED  = SERIES_FIRST + 2;
    public static final int SERIES_LAST    = SERIES_FIRST + 3;

    private int id;
    ISeries series;
    int valueIndex=-1;
    FrameworkMouseEvent mouseEvent=null;
    Point p;
    //TODO add clicked part to event?

    public SeriesMouseEvent(ISeries series, int id, int valueIndex, FrameworkMouseEvent mouseEvent) {
        this(series, id, valueIndex, new Point(mouseEvent.getX(), mouseEvent.getY()));
        this.mouseEvent=mouseEvent;
    }

    public SeriesMouseEvent(ISeries series, int id, int valueIndex, Point p) {
        //super(series, id);
        this.series=series;
        this.id=id;
        this.valueIndex=valueIndex;
        this.p=p;
        this.mouseEvent = null;
    }

    public ISeries getSeries() {
        return series;
    }

    public int getValueIndex() {
        return valueIndex;
    }

    public FrameworkMouseEvent getMouseEvent() {
        return mouseEvent;
    }

    public Point getPoint() {
        return p;
    }

	public int getID() {
		return id;
	}
}

