package com.steema.teechart.tools;

import com.steema.teechart.events.ChartDrawEvent;
import com.steema.teechart.languages.Language;
import com.steema.teechart.events.ChartEvent;

import com.steema.teechart.Cursor;
import com.steema.teechart.Rectangle;
import com.steema.teechart.misc.Utils;
import com.steema.teechart.drawing.Color;
import com.steema.teechart.drawing.ChartPen;
import com.steema.teechart.drawing.Point;
import com.steema.teechart.drawing.SystemColor;
import com.steema.teechart.IBaseChart;
import com.steema.teechart.drawing.IGraphics3D;
import com.steema.teechart.styles.ISeries;
import com.steema.teechart.styles.Series;
import com.steema.teechart.events.FrameworkMouseEvent;

import java.util.EventListener;

/**
 *
 * <p>Title: CursorTool class</p>
 *
 * <p>Description: Cursor tool.</p>
 *
 * <p>Copyright (c) 2005-2019 by Steema Software SL. All Rights Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 *
 */
public class CursorTool extends ToolSeries {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private boolean followMouse;
    private boolean snap;
    private CursorToolStyle style = CursorToolStyle.BOTH;
    private boolean useChartRect;

    private CursorClicked dragging = CursorClicked.NONE;

    private double iXValue;
    private double iYValue;
    private Point iPoint = new Point( -1, -1);

    private Cursor cursor;
    
    public CursorTool() {
        this((IBaseChart)null);
    }

    public CursorTool(IBaseChart c) {
        super(c);
    }

    public CursorTool(Series s) {
        super(s.getChart());
        iSeries = s;
    }

    public void addCursorListener(CursorListener l) {
        listenerList.add(CursorListener.class, l);
    }

    public void removeCursorListener(CursorListener l) {
        listenerList.remove(CursorListener.class, l);
    }

    protected void fireMoved(CursorEvent e) {
        Object[] listeners = listenerList.getListenerList();
        for (int i = listeners.length - 2; i >= 0; i -= 2) {
            if (listeners[i] == CursorListener.class) {
              ((CursorListener) listeners[i + 1]).cursorMoved(e);
            }
        }
    }

    transient private CursorToolResolver rectangleResolver;

    public void setAxisRectangleResolver(CursorToolResolver resolver) {
        this.rectangleResolver = resolver;
    }

    public void removeAxisRectangleResolver() {
        this.rectangleResolver = null;
    }

    private void calcValuePositions(int x, int y) {
        if (dragging == CursorClicked.VERTICAL) {
            iXValue = getHorizAxis().calcPosPoint(x);
        } else
        if (dragging == CursorClicked.HORIZONTAL) {
            iYValue = getVertAxis().calcPosPoint(y);
        } else {
            iXValue = getHorizAxis().calcPosPoint(x);
            iYValue = getVertAxis().calcPosPoint(y);
        }
    }

    protected Rectangle doGetAxisRect(Rectangle tmpResult) {
        if (rectangleResolver != null) {
            tmpResult = rectangleResolver.getAxisRectangle(this, tmpResult);
        }
        return tmpResult;
    }

    private Rectangle internalGetAxisRect() {
        if (useChartRect) {
            return doGetAxisRect(chart.getChartRect());
        } else {
            Rectangle result = new Rectangle();
            result.x = getHorizAxis().iStartPos;
            result.width = getHorizAxis().iEndPos - result.x;
            result.y = getVertAxis().iStartPos;
            result.height = getVertAxis().iEndPos - result.y;
            return doGetAxisRect(result);
        }
    }

    /**
     * Moves Cursor when moving the mouse.<br>
     * Default value: false
     *
     * @return boolean
     */
    public boolean getFollowMouse() {
        return followMouse;
    }

    /**
     * Moves Cursor when moving the mouse.<br>
     * Default value: false
     *
     * @param value boolean
     */
    public void setFollowMouse(boolean value) {
        followMouse = value;
    }

    /**
     * Element Pen characteristics.
     *
     * @return ChartPen
     */
    public ChartPen getPen() {
        if (pPen == null) {
            pPen = new ChartPen(chart, Color.BLACK);
        }
        return pPen;
    }

    /**
     * Defines which lines of the CursorTool are shown.<br>
     * Default value: CursorToolStyle.Both
     *
     *
     * @return CursorToolStyle
     */
    public CursorToolStyle getStyle() {
        return style;
    }

    /**
     * Defines which lines of the CursorTool are shown.<br>
     * Default value: CursorToolStyle.Both
     *
     *
     * @param value CursorToolStyle
     */
    public void setStyle(CursorToolStyle value) {
        if (style != value) {
            style = value;
            snapToPoint();
            invalidate();
        }
    }

    /**
     * The X Value for vertical component of Cursor Tool.
     *
     * @return double
     */
    public double getXValue() {
        return iXValue;
    }

    /**
     * Sets X Value for vertical component of Cursor Tool.
     *
     * @param value double
     */
    public void setXValue(double value) {
        if (iXValue != value) {
            iXValue = value;
            calcScreenPositions();
            invalidate();
        }
    }

    /**
     * The Y Value for horizontal component of Cursor Tool.
     *
     * @return double
     */
    public double getYValue() {
        return iYValue;
    }

    /**
     * Sets Y Value for horizontal component of Cursor Tool.
     *
     * @param value double
     */
    public void setYValue(double value) {
        if (iYValue != value) {
            iYValue = value;
            calcScreenPositions();
            invalidate();
        }
    }

    protected void calcScreenPositions() {
        if ((iPoint.x == -1) || (iPoint.y == -1)) {

            double iXValueAux=iXValue;
            double iYValueAux=iYValue;

            iPoint.x = (getHorizAxis().iStartPos + getHorizAxis().iEndPos) / 2;
            iPoint.y = (getVertAxis().iStartPos + getVertAxis().iEndPos) / 2;
            calcValuePositions(iPoint.x, iPoint.y);

            if(iXValueAux != 0) {
                iXValue = iXValueAux;
            }
            if(iYValueAux != 0) {
                iYValue = iYValueAux;
            }
            int tmpSnap = snapToPoint();
            calcScreenPositions();
            changed(tmpSnap);
        } else {
            iPoint.x = getHorizAxis().calcPosValue(iXValue);
            iPoint.y = getVertAxis().calcPosValue(iYValue);
        }
    }

    protected void changed(int snapPoint) {
        fireMoved(new CursorEvent(this, CursorEvent.CURSOR_MOVED, iSeries, iPoint, iXValue, iYValue, snapPoint));
    }

    public void chartEvent(ChartDrawEvent ce) {
        super.chartEvent(ce);
        if ((ce.getID()==ChartDrawEvent.PAINTED) &&
            (ce.getDrawPart()==ChartDrawEvent.CHART)) {
            calcScreenPositions();
            redrawCursor();
        }
    }

    private Cursor mouseMove(FrameworkMouseEvent e, Cursor c) {
        // if mouse button is pressed and dragging then...
        if (((dragging != CursorClicked.NONE) || followMouse) &&
            internalGetAxisRect().contains(e.getPoint()) ) {

            double iXValueAux=iXValue;
            double iYValueAux=iYValue;

            calcValuePositions(e.getX(), e.getY());
            int tmpSnap = snapToPoint(); // snap to the nearest point of SnapSeries
            if(iXValue!=iXValueAux || iYValue!=iYValueAux) {
                invalidate();
                changed(tmpSnap);
            }
            
            c = cursor;
            chart.setCancelMouse(false);
        } else {
            // mouse button is not pressed, user is not dragging the cursor
            // change the mouse cursor when passing over the Cursor
            CursorClicked tmp = clicked(e.getPoint());

            if (tmp == CursorClicked.HORIZONTAL) {
                cursor = Cursor.VSPLIT;
            } else
            if (tmp == CursorClicked.VERTICAL) {
            	cursor = Cursor.HSPLIT;
            } else
            if (tmp == CursorClicked.BOTH) {
            	cursor = Cursor.SIZEALL;
                //default: c=Cursor.Default; break; // OriginalCursor //CI Dec03
            } else
            	cursor = Cursor.DEFAULT;
            
            c = cursor;
            chart.setCancelMouse(tmp != CursorClicked.NONE);
        }

        return c;
    }

    public Cursor mouseEvent(FrameworkMouseEvent e, Cursor c) {
        if (e.getID() == FrameworkMouseEvent.MOUSE_RELEASED) {
            dragging = CursorClicked.NONE;
        } else
        if (e.getID() == FrameworkMouseEvent.MOUSE_MOVED || e.getID() == FrameworkMouseEvent.MOUSE_DRAGGED ) {
            c = mouseMove(e, c);
        }else
        if (e.getID() == FrameworkMouseEvent.MOUSE_PRESSED) {
            if (!followMouse) {
                dragging = clicked(e.getPoint());
                if (dragging != CursorClicked.NONE) {
                    chart.setCancelMouse(true);
                }
            }
        }

        return c;
    }

    public CursorClicked clicked(int x, int y) {
        return clicked(new Point(x, y));
    }

    public CursorClicked clicked(Point p) {
        CursorClicked result = CursorClicked.NONE;

        if (internalGetAxisRect().contains(p)) {
            if ((style == CursorToolStyle.BOTH) &&
                (Math.abs(p.y - iPoint.y) < clickTolerance)
                && (Math.abs(p.x - iPoint.x) < clickTolerance)) {
                result = CursorClicked.BOTH;
            } else
            if (((style == CursorToolStyle.HORIZONTAL) ||
                 (style == CursorToolStyle.BOTH)) &&
                (Math.abs(p.y - iPoint.y) < clickTolerance)) {
                result = CursorClicked.HORIZONTAL;
            } else
            if (((style == CursorToolStyle.VERTICAL) ||
                 (style == CursorToolStyle.BOTH)) &&
                (Math.abs(p.x - iPoint.x) < clickTolerance)) {
                result = CursorClicked.VERTICAL;
            }
        }
        return result;
    }

    public final class NearestPoint {
        int index;
        double difference;
    }


    /**
     * Returns nearest point to Cursor and smallest distance value.
     *
     *
     * @param style CursorToolStyle
     * @return NearestPoint
     */
    public NearestPoint nearestPoint(CursorToolStyle style) {

        NearestPoint result = new NearestPoint();

        double tmpDif = 0;

        result.index = -1;
        result.difference = -1;

        GetFirstLast tmp = getFirstLastSeries(iSeries);

        if (tmp.result) {
            for (int t = tmp.min; t <= tmp.max; t++) {
                if (style == CursorToolStyle.HORIZONTAL) {
                    tmpDif = Math.abs(iYValue - iSeries.getYValues().value[t]);
                } else
                if (style == CursorToolStyle.VERTICAL) {
                    tmpDif = Math.abs(iXValue - iSeries.getXValues().value[t]);
                } else {
                    tmpDif = Math.sqrt(Utils.sqr(iXValue -
                            iSeries.getXValues().value[t]) +
                                       Utils.sqr(iYValue -
                            iSeries.getYValues().value[t]));
                }

                if ((result.difference == -1) || (tmpDif < result.difference)) {
                    result.difference = tmpDif;
                    result.index = t;
                }
            }
        }
        return result;
    }

    /**
     * Aligns the TCursorTool with the nearest series point.<br>
     * Snap has no effect unless a series is assigned to the Series method.<br>
     * Default value: false
     *
     * @return boolean
     */
    public boolean getSnap() {
        return snap;
    }

    /**
     * Sets the TCursorTool to align with the nearest series point.<br>
     * Default value: false
     *
     * @param value boolean
     */
    public void setSnap(boolean value) {
        snap = value;
    }

    /**
     * Moves cursor to nearest Series point and returns point index.
     *
     * @return int
     */
    public int snapToPoint() {
        int result=-1;

        if ((iSeries != null) && getSnap()) {
            NearestPoint tmp = nearestPoint(style);
            result = tmp.index;
        }

        if (result != -1) {
            if (style == CursorToolStyle.HORIZONTAL) {
                iYValue = iSeries.getYValues().value[result];
            }
            if (style == CursorToolStyle.VERTICAL) {
                iXValue = iSeries.getXValues().value[result];
            } else {
                iXValue = iSeries.getXValues().value[result];
                iYValue = iSeries.getYValues().value[result];
            }
        }

        return result;
    }

    private void drawHorizontal(IGraphics3D g,Rectangle r, int y) {
        if (chart.getAspect().getView3D()) {
            g.horizontalLine(r.x, r.getRight(), y, 0);
        } else {
            g.horizontalLine(r.x, r.getRight(), y);
        }
    }

    private void drawVertical(IGraphics3D g,Rectangle r, int x) {
        if (chart.getAspect().getView3D()) {
            g.verticalLine(x, r.y, r.getBottom(), 0);
        } else {
            g.verticalLine(x, r.y, r.getBottom());
        }
    }

    /**
     * Gets descriptive text.
     *
     * @return String
     */
    public String getDescription() {
        return Language.getString("CursorTool");
    }

    /**
     * Gets detailed descriptive text.
     *
     * @return String
     */
    public String getSummary() {
        return Language.getString("CursorToolSummary");
    }

    private void drawCursorLines(IGraphics3D g, Rectangle r, int x, int y) {
        if (style == CursorToolStyle.HORIZONTAL) {
            if (y >= 0) {
                drawHorizontal(g,r, y);
            }
        } else
        if (style == CursorToolStyle.VERTICAL) {
            if (x >= 0) {
                drawVertical(g,r, x);
            }
        } else {
            if (y >= 0) {
                drawHorizontal(g,r, y);
            }
            if (x >= 0) {
                drawVertical(g,r, x);
            }
        }
    }

    private void redrawCursor() {

        /*//CDI - the case of the dissappearing graphics
        Graphics3D g = (Graphics3D)chart.getGraphics3D();
        Graphics2D g2D = null;
        if(g instanceof Graphics3DAWT) {
            g2D = ((Graphics3DAWT) g).getGraphics();
            if(g2D == null) {
                Container c = chart.getParent().getControl();
                Graphics2D tempG = (Graphics2D) c.getGraphics();
                ((Graphics3DAWT) g).setGraphics(tempG);
            }
        }*/


       IGraphics3D g = chart.getGraphics3D();

        if (chart != null) { // && g.ValidState()) {
            Color tmpColor = chart.getPanel().getColor();
            if (tmpColor.isEmpty()) {
                tmpColor = SystemColor.getControlColor();
            }
            g.setPen(getPen());
            g.getBrush().setVisible(false);
            drawCursorLines(g, internalGetAxisRect(), iPoint.x, iPoint.y);
        }
    }

    /**
     * The full Chart rectangle is displayed instead of the boundaries defined
     * by Series axis.<br>
     * Default value: false
     *
     * @return boolean
     */
    public boolean getUseChartRect() {
        return useChartRect;
    }

    /**
     * Sets to full Chart rectangle instead of the boundaries defined by Series
     * axis.<br>
     * Default value: false
     *
     * @param value boolean
     */
    public void setUseChartRect(boolean value) {
        useChartRect = setBooleanProperty(useChartRect, value);
    }

    public interface CursorToolResolver {
        Rectangle getAxisRectangle(Tool tool, Rectangle rectangle);
    }

    public final class CursorEvent {
        public static final int CURSOR_FIRST = ChartEvent.CHART_LAST + 1;
        public static final int CURSOR_MOVED = CURSOR_FIRST;
        public static final int CURSOR_LAST  = CURSOR_FIRST;

        protected Point p;
        protected ISeries series;
        protected double iXValue;
        protected double iYValue;
        protected int valueIndex;

        /**
         * Creates a new instance of CursorEvent
         */
        public CursorEvent(Object source, int id, ISeries series, Point p, double iXValue, double iYValue, int valueIndex) {
            //super(source, id);
            this.p = p;
            this.series = series;
            this.iXValue = iXValue;
            this.iYValue = iYValue;
            this.valueIndex = valueIndex;
        }

        public Point getPoint() {
            return p;
        }

        public ISeries getSeries() {
            return series;
        }

        public double getXValue() {
            return iXValue;
        }

        public double getYValue() {
            return iYValue;
        }

        public double getValueIndex() {
            return valueIndex;
        }
    }


    public interface CursorListener extends EventListener {
        void cursorMoved(CursorEvent e);
    }
}

