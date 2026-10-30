package com.steema.teechart;

import com.steema.teechart.misc.Enum;

/**
 * <p>Title: TextFormat</p>
 *
 * <p>Description: TeeChart for Java</p>
 *
 * <p>Copyright (c) 2004-2019 by Steema Software SL. All Rights
 * Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 *
 */

final public class TextFormat extends Enum {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
     * Defines a NORMAL format.
     */
    public static final TextFormat NORMAL = new TextFormat(0);
    /**
     * Defines a HTML  format.
     */
    public static final TextFormat HTML = new TextFormat(1);

    private TextFormat(int value) {
        super(value);
    }
}

