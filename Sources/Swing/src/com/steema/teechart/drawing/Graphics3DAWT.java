package com.steema.teechart.drawing;

import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.Arc2D;

import java.awt.RenderingHints;
import java.awt.Paint;
import java.awt.Graphics;
import java.awt.font.TextLayout;
import java.awt.geom.Rectangle2D;
import java.awt.PaintContext;
import java.awt.image.ColorModel;
import java.awt.geom.Point2D;
import java.awt.image.WritableRaster;
import java.awt.image.Raster;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Shape;

import com.steema.teechart.Dimension;
import com.steema.teechart.Rectangle;
import com.steema.teechart.PointDouble;
import com.steema.teechart.IBaseChart;
import com.steema.teechart.Aspect;
import com.steema.teechart.ImageMode;
import com.steema.teechart.misc.ImageUtils;
import com.steema.teechart.misc.Utils;

/**
 * 
 * <p>
 * Title: Graphics3DAWT class
 * </p>
 * 
 * <p>
 * Description: Access to central Chart drawing elements
 * </p>
 * 
 * <p>
 * Copyright (c) 2005-2019 by Steema Software SL. All Rights Reserved.
 * </p>
 * 
 * <p>
 * Company: Steema Software SL
 * </p>
 * 
 */
public class Graphics3DAWT extends Graphics3D {

    /**
	 * 
	 */
    private static final long serialVersionUID = 1L;
    public transient int currentX;
    public transient int currentY;
    protected transient Graphics2D g;
    public transient boolean dirty = true;

    public Graphics3DAWT() {
        this(null);
    }

    public Graphics3DAWT(IBaseChart c) {
        super(c);
    }

    /**
     * Accesses Drawing Graphics
     * 
     * @return Graphics2D
     */
    public Object getGraphics() {
        return g;
    }

    public void resetState() {
        reset();
    }

    public void showImage() {
        //g = null; //CDI out for ScrollPager
        dirty = false;
    }

    public boolean getDirty() {
        return dirty;
    }

    public void setDirty(boolean value) {
        dirty = value;
    }

    public boolean validState() {
        return g != null;
    }

    public void initWindow(final Aspect a, final Rectangle r, int maxDepth) {
        super.initWindow(a, r, maxDepth);

        if (!metafiling) {
            /** @todo IMPROVE !! */
            if (smoothingMode) {
                g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                        RenderingHints.VALUE_ANTIALIAS_ON);
                g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
                        RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
                g.setRenderingHint(RenderingHints.KEY_RENDERING,
                        RenderingHints.VALUE_RENDER_QUALITY);
            } else {
                g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                        RenderingHints.VALUE_ANTIALIAS_OFF);
                g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
                        RenderingHints.VALUE_TEXT_ANTIALIAS_OFF);
                g.setRenderingHint(RenderingHints.KEY_RENDERING,
                        RenderingHints.VALUE_RENDER_SPEED);
            }
        }
    }

    protected void roundRectangle(final Rectangle r, int roundWidth, int roundHeight,
            Graphics2D g) {
        if (brush.getVisible()) {
            brushPrepare(brush, g, r);
            g.fillRoundRect(r.x, r.y, r.width, r.height, roundWidth,
                    roundHeight);
        }
        if (pen.visible) {
            penPrepare(pen, g);
            g.drawRoundRect(r.x, r.y, r.width, r.height, roundWidth,
                    roundHeight);
        }
    }

    static final private void penPrepare(final ChartPen pen, final Graphics2D g) {
        g.setColor(pen.getColor());
        g.setStroke(pen.getStroke());
    }

    // only used by ButtonPen
    static final public void penPrepare(final ChartPen pen, final Graphics g) {
        g.setColor(pen.getColor());

        if (g instanceof Graphics2D) {
            ((Graphics2D) g).setStroke(pen.getStroke());
        }
    }

    public void setPen(final ChartPen value) {
        if (value != null) {
            pen.assign(value);
            g.setColor(pen.getColor());
            g.setStroke(pen.getStroke());
        }
    }

    static private void brushPrepare(final ChartBrush brush, final Graphics2D g, final Point[] p) {

        // Note: Speed optimization. "polygonRect" is only called when needed.

        if (brush.getImage() != null) {
            g.setPaint(brush.getTexture(polygonRect(p)));
        } else {
            if (brush.getGradientVisible()) {
                // p.getBounds() faster than our polygonRect ?
                prepareGradient(brush.getGradient(), g, polygonRect(p));
            } else {
                g.setColor(brush.getColor());

                if (brush.getSolid()) {
                    g.setPaint(null);
                } else {
                    g.setPaint(brush.getHatchTexture());
                }
            }
        }
    }

    static private void brushPrepare(final ChartBrush brush, final Graphics2D g, final Rectangle r) {
        if (brush.getImage() != null) {
            g.setPaint(brush.getTexture(r));
        } else {
            if (brush.getGradientVisible()) {
                prepareGradient(brush.getGradient(), g, r);
            } else {
                g.setColor(brush.getColor());

                if (brush.getSolid()) {
                    g.setPaint(null);
                } else {
                    g.setPaint(brush.getHatchTexture());
                }
            }
        }
    }

    public void prepareDrawImage() {
        // g.InterpolationMode = InterpolationMode.NearestNeighbor;
        // g.PixelOffsetMode = PixelOffsetMode.HighQuality;
    }

    private static class MiddleGradientPaint implements Paint {

        private Point2D.Float p1;
        private Point2D.Float p2;
        private Color startColor;
        private Color endColor;
        private Color middleColor;

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
        public MiddleGradientPaint(Point2D pt1, Color startColor, Point2D pt2,
                Color endColor, Color middleColor) {
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

        public PaintContext createContext(ColorModel cm,
                java.awt.Rectangle deviceBounds, Rectangle2D userBounds,
                AffineTransform xform, RenderingHints hints) {
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
        private Color startColor;
        private Color endColor;
        protected int iTransition = 1;    

        public RadialGradientPaint(final Point2D point, final Point2D radius,
                final Color startColor, final Color endColor, int transition) {
            this.center = point;
            this.startColor = startColor;
            this.endColor = endColor;
            this.radius = radius;
            this.iTransition = transition;
        }

        public PaintContext createContext(final ColorModel cm,
                final java.awt.Rectangle deviceBounds, final Rectangle2D userBounds,
                final AffineTransform xform, final RenderingHints hints) {
            return new RadialGradientContext(xform.transform(center, null),
                    startColor, xform.deltaTransform(radius, null), endColor, iTransition);
        }

        public int getTransparency() {
            return (((startColor.getAlpha() & endColor.getAlpha()) == 0xff) ? OPAQUE
                    : TRANSLUCENT);
        }

        private class RadialGradientContext implements PaintContext {

            private Point2D point;
            private Point2D radius;
            protected Color startColor;
            private Color endColor;
            protected int iTransition = 1;                  

            public RadialGradientContext(final Point2D point, final Color startColor,
                    final Point2D radius, final Color endColor, int transition) {
                this.point = point;
                this.radius = radius;
                this.startColor = startColor;
                this.endColor = endColor;
                this.iTransition = transition;
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
                        double ratio = point.distance(x + i, y + j) / (tmpRadius * iTransition);

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

                WritableRaster raster = getColorModel().createCompatibleWritableRaster(w, h);
                raster.setPixels(0, 0, w, h, data);
                return raster;
            }
        }
    }

    public void setPixel(int x, int y, int z, Color color) {
        g.setColor(color);
        g.fillRect(x, y, 1, 1);
    }

    public void measureString(Dimension dimension, final ChartFont f, final String text) {
        final Font tmpFont = f.getDrawingFont(chart.getAspect().getFontZoom());

        if (g == null) {
            g = (Graphics2D) chart.getParent().getGraphics();
        }

        g.setFont(tmpFont);

        final Rectangle2D rect = tmpFont.getStringBounds(text, g.getFontRenderContext());

        dimension.width = 1 + (int) rect.getWidth();
        dimension.height = 1 + (int) rect.getHeight();
    }

    public void setClip(Shape region) {
        g.setClip(region);
    }

    public void setGraphics(final Object value) {
        if (value instanceof Graphics2D) {
            g = (Graphics2D) value;
        }
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
            Rectangle rect) {
        if (rect.width < 1) {
            rect.width = 1;
        }
        if (rect.height < 1) {
            rect.height = 1;
        }

        Point p1 = rect.getLocation();
        Point p2 = new Point(rect.getRight(), rect.getBottom());

        if (gradient.getDirection() == GradientDirection.RADIAL) {
            
            g.setPaint(new RadialGradientPaint(new Point2D.Double((1 + gradient.getRadialX() + rect.center().getX()), 
                                                   (1 + gradient.getRadialY() + rect.center().getY())), 
                                                    new Point2D.Double(rect.width / 2, rect.height / 2), 
                                                    gradient.getStartColor(),
                                                    gradient.getEndColor(),gradient.getRadialTransition()));
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
            g.setPaint(new MiddleGradientPaint(p1, gradient.correctColor(gradient.getStartColor()), p2, gradient.correctColor(gradient.getEndColor()), gradient.correctColor(gradient.getMiddleColor())));
        } else {
            g.setPaint(new GradientPaint(p1, gradient.correctColor(gradient.getStartColor()), p2, gradient.correctColor(gradient.getEndColor())));
        }

    }

    protected void line(ChartPen p, Point a, Point b) {
        if (pen.getVisible()) {
            penPrepare(p, g);
            g.drawLine(a.x, a.y, b.x, b.y);
            moveTo(b);
        }
    }

    /**
     * Draws a line from the present PenPos to the new co-ordinates.<br>
     * The Line method has 4 overloads to facilitate the setting of the end
     * co-ordinate using Point p or int x, int y, both with or without a z
     * parameter.
     * 
     * @param x
     *            int
     * @param y
     *            int
     */
    public void lineTo(int x, int y) {
        if (pen.getVisible()) {
            penPrepare(pen, g);
            g.drawLine(currentX, currentY, x, y);
            moveTo(x, y);
        }
    }

    /**
     * Draws a Line between co-ordinates.
     * 
     * @param x0
     *            int
     * @param y0
     *            int
     * @param x1
     *            int
     * @param y1
     *            int
     */
    public void line(int x0, int y0, int x1, int y1) {
        if (pen.getVisible()) {
            penPrepare(pen, g);
            g.drawLine(x0, y0, x1, y1);
            moveTo(x1, y1);
        }
    }

    /**
     * Draws a Vertical Line from (X,Top) to (X,Bottom).
     * 
     * @param x
     *            int
     * @param top
     *            int
     * @param bottom
     *            int
     */
    public void verticalLine(int x, int top, int bottom) {
        if (pen.getVisible()) {
            penPrepare(pen, g);
            g.drawLine(x, top, x, bottom);
            moveTo(x, bottom);
        }
    }

    /**
     * Draws a Horizontal Line.
     * 
     * @param left
     *            int
     * @param right
     *            int
     * @param y
     *            int
     */
    public void horizontalLine(int left, int right, int y) {
        if (pen.getVisible()) {
            penPrepare(pen, g);
            g.drawLine(left, y, right, y);
            moveTo(right, y);
        }
    }

    /**
     * Draws Bezier splines for the Point array p.
     * 
     * @param p
     *            Point[]
     */
    public void drawBeziers(final Point[] p) {
        penPrepare(pen, g);
        
        drawBeziers(0, p);

        /** @todo FINISH !! */
        // g.drawBeziers(p);
    }

    /**
     * Draws a rectangle with rounded corners of personalized curvature and
     * size.
     * 
     * @param r
     *            Rectangle
     * @param roundWidth
     *            int
     * @param roundHeight
     *            int
     */
    public void roundRectangle(final Rectangle r, int roundWidth, int roundHeight) {
        roundRectangle(r, roundWidth, roundHeight, g);
    }

    public void rectangle(final ChartBrush b, final Rectangle r) {
        brushPrepare(b, g, r);
        g.fill(r);
    }

    protected void drawBrushImage(final Rectangle r) {
        final Rectangle t = new Rectangle(r);

        if (brush.getImageMode() == ImageMode.TILE) {
            brushPrepare(brush, g, brush.getImageRect());
            g.fill(r);
        } else if (brush.getImageMode() == ImageMode.STRETCH) {
            brushPrepare(brush, g, r);
            g.fill(r);
        }
        /*
         * if ((brush.getImageMode() == ImageMode.NORMAL) ||
         * (brush.getImageMode() == ImageMode.CENTER)) { brush.setSolid(true);
         * brushPrepare(brush, g, new Rectangle(0,0,
         * brush.getImage().getWidth(null), brush.getImage().getHeight(null)));
         * //brushPrepare(brush, g, r) brush.setSolid(false); }
         */

        draw(r, brush.getImage(), brush.getImageMode(), brush.getImageTransparent());
    }

    /*
     * Internal use. Corrects negatively orientated rectangles
     * TJ71015368 Swing paint issue
     */
    private Rectangle invertNegativeRect(Rectangle r) {
        if (r.width < 0) {
            int tmpL = r.getLeft();
            r.setLeft(r.getRight());
            r.setRight(tmpL);
        }
        if (r.height < 0) {
            int tmpT = r.getTop();
            r.setTop(r.getBottom());
            r.setBottom(tmpT);
        }
        return r;
    }

    /**
     * Draws a Rectangle (Rectangle r).
     * 
     * @param r Rectangle
     */
    public void rectangle(final Rectangle rect) {
        Rectangle r = invertNegativeRect(rect);  //TJ71015368
        if (brush.visible) {
            if (brush.getImage() != null) {
                drawBrushImage(r);
            } else {
                brushPrepare(brush, g, r);
                g.fill(r);
            }
        }

        if (pen.visible) {
            penPrepare(pen, g);
            r.width--;
            r.height--;
            if (r.width < 0) {
                r.width = -r.width;
                r.x = r.x - r.width;
            }
            if (r.height < 0) {
                r.height = -r.height;
                r.y = r.y - r.height;
            }
            g.draw(r);
        }
    }

    /**
     * Use Arc to draw an elliptically curved line with the current Pen.
     * 
     * @param x1
     *            int
     * @param y1
     *            int
     * @param x2
     *            int
     * @param y2
     *            int
     * @param startAngle
     *            double
     * @param sweepAngle
     *            double
     */
    public void arc(int x1, int y1, int x2, int y2, final double startAngle,
            final double sweepAngle) {
        if (pen.visible) {
            penPrepare(pen, g);
            g.drawArc(x1, y1, x2-x1, y2-y1, Utils.round(startAngle), Utils.round(sweepAngle));
        }
    }

    // CDI GaugesSeries
    /**
     * Draws an arc in the rectangle defined by the 4 co-ordinates.
     * 
     * @param x1
     *            int
     * @param y1
     *            int
     * @param x2
     *            int
     * @param y2
     *            int
     * @param x3
     *            int
     * @param y3
     *            int
     * @param x4
     *            int
     * @param y4
     *            int
     */
    public void arc(int x1, int y1, int x2, int y2, int x3, int y3, int x4,
            int y4) {
        if (pen.visible) {
            double start = 0, sweep = 0;
            super.calcArcAngles(x1, y1, x2, y2, x3, y3, x4, y4, start, sweep);
            arc(x1, y1, x2, y2, start, sweep);
        }
    }

    // CDI DrawZone
    protected void transparentEllipse(int x1, int y1, int x2, int y2) {
        final Rectangle r = Rectangle.fromLTRB(x1, y1, x2, y2);

        /** @todo WHAT ????!!!!! (use brush.color transparency = 100) */
        brush.color = chart.getPanel().getColor();
        brushPrepare(brush, g, r);
        g.fillOval(r.x, r.y, r.width, r.height);
    }

    /**
     * Draws an Ellipse between the 2 co-ordinates.
     * 
     * @param x1
     *            int
     * @param y1
     *            int
     * @param x2
     *            int
     * @param y2
     *            int
     */
    public void ellipse(int x1, int y1, int x2, int y2) {
        final Rectangle r = Rectangle.fromLTRB(x1, y1, x2, y2);

        if (brush.visible) {
            brushPrepare(brush, g, r);
            g.fillOval(r.x, r.y, r.width, r.height);
        }

        if (pen.visible) {
            penPrepare(pen, g);
            g.drawOval(r.x, r.y, r.width, r.height);
        }
    }

    public void roundRectangle(final Rectangle r) {
        if (brush.getVisible()) {
            brushPrepare(brush, g, r);
            g.fillRoundRect(r.x, r.y, r.width, r.height, 8, 8);
        }
        if (pen.visible) {
            penPrepare(pen, g);
            g.drawRoundRect(r.x, r.y, r.width, r.height, 8, 8);
        }
    }
    transient private Dimension drawSize = new Dimension(0, 0);

    protected void drawString(int x, int y, final String text, final ChartBrush brush) {
        g.setFont(font.getDrawingFont(chart.getAspect().getFontZoom()));
        g.setColor(brush.getColor());

        measureString(drawSize, font, text);

        brushPrepare(brush, g, new Rectangle(x, y, drawSize.width, drawSize.height));

        if (getTextAlign() == StringAlignment.CENTER) {
            x -= (drawSize.width / 2);
        } else if (getTextAlign() == StringAlignment.FAR) {
            x -= drawSize.width;
        }

        y += drawSize.height - 2;

        g.drawString(text, x, y);
    }

    public void textOut(int x, int y, final String text) {
        super.textOut(x, y, text);

        if (font.hasOutline()) {
            penPrepare(font.getOutline(), g);

            final Font f = font.getDrawingFont(chart.getAspect().getFontZoom());
            g.setFont(f);

            final TextLayout tl = new TextLayout(text, f, g.getFontRenderContext());
            measureString(drawSize, font, text);

            final AffineTransform t = new AffineTransform();
            t.setToTranslation(x, y + drawSize.getHeight() - 2);
            g.draw(tl.getOutline(t));
        }
    }

    /**
     * Creates a rectangular Windows GDI clipping region.<br>
     * ClipRectangle creates a Windows GDI clipping region and selects it into
     * TChart.Drawing Canvas device context handle. <br>
     * Clipping regions are used to prevent a drawing escaping from desired
     * polygon coordinates. <br>
     * The UnClipRectangle method removes any clipping region applied to TChart
     * Drawing Canvas. <br>
     * You can use this method to avoid Series or custom drawing to go over the
     * desired rectangle coordinates.<br>
     * <nr> <b>Note:</b> Printing and creating metafiles do not accept clipping
     * regions in logical pixels.
     * 
     * @param r
     *            Rectangle
     */
    public void clipRectangle(Rectangle r) {
        g.clip(correctRectangle(r));
    }

    /**
     * Creates a Polygonal Windows GDI clipping region.<br>
     * ClipPolygon creates a Windows GDI clipping region and selects it into
     * TChart.Drawing Canvas device context handle. <br>
     * Clipping regions are used to prevent a drawing escaping from desired
     * polygon coordinates. <br>
     * The UnClipRectangle method removes any clipping region applied to TChart
     * Drawing Canvas. <br>
     * You can use this method to avoid Series or custom drawing to go over the
     * desired rectangle coordinates.<br>
     * <nr> <b>Note:</b> Printing and creating metafiles do not accept clipping
     * regions in logical pixels.
     * 
     * @param p
     *            Point[]
     */
    public void clipPolygon(Point[] p) {
        g.clip(getPolygon(p));
    }

    /**
     * Removes any clipping region applied to Chart Drawing.
     */
    public void unClip() {
        // overpaint on scrolled frame apps requires
        // working solution that does not reflect ideal,
        // unlimited unClip. Use of setClip causes
        // problems with overall clipped bounds

        //what to do ....

//		int tmpHeight = chart.getChartBounds().height;
//		int tmpWidth = chart.getChartBounds().width;
//		java.awt.Rectangle viewRect = g.getClipBounds();
//
//		if (g.getClipBounds().getY() + g.getClipBounds().getHeight() < chart
//				.getChartRect().getY() + chart.getChartRect().getHeight()) {
//			tmpHeight = (int) (viewRect.getHeight() + viewRect.getY());
//		}
//
//		if (g.getClipBounds().getX() + g.getClipBounds().getWidth() < chart
//				.getChartRect().getX() + chart.getChartRect().getWidth()) {
//			tmpWidth = (int) (viewRect.getWidth() + viewRect.getX());
//		}
//
//		java.awt.Rectangle newR = new java.awt.Rectangle(0, 0,
//				(int) (tmpWidth), (int) (tmpHeight));

//		g.setClip(newR);

        g.setClip(0, 0, (int) chart.getChartBounds().getWidth()
                - chart.getPanel().getBevelWidth(),
                (int) chart.getChartBounds().getHeight()
                - chart.getPanel().getBevelWidth());
    }

    /**
     * Creates a Elliptical Windows GDI clipping region.<br>
     * ClipEllipse creates a Windows GDI clipping region and selects it into
     * TChart.Drawing Canvas device context handle. <br>
     * Clipping regions are used to prevent a drawing escaping from desired
     * polygon coordinates. <br>
     * The UnClipRectangle method removes any clipping region applied to TChart
     * Drawing Canvas. <br>
     * You can use this method to avoid Series or custom drawing to go over the
     * desired rectangle coordinates.<br>
     * <nr> <b>Note:</b> Printing and creating metafiles do not accept clipping
     * regions in logical pixels.
     * 
     * @param r
     *            Rectangle
     */
    public void clipEllipse(Rectangle r) {
        Ellipse2D.Double tmp = new Ellipse2D.Double(r.x, r.y, r.width, r.height);
        g.clip(tmp);
    }

    /**
     * Draws a rotated text String at the specified xy coordinates with the
     * RotDegree rotation angle.<br>
     * RotDegree values must be between 0 and 360. The string is drawn on the
     * Chart Drawing Canvas.
     * 
     * @param x
     *            int
     * @param y
     *            int
     * @param text
     *            String
     * @param rotDegree
     *            double
     */
    public void rotateLabel(int x, int y, String text, double rotDegree) {
        AffineTransform old = g.getTransform();
        g.rotate(-Math.toRadians(rotDegree), x, y);
        textOut(x, y, text);
        g.setTransform(old);
    }
    AffineTransform oldTransformState;

    public void rotateTransform(Point p, double rotDegree) {
        oldTransformState = g.getTransform();
        g.rotate(-Math.toRadians(rotDegree), p.x, p.y);
    }

    public void resetTransform() {
        if (oldTransformState != null) {
            g.setTransform(oldTransformState);
        }
        oldTransformState = null;
    }

    /**
     * Sets the value of PenPos to x and y co-ordinates before calling LineTo.
     * 
     * @param x
     *            int
     * @param y
     *            int
     */
    public void moveTo(int x, int y) {
        currentX = x;
        currentY = y;
    }

    /**
     * Draws a Pie slice using StartAngle and End Angle.
     * 
     * @param x1
     *            int
     * @param y1
     *            int
     * @param x2
     *            int
     * @param y2
     *            int
     * @param startAngle
     *            double
     * @param endAngle
     *            double
     */
    public void pie(int x1, int y1, int x2, int y2, double startAngle,
            double endAngle) {
        int start = -Utils.round(startAngle);
        int end = -Utils.round(endAngle - startAngle);

        Rectangle r = Rectangle.fromLTRB(x1, y1, x2, y2);
        Arc2D.Double arc = new Arc2D.Double(r, start, end, Arc2D.PIE);

        if (brush.visible) {
            brushPrepare(brush, g, r);
            g.fill(arc);
        }

        if (pen.visible) {
            penPrepare(pen, g);
            g.draw(arc);
        }
    }

    /**
     * Clears background defined for Rectangle Left, Top, Right, Bottom.
     * 
     * @param left
     *            int
     * @param top
     *            int
     * @param right
     *            int
     * @param bottom
     *            int
     */
    public void eraseBackground(int left, int top, int right, int bottom) {
        Rectangle r = Rectangle.fromLTRB(left, top, right, bottom);
        brushPrepare(brush, g, r);
        g.fill(r);
    }

    public void fillRectangle(ChartBrush brush, int x, int y, int width,
            int height) {
        g.setColor(brush.color);
        g.fillRect(x, y, width, height);
    }

    /**
     * Sets the 3D pixel to Color, using Pen Width.
     * 
     * @param x
     *            int
     * @param y
     *            int
     * @param z
     *            int
     * @param color
     *            Color
     */
    public void pixel(int x, int y, int z, Color color) {
        if (pen.getVisible()) {
            penPrepare(pen, g);
            g.setColor(color);
            Point p = calc3DPos(x, y, z);
            g.drawLine(p.x, p.y, p.x + 1, p.y + 1);
        }
    }

    /**
     * Draws a series of line segments to join point array p.
     * 
     * @param p
     *            Point[]
     */
    public void polyLine(Point[] p) {
        if (pen.getVisible()) {
            penPrepare(pen, g);
            int l = p.length;
            for (int t = 1; t < l; t++) {
                g.drawLine(p[t - 1].x, p[t - 1].y, p[t].x, p[t].y);
            }
        }
    }

    /*
     * Fills Arcbound Pie segment
     */
    public boolean fillPieSegment(int x1, int y1, int x2, int y2, int sd,
            int rd, int cl) {
        brushPrepare(brush, g, new Rectangle(0, 0, 0, 0));
        java.awt.geom.Arc2D.Float arc1 = new java.awt.geom.Arc2D.Float(x1, y1,
                x2, y2, sd, rd, cl);
        g.fill(arc1);
        g.draw(arc1);
        arc(new Rectangle(x1, y1, x2, y2), sd, rd);
        return true;
    }

    /**
     * Draws a polygon using the specified GDI brush.
     * 
     * @param brush
     *            ChartBrush
     * @param points
     *            Point[]
     */
    public void polygon(ChartBrush brush, Point[] points) {
        brushPrepare(brush, g, points);
        g.fillPolygon(getPolygon(points));
    }

    /**
     * Draws a polygon.
     * 
     * @param p
     *            Point[]
     */
    public void polygon(Point[] p) {
        Polygon tmp = getPolygon(p);

        if (brush.visible) {
            brushPrepare(brush, g, p);
            g.fill(tmp);
        }

        if (pen.visible) {
            penPrepare(pen, g);
            g.draw(tmp);
        }
    }

    public void polygon(final com.steema.teechart.PointDouble[] points) {

        final Point[] p = PointDouble.round(points);
        polygon(p);
    }
    
    /**
     * Paints the image in rectangle r.<br>
     * It can also be used to Print the Chart in a customized way: <br>
     * Or you can use the TeeChart Draw method to render an image at any
     * location.
     * 
     * @param r
     *            Rectangle
     * @param image
     *            Image
     * @param transparent
     *            boolean
     * @param smoothed
     *            boolean
     */
	
	public void draw(Rectangle r, Image image, boolean transparent, boolean smoothed) {
		if (transparent) {
			// ImageAttributes imageAttr = new ImageAttributes();
			// Bitmap bmp = new Bitmap(image);
			// Color backColor = bmp.GetPixel(1, 1);
			// imageAttr.SetColorKey(backColor, backColor);
		}

		if (!smoothed) {
			g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_SPEED);
		}

		g.drawImage(ImageUtils.bufferedFrom(image), r.x, r.y, r.width, r.height, null);
	}

    /**
     * Paints the image at x,y coordinates.<br>
     * It can also be used to Print the Chart in a customized way: <br>
     * Or you can use the TeeChart Draw method to render an image at any
     * location
     * 
     * @param x
     *            int
     * @param y
     *            int
     * @param image
     *            Image
     */
    public void draw(int x, int y, Image image) {
        g.drawImage(ImageUtils.bufferedFrom(image), x, y, null);
    }

    public static boolean hasAlpha(Image image) {
        return image.hasAlpha();
    }

    /*
    public java.awt.Image bufferedFrom(Image image, Color foreground) {
    int tmpType = hasAlpha(image) ? BufferedImage.TYPE_INT_ARGB
    : BufferedImage.TYPE_INT_RGB;
    
    int width = -1;
    int height = -1;
    
    while (width == -1 || height == -1) {
    width = image.getWidth(null);
    height = image.getHeight(null);
    }
    
    java.awt.Image b = new BufferedImage(width, height, tmpType);
    Graphics g = b.getGraphics();
    try {
    g.drawImage(image, 0, 0, foreground, null);
    } finally {
    g.dispose();
    }
    return b;
    }
     */
    public Shape getChartPolygon(Rectangle rect, int minZ, int maxZ) {

        if (is3D) {
            // Aspect a = aspect;
            // if ( a.Elevation==270 )
            // {
            // if ( (a.Rotation==270) || (a.Rotation==360) )
            // {
            Point tmpLT = calc3DPoint(rect.x, rect.y, minZ);
            Point tmpRB = calc3DPoint(rect.getRight(), rect.y, maxZ);
            // //ClipRectangle(tmpLT.x,tmpLT.y,tmpRB.x,tmpRB.y);
            // return new Region(new
            // Rectangle(tmpLT.x,tmpLT.y,tmpRB.x-tmpLT.x,tmpRB.y-tmpLT.y));
            // }
            // }
            //
            // if (a.Orthogonal)
            // {
            // if (a.OrthoAngle>90) ClipToLeft(rect,minZ,maxZ);
            // else ClipToRight(rect,minZ,maxZ);
            // }
            // else
            // if (a.Rotation>=270) ClipToRight(rect,minZ,maxZ);
            return new Rectangle(tmpLT.x, tmpLT.y, tmpRB.x - tmpLT.x, tmpRB.y
                    - tmpLT.y);
        } else {
            // ClipRectangle(rect.x+1,rect.y+1,rect.Right-1,rect.Bottom-1);
            return new Rectangle(rect.x + 1, rect.y + 1, rect.getRight() - 1
                    - rect.x + 1, rect.getBottom() - 1 - rect.y + 1);
        }
    }

    private Shape addRightRegion(Rectangle rect, int minZ, int maxZ) {
        Point[] p = new Point[6];

        p[0] = calc3DPoint(rect.x, rect.getBottom(), minZ);
        p[1] = calc3DPoint(rect.x, rect.y, minZ);

        Point pa = calc3DPoint(rect.x, rect.y, maxZ);
        Point pb = calc3DPoint(rect.getRight(), rect.y, minZ);

        p[2] = (pb.y < pa.y) ? pb : pa;

        p[3] = calc3DPoint(rect.getRight(), rect.y, maxZ);

        pa = calc3DPoint(rect.getRight(), rect.getBottom(), maxZ);
        pb = calc3DPoint(rect.getRight(), rect.y, minZ);

        p[4] = (pb.x > pa.x) ? pb : pa;

        p[5] = calc3DPoint(rect.getRight(), rect.getBottom(), minZ);

        if (p[5].x < p[0].x) {
            p[0].x = p[5].x;
            if (pb.y < p[0].y) {
                p[0].y = pb.y;
            }
        }

        Polygon myPath = new Polygon();

        for (int t = 0; t < p.length; t++) {
            myPath.addPoint(p[t].x, p[t].y);
        }

        return myPath;
        // ClipPolygon(p);
    }

    static private Color specialColor(Color baseColor) {
        if (baseColor == Color.fromArgb(baseColor.getAlpha(), 255, 255, 255)) {
            baseColor = Color.fromArgb(baseColor.getAlpha(), 212, 212, 212);
        }
        if (baseColor == Color.fromArgb(baseColor.getAlpha(), 255, 255, 0)) {
            baseColor = Color.fromArgb(baseColor.getAlpha(), 212, 212, 0);
        }

        return baseColor;
    }

    /**
     * Internal use. 3D spheres
     * 
     * @param x1
     *            int
     * @param y1
     *            int
     * @param x2
     *            int
     * @param y2
     *            int
     */
    protected void sphereEnh(int x1, int y1, int x2, int y2) {
        // Colors and areas
        Color baseColor = brush.getColor();
        brush.setColor(specialColor(baseColor));
        int drawingTransparency = brush.getTransparency();
        Rectangle outerRect = new Rectangle(x1, y1, x2 - x1, y2 - y1); // bounding
        // outer
        // ellipse
        Rectangle innerRect = new Rectangle(outerRect.x
                + (int) (outerRect.width * 0.08), outerRect.y
                + (int) (outerRect.height * 0.03),
                (int) (outerRect.width * 0.84), (int) (outerRect.height * 0.94)); // bounding
        // inner
        // ellipse

        pen.setVisible(false);

        // Main Circle
        Rectangle tmpR = chart.getChartRect();
        Area chartRectRegion = new Area(addRightRegion(tmpR, 0, chart.getAspect().getWidth3D()));
        setClip(chartRectRegion);
        ellipse(outerRect);

        // when rotating Chart, protect
        if ((x2 - x1 < 5) || (y2 - y1 < 5)) {
            unClip();
            return;
        }

        // Bottom Gradient
        Ellipse2D.Double innerGr = new Ellipse2D.Double(outerRect.x,
                outerRect.y, outerRect.width, outerRect.height);
        Area outerRegion = new Area(innerGr);
        outerRegion.intersect(chartRectRegion);
        setClip(outerRegion);
        Area myPath = new Area();
        Rectangle rect = new Rectangle(outerRect.x - (outerRect.width / 10),
                outerRect.y - (outerRect.height / 10), outerRect.width,
                outerRect.height);
        // when rotating Chart, protect
        if ((rect.width < 5) || (rect.height < 5)) {
            unClip();
            return;
        }

        myPath.add(new Area(new Ellipse2D.Double(rect.x, rect.y, rect.width,
                rect.height)));

        // PathGradientBrush myPGBrush = new PathGradientBrush(myPath);

        // Color tmpColor = baseColor.applyBright(128);
        // myPGBrush.centerColor = tmpColor; //CalcGradientColor(baseColor);
        // Color[] mySurroundColor = {Color.TRANSPARENT};
        // myPGBrush.surroundColors = mySurroundColor;
        fillRectangle(getBrush(), outerRect.x, outerRect.y, outerRect.width,
                outerRect.height);

        unClip();
    }

    /**
     * Internal use. Enhanced spheres
     * 
     * @param x1
     *            int
     * @param y1
     *            int
     * @param x2
     *            int
     * @param y2
     *            int
     */
    public void ellipseEnh(int x1, int y1, int x2, int y2) {
        // Colors and areas
        final Color baseColor = brush.getColor();
        brush.setColor(specialColor(baseColor));
        int drawingTransparency = brush.getTransparency();

        final Rectangle outerRect = new Rectangle(x1, y1, (x2 - x1), (y2 - y1)); // bounding
        // outer
        // ellipse
        final Rectangle innerRect = new Rectangle(outerRect.x
                + (int) (outerRect.width * 0.08), outerRect.y
                + (int) (outerRect.height * 0.03),
                (int) (outerRect.width * 0.84), (int) (outerRect.height * 0.94)); // bounding
        // inner
        // ellipse

        pen.setVisible(false);

        // Main Circle
        final Rectangle tmpR = chart.getChartRect();
        final Area chartRectRegion = new Area(addRightRegion(tmpR, 0, chart.getAspect().getWidth3D()));

        setClip(chartRectRegion);

        ellipse(outerRect);

        // when rotating Chart, protect
        if ((x2 - x1 < 5) || (y2 - y1 < 5)) {
            unClip();
            return;
        }

        Ellipse2D.Double innerGr = new Ellipse2D.Double(outerRect.x,
                outerRect.y, outerRect.width, outerRect.height);

        final Area a = new Area(innerGr);
        a.intersect(chartRectRegion);

        setClip(a);

        final Rectangle rect = new Rectangle(outerRect.x
                + (int) (outerRect.width / 10 * 0.5), outerRect.y
                + (outerRect.height / 10 * 4), outerRect.width / 10 * 9,
                outerRect.height / 10 * 7);

        // when rotating Chart, protect
        if ((rect.width < 5) || (rect.height < 5)) {
            unClip();
            return;
        }

        final Area myPath = new Area();
        myPath.add(new Area(new Ellipse2D.Double(rect.x, rect.y, rect.width,
                rect.height)));

        // PathGradientBrush myPGBrush = new PathGradientBrush(myPath);

        // pending - Blend improvements
        // float[] factors={0.0f,0.025f,0.05f,0.1f,0.3f,0.3f};
        // float[] positions={0.0f,0.1f,0.2f,0.3f,0.99f,1.0f};
        // float[] factors={0.0f,0.025f,0.05f,0.1f,0.15f,0.35f,0.4f,0.45f,0.6f};
        // float[] positions={0.0f,0.1f,0.15f,0.2f,0.3f,0.8f,0.85f,0.99f,1.0f};
        // Blend blend=new Blend();
        // blend.Positions=positions;
        // blend.Factors=factors;
        // myPGBrush.Blend=blend;

        // Color tmpColor = baseColor.applyBright(128);
        // tmpColor = tmpColor.applyBright(128);
        // myPGBrush.centerColor = tmpColor; //CalcGradientColor(baseColor);
        // Color[] mySurroundColor = {Color.TRANSPARENT};
        // myPGBrush.surroundColors = mySurroundColor;
        fillRectangle(getBrush(), outerRect.x, outerRect.y, outerRect.width,
                outerRect.height);

        unClip();

        // Top Gradient
        brush.setTransparency(0);
        brush.getGradient().setVisible(true);
        brush.getGradient().setStartColor(Color.WHITE);
        brush.getGradient().setMiddleColor(Color.TRANSPARENT);
        brush.getGradient().setEndColor(Color.TRANSPARENT);

        innerGr = new Ellipse2D.Double(innerRect.x, innerRect.y,
                innerRect.width, innerRect.height);

        final Area rectRegion = new Area(new Rectangle(innerRect.x, innerRect.y,
                innerRect.width, (int) (innerRect.height * 0.7)));
        final Area innerRegion = new Area(innerGr);

        innerRegion.intersect(rectRegion);
        innerRegion.intersect(chartRectRegion);
        setClip(innerRegion);

        rectangle(innerRect.x, innerRect.y, innerRect.getRight(), innerRect.y
                + (int) (innerRect.height * 0.9)); // shorten to bringup
        // gradient

        unClip();
    }

	public int sizeToDIP(float value) {
		return (int) value;
	}

	public int sizeToUnit(int unit, float value) {
		return (int) value;
	}
}
