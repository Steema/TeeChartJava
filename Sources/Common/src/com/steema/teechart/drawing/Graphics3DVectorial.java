package com.steema.teechart.drawing;

import com.steema.teechart.Dimension;
import com.steema.teechart.IBaseChart;
import com.steema.teechart.PointDouble;
import com.steema.teechart.misc.ImageUtils;
import java.io.OutputStream;
import java.io.IOException;

/**
 *
 * <p>Title: Graphics3DVectorial class</p>
 *
 * <p>Description: Abstract class for all vector image formats.<br>
 * Key elements are internal bitmap and UseBuffered = false</p>
 *
 * <p>Copyright (c) 2005-2019 by Steema Software SL. All Rights Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 *
 */
@SuppressWarnings("serial")
public abstract class Graphics3DVectorial extends Graphics3D {

    private OutputStream istream;

    public Graphics3DVectorial(IBaseChart c) {
        super(c);
    }

    public Graphics3DVectorial(OutputStream stream, IBaseChart c) {
        this(c);
        istream = stream;
        setUseBuffer(false);
        setGraphics(c.getParent().getGraphics());
    }

    private Object graphics;

    public Object getGraphics() {
        return graphics == null ? chart.getParent().getGraphics() : graphics;
    }

    public void setGraphics(Object value) {
    	graphics = value;
    }

    public String pointToStr(PointDouble point) {
        return Double.toString(point.x) + " , " + Double.toString(point.y);
    }
    
    public String pointToStr(double x, double y) {
        return Double.toString(x) + " , " + Double.toString(y);
    }

    public void measureString(Dimension dimension, final ChartFont f, final String text) {
       ImageUtils.measureImageString(dimension, getChart(), f, text);
    }

    /**
     * Adds line to stream
     *
     * @param text String text to be added to stream
     */
    protected void addToStream(final String text) {
        String linetext = text + "\n";
        try {
            istream.write(linetext.getBytes());
        } catch (IOException ex) {
        }
    }


	public void ellipseEnh(int x1, int y1, int x2, int y2) {
		// TODO Auto-generated method stub

	}


	protected void sphereEnh(int x1, int y1, int x2, int y2) {
		// TODO Auto-generated method stub

	}

}
