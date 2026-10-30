/*
 * ChartMouseListener.java
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
public interface ChartMouseListener extends EventListener {

    /**
     * Invoked when the mouse button has been clicked (pressed and released) on an Axis
     */
    public void axesClicked(ChartMouseEvent e);

    /**
     * Invoked when the mouse button has been clicked (pressed and released) on the Background
     */
    public boolean backgroundClicked(ChartMouseEvent e);

    /**
     * Invoked when the mouse button has been clicked (pressed and released) on a Legend
     */
    public void legendClicked(ChartMouseEvent e);

    /**
     * Invoked when the mouse button has been clicked (pressed and released) on an Title
     */
    public void titleClicked(ChartMouseEvent e);

    /**
     * Invoked when the mouse button has been released
     */
    public boolean mouseUp(ChartMouseEvent e);

    /**
     * Invoked when the mouse button has been released
     */
    public boolean mouseMove(ChartMouseEvent e);
}

