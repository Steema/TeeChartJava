package com.steema.teechart.editors;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Dimension;
import java.awt.BorderLayout;
import java.awt.Font;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import java.awt.Frame;
import java.awt.Container;
import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.KeyEvent;

import com.steema.teechart.misc.Utils;
import com.steema.teechart.tools.Tool;
import com.steema.teechart.tools.ToolSeries;
import com.steema.teechart.tools.ToolAxis;
import com.steema.teechart.IBaseChart;
import com.steema.teechart.languages.Language;

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
public class ToolsGallery extends JDialog {


    JPanel panel1 = new JPanel();
    BorderLayout borderLayout1 = new BorderLayout();
    JPanel jPanel1 = new JPanel();
    JButton bOk = new JButton();
    JButton jButton2 = new JButton();
    TitledBorder titledBorder1 = new TitledBorder("");
    JTabbedPane jTabbedPane1 = new JTabbedPane();
    JList lSeries = new JList();
    JList lAxis = new JList();
    JList lOther = new JList();
    JScrollPane paneSeries=new JScrollPane();
    JScrollPane paneAxis=new JScrollPane();
    JScrollPane paneOther=new JScrollPane();

    private boolean result;
    JPanel jPanel2 = new JPanel();
    BorderLayout borderLayout2 = new BorderLayout();
    JScrollPane jScrollPane1 = new JScrollPane();
    JTextArea summary = new JTextArea();

    public ToolsGallery(Frame owner, String title, boolean modal) {
        super(owner, title, modal);
        try {
            setDefaultCloseOperation(DISPOSE_ON_CLOSE);
            jbInit();
            pack();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public ToolsGallery() {
        this(new Frame(), Language.getString("ToolsGallery"), false);

        lSeries.setModel(new DefaultListModel());
        lAxis.setModel(new DefaultListModel());
        lOther.setModel(new DefaultListModel());
        fillTools();

        lSeries.setCellRenderer(new Renderer());
        lAxis.setCellRenderer(new Renderer());
        lOther.setCellRenderer(new Renderer());

        MouseAdapter tmp=new MouseAdapter() {

            private ToolsGallery getGallery(Container value) {
                do {
                    if (value instanceof ToolsGallery) {
                        return (ToolsGallery)value;
                    } else {
                        value=value.getParent();
                    }
                } while (value!=null);

                return null;
            }

            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    ToolsGallery tmp=getGallery((JList)e.getSource());
                    tmp.result=true;
                    tmp.dispose();
                }
            }
        };

        lSeries.addMouseListener(tmp);
        lAxis.addMouseListener(tmp);
        lOther.addMouseListener(tmp);

        // ESCAPE to close
        InputMap iMap = getRootPane().getInputMap(
                JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
        iMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), Language.getString("escape"));
        getRootPane().getActionMap().put(Language.getString("escape"), new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                jButton2_actionPerformed(null);
            }
        });

        setSize(250,400);
    }

    static public Tool showGallery(Container owner, IBaseChart chart) {
        ToolsGallery dialog = new ToolsGallery();
        dialog.setTitle(Language.getString("Chart_Tools_Gallery"));
        dialog.setModal(true);
        dialog.setLocationRelativeTo(owner);
        dialog.setVisible(true);

        Tool tool = null;

        if (dialog.result) {
            Class tmp = dialog.getCurrentTool().getClass();
            try {
                tool = (Tool) tmp.newInstance();
                tool.setChart(chart);
            } catch (IllegalAccessException ex) {
            } catch (InstantiationException ex) {
            }
        }

        return tool;
    }

    private Tool getCurrentTool() {
        return (Tool) getList().getSelectedValue();
    }

    private JList getList() {
        JScrollPane scroll=(JScrollPane) jTabbedPane1.getSelectedComponent();
        return (JList)(scroll.getViewport().getComponent(0));
    }

    private void fillTools() {

        for (int t = 0; t < Utils.TOOLTYPESCOUNT; t++) {

            JList tmpList=null;
            Tool tmp = null;

            try {
                tmp = (Tool) Utils.toolTypesOf[t].newInstance();
            } catch (IllegalAccessException ex) {
            } catch (InstantiationException ex) {
            }

            if (tmp!=null) {
                if (tmp instanceof ToolSeries) {
                   tmpList = lSeries;
                } else
                if (tmp instanceof ToolAxis) {
                    tmpList = lAxis;
                } else {
                    tmpList = lOther;
                }

                ((DefaultListModel) tmpList.getModel()).addElement(tmp);
            }
        }

        bOk.setEnabled(false);
    }

    private void jbInit() throws Exception {
        panel1.setLayout(borderLayout1);
        jPanel1.setPreferredSize(new Dimension(10, 84));
        jPanel1.setLayout(borderLayout2);
        bOk.setMnemonic(Language.getString("A").charAt(0));
        bOk.setSelected(true);
        bOk.setText(Language.getString("Add"));
        bOk.addActionListener(new ToolsGallery_bOk_actionAdapter(this));
        jButton2.setText(Language.getString("Cancel"));
        jButton2.addActionListener(new ToolsGallery_jButton2_actionAdapter(this));
        jTabbedPane1.addChangeListener(new
                                       ToolsGallery_jTabbedPane1_changeAdapter(this));
        lSeries.addListSelectionListener(new
                ToolsGallery_lSeries_listSelectionAdapter(this));
        lAxis.addListSelectionListener(new
                                       ToolsGallery_lAxis_listSelectionAdapter(this));
        lOther.addListSelectionListener(new
                ToolsGallery_lOther_listSelectionAdapter(this));
        jPanel2.setMinimumSize(new Dimension(135, 38));
        jPanel2.setPreferredSize(new Dimension(135, 38));
        summary.setFont(new java.awt.Font(Language.getString("Tahoma"), Font.PLAIN, 11));
        summary.setToolTipText("");
        summary.setLineWrap(true);
        summary.setWrapStyleWord(true);
        jScrollPane1.setHorizontalScrollBarPolicy(JScrollPane.
                                                  HORIZONTAL_SCROLLBAR_NEVER);
        getContentPane().add(panel1);
        panel1.add(jPanel1, java.awt.BorderLayout.SOUTH);
        jPanel2.add(bOk);
        jPanel2.add(jButton2);
        panel1.add(jTabbedPane1, java.awt.BorderLayout.CENTER);

        paneSeries.getViewport().add(lSeries, java.awt.BorderLayout.CENTER);
        paneAxis.getViewport().add(lAxis, java.awt.BorderLayout.CENTER);
        paneOther.getViewport().add(lOther, java.awt.BorderLayout.CENTER);

        jTabbedPane1.add(paneSeries, Language.getString("Series"));
        jTabbedPane1.add(paneAxis, Language.getString("Axis"));
        jTabbedPane1.add(paneOther, Language.getString("Other"));
        jPanel1.add(jPanel2, java.awt.BorderLayout.SOUTH);
        jPanel1.add(jScrollPane1, java.awt.BorderLayout.CENTER);
        jScrollPane1.getViewport().add(summary);
    }

    public void jTabbedPane1_stateChanged(ChangeEvent e) {
        bOk.setEnabled(getList().getSelectedIndex() != -1);

        Tool tmp=getCurrentTool();
        summary.setText((tmp==null) ? "" : tmp.getSummary());
    }

    public void bOk_actionPerformed(ActionEvent e) {
        result = true;
        dispose();
    }

    public void jButton2_actionPerformed(ActionEvent e) {
        dispose();
    }

    public void lSeries_valueChanged(ListSelectionEvent e) {
        jTabbedPane1_stateChanged(null);
    }

    public void lAxis_valueChanged(ListSelectionEvent e) {
        jTabbedPane1_stateChanged(null);
    }

    public void lOther_valueChanged(ListSelectionEvent e) {
        jTabbedPane1_stateChanged(null);
    }
}


final class ToolsGallery_bOk_actionAdapter implements ActionListener {
    private ToolsGallery adaptee;
    ToolsGallery_bOk_actionAdapter(ToolsGallery adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.bOk_actionPerformed(e);
    }
}


final class ToolsGallery_jButton2_actionAdapter implements ActionListener {
    private ToolsGallery adaptee;
    ToolsGallery_jButton2_actionAdapter(ToolsGallery adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.jButton2_actionPerformed(e);
    }
}


final class ToolsGallery_lSeries_listSelectionAdapter implements
        ListSelectionListener {
    private ToolsGallery adaptee;
    ToolsGallery_lSeries_listSelectionAdapter(ToolsGallery adaptee) {
        this.adaptee = adaptee;
    }

    public void valueChanged(ListSelectionEvent e) {
        adaptee.lSeries_valueChanged(e);
    }
}


final class ToolsGallery_lAxis_listSelectionAdapter implements ListSelectionListener {
    private ToolsGallery adaptee;
    ToolsGallery_lAxis_listSelectionAdapter(ToolsGallery adaptee) {
        this.adaptee = adaptee;
    }

    public void valueChanged(ListSelectionEvent e) {
        adaptee.lAxis_valueChanged(e);
    }
}


final class ToolsGallery_lOther_listSelectionAdapter implements ListSelectionListener {
    private ToolsGallery adaptee;
    ToolsGallery_lOther_listSelectionAdapter(ToolsGallery adaptee) {
        this.adaptee = adaptee;
    }

    public void valueChanged(ListSelectionEvent e) {
        adaptee.lOther_valueChanged(e);
    }
}


final class ToolsGallery_jTabbedPane1_changeAdapter implements ChangeListener {
    private ToolsGallery adaptee;
    ToolsGallery_jTabbedPane1_changeAdapter(ToolsGallery adaptee) {
        this.adaptee = adaptee;
    }

    public void stateChanged(ChangeEvent e) {
        adaptee.jTabbedPane1_stateChanged(e);
    }
}

final class Renderer extends javax.swing.DefaultListCellRenderer {
    public Component getListCellRendererComponent(JList list, Object value,
                                                  int index, boolean selected,
                                                  boolean focus) {
        JLabel jl = (JLabel)super.getListCellRendererComponent(list, value,
                index, selected, focus);
        jl.setText(((Tool)value).getDescription());

        ImageIcon i = new ImageIcon(((Tool)value).getBitmapEditor());

        if (i != null) {
            jl.setIcon(i);
        }

        return jl;
    }
}


