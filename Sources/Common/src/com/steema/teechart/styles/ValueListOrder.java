package com.steema.teechart.styles;

import com.steema.teechart.misc.Enum;

/**
 *
 * <p>Title: ValueListOrder class</p>
 *
 * <p>Description: Describes the possible values of the ValueList.Order
 * method.</p>
 *
 * <p>Copyright (c) 2005-2019 by Steema Software SL. All Rights Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 *
 * @see com.steema.teechart.styles.ValueList#getOrder
 */
final public class ValueListOrder extends Enum {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
     * Values are ordered in the order in which they were added to the
     * ValueList.
     */
    public static final ValueListOrder NONE = new ValueListOrder(0);
    /**
     * Values are ordered in ascending numerical order.
     */
    public static final ValueListOrder ASCENDING = new ValueListOrder(1);
    /**
     * Values are ordered in descending numerical order.
     */
    public static final ValueListOrder DESCENDING = new ValueListOrder(2);

    private ValueListOrder(int value) {
        super(value);
    }
}

