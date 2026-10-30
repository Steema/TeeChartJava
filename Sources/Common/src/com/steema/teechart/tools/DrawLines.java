package com.steema.teechart.tools;

import java.util.ArrayList;

/**
 *
 * <p>Title: DrawLines class</p>
 *
 * <p>Description: Collection of <see cref="DrawLineItem"/></p>
 *
 * <p>Copyright (c) 2005-2019 by Steema Software SL. All Rights Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 *
 */
public class DrawLines extends ArrayList {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected DrawLine tool;

    public int add(DrawLineItem l) {
        int n = indexOf(l);
        super.add(l);
        return (n == -1) ? size()-1 : n;
    }

    /**
     * Deletes all Series values.<br>
     * Dependent Series are notified. If no new points are appended to the
     * Series, nothing will be painted.
     */
    public void clear() {
        super.clear();
        tool.invalidate();
    }

    /**
     * Deletes the specified Line from the Chart list of Lines.
     *
     * @param s DrawLineItem
     */
    public void remove(DrawLineItem s) {
        super.remove(s);
        tool.invalidate();
    }

    public DrawLineItem getFirst() {
        if (size() > 0) {
            return (DrawLineItem) get(0);
        } else {
            return null;
        }

    }

    public DrawLineItem getLast() {
        if (size() > 0) {
            return (DrawLineItem) get(size()-1);
        } else {
            return null;
        }
    }

    public DrawLineItem getLine(int index) {
        return (DrawLineItem) get(index);
    }

    /**
     * This method can be used to obtain the index of a given line in the line
     * collection.
     *
     * @param l DrawLineItem
     * @return int
     */
    public int indexOf(DrawLineItem l) {
        for (int t = 0; t < size(); t++) {
            if (get(t) == l) {
                return t;
            }
        }
        return -1;
    }
}


