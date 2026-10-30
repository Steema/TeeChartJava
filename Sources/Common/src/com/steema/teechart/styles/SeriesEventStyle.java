package com.steema.teechart.styles;

import com.steema.teechart.misc.Enum;

/**
 *
 * <p>Title: SeriesEventStyle class</p>
 *
 * <p>Description: For internal use by Series instances to notify changes.</p>
 *
 * <p>Copyright (c) 2005-2019 by Steema Software SL. All Rights Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 *
 */
final public class SeriesEventStyle extends Enum {

    public static final SeriesEventStyle ADD = new SeriesEventStyle();

    public static final SeriesEventStyle REMOVE = new SeriesEventStyle();

    public static final SeriesEventStyle REMOVEALL = new SeriesEventStyle();

    public static final SeriesEventStyle CHANGETITLE = new SeriesEventStyle();

    public static final SeriesEventStyle CHANGECOLOR = new SeriesEventStyle();

    public static final SeriesEventStyle SWAP = new SeriesEventStyle();

    public static final SeriesEventStyle CHANGEACTIVE = new SeriesEventStyle();

    public static final SeriesEventStyle DATACHANGED = new SeriesEventStyle();

    private SeriesEventStyle() {
        super();
    }
}

