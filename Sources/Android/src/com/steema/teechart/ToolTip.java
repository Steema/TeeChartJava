package com.steema.teechart;

import android.view.View;
import android.widget.Toast;

import com.steema.teechart.drawing.ChartFont;
import com.steema.teechart.drawing.Color;
import com.steema.teechart.styles.SeriesMarks;

/**
 * <p>
 * Title:
 * </p>
 * 
 * <p>
 * Description:
 * </p>
 * 
 * <p>
 * Copyright: Copyright (c) 2006-2019
 * </p>
 * 
 * <p>
 * Company:
 * </p>
 * 
 * @author not attributable
 * @version 1.0
 */
public class ToolTip {

	private Chart chart;
	private boolean active = false;
	private int initialDelay = 100;
	private int hideDelay = 1250;
	private String text = "";
	private SeriesMarks marks;

	public ToolTip(Chart chart) {
		this.chart = chart;

		fontColor = new Color(0, 0, 0);
		backColor = new Color(255, 255, 174);
	}

	protected ChartFont prepareFont(ChartFont f) {

		// int style = 0;

		// if (f.getBold())
		// style = style | SWT.BOLD;

		// if (f.getItalic())
		// style = style | SWT.ITALIC;

		// g.setForeground(color(f.getBrush().getColor()));

		// if (!g.getFont().equals(oldFont))
		// g.getFont().dispose();

		return null; // new Font(null, f.getName(), f.getSize()-2, style);

	}

	private Color backColor;
	private Color fontColor;
	private Toast toast;

	public void prepareMarks(SeriesMarks marks) {
		this.marks = marks;
		fontColor = marks.getFont().getColor();
		backColor = marks.getBackColor();
	}

	public void setActive(boolean value) {
		if (value)
			activate();
		else
			deactivate();

		active = value;
	}

	public void setText(String text) {
		this.text = text;
	}

	public void hide() {
		setActive(false);
	}

	public void show() {
		setActive(true);
	}

	public String getText() {
		return text;
	}

	public int getInitialDelay() {
		return initialDelay;
	}

	public void setInitialDelay(int value) {
		initialDelay = value;
	}

	public int getDismissDelay() {
		return hideDelay;
	}

	public void setDismissDelay(int value) {
		hideDelay = value;
	}

	public void deactivate() {
		if (toast != null) {
			toast.cancel();
		
			toast=null;
		}
	}

	public void activate() {
		if (toast==null)
			toast=Toast.makeText(((View) chart.getParent().getControl()).getContext(),
				text, Toast.LENGTH_SHORT);
		else
			toast.setText(text);
		
		toast.show();
	}
}