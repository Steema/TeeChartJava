package com.steema.teechart.tools;

import com.steema.teechart.Cursor;
import com.steema.teechart.IBaseChart;
import com.steema.teechart.drawing.Point;
import com.steema.teechart.events.ChangeEvent;
import com.steema.teechart.events.DragListener;
import com.steema.teechart.events.FrameworkMouseEvent;
import com.steema.teechart.languages.Language;
import com.steema.teechart.styles.ISeries;
import com.steema.teechart.styles.Series;

/**
 *
 * <p>Title: DragPoint class</p>
 *
 * <p>Description: DragPoint Tool.</p>
 *
 * <p>Copyright (c) 2005-2019 by Steema Software SL. All Rights Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 *
 */
public class DragPoint extends ToolSeries {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private DragPointStyle style = DragPointStyle.BOTH;
    private Series theSeries;
    private int button = FrameworkMouseEvent.BUTTON1;
    private Cursor cursor = Cursor.HAND;

    private int dragging = -1;

    public DragPoint(IBaseChart c) {
        super(c);
    }

    public DragPoint(Series s) {
        super(s);
    }

    public DragPoint() {
        this((IBaseChart)null);
    }

   public void addDragListener(DragListener l) {
        listenerList.add(DragListener.class, l);
    }

    public void removeDragListener(DragListener l) {
        listenerList.remove(DragListener.class, l);
    }

    /**
     * Gets descriptive text.
     *
     * @return String
     */
    public String getDescription() {
        return Language.getString("DragPoint");
    }

    /**
     * Gets detailed descriptive text.
     *
     * @return String
     */
    public String getSummary() {
        return Language.getString("DragPointSummary");
    }

    /**
     * Determines whether user can drag series points vertically,
     * horizontally or both. <br>
     * Default value: Both
     *
     *
     * @return DragPointStyle
     */
    public DragPointStyle getStyle() {
        return style;
    }

    /**
     * Determines whether user can drag series points vertically,
     * horizontally or both. <br>
     * Default value: Both
     *
     *
     * @param value DragPointStyle
     */
    public void setStyle(DragPointStyle value) {
        style = value;
    }

    /**
     * Determines the mousebutton to activate DragPoint.<br>
     * Default value: MouseButtons.Left
     *
     * @return int
     */
    public int getButton() {
        return button;
    }

    /**
     * Sets which mousebutton activates DragPoint.<br>
     * Default value: MouseButtons.Left
     *
     * @param value int
     */
    public void setButton(int value) {
        button = value;
    }

    /**
     * Determines the type of DragPoint Cursor displayed.<br>
     * Default value: Hand
     *
     * @return Cursor
     */
    public Cursor getCursor() {
        return cursor;
    }

    /**
     * Determines the type of DragPoint Cursor displayed.<br>
     * Default value: Hand
     *
     * @param value Cursor
     */
    public void setCursor(Cursor value) {
        cursor = value;
    }

    private ISeries clickedSeries(Point p) {

        if (iSeries == null) {
            for (int t = 0; t < chart.getSeriesCount(); t++) {
                Series s = chart.getSeries(t);
                if ((s.getActive()) && (s.clicked(p) != -1)) {
                    return s;
                }
            }
        } else
        if (iSeries.clicked(p) != -1) {
            return iSeries;
        }

        return null;
    }

    public int getDragPoint() {
        return dragging;
    }

    public Cursor mouseEvent(FrameworkMouseEvent e, Cursor c) {

        if (e.getID() == FrameworkMouseEvent.MOUSE_RELEASED) {
            dragging = -1;
            fireDragged( new ChangeEvent(this));
        } else
        if (e.getID() == FrameworkMouseEvent.MOUSE_MOVED || e.getID() == FrameworkMouseEvent.MOUSE_DRAGGED) {

            if (dragging != -1) {

                boolean changed = false;

                //Change the Series point X and / or Y values...
                if ((style == DragPointStyle.X) ||
                    (style == DragPointStyle.BOTH)) {
                    theSeries.getXValues().value[dragging] = theSeries.
                            xScreenToValue(e.getX());
                    changed = true;
                }

                if ((style == DragPointStyle.Y) ||
                    (style == DragPointStyle.BOTH)) {
                    theSeries.getYValues().value[dragging] = theSeries.
                            yScreenToValue(e.getY());
                    changed = true;
                }

                // Notify the point values change...
                fireDragging( new ChangeEvent(this));

                if (changed) {
                    invalidate();
                }
                
                c = cursor;
                chart.setCancelMouse(true);
            } else {
                ISeries s = clickedSeries(e.getPoint());

                if (s != null) {
                    c = cursor;
                    chart.setCancelMouse(true);
                }
            }
        } else
        if ((e.getID() == FrameworkMouseEvent.MOUSE_PRESSED) && (e.getButton() == button)) {

            theSeries = null;
            dragging = -1;

            if (iSeries == null) {
                for (int t = 0; t < chart.getSeriesCount(); t++) {
                    Series s = chart.getSeries(t);
                    if (s.getActive()) {
                        dragging = s.clicked(e.getPoint());
                        if (dragging != -1) {
                            theSeries = s;
                            break;
                        }
                    }
                }
            } else {
                dragging = iSeries.clicked(e.getPoint());
                if (dragging != -1) {
                    theSeries = iSeries;
                }
            }
            if (dragging != -1) {
                chart.setCancelMouse(true);
            }
        }

        return c;
    }
}

