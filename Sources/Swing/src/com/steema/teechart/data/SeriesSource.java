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

    private ISeries iSeries;

    public SeriesSource() {
        super();
    }

    /**
     * The Series that has this component as DataSource.
     *
     * @return ISeries
     */
    public ISeries getSeries() {
        return iSeries;
    }

    /**
     * The Series that has this component as DataSource.
     *
     * @param value ISeries
     */
    public void setSeries(ISeries value) {
        iSeries = value;
    }

    public void dispose() {
        // Sets series DataSource to null when disposing.
        if ((getSeries() != null) && (getSeries().getDataSource() == this)) {
            getSeries().setDataSource(null);
        }
    }

    /**
     * Fills Series with data from source.
     */
    public void refreshData() {}
}

