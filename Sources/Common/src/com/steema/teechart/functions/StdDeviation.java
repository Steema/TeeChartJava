package com.steema.teechart.functions;

import java.util.ArrayList;
import com.steema.teechart.languages.Language;

import com.steema.teechart.styles.ValueList;
import com.steema.teechart.IBaseChart;
import com.steema.teechart.styles.Series;


/**
 *
 * <p>Title: StdDeviation class</p>
 *
 * <p>Description: Standard Deviation Function.</p>
 *
 * <p>Copyright (c) 2005-2019 by Steema Software SL. All Rights
 * Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 *
 */
public class StdDeviation extends Function {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private boolean complete;
    private double iSum, iSum2;
    private int iNumPoints;

    public StdDeviation() {
        this((IBaseChart)null);
    }

    public StdDeviation(IBaseChart c) {
        super(c);
    }

    private void accumulate(double value) {
        iSum += value;
        iSum2 += (value * value);
    }

    private double calculateDeviation() {
        double divisor = complete ? (iNumPoints * iNumPoints) :
                         iNumPoints * (iNumPoints - 1);
        double result = ((iNumPoints * iSum2) - (iSum * iSum)) / divisor;
        if (result < 0) {
            result = 0; // MS fix
        }
        return Math.sqrt(result);
    }

    /**
     * Enables complete StdDeviation of input Series.<br>
     * Default value: false
     *
     * @return boolean
     */
    public boolean getComplete() {
        return complete;
    }

    /**
     * Enables complete StdDeviation of input Series.<br>
     * Default value: false
     *
     * @param value boolean
     */
    public void setComplete(boolean value) {
        if (complete != value) {
            complete = value;
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
    public double calculate(Series s, int firstIndex, int lastIndex) {
        if (firstIndex == -1) {
            firstIndex = 0;
            iNumPoints = s.getCount();
            lastIndex = iNumPoints - 1;
        } else {
            iNumPoints = lastIndex - firstIndex + 1;
        }

        if (iNumPoints > 1) {
            iSum2 = 0;
            iSum = 0;
            ValueList v = valueList(s);
            for (int t = firstIndex; t <= lastIndex; t++) {
                accumulate(v.value[t]);
            }
            return calculateDeviation();
        } else {
            return 0;
        }
    }

    /**
     * Performs function operation on list of series (SourceSeriesList).<BR>
     * The ValueIndex parameter defines ValueIndex of point in each Series in
     * list.<br>
     * You can override CalculateMany function to perform customized
     * calculation on list of SourceSeries. <br>
     *
     * @param sourceSeries ArrayList
     * @param valueIndex int
     * @return double
     */
    public double calculateMany(ArrayList sourceSeries, int valueIndex) {
        if (!sourceSeries.isEmpty()) {
            iNumPoints = 0;
            iSum2 = 0;
            iSum = 0;
            for (int t = 0; t < sourceSeries.size(); t++) {
                ValueList v = valueList((Series) sourceSeries.get(t));
                if (v.count > valueIndex) {
                    accumulate(v.value[valueIndex]);
                    iNumPoints++;
                }
            }
            return (iNumPoints > 1) ? calculateDeviation() : 0;
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
        return Language.getString("FunctionStdDeviation");
    }
}

