package com.steema.teechart.editors;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import com.steema.teechart.styles.Series;

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
public abstract class BaseSeriesEditor extends JPanel {

    public BaseSeriesEditor() {
        super();
    }

    public abstract void setSeries(Series series, JTabbedPane tabs);
}

