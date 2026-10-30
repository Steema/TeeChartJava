package com.steema.teechart.editors.functions;

import java.awt.Rectangle;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.event.*;

import com.steema.teechart.functions.Function;
import com.steema.teechart.functions.Smoothing;
import com.steema.teechart.languages.Language;


/**
 * <p>Title: SmoothingFunctionEditor class</p>
 *
 * <p>Description:  Smoothing Function Editor panel</p>
 *
 * <p>Copyright (c) 2004-2019 by Steema Software SL. All Rights Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 *
 */
public class SmoothingFunctionEditor extends JPanel {


    public SmoothingFunctionEditor() {
        try {
            jbInit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void setFunction(Function f) {
        function = (Smoothing)f;
        spFactor.setValue(new Float(function.getFactor()));
        cbInterpolate.setSelected(function.getInterpolate());
    }

    private void jbInit() throws Exception {
        this.setLayout(null);
        cbInterpolate.setSelected(true);
        cbInterpolate.setText(Language.getString("Interpolate"));
        cbInterpolate.setBounds(new Rectangle(69, 24, 83, 23));
        cbInterpolate.addActionListener(new
                SmoothingFunctionEditor_cbInterpolate_actionAdapter(this));
        spFactor.setBounds(new Rectangle(72, 53, 71, 21));
        spFactor.setValue(new Integer(1));
        spFactor.addChangeListener(new
                SmoothingFunctionEditor_spFactor_changeAdapter(this));
        jLabel1.setDisplayedMnemonic(Language.getString("F8").charAt(0));
        jLabel1.setHorizontalAlignment(SwingConstants.RIGHT);
        jLabel1.setText(Language.getString("Factor"));
        jLabel1.setBounds(new Rectangle(13, 56, 52, 15));
        this.add(spFactor);
        this.add(cbInterpolate);
        this.add(jLabel1);
    }

    Smoothing function;
    public JComponent controlToEnable;
    JCheckBox cbInterpolate = new JCheckBox();
    JSpinner spFactor = new JSpinner();
    JLabel jLabel1 = new JLabel();

    private void Changed()
    {
            if (controlToEnable != null) controlToEnable.setEnabled(true);
    }

    public void spFactor_stateChanged(ChangeEvent e) {
        function.setFactor(((Integer)spFactor.getValue()).intValue());
        Changed();
    }

    public void cbInterpolate_actionPerformed(ActionEvent e) {
        function.setInterpolate(cbInterpolate.isSelected());
        Changed();
    }
}


class SmoothingFunctionEditor_cbInterpolate_actionAdapter implements
        ActionListener {
    private SmoothingFunctionEditor adaptee;
    SmoothingFunctionEditor_cbInterpolate_actionAdapter(SmoothingFunctionEditor
            adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.cbInterpolate_actionPerformed(e);
    }
}


class SmoothingFunctionEditor_spFactor_changeAdapter implements ChangeListener {
    private SmoothingFunctionEditor adaptee;
    SmoothingFunctionEditor_spFactor_changeAdapter(SmoothingFunctionEditor
            adaptee) {
        this.adaptee = adaptee;
    }

    public void stateChanged(ChangeEvent e) {
        adaptee.spFactor_stateChanged(e);
    }
}


