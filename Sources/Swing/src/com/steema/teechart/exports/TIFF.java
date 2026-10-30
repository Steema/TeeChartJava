package com.steema.teechart.exports;

import java.awt.Rectangle;
import java.awt.Color;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.BevelBorder;
import javax.swing.border.TitledBorder;

/**
 * <p>Title: TIFF class</p>
 *
 * <p>Description: TIFF export</p>
 *
 * <p>Copyright (c) 2004-2019 by Steema Software SL. All Rights Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 *
 */
public class TIFF extends JPanel {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public TIFF() {
        try {
            jbInit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public boolean getCompressionLZW()
    {
        return radioButton1.isSelected();
    }

    private void jbInit() throws Exception {
        this.setLayout(null);
        jPanel1.setBorder(border4);
        jPanel1.setBounds(new Rectangle(17, 15, 195, 55));
        jPanel1.setLayout(null);
        radioButton1.setMnemonic('L');
        radioButton1.setSelected(true);
        radioButton1.setText("LZW");
        radioButton1.setBounds(new Rectangle(15, 20, 64, 23));
        radioButton2.setMnemonic('R');
        radioButton2.setText("RLE");
        radioButton2.setBounds(new Rectangle(104, 20, 65, 23));
        this.add(jPanel1);
        jPanel1.add(radioButton1);
        jPanel1.add(radioButton2);
    }

    JPanel jPanel1 = new JPanel();
    Border border1 = BorderFactory.createBevelBorder(BevelBorder.LOWERED,
            Color.white, Color.white, new Color(115, 114, 105),
            new Color(165, 163, 151));
    Border border2 = new TitledBorder(border1, "Compression:");
    JRadioButton radioButton1 = new JRadioButton();
    JRadioButton radioButton2 = new JRadioButton();
    Border border3 = BorderFactory.createBevelBorder(BevelBorder.LOWERED,
            Color.white, Color.white, new Color(115, 114, 105),
            new Color(165, 163, 151));
    Border border4 = new TitledBorder(border3, "Compression:");
}


