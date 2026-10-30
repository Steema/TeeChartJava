package com.steema.teechart.editors.series;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import java.awt.Rectangle;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.steema.teechart.styles.CustomBar;
import com.steema.teechart.styles.MultiBars;
import com.steema.teechart.languages.Language;

/**
 * <p>Title: TeeChart for Java</p>
 *
 * <p>Description: TeeChart for Java</p>
 *
 * <p>Copyright (c) 2004-2019 by Steema Software SL. All Rights Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 *
 */
public class StackBarEditor extends JPanel {

    private CustomBar bar;
    JPanel jPanel1 = new JPanel();
    Border border1 = BorderFactory.createEtchedBorder(EtchedBorder.RAISED,
            Color.white, new Color(148, 145, 140));
    Border border2 = new TitledBorder(border1, Language.getString("Multiple_Bar"));
    JRadioButton rbNone = new JRadioButton();
    JRadioButton rbSide = new JRadioButton();
    JRadioButton rbStacked = new JRadioButton();
    JRadioButton rbStacked100 = new JRadioButton();
    JRadioButton rbSideAll = new JRadioButton();
    JRadioButton rbSelfStack = new JRadioButton();
    JCheckBox cbUseOrigin = new JCheckBox();
    JTextField eOrigin = new JTextField();
    JLabel jLabel1 = new JLabel();
    JSpinner spStackGroup = new JSpinner();

    public StackBarEditor() {
        try {
            jbInit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        ButtonGroup group=new ButtonGroup();
        group.add(rbNone);
        group.add(rbSide);
        group.add(rbStacked);
        group.add(rbStacked100);
        group.add(rbSideAll);
        group.add(rbSelfStack);
    }

    public StackBarEditor(CustomBar bar) {
        this();
        this.bar=bar;

        MultiBars tmp=bar.getMultiBar();

        rbNone.setSelected(tmp==MultiBars.NONE);
        rbSide.setSelected(tmp==MultiBars.SIDE);
        rbStacked.setSelected(tmp==MultiBars.STACKED);
        rbStacked100.setSelected(tmp==MultiBars.STACKED100);
        rbSideAll.setSelected(tmp==MultiBars.SIDEALL);
        rbSelfStack.setSelected(tmp==MultiBars.SELFSTACK);

        cbUseOrigin.setSelected(bar.getUseOrigin());
        eOrigin.setText(Double.toString(bar.getOrigin()));

        spStackGroup.setValue(new Integer(bar.getStackGroup()));

        spStackGroup.setEnabled((tmp==MultiBars.STACKED) || (tmp==MultiBars.STACKED100));
    }

    private void jbInit() throws Exception {
        this.setLayout(null);
        jPanel1.setBorder(border2);
        jPanel1.setBounds(new Rectangle(8, 10, 140, 151));
        jPanel1.setLayout(null);
        rbNone.setText(Language.getString("None"));
        rbNone.setBounds(new Rectangle(7, 17, 93, 23));
        rbNone.addActionListener(new StackBarEditor_rbNone_actionAdapter(this));
        rbSide.setText(Language.getString("Side"));
        rbSide.setBounds(new Rectangle(7, 36, 93, 23));
        rbSide.addActionListener(new StackBarEditor_rbSide_actionAdapter(this));
        rbStacked.setText(Language.getString("Stacked"));
        rbStacked.setBounds(new Rectangle(7, 56, 93, 23));
        rbStacked.addActionListener(new StackBarEditor_rbStacked_actionAdapter(this));
        rbStacked100.setText(Language.getString("Stacked"));
        rbStacked100.setBounds(new Rectangle(7, 77, 122, 23));
        rbStacked100.addActionListener(new
                StackBarEditor_rbStacked100_actionAdapter(this));
        rbSideAll.setText(Language.getString("Side_All"));
        rbSideAll.setBounds(new Rectangle(7, 98, 93, 23));
        rbSideAll.addActionListener(new StackBarEditor_rbSideAll_actionAdapter(this));
        rbSelfStack.setText(Language.getString("Self_Stack"));
        rbSelfStack.setBounds(new Rectangle(7, 119, 93, 23));
        rbSelfStack.addActionListener(new
                                      StackBarEditor_rbSelfStack_actionAdapter(this));
        cbUseOrigin.setMnemonic(Language.getString("O").charAt(0));
        cbUseOrigin.setSelected(true);
        cbUseOrigin.setText(Language.getString("Use_Origin"));
        cbUseOrigin.setBounds(new Rectangle(159, 17, 101, 23));
        cbUseOrigin.addActionListener(new
                                      StackBarEditor_cbUseOrigin_actionAdapter(this));
        eOrigin.setText("0");
        eOrigin.setBounds(new Rectangle(163, 43, 104, 20));
        eOrigin.addActionListener(new StackBarEditor_eOrigin_actionAdapter(this));
        jLabel1.setDisplayedMnemonic(Language.getString("S").charAt(0));
        jLabel1.setLabelFor(spStackGroup);
        jLabel1.setText(Language.getString("Stack_group"));
        jLabel1.setBounds(new Rectangle(163, 88, 84, 14));
        spStackGroup.setBounds(new Rectangle(163, 105, 67, 20));
        spStackGroup.setEnabled(false);
        spStackGroup.addChangeListener(new
                StackBarEditor_spStackGroup_changeAdapter(this));
        this.add(jPanel1);
        jPanel1.add(rbStacked);
        jPanel1.add(rbStacked100);
        jPanel1.add(rbSideAll);
        jPanel1.add(rbSelfStack);
        jPanel1.add(rbNone);
        jPanel1.add(rbSide);
        this.add(cbUseOrigin);
        this.add(eOrigin);
        this.add(jLabel1);
        this.add(spStackGroup);
    }

    public void rbNone_actionPerformed(ActionEvent e) {
        bar.setMultiBar(MultiBars.NONE);
        spStackGroup.setEnabled(false);
    }

    public void rbSide_actionPerformed(ActionEvent e) {
        bar.setMultiBar(MultiBars.SIDE);
        spStackGroup.setEnabled(false);
    }

    public void rbStacked_actionPerformed(ActionEvent e) {
        bar.setMultiBar(MultiBars.STACKED);
        spStackGroup.setEnabled(true);
    }

    public void rbStacked100_actionPerformed(ActionEvent e) {
        bar.setMultiBar(MultiBars.STACKED100);
        spStackGroup.setEnabled(true);
    }

    public void rbSideAll_actionPerformed(ActionEvent e) {
        bar.setMultiBar(MultiBars.SIDEALL);
        spStackGroup.setEnabled(false);
    }

    public void rbSelfStack_actionPerformed(ActionEvent e) {
        bar.setMultiBar(MultiBars.SELFSTACK);
        spStackGroup.setEnabled(false);
    }

    public void cbUseOrigin_actionPerformed(ActionEvent e) {
        bar.setUseOrigin(cbUseOrigin.isSelected());
    }

    public void eOrigin_actionPerformed(ActionEvent e) {
        bar.setOrigin(Double.parseDouble(eOrigin.getText()));
    }

    public void spStackGroup_stateChanged(ChangeEvent e) {
        bar.setStackGroup(((Integer)spStackGroup.getValue()).intValue());
    }
}


class StackBarEditor_spStackGroup_changeAdapter implements ChangeListener {
    private StackBarEditor adaptee;
    StackBarEditor_spStackGroup_changeAdapter(StackBarEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void stateChanged(ChangeEvent e) {
        adaptee.spStackGroup_stateChanged(e);
    }
}


class StackBarEditor_eOrigin_actionAdapter implements ActionListener {
    private StackBarEditor adaptee;
    StackBarEditor_eOrigin_actionAdapter(StackBarEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.eOrigin_actionPerformed(e);
    }
}


class StackBarEditor_cbUseOrigin_actionAdapter implements ActionListener {
    private StackBarEditor adaptee;
    StackBarEditor_cbUseOrigin_actionAdapter(StackBarEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.cbUseOrigin_actionPerformed(e);
    }
}


class StackBarEditor_rbSelfStack_actionAdapter implements ActionListener {
    private StackBarEditor adaptee;
    StackBarEditor_rbSelfStack_actionAdapter(StackBarEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.rbSelfStack_actionPerformed(e);
    }
}


class StackBarEditor_rbSideAll_actionAdapter implements ActionListener {
    private StackBarEditor adaptee;
    StackBarEditor_rbSideAll_actionAdapter(StackBarEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.rbSideAll_actionPerformed(e);
    }
}


class StackBarEditor_rbStacked100_actionAdapter implements ActionListener {
    private StackBarEditor adaptee;
    StackBarEditor_rbStacked100_actionAdapter(StackBarEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.rbStacked100_actionPerformed(e);
    }
}


class StackBarEditor_rbStacked_actionAdapter implements ActionListener {
    private StackBarEditor adaptee;
    StackBarEditor_rbStacked_actionAdapter(StackBarEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.rbStacked_actionPerformed(e);
    }
}


class StackBarEditor_rbSide_actionAdapter implements ActionListener {
    private StackBarEditor adaptee;
    StackBarEditor_rbSide_actionAdapter(StackBarEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.rbSide_actionPerformed(e);
    }
}


class StackBarEditor_rbNone_actionAdapter implements ActionListener {
    private StackBarEditor adaptee;
    StackBarEditor_rbNone_actionAdapter(StackBarEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.rbNone_actionPerformed(e);
    }
}

