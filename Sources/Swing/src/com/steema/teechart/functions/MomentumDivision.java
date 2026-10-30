package com.steema.teechart.functions;


import com.steema.teechart.languages.Language;
import com.steema.teechart.styles.ValueList;
import com.steema.teechart.styles.Series;
import com.steema.teechart.IBaseChart;

/**
 *
 * <p>Title: MomentumDivision</p>
 *
 * <p>Description: Momentum Division Function.</p>
 *
 * <p>Copyright (c)
 * 2005-2019 by Steema Software SL. All Rights
 * Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 *
 */
public class MomentumDivision extends Moving {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public MomentumDivision() {
        this((IBaseChart)null);
    }

    public MomentumDivision(IBaseChart c) {
        super(c);
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
        if (firstIndex == -1) {
            firstIndex = 0;
            lastIndex = s.getCount() - 1;
        }
        ValueList v = valueList(s);
        return (v.value[firstIndex] == 0) ? 0 :
                100 * v.value[lastIndex] / v.value[firstIndex];
    }

    /**
     * Gets descriptive text.
     *
     * @return String
     */
    public String getDescription() {
        return Language.getString("FunctionMomentumDiv");
    }
}

