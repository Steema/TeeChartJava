package com.steema.teechart.styles;

import com.steema.teechart.IBaseChart;
import com.steema.teechart.drawing.ChartPen;
import com.steema.teechart.drawing.Color;

import java.io.Serializable;


public class ErrorsFormat3D extends ErrorsFormat implements Serializable
{
    private ChartPen front, back;

    public ErrorsFormat3D(IBaseChart c)
    {
	super(c);
    }
    
    public ChartPen getFront()
    {
      if (front == null) front = new ChartPen(IntChart, Color.BLACK, true);
      return front;
    }
    
    public void setFront(ChartPen value)
    {
       front = value;
    }

    public ChartPen getBack()
    {
       if (back == null) back = new ChartPen(IntChart, Color.BLACK, true);
       return back;
    }
    
    public void setBack(ChartPen value)
    {
       back = value;
    }
  }
