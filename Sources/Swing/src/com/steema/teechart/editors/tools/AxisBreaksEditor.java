/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.steema.teechart.editors.tools;

import java.awt.Rectangle;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.event.*;

import com.steema.teechart.editors.BaseToolsEditor;
import com.steema.teechart.editors.BrushEditor;
import com.steema.teechart.languages.Language;
import com.steema.teechart.tools.AxisBreak;
import com.steema.teechart.tools.AxisBreaksTool;
import com.steema.teechart.tools.Tool;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JSpinner;
import javax.swing.border.Border;

/**
 *
 * @author admin
 */
public class AxisBreaksEditor extends AxisToolEdit {

    Tool atool;
    AxisBreaksTool axisbreaks;
    AxisBreak currentBreak;

    public void setTool(Tool tool) {

        setting = true;
        
        atool=tool;
        super.setTool(tool);
        axisbreaks=(AxisBreaksTool)tool;

        lbBreaks.setModel(new DefaultListModel());
        fillAndSet();

        lbBreaks.setCellRenderer(new javax.swing.DefaultListCellRenderer(){
             public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {

                JLabel jl = (JLabel)super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);

                jl.setText("Break "+String.valueOf(index));
                return jl;
            }
        });

        setting=false;
    }

    public AxisBreaksEditor() {
        try {
            jbInit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void jbInit() throws Exception {

        jLabelGap.setDisplayedMnemonic(Language.getString("B").charAt(0));
        jLabelGap.setHorizontalAlignment(SwingConstants.RIGHT);
        jLabelGap.setText(Language.getString("GapSize"));
        jLabelGap.setBounds(new Rectangle(95, 94, 49, 14));

        jLabelStartVal.setDisplayedMnemonic(Language.getString("S").charAt(0));
        jLabelStartVal.setHorizontalAlignment(SwingConstants.RIGHT);
        jLabelStartVal.setText(Language.getString("Start"));
        jLabelStartVal.setBounds(new Rectangle(95, 134, 49, 14));

        jLabelEndVal.setDisplayedMnemonic(Language.getString("E").charAt(0));
        jLabelEndVal.setHorizontalAlignment(SwingConstants.RIGHT);
        jLabelEndVal.setText(Language.getString("End"));
        jLabelEndVal.setBounds(new Rectangle(95, 174, 49, 14));

        bFill.setBounds(new Rectangle(133, 43, 75, 24));
        bFill.setMnemonic(Language.getString("F").charAt(0));
        bFill.setText(Language.getString("Fill"));
        bFill.addActionListener(new AxisBreaksEditor_bFill_actionAdapter(this));

        spGapSize.setBounds(new Rectangle(150, 90, 58, 20));
        spGapSize.addChangeListener(new AxisBreaksEditor_spGapSize_changeAdapter(this));
        tbStartValue.setBounds(new Rectangle(150, 130, 58, 20));
        tbStartValue.addKeyListener(new AxisBreaksEditor_tbStartValue_keyAdapter(this));
        tbEndValue.setBounds(new Rectangle(150, 170, 58, 20));
        tbEndValue.addKeyListener(new AxisBreaksEditor_tbEndValue_keyAdapter(this));

        lbBreaks.addListSelectionListener(new
                                         AxisBreaksEditor_tools_listSelectionAdapter(this));
        lbBreaks.setBounds(new Rectangle(10,90,80,150));

        this.add(jLabelGap);
        this.add(jLabelStartVal);
        this.add(jLabelEndVal);
        this.add(lbBreaks);
        this.add(tbStartValue);
        this.add(tbEndValue);
        this.add(spGapSize);
        this.add(spEndVal);
        this.add(bFill);
    }

    boolean setting;
    JLabel jLabelGap = new JLabel();
    JLabel jLabelStartVal = new JLabel();
    JLabel jLabelEndVal = new JLabel();
    JButton bFill = new JButton();
    JSpinner spGapSize = new JSpinner();
    JSpinner spEndVal = new JSpinner();
    JTextField tbStartValue = new JTextField();
    JTextField tbEndValue = new JTextField();
    JList lbBreaks = new JList();

    private void fillAndSet() {
        DefaultListModel tmp = (DefaultListModel) lbBreaks.getModel();
        tmp.clear();

        for (int t = 0; t < axisbreaks.getBreaks().size(); t++) {
            tmp.addElement(axisbreaks.getBreaks().get(t));
        }

        if (tmp.size() > 0) {
            lbBreaks.setSelectedIndex(0);
            currentBreak=axisbreaks.getBreaks().get(0);
        }

        tbStartValue.setText(String.valueOf(axisbreaks.getBreaks().get(0).getStartValue()));
        tbEndValue.setText(String.valueOf(axisbreaks.getBreaks().get(0).getEndValue()));
        spGapSize.setValue(new Integer(axisbreaks.getGapSize()));
    }

    public void tbStartValue_keyReleased(KeyEvent e) {
        double tmpval=0;
        tmpval=com.steema.teechart.misc.Utils.stringToDouble(tbStartValue.getText(),tmpval);
        if (tmpval<=currentBreak.getEndValue())
          currentBreak.setStartValue(tmpval);
        else
          currentBreak.setStartValue(currentBreak.getEndValue());
    }

    public void tbEndValue_keyReleased(KeyEvent e) {
        double tmpval=0;
        tmpval=com.steema.teechart.misc.Utils.stringToDouble(tbEndValue.getText(),tmpval);
        if (tmpval>=currentBreak.getStartValue())
          currentBreak.setEndValue(tmpval);
        else
          currentBreak.setEndValue(currentBreak.getStartValue());
    }

    public void spGapSize_stateChanged(ChangeEvent e) {

        if (!setting) {
            Object value = spGapSize.getValue();
            if(value instanceof Double) {

              axisbreaks.setGapSize(((Integer) value).intValue());
   
            }
            else if(value instanceof Integer)  {

               axisbreaks.setGapSize(((Integer) value).intValue());
            }
        }
    }
    
    private AxisBreak getAxisBreak() {
        return (AxisBreak) lbBreaks.getSelectedValue();
    }

    public void tools_valueChanged(ListSelectionEvent e) {
        currentBreak = getAxisBreak();
        if (currentBreak != null) {
            
            tbStartValue.setText(String.valueOf(currentBreak.getStartValue()));
            tbEndValue.setText(String.valueOf(currentBreak.getEndValue()));

        }
    }

    public void bFill_actionPerformed(ActionEvent e) {
        BrushEditor.edit(axisbreaks.getBrush());
    }
}

    final class AxisBreaksEditor_tools_listSelectionAdapter implements
        ListSelectionListener {
    private AxisBreaksEditor adaptee;
    AxisBreaksEditor_tools_listSelectionAdapter(AxisBreaksEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void valueChanged(ListSelectionEvent e) {
        adaptee.tools_valueChanged(e);
    }
   }

    class AxisBreaksEditor_spGapSize_changeAdapter implements ChangeListener {
        private AxisBreaksEditor adaptee;
        AxisBreaksEditor_spGapSize_changeAdapter(AxisBreaksEditor adaptee) {
            this.adaptee = adaptee;
        }

        public void stateChanged(ChangeEvent e) {
             adaptee.spGapSize_stateChanged(e);
        }
    }

    class AxisBreaksEditor_bFill_actionAdapter implements ActionListener {
        private AxisBreaksEditor adaptee;
        AxisBreaksEditor_bFill_actionAdapter(AxisBreaksEditor adaptee) {
            this.adaptee = adaptee;
        }

        public void actionPerformed(ActionEvent e) {
            adaptee.bFill_actionPerformed(e);
        }
    }

    class AxisBreaksEditor_tbStartValue_keyAdapter extends KeyAdapter {
        private AxisBreaksEditor adaptee;
        AxisBreaksEditor_tbStartValue_keyAdapter(AxisBreaksEditor adaptee) {
            this.adaptee = adaptee;
        }

        public void keyReleased(KeyEvent e) {
            adaptee.tbStartValue_keyReleased(e);
        }
    }

    class AxisBreaksEditor_tbEndValue_keyAdapter extends KeyAdapter {
        private AxisBreaksEditor adaptee;
        AxisBreaksEditor_tbEndValue_keyAdapter(AxisBreaksEditor adaptee) {
            this.adaptee = adaptee;
        }

        public void keyReleased(KeyEvent e) {
            adaptee.tbEndValue_keyReleased(e);
        }
    }

