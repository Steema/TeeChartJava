package com.steema.teechart.functions;

import com.steema.teechart.styles.ValueList;
import java.util.ArrayList;
import com.steema.teechart.styles.Series;

/**
 *
 * <p>Title: ManySeries class</p>
 *
 * <p>Description: Internal use. Base class for multiple Series function
 * calculations</p>
 *
 * <p>Copyright (c) 2005-2019 by Steema Software SL. All Rights
 * Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 *
 */
public class ManySeries extends Function {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	protected double calculateValue(double result, double value) {
        return 0; // abstract
    }


    /**
     * Performs function operation on list of series (SourceSeriesList).<br>
     * The ValueIndex parameter defines ValueIndex of point in each Series
     * in list. <br>
     * You can override CalculateMany function to perform customized
     * calculation on list of SourceSeries. <br>
     *
     * @param sourceSeries ArrayList
     * @param valueIndex int
     * @return double
     */
    public double calculateMany(ArrayList sourceSeries,
                                int valueIndex) {
        ValueList v;
        boolean tmpFirst = true;
        double result = 0;
        for (int t = 0; t < sourceSeries.size(); t++) {
            v = valueList((Series) (sourceSeries.get(t)));
            if (v.count > valueIndex) {
                if (tmpFirst) {
                    result = v.value[valueIndex];
                    tmpFirst = false;
                } else {
                    result = calculateValue(result, v.value[valueIndex]);
                }
            }
        }
        return result;
    }
}

