package com.steema.teechart.styles;

import com.steema.teechart.misc.Enum;

/**
 *
 * <p>Title: TreatNullsStyle class</p>
 *
 * <p>Description: Defines how to treat null points.</p>
 *
 * <p>Copyright (c) 2005-2019 by Steema Software SL. All Rights Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 *
 * @see Custom#getTreatNulls()
 * @see CustomErrorPoint3D#getTreatNulls()
 * @see CustomPoint#getTreatNulls()
 * @see CustomPolar#getTreatNulls()
 * @see FastLine#getTreatNulls()
 * @see OHLC#getTreatNulls()
 * @see Points3D#getTreatNulls()
 */
final public class TreatNullsStyle extends Enum {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
     * Null points are not painted, but they are still used in other calculations.
     */
    public static final TreatNullsStyle DONOTPAINT = new TreatNullsStyle(0);
    /**
     * Null points are skipped in drawing and calculations.
     */
    public static final TreatNullsStyle SKIP = new TreatNullsStyle(1);
    /**
     * Null points are completely ignored.
     */
    public static final TreatNullsStyle IGNORE = new TreatNullsStyle(2);

    private TreatNullsStyle(int value) {
        super(value);
    }

    static public TreatNullsStyle fromInt(int value) {
        switch (value) {
        case 0:
            return DONOTPAINT;
        case 1:
            return SKIP;
        case 2:
            return IGNORE;
        default:
            return DONOTPAINT;

        }
    }
}

