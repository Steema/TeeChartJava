package com.steema.teechart.editors.series;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.border.BevelBorder;

import java.awt.Rectangle;
import java.awt.Color;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.steema.teechart.styles.Series;
import com.steema.teechart.styles.Gauges;
import com.steema.teechart.styles.HandStyle;
import com.steema.teechart.editors.BaseSeriesEditor;
import com.steema.teechart.editors.ChartFontEditor;
import com.steema.teechart.editors.ButtonPen;
import com.steema.teechart.editors.ChartPenEditor;
import com.steema.teechart.languages.Language;


/**
 * <p>Title: TeeChart for Java</p>
 *
 * <p>Description: Gauges series editor</p>
 *
 * <p>Copyright (c) 2004-2019 by Steema Software SL. All Rights Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 *
 */
public class GaugesEditor extends BaseSeriesEditor {

    private Gauges gauge;

    ChartPenEditor penEditor = new ChartPenEditor();
    JTabbedPane jTabbedPane1 = new JTabbedPane();
    BorderLayout borderLayout1 = new BorderLayout();
    JPanel jPanel1 = new JPanel();
    JPanel jPanel2 = new JPanel();
    JPanel jPanel3 = new JPanel();
    JLabel jLabel1 = new JLabel();
    JComboBox cbStyle = new JComboBox();
    Border border1 = BorderFactory.createEtchedBorder(EtchedBorder.RAISED,
            Color.white, new Color(148, 145, 140));
    JCheckBox cbEach = new JCheckBox();
    JButton bEndPoint = new JButton();
    JCheckBox cbShowLabels = new JCheckBox();
    JCheckBox cbInside = new JCheckBox();
    Border border2 = BorderFactory.createBevelBorder(BevelBorder.LOWERED,
            Color.white, Color.white, new Color(115, 114, 105),
            new Color(165, 163, 151));
    Border border3 = new TitledBorder(border2, Language.getString("Alignment"));
    Border border4 = BorderFactory.createBevelBorder(BevelBorder.RAISED,
            Color.white, Color.white, new Color(115, 114, 105),
            new Color(165, 163, 151));
    Border border5 = new TitledBorder(border4, Language.getString("Alignment"));
    Border border6 = BorderFactory.createBevelBorder(BevelBorder.RAISED,
            Color.white, Color.white, new Color(124, 124, 124),
            new Color(178, 178, 178));
    Border border7 = BorderFactory.createLineBorder(Color.white, 2);
    Border border8 = BorderFactory.createLineBorder(Color.white, 2);
    Border border9 = BorderFactory.createEtchedBorder(EtchedBorder.RAISED,
            Color.white, new Color(178, 178, 178));
    Border border10 = BorderFactory.createBevelBorder(BevelBorder.LOWERED,
            Color.white, Color.white, new Color(115, 114, 105),
            new Color(165, 163, 151));
    Border border11 = new TitledBorder(border10, Language.getString("Alignment"));
    Border border12 = BorderFactory.createBevelBorder(BevelBorder.LOWERED,
            Color.white, Color.white, new Color(115, 114, 105),
            new Color(165, 163, 151));
    Border border13 = new TitledBorder(border12, Language.getString("Horiz_Alignment"));
    Border border14 = BorderFactory.createBevelBorder(BevelBorder.RAISED,
            Color.white, Color.white, new Color(115, 114, 105),
            new Color(165, 163, 151));
    Border border15 = new TitledBorder(border14, Language.getString("Horiz_Alignment"));
    Border border16 = BorderFactory.createBevelBorder(BevelBorder.LOWERED,
            Color.white, Color.white, new Color(115, 114, 105),
            new Color(165, 163, 151));
    Border border17 = new TitledBorder(border16, Language.getString("Horiz_Alignment"));
    Border border18 = BorderFactory.createBevelBorder(BevelBorder.RAISED,
            Color.white, Color.white, new Color(115, 114, 105),
            new Color(165, 163, 151));
    Border border19 = new TitledBorder(border18, Language.getString("Horiz_Alignment"));
    Border border20 = BorderFactory.createBevelBorder(BevelBorder.RAISED,
            Color.white, Color.white, new Color(115, 114, 105),
            new Color(165, 163, 151));
    Border border21 = new TitledBorder(border20, Language.getString("Alignment"));
    JLabel jLabel5 = new JLabel();
    JLabel jLabel6 = new JLabel();
    Border border22 = BorderFactory.createBevelBorder(BevelBorder.LOWERED,
            Color.white, Color.white, new Color(115, 114, 105),
            new Color(165, 163, 151));
    Border border23 = new TitledBorder(border22, Language.getString("Alignment"));
    Border border24 = BorderFactory.createBevelBorder(BevelBorder.LOWERED,
            Color.white, Color.white, new Color(115, 114, 105),
            new Color(165, 163, 151));
    Border border25 = new TitledBorder(border24, Language.getString("Horiz_Alignment"));
    ButtonPen bPLine = new ButtonPen();
    ButtonPen bPAxis = new ButtonPen();
    JLabel jLabel7 = new JLabel();
    JLabel jLabel8 = new JLabel();
    JSpinner spValue = new JSpinner();
    JSpinner spTotalAngle = new JSpinner();
    JSpinner spDistance = new JSpinner();
    JSpinner sp1 = new JSpinner();
    JSpinner sp3 = new JSpinner();
    JSpinner sp2 = new JSpinner();
    JSpinner sp4 = new JSpinner();
    JSpinner spMax = new JSpinner();
    JSpinner spMin = new JSpinner();
    ButtonPen bPTicks = new ButtonPen();
    ButtonPen bPMinor = new ButtonPen();
    JButton bFont = new JButton();
    JLabel jLabel2 = new JLabel();
    JTextField textBox1 = new JTextField();
    JButton bCenter = new JButton();

    public GaugesEditor() {
        try {
            jbInit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        cbStyle.addItem(Language.getString("Line"));
        cbStyle.addItem(Language.getString("Triangle"));
    }

    public void setSeries(Series series, JTabbedPane tabs) {
        gauge = (Gauges) series;

        bPLine.setPen(gauge.getPen());
        bPAxis.setPen(gauge.getVertAxis().getAxisPen());
        switch(gauge.getHandStyle().getValue()) {
                case 0:
                {
                    cbStyle.setSelectedIndex(0);
                    break;
                }
                case 1:
                {
                    cbStyle.setSelectedIndex(1);
                    break;
                }
        }
        spValue.setValue(new Double(gauge.getValue()));
        spTotalAngle.setValue(new Double(gauge.getTotalAngle()));
        spDistance.setValue(new Double(gauge.getHandDistance()));
        cbShowLabels.setSelected(gauge.getVertAxis().getLabels().getVisible());
        cbInside.setSelected(gauge.getLabelsInside());
        textBox1.setText(gauge.getVertAxis().getLabels().getValueFormat());
        sp1.setValue (new Double(gauge.getVertAxis().getTicks().getLength()));
        sp2.setValue (new Double(gauge.getMinorTickDistance()));
        sp3.setValue (new Double(gauge.getVertAxis().getMinorTickCount()));
        sp4.setValue (new Double(gauge.getVertAxis().getMinorTicks().getLength()));
        spMax.setValue (new Double(gauge.getMaximum()));
        spMin.setValue (new Double(gauge.getMinimum()));
    }

    private void jbInit() throws Exception {
        this.setLayout(borderLayout1);
        jPanel1.setLayout(null);
        jLabel1.setDisplayedMnemonic(Language.getString("T").charAt(0));
        jLabel1.setHorizontalAlignment(SwingConstants.RIGHT);
        jLabel1.setLabelFor(spTotalAngle);
        jLabel1.setText(Language.getString("Total_Angle"));
        jLabel1.setBounds(new Rectangle(22, 95, 68, 14));
        cbStyle.setBounds(new Rectangle(119, 9, 110, 22));
        cbStyle.addActionListener(new GaugesEditor_cbStyle_actionAdapter(this));
        jPanel3.setBorder(border1);
        jPanel3.setBounds(new Rectangle(0, 0, 395, 274));
        jPanel3.setLayout(null);
        cbEach.setMnemonic(Language.getString("E").charAt(0));
        cbEach.setText(Language.getString("Color_each"));
        cbEach.setBounds(new Rectangle(6, 6, 81, 23));
        bEndPoint.setBounds(new Rectangle(169, 89, 86, 23));
        bEndPoint.setMnemonic(Language.getString("E").charAt(0));
        bEndPoint.setText(Language.getString("End_Point"));
        bEndPoint.addActionListener(new GaugesEditor_bEndPoint_actionAdapter(this));
        jPanel2.setLayout(null);
        cbShowLabels.setMnemonic(Language.getString("S").charAt(0));
        cbShowLabels.setText(Language.getString("Show_Labels"));
        cbShowLabels.setBounds(new Rectangle(22, 10, 112, 23));
        cbShowLabels.addActionListener(new
                                       GaugesEditor_cbShowLabels_actionAdapter(this));
        cbInside.setMnemonic(Language.getString("I").charAt(0));
        cbInside.setText(Language.getString("Inside"));
        cbInside.setBounds(new Rectangle(22, 36, 128, 23));
        cbInside.addActionListener(new GaugesEditor_cbInside_actionAdapter(this));
        jLabel5.setDisplayedMnemonic(Language.getString("A").charAt(0));
        jLabel5.setHorizontalAlignment(SwingConstants.RIGHT);
        jLabel5.setText(Language.getString("Maximum"));
        jLabel5.setBounds(new Rectangle(48, 99, 59, 18));
        jLabel6.setDisplayedMnemonic(Language.getString("N").charAt(0));
        jLabel6.setHorizontalAlignment(SwingConstants.RIGHT);
        jLabel6.setText(Language.getString("Minimum"));
        jLabel6.setBounds(new Rectangle(44, 126, 63, 15));
        bPLine.setBounds(new Rectangle(18, 9, 76, 23));
        bPLine.setMnemonic(Language.getString("L").charAt(0));
        bPLine.setText(Language.getString("Line"));
        bPAxis.setBounds(new Rectangle(19, 36, 76, 23));
        bPAxis.setMnemonic(Language.getString("A").charAt(0));
        bPAxis.setText(Language.getString("Axis"));
        jLabel7.setDisplayedMnemonic(Language.getString("D").charAt(0));
        jLabel7.setHorizontalAlignment(SwingConstants.RIGHT);
        jLabel7.setLabelFor(spDistance);
        jLabel7.setText(Language.getString("Distance"));
        jLabel7.setBounds(new Rectangle(22, 121, 68, 14));
        jLabel8.setDisplayedMnemonic(Language.getString("V").charAt(0));
        jLabel8.setHorizontalAlignment(SwingConstants.RIGHT);
        jLabel8.setLabelFor(spValue);
        jLabel8.setText(Language.getString("Value"));
        jLabel8.setBounds(new Rectangle(22, 70, 68, 14));
        spValue.setBounds(new Rectangle(101, 65, 53, 23));
        spValue.addChangeListener(new GaugesEditor_spValue_changeAdapter(this));
        spTotalAngle.setBounds(new Rectangle(102, 91, 53, 23));
        spTotalAngle.addChangeListener(new
                                       GaugesEditor_spTotalAngle_changeAdapter(this));
        spDistance.setBounds(new Rectangle(102, 117, 53, 23));
        spDistance.addChangeListener(new GaugesEditor_spDistance_changeAdapter(this));
        sp1.setBounds(new Rectangle(112, 23, 62, 22));
        sp1.addChangeListener(new GaugesEditor_sp1_changeAdapter(this));
        sp3.setBounds(new Rectangle(112, 51, 62, 21));
        sp3.addChangeListener(new GaugesEditor_sp3_changeAdapter(this));
        sp2.setBounds(new Rectangle(220, 23, 61, 22));
        sp2.addChangeListener(new GaugesEditor_sp2_changeAdapter(this));
        sp4.setBounds(new Rectangle(221, 51, 60, 21));
        sp4.addChangeListener(new GaugesEditor_sp4_changeAdapter(this));
        spMax.setBounds(new Rectangle(113, 96, 53, 23));
        spMax.addChangeListener(new GaugesEditor_spMax_changeAdapter(this));
        spMin.setBounds(new Rectangle(114, 123, 53, 23));
        spMin.addChangeListener(new GaugesEditor_spMin_changeAdapter(this));
        bPTicks.setBounds(new Rectangle(24, 23, 76, 23));
        bPTicks.setMnemonic(Language.getString("T").charAt(0));
        bPTicks.setText(Language.getString("Ticks"));
        bPMinor.setBounds(new Rectangle(25, 50, 76, 23));
        bPMinor.setMnemonic(Language.getString("M").charAt(0));
        bPMinor.setText(Language.getString("Minor"));
        bFont.setBounds(new Rectangle(24, 67, 74, 24));
        bFont.setMnemonic(Language.getString("F").charAt(0));
        bFont.setText(Language.getString("Font"));
        bFont.addActionListener(new GaugesEditor_bFont_actionAdapter(this));
        jLabel2.setDisplayedMnemonic(Language.getString("F").charAt(0));
        jLabel2.setLabelFor(textBox1);
        jLabel2.setText(Language.getString("Format"));
        jLabel2.setBounds(new Rectangle(24, 99, 65, 16));
        textBox1.setText("");
        textBox1.setBounds(new Rectangle(22, 120, 100, 19));
        textBox1.addActionListener(new GaugesEditor_textBox1_actionAdapter(this));
        bCenter.setBounds(new Rectangle(119, 36, 85, 23));
        bCenter.setMnemonic(Language.getString("C").charAt(0));
        bCenter.setText(Language.getString("Center"));
        bCenter.addActionListener(new GaugesEditor_bCenter_actionAdapter(this));
        this.add(jTabbedPane1, java.awt.BorderLayout.CENTER);
        jTabbedPane1.add(jPanel1, Language.getString("Options"));
        jPanel1.add(bPLine);
        jPanel1.add(bPAxis);
        jPanel1.add(bEndPoint);
        jPanel1.add(jLabel8);
        jPanel1.add(jLabel1);
        jPanel1.add(jLabel7);
        jPanel1.add(cbStyle);
        jPanel1.add(spValue);
        jPanel1.add(spTotalAngle);
        jPanel1.add(spDistance);
        jPanel1.add(bCenter);
        jTabbedPane1.add(jPanel2, Language.getString("Ticks"));
        jPanel2.add(sp1);
        jPanel2.add(sp2);
        jPanel2.add(spMax);
        jPanel2.add(spMin);
        jPanel2.add(jLabel6);
        jPanel2.add(jLabel5);
        jPanel2.add(bPTicks);
        jPanel2.add(bPMinor);
        jPanel2.add(sp3);
        jPanel2.add(sp4);
        jTabbedPane1.add(jPanel3, Language.getString("Labels"));
        jPanel3.add(cbShowLabels);
        jPanel3.add(cbInside);
        jPanel3.add(bFont);
        jPanel3.add(jLabel2);
        jPanel3.add(textBox1);
    }

    public void bCenter_actionPerformed(ActionEvent e) {
      com.steema.teechart.editors.series.SeriesPointerEditor.edit(this,gauge.getCenter());
    }

    public void bEndPoint_actionPerformed(ActionEvent e) {
        com.steema.teechart.editors.series.SeriesPointerEditor.edit(this,gauge.getEndPoint());
    }

    public void spValue_stateChanged(ChangeEvent e) {
        Object value = spValue.getValue();
        int tmp = -1;
        if(value instanceof Double) {
            tmp = ((Double)value).intValue();
        }
        else if(value instanceof Integer) {
            tmp = ((Integer)value).intValue();
        }
        gauge.setValue(tmp);

    }

    public void spTotalAngle_stateChanged(ChangeEvent e) {
        Object value = spTotalAngle.getValue();
        int tmp = -1;
        if(value instanceof Double) {
            tmp = ((Double)value).intValue();
        }
        else if(value instanceof Integer) {
            tmp = ((Integer)value).intValue();
        }
        gauge.setTotalAngle(tmp);
    }

    public void spDistance_stateChanged(ChangeEvent e) {
        Object value = spDistance.getValue();
        int tmp = -1;
        if(value instanceof Double) {
            tmp = ((Double)value).intValue();
        }
        else if(value instanceof Integer) {
            tmp = ((Integer)value).intValue();
        }
        gauge.setHandDistance(tmp);
    }

    public void bFont_actionPerformed(ActionEvent e) {
        ChartFontEditor.edit(this,gauge.getVertAxis().getLabels().getFont());
    }

    public void cbShowLabels_actionPerformed(ActionEvent e) {
        gauge.getVertAxis().getLabels().setVisible(cbShowLabels.isSelected());
    }

    public void cbInside_actionPerformed(ActionEvent e) {
        gauge.setLabelsInside(cbInside.isSelected());
    }

    public void spMax_stateChanged(ChangeEvent e) {
        Object value = spMax.getValue();
        int tmp = -1;
        if(value instanceof Double) {
            tmp = ((Double)value).intValue();
        }
        else if(value instanceof Integer) {
            tmp = ((Integer)value).intValue();
        }
       gauge.setMaximum(tmp);
    }

    public void spMin_stateChanged(ChangeEvent e) {
        Object value = spMin.getValue();
        int tmp = -1;
        if(value instanceof Double) {
            tmp = ((Double)value).intValue();
        }
        else if(value instanceof Integer) {
            tmp = ((Integer)value).intValue();
        }
        gauge.setMinimum(tmp);
    }

    public void sp1_stateChanged(ChangeEvent e) {
        Object value = sp1.getValue();
        int tmp = -1;
        if(value instanceof Double) {
            tmp = ((Double)value).intValue();
        }
        else if(value instanceof Integer) {
            tmp = ((Integer)value).intValue();
        }
        gauge.getVertAxis().getTicks().setLength(tmp);
    }

    public void sp2_stateChanged(ChangeEvent e) {
        Object value = sp2.getValue();
        int tmp = -1;
        if(value instanceof Double) {
            tmp = ((Double)value).intValue();
        }
        else if(value instanceof Integer) {
            tmp = ((Integer)value).intValue();
        }
        gauge.setMinorTickDistance(tmp);
    }

    public void sp3_stateChanged(ChangeEvent e) {
        Object value = sp3.getValue();
        int tmp = -1;
        if(value instanceof Double) {
            tmp = ((Double)value).intValue();
        }
        else if(value instanceof Integer) {
            tmp = ((Integer)value).intValue();
        }
        gauge.getVertAxis().setMinorTickCount(tmp);
    }

    public void sp4_stateChanged(ChangeEvent e) {
        Object value = sp4.getValue();
        int tmp = -1;
        if(value instanceof Double) {
            tmp = ((Double)value).intValue();
        }
        else if(value instanceof Integer) {
            tmp = ((Integer)value).intValue();
        }
        gauge.getVertAxis().getMinorTicks().setLength(tmp);
    }

    public void cbStyle_actionPerformed(ActionEvent e) {
        if (gauge!=null) {
            switch (cbStyle.getSelectedIndex()) {
            case 0: {
                gauge.setHandStyle(HandStyle.LINE);
                break;
            }
            case 1: {
                gauge.setHandStyle(HandStyle.TRIANGLE);
                break;
            }
            }
        }
    }

    public void textBox1_actionPerformed(ActionEvent e) {
        gauge.getVertAxis().getLabels().setValueFormat(textBox1.getText());
    }
}


class GaugesEditor_bCenter_actionAdapter implements ActionListener {
    private GaugesEditor adaptee;
    GaugesEditor_bCenter_actionAdapter(GaugesEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.bCenter_actionPerformed(e);
    }
}


final class GaugesEditor_textBox1_actionAdapter implements ActionListener {
    private GaugesEditor adaptee;
    GaugesEditor_textBox1_actionAdapter(GaugesEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.textBox1_actionPerformed(e);
    }
}


final class GaugesEditor_cbStyle_actionAdapter implements ActionListener {
    private GaugesEditor adaptee;
    GaugesEditor_cbStyle_actionAdapter(GaugesEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.cbStyle_actionPerformed(e);
    }
}


final class GaugesEditor_sp4_changeAdapter implements ChangeListener {
    private GaugesEditor adaptee;
    GaugesEditor_sp4_changeAdapter(GaugesEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void stateChanged(ChangeEvent e) {
        adaptee.sp4_stateChanged(e);
    }
}


final class GaugesEditor_sp3_changeAdapter implements ChangeListener {
    private GaugesEditor adaptee;
    GaugesEditor_sp3_changeAdapter(GaugesEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void stateChanged(ChangeEvent e) {
        adaptee.sp3_stateChanged(e);
    }
}


final class GaugesEditor_sp2_changeAdapter implements ChangeListener {
    private GaugesEditor adaptee;
    GaugesEditor_sp2_changeAdapter(GaugesEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void stateChanged(ChangeEvent e) {
        adaptee.sp2_stateChanged(e);
    }
}


final class GaugesEditor_sp1_changeAdapter implements ChangeListener {
    private GaugesEditor adaptee;
    GaugesEditor_sp1_changeAdapter(GaugesEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void stateChanged(ChangeEvent e) {
        adaptee.sp1_stateChanged(e);
    }
}


final class GaugesEditor_spMin_changeAdapter implements ChangeListener {
    private GaugesEditor adaptee;
    GaugesEditor_spMin_changeAdapter(GaugesEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void stateChanged(ChangeEvent e) {
        adaptee.spMin_stateChanged(e);
    }
}


final class GaugesEditor_spMax_changeAdapter implements ChangeListener {
    private GaugesEditor adaptee;
    GaugesEditor_spMax_changeAdapter(GaugesEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void stateChanged(ChangeEvent e) {
        adaptee.spMax_stateChanged(e);
    }
}


final class GaugesEditor_cbInside_actionAdapter implements ActionListener {
    private GaugesEditor adaptee;
    GaugesEditor_cbInside_actionAdapter(GaugesEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.cbInside_actionPerformed(e);
    }
}


final class GaugesEditor_cbShowLabels_actionAdapter implements ActionListener {
    private GaugesEditor adaptee;
    GaugesEditor_cbShowLabels_actionAdapter(GaugesEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.cbShowLabels_actionPerformed(e);
    }
}


final class GaugesEditor_bFont_actionAdapter implements ActionListener {
    private GaugesEditor adaptee;
    GaugesEditor_bFont_actionAdapter(GaugesEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.bFont_actionPerformed(e);
    }
}


final class GaugesEditor_spDistance_changeAdapter implements ChangeListener {
    private GaugesEditor adaptee;
    GaugesEditor_spDistance_changeAdapter(GaugesEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void stateChanged(ChangeEvent e) {
        adaptee.spDistance_stateChanged(e);
    }
}


final class GaugesEditor_spTotalAngle_changeAdapter implements ChangeListener {
    private GaugesEditor adaptee;
    GaugesEditor_spTotalAngle_changeAdapter(GaugesEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void stateChanged(ChangeEvent e) {
        adaptee.spTotalAngle_stateChanged(e);
    }
}


final class GaugesEditor_spValue_changeAdapter implements ChangeListener {
    private GaugesEditor adaptee;
    GaugesEditor_spValue_changeAdapter(GaugesEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void stateChanged(ChangeEvent e) {
        adaptee.spValue_stateChanged(e);
    }
}


final class GaugesEditor_bEndPoint_actionAdapter implements ActionListener {
    private GaugesEditor adaptee;
    GaugesEditor_bEndPoint_actionAdapter(GaugesEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.bEndPoint_actionPerformed(e);
    }
}


