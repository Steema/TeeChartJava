package com.steema.teechart.swt;

import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.PaintContext;
import java.awt.GradientPaint;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.ColorModel;
import java.awt.image.Raster;
import java.awt.image.WritableRaster;

import com.steema.teechart.Aspect;
import com.steema.teechart.drawing.ChartBrush;
import com.steema.teechart.drawing.ChartFont;
import com.steema.teechart.drawing.ChartPen;
import com.steema.teechart.drawing.Gradient;
import com.steema.teechart.drawing.GradientDirection;
import com.steema.teechart.drawing.Graphics3D; //mm /*import com.steema.teechart.drawing.LinearGradientMode;*/
import com.steema.teechart.drawing.Image;
import com.steema.teechart.drawing.MiddleGradientPaintContext;
import com.steema.teechart.drawing.Point;
import com.steema.teechart.drawing.StringAlignment;
import com.steema.teechart.Dimension;
import com.steema.teechart.IBaseChart;
import com.steema.teechart.ImageMode;
import com.steema.teechart.PointDouble;
import com.steema.teechart.swt.GDIFontObject;

import org.eclipse.swt.graphics.Path;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Transform;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Region;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.graphics.Device;
import org.eclipse.swt.graphics.Pattern;

/**
 * TeeChart Graphics3D implementation for Eclipse SWT library.
 * 
 * See also com.steema.teechart.swt.TChart class.
 * 
 */
@SuppressWarnings("serial")
public class Graphics3DSWT extends Graphics3D {

	protected int currentX;
	protected int currentY;

	public GC g;
	private Device device;

	public Graphics3DSWT(IBaseChart c) {
		super(c);
	}

	// / <summary>
	// / Accesses Drawing Graphics
	// / </summary>
	public Object getGraphics() {
		return g;
	}

	public Object getImagePixels(Image image) {
		return image.data.getImageData().data;
	}

	public org.eclipse.swt.graphics.RGB[] getRGBPalette() {

		RGB[] rgb = new RGB[getColorPalette().length];
		for (int i = 0; i < getColorPalette().length; i++) {
			rgb[i] = new RGB(getColorPalette()[i].getRed(),
					getColorPalette()[i].getGreen(),
					getColorPalette()[i].getBlue());
		}
		return rgb;
	}

	Font oldFont;

	public void resetState() {
		if (g.getFont() != null)
			oldFont = g.getFont();
	}

	// public void setClip(Shape region) {
	/*
	 * Region r=new Region(device);
	 * 
	 * java.awt.Rectangle tmp=region.getBounds(); Rectangle rect=new
	 * Rectangle(tmp.x,tmp.y,tmp.width,tmp.height);
	 * 
	 * r.add(rect); g.setClipping(r); r.dispose();
	 */
	// }

	public void setGraphics(Object value) {
		if (value instanceof GC) {
			g = (GC) value;
		}
	}

	public void setPixel(int x, int y, Color color) {
		penSetColor(color);
		penPrepare(pen);
		g.drawLine(x, y, x + 1, y + 1);
	}

	public void setPixel(int x, int y, int z,
			com.steema.teechart.drawing.Color color) {
		pixel(x, y, z, color);
	}

	public void showImage() {
		// g.setFont(null);
		// g.dispose();
	}

	public boolean validState() {
		return g != null;
	}

	public void initWindow(Aspect a, com.steema.teechart.Rectangle r,
			int maxDepth) {
		super.initWindow(a, r, maxDepth);

		if (textSmooth)
			g.setTextAntialias(SWT.ON);
		else
			g.setTextAntialias(SWT.DEFAULT);

		if (smoothingMode)
			g.setAntialias(SWT.ON); // SWT.ON: mode =
									// Gdip.SmoothingModeAntiAlias;
		else
			g.setAntialias(SWT.DEFAULT); // SWT.DEFAULT: mode =
		// Gdip.SmoothingModeDefault;
	}

	public void prepareDrawImage() {
		/*
		 * g.InterpolationMode = InterpolationMode.NearestNeighbor;
		 * g.PixelOffsetMode = PixelOffsetMode.HighQuality;
		 */
	}

	/**
	 * Brush to fill rectangular area rect
	 * 
	 * @param gradient
	 *            Gradient
	 * @param g
	 *            Graphics2D
	 * @param rect
	 *            Rectangle
	 */
	static public void prepareGradient(Gradient gradient, Graphics2D g,
			com.steema.teechart.Rectangle rect) {
		
		//removable method - not called in SWT
		
		if (rect.width < 1) {
			rect.width = 1;
		}
		if (rect.height < 1) {
			rect.height = 1;
		}

		Point p1 = rect.getLocation();
		Point p2 = new Point(rect.getRight(), rect.getBottom());

		if (gradient.getDirection() == GradientDirection.RADIAL) {

			/*
			 * alternative radialgradient Color[] colors =
			 * {gradient.getStartColor(),gradient.getMiddleColor(),
			 * gradient.getEndColor()}; float[] dist = {0.0f, 0.2f, 1.0f};
			 * 
			 * java.awt.geom.Point2D center = new java.awt.geom.Point2D.Double(
			 * (1 + gradient.getRadialX() + rect.center().getX()), (1 +
			 * gradient.getRadialY() + rect.center().getY()));
			 * 
			 * float radius = rect.width / 2; //gradient.getRadialX();
			 * 
			 * g.setPaint( new java.awt.RadialGradientPaint(center, radius,
			 * dist, colors));
			 */

			g.setPaint(new RadialGradientPaint(new Point2D.Double((1 + gradient
					.getRadialX() + rect.center().getX()), (1 + gradient
					.getRadialY() + rect.center().getY())), new Point2D.Double(
					rect.width / 2, rect.height / 2), gradient.getStartColor(),
					gradient.getEndColor()));
			return;
		} else if (gradient.getDirection() == GradientDirection.VERTICAL) {
			p1.x = rect.center().x;
			p2.x = p1.x;
		} else if (gradient.getDirection() == GradientDirection.HORIZONTAL) {
			p1.y = rect.center().y;
			p2.y = p1.y;
		} else if (gradient.getDirection() == GradientDirection.FORWARDDIAGONAL) {
			p1.y = p2.y;
			p2.y = rect.y;
		}

		/** @todo Calculate p1 and p2 for gradient.getAngle() */
		if (gradient.getUseMiddle()) {
			g.setPaint(new MiddleGradientPaint(p1, gradient
					.correctColor(gradient.getStartColor()), p2, gradient
					.correctColor(gradient.getEndColor()), gradient
					.correctColor(gradient.getMiddleColor())));
		} else {
			g.setPaint(new GradientPaint(p1, gradient.correctColor(gradient
					.getStartColor()), p2, gradient.correctColor(gradient
					.getEndColor())));
		}

	}

	private static class MiddleGradientPaint implements Paint {

		private Point2D.Float p1;
		private Point2D.Float p2;
		private com.steema.teechart.drawing.Color startColor;
		private com.steema.teechart.drawing.Color endColor;
		private com.steema.teechart.drawing.Color middleColor;

		/**
		 * Constructs a simple <code>MiddleGradientPaint</code> object.
		 * 
		 * @param pt1
		 *            the first specified <code>Point</code> in user space
		 * @param color1
		 *            <code>Color</code> at the first specified
		 *            <code>Point</code>
		 * @param pt2
		 *            the second specified <code>Point</code> in user space
		 * @param color2
		 *            <code>Color</code> at the second specified
		 *            <code>Point</code>
		 * @param color3
		 *            <code>Color</code> at the middle of pt2 & pt1
		 * @throws NullPointerException
		 *             if either one of colors or points is null
		 */
		public MiddleGradientPaint(final Point2D pt1,
				final com.steema.teechart.drawing.Color startColor, final Point2D pt2,
				final com.steema.teechart.drawing.Color endColor,
				final com.steema.teechart.drawing.Color middleColor) {
			
			if ((startColor == null) || (endColor == null)
					|| (middleColor == null) || (pt1 == null) || (pt2 == null)) {
				throw new NullPointerException(
						"Colors and points should be non-null");
			}

			p1 = new Point2D.Float((float) pt1.getX(), (float) pt1.getY());
			p2 = new Point2D.Float((float) pt2.getX(), (float) pt2.getY());
			this.startColor = startColor;
			this.endColor = endColor;
			this.middleColor = middleColor;
		}

		public PaintContext createContext(final ColorModel cm,
				final java.awt.Rectangle deviceBounds, final Rectangle2D userBounds,
				final AffineTransform xform, final RenderingHints hints) {
			return new MiddleGradientPaintContext(cm, p1, p2, xform,
					startColor, endColor, middleColor);
		}

		;

		/**
		 * Returns the transparency mode for this <code>GradientPaint</code>.
		 * 
		 * @return an integer value representing this <code>GradientPaint</code>
		 *         object's transparency mode.
		 * @see Transparency
		 */

		public int getTransparency() {
			int a1 = startColor.getAlpha();
			int a2 = endColor.getAlpha();
			int a3 = middleColor.getAlpha();
			return (((a1 & a2 & a3) == 0xff) ? OPAQUE : TRANSLUCENT);
		}
	}

	private static class RadialGradientPaint implements Paint {

		private Point2D center;
		private Point2D radius;
		private com.steema.teechart.drawing.Color startColor;
		private com.steema.teechart.drawing.Color endColor;

		public RadialGradientPaint(Point2D point, Point2D radius,
				com.steema.teechart.drawing.Color startColor,
				com.steema.teechart.drawing.Color endColor) {
			this.center = point;
			this.startColor = startColor;
			this.endColor = endColor;
			this.radius = radius;
		}

		public PaintContext createContext(ColorModel cm,
				java.awt.Rectangle deviceBounds, Rectangle2D userBounds,
				AffineTransform xform, RenderingHints hints) {
			return new RadialGradientContext(xform.transform(center, null),
					startColor, xform.deltaTransform(radius, null), endColor);
		}

		public int getTransparency() {
			return (((startColor.getAlpha() & endColor.getAlpha()) == 0xff) ? OPAQUE
					: TRANSLUCENT);
		}

		private class RadialGradientContext implements PaintContext {

			private Point2D point;
			private Point2D radius;
			protected com.steema.teechart.drawing.Color startColor;
			private com.steema.teechart.drawing.Color endColor;

			public RadialGradientContext(Point2D point,
					com.steema.teechart.drawing.Color startColor,
					Point2D radius, com.steema.teechart.drawing.Color endColor) {
				this.point = point;
				this.radius = radius;
				this.startColor = startColor;
				this.endColor = endColor;
			}

			public void dispose() {
			}

			public ColorModel getColorModel() {
				return ColorModel.getRGBdefault();
			}

			public Raster getRaster(int x, int y, int w, int h) {

				double tmpRadius = radius.distance(0, 0);

				int[] data = new int[w * h * 4];

				int startRed = startColor.getRed();
				int difRed = endColor.getRed() - startRed;

				int startGreen = startColor.getGreen();
				int difGreen = endColor.getGreen() - startGreen;

				int startBlue = startColor.getBlue();
				int difBlue = endColor.getBlue() - startBlue;

				int startAlpha = startColor.getAlpha();
				int difAlpha = endColor.getAlpha() - startAlpha;

				for (int j = 0; j < h; j++) {
					for (int i = 0; i < w; i++) {
						double ratio = point.distance(x + i, y + j) / tmpRadius;

						if (ratio > 1.0) {
							ratio = 1.0;
						}

						int base = (j * w + i) * 4;

						data[base + 0] = (int) (startRed + ratio * difRed);
						data[base + 1] = (int) (startGreen + ratio * difGreen);
						data[base + 2] = (int) (startBlue + ratio * difBlue);
						data[base + 3] = (int) (startAlpha + ratio * difAlpha);
					}
				}

				WritableRaster raster = getColorModel()
						.createCompatibleWritableRaster(w, h);
				raster.setPixels(0, 0, w, h, data);
				return raster;
			}
		}
	}

	private Color[] brushPrepare(final ChartBrush brush) {
		
		Color[] colors = new Color[1];
		
		if (brush.getImage() != null)
			g.setBackgroundPattern(new Pattern(device, brush.getImage().data));
		else
		{
			colors[0] = color(brush.getColor());
			g.setBackground(colors[0]);
		}
		
		return colors;
	}

	private Color[] brushPrepare(final ChartBrush brush, final Rectangle rect) {
		
		Color[] colors = null;
        
		if (brush.getGradientVisible()) {
			colors = new Color[2];
			colors[0] = color((brush.getGradient().getStartColor()));
			colors[1] = color((brush.getGradient().getEndColor()));
			
			Pattern scaledPattern = new Pattern(device, (float) rect.x,
					(float) rect.y, (float) (rect.x + rect.width),
					(float) (rect.y + rect.height),
					colors[0], brush.getGradient().getStartColor().getAlpha(),
					colors[1], brush.getGradient().getEndColor().getAlpha());

			g.setBackgroundPattern(scaledPattern);
		} else
			colors = brushPrepare(brush);
		
		return colors;
	}
	
	private Color scaleColor1;
	private Color scaleColor2;

	private Color[] brushPrepare(final ChartBrush brush,
			final Point[] p) {
		
		Color[] colors = null;
		
		//Color tmpResult = null;
		//Color tmpscaleColor1 = null;
        //Color tmpscaleColor2 = null;

		if (brush.getGradientVisible()) {
			final com.steema.teechart.Rectangle rect = polygonRect(p);
			
			colors = new Color[2];
			
			colors[0] = color((brush.getGradient().getStartColor()));
			colors[1] = color((brush.getGradient().getEndColor()));
			
			final Pattern scaledPattern = new Pattern(device, (float) rect.x,
					(float) rect.y, (float) (rect.x + rect.width),
					(float) (rect.y + rect.height),
					colors[0], brush.getGradient().getStartColor().getAlpha(),
					colors[1], brush.getGradient().getEndColor().getAlpha());
			
			g.setBackgroundPattern(scaledPattern);
		} else
			colors = brushPrepare(brush);
		
		return colors;
	}

	private Color[] brushPrepare(final ChartBrush brush,
			final com.steema.teechart.Rectangle rect) {
        Color[] colors = null;

        
		if (brush.getGradientVisible()) {
			colors = new Color[2];
			colors[0] = color((brush.getGradient().getStartColor()));
			colors[1] = color((brush.getGradient().getEndColor()));
			
			Pattern scaledPattern = new Pattern(device, (float) rect.x,
					(float) rect.y, (float) (rect.x + rect.width),
					(float) (rect.y + rect.height),
					colors[0], brush.getGradient().getStartColor().getAlpha(),
					colors[1], brush.getGradient().getEndColor().getAlpha());
			g.setBackgroundPattern(scaledPattern);
		} else
			colors = brushPrepare(brush);
		
		return colors;
	}

	private Color color(java.awt.Color value) {
		if (!g.isDisposed())
			g.setAlpha(value.getAlpha());
		return new Color(device, value.getRed(), value.getGreen(),
				value.getBlue());
	}

	private Color[] penPrepare(ChartPen pen) {
		
		Color[] tmpColors = new Color[1];
		tmpColors[0] = color(pen.color); 
		
		g.setForeground(tmpColors[0]);

		switch (pen.getStyle().getValue()) {
		case 0:
			g.setLineStyle(SWT.LINE_SOLID);
			break;
		case 1:
			g.setLineStyle(SWT.LINE_DOT);
			break;
		case 2:
			g.setLineStyle(SWT.LINE_DASH);
			break;
		case 3:
			g.setLineStyle(SWT.LINE_DASHDOT);
			break;
		default:
			g.setLineStyle(SWT.LINE_DASHDOTDOT);
			break;
		}

		/*
		switch (pen.getDashCap()) {
		case LineCap.BEVEL: g.setLineCap();
		}
		
		switch (pen.getJoin()) {
		...
		}
		*/
		
		g.setLineWidth(pen.getWidth());
		
		return tmpColors;
	}

	protected void line(ChartPen pen, Point a, Point b) {
		Color[] tmpColors = null;
		tmpColors = penPrepare(pen);
		g.drawLine(a.x, a.y, b.x, b.y);
		moveTo(b);
		if (tmpColors != null) disposeColors(tmpColors);
	}

	// / <summary>
	// / Draws a line from the present PenPos to the new co-ordinates.
	// / </summary>
	public void lineTo(int x, int y) {
		Color[] tmpColors = null;
		tmpColors = penPrepare(pen);
		g.drawLine(currentX, currentY, x, y);
		moveTo(x, y);
		if (tmpColors!=null) disposeColors(tmpColors);
	}

	// / <summary>
	// / Draws a Line between co-ordinates.
	// / </summary>
	public void line(int x0, int y0, int x1, int y1) {
		Color[] tmpColors = null;
		tmpColors = penPrepare(pen);
		g.drawLine(x0, y0, x1, y1);
		moveTo(x1, y1);
		if (tmpColors!=null) disposeColors(tmpColors);
	}

	// / <summary>
	// / Draws a Vertical Line from (X,Top) to (X,Bottom).
	// / </summary>
	public void verticalLine(int x, int top, int bottom) {
		Color[] tmpColors = null;
		tmpColors = penPrepare(pen);
		g.drawLine(x, top, x, bottom);
		moveTo(x, bottom);
		if (tmpColors!=null) disposeColors(tmpColors);
	}

	// / <summary>
	// / Draws a Horizontal Line.
	// / </summary>
	public void horizontalLine(int left, int right, int y) {
		Color[] tmpColors = null;
		tmpColors = penPrepare(pen);
		g.drawLine(left, y, right, y);
		moveTo(right, y);
		if (tmpColors!=null) disposeColors(tmpColors);
	}

	// / <summary>
	// / Draws Bzier splines for the Point array p
	// / </summary>
	public void drawBeziers(final Point[] p) {
		/** @todo FINISH !! */
		// g.drawBeziers(p);
	}

	// / <summary>
	// / Draws a rectangle with rounded corners of personalized curvature and
	// size.
	// / </summary>
	public void roundRectangle(com.steema.teechart.Rectangle r, int roundWidth,
			int roundHeight) {
		Color[] tmpColors = null;
		
		if (brush.getVisible()) {
			if (brush.getImage() != null) {
				drawBrushImage(r);
			} else {
				tmpColors = brushPrepare(brush, r);
				g.fillRoundRectangle(r.x, r.y, r.width, r.height, roundWidth, roundHeight);
				if (tmpColors!=null) disposeColors(tmpColors);
			}
		}

		if (pen.visible) {
			tmpColors = penPrepare(pen);
			r.width--;
			r.height--;
			g.drawRoundRectangle(r.x, r.y, r.width, r.height, roundWidth, roundHeight);
			if (tmpColors!=null) disposeColors(tmpColors);
		}
	}
	
	private void disposeColors(Color[] colors)
	{
		if (colors!=null)
		{
			for (int i=0; i < colors.length; i++)
				if (colors[i] != null)
				  colors[i].dispose();
		}
		
		colors = null;
		
		if (g.getBackgroundPattern()!=null)
		  g.getBackgroundPattern().dispose();
	}

	public void rectangle(final ChartBrush brush, final com.steema.teechart.Rectangle r) {
		Color[] tmpColors = brushPrepare(brush, r);
		g.fillRectangle(r.x, r.y, r.width, r.height);
		
		if (tmpColors!=null) disposeColors(tmpColors);
	}

	protected void drawBrushImage(final com.steema.teechart.Rectangle r) {
		if ((brush.getImageMode() == ImageMode.NORMAL)
				|| (brush.getImageMode() == ImageMode.CENTER)) {
			brush.setSolid(true);
			rectangle(brush, r.x, r.y, r.width, r.height);
			brush.setSolid(false);
		}

		if (brush.getImageTransparent()) {
			draw(r, brush.getImage(), brush.getImageMode(), true);
		} else {
			draw(r, brush.getImage(), brush.getImageMode(), false);
		}
	}

	// / <summary>
	// / Draws a Rectangle (Rectangle r).
	// / </summary>
	public void rectangle(final com.steema.teechart.Rectangle r) {
		
		Color[] tmpColors = null;
		
		if (brush.getVisible()) {
			if (brush.getImage() != null) {
				drawBrushImage(r);
			} else {
				tmpColors = brushPrepare(brush, r);
				g.fillRectangle(r.x, r.y, r.width, r.height);
				if (tmpColors!=null) disposeColors(tmpColors);
			}
		}

		if (pen.visible) {
			tmpColors = penPrepare(pen);
			r.width--;
			r.height--;
			// g.setXORMode(true);
			g.drawRectangle(r.x, r.y, r.width, r.height);
			if (tmpColors!=null) disposeColors(tmpColors);
		}
	}

	// / <summary>
	// / Use Arc to draw an elliptically curved line with the current Pen.
	// / </summary>
	public void arc(int x1, int y1, int x2, int y2, double startAngle,
			double sweepAngle) {
		if (pen.visible) {
			Color[] tmpColors = null;
			tmpColors = penPrepare(pen);
			g.drawArc(x1, y1, x2-x1, y2-y1, (int) startAngle, (int) sweepAngle);
			if (tmpColors!=null) disposeColors(tmpColors);
		}
	}

	// CDI GaugesSeries
	// / <summary>
	// / Draws an arc in the rectangle defined by the 4 co-ordinates.
	// / </summary>
	public void arc(int x1, int y1, int x2, int y2, int x3, int y3, int x4,
			int y4) {
		if (pen.visible) {
			int w = x2 - x1;
			int h = y2 - y1;
			double xCenter = (w * 0.5) + x1;
			double yCenter = (h * 0.5) + y1;

			double startAngle = 360 - rad2Deg(Math.atan2((y2 - yCenter)
					- ((y3 - yCenter) - (y1 - yCenter)), x3 - xCenter));
			double endAngle = 360 - rad2Deg(Math.atan2((y2 - yCenter)
					- ((y4 - yCenter) - (y1 - yCenter)), x4 - xCenter));

			double sweepAngle;
			if (startAngle < endAngle) {
				sweepAngle = Math.abs(endAngle - startAngle);
			} else {
				sweepAngle = Math.abs(endAngle + (360 - startAngle));
			}

			g.drawArc(x1, y1, x2 - x1, y2 - y1, (int) startAngle,
					(int) sweepAngle);
		}
	}

	// CDI DrawZone
	protected void transparentEllipse(int x1, int y1, int x2, int y2) {
		/** @todo ?!!!!! (use brush.color transparency = 100) */
		Color[] tmpColors = null;
		
		brush.setColor(chart.getPanel().getColor());
		tmpColors = brushPrepare(brush);
		g.fillOval(x1, y1, x2 - x1, y2 - y1);
		if (tmpColors!=null) disposeColors(tmpColors);
	}

	// / <summary>
	// / Draws an Ellipse between the 2 co-ordinates.
	// / </summary>
	public void ellipse(int x1, int y1, int x2, int y2) {
		int w = x2 - x1;
		int h = y2 - y1;
		
		Color[] tmpColors = null;

		if (brush.getVisible()) {
			tmpColors = brushPrepare(brush, new Rectangle(x1, y1, x2, y2));
			g.fillOval(x1, y1, w, h);
			if (tmpColors!=null) disposeColors(tmpColors);
		}

		if (pen.visible) {
			tmpColors = penPrepare(pen);
			g.drawOval(x1, y1, w, h);
			if (tmpColors!=null) disposeColors(tmpColors);
		}
	}

	public void roundRectangle(Rectangle r) {
		roundRectangle(new com.steema.teechart.Rectangle(r), 8, 8);
	}

	private GDIFontObject prepareFont() {
		return prepareFont(font, font.getColor());
	}
	
	private GDIFontObject prepareFont(ChartFont f) {
		return prepareFont(f, f.getColor());
	}
	
	private GDIFontObject prepareFont(com.steema.teechart.drawing.Color c) {
		return prepareFont(font, c);
	}

	transient private Dimension drawSize = new Dimension(0,0);
	
	protected void drawString(int x, int y, String text, ChartBrush aBrush) {
		measureString(drawSize, font, text);
		
		Color[] tmpColors = null;
		Color tmpFrontColor = null;
		
		GDIFontObject fontObj = prepareFont(aBrush.getColor());
		
		tmpFrontColor = fontObj.getColor();

		tmpColors = brushPrepare(aBrush, new Rectangle(x, y, drawSize.width, drawSize.height));

		if (getTextAlign() == StringAlignment.CENTER) {
			x -= (drawSize.width / 2);
		} else if (getTextAlign() == StringAlignment.FAR) {
			x -= drawSize.width;
		}

		g.drawString(text, x, y, true);
		
		if (tmpFrontColor!=null) tmpFrontColor.dispose();
		if (tmpColors!=null) disposeColors(tmpColors);
		fontObj.getFont().dispose();
	}
	
	protected GDIFontObject prepareFont(ChartFont f,  com.steema.teechart.drawing.Color c) {

		int style = 0;

		if (f.getBold())
			style = style | SWT.BOLD;

		if (f.getItalic())
			style = style | SWT.ITALIC;
		
		Color fontColor = color(c);

		g.setForeground(fontColor);

		if (!g.getFont().equals(oldFont))
			g.getFont().dispose();
		
		GDIFontObject fontObj = new GDIFontObject(fontColor,
				                    new Font(null, f.getName(), f.getSize() - 2, style));

		g.setFont(fontObj.getFont());
		
		return fontObj;
	}

	public void measureString(Dimension dimension, final ChartFont f, final String text) {
		if (g == null) {
			g = (GC) chart.getGraphics3D().getGraphics();
		}
        
		GDIFontObject fontObj = prepareFont(f);
		final org.eclipse.swt.graphics.Point p = g.stringExtent(text);
		dimension.width = p.x + 1;
		dimension.height = p.y + 1;
		
		if (fontObj.getColor() != null) fontObj.getColor().dispose();
		if (fontObj.getFont() != null) fontObj.getFont().dispose();
		fontObj = null;
	}

	// / <summary>
	// / Creates a rectangular Windows GDI clipping region.
	// / </summary>
	public void clipRectangle(com.steema.teechart.Rectangle r) {
		g.setClipping(r.x, r.y, r.width, r.height);
	}

	private Region rregion;

	private Region getRegion(final Point[] p) {
		rregion = new Region(device);
		rregion.add(getSWTPolygon(p));
		return rregion;
	}

	// / <summary>
	// / Creates a Polygonal Windows GDI clipping region.
	// / </summary>
	public void clipPolygon(final Point[] p) {
		g.setClipping(getRegion(p));
		if (rregion != null)
			rregion.dispose();
	}

	// / <summary>
	// / Removes any clipping region applied to Chart Drawing.
	// / </summary>
	// [Description("Removes any clipping region applied to Chart Drawing.")]
	public void unClip() {
		IBaseChart c = getChart();
		g.setClipping(c.getLeft(), c.getTop(), c.getWidth(), c.getHeight());
	}

	// / <summary>
	// / Creates a Elliptical Windows GDI clipping region.
	// / </summary>
	public void clipEllipse(com.steema.teechart.Rectangle r) {

		// No ellipse clipping. Maybe with org.swt.draw2D ?
		g.setClipping(r.x, r.y, r.width, r.height);
	}

	// / <summary>
	// / Draws a rotated text String at the specified xy coordinates with the
	// RotDegree rotation angle.
	// / </summary>
	public void rotateLabel(int x, int y, String text, double rotDegree) {

		Transform transform = new Transform(g.getDevice());
		GDIFontObject fontObj = prepareFont();

		// Rotate by rotDegree degrees
		transform.translate(x, y);
		transform.rotate(-(float) rotDegree);
		g.setTransform(transform);
	
		drawString(0, 0, text, font.getBrush());
		if (fontObj.getColor() != null) fontObj.getColor().dispose();
		if (fontObj.getFont() != null) fontObj.getFont().dispose();
		fontObj = null;
		transform.dispose();
		g.setTransform(null);
	}
	
	Transform locTransform = null;

	public void rotateTransform(Point p, double rotDegree) {
		locTransform = new Transform(g.getDevice());

		locTransform.translate(p.x, p.y);
		locTransform.rotate(-(float) rotDegree);

		g.setTransform(locTransform);

		p.x = 0;
		p.y = 0;
	}

	public void resetTransform() {
		if (locTransform != null)
			locTransform.dispose();
		g.setTransform(null);
	}

	// / <summary>
	// / Sets the value of PenPos to x and y co-ordinates before calling LineTo.
	// / </summary>
	public void moveTo(int x, int y) {
		currentX = x;
		currentY = y;
	}

	public void fillRectangle(ChartBrush brush, int x, int y, int width,
			int height) {
		Color[] tmpColors = null;
		tmpColors = brushPrepare(brush);
		g.fillRectangle(x, y, width, height);
		if (tmpColors!=null) disposeColors(tmpColors);
	}

	private Rectangle fromLTRB(int x1, int y1, int x2, int y2) {
		return new Rectangle(x1, y1, x2 - x1, y2 - y1);
	}

	// / <summary>
	// / Draws a Pie slice using StartAngle and End Angle.
	// / </summary>
	public void pie(int x1, int y1, int x2, int y2, double startAngle,
			double endAngle) {
		int start = (int) -startAngle;
		int end = (int) -(endAngle - startAngle);
		
		Color[] tmpColors = null;

		if (brush.getVisible()) {
			tmpColors = brushPrepare(brush, fromLTRB(x1, y1, x2, y2));
			g.fillArc(x1, y1, x2, y2, start, end);
			if (tmpColors!=null) disposeColors(tmpColors);
		}

		if (pen.visible) {
			tmpColors = penPrepare(pen);
			g.drawArc(x1, y1, x2, y2, start, end);
			if (tmpColors!=null) disposeColors(tmpColors);
		}
	}

	// / <summary>
	// / Clears background defined for Rectangle Left, Top, Right, Bottom.
	// / </summary>
	public void eraseBackground(int left, int top, int right, int bottom) {
		fillRectangle(brush, left, top, right - left, bottom - top);
	}

	private java.awt.Color awtColor(Color value) {
		return new java.awt.Color(value.getRed(), value.getGreen(),
				value.getBlue());
	}

	private void setPaint(GradientPaint paint) {
		// ??? Gradients in SWT ? (as traditional VCL/Win32 style?)
	}

	// / <summary>
	// / Fills a screen area with color-gradient background.
	// / </summary>
	// mm
	public void gradientFill(int left, int top, int right, int bottom,
			Color startColor, Color endColor/*
											 * , LinearGradientMode direction
											 */) {
		GradientPaint tmp = new GradientPaint(new Point(left, top),
				awtColor(startColor), new Point(right, bottom),
				awtColor(endColor));
		setPaint(tmp);

		/** @todo FINISH TRANSFORM for gradient direction */
		g.fillRectangle(left, top, right, bottom);

		setPaint(null);
	}

	private void penSetColor(org.eclipse.swt.graphics.Color color) {
		g.setForeground(color);
	}

	private void penSetColor(java.awt.Color color) {
		// penSetColor(color(color));
	}

	// / <summary>
	// / Sets the 3D pixel to OLE_Color, using Pen.Width.
	// / </summary>
	public void pixel(int x, int y, int z,
			com.steema.teechart.drawing.Color color) {
		
		Color[] tmpColors = null;
		
		penSetColor(color);
		tmpColors = penPrepare(pen);
		Point p = calc3DPos(x, y, z);
		g.drawLine(p.x, p.y, p.x + 1, p.y + 1);
		if (tmpColors!=null) disposeColors(tmpColors);
	}

	// / <summary>
	// / Draws a series of line segments to join point array p at z displacement
	// / </summary>
	public void polyLine(int z, final Point[] p) {
		int l = p.length;
		
		final Point[] tmpP = new Point[l];

		for (int t = 0; t < l; t++)
			tmpP[t] = calc3DPoint(p[t], z);
		
		for (int t = 1; t < l; t++)
			g.drawLine(tmpP[t - 1].x, tmpP[t - 1].y, tmpP[t].x, tmpP[t].y);
		
	}

	public boolean fillPieSegment(int x1, int y1, int x2, int y2, int sd,
			int rd, int cl) {
		return false;
	}

	private final int[] getSWTPolygon(final Point[] p) {
		final int[] tmp = new int[2 * p.length];

		for (int t = 0; t < p.length; t++) {
			tmp[2 * t] = p[t].x;
			tmp[2 * t + 1] = p[t].y;
		}

		return tmp;
	}

	/*
	 * private final Region getRegion(final Polygon p) {
	 * 
	 * final Point[] points=new Point[p.npoints];
	 * 
	 * for (int t = 0; t < p.npoints; t++) { points[t].x = p.xpoints[t];
	 * points[t].y = p.ypoints[t]; }
	 * 
	 * Region result=new Region(device); result.add(getSWTPolygon(points));
	 * 
	 * return result; }
	 * 
	 * private final int[] getPoints(final Polygon p) {
	 * 
	 * int[] vals=new int[p.npoints * 2];
	 * 
	 * int j=0; for (int t = 0; t < p.npoints; t++) { vals[j] = p.xpoints[t];
	 * j++; vals[j] = p.ypoints[t]; j++; }
	 * 
	 * return vals; }
	 */

	private final int[] getPoints(final Point[] p) {

		int[] vals = new int[p.length * 2];

		int j = 0;
		for (int t = 0; t < p.length; t++) {
			vals[j++] = p[t].x;
			vals[j++] = p[t].y;
		}

		return vals;
	}

	public void polygon(ChartBrush brush, final Point[] p) {
		Color[] tmpColors = brushPrepare(brush);
		polygon(p);
		if (tmpColors!=null) disposeColors(tmpColors);
	}

	// / <summary>
	// / Draws a polygon.
	// / </summary>
	public void polygon(final Point[] p) {
		int[] tmp = getSWTPolygon(p);
		
		Color[] tmpColors = null;

		if (brush.getVisible()) {
			tmpColors = brushPrepare(brush, p);
			g.fillPolygon(tmp);
			if (tmpColors!=null) disposeColors(tmpColors);
		}

		if (pen.visible) {
			tmpColors = penPrepare(pen);
			g.drawPolygon(tmp);
			if (tmpColors!=null) disposeColors(tmpColors);
		}
	}

	public void polyLine(final Point[] p) {
		if (pen.visible) {
			Color[] tmpColors = penPrepare(pen);
			g.drawPolyline(getSWTPolygon(p));
			if (tmpColors!=null) disposeColors(tmpColors);
		}
	}

	public void draw(com.steema.teechart.Rectangle r, Image image,
			boolean transparent, boolean smoothed) {
	}

	// / <summary>
	// / Paints the image in rectangle r.
	// / </summary>
	public void draw(org.eclipse.swt.graphics.Rectangle r,
			org.eclipse.swt.graphics.Image image, boolean transparent) {
		if (transparent) { // TODO //MM
			g.drawImage(image, r.x, r.y);
			// ImageAttributes imageAttr = new ImageAttributes();
			// Bitmap bmp = new Bitmap(image);
			// Color backColor = bmp.GetPixel(1, 1);
			// imageAttr.SetColorKey(backColor, backColor);

			// g.drawImage(image, 0, 0, image.getWidth(null),
			// image.getHeight(null), 0, 0, 0, 0);
		} else {
			// g.drawImage(image, r.x, r.y);
			g.drawImage(image, r.x, r.y);
		}
	}

	// / <summary>
	// / Paints the image at x,y coordinates.
	// / </summary>
	public void draw(int x, int y, Image image) {
		if (image != null) {
			// g.drawImage(image, x, y);
		}
	}

	public boolean getSmoothingMode() {
		return smoothingMode;
	}

	/*
	 * Setting Render smoothing sets Text smoothing too. If you wish to override
	 * the setting for Text use the setTextSmoothingMode after setting
	 * smoothing.
	 */
	public void setSmoothingMode(boolean value) {
		if (smoothingMode != value) {
			textSmooth = value;
			smoothingMode = value;
			this.invalidate();
		}
	}

	public boolean getTextSmoothingMode() {
		return textSmooth;
	}

	public void setTextSmoothingMode(boolean value) {
		if (textSmooth != value) {
			textSmooth = value;
			this.invalidate();
		}
	}

	protected Path getPath(final Point[] p) {
		Path tmp = new Path(org.eclipse.swt.widgets.Display.getCurrent());
		for (int t = 0; t < p.length; t++) {
			if (t == 0)
				tmp.moveTo(p[t].x, p[t].y);
			else
				tmp.lineTo(p[t].x, p[t].y);
		}
		return tmp;
	}

	public void polygon(final com.steema.teechart.PointDouble[] points) {

		final Point[] p = PointDouble.round(points);
		polygon(p);

	}

	@Override
	public void ellipseEnh(int x1, int y1, int x2, int y2) {
		// TODO Auto-generated method stub
	}

	@Override
	protected void sphereEnh(int x1, int y1, int x2, int y2) {
		// TODO Auto-generated method stub
	}
	
	public int sizeToDIP(float value) {
		return (int) value;
	}

	public int sizeToUnit(int unit, float value) {
		return (int) value;
	}
}
