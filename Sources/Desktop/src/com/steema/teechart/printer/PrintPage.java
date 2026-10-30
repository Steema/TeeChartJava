package com.steema.teechart.printer;

import com.steema.teechart.Rectangle;
import com.steema.teechart.drawing.Graphics3D;
import com.steema.teechart.drawing.Image;

/**
 *
 * <p>Title: PrintPage class</p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright (c) 2005-2019 by Steema Software SL. All Rights
 * Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 *
 *
 */
public class PrintPage {

    private Rectangle marginBounds=new Rectangle();
    private Rectangle pageBounds=new Rectangle();
    private Graphics3D graphics;

    public void drawImage(Image image, Rectangle dest, Rectangle source) {
        graphics.draw(dest, image, false);
    }

    public void drawString(String text,int x, int y) {
        graphics.textOut(x, y, text);
    }

    public Rectangle getMarginBounds() {
        return marginBounds;
    }

    public Rectangle getPageBounds() {
        return pageBounds;
    }
}

