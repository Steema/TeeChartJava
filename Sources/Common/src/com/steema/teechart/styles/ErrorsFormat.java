package com.steema.teechart.styles;

import com.steema.teechart.IBaseChart;
import com.steema.teechart.drawing.ChartPen;
import com.steema.teechart.drawing.Color;

public class ErrorsFormat
{
    private ChartPen left, right, top, bottom;
    public IBaseChart IntChart;

    public ErrorsFormat(IBaseChart c)
    {
      IntChart = c;
    }

    public ChartPen getLeft()
    {
       if (left == null) left = new ChartPen(IntChart, Color.BLACK, true);
       return left;
    }
      
    public void setLeft(ChartPen value)
    {
       left = value;
    }

    public ChartPen getRight()
    {
        if (right == null) right = new ChartPen(IntChart, Color.BLACK, true);
        return right;
    }
      
    public void setRight(ChartPen value)
    {
        right = value;
    }

    public ChartPen getTop()
    {
      if (top == null) top = new ChartPen(IntChart, Color.BLACK, true);
      return top;
    }
     
    public void setTop(ChartPen value)
    {
       top = value;
    }

    public ChartPen getBottom()
    {
        if (bottom == null) bottom = new ChartPen(IntChart, Color.BLACK, true);
        return bottom;
    }
    
    public void setBottom(ChartPen value)
    {
        bottom = value;
    }
  }
