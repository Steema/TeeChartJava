package com.steema.teechart.styles;

import com.steema.teechart.drawing.ChartBrush;
import com.steema.teechart.drawing.ChartPen;
import com.steema.teechart.drawing.Point;
import com.steema.teechart.IBaseChart;
import com.steema.teechart.Point3D;
import com.steema.teechart.PointDouble;
import com.steema.teechart.drawing.Color;
import com.steema.teechart.misc.Utils;
import com.steema.teechart.drawing.IGraphics3D;
import com.steema.teechart.Rectangle;
import com.steema.teechart.axis.Axis;
import com.steema.teechart.drawing.LineCap;
import com.steema.teechart.drawing.Graphics3D;
import com.steema.teechart.functions.Smoothing;
import com.steema.teechart.tools.ToolSeries;

/**
 *
 * <p>Title: Custom class</p>
 *
 * <p>Description: Base Series class inherited by a number of TeeChart
 * series styles.</p>
 *
 * <p>Copyright (c) 2005-2019 by Steema Software SL. All Rights
 * Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 *
 */
public class Custom extends CustomPoint
{
    private static final long serialVersionUID = 1L;

    protected ChartBrush bAreaBrush;
    protected ChartPen pAreaLines;
    protected boolean bClickableLine = true;
    protected boolean bDark3D = true;
    protected boolean drawArea;
    protected boolean drawLine = true;
    private boolean invertedStairs;
    //private Color areaColor;
    private boolean colorEachLine = true;
    private int lineHeight;
    private ChartPen outLine;
    private boolean stairs;
    private boolean smoothed = false;
    // private
    transient private int bottomPos;
    transient private int oldBottomPos;
    transient private int oldX;
    transient private int oldY;
    transient private Color oldColor;
    transient private double tmpDark3DRatio;
    transient private Color tmpColor;
    transient private boolean isLastValue;

    public Custom()
    {
        this((IBaseChart) null);
    }

    public Custom(IBaseChart c)
    {
        super(c);
        getLinePen().setDefaultColor(Color.BLACK);

    }

    protected Object readResolve()
    {
        oldColor = Color.EMPTY;
        return super.readResolve();
    }

    /**
     * Allows mouse clicks over the line drawn between points.<br>
     * Default value: true
     *
     * <p>Example:
     * <pre><font face="Courier" size="4">
     * lineSeries.setClickableLine( false );
     * </font></pre></p>
     *
     * @return boolean
     */
    public boolean getClickableLine()
    {
        return bClickableLine;
    }

    /**
     * Allows mouse clicks over the line drawn between points.<br>
     * Default value: true
     *
     * @param value boolean
     */
    public void setClickableLine(boolean value)
    {
        bClickableLine = setBooleanProperty(bClickableLine, value);
    }

    /**
     * Opacity level from 0 to 100%
     *
     * @return int
     */
    public int getOpacity()
    {
        return 100 - getTransparency();
    }

    /**
     * Sets Opacity level from 0 to 100%
     *
     * @param value int
     */
    public void setOpacity(int value)
    {
        setTransparency(100 - value);
    }

    /**
     * Transparency level from 0 to 100%<br>
     * Default value: 0
     *
     * @return int
     */
    public int getTransparency()
    {
        return bBrush.getTransparency();
    }

    /**
     * Sets Transparency level from 0 to 100%<br>
     * Default value: 0 <br>
     * <p>Example:<pre><font face="Courier" size="4">
     * Series1.setTransparency(45);
     * </font></pre></p>
     *
     * @param value int
     */
    public void setTransparency(int value)
    {
        bBrush.setTransparency(value);
        setColor(bBrush.getColor());
    }

    /**
     * Sets Brush characteristics.
     *
     * @return ChartBrush
     */
    public ChartBrush getBrush()
    {
        return bBrush;
    }

    /**
     * Darkens parts of 3D Line Series to add depth.<br>
     * Default value: true
     *
     * @return boolean
     */
    public boolean getDark3D()
    {
        return bDark3D;
    }

    /**
     * Darkens parts of 3D Line Series to add depth.<br>
     * Default value: true
     *
     * @param value boolean
     */
    public void setDark3D(boolean value)
    {
        bDark3D = setBooleanProperty(bDark3D, value);
    }

    public void setChart(IBaseChart c)
    {
        super.setChart(c);
        if (bAreaBrush != null)
        {
            bAreaBrush.setChart(c);
        }
        if (pAreaLines != null)
        {
            pAreaLines.setChart(c);
        }
        if (outLine != null)
        {
            outLine.setChart(c);
        }
        if(smoothSeries != null) {
            smoothSeries.setChart(c);
        }
    }

    public void setColor(Color value)
    {
        super.setColor(value);
        this.getBrush().setColor(value);
    }

    /**
     * Steps line joining adjacent points.<br>
     * In most normal situations, a series draws a line between each Line
     * point. This makes the Line appear as a "mountain" shape. However,
     * setting Stairs to true will make the Series draw 2 Lines between each
     * pair of points, thus giving a "stair" appearance.<br>
     * This is most used in some financial Chart representations. <br>
     * When Stairs is set to true you may set InvertedStairs to true to alter
     * the direction of the step. <br>
     * Default value: false
     *
     * @return boolean
     */
    public boolean getStairs()
    {
        return stairs;
    }

    /**
     * Steps line joining adjacent points.<br>
     * Default value: false
     *
     * <p>Example:
     * <pre><font face="Courier" size="4">
     * areaSeries1.setStairs(true);
     * areaSeries2.setStairs(true);
     * </font></pre></p>
     *
     * @see #getStairs
     * @param value boolean
     */
    public void setStairs(boolean value)
    {
        stairs = setBooleanProperty(stairs, value);
    }

    /**
     * Enables/Disables the coloring of each connecting line of a series.<br>
     * Default value: true
     *
     * <p>Example:
     * <pre><font face="Courier" size="4">
     * lineSeries.setColorEach(true); lineSeries.setColorEachLine(false);
     * </font></pre></p>
     *
     * @return boolean
     */
    public boolean getColorEachLine()
    {
        return colorEachLine;
    }

    /**
     * Enables/Disables the coloring of each connecting line of a series.<br>
     * Default value: true
     *
     * @param value boolean
     */
    public void setColorEachLine(boolean value)
    {
        colorEachLine = setBooleanProperty(colorEachLine, value);
    }

    /**
     * Changes the direction of the step, when true.<br>
     * Default value: false
     *
     * @return boolean
     */
    public boolean getInvertedStairs()
    {
        return invertedStairs;
    }

    /**
     * Changes the direction of the step, when true.<br>
     * Default value: false
     *
     * @param value boolean
     */
    public void setInvertedStairs(boolean value)
    {
        invertedStairs = setBooleanProperty(invertedStairs, value);
    }

    /**
     * Pen for Series Line's outer pen.<br>
     * Default value: null
     *
     * <p>Example:
     * <pre><font face="Courier" size="4">
     * lineSeries.getOutline().setColor(Color.Yellow);
     * </font></pre></p>
     *
     * @return ChartPen
     */
    public ChartPen getOutLine()
    {
        if (outLine == null)
        {
            outLine = new ChartPen(chart, Color.BLACK, false,
                    LineCap.ROUND);
        }
        return outLine;
    }
    
     /**
     * Gets whether the Line Series' line is smoothed.<br>
     * Default value: false
     *
     * @return boolean
     */
    public boolean getSmoothed()
    {
        return smoothed;
    }
    
    public void assign(Series source) {
        super.assign(source);
        if(source instanceof Custom) {
            Custom series = (Custom)source;
            this.setStairs(series.getStairs());
            this.setClickableLine(series.getClickableLine());
            this.setLineHeight(series.getLineHeight());
            if(series.bAreaBrush != null) {
              if(this.bAreaBrush == null)
                this.bAreaBrush =  new ChartBrush(this.getChart());
                this.bAreaBrush.assign(series.bAreaBrush);
            }
            if(series.pAreaLines != null) {
                if(this.pAreaLines == null)
                  this.pAreaLines =  new ChartPen(this.getChart());
                this.pAreaLines.assign(series.pAreaLines);
            }
            this.getLinePen().assign(series.getLinePen());
            
            this.bDark3D=((Custom)source).bDark3D;
            this.drawArea=((Custom)source).drawArea;
            this.invertedStairs=((Custom)source).invertedStairs;
            this.colorEachLine=((Custom)source).colorEachLine;
            this.lineHeight=((Custom)source).lineHeight;
            this.outLine=((Custom)source).outLine;
            this.stairs=((Custom)source).stairs;
            this.smoothed=((Custom)source).smoothed;
        }
    }
    
    private Custom smoothSeries;
    private Smoothing smoothFunction;

    boolean oldPenVisible = true;
    boolean oldAreaLinesVisible = true;
    boolean oldShowInLegend;
        
    public void SmoothSeries()
    {
      boolean settingSrc = false;     

      if (smoothed == true)
      {
        if ((smoothSeries == null) || (!this.getXValues().getStatsOk()) || (!this.getYValues().getStatsOk()))
        {
          if (smoothSeries == null)
          {
            smoothSeries = new Custom();
            smoothSeries.getPointer().setVisible(false);
            oldPenVisible = this.getLinePen().getVisible();
            oldColor = this.getColor();
            oldShowInLegend = this.getChart().getLegend().getVisible();
            if (this.drawArea)
            {
              oldAreaLinesVisible = this.pAreaLines.getVisible();
              this.pAreaLines.setVisible(false);
            }
            smoothSeries.InternalUse = true;
            smoothFunction = new Smoothing(this.getChart());
            smoothFunction.setInterpolate(true);
            smoothFunction.setFactor(8);
            setFunction(smoothFunction);
            settingSrc = true;
          }

          if (smoothSeries.getCount() == 0)
          {
            smoothSeries.getXValues().setValues(getXValues().getValues());
            smoothSeries.getXValues().setCount(getCount());
            smoothSeries.getYValues().setValues(getYValues().getValues());
            smoothSeries.getYValues().setCount(getCount());
          }

          if (settingSrc)
          {
            oldDataSource = getDataSource();
            setDataSource(smoothSeries);
          }

          this.checkDataSource();
        }
      }
      else
      {
        if (smoothSeries != null)
        {
          if (this.drawArea)
          {
            this.pAreaLines.setVisible(oldAreaLinesVisible);
          }

          if (getCount() > smoothSeries.getCount())
          {
            setFunction(null);
            smoothFunction = null;
            setDataSource(oldDataSource);

            if (getDataSource() != null)
            {
              checkDataSource();
            }
            else
            {
              getXValues().setValues(smoothSeries.getXValues().getValues());
              getXValues().setCount(smoothSeries.getCount());
              getXValues().setStatsOk(false);
              getYValues().setValues(smoothSeries.getYValues().getValues());
              getYValues().setCount(smoothSeries.getCount());
              getYValues().setStatsOk(false);
            }

            getChart().getSeries().remove(smoothSeries);
            smoothSeries = null;
          }
        }
      }
    }    
    
     /**
     * Sets whether the Line Series' line is smoothed.<br>
     * Default value: false
     *
     * @return boolean
     */
    public void setSmoothed(boolean value)
    {
        boolean oldPenVisible = true;
        boolean oldBrushVisible = true;
        boolean oldBrushGradientVisible = false;
        Color oldAreaBrushColor = this.getColor();
        boolean oldAreaLinesVisible = true;
        smoothed = value;
        if(value) {
            smoothSeries = (Custom)this.clone();
            smoothSeries.getPointer().setVisible(false);
            oldPenVisible = this.getLinePen().getVisible();
            oldBrushVisible = this.getBrush().getVisible();
            if(this.drawArea) {
                oldAreaBrushColor = this.bAreaBrush.getColor();
                oldAreaLinesVisible = this.pAreaLines.getVisible();
            }
            smoothSeries.InternalUse = true; 
            smoothSeries.setShowInLegend(false);
            smoothFunction = new Smoothing(getChart());
            smoothFunction.InternalUse = true;
            smoothFunction.setInterpolate(true);
            smoothFunction.setFactor(8);
            smoothSeries.setFunction(smoothFunction);
            smoothSeries.setDataSource(this);
            if(getChart() != null) {
                for(int i=0; i < getChart().getTools().size(); i++) {
                    if(getChart().getTools().getTool(i) instanceof ToolSeries) {
                        getChart().getSeries().exchange(getChart().getSeries().indexOf(this),
                                getChart().getSeries().indexOf(smoothSeries));
                    }
                }
            }
            this.getLinePen().setVisible(false);
            this.bBrush.getGradient().setVisible(true);
            this.bBrush.setVisible(false);
            if(this.drawArea) {
                this.bAreaBrush.setVisible(false);
                this.pAreaLines.setVisible(false);
                //this.bAreaBrush.setColor(Color.red);
            }          
        }else {
            this.getLinePen().setVisible(oldPenVisible);
            this.bBrush.setVisible(oldBrushVisible);
            this.bBrush.getGradient().setVisible(oldBrushGradientVisible);
            if(this.drawArea) {
                this.bAreaBrush.setVisible(true);
                this.bAreaBrush.setColor(oldAreaBrushColor);
                this.pAreaLines.setVisible(oldAreaLinesVisible);
            } 
            if(smoothSeries != null) {
                getChart().getSeries().remove(smoothSeries);
            }
            if(getChart() != null) {
                for(int i=0; i < getChart().getTools().size(); i++) {
                    if(getChart().getTools().getTool(i) instanceof ToolSeries) {
                        if(((ToolSeries)getChart().getTools().getTool(i)).getSeries() == smoothSeries) {
                          ((ToolSeries)getChart().getTools().getTool(i)).setSeries(this);  
                        } 
                    }
                }
            }
        }
    }

    /**
     * The vertical thickness of the line in pixels.<br>
     * Default value: 0
     *
     * @return int
     */
    public int getLineHeight()
    {
        return lineHeight;
    }

    /**
     * Sets the vertical thickness of the line in pixels.<br>
     * Default value: 0
     *
     * @param value int
     */
    public void setLineHeight(int value)
    {
        lineHeight = setIntegerProperty(lineHeight, value);
    }

    public void calcHorizMargins(Margins margins)
    {
        super.calcHorizMargins(margins);
        internalCalcMargin(!getYMandatory(), true, margins);
    }

    private void internalCalcMargin(boolean sameSide,
            boolean horizontal,
            Margins margins)
    {
        if (horizontal)
        {
            getPointer().calcHorizMargins(margins);
        }
        else
        {
            getPointer().calcVerticalMargins(margins);
        }

        int a = margins.min;
        int b = margins.max;

        if (drawLine)
        {
            if (stairs)
            {
                a = Math.max(a, linePen.getWidth());
                b = Math.max(b, linePen.getWidth() + 1);
            }
            if ((outLine != null) && (outLine.getVisible()))
            {
                a = Math.max(a, outLine.getWidth());
                b = Math.max(b, outLine.getWidth());
            }
        }

        if (marks != null)
        {
            if (marks.getVisible() && sameSide)
            {
                if (yMandatory)
                {
                    a = Math.max(a, marks.getArrowLength());
                }
                else
                {
                    b = Math.max(b, marks.getArrowLength());
                }
            }

            if (marks.getVisible() && sameSide)
            {
                int tmp = marks.getCallout().getLength()
                        + marks.getCallout().getDistance();
                if (yMandatory)
                {
                    a = Math.max(b, tmp);
                }
                else
                {
                    b = Math.max(a, tmp);
                }
            }
        }
    }

    public void calcVerticalMargins(Margins margins)
    {
        super.calcVerticalMargins(margins);

        internalCalcMargin(yMandatory, false, margins);

        if ((lineHeight > 0) && (!drawArea) && chart.getAspect().getView3D())
        {
            if (lineHeight > margins.max)
            {
                margins.max = lineHeight;
            }
        }
    }

    static private boolean pointInVertLine(Point p, int x0, int y0, int y1)
    {
        return Utils.pointInLineTolerance(p, x0, y0, x0, y1, 3);
    }

    static private boolean pointInHorizLine(Point p, int x0, int y0, int x1)
    {
        return Utils.pointInLineTolerance(p, x0, y0, x1, y0, 3);
    }

    private boolean checkPointInLine(Point p, int tmpX, int tmpY,
            int oldXPos, int oldYPos)
    {

        if (chart.getAspect().getView3D())
        {
            Point[] tmp =
            {
                new Point(tmpX, tmpY),
                new Point(tmpX
                + chart.getSeriesWidth3D(),
                tmpY - chart.getSeriesHeight3D()),
                new Point(oldXPos
                + chart.getSeriesWidth3D(),
                oldYPos - chart.getSeriesHeight3D()),
                new Point(oldXPos, oldYPos)
            };
            return Graphics3D.pointInPolygon(p, tmp);
        }
        else if (stairs)
        {
            if (invertedStairs)
            {
                return pointInVertLine(p, oldXPos, oldYPos, tmpY)
                        || pointInHorizLine(p, oldXPos, tmpY, tmpX);
            }
            else
            {
                return pointInHorizLine(p, oldXPos, oldYPos, tmpX)
                        || pointInVertLine(p, tmpX, oldYPos, tmpY);
            }
        }
        else
        {
            return Utils.pointInLineTolerance(p, tmpX, tmpY,
                    oldXPos,
                    oldYPos, 3);
        }
    }

    /**
     * Calculates if any point is at XY position.
     *
     * @param x int
     * @param y int
     * @return int Point index
     */
    public int clicked(int x, int y)
    {

        if (chart != null)
        {
            Point p = chart.getGraphics3D().calculate2DPosition(x, y, getStartZ());
            x = p.x;
            y = p.y;
        }

        int result = super.clicked(x, y);

        if ((result == -1)
                && (firstVisible > -1) && (lastVisible > -1))
        {
            int oldXPos = 0;
            int oldYPos = 0;
            int previousBottomPos = 0;
            Point p = new Point(x, y);

            //for (int t = lastVisible; t >= firstVisible; t--) {
            for (int t = firstVisible; t <= lastVisible; t++)
            {

                int tmpX = calcXPos(t);
                int tmpY = calcYPos(t);

                if (getPointer().getVisible())
                {
                    if (clickedPointer(t, tmpX, tmpY, x, y))
                    {
                        //doClickPointer(t, x, y);
                        return t;
                    }
                }
                if ((tmpX == x) && (tmpY == y))
                {
                    return t;
                }

                if ((t > firstVisible) && bClickableLine && (t > 0))
                {
                    if (checkPointInLine(p, tmpX, tmpY, oldXPos, oldYPos)
                            || (drawArea
                            && Graphics3D.pointInPolygon(p, new Point[]
                            {
                                new Point(oldXPos, oldYPos),
                                new Point(tmpX, tmpY),
                                new Point(tmpX, getOriginPos(t)),
                                new Point(oldXPos, getOriginPos(t - 1))
                            })))
                    {
                        return t;
                    }
                }

                oldXPos = tmpX;
                oldYPos = tmpY;
                previousBottomPos = bottomPos;
            }
        }

        return result;
    }

    protected void draw()
    {
        if (this.drawLine)
        {
            int left = this.calcXPos(super.firstVisible);
            int top = super.calcPosValue(super.mandatory.getMaximum());
            int right = this.calcXPos(super.lastVisible);
            int bottom = super.calcPosValue(super.mandatory.getMinimum());
        }
        if ((outLine != null) && (outLine.getVisible()))
        {
            Color previousColor = getColor();
            setColor(outLine.getColor());
            int oldWidth = linePen.getWidth();
            ChartPen oldPen = linePen;
            linePen = outLine;
            linePen.setWidth(oldWidth + outLine.getWidth() + 2);
            super.draw();
            linePen.setWidth(oldWidth);
            linePen = oldPen;
            setColor(previousColor);
        }
        super.draw();
    }

    /* calculate vertical pixel */
    private int calcYPosLeftRight(double yLimit, int anotherIndex,
            int valueIndex)
    {
        double tmpPredValueX = vxValues.value[anotherIndex];
        double tmpDif = vxValues.value[valueIndex] - tmpPredValueX;
        if (tmpDif == 0)
        {
            return calcYPos(anotherIndex);
        }
        else
        {
            double tmpPredValueY = vyValues.value[anotherIndex];
            return getVertAxis().calcYPosValue(1.0 * tmpPredValueY
                    + (yLimit - tmpPredValueX)
                    * (vyValues.value[valueIndex]
                    - tmpPredValueY) / tmpDif);
        }
    }

//    private Rectangle RectFromPoints(int P0X,int P0Y,int P1X,int P1Y,int P2X,int P2Y,int P3X,int P3Y)
//    {
//      Rectangle r=new Rectangle();
//      r.x=Math.min(P3X,Math.min(P2X,Math.min(P0X,P1X)));
//      r.y=Math.min(P3Y,Math.min(P2Y,Math.min(P0Y,P1Y)));
//      r.width=Math.max(P3X,Math.max(P2X,Math.max(P0X,P1X)))-r.x;
//      r.height=Math.max(P3Y,Math.max(P2Y,Math.max(P0Y,P1Y)))-r.y;
//      return chart.getAspect().getView3D() ? chart.getGraphics3D().CalcRect3D(r,getStartZ()) : r;
//    }
    protected Color getAreaBrushColor(Color c)
    {
        if (bColorEach)
        {
            return c;
        }
        else if (bAreaBrush == null)
        {
            return c;
        }
        else
        {
            return bAreaBrush.getColor().isEmpty() ? c : bAreaBrush.getColor();
        }
    }

    private void drawArea(Color brushColor, int x, int y)
    {
        IGraphics3D g = chart.getGraphics3D();

        if (!bAreaBrush.getColor().isEmpty())
        {
            bAreaBrush.setTransparency(getTransparency());
        }

        chart.setBrushCanvas(brushColor, bAreaBrush,
                bAreaBrush.getColor().isEmpty() ? getColor()
                : bAreaBrush.getColor());

        if (chart.getAspect().getView3D() && isLastValue)
        { // final point
            if (yMandatory)
            {
                g.rectangleZ(x, y, bottomPos, getStartZ(), getEndZ());
            }
            else
            {
                g.rectangleY(x, y, bottomPos, getStartZ(), getEndZ());
            }
        }

        int tmpY;
        int tmpBottom;
        Rectangle tmpR;

        if (stairs)
        {
            if (invertedStairs)
            {
                tmpY = yMandatory ? y : x;
                tmpBottom = bottomPos;
            }
            else
            {
                tmpY = yMandatory ? oldY : oldX;
                tmpBottom = oldBottomPos;
            }

            if (yMandatory)
            {
                //tmpR=new Rectangle(OldX,tmpBottom,x+1-OldX,tmpY-tmpBottom);
                tmpR = new Rectangle(oldX, tmpY, x - oldX,
                        tmpBottom - tmpY);
            }
            else
            {
                tmpR = new Rectangle(tmpBottom, y, tmpY - tmpBottom - 1,
                        oldY - y);
            }

            if (chart.getAspect().getView3D())
            {
                g.rectangle(tmpR, getStartZ());
                if (g.getSupportsFullRotation())
                {
                    g.rectangle(tmpR, getEndZ());
                }
            }
            else
            {
                g.rectangle(tmpR);
            }
        }
        else
        {
            // not in "stairs" mode...

            Point tmp0, tmp3;

            if (yMandatory)
            {
                tmp0 = new Point(oldX, oldBottomPos);
                tmp3 = new Point(x, bottomPos);
            }
            else
            {
                tmp0 = new Point(oldBottomPos, oldY);
                tmp3 = new Point(bottomPos, y);
            }

            Point tmp1 = new Point(oldX, oldY);
            Point tmp2 = new Point(x, y);

            if (chart.getAspect().getView3D())
            {
                g.plane(tmp0, tmp1, tmp2, tmp3, startZ);
            }
            else if ((getBrush() != null) && (getBrush().getGradientVisible()))
            {
                Point[] tmp =
                {
                    tmp0, tmp1, tmp2, tmp3
                };
                g.clipPolygon(tmp);

                int tmpMax = calcPosValue(mandatory.getMaximum());
                int tmpMin = calcPosValue(mandatory.getMinimum());

                if (yMandatory)
                {
                    tmpR = new Rectangle(oldX, tmpMax, x, tmpMin);
                }
                else
                {
                    tmpR = new Rectangle(tmpMin, oldY, tmpMax, y);
                }

                //Rectangle cr=chart.getChartRect();
                //tmpR=new Rectangle(OldX,cr.y,x-OldX,cr.Bottom()-cr.y);

                getBrush().getGradient().draw(g, tmpR);
                g.unClip();

                getBrush().setVisible(false);
                if (pAreaLines.getVisible())
                {
                    if (yMandatory)
                    {
                        g.verticalLine(oldX, oldY, oldBottomPos);
                    }
                    else
                    {
                        g.horizontalLine(oldBottomPos, oldX, oldY);
                    }
                }

            }
            else
            { // 2D
                Point[] tmp =
                {
                    tmp0, tmp1, tmp2, tmp3
                };
                g.polygon(tmp);
            }

            if (g.getSupportsFullRotation())
            { // Back plane
                g.plane(tmp0, tmp1, tmp2, tmp3, getEndZ());
            }

            if (linePen.getVisible())
            {
                g.setPen(getLinePen());
                g.line(oldX, oldY, x, y, getStartZ());
            }
        }
    }
    private Point[] p4 = new Point[4];
    
 private void drawPoint(boolean drawOldPointer, int valueIndex, int x, int y)

    {

      int tmpDifX;
      Point[] P4 = new Point[4];
      Color OldDarkColor;
      boolean tmpDark3D;
      boolean isHotSpot = false;
      Graphics3D g = (Graphics3D) chart.getGraphics3D();
      Color oldBrushColor;
      Color oldPenColor;

      if (((x != oldX) || (y != oldY)) && ((getTreatNulls() == TreatNullsStyle.IGNORE))  || !isNull(valueIndex))
      {// <-- if !null
        if (chart.getAspect().getView3D())
        {// <-- 3D
          if (drawArea || drawLine)
          {
            g.setPen(getLinePen());
            g.setBrush(getBrush());

            if (colorEachLine || drawArea)
              OldDarkColor = getAreaBrushColor(tmpColor);
            else
              OldDarkColor = getColor();  // 6.01

            oldPenColor = getLinePen().getColor();
            oldBrushColor = getBrush().getColor();
            getBrush().setColor(OldDarkColor);

            Point tmpPoint = new Point(x, y);
            Point tmpOldP = new Point(oldX, oldY);

            if (stairs)
            {
              if (invertedStairs)
              { /* || LastValue=FirstValueIndex */
                if (bDark3D) g.getBrush().applyDark(Graphics3D.DARKCOLORQUANTITY);
                if (yMandatory)
                {
                  if (drawValuesForward())
                    g.rectangleZ(tmpOldP.x, tmpOldP.y, y, startZ, endZ);
                  else
                    g.rectangleZ(x, tmpOldP.y, y, startZ, endZ);
                }
                else
                {
                  g.rectangleY(oldX, oldY, x, startZ, endZ);
                }
                if (bDark3D) g.getBrush().setColor(OldDarkColor);
                if (yMandatory)
                {
                  if (drawValuesForward())
                    g.rectangleY(tmpPoint.x, tmpPoint.y, oldX, startZ, endZ);
                  else
                    g.rectangleY(oldX, oldY, x, startZ, endZ);
                }
                else
                {
                  g.rectangleZ(x, tmpOldP.y, y, startZ, endZ);
                }

              }
              else
              {
                if (yMandatory)
                {
                  if (drawValuesForward())
                    g.rectangleY(tmpOldP.x, tmpOldP.y, x, startZ, endZ);
                  else
                    g.rectangleY(tmpOldP.x, y, x, startZ, endZ);
                }
                else
                {
                  if (drawValuesForward())
                    g.rectangleZ(oldX, tmpOldP.y, y, startZ, endZ);
                  else
                    g.rectangleZ(x, tmpOldP.y, y, startZ, endZ);
                }
                if (bDark3D) g.getBrush().applyDark(Graphics3D.DARKCOLORQUANTITY);
                if (yMandatory)
                {
                  if (drawValuesForward())
                    g.rectangleZ(tmpPoint.x, tmpPoint.y, oldY, startZ, endZ);
                  else
                    g.rectangleZ(oldX, oldY, y, startZ, endZ);
                }
                else
                {
                  if (drawValuesForward())
                    g.rectangleY(oldX, y, x, startZ, endZ);
                  else
                    g.rectangleY(oldX, oldY, x, startZ,endZ);
                }
                if (bDark3D) g.getBrush().setColor(OldDarkColor);
              }
            }
            else
            {
              boolean highLine = false;

              if ((lineHeight > 0) && (!drawArea))
              {
                Point3D neartmpPoint = new Point3D(tmpPoint.x,tmpPoint.y, startZ);
                Point3D fartmpPoint = new Point3D(tmpPoint.x,tmpPoint.y, endZ);

                Point3D neartmpPointHigh = new Point3D(tmpPoint.x, tmpPoint.y + lineHeight, startZ);
                Point3D fartmpPointHigh = new Point3D(tmpPoint.x, tmpPoint.y + lineHeight, endZ);

                Point3D neartmpOldP = new Point3D(tmpOldP.x,tmpOldP.y, startZ);
                Point3D fartmpOldP = new Point3D(tmpOldP.x,tmpOldP.y, endZ);

                Point3D neartmpOldPHigh = new Point3D(tmpOldP.x, tmpOldP.y + lineHeight, startZ);
                Point3D fartmpOldPHigh = new Point3D(tmpOldP.x, tmpOldP.y + lineHeight, endZ);

                Point3D[] back = new Point3D[]
                {
                  fartmpPoint,
                  fartmpPointHigh,
                  fartmpOldPHigh,
                  fartmpOldP
                };

                Point3D[] bottom = new Point3D[]
                {
                  neartmpOldPHigh,
                  fartmpOldPHigh,
                  fartmpPointHigh,
                  neartmpPointHigh
                };

                Point3D[] front = new Point3D[]
                {
                  neartmpOldP,
                  neartmpPoint,
                  neartmpPointHigh,
                  neartmpOldPHigh
                };

                Point3D[] top = new Point3D[]
                {
                  neartmpOldP,
                  neartmpPoint,
                  fartmpPoint,
                  fartmpOldP,
                };

                g.polygon(bottom);
                g.polygon(back);
                g.polygon(top);
                g.polygon(front);
                if (isLastValue)
                  g.rectangleZ(tmpPoint.x, tmpPoint.y, tmpPoint.y + lineHeight, startZ, endZ);
                highLine = true;
              }

              tmpDark3D = bDark3D && (!g.getSupportsFullRotation());
              if (tmpDark3D)
              {
                tmpDifX = tmpPoint.x - tmpOldP.x;
                if ((tmpDifX != 0) &&
                  (tmpDark3DRatio != 0) &&
                  ((tmpOldP.y - tmpPoint.y) / tmpDifX > tmpDark3DRatio))
                {
                  g.getBrush().applyDark(Graphics3D.DARKCOLORQUANTITY);
                  if ((lineHeight > 0) && (!drawArea) && Math.abs(tmpPoint.y - tmpOldP.y) > lineHeight + 1)
                  { /*special case*/
                    tmpPoint.y += lineHeight;
                    tmpOldP.y += lineHeight;
                  }
                }
              }
              if (g.getMonochrome()) g.getBrush().setColor(Color.WHITE);

              if (!highLine) g.plane(tmpPoint, tmpOldP, startZ, endZ); //CDI
              if (tmpDark3D) g.getBrush().setColor(OldDarkColor);
            }
            getBrush().setColor(oldBrushColor);
            getLinePen().setColor(oldPenColor);
          }
        }

        if (drawArea)
        {
          Color tmp = getAreaBrushColor(tmpColor);

          oldPenColor = pAreaLines.getColor();

          if (pAreaLines.getVisible())
          {
            if (getColorEach())
            {
              pAreaLines.setColor(Utils.darkenColor(tmp, 60));
            }
          }
          else
          {
            pAreaLines.setColor(tmp);
          }

          g.setPen(pAreaLines);

          drawArea(tmp, x, y);

          pAreaLines.setColor(oldPenColor);
        }
        else
          if ((!chart.getAspect().getView3D()) && drawLine && getLinePen().getVisible()        //CDI if LinePen.Visible = false should not paint in 2D

)
          {

            // line 2D
            oldBrushColor = getBrush().getColor();
            oldPenColor = getLinePen().getColor();
            linePrepareCanvas(g, (getColorEach() && !point.getVisible()) || (colorEachLine && point.getVisible()) || (!point.getVisible()) ? tmpColor : getColor());

            if (stairs)
            {
              if (invertedStairs) g.verticalLine(oldX, oldY, y); else g.horizontalLine(oldX, x, oldY);
              g.lineTo(x, y);
            }
            else g.line(oldX, oldY, x, y);

            getBrush().setColor(oldBrushColor);
            getLinePen().setColor(oldPenColor);
          }

        /* pointers */
        if (point.getVisible() && drawOldPointer)
        {

          if (isHotSpot)
          {
            drawPointer(x, y, tmpColor, valueIndex); //CDI call 1
          }
          else
          {
            if (drawValuesForward() ? valueIndex > 0 : valueIndex < getCount()) //CDI TF02014856
            {
              if (isNull(drawValuesForward() ? valueIndex - 1 : valueIndex + 1))
              {
                int count = getPreviousNotNull(drawValuesForward() ? valueIndex - 1 : valueIndex + 1);
                Color tmpCol2 = getValueColor(count);
                drawPointer(oldX, oldY, tmpCol2, count);
              }
              else
              {
                drawPointer(oldX, oldY, oldColor, drawValuesForward() ? valueIndex - 1 : valueIndex + 1); //CDI call 3
              }
            }

            if (isLastValue && (!isNull(valueIndex)/*tmpColor != Color.Transparent*/))
            { /*<-- if ( !null */
              if (!drawValuesForward())
              {
                drawPointer(oldX, oldY, oldColor, valueIndex + 1);    //CDI call 4
              }
              drawPointer(x, y, tmpColor, valueIndex);
            }
          }
        }
      }
      else if (point.getVisible()) //CDI TF02013253, TF02013056, TF02014437
      {
        if ((x == oldX) && (y == oldY))
        {
          if (isNull(valueIndex))
          {
            drawPointer(oldX, oldY, oldColor, drawValuesForward() ? valueIndex - 1 : valueIndex + 1);
          }
          else
          {
            drawPointer(x, y, tmpColor, valueIndex);
          }
        }
        else if (isNull(valueIndex))
        {

          if (oldColor != Color.TRANSPARENT)

           drawPointer(oldX, oldY, oldColor, drawValuesForward() ? valueIndex - 1 : valueIndex + 1);
        }
      }
    }

     private int getPreviousNotNull(int nullValueIndex)
    {
      int result = nullValueIndex - 1;
      while (result > -1)
      {
        if (!isNull(result))
        {
          break;
        }
        --result;
      }
      return result;
    }


    /**
     * Called internally. Draws the "ValueIndex" point of the Series.
     *
     * @param valueIndex int
     */
    public void drawValue(int valueIndex)
    {
        IGraphics3D g = chart.getGraphics3D();

        tmpColor = getValueColor(valueIndex);

        if (isNull(valueIndex) && getTreatNulls() == TreatNullsStyle.IGNORE)
        {
            tmpColor = getColor();
        }

        int x = calcXPos(valueIndex);
        int y = calcYPos(valueIndex);

        g.getPen().setColor(Color.BLACK);
        g.getBrush().setColor(tmpColor);

        if ((isNull(valueIndex)/*tmpColor == Color.Transparent*/)
                && (getTreatNulls() == TreatNullsStyle.SKIP))
        {
            x = oldX;
            y = oldY;
        }

        if (isNull(valueIndex - 1)/*OldColor == Color.Transparent*/)
        {
            if (getTreatNulls() == TreatNullsStyle.DONOTPAINT)
            {
                oldX = x;
                oldY = y;
            }
        }

        bottomPos = getOriginPos(valueIndex);

        int tmpFirst;

        if (drawValuesForward())
        {
            tmpFirst = firstVisible;
            isLastValue = (valueIndex == lastVisible);
        }
        else
        {
            tmpFirst = lastVisible;
            isLastValue = (valueIndex == firstVisible);
        }

        if (valueIndex == tmpFirst)
        {
            // first point
            if (bDark3D)
            {
                if (chart.getSeriesWidth3D() != 0)
                {
                    tmpDark3DRatio = chart.getSeriesHeight3D() / chart.getSeriesWidth3D();
                }
                else
                {
                    tmpDark3DRatio = 1;
                }
            }

            if ((tmpFirst == firstVisible) && (valueIndex > 0))
            {
                // previous point outside left
                if (drawArea)
                {
                    oldX = calcXPos(valueIndex - 1);
                    oldY = calcYPos(valueIndex - 1);
                    oldBottomPos = getOriginPos(valueIndex - 1);
                }
                else
                {
                    Rectangle tmpRect = chart.getChartRect();
                    oldX = getHorizAxis().getInverted() ? tmpRect.getRight()
                            : tmpRect.x;

                    if (stairs)
                    {
                        oldY = calcYPos(valueIndex - 1);
                    }
                    else
                    {
                        oldY = calcYPosLeftRight(xScreenToValue(oldX),
                                valueIndex - 1, valueIndex);
                    }
                }
                if (!isNull(valueIndex - 1))
                {
                    drawPoint(false, valueIndex, x, y);
                }
            }

            if (isLastValue && point.getVisible())
            {
                drawPointer(x, y, tmpColor, valueIndex);
            }

            if (g.getSupportsFullRotation() && drawArea
                    && chart.getAspect().getView3D())
            {
                g.rectangleZ(x, y, bottomPos, getStartZ(), getEndZ());
            }
        }
        else
        {
            drawPoint(true, valueIndex, x, y);
        }

        oldX = x;
        oldY = y;
        oldBottomPos = bottomPos;
        oldColor = tmpColor;
    }

    public void drawHiddenValue(int valueIndex, PointDouble startEndVals,
            int gapSize, Axis axis)
    {
      int tX;
      int tY;
      int tmpGapSize;

      tX = calcXPos(valueIndex);
      tY = calcYPos(valueIndex);

      tmpGapSize = (int)(gapSize / 2);

      if (!(axis.getHorizontal()) && !(getVertAxis().getInverted()))
        tmpGapSize = -tmpGapSize;
      else if ((axis.getHorizontal()) && (getHorizAxis().getInverted()))
        tmpGapSize = -tmpGapSize;

      if (axis.getHorizontal())
        tX = calcXPosValue(startEndVals.x) + tmpGapSize;
      else
        tY = calcYPosValue(startEndVals.y) + tmpGapSize;

      if ((valueIndex > 0) && ((axis.getHorizontal())) && ((getXValues().value[valueIndex - 1] >= startEndVals.x) && (getXValues().value[valueIndex - 1] <= startEndVals.y))
           || ((!axis.getHorizontal())) && (getYValues().value[valueIndex - 1] >= startEndVals.x) &&
          (getYValues().value[valueIndex - 1] <= startEndVals.y))
      {
        //chart.graphics3D.Pen.Style = DashStyle.
        chart.getGraphics3D().getPen().setColor(Utils.getEmptyColor());
      }
      else
      {

        chart.getGraphics3D().setPen(this.getLinePen());
        chart.getGraphics3D().getPen().setColor(getValueColor(valueIndex));
        // TODO chart.graphics3D.Pen.Assign(Pen);
      }

      //chart.graphics3D.Pen.Color = Color.Yellow;
      chart.getGraphics3D().line(oldX, oldY, tX, tY);

      if (getPointer().getVisible())
      {
        if (oldColor == Utils.getEmptyColor())
        {
          if (!doNotPaintNull())
            drawPointer(oldX, oldY, getBrush().getColor(), drawValuesForward() ? valueIndex - 1 : valueIndex + 1);
        }
        else
          drawPointer(oldX, oldY, oldColor, drawValuesForward() ? valueIndex - 1 : valueIndex + 1);

      }

      oldX = tX;
      oldY = tY;
    }


    private void drawLine(IGraphics3D g, boolean drawRectangle,
            Color tmpColor,
            Rectangle rect)
    {
        if (chart.getLegend().getSymbol().getDefaultPen())
        {
            linePrepareCanvas(g, (getColorEach() && !point.getVisible()) || (colorEachLine && point.getVisible()) || (!point.getVisible()) ? tmpColor : getColor());
        }
        
        if (drawRectangle)
        {
            g.rectangle(rect);
        }
        else
        {
            g.horizontalLine(rect.x, rect.getRight(),
                    (rect.y + rect.getBottom()) / 2);
        }
    }

    protected void drawLegendShape(IGraphics3D g, int valueIndex,
            Rectangle rect)
    {
        Color tmpLegendColor = (valueIndex == -1) ? getColor()
                : getValueColor(valueIndex);

        if (getPointer().getVisible())
        {
            if (drawLine)
            {
                drawLine(g, false, tmpLegendColor, rect);
            }
            point.drawLegendShape(g, tmpLegendColor, rect, getLinePen().getVisible());
        }
        else if (drawLine && (!drawArea))
        {
            drawLine(g, chart.getAspect().getView3D(), tmpLegendColor, rect);
        }
        else
        {
            super.drawLegendShape(g, valueIndex, rect);
        }
    }

    private void linePrepareCanvas(IGraphics3D g, Color aColor)
    {
        if (g.getMonochrome()) {
            aColor = Color.WHITE;
        }
        
        g.setBrush(getBrush());
        g.setPen(getLinePen());
        
        if (chart.getAspect().getView3D()) {
            if (bBrush.getImage() != null) {
                g.getBrush().setImage(bBrush.getImage());
            }
            
            if (aColor.isNull()) {
                g.getPen().setColor(aColor);
            }
        }
        else {
            g.getBrush().setVisible(false);
            g.getPen().setColor(aColor);
        }
    }
}
