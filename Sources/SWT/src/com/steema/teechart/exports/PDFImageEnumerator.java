package com.steema.teechart.exports;

import java.util.ArrayList;
import java.util.Iterator;

import com.steema.teechart.drawing.Image;
import com.steema.teechart.exports.PDFData.PDFImages;

/**
 *
 * <p>Title: PDFImageEnumerator class</p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright (c)
 * 2005-2013 by Steema Software SL. All Rights Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 *
 */
public class PDFImageEnumerator implements Iterator {

    private PDFImages images;
    private int m_index, m_width, m_height;
//    private ImageFormat m_imgformat;
    public ArrayList offsets = new ArrayList();
//    public Stream ImageStream = new MemoryStream();

    private int position = -1;

    public void reset() {
        position = -1;
    }

    public Object getCurrent() {
        return images.m_images.get(position);
    }

    public boolean hasNext() {
        return position < images.m_images.size();
    }

    public Object next() {
        if (hasNext()) {
            position++;
            return getCurrent();
        } else {
            return null;
        }
    }

    /** TODO throw UnsupportedOperationException */
    public void remove() {
    }

    public PDFImageEnumerator(PDFImages images,
                              Image image) {
        this.images = images;
        m_index = images.getCount();
//                m_imgformat = image.RawFormat;
        m_width = image.getWidth(null);
        m_height = image.getHeight(null);
//                image.Save(ImageStream, image.RawFormat);
    }

    public String getImageName() {
        return "/Im" + Integer.toString(m_index);
    }

    public int getImageWidth() {
        return m_width;
    }

    public int getImageHeight() {
        return m_height;
    }

//  public ImageFormat getImageFormat() {
//        return m_imgformat;
//    }
}

