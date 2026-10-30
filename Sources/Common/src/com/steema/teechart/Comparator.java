/*
 * Comparator.java
 *
 * <p>Copyright (c) 2005-2019 by Steema Software SL. All Rights Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 */

package com.steema.teechart;

/**
 * A comparison function, which imposes a <i>total ordering</i> on some
 * collection of integers.  Comparators can be passed to a sort method (such as
 * <tt>misc.Utils.sort</tt>) to allow precise control over the sort order.
 *
 */
public interface Comparator {
    /**
     * Compares its two arguments for order.  Returns a negative integer,
     * zero, or a positive integer as the first argument is less than, equal
     * to, or greater than the second.<p>
     *
     * @param a the first integer to be compared.
     * @param b the second integer to be compared.
     * @return a negative integer, zero, or a positive integer as the
     * 	       first argument is less than, equal to, or greater than the
     *	       second.
     */
    int compare(int a, int b);
}

