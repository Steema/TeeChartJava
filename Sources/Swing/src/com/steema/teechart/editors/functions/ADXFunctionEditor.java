package com.steema.teechart.editors.functions;

import java.awt.Rectangle;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.event.*;

import com.steema.teechart.editors.ButtonPen;
import com.steema.teechart.functions.Function;
import com.steema.teechart.functions.ADX;
import com.steema.teechart.editors.BaseFunctionsEditor;
import com.steema.teechart.languages.Language;

/**
 * <p>Title: class</p>
 *
 * <p>Description:  editor panel</p>
 *
 * <p>Copyright (c) 2004-2019 by Steema Software SL. All Rights Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 *
 */
public class ADXFunctionEditor extends BaseFunctionsEditor {


    public ADXFunctionEditor() {
        try {
            jbInit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void setFunction(Function f) {      
        function=(ADX)f;
        spPeriod.setValue(new Integer((int) function.getPeriod()));
        bUp.setPen(function.getUpLinePen());
        bDown.setPen(function.getDownLinePen());
    }

    private void jbInit() throws Exception {
        this.setLayout(null);
        jLabel1.setDisplayedMnemonic(Language.getString("P").charAt(0));
        jLabel1.setHorizontalAlignment(SwingConstants.RIGHT);
        jLabel1.setLabelFor(spPeriod);
        jLabel1.setText(Language.getString("Period"));
        jLabel1.setBounds(new Rectangle(36, 39, 46, 15));
        spPeriod.setBounds(new Rectangle(86, 35, 56, 22));
        spPeriod.addChangeListener(new ADXFunctionEditor_spPeriod_changeAdapter(this));
        bUp.setBounds(new Rectangle(86, 66, 87, 23));
        bUp.setMnemonic(Language.getString("U").charAt(0));
        bUp.setText(Language.getString("Up"));
        bUp.addActionListener(new ADXFunctionEditor_bUp_actionAdapter(this));
        bDown.setBounds(new Rectangle(86, 96, 87, 23));
        bDown.setMnemonic(Language.getString("D").charAt(0));
        bDown.setText(Language.getString("Down"));
        bDown.addActionListener(new ADXFunctionEditor_bDown_actionAdapter(this));
        this.add(jLabel1);
        this.add(spPeriod);
        this.add(bUp);
        this.add(bDown);
    }

    ADX function;
    public JComponent controlToEnable;
    JLabel jLabel1 = new JLabel();
    JSpinner spPeriod = new JSpinner();
    ButtonPen bUp = new ButtonPen();
    ButtonPen bDown = new ButtonPen();

    public void spPeriod_stateChanged(ChangeEvent e) {
        function.setPeriod(((Integer)spPeriod.getValue()).intValue());
        Changed();
    }

    public void bUp_actionPerformed(ActionEvent e) {
        function.getUpLinePen().assign(bUp.getPen());
        Changed();
    }

    public void bDown_actionPerformed(ActionEvent e) {
        function.getDownLinePen().assign(bDown.getPen());
        Changed();
    }
    private void Changed()
    {
       if (controlToEnable!=null) controlToEnable.setEnabled(true);
    }
}


class ADXFunctionEditor_bDown_actionAdapter implements ActionListener {
    private ADXFunctionEditor adaptee;
    ADXFunctionEditor_bDown_actionAdapter(ADXFunctionEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.bDown_actionPerformed(e);
    }
}


class ADXFunctionEditor_bUp_actionAdapter implements ActionListener {
    private ADXFunctionEditor adaptee;
    ADXFunctionEditor_bUp_actionAdapter(ADXFunctionEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.bUp_actionPerformed(e);
    }
}


class ADXFunctionEditor_spPeriod_changeAdapter implements ChangeListener {
    private ADXFunctionEditor adaptee;
    ADXFunctionEditor_spPeriod_changeAdapter(ADXFunctionEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void stateChanged(ChangeEvent e) {
        adaptee.spPeriod_stateChanged(e);
    }
}


