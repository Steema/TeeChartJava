package com.steema.teechart.editors.tools;

import java.awt.Rectangle;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.event.*;

import com.steema.teechart.editors.ButtonPen;
import com.steema.teechart.editors.BrushEditor;
import com.steema.teechart.tools.Tool;
import com.steema.teechart.languages.Language;
import com.steema.teechart.tools.SeriesBand;

/**
 * <p>Title: SeriesBandEditor class</p>
 *
 * <p>Description: SeriesBand Editor panel</p>
 *
 * <p>Copyright (c) 2004-2019 by Steema Software SL. All Rights Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 *
 */
public class SeriesBandEditor extends ToolSeriesEditor {

    public SeriesBandEditor() {
        try {
            jbInit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void setTool(Tool tool) {
        setting = true;
        seriesBand = (SeriesBand) tool;

        cbSeries2.removeAllItems();

        cbSeries2.addItem(Language.getString("None"));
        for (int i = 0; i < seriesBand.getChart().getSeriesCount(); ++i) {
            if (!seriesBand.getChart().getSeries(i).InternalUse) {
                cbSeries2.addItem(seriesBand.getChart().getSeries(i).toString());
            }
        }

        super.setTool(seriesBand);

        if (seriesBand.getSeries2() != null) {
            cbSeries2.setSelectedItem(seriesBand.getSeries2().toString());
        }
        else {
            cbSeries2.setSelectedIndex(0);
        }

        cbDrawBehind.setSelected(seriesBand.getDrawBehindSeries());
        spTransp.setValue(new Integer(seriesBand.getTransparency()));
        bPen.setPen(seriesBand.getPen());

        setting = false;
    }

    private void jbInit() throws Exception {
        this.setLayout(null);
        cbSeries2.setBounds(new Rectangle(75, 44, 139, 23));
        cbSeries2.addActionListener(
                new SeriesBandEditor_cbSeries2_actionAdapter(this));
        jLabel3.setDisplayedMnemonic(Language.getString("S").charAt(0));
        jLabel3.setHorizontalAlignment(SwingConstants.RIGHT);
        jLabel3.setText(Language.getString("Series2"));
        jLabel3.setBounds(new Rectangle(20, 49, 44, 15));
        bPen.setBounds(new Rectangle(33, 98, 78, 23));
        bPen.setMnemonic(Language.getString("P").charAt(0));
        bPen.setText(Language.getString("Pen"));
        bBrush.addActionListener(
                new SeriesBandEditor_bBrush_actionAdapter(this));
        bBrush.setBounds(new Rectangle(116, 98, 78, 23));
        bBrush.setMnemonic(Language.getString("B").charAt(0));
        bBrush.setText(Language.getString("Brush"));
        cbDrawBehind.setMnemonic(Language.getString("D").charAt(0));
        cbDrawBehind.setText(Language.getString("Draw_Behind_Series"));
        cbDrawBehind.setBounds(new Rectangle(33, 129, 123, 23));
        cbDrawBehind.addActionListener(new SeriesBandEditor_cbDrawBehind_actionAdapter(this));
        jLabel2.setDisplayedMnemonic(Language.getString("T").charAt(0));
        jLabel2.setHorizontalAlignment(SwingConstants.RIGHT);
        jLabel2.setLabelFor(spTransp);
        jLabel2.setText(Language.getString("Transparency"));
        jLabel2.setBounds(new Rectangle(33, 198, 84, 15));
        spTransp.setBounds(new Rectangle(154, 194, 60, 23));
        spTransp.addChangeListener(
                new SeriesBandEditor_spTransp_changeAdapter(this));
        this.add(cbSeries2);
        this.add(bBrush);
        this.add(bPen);
        this.add(cbDrawBehind);
        this.add(spTransp);
        this.add(jLabel2);
        this.add(jLabel3);
    }

    SeriesBand seriesBand;
    JComboBox cbSeries2 = new JComboBox();
    boolean drawBehind;
    JButton bBrush = new JButton();
    ButtonPen bPen = new ButtonPen();
    JCheckBox cbDrawBehind = new JCheckBox();
    JLabel jLabel2 = new JLabel();
    JLabel jLabel3 = new JLabel();
    JSpinner spTransp = new JSpinner();

    public void cbSeries2_actionPerformed(ActionEvent e) {
        if (seriesBand != null && !setting) {
            if (cbSeries2.getSelectedIndex() == -1 ||
                    cbSeries2.getSelectedIndex() == 0) {
                seriesBand.setSeries2(null);
            } else {
                seriesBand.setSeries2(seriesBand.getChart().getSeries(
                        cbSeries2.getSelectedIndex() - 1));
            }
        }
    }

    public void bBrush_actionPerformed(ActionEvent e) {
        BrushEditor.edit(seriesBand.getBrush());
    }

    public void cbDrawBehind_actionPerformed(ActionEvent e) {
        if (seriesBand != null && !setting) {
            seriesBand.setDrawBehindSeries(cbDrawBehind.isSelected());
        }
    }

    public void spTransp_stateChanged(ChangeEvent e) {
        if (seriesBand != null && !setting) {
            seriesBand.setTransparency(
                    ((Integer) spTransp.getValue()).intValue());
        }
    }
}

class SeriesBandEditor_cbSeries2_actionAdapter implements ActionListener {

    private SeriesBandEditor adaptee;

    SeriesBandEditor_cbSeries2_actionAdapter(SeriesBandEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.cbSeries2_actionPerformed(e);
    }
}

class SeriesBandEditor_spTransp_changeAdapter implements ChangeListener {

    private SeriesBandEditor adaptee;

    SeriesBandEditor_spTransp_changeAdapter(SeriesBandEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void stateChanged(ChangeEvent e) {
        adaptee.spTransp_stateChanged(e);
    }
}

class SeriesBandEditor_cbDrawBehind_actionAdapter implements ActionListener {

    private SeriesBandEditor adaptee;

    SeriesBandEditor_cbDrawBehind_actionAdapter(SeriesBandEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.cbDrawBehind_actionPerformed(e);
    }
}

class SeriesBandEditor_bBrush_actionAdapter implements ActionListener {

    private SeriesBandEditor adaptee;

    SeriesBandEditor_bBrush_actionAdapter(SeriesBandEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.bBrush_actionPerformed(e);
    }
}
