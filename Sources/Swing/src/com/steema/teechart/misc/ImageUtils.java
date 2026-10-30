/**
 *
 * <p>Title: misc.ImageUtils class</p>
 *
 * <p>Description: Chart utility procedures</p>
 *
 * <p>Copyright (c) 2005-2019 by Steema Software SL. All Rights
 * Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 *
 */

package com.steema.teechart.misc;

import com.steema.teechart.IBaseChart;
import com.steema.teechart.drawing.ChartFont;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.PixelGrabber;
import java.awt.MediaTracker;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.geom.Rectangle2D;
import java.awt.image.MemoryImageSource;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.URL;

import javax.imageio.ImageIO;

/**
 *
 * @author tom
 */
public class ImageUtils {

    /**
     * Returns a newly created image using contents read from stream.
     * Might return a null image if the boolean flag cannot be read.
     *
     * @param stream ObjectInputStream
     * @param object 
     * @param object 
     * @return Image
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public static com.steema.teechart.drawing.Image readImage(ObjectInputStream stream, Object object) throws
            IOException, ClassNotFoundException {
        if (stream.readBoolean()) {
        	Dimension dim = ( Dimension ) stream.readObject( );
            
            int [ ] pix = ( int [ ] ) stream.readObject( );
            
            return toImage(Toolkit.getDefaultToolkit( ).createImage(
            		new MemoryImageSource( dim.width , dim.height , 
            				pix , 0 , dim.width ) ) );
        } else {
            return null;
        }
    }
    
    private static com.steema.teechart.drawing.Image tmpBmp;
    
    public static void measureImageString(Dimension dimension, final IBaseChart chart, final ChartFont f, final String text) { 
        final Font tmpFont = f.getDrawingFont(chart.getAspect().getFontZoom());

        if(tmpBmp == null) tmpBmp = new com.steema.teechart.drawing.Image(1, 1);

	Graphics2D tmpG = (Graphics2D)tmpBmp.getGraphics();
        
        if (chart.getGraphics3D().getSmoothingMode()) {
            tmpG.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
						RenderingHints.VALUE_ANTIALIAS_ON);
            tmpG.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
						RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
            tmpG.setRenderingHint(RenderingHints.KEY_RENDERING,
						RenderingHints.VALUE_RENDER_QUALITY);
	} else {
            tmpG.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
						RenderingHints.VALUE_ANTIALIAS_OFF);
            tmpG.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
						RenderingHints.VALUE_TEXT_ANTIALIAS_OFF);
            tmpG.setRenderingHint(RenderingHints.KEY_RENDERING,
						RenderingHints.VALUE_RENDER_SPEED);
	}

        tmpG.setFont(tmpFont);

	final Rectangle2D rect = tmpFont.getStringBounds(text, tmpG.getFontRenderContext());

	dimension.width = Utils.round(rect.getWidth() * 1.5);
	dimension.height = 1 + (int) rect.getHeight();
    }

    /**
     * Stores an image contents to stream.
     *
     * @param stream ObjectOutputStream
     * @param image Image
     */
    public static void writeImage(ObjectOutputStream stream, Image image)  throws
            IOException {
        if (image == null) {
            stream.writeBoolean(false);
        } else {
            stream.writeBoolean(true);

             Dimension tmpD = new Dimension(image.getWidth(null), image.getHeight(null));
             stream.writeObject( tmpD );
             stream.writeObject( getImagePixels(image) );
        }
    }

    private static Object getImagePixels(Image image) {
    	PixelGrabber grabber = new PixelGrabber( image , 0 , 0 , -1 , -1 , true );
    	return grabber.getPixels( );
	}

	public final static com.steema.teechart.drawing.Image toImage(Image image) {
    	if (image==null) return null;
    	else
    		if (image instanceof BufferedImage)
    			return new com.steema.teechart.drawing.Image((BufferedImage)image);
    		else
    			return new com.steema.teechart.drawing.Image(toBufferedImage(image));
	}
	
	/**
	 * Converts a given Image into a BufferedImage
	 *
	 * @param img The Image to be converted
	 * @return The converted BufferedImage
	 */
	public static BufferedImage toBufferedImage(Image img)
	{
	    if (img instanceof BufferedImage)
	    {
	        return (BufferedImage) img;
	    }

	    // Create a buffered image with transparency
	    BufferedImage bimage = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_ARGB);

	    // Draw the image on to the buffered image
	    Graphics2D bGr = bimage.createGraphics();
	    bGr.drawImage(img, 0, 0, null);
	    bGr.dispose();

	    // Return the buffered image
	    return bimage;
	}

	public static Image getImage(String imagefile, Object object) {
        Image image = Toolkit.getDefaultToolkit().createImage(imagefile);
        waitForImage(image, (Component)object);
        return image; 
    }

    public static Image getImage(URL imageURL, Component c) {
        Image image = Toolkit.getDefaultToolkit().createImage(imageURL);
        waitForImage(image, c);
        return image;
    }

    public static boolean waitForImage(Image image, Component c) {
    	
        MediaTracker tracker = new MediaTracker(c);
        tracker.addImage(image, 0);
        try {
            tracker.waitForID(0);
        } catch(InterruptedException ie) {
            System.out.println("loading interrupted");
        }
        return (!tracker.isErrorAny());
    }

	public static java.awt.Image getImage(URL resource) {
		return getImage(resource,null);
	}

	public static com.steema.teechart.drawing.Image getImage(String path) {
		return toImage(getImage(path,null));
	}

	public static java.awt.image.BufferedImage getImage(InputStream stream) {
        try {
			return ImageIO.read(stream);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public static com.steema.teechart.drawing.Image getImage(URL resource,
			Object c) {
		return toImage(getImage(resource,(Component)c));
	}

	public static BufferedImage bufferedFrom(
			com.steema.teechart.drawing.Image image) {
		return toBufferedImage(image);
	}

	public static com.steema.teechart.drawing.Image getImage(
			InputStream stream, Object control) {
		// TODO Auto-generated method stub
		return new com.steema.teechart.drawing.Image(getImage(stream));
	}
}
