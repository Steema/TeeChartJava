package com.steema.teechart.styles;

import com.steema.teechart.TeeBase;
import com.steema.teechart.IBaseChart;

/**
 * <p>Title: PieMarks class</p>
 *
 * <p>Description: Customized pie series marks with additional properties.</p>
 *
 * <p>Copyright (c) 2005-2019 by Steema Software SL. All Rights Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 *
 */
public class PieMarks extends TeeBase
{
  private static final long serialVersionUID = 1L;
  
  Series series = null;
  private boolean vertcenter = false;
  private int legsize = 0;
  private boolean insideSlice = false;
  private boolean rotated = false;
  private MarksRotateStyle rotateStyle = MarksRotateStyle.Radial;

  public enum MarksRotateStyle {
    // / <summary>
    // /Set the rotation of the Marks as Radial.
    // / </summary>
    Radial,
    // / <summary>
    // /Set the rotation of the Marks as Tangencial.
    // / </summary>
    Tangencial
  };

  public PieMarks(IBaseChart c, Series s)
  {
    super(c);
    if (series == null) series = s;
  }

  public boolean getVertCenter()
  {
    return vertcenter;
  }

  public void setVertCenter(boolean value)
  {
    if (vertcenter != value)
    {
      vertcenter = value;
      if (series != null) series.refreshSeries();
    }
  }

  public int getLegSize()
  {
    return legsize; 
  }

  public void setLegSize(int value)
  {
    if (legsize != value)
    {
      legsize = value;
      if (series != null) series.refreshSeries();
    }
  }

  /// <summary>
  /// When true plots Mark in centre of slice
  /// </summary>
  public boolean getInsideSlice() {return insideSlice; }

  public void setInsideSlice(boolean value) {
      insideSlice = value;
  }

  /// <summary>
  /// When true rotates Marks in line with slice
  /// </summary>
  public boolean getRotated() { return rotated; }

  public void setRotated(boolean value) {
      rotated = value;
  }

  /// <summary>
  /// Sets the rotation of the Marks in line with slice.
  /// </summary>
  public MarksRotateStyle getRotateStyle() { return rotateStyle; }

  public void setRotateStyle(MarksRotateStyle value) {
    if (rotateStyle != value) {
      rotateStyle = value;
      invalidate();
    }
  }
}
