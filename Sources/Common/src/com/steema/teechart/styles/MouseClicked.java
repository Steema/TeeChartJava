package com.steema.teechart.styles;

import com.steema.teechart.Cursor;

/**
 * <p>Title: MouseClicked class</p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright (c) 2005-2019 by Steema Software SL. All Rights
 * Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 *
 *
 */
public final class MouseClicked {
    
	public boolean clicked;
    public Cursor cursor;
    
    public MouseClicked(Cursor cursor, boolean clicked) {
        this.clicked = clicked;
        this.cursor = cursor;
    }
    public MouseClicked(Cursor cursor) {
        this(cursor, false);
    }
}

