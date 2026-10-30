package com.steema.teechart;

import com.steema.teechart.drawing.Color;

/**
 *
 * <p>Title: Footer class</p>
 *
 * <p>Description: Text displayed below Chart</p>
 *
 * <p>Copyright (c) 2005-2019 by Steema Software SL. All Rights Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 *
 */
public class Footer extends Title {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Footer(IBaseChart c) {
        super(c);
        getFont().getBrush().setDefaultColor(Color.RED);
        onTop = false;
    }

    protected Object readResolve() {
        onTop=false;
        return this;
    }

}

