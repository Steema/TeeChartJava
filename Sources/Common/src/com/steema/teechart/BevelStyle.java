package com.steema.teechart;

import com.steema.teechart.misc.Enum;

/**
 *
 * <p>Title: BevelStyle class</p>
 *
 * <p>Description: Defines the styles of the bevels (frames) around rectangles.</p>
 *
 * <p>Copyright (c) 2005-2019 by Steema Software SL. All Rights Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 */
final public class BevelStyle extends Enum {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
     * No bevel.
     */
    public static final BevelStyle NONE = new BevelStyle(0);

    /**
     * Lowered Bevel.
     */
    public static final BevelStyle LOWERED = new BevelStyle(1);

    /**
     * Raised bevel.
     */
    public static final BevelStyle RAISED = new BevelStyle(2);

    private BevelStyle(int value) {
        super(value);
    }

    static public BevelStyle fromValue(int value) {
        switch (value) {
        case 0:
            return NONE;
        case 1:
            return LOWERED;
        default:
            return RAISED;
        }
    }
}

