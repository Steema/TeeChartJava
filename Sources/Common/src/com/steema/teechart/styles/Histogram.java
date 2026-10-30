package com.steema.teechart.styles;


import com.steema.teechart.languages.Language;
import com.steema.teechart.Rectangle;
import com.steema.teechart.drawing.IGraphics3D;
import com.steema.teechart.drawing.ChartPen;
import com.steema.teechart.drawing.ChartBrush;
import com.steema.teechart.drawing.Point;
import com.steema.teechart.IBaseChart;
import com.steema.teechart.drawing.Color;
import com.steema.teechart.drawing.Graphics3D;
import com.steema.teechart.editors.gallery.Gallery;


/**
 *
 * <p>Title: Histogram class</p>
 *
 * <p>Description: Histogram Series.</p>
 *
 * <p>Example:
 * <pre><font face="Courier" size="4">
 * Histogram series = new Histogram(myChart.getChart());
 * series.fillSampleValues(10);
 * </font></pre></p>
 *
 * <p>Copyright (c) 2005-2019 by Steema Software SL. All Rights Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 *
 */
public class Histogram extends BaseLine {
    /**
	 * 
	 */
    private static final long serialVersionUID = 1L;

    private ChartPen linesPen;

    transient protected int previous;
    private int origin;
    private boolean useOrigin;

    public Histogram() {
        this((IBaseChart)null);
    }

    public Histogram(IBaseChart c) {
        super(c);
        calcVisiblePoints = false;
        getLinePen().setColor(Color.BLACK);
        origin=0;
        useOrigin = false;
    }
    
    public void assign(Series source) {
        if (source instanceof Histogram) {
            Histogram tmp = (Histogram)source;
            tmp.setChart(((Histogram) source).getChart());
            tmp.setTransparency(((Histogram) source).getTransparency());
            tmp.setUseYOrigin(((Histogram) source).getUseYOrigin());
        }
        super.assign(source);
    }

    /**
     * Determines the pen to be used for drawing the line connecting all
     * points. <br>
     *
     * @return ChartPen
     */
    public ChartPen getLinesPen() {
        if (linesPen == null) {
            linesPen = new ChartPen(chart, Color.BLACK);
        }
        return linesPen;
    }

    /**
     * Defines the Brush fill for the Histogram series.
     *
     * @return ChartBrush
     */
    public ChartBrush getBrush() {
        return bBrush;
    }

    public void setYOrigin(int value)
    {
        origin = value;
        this.invalidate();
    }
    
    public int getYOrigin()
    {
        return origin;
    }
    public void setChart(IBaseChart c) {
        super.setChart(c);

        if (linesPen != null) {
            linesPen.setChart(c);
        }

        if (bBrush != null) {
            bBrush.setChart(c);
        }
    }

    /**
     * The Transparency level from 0 to 100%.<br>
     * Default value: 0
     *
     * @return int
     */
    public int getTransparency() {
        return bBrush.getTransparency();
    }

    /**
     * Sets Transparency level from 0 to 100%.<br>
     * Default value: 0
     *
     * @param value int
     */
    public void setTransparency(int value) {
        bBrush.setTransparency(value);
        repaint();
    }

    protected int visiblePoints() {
        int result = chart.getPage().getMaxPointsPerPage();
        return (result == 0) ? getCount() : result;
    }

    public void calcHorizMargins(Margins margins) {
        super.calcHorizMargins(margins);
        int tmp = visiblePoints();
        if (tmp > 0) {
            tmp = (getHorizAxis().iAxisSize / visiblePoints()) / 2;
        }

        margins.min += tmp;
        margins.max += tmp;

        if (getLinePen().getVisible()) {
            margins.max += getLinePen().getWidth();
        }
    }

    public void calcVerticalMargins(Margins margins) {
        super.calcVerticalMargins(margins);
        if (getLinePen().getVisible()) {
            margins.min += getLinePen().getWidth();
        }
    }

    private void verticalLine(int x, int y0, int y1) {
        if (chart.getAspect().getView3D()) {
            chart.getGraphics3D().verticalLine(x, y0, y1, getMiddleZ());
        } else {
            chart.getGraphics3D().verticalLine(x, y0, y1);
        }
    }

    private void horizLine(int x0, int x1, int y) {
        if (chart.getAspect().getView3D()) {
            chart.getGraphics3D().horizontalLine(x0, x1, y, getMiddleZ());
        } else {
            chart.getGraphics3D().horizontalLine(x0, x1, y);
        }
    }
    
    protected Rectangle calcRectangle(int valueIndex)
    {
      Rectangle r = new Rectangle();

      if (visiblePoints() > 0)
      {
        double tmp;
        if (visiblePoints() > 1)
        {
          if(drawValuesForward()) {
            if (valueIndex == firstDisplayed())
            {
                tmp = (calcXPos(valueIndex + 1) - calcXPos(valueIndex)) / 2.0;
            }
            else
            {
                tmp = (calcXPos(valueIndex) - calcXPos(valueIndex - 1)) / 2.0;
            } 
          } 
          else {
            if (valueIndex == firstDisplayed())
            {
                tmp = (calcXPos(valueIndex) - calcXPos(valueIndex - 1)) / 2.0;
            }
            else
            {
                tmp = (calcXPos(valueIndex + 1) - calcXPos(valueIndex)) / 2.0;
            }               
          }

        }
        else
        {
          tmp = getHorizAxis().iAxisSize / visiblePoints();
        }

        if (valueIndex == firstDisplayed())
        {
          double X;
          if(drawValuesForward()) {
             X = calcXPos(valueIndex) - tmp; 
          }
          else {
             X = calcXPos(valueIndex) + tmp; 
          }
          
          double width = tmp * 2.0;

          r.width = (int)Math.round(Math.abs(width));
          r.x = (int)Math.round(X);
        }
        else
        {
          r.x = previous;
          if (drawValuesForward())
            r.width = (int) Math.round((calcXPos(valueIndex) + tmp) - r.x);
          else
            r.width = (int) Math.round((calcXPos(valueIndex) - tmp) - r.x);
        }

        previous = r.getRight();
        r.y = calcYPos(valueIndex);
        
      int height = (int) (getVertAxis().getInverted() ? getVertAxis().iStartPos - r.y : getOriginPos((int) getYOrigin()) - r.y);
        
        if(height < 0) {
           r.y += height;
           r.height = -height;
        }
        else {
            r.height = height;
        }
        
        r = chart.getGraphics3D().calcRect3D(r, middleZ);
      }
      else
        r = new Rectangle(0,0,0,0);

      return r;
    }
     private int getOriginPos(int valueIndex)
    {
      if (useOrigin)
        return calcPosValue(getYOrigin());
      else
        return getVertAxis().iEndPos;
    }
    public int clicked(int x, int y)
    {
      int result = super.clicked(x, y);
      Rectangle r = new Rectangle(Rectangle.EMPTY);

      Point p;

      if ((firstVisible != -1) && (lastVisible != -1))
      {
        if ((chart != null)) {
            p = chart.getGraphics3D().calculate2DPosition(x, y, middleZ);
          for (int t = firstVisible; t <= lastVisible; t++)
          {
            r = calcRectangle(t);
            if (r.contains(p))
            {
              result = t;
              break;
            }
          }
        }
      }
      return result;
    }

    /**
     * Called internally. Draws the "ValueIndex" point of the Series.
     *
     * @param valueIndex int
     */
    public void drawValue(int valueIndex) {
       Rectangle r = calcRectangle(valueIndex);
      int bottomPos;
      Graphics3D g = (Graphics3D) chart.getGraphics3D();

      bottomPos = getOriginPos(valueIndex);

      if (getBrush().getVisible())
      {
        g.setBrush(getBrush());
        Color tmpColor = getValueColor(valueIndex);
        if (getTransparency() > 0) //CDI TF02013220
        {
          tmpColor = Graphics3D.transparentColor(getTransparency(), tmpColor);
        }
        g.getBrush().setColor(tmpColor);

        boolean oldVisible = g.getPen().getVisible();
        g.getPen().setVisible(false);


          g.rectangle(r);
        g.getPen().setVisible(oldVisible);
      }

      if (getLinePen().getVisible())
      {
        g.setPen(getLinePen());

        if (yMandatory)
        {
          if (valueIndex == firstDisplayed())
          {
            verticalLine(r.x, r.getBottom(), r.y);
          }
          else
          {
            if (drawValuesForward())
            {
              if (getVertAxis().getInverted())
              {
                verticalLine(r.x, calcYPos(valueIndex - 1), r.getBottom());
              }
              else
              {
                verticalLine(r.x, r.y, calcYPos(valueIndex - 1));
              }
            }
            else
            {
              verticalLine(r.x, r.y, calcYPos(valueIndex + 1));
            }
          }

          if (getVertAxis().getInverted())
          {
            horizLine(r.x, r.getRight(), r.getBottom());
          }
          else
          {
            if (getYValues().getValue(valueIndex) >= getYOrigin())
              if(r.getTop() < bottomPos) horizLine(r.x, r.getRight(), (int) (bottomPos - r.getHeight()));
            else
              horizLine(r.x, r.getRight(), (int) (bottomPos + r.getHeight()));
          }
          if (valueIndex == lastDisplayed())
          {
            verticalLine(r.getRight(), r.y, r.getBottom());
          }
        }
        else
        {
          if (valueIndex == firstDisplayed())
            horizLine(r.getLeft(), r.getRight(), r.getBottom());
          else
          {
            if (drawValuesForward())
            {
              if (getHorizAxis().getInverted())
              {
                horizLine(r.getLeft(), calcXPos(valueIndex - 1), r.getBottom());
              }
              else
              {
                horizLine(calcXPos(valueIndex - 1), r.getRight(), r.getBottom());
              }
            }
            else
            {
              horizLine(calcXPos(valueIndex + 1), r.getRight(), r.getBottom());
            }
          }

          if (getHorizAxis().getInverted())
          {
           verticalLine(r.getLeft(), r.getTop(), r.getBottom());
          }
          else
          {
            verticalLine(r.getRight(), r.getTop(), r.getBottom());
          }
          if (valueIndex == lastDisplayed())
            horizLine(r.getLeft(), r.getRight(), r.getTop());
        }
      }

      boolean doLinesPen = drawValuesForward() ? valueIndex > firstDisplayed() : valueIndex < firstDisplayed();

      if (doLinesPen && (linesPen != null) && linesPen.getVisible())
      {
        if (yMandatory)
        {

          int tmp;

          if (drawValuesForward())
          {
            tmp = calcYPos(valueIndex - 1);
          }
          else
          {
            tmp = calcYPos(valueIndex + 1);
          }
          tmp = getVertAxis().getInverted() ? Math.min(r.y, tmp) : Math.max(r.y, tmp);
          if (!getLinePen().getVisible()) tmp--;
          g.setPen(linesPen);
          verticalLine(r.x, r.getBottom(), tmp);
        }
        else
        {
          int tmp;

          if (drawValuesForward())
          {
            tmp = calcXPos(valueIndex - 1);
          }
          else
          {
            tmp = calcXPos(valueIndex + 1);
          }
          tmp = getHorizAxis().getInverted() ? Math.max(r.getRight(), tmp) : Math.max(r.getLeft(), tmp);
          if (!getLinePen().getVisible()) tmp--;
          g.setPen(linesPen);
          horizLine(r.getLeft(), tmp, r.getBottom());
        }
      }
    }


    public boolean getUseYOrigin()
    {
        return useOrigin;
    }
    
    public void setUseYOrigin(boolean value)
    {
        useOrigin = value;
        this.invalidate();
    }


    public void createSubGallery(Gallery addSubChart) {
        super.createSubGallery(addSubChart);
        addSubChart.createSubChart(Language.getString("Hollow"));
        addSubChart.createSubChart(Language.getString("NoBorder"));
        addSubChart.createSubChart(Language.getString("Lines"));
        addSubChart.createSubChart(Language.getString("Transparency")); // 5.02
    }

    public void setSubGallery(int index) {
        switch (index) {
        case 1:
            getBrush().setVisible(false);
            break;
        case 2:
            getLinePen().setVisible(false);
            break;
        case 3:
            getLinesPen().setVisible(true);
            break;
        case 4:
            setTransparency(30);
            break;
        default:
            super.setSubGallery(index);
        }
    }

    /**
     * Gets descriptive text.
     *
     * @return String
     */
    public String getDescription() {
        return Language.getString("GalleryHistogram");
    }
}

