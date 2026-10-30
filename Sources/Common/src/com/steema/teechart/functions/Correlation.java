package com.steema.teechart.functions;


import com.steema.teechart.languages.Language;
import com.steema.teechart.styles.Series;

/**
 *
 * <p>Title: Correlation class</p>
 *
 * <p>Description: Correlation function</p>
 *
 * <p>Copyright (c) 2005-2019 by Steema Software SL. All Rights
 * Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 *
 */
public class Correlation extends BaseTrend {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private void addPoint(double x, double y) {
        if (getSeries().getYMandatory()) {
            getSeries().add(x, y);
        } else {
            getSeries().add(y, x);
        }
    }

    public double calculate(Series s, int firstIndex,
                            int lastIndex) {
        return coefficient(s, firstIndex, lastIndex);
    }

    protected void calculatePeriod(Series source, double tmpX,
                                   int firstIndex, int lastIndex) {
        double tmp = calculate(source, firstIndex, lastIndex);
        if (getSeries().getAllowSinglePoint()) {
            getSeries().add(tmp);
        } else {
            addPoint(source.getNotMandatory().getMinimum(), tmp);
            addPoint(source.getNotMandatory().getMaximum(), tmp);
        }
    }

    /**
     * Gets descriptive text.
     *
     * @return String
     */
    public String getDescription() {
        return Language.getString("FunctionCorrelation");
    }
}

