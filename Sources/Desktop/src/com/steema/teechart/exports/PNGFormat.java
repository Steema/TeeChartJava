package com.steema.teechart.exports;

import com.steema.teechart.IBaseChart;
import java.util.Locale;
import javax.imageio.plugins.jpeg.JPEGImageWriteParam;

/**
 * <p>Title: TeeChart for Java</p>
 *
 * <p>Description: TeeChart for Java</p>
 *
 * <p>Copyright (c) 2004-2019 by Steema Software SL. All Rights
 * Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 *
 */
public class PNGFormat extends ImageExportFormat {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public PNGFormat(IBaseChart chart) {
        super(chart);

        //loads JPEGImageWriteParams for progressive support
        format = new JPEGImageWriteParam(Locale.getDefault());
        fileExtension = "png";
    }
}

