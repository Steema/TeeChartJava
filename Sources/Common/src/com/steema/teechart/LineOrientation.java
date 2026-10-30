package com.steema.teechart;

import com.steema.teechart.misc.Enum;

/**
 *
 * <p>Title: LineOrientation class.</p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright (c) 2005-2019 by Steema Software SL. All Rights
 * Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 */
final public class LineOrientation extends Enum {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final LineOrientation POINT = new LineOrientation(0);
    public static final LineOrientation HORIZONTAL = new LineOrientation(1);
    public static final LineOrientation VERTICAL = new LineOrientation(2);

    private LineOrientation(int value) {
        super(value);
    }
}

