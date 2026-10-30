package com.steema.teechart.exports;

import java.io.IOException;

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

    private int width;
    private int height;
    public String fileExtension = "";
    protected Object format = null; // ImageWriteParam 

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

    	chart.image(width, height).save(fileName, fileExtension, format);
    }
}

