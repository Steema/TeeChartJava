package com.steema.teechart.editors.export;

import javax.imageio.ImageWriteParam;
import javax.swing.JPanel;

import com.steema.teechart.exports.editors.PNGOptions;

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
public class PNGEditor extends ExportEditors {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public JPanel getOptions() {
        if (options == null) {
            options = new PNGOptions();
        }

        return options;
    }

    protected void updateOptions(ImageWriteParam format) {
        ImageWriteParam f = (ImageWriteParam) format;
        PNGOptions ops = (PNGOptions) options;

// TODO      f.Compression=ops.Compression;
// TODO      f.GrayScale=ops.GrayScale;
    }
}

