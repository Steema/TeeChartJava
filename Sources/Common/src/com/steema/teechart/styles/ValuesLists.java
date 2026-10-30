package com.steema.teechart.styles;

import java.util.ArrayList;

/**
 *
 * <p>Title: ValuesLists class</p>
 *
 * <p>Description: ValueLists is a collection of ValueList objects.</p>
 *
 * <p>Copyright (c) 2005-2019 by Steema Software SL. All Rights
 * Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 *
 */
public final class ValuesLists extends ArrayList {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ValueList getValueList(int index) {
        return (ValueList)super.get(index);
    }

    public int indexOf(ValueList value) {
        for (int t = 0; t < size(); t++) {
            if (getValueList(t) == value) {
                return t;
            }
        }
        return -1;
    }
}

