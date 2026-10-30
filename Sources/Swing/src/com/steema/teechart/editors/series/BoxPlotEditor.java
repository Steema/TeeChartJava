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

/**
 * <p>Title: BoxPlotEditor class</p>
 *
 * <p>Description: TeeChart for Java</p>
 *
 * <p>Copyright (c) 2004-2019 by Steema Software SL. All Rights Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 *
 */
public class BoxPlotEditor extends BaseSeriesEditor {


    public BoxPlotEditor() {
        super();
        try {
            jbInit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void setSeries(Series series, JTabbedPane tabs) {
        boxplot = (CustomBox) series;

        bMedian.setPen(boxplot.getMedianPen());
        bWhisker.setPen(boxplot.getWhiskerPen());

        ePos.setText(String.valueOf(boxplot.getPosition()));
        eLength.setText(String.valueOf(boxplot.getWhiskerLength()));

        tabs.add(new SeriesPointerEditor(boxplot.getMildOut()), Language.getString("MildOut"), tabs.getTabCount());
        tabs.add(new SeriesPointerEditor(boxplot.getExtrOut()), Language.getString("ExtrOut"), tabs.getTabCount());
        tabs.add(new SeriesPointerEditor(boxplot.getBox()), Language.getString("Box"), tabs.getTabCount());


    }

    private void jbInit() throws Exception {
        this.setLayout(null);
        bMedian.setBounds(new Rectangle(24, 20, 83, 23));
        bMedian.setMaximumSize(new Dimension(81, 23));
        bMedian.setMinimumSize(new Dimension(81, 23));
        bMedian.setPreferredSize(new Dimension(81, 23));
        bMedian.setMnemonic(Language.getString("M").charAt(0));
        bMedian.setText(Language.getString("Median"));
        bWhisker.setBounds(new Rectangle(24, 64, 84, 23));
        bWhisker.setActionCommand(Language.getString("Whisker"));
        bWhisker.setMnemonic(Language.getString("W").charAt(0));
        bWhisker.setText(Language.getString("Whisker"));
        JLabel5.setDisplayedMnemonic('0');
        JLabel5.setHorizontalAlignment(SwingConstants.TRAILING);
        JLabel5.setLabelFor(eLength);
        JLabel5.setText(Language.getString("Length"));
        JLabel5.setBounds(new Rectangle(119, 68, 51, 14));
        jLabel4.setDisplayedMnemonic('0');
        jLabel4.setHorizontalAlignment(SwingConstants.TRAILING);
        jLabel4.setLabelFor(ePos);
        jLabel4.setText(Language.getString("Position"));
        jLabel4.setBounds(new Rectangle(118, 24, 51, 14));
        eLength.setText("");
        eLength.setBounds(new Rectangle(177, 65, 53, 20));
        eLength.addKeyListener(new BoxPlotEditor_eLength_keyAdapter(this));
        ePos.setText("");
        ePos.setBounds(new Rectangle(177, 22, 53, 20));
        ePos.addKeyListener(new BoxPlotEditor_ePos_keyAdapter(this));
        this.add(bMedian);
        this.add(bWhisker);
        this.add(jLabel4);
        this.add(JLabel5);
        this.add(eLength);
        this.add(ePos);
    }

    Border border1 = BorderFactory.createEtchedBorder(Color.white,
            new Color(148, 145, 140));
    Border border2 = new TitledBorder(border1, Language.getString("arrow"));
    private CustomBox boxplot;
    Border border3 = BorderFactory.createEtchedBorder(EtchedBorder.RAISED,
            Color.white, new Color(148, 145, 140));
    Border border4 = new TitledBorder(border3, Language.getString("Multiple_arrows"));
    Border border5 = BorderFactory.createEtchedBorder(Color.white,
            new Color(148, 145, 140));
    Border border6 = new TitledBorder(border5, Language.getString("Color"));
    Border border7 = BorderFactory.createEtchedBorder(EtchedBorder.RAISED,
            Color.white, new Color(148, 145, 140));
    ButtonPen bMedian = new ButtonPen();
    ButtonPen bWhisker = new ButtonPen();
    JLabel JLabel5 = new JLabel();
    JLabel jLabel4 = new JLabel();
    JTextField eLength = new JTextField();
    JTextField ePos = new JTextField();


    public void ePos_keyReleased(KeyEvent e) {
        boxplot.setPosition(com.steema.teechart.misc.Utils.stringToDouble(ePos.getText(), boxplot.getPosition()));
    }

    public void eLength_keyReleased(KeyEvent e) {
        boxplot.setWhiskerLength(com.steema.teechart.misc.Utils.stringToDouble(eLength.getText(), boxplot.getWhiskerLength()));
    }
}


class BoxPlotEditor_eLength_keyAdapter extends KeyAdapter {
    private BoxPlotEditor adaptee;
    BoxPlotEditor_eLength_keyAdapter(BoxPlotEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void keyReleased(KeyEvent e) {
        adaptee.eLength_keyReleased(e);
    }
}


class BoxPlotEditor_ePos_keyAdapter extends KeyAdapter {
    private BoxPlotEditor adaptee;
    BoxPlotEditor_ePos_keyAdapter(BoxPlotEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void keyReleased(KeyEvent e) {
        adaptee.ePos_keyReleased(e);
    }
}


