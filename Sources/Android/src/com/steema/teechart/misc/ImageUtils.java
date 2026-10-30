package com.steema.teechart.misc;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.URL;

import com.steema.teechart.Dimension;
import com.steema.teechart.IBaseChart;
import com.steema.teechart.drawing.ChartFont;
import com.steema.teechart.drawing.Image;

public class ImageUtils {

	public static Image getImage(final String fileName) {
		return new Image(fileName);
	}

	public static Image getImage(final InputStream stream) {
		return new Image(stream);
	}

	public static Image readImage(final ObjectInputStream stream,
			final Object object) {
		boolean isNull = true;

		try {
			isNull = stream.readBoolean();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (isNull)
			return null;
		else
			return getImage(stream);
	}
	
	public static void measureImageString(Dimension dimension,
			final IBaseChart chart, final ChartFont f, final String text) {
			
	}

	public static void writeImage(ObjectOutputStream stream, Image image) {
		try {
			if (image == null) {
				stream.writeBoolean(false);
			} else {
				stream.writeBoolean(true);
				image.save(stream);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static Image getImage(final URL resource, final Object c) {
		return null;
		// return (Image) Image.createFromResourceStream(res, value, is,
		// srcName);
	}

	public static Image getImage(final URL resource) {
		return getImage(resource, null);
	}

	public static Image toImage(final Image image) {
		return image;
	}
}
