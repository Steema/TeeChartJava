package com.steema.teechart;

import com.steema.teechart.misc.Enum;

/**
 *
 * <p>Title: PositionUnits class</p>
 *
 * <p>Description: Defines axis Position units (pixels or percentage)</p>
 *
 * <p>Copyright (c) 2005-2019 by Steema Software SL. All Rights
 * Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 *
 */
final public class PositionUnits extends Enum {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
     * Units as a percentage of horizontal or vertical chart size.
     */
    public static final PositionUnits PERCENT = new PositionUnits(0);
    /**
     * Units as pixels.
     */
    public static final PositionUnits PIXELS = new PositionUnits(1);

    private PositionUnits(int value) {
        super(value);
    }
}

