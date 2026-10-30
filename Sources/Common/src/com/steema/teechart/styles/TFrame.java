package com.steema.teechart.styles;

import com.steema.teechart.TeeBase;
import com.steema.teechart.Chart;
import com.steema.teechart.IBaseChart;
import com.steema.teechart.Rectangle;
import com.steema.teechart.drawing.*;
import com.steema.teechart.drawing.Color;
import com.steema.teechart.drawing.IGraphics3D;
import com.steema.teechart.drawing.ChartBrush;

/* The TFrame object draws a frame of three bands at the outer edge of an ellipse or circle defined by a rectangle.
  */
public class TFrame extends TeeBase {
	
	private static final long serialVersionUID = 1L;
  
    public TFrame(IBaseChart c, Color[] gColorPalette)
    {
      super(c);
      width = 5;
      visible = true;
      tmpBrush = new ChartBrush(c, Color.TRANSPARENT, true);
      gaugeColorPalette = gColorPalette;
      circled = true;
    }

    public TFrame(IBaseChart c, Color[] gColorPalette, boolean circle)
    {
      this(c, gColorPalette);
      circled = circle;
    }

    public TFrame()
    {
      this((Chart)null, null);
    }

    public IGraphics3D prepareGraphics(IGraphics3D g,ChartBrush brush)
    {
      IGraphics3D result = g;
      result.setBrush(brush);
      return result;
    }

    private IGraphics3D prepareGraphics(ChartBrush brush)
    {
      IGraphics3D result = chart.getGraphics3D();
      result.setBrush(brush);
      return result;
    }



    /*
    * Draw's the TFrame object within the bounds defined by Rect.
    */
    public void draw(Rectangle rect)
    {
        draw(chart.getGraphics3D(),rect);
    }

   /*
    * Draw's the TFrame object within the bounds defined by Rect.
    */
    public void draw(IGraphics3D graphics, Rectangle rect)
    {

      IGraphics3D g = prepareGraphics(graphics, getOuterBand());
      double tmpWidth = calcWidth(rect) / 10.0;

      if (circled)
      {
        g.ellipse(rect);
      }
      else
      {
        g.rectangle(rect);
      }
      g = prepareGraphics(graphics, getMiddleBand());
      rect.inflate(Math.round(-Math.round(tmpWidth * 1)), Math.round(-Math.round(tmpWidth * 1)));
      if (circled)
      {
        g.ellipse(rect);
      }
      else
      {
        g.rectangle(rect);
      }
      g = prepareGraphics(graphics, getInnerBand());
      rect.inflate((int)-Math.round(tmpWidth * 7), (int)-Math.round(tmpWidth * 7));
      if (circled)
      {
        g.ellipse(rect);
      }
      else
      {
        g.rectangle(rect);
      }
      g = prepareGraphics(graphics, tmpBrush);
      rect.inflate((int)-Math.round(tmpWidth * 2), (int)-Math.round(tmpWidth * 2));
      if (circled)
      {
        g.ellipse(rect);
      }
      else
      {
        g.rectangle(rect);
      }
    }

   /*
    * Calculates the actual width of the TFrame object in pixels as using the <see cref="TFrame.Width"/> property and the rectangle Rect.
    */
    public int calcWidth(Rectangle rect)
    {
      double minWidth = Math.min(rect.width, rect.height);
      minWidth /= 100;
      return (int)Math.round(minWidth * width);

    }

    private ChartBrush innerBand = new ChartBrush(this.chart);
    private ChartBrush middleBand;
    private ChartBrush outerBand;
    private ChartBrush tmpBrush;
    private int width;
    private boolean visible;
    private Color[] gaugeColorPalette;
    private boolean circled;

   /*
    * Gets and sets the CircularGauge's color palette.
    */
    public Color[] getGaugeColorPalette()
    {
      if (gaugeColorPalette == null || gaugeColorPalette.length == 0)
      {
        gaugeColorPalette = chart.getGraphics3D().getColorPalette();
      }
      return gaugeColorPalette;
    }
    
    public void setGaugeColorPalette(Color[] value)
      {
        gaugeColorPalette = value;
        outerBand = null;
        innerBand = null;
        middleBand = null;
      }

    /*
    * Gets and sets the visibility of the TFrame object.
    */
    public boolean getVisible()
    {
        return visible;

    }
    
    public void setVisible(boolean value)
      {
        visible = value;
      }
    

   /*
    * Gets and sets the width of the TFrame object as a percentage of its bounding rectangle.
    */
    public int getWidth()
    {
        return width;
    }

    public void setWidth(int value)
      {
        width = value;
      }
    


   /*
    * Toggles the drawing of a circular or rectangular frame.
    */
    public boolean getCircled()
    {
        return circled;

    }
    
    public void setCircled(boolean value)
      {
        circled = value;
      }
    


   /*
    * Gets and sets the OuterBand's ChartBrush characteristics.
    */
    public ChartBrush getOuterBand()
    {
        if (outerBand == null)
        {
          outerBand = new ChartBrush(chart, CustomGauge.getGaugePaletteColor(0, getGaugeColorPalette()), true);
        }
        return outerBand;

    }
    
    public void setOuterBand(ChartBrush value)
      {
        outerBand = value;
      }
    

   /*
    * Gets and sets the MiddleBand's ChartBrush characteristics.
    */
    public ChartBrush getMiddleBand()
    {

        if (middleBand == null)
        {
          middleBand = new ChartBrush(chart, CustomGauge.getGaugePaletteColor(1, gaugeColorPalette), true);
        }
        return middleBand;
      }
    
    
    public void setMiddleBand(ChartBrush value)
      {
        middleBand = value;
      }

   /*
    * Gets and sets the InnerBand's ChartBrush characteristics.
    */
    public ChartBrush getInnerBand()
    {

        if (innerBand == null)
        {
          innerBand = new ChartBrush(chart, CustomGauge.getGaugePaletteColor(2, gaugeColorPalette), true);
        }
        return innerBand;
      }
    
    
    public void setInnerBand(ChartBrush value)
      {
        innerBand = value;
      }
}
