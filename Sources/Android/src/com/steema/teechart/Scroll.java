package com.steema.teechart;

import com.steema.teechart.drawing.Point;
import com.steema.teechart.events.FrameworkMouseEvent;

/**
 *
 * <p>Title: Scroll class</p>
 *
 * <p>Description: Chart mouse scroll characteristics</p>
 *
 * <p>Copyright (c) 2005-2019 by Steema Software SL. All Rights
 * Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 *
 */
public class Scroll extends ZoomScroll {
    private ScrollMode allow = ScrollMode.BOTH;
    private int mouseButton = FrameworkMouseEvent.BUTTON3;

    public Scroll(IBaseChart c) {
        super(c);
    }

    /**
     * Controls runtime Scrolling modes.<br>
     * Default value: Both<br><br>
     *
     * Possible values are:<br>
     * None&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
     * Deny scrolling.<br>
     * Horizontal&nbsp;&nbsp;Allow only Horizontal Scrolling.<br>
     * Vertical&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Allow only Vertical
     * Scrolling.<br>
     * Both&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
     * Allow complete Horizontal and Vertical Scrolling.<br>
     *
     *
     * @return ScrollMode
     */
    public ScrollMode getAllow() {
        return allow;
    }

    /**
     * Controls runtime Scrolling modes.<br>
     * Default value: Both<br><br>
     *
     *
     * @param value ScrollMode
     */
    public void setAllow(ScrollMode value) {
        allow = value;
    }

    /**
     * The Mousebutton to use for the scroll action.<br>
     * Default value: Right
     *
     * @return int Mouse Button
     */
    public int getMouseButton() {
        return mouseButton;
    }

    /**
     * Sets the Mousebutton to use for the scroll action.<br>
     * Default value: Right
     *
     * @param value int
     */
    public void setMouseButton(int value) {
        mouseButton = value;
    }

    /**
     * Initializes a Zoom or Scroll operation starting at Point p coordinates.
     *
     * @param p Point
     */
    public void activate(final Point p) {
        super.activate(p.x, p.y, false);
    }
}

