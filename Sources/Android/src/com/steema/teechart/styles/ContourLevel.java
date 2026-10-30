package com.steema.teechart.styles;

import com.steema.teechart.drawing.Color;
import com.steema.teechart.drawing.ChartPen;
import com.steema.teechart.drawing.Point;
import com.steema.teechart.axis.Axis;
import com.steema.teechart.drawing.Graphics3D;

import java.io.Serializable;

/**
 *
 * <p>Title: ContourLevel class</p>
 *
 * <p>Description: Contains characteristic information for a Contour
 * series "level".</p>
 *
 * <p>Copyright (c) 2005-2019 by Steema Software SL. All Rights
 * Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 *
 */
public class ContourLevel implements Serializable  {
    
    private Color color;
    private double upTo;
    private Contour iSeries;
    
    private ChartPen pen;
    private int index;
    protected LevelSegments segments = new LevelSegments();
       

    /**
     * Contains characteristic information for a Contour series' "level".
     *
     * @param cs Contour
     */
    public ContourLevel(Contour cs, int levelIndex) {
        iSeries = cs;
        index = levelIndex; 
    }
    
    public ContourLevel(Contour cs) {
        iSeries = cs;
        index = 0; 
    }
    
    public Color internalColor()
    {
      return color.equals(Color.EMPTY) ? 
        iSeries.getValueColorValue(getUpToValue()) : color;
    }

    private void checkAuto() {
        if (!iSeries.iModifyingLevels) {
            iSeries.setAutomaticLevels(false);
        }
    }
    
    public ChartPen getPen()
    {
      if (pen == null)
        pen = new ChartPen(iSeries.chart);

      return pen;
    }
    
    public void setPen(ChartPen value)
    {
      pen = value;
      iSeries.invalidate();
    }
    
    protected ChartPen getInternalPen()
    {
      return (pen != null) ? pen : iSeries.getPen();
    }
    
    public boolean clicked(int x, int y, Point indices)
    {
      //segmentindex = x
      //pointindex = y
      for (int t = 0; t < segments.size(); t++)
      {
        Point tmpPoint = new Point(t,indices.y);
        if (clickedSegment(x, y, tmpPoint))
        {
          indices.x = t;
          indices.y = tmpPoint.y;
          return true;
        }
      }
      indices.x = -1;
      indices.y = -1;
      return false;
    }
    
    public boolean clickedSegment(int x, int y, Point indices)
    {
      Point p = new Point(x,y);
      if (iSeries.chart != null)
        p=iSeries.chart.getGraphics3D().calculate2DPosition(x, y, iSeries.middleZ);
      Point[] points = getSegmentPoints(indices.x);
      Point tmp = new Point(p.x, p.y);
      for (int t = 0; t < points.length - 1; t++)
      {
        if (Graphics3D.pointInLineTolerance(tmp, points[t], points[t + 1],4))
        {
          indices.y = t;
          return true;
        }
      }
      indices.y = -1;
      return false;
    }
    
    public Point[] getSegmentPoints(int segmentindex)
    {
      int tmp = segments.getItem(segmentindex).count;

      Point[] result = new Point[tmp];
      Axis tmphaxis = iSeries.getHorizAxis();
      Axis tmpzaxis = iSeries.getZAxis();
      for (int t = 0; t < tmp; t++)
      {
//        int x=tmphaxis.calcXPosValue(segments.getItem(segmentindex).points[t].x);
//        int y=tmpzaxis.calcPosValue(segments.getItem(segmentindex).points[t].y));
        result[t] = new Point(tmphaxis.calcXPosValue(segments.getItem(segmentindex).points[t].x),
                tmpzaxis.calcPosValue(segments.getItem(segmentindex).points[t].y));
                     

      }
      return result;
    }

    /**
     * Colour of Contour series Level.
     *
     * @param value Color
     */
    public void setColor(Color value) {
        color = value;
        if (iSeries != null) iSeries.invalidate();
        if (pen != null) pen.color = value;
        checkAuto();
    }

    private void setUpTo(double value) {
        upTo = value;
        checkAuto();
    }
    
    public boolean defaultPen()
    {
      return (pen == null);
    }

    /**
     * The range value for ContourLevel.
     *
     * @return double
     */
    public double getUpToValue() {
        return upTo;
    }

    /**
     * Sets range value for ContourLevel.
     *
     * @param value double
     */
    public void setUpToValue(double value) {
        setUpTo(value);
    }

    /**
     * Colour of Contour series Level.
     *
     * @return Color
     */
    public Color getColor() {
        return color;
    }
}


