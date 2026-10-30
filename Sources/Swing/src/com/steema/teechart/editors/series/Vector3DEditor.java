package com.steema.teechart.editors.series;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import java.awt.Rectangle;
import java.awt.Color;

import com.steema.teechart.styles.Series;
import com.steema.teechart.styles.Vector3D;
import com.steema.teechart.editors.ButtonPen;
import com.steema.teechart.editors.BaseSeriesEditor;
import com.steema.teechart.languages.Language;

/**
 * <p>Title: Vector3DEditor class</p>
 *
 * <p>Description: Vector3D Series Editor</p>
 *
 * <p>Copyright (c) 2004-2019 by Steema Software SL. All Rights Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 *
 */
public class Vector3DEditor extends BaseSeriesEditor {


    public Vector3DEditor() {
        super();
        try {
            jbInit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void setSeries(Series series, JTabbedPane tabs) {
        vector3D = (Vector3D) series;

        spWidth.setValue(new Float(vector3D.getArrowWidth()));
        spHeight.setValue(new Float(vector3D.getArrowHeight()));
        bStart.setPen(vector3D.getStartArrow());
        bEnd.setPen(vector3D.getEndArrow());
    }

    private void jbInit() throws Exception {
        this.setLayout(null);
        bStart.setBounds(new Rectangle(24, 66, 73, 23));
        bStart.setToolTipText("");
        bStart.setMnemonic(Language.getString("S").charAt(0));
        bStart.setText(Language.getString("Start"));
        jLabel3.setDisplayedMnemonic(Language.getString("O").charAt(0));
        jLabel3.setHorizontalAlignment(SwingConstants.TRAILING);
        jLabel3.setText(Language.getString("Transparency"));
        jLabel3.setBounds(new Rectangle( -9, 154, 93, 14));
        bEnd.setBounds(new Rectangle(113, 66, 73, 23));
        bEnd.setToolTipText("");
        bEnd.setMnemonic(Language.getString("E").charAt(0));
        bEnd.setText(Language.getString("End"));
        jLabel1.setDisplayedMnemonic(Language.getString("A").charAt(0));
        jLabel1.setHorizontalAlignment(SwingConstants.TRAILING);
        jLabel1.setText(Language.getString("Arrow_Width"));
        jLabel1.setBounds(new Rectangle(14, 12, 82, 15));
        jLabel2.setDisplayedMnemonic(Language.getString("H").charAt(0));
        jLabel2.setHorizontalAlignment(SwingConstants.TRAILING);
        jLabel2.setText(Language.getString("Arrow_Height"));
        jLabel2.setBounds(new Rectangle(14, 38, 82, 15));
        spWidth.setBounds(new Rectangle(101, 6, 62, 24));
        spWidth.addChangeListener(new Vector3DEditor_spWidth_changeAdapter(this));
        spHeight.setBounds(new Rectangle(101, 33, 61, 24));
        spHeight.addChangeListener(new Vector3DEditor_spHeight_changeAdapter(this));
        this.add(jLabel1);
        this.add(jLabel2);
        this.add(bStart);
        this.add(bEnd);
        this.add(spWidth);
        this.add(spHeight);
    }

    Border border1 = BorderFactory.createEtchedBorder(Color.white,
            new Color(148, 145, 140));
    Border border2 = new TitledBorder(border1, Language.getString("custom"));
    private Vector3D vector3D;
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
    ButtonPen bStart = new ButtonPen();
    Border border10 = BorderFactory.createEtchedBorder(Color.white,
            new Color(148, 145, 140));
    Border border11 = new TitledBorder(border10, Language.getString("Line_mode"));
    JLabel jLabel3 = new JLabel();
    ButtonPen bEnd = new ButtonPen();
    JLabel jLabel1 = new JLabel();
    JLabel jLabel2 = new JLabel();
    JSpinner spWidth = new JSpinner();
    JSpinner spHeight = new JSpinner();

    public void spWidth_stateChanged(ChangeEvent e) {
        Object value = spWidth.getValue();
        int tmp = -1;
        if(value instanceof Double) {
            tmp = ((Double)spWidth.getValue()).intValue();
        }
        else if(value instanceof Integer) {
            tmp = ((Integer)spWidth.getValue()).intValue();
        }
        vector3D.setArrowWidth(tmp);
    }

    public void spHeight_stateChanged(ChangeEvent e) {
        Object value = spHeight.getValue();
        int tmp = -1;
        if(value instanceof Double) {
            tmp = ((Double)spHeight.getValue()).intValue();
        }
        else if(value instanceof Integer) {
            tmp = ((Integer)spHeight.getValue()).intValue();
        }
        vector3D.setArrowHeight(tmp);
    }
}

class Vector3DEditor_spWidth_changeAdapter implements ChangeListener {
    private Vector3DEditor adaptee;
    Vector3DEditor_spWidth_changeAdapter(Vector3DEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void stateChanged(ChangeEvent e) {
        adaptee.spWidth_stateChanged(e);
    }
}

class Vector3DEditor_spHeight_changeAdapter implements ChangeListener {
    private Vector3DEditor adaptee;
    Vector3DEditor_spHeight_changeAdapter(Vector3DEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void stateChanged(ChangeEvent e) {
        adaptee.spHeight_stateChanged(e);
    }
}

