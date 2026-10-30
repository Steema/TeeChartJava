package com.steema.teechart.editors;

import java.awt.*;
import java.awt.Rectangle;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.*;
import com.steema.teechart.Shadow;

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
public class ShadowEditor extends JPanel {

    private Shadow shadow;
    ButtonColor buttonColor1 = new ButtonColor();
    JPanel jPanel1 = new JPanel();
    Border border1 = BorderFactory.createEtchedBorder(Color.white,
            new Color(148, 145, 140));
    Border border2 = new TitledBorder(border1, Language.getString("Size"));
    JLabel jLabel1 = new JLabel();
    JLabel jLabel2 = new JLabel();
    JSpinner spHoriz = new JSpinner();
    JSpinner spVert = new JSpinner();
    JCheckBox cbVisible = new JCheckBox();
    JButton jButton1 = new JButton();

    public ShadowEditor() {
        try {
            jbInit();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public ShadowEditor(Shadow shadow) {
        this();
        refreshControls(shadow);
    }

    public static void showModal(Shadow shadow) {
        ShadowEditor shadowEdit=new ShadowEditor(shadow);

        BaseEditorDialog.showModal(shadowEdit);
    }

    private void jbInit() throws Exception {
        setLayout(null);
        buttonColor1.setBounds(new Rectangle(10, 33, 75, 23));
        buttonColor1.setMnemonic(Language.getString("C").charAt(0));
        buttonColor1.setText(Language.getString("Color"));
        buttonColor1.addActionListener(new
                                       ShadowEditor_buttonColor1_actionAdapter(this));
        jPanel1.setBorder(border2);
        jPanel1.setBounds(new Rectangle(8, 59, 146, 73));
        jPanel1.setLayout(null);
        jLabel1.setDisplayedMnemonic(Language.getString("H").charAt(0));
        jLabel1.setHorizontalAlignment(SwingConstants.TRAILING);
        jLabel1.setLabelFor(spHoriz);
        jLabel1.setText(Language.getString("Horizontal"));
        jLabel1.setBounds(new Rectangle(9, 20, 62, 14));
        jLabel2.setDisplayedMnemonic(Language.getString("V").charAt(0));
        jLabel2.setHorizontalAlignment(SwingConstants.TRAILING);
        jLabel2.setLabelFor(spVert);
        jLabel2.setText(Language.getString("Vertical"));
        jLabel2.setBounds(new Rectangle(9, 46, 62, 14));
        spHoriz.setBounds(new Rectangle(82, 15, 51, 22));
        spHoriz.addChangeListener(new ShadowEditor_spHoriz_changeAdapter(this));
        spVert.setBounds(new Rectangle(82, 43, 50, 22));
        spVert.addChangeListener(new ShadowEditor_spVert_changeAdapter(this));
        cbVisible.setMnemonic(Language.getString("V").charAt(0));
        cbVisible.setText(Language.getString("Visible"));
        cbVisible.setBounds(new Rectangle(9, 5, 99, 23));
        cbVisible.addActionListener(new ShadowEditor_cbVisible_actionAdapter(this));
        jButton1.setBounds(new Rectangle(99, 33, 68, 22));
        jButton1.setMnemonic(Language.getString("F").charAt(0));
        jButton1.setText(Language.getString("Fill"));
        jButton1.addActionListener(new ShadowEditor_jButton1_actionAdapter(this));
        jPanel1.add(jLabel1);
        jPanel1.add(spHoriz);
        jPanel1.add(jLabel2);
        jPanel1.add(spVert);
        this.add(cbVisible);
        this.add(buttonColor1);
        this.add(jPanel1);
        this.add(jButton1);
        setPreferredSize(new Dimension(180,140));
    }

    public void cbVisible_actionPerformed(ActionEvent e) {
        shadow.setVisible(cbVisible.isSelected());
    }

    public void spHoriz_stateChanged(ChangeEvent e) {
        shadow.setWidth(((Integer)spHoriz.getValue()).intValue());
    }

    public void spVert_stateChanged(ChangeEvent e) {
        shadow.setHeight(((Integer)spVert.getValue()).intValue());
    }

    public void buttonColor1_actionPerformed(ActionEvent e) {
        shadow.setColor(buttonColor1.getColor());
    }

    void refreshControls(Shadow shadow) {
        this.shadow=shadow;
        buttonColor1.setColor(shadow.getColor());
        cbVisible.setSelected(shadow.getVisible());
        spHoriz.setValue(new Integer(shadow.getWidth()));
        spVert.setValue(new Integer(shadow.getHeight()));
    }

    void jButton1_actionPerformed(ActionEvent e) {
        BrushEditor.edit(shadow.getBrush());
    }
}


final class ShadowEditor_jButton1_actionAdapter implements ActionListener {
    private ShadowEditor adaptee;
    ShadowEditor_jButton1_actionAdapter(ShadowEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.jButton1_actionPerformed(e);
    }
}


final class ShadowEditor_buttonColor1_actionAdapter implements ActionListener {
    private ShadowEditor adaptee;
    ShadowEditor_buttonColor1_actionAdapter(ShadowEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.buttonColor1_actionPerformed(e);
    }
}


final class ShadowEditor_spHoriz_changeAdapter implements ChangeListener {
    private ShadowEditor adaptee;
    ShadowEditor_spHoriz_changeAdapter(ShadowEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void stateChanged(ChangeEvent e) {
        adaptee.spHoriz_stateChanged(e);
    }
}


final class ShadowEditor_spVert_changeAdapter implements ChangeListener {
    private ShadowEditor adaptee;
    ShadowEditor_spVert_changeAdapter(ShadowEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void stateChanged(ChangeEvent e) {
        adaptee.spVert_stateChanged(e);
    }
}


final class ShadowEditor_cbVisible_actionAdapter implements ActionListener {
    private ShadowEditor adaptee;
    ShadowEditor_cbVisible_actionAdapter(ShadowEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.cbVisible_actionPerformed(e);
    }
}

