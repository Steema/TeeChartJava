package com.steema.teechart.functions;

import com.steema.teechart.misc.Enum;

/**
 *
 * <p>Title: DownSamplingMethod class</p>
 *
 * <p>Description: Defines type of downsampling method.</p>
 *
 * <p>Copyright (c) 2005-2019 by Steema Software SL. All Rights
 * Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 *
 */
final public class DownSamplingMethod extends Enum {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
     * Replace group of points with group maximum value.
     */
    public static final DownSamplingMethod MAX = new DownSamplingMethod(0);
    /**
     * Replace group of points with group minimum value.
     */
    public static final DownSamplingMethod MIN = new DownSamplingMethod(1);
    /**
     * Replace group of points with two points : group minimum and maximum
     * value.
     */
    public static final DownSamplingMethod MINMAX = new DownSamplingMethod(2);
    /**
     * Replace group of points with two points : group minimum and maximum value. Draw
     * From Last value of one group to the First value of the next. Only include the first
     * null of the group in the calculation but without plotting it.
     */
    public static final DownSamplingMethod MINMAXFIRSTLAST = new DownSamplingMethod(3);
    /**
     * Replace group of points with two points : group minimum and maximum value. Draw
     * From Last value of one group to the First value of the next. Only include the first
     * null of the group in the calculation but without plotting it.
     */
    public static final DownSamplingMethod MINMAXFIRSTLASTNULL = new DownSamplingMethod(4);
    /**
     * Replace group of points with group average value.
     */
    public static final DownSamplingMethod AVERAGE = new DownSamplingMethod(5);

    private DownSamplingMethod(int value) {
        super(value);
    }
}

