package com.steema.teechart.styles;

import com.steema.teechart.drawing.Color;
import com.steema.teechart.IBaseChart;
import com.steema.teechart.TextShape;

/**
 *
 * <p>Title: MarksItem class</p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright (c) 2005-2019 by Steema Software SL. All Rights
 * Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 *
 */
public class MarksItem extends TextShape {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public final static Color MARKCOLOR = Color.LIGHT_YELLOW;

    public MarksItem() {
        this((IBaseChart)null);
    }

    public MarksItem(IBaseChart c) {
        super(c);
        setColor(MARKCOLOR);
    }
}



