package com.steema.teechart.styles;

import com.steema.teechart.misc.Enum;

/**
 *
 * <p>Title: PieOtherStyles class</p>
 *
 * <p>Description: Describes the possible values of the Style method of
 * Pie.PieOtherSlice class.</p>
 *
 * <p>Copyright (c) 2005-2019 by Steema Software SL. All Rights Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 *
 *
 * @see com.steema.teechart.styles.Pie.PieOtherSlice
 */
final public class PieOtherStyles extends Enum {
    /**
     * No "Other" slice is generated. (default)
     */
    public static final PieOtherStyles NONE = new PieOtherStyles(0);
    /**
     * Slices with values below a percentage are grouped.
     */
    public static final PieOtherStyles BELOWPERCENT = new PieOtherStyles(1);
    /**
     * Slices with values below a value are grouped.
     */
    public static final PieOtherStyles BELOWVALUE = new PieOtherStyles(2);

    private PieOtherStyles(int value) {
        super(value);
    }
}

