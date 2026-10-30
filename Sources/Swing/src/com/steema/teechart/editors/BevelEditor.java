package com.steema.teechart.editors;

import java.awt.Rectangle;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.event.*;

import com.steema.teechart.*;
import com.steema.teechart.languages.Language;

/**
 * <p>Title: TeeChart for Java</p>
 *
 * <p>Description: Bevel editor panel</p>
 *
 * <p>Copyright (c) 2004-2019 by Steema Software SL. All Rights Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 *
 */
public class BevelEditor extends JPanel {

    private Bevel bevel;
    JLabel jLabel1 = new JLabel();
    JComboBox cbOuter = new JComboBox();
    ButtonColor bOuter = new ButtonColor();
    JLabel jLabel2 = new JLabel();
    JComboBox cbInner = new JComboBox();
    ButtonColor bInner = new ButtonColor();
    JLabel jLabel3 = new JLabel();
    JSpinner spSize = new JSpinner();

    public BevelEditor() {
        try {
            jbInit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        cbOuter.addItem(Language.getString("None"));
        cbOuter.addItem(Language.getString("Lowered"));
        cbOuter.addItem(Language.getString("Raised"));

        cbInner.addItem(Language.getString("None"));
        cbInner.addItem(Language.getString("Lowered"));
        cbInner.addItem(Language.getString("Raised"));
    }

    public BevelEditor(Bevel bevel) {
        this();
        refreshControls(bevel);
    }

    public void refreshControls(Bevel bevel) {
        this.bevel=bevel;
        bOuter.setColor(bevel.getColorOne());
        bInner.setColor(bevel.getColorTwo());
        spSize.setValue(new Integer(bevel.getWidth()));
        cbOuter.setSelectedIndex(bevel.getOuter().getValue());
        cbInner.setSelectedIndex(bevel.getInner().getValue());
    }

    private void jbInit() throws Exception {
        jLabel1.setDisplayedMnemonic(Language.getString("O").charAt(0));
        jLabel1.setLabelFor(cbOuter);
        jLabel1.setText(Language.getString("Bevel_outer"));
        jLabel1.setBounds(new Rectangle(9, 7, 93, 14));
        this.setLayout(null);
        cbOuter.setBounds(new Rectangle(9, 26, 80, 22));
        cbOuter.addActionListener(new BevelEditor_cbOuter_actionAdapter(this));
        bOuter.setBounds(new Rectangle(9, 57, 80, 22));
        bOuter.setMnemonic(Language.getString("C").charAt(0));
        bOuter.setText(Language.getString("Color"));
        bOuter.addActionListener(new BevelEditor_bOuter_actionAdapter(this));
        jLabel2.setDisplayedMnemonic(Language.getString("I").charAt(0));
        jLabel2.setLabelFor(cbInner);
        jLabel2.setText(Language.getString("Bevel_inner"));
        jLabel2.setBounds(new Rectangle(106, 7, 102, 14));
        cbInner.setBounds(new Rectangle(106, 26, 80, 22));
        cbInner.addActionListener(new BevelEditor_cbInner_actionAdapter(this));
        bInner.setBounds(new Rectangle(106, 57, 80, 22));
        bInner.setMnemonic(Language.getString("R").charAt(0));
        bInner.setText(Language.getString("Color"));
        bInner.addActionListener(new BevelEditor_bInner_actionAdapter(this));
        jLabel3.setDisplayedMnemonic(Language.getString("E").charAt(0));
        jLabel3.setHorizontalAlignment(SwingConstants.TRAILING);
        jLabel3.setText(Language.getString("Size"));
        jLabel3.setBounds(new Rectangle(12, 96, 65, 14));
        spSize.setBounds(new Rectangle(84, 92, 45, 22));
        spSize.addChangeListener(new BevelEditor_spSize_changeAdapter(this));
        this.add(jLabel1);
        this.add(cbOuter);
        this.add(bOuter);
        this.add(bInner);
        this.add(jLabel2);
        this.add(cbInner);
        this.add(spSize);
        this.add(jLabel3);
    }

    public void bOuter_actionPerformed(ActionEvent e) {
        bevel.setColorOne(bOuter.getColor());
    }

    public void bInner_actionPerformed(ActionEvent e) {
        bevel.setColorTwo(bInner.getColor());
    }

    public void cbOuter_actionPerformed(ActionEvent e) {
        if (bevel!=null) {
            bevel.setOuter(BevelStyle.fromValue(cbOuter.getSelectedIndex()));
        }
    }

    public void cbInner_actionPerformed(ActionEvent e) {
        if (bevel!=null) {
            bevel.setInner(BevelStyle.fromValue(cbInner.getSelectedIndex()));
        }
    }

    public void spSize_stateChanged(ChangeEvent e) {
        bevel.setWidth(((Integer)spSize.getValue()).intValue());
    }

    public void doEnableControls(boolean value) {
        bInner.setEnabled(value);
        bOuter.setEnabled(value);
        cbOuter.setEnabled(value);
        cbInner.setEnabled(value);
        spSize.setEnabled(value);
        jLabel1.setEnabled(value);
        jLabel2.setEnabled(value);
        jLabel3.setEnabled(value);
    }
}


class BevelEditor_spSize_changeAdapter implements ChangeListener {
    private BevelEditor adaptee;
    BevelEditor_spSize_changeAdapter(BevelEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void stateChanged(ChangeEvent e) {
        adaptee.spSize_stateChanged(e);
    }
}


class BevelEditor_cbInner_actionAdapter implements ActionListener {
    private BevelEditor adaptee;
    BevelEditor_cbInner_actionAdapter(BevelEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.cbInner_actionPerformed(e);
    }
}


class BevelEditor_cbOuter_actionAdapter implements ActionListener {
    private BevelEditor adaptee;
    BevelEditor_cbOuter_actionAdapter(BevelEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.cbOuter_actionPerformed(e);
    }
}


class BevelEditor_bInner_actionAdapter implements ActionListener {
    private BevelEditor adaptee;
    BevelEditor_bInner_actionAdapter(BevelEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.bInner_actionPerformed(e);
    }
}


class BevelEditor_bOuter_actionAdapter implements ActionListener {
    private BevelEditor adaptee;
    BevelEditor_bOuter_actionAdapter(BevelEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.bOuter_actionPerformed(e);
    }
}

