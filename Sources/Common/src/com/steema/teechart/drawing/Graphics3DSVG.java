package com.steema.teechart.drawing;

import com.steema.teechart.Aspect;
import com.steema.teechart.IBaseChart;
import com.steema.teechart.PointDouble;
import com.steema.teechart.Rectangle;
import com.steema.teechart.misc.Utils;
import java.io.OutputStream;

/**
 *
 * <p>Title: Graphics3DSVG class</p>
 *
 * <p>Description: Summary description for Graphics3DSVG.</p>
 *
 * <p>Copyright (c) 2005-2019 by Steema Software SL. All Rights
 * Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 *
 */
public class Graphics3DSVG extends Graphics3DVectorial {

    /**
	 * 
	 */
    private static final long serialVersionUID = 1L;
    private String m_String;
    private int fx, fy;
    private int iClipCount;
    private int iClipStack;
    private final Point iP;
    private final boolean iAppend;

    private void addEnd(String st) {
        addToStream(st + "/>");
    }

    public void resetState() {
        reset();
    }
    
    public void rotateTransform(Point p, double rotDegree) {}
    public void resetTransform(){}

	public int sizeToDIP(float value) {
		return (int) value;
	}

	public int sizeToUnit(int unit, float value) {
		return (int) value;
	}

    public void fillRectangle(ChartBrush brush,int x,int y,int width,int height){
        /** TODO FINISH ! */
    }

    private String fontProperties(ChartFont f) {
        m_String = "font-family=\"" + f.getName().toString() +
                   "\" font-size=\"" +
                   Integer.toString(f.getSize()) + "pt\" ";
        if (f.getBold()) {
            m_String += " font-weight=\"bold\"";
        }
        if (f.getItalic()) {
            m_String += " font-style=\"italic\"";
        }
        if (f.getUnderline()) {
            m_String += " text-decoration=\"underline\"";
        }
        if (f.getStrikeout()) {
            m_String += " text-decoration=\"line-through\"";
        }
        return m_String;
    }

    private void SVGClip() {
        iClipStack++;
        iClipCount++;
        String clipname = "Clip" + Integer.toString(iClipCount);
        addToStream("<g clip-path=\"url(#" + clipname + ")\">");
        addToStream("<defs>");
        addToStream("<clipPath id=\"" + clipname +
                    "\" style=\"clip-rule:nonzero\">");
    }

    private void SVGEndClip() {
        addToStream("</clipPath>");
        addToStream("</defs>");
    }

    private String SVGPen(boolean fillNull) {
        if (getPen().getVisible()) {
            m_String = fillNull ? " fill=\"none\"" : "";        
            m_String += " stroke=" + SVGColor(getPen().getColor());
            if (getPen().getWidth() > 1) {
                m_String += " stroke-width=\"" +
                        Integer.toString(getPen().getWidth()) + "\"";
            }
            if (getPen().getStyle() != DashStyle.SOLID) {
                m_String += " stroke-dasharray=\"" + penStyle(getPen()) + "\" ";
            }

            if (getPen().getEndCap() == LineCap.BEVEL) {
                m_String += " stroke-linecap=\"square\"";
            } else if (getPen().getEndCap() == LineCap.MITER) {
                m_String += " stroke-linecap=\"flat\"";
            }
        } else {
            m_String = " stroke=\"none\"";
        }
        return m_String;
    }

    private String SVGColor(Color c) {
        m_String = "";
        // one of pre-defined html colors
        if (c == Color.black) {
            m_String = "\"black\"";
        } else if (c == Color.SILVER) {
            m_String = "\"silver\"";
        } else if (c == Color.GRAY) {
            m_String = "\"gray\"";
        } else if (c == Color.white) {
            m_String = "\"white\"";
        } else if (c == Color.MAROON) {
            m_String = "\"maroon\"";
        } else if (c == Color.red) {
            m_String = "\"red\"";
        } else if (c == Color.PURPLE) {
            m_String = "\"purple\"";
        } else if (c == Color.FUCHSIA) {
            m_String = "\"fuchsia\"";
        } else if (c == Color.green) {
            m_String = "\"green\"";
        } else if (c == Color.LIME) {
            m_String = "\"lime\"";
        } else if (c == Color.OLIVE) {
            m_String = "\"olive\"";
        } else if (c == Color.yellow) {
            m_String = "\"yellow\"";
        } else if (c == Color.NAVY) {
            m_String = "\"navy\"";
        } else if (c == Color.BLUE) {
            m_String = "\"blue\"";
        } else if (c == Color.TEAL) {
            m_String = "\"teal\"";
        } else if (c == Color.AQUA) {
            m_String = "\"aqua\"";
        } else {
            m_String += "\"rgb(" + Integer.toString(c.getRed()) + ",";
            m_String += Integer.toString(c.getGreen()) + ",";
            m_String += Integer.toString(c.getBlue()) + ")\"";
        }
        return m_String;
    }

    private String penStyle(ChartPen ipen) {
        m_String = "";
        if (ipen.getStyle() == DashStyle.DOT) {
            m_String = "2, 2";
        } else if (ipen.getStyle() == DashStyle.DASH) {
            m_String = "4, 2";
        } else if (ipen.getStyle() == DashStyle.DASHDOT) {
            m_String = "4, 2, 2, 2";
        } else if (ipen.getStyle() == DashStyle.DASHDOTDOT) {
            m_String = "4, 2, 2, 2, 2, 2";
        }

        return m_String;
    }

    private String pointToStr(int x, int y) {
        m_String = Integer.toString(x) + "," + Integer.toString(y);
        return m_String;
    }

    private String theBounds() {
        m_String = "width=\"" + Integer.toString(chart.getChartBounds().width+iP.x) +
                   "px\" ";
        m_String += "height=\"" +
                Integer.toString(chart.getChartBounds().height+iP.y) + "px\"";
        return m_String;
    }

    private String SVGBrushPen(boolean usePen) {
        if (getBrush().getVisible()) {
            m_String = " fill=" + SVGColor(getBrush().getColor());
            float tr = getBrush().getTransparency() * 0.01F;
            if (tr > 0.0F) {
                String trfact = Float.toString(tr);
                trfact = trfact.replace(',', '.');
                m_String += " fill-opacity=\"" + trfact + "\"";
            }
        } else {
            m_String = " fill=\"none\"";
        }

        if (usePen) {
            m_String += SVGPen(false);
        }

        return m_String;
    }

    private void prepareShape() {
        addToStream("<polygon" + SVGBrushPen(true));
    }

    private String SVGRect(Rectangle r) {
        int w = r.width;
        int h = r.height;
        m_String = "x=\"" + Integer.toString(r.getLeft()) + "\" y=\"" +
                   Integer.toString(r.getTop()) +
                   "\" ";
        m_String += " width=\"" + Integer.toString(w) + "\"" + " height=\"" +
                Integer.toString(h) + "\"";
        return m_String;
    }


    private String SVGPoints(Point[] p) {
        m_String = "points=\"";
        for (int i = 0; i < p.length; i++) {
            m_String += pointToStr(p[i].x, p[i].y) + " ";
        }

        m_String += "\"";
        return m_String;
    }
    
        private String SVGPoints(PointDouble[] p) {
        m_String = "points=\"";
        for (int i = 0; i < p.length; i++) {
            m_String += pointToStr(p[i].x, p[i].y) + " ";
        }

        m_String += "\"";
        return m_String;
    }

    private String SVGEllipse(int x1, int y1, int x2, int y2) {
        int cx = (x1 + x2) / 2;
        int cy = (y1 + y2) / 2;
        int rx = (x2 - x1) / 2;
        int ry = (y2 - y1) / 2;
        m_String = "cx=\"" + Integer.toString(cx) + "\" cy=\"" +
                   Integer.toString(cy);
        m_String += "\" rx=\"" + Integer.toString(rx) + "\" ry=\"" +
                Integer.toString(ry) +
                "\"";
        return m_String;
    }

    private void doText(int x, int y, String text, double degangle, Color c) {
        y += Utils.round(1.25 * font.getSize()); // from px to pt

        if (degangle != 0.0) {
            degangle *= -1;
            int dangle = Utils.round(degangle);
            m_String = "<g transform=\"translate(" + Integer.toString(x) + "," +
                       Integer.toString(y) + ") ";
            m_String += "rotate(" + Integer.toString(dangle) + ")\">";
            addToStream(m_String);
            m_String = "<text x=\"0\" y=\"0\" ";
        } else {
            m_String = "<text x=\"" + Integer.toString(x) + "\" y=\"" +
                       Integer.toString(y) +
                       "\" ";
        }
        // font properties
        m_String += fontProperties(font);

        // text alignment
        if (getTextAlign() == StringAlignment.CENTER) {
            m_String += " text-anchor=\"middle\"";
        } else if (getTextAlign() == StringAlignment.FAR) {
            m_String += " text-anchor=\"end\"";
        } else {
            m_String += " text-anchor=\"start\"";
        }

        // color
        m_String += " fill=" + SVGColor(c) + ">";

        addToStream(m_String);
        addToStream(SVGText(text));
        addToStream("</text>");
        if (degangle != 0) {
            addToStream("</g>");
        }
    }
    
    private String SVGText(String text)
    {
      char[] res = text.toCharArray();
      int ccode;
      text = "";
      for(char c : res)
      {
        if (Character.isLetterOrDigit(c))
          text += c;
        else
        {
          ccode = c;
          switch (ccode)
          {
            case 34: text += "&quot;"; break;
            case 38: text += "&amp;"; break;
            case 39: text += "&apos;"; break;
            case 60: text += "&lt;"; break;
            case 62: text += "&gt;"; break;
            default: text += "&#" + Integer.toString(ccode) + ";"; break;
          }
        }
      }
      return text;
    }

    private void internalRect(ChartBrush b, Rectangle r, boolean UsePen,
                              boolean IsRound) {
        if (b.getVisible() || (UsePen && getPen().getVisible())) {
            m_String = "<rect " + SVGRect(r) + SVGBrushPen(UsePen);
            if (IsRound) {
                m_String += " rx=\"5\"";
            }
            addEnd(m_String);
        }
    }
    
    public Graphics3DSVG(OutputStream istream, IBaseChart c, Point p, boolean append, int clip) {
        super(istream, c);
        iP = new Point(p);
        iAppend = append;
        iClipCount = clip;
        
        if (!append) {
            addToStream("<?xml version=\"1.0\" standalone=\"no\"?>");
            docType = "<!DOCTYPE svg PUBLIC \" -//W3C//DTD SVG 1.1//EN\" \"http://www.w3.org/Graphics/SVG/1.1/DTD/svg11.dtd\">";
        }
        
        addToStream("\n<!-- \nNew Chart\n-->");
        
        antiAliasing = true;
    }
    
    public void initWindow(Aspect a, Rectangle r, int MaxDepth) {
        super.initWindow(a, r, MaxDepth);
        
        if (!iAppend) {
            addToStream(docType);
            m_String = "<svg xmlns=\"http://www.w3.org/2000/svg\" " + theBounds();
            if (antiAliasing) {
                m_String += " style=\"text-antialiasing:true\"";
            }
            addToStream(m_String + ">");
        }
    }

    public String docType;
    public boolean antiAliasing;

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
        if (getPen().getVisible()) {
            Rectangle rect = Rectangle.fromLTRB(x1, y1, x2, y2);
            PointDouble startP = pointFromEllipse(rect, startAngle, 0);
            PointDouble endP = pointFromEllipse(rect, (startAngle + (360 - sweepAngle)), 0);
            
            m_String = "<path" + SVGPen(true);
            m_String += " d=\"M" + pointToStr(startP) + " A" + pointToStr(rect.width / 2.0, rect.height / 2.0) + " 0 0, 1 " + pointToStr(endP) + "\"";
            addEnd(m_String);
        }
    }

    public void arc(int x1, int y1, int x2, int y2, int x3, int y3, int x4,
                    int y4) {
        if (getPen().getVisible()) {
            double start = 0, sweep = 0;
            calcArcAngles(x1, y1, x2, y2, x3, y3, x4, y4, start, sweep);
            arc(x1, y1, x2, y2, start, sweep);
        }
    }

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
            m_String = "<ellipse " + SVGEllipse(x1, y1, x2, y2);
            addEnd(m_String + SVGBrushPen(true));
        }
    }

    protected void transparentEllipse(int x1, int y1, int x2, int y2) {
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
        SVGClip();
        addEnd("<rect " + SVGRect(r));
        SVGEndClip();
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
        SVGClip();
        addEnd("<polygon " + SVGPoints(p));
        SVGEndClip();
    }

    /**
     * Removes any clipping region applied to Chart Drawing.
     */
    public void unClip() {
        if (iClipStack > 0) {
            iClipStack--;
            addToStream("</g>");
        }
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
        SVGClip();
        addEnd("<ellipse " + SVGEllipse(r.x, r.y, r.getRight(), r.getBottom()));
        SVGEndClip();
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
        ChartFont f=getFont();

        if (f.shouldDrawShadow()) {
            doText(x + f.getShadow().getWidth(),
                   y + f.getShadow().getHeight(), text,
                   rotDegree, f.getShadow().getColor());
        }

        doText(x, y, text, rotDegree, f.getColor());
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
        if (getBrush().getVisible() || getPen().getVisible()) {
            int xc = (x2 + x1) / 2;
            int yc = (y2 + y1) / 2;
            int xr = (x2 - x1) / 2;
            int yr = (y2 - y1) / 2;

            startAngle = startAngle * Math.PI / 180.0F;
            endAngle = endAngle * Math.PI / 180.0F;

            int x3 = xc + Utils.round(xr * Math.cos(startAngle));
            int y3 = yc - Utils.round(yr * Math.sin(startAngle));
            int x4 = xc + Utils.round(xr * Math.cos(endAngle));
            int y4 = yc - Utils.round(yr * Math.sin(endAngle));

            prepareShape();
            m_String = " points=\"" + pointToStr(xc, yc) + " ";
            m_String += pointToStr(x1, y1) + " " + pointToStr(x2, y2) + " ";
            m_String += pointToStr(x3, y3) + " " + pointToStr(x4, y4) + "\"";
            addEnd(m_String);
        }
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
    }
    
    public boolean fillPieSegment(int x1,int y1,int x2,int y2,int sd,int rd,int cl) { return false; }

    /**
     * Draws a series of line segments to join point array p.
     *
     * @param p Point[]
     */
    public void polyLine(Point[] p) {
        if (getBrush().getVisible() || getPen().getVisible()) {
            addToStream("<polyline " + SVGPen(true));
            addEnd(SVGPoints(p));
        }
    }
    
    public void polygon(final com.steema.teechart.PointDouble[] p) 
    {
       if (getBrush().getVisible() || getPen().getVisible()) {
           prepareShape();
           addEnd(SVGPoints(p));
       }
    }

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
        if (getBrush().getVisible() || getPen().getVisible()) {
            prepareShape();
            addEnd(SVGPoints(p));
        }
    }

    /**
     * Paints the image in rectangle r.<br>
     * It can also be used to Print the Chart in a customized way: <br>
     * Or you can use the TeeChart Draw method to render an image at any
     * location.
     *
     * @param r Rectangle
     * @param image Image
     * @param transparent boolean
     * @param smoothed boolean
     */
    public void draw(Rectangle r, Image image, boolean transparent, boolean smoothed) {
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
        moveTo(a);
        lineTo(b);
    }

    public void moveTo(int x, int y) {
        fx = x;
        fy = y;
    }

    /**
     * Draws a line from the present PenPos to the new co-ordinates.<br>
     * The Line method has 4 overloads to facilitate the setting of the end
     * co-ordinate using Point p or int x, int y, both with or without a z
     * parameter.
     *
     * @param x int
     * @param y int
     */
    public void lineTo(int x, int y) {
        m_String = "<line x1=\"" + Integer.toString(fx) + "\" y1=\"" +
                   Integer.toString(fy) +
                   "\" ";
        m_String += "x2=\"" + Integer.toString(x) + "\" y2=\"" +
                Integer.toString(y) +
                "\"" + SVGPen(true);
        addEnd(m_String);
        fx = x;
        fy = y;
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
        internalRect(b, r, true, false);
        
    }

    private void drawBrushImage(Rectangle rect) {
    }

    /**
     * Draws a Rectangle (Rectangle r).
     *
     * @param r Rectangle
     */
    public void rectangle(Rectangle r) {
        internalRect(getBrush(), r, true, false);
    }

    public void showImage() {
        addToStream("</svg>");
        // finished
    }

    /**
     * Draws a rectangle with rounded corners of personalized curvature and
     * size.<br>
     *
     * @param r Rectangle
     * @param roundWidth int
     * @param roundHeight int
     */
    public void roundRectangle(Rectangle r, int roundWidth, int roundHeight) {
        internalRect(getBrush(), r, true, true);
    }

    /**
     * Draws a Vertical Line from (X,Top) to (X,Bottom).
     *
     * @param x int
     * @param top int
     * @param bottom int
     */
    public void verticalLine(int x, int top, int bottom) {
        moveTo(x, bottom);
        lineTo(x, top);
    }

    public void donut(int xCenter, int yCenter, int xRadius, int yRadius,
                      double startAngle, double endAngle, int donutPercent) {
        /** @todo FINISH ! */
    }

    public void cone(boolean vertical, int left, int top, int right, int bottom,
                     int z0, int z1,
                     boolean darkSides, int conePercent) {
        /** TODO FINISH ! */
    }

    public void setPixel(int x,int y, int z, Color color) {
        /** TODO FINISH ! */
    }

    public void sphere(int x, int y, int z, int radius, boolean enhanced) {
        /** TODO FINISH ! */
    }

    public void sphere(int left,int top,int right,int bottom) {
        /** @todo FINISH ! */
    }

    public void ellipse(int left,int top,int right,int bottom,boolean enhanced) {
        ellipse(left, top, right, bottom);
    }
}

