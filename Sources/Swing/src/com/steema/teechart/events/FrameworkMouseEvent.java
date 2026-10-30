/*
 * Copyright 2026 Steema Software SL
 * SPDX-License-Identifier: Apache-2.0
 */
package com.steema.teechart.events;

import java.awt.Component;
import java.awt.event.MouseEvent;
import com.steema.teechart.drawing.Point;

/** AWT mouse event adapter exposing TeeChart's covariant Point type. */
public class FrameworkMouseEvent extends MouseEvent {
    private static final long serialVersionUID = 1L;

    public Object widget;

    public FrameworkMouseEvent(Component source, int eventId, long timestamp, int modifiers,
                               int x, int y, int clicks, boolean popup) {
        this(source, eventId, timestamp, modifiers, x, y, clicks, popup, MouseEvent.NOBUTTON);
    }

    public FrameworkMouseEvent(Component source, int eventId, long timestamp, int modifiers,
                               int x, int y, int clicks, boolean popup, int button) {
        super(source, eventId, timestamp, modifiers, x, y, clicks, popup, button);
    }

    /** Absolute coordinates remain ignored, as in the existing TeeChart API. */
    public FrameworkMouseEvent(Component source, int eventId, long timestamp, int modifiers,
                               int x, int y, int absoluteX, int absoluteY,
                               int clicks, boolean popup, int button) {
        this(source, eventId, timestamp, modifiers, x, y, clicks, popup, button);
    }

    public Point getPoint() {
        return snapshotPosition();
    }

    private synchronized Point snapshotPosition() {
        return new Point(getX(), getY());
    }
}
