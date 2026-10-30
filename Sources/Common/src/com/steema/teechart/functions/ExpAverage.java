package com.steema.teechart.functions;


import com.steema.teechart.languages.Language;
import com.steema.teechart.ChartException;
import com.steema.teechart.styles.ValueList;
import com.steema.teechart.styles.Series;
import com.steema.teechart.IBaseChart;

/**
 *
 * <p>Title: ExpAverage class</p>
 *
 * <p>Description: Exponential Average Function.</p>
 *
 * <p>Copyright (c) 2005-2019 by Steema Software SL. All Rights
 * Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 *
 */
public class ExpAverage extends Moving {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private double weight = 0.2;

    public ExpAverage() {
        this((IBaseChart)null);
    }

    public ExpAverage(IBaseChart c) {
        super(c);
        dPeriod = 1;
    }

    /**
     * Performs an Exponential average based on its DataSource values.<br><br>
     * The formula used  is: <br><br>
     * ExpAverage ( point ) = ExpAverage ( previous point ) * ( 1 - Weight )
     * + Weight * PointValue <br>
     * Default value: 0.2
     *
     * @return double
     */
    public double getWeight() {
        return weight;
    }

    /**
     * Performs an Exponential average based on its DataSource values.<br><br>
     * Default value: 0.2D
     *
     * @see ExpAverage#getWeight
     * @param value double
     * @throws TeeChartException
     */
    public void setWeight(double value) throws ChartException {
        if ((value < 0) || (value > 1)) {
            throw new ChartException(Language.getString("ExpAverageWeight"));
        }

        if (weight != value) {
            weight = value;
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
     * @param s Series
     * @param firstIndex int
     * @param lastIndex int
     * @return double
     */
    public double calculate(Series s, int firstIndex,
                            int lastIndex) {
        ValueList v = valueList(s);
        double result = v.getValue(lastIndex);
        return (lastIndex > 0) ?
                v.getValue(lastIndex - 1) * (1 - weight) + result * weight :
                result;
    }

    /**
     * Gets descriptive text.
     *
     * @return String
     */
    public String getDescription() {
        return Language.getString("FunctionExpAverage");
    }
}


