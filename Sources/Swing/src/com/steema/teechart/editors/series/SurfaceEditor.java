package com.steema.teechart.editors.series;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import javax.swing.border.EtchedBorder;

import java.awt.Rectangle;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.steema.teechart.styles.Series;
import com.steema.teechart.styles.Surface;
import com.steema.teechart.editors.BaseSeriesEditor;
import com.steema.teechart.editors.ButtonPen;
import com.steema.teechart.editors.BrushEditor;
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
public class SurfaceEditor extends BaseSeriesEditor {


    public SurfaceEditor() {
        super();
        try {
            jbInit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void setSeries(Series series, JTabbedPane tabs) {
        surface = (Surface) series;

                if (surface!=null)
                {
                    cbSmooth.setSelected(surface.getSmoothPalette());
                    bPen.setPen(surface.getPen());

                    if (surface.getWireFrame()) rbWireFrame.setSelected(true);
                    else if (surface.getDotFrame()) rbDotFrame.setSelected(true);
                         else rbSolid.setSelected(true);

                    if (grid3DEditor==null) {
                        grid3DEditor = new GridEditor();
                        grid3DEditor.setSeries(series, tabs);
                        tabs.add(Language.getString("Grid_3D"), grid3DEditor);
                    }

                }

                ButtonGroup group = new ButtonGroup();
                  group.add(rbSolid);
                  group.add(rbWireFrame);
                  group.add(rbDotFrame);
    }

    private void jbInit() throws Exception {
        border12 = BorderFactory.createEtchedBorder(Color.white,
                new Color(148, 145, 140));
        this.setLayout(null);
        jLabel3.setDisplayedMnemonic(Language.getString("O").charAt(0));
        jLabel3.setHorizontalAlignment(SwingConstants.TRAILING);
        jLabel3.setText(Language.getString("Transparency"));
        jLabel3.setBounds(new Rectangle( -9, 154, 93, 14));
        cbSmooth.setMnemonic(Language.getString("S").charAt(0));
        cbSmooth.setText(Language.getString("Smooth_Palette"));
        cbSmooth.setBounds(new Rectangle(10, 69, 110, 17));
        cbSmooth.addActionListener(new SurfaceEditor_cbSmooth_actionAdapter(this));
        bBrush.setBounds(new Rectangle(12, 36, 75, 23));
        bBrush.setMnemonic(Language.getString("B").charAt(0));
        bBrush.setText(Language.getString("Brush"));
        bBrush.addActionListener(new SurfaceEditor_bBrush_actionAdapter(this));
        bPen.setBounds(new Rectangle(11, 9, 76, 23));
        bPen.setMnemonic(Language.getString("P").charAt(0));
        bPen.setText(Language.getString("Pen"));
        bSideBrush.setBounds(new Rectangle(146, 66, 129, 23));
        bSideBrush.setMnemonic(Language.getString("I").charAt(0));
        bSideBrush.setText(Language.getString("Side_Brush"));
        bSideBrush.addActionListener(new SurfaceEditor_bSideBrush_actionAdapter(this));
        rbSolid.setMnemonic(Language.getString("O").charAt(0));
        rbSolid.setText(Language.getString("Solid"));
        rbSolid.setBounds(new Rectangle(11, 15, 63, 23));
        rbSolid.addActionListener(new SurfaceEditor_rbSolid_actionAdapter(this));
        jPanel1.setBorder(border18);
        jPanel1.setBounds(new Rectangle(96, 11, 285, 44));
        jPanel1.setLayout(null);
        rbWireFrame.setMnemonic(Language.getString("W").charAt(0));
        rbWireFrame.setText(Language.getString("WireFrame"));
        rbWireFrame.setBounds(new Rectangle(77, 15, 86, 23));
        rbWireFrame.addActionListener(new SurfaceEditor_rbWireFrame_actionAdapter(this));
        rbDotFrame.setMnemonic(Language.getString("F").charAt(0));
        rbDotFrame.setText(Language.getString("DotFrame"));
        rbDotFrame.setBounds(new Rectangle(175, 15, 93, 23));
        rbDotFrame.addActionListener(new SurfaceEditor_rbDotFrame_actionAdapter(this));
        jPanel1.add(rbDotFrame);
        jPanel1.add(rbSolid);
        jPanel1.add(rbWireFrame);
        this.add(bSideBrush);
        this.add(bPen);
        this.add(bBrush);
        this.add(cbSmooth);
        this.add(jPanel1);
    }

    private GridEditor grid3DEditor;
    Border border1 = BorderFactory.createEtchedBorder(Color.white,
            new Color(148, 145, 140));
    Border border2 = new TitledBorder(border1, Language.getString("custom"));
    private Surface surface;
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
    JLabel jLabel3 = new JLabel();
    Border border12 = BorderFactory.createEtchedBorder(EtchedBorder.RAISED,
            Color.white, new Color(148, 145, 140));
    JCheckBox cbSmooth = new JCheckBox();
    JButton bBrush = new JButton();
    Border border13 = BorderFactory.createEtchedBorder(Color.white,
            new Color(148, 145, 140));
    Border border14 = new TitledBorder(border13, Language.getString("Color"));
    ButtonPen bPen = new ButtonPen();
    JButton bSideBrush = new JButton();
    JRadioButton rbSolid = new JRadioButton();
    JPanel jPanel1 = new JPanel();
    JRadioButton rbWireFrame = new JRadioButton();
    JRadioButton rbDotFrame = new JRadioButton();
    Border border15 = BorderFactory.createBevelBorder(BevelBorder.LOWERED,
            Color.white, Color.white, new Color(115, 114, 105),
            new Color(165, 163, 151));
    Border border16 = new TitledBorder(border15, Language.getString("Drawing_Mode"));
    Border border17 = BorderFactory.createEtchedBorder(EtchedBorder.RAISED,
            Color.white, new Color(165, 163, 151));
    Border border18 = new TitledBorder(border17, Language.getString("Drawing_Mode"));

    public void bBrush_actionPerformed(ActionEvent e) {
        BrushEditor.edit(this, surface.getBrush());
    }

    public void cSideBrush_actionPerformed(ActionEvent e) {
        BrushEditor.edit(this, surface.getSideBrush());
    }

    public void rbSolid_actionPerformed(ActionEvent e) {
        surface.setWireFrame(false);
        surface.setDotFrame(false);
    }

    public void rbWireFrame_actionPerformed(ActionEvent e) {
        surface.setWireFrame(true);
        surface.setDotFrame(false);
    }

    public void rbDotFrame_actionPerformed(ActionEvent e) {
        surface.setWireFrame(false);
        surface.setDotFrame(true);
    }

    public void cbSmooth_actionPerformed(ActionEvent e) {
        surface.setSmoothPalette(cbSmooth.isSelected());
    }
}


final class SurfaceEditor_bBrush_actionAdapter implements ActionListener {
    private SurfaceEditor adaptee;
    SurfaceEditor_bBrush_actionAdapter(SurfaceEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.bBrush_actionPerformed(e);
    }
}

final class SurfaceEditor_bSideBrush_actionAdapter implements ActionListener {
    private SurfaceEditor adaptee;
    SurfaceEditor_bSideBrush_actionAdapter(SurfaceEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.cbSmooth_actionPerformed(e);
    }
}

final class SurfaceEditor_rbSolid_actionAdapter implements ActionListener {
    private SurfaceEditor adaptee;
    SurfaceEditor_rbSolid_actionAdapter(SurfaceEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.rbSolid_actionPerformed(e);
    }
}
final class SurfaceEditor_rbWireFrame_actionAdapter implements ActionListener {
    private SurfaceEditor adaptee;
    SurfaceEditor_rbWireFrame_actionAdapter(SurfaceEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.rbWireFrame_actionPerformed(e);
    }
}
final class SurfaceEditor_rbDotFrame_actionAdapter implements ActionListener {
    private SurfaceEditor adaptee;
    SurfaceEditor_rbDotFrame_actionAdapter(SurfaceEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.rbDotFrame_actionPerformed(e);
    }
}

class SurfaceEditor_cbSmooth_actionAdapter implements ActionListener {
    private SurfaceEditor adaptee;
    SurfaceEditor_cbSmooth_actionAdapter(SurfaceEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.cbSmooth_actionPerformed(e);
    }
}



