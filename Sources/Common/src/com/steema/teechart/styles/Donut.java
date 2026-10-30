package com.steema.teechart.styles;


import com.steema.teechart.languages.Language;
import com.steema.teechart.IBaseChart;

/**
 *
 * <p>Title: Donut class</p>
 *
 * <p>Description: Donut series.</p>
 *
 * <p>Example:
 * <pre><font face="Courier" size="4">
 * series = new com.steema.teechart.styles.Donut(myChart.getChart());
 * series.fillSampleValues(8);
 * series.setDonutPercent(50);
 * series.setCircled(true);
 * </font></pre></p>
 *
 * <p>Copyright (c) 2005-2019 by Steema Software SL. All Rights
 * Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 *
 */
public class Donut extends Pie {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	static final int DEFAULTDONUTPERCENT = 50;

    public Donut() {
        this((IBaseChart)null);
    }

    public Donut(IBaseChart c) {
        super(c);
        setDonutPercent(DEFAULTDONUTPERCENT);
    }
    
    public void assign(Series source) {
        if (source instanceof Donut) {
            Donut tmp = (Donut)source;
            
            this.setDonutPercent(tmp.DEFAULTDONUTPERCENT);
        }
        super.assign(source);
    }

    public void galleryChanged3D(boolean is3D) {
        super.galleryChanged3D(is3D);
        setCircled(true);
    }

    /**
     * The dimension of the middle hole.
     * Default value: DefaultDonutPercent
     *
     * @return int
     */
    public int getDonutPercent() {
        return iDonutPercent;
    }

    /**
     * Sets the dimension of the middle hole.
     * Default value: DefaultDonutPercent
     *
     * <p>Example:
     * <pre><font face="Courier" size="4">
     * series.setDonutPercent(75);
     * </font></pre></p>
     *
     * @param value int
     */
    public void setDonutPercent(int value) {
        super.setDonutPercent(value);
    }

    /**
     * Gets descriptive text.
     *
     * @return String
     */
    public String getDescription()
    {
        return Language.getString("GalleryDonut");
    }
}

