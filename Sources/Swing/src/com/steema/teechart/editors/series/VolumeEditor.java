package com.steema.teechart.editors.series;

import javax.swing.*;
import javax.swing.BorderFactory;

import java.awt.BorderLayout;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.steema.teechart.styles.Volume;
import com.steema.teechart.styles.Series;
import com.steema.teechart.editors.BaseSeriesEditor;
import com.steema.teechart.editors.ChartPenEditor;
import com.steema.teechart.languages.Language;

/**
 * <p>Title: TeeChart for Java</p>
 *
 * <p>Description: TeeChart for Java</p>
 *
 * <p>Copyright (c) 2004-2019 by Steema Software SL. All Rights
 * Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 *
 */
public class VolumeEditor extends BaseSeriesEditor {


    private ChartPenEditor penEditor = new ChartPenEditor();
    private Volume volume;
    JPanel jPanel1 = new JPanel();
    BorderLayout borderLayout1 = new BorderLayout();
    JCheckBox cbUseOrigin = new JCheckBox();
    JCheckBox cbColorEach = new JCheckBox();


    public VolumeEditor() {
        super();
        try {
            jbInit();

            penEditor.cbVisible.setVisible(false);
            jPanel1.add(penEditor.penPanel, BorderLayout.CENTER);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * setSeries
     *
     * @param series Series
     * @param tabs JTabbedPane
     * @todo Implement this com.steema.teechart.editors.BaseSeriesEditor
     *   method
     */
    public void setSeries(Series series, JTabbedPane tabs) {
        volume = (Volume)series;
        penEditor.refreshControls(volume.getLinePen());
    }

    private void jbInit() throws Exception {
        this.setLayout(null);
        jPanel1.setBorder(BorderFactory.createEtchedBorder());
        jPanel1.setBounds(new Rectangle(11, 13, 276, 142));
        jPanel1.setLayout(borderLayout1);
        cbUseOrigin.setMnemonic(Language.getString("U").charAt(0));
        cbUseOrigin.setText(Language.getString("Use_Origin"));
        cbUseOrigin.setBounds(new Rectangle(16, 168, 81, 23));
        cbUseOrigin.addActionListener(new
                                      VolumeEditor_cbUseOrigin_actionAdapter(this));
        cbColorEach.setMnemonic(Language.getString("E").charAt(0));
        cbColorEach.setText(Language.getString("Color_each"));
        cbColorEach.setBounds(new Rectangle(112, 168, 81, 23));
        cbColorEach.addActionListener(new
                                      VolumeEditor_cbColorEach_actionAdapter(this));
        this.add(jPanel1);
        this.add(cbUseOrigin);
        this.add(cbColorEach);

    }

    public void cbUseOrigin_actionPerformed(ActionEvent e) {
        volume.setUseOrigin(cbUseOrigin.isSelected());
    }

    public void cbColorEach_actionPerformed(ActionEvent e) {
        volume.setColorEach(cbColorEach.isSelected());
    }
}


final class VolumeEditor_cbColorEach_actionAdapter implements ActionListener {
    private VolumeEditor adaptee;
    VolumeEditor_cbColorEach_actionAdapter(VolumeEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.cbColorEach_actionPerformed(e);
    }
}


final class VolumeEditor_cbUseOrigin_actionAdapter implements ActionListener {
    private VolumeEditor adaptee;
    VolumeEditor_cbUseOrigin_actionAdapter(VolumeEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.cbUseOrigin_actionPerformed(e);
    }
}

