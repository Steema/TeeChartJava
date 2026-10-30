package com.steema.teechart.editors.series;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.border.EtchedBorder;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;

import java.awt.BorderLayout;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Color;

import com.steema.teechart.editors.BaseSeriesEditor;
import com.steema.teechart.styles.Series;
import com.steema.teechart.styles.CustomBar;
import com.steema.teechart.styles.BarStyle;
import com.steema.teechart.editors.ButtonPen;
import com.steema.teechart.editors.ButtonColor;
import com.steema.teechart.editors.GradientEditor;
import com.steema.teechart.editors.BrushEditor;
import com.steema.teechart.languages.Language;

/**
 * <p>Title: TeeChart for Java</p>
 *
 * <p>Description: Bar and HorizBar series editor</p>
 *
 * <p>Copyright (c) 2004-2019 by Steema Software SL. All Rights Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 *
 */
public class BarEditor extends BaseSeriesEditor {

    private CustomBar bar;
    JTabbedPane jTabbedPane1 = new JTabbedPane();
    BorderLayout borderLayout1 = new BorderLayout();
    JPanel jPanel1 = new JPanel();
    JPanel jPanel2 = new JPanel();
    JLabel jLabel1 = new JLabel();
    JPanel jPanelf = new JPanel();
    JComboBox cbStyle = new JComboBox();
    ButtonPen bBorder = new ButtonPen();
    JPanel jPanel3 = new JPanel();
    Border border1 = BorderFactory.createEtchedBorder(EtchedBorder.RAISED,
            Color.white, new Color(148, 145, 140));
    Border border1b = new TitledBorder(border1, Language.getString("Color"));
    JCheckBox cbEach = new JCheckBox();
    ButtonColor bColor = new ButtonColor();
    JButton jButton1 = new JButton();
    JButton jButton2 = new JButton();
    JCheckBox cbDark3D = new JCheckBox();
    JCheckBox cbRelative = new JCheckBox();
    JLabel jLabel2 = new JLabel();
    JLabel jLabel3 = new JLabel();
    JLabel jLabel4 = new JLabel();
    JSpinner spDepth = new JSpinner();
    JSpinner spWidth = new JSpinner();
    JSpinner spOffset = new JSpinner();
    JCheckBox cbSideMargins = new JCheckBox();
    JCheckBox cbAutoMark = new JCheckBox();
    ButtonPen bTickLines = new ButtonPen();

    public BarEditor() {
        try {
            jbInit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        cbStyle.addItem(Language.getString("Rectangle"));
        cbStyle.addItem(Language.getString("Pyramid"));
        cbStyle.addItem(Language.getString("Invert_Pyramid"));
        cbStyle.addItem(Language.getString("Cylinder"));
        cbStyle.addItem(Language.getString("Ellipse"));
        cbStyle.addItem(Language.getString("Arrow"));
        cbStyle.addItem(Language.getString("Rect_Gradient"));
        cbStyle.addItem(Language.getString("Cone"));
        cbStyle.addItem(Language.getString("Invert_Arrow"));
        cbStyle.addItem(Language.getString("Invert_Cone"));
        jPanelf.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
        jPanelf.setBounds(4, 3, 288, 74);
        
        jPanel1.add(jPanelf);

    }

    public void setSeries(Series series, JTabbedPane tabs) {
        bar = (CustomBar) series;
        bBorder.setPen(bar.getPen());
        cbStyle.setSelectedIndex(bar.getBarStyle().getValue());
        bColor.setColor(bar.getColor());
        cbEach.setSelected(bar.getColorEach());
        cbDark3D.setSelected(bar.getDark3D());
        cbRelative.setSelected(bar.getGradientRelative());
        cbSideMargins.setSelected(bar.getSideMargins());
        cbAutoMark.setSelected(bar.getAutoMarkPosition());
        bTickLines.setPen(bar.getTickLines());

        spDepth.setValue(new Integer(bar.getDepthPercent()));
        spWidth.setValue(new Integer(bar.getCustomBarWidth()));
        spOffset.setValue(new Integer(bar.getOffsetPercent()));

        tabs.add(new StackBarEditor(bar), Language.getString("Stack"), 0);
    }

    private void jbInit() throws Exception {
        this.setLayout(borderLayout1);
        jPanel1.setLayout(null);
        jLabel1.setDisplayedMnemonic(Language.getString("S").charAt(0));
        jLabel1.setLabelFor(cbStyle);
        jLabel1.setText(Language.getString("Style"));
        jLabel1.setBounds(new Rectangle(12, 167, 68, 14));
        cbStyle.setBounds(new Rectangle(12, 190, 110, 21));
        cbStyle.addActionListener(new BarEditor_cbStyle_actionAdapter(this));
        bBorder.setBounds(new Rectangle(175, 13, 95, 23));
        bBorder.setMnemonic(Language.getString("B").charAt(0));
        bBorder.setText(Language.getString("Border"));
        bTickLines.setBounds(new Rectangle(175, 43, 95, 23));
        bTickLines.setMnemonic(Language.getString("B").charAt(0));
        bTickLines.setText(Language.getString("TickLines"));
        jPanel3.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), "Color", TitledBorder.LEADING, TitledBorder.TOP, null, null));
        jPanel3.setBounds(new Rectangle(163, 79, 129, 80));
        jPanel3.setLayout(null);
        cbEach.setMnemonic(Language.getString("E").charAt(0));
        cbEach.setText(Language.getString("Color_each"));
        cbEach.setBounds(new Rectangle(6, 18, 101, 23));
        cbEach.addActionListener(new BarEditor_cbEach_actionAdapter(this));
        bColor.setBounds(new Rectangle(10, 45, 77, 23));
        bColor.setMnemonic(Language.getString("C").charAt(0));
        bColor.setText(Language.getString("Color"));
        bColor.addActionListener(new BarEditor_bColor_actionAdapter(this));
        jButton1.setBounds(new Rectangle(12, 88, 95, 23));
        jButton1.setMnemonic(Language.getString("G").charAt(0));
        jButton1.setText(Language.getString("Gradient"));
        jButton1.addActionListener(new BarEditor_jButton1_actionAdapter(this));
        jButton2.setBounds(new Rectangle(64, 13, 95, 23));
        jButton2.setText(Language.getString("Pattern"));
        jButton2.addActionListener(new BarEditor_jButton2_actionAdapter(this));
        cbDark3D.setMnemonic(Language.getString("D").charAt(0));
        cbDark3D.setText(Language.getString("Dark_Bar_3D_sides"));
        cbDark3D.setBounds(new Rectangle(173, 163, 137, 23));
        cbDark3D.addActionListener(new BarEditor_cbDark3D_actionAdapter(this));
        cbRelative.setMnemonic(Language.getString("R").charAt(0));
        cbRelative.setText(Language.getString("Gradient_Relative"));
        cbRelative.setBounds(new Rectangle(12, 119, 131, 23));
        cbRelative.addActionListener(new BarEditor_cbRelative_actionAdapter(this));
        jPanel2.setLayout(null);
        jLabel2.setHorizontalAlignment(SwingConstants.TRAILING);
        jLabel2.setLabelFor(spDepth);
        jLabel2.setText(Language.getString("_Bar_Depth"));
        jLabel2.setBounds(new Rectangle(4, 13, 104, 14));
        jLabel3.setHorizontalAlignment(SwingConstants.TRAILING);
        jLabel3.setLabelFor(spWidth);
        jLabel3.setText(Language.getString("_Bar_Width"));
        jLabel3.setBounds(new Rectangle(4, 42, 104, 14));
        jLabel4.setHorizontalAlignment(SwingConstants.TRAILING);
        jLabel4.setLabelFor(spOffset);
        jLabel4.setText(Language.getString("_Bar_Offset"));
        jLabel4.setBounds(new Rectangle(4, 70, 104, 14));
        spDepth.setBounds(new Rectangle(115, 10, 50, 20));
        spDepth.addChangeListener(new BarEditor_spDepth_changeAdapter(this));
        spWidth.setBounds(new Rectangle(115, 39, 50, 20));
        spWidth.addChangeListener(new BarEditor_spWidth_changeAdapter(this));
        spOffset.setBounds(new Rectangle(115, 67, 50, 20));
        spOffset.addChangeListener(new BarEditor_spOffset_changeAdapter(this));
        cbSideMargins.setMnemonic(Language.getString("I").charAt(0));
        cbSideMargins.setText(Language.getString("Bar_Side_Margins"));
        cbSideMargins.setBounds(new Rectangle(53, 102, 151, 23));
        cbSideMargins.addActionListener(new
                                        BarEditor_cbSideMargins_actionAdapter(this));
        cbAutoMark.setMnemonic(Language.getString("A").charAt(0));
        cbAutoMark.setText(Language.getString("Auto_Mark_position"));
        cbAutoMark.setBounds(new Rectangle(53, 126, 172, 23));
        cbAutoMark.addActionListener(new BarEditor_cbAutoMark_actionAdapter(this));
        this.add(jTabbedPane1, java.awt.BorderLayout.CENTER);
        jTabbedPane1.add(jPanel1, Language.getString("Format"));
        jPanel1.add(jLabel1);
        jPanel1.add(cbStyle);
        jPanel1.add(jPanel3);
        jPanel1.add(cbRelative);
        jPanel1.add(cbDark3D);
        jPanelf.setLayout(null);
        jPanelf.add(jButton2);
        jPanelf.add(bBorder);
        jPanelf.add(bTickLines);
        jPanel1.add(jButton1);        
        jPanel3.add(cbEach);
        jPanel3.add(bColor);
        jTabbedPane1.add(jPanel2, Language.getString("Size"));
        jPanel2.add(spDepth);
        jPanel2.add(spWidth);
        jPanel2.add(spOffset);
        jPanel2.add(jLabel4);
        jPanel2.add(jLabel3);
        jPanel2.add(jLabel2);
        jPanel2.add(cbSideMargins);
        jPanel2.add(cbAutoMark);        
    }

    public void cbStyle_actionPerformed(ActionEvent e) {
        if (bar!=null) {
            bar.setBarStyle(BarStyle.fromValue(cbStyle.getSelectedIndex()));
        }
    }

    public void cbEach_actionPerformed(ActionEvent e) {
        bar.setColorEach(cbEach.isSelected());
    }

    public void bColor_actionPerformed(ActionEvent e) {
        bar.setColor(bColor.getColor());
    }

    public void jButton1_actionPerformed(ActionEvent e) {
        GradientEditor.edit(this, bar.getGradient());
    }

    public void jButton2_actionPerformed(ActionEvent e) {
        BrushEditor.edit(this, bar.getBrush());
    }

    public void cbDark3D_actionPerformed(ActionEvent e) {
        bar.setDark3D(cbDark3D.isSelected());
    }

    public void cbRelative_actionPerformed(ActionEvent e) {
        bar.setGradientRelative(cbRelative.isSelected());
    }

    public void cbSideMargins_actionPerformed(ActionEvent e) {
        bar.setSideMargins(cbSideMargins.isSelected());
    }

    public void cbAutoMark_actionPerformed(ActionEvent e) {
        bar.setAutoMarkPosition(cbAutoMark.isSelected());
    }

    public void spDepth_stateChanged(ChangeEvent e) {
        bar.setDepthPercent(((Integer)spDepth.getValue()).intValue());
    }

    public void spWidth_stateChanged(ChangeEvent e) {
        bar.setCustomBarWidth(((Integer)spWidth.getValue()).intValue());
    }

    public void spOffset_stateChanged(ChangeEvent e) {
        bar.setOffsetPercent(((Integer)spOffset.getValue()).intValue());
    }
}


final class BarEditor_spOffset_changeAdapter implements ChangeListener {
    private BarEditor adaptee;
    BarEditor_spOffset_changeAdapter(BarEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void stateChanged(ChangeEvent e) {
        adaptee.spOffset_stateChanged(e);
    }
}


final class BarEditor_spWidth_changeAdapter implements ChangeListener {
    private BarEditor adaptee;
    BarEditor_spWidth_changeAdapter(BarEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void stateChanged(ChangeEvent e) {
        adaptee.spWidth_stateChanged(e);
    }
}


final class BarEditor_spDepth_changeAdapter implements ChangeListener {
    private BarEditor adaptee;
    BarEditor_spDepth_changeAdapter(BarEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void stateChanged(ChangeEvent e) {
        adaptee.spDepth_stateChanged(e);
    }
}


final class BarEditor_cbAutoMark_actionAdapter implements ActionListener {
    private BarEditor adaptee;
    BarEditor_cbAutoMark_actionAdapter(BarEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.cbAutoMark_actionPerformed(e);
    }
}


final class BarEditor_cbSideMargins_actionAdapter implements ActionListener {
    private BarEditor adaptee;
    BarEditor_cbSideMargins_actionAdapter(BarEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.cbSideMargins_actionPerformed(e);
    }
}


final class BarEditor_cbRelative_actionAdapter implements ActionListener {
    private BarEditor adaptee;
    BarEditor_cbRelative_actionAdapter(BarEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.cbRelative_actionPerformed(e);
    }
}


final class BarEditor_cbDark3D_actionAdapter implements ActionListener {
    private BarEditor adaptee;
    BarEditor_cbDark3D_actionAdapter(BarEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.cbDark3D_actionPerformed(e);
    }
}


final class BarEditor_jButton2_actionAdapter implements ActionListener {
    private BarEditor adaptee;
    BarEditor_jButton2_actionAdapter(BarEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.jButton2_actionPerformed(e);
    }
}


final class BarEditor_jButton1_actionAdapter implements ActionListener {
    private BarEditor adaptee;
    BarEditor_jButton1_actionAdapter(BarEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.jButton1_actionPerformed(e);
    }
}


final class BarEditor_bColor_actionAdapter implements ActionListener {
    private BarEditor adaptee;
    BarEditor_bColor_actionAdapter(BarEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.bColor_actionPerformed(e);
    }
}


final class BarEditor_cbEach_actionAdapter implements ActionListener {
    private BarEditor adaptee;
    BarEditor_cbEach_actionAdapter(BarEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.cbEach_actionPerformed(e);
    }
}


final class BarEditor_cbStyle_actionAdapter implements ActionListener {
    private BarEditor adaptee;
    BarEditor_cbStyle_actionAdapter(BarEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.cbStyle_actionPerformed(e);
    }
}

