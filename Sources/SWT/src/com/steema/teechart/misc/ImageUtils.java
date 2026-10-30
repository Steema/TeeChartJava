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

import com.steema.teechart.Chart;
import com.steema.teechart.IBaseChart;
import com.steema.teechart.drawing.ChartFont;
import com.steema.teechart.drawing.Image;
import com.steema.teechart.swt.GDIFontObject;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.URL;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Device;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.widgets.Composite;

/**
 * 
 * @author tom
 */
public class ImageUtils {

	/**
	 * Returns a newly created image using contents read from stream. Might
	 * return a null image if the boolean flag cannot be read.
	 * 
	 * @param stream
	 *            ObjectInputStream
	 * @return Image
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	public static Image readImage(ObjectInputStream stream, Object object)
			throws IOException, ClassNotFoundException {
		if (stream.readBoolean()) {
			Dimension dim = (Dimension) stream.readObject();

			Image i = new Image(dim.width, dim.height,
					((Composite) object).getDisplay());
			i.data.getImageData().data = (byte[]) stream.readObject();
			return i;
		} else {
			return null;
		}
	}


	public static void measureImageString(Dimension dimension,
			final IBaseChart chart, final ChartFont f, final String text) {

		com.steema.teechart.drawing.Image tmpBmp = new com.steema.teechart.drawing.Image(1, 1, chart.getParent().getControl());

		GC tmpG = tmpBmp.getGraphics();

		if (chart.getGraphics3D().getTextSmooth())
			tmpG.setTextAntialias(SWT.ON);
		else
			tmpG.setTextAntialias(SWT.DEFAULT);

		if (chart.getGraphics3D().getSmoothingMode())
			tmpG.setAntialias(SWT.ON);

		else
			tmpG.setAntialias(SWT.DEFAULT);
		
		GDIFontObject fontObj = prepareFont(f, tmpG, ((Composite)chart.getParent().getControl()).getDisplay());
		final org.eclipse.swt.graphics.Point p = tmpG.stringExtent(text);
		dimension.width = p.x + 1;
		dimension.height = p.y + 1;
		
		if (fontObj.getColor() != null) fontObj.getColor().dispose();
		if (fontObj.getFont() != null) fontObj.getFont().dispose();
		tmpG.dispose();
	}

	private static Color color(java.awt.Color value, GC g, Device device) {
		if (!g.isDisposed())
			g.setAlpha(value.getAlpha());
		return new Color(device, value.getRed(), value.getGreen(),
				value.getBlue());
	}

	private static GDIFontObject prepareFont(ChartFont f, GC g, Device device) {

		int style = 0;

		if (f.getBold())
			style = style | SWT.BOLD;

		if (f.getItalic())
			style = style | SWT.ITALIC;
		
		GDIFontObject fontObj = new GDIFontObject(color(f.getBrush().getColor(), g, device),
				new org.eclipse.swt.graphics.Font(null, f.getName(), f.getSize() - 2, style));

		g.setForeground(fontObj.getColor());
		g.setFont(fontObj.getFont());
		
		return fontObj;
	}

	/**
	 * Stores an image contents to stream.
	 * 
	 * @param stream
	 *            ObjectOutputStream
	 * @param image
	 *            Image
	 */
	public static void writeImage(ObjectOutputStream stream, Image image)
			throws IOException {
		if (image == null) {
			stream.writeBoolean(false);
		} else {
			stream.writeBoolean(true);

			stream.writeObject(new Dimension(image.getWidth(null), image
					.getHeight(null)));
			stream.writeObject(image.data.getImageData().data);
		}
	}

	public static Image getImage(String imagefile, Object object) {
		return new Image(imagefile, ((Composite) object).getDisplay());
	}

	public static Image getImage(InputStream stream, Object c) {
		return new Image(stream, ((Composite) c).getDisplay());
	}

	public static Image getImage(URL resource, Object c) {
		try {
			return new Image(resource, ((Composite) c).getDisplay());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static BufferedImage bufferedFrom(Image image) {
		BufferedImage b = new BufferedImage(image.getWidth(null),
				image.getHeight(null), BufferedImage.TYPE_INT_BGR);
		// b.setData(...
		return b;
	}

	public static Image toImage(Image image) {
		return image;
	}
}
