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
public interface SeriesMouseListener extends EventListener {

    /**
     * Invoked when a Series is clicked.
     */
    public void seriesClicked(SeriesMouseEvent e);

    /**
     * Invoked when the mouse pointer enters the Series.
     */
    public void seriesEntered(SeriesMouseEvent e);

    /**
     * Invoked when the mouse pointer leaves the Series.
     */
    public void seriesExited(SeriesMouseEvent e);
}

