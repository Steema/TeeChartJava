package com.steema.teechart.functions;


import com.steema.teechart.languages.Language;

/**
 *
 * <p>Title: Multiply class</p>
 *
 * <p>Description: Multiply Function.</p>
 *
 * <p>Example:
 * <pre><font face="Courier" size="4">
 * multiplyFunction = new com.steema.teechart.functions.Multiply();
 * multiplyFunction.setChart(myChart.getChart());
 * multiplyFunction.setPeriod(0); //all points
 *
 * Series[] tmp={barSeries1,barSeries2};
 * lineSeries.setDataSource(tmp);
 *
 * lineSeries.setFunction(multiplyFunction);
 * </font></pre></p>
 *
 * <p>Copyright (c) 2005-2019 by Steema Software SL. All Rights
 * Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 *
 */
public class Multiply extends ManySeries {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected double calculateValue(double result, double value) {
        return result * value;
    }

    /**
     * Gets descriptive text.
     *
     * @return String
     */
    public String getDescription() {
        return Language.getString("FunctionMultiply");
    }
}


