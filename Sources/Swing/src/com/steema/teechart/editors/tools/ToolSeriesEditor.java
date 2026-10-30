package com.steema.teechart.editors.tools;

import java.awt.Rectangle;
import com.steema.teechart.languages.Language;
import java.awt.event.*;

import javax.swing.*;
import com.steema.teechart.tools.*;

import com.steema.teechart.editors.BaseToolsEditor;

/**
 * <p>Title: ToolSeriesEditor class</p>
 *
 * <p>Description: Tool Series Editor panel</p>
 *
 * <p>Copyright (c) 2004-2019 by Steema Software SL. All Rights Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 *
 */
public class ToolSeriesEditor extends BaseToolsEditor {

    public ToolSeriesEditor() {
        try {
            jbInit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    // to review public void setTool(ToolSeries t/*, System.Type include*/) // internal
    public void setTool(Tool t) {
        setting = true;
        toolSeries = (ToolSeries) t;

        cbSeries.removeAllItems();

        cbSeries.addItem(Language.getString("None"));
        for (int i = 0; i < toolSeries.getChart().getSeriesCount(); ++i) {
            if (!toolSeries.getChart().getSeries(i).InternalUse) {
                cbSeries.addItem(toolSeries.getChart().getSeries(i).toString());
            }

            /*       .. having parameter System.Type
             if(include==null)
             cbSeries.addItem(toolSeries.getChart().getSeries(i).toString());
             else
             if (toolSeries.getChart().getSeries(i).getType() == include)
             cbSeries.addItem(toolSeries.getChart().getSeries(i).toString());
             */
        }

        if (toolSeries.getSeries() != null) {
            cbSeries.setSelectedItem(toolSeries.getSeries().toString());
        } else {
            cbSeries.setSelectedIndex(0);
        }

        setting = false;
    }

    private void jbInit() throws Exception {
        this.setLayout(null);
        cbSeries.setBounds(new Rectangle(75, 14, 139, 23));
        cbSeries.addActionListener(new ToolSeriesEditor_cbSeries_actionAdapter(this));
        jLabel1.setDisplayedMnemonic(Language.getString("S").charAt(0));
        jLabel1.setHorizontalAlignment(SwingConstants.RIGHT);
        jLabel1.setText(Language.getString("Series"));
        jLabel1.setBounds(new Rectangle(20, 19, 44, 15));
        this.add(cbSeries);
        this.add(jLabel1);
    }

    boolean setting;
    private ToolSeries toolSeries;
    JComboBox cbSeries = new JComboBox();
    JLabel jLabel1 = new JLabel();

    public void cbSeries_actionPerformed(ActionEvent e) {
        if (!setting) {
            if (cbSeries.getSelectedIndex() == -1 || cbSeries.getSelectedIndex() == 0) {
                toolSeries.setSeries(null);
            } else {
                toolSeries.setSeries(toolSeries.getChart().getSeries(cbSeries.getSelectedIndex() - 1));
            }
        }
    }
}

class ToolSeriesEditor_cbSeries_actionAdapter implements ActionListener {

    private ToolSeriesEditor adaptee;

    ToolSeriesEditor_cbSeries_actionAdapter(ToolSeriesEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.cbSeries_actionPerformed(e);
    }
}
