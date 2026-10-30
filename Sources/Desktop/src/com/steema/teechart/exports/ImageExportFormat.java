package com.steema.teechart.exports;

import java.awt.Image;
import java.awt.image.RenderedImage;

import java.util.Iterator;

import java.io.IOException;
import java.io.File;

import javax.imageio.ImageWriteParam;
import javax.imageio.stream.ImageOutputStream;
import javax.imageio.ImageWriter;
import javax.imageio.ImageIO;
import javax.imageio.IIOImage;

import com.steema.teechart.Rectangle;
import com.steema.teechart.TeeBase;
import com.steema.teechart.IBaseChart;

/**
 * <p>Title: TeeChart for Java</p>
 *
 * <p>Description: TeeChart for Java</p>
 *
 * <p>Copyright (c) 2004-2019 by Steema Software SL. All Rights Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 *
 */
public class ImageExportFormat extends TeeBase {
	
	private static final long serialVersionUID = 1L;

    private int width;
    private int height;
    public String fileExtension = "";
    protected ImageWriteParam format = null;

    public ImageExportFormat(IBaseChart chart) {
        super(chart);
        Rectangle r = chart.getChartBounds();
        width = r.width;
        height = r.height;

        /*
                 if ((height != 0) && (width != 0)) {
            chart.getExport().chartWidthHeightRatio = (double) width /
                    (double) height;
                 }
         */
    }
    /**
     * Returns the Height of the Exported image.
     *
     * @return int
     */
    public int getHeight() {
        return height;
    }
    /**
     * Returns the Width of the Exported image.
     *
     * @return int
     */
    public int getWidth() {
        return width;
    }
    /**
     * Sets the Width of the Exported image.
     *
     * @param value int
     */
    public void setWidth(int value) {
        width = value;
    }
    /**
     * Sets the Height of the Exported image.
     *
     * @param value int
     */
    public void setHeight(int value) {
        height = value;
    }
    /**
     * Saves image to File.
     *
     * @param fileName String
     * @throws IOException
     */
    public void save(String fileName) throws IOException {
        if (width <= 0) {
            width = 400;
        }

        if (height <= 0) {
            height = 300;
        }
        
        chart.image(width,height).save(fileName,fileExtension,format);
    }
    /**
     * Saves image to Stream.
     *
     * @param ios ImageOutputStream
     * @throws IOException
     */
    public void save(ImageOutputStream ios) throws IOException {
        if (width <= 0) {
            width = 400;
        }

        if (height <= 0) {
            height = 300;
        }
        
        //org.eclipse.swt.graphics.Image tmpData = chart.image(width, height).data;
        //java.awt.Image img = chart.image(width, height).convertToAWT(tmpData.getImageData());
        //java.awt.Image img = chart.image(width, height).AWTImage();
        
        //Swing is AWT but offers common code with SWT
        Image img = chart.image(width, height).AWTImage();
        
        RenderedImage rendImage = (RenderedImage) img;
        Iterator iter = ImageIO.getImageWritersByFormatName(fileExtension);

        ImageWriter writer = (ImageWriter) iter.next();

        writer.setOutput(ios);

        // Write the image
        writer.write(null, new IIOImage(rendImage, null, null), format);

        // Cleanup
        ios.flush();
        writer.dispose();
    }
}

