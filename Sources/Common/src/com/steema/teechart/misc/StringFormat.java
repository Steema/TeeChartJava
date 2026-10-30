package com.steema.teechart.misc;

import java.util.ArrayList;

/**
 * <p>Title: StringFormat class</p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright (c) 2005-2019 by Steema Software SL. All Rights
 * Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 *
 */
public class StringFormat {

    static public String format(String format, String prefix, double value,
                                String valueFormat) {
        /** @todo FINISH ! */
        return prefix + " " + Double.toString(value);
    }

    static public String format2(String format, double value1, double value2) {
        return format + " " + Double.toString(value1) + " " +
                Double.toString(value2);
    }

    static public String format2(String format, int value1, int value2) {
        Object[] obj = {Integer.toString(value1), Integer.toString(value2)};
        return format(format, obj);
    }

    static public String format(String formatStr, String value) {
        Object[] args = {value};
        return java.text.MessageFormat.format(formatStr, args);
    }

    static public String format(String formatStr, Object[] args) {
        return java.text.MessageFormat.format(formatStr, args);
    }

    static public String[] split( String in, String ch ){
        ArrayList<String> tmp=new ArrayList<String>();

        int pos=-1;

        do {
            pos = in.indexOf(ch);

            if (pos != -1) {
                String s = in.substring(0, pos);
                tmp.add(s);
                in = in.substring(pos + 1);
            }
        } while (pos != -1);

            if (in.length() > 0) {
                tmp.add(in);
            }


        String[] result = new String[tmp.size()];
        return (String[])tmp.toArray(result);
    }
}

