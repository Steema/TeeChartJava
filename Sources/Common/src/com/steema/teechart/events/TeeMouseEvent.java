package com.steema.teechart.events;

import com.steema.teechart.events.FrameworkMouseEvent;

/**
 *
 * <p>Title: TeeMouseEvent</p>
 *
 * <p>Description: Internal use. Mouse events</p>
 *
 * <p>Copyright (c) 2005-2019 by Steema Software SL. All Rights
 * Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 *
 */
public class TeeMouseEvent extends TeeEvent {
    public FrameworkMouseEvent event;

    public TeeMouseEvent(FrameworkMouseEvent event) {
        super();
        this.event=event;
    }
}

