package com.steema.teechart.exports;

import javax.imageio.ImageWriteParam;
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
public final class JPEGFormat extends ImageExportFormat {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public JPEGFormat(IBaseChart chart) {
        super(chart);

        format = new JPEGImageWriteParam(Locale.getDefault());
        format.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
        fileExtension = "jpg";
    }

    public JPEGImageWriteParam getJPEGOptions() {
        return (JPEGImageWriteParam) format;
    }

    public void setJPEGOptions(JPEGImageWriteParam params) {
        format = params;
    }
}

