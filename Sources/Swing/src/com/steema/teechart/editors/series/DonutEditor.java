package com.steema.teechart.editors.series;

// some lines to be uncommented...

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import java.awt.*;

import com.steema.teechart.styles.Series;
import com.steema.teechart.styles.Donut;
import com.steema.teechart.editors.BaseSeriesEditor;
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
public class DonutEditor extends BaseSeriesEditor {


    public DonutEditor() {
        super();
        try {
            jbInit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private com.steema.teechart.editors.series.CircledEditor circledEditor;
    private com.steema.teechart.editors.series.PieEditor pieEditor;


    public void setSeries(Series series, JTabbedPane tabs) {
        donut = (Donut) series;

        spDonut.setValue(new Integer(donut.getDonutPercent()));

        if(pieEditor == null) {
            pieEditor = new PieEditor();
            pieEditor.setSeries(donut, tabs);
            tabs.add(pieEditor, Language.getString("Pie"), 1);
        }


        if(circledEditor == null) {
            circledEditor = new CircledEditor();
            circledEditor.setSeries(donut, tabs);
            tabs.add(circledEditor, Language.getString("Circled"), tabs.getTabCount());
        }

    }

    private void jbInit() throws Exception {
        this.setLayout(null);
        spDonut.setToolTipText("");
        spDonut.setBounds(new Rectangle(89, 20, 46, 23));
        spDonut.addChangeListener(new DonutEditor_spDonut_changeAdapter(this));
        jLabel2.setDisplayedMnemonic(Language.getString("H").charAt(0));
        jLabel2.setHorizontalAlignment(SwingConstants.TRAILING);
        jLabel2.setLabelFor(spDonut);
        jLabel2.setText(Language.getString("Hole"));
        jLabel2.setBounds(new Rectangle(25, 25, 57, 14));
        this.add(jLabel2);
        this.add(spDonut);
    }

    Border border1 = BorderFactory.createEtchedBorder(Color.white,
            new Color(148, 145, 140));
    Border border2 = new TitledBorder(border1, Language.getString("custom"));
    private Donut donut;
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
    JSpinner spDonut = new JSpinner();

    public void spDonut_stateChanged(ChangeEvent e) {
        donut.setDonutPercent(((Integer)spDonut.getValue()).intValue());
    }
}

class DonutEditor_spDonut_changeAdapter implements ChangeListener {
    private DonutEditor adaptee;
    DonutEditor_spDonut_changeAdapter(DonutEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void stateChanged(ChangeEvent e) {
        adaptee.spDonut_stateChanged(e);
    }
}

