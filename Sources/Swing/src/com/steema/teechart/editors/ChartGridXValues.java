/*
 * ChartGridXValues.java
 *
 * <p>Description: </p>
 *
 * <p>Copyright (c) 2005-2019 by Steema Software SL. All Rights Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 */

package com.steema.teechart.editors;

import com.steema.teechart.misc.Enum;

/**
 *
 */
public final class ChartGridXValues extends Enum {

    static final public ChartGridXValues AUTO = new ChartGridXValues(0);
    static final public ChartGridXValues YES = new ChartGridXValues(1);
    static final public ChartGridXValues NO = new ChartGridXValues(2);

    private ChartGridXValues(int value) {
        super(value);
    }

    static public ChartGridXValues fromValue(int value) {
        switch (value) {
            case 0:
                return AUTO;
            case 1:
                return YES;
            default:
                return NO;
        }
    }
}

