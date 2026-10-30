package com.steema.teechart.styles;

import com.steema.teechart.IBaseChart;
import com.steema.teechart.Rectangle;
import com.steema.teechart.TeeBase;
import com.steema.teechart.drawing.*;
import com.steema.teechart.misc.Utils;

/**
 *
 * <p>Title: SeriesPointer class</p>
 *
 * <p>Description: Some Series have a Pointer method which returns a
 * SeriesPointer class. Pointers are shape figures drawn on each Y point
 * coordinate.</p>
 *
 * <p>Copyright (c) 2005-2019 by Steema Software SL. All Rights
 * Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 *
 */
public class SeriesPointer extends TeeBase {
	
	private static final long serialVersionUID = 1L;

    protected boolean bVisible = true;
    transient protected boolean defaultVisible;
    protected boolean allowChangeSize;

    private boolean dark3D = true;
    protected boolean draw3D = true;
    private boolean inflate = true;
    private int horizSize = 4;
    private int vertSize = 4;
    private PointerStyle style = PointerStyle.RECTANGLE;
    private ChartPen pen;
    private ChartBrush bBrush;

    transient protected int xMinus, xPlus, yMinus, yPlus;

    transient private ISeries series;

    // Constructor
    public SeriesPointer(IBaseChart c, ISeries s) {
        super(c);

        readResolve();

        series = s;
    }

    public void assign(SeriesPointer source) {
        if (source.bBrush != null) {
            this.getBrush().assign(source.bBrush);
        }
        if (source.pen != null) {
            this.getPen().assign(source.pen);
        }
        this.style = source.style;
        this.vertSize = source.vertSize;
        this.horizSize = source.horizSize;
        this.dark3D = source.dark3D;
        this.draw3D = source.draw3D;
        this.inflate = source.inflate;
        this.bVisible = source.bVisible;
        this.allowChangeSize = source.allowChangeSize;
        this.defaultVisible = source.defaultVisible;
    }

    protected Object readResolve() {
        defaultVisible = true;
        return this;
    }

    /**
     * Fills pointer sides in 3D mode with darker color.<br>
     * Default value: true
     *
     * @return boolean
     */
    public boolean getDark3D() {
        return dark3D;
    }

    /**
     * Fills pointer sides in 3D mode with darker color.<br>
     * Default value: true
     *
     * @param value boolean
     */
    public void setDark3D(boolean value) {
        dark3D = setBooleanProperty(dark3D, value);
    }

    /**
     * Draws pointer in 3D mode.<br>
     * Currently only rectangle points have 3D capability. <br>
     * Default value: true
     *
     * @return boolean
     */
    public boolean getDraw3D() {
        return draw3D;
    }

    /**
     * Draws pointer in 3D mode.<br>
     * Default value: true
     *
     * @param value boolean
     */
    public void setDraw3D(boolean value) {
        draw3D = setBooleanProperty(draw3D, value);
    }

    /**
     * Shows or hides the pointer.<br>
     * When using a Points series (or any Series class derived from Points
     * series), setting Visible to false will not display anything. <br>
     * Pointers can be useful with Line series or Area series. <br>
     * When points are Visible, extra margins are applied to the four chart
     * axes (Left, Right, Top and Bottom). This is to make points just at axis
     * limits to be shown. <br>
     * You can deactivate these extra margins by setting
     * IPointer.InflateMargins to false. <br><br>
     * Points are filled using IPointer.Brush.
     *
     * @return boolean
     */
    public boolean getVisible() {
        return bVisible;
    }

    /**
     * Shows or hides the pointer.<br>
     *
     * @param value boolean
     */
    public void setVisible(boolean value) {
        bVisible = setBooleanProperty(bVisible, value);
    }

    /**
     * Horizontal size of pointer in pixels.<br>
     * Series that derive from Points series usually override the HorizSize
     * and VertSize methods. <br>
     * For example, Bubble series uses the Radius method to determine the
     * correct HorizSize and VertSize, so these methods have no effect in
     * that Series. <br>
     * Default value: 4
     *
     * @return int
     */
    public int getHorizSize() {
        return horizSize;
    }

    /**
     * Horizontal size of pointer in pixels.<br>
     * Default value: 4
     *
     * @param value int
     */
    public void setHorizSize(int value) {
        horizSize = setIntegerProperty(horizSize, value);
    }

    /**
     * Expands axes to fit pointers.<br>
     * When false, Chart Axis scales will be preserved and points close to the
     * Axis limits will be partially displayed. <br>
     * Default value: true
     *
     * @return boolean
     */
    public boolean getInflateMargins() {
        return inflate;
    }

    /**
     * Expands axes to fit pointers.<br>
     * Default value: true
     *
     * @param value boolean
     */
    public void setInflateMargins(boolean value) {
        inflate = setBooleanProperty(inflate, value);
    }

    /**
     * The Pointer style.<br>
     * It defines the shape used to display the Series Points.<br>
     * The default Rectangle style can be optionally in 3D mode by setting
     * Pointer.Draw3D to true. <br>
     * Series Pointer.Visible should be true.<br>
     * Default value: rectangle
     *
     *
     * @return PointerStyle
     */
    public PointerStyle getStyle() {
        return style;
    }

    /**
     * Sets the Pointer style.<br>
     * Default value: rectangle
     *
     *
     * @param value PointerStyle
     */
    public void setStyle(PointerStyle value) {
        if (style != value) {
            style = value;
            invalidate();
        }
    }

    //[Browsable(false)]
    public ISeries getSeries() {
        return series;
    }
    
    public void setSeries(ISeries s) {
        series = s;
    }

    /**
     * The Horizontal size of pointer in pixels.<br>
     * Series that derive from PointSeries usually override the HorizontalSize
     * and VerticalSize methods. <br>
     * For example, Bubble series uses the Radius property to determine the
     * correct HorizSize and VertSize, so these methods have no effect in
     * that Series. <br>
     * Default value: 4
     *
     * @return int
     */
    public int getVertSize() {
        return vertSize;
    }

    /**
     * Sets Horizontal size of pointer in pixels.<br>
     * Default value: 4
     *
     * @param value int
     */
    public void setVertSize(int value) {
        vertSize = setIntegerProperty(vertSize, value);
    }

    /**
     * The Percent of semiglass effect.<br>
     * Default valuie: 0
     *
     * @return int
     */
    public int getTransparency() {
        return getBrush().getTransparency();
    }

    /**
     * Sets Percent of semiglass effect.<br>
     * Default valuie: 0
     *
     * <p>Example:
     * <pre><font face="Courier" size="4">
     * bubbleSeries.getPointer().setTransparency(50); // -- 50 %
     * </font></pre></p>
     *
     * @param value int
     */
    public void setTransparency(int value) {
        getBrush().setTransparency(value);
    }

    /**
     * Configures Gradient filling attributes.<br>
     * Default value: null
     *
     * <p>Example:
     * <pre><font face="Courier" size="4">
     *   bubbleSeries.getPointer().getGradient().setVisible(true);
     * </font></pre></p>
     *
     * @return Gradient
     */
    public Gradient getGradient() {
        return getBrush().getGradient();
    }

    private void drawDiagonalCross(IGraphics3D g, boolean is3D,
                                   Color colorValue) {
        //g.AssignVisiblePenColor(Pen,ColorValue);
        if (is3D) {
            g.line(xMinus, yMinus, xPlus + 1, yPlus + 1, getStartZ());
            g.line(xPlus, yMinus, xMinus - 1, yPlus + 1, getStartZ());
        } else {
            g.line(xMinus, yMinus, xPlus + 1, yPlus + 1);
            g.line(xPlus, yMinus, xMinus - 1, yPlus + 1);
        }
    }

    private void drawCross(IGraphics3D g, boolean is3D, int px, int py,
                           Color colorValue) {
        //g.AssignVisiblePenColor(Pen,ColorValue);
        if (is3D) {
            g.verticalLine(px, yMinus, yPlus + 1, getStartZ());
            g.horizontalLine(xMinus, xPlus + 1, py, getStartZ());
        } else {
            g.verticalLine(px, yMinus, yPlus + 1);
            g.horizontalLine(xMinus, xPlus + 1, py);
        }
    }

    private void doTriangle3D(IGraphics3D g, int deltaY, int px, int py) {
        if (draw3D) {
            g.pyramid(true, xMinus, py - deltaY, xPlus, py + deltaY,
                      getStartZ(),
                      getEndZ(), dark3D);
        } else {
            g.triangle(new Point(xMinus, py + deltaY),
                       new Point(xPlus, py + deltaY),
                       new Point(px, py - deltaY), getStartZ());
        }
    }

    private void doHorizTriangle3D(IGraphics3D g, int deltaX, int px, int py) {
        if (draw3D) {
            g.pyramid(false, px + deltaX, yMinus, px - deltaX, yPlus,
                      getStartZ(),
                      getEndZ(), dark3D);
        } else {
            g.triangle(new Point(px + deltaX, yMinus),
                       new Point(px + deltaX, yPlus),
                       new Point(px - deltaX, py), getStartZ());
        }
    }

    /**
     * For internal use.
     *
     * @return int
     */
    public int getStartZ() {
        if (series != null) {
            return series.getStartZ();
        } else {
            return 0;
        }
    }

    /**
     * For internal use.
     *
     * @return int
     */
    public int getMiddleZ() {
        if (series != null) {
            return series.getMiddleZ();
        } else {
            return 0;
        }
    }

    /**
     * For internal use.
     *
     * @return int
     */
    public int getEndZ() {
        if (series != null) {
            return series.getEndZ();
        } else {
            return 0;
        }
    }

    /**
     * The pointer color.
     *
     * @return Color
     */
    public Color getColor() {
        return getBrush().getColor();
    }

    /**
     * Sets the pointer color.
     *
     * @param value Color
     */
    public void setColor(Color value) {
        getBrush().setColor(value);
    }

    public void setChart(IBaseChart c) {
        super.setChart(c);
        if (pen != null) {
            pen.setChart(c);
        }
        if (bBrush != null) {
            bBrush.setChart(c);
        }
    }

    /**
     * Internal use. Draw Pointer
     *
     *
     * @param g IGraphics3D
     * @param is3D boolean
     * @param px int
     * @param py int
     * @param tmpHoriz int
     * @param tmpVert int
     * @param colorValue Color
     * @param aStyle PointerStyle
     */
    public void draw(IGraphics3D g, boolean is3D, int px, int py, int tmpHoriz,
                     int tmpVert, Color colorValue,
                     PointerStyle aStyle) {
//			//CDI Bubble Transparency fix
//			if(Brush.Transparency > 0)
//			{
        g.getBrush().setTransparency(getBrush().getTransparency());
        g.getBrush().setColor(colorValue.transparentColor(g.getBrush().
                getTransparency()));
//			}
//			else
//  			//CDI SeriesXYPoint
//	  		g.getBrush().setColor( colorValue);

        xMinus = px - tmpHoriz;
        xPlus = px + tmpHoriz;
        yMinus = py - tmpVert;
        yPlus = py + tmpVert;
        
        prepareCanvas(g, colorValue);

        if (is3D) {
            if (aStyle == PointerStyle.RECTANGLE) {
                if (draw3D) {
                    g.cube(xMinus, yMinus, xPlus, yPlus, getStartZ(),
                           getEndZ(),
                           dark3D);
                } else {
                    if (getStartZ()==0)
                        g.rectangle(xMinus, yMinus, xPlus + 1, yPlus + 1);
                    else
                        g.rectangle(xMinus, yMinus, xPlus + 1, yPlus + 1,
                                getStartZ());
                }
            } else
            if (aStyle == PointerStyle.CIRCLE) {
                if (draw3D && g.getSupportsFullRotation()) {
                    g.sphere(px, py, getMiddleZ(), tmpHoriz);
                } else {
                    g.ellipse(xMinus, yMinus, xPlus, yPlus, getStartZ());
                }
            } else
            if (aStyle == PointerStyle.SPHERE) {
                g.sphere(Rectangle.fromLTRB(xMinus, yMinus, xPlus, yPlus),
                         getStartZ(), false);
            } else
            if (aStyle == PointerStyle.POLISHEDSPHERE) {
                g.ellipse(xMinus, yMinus, xPlus, yPlus, getStartZ(), true);
            } else
            if (aStyle == PointerStyle.TRIANGLE) {
                doTriangle3D(g, tmpVert, px, py);
            } else
            if (aStyle == PointerStyle.DOWNTRIANGLE) {
                doTriangle3D(g, -tmpVert, px, py);
            } else
            if (aStyle == PointerStyle.LEFTTRIANGLE) {
                doHorizTriangle3D(g, tmpHoriz, px, py);
            } else
            if (aStyle == PointerStyle.RIGHTTRIANGLE) {
                doHorizTriangle3D(g, -tmpHoriz, px, py);
            } else
            if (aStyle == PointerStyle.CROSS) {
                drawCross(g, true, px, py, colorValue);
            } else
            if (aStyle == PointerStyle.DIAGCROSS) {
                drawDiagonalCross(g, true, colorValue);
            } else
            if (aStyle == PointerStyle.STAR) {
                drawCross(g, true, px, py, colorValue);
                drawDiagonalCross(g, true, colorValue);
            } else
            if (aStyle == PointerStyle.DIAMOND) {
                g.plane(new Point(xMinus, py),
                        new Point(px, yMinus),
                        new Point(xPlus, py),
                        new Point(px, yPlus), getStartZ());
            } else
            if (aStyle == PointerStyle.SMALLDOT) {
                g.setPixel(px, py, getMiddleZ(), colorValue);
            }
        } else {
            if (aStyle == PointerStyle.RECTANGLE) {
                g.rectangle(xMinus, yMinus, xPlus + 1, yPlus + 1);
            } else
            if (aStyle == PointerStyle.CIRCLE) {
                g.ellipse(xMinus, yMinus, xPlus, yPlus);
            } else
            if (aStyle == PointerStyle.SPHERE) {
                g.sphere(xMinus, yMinus, xPlus, yPlus, true);
            } else
            if (aStyle == PointerStyle.POLISHEDSPHERE) {
                g.ellipse(xMinus, yMinus, xPlus, yPlus, true);
            } else
            if (aStyle == PointerStyle.TRIANGLE) {
                Point[] p = {new Point(xMinus, yPlus),
                            new Point(xPlus, yPlus),
                            new Point(px, yMinus)};
                g.polygon(p);
            } else
            if (aStyle == PointerStyle.DOWNTRIANGLE) {
                Point[] p = {new Point(xMinus, yMinus),
                            new Point(xPlus, yMinus),
                            new Point(px, yPlus)};
                g.polygon(p);
            } else
            if (aStyle == PointerStyle.LEFTTRIANGLE) {
                Point[] p = {new Point(xMinus, py),
                            new Point(xPlus, yMinus),
                            new Point(xPlus, yPlus)};
                g.polygon(p);
            } else
            if (aStyle == PointerStyle.RIGHTTRIANGLE) {
                Point[] p = {new Point(xMinus, yMinus),
                            new Point(xMinus, yPlus),
                            new Point(xPlus, py)};
                g.polygon(p);
            } else
            if (aStyle == PointerStyle.CROSS) {
                drawCross(g, false, px, py, colorValue);
            } else
            if (aStyle == PointerStyle.DIAGCROSS) {
                drawDiagonalCross(g, false, colorValue);
            } else
            if (aStyle == PointerStyle.STAR) {
                drawCross(g, false, px, py, colorValue);
                drawDiagonalCross(g, false, colorValue);
            } else
            if (aStyle == PointerStyle.DIAMOND) {
                Point[] p = {new Point(xMinus, py),
                            new Point(px, yMinus),
                            new Point(xPlus, py),
                            new Point(px, yPlus)};
                g.polygon(p);
            } else
            if (aStyle == PointerStyle.SMALLDOT) {
                g.setPixel(px, py, getMiddleZ(), colorValue);
            }
        }
    }

    public void draw(Point p, Color colorValue) {
        draw(p.x, p.y, colorValue);
    }

    public void draw(Point p, Color colorValue, PointerStyle style) {
        draw(p.x, p.y, colorValue, style);
    }

    public void draw(int px, int py, Color colorValue) {
        draw(chart.getGraphics3D(), chart.getAspect().getView3D(), px, py,
             horizSize,
             vertSize,
             colorValue, style);
    }

    public void draw(int px, int py, Color colorValue, PointerStyle aStyle) {
        draw(chart.getGraphics3D(), chart.getAspect().getView3D(), px, py,
             horizSize,
             vertSize,
             colorValue, aStyle);
    }

    protected void prepareCanvas(IGraphics3D g, Color colorValue) {
        g.setPen(getPen());
        g.setBrush(getBrush());
        
        if (series != null) {            
            if (getBrush().getTransparency() > 0) {
                g.getBrush().setTransparency(getBrush().getTransparency());
                g.getBrush().setColor(Graphics3D.transparentColor(
                        g.getBrush().getTransparency(), colorValue));
            }
            else {
                g.getBrush().setColor(colorValue);
            }
            
            //CDI mais non, mon amie.
            //can't draw a "hollow" point with this in
            //may have been copied when I had left a "bad-code" 
            //moment in the .NET code 
            //if (colorValue == Color.TRANSPARENT) {
            //    g.getPen().setColor(colorValue);
            //}

            if (series.getColorEach()) {      
                g.getPen().setColor(Utils.darkenColor(colorValue, 60));

                ChartBrush brush = getBrush();
                
                if (brush.getGradientVisible()) {
                    g.getBrush().getGradient().setStartColor(colorValue);
                    g.getBrush().getGradient().setMiddleColor(Color.EMPTY);                    
                    g.getBrush().getGradient().setEndColor(Color.WHITE);
                }
            }
        }
    }

    /**
     * Pen used to draw a frame around Series Pointers.
     *
     * @return ChartPen
     */
    public ChartPen getPen() {
        if (pen == null) {
            pen = new ChartPen(chart, Color.BLACK);
        }
        return pen;
    }

    /**
     * Brush used to fill Series Pointers.
     *
     * @return ChartBrush
     */
    public ChartBrush getBrush() {
        if (bBrush == null) {
            bBrush = new ChartBrush(chart);
            if (series != null) {
                bBrush.setColor(series.getColor());
            }
        }
        return bBrush;
    }

    protected void drawLegendShape(Color color, Rectangle rect, boolean drawPen) {
        drawLegendShape(chart.getGraphics3D(), color, rect, drawPen);
    }

    protected void drawLegendShape(IGraphics3D g, Color color, Rectangle rect,
                                   boolean drawPen) {
        int tmpHoriz, tmpVert;

        prepareCanvas(g, color);

        if (drawPen) {
            tmpHoriz = rect.width / 3;
            tmpVert = rect.height / 3;
        } else {
            tmpHoriz = 1 + (rect.width / 2);
            tmpVert = 1 + (rect.height / 2);
        }

        draw(g, false, (rect.x + rect.getRight()) / 2,
             (rect.y + rect.getBottom()) / 2,
             Math.min(horizSize, tmpHoriz),
             Math.min(vertSize, tmpVert), color, style);
    }

    protected void calcHorizMargins(Margins margins) {
        if (bVisible && inflate) {
            margins.min = Math.max(margins.min, horizSize + 1);
            margins.max = Math.max(margins.max, horizSize + 1);
        }
    }

    protected void calcVerticalMargins(Margins margins) {
        if (bVisible && inflate) {
            margins.min = Math.max(margins.min, vertSize + 1);
            margins.max = Math.max(margins.max, vertSize + 1);
        }
    }

    void setDefaultVisible(boolean value) {
        defaultVisible = value;
        bVisible = value;
    }
}

