package com.steema.teechart.editors.functions;


import java.awt.Rectangle;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.event.*;

import com.steema.teechart.misc.*;
import com.steema.teechart.editors.ButtonPen;
import com.steema.teechart.editors.BaseFunctionsEditor;
import com.steema.teechart.functions.Bollinger;
import com.steema.teechart.functions.Function;
import com.steema.teechart.languages.Language;

/**
 * <p>Title: BollingerFunctionEditor class</p>
 *
 * <p>Description:  Bollinger Function editor panel</p>
 *
 * <p>Copyright (c) 2004-2019 by Steema Software SL. All Rights Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 *
 */

public class BollingerFunctionEditor extends BaseFunctionsEditor {


    public BollingerFunctionEditor() {
        try {
            jbInit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void setFunction(Function f) {
        function = (Bollinger)f;
        bLower.setPen(function.getLowBandPen());
        spPeriod.setValue(new Integer((int) function.getPeriod()));
        cbExponential.setSelected(function.getExponential());
        textBoxDeviation.setText(String.valueOf(function.getDeviation()));
    }

    private void jbInit() throws Exception {
        this.setLayout(null);
        cbExponential.setMnemonic(Language.getString("E26").charAt(0));
        cbExponential.setText(Language.getString("Exponential"));
        cbExponential.setBounds(new Rectangle(82, 62, 83, 23));
        cbExponential.addActionListener(new
                BollingerFunctionEditor_cbExponential_actionAdapter(this));
        jLabel1.setDisplayedMnemonic(Language.getString("D21").charAt(0));
        jLabel1.setHorizontalAlignment(SwingConstants.RIGHT);
        jLabel1.setLabelFor(textBoxDeviation);
        jLabel1.setText(Language.getString("Deviation"));
        jLabel1.setBounds(new Rectangle(26, 93, 58, 15));
        bLower.setBounds(new Rectangle(159, 90, 75, 23));
        bLower.setMnemonic(Language.getString("L19").charAt(0));
        bLower.setText(Language.getString("Lower"));
        bLower.addActionListener(new
                                 BollingerFunctionEditor_bLower_actionAdapter(this));
        spPeriod.setBounds(new Rectangle(86, 35, 56, 22));
        spPeriod.addChangeListener(new
                BollingerFunctionEditor_spPeriod_changeAdapter(this));
        jLabel2.setDisplayedMnemonic(Language.getString("P21").charAt(0));
        jLabel2.setHorizontalAlignment(SwingConstants.RIGHT);
        jLabel2.setText(Language.getString("Period"));
        jLabel2.setBounds(new Rectangle(36, 39, 46, 15));
        textBoxDeviation.setBounds(new Rectangle(86, 91, 62, 20));
        textBoxDeviation.addActionListener(new
                BollingerFunctionEditor_textBoxDeviation_actionAdapter(this));
        
        this.add(spPeriod);
        this.add(jLabel2);
        this.add(textBoxDeviation);
        this.add(cbExponential);
        this.add(jLabel1);
        this.add(bLower);
    }

    Bollinger function;
    public JComponent controlToEnable;
    JCheckBox cbExponential = new JCheckBox();
    JLabel jLabel1 = new JLabel();
    ButtonPen bLower = new ButtonPen();
    JSpinner spPeriod = new JSpinner();
    JLabel jLabel2 = new JLabel();
    JTextField textBoxDeviation = new JTextField();

    private void Changed()
    {
            if (controlToEnable != null) controlToEnable.setEnabled(true);
    }

    public void spPeriod_stateChanged(ChangeEvent e) {
        function.setPeriod(((Integer)spPeriod.getValue()).intValue());
        Changed();
    }

    public void cbExponential_actionPerformed(ActionEvent e) {
        function.setExponential(cbExponential.isSelected());
        Changed();
    }

    public void textBoxDeviation_actionPerformed(ActionEvent e) {
        function.setDeviation(Utils.stringToDouble(textBoxDeviation.getText(),function.getDeviation()));
        Changed();
    }

    public void bLower_actionPerformed(ActionEvent e) {
        function.getLowBandPen().assign(bLower.getPen());
        Changed();
    }
}


class BollingerFunctionEditor_bLower_actionAdapter implements ActionListener {
    private BollingerFunctionEditor adaptee;
    BollingerFunctionEditor_bLower_actionAdapter(BollingerFunctionEditor
                                                 adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.bLower_actionPerformed(e);
    }
}


class BollingerFunctionEditor_textBoxDeviation_actionAdapter implements ActionListener {
    private BollingerFunctionEditor adaptee;
    BollingerFunctionEditor_textBoxDeviation_actionAdapter(BollingerFunctionEditor
            adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.textBoxDeviation_actionPerformed(e);
    }
}


class BollingerFunctionEditor_cbExponential_actionAdapter implements
        ActionListener {
    private BollingerFunctionEditor adaptee;
    BollingerFunctionEditor_cbExponential_actionAdapter(BollingerFunctionEditor
            adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.cbExponential_actionPerformed(e);
    }
}


class BollingerFunctionEditor_spPeriod_changeAdapter implements ChangeListener {
    private BollingerFunctionEditor adaptee;
    BollingerFunctionEditor_spPeriod_changeAdapter(BollingerFunctionEditor
            adaptee) {
        this.adaptee = adaptee;
    }

    public void stateChanged(ChangeEvent e) {
        adaptee.spPeriod_stateChanged(e);
    }
}


