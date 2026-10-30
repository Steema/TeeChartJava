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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.steema.teechart.styles.Series;
import com.steema.teechart.styles.CustomPolar;
import com.steema.teechart.editors.BaseSeriesEditor;
import com.steema.teechart.editors.ButtonPen;
import com.steema.teechart.editors.BrushEditor;
import com.steema.teechart.editors.ButtonColor;
import com.steema.teechart.editors.ChartFontEditor;
import com.steema.teechart.ChartException;
import com.steema.teechart.languages.Language;
import com.steema.teechart.styles.TreatNullsStyle;

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
public class PolarEditor extends BaseSeriesEditor {


    public PolarEditor() {
        super();
        try {
            jbInit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        
        cbTreatNulls.addItem(Language.getString("DontPaint"));
        cbTreatNulls.addItem(Language.getString("Skip"));
        cbTreatNulls.addItem(Language.getString("Ignore"));
    }

    private CircledEditor circled;

    public void setSeries(Series series, JTabbedPane tabs) {
        CustomPolar polar = (CustomPolar)series;
        spAngleInc.setVisible(false);
        jLabel5.setVisible(false);
        cbTreatNulls.setSelectedIndex(polar.getTreatNulls().getValue());
        SetPolar((CustomPolar) series);
        if(circled == null) {
            circled = new CircledEditor();
            circled.setSeries(polar, tabs);
            tabs.add(circled, Language.getString("Circled"), 1);
        }
        tabs.add(new SeriesPointerEditor(polar.getPointer()),Language.getString("Point"), tabs.getTabCount());
    }

    protected void SetPolar(Series series)
    {
            polar = (CustomPolar) series;
            cbAngleLabels.setSelected(polar.getCircleLabels());
            cbClockWise.setSelected(polar.getClockWiseLabels());
            cbCloseCircle.setSelected(polar.getCloseCircle());
            cbColorEach.setSelected(polar.getColorEach());
            cbInside.setSelected(polar.getCircleLabelsInside());
            cbLabelsRot.setSelected(polar.getCircleLabelsRotated());
            spTransp.setValue(new Integer(polar.getTransparency()));
            bColor.setColor(polar.getColor());
            bPen.setPen(polar.getPen());
            bCircle.setPen(polar.getCirclePen());
      }

    private void jbInit() throws Exception {
        border12 = BorderFactory.createEtchedBorder(Color.white,
                new Color(148, 145, 140));
        this.setLayout(null);
        jLabel2.setDisplayedMnemonic(Language.getString("T").charAt(0));
        jLabel2.setHorizontalAlignment(SwingConstants.TRAILING);
        jLabel2.setLabelFor(spTransp);
        jLabel2.setText(Language.getString("Transparency"));
        jLabel2.setBounds(new Rectangle(22, 143, 81, 14));
        jLabel3.setDisplayedMnemonic(Language.getString("O").charAt(0));
        jLabel3.setHorizontalAlignment(SwingConstants.TRAILING);
        jLabel3.setText(Language.getString("Transparency"));
        jLabel3.setBounds(new Rectangle( -9, 154, 93, 14));
        bPattern.setBounds(new Rectangle(17, 42, 75, 23));
        bPattern.setMnemonic(Language.getString("T").charAt(0));
        bPattern.setText(Language.getString("Pattern"));
        cbCloseCircle.setMnemonic(Language.getString("L").charAt(0));
        cbCloseCircle.setText(Language.getString("Close_Circle"));
        cbCloseCircle.setBounds(new Rectangle(100, 15, 92, 17));
        cbLabelsRot.setMnemonic(Language.getString("O").charAt(0));
        cbLabelsRot.setText(Language.getString("Rotated"));
        cbLabelsRot.setBounds(new Rectangle(12, 49, 92, 17));
        cbLabelsRot.addActionListener(new PolarEditor_cbLabelsRot_actionAdapter(this));
        bPen.setBounds(new Rectangle(16, 12, 76, 23));
        bPen.setMnemonic(Language.getString("P").charAt(0));
        bPen.setText(Language.getString("Pen"));
        jPanel1.setBorder(border34);
        jPanel1.setBounds(new Rectangle(192, 3, 169, 132));
        jPanel1.setLayout(null);
        spTransp.setBounds(new Rectangle(112, 139, 53, 23));
        jLabel4.setDisplayedMnemonic(Language.getString("R").charAt(0));
        jLabel4.setHorizontalAlignment(SwingConstants.TRAILING);
        jLabel4.setLabelFor(spRadiusInc);
        jLabel4.setText(Language.getString("Radius_Increment"));
        jLabel4.setBounds(new Rectangle(8, 116, 98, 15));
        spRadiusInc.setBounds(new Rectangle(112, 111, 66, 24));
        spRadiusInc.addChangeListener(new PolarEditor_spRadiusInc_changeAdapter(this));
        jLabel5.setDisplayedMnemonic(Language.getString("I").charAt(0));
        jLabel5.setHorizontalAlignment(SwingConstants.TRAILING);
        jLabel5.setLabelFor(spAngleInc);
        jLabel5.setText(Language.getString("Angle_Increment"));
        jLabel5.setBounds(new Rectangle(5, 88, 101, 15));
        spAngleInc.setBounds(new Rectangle(112, 83, 65, 24));
        spAngleInc.addChangeListener(new PolarEditor_spAngleInc_changeAdapter(this));
        bCircle.setBounds(new Rectangle(103, 42, 76, 23));
        bCircle.setMnemonic(Language.getString("C").charAt(0));
        bCircle.setText(Language.getString("Circle"));
        cbClockWise.setMnemonic(Language.getString("W").charAt(0));
        cbClockWise.setText(Language.getString("ClockWise"));
        cbClockWise.setBounds(new Rectangle(12, 75, 110, 17));
        cbClockWise.addActionListener(new PolarEditor_cbClockWise_actionAdapter(this));
        cbInside.setMnemonic(Language.getString("S").charAt(0));
        cbInside.setText(Language.getString("Inside"));
        cbInside.setBounds(new Rectangle(98, 23, 67, 17));
        cbInside.addActionListener(new PolarEditor_cbInside_actionAdapter(this));
        cbAngleLabels.setMnemonic(Language.getString("V").charAt(0));
        cbAngleLabels.setText(Language.getString("Visible"));
        cbAngleLabels.setBounds(new Rectangle(12, 23, 80, 17));
        cbAngleLabels.addActionListener(new
        PolarEditor_cbAngleLabels_actionAdapter(this));
        bFont.setBounds(new Rectangle(16, 99, 75, 23));
        bFont.setMnemonic(Language.getString("F").charAt(0));
        bFont.setText(Language.getString("Font"));
        bFont.addActionListener(new PolarEditor_jButton1_actionAdapter(this));
        cbColorEach.setToolTipText("");
        cbColorEach.setMnemonic(Language.getString("E").charAt(0));
        cbColorEach.setText(Language.getString("Color_each"));
        cbColorEach.setBounds(new Rectangle(190, 142, 146, 17));
        bColor.setBounds(new Rectangle(112, 166, 74, 23));
        bColor.setMnemonic(Language.getString("O").charAt(0));
        bColor.setText(Language.getString("Color"));
        cbCloseCircle.addActionListener(new
        PolarEditor_cbCloseCircle_actionAdapter(this));
        bPattern.addActionListener(new PolarEditor_bPattern_actionAdapter(this));
        spTransp.addChangeListener(new PolarEditor_spTransp_changeAdapter(this));
        bColor.addActionListener(new PolarEditor_bColor_actionAdapter(this));
        cbColorEach.addActionListener(new PolarEditor_cbColorEach_actionAdapter(this));                
        jPanel2.setBorder(border36);
        jPanel2.setBounds(new Rectangle(16, 200, 155, 60));
        jPanel2.setLayout(null);
        cbTreatNulls.setBounds(new Rectangle(10, 22, 135, 22));
        cbTreatNulls.addActionListener(new 
                PolarEditor_cbTreatNulls_actionAdapter(this));
        this.add(cbColorEach);
        this.add(jLabel4);
        this.add(jLabel2);
        this.add(spTransp);
        this.add(spRadiusInc);
        this.add(spAngleInc);
        this.add(jLabel5);
        this.add(bPattern);
        jPanel1.add(cbAngleLabels);
        jPanel1.add(cbLabelsRot);
        jPanel1.add(cbClockWise);
        jPanel1.add(bFont);
        jPanel1.add(cbInside);
        jPanel2.add(cbTreatNulls);
        this.add(bCircle);
        this.add(cbCloseCircle);
        this.add(bPen);
        this.add(bColor);
        this.add(jPanel1);
        this.add(jPanel2);
    }

    Border border1 = BorderFactory.createEtchedBorder(Color.white,
            new Color(148, 145, 140));
    Border border2 = new TitledBorder(border1, Language.getString("custom"));
    private CustomPolar polar;
    Border border3 = BorderFactory.createEtchedBorder(EtchedBorder.RAISED,
            Color.white, new Color(148, 145, 140));
    Border border4 = new TitledBorder(border3, Language.getString("Multiple_customs"));
    Border border5 = BorderFactory.createEtchedBorder(Color.white,
            new Color(148, 145, 140));
    Border border6 = new TitledBorder(border5, Language.getString("Color"));
    Border border7 = BorderFactory.createEtchedBorder(EtchedBorder.RAISED,
            Color.white, new Color(148, 145, 140));
    Border border8 = BorderFactory.createEtchedBorder(Color.white,
            new Color(148, 145, 140));
    Border border9 = new TitledBorder(border8, Language.getString("Levels"));
    JTextField eValue = new JTextField();
    Border border10 = BorderFactory.createEtchedBorder(Color.white,
            new Color(148, 145, 140));
    Border border11 = new TitledBorder(border10, Language.getString("Line_mode"));
    JLabel jLabel2 = new JLabel();
    JLabel jLabel3 = new JLabel();
    Border border12 = BorderFactory.createEtchedBorder(EtchedBorder.RAISED,
            Color.white, new Color(148, 145, 140));
    JButton bPattern = new JButton();
    Border border13 = BorderFactory.createEtchedBorder(Color.white,
            new Color(148, 145, 140));
    Border border14 = new TitledBorder(border13, Language.getString("Color"));
    JCheckBox cbCloseCircle = new JCheckBox();
    JCheckBox cbLabelsRot = new JCheckBox();
    ButtonPen bPen = new ButtonPen();
    JPanel jPanel1 = new JPanel();
    Border border15 = BorderFactory.createEmptyBorder();
    Border border16 = new TitledBorder(border15, Language.getString("Origin"));
    Border border17 = BorderFactory.createBevelBorder(BevelBorder.RAISED,
            Color.white, Color.white, new Color(115, 114, 105),
            new Color(165, 163, 151));
    Border border18 = new TitledBorder(border17, Language.getString("Origin"));
    Border border19 = BorderFactory.createEtchedBorder(EtchedBorder.RAISED,
            Color.white, new Color(165, 163, 151));
    Border border20 = new TitledBorder(border19, Language.getString("Origin"));
    JSpinner spTransp = new JSpinner();
    Border border21 = BorderFactory.createEmptyBorder();
    Border border22 = new TitledBorder(border21, Language.getString("Percent"));
    Border border23 = BorderFactory.createBevelBorder(BevelBorder.RAISED,
            Color.white, Color.white, new Color(115, 114, 105),
            new Color(165, 163, 151));
    Border border24 = new TitledBorder(border23, Language.getString("Percent"));
    TitledBorder titledBorder1 = new TitledBorder("");
    Border border25 = BorderFactory.createEtchedBorder(Color.white,
            new Color(165, 163, 151));
    Border border26 = new TitledBorder(border25, Language.getString("Percent"));
    Border border27 = BorderFactory.createEtchedBorder(EtchedBorder.RAISED,
            Color.white, new Color(165, 163, 151));
    Border border28 = new TitledBorder(border27, Language.getString("Origin"));
    Border border29 = BorderFactory.createEtchedBorder(EtchedBorder.RAISED,
            Color.white, new Color(165, 163, 151));
    Border border30 = new TitledBorder(border29, Language.getString("Percent"));
    Border border31 = BorderFactory.createEtchedBorder(EtchedBorder.RAISED,
            Color.white, new Color(165, 163, 151));
    Border border32 = new TitledBorder(border31, Language.getString("Percent"));
    JLabel jLabel4 = new JLabel();
    JSpinner spRadiusInc = new JSpinner();
    JLabel jLabel5 = new JLabel();
    JSpinner spAngleInc = new JSpinner();
    ButtonPen bCircle = new ButtonPen();
    Border border33 = BorderFactory.createEtchedBorder(EtchedBorder.RAISED,
            Color.white, new Color(165, 163, 151));
    Border border34 = new TitledBorder(border33, Language.getString("Labels"));
    JCheckBox cbClockWise = new JCheckBox();
    JCheckBox cbInside = new JCheckBox();
    JCheckBox cbAngleLabels = new JCheckBox();
    JButton bFont = new JButton();
    JCheckBox cbColorEach = new JCheckBox();
    ButtonColor bColor = new ButtonColor();
    JPanel jPanel2 = new JPanel();
    Border border35 = BorderFactory.createEtchedBorder(EtchedBorder.RAISED,
            Color.white, new Color(148, 175, 140));
    Border border36 = new TitledBorder(border35, Language.getString("TreatNulls"));
    JComboBox cbTreatNulls = new JComboBox();


    private void EnableLabels() {
            cbLabelsRot.setEnabled(cbAngleLabels.isEnabled());
            cbClockWise.setEnabled(cbAngleLabels.isEnabled());
            bFont.setEnabled(cbAngleLabels.isEnabled());
    }

    public void cbAngleLabels_actionPerformed(ActionEvent e) {
        polar.setCircleLabels(cbAngleLabels.isSelected());
        EnableLabels();
    }

    public void cbInside_actionPerformed(ActionEvent e) {
        polar.setCircleLabelsInside(cbInside.isSelected());
    }

    public void cbLabelsRot_actionPerformed(ActionEvent e) {
        polar.setCircleLabelsRotated(cbLabelsRot.isSelected());
    }

    public void cbClockWise_actionPerformed(ActionEvent e) {
        polar.setClockWiseLabels(cbClockWise.isSelected());
    }

    public void bFont_actionPerformed(ActionEvent e) {
        ChartFontEditor.edit(this,polar.getCircleLabelsFont());
    }

    public void cbCloseCircle_actionPerformed(ActionEvent e) {
        polar.setCloseCircle(cbCloseCircle.isSelected());
    }

    public void bPattern_actionPerformed(ActionEvent e) {
        BrushEditor.edit(polar.getBrush());
    }

    public void spAngleInc_stateChanged(ChangeEvent e) {
        polar.setAngleIncrement(((Integer)spAngleInc.getValue()).intValue());
    }

    public void spRadiusInc_stateChanged(ChangeEvent e) {
        try {
            polar.setRadiusIncrement(((Integer)spRadiusInc.getValue()).intValue());
        } catch (ChartException ex) {
        }
    }

    public void spTransp_stateChanged(ChangeEvent e) {
        polar.setTransparency(((Integer)spTransp.getValue()).intValue());
    }

    public void bColor_actionPerformed(ActionEvent e) {
        polar.setColor(bColor.getColor());
    }

    public void cbColorEach_actionPerformed(ActionEvent e) {
        polar.setColorEach(cbColorEach.isSelected());
    }
    
    public void cbTreatNulls_actionPerformed(ActionEvent e) {
        if (polar!=null) {
            polar.setTreatNulls(TreatNullsStyle.fromInt(
                    cbTreatNulls.getSelectedIndex()));
        }
    }      

}


class PolarEditor_cbColorEach_actionAdapter implements ActionListener {
    private PolarEditor adaptee;
    PolarEditor_cbColorEach_actionAdapter(PolarEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.cbColorEach_actionPerformed(e);
    }
}


class PolarEditor_bColor_actionAdapter implements ActionListener {
    private PolarEditor adaptee;
    PolarEditor_bColor_actionAdapter(PolarEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.bColor_actionPerformed(e);
    }
}


class PolarEditor_spTransp_changeAdapter implements ChangeListener {
    private PolarEditor adaptee;
    PolarEditor_spTransp_changeAdapter(PolarEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void stateChanged(ChangeEvent e) {
        adaptee.spTransp_stateChanged(e);
    }
}


class PolarEditor_spRadiusInc_changeAdapter implements ChangeListener {
    private PolarEditor adaptee;
    PolarEditor_spRadiusInc_changeAdapter(PolarEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void stateChanged(ChangeEvent e) {
        adaptee.spRadiusInc_stateChanged(e);
    }
}


class PolarEditor_spAngleInc_changeAdapter implements ChangeListener {
    private PolarEditor adaptee;
    PolarEditor_spAngleInc_changeAdapter(PolarEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void stateChanged(ChangeEvent e) {
        adaptee.spAngleInc_stateChanged(e);
    }
}


class PolarEditor_bPattern_actionAdapter implements ActionListener {
    private PolarEditor adaptee;
    PolarEditor_bPattern_actionAdapter(PolarEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.bPattern_actionPerformed(e);
    }
}


class PolarEditor_cbCloseCircle_actionAdapter implements ActionListener {
    private PolarEditor adaptee;
    PolarEditor_cbCloseCircle_actionAdapter(PolarEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.cbCloseCircle_actionPerformed(e);
    }
}


class PolarEditor_jButton1_actionAdapter implements ActionListener {
    private PolarEditor adaptee;
    PolarEditor_jButton1_actionAdapter(PolarEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.bFont_actionPerformed(e);
    }
}


class PolarEditor_cbClockWise_actionAdapter implements ActionListener {
    private PolarEditor adaptee;
    PolarEditor_cbClockWise_actionAdapter(PolarEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.cbClockWise_actionPerformed(e);
    }
}


class PolarEditor_cbLabelsRot_actionAdapter implements ActionListener {
    private PolarEditor adaptee;
    PolarEditor_cbLabelsRot_actionAdapter(PolarEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.cbLabelsRot_actionPerformed(e);
    }
}


class PolarEditor_cbInside_actionAdapter implements ActionListener {
    private PolarEditor adaptee;
    PolarEditor_cbInside_actionAdapter(PolarEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.cbInside_actionPerformed(e);
    }
}


class PolarEditor_cbAngleLabels_actionAdapter implements ActionListener {
    private PolarEditor adaptee;
    PolarEditor_cbAngleLabels_actionAdapter(PolarEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.cbAngleLabels_actionPerformed(e);
    }
}

class PolarEditor_cbTreatNulls_actionAdapter implements ActionListener {
    private PolarEditor adaptee;
    PolarEditor_cbTreatNulls_actionAdapter(PolarEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.cbTreatNulls_actionPerformed(e);
    }
}
