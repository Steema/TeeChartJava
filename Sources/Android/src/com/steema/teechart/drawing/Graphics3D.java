package com.steema.teechart.drawing;

import com.steema.teechart.ArrowPoint;
import com.steema.teechart.Aspect;
import com.steema.teechart.BevelStyle;
import com.steema.teechart.Dimension;
import com.steema.teechart.IBaseChart;
import com.steema.teechart.ImageMode;
import com.steema.teechart.LineOrientation;
import com.steema.teechart.Point3D;
import com.steema.teechart.PointDouble;
import com.steema.teechart.Rectangle;
import com.steema.teechart.Shadow;
import com.steema.teechart.TeeBase;
import com.steema.teechart.Triangle3D;
import com.steema.teechart.misc.Utils;
import com.steema.teechart.themes.Theme;
import java.util.ArrayList;

/**
 * 
 * <p>
 * Title: Graphics3D class
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
public abstract class Graphics3D extends TeeBase implements IGraphics3D {
	
	private static final long serialVersionUID = 1L;

	public static final int DARKCOLORQUANTITY = 64;
	public static final int DARKERCOLORQUANTITY = 128;
	public Point[] fourPoints = new Point[4];

	protected boolean smoothingMode = true;
	transient protected boolean textSmooth = true;

	protected Rectangle bounds;

	protected StringFormat stringFormat = new StringFormat();

	protected ChartPen pen = new ChartPen(Color.BLACK);

	public void reset() {
		getPen().reset();
		getBrush().reset();
		getFont().reset();
		setBackColor(Color.WHITE);
		// setBackMode(transparent);
		setTextAlign(StringAlignment.NEAR);
	}
    
	public void save(){}
	public void restore(){}

	public Aspect getAspect() {
		return chart.getAspect();
	}

	/**
	 * Indicates the kind of pen used to draw Canvas lines.
	 * 
	 * @return ChartPen
	 */
	public ChartPen getPen() {
		return pen;
	}

	/**
	 * Determines the kind of pen used to draw Canvas lines.
	 * 
	 * @param value
	 *            ChartPen
	 */
	public void setPen(final ChartPen value) {
		if (value != null)
			pen.assign(value);
	}

	protected ChartBrush brush = new ChartBrush(null);

	/**
	 * Determines the kind of brush used to fill the Canvas draw rectangle
	 * background.<BR>
	 * The Brush.Visible method must be set to true.
	 * 
	 * @return ChartBrush
	 */
	public ChartBrush getBrush() {
		if (brush == null)
			brush = new ChartBrush(null);

		return brush;
	}

	/**
	 * Determines the kind of brush used to fill the Canvas draw rectangle
	 * background.<BR>
	 * The Brush.Visible method must be set to true.
	 * 
	 * @param value
	 *            ChartBrush
	 */
	public void setBrush(final ChartBrush value) {
		if (value != null)
			brush.assign(value);
	}

	public void changed(Object o) {
	}

	/**
	 * Determines the Brush.<!-- -->Gradient properties of the Canvas.
	 * 
	 * @return Gradient
	 */
	public Gradient getGradient() {
		return brush.getGradient();
	}

	/**
	 * Determines the Brush.<!-- -->Gradient properties of the Canvas.
	 * 
	 * @param value
	 *            Gradient
	 */
	public void setGradient(final Gradient value) {
		brush.setGradient(value);
	}

	protected ChartFont font = new ChartFont(chart);

	public boolean getDirty() {
		return false;
	}

	/**
	 * Determines the Font for outputted text when using the Drawing Canvas.
	 * 
	 * @return ChartFont
	 */
	public ChartFont getFont() {
		return font;
	}

	/**
	 * Determines the Font for outputted text when using the Drawing Canvas.
	 * 
	 * @param value
	 *            ChartFont
	 */
	public void setFont(final ChartFont value) {
		font.assign(value);
	}

	private boolean buffered = true;

	private Point3D rotationCenter = new Point3D();
	protected Aspect aspect;
	private Point[] iPoints = new Point[4];
	public PointDouble[] iPointDoubles = new PointDouble[4];
	protected boolean is3D;
	private boolean isOrtho;

	private double s2, c1, s1, c3, s3, c2, c2s3, c2c3;
	private double tempXX, tempYX, tempXZ, tempYZ;

	private boolean iPerspec, iTilt;

	private double iOrthoX, iOrthoY, iZoomFactor, iZoomPerspec;
	private boolean iZoomText;

	private int xCenter, yCenter, zCenter, xCenterOffset, yCenterOffset;

	protected boolean metafiling;
	private Pie3D pie3D;

	public boolean monochrome;

	public enum RoundRectangleStyle {
		Top,
		Bottom,
		Left,
		Right,
		All
	}
          
	public Graphics3D(IBaseChart c) {
		super(c);

		iPoints[0] = new Point(0, 0);
		iPoints[1] = new Point(0, 0);
		iPoints[2] = new Point(0, 0);
		iPoints[3] = new Point(0, 0);

		cubePoints[0] = new Point(0, 0);
		cubePoints[1] = new Point(0, 0);
		cubePoints[2] = new Point(0, 0);
		cubePoints[3] = new Point(0, 0);

		if (chart != null) {
			setAspect(chart.getAspect());
		}
	}

	private Color[] colorPalette = Theme.OperaPalette;

	public int getColorPaletteLength() {
		return colorPalette.length;
	}

	public Color[] getColorPalette() {
		return colorPalette;
	}

	private void setAspect(final Aspect value) {
		aspect = value;
		is3D = aspect.getView3D();
		isOrtho = aspect.getOrthogonal();
		aspect.setSmoothingMode(smoothingMode);
		aspect.setTextSmooth(textSmooth);
	}

	public boolean getMetafiling() {
		return metafiling;
	}

	public void setMetafiling(boolean value) {
		metafiling = value;
	}

	/**
	 * Returns a color from global ColorPalette array variable.
	 * 
	 * @param index
	 *            int
	 * @return Color
	 */
	public Color getDefaultColor(int index) {
		return colorPalette[index % colorPalette.length];
	}

	/**
	 * Specifies a color from global ColorPalette array variable.
	 * 
	 * @param palette
	 *            Color[]
	 */
	public void setColorPalette(final Color[] palette) {
		colorPalette = new Color[palette.length];
		for (int t = 0; t < palette.length; t++) {
			colorPalette[t] = palette[t];
		}
	}

	/**
	 * Converts the Color parameter to a darker color.<br>
	 * The HowMuch parameter indicates the quantity of dark increment. It is
	 * used by Bar Series and Pie Series to calculate the right color to draw
	 * Bar sides and Pie 3D zones.
	 * 
	 * @param c
	 *            Color
	 * @param howMuch
	 *            int
	 */
	static public void applyDark(final Color c, int howMuch) {
		c.applyDark(howMuch);
	}

	/**
	 * Converts the Color parameter to a brighter color. The HowMuch parameter
	 * indicates the quantity of bright increment. It is used by Bar Series and
	 * Pie Series to calculate the right color to draw Bar sides and Pie 3D
	 * zones.
	 * 
	 * 
	 * @param c
	 *            Color
	 * @param howMuch
	 *            int
	 * @return Color
	 */
	static public void applyBright(final Color c, int howMuch) {
		c.applyBright(howMuch);
	}

	public static boolean cull(final Point p0, final Point p1, final Point p2) {
		return (((p0.x - p1.x) * (p2.y - p1.y)) - ((p2.x - p1.x) * (p0.y - p1.y))) < 0;
	}

	public static boolean cull(final Point[] p) {
		return cull(p[0], p[1], p[2]);
	}

	public boolean getMonochrome() {
		return monochrome;
	}

	/**
	 * The alignment used when displaying text using TextOut or TextOut3D. <br>
	 * Default value: Near
	 * 
	 * @return StringAlignment
	 */
	public StringAlignment getTextAlign() {
		return stringFormat.alignment;
	}

	/**
	 * Sets the alignment used when displaying text using TextOut or TextOut3D. <br>
	 * Default value: Near
	 * 
	 * @param value
	 *            StringAlignment
	 */
	public void setTextAlign(final StringAlignment value) {
		stringFormat.alignment = value;
	}

	// Determine whether a line is ltHorizonal or ltVertical, along with the
	// appropriate slope and intercept FOR point-slope line equations. These
	// parameters are used to determine if a line is selected.
	static private CalcLineOutput calcLineParameters(final Point pointA,
			final Point pointB) {
		CalcLineOutput result = new CalcLineOutput();

		Point delta = new Point(pointA.x - pointB.x, pointA.y - pointB.y);

		if ((delta.x == 0) && (delta.y == 0)) {
			// This special CASE should never happen if iMinPixels > 0
			result.slope = 0.0;
			result.intercept = 0.0;
			result.orientation = LineOrientation.POINT;
		} else {
			if (Math.abs(delta.x) >= Math.abs(delta.y)) {
				// The line is more horizontal than vertical. Determine values
				// FOR
				// equation: Y = slope*X + intercept
				if (delta.x == 0) {
					result.slope = 0;
				} else {
					result.slope = (double) delta.y / (double) delta.x;
				}
				result.intercept = pointA.y - pointA.x * result.slope;
				result.orientation = LineOrientation.HORIZONTAL;
			} else {
				// The line is more vertical than horizontal. Determine values
				// FOR
				// equation: X = slope*Y + intercept
				if (delta.y == 0) {
					result.slope = 0;
				} else {
					result.slope = (double) delta.x / (double) delta.y;
				}
				result.intercept = pointA.x - pointA.y * result.slope;
				result.orientation = LineOrientation.VERTICAL;
			}
		}

		return result;
	}

	public Point[] rotateRectangle(final Rectangle r, final double angle) {
		final Point tmpCenter = r.center();
		final double tmp = angle * Utils.PISTEP;
		final double tmpSin = Math.sin(tmp);
		final double tmpCos = Math.cos(tmp);

		Rectangle tmpRect = r;
		tmpRect.offset(-tmpCenter.x, -tmpCenter.y);

		Point[] result = new Point[4];
		result[0] = rotatePoint(tmpRect.x, tmpRect.y, tmpCenter, tmpCos, tmpSin);
		result[1] = rotatePoint(tmpRect.getRight(), tmpRect.y, tmpCenter,
				tmpCos, tmpSin);
		result[2] = rotatePoint(tmpRect.getRight(), tmpRect.getBottom(),
				tmpCenter, tmpCos, tmpSin);
		result[3] = rotatePoint(tmpRect.x, tmpRect.getBottom(), tmpCenter,
				tmpCos, tmpSin);

		return result;
	}

	private static Point rotatePoint(int ax, int ay, final Point tmpCenter,
			final double tmpCos, final double tmpSin) {
		Point p = new Point();
		p.x = tmpCenter.x + (int) (ax * tmpCos + ay * tmpSin);
		p.y = tmpCenter.y + (int) (-ax * tmpSin + ay * tmpCos);
		return p;
	}

	/**
	 * Determines if point p is "near" line segment between p0 and p1.
	 * 
	 * @param p
	 *            Point
	 * @param p0
	 *            Point
	 * @param p1
	 *            Point
	 * @param tolerancePixels
	 *            int
	 * @return boolean
	 */
	public static boolean pointInLineTolerance(Point p, Point p0, Point p1,
			int tolerancePixels) {
		return pointInLineTolerance(p, p0.x, p0.y, p1.x, p1.y, tolerancePixels);
	}

	/**
	 * Determines if point p is "near" line segment between px,py point and
	 * qx,qy point.
	 * 
	 * @param p
	 *            Point
	 * @param px
	 *            int
	 * @param py
	 *            int
	 * @param qx
	 *            int
	 * @param qy
	 *            int
	 * @param tolerance
	 *            int
	 * @return boolean
	 */
	static public boolean pointInLineTolerance(Point p, int px, int py, int qx,
			int qy, int tolerance) {
		Point point1 = new Point(px, py);
		Point point2 = new Point(qx, qy);

		// if an Endpoint is not selected, was part of line selected?
		CalcLineOutput output = calcLineParameters(point1, point2);

		if (output.orientation == LineOrientation.HORIZONTAL) {
			// first check if selection within horizontal range of line
			if ((p.x >= Math.min(point1.x, point2.x))
					&& (p.x <= Math.max(point1.x, point2.x))) {

				// Since X is within range of line, now see if Y value is close
				// enough to the calculated Y value FOR the line to be selected.
				int calculated = Utils.round(output.slope * p.x
						+ output.intercept);
				return (Math.abs(calculated - p.y) <= tolerance);
			}
		} else if (output.orientation == LineOrientation.VERTICAL) {
			// first check if selection within vertical range of line
			if ((p.y >= Math.min(point1.y, point2.y))
					&& (p.y <= Math.max(point1.y, point2.y))) {

				// Since Y is within range of line, now see if X value is close
				// enough to the calculated X value FOR the line to be selected.
				int calculated = Utils.round(output.slope * p.y
						+ output.intercept);
				return (Math.abs(calculated - p.x) <= tolerance);
			}
		} else if (output.orientation == LineOrientation.POINT) {
			tolerance /= 2;
			Rectangle r = Rectangle.fromLTRB(point1.x - tolerance, point1.y
					- tolerance, point2.x + tolerance, point2.y + tolerance);
			return r.contains(p.x, p.y);
		}

		return false;
	}

	// static private boolean Dif(int a,int b,int Tolerance) {
	// return (a+Tolerance)<b;
	// }
	//
	// static public boolean PointInLineTolerance(Point P,int px,int py,int
	// qx,int qy,int Tolerance) {
	// if ( Math.abs((qy-py)*(P.x-px)-(P.y-py)*(qx-px)) >=
	// (Math.max(Math.abs(qx-px),Math.abs(qy-py))))
	// return false;
	// else
	// if ((Dif(qx,px,Tolerance) && Dif(px,P.x,Tolerance)) ||
	// (Dif(qy,py,Tolerance) && Dif(py,P.y,Tolerance))) return false; else
	// if ((Dif(P.x,px,Tolerance) && Dif(px,qx,Tolerance)) ||
	// (Dif(P.y,py,Tolerance) && Dif(py,qy,Tolerance))) return false; else
	// if ((Dif(px,qx,Tolerance) && Dif(qx,P.x,Tolerance)) ||
	// (Dif(py,qy,Tolerance) && Dif(qy,P.y,Tolerance))) return false; else
	// if ((Dif(P.x,qx,Tolerance) && Dif(qx,px,Tolerance)) ||
	// (Dif(P.y,qy,Tolerance) && Dif(qy,py,Tolerance))) return false; else
	// return true;
	//
	// }
	/**
	 * Returns a valid Windows Brush Style from anpalette of many possible Brush
	 * styles.
	 * 
	 * @param index
	 *            int
	 * @return HatchStyle
	 */
	static public HatchStyle getDefaultPattern(int index) {
		HatchStyle[] patternPalette = new HatchStyle[] { HatchStyle.HORIZONTAL,
				HatchStyle.VERTICAL, HatchStyle.FORWARDDIAGONAL,
				HatchStyle.BACKWARDDIAGONAL, HatchStyle.CROSS,
				HatchStyle.DIAGONALCROSS, HatchStyle.DIAGONALBRICK,
				HatchStyle.DIVOT, HatchStyle.LARGECONFETTI,
				HatchStyle.OUTLINEDDIAMOND, HatchStyle.PLAID,
				HatchStyle.SHINGLE, HatchStyle.SOLIDDIAMOND, HatchStyle.SPHERE,
				HatchStyle.TRELLIS, HatchStyle.WAVE, HatchStyle.WEAVE,
				HatchStyle.ZIGZAG };
		return patternPalette[index % patternPalette.length];
	}

	/**
	 * Returns the bounding rectangle for a given array of XY points.
	 * 
	 * @param num
	 *            int
	 * @param p
	 *            Point[]
	 * @return Rectangle
	 */
	public Rectangle rectFromPolygon(int num, Point[] p) {
		Rectangle r = new Rectangle(p[0].x, p[0].y, 0, 0);

		for (int t = 1; t < num; t++) {
			if (p[t].x < r.x) {
				r.x = p[t].x;
			} else if (p[t].x > r.getRight()) {
				r.width = p[t].x - r.x;
			}

			if (p[t].y < r.y) {
				r.y = p[t].y;
			} else if (p[t].y > r.getBottom()) {
				r.height = p[t].y - r.y;
			}
		}

		r.width++;
		r.height++;
		return r;
	}

	/**
	 * Obsolete.&nbsp;Please use Rectangle.<!-- -->Contains method.
	 * 
	 * @param rect
	 *            Rectangle
	 * @param x
	 *            int
	 * @param y
	 *            int
	 * @return boolean
	 */
	static public boolean pointInRect(Rectangle rect, int x, int y) {
		return rect.contains(x, y);
		// return ((x>=rect.x) && (y>=rect.y) && (x<rect.Right) &&
		// (y<rect.Bottom));
	}

	/**
	 * Returns true if point P is inside the vert triangle of x0y0, midxY1,
	 * x1y0.
	 * 
	 * @param p
	 *            Point
	 * @param x0
	 *            int
	 * @param x1
	 *            int
	 * @param y0
	 *            int
	 * @param y1
	 *            int
	 * @return boolean
	 */
	static public boolean pointInTriangle(Point p, int x0, int x1, int y0,
			int y1) {
		Point[] tmp = { new Point(x0, y0), new Point((x0 + x1) / 2, y1),
				new Point(x1, y0) };

		return pointInPolygon(p, tmp);
	}

	/**
	 * Returns true if point P is inside the horizontal triangle.
	 * 
	 * @param p
	 *            Point
	 * @param y0
	 *            int
	 * @param y1
	 *            int
	 * @param x0
	 *            int
	 * @param x1
	 *            int
	 * @return boolean
	 */
	static public boolean pointInHorizTriangle(Point p, int y0, int y1, int x0,
			int x1) {
		Point[] tmp = { new Point(x0, y0), new Point(x1, (y0 + y1) / 2),
				new Point(x0, y1) };
		return pointInPolygon(p, tmp);
	}

	/**
	 * Returns point "ATo" minus ADist pixels from AFrom point.
	 */
	static public Point pointAtDistance(final Point From, final Point To,
			int distance) {
		int px = To.x;
		int py = To.y;

		if (To.x != From.x) {
			final double angle = Math.atan2(To.y - From.y, To.x - From.x);
			px -= Math.round(distance * Math.cos(angle));
			py -= Math.round(distance * Math.sin(angle));
		} else {
			if (To.y < From.y)
				py += distance;
			else
				py -= distance;
		}
		return new Point(px, py);
	}

	/**
	 * Returns the PointDouble which is on the ellipse bounded by rectBounds at the angle degrees.
	 */
	public PointDouble pointFromEllipse(Rectangle rectBounds, double degrees) {
		return pointFromEllipse(rectBounds, degrees, 0);
	}
        
	/**
	 * Returns the PointDouble which is on the ellipse bounded by rectBounds at
	 * the angle degrees. Optionally translates the point in TeeChart's 3D space
	 * when the Z Position is greater than zero.
	 */
	public PointDouble pointFromEllipse(final Rectangle rectBounds,
			final double degrees, int zPos) {
		final Point centreOut = rectBounds.center();

		final double radians = (Math.PI * degrees) / 180.0f;
		final double radiusH = rectBounds.getWidth() / 2.0f;
		final double radiusV = rectBounds.getHeight() / 2.0f;

		final boolean fXMajor = radiusH > radiusV;
		final double a = fXMajor ? radiusH : radiusV, b = fXMajor ? radiusV
				: radiusH;
		final double eccentricity = Math.sqrt(1 - (b * b) / (a * a));
		final double radiusAngle = b
				/ Math.sqrt(1
						- (eccentricity * eccentricity)
						* Math.pow(
								fXMajor ? Math.cos(radians) : Math.sin(radians),
								2));

		final double tmpX = (centreOut.x + radiusAngle * Math.cos(radians));
		final double tmpY = (centreOut.y - radiusAngle * Math.sin(radians));

		if (zPos > 0) {
			PointDouble tmp = new PointDouble();
			calc3DPos(tmp, tmpX, tmpY, zPos);
			return tmp;
		} else
			return new PointDouble(tmpX, tmpY);
	}

	public PointDouble pointFromCircle(Rectangle rectBounds, double degrees) {
		return pointFromCircle(rectBounds, degrees, 0, false);
	}

	public PointDouble pointFromCircle(Rectangle rectBounds, double degrees,
			int zPos, boolean clockWise) {

		int xCenter, yCenter;
		int[] rectCenter = new int[2];
		rectCenter = getRectCenter(rectBounds);
		xCenter = rectCenter[0];
		yCenter = rectCenter[1];

		double radians = (Math.PI * degrees) / 180.0f;
		double radiusH = rectBounds.getWidth() / 2.0f, radiusV = rectBounds
				.getHeight() / 2.0f;

		double tmpX = xCenter + radiusH * Math.cos(radians);
		double tmpY = 0;
		if (clockWise) {
			tmpY = yCenter + radiusV * Math.sin(radians);
		} else {
			tmpY = yCenter - radiusV * Math.sin(radians);
		}

		PointDouble tmpPoint = new PointDouble();

		if (zPos > 0) {
			calc3DPos(tmpPoint, tmpX, tmpY, zPos);
		} else {
			tmpPoint = new PointDouble(tmpX, tmpY);

		}

		return tmpPoint;
	}

	public PointDouble pointFromSpiral(final Rectangle rectBounds,
			final double degrees, final double twist) {
		final Point centrePoint = rectBounds.center();

		final double radians = (Math.PI * degrees) / 180.0;
		double radiusH = rectBounds.getWidth() / 2.0, radiusV = rectBounds
				.getHeight() / 2.0;

		boolean fXMajor = radiusH > radiusV;
		double a = fXMajor ? radiusV : radiusH;
		double r = a * Math.exp(radians * twist);

		final double tmpX = centrePoint.x + (r * Math.cos(radians));
		final double tmpY = centrePoint.y + (r * Math.sin(radians));

		return new PointDouble(tmpX, tmpY);
	}

	public PointDouble pointFromCircle(final PieRect rect,
			final double degrees, int zPos, boolean clockWise) {
		final double radians = (Math.PI * degrees) / 180.0;

		final double tmpX = rect.center.x + rect.radiusH * Math.cos(radians);
		final double tmpY = clockWise ? rect.center.y + rect.radiusV
				* Math.sin(radians) : rect.center.y - rect.radiusV
				* Math.sin(radians);

		if (zPos > 0) {
			PointDouble tmpPoint = new PointDouble();
			calc3DPos(tmpPoint, tmpX, tmpY, zPos);
			return tmpPoint;
		} else
			return new PointDouble(tmpX, tmpY);
	}

	/**
	 * Returns true if point P is inside the ellipse bounded by Left, Top,
	 * Right, Bottom.
	 * 
	 * @param p
	 *            Point
	 * @param left
	 *            int
	 * @param top
	 *            int
	 * @param right
	 *            int
	 * @param bottom
	 *            int
	 * @return boolean
	 */
	static public boolean pointInEllipse(Point p, int left, int top, int right,
			int bottom) {
		return pointInEllipse(p, Rectangle.fromLTRB(left, top, right, bottom));
	}

	/**
	 * Returns true if point P is inside the ellipse bounded by Rect.
	 * 
	 * @param p
	 *            Point
	 * @param rect
	 *            Rectangle
	 * @return boolean
	 */
	static public boolean pointInEllipse(Point p, Rectangle rect) {
		if(rect == null)
			return false;

		Point tmp = rect.center();
		int tmpWidth = (int) Utils.sqr(tmp.x - rect.x);
		int tmpHeight = (int) Utils.sqr(tmp.y - rect.y);

		return (tmpWidth != 0)
				&& (tmpHeight != 0)
				&& ((Utils.sqr(p.x - tmp.x) / tmpWidth)
						+ (Utils.sqr(p.y - tmp.y) / tmpHeight) <= 1);
	}

	/**
	 * Returns true if point P is inside Poly polygon.
	 * 
	 * @param p
	 *            Point
	 * @param poly
	 *            Point[]
	 * @return boolean
	 */
	static public boolean pointInPolygon(Point p, Point[] poly) {
		boolean result = false;
		int j = poly.length - 1;
		int tmp = j;

		for (int i = 0; i <= tmp; i++) {
			if (((((poly[i].y <= p.y) && (p.y < poly[j].y)) || ((poly[j].y <= p.y) && (p.y < poly[i].y))) && (p.x < (poly[j].x - poly[i].x)
					* (p.y - poly[i].y) / (poly[j].y - poly[i].y) + poly[i].x))) {
				result = !result;
			}
			j = i;
		}

		return result;
	}

	protected double getIZoomfactor() {
		return iZoomFactor;
	}

	protected void setIZoomfactor(double value) {
		iZoomFactor = value;
	}

	/**
	 * The X coordinate of the pixel location of the center of the 3D Canvas.<br>
	 * The origin of the pixel coordinate system is in the top left corner of
	 * the parent window.
	 * 
	 * @return int
	 */
	public int getXCenter() {
		return xCenter;
	}

	/**
	 * Specifies the X coordinate of the pixel location of the center of the 3D
	 * Canvas.<br>
	 * The origin of the pixel coordinate system is in the top left corner of
	 * the parent window.
	 * 
	 * @param value
	 *            int
	 */
	public void setXCenter(int value) {
		xCenter = value;
	}

	/**
	 * The Y coordinate of the pixel location of the center of the 3D Canvas.<br>
	 * The origin of the pixel coordinate system is in the top left corner of
	 * the parent window.
	 * 
	 * @return int
	 */
	public int getYCenter() {
		return yCenter;
	}

	/**
	 * Specifies the Y coordinate of the pixel location of the center of the 3D
	 * Canvas.<br>
	 * The origin of the pixel coordinate system is in the top left corner of
	 * the parent window.
	 * 
	 * @param value
	 *            int
	 */
	public void setYCenter(int value) {
		yCenter = value;
	}

	/**
	 * The anti-alias mode for the Graphics Pen when Custom drawing.<br>
	 * For example: <br>
	 * AntiAlias - Specifies antialiased rendering.<br>
	 * Default - Specifies the default mode.<br>
	 * HighQuality - Specifies high quality, low speed rendering. <br>
	 * HighSpeed - Specifies high speed, low quality rendering.<br>
	 * Invalid - Specifies an invalid mode. <br>
	 * 
	 * @return boolean
	 */
	public boolean getSmoothingMode() {
		return smoothingMode;
	}

	/**
	 * Sets the anti-alias mode for the Graphics Pen when Custom drawing.<br>
	 * For example: <br>
	 * AntiAlias - Specifies antialiased rendering.<br>
	 * Default - Specifies the default mode.<br>
	 * HighQuality - Specifies high quality, low speed rendering. <br>
	 * HighSpeed - Specifies high speed, low quality rendering.<br>
	 * Invalid - Specifies an invalid mode. <br>
	 * 
	 * 
	 * @param value
	 *            boolean
	 */
	public void setSmoothingMode(boolean value) {
		smoothingMode = setBooleanProperty(smoothingMode, value);
	}

	/**
	 * Chooses between speed or display quality for Text rendering.
	 * 
	 * @return boolean
	 */
	public boolean getTextSmooth() {
		return textSmooth;
	}

	public void setTextSmooth(boolean value) {
		textSmooth = setBooleanProperty(textSmooth, value);
	}

	static private boolean internalGetSupports3DText() {
		return false;
	}

	/**
	 * Returns if Canvas supports 3D Text or not.
	 * 
	 * @return boolean
	 */
	public boolean getSupports3DText() {
		return internalGetSupports3DText();
	}

	/**
	 * Calculates and returns the XY position in pixels of the XYZ 3D
	 * coordinate.
	 * 
	 * @param P
	 *            Point3D
	 * @return Point
	 */
	public Point calculate3DPosition(Point3D P) {
		return calculate3DPosition(P.x, P.y, P.z);
	}

	/**
	 * Calculates and returns the XY position in pixels of the XYZ 3D
	 * coordinate.
	 * 
	 * @param x
	 *            int
	 * @param y
	 *            int
	 * @param z
	 *            int
	 * @return Point
	 */
	public Point calculate3DPosition(int x, int y, int z) {
		return calc3DPos(x, y, z);
	}

	/**
	 * Calculates and returns the XY position in pixels of the XYZ 3D
	 * coordinate.
	 * 
	 * @param P
	 *            Point
	 * @param z
	 *            int
	 * @return Point
	 */
	public Point calculate3DPosition(Point P, int z) {
		return calculate3DPosition(P.x, P.y, z);
	}

	public Point calculate2DPosition(Point p, int z) {
		return calculate2DPosition(p.x, p.y, z);
	}

	/**
	 * Calculates and returns the XY position in pixels of the coordinate.
	 * 
	 * @param x
	 *            int
	 * @param y
	 *            int
	 * @param z
	 *            int
	 * @return Point
	 */
	public Point calculate2DPosition(int x, int y, int z) {

		if (iZoomFactor != 0) {
			double tmp = 1.0 / iZoomFactor;

			if (isOrtho) {
				x = Utils.round(((x - xCenterOffset) * tmp) - (iOrthoX * z))
						+ xCenter;
				y = Utils.round(((y - yCenterOffset) * tmp) + (iOrthoY * z))
						+ yCenter;
			} else if (is3D && (tempXX != 0) && (c2c3 != 0)) {

				int x1 = x;
				z -= zCenter;
				x = Utils
						.round((((x1 - xCenterOffset) * tmp) - (z * tempXZ) - (y - yCenter)
								* c2s3)
								/ tempXX)
						+ xCenter;
				y = Utils
						.round((((y - yCenterOffset) * tmp) - (z * tempYZ) - (x1 - xCenter)
								* tempYX)
								/ c2c3)
						+ yCenter;
			}
		}

		return new Point(x, y);
	}

	public void calcArcAngles(int x1, int y1, int x2, int y2, int x3, int y3,
			int x4, int y4, double startAngle, double sweepAngle) {
		double halfDivPi = 180.0 / Math.PI;

		int xc = (x2 + x1) / 2;
		int yc = (y2 + y1) / 2;
		int xr = (x2 - x1) / 2;
		int yr = (y2 - y1) / 2;

		startAngle = Math.atan2(yc - y3, x3 - xc);
		if (startAngle < 0)
			startAngle += 2.0 * Math.PI;
		startAngle *= halfDivPi;
		sweepAngle = Math.atan2(yc - y4, x4 - xc);
		if (sweepAngle < 0)
			sweepAngle += 2.0 * Math.PI;
		sweepAngle *= halfDivPi;
		sweepAngle -= startAngle;
	}

	public void calcArcPoints(int x1, int y1, int x2, int y2,
			double startAngle, double sweepAngle, int x3, int y3, int x4, int y4) {
		double endAngle = startAngle + sweepAngle;
		int xc = (x2 + x1) / 2;
		int yc = (y2 + y1) / 2;
		int xr = (x2 - x1) / 2;
		int yr = (y2 - y1) / 2;

		startAngle *= Math.PI / 180.0;
		endAngle *= Math.PI / 180.0;

		x3 = xc + Utils.round(xr * Math.cos(startAngle));
		y3 = yc - Utils.round(yr * Math.sin(startAngle));
		x4 = xc + Utils.round(xr * Math.cos(endAngle));
		y4 = yc - Utils.round(yr * Math.sin(endAngle));
	}

	public Point calc3DPoint(Point3D p) {
		return calc3DPos(p.x, p.y, p.z);
	}

	public Point calc3DPoint(int x, int y, int z) {
		return calc3DPos(x, y, z);
	}

	public Point calc3DPoint(final Point p, int z) {
		return calc3DPos(p.x, p.y, z);
	}

	/**
	 * Calculates and returns the XY position in pixels of the point p Z 3D
	 * coordinate. <br>
	 * Can be used when custom drawing using 3D XYZ coordinates are returned
	 * from the axes or not.
	 * 
	 * @param source
	 *            Point3D
	 * @return Point
	 */
	public Point calc3DPos(final Point3D source) {
		return calc3DPos(source.x, source.y, source.z);
	}

	/**
	 * Calculates and returns the XY position in pixels of the point p Z 3D
	 * coordinate.<br>
	 * Can be used when custom drawing using 3D XYZ coordinates are returned
	 * from the axes or not.
	 * 
	 * @param source
	 *            Point
	 * @param z
	 *            int
	 * @return Point
	 */
	public Point calc3DPos(final Point source, int z) {
		return calc3DPos(source.x, source.y, z);
	}

	/**
	 * Calculates and returns the XY position in pixels of the point p with Z =
	 * 0 3D coordinate.<br>
	 * Can be used when custom drawing using 3D XYZ coordinates are returned
	 * from the axes or not.
	 * 
	 * @param source
	 *            Point
	 * @return Point
	 */
	public void calc3DPos(Point result, final Point source) {
		calc3DPos(result, source.x, source.y, 0);
	}

	/**
	 * Calculates and returns the XY position in pixels of the XYZ 3D
	 * coordinate.<br>
	 * Can be used when custom drawing using 3D XYZ coordinates are returned
	 * from the axes or not.
	 * 
	 * @param x
	 *            int
	 * @param y
	 *            int
	 * @param z
	 *            int
	 * @return Point
	 */
	public void calc3DPos(Point p, int x, int y, int z) {
		double xx, yy;
		PointDouble point = new PointDouble(x,y);

		if (isOrtho) {
			point.x = (iZoomFactor * (x - xCenter + (iOrthoX * z)))
					+ xCenterOffset;
			point.y = (iZoomFactor * (y - yCenter - (iOrthoY * z)))
					+ yCenterOffset;
		} else if (is3D) {
			z -= zCenter;
			x -= xCenter;
			y -= yCenter;

			final double zz = z * c2 - x * s2;

			double tmp = iZoomFactor;

			if (iPerspec) {
				tmp /= (1 + iZoomPerspec * (zz * c1 + y * s1));
			}

			if (iTilt) {
				xx = x * c2 + z * s2;
				yy = y * c1 - zz * s1;

				point.x = ((xx * c3 - yy * s3) * tmp) + xCenterOffset;
				point.y = ((yy * c3 + xx * s3) * tmp) + yCenterOffset;
			} else {
				point.x = ((x * c2 + z * s2) * tmp) + xCenterOffset;
				point.y = ((y * c1 - zz * s1) * tmp) + yCenterOffset;
			}
		}
		
		p.x = (int)point.x;
		p.y = (int)point.y;
	}

	public Point calc3DPos(int x, int y, int z) {
		Point result = new Point();
		calc3DPos(result, x, y, z);
		return result;
	}

	public void calc3DPos(PointDouble point, double x, double y, double z) {
		double xx, yy;
		point.x = x;
		point.y = y;

		if (isOrtho) {
			point.x = (iZoomFactor * (x - xCenter + (iOrthoX * z)))
					+ xCenterOffset;
			point.y = (iZoomFactor * (y - yCenter - (iOrthoY * z)))
					+ yCenterOffset;
		} else if (is3D) {
			z -= zCenter;
			x -= xCenter;
			y -= yCenter;

			final double zz = z * c2 - x * s2;

			double tmp = iZoomFactor;

			if (iPerspec) {
				tmp /= (1 + iZoomPerspec * (zz * c1 + y * s1));
			}

			if (iTilt) {
				xx = x * c2 + z * s2;
				yy = y * c1 - zz * s1;

				point.x = ((xx * c3 - yy * s3) * tmp) + xCenterOffset;
				point.y = ((yy * c3 + xx * s3) * tmp) + yCenterOffset;
			} else {
				point.x = ((x * c2 + z * s2) * tmp) + xCenterOffset;
				point.y = ((y * c1 - zz * s1) * tmp) + yCenterOffset;
			}
		}
	}

	/**
	 * Calculates and returns the XY position as double of the XYZ 3D
	 * coordinate.<br>
	 * Can be used when custom drawing using 3D XYZ coordinates are returned
	 * from the axes or not.
	 */
	public void calc3DPos(PointDouble point, int x, int y, int z) {
		double xx, yy;
		point.x = x;
		point.y = y;

		if (isOrtho) {
			point.x = (iZoomFactor * (x - xCenter + (iOrthoX * z)))
					+ xCenterOffset;
			point.y = (iZoomFactor * (y - yCenter - (iOrthoY * z)))
					+ yCenterOffset;
		} else if (is3D) {
			z -= zCenter;
			x -= xCenter;
			y -= yCenter;

			final double zz = z * c2 - x * s2;

			double tmp = iZoomFactor;

			if (iPerspec) {
				tmp /= (1 + iZoomPerspec * (zz * c1 + y * s1));
			}

			if (iTilt) {
				xx = x * c2 + z * s2;
				yy = y * c1 - zz * s1;

				point.x = ((xx * c3 - yy * s3) * tmp) + xCenterOffset;
				point.y = ((yy * c3 + xx * s3) * tmp) + yCenterOffset;
			} else {
				point.x = ((x * c2 + z * s2) * tmp) + xCenterOffset;
				point.y = ((y * c1 - zz * s1) * tmp) + yCenterOffset;
			}
		}
	}

	/**
	 * Returns Color parameter after changing its "A" value.
	 * 
	 * @param transparency
	 *            int from 0 to 100
	 * @param color
	 *            Color
	 * @return Color with transparency percentage applied.
	 */
	static public Color transparentColor(int transparency, final Color color) {
		return color.transparentColor(transparency);
	}

	/**
	 * Returns percentage based on Color "A" value.<br>
	 * It is a value between 0 and 100 which sets the transparency percentage
	 * with respect foreground to background.
	 * 
	 * @param color
	 *            Color
	 * @return int percentage from 0 to 100%
	 */
	static public int transparency(final Color color) {
		return Color.transparencyOf(color);
	}

	/**
	 * Calculates and returns the position in pixels of the Rectangle 3D
	 * coordinates. <br>
	 * 
	 * @param r
	 *            Rectangle
	 * @param z
	 *            int
	 * @return Rectangle
	 */
	public Rectangle calcRect3D(Rectangle r, int z) {
		Point p0 = calc3DPos(r.x, r.y, z);
		Point p1 = calc3DPos(r.getRight(), r.getBottom(), z);

		r.x = p0.x;
		r.y = p0.y;
		r.width = p1.x - r.x;
		r.height = p1.y - r.y;
		return r;
	}

	/**
	 * Returns an array of four Point objects
	 * 
	 * @param r
	 *            Rectangle
	 * @param z
	 *            int
	 * @return Point[]
	 */
	public Point[] fourPointsFromRect(final Rectangle r, int z) {
		final Point[] p = new Point[4];
		p[0] = calc3DPoint(r.getLocation(), z);
		p[1] = calc3DPoint(r.getRight(), r.getTop(), z);
		p[2] = calc3DPoint(r.getRight(), r.getBottom(), z);
		p[3] = calc3DPoint(r.getLeft(), r.getBottom(), z);
		return p;
	}

	/**
	 * Returns the smallest bounding rectangle containing the four corners of
	 * the "R" rectangle after calculating their 3D position.
	 * 
	 * @param r
	 *            Rectangle
	 * @param z
	 *            int
	 * @return Rectangle
	 */
	public Rectangle rectFromRectZ(final Rectangle r, final int z) {
		return rectFromPolygon(4, fourPointsFromRect(r, z));
	}

	/**
	 * Sets the value of PenPos to x and y co-ordinates before calling LineTo.
	 * 
	 * @param x
	 *            int
	 * @param y
	 *            int
	 */
	public abstract void moveTo(int x, int y);

	/**
	 * Sets the value of PenPos to Point p before calling LineTo.
	 * 
	 * @param p
	 *            Point
	 */
	public void moveTo(final Point p) {
		moveTo(p.x, p.y);
	}

	/**
	 * Sets the value of PenPos to x, y and z co-ordinates before calling
	 * LineTo.
	 * 
	 * @param x
	 *            int
	 * @param y
	 *            int
	 * @param z
	 *            int
	 */
	public void moveTo(int x, int y, int z) {
		moveTo(calc3DPos(x, y, z));
	}

	/**
	 * Sets the value of PenPos to Point p with z depth offset before calling
	 * LineTo.
	 * 
	 * @param p
	 *            Point
	 * @param z
	 *            int
	 */
	public void moveTo(final Point p, final int z) {
		moveTo(calc3DPos(p, z));
	}

	/**
	 * Sets the value of PenPos to 3D Point p before calling LineTo.
	 * 
	 * @param p
	 *            Point3D
	 */
	public void moveTo(final Point3D p) {
		moveTo(calc3DPos(p));
	}

	/**
	 * Draws a line to Point with z depth offset.
	 * 
	 * @param p
	 *            Point
	 * @param z
	 *            int
	 */
	public void lineTo(final Point p, final int z) {
		lineTo(p.x, p.y, z);
	}

	/**
	 * Draws a line to Point with z = 0 depth offset.
	 * 
	 * @param p
	 *            Point
	 */
	public void lineTo(final Point p) {
		lineTo(p.x, p.y);
	}

	/**
	 * Draws line from present position to end co-ordinates with z depth offset.
	 * 
	 * @param x
	 *            int
	 * @param y
	 *            int
	 * @param z
	 *            int
	 */
	public void lineTo(final int x, final int y, final int z) {
		lineTo(calc3DPos(x, y, z));
	}

	/**
	 * Draws a Line to 3D Point.
	 * 
	 * @param p
	 *            Point3D
	 */
	public void lineTo(final Point3D p) {
		lineTo(calc3DPos(p));
	}

	/**
	 * Draws a Line between point p0 and point p1 with Z offset.
	 * 
	 * 
	 * @param p0
	 *            Point Origin xy
	 * @param p1
	 *            Point Destination xy
	 * @param z
	 *            int Depth offset
	 */
	public void line(final Point p0, final Point p1, int z) {
		line(p0.x, p0.y, p1.x, p1.y, z);
	}

	/**
	 * Draws a Line between point p0 and point p1.
	 * 
	 * @param p0
	 *            Point is origin xy
	 * @param p1
	 *            Point is destination xy
	 */
	public void line(final Point p0, final Point p1) {
		line(p0.x, p0.y, p1.x, p1.y);
	}

	/**
	 * Draws a Line between co-ordinates with different start/end z depth
	 * offset.
	 * 
	 * @param x0
	 *            int
	 * @param y0
	 *            int
	 * @param z0
	 *            int
	 * @param x1
	 *            int
	 * @param y1
	 *            int
	 * @param z1
	 *            int
	 */
	public void line(int x0, int y0, int z0, int x1, int y1, int z1) {
		line(calc3DPos(x0, y0, z0), calc3DPos(x1, y1, z1));
	}

	/**
	 * Draws a Line between co-ordinates with z depth offset.
	 * 
	 * @param x0
	 *            int
	 * @param y0
	 *            int
	 * @param x1
	 *            int
	 * @param y1
	 *            int
	 * @param z
	 *            int
	 */
	public void line(int x0, int y0, int x1, int y1, int z) {
		line(calc3DPos(x0, y0, z), calc3DPos(x1, y1, z));
	}

	/**
	 * Draws a Vertical Line from (X,Top) to (X,Bottom) at z depth position.
	 * 
	 * @param x
	 *            int
	 * @param top
	 *            int
	 * @param bottom
	 *            int
	 * @param z
	 *            int
	 */
	public void verticalLine(int x, int top, int bottom, int z) {
		calc3DPos(line0, x, top, z);
		calc3DPos(line1, x, bottom, z);
		line(line0, line1);
	}

	final private class Pie3D {

		public static final int MAXPIESTEPS = 32;
		public PointDouble[] points = new PointDouble[2 * MAXPIESTEPS + 1];
		public PointDouble[] points3D2 = new PointDouble[2 * MAXPIESTEPS + 1];
		public PointDouble[] points3D = new PointDouble[2 * MAXPIESTEPS + 1];
		public int start3D, end3D, circleSteps;
		transient private Color oldColor;

		private Graphics3D g;

		private boolean dark;
		private double tmpXRadius, tmpYRadius;
		private int donut;
		private Point center = new Point();

		public Pie3D(final Graphics3D graphics) {
			super();
			g = graphics;
		}

		public void draw3DPie() {
			int tt;

			if (dark) {
				g.internalApplyDark(oldColor, 32);
			}

			if ((start3D == 1) && (end3D == circleSteps)) {
				for (int t = 1; t <= circleSteps; t++) {
					points3D[t - 1] = points[t];
				}
				tt = circleSteps;
			} else {
				tt = 0;
				for (int t = start3D; t <= end3D; t++) {
					points3D[tt] = points[t];
					points3D[end3D - start3D + 1 + tt] = points3D[2
							* circleSteps - end3D + tt];
					tt++;
				}
			}

			g.polygon(sliceArray(points3D, 2 * tt));
		}

		private Point calcCenter(final double angle, int z) {
			if (donut > 0) {
				final double tmpSin = Math.sin(angle);
				final double tmpCos = Math.cos(angle);
				final int tmpX = center.x + Utils.round(tmpXRadius * tmpCos);
				final int tmpY = center.y - Utils.round(tmpYRadius * tmpSin);
				return g.calc3DPos(tmpX, tmpY, z);
			} else {
				return g.calc3DPos(center, z);
			}
		}

		private void finishSide(final double angle, int z) {
			g.iPoints[3] = calcCenter(angle, z);
			if (dark) {
				g.internalApplyDark(oldColor, 32);
			}
			g.polygonFour();
		}

		private PieRect z0Rect, z1Rect;
		private PieRect z0DonutRect, z1DonutRect;
		private PieRect z0BevelRect;
		private PieRect z1BevelRect;

		private int zPos;
		private int iStartAngle, iEndAngle;
		private int sweepAngle;
		private boolean isDonut, drawEntirePie, iDrawSides, hasBevel;

		private PointDouble middle0 = new PointDouble();
		private PointDouble middle1 = new PointDouble();
		private PointDouble start0, end0, start1, end1;
		private PointDouble startD0, endD0, startD1, endD1;
		private PointDouble startB0, endB0, startB1, endB1;

		private PointDouble[] list = new PointDouble[100];
		private int listSize;

		private ChartBrush gradientBrush;

		/*
		 * SLOW !!! private PointDouble[] redimArray(int count, int index, final
		 * PointDouble[] points) { final PointDouble[] tmpArr = new
		 * PointDouble[count]; int loc = 0;
		 * 
		 * for (int i=index; i<(points.length+index); i++) { tmpArr[i] =
		 * points[loc]; loc++; }
		 * 
		 * return tmpArr; }
		 */

		private void addToList(final PointDouble point) {
			if (list.length <= listSize) {
				final PointDouble[] tmp = new PointDouble[list.length + 100];

				for (int t = 0; t < listSize; t++)
					tmp[t] = list[t];

				list = tmp;
			}

			list[listSize++] = point;
		}

		Rectangle pieRect = new Rectangle(0, 0, 0, 0);

		public void pie(int xCenter, int yCenter, int xRadius, int yRadius,
				int z0, int z1, double startAngle, double endAngle,
				boolean darkSides, boolean drawSides, int donutPercent,
				int bevelPercent, EdgeStyle edgeStyle, boolean last /*
																	 * , Shadow
																	 * shadow
																	 */) {
			int left0, right0, top0, bottom0;
			Point tmpP = new Point();

			isDonut = false;
			hasBevel = false;

			tmpP.x = xCenter;
			tmpP.y = yCenter;

			left0 = tmpP.x - xRadius;
			right0 = tmpP.x + xRadius;
			top0 = tmpP.y - yRadius;
			bottom0 = tmpP.y + yRadius;

			zPos = z1 - z0;

			z0Rect = new PieRect(left0, top0, right0, bottom0);
			z1Rect = new PieRect(left0, top0 + zPos, right0, bottom0 + zPos);

			iDrawSides = drawSides;

			iStartAngle = (int) Math.round((startAngle * 180.0) / Math.PI);
			iEndAngle = (int) Math.round((endAngle * 180.0) / Math.PI);

			if (iEndAngle < iStartAngle) {
				iEndAngle += 360;
			}

			if (g.getBrush().getSolid())
				oldColor = g.getBrush().getColor();
			else
				oldColor = g.getBackColor();

			// Color shadowColor = Color.EMPTY;

			/*
			 * if (shadow!=null && shadow.Visible) { shadowColor =
			 * shadow.Brush.Color; }
			 */

			drawEntirePie = g.getBrush().getTransparency() > 0
					&& g.getChart().getAspect().getView3D();

			if (donutPercent > 0) {
				final double tmpXRadius = donutPercent * xRadius * 0.01;
				final double tmpYRadius = donutPercent * yRadius * 0.01;

				left0 = (int) Math.round(tmpP.x - tmpXRadius);
				right0 = (int) Math.round(tmpP.x + tmpXRadius);
				top0 = (int) Math.round(tmpP.y - tmpYRadius);
				bottom0 = (int) Math.round(tmpP.y + tmpYRadius);

				z0DonutRect = new PieRect(left0, top0, right0, bottom0);
				z1DonutRect = new PieRect(left0, top0 + zPos, right0, bottom0
						+ zPos);

				startD0 = pointFromCircle(z0DonutRect, iStartAngle, zPos, false);
				endD0 = pointFromCircle(z0DonutRect, iEndAngle, zPos, false);

				startD1 = pointFromCircle(z1DonutRect, iStartAngle, zPos, false);
				endD1 = pointFromCircle(z1DonutRect, iEndAngle, zPos, false);

				isDonut = true;
			}

			if (bevelPercent > 0) {
				final double bevelAmount = bevelPercent * zPos * 0.01;

				left0 = (int) Math.round(tmpP.x - (xRadius - bevelAmount));
				right0 = (int) Math.round(tmpP.x + (xRadius - bevelAmount));
				top0 = (int) Math.round(tmpP.y - (yRadius - bevelAmount));
				bottom0 = (int) Math.round(tmpP.y + (yRadius - bevelAmount));

				z0BevelRect = new PieRect(left0, top0, right0, bottom0);
				z1BevelRect = new PieRect(z0Rect.rect.x, z0Rect.rect.y
						+ (int) Math.round(bevelAmount),
						z0Rect.rect.getRight(), z0Rect.rect.getBottom()
								+ (int) Math.round(bevelAmount));

				// z1BevelRect.Offset(0.0, bevelAmount);

				startB0 = pointFromCircle(z0BevelRect, iStartAngle, zPos, false);
				endB0 = pointFromCircle(z0BevelRect, iEndAngle, zPos, false);

				startB1 = pointFromCircle(z1BevelRect, iStartAngle, zPos, false);
				endB1 = pointFromCircle(z1BevelRect, iEndAngle, zPos, false);

				hasBevel = true;
			}

			g.calc3DPos(middle0, tmpP.x, tmpP.y, zPos);
			g.calc3DPos(middle1, tmpP.x, tmpP.y + zPos, zPos);

			start0 = pointFromCircle(z0Rect, iStartAngle, zPos, false);
			end0 = pointFromCircle(z0Rect, iEndAngle, zPos, false);

			start1 = pointFromCircle(z1Rect, iStartAngle, zPos, false);
			end1 = pointFromCircle(z1Rect, iEndAngle, zPos, false);

			sweepAngle = iEndAngle - iStartAngle;

			/*
			 * final double szPerc = g.getAspect().getZoom() / 100.0;
			 * 
			 * pieRect = Rectangle.fromLTRB( PointDouble.round(middle0).x -
			 * (int) Math.round(xRadius * szPerc), PointDouble.round(middle0).y
			 * - (int) Math.round((yRadius + 1) * szPerc),
			 * PointDouble.round(middle0).x + (int) Math.round(xRadius *
			 * szPerc), PointDouble.round(middle0).y + (int) Math.round((yRadius
			 * - 1) * szPerc));
			 */

			drawBottom(iStartAngle, iEndAngle);

			boolean invisiblePen = g.getPen().getColor() == oldColor; // !g.getPen().getVisible();

			if (darkSides) {
				g.internalApplyDark(oldColor, 32);

				if (invisiblePen) {
					Color c = oldColor;
					c.applyDark(32);
					g.getPen().setColor(c);
				}
			}

			if (chart.getAspect().getView3D())
				drawSides(iStartAngle, iEndAngle);

			if (darkSides) {
				if (g.getBrush().getSolid())
					g.getBrush().setColor(oldColor);
				else
					g.setBackColor(oldColor);

				if (invisiblePen)
					g.getPen().setColor(oldColor);
			}

			drawTop(iStartAngle, iEndAngle);

			drawLighting(edgeStyle, last);
		}

		private void drawSides(int startAng, int endAng) {
			listSize = 0;

			if (end0.x > middle0.x) {
				if (start0.x < middle0.x) {
					drawBack(startAng, endAng);
					drawRight();
					drawLeft();
					drawFront(startAng, endAng);
				} else {
					if (sweepAngle > 180) {
						drawBack(startAng, endAng);
						drawRight();
						drawLeft();
						drawFront(startAng, endAng);
					} else {
						drawLeft();
						drawBack(startAng, endAng);
						drawFront(startAng, endAng);
						drawRight();
					}
				}
			} else {
				if (start0.x < middle0.x) {
					if (sweepAngle > 180) {
						drawBack(startAng, endAng);
						drawLeft();
						drawRight();
						drawFront(startAng, endAng);
					} else {
						drawRight();
						drawBack(startAng, endAng);
						drawFront(startAng, endAng);
						drawLeft();
					}
				} else {
					if (sweepAngle > 180) {
						drawBack(startAng, endAng);
						drawLeft();
						drawFront(startAng, endAng);
						drawRight();
					} else {
						drawBack(startAng, endAng);
						drawFront(startAng, endAng);
						drawRight();
						drawLeft();
					}
				}
			}
		}

		private void drawBack(int startAng, int endAng) {
			boolean doDrawBack = isDonut;

			listSize = 0;

			if (!drawEntirePie && doDrawBack && start0.y > middle0.y
					&& end0.y > middle0.y && sweepAngle < 180) {
				doDrawBack = false;
			}

			if (doDrawBack) {
				for (double i = startAng; i <= endAng; i = i + 0.25)
					addToList(pointFromCircle(z0DonutRect, i, zPos, false));

				for (double i = endAng; i >= startAng; i = i - 0.25)
					addToList(pointFromCircle(z1DonutRect, i, zPos, false));

				drawPoints();
			}
		}

		private void drawFront(int startAng, int endAng) {
			boolean doDrawFront = true;

			if ((!drawEntirePie && start0.y < middle0.y && end0.y < middle0.y && sweepAngle < 180)) {
				doDrawFront = false;
				drawBevel(startAng, endAng);
			}

			if (doDrawFront) {
				listSize = 0;

				int tmpEndAng = endAng;
				int tmpStartAng = startAng;

				while (tmpEndAng > 360)
					tmpEndAng -= 360;

				while (tmpEndAng < -360)
					tmpEndAng += 360;

				while (tmpStartAng > 360)
					tmpStartAng -= 360;

				while (tmpStartAng < -360)
					tmpStartAng += 360;

				if (tmpEndAng < 0) {
					tmpEndAng = 360 + tmpEndAng;
				}

				if (tmpStartAng < 0) {
					tmpStartAng = 360 + tmpStartAng;
				}

				final PieRect r = hasBevel ? z1BevelRect : z0Rect;

				if (start0.y > middle0.y && end0.y < middle0.y) // right
				{
					if (drawEntirePie) {

						for (double i = 0; i <= tmpEndAng; i = i + 0.25)
							addToList(pointFromCircle(r, i, zPos, false));

						for (double i = tmpEndAng; i >= 0; i = i - 0.25)
							addToList(pointFromCircle(z1Rect, i, zPos, false));

						drawPoints();
					}

					if (tmpStartAng > 0) {
						for (double i = 360; i >= tmpStartAng; i = i - 0.25)
							addToList(pointFromCircle(z1Rect, i, zPos, false));

						for (double i = tmpStartAng; i <= 360; i = i + 0.25)
							addToList(pointFromCircle(r, i, zPos, false));

						drawPoints();
					}

					drawBevel(tmpStartAng, tmpEndAng);

				} else if (start0.y < middle0.y && end0.y > middle0.y) // left
				{
					if (drawEntirePie) {

						for (double i = 180; i >= tmpStartAng; i = i - 0.25)
							addToList(pointFromCircle(r, i, zPos, false));

						for (double i = tmpStartAng; i <= 180; i = i + 0.25)
							addToList(pointFromCircle(z1Rect, i, zPos, false));

						drawPoints();
					}

					for (double i = 180; i <= tmpEndAng; i = i + 0.25)
						addToList(pointFromCircle(z1Rect, i, zPos, false));

					for (double i = tmpEndAng; i >= 180; i = i - 0.25)
						addToList(pointFromCircle(r, i, zPos, false));

					drawPoints();
					drawBevel(tmpStartAng, tmpEndAng);
				} else if (start0.y >= middle0.y && end0.y > middle0.y
						&& sweepAngle > 180) // front
				{
					for (double i = 180; i >= 0; i = i - 0.25)
						addToList(pointFromCircle(r, i, zPos, false));

					for (double i = 0; i <= 180; i = i + 0.25)
						addToList(pointFromCircle(z1Rect, i, zPos, false));

					drawPoints();

					for (double i = tmpEndAng; i >= 180; i = i - 0.25)
						addToList(pointFromCircle(z1Rect, i, zPos, false));

					for (double i = 180; i <= tmpEndAng; i = i + 0.25)
						addToList(pointFromCircle(r, i, zPos, false));

					drawPoints();

					if (tmpStartAng > 0) {
						for (double i = 360; i >= tmpStartAng; i = i - 0.25)
							addToList(pointFromCircle(z1Rect, i, zPos, false));

						for (double i = tmpStartAng; i <= 360; i = i + 0.25)
							addToList(pointFromCircle(r, i, zPos, false));

						drawPoints();
					}

					drawBevel(tmpStartAng, tmpEndAng, true);
				} else {
					if (tmpEndAng < tmpStartAng)
						tmpEndAng += 360;

					for (double i = tmpStartAng; i <= tmpEndAng; i = i + 0.25)
						addToList(pointFromCircle(r, i, zPos, false));

					for (double i = tmpEndAng; i >= tmpStartAng; i = i - 0.25)
						addToList(pointFromCircle(z1Rect, i, zPos, false));

					drawPoints();
					drawBevel(tmpStartAng, tmpEndAng);
				}
			}
		}

		private void drawLeft() {
			if (sweepAngle < 360 && (drawEntirePie || iDrawSides)) {
				listSize = 0;

				if (isDonut) {
					if (hasBevel) {
						addToList(endD0);
						addToList(endB0);
						addToList(endB1);
						addToList(end1);
						addToList(endD1);
						drawPoints();
					} else {
						g.iPointDoubles[0] = endD0;
						g.iPointDoubles[1] = end0;
						g.iPointDoubles[2] = end1;
						g.iPointDoubles[3] = endD1;
						g.polygonFourDouble();
					}
				} else {
					if (hasBevel) {
						addToList(middle0);
						addToList(endB0);
						addToList(endB1);
						addToList(end1);
						addToList(middle1);
						drawPoints();
					} else {
						g.iPointDoubles[0] = middle0;
						g.iPointDoubles[1] = end0;
						g.iPointDoubles[2] = end1;
						g.iPointDoubles[3] = middle1;
						g.polygonFourDouble();
					}
				}

			}
		}

		private void drawLighting(final EdgeStyle edgeStyle, boolean last) {
			if (hasBevel) {
				if (gradientBrush == null)
					gradientBrush = new ChartBrush(g.getChart());

				final boolean oldPenVisible = g.getPen().getVisible();
				g.getPen().setVisible(false);
				g.setBrush(gradientBrush);

				switch (edgeStyle.getValue()) {
				case 0:
					doFlatGradient(zPos);
					break;
				case 1:
					doCurvedGradient(zPos);
					break;
				}

				g.getPen().setVisible(oldPenVisible);
			}
		}

		private void drawRight() {
			if (sweepAngle < 360 && (drawEntirePie || iDrawSides)) {
				listSize = 0;

				if (isDonut) {
					if (hasBevel) {
						addToList(startD0);
						addToList(startB0);
						addToList(startB1);
						addToList(start1);
						addToList(startD1);
						drawPoints();
					} else {
						g.iPointDoubles[0] = startD0;
						g.iPointDoubles[1] = start0;
						g.iPointDoubles[2] = start1;
						g.iPointDoubles[3] = startD1;
						g.polygonFourDouble();
					}
				} else {
					if (hasBevel) {
						addToList(middle0);
						addToList(startB0);
						addToList(startB1);
						addToList(start1);
						addToList(middle1);
						drawPoints();
					} else {
						g.iPointDoubles[0] = middle0;
						g.iPointDoubles[1] = start0;
						g.iPointDoubles[2] = start1;
						g.iPointDoubles[3] = middle1;
						g.polygonFourDouble();
					}
				}

			}
		}

		private void drawBevel(int startAng, int endAng, boolean split) {
			if (hasBevel) {
				int tmpStartAng = startAng;
				int tmpEndAng = endAng;

				if (split) {
					final boolean oldPenVisible = g.getPen().getVisible();
					g.getPen().setVisible(false);

					for (double i = 180; i >= 0; i = i - 0.25)
						addToList(pointFromCircle(z0BevelRect, i, zPos, false));

					for (double i = 0; i <= 180; i = i + 0.25)
						addToList(pointFromCircle(z1BevelRect, i, zPos, false));

					drawPoints();

					for (double i = tmpEndAng; i >= 180; i = i - 0.25)
						addToList(pointFromCircle(z1BevelRect, i, zPos, false));

					for (double i = 180; i <= tmpEndAng; i = i + 0.25)
						addToList(pointFromCircle(z0BevelRect, i, zPos, false));

					drawPoints();

					if (tmpStartAng > 0) {
						for (double i = 360; i >= tmpStartAng; i = i - 0.25)
							addToList(pointFromCircle(z1BevelRect, i, zPos,
									false));

						for (double i = tmpStartAng; i <= 360; i = i + 0.25)
							addToList(pointFromCircle(z0BevelRect, i, zPos,
									false));

						drawPoints();
					}

					g.getPen().setVisible(oldPenVisible);
				} else {
					if (tmpEndAng < tmpStartAng)
						tmpEndAng += 360;

					for (double i = tmpStartAng; i <= tmpEndAng; i = i + 0.25)
						addToList(pointFromCircle(z0BevelRect, i, zPos, false));

					for (double i = tmpEndAng; i >= tmpStartAng; i = i - 0.25)
						addToList(pointFromCircle(z1BevelRect, i, zPos, false));
				}

				drawPoints();
			}
		}

		private void drawBevel(int startAng, int endAng) {
			drawBevel(startAng, endAng, false);
		}

		private void drawTop(int startAng, int endAng) {
			listSize = 0;

			if (sweepAngle < 360 && !isDonut)
				addToList(middle0);

			final PieRect r = hasBevel ? z0BevelRect : z0Rect;

			for (double i = startAng; i <= endAng; i += 0.25)
				addToList(pointFromCircle(r, i, zPos, false));

			if (isDonut) {
				for (double i = endAng; i >= startAng; i -= 0.25)
					addToList(pointFromCircle(z0DonutRect, i, zPos, false));
			}

			if (sweepAngle < 360 && !isDonut)
				addToList(middle0);

			drawPoints(startAng, sweepAngle);
		}

		private void drawBottom(int startAng, int endAng) {
			if (drawEntirePie) {
				listSize = 0; // TJ71015695
				if (sweepAngle < 360 && !isDonut)
					addToList(middle1);

				for (double i = startAng; i <= endAng; i = i + 0.25)
					addToList(pointFromCircle(z1Rect, i, zPos, false));

				if (isDonut)
					for (double i = endAng; i >= startAng; i = i - 0.25)
						addToList(pointFromCircle(z1DonutRect, i, zPos, false));

				if (sweepAngle < 360 && !isDonut)
					addToList(middle1);

				drawPoints(startAng, sweepAngle);

			}
		}

		private void drawPoints(int startAng, int sweepAng) {
			if (listSize > 0) {
				// g.getPen().setVisible(false);
				// g.getPen().setVisible(true);
				// if (!g.fillPieSegment(pieRect.getLeft(),pieRect.getTop(),
				// (int)pieRect.getWidth(),(int)pieRect.getHeight(),
				// startAng, sweepAng,java.awt.geom.Arc2D.PIE))
				addToList(list[0]);
				g.polygon(list, listSize);
				// g.arc(pieRect,startAng,sweepAng);
				listSize = 0;
			}
		}

		// public void drawArc(java.awt.Graphics2D g2D,int x1,int y1,int x2,int
		// y2,int sd,int rd,int cl)
		// {
		// g.setBrush(this.gradientBrush);
		// java.awt.geom.Arc2D.Float arc1=new
		// java.awt.geom.Arc2D.Float(x1,y1,x2,y2,sd,rd,cl);
		// g2D.fill(arc1);
		// }

		private void drawPoints() {
			if (listSize > 0) {
				g.polygon(list, listSize);
				listSize = 0;
			}
		}

		private void doTopGradient(int zDepth) {
			g.getPen().setVisible(false);

			listSize = 0;

			for (double i = 0; i <= 360; i = i + 0.25)
				addToList(pointFromCircle(z0BevelRect, i, zDepth, false));

			final Gradient gr = gradientBrush.getGradient();

			gr.setVisible(true);
			gr.setStartColor(Color.fromArgb(255, 255, 255, 255));
			gr.setEndColor(Color.fromArgb(0, 255, 255, 255));
			gr.setDirection(GradientDirection.RADIAL);
                        gr.setRadialX((int) -Math.round(z0Rect.rect.width / 4.0));
			gr.setRadialY((int) -Math.round(z0Rect.rect.height / 4.0));
                        gr.setRadialTransition(2);
			gr.setUseMiddle(false);

			listSize = 0;

			if (!isDonut)
				addToList(middle0);

			for (double i = iStartAngle; i <= iEndAngle; i = i + 0.25)
				addToList(pointFromCircle(z0BevelRect, i, zDepth, false));

			if (isDonut)
				for (double i = iEndAngle; i >= iStartAngle; i = i - 0.25)
					addToList(pointFromCircle(z0DonutRect, i, zDepth, false));

			g.setBrush(gradientBrush);
			g.polygon(list, listSize);
		}

		private void doFlatGradient(int zDepth) {
			doTopGradient(zDepth);

			if ((iStartAngle <= 295 && iEndAngle >= 335)
					|| (iStartAngle >= 295 && iStartAngle <= 335)
					|| (iEndAngle >= 295 && iEndAngle <= 335)) {

				listSize = 0;

				for (double i = 295; i <= 335; i = i + 0.25)
					addToList(pointFromCircle(z1BevelRect, i, zDepth, false));

				for (double i = 335; i >= 295; i = i - 0.25)
					addToList(pointFromCircle(z0BevelRect, i, zDepth, false));

				final Gradient gr = gradientBrush.getGradient();

				gr.setVisible(true);
				gr.setStartColor(Color.fromArgb(0, 255, 255, 255));
				gr.setMiddleColor(Color.fromArgb(160, 255, 255, 255));
				gr.setUseMiddle(true);
				gr.setEndColor(Color.fromArgb(0, 255, 255, 255));

				gr.setDirection(GradientDirection.FORWARDDIAGONAL);
				// gr.setAngle(0);
				gr.setCustomTargetPolygon(PointDouble.round(list, listSize));

				listSize = 0;

				double start, end;

				if (iStartAngle <= 295 && iEndAngle >= 335) {
					start = 295;
					end = 335;
				} else if (iStartAngle >= 295 && iStartAngle <= 335) {
					start = iStartAngle;
					end = 335;
				} else if (iEndAngle >= 295 && iEndAngle <= 335) {
					start = 295;
					end = iEndAngle;
				} else {
					start = 0;
					end = 0;
				}

				for (double i = start; i <= end; i = i + 0.25)
					addToList(pointFromCircle(z1BevelRect, i, zDepth, false));

				for (double i = end; i >= start; i = i - 0.25)
					addToList(pointFromCircle(z0BevelRect, i, zDepth, false));

				g.setBrush(gradientBrush);
				g.polygon(list, listSize);
			}

		}

		private void doCurvedGradient(int zDepth) {
			doTopGradient(zDepth);

			if ((iStartAngle <= 295 && iEndAngle >= 335)
					|| (iStartAngle >= 295 && iStartAngle <= 335)
					|| (iEndAngle >= 295 && iEndAngle <= 335)) {
				listSize = 0;

				for (double i = 295; i <= 335; i = i + 0.25)
					addToList(pointFromCircle(z1BevelRect, i, zDepth, false));

				for (double i = 335; i >= 295; i = i - 0.25)
					addToList(pointFromCircle(z0BevelRect, i, zDepth, false));

				final Gradient gr = gradientBrush.getGradient();

				gr.setRadialX(0);
				gr.setRadialY(0);

				gr.setVisible(true);
				gr.setStartColor(Color.fromArgb(255, 255, 255, 255));
				// gr.setMiddleColor(Color.fromArgb(230, 255, 255, 255));
				gr.setEndColor(Color.fromArgb(0, 255, 255, 255));

				gr.setDirection(GradientDirection.RADIAL);
                                gr.setRadialTransition(1);
				gr.setAngle(135);
				gr.setCustomTargetPolygon(PointDouble.round(list, listSize));

				listSize = 0;

				double start, end;

				if (iStartAngle <= 295 && iEndAngle >= 335) {
					start = 295;
					end = 335;
				} else if (iStartAngle >= 295 && iStartAngle <= 335) {
					start = iStartAngle;
					end = 335;
				} else if (iEndAngle >= 295 && iEndAngle <= 335) {
					start = 295;
					end = iEndAngle;
				} else {
					start = 0;
					end = 0;
				}

				for (double i = start; i <= end; i = i + 0.25)
					addToList(pointFromCircle(z1BevelRect, i, zDepth, false));

				for (double i = end; i >= start; i = i - 0.25)
					addToList(pointFromCircle(z0BevelRect, i, zDepth, false));

				g.setBrush(gradientBrush);
				g.polygon(list, listSize);
			}
		}
	}

	/**
	 * Draws a pie slice using startAngle and endAngle with dark sides.
	 * 
	 * @param xCenter
	 *            int
	 * @param yCenter
	 *            int
	 * @param xRadius
	 *            int
	 * @param yRadius
	 *            int
	 * @param z0
	 *            int
	 * @param z1
	 *            int
	 * @param startAngle
	 *            double
	 * @param endAngle
	 *            double
	 * @param darkSides
	 *            boolean
	 * @param drawSides
	 *            boolean public void pie(int xCenter, int yCenter, int xRadius,
	 *            int yRadius, int z0, int z1, final double startAngle, final
	 *            double endAngle, boolean darkSides, boolean drawSides) {
	 *            pie(xCenter, yCenter, xRadius, yRadius, z0, z1, startAngle,
	 *            endAngle, darkSides, drawSides, 0); }
	 */

	/**
	 * Draws a 3D Pie slice using start Angle and end Angle and donut percent.
	 * 
	 * @param xCenter
	 *            int
	 * @param yCenter
	 *            int
	 * @param xRadius
	 *            int
	 * @param yRadius
	 *            int
	 * @param z0
	 *            int
	 * @param z1
	 *            int
	 * @param startAngle
	 *            double
	 * @param endAngle
	 *            double
	 * @param darkSides
	 *            boolean
	 * @param drawSides
	 *            boolean
	 * @param donutPercent
	 *            int public void pie(int xCenter, int yCenter, int xRadius, int
	 *            yRadius, int z0, int z1, final double startAngle, final double
	 *            endAngle, boolean darkSides, boolean drawSides, int
	 *            donutPercent) { if (pie3D == null) { pie3D = new Pie3D(this);
	 *            } }
	 */

	public void pie(int xCenter, int yCenter, int xOffset, int yOffset,
			int xRadius, int yRadius, int z0, int z1, final double startAngle,
			final double endAngle, boolean darkSides, boolean drawSides,
			int donutPercent, int bevelPercent, final EdgeStyle edgeStyle,
			boolean last/* , Shadow shadow */) {
		if (pie3D == null)
			pie3D = new Pie3D(this);

		pie3D.pie(xCenter + xOffset, yCenter - yOffset, xRadius, yRadius, z0,
				z1, startAngle, endAngle, darkSides, drawSides, donutPercent,
				bevelPercent, edgeStyle, last);
	}

	/**
	 * Obsolete.&nbsp;Use Line method instead.
	 * 
	 * @param x
	 *            int
	 * @param y
	 *            int
	 * @param x1
	 *            int
	 * @param y1
	 *            int
	 * @param z
	 *            int
	 */
	public void lineWithZ(int x, int y, int x1, int y1, int z) {
		line(x, y, x1, y1, z);
	}

	public void arc(final Rectangle rect, final double startAngle,
			final double sweepAngle) {
            arc(rect.getLeft(), rect.getTop(), rect.getRight(), rect.getBottom(),
                    startAngle, sweepAngle);
	}

	public abstract void arc(int x1, int y1, int x2, int y2,
			final double startAngle, final double sweepAngle);

	public void arc(final Rectangle rect, final Point start, final Point end) {
		arc(rect.x, rect.y, rect.getRight(), rect.getBottom(), start.x,
				start.y, end.x, end.y);
	}

	static protected double rad2Deg(final double radian) {
		return 180.0F * radian / Math.PI;
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
	 * @param x3
	 *            int
	 * @param y3
	 *            int
	 * @param x4
	 *            int
	 * @param y4
	 *            int
	 */
	public abstract void arc(int x1, int y1, int x2, int y2, int x3, int y3,
			int x4, int y4);

	/**
	 * Draws an Ellipse bounding Rectangle r.
	 * 
	 * @param r
	 *            Rectangle
	 */
	public void ellipse(final Rectangle r) {
		ellipse(r.x, r.y, r.getRight(), r.getBottom());
	}

	/**
	 * Ellipse bounding Rectangle r at z depth.
	 * 
	 * @param r
	 *            Rectangle
	 * @param z
	 *            int
	 */
	public void ellipse(final Rectangle r, int z) {
		ellipse(r.x, r.y, r.getRight(), r.getBottom(), z);
	}

	// CDI DrawZone
	protected abstract void transparentEllipse(int x1, int y1, int x2, int y2);

	// CDI DrawZone
	public void transparentEllipse(int x1, int y1, int x2, int y2, int z) {
		Point p1 = calc3DPos(x1, y1, z);
		Point p2 = calc3DPos(x2, y2, z);
		transparentEllipse(p1, p2);
	}

	protected void transparentEllipse(final Point p0, final Point p1) {
		transparentEllipse(p0.x, p0.y, p1.x, p1.y);
	}

	/**
	 * Ellipse bounding Rect (X1,Y1,X2,Y2) at Z position.
	 * 
	 * @param x1
	 *            int
	 * @param y1
	 *            int
	 * @param x2
	 *            int
	 * @param y2
	 *            int
	 * @param z
	 *            int
	 */
	public void ellipse(int x1, int y1, int x2, int y2, int z) {
		Point p1 = calc3DPos(x1, y1, z);
		Point p2 = calc3DPos(x2, y2, z);
		ellipse(p1, p2);
	}

	public void ellipse(final Point p0, final Point p1) {
		ellipse(p0.x, p0.y, p1.x, p1.y);
	}

	/**
	 * Ellipse bounding rectangle r with Z offset at angle.
	 * 
	 * @param r
	 *            Rectangle
	 * @param z
	 *            int
	 * @param angle
	 *            double
	 */
	public void ellipse(final Rectangle r, int z, final double angle) {
		ellipse(r.x, r.y, r.getRight(), r.getBottom(), z, angle);
	}

	public abstract void fillRectangle(final ChartBrush brush, int x, int y,
			int width, int height);

	public void ellipse(int x1, int y1, int x2, int y2, boolean enhanced) {
		ellipseEnh(x1, y1, x2, y2);
	}

	/**
	 * Enhanced Ellipse bounding rectangle (X1,Y1,X2,Y2) at Z position.
	 * 
	 * @param x1
	 *            int
	 * @param y1
	 *            int
	 * @param x2
	 *            int
	 * @param y2
	 *            int
	 * @param z
	 *            int
	 * @param enhanced
	 *            boolean
	 */
	public void ellipse(int x1, int y1, int x2, int y2, int z, boolean enhanced) {
		Point p1 = calc3DPos(x1, y1, z);
		Point p2 = calc3DPos(x2, y2, z);
		ellipseEnh(p1.x, p1.y, p2.x, p2.y);
	}

	public abstract void ellipseEnh(int x1, int y1, int x2, int y2);

	public void sphere(int x0, int y0, int x1, int y1) {
		sphere(Rectangle.fromLTRB(x0, y0, x1, y1), 0, false);
	}

	public void sphere(int x, int y, int z, int radius, boolean enhanced) {
		if (enhanced) {
			sphere(x - radius, y - radius, x + radius, y + radius, z, true);
		} else {
			sphere(x - radius, y - radius, x + radius, y + radius);
		}
	}

	public void sphere(int x1, int y1, int x2, int y2, int z, boolean enhanced) {
		final Point p1 = calc3DPos(x1, y1, z);
		final Point p2 = calc3DPos(x2, y2, z);
		sphere(new Rectangle(p1.x, p1.y, p2.x, p2.y), z, enhanced);
	}

	public void sphere(final Rectangle rect, int z, boolean enhanced) {
		if (enhanced) {
			sphereEnh(rect.x, rect.y, rect.getRight(), rect.getBottom());
		} else {
			sphere(rect.x, rect.y, rect.getRight(), rect.getBottom());
		}
	}

	protected abstract void sphereEnh(int x1, int y1, int x2, int y2);

	public abstract void ellipse(int x1, int y1, int x2, int y2);

	/**
	 * Draws a polygon (Point p1, Point p2, Point p3, Point p4) at Z depth
	 * offset.
	 * 
	 * @param p1
	 *            Point
	 * @param p2
	 *            Point
	 * @param p3
	 *            Point
	 * @param p4
	 *            Point
	 * @param z
	 *            int
	 */
	public void plane(final Point p1, final Point p2, final Point p3,
			final Point p4, int z) {
		calc3DPos(iPoints[0], p1.x, p1.y, z);
		calc3DPos(iPoints[1], p2.x, p2.y, z);
		calc3DPos(iPoints[2], p3.x, p3.y, z);
		calc3DPos(iPoints[3], p4.x, p4.y, z);
		polygonFour();
	}

	/**
	 * Draws a polygon (Point p1, Point p2) at Z depth offset.
	 * 
	 * @param p1
	 *            Point
	 * @param p2
	 *            Point
	 * @param z0
	 *            int
	 * @param z1
	 *            int
	 */
	public void plane(final Point p1, final Point p2, int z0, int z1) {
		calc3DPos(iPoints[0], p1.x, p1.y, z0);
		calc3DPos(iPoints[1], p2.x, p2.y, z0);
		calc3DPos(iPoints[2], p2.x, p2.y, z1);
		calc3DPos(iPoints[3], p1.x, p1.y, z1);
		polygonFour();
	}

	/**
	 * Draws a polygon using points array p with different start/end z depth
	 * offset.
	 * 
	 * @param z0
	 *            int
	 * @param z1
	 *            int
	 * @param p
	 *            Point[]
	 */
	public void plane(int z0, int z1, final Point[] p) {
		calc3DPos(iPoints[0], p[0].x, p[0].y, z0);
		calc3DPos(iPoints[1], p[1].x, p[1].y, z0);
		calc3DPos(iPoints[2], p[2].x, p[2].y, z1);
		calc3DPos(iPoints[3], p[3].x, p[3].y, z1);
		polygonFour();
	}

	private void internalApplyDark(final Color oldColor, int quantity) {
		brush.applyDark(oldColor, quantity);
	}

	/**
	 * Displays the 2D non-rotated label at the specified X Y screen
	 * coordinates.<br>
	 * Text is outputted to the correct internal drawing Graphics2D. <br>
	 * The X and Y coordinates must be valid and fit inside the Chart rectangle.
	 * It uses the current drawing Font attributes. <br>
	 * Writes text at the named x and y co-ordinates.
	 * 
	 * @param x
	 *            int
	 * @param y
	 *            int
	 * @param text
	 *            String
	 */
	public void textOut(int x, int y, final String text) {
		final ChartFont f = getFont();

		if (f.shouldDrawShadow()) {

			final Shadow s = f.getShadow();

			drawString(x + s.getWidth(), y + s.getHeight(), text, s.getBrush());
		}

		drawString(x, y, text, f.getBrush());
	}

	transient private Point textOutPoint = new Point(0, 0);

	/**
	 * Displays 2D non-rotated label at the specified x,y and z screen
	 * coordinates.<br>
	 * Text is outputted to the correct internal Drawing Canvas. <br>
	 * The X and Y coordinates must be valid and fit inside the Chart rectangle.
	 * It uses the current Chart.Drawing.Font attributes. <br>
	 * Writes text at the named co-ordinates.
	 * 
	 * @param x
	 *            int
	 * @param y
	 *            int
	 * @param z
	 *            int
	 * @param text
	 *            String
	 */
	public void textOut(int x, int y, int z, final String text) {
		calc3DPos(textOutPoint, x, y, z);

		if ((iZoomText) && (iZoomFactor != -1)) {

			final double old = font.getDrawingFontSize()
					* chart.getAspect().getFontZoom() * 0.01;

			final int tmp = (int) Math.max(1, iZoomFactor * old);

			if (old != tmp) {
				font.setSize(tmp);
			}
		}

		textOut(textOutPoint, text);
	}

	public void textOut(final Point p, final String text) {
		textOut(p.x, p.y, text);
	}

	/**
	 * Draws a rectangle with rounded corners of personalized curvature and
	 * size.
	 * 
	 * @param r
	 *            Rectangle
	 */
	public void roundRectangle(final Rectangle r) {
		roundRectangle(r, 8, 8);
	}

	public void roundRectangle(Rectangle rect, int roundRect) {
		roundRectangle(rect, roundRect, roundRect);
	}
    
    public void roundRectangle(final Rectangle r, int roundWidth, int roundHeight, int z, RoundRectangleStyle roundStyle) {
        PointDouble[] points = getClipRoundRectangle(r, roundWidth, roundStyle);
        polygon(points);
    }
    
    public PointDouble[] getClipRoundRectangle(Rectangle rect, int borderRound, RoundRectangleStyle borderStyle) {
        int tmpRounded = borderRound * 2; //backwards compatibility

        Rectangle topLeft = Utils.emptyRectangle();
        Rectangle topRight = Utils.emptyRectangle();
        Rectangle bottomLeft = Utils.emptyRectangle();
        Rectangle bottomRight = Utils.emptyRectangle();

        switch (borderStyle) {
            case Top:
                topLeft = Rectangle.fromLTRB(rect.getLeft(), rect.getTop(), rect.getLeft() + tmpRounded, rect.getTop() + tmpRounded);
                topRight = Rectangle.fromLTRB(rect.getRight() - tmpRounded, rect.getTop(), rect.getRight(), rect.getTop() + tmpRounded);
                break;
            case Bottom:
                bottomLeft = Rectangle.fromLTRB(rect.getLeft(), rect.getBottom() - tmpRounded, rect.getLeft() + tmpRounded, rect.getBottom());
                bottomRight = Rectangle.fromLTRB(rect.getRight() - tmpRounded, rect.getBottom() - tmpRounded, rect.getRight(), rect.getBottom());
                break;
            case Left:
                topLeft = Rectangle.fromLTRB(rect.getLeft(), rect.getTop(), rect.getLeft() + tmpRounded, rect.getTop() + tmpRounded);
                bottomLeft = Rectangle.fromLTRB(rect.getLeft(), rect.getBottom() - tmpRounded, rect.getLeft() + tmpRounded, rect.getBottom());
                break;
            case Right:
                topRight = Rectangle.fromLTRB(rect.getRight() - tmpRounded, rect.getTop(), rect.getRight(), rect.getTop() + tmpRounded);
                bottomRight = Rectangle.fromLTRB(rect.getRight() - tmpRounded, rect.getBottom() - tmpRounded, rect.getRight(), rect.getBottom());
                break;
            case All:
                topLeft = Rectangle.fromLTRB(rect.getLeft(), rect.getTop(), rect.getLeft() + tmpRounded, rect.getTop() + tmpRounded);
                topRight = Rectangle.fromLTRB(rect.getRight() - tmpRounded, rect.getTop(), rect.getRight(), rect.getTop() + tmpRounded);
                bottomLeft = Rectangle.fromLTRB(rect.getLeft(), rect.getBottom() - tmpRounded, rect.getLeft() + tmpRounded, rect.getBottom());
                bottomRight = Rectangle.fromLTRB(rect.getRight() - tmpRounded, rect.getBottom() - tmpRounded, rect.getRight(), rect.getBottom());
                break;
        }

        ArrayList<PointDouble> points = new ArrayList<PointDouble>();

        if (borderStyle == RoundRectangleStyle.Top || borderStyle == RoundRectangleStyle.Left || borderStyle == RoundRectangleStyle.All) {
            for (int i = 90; i <= 180; i++) {
                points.add(pointFromEllipse(topLeft, i));
            }
        } else {
            points.add(new PointDouble(rect.getLeft(), rect.getTop()));
        }

        if (borderStyle == RoundRectangleStyle.Bottom || borderStyle == RoundRectangleStyle.Left || borderStyle == RoundRectangleStyle.All) {
            for (int i = 180; i <= 270; i++) {
                points.add(pointFromEllipse(bottomLeft, i));
            }
        } else {
            points.add(new PointDouble(rect.getLeft(), rect.getBottom()));
        }

        if (borderStyle == RoundRectangleStyle.Bottom || borderStyle == RoundRectangleStyle.Right || borderStyle == RoundRectangleStyle.All) {
            for (int i = 270; i <= 360; i++) {
                points.add(pointFromEllipse(bottomRight, i));
            }
        } else {
            points.add(new PointDouble(rect.getRight(), rect.getBottom()));
        }

        if (borderStyle == RoundRectangleStyle.Top || borderStyle == RoundRectangleStyle.Right || borderStyle == RoundRectangleStyle.All) {
            for (int i = 1; i <= 90; i++) {
                points.add(pointFromEllipse(topRight, i));
            }
        } else {
            points.add(new PointDouble(rect.getRight(), rect.getTop()));
        }

        PointDouble[] result = new PointDouble[points.size()];
        return points.toArray(result);
    }
    
	static public Point rectCenter(final Rectangle r) {
		return new Point((r.x + r.getRight()) / 2, (r.y + r.getBottom()) / 2);
	}

	static public int[] getRectCenter(final Rectangle r) {
		int x = (r.getLeft() + r.getRight()) / 2;
		int y = (r.getTop() + r.getBottom()) / 2;
		int[] center = new int[2];
		center[0] = x;
		center[1] = y;
		return center;
	}

	final private static double perspecFactor = 1.0 / 150.0;

	public void calcPerspective(final Rectangle r) {
		final int perspec = aspect.getPerspective();
		iPerspec = (perspec > 0);

		if (iPerspec) {
			int tmp = r.width;
			if (tmp < 1) {
				tmp = 1;
			}

			iZoomPerspec = iZoomFactor * perspec * perspecFactor / tmp;

		}
	}

	/**
	 * Internal use. Calculates the projection co-ordinates for rectangle
	 * Bounds.<br>
	 * 
	 * @param maxDepth
	 *            int is the max shape depth
	 * @param r
	 *            Rectangle is the projected shape rectangle
	 */
	public void projection(int maxDepth, final Rectangle r) {
		xCenter = (int) (r.x + (r.width * 0.5) + getRotationCenter().x);
		yCenter = (int) (r.y + (r.height * 0.5) + getRotationCenter().y);
		zCenter = (int) ((maxDepth * 0.5) + getRotationCenter().z);
		xCenterOffset = xCenter + aspect.getHorizOffset();
		yCenterOffset = yCenter + aspect.getVertOffset();

		calcPerspective(r);
	}

	/**
	 * Draws a vertical or horizontal Pyramid with optional dark shaded sides.
	 * 
	 * @param vertical
	 *            boolean
	 * @param r
	 *            Rectangle
	 * @param z0
	 *            int
	 * @param z1
	 *            int
	 * @param darkSides
	 *            boolean
	 */
	public void pyramid(boolean vertical, final Rectangle r, int z0, int z1,
			boolean darkSides) {
		pyramid(vertical, r.x, r.y, r.getRight(), r.getBottom(), z0, z1,
				darkSides);
	}

	/**
	 * Draws a vertical or horizontal Pyramid with optional dark shaded sides.
	 * 
	 * @param vertical
	 *            boolean
	 * @param left
	 *            int
	 * @param top
	 *            int
	 * @param right
	 *            int
	 * @param bottom
	 *            int
	 * @param z0
	 *            int
	 * @param z1
	 *            int
	 * @param darkSides
	 *            boolean
	 */
	public void pyramid(boolean vertical, int left, int top, int right,
			int bottom, int z0, int z1, boolean darkSides) {
		Point p0, p1, p2, p3, pTop;

		final Color oldColor = brush.getSolid() ? brush.getColor()
				: getBackColor();

		if (vertical) {
			if (top != bottom) {
				p0 = calc3DPoint(left, bottom, z0);
				p1 = calc3DPoint(right, bottom, z0);
				pTop = calc3DPoint((left + right) / 2, top, (z0 + z1) / 2);
				polygon(p0, pTop, p1);
				p2 = calc3DPoint(left, bottom, z1);

				if (top < bottom) {
					if (p2.y < pTop.y) {
						polygon(p0, pTop, p2);
					}
				}
				if (darkSides) {
					internalApplyDark(oldColor, DARKERCOLORQUANTITY);
				}

				p3 = calc3DPoint(right, bottom, z1);
				polygon(p1, pTop, p3);

				if ((top < bottom) && (p2.y < pTop.y)) {
					polygon(pTop, p2, p3);
				}
			}
			if (top >= bottom) {
				if (darkSides) {
					internalApplyDark(oldColor, DARKCOLORQUANTITY);
				}
				rectangleY(left, bottom, right, z0, z1);
			}
		} else {
			if (left != right) {
				p0 = calc3DPoint(left, top, z0);
				p1 = calc3DPoint(left, bottom, z0);
				pTop = calc3DPoint(right, (top + bottom) / 2, (z0 + z1) / 2);
				polygon(p0, pTop, p1);

				if (darkSides) {
					internalApplyDark(oldColor, DARKCOLORQUANTITY);
				}
				p2 = calc3DPoint(left, top, z1);
				polygon(p0, pTop, p2);
			}
			if (left >= right) {
				if (darkSides) {
					internalApplyDark(oldColor, DARKERCOLORQUANTITY);
				}
				rectangleZ(left, top, bottom, z0, z1);
			}
		}
	}

	/**
	 * Draws a polygon of four points.
	 * 
	 * @param z0
	 *            int
	 * @param z1
	 *            int
	 * @param p
	 *            Point[]
	 */
	public void planeFour3D(int z0, int z1, final Point[] p) {
		calc3DPos(iPoints[0], p[0].x, p[0].y, z0);
		calc3DPos(iPoints[1], p[1].x, p[1].y, z0);
		calc3DPos(iPoints[2], p[2].x, p[2].y, z1);
		calc3DPos(iPoints[3], p[3].x, p[3].y, z1);
		polygonFour();
	}

	public abstract void eraseBackground(int left, int top, int right,
			int bottom);

	public abstract void rectangle(final Rectangle r);

	abstract public void roundRectangle(final Rectangle r, int roundWidth,
			int roundHeight);

	/**
	 * Sets / returns the color used to fill spaces when displaying text or
	 * filling with brushes of different style other than bsSolid.<br>
	 * Brush.Visible must be set to true.
	 * 
	 * @return Color
	 */
	public Color getBackColor() {
		return brush.getColor();
	}

	public void setBackColor(final Color value) {
		brush.setColor(value);
	}

	/**
	 * Returns if Canvas can do rotation and elevation of more than 90 degree.
	 * 
	 * @return boolean
	 */
	public boolean getSupportsFullRotation() {
		return false;
	}

	/**
	 * Draws items to a canvas to prevent flickering on screen.
	 * 
	 * @return boolean
	 */
	public boolean getUseBuffer() {
		return buffered;
	}

	public void setUseBuffer(boolean value) {
		buffered = value;

		if (chart.getParent() != null) {
			chart.getParent().doSetBuffered(buffered);
		}
	}

	/**
	 * Sets the Pixel location (using X,Y,Z) of the centre of rotation for use
	 * with the Aspect Rotation and Elevation properties.<br>
	 * 
	 * @return Point3D
	 */
	public Point3D getRotationCenter() {
		return rotationCenter;
	}

	public void setRotationCenter(final Point3D value) {
		rotationCenter = value;
		invalidate();
	}

	/**
	 * Returns the center Horizontal coordinate of the Chart. <br>
	 * This IS <b>NOT</b> the same as Chart1.Width / 2, because Chart margins,
	 * Axis margins, Legend width, etc are first subtracted from the Chart
	 * horizontal size.<br>
	 * In other words, Pie Series uses ChartXCenter for the Pie center.
	 * 
	 * @return int
	 */
	public int getChartXCenter() {
		return xCenter;
	}

	/**
	 * Returns the middle Vertical coordinate of the Chart. <br>
	 * This IS <b>NOT</b> the same as Chart1.Width / 2, because Chart margins,
	 * Axis margins, Legend width, etc are first subtracted from the Chart
	 * horizontal size.<br>
	 * In other words, Pie Series uses ChartXCenter for the Pie center.
	 * 
	 * @return int
	 */
	public int getChartYCenter() {
		return yCenter;
	}

	/**
	 * Determines the Font Height to be used for outputted text when using the
	 * Drawing.
	 * 
	 * @param f
	 *            ChartFont
	 * @return int
	 */
	public int fontTextHeight(final ChartFont f) {
		return textHeight(f, "W");
	}

	/**
	 * Defines the Height of the Font in pixels.
	 * 
	 * @return int
	 */
	public int getFontHeight() {
		return textHeight("W") + 2;
	}

	public void polygonFourDouble() {
		polygon(iPointDoubles);
	}

	private void polygonFour() {
		polygon(iPoints);
	}

	/**
	 * Horizontal Rectangle from Left to Right, from Z0 to Z1 position, at Top
	 * Y.<br>
	 * 
	 * @param left
	 *            int
	 * @param top
	 *            int
	 * @param bottom
	 *            int
	 * @param z0
	 *            int
	 * @param z1
	 *            int
	 */
	public void rectangleZ(int left, int top, int bottom, int z0, int z1) {
		calc3DPos(iPoints[0], left, top, z0);
		calc3DPos(iPoints[1], left, top, z1);
		calc3DPos(iPoints[2], left, bottom, z1);
		calc3DPos(iPoints[3], left, bottom, z0);
		polygonFour();
	}

	/**
	 * Horizontal Rectangle from Left to Right, from Z0 to Z1 position, at Top
	 * Y.<br>
	 * 
	 * @param left
	 *            int
	 * @param top
	 *            int
	 * @param right
	 *            int
	 * @param z0
	 *            int
	 * @param z1
	 *            int
	 */
	public void rectangleY(int left, int top, int right, int z0, int z1) {
		calc3DPos(iPoints[0], left, top, z0);
		calc3DPos(iPoints[1], right, top, z0);
		calc3DPos(iPoints[2], right, top, z1);
		calc3DPos(iPoints[3], left, top, z1);
		polygonFour();
	}

	/**
	 * Obsolete.&nbsp;Please use Rectangle method instead.
	 * 
	 * @param r
	 *            Rectangle
	 * @param z
	 *            int
	 */
	public void rectangleWithZ(final Rectangle r, int z) {
		rectangle(r, z);
	}

	/**
	 * Draws a rectangle between the defined co-ordinates with z depth.
	 * 
	 * @param left
	 *            int
	 * @param top
	 *            int
	 * @param right
	 *            int
	 * @param bottom
	 *            int
	 * @param z
	 *            int
	 */
	public void rectangle(int left, int top, int right, int bottom, int z) {
		rectangle(Rectangle.fromLTRB(left, top, right, bottom), z);
	}

	/**
	 * Draws a Rectangle (Rectangle r) at z depth.
	 * 
	 * @param r
	 *            Rectangle
	 * @param z
	 *            int
	 */
	public void rectangle(final Rectangle r, int z) {
		calc3DPos(iPoints[0], r.x, r.y, z);
		calc3DPos(iPoints[1], r.getRight(), r.y, z);
		calc3DPos(iPoints[2], r.getRight(), r.getBottom(), z);
		calc3DPos(iPoints[3], r.x, r.getBottom(), z);
		polygonFour();
	}

    public void draw(Rectangle r, Image image, boolean transparent) {
    	draw(r, image, transparent, true);
    }
    
	public abstract void pie(int x1, int y1, int x2, int y2,
			final double startAngle, final double endAngle);

	public abstract void polygon(final Point[] p);

	public abstract void polygon(final PointDouble[] p);

	public abstract boolean fillPieSegment(int x1, int y1, int x2, int y2,
			int sd, int rd, int cl);

	public void polygon(final Point3D[] p) {
		final Point[] points = new Point[p.length];

		for (int t = 0; t < p.length; t++)
			points[t] = calc3DPoint(p[t]);

		polygon(points);
	}

	public void polygon(final PointDouble[] p, int size) {
		final PointDouble[] points = new PointDouble[size];

		for (int i = 0; i < size; i++)
			points[i] = new PointDouble(p[i].x, p[i].y);

		polygon(points);
	}

        public void polygon(double z, final PointDouble[] p)
        {
            final Point[] points = new Point[p.length];
            
            for (int t = 0; t < p.length; t++)
                points[t] = calc3DPoint(new Point((int) Math.round(p[t].x),
                                         (int) Math.round(p[t].y)), (int) Math.round(z));

            polygon(points);
        }

	/**
	 * Draws a polygon with z position offset.
	 * 
	 * @param z
	 *            int
	 * @param p
	 *            Point[]
	 */
	public void polygon(int z, final Point[] p) {
		for (int t = 0; t < p.length; t++) {
			p[t] = calc3DPoint(p[t], z);
		}
		polygon(p);
	}

	public void polygon(final Point p0, final Point p1, final Point p2) {
		final Point[] p = { p0, p1, p2 };
		polygon(p);
	}

	protected final Polygon getPolygon(final Point[] p) {
		final Polygon tmp = new Polygon();

		for (int t = 0; t < p.length; t++)
			tmp.addPoint(p[t].x, p[t].y);

		return tmp;
	}

	private static final int NUMCYLINDERSIDES = 16;
	private static final double STEP = 2.0 * Math.PI / NUMCYLINDERSIDES;
	private static final int STEPCOLOR = 256 / NUMCYLINDERSIDES;

	private void internalCylinder(boolean vertical, Rectangle r, int z0,
			int z1, boolean dark3D, int conePercent) {
		int tmpSize, tmpMid, radius, t, numSide;

		Point3D[] poly = new Point3D[NUMCYLINDERSIDES];
		Point[] tmpPoly = new Point[4];

		double tmpSin;
		double tmpCos;
		boolean tmp;

		// if ( conePercent==0 ) conePercent=TeeDefaultConePercent;

		final Color oldColor = brush.getSolid() ? brush.getColor()
				: getBackColor();

		int zRadius = (z1 - z0) / 2;
		int tmpMidZ = (z1 + z0) / 2;

		if (vertical) {
			radius = (r.getRight() - r.x) / 2;
			tmpMid = (r.getRight() + r.x) / 2;
			tmpSize = r.height;

			for (t = 0; t < NUMCYLINDERSIDES; t++) {
				tmpSin = Math.sin((t - 3) * STEP);
				tmpCos = Math.cos((t - 3) * STEP);

				poly[t] = new Point3D();

				poly[t].x = tmpMid + Utils.round(tmpSin * radius);

				/*
				 * if (r.y < r.getBottom()) { poly[t].y = r.y; } else {
				 * poly[t].y = r.getBottom(); }
				 */
				poly[t].y = r.y;

				poly[t].z = tmpMidZ - Utils.round(tmpCos * zRadius);
			}

		} else {
			radius = (r.getBottom() - r.y) / 2;
			tmpMid = (r.getBottom() + r.y) / 2;
			tmpSize = r.width;

			for (t = 0; t < NUMCYLINDERSIDES; t++) {
				tmpSin = Math.sin((t - 4) * STEP);
				tmpCos = Math.cos((t - 4) * STEP);

				poly[t] = new Point3D();

				/*
				 * if (r.x < r.getRight()) { poly[t].x = r.getRight(); } else {
				 * poly[t].x = r.x; }
				 */
				poly[t].x = r.x;

				poly[t].y = tmpMid + Utils.round(tmpSin * radius);
				poly[t].z = tmpMidZ - Utils.round(tmpCos * zRadius);
			}

		}

		radius = Utils.round(radius * conePercent * 0.01);
		zRadius = Utils.round(zRadius * conePercent * 0.01);

		if (vertical) {
			tmpPoly[1] = calc3DPoint(poly[0].x, poly[0].y + tmpSize, poly[0].z);
			tmpSin = Math.sin((0 - 3) * STEP);
			tmpCos = Math.cos((0 - 3) * STEP);
			poly[0].x = tmpMid + Utils.round(tmpSin * radius);
			poly[0].z = tmpMidZ - Utils.round(tmpCos * zRadius);
		} else {
			tmpPoly[1] = calc3DPoint(poly[0].x - tmpSize, poly[0].y, poly[0].z);
			tmpSin = Math.sin((0 - 4) * STEP);
			tmpCos = Math.cos((0 - 4) * STEP);
			poly[0].y = tmpMid + Utils.round(tmpSin * radius);
			poly[0].z = tmpMidZ - Utils.round(tmpCos * zRadius);
		}

		tmpPoly[0] = calc3DPoint(poly[0]);
		Point tp = tmpPoly[1];
		numSide = 0;

		for (t = 1; t < NUMCYLINDERSIDES; t++) {
			if (vertical) {
				tmpPoly[2] = calc3DPoint(poly[t].x, poly[t].y + tmpSize,
						poly[t].z);
				tmpSin = Math.sin((t - 3) * STEP);
				tmpCos = Math.cos((t - 3) * STEP);
				poly[t].x = tmpMid + Utils.round(tmpSin * radius);
				poly[t].z = tmpMidZ - Utils.round(tmpCos * zRadius);
			} else {
				tmpPoly[2] = calc3DPoint(poly[t].x - tmpSize, poly[t].y,
						poly[t].z);
				tmpSin = Math.sin((t - 4) * STEP);
				tmpCos = Math.cos((t - 4) * STEP);
				poly[t].y = tmpMid + Utils.round(tmpSin * radius);
				poly[t].z = tmpMidZ - Utils.round(tmpCos * zRadius);
			}

			tmpPoly[3] = calc3DPoint(poly[t]);
			if (!cull(tmpPoly[0], tmpPoly[1], tmpPoly[2])) {
				if (dark3D) {
					internalApplyDark(oldColor, STEPCOLOR
							* ((tmpSize < 0) ? NUMCYLINDERSIDES - numSide
									: numSide));
				}

				Point[] p = { tmpPoly[0], tmpPoly[1], tmpPoly[2], tmpPoly[3] };
				polygon(p);
			}
			tmpPoly[0] = tmpPoly[3];
			tmpPoly[1] = tmpPoly[2];
			numSide++;
		}

		tmpPoly[3] = calc3DPoint(poly[0]);
		tmpPoly[2] = tp;
		if (!cull(tmpPoly[0], tmpPoly[1], tmpPoly[2]))
			polygon(tmpPoly);

		calc3DPos(tmpPoly[0], 0, 0, 0);
		calc3DPos(tmpPoly[1], 0, 10, 0);
		calc3DPos(tmpPoly[2], 0, 10, 10);
		boolean coverAtLeft = cull(tmpPoly[0], tmpPoly[1], tmpPoly[2]);
		Point[] tmpPoly2 = new Point[NUMCYLINDERSIDES];
		if (!vertical && coverAtLeft) {
			if (conePercent == 0)
				conePercent = 1;
			for (t = 0; t < NUMCYLINDERSIDES; t++) {
				tmpPoly2[t] = calculate3DPosition(r.getLeft(),
						Utils.round(100.0 * poly[t].y / conePercent),
						Utils.round(100.0 * poly[t].z / conePercent));
			}
		} else {
			for (t = 0; t < NUMCYLINDERSIDES; t++) {
				tmpPoly2[t] = calc3DPoint(poly[t]);
			}
		}

		if (dark3D) {
			internalApplyDark(oldColor, STEPCOLOR
					* ((tmpSize < 0) ? NUMCYLINDERSIDES - numSide : numSide));
		}

		polygon(tmpPoly2);
	}

	public void cone(boolean vertical, int left, int top, int right,
			int bottom, int z0, int z1, boolean darkSides, int conePercent) {
		internalCylinder(vertical,
				Rectangle.fromLTRB(left, top, right, bottom), z0, z1,
				darkSides, conePercent);
	}

	/**
	 * Paints a cone. Use <br>
	 * ONLY with OPENGL</b>.
	 * 
	 * @param vertical
	 *            boolean
	 * @param left
	 *            int
	 * @param top
	 *            int
	 * @param right
	 *            int
	 * @param bottom
	 *            int
	 * @param z0
	 *            int
	 * @param z1
	 *            int
	 * @param darkSides
	 *            boolean
	 */
	public void cone(boolean vertical, int left, int top, int right,
			int bottom, int z0, int z1, boolean darkSides) {
		internalCylinder(vertical,
				Rectangle.fromLTRB(left, top, right, bottom), z0, z1,
				darkSides, 0);
	}

	/**
	 * Paints a cone.
	 * 
	 * @param vertical
	 *            boolean
	 * @param r
	 *            Rectangle
	 * @param z0
	 *            int
	 * @param z1
	 *            int
	 * @param darkSides
	 *            boolean
	 */
	public void cone(boolean vertical, Rectangle r, int z0, int z1,
			boolean darkSides) {
		internalCylinder(vertical, r, z0, z1, darkSides, 0);
	}

	/**
	 * Paints a cone with Cone Percent.<br>
	 * Use <br>
	 * ONLY with OPENGL</b>.<br>
	 * This parameter varies the apex size as a percentage of the base.<br>
	 * 
	 * @param vertical
	 *            boolean
	 * @param r
	 *            Rectangle
	 * @param z0
	 *            int
	 * @param z1
	 *            int
	 * @param darkSides
	 *            boolean
	 * @param conePercent
	 *            int varies the apex size as a percentage of the base.
	 */
	public void cone(boolean vertical, Rectangle r, int z0, int z1,
			boolean darkSides, int conePercent) {
		internalCylinder(vertical, r, z0, z1, darkSides, conePercent);
	}

	/**
	 * Sort an array of points by angles.
	 * 
	 * @param p
	 *            Point[]
	 * @param angles
	 *            double[]
	 * @param low
	 *            int
	 * @param high
	 *            int
	 */
	static private void quickSortAngle(Point[] p, double[] angles, int low,
			int high) {
		int lo = low;
		int hi = high;

		double midangle = angles[(lo + hi) / 2];

		do {
			while (angles[lo] < midangle) {
				lo++;
			}
			while (angles[hi] > midangle) {
				hi--;
			}
			if (lo <= hi) {
				// swap points
				Point tpoint = p[lo];
				p[lo] = p[hi];
				p[hi] = tpoint;
				// swap angles
				double tangle = angles[lo];
				angles[lo] = angles[hi];
				angles[hi] = tangle;
				lo++;
				hi--;
			}
		} while (lo <= hi);

		// perform quicksorts on subsections
		if (hi > low) {
			quickSortAngle(p, angles, low, hi);
		}
		if (lo < high) {
			quickSortAngle(p, angles, lo, high);
		}
	}

	/**
	 * Calculates the boundary points of the convex hull of a set of 2D xy
	 * points.&nbsp;Original
	 * 
	 * @param p
	 *            Point[]
	 * @return int
	 */
	public int convexHull(Point[] p) {
		if (p.length == 3) {
			return 3;
		} else if (p.length < 3) {
			return 0;
		} else {
			// find pivot point, which is known to be on the hull
			// point with lowest y - if there are multiple, point with highest x
			int lminY = 10000000;
			int lmaxX = 10000000;
			int lpivotIndex = 0;

			final int lbound = 0;

			int ubound = p.length - 1;
			int tmpubound = ubound - 1;

			for (int lIndex = lbound; lIndex <= ubound; lIndex++) {
				if (p[lIndex].y == lminY) {
					if (p[lIndex].x > lmaxX) {
						lmaxX = p[lIndex].x;
						lpivotIndex = lIndex;
					}
				} else if (p[lIndex].y < lminY) {
					lminY = p[lIndex].y;
					lmaxX = p[lIndex].x;
					lpivotIndex = lIndex;
				}
			}

			// put pivot into separate variable and remove from array
			Point lPivot = p[lpivotIndex];
			p[lpivotIndex] = p[ubound];

			// calculate angle to pivot for each point in the array
			// quicker to calculate dot product of point with a horizontal
			// comparison vector
			double[] langles = new double[tmpubound + 1];

			for (int lindex = 0; lindex <= tmpubound; lindex++) {
				int lvx = lPivot.x - p[lindex].x;
				int lvy = lPivot.y - p[lindex].y;

				// reduce to a unit-vector - length 1
				double tmp = Math.sqrt(lvx * lvx + lvy * lvy);
				langles[lindex] = (tmp == 0) ? 0.0 : lvx / tmp;
			}

			// sort the points by angle
			quickSortAngle(p, langles, 0, tmpubound);

			// step through array to remove points that are not part of the
			// convex hull
			int t = 1;
			boolean lrightturn = true;

			do {
				// assign points behind and in front of current point
				if (t == 0) {
					lrightturn = true;
				} else {
					Point lBehind = p[t - 1];
					Point lInFront = (t == tmpubound) ? lPivot : p[t + 1];

					// work out if we are making a right or left turn using
					// vector product
					lrightturn = ((lBehind.x - p[t].x) * (lInFront.y - p[t].y))
							- ((lInFront.x - p[t].x) * (lBehind.y - p[t].y)) < 0;
				}

				if (lrightturn) {
					t++;
				} else {
					int tmphigh = tmpubound;
					// remove point from convex hull
					if (t != tmphigh) {
						for (int i = t; i < tmphigh; i++) {
							p[i] = p[i + 1];
						}
					}
					tmpubound = tmphigh - 1;
					t--; // backtrack to previous point
				}
			} while (t != tmpubound);

			// add pivot back into points array
			tmpubound++;
			p[tmpubound] = lPivot;
			return tmpubound + 1;
		}
	}

	/**
	 * Draws cylinder toggle Boolean for vertical or horizontal cylinder.
	 * 
	 * @param vertical
	 *            boolean
	 * @param r
	 *            Rectangle
	 * @param z0
	 *            int
	 * @param z1
	 *            int
	 * @param darkSides
	 *            boolean
	 */
	public void cylinder(boolean vertical, final Rectangle r, int z0, int z1,
			boolean darkSides) {
		internalCylinder(vertical, r, z0, z1, darkSides, 100);
	}

	/**
	 * Returns true if two lines cross.
	 * 
	 * @param x1
	 *            double
	 * @param y1
	 *            double
	 * @param x2
	 *            double
	 * @param y2
	 *            double
	 * @param x3
	 *            double
	 * @param y3
	 *            double
	 * @param x4
	 *            double
	 * @param y4
	 *            double
	 * @return LineCross
	 */
	static public LineCross crossingLines(final double x1, final double y1,
			final double x2, final double y2, final double x3, final double y3,
			final double x4, final double y4) {
		final double a1 = y2 - y1;
		final double a2 = y4 - y3;
		final double b1 = x2 - x1;
		final double b2 = x4 - x3;

		final double slope1 = a1 / b1;
		final double slope2 = a2 / b2;
		final double intercept1 = y1 - (slope1 * x1);
		final double intercept2 = y3 - (slope2 * x3);

		final double dif = slope2 - slope1;

		LineCross result = new LineCross();

		if (Math.abs(dif) > 1E-15) {
			result.cross.x = (intercept1 - intercept2) / dif;
		} else {
			result.cross.x = x2;
		}

		result.cross.y = (slope1 * result.cross.x) + intercept1;

		final double c1 = (x1 * y2) - (x2 * y1);
		final double c2 = (x3 * y4) - (x4 * y3);

		result.result = ((((a1 * x3 - b1 * y3 - c1 > 0) ^ (a1 * x4 - b1 * y4
				- c1 > 0)) && ((a2 * x1 - b2 * y1 - c2 > 0) ^ (a2 * x2 - b2
				* y2 - c2 > 0))) || (((a1 * x3 - b1 * y3 - c1 != 0) ^ (a1 * x4
				- b1 * y4 - c1 != 0)) && ((a2 * x1 - b2 * y1 - c2 != 0) ^ (a2
				* x2 - b2 * y2 - c2 != 0))));

		return result;
	}

	/**
	 * Draws a Cube.
	 * 
	 * @param r
	 *            Rectangle
	 * @param z0
	 *            int
	 * @param z1
	 *            int
	 */
	public void cube(final Rectangle r, int z0, int z1) {
		cube(r.x, r.y, r.getRight(), r.getBottom(), z0, z1, true);
	}

	/**
	 * Draws a Cube with Dark Sides.
	 * 
	 * @param r
	 *            Rectangle
	 * @param z0
	 *            int
	 * @param z1
	 *            int
	 * @param darkSides
	 *            boolean
	 */
	public void cube(final Rectangle r, int z0, int z1, boolean darkSides) {
		cube(r.x, r.y, r.getRight(), r.getBottom(), z0, z1, darkSides);
	}

	private double culling() {
		return ((iPoints[3].x - iPoints[2].x) * (iPoints[1].y - iPoints[2].y))
				- ((iPoints[1].x - iPoints[2].x) * (iPoints[3].y - iPoints[2].y));
	}

	transient private Point[] cubePoints = new Point[4];

	/**
	 * Draws a Cube with Dark Sides.
	 * 
	 * @param left
	 *            int
	 * @param top
	 *            int
	 * @param right
	 *            int
	 * @param bottom
	 *            int
	 * @param z0
	 *            int
	 * @param z1
	 *            int
	 * @param darkSides
	 *            boolean
	 */
	public void cube(int left, int top, int right, int bottom, int z0, int z1,
			boolean darkSides) {
		final Color oldColor = brush.getColor();

		calc3DPos(cubePoints[0], left, top, z0);
		calc3DPos(cubePoints[1], right, top, z0);
		calc3DPos(cubePoints[2], right, bottom, z0);
		calc3DPos(cubePoints[3], right, top, z1);

		iPoints[0].set(cubePoints[0]);
		iPoints[1].set(cubePoints[1]);
		iPoints[2].set(cubePoints[2]);
		calc3DPos(iPoints[3], left, bottom, z0);

		if (culling() > 0) {
			polygonFour(); // front-side
		} else {
			calc3DPos(iPoints[0], left, top, z1);
			calc3DPos(iPoints[1], right, top, z1);
			calc3DPos(iPoints[2], right, bottom, z1);
			calc3DPos(iPoints[3], left, bottom, z1);
			polygonFour(); // back-side
		}

		calc3DPos(iPoints[2], right, bottom, z1);
		iPoints[0].set(cubePoints[1]);
		iPoints[1].set(cubePoints[3]);
		iPoints[3].set(cubePoints[2]);

		if (culling() > 0) {
			if (darkSides) {
				internalApplyDark(oldColor, DARKERCOLORQUANTITY);
			}
			polygonFour(); // left-side
		}

		iPoints[0].set(cubePoints[0]);
		calc3DPos(iPoints[1], left, top, z1);
		calc3DPos(iPoints[2], left, bottom, z1);
		calc3DPos(iPoints[3], left, bottom, z0);

		double tmp = (iPoints[3].x - iPoints[0].x)
				* (iPoints[1].y - iPoints[0].y) - (iPoints[1].x - iPoints[0].x)
				* (iPoints[3].y - iPoints[0].y);

		if (tmp > 0) {
			if (darkSides) {
				internalApplyDark(oldColor, DARKERCOLORQUANTITY);
			}
			polygonFour(); // right-side
		}

		calc3DPos(iPoints[3], left, top, z1);

		// culling
		tmp = (cubePoints[0].x - cubePoints[1].x)
				* (cubePoints[3].y - cubePoints[1].y)
				- (cubePoints[3].x - cubePoints[1].x)
				* (cubePoints[0].y - cubePoints[1].y);

		if (tmp > 0) {
			iPoints[0].set(cubePoints[0]);
			iPoints[1].set(cubePoints[1]);
			iPoints[2].set(cubePoints[3]);

			if (darkSides) {
				internalApplyDark(oldColor, DARKCOLORQUANTITY);
			}
			polygonFour(); // top-side
		}

		calc3DPos(iPoints[0], left, bottom, z0);
		calc3DPos(iPoints[2], right, bottom, z1);
		calc3DPos(iPoints[1], left, bottom, z1);
		iPoints[3].set(cubePoints[2]);

		if (culling() < 0) {
			if (darkSides) {
				internalApplyDark(oldColor, DARKCOLORQUANTITY);
			}
			polygonFour();
		}

		brush.setColor(oldColor);
	}

	public static Point[] sliceArray(final Point[] source, int length) {
		Point[] result = new Point[length];
		for (int t = 0; t < length; t++) {
			result[t] = source[t];
		}
		return result;
	}

	public static PointDouble[] sliceArray(final PointDouble[] source,
			int length) {
		PointDouble[] result = new PointDouble[length];
		for (int t = 0; t < length; t++) {
			result[t] = source[t];
		}
		return result;
	}

	private static final int MAXCIRCLESTEPS = 128;

	/**
	 * Draws a Donut slice.
	 * 
	 * @param xCenter
	 *            int center horizontal position
	 * @param yCenter
	 *            int center vertical position
	 * @param xRadius
	 *            int horizontal radius
	 * @param yRadius
	 *            int vertical radius
	 * @param startAngle
	 *            double angle from 0 to 360 of slice begin
	 * @param endAngle
	 *            double angle from 0 to 360 of slice end
	 * @param holePercent
	 *            int percent from 0 to 100 of donut center hole
	 */
	public void donut(int xCenter, int yCenter, int xRadius, int yRadius,
			final double startAngle, final double endAngle, int holePercent) {
		Point[] points = new Point[2 * MAXCIRCLESTEPS];

		final double tmpXRadius = holePercent * xRadius * 0.01;
		final double tmpYRadius = holePercent * yRadius * 0.01;

		int circleSteps = Utils.round(MAXCIRCLESTEPS * (endAngle - startAngle)
				/ Math.PI);

		if (circleSteps < 2) {
			circleSteps = 2;
		} else if (circleSteps > MAXCIRCLESTEPS) {
			circleSteps = MAXCIRCLESTEPS;
		}

		final double step = (endAngle - startAngle) / (circleSteps - 1);

		double tmpAngle = startAngle;

		for (int t = 1; t <= circleSteps; t++) {
			final double tmpSin = Math.sin(tmpAngle);
			final double tmpCos = Math.cos(tmpAngle);

			points[t] = new Point(xCenter + Utils.round(xRadius * tmpCos),
					yCenter - Utils.round(yRadius * tmpSin));

			int tmp = (t == 1) ? 0 : 2 * circleSteps - t + 1;

			points[tmp] = new Point(xCenter + Utils.round(tmpXRadius * tmpCos),
					yCenter - Utils.round(tmpYRadius * tmpSin));

			tmpAngle += step;
		}

		polygon(sliceArray(points, 2 * circleSteps));
	}

	/**
	 * Draws a line with an arrow head of ArrowWidth and ArrowHeight dimensions
	 * in pixels.
	 * 
	 * @param filled
	 *            boolean
	 * @param fromPoint
	 *            Point
	 * @param toPoint
	 *            Point
	 * @param headWidth
	 *            int
	 * @param headHeight
	 *            int
	 * @param z
	 *            int
	 */
	public void arrow(boolean filled, final Point fromPoint,
			final Point toPoint, int headWidth, int headHeight, int z) {
		/*
		 * pc |\ ph pf | \ |------------ \ ToPoint From |------------ / pg pe |
		 * / pd |/
		 */

		final int dx = toPoint.x - fromPoint.x;
		final int dy = fromPoint.y - toPoint.y;
		final double l = Math.sqrt(dx * dx + dy * dy);

		if (l > 0) { // if at least one pixel...
			ArrowPoint a = new ArrowPoint();
			a.z = z;
			a.g = this;

			int tmpHoriz = headWidth;
			int tmpVert = Math.min(Utils.round(l), headHeight);
			a.sinA = dy / l;
			a.cosA = dx / l;
			double xb = toPoint.x * a.cosA - toPoint.y * a.sinA;
			double yb = toPoint.x * a.sinA + toPoint.y * a.cosA;
			a.x = xb - tmpVert;
			a.y = yb - tmpHoriz * 0.5;

			Point pc = a.calc();
			a.y = yb + tmpHoriz * 0.5;
			Point pd = a.calc();

			if (filled) {
				double tmpHoriz4 = tmpHoriz * 0.25;
				a.y = yb - tmpHoriz4;
				Point pe = a.calc();
				a.y = yb + tmpHoriz4;
				Point pf = a.calc();
				a.x = fromPoint.x * a.cosA - fromPoint.y * a.sinA;
				a.y = yb - tmpHoriz4;
				Point pg = a.calc();
				a.y = yb + tmpHoriz4;
				Point ph = a.calc();

				Point[] tmp = { ph, pg, pe, pc, calc3DPoint(toPoint, z), pd, pf };
				polygon(tmp);
			} else {
				moveTo(fromPoint, z);
				lineTo(toPoint, z);
				lineTo(pd, z);
				moveTo(toPoint, z);
				lineTo(pc, z);
			}
		}
	}

	public void calcTrigValues() {
		double rx = 0, ry = 0, rz = 0;

		if (!aspect.getOrthogonal()) {
			rx = -aspect.getElevation();
			ry = -aspect.getRotation();
			rz = aspect.getTilt();
		}

		s1 = Math.sin(rx * Utils.PISTEP);
		c1 = Math.cos(rx * Utils.PISTEP);

		s2 = Math.sin(ry * Utils.PISTEP);
		c2 = Math.cos(ry * Utils.PISTEP);
		s3 = Math.sin(rz * Utils.PISTEP);
		c3 = Math.cos(rz * Utils.PISTEP);

		c2s3 = c2 * s3;
		c2c3 = Math.max(1E-5, c2 * c3);

		tempXX = Math.max(1E-5, s1 * s2 * s3 + c1 * c3);
		tempYX = (c3 * s1 * s2 - c1 * s3);

		tempXZ = (c1 * s2 * s3 - c3 * s1);
		tempYZ = (c1 * c3 * s2 + s1 * s3);

		// c2s1 = c2 * s1;
		// c2c1 = c1 * c2;

		iPerspec = false;
		iZoomPerspec = 0;
		iTilt = rz != 0;
	}

	public void initWindow(final Aspect a, final Rectangle r, int maxDepth) {
		bounds = r;
		setAspect(a);
		iZoomFactor = 1;

		// if (bitmap==null) bitmap=new Bitmap(r.width,r.height);

		if (is3D) {
			if (isOrtho) {
				double tmpAngle = aspect.getOrthoAngle();
				if (tmpAngle > 90) {
					iOrthoX = -1;
					tmpAngle = 180 - tmpAngle;
				} else {
					iOrthoX = 1;
				}

				final double o = aspect.getOrthoAngle() * Utils.PISTEP;
				final double tmpCos = Math.cos(o);

				iOrthoY = (tmpCos < 0.01) ? 1 : Math.sin(o) / tmpCos;
			}

			iZoomFactor = 0.01 * aspect.getZoom();
			iZoomText = aspect.getZoomText();
		}

		calcTrigValues();
	}

	private final Dimension textSize = new Dimension(0, 0);

	/**
	 * Returns the vertical text size in pixels of ChartFont f.
	 * 
	 * @param f
	 *            ChartFont
	 * @param text
	 *            String
	 * @return int Height in pixels of ChartFont f.
	 */
	public int textHeight(final ChartFont f, final String text) {
		measureString(textSize, f, text);
		return textSize.height;
	}

	/**
	 * Returns the horizontal text size in pixels of ChartFont f.
	 * 
	 * @param f
	 *            ChartFont
	 * @param text
	 *            String
	 * @return int Width in pixels of ChartFont f.
	 */
	public int textWidth(final ChartFont f, final String text) {
		measureString(textSize, f, text);
		return textSize.width;
	}

	/**
	 * Returns the vertical size in pixels of the text String.
	 * 
	 * @param text
	 *            String
	 * @return int Height in pixels of the text String.
	 */
	public int textHeight(final String text) {
		return textHeight(font, text);
	}

	/**
	 * Returns the horizontal size in pixels of the text String.
	 * 
	 * @param text
	 *            String
	 * @return int Width in pixels of the text String.
	 */
	public int textWidth(final String text) {
		return textWidth(font, text);
	}

	/**
	 * Draws a triangle (point p0, pointp1, pointp2) at Z position.
	 * 
	 * @param p0
	 *            Point
	 * @param p1
	 *            Point
	 * @param p2
	 *            Point
	 * @param z
	 *            int
	 */
	public void triangle(Point p0, Point p1, Point p2, int z) {
		p0 = calc3DPoint(p0.x, p0.y, z);
		p1 = calc3DPoint(p1.x, p1.y, z);
		p2 = calc3DPoint(p2.x, p2.y, z);
		polygon(p0, p1, p2);
	}

	/**
	 * Draws a triangle (Triangle3D p).
	 * 
	 * @param p
	 *            Triangle3D
	 */
	public void triangle(final Triangle3D p) {
		Point[] tmp = { calc3DPoint(p.p0), calc3DPoint(p.p1), calc3DPoint(p.p2) };
		polygon(tmp);
	}

	protected abstract void drawString(int x, int y, final String text,
			final ChartBrush brush);

	public abstract void measureString(Dimension size, final ChartFont f,
			final String text);

	/**
	 * Draws a Line from (X,Y,Z0) to (X,Y,Z1).
	 * 
	 * @param x
	 *            int
	 * @param y
	 *            int
	 * @param z0
	 *            int
	 * @param z1
	 *            int
	 */
	public void zLine(int x, int y, int z0, int z1) {
		calc3DPos(line0, x, y, z0);
		calc3DPos(line1, x, y, z1);
		line(line0, line1);
	}

	/**
	 * Draws Bezier splines for the Point array p at displacement z
	 * 
	 * @param z
	 *            int
	 * @param p
	 *            Point[]
	 */
	public void drawBeziers(int z, final Point[] p) {
		internalBezier(z, true, p);
	}

	/**
	 * Draws a rotated text String at the specified x,y and z coordinates with
	 * the RotDegree rotation angle.<br>
	 * RotDegree values must be between 0 and 360.<br>
	 * The string is drawn on the Chart Drawing Canvas.
	 * 
	 * @param x
	 *            int
	 * @param y
	 *            int
	 * @param z
	 *            int
	 * @param text
	 *            String
	 * @param rotDegree
	 *            double
	 */
	public void rotateLabel(int x, int y, int z, final String text,
			final double rotDegree) {
		rotateLabel(calc3DPos(x, y, z), text, rotDegree);
	}

	/**
	 * Draws a rotated text String at the specified Point x,y coordinates with
	 * the RotDegree rotation angle.<br>
	 * RotDegree values must be between 0 and 360.<br>
	 * The string is drawn on the Chart Drawing Canvas.
	 * 
	 * @param p
	 *            Point
	 * @param text
	 *            String
	 * @param rotDegree
	 *            double
	 */
	public void rotateLabel(final Point p, final String text,
			final double rotDegree) {
		rotateLabel(p.x, p.y, text, rotDegree);
	}

	private static final double HALFDIVPI = 180 / Math.PI;

	/**
	 * Draws a Pie slice.
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
	public void pie(int x1, int y1, int x2, int y2, int x3, int y3, int x4,
			int y4) {

		final int width = x2 - x1;
		final int height = y2 - y1;
		final int centerX = x1 + (width / 2);
		final int centerY = y1 + (height / 2);

		double theta = Utils.atan2(centerY - y3, x3 - centerX);
		if (theta < 0) {
			theta += 2.0 * Math.PI;
		}
		theta *= HALFDIVPI;

		double theta2 = Utils.atan2(centerY - y4, x4 - centerX);
		if (theta2 < 0) {
			theta2 += 2.0 * Math.PI;
		}
		theta2 *= HALFDIVPI;

		pie(x1, y1, x2, y2, theta, theta2 - theta);
	}

	private final Point line0 = new Point();
	private final Point line1 = new Point();

	/**
	 * Draws a Horizontal at z depth position.
	 * 
	 * @param left
	 *            int
	 * @param right
	 *            int
	 * @param y
	 *            int
	 * @param z
	 *            int
	 */
	public void horizontalLine(int left, int right, int y, int z) {
		calc3DPos(line0, left, y, z);
		calc3DPos(line1, right, y, z);
		line(line0, line1);
	}

	/**
	 * Returns bounding rectangle for point array p
	 * 
	 * @param p
	 *            Point[]
	 * @return Rectangle
	 */
	static public Rectangle polygonRect(final Point[] p) {
		Rectangle r = new Rectangle();

		if (p.length > 0) {
			r.x = p[0].x;
			r.width = 0;
			r.y = p[0].y;
			r.height = 0;

			for (int t = 0; t < p.length - 1; t++) {
				if (p[t].x < r.x) {
					r.width = r.getRight() - p[t].x;
					r.x = p[t].x;
				}
				if (p[t].x > r.getRight()) {
					r.width += p[t].x - r.getRight();
				}
				if (p[t].y < r.y) {
					r.height = r.getBottom() - p[t].y;
					r.y = p[t].y;
				}
				if (p[t].y > r.getBottom()) {
					r.height += p[t].y - r.getBottom();
				}
			}
		}

		return r;
	}

	// private Region GetCorner(Rectangle BaseRect)
	// {
	// int Diameter;
	// Rectangle ArcRect;
	// GraphicsPath RR=new GraphicsPath();
	//
	// if (BaseRect.Width > BaseRect.Height)
	// {
	//
	// Diameter = BaseRect.Height;
	// ArcRect = new Rectangle(BaseRect.Location, new Size(Diameter, Diameter));
	//
	// RR.AddArc(ArcRect, 90, 180);
	// ArcRect.x = BaseRect.Right - Diameter;
	// RR.AddArc(ArcRect, 270, 180);
	// }
	// else
	// {
	// if (BaseRect.Height > BaseRect.Width )
	// {
	// Diameter = BaseRect.Width;
	// ArcRect = new Rectangle(BaseRect.Location, new Size(Diameter, Diameter));
	// RR.AddArc(ArcRect, 180, 180);
	// ArcRect.y = BaseRect.Bottom - Diameter;
	// RR.AddArc(ArcRect, 0, 180);
	// }
	// else
	// {
	// RR.AddEllipse(BaseRect);
	// }
	//
	// RR.AddEllipse(BaseRect);
	// }
	// RR.CloseFigure();
	// return new Region(RR);
	// }
	//
	//
	// private Region RoundRectRegion(Rectangle BaseRect,double Radius)
	// {
	// if (Radius <= 0) return new Region(BaseRect);
	//
	// if (Radius >= (Math.min(BaseRect.Width, BaseRect.Height) / 2.0))
	// return GetCorner(BaseRect);
	//
	// int Diameter= (int)Utils.round(Radius + Radius);
	// Rectangle ArcRect = new Rectangle(BaseRect.Location, new Size(Diameter,
	// Diameter));
	// GraphicsPath RR = new GraphicsPath();
	//
	// RR.AddArc(ArcRect, 180, 90);
	//
	// ArcRect.x = BaseRect.Right - Diameter;
	// RR.AddArc(ArcRect, 270, 90);
	//
	// ArcRect.y = BaseRect.Bottom - Diameter;
	// RR.AddArc(ArcRect, 0, 90);
	//
	// ArcRect.x = BaseRect.x;
	// RR.AddArc(ArcRect, 90, 90);
	//
	// RR.CloseFigure();
	//
	//
	// return new Region(RR);
	// }
	//
	// public void RoundRect(int Left,int Top,int Right,int Bottom,int
	// RoundSize1, int RoundSize2)
	// {
	// Region
	// r=RoundRectRegion(Rectangle.FromLTRB(Left,Top,Right,Bottom),RoundSize1);
	// }
	// / <summary>
	// / Draws a sphere.
	// / </summary>
	public void sphere(int x, int y, int z, final double radius) {
		int tmp = Utils.round(radius);
		ellipse(x - tmp, y - tmp, x + tmp, y + tmp, z);
	}

	// private void BezierCorner(Point p0, Point c0, Point c1, Point p1,
	// ref Point[] result, int num)
	// {
	// double t, t_sq, t_cb, incr, r1, r2, r3, r4;
	// int i;
	//
	// t=incr=1.0/num;
	// for(i=0; i<num; i++)
	// {
	// t_sq = t * t;
	// t_cb = t * t_sq;
	// r1 = 1 - 3*t + 3*t_sq - t_cb;
	// r2 = 3*t - 6*t_sq + 3*t_cb;
	// r3 = 3*t_sq - 3*t_cb;
	// r4 = t_cb;
	// result[i].x = (int)Utils.round(r1*p0.x + r2*c0.x + r3*c1.x + r4*p1.x);
	// result[i].y = (int)Utils.round(r1*p0.y + r2*c0.y + r3*c1.y + r4*p1.y);
	// t+=incr;
	// }
	// }

	// / <summary>
	// / Draws a rectangle between the defined co-ordinates.
	// / </summary>
	// public void rectangle(Rectangle r) {
	// rectangle(r.x, r.y, r.x + r.width, r.y + r.height);
	// }

	/**
	 * Draws a rectangle between the defined co-ordinates.
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
	public void rectangle(int left, int top, int right, int bottom) {
		int tmpW, tmpH;

		if (left > right) {
			tmpW = left - right;
			left = right;
		} else {
			tmpW = right - left;
		}

		if (top > bottom) {
			tmpH = top - bottom;
			top = bottom;
		} else {
			tmpH = bottom - top;
		}

		rectangle(new Rectangle(left, top, tmpW, tmpH));
	}

	private static final int NUMCIRCLEPOINTS = 64;

	/**
	 * Ellipse bounding rectangle (Left, Top, Right, Bottom) with Z offset at
	 * angle.
	 * 
	 * @param left
	 *            int
	 * @param top
	 *            int
	 * @param right
	 *            int
	 * @param bottom
	 *            int
	 * @param z
	 *            int
	 * @param angle
	 *            double
	 */
	public void ellipse(int left, int top, int right, int bottom, int z,
			double angle) {
		Point[] p = new Point[NUMCIRCLEPOINTS]; // : Array[0..NumCirclePoints-1]
												// of TPoint;
		Point[] points = new Point[3];
		for (int t = 0; t < points.length; t++) {
			points[t] = new Point();
		}
		;
		final double xCenter = (right + left) * 0.5;
		final double yCenter = (bottom + top) * 0.5;
		final double xRadius = (right - left) * 0.5;
		final double yRadius = (bottom - top) * 0.5;
		angle *= Math.PI / 180.0;

		final double tmpPiStep = 2 * Math.PI / (NUMCIRCLEPOINTS - 1);

		// initial rotation (rotation matrix elements)
		final double tmpSinAngle = Math.sin(angle);
		final double tmpCosAngle = Math.cos(angle);

		for (int t = 0; t < NUMCIRCLEPOINTS; t++) {
			final double tmpSin = Math.sin(t * tmpPiStep);
			final double tmpCos = Math.cos(t * tmpPiStep);
			final double tmpX = xRadius * tmpSin;
			final double tmpY = yRadius * tmpCos;

			p[t] = new Point(Utils.round(xCenter
					+ (tmpX * tmpCosAngle + tmpY * tmpSinAngle)),
					Utils.round(yCenter
							+ (-tmpX * tmpSinAngle + tmpY * tmpCosAngle)));
		}

		if (brush.getVisible()) {
			boolean old = pen.getVisible();
			pen.setVisible(false);

			final int xc = Utils.round(xCenter);
			final int yc = Utils.round(yCenter);

			for (int t = 1; t < NUMCIRCLEPOINTS; t++) {
				// has to be in loop because the Polygon
				// transforms the positions from 3d to 2d in each pass
				points[0].x = xc;
				points[0].y = yc;
				points[1] = p[t - 1];
				points[2] = p[t];
				polygon(z, points);
			}
			// close it up with polygon from last to first
			points[0].x = xc;
			points[0].y = yc;
			points[1] = p[NUMCIRCLEPOINTS - 1];
			points[2] = p[0];
			polygon(z, points);

			pen.setVisible(old);
		}

		if (pen.getVisible()) {
			polyLine(z, p);
		}
	}

	/**
	 * Creates a Windows GDI clipping region and selects it into TChart.<!--
	 * -->Canvas device context handle.<br>
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
	public void clipRectangle(int left, int top, int right, int bottom) {
		clipRectangle(Rectangle.fromLTRB(left, top, right, bottom));
	}

	public abstract void clipRectangle(final Rectangle r);

	protected Rectangle correctRectangle(final Rectangle r) {
		if (r.height < 0) {
			r.y = r.getBottom();
			r.height = -r.height;
		}

		if (r.width < 0) {
			r.x = r.getRight();
			r.width = -r.width;
		}
		return r;
	}

	/**
	 * Obsolete.&nbsp;Please use UnClip() method instead.
	 */
	public void unClipRectangle() {
		unClip();
	}

	/**
	 * Draws a pyramid with a truncated apex of variable thickness.
	 * 
	 * @param r
	 *            Rectangle
	 * @param startZ
	 *            int
	 * @param endZ
	 *            int
	 * @param truncX
	 *            int
	 * @param truncZ
	 *            int
	 */
	public void pyramidTrunc(final Rectangle r, int startZ, int endZ,
			int truncX, int truncZ) {
		InternalPyramidTrunc p = new InternalPyramidTrunc();
		p.r = r;
		p.startZ = startZ;
		p.endZ = endZ;
		p.truncX = truncX;
		p.truncZ = truncZ;
		p.draw(this);
	}

	private void clipToRight(final Rectangle rect, int minZ, int maxZ) {
		Point[] p = new Point[6];

		p[0] = calc3DPoint(rect.x, rect.getBottom(), minZ);
		p[1] = calc3DPoint(rect.x, rect.y, minZ);

		{
			Point pa = calc3DPoint(rect.x, rect.y, maxZ);
			Point pb = calc3DPoint(rect.getRight(), rect.y, minZ);

			p[2] = (pb.y < pa.y) ? pb : pa;
		}

		p[3] = calc3DPoint(rect.getRight(), rect.y, maxZ);

		Point pc = calc3DPoint(rect.getRight(), rect.getBottom(), maxZ);
		Point pd = calc3DPoint(rect.getRight(), rect.y, minZ);

		p[4] = (pd.x > pc.x) ? pd : pc;

		p[5] = calc3DPoint(rect.getRight(), rect.getBottom(), minZ);
		if (p[5].x < p[0].x) {
			p[0].x = p[5].x;
			if (pd.y < p[0].y) {
				p[0].y = pd.y;
			}
		}

		clipPolygon(p);
	}

	private void clipToLeft(final Rectangle rect, int minZ, int maxZ) {
		clipPolygon(new Point[] { calc3DPoint(rect.x, rect.getBottom(), minZ),
				calc3DPoint(rect.x, rect.getBottom(), maxZ),
				calc3DPoint(rect.x, rect.y, maxZ),
				calc3DPoint(rect.getRight(), rect.y, maxZ),
				calc3DPoint(rect.getRight(), rect.y, minZ),
				calc3DPoint(rect.getRight(), rect.getBottom(), minZ) });
	}

	/**
	 * Creates a cubic Windows GDI clipping region.
	 * 
	 * @param rect
	 *            Rectangle
	 * @param minZ
	 *            int
	 * @param maxZ
	 *            int
	 */
	public void clipCube(final Rectangle rect, int minZ, int maxZ) {

		if (is3D) {
			if (aspect.getElevation() == 270) {
				if ((aspect.getRotation() == 270)
						|| (aspect.getRotation() == 360)) {
					Point tmpLT = calc3DPoint(rect.x, rect.y, minZ);
					Point tmpRB = calc3DPoint(rect.getRight(), rect.y, maxZ);
					clipRectangle(tmpLT.x, tmpLT.y, tmpRB.x, tmpRB.y);
					return;
				}
			}

			if (isOrtho) {
				if (aspect.getOrthoAngle() > 90) {
					clipToLeft(rect, minZ, maxZ);
				} else {
					clipToRight(rect, minZ, maxZ);
				}
			} else if (aspect.getRotation() >= 270) {
				clipToRight(rect, minZ, maxZ);
			}
		} else {
			clipRectangle(rect.x + 1, rect.y + 1, rect.getRight() - 1,
					rect.getBottom() - 1);
		}
	}

	public abstract void clipEllipse(final Rectangle r);

	public abstract void clipPolygon(final Point[] p);
	
	/**
	 * Paints the image in rectangle r.
	 * 
	 * @param r
	 *            Rectangle
	 * @param image
	 *            Image
	 * @param mode
	 *            ImageMode
	 * @param transparent
	 *            boolean
	 */
	public void draw(final Rectangle r, final Image image, ImageMode mode,
			boolean transparent) {

		if ((mode == ImageMode.TILE) || (mode == ImageMode.STRETCH)) {
			return; // image is drawn by TexturePaint
		}

		int imageWidth = image.getWidth(null);
		int imageHeight = image.getHeight(null);
		Rectangle iRect = new Rectangle(r);

		if (mode == ImageMode.CENTER) {
			iRect.x += (r.width - imageWidth) / 2;
			iRect.y += (r.height - imageHeight) / 2;
			iRect.width = imageWidth;
			iRect.height = imageHeight;
		} else if (mode == ImageMode.NORMAL) {
			iRect.width = imageWidth;
			iRect.height = imageHeight;
		}
		;

		draw(iRect, image, transparent);
	}

	public abstract void horizontalLine(int left, int right, int y);

	public abstract void pixel(int x, int y, int z, final Color color);

	/**
	 * Draws a series of line segments to join point array p at z displacement.
	 * 
	 * @param z
	 *            int
	 * @param p
	 *            Point[]
	 */
	public void polyLine(int z, final Point[] p) {
		int l = p.length;
		Point[] tmpP = new Point[l];
		for (int t = 0; t < l; t++) {
			tmpP[t] = calc3DPoint(p[t], z);
		}
		for (int t = 1; t < l; t++) {
			line(tmpP[t - 1], tmpP[t]);
		}
	}

	public abstract void polyLine(final Point[] p);

	public abstract void rotateLabel(int x, int y, final String text,
			final double rotDegree);

	public abstract void unClip();

	public abstract void verticalLine(int x, int top, int bottom);

	public abstract void line(int x0, int y0, int x1, int y1);

	public abstract void lineTo(int x, int y);

	/**
	 * Draws Bezier splines for the Point array p
	 * 
	 * @param p
	 *            Point[]
	 */
	public void drawBeziers(final Point[] p) {
		internalBezier(0, false, p);
	}

	private void drawBezier(boolean is3D, int z, final Point[] points, int first) {

		final Point p1 = points[first - 2];
		final Point p2 = points[first - 1];
		final Point p3 = points[first];

		Point p = new Point();

		for (int t = 1; t < 32; t++) {
			final double mu = (double) t / 32;
			final double mu2 = mu * mu;
			final double mum1 = 1 - mu;
			final double mum12 = mum1 * mum1;

			p.x = (int) (p1.x * mum12 + 2 * p2.x * mum1 * mu + p3.x * mu2);
			p.y = (int) (p1.y * mum12 + 2 * p2.y * mum1 * mu + p3.y * mu2);

			if (is3D) {
				lineTo(p, z);
			} else {
				lineTo(p);
			}
		}
	}

	private void internalBezier(int z, boolean is3D, final Point[] points) {
		if (is3D) {
			moveTo(points[0], z);
		} else {
			moveTo(points[0]);
		}

		drawBezier(is3D, z, points, 2);
		int t = 4;

		while (t < points.length) {
			drawBezier(is3D, z, points, t);
			t += 2;
		}
	}

	public abstract void prepareDrawImage();

	public boolean validState() {
		return true;
	}

	private void doBevelRect(final Rectangle rect, final ChartPen a,
			final ChartPen b) {
		Point topRight = new Point(rect.getRight() - 1, rect.y);
		Point bottomLeft = new Point(rect.x, rect.getBottom() - 1);
		Point bottomRight = new Point(rect.getRight() - 1, rect.getBottom() - 1);

		line(a, rect.getLocation(), topRight);
		line(a, rect.getLocation(), bottomLeft);
		line(b, bottomLeft, bottomRight);
		line(b, bottomRight, topRight);
	}

	public void paintBevel(final BevelStyle bevel, final Rectangle rect,
			int width, final Color one, final Color two) {
		ChartPen a, b;

		if (bevel == BevelStyle.RAISED) {
			a = new ChartPen(one);
			b = new ChartPen(two);
		} else {
			a = new ChartPen(two);
			b = new ChartPen(one);
		}

		// rect.height--;
		// rect.width--;

		int tmp = width;
		while (tmp > 0) {
			tmp--;
			doBevelRect(rect, a, b);
			rect.grow(-1, -1);
		}

		// rect.height++;
		// rect.width++;

		// a.Dispose();
		// b.Dispose();
	}

	private void drawBorder(final Rectangle rect, int bevelWidth,
			final ChartPen pen, int borderRound) {
		// TODO review
		// GraphicsPath path = new GraphicsPath();
		Rectangle path = new Rectangle();

		Rectangle region = new Rectangle(Rectangle.EMPTY);
		Rectangle tmpRect = rect;
		if (pen.getVisible()) {
			tmpRect.offset(1, 1);
			// tmpRect.Inflate(-2,-2);
			if (borderRound > 0) {
				// path.AddPath(GetClipRoundRectangle(tmpRect,borderRound,0),
				// false);
				path.add(tmpRect); // to remove
			} else {
				path.add(tmpRect);
			}
			tmpRect.offset(-1, -1);
			tmpRect.inflate(2, 2);
			tmpRect.inflate(-bevelWidth, -bevelWidth);
			if (borderRound > 0) {
				// path.AddPath(GetClipRoundRectangle(tmpRect,borderRound,BevelWidth),
				// false);
				path.add(tmpRect); // to remove
			} else {
				path.add(tmpRect);
			}

			rectangle(path);
		}
	}

	public Rectangle orientRectangle(final Rectangle rect) {
		if (rect.getLeft() > rect.getRight()) {
			rect.x = rect.getRight();
			rect.width = Math.abs(rect.width);
		}
		if (rect.getTop() > rect.getBottom()) {
			rect.y = rect.getBottom();
			rect.height = Math.abs(rect.height);
		}

		return rect;
	}

	public void fillRectangle(final Rectangle rect) {
		fillRectangle(brush, rect.x, rect.y, rect.width, rect.height);
	}

	private void fillBorder(final Rectangle rect, int bevelWidth,
			final ChartBrush brush, int borderRound) {
		// TODO review
		// GraphicsPath path = new GraphicsPath();
		Rectangle path = new Rectangle();

		Rectangle region = new Rectangle(Rectangle.EMPTY);
		Rectangle tmpRect = rect;

		ChartBrush drawingBrush;
		/*
		 * TODO if (brush.getGradientVisible()) drawingBrush =
		 * brush.getGradient().drawingbrush(rect); else drawingBrush =
		 * brush.tDrawingBrush();
		 */
		if (brush.getVisible()) {
			if (borderRound > 0) {
				// path.add(g.GetClipRoundRectangle(tmpRect,borderRound,0),
				// false);
				path.add(tmpRect); // to remove
			} else {
				path.add(tmpRect);
			}

			region.union(path);
			// path.Reset();
			tmpRect.inflate(-bevelWidth, -bevelWidth);
			if (borderRound > 0) {
				// path.AddPath(GetClipRoundRectangle(tmpRect,borderRound,BevelWidth),
				// false);
				path.add(tmpRect); // to remove
			} else {
				path.add(tmpRect);
			}
			// region.Xor(path);

			fillRectangle(region);
		}
	}

	public void paintImageBevel(final Rectangle rect, int width,
			final ChartPen pen, final ChartBrush brush, int borderRound) {
		fillBorder(rect, width, brush, borderRound);
		drawBorder(rect, width, pen, borderRound);
	}

	public abstract void rectangle(final ChartBrush b, final Rectangle r);

	public void rectangle(final ChartBrush b, int x, int y, int right,
			int bottom) {
		rectangle(b, Rectangle.fromLTRB(x, y, right, bottom));
	}

	protected abstract void line(final ChartPen p, final Point a, final Point b);

	public abstract void setGraphics(Object value);

	public abstract void setPixel(int x, int y, int z, final Color color);

	public void setDirty(boolean value) {
	}

	public void drawBlur(final Rectangle rect, int smoothBlur, boolean isEllipse) {
		if (isEllipse)
			ellipse(rect);
		else
			rectangle(rect);
	}
        
	public void drawBlur(final Rectangle rect, int smoothBlur, int roundSize, boolean isEllipse) {
            if (roundSize > 0)
                roundRectangle(rect, roundSize);
            else
                drawBlur(rect, smoothBlur, isEllipse);
	}

	public void drawBlur(final PointDouble[] p, int smoothBlur) {
		polygon(p);
	}

}

final class CalcLineOutput {
	public LineOrientation orientation;
	public double slope;
	public double intercept;
}
