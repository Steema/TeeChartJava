package com.steema.teechart.editors.tools;

import java.awt.Rectangle;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.event.*;

import com.steema.teechart.editors.tools.ToolSeriesEditor;
import com.steema.teechart.tools.Tool;
import com.steema.teechart.tools.SeriesAnimation;
import com.steema.teechart.languages.Language;

/**
 * <p>Title: SeriesAnimationEditor class</p>
 *
 * <p>Description: Serie sAnimation Editor panel</p>
 *
 * <p>Copyright (c) 2004-2019 by Steema Software SL. All Rights Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 *
 */
public class SeriesAnimationEditor extends ToolSeriesEditor {


    public SeriesAnimationEditor() {
        try {
            jbInit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void setTool(Tool tool) {
        seriesAnimation=(SeriesAnimation)tool;
        super.setTool(seriesAnimation);

        if (seriesAnimation!=null)
        {
                trackBar1.setValue(seriesAnimation.getSteps());
                jLabel4.setText(String.valueOf(seriesAnimation.getSteps()));
                cbStartMin.setSelected(seriesAnimation.getStartAtMin());
                tbStart.setText(String.valueOf(seriesAnimation.getStartValue()));
                tbStart.setEnabled(!cbStartMin.isSelected());
                jSpinner1.setValue(new Integer(seriesAnimation.getDrawEvery()));
                bExecute.setEnabled((seriesAnimation.getSeries()!=null));
        }
    }

    private void jbInit() throws Exception {
        this.setLayout(null);
        jLabel1.setDisplayedMnemonic(Language.getString("S").charAt(0));
        jLabel1.setHorizontalAlignment(SwingConstants.RIGHT);
        jLabel1.setText(Language.getString("Steps"));
        jLabel1.setBounds(new Rectangle(22, 59, 34, 15));
        jLabel2.setDisplayedMnemonic(Language.getString("V").charAt(0));
        jLabel2.setHorizontalAlignment(SwingConstants.RIGHT);
        jLabel2.setText(Language.getString("Start_value_"));
        jLabel2.setBounds(new Rectangle(13, 110, 65, 15));
        jLabel3.setDisplayedMnemonic(Language.getString("A").charAt(0));
        jLabel3.setHorizontalAlignment(SwingConstants.RIGHT);
        jLabel3.setText(Language.getString("Draw_every"));
        jLabel3.setBounds(new Rectangle(9, 139, 69, 15));
        tbStart.setBounds(new Rectangle(84, 108, 90, 20));
        tbStart.addActionListener(new
                                  SeriesAnimationEditor_tbStart_actionAdapter(this));
        bExecute.setBounds(new Rectangle(84, 164, 76, 23));
        bExecute.setMnemonic(Language.getString("E").charAt(0));
        bExecute.setText(Language.getString("Execute"));
        bExecute.addActionListener(new
                                   SeriesAnimationEditor_bExecute_actionAdapter(this));
        jSpinner1.setBounds(new Rectangle(84, 135, 56, 22));
        jSpinner1.addChangeListener(new
                SeriesAnimationEditor_jSpinner1_changeAdapter(this));
        cbStartMin.setMnemonic(Language.getString("M").charAt(0));
        cbStartMin.setText(Language.getString("Start_at_min_value"));
        cbStartMin.setBounds(new Rectangle(82, 81, 136, 23));
        cbStartMin.addActionListener(new
                SeriesAnimationEditor_cbStartMin_actionAdapter(this));
        trackBar1.setMaximum(1000);
        trackBar1.setMinimum(1);
        trackBar1.setBounds(new Rectangle(62, 55, 129, 22));
        trackBar1.addChangeListener(new
                SeriesAnimationEditor_trackBar1_changeAdapter(this));
        jLabel4.setDisplayedMnemonic(Language.getString("S").charAt(0));
        jLabel4.setHorizontalAlignment(SwingConstants.LEFT);
        jLabel4.setText("0");
        jLabel4.setBounds(new Rectangle(193, 59, 27, 15));
        this.add(tbStart);
        this.add(jLabel2);
        this.add(jLabel3);
        this.add(jSpinner1);
        this.add(bExecute);
        this.add(cbStartMin);
        this.add(jLabel4);
        this.add(jLabel1);
        this.add(trackBar1);
    }

    SeriesAnimation seriesAnimation;
    JLabel jLabel1 = new JLabel();
    JLabel jLabel2 = new JLabel();
    JLabel jLabel3 = new JLabel();
    JTextField tbStart = new JTextField();
    JButton bExecute = new JButton();
    JSpinner jSpinner1 = new JSpinner();
    JCheckBox cbStartMin = new JCheckBox();
    JSlider trackBar1 = new JSlider();
    JLabel jLabel4 = new JLabel();

    public void bExecute_actionPerformed(ActionEvent e) {
        bExecute.setEnabled(false);
        try
        {
           seriesAnimation.execute();
         } catch (IllegalAccessException ex) {
         } catch (InstantiationException ex) {
        }
        finally
        {
                bExecute.setEnabled(true);
        }
    }

    public void trackBar1_stateChanged(ChangeEvent e) {
        seriesAnimation.setSteps(trackBar1.getValue());
        jLabel4.setText(String.valueOf(seriesAnimation.getSteps()));
    }

    public void cbStartMin_actionPerformed(ActionEvent e) {
        seriesAnimation.setStartAtMin(cbStartMin.isSelected());
        tbStart.setEnabled(!cbStartMin.isSelected());
    }

    public void tbStart_actionPerformed(ActionEvent e) {
        double tmpval=0;
        tmpval=com.steema.teechart.misc.Utils.stringToDouble(tbStart.getText(),tmpval);
        seriesAnimation.setStartValue(tmpval);
    }

    public void jSpinner1_stateChanged(ChangeEvent e) {
        seriesAnimation.setDrawEvery(((Integer)jSpinner1.getValue()).intValue());
    }

    public void cbSeries_actionPerformed(ActionEvent e) {
        super.cbSeries_actionPerformed(e);
        bExecute.setEnabled((seriesAnimation.getSeries()!=null));

    }


 /*   private void cbStartMin_actionPerformed()
    {
        bExecute.setEnabled((seriesAnimation.getSeries()!=null));
    }*/
}


class SeriesAnimationEditor_jSpinner1_changeAdapter implements ChangeListener {
    private SeriesAnimationEditor adaptee;
    SeriesAnimationEditor_jSpinner1_changeAdapter(SeriesAnimationEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void stateChanged(ChangeEvent e) {
        adaptee.jSpinner1_stateChanged(e);
    }
}


class SeriesAnimationEditor_tbStart_actionAdapter implements ActionListener {
    private SeriesAnimationEditor adaptee;
    SeriesAnimationEditor_tbStart_actionAdapter(SeriesAnimationEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.tbStart_actionPerformed(e);
    }
}


class SeriesAnimationEditor_cbStartMin_actionAdapter implements ActionListener {
    private SeriesAnimationEditor adaptee;
    SeriesAnimationEditor_cbStartMin_actionAdapter(SeriesAnimationEditor
            adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.cbStartMin_actionPerformed(e);
    }
}


class SeriesAnimationEditor_trackBar1_changeAdapter implements ChangeListener {
    private SeriesAnimationEditor adaptee;
    SeriesAnimationEditor_trackBar1_changeAdapter(SeriesAnimationEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void stateChanged(ChangeEvent e) {
        adaptee.trackBar1_stateChanged(e);
    }
}


class SeriesAnimationEditor_bExecute_actionAdapter implements ActionListener {
    private SeriesAnimationEditor adaptee;
    SeriesAnimationEditor_bExecute_actionAdapter(SeriesAnimationEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.bExecute_actionPerformed(e);
    }
}


