package com.steema.teechart.styles;

import com.steema.teechart.misc.Enum;

/**
 *
 * <p>Title: SurfaceStyle class</p>
 *
 * <p>Description: Reserved for future use.</p>
 *
 * <p>Copyright (c) 2005-2019 by Steema Software SL. All Rights
 * Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 *
 */
final public class SurfaceStyle extends Enum {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final SurfaceStyle SOLID = new SurfaceStyle(0);
    public static final SurfaceStyle WIRE = new SurfaceStyle(1);
    public static final SurfaceStyle DOT = new SurfaceStyle(2);

    private SurfaceStyle(int value) {
        super(value);
    }
}

