package com.steema.teechart.editors.series;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.border.EtchedBorder;
import javax.swing.border.Border;

import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Container;
import java.awt.Insets;

import com.steema.teechart.drawing.Color;
import com.steema.teechart.styles.SeriesPointer;
import com.steema.teechart.editors.GradientEditor;
import com.steema.teechart.styles.PointerStyle;
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
public class SeriesPointerEditor extends JPanel {

    private SeriesPointer pointer;
    JCheckBox cbVisible = new JCheckBox();
    JCheckBox cb3D = new JCheckBox();
    JCheckBox cbDark3D = new JCheckBox();
    JCheckBox cbInflate = new JCheckBox();
    JTabbedPane jTabbedPane1 = new JTabbedPane();
    JPanel jPanel1 = new JPanel();
    BorderLayout borderLayout1 = new BorderLayout();
    JPanel jPanel2 = new JPanel();
    GradientEditor gradient = new GradientEditor();
    BorderLayout borderLayout2 = new BorderLayout();
    JLabel jLabel1 = new JLabel();
    JComboBox cbStyle = new JComboBox();
    JLabel jLabel2 = new JLabel();
    JSpinner spWidth = new JSpinner();
    JLabel jLabel3 = new JLabel();
    JSpinner spHeight = new JSpinner();
    ButtonPen bPen = new ButtonPen();
    JPanel jPanel3 = new JPanel();
    Border border1 = BorderFactory.createEtchedBorder(EtchedBorder.RAISED,
            Color.white, new Color(148, 145, 140));
    JButton jButton1 = new JButton();
    JCheckBox cbDefaultBrush = new JCheckBox();
    JLabel jLabel4 = new JLabel();
    JSlider slTransp = new JSlider();

    public SeriesPointerEditor() {
        try {
            jbInit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        cbStyle.addItem(Language.getString("Square"));
        cbStyle.addItem(Language.getString("Circle"));
        cbStyle.addItem(Language.getString("Triangle"));
        cbStyle.addItem(Language.getString("Down_Triangle"));
        cbStyle.addItem(Language.getString("Cross"));
        cbStyle.addItem(Language.getString("Diagonal_Cross"));
        cbStyle.addItem(Language.getString("Star"));
        cbStyle.addItem(Language.getString("Diamond"));
        cbStyle.addItem(Language.getString("Small_Dot"));
        cbStyle.addItem(Language.getString("Nothing"));
        cbStyle.addItem(Language.getString("Left_Triangle"));
        cbStyle.addItem(Language.getString("Right_Triangle"));
        cbStyle.addItem(Language.getString("Sphere"));
        cbStyle.addItem(Language.getString("Polished_Sphere"));
        cbStyle.addItem(Language.getString("Hexagon"));

    }

    public SeriesPointerEditor(SeriesPointer pointer) {
        this();
        this.pointer = pointer;
        cbVisible.setSelected(pointer.getVisible());
        cb3D.setSelected(pointer.getDraw3D());
        cbDark3D.setSelected(pointer.getDark3D());
        cbInflate.setSelected(pointer.getInflateMargins());
        gradient.refreshControls(pointer.getGradient());
        cbStyle.setSelectedIndex(pointer.getStyle().getValue());
        spWidth.setValue(new Integer(pointer.getHorizSize()));
        spHeight.setValue(new Integer(pointer.getVertSize()));
        bPen.setPen(pointer.getPen());
        checkDefColor();
        slTransp.setValue(pointer.getTransparency());
    }

    private void checkDefColor() {
        cbDefaultBrush.setSelected(pointer.getColor().isEmpty() &&
                                   pointer.getPen().getColor().isEmpty());
    }

    private void jbInit() throws Exception {
        cbVisible.setMnemonic(Language.getString("V").charAt(0));
        cbVisible.setText(Language.getString("Visible"));
        cbVisible.setBounds(new Rectangle(3, 0, 55, 23));
        cbVisible.addActionListener(new
                                    SeriesPointerEditor_cbVisible_actionAdapter(this));
        this.setLayout(borderLayout1);
        cb3D.setMnemonic('3');
        cb3D.setText(Language.getString("3D"));
        cb3D.setBounds(new Rectangle(3, 21, 59, 23));
        cb3D.addActionListener(new SeriesPointerEditor_cb3D_actionAdapter(this));
        cbDark3D.setMnemonic(Language.getString("K").charAt(0));
        cbDark3D.setText(Language.getString("Dark_3D"));
        cbDark3D.setBounds(new Rectangle(65, 21, 81, 23));
        cbDark3D.addActionListener(new
                                   SeriesPointerEditor_cbDark3D_actionAdapter(this));
        cbInflate.setMnemonic(Language.getString("M").charAt(0));
        cbInflate.setText(Language.getString("Inflate_Margins"));
        cbInflate.setBounds(new Rectangle(3, 45, 103, 23));
        cbInflate.addActionListener(new
                                    SeriesPointerEditor_cbInflate_actionAdapter(this));
        jPanel1.setLayout(null);
        jPanel2.setLayout(borderLayout2);
        jLabel1.setDisplayedMnemonic(Language.getString("S").charAt(0));
        jLabel1.setHorizontalAlignment(SwingConstants.TRAILING);
        jLabel1.setLabelFor(cbStyle);
        jLabel1.setText(Language.getString("Style"));
        jLabel1.setBounds(new Rectangle(117, 4, 55, 14));
        cbStyle.setBounds(new Rectangle(178, 2, 117, 22));
        cbStyle.addActionListener(new SeriesPointerEditor_cbStyle_actionAdapter(this));
        jLabel2.setDisplayedMnemonic(Language.getString("W").charAt(0));
        jLabel2.setHorizontalAlignment(SwingConstants.TRAILING);
        jLabel2.setLabelFor(spWidth);
        jLabel2.setText(Language.getString("Width"));
        jLabel2.setBounds(new Rectangle(172, 37, 63, 14));
        spWidth.setBounds(new Rectangle(241, 34, 54, 20));
        spWidth.addChangeListener(new SeriesPointerEditor_spWidth_changeAdapter(this));
        jLabel3.setHorizontalAlignment(SwingConstants.TRAILING);
        jLabel3.setLabelFor(spHeight);
        jLabel3.setText(Language.getString("Height_4"));
        jLabel3.setBounds(new Rectangle(172, 62, 63, 14));
        spHeight.setBounds(new Rectangle(241, 58, 54, 20));
        spHeight.addChangeListener(new
                                   SeriesPointerEditor_spHeight_changeAdapter(this));
        bPen.setBounds(new Rectangle(204, 95, 91, 23));
        bPen.setMnemonic(Language.getString("B").charAt(0));
        bPen.setText(Language.getString("Border"));
        jPanel3.setBorder(border1);
        jPanel3.setBounds(new Rectangle(3, 79, 181, 41));
        jPanel3.setLayout(null);
        jButton1.setBounds(new Rectangle(7, 8, 78, 23));
        jButton1.setMargin(new Insets(2, 1, 2, 1));
        jButton1.setMnemonic(Language.getString("P").charAt(0));
        jButton1.setText(Language.getString("Pattern"));
        jButton1.addActionListener(new
                                   SeriesPointerEditor_jButton1_actionAdapter(this));
        cbDefaultBrush.setMnemonic(Language.getString("D").charAt(0));
        cbDefaultBrush.setText(Language.getString("Default"));
        cbDefaultBrush.setBounds(new Rectangle(90, 8, 87, 23));
        cbDefaultBrush.addActionListener(new
                                         SeriesPointerEditor_cbDefaultBrush_actionAdapter(this));
        jLabel4.setDisplayedMnemonic(Language.getString("T").charAt(0));
        jLabel4.setHorizontalAlignment(SwingConstants.TRAILING);
        jLabel4.setLabelFor(slTransp);
        jLabel4.setText(Language.getString("Transparency"));
        jLabel4.setBounds(new Rectangle(93, 130, 100, 14));
        slTransp.setBounds(new Rectangle(192, 125, 103, 24));
        slTransp.addChangeListener(new
                                   SeriesPointerEditor_slTransp_changeAdapter(this));
        jTabbedPane1.add(jPanel1, Language.getString("Format"));
        jPanel1.add(cbVisible);
        jPanel1.add(cb3D);
        jPanel1.add(cbDark3D);
        jPanel1.add(cbInflate);
        jPanel1.add(jLabel1);
        jPanel1.add(cbStyle);
        jPanel1.add(bPen);
        jPanel1.add(jPanel3);
        jPanel3.add(jButton1);
        jPanel3.add(cbDefaultBrush);
        jPanel1.add(jLabel3);
        jPanel1.add(jLabel2);
        jPanel1.add(spWidth);
        jPanel1.add(spHeight);
        jPanel1.add(slTransp);
        jPanel1.add(jLabel4);
        this.add(jTabbedPane1, java.awt.BorderLayout.CENTER);
        jTabbedPane1.add(jPanel2, Language.getString("Gradient"));
        jPanel2.add(gradient, BorderLayout.CENTER);
    }

    public void cbVisible_actionPerformed(ActionEvent e) {
        pointer.setVisible(cbVisible.isSelected());
    }

    public void cb3D_actionPerformed(ActionEvent e) {
        pointer.setDraw3D(cb3D.isSelected());
    }

    public void cbDark3D_actionPerformed(ActionEvent e) {
        pointer.setDark3D(cbDark3D.isSelected());
    }

    public void cbInflate_actionPerformed(ActionEvent e) {
        pointer.setInflateMargins(cbInflate.isSelected());
    }

    static public void edit(Container owner, SeriesPointer pointer) {
        JDialog d = new JDialog();
        d.setTitle(Language.getString("Pointer_Editor"));
        d.setModal(true);
        d.setSize(new Dimension(315, 250));
        SeriesPointerEditor g = new SeriesPointerEditor(pointer);
        d.getContentPane().add(g);
        d.setLocationRelativeTo(owner);
        d.setVisible(true);
    }

    public void cbStyle_actionPerformed(ActionEvent e) {
        if (pointer != null) {
            pointer.setStyle(PointerStyle.fromInt(cbStyle.getSelectedIndex()));
        }
    }

    public void spWidth_stateChanged(ChangeEvent e) {
        pointer.setHorizSize(((Integer) spWidth.getValue()).intValue());
    }

    public void spHeight_stateChanged(ChangeEvent e) {
        pointer.setVertSize(((Integer) spHeight.getValue()).intValue());
    }

    public void jButton1_actionPerformed(ActionEvent e) {
        BrushEditor.edit(this, pointer.getBrush());
    }

    public void cbDefaultBrush_actionPerformed(ActionEvent e) {
        if (cbDefaultBrush.isSelected()) {
            pointer.setColor(Color.EMPTY);
            pointer.getPen().setColor(Color.EMPTY);
        } else {
            if (pointer.getPen().getColor().isEmpty()) {
                pointer.getPen().setColor(Color.BLACK);
            }
        }

        bPen.invalidate();
    }

    public void slTransp_stateChanged(ChangeEvent e) {
        pointer.setTransparency(slTransp.getValue());
    }
}


class SeriesPointerEditor_slTransp_changeAdapter implements ChangeListener {
    private SeriesPointerEditor adaptee;
    SeriesPointerEditor_slTransp_changeAdapter(SeriesPointerEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void stateChanged(ChangeEvent e) {
        adaptee.slTransp_stateChanged(e);
    }
}


class SeriesPointerEditor_cbDefaultBrush_actionAdapter implements
        ActionListener {
    private SeriesPointerEditor adaptee;
    SeriesPointerEditor_cbDefaultBrush_actionAdapter(SeriesPointerEditor
            adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.cbDefaultBrush_actionPerformed(e);
    }
}


class SeriesPointerEditor_jButton1_actionAdapter implements ActionListener {
    private SeriesPointerEditor adaptee;
    SeriesPointerEditor_jButton1_actionAdapter(SeriesPointerEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.jButton1_actionPerformed(e);
    }
}


class SeriesPointerEditor_spHeight_changeAdapter implements ChangeListener {
    private SeriesPointerEditor adaptee;
    SeriesPointerEditor_spHeight_changeAdapter(SeriesPointerEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void stateChanged(ChangeEvent e) {
        adaptee.spHeight_stateChanged(e);
    }
}


class SeriesPointerEditor_spWidth_changeAdapter implements ChangeListener {
    private SeriesPointerEditor adaptee;
    SeriesPointerEditor_spWidth_changeAdapter(SeriesPointerEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void stateChanged(ChangeEvent e) {
        adaptee.spWidth_stateChanged(e);
    }
}


class SeriesPointerEditor_cbStyle_actionAdapter implements ActionListener {
    private SeriesPointerEditor adaptee;
    SeriesPointerEditor_cbStyle_actionAdapter(SeriesPointerEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.cbStyle_actionPerformed(e);
    }
}


final class SeriesPointerEditor_cbInflate_actionAdapter implements
        ActionListener {
    private SeriesPointerEditor adaptee;
    SeriesPointerEditor_cbInflate_actionAdapter(SeriesPointerEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.cbInflate_actionPerformed(e);
    }
}


final class SeriesPointerEditor_cbDark3D_actionAdapter implements
        ActionListener {
    private SeriesPointerEditor adaptee;
    SeriesPointerEditor_cbDark3D_actionAdapter(SeriesPointerEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.cbDark3D_actionPerformed(e);
    }
}


final class SeriesPointerEditor_cb3D_actionAdapter implements ActionListener {
    private SeriesPointerEditor adaptee;
    SeriesPointerEditor_cb3D_actionAdapter(SeriesPointerEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.cb3D_actionPerformed(e);
    }
}


final class SeriesPointerEditor_cbVisible_actionAdapter implements
        ActionListener {
    private SeriesPointerEditor adaptee;
    SeriesPointerEditor_cbVisible_actionAdapter(SeriesPointerEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.cbVisible_actionPerformed(e);
    }
}

