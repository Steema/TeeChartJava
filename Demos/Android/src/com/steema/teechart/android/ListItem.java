package com.steema.teechart.android;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Gallery;
import android.widget.ListView;


import com.steema.teechart.TChart;

public class ListItem extends Activity implements OnClickListener {

	private Button buttonSeries, buttonTools, buttonThemes;
	public ListView listview;
	public String[] listItemSeries, listItemTools, listItemThemes;
	public EditText textEdit;
	public Gallery gall;
	public final Context context = ListItem.this;
	public CustomAdapter lvAdapter;
	public ArrayList<Device> m_Devices;
	public int toolsUsed;

	public Device device;
	public TChart chart;
	private boolean serieSelectedInAnyMoment = false,
			themeSelectedInAnyMoment = false, toolSelectedInAnyMoment = false;
	private int numSerieSelected, numThemeSelected;

	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		listItemSeries = getResources().getStringArray(R.array.SeriesArray);
		setContentView(R.layout.list_item);
		listview = (ListView) findViewById(R.id.listView1);
		listview.setAdapter(null);
		ArrayList<Device> m_Devices = new ArrayList<Device>();
		for (int i = 0; i < listItemSeries.length; i++) {
			device = new Device(listItemSeries[i], i, 0);
			m_Devices.add(device);
			
		}

		lvAdapter = new CustomAdapter(context, m_Devices);
		listview.setAdapter(lvAdapter);
		listview.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				Intent myIntent = new Intent(arg1.getContext(), ChartView.class);
				myIntent.putExtra("valueListener", 0);
				serieSelectedInAnyMoment = true;
				myIntent.putExtra("SerieSelected", true);
				if (themeSelectedInAnyMoment) {
					myIntent.putExtra("numThemeSelected", numThemeSelected);
					myIntent.putExtra("ThemeSelected", true);
				} else {
					myIntent.putExtra("ThemeSelected", false);
				}
				myIntent.putExtra("SeriesPosition", arg2);
				numSerieSelected = arg2;
				startActivityForResult(myIntent, 1);
			}
		});

		buttonSeries = (Button) findViewById(R.id.buttonSeries);
		buttonSeries.setOnClickListener(this);
		buttonTools = (Button) findViewById(R.id.buttonTools);
		buttonTools.setOnClickListener(this);
		buttonThemes = (Button) findViewById(R.id.buttonThemes);
		buttonThemes.setOnClickListener(this);
		selectTab(R.id.buttonSeries);

	}

	public void onClick(View v) {
		if (v.getId() == R.id.buttonSeries) {
			selectTab(R.id.buttonSeries);

			listItemSeries = getResources().getStringArray(R.array.SeriesArray);
			listview = (ListView) findViewById(R.id.listView1);
			listview.setAdapter(null);
			m_Devices = new ArrayList<Device>();

			for (int i = 0; i < listItemSeries.length; i++) {
				device = new Device(listItemSeries[i], i, 0);
				m_Devices.add(device);
			}

			lvAdapter = new CustomAdapter(context, m_Devices);
			listview.setAdapter(lvAdapter);
			listview.setOnItemClickListener(new OnItemClickListener() {
				public void onItemClick(AdapterView<?> arg0, View arg1,
						int arg2, long arg3) {
					Intent myIntent = new Intent(arg1.getContext(),
							ChartView.class);
					myIntent.putExtra("valueListener", 0);
					serieSelectedInAnyMoment = true;
					myIntent.putExtra("SerieSelected", true);
					if (themeSelectedInAnyMoment) {
						myIntent.putExtra("numThemeSelected", numThemeSelected);
						myIntent.putExtra("ThemeSelected", true);
					} else {
						myIntent.putExtra("ThemeSelected", false);
					}
					if (toolSelectedInAnyMoment) {
						myIntent.putExtra("ToolSelected", true);
						myIntent.putExtra("toolsList", toolsUsed);
					} else {
						myIntent.putExtra("ToolSelected", false);
					}
					myIntent.putExtra("SeriesPosition", arg2);
					numSerieSelected = arg2;
					startActivityForResult(myIntent, 1);
				}
			});

		} else if (v.getId() == R.id.buttonThemes) {
			selectTab(R.id.buttonThemes);
			listItemThemes = getResources().getStringArray(R.array.ThemesArray);
			m_Devices = new ArrayList<Device>();
			for (int i = 0; i < listItemThemes.length; i++) {
				device = new Device(listItemThemes[i], i, 2);
				m_Devices.add(device);
			}

			lvAdapter = new CustomAdapter(context, m_Devices);
			listview.setAdapter(lvAdapter);
			listview.setOnItemClickListener(new OnItemClickListener() {
				public void onItemClick(AdapterView<?> arg0, View arg1,
						int arg2, long arg3) {
					Intent myIntent = new Intent(arg1.getContext(),
							ChartView.class);
					// if button clicked is themes, valueListener = 1
					themeSelectedInAnyMoment = true;
					myIntent.putExtra("valueListener", 1);
					if (serieSelectedInAnyMoment) {
						myIntent.putExtra("SeriesPosition", numSerieSelected);
						myIntent.putExtra("SerieSelected", true);
					} else {
						myIntent.putExtra("SerieSelected", false);
					}
					if (toolSelectedInAnyMoment) {
						myIntent.putExtra("ToolSelected", true);
						myIntent.putExtra("toolsList", toolsUsed);
					} else {
						myIntent.putExtra("ToolSelected", false);
					}

					myIntent.putExtra("ThemeSelected", true);
					myIntent.putExtra("numThemeSelected", arg2);
					numThemeSelected = arg2;
					startActivityForResult(myIntent, 1);
				}
			});

		} else if (v.getId() == R.id.buttonTools) {
			selectTab(R.id.buttonTools);
			listItemTools = getResources().getStringArray(R.array.toolsArray);
			m_Devices = new ArrayList<Device>();

			for (int i = 0; i < listItemTools.length; i++) {
				device = new Device(listItemTools[i], i, 1);
				m_Devices.add(device);
			}

			lvAdapter = new CustomAdapter(context, m_Devices);
			listview.setAdapter(lvAdapter);
			listview.setOnItemClickListener(new OnItemClickListener() {
				public void onItemClick(AdapterView<?> arg0, View arg1,
						int arg2, long arg3) {
					Intent myIntent = new Intent(arg1.getContext(),
							ChartView.class);
					// if button clicked is themes, valueListener = 1
					toolSelectedInAnyMoment = true;
					myIntent.putExtra("valueListener", 2);
					if (serieSelectedInAnyMoment) {
						myIntent.putExtra("SeriesPosition", numSerieSelected);
						myIntent.putExtra("SerieSelected", true);
					} else {
						myIntent.putExtra("SerieSelected", false);
					}
					if (themeSelectedInAnyMoment) {
						myIntent.putExtra("numThemeSelected", numThemeSelected);
						myIntent.putExtra("ThemeSelected", true);
					} else {
						myIntent.putExtra("ThemeSelected", false);
					}

					myIntent.putExtra("ToolSelected", true);
					toolsUsed= arg2;
					myIntent.putExtra("toolsList", toolsUsed);
					startActivityForResult(myIntent, 1);
				}
			});

		}
	}

	private void selectTab(int selectedId) {
		buttonSeries.setSelected(selectedId == R.id.buttonSeries);
		buttonTools.setSelected(selectedId == R.id.buttonTools);
		buttonThemes.setSelected(selectedId == R.id.buttonThemes);
	}

}
