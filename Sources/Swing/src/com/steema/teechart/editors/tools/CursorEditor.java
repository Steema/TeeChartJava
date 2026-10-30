package com.steema.teechart.editors.tools;

import java.awt.Rectangle;
import java.awt.event.*;
import javax.swing.*;

import com.steema.teechart.editors.tools.ToolSeriesEditor;
import com.steema.teechart.editors.ButtonPen;
import com.steema.teechart.tools.CursorTool;
import com.steema.teechart.tools.CursorToolStyle;
import com.steema.teechart.tools.Tool;
import com.steema.teechart.languages.Language;

/**
 * <p>Title: CursorEditor class</p>
 *
 * <p>Description:  Cursor Editor panel</p>
 *
 * <p>Copyright (c) 2004-2019 by Steema Software SL. All Rights Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 *
 */
public class CursorEditor extends ToolSeriesEditor {


    public CursorEditor() {
        try {
            jbInit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void setTool(Tool tool) {
        setting=true;
        cursorTool=(CursorTool)tool;
        super.setTool(cursorTool);
        switch (cursorTool.getStyle().getValue())
        {
            case 0: cbStyle.setSelectedIndex(0); break;
            case 1: cbStyle.setSelectedIndex(1); break;
            default: cbStyle.setSelectedIndex(2); break;
        }
        cbSnap.setSelected(cursorTool.getSnap());
        cbFollow.setSelected(cursorTool.getFollowMouse());
        bPen.setPen(cursorTool.getPen());
        setting=false;
    }

    private void jbInit() throws Exception {

        cbStyle.addItem(Language.getString("Horizontal"));
        cbStyle.addItem(Language.getString("Vertical"));
        cbStyle.addItem(Language.getString("Both"));

        this.setLayout(null);
        jLabel1.setDisplayedMnemonic(Language.getString("T").charAt(0));
        jLabel1.setHorizontalAlignment(SwingConstants.RIGHT);
        jLabel1.setText(Language.getString("Style"));
        jLabel1.setBounds(new Rectangle(25, 55, 34, 15));
        cbStyle.setBounds(new Rectangle(65, 51, 89, 23));
        cbStyle.addActionListener(new CursorEditor_cbStyle_actionAdapter(this));
        cbSnap.setMnemonic(Language.getString("N").charAt(0));
        cbSnap.setText(Language.getString("Snap"));
        cbSnap.setBounds(new Rectangle(62, 82, 83, 23));
        cbSnap.addActionListener(new CursorEditor_cbSnap_actionAdapter(this));
        cbFollow.setMnemonic(Language.getString("F").charAt(0));
        cbFollow.setText(Language.getString("Follow_Mouse"));
        cbFollow.setBounds(new Rectangle(62, 108, 127, 23));
        cbFollow.addActionListener(new CursorEditor_cbFollow_actionAdapter(this));
        bPen.setBounds(new Rectangle(65, 139, 76, 23));
        bPen.setMnemonic(Language.getString("P").charAt(0));
        bPen.setText(Language.getString("Pen"));
        this.add(cbStyle);
        this.add(cbSnap);
        this.add(cbFollow);
        this.add(jLabel1);
        this.add(bPen);
    }

    boolean setting;
    CursorTool cursorTool;
    JLabel jLabel1 = new JLabel();
    JComboBox cbStyle = new JComboBox();
    JCheckBox cbSnap = new JCheckBox();
    JCheckBox cbFollow = new JCheckBox();
    ButtonPen bPen = new ButtonPen();

    public void cbStyle_actionPerformed(ActionEvent e) {
        if (!setting)
        {
            if (cbStyle.getSelectedIndex()==0)
                cursorTool.setStyle(CursorToolStyle.HORIZONTAL);
            else
                if (cbStyle.getSelectedIndex()==1)
                    cursorTool.setStyle(CursorToolStyle.VERTICAL);
                else
                    cursorTool.setStyle(CursorToolStyle.BOTH);
        }
    }

    public void cbSnap_actionPerformed(ActionEvent e) {
        if (!setting)
                cursorTool.setSnap(cbSnap.isSelected());
    }

    public void cbFollow_actionPerformed(ActionEvent e) {
        if (!setting)
                cursorTool.setFollowMouse(cbFollow.isSelected());
    }
}


class CursorEditor_cbFollow_actionAdapter implements ActionListener {
    private CursorEditor adaptee;
    CursorEditor_cbFollow_actionAdapter(CursorEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.cbFollow_actionPerformed(e);
    }
}


class CursorEditor_cbSnap_actionAdapter implements ActionListener {
    private CursorEditor adaptee;
    CursorEditor_cbSnap_actionAdapter(CursorEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.cbSnap_actionPerformed(e);
    }
}


class CursorEditor_cbStyle_actionAdapter implements ActionListener {
    private CursorEditor adaptee;
    CursorEditor_cbStyle_actionAdapter(CursorEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.cbStyle_actionPerformed(e);
    }
}


