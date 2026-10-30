/*
 * ChartMotionListener.java
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
public interface ChartMotionListener extends EventListener {

    /**
     * Invoked when the chart has been scrolled
     */
    public void scrolled(ChartEvent e);

    /**
     * Invoked when the chart has been zoomed
     */
    public void zoomed(ChartEvent e);

    /**
     * Invoked when the chart has been unzoomed
     */
    public void unzoomed(ChartEvent e);
}

