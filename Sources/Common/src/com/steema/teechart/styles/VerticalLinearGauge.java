package com.steema.teechart.styles;

import com.steema.teechart.IBaseChart;
import com.steema.teechart.languages.Language;

/**
 *
 * <p>Title: LinearGauge class</p>
 *
 * <p>Description: LinearGauge Series.</p>
 *
 * <p>Copyright (c) 2005-2019 by Steema Software SL. All Rights Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 *
 */
public class VerticalLinearGauge extends LinearGauge {
	
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	public VerticalLinearGauge(IBaseChart c) {
        super(c); 
        super.setHorizontal(false);
    }

    public VerticalLinearGauge() {
        this((IBaseChart)null);
    }

      
    public String getDescription() {
        return Language.getString("GalleryVerticalGauge");
    }

}
