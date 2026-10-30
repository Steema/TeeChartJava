package com.steema.teechart.editors.tools;

import java.awt.Rectangle;
import com.steema.teechart.languages.Language;
import java.awt.event.*;

import javax.swing.*;

import com.steema.teechart.editors.tools.ToolSeriesEditor;
import com.steema.teechart.tools.Tool;
import com.steema.teechart.tools.DragMarks;

/**
 * <p>Title: DragMarksEditor class</p>
 *
 * <p>Description:  Drag Marks Editor panel</p>
 *
 * <p>Copyright (c) 2004-2019 by Steema Software SL. All Rights Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 *
 */
public class DragMarksEditor extends ToolSeriesEditor {


    public DragMarksEditor() {
        try {
            jbInit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void setTool(Tool tool) {
        dragMarks=(DragMarks)tool;
        super.setTool(dragMarks);
        cbSeries.insertItemAt(Language.getString("All"),0);
        cbSeries.removeItemAt(1);           //fires ae, must removed here
        //Select something if nothing is selected
        if(cbSeries.getSelectedIndex() == -1)
        {
            if (cbSeries.getItemCount()>0)
                cbSeries.setSelectedIndex(0);
        }
    }

    private void jbInit() throws Exception {
        this.setLayout(null);
        bReset.setBounds(new Rectangle(39, 47, 142, 24));
        bReset.setToolTipText("");
        bReset.setMnemonic(Language.getString("R").charAt(0));
        bReset.setText(Language.getString("Reset_positions"));
        bReset.addActionListener(new DragMarksEditor_bReset_actionAdapter(this));
        this.add(bReset);
    }

    DragMarks dragMarks;
    JButton bReset = new JButton();

    public void bReset_actionPerformed(ActionEvent e) {
        if (dragMarks.getSeries()!=null)
            dragMarks.getSeries().getMarks().resetPositions();
        else
            for (int i=0;i<dragMarks.getChart().getSeriesCount();++i)
                dragMarks.getChart().getSeries(i).getMarks().resetPositions();
    }
}


class DragMarksEditor_bReset_actionAdapter implements ActionListener {
    private DragMarksEditor adaptee;
    DragMarksEditor_bReset_actionAdapter(DragMarksEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.bReset_actionPerformed(e);
    }
}


