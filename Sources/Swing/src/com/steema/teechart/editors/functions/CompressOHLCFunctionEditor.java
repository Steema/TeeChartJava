package com.steema.teechart.editors.functions;

import java.awt.Rectangle;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.event.*;

import com.steema.teechart.functions.Function;
import com.steema.teechart.functions.CompressOHLC;
import com.steema.teechart.functions.CompressionPeriod;
import com.steema.teechart.languages.Language;

/**
 * <p>Title: CompressOHLCFunctionEditor class</p>
 *
 * <p>Description:  CompressOHLC Function Editor panel</p>
 *
 * <p>Copyright (c) 2004-2019 by Steema Software SL. All Rights Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 *
 */
public class CompressOHLCFunctionEditor extends JPanel {


    public CompressOHLCFunctionEditor() {
        try {
            jbInit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        cbPeriod.addItem(Language.getString("Daily"));
        cbPeriod.addItem(Language.getString("Weekly"));
        cbPeriod.addItem(Language.getString("Monthly"));
        cbPeriod.addItem(Language.getString("Bi_Monthly"));
        cbPeriod.addItem(Language.getString("Quarterly"));
        cbPeriod.addItem(Language.getString("Yearly"));
    }

    public void setFunction(Function f) {
        function = (CompressOHLC)f;
        switch (function.getCompress().getValue()){
                case 0:
                        cbPeriod.setSelectedIndex(0);
                        break;
                case 1:
                        cbPeriod.setSelectedIndex(1);
                        break;
                case 2:
                        cbPeriod.setSelectedIndex(2);
                        break;
                case 3:
                        cbPeriod.setSelectedIndex(3);
                        break;
                case 4:
                        cbPeriod.setSelectedIndex(4);
                        break;
                case 5:
                        cbPeriod.setSelectedIndex(5);
                        break;
        }
//        spNum.setValue((decimal)function.getPeriod());
        jRadioButton1.setSelected(((Integer)spNum.getValue()).intValue()==0);
        jRadioButton2.setSelected(jRadioButton1.isSelected());
    }

    private void jbInit() throws Exception {
        this.setLayout(null);
        jRadioButton1.setMnemonic(Language.getString("D20").charAt(0));
        jRadioButton1.setSelected(true);
        jRadioButton1.setText(Language.getString("DateTime_period"));
        jRadioButton1.setBounds(new Rectangle(17, 22, 111, 23));
        jRadioButton1.addActionListener(new
                CompressOHLCFunctionEditor_jRadioButton1_actionAdapter(this));
        jRadioButton2.setMnemonic(Language.getString("N11").charAt(0));
        jRadioButton2.setText(Language.getString("Number_of_points"));
        jRadioButton2.setBounds(new Rectangle(16, 50, 114, 23));
        jRadioButton2.addActionListener(new
                CompressOHLCFunctionEditor_jRadioButton2_actionAdapter(this));
        cbPeriod.setBounds(new Rectangle(134, 21, 83, 22));
        cbPeriod.addActionListener(new
                CompressOHLCFunctionEditor_cbPeriod_actionAdapter(this));
        spNum.setBounds(new Rectangle(134, 50, 82, 20));
        spNum.setValue(new Integer(1));
        spNum.addChangeListener(new
                                CompressOHLCFunctionEditor_spNum_changeAdapter(this));
        this.add(spNum);
        this.add(cbPeriod);
        this.add(jRadioButton1);
        this.add(jRadioButton2);

        ButtonGroup group = new ButtonGroup();
        group.add(jRadioButton1);
        group.add(jRadioButton2);
    }

    CompressOHLC function;
    public JComponent controlToEnable;
    JRadioButton jRadioButton1 = new JRadioButton();
    JRadioButton jRadioButton2 = new JRadioButton();
    JComboBox cbPeriod = new JComboBox();
    JSpinner spNum = new JSpinner();

    private void Changed() {
            if (controlToEnable!=null) controlToEnable.setEnabled(true);
    }

    public void spNum_stateChanged(ChangeEvent e) {
        function.setPeriod(((Integer)spNum.getValue()).intValue());
        Changed();
    }

    public void cbPeriod_actionPerformed(ActionEvent e) {
        if (function !=null) {
            switch (cbPeriod.getSelectedIndex()) {
            case 0:
                function.setCompress(CompressionPeriod.DAY);
                break;
            case 1:
                function.setCompress(CompressionPeriod.WEEK);
                break;
            case 2:
                function.setCompress(CompressionPeriod.MONTH);
                break;
            case 3:
                function.setCompress(CompressionPeriod.BIMONTH);
                break;
            case 4:
                function.setCompress(CompressionPeriod.QUARTER);
                break;
            case 5:
                function.setCompress(CompressionPeriod.YEAR);
                break;
            }

            Changed();
        }
    }

    public void jRadioButton1_actionPerformed(ActionEvent e) {
        spNum.setValue(new Integer(0));
        cbPeriod.requestFocus();
        Changed();
    }

    public void jRadioButton2_actionPerformed(ActionEvent e) {
        spNum.requestFocus();
	Changed();
    }
}


class CompressOHLCFunctionEditor_jRadioButton2_actionAdapter implements
        ActionListener {
    private CompressOHLCFunctionEditor adaptee;
    CompressOHLCFunctionEditor_jRadioButton2_actionAdapter(
            CompressOHLCFunctionEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.jRadioButton2_actionPerformed(e);
    }
}


class CompressOHLCFunctionEditor_spNum_changeAdapter implements ChangeListener {
    private CompressOHLCFunctionEditor adaptee;
    CompressOHLCFunctionEditor_spNum_changeAdapter(CompressOHLCFunctionEditor
            adaptee) {
        this.adaptee = adaptee;
    }

    public void stateChanged(ChangeEvent e) {
        adaptee.spNum_stateChanged(e);
    }
}


class CompressOHLCFunctionEditor_cbPeriod_actionAdapter implements
        ActionListener {
    private CompressOHLCFunctionEditor adaptee;
    CompressOHLCFunctionEditor_cbPeriod_actionAdapter(
            CompressOHLCFunctionEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.cbPeriod_actionPerformed(e);
    }
}


class CompressOHLCFunctionEditor_jRadioButton1_actionAdapter implements
        ActionListener {
    private CompressOHLCFunctionEditor adaptee;
    CompressOHLCFunctionEditor_jRadioButton1_actionAdapter(
            CompressOHLCFunctionEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.jRadioButton1_actionPerformed(e);
    }
}

