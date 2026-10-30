package com.steema.teechart.exports.editors;

import java.awt.Rectangle;
import javax.swing.*;

/**
 * <p>Title: GIGOptions class</p>
 *
 * <p>Description: Gif export</p>
 *
 * <p>Copyright (c) 2004-2019 by Steema Software SL. All Rights Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 *
 */

public class GIFOptions extends JPanel {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public GIFOptions() {
        try {
            jbInit();
            Load();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void Load() {
        DefaultComboBoxModel model = new DefaultComboBoxModel();
        model.addElement("None");
        model.addElement("GrayScale");
        cbReduction.setModel(model);
        cbReduction.setSelectedIndex(0);
    }

    public int getColorReduction() {
        return cbReduction.getSelectedIndex();
    }

    private void jbInit() throws Exception {
        this.setLayout(null);
        jLabel1.setDisplayedMnemonic('R');
        jLabel1.setHorizontalAlignment(SwingConstants.RIGHT);
        jLabel1.setLabelFor(cbReduction);
        jLabel1.setText("Reduction:");
        jLabel1.setBounds(new Rectangle(5, 26, 62, 15));
        cbReduction.setBounds(new Rectangle(72, 22, 85, 23));
        this.add(cbReduction);
        this.add(jLabel1);
    }

    JLabel jLabel1 = new JLabel();
    JComboBox cbReduction = new JComboBox();
}

