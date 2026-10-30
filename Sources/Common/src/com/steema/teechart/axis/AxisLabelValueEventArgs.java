/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.steema.teechart.axis;

  /// <summary>

import com.steema.teechart.events.TeeEvent;

  /*
  * Internal Axis LabelValue EventArgs. See
  */
  public class AxisLabelValueEventArgs extends TeeEvent
  {
    private double tempValue;

    public AxisLabelValueEventArgs(double tmpValue)
    {
      tempValue = tmpValue;
    }

    public double getTmpValue()
    { return tempValue; }

    public void setTmpValue(double value)
    {
      tempValue = value;
    }
  }
