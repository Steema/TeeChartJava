/*
 * AxisLabelResolver.java
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
public interface AxisLabelResolver {

    String getLabel(Axis axis, ISeries s, int valueIndex, String labelText);

    NextAxisLabelValue getNextLabel(Axis axis, int labelIndex, NextAxisLabelValue nextLabel);

    void scrollModHandler(Axis a, ScrollModEventArgs e);

}

