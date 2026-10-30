package com.steema.teechart.editors.tools;

import java.awt.Rectangle;
import com.steema.teechart.languages.Language;
import java.awt.event.*;

import javax.swing.*;
import com.steema.teechart.tools.Tool;

// TODO import com.steema.teechart.tools.SeriesHotspot;

/**
 * <p>Title: HotSpotEditor class</p>
 *
 * <p>Description:  HotSpot Editor panel</p>
 *
 * <p>Copyright (c) 2004-2019 by Steema Software SL. All Rights Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 *
 */

public class HotSpotEditor extends ToolSeriesEditor {


    public HotSpotEditor() {
        try {
            jbInit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        cbStyle.addItem(Language.getString("Value"));
        cbStyle.addItem(Language.getString("Percent"));
        cbStyle.addItem(Language.getString("Label1"));
        cbStyle.addItem(Language.getString("Label_and_Percent"));
        cbStyle.addItem(Language.getString("Label_and_Value1"));
        cbStyle.addItem(Language.getString("Legend"));
        cbStyle.addItem(Language.getString("Percent_and_Total1"));
        cbStyle.addItem(Language.getString("Label_Percent_and1"));
        cbStyle.addItem(Language.getString("XValue1"));
        cbStyle.addItem(Language.getString("Xand_Y1"));

        cbAction.addItem(Language.getString("Mark"));
        cbAction.addItem(Language.getString("URL"));
        cbAction.addItem(Language.getString("Script"));

        cbHelperScript.addItem(Language.getString("None"));
        cbHelperScript.addItem(Language.getString("Annotations"));
    }

    public void setTool(Tool tool) {
        setting=true;
/* TODO
        seriesHotspot=(SeriesHotspot)tool;
        cbSeries.getModel().Items[0]=Language.getString("All");

        if (seriesHotspot.getSeries()==null)
                cbSeries.setSelectedIndex(0);
        else
                cbSeries.setSelectedIndex(1+seriesHotspot.getChart().getSeries().IndexOf(seriesHotspot.getSeries()));

        cbStyle.setSelectedIndex(((Integer)seriesHotspot.getStyle()).intValue());

        cbAction.setSelectedIndex=(((Integer)seriesHotspot.getMapAction());
        cbHelperScript.SelectedIndex=(int)tool.HelperScript;

        cbHelperScript.setEnabled(seriesHotspot.getMapAction()==MapAction.getScript() ? true : false);
*/
        setting=false;
    }

    private void jbInit() throws Exception {
        this.setLayout(null);
        jPanel1.setBounds(new Rectangle(0, 0, 400, 49));
        jLabel1.setDisplayedMnemonic(Language.getString("M").charAt(0));
        jLabel1.setHorizontalAlignment(SwingConstants.RIGHT);
        jLabel1.setText(Language.getString("Map_Action_"));
        jLabel1.setBounds(new Rectangle(22, 110, 81, 15));
        cbStyle.setBounds(new Rectangle(80, 64, 156, 23));
        cbStyle.addActionListener(new HotSpotEditor_cbStyle_actionAdapter(this));
        cbAction.setBounds(new Rectangle(115, 106, 121, 23));
        cbAction.addActionListener(new HotSpotEditor_cbAction_actionAdapter(this));
        cbHelperScript.setBounds(new Rectangle(115, 145, 121, 23));
        cbHelperScript.addActionListener(new
                HotSpotEditor_cbHelperScript_actionAdapter(this));
        jLabel2.setDisplayedMnemonic(Language.getString("H").charAt(0));
        jLabel2.setHorizontalAlignment(SwingConstants.RIGHT);
        jLabel2.setText(Language.getString("Helper_script_"));
        jLabel2.setBounds(new Rectangle(18, 149, 85, 15));
        jLabel3.setDisplayedMnemonic(Language.getString("T").charAt(0));
        jLabel3.setHorizontalAlignment(SwingConstants.RIGHT);
        jLabel3.setText(Language.getString("Style"));
        jLabel3.setBounds(new Rectangle(36, 68, 34, 15));
        this.add(jPanel1);
        this.add(jLabel1);
        this.add(cbAction);
        this.add(cbHelperScript);
        this.add(jLabel2);
        this.add(jLabel3);
        this.add(cbStyle);
    }

//  SeriesHotspot seriesHotspot;
    JPanel jPanel1 = new JPanel();
    JLabel jLabel1 = new JLabel();
    JComboBox cbStyle = new JComboBox();
    JComboBox cbAction = new JComboBox();
    JComboBox cbHelperScript = new JComboBox();
    JLabel jLabel2 = new JLabel();
    JLabel jLabel3 = new JLabel();

    public void cbStyle_actionPerformed(ActionEvent e) {
/*        if (!setting)
          seriesHotspot.setStyle((MarksStyles)cbStyle.getSelectedIndex());
*/
    }

    public void cbAction_actionPerformed(ActionEvent e) {
/*        if (!setting)
                seriesHotspot.setMapAction((MapAction)cbAction.getSelectedIndex());

        cbHelperScript.setEnabled(seriesHotspot.getMapAction()==MapAction.getScript() ? true : false);
*/
    }

    public void cbHelperScript_actionPerformed(ActionEvent e) {
/*        if (!setting)
                seriesHotspot.setHelperScript((HotspotHelperScripts)cbHelperScript.getSelectedIndex());
*/
    }
}


class HotSpotEditor_cbHelperScript_actionAdapter implements ActionListener {
    private HotSpotEditor adaptee;
    HotSpotEditor_cbHelperScript_actionAdapter(HotSpotEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.cbHelperScript_actionPerformed(e);
    }
}


class HotSpotEditor_cbAction_actionAdapter implements ActionListener {
    private HotSpotEditor adaptee;
    HotSpotEditor_cbAction_actionAdapter(HotSpotEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.cbAction_actionPerformed(e);
    }
}


class HotSpotEditor_cbStyle_actionAdapter implements ActionListener {
    private HotSpotEditor adaptee;
    HotSpotEditor_cbStyle_actionAdapter(HotSpotEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.cbStyle_actionPerformed(e);
    }
}


