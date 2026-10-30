package com.steema.teechart.editors.series;

/* Some lines to uncomment */
import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.border.TitledBorder;
import javax.swing.border.Border;

import java.awt.BorderLayout;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Color;

import com.steema.teechart.styles.SeriesMarks;
import com.steema.teechart.styles.MarksStyle;
import com.steema.teechart.editors.ButtonPen;
import com.steema.teechart.editors.ButtonColor;
import com.steema.teechart.styles.ArrowHeadStyle;
import com.steema.teechart.languages.Language;

/**
 * <p>Title: SeriesMarksEditor class</p>
 *
 * <p>Description: Series Marks Editor</p>
 *
 * <p>Copyright (c) 2004-2019 by Steema Software SL. All Rights Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 *
 */
public class SeriesMarksEditor extends JPanel {

    private SeriesMarks marks;
    JTabbedPane jTabbedPane1 = new JTabbedPane();
    BorderLayout borderLayout1 = new BorderLayout();
    JPanel jPanel1 = new JPanel();
    JPanel jPanel2 = new JPanel();
    JPanel jPanel3 = new JPanel();
    JLabel jLabel1 = new JLabel();
    JComboBox cbHead = new JComboBox();
    ButtonPen bMarkLinCol = new ButtonPen();
    Border border1 = BorderFactory.createEtchedBorder(EtchedBorder.RAISED,
            Color.white, new Color(148, 145, 140));
    JButton bCalloutPointer = new JButton();
    JPanel jPanel4 = new JPanel();
    JLabel jLabel5 = new JLabel();
    JComboBox jComboBox1 = new JComboBox();
    ButtonPen buttonPen1 = new ButtonPen();
    JPanel jPanel5 = new JPanel();
    JCheckBox jCheckBox1 = new JCheckBox();
    ButtonColor buttonColor1 = new ButtonColor();
    JButton jButton3 = new JButton();
    JButton jButton4 = new JButton();
    JCheckBox jCheckBox2 = new JCheckBox();
    JCheckBox jCheckBox3 = new JCheckBox();
    JPanel jPanel6 = new JPanel();
    JComboBox cbStyle = new JComboBox();
    JPanel jPanel7 = new JPanel();
    JCheckBox cbVisible = new JCheckBox();
    ButtonColor buttonColor2 = new ButtonColor();
    JCheckBox cbClipped = new JCheckBox();
    JCheckBox cbMultiLine = new JCheckBox();
    JCheckBox cbAllVisible = new JCheckBox();
    JLabel jLabel7 = new JLabel();
    JLabel jLabel8 = new JLabel();
    JSpinner spDrawEvery = new JSpinner();
    JLabel jLabel6 = new JLabel();
    JSpinner spAngle = new JSpinner();
    JSpinner spLength = new JSpinner();
    JLabel jLabel9 = new JLabel();
    JSpinner spHeadSize = new JSpinner();
    JLabel jLabel10 = new JLabel();
    JSpinner spArrowDist = new JSpinner();
    JLabel jLabel11 = new JLabel();
    JSpinner spArrowLength = new JSpinner();
    JLabel jLabel12 = new JLabel();

    public SeriesMarksEditor() {
        try {
            jbInit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        cbStyle.addItem(Language.getString("Value"));
        cbStyle.addItem(Language.getString("Percent"));
        cbStyle.addItem(Language.getString("Label"));
        cbStyle.addItem(Language.getString("Label_and_Percent"));
        cbStyle.addItem(Language.getString("Label_and_Value"));
        cbStyle.addItem(Language.getString("Legend"));
        cbStyle.addItem(Language.getString("Percent_Total"));
        cbStyle.addItem(Language.getString("Label_and_Percent"));
        cbStyle.addItem(Language.getString("Xvalue"));
        cbStyle.addItem(Language.getString("Xand_Y_values"));

        cbHead.addItem(Language.getString("Rectangle"));
        cbHead.addItem(Language.getString("Pyramid"));
        cbHead.addItem(Language.getString("Invert_Pyramid"));
        cbHead.addItem(Language.getString("Cylinder"));
        cbHead.addItem(Language.getString("Ellipse"));
        cbHead.addItem(Language.getString("Arrow"));
        cbHead.addItem(Language.getString("Rect_Gradient"));
        cbHead.addItem(Language.getString("Cone"));
        cbHead.addItem(Language.getString("Invert_Arrow"));
        cbHead.addItem(Language.getString("Invert_Cone"));
    }

    public void setSeries(SeriesMarks series, JTabbedPane tabs) {

        marks = (SeriesMarks) series;

        cbVisible.setSelected(marks.getVisible());
        cbClipped.setSelected(marks.getClip());
        cbMultiLine.setSelected(marks.getMultiLine());
        spDrawEvery.setValue(new Integer(marks.getDrawEvery()));
        spAngle.setValue(new Double(marks.getAngle()));
        spLength.setValue(new Integer(marks.getArrowLength()));
        cbAllVisible.setSelected(marks.allSeriesVisible());
        bMarkLinCol.setPen(marks.getArrow());

        //These properties are now obsolete and managed by the Callout
        bMarkLinCol.setVisible(false);
        spLength.setVisible(false);
        jLabel6.setVisible(false);
        jPanel7.setVisible(false);

        switch (marks.getStyle().getValue()) {
        case 0:
            cbStyle.setSelectedIndex(0);
            break;
        case 1:
            cbStyle.setSelectedIndex(1);
            break;
        case 2:
            cbStyle.setSelectedIndex(2);
            break;
        case 3:
            cbStyle.setSelectedIndex(3);
            break;
        case 4:
            cbStyle.setSelectedIndex(4);
            break;
        case 5:
            cbStyle.setSelectedIndex(5);
            break;
        case 6:
            cbStyle.setSelectedIndex(6);
            break;
        case 7:
            cbStyle.setSelectedIndex(7);
            break;
        case 8:
            cbStyle.setSelectedIndex(8);
            break;
        case 9:
            cbStyle.setSelectedIndex(9);
            break;
        }

        bMarkLinCol.setPen(marks.getCallout().getArrow());
        spArrowDist.setValue(new Integer(marks.getCallout().getDistance()));
        cbHead.setSelectedIndex(marks.getCallout().getArrowHead().getValue());
        spHeadSize.setValue(new Integer(marks.getCallout().getArrowHeadSize()));
        spArrowLength.setValue(new Integer(marks.getCallout().getLength()));
    }

    private void jbInit() throws Exception {
        border1 = new TitledBorder(BorderFactory.createEtchedBorder(
                EtchedBorder.RAISED, Color.white, new Color(148, 145, 140)),
                                   Language.getString("Lines"));
        this.setLayout(borderLayout1);
        jPanel1.setLayout(null);
        jLabel1.setDisplayedMnemonic(Language.getString("A").charAt(0));
        jLabel1.setLabelFor(cbHead);
        jLabel1.setText(Language.getString("Arrow_Head"));
        jLabel1.setBounds(new Rectangle(22, 69, 68, 14));
        cbHead.setBounds(new Rectangle(22, 87, 110, 22));
        cbHead.addActionListener(new SeriesMarksEditor_cbHead_actionAdapter(this));
        bMarkLinCol.setBounds(new Rectangle(8, 26, 91, 23));
        bMarkLinCol.setMnemonic(Language.getString("B").charAt(0));
        bMarkLinCol.setText(Language.getString("Border"));
        bCalloutPointer.setBounds(new Rectangle(128, 26, 95, 23));
        bCalloutPointer.setText(Language.getString("Pointer"));
        bCalloutPointer.addActionListener(new
                                          SeriesMarksEditor_bCalloutPointer_actionAdapter(this));
        jPanel2.setLayout(null);
        jPanel3.setLayout(null);
        jPanel4.setBounds(new Rectangle(0, 0, 10, 10));
        jLabel5.setDisplayedMnemonic(Language.getString("S").charAt(0));
        jLabel5.setLabelFor(cbHead);
        jLabel5.setText(Language.getString("Style"));
        jLabel5.setBounds(new Rectangle(8, 13, 68, 14));
        jComboBox1.setBounds(new Rectangle(8, 31, 110, 22));
        buttonPen1.setBounds(new Rectangle(8, 63, 91, 23));
        buttonPen1.setMnemonic(Language.getString("B").charAt(0));
        buttonPen1.setText(Language.getString("Border"));
        jPanel5.setBorder(border1);
        jPanel5.setBounds(new Rectangle(8, 97, 110, 64));
        jCheckBox1.setMnemonic(Language.getString("E").charAt(0));
        jCheckBox1.setText(Language.getString("Color_each"));
        jCheckBox1.setBounds(new Rectangle(6, 6, 81, 23));
        buttonColor1.setBounds(new Rectangle(10, 29, 77, 23));
        buttonColor1.setMnemonic(Language.getString("C").charAt(0));
        buttonColor1.setText(Language.getString("Color"));
        jButton3.setBounds(new Rectangle(139, 26, 95, 23));
        jButton3.setText(Language.getString("Pattern"));
        jButton4.setBounds(new Rectangle(139, 58, 95, 23));
        jButton4.setMnemonic(Language.getString("G").charAt(0));
        jButton4.setText(Language.getString("Gradient"));
        jCheckBox2.setMnemonic(Language.getString("R").charAt(0));
        jCheckBox2.setText(Language.getString("Gradient_Relative"));
        jCheckBox2.setBounds(new Rectangle(139, 91, 131, 23));
        jCheckBox3.setMnemonic(Language.getString("D").charAt(0));
        jCheckBox3.setText(Language.getString("Dark_Bar_3D_sides"));
        jCheckBox3.setBounds(new Rectangle(139, 117, 114, 23));
        cbStyle.setBounds(new Rectangle(177, 37, 101, 22));
        cbStyle.addActionListener(new SeriesMarksEditor_cbStyle_actionAdapter(this));
        jPanel7.setBorder(border1);
        jPanel7.setBounds(new Rectangle(10, 137, 274, 50));
        jPanel7.setLayout(null);
        cbVisible.setMnemonic(Language.getString("V").charAt(0));
        cbVisible.setText(Language.getString("Visible"));
        cbVisible.setBounds(new Rectangle(10, 8, 77, 23));
        cbVisible.addActionListener(new
                                    SeriesMarksEditor_cbVisible_actionAdapter(this));
        buttonColor2.setBounds(new Rectangle(25, 17, 71, 23));
        buttonColor2.setMnemonic(Language.getString("C").charAt(0));
        buttonColor2.setText(Language.getString("Color"));
        jPanel6.setLayout(null);
        cbClipped.setMnemonic(Language.getString("D").charAt(0));
        cbClipped.setText(Language.getString("Clipped"));
        cbClipped.setBounds(new Rectangle(10, 36, 77, 23));
        cbClipped.addActionListener(new
                                    SeriesMarksEditor_cbClipped_actionAdapter(this));
        cbMultiLine.setMnemonic(Language.getString("M").charAt(0));
        cbMultiLine.setText(Language.getString("Multi_line"));
        cbMultiLine.setBounds(new Rectangle(10, 64, 77, 23));
        cbAllVisible.setMnemonic(Language.getString("A").charAt(0));
        cbAllVisible.setText(Language.getString("All_Series_Visible"));
        cbAllVisible.setBounds(new Rectangle(112, 8, 127, 23));
        cbAllVisible.addActionListener(new
                                       SeriesMarksEditor_cbAllVisible_actionAdapter(this));
        jLabel7.setDisplayedMnemonic(Language.getString("S").charAt(0));
        jLabel7.setLabelFor(cbStyle);
        jLabel7.setText(Language.getString("Style"));
        jLabel7.setBounds(new Rectangle(139, 35, 28, 24));
        jLabel8.setDisplayedMnemonic(Language.getString("W").charAt(0));
        jLabel8.setHorizontalAlignment(SwingConstants.TRAILING);
        jLabel8.setLabelFor(spDrawEvery);
        jLabel8.setText(Language.getString("Draw_every"));
        jLabel8.setBounds(new Rectangle(2, 102, 92, 14));
        spDrawEvery.setBounds(new Rectangle(102, 97, 50, 22));
        spDrawEvery.addChangeListener(new
                                      SeriesMarksEditor_spDrawEvery_changeAdapter(this));
        jLabel6.setDisplayedMnemonic(Language.getString("L").charAt(0));
        jLabel6.setHorizontalAlignment(SwingConstants.TRAILING);
        jLabel6.setLabelFor(spLength);
        jLabel6.setText(Language.getString("Length"));
        jLabel6.setBounds(new Rectangle(148, 22, 48, 14));
        spAngle.setBounds(new Rectangle(229, 97, 50, 23));
        spAngle.addChangeListener(new SeriesMarksEditor_spAngle_changeAdapter(this));
        spLength.setBounds(new Rectangle(206, 16, 50, 23));
        spLength.addChangeListener(new SeriesMarksEditor_spLength_changeAdapter(this));
        jLabel9.setDisplayedMnemonic(Language.getString("N").charAt(0));
        jLabel9.setHorizontalAlignment(SwingConstants.TRAILING);
        jLabel9.setLabelFor(spAngle);
        jLabel9.setText(Language.getString("Angle"));
        jLabel9.setBounds(new Rectangle(171, 103, 48, 14));
        spHeadSize.setBounds(new Rectangle(82, 120, 50, 23));
        spHeadSize.addChangeListener(new
                                     SeriesMarksEditor_spHeadSize_changeAdapter(this));
        jLabel10.setDisplayedMnemonic(Language.getString("S").charAt(0));
        jLabel10.setHorizontalAlignment(SwingConstants.TRAILING);
        jLabel10.setLabelFor(spHeadSize);
        jLabel10.setText(Language.getString("Size"));
        jLabel10.setBounds(new Rectangle(23, 125, 48, 14));
        spArrowDist.setBounds(new Rectangle(209, 120, 50, 23));
        spArrowDist.addChangeListener(new
                                      SeriesMarksEditor_spArrowDist_changeAdapter(this));
        jLabel11.setDisplayedMnemonic(Language.getString("D").charAt(0));
        jLabel11.setHorizontalAlignment(SwingConstants.TRAILING);
        jLabel11.setLabelFor(spArrowDist);
        jLabel11.setText(Language.getString("Distance"));
        jLabel11.setBounds(new Rectangle(151, 125, 48, 14));
        spArrowLength.setBounds(new Rectangle(209, 86, 50, 23));
        spArrowLength.addChangeListener(new
                                        SeriesMarksEditor_spArrowLength_changeAdapter(this));
        jLabel12.setDisplayedMnemonic(Language.getString("L").charAt(0));
        jLabel12.setHorizontalAlignment(SwingConstants.TRAILING);
        jLabel12.setLabelFor(spArrowLength);
        jLabel12.setText(Language.getString("Length"));
        jLabel12.setBounds(new Rectangle(151, 91, 48, 14));
        this.add(jTabbedPane1, java.awt.BorderLayout.CENTER);
        jPanel1.add(jPanel4);
        jPanel4.add(jCheckBox3);
        jPanel4.add(jCheckBox2);
        jPanel4.add(jButton4);
        jPanel4.add(jButton3);
        jPanel4.add(jPanel5);
        jPanel5.add(buttonColor1);
        jPanel5.add(jCheckBox1);
        jPanel4.add(buttonPen1);
        jPanel4.add(jComboBox1);
        jPanel4.add(jLabel5);
        jPanel1.add(bMarkLinCol);
        jPanel1.add(bCalloutPointer);
        jPanel1.add(cbHead);
        jPanel1.add(jLabel1);
        jPanel1.add(spHeadSize);
        jPanel1.add(jLabel10);
        jPanel1.add(spArrowLength);
        jPanel1.add(jLabel12);
        jPanel1.add(jLabel11);
        jPanel1.add(spArrowDist);
        jTabbedPane1.add(jPanel6, Language.getString("Style"));
        jTabbedPane1.add(jPanel1, Language.getString("Arrows"));
        jTabbedPane1.add(jPanel2, Language.getString("Symbol"));
        jTabbedPane1.add(jPanel3, Language.getString("Tail"));
        jPanel6.add(cbMultiLine);
        jPanel6.add(cbClipped);
        jPanel6.add(cbStyle, null);
        jPanel6.add(cbAllVisible);
        jPanel6.add(jPanel7, null);
        jPanel7.add(jLabel6);
        jPanel7.add(spLength);
        jPanel7.add(buttonColor2);
        jPanel6.add(spDrawEvery);
        jPanel6.add(spAngle);
        jPanel6.add(jLabel8);
        jPanel6.add(jLabel9);
        jPanel6.add(jLabel7);
        jPanel6.add(cbVisible);
    }

    public void cbVisible_actionPerformed(ActionEvent e) {
        marks.setVisible(cbVisible.isSelected());
    }

    public void cbAllVisible_actionPerformed(ActionEvent e) {
        for (int i = 0; i < marks.getChart().getSeriesCount(); i++) {
            marks.getChart().getSeries(i).getMarks().setVisible(cbAllVisible.
                    isSelected());
        }
        cbVisible.setSelected(cbAllVisible.isSelected());
    }

    public void spDrawEvery_stateChanged(ChangeEvent e) {
        if (marks != null) {
            marks.setDrawEvery(((Integer) spDrawEvery.getValue()).intValue());
        }
    }

    public void spAngle_stateChanged(ChangeEvent e) {
        if (marks != null) {
            marks.setAngle(((Double) spAngle.getValue()).doubleValue());
        }
    }

    public void spLength_stateChanged(ChangeEvent e) {
        if (marks != null) {
            marks.setArrowLength(((Integer) spLength.getValue()).intValue());
        }
    }

    public void cbClipped_actionPerformed(ActionEvent e) {
        marks.setClip(cbClipped.isSelected());
    }

    public void cbStyle_actionPerformed(ActionEvent e) {
        if (marks != null) {
            switch (cbStyle.getSelectedIndex()) {
            case 0:
                marks.setStyle(MarksStyle.VALUE);
                break;
            case 1:
                marks.setStyle(MarksStyle.PERCENT);
                break;
            case 2:
                marks.setStyle(MarksStyle.LABEL);
                break;
            case 3:
                marks.setStyle(MarksStyle.LABELPERCENT);
                break;
            case 4:
                marks.setStyle(MarksStyle.LABELVALUE);
                break;
            case 5:
                marks.setStyle(MarksStyle.LEGEND);
                break;
            case 6:
                marks.setStyle(MarksStyle.PERCENTTOTAL);
                break;
            case 7:
                marks.setStyle(MarksStyle.LABELPERCENTTOTAL);
                break;
            case 8:
                marks.setStyle(MarksStyle.XVALUE);
                break;
            case 9:
                marks.setStyle(MarksStyle.XY);
                break;
            }
        }
    }

    public void bCalloutPointer_actionPerformed(ActionEvent e) {
        SeriesPointerEditor.edit(this, marks.getCallout());
    }

    public void spHeadSize_stateChanged(ChangeEvent e) {
        marks.getCallout().setArrowHeadSize(((Integer) spHeadSize.getValue()).
                                            intValue());
    }

    public void cbHead_actionPerformed(ActionEvent e) {
        if (marks!=null) {
            marks.getCallout().setArrowHead(ArrowHeadStyle.fromValue(cbHead.
                    getSelectedIndex()));
        }
    }

    public void spArrowLength_stateChanged(ChangeEvent e) {
        marks.getCallout().setLength(((Integer) spArrowLength.getValue()).
                                     intValue());
    }

    public void spArrowDist_stateChanged(ChangeEvent e) {
        marks.getCallout().setDistance(((Integer) spArrowDist.getValue()).
                                       intValue());
    }
}


class SeriesMarksEditor_spArrowDist_changeAdapter implements ChangeListener {
    private SeriesMarksEditor adaptee;
    SeriesMarksEditor_spArrowDist_changeAdapter(SeriesMarksEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void stateChanged(ChangeEvent e) {
        adaptee.spArrowDist_stateChanged(e);
    }
}


class SeriesMarksEditor_spArrowLength_changeAdapter implements ChangeListener {
    private SeriesMarksEditor adaptee;
    SeriesMarksEditor_spArrowLength_changeAdapter(SeriesMarksEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void stateChanged(ChangeEvent e) {
        adaptee.spArrowLength_stateChanged(e);
    }
}


class SeriesMarksEditor_cbHead_actionAdapter implements ActionListener {
    private SeriesMarksEditor adaptee;
    SeriesMarksEditor_cbHead_actionAdapter(SeriesMarksEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.cbHead_actionPerformed(e);
    }
}


class SeriesMarksEditor_spHeadSize_changeAdapter implements ChangeListener {
    private SeriesMarksEditor adaptee;
    SeriesMarksEditor_spHeadSize_changeAdapter(SeriesMarksEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void stateChanged(ChangeEvent e) {
        adaptee.spHeadSize_stateChanged(e);
    }
}


class SeriesMarksEditor_bCalloutPointer_actionAdapter implements ActionListener {
    private SeriesMarksEditor adaptee;
    SeriesMarksEditor_bCalloutPointer_actionAdapter(SeriesMarksEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.bCalloutPointer_actionPerformed(e);
    }
}


class SeriesMarksEditor_cbStyle_actionAdapter implements ActionListener {
    private SeriesMarksEditor adaptee;
    SeriesMarksEditor_cbStyle_actionAdapter(SeriesMarksEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.cbStyle_actionPerformed(e);
    }
}


class SeriesMarksEditor_cbClipped_actionAdapter implements ActionListener {
    private SeriesMarksEditor adaptee;
    SeriesMarksEditor_cbClipped_actionAdapter(SeriesMarksEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.cbClipped_actionPerformed(e);
    }
}


class SeriesMarksEditor_spLength_changeAdapter implements ChangeListener {
    private SeriesMarksEditor adaptee;
    SeriesMarksEditor_spLength_changeAdapter(SeriesMarksEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void stateChanged(ChangeEvent e) {
        adaptee.spLength_stateChanged(e);
    }
}


class SeriesMarksEditor_spAngle_changeAdapter implements ChangeListener {
    private SeriesMarksEditor adaptee;
    SeriesMarksEditor_spAngle_changeAdapter(SeriesMarksEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void stateChanged(ChangeEvent e) {
        adaptee.spAngle_stateChanged(e);
    }
}


class SeriesMarksEditor_spDrawEvery_changeAdapter implements ChangeListener {
    private SeriesMarksEditor adaptee;
    SeriesMarksEditor_spDrawEvery_changeAdapter(SeriesMarksEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void stateChanged(ChangeEvent e) {
        adaptee.spDrawEvery_stateChanged(e);
    }
}


class SeriesMarksEditor_cbAllVisible_actionAdapter implements ActionListener {
    private SeriesMarksEditor adaptee;
    SeriesMarksEditor_cbAllVisible_actionAdapter(SeriesMarksEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.cbAllVisible_actionPerformed(e);
    }
}


class SeriesMarksEditor_cbVisible_actionAdapter implements ActionListener {
    private SeriesMarksEditor adaptee;
    SeriesMarksEditor_cbVisible_actionAdapter(SeriesMarksEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.cbVisible_actionPerformed(e);
    }
}

