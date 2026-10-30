package com.steema.teechart.tools;

import com.steema.teechart.misc.Enum;

/**
 *
 * <p>Title: DrawLineHandle class</p>
 *
 * <p>Description: Placement of Line handles for <see cref="DrawLine"/></p>
 *
 * <p>Copyright (c) 2005-2019 by Steema Software SL. All Rights Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 *
 */
final public class DrawLineHandle extends Enum {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final DrawLineHandle NONE = new DrawLineHandle(0);
    public static final DrawLineHandle START = new DrawLineHandle(1);
    public static final DrawLineHandle END = new DrawLineHandle(2);
    public static final DrawLineHandle SERIES = new DrawLineHandle(3);

    private DrawLineHandle(int value) {
        super(value);
    }
}


