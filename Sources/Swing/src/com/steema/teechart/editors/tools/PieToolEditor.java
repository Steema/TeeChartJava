package com.steema.teechart.editors.tools;

import java.awt.Rectangle;
import java.awt.event.*;

import javax.swing.*;

import javax.swing.border.BevelBorder;
import java.awt.Color;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import com.steema.teechart.tools.PieToolStyle;
import com.steema.teechart.tools.PieTool;
import com.steema.teechart.tools.Tool;
import com.steema.teechart.editors.ButtonPen;
import com.steema.teechart.editors.BaseToolsEditor;
import com.steema.teechart.languages.Language;
import javax.swing.border.EtchedBorder;

/**
 * <p>Title: PieToolEditor class</p>
 *
 * <p>Description:  PieTool Editor panel</p>
 *
 * <p>Copyright (c) 2004-2019 by Steema Software SL. All Rights Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 *
 */
public class PieToolEditor extends BaseToolsEditor {


    public PieToolEditor() {
        try {
            jbInit();
            Load();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void setTool(Tool tool) {
        pieTool=(PieTool)tool;
        if (pieTool!=null)
        {
          for (int i=0;i<pieTool.getChart().getSeriesCount();++i) {
              if (pieTool.getChart().getSeries(i).getClass() ==
                  com.steema.teechart.styles.Pie.class) {
                  cbSeries.addItem(pieTool.getChart().getSeries(i));
              }
          }
          cbSeries.setEnabled(pieTool.getChart().getSeriesCount()>0);
          cbSeries.setSelectedItem(pieTool.getSeries());
          bBorder.setPen(pieTool.getPen());

          if (pieTool.getStyle()==PieToolStyle.FOCUS) {
              radioButton1.setSelected(true);
          }
          else {
              radioButton2.setSelected(true);
          }
      }
    }

    private void Load() {

        ButtonGroup group = new ButtonGroup();
        group.add(radioButton1);
        group.add(radioButton2);
    }

    private void jbInit() throws Exception {
        this.setLayout(null);
        jLabel1.setDisplayedMnemonic(Language.getString("P").charAt(0));
        jLabel1.setHorizontalAlignment(SwingConstants.RIGHT);
        jLabel1.setText(Language.getString("Pie_Series"));
        jLabel1.setBounds(new Rectangle(19, 15, 61, 15));
        cbSeries.setBounds(new Rectangle(93, 11, 124, 22));
        cbSeries.addActionListener(new PieToolEditor_cbSeries_actionAdapter(this));
        jPanel1.setBorder(border18);
        jPanel1.setBounds(new Rectangle(20, 38, 197, 57));
        jPanel1.setLayout(null);
        radioButton1.setMnemonic(Language.getString("F").charAt(0));
        radioButton1.setText(Language.getString("Focus"));
        radioButton1.setBounds(new Rectangle(13, 21, 81, 23));
        radioButton1.addActionListener(new
                                       PieToolEditor_radioButton1_actionAdapter(this));
        radioButton2.setMnemonic(Language.getString("E").charAt(0));
        radioButton2.setText(Language.getString("Explode"));
        radioButton2.setBounds(new Rectangle(94, 21, 91, 23));
        radioButton2.addActionListener(new
                                       PieToolEditor_radioButton2_actionAdapter(this));
        bBorder.setBounds(new Rectangle(20, 100, 78, 23));
        bBorder.setMnemonic(Language.getString("B").charAt(0));
        bBorder.setText(Language.getString("Border"));
        this.add(cbSeries);
        jPanel1.add(radioButton2);
        jPanel1.add(radioButton1);
        this.add(jLabel1);
        this.add(bBorder);
        this.add(jPanel1);
    }

    PieTool pieTool;
    JLabel jLabel1 = new JLabel();
    JComboBox cbSeries = new JComboBox();
    JPanel jPanel1 = new JPanel();
    Border border1 = BorderFactory.createBevelBorder(BevelBorder.LOWERED,
            Color.white, Color.white, new Color(115, 114, 105),
            new Color(165, 163, 151));
    Border border2 = new TitledBorder(border1, Language.getString("Style"));
    JRadioButton radioButton1 = new JRadioButton();
    JRadioButton radioButton2 = new JRadioButton();
    Border border3 = BorderFactory.createBevelBorder(BevelBorder.RAISED,
            Color.white, Color.white, new Color(115, 114, 105),
            new Color(165, 163, 151));
    Border border4 = new TitledBorder(border3, Language.getString("Style"));
    Border border5 = BorderFactory.createBevelBorder(BevelBorder.LOWERED,
            Color.white, Color.white, new Color(115, 114, 105),
            new Color(165, 163, 151));
    Border border6 = new TitledBorder(border5, Language.getString("Style"));
    ButtonPen bBorder = new ButtonPen();
    Border border7 = BorderFactory.createBevelBorder(BevelBorder.LOWERED,
            Color.white, Color.white, new Color(115, 114, 105),
            new Color(165, 163, 151));
    Border border8 = new TitledBorder(border7, Language.getString("Style"));
    Border border9 = BorderFactory.createBevelBorder(BevelBorder.RAISED,
            Color.white, Color.white, new Color(115, 114, 105),
            new Color(165, 163, 151));
    Border border10 = new TitledBorder(border9, Language.getString("Style"));
    Border border11 = BorderFactory.createBevelBorder(BevelBorder.RAISED,
            Color.white, Color.white, new Color(115, 114, 105),
            new Color(165, 163, 151));
    Border border12 = new TitledBorder(border11, Language.getString("Style"));
    Border border13 = BorderFactory.createLineBorder(Color.white, 2);
    Border border14 = new TitledBorder(border13, "Style");
    Border border15 = BorderFactory.createEtchedBorder(EtchedBorder.RAISED,
            Color.white, new Color(165, 163, 151));
    Border border16 = new TitledBorder(border15, "Style");
    Border border17 = BorderFactory.createEtchedBorder(Color.white,
            new Color(165, 163, 151));
    Border border18 = new TitledBorder(border17, "Style");

    public void cbSeries_actionPerformed(ActionEvent e) {
        pieTool.setSeries(((com.steema.teechart.styles.Series)cbSeries.getSelectedItem()));
    }

    public void radioButton1_actionPerformed(ActionEvent e) {
        pieTool.setStyle(PieToolStyle.FOCUS);
    }

    public void radioButton2_actionPerformed(ActionEvent e) {
        pieTool.setStyle(PieToolStyle.EXPLODE);
    }

}


class PieToolEditor_radioButton2_actionAdapter implements ActionListener {
    private PieToolEditor adaptee;
    PieToolEditor_radioButton2_actionAdapter(PieToolEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.radioButton2_actionPerformed(e);
    }
}


class PieToolEditor_radioButton1_actionAdapter implements ActionListener {
    private PieToolEditor adaptee;
    PieToolEditor_radioButton1_actionAdapter(PieToolEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.radioButton1_actionPerformed(e);
    }
}


class PieToolEditor_cbSeries_actionAdapter implements ActionListener {
    private PieToolEditor adaptee;
    PieToolEditor_cbSeries_actionAdapter(PieToolEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.cbSeries_actionPerformed(e);
    }
}


