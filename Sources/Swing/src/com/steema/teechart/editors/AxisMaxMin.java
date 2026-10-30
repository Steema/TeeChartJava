package com.steema.teechart.editors;

import javax.swing.JLabel;
import javax.swing.JDialog;
import java.awt.Rectangle;
import javax.swing.JButton;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import com.steema.teechart.languages.Language;
import com.steema.teechart.DateTime;

import javax.swing.JTextField;
import javax.swing.JOptionPane;

/**
 * <p>Title: AxisMaxMin class</p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright (c) 2004-2019 by Steema Software SL. All Rights Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 *
 */

public class AxisMaxMin extends JDialog {

    public static final int OK = 0;
    public static final int CANCEL = 1;

    private int dialogResult = CANCEL;

    protected boolean isDateTime;
    protected double maxMin;
    JButton bOK = new JButton();
    JButton bCancel = new JButton();
    JLabel jLabel1 = new JLabel();
    JLabel jLabel2 = new JLabel();
    JTextField eMaximum = new JTextField();
    JTextField eMinimum = new JTextField();

    public AxisMaxMin() {
        try {
            jbInit();
            Load();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void Load() {

        DateTime tmpDT;

        if ( isDateTime ) {
            tmpDT=new DateTime(maxMin);
            if ( maxMin>=1 )
                eMaximum.setText(tmpDT.toShortDateString());
            else {
                jLabel1.setVisible(false);
                eMaximum.setVisible(false);
            }

            eMinimum.setText(DateTime.getToday().getTime().toString());
        } else {
                jLabel1.setText(Language.getString("AxisDlgValue"));
                eMaximum.setText(String.valueOf(maxMin));
                jLabel2.setVisible(false);
                eMinimum.setVisible(false);
        }
    }

    public int showModal() {
        setVisible(true);
        return dialogResult;
    }

    private void jbInit() throws Exception {
        this.getContentPane().setLayout(null);
        bOK.setBounds(new Rectangle(192, 13, 77, 25));
        bOK.setText(Language.getString("OK"));
        bOK.addActionListener(new AxisMaxMin_bOK_actionAdapter(this));
        bCancel.setBounds(new Rectangle(192, 40, 77, 25));
        bCancel.setText(Language.getString("Cancel"));
        bCancel.addActionListener(new AxisMaxMin_bCancel_actionAdapter(this));
        jLabel1.setDisplayedMnemonic('D');
        jLabel1.setLabelFor(eMaximum);
        jLabel1.setText(Language.getString("Date"));
        jLabel1.setBounds(new Rectangle(23, 18, 34, 15));
        jLabel2.setDisplayedMnemonic('T');
        jLabel2.setLabelFor(eMinimum);
        jLabel2.setText(Language.getString("Time"));
        jLabel2.setBounds(new Rectangle(23, 45, 34, 15));
        eMaximum.setBounds(new Rectangle(63, 15, 115, 20));
        eMinimum.setBounds(new Rectangle(63, 42, 115, 20));
        this.getContentPane().add(jLabel1);
        this.setTitle("");
        this.getContentPane().add(eMaximum);
        this.getContentPane().add(bOK);
        this.getContentPane().add(bCancel);
        this.getContentPane().add(eMinimum);
        this.getContentPane().add(jLabel2);
    }

    public void bOK_actionPerformed(ActionEvent e) {
        try {
            if ( isDateTime ) {
                /* TODO DateTime
                if ( eMaximum.isVisible() ) {
                   maxMin = DateTime.Parse(EMaximum.Text + ' ' + EMinimum.Text).
                             ToOADate();
                } else {
                    maxMin=DateTime.Parse(EMaximum.Text).ToOADate();
                }*/
            } else
                maxMin=com.steema.teechart.misc.Utils.stringToDouble(eMaximum.getText(),maxMin);
        } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null,
                              Language.getString("IncorrectMaxMinValue") + ":\n" +
                              ex.getLocalizedMessage());
        }
        dialogResult=OK;
        dispose();
    }

    public void bCancel_actionPerformed(ActionEvent e) {
        dialogResult=CANCEL;
        dispose();
    }
}


class AxisMaxMin_bCancel_actionAdapter implements ActionListener {
    private AxisMaxMin adaptee;
    AxisMaxMin_bCancel_actionAdapter(AxisMaxMin adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.bCancel_actionPerformed(e);
    }
}


class AxisMaxMin_bOK_actionAdapter implements ActionListener {
    private AxisMaxMin adaptee;
    AxisMaxMin_bOK_actionAdapter(AxisMaxMin adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.bOK_actionPerformed(e);
    }
}

