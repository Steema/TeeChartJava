/**
 *
 * <p>Title: LegendItems class</p>
 *
 * <p>Description: Accesses all the Legend Items</p>
 *
 * <p>Copyright (c) 2008-2019 by Steema Software SL. All Rights Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 *
 */

package com.steema.teechart.legend;

import java.util.*;

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
