package com.steema.teechart.styles;

import com.steema.teechart.misc.Enum;

/**
 *
 * <p>Title: HandStyle clas</p>
 *
 * <p>Description: Describes the possible values of Gauges.HandStyle.</p>
 *
 * <p>Copyright (c) 2005-2019 by Steema Software SL. All Rights
 * Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 *
 * @see com.steema.teechart.styles.Gauges#getHandStyle
 */
final public class HandStyle extends Enum {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
     * Gauge hand drawn as a Line.
     */
    public static final HandStyle LINE = new HandStyle(0);
    /**
     * Gauge hand drawn as a triangular pointer.
     */
    public static final HandStyle TRIANGLE = new HandStyle(1);

    private HandStyle(int value) {
        super(value);
    }
};

