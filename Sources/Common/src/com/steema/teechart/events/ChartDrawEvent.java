/*
 * ChartDrawEvent.java
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
public class ChartDrawEvent extends ChartEvent {

    public static final int PAINT_FIRST = ChartEvent.CHART_LAST + 1;
    public static final int PAINTING    = PAINT_FIRST;
    public static final int PAINTED     = PAINT_FIRST + 1;
    public static final int PRINTING    = PAINT_FIRST + 2;
    public static final int PRINTED     = PAINT_FIRST + 3;
    public static final int PAINT_LAST  = PAINT_FIRST + 3;

    public static final int CHART  = 1;
    public static final int AXES   = 2;
    public static final int SERIES = 3;

    private int drawPart;

    /**
     * Creates a new instance of ChartDrawEvent
     */
    public ChartDrawEvent(Object source, int id, int drawPart) {
        super(source, id);
        this.drawPart = drawPart;
    }

    public int getDrawPart() {
        return drawPart;
    }

}

