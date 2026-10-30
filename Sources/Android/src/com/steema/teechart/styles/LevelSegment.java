/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.steema.teechart.styles;

import java.io.Serializable;

/**
 *
 * @author Administrator
 */
public class LevelSegment implements Serializable{

    public int count;
    public LevelPoint[] points;
    
    public LevelPoint[] getPoints()
    {
      return points;
    }
}
