package com.steema.teechart.editors.series;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.BevelBorder;

import java.awt.Rectangle;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.steema.teechart.styles.Series;
import com.steema.teechart.styles.Waterfall;
import com.steema.teechart.editors.ButtonPen;
import com.steema.teechart.editors.ChartPenEditor;
import com.steema.teechart.languages.Language;

/**
 * <p>Title: WaterFallEditor class</p>
 *
 * <p>Description: WaterFall Series Editor</p>
 *
 * <p>Copyright (c) 2004-2019 by Steema Software SL. All Rights Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 *
 */
public class WaterFallEditor extends com.steema.teechart.editors.series.SurfaceEditor {


    public WaterFallEditor() {
        super();
        try {
            jbInit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void setSeries(Series series, JTabbedPane tabs) {
        waterfall = (Waterfall) series;
        if(waterfall != null) {
            bLines.setPen(waterfall.getPen());
            super.setSeries(series, tabs);
        }
    }

    private void jbInit() throws Exception {
        border12 = BorderFactory.createEtchedBorder(Color.white,
                new Color(148, 145, 140));
        this.setLayout(null);
        bLines.setBounds(new Rectangle(11, 97, 76, 23));
        bLines.setMnemonic(Language.getString("L").charAt(0));
        bLines.setText(Language.getString("Lines"));
        bLines.addActionListener(new WaterFallEditor_bLines_actionAdapter(this));
        this.add(bLines);
    }

    Border border1 = BorderFactory.createEtchedBorder(Color.white,
            new Color(148, 145, 140));
    Border border2 = new TitledBorder(border1, Language.getString("custom"));
    private Waterfall waterfall;
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
    Border border10 = BorderFactory.createEtchedBorder(Color.white,
            new Color(148, 145, 140));
    Border border11 = new TitledBorder(border10, Language.getString("Line_mode"));
    Border border12 = BorderFactory.createEtchedBorder(EtchedBorder.RAISED,
            Color.white, new Color(148, 145, 140));
    Border border13 = BorderFactory.createEtchedBorder(Color.white,
            new Color(148, 145, 140));
    Border border14 = new TitledBorder(border13, Language.getString("Color"));
    Border border15 = BorderFactory.createBevelBorder(BevelBorder.LOWERED,
            Color.white, Color.white, new Color(115, 114, 105),
            new Color(165, 163, 151));
    Border border16 = new TitledBorder(border15, Language.getString("Drawing_Mode"));
    Border border17 = BorderFactory.createEtchedBorder(EtchedBorder.RAISED,
            Color.white, new Color(165, 163, 151));
    Border border18 = new TitledBorder(border17, Language.getString("Drawing_Mode"));
    ButtonPen bLines = new ButtonPen();
    ChartPenEditor penEditor = new ChartPenEditor();

    public void bLines_actionPerformed(ActionEvent e) {
        penEditor.setVisible(true);
        penEditor.refreshControls(waterfall.getWaterLines());
    }
}


class WaterFallEditor_bLines_actionAdapter implements ActionListener {
    private WaterFallEditor adaptee;
    WaterFallEditor_bLines_actionAdapter(WaterFallEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.bLines_actionPerformed(e);
    }
}


