package com.steema.teechart.editors.tools;

import java.awt.Rectangle;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.event.*;

import com.steema.teechart.tools.*;
import com.steema.teechart.editors.BrushEditor;
import com.steema.teechart.languages.Language;

/**
 * <p>Title: AxisArrowEditorclass</p>
 *
 * <p>Description:  Axis Arrow Editor panel</p>
 *
 * <p>Copyright (c) 2004-2019 by Steema Software SL. All Rights Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 *
 */
public class AxisArrowEditor extends AxisToolEdit {


    public AxisArrowEditor() {
        try {
            jbInit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void setTool(Tool tool) {
        setting=true;
        super.setTool(tool);
        axisArrow=(AxisArrow)tool;
        cbInverted.setSelected(axisArrow.getScrollInverted());
        spLength.setValue(new Integer(axisArrow.getLength()));
        spScroll.setValue(new Integer(axisArrow.getScrollPercent()));
        cbPosition.setSelectedIndex(axisArrow.getPosition().getValue());
        setting=false;
    }

    private void jbInit() throws Exception {
        cbPosition.addItem(Language.getString("Start"));
        cbPosition.addItem(Language.getString("End"));
        cbPosition.addItem(Language.getString("Both"));
        bFill.setBounds(new Rectangle(133, 43, 75, 24));
        bFill.setMnemonic(Language.getString("F").charAt(0));
        bFill.setText(Language.getString("Fill"));
        bFill.addActionListener(new AxisArrowEditor_bFill_actionAdapter(this));
        jLabel1.setDisplayedMnemonic(Language.getString("S").charAt(0));
        jLabel1.setHorizontalAlignment(SwingConstants.RIGHT);
        jLabel1.setText(Language.getString("Scroll"));
        jLabel1.setBounds(new Rectangle(18, 152, 49, 14));
        spLength.setBounds(new Rectangle(75, 87, 61, 24));
        spLength.addChangeListener(new AxisArrowEditor_spLength_changeAdapter(this));
        cbInverted.setMnemonic(Language.getString("I").charAt(0));
        cbInverted.setText(Language.getString("Inverted_Scroll"));
        cbInverted.setBounds(new Rectangle(70, 118, 146, 23));
        cbInverted.addActionListener(new
                                     AxisArrowEditor_cbInverted_actionAdapter(this));
        spScroll.setBounds(new Rectangle(75, 146, 61, 24));
        spScroll.addChangeListener(new AxisArrowEditor_spScroll_changeAdapter(this));
        jLabel2.setDisplayedMnemonic(Language.getString("L").charAt(0));
        jLabel2.setHorizontalAlignment(SwingConstants.RIGHT);
        jLabel2.setText("%");
        jLabel2.setBounds(new Rectangle(141, 152, 16, 14));
        jLabel3.setDisplayedMnemonic(Language.getString("P").charAt(0));
        jLabel3.setHorizontalAlignment(SwingConstants.RIGHT);
        jLabel3.setText(Language.getString("Position"));
        jLabel3.setBounds(new Rectangle(18, 182, 49, 14));
        jLabel4.setDisplayedMnemonic(Language.getString("L").charAt(0));
        jLabel4.setHorizontalAlignment(SwingConstants.RIGHT);
        jLabel4.setText(Language.getString("Length"));
        jLabel4.setBounds(new Rectangle(18, 93, 49, 14));
        cbPosition.setBounds(new Rectangle(75, 176, 135, 24));
        cbPosition.addActionListener(new
                                     AxisArrowEditor_cbPosition_actionAdapter(this));
        this.add(spLength);
        this.add(spScroll);
        this.add(cbInverted);
        this.add(jLabel1);
        this.add(jLabel2);
        this.add(jLabel4);
        this.add(jLabel3);
        this.add(cbPosition);
        this.add(bFill);
    }

    boolean setting;
    AxisArrow axisArrow;
    JButton bFill = new JButton();
    JLabel jLabel1 = new JLabel();
    JSpinner spLength = new JSpinner();
    JCheckBox cbInverted = new JCheckBox();
    JSpinner spScroll = new JSpinner();
    JLabel jLabel2 = new JLabel();
    JLabel jLabel3 = new JLabel();
    JLabel jLabel4 = new JLabel();
    JComboBox cbPosition = new JComboBox();

    public void cbInverted_actionPerformed(ActionEvent e) {
        if (!setting)
          axisArrow.setScrollInverted(cbInverted.isSelected());
    }

    public void spLength_stateChanged(ChangeEvent e) {
        if (!setting) {
            Object value = spLength.getValue();
            if(value instanceof Double) {
                axisArrow.setLength(((Double) value).intValue());
            }
            else if(value instanceof Integer)  {
                axisArrow.setLength(((Integer) value).intValue());
            }
        }
    }

    public void spScroll_stateChanged(ChangeEvent e) {
        if (!setting)
          axisArrow.setScrollPercent(((Integer)spScroll.getValue()).intValue());
    }

    public void bFill_actionPerformed(ActionEvent e) {
        BrushEditor.edit(axisArrow.getBrush());
    }

    public void cbPosition_actionPerformed(ActionEvent e) {
        if (!setting)
            if (cbPosition.getSelectedIndex()==0)
                axisArrow.setPosition(AxisArrowPosition.START);
            else
                if (cbPosition.getSelectedIndex()==1)
                    axisArrow.setPosition(AxisArrowPosition.END);
                else
                    axisArrow.setPosition(AxisArrowPosition.BOTH);
    }
}


class AxisArrowEditor_cbPosition_actionAdapter implements ActionListener {
    private AxisArrowEditor adaptee;
    AxisArrowEditor_cbPosition_actionAdapter(AxisArrowEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.cbPosition_actionPerformed(e);
    }
}


class AxisArrowEditor_bFill_actionAdapter implements ActionListener {
    private AxisArrowEditor adaptee;
    AxisArrowEditor_bFill_actionAdapter(AxisArrowEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.bFill_actionPerformed(e);
    }
}


class AxisArrowEditor_spScroll_changeAdapter implements ChangeListener {
    private AxisArrowEditor adaptee;
    AxisArrowEditor_spScroll_changeAdapter(AxisArrowEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void stateChanged(ChangeEvent e) {
        adaptee.spScroll_stateChanged(e);
    }
}


class AxisArrowEditor_spLength_changeAdapter implements ChangeListener {
    private AxisArrowEditor adaptee;
    AxisArrowEditor_spLength_changeAdapter(AxisArrowEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void stateChanged(ChangeEvent e) {
        adaptee.spLength_stateChanged(e);
    }
}


class AxisArrowEditor_cbInverted_actionAdapter implements ActionListener {
    private AxisArrowEditor adaptee;
    AxisArrowEditor_cbInverted_actionAdapter(AxisArrowEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.cbInverted_actionPerformed(e);
    }
}


