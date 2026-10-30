package com.steema.teechart.functions;


import com.steema.teechart.languages.Language;
import com.steema.teechart.styles.ValueList;
import com.steema.teechart.IBaseChart;
import com.steema.teechart.styles.Series;

/**
 *
 * <p>Title: ExpMovAverage class</p>
 *
 * <p>Description: Exponential Moving Average Function</p>
 *
 * <p>Copyright (c) 2005-2019 by Steema Software SL. All Rights
 * Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 *
 */
public class ExpMovAverage extends Function {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ExpMovAverage() {
        this((IBaseChart)null);
    }

    public ExpMovAverage(IBaseChart c) {
        super(c);
        canUsePeriod = false;
        SingleSource=true;
        dPeriod = 10;
    }

    protected void doCalculation(Series source,
                                 ValueList notMandatorySource) {

        getSeries().clear();

        if ((getPeriod() > 0) && (source.getCount() > 1)) {
            ValueList tmpV = valueList(source);
            double tmp = 2 / (getPeriod() + 1);
            double old = tmpV.value[0];
            getSeries().add(notMandatorySource.value[0], old);
            for (int t = 1; t < source.getCount(); t++) {
                old = (tmpV.value[t] * tmp) + (old * (1 - tmp));
                getSeries().add(notMandatorySource.value[t], old);
            }
        }
    }

    /**
     * Gets descriptive text.
     *
     * @return String
     */
    public String getDescription() {
        return Language.getString("FunctionExpMovAve");
    }
}

