package com.steema.teechart.data;

import com.steema.teechart.styles.Series;

import android.database.Cursor;

public class CursorSeriesSource extends SeriesSource {

	public Cursor cursor;
	private Series series;
	
	 
	public void refreshData() {
		
		if (cursor != null) {
		    if (cursor.moveToFirst()) {

		    	series = (Series)super.series;

		    	
		    	int labels = cursor.getColumnIndex(series.getLabelMember()); 
		        int x = cursor.getColumnIndex(series.getNotMandatory().valueSource);
		        int y = cursor.getColumnIndex(series.getMandatory().valueSource);
		    
		        do {
		        	
		            final double yValue = cursor.getDouble(y);

		            if (x == -1) {
			            if (labels == -1)
			            	series.add(yValue);
			            else {
			            	final String label = cursor.getString(labels);
			            
			            	series.add(yValue, label);
			            }
		            }
		            else
		            {
			            final double xValue = cursor.getDouble(x);
		            
			            if (labels == -1)
			            	series.add(xValue, yValue);
			            else {
			            	final String label = cursor.getString(labels);
		            
			            	series.add(xValue, yValue, label);
			            }
		            }
		            
		        } while (cursor.moveToNext());
		    }
		}
	}
}
