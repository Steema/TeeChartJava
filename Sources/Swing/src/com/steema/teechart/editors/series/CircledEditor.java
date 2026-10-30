package com.steema.teechart.editors.series;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.border.EtchedBorder;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;

import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.steema.teechart.styles.Circular;
import com.steema.teechart.styles.Series;
import com.steema.teechart.editors.BaseSeriesEditor;
import com.steema.teechart.editors.ButtonColor;
import com.steema.teechart.editors.GradientEditor;
import com.steema.teechart.drawing.Color;
import com.steema.teechart.Chart;
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
public class CircledEditor extends BaseSeriesEditor {


    private Circular circular;
    JCheckBox cbCircled = new JCheckBox();
    JCheckBox cb3D = new JCheckBox();
    ButtonColor bColor = new ButtonColor();
    JButton bGradient = new JButton();
    JLabel jLabel1 = new JLabel();
    JSpinner spRotation = new JSpinner();
    JPanel jPanel1 = new JPanel();
    Border border1 = BorderFactory.createEtchedBorder(EtchedBorder.RAISED,
            Color.white, new Color(148, 145, 140));
    Border border2 = new TitledBorder(border1, Language.getString("Radius"));
    JLabel jLabel2 = new JLabel();
    JLabel jLabel3 = new JLabel();
    JSpinner spHoriz = new JSpinner();
    JSpinner spVert = new JSpinner();
    JCheckBox cbAutoHoriz = new JCheckBox();
    JCheckBox cbAutoVert = new JCheckBox();
    JCheckBox cbTransp = new JCheckBox();

    public CircledEditor() {
        try {
            jbInit();

            ((SpinnerNumberModel) spHoriz.getModel()).setMinimum(new Integer(0));
            ((SpinnerNumberModel) spVert.getModel()).setMinimum(new Integer(0));

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * setSeries
     *
     * @param series Series
     * @param tabs JTabbedPane
     */
    public void setSeries(Series series, JTabbedPane tabs) {
        circular = (Circular) series;

        // TODO: Bad trick cast.
        cb3D.setSelected(((Chart) circular.getChart()).getAspect().getView3D());

        cbCircled.setSelected(circular.getCircled());
        bColor.setColor(circular.getColor());
        spRotation.setValue(new Integer(circular.getRotationAngle()));

        spHoriz.setValue(new Integer(circular.getCustomXRadius()));
        cbAutoHoriz.setSelected(circular.getCustomXRadius() == 0);

        spVert.setValue(new Integer(circular.getCustomYRadius()));
        cbAutoVert.setSelected(circular.getCustomYRadius() == 0);

        cbTransp.setSelected(circular.getCircleBackColor() == Color.EMPTY);
        cbTransp.setEnabled(!cbTransp.isSelected());

    }

    private void jbInit() throws Exception {
        this.setLayout(null);
        cbCircled.setMnemonic(Language.getString("C").charAt(0));
        cbCircled.setText(Language.getString("Circled"));
        cbCircled.setBounds(new Rectangle(7, 10, 90, 23));
        cbCircled.addActionListener(new CircledEditor_cbCircled_actionAdapter(this));
        cb3D.setMnemonic('3');
        cb3D.setText(Language.getString("3Dimensions"));
        cb3D.setBounds(new Rectangle(7, 38, 115, 23));
        cb3D.addActionListener(new CircledEditor_cb3D_actionAdapter(this));
        bColor.setBounds(new Rectangle(10, 66, 97, 23));
        bColor.setMnemonic(Language.getString("O").charAt(0));
        bColor.setText(Language.getString("Color"));
        bColor.addActionListener(new CircledEditor_bColor_actionAdapter(this));
        bGradient.setBounds(new Rectangle(118, 66, 93, 23));
        bGradient.setMnemonic(Language.getString("G").charAt(0));
        bGradient.setText(Language.getString("Gradient"));
        bGradient.addActionListener(new CircledEditor_bGradient_actionAdapter(this));
        jLabel1.setDisplayedMnemonic(Language.getString("R").charAt(0));
        jLabel1.setLabelFor(spRotation);
        jLabel1.setText(Language.getString("Rotation"));
        jLabel1.setBounds(new Rectangle(134, 15, 65, 15));
        spRotation.setBounds(new Rectangle(134, 34, 64, 20));
        spRotation.addChangeListener(new CircledEditor_spRotation_changeAdapter(this));
        jPanel1.setBorder(border2);
        jPanel1.setBounds(new Rectangle(8, 126, 212, 75));
        jPanel1.setLayout(null);
        jLabel2.setDisplayedMnemonic(Language.getString("H").charAt(0));
        jLabel2.setHorizontalAlignment(SwingConstants.TRAILING);
        jLabel2.setText(Language.getString("Horizontal"));
        jLabel2.setBounds(new Rectangle(11, 19, 79, 15));
        jLabel3.setDisplayedMnemonic(Language.getString("V").charAt(0));
        jLabel3.setHorizontalAlignment(SwingConstants.TRAILING);
        jLabel3.setText(Language.getString("Vertical"));
        jLabel3.setBounds(new Rectangle(9, 46, 81, 15));
        spHoriz.setBounds(new Rectangle(94, 16, 53, 20));
        spHoriz.addChangeListener(new CircledEditor_spHoriz_changeAdapter(this));
        spVert.setBounds(new Rectangle(94, 44, 53, 20));
        spVert.addChangeListener(new CircledEditor_spVert_changeAdapter(this));
        cbAutoHoriz.setText(Language.getString("Auto"));
        cbAutoHoriz.setBounds(new Rectangle(150, 14, 54, 23));
        cbAutoHoriz.addActionListener(new
                                      CircledEditor_cbAutoHoriz_actionAdapter(this));
        cbAutoVert.setText(Language.getString("Auto"));
        cbAutoVert.setBounds(new Rectangle(150, 42, 55, 23));
        cbAutoVert.addActionListener(new CircledEditor_jCheckBox1_actionAdapter(this));
        cbTransp.setMnemonic(Language.getString("T").charAt(0));
        cbTransp.setText(Language.getString("Transparent"));
        cbTransp.setBounds(new Rectangle(9, 97, 142, 23));
        cbTransp.addActionListener(new CircledEditor_cbTransp_actionAdapter(this));
        this.add(cbCircled);
        this.add(cb3D);
        this.add(spRotation);
        this.add(jLabel1);
        this.add(jPanel1);
        jPanel1.add(jLabel2);
        jPanel1.add(spHoriz);
        jPanel1.add(jLabel3);
        jPanel1.add(spVert);
        jPanel1.add(cbAutoHoriz);
        jPanel1.add(cbAutoVert);
        this.add(bGradient);
        this.add(cbTransp);
        this.add(bColor);
    }

    public void cbCircled_actionPerformed(ActionEvent e) {
        circular.setCircled(cbCircled.isSelected());
    }

    public void cb3D_actionPerformed(ActionEvent e) {
        // TODO: Bad trick casting to Chart.
        ((Chart) circular.getChart()).getAspect().setView3D(cb3D.isSelected());
    }

    public void bGradient_actionPerformed(ActionEvent e) {
        GradientEditor.edit(this, circular.getCircleGradient());
        cbTransp.setSelected(false);
        cbTransp.setEnabled(true);
    }

    public void bColor_actionPerformed(ActionEvent e) {
        circular.setColor(bColor.getColor());
        cbTransp.setSelected(false);
        cbTransp.setEnabled(true);
    }

    public void spRotation_stateChanged(ChangeEvent e) {
        circular.setRotationAngle(((Integer) spRotation.getValue()).intValue());
    }

    public void cbAutoHoriz_actionPerformed(ActionEvent e) {
        if (cbAutoHoriz.isSelected()) {
            circular.setCustomXRadius(0);
            spHoriz.setValue(new Integer(0));
        }
    }

    public void spHoriz_stateChanged(ChangeEvent e) {
        if (circular!=null) {
            circular.setCustomXRadius(((Integer) spHoriz.getValue()).intValue());
            cbAutoHoriz.setSelected(circular.getCustomXRadius() == 0);
        }
    }

    public void cbAutoVert_actionPerformed(ActionEvent e) {
        if (cbAutoVert.isSelected()) {
            circular.setCustomYRadius(0);
            spVert.setValue(new Integer(0));
        }
    }

    public void spVert_stateChanged(ChangeEvent e) {
        if (circular!=null) {
            circular.setCustomYRadius(((Integer) spVert.getValue()).intValue());
            cbAutoVert.setSelected(circular.getCustomYRadius() == 0);
        }
    }

    public void cbTransp_actionPerformed(ActionEvent e) {
        if (cbTransp.isSelected()) {

            circular.setCircleBackColor(Color.EMPTY);
            circular.getCircleGradient().setVisible(false);
            bColor.invalidate();
        }

        cbTransp.setEnabled(!cbTransp.isSelected());
    }
}


final class CircledEditor_cbTransp_actionAdapter implements ActionListener {
    private CircledEditor adaptee;
    CircledEditor_cbTransp_actionAdapter(CircledEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.cbTransp_actionPerformed(e);
    }
}


final class CircledEditor_spVert_changeAdapter implements ChangeListener {
    private CircledEditor adaptee;
    CircledEditor_spVert_changeAdapter(CircledEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void stateChanged(ChangeEvent e) {
        adaptee.spVert_stateChanged(e);
    }
}


final class CircledEditor_jCheckBox1_actionAdapter implements ActionListener {
    private CircledEditor adaptee;
    CircledEditor_jCheckBox1_actionAdapter(CircledEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.cbAutoVert_actionPerformed(e);
    }
}


final class CircledEditor_spHoriz_changeAdapter implements ChangeListener {
    private CircledEditor adaptee;
    CircledEditor_spHoriz_changeAdapter(CircledEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void stateChanged(ChangeEvent e) {
        adaptee.spHoriz_stateChanged(e);
    }
}


final class CircledEditor_cbAutoHoriz_actionAdapter implements ActionListener {
    private CircledEditor adaptee;
    CircledEditor_cbAutoHoriz_actionAdapter(CircledEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.cbAutoHoriz_actionPerformed(e);
    }
}


final class CircledEditor_spRotation_changeAdapter implements ChangeListener {
    private CircledEditor adaptee;
    CircledEditor_spRotation_changeAdapter(CircledEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void stateChanged(ChangeEvent e) {
        adaptee.spRotation_stateChanged(e);
    }
}


final class CircledEditor_bColor_actionAdapter implements ActionListener {
    private CircledEditor adaptee;
    CircledEditor_bColor_actionAdapter(CircledEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.bColor_actionPerformed(e);
    }
}


final class CircledEditor_bGradient_actionAdapter implements ActionListener {
    private CircledEditor adaptee;
    CircledEditor_bGradient_actionAdapter(CircledEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.bGradient_actionPerformed(e);
    }
}


final class CircledEditor_cb3D_actionAdapter implements ActionListener {
    private CircledEditor adaptee;
    CircledEditor_cb3D_actionAdapter(CircledEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.cb3D_actionPerformed(e);
    }
}


final class CircledEditor_cbCircled_actionAdapter implements ActionListener {
    private CircledEditor adaptee;
    CircledEditor_cbCircled_actionAdapter(CircledEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.cbCircled_actionPerformed(e);
    }
}

