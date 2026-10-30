package com.steema.teechart.editors;

import java.awt.*;
import com.steema.teechart.languages.Language;
import java.awt.Rectangle;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import com.steema.teechart.axis.AxisLabels;
import com.steema.teechart.axis.Axis;
import com.steema.teechart.axis.AxisLabelAlign;

import com.steema.teechart.axis.AxisLabelStyle;

/**
 * <p>Title: TeeChart for Java</p>
 *
 * <p>Description: AxisIncrement editor panel</p>
 *
 * <p>Copyright (c) 2004-2019 by Steema Software SL. All Rights Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 *
 */
public class AxisLabelsEditor extends JPanel {

    private AxisLabels axisLabels;
    private Axis axis;
    private TextEditor labelText;
    private java.awt.Frame components = null;

    ButtonGroup group;
    JLabel jLabel1 = new JLabel();
    JCheckBox cbLabels = new JCheckBox();
    JPanel tab = new JPanel();
    Border border1 = BorderFactory.createEtchedBorder(Color.white,
            new Color(165, 163, 151));
    Border border2 = new TitledBorder(border1, Language.getString("Increment"));
    JTabbedPane jTabbedPane1 = new JTabbedPane();
    JPanel tab1 = new JPanel();
    JPanel tab2 = new JPanel();
    JPanel tab3 = new JPanel();
    JCheckBox cbMultiLine = new JCheckBox();
    JCheckBox cbRoundFirst = new JCheckBox();
    JCheckBox cbOnAxis = new JCheckBox();
    JCheckBox cbAlternate = new JCheckBox();
    JLabel jLabel2 = new JLabel();
    JLabel jLabel3 = new JLabel();
    JSpinner spLabelsSize = new JSpinner();
    JSpinner spLabelsAngle = new JSpinner();
    JSpinner spSepar = new JSpinner();
    Border border3 = BorderFactory.createEtchedBorder(Color.white,
            new Color(165, 163, 151));
    Border border4 = new TitledBorder(border3, Language.getString("Style"));
    JRadioButton rbAuto = new JRadioButton();
    JRadioButton rbNone = new JRadioButton();
    JRadioButton rbValue = new JRadioButton();
    JRadioButton rbMark = new JRadioButton();
    JRadioButton rbText = new JRadioButton();
    JCheckBox cbExpo = new JCheckBox();
    JLabel labelAxisFormat = new JLabel();
    JComboBox cbFormat = new JComboBox();
    JCheckBox cbLabelAlign = new JCheckBox();

    public AxisLabelsEditor() {
        try {
            jbInit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public AxisLabelsEditor(JFrame parent) {
        this();

// TODO      Editormisc.Utils.InsertForm(this,parent);
    }

    public void SetAxis(Axis a) {
        axis = a;
        axisLabels = a.getLabels();

        String tmp = "";

        // Axis Labels
        cbLabels.setSelected(axisLabels.getVisible());
        GetLabelStyle(axisLabels.iStyle.getValue());
        cbOnAxis.setSelected(axisLabels.getOnAxis());
        cbRoundFirst.setSelected(axisLabels.getRoundFirstLabel());
        spLabelsAngle.setValue(new Integer(axisLabels.getAngle()));
        spSepar.setValue(new Integer(axisLabels.getSeparation()));
        spLabelsSize.setValue(new Float(axisLabels.getCustomSize()));
        cbMultiLine.setSelected(axisLabels.getMultiLine());
        cbExpo.setSelected(axisLabels.getExponent());
        cbFormat.removeAllItems();

        if ( axis.isDateTime() ) {
            DefaultComboBoxModel model = new DefaultComboBoxModel();
            if ( axis.getMaximum()-axis.getMinimum()<=1 ) {
             //   model.addElement(System.Globalization.DateTimeFormatInfo.CurrentInfo.ShortTimePattern);
             //   model.addElement(System.Globalization.DateTimeFormatInfo.CurrentInfo.LongTimePattern);
                model.addElement(Language.getString("t"));
                model.addElement(Language.getString("T"));
                model.addElement(Language.getString("HH_mm"));
                model.addElement(Language.getString("hh_mm"));
                model.addElement(Language.getString("hh_mm_ss"));
                model.addElement(Language.getString("hh_mm_ss_tt"));
                model.addElement(Language.getString("hh_mm_ss_fff"));
            } else {
            //    model.addElement(System.Globalization.DateTimeFormatInfo.CurrentInfo.ShortDatePattern);
            //    model.addElement(System.Globalization.DateTimeFormatInfo.CurrentInfo.LongDatePattern);
                model.addElement(Language.getString("d"));
                model.addElement(Language.getString("D"));
                model.addElement(Language.getString("yyyy_mm_dd"));
                model.addElement(Language.getString("mm_dd_yyyy"));
                model.addElement(Language.getString("dd_mm_yyyy"));
                model.addElement(Language.getString("ddd"));
                model.addElement(Language.getString("dddd"));
                model.addElement(Language.getString("MMM"));
                model.addElement(Language.getString("MMMM"));
            }
            cbFormat.setModel(model);

            labelAxisFormat.setText(Language.getString("DateTimeFormat"));
            tmp=axisLabels.getDateTimeFormat();

            if ( tmp.length()==0 )
                tmp=axis.dateTimeDefaultFormat(axis.getMaximum()-axis.getMinimum()).toString();
      } else {
//          Editormisc.Utils.AddDefaultValueFormats(cbFormat.Items);
          labelAxisFormat.setText(Language.getString("ValuesFormat"));
          tmp=axisLabels.getValueFormat();
          if ( tmp.length()==0 )
              tmp=Language.getString("DefValueFormat");
      }

      DefaultComboBoxModel model = new DefaultComboBoxModel();
      model = (DefaultComboBoxModel)cbFormat.getModel();
      if (model.getIndexOf(tmp)==-1)
          model.addElement(tmp);

      cbFormat.setSelectedItem(tmp);

      if (labelText!=null)
        labelText.refreshControls(axis.getLabels().getFont());

      cbLabelAlign.setSelected((axisLabels.getAlign()==AxisLabelAlign.DEFAULT));
      cbLabelAlign.setEnabled((!axis.getHorizontal()) && (!axis.isDepthAxis));
    }

    private void GetLabelStyle(int styleIndex) {
      switch (styleIndex) {
        case 0: rbAuto.setSelected(true); break;
        case 1: rbNone.setSelected(true); break;
        case 2: rbValue.setSelected(true); break;
        case 3: rbMark.setSelected(true); break;
        case 4: rbText.setSelected(true); break;
      }
    }

    private void SetLabelStyle()
    {
      if (rbAuto.isSelected())
          axisLabels.setStyle(AxisLabelStyle.AUTO);
      else if (rbValue.isSelected())
          axisLabels.setStyle(AxisLabelStyle.VALUE);
      else if (rbText.isSelected())
          axisLabels.setStyle(AxisLabelStyle.TEXT);
      else if (rbMark.isSelected())
          axisLabels.setStyle(AxisLabelStyle.MARK);
      else if (rbNone.isSelected())
          axisLabels.setStyle(AxisLabelStyle.NONE);
    }

    public void dispose( boolean disposing ) {
      if( disposing )
        if(components != null)
            components.dispose();

// TODO      base.Dispose( disposing );
    }

    private void jbInit() throws Exception {
        jLabel1.setDisplayedMnemonic(Language.getString("Z").charAt(0));
        jLabel1.setHorizontalAlignment(SwingConstants.RIGHT);
        jLabel1.setLabelFor(spLabelsSize);
        jLabel1.setText(Language.getString("Size"));
        jLabel1.setBounds(new Rectangle(185, 11, 67, 14));
        this.setLayout(null);
        cbLabels.setMnemonic(Language.getString("V").charAt(0));
        cbLabels.setText(Language.getString("Visible"));
        cbLabels.setBounds(new Rectangle(2, 2, 113, 20));
        cbLabels.addActionListener(new AxisLabelsEditor_cbLabels_actionAdapter(this));
        tab.setBorder(border4);
        tab.setBounds(new Rectangle(6, 109, 296, 72));
        tab.setLayout(null);
        jTabbedPane1.setBounds(new Rectangle(5, 0, 374, 218));
        tab1.setLayout(null);
        tab2.setLayout(null);
        tab3.setLayout(null);
        cbMultiLine.setMnemonic(Language.getString("M").charAt(0));
        cbMultiLine.setText(Language.getString("Multi_line"));
        cbMultiLine.setBounds(new Rectangle(2, 21, 83, 23));
        cbMultiLine.addActionListener(new
                AxisLabelsEditor_cbMultiLine_actionAdapter(this));
        cbRoundFirst.setMnemonic(Language.getString("R").charAt(0));
        cbRoundFirst.setText(Language.getString("Round_First"));
        cbRoundFirst.setBounds(new Rectangle(2, 41, 83, 23));
        cbRoundFirst.addActionListener(new
                AxisLabelsEditor_cbRoundFirst_actionAdapter(this));
        cbOnAxis.setMnemonic(Language.getString("L").charAt(0));
        cbOnAxis.setText(Language.getString("Label_on_Axis"));
        cbOnAxis.setBounds(new Rectangle(2, 61, 103, 23));
        cbOnAxis.addActionListener(new AxisLabelsEditor_cbOnAxis_actionAdapter(this));
        cbAlternate.setMnemonic(Language.getString("A").charAt(0));
        cbAlternate.setText(Language.getString("Alternate"));
        cbAlternate.setBounds(new Rectangle(2, 81, 83, 23));
        cbAlternate.addActionListener(new
                AxisLabelsEditor_cbAlternate_actionAdapter(this));
        jLabel2.setDisplayedMnemonic(Language.getString("E").charAt(0));
        jLabel2.setHorizontalAlignment(SwingConstants.RIGHT);
        jLabel2.setLabelFor(spLabelsAngle);
        jLabel2.setText(Language.getString("Angle"));
        jLabel2.setBounds(new Rectangle(219, 38, 34, 15));
        jLabel3.setDisplayedMnemonic(Language.getString("I").charAt(0));
        jLabel3.setHorizontalAlignment(SwingConstants.RIGHT);
        jLabel3.setLabelFor(spSepar);
        jLabel3.setText(Language.getString("Min_Separation"));
        jLabel3.setBounds(new Rectangle(145, 65, 109, 15));
        spLabelsSize.setBounds(new Rectangle(256, 8, 47, 20));
        spLabelsSize.addChangeListener(new
                AxisLabelsEditor_spLabelsSize_changeAdapter(this));
        spLabelsAngle.setBounds(new Rectangle(256, 35, 48, 20));
        spLabelsAngle.addPropertyChangeListener(new
                AxisLabelsEditor_spLabelsAngle_propertyChangeAdapter(this));
        spLabelsAngle.addChangeListener(new
                AxisLabelsEditor_spLabelsAngle_changeAdapter(this));
        spSepar.setBounds(new Rectangle(256, 62, 49, 20));
        spSepar.addChangeListener(new AxisLabelsEditor_spSepar_changeAdapter(this));
        rbAuto.setMnemonic(Language.getString("A").charAt(0));
        rbAuto.setText(Language.getString("Auto"));
        rbAuto.setBounds(new Rectangle(16, 17, 91, 23));
        rbAuto.addActionListener(new AxisLabelsEditor_rbAuto_actionAdapter(this));
        rbNone.setMnemonic(Language.getString("N").charAt(0));
        rbNone.setText(Language.getString("None"));
        rbNone.setBounds(new Rectangle(16, 39, 91, 23));
        rbValue.setMnemonic(Language.getString("U").charAt(0));
        rbValue.setText(Language.getString("Value"));
        rbValue.setBounds(new Rectangle(107, 17, 91, 23));
        rbValue.addActionListener(new AxisLabelsEditor_rbValue_actionAdapter(this));
        rbMark.setToolTipText("");
        rbMark.setMnemonic(Language.getString("K").charAt(0));
        rbMark.setText(Language.getString("Mark"));
        rbMark.setBounds(new Rectangle(107, 39, 91, 23));
        rbText.setMnemonic(Language.getString("T").charAt(0));
        rbText.setText(Language.getString("Text"));
        rbText.setBounds(new Rectangle(215, 17, 71, 23));
        cbExpo.setMnemonic(Language.getString("E").charAt(0));
        cbExpo.setText(Language.getString("Exponential"));
        cbExpo.setBounds(new Rectangle(10, 7, 83, 23));
        cbExpo.addActionListener(new AxisLabelsEditor_cbExpo_actionAdapter(this));
        labelAxisFormat.setDisplayedMnemonic(Language.getString("F").charAt(0));
        labelAxisFormat.setLabelFor(cbFormat);
        labelAxisFormat.setText(Language.getString("Labels_Format"));
        labelAxisFormat.setBounds(new Rectangle(15, 38, 94, 15));
        cbFormat.setBounds(new Rectangle(13, 55, 164, 19));
        cbFormat.addPropertyChangeListener(new
                AxisLabelsEditor_cbFormat_propertyChangeAdapter(this));
        cbFormat.addActionListener(new AxisLabelsEditor_cbFormat_actionAdapter(this));
        cbLabelAlign.setMnemonic(Language.getString("A").charAt(0));
        cbLabelAlign.setText(Language.getString("Default_Alignment"));
        cbLabelAlign.setBounds(new Rectangle(13, 83, 152, 23));
        cbLabelAlign.addActionListener(new
                AxisLabelsEditor_cbLabelAlign_actionAdapter(this));
        jTabbedPane1.addChangeListener(new
                AxisLabelsEditor_jTabbedPane1_changeAdapter(this));
        rbValue.addActionListener(new AxisLabelsEditor_rbValue_actionAdapter(this));
        tab1.add(cbLabels);
        tab1.add(cbMultiLine);
        tab1.add(cbRoundFirst);
        tab1.add(cbOnAxis);
        tab1.add(cbAlternate);
        tab1.add(jLabel3);
        tab1.add(jLabel1);
        tab1.add(jLabel2);
        tab1.add(spLabelsSize);
        tab1.add(spLabelsAngle);
        tab1.add(spSepar);
        tab1.add(tab);
        tab.add(rbAuto);
        tab.add(rbNone);
        tab.add(rbText);
        tab.add(rbValue);
        tab.add(rbMark);
        jTabbedPane1.add(tab1, Language.getString("Style"));
        jTabbedPane1.add(tab2, Language.getString("Format"));
        tab2.add(cbExpo);
        tab2.add(labelAxisFormat);
        tab2.add(cbFormat);
        tab2.add(cbLabelAlign);
        jTabbedPane1.add(tab3, Language.getString("Text"));
        this.add(jTabbedPane1);

        group = new ButtonGroup();
        group.add(rbAuto);
        group.add(rbMark);
        group.add(rbValue);
        group.add(rbNone);
        group.add(rbText);
    }

    public void cbExpo_actionPerformed(ActionEvent e) {
        axisLabels.setExponent(cbExpo.isSelected());
    }

    public void cbFormat_actionPerformed(ActionEvent e) {
        if (axis.isDateTime())
            axisLabels.setDateTimeFormat(cbFormat.toString());
        else
            axisLabels.setValueFormat(cbFormat.toString());
    }

    public void cbLabelAlign_actionPerformed(ActionEvent e) {
        if (cbLabelAlign.isSelected())
          axisLabels.setAlign(AxisLabelAlign.DEFAULT);
        else
          axisLabels.setAlign(AxisLabelAlign.OPPOSITE);
    }

    public void cbLabels_actionPerformed(ActionEvent e) {
        axisLabels.setVisible(cbLabels.isSelected());
    }

    public void cbMultiLine_actionPerformed(ActionEvent e) {
        axisLabels.setMultiLine(cbMultiLine.isSelected());
    }

    public void cbRoundFirst_actionPerformed(ActionEvent e) {
        axisLabels.setRoundFirstLabel(cbRoundFirst.isSelected());
    }

    public void cbOnAxis_actionPerformed(ActionEvent e) {
        axisLabels.setOnAxis(cbOnAxis.isSelected());
    }

    public void spLabelsSize_stateChanged(ChangeEvent e) {
        axisLabels.setCustomSize(((Integer)spLabelsSize.getValue()).intValue());
    }

    public void spLabelsAngle_stateChanged(ChangeEvent e) {
        axisLabels.setAngle(((Integer)spLabelsAngle.getValue()).intValue());
    }

    public void spSepar_stateChanged(ChangeEvent e) {
        axisLabels.setSeparation(((Integer)spSepar.getValue()).intValue());
    }

    public void rbValue_actionPerformed(ActionEvent e) {
        if (group.getSelection().isSelected())
            SetLabelStyle();
    }

    public void jTabbedPane1_stateChanged(ChangeEvent e) {
        if (jTabbedPane1.getSelectedIndex()==2)  // tabLabelText
          if (labelText==null) {
            labelText=new TextEditor(tab3,axisLabels.getFont());

          }
    }

    public void cbAlternate_actionPerformed(ActionEvent e) {
        axisLabels.setAlternate(cbAlternate.isSelected());
    }

    public void cbFormat_propertyChange(PropertyChangeEvent evt) {
        cbFormat_actionPerformed(new ActionEvent(this,0,"")) ;
    }

    public void spLabelsAngle_propertyChange(PropertyChangeEvent evt) {
        spLabelsAngle_stateChanged(new ChangeEvent(this));
    }
}


final class AxisLabelsEditor_jTabbedPane1_changeAdapter implements ChangeListener {
    private AxisLabelsEditor adaptee;
    AxisLabelsEditor_jTabbedPane1_changeAdapter(AxisLabelsEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void stateChanged(ChangeEvent e) {
        adaptee.jTabbedPane1_stateChanged(e);
    }
}


final class AxisLabelsEditor_rbValue_actionAdapter implements ActionListener {
    private AxisLabelsEditor adaptee;
    AxisLabelsEditor_rbValue_actionAdapter(AxisLabelsEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {

        adaptee.rbValue_actionPerformed(e);
    }
}


final class AxisLabelsEditor_rbAuto_actionAdapter implements ActionListener {
    private AxisLabelsEditor adaptee;
    AxisLabelsEditor_rbAuto_actionAdapter(AxisLabelsEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.rbValue_actionPerformed(e);
    }
}


final class AxisLabelsEditor_spSepar_changeAdapter implements ChangeListener {
    private AxisLabelsEditor adaptee;
    AxisLabelsEditor_spSepar_changeAdapter(AxisLabelsEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void stateChanged(ChangeEvent e) {
        adaptee.spSepar_stateChanged(e);
    }
}


final class AxisLabelsEditor_spLabelsAngle_changeAdapter implements ChangeListener {
    private AxisLabelsEditor adaptee;
    AxisLabelsEditor_spLabelsAngle_changeAdapter(AxisLabelsEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void stateChanged(ChangeEvent e) {
        adaptee.spLabelsAngle_stateChanged(e);
    }
}


class AxisLabelsEditor_spLabelsAngle_propertyChangeAdapter implements
        PropertyChangeListener {
    private AxisLabelsEditor adaptee;
    AxisLabelsEditor_spLabelsAngle_propertyChangeAdapter(AxisLabelsEditor
            adaptee) {
        this.adaptee = adaptee;
    }

    public void propertyChange(PropertyChangeEvent evt) {
        adaptee.spLabelsAngle_propertyChange(evt);
    }
}


final class AxisLabelsEditor_cbAlternate_actionAdapter implements ActionListener {
    private AxisLabelsEditor adaptee;
    AxisLabelsEditor_cbAlternate_actionAdapter(AxisLabelsEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.cbAlternate_actionPerformed(e);
    }
}


final class AxisLabelsEditor_spLabelsSize_changeAdapter implements ChangeListener {
    private AxisLabelsEditor adaptee;
    AxisLabelsEditor_spLabelsSize_changeAdapter(AxisLabelsEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void stateChanged(ChangeEvent e) {
        adaptee.spLabelsSize_stateChanged(e);
    }
}


final class AxisLabelsEditor_cbOnAxis_actionAdapter implements ActionListener {
    private AxisLabelsEditor adaptee;
    AxisLabelsEditor_cbOnAxis_actionAdapter(AxisLabelsEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.cbOnAxis_actionPerformed(e);
    }
}


final class AxisLabelsEditor_cbRoundFirst_actionAdapter implements ActionListener {
    private AxisLabelsEditor adaptee;
    AxisLabelsEditor_cbRoundFirst_actionAdapter(AxisLabelsEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.cbRoundFirst_actionPerformed(e);
    }
}


final class AxisLabelsEditor_cbMultiLine_actionAdapter implements ActionListener {
    private AxisLabelsEditor adaptee;
    AxisLabelsEditor_cbMultiLine_actionAdapter(AxisLabelsEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.cbMultiLine_actionPerformed(e);
    }
}


final class AxisLabelsEditor_cbLabels_actionAdapter implements ActionListener {
    private AxisLabelsEditor adaptee;
    AxisLabelsEditor_cbLabels_actionAdapter(AxisLabelsEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.cbLabels_actionPerformed(e);
    }
}


final class AxisLabelsEditor_cbLabelAlign_actionAdapter implements ActionListener {
    private AxisLabelsEditor adaptee;
    AxisLabelsEditor_cbLabelAlign_actionAdapter(AxisLabelsEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.cbLabelAlign_actionPerformed(e);
    }
}


final class AxisLabelsEditor_cbFormat_actionAdapter implements ActionListener {
    private AxisLabelsEditor adaptee;
    AxisLabelsEditor_cbFormat_actionAdapter(AxisLabelsEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.cbFormat_actionPerformed(e);
    }
}


class AxisLabelsEditor_cbFormat_propertyChangeAdapter implements
        PropertyChangeListener {
    private AxisLabelsEditor adaptee;
    AxisLabelsEditor_cbFormat_propertyChangeAdapter(AxisLabelsEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void propertyChange(PropertyChangeEvent evt) {
        adaptee.cbFormat_propertyChange(evt);
    }
}


final class AxisLabelsEditor_cbExpo_actionAdapter implements ActionListener {
    private AxisLabelsEditor adaptee;
    AxisLabelsEditor_cbExpo_actionAdapter(AxisLabelsEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.cbExpo_actionPerformed(e);
    }
}

