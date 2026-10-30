package com.steema.teechart.styles;

import com.steema.teechart.misc.Enum;

/**
 *
 * <p>Title: CandleStyle class</p>
 *
 * <p>Description: Describes the possible values of Candle.Style</p>
 *
 * <p>Copyright (c) 2005-2019 by Steema Software SL. All Rights Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 */
final public class CandleStyle extends Enum {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
     * Candle points represented by vertical rectangles with Open and Close
     * vertical tick marks.
     */
    public static final CandleStyle CANDLESTICK = new CandleStyle();
    /**
     * Candle points represented by vertical lines with Open and Close
     * horizontal tick marks.
     */
    public static final CandleStyle CANDLEBAR = new CandleStyle();
    /**
     * Candle points represented by vertical rectangles without Open and Close
     * vertical tick marks.
     */
    public static final CandleStyle OPENCLOSE = new CandleStyle();
    /**
     * Candle points represented by a line.
     */
    public static final CandleStyle LINE = new CandleStyle();

    private CandleStyle() {
        super();
    }
}

