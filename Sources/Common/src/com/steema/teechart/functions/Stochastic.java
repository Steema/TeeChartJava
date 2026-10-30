package com.steema.teechart.functions;


import com.steema.teechart.languages.Language;
import java.util.ArrayList;
import com.steema.teechart.styles.ValueList;
import com.steema.teechart.IBaseChart;
import com.steema.teechart.styles.Series;
import com.steema.teechart.styles.OHLC;

/**
 *
 * <p>Title: Stochastic class</p>
 *
 * <p>Description: Stochastic Function.</p>
 *
 * <p>Copyright (c) 2005-2019 by Steema Software SL. All Rights
 * Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 *
 */
public class Stochastic extends Moving {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	double[] nums, dens;

    public Stochastic() {
        this((IBaseChart)null);
    }

    public Stochastic(IBaseChart c) {
        super(c);
        SingleSource=true;
        HideSourceList=true;
    }

    /**
     * Gets all points from Source series, performs a function operation on
     * points and stores results in ParentSeries.<br>
     *
     * @param source ArrayList
     */
    public void addPoints(ArrayList source) {
        nums = new double[((Series) source.get(0)).getCount()];
        dens = new double[((Series) source.get(0)).getCount()];
        super.addPoints(source);
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
        double result = 0;
        if (s instanceof OHLC)
        {
            ValueList lows = ((OHLC) s).getLowValues();
            ValueList highs = ((OHLC) s).getHighValues();
            double tmpLow = lows.value[firstIndex];
            double tmpHigh = highs.value[firstIndex];

            for (int t = firstIndex; t <= lastIndex; t++) {
                if (lows.value[t] < tmpLow) {
                    tmpLow = lows.value[t];
                }
                if (highs.value[t] > tmpHigh) {
                    tmpHigh = highs.value[t];
                }
            }
            nums[lastIndex] = valueList(s).value[lastIndex] - tmpLow;
            dens[lastIndex] = tmpHigh - tmpLow;

            if (tmpHigh != tmpLow) {
                result = 100.0 * (nums[lastIndex] / dens[lastIndex]);
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
        return Language.getString("FunctionStochastic");
    }
}

