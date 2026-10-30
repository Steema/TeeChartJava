package com.steema.teechart.android.editors;

import com.steema.teechart.Chart;
import com.steema.teechart.Wall;
import com.steema.teechart.Walls;
import com.steema.teechart.Zoom.ZoomStyle;
import com.steema.teechart.languages.Language;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.AdapterView.OnItemSelectedListener;

public class ZoomEditor extends Dialog {
	
	private Spinner zoom;
	private Chart chart;
	
	public ZoomEditor(final Context context, final Chart aChart) {
		super(context);
		
		this.chart = aChart;
	
		LinearLayout layout = new LinearLayout(context);
		layout.setOrientation(LinearLayout.VERTICAL);
		layout.setVerticalScrollBarEnabled(true);
		
		setTitle(Language.getString("Zoom"));
	
		final String[] zoomList = { 
				"INCHART",
				"FULLCHART",
				"INCHART_MULTI"
				};
	
		zoom = new Spinner(context);
		zoom.setAdapter(new ArrayAdapter(context,
	            android.R.layout.simple_spinner_item, zoomList));
		
		zoom.setOnItemSelectedListener(new OnItemSelectedListener() {
	
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				setZoomStyle(currentZoomStyle());
			}
	
			public void onNothingSelected(AdapterView<?> arg0) {
			}
		});
		layout.addView(zoom);
		
		setZoomStyle(chart.getZoom().getZoomStyle());
		zoom.setSelection(chart.getZoom().getZoomStyle().ordinal());

		setContentView(layout);
	}
	
	protected void setZoomStyle(ZoomStyle zStyle) {
		chart.getZoom().setZoomStyle(zStyle);
	}
	
	protected ZoomStyle currentZoomStyle() {
		switch (zoom.getSelectedItemPosition()) {
		case 0: return ZoomStyle.INCHART;
		case 1: return ZoomStyle.FULLCHART;
		default : return ZoomStyle.CLASSIC;
		}
	}
}

