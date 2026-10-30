/*
 * ChartMouseEvent.java
 *
 */
package com.steema.teechart.events;

import com.steema.teechart.ClickedParts;
import com.steema.teechart.drawing.Point;

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
public class ChartMouseEvent extends ChartEvent {

    public static final int MOUSE_FIRST     = ChartEvent.CHART_LAST + 1;
    public static final int MOUSE_CLICKED   = MOUSE_FIRST;
    public static final int MOUSE_UP        = MOUSE_CLICKED + 1;
    public static final int MOUSE_MOVE      = MOUSE_UP + 1;
    public static final int MOUSE_LAST      = MOUSE_FIRST + 3;
    
    private ClickedParts clickedPart;
    private Point clickedPoint;
    private int clickedButton;

    /**
     * Creates a new instance of ChartMouseEvent
     */
    public ChartMouseEvent(Object source, int id, ClickedParts clickedPart, FrameworkMouseEvent mouseEvent) {
        super(source, id);
        this.clickedPart = clickedPart;
        this.clickedPoint = mouseEvent.getPoint();
        this.clickedButton = mouseEvent.getButton();
    }

    public ClickedParts getClickedPart() {
        return clickedPart;
    }

    public boolean clickedAxes() {
        return clickedPart == ClickedParts.AXIS;
    }

    public boolean clickedBackground() {
        return clickedPart == ClickedParts.CHARTRECT;
    }

    public boolean clickedLegend() {
        return clickedPart == ClickedParts.LEGEND;
    }

    public boolean clickedSeries() {
        return clickedPart == ClickedParts.SERIES;
    }

    public boolean clickedTitles() {
        return ((clickedPart == ClickedParts.HEADER) ||
                (clickedPart == ClickedParts.SUBHEADER) ||
                (clickedPart == ClickedParts.FOOT) ||
                (clickedPart == ClickedParts.SUBFOOT));
    }

    public boolean clickedTool() {
        return clickedPart == ClickedParts.TOOL;
    }
    
    public Point getPoint() {
    	return clickedPoint;
    }
    
    public int getButton() {
    	return clickedButton;
    }
}

