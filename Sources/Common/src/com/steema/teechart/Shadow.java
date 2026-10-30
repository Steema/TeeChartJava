package com.steema.teechart;

import com.steema.teechart.drawing.Color;
import com.steema.teechart.drawing.ChartBrush;
import com.steema.teechart.drawing.IGraphics3D;
import com.steema.teechart.drawing.Point;

/**
 *
 * <p>Title: Shadow class</p>
 *
 * <p>Description: Properties to draw a shadow.</p>
 *
 * <p>Copyright (c) 2005-2019 by Steema Software SL. All Rights
 * Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 *
 */
public class Shadow extends TeeBase {
	
	private static final long serialVersionUID = 1L;

    protected ChartBrush brush;
    private int height = 3;
    private int width = 3;
    private int smoothBlur = 3;    
    
    protected boolean smooth = true;
    public boolean visible;

    transient protected boolean defaultVisible;
    transient protected int defaultSize;
    transient protected Color defaultColor;

    /**
     * Shadow Constructor.
     *
     * @param c IBaseChart
     */
    public Shadow(IBaseChart c) {
        super(c);
        defaultSize=3;
        brush = new ChartBrush(c, Color.DARK_GRAY);
    }

    /**
     * Shadow constructor, defines Shadow size.
     *
     * @param c IBaseChart
     * @param size int
     */
    public Shadow(IBaseChart c, int size) {
        this(c);

        defaultSize = size;
        width = size;
        height = size;
    }

    /**
     * Shadow constructor, defines initial size and color
     *
     * @param c IBaseChart
     * @param size int
     * @param color Color
     */
    public Shadow(IBaseChart c, int size, final Color color) {
        this(c,size);
        setColor(color);
        defaultColor = color;
    }

    /**
     * Assigns characteristics of Shadow 'value'.<br>
     * Copies all properties from Source Shadow to Self.
     *
     * @param value Shadow
     */
    public void assign(final Shadow value) {
        height = value.height;
        width = value.width;
        visible = value.visible;
        brush.assign(value.brush);
        smooth = value.smooth;
        smoothBlur = value.smoothBlur;
    }

    /**
     * Defines the shadow Color. Gets or sets Color used to fill shadow.
     *
     * @return Color
     */
    public Color getColor() {
        return brush.getColor();
    }

    /**
     * Defines the shadow Color. Gets or sets Color used to fill shadow.
     *
     * @param value Color
     */
    public void setColor(final Color value) {
        brush.setColor(value);
    }

    public void setDefaultSize(int value) {
        defaultSize = value;
        height = value;
        width = value;
    }

    public void setDefaultVisible(boolean value) {
        defaultVisible = value;
        visible = value;
    }

    protected boolean shouldSerializeHeight() {
        return height != defaultSize;
    }

    /**
     * The Transparency level from 0 to 100% of shadow.<br>
     * Transparency is a value between 0 and 100 which sets the transparency
     * percentage with respect to foreground versus background for the shadow.
     *
     * @return int
     */
    public int getTransparency() {
        return brush.getTransparency();
    }

    /**
     * Sets Transparency level from 0 to 100% of shadow.<br>
     *
     * @param value int
     */
    public void setTransparency(int value) {
        brush.setTransparency(value);
    }

    /**
     * The vertical displacement of the shadow in pixels.
     *
     * @return int
     */
    public int getHeight() {
        return height;
    }

    /**
     * Sets the vertical displacement of the shadow in pixels.
     *
     * <p>Example:
     * <pre><font face="Courier" size="4">
     * pieSeries = new com.steema.teechart.styles.Pie(myChart.getChart());
     * pieSeries.getMarks().setVisible(true);
     * pieSeries.getShadow().setVisible(true);
     * pieSeries.getShadow().setWidth(30);
     * pieSeries.getShadow().setHeight(50);
     * pieSeries.getShadow().setColor(Color.SILVER);
     * pieSeries.fillSampleValues(9);
     *
     * @param value int
     */
    public void setHeight(int value) {
        height = setIntegerProperty(height, value);
    }

    /**
     * Obsolete.&nbsp;Please use Width property.
     *
     * @return int
     */
    public int getHorizSize() {
        return width;
    }

    /**
     * Obsolete.&nbsp;Please use Width property.
     *
     * @param value int
     */
    public void setHorizSize(int value) {
        width = value;
    }

    /**
     * Obsolete.&nbsp;Please use Height property.
     *
     * @return int
     */
    public int getVertSize() {
        return height;
    }

    /**
     * Obsolete.&nbsp;Please use Height property.
     *
     * @param value int
     */
    public void setVertSize(int value) {
        height = value;
    }

    protected boolean shouldSerializeWidth() {
        return width != defaultSize;
    }

    /**
     * The horizontal shadow size in pixels.
     *
     * @return int
     */
    public int getWidth() {
        return width;
    }

    /**
     * Sets the horizontal shadow size in pixels.
     *
     * <p>Example:
     * <pre><font face="Courier" size="4">
     * pieSeries = new com.steema.teechart.styles.Pie(myChart.getChart());
     * pieSeries.getMarks().setVisible(true);
     * pieSeries.getShadow().setVisible(true);
     * pieSeries.getShadow().setWidth(30);
     * pieSeries.getShadow().setHeight(50);
     * pieSeries.getShadow().setColor(Color.SILVER);
     * pieSeries.fillSampleValues(9);
     *
     * @param value int
     */
    public void setWidth(int value) {
        width = setIntegerProperty(width, value);
    }

    /**
     * Defines the Brush characteristics to fill the shadow.
     *
     * @return ChartBrush
     */
    public ChartBrush getBrush() {
        return brush;
    }

    /**
     * Size in pixels of shadow.<br>
     * Returns the biggest of the HorizSize and VertSize properties.
     * When setting Size, it will set both HorizSize and VertSize to the
     * same value.
     *
     * @return Dimension
     */
    public Dimension getSize() {
        return new Dimension(width, height);
    }

    /**
     * Sets both horizontal and vertical shadow size to same value.
     *
     * @param value int
     */
    public void setSize(int value) {
      width=value;
      height=value;
      invalidate();
    }

    /**
     * Size in pixels of shadow.<br>
     *
     * @param value Dimension
     */
    public void setSize(final Dimension value) {
        width = value.width;
        height = value.height;
        invalidate();
    }

    protected boolean shouldSerializeVisible() {
        return visible != defaultVisible;
    }

    /**
     * Determines whether the shadow will appear on screen.
     *
     * @return boolean
     */
    public boolean getVisible() {
        return visible;
    }

    /**
     * Determines whether the shadow will appear on screen.
     *
     * @param value boolean
     */
    public void setVisible(boolean value) {
        visible = setBooleanProperty(visible, value);
    }

    /**
     * Draws a shadow around the Rect rectangle parameter, using the ACanvas
     * canvas.<br>
     * Uses the Height, Width and Transparency properties to draw the shadow.
     *
     * @param g IGraphics3D
     * @param rect Rectangle
     */
    public void draw(IGraphics3D g, final Rectangle rect) {
        draw(g, rect, 0, 0, false);
    }

    /**
     * Draws a shadow around the Rect rectangle parameter.<br>
     * Uses the Height, Width and Transparency properties to draw the shadow.
     *
     * @param g IGraphics3D
     * @param rect Rectangle
     * @param angle int
     * @param z int
     * @param is3D boolean
     */
    public void draw(IGraphics3D g, final Rectangle rect, int angle, int z, boolean is3D) {
        draw(g, rect, 0, angle, z, is3D);
    }
    
    /**
     * Draws a shadow with rounded edges around the Rect rectangle parameter.<br>
     * Uses the Height, Width and Transparency properties to draw the shadow.
     *
     * @param g IGraphics3D
     * @param rect Rectangle
     * @param roundSize int
     * @param angle int
     * @param z int
     * @param is3D boolean
     */
    void draw(IGraphics3D g, Rectangle rect, int roundSize, int angle, int z, boolean is3D) {
        if ((height != 0) || (width != 0)) {
            g.getPen().setVisible(false);
            g.setBrush(getBrush());

            Rectangle r = rect.copy();
            r.offset(width, height);
                
            if (smooth) {
                if (angle == 0) {
                    if (is3D) {
                        r = g.calcRect3D(r, z);
                    }

                    g.drawBlur(r, smoothBlur, roundSize, false);
                }
            } else {
                if (angle > 0) {
                    g.polygon(z, g.rotateRectangle(r, angle));
                    //g.polygon(z, g.rotateRectangle(r, angle));
                } else {
                    if (roundSize > 0) {
                        g.roundRectangle(r, roundSize);
                    }
                    else {
                        if (Math.abs(width) > rect.width || Math.abs(height) > rect.height) {
                            if (is3D) {
                                g.rectangle(r, z);
                            }
                            else {
                                g.rectangle(r);
                            }
                        }
                        else {
                            final Rectangle bottom = new Rectangle(rect.getLeft() + width, rect.getBottom(), (rect.getRight() - rect.getLeft()), height);
                            final Rectangle right = new Rectangle(rect.getRight(), rect.getTop() + height, width, (rect.getBottom() - rect.getTop()) - height);

                            if (is3D) {
                                g.rectangle(bottom, z);
                                g.rectangle(right, z);
                            }
                            else {
                                g.rectangle(bottom);
                                g.rectangle(right);
                            }
                        }
                    }
                }
            }
        }
    }
    
    /*
    * Draws a shadow around the polygon described by the array of points parameter.
    */
    public void draw(IGraphics3D g, final double aWidth, final double aHeight, final Point[] points)
    {
      if ((aWidth != 0) || (aHeight != 0))
      {
        final boolean oldVisible = g.getPen().getVisible();
        g.getPen().setVisible(false);
        g.setBrush(getBrush());

        final PointDouble[] p = new PointDouble[points.length];
        for (int i = 0; i < points.length; i++)
          p[i] = new PointDouble(points[i].x + aWidth, points[i].y + aHeight);

        if (smooth)
        	g.drawBlur(p, smoothBlur);
        else
        	g.polygon(p);
        
        g.getPen().setVisible(oldVisible);
      }
    }
    
    /**
    * Draws a shadow around the ellipse described by the top left and bottom right parameters.
    */
    public void draw(IGraphics3D g, int x1, int y1, int x2, int y2)
    {
      draw(g, x1, y1, x2, y2, 0);
    }

    /**
    * Draws a shadow around the ellipse described by the top left and bottom right parameters
    * at depth z.
    */
    public void draw(IGraphics3D g, int x1, int y1, int x2, int y2, int z)
    {
      if ((height != 0) || (width != 0))
      {
        final boolean oldVisible = g.getPen().getVisible();
        g.getPen().setVisible(false);
        g.setBrush(getBrush());

        Rectangle tmpR = new Rectangle(x1, y1, x2-x1, y2-y1);
        tmpR.offset(getWidth(), getHeight());

        if (smooth) {
        	
        	if (chart.getAspect().getView3D())
        		tmpR = g.calcRect3D(tmpR, z);
        	
        	g.drawBlur(tmpR, smoothBlur, true);
        }
        else
        {
            final PointDouble[] P = new PointDouble[360];
            
            for (int i = 0; i < 360; i++)
              P[i] = g.pointFromEllipse(tmpR, i, z);

            g.polygon(P);
        }
        
        g.getPen().setVisible(oldVisible);
      }
    }
    
    public void setChart(IBaseChart c) {
        super.setChart(c);

        if (brush != null) {
            brush.setChart(chart);
        }
    }
    /**
     * Internal use.
     * 
     * @return boolean
     */
    public boolean getSmooth(){
      return smooth;
    }
    /**
     * Internal use.
     * 
     * @param value
     */
    public void setSmooth(boolean value){
    	smooth = setBooleanProperty(smooth, value);
    }

    /**
     * Reserved for future use.
     * 
     * @return int
     */
    public int getSmoothBlur()
    {
      return smoothBlur;
    }
    /**
     * Reserved for future use.
     * 
     * @param value
     */
    public void setSmoothBlur(int value){
      if (value != smoothBlur) {
        smoothBlur = value;
        invalidate();
      }
    }

}

