/*
 * ChartPaintListener.java
 *
 */

package com.steema.teechart.events;

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
public interface ChartPaintListener extends SeriesPaintListener {

    /**
     * Invoked before Axes are drawn
     */
    public void axesPainting(ChartDrawEvent e);

    /**
     * Invoked after Chart is drawn
     */
    public void chartPainted(ChartDrawEvent e);

    /**
     * Invoked before Chart is drawn
     */
    public void chartPainting(ChartDrawEvent e);
}

