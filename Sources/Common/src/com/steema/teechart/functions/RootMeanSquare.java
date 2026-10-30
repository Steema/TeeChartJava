package com.steema.teechart.functions;


import com.steema.teechart.languages.Language;
import java.util.ArrayList;
import com.steema.teechart.styles.ValueList;
import com.steema.teechart.IBaseChart;
import com.steema.teechart.styles.Series;

/**
 *
 * <p>Title: RootMeanSquare class</p>
 *
 * <p>Description: Root Mean Square Function.</p>
 *
 * <p>Copyright (c) 2005-2019 by Steema Software SL. All Rights
 * Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 *
 */
public class RootMeanSquare extends Function {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private boolean complete;
    private int iNumPoints;
    private double iSum2;

    public RootMeanSquare() {
        this((IBaseChart)null);
    }

    public RootMeanSquare(IBaseChart c) {
        super(c);
    }

    /**
     * Defines the RMS formula.  <br><br>
     * RMS = Sqrt(Sum(Sqr(x[i]))/NumPoints)        ...     Complete = true <br>
     * RMS = Sqrt(Sum(Sqr(x[i]))/(NumPoints-1))     ...     Complete = false<br>
     * Default value: false
     *
     * @return boolean
     */
    public boolean getComplete() {
        return complete;
    }

    /**
     * Defines the RMS formula.  <br><br>
     * Default value: false
     *
     * @see RootMeanSquare#getComplete
     * @param value boolean
     */
    public void setComplete(boolean value) {
        if (complete != value) {
            complete = value;
            recalculate();
        }
    }

    private void accumulate(double value) {
        iSum2 += (value * value);
    }

    private double calculateRMS() {
        // safeguard against only one point
        return Math.sqrt(iSum2 / (complete ? iNumPoints : iNumPoints - 1));
    }

    /**
     * Performs function operation on SourceSeries series.<br>
     * First and Last parameters are ValueIndex of first and last point used in
     * calculation. <br>
     * You can override Calculate function to perform customized calculation on
     * one SourceSeries. <br>
     *
     * @param s Series
     * @param firstIndex int
     * @param lastIndex int
     * @return double
     */
    public double calculate(Series s, int firstIndex,
                            int lastIndex) {
        if (firstIndex == -1) {
            firstIndex = 0;
            iNumPoints = s.getCount();
            lastIndex = iNumPoints - 1;
        } else {
            iNumPoints = lastIndex - firstIndex + 1;
        }

        if (iNumPoints > 1) {
            iSum2 = 0;
            ValueList v = valueList(s);
            for (int t = firstIndex; t <= lastIndex; t++) {
                accumulate(v.value[t]);
            }
            return calculateRMS();
        } else {
            return 0;
        }
    }

    /**
     * Performs function operation on list of series (SourceSeriesList).<br>
     * The ValueIndex parameter defines ValueIndex of point in each Series in
     * list. <br>
     * You can override CalculateMany function to perform customized
     * calculation on list of SourceSeries. <br>
     *
     * @param sourceSeries ArrayList
     * @param valueIndex int
     * @return double
     */
    public double calculateMany(ArrayList sourceSeries, int valueIndex) {
        if (sourceSeries.size() > 0) {
            iNumPoints = 0;
            iSum2 = 0;
            for (int t = 0; t < sourceSeries.size(); t++) {
                ValueList v = valueList((Series) sourceSeries.get(t));
                if (v.count > valueIndex) {
                    accumulate(v.value[valueIndex]);
                    iNumPoints++;
                }
            }

            return (iNumPoints > 1) ? calculateRMS() : 0;

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
        return Language.getString("FunctionRMS");
    }
}

