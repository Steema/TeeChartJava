package com.steema.teechart.exports;

import javax.imageio.plugins.jpeg.JPEGImageWriteParam;
import java.util.Locale;

import com.steema.teechart.IBaseChart;

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

public final class GIFFormat extends ImageExportFormat {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public GIFFormat(IBaseChart chart) {
        super(chart);
        format = new JPEGImageWriteParam(Locale.getDefault());
// TODO        format = new GIFImageWriteParam(Locale.getDefault());
// TODO        format.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
        fileExtension = "gif";
    }

    /* TODO    public GIFImageWriteParam getGIFOptions() {
          return (GIFImageWriteParam)format;
        }


        public void setGIFOptions(GIFImageWriteParam params) {
          format=params;
        }
     */
}

