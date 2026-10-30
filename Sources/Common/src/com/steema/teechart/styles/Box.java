package com.steema.teechart.styles;


import com.steema.teechart.languages.Language;
import com.steema.teechart.IBaseChart;

/**
 *
 * <p>Title: Box class</p>
 *
 * <p>Description: The Box Series plots a vertical box series.<br><br>
 * The p-th percentile (ranging from 0 to 1.0) is computed as<br><br>
 * Zp = (1-d)*X[k] + d*X[k+1], <br><br>
 * where k+1 equals the integer part of P(n+1), d is the fractional part of<br>
 * p(n+1), and X[k-1] is the kth observation when the data are sorted from<br>
 * lowest to highest.<br></p>
 *
 * <p>Example:
 * <pre><font face="Courier" size="4">
 * series1 = new com.steema.teechart.styles.Box(myChart.getChart());
 * series1.add(new int[] {3,6,8,15,19,21});
 * series2 = new com.steema.teechart.styles.Box(myChart.getChart());
 * series2.add(new int[] {3,6,8,15,19,21});
 * series2.setPosition(1);
 * </font></pre></p>
 *
 * <p>Copyright (c) 2005-2019 by Steema Software SL. All Rights
 * Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 *
 */
public class Box extends CustomBox {

    public Box() {
        this((IBaseChart)null);
    }

    public Box(IBaseChart c) {
        super(c);
    }

    /**
     * The Maximum Value of the Series X Values List.
     *
     * @return double
     */
    public double getMaxXValue() {
        return dPosition;
    }

    /**
     * The Minimum Value of the Series X Values List.
     *
     * @return double
     */
    public double getMinXValue() {
        return dPosition;
    }

    /**
     * Gets descriptive text.
     *
     * @return String
     */
    public String getDescription() {
        return Language.getString("GalleryBoxPlot");
    }
}



