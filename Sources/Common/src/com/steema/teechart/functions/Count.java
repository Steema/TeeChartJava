package com.steema.teechart.functions;

import java.util.ArrayList;
import com.steema.teechart.languages.Language;

import com.steema.teechart.styles.Series;

/**
 *
 * <p>Title: Count class</p>
 *
 * <p>Description: Count Function.</p>
 *
 * <p>Example:
 * <pre><font face="Courier" size="4">
 * countFunction = new com.steema.teechart.functions.Count();
 * countFunction.setChart(myChart.getChart());
 * countFunction.setPeriod(0); //all points
 *
 * lineSeries.setDataSource(barSeries);
 * lineSeries.setFunction(countFunction);
 * </font></pre></p>
 *
 * <p>Copyright (c) 2005-2019 by Steema Software SL. All Rights
 * Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 *
 */
public class Count extends Function {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
     * Performs function operation on SourceSeries series.<br>
     * First and Last parameters are ValueIndex of first and last point used
     * in calculation. <br>
     * You can override Calculate function to perform customized calculation
     * on one SourceSeries. <br>
     *
     * @param sourceSeries Series
     * @param firstIndex int
     * @param lastIndex int
     * @return double
     */
    public double calculate(Series sourceSeries,
                            int firstIndex,
                            int lastIndex) {
        return (firstIndex == -1) ? valueList(sourceSeries).count :
                lastIndex - firstIndex + 1;
    }

    /**
     * Performs function operation on list of series (SourceSeriesList).<br>
     * The ValueIndex parameter defines ValueIndex of point in each Series in
     * list. You can override CalculateMany function to perform customized
     * calculation on list of SourceSeries. <br>
     *
     * @param sourceSeriesList ArrayList
     * @param valueIndex int
     * @return double
     */
    public double calculateMany(ArrayList sourceSeriesList,
                                int valueIndex) {
        double result = 0;

        for (int t = 0; t < sourceSeriesList.size(); t++) {
            if (valueList((Series) sourceSeriesList.get(t)).count > valueIndex) {
                result++;
            }
        }

        return result;
    }

    /**
     * Gets descriptive text.
     *
     * @return String
     */
    public String getDescription() {
        return Language.getString("FunctionCount");
    }
}



