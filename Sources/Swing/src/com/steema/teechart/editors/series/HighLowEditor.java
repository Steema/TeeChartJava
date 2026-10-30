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
import com.steema.teechart.styles.HighLow;
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
public class HighLowEditor extends BaseSeriesEditor {


    public HighLowEditor() {
        super();
        try {
            jbInit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void setSeries(Series series, JTabbedPane tabs) {
        highLow = (HighLow) series;

        if (series!=null)
        {
          bLines.setPen(highLow.getPen());
          bHigh.setPen(highLow.getHighPen());
          bLow.setPen(highLow.getLowPen());
          bColor.setColor(highLow.getColor());
      }
    }

    private void jbInit() throws Exception {
        this.setLayout(null);
        bLowBrush.setBounds(new Rectangle(179, 39, 72, 23));
        bLowBrush.setMnemonic(Language.getString("A").charAt(0));
        bLowBrush.setText(Language.getString("Pattern"));
        bLowBrush.addActionListener(new HighLowEditor_bLowBrush_actionAdapter(this));
        bHigh.setBounds(new Rectangle(97, 12, 73, 23));
        bHigh.setToolTipText("");
        bHigh.setMnemonic(Language.getString("H").charAt(0));
        bHigh.setText(Language.getString("High"));
        jLabel3.setDisplayedMnemonic(Language.getString("O").charAt(0));
        jLabel3.setHorizontalAlignment(SwingConstants.TRAILING);
        jLabel3.setText(Language.getString("Transparency"));
        jLabel3.setBounds(new Rectangle( -9, 154, 93, 14));
        bColor.setBounds(new Rectangle(14, 40, 74, 23));
        bColor.setMnemonic(Language.getString("C").charAt(0));
        bColor.setText(Language.getString("Color"));
        bColor.addActionListener(new HighLowEditor_bColor_actionAdapter(this));
        bLines.setBounds(new Rectangle(14, 12, 73, 23));
        bLines.setToolTipText("");
        bLines.setMnemonic(Language.getString("L").charAt(0));
        bLines.setText(Language.getString("Lines"));
        bLow.setBounds(new Rectangle(97, 40, 73, 23));
        bLow.setToolTipText("");
        bLow.setMnemonic(Language.getString("L").charAt(0));
        bLow.setText(Language.getString("Low"));
        bBrush.setBounds(new Rectangle(179, 12, 72, 23));
        bBrush.setMnemonic(Language.getString("P").charAt(0));
        bBrush.setText(Language.getString("Pattern"));
        bBrush.addActionListener(new HighLowEditor_bBrush_actionAdapter(this));
        cbColorEach.setMnemonic(Language.getString("C").charAt(0));
        cbColorEach.setText(Language.getString("color_each"));
        cbColorEach.setBounds(new Rectangle(11, 70, 105, 20));
        cbColorEach.addActionListener(new HighLowEditor_cbColorEach_actionAdapter(this));
        this.add(bColor);
        this.add(bHigh);
        this.add(bLines);
        this.add(bLow);
        this.add(bLowBrush);
        this.add(bBrush);
        this.add(cbColorEach);
    }

    Border border1 = BorderFactory.createEtchedBorder(Color.white,
            new Color(148, 145, 140));
    Border border2 = new TitledBorder(border1, Language.getString("custom"));
    private HighLow highLow;
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
    JButton bLowBrush = new JButton();
    ButtonPen bHigh = new ButtonPen();
    Border border10 = BorderFactory.createEtchedBorder(Color.white,
            new Color(148, 145, 140));
    Border border11 = new TitledBorder(border10, Language.getString("Line_mode"));
    JLabel jLabel3 = new JLabel();
    ButtonColor bColor = new ButtonColor();
    ButtonPen bLines = new ButtonPen();
    ButtonPen bLow = new ButtonPen();
    JButton bBrush = new JButton();
    JCheckBox cbColorEach = new JCheckBox();

    public void cbColorEach_actionPerformed(ActionEvent e) {
        highLow.setColorEach(cbColorEach.isSelected());
    }

    public void bBrush_actionPerformed(ActionEvent e) {
        BrushEditor.edit(this, highLow.getHighBrush());
    }

    public void bLowBrush_actionPerformed(ActionEvent e) {
        BrushEditor.edit(this, highLow.getLowBrush());
    }

    public void bColor_actionPerformed(ActionEvent e) {
        highLow.setColor(bColor.getColor());
    }

}

class HighLowEditor_cbColorEach_actionAdapter implements ActionListener {
    private HighLowEditor adaptee;
    HighLowEditor_cbColorEach_actionAdapter(HighLowEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.cbColorEach_actionPerformed(e);
    }
}

final class HighLowEditor_bBrush_actionAdapter implements ActionListener {
    private HighLowEditor adaptee;
    HighLowEditor_bBrush_actionAdapter(HighLowEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.bBrush_actionPerformed(e);
    }
}

final class HighLowEditor_bLowBrush_actionAdapter implements ActionListener {
    private HighLowEditor adaptee;
    HighLowEditor_bLowBrush_actionAdapter(HighLowEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.bLowBrush_actionPerformed(e);
    }
}

final class HighLowEditor_bColor_actionAdapter implements ActionListener {
    private HighLowEditor adaptee;
    HighLowEditor_bColor_actionAdapter(HighLowEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.bColor_actionPerformed(e);
    }
}



