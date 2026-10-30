package com.steema.teechart.styles;

import com.steema.teechart.TeeBase;
import com.steema.teechart.drawing.ChartPen;
import com.steema.teechart.drawing.IGraphics3D;

  public abstract class ChartErrorsBase extends TeeBase
  {
	private static final long serialVersionUID = 1L;
	
    private ValueList left, right, top, bottom;
    private boolean seriesColor;
    private int size = 100;
    private ErrorWidthUnit errorSizeUnits = ErrorWidthUnit.PERCENT;
    public Series ISeries;

    public ChartErrorsBase(Series AOwner)
    {
        super(AOwner.getChart());
    
        ISeries = AOwner;

        left = new ValueList(ISeries, "LeftErrors");
        right = new ValueList(ISeries, "RightErrors");
        top = new ValueList(ISeries, "TopErrors");
        bottom = new ValueList(ISeries, "BottomErrors");
    }

    public ValueList getLeft() 
    { 
        //if(left == null) left = new ValueList(ISeries, "LeftErrors");
        return  left; 
    }

    public ValueList getRight() 
    { 
        //if(right == null) right = new ValueList(ISeries, "RightErrors");
        return right; 
    }

    public ValueList getTop() 
    { 
        //if(top == null) top = new ValueList(ISeries, "TopErrors");
        return top; 
    }

    public ValueList getBottom() 
    { 
        //if(bottom == null) bottom = new ValueList(ISeries, "BottomErrors");
        return bottom; 
    }

    public ErrorWidthUnit getErrorSizeUnits() { return errorSizeUnits; }
    
    public void setErrorSizeUnits(ErrorWidthUnit value)
    {
        if (errorSizeUnits != value)
        {
          errorSizeUnits = value;
          invalidate();
        }
     }

    public int getSize() { return size; }
      
    public void setSize(int value) { size = setIntegerProperty(size, value); }

    public boolean getSeriesColor() { return seriesColor; }
     
    public void setSeriesColor(boolean value) { value = setBooleanProperty(seriesColor, value); }

    public void preparePen(int ValueIndex, ChartPen ErrorPen)
    {
      IGraphics3D g = ISeries.getChart().getGraphics3D();

      if (seriesColor) ErrorPen.setColor(ISeries.getValueColor(ValueIndex));
      g.setPen(ErrorPen);

    }

    private void drawHoriz(int XPos, int X, int Y, int HalfSize, int AZ)
    {
      IGraphics3D g = ISeries.getChart().getGraphics3D();

      if (ISeries.getChart().getAspect().getView3D())
      {
        g.horizontalLine(X, XPos, Y, AZ);
        g.verticalLine(XPos, Y - HalfSize, Y + HalfSize, AZ);
      }
      else
      {
        g.horizontalLine(X, XPos, Y);
        g.verticalLine(XPos, Y - HalfSize, Y + HalfSize);
      }
    }

    private void drawVert(int YPos, int X, int Y, int HalfSize, int AZ)
    {
      IGraphics3D g = ISeries.getChart().getGraphics3D();

      if (ISeries.getChart().getAspect().getView3D())
      {
        g.verticalLine(X, Y, YPos, AZ);
        g.horizontalLine(X - HalfSize, X + HalfSize, YPos, AZ);
      }
      else
      {
        g.verticalLine(X, Y, YPos);
        g.horizontalLine(X - HalfSize, X + HalfSize, YPos);
      }
    }

    public void drawError(int X, int Y, int ALength, int HalfSize, int AZ, boolean IsHoriz)
    {
      if (IsHoriz)
        drawHoriz(X - ALength, X, Y, HalfSize, AZ);
      else
        drawVert(Y - ALength, X, Y, HalfSize, AZ);
    }

    public void drawZError(int X, int Y, int Z, int ALength, int HalfSize)
    {
      IGraphics3D g = ISeries.getChart().getGraphics3D();
      g.moveTo(X, Y, Z);
      g.lineTo(X, Y, Z - ALength);
      g.moveTo(X, Y - HalfSize, Z - ALength);
      g.lineTo(X, Y + HalfSize, Z - ALength);
    }
  }
