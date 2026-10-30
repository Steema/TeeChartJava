/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.steema.teechart.functions;

/**
 *
 * @author Steema Software SL
 */
public class Regression {

  /// <summary>
  /// Ordinary least-square linear regression with or without weights
  /// </summary>
  /// <param name="numpoints">The number of points in x array.</param>
  /// <param name="x">Array of x values. The size of x and y must be the same.</param>
  /// <param name="y">Array of y values. The size of x and x must be the same.</param>
  /// <param name="weights">Point weights. If null, no weights are used in linear regression.</param>
  /// <param name="coeffs">Returns linear regression coefficients.</param>
  public static double[] LinearRegression(int numpoints, double[] x, double[] y, double[] weights)
  {
      double[] coeffs = new double[2];
      double sum = 0.0;
      double sumX = 0.0;
      double sumX2 = 0.0;
      double sumY = 0.0;
      double sumXY = 0.0;
      if (weights != null)
      {
          for (int i=0; i<numpoints; i++)
          {
              sum += weights[i];
              sumX += x[i]*weights[i];
              sumX2 += x[i]*x[i]*weights[i];
              sumY += y[i]*weights[i];
              sumXY += x[i]*y[i]*weights[i];
          }
      }
      else
      {
          sum = (double)numpoints;
          for (int i=0; i<numpoints; i++)
          {
              sumX += x[i];
              sumY += y[i];
              sumX2 += x[i]*x[i];
              sumXY += x[i]*y[i];
          }
      }
      double D = sum * sumX2 - sumX*sumX;
      double tmp1 = sumX2/D;
      double tmp2 = -sumX/D;
      double tmp3 = sum/D;
      coeffs[0] = tmp1*sumY+tmp2*sumXY;
      coeffs[1] = tmp2*sumY + tmp3*sumXY;
      
      return coeffs;
  }

}
