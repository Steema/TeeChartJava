package com.steema.teechart.styles;

import com.steema.teechart.IBaseChart;
import com.steema.teechart.TeeBase;

/**
 *
 * @author Steema Software
 */
public class ContourSeriesMarks extends TeeBase 
{
  private static final long serialVersionUID = 1L;
	
  protected Contour iSeries = null;
  protected boolean antioverlap = false;
  protected boolean atSegments = true;
  protected boolean colorlevel = false;
  protected int density = 4;
  protected int margin = 0;
  
  public ContourSeriesMarks(IBaseChart c) 
  {
    super(c);
  }
  
  public ContourSeriesMarks(Contour cs)
  {
    super(null);
    iSeries = cs;
  }
 
  public boolean getVisible()
  {
    return iSeries.getMarks().getVisible(); 
  }
  
  public void setVisible(boolean value)
  {
    iSeries.getMarks().setVisible(value);
  }

  public boolean getAtSegments()
  {
    return atSegments; 
  }
  
  public void setAtSegments(boolean value)
  {
    if (atSegments != value)
    {
      atSegments = value;
      iSeries.invalidate();
    }
  }
  
  public boolean getAntiOverlap()
  {
    return antioverlap; 
  }
  
  public void setAntiOverlap(boolean value)
  {
    if (antioverlap != value)
    {
      antioverlap = value;
      iSeries.invalidate();
    }
  }

  public boolean getColorLevel() {
      return colorlevel;
  }

  public void setColorLevel(boolean value) 
  {
    if (colorlevel != value)
    {
      colorlevel = value;
      iSeries.invalidate();
    }
  }
  
  public int getDensity()
  {
    return density; 
  }
  
  public void setDensity(int value)
  {
    if (density != value)
    {
      density = value;
      iSeries.invalidate();
    }
  }
  
  public int getMargin()
  {
    return margin; 
  }
   
  public void setMargin(int value)
  {
    if (margin != value)
    {
      margin = value;
      iSeries.invalidate();
    }
  }
}
