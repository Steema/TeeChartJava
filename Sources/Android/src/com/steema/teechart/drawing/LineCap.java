package com.steema.teechart.drawing;

import com.steema.teechart.misc.Enum;

/**
 * <p>Title: LineCap class</p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright (c) 2005-2019 by Steema Software SL. All Rights Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 *
 */
final public class LineCap extends Enum {

    /**
     * Defines a beveled union style between line segments
     */
    public static final LineCap BEVEL = new LineCap(0);

    /**
     * Defines a miter union style between line segments
     */
    public static final LineCap MITER = new LineCap(1);

    /**
     * Defines a round union style between line segments
     */
    public static final LineCap ROUND = new LineCap(2);

    private LineCap(int value) {
        super(value);
    }

    static public LineCap fromValue(int value) {
        switch (value) {
        case 0:
            return BEVEL;
        case 1:
            return MITER;
        default:
            return ROUND;
        }
    }
}

