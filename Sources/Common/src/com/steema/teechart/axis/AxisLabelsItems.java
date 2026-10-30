package com.steema.teechart.axis;

import java.util.ArrayList;

/**
 * Custom labels list
 *
 * <p>Title: AxisLabelsItems class</p>
 *
 * <p>Description: Custom labels list</p>
 *
 * <p>Copyright (c) 2005-2019 by Steema Software SL. All Rights Reserved.</p>
 *
 * <p>Company: Steema Software SL </p>
 *
 */
public class AxisLabelsItems extends ArrayList {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	transient protected Axis iAxis;

    public AxisLabelsItems(Axis a) {
        super();
        iAxis = a;
    }

    /**
     * Accesses indexed Label characteristics
     *
     * @param index int
     * @return AxisLabelItem
     */
    public AxisLabelItem getItem(int index) {
        return (AxisLabelItem)get(index);
    }

    /**
     * Adds new label
     *
     * @param value double The axis data value
     * @return  new AxisLabelItem
     */
    public AxisLabelItem add(double value) {

        AxisLabelItem result = new AxisLabelItem(iAxis.chart);
        result.iAxisLabelsItems = this;
        result.setTransparent(true);
        result.setValue(value);

        super.add(result);

        return result;
    }


    /**
     * Adds a new label with text
     *
     * @param value double The axis data value
     * @param text String Label text
     * @return  new AxisLabelItem
     */
    public AxisLabelItem add(double value, String text) {
        AxisLabelItem result = add(value);
        result.setText(text);
        return result;
    }

    public void copyFrom(AxisLabelsItems source) {
        clear();

        for (int t = 0; t < source.size(); t++) {
            add(source.getItem(t).getValue(), source.getItem(t).getText());
        }
    }

    /**
     * Clears custom labels list
     *
     */
    public void clear() {
        super.clear();
        iAxis.chart.invalidate();
    }
}



