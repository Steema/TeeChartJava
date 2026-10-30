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
public interface DragListener extends EventListener {

    /**
     * Invoked when a drag is finished.
     */
    void dragFinished(ChangeEvent e);

    /**
     * Invoked when a drag is going on.
     */
    void dragMoving(ChangeEvent e);
}

