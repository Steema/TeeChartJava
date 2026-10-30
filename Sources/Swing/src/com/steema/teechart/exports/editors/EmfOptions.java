package com.steema.teechart.exports.editors;

import java.awt.Rectangle;
import javax.swing.*;

/**
 * <p>Title: EMFOptions class</p>
 *
 * <p>Description: EMF Options export</p>
 *
 * <p>Copyright (c) 2004-2019 by Steema Software SL. All Rights Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 *
 */

public class EmfOptions extends JPanel {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	JComboBox cbEmfType = new JComboBox();

    public EmfOptions() {
        try {
            jbInit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
// TODO       cbEmfType.Items.AddRange(Enum.GetNames(typeof(EmfType)));
        cbEmfType.addItem("EmfOnly");

    }

    private void jbInit() throws Exception {
        this.setLayout(null);
        cbEmfType.setBounds(new Rectangle(13, 12, 96, 22));
        this.add(cbEmfType);
    }

    public int getEMFType() {
            return cbEmfType.getSelectedIndex();
    }
}



