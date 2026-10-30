package com.steema.teechart.functions;

import com.steema.teechart.styles.ValueList;
import com.steema.teechart.IBaseChart;
import com.steema.teechart.styles.Series;
import com.steema.teechart.misc.Utils;

/**
 *
 * <p>Title: Moving class</p>
 *
 * <p>Description: Internal use. Base class for moving calculation functions</p>
 *
 * <p>Copyright (c) 2005-2019 by Steema Software SL. All Rights
 * Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 *
 */
public class Moving extends Function {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Moving() {
        this(null);
    }

    public Moving(IBaseChart c) {
        super(c);
        dPeriod = 1;
    }

    protected void doCalculation(Series source,
                                 ValueList notMandatorySource) {
        int p = Utils.round(dPeriod);

        for (int t = p - 1; t < source.getCount(); t++) {
            addFunctionXY(source.getYMandatory(), notMandatorySource.value[t],
                          calculate(source, t - p + 1, t));
        }
    }
}

