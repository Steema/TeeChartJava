package com.steema.teechart.styles;


import com.steema.teechart.languages.Language;
import com.steema.teechart.IBaseChart;

/**
 *
 * <p>Title: HorizBox class</p>
 *
 * <p>Description: The Box Series plots an horizontal box series.</p>
 *
 * <p>Copyright (c) 2005-2019 by Steema Software SL. All Rights
 * Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 *
 */
public class HorizBox extends CustomBox {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public HorizBox() {
        this((IBaseChart)null);
    }

    public HorizBox(IBaseChart c) {
        super(c);
        setHorizontal();
        iVertical = false;
    }

    /**
     * The Maximum Value of the Series Y Values List.
     *
     * @return double
     */
    public double getMaxYValue() {
        return dPosition;
    }

    /**
     * The Minimum Value of the Series Y Values List.<br>
     * As some Series have more than one Y Values List, this Minimum Value is
     * the "Minimum of Minimums" of all Series Y Values lists. <br>
     *
     * @return double
     */
    public double getMinYValue() {
        return dPosition;
    }

    /**
     * Gets descriptive text.
     *
     * @return String
     */
    public String getDescription() {
        return Language.getString("GalleryHorizBoxPlot");
    }
}

