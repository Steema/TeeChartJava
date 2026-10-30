package com.steema.teechart.editors;

import javax.swing.JPanel;

import com.steema.teechart.tools.Tool;

/**
 * <p>Title: TeeChart for Java</p>
 *
 * <p>Description: TeeChart for Java</p>
 *
 * <p>Copyright (c) 2004-2019 by Steema Software SL. All Rights Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 *
 */
public abstract class BaseToolsEditor extends JPanel {

    public BaseToolsEditor() {
        super();
    }

    public abstract void setTool(Tool tool);
}

