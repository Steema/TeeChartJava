package com.steema.teechart.tools;

import com.steema.teechart.misc.Enum;

/**
 *
 * <p>Title: AxisArrowPosition class</p>
 *
 * <p>Description: Describes the possible values of the AxisArrow.Position
 * method.</p>
 *
 * <p>Copyright (c) 2005-2019 by Steema Software SL. All Rights Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 *
 * @see com.steema.teechart.tools.AxisArrow#getPosition
 */
final public class AxisArrowPosition extends Enum {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
     * Draws an arrow at the beginning of the Axis.
     */
    public static final AxisArrowPosition START = new AxisArrowPosition(0);

    /**
     * Draws an arrow at the end of the Axis.
     */
    public static final AxisArrowPosition END = new AxisArrowPosition(1);

    /**
     * Draws arrows at both ends of the Axis.
     */
    public static final AxisArrowPosition BOTH = new AxisArrowPosition(2);

    private AxisArrowPosition(int value) {
        super(value);
    }
}

