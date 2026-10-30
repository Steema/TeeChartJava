package com.steema.teechart.styles;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.view.View;

import com.steema.teechart.Rectangle;
import com.steema.teechart.IBaseChart;

import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
import com.steema.teechart.TChart;
import com.steema.teechart.drawing.Graphics3D;
import com.steema.teechart.drawing.Image;
import com.steema.teechart.misc.ImageUtils;

import teechart.android.R;

/**
 *
 * <p>Title: ImageBar class</p>
 *
 * <p>Description: ImageBar Series.</p>
 *
 * <p>Example:
 * <pre><font face="Courier" size="4">
 * series = new ImageBar(myChart.getChart());
 * series.setColor(Color.RED);
 * series.fillSampleValues(6);
 * series.getMarks().setArrowLength(20);
 * series.getMarks().getCallout().getBrush().setColor(Color.BLACK);
 * series.getMarks().getCallout().setLength(20);
 * series.getMarks().setVisible(true);
 * series.setImageTiled(true);
 * </font></pre></p>
 *
 * <p>Copyright (c) 2005-2019 by Steema Software SL. All Rights Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 *
 */
public class ImageBar extends Bar {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	transient private Image image;

    private boolean imageTiled = false;

    public ImageBar() {
        this(null);
    }

    public ImageBar(IBaseChart c) {
        super(c);
        
        setDefaultImage();
    }

    private void setDefaultImage() {
    	if (chart != null) {
            Context c = ((View)((TChart)chart.getParent()).getParent()).getContext();
            image = new Image(BitmapFactory.decodeResource(c.getResources(), R.mipmap.dollar));
        }
	}

	public Image getImage() {
        return image;
    }

    public void setImage(Image value) {
        image = value;
        this.repaint();
    }

    /**
     * When true, ImageBar's image is tiled across Bars.
     * Default behavior is for the image to stretch. <br>
     * Default value: false
     *
     * @return boolean
     */
    public boolean getImageTiled() {
        return imageTiled;
    }

    public void setImageTiled(boolean value) {
        imageTiled = setBooleanProperty(imageTiled, value);
    }

     
    public void setChart(IBaseChart c) {
    	super.setChart(c);
    	
    	if (image == null)
    		setDefaultImage();
    }
    
    private void drawTiledImage(Image aImage, Rectangle r, boolean startFromTop) {
        Rectangle tmpRect;

        int tmpWidth = -1;
        int tmpHeight = -1;

        while (tmpWidth == -1 || tmpHeight == -1) {
            tmpWidth = aImage.getWidth(null);
            tmpHeight = aImage.getHeight(null);
        }

        if ((tmpWidth > 0) && (tmpHeight > 0)) {
            chart.getGraphics3D().clipRectangle(r);

            int RectW = r.width;
            int RectH = r.height;
            int tmpY = 0;

            while (tmpY < RectH) {
                int tmpX = 0;

                while (tmpX < RectW) {

                    if (startFromTop) {
                        tmpRect = Rectangle.fromLTRB(r.x, r.y + tmpY,
                                r.getRight(),
                                r.y + tmpY + tmpHeight);
                    } else {
                        tmpRect = Rectangle.fromLTRB(r.x,
                                r.getBottom() - tmpY - tmpHeight, r.getRight(),
                                r.getBottom() - tmpY);
                    }

                    chart.getGraphics3D().draw(tmpRect, image, false);
                    tmpX += tmpWidth;
                }
                tmpY += tmpHeight;
            }

            chart.getGraphics3D().unClip();
        }
    }

    public void prepareForGallery(boolean isEnabled) {
        super.prepareForGallery(isEnabled);
        fillSampleValues(2);
        chart.getAspect().setOrthogonal(true);
    }

    /**
     * Internal use. Draws Bar
     *
     * @param barIndex int
     * @param startPos int
     * @param endPos int
     */
    public void drawBar(int barIndex, int startPos, int endPos) {

        /* first thing to dois to call the inherited DrawBar method of BarSeries */
        super.drawBar(barIndex, startPos, endPos);

        if (image != null) {
            /* <-- if non empty image... */

            /* Calculate the exact rectangle, removing borders */
            Rectangle r = getBarBounds();

            int tmp;

            if (r.getBottom() < r.y) {
                tmp = r.y;
                r.y = r.getBottom();
                r.height = r.y - tmp;
            }

            if (getPen().getVisible()) {
                tmp = getPen().getWidth();
                if ((tmp > 1) && ((tmp % 2) == 0)) {
                    tmp--;
                }
                r.x += tmp;
                r.y += tmp;
                if (!chart.getAspect().getView3D()) {
                    r.width--;
                    r.height--;
                }
            }

            boolean tmp3D = chart.getAspect().getView3D() && (! chart.getAspect().getOrthogonal());

            if (! tmp3D)
               r = chart.getGraphics3D().calcRect3D(r, getStartZ());
            else
            {
              if (! Graphics3D.cull(chart.getGraphics3D().fourPointsFromRect(r, getStartZ()))) 
            		  return ;
            }
            

            /* Draw the image */
            if (imageTiled) {
                /* tiled */
                drawTiledImage(image, r,
                               getBarBounds().getBottom() <
                               getBarBounds().getTop());
            } else {
                /* stretched */
                chart.getGraphics3D().draw(r, image, false);
            }
        }
    }

    // Serialization methods to store/load Image contents

    private void writeObject(ObjectOutputStream stream) throws java.io.
            IOException {
        stream.defaultWriteObject();
        ImageUtils.writeImage(stream, image);
    }

    private void readObject(ObjectInputStream stream) throws java.io.
            IOException, ClassNotFoundException {
        stream.defaultReadObject();
        Image tmp = ImageUtils.readImage(stream,chart.getParent().getControl());
        if (tmp != null) {
            image = tmp;
        }
    }
}

