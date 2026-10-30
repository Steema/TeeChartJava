package com.steema.teechart.exports;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

import com.steema.teechart.IBaseChart;

final public class TemplateExport {

	private IBaseChart chart;

	public TemplateExport(IBaseChart c) {
		chart = c;
	}
	
	public void toStream(OutputStream stream) throws IOException {
		ObjectOutputStream out = new ObjectOutputStream(stream);
		prepareTransients();
		out.writeObject(chart);
		out.close();
	}

	private void prepareTransients() {
	}

}
