package com.steema.teechart.editors.series;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import javax.swing.border.EtchedBorder;

import java.awt.Rectangle;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.steema.teechart.styles.Series;
import com.steema.teechart.styles.TriSurface;
import com.steema.teechart.editors.ButtonPen;
import com.steema.teechart.editors.BaseSeriesEditor;
import com.steema.teechart.editors.BrushEditor;
import com.steema.teechart.languages.Language;

/**
 * <p>Title: TriSurfaceEditor class</p>
 *
 * <p>Description: TriSurface Series Editor</p>
 *
 * <p>Copyright (c) 2004-2019 by Steema Software SL. All Rights Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 *
 */
public class TriSurfaceEditor extends BaseSeriesEditor {


    public TriSurfaceEditor() {
        super();
        try {
            jbInit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void setSeries(Series series, JTabbedPane tabs) {
        triSurface = (TriSurface) series;

        if (triSurface!=null)
        {
             if (grid3DEditor==null) {
                 grid3DEditor = new GridEditor();
                 grid3DEditor.setSeries(series, tabs);
                 tabs.add(Language.getString("Grid_3D"), grid3DEditor);
             }

        }
    }

    private void jbInit() throws Exception {
        this.setLayout(null);
        bPen.setBounds(new Rectangle(18, 11, 72, 23));
        bPen.setMnemonic(Language.getString("P").charAt(0));
        bPen.setText(Language.getString("Pen"));
        bOutLine.setBounds(new Rectangle(110, 37, 73, 23));
        bOutLine.setToolTipText("");
        bOutLine.setMnemonic(Language.getString("O").charAt(0));
        bOutLine.setText(Language.getString("Outline"));
        jLabel3.setDisplayedMnemonic(Language.getString("O").charAt(0));
        jLabel3.setHorizontalAlignment(SwingConstants.TRAILING);
        jLabel3.setText(Language.getString("Transparency"));
        jLabel3.setBounds(new Rectangle( -9, 154, 93, 14));
        bBrush.setBounds(new Rectangle(19, 37, 72, 23));
        bBrush.setMnemonic(Language.getString("B").charAt(0));
        bBrush.setText(Language.getString("Brush"));
        bBrush.addActionListener(new TriSurfaceEditor_bBrush_actionAdapter(this));
        this.add(bPen);
        this.add(bBrush);
        this.add(bOutLine);
    }

    Border border1 = BorderFactory.createEtchedBorder(Color.white,
            new Color(148, 145, 140));
    Border border2 = new TitledBorder(border1, Language.getString("custom"));
    private TriSurface triSurface;
    private GridEditor grid3DEditor;
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
    JLabel jLabel3 = new JLabel();
    ButtonPen bPen = new ButtonPen();
    ButtonPen bOutLine = new ButtonPen();
    JButton bBrush = new JButton();

    public void bBrush_actionPerformed(ActionEvent e) {
        BrushEditor.edit(this, triSurface.getBrush());
    }
}

final class TriSurfaceEditor_bBrush_actionAdapter implements ActionListener {
    private TriSurfaceEditor adaptee;
    TriSurfaceEditor_bBrush_actionAdapter(TriSurfaceEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.bBrush_actionPerformed(e);
    }
}



