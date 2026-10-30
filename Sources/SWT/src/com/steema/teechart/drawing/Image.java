package com.steema.teechart.drawing;

import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.DirectColorModel;
import java.awt.image.IndexColorModel;
import java.awt.image.RenderedImage;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Iterator;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Device;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.ImageLoader;
import org.eclipse.swt.graphics.PaletteData;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.widgets.Composite;

import com.steema.teechart.misc.ImageUtils;

public final class Image {

	public org.eclipse.swt.graphics.Image data;

	public Image(int width, int height, final Object object) {
		if (data !=null) data.dispose();
		data=new org.eclipse.swt.graphics.Image(((Composite)object).getDisplay(), width,height);
	}

	public Image(int width, int height, final Device device) {
		if (data !=null) data.dispose();
		data=new org.eclipse.swt.graphics.Image(device, width,height);
	}

	public Image(final String imagefile, final Device device) {
		if (data !=null) data.dispose();
		data=new org.eclipse.swt.graphics.Image(device,imagefile);
	}

	public Image(URL imageURL, Device device) throws IOException {
		if (data !=null) data.dispose();
		data=new org.eclipse.swt.graphics.Image(device,imageURL.openStream());
	}

	public Image(final InputStream stream, final Device device) {
		if (data !=null) data.dispose();
		data = new org.eclipse.swt.graphics.Image(device, stream);
	}

	public int getWidth(final Object object) {
		return data.getBounds().width;
	}

	public int getHeight(final Object object) {
		return data.getBounds().height;
	}

	public int getRGB(int x, int y) {
		return data.getImageData().getPixel(x, y);
	}
	
	public GC getGraphics() {
		return new GC(data);
	}

	public void setRGB(int x, int y, int rgb) {
		data.getImageData().setPixel(x, y, rgb);
	}

	public boolean hasAlpha() {
		return data.getImageData().getTransparencyType()!=SWT.TRANSPARENCY_NONE;
	}
	

	public void save(final String fileName, final String fileExtension, final ImageWriteParam format) throws IOException {
		ImageLoader imageLoader = new ImageLoader();
		ImageData imageData = data.getImageData();
		imageLoader.data = new ImageData[] {imageData};
		if(fileExtension == "jpg")
			imageLoader.save(fileName, SWT.IMAGE_JPEG);
		else if(fileExtension == "bmp")
			imageLoader.save(fileName, SWT.IMAGE_BMP);
		else if(fileExtension == "gif")
			imageLoader.save(fileName, SWT.IMAGE_GIF);
		else if(fileExtension == "png")
			imageLoader.save(fileName, SWT.IMAGE_PNG);

    }
	
	public BufferedImage convertToAWT(ImageData data) {
		    ColorModel colorModel = null;
		    PaletteData palette = data.palette;
		    if (palette.isDirect) {
		      colorModel = new DirectColorModel(data.depth, palette.redMask,
		          palette.greenMask, palette.blueMask);
		      BufferedImage bufferedImage = new BufferedImage(colorModel,
		          colorModel.createCompatibleWritableRaster(data.width,
		              data.height), false, null);
		      WritableRaster raster = bufferedImage.getRaster();
		      int[] pixelArray = new int[3];
		      for (int y = 0; y < data.height; y++) {
		        for (int x = 0; x < data.width; x++) {
		          int pixel = data.getPixel(x, y);
		          RGB rgb = palette.getRGB(pixel);
		          pixelArray[0] = rgb.red;
		          pixelArray[1] = rgb.green;
		          pixelArray[2] = rgb.blue;
		          raster.setPixels(x, y, 1, 1, pixelArray);
		        }
		      }
		      return bufferedImage;
		    } else {
		      RGB[] rgbs = palette.getRGBs();
		      byte[] red = new byte[rgbs.length];
		      byte[] green = new byte[rgbs.length];
		      byte[] blue = new byte[rgbs.length];
		      for (int i = 0; i < rgbs.length; i++) {
		        RGB rgb = rgbs[i];
		        red[i] = (byte) rgb.red;
		        green[i] = (byte) rgb.green;
		        blue[i] = (byte) rgb.blue;
		      }
		      if (data.transparentPixel != -1) {
		        colorModel = new IndexColorModel(data.depth, rgbs.length, red,
		            green, blue, data.transparentPixel);
		      } else {
		        colorModel = new IndexColorModel(data.depth, rgbs.length, red,
		            green, blue);
		      }
		      BufferedImage bufferedImage = new BufferedImage(colorModel,
		          colorModel.createCompatibleWritableRaster(data.width,
		              data.height), false, null);
		      WritableRaster raster = bufferedImage.getRaster();
		      int[] pixelArray = new int[1];
		      for (int y = 0; y < data.height; y++) {
		        for (int x = 0; x < data.width; x++) {
		          int pixel = data.getPixel(x, y);
		          pixelArray[0] = pixel;
		          raster.setPixel(x, y, pixelArray);
		        }
		      }
		      return bufferedImage;
		    }
		  }

	public BufferedImage grayBuffered() {
		// TODO Auto-generated method stub
		org.eclipse.swt.graphics.Image greyImage = new org.eclipse.swt.graphics.Image(data.getDevice(), data, SWT.IMAGE_GRAY);
		return convertToAWT(greyImage.getImageData());
	}
	
	public java.awt.Image AWTImage()
	{
 	  return convertToAWT(data.getImageData());
	}
}
