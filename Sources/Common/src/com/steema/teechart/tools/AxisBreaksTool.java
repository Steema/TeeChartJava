package com.steema.teechart.tools;

import com.steema.teechart.Chart;
import com.steema.teechart.DateTime;
import com.steema.teechart.IBaseChart;
import com.steema.teechart.PointDouble;
import com.steema.teechart.Rectangle;
import com.steema.teechart.axis.Axis;
import com.steema.teechart.axis.Axis;
import com.steema.teechart.axis.AxisCalcResolver;
import com.steema.teechart.axis.AxisLabelResolver;
import com.steema.teechart.axis.AxisLabelValueEventArgs;
import com.steema.teechart.axis.NextAxisLabelValue;
import com.steema.teechart.drawing.ChartBrush;
import com.steema.teechart.drawing.ChartPen;
import com.steema.teechart.drawing.Color;
import com.steema.teechart.drawing.Point;
import com.steema.teechart.events.ChartDrawEvent;
import com.steema.teechart.events.ChartEvent;
import com.steema.teechart.events.ScrollModEventArgs;
import com.steema.teechart.misc.Utils;
import com.steema.teechart.styles.ISeries;
import com.steema.teechart.styles.Series;
import com.steema.teechart.tools.AxisBreaksTool;
import com.steema.teechart.languages.Language;
import com.steema.teechart.styles.Series.MarkTextResolver;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * <p>Copyright (c) 2005-2019 by Steema Software SL. All Rights
 * Reserved.</p>
 * 
 */
public class AxisBreaksTool extends ToolAxis {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Point[] tmpPoints = new Point[0];
    private AxisBreaks breaks = new AxisBreaks();
    private int gapSize = 5;
/*
    protected AxisCalcPosValueDelegate oldCalcX;
    protected AxisCalcPosValueDelegate oldCalcY;

    protected AxisCalcPosValueDelegate AxisCalcXDelegate;
    protected AxisCalcPosValueDelegate AxisCalcYDelegate;

    protected AxisCalcPosPointDelegate oldCalcPosPoint;
    protected AxisCalcPosPointDelegate AxisCalcPosPointDelegate;
*/
    public AxisBreaksTool()
    {
      super((Chart)null);
    }

    public AxisBreaksTool(Axis a)
    {
      super(a.getChart());

      breaks.tool = this;
      getBrush().setColor(Color.WHITE);
      getPen().setColor(Color.BLACK);
      setAxis(a);

      //setCalcPosValue();
      setLabelResolver();
    }

    public AxisBreaksTool(Chart c)
    {
      super(c);
      breaks.tool = this;
      getBrush().setColor(Color.WHITE);
      getPen().setColor(Color.BLACK);
    }

    public int getGapSize()
    {
      return gapSize;
    }

    public void setGapSize(int value)
    {
      gapSize = setIntegerProperty(gapSize, value);
    }

    /*
    * Advanced use. Requests the Axis to re-check assigned calculation methods
    */
    public void setCalcPosValue()
    {
        if (getAxis() != null)
        {
           getAxis().setAxisCalcResolver(new AxisCalcResolver(){

              public void AxisLabelValueDelegate(Axis axis, AxisLabelValueEventArgs e) {
                   checkNextLabelValue(axis, e);
              }

              public int AxisCalcPosLabelsEventHandler(Axis axis, int value){
                  //mm pending
                  return 0;
              }

              public int AxisCalcXPosValueDelegate(double value){
                  if (getAxis().getIsDepthAxis())
                  {
                      return getAxis().internalCalcDepthPosValue(value);
                  }
                  else
                  if (getAxis().getLogarithmic())
                  {
                      return getAxis().internalCalcLogPosValue(true, value);
                  }
                  else if (getAxis().iRangezero)
                  {
                      //return getAxis().iCenterPos;
                      return 0;
                      //MM pending
                  }
                  else
                  {
                      return calcXBreaks(value);
                  }
              }

              public int AxisCalcYPosValueDelegate(double value){
                  if (getAxis().getIsDepthAxis())
                  {
                      return getAxis().internalCalcDepthPosValue(value);
                  }
                  else
                  if (getAxis().getLogarithmic())
                  {
                      return getAxis().internalCalcLogPosValue(false, value);
                  }
                  else if (getAxis().iRangezero)
                  {
                      //return getAxis().iCenterPos;
                      return 0;  //mm pending
                  }
                  else
                  {
                      return calcYBreaks(value);
                  }
              }

              public int AxisCalcPosValueDelegate(double value){
                  if (getAxis().getHorizontal())
                     return AxisCalcXPosValueDelegate(value);
                  else
                     return AxisCalcYPosValueDelegate(value);
              }

              public double AxisCalcPosPointDelegate(int value){
                  //CalcPosPoint = new AxisCalcPosPointDelegate(InternalCalcPosPoint);
                  return AdjustCalcPosPoint(value);
                  //return getAxis().internalCalcPosPoint(value);
              }

              //calls to Axis' methods.
              public double oldCalcPosPointDelegate(int value){
                   if (getAxis().getHorizontal())
                     return oldCalcXDelegate(value);
                  else
                     return oldCalcYDelegate(value);
              }

              public int oldCalcXDelegate(double value){
                  return AxisCalcXPosValueDelegate(value);
              }

              public int oldCalcYDelegate(double value){
                  return AxisCalcYPosValueDelegate(value);
              }
          });
        }
    }

    protected int oldCalcX(double value)
    {
        return getAxis().setAxisCalcXPosValueDelegate(value);
    }

    protected int oldCalcY(double value)
    {
        return getAxis().setAxisCalcYPosValueDelegate(value);
    }

    protected double oldCalcPosPoint(int value)
    {
        return getAxis().internalCalcPosPoint(value);
    }

    public double AdjustCalcPosPoint(int pixelVal)
    {
      double scaleVal = oldCalcPosPoint(pixelVal);

      if (breaks.size() > 0)
      {
        double tmpStep = 0;
        int hiddenBreaks = 0;
        //for (int i=0;i<Breaks.each (AxisBreak br in Breaks)
        for (int i=0;i<breaks.size();i++)
        {
          if (breaks.get(i).getEndValue() > getAxis().getMinimum())
          {
            if (scaleVal > breaks.get(i).getStartValue())
            {
              tmpStep = tmpStep + ((breaks.get(i).getEndValue()) - (breaks.get(i).getStartValue()));

              if (((getAxis().getHorizontal()) && (!getAxis().getInverted())) || ((!getAxis().getHorizontal()) && (getAxis().getInverted())))
              {
                if (pixelVal > (getAxis().calcPosValue(breaks.get(i).getStartValue())) + gapSize)
                    scaleVal = oldCalcPosPoint(pixelVal - ((gapSize) * (i + 1 - hiddenBreaks))) + tmpStep;
                else
                  break;
              }
              else
              {
                if (pixelVal < (getAxis().calcPosValue(breaks.get(i).getStartValue())) - gapSize)
                  scaleVal = oldCalcPosPoint(pixelVal + (gapSize * (i + 1 - hiddenBreaks))) - tmpStep;
              }
            }
            else
              break;
          }
          else
            hiddenBreaks++;
        }
      }
      return scaleVal;
    }

    public int breakSizeUpTo(double Value)
    {
      double tmp;
      int result = 0;
      AxisBreak aBreak;

      if (Value > breaks.tool.getAxis().getMaximum())
          return 0;

      for (int t = 0; t < breaks.size(); t++)
      {
        aBreak = breaks.get(t);
        if ((aBreak.getEnabled()) && ((aBreak.getEndValue() - aBreak.getStartValue()) != 0))
        {
          if (aBreak.getStartValue() < Value)
          {
            if (breaks.tool.getAxis().getHorizontal())
            {
              if (aBreak.getStartValue() > breaks.tool.getAxis().getMinimum())
              {
                if (aBreak.getEndValue() <= Value)
                  tmp = (aBreak.getEndValue() - aBreak.getStartValue());
                else
                  tmp = aBreak.getStartValue() - Value;
              }
              else
              {
                if (aBreak.getStartValue() + (aBreak.getEndValue() - aBreak.getStartValue()) > getAxis().getMinimum())
                  if (aBreak.getStartValue() + (aBreak.getEndValue() - aBreak.getStartValue()) <= Value)
                    tmp = aBreak.getStartValue() + (aBreak.getEndValue() - aBreak.getStartValue()) - getAxis().getMinimum();
                  else
                    tmp = aBreak.getStartValue() - Value;
                else
                  tmp = 0;
              }
            }
            else
            {
              if (aBreak.getStartValue() > getAxis().getMinimum())
                if ((aBreak.getStartValue() + (aBreak.getEndValue() - aBreak.getStartValue()) <= Value))
                  tmp = (aBreak.getEndValue() - aBreak.getStartValue());
                else
                  tmp = aBreak.getStartValue() - Value;
              else
                if (aBreak.getStartValue() + (aBreak.getEndValue() - aBreak.getStartValue()) > getAxis().getMinimum())
                  if (aBreak.getStartValue() + (aBreak.getEndValue() - aBreak.getStartValue()) <= Value)
                    tmp = aBreak.getStartValue() + (aBreak.getEndValue() - aBreak.getStartValue()) - getAxis().getMinimum();
                  else
                    tmp = aBreak.getStartValue() - Value;
                else
                  tmp = 0;
            }

            if (Math.abs(tmp) > 0)
              if (getAxis().getHorizontal())
              {
                if (getAxis().getInverted())
                  result -= getAxis().calcSizeValue(Math.abs(tmp)) - breaks.tool.gapSize;
                else
                  result += getAxis().calcSizeValue(Math.abs(tmp)) - breaks.tool.gapSize;
              }
              else
              {
                if (getAxis().getInverted())
                  result -= getAxis().calcSizeValue(Math.abs(tmp)) - breaks.tool.gapSize;
                else
                  result += getAxis().calcSizeValue(Math.abs(tmp)) - breaks.tool.gapSize;
              }
          }
        }
      }
      return result;
    }

    public NextAxisLabelValue axisCalcMaxLabel(Axis axis, int labelIndex, NextAxisLabelValue nextLabel)
    {
      if (getAxis() == axis)
      {
        double tmpMin = axis.getMinimum();
        double tmpMax = axis.getMaximum();
        nextLabel.setLabelValue(totalLength(tmpMin,tmpMax));
      }
      return nextLabel;
    }

     public void checkNextLabelValue(Axis axis, AxisLabelValueEventArgs e)
    {
      AxisBreak aBreak;

      if (e.getTmpValue() > breaks.tool.getAxis().getMaximum())
        return;

      for (int t = 0; t < breaks.size(); t++)
      {
        aBreak = breaks.get(t);
        if (e.getTmpValue() > aBreak.getEndValue())
          return;

        if ((aBreak.getEnabled()) && ((aBreak.getEndValue() - aBreak.getStartValue()) != 0))
        {
          if ((aBreak.getStartValue() < e.getTmpValue()) && (aBreak.getEndValue() > e.getTmpValue())
            && (aBreak.getStartValue() > breaks.tool.getAxis().getMinimum()))
          {
            e.setTmpValue(aBreak.getStartValue());
          }
        }
      }
    }

    private int calcXBreaks(double value)
    {
      boolean oldUseMaxPixelPos = getAxis().getUseMaxPixelPos();

      getAxis().setUseMaxPixelPos(false);
      int result = oldCalcX(value);
      getAxis().setUseMaxPixelPos(oldUseMaxPixelPos);

      if (this.getBreaks().size() > 0)
        if (getAxis().getHorizontal())
          result -= breakSizeUpTo(value);

      if (result > getAxis().MAXPIXELPOS)
        result = getAxis().MAXPIXELPOS;
      else
        if (result < -getAxis().MAXPIXELPOS)
          result = -getAxis().MAXPIXELPOS;

      return result;
    }

    private int calcYBreaks(double Value)
    {
      boolean oldUseMaxPixelPos = getAxis().getUseMaxPixelPos();

      getAxis().setUseMaxPixelPos(false);
      int result = oldCalcY(Value);
      getAxis().setUseMaxPixelPos(oldUseMaxPixelPos);

      if (this.getBreaks().size() > 0)
        if (!getAxis().getHorizontal())
          result += breakSizeUpTo(Value);

      if (result > getAxis().MAXPIXELPOS)
        result = getAxis().MAXPIXELPOS;
      else
        if (result < -getAxis().MAXPIXELPOS)
          result = -getAxis().MAXPIXELPOS;

      return result;
    }

    // Drawing methods of Break
    private void drawRectangle(Rectangle tmpR)
    {
      Rectangle tmpRect, tmpCR;
      tmpCR = chart.getChartRect();

      tmpRect = Rectangle.fromLTRB(tmpCR.getLeft() + 1, tmpCR.getTop() + 1, tmpCR.getRight() - 1, tmpCR.getBottom() - 1);

      chart.getGraphics3D().clipRectangle(chart.getGraphics3D().rectFromRectZ(tmpRect, 0)); //tmpZ));
      chart.getGraphics3D().setPen(getPen());

      if (chart.getAspect().getView3D())
        chart.getGraphics3D().rectangle(tmpR, 0);
      else
        chart.getGraphics3D().rectangle(tmpR);

      chart.getGraphics3D().unClip();
    }

    private void DrawPolygon()
    {
      Rectangle tmpRect, tmpR;
      tmpR = chart.getChartRect();

      tmpRect = Rectangle.fromLTRB(tmpR.getLeft() + 1,tmpR.getTop() + 1, tmpR.getRight() - 1, tmpR.getBottom() - 1);
      //tmpRect = new Rectangle(tmpR.Left + 1,tmpR.Top + 1,
      //    tmpR.Width-2,
      //    tmpR.Height - 2);

      chart.getGraphics3D().clipRectangle(chart.getGraphics3D().rectFromRectZ(tmpRect, 0)); //tmpZ));
      chart.getGraphics3D().setPen(getPen());

      if (chart.getAspect().getView3D())
        chart.getGraphics3D().polygon(tmpPoints); //,tmpZ)
      else
        chart.getGraphics3D().polygon(tmpPoints);

      chart.getGraphics3D().unClip();
    }

    private Point[] incrementArray(Point[] value)
    {
      int count = value.length + 1;
      Point[] newValue = new Point[count];
      System.arraycopy(value, 0, newValue, 0, value.length);
      return newValue;
    }

    private void doDrawZigZag(int tmp, boolean SmallZigZag)
    {
      int tmpii, tmpi;
      int i2, c;
      Rectangle tmpR;
      int ZigZagCount;
      Point p, p1;
      boolean ldown;
      int tmpGapSize;

      tmpR = chart.getChartRect();
      tmpii = 0;
      tmpi = 0;

      if (!getAxis().getHorizontal())
      {
        if (getAxis().getInverted())
          tmpGapSize = gapSize;
        else
          tmpGapSize = -gapSize;

        chart.getGraphics3D().horizontalLine(getAxis().getPosition() - 5, getAxis().getPosition() + 5, tmp);
        chart.getGraphics3D().horizontalLine(getAxis().getPosition() - 5, getAxis().getPosition() + 5, tmp + tmpGapSize);

        p = chart.getGraphics3D().calculate3DPosition(tmpR.getLeft() - 5, tmp + tmpGapSize, 0);
        p1 = chart.getGraphics3D().calculate3DPosition(tmpR.getRight(), tmp + tmpGapSize, 0);

        if (!getAxis().getInverted())
          tmp = tmp - gapSize;

        i2 = tmp;
        c = 0;
        ldown = false;

        if (SmallZigZag)
          ZigZagCount = 5;
        else
          ZigZagCount = 15;

        for (int i = p.x; i <= p1.x; i++)
        {
          if ((i - p.x) > 10)
            if (ldown == false)
            {
              if (c < ZigZagCount)
              {
                ldown = false;
                ++c;
              }
              else
                ldown = true;
            }
            else
            {
              if (c > 0)
              {
                ldown = true;
                --c;
              }
              else
                ldown = false;
            }

          if ((c == ZigZagCount) || (c == 0) || (i == p1.x) || (i == p.x))
          {
            tmpPoints = incrementArray(tmpPoints);
            tmpPoints[tmpPoints.length - 1] = new Point(i,i2 + c);
          }
        }

        tmpPoints = incrementArray(tmpPoints);
        tmpPoints[tmpPoints.length - 1] = new Point(p1.x,i2 + c - tmpii + gapSize);

        ldown = !ldown;
        p.y = p.y + gapSize;

        for (int i = p1.x; i >= p.x; i--)
        {
          if (((p1.x - i) > 0) && ((i - p.x) > 9))
            if (ldown == false)
            {
              if (c < ZigZagCount)
              {
                ldown = false;
                ++c;
              }
              else
                ldown = true;
            }
            else
            {
              if (c > 0)
              {
                ldown = true;
                --c;
              }
              else
                ldown = false;
            }

          if ((c == ZigZagCount) || (c == 0) || (i == p.x) || (i == p1.x))
          {
            tmpPoints = incrementArray(tmpPoints);
            tmpPoints[tmpPoints.length - 1] = new Point(i, i2 + c + gapSize);
          }
        }

        chart.getGraphics3D().setBrush(bBrush);

        DrawPolygon();
        tmpPoints = new Point[0];
      }
      else   // horizontal
      {
        if (getAxis().getInverted())
          tmpGapSize = -gapSize;
        else
          tmpGapSize = gapSize;

        chart.getGraphics3D().verticalLine(tmp, getAxis().getPosition() - 5, getAxis().getPosition() + 5);
        chart.getGraphics3D().verticalLine(tmp + tmpGapSize, getAxis().getPosition() - 5, getAxis().getPosition() + 5);

        p = chart.getGraphics3D().calculate3DPosition(tmp - tmpGapSize, tmpR.getTop(), 0);
        p1 = chart.getGraphics3D().calculate3DPosition(tmp, tmpR.getBottom() + 5, 0);

        if (getAxis().getInverted())
          tmp = tmp - gapSize;

        i2 = tmp;
        c = 0;
        ldown = false;

        if (SmallZigZag)
          ZigZagCount = 5;
        else
          ZigZagCount = 15;

        for (int i = p1.y; i >= p.y; i--)
        {
          if ((i + 10) < p1.y)
            if (ldown == false)
            {
              if (c < ZigZagCount)
              {
                ldown = false;
                ++c;
              }
              else
                ldown = true;
            }
            else
            {
              if (c > 0)
              {
                ldown = true;
                --c;
              }
              else
                ldown = false;
            }

          if ((c == ZigZagCount) || (c == 0) || (i == p1.y) || (i == p.y))
          {
            tmpPoints = incrementArray(tmpPoints);
            tmpPoints[tmpPoints.length - 1] = new Point(i2 + c,i);
          }

          tmpi = i;
        }

        tmpPoints = incrementArray(tmpPoints);
        tmpPoints[tmpPoints.length - 1] = new Point(i2 + c - tmpii + gapSize,tmpi);

        ldown = !ldown;
        p.x = p.x + gapSize;

        for (int i = p.y; i <= p1.y; i++)
        {
          if (((p1.y - i) > 0) && ((p1.y - i) > 10))
            if (ldown == false)
            {
              if (c < ZigZagCount)
              {
                ldown = false;
                ++c;
              }
              else
                ldown = true;
            }
            else
            {
              if (c > 0)
              {
                ldown = true;
                --c;
              }
              else
                ldown = false;
            }

          if ((c == ZigZagCount) || (c == 0) || (i == p.getY()) || (i == p1.getY()))
          {
            tmpPoints = incrementArray(tmpPoints);
            tmpPoints[tmpPoints.length - 1] = new Point(i2 + c + gapSize,i);
          }
        }

        chart.getGraphics3D().setBrush(bBrush);

        DrawPolygon();
        tmpPoints = new Point[0];
      }
    }

        /// <summary>
    /// List of breaks.
    /// </summary>
//#if DESIGNATTRIBUTES
//[DefaultValue(true), Description("List of breaks.")]
//#endif
    public AxisBreaks getBreaks()
    {  return breaks; }

    public void setBreaks(AxisBreaks value)
    { breaks = value; } // no need to repaint

    private void doDrawLine(int tmp)
    {
      Point p, p1;
      int tmpGapSize;
      Rectangle tmpR;
      tmpR = chart.getChartRect();

      if (!getAxis().getHorizontal())
      {
        if (getAxis().getInverted())
          tmpGapSize = -gapSize;
        else
          tmpGapSize = gapSize;

        chart.getGraphics3D().horizontalLine(getAxis().getPosition() - 5, getAxis().getPosition() + 5, tmp);
        chart.getGraphics3D().horizontalLine(getAxis().getPosition() - 5, getAxis().getPosition() + 5, tmp - tmpGapSize);

        p = chart.getGraphics3D().calculate3DPosition(getAxis().getPosition() - 5, tmp - tmpGapSize, 0);
        p1 = chart.getGraphics3D().calculate3DPosition(tmpR.getRight(), tmp - tmpGapSize, 0);

        if (getAxis().getInverted())
          tmp = tmp + gapSize;

        chart.getGraphics3D().setBrush(bBrush);

        Rectangle r = Rectangle.fromLTRB(p.x - getPen().getWidth(), tmp - gapSize, p1.x + getPen().getWidth(), tmp);
        drawRectangle(r);
      }
      else   // Horizontal
      {
        if (getAxis().getInverted())
          tmpGapSize = -gapSize;
        else
          tmpGapSize = gapSize;

        if (((tmp) < getAxis().iEndPos) && ((tmp) > getAxis().iStartPos))
          chart.getGraphics3D().verticalLine(tmp, getAxis().getPosition() - 5, getAxis().getPosition() + 5);
        if (((tmp + tmpGapSize) < getAxis().iEndPos) && ((tmp + tmpGapSize) > getAxis().iStartPos))
          chart.getGraphics3D().verticalLine(tmp + tmpGapSize-1, getAxis().getPosition() - 5, getAxis().getPosition() + 5);

        p = chart.getGraphics3D().calculate3DPosition(0, tmpR.getTop(), 0);
        p1 = chart.getGraphics3D().calculate3DPosition(0, tmpR.getBottom() + 5, 0);

        if (getAxis().getInverted())
          tmp = tmp - gapSize;

        chart.getGraphics3D().setBrush(bBrush);

        Rectangle r = Rectangle.fromLTRB(tmp, p.y - getPen().getWidth(), tmp + gapSize, p1.y + getPen().getWidth());
        drawRectangle(r);
      }
    }

    private void doDrawNone()
    {
        //just didactic
    }

    public void chartEvent(ChartDrawEvent ce)
    {
      int tmp;

      //MM todo  if (e is ChartDrawEvent)
      {
        for (int t = 0; t < getBreaks().size(); t++)
        {
          if ((getBreaks().get(t).getEnabled()) && ((getBreaks().get(t).getEndValue() - getBreaks().get(t).getStartValue()) != 0))
            if ((breaks.get(t).getStartValue() >= getAxis().getMinimum()) && (breaks.get(t).getStartValue() <= getAxis().getMaximum()))
            {
              tmp = getAxis().calcPosValue(breaks.get(t).getStartValue());
              chart.getGraphics3D().setPen(getPen());

              if (breaks.get(t).getStyle() == AxisBreakStyle.SMALLZIGZAG)
                    doDrawZigZag(tmp, true);
              else if (breaks.get(t).getStyle() == AxisBreakStyle.ZIGZAG)
                    doDrawZigZag(tmp, false);
              else if (breaks.get(t).getStyle() == AxisBreakStyle.LINE)
                    doDrawLine(tmp);
              else doDrawNone(); //just didactic
            }
          }
        }

      super.chartEvent(ce);
    }

    private void setLabelResolver()
    {
        this.chart.getParent().setAxisLabelResolver(new AxisLabelResolver() {

            public String getLabel(Axis axis, ISeries s, int valueIndex, String labelText) {
                return labelText;
            }

            public NextAxisLabelValue getNextLabel(Axis axis, int labelIndex, NextAxisLabelValue nextLabel) {
                return axisCalcMaxLabel(axis,labelIndex,nextLabel);
            }

            public void scrollModHandler(Axis a, ScrollModEventArgs e){
                   axisBreakDoAllowScroll(a,e);
            }
        });
    }

    //protected  getNextAxisLabelEventHandler OnCalcMaxLabelDelegate;

    /*protected void OnDisposing()
    {
      if (chart != null)
      {
        if (getAxis() != null)
        {
          if (getAxis().calcMaxLabel != null)
            getAxis().CalcMaxLabel -= new GetNextAxisLabelEventHandler(AxisCalcMaxLabel);
          if ((Axis != null) && (getAxis().DrawAxis != null))
            getAxis().DrawAxis -= new DrawAxisEventDelegate(AxisDrawLine);
          if ((Axis != null) && (getAxis().CheckNextLabelValue != null))
            getAxis().CheckNextLabelValue -= new AxisLabelValueDelegate(CheckNextLabelValue);

          if (getAxis().CalcXPosValue != null)
            if (getAxis().CalcXPosValue.Method == AxisCalcXDelegate.Method)
            {
              getAxis().CalcXPosValue -= AxisCalcXDelegate;
            }

          if (getAxis().CalcYPosValue != null)
            if (getAxis().CalcYPosValue.Method == AxisCalcYDelegate.Method)
            {
              getAxis().CalcYPosValue -= AxisCalcYDelegate;
            }

          getAxis().SetCalcPosValue();
          getAxis().IGapSize = 0;
        }

        if (chart.Tools.Count > 0)
          for (int t=0;t<chart.getTools().size();t++)
          {
            if (chart.getTools().getTool(t).getClass() == AxisBreaksTool)
              break;

            chart.ToolCheckHidden = null;
          }
        else
          chart.ToolCheckHidden = null;
      }

      super.OnDisposing();
    }*/

    double scaleAdj = 0.0000001;

    /// <summary>
    /// Default 0.0000001. This variable avoids jump/flicker when scrolling across Break boundaries
    /// by moving scroll to 'just' outside of Break zone when traversing Axis Min or Max.
    /// Adjust if your Axis scale requires different sensitivity.
    /// </summary>
    public double getScaleAdjustment()
    { return scaleAdj; }

    public void setScaleAdjustment(double value) 
    { scaleAdj = setDoubleProperty(scaleAdj , value); }

    public void axisBreakDoAllowScroll(Axis a, ScrollModEventArgs e)
    {
      AxisBreak aBreak;
      int delta;

      if (e.Min > a.getMinimum())
        delta = -1;
      else
        delta = +1;

      if (getAxis() == a)
      {
        for (int t = 0; t < breaks.size(); t++)
        {
          aBreak = breaks.get(t);

          if (delta < 0)
          {
            if ((aBreak.getEnabled()) && ((e.Min > aBreak.getStartValue()) && (e.Min < aBreak.getEndValue())))
            {
              e.Min = aBreak.getEndValue() + getScaleAdjustment();
            }

            if ((aBreak.getEnabled()) && ((e.Max > aBreak.getStartValue()) && (e.Max < aBreak.getEndValue())))
            {
              e.Max = aBreak.getEndValue() + getScaleAdjustment();
            }
          }
          else
          {
            if ((aBreak.getEnabled()) && ((e.Min < aBreak.getEndValue()) && (e.Min > aBreak.getStartValue())))
            {
              e.Min = aBreak.getStartValue() - getScaleAdjustment();
            }

            if ((aBreak.getEnabled()) && ((e.Max < aBreak.getEndValue()) && (e.Max > aBreak.getStartValue())))
            {
              e.Max = aBreak.getStartValue() - getScaleAdjustment();
            }
          }
        }
      }
    }

    public void setAxis(Axis value)
    {
      chart = value.chart;

      if (getAxis() != value)
        if (getAxis() != null)
        {
          breaks.clear();
          getAxis().setHorizontal(getAxis().getHorizontal()); // force reset "calc" methods
          getAxis().setCalcPosValue();
          //mm todo
          /*getAxis().calcMaxLabel -= new getNextAxisLabelEventHandler(AxisCalcMaxLabel);
          getAxis().CheckNextLabelValue -= new axisLabelValueDelegate(CheckNextLabelValue);
          getAxis().drawAxis -= new drawAxisEventDelegate(AxisDrawLine);*/
        }

      super.setAxis(value);

      if (getAxis() != null)
      {
        //mm todo
        //if ((getAxis().calcMaxLabel == null)
        //    || (getAxis().calcMaxLabel.Method != (new GetNextAxisLabelEventHandler(AxisCalcMaxLabel)).Method))
        //  getAxis().calcMaxLabel += new GetNextAxisLabelEventHandler(AxisCalcMaxLabel);
        //if ((getAxis().drawAxis == null)
        //    || (getAxis().drawgetAxis().Method != (new DrawAxisEventDelegate(AxisDrawLine)).Method))
        //  getAxis().drawAxis += new DrawAxisEventDelegate(AxisDrawLine);
        //if ((getAxis().checkNextLabelValue == null)
        //    || (getAxis().checkNextLabelValue.Method != (new AxisLabelValueDelegate(CheckNextLabelValue)).Method))
        //  getAxis().checkNextLabelValue += new AxisLabelValueDelegate(CheckNextLabelValue);

        setCalcPosValue();

        for (int i = 0; i < chart.getSeries().size(); i++)
        {    
          final Series tmpSeries=chart.getSeries().getSeries(i);
          chart.getSeries().getSeries(i).setMarkTextResolver(new MarkTextResolver(){

                public String getMarkText(int valueIndex, String markText) {
                    return getAMarkText(tmpSeries, valueIndex,markText);
                }
          });
        }
      }
    }

    public void checkDrawHidden(Series series, int tmpIndex)
    {
      for (int i = 0; i < chart.getTools().size(); i++)
        if (chart.getTools().getTool(i).getClass() == AxisBreaksTool.class)
          if (((chart.getTools().getTool(i).getActive()))
             && (((AxisBreaksTool) (chart.getTools().getTool(i))).breaks.size() > 0))
          {
            for (int tt = 0; tt < ((AxisBreaksTool) (chart.getTools().getTool(i))).breaks.size(); tt++)
            {
              double tmpStartValue = ((AxisBreaksTool) (chart.getTools().getTool(i))).breaks.get(tt).getStartValue();
              double tmpEndValue = ((AxisBreaksTool) (chart.getTools().getTool(i))).breaks.get(tt).getEndValue();

              if ((((AxisBreaksTool) (chart.getTools().getTool(i))).getAxis().getHorizontal()))
              {
                if ((series.getXValues().value[tmpIndex] > tmpStartValue)
                   &&
                   (series.getXValues().value[tmpIndex] < tmpEndValue))
                {
                  PointDouble startEndVals = new PointDouble(tmpStartValue,tmpEndValue);
                  series.drawHiddenValue(tmpIndex, startEndVals,
                    ((AxisBreaksTool)(chart.getTools().getTool(i))).gapSize,
                    ((AxisBreaksTool)(chart.getTools().getTool(i))).getAxis());
                  tmpStartValue=startEndVals.x;
                  tmpEndValue=startEndVals.y;
                  return;
                }
              }
              else
              {
                if ((series.getYValues().value[tmpIndex] > tmpStartValue)
                   &&
                   (series.getYValues().value[tmpIndex] < tmpEndValue))
                {
                  PointDouble startEndVals = new PointDouble(tmpStartValue,tmpEndValue);
                  series.drawHiddenValue(tmpIndex, startEndVals,
                    ((AxisBreaksTool)(chart.getTools().getTool(i))).gapSize,
                    ((AxisBreaksTool)(chart.getTools().getTool(i))).getAxis());
                  tmpStartValue=startEndVals.x;
                  tmpEndValue=startEndVals.y;
                  return;
                }
              }
            }
          }
      series.drawValue(tmpIndex);
    }

    // Removes axis labels that are "inside" breaks
    private String chartGetAxisLabel(Axis axis, ISeries s, int valueIndex, String labelText) throws ParseException
    {
      double tmp;

      if (axis == getAxis())
        for (int t = 0; t < breaks.size(); t++)
        {
          if ((breaks.get(t).getEnabled())
              && ((breaks.get(t).getEndValue() - breaks.get(t).getStartValue()) != 0))
          {
            if (axis.isDateTime())
            {
                SimpleDateFormat parse = new SimpleDateFormat(getAxis().getLabels().getDateTimeFormat());
                try {
                  Date dt= parse.parse(labelText);
                  tmp = dt.getTime();
                 } catch (ParseException ex) {
                     Logger.getLogger(AxisBreaksTool.class.getName()).log(Level.SEVERE, null, ex);
                     tmp = 0;
                 }
            }
            else
              tmp = Utils.isNullOrEmpty(labelText) ?
                      Double.NEGATIVE_INFINITY : Double.parseDouble(labelText);

            if ((tmp > breaks.get(t).getStartValue())
                    && (tmp < (breaks.get(t).getStartValue()
                        + (breaks.get(t).getEndValue()
                        - breaks.get(t).getStartValue()))))
            {
              return "";
            }

            if (tmp > getAxis().getMaximum())
            {
              return "";
            }
          }
        }

      return labelText;
    }

    public void setChart(IBaseChart value)
    {
      super.setChart(value);

      if (chart != null && chart.getParent() != null)
      {
        chart.getParent().setAxisLabelResolver(new AxisLabelResolver(){

                public String getLabel(Axis axis, ISeries s, int valueIndex, String labelText) {
                    try {
                        return chartGetAxisLabel(axis, s, valueIndex, labelText);
                    } catch (ParseException ex) {
                        Logger.getLogger(AxisBreaksTool.class.getName()).log(Level.SEVERE, null, ex);
                        return labelText;
                    }
                }

                public NextAxisLabelValue getNextLabel(Axis axis, int labelIndex, NextAxisLabelValue nextLabel) {
                    return nextLabel;
                }

                public void scrollModHandler(Axis a, ScrollModEventArgs e){
                   axisBreakDoAllowScroll(a,e);
                }
            });
        
        if (chart.getToolCheckHidden() == null)
          chart.setToolCheckHidden(this);
      }
    }

    // Returns sum of all breaks "Length" that are between AFrom and ATo range
    private double totalLength(double aFrom, double aTo)
    {
      double result = 0;
      int gapCounter=0;

      if (breaks.size() > 0)
      {
          for (int t = 0; t < breaks.size(); t++)
          {
              //if (Breaks[t].EndValue > this.getAxis().Minimum)
              //{

              //getAxis().IGapSize = breaks.tool.gapSize * (t + 1);
              if ((breaks.get(t).getStartValue() > aFrom) && (breaks.get(t).getEndValue() < aTo))
              {
                  gapCounter++;
                  result += (breaks.get(t).getEndValue() - breaks.get(t).getStartValue());
              }
              //}

          }
          getAxis().iGapSize = breaks.tool.gapSize * gapCounter;
      }
      else
          getAxis().iGapSize = 0;


      return result;
    }

    private void AxisDrawLine()
    {
      int tmpStart;
      int tmpEnd;

      tmpStart = getAxis().iStartPos;
      tmpEnd = getAxis().iEndPos;

      if (getActive())
      {
        if (breaks.size() == 0)
          getAxis().drawLineSegment(tmpStart, tmpEnd);
        else
        {
          for (int t = 0; t < breaks.size(); t++)
          {
            if (breaks.get(t).getEnabled() && (breaks.get(t).getStartValue() <= getAxis().getMaximum()) &&
                ((breaks.get(t).getEndValue() - breaks.get(t).getStartValue()) != 0))
            {
              tmpEnd = getAxis().calcPosValue(breaks.get(t).getStartValue());
              if ((tmpEnd <= getAxis().iEndPos) && (tmpEnd >= getAxis().iStartPos))
              {
                if (getAxis().getHorizontal())
                  if (getAxis().getInverted())
                  {
                    getAxis().drawLineSegment(tmpStart, tmpEnd - gapSize);
                    //                                       getAxis().IEndPos -= gapSize;
                  }
                  else
                  {
                    getAxis().drawLineSegment(tmpStart, tmpEnd);
                  }
                else
                  if (getAxis().getInverted())
                    getAxis().drawLineSegment(tmpStart, tmpEnd);
                  else
                  {
                    getAxis().drawLineSegment(tmpStart, tmpEnd - gapSize);
                    //                                       getAxis().IEndPos -= gapSize;
                  }
                tmpStart = tmpEnd;
              }
            }
          }

          if (tmpStart < getAxis().iEndPos)
            if (getAxis().getHorizontal())
              if (getAxis().getInverted())
                getAxis().drawLineSegment(tmpStart, getAxis().iEndPos);
              else
                getAxis().drawLineSegment(tmpStart + gapSize, getAxis().iEndPos);
            else
              if (getAxis().getInverted())
                getAxis().drawLineSegment(tmpStart + gapSize, getAxis().iEndPos);
              else
                getAxis().drawLineSegment(tmpStart, getAxis().iEndPos);
        }
      }
      else
        getAxis().drawLineSegment(tmpStart, tmpEnd);
    }

    private String getAMarkText(Series series, int valueIndex, String markText)
    {
      for (int t = 0; t < breaks.size(); t++)
      {
        if ((series.getYValues().value[valueIndex] >= breaks.get(t).getStartValue()) &&
               (series.getYValues().value[valueIndex] <= breaks.get(t).getEndValue()))
          return "";
      }
      return markText;
    }

    /// <summary>
    /// Gets descriptive text.
    /// </summary>
//#if DESIGNATTRIBUTES
//[Description("Gets descriptive text.")]
//#endif
    public String getDescription() {
        return Language.getString("AxisBreaksTool");
    }
    /// <summary>
    /// Gets detailed descriptive text.
    /// </summary>
//#if DESIGNATTRIBUTES
//[Description("Gets detailed descriptive text.")]
//#endif
    public String getSummary() {
        return Language.getString("AxisBreaksSummary");
    }

    /// <summary>
    /// Element Brush characteristics.
    /// </summary>
//#if DESIGNATTRIBUTES
//[
//#if !SILVERLIGHT
//DesignerSerializationVisibility(DesignerSerializationVisibility.Content),
//#endif
//Description("Element Brush characteristics."),
//Category("Appearance")
//]
//#endif
      public ChartBrush getBrush()
      {
        if (bBrush == null) bBrush = new ChartBrush(chart);
        return bBrush;
      }

      public void setBrush(ChartBrush value)
      {
        bBrush = value;
      }

    /// <summary>
    /// Specifies the pen used to draw the gap outline.
    /// </summary>
//#if DESIGNATTRIBUTES
//[System.ComponentModel.Description("Specifies the pen used to draw the gap outline.")]
//[
//#if !SILVERLIGHT
//System.ComponentModel.DesignerSerializationVisibility(System.ComponentModel.DesignerSerializationVisibility.Content),
//#endif
//System.ComponentModel.Category("Appearance")]
//#endif
    public ChartPen getPen()
    {
        if (pPen == null) pPen = new ChartPen(chart, Color.BLACK);
        return pPen;
    }

    public void setPen(ChartPen value)
    {
        pPen = value;
        invalidate();
    }
}
