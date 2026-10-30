package com.steema.teechart.styles;


import com.steema.teechart.languages.Language;
import com.steema.teechart.IBaseChart;
import com.steema.teechart.editors.gallery.Gallery;

/**
 *
 * <p>Title: Waterfall class</p>
 *
 * <p>Description: The Waterfall series draws vertical "slices", one for each
 * Z row of the equivalent surface.</p>
 *
 * <p>Example:
 * <pre><font face="Courier" size="4">
 * series = new Waterfall(myChart.getChart());
 * series.fillSampleValues(20);
 * series.setTimesZOrder(2);
 * </font></pre></p>
 *
 * <p>Copyright (c) 2005-2019 by Steema Software SL. All Rights Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 *
 */
public class Waterfall extends Surface {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public  String getDescription() {
        return Language.getString("GalleryWaterFall");
    }

    public Waterfall(IBaseChart c) {
        super(c);
        setWaterFall(true);
        getWaterLines().setVisible( true);
    }

    public Waterfall() {
        this((IBaseChart)null);
    }

    public void createSubGallery(Gallery addSubChart) {
        super.createSubGallery(addSubChart);
        addSubChart.createSubChart(Language.getString("NoLines"));
    }

    public  void setSubGallery(int index) {
        if (index == 6) {
            getWaterLines().setVisible( false);
        } else {
            super.setSubGallery(index);
        }
    }

}

