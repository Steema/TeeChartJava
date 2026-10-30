/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.steema.teechart.axis;

/**
 *
 * @author admin
 */
public interface AxisCalcResolver {

  //void GetAxisDrawLabelEventHandler(Object sender, GetAxisDrawLabelEventArgs e);
  //void DrawAxisEventDelegate();
  void AxisLabelValueDelegate(Axis axis, AxisLabelValueEventArgs e);
  int AxisCalcPosLabelsEventHandler(Axis axis, int value); //MS: v3
  int AxisCalcPosValueDelegate(double value);
  int AxisCalcXPosValueDelegate(double value);
  int AxisCalcYPosValueDelegate(double value);
  double AxisCalcPosPointDelegate(int value);
  int oldCalcXDelegate(double value);
  int oldCalcYDelegate(double value);
  double oldCalcPosPointDelegate(int value);

}
