package com.steema.teechart.printer;

import com.steema.teechart.TeeBase;
import com.steema.teechart.IBaseChart;

/**
 *
 * <p>Title: Printer class</p>
 *
 * <p>Description: Chart printing characteristics</p>
 *
 * <p>Copyright (c) 2005-2019 by Steema Software SL. All Rights Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 *
 */
public final class Printer extends TeeBase {

	public boolean isPrinting;

	public Printer(IBaseChart chart) {
		super(chart);
		// TODO Auto-generated constructor stub
	}

	public boolean getPrintPanelBackground() {
		// TODO Auto-generated method stub
		return false;
	}

}

