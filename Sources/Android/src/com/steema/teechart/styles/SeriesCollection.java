package com.steema.teechart.styles;

import java.util.ArrayList;
import com.steema.teechart.IBaseChart;

/**
 *
 * <p>Title: SeriesCollection class</p>
 *
 * <p>Description: The SeriesCollection class, a collection of Series objects,
 * is manipulated via the TChart TChart.getSeries() method.</p>
 *
 * <p>Copyright (c) 2005-2019 by Steema Software SL. All Rights Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 *
 * @see com.steema.teechart.TChart#getSeries
 */
public final class SeriesCollection extends ArrayList {

    transient public IBaseChart chart;

    private boolean applyZOrder = true;

    public SeriesCollection() {
        this(null);
    }

    public SeriesCollection(IBaseChart c) {
        super();
        chart = c;
    }

    public void internalAdd(Series s) {
       super.add(s);
    }

    public Series add(Class type) throws InstantiationException,
            IllegalAccessException {
        return add((Series) (type.newInstance()));
    }

    /**
     * Adds a new Series instance to Chart.
     *
     * @param s Series The Series instance to add.
     * @return Series The same Series.
     */
    public Series add(Series s) {
        if (indexOf(s) == -1) {
            super.add(s);
            chart.broadcastEvent(s, SeriesEventStyle.ADD);

            s.setChart(chart); //CDI always set the chart
                               //otherwise overridden setChart calls 
                               //ignored.
            s.added();
        }

        return s;
    }

    public Series getSeries(int index) {
        return (index < size()) ? (Series) get(index) : null;
    }

    public void setSeries(int index, Series value) {
        set(index, value);
    }

    public void insert(int index, Series s) {
        /** @todo VALID? */
        add(index, s);
    }

    public void moveTo(Series s, int index) {
        int old = indexOf(s);

        if (old != index) {
            Object tmp = get(index);
            
            this.insert(index, s);
            if (old > index) old++;
            if (old != -1) this.remove(old);
            s.repaint();
        }
    }

    /**
     * Returns the corresponding point index which has the specified Value.
     *
     * @param s Series
     * @return int
     */
    public int indexOf(Series s) {
        for (int t = 0; t < size(); t++) {
            if (getSeries(t) == s) {
                return t;
            }
        }
        return -1;
    }

    public Series withTitle(String title) {
        for (int t = 0; t < size(); t++) {
            Series s = getSeries(t);
            if (s.toString().equals(title)) {
                return s;
            }
        }
        return null;
    }

    /**
     * Deletes the specified Series from the Chart list of series.
     *
     * @param s Series
     */
    public void remove(Series s) {
        //s.NotifyDesignerHost();
        int i = indexOf(s);
        if (i > -1) {
            chart.broadcastEvent(s, SeriesEventStyle.REMOVE);
            // s.Removed();
            remove(i);
            chart.invalidate();
        }
    }

    /**
     * Removes all Series from the Chart but  does not dispose of (destroy)
     * them.
     */
    public void removeAllSeries() {
        while (size() > 0) {
            remove(0);
        }
    }

    /**
     * Changes the Series order, swapping one Series Z position with another.<br>
     * The Chart repaints to reflect the new Series order. <br>
     * It accesses TChart.SeriesList method.
     *
     * @param series1 int
     * @param series2 int
     */
    public void exchange(int series1, int series2) {
        Series s1 = getSeries(series1);
        Series s2 = getSeries(series2);
        setSeries(series1, s2);
        setSeries(series2, s1);
        chart.broadcastEvent(null, SeriesEventStyle.SWAP);
        chart.invalidate();
    }

    /**
     * Removes (and optionally disposes) all Series.
     *
     * @param dispose boolean
     */
    public void clear(boolean dispose) {
        while (size() > 0) {
            Series tmp = getSeries(0);
            remove(0);

            tmp.onDisposing();

            if (dispose) {
                tmp.dispose(); // DB
            }
        }

        super.clear();
        chart.invalidate();
        chart.broadcastEvent(null, SeriesEventStyle.REMOVEALL);
    }

    /**
     * Removes and disposes all Series.
     */
    public void clear() {
        clear(true);
    }

    /**
     * Defines the Chart component.
     *
     * @return IBaseChart
     */
    public IBaseChart getChart() {
        return chart;
    }

    public boolean activeUseAxis() {
        for (int t = 0; t < size(); t++) {
            Series s = getSeries(t);
            if (s.getActive()) {
                return s.getUseAxis();
            }
        }
        return true;
    }

    /**
     * Sets multiple Series on same Chart in different Z spaces when true.<br>
     * Run-time only. <br>
     * It's valid only when TChart.View3D is true and when there's more than
     * one Series in the same chart.<br>
     * When false, all Series are drawn using the full Chart Z space. The
     * Chart output can be confusing if Series overlap. <br>
     * Default value: true
     *
     * @return boolean
     */
    public boolean getApplyZOrder() {
        return applyZOrder;
    }

    /**
     * Sets multiple Series on same Chart in different Z spaces when true.<br>
     * Run-time only. <br>
     * Default value: true
     *
     * @param value boolean
     */
    public void setApplyZOrder(boolean value) {
        applyZOrder = value;
        if (chart != null) {
            chart.invalidate();
        }
    }

    /**
     * Adds random values to all series in the collection to help visualize the Series.<br>
     * Each series class determines how many random points to add.
     */
    public void fillSampleValues() {
        for (int t = 0; t < size(); t++) {
            getSeries(t).fillSampleValues();
        }
    }

    /**
     * Adds the specified NumValues random points to all series in the collection.
     *
     * @param numValues int the number of sample values to add.
     */
    public void fillSampleValues(int numValues) {
        for (int t = 0; t < size(); t++) {
            getSeries(t).fillSampleValues(numValues);
        }
    }

    public void setChart(IBaseChart chart) {
        this.chart = chart;

        for (int t = 0; t < size(); t++) {
            getSeries(t).setChart(chart);
        }
    }
}

