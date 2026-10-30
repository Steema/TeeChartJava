package com.steema.teechart.editors.series;

import com.steema.teechart.drawing.Color;
import javax.swing.*;

import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.steema.teechart.editors.BaseSeriesEditor;
import com.steema.teechart.styles.Series;
import com.steema.teechart.styles.Points;
import com.steema.teechart.editors.ButtonColor;
import com.steema.teechart.styles.CustomStack;
import com.steema.teechart.languages.Language;
import com.steema.teechart.styles.TreatNullsStyle;
import javax.swing.border.Border;
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
public class PointsEditor extends BaseSeriesEditor {


    private Points points;
    JLabel jLabel1 = new JLabel();
    JComboBox cbStack = new JComboBox();
    JPanel jPanel1 = new JPanel();
    Border border1 = BorderFactory.createEtchedBorder(EtchedBorder.RAISED,
            Color.white, new Color(148, 145, 140));
    //Border border2 = new TitledBorder(border1, Language.getString("TreatNulls"));
    JComboBox cbTreatNulls = new JComboBox();
    private final JPanel panel = new JPanel();
    private final JCheckBox cbEach = new JCheckBox();
    private final ButtonColor bColor = new ButtonColor();

    public PointsEditor() {
        try {
            jbInit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        cbStack.addItem(Language.getString("None"));
        cbStack.addItem(Language.getString("Overlap"));
        cbStack.addItem(Language.getString("Stack"));
        
        cbTreatNulls.addItem(Language.getString("DontPaint"));
        cbTreatNulls.addItem(Language.getString("Skip"));
        cbTreatNulls.addItem(Language.getString("Ignore"));
        
        JLabel lbTreatNulls = new JLabel();
        lbTreatNulls.setText(Language.getString("TreatNulls"));
        lbTreatNulls.setHorizontalAlignment(SwingConstants.TRAILING);
        lbTreatNulls.setDisplayedMnemonic('K');
        lbTreatNulls.setBounds(new Rectangle(-30, 89, 86, 14));
        lbTreatNulls.setBounds(38, 15, 86, 14);
        jPanel1.add(lbTreatNulls);
        panel.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), "Color", TitledBorder.LEADING, TitledBorder.TOP, null, null));
        panel.setLayout(null);
        panel.setBounds(new Rectangle(163, 79, 129, 80));
        //panel.setBorder(border6);
        panel.setBounds(167, 11, 129, 80);
        
        add(panel);
        cbEach.setBounds(new Rectangle(11, 15, 109, 23));
        cbEach.setBounds(11, 15, 109, 23);
        
        panel.add(cbEach);
        bColor.setBounds(new Rectangle(11, 43, 78, 23));
        bColor.setBounds(11, 43, 78, 23);
        
        panel.add(bColor);
    }

    public void setSeries(Series series, JTabbedPane tabs) {
        points=(Points)series;
        bColor.setColor(points.getColor());
        cbEach.setSelected(points.getColorEach());
        cbStack.setSelectedIndex(points.getStacked().getValue());
        SeriesPointerEditor pointerEditor = new SeriesPointerEditor(points.getPointer());
        pointerEditor.cbVisible.setVisible(false);
        cbTreatNulls.setSelectedIndex(points.getTreatNulls().getValue());

        tabs.add(pointerEditor,Language.getString("Pointer"), 0);
    }

    private void jbInit() throws Exception {
        this.setLayout(null);
        bColor.setMnemonic(Language.getString("C").charAt(0));
        bColor.setText(Language.getString("Color"));
        bColor.addActionListener(new PointsEditor_bColor_actionAdapter(this));
        cbEach.setMnemonic(Language.getString("E").charAt(0));
        cbEach.setText(Language.getString("Color_each"));
        cbEach.addActionListener(new PointsEditor_cbEach_actionAdapter(this));
        jLabel1.setDisplayedMnemonic(Language.getString("S").charAt(0));
        jLabel1.setLabelFor(cbStack);
        jLabel1.setText(Language.getString("Stacked"));
        jLabel1.setBounds(new Rectangle(11, 12, 132, 14));
        cbStack.setBounds(new Rectangle(11, 31, 124, 22));
        cbStack.addActionListener(new PointsEditor_cbStack_actionAdapter(this));
        cbTreatNulls.setBounds(new Rectangle(141, 11, 100, 22));
        cbTreatNulls.addActionListener(new 
                PointsEditor_cbTreatNulls_actionAdapter(this));
        jPanel1.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
        jPanel1.setBounds(new Rectangle(10, 102, 286, 42));
        jPanel1.setLayout(null);
        jPanel1.add(cbTreatNulls);
        this.add(jLabel1);
        this.add(cbStack);
        this.add(jPanel1);
    }

    public void bColor_actionPerformed(ActionEvent e) {
        points.setColor(bColor.getColor());
    }

    public void cbEach_actionPerformed(ActionEvent e) {
        points.setColorEach(cbEach.isSelected());
    }

    public void cbStack_actionPerformed(ActionEvent actionEvent) {
        if (points!=null) {
            points.setStacked(CustomStack.fromInt(cbStack.getSelectedIndex()));
        }
    }
    
    public void cbTreatNulls_actionPerformed(ActionEvent e) {
        if (points!=null) {
            points.setTreatNulls(TreatNullsStyle.fromInt(
                    cbTreatNulls.getSelectedIndex()));
        }
    }  
}


final class PointsEditor_cbStack_actionAdapter implements ActionListener {
    private PointsEditor adaptee;
    PointsEditor_cbStack_actionAdapter(PointsEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent actionEvent) {
        adaptee.cbStack_actionPerformed(actionEvent);
    }
}


final class PointsEditor_cbEach_actionAdapter implements ActionListener {
    private PointsEditor adaptee;
    PointsEditor_cbEach_actionAdapter(PointsEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.cbEach_actionPerformed(e);
    }
}


final class PointsEditor_bColor_actionAdapter implements ActionListener {
    private PointsEditor adaptee;
    PointsEditor_bColor_actionAdapter(PointsEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.bColor_actionPerformed(e);
    }
}

final class PointsEditor_cbTreatNulls_actionAdapter implements ActionListener {
    private PointsEditor adaptee;
    PointsEditor_cbTreatNulls_actionAdapter(PointsEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.cbTreatNulls_actionPerformed(e);
    }
}
