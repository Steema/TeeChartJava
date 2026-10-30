/*
 * DefaultResolvers.java
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
public final class DefaultResolvers {
    public static final TextResolver TEXT_RESOLVER = new TextResolver() {
        public String getText(Object sender, String text) {
            return text;
        }
    };

    public final static DateTimeResolver DATETIME_RESOLVER = new DateTimeResolver() {
        public DateTime getDate(Object sender, DateTime dateTime) {
            return dateTime;
        }
    };

    public final static NumberResolver NUMBER_RESOLVER = new NumberResolver() {
        public int getNumber(Object sender, int number) {
            return number;
        }
    };
}

