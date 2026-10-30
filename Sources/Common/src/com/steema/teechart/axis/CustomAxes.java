package com.steema.teechart.axis;

import java.util.ArrayList;
import com.steema.teechart.IBaseChart;

/**
 *
 * <p>Title: CustomAxes class</p>
 *
 * <p>Description: Used to access the Custom series List.</p>
 *
 * <p>Copyright (c) 2005-2019 by Steema Software SL. All Rights
 * Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 *
 */
public final class CustomAxes extends ArrayList {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	transient private IBaseChart chart;

    /**
     * Creates and adds a custom axis to the chart.
     *
     * @return Axis
     */
    public Axis getNew() {
        return add(new Axis(chart));
    }

    public void setChart(IBaseChart value) {
        chart = value;

        for (int t = 0; t < size(); t++) {
            getAxis(t).setChart(chart);
        }
    }

    /**
     * Adds a Custom Axis.
     *
     * @param axis Axis
     * @return Axis
     */
    public Axis add(Axis axis) {
        if (indexOf(axis) == -1) {
            super.add(axis);
        }
        axis.setChart(chart);
        return axis;
    }
    /**
     * Accesses Axis characteristics of corresponding index value.
     *
     * @param index int
     * @return Axis
     */
    public Axis getAxis(int index) {
        return (Axis) get(index);
    }
    /**
     * Sets Axis characteristics of corresponding index value.
     *
     * @param index int
     * @param value Axis
     */
    public void setAxis(int index, Axis value) {
        set(index, value);
    }
    /**
     * Returns the corresponding axis index which has the specified value.
     *
     * @param a Axis
     * @return int
     */
    public int indexOf(Axis a) {
        for (int t = 0; t < size(); t++) {
            if (get(t) == a) {
                return t;
            }
        }
        return -1;
    }


    /**
     * Removes the Custom Axis of Index 'CustomAxisIndex'.<br>
     * Any Series associated with the Axis are reset to their default Axes.
     *
     * @param a Axis
     */
    public void remove(Axis a) {
        int i = indexOf(a);
        if (i != -1) {
            remove(i);
        }
    }


    /**
     * Removes all Custom Axes.<br>
     * Any Series associated with the Axes are reset to their default Axes.
     */
    public void removeAll() {
        while (size() > 0) {
            remove(0);
        }
    }

    /*
         protected void OnInsertComplete(int index, Object value) {
        super.OnInsertComplete(index, value);
        ((Axis) value).Chart = Chart;
         }
     */
}

