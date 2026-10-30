package com.steema.teechart.editors;

import javax.swing.JPanel;

import com.steema.teechart.functions.Function;

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
public abstract class BaseFunctionsEditor extends JPanel {

    public BaseFunctionsEditor() {
        super();
    }

    public abstract void setFunction(Function function);
}

