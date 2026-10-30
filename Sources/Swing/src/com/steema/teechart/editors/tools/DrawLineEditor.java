package com.steema.teechart.editors.tools;

import java.awt.Rectangle;
import java.awt.event.*;
import javax.swing.*;

import com.steema.teechart.editors.tools.ToolSeriesEditor;
import com.steema.teechart.editors.ButtonPen;
import com.steema.teechart.tools.Tool;
import com.steema.teechart.tools.DrawLine;
import com.steema.teechart.languages.Language;

/**
 * <p>Title: DrawLineEditor class</p>
 *
 * <p>Description: DrawLine Editor panel</p>
 *
 * <p>Copyright (c) 2004-2019 by Steema Software SL. All Rights Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 *
 */
public class DrawLineEditor extends ToolSeriesEditor {


    public DrawLineEditor() {
        try {
            jbInit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void setTool(Tool tool) {
        setting=true;
        drawLine=(DrawLine)tool;
        super.setTool(drawLine);
        cbButton.setSelectedIndex(drawLine.getButton() - 1);
        cbEnableDraw.setSelected(drawLine.getEnableDraw());
        cbEnableSelect.setSelected(drawLine.getEnableSelect());
        bRemoveAll.setEnabled(drawLine.getLines().size()>0);
        bPen.setPen(drawLine.getPen());
        setting=false;
    }

    private void jbInit() throws Exception {
        this.setLayout(null);
        cbButton.addItem(Language.getString("Left"));
        cbButton.addItem(Language.getString("Middle2"));
        cbButton.addItem(Language.getString("Right"));
        cbButton.addItem(Language.getString("XButton_12"));
        cbButton.addItem(Language.getString("XButton_22"));
        bPen.setBounds(new Rectangle(64, 49, 75, 23));
        bPen.setMnemonic(Language.getString("P").charAt(0));
        bPen.setText(Language.getString("Pen"));
        jLabel1.setDisplayedMnemonic(Language.getString("B").charAt(0));
        jLabel1.setHorizontalAlignment(SwingConstants.RIGHT);
        jLabel1.setLabelFor(cbButton);
        jLabel1.setText(Language.getString("Button_"));
        jLabel1.setBounds(new Rectangle(54, 88, 47, 15));
        cbButton.setBounds(new Rectangle(109, 84, 91, 23));
        cbButton.addActionListener(new DrawLineEditor_cbButton_actionAdapter(this));
        cbEnableDraw.setMnemonic(Language.getString("D").charAt(0));
        cbEnableDraw.setText(Language.getString("Enable_Draw"));
        cbEnableDraw.setBounds(new Rectangle(109, 116, 128, 23));
        cbEnableDraw.addActionListener(new
                DrawLineEditor_cbEnableDraw_actionAdapter(this));
        cbEnableSelect.setMnemonic(Language.getString("E").charAt(0));
        cbEnableSelect.setText(Language.getString("Enable_Select"));
        cbEnableSelect.setBounds(new Rectangle(109, 140, 128, 23));
        cbEnableSelect.addActionListener(new
                DrawLineEditor_cbEnableSelect_actionAdapter(this));
        bRemoveAll.setBounds(new Rectangle(112, 170, 93, 23));
        bRemoveAll.setMnemonic(Language.getString("A").charAt(0));
        bRemoveAll.setText(Language.getString("Remove_All"));
        bRemoveAll.addActionListener(new
                                     DrawLineEditor_bRemoveAll_actionAdapter(this));
        this.add(cbButton);
        this.add(cbEnableDraw);
        this.add(cbEnableSelect);
        this.add(bRemoveAll);
        this.add(bPen);
        this.add(jLabel1);
    }

    DrawLine drawLine;
    boolean setting;
    ButtonPen bPen = new ButtonPen();
    JLabel jLabel1 = new JLabel();
    JComboBox cbButton = new JComboBox();
    JCheckBox cbEnableDraw = new JCheckBox();
    JCheckBox cbEnableSelect = new JCheckBox();
    JButton bRemoveAll = new JButton();

    public void cbButton_actionPerformed(ActionEvent e) {
        if (!setting)
          drawLine.setButton(cbButton.getSelectedIndex() + 1);
    }

    public void cbEnableDraw_actionPerformed(ActionEvent e) {
        if (!setting)
          drawLine.setEnableDraw(cbEnableDraw.isSelected());
    }

    public void cbEnableSelect_actionPerformed(ActionEvent e) {
        if (!setting)
          drawLine.setEnableSelect(cbEnableSelect.isSelected());
    }

    public void bRemoveAll_actionPerformed(ActionEvent e) {
        drawLine.getLines().clear();;
        bRemoveAll.setEnabled(false);
    }
}


class DrawLineEditor_bRemoveAll_actionAdapter implements ActionListener {
    private DrawLineEditor adaptee;
    DrawLineEditor_bRemoveAll_actionAdapter(DrawLineEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.bRemoveAll_actionPerformed(e);
    }
}


class DrawLineEditor_cbEnableSelect_actionAdapter implements ActionListener {
    private DrawLineEditor adaptee;
    DrawLineEditor_cbEnableSelect_actionAdapter(DrawLineEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.cbEnableSelect_actionPerformed(e);
    }
}


class DrawLineEditor_cbEnableDraw_actionAdapter implements ActionListener {
    private DrawLineEditor adaptee;
    DrawLineEditor_cbEnableDraw_actionAdapter(DrawLineEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.cbEnableDraw_actionPerformed(e);
    }
}


class DrawLineEditor_cbButton_actionAdapter implements ActionListener {
    private DrawLineEditor adaptee;
    DrawLineEditor_cbButton_actionAdapter(DrawLineEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.cbButton_actionPerformed(e);
    }
}


