package com.steema.teechart.events;

import com.steema.teechart.events.FrameworkMouseEvent;
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
public interface ToolMouseListener extends EventListener {

    /**
     * Invoked when a Tool is clicked.
     */
    public void toolClicked(FrameworkMouseEvent e);
}

