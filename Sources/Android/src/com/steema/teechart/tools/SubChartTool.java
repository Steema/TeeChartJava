/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.steema.teechart.tools;

import android.content.Context;

import com.steema.teechart.Chart;
import com.steema.teechart.Cursor;
import com.steema.teechart.IBaseChart;
import com.steema.teechart.events.ChartDrawEvent;
import com.steema.teechart.events.FrameworkMouseEvent;
import com.steema.teechart.languages.Language;

/**
 * 
 * @author Shunyata Kharg
 */
public class SubChartTool extends Tool {

	private ChartCollection charts;

	public SubChartTool() {
		this((IBaseChart) null);
	}

	public SubChartTool(IBaseChart c) {
		super(c);
	}

	@Override
	public void chartEvent(ChartDrawEvent ce) {
		if (getActive() && ce.getDrawPart() == ChartDrawEvent.CHART && ce.getID() == ChartDrawEvent.PAINTED) {
			for (int i = 0; i < getCharts().size(); i++) {
				if ((getChart() != null) && (getChart().getGraphics3D() != null)) {
					getCharts().get(i).getChart().getChart().setGraphics3D(getChart().getGraphics3D());
					getCharts().get(i).getChart().getChart().paint(getChart().getGraphics3D(), getCharts().get(i).getBounds());
				}
			}
		}
	}

	@Override
	public Cursor mouseEvent(FrameworkMouseEvent e, Cursor c) {
		for (int i = 0; i < getCharts().size(); i++) {
			if (getCharts().get(i).getBounds().contains(e.getX(), e.getY())) {
				((InnerChart) getCharts().get(i).getChart()).fireMouseEvent(e);

				getChart().setCancelMouse(getCharts().get(i).getChart().getChart().cancelMouse || getCharts().get(i).getChart().getZoom().getActive()
						|| getCharts().get(i).getChart().getPanning().getActive());

				if (((Chart) getChart()).cancelMouse) {
					return getCharts().get(i).getChart().getTeeCursor();
				}
			}
		}
		return Cursor.DEFAULT;
	}

	public ChartCollection getCharts() {
		if (charts == null) {
			charts = new ChartCollection(this);
		}
		return charts;
	}

	/**
	 * Gets descriptive text.
	 * 
	 * @return String
	 */
	@Override
	public String getDescription() {
		return Language.getString("SubChartTool");
	}

	/**
	 * Gets detailed descriptive text.
	 * 
	 * @return String
	 */
	@Override
	public String getSummary() {
		return Language.getString("SubChartSummary");
	}
}
