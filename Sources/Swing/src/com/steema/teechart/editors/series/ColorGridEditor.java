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
import com.steema.teechart.styles.ColorGrid;
import com.steema.teechart.editors.ButtonPen;
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
public class ColorGridEditor extends com.steema.teechart.editors.series.GridEditor {


    public ColorGridEditor() {
        super();
        try {
            jbInit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void setSeries(Series series, JTabbedPane tabs) {
        colorGrid = (ColorGrid) series;
        if(colorGrid != null) {
            super.setSeries(series, tabs);
            cbCentered.setSelected(colorGrid.getCenteredPoints());
            bGrid.setPen(colorGrid.getPen());
        }
    }

    private void jbInit() throws Exception {
        border12 = BorderFactory.createEtchedBorder(Color.white,
                new Color(148, 145, 140));
        this.setLayout(null);
        cbCentered.setMnemonic(Language.getString("C").charAt(0));
        cbCentered.setText(Language.getString("Centered"));
        cbCentered.setBounds(new Rectangle(269, 130, 81, 21));
        cbCentered.addActionListener(new
                                     ColorGridEditor_cbCentered_actionAdapter(this));
        bGrid.setBounds(new Rectangle(8, 135, 73, 23));
        bGrid.setMnemonic(Language.getString("G").charAt(0));
        bGrid.setText(Language.getString("Grid"));
        this.add(cbCentered);
        this.add(bGrid);
    }

    Border border1 = BorderFactory.createEtchedBorder(Color.white,
            new Color(148, 145, 140));
    Border border2 = new TitledBorder(border1, Language.getString("custom"));
    private ColorGrid colorGrid;
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
    JCheckBox cbCentered = new JCheckBox();
    ButtonPen bGrid = new ButtonPen();
    public void cbCentered_actionPerformed(ActionEvent e) {
        colorGrid.setCenteredPoints(cbCentered.isSelected());
    }
}


class ColorGridEditor_cbCentered_actionAdapter implements ActionListener {
    private ColorGridEditor adaptee;
    ColorGridEditor_cbCentered_actionAdapter(ColorGridEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.cbCentered_actionPerformed(e);
    }
}


