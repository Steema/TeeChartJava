package com.steema.teechart.styles;

import java.util.Random;
import com.steema.teechart.DateTime;

/**
 * <p>Title: SeriesRandom class</p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright (c) 2005-2019 by Steema Software SL. All Rights
 * Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 *
 */
public class SeriesRandom {
    /**
     * Used internally to add random values to a series.
     */
    private static Random r = new Random(DateTime.getNow().getMillisecond());

    public double tmpX, StepX, tmpY, MinY, DifY;

    public double Random() {
        return r.nextDouble();
    }
    
    public double Random(int Range) {
        return r.nextInt(Range);
    }

    public SeriesRandom() {
        super();
    }
}

