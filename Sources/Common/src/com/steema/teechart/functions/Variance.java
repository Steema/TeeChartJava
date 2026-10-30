package com.steema.teechart.functions;

import java.util.ArrayList;
import com.steema.teechart.languages.Language;

import com.steema.teechart.misc.Utils;
import com.steema.teechart.styles.Series;
import com.steema.teechart.styles.ISeries;


/**
 *
 * <p>Title: Variance class</p>
 *
 * <p>Description: Variance function.</p>
 *
 * <p>Example:
 * <pre><font face="Courier" size="4">
 * function = new com.steema.teechart.functions.Variance();
 * function.setChart(myChart.getChart());
 * function.setPeriod(0); //all points
 *
 * Line functionSeries = new Line(myChart.getChart());
 * functionSeries.setTitle("Variance");
 * functionSeries.setDataSource(series);
 * functionSeries.setVerticalAxis(VerticalAxis.RIGHT);
 * functionSeries.setFunction(function);
 * </font></pre></p>
 *
 * <p>Copyright (c) 2005-2019 by Steema Software SL. All Rights
 * Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 *
 */
public class Variance extends Function {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
     * Performs function operation on s series.
     *
     * @param s Series
     * @param firstIndex int
     * @param lastIndex int
     * @return double
     */
    public double calculate(Series s, int firstIndex, int lastIndex) {
        if (firstIndex == -1) {
            firstIndex = 0;
        }
        if (lastIndex == -1) {
            lastIndex = s.getCount() - 1;
        }

        int count = lastIndex - firstIndex + 1;

        if (count > 0) {
            double mean = s.getMandatory().getTotal() / (double) count;

            if (count != s.getCount()) {
                mean = 0.0;

                for (int t = firstIndex; t <= lastIndex; t++) {
                    mean += s.getMandatory().value[t];
                }
                mean /= (double) count;
            }

            double sum = 0.0;
            for (int t = firstIndex; t <= lastIndex; t++)
                sum += (s.getMandatory().value[t]-mean)*(s.getMandatory().value[t]-mean);

            return sum / (double) count;
        } else {
            return 0.0;
        }
    }

    /**
     * Performs function operation on all sourceSeries series.
     *
     * @param sourceSeries ArrayList
     * @param valueIndex int
     * @return double
     */
    public double calculateMany(ArrayList sourceSeries, int valueIndex) {
        int count = sourceSeries.size();
        if (count > 0) {
            double mean = 0.0;
            for (int t = 0; t < count; t++) {
                mean += ((ISeries)sourceSeries.get(t)).getMandatory().value[
                        valueIndex];
            }
            mean /= (double) count;

            double sum = 0.0;
            for (int t = 0; t < count; t++) {
                sum +=
                        Utils.sqr(((ISeries) sourceSeries.get(t)).getMandatory().value[valueIndex] -
                                 mean);
            }

            return sum / (double) count;

        } else {
            return 0.0;
        }
    }

    /**
     * Gets descriptive text.
     *
     * @return String
     */
    public String getDescription() {
        return Language.getString("FunctionVariance");
    }
}

