package com.steema.teechart.editors.series;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;

import java.awt.Rectangle;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.steema.teechart.styles.Series;
import com.steema.teechart.styles.Contour;
import com.steema.teechart.editors.ButtonPen;
import com.steema.teechart.editors.BaseSeriesEditor;
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
public class ContourEditor extends BaseSeriesEditor {


    public ContourEditor() {
        super();
        try {
            jbInit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private com.steema.teechart.styles.ContourLevel contourLevel;

    public void setSeries(Series series, JTabbedPane tabs) {
        contour = (Contour) series;

        spYPos.setValue(new Double(contour.getYPosition()));
        spLevel.setValue(new Double(0));
        spNum.setValue(new Integer(contour.getNumLevels()));
        cbYPosLevel.setSelected(contour.getYPositionLevel());
        cbAutoLevels.setSelected(contour.getAutomaticLevels());
        spNum.setEnabled(cbAutoLevels.isSelected());
        cbColorEach.setSelected(contour.getColorEach());
        cbColorEach.setEnabled(cbAutoLevels.isSelected());
        buttonPen1.setPen(contour.getPen());

        if (grid3DEditor==null) {
           grid3DEditor = new GridEditor();
           grid3DEditor.setSeries(series, tabs);
           tabs.add(Language.getString("Grid_3D"), grid3DEditor);
        }


    }

    private void jbInit() throws Exception {
        this.setLayout(null);
        jPanel3.setBorder(border9);
        jPanel3.setBounds(new Rectangle(6, 67, 361, 92));
        jPanel3.setLayout(null);
        cbAutoLevels.setMnemonic(Language.getString("A").charAt(0));
        cbAutoLevels.setText(Language.getString("Automatic"));
        cbAutoLevels.setBounds(new Rectangle(11, 18, 109, 23));
        cbAutoLevels.addActionListener(new ContourEditor_cbAutoLevels_actionAdapter(this));
        buttonPen1.setBounds(new Rectangle(10, 9, 91, 23));
        buttonPen1.setMnemonic(Language.getString("P").charAt(0));
        buttonPen1.setText(Language.getString("Pen"));
        spYPos.setToolTipText("");
        spYPos.setBounds(new Rectangle(143, 37, 78, 23));
        spYPos.addChangeListener(new ContourEditor_spYPos_changeAdapter(this));
        jLabel4.setDisplayedMnemonic(Language.getString("O").charAt(0));
        jLabel4.setHorizontalAlignment(SwingConstants.TRAILING);
        jLabel4.setLabelFor(spYPos);
        jLabel4.setText(Language.getString("Vertical_Position"));
        jLabel4.setBounds(new Rectangle(39, 41, 93, 14));
        cbYPosLevel.setMnemonic(Language.getString("L").charAt(0));
        cbYPosLevel.setText(Language.getString("Levels_Position"));
        cbYPosLevel.setBounds(new Rectangle(229, 37, 122, 22));
        cbYPosLevel.addActionListener(new ContourEditor_cbYPosLevel_actionAdapter(this));
        jLabel1.setDisplayedMnemonic(Language.getString("O").charAt(0));
        jLabel1.setHorizontalAlignment(SwingConstants.TRAILING);
        jLabel1.setText(Language.getString("Height"));
        jLabel1.setBounds(new Rectangle(123, 271, 51, 14));
        jLabel3.setHorizontalAlignment(SwingConstants.TRAILING);
        jLabel3.setLabelFor(spNum);
        jLabel3.setText(Language.getString("Number"));
        jLabel3.setBounds(new Rectangle(112, 20, 55, 19));
        jSpinner1.setToolTipText("");
        jSpinner1.setBounds(new Rectangle(221, 218, 56, 23));
        spNum.setBounds(new Rectangle(175, 18, 61, 23));
        spNum.addChangeListener(new ContourEditor_spNum_changeAdapter(this));
        cbColorEach.setMnemonic(Language.getString("C").charAt(0));
        cbColorEach.setText(Language.getString("Color_each"));
        cbColorEach.setBounds(new Rectangle(242, 18, 109, 23));
        cbColorEach.addActionListener(new ContourEditor_cbColorEach_actionAdapter(this));
        jLabel5.setHorizontalAlignment(SwingConstants.TRAILING);
        jLabel5.setLabelFor(spLevel);
        jLabel5.setText(Language.getString("Level"));
        jLabel5.setBounds(new Rectangle(22, 56, 55, 19));
        spLevel.setBounds(new Rectangle(84, 53, 61, 23));
        spLevel.addChangeListener(new ContourEditor_spLevel_changeAdapter(this));
        eValue.setBounds(new Rectangle(152, 53, 112, 23));
        eValue.addActionListener(new ContourEditor_eValue_actionAdapter(this));
        jPanel3.add(jLabel1);
        jPanel3.add(cbAutoLevels);
        jPanel3.add(jSpinner1);
        jPanel3.add(spNum);
        jPanel3.add(spLevel);
        jPanel3.add(eValue);
        jPanel3.add(cbColorEach);
        jPanel3.add(jLabel3);
        jPanel3.add(jLabel5);
        this.add(spYPos);
        this.add(buttonPen1);
        this.add(jPanel3);
        this.add(jLabel4);
        this.add(cbYPosLevel);
    }

    Border border1 = BorderFactory.createEtchedBorder(Color.white,
            new Color(148, 145, 140));
    Border border2 = new TitledBorder(border1, Language.getString("contour"));
    private Contour contour;
    private GridEditor grid3DEditor;
    Border border3 = BorderFactory.createEtchedBorder(EtchedBorder.RAISED,
            Color.white, new Color(148, 145, 140));
    Border border4 = new TitledBorder(border3, Language.getString("Multiple_contours"));
    JPanel jPanel3 = new JPanel();
    Border border5 = BorderFactory.createEtchedBorder(Color.white,
            new Color(148, 145, 140));
    Border border6 = new TitledBorder(border5, Language.getString("Color"));
    JCheckBox cbAutoLevels = new JCheckBox();
    Border border7 = BorderFactory.createEtchedBorder(EtchedBorder.RAISED,
            Color.white, new Color(148, 145, 140));
    ButtonPen buttonPen1 = new ButtonPen();
    JSpinner spYPos = new JSpinner();
    JLabel jLabel4 = new JLabel();
    JCheckBox cbYPosLevel = new JCheckBox();
    Border border8 = BorderFactory.createEtchedBorder(Color.white,
            new Color(148, 145, 140));
    Border border9 = new TitledBorder(border8, Language.getString("Levels"));
    JLabel jLabel1 = new JLabel();
    JLabel jLabel3 = new JLabel();
    JSpinner jSpinner1 = new JSpinner();
    JSpinner spNum = new JSpinner();
    JCheckBox cbColorEach = new JCheckBox();
    JLabel jLabel5 = new JLabel();
    JSpinner spLevel = new JSpinner();
    JTextField eValue = new JTextField();


    public void cbAutoLevels_actionPerformed(ActionEvent e) {
        contour.setAutomaticLevels(cbAutoLevels.isSelected());
    }

    public void cbYPosLevel_actionPerformed(ActionEvent e) {
        contour.setYPositionLevel(cbYPosLevel.isSelected());
    }

    public void cbColorEach_actionPerformed(ActionEvent e) {
        contour.setColorEach(cbColorEach.isSelected());
    }

    public void spYPos_stateChanged(ChangeEvent e) {
        contour.setYPosition(((Double) spYPos.getValue()).doubleValue());
    }

    public void spNum_stateChanged(ChangeEvent e) {
        if (contour!=null)
        {
          contour.setNumLevels(((Integer)spNum.getValue()).intValue());
          contour.createAutoLevels();
        }
    }

    public void spLevel_stateChanged(ChangeEvent e) {
        if (!contour.getLevels().isEmpty())
        {
            Object value = spLevel.getValue();
            int tmp = -1;
            if(value instanceof Double) {
                tmp = ((Double) value).intValue();
            }
            else if(value instanceof Integer) {
                tmp = ((Integer) value).intValue();
            }
            contourLevel = contour.getLevels().getLevel(tmp);
            eValue.setText(Double.toString(contourLevel.getUpToValue()));
        }
    }

    public void eValue_actionPerformed(ActionEvent e) {
        contourLevel.setUpToValue(Double.parseDouble(eValue.getText()));
        contour.invalidate();
    }
}

class ContourEditor_cbAutoLevels_actionAdapter implements ActionListener {
    private ContourEditor adaptee;
    ContourEditor_cbAutoLevels_actionAdapter(ContourEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.cbAutoLevels_actionPerformed(e);
    }
}

class ContourEditor_cbYPosLevel_actionAdapter implements ActionListener {
    private ContourEditor adaptee;
    ContourEditor_cbYPosLevel_actionAdapter(ContourEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.cbYPosLevel_actionPerformed(e);
    }
}

class ContourEditor_cbColorEach_actionAdapter implements ActionListener {
    private ContourEditor adaptee;
    ContourEditor_cbColorEach_actionAdapter(ContourEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.cbColorEach_actionPerformed(e);
    }
}

class ContourEditor_spYPos_changeAdapter implements ChangeListener {
    private ContourEditor adaptee;
    ContourEditor_spYPos_changeAdapter(ContourEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void stateChanged(ChangeEvent e) {
        adaptee.spYPos_stateChanged(e);
    }
}

class ContourEditor_spNum_changeAdapter implements ChangeListener {
    private ContourEditor adaptee;
    ContourEditor_spNum_changeAdapter(ContourEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void stateChanged(ChangeEvent e) {
        adaptee.spNum_stateChanged(e);
    }
}


class ContourEditor_spLevel_changeAdapter implements ChangeListener {
    private ContourEditor adaptee;
    ContourEditor_spLevel_changeAdapter(ContourEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void stateChanged(ChangeEvent e) {
        adaptee.spLevel_stateChanged(e);
    }
}

class ContourEditor_eValue_actionAdapter implements ActionListener {
    private ContourEditor adaptee;
    ContourEditor_eValue_actionAdapter(ContourEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.eValue_actionPerformed(e);
    }
}

