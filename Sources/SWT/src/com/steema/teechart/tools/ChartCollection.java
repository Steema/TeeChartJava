/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.steema.teechart.tools;

import com.steema.teechart.TChart;
import java.util.ArrayList;

import org.eclipse.swt.widgets.Composite;

/**
 *
 * @author Shunyata Kharg
 */
public final class ChartCollection extends ArrayList<SubChart> {
    private Tool owner;
    private Composite composite; 
    private int style;
    
    public ChartCollection(Tool tool, Composite comp, int style) {
    	this(tool);
    	this.composite = comp;
    	this.style = style;
    }
    
    public ChartCollection(Tool tool) {
        super();
        owner = tool;
    }
    
    public ChartCollection() {
        this(null);
    }
    
    public Tool getOwner() {
        return owner;
    }
    
    public void setOwner(Tool value) {
        owner = value;
    }
    
    public TChart addChart(String name) {
        add(new SubChart(this, composite, style));
        SubChart c = get(size() - 1);
        //c.getChart().setName(name);
        c.getChart().getHeader().setText(name);
        return c.getChart();
    }
    
}
