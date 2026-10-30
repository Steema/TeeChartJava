package com.steema.teechart.exports;

import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;

import com.steema.teechart.drawing.Image;
import com.steema.teechart.misc.ImageUtils;
import com.steema.teechart.Dimension;
import com.steema.teechart.IBaseChart;
import com.steema.teechart.TeeBase;

/**
 * <p>Title: TeeChart for Java</p>
 *
 * <p>Description: TeeChart for Java</p>
 *
 * <p>Example:
 * <pre><font face="Courier" size="4">
 * myChart.getExport().getImage().getBMP().save(tmpName);
 * </font></pre></p>
 *
 * <p>Copyright  (c) 2004-2019 by Steema Software SL. All Rights Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 *
 */
public class ImageExport extends TeeBase {
	
	private static final long serialVersionUID = 1L;

	private JPEGFormat jpegFormat;
    private PNGFormat pngFormat;
    private BMPFormat bmpFormat;
    private SVGFormat svgFormat;
    private JavascriptFormat jscriptFormat;
    //private GIFFormat gifFormat;

    public ImageExport(IBaseChart chart) {
        super(chart);
    }
    
    /**
     * Exports Chart as JPEG format.
     *
     * @return JPEGFormat
     */
    public JPEGFormat getJPEG() {
        if (jpegFormat == null) {
            jpegFormat = new JPEGFormat(chart);
        }
        return jpegFormat;
    }
    
    /**
     * Exports Chart as PNG format.
     * @return PNGFormat
     */
    public PNGFormat getPNG() {
        if (pngFormat == null) {
            pngFormat = new PNGFormat(chart);
        }
        return pngFormat;
    }
    
    /**
     * Exports Chart as BMP format.
     *
     * @return BMPFormat
     */
    public BMPFormat getBMP() {
        if (bmpFormat == null) {
            bmpFormat = new BMPFormat(chart);
        }
        return bmpFormat;
    }
    
    /**
     * Exports Chart as SVG format.
     *
     * @return SVGFormat
     */
    public SVGFormat getSVG() {
        if (svgFormat == null) {
            svgFormat = new SVGFormat(chart);
        }
        return svgFormat;
    }
    
    /**
     * Exports Chart as Javascript format.
     *
     * @return JavascriptFormat
     */
    public JavascriptFormat getJScript() {
    	if (jscriptFormat == null) {
    		jscriptFormat = new JavascriptFormat(chart);
    	}
    	return jscriptFormat;
    }

	public Image image(Dimension dimension) {
        return chart.image(dimension.width, dimension.height);
    }
    
    public Image image() {
        return chart.image(chart.getWidth(), chart.getHeight());
    }
    
	public Image image(int width, int height) {
		return chart.image(width, height);
	}

    private class TransferImage implements Transferable {
        private java.awt.Image image;

        public TransferImage(java.awt.Image i) {
			super();
			image=i;
		}

		public synchronized DataFlavor[] getTransferDataFlavors() {
            return new DataFlavor[] {DataFlavor.imageFlavor};
        }

        public boolean isDataFlavorSupported(DataFlavor flavor) {
            return DataFlavor.imageFlavor.equals(flavor);
        }

        public synchronized Object getTransferData(DataFlavor flavor) throws
                UnsupportedFlavorException {
            if (!isDataFlavorSupported(flavor)) {
                throw new UnsupportedFlavorException(flavor);
            }
            return image;
        }
    }

    /**
     * Copy image to clipboard.
     */
    public void copyToClipboard() {
        copyToClipboard(chart.getWidth(), chart.getHeight());
    }
    
    /**
     * Copy image to clipboard.
     *
     * @param width int
     * @param height int
     */
    public void copyToClipboard(int width, int height) {
    	chart.getParent().copyToClipboard(new TransferImage(ImageUtils.bufferedFrom(chart.image(width, height))));
    }
}

