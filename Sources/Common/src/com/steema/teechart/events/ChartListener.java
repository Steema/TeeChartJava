/*
 * ChartListener.java
 *
 */

package com.steema.teechart.events;

import java.util.EventListener;

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
public interface ChartListener extends EventListener {

    /**
     * Invoked when a series has been added to the chart
     */
    void seriesAdded(ChartEvent e);

    /**
     * Invoked when a tool has been added to the chart
     */
    void toolAdded(ChartEvent e);

}

