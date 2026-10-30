package com.steema.teechart.data;

import com.steema.teechart.styles.ISeries;

/**
 *
 * <p>Title: SeriesSource class</p>
 *
 * <p>Description: Abstract class to define Series.DataSource components.</p>
 *
 * <p>Copyright (c) 2005-2019 by Steema Software SL. All Rights Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 *
 */
public abstract class SeriesSource {

    protected ISeries series;

    public SeriesSource() {
        super();
    }

    /**
     * The Series that has this component as DataSource.
     *
     * @return ISeries
     */
    public final ISeries getSeries() {
        return series;
    }

    /**
     * The Series that has this component as DataSource.
     *
     * @param value ISeries
     */
    public void setSeries(ISeries value) {
        series = value;
    }

    protected void dispose(boolean disposing) {
        // Sets series DataSource to null when disposing.
        if ((disposing) && (series != null) && (series.getDataSource() == this)) {
            series.setDataSource(null);
        }
    }

    /**
     * Fills Series with data from source.
     */
    public abstract void refreshData();
}

