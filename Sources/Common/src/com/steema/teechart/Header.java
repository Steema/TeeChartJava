package com.steema.teechart;

import com.steema.teechart.drawing.Color;

/**
 *
 * <p>Title: Header class</p>
 *
 * <p>Description: Text displayed above Chart</p>
 *
 * <p>Copyright (c) 2005-2019 by Steema Software SL. All Rights
 * Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 *
 */
public class Header extends Title {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Header(IBaseChart c) {
        super(c);
        getFont().getBrush().setDefaultColor(Color.BLUE);
    }
}

