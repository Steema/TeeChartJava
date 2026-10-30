package com.steema.teechart.editors.export;

import javax.imageio.ImageWriteParam;
import javax.swing.JPanel;

import com.steema.teechart.exports.editors.GIFOptions;

//import javax.imageio.plugins.jpeg.JPEGImageWriteParam;

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
public class GIFEditor extends ExportEditors {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public JPanel getOptions() {
        if (options == null) {
            options = new GIFOptions();
        }

        return options;
    }

    protected void updateOptions(ImageWriteParam format) {
        ImageWriteParam f = (ImageWriteParam) format;
        GIFOptions ops=(GIFOptions) options;

// TODO   JPEGImageWriteParam f = (JPEGImageWriteParam) format;
// TODO   GIFFormat f=(GIFFormat)format;

// TODO   f.setColorReduction((GIFFormat.getGIFColorReduction())ops.getColorReduction());
    }
}

