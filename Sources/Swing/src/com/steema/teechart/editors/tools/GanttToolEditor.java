package com.steema.teechart.editors.tools;

import java.awt.Rectangle;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.event.*;

import com.steema.teechart.Cursor;
import com.steema.teechart.editors.tools.ToolSeriesEditor;
import com.steema.teechart.editors.Utils;

import com.steema.teechart.tools.Tool;
import com.steema.teechart.tools.GanttTool;
import com.steema.teechart.languages.Language;

/**
 * <p>Title: GanttToolEditor class</p>
 *
 * <p>Description: GanttTool Editor panel</p>
 *
 * <p>Copyright (c) 2004-2019 by Steema Software SL. All Rights Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 *
 */
public class GanttToolEditor extends ToolSeriesEditor {


    public GanttToolEditor() {
        try {
            jbInit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void setTool(Tool tool) {
        ganttTool=(GanttTool)tool;
        super.setTool(ganttTool);

        cbAllowDrag.setSelected(ganttTool.getAllowDrag());
        cbAllowResize.setSelected(ganttTool.getAllowResize());
        spMinPixels.setValue(new Integer(ganttTool.getMinPixels()));

        Utils.addCursorsToCombo(cbCursorDrag);
        Utils.addCursorsToCombo(cbCursorResize);
    }

    private void jbInit() throws Exception {
        this.setLayout(null);
        cbAllowDrag.setMnemonic(Language.getString("D").charAt(0));
        cbAllowDrag.setText(Language.getString("Allow_Drag"));
        cbAllowDrag.setBounds(new Rectangle(14, 86, 97, 23));
        cbAllowDrag.addActionListener(new
                                      GanttToolEditor_cbAllowDrag_actionAdapter(this));
        cbAllowResize.setMnemonic(Language.getString("R").charAt(0));
        cbAllowResize.setText(Language.getString("Allow_Resize"));
        cbAllowResize.setBounds(new Rectangle(14, 139, 106, 23));
        cbAllowResize.addActionListener(new
                GanttToolEditor_cbAllowResize_actionAdapter(this));
        jLabel1.setToolTipText("");
        jLabel1.setDisplayedMnemonic(Language.getString("T").charAt(0));
        jLabel1.setHorizontalAlignment(SwingConstants.RIGHT);
        jLabel1.setLabelFor(spMinPixels);
        jLabel1.setText(Language.getString("Resize_pixels"));
        jLabel1.setBounds(new Rectangle(10, 60, 123, 15));
        spMinPixels.setBounds(new Rectangle(142, 55, 51, 22));
        spMinPixels.addChangeListener(new
                                      GanttToolEditor_spMinPixels_changeAdapter(this));
        jLabel2.setDisplayedMnemonic(Language.getString("C").charAt(0));
        jLabel2.setHorizontalAlignment(SwingConstants.RIGHT);
        jLabel2.setLabelFor(cbCursorDrag);
        jLabel2.setText(Language.getString("Cursor"));
        jLabel2.setBounds(new Rectangle(42, 116, 49, 15));
        jLabel3.setDisplayedMnemonic(Language.getString("U").charAt(0));
        jLabel3.setHorizontalAlignment(SwingConstants.RIGHT);
        jLabel3.setLabelFor(cbCursorResize);
        jLabel3.setText(Language.getString("Cursor"));
        jLabel3.setBounds(new Rectangle(42, 172, 49, 15));
        cbCursorDrag.setBounds(new Rectangle(98, 112, 95, 23));
        cbCursorDrag.addActionListener(new
                GanttToolEditor_cbCursorDrag_actionAdapter(this));
        cbCursorResize.setBounds(new Rectangle(98, 165, 95, 23));
        cbCursorResize.addActionListener(new
                GanttToolEditor_cbCursorResize_actionAdapter(this));
        this.add(cbCursorResize);
        this.add(cbAllowDrag);
        this.add(cbCursorDrag);
        this.add(cbAllowResize);
        this.add(jLabel1);
        this.add(spMinPixels);
        this.add(jLabel3);
        this.add(jLabel2);
    }

    GanttTool ganttTool;
    JCheckBox cbAllowDrag = new JCheckBox();
    JCheckBox cbAllowResize = new JCheckBox();
    JLabel jLabel1 = new JLabel();
    JSpinner spMinPixels = new JSpinner();
    JLabel jLabel2 = new JLabel();
    JLabel jLabel3 = new JLabel();
    JComboBox cbCursorDrag = new JComboBox();
    JComboBox cbCursorResize = new JComboBox();

    public void spMinPixels_stateChanged(ChangeEvent e) {
        ganttTool.setMinPixels(((Integer)spMinPixels.getValue()).intValue());
    }

    public void cbAllowDrag_actionPerformed(ActionEvent e) {
        ganttTool.setAllowDrag(cbAllowDrag.isSelected());
    }

    public void cbAllowResize_actionPerformed(ActionEvent e) {
        ganttTool.setAllowResize(cbAllowResize.isSelected());
    }

    public void cbCursorDrag_actionPerformed(ActionEvent e) {
        ganttTool.setCursorDrag(new Cursor(cbCursorDrag.getSelectedIndex()));
    }

    public void cbCursorResize_actionPerformed(ActionEvent e) {
        ganttTool.setCursorResize(new Cursor(cbCursorResize.getSelectedIndex()));
    }
}


class GanttToolEditor_cbCursorResize_actionAdapter implements ActionListener {
    private GanttToolEditor adaptee;
    GanttToolEditor_cbCursorResize_actionAdapter(GanttToolEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.cbCursorResize_actionPerformed(e);
    }
}


class GanttToolEditor_cbCursorDrag_actionAdapter implements ActionListener {
    private GanttToolEditor adaptee;
    GanttToolEditor_cbCursorDrag_actionAdapter(GanttToolEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.cbCursorDrag_actionPerformed(e);
    }
}


class GanttToolEditor_cbAllowResize_actionAdapter implements ActionListener {
    private GanttToolEditor adaptee;
    GanttToolEditor_cbAllowResize_actionAdapter(GanttToolEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.cbAllowResize_actionPerformed(e);
    }
}


class GanttToolEditor_spMinPixels_changeAdapter implements ChangeListener {
    private GanttToolEditor adaptee;
    GanttToolEditor_spMinPixels_changeAdapter(GanttToolEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void stateChanged(ChangeEvent e) {
        adaptee.spMinPixels_stateChanged(e);
    }
}


class GanttToolEditor_cbAllowDrag_actionAdapter implements ActionListener {
    private GanttToolEditor adaptee;
    GanttToolEditor_cbAllowDrag_actionAdapter(GanttToolEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.cbAllowDrag_actionPerformed(e);
    }
}

