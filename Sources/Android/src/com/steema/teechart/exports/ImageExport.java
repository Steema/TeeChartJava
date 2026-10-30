package com.steema.teechart.exports;

import com.steema.teechart.IBaseChart;
import com.steema.teechart.drawing.Image;

public class ImageExport {

	private JavascriptFormat jscriptFormat;
	IBaseChart chart;
	
	public ImageExport(IBaseChart chart) {
		this.chart=chart;
	}

	public Image image(int width, int height) {
		return chart.image(width, height);
	}

    /**
     * Exports Chart as Javascript format.
     *
     * @return JavascriptFormat
     */
    public JavascriptFormat getJScript() {
    	if (jscriptFormat == null) {
    		jscriptFormat = new JavascriptFormat(chart);
    	}
    	return jscriptFormat;
    }
}
