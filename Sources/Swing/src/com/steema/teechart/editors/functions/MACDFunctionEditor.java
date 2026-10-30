package com.steema.teechart.editors.functions;

import java.awt.Rectangle;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.event.*;

import com.steema.teechart.editors.ButtonPen;
import com.steema.teechart.functions.Function;
import com.steema.teechart.editors.BaseFunctionsEditor;
import com.steema.teechart.languages.Language;
import com.steema.teechart.functions.MACD;

/**
 * <p>Title: MACDFunctionEditor class</p>
 *
 * <p>Description: MACD Function Editor panel</p>
 *
 * <p>Copyright (c) 2004-2019 by Steema Software SL. All Rights Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 *
 */
public class MACDFunctionEditor extends  BaseFunctionsEditor {


    public MACDFunctionEditor() {
        try {
            jbInit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void setFunction(Function f) {
        function = (MACD)f;
        jSpinner1.setValue(new Integer((int) function.getPeriod()));
        jSpinner2.setValue(new Integer((int) function.getPeriod2()));
        jSpinner3.setValue(new Integer((int) function.getPeriod3()));
        bHistogram.setPen(function.getHistoPen());
        bMACDExp.setPen(function.getMACDExpPen());
        bMACD.setPen(function.getMACDPen());
    }

    private void jbInit() throws Exception {
        this.setLayout(null);
        bHistogram.setBounds(new Rectangle(136, 19, 91, 23));
        bHistogram.setMnemonic(Language.getString("H").charAt(0));
        bHistogram.setText(Language.getString("Histogram"));
        bMACD.setBounds(new Rectangle(137, 46, 91, 23));
        bMACD.setMnemonic(Language.getString("M").charAt(0));
        bMACD.setText(Language.getString("MACD"));
        bMACDExp.setBounds(new Rectangle(137, 74, 91, 23));
        bMACDExp.setToolTipText("");
        bMACDExp.setMnemonic(Language.getString("E").charAt(0));
        bMACDExp.setText(Language.getString("Exp_Line"));
        jLabel1.setDisplayedMnemonic('2');
        jLabel1.setHorizontalAlignment(SwingConstants.RIGHT);
        jLabel1.setText(Language.getString("Period"));
        jLabel1.setBounds(new Rectangle(14, 51, 48, 15));
        jLabel2.setDisplayedMnemonic('3');
        jLabel2.setHorizontalAlignment(SwingConstants.RIGHT);
        jLabel2.setText(Language.getString("Period"));
        jLabel2.setBounds(new Rectangle(14, 79, 48, 15));
        jLabel3.setDisplayedMnemonic('1');
        jLabel3.setHorizontalAlignment(SwingConstants.RIGHT);
        jLabel3.setText(Language.getString("Period"));
        jLabel3.setBounds(new Rectangle(14, 23, 48, 15));
        jSpinner1.setBounds(new Rectangle(66, 19, 67, 23));
        jSpinner2.setBounds(new Rectangle(66, 47, 67, 23));
        jSpinner3.setBounds(new Rectangle(66, 75, 67, 23));
        this.add(jLabel3);
        this.add(bHistogram);
        this.add(bMACD);
        this.add(jLabel1);
        this.add(jSpinner1);
        this.add(jSpinner2);
        this.add(jSpinner3);
        this.add(jLabel2);
        this.add(bMACDExp);
    }

    MACD function;
    public JComponent controlToEnable;
    ButtonPen bHistogram = new ButtonPen();
    ButtonPen bMACD = new ButtonPen();
    ButtonPen bMACDExp = new ButtonPen();
    JLabel jLabel1 = new JLabel();
    JLabel jLabel2 = new JLabel();
    JLabel jLabel3 = new JLabel();
    JSpinner jSpinner1 = new JSpinner();
    JSpinner jSpinner2 = new JSpinner();
    JSpinner jSpinner3 = new JSpinner();


    public void jSpinner1_stateChanged(ChangeEvent e) {
        function.setPeriod(((Integer)jSpinner1.getValue()).intValue());
        Changed();
    }

    public void jSpinner2_stateChanged(ChangeEvent e) {
        function.setPeriod2(((Integer)jSpinner2.getValue()).intValue());
        Changed();
    }
    
    public void jSpinner3_stateChanged(ChangeEvent e) {
        function.setPeriod3(((Integer)jSpinner3.getValue()).intValue());
        Changed();
    }

    public void bHistogram_actionPerformed(ActionEvent e) {
        function.getHistoPen().assign(bHistogram.getPen());
        Changed();
    }

    public void bMACDExp_actionPerformed(ActionEvent e) {
        function.getMACDExpPen().assign(bMACDExp.getPen());
        Changed();
    }

    public void bMACD_actionPerformed(ActionEvent e) {
        function.getMACDPen().assign(bMACD.getPen());
        Changed();
    }
   
    private void Changed()
    {
            if (controlToEnable != null) controlToEnable.setEnabled(true);
    }
  
}

class MACDFunctionEditor_bHistogram_actionAdapter implements ActionListener {
    private MACDFunctionEditor adaptee;
    MACDFunctionEditor_bHistogram_actionAdapter(MACDFunctionEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.bHistogram_actionPerformed(e);
    }
}

class MACDFunctionEditor_bMACDExp_actionAdapter implements ActionListener {
    private MACDFunctionEditor adaptee;
    MACDFunctionEditor_bMACDExp_actionAdapter(MACDFunctionEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.bMACDExp_actionPerformed(e);
    }
}

class MACDFunctionEditor_bMACD_actionAdapter implements ActionListener {
    private MACDFunctionEditor adaptee;
    MACDFunctionEditor_bMACD_actionAdapter(MACDFunctionEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.bMACD_actionPerformed(e);
    }
}

class MACDFunctionEditor_jSpinner1_changeAdapter implements ChangeListener {
    private MACDFunctionEditor adaptee;
    MACDFunctionEditor_jSpinner1_changeAdapter(MACDFunctionEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void stateChanged(ChangeEvent e) {
        adaptee.jSpinner1_stateChanged(e);
    }
}

class MACDFunctionEditor_jSpinner2_changeAdapter implements ChangeListener {
    private MACDFunctionEditor adaptee;
    MACDFunctionEditor_jSpinner2_changeAdapter(MACDFunctionEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void stateChanged(ChangeEvent e) {
        adaptee.jSpinner2_stateChanged(e);
    }
}

class MACDFunctionEditor_jSpinner3_changeAdapter implements ChangeListener {
    private MACDFunctionEditor adaptee;
    MACDFunctionEditor_jSpinner3_changeAdapter(MACDFunctionEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void stateChanged(ChangeEvent e) {
        adaptee.jSpinner3_stateChanged(e);
    }
}



