package com.steema.teechart.tools;

import com.steema.teechart.Rectangle;
import com.steema.teechart.drawing.ChartPen;
import com.steema.teechart.drawing.Color;
import com.steema.teechart.drawing.IGraphics3D;
import com.steema.teechart.drawing.Point;

/**
 *
 * <p>Title: DrawLine class</p>
 *
 * <p>Description: Line of DrawLine Tool.</p>
 *
 * <p>Copyright (c) 2005-2019 by Steema Software SL. All Rights Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 *
 */
public class DrawLineItem
{

    transient protected DrawLineStyle style;
    transient protected Point.Double endPos = new Point.Double();
    transient protected Point.Double startPos = new Point.Double();
    protected ChartPen pen;
    private DrawLine tool;

    public DrawLineItem()
    {

        this((DrawLine) null);

    }


    public DrawLineItem(DrawLine owner)
    {
        tool = owner;
        if (tool != null)
        {
            setPen(tool.getPen());
            tool.getLines().add(this);
        }
    }

    static private Rectangle rectangleFromPoint(Point p)
    {
        return new Rectangle(p.x - 3, p.y - 3, 6, 6);
    }

    public DrawLineStyle getDrawLineStyle()
    {
        return style;
    }

    public void setDrawLineStyle(DrawLineStyle style)
    {
        this.style = style;
    }

    public Point.Double getEndPos()
    {
        return endPos;
    }

    public void setEndPos(Point.Double pos)
    {
        this.endPos = pos;
    }

    public Point.Double getStartPos()
    {
        return startPos;
    }

    public void setStartPos(Point.Double pos)
    {
        this.startPos = pos;
    }

    /**
     * Returns Rect of the DrawLine end handle.
     *
     * @return Rectangle
     */
    public Rectangle getEndHandle()
    {
        return rectangleFromPoint(tool.axisPoint(endPos));
    }

    /**
     * Returns Rect of the DrawLine start handle.
     *
     * @return Rectangle
     */
    public Rectangle getStartHandle()
    {
        return rectangleFromPoint(tool.axisPoint(startPos));
    }

    public void drawHandles()
    {
        drawHandles(tool.chart.getGraphics3D());
    }

    public void drawHandles(IGraphics3D g)
    {
        boolean oldBrushVisible = g.getBrush().getVisible();
        boolean oldPenVisible = g.getPen().getVisible();
        boolean oldSolid = g.getBrush().getSolid();
        Color oldColor = g.getBrush().getColor();
        g.getBrush().setVisible(true);
        g.getBrush().setSolid(true);
        g.rectangle(getStartHandle(), 0);
        g.rectangle(getEndHandle(), 0);
        g.getBrush().setColor((tool.chart.getPanel().getColor() == Color.BLACK) ? Color.SILVER : Color.BLACK);
        g.getPen().setVisible(false);
        g.getBrush().setVisible(oldBrushVisible);
        g.getBrush().setSolid(oldSolid);
        g.getBrush().setColor(oldColor);
        g.getPen().setVisible(oldPenVisible);
    }

    public ChartPen getPen()
    {
        if (pen == null) pen = new ChartPen(tool.getChart(), Color.BLACK); return pen;
    }
    public void setPen(ChartPen value)
    {
        pen = value;
    }

    
}
