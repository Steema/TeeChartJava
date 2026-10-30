package com.steema.teechart.drawing;

import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageWriteParam;

import com.steema.teechart.misc.ImageUtils;
import java.awt.image.RenderedImage;
import java.io.File;
import java.util.Iterator;
import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;

public final class Image extends BufferedImage {

	public Image(int width, int height, int imageType) {
		super(width, height, imageType);
	}

	public Image(int width, int height) {
		super(width,height,BufferedImage.TYPE_3BYTE_BGR);
	}

	public Image(BufferedImage image) {
		super(image.getWidth(),image.getHeight(),image.getType());
		setData(image.getData());
	}

	public Image(int width, int height, Object control) {
		this(width, height);
	}

	public boolean hasAlpha() {
		return getType()==BufferedImage.TYPE_4BYTE_ABGR;
	}

	public void save(String fileName, String fileExtension,
			ImageWriteParam format) throws IOException {

            File outfile = new File(fileName);
            ImageOutputStream ios = ImageIO.createImageOutputStream(outfile);
     
            RenderedImage rendImage = (RenderedImage)this;
            Iterator iter = ImageIO.getImageWritersByFormatName(fileExtension);

            ImageWriter writer = (ImageWriter) iter.next();

            writer.setOutput(ios);

            // Write the image
            writer.write(null, new IIOImage(rendImage, null, null), format);

            // Cleanup
            ios.flush();
            writer.dispose();
            
            ios.close();
	}

	public java.awt.Image grayBuffered() {
        BufferedImage BufImage = new BufferedImage(
                getWidth(null),getHeight(null),
                BufferedImage.TYPE_USHORT_GRAY);

        java.awt.image.ColorConvertOp colorConvert = new
                java.awt.image.
                ColorConvertOp(
                        java.awt.color.ColorSpace.
                        getInstance(java.awt.color.
                ColorSpace.
                CS_GRAY), null);

        colorConvert.filter(ImageUtils.bufferedFrom(this), BufImage);
        
        return BufImage;
	}

        public java.awt.Image AWTImage()
	{
 	  return this;
	}
}
