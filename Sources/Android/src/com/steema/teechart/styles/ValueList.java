package com.steema.teechart.styles;

import com.steema.teechart.DateTime;
import com.steema.teechart.TeeBase;
import com.steema.teechart.Comparator;
import com.steema.teechart.misc.Utils;

/**
 *
 * <p>Title: ValueList class</p>
 *
 * <p>Description: Array to hold Series data point values.</p>
 *
 * <p>Copyright (c) 2005-2019 by Steema Software SL. All Rights Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 *
 */
public final class ValueList extends TeeBase {
	
    private static final long serialVersionUID = 1L;

    static public int defaultCapacity;

    public double[] value = new double[1];
    public int count;

    transient public int capacity;

    boolean dateTime;

    transient double maximum, minimum, total, totalABS;
    public transient boolean statsOk;

    private ValueListOrder order = ValueListOrder.NONE;

    transient public double tempValue;

    transient public String valueSource = "";
    transient public ISeries series;
    

    // "name" should not be transient, as it can be the "valueSource"
    // (setDataMember) of another series source. So it must be serialized.
    protected String name;

    public ValueList() { this(null, null); }
    
    public ValueList(ISeries s, String name) {
        this(s, name, defaultCapacity);
    }

    public ValueList(ISeries s, String name, int initialCapacity) {
        super(null);
        series = s;
        if(series != null) series.valuesListAdd(this);
        this.name = name;
        capacity = initialCapacity;
        setStatsOk(false);
    }

    /**
     * Field to use as source for this value list.<br>
     * Default value: ""
     *
     * @return String
     */
    public String getDataMember() {
        return valueSource;
    }

    /**
     * Field to use as source for this value list.<br>
     * Default value: ""
     *
     * @param value String
     */
    public void setDataMember(String value) {
        if (! valueSource.equals(value)) {
            valueSource = value;
            if (series != null) {
                series.checkDataSource();
            }
        }
    }

    /**
     * Allows values to be expressed either as numbers or as Date+Time
     * values.<br><br>
     * Each Series value list has a boolean property called DateTime.  The
     * boolean DateTime method tells TeeChart what type the numbers are. The
     * horizontal (x axis) and vertical (y axis) value defaults are number
     * format (DateTime False). DateTime can be changed both at design-time and
     * run-time, forcing the Chart to repaint. It used whenever a value must be
     * converted to text, for example, to draw it as the chart axis labels.
     * Axis labels will be drawn in DateTime or numeric format accordingly to
     * the setting of the DateTime method. <br><br>
     * You can also set the Chart Series ValueFormat and the Chart Axis
     * DateTimeFormat formatting strings, to control how the values will be
     * displayed. <br>
     * Default value: false
     *
     * @return boolean
     */
    public boolean getDateTime() {
        return dateTime;
    }

    /**
     * Allows values to be expressed either as numbers or as Date+Time
     * values.<br>
     * Default value: false
     *
     * @param value boolean
     */
    public void setDateTime(boolean value) {
        if (dateTime != value) {
            dateTime = value;
            series.invalidate();
        }
    }

    /**
     * Determines if points are automatically sorted or left at original
     * position.<br> Runtime only. <br>
     * This Order is used by default by the Series XValues to draw lines from
     * Left to Right. Setting the XValues.Order property to loNone will respect
     * the points order at point creation. This can be used to draw polygons.
     * <br>Default value: None
     *
     * @return ValueListOrder
     */
    public ValueListOrder getOrder() {
        return order;
    }

    /**
     * Determines if points are automatically sorted or left at original
     * position.<br> Runtime only. <br>
     * Default value: None
     *
     * @param value ValueListOrder
     */
    public void setOrder(ValueListOrder value) {
        if (order != value) {
            order = value;

            //DB: Removed as it breaks series initialization at design-time,
            //when changing series order it'll call fillSampleValues.
            //if (series != null) {
            //    series.checkDataSource();
            //}
        }
    }

    /**
     * Returns the name of this ValueList.
     * The Name property can be used to link series when using functions.
     * You can link one series ValueList to another series list, by using
     * the setDataMember method.
     *
     * For example:
     *
     * mySeries.setDataSource( myCandle );
     * mySeries.getYValues().setDataMember(myCandle.getHighValues().getName());
     *
     * @param name String
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns the name of this ValueList.
     * The "Name" property is used as the "dataMember" of other series
     * when they are linked through functions.
     *
     * @return String
     */
    public String getName() {
        return this.name;
    }

    /**
     * Removes all values in the list.
     * Warning: You should not call this "clear" method directly.
     * Call the series "clear" method instead.
     */
    public void clear() {
        count = 0;
        value = new double[1];
        statsOk = false;
    }
    
    public void clear(int startCapacity) {
        setCount(0);
        setValues(new double[startCapacity]);
        setStatsOk(false);
    }

    /**
     * Returns the corresponding point index which has the specified Value.<br>
     * You can use it for example to obtain X co-ordinates based on Y values, or
     * vice-versa.
     *
     * @param value double
     * @return int
     */
    public int indexOf(final double value) {
        for (int t = 0; t < this.value.length; t++) {
            if (this.value[t] == value) {
                return t;
            }
        }
        return -1;
    }

    public void removeRange(int index, int count) {
        this.count -= count;
        for (int t = index; t < this.count; t++) {
            value[t] = value[t + count];
        }
        statsOk = false;
    }

    public void removeAt(int index) {
        removeRange(index, 1);
    }

    /**
     * Re-orders Series points, interchanging their position in the Series
     * Values lists.<br>
     * By default, Series are set to sort points in ascending order using their
     * X coordinates. This is accomplished with this code: <br><br>
     * [C#]<br>
     * tChart1.getSeries(0).getXValues().setOrder( loAscending )<br>
     * tChart1.getSeries(0).getYValues().setOrder( loNone )<br><br>
     * By default, Series draw points using the point ValueIndex, except in
     * some non common situations like having the horizontal axis inverted.<br>
     * <b>Important Note:</b>  Re-Ordering Series points do NOT change point
     * X coordinates. Series points which have no X coordinates are assigned a
     * unique incremental number to determine the point horizontal positions.
     * Automatic Point indexes  start at zero. You will need to change every
     * point X coordinate when sorting Series points with automatic X values.
     */
    public void sort() {
        if (order != ValueListOrder.NONE) {
            Utils.sort(0, count - 1,
                            new CompareValueIndex(),
                            series.getValueIndexSwapper()
                       );
        }
    }

    // Eliminates excess of empty values in array.
    // (Array always contains Count plus some more empty values to
    // reduce overhead expanding the array each time a new value is added).
    public void trim() {
        double[] newValue = new double[count];
        System.arraycopy(value,0,newValue,0,count);

        /*
        for (int t = 0; t < count; t++) {
            newValue[t] = value[t];
        }
        */

        value = newValue;
    }

    /**
     * Returns the First point value.
     *
     * @return double
     */
    public double getFirst() {
        return value[0];
    }

    void insertChartValue(int valueIndex,double value) {
        incrementArray();

        for (int t = count - 1; t > valueIndex; t--) {
            this.value[t] = this.value[t - 1];
        }

        this.value[valueIndex] = value;
        statsOk = false;
    }

    /**
     * Returns the Last point value.<br>
     * This is the same value as the Count - 1 index value:
     *
     * @return double
     */
    public double getLast() {
        return value[count - 1];
    }

    /**
     * Obsolete.&nbsp;Please use IndexOf method instead.
     *
     * @param value double
     * @return int
     */
    public int locate(double value) {
        return indexOf(value);
    }

    public void assign(ValueList value) {
        order = value.order;
        dateTime = value.dateTime;
        name = value.name;
        valueSource = value.valueSource;
    }

    public void calcStats() {
        if (count > 0) {
            minimum = value[0];
            maximum = minimum;
            total = minimum;
            totalABS = Math.abs(total);
            double tmp;

            for (int t = 1; t < count; t++) {
                tmp = value[t];
                if (tmp < minimum) {
                    minimum = tmp;
                }
                if (tmp > maximum) {
                    maximum = tmp;
                }
                total += tmp;
                totalABS += Math.abs(tmp);
            }
        } else {
            minimum = 0;
            maximum = 0;
            total = 0;
            totalABS = 0;
        }
        statsOk = true;
    }

    /**
     *
     * @return double
     */
    public double getRange()
    {
        if (!statsOk) {
            calcStats();
        }
        return maximum - minimum;
    }

    /**
     * The highest of all values in the list.<br>
     * As new points are being added to Series, the IValueList object
     * calculates the Maximum, Minimum and TotalABS properties. <br>
     * This applies to all Series lists of values, such as XValues, YValues,
     * etc. <br>
     *
     * @return double
     */
    public double getMaximum()
    {
        if (!statsOk) {
            calcStats();
        }
        return maximum;
    }

    /**
     * Obsolete.&nbsp;Please use Maximum method instead.
     *
     * @return double
     */
    public double getMaxValue() {
        return maximum;
    }

    /**
     * Obsolete.&nbsp;Please use Minimum instead.
     *
     * @return double
     */
    public double getMinValue() {
        return minimum;
    }

    /**
     * The lowest of all values in the list.<br>
     *
     * @see ValueList#getMaximum
     * @return double
     */
    public double getMinimum() {
        if (!statsOk) {
            calcStats();
        }
        return minimum;
    }
    
    /**
     * Gets or sets a value which indicates whether the Values have been <br>
     * modified to trigger a recalculation of Series statistics.
     */
    public boolean getStatsOk()
    {
        return statsOk;
    }
    
    public void setStatsOk(boolean value)
    {
        setBooleanProperty(statsOk, value);
    }

    /**
     * The sum of all IValueList values.<br>
     * When adding, deleting or modifying point values using the right methods,
     * Total is automatically incremented and decremented. <br>
     * Total is used by some Functions to improve speed when performing
     * calculations against point values, where having already calculated
     * the sum of point values is necessary. <br>
     *
     * @return double
     */
    public double getTotal() {
        if (!statsOk) {
            calcStats();
        }
        return total;
    }

    /**
     * The sum of all absolute values in the list.<br>
     * Run-time and read only. <br>
     * The values are first converted to their absolute value.<br>
     * Pie series, for example, uses this property to calculate the percent
     * each Pie slice represents. <br>
     *
     * @see ValueList#getMaximum
     * @return double
     */
    public double getTotalABS() {
        if (!statsOk) {
            calcStats();
        }
        return totalABS;
    }

    //set { totalABS=value; } // TODO: How to avoid Pie series setting ?

    public double getValue(int index) {
        return value[index];
    }

    public void setValue(int index, double value) {
        this.value[index] = value;
        statsOk = false;
    }

    // For XMLEncoder only
    public double[] getValues() {
      return value;
    }

    // For XMLEncoder only
    public void setValues(double[] value) {
      this.value=value;
      statsOk = false;
    }

    // For XMLEncoder only
    public int getCount() {
      return count;
    }

    // For XMLEncoder only
    public void setCount(int value) {
      count=value;
    }

    public DateTime asDateTime(int index) {
        return new DateTime(value[index]);
    }

    private void incrementArray() {
        count++;
        int tmp = value.length;
        if (count >= tmp) {
            if (capacity > 0) {
                tmp += capacity;
            } else
            if (count > 3) {
                tmp += count / 4;
            } else {
                tmp += 100;
            }

            double[] newValue = new double[tmp];

            System.arraycopy(value,0,newValue,0,count-1);

//            for (int t = 0; t < Count-1; t++) {
//                newValue[t] = Value[t];
//            }

            value = newValue;
        }
    }

    public int addChartValue(double value) {
        int result;

        if (order == ValueListOrder.NONE) {
            result = count;

            incrementArray();
            this.value[result] = value;
        } else {
            int t = count - 1;
            if ((t == -1) ||
                ((order == ValueListOrder.ASCENDING) &&
                 (value >= this.value[t])) ||
                ((order == ValueListOrder.DESCENDING) &&
                 (value <= this.value[t]))) {

                result = count;
                incrementArray();
                this.value[result] = value;
            } else {
                if (order == ValueListOrder.ASCENDING) {
                    while ((t >= 0) && (this.value[t] > value)) {
                        t--;
                    }
                } else {
                    while ((t >= 0) && (this.value[t] < value)) {
                        t--;
                    }
                }
                result = t + 1;

                incrementArray();
                for (t = (count - 1); t > (result); t--) {
                    this.value[t] = this.value[t - 1];
                }
                this.value[result] = value;
            }
        }
        statsOk = false;
        return result;
    }

    private class CompareValueIndex implements Comparator {
        public final int compare(int a, int b) {
            int result = (value[a] < value[b]) ? -1 :
                (value[a] > value[b]) ? 1 : 0;
            if (order == ValueListOrder.DESCENDING) {
                result = -result;
            }
            return result;
        }
    }

    /**
     * Renumbers all values in a ValueList class starting at zero.<br>
     * <b>Warning:</b>  Calling fillSequence removes any previous value in a
     * ValueList. <br>
     * Use fillSequence when deleting points at runtime.
     *
     * @see ValueList#sort
     */
    public void fillSequence() {
        for (int t = 0; t < count; t++) {
            value[t] = t;
        }
        statsOk = false;
    }

    public void exchange(int index1, int index2) {
        final double tmp = value[index1];
        value[index1] = value[index2];
        value[index2] = tmp;
    }
}

