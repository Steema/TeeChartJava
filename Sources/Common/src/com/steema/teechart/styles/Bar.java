package com.steema.teechart.styles;

import com.steema.teechart.languages.Language;

import com.steema.teechart.drawing.Color;
import com.steema.teechart.drawing.Graphics3D;
import com.steema.teechart.drawing.IGraphics3D;
import com.steema.teechart.drawing.Point;
import com.steema.teechart.IBaseChart;
import com.steema.teechart.Rectangle;

import com.steema.teechart.misc.Utils;

/**
 *
 * <p>Title: Bar class</p>
 *
 * <p>Description: The Bar Series component outputs all points as vertical
 * bars.</p>
 *
 * <p>Copyright (c) 2005-2019 by Steema Software SL. All Rights
 * Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 *
 */
public class Bar extends CustomBar {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Bar() {
        this((IBaseChart)null);
    }

    public Bar(IBaseChart c) {
        super(c);
        marks.getTailParams().setAlign(TailAlignment.Auto);
    }

    /**
     * Gets descriptive text.
     *
     * @return String
     */
    public String getDescription() {
        return Language.getString("GalleryBar");
    }

    /**
     * Determines the percent of total Bar width used.<br>
     * Setting BarWidthPercent = 100 makes joined Bars.<br>
     * You can control how many Bars appear at the same time by using
     * Page.MaxPointsPerPage.<br>
     * Default value: 70
     *
     * @return int
     */
    public int getBarWidthPercent() {
        return barSizePercent;
    }

    /**
     * Sets the percent of total Bar width used.<br>
     * Default value: 70
     *
     * @see #getBarWidthPercent
     * @param value int
     */
    public void setBarWidthPercent(int value) {
        setBarSizePercent(value);
    }

    protected int internalCalcMarkLength(int valueIndex) {
        return chart.getGraphics3D().getFontHeight();
    }

    protected boolean internalClicked(int valueIndex, Point point) {
        boolean tmpResult = false;

        int tmpX = calcXPos(valueIndex);

        if ((point.x >= tmpX) && (point.x <= (tmpX + iBarSize))) {
            int tmpY = calcYPos(valueIndex);
            int endY = getOriginPos(valueIndex);
            if (endY < tmpY) {
                int tmpSwap = endY;
                endY = tmpY;
                tmpY = tmpSwap;
            }

            BarStyle tmpStyle = getBarStyle();
            if (tmpStyle == BarStyle.INVPYRAMID) {
                tmpResult = Graphics3D.pointInTriangle(point, tmpX,
                        tmpX + iBarSize, tmpY, endY);
            } else
            if ((tmpStyle == BarStyle.PYRAMID) | (tmpStyle == BarStyle.CONE)) {
                tmpResult = Graphics3D.pointInTriangle(point, tmpX,
                        tmpX + iBarSize, endY, tmpY);
            } else
            if (tmpStyle == BarStyle.ELLIPSE) {
                tmpResult = Graphics3D.pointInEllipse(point,
                        new Rectangle(tmpX, tmpY, tmpX + iBarSize, endY));
            } else {
                tmpResult = (point.y >= tmpY) && (point.y <= endY);
            }
        }
        return tmpResult;
    }
    
    protected Rectangle calcBarBounds(int valueIndex)
    {
    	Rectangle r;
    	int top, left, right, bottom;

    	if (valueIndex < getCount())
    	{
    		left = calcXPos(valueIndex);

    		if ((barSizePercent == 100) && (customBarSize == 0) && (iMultiBar != MultiBars.SELFSTACK) && (iMultiBar != MultiBars.SIDE))
    		{// 5.02 //CDI TF02015176
    			if (getHorizAxis().getInverted())
    			{
    				if (valueIndex > 0) right = calcXPos(valueIndex - 1);
    				else right = left + iBarSize;
    			}
    			else
    			{
    				if(valueIndex < getCount() - 1) right = calcXPos(valueIndex + 1);
    				else right = left + iBarSize;
    			}
    		}
    		else
    			right = left + iBarSize;  // CDI changed from IBarSize + 1

    		top = calcYPos(valueIndex);
    		bottom = getOriginPos(valueIndex);

    		r = Rectangle.fromLTRB(left, top, right, bottom);

    		if (getPen().getVisible() && !chart.getAspect().getView3D()) // CDI changed
    			r.width++;
    	}
    	else
    	{
    		r = Utils.emptyRectangle();
    	}
    	return r;
    }

    public void calcHorizMargins(Margins margins) {
        super.calcHorizMargins(margins);
        internalApplyBarMargin(margins);
    }

    public void calcVerticalMargins(Margins margins) {

        super.calcVerticalMargins(margins);

        int tmp = calcMarkLength(0);

        if (tmp > 0) {
            tmp++;
            if (bUseOrigin && (super.getMinYValue() < dOrigin)) {
                if (getVertAxis().getInverted()) {
                    margins.min += tmp;
                } else {
                    margins.max += tmp;
                }
            }

            if ((!bUseOrigin) || (super.getMaxYValue() > dOrigin)) {
                if (getVertAxis().getInverted()) {
                    margins.max += tmp;
                } else {
                    margins.min += tmp;
                }
            }
        }
    }
    
    /**
     * Called internally. Draws the "ValueIndex" point of the Series.
     *
     * @param valueIndex int
     */
    public void drawValue(int valueIndex)
    {
        normalBarColor = getValueColor(valueIndex);

      if ((normalBarColor != Color.EMPTY) && (normalBarColor != Color.TRANSPARENT)) 
      {
        iBarBounds = calcBarBounds(valueIndex);
        int tmpY = iBarBounds.getTop();

        if ((mandatory.getValue(valueIndex) != 0) || ((getMultiBar() != MultiBars.STACKED) && (getMultiBar() != MultiBars.STACKED100) && (getMultiBar() != MultiBars.SELFSTACK)))
        {
          if (iBarBounds.getBottom() > tmpY)
            drawBar(valueIndex, tmpY, iBarBounds.getBottom());
          else
            drawBar(valueIndex, iBarBounds.getBottom(), tmpY);
        }
      }
    }

    protected void drawTickLine(int tickPos, BarStyle aStyle)
    {
        super.drawTickLine(tickPos, aStyle);
        
        if ((aStyle == BarStyle.RECTANGLE) | (aStyle == BarStyle.RECTGRADIENT)) {
            chart.getGraphics3D().horizontalLine(getBarBounds().getLeft(), getBarBounds().getRight(), tickPos, startZ);
        } else
        if (aStyle == BarStyle.ARROW) {
            int tmp = getBarBounds().width / 4;
            chart.getGraphics3D().horizontalLine(getBarBounds().getLeft() + tmp, getBarBounds().getRight() - tmp, tickPos, middleZ);
        } 
        
        if (chart.getAspect().getView3D()) {
            if ((aStyle == BarStyle.RECTANGLE) | (aStyle == BarStyle.RECTGRADIENT)) {
                Point p0 = chart.getGraphics3D().calculate3DPosition(getBarBounds().getBottom(), getBarBounds().getRight(), startZ);
                Point p1 = chart.getGraphics3D().calculate3DPosition(getBarBounds().getBottom(), getBarBounds().getRight(), endZ);
                Point p2 = chart.getGraphics3D().calculate3DPosition(getBarBounds().getRight(), getBarBounds().getTop(), endZ);                                     
                if (Graphics3D.cull(p0, p1, p2))
                {
                    p0 = chart.getGraphics3D().calculate3DPosition(getBarBounds().getLeft(), getBarBounds().getBottom(), startZ);
                    p1 = chart.getGraphics3D().calculate3DPosition(getBarBounds().getLeft(), getBarBounds().getBottom(), endZ);
                    p2 = chart.getGraphics3D().calculate3DPosition(getBarBounds().getLeft(), getBarBounds().getTop(), endZ);                    
                    if (Graphics3D.cull(p0, p1, p2))
                      chart.getGraphics3D().zLine(getBarBounds().getLeft(), tickPos, startZ, endZ);
                }
                else
                    chart.getGraphics3D().zLine(getBarBounds().getRight(), tickPos, startZ, endZ);
            } 
        }
    }
    
    /**
     * Internal use
     *
     * @param barIndex int
     * @param startPos int
     * @param endPos int
     */
    public void drawBar(int barIndex, int startPos, int endPos) {
        
    	setPenBrushBar(normalBarColor);

        int tmpMidX = getBarBoundsMidX();
        BarStyle tmp = doGetBarStyle(barIndex);

        IGraphics3D g = chart.getGraphics3D();
        Rectangle r = iBarBounds;
        
        if (chart.getAspect().getView3D()) {
            if (tmp == BarStyle.PYRAMID) {
                g.pyramid(true, r.x, startPos, r.getRight(), endPos, getStartZ(),
                          getEndZ(),
                          bDark3D);
            } else
            if (tmp == BarStyle.INVPYRAMID) {
                g.pyramid(true, r.x, endPos, r.getRight(), startPos, getStartZ(),
                          getEndZ(),
                          bDark3D);
            } else
            if (tmp == BarStyle.CYLINDER) {
                g.cylinder(true, iBarBounds, getStartZ(), getEndZ(), bDark3D);
            } else
            if (tmp == BarStyle.ELLIPSE) {
                g.ellipse(iBarBounds, getMiddleZ());
            } else
            if (tmp == BarStyle.ARROW) {
                g.arrow(true, new Point(tmpMidX, endPos),
                        new Point(tmpMidX, startPos),
                        r.width, r.width / 2, getMiddleZ());
            } else
            if ((tmp == BarStyle.RECTANGLE) || (tmp == BarStyle.RECTGRADIENT)
                    || (tmp == BarStyle.ROUNDRECTANGLE)) {
                
                g.cube(r.x, startPos, r.getRight(), endPos, getStartZ(),
                       getEndZ(), bDark3D);
                if ((getGradient().getVisible()) || (tmp == BarStyle.RECTGRADIENT))
	                if (g.getSupportsFullRotation() ||
	                    chart.getAspect().getOrthogonal()) {
	                    doGradient3D(barIndex,
	                                 g.calc3DPoint(r.x, startPos, getStartZ()),
	                                 g.calc3DPoint(r.getRight(), endPos, getStartZ()));
	                }
            } else
            if (tmp == BarStyle.CONE) {
                g.cone(true, iBarBounds, getStartZ(), getEndZ(), bDark3D,
                       conePercent);
            } else
            if (tmp == BarStyle.INVCONE) {
                double h = iBarBounds.getHeight();
                iBarBounds.y = iBarBounds.getBottom();
                iBarBounds.height -= h * 2;
                g.cone(true, iBarBounds, getStartZ(), getEndZ(), bDark3D,
                       conePercent);
            }
        } else {
            if ((tmp == BarStyle.RECTANGLE) |
                (tmp == BarStyle.CYLINDER)) {
            	if (getGradient().getVisible())
            		doBarGradient(barIndex,
                            new Rectangle(r.x, startPos, r.getRight() - r.x,
                                          endPos - startPos));
            	else
                  barRectangle(normalBarColor, iBarBounds);
            } else
            if ((tmp == BarStyle.PYRAMID) |
                (tmp == BarStyle.CONE)) {
                Point[] p = {new Point(r.x, endPos),
                            new Point(tmpMidX, startPos),
                            new Point(r.getRight(), endPos)};
                g.polygon(p);
            } else
            if ((tmp == BarStyle.INVPYRAMID) |
                (tmp == BarStyle.INVCONE)) {
                Point[] p = {new Point(r.x, startPos),
                            new Point(tmpMidX, endPos),
                            new Point(r.getRight(), startPos)};
                g.polygon(p);
            } else
            if (tmp == BarStyle.ELLIPSE) {
                g.ellipse(iBarBounds);
            } else
            if (tmp == BarStyle.ARROW) {
                g.arrow(true, new Point(tmpMidX, endPos),
                        new Point(tmpMidX, startPos),
                        r.width, r.width / 2, getMiddleZ());
            } else
            if (tmp == BarStyle.RECTGRADIENT) {
                doBarGradient(barIndex,
                              new Rectangle(r.x, startPos, r.getRight() - r.x,
                                            endPos - startPos));
            } else
            if (tmp == BarStyle.ROUNDRECTANGLE) {
                barRectangle(normalBarColor, iBarBounds, barIndex, true);
            }
        }
        drawTickLines(startPos, endPos, tmp);
    }

    protected boolean moreSameZOrder() {
        return (iMultiBar == MultiBars.SIDEALL) ? false : super.moreSameZOrder();
    }
    
	@Override
	public boolean drawSeriesForward(int valueIndex) {

		boolean result = super.drawSeriesForward(valueIndex);

		if ((iMultiBar == MultiBars.NONE) || (iMultiBar == MultiBars.SIDE) || (iMultiBar == MultiBars.SIDEALL)) {
			result = super.drawSeriesForward(valueIndex);
		} else if ((iMultiBar == MultiBars.STACKED) || (iMultiBar == MultiBars.STACKED100)) {
			result = !getMandatoryAxis().getInverted();
		} else if (iMultiBar == MultiBars.SELFSTACK) {
			result = getYValues().getValue(valueIndex) >= getOrigin();
			result = (getVertAxis().getInverted() ? result : !result);
		}

		return result;
	}
	
    /**
     * The Screen X pixel coordinate of the ValueIndex Series
     * value.<br>
     * The horizontal Bar position is the "real" X pos + the BarWidth by our
     * BarSeries order.
     *
     * @param valueIndex int
     * @return int
     */
    public int calcXPos(int valueIndex) {

        int result = 0;

        if (iMultiBar == MultiBars.SIDEALL) {
            result = getHorizAxis().calcXPosValue(iPreviousCount + valueIndex) -
                     (iBarSize / 2);
        } else
        if (iMultiBar == MultiBars.SELFSTACK) {
            result = (super.calcXPosValue(getMinXValue())) - (iBarSize / 2);
        } else {
            result = super.calcXPos(valueIndex);

            if (iMultiBar != MultiBars.NONE) {
                result +=
                        Utils.round(iBarSize *
                                        ((iOrderPos - (iNumBars * 0.5)) - 1.0));
            } else {
                result -= (iBarSize / 2);
            }
        }

        return applyBarOffset(result);
    }

    /**
     * The  Screen Y pixel coordinate of the ValueIndex Series value.
     *
     * @param valueIndex int
     * @return int
     */
    public int calcYPos(int valueIndex) {
        int result = 0;

        if ((iMultiBar == MultiBars.NONE) |
            (iMultiBar == MultiBars.SIDE) |
            (iMultiBar == MultiBars.SIDEALL)) {
            result = super.calcYPos(valueIndex);
        } else {
            double tmpValue = vyValues.value[valueIndex] +
                              pointOrigin(valueIndex, false);
            if ((iMultiBar == MultiBars.STACKED) ||
                (iMultiBar == MultiBars.SELFSTACK)) {
                result = calcYPosValue(tmpValue);
            } else {
                double tmp = pointOrigin(valueIndex, true);
                result = (tmp != 0) ? calcYPosValue(tmpValue * 100.0 / tmp) : 0;
            }
        }

        return result;
    }
    boolean autoMarkPointer = false;
    @Override
    protected void drawMark(int valueIndex, String s,
                            SeriesMarksPosition p)
    {
     int DifW;
      int DifH;

      DifW = iBarSize / 2;
      DifH = getMarks().getCallout().getLength() + getMarks().getCallout().getDistance();


      if (!getMarksOnBar())
      {
          if (p.arrowFrom.getY() > getOriginPos(valueIndex))
          {
              DifH = -DifH - p.height;
              p.arrowFrom.y += getMarks().getCallout().getDistance();
              if (autoMarkPointer)
              {
                getMarks().getTailParams().setAlign(TailAlignment.TopCenter);
              }
              //getMarks().getTailParams().setAlign(TailAlignment.TopCenter);
          }
          else {
              p.arrowFrom.y -= getMarks().getCallout().getDistance();

              if (autoMarkPointer)
              {
                  getMarks().getTailParams().setAlign(TailAlignment.BottomCenter);
              }

              //getMarks().getTailParams().setAlign(TailAlignment.BottomCenter);
          }

          p.leftTop.x += DifW;
          p.leftTop.y -= (DifH < 0) ? DifH - p.height : DifH;
          p.arrowTo.x += DifW;
          p.arrowTo.y -= DifH;
          p.arrowFrom.x += DifW;
          p.arrowFrom.y -= 1;
      }
      else
      {
        getMarks().getArrow().setVisible(false);
        p.leftTop.x +=DifW;

        int barTop = calcYPos(valueIndex);
        int barBottom = getOriginPos(valueIndex);

       if(getMarksLocation()== MarksLocation.Start)
       {
              if ((getMarks().getAngle() == 90) || (getMarks().getAngle() == 270)) 
                p.leftTop.y -= (barTop-barBottom) + 10 +  Utils.round(getMarks().getFont().getSize() * 0.8);
              else
                p.leftTop.y -= (barTop-barBottom + Utils.round(getMarks().getFont().getSize() / 2));
    	}
       else if(getMarksLocation()== MarksLocation.Center)
    	{
   
              if ((getMarks().getAngle() == 90) || (getMarks().getAngle()== 270)) 
                p.leftTop.y = barTop - ((barTop - barBottom) / 2) - (getMarks().textWidth(valueIndex) / 2);
              else
                p.leftTop.y = barTop - ((barTop - barBottom) / 2) - (getMarks().getFont().getSize() / 2);
    	  }
       else if(getMarksLocation()== MarksLocation.End)
    		   {
              if ((getMarks().getAngle() == 90) || (getMarks().getAngle() == 270)) 
                p.leftTop.y += getMarks().textWidth(valueIndex);
              else
                p.leftTop.y += Math.round(getMarks().getFont().getSize() * 2.2);
    		   }
          }
   
        if (getAutoMarkPosition()) {
            getMarks().antiOverlap(firstVisible, valueIndex, p, getYValues().getValue(valueIndex) < 0);
        }

        super.drawMark(valueIndex, s, p);

    }

    @Override
    public boolean drawMarksSeries(Series s, boolean activeRegion) {

        autoMarkPointer = this.getMarks().getTailParams().getAlign()== TailAlignment.Auto;

        super.drawMarksSeries(s, activeRegion);

        if (autoMarkPointer)
        {
            autoMarkPointer = false;
            this.getMarks().getTailParams().setAlign(TailAlignment.Auto);
        //    this.getMarks().setTailParams(tmpTailItems);
        }
        return activeRegion;
    }

    /**
     * The corresponding screen pixels coordinate of the leftmost
     * horizontal bar edge.
     *
     * @param valueIndex int
     * @return int
     */
    public int getOriginPos(int valueIndex) {
        return internalGetOriginPos(valueIndex, getVertAxis().iEndPos);
    }

    /**
     * The Maximum Value of the Series X Values List.
     *
     * @return double
     */
    public double getMaxXValue() {
        if (iMultiBar == MultiBars.SELFSTACK) {
            return getMinXValue();
        } else {
            return (iMultiBar == MultiBars.SIDEALL) ?
                    iPreviousCount + getCount() - 1 :
                    super.getMaxXValue();
        }
    }

    /**
     * The Minimum Value of the Series X Values List.
     *
     * @return double
     */
    public double getMinXValue() {

        if (iMultiBar == MultiBars.SELFSTACK) {
            return getChart().getSeriesIndexOf(this);
        } else {
            return super.getMinXValue();
        }
    }

    /**
     * The Maximum Value of the Series Y Values List.
     *
     * @return double
     */
    public double getMaxYValue() {
        return maxMandatoryValue(super.getMaxYValue());
    }

    /**
     * The Minimum Value of the Series Y Values List.
     *
     * @return double
     */
    public double getMinYValue() {
        return minMandatoryValue(super.getMinYValue());
    }
}

