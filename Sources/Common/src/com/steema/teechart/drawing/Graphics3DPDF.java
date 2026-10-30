package com.steema.teechart.drawing;

import com.steema.teechart.Dimension;
import com.steema.teechart.Rectangle;
import com.steema.teechart.IBaseChart;
import com.steema.teechart.Aspect;

import com.steema.teechart.exports.PDFData;
import com.steema.teechart.exports.PDFFonts;
import com.steema.teechart.exports.PDFData.PDFSegmentList;
import com.steema.teechart.exports.PDFData.PDFImages;

import com.steema.teechart.drawing.Color;
import com.steema.teechart.misc.MemoryOutputStream;

import java.io.OutputStream;
import java.io.IOException;
import java.text.DecimalFormat;

/**
 *
 * <p>Title: Graphics3DPDF class</p>
 *
 * <p>Description: Summary description for Graphics3DPDF.</p>
 *
 * <p>Copyright (c) 2005-2019 by Steema Software SL. All Rights
 * Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 *
 */
@SuppressWarnings("serial")
public class Graphics3DPDF extends Graphics3DVectorial {

    private int fX, fY;
    private MemoryOutputStream iStream;
    private PDFFonts fonts;
    private PDFImages images;
    private String m_String;
    private int iHeight; //, iWidth;

    private PDFSegmentList segments;
    private int prevSegmentEnd = 0;

    private DecimalFormat format = new DecimalFormat("0.00");
    static private DecimalFormat format3 = new DecimalFormat("0.000");

    public Graphics3DPDF(PDFData pdfdata, IBaseChart c) {

        super(c);

        setUseBuffer(false);
        iStream = pdfdata.getStream();
        fonts = pdfdata.getFonts();
        segments = pdfdata.getSegments();
    }

    public void initWindow(Aspect a, Rectangle r, int maxDepth) {
        super.initWindow(a, r, maxDepth);
        theBounds();
    }

    public void rotateTransform(Point p, double rotDegree) {}
    public void resetTransform(){}

	public int sizeToDIP(float value) {
		return (int) value;
	}

	public int sizeToUnit(int unit, float value) {
		return (int) value;
	}

    public void changed(Object o) {
    }

    private void theBounds() {
        //iWidth = getChart().getWidth();
        iHeight = getChart().getHeight();
    }

    private double trVertCoord(double y) {
        return iHeight - y;
    }

    private int trVertCoord(int y) {
        return iHeight - y;
    }

    static private double fontDPI() {
        return 72.0F / 96.0F;
    }

    public void fillRectangle(ChartBrush brush,int x,int y,int width,int height){
        /** @todo FINISH ! */
    }

    static private String fixSeparator(String text) {
        return text.replace(',', '.');
    }

    public void setPixel(int x,int y, int z, Color color) {
        /** @todo FINISH ! */
    }

    public void draw(Rectangle rect, Image image, boolean transparent, boolean smoothed) {
        /** @todo FINISH ! */
    }

    public void resetState() {
        reset();
    }

    private String pdfColor(Color c) {
        double rfactor = c.getRed() / 255.0F;
        double gfactor = c.getGreen() / 255.0F;
        double bfactor = c.getBlue() / 255.0F;
        m_String = format.format(rfactor) + " " +
                   format.format(gfactor) + " " +
                   format.format(bfactor);
        return fixSeparator(m_String);
    }

    static private String penStyle(DashStyle style) {
        String res;
        if (style == DashStyle.DASH) {
            res = "[3 3] 0 d";
        } else
        if (style == DashStyle.DOT) {
            res = "[2] 1 d";
        } else
        if (style == DashStyle.DASHDOT) {
            res = "[3 2] 2 d";
        } else
        if (style == DashStyle.DASHDOTDOT) {
            res = "[3 2 2 2 2] 2 d";
        } else {
            res = "[ ] 0 d";
        }

        return res;
    }

    private String penProperties(ChartPen apen) {
        return pdfColor(apen.getColor()) + " RG " +
                Integer.toString(apen.getWidth()) + " w " +
                penStyle(apen.getStyle());
    }

    private String brushProperties(ChartBrush abrush) {
        return pdfColor(abrush.getColor()) + " rg";
    }

    static private void writeToStream(OutputStream astream, String text) {
        byte[] buff = text.getBytes(); // Encoding.ASCII.GetBytes(text);

        try {
            astream.write(buff); //, 0, buff.length);
        } catch (IOException e) {
        }

    }

    @Override
    public String pointToStr(double x, double y) {
        y = trVertCoord(y);
        m_String = format.format(x) + " " +
                   format.format(y);
        return fixSeparator(m_String);
    }

    private String pointToStr(int x, int y) {
        return pointToStr((double) x, (double) y);
    }

    private void internalRect(ChartBrush b, Rectangle r, boolean usePen) {
        if (b.getVisible() || (usePen && getPen().getVisible())) {
            int tB = r.getBottom();
            tB = trVertCoord(tB);
            m_String = penProperties(getPen()) + " " + brushProperties(b) + " ";
            m_String += format.format(r.getLeft()) + " " +
                    format.format(tB) +
                    " " +
                    format.format(r.width) + " " +
                    format.format(r.height) +
                    " re";
            m_String = fixSeparator(m_String);
            writeToStream(iStream, m_String);

            if (b.getVisible()) {
                if (getPen().getVisible()) {
                    writeToStream(iStream, " B\n");
                } else {
                    writeToStream(iStream, " f\n");
                }
            } else {
                writeToStream(iStream, " S\n");
            }
        }
    }

    static private String internalBezCurve(double ax1, double ay1, double ax2,
                                           double ay2,
                                           double ax3, double ay3) {
        return format3.format(ax1) + " " +
                format3.format(ay1) + " " +
                format3.format(ax2) + " " +
                format3.format(ay2) + " " +
                format3.format(ax3) + " " +
                format3.format(ay3) + " c\n";
    }

    private void internalPolygon(ChartBrush br, ChartPen pn, boolean polyLine,
                                 Point[] p) {
        if (br.getVisible() || pn.getVisible()) {
            if (getPen().getVisible()) {
                writeToStream(iStream, penProperties(pn) + " ");
            }
            writeToStream(iStream, pointToStr(p[0].x, p[0].y) + " m\n");
            for (int i = 1; i <= p.length; i++) {
                writeToStream(iStream, pointToStr(p[i].x, p[i].y) + " l\n");
            }
            if (!polyLine) {
                writeToStream(iStream, "h ");
            }

            if (br.getVisible() && !polyLine) {
                writeToStream(iStream, brushProperties(br));
                if (pn.getVisible()) {
                    writeToStream(iStream, " B\n");
                } else {
                    writeToStream(iStream, " f\n");
                }
            } else {
                writeToStream(iStream, " S\n");
            }
        }
    }

    static private Point.Double rotate(double ax, double ay, double angle) {
        double vcos = Math.cos(angle);
        double vsin = Math.sin(angle);
        double tx = ax;
        double ty = ay;
        ax = vcos * tx - vsin * ty;
        ay = vsin * tx + vcos * ty;
        return new Point.Double(ax, ay);
    }

    private void arcSegment(double ax, double ay, double ra, double rb,
                            double midtheta, double hangle, int amt0,
                            double fccwc) {
        double htheta = Math.abs(hangle);
        double vcos = Math.cos(htheta);
        double vsin = Math.sin(htheta);

        if (ra < rb) {
            double temp = ra;
            ra = rb;
            rb = temp;
        }

        double ax0 = ra * vcos;
        double ay0 = -fccwc * ra * vsin;
        Point.Double p = rotate(ax0, ay0, midtheta);
        double t1 = ax + p.x;
        double t2 = ay + p.y;

        if (amt0 == 1) {
            m_String = format3.format(t1) + " " +
                       format3.format(t2) +
                       " m\n";
        } else if (amt0 == 0) {
            m_String = format3.format(t1) + " " +
                       format3.format(t2) +
                       " l\n";
        } else {
            m_String = "";
        }

        double ax1 = ra * (4.0F - vcos) / 3.0F;
        double ax2 = ax1;
        double ay1 = ra * fccwc * (1.0F - vcos) * (vcos - 3.0F) / (3.0F * vsin);
        double ay2 = -ay1;
        double ax3 = ra * vcos;
        double ay3 = fccwc * ra * vsin;
        Point.Double p1 = rotate(ax1, ay1, midtheta);
        Point.Double p2 = rotate(ax2, ay2, midtheta);
        Point.Double p3 = rotate(ax3, ay3, midtheta);
        m_String +=
                internalBezCurve(ax + p1.x, ay + p1.y, ax + p2.x, ay + p2.y,
                                 ax + p3.x,
                                 ay + p3.y);
        m_String = fixSeparator(m_String);
        writeToStream(iStream, m_String);
    }


    private static final double PIDIV180 = -Math.PI / 180.0F;

    private void internalArc(ChartBrush br, ChartPen pn, int x1, int y1, int x2,
                             int y2, double startAngle, double sweepAngle,
                             int moveto0, boolean drawpie) {
        if (br.getVisible() || pn.getVisible()) {
            writeToStream(iStream, penProperties(pn));
            if (br.getVisible() && drawpie) {
                writeToStream(iStream, " " + brushProperties(br) + "\n");
            } else {
                writeToStream(iStream, " ");
            }

            // center pos + radius
            double xc = (x1 + x2) * 0.5F;
            double yc = (y1 + y2) * 0.5F;
            double a = (x2 - x1) * 0.5F;
            double b = (y2 - y1) * 0.5F;

            //  this is only approx. algorithm
            if (a != b) {
                writeToStream(iStream, "q\n");
                double rat, tr;
                if (a > b) {
                    rat = b / a;
                    tr = yc * (1.0F - rat);
                    m_String = "1 0 0 " + format3.format(rat) +
                               " 0 " +
                               format3.format(tr);
                } else {
                    rat = a / b;
                    tr = xc * (1.0F - rat);
                    m_String = format3.format(rat) +
                               " 0 0 1 " +
                               format3.format(tr) + " 0";
                }
                m_String = fixSeparator(m_String);
                m_String += " cm\n";
                writeToStream(iStream, m_String);
            }

            // NET uses sligtly different angle, so...
            // NOTE : check if this is correct
            /*
                     startAngle = (360.0F-startAngle)*(double)Math.PI/180.0F;
                     sweepAngle *= -(double)Math.PI/180.0F;
             */

            startAngle *= PIDIV180;
            sweepAngle *= PIDIV180;
            if (startAngle < 0) {
                startAngle += 2.0F * Math.PI;
            }

            double endAngle = startAngle + sweepAngle;
            if (endAngle < 0) {
                endAngle += 2.0F * Math.PI;
            }

            if (drawpie) {
                writeToStream(iStream, pointToStr(xc, yc) + " m\n");
            }

            yc = trVertCoord(yc);
            double fccwc = 1.0F;
            int segCount = 1;
            if (endAngle < startAngle) {
                fccwc = -1.0F;
            } while (Math.abs(sweepAngle) / segCount > Math.PI * 0.5F) {
                segCount++;
            }

            double angleBump = sweepAngle / segCount;
            double hBump = 0.5F * angleBump;
            double currAngle = startAngle + hBump;

            for (int i = 0; i < segCount; i++) {
                if (i == 0) {
                    arcSegment(xc, yc, a, b, currAngle, hBump, moveto0, fccwc);
                } else {
                    arcSegment(xc, yc, a, b, currAngle, hBump, -1, fccwc);
                }
                currAngle += angleBump;
            }

            if (br.getVisible() && drawpie) {
                if (pn.getVisible()) {
                    writeToStream(iStream, " h B\n");
                } else {
                    writeToStream(iStream, " h f\n");
                }
            } else if (drawpie) {
                writeToStream(iStream, " s\n");
            } else if (!drawpie) {
                writeToStream(iStream, " S \n");
            }
            if (a != b) {
                writeToStream(iStream, "Q\n");
            }
        }
    }

    static private String textToPDFText(String text) {
        text = text.replaceAll("\\", "\\\\");
        text = text.replaceAll("(", "\\(");
        text = text.replaceAll(")", "\\)");
        return text;
    }

    private void doText(double x, double y, String text, double rotrad, Color c) {
        writeToStream(iStream, pdfColor(c) + " rg ");
        writeToStream(iStream, "BT ");
        int fontIndex = fonts.findFont(font);

        segments.add(iStream.getCount() - prevSegmentEnd,
                     fontIndex + 1, fonts.get(fontIndex).getDictFontName().length());
        prevSegmentEnd = iStream.getCount();
        writeToStream(iStream,
                      " " + Integer.toString(font.getSize()) + " Tf ");

        // Get text width and height
        double tw = 0.0F;

        if (getTextAlign() == StringAlignment.CENTER) {
            tw = textWidth(text) * 0.5F;
        } else if (getTextAlign() == StringAlignment.FAR) {
            tw = textWidth(text);
        }

        double th = textHeight(text) / fontDPI();
        y -= 0.5F * th;
        y = trVertCoord(y);

        double vcos = Math.cos(rotrad);
        double vsin = Math.sin(rotrad);
        // need lower left, NOT upper left, so:
        double xc = x - (tw * vcos - th * vsin);
        double yc = y - (tw * vsin + th * vcos);

        m_String = format3.format(vcos) + " " +
                   format3.format(vsin) + " " +
                   format3.format((vsin * -1)) +
                   format3.format(vsin) + " " +
                   format3.format(vcos) + " " +
                   format3.format(xc) + " " +
                   format3.format(yc) + " Tm ";
        m_String = fixSeparator(m_String);
        writeToStream(iStream, m_String);
        writeToStream(iStream, "(" + textToPDFText(text) + ") Tj ");
        writeToStream(iStream, "ET \n");
    }

    private void internalDraw(double sx, double sy, double tx, double ty,
                              Image image) {
        int imageIndex = images.findImage(image);
        writeToStream(iStream, "q ");
        m_String = format3.format(sx) + " 0 0 ";
        m_String += format3.format(sy) + " " +
                format3.format(tx) + " ";
        m_String += format3.format(ty);
        m_String = fixSeparator(m_String);
        m_String += " cm ";
        writeToStream(iStream, m_String);

        // Object number is not correct yet!
        segments.add(iStream.getCount() - prevSegmentEnd,
                     imageIndex + 1,
                     images.get(imageIndex).getImageName().length());
        prevSegmentEnd = iStream.getCount();

        m_String = " Do Q\n";
        writeToStream(iStream, m_String);
    }

    /**
     * Use Arc to draw an elliptically curved line with the current Pen.
     *
     * @param x1 int
     * @param y1 int
     * @param x2 int
     * @param y2 int
     * @param startAngle double
     * @param sweepAngle double
     */
    public void arc(int x1, int y1, int x2, int y2, double startAngle,
                    double sweepAngle) {
        internalArc(getBrush(), getPen(), x1, y1, x2, y2, startAngle,
                    sweepAngle, 1, false);
    }

    public void arc(int x1, int y1, int x2, int y2, int x3, int y3, int x4,
                    int y4) {
        /** @todo Shouldn't all this be implemented in Graphics3D class ? The same as for Pie method.  */
    }

    static final double BEZ = 0.552F;

    /**
     * Draws an Ellipse between the 2 co-ordinates.
     *
     * @param x1 int
     * @param y1 int
     * @param x2 int
     * @param y2 int
     */
    public void ellipse(int x1, int y1, int x2, int y2) {
        if (getBrush().getVisible() || getPen().getVisible()) {
            writeToStream(iStream,
                          penProperties(getPen()) + " " +
                          brushProperties(getBrush()) +
                          " ");
            double ra = (x2 - x1) * 0.5F;
            double rb = (y2 - y1) * 0.5F;
            double xc = (x1 + x2) * 0.5F;
            double yc = (y1 + y2) * 0.5F;
            yc = trVertCoord(yc);
            double sp = xc + ra;
            m_String = format3.format(sp) + " " +
                       format3.format(yc) +
                       " m ";
            m_String +=
                    internalBezCurve(xc + ra, yc + BEZ * rb, xc + BEZ * ra,
                                     yc + rb, xc, yc + rb);
            m_String +=
                    internalBezCurve(xc - BEZ * ra, yc + rb, xc - ra,
                                     yc + BEZ * rb, xc - ra, yc);
            m_String +=
                    internalBezCurve(xc - ra, yc - BEZ * rb, xc - BEZ * ra,
                                     yc - rb, xc, yc - rb);
            m_String +=
                    internalBezCurve(xc + BEZ * ra, yc - rb, xc + ra,
                                     yc - BEZ * rb, xc + ra, yc);
            m_String = fixSeparator(m_String);
            writeToStream(iStream, m_String);
            if (getBrush().getVisible()) {
                if (getPen().getVisible()) {
                    writeToStream(iStream, " B\n");
                } else {
                    writeToStream(iStream, " f\n");
                }
            } else {
                writeToStream(iStream, " S\n");
            }
        }
    }

    protected void transparentEllipse(int x1, int y1, int x2, int y2) {
        // Not implemented in PDF
    }

    protected void drawString(int x, int y, String text, ChartBrush brush) {
        doText(x, y, text, 0, brush.getColor());
    }

    /**
     * Creates a rectangular Windows GDI clipping region.<br>
     * ClipRectangle creates a Windows GDI clipping region and selects
     * it into TChart.Drawing Canvas device context handle. <br>
     * Clipping regions are used to prevent a drawing escaping from desired
     * polygon coordinates. <br>
     * The UnClipRectangle method removes any clipping region applied to TChart
     * Drawing Canvas. <br>
     * You can use this method to avoid Series or custom drawing to go over
     * the desired rectangle coordinates.<br><nr>
     * <b>Note:</b> Printing and creating metafiles do not accept clipping
     * regions in logical pixels.
     *
     * @param r Rectangle
     */
    public void clipRectangle(Rectangle r) {
        /*
               IClipCalled = true;
               WriteToStream(IStream,"q\n");
               int tB = r.Bottom();
               int tT = r.Top;
               TrVertCoord(ref tB);
               TrVertCoord(ref tT);
         m_String = r.Left.ToString("0.00") + " " + r.Bottom().ToString("0.00") + " " +
         r.Width.ToString("0.00") + " " + r.height.ToString("0.00") + " re W n\n";
               m_String=FixSeparator(m_String);
               WriteToStream(IStream,m_String);
         */
    }

    /**
     * Creates a Polygonal Windows GDI clipping region.<br>
     * ClipPolygon creates a Windows GDI clipping region and selects it into
     * TChart.Drawing Canvas device context handle. <br>
     * Clipping regions are used to prevent a drawing escaping from desired
     * polygon coordinates. <br>
     * The UnClipRectangle method removes any clipping region applied to TChart
     * Drawing Canvas. <br>
     * You can use this method to avoid Series or custom drawing to go over
     * the desired rectangle coordinates.<br><nr>
     * <b>Note:</b> Printing and creating metafiles do not accept clipping
     * regions in logical pixels.
     *
     * @param p Point[]
     */
    public void clipPolygon(Point[] p) {
    }

    /**
     * Removes any clipping region applied to Chart Drawing.
     */
    public void unClip() {
        /*
               {
          WriteToStream(IStream,"Q\n");
          IClipCalled = false;
               }
         */
    }

    /**
     * Creates a Elliptical Windows GDI clipping region.<br>
     * ClipEllipse creates a Windows GDI clipping region and selects it into
     * TChart.Drawing Canvas device context handle. <br>
     * Clipping regions are used to prevent a drawing escaping from desired
     * polygon coordinates. <br>
     * The UnClipRectangle method removes any clipping region applied to TChart
     * Drawing Canvas. <br>
     * You can use this method to avoid Series or custom drawing to go over
     * the desired rectangle coordinates.<br><nr>
     * <b>Note:</b> Printing and creating metafiles do not accept clipping
     * regions in logical pixels.
     *
     * @param r Rectangle
     */
    public void clipEllipse(Rectangle r) {
    }

    /**
     * Draws a rotated text String at the specified xy coordinates with the
     * RotDegree rotation angle.<br>
     * RotDegree values must be between 0 and 360. The string is drawn on the
     * Chart Drawing Canvas.
     *
     * @param x int
     * @param y int
     * @param text String
     * @param rotDegree double
     */
    public void rotateLabel(int x, int y, String text, double rotDegree) {
        ChartFont f = getFont();

        if (f.shouldDrawShadow()) {
            doText(x + f.getShadow().getWidth(),
                   y + f.getShadow().getHeight(), text,
                   rotDegree * 0.01745329, f.getShadow().getColor());
        }

        doText(x, y, text, rotDegree * 0.01745329, f.getColor());
    }

    /**
     * Draws a Pie slice using StartAngle and End Angle.
     *
     * @param x1 int
     * @param y1 int
     * @param x2 int
     * @param y2 int
     * @param startAngle double
     * @param endAngle double
     */
    public void pie(int x1, int y1, int x2, int y2, double startAngle,
                    double endAngle) {
        double sweepAngle = (endAngle - startAngle);
        internalArc(getBrush(), getPen(), x1, y1, x2, y2, startAngle,
                    sweepAngle, 0, true);
    }

    /**
     * Clears background defined for Rectangle Left, Top, Right, Bottom.
     *
     * @param left int
     * @param top int
     * @param right int
     * @param bottom int
     */
    public void eraseBackground(int left, int top, int right, int bottom) {
    }

    /**
     * Fills a Screen area with multi-colored lines to obtain a nice shadow
     * effect and coloured backgrounds.<br><br>
     * The Chart.Gradient component uses this method internally.
     *
     *
     * @param left int
     * @param top int
     * @param right int
     * @param bottom int
     * @param startColor Color
     * @param endColor Color
     * @param direction GradientDirection
     */
    public void gradientFill(int left, int top, int right, int bottom,
                             Color startColor, Color endColor,
                             GradientDirection direction) {
    }

    /**
     * Sets the 3D pixel to OLE_Color, using Pen.<!-- -->Width.
     *
     * @param x int
     * @param y int
     * @param z int
     * @param color Color
     */
    public void pixel(int x, int y, int z, Color color) {
        getPen().setColor(color);
        Point p = calc3DPos(x, y, z);
        moveTo(p);
        lineTo(p);
    }

    /**
     * Draws a series of line segments to join point array p.
     *
     * @param p Point[]
     */
    public void polyLine(Point[] p) {
        internalPolygon(getBrush(), getPen(), true, p);
    }
    
    public void polygon(final com.steema.teechart.PointDouble[] p) {}

    /**
     * Draws a polygon using the specified GDI brush.
     *
     * @param b ChartBrush
     * @param p Point[]
     */
    public void polygon(ChartBrush b, Point[] p) {
    }

    /**
     * Draws a polygon.
     *
     * @param p Point[]
     */
    public void polygon(Point[] p) {
        internalPolygon(getBrush(), getPen(), false, p);
    }
    
    public boolean fillPieSegment(int x1,int y1,int x2,int y2,int sd,int rd,int cl) { return false; }

    /**
     * Paints the image in rectangle r.<br>
     * It can also be used to Print the Chart in a customized way: <br>
     * Or you can use the TeeChart Draw method to render an image at any
     * location.
     *
     * @param r Rectangle
     * @param image Image
     */
    public void draw(Rectangle r, Image image) {
        internalDraw(r.width, r.height, r.x, iHeight - r.getBottom(), image);
    }

    /**
     * Paints the image at x,y coordinates.<br>
     * It can also be used to Print the Chart in a customized way: <br>
     * Or you can use the TeeChart Draw method to render an image at any
     * location.
     *
     * @param x int
     * @param y int
     * @param image Image
     */
    public void draw(int x, int y, Image image) {
        internalDraw(image.getWidth(null), image.getHeight(null), x,
                     iHeight - y - image.getHeight(null),
                     image);
    }

    /**
     * Draws Bezier splines for the Point array p.
     *
     * @param p Point[]
     */
    public void drawBeziers(Point[] p) {
    }

    /**
     * Draws a Horizontal Line.<br>
     *
     * @param left int
     * @param right int
     * @param y int
     */
    public void horizontalLine(int left, int right, int y) {
        moveTo(left, y);
        lineTo(right, y);
    }

    protected void line(ChartPen p, Point a, Point b) {
        moveTo(a.x, a.y);
        m_String = penProperties(p) + " " + pointToStr(fX, fY) + " m " +
                   pointToStr(b.x, b.y) + " l S\n";
        writeToStream(iStream, m_String);
        fX = b.x;
        fY = b.y;
    }

    public void moveTo(int x, int y) {
        fX = x;
        fY = y;
    }

    /**
     * Draws a line from the present PenPos to the new co-ordinates.<br>
     * The Line method has 4 overloads to facilitate the setting of the end
     * co-ordinate using Point p or int x, int y, both with or without a z
     * parameter
     *
     * @param x int
     * @param y int
     */
    public void lineTo(int x, int y) {
        m_String = penProperties(getPen()) + " " + pointToStr(fX, fY) + " m " +
                   pointToStr(x, y) + " l S\n";
        writeToStream(iStream, m_String);
        fX = x;
        fY = y;
    }

    /**
     * Draws a Line between co-ordinates.
     *
     * @param x0 int
     * @param y0 int
     * @param x1 int
     * @param y1 int
     */
    public void line(int x0, int y0, int x1, int y1) {
        moveTo(x0, y0);
        lineTo(x1, y1);
    }

    public void prepareDrawImage() {
    }

    public void rectangle(ChartBrush b, Rectangle r) {
    }

    //protected void drawBrushImage(Rectangle rect) {
    //}

    /**
     * Draws a Rectangle (Rectangle r).
     *
     * @param r Rectangle
     */
    public void rectangle(Rectangle r) {
        internalRect(getBrush(), r, true);
    }

    public void showImage() {
        segments.add(iStream.getCount() - prevSegmentEnd, -1, 0);
    }

    /**
     * Draws a rectangle with rounded corners of personalized curvature and
     * size.
     *
     * @param r Rectangle
     * @param roundWidth int
     * @param roundHeight int
     */
    public void roundRectangle(Rectangle r, int roundWidth, int roundHeight) {
        internalRect(getBrush(), r, true); // TODO : improve this
    }

    /**
     * Draws a Vertical Line from (X,Top) to (X,Bottom).
     *
     * @param x int
     * @param top int
     * @param bottom int
     */
    public void verticalLine(int x, int top, int bottom) {
        moveTo(x, top);
        lineTo(x, bottom);
    }

    public void measureString(Dimension size, ChartFont f, String text) {
        double fdpi = fontDPI();
        super.measureString(size, f, text);
        size.setSize(size.getHeight() * fdpi, size.getWidth() * fdpi);
    }

    public void donut(int xCenter, int yCenter, int xRadius, int yRadius,
                      double startAngle, double endAngle, int donutPercent) {
        /** @todo FINISH ! */
    }

    public void cone(boolean vertical, int left, int top, int right, int bottom,
                     int z0, int z1,
                     boolean darkSides, int conePercent) {
        /** @todo FINISH ! */
    }

    public void sphere(int x, int y, int z, int radius, boolean enhanced) {
        /** @todo FINISH ! */
    }

    public void sphere(int left, int top, int right, int bottom) {
        /** @todo FINISH ! */
    }

    public void ellipse(int left, int top, int right, int bottom,
                        boolean enhanced) {
        /** @todo FINISH ! */
    }

}

