package com.steema.teechart.editors.series;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import javax.swing.border.EtchedBorder;

import java.awt.*;

import com.steema.teechart.styles.Series;
import com.steema.teechart.styles.Bezier;
import com.steema.teechart.editors.ButtonPen;
import com.steema.teechart.editors.BaseSeriesEditor;
import com.steema.teechart.languages.Language;

/**
 * <p>Title: BezierEditor class</p>
 *
 * <p>Description: TeeChart for Java</p>
 *
 * <p>Copyright (c) 2004-2019 by Steema Software SL. All Rights Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 *
 */
public class BezierEditor extends BaseSeriesEditor {


    public BezierEditor() {
        super();
        try {
            jbInit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void setSeries(Series series, JTabbedPane tabs) {
        bezier = (Bezier) series;

        buttonPen1.setPen(bezier.getLinePen());
        tabs.add(new SeriesPointerEditor(bezier.getPointer()), Language.getString("Point"), 1);
    }

    private void jbInit() throws Exception {
        this.setLayout(null);
        buttonPen1.setBounds(new Rectangle(16, 11, 91, 23));
        buttonPen1.setMnemonic(Language.getString("B").charAt(0));
        buttonPen1.setText(Language.getString("Border"));
        this.add(buttonPen1);
    }

    Border border1 = BorderFactory.createEtchedBorder(Color.white,
            new Color(148, 145, 140));
    Border border2 = new TitledBorder(border1, Language.getString("arrow"));
    private Bezier bezier;
    Border border3 = BorderFactory.createEtchedBorder(EtchedBorder.RAISED,
            Color.white, new Color(148, 145, 140));
    Border border4 = new TitledBorder(border3, Language.getString("Multiple_arrows"));
    Border border5 = BorderFactory.createEtchedBorder(Color.white,
            new Color(148, 145, 140));
    Border border6 = new TitledBorder(border5, Language.getString("Color"));
    Border border7 = BorderFactory.createEtchedBorder(EtchedBorder.RAISED,
            Color.white, new Color(148, 145, 140));
    ButtonPen buttonPen1 = new ButtonPen();
}





