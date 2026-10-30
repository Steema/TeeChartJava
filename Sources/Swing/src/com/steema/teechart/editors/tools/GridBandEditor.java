package com.steema.teechart.editors.tools;

import java.awt.Rectangle;
import java.awt.event.*;

import javax.swing.*;
import com.steema.teechart.editors.tools.AxisToolEdit;
import com.steema.teechart.editors.ButtonColor;
import com.steema.teechart.tools.Tool;
import com.steema.teechart.tools.GridBand;
import com.steema.teechart.editors.BrushEditor;

import com.steema.teechart.languages.Language;

/**
 * <p>Title: GridBandEditor class</p>
 *
 * <p>Description: GridBand Editor panel</p>
 *
 * <p>Copyright (c) 2004-2019 by Steema Software SL. All Rights Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 *
 */
public class GridBandEditor extends AxisToolEdit {


    public GridBandEditor() {
        try {
            jbInit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void setTool(Tool tool) {
        gridBand=(GridBand)tool;
        super.setTool(gridBand);
        this.bPen.setVisible(false);
        buttonColor1.setColor(gridBand.getBand1().getColor());
        buttonColor2.setColor(gridBand.getBand2().getColor());

    }

    private void jbInit() throws Exception {
        this.setLayout(null);
        bBand1.setBounds(new Rectangle(24, 89, 78, 24));
        bBand1.setMnemonic('1');
        bBand1.setText(Language.getString("Band_1_"));
        bBand1.addActionListener(new GridBandEditor_bBand1_actionAdapter(this));
        bBand2.setBounds(new Rectangle(24, 124, 78, 24));
        bBand2.setMnemonic('2');
        bBand2.setText(Language.getString("Band_2_"));
        bBand2.addActionListener(new GridBandEditor_bBand2_actionAdapter(this));
        buttonColor1.setBounds(new Rectangle(117, 89, 78, 24));
        buttonColor1.setMnemonic(Language.getString("C").charAt(0));
        buttonColor1.setText(Language.getString("Color"));
        buttonColor1.addActionListener(new
                GridBandEditor_buttonColor1_actionAdapter(this));
        buttonColor2.setBounds(new Rectangle(117, 124, 78, 24));
        buttonColor2.setMnemonic(Language.getString("O").charAt(0));
        buttonColor2.setText(Language.getString("Color"));
        buttonColor2.addActionListener(new
                GridBandEditor_buttonColor2_actionAdapter(this));
        this.add(bBand1);
        this.add(bBand2);
        this.add(buttonColor1);
        this.add(buttonColor2);
    }

    GridBand gridBand;
    JButton bBand1 = new JButton();
    JButton bBand2 = new JButton();
    ButtonColor buttonColor1 = new ButtonColor();
    ButtonColor buttonColor2 = new ButtonColor();

    public void bBand1_actionPerformed(ActionEvent e) {
        BrushEditor.edit(this,gridBand.getBand1());
        buttonColor1.setColor(gridBand.getBand1().getColor());
    }

    public void bBand2_actionPerformed(ActionEvent e) {
        BrushEditor.edit(this,gridBand.getBand2());
        buttonColor2.setColor(gridBand.getBand2().getColor());
    }

    public void buttonColor1_actionPerformed(ActionEvent e) {
        gridBand.getBand1().setColor(buttonColor1.getColor());
    }

    public void buttonColor2_actionPerformed(ActionEvent e) {
        gridBand.getBand2().setColor(buttonColor2.getColor());
    }
}


class GridBandEditor_buttonColor2_actionAdapter implements ActionListener {
    private GridBandEditor adaptee;
    GridBandEditor_buttonColor2_actionAdapter(GridBandEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.buttonColor2_actionPerformed(e);
    }
}


class GridBandEditor_buttonColor1_actionAdapter implements ActionListener {
    private GridBandEditor adaptee;
    GridBandEditor_buttonColor1_actionAdapter(GridBandEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.buttonColor1_actionPerformed(e);
    }
}


class GridBandEditor_bBand2_actionAdapter implements ActionListener {
    private GridBandEditor adaptee;
    GridBandEditor_bBand2_actionAdapter(GridBandEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.bBand2_actionPerformed(e);
    }
}


class GridBandEditor_bBand1_actionAdapter implements ActionListener {
    private GridBandEditor adaptee;
    GridBandEditor_bBand1_actionAdapter(GridBandEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.bBand1_actionPerformed(e);
    }
}


