package com.steema.teechart.printer;

import com.steema.teechart.misc.Enum;

/**
 *
 * <p>Title: PrintMarginUnit class</p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright (c) 2005-2019 by Steema Software SL. All Rights
 * Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 */
final public class PrintMarginUnit extends Enum {
    /**
     * Sets units as percentage of page width and height.
     */
    public static final PrintMarginUnit PERCENT = new PrintMarginUnit(0);
    /**
     * Sets units as standard 1/72nds inch printer units.
     */
    public static final PrintMarginUnit SEVENTYSECONDSINCH = new PrintMarginUnit(
            1);

    private PrintMarginUnit(int value) {
        super(value);
    }
}

