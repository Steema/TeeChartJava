/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.steema.teechart.legend;

import com.steema.teechart.Rectangle;
import com.steema.teechart.TeeBase;
import com.steema.teechart.drawing.StringAlignment;

/**
 *
 * @author Stark
 * @date 2008-01-29
 */
public class LegendItem extends TeeBase {
	
  private static final long serialVersionUID = 1L;
  
  private StringAlignment align;
  private Rectangle symbolRect;
  private int left;
  private int top;
  private String FText;
  private String FText2;
  private Legend iLegend;
  
  public LegendItem(Legend legend){
    super(legend.getChart());
    iLegend = legend;
  }
    
  public void setSymbolRect(Rectangle rect )
  {
    if (rect != symbolRect){
    symbolRect = rect;
    }
  }
  
  public Rectangle getSymbolRect(){
    return symbolRect;
  }
  
  public void setAlign(StringAlignment value){
    if (value != align){
      align = value;
    }
  } 
  
  public StringAlignment getAlign(){
      return align;
    }
  
  public void setLeft(int value){
    if (value != left) {
      left = value;
    }
  }
  
  public int getLeft(){
    return left;
  }
  
  public void setTop(int value){
    if (value != top) {
      top = value;
    }
  }
  
  public int getTop(){
    return top;
  }
  
  public void setFText(String text){
    if (text != null) {
      if(!text.equals(FText)){
        FText = text;
      }
    }
  }
  
  public String getFText(){
    return FText;
  }
  
  public void setFText2(String text){
    if (text != null) {
      if(!text.equals(FText2)){
        FText2 = text;
      }
    }
  }
  
public String getFText2(){
    return FText2;
  }

private String setTextVariable(String variable, String value){
	if (!(variable.equals(value)))
	{
		variable = value;
		iLegend.getLegendItems().setCustom(true);
		iLegend.invalidate();
	}
    return variable;
  }
}