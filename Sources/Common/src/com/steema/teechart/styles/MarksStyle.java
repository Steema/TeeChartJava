package com.steema.teechart.styles;

import com.steema.teechart.misc.Enum;

/**
 *
 * <p>Title: MarksStyle class</p>
 *
 * <p>Description: Describes the possible values of Series Marks Style.</p>
 *
 * @see SeriesMarks#getStyle
 *
 * <p>Copyright (c) 2005-2019 by Steema Software SL. All Rights Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 */
final public class MarksStyle extends Enum {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
     * Shows the point value.
     */
    public static final MarksStyle VALUE = new MarksStyle(0);
    /**
     * Shows the percent the point value represents.
     */
    public static final MarksStyle PERCENT = new MarksStyle(1);
    /**
     * Shows the associated Point Label.
     */
    public static final MarksStyle LABEL = new MarksStyle(2);
    /**
     * Shows the point Label and the percent value the point represents.
     */
    public static final MarksStyle LABELPERCENT = new MarksStyle(3);
    /**
     * Shows the point Label and the point value.
     */
    public static final MarksStyle LABELVALUE = new MarksStyle(4);
    /**
     * Shows whatever is shown at Chart Legend.
     */
    public static final MarksStyle LEGEND = new MarksStyle(5);
    /**
     * Shows the percent the point represents together with the "of" word and
     * the sum of all points absolute values.
     */
    public static final MarksStyle PERCENTTOTAL = new MarksStyle(6);
    /**
     * Shows the point Label toghether with the resulting "PercentTotal" style.
     */
    public static final MarksStyle LABELPERCENTTOTAL = new MarksStyle(7);
    /**
     * Shows the point XValue.
     */
    public static final MarksStyle XVALUE = new MarksStyle(8);
    /**
     * Shows the point XValue and YValue.
     */
    public static final MarksStyle XY = new MarksStyle(9);
    /**
     * Show the point Series Title.
     */
    public static final MarksStyle SERIESTITLE = new MarksStyle(10);
    /**
     * Show the point Index.
     */
    public static final MarksStyle POINTINDEX = new MarksStyle(11);
    /**
     * Show the point percent relative to first point value.
     */
    public static final MarksStyle PERCENTRELATIVE = new MarksStyle(12);

    private MarksStyle(int value) {
        super(value);
    }

    static public MarksStyle fromValue(int value) {
        switch (value) {
        case 0:
            return VALUE;
        case 1:
            return PERCENT;
        case 2:
            return LABEL;
        case 3:
            return LABELPERCENT;
        case 4:
            return LABELVALUE;
        case 5:
            return LEGEND;
        case 6:
            return PERCENTTOTAL;
        case 7:
            return LABELPERCENTTOTAL;
        case 8:
            return XVALUE;
        case 9:
            return XY;
        case 10:
            return SERIESTITLE;
        case 11:
            return POINTINDEX;
        default:
            return PERCENTRELATIVE;
        }
    }
}

