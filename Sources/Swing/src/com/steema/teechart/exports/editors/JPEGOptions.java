package com.steema.teechart.exports.editors;

import java.awt.Rectangle;
import javax.swing.*;
import com.steema.teechart.languages.Language;

/**
 * <p>Title: JPEG clas</p>
 *
 * <p>Description: JPEG export </p>
 *
 * <p>Copyright (c) 2004-2019 by Steema Software SL. All Rights Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 *
 */
public class JPEGOptions extends JPanel {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public JPEGOptions() {
        try {
            jbInit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        spQuality.setValue(new Integer(95));
    }

    public float getQuality() {
        Float ff=new Float(spQuality.getValue().toString());
        return ff.floatValue() / 100.0F;
    }

    private void jbInit() throws Exception {
        jLabel1.setHorizontalAlignment(SwingConstants.LEFT);
        jLabel1.setLabelFor(spQuality);
        jLabel1.setText(Language.getString("%_QUALITY"));
        jLabel1.setBounds(new Rectangle(90, 20, 70, 15));
        spQuality.setBounds(new Rectangle(28, 17, 53, 21));
        this.setLayout(null);
        this.add(spQuality);
        this.add(jLabel1);
    }

    JLabel jLabel1 = new JLabel();
    JSpinner spQuality = new JSpinner();
}

