/*
 * PointerStyleResolver.java
 *
 * <p>Copyright (c) 2005-2019 by Steema Software SL. All Rights Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 */

package com.steema.teechart.styles;

/**
 *
 */
public interface PointerStyleResolver {
    public PointerStyle getStyle(ISeries series, int valueIndex, PointerStyle style);
}

