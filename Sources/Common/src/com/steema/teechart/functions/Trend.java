package com.steema.teechart.functions;


import com.steema.teechart.languages.Language;
import com.steema.teechart.IBaseChart;

/**
 *
 * <p>Title: Trend class</p>
 *
 * <p>Description: Trendline function.</p>
 *
 * <p>Copyright (c) 2005-2019 by Steema Software SL. All Rights
 * Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 *
 */
public class Trend extends BaseTrend {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Trend() {
        this((IBaseChart)null);
    }

    public Trend(IBaseChart c) {
        super(c);
    }

    /**
     * Gets descriptive text.
     *
     * @return String
     */
    public String getDescription() {
        return Language.getString("FunctionTrend");
    }

}

