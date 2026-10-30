package com.steema.teechart.styles;

import com.steema.teechart.editors.gallery.Gallery;

import com.steema.teechart.drawing.Color;
import com.steema.teechart.drawing.IGraphics3D;
import com.steema.teechart.drawing.Point;

import com.steema.teechart.IBaseChart;
import com.steema.teechart.drawing.ChartPen;
import com.steema.teechart.drawing.ChartBrush;

import com.steema.teechart.Point3D;
import com.steema.teechart.misc.Utils;
import com.steema.teechart.languages.Language;

/**
 *
 * @author Steema Software
 */
final class SurfaceSides {
  private Surface series;
  private boolean levels;
  private ChartPen pen;
  
  public SurfaceSides(Surface Series) 
  {
    levels = true;
    series = Series;
    pen = new ChartPen(series.chart, false);
  }

  private void canvasChanged()
  {
    series.invalidate();
  }

  public ChartBrush getBrush()
  {
    return series.getSideBrush(); 
  }

  public ChartPen getPen()
  {
    return pen;
  }
  
  public boolean getLevels()
  {
    return levels;
  }

  public void setLevels(boolean value)
  {
    if (levels != value)
    {
      levels = value;
      series.invalidate();
    }
  }
}

public class IsoSurface extends Surface {

  private ChartPen bandPen;
  private SurfaceSides sides;
  private ValueList xv, zv, v;
  private boolean iTransp;
  private boolean hasImage;
  private int iCalcYPos;
  private int paletteLength;
  private double tmpValue;
  private boolean isZ;
  private Point[] iPoints2D;
  private Point3D[] P3D;
  private double yPosition;
  private boolean useY;

  public IsoSurface() { this((IBaseChart)null);  }

  public IsoSurface(IBaseChart c)
  {
    super(c);
    sides = new SurfaceSides(this);
    bUseColorRange = false;
    bUsePalette = true;
  }
  
    public void assign(Series source) {
        if (source instanceof IsoSurface) {
            IsoSurface tmp = (IsoSurface)source;
            tmp.setChart(((IsoSurface) source).getChart());
            tmp.getBandPen().assign(((IsoSurface)source).getBandPen());
            tmp.setSides(((IsoSurface)source).getSides());
            tmp.setUseYPosition(((IsoSurface)source).getUseYPosition());
            tmp.setYPosition(((IsoSurface)source).getYPosition());
        }
        super.assign(source);
    }
    
//   
  public void draw()
  {
    IGraphics3D g = chart.getGraphics3D();
    boolean oldFast = g.getSupportsFullRotation();

    xv = vxValues;
    zv = vzValues;
    v = mandatory;

    xv.statsOk=false;
    zv.statsOk=false;
    v.statsOk=false;
    
    hasImage = getBrush().getImage() != null;

    if (bUseColorRange)
    {
      generatePalette();
    }

    iTransp = (getBrush().getTransparency() > 0) && !g.getSupportsFullRotation();
    paletteLength = getPalette().size();
    iCalcYPos = calcYPosValue(yPosition);
    super.draw();
    iPoints2D = null;
  }

  private void generatePalette()
  {
    for (int t = 0; t < paletteLength; t++)
    {
      GridPalette palette = getPalette().getPalette(t);
      palette.color = this.getValueColorValue(palette.upToValue);
      getPalette().set(t, palette);
    }

  }

  public void createSubGallery(Gallery addSubChart) {
          super.createSubGallery(addSubChart);
          addSubChart.createSubChart(Language.getString("WireFrame"));
          addSubChart.createSubChart(Language.getString("DotFrame"));
          addSubChart.createSubChart(Language.getString("Sides"));
          addSubChart.createSubChart(Language.getString("NoBorder"));
      }

  public void setSubGallery(int index)
  {
    switch (index)
    {
      case 0: bUseColorRange = false; bUsePalette = false; break;
      case 1: bUseColorRange = true; bUsePalette = false; break;
      case 2: setWireFrame(true); bandPen.setVisible(true); break;
      case 3: setDotFrame(true); bandPen.setVisible(true); break;
      case 4: this.getSideBrush().setVisible(true); break;
      case 5: bandPen.setVisible(false); break;
    }
  }

  protected void drawSidePortion(Point[] P, int z0, int z1)
  {
    IGraphics3D g = chart.getGraphics3D();
    P3D = new Point3D[4];
    int tmp0 = P[0].y;
    int tmp3 = P[3].y;
    int tmpMin, tmpMin2, tmpPrev, tmp, tmpDif;
    double dif;
    
    isZ = z0 != z1;

    if (tmp3 == tmp0)
    {
            dif = 0;
    }
    else if (isZ)
    {
            dif = Math.abs((z1 - z0) / (tmp3 - tmp0));
            P3D[0].x = P[0].x;
            P3D[1].x = P[1].x;
            P3D[2].x = P[2].x;
            P3D[3].x = P[3].x;

            P3D[0].z = z0;
            P3D[1].z = z0;
            P3D[2].z = z1;
            P3D[3].z = z1;
    }
    else
    {
      dif = Math.abs((P[0].x - P[3].x) / (tmp3 - tmp0));
    }

    tmpMin = tmp0;
    tmpMin2 = tmp3;

    if (tmp0 > tmp3)
    {  
      //swap
      int tmpVar = tmpMin;
      tmpMin = tmpMin2;
      tmpMin2 = tmpVar;
    }

    tmpPrev = P[1].y;

    g.setBrush(getSideBrush());
    g.setPen(this.getSides().getPen());

    for (int t = 0; t < paletteLength; t++)
    {
      tmp = getVertAxis().calcYPosValue(getPalette().getPalette(t).upToValue);

      if (tmp < tmpPrev)
      {
        P[1].y = tmpPrev;
        P[2].y = tmpPrev;

        if (tmp < tmpMin2)
        {
          if (tmp < tmpMin)
            tmp = tmpMin;

          if (tmpPrev > tmpMin2)
          {
            P[0].y = tmpMin2;
            P[3].y = tmpMin2;

            fill(false, t, P, z0, z1);  

            drawLine(P, z0, z1);

            P[1].y = tmpMin2;
            P[2].y = tmpMin2;
          }

          P[0].y = tmp;
          P[3].y = tmp;

          tmpDif = Utils.round(dif * (tmp - tmpMin));

          if (tmp3 == tmpMin2)
          {
            if (isZ)
              P3D[3].z = P3D[0].z - tmpDif;
            else
              P[3].x = P[0].x - tmpDif;

            fill(false, t, P, z0, z1); 

            if (isZ)
              P3D[2].z = P3D[3].z;
            else
              P[2].x = P[3].x;
          }
          else
          {
            if (isZ)
              P3D[0].z = P3D[3].z + tmpDif;
            else
              P[0].x = P[3].x + tmpDif;

              fill(false, t, P, z0, z1);

              if (isZ)
                P3D[1].z = P3D[0].z;
              else
                P[1].x = P[0].x;
          }
        }
        else
        {
          P[0].y = tmp;
          P[3].y = tmp;
          fill(true, t, P, z0, z1);	
        }

        if (tmp <= tmpMin)
        {
          break;
        }

        tmpPrev = tmp;

        drawLine(P, z0, z1);
      }
    }
  }

  private void drawLine(Point[] P, int z0, int z1)
  {
    IGraphics3D g = chart.getGraphics3D();
    if (sides.getPen().getVisible())
    {
      g.setPen(sides.getPen());
      g.moveTo(P[1].x, P[1].y - 1, z0);
      g.lineTo(P[2].x, P[2].y - 1, z1);
    }
  }

  private void fill(boolean outLine, int t, Point[] P, int z0, int z1)
  {
    IGraphics3D g = chart.getGraphics3D();
    if (sides.getLevels())
    {
      g.getBrush().setColor(getPalette().getPalette(t).color);
      if (g.getBrush().getColor() == Color.transparent)
      {
        return;
      }
    }

    if (isZ)
    {
      P3D[0].y = P[0].y;
      P3D[1].y = P[1].y;
      P3D[2].y = P[2].y;
      P3D[3].y = P[3].y;

      if (sideLines.getVisible())
      {
        g.polygon(P3D);
      }
      else
      {
        drawPenLessPolygon(P3D);
      }
    }
    else
    {
      if (sideLines.getVisible())
      {
        g.planeFour3D(z0, z1, P);
      }
      else
      {
        boolean oldVisible = g.getPen().getVisible();
        g.getPen().setVisible(false);
        g.planeFour3D(z0, z1, P);
        g.getPen().setVisible(oldVisible);
      }
    }
  }

 //  
  public void prepareForGallery(boolean isEnabled)
  {
    super.prepareForGallery(isEnabled);
    getBandPen().setVisible(false);
  }

  protected boolean shouldDrawFast()
  {
    return false;
  }

//   
  protected boolean shouldDrawSides()
  {
    return ((super.shouldDrawSides()) || (sides.getPen().getVisible()));
  }

 //  
  public void drawCell(int x, int z)
  //protected void drawCell(int x, int z)
  {
    Color tmpColor;
    int tmpPal;
    Point[] iPoints = new Point[4];
    IGraphics3D g = chart.getGraphics3D();

    if (fourGridIndex(x, z))
    {
      tmpColor = internalColor(valueIndex0);

      if (tmpColor != Color.EMPTY)
      {
        tmpPal = calcFirstLevel();

        if (tmpPal != -1)
        {
          if (iTransp)
          {
            if (chart.getAspect().getView3D())
            {
              if (getUseYPosition())
              {
                points[0].y = iCalcYPos;
                points[1].y = iCalcYPos;
                points[2].y = iCalcYPos;
                points[3].y = iCalcYPos;
              }
 //***             pointsTo2D(calcZPos(valueIndex0), calcZPos(valueIndex2), ref iPoints);
            }
          }

          loopLevels(tmpPal);
        }
      }
    }

  }

  private void loopLevels(int level)
  {
    int tmpOp = calcOposite(tmpMin);
    Point3D old0, old1, PL, PR, POp;
    Point3D new0 = new Point3D();
    Point3D new1 = new Point3D();
    Point3D new02 = new Point3D();
    Point3D new12 = new Point3D();
    IGraphics3D g = chart.getGraphics3D();
    Point[] PP = new Point[4];
    PP[0] =  new Point();
    PP[1] =  new Point();
    PP[2] =  new Point();
    PP[3] =  new Point();
    int l = 0, r = 0;

    old0 = calcPoint(tmpMin);
    old1 = new Point3D(old0.x,old0.y,old0.z);

    boolean flagL = false;
    boolean flagR = false;
    boolean flag0 = false;
    boolean flagOp = false;
    boolean flag2 = false;

    int tmpL = tmpMin;
    int tmpR = tmpL;

    Point tmpPoint=new Point(l,r);
    calcLR(tmpMin, tmpPoint);

    l = tmpPoint.x;
    r = tmpPoint.y;
    
    PL = calcPoint(l);
    PR = calcPoint(r);
    POp = calcPoint(tmpOp);

    while (level < paletteLength)
    {
      tmpValue = getPalette().getPalette(level).upToValue;
                  Color oldBrushColor = getBrush().getColor();
                  boolean oldBrushVisible = getBrush().getVisible();

      if (getPalette().getPalette(level).color == Color.EMPTY)
      {
        prepareBrush(Color.transparent);
      }
      else
      {
        prepareBrush(getPalette().getPalette(level).color);
      }
                  
      if((!flag0) && (tmpValue>=v.getValue(tmpMax))) 
      {
        if (chart.getAspect().getView3D())
        {
          g.calc3DPos(PP[0], old0.x, old0.y, old0.z);
          g.calc3DPos(PP[1], PL.x, PL.y, PL.z);
          g.calc3DPos(PP[2], POp.x, POp.y, POp.z);
          g.calc3DPos(PP[3], PR.x, PR.y, PR.z);
          drawPenLessPolygon(PP);
        }
        else
        {
          fillPolygon(new Point3D[] {old0, PL, POp, PR});
        }

        break;
      }

      if (chart.getAspect().getView3D())
      {
        if (getUseYPosition())
        {
          new0.y = iCalcYPos;
        }
        else
        {
          new0.y = calcYPosValue(Math.min(v.getValue(tmpMax), tmpValue));
        }

        new1.y = new0.y;
      }

      if (!flagL && (tmpValue >= v.getValue(l)))
      {
        flagL = true;
        tmpL = tmpOp;

        if (flagR && (tmpValue >= v.getValue(tmpR)))
        {
          fillPolygon(new Point3D[] {old0, old1, calcPoint(tmpR), PL});
        }
        else if (!flagR && (tmpValue >= v.getValue(r)))
        {
          flagR = true;
          tmpR = tmpOp;

          if (tmpValue >= v.getValue(tmpOp))
          {
            flagOp = true;
            fillPolygon(new Point3D[] {old0, old1, PR, POp, PL});
          }
          else
          {
            calcXZ(tmpL, l, new0);
            calcXZ(tmpR, r, new1);
            fillPolygon(new Point3D[] {old1, old0, PL, new0, new1, PR});
          }
        }
        else if (tmpValue >= v.getValue(tmpOp))
        {
          if (flagOp)
          {
            fillPolygon(new Point3D[] {old0, PL, new02});
          }
          else
          {
            flagOp = true;
            tmpL = tmpOp;
            calcXZ(tmpL, r, new0);
            calcXZ(tmpR, r, new1);
            fillPolygon(new Point3D[] {old0, PL, POp, new0, new1});
          }
        }
        else
        {
          calcXZ(l, tmpL, new0);
          calcXZ(tmpR, r, new1);
          fillPolygon(new Point3D[] {PL, old0, old1, new1, new0});   //3
        }
      }
      else if (!flagR && (tmpValue >= v.getValue(r)))
      {
        flagR = true;
        if (flagOp)
        {
          if (flag2)
          {
            old0 = new Point3D(new12.x,new12.y,new12.z);
            fillPolygon(new Point3D[] {old1, PR, new12});
          }
          else
          {
            fillPolygon(new Point3D[] {old0, old1, PR});
          }
        }
        else
        {
          tmpR = tmpOp;
          if (flagL && (tmpValue >= v.getValue(tmpL)))
          {
            fillPolygon(new Point3D[] {old0, old1, PR, calcPoint(tmpL)});
          }
          else if (!flagL && (tmpValue >= v.getValue(l)))
          {
            flagL = true;
            tmpL = tmpOp;
            calcXZ(tmpL, l, new0);
            calcXZ(tmpR, r, new1);
            fillPolygon(new Point3D[] {old0, old1, PL, new0, new1, PR});
          }
          else if (tmpValue >= v.getValue(tmpOp))
          {
            flagOp = true;
            calcXZ(l, tmpL, new0);
            calcXZ(tmpR, l, new1);
            fillPolygon(new Point3D[] {old0, old1, PR, POp, new1, new0});
          }
          else
          {
            calcXZ(l, tmpL, new0);
            calcXZ(tmpR, r, new1);
            fillPolygon(new Point3D[] {old0, old1, PR, new1, new0});  //1
          }
        }
      }
      else if (tmpValue >= v.getValue(tmpOp))
      {
        flagOp = true;
        if (flagL && flagR)
        {
          fillPolygon(new Point3D[] {old0, old1, POp});   //2
        }
        else if (flagR)
        {
          tmpR = tmpOp;
          calcXZ(l, tmpL, new0);
          calcXZ(l, tmpR, new1);
          fillPolygon(new Point3D[] {old0, old1, POp, new1, new0});
        }
        else if (flagL)
        {
          tmpL = tmpOp;
          calcXZ(r, tmpL, new0);
          calcXZ(r, tmpR, new1);
          fillPolygon(new Point3D[] {old0, old1, new1, new0, POp});    //4
        }
        else
        {
          flag2 = true;
          calcXZ(l, tmpL, new0);
          calcXZ(r, tmpR, new1);

          new02.y = new0.y;
          new12.y = new1.y;

          calcXZ(l, tmpOp, new02);
          calcXZ(r, tmpOp, new12);

          fillPolygon(new Point3D[] {old0, new0, new02, POp, new12, new1});

          old1 = new Point3D(new02.x,new02.y,new02.z);
        }
      }
      else
      {
        calcXZ(l, tmpL, new0);
        calcXZ(tmpR, r, new1);
        fillPolygon(new Point3D[] {old0, old1, new1, new0});
      }

        getBrush().setColor(oldBrushColor);
        getBrush().setVisible(oldBrushVisible);

        if (getBandPen().getVisible() || getWireFrame() || getDotFrame())
        {
          if (getDotFrame())
          {
            g.setPixel(old1.x, old1.y, old1.z, getPalette().getPalette(level).color);
          }
          else if (getWireFrame())
          {
            Color oldBandPenColor = bandPen.getColor();

            g.setPen(bandPen);
            g.getPen().setColor(getPalette().getPalette(level).color);
            g.moveTo(old0);
            g.lineTo(old1);
            bandPen.setColor(oldBandPenColor);
          }
          else
          {
            g.setPen(bandPen);
            g.moveTo(old0);
            g.lineTo(old1);
          }

        }

        if (tmpValue > v.getValue(tmpMax))
        {
          break;
        }

        old0 = new Point3D(new0.x,new0.y,new0.z);
        old1 = new Point3D(new1.x,new1.y,new1.z);

        flag0=true;

        ++level;
    }
  }

  private void fillPolygon(Point3D[] points)
  {
  
    if (chart.getAspect().getView3D())
    {
      drawPenLessPolygon3D(points);
    }
    else
    {
      int tmp = points.length;

      iPoints2D = updateArray(tmp,iPoints2D);

      for (int i = 0; i < tmp; i++)
      {
        iPoints2D[i] = new Point(points[i].x, points[i].y);

        //System.Diagnostics.Debug.WriteLine("iPoints2D[" + i.ToString() + "].x: " + iPoints2D[i].x.ToString());
        //System.Diagnostics.Debug.WriteLine("iPoints2D[" + i.ToString() + "].y: " + iPoints2D[i].y.ToString());
      }

      drawPenLessPolygon(iPoints2D);
    }
  }

  private void drawPenLessPolygon(/*params*/ Point[] point)
  {
    IGraphics3D g = chart.getGraphics3D();
    boolean oldVisible = g.getPen().getVisible();
    g.getPen().setVisible(false);
    g.polygon(point);
    g.getPen().setVisible(oldVisible);
  }

  private void drawPenLessPolygon3D(/*params*/ Point3D[] point)
  {
    IGraphics3D g = chart.getGraphics3D();
    boolean oldVisible = g.getPen().getVisible();
    g.getPen().setVisible(false);
    g.polygon(point);
    g.getPen().setVisible(oldVisible);
  }

  private void drawPenLessPolygon(Point3D[] point)
  {
    IGraphics3D g = chart.getGraphics3D();
    boolean oldVisible = g.getPen().getVisible();
    g.getPen().setVisible(false);
    g.polygon(point);
    g.getPen().setVisible(oldVisible);
  }

  private void calcXZ(int A, int B, Point3D P)
  {
    double tmp;

//  #if TEEUSEFLOAT
//          double tmpX, tmpZ;
//
//          tmpX = xv[A];
//          tmpZ = zv[A];
//
//          tmp=v[B]-v[A];
//          if (tmp == 0) 
//          { 
//                  tmp=1;
//          }
//
//          tmp=(tmpValue-v[A])/tmp;
//
//          if (tmpX != xv[B]) 
//          {
//     tmpX = tmpX - (tmpX - xv[B]) * tmp;
//          }
//
//          if (tmpZ != zv[B])
//          {
//                  tmpZ = tmpZ - (tmpZ - zv[B]) * tmp;
//          }
//
//          P.x = CalcXPosValue(tmpX);
//          P.z = chart.Axes.Depth.CalcPosValue(tmpZ); 
//
//  #else

    P.x = calcXPos(A);

    if (chart.getAspect().getView3D())
    {
      P.z = calcZPos(A);
    }
    else
    {
      P.y = getVertAxis().calcYPosValue(this.getZValues().getValue(A));
    }

    tmp=v.getValue(B)-v.getValue(A);
    if (tmp == 0)
    {
      tmp = 1;
    }

    tmp=(tmpValue-v.getValue(A))/tmp;

    if (xv.getValue(A) != xv.getValue(B))
    {
      P.x -= Utils.round((P.x - calcXPos(B)) * tmp);
    }

    if (zv.getValue(A) != zv.getValue(B))
    {
      if (chart.getAspect().getView3D())
      {
        P.z -= Utils.round((P.z - calcZPos(B)) * tmp);
      }
      else
      {
        P.y -= Utils.round((P.y-getVertAxis().calcYPosValue(getZValues().getValue(B)))*tmp);
      }
    }
  }

  private Point3D calcPoint(int index)
  {
    Point3D result = new Point3D();

    result.x = calcXPos(index);

    if (chart.getAspect().getView3D())
    {
      if (getUseYPosition())
      {
        result.y = iCalcYPos;
      }
      else
      {
        result.y = calcYPos(index);
      }

      result.z = calcZPos(index);
    }
    else
    {
      result.y = getVertAxis().calcYPosValue(getZValues().getValue(index));
    }

    return result;
  }

  private void calcLR(int index, Point tmpPoint)
  {
    int l=tmpPoint.x;
    int r=tmpPoint.y;
    
    if (index == valueIndex0)
    {
      l = valueIndex3;
      r = valueIndex1;
    }
    else if (index == valueIndex1)
    {
      l = valueIndex0;
      r = valueIndex2;
    }
    else if (index == valueIndex2)
    {
      l = valueIndex1;
      r = valueIndex3;
    }
    else
    {
      l = valueIndex2;
      r = valueIndex0;
    }
    
    tmpPoint.x = l;
    tmpPoint.y = r;
  }

  private int calcOposite(int index)
  {
    int result = 0;
    if (index == valueIndex3)
    {
      result = valueIndex1;
    }
    else if (index == valueIndex1)
    {
      result = valueIndex3;
    }
    else if (index == valueIndex0)
    {
      result = valueIndex2;
    }
    else
    {
      result = valueIndex0;
    }
    return result;
  }

  private void prepareBrush(Color tmpColor)
  {
    IGraphics3D g = chart.getGraphics3D();

    g.setBrush(getBrush());

    if (!getWireFrame() && !getDotFrame())
    {
      if (getBrush().getColor().getAlpha() < 255)
              tmpColor = Color.fromArgb(getBrush().getColor().getAlpha(), tmpColor);

      g.getBrush().setColor(tmpColor);
    }
    else
    {
      g.getBrush().setVisible(false);
    }
  }

  private void PointsTo2D(int z0, int z1, Point[] P)
  {
    IGraphics3D g = chart.getGraphics3D();
    P[0] = g.calc3DPos(points[0], z0);
    P[1] = g.calc3DPos(points[1], z0);
    P[2] = g.calc3DPos(points[2], z1);
    P[3] = g.calc3DPos(points[3], z1);
  }

  private int tmpMin, tmpMax;

  private int calcFirstLevel()
  {
    int[] i = new int[4];
    int result;
    int t;
    i[0] = valueIndex0;
    i[1] = valueIndex1;
    i[2] = valueIndex2;
    i[3] = valueIndex3;

    tmpMin = i[0];
    double tmpV = v.getValue(tmpMin);
    double tmpM = tmpV;
    tmpMax = tmpMin;

    for (t = 1; t <= 3; t++)
    {
      if (v.getValue(i[t]) < tmpV)
      {
        tmpV = v.getValue(i[t]);
        tmpMin = i[t];
      }
      else if (v.getValue(i[t]) > tmpM)
      {
        tmpM = v.getValue(i[t]);
        tmpMax = i[t];
      }
    }

    result = -1;

    for (t = 0; t < paletteLength; t++)
    {
      GridPalette palette = getPalette().getPalette(t);
      if (palette.upToValue > tmpV)
      {
        result = t;
        break;
      }
    }

    return result;
  }

  protected boolean setSameBrush()
  {
    return true;
  }

  private Color internalColor(int valueIndex)
  {
    return this.getValueColor(valueIndex);
  }

  /// <summary>
  /// 
  /// </summary>
  public boolean getUseYPosition()
  {
    return useY; 
  }
  
  public void setUseYPosition(boolean  value)
  { 
    useY = value;
  }

  /// <summary>
  /// 
  /// </summary>
  public double getYPosition()
  {
    return yPosition; 
  }
  
  public void setYPosition(double value)
  { 
    yPosition = value;
  }

  public ChartPen getBandPen()
  {
    if (bandPen == null)
    {
      bandPen = new ChartPen(chart, Color.BLACK, true);
    }
    return bandPen; 
  }
  
  public void setBandPen(ChartPen value)
  { 
    bandPen = value;
  }

  public SurfaceSides getSides()
  {
    return sides; 
  }
   
  public void setSides(SurfaceSides value)
  {
    sides = value;
  }

  /// <summary>
  /// Gets descriptive text.
  /// </summary>
  public String getDescription() 
  {
    return Language.getString("GalleryISOSurface");
  }
}
