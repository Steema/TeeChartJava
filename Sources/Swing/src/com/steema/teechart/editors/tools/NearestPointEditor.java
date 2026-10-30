package com.steema.teechart.editors.tools;

import java.awt.Rectangle;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.event.*;

import com.steema.teechart.editors.tools.ToolSeriesEditor;
import com.steema.teechart.editors.ButtonPen;
import com.steema.teechart.editors.BrushEditor;
import com.steema.teechart.tools.NearestPoint;
import com.steema.teechart.tools.Tool;
import com.steema.teechart.tools.NearestPointStyle;
import com.steema.teechart.languages.Language;

/**
 * <p>Title: NearestPointEditor class</p>
 *
 * <p>Description:  NearestPoint Editor panel</p>
 *
 * <p>Copyright (c) 2004-2019 by Steema Software SL. All Rights Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 *
 */
public class NearestPointEditor extends ToolSeriesEditor {


    public NearestPointEditor() {
        try {
            jbInit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void setTool(Tool tool) {
        setting=true;
        nearestPoint=(NearestPoint)tool;
        super.setTool(nearestPoint);

        cbDrawLine.setSelected(nearestPoint.getDrawLine());
        cbStyle.setSelectedIndex(nearestPoint.getStyle().getValue());
        spSize.setValue(new Integer(nearestPoint.getSize()));
        bBorder.setPen(nearestPoint.getPen());
        setting=false;
    }

    private void jbInit() throws Exception {
        this.setLayout(null);
        cbStyle.addItem(Language.getString("None"));
        cbStyle.addItem(Language.getString("Circle"));
        cbStyle.addItem(Language.getString("Rectangle"));
        cbStyle.addItem(Language.getString("Diamond"));
        bFill.setBounds(new Rectangle(33, 48, 78, 23));
        bFill.setMnemonic(Language.getString("F").charAt(0));
        bFill.setText(Language.getString("Fill"));
        bFill.addActionListener(new NearestPointEditor_bFill_actionAdapter(this));
        bBorder.setBounds(new Rectangle(116, 48, 78, 23));
        bBorder.setMnemonic(Language.getString("B").charAt(0));
        bBorder.setText(Language.getString("Border"));
        cbDrawLine.setMnemonic(Language.getString("D").charAt(0));
        cbDrawLine.setText(Language.getString("Draw_Line"));
        cbDrawLine.setBounds(new Rectangle(33, 79, 83, 23));
        cbDrawLine.addActionListener(new
                NearestPointEditor_cbDrawLine_actionAdapter(this));
        jLabel1.setDisplayedMnemonic(Language.getString("Z").charAt(0));
        jLabel1.setHorizontalAlignment(SwingConstants.RIGHT);
        jLabel1.setLabelFor(spSize);
        jLabel1.setText(Language.getString("Size"));
        jLabel1.setBounds(new Rectangle(91, 148, 34, 15));
        cbStyle.setBounds(new Rectangle(74, 111, 120, 23));
        cbStyle.addActionListener(new NearestPointEditor_cbStyle_actionAdapter(this));
        jLabel2.setDisplayedMnemonic(Language.getString("S").charAt(0));
        jLabel2.setHorizontalAlignment(SwingConstants.RIGHT);
        jLabel2.setLabelFor(cbStyle);
        jLabel2.setText(Language.getString("Style"));
        jLabel2.setBounds(new Rectangle(33, 114, 34, 15));
        spSize.setBounds(new Rectangle(134, 144, 60, 23));
        spSize.addChangeListener(new NearestPointEditor_spSize_changeAdapter(this));
        this.add(bFill);
        this.add(bBorder);
        this.add(cbDrawLine);
        this.add(cbStyle);
        this.add(spSize);
        this.add(jLabel2);
        this.add(jLabel1);
    }

    NearestPoint nearestPoint;
    boolean setting;
    JButton bFill = new JButton();
    ButtonPen bBorder = new ButtonPen();
    JCheckBox cbDrawLine = new JCheckBox();
    JLabel jLabel1 = new JLabel();
    JComboBox cbStyle = new JComboBox();
    JLabel jLabel2 = new JLabel();
    JSpinner spSize = new JSpinner();

    public void bFill_actionPerformed(ActionEvent e) {
        BrushEditor.edit(nearestPoint.getBrush());
    }

    public void cbDrawLine_actionPerformed(ActionEvent e) {
        if (!setting)
          nearestPoint.setDrawLine(cbDrawLine.isSelected());
    }

    public void spSize_stateChanged(ChangeEvent e) {
        if (!setting)
          nearestPoint.setSize(((Integer)spSize.getValue()).intValue());
    }

    public void cbStyle_actionPerformed(ActionEvent e) {
        if (!setting)
        {
            switch (cbStyle.getSelectedIndex())
            {
            case 0: nearestPoint.setStyle(NearestPointStyle.NONE); break;
            case 1: nearestPoint.setStyle(NearestPointStyle.CIRCLE); break;
            case 2: nearestPoint.setStyle(NearestPointStyle.RECTANGLE); break;
            case 3: nearestPoint.setStyle(NearestPointStyle.DIAMOND); break;
            }
        }
    }
}


class NearestPointEditor_cbStyle_actionAdapter implements ActionListener {
    private NearestPointEditor adaptee;
    NearestPointEditor_cbStyle_actionAdapter(NearestPointEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.cbStyle_actionPerformed(e);
    }
}


class NearestPointEditor_spSize_changeAdapter implements ChangeListener {
    private NearestPointEditor adaptee;
    NearestPointEditor_spSize_changeAdapter(NearestPointEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void stateChanged(ChangeEvent e) {
        adaptee.spSize_stateChanged(e);
    }
}


class NearestPointEditor_cbDrawLine_actionAdapter implements ActionListener {
    private NearestPointEditor adaptee;
    NearestPointEditor_cbDrawLine_actionAdapter(NearestPointEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.cbDrawLine_actionPerformed(e);
    }
}


class NearestPointEditor_bFill_actionAdapter implements ActionListener {
    private NearestPointEditor adaptee;
    NearestPointEditor_bFill_actionAdapter(NearestPointEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.bFill_actionPerformed(e);
    }
}


