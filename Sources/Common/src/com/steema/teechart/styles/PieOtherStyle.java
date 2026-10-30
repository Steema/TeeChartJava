package com.steema.teechart.styles;

import com.steema.teechart.misc.Enum;

/**
 *
 * <p>Title: PieOtherStyle class</p>
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
final public class PieOtherStyle extends Enum {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
     * No "Other" slice is generated. (default)
     */
    public static final PieOtherStyle NONE = new PieOtherStyle(0);
    /**
     * Slices with values below a percentage are grouped.
     */
    public static final PieOtherStyle BELOWPERCENT = new PieOtherStyle(1);
    /**
     * Slices with values below a value are grouped.
     */
    public static final PieOtherStyle BELOWVALUE = new PieOtherStyle(2);

    private PieOtherStyle(int value) {
        super(value);
    }
    
    static public PieOtherStyle fromInt(int value) {
      switch(value) {
       case 0: return NONE;
       case 1: return BELOWPERCENT;
       default: return BELOWVALUE;
      }
    }
}

