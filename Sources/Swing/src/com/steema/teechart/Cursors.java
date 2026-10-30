package com.steema.teechart;

import java.awt.Cursor;

/**
 *
 * <p>Title: Cursors class</p>
 *
 * <p>Description: List of system installed mouse cursors.</p>
 *
 * <p>Copyright (c) 2005-2019 by Steema Software SL. All Rights Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 *
 */
public class Cursors extends Cursor {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final Cursor DEFAULT = getDefaultCursor();
    public static final Cursor HAND = getPredefinedCursor(HAND_CURSOR);
    public static final Cursor CROSS = getPredefinedCursor(CROSSHAIR_CURSOR);

    public static final Cursor SIZEWE = getPredefinedCursor(SW_RESIZE_CURSOR);
    public static final Cursor HSPLIT = getPredefinedCursor(W_RESIZE_CURSOR);
    public static final Cursor VSPLIT = getPredefinedCursor(S_RESIZE_CURSOR);
    public static final Cursor SIZEALL = getPredefinedCursor(N_RESIZE_CURSOR);
    public static final Cursor NO = getPredefinedCursor(TEXT_CURSOR);

    private Cursors() {
        super(DEFAULT_CURSOR);
    }

    public static Cursor fromCursor(com.steema.teechart.Cursor cursor) {
    	if(cursor == null)
            cursor = new com.steema.teechart.Cursor(0);
        if (cursor.getType() < Cursor.DEFAULT_CURSOR || cursor.getType() > Cursor.MOVE_CURSOR) {
            return new Cursor(Cursor.DEFAULT_CURSOR);
        } else {
            return getPredefinedCursor(cursor.getType());
        }
    }
    
	public static com.steema.teechart.Cursor toCursor(java.awt.Cursor cursor) {
		switch (cursor.getType()) {
		case Cursor.CROSSHAIR_CURSOR: return com.steema.teechart.Cursor.CROSS;
		case Cursor.CUSTOM_CURSOR: return null;
		case Cursor.DEFAULT_CURSOR: return com.steema.teechart.Cursor.DEFAULT;
		case Cursor.E_RESIZE_CURSOR: return com.steema.teechart.Cursor.E_RESIZE;
		case Cursor.HAND_CURSOR: return com.steema.teechart.Cursor.HAND;
		case Cursor.MOVE_CURSOR: return com.steema.teechart.Cursor.SIZEALL;
		case Cursor.N_RESIZE_CURSOR: return com.steema.teechart.Cursor.N_RESIZE;
		case Cursor.NE_RESIZE_CURSOR: return com.steema.teechart.Cursor.NE_RESIZE;
		case Cursor.NW_RESIZE_CURSOR: return com.steema.teechart.Cursor.NW_RESIZE;
		case Cursor.SE_RESIZE_CURSOR: return com.steema.teechart.Cursor.SE_RESIZE;
                case Cursor.W_RESIZE_CURSOR: return com.steema.teechart.Cursor.HSPLIT;
                case Cursor.S_RESIZE_CURSOR: return com.steema.teechart.Cursor.VSPLIT;
		 default: return com.steema.teechart.Cursor.DEFAULT;
		}
	}
};

