/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.steema.teechart.events;

/**
 *
 * @author admin
 */
public class ScrollModEventArgs extends TeeEvent {

    public double Min;
    public double Max;
    public boolean AllowScroll;

    public ScrollModEventArgs(double min, double max, boolean allowScroll)
    {
      Min = min;
      Max = max;
      AllowScroll = allowScroll;
    }
}
