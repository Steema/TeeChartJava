package com.steema.teechart.functions;

import java.util.ArrayList;
import com.steema.teechart.languages.Language;

import com.steema.teechart.styles.ValueList;
import com.steema.teechart.styles.Series;

/**
 *
 * <p>Title: Add class</p>
 *
 * <p>Description: Add Function.</p>
 *
 * <p>Example:
 * <pre><font face="Courier" size="4">
 * addFunction = new com.steema.teechart.functions.Add();
 * addFunction.setChart(myChart.getChart());
 * addFunction.setPeriod(0); //all points
 * </font></pre></p>
 *
 * <p>Copyright (c) 2005-2019 by Steema Software SL. All Rights
 * Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 *
 */
public class Add extends Function {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
     * Performs function operation on SourceSeries series.
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
            return v.getTotal();
        } else {
            double result = 0;
            for (int t = firstIndex; t <= lastIndex; t++) {
                result += v.value[t];
            }
            return result;
        }
    }

    /**
     * Performs function operation on list of series (SourceSeriesList).
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
                result += v.value[valueIndex];
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
        return Language.getString("FunctionAdd");
    }
}

