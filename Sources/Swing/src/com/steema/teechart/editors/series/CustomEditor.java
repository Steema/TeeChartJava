package com.steema.teechart.editors.series;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import java.awt.Rectangle;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.steema.teechart.styles.Series;
import com.steema.teechart.styles.CustomStack;
import com.steema.teechart.styles.Custom;
import com.steema.teechart.editors.ButtonPen;
import com.steema.teechart.editors.BaseSeriesEditor;
import com.steema.teechart.editors.ButtonColor;
import com.steema.teechart.editors.BrushEditor;
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
public class CustomEditor extends BaseSeriesEditor {


    public CustomEditor() {
        super();
        try {
            jbInit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        cbStack.addItem(Language.getString("None"));
        cbStack.addItem(Language.getString("Overlap"));
        cbStack.addItem(Language.getString("Stack"));
        cbStack.addItem(Language.getString("Stack"));
    }

    private com.steema.teechart.styles.ContourLevel contourLevel;

    public void setSeries(Series series, JTabbedPane tabs) {
        custom = (Custom) series;

        cbDark3D.setSelected(custom.getDark3D());
        cbColorEach.setSelected(custom.getColorEach());
        cbClick.setSelected(custom.getClickableLine());
        cbStairs.setSelected(custom.getStairs());
        cbInvStairs.setSelected(custom.getInvertedStairs());
        cbInvStairs.setEnabled(custom.getStairs());
        spHeight.setValue(new Integer(custom.getLineHeight()));
        cbColorEachLine.setSelected(custom.getColorEachLine());
        cbStack.setSelectedIndex((int)custom.getStacked().getValue());
        spTransparency.setValue(new Integer(custom.getTransparency()));
        bLineColor.setColor(custom.getColor());
        bOutline.setPen(custom.getOutLine());
        bLineBorder.setPen(custom.getLinePen());
    }

    private void jbInit() throws Exception {
        this.setLayout(null);
        jPanel3.setBorder(border11);
        jPanel3.setBounds(new Rectangle(228, 10, 110, 77));
        jPanel3.setLayout(null);
        cbDark3D.setMnemonic(Language.getString("D").charAt(0));
        cbDark3D.setText(Language.getString("Dark_3D"));
        cbDark3D.setBounds(new Rectangle(96, 8, 122, 22));
        cbDark3D.addActionListener(new CustomEditor_cbDark3D_actionAdapter(this));
        cbClick.setMnemonic(Language.getString("K").charAt(0));
        cbClick.setText(Language.getString("Clickable"));
        cbClick.setBounds(new Rectangle(96, 52, 122, 22));
        cbClick.addActionListener(new CustomEditor_cbClick_actionAdapter(this));
        cbColorEach.setMnemonic(Language.getString("E").charAt(0));
        cbColorEach.setText(Language.getString("color_each"));
        cbColorEach.setBounds(new Rectangle(96, 30, 122, 22));
        cbColorEach.addActionListener(new CustomEditor_cbColorEach_actionAdapter(this));
        cbStairs.setMnemonic(Language.getString("S").charAt(0));
        cbStairs.setText(Language.getString("Stairs"));
        cbStairs.setBounds(new Rectangle(11, 18, 80, 23));
        cbStairs.addActionListener(new CustomEditor_cbStairs_actionAdapter(this));
        bLineBorder.setBounds(new Rectangle(10, 9, 76, 23));
        bLineBorder.setMnemonic(Language.getString("B").charAt(0));
        bLineBorder.setText(Language.getString("Border"));
        spHeight.setToolTipText("");
        spHeight.setBounds(new Rectangle(93, 103, 46, 23));
        spHeight.addChangeListener(new CustomEditor_spHeight_changeAdapter(this));
        spTransparency.setToolTipText("");
        spTransparency.setBounds(new Rectangle(93, 162, 46, 23));
        spTransparency.addChangeListener(new CustomEditor_spTransparency_changeAdapter(this));
        jLabel4.setDisplayedMnemonic(Language.getString("O").charAt(0));
        jLabel4.setHorizontalAlignment(SwingConstants.TRAILING);
        jLabel4.setLabelFor(spHeight);
        jLabel4.setText(Language.getString("Height_3D"));
        jLabel4.setBounds(new Rectangle( -8, 107, 93, 14));
        cbColorEachLine.setMnemonic(Language.getString("N").charAt(0));
        cbColorEachLine.setText(Language.getString("Color_each_line"));
        cbColorEachLine.setBounds(new Rectangle(96, 74, 122, 22));
        cbColorEachLine.addActionListener(new CustomEditor_cbColorEachLine_actionAdapter(this));
        jLabel1.setDisplayedMnemonic(Language.getString("O").charAt(0));
        jLabel1.setHorizontalAlignment(SwingConstants.TRAILING);
        jLabel1.setText(Language.getString("Height"));
        jLabel1.setBounds(new Rectangle(123, 271, 51, 14));
        cbInvStairs.setMnemonic(Language.getString("I").charAt(0));
        cbInvStairs.setText(Language.getString("Inverted"));
        cbInvStairs.setBounds(new Rectangle(11, 43, 87, 23));
        cbInvStairs.addActionListener(new CustomEditor_cbInvStairs_actionAdapter(this));
        bLineColor.setBounds(new Rectangle(11, 40, 74, 23));
        bLineColor.setMnemonic(Language.getString("C").charAt(0));
        bLineColor.setText(Language.getString("Color"));
        bLineColor.addActionListener(new CustomEditor_bLineColor_actionAdapter(this));
        bBrush.setBounds(new Rectangle(11, 71, 74, 23));
        bBrush.setMnemonic(Language.getString("P").charAt(0));
        bBrush.setText(Language.getString("Pattern"));
        bBrush.addActionListener(new CustomEditor_bBrush_actionAdapter(this));
        bOutline.setBounds(new Rectangle(240, 92, 85, 23));
        bOutline.setMnemonic(Language.getString("O").charAt(0));
        bOutline.setText(Language.getString("Outline"));
        jLabel2.setDisplayedMnemonic(Language.getString("O").charAt(0));
        jLabel2.setHorizontalAlignment(SwingConstants.TRAILING);
        jLabel2.setLabelFor(spTransparency);
        jLabel2.setText(Language.getString("Transparency"));
        jLabel2.setBounds(new Rectangle(5, 167, 81, 14));
        jLabel3.setDisplayedMnemonic(Language.getString("O").charAt(0));
        jLabel3.setHorizontalAlignment(SwingConstants.TRAILING);
        jLabel3.setLabelFor(spHeight);
        jLabel3.setText(Language.getString("Transparency"));
        jLabel3.setBounds(new Rectangle( -9, 154, 93, 14));
        jLabel5.setDisplayedMnemonic(Language.getString("O").charAt(0));
        jLabel5.setHorizontalAlignment(SwingConstants.TRAILING);
        jLabel5.setLabelFor(cbStack);
        jLabel5.setText(Language.getString("Stack"));
        jLabel5.setBounds(new Rectangle( -8, 136, 93, 14));
        cbStack.setBounds(new Rectangle(92, 133, 110, 22));
        cbStack.addActionListener(new CustomEditor_cbStack_actionAdapter(this));
        jPanel3.add(jLabel1);
        jPanel3.add(cbStairs);
        jPanel3.add(jSpinner1);
        jPanel3.add(cbInvStairs);
        this.add(bLineColor);
        this.add(cbColorEach);
        this.add(cbClick);
        this.add(cbColorEachLine);
        this.add(jLabel5);
        this.add(cbStack);
        this.add(spTransparency);
        this.add(jLabel2);
        this.add(bLineBorder);
        this.add(cbDark3D);
        this.add(spHeight);
        this.add(jLabel4);
        this.add(bBrush);
        this.add(bOutline);
        this.add(jPanel3);
    }

    Border border1 = BorderFactory.createEtchedBorder(Color.white,
            new Color(148, 145, 140));
    Border border2 = new TitledBorder(border1, Language.getString("custom"));
    private Custom custom;
    Border border3 = BorderFactory.createEtchedBorder(EtchedBorder.RAISED,
            Color.white, new Color(148, 145, 140));
    Border border4 = new TitledBorder(border3, Language.getString("Multiple_customs"));
    JPanel jPanel3 = new JPanel();
    Border border5 = BorderFactory.createEtchedBorder(Color.white,
            new Color(148, 145, 140));
    Border border6 = new TitledBorder(border5, Language.getString("Color"));
    JCheckBox cbStairs = new JCheckBox();
    Border border7 = BorderFactory.createEtchedBorder(EtchedBorder.RAISED,
            Color.white, new Color(148, 145, 140));
    ButtonPen bLineBorder = new ButtonPen();
    JSpinner spHeight = new JSpinner();
    JLabel jLabel4 = new JLabel();
    JCheckBox cbColorEachLine = new JCheckBox();
    Border border8 = BorderFactory.createEtchedBorder(Color.white,
            new Color(148, 145, 140));
    Border border9 = new TitledBorder(border8, Language.getString("Levels"));
    JLabel jLabel1 = new JLabel();
    JSpinner jSpinner1 = new JSpinner();
    JCheckBox cbInvStairs = new JCheckBox();
    JTextField eValue = new JTextField();
    ButtonColor bLineColor = new ButtonColor();
    JButton bBrush = new JButton();
    JCheckBox cbDark3D = new JCheckBox();
    JCheckBox cbClick = new JCheckBox();
    JCheckBox cbColorEach = new JCheckBox();
    ButtonPen bOutline = new ButtonPen();
    Border border10 = BorderFactory.createEtchedBorder(Color.white,
            new Color(148, 145, 140));
    Border border11 = new TitledBorder(border10, Language.getString("Line_mode"));
    JLabel jLabel2 = new JLabel();
    JSpinner spTransparency = new JSpinner();
    JLabel jLabel3 = new JLabel();
    JLabel jLabel5 = new JLabel();
    JComboBox cbStack = new JComboBox();

    public void cbDark3D_actionPerformed(ActionEvent e) {
        custom.setDark3D(cbDark3D.isSelected());
    }

    public void cbColorEach_actionPerformed(ActionEvent e) {
        custom.setColorEach(cbInvStairs.isSelected());
    }
    
    public void cbClick_actionPerformed(ActionEvent e) {
        custom.setClickableLine(cbClick.isSelected());
    }

    public void cbColorEachLine_actionPerformed(ActionEvent e) {
        custom.setColorEachLine(cbColorEachLine.isSelected());
    }

    public void cbStairs_actionPerformed(ActionEvent e) {
        custom.setStairs(cbStairs.isSelected());
        cbInvStairs.setEnabled(custom.getStairs());
    }

    public void cbInvStairs_actionPerformed(ActionEvent e) {
        custom.setInvertedStairs(cbInvStairs.isSelected());
    }

    public void bBrush_actionPerformed(ActionEvent e) {
        BrushEditor.edit(this, custom.getBrush());
    }

    public void spHeight_stateChanged(ChangeEvent e) {
        custom.setLineHeight(((Integer)spHeight.getValue()).intValue());
    }

    public void cbStack_actionPerformed(ActionEvent e) {
        if (custom!=null) {
            custom.setStacked(CustomStack.fromInt(cbStack.getSelectedIndex()));
        }
    }

    public void bLineColor_actionPerformed(ActionEvent e) {
        custom.setColor(bLineColor.getColor());
    }

    public void spTransparency_stateChanged(ChangeEvent e) {
        custom.setTransparency(((Integer)spTransparency.getValue()).intValue());
    }
}

class CustomEditor_cbDark3D_actionAdapter implements ActionListener {
    private CustomEditor adaptee;
    CustomEditor_cbDark3D_actionAdapter(CustomEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.cbDark3D_actionPerformed(e);
    }
}

class CustomEditor_cbClick_actionAdapter implements ActionListener {
    private CustomEditor adaptee;
    CustomEditor_cbClick_actionAdapter(CustomEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.cbClick_actionPerformed(e);
    }
}

class CustomEditor_cbColorEach_actionAdapter implements ActionListener {
    private CustomEditor adaptee;
    CustomEditor_cbColorEach_actionAdapter(CustomEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.cbColorEach_actionPerformed(e);
    }
}

class CustomEditor_cbColorEachLine_actionAdapter implements ActionListener {
    private CustomEditor adaptee;
    CustomEditor_cbColorEachLine_actionAdapter(CustomEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.cbColorEachLine_actionPerformed(e);
    }
}

class CustomEditor_cbStairs_actionAdapter implements ActionListener {
    private CustomEditor adaptee;
    CustomEditor_cbStairs_actionAdapter(CustomEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.cbStairs_actionPerformed(e);
    }
}

class CustomEditor_cbInvStairs_actionAdapter implements ActionListener {
    private CustomEditor adaptee;
    CustomEditor_cbInvStairs_actionAdapter(CustomEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.cbInvStairs_actionPerformed(e);
    }
}

class CustomEditor_spTransparency_changeAdapter implements ChangeListener {
    private CustomEditor adaptee;
    CustomEditor_spTransparency_changeAdapter(CustomEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void stateChanged(ChangeEvent e) {
        adaptee.spTransparency_stateChanged(e);
    }
}

class CustomEditor_spHeight_changeAdapter implements ChangeListener {
    private CustomEditor adaptee;
    CustomEditor_spHeight_changeAdapter(CustomEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void stateChanged(ChangeEvent e) {
        adaptee.spHeight_stateChanged(e);
    }
}

final class CustomEditor_bLineColor_actionAdapter implements ActionListener {
    private CustomEditor adaptee;
    CustomEditor_bLineColor_actionAdapter(CustomEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.bLineColor_actionPerformed(e);
    }
}

final class CustomEditor_bBrush_actionAdapter implements ActionListener {
    private CustomEditor adaptee;
    CustomEditor_bBrush_actionAdapter(CustomEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.bBrush_actionPerformed(e);
    }
}

final class CustomEditor_cbStack_actionAdapter implements ActionListener {
    private CustomEditor adaptee;
    CustomEditor_cbStack_actionAdapter(CustomEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.cbStack_actionPerformed(e);
    }
}


