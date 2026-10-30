package com.steema.teechart;

import com.steema.teechart.drawing.Color;
import com.steema.teechart.drawing.Graphics3D;
import com.steema.teechart.drawing.ChartBrush;
import com.steema.teechart.drawing.ChartPen;

/**
 *
 * <p>Title: ImageBevel class</p>
 *
 * <p>Description: Displays Image bevel around rectangles</p>
 *
 * <p>Copyright (c) 2005-2019 by Steema Software SL. All Rights
 * Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 *
 */

public class ImageBevel extends TeeBase {
    private ChartPen pen;
    private ChartBrush brush;
    private boolean visible;
    private int width=10;

    private static final long serialVersionUID = 1L;

    /**
     * Draws an Image Bevel of the specified width.<br>
     * Default value: 10
     *
     * @return int
     */
    public int getWidth() {
        return width;
    }

    /**
     * Draws an Image Bevel of the specified width.<br>
     * Default value: 10
     *
     * @param value int
     */
    public void setWidth(int value) {
        setIntegerProperty(width, value);
    }

    /**
     * Sets the visibility of the Image Bevel.<br>
     * Default value: false
     *
     * @return boolean
     */
    public boolean getVisible() {
        return visible;
    }
    /**
     * Sets the visibility of the Image Bevel.<br>
     * Default value: false
     *
     * @param value boolean
     */
    public void setVisible(boolean value) {
        setBooleanProperty(visible, value);
    }

    /**
     * Sets ImageBevel Pen characteristics.
     *
     * @return ChartPen
     */
    public ChartPen getPen() {
        if(pen==null)
            pen = new ChartPen(chart,Color.BLACK);
        return pen;
    }

    /**
     * Sets ImageBevel Brush characteristics.
     *
     * @return ChartBrush
     */
    public ChartBrush getBrush() {
        if(brush==null)
            brush = new ChartBrush(chart,Color.GRAY);
        return brush;
    }

    public ImageBevel(IBaseChart c) {
        super(c);
    }

    /**
     * Assigns all properties from a bevel to another.
     *
     * @param b ImageBevel
     */
    public void assign(ImageBevel b) {
        if (b!=null) {
            this.getPen().assign(b.getPen());
            this.getBrush().assign(b.getBrush());
            this.setVisible(b.getVisible());
        }
    }

    /**
     * Draws bevels around rectangle parameter.
     *
     * @param g Graphics3D
     * @param rect Rectangle
     * @param borderRound int
     */
    public void Draw(Graphics3D g, Rectangle rect, int borderRound) {
        if(getVisible())
            g.paintImageBevel(rect,width,pen,brush,borderRound);
    }
}


