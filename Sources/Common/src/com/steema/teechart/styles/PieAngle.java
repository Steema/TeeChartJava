package com.steema.teechart.styles;

/**
 * <p>Title: PieAngle class</p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright (c) 2005-2019 by Steema Software SL. All Rights
 * Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 *
 */
public class PieAngle {
    public double StartAngle;
    public double EndAngle;
    public double MidAngle;

    public boolean contains(double value)
    {
      return (value>=StartAngle) && (value<=EndAngle);
    }
}

