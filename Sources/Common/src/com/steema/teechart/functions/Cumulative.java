package com.steema.teechart.functions;

import java.util.ArrayList;
import com.steema.teechart.languages.Language;

import com.steema.teechart.styles.ValueList;
import com.steema.teechart.IBaseChart;
import com.steema.teechart.styles.Series;

/**
 *
 * <p>Title: Cumulative class</p>
 *
 * <p>Description: The Cumulative function sums the Series values starting
 * from the first point.</p>
 * <p>Example:
 * Given these values:  1 2 3
 * The Cumulative function returns:  1 3 6  ( 1=1, 1+2=3 and 1+2+3=6 )</p>
 *
 * <p>Example:
 * <pre><font face="Courier" size="4">
 * cumulativeFunction = new com.steema.teechart.functions.
 *       Cumulative(myChart.getChart());
 * cumulativeFunction.setPeriod(1);
 *
 * lineSeries.setDataSource(barSeries);
 * lineSeries.setFunction(cumulativeFunction);
 * </font></pre></p>
 *
 * <p>Copyright (c) 2005-2019 by Steema Software SL. All Rights
 * Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 *
 */
public class Cumulative extends Function {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Cumulative() {
        this(null);
    }

    public Cumulative(IBaseChart c) {
        super(c);
        dPeriod = 1;
    }
    /**
     * Performs function operation on SourceSeries series.<br>
     * First and Last parameters are ValueIndex of first and last point used
     * in calculation. You can override Calculate function to perform
     * customized calculation on one SourceSeries. <br>
     *
     * @param sourceSeries Series
     * @param firstIndex int
     * @param lastIndex int
     * @return double
     */
    public double calculate(Series sourceSeries,
                            int firstIndex,
                            int lastIndex) {

        double result = (dPeriod == 1 && series.getMandatory().getCount() > 0) ? series.getMandatory().getLast() : 0;
        if (firstIndex>=0)
  			{
  				for (int t = firstIndex; t <= lastIndex; t++) //B428
  				{
  					result += valueList(sourceSeries).value[t];
  				}
  			}
        return result;
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
        double result = (valueIndex == 0) ? 0 :
                        series.getMandatory().value[valueIndex - 1];

        for (int t = 0; t < sourceSeriesList.size(); t++) {
            ValueList v = valueList((Series) sourceSeriesList.get(t));
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
        return Language.getString("FunctionCumulative");
    }
}

