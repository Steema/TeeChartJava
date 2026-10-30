package com.steema.teechart.editors;

import java.awt.Rectangle;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;

import com.steema.teechart.*;
import com.steema.teechart.misc.Utils;

import java.awt.Color;
import java.awt.BorderLayout;
import com.steema.teechart.languages.Language;
import javax.swing.border.BevelBorder;
import javax.swing.border.EtchedBorder;
import java.text.DecimalFormat;

/**
 * <p>Title: TeeChart for Java</p>
 *
 * <p>Description: AxisIncrement editor panel</p>
 *
 * <p>Copyright (c) 2004-2019 by Steema Software SL. All Rights Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 *
 */

final public class AxisIncrement extends JDialog {
    BorderLayout borderLayout1 = new BorderLayout();
    private JComboBox cbSteps = new JComboBox();
    private JCheckBox cbExact = new JCheckBox();
    private Border border1 = BorderFactory.createEtchedBorder(Color.white,
            new Color(165, 163, 151));
    private Border border2 = new TitledBorder(border1, Language.getString("Increment"));
    private JRadioButton radioButton1 = new JRadioButton();
    private JRadioButton radioButton2 = new JRadioButton();
    private JTextField tbCustom = new JTextField();
    private JButton bOK = new JButton();
    private JButton bCancel = new JButton();

    protected boolean result;

    public static final int OK = 0;
    public static final int CANCEL = 1;

    private int dialogResult = CANCEL;

    protected boolean isDateTime;
    protected boolean isExact;
    protected double dIncrement;
    protected int iStep;
    Border border3 = BorderFactory.createEmptyBorder();
    Border border4 = new TitledBorder(border3, Language.getString("Increment"));
    JLabel jLabel1 = new JLabel();
    JPanel jPanel2 = new JPanel();
    Border border5 = BorderFactory.createBevelBorder(BevelBorder.RAISED,
            Color.white, Color.white, new Color(115, 114, 105),
            new Color(165, 163, 151));
    Border border6 = BorderFactory.createBevelBorder(BevelBorder.LOWERED,
            Color.white, Color.white, new Color(115, 114, 105),
            new Color(165, 163, 151));
    Border border7 = BorderFactory.createEtchedBorder(EtchedBorder.RAISED,
            Color.white, new Color(165, 163, 151));
    Border border8 = BorderFactory.createBevelBorder(BevelBorder.RAISED,
            Color.white, Color.white, new Color(115, 114, 105),
            new Color(165, 163, 151));
    Border border9 = BorderFactory.createBevelBorder(BevelBorder.LOWERED,
            Color.white, Color.white, new Color(115, 114, 105),
            new Color(165, 163, 151));
    Border border10 = BorderFactory.createEtchedBorder(Color.white,
            new Color(165, 163, 151));
    public AxisIncrement() {
        try {
            jbInit();
            load();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        cbSteps.addItem(Language.getString("One_Millisecond"));
        cbSteps.addItem(Language.getString("One_Second"));
        cbSteps.addItem(Language.getString("Five_Seconds"));
        cbSteps.addItem(Language.getString("Ten_Seconds"));
        cbSteps.addItem(Language.getString("Fifteen_Seconds"));
        cbSteps.addItem(Language.getString("Thirty_Seconds"));
        cbSteps.addItem(Language.getString("One_Minute"));
        cbSteps.addItem(Language.getString("Five_Minutes"));
        cbSteps.addItem(Language.getString("Ten_Minutes"));
        cbSteps.addItem(Language.getString("Fifteen_Minutes"));
        cbSteps.addItem(Language.getString("Thirty_Minutes"));
        cbSteps.addItem(Language.getString("One_Hour"));
        cbSteps.addItem(Language.getString("Two_Hours"));
        cbSteps.addItem(Language.getString("Six_Hours"));
        cbSteps.addItem(Language.getString("Twelve_Hours"));
        cbSteps.addItem(Language.getString("One_Day"));
        cbSteps.addItem(Language.getString("Two_Days"));
        cbSteps.addItem(Language.getString("Three_Days"));
        cbSteps.addItem(Language.getString("One_Week"));
        cbSteps.addItem(Language.getString("Half_Month"));
        cbSteps.addItem(Language.getString("One_Month"));
        cbSteps.addItem(Language.getString("Two_Months"));
        cbSteps.addItem(Language.getString("Three_Months"));
        cbSteps.addItem(Language.getString("Four_Months"));
        cbSteps.addItem(Language.getString("Six_Months"));
        cbSteps.addItem(Language.getString("One_Year"));
    }

    private void load() {
        cbExact.setVisible(isDateTime);
        cbExact.setEnabled(radioButton1.isSelected());
        cbExact.setSelected(isExact);

        if (isDateTime) {
            setEditText();

            if (isExact && (iStep != DateTimeStep.NONE)) {
                radioButton1.setSelected(true);
                cbSteps.setSelectedIndex(iStep);
                tbCustom.setText("");
                tbCustom.setEnabled(false);
                cbSteps.requestFocus();
            } else {
                radioButton2.setSelected(true);
                cbSteps.setSelectedIndex( -1);
            }
        } else {
            cbSteps.setVisible(false);
            radioButton2.setSelected(true);
            tbCustom.setText(Double.toString(dIncrement));
            int tmpDif=(tbCustom.getBounds().y - jLabel1.getBounds().y);
            tbCustom.getBounds().y=bOK.getBounds().y;
            jLabel1.getBounds().y=tbCustom.getBounds().y;
            tbCustom.requestFocus();
        }
    }

    public double getIncrement() {
        return dIncrement;
    }

    static private String GetDateTimeStepText(int tmp) {
        String result = "";
        AxisIncrement a = new AxisIncrement();
        a.cbSteps.getModel().getElementAt(tmp).toString();
        return result;
    }

    static public String getIncrementText(double increment,
                                          boolean isDateTime,
                                          boolean exactDateTime, String aFormat) {
        if (isDateTime) {
            String result;

            int tmp =  com.steema.teechart.axis.Axis.findDateTimeStep(increment);

            if (exactDateTime && (tmp != DateTimeStep.NONE)) {
                result = GetDateTimeStepText((int)DateTimeStep.STEP[tmp]);
            } else
            if (increment <= 0) {
                result=Utils.timeToStr(Utils.getDateTimeStep(DateTimeStep.ONESECOND));
            } else
            if (increment <= 1) {
                result = Utils.timeToStr(increment);
            } else {
                result = String.valueOf(Utils.round(increment));
                if (frac(increment) != 0) {
                    result += ' ' + Utils.timeToStr(frac(increment));
                }
            }

            return result;
        } else {
            return new DecimalFormat(aFormat).format(increment);
        }
    }

    static private double frac(double value) {
        return value - (int) (value);
    }

    private void setEditText() {
        if (dIncrement <= 0) {
            tbCustom.setText(Utils.timeToStr(Utils.getDateTimeStep(DateTimeStep.ONESECOND)));
        } else
        if (dIncrement >= 1) {
            tbCustom.setText(Integer.toString(Utils.round(dIncrement)));

            if (dIncrement - (int) (dIncrement) != 0) {
                tbCustom.setText(tbCustom.getText() + ' ' +
                                 String.valueOf(dIncrement -
                                                Utils.round(dIncrement)));
            }
        } else {
            tbCustom.setText(String.valueOf(dIncrement));
        }
    }

    private void doEnableControls(boolean value) {
        tbCustom.setEnabled(!value);
        cbExact.setSelected(value);
        isExact = value;
        cbExact.setEnabled(value);
        cbSteps.setEnabled(value);
    }

    private void updateControls() {
        if (radioButton1.isSelected()) {

            doEnableControls(true);

            if (iStep != DateTimeStep.NONE) {
                cbSteps.setSelectedIndex(iStep);
            } else {
                cbSteps.setSelectedIndex(0);
            }
            cbSteps.requestFocus();
        } else {
            doEnableControls(false);
            setEditText();
            tbCustom.requestFocus();
        }
    }

    private void jbInit() throws Exception {
        this.getContentPane().setLayout(null);
        jPanel2.setBorder(border10);
        jPanel2.setBounds(new Rectangle(10, 30, 276, 85));
        jPanel2.setLayout(null);
        radioButton2.setSelected(true);
        radioButton2.addActionListener(new
                                       AxisIncrement_radioButton2_actionAdapter(this));
        this.getContentPane().add(bOK);
        this.getContentPane().add(bCancel);
        this.getContentPane().add(cbExact);
        this.getContentPane().add(jLabel1);
        this.getContentPane().add(jPanel2);
        jPanel2.add(tbCustom);
        jPanel2.add(cbSteps);
        jPanel2.add(radioButton2);
        jPanel2.add(radioButton1);
        cbSteps.setBounds(new Rectangle(120, 19, 142, 22));
        cbExact.setMnemonic(Language.getString("E").charAt(0));
        cbExact.setText(Language.getString("Exact_DateTime"));
        cbExact.setBounds(new Rectangle(118, 15, 157, 23));
        cbExact.addActionListener(new AxisIncrement_cbExact_actionAdapter(this));
        radioButton1.setMnemonic(Language.getString("S").charAt(0));
        radioButton1.setText(Language.getString("Standard"));
        radioButton1.setBounds(new Rectangle(15, 18, 91, 23));
        radioButton1.addActionListener(new
                                       AxisIncrement_radioButton1_actionAdapter(this));
        radioButton2.setMnemonic(Language.getString("C").charAt(0));
        radioButton2.setText(Language.getString("Custom"));
        radioButton2.setBounds(new Rectangle(15, 46, 91, 23));
        tbCustom.setText("");
        tbCustom.setBounds(new Rectangle(120, 49, 143, 20));
        bOK.setBounds(new Rectangle(299, 55, 75, 23));
        bOK.setText(Language.getString("OK"));
        bCancel.setBounds(new Rectangle(299, 83, 75, 23));
        bCancel.setText(Language.getString("Cancel"));
        bCancel.addActionListener(new AxisIncrement_bCancel_actionAdapter(this));
        bOK.addActionListener(new AxisIncrement_bOK_actionAdapter(this));
        jLabel1.setText(Language.getString("Increment"));
        jLabel1.setBounds(new Rectangle(12, 2, 89, 18));

        ButtonGroup group = new ButtonGroup();
        group.add(radioButton1);
        group.add(radioButton2);
    }

    public void bCancel_actionPerformed(ActionEvent e) {
        dialogResult=CANCEL;
        dispose();
    }

    public void radioButton1_actionPerformed(ActionEvent e) {
        updateControls();
    }

    public void cbExact_actionPerformed(ActionEvent e) {
        isExact = cbExact.isSelected();
        if (!isExact) {
            radioButton1.setSelected(false);
            tbCustom.setEnabled(true);
            setEditText();
            tbCustom.requestFocus();
        }
    }

    public void bOK_actionPerformed(ActionEvent e) {
        try {
            if (isDateTime) {
                if (radioButton1.isSelected()) {
                    dIncrement=Utils.getDateTimeStep(cbSteps.getSelectedIndex());
                } else {
                    int i = tbCustom.getText().indexOf(" ");
                    if (i == 0) {
                        try {
                            try {
                                dIncrement = (Double.parseDouble(tbCustom.
                                        getText()));
                            } catch (Exception eInc) {
// TODO                                dIncrement=(Double.parseDouble(Convert.ToDateTime(eCustom.Text).TimeOfDay);
                            }
                        } catch (Exception eText) {
                            dIncrement = Double.parseDouble(tbCustom.getText());
                        }
                    } else {
// TODO                  dIncrement=Double.parseDouble(tbCustom.getText().substring(1,i-1))+
// TODO                  Double.parseDouble(new DateTime(tbCustom.getText().substring(i+1,255)).getTimeOfDay());
                    }
                }
            } else {
                dIncrement = Double.parseDouble(tbCustom.getText());
            }

        } catch (Exception eDateTime) {
            //
        }
        dialogResult=OK;
        dispose();
    }

    public int showModal() {
        setVisible(true);
        return dialogResult;
    }

    public void radioButton2_actionPerformed(ActionEvent e) {
        updateControls();
    }
}

final class AxisIncrement_bOK_actionAdapter implements ActionListener {
    private AxisIncrement adaptee;
    AxisIncrement_bOK_actionAdapter(AxisIncrement adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.bOK_actionPerformed(e);
    }
}


final class AxisIncrement_cbExact_actionAdapter implements ActionListener {
    private AxisIncrement adaptee;
    AxisIncrement_cbExact_actionAdapter(AxisIncrement adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.cbExact_actionPerformed(e);
    }
}


final class AxisIncrement_radioButton1_actionAdapter implements ActionListener {
    private AxisIncrement adaptee;
    AxisIncrement_radioButton1_actionAdapter(AxisIncrement adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.radioButton1_actionPerformed(e);
    }
}


class AxisIncrement_radioButton2_actionAdapter implements ActionListener {
    private AxisIncrement adaptee;
    AxisIncrement_radioButton2_actionAdapter(AxisIncrement adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.radioButton2_actionPerformed(e);
    }
}


final class AxisIncrement_bCancel_actionAdapter implements ActionListener {
    private AxisIncrement adaptee;
    AxisIncrement_bCancel_actionAdapter(AxisIncrement adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.bCancel_actionPerformed(e);
    }
}

