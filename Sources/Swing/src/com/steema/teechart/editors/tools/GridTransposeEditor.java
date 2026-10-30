package com.steema.teechart.editors.tools;

import java.awt.Rectangle;
import java.awt.event.*;
import javax.swing.*;

import com.steema.teechart.tools.Tool;
import com.steema.teechart.tools.GridTranspose;
import com.steema.teechart.styles.Custom3DGrid;
import com.steema.teechart.editors.BaseToolsEditor;
import com.steema.teechart.languages.Language;


/**
 * <p>Title: GridTransposeEditor class</p>
 *
 * <p>Description: GridTranspose Editor panel</p>
 *
 * <p>Copyright (c) 2004-2019 by Steema Software SL. All Rights Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 *
 */
public class GridTransposeEditor extends BaseToolsEditor {


    public GridTransposeEditor() {
        try {
            jbInit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void setTool(Tool tool) {
        gridTranspose=(GridTranspose)tool;
        Load();
    }

    private void Load() {
         if ((gridTranspose!=null) && (gridTranspose.getChart()!=null))
         {
           cbSeries.removeAllItems();
           for (int i=0;i<gridTranspose.getChart().getSeriesCount();++i)
                if (gridTranspose.getChart().getSeries(i) instanceof Custom3DGrid)
                   cbSeries.addItem(gridTranspose.getChart().getSeries(i));

           if (gridTranspose.getSeries()!=null)
             cbSeries.setSelectedItem(gridTranspose.getSeries());
           else if (cbSeries.getItemCount()>0)
             cbSeries.setSelectedIndex(0);
           bTranspose.setEnabled(cbSeries.getSelectedItem()!=null);
         }
    }

    private void jbInit() throws Exception {
        this.setLayout(null);
        cbSeries.setBounds(new Rectangle(21, 33, 124, 23));
        cbSeries.addActionListener(new
                                   GridTransposeEditor_cbSeries_actionAdapter(this));
        jLabel1.setDisplayedMnemonic(Language.getString("S").charAt(0));
        jLabel1.setHorizontalAlignment(SwingConstants.LEFT);
        jLabel1.setText(Language.getString("Grid_3D_Series_"));
        jLabel1.setBounds(new Rectangle(22, 13, 100, 15));
        bTranspose.setBounds(new Rectangle(21, 63, 127, 25));
        bTranspose.setMnemonic(Language.getString("T").charAt(0));
        bTranspose.setText(Language.getString("Transpose_now"));
        bTranspose.addActionListener(new
                GridTransposeEditor_bTranspose_actionAdapter(this));
        this.add(jLabel1);
        this.add(cbSeries);
        this.add(bTranspose);
    }

    GridTranspose gridTranspose;
    JComboBox cbSeries = new JComboBox();
    JLabel jLabel1 = new JLabel();
    JButton bTranspose = new JButton();

    public void bTranspose_actionPerformed(ActionEvent e) {
        gridTranspose.transpose();
    }

    public void cbSeries_actionPerformed(ActionEvent e) {

        gridTranspose.setSeries((Custom3DGrid)cbSeries.getSelectedItem());
        bTranspose.setEnabled(true);
    }
}


class GridTransposeEditor_cbSeries_actionAdapter implements ActionListener {
    private GridTransposeEditor adaptee;
    GridTransposeEditor_cbSeries_actionAdapter(GridTransposeEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.cbSeries_actionPerformed(e);
    }
}


class GridTransposeEditor_bTranspose_actionAdapter implements ActionListener {
    private GridTransposeEditor adaptee;
    GridTransposeEditor_bTranspose_actionAdapter(GridTransposeEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.bTranspose_actionPerformed(e);
    }
}


