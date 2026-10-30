package com.steema.teechart.functions;


import com.steema.teechart.languages.Language;

/**
 *
 * <p>Title: Subtract class</p>
 *
 * <p>Description: Subtract Function.</p>
 *
 * <p>Example:
 * <pre><font face="Courier" size="4">
 * subtractFunction = new com.steema.teechart.functions.Subtract();
 * subtractFunction.setChart(myChart.getChart());
 * subtractFunction.setPeriod(0); //all points
 * </font></pre></p>
 *
 * <p>Copyright (c) 2005-2019 by Steema Software SL. All Rights
 * Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 *
 */
public class Subtract extends ManySeries {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected double calculateValue(double result, double value) {
        return result - value;
    }

    /**
     * Gets descriptive text.
     *
     * @return String
     */
    public String getDescription() {
        return Language.getString("FunctionSubtract");
    }
}



