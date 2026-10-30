/*
 * ChartPrintListener.java
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
public interface ChartPrintListener extends EventListener {

    /**
     * Invoked after Chart is printed
     */
    public void chartPrinted(ChartDrawEvent e);

}

