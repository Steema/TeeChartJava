package com.steema.teechart.tools;

import com.steema.teechart.drawing.Point;
import com.steema.teechart.events.DefaultResolvers;
import com.steema.teechart.languages.Language;
import com.steema.teechart.styles.MarksStyle;

import com.steema.teechart.Cursor;
import com.steema.teechart.IBaseChart;
import com.steema.teechart.events.TextResolver;
import com.steema.teechart.events.FrameworkMouseEvent;
import com.steema.teechart.styles.Series;
import com.steema.teechart.ToolTip;

/**
 *
 * <p>Title: MarksTip class</p>
 *
 * <p>Description: Marks Tip.<br>
 * Marks tip tool, use it to display "tips" or "hints" when the end-user moves
 * or clicks the mouse over a series point.</p>
 *
 * <p>Copyright (c) 2005-2019 by Steema Software SL. All Rights Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 *
 */
public class MarksTip extends ToolSeries {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private MarksTipMouseAction mouseAction = MarksTipMouseAction.MOVE;
    private MarksStyle style = MarksStyle.LABEL;

    public MarksTip() {
        this((IBaseChart)null);
    }

    public MarksTip(IBaseChart c) {
        super(c);
        readResolve();
    }

    protected Object readResolve() {
        customToolTip = DefaultResolvers.TEXT_RESOLVER;
        return super.readResolve();
    }

    transient protected TextResolver customToolTip;

    public void setToolTipResolver(TextResolver resolver) {
        if (resolver != null) {
            this.customToolTip = resolver;
        } else {
            removeToolTipResolver();
        }
    }

    public void removeToolTipResolver() {
        this.customToolTip = DefaultResolvers.TEXT_RESOLVER;
    }

    /**
     * Defines the text format of the Mark ToolTip.<br>
     * Default value: MarksStyle.Label
     *
     *
     * @return MarksStyle
     */
    public MarksStyle getStyle() {
        return style;
    }

    /**
     * Defines the text format of the Mark ToolTip.<br>
     * Default value: MarksStyle.Label
     *
     *
     * @param value MarksStyle
     */
    public void setStyle(MarksStyle value) {
        if (style != value) {
            style = value;
            invalidate();
        }
    }

    /**
     * Activates Mark Tip on mouse move or click.<br>
     * Default value: MarksTipMouseAction.Move
     *
     * @return MarksTipMouseAction
     */
    public MarksTipMouseAction getMouseAction() {
        return mouseAction;
    }

    /**
     * Activates Mark Tip on mouse move or click.<br>
     * Default value: MarksTipMouseAction.Move
     *
     * @param value MarksTipMouseAction
     */
    public void setMouseAction(MarksTipMouseAction value) {
        mouseAction = value;
        chart.getToolTip().setText(null);
    }

    /**
     * The time lag before the Tool Tip appears.<br>
     * Default value: 500
     *
     * @return int
     */
    public int getMouseDelay() {
        if (chart==null) {
            return 0;
        } else {
            ToolTip tmp = chart.getToolTip();
            return (tmp==null) ? 0 : tmp.getInitialDelay();
        }
    }

    /**
     * Sets the time lag before the Tool Tip appears.<br>
     * Default value: 500
     *
     * @param value int
     */
    public void setMouseDelay(int value) {
        if (chart!=null) {
            if (chart.getToolTip()!=null) {
                chart.getToolTip().setInitialDelay(value);
            }
        }
    }
    
        /**
     * The time period during which the Tool Tip appears.<br>
     * Default value: 4000 (ms)
     *
     * @return int
     */
    public int getHideDelay() {
        if (chart==null) {
            return 0;
        } else {
            ToolTip tmp = chart.getToolTip();
            return (tmp==null) ? 0 : tmp.getDismissDelay();
        }
    }

    /**
     * Sets the time period during which the Tool Tip appears.<br>
     * Default value: 4000 (ms)
     *
     * @param value int
     */
    public void setHideDelay(int value) {
        if (chart!=null) {
            if (chart.getToolTip()!=null) {
                chart.getToolTip().setDismissDelay(value);
            }
        }
    }

    /**
     * Gets descriptive text.
     *
     * @return String
     */
    public String getDescription() {
        return Language.getString("MarksTipTool");
    }

    /**
     * Gets detailed descriptive text.
     *
     * @return String
     */
    public String getSummary() {
        return Language.getString("MarksTipSummary");
    }

    private class FindClicked {
        Series series;
        int index;
    }


    private FindClicked findClickedSeries(Point p) {
        FindClicked tmp=new FindClicked();

        for (int t = chart.getSeriesCount()-1; t >= 0 ; t--) {
            tmp.series = chart.getSeries(t);
            tmp.index = tmp.series.clicked(p);
            if (tmp.index != -1) {
                return tmp;
            }
        }

        // no series
        tmp.series = null;
        tmp.index = -1;
        return tmp;
    }

    public Cursor mouseEvent(FrameworkMouseEvent e, Cursor c) {

        c = super.mouseEvent(e, c);
        
        chart.getToolTip().setText("");

        if (((getMouseAction() == MarksTipMouseAction.MOVE) &&
             (e.getID() == FrameworkMouseEvent.MOUSE_MOVED)) ||
            ((getMouseAction() == MarksTipMouseAction.CLICK) &&
             (e.getID() == FrameworkMouseEvent.MOUSE_PRESSED))) {
            Series tmpSeries = null;
            int tmp = -1;

            // find which series is under XY
            if (iSeries != null) {
                tmpSeries = iSeries;
                tmp = tmpSeries.clicked(e.getPoint());
            } else {
                FindClicked tmp2 = findClickedSeries(e.getPoint()); /* 5.02 */
                tmpSeries = tmp2.series;
                tmp = tmp2.index;
                //System.out.println("tmp="+String.valueOf(tmp));
            }

            // if not ok, cancel hint...
            if (tmp == -1) {

                boolean tmpCancel = true;

                //* do not cancel if other MarkTipTools exist...
                 for (int tt = 0; tt < chart.getTools().size(); tt++) {
                     Tool t = chart.getTools().getTool(tt);
                     if ((t != this) && (t instanceof MarksTip)) {
                         /* 5.01 */
                         tmpCancel = false;
                         break;
                     }
                 }

                if (tmpCancel) {
                    chart.getToolTip().setText("");
                    chart.getToolTip().deactivate();
                }
            } else {
                // show hint !
                MarksStyle tmpStyle = tmpSeries.getMarks().getStyle();
                boolean tmpOld = chart.getAutoRepaint();
                chart.setAutoRepaint(false);
                tmpSeries.getMarks().setStyle(style);

                String tmpText = tmpSeries.getValueMarkText(tmp);

                tmpText = customToolTip.getText(this,tmpText);
                tmpSeries.getMarks().setStyle(tmpStyle);

                if (! chart.getToolTip().getText().equals(tmpText)) {
                	  chart.getToolTip().prepareMarks(tmpSeries.getMarks());
                    chart.getToolTip().setText(" " + tmpText); //spacer to clear cursor
                    chart.getToolTip().activate();
                }
                chart.setAutoRepaint(tmpOld);
            }
        }

        return c;
    }

    public void setActive(boolean value) {
        super.setActive(value);
        if (!getActive()) {
            chart.getToolTip().setText(null);
        }
    }
    
    public int getInitialDelay() {
    	return chart.getToolTip().getInitialDelay(); 
    }
    
    public void setInitialDelay(int value) 
    {
    	chart.getToolTip().setInitialDelay(value);
    }
}

