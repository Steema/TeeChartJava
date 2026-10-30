package com.steema.teechart.editors;

import java.awt.Rectangle;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Insets;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import javax.swing.JSpinner;
import javax.swing.border.EtchedBorder;

import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;

import com.steema.teechart.Cursor;
import com.steema.teechart.Zoom;
import com.steema.teechart.Chart;
import com.steema.teechart.PanelMarginUnits;
import com.steema.teechart.Panel;
import com.steema.teechart.ScrollMode;
import com.steema.teechart.languages.Language;

/**
 *
 * <p>Title: GeneralEditor class</p>
 *
 * <p>Description: Panel to edit Chart Zoom and Panning properties.</p>
 *
 * <p>Copyright (c)
 * 2005-2019 by Steema Software SL. All Rights Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 *
 */
public class GeneralEditor extends JPanel {


    private Zoom chartZoom;
    private Chart chart;

    public GeneralEditor() {
        try {
            jbInit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        jComboBox1.addItem(Language.getString("Percent"));
        jComboBox1.addItem(Language.getString("Pixels"));

        cbScrollMouse.addItem(Language.getString("Left"));
        cbScrollMouse.addItem(Language.getString("Middle"));
        cbScrollMouse.addItem(Language.getString("Right"));

        Utils.addCursorsToCombo(cbCursor);
    }

    final private ChangeListener sMarginListener = new ChangeListener() {
        public void stateChanged(ChangeEvent e) {
            Panel tmp = chart.getPanel();
            tmp.setMarginLeft(((Integer) ((JSpinner) e.getSource()).getValue()).
                              doubleValue());
        }
    };

    public GeneralEditor(Chart chart) {
        this();
        this.chart = chart;
        chartZoom = chart.getZoom();

        Panel tmp = chart.getPanel();
        sLeftMa.setValue(new Integer((int) tmp.getMarginLeft()));
        sTopMa.setValue(new Integer((int) tmp.getMarginTop()));
        sRightMa.setValue(new Integer((int) tmp.getMarginRight()));
        sBotMa.setValue(new Integer((int) tmp.getMarginBottom()));

        jComboBox1.setSelectedIndex(tmp.getMarginUnits().getValue());

        cbAllowZoom.setSelected(chart.getZoom().getAllow());
        cbAnimatedZoom.setSelected(chart.getZoom().getAnimated());
        bZoomPen.setPen(chart.getZoom().getPen());

        sLeftMa.addChangeListener(sMarginListener);
        sTopMa.addChangeListener(sMarginListener);
        sRightMa.addChangeListener(sMarginListener);
        sBotMa.addChangeListener(sMarginListener);

        ButtonGroup group = new ButtonGroup();
        group.add(rbScrollNone);
        group.add(rbScrollHoriz);
        group.add(rbScrollVert);
        group.add(rbScrollBoth);

        switch (chart.getPanning().getAllow().getValue()) {
        case 0:
            rbScrollNone.setSelected(true);
            break;
        case 1:
            rbScrollVert.setSelected(true);
            break;
        case 2:
            rbScrollHoriz.setSelected(true);
            break;
        default:
            rbScrollBoth.setSelected(true);
            break;
        }


        cbScrollMouse.setSelectedIndex(chart.getPanning().getMouseButton() - 1);
        cbCursor.setSelectedIndex(chart.getParent().getTeeCursor().getType());
    }

    private void jbInit() throws Exception {
        this.setLayout(borderLayout1);
        jPanel1.setBorder(border2);
        jPanel1.setBounds(new Rectangle(6, 36, 157, 148));
        jPanel1.setLayout(null);
        sRightMa.setBounds(new Rectangle(93, 55, 49, 19));
        sRightMa.addChangeListener(new GeneralEditor_sRightMa_changeAdapter(this));
        sTopMa.setBounds(new Rectangle(53, 28, 51, 20));
        sTopMa.addChangeListener(new GeneralEditor_sTopMa_changeAdapter(this));
        sLeftMa.setBounds(new Rectangle(11, 55, 53, 19));
        sLeftMa.addChangeListener(new GeneralEditor_sLeftMa_changeAdapter(this));
        sBotMa.setBounds(new Rectangle(53, 81, 54, 19));
        sBotMa.addChangeListener(new GeneralEditor_sBotMa_changeAdapter(this));
        jLabel1.setDisplayedMnemonic(Language.getString("U").charAt(0));
        jLabel1.setHorizontalAlignment(SwingConstants.TRAILING);
        jLabel1.setLabelFor(jComboBox1);
        jLabel1.setText(Language.getString("Units"));
        jLabel1.setBounds(new Rectangle(8, 116, 55, 17));
        jComboBox1.setBounds(new Rectangle(66, 115, 81, 19));
        jComboBox1.addActionListener(new GeneralEditor_jComboBox1_actionAdapter(this));
        cbCursor.setBounds(new Rectangle(70, 192, 81, 19));
        cbCursor.addActionListener(new GeneralEditor_cbCursor_actionAdapter(this));
        jLabel2.setDisplayedMnemonic(Language.getString("C").charAt(0));
        jLabel2.setHorizontalAlignment(SwingConstants.TRAILING);
        jLabel2.setLabelFor(cbCursor);
        jLabel2.setText(Language.getString("Cursor"));
        jLabel2.setBounds(new Rectangle(8, 193, 59, 17));
        jButton1.setBounds(new Rectangle(6, 6, 149, 25));
        jButton1.setMnemonic(Language.getString("E").charAt(0));
        jButton1.setText(Language.getString("Print_Preview"));
        jButton1.addActionListener(new GeneralEditor_jButton1_actionAdapter(this));
        jPanel3.setLayout(null);
        jPanel4.setBorder(border4);
        jPanel4.setBounds(new Rectangle(15, 15, 131, 120));
        jPanel4.setLayout(null);
        rbScrollNone.setMnemonic(Language.getString("N").charAt(0));
        rbScrollNone.setText(Language.getString("None"));
        rbScrollNone.setBounds(new Rectangle(9, 19, 110, 23));
        rbScrollNone.addActionListener(new
                                       GeneralEditor_rbScrollNone_actionAdapter(this));
        rbScrollBoth.setText(Language.getString("Both"));
        rbScrollBoth.setBounds(new Rectangle(9, 89, 111, 23));
        rbScrollBoth.addActionListener(new
                                       GeneralEditor_rbScrollBoth_actionAdapter(this));
        rbScrollHoriz.setText(Language.getString("Horizontal"));
        rbScrollHoriz.setBounds(new Rectangle(9, 43, 113, 23));
        rbScrollHoriz.addActionListener(new
                                        GeneralEditor_rbScrollHoriz_actionAdapter(this));
        rbScrollVert.setText(Language.getString("Vertical"));
        rbScrollVert.setBounds(new Rectangle(9, 66, 111, 23));
        rbScrollVert.addActionListener(new
                                       GeneralEditor_rbScrollVert_actionAdapter(this));
        jLabel3.setDisplayedMnemonic(Language.getString("M").charAt(0));
        jLabel3.setHorizontalAlignment(SwingConstants.LEFT);
        jLabel3.setLabelFor(cbScrollMouse);
        jLabel3.setText(Language.getString("Mouse_Button"));
        jLabel3.setBounds(new Rectangle(23, 147, 79, 19));
        cbScrollMouse.setBounds(new Rectangle(23, 168, 120, 19));
        cbScrollMouse.addActionListener(new
                                        GeneralEditor_cbScrollMouse_actionAdapter(this));
        cbAllowZoom.setMnemonic(Language.getString("A").charAt(0));
        cbAllowZoom.setText(Language.getString("Allow"));
        cbAllowZoom.setBounds(new Rectangle(12, 7, 83, 23));
        cbAllowZoom.addActionListener(new
                                      GeneralEditor_jCheckBox1_actionAdapter(this));
        jPanel2.setLayout(null);
        cbAnimatedZoom.setMnemonic(Language.getString("I").charAt(0));
        cbAnimatedZoom.setText(Language.getString("Animated"));
        cbAnimatedZoom.setBounds(new Rectangle(12, 28, 116, 22));
        cbAnimatedZoom.addActionListener(new
                                         GeneralEditor_cbAnimatedZoom_actionAdapter(this));
        jButton2.setBounds(new Rectangle(94, 64, 78, 25));
        jButton2.setMargin(new Insets(2, 1, 2, 1));
        jButton2.setMnemonic(Language.getString("A").charAt(0));
        jButton2.setText(Language.getString("Pattern"));
        jButton2.addActionListener(new GeneralEditor_jButton2_actionAdapter(this));
        jPanel5.setLayout(null);
        jPanel5.setPreferredSize(new Dimension(170, 1));
        bZoomPen.setBounds(new Rectangle(12, 64, 78, 25));
        bZoomPen.setMnemonic(Language.getString("B").charAt(0));
        bZoomPen.setText(Language.getString("Border"));
        jPanel1.add(jComboBox1);
        jPanel1.add(jLabel1);
        jPanel1.add(sTopMa);
        jPanel1.add(sLeftMa);
        jPanel1.add(sRightMa);
        jPanel1.add(sBotMa);
        jPanel5.add(cbCursor);
        jPanel5.add(jLabel2);
        jPanel5.add(jPanel1);
        jPanel2.add(cbAllowZoom);
        jPanel2.add(cbAnimatedZoom);
        jPanel2.add(jButton2);
        jPanel2.add(bZoomPen);
        jTabbedPane1.add(jPanel3, Language.getString("Scroll"));
        jPanel4.add(rbScrollHoriz);
        jPanel4.add(rbScrollVert);
        jPanel4.add(rbScrollBoth);
        jPanel4.add(rbScrollNone);
        jPanel3.add(jLabel3);
        jPanel3.add(cbScrollMouse);
        jPanel3.add(jPanel4);
        jPanel5.add(jButton1);
        this.add(jPanel5, java.awt.BorderLayout.WEST);
        this.add(jTabbedPane1, java.awt.BorderLayout.CENTER);
        jTabbedPane1.add(jPanel2, Language.getString("Zoom"));
    }

    JPanel jPanel1 = new JPanel();
    Border border1 = BorderFactory.createEtchedBorder(Color.white,
            new Color(148, 145, 140));
    Border border2 = new TitledBorder(border1, Language.getString("Margins"));
    JSpinner sTopMa = new JSpinner();
    JSpinner sRightMa = new JSpinner();
    JSpinner sLeftMa = new JSpinner();
    JSpinner sBotMa = new JSpinner();
    JLabel jLabel1 = new JLabel();
    JComboBox jComboBox1 = new JComboBox();
    JComboBox cbCursor = new JComboBox();
    JLabel jLabel2 = new JLabel();
    JTabbedPane jTabbedPane1 = new JTabbedPane();
    JPanel jPanel2 = new JPanel();
    JPanel jPanel3 = new JPanel();
    JButton jButton1 = new JButton();
    JPanel jPanel4 = new JPanel();
    Border border3 = BorderFactory.createEtchedBorder(EtchedBorder.RAISED,
            Color.white, new Color(148, 145, 140));
    Border border4 = new TitledBorder(border3, Language.getString("Allow_Scroll"));
    JRadioButton rbScrollNone = new JRadioButton();
    JRadioButton rbScrollBoth = new JRadioButton();
    JRadioButton rbScrollHoriz = new JRadioButton();
    JRadioButton rbScrollVert = new JRadioButton();
    JLabel jLabel3 = new JLabel();
    JComboBox cbScrollMouse = new JComboBox();
    JCheckBox cbAllowZoom = new JCheckBox();
    JCheckBox cbAnimatedZoom = new JCheckBox();
    JButton jButton2 = new JButton();
    JPanel jPanel5 = new JPanel();
    BorderLayout borderLayout1 = new BorderLayout();
    ButtonPen bZoomPen = new ButtonPen();

    public void jCheckBox1_actionPerformed(ActionEvent e) {
        chartZoom.setAllow(cbAllowZoom.isSelected());
    }

    public void cbAnimatedZoom_actionPerformed(ActionEvent e) {
        chartZoom.setAnimated(cbAnimatedZoom.isSelected());
    }

    public void jButton2_actionPerformed(ActionEvent e) {
        BrushEditor.edit(this, chartZoom.getBrush());
    }

    public void jComboBox1_actionPerformed(ActionEvent e) {
        if (chart != null) {
            if (jComboBox1.getSelectedIndex() == 0) {
                chart.getPanel().setMarginUnits(PanelMarginUnits.PERCENT);
                setUpDownLimits(0, 100);
            } else {
                chart.getPanel().setMarginUnits(PanelMarginUnits.PIXELS);
                setUpDownLimits(0, 2000);
            }
        }
    }

    static private void setUpDownLimit(JSpinner spinner, int min,
                                       int max) {
        ((SpinnerNumberModel) spinner.getModel()).setMinimum(new Integer(min));
        ((SpinnerNumberModel) spinner.getModel()).setMaximum(new Integer(max));
    }

    private void setUpDownLimits(int min, int max) {
        setUpDownLimit(sBotMa, min, max);
        setUpDownLimit(sTopMa, min, max);
        setUpDownLimit(sLeftMa, min, max);
        setUpDownLimit(sRightMa, min, max);
    }

    public void jButton1_actionPerformed(ActionEvent e) {
        PreviewDialog.showModal(chart);
    }

    public void rbScrollNone_actionPerformed(ActionEvent e) {
        chart.getPanning().setAllow(ScrollMode.NONE);
    }

    public void rbScrollHoriz_actionPerformed(ActionEvent e) {
        chart.getPanning().setAllow(ScrollMode.HORIZONTAL);
    }

    public void rbScrollVert_actionPerformed(ActionEvent e) {
        chart.getPanning().setAllow(ScrollMode.VERTICAL);
    }

    public void rbScrollBoth_actionPerformed(ActionEvent e) {
        chart.getPanning().setAllow(ScrollMode.BOTH);
    }

    public void cbCursor_actionPerformed(ActionEvent e) {
        if (chart != null) {
            chart.getParent().setCursor(new Cursor(cbCursor.getSelectedIndex()));
        }
    }

    public void sTopMa_stateChanged(ChangeEvent e) {
        chart.getPanel().setMarginTop(((Integer) sTopMa.getValue()).intValue());
    }

    public void sBotMa_stateChanged(ChangeEvent e) {
        chart.getPanel().setMarginBottom(((Integer) sBotMa.getValue()).intValue());
    }

    public void sLeftMa_stateChanged(ChangeEvent e) {
        chart.getPanel().setMarginLeft(((Integer) sLeftMa.getValue()).intValue());
    }

    public void sRightMa_stateChanged(ChangeEvent e) {
        chart.getPanel().setMarginRight(((Integer) sRightMa.getValue()).
                                        intValue());
    }

    public void cbScrollMouse_actionPerformed(ActionEvent e) {
        if (chart != null) {
            switch (cbScrollMouse.getSelectedIndex()) {
            case 0:
                chart.getPanning().setMouseButton(1);
                break;
            case 1:
                chart.getPanning().setMouseButton(2);
                break;
            case 2:
                chart.getPanning().setMouseButton(3);
                break;
            }
        }
    }
}


final class GeneralEditor_sRightMa_changeAdapter implements ChangeListener {
    private GeneralEditor adaptee;
    GeneralEditor_sRightMa_changeAdapter(GeneralEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void stateChanged(ChangeEvent e) {
        adaptee.sRightMa_stateChanged(e);
    }
}


final class GeneralEditor_sLeftMa_changeAdapter implements ChangeListener {
    private GeneralEditor adaptee;
    GeneralEditor_sLeftMa_changeAdapter(GeneralEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void stateChanged(ChangeEvent e) {
        adaptee.sLeftMa_stateChanged(e);
    }
}


final class GeneralEditor_sBotMa_changeAdapter implements ChangeListener {
    private GeneralEditor adaptee;
    GeneralEditor_sBotMa_changeAdapter(GeneralEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void stateChanged(ChangeEvent e) {
        adaptee.sBotMa_stateChanged(e);
    }
}


final class GeneralEditor_sTopMa_changeAdapter implements ChangeListener {
    private GeneralEditor adaptee;
    GeneralEditor_sTopMa_changeAdapter(GeneralEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void stateChanged(ChangeEvent e) {
        adaptee.sTopMa_stateChanged(e);
    }
}


final class GeneralEditor_cbCursor_actionAdapter implements ActionListener {
    private GeneralEditor adaptee;
    GeneralEditor_cbCursor_actionAdapter(GeneralEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.cbCursor_actionPerformed(e);
    }
}


final class GeneralEditor_rbScrollBoth_actionAdapter implements ActionListener {
    private GeneralEditor adaptee;
    GeneralEditor_rbScrollBoth_actionAdapter(GeneralEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.rbScrollBoth_actionPerformed(e);
    }
}


final class GeneralEditor_rbScrollVert_actionAdapter implements ActionListener {
    private GeneralEditor adaptee;
    GeneralEditor_rbScrollVert_actionAdapter(GeneralEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.rbScrollVert_actionPerformed(e);
    }
}


final class GeneralEditor_rbScrollHoriz_actionAdapter implements ActionListener {
    private GeneralEditor adaptee;
    GeneralEditor_rbScrollHoriz_actionAdapter(GeneralEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.rbScrollHoriz_actionPerformed(e);
    }
}


class GeneralEditor_cbScrollMouse_actionAdapter implements ActionListener {
    private GeneralEditor adaptee;
    GeneralEditor_cbScrollMouse_actionAdapter(GeneralEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.cbScrollMouse_actionPerformed(e);
    }
}


final class GeneralEditor_rbScrollNone_actionAdapter implements ActionListener {
    private GeneralEditor adaptee;
    GeneralEditor_rbScrollNone_actionAdapter(GeneralEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.rbScrollNone_actionPerformed(e);
    }
}


final class GeneralEditor_jButton1_actionAdapter implements ActionListener {
    private GeneralEditor adaptee;
    GeneralEditor_jButton1_actionAdapter(GeneralEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.jButton1_actionPerformed(e);
    }
}


final class GeneralEditor_jComboBox1_actionAdapter implements ActionListener {
    private GeneralEditor adaptee;
    GeneralEditor_jComboBox1_actionAdapter(GeneralEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.jComboBox1_actionPerformed(e);
    }
}


final class GeneralEditor_jButton2_actionAdapter implements ActionListener {
    private GeneralEditor adaptee;
    GeneralEditor_jButton2_actionAdapter(GeneralEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.jButton2_actionPerformed(e);
    }
}


final class GeneralEditor_cbAnimatedZoom_actionAdapter implements
        ActionListener {
    private GeneralEditor adaptee;
    GeneralEditor_cbAnimatedZoom_actionAdapter(GeneralEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.cbAnimatedZoom_actionPerformed(e);
    }
}


final class GeneralEditor_jCheckBox1_actionAdapter implements ActionListener {
    private GeneralEditor adaptee;
    GeneralEditor_jCheckBox1_actionAdapter(GeneralEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.jCheckBox1_actionPerformed(e);
    }
}

