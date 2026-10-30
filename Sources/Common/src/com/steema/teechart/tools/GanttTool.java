package com.steema.teechart.tools;

import com.steema.teechart.languages.Language;
import com.steema.teechart.events.ChangeEvent;
import com.steema.teechart.events.FrameworkMouseEvent;

import com.steema.teechart.events.DragListener;

import com.steema.teechart.Cursor;
import com.steema.teechart.IBaseChart;
import com.steema.teechart.events.SizeListener;
import com.steema.teechart.styles.Gantt;

/**
 *
 * <p>Title: GanttTool class</p>
 *
 * <p>Description: Gantt Tool.</p>
 *
 * <p>Copyright (c) 2005-2019 by Steema Software SL. All Rights Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 *
 */
public class GanttTool extends ToolSeries {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	final static public int HANDLE_ALL = 0;
    final static public int HANDLE_START = 1;
    final static public int HANDLE_END = 2;

    private boolean allowDrag = true;
    private boolean allowResize = true;
    private int bar = -1;
    private int barPart;
    private Cursor cursorDrag = Cursor.HAND;
    private Cursor cursorResize = Cursor.SIZEWE;
    private int minPixels = 5;
    private double xOriginal;


    public GanttTool() {
        this((IBaseChart)null);
    }

    public GanttTool(IBaseChart c) {
        super(c);
    }

    public void addSizeListener(SizeListener l) {
        listenerList.add(SizeListener.class, l);
    }

    public void removeSizeListener(SizeListener l) {
        listenerList.remove(SizeListener.class, l);
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
        return Language.getString("GanttTool");
    }

    /**
     * Gets detailed descriptive text.
     *
     * @return String
     */
    public String getSummary() {
        return Language.getString("GanttToolSummary");
    }

    public Gantt getGantt() {
        return ((iSeries != null) && (iSeries instanceof Gantt)) ?
                (Gantt) iSeries : null;
    }

    public DragGanttBar getDragItem() {
        return new DragGanttBar(bar, barPart);
    }

    /**
     * When true, the end user can use the mouse to click and drag Gantt series
     * bars. <br>
     * Default value: true
     *
     * @return boolean
     */
    public boolean getAllowDrag() {
        return allowDrag;
    }

    public void setAllowDrag(boolean value) {
        allowDrag = value;
    }

    /**
     * When true, the end user can use the mouse to resize Gantt series bars.
     * <br>To resize a gantt bar, click and drag the mouse when it's over an
     * edge (left or right) of the gantt bar. <br>
     * Default value: true
     *
     * @return boolean
     */
    public boolean getAllowResize() {
        return allowResize;
    }

    /**
     * When true, the end user can use the mouse to resize Gantt series bars.
     * <br>Default value: true
     *
     * @param value boolean
     */
    public void setAllowResize(boolean value) {
        allowResize = value;
    }

    /**
     * The cursor to show when the user is dragging a Gantt bar. <br>
     * Default value: Hand
     *
     * @return Cursor
     */
    public Cursor getCursorDrag() {
        return cursorDrag;
    }

    /**
     * Defines the cursor to show when the user is dragging a Gantt bar. <br>
     * Default value: Hand
     *
     * @param value Cursor
     */
    public void setCursorDrag(Cursor value) {
        cursorDrag = value;
    }

    /**
     * The cursor to show when the end user passes the mouse over an
     * edge (left or right) of a gantt series bar. <br>
     * Default value; SizeWE
     *
     * @return Cursor
     */
    public Cursor getCursorResize() {
        return cursorResize;
    }

    /**
     * Defines the cursor to show when the end user passes the mouse over an
     * edge (left or right) of a gantt series bar. <br>
     * Default value; SizeWE
     *
     * @param value Cursor
     */
    public void setCursorResize(Cursor value) {
        cursorResize = value;
    }

    /**
     * The amount in screen pixels to consider the user is moving the
     * mouse over a gantt bar edge (left or right). <br>
     * Default value: 5
     *
     * @return int
     */
    public int getMinPixels() {
        return minPixels;
    }

    /**
     * Defines the amount in screen pixels to consider the user is moving the
     * mouse over a gantt bar edge (left or right). <br>
     * Default value: 5
     *
     * @param value int
     */
    public void setMinPixels(int value) {
        minPixels = value;
    }

    public Cursor mouseEvent(FrameworkMouseEvent e, Cursor c) {
        if (getGantt() != null) {
            if (e.getID()== FrameworkMouseEvent.MOUSE_RELEASED) {
                bar = -1;
            } else
            if (e.getID() == FrameworkMouseEvent.MOUSE_MOVED || e.getID() == FrameworkMouseEvent.MOUSE_DRAGGED) {
                c = mouseMove(e, c);
            } else
            if (e.getID() == FrameworkMouseEvent.MOUSE_PRESSED) {
                c = mouseDown(e, c);
            }
        }
        return c;
    }

    private Cursor mouseMove(FrameworkMouseEvent e, Cursor c) {
        if (bar == -1) {
            if (allowResize) {
                int half = getGantt().getPointer().getVertSize() / 2;

                for (int t = 0; t < getGantt().getCount(); t++) {
                    int tmp = getGantt().getVertAxis().calcPosValue(getGantt().getYValues().
                            value[t]);

                    if ((e.getY() >= tmp - half) && (e.getY() <= tmp + half)) {

                        tmp = getGantt().getHorizAxis().calcPosValue(getGantt().
                                getStartValues().value[t]);

                        if (Math.abs(e.getX() - tmp) < minPixels) {
                            c = cursorResize;
                            chart.setCancelMouse( true);
                            break;
                        } else {
                            tmp = getGantt().getHorizAxis().calcPosValue(getGantt().
                                    getEndValues().value[t]);

                            if (Math.abs(tmp - e.getX()) < minPixels) {
                                c = cursorResize;
                                chart.setCancelMouse(true);
                                break;
                            }

                        }
                    }
                }
            }

            if (allowDrag && (bar == -1) && (getGantt().clicked(e.getPoint()) != -1)) {
                c = cursorDrag;
                chart.setCancelMouse(true);
            }
        } else { // Dragging bar...
            double change = getGantt().getHorizAxis().calcPosPoint(e.getX()) -
                            xOriginal;

            if (barPart==HANDLE_ALL) {
                // drag bar
                getGantt().getStartValues().value[bar] += change;
                getGantt().getEndValues().value[bar] += change;

                fireDragging(new ChangeEvent(this));
            }
            else
            if (barPart==HANDLE_START) {
                // resize bar at start
                getGantt().getStartValues().value[bar] += change;
                // limit
                if (getGantt().getStartValues().value[bar] > getGantt().getEndValues().value[bar]) {
                    getGantt().getStartValues().value[bar] = getGantt().getEndValues().value[bar];
                }

                fireResized(new ChangeEvent(this));
            }
            else
            if (barPart==HANDLE_END) {
                // resize bar at end
                getGantt().getEndValues().value[bar] += change;
                // limit
                if (getGantt().getEndValues().value[bar] < getGantt().getStartValues().value[bar]) {
                    getGantt().getEndValues().value[bar] = getGantt().getStartValues().value[bar];
                }

                fireResized(new ChangeEvent(this));
            }

            xOriginal = getGantt().getHorizAxis().calcPosPoint(e.getX());

            getGantt().invalidate();
        }
        return c;
    }

    private Cursor mouseDown(FrameworkMouseEvent e, Cursor c) {
        bar = -1;

        if (allowResize) {
            int half = getGantt().getPointer().getVertSize() / 2;

            xOriginal = chart.getAxes().getBottom().calcPosPoint(e.getX());

            // Check bar under mouse ( to resize at start or end )
            for (int t = 0; t < getGantt().getCount(); t++) {
                int tmp = getGantt().getVertAxis().calcPosValue(getGantt().getYValues().value[t]);

                if ((e.getY() >= tmp - half) && (e.getY() <= tmp + half)) {
                    int tmp1 = getGantt().getHorizAxis().calcPosValue(getGantt().
                            getStartValues().value[t]);
                    int tmp2 = getGantt().getHorizAxis().calcPosValue(getGantt().
                            getEndValues().value[t]);

                    if (Math.abs(e.getX() - tmp1) < minPixels) {
                        bar = t;
                        barPart = HANDLE_START;
                        break;
                    } else
                    if (Math.abs(e.getX() - tmp2) < minPixels) {
                        bar = t;
                        barPart = HANDLE_END;
                        break;
                    }
                }
            }
        }

        // Check bar under mouse (to drag)
        if ((bar == -1) && allowDrag) {
            bar = getGantt().clicked(e.getPoint());
            if (bar != -1) {
                barPart = HANDLE_ALL;
            }
        }
        return c;
    }

    public final class DragGanttBar {
        int bar;
        int part;

        DragGanttBar(int bar, int part)  {
            this.bar = bar;
            this.part = part;
        }

        public int getBar() {
            return this.bar;
        }

        public int getBarPart() {
            return this.part;
        }
    }
}

