package com.steema.teechart.editors;

import javax.swing.*;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.steema.teechart.Panel;
import com.steema.teechart.Chart;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import javax.swing.border.EtchedBorder;

import com.steema.teechart.drawing.SystemColor;
import com.steema.teechart.languages.Language;

/**
 * <p>Title: TeeChart for Java</p>
 *
 * <p>Description: TeeChart for Java</p>
 *
 * <p>Copyright (c) 2004-2019 by Steema Software SL. All Rights Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 *
 */
public class PanelEditor extends JPanel {

    JTabbedPane jTabbedPane1 = new JTabbedPane();
    JPanel jPanel1 = new JPanel();
    JPanel jPanel2 = new JPanel();
    JPanel jPanel3 = new JPanel();
    ButtonColor bColor = new ButtonColor();
    JCheckBox cbDefault = new JCheckBox();
    private Panel panel;
    BorderLayout borderLayout2 = new BorderLayout();
    BorderLayout borderLayout1 = new BorderLayout();
    JPanel jPanel4 = new JPanel();
    BorderLayout borderLayout3 = new BorderLayout();
    JCheckBox cbTransp = new JCheckBox();
    GradientEditor gradient=new GradientEditor();
    ShadowEditor shadow=new ShadowEditor();
    JButton jButton1 = new JButton();
    JPanel jPanel5 = new JPanel();
    Border border1 = BorderFactory.createEmptyBorder();
    Border border2 = new TitledBorder(border1, Language.getString("Bevel"));
    JPanel jPanel6 = new JPanel();
    Border border3 = BorderFactory.createEmptyBorder();
    Border border4 = new TitledBorder(border3, Language.getString("Image_Bevel"));
    ButtonPen button10 = new ButtonPen();
    JComboBox cbBevel = new JComboBox();

    private BevelEditor bevelEditor;
    BorderLayout borderLayout4 = new BorderLayout();
    Border border5 = BorderFactory.createEtchedBorder(EtchedBorder.RAISED,
            Color.white, new Color(165, 163, 151));
    Border border6 = new TitledBorder(border5, Language.getString("Image_Bevel"));
    Border border7 = BorderFactory.createEtchedBorder(EtchedBorder.RAISED,
            Color.white, new Color(165, 163, 151));
    Border border8 = new TitledBorder(border7, Language.getString("Bevel"));
    private BevelImageEditor bevelImageEditor;
    BorderLayout borderLayout5 = new BorderLayout();


    public PanelEditor() {
        super();
        try {
            jbInit();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        jPanel3.add(gradient, java.awt.BorderLayout.CENTER);
        jPanel4.add(shadow, java.awt.BorderLayout.CENTER);

    }

    public PanelEditor(Chart chart) {
        this();
        panel = chart.getPanel();

        gradient.refreshControls(panel.getGradient());
        cbDefault.setSelected(panel.getColor().equals(SystemColor.getControlColor()));
        bColor.setColor(panel.getColor());
        shadow.refreshControls(panel.getShadow());
        cbTransp.setSelected(panel.getTransparent());

        button10.setPen(panel.getPen());

        bevelEditor=new BevelEditor(panel.getBevel());
        jPanel5.add(bevelEditor, java.awt.BorderLayout.CENTER);

        bevelImageEditor=new BevelImageEditor(panel.getImageBevel());
        jPanel6.add(bevelImageEditor,java.awt.BorderLayout.CENTER);

        cbBevel.addItem(Language.getString("Bevel"));
        cbBevel.addItem(Language.getString("Image_Bevel"));

        if (panel.getImageBevel().getVisible())
            cbBevel.setSelectedIndex(1);
        else
            cbBevel.setSelectedIndex(0);
    }

    private void jbInit() throws Exception {
        jTabbedPane1.setPreferredSize(new Dimension(5, 50));
        bColor.setBounds(new Rectangle(7, 14, 85, 23));
        bColor.setMnemonic(Language.getString("C").charAt(0));
        bColor.setText(Language.getString("Color"));
        bColor.addActionListener(new PanelEditor_bColor_actionAdapter(this));
        jPanel1.setLayout(null);
        cbDefault.setMnemonic(Language.getString("D").charAt(0));
        cbDefault.setText(Language.getString("Default"));
        cbDefault.setBounds(new Rectangle(102, 15, 81, 23));
        cbDefault.addActionListener(new PanelEditor_jCheckBox1_actionAdapter(this));
        this.setLayout(borderLayout2);
        jPanel3.setLayout(borderLayout1);
        jPanel4.setLayout(borderLayout3);
        cbTransp.setMnemonic(Language.getString("T").charAt(0));
        cbTransp.setText(Language.getString("Transparent"));
        cbTransp.setBounds(new Rectangle(203, 15, 167, 23));
        cbTransp.addActionListener(new PanelEditor_cbTransp_actionAdapter(this));
        jButton1.setBounds(new Rectangle(7, 46, 85, 25));
        jButton1.setMnemonic(Language.getString("P").charAt(0));
        jButton1.setText(Language.getString("Pattern"));
        jButton1.addActionListener(new PanelEditor_jButton1_actionAdapter(this));
        jPanel2.setLayout(null);
        jPanel5.setBorder(border8);
        jPanel5.setBounds(new Rectangle(1, 53, 200, 143));
        jPanel5.setLayout(borderLayout4);
        jPanel6.setBorder(border6);
        jPanel6.setBounds(new Rectangle(199, 53, 157, 143));
        jPanel6.setLayout(borderLayout5);
        button10.setBounds(new Rectangle(23, 9, 80, 25));
        button10.setMnemonic(Language.getString("B").charAt(0));
        button10.setText(Language.getString("Border"));
        cbBevel.setBounds(new Rectangle(136, 10, 105, 23));
        cbBevel.addActionListener(new PanelEditor_cbBevel_actionAdapter(this));
        jPanel1.add(cbTransp);
        jPanel1.add(cbDefault);
        jPanel1.add(bColor);
        jPanel1.add(jButton1);
        this.add(jTabbedPane1, java.awt.BorderLayout.CENTER);
        jTabbedPane1.add(jPanel1, Language.getString("Background"));
        jPanel2.add(button10);
        jPanel2.add(cbBevel);
        jPanel2.add(jPanel5);
        jPanel2.add(jPanel6);
        jTabbedPane1.add(jPanel2, Language.getString("Borders"));
        jTabbedPane1.add(jPanel3, Language.getString("Gradient"));
        jTabbedPane1.add(jPanel4, Language.getString("Shadow"));
    }

    public void jCheckBox1_actionPerformed(ActionEvent e) {
        panel.setColor(SystemColor.getControlColor());
        bColor.invalidate();
    }

    public void bColor_actionPerformed(ActionEvent e) {
        panel.setColor(bColor.getColor());
        cbDefault.setSelected(panel.getColor().equals(SystemColor.getControlColor()));
    }

    public void cbTransp_actionPerformed(ActionEvent e) {
        panel.setTransparent(cbTransp.isSelected());
    }

    public void jButton1_actionPerformed(ActionEvent e) {
        BrushEditor.edit(panel.getBrush());
    }

    public void cbBevel_actionPerformed(ActionEvent e) {
        switch (cbBevel.getSelectedIndex()) {
          case 0:
              panel.getImageBevel().setVisible(false);
              bevelEditor.doEnableControls(true);
              bevelImageEditor.doEnableControls(false);
              break;
          case 1:
              panel.getImageBevel().setVisible(true);
              bevelEditor.doEnableControls(false);
              bevelImageEditor.doEnableControls(true);
              break;
        }
    }
}


class PanelEditor_cbBevel_actionAdapter implements ActionListener {
    private PanelEditor adaptee;
    PanelEditor_cbBevel_actionAdapter(PanelEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.cbBevel_actionPerformed(e);
    }
}


class PanelEditor_jButton1_actionAdapter implements ActionListener {
    private PanelEditor adaptee;
    PanelEditor_jButton1_actionAdapter(PanelEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.jButton1_actionPerformed(e);
    }
}


class PanelEditor_cbTransp_actionAdapter implements ActionListener {
    private PanelEditor adaptee;
    PanelEditor_cbTransp_actionAdapter(PanelEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.cbTransp_actionPerformed(e);
    }
}


final class PanelEditor_bColor_actionAdapter implements ActionListener {
    private PanelEditor adaptee;
    PanelEditor_bColor_actionAdapter(PanelEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.bColor_actionPerformed(e);
    }
}


final class PanelEditor_jCheckBox1_actionAdapter implements ActionListener {
    private PanelEditor adaptee;
    PanelEditor_jCheckBox1_actionAdapter(PanelEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.jCheckBox1_actionPerformed(e);
    }
}

