package com.steema.teechart.editors.functions;

import java.awt.Rectangle;
import java.awt.event.*;

import javax.swing.*;
import com.steema.teechart.functions.Function;
import com.steema.teechart.functions.OBV;
import com.steema.teechart.styles.Series;
import com.steema.teechart.languages.Language;

/**
 * <p>Title: OBVFunctionEditor class</p>
 *
 * <p>Description:  OBV Function Editor panel</p>
 *
 * <p>Copyright (c) 2004-2019 by Steema Software SL. All Rights Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 *
 */

public class OBVFunctionEditor extends JPanel {


    public OBVFunctionEditor() {
        try {
            jbInit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void setFunction(Function f) {
        function = (OBV)f;
    }

    public boolean contains(DefaultComboBoxModel model, Object o) {
        int size = model.getSize();
        for(int i = 0; i < size; i++) {
            Object obj = model.getElementAt(i);
            if(obj.equals(o)) {
                return true;
            }
        }
        return false;
    }

    public OBVFunctionEditor(Series s, JList list) {

        this();
        series = s;
        int pos;
        function = (OBV)s.getFunction();

        for (int i=0;i<list.getModel().getSize();++i)  {
// TODO     Series tmp = series.getChart().getSeries().getWithTitle(list.getModel().getElementAt(i);
// TODO     series.checkOtherSeries(tmp);
            cbVolume.addItem(String.valueOf(list.getModel().getElementAt(i)));
        }

            if(function.getVolume()!=null) {
                    cbVolume.addItem(Language.getString("_none"));
                    if (!contains(((DefaultComboBoxModel)cbVolume.getModel()),function.getVolume().toString())) {
                        cbVolume.addItem(String.valueOf(function.getVolume()));
                        pos = cbVolume.getModel().getSize();
                    }
                    else
                        pos = ((DefaultComboBoxModel)cbVolume.getModel()).getIndexOf(function.getVolume().toString());

                    cbVolume.setSelectedIndex(pos);
            }
            else {
                cbVolume.addItem(Language.getString("_none"));
                pos=cbVolume.getModel().getSize();
                cbVolume.setSelectedIndex(pos);
            }
    }

    private void jbInit() throws Exception {
        this.setLayout(null);
        cbVolume.setBounds(new Rectangle(21, 33, 121, 19));
        cbVolume.addActionListener(new OBVFunctionEditor_cbVolume_actionAdapter(this));
        jLabel1.setText(Language.getString("Volume_Series"));
        jLabel1.setBounds(new Rectangle(22, 16, 84, 15));
        this.add(cbVolume);
        this.add(jLabel1);
    }

    OBV function;
    public JComponent controlToEnable;
    private com.steema.teechart.styles.Series series;
    JComboBox cbVolume = new JComboBox();
    JLabel jLabel1 = new JLabel();

    private void Changed() {
            if (controlToEnable!=null) controlToEnable.setEnabled(true);
    }

    public void cbVolume_actionPerformed(ActionEvent e) {
/* TODO        Series tmp=series.getChart().getSeries().getWithTitle(String.valueOf(cbVolume.getModel().getElementAt(cbVolume.getSelectedIndex())));
        if(tmp!=null) {
                series.checkOtherSeries(tmp);
                function.setVolume(tmp);
        }
        else {
                function.setVolume(null);
        }
*/
        Changed();
    }
}


class OBVFunctionEditor_cbVolume_actionAdapter implements ActionListener {
    private OBVFunctionEditor adaptee;
    OBVFunctionEditor_cbVolume_actionAdapter(OBVFunctionEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.cbVolume_actionPerformed(e);
    }
}


