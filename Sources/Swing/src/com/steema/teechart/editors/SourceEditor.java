package com.steema.teechart.editors;

import java.awt.*;
import java.awt.Rectangle;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.border.*;

import java.awt.Dimension;
import com.steema.teechart.languages.Language;

/**
 * <p>Title: SourceEditor Class</p>
 *
 * <p>Description: Source Editor</p>
 *
 * <p>Copyright (c) 2004-2019 by Steema Software SL. All Rights Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 *
 */
public class SourceEditor extends JPanel {


    Border border1 = BorderFactory.createEtchedBorder(Color.white,
            new Color(148, 145, 140));
    Border border2 = new TitledBorder(border1, Language.getString("Size"));
    Border border3 = BorderFactory.createEtchedBorder(Color.white,
            new Color(148, 145, 140));
    Border border4 = new TitledBorder(border3, Language.getString("Shadow"));
    Border border5 = BorderFactory.createBevelBorder(BevelBorder.RAISED,
            Color.white, Color.white, new Color(115, 114, 105),
            new Color(165, 163, 151));
    Border border6 = BorderFactory.createBevelBorder(BevelBorder.LOWERED,
            Color.white, Color.white, new Color(115, 114, 105),
            new Color(165, 163, 151));
    JLabel jLabel1 = new JLabel();
    Border border7 = BorderFactory.createEtchedBorder(EtchedBorder.RAISED,
            Color.white, new Color(178, 178, 178));
    Border border8 = BorderFactory.createEtchedBorder(EtchedBorder.RAISED,
            Color.white, new Color(178, 178, 178));
    Component controlToEnable = null;
    JButton bApply = new JButton();
    JComboBox cbSources = new JComboBox();
    JPanel jPanel1 = new JPanel();
    Border border9 = BorderFactory.createEmptyBorder();
    BorderLayout borderLayout1 = new BorderLayout();
    BorderLayout borderLayout2 = new BorderLayout();


    protected com.steema.teechart.styles.Series series;

    public SourceEditor() {
        try {
            jbInit();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    protected void jbInit() throws Exception {
        jLabel1.setMaximumSize(new Dimension(45, 15));
        jLabel1.setMinimumSize(new Dimension(48, 15));
        jLabel1.setPreferredSize(new Dimension(48, 15));
        jLabel1.setDisplayedMnemonic(Language.getString("S").charAt(0));
        jLabel1.setHorizontalAlignment(SwingConstants.RIGHT);
        jLabel1.setHorizontalTextPosition(SwingConstants.LEADING);
        jLabel1.setLabelFor(cbSources);
        jLabel1.setText(Language.getString("Source"));
        jLabel1.setBounds(new Rectangle(23, 12, 57, 15));
        bApply.setBounds(new Rectangle(275, 6, 71, 23));
        bApply.setMnemonic(Language.getString("A").charAt(0));
        bApply.setText(Language.getString("Apply"));
        bApply.addActionListener(new SourceEditor_bApply_actionAdapter(this));
        cbSources.setBounds(new Rectangle(88, 9, 182, 19));
        cbSources.addActionListener(new SourceEditor_cbSources_actionAdapter(this));
        jPanel1.setBorder(border9);
        jPanel1.setMinimumSize(new Dimension(100, 100));
        jPanel1.setLayout(borderLayout1);
        this.setLayout(borderLayout2);
        this.add(jPanel1, java.awt.BorderLayout.NORTH);
        jPanel1.add(cbSources, java.awt.BorderLayout.CENTER);
        jPanel1.add(bApply, java.awt.BorderLayout.EAST);
        jPanel1.add(jLabel1, java.awt.BorderLayout.WEST);
    }

    protected
    /*virtual*/ void applyChanges() {}

    public void bApply_actionPerformed(ActionEvent e) {
        applyChanges();
        bApply.setEnabled(false);
    }

    public
    /*virtual*/ void cbSources_actionPerformed(ActionEvent e) {
        bApply.setEnabled(true);
    }

    protected
    /*virtual*/ boolean isValid(Object c) {
        return false;
    }

    protected void setCBSource() {
        if (series.getDataSource() != null) {
            for (int i = 0; i < cbSources.getItemCount(); i++) {
                if (cbSources.getItemAt(i) == series.getDataSource()) {
                    cbSources.setSelectedIndex(i);
                    break;
                }
            }
        }
    }

    protected void addComponentDataSource(Object c, JComboBox items,
                                          boolean addCurrent) {
        //object picks up DataSet, etc

        /*      if (isValid(c))
                if (series.getChart().isValidDataSource(series,c))
                {
                  Object tmp;

                  if (c instanceof styles.Series);
                    tmp=((styles.Series)c).toString();
                  else
                    tmp=c;

                 ((DefaultListModel)items.getModel()).addElement(tmp);
                }
         */
    }
}


final class SourceEditor_cbSources_actionAdapter implements ActionListener {
    private SourceEditor adaptee;
    SourceEditor_cbSources_actionAdapter(SourceEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.cbSources_actionPerformed(e);
    }
}


final class SourceEditor_bApply_actionAdapter implements ActionListener {
    private SourceEditor adaptee;
    SourceEditor_bApply_actionAdapter(SourceEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.bApply_actionPerformed(e);
    }
}

