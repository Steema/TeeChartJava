package com.steema.teechart.editors.series;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.border.BevelBorder;

import java.awt.Rectangle;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.steema.teechart.styles.Series;
import com.steema.teechart.styles.Tower;
import com.steema.teechart.styles.TowerStyle;
import com.steema.teechart.editors.BaseSeriesEditor;
import com.steema.teechart.editors.ButtonPen;
import com.steema.teechart.editors.BrushEditor;
import com.steema.teechart.languages.Language;


/**
 * <p>Title: TowerEditor class</p>
 *
 * <p>Description: Tower Series Editor</p>
 *
 * <p>Copyright (c) 2004-2019 by Steema Software SL. All Rights Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 *
 */
public class TowerEditor extends BaseSeriesEditor {


    public TowerEditor() {
        super();
        try {
            jbInit();

            DefaultComboBoxModel model = new DefaultComboBoxModel();
            model.addElement(Language.getString("Cube"));
            model.addElement(Language.getString("Rectangle"));
            model.addElement(Language.getString("Cover"));
            model.addElement(Language.getString("Cylinder"));
            model.addElement(Language.getString("Arrow"));
            model.addElement(Language.getString("Cone"));
            model.addElement(Language.getString("Pyramid"));
            cbStyle.setModel(model);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void setSeries(Series series, JTabbedPane tabs) {
        tower = (Tower) series;

        spDepth.setValue(new Integer(tower.getPercentDepth()));
        spWidth.setValue(new Integer(tower.getPercentWidth()));
        spTransp.setValue(new Integer(tower.getTransparency()));
        cbDark.setSelected(tower.getDark3D());
        bBorder.setPen(tower.getPen());
        cbOrigin.setSelected(tower.getUseOrigin());
        eOrigin.setText(String.valueOf(tower.getOrigin()));
        tower.setOrigin(Double.parseDouble(eOrigin.getText()));
        cbStyle.setSelectedIndex(tower.getTowerStyle().getValue());

        if (grid3DEditor==null) {
            grid3DEditor = new GridEditor();
            grid3DEditor.setSeries(series, tabs);
            tabs.add(Language.getString("Grid_3D"), grid3DEditor);
        }
    }

    private void jbInit() throws Exception {
        border12 = BorderFactory.createEtchedBorder(Color.white,
                new Color(148, 145, 140));
        this.setLayout(null);
        jLabel2.setDisplayedMnemonic(Language.getString("T").charAt(0));
        jLabel2.setHorizontalAlignment(SwingConstants.TRAILING);
        jLabel2.setLabelFor(spTransp);
        jLabel2.setText(Language.getString("Transparency"));
        jLabel2.setBounds(new Rectangle(25, 169, 81, 14));
        jLabel3.setDisplayedMnemonic(Language.getString("O").charAt(0));
        jLabel3.setHorizontalAlignment(SwingConstants.TRAILING);
        jLabel3.setText(Language.getString("Transparency"));
        jLabel3.setBounds(new Rectangle( -9, 154, 93, 14));
        bPattern.setBounds(new Rectangle(104, 14, 75, 23));
        bPattern.setMnemonic(Language.getString("P").charAt(0));
        bPattern.setText(Language.getString("Pattern"));
        bPattern.addActionListener(new TowerEditor_bPattern_actionAdapter(this));
        cbDark.setMnemonic(Language.getString("D").charAt(0));
        cbDark.setText(Language.getString("Dark_3D"));
        cbDark.setBounds(new Rectangle(192, 17, 92, 17));
        cbDark.addActionListener(new TowerEditor_cbDark_actionAdapter(this));
        cbOrigin.setMnemonic(Language.getString("U").charAt(0));
        cbOrigin.setText(Language.getString("Use_Origin"));
        cbOrigin.setBounds(new Rectangle(12, 20, 92, 17));
        cbOrigin.addActionListener(new TowerEditor_cbOrigin_actionAdapter(this));
        bBorder.setBounds(new Rectangle(17, 14, 76, 23));
        bBorder.setMnemonic(Language.getString("B").charAt(0));
        bBorder.setText(Language.getString("Border"));
        eOrigin.setBounds(new Rectangle(14, 42, 87, 20));
        eOrigin.addActionListener(new TowerEditor_eOrigin_actionAdapter(this));
        jPanel1.setBorder(border28);
        jPanel1.setBounds(new Rectangle(193, 40, 118, 75));
        jPanel1.setLayout(null);
        jLabel1.setDisplayedMnemonic(Language.getString("S").charAt(0));
        jLabel1.setHorizontalAlignment(SwingConstants.TRAILING);
        jLabel1.setLabelFor(cbStyle);
        jLabel1.setText(Language.getString("Style"));
        jLabel1.setBounds(new Rectangle(16, 52, 49, 14));
        spTransp.setBounds(new Rectangle(115, 165, 53, 23));
        spTransp.addChangeListener(new TowerEditor_spTransp_changeAdapter(this));
        cbStyle.setBounds(new Rectangle(72, 48, 108, 23));
        cbStyle.addActionListener(new TowerEditor_cbStyle_actionAdapter(this));
        jPanel2.setBorder(border32);
        jPanel2.setBounds(new Rectangle(20, 73, 162, 85));
        jPanel2.setLayout(null);
        jLabel4.setDisplayedMnemonic(Language.getString("W").charAt(0));
        jLabel4.setHorizontalAlignment(SwingConstants.TRAILING);
        jLabel4.setLabelFor(spWidth);
        jLabel4.setText(Language.getString("Width"));
        jLabel4.setBounds(new Rectangle(9, 54, 52, 15));
        spWidth.setBounds(new Rectangle(75, 49, 74, 24));
        spWidth.addChangeListener(new TowerEditor_spWidth_changeAdapter(this));
        jLabel5.setDisplayedMnemonic(Language.getString("D").charAt(0));
        jLabel5.setHorizontalAlignment(SwingConstants.TRAILING);
        jLabel5.setLabelFor(spDepth);
        jLabel5.setText(Language.getString("Depth"));
        jLabel5.setBounds(new Rectangle(9, 26, 52, 15));
        spDepth.setBounds(new Rectangle(75, 21, 74, 24));
        spDepth.addChangeListener(new TowerEditor_spDepth_changeAdapter(this));
        this.add(bBorder);
        jPanel1.add(eOrigin);
        jPanel1.add(cbOrigin);
        this.add(jPanel1);
        this.add(jLabel1);
        this.add(cbStyle);
        this.add(bPattern);
        jPanel2.add(jLabel5);
        jPanel2.add(spDepth);
        jPanel2.add(spWidth);
        jPanel2.add(jLabel4);
        this.add(cbDark);
        this.add(jLabel2);
        this.add(spTransp);
        this.add(jPanel2);
    }

    private GridEditor grid3DEditor;
    Border border1 = BorderFactory.createEtchedBorder(Color.white,
            new Color(148, 145, 140));
    Border border2 = new TitledBorder(border1, Language.getString("custom"));
    private Tower tower;
    Border border3 = BorderFactory.createEtchedBorder(EtchedBorder.RAISED,
            Color.white, new Color(148, 145, 140));
    Border border4 = new TitledBorder(border3, Language.getString("Multiple_customs"));
    Border border5 = BorderFactory.createEtchedBorder(Color.white,
            new Color(148, 145, 140));
    Border border6 = new TitledBorder(border5, Language.getString("Color"));
    Border border7 = BorderFactory.createEtchedBorder(EtchedBorder.RAISED,
            Color.white, new Color(148, 145, 140));
    Border border8 = BorderFactory.createEtchedBorder(Color.white,
            new Color(148, 145, 140));
    Border border9 = new TitledBorder(border8, Language.getString("Levels"));
    JTextField eValue = new JTextField();
    Border border10 = BorderFactory.createEtchedBorder(Color.white,
            new Color(148, 145, 140));
    Border border11 = new TitledBorder(border10, Language.getString("Line_mode"));
    JLabel jLabel2 = new JLabel();
    JLabel jLabel3 = new JLabel();
    Border border12 = BorderFactory.createEtchedBorder(EtchedBorder.RAISED,
            Color.white, new Color(148, 145, 140));
    JButton bPattern = new JButton();
    Border border13 = BorderFactory.createEtchedBorder(Color.white,
            new Color(148, 145, 140));
    Border border14 = new TitledBorder(border13, Language.getString("Color"));
    JCheckBox cbDark = new JCheckBox();
    JCheckBox cbOrigin = new JCheckBox();
    ButtonPen bBorder = new ButtonPen();
    JTextField eOrigin = new JTextField();
    JPanel jPanel1 = new JPanel();
    Border border15 = BorderFactory.createEmptyBorder();
    Border border16 = new TitledBorder(border15, Language.getString("Origin"));
    Border border17 = BorderFactory.createBevelBorder(BevelBorder.RAISED,
            Color.white, Color.white, new Color(115, 114, 105),
            new Color(165, 163, 151));
    Border border18 = new TitledBorder(border17, Language.getString("Origin"));
    JLabel jLabel1 = new JLabel();
    Border border19 = BorderFactory.createEtchedBorder(EtchedBorder.RAISED,
            Color.white, new Color(165, 163, 151));
    Border border20 = new TitledBorder(border19, Language.getString("Origin"));
    JSpinner spTransp = new JSpinner();
    JComboBox cbStyle = new JComboBox();
    JPanel jPanel2 = new JPanel();
    Border border21 = BorderFactory.createEmptyBorder();
    Border border22 = new TitledBorder(border21, Language.getString("Percent"));
    Border border23 = BorderFactory.createBevelBorder(BevelBorder.RAISED,
            Color.white, Color.white, new Color(115, 114, 105),
            new Color(165, 163, 151));
    Border border24 = new TitledBorder(border23, Language.getString("Percent"));
    TitledBorder titledBorder1 = new TitledBorder("");
    Border border25 = BorderFactory.createEtchedBorder(Color.white,
            new Color(165, 163, 151));
    Border border26 = new TitledBorder(border25, Language.getString("Percent"));
    Border border27 = BorderFactory.createEtchedBorder(EtchedBorder.RAISED,
            Color.white, new Color(165, 163, 151));
    Border border28 = new TitledBorder(border27, Language.getString("Origin"));
    Border border29 = BorderFactory.createEtchedBorder(EtchedBorder.RAISED,
            Color.white, new Color(165, 163, 151));
    Border border30 = new TitledBorder(border29, Language.getString("Percent"));
    Border border31 = BorderFactory.createEtchedBorder(EtchedBorder.RAISED,
            Color.white, new Color(165, 163, 151));
    Border border32 = new TitledBorder(border31, Language.getString("Percent"));
    JLabel jLabel4 = new JLabel();
    JSpinner spWidth = new JSpinner();
    JLabel jLabel5 = new JLabel();
    JSpinner spDepth = new JSpinner();


    public void cbDark_actionPerformed(ActionEvent e) {
          tower.setDark3D(cbDark.isSelected());
    }

    public void cbOrigin_actionPerformed(ActionEvent e) {
        tower.setUseOrigin(cbOrigin.isSelected());
    }

    public void eOrigin_actionPerformed(ActionEvent e) {
        tower.setOrigin(Double.parseDouble(eOrigin.getText()));
    }

    public void cbStyle_actionPerformed(ActionEvent e) {
        switch (cbStyle.getSelectedIndex())
        {
                case 0: tower.setTowerStyle(TowerStyle.CUBE); break;
                case 1: tower.setTowerStyle(TowerStyle.RECTANGLE); break;
                case 2: tower.setTowerStyle(TowerStyle.COVER); break;
                case 3: tower.setTowerStyle(TowerStyle.CYLINDER); break;
                case 4: tower.setTowerStyle(TowerStyle.ARROW); break;
                case 5: tower.setTowerStyle(TowerStyle.CONE); break;
                case 6: tower.setTowerStyle(TowerStyle.PYRAMID); break;
        }
    }

    public void spDepth_stateChanged(ChangeEvent e) {
        tower.setPercentDepth(((Integer)spDepth.getValue()).intValue());
    }

    public void spWidth_stateChanged(ChangeEvent e) {
        tower.setPercentWidth(((Integer)spWidth.getValue()).intValue());
    }

    public void spTransp_stateChanged(ChangeEvent e) {
        tower.setTransparency(((Integer)spTransp.getValue()).intValue());
    }

    public void bPattern_actionPerformed(ActionEvent e) {
        BrushEditor.edit(tower.getBrush());
    }
}

class TowerEditor_cbDark_actionAdapter implements ActionListener {
    private TowerEditor adaptee;
    TowerEditor_cbDark_actionAdapter(TowerEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.cbDark_actionPerformed(e);
    }
}

class TowerEditor_cbOrigin_actionAdapter implements ActionListener {
    private TowerEditor adaptee;
    TowerEditor_cbOrigin_actionAdapter(TowerEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.cbOrigin_actionPerformed(e);
    }
}

class TowerEditor_eOrigin_actionAdapter implements ActionListener {
    private TowerEditor adaptee;
    TowerEditor_eOrigin_actionAdapter(TowerEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.eOrigin_actionPerformed(e);
    }
}

final class TowerEditor_cbStyle_actionAdapter implements ActionListener {
    private TowerEditor adaptee;
    TowerEditor_cbStyle_actionAdapter(TowerEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.cbStyle_actionPerformed(e);
    }
}

class TowerEditor_spDepth_changeAdapter implements ChangeListener {
    private TowerEditor adaptee;
    TowerEditor_spDepth_changeAdapter(TowerEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void stateChanged(ChangeEvent e) {
        adaptee.spDepth_stateChanged(e);
    }
}

class TowerEditor_spWidth_changeAdapter implements ChangeListener {
    private TowerEditor adaptee;
    TowerEditor_spWidth_changeAdapter(TowerEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void stateChanged(ChangeEvent e) {
        adaptee.spWidth_stateChanged(e);
    }
}

class TowerEditor_spTransp_changeAdapter implements ChangeListener {
    private TowerEditor adaptee;
    TowerEditor_spTransp_changeAdapter(TowerEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void stateChanged(ChangeEvent e) {
        adaptee.spTransp_stateChanged(e);
    }
}

final class TowerEditor_bPattern_actionAdapter implements ActionListener {
    private TowerEditor adaptee;
    TowerEditor_bPattern_actionAdapter(TowerEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.bPattern_actionPerformed(e);
    }
}


