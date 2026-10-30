package com.steema.teechart.exports;

//import javax.imageio.plugins.bmp.BMPImageWriteParam;
//import java.util.Locale;

import com.steema.teechart.IBaseChart;
import javax.imageio.ImageWriteParam;

/**
 * <p>Title: TeeChart for Java</p>
 *
 * <p>Description: TeeChart for Java</p>
 *
 * <p>Copyright  (c) 2004-2019 by Steema Software SL. All Rights Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 *
 */
public final class BMPFormat extends ImageExportFormat {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public BMPFormat(IBaseChart chart) {
        super(chart);
        //format = new BMPImageWriteParam(Locale.getDefault());
        fileExtension = "bmp";
    }

    public ImageWriteParam getOptions() {
        return (ImageWriteParam) format;
    }

    public void setOptions(ImageWriteParam params) {
        format = params;
    }
}

