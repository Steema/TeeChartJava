package com.steema.teechart.styles;

import com.steema.teechart.IBaseChart;
import com.steema.teechart.drawing.ChartPen;
import com.steema.teechart.drawing.Color;

/**
 *
 * <p>Title: BaseLine class</p>
 *
 * <p>Description: Abstract Series class inherited by a number of TeeChart
 * series styles.</p>
 *
 * <p>Copyright (c) 2005-2019 by Steema Software SL. All Rights
 * Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 *
 */
public abstract class BaseLine extends Series {

    protected ChartPen linePen;
    protected TreatNullsStyle treatnulls = TreatNullsStyle.DONOTPAINT;
    protected boolean treatNans = false;

    protected BaseLine() {
        this((IBaseChart)null);
    }

    protected BaseLine(IBaseChart c) {
        super(c);
    }

    public void assign(Series source) {
        if (source instanceof BaseLine) {
            BaseLine tmp = (BaseLine)source;
            if (tmp.linePen != null) {
                this.getLinePen().assign(tmp.linePen);
            }
            treatnulls = tmp.getTreatNulls();
            setTreatNaNAsNull(tmp.getTreatNaNAsNull());
        }
        super.assign(source);
    }

    public void setChart(IBaseChart c) {
        super.setChart(c);
        getLinePen().setChart(chart);
    }
    
    protected double calcMinMaxValue(boolean isMin) {
      boolean tmpfirst = true;
      double result = 0.0;
      double tmpvalue = 0.0;
      
      for (int t = 0; t < getCount(); t++)
      {
        if ((!isNull(t)) || (getTreatNulls() == TreatNullsStyle.IGNORE)) {
          tmpvalue =getMandatory().value[t];
          if (tmpfirst) {
            result = tmpvalue;
            tmpfirst = false;
          }
          else {
            if (isMin) {
                result = Math.min(result, tmpvalue);
            }
            else {
                result = Math.max(result, tmpvalue);
            }
          }
        }
      }
      return result;
    }

    /**
     * Determines pen to draw the line connecting all points.<br>
     *
     * @return ChartPen
     */
    public ChartPen getLinePen() {
        if (linePen == null) {
            linePen = new ChartPen(chart, Color.EMPTY);
        }
        return linePen;
    }
    
    /**
     * Defines how null points are treated.<br>
     *
     * @return TreatNullsStyle
     */
    public TreatNullsStyle getTreatNulls()  {
        return treatnulls;
    }
    
    public void setTreatNulls(TreatNullsStyle value) {
        if  (treatnulls != value) {
            treatnulls = value;
            repaint();
        }
    }

    public void add(double[] xValues, double[] yValues, boolean append)
    {
      super.add(xValues, yValues, append); //B113
      if (treatNans)
      {
        for (int i = 0; i < getYValues().count; i++)
        {
          if (Double.isNaN(getYValues().value[i]))
          {
            setNull(i);
          }
        }
      }
    }

    public int add(double x, double y)
    {
      int result = super.add(x, y);
      if (treatNans)
      {
        if (Double.isNaN(getYValues().value[result]))
        {
          setNull(result);
        }
      }
      return result;
    }

    protected boolean IgnoreNull()
    {
      return treatnulls == TreatNullsStyle.IGNORE && treatNans == false;
    }

    protected boolean doNotPaintNull()
    {
      return treatnulls == TreatNullsStyle.DONOTPAINT || treatNans == true;
    }

/*
    [System.ComponentModel.Description("Gets and sets whether NaN points are treated as null points or not."),
    DefaultValue(false)]
*/
    public boolean getTreatNaNAsNull()
    {
        return treatNans;
    }

    public void setTreatNaNAsNull(boolean value)
    {
      treatNans = setBooleanProperty(treatNans, value);
    }
  }