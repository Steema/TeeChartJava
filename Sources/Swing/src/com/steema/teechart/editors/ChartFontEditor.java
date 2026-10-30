package com.steema.teechart.editors;

import java.awt.*;

import javax.swing.*;
import java.awt.BorderLayout;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.steema.teechart.drawing.ChartFont;

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
public class ChartFontEditor extends JPanel {


    private ChartFont chartFont;

    ShadowEditor shadowEditor = new ShadowEditor();
    GradientEditor gradientEditor = new GradientEditor();
    ButtonColor bColor = new ButtonColor();
    Border border1 = BorderFactory.createEtchedBorder(Color.white,
            new java.awt.Color(148, 145, 140));
    JLabel jLabel1 = new JLabel();
    JSpinner spSize = new JSpinner();
    ButtonPen bOutline = new ButtonPen();
    JTabbedPane jTabbedPane1 = new JTabbedPane();
    JPanel jPanel1 = new JPanel();
    BorderLayout borderLayout1 = new BorderLayout();
    JButton jButton2 = new JButton();
    JComboBox cbFonts = new JComboBox();
    JCheckBox cbBold = new JCheckBox();
    JCheckBox cbItalic = new JCheckBox();

    public ChartFontEditor() {
        try {
            jbInit();
        } catch (Exception exception) {
            exception.printStackTrace();
        }

        GraphicsEnvironment ge = GraphicsEnvironment.
                                 getLocalGraphicsEnvironment();
        cbFonts.setModel(new DefaultComboBoxModel(ge.
                                                  getAvailableFontFamilyNames()));
    }

    public ChartFontEditor(ChartFont font) {
        this();
        this.chartFont = font;
        refreshControls(chartFont);
    }

    /**
     * Shows the ChartFont editor dialog to modify font parameter properties.
     *
     * @param owner Frame
     * @param font ChartFont
     */
    public static void edit(Container owner, ChartFont font) {
        JDialog d = new JDialog();
        d.setTitle(Language.getString("Font Editor"));
        d.setModal(true);
        d.setSize(new Dimension(230, 200));
        ChartFontEditor f = new ChartFontEditor(font);

        d.getContentPane().add(f);
        d.setLocationRelativeTo(owner);
        d.setVisible(true);
    }

    private void jbInit() throws Exception {
        border1 = new TitledBorder(BorderFactory.createEtchedBorder(Color.white,
                new Color(148, 145, 140)), Language.getString("Shadow"));
        setLayout(borderLayout1);
        bColor.setBounds(new Rectangle(96, 48, 83, 23));
        bColor.setMnemonic(Language.getString("C").charAt(0));
        bColor.setText(Language.getString("Color"));
        bColor.addActionListener(new ChartFontEditor_bColor_actionAdapter(this));
        jLabel1.setDisplayedMnemonic(Language.getString("S").charAt(0));
        jLabel1.setText(Language.getString("Size"));
        jLabel1.setBounds(new Rectangle(8, 31, 64, 23));
        spSize.setBounds(new Rectangle(8, 51, 52, 20));
        spSize.addChangeListener(new ChartFontEditor_spSize_changeAdapter(this));
        bOutline.setBounds(new Rectangle(96, 80, 83, 23));
        bOutline.setMnemonic(Language.getString("O").charAt(0));
        bOutline.setText(Language.getString("Outline"));
        jPanel1.setLayout(null);
        jButton2.setBounds(new Rectangle(8, 80, 75, 23));
        jButton2.setMnemonic(Language.getString("I").charAt(0));
        jButton2.setText(Language.getString("Fill"));
        jButton2.addActionListener(new ChartFontEditor_jButton2_actionAdapter(this));
        cbFonts.setBounds(new Rectangle(6, 5, 177, 22));
        cbFonts.addActionListener(new ChartFontEditor_cbFonts_actionAdapter(this));
        cbBold.setMnemonic(Language.getString("B").charAt(0));
        cbBold.setText(Language.getString("Bold"));
        cbBold.setBounds(new Rectangle(7, 114, 65, 23));
        cbBold.addActionListener(new ChartFontEditor_cbBold_actionAdapter(this));
        cbItalic.setMnemonic(Language.getString("T").charAt(0));
        cbItalic.setText(Language.getString("Italic"));
        cbItalic.setBounds(new Rectangle(90, 114, 81, 23));
        cbItalic.addActionListener(new ChartFontEditor_cbItalic_actionAdapter(this));
        jTabbedPane1.add(jPanel1, Language.getString("Font"));
        jPanel1.add(cbFonts);
        jPanel1.add(spSize);
        jPanel1.add(jLabel1);
        jPanel1.add(jButton2);
        jPanel1.add(bOutline);
        jPanel1.add(bColor);
        jPanel1.add(cbBold);
        jPanel1.add(cbItalic);
        this.add(jTabbedPane1, java.awt.BorderLayout.CENTER);
        jTabbedPane1.add(shadowEditor, Language.getString("Shadow"));
        jTabbedPane1.add(gradientEditor, Language.getString("Gradient"));
    }

    void refreshControls(ChartFont chartFont) {
        this.chartFont = chartFont;
        bColor.setColor(chartFont.getColor());
        spSize.setValue(new Integer(chartFont.getSize()));
        shadowEditor.refreshControls(chartFont.getShadow());
        bOutline.setPen(chartFont.getOutline());
        cbFonts.setSelectedItem(chartFont.getName());
        gradientEditor.refreshControls(chartFont.getGradient());
        cbBold.setSelected(chartFont.getBold());
        cbItalic.setSelected(chartFont.getItalic());
    }

    public void bColor_actionPerformed(ActionEvent e) {
        chartFont.setColor(bColor.getColor());
    }

    public void spSize_stateChanged(ChangeEvent e) {
        chartFont.setSize(((Integer) spSize.getValue()).intValue());
    }

    public void cbFonts_actionPerformed(ActionEvent e) {
        chartFont.setName((String) cbFonts.getSelectedItem());
    }

    public void jButton2_actionPerformed(ActionEvent e) {
        BrushEditor.edit(chartFont.getBrush());
    }

    public void cbBold_actionPerformed(ActionEvent e) {
        chartFont.setBold(cbBold.isSelected());
    }

    public void cbItalic_actionPerformed(ActionEvent e) {
        chartFont.setItalic(cbItalic.isSelected());
    }
}


final class ChartFontEditor_cbItalic_actionAdapter implements ActionListener {
    private ChartFontEditor adaptee;
    ChartFontEditor_cbItalic_actionAdapter(ChartFontEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.cbItalic_actionPerformed(e);
    }
}


final class ChartFontEditor_cbBold_actionAdapter implements ActionListener {
    private ChartFontEditor adaptee;
    ChartFontEditor_cbBold_actionAdapter(ChartFontEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.cbBold_actionPerformed(e);
    }
}


final class ChartFontEditor_jButton2_actionAdapter implements ActionListener {
    private ChartFontEditor adaptee;
    ChartFontEditor_jButton2_actionAdapter(ChartFontEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.jButton2_actionPerformed(e);
    }
}


final class ChartFontEditor_cbFonts_actionAdapter implements ActionListener {
    private ChartFontEditor adaptee;
    ChartFontEditor_cbFonts_actionAdapter(ChartFontEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.cbFonts_actionPerformed(e);
    }
}


final class ChartFontEditor_spSize_changeAdapter implements ChangeListener {
    private ChartFontEditor adaptee;
    ChartFontEditor_spSize_changeAdapter(ChartFontEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void stateChanged(ChangeEvent e) {
        adaptee.spSize_stateChanged(e);
    }
}


final class ChartFontEditor_bColor_actionAdapter implements ActionListener {
    private ChartFontEditor adaptee;
    ChartFontEditor_bColor_actionAdapter(ChartFontEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.bColor_actionPerformed(e);
    }
}

