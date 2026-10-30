package com.steema.teechart.editors;

import javax.swing.*;
import java.awt.BorderLayout;

import com.steema.teechart.Chart;

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
public class DataEditor extends JPanel {

    public DataEditor(Chart chart) {
        this();
        this.chart=chart;
        jTable1=new ChartGrid(chart);

        JScrollPane scrollPane = new JScrollPane(jTable1);
        this.add(scrollPane, java.awt.BorderLayout.CENTER);
    }

    public DataEditor() {
        try {
            jbInit();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    ChartGrid jTable1;

    private void jbInit() throws Exception {
        this.setLayout(borderLayout1);
    }

    private Chart chart;
    BorderLayout borderLayout1 = new BorderLayout();
}

