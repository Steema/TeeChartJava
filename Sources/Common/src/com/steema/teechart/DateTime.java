package com.steema.teechart;

import java.text.DateFormat;
import java.util.GregorianCalendar;
import java.util.Calendar;
import java.text.SimpleDateFormat;
/**
 *
 *
 * <p>Title: DateTime class</p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright (c) 2005-2019 by Steema Software SL. All Rights
 * Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 *
 *
 * Based on millisecond atomic level
 *
 */
final public class DateTime extends GregorianCalendar {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	static public final long MINVALUE = 0;

    public DateTime(double value) {
        this((long)value);
    }

    public DateTime(long value) {
        super();
        setTimeInMillis(value);
    }

    public DateTime(int year, int month, int day) {
        super(year, month, day);
    }

    public int getYear() {
        return get(YEAR);
    }

    public int getMonth() {
        return get(MONTH);
    }

    public int getDay() {
        return get(DAY_OF_MONTH);
    }

    public int getDayOfWeek() {
        return get(DAY_OF_WEEK);
    }

    public int getHour() {
        return get(HOUR_OF_DAY);
    }

    public int getMinute() {
        return get(MINUTE);
    }

    public int getSecond() {
        return get(SECOND);
    }

    public int getMillisecond() {
        return get(MILLISECOND);
    }

    static public DateTime getNow() {
        return new DateTime(Calendar.getInstance().getTimeInMillis());
    }

    public String toShortTimeString() {
        return DateFormat.getTimeInstance(DateFormat.SHORT).format(getTime());
    }

    public String toShortDateString() {
        return DateFormat.getDateInstance(DateFormat.SHORT).format(getTime());
    }

    static public DateTime getMinValue() {
        return new DateTime(MINVALUE);
    }

    public String toString() {
        return toShortDateString();
    }

    public String toString(DateFormat format) {
        return format.format(getTime());
    }

    public String toString(String format) {
        return (new SimpleDateFormat(format)).format(getTime());
    }

    static public DateTime getToday() {
        DateTime tmp = DateTime.getNow();
        return new DateTime(tmp.getYear(), tmp.getMonth(), tmp.getDay());
    }

    public double toDouble() {
        return getTimeInMillis();
    }

    public static int getDaysInMonth(int year,int month)
    {
        int [] days = {31,28,31,30,31,30,31,31,30,31,30,31};

        if (month==2) {
            days[1] += (new DateTime(year,month,1)).isLeapYear(year) ? 1 : 0;
        }

        return days[month];

        // alternative (shorter) code:  faster?
        // DateTime tmp=new DateTime(year,month,1);
        // return tmp.getActualMaximum(tmp.DAY_OF_MONTH);
    }

    public DateTime addMonths(int amount)
    {
      add(MONTH,amount);
      return this;
    }
}

