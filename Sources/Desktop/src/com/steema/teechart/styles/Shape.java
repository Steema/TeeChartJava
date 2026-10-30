package com.steema.teechart.styles;

import com.steema.teechart.editors.gallery.Gallery;
import com.steema.teechart.languages.Language;

import com.steema.teechart.Rectangle;
import com.steema.teechart.TextShapeStyle;
import com.steema.teechart.drawing.Color;
import com.steema.teechart.drawing.ChartPen;
import com.steema.teechart.drawing.ChartBrush;
import com.steema.teechart.drawing.Gradient;
import com.steema.teechart.drawing.Point;
import com.steema.teechart.IBaseChart;
import com.steema.teechart.drawing.IGraphics3D;
import com.steema.teechart.TextShape;
import com.steema.teechart.drawing.ChartFont;

import com.steema.teechart.drawing.Graphics3D;
import com.steema.teechart.misc.Utils;

/**
 *
 * <p>Title: Shape class</p>
 *
 * <p>Description: Shape Series.</p>
 *
 * <p>Copyright (c) 2005-2019 by Steema Software SL. All Rights Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 *
 */
public class Shape extends Series {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private TextShape shape;
    private ShapeStyle style = ShapeStyle.CIRCLE;
    private ShapeXYStyle xyStyle = ShapeXYStyle.AXIS;
    private ShapeTextVertAlign textVertAlign = ShapeTextVertAlign.CENTER;
    private ShapeTextHorizAlign textHorizAlign = ShapeTextHorizAlign.CENTER;

    public Shape() {
        this((IBaseChart)null);
    }

    public Shape(IBaseChart c) {
        super(c);

        shape = new TextShape(c);
        calcVisiblePoints = false;
        addDefaultPoints();
    }
    
    public void assign(Series source) {
        if (source instanceof Shape) {
            Shape tmp = (Shape)source;
            tmp.setChart(((Shape)source).getChart()); 
            tmp.setHorizAlignment(((Shape)source).getHorizAlignment()); 
            tmp.setStyle(((Shape)source).getStyle()); 
            tmp.setText(((Shape)source).getText()); 
            tmp.setTransparent(((Shape)source).getTransparent()); 
            tmp.setVertAlignment(((Shape)source).getVertAlignment());
            tmp.setX0(((Shape)source).getX0());
            tmp.setY0(((Shape)source).getY0());
            tmp.setX1(((Shape)source).getX1());
            tmp.setY1(((Shape)source).getY1());
            tmp.setXYStyle(((Shape)source).getXYStyle());
        }
        super.assign(source);
    }     

    public void setChart(IBaseChart c) {

        super.setChart(c);

        if (shape != null) {
            shape.setChart(c);
        }
    }

    private void addDefaultPoints() {
        if (super.chart != null) { //CDI TF02010111
            add(0, 0);
            add(100, 100);
        }
    }

    protected void drawLegendShape(IGraphics3D g, int valueIndex,
                                   Rectangle rect) {
        drawShape(g, false, rect);
    }

    private void drawText(Rectangle r) {
        int shapeHorizMargin = 4; //static final
        int tmpPosX = 0;
        int tmpH = 0;
        int tmpPosY = 0;
        int tmpWidth = 0;

        IGraphics3D g = chart.getGraphics3D();
        if (getText().length > 0) {
            g.setFont(getFormat().getFont());
            tmpH = Utils.round(g.textHeight(getFont(), "H"));

            Point tmpMid = r.center();

            if (textVertAlign == ShapeTextVertAlign.TOP) {
                tmpPosY = r.getTop();
            } else
            if (textVertAlign == ShapeTextVertAlign.CENTER) {
                tmpPosY = tmpMid.y - Utils.round(tmpH * getText().length / 2.0);
            } else {
                tmpPosY = r.getBottom() - (tmpH * getText().length);
            }

            for (int t = 0; t < getText().length; t++) {
                tmpWidth = g.textWidth(getText(t));

                if (textHorizAlign == ShapeTextHorizAlign.CENTER) {
                    tmpPosX = tmpMid.x - (tmpWidth / 2);
                } else
                if (textHorizAlign == ShapeTextHorizAlign.LEFT) {
                    tmpPosX = r.getLeft() + getPen().getWidth() + shapeHorizMargin;
                } else {
                    tmpPosX = r.getRight() - getPen().getWidth() - tmpWidth -
                              shapeHorizMargin;
                }

                //MM TextAlign=TA_LEFT;

                if (getXYStyle() == ShapeXYStyle.PIXELS) {
                    g.textOut(tmpPosX, tmpPosY, getText(t));
                } else {
                    g.textOut(tmpPosX, tmpPosY, getStartZ(), getText(t));
                }

                tmpPosY += tmpH;
            }
        }
    }

    public String getText(int index) {
        return shape.getLines()[index];
    }

    /**
     * Coordinate used to define the englobing ShapeSeries rectangle.<br>
     * The values should be expressed in Axis coordinates. <br>
     * You can convert from Screen pixel coordinates to values and vice-versa
     * using several TChart and Series methods like XScreenToValue and
     * YScreenToValue.
     *
     * @return double
     */
    public double getX0() {
        return vxValues.value[0];
    }

    /**
     * Coordinate used to define the englobing ShapeSeries rectangle.<br>
     *
     * @param value double
     */
    public void setX0(double value) {
        vxValues.value[0] = value;
        invalidate();
    }

    /**
     * Coordinate used to define the englobing ShapeSeries rectangle.<br>
     * The values should be expressed in Axis coordinates. <br>
     * You can convert from Screen pixel coordinates to values and vice-versa
     * using several TChart and Series methods like XScreenToValue and
     * YScreenToValue.
     *
     * @return double
     */
    public double getX1() {
        return vxValues.value[1];
    }

    /**
     * Coordinate used to define the englobing ShapeSeries rectangle.<br>
     *
     * @param value double
     */
    public void setX1(double value) {
        vxValues.value[1] = value;
        invalidate();
    }

    /**
     * Coordinate used to define the englobing ShapeSeries rectangle.<br>
     * The values should be expressed in Axis coordinates. <br>
     * You can convert from Screen pixel coordinates to values and vice-versa
     * using several TChart and Series methods like XScreenToValue and
     * YScreenToValue.
     *
     * @return double
     */
    public double getY0() {
        return vyValues.value[0];
    }

    /**
     * Coordinate used to define the englobing ShapeSeries rectangle.<br>
     *
     * @param value double
     */
    public void setY0(double value) {
        vyValues.value[0] = value;
        invalidate();
    }

    /**
     * Coordinate used to define the englobing ShapeSeries rectangle.<br>
     * The values should be expressed in Axis coordinates. <br>
     * You can convert from Screen pixel coordinates to values and vice-versa
     * using several TChart and Series methods like XScreenToValue and
     * YScreenToValue.
     *
     * @return double
     */
    public double getY1() {
        return vyValues.value[1];
    }

    /**
     * Coordinate used to define the englobing ShapeSeries rectangle.<br>
     *
     * @param value double
     */
    public void setY1(double value) {
        vyValues.value[1] = value;
        invalidate();
    }

    /**
     * Defines how a TChartShape component appears on a Chart.<br>
     * Default value: Circle
     *
     *
     * @return ShapeStyle
     */
    public ShapeStyle getStyle() {
        return style;
    }

    /**
     * Defines how a TChartShape component appears on a Chart.<br>
     * Default value: Circle
     *
     *
     * @param value ShapeStyle
     */
    public void setStyle(ShapeStyle value) {
        style = value;
        chart.invalidate();
    }

    public TextShape getFormat() {
        return shape;
    }

    /**
     * Sets the vertical alignment of Text within a TChartShape Series shape.
     * <br>
     * It can be Top, Center, Bottom.
     *
     * @return ShapeTextVertAlign
     */
    public ShapeTextVertAlign getVertAlignment() {
        return textVertAlign;
    }

    /**
     * Sets the vertical alignment of Text within a TChartShape Series shape.
     * <br>
     *
     * @param value ShapeTextVertAlign
     */
    public void setVertAlignment(ShapeTextVertAlign value) {
        if (textVertAlign != value) {
            textVertAlign = value;
        }
        repaint();
    }

    /**
     * Horizontally aligns the text.<br>
     * There are three options; Centre, Left or Right.
     *
     * @return ShapeTextHorizAlign
     */
    public ShapeTextHorizAlign getHorizAlignment() {
        return textHorizAlign;
    }

    /**
     * Horizontally aligns the text.<br>
     *
     * @param value ShapeTextHorizAlign
     */
    public void setHorizAlignment(ShapeTextHorizAlign value) {
        if (textHorizAlign != value) {
            textHorizAlign = value;
        }
        repaint();
    }

    private Rectangle getShapeRectangle() {
        int x0, x1, y0, y1;

        if (xyStyle==ShapeXYStyle.PIXELS) {
            x0 = (int) (getX0());
            y0 = (int) (getY0());
            x1 = (int) (getX1());
            y1 = (int) (getY1());
        }
        else
        if (xyStyle==ShapeXYStyle.AXIS) {
            x0 = calcXPos(0);
            y0 = calcYPos(0);
            x1 = calcXPos(1);
            y1 = calcYPos(1);
        }
        else
            {
            x0 = calcXPos(0);
            y0 = calcYPos(0);
            x1 = x0 + (int) (getX1());
            y1 = y0 + (int) (getY1());
        }

        return Rectangle.fromLTRB(x0, y0, x1, y1);
    }

    private Rectangle getAdjustedRectangle() {
        Rectangle r = getShapeRectangle();
        if (r.y == r.getBottom()) {
            r.height = 1;
        } else
        if (r.y > r.getBottom()) {
            int tmp = r.height;
            r.y = r.getBottom();
            r.height = ( -tmp);
        }

        if (r.x == r.getRight()) {
            r.width = 1;
        } else
        if (r.x > r.getRight()) {
            int tmp = r.width;
            r.x = r.getRight();
            r.width = ( -tmp);
        }

        return r;
    }

    static private void drawDiagonalCross2D(IGraphics3D g, Rectangle r) {
        g.line(r.x, r.y, r.getRight() + 1, r.getBottom() + 1);
        g.line(r.x, r.getBottom(), r.getRight() + 1, r.y - 1);
    }

    private void drawDiagonalCross3D(IGraphics3D g,Rectangle r) {
        g.line(r.x, r.y, r.getRight(), r.getBottom(), getMiddleZ());
        g.line(r.x, r.getBottom(), r.getRight(), r.y, getMiddleZ());
    }

    private void drawCross3D(IGraphics3D g,Rectangle r) {
        Point tmpMid = r.center();
        g.verticalLine(tmpMid.x, r.y, r.getBottom(), getMiddleZ());
        g.horizontalLine(r.x, r.getRight(), tmpMid.y, getMiddleZ());
    }

    private void drawCross2D(IGraphics3D g,Rectangle r) {
        Point tmpMid = r.center();
        g.verticalLine(tmpMid.x, r.y, r.getBottom() + 1);
        g.horizontalLine(r.x, r.getRight() + 1, tmpMid.y);
    }

    private void doGradient(boolean is3D, Rectangle r) {
        if ((!getTransparent()) && getGradient().getVisible()) {
            Rectangle tmpR = is3D ?
                             chart.getGraphics3D().calcRect3D(r, getMiddleZ()) :
                             r;
            if (style == ShapeStyle.CIRCLE) {
                chart.getGraphics3D().clipEllipse(tmpR);
            }

            getGradient().draw(chart.getGraphics3D(), tmpR);

            if (style == ShapeStyle.CIRCLE) {
                chart.getGraphics3D().unClip();
            }
            chart.getGraphics3D().getBrush().setVisible(false);
        }
    }

    private void drawShape(boolean is3D, Rectangle r) {
        drawShape(chart.getGraphics3D(), is3D, r);
    }

    private void drawShape(IGraphics3D g, boolean is3D, Rectangle r) {
        g.setPen(getPen());

        if (getTransparent()) {
            g.getBrush().setVisible(false);
        } else {
            g.setBrush(getBrush());
            g.getBrush().setColor(getColor());
        }

        //            if ( Brush.Color.isEmpty())
        //                g.getBrush().getVisible()=false;

        Point tmpMid = r.center();

        if (is3D) {
            if (style==ShapeStyle.RECTANGLE) {
                doGradient(is3D, r);
                g.rectangle(r, getMiddleZ());
            }
            else
            if (style==ShapeStyle.CIRCLE) {
                doGradient(is3D, r);
                g.ellipse(r, getMiddleZ());
            }
            else
            if (style==ShapeStyle.VERTLINE) {
                g.verticalLine(tmpMid.x, r.y, r.getBottom(), getMiddleZ());
            }
            else
            if (style==ShapeStyle.HORIZLINE) {
                g.horizontalLine(r.x, r.getRight(), tmpMid.y, getMiddleZ());
            }
            else
            if (style==ShapeStyle.TRIANGLE) {
                g.triangle(new Point(r.x, r.getBottom()),
                           new Point(tmpMid.x, r.y),
                           new Point(r.getRight(), r.getBottom()), getMiddleZ());
            }
            else
            if (style==ShapeStyle.INVERTTRIANGLE) {
                g.triangle(new Point(r.x, r.y),
                           new Point(tmpMid.x, r.getBottom()),
                           new Point(r.getRight(), r.y), getMiddleZ());
            }
            else
            if (style==ShapeStyle.LINE) {
                g.line(r.x, r.y, r.getRight(), r.getBottom(), getMiddleZ());
            }
            else
            if (style==ShapeStyle.DIAMOND) {
                g.plane(new Point(r.x, tmpMid.y),
                        new Point(tmpMid.x, r.y),
                        new Point(r.getRight(), tmpMid.y),
                        new Point(tmpMid.x, r.getBottom()), getMiddleZ());
            }
            else
            if (style==ShapeStyle.CUBE) {
                g.cube(r, getStartZ(), getEndZ(), !getTransparent());
            }
            else
            if (style==ShapeStyle.CROSS) {
                drawCross3D(g,r);
            }
            else
            if (style==ShapeStyle.DIAGCROSS) {
                drawDiagonalCross3D(g,r);
            }
            else
            if (style==ShapeStyle.STAR) {
                drawCross3D(g,r);
                drawDiagonalCross3D(g,r);
            }
            else
            if (style==ShapeStyle.PYRAMID) {
                g.pyramid(true, r, getStartZ(), getEndZ(), !getTransparent());
            }
            else
            if (style==ShapeStyle.INVERTPYRAMID) {
                g.pyramid(true, r.x, r.getBottom(), r.getRight(), r.y, getStartZ(),
                          getEndZ(),
                          !getTransparent());
            }
        } else {
            if (style==ShapeStyle.RECTANGLE) {
                if (getFormat().getShapeStyle() ==
                    TextShapeStyle.ROUNDRECTANGLE) {
                    int roundSize = 12;
                    Rectangle tmpR = new Rectangle(r.getLeft(), r.getTop(),
                            r.width - roundSize,
                            r.height - roundSize);
                    g.roundRectangle(tmpR, roundSize, roundSize);
                } else {
                    doGradient(is3D, r);
                    g.rectangle(r.x, r.y, r.getRight()+1, r.getBottom()+1);
                }
            }
            else
            if (style==ShapeStyle.CIRCLE) {
                doGradient(is3D, r);
                g.ellipse(r);
            }
            else
            if (style==ShapeStyle.VERTLINE) {
                g.verticalLine(tmpMid.x, r.y, r.getBottom());
            }
            else
            if (style==ShapeStyle.HORIZLINE) {
                g.horizontalLine(r.x, r.getRight() + 1, tmpMid.y);
            }
            else
            if ((style==ShapeStyle.TRIANGLE) |
                (style==ShapeStyle.PYRAMID)) {
                Point[] tmp = {new Point(r.x, r.getBottom()),
                              new Point(tmpMid.x, r.y),
                              new Point(r.getRight(), r.getBottom())};
                g.polygon(tmp);
            }
            else
            if ((style==ShapeStyle.INVERTTRIANGLE) |
                (style==ShapeStyle.INVERTPYRAMID)) {
                Point[] tmp = {new Point(r.x, r.y),
                              new Point(tmpMid.x, r.getBottom()),
                              new Point(r.getRight(), r.y)};
                g.polygon(tmp);
            }
            else
            if (style==ShapeStyle.LINE) {
                g.line(r.x, r.y, r.getRight(), r.getBottom());
            }
            else
            if (style==ShapeStyle.DIAMOND) {
                Point[] tmp = {new Point(r.x, tmpMid.y),
                              new Point(tmpMid.x, r.y),
                              new Point(r.getRight(), tmpMid.y),
                              new Point(tmpMid.x, r.getBottom())};
                g.polygon(tmp);
            }
            else
            if (style==ShapeStyle.CUBE) {
                g.rectangle(r);
            }
            else
            if (style==ShapeStyle.CROSS) {
                drawCross2D(g,r);
            }
            else
            if (style==ShapeStyle.DIAGCROSS) {
                drawDiagonalCross2D(g,r);
            }
            else
            if (style==ShapeStyle.STAR) {
                drawCross2D(g,r);
                drawDiagonalCross2D(g,r);
            }
        }
    }

    /**
     * Called internally. Draws the "ValueIndex" point of the Series.
     *
     * @param valueIndex int
     */
    public void drawValue(int valueIndex) {
        if ((getCount() == 2) && (valueIndex == 0)) {
            Rectangle r = getAdjustedRectangle();
            if (r.intersects(chart.getChartRect())) {
                boolean tmp = (xyStyle == ShapeXYStyle.PIXELS) ? false :
                              chart.getAspect().getView3D();
                drawShape(tmp,
                          (style == ShapeStyle.LINE) ? getShapeRectangle() : r);
                drawText(r);
            }
        }
    }

    protected void addSampleValues(int numValues) {
        SeriesRandom r = randomBounds(1);
        if (r.StepX == 0) {
            addDefaultPoints();
        } else {
            add(r.tmpX + (r.StepX / 8.0), r.tmpY / 2);
            add(r.tmpX + r.StepX - (r.StepX / 8.0),
                r.tmpY + Utils.round(r.DifY * r.Random()));
        }
    }

    /**
     * Returns the ValueIndex of the "clicked" point in the Series.
     *
     * @param x int
     * @param y int
     * @return int
     */
    public int clicked(int x, int y) {

        Point p;

        if ((chart != null)) {
            p = chart.getGraphics3D().calculate2DPosition(x, y, getStartZ());
        } else {
            p = new Point(x, y);
        }

        Rectangle r = getShapeRectangle();
        Point tmpMid = r.center();

        boolean tmp;

        if (style==ShapeStyle.VERTLINE){
            tmp = Graphics3D.pointInLineTolerance(p, tmpMid.x, r.y, tmpMid.x,
                                                  r.getBottom(), 3);
        } else if (style==ShapeStyle.HORIZLINE) {
            tmp = Graphics3D.pointInLineTolerance(p, r.x, tmpMid.y, r.getRight(),
                                                  tmpMid.y, 3);
        } else if (style==ShapeStyle.LINE) {
            tmp = Graphics3D.pointInLineTolerance(p, r.x, r.y, r.getRight(),
                                                  r.getBottom(), 3);
        } else if (style==ShapeStyle.DIAMOND) {
            Point[] points={new Point(tmpMid.x, r.y),
                                            new Point(r.getRight(), tmpMid.y),
                                            new Point(tmpMid.x, r.getBottom()),
                                            new Point(r.x, tmpMid.y)};

            tmp = Graphics3D.pointInPolygon(p, points);
        } else if ((style==ShapeStyle.TRIANGLE) | (style==ShapeStyle.PYRAMID)) {
            tmp = Graphics3D.pointInTriangle(p, r.x, r.getRight(), r.getBottom(), r.y);
        } else if ((style==ShapeStyle.INVERTTRIANGLE) | (style==ShapeStyle.INVERTPYRAMID)) {
            tmp = Graphics3D.pointInTriangle(p, r.x, r.getRight(), r.y, r.getBottom());
        } else if (style==ShapeStyle.CIRCLE) {
            tmp = Graphics3D.pointInEllipse(p, r);
        } else {
            tmp = r.contains(x, y);
        }
        return tmp ? 0 : -1;
    }

    /**
     * Determines the font attributes used to output ShapeSeries.<br>
     * No auto font sizing is performed, so you must specify the desired font
     * size to avoid shape text from overlapping the Shape boundaries.
     *
     * @return ChartFont
     */
    public ChartFont getFont() {
        return shape.getFont();
    }

    /**
     * Defines the brush used to fill shape background.<br>
     * <p>Example:
     * <pre><font face="Courier" size="4">
     * //shape1
     * shape[0] = new Shape(myChart.getChart());
     * tmpShape = shape[0];
     * tmpShape.getMarks().setVisible(false);
     * tmpShape.setColor(Color.WHITE);
     * tmpShape.getBrush().setColor(Color.WHITE);
     * tmpShape.getBrush().setImageMode(ImageMode.TILE);
     * tmpShape.getBrush().loadImage(ChartSamplePanel.class.getResource(URL_IMAGE1));
     *
     * tmpShape.setStyle(ShapeStyle.CIRCLE);
     * tmpShape.getPen().setColor(Color.RED);
     * tmpShape.getPen().setWidth(2);
     * tmpShape.setX1(50);
     * tmpShape.setY1(50);
     * </font></pre></p>
     *
     * @return ChartBrush
     */
    public ChartBrush getBrush() {
        return shape.getBrush();
    }

    /**
     * Defines pen to draw Series Shape.
     *
     * @return ChartPen
     */
    public ChartPen getPen() {
        return shape.getPen();
    }

    /**
     * Gets Gradient fill characteristics for the ShapeSeries Shape.
     *
     * @return Gradient
     */
    public Gradient getGradient() {
        return shape.getGradient();
    }

    /**
     * Displays customized Strings inside Shapes.<br>
     * You can use the Font and Aligment properties to control Text display.<br>
     * <b>Note:</b> You may need to change Shape Font size to a different
     * value when creating metafiles or when zooming Charts.
     *
     * @return String[]
     */
    public String[] getText() {
        if (shape.getLines() == null) {
            shape.setLines(new String[0]);
        }
        return shape.getLines();
    }

    public void setText(String[] value) {
        shape.setLines(value);
    }

    /**
     * Allows Shape Brush attributes to fill the interior of the Shape.<br>
     * When false, Shapes do not redraw their background, so charting contents
     * behind Shape Series is seen inside the Shape. <br>
     * Default value: false
     *
     * @return boolean
     */
    public boolean getTransparent() {
        return shape.getTransparent();
    }

    /**
     * Allows Shape Brush attributes to fill the interior of the Shape.<br>
     * Default value: false
     *
     * @param value boolean
     */
    public void setTransparent(boolean value) {
        shape.setTransparent(value);
    }

    public void prepareForGallery(boolean isEnabled) {
        super.prepareForGallery(isEnabled);

        getFont().setColor(Color.WHITE);
        getFont().setSize(12);
        shape.setLines(new String[1]);
        if (chart.getSeriesIndexOf(this) == 1) {
            style = ShapeStyle.CIRCLE;
            getBrush().setColor(isEnabled ? Color.BLUE : Color.SILVER);
            String[] tmp={Language.getString("ShapeGallery1")};
            setText(tmp);
        } else {
            style = ShapeStyle.TRIANGLE;
            getBrush().setColor(isEnabled ? Color.RED : Color.SILVER);
            String[] tmp={Language.getString("ShapeGallery2")};
            setText(tmp);
        }
    }

    public void calcZOrder() {
        if (getUseAxis()) {
            super.calcZOrder();
        }
    }

    protected boolean moreSameZOrder() {
        return false;
    }

    /**
     * Can be set to:- Axis, AxisOrigin or Pixels.
     *
     *
     * @return ShapeXYStyle
     */
    public ShapeXYStyle getXYStyle() {
        return xyStyle;
    }

    /**
     * Can be set to:- Axis, AxisOrigin or Pixels.
     *
     *
     * @param value ShapeXYStyle
     */
    public void setXYStyle(ShapeXYStyle value) {
        if (xyStyle != value) {
            xyStyle = value;
            repaint();
        }
    }

    /**
     * Returns false if the Value parameter is the same as Self.
     *
     * @param s Series
     * @return boolean
     */
    public boolean isValidSourceOf(Series s) {
        return s instanceof Shape;
    }

    public void createSubGallery(Gallery addSubChart) {
        super.createSubGallery(addSubChart);

        addSubChart.createSubChart(Language.getString("Rectangle"));
        addSubChart.createSubChart(Language.getString("VertLine"));
        addSubChart.createSubChart(Language.getString("HorizLine"));
        addSubChart.createSubChart(Language.getString("Ellipse"));
        addSubChart.createSubChart(Language.getString("DownTri"));
        addSubChart.createSubChart(Language.getString("Line"));
        addSubChart.createSubChart(Language.getString("Diamond"));
        addSubChart.createSubChart(Language.getString("Cube"));
        addSubChart.createSubChart(Language.getString("Cross"));
        addSubChart.createSubChart(Language.getString("DiagCross"));
        addSubChart.createSubChart(Language.getString("Star"));
        addSubChart.createSubChart(Language.getString("Pyramid"));
        addSubChart.createSubChart(Language.getString("InvPyramid"));
        addSubChart.createSubChart(Language.getString("Hollow"));
    }

    public void setSubGallery(int index) {
        switch (index) {
        case 1:
            setStyle(ShapeStyle.RECTANGLE);
            break;
        case 2:
            setStyle(ShapeStyle.VERTLINE);
            break;
        case 3:
            setStyle(ShapeStyle.HORIZLINE);
            break;
        case 4:
            setStyle(ShapeStyle.CIRCLE);
            break;
        case 5:
            setStyle(ShapeStyle.INVERTTRIANGLE);
            break;
        case 6:
            setStyle(ShapeStyle.LINE);
            break;
        case 7:
            setStyle(ShapeStyle.DIAMOND);
            break;
        case 8:
            setStyle(ShapeStyle.CUBE);
            break;
        case 9:
            setStyle(ShapeStyle.CROSS);
            break;
        case 10:
            setStyle(ShapeStyle.DIAGCROSS);
            break;
        case 11:
            setStyle(ShapeStyle.STAR);
            break;
        case 12:
            setStyle(ShapeStyle.PYRAMID);
            break;
        case 13:
            setStyle(ShapeStyle.INVERTPYRAMID);
            break;
        case 14:
            setTransparent(!getTransparent());
            break;
        default:
            super.setSubGallery(index);
        }
    }

    /**
     * Gets descriptive text.
     *
     * @return String
     */
    public String getDescription() {
        return Language.getString("GalleryShape");
    }
}

