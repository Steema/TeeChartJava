package com.steema.teechart.tools;

import com.steema.teechart.IBaseChart;
import com.steema.teechart.styles.Series;
import com.steema.teechart.axis.Axis;

/**
 *
 * <p>Title: ToolSeries class</p>
 *
 * <p>Description: Base abstract class for Tool components with a Series method.
 * </p>
 *
 * <p>Copyright (c) 2005-2019 by Steema Software SL. All Rights Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 *
 */
public class ToolSeries extends Tool {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected Series iSeries;

    protected ToolSeries() {
        this((IBaseChart)null);
    }

    protected ToolSeries(IBaseChart c) {
        super(c);
    }

    public ToolSeries(Series s) {
        super(s.getChart());
        iSeries = s;
    }

    /**
     * Returns the associated Top or Bottom Chart Horizontal Axis.
     *
     * @return Axis
     */
    public Axis getHorizAxis() {
        return (iSeries == null) ? chart.getAxes().getBottom() : iSeries.getHorizAxis();
    }

    /**
     * Returns the associated Left or Right Chart Vertical Axis.
     *
     * @return Axis
     */
    public Axis getVertAxis() {
        return (iSeries == null) ? chart.getAxes().getLeft() : iSeries.getVertAxis();
    }

    /**
     * The Series with which Tools are associated.<br>
     * Default value: null
     *
     * @return Series
     */
    public Series getSeries() {
        return iSeries;
    }

    /**
     * Sets the Series with which Tools are associated.<br>
     * Default value: null
     *
     * @param value Series
     */
    public void setSeries(Series value) {
        iSeries = value;
        invalidate();
    }
}



