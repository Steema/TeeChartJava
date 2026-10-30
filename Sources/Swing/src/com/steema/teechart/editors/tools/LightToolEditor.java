package com.steema.teechart.editors.tools;

// TODO ..

import java.awt.Rectangle;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.event.*;

import com.steema.teechart.tools.Tool;
import com.steema.teechart.editors.BaseToolsEditor;
import com.steema.teechart.languages.Language;

/**
 * <p>Title: LightToolEditor class</p>
 *
 * <p>Description:  LightTool Editor panel</p>
 *
 * <p>Copyright (c) 2004-2019 by Steema Software SL. All Rights Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 *
 */


public class LightToolEditor extends BaseToolsEditor {


    public LightToolEditor() {
        try {
            jbInit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        cbStyle.addItem(Language.getString("Linear"));
        cbStyle.addItem(Language.getString("SpotLight"));

        hSBLeft.setValue(0);
        hSBTop.setValue(0);
        hSBFactor.setValue(0);
    }

    public void setTool(Tool tool) {
        /* to uncomment
        lightTool=(LightTool)tool;
        cbFollow.setSelected(lightTool.getFollowMouse());
        hSBLeft.setMaximum(lightTool.getChart().getWidth());
        hSBTop.setMaximum(lightTool.getChart().getHeight());

        if(lightTool.getLeft() == -1)
                hSBLeft.setValue(lightTool.getChart().getWidth() / 2);
        else
                hSBLeft.setValue(lightTool.getLeft());

        if(lightTool.getTop() == -1)
                hSBTop.setValue(lightTool.getChart().getHeight() / 2);
        else
                hSBTop.setValue(lightTool.getTop());

        hSBFactor.setValue(((Integer)lightTool.getFactor()).intValue());

        if(lightTool.getStyle() == LightStyle.LINEAR)
                cbStyle.setSelectedIndex(0);
        else
                cbStyle.setSelectedIndex(1);
         */
    }

    private void jbInit() throws Exception {
        this.setLayout(null);
        cbFollow.setMnemonic(Language.getString("F").charAt(0));
        cbFollow.setSelected(true);
        cbFollow.setText(Language.getString("Follow_Mouse1"));
        cbFollow.setBounds(new Rectangle(52, 17, 120, 23));
        cbFollow.addActionListener(new LightToolEditor_cbFollow_actionAdapter(this));
        jLabel1.setDisplayedMnemonic(Language.getString("L").charAt(0));
        jLabel1.setHorizontalAlignment(SwingConstants.RIGHT);
        jLabel1.setLabelFor(hSBLeft);
        jLabel1.setText(Language.getString("Left"));
        jLabel1.setBounds(new Rectangle(36, 95, 50, 15));
        jLabel2.setDisplayedMnemonic(Language.getString("T").charAt(0));
        jLabel2.setHorizontalAlignment(SwingConstants.RIGHT);
        jLabel2.setLabelFor(hSBTop);
        jLabel2.setText(Language.getString("Top"));
        jLabel2.setBounds(new Rectangle(36, 125, 50, 15));
        jLabel3.setDisplayedMnemonic(Language.getString("F").charAt(0));
        jLabel3.setHorizontalAlignment(SwingConstants.RIGHT);
        jLabel3.setLabelFor(hSBFactor);
        jLabel3.setText(Language.getString("Factor"));
        jLabel3.setBounds(new Rectangle(36, 155, 50, 15));
        jLabel4.setDisplayedMnemonic(Language.getString("S").charAt(0));
        jLabel4.setHorizontalAlignment(SwingConstants.RIGHT);
        jLabel4.setLabelFor(cbStyle);
        jLabel4.setText(Language.getString("Style_12"));
        jLabel4.setBounds(new Rectangle(36, 63, 50, 15));
        cbStyle.setBounds(new Rectangle(98, 59, 93, 23));
        cbStyle.addActionListener(new LightToolEditor_cbStyle_actionAdapter(this));
        hSBTop.setBounds(new Rectangle(97, 123, 97, 19));
        hSBTop.addChangeListener(new LightToolEditor_hSBTop_changeAdapter(this));
        hSBFactor.setBounds(new Rectangle(97, 153, 97, 19));
        hSBFactor.addChangeListener(new LightToolEditor_hSBFactor_changeAdapter(this));
        hSBLeft.setBounds(new Rectangle(97, 93, 97, 19));
        hSBLeft.addChangeListener(new LightToolEditor_hSBLeft_changeAdapter(this));
        this.add(cbFollow);
        this.add(jLabel1);
        this.add(jLabel4);
        this.add(jLabel3);
        this.add(jLabel2);
        this.add(cbStyle);
        this.add(hSBTop);
        this.add(hSBLeft);
        this.add(hSBFactor);
    }

//  LightTool lightTool;
    JCheckBox cbFollow = new JCheckBox();
    JLabel jLabel1 = new JLabel();
    JLabel jLabel2 = new JLabel();
    JLabel jLabel3 = new JLabel();
    JLabel jLabel4 = new JLabel();
    JComboBox cbStyle = new JComboBox();
    JSlider hSBTop = new JSlider();
    JSlider hSBFactor = new JSlider();
    JSlider hSBLeft = new JSlider();

    public void cbFollow_actionPerformed(ActionEvent e) {
//       lightTool.setFollowMouse(cbFollow.isSelected());
    }

    public void hSBLeft_stateChanged(ChangeEvent e) {
//       lightTool.setLeft(hSBLeft.getValue());
    }

    public void hSBTop_stateChanged(ChangeEvent e) {
//       lightTool.setTop(hSBTop.getValue());
    }

    public void hSBFactor_stateChanged(ChangeEvent e) {
//       lightTool.setFactor(hSBFactor.getValue());
    }

    public void cbStyle_actionPerformed(ActionEvent e) {
/*    if(cbStyle.getSelectedIndex() == 0)
                lightTool.setStyle(LightStyle.LINEAR);
        else
                lightTool.setStyle(LightStyle.SPOTLIGHT);
*/
    }
}


class LightToolEditor_cbStyle_actionAdapter implements ActionListener {
    private LightToolEditor adaptee;
    LightToolEditor_cbStyle_actionAdapter(LightToolEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.cbStyle_actionPerformed(e);
    }
}


class LightToolEditor_hSBFactor_changeAdapter implements ChangeListener {
    private LightToolEditor adaptee;
    LightToolEditor_hSBFactor_changeAdapter(LightToolEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void stateChanged(ChangeEvent e) {
        adaptee.hSBFactor_stateChanged(e);
    }
}


class LightToolEditor_hSBTop_changeAdapter implements ChangeListener {
    private LightToolEditor adaptee;
    LightToolEditor_hSBTop_changeAdapter(LightToolEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void stateChanged(ChangeEvent e) {
        adaptee.hSBTop_stateChanged(e);
    }
}


class LightToolEditor_hSBLeft_changeAdapter implements ChangeListener {
    private LightToolEditor adaptee;
    LightToolEditor_hSBLeft_changeAdapter(LightToolEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void stateChanged(ChangeEvent e) {
        adaptee.hSBLeft_stateChanged(e);
    }
}


class LightToolEditor_cbFollow_actionAdapter implements ActionListener {
    private LightToolEditor adaptee;
    LightToolEditor_cbFollow_actionAdapter(LightToolEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.cbFollow_actionPerformed(e);
    }
}


