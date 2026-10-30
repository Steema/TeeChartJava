package com.steema.teechart.editors.series;
// ... Verify code...
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
import com.steema.teechart.styles.Custom3DPalette;
import com.steema.teechart.styles.Custom3DGrid;
import com.steema.teechart.styles.PaletteStyle;
import com.steema.teechart.editors.BaseSeriesEditor;
import com.steema.teechart.editors.ButtonColor;
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

public class GridEditor extends BaseSeriesEditor {


    public GridEditor() {
        super();
        try {
            jbInit();

             DefaultComboBoxModel model = new DefaultComboBoxModel();
             model.addElement(Language.getString("Pale"));
             model.addElement(Language.getString("Strong"));
             model.addElement(Language.getString("GreyScale"));
             cbPalStyle.setModel(model);


        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void setSeries(Series series, JTabbedPane tabs) {
        custom3DPalette = (Custom3DPalette) series;
        SetProperties();
    }

    protected void SetProperties() {

        setting=true;


        if (custom3DPalette instanceof com.steema.teechart.styles.Custom3DGrid) // Custom3DGrid
        {
            seriesgrid = (Custom3DGrid) custom3DPalette;
            spXGrid.setValue(new Integer(seriesgrid.getNumXValues()));
            spZGrid.setValue(new Integer(seriesgrid.getNumZValues()));
            cbIrreg.setSelected(seriesgrid.getIrregularGrid());

//            boolean b=seriesgrid.CanCreateValues();
//            spXGrid.setEnabled(b);
//            spZGrid.setEnabled(b);
        }
        else
        {
            jPanel2.setVisible(false);
            cbIrreg.setVisible(false);
        }

        spDepth.setValue(new Integer(custom3DPalette.getTimesZOrder()));
        spPalette.setValue(new Integer(custom3DPalette.getPaletteSteps()));

        if (custom3DPalette.getPaletteStyle()==PaletteStyle.PALE)
          cbPalStyle.setSelectedIndex(0);
        else
          if (custom3DPalette.getPaletteStyle()==PaletteStyle.STRONG)
          cbPalStyle.setSelectedIndex(1);
        else
          if (custom3DPalette.getPaletteStyle()==PaletteStyle.GRAYSCALE)
          cbPalStyle.setSelectedIndex(2);
//        else
//          if (custom3DPalette.getPaletteStyle()==PaletteStyle.InvertedGray)
//          cbPalStyle.setSelectedIndex(3);
       // else
       //   cbPalStyle.setSelectedIndex(4);

        rbRange.setSelected(custom3DPalette.getUseColorRange());
        rbPalette.setSelected(custom3DPalette.getUsePalette());
        rbColor.setSelected((! custom3DPalette.getUseColorRange()) && (! custom3DPalette.getUsePalette()));
        bColor.setColor(custom3DPalette.getColor());
        bFromColor.setColor(custom3DPalette.getStartColor());
        bMidColor.setColor(custom3DPalette.getMidColor());
        bToColor.setColor(custom3DPalette.getEndColor());


      ButtonGroup group = new ButtonGroup();
      group.add(rbColor);
      group.add(rbRange);
      group.add(rbPalette);


      setting=false;
    }

    private void jbInit() throws Exception {
        this.setLayout(null);
        spPalette.setBounds(new Rectangle(138, 71, 53, 23));
        spPalette.addChangeListener(new GridEditor_spPalette_changeAdapter(this));
        jLabel3.setDisplayedMnemonic(Language.getString("T").charAt(0));
        jLabel3.setHorizontalAlignment(SwingConstants.TRAILING);
        jLabel3.setLabelFor(spPalette);
        jLabel3.setText(Language.getString("Steps"));
        jLabel3.setBounds(new Rectangle(84, 75, 47, 14));
        cbIrreg.setMnemonic(Language.getString("I").charAt(0));
        cbIrreg.setText(Language.getString("Irregular"));
        cbIrreg.setBounds(new Rectangle(269, 104, 84, 23));
        cbIrreg.addActionListener(new GridEditor_cbIrreg_actionAdapter(this));
        jPanel1.setBorder(border38);
        jPanel1.setBounds(new Rectangle(5, 3, 258, 130));
        jPanel1.setLayout(null);
        rbColor.setMnemonic(Language.getString("S").charAt(0));
        rbColor.setText(Language.getString("Series"));
        rbColor.setBounds(new Rectangle(10, 19, 63, 23));
        rbColor.addActionListener(new GridEditor_rbColor_actionAdapter(this));
        rbRange.setMnemonic(Language.getString("R").charAt(0));
        rbRange.setText(Language.getString("Range"));
        rbRange.setBounds(new Rectangle(10, 44, 63, 23));
        rbRange.addActionListener(new GridEditor_rbRange_actionAdapter(this));
        rbPalette.setMnemonic(Language.getString("P").charAt(0));
        rbPalette.setText(Language.getString("Palette"));
        rbPalette.setBounds(new Rectangle(10, 71, 63, 23));
        rbPalette.addActionListener(new GridEditor_rbPalette_actionAdapter(this));
        bToColor.setBounds(new Rectangle(184, 45, 40, 23));
        bToColor.setMnemonic(Language.getString("O").charAt(0));
        bToColor.setText("");
        bColor.setBounds(new Rectangle(97, 18, 70, 23));
        bColor.setMnemonic(Language.getString("O").charAt(0));
        bColor.setText(Language.getString("Color"));
        bFromColor.setBounds(new Rectangle(97, 45, 40, 23));
        bFromColor.setMnemonic(Language.getString("O").charAt(0));
        bFromColor.setText("");
        bMidColor.setBounds(new Rectangle(140, 45, 41, 23));
        bMidColor.setMnemonic(Language.getString("C").charAt(0));
        bMidColor.setText("");
        cbPalStyle.setBounds(new Rectangle(139, 98, 108, 23));
        cbPalStyle.addActionListener(new GridEditor_cbPalStyle_actionAdapter(this));
        jLabel2.setDisplayedMnemonic(Language.getString("Y").charAt(0));
        jLabel2.setHorizontalAlignment(SwingConstants.TRAILING);
        jLabel2.setLabelFor(cbPalStyle);
        jLabel2.setText(Language.getString("Style"));
        jLabel2.setBounds(new Rectangle(84, 103, 47, 14));
        jPanel3.setBorder(border40);
        jPanel3.setBounds(new Rectangle(265, 3, 127, 102));
        jPanel3.setLayout(null);
        spXGrid.setBounds(new Rectangle(64, 17, 52, 23));
        spXGrid.addChangeListener(new GridEditor_spXGrid_changeAdapter(this));
        spZGrid.setBounds(new Rectangle(64, 44, 52, 23));
        spZGrid.addChangeListener(new GridEditor_spXGrid_changeAdapter(this));
        spDepth.setBounds(new Rectangle(64, 71, 52, 23));
        spDepth.addChangeListener(new GridEditor_spDepth_changeAdapter(this));
        jLabel1.setDisplayedMnemonic(Language.getString("X").charAt(0));
        jLabel1.setHorizontalAlignment(SwingConstants.TRAILING);
        jLabel1.setLabelFor(spXGrid);
        jLabel1.setText(Language.getString("X"));
        jLabel1.setBounds(new Rectangle(28, 19, 26, 18));
        jLabel4.setDisplayedMnemonic(Language.getString("Z").charAt(0));
        jLabel4.setHorizontalAlignment(SwingConstants.TRAILING);
        jLabel4.setLabelFor(spZGrid);
        jLabel4.setText(Language.getString("Z"));
        jLabel4.setBounds(new Rectangle(28, 46, 26, 18));
        jLabel5.setDisplayedMnemonic(Language.getString("D").charAt(0));
        jLabel5.setHorizontalAlignment(SwingConstants.TRAILING);
        jLabel5.setLabelFor(spDepth);
        jLabel5.setText(Language.getString("Depth"));
        jLabel5.setBounds(new Rectangle(9, 73, 45, 18));
        jPanel1.add(rbColor);
        jPanel1.add(bColor);
        jPanel1.add(rbRange);
        jPanel1.add(rbPalette);
        jPanel1.add(bFromColor);
        jPanel1.add(bMidColor);
        jPanel1.add(jLabel3);
        jPanel1.add(spPalette);
        jPanel1.add(bToColor);
        jPanel1.add(cbPalStyle);
        jPanel1.add(jLabel2);
        jPanel3.add(jLabel1);
        jPanel3.add(spXGrid);
        jPanel3.add(spDepth);
        jPanel3.add(spZGrid);
        jPanel3.add(jLabel4);
        jPanel3.add(jLabel5);
        this.add(cbIrreg);
        this.add(jPanel3);
        this.add(jPanel1);
    }

    Border border1 = BorderFactory.createEtchedBorder(Color.white,
            new Color(148, 145, 140));
    Border border2 = new TitledBorder(border1, Language.getString("Candle"));

    private Custom3DPalette custom3DPalette;
    private Custom3DGrid seriesgrid;
    private boolean setting;

    JPanel jPanel2 = new JPanel();
    Border border3 = BorderFactory.createEtchedBorder(EtchedBorder.RAISED,
            Color.white, new Color(148, 145, 140));
    Border border4 = new TitledBorder(border3, Language.getString("Multiple_Candles"));
    Border border5 = BorderFactory.createEtchedBorder(Color.white,
            new Color(148, 145, 140));
    Border border6 = new TitledBorder(border5, Language.getString("Color"));
    Border border7 = BorderFactory.createEtchedBorder(EtchedBorder.RAISED,
            Color.white, new Color(148, 145, 140));
    Border border8 = BorderFactory.createEtchedBorder(EtchedBorder.RAISED,
            Color.white, new Color(148, 145, 140));
    Border border9 = new TitledBorder(border8, Language.getString("Style"));
    JSpinner spPalette = new JSpinner();
    JLabel jLabel3 = new JLabel();
    JCheckBox cbIrreg = new JCheckBox();
    JPanel jPanel1 = new JPanel();
    Border border10 = BorderFactory.createEtchedBorder(EtchedBorder.RAISED,
            Color.white, new Color(165, 163, 151));
    Border border11 = BorderFactory.createEtchedBorder(EtchedBorder.RAISED,
            Color.white, new Color(165, 163, 151));
    Border border12 = new TitledBorder(border11, Language.getString("Width_Units"));
    Border border13 = BorderFactory.createBevelBorder(BevelBorder.LOWERED,
            Color.white, Color.white, new Color(115, 114, 105),
            new Color(165, 163, 151));
    Border border14 = new TitledBorder(border13, Language.getString("Width_Units"));
    Border border15 = BorderFactory.createEtchedBorder(Color.white,
            new Color(165, 163, 151));
    Border border16 = new TitledBorder(border15, Language.getString("Width_Units"));
    Border border17 = BorderFactory.createEtchedBorder(EtchedBorder.RAISED,
            Color.white, new Color(165, 163, 151));
    Border border18 = new TitledBorder(border17, Language.getString("Width_Units"));
    Border border19 = BorderFactory.createEtchedBorder(EtchedBorder.RAISED,
            Color.white, new Color(148, 145, 140));
    Border border20 = new TitledBorder(border19, Language.getString("Style"));
    Border border21 = BorderFactory.createEtchedBorder(EtchedBorder.RAISED,
            Color.white, new Color(165, 163, 151));
    Border border22 = new TitledBorder(border21, Language.getString("Width_Units"));
    JRadioButton rbColor = new JRadioButton();
    JRadioButton rbRange = new JRadioButton();
    Border border23 = BorderFactory.createEtchedBorder(EtchedBorder.RAISED,
            Color.white, new Color(148, 145, 140));
    Border border24 = new TitledBorder(border23, Language.getString("Color_Mode"));
    JRadioButton rbPalette = new JRadioButton();
    Border border25 = BorderFactory.createEtchedBorder(EtchedBorder.RAISED,
            Color.white, new Color(165, 163, 151));
    Border border26 = new TitledBorder(border25, Language.getString("Color_Mode"));
    ButtonColor bToColor = new ButtonColor();
    ButtonColor bColor = new ButtonColor();
    ButtonColor bFromColor = new ButtonColor();
    ButtonColor bMidColor = new ButtonColor();
    JComboBox cbPalStyle = new JComboBox();
    JLabel jLabel2 = new JLabel();
    JPanel jPanel3 = new JPanel();
    Border border27 = BorderFactory.createEmptyBorder();
    Border border28 = new TitledBorder(border27, Language.getString("Grid_Size"));
    Border border29 = BorderFactory.createBevelBorder(BevelBorder.LOWERED,
            Color.white, Color.white, new Color(115, 114, 105),
            new Color(165, 163, 151));
    Border border30 = new TitledBorder(border29, Language.getString("Grid_Size"));
    Border border31 = BorderFactory.createBevelBorder(BevelBorder.LOWERED,
            Color.white, Color.white, new Color(115, 114, 105),
            new Color(165, 163, 151));
    Border border32 = new TitledBorder(border31, Language.getString("Grid_Size"));
    Border border33 = BorderFactory.createBevelBorder(BevelBorder.RAISED,
            Color.white, Color.white, new Color(115, 114, 105),
            new Color(165, 163, 151));
    Border border34 = new TitledBorder(border33, Language.getString("Grid_Size"));
    Border border35 = BorderFactory.createEtchedBorder(EtchedBorder.RAISED,
            Color.white, new Color(165, 163, 151));
    Border border36 = new TitledBorder(border35, Language.getString("Color_Mode"));
    Border border37 = BorderFactory.createEtchedBorder(EtchedBorder.RAISED,
            Color.white, new Color(165, 163, 151));
    Border border38 = new TitledBorder(border37, Language.getString("Color_Mode"));
    Border border39 = BorderFactory.createEtchedBorder(EtchedBorder.RAISED,
            Color.white, new Color(165, 163, 151));
    Border border40 = new TitledBorder(border39, Language.getString("Grid_Size"));
    JSpinner spXGrid = new JSpinner();
    JSpinner spZGrid = new JSpinner();
    JSpinner spDepth = new JSpinner();
    JLabel jLabel1 = new JLabel();
    JLabel jLabel4 = new JLabel();
    JLabel jLabel5 = new JLabel();


   public void bColor_actionPerformed(ActionEvent e) {

       Color newColor=bColor.getColor();
       if (newColor != custom3DPalette.getColor())
       {
         rbColor.setSelected(true);
//         custom3DPalette.setColor(newColor);
        }
    }

    public void rbColor_actionPerformed(ActionEvent e) {
        custom3DPalette.setColorEach(true);
        custom3DPalette.setColorEach(false);
        custom3DPalette.setUseColorRange(false);
        custom3DPalette.setUsePalette(false);
    }

    public void rbRange_actionPerformed(ActionEvent e) {
        custom3DPalette.setUseColorRange(true);
        custom3DPalette.setUsePalette(false);
    }

    public void rbPalette_actionPerformed(ActionEvent e) {
        custom3DPalette.setUseColorRange(false);
        custom3DPalette.setUsePalette(true);
    }

    public void cbIrreg_actionPerformed(ActionEvent e) {

        ((Custom3DGrid)(custom3DPalette)).setIrregularGrid(cbIrreg.isSelected());
    }

    public void cbPalStyle_actionPerformed(ActionEvent e) {
        if (! setting)
        {
          rbPalette.setSelected(true);
          if (cbPalStyle.getSelectedIndex()==0)
            custom3DPalette.setPaletteStyle(PaletteStyle.PALE);
          else
            if (cbPalStyle.getSelectedIndex()==1)
            custom3DPalette.setPaletteStyle(PaletteStyle.STRONG);
          else
            if (cbPalStyle.getSelectedIndex()==2)
            custom3DPalette.setPaletteStyle(PaletteStyle.GRAYSCALE);
//          else
//            if (cbPalStyle.getSelectedIndex()==3)
//            custom3DPalette.setPaletteStyle(PaletteStyle.InvertedGray);
//          else
//            custom3DPalette.setPaletteStyl(PaletteStyle.Rainbow);
      }
    }

    public void bFromColor_actionPerformed(ActionEvent e) {
         Color newColor=bFromColor.getColor();
         if (newColor != custom3DPalette.getStartColor())
         {
           rbRange.setSelected(true);
//           custom3DPalette.setStartColor(newColor);
         }
    }

    public void bMidColor_actionPerformed(ActionEvent e) {
        Color newColor=bMidColor.getColor();
        if (newColor != custom3DPalette.getMidColor())
        {
          rbRange.setSelected(true);
//           custom3DPalette.setMidColor(newColor);
         }
    }

    public void bToColor_actionPerformed(ActionEvent e) {
        Color newColor=bToColor.getColor();
        if (newColor != custom3DPalette.getEndColor())
        {
          rbRange.setSelected(true);
//           custom3DPalette.setEndColor(newColor);
         }
    }

    public void spPalette_stateChanged(ChangeEvent e) {
        if (! setting)
        {
          rbPalette.setSelected(true);
          custom3DPalette.setPaletteSteps(((Integer)spPalette.getValue()).intValue());
      }
    }

    public void spXGrid_stateChanged(ChangeEvent e) {
        if (!setting)
        {
//          if ((spXGrid.getValue() !=  seriesgrid.getNumXValues()) || (spZGrid.getValue() != seriesgrid.getNumZValues()))
//            seriesgrid.CreateValues(((Integer)spXGrid.getValue()).intValue(),((Integer)spZGrid.getValue()).intValue());
        }
    }

    public void spDepth_stateChanged(ChangeEvent e) {
        custom3DPalette.setTimesZOrder(((Integer)spDepth.getValue()).intValue());
    }
}


final class GridEditor_bColor_actionAdapter implements ActionListener {
    private GridEditor adaptee;
    GridEditor_bColor_actionAdapter(GridEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.bColor_actionPerformed(e);
    }
}

final class GridEditor_rbColor_actionAdapter implements ActionListener {
    private GridEditor adaptee;
    GridEditor_rbColor_actionAdapter(GridEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.rbColor_actionPerformed(e);
    }
}

final class GridEditor_rbRange_actionAdapter implements ActionListener {
    private GridEditor adaptee;
    GridEditor_rbRange_actionAdapter(GridEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.rbRange_actionPerformed(e);
    }
}

final class GridEditor_rbPalette_actionAdapter implements ActionListener {
    private GridEditor adaptee;
    GridEditor_rbPalette_actionAdapter(GridEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.rbPalette_actionPerformed(e);
    }
}

class GridEditor_cbIrreg_actionAdapter implements ActionListener {
    private GridEditor adaptee;
    GridEditor_cbIrreg_actionAdapter(GridEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.cbIrreg_actionPerformed(e);
    }
}

final class GridEditor_cbPalStyle_actionAdapter implements ActionListener {
    private GridEditor adaptee;
    GridEditor_cbPalStyle_actionAdapter(GridEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.cbPalStyle_actionPerformed(e);
    }
}

final class GridEditor_bFromColor_actionAdapter implements ActionListener {
    private GridEditor adaptee;
    GridEditor_bFromColor_actionAdapter(GridEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.bFromColor_actionPerformed(e);
    }
}

final class GridEditor_bMidColor_actionAdapter implements ActionListener {
    private GridEditor adaptee;
    GridEditor_bMidColor_actionAdapter(GridEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.bMidColor_actionPerformed(e);
    }
}

final class GridEditor_bToColor_actionAdapter implements ActionListener {
    private GridEditor adaptee;
    GridEditor_bToColor_actionAdapter(GridEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.bToColor_actionPerformed(e);
    }
}

class GridEditor_spPalette_changeAdapter implements ChangeListener {
    private GridEditor adaptee;
    GridEditor_spPalette_changeAdapter(GridEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void stateChanged(ChangeEvent e) {
        adaptee.spPalette_stateChanged(e);
    }
}

class GridEditor_spXGrid_changeAdapter implements ChangeListener {
    private GridEditor adaptee;
    GridEditor_spXGrid_changeAdapter(GridEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void stateChanged(ChangeEvent e) {
        adaptee.spXGrid_stateChanged(e);
    }
}

class GridEditor_spDepth_changeAdapter implements ChangeListener {
    private GridEditor adaptee;
    GridEditor_spDepth_changeAdapter(GridEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void stateChanged(ChangeEvent e) {
        adaptee.spDepth_stateChanged(e);
    }
}



