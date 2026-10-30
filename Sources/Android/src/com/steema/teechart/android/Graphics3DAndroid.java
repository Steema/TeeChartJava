package com.steema.teechart.android;

import android.graphics.Bitmap;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Path.Direction;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Region;
import android.os.Build;
import android.util.TypedValue;

import com.steema.teechart.Dimension;
import com.steema.teechart.IBaseChart;
import com.steema.teechart.PointDouble;
import com.steema.teechart.Rectangle;
import com.steema.teechart.TChart;
import com.steema.teechart.drawing.ChartBrush;
import com.steema.teechart.drawing.ChartFont;
import com.steema.teechart.drawing.ChartPen;
import com.steema.teechart.drawing.Color;
import com.steema.teechart.drawing.DashStyle;
import com.steema.teechart.drawing.Graphics3D;
import com.steema.teechart.drawing.Image;
import com.steema.teechart.drawing.Point;
import com.steema.teechart.drawing.StringAlignment;

@SuppressWarnings("serial")
public class Graphics3DAndroid extends Graphics3D {

	public Graphics3DAndroid(IBaseChart c) {
		super(c);
	}

	protected int currentX;
	protected int currentY;

	private Canvas canvas;

	public void polygon(final ChartBrush brush, final Point[] p) {
		final Path path=new Path();
		
		path.moveTo(p[0].x, p[0].y);
		
		for (int t=1; t<p.length; t++) 
			path.lineTo(p[t].x, p[t].y);
		
		canvas.drawPath(path, brush.getPaint(p));
	}
	
	public void draw(final int x, final int y, final Image image) {
		if (image.bitmap != null)
			canvas.drawBitmap(image.bitmap, x, y, null);
	}
	
	public void showImage() {
		// TODO Auto-generated method stub
	}

	public void resetState() {
	}

    public int sizeToDIP(float value) {
        return sizeToUnit(TypedValue.COMPLEX_UNIT_DIP, value);
    }

    public int sizeToUnit(int unit, float value) {
        return (int) TypedValue.applyDimension(
                unit, value,
                ((TChart) chart.getParent()).getContext().getResources().getDisplayMetrics());
    }

	/*
	public Object getImagePixels(Image im) {
		int[] pixels = null;
		toBitmap(im).getPixels(pixels, 0, im.getWidth(null), 0, 0, im.getWidth(null), 
				im.getHeight(null));
		return pixels;
	}
*/

	public Object getGraphics() {
		return canvas;
	}

	int restoreCount=0;
	
	public void rotateTransform(final Point p, final double angle) {
		restoreCount=canvas.save();
		canvas.rotate((float)(360 - angle), p.x, p.y);
	}
	
	public void resetTransform() {
		if (restoreCount>0)
			canvas.restoreToCount(restoreCount);
		//canvas.setMatrix(null);
	}

	public void moveTo(int x, int y) {
		currentX=x;
		currentY=y;
	}

	public void arc(int x1, int y1, int x2, int y2, final double startAngle,
			final double sweepAngle) {
		if (pen.visible) {
			canvas.drawArc(new RectF(x1,y1,x2,y2), (float)startAngle+180, (float)sweepAngle, 
					false, pen.getPaint());
		}
	}

	public void arc(int x1, int y1, int x2, int y2, int x3, int y3, int x4,
			int y4) {
		// TODO Auto-generated method stub
	}

	protected void transparentEllipse(int x1, int y1, int x2, int y2) {
		// TODO Auto-generated method stub
	}

	public void fillRectangle(final ChartBrush brush, int x, int y, int width,
			int height) {
		canvas.drawRect(new Rect(x,y,x+width,y+height), 
				brush.getPaint(new Rectangle(x, y, width, height)));
	}

	public void ellipseEnh(int x1, int y1, int x2, int y2) {
		// TODO Auto-generated method stub
		
	}
	
	protected void sphereEnh(int x1, int y1, int x2, int y2) {
		// TODO Auto-generated method stub
		
	}

	public void ellipse(int x1, int y1, int x2, int y2) {
		final RectF rect = new RectF(x1,y1,x2,y2);
		
		if (brush.getVisible())
			canvas.drawOval(rect, brush.getPaint(new Rectangle(x1,y1,x2-x1,y2-y1)));
		
		if (pen.getVisible())
			canvas.drawOval(rect, pen.getPaint());
	}

	public void eraseBackground(int left, int top, int right, int bottom) {
		canvas.drawPaint(brush.getPaint(new Rectangle(left,top,right-left,bottom-top)));
	}

	public void rectangle(final Rectangle r) {
		final Rect rect = new Rect(r.x, r.y, r.getRight(), r.getBottom());
		
		if (brush.getVisible()) {
			final Image i = brush.getImage();
			
			if ((i != null) && (i.bitmap != null))
				canvas.drawBitmap(i.bitmap, null, rect, null);
			else
				canvas.drawRect(rect, brush.getPaint(r));
		}
		
		if (pen.getVisible()) {
            if (usePath(pen)) {
                Path path = new Path();
                path.moveTo(rect.left, rect.top);
                path.lineTo(rect.right, rect.top);
                path.lineTo(rect.right, rect.bottom);
                path.lineTo(rect.left, rect.bottom);
                path.lineTo(rect.left, rect.top);
                canvas.drawPath(path, pen.getPaint());
            }
            else {
				canvas.drawRect(rect, pen.getPaint());
			}
		}
	}

	private static final Rect toRect(final Rectangle r) {
		return new Rect(r.x, r.y, r.getRight(), r.getBottom());
	}

	public void roundRectangle(final Rectangle r, int roundWidth, int roundHeight) {
		final RectF rect = toRectF(r);
		
		if (brush.getVisible())
			canvas.drawRoundRect(rect, roundWidth, roundHeight, brush.getPaint(r));
		
		if (pen.getVisible())
			canvas.drawRoundRect(rect, roundWidth, roundHeight, pen.getPaint());
	}

	private static final RectF toRectF(final Rectangle r) {
		return new RectF(r.x, r.y, r.getRight(), r.getBottom());
	}

	public void draw(final Rectangle r, final Image image, boolean transparent, boolean smoothed) {

        Bitmap tmpBitmap = image.bitmap;
        if ((!smoothed) && (tmpBitmap.getWidth() != r.width) && (tmpBitmap.getHeight() != r.height)) {
            tmpBitmap = Bitmap.createScaledBitmap(tmpBitmap, r.width, r.height, false);
        }

        canvas.drawBitmap(tmpBitmap,
				null, 
				new Rect(r.x, r.y, r.getRight(), r.getBottom()), null);
	}

	public void pie(int x1, int y1, int x2, int y2, final double startAngle,
			final double endAngle) {
		
		final RectF rect = new RectF(x1,y1,x2,y2);
		final float sweep = (float)(endAngle-startAngle);
			
		if (brush.getVisible())
			canvas.drawArc(rect, (float)startAngle, sweep, true,
				brush.getPaint(new Rectangle(x1,y1,x2-x1,y2-y1)));
		
		if (pen.getVisible())
			canvas.drawArc(rect, (float)startAngle, sweep, true,
					pen.getPaint());
	}

	public void polygon(final Point[] p) {
        final Path path=new Path();
        path.reset();
        path.moveTo(p[0].x, p[0].y);
		
		for (int t=1; t<p.length; t++)
            path.lineTo(p[t].x, p[t].y);
		
		if (brush.getVisible())
			canvas.drawPath(path, brush.getPaint(p));
		
		if (pen.getVisible())
			canvas.drawPath(path, pen.getPaint());
	}

	public void polygon(final PointDouble[] p) {
		final Path path=new Path();
		path.moveTo((float)p[0].x, (float)p[0].y);
		
		for (int t=1; t<p.length; t++)
			path.lineTo((float)p[t].x, (float)p[t].y);
		
		if (brush.getVisible())
			canvas.drawPath(path, brush.getPaint(p));
		
		if (pen.getVisible())
			canvas.drawPath(path, pen.getPaint());
	}

	/*
	public boolean fillPieSegment(int x1, int y1, int x2, int y2, int sd,
			int rd, int cl) {
		// TODO Auto-generated method stub
		return false;
	}
	*/
	
	private final Dimension textSize=new Dimension(0,0);

	protected void drawString(int x, int y, final String text, final ChartBrush brush) {
		final Paint fontPaint = font.getPaint((float) (chart.getAspect().getFontZoom() * 0.01));
		
		fontPaint.getTextBounds(text, 0, text.length(), textBounds);
		textSize.width=textBounds.width()+2;
		textSize.height=getFontHeight();
		
		if (fontPaint.getColor() != brush.getColor().getRGB()) //TJ71016479
			fontPaint.setColor(brush.getColor().getRGB());
		
		final StringAlignment align = getTextAlign();
		
		if (align == StringAlignment.CENTER) {
			x -= (textSize.width / 2);
		} else 
		if (align == StringAlignment.FAR) {
			x -= textSize.width;
		}
		
		canvas.drawText(text, x, y + textSize.height - 2, fontPaint);
	}

	private final Rect textBounds=new Rect();

	public void measureString(Dimension size, final ChartFont f, final String text) {
		f.getPaint((float) (chart.getAspect().getFontZoom()*0.01))
			.getTextBounds(text, 0, text.length(), textBounds);
		
		size.width=textBounds.width()+2;
		size.height=textBounds.height()+2;
	}

	public void clipRectangle(final Rectangle r) {
		canvas.save();
		canvas.clipRect(toRect(r),Region.Op.INTERSECT);
	}
	
	public void clipEllipse(final Rectangle r) {
		final Path p=new Path();
		p.addOval(toRectF(r), Direction.CCW);
		canvas.save();
		canvas.clipPath(p,Region.Op.INTERSECT);
	}

	public void clipPolygon(final Point[] p) {
		final Path m = new Path();
		
		m.moveTo(p[0].x, p[0].y);
		for (int t = 1; t < p.length; t++)
			m.lineTo(p[t].x, p[t].y);
		
		canvas.save();
		canvas.clipPath(m,Region.Op.INTERSECT);
	}

	public void horizontalLine(int left, int right, int y) {
		line(left,y,right,y);
	}

	public void pixel(int x, int y, int z, final Color color) {
		canvas.drawPoint(x, y, brush.getPaint());
	}

	public void polyLine(final Point[] p) {
		final Path path=new Path();
		path.moveTo(p[0].x, p[0].y);
		
		for (int t=1; t<p.length; t++)
			path.lineTo(p[t].x, p[t].y);
		
		canvas.drawPath(path, pen.getPaint());
	}

	public void rotateLabel(int x, int y, final String text, final double angle) {
		canvas.save();
		canvas.translate(x, y);
		canvas.rotate(-(float) angle);
		textOut(0, 0, text);
		canvas.restore();
	}

	public void save() {
		restoreCount = canvas.save();
	}

	public void restore() {
		if (restoreCount>0) {
			canvas.restore();
		}
	}
	
	public void unClip() {
		restore();
		//canvas.clipRect(0, 0, bounds.width, bounds.height,  android.graphics.Region.Op.INTERSECT);
	}

	public void verticalLine(int x, int top, int bottom) {
		line(x,top,x,bottom);
	}
	
	public void line(int x0, int y0, int x1, int y1) {
		line(x0, y0, x1, y1, pen);
	}

	public void lineTo(int x, int y) {
		line(currentX,currentY,x,y, pen);
	}

	public void prepareDrawImage() {
		// TODO Auto-generated method stub
	}
	
	public void rectangle(final ChartBrush b, final Rectangle r) {
		canvas.drawRect(toRect(r), b.getPaint(r));
	}
	
	protected void line(final ChartPen p, final Point a, final Point b) {
		line(a.x,a.y,b.x,b.y, p);
	}

	protected void line(final int x0, final int y0, final int x1, final int y1, final ChartPen p) {
		if (usePath(p)) {
			Path path = new Path();
			path.moveTo(x0, y0);
			path.lineTo(x1, y1);
			canvas.drawPath(path, p.getPaint());
		}
		else
			canvas.drawLine(x0,y0,x1,y1,p.getPaint());

		currentX=x1;
		currentY=y1;
	}

	public void setGraphics(Object value) {
		if (value instanceof Canvas) {
			canvas=(Canvas)value;
		}
	}
	
	public void setPixel(int x, int y, int z, final Color color) {
		final Paint p=brush.getPaint();
		p.setColor(color.getRGB());
		canvas.drawPoint(x, y, p);
	}

	public boolean fillPieSegment(int x1, int y1, int x2, int y2, int sd,
			int rd, int cl) {
		// TODO Auto-generated method stub
		return false;
	}

    public void drawBlur(final Rectangle rect, int smoothBlur, boolean isEllipse) {
    	final Paint paint = getBrush().getPaint();
    	
    	if (smoothBlur < 0)
    		smoothBlur = 1;
    	
    	paint.setMaskFilter(new BlurMaskFilter(smoothBlur, BlurMaskFilter.Blur.NORMAL));
    	
    	if (isEllipse)
        	canvas.drawOval(toRectF(rect), paint);
    	else
    		canvas.drawRect(toRect(rect), paint);
    	
    	paint.setMaskFilter(null);
    }
	
	public void drawBlur(final PointDouble[] p, int smoothBlur) {
    	final Paint paint = getBrush().getPaint();
    	paint.setMaskFilter(new BlurMaskFilter(smoothBlur, BlurMaskFilter.Blur.NORMAL));
		polygon(p);
    	paint.setMaskFilter(null);
	}

	private boolean usePath(ChartPen p) {
        if (p.getStyle() == DashStyle.SOLID)
            return false;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
            return ((TChart)chart.getParent()).isHardwareAccelerated();

        return false;
    }
}
