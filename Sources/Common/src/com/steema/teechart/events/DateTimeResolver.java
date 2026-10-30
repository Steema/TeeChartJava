/*
 * DateTimeResolver.java
 *
 */

package com.steema.teechart.events;

import com.steema.teechart.DateTime;

/**
 * <p>Title: TeeChart for Java</p>
 *
 * <p>Description: TeeChart for Java</p>
 *
 * <p>Copyright  (c) 2004-2019 by Steema Software SL. All Rights Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 *
 */
public interface DateTimeResolver {
    DateTime getDate(Object sender, DateTime dateTime);
}

