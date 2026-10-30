package com.steema.teechart.editors;

import java.awt.*;

import javax.swing.*;

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
public class ImagePanel extends JPanel {
    private Image img = null;

    public ImagePanel() {
        super();
    }

    public void setImage(Image value) {
        img = value;
    }

    public Image getImage() {
        return img;
    }


    public void paintComponent (Graphics g) {
        super.paintComponent (g);

        if(img != null) {
            g.drawImage(img, 0,0,getSize().width, getSize().height,0,0,img.getWidth(this),img.getHeight(this), this);
        }
    }

}

