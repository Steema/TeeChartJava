package com.steema.teechart;

import com.steema.teechart.misc.Enum;

/**
 *
 * <p>Title: DateTimeStep class</p>
 *
 * <p>Description: Describes a number of different DateTime steps.</p>
 *
 * <p>Copyright (c) 2005-2019 by Steema Software SL. All Rights
 * Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 */
final public class DateTimeStep extends Enum {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
     * Array of double values to define DateTimeStep.
     */
    final static public double[] DAYSTEP = {1.0 / (1000000.0 * 86400.0),
    									1.0 / (1000.0 * 86400.0),
                                        1.0 / 86400.0, 5.0 / 86400.0,
                                        10.0 / 86400.0,
                                        0.25 / 1440.0, 0.5 / 1440.0,
                                        1.0 / 1440.0,
                                        5.0 / 1440.0, 10.0 / 1440.0,
                                        0.25 / 24.0,
                                        0.5 / 24.0, 1.0 / 24.0, 2.0 / 24.0,
                                        6.0 / 24.0,
                                        12.0 / 24.0, 1, 2, 3, 7, 15, 30, 60,
                                        90,
                                        120, 182, 365, /*//MMnone:*/ 1};

    //Microsecond step
    final static public double[] STEP = {1.0 / 1000, //1 microsec
    								1.0, //1 millisec
                                    1000.0, 5000.0,
                                    10000.0, //10 secs
                                    15000.0,
                                    30000.0,
                                    60000.0, //1 min
                                    5.0 * 60000.0, //5 mins
                                    10.0 * 60000.0,
                                    15.0 * 60000.0,
                                    30.0 * 60000.0, //30 mins
                                    60.0 * 60000.0, //1hr
                                    2.0 *  3600000.0, //2hrs
                                    6.0 *  3600000.0,
                                    12.0 * 3600000.0, //12hrs
                                    24.0 * 3600000.0, //1 day
                                    2.0 * 86400000.0,
                                    3.0 * 86400000.0,
                                    7.0 * 86400000.0,
                                    15.0 * 86400000.0,
                                    30.0 * 86400000.0, //30 days
                                    60.0 * 86400000.0,
                                    90.0 * 86400000.0,
                                    120.0 * 86400000.0,
                                    182.0 * 86400000.0,
                                    365.0 * 86400000.0, //1 yr
                                    1};

    /**
     * Defines a OneMillisecond TimeSpan.
     */
    public static final int ONEMICROSECOND = 0;
    
    /**
     * Defines a OneMillisecond TimeSpan.
     */
    public static final int ONEMILLISECOND = 1;
    /**
     * Defines a OneSecond TimeSpan.
     */
    public static final int ONESECOND = 2;
    /**
     * Defines a FiveSeconds TimeSpan.
     */
    public static final int FIVESECONDS = 3;
    /**
     * Defines a TenSeconds TimeSpan.
     */
    public static final int TENSECONDS = 4;
    /**
     * Defines a FifteenSeconds TimeSpan.
     */
    public static final int FIFTEENSECONDS = 5;
    /**
     * Defines a ThirtySeconds TimeSpan.
     */
    public static final int THIRTYSECONDS = 6;
    /**
     * Defines a OneMinute TimeSpan.
     */
    public static final int ONEMINUTE = 7;
    /**
     * Defines a FiveMinutes TimeSpan.
     */
    public static final int FIVEMINUTES = 8;
    /**
     * Defines a TenMinutes TimeSpan.
     */
    public static final int TENMINUTES = 9;
    /**
     * Defines a FifteenMinutes TimeSpan.
     */
    public static final int FIFTEENMINUTES = 10;
    /**
     * Defines a ThirtyMinutes TimeSpan.
     */
    public static final int THIRTYMINUTES = 11;
    /**
     * Defines a OneHour TimeSpan.
     */
    public static final int ONEHOUR = 12;
    /**
     * Defines a TwoHours TimeSpan.
     */
    public static final int TWOHOURS = 13;
    /**
     * Defines a SixHours TimeSpan.
     */
    public static final int SIXHOURS = 14;
    /**
     * Defines a TwelveHours TimeSpan.
     */
    public static final int TWELVEHOURS = 15;
    /**
     * Defines a OneDay TimeSpan.
     */
    public static final int ONEDAY = 16;
    /**
     * Defines a TwoDays TimeSpan.
     */
    public static final int TWODAYS = 17;
    /**
     * Defines a ThreeDays TimeSpan.
     */
    public static final int THREEDAYS = 18;
    /**
     * Defines a OneWeek TimeSpan.
     */
    public static final int ONEWEEK = 19;
    /**
     * Defines a HalfMonth TimeSpan.
     */
    public static final int HALFMONTH = 20;
    /**
     * Defines a OneMonth TimeSpan.
     */
    public static final int ONEMONTH = 21;
    /**
     * Defines a TwoMonths TimeSpan.
     */
    public static final int TWOMONTHS = 22;
    /**
     * Defines a ThreeMonths TimeSpan.
     */
    public static final int THREEMONTHS = 23;
    /**
     * Defines a FourMonths TimeSpan.
     */
    public static final int FOURMONTHS = 24;
    /**
     * Defines a SixMonths TimeSpan.
     */
    public static final int SIXMONTHS = 25;
    /**
     * Defines a OneYear TimeSpan.
     */
    public static final int ONEYEAR = 26;
    /**
     *
     * Defines an automatic TimeSpan.
     */
    public static final int NONE = 27;

    private DateTimeStep(int value) {
        super(value);
    }

    public static int find(double value) {
        /** @todo FINISH ! (copy from Axis class) */
        return DateTimeStep.NONE;
    }

}

