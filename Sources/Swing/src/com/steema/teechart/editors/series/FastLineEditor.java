package com.steema.teechart.editors.series;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import javax.swing.border.EtchedBorder;

import java.awt.Rectangle;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.steema.teechart.editors.ChartPenEditor;
import com.steema.teechart.editors.BaseSeriesEditor;
import com.steema.teechart.styles.FastLine;
import com.steema.teechart.styles.Series;
import com.steema.teechart.languages.Language;
import com.steema.teechart.styles.TreatNullsStyle;


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
public class FastLineEditor extends BaseSeriesEditor {


    private FastLine fast;

    JPanel jPanel1 = new JPanel();
    ChartPenEditor penEditor = new ChartPenEditor();
    BorderLayout borderLayout1 = new BorderLayout();
    JPanel jPanel2 = new JPanel();
    Border border1 = BorderFactory.createEtchedBorder(EtchedBorder.RAISED,
            Color.white, new Color(148, 145, 140));
    Border border2 = new TitledBorder(border1, Language.getString("Line_mode"));
    JCheckBox cbStairs = new JCheckBox();
    JCheckBox cbInverted = new JCheckBox();
    JCheckBox cbEach = new JCheckBox();
    JCheckBox cbDrawAll = new JCheckBox();
    Border border3 = BorderFactory.createEtchedBorder(EtchedBorder.RAISED,
            Color.white, new Color(148, 145, 140));
    JPanel jPanel3 = new JPanel();
    Border border4 = new TitledBorder(border1, Language.getString("TreatNulls"));
    JComboBox cbTreatNulls = new JComboBox();

    public FastLineEditor() {
        try {
            jbInit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        penEditor.cbVisible.setVisible(false);
        jPanel1.add(penEditor.penPanel, BorderLayout.CENTER);
        
        cbTreatNulls.addItem(Language.getString("DontPaint"));
        cbTreatNulls.addItem(Language.getString("Skip"));
        cbTreatNulls.addItem(Language.getString("Ignore"));
    }

    /**
     * setSeries
     *
     * @param series Series
     * @param tabs JTabbedPane
     * @todo Implement this com.steema.teechart.editors.BaseSeriesEditor
     *   method
     */
    public void setSeries(Series series, JTabbedPane tabs) {
        fast=(FastLine)series;
        penEditor.refreshControls(fast.getLinePen());

        cbStairs.setSelected(fast.getStairs());
        cbInverted.setSelected(fast.getInvertedStairs());
        cbDrawAll.setSelected(fast.getDrawAllPoints());
        cbEach.setSelected(fast.getColorEach());
        cbTreatNulls.setSelectedIndex(fast.getTreatNulls().getValue());
    }

    private void jbInit() throws Exception {
        this.setLayout(null);
        jPanel1.setBorder(border3);
        jPanel1.setBounds(new Rectangle(6, 3, 269, 131));
        jPanel1.setLayout(borderLayout1);
        jPanel2.setBorder(border2);
        jPanel2.setBounds(new Rectangle(6, 137, 155, 50));
        jPanel2.setLayout(null);
        cbStairs.setMnemonic(Language.getString("S").charAt(0));
        cbStairs.setText(Language.getString("Stairs"));
        cbStairs.setBounds(new Rectangle(7, 14, 70, 23));
        cbStairs.addActionListener(new FastLineEditor_cbStairs_actionAdapter(this));
        cbInverted.setEnabled(false);
        cbInverted.setMnemonic(Language.getString("I").charAt(0));
        cbInverted.setText(Language.getString("Inverted"));
        cbInverted.setBounds(new Rectangle(72, 14, 70, 23));
        cbInverted.addActionListener(new
                                     FastLineEditor_cbInverted_actionAdapter(this));
        cbEach.setMnemonic(Language.getString("E").charAt(0));
        cbEach.setText(Language.getString("Color_each"));
        cbEach.setBounds(new Rectangle(170, 140, 81, 23));
        cbEach.addActionListener(new FastLineEditor_cbEach_actionAdapter(this));
        cbDrawAll.setMnemonic(Language.getString("A").charAt(0));
        cbDrawAll.setSelected(true);
        cbDrawAll.setText(Language.getString("Draw_all_points"));
        cbDrawAll.setBounds(new Rectangle(170, 162, 103, 23));
        cbDrawAll.addActionListener(new FastLineEditor_cbDrawAll_actionAdapter(this));
        cbTreatNulls.setBounds(new Rectangle(10, 22, 135, 22));
        cbTreatNulls.addActionListener(new 
                FastLineEditor_cbTreatNulls_actionAdapter(this));
        jPanel3.setBorder(border4);
        jPanel3.setBounds(new Rectangle(6, 190, 155, 60));
        jPanel3.setLayout(null);
        jPanel2.add(cbInverted);
        jPanel2.add(cbStairs);
        jPanel3.add(cbTreatNulls);
        this.add(cbDrawAll);
        this.add(cbEach);
        this.add(jPanel1);
        this.add(jPanel2);
        this.add(jPanel3);
    }

    public void cbStairs_actionPerformed(ActionEvent e) {
        fast.setStairs(cbStairs.isSelected());
        cbInverted.setEnabled(cbStairs.isSelected());
    }

    public void cbInverted_actionPerformed(ActionEvent e) {
        fast.setInvertedStairs(cbInverted.isSelected());
    }

    public void cbDrawAll_actionPerformed(ActionEvent e) {
        fast.setDrawAllPoints(cbDrawAll.isSelected());
    }

    public void cbEach_actionPerformed(ActionEvent e) {
        fast.setColorEach(cbEach.isSelected());
    }
    
    public void cbTreatNulls_actionPerformed(ActionEvent e) {
        if (fast!=null) {
            fast.setTreatNulls(TreatNullsStyle.fromInt(
                    cbTreatNulls.getSelectedIndex()));
        }
    }  
}


class FastLineEditor_cbEach_actionAdapter implements ActionListener {
    private FastLineEditor adaptee;
    FastLineEditor_cbEach_actionAdapter(FastLineEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.cbEach_actionPerformed(e);
    }
}


class FastLineEditor_cbDrawAll_actionAdapter implements ActionListener {
    private FastLineEditor adaptee;
    FastLineEditor_cbDrawAll_actionAdapter(FastLineEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.cbDrawAll_actionPerformed(e);
    }
}


class FastLineEditor_cbInverted_actionAdapter implements ActionListener {
    private FastLineEditor adaptee;
    FastLineEditor_cbInverted_actionAdapter(FastLineEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.cbInverted_actionPerformed(e);
    }
}


class FastLineEditor_cbStairs_actionAdapter implements ActionListener {
    private FastLineEditor adaptee;
    FastLineEditor_cbStairs_actionAdapter(FastLineEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.cbStairs_actionPerformed(e);
    }
}

class FastLineEditor_cbTreatNulls_actionAdapter implements ActionListener {
    private FastLineEditor adaptee;
    FastLineEditor_cbTreatNulls_actionAdapter(FastLineEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.cbTreatNulls_actionPerformed(e);
    }
}
