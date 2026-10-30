/*
 * GradientPaintContextTest.java
 *
 */

package com.steema.teechart.drawing;

import java.awt.PaintContext;
import java.awt.geom.AffineTransform;
import java.awt.geom.NoninvertibleTransformException;
import java.awt.geom.Point2D;
import java.awt.image.ColorModel;
import java.awt.image.DirectColorModel;
import java.awt.image.Raster;
import java.lang.ref.WeakReference;
import sun.awt.image.IntegerComponentRaster;

/**
 *
 * <p>Title: MiddleGradientPaintContext class</p>
 *
 *
 * <p>Copyright (c) 2005-2019 by Steema Software SL. All Rights Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 *
 * @author tom
 *
 * Most code taken from GradientPaintContext
 */
public class MiddleGradientPaintContext implements PaintContext {
    static ColorModel xrgbmodel =
	new DirectColorModel(24, 0x00ff0000, 0x0000ff00, 0x000000ff);
    static ColorModel xbgrmodel =
	new DirectColorModel(24, 0x000000ff, 0x0000ff00, 0x00ff0000);

    static ColorModel cachedModel;
    static WeakReference cached;

    static synchronized Raster getCachedRaster(ColorModel cm, int w, int h) {
	if (cm == cachedModel) {
	    if (cached != null) {
		Raster ras = (Raster) cached.get();
		if (ras != null &&
		    ras.getWidth() >= w &&
		    ras.getHeight() >= h)
		{
		    cached = null;
		    return ras;
		}
	    }
	}
	return cm.createCompatibleWritableRaster(w, h);
    }

    static synchronized void putCachedRaster(ColorModel cm, Raster ras) {
	if (cached != null) {
	    Raster cras = (Raster) cached.get();
	    if (cras != null) {
		int cw = cras.getWidth();
		int ch = cras.getHeight();
		int iw = ras.getWidth();
		int ih = ras.getHeight();
		if (cw >= iw && ch >= ih) {
		    return;
		}
		if (cw * ch >= iw * ih) {
		    return;
		}
	    }
	}
	cachedModel = cm;
	cached = new WeakReference(ras);
    }

    double x1;
    double y1;
    double dx;
    double dy;
    int interp[];
    Raster saved;
    ColorModel model;

    public MiddleGradientPaintContext(ColorModel cm,
				Point2D p1, Point2D p2, AffineTransform xform,
				Color startColor, Color endColor, Color middleColor) {
	// First calculate the distance moved in user space when
	// we move a single unit along the X & Y axes in device space.
	Point2D xvec = new Point2D.Double(1, 0);
	Point2D yvec = new Point2D.Double(0, 1);
	try {
	    AffineTransform inverse = xform.createInverse();
	    inverse.deltaTransform(xvec, xvec);
	    inverse.deltaTransform(yvec, yvec);
	} catch (NoninvertibleTransformException e) {
	    xvec.setLocation(0, 0);
	    yvec.setLocation(0, 0);
	}

	// Now calculate the (square of the) user space distance
	// between the anchor points. This value equals:
	//     (UserVec . UserVec)
	double udx = p2.getX() - p1.getX();
	double udy = p2.getY() - p1.getY();
	double ulenSq = udx * udx + udy * udy;

	if (ulenSq <= Double.MIN_VALUE) {
	    dx = 0;
	    dy = 0;
	} else {
	    // Now calculate the proportional distance moved along the
	    // vector from p1 to p2 when we move a unit along X & Y in
	    // device space.
	    //
	    // The length of the projection of the Device Axis Vector is
	    // its dot product with the Unit User Vector:
	    //     (DevAxisVec . (UserVec / Len(UserVec))
	    //
	    // The "proportional" length is that length divided again
	    // by the length of the User Vector:
	    //     (DevAxisVec . (UserVec / Len(UserVec))) / Len(UserVec)
	    // which simplifies to:
	    //     ((DevAxisVec . UserVec) / Len(UserVec)) / Len(UserVec)
	    // which simplifies to:
	    //     (DevAxisVec . UserVec) / LenSquared(UserVec)
	    dx = (xvec.getX() * udx + xvec.getY() * udy) / ulenSq;
	    dy = (yvec.getX() * udx + yvec.getY() * udy) / ulenSq;

            if (dx < 0) {
                // we need dx to be non-negative for simplicity of scanning
		// across the scan lines for the transition points.
                // To ensure that constraint, we negate the dx/dy
		// values and swap the points and colors.
		Point2D p = p1; p1 = p2; p2 = p;
		Color c = startColor; startColor = endColor; endColor = c;
		dx = -dx;
		dy = -dy;
	    }
	}

	Point2D dp1 = xform.transform(p1, null);
	this.x1 = dp1.getX();
	this.y1 = dp1.getY();

	int rgb1 = startColor.getRGB();
        int rgb2 = middleColor.getRGB();
	int rgb3 = endColor.getRGB();

	int a1 = (rgb1 >> 24) & 0xff;
	int r1 = (rgb1 >> 16) & 0xff;
	int g1 = (rgb1 >>  8) & 0xff;
	int b1 = (rgb1      ) & 0xff;

	int da1 = ((rgb2 >> 24) & 0xff) - a1;
	int dr1 = ((rgb2 >> 16) & 0xff) - r1;
	int dg1 = ((rgb2 >>  8) & 0xff) - g1;
	int db1 = ((rgb2      ) & 0xff) - b1;

	int a2 = (rgb2 >> 24) & 0xff;
	int r2 = (rgb2 >> 16) & 0xff;
	int g2 = (rgb2 >>  8) & 0xff;
	int b2 = (rgb2      ) & 0xff;

        int da2 = ((rgb3 >> 24) & 0xff) - a2;
	int dr2 = ((rgb3 >> 16) & 0xff) - r2;
	int dg2 = ((rgb3 >>  8) & 0xff) - g2;
	int db2 = ((rgb3      ) & 0xff) - b2;

        //TODO Fix this!
	if (a1 == 0xff && da1 == 0) {
	    model = xrgbmodel;
	    if (cm instanceof DirectColorModel) {
		DirectColorModel dcm = (DirectColorModel) cm;
		int tmp = dcm.getAlphaMask();
		if ((tmp == 0 || tmp == 0xff) &&
		    dcm.getRedMask() == 0xff &&
		    dcm.getGreenMask() == 0xff00 &&
		    dcm.getBlueMask() == 0xff0000)
		{
		    model = xbgrmodel;
		    tmp = r1; r1 = b1; b1 = tmp;
		    tmp = dr1; dr1 = db1; db1 = tmp;
		}
	    }
	} else {
	    model = ColorModel.getRGBdefault();
	}

        interp = new int[513];
        int rgb;

	for (int i = 0; i <= 256; i++) {
	    float rel = i / 256.0f;
	    rgb =
		(((int) (a1 + da1 * rel)) << 24) |
		(((int) (r1 + dr1 * rel)) << 16) |
		(((int) (g1 + dg1 * rel)) <<  8) |
		(((int) (b1 + db1 * rel))      );
	    interp[i] = rgb;

            rgb =
		(((int) (a2 + da2 * rel)) << 24) |
		(((int) (r2 + dr2 * rel)) << 16) |
		(((int) (g2 + dg2 * rel)) <<  8) |
		(((int) (b2 + db2 * rel))      );

            interp[256 + i] = rgb;
        }
    }

    /**
     * Release the resources allocated for the operation.
     */
    public void dispose() {
	if (saved != null) {
	    putCachedRaster(model, saved);
	    saved = null;
	}
    }

    /**
     * Return the ColorModel of the output.
     */
    public ColorModel getColorModel() {
        return model;
    }

    /**
     * Return a Raster containing the colors generated for the graphics
     * operation.
     * @param x int. Left. The area in device space for which colors are
     * generated.
     * @param y int. Top. See x
     * @param w int. Width. See x
     * @param h int. Height. See x
     */

    public Raster getRaster(int x, int y, int w, int h) {
	double rowrel = (x - x1) * dx + (y - y1) * dy;

	Raster rast = saved;
	if (rast == null || rast.getWidth() < w || rast.getHeight() < h) {
	    rast = getCachedRaster(model, w, h);
	    saved = rast;
	}
	IntegerComponentRaster irast = (IntegerComponentRaster) rast;
	int off = irast.getDataOffset(0);
	int adjust = irast.getScanlineStride() - w;
	int[] pixels = irast.getDataStorage();

        clipFillRaster(pixels, off, adjust, w, h, rowrel, dx, dy);

	return rast;
    }

    void clipFillRaster(int[] pixels, int off, int adjust, int w, int h,
			double rowrel, double dx, double dy) {
	while (--h >= 0) {
	    double colrel = rowrel;
	    int j = w;
	    if (colrel <= 0.0) {
		int rgb = interp[0];
		do {
		    pixels[off++] = rgb;
		    colrel += dx;
		} while (--j > 0 && colrel <= 0.0);
	    }
	    while (colrel < 1.0 && --j >= 0) {
                pixels[off++] = interp[(int) (colrel * 512)];
		colrel += dx;
	    }
	    if (j > 0) {
		int rgb = interp[512];
		do {
		    pixels[off++] = rgb;
		} while (--j > 0);
	    }

	    off += adjust;
	    rowrel += dy;
        }
    }
}
