package com.steema.teechart.exports.editors;

import java.awt.Rectangle;
import javax.swing.*;
import com.steema.teechart.languages.Language;

/**
 * <p>Title: PNG export</p>
 *
 * <p>Description: PNG export</p>
 *
 * <p>Copyright (c) 2004-2019 by Steema Software SL. All Rights Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 *
 */
public class PNGOptions extends JPanel {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public PNGOptions() {
        try {
            jbInit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void jbInit() throws Exception {
        jLabel1.setDisplayedMnemonic('C');
        jLabel1.setHorizontalAlignment(SwingConstants.RIGHT);
        jLabel1.setHorizontalTextPosition(SwingConstants.LEADING);
        jLabel1.setLabelFor(jSpinner1);
        jLabel1.setText(Language.getString("COMPRESSION_LEVEL"));
        jLabel1.setBounds(new Rectangle(28, 24, 102, 15));
        this.setLayout(null);
        cbGrayScale.setMnemonic('G');
        cbGrayScale.setText(Language.getString("Grayscale"));
        cbGrayScale.setBounds(new Rectangle(57, 52, 108, 22));
        jSpinner1.setBounds(new Rectangle(139, 20, 42, 23));
        this.add(jLabel1);
        this.add(jSpinner1);
        this.add(cbGrayScale);
        jSpinner1.setValue(new Integer(9));
    }

    JLabel jLabel1 = new JLabel();
    JCheckBox cbGrayScale = new JCheckBox();
    JSpinner jSpinner1 = new JSpinner();

    public int getCompression() {
            return (((Integer)jSpinner1.getValue()).intValue());
    }

    public boolean getGrayScale() {
        return cbGrayScale.isSelected();
    }
}

