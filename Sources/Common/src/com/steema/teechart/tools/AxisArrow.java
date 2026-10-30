package com.steema.teechart.tools;

import com.steema.teechart.events.ChartDrawEvent;
import com.steema.teechart.languages.Language;
import com.steema.teechart.Cursor;
import com.steema.teechart.IBaseChart;
import com.steema.teechart.axis.Axis;
import com.steema.teechart.drawing.ChartBrush;
import com.steema.teechart.drawing.Point;

import com.steema.teechart.ScrollResult;

import com.steema.teechart.events.FrameworkMouseEvent;

/**
 *
 * <p>Title: AxisArrow class</p>
 *
 * <p>Description: Axis Arrow tool.</p>
 *
 * <p>Copyright (c) 2005-2019 by Steema Software SL. All Rights Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 *
 */
public class AxisArrow extends ToolAxis {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int length = 16;
    private AxisArrowPosition position = AxisArrowPosition.BOTH;
    private int scrollPercent = 10;
    private boolean scrollInverted;

    public AxisArrow() {
        this((IBaseChart)null);
    }

    public AxisArrow(IBaseChart c) {
        super(c);
    }


    public AxisArrow(Axis a) {
        this(a.getChart());
        iAxis = a;
    }

    private int check(int pos1, int pos2) {
        if (Math.abs(pos1 - iAxis.getPosition()) < clickTolerance) {

            if ((position == AxisArrowPosition.START) ||
                (position == AxisArrowPosition.BOTH)) {
                if ((pos2 > iAxis.iStartPos) &&
                    (pos2 < iAxis.iStartPos + length)) {
                    return 0;
                }
            }

            if ((position == AxisArrowPosition.END) ||
                (position == AxisArrowPosition.BOTH)) {
                if ((pos2 < iAxis.iEndPos) && (pos2 > iAxis.iEndPos - length)) {
                    return 1;
                }
            }
        }
        return -1;
    }

    private int clickedArrow(Point p) {
        return iAxis.getHorizontal() ? check(p.y, p.x) : check(p.x, p.y);
    }

    /**
     * The length of the Arrows.<br>
     * Default value: 16
     *
     * @return int
     */
    public int getLength() {
        return length;
    }

    /**
     * Sets the length of the Arrows.<br>
     * Default value: 16
     *
     * @param value int
     */
    public void setLength(int value) {
        length = setIntegerProperty(length, value);
    }

    /**
     * The position where the arrows are drawn on the Axis.<br>
     * Default value: AxisArrowPosition.Both
     *
     * @return AxisArrowPosition
     */
    public AxisArrowPosition getPosition() {
        return position;
    }

    /**
     * Determines where the arrows are drawn on the Axis.<br>
     * Default value: AxisArrowPosition.Both
     *
     * @param value AxisArrowPosition
     */
    public void setPosition(AxisArrowPosition value) {
        if (position != value) {
            position = value;
            invalidate();
        }
    }

    private void drawArrow(int aPos, int aLength, int z) {
        Point p0, p1;

        if (iAxis.getHorizontal()) {
            p0 = new Point(aPos + aLength, iAxis.getPosition());
            p1 = new Point(aPos, iAxis.getPosition());
        } else {
            p0 = new Point(iAxis.getPosition(), aPos + aLength);
            p1 = new Point(iAxis.getPosition(), aPos);
        }

        chart.getGraphics3D().arrow(true, p0, p1, 20, 20, z);
    }

    /**
     * Element Brush characteristics.
     * @return ChartBrush
     */
    public ChartBrush getBrush() {
        if (bBrush == null) {
            bBrush = new ChartBrush(chart);
        }
        return bBrush;
    }

    public void chartEvent(ChartDrawEvent ce) {
        if (((ce.getID()==ChartDrawEvent.PAINTED) &&
             (ce.getDrawPart()==ChartDrawEvent.CHART)) &&
             (iAxis != null)) {

            chart.getGraphics3D().setBrush(getBrush());
            chart.getGraphics3D().setPen(getPen());

            int z = (chart.getAspect().getView3D() && iAxis.getOtherSide()) ?
                    chart.getAspect().getWidth3D() : 0;

            if ((position == AxisArrowPosition.START) ||
                (position == AxisArrowPosition.BOTH)) {
                drawArrow(iAxis.iStartPos, length, z);
            }

            if ((position == AxisArrowPosition.END) ||
                (position == AxisArrowPosition.BOTH)) {
                drawArrow(iAxis.iEndPos, -length, z);
            }
        }
    }

    /**
     * Gets descriptive text.
     *
     * @return String
     */
    public String getDescription() {
        return Language.getString("AxisArrowTool");
    }

    /**
     * Gets detailed descriptive text.
     *
     * @return String
     */
    public String getSummary() {
        return Language.getString("AxisArrowSummary");
    }

    private void doScroll(double aDelta) {
        if (chart.getParent() != null) {
            ScrollResult r = new ScrollResult();
            r.min = iAxis.getMinimum();
            r.max = iAxis.getMaximum();

            chart.getParent().doAllowScroll(iAxis, aDelta, r);

            if (r.allow) {
                iAxis.setMinimum(r.min);
                iAxis.setMaximum(r.max);
                iAxis.scroll(aDelta, false);
                if (chart.getParent() != null) {
                    chart.getParent().doScroll(this);
                }
            }
        }
    }

    public Cursor mouseEvent(FrameworkMouseEvent e, Cursor c) {
        if ((iAxis != null) && iAxis.getVisible()) {

            if (e.getID() == FrameworkMouseEvent.MOUSE_PRESSED) {

                if (scrollPercent != 0) {

                    int tmp = clickedArrow(e.getPoint());

                    double delta = (iAxis.getMaximum() - iAxis.getMinimum()) *
                                   scrollPercent *
                                   0.01; // 5.02

                    if (scrollInverted) {
                        delta = -delta; // 5.02
                    }

                    if (tmp == 0) {
                        doScroll(delta);
                    } else
                    if (tmp == 1) {
                        doScroll( -delta);
                    }

                    if ((tmp == 0) || (tmp == 1)) {
                        chart.setCancelMouse(true);
                    }
                }
            } else
            if (e.getID() == FrameworkMouseEvent.MOUSE_MOVED) {
                if (clickedArrow(e.getPoint()) != -1) {
                    c = Cursor.HAND;
                    chart.setCancelMouse(true);
                }
            }
        }

        return c;
    }


    /**
     * The TChart scroll rate as percentage of the associated axis.<br>
     * Default value: 10
     *
     * @return int
     */
    public int getScrollPercent() {
        return scrollPercent;
    }

    /**
     * Sets TChart scroll rate as percentage of the associated axis.<br>
     * Default value: 10
     *
     * @param value int
     */
    public void setScrollPercent(int value) {
        scrollPercent = value;
    } // no need to repaint

    /**
     * Reverses direction of applied Axis Arrow scroll.<br>
     * Default value: false
     *
     * @return boolean
     */
    public boolean getScrollInverted() {
        return scrollInverted;
    }

    /**
     * Reverses direction of applied Axis Arrow scroll.<br>
     * Default value: false
     *
     * @param value boolean
     */
    public void setScrollInverted(boolean value) {
        scrollInverted = value;
    } // no need to repaint
}

