package com.steema.teechart.editors.series;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import javax.swing.border.EtchedBorder;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.steema.teechart.styles.Series;
import com.steema.teechart.styles.Arrow;
import com.steema.teechart.editors.ButtonPen;
import com.steema.teechart.editors.BrushEditor;
import com.steema.teechart.editors.ButtonColor;
import com.steema.teechart.editors.BaseSeriesEditor;
import com.steema.teechart.languages.Language;

/**
 * <p>Title: ArrowEditor class</p>
 *
 * <p>Description: Arrow Editor panel</p>
 *
 * <p>Copyright (c) 2004-2019 by Steema Software SL. All Rights Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 *
 */
public class ArrowEditor extends BaseSeriesEditor {


    public ArrowEditor() {
        super();
        try {
            jbInit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void setSeries(Series series, JTabbedPane tabs) {
        arrow = (Arrow) series;

        cbColorEach.setSelected(arrow.getColorEach());
        bColor.setColor(arrow.getColor());
        spWidth.setValue(new Integer(1));
        spHeight.setValue(new Integer(1));
        buttonPen1.setPen(arrow.getPointer().getPen());
    }

    private void jbInit() throws Exception {
        this.setLayout(null);
        jPanel3.setBorder(border6);
        jPanel3.setBounds(new Rectangle(17, 39, 129, 92));
        jPanel3.setLayout(null);
        cbColorEach.setMnemonic(Language.getString("E").charAt(0));
        cbColorEach.setText(Language.getString("Color_each"));
        cbColorEach.setBounds(new Rectangle(11, 18, 109, 23));
        cbColorEach.addActionListener(new ArrowEditor_cbColorEach_actionAdapter(this));
        bColor.setBounds(new Rectangle(14, 46, 78, 23));
        bColor.setMnemonic(Language.getString("C").charAt(0));
        bColor.setText(Language.getString("Color"));
        bColor.addActionListener(new ArrowEditor_bColor_actionAdapter(this));
        buttonPen1.setBounds(new Rectangle(16, 11, 91, 23));
        buttonPen1.setMnemonic(Language.getString("B").charAt(0));
        buttonPen1.setText(Language.getString("Border"));
        jButton1.setBounds(new Rectangle(176, 10, 84, 23));
        jButton1.setMnemonic(Language.getString("P").charAt(0));
        jButton1.setText(Language.getString("Pattern"));
        spWidth.setToolTipText("");
        spWidth.setBounds(new Rectangle(203, 56, 56, 23));
        jLabel3.setDisplayedMnemonic(Language.getString("O").charAt(0));
        jLabel3.setHorizontalAlignment(SwingConstants.TRAILING);
        jLabel3.setText(Language.getString("Height"));
        jLabel3.setBounds(new Rectangle(142, 86, 51, 14));
        jLabel4.setDisplayedMnemonic(Language.getString("O").charAt(0));
        jLabel4.setHorizontalAlignment(SwingConstants.TRAILING);
        jLabel4.setText(Language.getString("Width"));
        jLabel4.setBounds(new Rectangle(141, 60, 51, 14));
        spHeight.setToolTipText("");
        spHeight.setBounds(new Rectangle(203, 82, 56, 23));
        jPanel3.add(cbColorEach);
        jPanel3.add(bColor);
        this.add(spWidth);
        this.add(spHeight);
        this.add(jLabel4);
        this.add(jLabel3);
        this.add(jButton1);
        this.add(buttonPen1);
        this.add(jPanel3);
    }

    Border border1 = BorderFactory.createEtchedBorder(Color.white,
            new Color(148, 145, 140));
    Border border2 = new TitledBorder(border1, Language.getString("arrow"));
    private Arrow arrow;
    Border border3 = BorderFactory.createEtchedBorder(EtchedBorder.RAISED,
            Color.white, new Color(148, 145, 140));
    Border border4 = new TitledBorder(border3, Language.getString("Multiple_arrows"));
    JPanel jPanel3 = new JPanel();
    Border border5 = BorderFactory.createEtchedBorder(Color.white,
            new Color(148, 145, 140));
    Border border6 = new TitledBorder(border5, Language.getString("Color"));
    JCheckBox cbColorEach = new JCheckBox();
    ButtonColor bColor = new ButtonColor();
    Border border7 = BorderFactory.createEtchedBorder(EtchedBorder.RAISED,
            Color.white, new Color(148, 145, 140));
    ButtonPen buttonPen1 = new ButtonPen();
    JButton jButton1 = new JButton();
    JSpinner spWidth = new JSpinner();
    JLabel jLabel3 = new JLabel();
    JLabel jLabel4 = new JLabel();
    JSpinner spHeight = new JSpinner();

    public void jButton2_actionPerformed(ActionEvent e) {
        BrushEditor.edit(arrow.getPointer().getBrush());
    }

    public void bColor_actionPerformed(ActionEvent e) {
        arrow.setColor(bColor.getColor());
    }

    public void cbColorEach_actionPerformed(ActionEvent e) {
        arrow.setColorEach(cbColorEach.isSelected());
    }
}

class ArrowEditor_cbColorEach_actionAdapter implements ActionListener {
    private ArrowEditor adaptee;
    ArrowEditor_cbColorEach_actionAdapter(ArrowEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.cbColorEach_actionPerformed(e);
    }
}

final class ArrowEditor_bColor_actionAdapter implements ActionListener {
    private ArrowEditor adaptee;
    ArrowEditor_bColor_actionAdapter(ArrowEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.bColor_actionPerformed(e);
    }
}



