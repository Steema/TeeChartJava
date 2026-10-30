package com.steema.teechart.drawing;

import com.steema.teechart.Dimension;
import com.steema.teechart.Rectangle;
import com.steema.teechart.Aspect;
import com.steema.teechart.BevelStyle;
import com.steema.teechart.Point3D;
import com.steema.teechart.PointDouble;
import com.steema.teechart.Triangle3D;
import com.steema.teechart.ImageMode;
import com.steema.teechart.drawing.Graphics3D.RoundRectangleStyle;

/**
 * <p>Title: IGraphics3D class</p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright (c) 2005-2019 by Steema Software SL. All Rights Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 *
 */
public interface IGraphics3D {

	
	class PieRect {
		
		public PieRect(int left0, int top0, int right0, int bottom0) {
			rect = Rectangle.fromLTRB(left0, top0, right0, bottom0);
			center = rect.center();
			radiusH = rect.width * 0.5;
			radiusV = rect.height * 0.5;
		}
		
		Point center;
		Rectangle rect;
	    double radiusH;
	    double radiusV;
		
	}
	
    void initWindow(final Aspect a, final Rectangle r, int maxDepth);

    Aspect getAspect();

    ChartBrush getBrush();

    ChartPen getPen();

    ChartFont getFont();

    Gradient getGradient();

    void setBrush(final ChartBrush value);

    void setFont(final ChartFont value);

    void setPen(final ChartPen value);

    void arc(final Rectangle Rect, final double startAngle, final double endAngle);

    void arc(int left, int top, int right, int bottom, final double startAngle,
    		final double endAngle);

    void arc(final Rectangle rect, final Point start, final Point end);

    Point[] rotateRectangle(final Rectangle rect, final double angle);

    void rectangle(final Rectangle rect);

    void rectangle(int left, int top, int right, int bottom);

    void rectangle(int left, int top, int right, int bottom, int z);

    void rectangle(final ChartBrush brush, final Rectangle rect);

    void rectangle(final Rectangle rect, int z);

    void rectangleY(int x0, int y, int x1, int z0, int z1);

    void rectangleZ(int x, int y0, int y1, int z0, int z1);
    
    PointDouble pointFromEllipse(final Rectangle rectBounds, final double degrees, int zPos);

    PointDouble pointFromCircle(final PieRect rectBounds, final double degrees, int zPos, boolean clockWise);
    
    PointDouble pointFromCircle(final Rectangle rectBounds, final double degrees, int zPos, boolean clockWise);
    
    PointDouble pointFromCircle(Rectangle rectBounds, double degrees);
    
    PointDouble pointFromSpiral(final Rectangle rectBounds, final double degrees, final double twist);
      
    void polygon(final Point[] p);
    
    void polygon(final com.steema.teechart.PointDouble[] points);
    
    void polygon(final Point3D[] p);

    void polygon(int z, final Point[] p);

    void polygon(final ChartBrush brush, final Point[] p);
    
    void polygon(double z, final PointDouble[] p);

    void polyLine(int z, final Point[] p);

    void polyLine(final Point[] p);

    void roundRectangle(final Rectangle rect, int horizRound, int vertRound);

    void roundRectangle(final Rectangle r, int roundWidth, int roundHeight, int z, RoundRectangleStyle roundStyle);
    
    void roundRectangle(final Rectangle rect, int roundRect);
    
    void roundRectangle(final Rectangle rect);

    boolean getSupportsFullRotation();

    boolean getSupports3DText();

    boolean getMetafiling();

    void setMetafiling(boolean value);

    void drawBeziers(int z, final Point[] p);

    void drawBeziers(final Point[] p);

    void cylinder(boolean vertical, final Rectangle rect, int z0, int z1,
                  boolean dark3D);

    void textOut(int x, int y, final String text);

    void textOut(int x, int y, int z, final String text);

    void rotateLabel(int x, int y, int z, final String text, final double angle);

    void rotateLabel(int x, int y, final String text, final double angle);

    void measureString(Dimension size, final ChartFont font, final String text);

    int textWidth(final String text);

    int textWidth(final ChartFont font, final String text);

    int textHeight(final String text);

    int textHeight(final ChartFont font, final String text);

    int fontTextHeight(final ChartFont font);

    StringAlignment getTextAlign();

    void setTextAlign(final StringAlignment alignment);

    int getFontHeight();

    Rectangle calcRect3D(final Rectangle rect, int z);

    void paintBevel(final BevelStyle style, final Rectangle rect, int width,
    		final Color colorOne, final Color colorTwo);

    void paintImageBevel(final Rectangle rect, int width, final ChartPen pen,
    		final ChartBrush brush, int borderRound);

    void setColorPalette(final Color[] palette);

    Color getDefaultColor(int index);

    boolean getSmoothingMode();

    void setSmoothingMode(boolean value);

    void arrow(boolean solid, final Point from, final Point to, int headHeight,
               int headWidth, int z);
    
    Point calc3DPoint(final Point p, int z);

    Point calc3DPoint(int x, int y, int z);

    Point calc3DPoint(final Point3D p);
    
    Point calc3DPos(final Point3D source);
    
    Point calc3DPos(final Point source, int z);
    
    void calc3DPos(Point result, final Point source);
    
    void calc3DPos(Point p, int x, int y, int z);
    
    void calc3DPos(PointDouble p, double x, double y, double z);

    void verticalLine(int x, int y0, int y1);

    void verticalLine(int x, int y0, int y1, int z);

    void horizontalLine(int x0, int x1, int y);

    void horizontalLine(int x0, int x1, int y, int z);

    void line(final Point p0, final Point p1);

    void line(final Point p0, final Point p1, int z);

    void line(int x0, int y0, int x1, int y1);

    void line(int x0, int y0, int x1, int y1, int z);

    void line(int x0, int y0, int x1, int y1, int z0, int z1);

    void moveTo(final Point p);

    void moveTo(final Point3D p);

    void moveTo(final Point p, int z);

    void moveTo(int x, int y);

    void moveTo(int x, int y, int z);

    void lineTo(int x, int y);

    void lineTo(final Point p);

    void lineTo(final Point p, int z);

    void lineTo(int x, int y, int z);

    void lineTo(final Point3D point);

    void zLine(int x, int y, int z0, int z1);

    void ellipse(int left, int top, int right, int bottom);

    void ellipse(int left, int top, int right, int bottom,
                 boolean dark3D);

    void ellipse(int left, int top, int right, int bottom, int z);

    void ellipse(int left, int top, int right, int bottom, int z,
                 boolean enhanced);

    void ellipse(final Rectangle rect, int z);

    void ellipse(final Rectangle r, int z, final double angle);

    void ellipse(final Rectangle rect);

    void transparentEllipse(int x0, int y0, int x1, int y1, int z);

    void setPixel(int x, int y, int z, final Color color);

    void sphere(int x, int y, int z, int radius);

    void sphere(int x, int y, int z, int radius, boolean enhanced);

    void sphere(final Rectangle rect, int z, boolean enhanced);

    void cube(final Rectangle rect, int z0, int z1, boolean dark3D);

    void cube(int left, int top, int right, int bottom, int z0, int z1,
              boolean dark3D);

    void pyramid(boolean draw3D, int left, int top, int right,
                 int bottom, int z0,
                 int z1, boolean dark3D);

    void pyramid(boolean draw3D, final Rectangle rect, int z0, int z1,
                 boolean dark3D);

    void pyramidTrunc(final Rectangle rect, int z0, int z1, int truncX,
                      int truncZ);

    void triangle(final Triangle3D points);

    void triangle(final Point p0, final Point p1, final Point p2, int z);

    void plane(final Point p0, final Point p1, final Point p2, final Point p3, int z);

    void plane(final Point p0, final Point p1, int z0, int z1);

    Point calculate3DPosition(int x, int y, int z);
    
    Point calculate2DPosition(int x, int y, int z);

    Rectangle orientRectangle(final Rectangle rect);

    void planeFour3D(int startZ, int endZ, final Point[] p);
    
    void save();
    
    void restore();

    void clipRectangle(final Rectangle rect);

    void clipEllipse(final Rectangle rect);

    boolean getMonochrome();

    int getXCenter();

    int getYCenter();
    
	void clipCube(final Rectangle rect, int z0, int z1);

    void clipPolygon(final Point[] p);

    void unClip();

    void prepareDrawImage();

    void draw(int x, int y, final Image image);

    void draw(final Rectangle rect, final Image image, boolean transparent);
    
    void draw(final Rectangle rect, final Image image, boolean transparent, boolean smoothed);

    void draw(final Rectangle rect, final Image image, final ImageMode mode,
              boolean draw3D);

    void cone(boolean vertical, final Rectangle rect, int z0, int z1,
              boolean dark3D, int conePercent);

    void cone(boolean vertical, int left, int top, int right, int bottom,
              int z0, int z1, boolean dark3D, int conePercent);

    void cone(boolean vertical, int left, int top, int right, int bottom,
              int z0, int z1, boolean dark3D);

    void calcTrigValues();

    void calcPerspective(final Rectangle rect);

    void setBackColor(final Color value);

    void pie(int xCenter, int yCenter, int xOffset,  int yOffset, 
             int xRadius, int yRadius, int z0, int z1, double startAngle, 
             final double endAngle, boolean darkSides, boolean drawSides, int donutPercent,
             int bevelPercent, final EdgeStyle edgeStyle, boolean last);

    void donut(int x, int y, int xRadius, int yRadius,
    		final double startAngle, final double endAngle, int donutPercent);

    int convexHull(final Point[] points);

    void showImage();

    boolean getDirty();

    void setDirty(boolean value);

    void setXCenter(int value);

    void setYCenter(int value);

    void projection(int width3D, final Rectangle rect);

    boolean getTextSmooth();

    void setTextSmooth(boolean value);

    void changed(final Object value);

    void setGraphics(final Object value);

    int getColorPaletteLength();
    
    Color[] getColorPalette();

    void resetState();

    Object getGraphics();
    
    void rotateTransform(final Point p, final double rotDegree);
    
    void resetTransform();

    void drawBlur(final Rectangle r, int smoothBlur, boolean isEllipse);
    
    void drawBlur(final Rectangle r, int smoothBlur, int rectSize, boolean isEllipse);

    void drawBlur(final PointDouble[] p, int smoothBlur);

    Point[] fourPointsFromRect(final Rectangle rect, int z);

    Rectangle rectFromRectZ(Rectangle r, int z);

    int sizeToDIP(float value);

    int sizeToUnit(int unit, float value);
}

