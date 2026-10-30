package com.steema.teechart.editors.tools;

// TODO ..

import java.awt.Rectangle;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.event.*;

import com.steema.teechart.editors.ButtonColor;
import com.steema.teechart.tools.Tool;
//  import com.steema.teechart.tools.ZoomTool;
import com.steema.teechart.editors.BaseToolsEditor;
import com.steema.teechart.languages.Language;

/**
 * <p>Title: ZoomToolEditor class</p>
 *
 * <p>Description:  Zoom Tool Editor panel</p>
 *
 * <p>Copyright (c) 2004-2019 by Steema Software SL. All Rights Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 *
 */

public class ZoomToolEditor extends BaseToolsEditor {


    public ZoomToolEditor() {
        try {
            jbInit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void setTool(Tool tool) {
/*        zoom = (ZoomTool)tool;
        buttonColor1.setColor(zoom.getZoomPenColor());
        numUDZIndex.setValue(zoom.getZoomCanvasIndex()); */
    }

    private void jbInit() throws Exception {
        this.setLayout(null);
        jLabel1.setHorizontalAlignment(SwingConstants.RIGHT);
        jLabel1.setText(Language.getString("ZIndex_"));
        jLabel1.setBounds(new Rectangle(68, 77, 46, 15));
        buttonColor1.setBounds(new Rectangle(112, 35, 74, 23));
        buttonColor1.setMnemonic(Language.getString("C").charAt(0));
        buttonColor1.setText(Language.getString("Color"));
        buttonColor1.addActionListener(new
                ZoomToolEditor_buttonColor1_actionAdapter(this));
        numUDZIndex.setBounds(new Rectangle(122, 73, 63, 22));
        numUDZIndex.addFocusListener(new
                                     ZoomToolEditor_numUDZIndex_focusAdapter(this));
        numUDZIndex.addChangeListener(new
                                      ZoomToolEditor_numUDZIndex_changeAdapter(this));
        this.add(buttonColor1);
        this.add(numUDZIndex);
        this.add(jLabel1);
    }

//     ZoomTool zoom;
    JLabel jLabel1 = new JLabel();
    ButtonColor buttonColor1 = new ButtonColor();
    JSpinner numUDZIndex = new JSpinner();

    public void buttonColor1_actionPerformed(ActionEvent e) {
//         zoom.ZoomPenColor = buttonColor1.Color;
    }

    public void numUDZIndex_stateChanged(ChangeEvent e) {
//        zoom.ZoomCanvasIndex = (int)numUDZIndex.Value;
    }

    public void numUDZIndex_focusLost(FocusEvent e) {
//        numUDZIndex_ValueChanged(sender, e);
    }
}


class ZoomToolEditor_numUDZIndex_focusAdapter extends FocusAdapter {
    private ZoomToolEditor adaptee;
    ZoomToolEditor_numUDZIndex_focusAdapter(ZoomToolEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void focusLost(FocusEvent e) {
        adaptee.numUDZIndex_focusLost(e);
    }
}


class ZoomToolEditor_numUDZIndex_changeAdapter implements ChangeListener {
    private ZoomToolEditor adaptee;
    ZoomToolEditor_numUDZIndex_changeAdapter(ZoomToolEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void stateChanged(ChangeEvent e) {
        adaptee.numUDZIndex_stateChanged(e);
    }
}


class ZoomToolEditor_buttonColor1_actionAdapter implements ActionListener {
    private ZoomToolEditor adaptee;
    ZoomToolEditor_buttonColor1_actionAdapter(ZoomToolEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.buttonColor1_actionPerformed(e);
    }
}


