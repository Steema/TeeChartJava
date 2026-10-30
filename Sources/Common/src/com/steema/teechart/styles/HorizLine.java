package com.steema.teechart.styles;


import com.steema.teechart.languages.Language;
import com.steema.teechart.IBaseChart;

/**
 *
 * <p>Title: HorizLine class</p>
 *
 * <p>Description: Horizontal Line Series.</p>
 *
 * <p>Example:
 * <pre><font face="Courier" size="4">
 *  lineSeries = new com.steema.teechart.styles.HorizLine(myChart.getChart());
 *  lineSeries.fillSampleValues(8);
 *  lineSeries.getPointer().setVisible(true);
 * </font></pre></p>
 *
 * <p>Copyright (c) 2005-2019 by Steema Software SL. All Rights
 *  Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 *
 */
public class HorizLine extends Line {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public HorizLine(IBaseChart c) {
        super(c);
        setHorizontal();
        getPointer().setDefaultVisible(false);
        calcVisiblePoints = false;
        getXValues().setOrder(ValueListOrder.NONE);
        getYValues().setOrder(ValueListOrder.ASCENDING);
    }

    public HorizLine() {
        this((IBaseChart)null);
    }

    /**
     * Gets descriptive text.
     *
     * @return String
     */
    public String getDescription() {
        return Language.getString("GalleryHorizLine");
    }
}

