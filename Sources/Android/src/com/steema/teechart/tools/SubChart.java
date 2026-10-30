/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.steema.teechart.tools;

import com.steema.teechart.Chart;
import com.steema.teechart.IBaseChart;
import com.steema.teechart.Rectangle;
import com.steema.teechart.TChart;
import com.steema.teechart.misc.Utils;

/**
 * 
 * @author Shunyata Kharg
 */
public class SubChart {
	private Tool iTool;
	private TChart chart;
	private String data;

	public SubChart(IBaseChart c) {
	}

	public SubChart(ChartCollection collection) {
		if (collection != null) {
			iTool = collection.getOwner();
			if (iTool != null) {
				getChart();
			}
		}
	}

	public SubChart() {
		this((ChartCollection) null);
	}

	public final TChart getChart() {
		if (chart == null) {
			chart = new InnerChart(this, ((TChart)getITool().getChart().getParent()).getContext());
			getData();
		}
		return chart;
	}

	public void setChart(TChart value) {
		chart = value;
	}

	public boolean clicked(int x, int y) {
		return getBounds().contains(x, y);
	}

	public int getHeight() {
		return getChart().getHeight();
	}

	public void setHeight(int value) {
		getChart().setHeight(value);
	}

	public int getWidth() {
		return getChart().getWidth();
	}

	public void setWidth(int value) {
		getChart().setWidth(value);
	}

	public int getTop() {
		return getChart().getTop();
	}

	public void setTop(int value) {
		getChart().getChart().getChartBounds().setTop(value);
	}

	public int getLeft() {
		return getChart().getLeft();
	}

	public void setLeft(int value) {
		getChart().getChart().getChartBounds().setLeft(value);
	}

	public Tool getITool() {
		return iTool;
	}

	public Rectangle getBounds() {
		Rectangle tmpR = getChart().getChart().getChartBounds();
		return new Rectangle(Math.abs(tmpR.getLeft()), Math.abs(tmpR.getTop()), Utils.round(Math.abs(tmpR.getWidth())), Utils.round(Math.abs(tmpR.getHeight())));/**/
	}

	private void getData() {
		if (!Utils.isNullOrEmpty(data)) {
			Chart c = chart.getChart();
			// chart.getImport().in
		}
	}
}
