package com.steema.teechart.editors.functions;

import java.awt.Rectangle;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.event.*;

import com.steema.teechart.functions.Function;
import com.steema.teechart.functions.MovingAverage;
import com.steema.teechart.languages.Language;


/**
 * <p>Title: MovingAverageFunctionEditor class</p>
 *
 * <p>Description:  MovingAverage Function Editor panel</p>
 *
 * <p>Copyright (c) 2004-2019 by Steema Software SL. All Rights Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 *
 */
public class MovingAverageFunctionEditor extends JPanel {


    public MovingAverageFunctionEditor() {
        try {
            jbInit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        cbStyle.addItem(Language.getString("Normal"));
        cbStyle.addItem(Language.getString("Weighted"));
        cbStyle.addItem(Language.getString("Weighted_by_Index"));
    }

    public void setFunction(Function f) {
        function = (MovingAverage) f;

        spPeriod.setValue(new Integer((int) function.getPeriod()));
        if (function.getWeighted()) {
            cbStyle.setSelectedIndex(1);
        } else if (function.getWeightedIndex()) {
            cbStyle.setSelectedIndex(2);
        } else {
            cbStyle.setSelectedIndex(0);
        }
    }

    private void jbInit() throws Exception {
        this.setLayout(null);
        jLabel1.setText(Language.getString("Period"));
        jLabel1.setBounds(new Rectangle(32, 20, 46, 15));
        jLabel1.setDisplayedMnemonic(Language.getString("P20").charAt(0));
        jLabel1.setHorizontalAlignment(SwingConstants.RIGHT);
        jLabel1.setLabelFor(spPeriod);
        spPeriod.setBounds(new Rectangle(82, 16, 88, 22));
        spPeriod.addChangeListener(new
                                   MovingAverageFunctionEditor_spPeriod_changeAdapter(this));
        cbStyle.setBounds(new Rectangle(82, 44, 89, 21));
        cbStyle.addActionListener(new
                                  MovingAverageFunctionEditor_cbStyle_actionAdapter(this));
        jLabel2.setHorizontalAlignment(SwingConstants.RIGHT);
        jLabel2.setText(Language.getString("Average"));
        jLabel2.setBounds(new Rectangle(14, 48, 64, 15));
        this.add(spPeriod);
        this.add(jLabel1);
        this.add(cbStyle);
        this.add(jLabel2);
    }

    MovingAverage function;
    public JComponent controlToEnable;
    JLabel jLabel1 = new JLabel();
    JSpinner spPeriod = new JSpinner();
    JComboBox cbStyle = new JComboBox();
    JLabel jLabel2 = new JLabel();

    private void Changed() {
        if (controlToEnable != null) {
            controlToEnable.setEnabled(true);
        }
    }

    public void cbStyle_actionPerformed(ActionEvent e) {
        if (function != null) {
            function.setWeighted(cbStyle.getSelectedIndex() == 1);
            function.setWeightedIndex(cbStyle.getSelectedIndex() == 2);
            Changed();
        }
    }

    public void spPeriod_stateChanged(ChangeEvent e) {
        function.setPeriod(((Integer) spPeriod.getValue()).intValue());
        Changed();
    }
}


class MovingAverageFunctionEditor_spPeriod_changeAdapter implements
        ChangeListener {
    private MovingAverageFunctionEditor adaptee;
    MovingAverageFunctionEditor_spPeriod_changeAdapter(
            MovingAverageFunctionEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void stateChanged(ChangeEvent e) {
        adaptee.spPeriod_stateChanged(e);
    }
}


class MovingAverageFunctionEditor_cbStyle_actionAdapter implements
        ActionListener {
    private MovingAverageFunctionEditor adaptee;
    MovingAverageFunctionEditor_cbStyle_actionAdapter(
            MovingAverageFunctionEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.cbStyle_actionPerformed(e);
    }
}

