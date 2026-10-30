package com.steema.teechart.editors.series;

// some lines to be uncommented..

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.event.ChangeEvent;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.steema.teechart.styles.Series;
import com.steema.teechart.styles.Candle;
import com.steema.teechart.styles.CandleStyle;
import com.steema.teechart.editors.ButtonPen;
import com.steema.teechart.editors.ButtonColor;
import com.steema.teechart.editors.BaseSeriesEditor;
import com.steema.teechart.languages.Language;

/**
 * <p>Title: CandleEditor class</p>
 *
 * <p>Description: TeeChart for Java</p>
 *
 * <p>Copyright (c) 2004-2019 by Steema Software SL. All Rights Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 *
 */
public class CandleEditor extends BaseSeriesEditor {


    public CandleEditor() {
        super();
        try {
            jbInit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void setSeries(Series series, JTabbedPane tabs) {
        candle = (Candle) series;

        //bLines.setPen(candle.getCandleLinesPen());

        ButtonGroup group = new ButtonGroup();
        group.add(rbStick);
        group.add(rbBar);
        group.add(rbOpenClose);
        group.add(rbLine);

        rbStick.setSelected(candle.getStyle() == CandleStyle.CANDLESTICK);
        rbBar.setSelected(candle.getStyle() == CandleStyle.CANDLEBAR);
        rbOpenClose.setSelected(candle.getStyle() == CandleStyle.OPENCLOSE);
        rbLine.setSelected(candle.getStyle() == CandleStyle.LINE);

        cbShowOpen.setSelected(candle.getShowOpen());
        cbShowClose.setSelected(candle.getShowClose());
//        cbDraw3D.setSelected(candle.getHasZValues());
        cbDark3D.setSelected(candle.getDark3D());
        bUpColor.setColor(candle.getUpCloseColor());
        bDownColor.setColor(candle.getDownCloseColor());
        spWidth.setValue(new Integer (candle.getCandleWidth()));
        bPen.setPen(candle.getPen());
//        bHighLow.setPen(candle.getHighLowPen());

//        tabs.add(new SeriesPointerEditor(candle.getPointer()),"Pointer", 0);
    }

    private void jbInit() throws Exception {
        this.setLayout(null);
        jPanel2.setBorder(border9);
        jPanel2.setBounds(new Rectangle(1, 1, 128, 113));
        jPanel2.setLayout(null);
        rbStick.setMnemonic(Language.getString("S").charAt(0));
        rbStick.setText(Language.getString("Stick"));
        rbStick.setBounds(new Rectangle(11, 16, 93, 23));
        rbStick.addActionListener(new CandleEditor_rbStick_actionAdapter(this));
        rbBar.setMnemonic(Language.getString("B").charAt(0));
        rbBar.setText(Language.getString("Bar"));
        rbBar.setBounds(new Rectangle(11, 38, 93, 23));
        rbBar.addActionListener(new CandleEditor_rbBar_actionAdapter(this));
        rbOpenClose.setMnemonic(Language.getString("O").charAt(0));
        rbOpenClose.setText(Language.getString("Open_Close"));
        rbOpenClose.setBounds(new Rectangle(11, 60, 111, 23));
        rbOpenClose.addActionListener(new CandleEditor_rbOpenClose_actionAdapter(this));
        cbDraw3D.setMnemonic('3');
        cbDraw3D.setText(Language.getString("Draw_3D"));
        cbDraw3D.setBounds(new Rectangle(158, 159, 92, 23));
        cbDraw3D.addActionListener(new CandleEditor_cbDraw3D_actionAdapter(this));
        bUpColor.setBounds(new Rectangle(136, 16, 129, 23));
        bUpColor.setPreferredSize(new Dimension(131, 23));
        bUpColor.setToolTipText("");
        bUpColor.setMnemonic(Language.getString("U").charAt(0));
        bUpColor.setText(Language.getString("Up_Close"));
        bUpColor.addActionListener(new CandleEditor_bUpColor_actionAdapter(this));
        cbShowOpen.setMnemonic(Language.getString("P").charAt(0));
        cbShowOpen.setText(Language.getString("Show_Open"));
        cbShowOpen.setBounds(new Rectangle(158, 115, 102, 23));
        cbShowOpen.addActionListener(new CandleEditor_cbShowOpen_actionAdapter(this));
        rbLine.setMnemonic(Language.getString("L").charAt(0));
        rbLine.setText(Language.getString("Line"));
        rbLine.setBounds(new Rectangle(11, 82, 111, 23));
        rbLine.addActionListener(new CandleEditor_rbLine_actionAdapter(this));
        bHighLow.setBounds(new Rectangle(136, 65, 129, 23));
        bHighLow.setMnemonic(Language.getString("H").charAt(0));
        bHighLow.setText(Language.getString("High_Low"));
        bDownColor.setBounds(new Rectangle(136, 40, 129, 23));
        bDownColor.setPreferredSize(new Dimension(129, 23));
        bDownColor.setToolTipText("");
        bDownColor.setMnemonic(Language.getString("D").charAt(0));
        bDownColor.setText(Language.getString("Down_Close"));
        bDownColor.addActionListener(new CandleEditor_bDownColor_actionAdapter(this));
        spWidth.setBounds(new Rectangle(88, 116, 53, 23));
        jLabel3.setDisplayedMnemonic(Language.getString("O").charAt(0));
        jLabel3.setHorizontalAlignment(SwingConstants.TRAILING);
        jLabel3.setLabelFor(spWidth);
        jLabel3.setText(Language.getString("Candle_Width"));
        jLabel3.setBounds(new Rectangle(5, 120, 77, 14));
        cbShowClose.setMnemonic(Language.getString("C").charAt(0));
        cbShowClose.setText(Language.getString("Show_Close"));
        cbShowClose.setBounds(new Rectangle(158, 136, 107, 23));
        cbShowClose.addActionListener(new CandleEditor_cbShowOpen_actionAdapter(this));
        bPen.setBounds(new Rectangle(15, 145, 75, 23));
        bPen.setMnemonic(Language.getString("B").charAt(0));
        bPen.setText(Language.getString("Border"));
        jLabel2.setDisplayedMnemonic(Language.getString("N").charAt(0));
        jLabel2.setHorizontalAlignment(SwingConstants.TRAILING);
        jLabel2.setText(Language.getString("Transparency"));
        jLabel2.setBounds(new Rectangle(1, 172, 80, 14));
        cbDark3D.setMnemonic(Language.getString("K").charAt(0));
        cbDark3D.setText(Language.getString("Dark_3D"));
        cbDark3D.setBounds(new Rectangle(158, 181, 92, 23));
        cbDark3D.addActionListener(new CandleEditor_cbDark3D_actionAdapter(this));
        siTransp.setBounds(new Rectangle(2, 188, 123, 23));
        jPanel2.add(rbBar);
        jPanel2.add(rbStick);
        jPanel2.add(rbOpenClose);
        jPanel2.add(rbLine);
        this.add(siTransp);
        this.add(jLabel3);
        this.add(bUpColor);
        this.add(cbDraw3D);
        this.add(cbDark3D);
        this.add(spWidth);
        this.add(cbShowClose);
        this.add(bDownColor);
        this.add(bHighLow);
        this.add(cbShowOpen);
        this.add(bPen);
        this.add(jLabel2);
        this.add(jPanel2);
    }

    Border border1 = BorderFactory.createEtchedBorder(Color.white,
            new Color(148, 145, 140));
    Border border2 = new TitledBorder(border1, Language.getString("Candle"));
    private Candle candle;
    JPanel jPanel2 = new JPanel();
    Border border3 = BorderFactory.createEtchedBorder(EtchedBorder.RAISED,
            Color.white, new Color(148, 145, 140));
    Border border4 = new TitledBorder(border3, Language.getString("Multiple_Candles"));
    JRadioButton rbStick = new JRadioButton();
    JRadioButton rbBar = new JRadioButton();
    JRadioButton rbOpenClose = new JRadioButton();
    Border border5 = BorderFactory.createEtchedBorder(Color.white,
            new Color(148, 145, 140));
    Border border6 = new TitledBorder(border5, Language.getString("Color"));
    JCheckBox cbDraw3D = new JCheckBox();
    ButtonColor bUpColor = new ButtonColor();
    Border border7 = BorderFactory.createEtchedBorder(EtchedBorder.RAISED,
            Color.white, new Color(148, 145, 140));
    JCheckBox cbShowOpen = new JCheckBox();
    Border border8 = BorderFactory.createEtchedBorder(EtchedBorder.RAISED,
            Color.white, new Color(148, 145, 140));
    Border border9 = new TitledBorder(border8, Language.getString("Style"));
    JRadioButton rbLine = new JRadioButton();
    ButtonPen bHighLow = new ButtonPen();
    ButtonColor bDownColor = new ButtonColor();
    JSpinner spWidth = new JSpinner();
    JLabel jLabel3 = new JLabel();
    JCheckBox cbShowClose = new JCheckBox();
    ButtonPen bPen = new ButtonPen();
    JLabel jLabel2 = new JLabel();
    JCheckBox cbDark3D = new JCheckBox();
    JSlider siTransp = new JSlider();

    public void rbStick_actionPerformed(ActionEvent e) {
        candle.setStyle(CandleStyle.CANDLESTICK);
    }

    public void rbBar_actionPerformed(ActionEvent e) {
        candle.setStyle(CandleStyle.CANDLEBAR);
    }

    public void rbOpenClose_actionPerformed(ActionEvent e) {
        candle.setStyle(CandleStyle.OPENCLOSE);
    }

    public void rbLine_actionPerformed(ActionEvent e) {
        candle.setStyle(CandleStyle.LINE);
    }

    public void bUpColor_actionPerformed(ActionEvent e) {
        candle.setUpCloseColor(bUpColor.getColor());
    }

    public void bDownColor_actionPerformed(ActionEvent e) {
        candle.setDownCloseColor(bDownColor.getColor());
    }

    public void slTransp_stateChanged(ChangeEvent e) {
        candle.setTransparency(siTransp.getValue());
    }
}

final class CandleEditor_bUpColor_actionAdapter implements ActionListener {
    private CandleEditor adaptee;
    CandleEditor_bUpColor_actionAdapter(CandleEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.bUpColor_actionPerformed(e);
    }
}

final class CandleEditor_bDownColor_actionAdapter implements ActionListener {
    private CandleEditor adaptee;
    CandleEditor_bDownColor_actionAdapter(CandleEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.bDownColor_actionPerformed(e);
    }
}

final class CandleEditor_rbStick_actionAdapter implements ActionListener {
    private CandleEditor adaptee;
    CandleEditor_rbStick_actionAdapter(CandleEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.rbStick_actionPerformed(e);
    }
}


final class CandleEditor_rbBar_actionAdapter implements ActionListener {
    private CandleEditor adaptee;
    CandleEditor_rbBar_actionAdapter(CandleEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.rbBar_actionPerformed(e);
    }
}

final class CandleEditor_rbOpenClose_actionAdapter implements ActionListener {
    private CandleEditor adaptee;
    CandleEditor_rbOpenClose_actionAdapter(CandleEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.rbOpenClose_actionPerformed(e);
    }
}

final class CandleEditor_rbLine_actionAdapter implements ActionListener {
    private CandleEditor adaptee;
    CandleEditor_rbLine_actionAdapter(CandleEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.rbLine_actionPerformed(e);
    }
}

final class CandleEditor_cbShowOpen_actionAdapter implements ActionListener {
    private CandleEditor adaptee;
    CandleEditor_cbShowOpen_actionAdapter(CandleEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
//        adaptee.cbShowOpen_actionPerformed(e);
    }
}

final class CandleEditor_cbShowClose_actionAdapter implements ActionListener {
    private CandleEditor adaptee;
    CandleEditor_cbShowClose_actionAdapter(CandleEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
//        adaptee.cbShowClose_actionPerformed(e);
    }
}

final class CandleEditor_cbDraw3D_actionAdapter implements ActionListener {
    private CandleEditor adaptee;
    CandleEditor_cbDraw3D_actionAdapter(CandleEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
//        adaptee.cbDraw3D_actionPerformed(e);
    }
}

final class CandleEditor_cbDark3D_actionAdapter implements ActionListener {
    private CandleEditor adaptee;
    CandleEditor_cbDark3D_actionAdapter(CandleEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
//        adaptee.cbDard3D_actionPerformed(e);
    }
}

