package com.steema.teechart.tools;

import com.steema.teechart.Cursor;
import com.steema.teechart.IBaseChart;
import com.steema.teechart.Rectangle;
import com.steema.teechart.axis.Axis;
import com.steema.teechart.drawing.Color;
import com.steema.teechart.drawing.IGraphics3D;
import com.steema.teechart.events.ChangeEvent;
import com.steema.teechart.events.ChartDrawEvent;
import com.steema.teechart.events.DragListener;
import com.steema.teechart.events.FrameworkMouseEvent;
import com.steema.teechart.languages.Language;

/**
 *
 * <p>Title: ColorLine class</p>
 *
 * <p>Description: Color Line tool. To draw custom lines at a axis value.</p>
 *
 * <p>Copyright (c) 2005-2019 by Steema Software SL. All Rights Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 *
 */
public class ColorLine extends ToolAxis {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private boolean allowDrag = true;
    private boolean dragRepaint = false;
    private boolean noLimitDrag = false;
    private boolean draw3D = true;
    private boolean drawBehind = false;
    private ColorLineStyle style = ColorLineStyle.CUSTOM;
    private double lineValue = 0;
    private boolean dragging = false;

    public ColorLine() {
        this((IBaseChart) null);
    }

    public ColorLine(IBaseChart c) {
        super(c);
    }

    public ColorLine(Axis a) {
        this(a.getChart());
        iAxis = a;
    }

    public void addDragListener(DragListener l) {
        listenerList.add(DragListener.class, l);
    }

    public void removeDragListener(DragListener l) {
        listenerList.remove(DragListener.class, l);
    }

    public boolean getDragging() {
        return dragging;
    }

    public void setDragging(boolean value) {
        dragging = value;
    }

    /**
     *
     * Default value: ColorLineStyle.CUSTOM
     *
     * @return ColorLineStyle
     */
    public ColorLineStyle getStyle() {
        return style;
    }

    /**
     *
     * Default value: ColorLine.CUSTOM
     *
     * @param value ColorLineStyle
     */
    public void setStyle(ColorLineStyle value) {
        if (style != value) {
            style = value;
            invalidate();
        }
    }

    protected double calcValue() {
        if (style == ColorLineStyle.MAXIMUM) {
            return iAxis.getMaximum();
        } else if (style == ColorLineStyle.CENTER) {
            return (iAxis.getMaximum() + iAxis.getMinimum()) * 0.5;
        } else if (style == ColorLineStyle.MINIMUM) {
            return iAxis.getMinimum();
        };
        return lineValue;
    }

    private void drawColorLine(boolean back) {
        if (lineValue < getAxis().getMinimum() || lineValue > getAxis().getMaximum()) return;

        IGraphics3D g = chart.getGraphics3D();
        Rectangle r = chart.getChartRect();

        if (!g.getPen().getVisible()) {
            g.getPen().color = Color.EMPTY;
        }

        int w = chart.getAspect().getWidth3D();

        int tmp = iAxis.calcPosValue(calcValue());

        if (back) {
            if (iAxis.getHorizontal()) {
                if (draw3D) {
                    g.zLine(tmp, r.getBottom(), 0, w);
                }
                if (draw3D || drawBehind) {
                    g.verticalLine(tmp, r.y, r.getBottom(), w);
                }
            } else {
                if (draw3D) {
                    g.zLine(r.x, tmp, 0, w);
                }
                if (draw3D || drawBehind) {
                    g.horizontalLine(r.x, r.getRight(), tmp, w);
                }
            }
        } else if (chart.getAspect().getView3D() || ((!dragging) || dragRepaint)) {
            if (iAxis.getHorizontal()) {
                if (draw3D) {
                    g.zLine(tmp, r.y, 0, w);
                }
                if (!drawBehind) {
                    g.verticalLine(tmp, r.y, r.getBottom(), 0);
                }
            } else {
                if (draw3D) {
                    g.zLine(r.getRight(), tmp, 0, w);
                }
                if (!drawBehind) {
                    g.horizontalLine(r.x, r.getRight(), tmp, 0);
                }
            }
        }
    }

    public void chartEvent(ChartDrawEvent e) {
        super.chartEvent(e);
        if ((iAxis != null)
                && (((e.getID() == ChartDrawEvent.PAINTING)
                && (e.getDrawPart() == ChartDrawEvent.SERIES))
                || ((e.getID() == ChartDrawEvent.PAINTED)
                && (e.getDrawPart() == ChartDrawEvent.CHART)))) {
            chart.getGraphics3D().setPen(getPen());
            drawColorLine((e.getID() == ChartDrawEvent.PAINTING)
                    && (e.getDrawPart() == ChartDrawEvent.SERIES));
        }
    }

    public Cursor mouseEvent(FrameworkMouseEvent e, Cursor c) {
        double tmpLimit;

        boolean tmpDoDraw = false;

        if (allowDrag && (iAxis != null)) {
            if (e.getID() == FrameworkMouseEvent.MOUSE_RELEASED) {
                dragging = false;
                // force repaint
                if (!dragRepaint) {
                    invalidate();
                }
                // call event
                doEndDragLine();
            } else if (e.getID() == FrameworkMouseEvent.MOUSE_MOVED || e.getID() == FrameworkMouseEvent.MOUSE_DRAGGED) {
                if (dragging) {
                    //MM force repaint could vary
                    if (!dragRepaint) {
                        invalidate();
                    }

                    int tmp = iAxis.getHorizontal() ? e.getX() : e.getY();

                    // calculate new position
                    double tmpNew = getAxis().calcPosPoint(tmp);

                    // check inside axis limits
                    if (!noLimitDrag) {
                        // do not use Axis Minimum & Maximum, we need the "real" min && max
                        if (iAxis.getHorizontal()) {
                            tmpLimit = iAxis.calcPosPoint(iAxis.iStartPos);
                            if (tmpNew < tmpLimit) {
                                tmpNew = tmpLimit;
                            } else {
                                tmpLimit = iAxis.calcPosPoint(iAxis.iEndPos);
                                if (tmpNew > tmpLimit) {
                                    tmpNew = tmpLimit;
                                }
                            }
                        } else {
                            tmpLimit = iAxis.calcPosPoint(iAxis.iEndPos);
                            if (tmpNew < tmpLimit) {
                                tmpNew = tmpLimit;
                            } else {
                                tmpLimit = iAxis.calcPosPoint(iAxis.iStartPos);
                                if (tmpNew > tmpLimit) {
                                    tmpNew = tmpLimit;
                                }
                            }
                        }
                    }

                    if (dragRepaint) {
                        // call set_Value to force repaint whole chart
                        setValue(tmpNew);
                    } else {

                        tmpDoDraw = lineValue != tmpNew;
                        if (tmpDoDraw) {
                            // draw line in xor mode, to avoid repaint the whole chart

                            chart.getGraphics3D().setPen(getPen());
                            //Pen.Mode=pmNotXor;

                            /* hide previous line */
                            drawColorLine(true);
                            drawColorLine(false);

                            /* set new value */
                            lineValue = tmpNew;
                        }
                    }
                    
                    if (iAxis.getHorizontal()) {
                        c = Cursor.HSPLIT;
                    } else {
                        c = Cursor.VSPLIT;
                    }
                    chart.setCancelMouse(true);

                    /* call event, allow event to change Value */
                    doDragLine();

                    if (tmpDoDraw) {
                        /* draw at new position */
                        drawColorLine(true);
                        drawColorLine(false);
                        /* reset pen mode */
                        //chart.graphics3D.getPen().Mode=pmCopy;
                    }
                } else {
                    /* is mouse on line? */
                    if (clicked(e.getX(), e.getY())) {
                        /* 5.02 */
                        /* show appropiate cursor */

                        if (iAxis.getHorizontal()) {
                            c = Cursor.HSPLIT;
                        } else {
                            c = Cursor.VSPLIT;
                        }
                        chart.setCancelMouse(true);
                    }
                    else c = Cursor.DEFAULT;
                }
            } else if (e.getID() == FrameworkMouseEvent.MOUSE_PRESSED) {
                // is mouse over line ?
                if (!dragging) {
                    dragging = clicked(e.getX(), e.getY());
                    chart.setCancelMouse(dragging);
                }
            }
        }
        return c;
    }

    /**
     * Gets descriptive text.
     *
     * @return String
     */
    public String getDescription() {
        return Language.getString("ColorLineTool");
    }

    /**
     * Gets detailed descriptive text.
     *
     * @return String
     */
    public String getSummary() {
        return Language.getString("ColorLineSummary");
    }

    protected void doEndDragLine() {
        fireDragged(new ChangeEvent(this));
    }

    protected void doDragLine() {
        fireDragging(new ChangeEvent(this));
    }

    public boolean clicked(int x, int y) {
        int tmp = iAxis.getHorizontal() ? x : y;
        if (Math.abs(tmp - iAxis.calcPosValue(calcValue())) < clickTolerance) {
            Rectangle r = chart.getChartRect();
            return iAxis.getHorizontal() ? (y >= r.y) && (y <= r.getBottom())
                    : (x >= r.x) && (x <= r.getRight());
        } else {
            return false;
        }
    }

    /**
     * Allows mousedrag of Line when true. <br>
     * Default value: true
     *
     * @return boolean
     */
    public boolean getAllowDrag() {
        return allowDrag;
    }

    /**
     * Allows mousedrag of Line when true. <br>
     * Default value: true
     *
     * @param value boolean
     */
    public void setAllowDrag(boolean value) {
        allowDrag = setBooleanProperty(allowDrag, value);
    }

    /**
     * Repaints the Chart while moving the ColorLine when true.<br>
     * Set to true to repaint the Chart while moving the ColorLine. <br>
     * Default value: false
     *
     * @return boolean
     */
    public boolean getDragRepaint() {
        return dragRepaint;
    }

    /**
     * Set to true to repaint the Chart while moving the ColorLine. <br>
     * Default value: false
     *
     * @param value boolean
     */
    public void setDragRepaint(boolean value) {
        dragRepaint = setBooleanProperty(dragRepaint, value);
    }

    /**
     * Draws ColorLine in 3D when true.<br>
     * Default value: true
     *
     * @return boolean
     */
    public boolean getDraw3D() {
        return draw3D;
    }

    /**
     * Draws ColorLine in 3D when true.<br>
     * Default value: true
     *
     * @param value boolean
     */
    public void setDraw3D(boolean value) {
        draw3D = setBooleanProperty(draw3D, value);
    }

    /**
     * Draws the ColorLine behind the series values.<br>
     * Default value: false
     *
     * @return boolean
     */
    public boolean getDrawBehind() {
        return drawBehind;
    }

    /**
     * Draws the ColorLine behind the series values.<br>
     * Default value: false
     *
     * @param value boolean
     */
    public void setDrawBehind(boolean value) {
        drawBehind = setBooleanProperty(drawBehind, value);
    }

    /**
     * Allows drag of ColorLine outside of the Chart rectangle.<br>
     * Default value: false
     *
     * @return boolean
     */
    public boolean getNoLimitDrag() {
        return noLimitDrag;
    }

    /**
     * Allows drag of ColorLine outside of the Chart rectangle.<br>
     * Default value: false
     *
     * @param value boolean
     */
    public void setNoLimitDrag(boolean value) {
        noLimitDrag = setBooleanProperty(noLimitDrag, value);
    }

    /**
     * Determines Axis position where the ColorLine has to be drawn.<br>
     * Default value: 0
     *
     * @return double
     */
    public double getValue() {
        return lineValue;
    }

    /**
     * Sets the Axis position where the ColorLine has to be drawn.<br>
     * Default value: 0
     *
     * @param value double
     */
    public void setValue(double value) {
        lineValue = setDoubleProperty(lineValue, value);
    }
}
