package com.steema.teechart.styles;

import java.util.ArrayList;

/**
 *
 * <p>Title: StringList class</p>
 *
 * <p>Description: The StringList class is manipulated via the Series
 * Series.Labels method.</p>
 *
 *
 * <p>Copyright (c) 2005-2019 by Steema Software SL. All Rights Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 *
 * @see com.steema.teechart.styles.Series#getLabels
 */
public final class StringList extends ArrayList {
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public StringList(int capacity) {
        super(capacity);
    }

    public StringList(String[] strings) {
        this(strings.length);
        for(int t=0;t<strings.length;t++) {
            this.add(strings[t]);
        }
    }

    public int getCount() {
        return size();
    }

    public String getString(int index) {
        return ((index < getCount()) && (index > -1)) ? (String) get(index) : "";
    }

    public void setString(int index, String value) {
        while (getCount() <= index) {
            add("");
        }
        set(index, value);
    }

    public void removeRange(int startIndex, int count) {
        super.removeRange(startIndex, startIndex + count - 1);
    }

    public void exchange(int a, int b) {
        String s = getString(a);
        setString(a, getString(b));
        setString(b, s);
    }
    
    /**
    * Insert a specified item to the list shifting list accordingly.
    * @param index (int) where to insert item.
    * @param insertItem the item that is to be inserted.
    */
    public void insert(int index, String insertItem) {
        while (getCount() < index) add(""); //TJ71013286
        super.add(index,(Object)(insertItem));
    }
}

