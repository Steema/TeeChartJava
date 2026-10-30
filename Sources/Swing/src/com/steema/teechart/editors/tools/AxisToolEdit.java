package com.steema.teechart.editors.tools;

import java.awt.Rectangle;
import com.steema.teechart.languages.Language;
import java.awt.event.*;

import javax.swing.*;

import com.steema.teechart.editors.ButtonPen;
import com.steema.teechart.tools.*;
import com.steema.teechart.editors.BaseToolsEditor;

/**
 * <p>Title: AxisToolEdit class</p>
 *
 * <p>Description:  Axis Tool Editor panel</p>
 *
 * <p>Copyright (c) 2004-2019 by Steema Software SL. All Rights Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 *
 */
public class AxisToolEdit extends  BaseToolsEditor {


    public AxisToolEdit() {
        try {
            jbInit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void setTool(Tool t)
    {
        setting=true;
        toolAxis=(ToolAxis)t;
        ((DefaultComboBoxModel)cbAxis.getModel()).removeAllElements();
        cbAxis.addItem(Language.getString("None"));
        String[] axesNames = toolAxis.getChart().getAxes().stringItems();
        for (int i=0;i<axesNames.length;++i) {
            cbAxis.addItem(axesNames[i]);
        }

        if (toolAxis.getAxis()==null)
            cbAxis.setSelectedIndex(0);
        else
          if ((toolAxis.getChart().getAxes().indexOf(toolAxis.getAxis())<=4) && (!toolAxis.getAxis().isCustom())) //MM Oct 04
              cbAxis.setSelectedIndex(1+toolAxis.getChart().getAxes().indexOf(toolAxis.getAxis()));
          else
              cbAxis.setSelectedIndex(5+toolAxis.getChart().getAxes().indexOf(toolAxis.getAxis()));

        bPen.setPen(toolAxis.getPen());
        setting=false;
    }

    private void jbInit() throws Exception {
        this.setLayout(null);
        bPen.setBounds(new Rectangle(50, 43, 77, 24));
        bPen.setMnemonic(Language.getString("B").charAt(0));
        bPen.setText(Language.getString("Border"));
        cbAxis.setBounds(new Rectangle(50, 13, 113, 23));
        cbAxis.addActionListener(new AxisToolEdit_cbAxis_actionAdapter(this));
        jLabel1.setDisplayedMnemonic(Language.getString("A").charAt(0));
        jLabel1.setHorizontalAlignment(SwingConstants.RIGHT);
        jLabel1.setLabelFor(cbAxis);
        jLabel1.setText(Language.getString("Axis"));
        jLabel1.setBounds(new Rectangle(9, 18, 34, 15));
        this.add(jLabel1);
        this.add(cbAxis);
        this.add(bPen);
    }

    private ToolAxis toolAxis;
    private boolean setting;

    ButtonPen bPen = new ButtonPen();
    JComboBox cbAxis = new JComboBox();
    JLabel jLabel1 = new JLabel();

    public void cbAxis_actionPerformed(ActionEvent e) {
        if (!setting)
          if (cbAxis.getSelectedIndex()<=0)
            toolAxis.setAxis(null);
          else
            if (cbAxis.getSelectedIndex()<=4)
              toolAxis.setAxis(toolAxis.getChart().getAxes().getAxis(cbAxis.getSelectedIndex()-1));
            else
              toolAxis.setAxis(toolAxis.getChart().getAxes().getAxis(cbAxis.getSelectedIndex()-1));
    }
}


class AxisToolEdit_cbAxis_actionAdapter implements ActionListener {
    private AxisToolEdit adaptee;
    AxisToolEdit_cbAxis_actionAdapter(AxisToolEdit adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.cbAxis_actionPerformed(e);
    }
}


