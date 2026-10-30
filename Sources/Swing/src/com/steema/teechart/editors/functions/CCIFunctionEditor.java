package com.steema.teechart.editors.functions;

import java.awt.Rectangle;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.event.*;

import com.steema.teechart.editors.BaseFunctionsEditor;
import com.steema.teechart.functions.Function;
import com.steema.teechart.functions.CCI;
import com.steema.teechart.languages.Language;

/**
 * <p>Title: CCIFunctionEditor class</p>
 *
 * <p>Description: CCI Function editor panel</p>
 *
 * <p>Copyright (c) 2004-2019 by Steema Software SL. All Rights Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 *
 */
public class CCIFunctionEditor extends BaseFunctionsEditor {


    public CCIFunctionEditor() {
        try {
            jbInit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void setFunction(Function f) {
        function = (CCI)f;
        tbConstant.setText(String.valueOf(function.getConstant()));
        spPeriod.setValue(new Integer((int) function.getPeriod()));

        tbConstant.addActionListener(new TextActionListener());
        tbConstant.getDocument().addDocumentListener(new tbConstantDocumentListener());
        tbConstant.getDocument().putProperty(Language.getString("name"), Language.getString("Text_Field"));
    }

    private void jbInit() throws Exception {
        this.setLayout(null);
        jLabel2.setDisplayedMnemonic(Language.getString("C").charAt(0));
        jLabel2.setHorizontalAlignment(SwingConstants.RIGHT);
        jLabel2.setLabelFor(tbConstant);
        jLabel2.setText(Language.getString("Constant"));
        jLabel2.setBounds(new Rectangle(21, 26, 58, 15));
        spPeriod.setBounds(new Rectangle(86, 48, 86, 22));
        spPeriod.addChangeListener(new CCIFunctionEditor_spPeriod_changeAdapter(this));
        jLabel3.setDisplayedMnemonic(Language.getString("P").charAt(0));
        jLabel3.setHorizontalAlignment(SwingConstants.RIGHT);
        jLabel3.setText(Language.getString("Period"));
        jLabel3.setBounds(new Rectangle(35, 52, 46, 15));
        tbConstant.setBounds(new Rectangle(86, 21, 86, 23));
        this.add(jLabel3);
        this.add(tbConstant);
        this.add(spPeriod);
        this.add(jLabel2);
    }

    CCI function;
    public JComponent controlToEnable;
    JLabel jLabel2 = new JLabel();
    JSpinner spPeriod = new JSpinner();
    JLabel jLabel3 = new JLabel();
    JTextField tbConstant = new JTextField();


    class TextActionListener implements ActionListener {
        /** Handle the text field Return. */
        public void actionPerformed(ActionEvent e) {
        }
    }


    class tbConstantDocumentListener implements DocumentListener {

        public void insertUpdate(DocumentEvent e) {
            tbConstantChange();
        }
        public void removeUpdate(DocumentEvent e) {
            tbConstantChange();
        }
        public void changedUpdate(DocumentEvent e) {
            //Plain text components don't fire these events
        }
        private void tbConstantChange() {
            /* TODO       if(!Regex.IsMatch(EConst.Text, @"[^0-9.,]") && EConst.Text!="")
                            try {
                                    function.Constant=Convert.ToDouble(EConst.Text);
                                    Changed();
                            }
                catch {throw;}*/
        }
    }


    private void Changed() {
            if (controlToEnable!=null) controlToEnable.setEnabled(true);
    }

    public void spPeriod_stateChanged(ChangeEvent e) {
        function.setPeriod(((Integer)spPeriod.getValue()).intValue());
        Changed();
    }
}


class CCIFunctionEditor_spPeriod_changeAdapter implements ChangeListener {
    private CCIFunctionEditor adaptee;
    CCIFunctionEditor_spPeriod_changeAdapter(CCIFunctionEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void stateChanged(ChangeEvent e) {
        adaptee.spPeriod_stateChanged(e);
    }
}


