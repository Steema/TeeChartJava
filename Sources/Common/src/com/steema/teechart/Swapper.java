/*
 * Swapper.java
 *
 * <p>Copyright (c) 2005-2019 by Steema Software SL. All Rights Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 */

package com.steema.teechart;

/**
 * A swapper function. Swappers can be passed to a sort method (such as
 * <tt>misc.Utils.sort</tt>) to allow precise control over the swap of elements.
 *
 */
public interface Swapper {
    /**
     * Swaps two elements
     *
     * @param a the index of first element to be swapped.
     * @param b the index of second element to be swapped.
     */
    void swap(int a, int b);
}

