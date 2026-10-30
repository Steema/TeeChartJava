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
import com.steema.teechart.styles.Gantt;
import com.steema.teechart.editors.ButtonPen;
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
public class GanttEditor extends BaseSeriesEditor {


    public GanttEditor() {
        super();
        try {
            jbInit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void setSeries(Series series, JTabbedPane tabs) {
        gantt = (Gantt) series;

        spPointVertSize.setValue(new Integer(gantt.getPointer().getVertSize()));
        cbColorEach.setSelected(gantt.getColorEach());
        bColor.setColor(gantt.getColor());
        bConnLines.setPen(gantt.getConnectingPen());
    }

    private void jbInit() throws Exception {
        border12 = BorderFactory.createEtchedBorder(Color.white,
                new Color(148, 145, 140));
        this.setLayout(null);
        spPointVertSize.setToolTipText("");
        spPointVertSize.setBounds(new Rectangle(71, 37, 53, 23));
        spPointVertSize.addChangeListener(new GanttEditor_spPointVertSize_changeAdapter(this));
        bConnLines.setBounds(new Rectangle(10, 7, 127, 23));
        bConnLines.setMnemonic(Language.getString("N").charAt(0));
        bConnLines.setText(Language.getString("Connecting_Lines"));
        jLabel2.setDisplayedMnemonic(Language.getString("D").charAt(0));
        jLabel2.setHorizontalAlignment(SwingConstants.TRAILING);
        jLabel2.setLabelFor(spPointVertSize);
        jLabel2.setText(Language.getString("Height"));
        jLabel2.setBounds(new Rectangle(11, 42, 53, 14));
        jLabel3.setDisplayedMnemonic(Language.getString("O").charAt(0));
        jLabel3.setHorizontalAlignment(SwingConstants.TRAILING);
        jLabel3.setText(Language.getString("Transparency"));
        jLabel3.setBounds(new Rectangle( -9, 154, 93, 14));
        jPanel1.setBorder(border12);
        jPanel1.setBounds(new Rectangle(11, 70, 127, 77));
        jPanel1.setLayout(null);
        bColor.setBounds(new Rectangle(20, 10, 83, 23));
        bColor.setMnemonic(Language.getString("C").charAt(0));
        bColor.setText(Language.getString("Color"));
        bColor.addActionListener(new GanttEditor_bColor_actionAdapter(this));
        cbColorEach.setMnemonic(Language.getString("E").charAt(0));
        cbColorEach.setText(Language.getString("color_each"));
        cbColorEach.setBounds(new Rectangle(17, 47, 92, 17));
        cbColorEach.addActionListener(new GanttEditor_cbColorEach_actionAdapter(this));
        this.add(bConnLines);
        this.add(jLabel2);
        this.add(spPointVertSize);
        this.add(jPanel1);
        jPanel1.add(bColor);
        jPanel1.add(cbColorEach);
    }

    Border border1 = BorderFactory.createEtchedBorder(Color.white,
            new Color(148, 145, 140));
    Border border2 = new TitledBorder(border1, Language.getString("custom"));
    private Gantt gantt;
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
    ButtonPen bConnLines = new ButtonPen();
    Border border10 = BorderFactory.createEtchedBorder(Color.white,
            new Color(148, 145, 140));
    Border border11 = new TitledBorder(border10, Language.getString("Line_mode"));
    JLabel jLabel2 = new JLabel();
    JSpinner spPointVertSize = new JSpinner();
    JLabel jLabel3 = new JLabel();
    JPanel jPanel1 = new JPanel();
    Border border12 = BorderFactory.createEtchedBorder(EtchedBorder.RAISED,
            Color.white, new Color(148, 145, 140));
    ButtonColor bColor = new ButtonColor();
    JCheckBox cbColorEach = new JCheckBox();

    public void spPointVertSize_stateChanged(ChangeEvent e) {
        gantt.getPointer().setVertSize(((Integer) spPointVertSize.getValue()).intValue());
    }

    public void cbColorEach_actionPerformed(ActionEvent e) {
        gantt.setColorEach(cbColorEach.isSelected());
    }

    public void bColor_actionPerformed(ActionEvent e) {
        gantt.setColor(bColor.getColor());
    }
}

class GanttEditor_spPointVertSize_changeAdapter implements ChangeListener {
    private GanttEditor adaptee;
    GanttEditor_spPointVertSize_changeAdapter(GanttEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void stateChanged(ChangeEvent e) {
        adaptee.spPointVertSize_stateChanged(e);
    }
}

class GanttEditor_cbColorEach_actionAdapter implements ActionListener {
    private GanttEditor adaptee;
    GanttEditor_cbColorEach_actionAdapter(GanttEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.cbColorEach_actionPerformed(e);
    }
}

final class GanttEditor_bColor_actionAdapter implements ActionListener {
    private GanttEditor adaptee;
    GanttEditor_bColor_actionAdapter(GanttEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.bColor_actionPerformed(e);
    }
}

