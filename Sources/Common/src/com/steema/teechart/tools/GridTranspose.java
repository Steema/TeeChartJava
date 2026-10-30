package com.steema.teechart.tools;


import com.steema.teechart.languages.Language;
import com.steema.teechart.IBaseChart;
import com.steema.teechart.styles.Custom3DGrid;

/**
 *
 * <p>Title: GridTranspose class</p>
 *
 * <p>Description: Transpose Grid 3D.</p>
 *
 * <p>Copyright (c) 2005-2019 by Steema Software SL. All Rights Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 *
 */
public class GridTranspose extends Tool {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Custom3DGrid series;

    public GridTranspose() {
        this((IBaseChart)null);
    }

    public GridTranspose(IBaseChart c) {
        super(c);
    }

    /**
     * Gets descriptive text.
     *
     * @return String
     */
    public String getDescription() {
        return Language.getString("GridTranspose");
    }

    /**
     * Gets detailed descriptive text.
     *
     * @return String
     */
    public String getSummary() {
        return Language.getString("GridTransposeSummary");
    }

    /**
     * Inverts (exchanges) X by Z values in a 3D grid series.
     */
    public void transpose() {
        if (getActive() && (series != null)) {
            Custom3DGrid tmp = new Custom3DGrid();
            try {
                for (int t = 0; t < series.getCount(); t++) {
                    tmp.add(series.getZValues().value[t], series.getYValues().value[t],
                            series.getXValues().value[t]);
                }

                series.assignValues(tmp);
            } finally {
                tmp.dispose();
            }
        }
    }

    /**
     * The 3D grid series to transpose.
     *
     * @return Custom3DGrid
     */
    public Custom3DGrid getSeries() {
        return series;
    }

    /**
     * Sets the 3D grid series to transpose.
     *
     * @param value Custom3DGrid
     */
    public void setSeries(Custom3DGrid value) {
        series = value;
    }
}

