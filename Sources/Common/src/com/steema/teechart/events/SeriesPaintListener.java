/*
 * SeriesPaintListener.java
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
public interface SeriesPaintListener extends EventListener {
    /**
     * Invoked before Series are drawn
     */
    public void seriesPainting(ChartDrawEvent e);

    /**
     * Invoked after Series are drawn
     */
    public void seriesPainted(ChartDrawEvent e);
}

