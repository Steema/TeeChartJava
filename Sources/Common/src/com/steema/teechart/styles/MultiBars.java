package com.steema.teechart.styles;

import com.steema.teechart.misc.Enum;

/**
 *
 * <p>Title: MultiBars class</p>
 *
 * <p>Description: Describes the possible values of CustomBar.MultiBar.</p>
 *
 * <p>Copyright (c) 2005-2019 by Steema Software SL. All Rights Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 *
 * <p>Example:
 * <pre><font face="Courier" size="4">
 * bar1Series.setMultiBar(MultiBars.SIDEALL);
 *  or
 * barSeries.setMultiBar(MultiBars.SELFSTACK);
 * </font></pre></p>
 *
 * @see com.steema.teechart.styles.CustomBar#getMultiBar
 */
final public class MultiBars extends Enum {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
     * Bar series are placed one behind the other.
     */
    public static final MultiBars NONE = new MultiBars(0);
    /**
     * Bar series points are placed one beside the other.
     */
    public static final MultiBars SIDE = new MultiBars(1);
    /**
     * Bar series are placed one on top of the other.
     */
    public static final MultiBars STACKED = new MultiBars(2);
    /**
     * Bar series are placed one on top of the other against a common axis
     * scale 0..100.
     */
    public static final MultiBars STACKED100 = new MultiBars(3);
    /**
     * Bar series, that is, all series points, are drawn one beside the other.
     */
    public static final MultiBars SIDEALL = new MultiBars(4);
    /**
     * The points of each bar series are drawn one on top of the other.
     */
    public static final MultiBars SELFSTACK = new MultiBars(5);

    private MultiBars(int value) {
        super(value);
    }

	public static MultiBars fromValue(int value) {
		switch (value) {
		case 0: return NONE;
		case 1: return SIDE;
		case 2: return STACKED;
		case 3: return STACKED100;
		case 4: return SIDEALL;
		default : return SELFSTACK;
		}
	}
}

