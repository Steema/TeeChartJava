package com.steema.teechart.styles;

import com.steema.teechart.misc.Enum;

/**
 *
 * <p>Title: SphereStyle class</p>
 *
 * <p>Description: Enhanced Sphere appearance styles</p>
 *
 * <p>Copyright (c) 2005-2019 by Steema Software SL. All Rights
 * Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 */
final public class SphereStyle extends Enum {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final SphereStyle NORMAL = new SphereStyle(0);
    public static final SphereStyle SPHERE = new SphereStyle(1);
    public static final SphereStyle POLISHED = new SphereStyle(2);

    private SphereStyle(int value) {
        super(value);
    }
}

