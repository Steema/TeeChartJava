package com.steema.teechart.functions;


import com.steema.teechart.misc.Enum;
/**
 *
 * <p>Title: RSI class</p>
 *
 * <p>Description: Defines RSI calculation styles..</p>
 *
 * <p>Copyright (c) 2005-2019 by Steema Software SL. All Rights
 * Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 */
final public class RSIStyle extends Enum {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
     * Use Open/Close values
     */
    public static final  RSIStyle OPENCLOSE = new RSIStyle(0);

    /**
     * Use Close values
     */
    public static final  RSIStyle CLOSE = new RSIStyle(1);

    private RSIStyle(int value) {
        super(value);
    }
}

