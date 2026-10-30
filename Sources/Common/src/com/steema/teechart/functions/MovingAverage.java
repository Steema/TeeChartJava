package com.steema.teechart.functions;


import com.steema.teechart.languages.Language;
import com.steema.teechart.IBaseChart;
import com.steema.teechart.styles.Series;
import com.steema.teechart.styles.ValueList;

/**
 *
 * <p>Title: MovingAverage class</p>
 *
 * <p>Description: Moving Average Function</p>
 *
 * <p>Copyright (c) 2005-2019 by Steema Software SL. All Rights
 * Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 *
 */
public class MovingAverage extends Moving {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private boolean fWeighted;
    private boolean fWeightedIndex;

    public MovingAverage() {
        this(null);
    }

    public MovingAverage(IBaseChart c) {
        super(c);
        fWeighted = false;
        fWeightedIndex = false;
    }

    /**
     * Calculates the Moving Average using the corresponding X point values
     * when true.<br>
     * Default value: false
     *
     * @return boolean
     */
    public boolean getWeighted() {
        return fWeighted;
    }

    /**
     * Set Weighted to true to calculate Moving Average using the corresponding
     * X point values.<br>
     * Default value: false
     *
     * @param value boolean
     */
    public void setWeighted(boolean value) {
        if (fWeighted != value) {
            fWeighted = value;
            recalculate();
        }
    }

    /**
     * Calculates the Moving Average using the source points index as the
     * "weight" when true.<br>
     * Default value: false
     *
     * @return boolean
     */
    public boolean getWeightedIndex() {
        return fWeightedIndex;
    }

    /**
     * Calculates the Moving Average using the source points index as the
     * "weight" when true.<br>
     * Default value: false
     *
     * @param value boolean
     */
    public void setWeightedIndex(boolean value) {
        if (fWeightedIndex != value) {
            fWeightedIndex = value;
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
        double result = 0;
        double tmpSumX = 0;
        ValueList tmpVList = valueList(sourceSeries);

        for (int t = firstIndex; t <= lastIndex; ++t) {

            double tmpXValue;
            double tmpYValue = tmpVList.value[t];

            if (fWeighted) {
                tmpXValue = sourceSeries.getXValues().value[t];
                result += tmpYValue * tmpXValue;
                tmpSumX += tmpXValue;
            } else if (fWeightedIndex) {
                tmpXValue = t - firstIndex + 1;
                result += tmpYValue * tmpXValue;
                tmpSumX += tmpXValue;
            } else {
                result += tmpYValue;
            }
        }

        if (fWeighted || fWeightedIndex) {
            if (tmpSumX != 0) {
                result /= tmpSumX;
            } else {
                result = 0;
            }
        } else {
            result /= (lastIndex - firstIndex + 1);
        }

        return result;
    }

    /**
     * Gets descriptive text.
     *
     * @return String
     */
    public String getDescription() {
        return Language.getString("FunctionMovingAverage");
    }
}

