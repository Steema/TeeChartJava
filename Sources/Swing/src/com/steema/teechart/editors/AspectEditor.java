package com.steema.teechart.editors;

import java.awt.event.ActionEvent;
import java.awt.Rectangle;
import java.awt.event.ActionListener;

import javax.swing.JPanel;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.JSlider;
import javax.swing.SwingConstants;

import com.steema.teechart.Chart;
import com.steema.teechart.Aspect;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import com.steema.teechart.languages.Language;

/**
 *
 * <p>Title: AspectEditor class</p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright (c) 2004-2019 by Steema Software SL. All Rights Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 *
 */
public class AspectEditor extends JPanel {

    private Chart chart;
    private Aspect aspect;

    public AspectEditor() {
        try {
            jbInit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public AspectEditor(Chart chart) {
      this();

      this.chart=chart;
      aspect=chart.getAspect();

      initDialog=true;

      cb3D.setSelected(aspect.getView3D());
      cbOrtho.setSelected(aspect.getOrthogonal());
      cbClip.setSelected(aspect.getClipPoints());
      jZoom.setValue(aspect.getZoom());
      jRotation.setValue(aspect.getRotation());
      jElevation.setValue(aspect.getElevation());
      jRotation.setEnabled(!cbOrtho.isSelected());
      jElevation.setEnabled(!cbOrtho.isSelected());
      jPerspective.setValue(aspect.getPerspective());
      jHoriz.setValue(aspect.getHorizOffset());
      jVert.setValue(aspect.getVertOffset());

      final SpinnerNumberModel sp3DModel = (SpinnerNumberModel) sp3D.
                                             getModel();
      sp3DModel.setMinimum(new Integer(0));
      sp3DModel.setMaximum(new Integer(100));
      sp3DModel.setValue(new Integer(aspect.getChart3DPercent()));

      final SpinnerNumberModel spOrthoModel = (SpinnerNumberModel) spOrthoAngle.
                                             getModel();
      spOrthoModel.setMinimum(new Integer(0));
      spOrthoModel.setMaximum(new Integer(90));
      spOrthoModel.setValue(new Integer(aspect.getOrthoAngle()));

      cbSmooth.setSelected(chart.getGraphics3D().getSmoothingMode());

      cbZoomText.setSelected(aspect.getZoomText());

      initDialog=false;
    }

    private void jbInit() throws Exception {
        this.setLayout(null);
        cb3D.setMnemonic('3');
        cb3D.setText(Language.getString("3Dimensions"));
        cb3D.setBounds(new Rectangle(5, 5, 106, 24));
        cb3D.addActionListener(new AspectEditor_jCheckBox1_actionAdapter(this));
        cbOrtho.setMnemonic(Language.getString("O").charAt(0));
        cbOrtho.setText(Language.getString("Orthogonal"));
        cbOrtho.setBounds(new Rectangle(5, 59, 98, 19));
        cbOrtho.addActionListener(new AspectEditor_cbOrtho_actionAdapter(this));
        cbClip.setMnemonic(Language.getString("C").charAt(0));
        cbClip.setText(Language.getString("Clip_points"));
        cbClip.setBounds(new Rectangle(5, 113, 96, 19));
        cbClip.addActionListener(new AspectEditor_cbClip_actionAdapter(this));
        jLabel1.setDisplayedMnemonic(Language.getString("Z").charAt(0));
        jLabel1.setHorizontalAlignment(SwingConstants.TRAILING);
        jLabel1.setLabelFor(jZoom);
        jLabel1.setText(Language.getString("Zoom"));
        jLabel1.setBounds(new Rectangle(145, 5, 42, 24));
        jZoom.setMaximum(1000);
        jZoom.setMinimum(1);
        jZoom.setPaintLabels(true);
        jZoom.setPaintTicks(true);
        jZoom.setBounds(new Rectangle(193, 5, 151, 31));
        jZoom.addChangeListener(new AspectEditor_jZoom_changeAdapter(this));
        labelZoom.setText("100%");
        labelZoom.setBounds(new Rectangle(347, 6, 38, 20));
        jRotation.setMaximum(360);
        jRotation.setMinimum(270);
        jRotation.setPaintLabels(true);
        jRotation.setPaintTicks(true);
        jRotation.setBounds(new Rectangle(192, 41, 151, 31));
        jRotation.addChangeListener(new AspectEditor_jSlider1_changeAdapter(this));
        jLabel2.setDisplayedMnemonic(Language.getString("R").charAt(0));
        jLabel2.setHorizontalAlignment(SwingConstants.RIGHT);
        jLabel2.setLabelFor(jRotation);
        jLabel2.setText(Language.getString("Rotation"));
        jLabel2.setBounds(new Rectangle(135, 40, 52, 24));
        labelRot.setText("360");
        labelRot.setBounds(new Rectangle(346, 43, 38, 20));
        jLabel3.setToolTipText("");
        jLabel3.setDisplayedMnemonic(Language.getString("E").charAt(0));
        jLabel3.setHorizontalAlignment(SwingConstants.RIGHT);
        jLabel3.setLabelFor(jElevation);
        jLabel3.setText(Language.getString("Elevation"));
        jLabel3.setBounds(new Rectangle(119, 76, 66, 24));
        jElevation.setMaximum(360);
        jElevation.setMinimum(270);
        jElevation.setPaintLabels(true);
        jElevation.setPaintTicks(true);
        jElevation.setBounds(new Rectangle(199, 77, 138, 30));
        jElevation.addChangeListener(new AspectEditor_jElevation_changeAdapter(this));
        labelEle.setText("360");
        labelEle.setBounds(new Rectangle(344, 77, 33, 20));
        jLabel4.setDisplayedMnemonic(Language.getString("P").charAt(0));
        jLabel4.setHorizontalAlignment(SwingConstants.RIGHT);
        jLabel4.setLabelFor(jPerspective);
        jLabel4.setText(Language.getString("Perspective"));
        jLabel4.setBounds(new Rectangle(119, 189, 69, 19));
        jPerspective.setMaximum(200);
        jPerspective.setBounds(new Rectangle(192, 185, 152, 27));
        jPerspective.addChangeListener(new
                                       AspectEditor_jPerspective_changeAdapter(this));
        labelPer.setText("100");
        labelPer.setBounds(new Rectangle(345, 185, 38, 22));
        jLabel5.setDisplayedMnemonic(Language.getString("H").charAt(0));
        jLabel5.setHorizontalAlignment(SwingConstants.RIGHT);
        jLabel5.setLabelFor(jHoriz);
        jLabel5.setText(Language.getString("Horiz_Offset"));
        jLabel5.setBounds(new Rectangle(110, 120, 79, 14));
        jHoriz.setMinimum( -1500);
        jHoriz.setMaximum( 1500);
        jHoriz.setBounds(new Rectangle(199, 113, 138, 29));
        jHoriz.addChangeListener(new AspectEditor_jHoriz_changeAdapter(this));
        labelHoriz.setToolTipText("");
        labelHoriz.setText("0");
        labelHoriz.setBounds(new Rectangle(344, 114, 31, 24));
        jLabel6.setDisplayedMnemonic(Language.getString("V").charAt(0));
        jLabel6.setHorizontalAlignment(SwingConstants.RIGHT);
        jLabel6.setLabelFor(jVert);
        jLabel6.setText(Language.getString("Vert_Offset"));
        jLabel6.setBounds(new Rectangle(106, 150, 83, 22));
        jVert.setMinimum( -1500);
        jVert.setMaximum( 1500);
        jVert.setBounds(new Rectangle(201, 147, 135, 26));
        jVert.addChangeListener(new AspectEditor_jVert_changeAdapter(this));
        labelVert.setToolTipText("");
        labelVert.setText("0");
        labelVert.setBounds(new Rectangle(342, 148, 35, 24));
        jLabel7.setHorizontalAlignment(SwingConstants.TRAILING);
        jLabel7.setLabelFor(sp3D);
        jLabel7.setText(Language.getString("3D"));
        jLabel7.setBounds(new Rectangle(8, 30, 43, 20));
        sp3D.setBounds(new Rectangle(57, 29, 50, 19));
        sp3D.addChangeListener(new AspectEditor_sp3D_changeAdapter(this));
        cbSmooth.setMnemonic(Language.getString("S").charAt(0));
        cbSmooth.setText(Language.getString("Smooth"));
        cbSmooth.setBounds(new Rectangle(5, 151, 81, 23));
        cbSmooth.addActionListener(new AspectEditor_cbSmooth_actionAdapter(this));
        jLabel8.setHorizontalAlignment(SwingConstants.TRAILING);
        jLabel8.setLabelFor(spOrthoAngle);
        jLabel8.setText(Language.getString("Angle"));
        jLabel8.setBounds(new Rectangle(4, 84, 50, 14));
        spOrthoAngle.setBounds(new Rectangle(58, 79, 48, 21));
        spOrthoAngle.addChangeListener(new
                                       AspectEditor_spOrthoAngle_changeAdapter(this));
        cbZoomText.setToolTipText("");
        cbZoomText.setMnemonic(Language.getString("T").charAt(0));
        cbZoomText.setText(Language.getString("Zoom_Text"));
        cbZoomText.setBounds(new Rectangle(5, 186, 81, 23));
        cbZoomText.addActionListener(new AspectEditor_cbZoomText_actionAdapter(this));
        jLabel9.setText("%");
        jLabel9.setBounds(new Rectangle(110, 30, 16, 20));
        this.add(cbClip);
        this.add(cb3D);
        this.add(jLabel1);
        this.add(jLabel2);
        this.add(labelZoom);
        this.add(labelRot);
        this.add(jRotation);
        this.add(jZoom);
        this.add(jElevation);
        this.add(labelEle);
        this.add(jLabel3);
        this.add(jLabel4);
        this.add(jPerspective);
        this.add(cbZoomText);
        this.add(jLabel5);
        this.add(jHoriz);
        this.add(labelHoriz);
        this.add(jVert);
        this.add(labelVert);
        this.add(jLabel6);
        this.add(jLabel7);
        this.add(sp3D);
        this.add(cbSmooth);
        this.add(jLabel8);
        this.add(spOrthoAngle);
        this.add(cbOrtho);
        this.add(jLabel9);
        this.add(labelPer);
        jHoriz.setMaximum(1500);
        jVert.setMaximum(1500);
    }

    JCheckBox cb3D = new JCheckBox();
    JCheckBox cbOrtho = new JCheckBox();
    JCheckBox cbClip = new JCheckBox();
    JLabel jLabel1 = new JLabel();
    JSlider jZoom = new JSlider();
    JLabel labelZoom = new JLabel();
    JSlider jRotation = new JSlider();
    JLabel jLabel2 = new JLabel();
    JLabel labelRot = new JLabel();
    JLabel jLabel3 = new JLabel();
    JSlider jElevation = new JSlider();
    JLabel labelEle = new JLabel();
    JLabel jLabel4 = new JLabel();
    JSlider jPerspective = new JSlider();
    JLabel labelPer = new JLabel();
    JLabel jLabel5 = new JLabel();
    JSlider jHoriz = new JSlider();
    JLabel labelHoriz = new JLabel();
    JLabel jLabel6 = new JLabel();
    JSlider jVert = new JSlider();
    JLabel labelVert = new JLabel();
    JLabel jLabel7 = new JLabel();
    JSpinner sp3D = new JSpinner();
    JCheckBox cbSmooth = new JCheckBox();
    JLabel jLabel8 = new JLabel();
    JSpinner spOrthoAngle = new JSpinner();
    JCheckBox cbZoomText = new JCheckBox();
    JLabel jLabel9 = new JLabel();
    private boolean initDialog;
    public void jCheckBox1_actionPerformed(ActionEvent e) {
      aspect.setView3D(cb3D.isSelected());
    }

    public void cbOrtho_actionPerformed(ActionEvent e) {
        aspect.setOrthogonal(cbOrtho.isSelected());
        jRotation.setEnabled(!cbOrtho.isSelected());
        jElevation.setEnabled(!cbOrtho.isSelected());
    }

    public void cbClip_actionPerformed(ActionEvent e) {
        aspect.setClipPoints(cbClip.isSelected());
    }

    public void jZoom_stateChanged(ChangeEvent e) {
        aspect.setZoom(jZoom.getValue());
        labelZoom.setText(Integer.toString(aspect.getZoom()));
    }

    public void jSlider1_stateChanged(ChangeEvent e) {
        aspect.setRotation(jRotation.getValue());
        labelRot.setText(Integer.toString(aspect.getRotation()));
    }

    public void jElevation_stateChanged(ChangeEvent e) {
        aspect.setElevation(jElevation.getValue());
        labelEle.setText(Integer.toString(aspect.getElevation()));
    }

    public void jPerspective_stateChanged(ChangeEvent e) {
        aspect.setPerspective(jPerspective.getValue());
        labelPer.setText(Integer.toString(aspect.getPerspective()));
    }

    public void jHoriz_stateChanged(ChangeEvent e) {
        aspect.setHorizOffset(jHoriz.getValue());
        labelHoriz.setText(Integer.toString(aspect.getHorizOffset()));
    }

    public void jVert_stateChanged(ChangeEvent e) {
        aspect.setVertOffset(jVert.getValue());
        labelVert.setText(Integer.toString(aspect.getVertOffset()));
    }

    public void cbSmooth_actionPerformed(ActionEvent e) {
        chart.getGraphics3D().setSmoothingMode(cbSmooth.isSelected());
    }

    public void spOrthoAngle_stateChanged(ChangeEvent e) {
        if (! initDialog)  {
            aspect.setOrthoAngle(((Integer) spOrthoAngle.getValue()).intValue());
        }
    }

    public void sp3D_stateChanged(ChangeEvent e) {
        if (! initDialog)  {
            aspect.setChart3DPercent(((Integer) sp3D.getValue()).intValue());
        }
    }

    public void cbZoomText_actionPerformed(ActionEvent e) {
        aspect.setZoomText(cbZoomText.isSelected());
    }
}


class AspectEditor_cbZoomText_actionAdapter implements ActionListener {
    private AspectEditor adaptee;
    AspectEditor_cbZoomText_actionAdapter(AspectEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.cbZoomText_actionPerformed(e);
    }
}


final class AspectEditor_sp3D_changeAdapter implements ChangeListener {
    private AspectEditor adaptee;
    AspectEditor_sp3D_changeAdapter(AspectEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void stateChanged(ChangeEvent e) {
        adaptee.sp3D_stateChanged(e);
    }
}


final class AspectEditor_spOrthoAngle_changeAdapter implements ChangeListener {
    private AspectEditor adaptee;
    AspectEditor_spOrthoAngle_changeAdapter(AspectEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void stateChanged(ChangeEvent e) {
        adaptee.spOrthoAngle_stateChanged(e);
    }
}


final class AspectEditor_cbSmooth_actionAdapter implements ActionListener {
    private AspectEditor adaptee;
    AspectEditor_cbSmooth_actionAdapter(AspectEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.cbSmooth_actionPerformed(e);
    }
}


final class AspectEditor_jVert_changeAdapter implements ChangeListener {
    private AspectEditor adaptee;
    AspectEditor_jVert_changeAdapter(AspectEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void stateChanged(ChangeEvent e) {
        adaptee.jVert_stateChanged(e);
    }
}


final class AspectEditor_jHoriz_changeAdapter implements ChangeListener {
    private AspectEditor adaptee;
    AspectEditor_jHoriz_changeAdapter(AspectEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void stateChanged(ChangeEvent e) {
        adaptee.jHoriz_stateChanged(e);
    }
}


final class AspectEditor_jPerspective_changeAdapter implements ChangeListener {
    private AspectEditor adaptee;
    AspectEditor_jPerspective_changeAdapter(AspectEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void stateChanged(ChangeEvent e) {
        adaptee.jPerspective_stateChanged(e);
    }
}


final class AspectEditor_jElevation_changeAdapter implements ChangeListener {
    private AspectEditor adaptee;
    AspectEditor_jElevation_changeAdapter(AspectEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void stateChanged(ChangeEvent e) {
        adaptee.jElevation_stateChanged(e);
    }
}


final class AspectEditor_jSlider1_changeAdapter implements ChangeListener {
    private AspectEditor adaptee;
    AspectEditor_jSlider1_changeAdapter(AspectEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void stateChanged(ChangeEvent e) {
        adaptee.jSlider1_stateChanged(e);
    }
}


final class AspectEditor_jZoom_changeAdapter implements ChangeListener {
    private AspectEditor adaptee;
    AspectEditor_jZoom_changeAdapter(AspectEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void stateChanged(ChangeEvent e) {
        adaptee.jZoom_stateChanged(e);
    }
}


final class AspectEditor_cbOrtho_actionAdapter implements ActionListener {
    private AspectEditor adaptee;
    AspectEditor_cbOrtho_actionAdapter(AspectEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.cbOrtho_actionPerformed(e);
    }
}


final class AspectEditor_cbClip_actionAdapter implements ActionListener {
    private AspectEditor adaptee;
    AspectEditor_cbClip_actionAdapter(AspectEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.cbClip_actionPerformed(e);
    }
}


final class AspectEditor_jCheckBox1_actionAdapter implements ActionListener {
    private AspectEditor adaptee;
    AspectEditor_jCheckBox1_actionAdapter(AspectEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.jCheckBox1_actionPerformed(e);
    }
}

