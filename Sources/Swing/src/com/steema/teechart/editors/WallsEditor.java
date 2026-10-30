package com.steema.teechart.editors;

import javax.swing.JPanel;
import java.awt.event.ActionEvent;
import javax.swing.JCheckBox;
import java.awt.Rectangle;
import com.steema.teechart.Chart;
import java.awt.event.ActionListener;
import com.steema.teechart.Walls;
import javax.swing.JTabbedPane;
import javax.swing.JLabel;
import javax.swing.JSpinner;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.BorderLayout;
import java.awt.Dimension;
import com.steema.teechart.languages.Language;

/**
 *
 * <p>Title: WallsEditor class</p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright (c) 2005-2019 by Steema Software SL. All Rights
 * Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 *
 */
public class WallsEditor extends JPanel {

    private Chart chart;
    private Walls walls;

    JPanel jPanel1 = new JPanel();
    JCheckBox cbViewWalls = new JCheckBox();
    JTabbedPane jTabbedPane1 = new JTabbedPane();
    JPanel jPanel2 = new JPanel();
    JPanel jPanel3 = new JPanel();
    JPanel jPanel4 = new JPanel();
    JPanel jPanel5 = new JPanel();
    JLabel jLabel1 = new JLabel();
    JSpinner jSpinner1 = new JSpinner();
    BorderLayout borderLayout1 = new BorderLayout();
    BorderLayout borderLayout2 = new BorderLayout();
    BorderLayout borderLayout3 = new BorderLayout();
    BorderLayout borderLayout4 = new BorderLayout();
    BorderLayout borderLayout5 = new BorderLayout();

    public WallsEditor() {
        try {
            jbInit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public WallsEditor(Chart chart) {
      this();
      this.chart=chart;
      walls=chart.getWalls();

      cbViewWalls.setSelected(walls.getVisible());

      jPanel2.add(new WallEditor(walls.getLeft()), java.awt.BorderLayout.CENTER);
      jPanel3.add(new WallEditor(walls.getRight()), java.awt.BorderLayout.CENTER);
      jPanel4.add(new WallEditor(walls.getBottom()), java.awt.BorderLayout.CENTER);
      jPanel5.add(new WallEditor(walls.getBack()), java.awt.BorderLayout.CENTER);

      jSpinner1.setValue(new Integer(walls.getLeft().getSize()));
    }

    private void jbInit() throws Exception {
        this.setLayout(borderLayout1);
        jPanel1.setLayout(null);
        cbViewWalls.setMnemonic(Language.getString("V").charAt(0));
        cbViewWalls.setText(Language.getString("Visible_Walls"));
        cbViewWalls.setBounds(new Rectangle(8, 5, 113, 23));
        cbViewWalls.addActionListener(new WallsEditor_cbViewWalls_actionAdapter(this));
        jLabel1.setDisplayedMnemonic(Language.getString("S").charAt(0));
        jLabel1.setHorizontalAlignment(SwingConstants.RIGHT);
        jLabel1.setLabelFor(jSpinner1);
        jLabel1.setText(Language.getString("Size"));
        jLabel1.setBounds(new Rectangle(140, 8, 43, 16));
        jSpinner1.addChangeListener(new WallsEditor_jSpinner1_changeAdapter(this));
        jSpinner1.setBounds(new Rectangle(189, 7, 50, 20));
        jPanel1.setMinimumSize(new Dimension(1, 10));
        jPanel1.setPreferredSize(new Dimension(1, 36));
        jPanel2.setLayout(borderLayout2);
        jPanel3.setLayout(borderLayout3);
        jPanel4.setLayout(borderLayout4);
        jPanel5.setLayout(borderLayout5);
        jTabbedPane1.addChangeListener(new
                                       WallsEditor_jTabbedPane1_changeAdapter(this));
        jPanel1.add(cbViewWalls);
        jPanel1.add(jLabel1);
        jPanel1.add(jSpinner1);
        jTabbedPane1.add(jPanel2, Language.getString("Left"));
        jTabbedPane1.add(jPanel3, Language.getString("Right"));
        jTabbedPane1.add(jPanel4, Language.getString("Bottom"));
        jTabbedPane1.add(jPanel5, Language.getString("Back"));
        this.add(jPanel1, java.awt.BorderLayout.NORTH);
        this.add(jTabbedPane1, java.awt.BorderLayout.CENTER);
    }

    public void cbViewWalls_actionPerformed(ActionEvent e) {
        walls.setVisible(cbViewWalls.isSelected());
    }

    public void jSpinner1_stateChanged(ChangeEvent e) {
        walls.setSize(((Integer)jSpinner1.getValue()).intValue());
    }

    public void jTabbedPane1_stateChanged(ChangeEvent e) {
        if(walls != null) {
            switch (jTabbedPane1.getSelectedIndex()) {
            case 0:
                jSpinner1.setValue(new Integer(walls.getLeft().getSize()));
                break;
            case 1:
                jSpinner1.setValue(new Integer(walls.getRight().getSize()));
                break;
            case 2:
                jSpinner1.setValue(new Integer(walls.getBottom().getSize()));
                break;
            case 3:
                jSpinner1.setValue(new Integer(walls.getBack().getSize()));
                break;
            }
        }
    }

}


class WallsEditor_cbViewWalls_actionAdapter implements ActionListener {
    private WallsEditor adaptee;
    WallsEditor_cbViewWalls_actionAdapter(WallsEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.cbViewWalls_actionPerformed(e);
    }
}


class WallsEditor_jTabbedPane1_changeAdapter implements ChangeListener {
    private WallsEditor adaptee;
    WallsEditor_jTabbedPane1_changeAdapter(WallsEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void stateChanged(ChangeEvent e) {
        adaptee.jTabbedPane1_stateChanged(e);
    }
}


class WallsEditor_jSpinner1_changeAdapter implements ChangeListener {
    private WallsEditor adaptee;
    WallsEditor_jSpinner1_changeAdapter(WallsEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void stateChanged(ChangeEvent e) {
        adaptee.jSpinner1_stateChanged(e);
    }
}

