package com.steema.teechart.editors.series;

// Lines to uncomment....

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import javax.swing.border.EtchedBorder;

import java.awt.Rectangle;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.steema.teechart.styles.Series;
import com.steema.teechart.styles.PointFigure;
import com.steema.teechart.editors.BaseSeriesEditor;
import java.awt.event.KeyEvent;
import java.awt.event.KeyAdapter;
import com.steema.teechart.languages.Language;

/**
 * <p>Title: PointFigureEditor class</p>
 *
 * <p>Description: PointFigure Series Editor</p>
 *
 * <p>Copyright (c) 2004-2019 by Steema Software SL. All Rights Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 *
 */
public class PointFigureEditor extends BaseSeriesEditor {


    public PointFigureEditor() {
        super();
        try {
            jbInit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void setSeries(Series series, JTabbedPane tabs) {
        pointFigure = (PointFigure) series;

        tbBoxSize.setText(new Double(pointFigure.getBoxSize()).toString());
        tbReversalAmt.setText(new Double(pointFigure.getReversalAmount()).toString());

        tabs.add(new SeriesPointerEditor(pointFigure.getDownSymbol()), Language.getString("Down"), 1);
        tabs.add(new SeriesPointerEditor(pointFigure.getUpSymbol()), Language.getString("Up"), 1);


    }

    private void jbInit() throws Exception {
        this.setLayout(null);
        jLabel2.setDisplayedMnemonic(Language.getString("R").charAt(0));
        jLabel2.setHorizontalAlignment(SwingConstants.TRAILING);
        jLabel2.setText(Language.getString("Reversal_amount"));
        jLabel2.setBounds(new Rectangle(25, 59, 102, 14));
        jLabel1.setDisplayedMnemonic(Language.getString("B").charAt(0));
        jLabel1.setHorizontalAlignment(SwingConstants.TRAILING);
        jLabel1.setText(Language.getString("Box_size"));
        jLabel1.setBounds(new Rectangle(70, 26, 57, 14));
        tbReversalAmt.setText("3");
        tbReversalAmt.setBounds(new Rectangle(151, 54, 84, 23));
        tbReversalAmt.addKeyListener(new
                                     PointFigureEditor_tbReversalAmt_keyAdapter(this));
        tbReversalAmt.addActionListener(new PointFigureEditor_tbReversalAmt_actionAdapter(this));
        tbBoxSize.setText("1");
        tbBoxSize.setBounds(new Rectangle(151, 22, 84, 23));
        tbBoxSize.addKeyListener(new PointFigureEditor_tbBoxSize_keyAdapter(this));
        tbBoxSize.addActionListener(new PointFigureEditor_tbBoxSize_actionAdapter(this));
        this.add(jLabel2);
        this.add(jLabel1);
        this.add(tbBoxSize);
        this.add(tbReversalAmt);
    }

    Border border1 = BorderFactory.createEtchedBorder(Color.white,
            new Color(148, 145, 140));
    Border border2 = new TitledBorder(border1, Language.getString("custom"));
    private PointFigure pointFigure;
    private SeriesPointerEditor downEditor;
    private SeriesPointerEditor upEditor;
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
    JLabel jLabel1 = new JLabel();
    JTextField tbReversalAmt = new JTextField();
    JTextField tbBoxSize = new JTextField();
    public void tbBoxSize_actionPerformed(ActionEvent e) {
    }

    public void tbReversalAmt_actionPerformed(ActionEvent e) {
        pointFigure.setReversalAmount(new Double(tbReversalAmt.getText()).doubleValue());
    }

    public void tbBoxSize_keyReleased(KeyEvent e) {
        pointFigure.setBoxSize(com.steema.teechart.misc.Utils.stringToDouble(tbBoxSize.getText(), 1));

    }

    public void tbReversalAmt_keyReleased(KeyEvent e) {
        pointFigure.setReversalAmount(com.steema.teechart.misc.Utils.stringToDouble(tbReversalAmt.getText(), 1));
    }


}


class PointFigureEditor_tbReversalAmt_actionAdapter implements ActionListener {
    private PointFigureEditor adaptee;
    PointFigureEditor_tbReversalAmt_actionAdapter(PointFigureEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.tbReversalAmt_actionPerformed(e);
    }
}


class PointFigureEditor_tbBoxSize_actionAdapter implements ActionListener {
    private PointFigureEditor adaptee;
    PointFigureEditor_tbBoxSize_actionAdapter(PointFigureEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.tbBoxSize_actionPerformed(e);
    }
}


class PointFigureEditor_tbReversalAmt_keyAdapter extends KeyAdapter {
    private PointFigureEditor adaptee;
    PointFigureEditor_tbReversalAmt_keyAdapter(PointFigureEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void keyReleased(KeyEvent e) {
        adaptee.tbReversalAmt_keyReleased(e);
    }
}


class PointFigureEditor_tbBoxSize_keyAdapter extends KeyAdapter {
    private PointFigureEditor adaptee;
    PointFigureEditor_tbBoxSize_keyAdapter(PointFigureEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void keyReleased(KeyEvent e) {
        adaptee.tbBoxSize_keyReleased(e);
    }
}


