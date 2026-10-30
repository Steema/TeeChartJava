package com.steema.teechart.editors.series;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.border.TitledBorder;

import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.steema.teechart.styles.Series;
import com.steema.teechart.styles.Area;
import com.steema.teechart.editors.ButtonPen;
import com.steema.teechart.editors.BrushEditor;
import com.steema.teechart.styles.MultiAreas;
import com.steema.teechart.editors.ButtonColor;
import com.steema.teechart.editors.BaseSeriesEditor;
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
public class AreaEditor extends BaseSeriesEditor {


    public AreaEditor() {
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

    public void setSeries(Series series, JTabbedPane tabs) {
        area = (Area) series;

        cbStairs.setSelected(area.getStairs());
        cbInverted.setSelected(area.getInvertedStairs());
        cbSmoothed.setSelected(area.getSmoothed()); 
        bBorder.setPen(area.getLinePen());
        bLines.setPen(area.getAreaLinesPen());

        ButtonGroup group = new ButtonGroup();
        group.add(rbNone);
        group.add(rbStack);
        group.add(rbStack100);

        rbNone.setSelected(area.getMultiArea() == MultiAreas.NONE);
        rbStack.setSelected(area.getMultiArea() == MultiAreas.STACKED);
        rbStack100.setSelected(area.getMultiArea() == MultiAreas.STACKED100);

        cbColorEach.setSelected(area.getColorEach());
        bColor.setColor(area.getColor());

        cbUseOrigin.setSelected(area.getUseOrigin());
        spOrigin.setValue(new Double(area.getOrigin()));

        slTransp.setValue(area.getTransparency());

        tabs.add(new SeriesPointerEditor(area.getPointer()),Language.getString("Pointer"), 0);
        
        cbTreatNulls.setSelectedIndex(area.getTreatNulls().getValue());
    }

    private void jbInit() throws Exception {
        this.setLayout(null);
        jPanel1.setBorder(border2);
        jPanel1.setBounds(new Rectangle(4, 3, 288, 76));
        jPanel1.setLayout(null);
        cbStairs.setMnemonic(Language.getString("S").charAt(0));
        cbStairs.setText(Language.getString("Stairs"));
        cbStairs.setBounds(new Rectangle(7, 12, 81, 23));
        cbStairs.addActionListener(new AreaEditor_cbStairs_actionAdapter(this));
        cbInverted.setMnemonic(Language.getString("I").charAt(0));
        cbInverted.setText(Language.getString("Inverted"));
        cbInverted.setBounds(new Rectangle(7, 48, 81, 23));
        cbInverted.addActionListener(new AreaEditor_cbInverted_actionAdapter(this));
        cbSmoothed.setMnemonic(Language.getString("T").charAt(0));
        cbSmoothed.setText(Language.getString("Smoothed"));
        cbSmoothed.setBounds(new Rectangle(7, 30, 81, 23));
        cbSmoothed.addActionListener(new AreaEditor_cbSmoothed_actionAdapter(this));          
        jButton1.setBounds(new Rectangle(97, 13, 84, 23));
        jButton1.setMnemonic(Language.getString("P").charAt(0));
        jButton1.setText(Language.getString("Pattern"));
        jButton1.addActionListener(new AreaEditor_jButton1_actionAdapter(this));
        bBorder.setBounds(new Rectangle(189, 13, 91, 23));
        bBorder.setMnemonic(Language.getString("B").charAt(0));
        bBorder.setText(Language.getString("Border"));
        jButton2.setBounds(new Rectangle(97, 43, 82, 23));
        jButton2.setMnemonic(Language.getString("F").charAt(0));
        jButton2.setText(Language.getString("Fill"));
        jButton2.addActionListener(new AreaEditor_jButton2_actionAdapter(this));
        bLines.setBounds(new Rectangle(189, 43, 91, 23));
        bLines.setMargin(new Insets(2, 1, 2, 1));
        bLines.setMnemonic(Language.getString("L").charAt(0));
        bLines.setText(Language.getString("Area_Lines"));
        jPanel2.setBorder(border4);
        jPanel2.setBounds(new Rectangle(4, 79, 150, 80));
        jPanel2.setLayout(null);
        rbNone.setMnemonic(Language.getString("N").charAt(0));
        rbNone.setText(Language.getString("None"));
        rbNone.setBounds(new Rectangle(11, 14, 93, 23));
        rbNone.addActionListener(new AreaEditor_rbNone_actionAdapter(this));
        rbStack.setMnemonic(Language.getString("T").charAt(0));
        rbStack.setText(Language.getString("Stack"));
        rbStack.setBounds(new Rectangle(11, 34, 93, 23));
        rbStack.addActionListener(new AreaEditor_rbStack_actionAdapter(this));
        rbStack100.setMnemonic(Language.getString("K").charAt(0));
        rbStack100.setText(Language.getString("_STACKED_100%"));
        rbStack100.setBounds(new Rectangle(11, 54, 110, 23));
        rbStack100.addActionListener(new AreaEditor_rbStack100_actionAdapter(this));
        jPanel3.setBorder(border6);
        jPanel3.setBounds(new Rectangle(163, 79, 129, 80));
        jPanel3.setLayout(null);
        cbColorEach.setMnemonic(Language.getString("E").charAt(0));
        cbColorEach.setText(Language.getString("Color_each"));
        cbColorEach.setBounds(new Rectangle(11, 15, 109, 23));
        cbColorEach.addActionListener(new AreaEditor_jCheckBox1_actionAdapter(this));
        bColor.setBounds(new Rectangle(11, 43, 78, 23));
        bColor.setMnemonic(Language.getString("C").charAt(0));
        bColor.setText(Language.getString("Color"));
        bColor.addActionListener(new
                                 AreaEditor_buttonColor1_actionAdapter(this));
        jPanel4.setBorder(border7);
        jPanel4.setBounds(new Rectangle(5, 164, 286, 54));
        jPanel4.setLayout(null);
        cbUseOrigin.setMnemonic(Language.getString("U").charAt(0));
        cbUseOrigin.setText(Language.getString("Use_Origin"));
        cbUseOrigin.setBounds(new Rectangle(5, 3, 120, 23));
        cbUseOrigin.addActionListener(new AreaEditor_cbUseOrigin_actionAdapter(this));
        jLabel1.setDisplayedMnemonic(Language.getString("O").charAt(0));
        jLabel1.setHorizontalAlignment(SwingConstants.TRAILING);
        jLabel1.setLabelFor(spOrigin);
        jLabel1.setText(Language.getString("Origin"));
        jLabel1.setBounds(new Rectangle(10, 30, 51, 14));
        spOrigin.setBounds(new Rectangle(64, 26, 70, 23));
        spOrigin.addChangeListener(new AreaEditor_spOrigin_changeAdapter(this));
        jLabel2.setForeground(Color.black);
        jLabel2.setDisplayedMnemonic(Language.getString("N").charAt(0));
        jLabel2.setLabelFor(slTransp);
        jLabel2.setText(Language.getString("Transparency"));
        jLabel2.setBounds(new Rectangle(155, 7, 114, 14));
        slTransp.setBounds(new Rectangle(154, 26, 123, 23));
        slTransp.addChangeListener(new AreaEditor_slTransp_changeAdapter(this));
        jPanel5.setBorder(border7);
        jPanel5.setBounds(new Rectangle(5, 224, 286, 38));
        jPanel5.setLayout(null);
        jLabel3.setDisplayedMnemonic(Language.getString("K").charAt(0));
        jLabel3.setHorizontalAlignment(SwingConstants.TRAILING);
        jLabel3.setLabelFor(cbTreatNulls);
        jLabel3.setText(Language.getString("TreatNulls"));
        jLabel3.setBounds(new Rectangle(38, 15, 86, 14));        
        cbTreatNulls.setBounds(new Rectangle(141, 11, 100, 22));
        cbTreatNulls.addActionListener(new 
                AreaEditor_cbTreatNulls_actionAdapter(this));
        jPanel4.add(spOrigin);
        jPanel4.add(cbUseOrigin);
        jPanel4.add(jLabel1);
        jPanel4.add(jLabel2);
        jPanel4.add(slTransp);
        this.add(jPanel1);
        jPanel3.add(cbColorEach);
        jPanel3.add(bColor);
        this.add(jPanel4);
        jPanel2.add(rbStack);
        jPanel2.add(rbNone);
        jPanel2.add(rbStack100);
        this.add(jPanel3);
        jPanel1.add(cbStairs);
        jPanel1.add(cbInverted);
        jPanel1.add(cbSmoothed);
        jPanel1.add(jButton1);
        jPanel1.add(jButton2);
        jPanel1.add(bBorder);
        jPanel1.add(bLines);
        this.add(jPanel2);
        this.add(jPanel5);
        jPanel5.add(jLabel3);
        jPanel5.add(cbTreatNulls);
    }

    JPanel jPanel1 = new JPanel();
    Border border1 = BorderFactory.createEtchedBorder(Color.white,
            new Color(148, 145, 140));
    Border border2 = new TitledBorder(border1, Language.getString("Area"));
    JCheckBox cbStairs = new JCheckBox();
    JCheckBox cbInverted = new JCheckBox();
    JCheckBox cbSmoothed = new JCheckBox();
    private Area area;
    JButton jButton1 = new JButton();
    ButtonPen bBorder = new ButtonPen();
    JButton jButton2 = new JButton();
    ButtonPen bLines = new ButtonPen();
    JPanel jPanel2 = new JPanel();
    Border border3 = BorderFactory.createEtchedBorder(EtchedBorder.RAISED,
            Color.white, new Color(148, 145, 140));
    Border border4 = new TitledBorder(border3, Language.getString("Multiple_Areas"));
    JRadioButton rbNone = new JRadioButton();
    JRadioButton rbStack = new JRadioButton();
    JRadioButton rbStack100 = new JRadioButton();
    JPanel jPanel3 = new JPanel();
    Border border5 = BorderFactory.createEtchedBorder(Color.white,
            new Color(148, 145, 140));
    Border border6 = new TitledBorder(border5, Language.getString("Color"));
    JCheckBox cbColorEach = new JCheckBox();
    ButtonColor bColor = new ButtonColor();
    JPanel jPanel4 = new JPanel();
    Border border7 = BorderFactory.createEtchedBorder(EtchedBorder.RAISED,
            Color.white, new Color(148, 145, 140));
    JCheckBox cbUseOrigin = new JCheckBox();
    JLabel jLabel1 = new JLabel();
    JSpinner spOrigin = new JSpinner();
    JLabel jLabel2 = new JLabel();
    JSlider slTransp = new JSlider();
    JPanel jPanel5 = new JPanel();
    JLabel jLabel3 = new JLabel();
    JComboBox cbTreatNulls = new JComboBox();    

    public void cbStairs_actionPerformed(ActionEvent e) {
        area.setStairs(cbStairs.isSelected());
    }

    public void cbInverted_actionPerformed(ActionEvent e) {
        area.setInvertedStairs(cbInverted.isSelected());
    }

    public void jButton1_actionPerformed(ActionEvent e) {
        BrushEditor.edit(area.getAreaBrush());
    }

    public void jButton2_actionPerformed(ActionEvent e) {
        BrushEditor.edit(area.getBrush());
    }

    public void rbNone_actionPerformed(ActionEvent e) {
        area.setMultiArea(MultiAreas.NONE);
    }

    public void rbStack_actionPerformed(ActionEvent e) {
        area.setMultiArea(MultiAreas.STACKED);
    }

    public void rbStack100_actionPerformed(ActionEvent e) {
        area.setMultiArea(MultiAreas.STACKED100);
    }

    public void buttonColor1_actionPerformed(ActionEvent e) {
        area.setColor(bColor.getColor());
    }

    public void cbColorEach_actionPerformed(ActionEvent e) {
        area.setColorEach(cbColorEach.isSelected());
    }

    public void cbUseOrigin_actionPerformed(ActionEvent e) {
        area.setUseOrigin(cbUseOrigin.isSelected());
    }
    
    public void cbTreatNulls_actionPerformed(ActionEvent e) {
        if (area!=null) {
            area.setTreatNulls(TreatNullsStyle.fromInt(
                    cbTreatNulls.getSelectedIndex()));
        }
    }    
    
    public void cbSmoothed_actionPerformed(ActionEvent e) {
          area.setSmoothed(cbSmoothed.isSelected());
          area.SmoothSeries();
    }     

    public void spOrigin_stateChanged(ChangeEvent e) {
        area.setOrigin(((Double) spOrigin.getValue()).doubleValue());
    }

    public void slTransp_stateChanged(ChangeEvent e) {
        area.setTransparency(slTransp.getValue());
        area.repaint();
    }
}


class AreaEditor_slTransp_changeAdapter implements ChangeListener {
    private AreaEditor adaptee;
    AreaEditor_slTransp_changeAdapter(AreaEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void stateChanged(ChangeEvent e) {
        adaptee.slTransp_stateChanged(e);
    }
}


final class AreaEditor_spOrigin_changeAdapter implements ChangeListener {
    private AreaEditor adaptee;
    AreaEditor_spOrigin_changeAdapter(AreaEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void stateChanged(ChangeEvent e) {
        adaptee.spOrigin_stateChanged(e);
    }
}

class AreaEditor_cbSmoothed_actionAdapter implements ActionListener {
    private AreaEditor adaptee;
    AreaEditor_cbSmoothed_actionAdapter(AreaEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.cbSmoothed_actionPerformed(e);
    }
}


final class AreaEditor_cbUseOrigin_actionAdapter implements ActionListener {
    private AreaEditor adaptee;
    AreaEditor_cbUseOrigin_actionAdapter(AreaEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.cbUseOrigin_actionPerformed(e);
    }
}


final class AreaEditor_jCheckBox1_actionAdapter implements ActionListener {
    private AreaEditor adaptee;
    AreaEditor_jCheckBox1_actionAdapter(AreaEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.cbColorEach_actionPerformed(e);
    }
}


final class AreaEditor_buttonColor1_actionAdapter implements ActionListener {
    private AreaEditor adaptee;
    AreaEditor_buttonColor1_actionAdapter(AreaEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.buttonColor1_actionPerformed(e);
    }
}


final class AreaEditor_rbStack100_actionAdapter implements ActionListener {
    private AreaEditor adaptee;
    AreaEditor_rbStack100_actionAdapter(AreaEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.rbStack100_actionPerformed(e);
    }
}


final class AreaEditor_rbStack_actionAdapter implements ActionListener {
    private AreaEditor adaptee;
    AreaEditor_rbStack_actionAdapter(AreaEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.rbStack_actionPerformed(e);
    }
}


final class AreaEditor_rbNone_actionAdapter implements ActionListener {
    private AreaEditor adaptee;
    AreaEditor_rbNone_actionAdapter(AreaEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.rbNone_actionPerformed(e);
    }
}


final class AreaEditor_jButton2_actionAdapter implements ActionListener {
    private AreaEditor adaptee;
    AreaEditor_jButton2_actionAdapter(AreaEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.jButton2_actionPerformed(e);
    }
}


final class AreaEditor_jButton1_actionAdapter implements ActionListener {
    private AreaEditor adaptee;
    AreaEditor_jButton1_actionAdapter(AreaEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.jButton1_actionPerformed(e);
    }
}


final class AreaEditor_cbInverted_actionAdapter implements ActionListener {
    private AreaEditor adaptee;
    AreaEditor_cbInverted_actionAdapter(AreaEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.cbInverted_actionPerformed(e);
    }
}


final class AreaEditor_cbStairs_actionAdapter implements ActionListener {
    private AreaEditor adaptee;
    AreaEditor_cbStairs_actionAdapter(AreaEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.cbStairs_actionPerformed(e);
    }
}

final class AreaEditor_cbTreatNulls_actionAdapter implements ActionListener {
    private AreaEditor adaptee;
    AreaEditor_cbTreatNulls_actionAdapter(AreaEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.cbTreatNulls_actionPerformed(e);
    }
}

