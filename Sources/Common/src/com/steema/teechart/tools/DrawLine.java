package com.steema.teechart.tools;

import com.steema.teechart.events.ChangeEvent;
import com.steema.teechart.events.ChartDrawEvent;
import com.steema.teechart.languages.Language;
import com.steema.teechart.events.DragListener;

import com.steema.teechart.drawing.Color;
import com.steema.teechart.Cursor;
import com.steema.teechart.IBaseChart;
import com.steema.teechart.drawing.ChartPen;
import com.steema.teechart.drawing.Graphics3D;
import com.steema.teechart.drawing.Point;
import com.steema.teechart.Rectangle;
import com.steema.teechart.drawing.IGraphics3D;

import com.steema.teechart.events.FrameworkMouseEvent;
import java.util.EventListener;

/**
 *
 * <p>Title: DrawLine class</p>
 *
 * <p>Description: Draw Line tool.</p>
 *
 * <p>Copyright (c) 2005-2019 by Steema Software SL. All Rights Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 *
 */
public class DrawLine extends ToolSeries
{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public Point fromPoint = new Point();
    public Point toPoint = new Point();
    protected DrawLineHandle iHandle = DrawLineHandle.NONE;
    private int button = FrameworkMouseEvent.BUTTON1;
    private boolean fSelectNew = true;
    private boolean enableDraw = true;
    private boolean enableSelect = true;
    private DrawLines lines = new DrawLines();
    private boolean firstMouseDown;
    private DrawLineItem tmpLine = null;
    private boolean drawing;
    private Point point;
    private DrawLineItem selected;
    private DrawLineStyle style = DrawLineStyle.LINE;

    public DrawLine()
    {
        this((IBaseChart) null);
    }

    public DrawLine(IBaseChart c)
    {
        super(c);
        lines.tool = this;
    }
    transient protected DrawLineSelector drawLineSelector;

    public void addDrawLineSelector(DrawLineSelector selector)
    {
        this.drawLineSelector = selector;
    }

    public void removeDrawLineSelector()
    {
        this.drawLineSelector = null;
    }

    public void addDragListener(DragListener l)
    {
        listenerList.add(DragListener.class, l);
    }

    public void removeDragListener(DragListener l)
    {
        listenerList.remove(DragListener.class, l);
    }

    public void addDrawLineListener(DrawLineListener l)
    {
        listenerList.add(DrawLineListener.class, l);
    }

    public void removeDrawLineListener(DrawLineListener l)
    {
        listenerList.remove(DrawLineListener.class, l);
    }

    protected void fireNew(ChangeEvent e)
    {
        Object[] listeners = listenerList.getListenerList();
        for (int i = listeners.length - 2; i >= 0; i -= 2)
        {
            if (listeners[i] == DrawLineListener.class)
            {
                ((DrawLineListener) listeners[i + 1]).lineNew(e);
            }
        }
    }

    protected void fireSelected(ChangeEvent e)
    {
        Object[] listeners = listenerList.getListenerList();
        for (int i = listeners.length - 2; i >= 0; i -= 2)
        {
            if (listeners[i] == DrawLineListener.class)
            {
                ((DrawLineListener) listeners[i + 1]).lineSelected(e);
            }
        }
    }

    /**
     * Returns the list of lines drawn on the Chart.<br>
     *
     * @return DrawLines
     */
    public DrawLines getLines()
    {
        return lines;
    }

    /**
     * The mousebutton to activate the DrawLineTool.<br>
     * Default value: MouseButtons.Left
     *
     * @return int
     */
    public int getButton()
    {
        return button;
    }

    /**
     * Defines which mousebutton activates the DrawLineTool.<br>
     * Default value: MouseButtons.Left
     *
     * @param value int
     */
    public void setButton(int value)
    {
        button = value;
    }

    /**
     * Enables/Disables the drawing of lines on the chart by the user.<br>
     * All drawn lines are stored in the Lines method.<br>
     * Default value: true
     *
     * @return boolean
     */
    public boolean getEnableDraw()
    {
        return enableDraw;
    }

    /**
     * Enables/Disables the drawing of lines on the chart by the user.<br>
     * Default value: true
     *
     * @param value boolean
     */
    public void setEnableDraw(boolean value)
    {
        enableDraw = value;
    }

    /**
     * Enables selection of lines for repositioning on the Chart.<br>
     * Default value: true
     *
     * @return boolean
     */
    public boolean getEnableSelect()
    {
        return enableSelect;
    }

    /**
     * Enables selection of lines for repositioning on the Chart when true.<br>
     * Default value: true
     *
     * @param value boolean
     */
    public void setEnableSelect(boolean value)
    {
        enableSelect = value;
    }

    /**
     * Gets descriptive text.
     *
     * @return String
     */
    public String getDescription()
    {
        return Language.getString("DrawLineTool");
    }

    /**
     * Gets detailed descriptive text.
     *
     * @return String
     */
    public String getSummary()
    {
        return Language.getString("DrawLineSummary");
    }

    private void doDrawLine(IGraphics3D g, Point startPos, Point endPos,
            DrawLineStyle AStyle)
    {
        boolean oldBrush;
        if (!super.getChart().getAspect().getView3D())
        {
            if (AStyle != DrawLineStyle.LINE)
            {
                if (AStyle == DrawLineStyle.HORIZPARALLEL)
                {
                    g.horizontalLine(startPos.x, endPos.x, startPos.y);
                    g.horizontalLine(startPos.x, endPos.x, endPos.y);
                }
                else if (AStyle == DrawLineStyle.RECTANGLE)
                {
                    oldBrush = g.getBrush().getVisible();
                    g.getBrush().setVisible(false);
                    g.rectangle(Rectangle.fromLTRB(startPos.x, startPos.y, endPos.x, endPos.y));
                    g.getBrush().setVisible(oldBrush);
                }
                else if (AStyle == DrawLineStyle.ELLIPSE)
                {
                    oldBrush = g.getBrush().getVisible();
                    g.getBrush().setVisible(false);
                    int startX = startPos.x < endPos.x ? startPos.x : endPos.x;
                    int startY = startPos.y < endPos.y ? startPos.y : endPos.y;
                    int endX = startPos.x > endPos.x ? startPos.x : endPos.x;
                    int endY = startPos.y > endPos.y ? startPos.y : endPos.y;
                    
                    g.ellipse(startX, startY, endX, endY);
                    g.getBrush().setVisible(oldBrush);
                }
                else
                {
                    g.verticalLine(startPos.x, startPos.y, endPos.y);
                    g.verticalLine(endPos.x, startPos.y, endPos.y);
                }
            }
            else
            {
                g.line(startPos.x, startPos.y, endPos.x, endPos.y);
            }
        }
        else if (AStyle != DrawLineStyle.LINE)
        {
            if (AStyle == DrawLineStyle.HORIZPARALLEL)
            {
                g.horizontalLine(startPos.x, endPos.x, startPos.y, 0);
                g.horizontalLine(startPos.x, endPos.x, endPos.y, 0);
            }
            else if (AStyle == DrawLineStyle.RECTANGLE)
            {
                oldBrush = g.getBrush().getVisible();
                g.getBrush().setVisible(false);
                g.rectangle(Rectangle.fromLTRB(startPos.x, startPos.y, endPos.x, endPos.y), 0);
                g.getBrush().setVisible(oldBrush);
            }
            else if (AStyle == DrawLineStyle.ELLIPSE)
            {
                oldBrush = g.getBrush().getVisible();
                g.getBrush().setVisible(false);
                g.ellipse(startPos.x, startPos.y, endPos.x, endPos.y, 0);
                g.getBrush().setVisible(oldBrush);
            }
            else
            {
                g.verticalLine(startPos.x, startPos.y, endPos.y, 0);
                g.verticalLine(endPos.x, startPos.y, endPos.y, 0);
            }
        }
        else
        {
            g.moveTo(startPos.x, startPos.y, 0);
            g.lineTo(endPos.x, endPos.y, 0);
        }
    }

    private boolean clickedLine(DrawLineItem line,
            DrawLineHandle handle, Point p)
    {

        Point tmpStart = axisPoint(line.startPos);
        Point tmpEnd = axisPoint(line.endPos);

        if (handle == DrawLineHandle.START)
        {
            return line.getStartHandle().contains(p);
        }
        else if (handle == DrawLineHandle.END)
        {
            return line.getEndHandle().contains(p);
        }
        else
        {
            if (line.style == DrawLineStyle.LINE)
            {
                return Graphics3D.pointInLineTolerance(p,
                        tmpStart.x, tmpStart.y,
                        tmpEnd.x, tmpEnd.y, clickTolerance);
            }
            else if (line.style == DrawLineStyle.HORIZPARALLEL)
            {
                boolean result = Graphics3D.pointInLineTolerance(p,
                        tmpStart.x, tmpStart.y,
                        tmpEnd.x, tmpStart.y, clickTolerance);
                if (!result)
                {
                    result = Graphics3D.pointInLineTolerance(p,
                            tmpStart.x, tmpEnd.y,
                            tmpEnd.x, tmpEnd.y, clickTolerance);
                }
                return result;
            }
            else if (line.style == DrawLineStyle.RECTANGLE || line.style == DrawLineStyle.ELLIPSE)
            {
                return Rectangle.fromLTRB(tmpStart.x, tmpStart.y, tmpEnd.x, tmpEnd.y).contains(p.x, p.y);
            }
            else
            {
                boolean result = Graphics3D.pointInLineTolerance(p,
                        tmpStart.x, tmpStart.y,
                        tmpStart.x, tmpEnd.y, clickTolerance);
                if (!result)
                {
                    result = Graphics3D.pointInLineTolerance(p,
                            tmpEnd.x, tmpStart.y,
                            tmpEnd.x, tmpEnd.y, clickTolerance);
                }
                return result;
            }
        }
    }

    private DrawLineItem internalClicked(Point point, DrawLineHandle handle)
    {

         chart.getGraphics3D().calculate2DPosition(point.x, point.y, 0);
         Point p = new Point(point.x, point.y);
        // first, find if clicked the selected line...
        if ((selected != null) && clickedLine(selected, handle, p))
        {
            return selected;
        }

        // find which clicked line...
        for (int t = 0; t < lines.size(); t++)
        {
            DrawLineItem line = lines.getLine(t);
            if (clickedLine(line, handle, p))
            {
                return line;
            }
        }

        return null;
    }

    public DrawLineItem clicked(int x, int y)
    {
        // try first with whole line...
        DrawLineItem result = internalClicked(new Point(x, y), DrawLineHandle.NONE);
        // try then at start and end line points...
        if (result == null)
        {
            result = internalClicked(new Point(x, y), DrawLineHandle.START);
        }
        if (result == null)
        {
            result = internalClicked(new Point(x, y), DrawLineHandle.END);
        }
        return result;
    }

    public DrawLineItem clicked(Point p)
    {
        // try first with whole line...
        DrawLineItem result = internalClicked(p, DrawLineHandle.NONE);

        // try then at start and end line points...
        if (result == null)
        {
            result = internalClicked(p, DrawLineHandle.START);
        }
        if (result == null)
        {
            result = internalClicked(p, DrawLineHandle.END);
        }
        return result;
    }

    public Point axisPoint(Point.Double p)
    {
        // convert from axis double XY to screen pixels XY
        return new Point(getHorizAxis().calcPosValue(p.x),
                getVertAxis().calcPosValue(p.y));
    }

    public Point.Double screenPoint(Point p)
    {
        // convert from screen pixels XY position to axis double XY
        return new Point.Double(getHorizAxis().calcPosPoint(p.x),
                getVertAxis().calcPosPoint(p.y));
    }

    public void deleteSelected()
    {
        if (selected != null)
        {
            drawing = false; // 5.02
            iHandle = DrawLineHandle.NONE; // 5.02
            lines.remove(selected);
            selected = null;
        }
    }

    /**
     * Returns the line or lines that are currently selected.
     *
     * @return DrawLineItem
     */
    public DrawLineItem getSelected()
    {
        return selected;
    }

    public void setSelected(DrawLineItem value)
    {
        if (selected != value)
        {
            selected = value;
            invalidate();
        }
    }

    /**
     * Element Pen characteristics.
     *
     * @return ChartPen
     */
    public ChartPen getPen()
    {
        if (pPen == null)
        {
            pPen = new ChartPen(chart, Color.BLACK);
        }
        return pPen;
    }
    public void setPen(ChartPen value)
    {
        pPen = value;
    }

//    private void redrawLine(DrawLineItem line)
//    {
//        IGraphics3D g = chart.getGraphics3D();
//
//        if (chart != null)
//        { //&& g.ValidState()) {
//            g.setPen(getPen());
//            DrawLineStyle s = (line == null) ? DrawLineStyle.LINE
//                    : line.style;
//            if (line != null)
//            {
//                doDrawLine(g, axisPoint(line.startPos), axisPoint(line.endPos), s);
//            }
//
//        }
//    }
    private void redrawLine(DrawLineItem line)
    {

      redrawLine(chart.getGraphics3D(), line);
   }

    protected void redrawLine(IGraphics3D g, DrawLineItem line)
    {

      if ((getChart() != null))
      {
        DrawLineStyle s = (line == null) ? this.getDrawLineStyle() : line.getDrawLineStyle();

        if (line != null && line.getPen() != null)
        {
          g.setPen(line.getPen());
        }
        else
        {
          g.setPen(getPen());
        }

        clipDrawingRegion();
        if (line == null)
        {
          doDrawLine(g, this.fromPoint, this.toPoint, s);
        }
        else
        {
          doDrawLine(g, axisPoint(line.startPos), axisPoint(line.endPos), s);
        }

        g.unClip();
      }
    }

    
    protected void clipDrawingRegion()
    {
        Rectangle r;

        if (getSeries() != null)
        {
            r = Rectangle.fromLTRB(this.getSeries().getHorizAxis().iStartPos, this.getSeries().getVertAxis().iStartPos,
                    this.getSeries().getHorizAxis().iEndPos, this.getSeries().getVertAxis().iEndPos);
        }
        else
        {
            r = super.getChart().getChartRect();
        }

        if (chart.canClip())
        {
            chart.getGraphics3D().clipCube(r, 0, super.getChart().getAspect().getWidth3D());
        }
    }

    @Override
    public void chartEvent(ChartDrawEvent ce)
    {
        super.chartEvent(ce);
        if (((ce.getID() == ChartDrawEvent.PAINTED)
                && (ce.getDrawPart() == ChartDrawEvent.CHART))
                && (lines.size() > 0))
        {
            //chart.graphics3D.BackMode=cbmTransparent;
            clipDrawingRegion();

            for (int t = 0; t < lines.size(); t++)
            {
                redrawLine(lines.getLine(t));
            }

            if (selected != null)
            {
                selected.drawHandles();
            }
            if (drawing)
            {
                redrawLine(selected);
            }
            chart.getGraphics3D().unClip();

        }
    }

    private final class CheckCursorResults
    {

        boolean result;
        Cursor cursor;
    }

    /**
     * Returns True if mouse is over selected line ending points or
     * over any non-selected line.<br>
     * If True, the Chart cursor is changed.<br>
     *
     * @param p Point
     * @param c Cursor
     * @return CheckCursorResults
     */
    private CheckCursorResults checkCursor(Point p, Cursor c)
    {
        CheckCursorResults r = new CheckCursorResults();
        r.cursor = Cursor.DEFAULT;
        r.result = false;

        if ((selected != null) && ((internalClicked(p, DrawLineHandle.START) == selected)
                || (internalClicked(p, DrawLineHandle.END) == selected)))
        { // ending points
            r.cursor = Cursor.CROSS;
            r.result = true;
        }
        else if (clicked(p) != null)
        { // over a line
            r.cursor = Cursor.HAND;
            r.result = true;
        }

        return r;
    }

    private void setSelection(Point P)
    {
        fromPoint = axisPoint(tmpLine.startPos);
        toPoint = axisPoint(tmpLine.endPos);
        point = new Point(P.x, P.y);
    }

    private Cursor doMouseDown(FrameworkMouseEvent e, Cursor c)
    {
        if (e.getButton() == button)
        {
            tmpLine = enableSelect ? clicked(e.getPoint()) : null;
            //firstMouseDown = true;

            if (tmpLine != null)
            { // clicked line, do select
                setSelection(e.getPoint());
                iHandle = DrawLineHandle.SERIES;
//                fromPoint = axisPoint(tmpLine.startPos);
//                toPoint = axisPoint(tmpLine.endPos);
//                point = new Point(e.getPoint());

                if (tmpLine != selected)
                {
                    boolean tmpAllow = true;

                    if (drawLineSelector != null)
                    {
                        tmpAllow = drawLineSelector.selectable(this, tmpLine, tmpAllow);
                    }

                    if (tmpAllow)
                    { // change selected line
                        if (selected != null)
                        {
                            selected = tmpLine;
                            invalidate();
                        }
                        else
                        {
                            selected = tmpLine;
                            selected.drawHandles();
                        }

                        fireSelected(new ChangeEvent(this));
                    }
                }
                else if (internalClicked(e.getPoint(), DrawLineHandle.START) != null)
                {
                    iHandle = DrawLineHandle.START;
                }
                else if (internalClicked(e.getPoint(), DrawLineHandle.END) != null)
                {
                    iHandle = DrawLineHandle.END;
                }

                chart.setCancelMouse(true);
            }
            else
            { // un-select
                selected = null;
                if (enableDraw)
                {
                    drawing = true;
                    fromPoint = new Point(e.getPoint());
                    toPoint.x = fromPoint.x;
                    toPoint.y = fromPoint.y;
                    redrawLine(selected);
                    chart.setCancelMouse(true);
                }
            }
        }
        return c;
    }

    private void repositionLine(DrawLineItem line)
    {
        line.startPos = screenPoint(fromPoint);
        line.endPos = screenPoint(toPoint);
    }

    private Cursor doMouseMove(FrameworkMouseEvent e, Cursor c)
    {
        if (drawing || (iHandle != DrawLineHandle.NONE))
        { // drawing or dragging

            Point p = new Point(e.getPoint());
            this.invalidate();
            if (drawing)
            {
                toPoint = p;
                if (firstMouseDown)
                {
                    tmpLine = new DrawLineItem(this);
                    firstMouseDown = false;
                }
            }
            else if (iHandle == DrawLineHandle.START)
            {
                fromPoint = p;
            }
            else if (iHandle == DrawLineHandle.END)
            {
                toPoint = p;
            }
            else if (iHandle == DrawLineHandle.SERIES)
            {
                fromPoint.x += e.getX() - point.x;
                fromPoint.y += e.getY() - point.y;
                toPoint.x += e.getX() - point.x;
                toPoint.y += e.getY() - point.y;
                point = p;
            }
            fireDragging(new ChangeEvent(this));
            if (tmpLine != null)
            {
                repositionLine(tmpLine);
            }
            if (selected != null)
            {
                repositionLine(selected);
            }
            invalidate();
            chart.setCancelMouse(true);

        }
        else if (enableSelect)
        { // change the cursor if mouse is over lines
            CheckCursorResults r = checkCursor(e.getPoint(), c);
            chart.setCancelMouse(r.result);
            c = r.cursor;
        }

        return c;
    }

    private Cursor doMouseUp(FrameworkMouseEvent e, Cursor c)
    {
        if (e.getButton() == button)
        {
            if (iHandle != DrawLineHandle.NONE && selected != null)
            { //CDI TF02010059,  if dragging...
                if ((iHandle == DrawLineHandle.START) || (iHandle == DrawLineHandle.SERIES))
                {
                    selected.startPos = screenPoint(fromPoint);
                }
                if ((iHandle == DrawLineHandle.END) || (iHandle == DrawLineHandle.SERIES))
                {
                    selected.endPos = screenPoint(toPoint);
                }
                iHandle = DrawLineHandle.NONE;
                // stop dragging, repaint
                invalidate();

                fireDragged(new ChangeEvent(this));
            }
            else if (drawing)
            { // drawing a new line...
                if ((this.fromPoint.x != this.toPoint.x) || (this.fromPoint.y != this.toPoint.y))
                {
                    tmpLine = new DrawLineItem(this);
                    tmpLine.startPos = this.screenPoint(this.fromPoint);
                    tmpLine.endPos = this.screenPoint(this.toPoint);
                    tmpLine.style = this.getDrawLineStyle();

                    if (getSelectNewLines())
                    {
                        setSelection(e.getPoint());
                        selected = tmpLine;
                    }

                    invalidate();
                }

                drawing = false;
                drawing = false;

                fireNew(new ChangeEvent(this));
            }
        }
        return c;
    }

    public boolean getSelectNewLines()
    {
         return fSelectNew;
    }

    public void setSelectNewLines(boolean value)
    {
        fSelectNew = value;
    }

    public DrawLineStyle getDrawLineStyle()
    {
        return style;
    }
    public void setDrawLineStyle(DrawLineStyle value)
    {
        style = value;
    }

    public Cursor mouseEvent(FrameworkMouseEvent e, Cursor c)
    {
        if (e.getID() == FrameworkMouseEvent.MOUSE_PRESSED)
        {
            c = doMouseDown(e, c);
        }
        else if (e.getID() == FrameworkMouseEvent.MOUSE_MOVED || e.getID() == FrameworkMouseEvent.MOUSE_DRAGGED)
        {
            c = doMouseMove(e, c);
        }
        else if (e.getID() == FrameworkMouseEvent.MOUSE_RELEASED)
        {
            c = doMouseUp(e, c);
        }
        return c;
    }

    public interface DrawLineListener extends EventListener
    {

        void lineNew(ChangeEvent e);

        void lineSelected(ChangeEvent e);
    }

    public class DrawLineAdapter implements DrawLineListener
    {

        public void lineNew(ChangeEvent e)
        {
        }

        ;

        public void lineSelected(ChangeEvent e)
        {
        }

        ;
    }

    public interface DrawLineSelector
    {

        boolean selectable(DrawLine sender, DrawLineItem line, boolean allow);
    }
}
