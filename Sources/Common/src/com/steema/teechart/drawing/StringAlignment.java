package com.steema.teechart.drawing;

import com.steema.teechart.misc.Enum;

/**
 * <p>Title: StringAlignment class</p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright (c) 2005-2019 by Steema Software SL. All Rights
 * Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 *
 */
final public class StringAlignment extends Enum {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	static public final StringAlignment NEAR = new StringAlignment(0);
    static public final StringAlignment CENTER = new StringAlignment(1);
    static public final StringAlignment FAR = new StringAlignment(2);

    private StringAlignment(int value) {
        super(value);
    }
    
    public StringAlignment() {
        super(0);
    }

    public static StringAlignment fromValue(int v) {
        switch (v) {
        case 0:
            return NEAR;
        case 1:
            return CENTER;
        default:
            return FAR;

        }
    }
}

