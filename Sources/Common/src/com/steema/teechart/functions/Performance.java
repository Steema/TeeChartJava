package com.steema.teechart.functions;


import com.steema.teechart.languages.Language;
import com.steema.teechart.styles.ValueList;
import com.steema.teechart.IBaseChart;
import com.steema.teechart.styles.Series;

/**
 *
 * <p>Title: PerformanceFunction class</p>
 *
 * <p>Description: Performance Function.</p>
 *
 * <p>Copyright (c) 2005-2019 by Steema Software SL. All Rights
 * Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 *
 */
public class Performance extends Moving {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Performance() {
        this((IBaseChart)null);
    }

    public Performance(IBaseChart c) {
        super(c);
    }

    public double calculate(Series s, int firstIndex,
                            int lastIndex) {
        if (firstIndex == -1) {
            lastIndex = s.getCount() - 1;
        }
        ValueList v = valueList(s);
        if (v.value[0] != 0) {
            return (v.value[lastIndex] - v.value[0]) * 100.0 / v.value[0];
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
        return Language.getString("FunctionPerf");
    }
}

