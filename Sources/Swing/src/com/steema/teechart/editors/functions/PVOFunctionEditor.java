package com.steema.teechart.editors.functions;

import java.awt.Rectangle;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.event.*;

import com.steema.teechart.functions.Function;
import com.steema.teechart.functions.PVO;
import com.steema.teechart.languages.Language;

/**
 * <p>Title: PVOFunctionEditor class</p>
 *
 * <p>Description:  PVO Function Editor panel</p>
 *
 * <p>Copyright (c) 2004-2019 by Steema Software SL. All Rights Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 *
 */
public class PVOFunctionEditor extends JPanel {


    public PVOFunctionEditor() {
        try {
            jbInit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void setFunction(Function f) {
        function = (PVO)f;
        spPeriod2.setValue(new Integer(function.getPeriod2()));
        spPeriod1.setValue(new Integer((int)function.getPeriod()));
        cbPercent.setSelected(function.getPercentage());
    }

    private void jbInit() throws Exception {
        this.setLayout(null);
        spPeriod2.setBounds(new Rectangle(66, 43, 86, 22));
        spPeriod2.addChangeListener(new
                                    PVOFunctionEditor_spPeriod2_changeAdapter(this));
        jLabel1.setDisplayedMnemonic(Language.getString("E").charAt(0));
        jLabel1.setHorizontalAlignment(SwingConstants.RIGHT);
        jLabel1.setLabelFor(spPeriod2);
        jLabel1.setText(Language.getString("Period"));
        jLabel1.setBounds(new Rectangle(15, 47, 46, 15));
        spPeriod1.setBounds(new Rectangle(66, 15, 86, 22));
        spPeriod1.addChangeListener(new
                                    PVOFunctionEditor_spPeriod1_changeAdapter(this));
        jLabel2.setDisplayedMnemonic(Language.getString("P").charAt(0));
        jLabel2.setHorizontalAlignment(SwingConstants.RIGHT);
        jLabel2.setLabelFor(spPeriod1);
        jLabel2.setText(Language.getString("Period"));
        jLabel2.setBounds(new Rectangle(15, 19, 46, 15));
        cbPercent.setMnemonic(Language.getString("C").charAt(0));
        cbPercent.setText(Language.getString("Percentage"));
        cbPercent.setBounds(new Rectangle(63, 72, 83, 23));
        cbPercent.addActionListener(new
                                    PVOFunctionEditor_cbPercent_actionAdapter(this));
        this.add(jLabel2);
        this.add(spPeriod1);
        this.add(jLabel1);
        this.add(spPeriod2);
        this.add(cbPercent);
    }

    PVO function;
    public JComponent controlToEnable;
    JSpinner spPeriod2 = new JSpinner();
    JLabel jLabel1 = new JLabel();
    JSpinner spPeriod1 = new JSpinner();
    JLabel jLabel2 = new JLabel();
    JCheckBox cbPercent = new JCheckBox();

    private void Changed() {
            if (controlToEnable!=null) controlToEnable.setEnabled(true);
    }

    public void spPeriod2_stateChanged(ChangeEvent e) {
        function.setPeriod2(((Integer)spPeriod2.getValue()).intValue());
        Changed();
    }

    public void spPeriod1_stateChanged(ChangeEvent e) {
        function.setPeriod(((Integer)spPeriod1.getValue()).intValue());
        Changed();
    }

    public void cbPercent_actionPerformed(ActionEvent e) {
        function.setPercentage(cbPercent.isSelected());
        Changed();
    }
}


class PVOFunctionEditor_cbPercent_actionAdapter implements ActionListener {
    private PVOFunctionEditor adaptee;
    PVOFunctionEditor_cbPercent_actionAdapter(PVOFunctionEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.cbPercent_actionPerformed(e);
    }
}


class PVOFunctionEditor_spPeriod1_changeAdapter implements ChangeListener {
    private PVOFunctionEditor adaptee;
    PVOFunctionEditor_spPeriod1_changeAdapter(PVOFunctionEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void stateChanged(ChangeEvent e) {
        adaptee.spPeriod1_stateChanged(e);
    }
}


class PVOFunctionEditor_spPeriod2_changeAdapter implements ChangeListener {
    private PVOFunctionEditor adaptee;
    PVOFunctionEditor_spPeriod2_changeAdapter(PVOFunctionEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void stateChanged(ChangeEvent e) {
        adaptee.spPeriod2_stateChanged(e);
    }
}


