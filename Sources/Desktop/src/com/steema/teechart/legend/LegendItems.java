/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.steema.teechart.legend;

import java.util.*;

/**
 *
 * @author Stark
 * @date 2008-01-28 -- 2008-01-29
 */
public class LegendItems  extends ArrayList{
    
  /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
private Legend iLegend;
  private boolean custom = false;
  
  public LegendItems(Legend legend) {
    super();
    iLegend = legend;
  }
  
  public void setCustom(boolean value){
    if(value != custom){
      custom = value;
    }
  }
  
  public boolean getCustom(){
    return custom;
  }
  
  public LegendItem getLegendItem(int index){
    LegendItem result; 

    while (this.size() -1 < index){
      super.add(new LegendItem(iLegend));
    }
    result = (LegendItem) super.get(index);
    if (null == result){
      result = new LegendItem(iLegend);
    }

    return result;
  }
 
  public void setLegendItem(LegendItem value){ 
    super.add(value);
  }
  
  public void setLegendItem(LegendItem value, int index){ 
    super.add(index, value);
  }
}
