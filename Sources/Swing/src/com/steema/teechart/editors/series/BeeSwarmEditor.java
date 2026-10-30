package com.steema.teechart.editors.series;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import javax.swing.border.EtchedBorder;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyAdapter;

import com.steema.teechart.styles.Series;
import com.steema.teechart.styles.CustomBox;

import com.steema.teechart.editors.ButtonPen;
import com.steema.teechart.editors.BaseSeriesEditor;
import com.steema.teechart.languages.Language;
import com.steema.teechart.styles.BeeSwarm;
import com.steema.teechart.styles.Violin;

/**
 * <p>Title: BeeSwarmEditor class</p>
 *
 * <p>Description: TeeChart for Java</p>
 *
 * <p>Copyright (c) 2004-2021 by Steema Software SL. All Rights Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 *
 */
public class BeeSwarmEditor extends BaseSeriesEditor {

    private BeeSwarm beeSwarm;
    private KDEEditor kdeEditor;

    public BeeSwarmEditor() {
        super();
        try {
            jbInit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void setSeries(Series series, JTabbedPane tabs) {
        beeSwarm = (BeeSwarm) series;
        
        kdeEditor = new KDEEditor();
        this.add(kdeEditor);
        kdeEditor.setBounds(new Rectangle(-30, 0, 478, 423));
        kdeEditor.setSeries(beeSwarm, tabs);
    }

    private void jbInit() throws Exception {
        this.setLayout(null);

    }

}

