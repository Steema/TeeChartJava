package com.steema.teechart.tools;

import com.steema.teechart.events.ChangeListener;
import com.steema.teechart.events.ChartDrawEvent;
import com.steema.teechart.languages.Language;
import com.steema.teechart.Cursor;
import com.steema.teechart.Rectangle;
import com.steema.teechart.drawing.DashStyle;
import com.steema.teechart.drawing.Color;

import com.steema.teechart.drawing.IGraphics3D;
import com.steema.teechart.drawing.ChartPen;
import com.steema.teechart.drawing.ChartBrush;
import com.steema.teechart.drawing.Point;
import com.steema.teechart.IBaseChart;
import com.steema.teechart.events.ChangeEvent;
import com.steema.teechart.misc.MathUtils;
import com.steema.teechart.styles.BaseLine;
import com.steema.teechart.styles.Series;
import com.steema.teechart.styles.TreatNullsStyle;
import com.steema.teechart.events.FrameworkMouseEvent;
import java.util.EventListener;

/**
 *
 * <p>Title: NearestPoint class</p>
 *
 * <p>Description: Nearest tool, use it to display a graphical signal when the
 * mouse is moving near a series point.</p>
 *
 * <p>Copyright (c) 2005-2019 by Steema Software SL. All Rights Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 *
 */
public class NearestPoint extends ToolSeries {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private boolean drawLine = true;
    private boolean fullRepaint = true;
    private int size = 20;
    private NearestPointStyle style = NearestPointStyle.CIRCLE;
    private NearestPointDirection direction = NearestPointDirection.BOTH;
    private Point iMouse;
    private int oldPoint;
    public int point = -1;

    public NearestPoint(Series s) {
        this(s.getChart());
        iSeries = s;
    }

    public NearestPoint() {
        this((IBaseChart) null);
    }

    public NearestPoint(IBaseChart c) {
        super(c);
        getPen().setDefaultColor(Color.WHITE);
        getPen().setDefaultStyle(DashStyle.DOT);
    }

    public void addChangeListener(ChangeListener l) {
        listenerList.add(EventListener.class, (EventListener) l);  //mm todo
    }

    public void removeChangeListener(ChangeListener l) {
        listenerList.remove(EventListener.class, (EventListener) l);
    }

    /**
     * Defines the Size of the NearestTool shape.<br>
     * Default value: 20
     *
     * @return int
     */
    public int getSize() {
        return size;
    }

    /**
     * Defines the Size of the NearestTool shape.<br>
     * Default value: 20
     *
     * @param value int
     */
    public void setSize(int value) {
        size = setIntegerProperty(size, value);
    }

    /**
     * The shape of the NearestTool.<br>
     * Default value: NearestPointStyle.Circle
     *
     *
     * @return NearestPointStyle
     */
    public NearestPointStyle getStyle() {
        return style;
    }

    /**
     * Sets the shape of the NearestTool.<br>
     * Default value: NearestPointStyle.Circle
     *
     *
     * @param value NearestPointStyle
     */
    public void setStyle(NearestPointStyle value) {
        if (style != value) {
            style = value;
            invalidate();
        }
    }

    /**
     * Gets descriptive text.
     *
     * @return String
     */
    public String getDescription() {
        return Language.getString("NearestTool");
    }

    /**
     * Gets detailed descriptive text.
     *
     * @return String
     */
    public String getSummary() {
        return Language.getString("NearestPointSummary");
    }

    /**
     * Draws a temporary line from the mouse coordinates to the nearest
     * point.<br>
     * Default value: true
     *
     * @return boolean
     */
    public boolean getDrawLine() {
        return drawLine;
    }

    /**
     * Draws a temporary line from the mouse coordinates to the nearest
     * point.<br>
     * Default value: true
     *
     * @param value boolean
     */
    public void setDrawLine(boolean value) {
        drawLine = setBooleanProperty(drawLine, value);
    }

    /**
     * Allows the whole Parent Chart to repainted when true.<br>
     * Default value: true
     *
     * @return boolean
     */
    public boolean getFullRepaint() {
        return fullRepaint;
    }

    /**
     * Allows the whole Parent Chart to repainted when true.<br>
     * Default value: true
     *
     * @param value boolean
     */
    public void setFullRepaint(boolean value) {
        fullRepaint = setBooleanProperty(fullRepaint, value);
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
     * Element Brush characteristics.
     *
     * @return ChartBrush
     */
    public ChartBrush getBrush() {
        if (bBrush == null) {
            bBrush = new ChartBrush(chart, false);
        }
        return bBrush;
    }

    private void paintHint() {

        if ((iSeries != null) && (point != -1)) {
            IGraphics3D g = chart.getGraphics3D();
            g.setPen(getPen());

            //if ( ! fullRepaint ) g.getPen().Mode=pmNotXor;

            int x = iSeries.calcXPos(point);
            int y = iSeries.calcYPos(point);

            if (style != NearestPointStyle.NONE) {
                g.setBrush(getBrush());
                Rectangle r = Rectangle.fromLTRB(x - size, y - size,
                        x + size, y + size);

                if (style == NearestPointStyle.CIRCLE) {
                    if (chart.getAspect().getView3D()) {
                        g.ellipse(r, iSeries.getStartZ());
                    } else {
                        g.ellipse(r);
                    }
                } else if (style == NearestPointStyle.RECTANGLE) {
                    if (chart.getAspect().getView3D()) {
                        g.rectangle(r, iSeries.getStartZ());
                    } else {
                        g.rectangle(r);
                    }
                } else if (style == NearestPointStyle.DIAMOND) {
                    Point[] p = new Point[4];
                    p[0] = new Point(x, y - size);
                    p[1] = new Point(x + size, y);
                    p[2] = new Point(x, y + size);
                    p[3] = new Point(x - size, y);
                    g.polygon(iSeries.getStartZ(), p);
                }
            }

            if (drawLine) {
                g.getPen().setStyle(DashStyle.SOLID);
                g.moveTo(iMouse);
                g.lineTo(x, y);
            }
            // if ( ! fullRepaint ) g.getPen().Mode=pmCopy;
        }
    }

    public void chartEvent(ChartDrawEvent e) {
        super.chartEvent(e);
        if ((e.getID() == ChartDrawEvent.PAINTED)
                && (e.getDrawPart() == ChartDrawEvent.CHART)) {
            paintHint();
        }
    }

    public int getNearestPoint(Point p) {
        int result = -1;
        int dif = 10000;

        GetFirstLast tmp = getFirstLastSeries(iSeries);
        if (tmp.result) {

            // traverse all points in a Series...
            for (int t = tmp.min; t <= tmp.max; t++) {
            	
            	if (!((iSeries instanceof BaseLine) && (((BaseLine)iSeries).getTreatNulls() != TreatNullsStyle.IGNORE) && (iSeries.isNull(t)))) {
	                // calculate position in pixels
	                int tmpX = iSeries.calcXPos(t);
	                int tmpY = iSeries.calcYPos(t);
	
	                Rectangle r = chart.getChartRect();
	                if (r.contains(tmpX, tmpY)) {
	                    /* 5.01 */
	
	                    // calculate distance in pixels...
	                    int dist = 0;
	                    if (direction == NearestPointDirection.HORIZONTAL) {
	                        dist = MathUtils.round(Math.sqrt(MathUtils.sqr(p.x - tmpX)
	                                + MathUtils.sqr(p.x - tmpX)));
	                    } else if (direction == NearestPointDirection.VERTICAL) {
	                        dist = MathUtils.round(Math.sqrt(MathUtils.sqr(p.y - tmpY)
	                                + MathUtils.sqr(p.y - tmpY)));
	                    } else if (direction == NearestPointDirection.BOTH) {
	                        dist = MathUtils.round(Math.sqrt(MathUtils.sqr(p.x - tmpX)
	                                + MathUtils.sqr(p.y - tmpY)));
	                    }
	
	                    if (dist < dif) { // store if distance is lower...
	                        dif = dist;
	                        result = t; // <-- set this point to be the nearest...
	                    }
	                }
            	}
            }
        }

        return result;
    }

    public Cursor mouseEvent(FrameworkMouseEvent e, Cursor c) {
        if ((e.getID() == FrameworkMouseEvent.MOUSE_MOVED) && (iSeries != null)) {

            if (!fullRepaint) {
                paintHint();
            }

            iMouse = new Point(e.getPoint());
            oldPoint = point;
            point = getNearestPoint(iMouse);
            if (oldPoint != point) {
                this.fireChanged(new ChangeEvent(this));
            }

            if (!fullRepaint) {
                paintHint();
            }

            if (fullRepaint) {
                invalidate();
            }
        }
        return c;
    }

    public NearestPointDirection getDirection() {
        return direction;
    }

    public void setDirection(NearestPointDirection value) {
        if (value != direction) {
            direction = value;
            invalidate();
        }
    }
}
