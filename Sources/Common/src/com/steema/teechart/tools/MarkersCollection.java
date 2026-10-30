/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.steema.teechart.tools;

import com.steema.teechart.Chart;
import com.steema.teechart.IBaseChart;

/**
 *
 * @author Administrator
 */
public class MarkersCollection extends ToolsCollection 
{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public MarkersCollection() {
        this((Chart) null);
    }
    
    public MarkersCollection(IBaseChart c)
    { 
	 super(c);
        
    }
    
    public Marker getMarker(int i)
    {
         return (Marker)super.get(i);
    }
    public void setMarker(Marker value, int index)
    {
        super.set(index, value);
    }
    
    public Marker add(String aText) 
    {
        Marker result = null;
        try {
            result = (Marker)super.add(Marker.class);
            result.getShape().setTransparent(true);
            result.getShape().getFont().setName("Arial");
            result.getShape().getPen().setVisible(false);
            result.getShape().getShadow().setVisible(false);
            result.setText(aText);
        } catch (IllegalAccessException e) {
            return null;
        } catch (InstantiationException e) {
            return null;
        }
	return result;
   }
    public void setParentChart(IBaseChart chart)
    {
	
        this.chart = chart;
	for (int i=0;i<size();i++)
	{
                getMarker(i).setChart(chart);
     	}
   }

    


}
