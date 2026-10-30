package com.steema.teechart.styles;

import com.steema.teechart.editors.gallery.Gallery;
import com.steema.teechart.languages.Language;
import com.steema.teechart.drawing.Color;
import com.steema.teechart.drawing.Graphics3D;
import com.steema.teechart.drawing.Point;
import com.steema.teechart.misc.Utils;

import com.steema.teechart.Rectangle;
import com.steema.teechart.drawing.IGraphics3D;
import com.steema.teechart.IBaseChart;
import com.steema.teechart.drawing.ChartPen;

/**
 *
 * <p>Title: Candle class</p>
 *
 * <p>Description: Candle Series.</p>
 *
 * <p>Example:
 * <pre><font face="Courier" size="4">
 * candleSeries = new com.steema.teechart.styles.Candle(myChart.getChart());
 * candleSeries.fillSampleValues(30);
 * candleSeries.getColors().setColor(11, Color.YELLOW);
 * candleSeries.getColors().setColor(15, Color.LIME);
 * candleSeries.getColors().setColor(16, Color.BLUE);
 * candleSeries.repaint();
 * </font></pre></p>
 *
 * <p>Copyright (c) 2005-2019 by Steema Software SL. All Rights Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 *
 */
public class Candle extends OHLC {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static final int DEFAULTCANDLEWIDTH = 4;

    private Color upCloseColor = Color.WHITE;
    private Color downCloseColor = Color.RED;
    private int candleWidth = DEFAULTCANDLEWIDTH;
    private CandleStyle style = CandleStyle.CANDLESTICK;
    private boolean showOpenTick = true;
    private boolean showCloseTick = true;

    transient private Point oldP;

    public Candle(IBaseChart c) {
        super(c);

        getPointer().setDraw3D(false);
    }

    public Candle() {
        this(null);
    }
    
    @Override
    public void assign(Series source) {
            super.assign(source);
            if(source instanceof Candle)
            {
                this.upCloseColor=((Candle)source).upCloseColor;
                this.downCloseColor=((Candle)source).downCloseColor;
                this.candleWidth=((Candle)source).candleWidth;
                this.style=((Candle)source).style;
                this.showOpenTick=((Candle)source).showOpenTick;
                this.showCloseTick=((Candle)source).showCloseTick;
            }
    }    

    /**
     * Determines how the Candle points will be drawn.<br>
     * When it is CandleStick, a vertical rectangle represents each candle
     * point.<br>
     * When it is CandleBar, a vertical line is drawn, among Open and Close
     * horizontal tick marks.<br>
     * You can control both the candle colors and width. <br>
     * Default value: CandleStick
     *
     *
     * @return CandleStyle
     */
    public CandleStyle getStyle() {
        return style;
    }

    /**
     * Determines how the Candle points will be drawn.<br>
     * Default value: CandleStick<br>
     *
     * <p>Example:
     * <pre><font face="Courier" size="4">
     * candleSeries = new com.steema.teechart.styles.Candle(myChart.getChart());
     * candleSeries.fillSampleValues(30);
     *
     * candleSeries.setStyle(CandleStyles.OpenClose);
     * </font></pre></p>
     *
     * @param value CandleStyle
     */
    public void setStyle(CandleStyle value) {
        if (style != value) {
            style = value;
            invalidate();
        }
    }

    public void createSubGallery(Gallery addSubChart) {
        super.createSubGallery(addSubChart);
        addSubChart.createSubChart(Language.getString("CandleBar"));
        addSubChart.createSubChart(Language.getString("CandleNoOpen"));
        addSubChart.createSubChart(Language.getString("CandleNoClose"));
        addSubChart.createSubChart(Language.getString("NoBorder"));
        addSubChart.createSubChart(Language.getString("Line"));
    }

    public void setSubGallery(int index) {
        switch (index) {
        case 1:
            setStyle(CandleStyle.CANDLEBAR);
            break;
        case 2: {
            getPen().setVisible(true);
            setStyle( CandleStyle.CANDLEBAR);
            showOpenTick = false;
        }
        break;
        case 3: {
            getPen().setVisible(true);
            setStyle(CandleStyle.CANDLEBAR);
            showCloseTick = false;
        }
        break;
        case 4: {
            setStyle( CandleStyle.CANDLESTICK);
            getPen().setVisible(false);
        }
        break;
        case 5: {
            setStyle(CandleStyle.LINE);
        }
        break;
        default:
            super.setSubGallery(index);
        }
    }

    /**
     * The horizontal Candle Size in pixels.<br>
     * It is based on pixels for Screen charts. When printing, this number is
     * multiplied by the ratio between the Printer width and the Screen width.
     * <br>
     * Default value: 6
     *
     * @return int
     */
    public int getCandleWidth() {
        return candleWidth;
    }

    /**
     * Sets the horizontal Candle Size in pixels.<br>
     * Default value: 6
     *
     * @param value int
     */
    public void setCandleWidth(int value) {
        candleWidth = setIntegerProperty(candleWidth, value);
    }

    /**
     * Candle color fill when Open value is greater than Close value.<br>
     * By default, UpCloseColor is WHITE and DownCloseColor is RED. <br>
     * Each Candle will be filled with a different color depending on its Open
     * and Close values.<br>
     * If Open value is greater than Close value, then the selected color will
     * be the UpCloseColor color.<br>
     * If Close value is greater or equal than Open value, then the selected
     * color will be the DownCloseColor color. <br>
     * Default value: white
     *
     *
     *
     * @return Color
     */
    public Color getUpCloseColor() {
        return upCloseColor;
    }

    /**
     * Candle color fill when Open value is greater than Close value.<br>
     * Default value: white
     *
     * @param value Color
     */
    public void setUpCloseColor(Color value) {
        upCloseColor = setColorProperty(upCloseColor, value);
    }

    /**
     * Candle color fill when Close value is greater than Open value.<br>
     * By default, UpCloseColor is WHITE and DownCloseColor is RED. <br>
     * Each Candle will be filled with a different color depending on its Open
     * and Close values.<br>
     * If Open value is greater than Close value, then the selected color will
     * be the UpCloseColor color.<br>
     * If Close value is greater or equal than Open value, then the selected
     * color will be the DownCloseColor color. <br>
     * Default value: red
     *
     *
     *
     * @return Color
     */
    public Color getDownCloseColor() {
        return downCloseColor;
    }

    /**
     * Candle color fill when Close value is greater than Open value.<br>
     * Default value: red
     *
     * @param value Color
     */
    public void setDownCloseColor(Color value) {
        downCloseColor = setColorProperty(downCloseColor, value);
    }

    /**
     * Determines whether Open prices will be displayed.<br>
     * It only has effect when Candle series.CandleStyle is set to csCandleBar.
     * CandleWidth determines the length in pixels of Open and Close ticks. <br>
     * Default value: true
     *
     * @return boolean
     */
    public boolean getShowOpen() {
        return showOpenTick;
    }

    /**
     * Determines whether Open prices will be displayed.<br>
     * Default value: true
     *
     * @param value boolean
     */
    public void setShowOpen(boolean value) {
        showOpenTick = setBooleanProperty(showOpenTick, value);
    }

    /**
     * Determines whether Close prices will be displayed.<br>
     * It only has effect when Candle series.CandleStyle is set to csCandleBar.
     * CandleWidth determines the length in pixels of Open and Close ticks. <br>
     * Default value: true
     *
     * @return boolean
     */
    public boolean getShowClose() {
        return showCloseTick;
    }

    /**
     * Determines whether Close prices will be displayed.<br>
     * Default value: true
     *
     * @param value boolean
     */
    public void setShowClose(boolean value) {
        showCloseTick = setBooleanProperty(showCloseTick, value);
    }

    private Color calculateColor(int valueIndex) {
        Color result = getValueColor(valueIndex);

        if (result == getColor()) {
            if (vOpenValues.value[valueIndex] > getCloseValues().value[valueIndex])
            /* 5.01 */
            {
                result = downCloseColor;
            } else
            if (vOpenValues.value[valueIndex] < getCloseValues().value[valueIndex]) {
                result = upCloseColor;
            } else {
                // color algorithm when open is equal to close
                if (valueIndex == 0) {
                    result = upCloseColor; // <-- first point
                } else
                if (getCloseValues().value[valueIndex - 1] >
                    getCloseValues().value[valueIndex]) {
                    result = downCloseColor;
                } else
                if (getCloseValues().value[valueIndex - 1] <
                    getCloseValues().value[valueIndex]) {
                    result = upCloseColor;
                } else {
                    result = getValueColor(valueIndex - 1);
                }
            }
        }

        return result;
    }

    /**
     * Called internally. Draws the "ValueIndex" point of the Series.
     *
     * @param valueIndex int
     */
    public void drawValue(int valueIndex) {
        if(!this.isNull(valueIndex)) {
        PointerStyle tmpStyle = PointerStyle.RECTANGLE;

        onGetPointerStyle(valueIndex, tmpStyle);

        IGraphics3D g = chart.getGraphics3D();

        /* Pointer Pen && Brush styles */
        point.prepareCanvas(g, Color.EMPTY);

        /* The horizontal position */
        int x = calcXPosValue(getDateValues().value[valueIndex]);

        // Vertical positions of Open, High, Low & Close values for this point
        int yOpen = calcYPosValue(vOpenValues.value[valueIndex]);
        int yHigh = calcYPosValue(vHighValues.value[valueIndex]);
        int yLow = calcYPosValue(vLowValues.value[valueIndex]);
        int yClose = calcYPosValue(getCloseValues().value[valueIndex]);

        int tmpLeftWidth = candleWidth / 2; /* calculate half Candle Width */
        int tmpRightWidth = candleWidth - tmpLeftWidth;

        if ((style == CandleStyle.CANDLESTICK) ||
            (style == CandleStyle.OPENCLOSE)) {

            // draw Candle Stick

            if (chart.getAspect().getView3D() && point.getDraw3D()) {
                int tmpTop=0;
                int tmpBottom=0;

                if (yClose > yOpen) {
                    tmpTop = yOpen;
                    tmpBottom = yClose;
                } else {
                    tmpTop = yClose;
                    tmpBottom = yOpen;
                }

                // Draw Candle Vertical Line from bottom to Low
                if (style == CandleStyle.CANDLESTICK) {
                    g.verticalLine(x, tmpBottom, yLow, getMiddleZ());
                }

                // Draw 3D Candle
                g.getBrush().setColor( calculateColor(valueIndex));
                if (yOpen == yClose) {
                    g.getPen().setColor( calculateColor(valueIndex));
                }

                if (getTransparency() > 0) {
                    g.getBrush().setTransparency( getTransparency());
                }

                g.cube(x - tmpLeftWidth, tmpTop, x + tmpRightWidth,
                       tmpBottom, getStartZ(), getEndZ(), point.getDark3D());

                // Draw Candle Vertical Line from Top to High
                if (style == CandleStyle.CANDLESTICK) {
                    g.verticalLine(x, tmpTop, yHigh, getMiddleZ());
                }
            } else {
                // Draw Candle Vertical Line from High to Low
                if (style == CandleStyle.CANDLESTICK) {
                    if (chart.getAspect().getView3D()) {
                        g.verticalLine(x, yLow, yHigh, getMiddleZ());
                    } else {
                        g.verticalLine(x, yLow, yHigh);
                    }
                }

                // remember that Y coordinates are inverted

                // prevent zero height rectangles 5.02
                // in previous releases, an horizontal line was displayed instead
                // of the small candle rectangle
                if (yOpen == yClose) {
                    yClose--;
                }

                // draw the candle
                g.getBrush().setColor( calculateColor(valueIndex));
                if (getTransparency() > 0) {
                    g.getBrush().setTransparency(getTransparency());
                }

                if (chart.getAspect().getView3D()) {
                    g.rectangle(new Rectangle(x - tmpLeftWidth,
                                              yOpen,
                                              tmpLeftWidth + tmpRightWidth,
                                              yClose - yOpen),
                                getMiddleZ());
                } else {
                    if (!point.getPen().getVisible()) {
                        if (yOpen < yClose) {
                            yOpen--;
                        } else {
                            yClose--;
                        }
                    }
                    g.rectangle(x - tmpLeftWidth, yOpen,
                                x + tmpRightWidth + 1, yClose);
                }
            }
        } else
        if (style == CandleStyle.CANDLEBAR) {
            // draw Candle bar
            g.setPen(point.getPen());
            g.getPen().setColor(calculateColor(valueIndex));

            if (yLow == yHigh) {
                yLow--;
            }

            // Draw Candle Vertical Line from High to Low
            if (chart.getAspect().getView3D()) {
                g.verticalLine(x, yLow, yHigh, getMiddleZ());
                if (showOpenTick) {
                    g.horizontalLine(x, x - tmpLeftWidth - 1, yOpen,
                                     getMiddleZ());
                }
                if (showCloseTick) {
                    g.horizontalLine(x, x + tmpRightWidth + 1,
                                     yClose, getMiddleZ());
                }
            } else {
                // 5.02
                g.verticalLine(x, yLow, yHigh);
                if (showOpenTick) {
                    g.horizontalLine(x, x - tmpLeftWidth - 1, yOpen);
                }
                if (showCloseTick) {
                    g.horizontalLine(x, x + tmpRightWidth + 1,
                                     yClose);
                }
            }
        } else {
            // Line
            Point p = new Point(x, yClose);

            int tmpFirst = drawValuesForward() ? firstVisible :
                           lastVisible;

            if ((valueIndex != tmpFirst) && (!isNull(valueIndex))) {
                g.setPen(getPen());
                g.getPen().setColor( calculateColor(valueIndex));
                if (chart.getAspect().getView3D()) {
                    g.line(oldP, p, getMiddleZ());
                } else {
                    g.line(oldP, p);
                }
            }

            oldP = p;
        }
        }
    }

    //[DesignerSerializationVisibility(DesignerSerializationVisibility.Hidden),
    //Category("Appearance")]
    public ChartPen getPen() {
        return getPointer().getPen();
    }
    
    public double getMinYValue() {
      if(this.getStyle() == CandleStyle.LINE)  {
          return getCloseValues().getMinimum();
      }
      else {
          return super.getMinYValue();
      }
    }
    
    public double getMaxYValue() {
      if(this.getStyle() == CandleStyle.LINE)  {
          return getCloseValues().getMaximum();
      }
      else {
          return super.getMaxYValue();
      }  
    }

    public void prepareForGallery(boolean isEnabled) {
        super.prepareForGallery(isEnabled);
        fillSampleValues(4);
        setColorEach(true);

        if (isEnabled) {
            upCloseColor = Color.BLUE;
        } else {
            upCloseColor = Color.SILVER;
            downCloseColor = Color.SILVER;
            point.getPen().setColor(Color.GRAY);
        }
        point.getPen().setWidth(2);
        candleWidth = 12;
    }

    /**
     * Gets descriptive text.
     *
     * @return String
     */
    public String getDescription() {
        return Language.getString("GalleryCandle");
    }
    
    
    /*
    * Returns the ValueIndex of the x,y located point in the Series.
    */
    public int clicked(int x,int y) 
    {
        return clicked(new Point(x,y)); 
    }
    
    /*
    * Returns the ValueIndex of the "clicked" point in the Series.
    */
    public int clicked(Point p) 
    {
            int result=-1;

            if (firstVisible>-1 && lastVisible>-1)
            {

                    if (chart!=null) chart.getGraphics3D().calculate2DPosition(p.x, p.y, startZ);

                    //Point p = new Point(x,y);

                    for (int t=firstVisible; t<=lastVisible; t++)
                            if (clickedCandle(t,p))
                            {
                                    result=t;
                                    break;
                            }
            }
            return result;
    }
    
    /*
    * Returns true if point p is inside the bounds of the ValueIndex candle
    */
    public boolean clickedCandle(int valueIndex, Point p)
    {
            boolean result = false;
      
            int tmpX =calcXPosValue(getDateValues().value[valueIndex]); /* The horizontal position */
            int yOpen =calcYPosValue(vOpenValues.value[valueIndex]);
            int yHigh =calcYPosValue(vHighValues.value[valueIndex]);
            int yLow  =calcYPosValue(vLowValues.value[valueIndex]);
            int yClose=calcYPosValue(getCloseValues().value[valueIndex]);

            int tmpLeftWidth=candleWidth / 2; /* calc half Candle Width */
            int tmpRightWidth=candleWidth-tmpLeftWidth;

            int tmpTop;
            int tmpBottom;
            int tmpFirst;
            Point tmpTo=new Point();

            if (style==CandleStyle.CANDLESTICK || style==CandleStyle.OPENCLOSE)
            {
                    if (chart.getAspect().getView3D() && getPointer().getDraw3D())
                    {
                            tmpTop = yClose;
                            tmpBottom = yOpen;

                            if (tmpTop>tmpBottom) {
                                int tmp = tmpBottom;
                                tmpBottom = tmpTop;
                                tmpTop = tmp;
                            }
                        
                            if (style==CandleStyle.CANDLESTICK && 
                                    (Graphics3D.pointInLineTolerance(p,tmpX,tmpBottom,tmpX,yLow,3) ||
                                     Graphics3D.pointInLineTolerance(p,tmpX,tmpTop,tmpX,yHigh,3))) 
                                    return true;

                            Rectangle tmpR=Rectangle.fromLTRB(tmpX-tmpLeftWidth,tmpTop,tmpX+tmpRightWidth,tmpBottom);
                            if (tmpR.contains(p))
                                    return true;
                    }
                    else
                    {
                            if (style==CandleStyle.CANDLESTICK && 
                                    Graphics3D.pointInLineTolerance(p,tmpX,yLow,tmpX,yHigh,3))
                                    return true;

                            if (yOpen==yClose) yClose--;

                            if (chart.getAspect().getView3D())
                            {
                                Rectangle tmpR=Rectangle.fromLTRB(tmpX-tmpLeftWidth,yOpen,tmpX+tmpRightWidth,yClose);
                                if (tmpR.contains(p))
                                    return true;
                            }
                            else
                            {
                                    if (!getPen().getVisible())
                                            if (yOpen<yClose)
                                                    yOpen--;
                                            else
                                                    yClose--;

                                    Rectangle tmpR=Rectangle.fromLTRB(tmpX-tmpLeftWidth,yOpen,tmpX+tmpRightWidth+1,yClose);
                                    if (tmpR.contains(p))
                                            return true;
                            }
                    }
            }
            else if (style==CandleStyle.LINE)
            {
                    tmpFirst = firstVisible;
                    tmpTo.x=tmpX;
                    tmpTo.y=yClose;
                    if (valueIndex!=tmpFirst) 
                      result = Graphics3D.pointInLineTolerance(p,oldP.x,oldP.y,tmpTo.x,tmpTo.y,3);
                    oldP = tmpTo;
            }
            else
            {
                    if (Graphics3D.pointInLineTolerance(p,tmpX,yLow,tmpX,yHigh,3) ||
                            (showOpenTick && Graphics3D.pointInLineTolerance(p,tmpX,yOpen,tmpX-tmpLeftWidth-1,yOpen,3)) ||
                            (showCloseTick && Graphics3D.pointInLineTolerance(p,tmpX,yClose,tmpX+tmpRightWidth+1,yClose,3)))
                            result=true;
            }
            return result;
    }
}

