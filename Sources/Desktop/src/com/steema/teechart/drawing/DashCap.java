package com.steema.teechart.drawing;

import java.awt.BasicStroke;
import com.steema.teechart.misc.Enum;

/**
 * <p>Title: DashCap class</p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright (c) 2005-2019 by Steema Software SL. All Rights Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 *
 */
final public class DashCap extends Enum {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final DashCap FLAT = new DashCap(BasicStroke.CAP_BUTT);
    public static final DashCap ROUND = new DashCap(BasicStroke.CAP_ROUND);
    public static final DashCap SQUARE = new DashCap(BasicStroke.CAP_SQUARE);

    private DashCap(int value) {
        super(value);
    }

    static public DashCap fromValue(int value) {
        switch (value) {
        case 0:
            return DashCap.FLAT;
        case 1:
            return DashCap.ROUND;
        default:
            return DashCap.SQUARE;
        }
    }
}

