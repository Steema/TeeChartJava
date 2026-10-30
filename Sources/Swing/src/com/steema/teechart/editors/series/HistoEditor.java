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
import com.steema.teechart.styles.Histogram;
import com.steema.teechart.editors.BrushEditor;
import com.steema.teechart.editors.BaseSeriesEditor;
import com.steema.teechart.editors.ButtonColor;
import com.steema.teechart.editors.ChartPenEditor;
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
public class HistoEditor extends BaseSeriesEditor {


    public HistoEditor() {
        super();
        try {
            jbInit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void setSeries(Series series, JTabbedPane tabs) {
        histogram = (Histogram) series;

        spTransp.setValue(new Integer(histogram.getTransparency()));
        cbColorEach.setSelected(histogram.getColorEach());
    }

    private void jbInit() throws Exception {
        border12 = BorderFactory.createEtchedBorder(Color.white,
                new Color(148, 145, 140));
        this.setLayout(null);
        spTransp.setToolTipText("");
        spTransp.setBounds(new Rectangle(167, 98, 53, 23));
        spTransp.addChangeListener(new HistoEditor_spTransp_changeAdapter(this));
        jLabel2.setDisplayedMnemonic(Language.getString("T").charAt(0));
        jLabel2.setHorizontalAlignment(SwingConstants.TRAILING);
        jLabel2.setLabelFor(spTransp);
        jLabel2.setText(Language.getString("Transparency"));
        jLabel2.setBounds(new Rectangle(77, 103, 83, 14));
        jLabel3.setDisplayedMnemonic(Language.getString("O").charAt(0));
        jLabel3.setHorizontalAlignment(SwingConstants.TRAILING);
        jLabel3.setText(Language.getString("Transparency"));
        jLabel3.setBounds(new Rectangle( -9, 154, 93, 14));
        jPanel1.setBorder(border14);
        jPanel1.setBounds(new Rectangle(98, 6, 117, 81));
        jPanel1.setLayout(null);
        bColor.setBounds(new Rectangle(20, 21, 77, 23));
        bColor.setMnemonic(Language.getString("C").charAt(0));
        bColor.setText(Language.getString("Color"));
        bColor.addActionListener(new HistoEditor_bColor_actionAdapter(this));
        cbColorEach.setMnemonic(Language.getString("E").charAt(0));
        cbColorEach.setText(Language.getString("color_cach"));
        cbColorEach.setBounds(new Rectangle(17, 53, 92, 17));
        cbColorEach.addActionListener(new HistoEditor_cbColorEach_actionAdapter(this));
        bBorder.setBounds(new Rectangle(9, 9, 79, 23));
        bBorder.setMnemonic(Language.getString("B").charAt(0));
        bBorder.setText(Language.getString("Border"));
        bBorder.addActionListener(new HistoEditor_bBorder_actionAdapter(this));
        bLines.setBounds(new Rectangle(10, 36, 79, 23));
        bLines.setMnemonic(Language.getString("L").charAt(0));
        bLines.setText(Language.getString("Lines"));
        bLines.addActionListener(new HistoEditor_bLines_actionAdapter(this));
        bPattern.setBounds(new Rectangle(10, 63, 79, 23));
        bPattern.setMnemonic(Language.getString("P").charAt(0));
        bPattern.setText(Language.getString("Pattern"));
        bPattern.addActionListener(new HistoEditor_bPattern_actionAdapter(this));
        jPanel1.add(bColor);
        jPanel1.add(cbColorEach);
        this.add(jLabel2);
        this.add(spTransp);
        this.add(bBorder);
        this.add(bLines);
        this.add(bPattern);
        this.add(jPanel1);
    }

    Border border1 = BorderFactory.createEtchedBorder(Color.white,
            new Color(148, 145, 140));
    Border border2 = new TitledBorder(border1, Language.getString("custom"));
    private Histogram histogram;
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
    JSpinner spTransp = new JSpinner();
    JLabel jLabel3 = new JLabel();
    JPanel jPanel1 = new JPanel();
    Border border12 = BorderFactory.createEtchedBorder(EtchedBorder.RAISED,
            Color.white, new Color(148, 145, 140));
    ButtonColor bColor = new ButtonColor();
    JCheckBox cbColorEach = new JCheckBox();
    JButton bBorder = new JButton();
    JButton bLines = new JButton();
    JButton bPattern = new JButton();
    Border border13 = BorderFactory.createEtchedBorder(Color.white,
            new Color(148, 145, 140));
    Border border14 = new TitledBorder(border13, Language.getString("Color"));

    public void cbColorEach_actionPerformed(ActionEvent e) {
        histogram.setColorEach(cbColorEach.isSelected());
    }

    public void spTransp_stateChanged(ChangeEvent e) {
        histogram.setTransparency(((Integer)spTransp.getValue()).intValue());
    }

    public void bColor_actionPerformed(ActionEvent e) {
        histogram.setColor(bColor.getColor());
    }

    public void bBorder_actionPerformed(ActionEvent e) {
// to uncomment        PenEditor.edit(histogram.getLinePen());
        ChartPenEditor.showDialog(null, histogram.getLinePen());
    }

    public void bLines_actionPerformed(ActionEvent e) {
// to uncomment        PenEditor.edit(histogram.getLinesPen());
        ChartPenEditor.showDialog(null, histogram.getLinesPen());

    }

    public void bPattern_actionPerformed(ActionEvent e) {
        BrushEditor.edit(histogram.getBrush());
    }
}

class HistoEditor_cbColorEach_actionAdapter implements ActionListener {
    private HistoEditor adaptee;
    HistoEditor_cbColorEach_actionAdapter(HistoEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.cbColorEach_actionPerformed(e);
    }
}

class HistoEditor_spTransp_changeAdapter implements ChangeListener {
    private HistoEditor adaptee;
    HistoEditor_spTransp_changeAdapter(HistoEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void stateChanged(ChangeEvent e) {
        adaptee.spTransp_stateChanged(e);
    }
}

final class HistoEditor_bColor_actionAdapter implements ActionListener {
    private HistoEditor adaptee;
    HistoEditor_bColor_actionAdapter(HistoEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.bColor_actionPerformed(e);
    }
}

final class HistoEditor_bBorder_actionAdapter implements ActionListener {
    private HistoEditor adaptee;
    HistoEditor_bBorder_actionAdapter(HistoEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.bBorder_actionPerformed(e);
    }
}

final class HistoEditor_bLines_actionAdapter implements ActionListener {
    private HistoEditor adaptee;
    HistoEditor_bLines_actionAdapter(HistoEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.bLines_actionPerformed(e);
    }
}

final class HistoEditor_bPattern_actionAdapter implements ActionListener {
    private HistoEditor adaptee;
    HistoEditor_bPattern_actionAdapter(HistoEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.bPattern_actionPerformed(e);
    }
}

