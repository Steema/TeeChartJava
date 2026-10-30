/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.steema.teechart.tools;

import java.util.ArrayList;

import com.steema.teechart.TChart;

/**
 * 
 * @author Shunyata Kharg
 */
public final class ChartCollection extends ArrayList<SubChart> {
	private Tool owner;

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
		add(new SubChart(this));
		SubChart c = get(size() - 1);
		// c.getChart().setName(name);
		c.getChart().getHeader().setText(name);
		return c.getChart();
	}

}
