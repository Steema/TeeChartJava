package com.steema.teechart;

import com.steema.teechart.misc.Enum;
import java.util.ArrayList;

/**
 *
 * <p>Title: PanelMarginUnits class</p>
 *
 * <p>Description: Describes the possible values of Panel.MarginUnits.</p>
 *
 * <p>Copyright (c) 2005-2019 by Steema Software SL. All Rights
 * Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 *
 */
final public class PanelMarginUnits extends Enum {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static int upperBound=0;
    private static ArrayList values = new ArrayList();

    /**
     * Defines units as a percentage of the panel size.
     */
    public static final PanelMarginUnits PERCENT = new PanelMarginUnits(0);

    /**
     * Defines units in pixels.
     */
    public static final PanelMarginUnits PIXELS = new PanelMarginUnits(1);

    public static int size() { return upperBound; }

    public PanelMarginUnits atIndex(int index) {
        return (PanelMarginUnits)values.get(index);
    }

    private PanelMarginUnits(int value) {
        super(value);
        upperBound++;
        values.add(this);
    }
}

