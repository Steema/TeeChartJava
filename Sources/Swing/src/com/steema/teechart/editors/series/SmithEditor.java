package com.steema.teechart.editors.series;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;

import java.awt.Rectangle;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.steema.teechart.styles.Series;
import com.steema.teechart.styles.Smith;
import com.steema.teechart.editors.BaseSeriesEditor;
import com.steema.teechart.editors.ButtonPen;
import com.steema.teechart.editors.ChartFontEditor;
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
public class SmithEditor extends BaseSeriesEditor {


    public SmithEditor() {
        super();
        try {
            jbInit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private CircledEditor circled;

    public void setSeries(Series series, JTabbedPane tabs) {
        smith = (Smith) series;

        if (smith!=null)
        {
          cbColorEach.setSelected(smith.getColorEach());
          cbR.setSelected(smith.getRLabels());
          cbC.setSelected(smith.getCLabels());
          eImag.setText(smith.getImagSymbol());
          bRPen.setPen(smith.getRCirclePen());
          bCPen.setPen(smith.getCCirclePen());
          bCircle.setPen(smith.getCirclePen());
          bBorder.setPen(smith.getPen());

          if(circled == null) {
              circled = new CircledEditor();
              circled.setSeries(smith, tabs);
              tabs.add(circled, Language.getString("Circled"), 1);
          }
          tabs.add(new SeriesPointerEditor(smith.getPointer()),Language.getString("Point"), tabs.getTabCount());
        }
    }

    private void jbInit() throws Exception {
        border12 = BorderFactory.createEtchedBorder(Color.white,
                new Color(148, 145, 140));
        this.setLayout(null);
        jLabel2.setDisplayedMnemonic(Language.getString("I").charAt(0));
        jLabel2.setHorizontalAlignment(SwingConstants.TRAILING);
        jLabel2.setLabelFor(null);
        jLabel2.setText(Language.getString("Imag_Symbol"));
        jLabel2.setBounds(new Rectangle(138, 78, 83, 14));
        jLabel3.setDisplayedMnemonic(Language.getString("O").charAt(0));
        jLabel3.setHorizontalAlignment(SwingConstants.TRAILING);
        jLabel3.setText(Language.getString("Transparency"));
        jLabel3.setBounds(new Rectangle( -9, 154, 93, 14));
        cbColorEach.setMnemonic(Language.getString("O").charAt(0));
        cbColorEach.setText(Language.getString("Color_each"));
        cbColorEach.setBounds(new Rectangle(17, 76, 92, 17));
        cbColorEach.addActionListener(new SmithEditor_cbColorEach_actionAdapter(this));
        bCFont.setBounds(new Rectangle(196, 12, 79, 23));
        bCFont.setMnemonic(Language.getString("F").charAt(0));
        bCFont.setText(Language.getString("Font"));
        bCFont.addActionListener(new SmithEditor_bCFont_actionAdapter(this));
        cbC.setMnemonic(Language.getString("C").charAt(0));
        cbC.setText(Language.getString("CLabels"));
        cbC.setBounds(new Rectangle(17, 15, 92, 17));
        cbC.addActionListener(new SmithEditor_cbC_actionAdapter(this));
        cbR.setMnemonic(Language.getString("R").charAt(0));
        cbR.setText(Language.getString("RLabels"));
        cbR.setBounds(new Rectangle(17, 45, 92, 17));
        cbR.addActionListener(new SmithEditor_cbR_actionAdapter(this));
        bBorder.setBounds(new Rectangle(19, 103, 76, 23));
        bBorder.setMnemonic(Language.getString("B").charAt(0));
        bBorder.setText(Language.getString("Border"));
        bCircle.setBounds(new Rectangle(99, 103, 76, 23));
        bCircle.setMnemonic(Language.getString("C").charAt(0));
        bCircle.setText(Language.getString("Circle"));
        bCPen.setBounds(new Rectangle(117, 12, 76, 23));
        bCPen.setMnemonic(Language.getString("P").charAt(0));
        bCPen.setText(Language.getString("CPen"));
        bRPen.setBounds(new Rectangle(117, 42, 76, 23));
        bRPen.setMnemonic(Language.getString("E").charAt(0));
        bRPen.setText(Language.getString("RPen"));
        bRFont.setBounds(new Rectangle(196, 42, 79, 23));
        bRFont.setMnemonic(Language.getString("N").charAt(0));
        bRFont.setText(Language.getString("Font"));
        bRFont.addActionListener(new SmithEditor_bRFont_actionAdapter(this));
        eImag.setBounds(new Rectangle(227, 76, 47, 20));
        eImag.addActionListener(new SmithEditor_eImag_actionAdapter(this));
        this.add(cbR);
        this.add(cbColorEach);
        this.add(cbC);
        this.add(bBorder);
        this.add(bCircle);
        this.add(bCPen);
        this.add(bRPen);
        this.add(bCFont);
        this.add(bRFont);
        this.add(jLabel2);
        this.add(eImag);
    }

    Border border1 = BorderFactory.createEtchedBorder(Color.white,
            new Color(148, 145, 140));
    Border border2 = new TitledBorder(border1, Language.getString("custom"));
    private Smith smith;
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
    JCheckBox cbColorEach = new JCheckBox();
    JButton bCFont = new JButton();
    Border border13 = BorderFactory.createEtchedBorder(Color.white,
            new Color(148, 145, 140));
    Border border14 = new TitledBorder(border13, Language.getString("Color"));
    JCheckBox cbC = new JCheckBox();
    JCheckBox cbR = new JCheckBox();
    ButtonPen bBorder = new ButtonPen();
    ButtonPen bCircle = new ButtonPen();
    ButtonPen bCPen = new ButtonPen();
    ButtonPen bRPen = new ButtonPen();
    JButton bRFont = new JButton();
    JTextField eImag = new JTextField();

    public void cbC_actionPerformed(ActionEvent e) {
        smith.setCLabels(cbC.isSelected());
    }

    public void cbR_actionPerformed(ActionEvent e) {
        smith.setRLabels(cbR.isSelected());
    }

    public void cbColorEach_actionPerformed(ActionEvent e) {
        smith.setColorEach(cbColorEach.isSelected());
    }

    public void eImag_actionPerformed(ActionEvent e) {
        smith.setImagSymbol(eImag.getText());
    }

    public void bCFont_actionPerformed(ActionEvent e) {
        ChartFontEditor.edit(this,smith.getCLabelsFont());
    }

    public void bRFont_actionPerformed(ActionEvent e) {
        ChartFontEditor.edit(this,smith.getRLabelsFont());
    }
}

class SmithEditor_cbC_actionAdapter implements ActionListener {
    private SmithEditor adaptee;
    SmithEditor_cbC_actionAdapter(SmithEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.cbC_actionPerformed(e);
    }
}

class SmithEditor_cbR_actionAdapter implements ActionListener {
    private SmithEditor adaptee;
    SmithEditor_cbR_actionAdapter(SmithEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.cbR_actionPerformed(e);
    }
}

class SmithEditor_cbColorEach_actionAdapter implements ActionListener {
    private SmithEditor adaptee;
    SmithEditor_cbColorEach_actionAdapter(SmithEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.cbColorEach_actionPerformed(e);
    }
}

class SmithEditor_eImag_actionAdapter implements ActionListener {
    private SmithEditor adaptee;
    SmithEditor_eImag_actionAdapter(SmithEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.eImag_actionPerformed(e);
    }
}

final class SmithEditor_bCFont_actionAdapter implements ActionListener {
    private SmithEditor adaptee;
    SmithEditor_bCFont_actionAdapter(SmithEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.bCFont_actionPerformed(e);
    }
}

final class SmithEditor_bRFont_actionAdapter implements ActionListener {
    private SmithEditor adaptee;
    SmithEditor_bRFont_actionAdapter(SmithEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.bRFont_actionPerformed(e);
    }
}

