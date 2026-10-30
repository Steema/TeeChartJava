/*
 * AxisLabelAdapter.java
 *
 */

package com.steema.teechart.axis;

import com.steema.teechart.events.ScrollModEventArgs;
import com.steema.teechart.styles.ISeries;

/**
 * <p>Title: TeeChart for Java</p>
 *
 * <p>Description: TeeChart for Java</p>
 *
 * <p>Copyright  (c) 2004-2019 by Steema Software SL. All Rights Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 *
 */
public class AxisLabelAdapter implements AxisLabelResolver {

    /**
     * Creates a new instance of AxisLabelAdapter
     */
    public AxisLabelAdapter() {}

    public String getLabel(Axis axis, ISeries s, int valueIndex, String labelText) {
        return labelText;
    }

    public NextAxisLabelValue getNextLabel(Axis axis, int labelIndex, NextAxisLabelValue nextAxisLabel) {
        return nextAxisLabel;
    }

    public void scrollModHandler(Axis a, ScrollModEventArgs e) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}

