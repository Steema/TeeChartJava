package com.steema.teechart.styles;

import com.steema.teechart.Dimension;
import com.steema.teechart.Rectangle; 
import com.steema.teechart.drawing.Point;

/**
 *
 * <p>Title: SeriesMarksPosition class</p>
 *
 * <p>Description: Series Mark Position</p>
 *
 * <p>Copyright (c) 2005-2019 by Steema Software SL. All Rights
 * Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 *
 */
public class SeriesMarksPosition {
    public boolean arrowFix;
    public Point arrowFrom=new Point();
    public Point arrowTo=new Point();
    public boolean custom;
    public int height;
    public Point leftTop=new Point();
    public int width;
    public Point midPoint=new Point();
    public boolean hasMid;

    /**
     * Returns the bounding rectangle of the indexed Mark.
     *
     * @return Rectangle
     */
    public Rectangle getBounds() {
        return new Rectangle(leftTop, new Dimension(width, height));
    }

    public void assign(SeriesMarksPosition source) {
        arrowFrom.x = source.arrowFrom.x;
        arrowFrom.y = source.arrowFrom.y;
        arrowTo.x = source.arrowTo.x;
        arrowTo.y = source.arrowTo.y;
        leftTop.x = source.leftTop.x;
        leftTop.y = source.leftTop.y;
        height = source.height;
        width = source.width;
        midPoint = new Point(source.midPoint.x,source.midPoint.y);
        hasMid = source.hasMid;
    }
    
    public void moveTo(int x, int y) {
      
        arrowTo.x = x;
        arrowTo.y = y;
        arrowFrom.x = arrowTo.x;
        arrowFrom.y = arrowTo.y;
        
        leftTop.x = arrowTo.x - (width / 2);
        leftTop.y = arrowTo.y - height + 1;
    }
}


