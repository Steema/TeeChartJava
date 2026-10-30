package com.steema.teechart.editors.functions;

import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import com.steema.teechart.functions.Custom;
import com.steema.teechart.functions.Function;
import com.steema.teechart.languages.Language;

/**
 * <p>Title: CustomFunctionEditor class</p>
 *
 * <p>Description: Custom Function Editor panel</p>
 *
 * <p>Copyright (c) 2004-2019 by Steema Software SL. All Rights Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 *
 */
public class CustomFunctionEditor extends JPanel {


    public CustomFunctionEditor() {
        try {
            jbInit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void setFunction(Function f) {
        function = (Custom)f;
        tbStartX.setText(String.valueOf(function.getStartX()));
        tbStep.setText(String.valueOf(function.getPeriod()));
        spNumPoints.setValue(new Integer(function.getNumPoints()));

        tbStep.addActionListener(new TextActionListener());
        tbStep.getDocument().addDocumentListener(new tbStepDocumentListener());
        tbStep.getDocument().putProperty(Language.getString("name"), Language.getString("Text_Field"));
        tbStartX.addActionListener(new TextActionListener());
        tbStartX.getDocument().addDocumentListener(new tbStartXDocumentListener());
        tbStartX.getDocument().putProperty(Language.getString("name"), Language.getString("Text_Field"));

    }

    private void jbInit() throws Exception {
        this.setLayout(null);
        jLabel1.setDisplayedMnemonic(Language.getString("S").charAt(0));
        jLabel1.setHorizontalAlignment(SwingConstants.RIGHT);
        jLabel1.setLabelFor(tbStartX);
        jLabel1.setText(Language.getString("Start_X"));
        jLabel1.setBounds(new Rectangle(38, 25, 47, 15));
        jLabel2.setDisplayedMnemonic(Language.getString("T").charAt(0));
        jLabel2.setHorizontalAlignment(SwingConstants.RIGHT);
        jLabel2.setLabelFor(tbStep);
        jLabel2.setText(Language.getString("Step"));
        jLabel2.setBounds(new Rectangle(43, 54, 43, 15));
        jLabel3.setDisplayedMnemonic(Language.getString("N").charAt(0));
        jLabel3.setHorizontalAlignment(SwingConstants.RIGHT);
        jLabel3.setText(Language.getString("Num_points"));
        jLabel3.setBounds(new Rectangle(20, 82, 68, 15));
        tbStartX.setText("0");
        tbStartX.setHorizontalAlignment(SwingConstants.RIGHT);
        tbStartX.setBounds(new Rectangle(100, 21, 86, 20));
        tbStep.setText("1");
        tbStep.setHorizontalAlignment(SwingConstants.RIGHT);
        tbStep.setBounds(new Rectangle(100, 50, 86, 20));
        spNumPoints.setBounds(new Rectangle(99, 79, 71, 21));
        spNumPoints.addChangeListener(new
                CustomFunctionEditor_spNumPoints_changeAdapter(this));
        this.add(jLabel2);
        this.add(jLabel1);
        this.add(spNumPoints);
        this.add(tbStep);
        this.add(tbStartX);
        this.add(jLabel3);
    }

    Custom function;
    public JComponent controlToEnable;
    JLabel jLabel1 = new JLabel();
    JLabel jLabel2 = new JLabel();
    JLabel jLabel3 = new JLabel();
    JTextField tbStartX = new JTextField();
    JTextField tbStep = new JTextField();
    JSpinner spNumPoints = new JSpinner();

    private void Changed() {
      if (controlToEnable!=null) controlToEnable.setEnabled(true);
    }

    public void spNumPoints_stateChanged(ChangeEvent e) {
        function.setNumPoints(((Integer)spNumPoints.getValue()).intValue());
        Changed();
    }

    class TextActionListener implements ActionListener {
        /** Handle the text field Return. */
        public void actionPerformed(ActionEvent e) {
        }
    }

    class tbStepDocumentListener implements DocumentListener {

        public void insertUpdate(DocumentEvent e) {
            if (tbStep.getText().length()>0) {
                function.setPeriod(Double.parseDouble(tbStep.getText()));
                Changed();
            }
        }
        public void removeUpdate(DocumentEvent e) {
            if (tbStep.getText().length()>0) {
                function.setPeriod(Double.parseDouble(tbStep.getText()));
                Changed();
            }
        }
        public void changedUpdate(DocumentEvent e) {
            //Plain text components don't fire these events
        }
    }

    class tbStartXDocumentListener implements DocumentListener {

        public void insertUpdate(DocumentEvent e) {
            if (tbStartX.getText().length()>0) {
                function.setStartX(Double.parseDouble(tbStartX.getText()));
                Changed();
            }
        }
        public void removeUpdate(DocumentEvent e) {
            if (tbStartX.getText().length()>0) {
                function.setStartX(Double.parseDouble(tbStartX.getText()));
                Changed();
            }
        }
        public void changedUpdate(DocumentEvent e) {
            //Plain text components don't fire these events
        }
    }
}

class CustomFunctionEditor_spNumPoints_changeAdapter implements ChangeListener {
    private CustomFunctionEditor adaptee;
    CustomFunctionEditor_spNumPoints_changeAdapter(CustomFunctionEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void stateChanged(ChangeEvent e) {
        adaptee.spNumPoints_stateChanged(e);
    }
}

