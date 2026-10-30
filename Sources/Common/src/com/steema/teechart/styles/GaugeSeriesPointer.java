package com.steema.teechart.styles;

import com.steema.teechart.IBaseChart;
import com.steema.teechart.Shadow;
import com.steema.teechart.Rectangle;
import com.steema.teechart.PointDouble;
import com.steema.teechart.drawing.IGraphics3D;
import com.steema.teechart.drawing.Color;
import com.steema.teechart.drawing.Gradient;
import com.steema.teechart.drawing.Point;

public class GaugeSeriesPointer extends SeriesPointer {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int xMinusSmall, xPlusSmall, yMinusSmall, yPlusSmall;
    private double iStartAngle;

    public GaugeSeriesPointer(IBaseChart c, Series s)
    {
      super(c,s);
    }

//    protected override bool ShouldSerializeVisible()
//    {
//      return true;
//    }

    private GaugePointerStyles style;
    private PointerStyle tmpStyle;
    private Shadow shadow;

    public GaugePointerStyles getGaugePointerStyle()
    {
        tmpStyle = super.getStyle();
        if (tmpStyle != PointerStyle.NOTHING)
        {
          style = GaugePointerStyles.fromInt(tmpStyle.getValue());
        }
        return style;
    }
    
    public void setGaugePointerStyle(GaugePointerStyles value)
    {
        style = value;
        super.setStyle(getPointerStyle(style));
        this.getSeries().invalidate();
    }

    public Shadow getShadow()
    {
        if (shadow == null)
        {
          shadow = new Shadow(chart, 3);
          shadow.setDefaultVisible(true);
          shadow.setVisible(true);
        }
        return shadow;
    }
    
    public void setShadow(Shadow value)
    {
      shadow = value;
    }

    public void drawShadow(IGraphics3D g, float angle, int px, int py, int tmpHoriz, int tmpVert, GaugePointerStyles aStyle)
    {
      if (getShadow().getVisible())
      {
        setValues(px, py, tmpHoriz, tmpVert);

        if (getGaugePointerStyle() == GaugePointerStyles.HAND)
        {
          Point[] p ={ new Point(xMinus,yMinus),
                                        new Point(xPlus,yMinus),
                                        new Point(xPlusSmall,yPlusSmall),
                                        new Point(px,yPlus),
                                        new Point(xMinusSmall,yPlusSmall)
                                     };

          double endAngle = iStartAngle + 180;
          double heightStep = (getShadow().getHeight() * 2) / 180.0;
          double widthStep = (getShadow().getWidth() * 2) / 180.0;
          double tmpAngle = angle - iStartAngle;

          int tmpHeight = (int)Math.round(tmpAngle * heightStep);
          int tmpWidth = (int)Math.round(tmpAngle * widthStep);

          if (tmpHeight > getShadow().getHeight())
          {
            tmpHeight = getShadow().getHeight();
          }
          else if (tmpHeight < (getShadow().getHeight() * -1))
          {
            tmpHeight = getShadow().getHeight() * -1;
          }

          if (tmpWidth > getShadow().getWidth())
          {
            tmpWidth = getShadow().getWidth();
          }
          else if (tmpWidth < (getShadow().getWidth() * -1))
          {
            tmpWidth = getShadow().getWidth() * -1;
          }

          getShadow().draw(g, -tmpWidth, -tmpHeight, p);
        }
        else if (getGaugePointerStyle() == GaugePointerStyles.CENTER)
        {
          getShadow().draw(g, xMinus, yMinus, xPlus, yPlus);
        }
      }
    }

    private void setValues(int x, int y, int horiz, int vert)
    {
      iStartAngle = Math.atan(getShadow().getHeight() / getShadow().getWidth()) * (180.0 / Math.PI);
      iStartAngle += 90;
      xMinus = x - horiz;
      xPlus = x + horiz;
      yMinus = y - vert;
      yPlus = y + vert;
      xMinusSmall = xMinus + 2;
      xPlusSmall = xPlus - 2;

      yMinusSmall = yMinus + (horiz * 2);
      yPlusSmall = yPlus - (horiz * 2);
    }
    
    public void drawColorLine(IGraphics3D g, int startAngle, int endAngle, Rectangle container) {
        drawColorLine(g, startAngle, endAngle, container, true);
    }

    public void drawColorLine(IGraphics3D g, int startAngle, int endAngle, Rectangle container, boolean spiralled)
    {
      if (getVisible())
      {
        Color oldPenColor = getPen().getColor();
        Color oldBrushColor = getBrush().getColor();
        Gradient oldBrushGradient = getBrush().getGradient();

        prepareCanvas(g, getColor());

        int sweepAngle = Math.abs(endAngle - startAngle);

        int tmpStartAngle = Math.round(startAngle);
        tmpStartAngle += 90;
        int tmpEndAngle = tmpStartAngle + sweepAngle;

        int tmpRange = (tmpEndAngle-tmpStartAngle) + 1;

        final Point[] points = new Point[tmpRange*2];

        Rectangle center = (Rectangle)container.copy();
        
        int j=0;
        for (int i = tmpStartAngle; i <= tmpEndAngle; i++)
        {
          points[j] = PointDouble.round(g.pointFromCircle(center, i, 0, true));
          j++;
        }
        
        if(spiralled) {
            for (int i = tmpEndAngle; i >= tmpStartAngle; i--)
            {
                double twist = getVertSize() / -100.0;
                points[j]=PointDouble.round(g.pointFromSpiral(center, i * 1.0, twist));
                j++;
            }
        }
        else {
            center.inflate(getHorizSize() * 2, getHorizSize() * 2);
            for (int i = tmpEndAngle; i >= tmpStartAngle; i--) {
               points[j]=PointDouble.round(g.pointFromCircle(center, i, 0, true));
               j++;
            }
        }

        if (points.length > 0)
        {
          if (getGradient() != null && getGradient().getVisible())
            getGradient().setAngle(startAngle - 90);

          g.polygon(points);
        }

        getBrush().setColor(oldBrushColor);
        getBrush().setGradient(oldBrushGradient);
        getPen().setColor(oldPenColor);
      }
    }

    public void draw(IGraphics3D g, Point inner, Point innerPlus, Point innerMinus, Point outer, Point outerPlus, Point outerMinus)
    {
      if (getGaugePointerStyle() != GaugePointerStyles.TICK && style != GaugePointerStyles.MINORTICK)
      {
        draw(g, false, outer.x, outer.y - Math.round(getVertSize() / 2), Math.round(getHorizSize() / 2), Math.round(getVertSize() / 2), getColor(), style);
      }
      else 
      if (getGaugePointerStyle() == GaugePointerStyles.TICK && getVisible())
      {

        //prepareCanvas(g, getColor());
        Color oldPenColor = getPen().getColor();
        Color oldBrushColor = getBrush().getColor();
        Gradient oldBrushGradient = getBrush().getGradient();

        g.getBrush().setTransparency(getBrush().getTransparency());
        g.getBrush().setColor(getColor().transparentColor(g.getBrush().getTransparency()));

        g.polygon(new Point[] {outerPlus, outerMinus, innerMinus, innerPlus});

        getBrush().setColor(oldBrushColor);
        getBrush().setGradient(oldBrushGradient);
        getPen().setColor(oldPenColor);
      }
      else 
      if (getGaugePointerStyle() == GaugePointerStyles.MINORTICK && getVisible())
      {
        draw(g, false, outer.x, outer.y, getHorizSize(), getVertSize(), getColor(), GaugePointerStyles.CIRCLE);
      }
    }
    
    private PointerStyle getPointerStyle(GaugePointerStyles aStyle)
    {
      if (aStyle.getValue()<14)
        return PointerStyle.fromInt(aStyle.getValue());
      else
        return PointerStyle.NOTHING;
    }

    public void draw(IGraphics3D g, boolean is3D, int px, int py, int tmpHoriz, int tmpVert, Color colorValue, GaugePointerStyles aStyle)
    {
      if (aStyle != GaugePointerStyles.HAND && aStyle != GaugePointerStyles.CENTER)
      {
        super.draw(g, is3D, px, py, tmpHoriz, tmpVert, colorValue, getPointerStyle(aStyle));
      }
      else if (getVisible())
      {
        Color oldPenColor = getPen().getColor();
        Color oldBrushColor = getBrush().getColor();
        Gradient oldBrushGradient = getBrush().getGradient();

        prepareCanvas(g, getColor());

        setValues(px, py, tmpHoriz, tmpVert);

        if (getGaugePointerStyle() == GaugePointerStyles.HAND)
        {
          Point[] p ={   new Point(xMinus,yMinus),
                                        new Point(xPlus,yMinus),
                                        new Point(xPlusSmall,yPlusSmall),
                                        new Point(px,yPlus),
                                        new Point(xMinusSmall,yPlusSmall)

                                     };

          g.polygon(p);
        }
        else if (getGaugePointerStyle() == GaugePointerStyles.CENTER)
        {
          g.ellipse(xMinus, yMinus, xPlus, yPlus);
        }

        getBrush().setColor(oldBrushColor);
        getBrush().setGradient(oldBrushGradient);
        getPen().setColor(oldPenColor);
      }
    }
}
