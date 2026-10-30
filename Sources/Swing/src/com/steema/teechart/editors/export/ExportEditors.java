package com.steema.teechart.editors.export;

import javax.swing.JPanel;
import javax.imageio.ImageWriteParam;

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
public class ExportEditors extends JPanel {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected JPanel options;

    public ExportEditors() {
        options=null;
    }

    public JPanel getOptions() {
      return null;
    }

    public void setOptions(ImageWriteParam format)  {
      updateOptions(format);
    }

    protected void updateOptions(ImageWriteParam format) {
    }
}

