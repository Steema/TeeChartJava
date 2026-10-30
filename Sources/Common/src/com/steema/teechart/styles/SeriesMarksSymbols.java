/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.steema.teechart.styles;

import com.steema.teechart.IBaseChart;

/**
 *
 * @author Administrator
 */
public class SeriesMarksSymbols extends com.steema.teechart.Shape {

  /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

public SeriesMarksSymbols(IBaseChart c) 
  {
    super(c);
    setVisible(false);
    defaultVisible = false;
    setTransparent(false);

    getShadow().setVisible(true);
    getShadow().setDefaultVisible(true);
    getShadow().setWidth(1);
    this.getShadow().setHeight(1);
    this.getShadow().setDefaultSize(1);
  }
  
  protected boolean ShouldDraw()
  {
    return (getVisible() && !getTransparent());
  }
}
