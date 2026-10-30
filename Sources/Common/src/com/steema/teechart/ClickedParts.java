package com.steema.teechart;

import com.steema.teechart.misc.Enum;

/**
 *
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright (c) 2005-2019 by Steema Software SL. All Rights Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 *
 */
final public class ClickedParts extends Enum {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	final public static ClickedParts NONE = new ClickedParts(0);
    final public static ClickedParts LEGEND = new ClickedParts(1);
    final public static ClickedParts SERIES = new ClickedParts(2);
    final public static ClickedParts AXIS = new ClickedParts(3);
    final public static ClickedParts FOOT = new ClickedParts(4);
    final public static ClickedParts HEADER = new ClickedParts(5);
    final public static ClickedParts SUBHEADER = new ClickedParts(6);
    final public static ClickedParts SUBFOOT = new ClickedParts(7);
    final public static ClickedParts CHARTRECT = new ClickedParts(8);
    final public static ClickedParts SERIESMARKS = new ClickedParts(9);
    final public static ClickedParts TOOL = new ClickedParts(10);

    private ClickedParts(int value) {
        super(value);
    }
}

