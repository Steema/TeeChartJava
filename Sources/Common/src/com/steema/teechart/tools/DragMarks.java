package com.steema.teechart.tools;

import com.steema.teechart.languages.Language;

import com.steema.teechart.styles.SeriesMarksPosition;
import com.steema.teechart.Cursor;
import com.steema.teechart.IBaseChart;
import com.steema.teechart.styles.ISeries;
import com.steema.teechart.drawing.Point;
import com.steema.teechart.events.FrameworkMouseEvent;

/**
 *
 * <p>Title: DragMarks class</p>
 *
 * <p>Description: DragMarks Tool.</p>
 *
 * <p>Copyright (c) 2005-2019 by Steema Software SL. All Rights Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 *
 */
public class DragMarks extends ToolSeries {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private int oldX, oldY;

    SeriesMarksPosition position;

    /**
     * Gets descriptive name.
     *
     * @return String
     */
    public String getDescription() {
        return Language.getString("DragMarksTool");
    }

    /**
     * Gets detailed descriptive text.
     *
     * @return String
     */
    public String getSummary() {
        return Language.getString("DragMarksSummary");
    }

    public DragMarks() {
        this((IBaseChart)null);
    }

    public DragMarks(IBaseChart c) {
        super(c);
    }

    static private boolean checkCursorSeries(ISeries s, Point p) {
        return s.getActive() && s.getMarks().getVisible() && (s.getMarks().clicked(p) != -1);
    }

    private Cursor checkCursor(Cursor c, Point p) {
        boolean tmp = false;

        if (iSeries != null) {
            tmp = checkCursorSeries(iSeries, p);
        } else {
            for (int t = chart.getSeriesCount() - 1; t >= 0; t--) {
                tmp = checkCursorSeries(chart.getSeries(t), p);
                if (tmp) {
                    break;
                }
            }
        }

        if (tmp) {
            chart.setCancelMouse(true);
            c = Cursor.HAND;
        }
        return c;
    }

    private Cursor mouseMove(FrameworkMouseEvent e, Cursor c) {
        if (position == null) {
            c=checkCursor(c, e.getPoint());
        } else {
            int difX = e.getX() - oldX;
            int difY = e.getY() - oldY;
            position.custom = true;
            position.leftTop.x += difX;
            position.leftTop.y += difY;
            position.arrowTo.x += difX;
            position.arrowTo.y += difY;
            oldX = e.getX();
            oldY = e.getY();
            chart.setCancelMouse(true);
            invalidate();
        }
        return c;
    }

    private int checkSeries(ISeries s, Point p) {
        int result = -1;
        if (s.getActive()) {
            result = s.getMarks().clicked(p);
            if (result != -1) {
                position = s.getMarks().getPositions().getPosition(result);
                return result;
            }
        }
        return result;
    }

    private void mouseDown(FrameworkMouseEvent e) {
        if (iSeries != null) {
            checkSeries(iSeries, e.getPoint());
        } else {
            for (int t = chart.getSeriesCount() - 1; t >= 0; t--) {
                if (checkSeries(chart.getSeries(t), e.getPoint()) != -1) {
                    break;
                }
            }
        }

        if (position != null) {
            oldX = e.getX();
            oldY = e.getY();
        }
    }

    public Cursor mouseEvent(FrameworkMouseEvent e, Cursor c) {
        if (e.getID() == FrameworkMouseEvent.MOUSE_RELEASED) {
            position = null;
        } else
        if (e.getID() == FrameworkMouseEvent.MOUSE_PRESSED) {
            mouseDown(e);
            if (position != null) {
                chart.setCancelMouse(true);
            }
        } else
        if (e.getID() == FrameworkMouseEvent.MOUSE_MOVED || e.getID() == FrameworkMouseEvent.MOUSE_DRAGGED) {
            c=mouseMove(e, c);
        }

        return c;
    }
}

