package com.steema.teechart.editors;

import java.awt.*;
import java.awt.Rectangle;
import java.awt.event.*;
import java.util.EventListener;

import javax.swing.*;
import javax.swing.event.*;

import com.steema.teechart.tools.Tool;
import com.steema.teechart.Chart;

import com.steema.teechart.tools.ToolsCollection;
import com.steema.teechart.languages.Language;


/**
 * <p>Title: ToolsEditor class</p>
 *
 * <p>Description: Tools Editor</p>
 *
 * <p>Copyright (c) 2004-2019 by Steema Software SL. All Rights Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 *
 */
public class ToolsEditor extends JPanel {


    private ToolsCollection tools;

    public ToolsEditor() {
        try {
            jbInit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public ToolsEditor(ToolsCollection tools) {
        this();
        this.tools = tools;

        SeriesEditor.setUpDownIcons(bUp, bDown);

        lbTools.setModel(new DefaultListModel());
        fillAndSet();

        lbTools.setCellRenderer(new Renderer());
    }

    public ToolsEditor(Chart chart) {
        this(chart.getTools());
    }

    public ToolsEditor(Chart chart, Tool tool) {
        this(chart);
        int n = com.steema.teechart.misc.Utils.toolTypeIndex(tool);
        lbTools.setSelectedIndex(n);
    }

    /**
     * fillAndSet
     */
    private void fillAndSet() {
        DefaultListModel tmp = (DefaultListModel) lbTools.getModel();
        tmp.clear();

        for (int t = 0; t < tools.size(); t++) {
            tmp.addElement(tools.getTool(t));
        }

        bDelete.setEnabled(!tmp.isEmpty());

        if (tmp.size() > 0) {
            lbTools.setSelectedIndex(0);
        }

        enableButtons();
    }

    /**
     * enableButtons
     */
    private void enableButtons() {
        bUp.setEnabled(lbTools.getSelectedIndex() > 0);
        bDown.setEnabled(lbTools.getSelectedIndex() <
                         lbTools.getModel().getSize() - 1);
    }

    private void jbInit() throws Exception {
        this.setLayout(borderLayout1);
        jPanel1.setPreferredSize(new Dimension(10, 40));
        jPanel1.setLayout(null);
        bAdd.setBounds(new Rectangle(10, 7, 75, 23));
        bAdd.setMnemonic(Language.getString("A").charAt(0));
        bAdd.setText(Language.getString("Add"));
        bAdd.addActionListener(new ToolsEditor_bAdd_actionAdapter(this));
        bDelete.setBounds(new Rectangle(99, 7, 75, 23));
        bDelete.setMnemonic(Language.getString("D").charAt(0));
        bDelete.setText(Language.getString("Delete"));
        bDelete.addActionListener(new ToolsEditor_bDelete_actionAdapter(this));
        cbActive.setEnabled(false);
        cbActive.setMnemonic(Language.getString("C").charAt(0));
        cbActive.setText(Language.getString("Active"));
        cbActive.setBounds(new Rectangle(193, 6, 81, 23));
        cbActive.addActionListener(new ToolsEditor_cbActive_actionAdapter(this));
        bUp.setBounds(new Rectangle(275, 6, 31, 25));
        bUp.addActionListener(new ToolsEditor_bUp_actionAdapter(this));
        bDown.setBounds(new Rectangle(315, 6, 32, 25));
        bDown.addActionListener(new ToolsEditor_bDown_actionAdapter(this));
        jSplitPane1.setRightComponent(null);
        lbTools.addListSelectionListener(new
                                         ToolsEditor_tools_listSelectionAdapter(this));
        toolEditor.setLayout(borderLayout2);
        this.add(jPanel1, java.awt.BorderLayout.NORTH);
        jPanel1.add(bAdd);
        jPanel1.add(bDelete);
        jPanel1.add(cbActive);
        jPanel1.add(bUp);
        jPanel1.add(bDown);
        this.add(jSplitPane1, java.awt.BorderLayout.CENTER);
        jSplitPane1.add(lbTools, JSplitPane.LEFT);
        jSplitPane1.add(toolEditor, JSplitPane.RIGHT);
        jSplitPane1.setDividerLocation(125);
    }

    BorderLayout borderLayout1 = new BorderLayout();
    JPanel jPanel1 = new JPanel();
    JButton bAdd = new JButton();
    JButton bDelete = new JButton();
    JCheckBox cbActive = new JCheckBox();
    JSplitPane jSplitPane1 = new JSplitPane();
    JList lbTools = new JList();
    JButton bUp = new JButton();
    JButton bDown = new JButton();
    JPanel toolEditor = new JPanel();
    BorderLayout borderLayout2 = new BorderLayout();

    public void bUp_actionPerformed(ActionEvent e) {
        swap(lbTools.getSelectedIndex(), lbTools.getSelectedIndex() - 1);
    }

    private void swap(int oldIndex, int newIndex) {
        Tool old = tools.getTool(oldIndex);
        tools.setTool(oldIndex, tools.getTool(newIndex));
        tools.setTool(newIndex, old);
        fillAndSet();
        lbTools.setSelectedIndex(newIndex);
    }

    public void bDown_actionPerformed(ActionEvent e) {
        swap(lbTools.getSelectedIndex(), lbTools.getSelectedIndex() + 1);
    }

    private Tool getCurrentTool() {
        return (Tool) lbTools.getSelectedValue();
    }

    public void tools_valueChanged(ListSelectionEvent e) {
        Tool currentTool = getCurrentTool();
        if (currentTool != null) {
           int n = com.steema.teechart.misc.Utils.toolTypeIndex(currentTool);
            Class editorClass = Utils.toolsEditorsOf[n];

            if (editorClass == null) {
                toolEditor.removeAll();
            } else {
                BaseToolsEditor editor = null;
                try {
                    editor = (BaseToolsEditor) editorClass.newInstance();
                } catch (IllegalAccessException ex) {
                } catch (InstantiationException ex) {
                }

                toolEditor.removeAll();
                toolEditor.add(editor, BorderLayout.CENTER);

                editor.setTool(currentTool);

            }

            cbActive.setEnabled(true);
            cbActive.setSelected(currentTool.getActive());

            enableButtons();
        }
    }

    // Internal event
    interface ToolListener extends EventListener {
        public void toolAdded(Tool value);

        public void toolRemoved(Tool value);
    }


    private ToolListener listener;

    public synchronized void addToolListener(ToolListener l) {
        listener = l;
    }

    public void bDelete_actionPerformed(ActionEvent e) {

        int tmp = lbTools.getSelectedIndex();

        if ((getCurrentTool() != null) &&
            (Utils.yesNoDelete(getCurrentTool().getDescription()))) {

            if (listener != null) {
                listener.toolRemoved(getCurrentTool());
            }

            getCurrentTool().setChart(null);
            deleteForm();
            fillAndSet();

            if (tmp > 1) {
                lbTools.setSelectedIndex(tmp - 1);
            } else

            if (lbTools.getModel().getSize() > 0) {
                lbTools.setSelectedIndex(lbTools.getModel().getSize() - 1);
            }

            bDelete.setEnabled(lbTools.getSelectedIndex() != -1);

            if (lbTools.getSelectedIndex() != -1) {
                tools_valueChanged(null);
            }
        }
    }

    private void deleteForm() {
        toolEditor.removeAll();
        toolEditor.updateUI();
    }

    public void bAdd_actionPerformed(ActionEvent e) {
        Tool tool = ToolsGallery.showGallery(this, tools.chart);
        if (tool != null) {

            if (listener != null) {
                listener.toolAdded(tool);
            }

            fillAndSet();
            lbTools.setSelectedIndex(lbTools.getModel().getSize() - 1);
        }
    }

    public void cbActive_actionPerformed(ActionEvent e) {
        //cbActive.setSelected(getCurrentTool().getActive());
//        cbActive.setSelected(getCurrentTool().getActive());
        getCurrentTool().setActive(cbActive.isSelected());

    }
}


final class ToolsEditor_cbActive_actionAdapter implements ActionListener {
    private ToolsEditor adaptee;
    ToolsEditor_cbActive_actionAdapter(ToolsEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.cbActive_actionPerformed(e);
    }
}


final class ToolsEditor_bAdd_actionAdapter implements ActionListener {
    private ToolsEditor adaptee;
    ToolsEditor_bAdd_actionAdapter(ToolsEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.bAdd_actionPerformed(e);
    }
}


final class ToolsEditor_bDelete_actionAdapter implements ActionListener {
    private ToolsEditor adaptee;
    ToolsEditor_bDelete_actionAdapter(ToolsEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.bDelete_actionPerformed(e);
    }
}


final class ToolsEditor_tools_listSelectionAdapter implements
        ListSelectionListener {
    private ToolsEditor adaptee;
    ToolsEditor_tools_listSelectionAdapter(ToolsEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void valueChanged(ListSelectionEvent e) {
        adaptee.tools_valueChanged(e);
    }
}


final class ToolsEditor_bDown_actionAdapter implements ActionListener {
    private ToolsEditor adaptee;
    ToolsEditor_bDown_actionAdapter(ToolsEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.bDown_actionPerformed(e);
    }
}


final class ToolsEditor_bUp_actionAdapter implements ActionListener {
    private ToolsEditor adaptee;
    ToolsEditor_bUp_actionAdapter(ToolsEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.bUp_actionPerformed(e);
    }
}

