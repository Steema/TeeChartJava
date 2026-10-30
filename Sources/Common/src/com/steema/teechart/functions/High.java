package com.steema.teechart.functions;


import com.steema.teechart.languages.Language;
import com.steema.teechart.styles.ValueList;
import java.util.ArrayList;
import com.steema.teechart.styles.Series;

/**
 *
 * <p>Title: High class</p>
 *
 * <p>Description: High Function. Returns highest value.</p>
 *
 * <p>Example:
 * <pre><font face="Courier" size="4">
 * highFunction = new com.steema.teechart.functions.High();
 * highFunction.setChart(myChart.getChart());
 * highFunction.setPeriod(0); //all points
 *
 * lineSeries.setDataSource(barSeries);
 * lineSeries.setFunction(highFunction);
 * </font></pre></p>
 *
 * <p>Copyright (c) 2005-2019 by Steema Software SL. All Rights
 * Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 *
 */
public class High extends Function {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
     * Performs function operation on SourceSeries series.<br>
     * First and Last parameters are ValueIndex of first and last point used
     * in calculation. <br<
     * You can override Calculate function to perform customized calculation
     * on one SourceSeries.
     *
     * @param sourceSeries Series
     * @param firstIndex int
     * @param lastIndex int
     * @return double
     */
    public double calculate(Series sourceSeries,
                            int firstIndex,
                            int lastIndex) {
        ValueList v = valueList(sourceSeries);
        if (firstIndex == -1) {
            return v.getMaximum();
        } else {
            double result = v.getValue(firstIndex);
            for (int t = firstIndex + 1; t <= lastIndex; t++) {
                double tmp = v.value[t];
                if (tmp > result) {
                    result = tmp;
                }
            }
            return result;
        }
    }


    /**
     * Performs function operation on list of series (SourceSeriesList).<br>
     * The ValueIndex parameter defines ValueIndex of point in each Series in
     * list. <br>
     * You can override CalculateMany function to perform customized
     * calculation on list of SourceSeries. <br>
     *
     * @param sourceSeriesList ArrayList
     * @param valueIndex int
     * @return double
     */
    public double calculateMany(ArrayList sourceSeriesList,
                                int valueIndex) {
        ValueList v;
        double result = 0;

        for (int t = 0; t < sourceSeriesList.size(); t++) {
            v = valueList((Series) sourceSeriesList.get(t));
            if (v.count > valueIndex) {
                double tmp = v.value[valueIndex];
                if ((t == 0) || (tmp > result)) {
                    result = tmp;
                }
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
        return Language.getString("FunctionHigh");
    }
}

