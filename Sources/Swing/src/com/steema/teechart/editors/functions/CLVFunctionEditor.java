package com.steema.teechart.editors.functions;

import java.awt.Rectangle;
import java.awt.event.*;

import javax.swing.*;
import com.steema.teechart.functions.CLV;
import com.steema.teechart.functions.Function;
import com.steema.teechart.styles.Series;
import com.steema.teechart.styles.SeriesCollection;
import com.steema.teechart.languages.Language;

/**
 * <p>Title: CLVFunctionEditor class</p>
 *
 * <p>Description: CLV Function Editor panel</p>
 *
 * <p>Copyright (c) 2004-2019 by Steema Software SL. All Rights Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 *
 */
public class CLVFunctionEditor extends JPanel {


    public CLVFunctionEditor() {
        try {
            jbInit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void setFunction(Function f) {
        function = (CLV)f;
    }

    public CLVFunctionEditor(com.steema.teechart.styles.Series s, JList list) {
        this();
        int pos=-1;
        series = s;
        function=(com.steema.teechart.functions.CLV)series.getFunction();

        SeriesCollection tmpSeries = new SeriesCollection(series.getChart());
        tmpSeries.clear();
        for (int t = 0; t < series.getChart().getSeriesCount(); t++) {
            Series ser = series.getChart().getSeries(t);
            tmpSeries.internalAdd(ser);
        }

        for (int i=0;i<list.getModel().getSize();i++) {
            Series tmp=tmpSeries.withTitle(list.getModel().getElementAt(i).toString());
            try {
                series.checkOtherSeries(tmp);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            DefaultComboBoxModel model = new DefaultComboBoxModel();
            model.addElement(list.getModel().getElementAt(i).toString());
            cbVolume.setModel(model);
        }

        cbAccumulate.setSelected(function.getAccumulate());
        if (function.getVolume()!=null) {
            cbVolume.addItem(Language.getString("_none"));

            for (int i=0;i<cbVolume.getModel().getSize();i++) {
                if (!(cbVolume.getModel().getElementAt(i).toString()==function.getVolume().toString())) {
                    DefaultComboBoxModel model=new DefaultComboBoxModel();
                    model.addElement(function.getVolume().toString());
                    cbVolume.setModel(model);
                    pos=cbVolume.getModel().getSize()-1;
                } else {
                    pos = i;
                }
                cbVolume.setSelectedIndex(pos);
            }
        }
        else {
            cbVolume.addItem(Language.getString("_none"));
            pos=cbVolume.getModel().getSize()-1;
            cbVolume.setSelectedIndex(pos);
        }
    }

    private void jbInit() throws Exception {
        this.setLayout(null);
        cbAccumulate.setMnemonic(Language.getString("A").charAt(0));
        cbAccumulate.setText(Language.getString("Accumulate"));
        cbAccumulate.setBounds(new Rectangle(17, 61, 83, 23));
        cbAccumulate.addActionListener(new
                CLVFunctionEditor_cbAccumulate_actionAdapter(this));
        jLabel1.setDisplayedMnemonic(Language.getString("V").charAt(0));
        jLabel1.setLabelFor(cbVolume);
        jLabel1.setText(Language.getString("Volume_Series"));
        jLabel1.setBounds(new Rectangle(22, 16, 84, 15));
        cbVolume.setBounds(new Rectangle(21, 33, 121, 19));
        cbVolume.addActionListener(new CLVFunctionEditor_cbVolume_actionAdapter(this));
        this.add(jLabel1);
        this.add(cbVolume);
        this.add(cbAccumulate);
    }

    CLV function;
    public JComponent controlToEnable;
    private com.steema.teechart.styles.Series series;
    JCheckBox cbAccumulate = new JCheckBox();
    JLabel jLabel1 = new JLabel();
    JComboBox cbVolume = new JComboBox();

    private void Changed() {
            if (controlToEnable!=null) controlToEnable.setEnabled(true);
    }

    public void cbVolume_actionPerformed(ActionEvent e) {
        SeriesCollection tmpSeries = new SeriesCollection(series.getChart());
        tmpSeries.clear();
        for (int t = 0; t < series.getChart().getSeriesCount(); t++) {
            Series ser = series.getChart().getSeries(t);
            tmpSeries.internalAdd(ser);
        }

        Series tmp=tmpSeries.withTitle(cbVolume.getModel().getElementAt(cbVolume.getSelectedIndex()).toString());
        if(tmp!=null) {
            try {
                series.checkOtherSeries(tmp);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            function.setVolume(tmp);
        } else {
            function.setVolume(null);
        }

        Changed();
    }

    public void cbAccumulate_actionPerformed(ActionEvent e) {
        function.setAccumulate(cbAccumulate.isSelected());
        Changed();
    }

}


class CLVFunctionEditor_cbAccumulate_actionAdapter implements ActionListener {
    private CLVFunctionEditor adaptee;
    CLVFunctionEditor_cbAccumulate_actionAdapter(CLVFunctionEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.cbAccumulate_actionPerformed(e);
    }
}


class CLVFunctionEditor_cbVolume_actionAdapter implements ActionListener {
    private CLVFunctionEditor adaptee;
    CLVFunctionEditor_cbVolume_actionAdapter(CLVFunctionEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.cbVolume_actionPerformed(e);
    }
}


