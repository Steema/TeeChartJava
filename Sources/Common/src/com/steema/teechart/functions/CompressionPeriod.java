package com.steema.teechart.functions;

import com.steema.teechart.misc.Enum;

/**
 *
 * <p>Title: CompressionPeriod class</p>
 *
 * <p>Description: Describes the possible values of
 * CompressOHLC.Compress.</p>
 *
 * <p>Copyright (c) 2005-2019 by Steema Software SL. All Rights
 * Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 *
 */
final public class CompressionPeriod extends Enum {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
     * Day.
     */
    public static final CompressionPeriod DAY = new CompressionPeriod(0);
    /**
     * Week.
     */
    public static final CompressionPeriod WEEK = new CompressionPeriod(1);
    /**
     * Month
     */
    public static final CompressionPeriod MONTH = new CompressionPeriod(2);
    /**
     * BiMonthly.
     */
    public static final CompressionPeriod BIMONTH = new CompressionPeriod(3);
    /**
     * Quarter.
     */
    public static final CompressionPeriod QUARTER = new CompressionPeriod(4);
    /**
     * Year.
     */
    public static final CompressionPeriod YEAR = new CompressionPeriod(5);

    private CompressionPeriod(int value) {
        super(value);
    }
};

