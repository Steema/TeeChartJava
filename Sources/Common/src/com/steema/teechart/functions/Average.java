package com.steema.teechart.functions;

import com.steema.teechart.styles.ValueList;
import com.steema.teechart.languages.Language;
import java.util.ArrayList;

import com.steema.teechart.styles.Series;

/**
 *
 * <p>Title: Average class</p>
 *
 * <p>Description: Average (mean) Function.</p>
 *
 * <p>Example:
 * <pre><font face="Courier" size="4">
 * avgFunction = new com.steema.teechart.functions.Average();
 * avgFunction.setChart(myChart.getChart());
 * avgFunction.setPeriod(0); //all points
 * avgFunction.setIncludeNulls(false);
 *
 * lineSeries.setDataSource(barSeries);
 * lineSeries.setFunction(avgFunction);
 * </font></pre></p>
 *
 * <p>Copyright (c) 2005-2019 by Steema Software SL. All Rights
 * Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 *
 */
public class Average extends Function {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private boolean includeNulls = true;

    /**
     * Initializes a new Average function, using Null values.
     */
    public Average() {
        this(true);
    }

    /**
     * If UseNulls is true, null values will be treated as zero in average
     * calculation.
     *
     * @param useNulls boolean
     */
    public Average(boolean useNulls) {
        super();
        includeNulls = useNulls;
    }

    /**
     * Calculates the average using only the non-null points of a series, or
     * not.
     * @return boolean
     */
    public boolean getIncludeNulls() {
        return includeNulls;
    }

    /**
     * Calculates the average using only the non-null points of a series, or
     * not.
     *
     * @param value boolean
     */
    public void setIncludeNulls(boolean value) {
        if (includeNulls != value) {
            includeNulls = value;
            recalculate();
        }
    }

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
        if ((firstIndex == -1) && includeNulls) {
            return (sourceSeries.getCount() > 0) ?
                    valueList(sourceSeries).getTotal() / sourceSeries.getCount() : 0;
        } else {
            if (firstIndex == -1) {
                firstIndex = 0;
                lastIndex = sourceSeries.getCount() - 1;
            }

            double result = 0;
            int tmpCount = 0;
            ValueList v = valueList(sourceSeries);

            for (int t = firstIndex; t <= lastIndex; t++) {
                if (includeNulls || (!sourceSeries.isNull(t))) {
                    result += v.value[t];
                    tmpCount++;
                }
            }

            return (tmpCount == 0) ? 0 : result / tmpCount;
        }
    }

    /**
     * Performs function operation on list of series (SourceSeriesList).<br>
     * The ValueIndex parameter defines ValueIndex of point in each Series
     * in list. <br>
     * You can override CalculateMany function to perform customized
     * calculation on list of SourceSeries. <br>
     *
     * @param sourceSeriesList ArrayList
     * @param valueIndex int
     * @return double
     */
    public double calculateMany(ArrayList sourceSeriesList,
                                int valueIndex) {
        if (sourceSeriesList.size() > 0) {
            ValueList v;
            double result = 0;
            int counter = 0;
            for (int t = 0; t < sourceSeriesList.size(); t++) {
                Series s = (Series) sourceSeriesList.get(t);
                v = valueList(s);
                if ((v.count > valueIndex) && (includeNulls || (!s.isNull(t)))) {
                    counter++;
                    result += v.value[valueIndex];
                }
            }
            return (counter == 0) ? 0 : result / counter;
        } else {
            return 0;
        }
    }

    /**
     * Gets descriptive text.
     *
     * @return String
     */
    public String getDescription() {
        return Language.getString("FunctionAverage");
    }
}


