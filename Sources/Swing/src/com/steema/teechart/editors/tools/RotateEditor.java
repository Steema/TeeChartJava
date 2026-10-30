package com.steema.teechart.editors.tools;

import java.awt.Rectangle;
import java.awt.event.*;
import javax.swing.*;

//import com.steema.teechart.*;
import com.steema.teechart.editors.ButtonPen;
import com.steema.teechart.tools.Rotate;
import com.steema.teechart.tools.RotateStyle;
import com.steema.teechart.tools.Tool;
import com.steema.teechart.editors.BaseToolsEditor;
import com.steema.teechart.languages.Language;

/**
 * <p>Title: RotateEditor class</p>
 *
 * <p>Description: Rotate Editor panel</p>
 *
 * <p>Copyright (c) 2004-2019 by Steema Software SL. All Rights Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 *
 */
public class RotateEditor extends BaseToolsEditor {


    public RotateEditor() {
        try {
            jbInit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void setTool(Tool tool) {
        rotate=(Rotate)tool;

        cbInverted.setSelected(rotate.getInverted());

        switch (rotate.getStyle().getValue())
        {
           case 0: cbStyle.setSelectedIndex(0); break;
           case 1: cbStyle.setSelectedIndex(1); break;
           default: cbStyle.setSelectedIndex(2); break;
        }

        cbMouse.setSelectedIndex(rotate.getButton() - 1);
        bOutline.setPen(rotate.getPen());
    }

    private void jbInit() throws Exception {
        this.setLayout(null);
        cbStyle.addItem(Language.getString("Both"));
        cbStyle.addItem(Language.getString("Rotation"));
        cbStyle.addItem(Language.getString("Elevation"));

        cbMouse.addItem(Language.getString("Left"));
        cbMouse.addItem(Language.getString("Middle"));
        cbMouse.addItem(Language.getString("Right"));
        cbMouse.addItem(Language.getString("XButton_1"));
        cbMouse.addItem(Language.getString("XButton_2"));

        cbInverted.setMnemonic(Language.getString("I").charAt(0));
        cbInverted.setText(Language.getString("Inverted"));
        cbInverted.setBounds(new Rectangle(54, 10, 83, 23));
        cbInverted.addActionListener(new RotateEditor_cbInverted_actionAdapter(this));
        jLabel1.setDisplayedMnemonic(Language.getString("M").charAt(0));
        jLabel1.setHorizontalAlignment(SwingConstants.LEFT);
        jLabel1.setLabelFor(cbMouse);
        jLabel1.setText(Language.getString("Mouse_Button"));
        jLabel1.setBounds(new Rectangle(54, 72, 83, 15));
        cbStyle.setBounds(new Rectangle(54, 40, 125, 23));
        cbStyle.addActionListener(new RotateEditor_cbStyle_actionAdapter(this));
        cbMouse.setBounds(new Rectangle(55, 91, 111, 23));
        cbMouse.addActionListener(new RotateEditor_cbMouse_actionAdapter(this));
        jLabel2.setDisplayedMnemonic(Language.getString("S").charAt(0));
        jLabel2.setHorizontalAlignment(SwingConstants.RIGHT);
        jLabel2.setLabelFor(cbStyle);
        jLabel2.setText(Language.getString("Style"));
        jLabel2.setBounds(new Rectangle(11, 44, 34, 15));
        bOutline.setBounds(new Rectangle(55, 124, 77, 23));
        bOutline.setMnemonic(Language.getString("O").charAt(0));
        bOutline.setText(Language.getString("Outline"));
        this.add(bOutline);
        this.add(jLabel2);
        this.add(cbStyle);
        this.add(cbInverted);
        this.add(cbMouse);
        this.add(jLabel1);
    }

    Rotate rotate;
    JCheckBox cbInverted = new JCheckBox();
    JLabel jLabel1 = new JLabel();
    JComboBox cbStyle = new JComboBox();
    JComboBox cbMouse = new JComboBox();
    JLabel jLabel2 = new JLabel();
    ButtonPen bOutline = new ButtonPen();

    public void cbInverted_actionPerformed(ActionEvent e) {
        rotate.setInverted(cbInverted.isSelected());
    }

    public void cbStyle_actionPerformed(ActionEvent e) {
        switch (cbStyle.getSelectedIndex()) {
        case 0:
            rotate.setStyle(RotateStyle.ALL);
            break;
        case 1:
            rotate.setStyle(RotateStyle.ROTATION);
            break;
        default:
            rotate.setStyle(RotateStyle.ELEVATION);
            break;
        }
    }

    public void cbMouse_actionPerformed(ActionEvent e) {
        rotate.setButton(cbMouse.getSelectedIndex() + 1);
    }
}


class RotateEditor_cbMouse_actionAdapter implements ActionListener {
    private RotateEditor adaptee;
    RotateEditor_cbMouse_actionAdapter(RotateEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.cbMouse_actionPerformed(e);
    }
}


class RotateEditor_cbStyle_actionAdapter implements ActionListener {
    private RotateEditor adaptee;
    RotateEditor_cbStyle_actionAdapter(RotateEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.cbStyle_actionPerformed(e);
    }
}


class RotateEditor_cbInverted_actionAdapter implements ActionListener {
    private RotateEditor adaptee;
    RotateEditor_cbInverted_actionAdapter(RotateEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.cbInverted_actionPerformed(e);
    }
}


