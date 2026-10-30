package com.steema.teechart.editors.tools;

import java.awt.Rectangle;
import java.awt.event.*;

import javax.swing.*;

import com.steema.teechart.tools.Tool;
import com.steema.teechart.tools.ExtraLegend;
import com.steema.teechart.editors.LegendEditor;
import com.steema.teechart.editors.tools.ToolSeriesEditor;
import com.steema.teechart.languages.Language;

/**
 * <p>Title: ExtraLegendEditor class</p>
 *
 * <p>Description: Extra Legend Editor panel</p>
 *
 * <p>Copyright (c) 2004-2019 by Steema Software SL. All Rights Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 *
 */
public class ExtraLegendEditor extends ToolSeriesEditor {


    public ExtraLegendEditor() {
        try {
            jbInit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void setTool(Tool tool) {
        extraLegend=(ExtraLegend)tool;
        super.setTool(extraLegend);
    }

    private void jbInit() throws Exception {
        this.setLayout(null);
        bEditLegend.setBounds(new Rectangle(55, 50, 132, 23));
        bEditLegend.setMnemonic(Language.getString("E").charAt(0));
        bEditLegend.setText(Language.getString("Edit_Legend_"));
        bEditLegend.addActionListener(new
                ExtraLegendEditor_bEditLegend_actionAdapter(this));
        this.add(bEditLegend);
    }

    ExtraLegend extraLegend;
    LegendEditor lEdit;
    JButton bEditLegend = new JButton();

    public void bEditLegend_actionPerformed(ActionEvent e) {
      LegendEditor.editLegend(null, extraLegend.getLegend());
    }
}


final class ExtraLegendEditor_bEditLegend_actionAdapter implements ActionListener {
    private ExtraLegendEditor adaptee;
    ExtraLegendEditor_bEditLegend_actionAdapter(ExtraLegendEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.bEditLegend_actionPerformed(e);
    }
}


