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
import com.steema.teechart.styles.Funnel;
import com.steema.teechart.editors.ButtonPen;
import com.steema.teechart.editors.BaseSeriesEditor;
import com.steema.teechart.editors.ButtonColor;
import com.steema.teechart.editors.BrushEditor;
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
public class FunnelEditor extends BaseSeriesEditor {


    public FunnelEditor() {
        super();
        try {
            jbInit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void setSeries(Series series, JTabbedPane tabs) {
        funnel = (Funnel) series;

        spDifLimit.setValue(new Double(funnel.getDifferenceLimit()));
        bLines.setPen(funnel.getLinesPen());
        bBorder.setPen(funnel.getPen());
        bAboveColor.setColor(funnel.getAboveColor());
        bWithinColor.setColor(funnel.getWithinColor());
        bBelowColor.setColor(funnel.getBelowColor());
    }

    private void jbInit() throws Exception {
        this.setLayout(null);
        bBorder.setBounds(new Rectangle(102, 68, 80, 23));
        bBorder.setMnemonic(Language.getString("B").charAt(0));
        bBorder.setText(Language.getString("Border"));
        spDifLimit.setToolTipText("");
        spDifLimit.setBounds(new Rectangle(196, 11, 46, 23));
        spDifLimit.addChangeListener(new FunnelEditor_spDifLimit_changeAdapter(this));
        bAboveColor.setBounds(new Rectangle(15, 12, 74, 23));
        bAboveColor.setMnemonic(Language.getString("A").charAt(0));
        bAboveColor.setText(Language.getString("Above"));
        bAboveColor.addActionListener(new FunnelEditor_bAboveColor_actionAdapter(this));
        bBrush.setBounds(new Rectangle(196, 40, 80, 23));
        bBrush.setMnemonic(Language.getString("P").charAt(0));
        bBrush.setText(Language.getString("Pattern"));
        bBrush.addActionListener(new FunnelEditor_bBrush_actionAdapter(this));
        bLines.setBounds(new Rectangle(102, 40, 80, 23));
        bLines.setMnemonic(Language.getString("O").charAt(0));
        bLines.setText(Language.getString("Outline"));
        jLabel2.setDisplayedMnemonic(Language.getString("D").charAt(0));
        jLabel2.setHorizontalAlignment(SwingConstants.TRAILING);
        jLabel2.setLabelFor(spDifLimit);
        jLabel2.setText(Language.getString("Dif_Limit"));
        jLabel2.setBounds(new Rectangle(108, 16, 81, 14));
        jLabel3.setDisplayedMnemonic(Language.getString("O").charAt(0));
        jLabel3.setHorizontalAlignment(SwingConstants.TRAILING);
        jLabel3.setText(Language.getString("Transparency"));
        jLabel3.setBounds(new Rectangle( -9, 154, 93, 14));
        bWithinColor.setBounds(new Rectangle(14, 40, 74, 23));
        bWithinColor.setMnemonic(Language.getString("W").charAt(0));
        bWithinColor.setText(Language.getString("Within"));
        bWithinColor.addActionListener(new FunnelEditor_bWithinColor_actionAdapter(this));
        bBelowColor.setBounds(new Rectangle(14, 68, 74, 23));
        bBelowColor.setMnemonic(Language.getString("E").charAt(0));
        bBelowColor.setText(Language.getString("Below"));
        bBelowColor.addActionListener(new FunnelEditor_bBelowColor_actionAdapter(this));
        this.add(bAboveColor);
        this.add(bWithinColor);
        this.add(bBelowColor);
        this.add(bBorder);
        this.add(bLines);
        this.add(bBrush);
        this.add(spDifLimit);
        this.add(jLabel2);
    }

    Border border1 = BorderFactory.createEtchedBorder(Color.white,
            new Color(148, 145, 140));
    Border border2 = new TitledBorder(border1, Language.getString("custom"));
    private Funnel funnel;
    Border border3 = BorderFactory.createEtchedBorder(EtchedBorder.RAISED,
            Color.white, new Color(148, 145, 140));
    Border border4 = new TitledBorder(border3, Language.getString("Multiple_customs"));
    Border border5 = BorderFactory.createEtchedBorder(Color.white,
            new Color(148, 145, 140));
    Border border6 = new TitledBorder(border5, Language.getString("Color"));
    Border border7 = BorderFactory.createEtchedBorder(EtchedBorder.RAISED,
            Color.white, new Color(148, 145, 140));
    ButtonPen bBorder = new ButtonPen();
    Border border8 = BorderFactory.createEtchedBorder(Color.white,
            new Color(148, 145, 140));
    Border border9 = new TitledBorder(border8, Language.getString("Levels"));
    JTextField eValue = new JTextField();
    ButtonColor bAboveColor = new ButtonColor();
    JButton bBrush = new JButton();
    ButtonPen bLines = new ButtonPen();
    Border border10 = BorderFactory.createEtchedBorder(Color.white,
            new Color(148, 145, 140));
    Border border11 = new TitledBorder(border10, Language.getString("Line_mode"));
    JLabel jLabel2 = new JLabel();
    JSpinner spDifLimit = new JSpinner();
    JLabel jLabel3 = new JLabel();
    ButtonColor bWithinColor = new ButtonColor();
    ButtonColor bBelowColor = new ButtonColor();

    public void spDifLimit_stateChanged(ChangeEvent e) {
        funnel.setDifferenceLimit(((Double)spDifLimit.getValue()).doubleValue());
    }

    public void bAboveColor_actionPerformed(ActionEvent e) {
        funnel.setAboveColor(bAboveColor.getColor());
    }

    public void bWithinColor_actionPerformed(ActionEvent e) {
        funnel.setWithinColor(bWithinColor.getColor());
    }

    public void bBelowColor_actionPerformed(ActionEvent e) {
        funnel.setBelowColor(bBelowColor.getColor());
    }

    public void bBrush_actionPerformed(ActionEvent e) {
        BrushEditor.edit(this, funnel.getBrush());
    }
}

class FunnelEditor_spDifLimit_changeAdapter implements ChangeListener {
    private FunnelEditor adaptee;
    FunnelEditor_spDifLimit_changeAdapter(FunnelEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void stateChanged(ChangeEvent e) {
        adaptee.spDifLimit_stateChanged(e);
    }
}

final class FunnelEditor_bAboveColor_actionAdapter implements ActionListener {
    private FunnelEditor adaptee;
    FunnelEditor_bAboveColor_actionAdapter(FunnelEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.bAboveColor_actionPerformed(e);
    }
}

final class FunnelEditor_bWithinColor_actionAdapter implements ActionListener {
    private FunnelEditor adaptee;
    FunnelEditor_bWithinColor_actionAdapter(FunnelEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.bWithinColor_actionPerformed(e);
    }
}

final class FunnelEditor_bBelowColor_actionAdapter implements ActionListener {
    private FunnelEditor adaptee;
    FunnelEditor_bBelowColor_actionAdapter(FunnelEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.bBelowColor_actionPerformed(e);
    }
}

final class FunnelEditor_bBrush_actionAdapter implements ActionListener {
    private FunnelEditor adaptee;
    FunnelEditor_bBrush_actionAdapter(FunnelEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.bBrush_actionPerformed(e);
    }
}

