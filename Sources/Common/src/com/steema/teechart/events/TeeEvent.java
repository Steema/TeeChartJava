package com.steema.teechart.events;

import com.steema.teechart.IBaseChart;

/**
 *
 * <p>Title: TeeEvent class</p>
 *
 * <p>Description: Internal use. Underlying Chart event class</p>
 *
 * <p>Copyright (c) 2005-2019 by Steema Software SL. All Rights
 * Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 *
 */
public class TeeEvent {
    public IBaseChart sender;

    /**
     * Interface supports inter-Component communication.
     */
    public TeeEvent() {
    }
}

