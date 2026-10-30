package com.steema.teechart.android;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.Gallery;
import android.widget.ListView;

import com.steema.teechart.TChart;

public class ListItemPhone extends Activity  {

	public ListView listview;
	public String[] listItemPhone;
	public EditText textEdit;
	public Gallery gall;
	public final Context context = ListItemPhone.this;
	public CustomAdapter lvAdapter;
	public ArrayList<Device> m_Devices;
	public int toolsUsed;

	public Device device;
	public TChart chart;

	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		listItemPhone = getResources().getStringArray(R.array.PhoneStatisticsArray);
		setContentView(R.layout.list_item_phone);
		listview = (ListView) findViewById(R.id.listViewPhone);
		listview.setAdapter(null);
		ArrayList<Device> m_Devices = new ArrayList<Device>();
		for (int i = 0; i < listItemPhone.length; i++) {
			device = new Device(listItemPhone[i], i, 3);
			m_Devices.add(device);
			
		}

		lvAdapter = new CustomAdapter(context, m_Devices);
		listview.setAdapter(lvAdapter);
		listview.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				if(arg2==0)
				{
				    Intent myIntent = new Intent(arg1.getContext(), VolumeStatistics.class);
		            startActivityForResult(myIntent, 0);
				}
				else if(arg2==1)
				{
				    Intent myIntent = new Intent(arg1.getContext(), BatteryStatistics.class);
		            startActivityForResult(myIntent, 0);
				}
				else if(arg2==2)
				{
				    Intent myIntent = new Intent(arg1.getContext(), WIFIStatistics.class);
		            startActivityForResult(myIntent, 0);
				}
				
				else if(arg2==3)
				{
				    Intent myIntent = new Intent(arg1.getContext(), ImageStatistics.class);
		            startActivityForResult(myIntent, 0);
				}
			}
		});


	}



}