package com.steema.teechart.editors.export;

import javax.swing.JPanel;

import javax.imageio.ImageWriteParam;
//import javax.imageio.plugins.bmp.BMPImageWriteParam;

import com.steema.teechart.exports.editors.BMPOptions;

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
public class BMPEditor extends ExportEditors {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public JPanel getOptions() {
        if (options == null) {
            options = new BMPOptions();
        }

        return options;
    }

    protected void updateOptions(ImageWriteParam format) {
  //      BMPImageWriteParam f = (BMPImageWriteParam) format;
    }
}

