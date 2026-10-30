package com.steema.teechart.editors.tools;

import java.awt.Rectangle;
import java.awt.event.*;
import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;

import com.steema.teechart.Cursor;
import com.steema.teechart.editors.tools.ToolSeriesEditor;
import com.steema.teechart.tools.Tool;
import com.steema.teechart.tools.DragPoint;
import com.steema.teechart.tools.DragPointStyle;
import com.steema.teechart.editors.Utils;
import com.steema.teechart.languages.Language;

/**
 * <p>Title: DragPointEditor class</p>
 *
 * <p>Description: DragPoint Editor panel</p>
 *
 * <p>Copyright (c) 2004-2019 by Steema Software SL. All Rights Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 *
 */
public class DragPointEditor extends ToolSeriesEditor {


    public DragPointEditor() {
        try {
            jbInit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void setTool(Tool tool) {
        dragPoint=(DragPoint)tool;
        super.setTool(dragPoint);

        if (dragPoint.getStyle()==DragPointStyle.X)
          radioButton1.setSelected(true);
        else
          if (dragPoint.getStyle()==DragPointStyle.Y)
          radioButton2.setSelected(true);
        else
          radioButton3.setSelected(true);

        Utils.addCursorsToCombo(cbCursor);
        cbButton.setSelectedIndex(dragPoint.getButton() - 1);
    }

    private void jbInit() throws Exception {
        this.setLayout(null);
        cbButton.addItem(Language.getString("Left"));
        cbButton.addItem(Language.getString("Middle1"));
        cbButton.addItem(Language.getString("Right"));
        cbButton.addItem(Language.getString("XButton_11"));
        cbButton.addItem(Language.getString("XButton_21"));
        jPanel1.setBorder(border4);
        jPanel1.setBounds(new Rectangle(64, 46, 105, 94));
        jPanel1.setLayout(null);
        radioButton1.setMnemonic(Language.getString("X").charAt(0));
        radioButton1.setText(Language.getString("X"));
        radioButton1.setBounds(new Rectangle(9, 13, 91, 23));
        radioButton1.addActionListener(new
                DragPointEditor_radioButton1_actionAdapter(this));
        radioButton2.setMnemonic(Language.getString("Y").charAt(0));
        radioButton2.setText(Language.getString("Y"));
        radioButton2.setBounds(new Rectangle(9, 38, 91, 23));
        radioButton2.addActionListener(new
                DragPointEditor_radioButton2_actionAdapter(this));
        radioButton3.setMnemonic(Language.getString("B").charAt(0));
        radioButton3.setText(Language.getString("Both2"));
        radioButton3.setBounds(new Rectangle(9, 64, 91, 23));
        radioButton3.addActionListener(new
                DragPointEditor_radioButton3_actionAdapter(this));
        jLabel1.setDisplayedMnemonic(Language.getString("B").charAt(0));
        jLabel1.setHorizontalAlignment(SwingConstants.RIGHT);
        jLabel1.setLabelFor(cbButton);
        jLabel1.setText(Language.getString("Mouse_Button_1"));
        jLabel1.setBounds(new Rectangle(2, 163, 83, 15));
        jLabel2.setDisplayedMnemonic(Language.getString("C").charAt(0));
        jLabel2.setHorizontalAlignment(SwingConstants.RIGHT);
        jLabel2.setLabelFor(cbCursor);
        jLabel2.setText(Language.getString("Cursor_3"));
        jLabel2.setBounds(new Rectangle(17, 193, 62, 15));
        cbButton.setBounds(new Rectangle(93, 159, 82, 23));
        cbButton.addActionListener(new DragPointEditor_cbButton_actionAdapter(this));
        cbCursor.setBounds(new Rectangle(93, 189, 82, 23));
        cbCursor.addActionListener(new DragPointEditor_cbCursor_actionAdapter(this));
        this.add(cbCursor);
        this.add(jPanel1);

        ButtonGroup group = new ButtonGroup();
        group.add(radioButton1);
        group.add(radioButton2);
        group.add(radioButton3);

        jPanel1.add(radioButton1);
        jPanel1.add(radioButton2);
        jPanel1.add(radioButton3);
        this.add(cbButton);
        this.add(jLabel2);
        this.add(jLabel1);


    }

    DragPoint dragPoint;
    JPanel jPanel1 = new JPanel();
    Border border1 = BorderFactory.createBevelBorder(BevelBorder.LOWERED,
            Color.white, Color.white, new Color(115, 114, 105),
            new Color(165, 163, 151));
    Border border2 = new TitledBorder(border1, Language.getString("Styles_"));
    Border border3 = BorderFactory.createEtchedBorder(Color.white,
            new Color(165, 163, 151));
    Border border4 = new TitledBorder(border3, Language.getString("Style"));
    JRadioButton radioButton1 = new JRadioButton();
    JRadioButton radioButton2 = new JRadioButton();
    JRadioButton radioButton3 = new JRadioButton();
    JLabel jLabel1 = new JLabel();
    JLabel jLabel2 = new JLabel();
    JComboBox cbButton = new JComboBox();
    JComboBox cbCursor = new JComboBox();

    public void radioButton1_actionPerformed(ActionEvent e) {
        dragPoint.setStyle(DragPointStyle.X);
    }

    public void radioButton2_actionPerformed(ActionEvent e) {
        dragPoint.setStyle(DragPointStyle.Y);
    }

    public void radioButton3_actionPerformed(ActionEvent e) {
        dragPoint.setStyle(DragPointStyle.BOTH);
    }

    public void cbCursor_actionPerformed(ActionEvent e) {
        dragPoint.setCursor(new Cursor(cbCursor.getSelectedIndex()));
    }

    public void cbButton_actionPerformed(ActionEvent e) {
        dragPoint.setButton(cbButton.getSelectedIndex() + 1);
    }
}


class DragPointEditor_cbButton_actionAdapter implements ActionListener {
    private DragPointEditor adaptee;
    DragPointEditor_cbButton_actionAdapter(DragPointEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.cbButton_actionPerformed(e);
    }
}


class DragPointEditor_cbCursor_actionAdapter implements ActionListener {
    private DragPointEditor adaptee;
    DragPointEditor_cbCursor_actionAdapter(DragPointEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.cbCursor_actionPerformed(e);
    }
}


class DragPointEditor_radioButton3_actionAdapter implements ActionListener {
    private DragPointEditor adaptee;
    DragPointEditor_radioButton3_actionAdapter(DragPointEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.radioButton3_actionPerformed(e);
    }
}


class DragPointEditor_radioButton2_actionAdapter implements ActionListener {
    private DragPointEditor adaptee;
    DragPointEditor_radioButton2_actionAdapter(DragPointEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.radioButton2_actionPerformed(e);
    }
}


class DragPointEditor_radioButton1_actionAdapter implements ActionListener {
    private DragPointEditor adaptee;
    DragPointEditor_radioButton1_actionAdapter(DragPointEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.radioButton1_actionPerformed(e);
    }
}


