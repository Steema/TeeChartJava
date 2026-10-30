package com.steema.teechart.styles;


import com.steema.teechart.languages.Language;
import com.steema.teechart.drawing.IGraphics3D;
import com.steema.teechart.drawing.Point;
import com.steema.teechart.IBaseChart;
import com.steema.teechart.drawing.Color;

/**
 *
 * <p>Title: Bezier class</p>
 *
 * <p>Description: Bezier Series.</p>
 *
 * <p>Example:
 * <pre><font face="Courier" size="4">
 * series = new Bezier(myChart.getChart());
 * series.setStacked(CustomStack.NONE);
 * series.getLinePen().setColor(Color.RED);
 * series.fillSampleValues(30);
 * </font></pre></p>
 *
 * <p>Copyright (c) 2005-2019 by Steema Software SL. All Rights
 * Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 *
 */
public class Bezier extends Custom {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Bezier() {
        this((IBaseChart)null);
    }

    public Bezier(IBaseChart c) {
        super(c);
    }

    protected void draw() {
        // Limitation: points must be 4 + n*3
    	
    	final int count = getCount();
    	
        int tmp = count;
        while (((tmp - 4) % 3) != 0) {
            tmp++;
        }

        Point[] p = new Point[tmp];
        for (int t = 0; t < count; t++) {
            p[t] = new Point(calcXPos(t), calcYPos(t));
        }

        // Copy last point to remainder (see limitation above)
        for (int t = count; t < tmp; t++) {
            p[t] = p[count - 1];
        }

        IGraphics3D g = chart.getGraphics3D();
        g.getBrush().setVisible(false);

        if (!getLinePen().getVisible())
            g.getPen().setColor(Color.TRANSPARENT);
        else
            g.setPen(getLinePen());

        if (chart.getAspect().getView3D()) {
            g.drawBeziers(getMiddleZ(), p);
        } else {
            g.drawBeziers(p);
        }

        // Draw pointers...
        if (point.getVisible()) {
            for (int t = 0; t < count; t++) {
                drawPointer(p[t].x, p[t].y, getValueColor(t), t);
            }
        }
    }

    /**
     * Internal use.
     *
     * @param isEnabled boolean
     */
    public void prepareForGallery(boolean isEnabled) {
        super.prepareForGallery(isEnabled);
        fillSampleValues(7); // 4 + n*3  ( 4 fix points + 3 for each group )
        setColorEach(isEnabled);
        getPointer().setDraw3D(false);
    }

    public void setColor(Color c) {
        super.setColor(c);
        getLinePen().setColor(c);
    }

    /**
     * Gets descriptive text.
     *
     * @return String
     */
    public String getDescription() {
        return Language.getString("GalleryBezier");
    }
}

