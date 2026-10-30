package com.steema.teechart.functions;


import com.steema.teechart.languages.Language;

/**
 *
 * <p>Title: Divide class</p>
 *
 * <p>Description: Divide Function.</p>
 *
 * <p>Example:
 * <pre><font face="Courier" size="4">
 * divideFunction = new com.steema.teechart.functions.Divide();
 * divideFunction.setChart(myChart.getChart());
 * divideFunction.setPeriod(0); //all points
 *
 * ArrayList tmpArray = new ArrayList();
 * tmpArray.add(barSeries1);
 * tmpArray.add(barSeries2);
 * lineSeries.setDataSource(tmpArray);
 * lineSeries.setFunction(divideFunction);
 * </font></pre></p>
 *
 * <p>Copyright (c) 2005-2019 by Steema Software SL. All Rights
 * Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 *
 */
public class Divide extends ManySeries {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected double calculateValue(double result, double value) {
        return (value == 0) ? result : result / value;
    }

    /**
     * Gets descriptive text.
     *
     * @return String
     */
    public String getDescription() {
        return Language.getString("FunctionDivide");
    }
}



