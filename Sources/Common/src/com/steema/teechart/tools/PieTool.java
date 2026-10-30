package com.steema.teechart.tools;

import com.steema.teechart.languages.Language;
import com.steema.teechart.events.FrameworkMouseEvent;

import com.steema.teechart.Cursor;
import com.steema.teechart.IBaseChart;
import com.steema.teechart.drawing.ChartPen;
import com.steema.teechart.drawing.Color;
import com.steema.teechart.drawing.Point;
import com.steema.teechart.styles.Pie;
import com.steema.teechart.drawing.Graphics3D;

/**
 *
 * <p>Title: PieTool class</p>
 *
 * <p>Description: Pie Slices tool.</p>
 *
 * <p>Copyright (c) 2005-2019 by Steema Software SL. All Rights Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 *
 */
public class PieTool extends ToolSeries {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private PieToolStyle style = PieToolStyle.FOCUS;
    private int eventKind = FrameworkMouseEvent.MOUSE_MOVED;
    private int iSlice = -1;

    public PieTool() {
        this((IBaseChart)null);
    }

    public PieTool(IBaseChart c) {
        super(c);
        getPen().setWidth(3);
    }

    private void focusSlice(int valueIndex, boolean focused) {
        Pie s = ((Pie) getSeries());

        if (style == PieToolStyle.EXPLODE) {
            int delta = focused ? 1 : -1;
            for (int t = 1; t <= 20; t++) {
                s.getExplodedSlice().setSlice(valueIndex, 2 * t * delta);
                chart.invalidate();
            }
        } else {
            if (!focused) {
                chart.getParent().refreshControl();
            } else {
                Color tmpColor = getPen().getColor();
                if (getSeries().getValueColor(valueIndex) == getPen().getColor()) {
                    if (getPen().getColor() == Color.BLACK) {
                        tmpColor = Color.WHITE;
                    } else {
                        tmpColor = Color.BLACK;
                    }
                }

                Graphics3D g3D=chart.getParent().checkGraphics();

                g3D.setPen(getPen());
                g3D.getPen().setColor(tmpColor);

                int previousSlice = (valueIndex == 0) ? getSeries().getCount() - 1 :
                                    valueIndex - 1;

                Point tmp = s.angleToPos(s.angles[previousSlice].EndAngle,
                                         s.getXRadius(),
                                         s.getYRadius());
                s.drawOutlineSlice(valueIndex);
            }
        }
    }


    /**
     * Gets descriptive text.
     *
     * @return String
     */
    public String getDescription() {
        return Language.getString("PieTool");
    }

    /**
     * Gets detailed descriptive text.
     *
     * @return String
     */
    public String getSummary() {
        return Language.getString("PieToolSummary");
    }

    public Cursor mouseEvent(FrameworkMouseEvent e, Cursor c) {
        if ((e.getID() == eventKind) && (getSeries() != null)) {

            int tmp2 = getSeries().clicked(e.getPoint());
            if (iSlice != tmp2) {
                if (iSlice != -1) {
                    focusSlice(iSlice, false);
                }
                iSlice = tmp2;
                if (iSlice != -1) {
                    focusSlice(iSlice, true);
                }
            }
        }

        return c;
    }

    /**
     * Returns the currently focused slice.  (Point index)
     *
     * @return int
     */
    public int getSlice() {
        return iSlice;
    }

    /**
     * Makes the pie tool draw a border around the focused pie slice.<br>
     * Set Style to Explode to explode and un-explode the focused slice.<br>
     * Default value: PieToolStyle.Focus
     *
     * @return PieToolStyle
     */
    public PieToolStyle getStyle() {
        return style;
    }

    /**
     * Makes the pie tool draw a border around the focused pie slice.<br>
     * Default value: PieToolStyle.Focus
     *
     * @param value PieToolStyle
     */
    public void setStyle(PieToolStyle value) {
        style = value;
    }

    /**
     * Makes the pie tool to be triggered when the selected FrameworkMouseEvent occurs.<br>
     * Set FrameworkMouseEvent to MOUSE_MOVED or MOUSE_PRESSED.<br>
     * Default value: FrameworkMouseEvent.MOUSE_MOVED
     *
     * @return int
     */
    public int getEventKind() {
        return eventKind;
    }

    /**
     * Makes the pie tool to be triggered when the selected FrameworkMouseEvent occurs.<br>
     * Default value: FrameworkMouseEvent.MOUSE_MOVED
     *
     * @param value int
     */
    public void setEventKind(int value) {
        eventKind = value;
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
}

