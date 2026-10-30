/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.steema.teechart.axis;

/**
 *
 * @author admin
 */
public class AxisCalcAdapter implements AxisCalcResolver {
    
  public void AxisLabelValueDelegate(Object sender, AxisLabelValueEventArgs e){};
  public int AxisCalcPosLabelsEventHandler(Axis axis, int value) { return 0; };
  public int AxisCalcPosValueDelegate(double value) { return 0; };
  public double AxisCalcPosPointDelegate(int value) { return 0; };

    public int AxisCalcXPosValueDelegate(double value) {
        //throw new UnsupportedOperationException("Not supported yet.");
        return 0;
    }

    public int AxisCalcYPosValueDelegate(double value) {
        //throw new UnsupportedOperationException("Not supported yet.");
        return 0;
    }

    public int oldCalcXDelegate(double value) {
        //throw new UnsupportedOperationException("Not supported yet.");
        return 0;
    }

    public int oldCalcYDelegate(double value) {
        //throw new UnsupportedOperationException("Not supported yet.");
        return 0;
    }

    public double oldCalcPosPointDelegate(int value) {
        //throw new UnsupportedOperationException("Not supported yet.");
        return 0;
    }

    public void AxisLabelValueDelegate(Axis axis, AxisLabelValueEventArgs e) {
        //throw new UnsupportedOperationException("Not supported yet.");
    }

}
