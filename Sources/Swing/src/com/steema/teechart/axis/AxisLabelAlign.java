package com.steema.teechart.axis;

import com.steema.teechart.misc.Enum;

/**
 * determines the position of Labels on an Axis.
 *
 * <p>Title: AxisLabelAlign class</p>
 *
 * <p>Description: Sets position of Labels on an Axis.</p>
 *
 * <p>Copyright (c) 2005-2019 by Steema Software SL. All Rights Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 *
 */
public final class AxisLabelAlign extends Enum {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
     * Default position.
     */
    final public static AxisLabelAlign DEFAULT = new AxisLabelAlign();

    /**
     * Labels aligned opposite the default position.
     */
    final public static AxisLabelAlign OPPOSITE = new AxisLabelAlign();
    
    /**
     * Labels centered on the default position.
     */
    final public static AxisLabelAlign CENTERED = new AxisLabelAlign();

    private AxisLabelAlign() {
        super();
    }
}

