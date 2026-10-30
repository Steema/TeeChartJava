package com.steema.teechart.styles;


import com.steema.teechart.languages.Language;
import com.steema.teechart.IBaseChart;

/**
 *
 * <p>Title: Polar class</p>
 *
 * <p>Description: Polar Series.</p>
 *
 * <p>Copyright (c) 2005-2019 by Steema Software SL. All Rights
 * Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 *
 */
public class Polar extends CustomPolar {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Polar() {
        this(null);
    }

    public Polar(IBaseChart c) {
        super(c);
    }

    protected void addSampleValues(int numValues) {
        double tmp = 360.0 / numValues;
        SeriesRandom r = randomBounds(numValues);
        for (int t = 1; t <= numValues; t++) {
            add(t * tmp, // Angle
                1 + (1000 * r.Random())); // Value (Radius)
        }
    }

    /**
     * Gets descriptive text.
     *
     * @return String
     */
    public String getDescription() {
        return Language.getString("GalleryPolar");
    }

}

