package com.steema.teechart.tools;

import java.util.ArrayList;

import com.steema.teechart.IBaseChart;

/**
 *
 * <p>Title: ToolsCollection class</p>
 *
 * <p>Description: Collection of Tool components.</p>
 *
 * <p>Copyright (c)
 * 2005-2013 by Steema Software SL. All Rights Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 *
 */
public class ToolsCollection extends ArrayList {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	transient public IBaseChart chart;

    public ToolsCollection(IBaseChart c) {
        super();
        chart = c;
    }

    /**
     * Adds a new tool to your TChart.
     * Returns the added tool instance.
     * @param tool Tool
     * @return int
     */
    public Tool add(Tool tool) {
        tool.setChart(chart);
        return internalAdd(tool);
    }

    Tool internalAdd(Tool tool) {
        if (indexOf(tool) == -1 && !tool.InternalUse) {
            super.add(tool);
        }
        return tool;
    }

    public Tool add(Class type) throws InstantiationException,
            IllegalAccessException {
        return add((Tool) (type.newInstance()));
    }

    public Tool getTool(int index) {
        return (Tool) get(index);
    }

    public void setTool(int index, Tool value) {
        set(index, value);
    }

    /**
     * Returns the corresponding point index which has the specified Value.
     *
     * @param s Tool
     * @return int
     */
    public int indexOf(Tool s) {
        for (int t = 0; t < size(); t++) {

            if (getTool(t) == s) {
                return t;
            }
        }

        return -1;
    }

    /**
     * Removes a tool from the TChart.
     *
     * @param s Tool
     */
    public void remove(Tool s) {
        int i = indexOf(s);
        if (i != -1) {
            remove(i);
            
            if (!chart.getParent().isDisposing())
                chart.invalidate();
        }
    }

    /**
     * Sets Chart interface to tools collection
     *
     * @param chart IBaseChart
     */
    public void setChart(IBaseChart chart) {
        this.chart = chart;

        for (int t = 0; t < size(); t++) {
            getTool(t).setChart(chart);
        }
    }
    
    public void dispose()
    {
    	for (int i = 0 ; i < size();i++)
        {
           getTool(i).dispose();
        }
    }
}

