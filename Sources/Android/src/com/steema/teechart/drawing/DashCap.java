package com.steema.teechart.drawing;

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

    public static final DashCap FLAT = new DashCap(0);
    public static final DashCap ROUND = new DashCap(1);
    public static final DashCap SQUARE = new DashCap(2);

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

