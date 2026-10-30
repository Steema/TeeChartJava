package com.steema.teechart.editors.series;

// some lines to be uncommented..

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.border.Border;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.border.BevelBorder;

import java.awt.Rectangle;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.border.EtchedBorder;

import com.steema.teechart.styles.Series;
import com.steema.teechart.styles.ErrorWidthUnit;
import com.steema.teechart.editors.ButtonPen;
import com.steema.teechart.editors.BaseSeriesEditor;
import com.steema.teechart.languages.Language;
import com.steema.teechart.styles.ErrorPoint;

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
public class ErrorPointEditor extends BaseSeriesEditor {


    public ErrorPointEditor() {
        super();
        try {
            jbInit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void setSeries(Series series, JTabbedPane tabs) {
        customError = (ErrorPoint) series;

        if (customError.getErrors().getErrorSizeUnits()==ErrorWidthUnit.PERCENT)
            rbPercent.setSelected(true);
        else
            rbPixels.setSelected(true);

        spBarWidth.setValue(new Integer(customError.getErrors().getSize()));
        cbColorEach.setSelected(customError.getColorEach());

        ButtonGroup group = new ButtonGroup();
        group.add(rbLeft);
        group.add(rbRight);
        group.add(rbTop);
        group.add(rbBottom);

        ButtonGroup group2 = new ButtonGroup();
        group2.add(rbPercent);
        group2.add(rbPixels);
        
        rbLeft.setPen(customError.getErrors().getFormat().getLeft());
        rbRight.setPen(customError.getErrors().getFormat().getRight());
        rbTop.setPen(customError.getErrors().getFormat().getTop());
        rbBottom.setPen(customError.getErrors().getFormat().getBottom());
    }

    private void jbInit() throws Exception {
        this.setLayout(null);
        jPanel2.setBorder(border20);
        jPanel2.setBounds(new Rectangle(11, 104, 233, 89));
        jPanel2.setLayout(null);
        rbLeft.setMnemonic(Language.getString("L").charAt(0));
        rbLeft.setText(Language.getString("Left..."));
        rbLeft.setBounds(new Rectangle(11, 16, 93, 23));
        rbRight.setMnemonic(Language.getString("R").charAt(0));
        rbRight.setText(Language.getString("Right..."));
        rbRight.setBounds(new Rectangle(11, 38, 93, 23));
        rbTop.setMnemonic(Language.getString("T").charAt(0));
        rbTop.setText(Language.getString("Top..."));
        rbTop.setBounds(new Rectangle(113, 16, 93, 23));
        rbBottom.setText(Language.getString("Bottom..."));
        rbBottom.setBounds(new Rectangle(113, 38, 93, 23));
        rbBottom.setMnemonic(Language.getString("B").charAt(0));
        spBarWidth.setBounds(new Rectangle(81, 47, 53, 23));
        spBarWidth.addChangeListener(new ErrorPointEditor_spBarWidth_changeAdapter(this));
        jLabel3.setDisplayedMnemonic(Language.getString("O").charAt(0));
        jLabel3.setHorizontalAlignment(SwingConstants.TRAILING);
        jLabel3.setLabelFor(spBarWidth);
        jLabel3.setText(Language.getString("Error_Width"));
        jLabel3.setBounds(new Rectangle(5, 51, 70, 14));
        cbColorEach.setMnemonic(Language.getString("C").charAt(0));
        cbColorEach.setText(Language.getString("color_each"));
        cbColorEach.setBounds(new Rectangle(16, 77, 92, 23));
        cbColorEach.addActionListener(new ErrorPointEditor_cbColorEach_actionAdapter(this));
        jPanel1.setBorder(border22);
        jPanel1.setBounds(new Rectangle(153, 13, 92, 76));
        jPanel1.setLayout(null);
        rbPercent.setMnemonic(Language.getString("E").charAt(0));
        rbPercent.setText(Language.getString("Percent"));
        rbPercent.setBounds(new Rectangle(10, 19, 63, 23));
        rbPercent.addActionListener(new ErrorPointEditor_rbPercent_actionAdapter(this));
        rbPixels.setMnemonic(Language.getString("I").charAt(0));
        rbPixels.setText(Language.getString("Pixels"));
        rbPixels.setBounds(new Rectangle(10, 46, 63, 23));
        rbPixels.addActionListener(new ErrorPointEditor_rbPixels_actionAdapter(this));
        jPanel2.add(rbRight);
        jPanel2.add(rbLeft);
        jPanel2.add(rbTop);
        jPanel2.add(rbBottom);
        this.add(jLabel3);
        this.add(spBarWidth);
        this.add(cbColorEach);
        this.add(jPanel1);
        jPanel1.add(rbPercent);
        jPanel1.add(rbPixels);
        this.add(jPanel2);
    }

    Border border1 = BorderFactory.createEtchedBorder(Color.white,
            new Color(148, 145, 140));
    Border border2 = new TitledBorder(border1, Language.getString("Candle"));
    private ErrorPoint customError;
    JPanel jPanel2 = new JPanel();
    Border border3 = BorderFactory.createEtchedBorder(EtchedBorder.RAISED,
            Color.white, new Color(148, 145, 140));
    Border border4 = new TitledBorder(border3, Language.getString("Multiple_Candles"));
    ButtonPen rbLeft = new ButtonPen();
    ButtonPen rbRight = new ButtonPen();
    ButtonPen rbBottom = new ButtonPen();
    ButtonPen rbTop = new ButtonPen();
    Border border5 = BorderFactory.createEtchedBorder(Color.white,
            new Color(148, 145, 140));
    Border border6 = new TitledBorder(border5, Language.getString("Color"));
    Border border7 = BorderFactory.createEtchedBorder(EtchedBorder.RAISED,
            Color.white, new Color(148, 145, 140));
    Border border8 = BorderFactory.createEtchedBorder(EtchedBorder.RAISED,
            Color.white, new Color(148, 145, 140));
    Border border9 = new TitledBorder(border8, Language.getString("Error_Borders"));
    JSpinner spBarWidth = new JSpinner();
    JLabel jLabel3 = new JLabel();
    JCheckBox cbColorEach = new JCheckBox();
    JPanel jPanel1 = new JPanel();
    Border border10 = BorderFactory.createEtchedBorder(EtchedBorder.RAISED,
            Color.white, new Color(165, 163, 151));
    Border border11 = BorderFactory.createEtchedBorder(EtchedBorder.RAISED,
            Color.white, new Color(165, 163, 151));
    Border border12 = new TitledBorder(border11, Language.getString("Width_Units"));
    Border border13 = BorderFactory.createBevelBorder(BevelBorder.LOWERED,
            Color.white, Color.white, new Color(115, 114, 105),
            new Color(165, 163, 151));
    Border border14 = new TitledBorder(border13, Language.getString("Width_Units"));
    Border border15 = BorderFactory.createEtchedBorder(Color.white,
            new Color(165, 163, 151));
    Border border16 = new TitledBorder(border15, Language.getString("Width_Units"));
    Border border17 = BorderFactory.createEtchedBorder(EtchedBorder.RAISED,
            Color.white, new Color(165, 163, 151));
    Border border18 = new TitledBorder(border17, Language.getString("Width_Units"));
    Border border19 = BorderFactory.createEtchedBorder(EtchedBorder.RAISED,
            Color.white, new Color(148, 145, 140));
    Border border20 = new TitledBorder(border19, Language.getString("Error Borders"));
    Border border21 = BorderFactory.createEtchedBorder(EtchedBorder.RAISED,
            Color.white, new Color(165, 163, 151));
    Border border22 = new TitledBorder(border21, Language.getString("Width_Units"));
    JRadioButton rbPercent = new JRadioButton();
    JRadioButton rbPixels = new JRadioButton();

    public void spBarWidth_stateChanged(ChangeEvent e) {
        customError.getErrors().setSize(((Integer)spBarWidth.getValue()).intValue());
    }

    public void cbColorEach_actionPerformed(ActionEvent e) {
        customError.setColorEach(cbColorEach.isSelected());
    }

    public void rbPercent_actionPerformed(ActionEvent e) {
        customError.getErrors().setErrorSizeUnits(ErrorWidthUnit.PERCENT);          
    }

    public void rbPixels_actionPerformed(ActionEvent e) {
        customError.getErrors().setErrorSizeUnits(ErrorWidthUnit.PIXELS);
    }
}

class ErrorPointEditor_spBarWidth_changeAdapter implements ChangeListener {
    private ErrorPointEditor adaptee;
    ErrorPointEditor_spBarWidth_changeAdapter(ErrorPointEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void stateChanged(ChangeEvent e) {
        adaptee.spBarWidth_stateChanged(e);
    }
}

class ErrorPointEditor_cbColorEach_actionAdapter implements ActionListener {
    private ErrorPointEditor adaptee;
    ErrorPointEditor_cbColorEach_actionAdapter(ErrorPointEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.cbColorEach_actionPerformed(e);
    }
}

final class ErrorPointEditor_rbPercent_actionAdapter implements ActionListener {
    private ErrorPointEditor adaptee;
    ErrorPointEditor_rbPercent_actionAdapter(ErrorPointEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.rbPercent_actionPerformed(e);
    }
}

final class ErrorPointEditor_rbPixels_actionAdapter implements ActionListener {
    private ErrorPointEditor adaptee;
    ErrorPointEditor_rbPixels_actionAdapter(ErrorPointEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.rbPixels_actionPerformed(e);
    }
}


