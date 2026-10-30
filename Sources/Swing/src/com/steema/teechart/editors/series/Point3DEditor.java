package com.steema.teechart.editors.series;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Color;

import com.steema.teechart.editors.BaseSeriesEditor;
import com.steema.teechart.styles.Series;
import com.steema.teechart.styles.Points3D;
import com.steema.teechart.editors.ButtonColor;
import com.steema.teechart.editors.ButtonPen;
import com.steema.teechart.languages.Language;
import com.steema.teechart.styles.TreatNullsStyle;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

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
public class Point3DEditor extends BaseSeriesEditor {


    private Points3D points3D;
    ButtonColor bColor = new ButtonColor();
    JCheckBox cbEach = new JCheckBox();
    JLabel jLabel1 = new JLabel();
    JPanel jPanel1 = new JPanel();
    Border border1 = BorderFactory.createBevelBorder(BevelBorder.LOWERED,
            Color.white, Color.white, new Color(103, 101, 98),
            new Color(148, 145, 140));
    ButtonPen bBaseLine = new ButtonPen();
    ButtonPen bLine = new ButtonPen();
    JSpinner spPointDepth = new JSpinner();
    JPanel jPanel2 = new JPanel();
    Border border2 = BorderFactory.createEtchedBorder(EtchedBorder.RAISED,
            Color.white, new Color(148, 145, 140));
    Border border3 = new TitledBorder(border2, Language.getString("TreatNulls"));
    JComboBox cbTreatNulls = new JComboBox();

    public Point3DEditor() {
        try {
            jbInit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        
        cbTreatNulls.addItem(Language.getString("DontPaint"));
        cbTreatNulls.addItem(Language.getString("Skip"));
        cbTreatNulls.addItem(Language.getString("Ignore"));
    }

    public void setSeries(Series series, JTabbedPane tabs) {
        points3D=(Points3D)series;

        if (points3D!= null)
        {
          cbEach.setSelected(points3D.getColorEach());
//          spPointDepth.setValue((decimal)points3D.getDepthSize());
          bColor.setColor(points3D.getColor());
          bColor.setEnabled(!points3D.getColorEach());
          bLine.setPen(points3D.getLinePen());
          bBaseLine.setPen(points3D.getBaseLine());
          cbTreatNulls.setSelectedIndex(points3D.getTreatNulls().getValue());
          tabs.add(new SeriesPointerEditor(points3D.getPointer()),Language.getString("Pointer"), 1);
        }

    }

    private void jbInit() throws Exception {
        border1 = BorderFactory.createEtchedBorder(Color.white,
                new Color(148, 145, 140));
        this.setLayout(null);
        bColor.setBounds(new Rectangle(12, 13, 75, 23));
        bColor.setMnemonic(Language.getString("C").charAt(0));
        bColor.setText(Language.getString("Color"));
        bColor.addActionListener(new Point3DEditor_bColor_actionAdapter(this));
        cbEach.setMnemonic(Language.getString("E").charAt(0));
        cbEach.setText(Language.getString("Color_each"));
        cbEach.setBounds(new Rectangle(6, 49, 92, 23));
        cbEach.addActionListener(new Point3DEditor_cbEach_actionAdapter(this));
        jLabel1.setDisplayedMnemonic(Language.getString("D").charAt(0));
        jLabel1.setHorizontalAlignment(SwingConstants.RIGHT);
        jLabel1.setText(Language.getString("Depth"));
        jLabel1.setBounds(new Rectangle(117, 66, 49, 14));
        jPanel1.setBorder(border1);
        jPanel1.setBounds(new Rectangle(14, 13, 102, 86));
        jPanel1.setLayout(null);
        bBaseLine.setBounds(new Rectangle(135, 115, 91, 23));
        bBaseLine.setMnemonic(Language.getString("B").charAt(0));
        bBaseLine.setText(Language.getString("Base_Line"));
        bLine.setBounds(new Rectangle(150, 26, 76, 23));
        bLine.setMnemonic(Language.getString("L").charAt(0));
        bLine.setText(Language.getString("Line"));
        spPointDepth.setBounds(new Rectangle(175, 62, 51, 21));
        spPointDepth.addChangeListener(new
                                       Point3DEditor_spPointDepth_changeAdapter(this));
        jPanel1.add(bColor);
        jPanel1.add(cbEach);
        cbTreatNulls.setBounds(new Rectangle(10, 22, 90, 22));
        cbTreatNulls.addActionListener(new 
                Point3Editor_cbTreatNulls_actionAdapter(this));
        jPanel2.setBorder(border3);
        jPanel2.setBounds(new Rectangle(14, 110, 110, 60));
        jPanel2.setLayout(null);
        jPanel2.add(cbTreatNulls);
        this.add(bBaseLine);
        this.add(bLine);
        this.add(spPointDepth);
        this.add(jLabel1);
        this.add(jPanel1);
        this.add(jPanel2);
    }

    public void bColor_actionPerformed(ActionEvent e) {
        points3D.setColor(bColor.getColor());
        points3D.setColorEach(false);
    }

    public void cbEach_actionPerformed(ActionEvent e) {
        points3D.setColorEach(cbEach.isSelected());
        bColor.setEnabled(!points3D.getColorEach());
    }
    
    public void cbTreatNulls_actionPerformed(ActionEvent e) {
        if (points3D!=null) {
            points3D.setTreatNulls(TreatNullsStyle.fromInt(
                    cbTreatNulls.getSelectedIndex()));
        }
    } 

    public void spPointDepth_stateChanged(ChangeEvent e) {
        points3D.setDepthSize(((Double)spPointDepth.getValue()).doubleValue());
    }
}


class Point3DEditor_spPointDepth_changeAdapter implements ChangeListener {
    private Point3DEditor adaptee;
    Point3DEditor_spPointDepth_changeAdapter(Point3DEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void stateChanged(ChangeEvent e) {
        adaptee.spPointDepth_stateChanged(e);
    }
}


final class Point3DEditor_cbEach_actionAdapter implements ActionListener {
    private Point3DEditor adaptee;
    Point3DEditor_cbEach_actionAdapter(Point3DEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.cbEach_actionPerformed(e);
    }
}


final class Point3DEditor_bColor_actionAdapter implements ActionListener {
    private Point3DEditor adaptee;
    Point3DEditor_bColor_actionAdapter(Point3DEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.bColor_actionPerformed(e);
    }
}

final class Point3Editor_cbTreatNulls_actionAdapter implements ActionListener {
    private Point3DEditor adaptee;
    Point3Editor_cbTreatNulls_actionAdapter(Point3DEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.cbTreatNulls_actionPerformed(e);
    }
}
