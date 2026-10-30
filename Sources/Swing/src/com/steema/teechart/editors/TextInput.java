package com.steema.teechart.editors;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Rectangle;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import com.steema.teechart.languages.Language;

/**
 *
 * <p>Title:  TextInput class</p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright (c) 2005-2019 by Steema Software SL. All Rights
 * Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 *
 */
final public class TextInput extends JDialog {


    /** A return status code - returned if Cancel button has been pressed */
    public static final int RET_CANCEL = 0;
    /** A return status code - returned if OK button has been pressed */
    public static final int RET_OK = 1;

    private int returnStatus = RET_CANCEL;

    public TextInput() {
        try {
            jbInit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void jbInit() throws Exception {
        jLabel1.setText(Language.getString("jLabel1"));
        jLabel1.setBounds(new Rectangle(0, 0, 327, 20));
        jTextField1.setPreferredSize(new Dimension(57, 11));
        jTextField1.setText(Language.getString("jTextField1"));
        jTextField1.setBounds(new Rectangle( -1, 20, 327, 38));
        this.setModal(true);
        this.setTitle("");
        this.getContentPane().setLayout(null);
        jButton1.addActionListener(new TextInput_jButton1_actionAdapter(this));
        this.getContentPane().add(jLabel1, null);
        jButton2.setBounds(new Rectangle(239, 61, 74, 25));
        jButton2.setText(Language.getString("Cancel"));
        jButton2.addActionListener(new TextInput_jButton2_actionAdapter(this));
        jButton1.setBounds(new Rectangle(153, 61, 74, 25));
        jButton1.setText(Language.getString("OK"));
        this.getContentPane().add(jTextField1, null);
        this.getContentPane().add(jButton1);
        this.getContentPane().add(jButton2);
        setSize(new Dimension(327, 124));
        setLocationRelativeTo(getParent());
    }

    JLabel jLabel1 = new JLabel();
    JTextField jTextField1 = new JTextField();
    JButton jButton1 = new JButton();
    JButton jButton2 = new JButton();

    static public boolean query(String title, String text, TextInputString value) {

      TextInput input=new TextInput();
        input.jLabel1.setText(text);
        input.setTitle(title);
        input.jTextField1.setText(value.text);
        input.setVisible(true);
        input.setModal(true);

        if (input.returnStatus==RET_OK) {
            value.text = input.jTextField1.getText();
        }

        return input.returnStatus==RET_OK;
    }

    public void jButton2_actionPerformed(ActionEvent e) {
        doClose(RET_CANCEL);
    }

    public void jButton1_actionPerformed(ActionEvent e) {
        doClose(RET_OK);
    }

    private void doClose(int retStatus) {
        returnStatus = retStatus;
        setVisible(false);
        dispose();
    }

}


class TextInput_jButton1_actionAdapter implements ActionListener {
    private TextInput adaptee;
    TextInput_jButton1_actionAdapter(TextInput adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.jButton1_actionPerformed(e);
    }
}


class TextInput_jButton2_actionAdapter implements ActionListener {
    private TextInput adaptee;
    TextInput_jButton2_actionAdapter(TextInput adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.jButton2_actionPerformed(e);
    }
}

