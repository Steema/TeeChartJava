package com.steema.teechart.misc;

import java.util.Random;

import com.steema.teechart.Rectangle;
import com.steema.teechart.drawing.ChartFont;
import com.steema.teechart.drawing.Color;
import com.steema.teechart.drawing.IGraphics3D;
import com.steema.teechart.drawing.StringAlignment;



/**
 * <p>Title: TeeChart for Java</p>
 *
 * <p>Description: TeeChart for Java</p>
 *
 * <p>Copyright: (c) 2004-2019 by Steema Software SL. All Rights Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 *
 * @author db
 * @version 1.1
 */
public class ERMode {
    private ERMode() {
    }

    static public void ERPublish(IGraphics3D g, Rectangle r) {

        int red,green,blue;

        ChartFont f = g.getFont();
        f.setBold(true);
        Random rand = new Random();
        
        f.setSize(32);
        f.getOutline().setVisible(false); 
        f.getShadow().setVisible(false);

        red=rand.nextInt(255);
        green=rand.nextInt(255);
        blue=rand.nextInt(255);
  
        f.setColor(Color.fromArgb(0, red,green,blue));

        f.getBrush().setTransparency(70);
        
        com.steema.teechart.drawing.Point p = r.center();
        g.setTextAlign(StringAlignment.NEAR);
        g.rotateLabel(p.x - (g.textWidth(Utils.VersionMode) / 2) + 10,
                      r.getBottom() - 50, Utils.VersionMode, 45);
    }

    static public String ERDescription() {
        return "Evaluation";
    }
}
