package com.steema.teechart.editors.tools;

import java.awt.Rectangle;
import com.steema.teechart.languages.Language;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.event.*;

import com.steema.teechart.editors.tools.ToolSeriesEditor;
import com.steema.teechart.tools.Tool;
import com.steema.teechart.tools.MarksTip;
import com.steema.teechart.tools.MarksTipMouseAction;
import com.steema.teechart.styles.MarksStyle;

/**
 * <p>Title: MArksTipEditor class</p>
 *
 * <p>Description: MarksTip Editor panel</p>
 *
 * <p>Copyright (c) 2004-2019 by Steema Software SL. All Rights Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 *
 */
public class MarksTipEditor extends ToolSeriesEditor {


    public MarksTipEditor() {
        try {
            jbInit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void setTool(Tool tool) {
        setting=true;
        marksTip=(MarksTip)tool;
        super.setTool(marksTip);
        cbSeries.insertItemAt(Language.getString("All"), 0);
        cbSeries.removeItemAt(1);           //fires ae, must removed here
        if (marksTip.getSeries()==null)
                cbSeries.setSelectedIndex(0);
        else
                cbSeries.setSelectedIndex(1+marksTip.getChart().getSeriesIndexOf(marksTip.getSeries()));

        cbStyle.setSelectedIndex(marksTip.getStyle().getValue());
        spDelay.setValue(new Integer(marksTip.getMouseDelay()));

        if (marksTip.getMouseAction()==MarksTipMouseAction.MOVE)
                cbAction.setSelectedIndex(0);
        else
                cbAction.setSelectedIndex(1);

        setting=false;
    }

    private void jbInit() throws Exception {
        this.setLayout(null);
        cbStyle.addItem(Language.getString("Value"));
        cbStyle.addItem(Language.getString("Percent"));
        cbStyle.addItem(Language.getString("Label"));
        cbStyle.addItem(Language.getString("Label_and_Percent"));
        cbStyle.addItem(Language.getString("Label_and_Value"));
        cbStyle.addItem(Language.getString("Legend"));
        cbStyle.addItem(Language.getString("Percent_and_Total"));
        cbStyle.addItem(Language.getString("Label_Percent_and"));
        cbStyle.addItem(Language.getString("XValue"));
        cbStyle.addItem(Language.getString("Xand_Y"));

        cbAction.addItem(Language.getString("Move"));
        cbAction.addItem(Language.getString("Click"));

        cbStyle.setBounds(new Rectangle(65, 51, 131, 23));
        cbStyle.addActionListener(new MarksTipEditor_cbStyle_actionAdapter(this));
        jLabel1.setDisplayedMnemonic(Language.getString("A").charAt(0));
        jLabel1.setHorizontalAlignment(SwingConstants.RIGHT);
        jLabel1.setLabelFor(cbAction);
        jLabel1.setText(Language.getString("Action"));
        jLabel1.setBounds(new Rectangle(1, 84, 57, 22));
        cbAction.setBounds(new Rectangle(65, 84, 131, 23));
        cbAction.addActionListener(new MarksTipEditor_cbAction_actionAdapter(this));
        jLabel2.setDisplayedMnemonic(Language.getString("D").charAt(0));
        jLabel2.setHorizontalAlignment(SwingConstants.RIGHT);
        jLabel2.setLabelFor(spDelay);
        jLabel2.setText(Language.getString("Delay"));
        jLabel2.setBounds(new Rectangle(1, 117, 57, 22));
        jLabel3.setDisplayedMnemonic(Language.getString("S").charAt(0));
        jLabel3.setHorizontalAlignment(SwingConstants.RIGHT);
        jLabel3.setLabelFor(cbStyle);
        jLabel3.setText(Language.getString("Style"));
        jLabel3.setBounds(new Rectangle(1, 51, 57, 22));
        spDelay.setBounds(new Rectangle(65, 117, 64, 23));
        spDelay.addChangeListener(new MarksTipEditor_spDelay_changeAdapter(this));
        this.add(jLabel3);
        this.add(cbAction);
        this.add(cbStyle);
        this.add(jLabel1);
        this.add(jLabel2);
        this.add(spDelay);
    }

    MarksTip marksTip;
    boolean setting;
    JComboBox cbStyle = new JComboBox();
    JLabel jLabel1 = new JLabel();
    JComboBox cbAction = new JComboBox();
    JLabel jLabel2 = new JLabel();
    JLabel jLabel3 = new JLabel();
    JSpinner spDelay = new JSpinner();

    public void spDelay_stateChanged(ChangeEvent e) {
        if (!setting)
            marksTip.setMouseDelay(((Integer)spDelay.getValue()).intValue());
    }

    public void cbStyle_actionPerformed(ActionEvent e) {
        if (!setting)
            marksTip.setStyle(MarksStyle.fromValue(cbStyle.getSelectedIndex()));
    }

    public void cbAction_actionPerformed(ActionEvent e) {
        if (!setting)
                if (cbAction.getSelectedIndex()==0)
                        marksTip.setMouseAction(MarksTipMouseAction.MOVE);
                else
                        marksTip.setMouseAction(MarksTipMouseAction.CLICK);
    }
}


class MarksTipEditor_cbAction_actionAdapter implements ActionListener {
    private MarksTipEditor adaptee;
    MarksTipEditor_cbAction_actionAdapter(MarksTipEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.cbAction_actionPerformed(e);
    }
}


class MarksTipEditor_cbStyle_actionAdapter implements ActionListener {
    private MarksTipEditor adaptee;
    MarksTipEditor_cbStyle_actionAdapter(MarksTipEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.cbStyle_actionPerformed(e);
    }
}


class MarksTipEditor_spDelay_changeAdapter implements ChangeListener {
    private MarksTipEditor adaptee;
    MarksTipEditor_spDelay_changeAdapter(MarksTipEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void stateChanged(ChangeEvent e) {
        adaptee.spDelay_stateChanged(e);
    }
}


