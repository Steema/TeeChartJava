package com.steema.teechart;

import com.steema.teechart.misc.Enum;

/**
 *
 * <p>Title: ScrollMode class</p>
 *
 * <p>Description: Describes the possible values of the Scroll.Allow
 * property.</p>
 *
 * <p>Copyright (c) 2005-2019 by Steema Software SL. All Rights
 * Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 */
final public class ScrollMode extends Enum {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
     * Denies scrolling.
     */
    public static final ScrollMode NONE = new ScrollMode(0);
    /**
     * Allows only Horizontal Scrolling.
     */
    public static final ScrollMode VERTICAL = new ScrollMode(1);
    /**
     * Allows only Vertical Scrolling.
     */
    public static final ScrollMode HORIZONTAL = new ScrollMode(2);
    /**
     * Allows complete Horizontal and Vertical Scrolling.
     */
    public static final ScrollMode BOTH = new ScrollMode(3);

    private ScrollMode(int value) {
        super(value);
    }
}

