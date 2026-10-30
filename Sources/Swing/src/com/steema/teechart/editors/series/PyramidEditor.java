package com.steema.teechart.editors.series;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import java.awt.Rectangle;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.steema.teechart.styles.Series;
import com.steema.teechart.styles.Pyramid;
import com.steema.teechart.editors.BrushEditor;
import com.steema.teechart.editors.BaseSeriesEditor;
import com.steema.teechart.editors.ButtonColor;
import com.steema.teechart.editors.ChartPenEditor;
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
public class PyramidEditor extends BaseSeriesEditor {


    public PyramidEditor() {
        super();
        try {
            jbInit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void setSeries(Series series, JTabbedPane tabs) {
        pyramid = (Pyramid) series;

        spSize.setValue(new Integer(pyramid.getSizePercent()));
        cbColorEach.setSelected(pyramid.getColorEach());
        bColor.setEnabled(pyramid.getColorEach());
        bColor.setColor(pyramid.getColor());
    }

    private void jbInit() throws Exception {
        border12 = BorderFactory.createEtchedBorder(Color.white,
                new Color(148, 145, 140));
        this.setLayout(null);
        spSize.setToolTipText("");
        spSize.setBounds(new Rectangle(116, 75, 53, 23));
        spSize.addChangeListener(new PyramidEditor_spSize_changeAdapter(this));
        jLabel2.setDisplayedMnemonic(Language.getString("S").charAt(0));
        jLabel2.setHorizontalAlignment(SwingConstants.TRAILING);
        jLabel2.setLabelFor(spSize);
        jLabel2.setText(Language.getString("Size"));
        jLabel2.setBounds(new Rectangle(26, 80, 83, 14));
        jLabel3.setDisplayedMnemonic(Language.getString("O").charAt(0));
        jLabel3.setHorizontalAlignment(SwingConstants.TRAILING);
        jLabel3.setText(Language.getString("Transparency"));
        jLabel3.setBounds(new Rectangle( -9, 154, 93, 14));
        bColor.setBounds(new Rectangle(116, 14, 77, 23));
        bColor.setMnemonic(Language.getString("C").charAt(0));
        bColor.setText(Language.getString("Color"));
        bColor.addActionListener(new PyramidEditor_bColor_actionAdapter(this));
        cbColorEach.setMnemonic(Language.getString("E").charAt(0));
        cbColorEach.setText(Language.getString("color_each"));
        cbColorEach.setBounds(new Rectangle(112, 46, 92, 17));
        cbColorEach.addActionListener(new PyramidEditor_cbColorEach_actionAdapter(this));
        bBorder.setBounds(new Rectangle(17, 14, 85, 23));
        bBorder.setMnemonic(Language.getString("B").charAt(0));
        bBorder.setText(Language.getString("Border"));
        bBorder.addActionListener(new PyramidEditor_bBorder_actionAdapter(this));
        bPattern.setBounds(new Rectangle(18, 43, 84, 23));
        bPattern.setMnemonic(Language.getString("P").charAt(0));
        bPattern.setText(Language.getString("Pattern"));
        bPattern.addActionListener(new PyramidEditor_bPattern_actionAdapter(this));
        this.add(bBorder);
        this.add(bPattern);
        this.add(bColor);
        this.add(cbColorEach);
        this.add(spSize);
        this.add(jLabel2);
    }

    Border border1 = BorderFactory.createEtchedBorder(Color.white,
            new Color(148, 145, 140));
    Border border2 = new TitledBorder(border1, Language.getString("custom"));
    private Pyramid pyramid;
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
    JSpinner spSize = new JSpinner();
    JLabel jLabel3 = new JLabel();
    Border border12 = BorderFactory.createEtchedBorder(EtchedBorder.RAISED,
            Color.white, new Color(148, 145, 140));
    ButtonColor bColor = new ButtonColor();
    JCheckBox cbColorEach = new JCheckBox();
    JButton bBorder = new JButton();
    JButton bPattern = new JButton();
    Border border13 = BorderFactory.createEtchedBorder(Color.white,
            new Color(148, 145, 140));
    Border border14 = new TitledBorder(border13, Language.getString("Color"));

    public void cbColorEach_actionPerformed(ActionEvent e) {
        pyramid.setColorEach(cbColorEach.isSelected());
    }

    public void spSize_stateChanged(ChangeEvent e) {
        pyramid.setSizePercent(((Integer)spSize.getValue()).intValue());
    }

    public void bColor_actionPerformed(ActionEvent e) {
        pyramid.setColor(bColor.getColor());
    }

    public void bBorder_actionPerformed(ActionEvent e) {
        ChartPenEditor.showDialog(null, pyramid.getPen());
    }

    public void bPattern_actionPerformed(ActionEvent e) {
        BrushEditor.edit(pyramid.getBrush());
    }
}

class PyramidEditor_cbColorEach_actionAdapter implements ActionListener {
    private PyramidEditor adaptee;
    PyramidEditor_cbColorEach_actionAdapter(PyramidEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.cbColorEach_actionPerformed(e);
    }
}

class PyramidEditor_spSize_changeAdapter implements ChangeListener {
    private PyramidEditor adaptee;
    PyramidEditor_spSize_changeAdapter(PyramidEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void stateChanged(ChangeEvent e) {
        adaptee.spSize_stateChanged(e);
    }
}

final class PyramidEditor_bColor_actionAdapter implements ActionListener {
    private PyramidEditor adaptee;
    PyramidEditor_bColor_actionAdapter(PyramidEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.bColor_actionPerformed(e);
    }
}

final class PyramidEditor_bBorder_actionAdapter implements ActionListener {
    private PyramidEditor adaptee;
    PyramidEditor_bBorder_actionAdapter(PyramidEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.bBorder_actionPerformed(e);
    }
}

final class PyramidEditor_bPattern_actionAdapter implements ActionListener {
    private PyramidEditor adaptee;
    PyramidEditor_bPattern_actionAdapter(PyramidEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.bPattern_actionPerformed(e);
    }
}

