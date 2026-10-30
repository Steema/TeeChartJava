package com.steema.teechart.styles;

import com.steema.teechart.misc.Enum;

/**
 *
 * <p>Title: MultiAreas class</p>
 *
 * <p>Description: Describes the possible values of Area.MultiArea.</p>
 *
 * <p>Copyright (c) 2005-2019 by Steema Software SL. All Rights Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 *
 * @see com.steema.teechart.styles.Area#getMultiArea
 */
final public class MultiAreas extends Enum {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
     * Areas will be drawn one behind the other.
     */
    public static final MultiAreas NONE = new MultiAreas(0);
    /**
     * Draws each Area on top of previous one.
     */
    public static final MultiAreas STACKED = new MultiAreas(1);
    /**
     * Adjusts each individual point to a common 0..100 axis scale.
     */
    public static final MultiAreas STACKED100 = new MultiAreas(2);

    private MultiAreas(int value) {
        super(value);
    }
}

