package com.steema.teechart.editors.export;

import javax.swing.JPanel;

import javax.imageio.ImageWriteParam;
import javax.imageio.plugins.jpeg.JPEGImageWriteParam;

import com.steema.teechart.exports.editors.JPEGOptions;

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
public class JPEGEditor extends ExportEditors {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public JPanel getOptions() {
        if (options == null) {
            options = new JPEGOptions();
        }

        return options;
    }

    protected void updateOptions(ImageWriteParam format) {
        JPEGImageWriteParam f = (JPEGImageWriteParam) format;
        JPEGOptions ops = (JPEGOptions) options;

        f.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
        f.setCompressionQuality(ops.getQuality());
    }
}

