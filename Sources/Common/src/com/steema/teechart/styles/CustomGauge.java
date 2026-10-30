package com.steema.teechart.styles;

import com.steema.teechart.IBaseChart;
import com.steema.teechart.Rectangle;
import com.steema.teechart.axis.Axis;
import com.steema.teechart.drawing.ChartBrush;
import com.steema.teechart.drawing.ChartPen;
import com.steema.teechart.drawing.Color;
import com.steema.teechart.drawing.DashCap;
import com.steema.teechart.drawing.DashStyle;
import com.steema.teechart.drawing.GradientDirection;
import com.steema.teechart.drawing.Graphics3D;
import com.steema.teechart.drawing.IGraphics3D;
import com.steema.teechart.drawing.LineCap;
import com.steema.teechart.drawing.Point;
import com.steema.teechart.misc.Utils;

public class CustomGauge extends Series {

  /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
public CustomGauge()
    {
      super((IBaseChart)null);
    }

    public CustomGauge(IBaseChart c)
    {
      super(c);
      horizontal = true;
    }

    protected Rectangle iOrigRectangle, iNewRectangle;
    protected double iRange;
    private Rectangle customBounds = new Rectangle(Rectangle.EMPTY);
    protected void setValues()
    {
      calcOrigRectangle();
      calcNewRectangle();
      iRange = getMaximum() - getMinimum();

      if (getFrame().getVisible())
      {
        iNewRectangle.inflate(-getFrame().calcWidth(iNewRectangle), -getFrame().calcWidth(iNewRectangle));
      }
    }
   protected void calcOrigRectangle() 
    {
      if (customBounds.isEmpty())
      {
        iOrigRectangle = getChart().getChartRect();
      }
      else
      {
        Rectangle tmp=getCustomBounds();
        iOrigRectangle = new Rectangle(tmp.x,tmp.y,tmp.width,tmp.height);
      }
    }
    protected  void calcNewRectangle()
    {
      iNewRectangle = Rectangle.fromLTRB(iOrigRectangle.getLeft(), iOrigRectangle.getTop(), iOrigRectangle.getRight(), iOrigRectangle.getBottom());
    }
    
    public Rectangle getCustomBounds()
    {
        return customBounds;
    }
    public void setCustomBounds(Rectangle value)
    {
        customBounds = value;
    }
    public boolean customBoundsIsEmpty()
    {
       return customBounds==Rectangle.EMPTY;
    }
    
    protected void addSampleValues(int numValues)
    {
      java.util.Random rnd = new java.util.Random();
      double max = getMaximum();
      if (max == Double.POSITIVE_INFINITY)
      {
        max = 1000;
      }
      setValue(getMinimum() + (max - getMinimum()) * rnd.nextDouble());
    }


    public void dispose()
    {
      if (getAxis() != null)
      {
        setDefaultAxis();
      }
      super.dispose();
    }

    protected void drawFace(IGraphics3D g) { }
    protected void drawAxis(IGraphics3D g) { }
    protected void drawHand(IGraphics3D g) { }
    protected void drawColorLines(IGraphics3D g) { }
    
    public Axis getAxis()
    {
      return null;
    }

//TODO    protected void onValueChanged(EventArgs e)
//    {
//      if (ValueChanged != null)
//        ValueChanged(this, e);
//    }

    protected void drawFrame()
    {
      if (getFrame().getVisible())
      {
        getFrame().draw(iOrigRectangle);
      }
    }

    public void draw()
    {
          draw((Graphics3D) chart.getGraphics3D());
    }
    public void draw(IGraphics3D graphics)
    {
      if (getAxis() != null)
      {
        setValues();
        drawFrame(graphics);
        Graphics3D g = (Graphics3D) prepareGraphics(graphics, null, getFaceBrush());
        drawFace(g);
        g = (Graphics3D) prepareGraphics(graphics, getAxis().getAxisPen(), null);
        drawAxis(g);
        g = (Graphics3D) prepareGraphics(graphics, null, null);
        drawHand(g);
      }
    }

    protected IGraphics3D prepareGraphics(ChartPen pen, ChartBrush brush)
    {
      return prepareGraphics(chart.getGraphics3D(), pen, brush);
    }

    protected IGraphics3D prepareGraphics(IGraphics3D graphics, ChartPen pen, ChartBrush brush)
    {
      if (pen != null)
        graphics.setPen(pen);
      if (brush != null)
        graphics.setBrush(brush);
      return graphics;
    }

    protected void drawFrame(IGraphics3D g)
    {
      if (getFrame().getVisible())
      {
        getFrame().draw(g,iOrigRectangle);
      }
    }


    protected void drawAxisTick(IGraphics3D g, Point inner, Point innerPlus, 
                        Point innerMinus, Point outer, Point outerPlus, Point outerMinus)
    {
      getTicks().draw(g, inner, innerPlus, innerMinus, outer, outerPlus, outerMinus);
    }

    protected void drawAxisMinorTick(IGraphics3D g, Point inner, Point outer)
    {
      //MinorTicks.PrepareCanvas(g, MinorTicks.Color);	//CDI done in draw method
      Point innerPlus = new Point(inner.x + Utils.round(getMinorTicks().getHorizSize() / 2.0), inner.y);
      Point innerMinus = new Point(inner.x - Utils.round(getMinorTicks().getHorizSize() / 2.0), inner.y);
      Point outerPlus = new Point(inner.x + Utils.round(getMinorTicks().getHorizSize() / 2.0), inner.y + getMinorTicks().getVertSize());
      Point outerMinus = new Point(inner.x - Utils.round(getMinorTicks().getHorizSize() / 2.0), inner.y + getMinorTicks().getVertSize());
      getMinorTicks().draw(g, inner, innerPlus, innerMinus, outer, outerPlus, outerMinus);
    }

    protected void setAxisOnce()
    {
      if (isDefaultAxis())
      {
        getAxis().getAxisPen().setVisible(true);
        getAxis().getLabels().setVisible(true);
        getAxis().getLabels().getFont().setColor(CustomGauge.getGaugePaletteColor(20, gaugeColorPalette));
        getAxis().getTitle().setVisible(true);
       // getAxis().getTitle().setCaption("Axis Title %");
        getAxis().getTitle().getFont().setSize(12);
        getAxis().getTitle().getFont().setColor(CustomGauge.getGaugePaletteColor(21, gaugeColorPalette));
        getAxis().getLabels().getFont().setName("Arial");
        getAxis().getLabels().getFont().setSize(16);
        getAxis().getLabels().getFont().setBold(true);
      }
    }

    private void setDefaultAxis()
    {
      getAxis().getAxisPen().setVisible(true);
      getAxis().getLabels().setVisible(true);
      getAxis().getLabels().getFont().setColor(Color.BLACK);
      getAxis().getTitle().getFont().setColor(Color.BLACK);
      getAxis().getTitle().setVisible(true);
      getAxis().getLabels().getFont().setName("Verdana");
      getAxis().getTitle().setCaption("");
      getAxis().getLabels().getFont().setSize(8);
      getAxis().getLabels().getFont().setBold(false);
      getAxis().getTicks().setLength(4);
      getAxis().getMinorTicks().setLength(2);
    }

    protected boolean isDefaultAxis()
    {
      boolean result = getAxis() != null;

      if (result)
        result = getAxis().getLabels().getVisible() == true;
      if (result)
        result = getAxis().getLabels().getFont().getName().equals("Default");
      if (result)
        result = getAxis().getLabels().getFont().getSize() == 11;
      if (result)
        result = getAxis().getLabels().getFont().getBold() == false;
      if (result)
        result = getAxis().getLabels().getFont().getItalic() == false;
      if (result)
        result = (getAxis().getLabels().getFont().getColor().equals(Color.fromArgb(255, 0, 0, 0)) || (getAxis().getLabels().getFont().getColor().equals( Color.BLACK)));

      if (result)
        result = getAxis().getLabels().getFont().getStrikeout() == false;
      if (result)
        result = getAxis().getLabels().getFont().getUnderline() == false;

      if (result)
        result = getAxis().getTitle().getVisible() == true;
      if (result)
        result = getAxis().getTitle().getFont().getName().equals("Default");
      if (result)
        result = getAxis().getTitle().getCaption().length() == 0;
      if (result)
        result = getAxis().getTitle().getFont().getSize() == 11;
      if (result)
        result = getAxis().getTitle().getFont().getBold() == false;
      if (result)
        result = getAxis().getTitle().getFont().getItalic() == false;
      if (result)
        result = (getAxis().getTitle().getFont().getColor().equals(Color.fromArgb(255, 0, 0, 0)) || (getAxis().getLabels().getFont().getColor().equals( Color.BLACK)));
      if (result)
        result = getAxis().getTitle().getFont().getStrikeout() == false;
      if (result)
        result = getAxis().getTitle().getFont().getUnderline() == false;

      if (result) {
        result = (getAxis().getAxisPen().getColor().equals(Color.fromArgb(255, 64, 64, 64)));
      }

      if (result)
        result = getAxis().getAxisPen().getWidth() == 2;
      if (result)
        result = getAxis().getAxisPen().getVisible() == true;
      if (result)
        result = getAxis().getAxisPen().getDashCap() == DashCap.FLAT;

      if (result)
        result = getAxis().getAxisPen().getEndCap() == LineCap.BEVEL; //Flat

      if (result)
          result = getAxis().getAxisPen().getStyle() == DashStyle.SOLID;

      if (result)
        result = getAxis().getAxisPen().getTransparency() == 0;

      return result;
    }

    public void doBeforeDrawChart()
    {
      setAxisOnce();
      super.doBeforeDrawChart();
    }

    /*
    *  Called when the Gauge <see cref="CustomGauge.Value"/> property is changed.
    */
    //TODO public event GaugesChangeHandler valueChanged;
    
    public int clicked(int x, int y) {
    	if (iOrigRectangle.contains(x, y)) return 0;
    	else return -1;
    }
    
    public static Color[] BluesPalette = new Color[]
    {
			Color.fromArgb(0, 0, 254),
			Color.fromArgb(170, 206, 255),
			Color.fromArgb(26, 58, 104),

			Color.fromArgb(147, 172, 209),
			Color.fromArgb(207, 220, 233),
			Color.fromArgb(147, 172, 209),
	
			/*Color.fromArgb(19, 247, 28),
			Color.EMPTY,
			Color.fromArgb(207, 220, 233),

			Color.fromArgb(0, 0, 254),
			Color.EMPTY,
			Color.fromArgb(100, 40, 40),*/
                        
                        Color.fromArgb(100, 40, 40),
			Color.EMPTY,
			Color.EMPTY,
                        
                        Color.EMPTY,
                        Color.EMPTY,
			Color.fromArgb(30, 200, 30),

			Color.fromArgb(171, 139, 72),
			Color.fromArgb(253,190,91),
			Color.EMPTY,
			Color.fromArgb(255, 213, 142),

			Color.fromArgb(4, 85, 131),
			Color.fromArgb(9, 92, 138),
			Color.EMPTY,
			Color.fromArgb(22,66,112),

			Color.fromArgb(207, 220, 233),
			Color.fromArgb(207, 220, 233),

			Color.fromArgb(100, 100, 100),
			Color.fromArgb(100, 100, 100),
                        
                        Color.fromArgb(128, 128, 128),
                        Color.fromArgb(192, 192, 192),
                        Color.fromArgb(224, 224, 224)
    };

    public static Color[] BlackPalette = new Color[]
    {
			Color.fromArgb(40, 40, 40),
			Color.fromArgb(50, 50, 50),
			Color.fromArgb(100, 100, 100),

			Color.fromArgb(15, 15, 15),			 //3
			Color.fromArgb(100, 100, 100),
			Color.fromArgb(15, 15, 15),

			Color.fromArgb(100, 40, 40),		 //6
			Color.EMPTY,
			Color.fromArgb(100, 100, 100),

			Color.fromArgb(100, 100, 100),		//9   
			Color.EMPTY,
			Color.fromArgb(30, 200, 30),

			Color.fromArgb(200,115,60),	     //12
			Color.fromArgb(200,115,60),
			Color.fromArgb(200,115,60),
			Color.fromArgb(150,50,10),

			Color.fromArgb(130, 130, 130),   //16
			Color.fromArgb(130, 130, 130),
			Color.EMPTY,
			Color.fromArgb(30,30,30),

			Color.fromArgb(100, 100, 100),
			Color.fromArgb(100, 100, 100),

			Color.fromArgb(100, 100, 100),
			Color.fromArgb(100, 100, 100)
    };
    
     public static Color[] LCDPalette = new Color[]
    {
			Color.fromArgb(40, 40, 40),
			Color.fromArgb(50, 50, 50),
			Color.fromArgb(100, 100, 100),

			Color.fromArgb(170,170,130),		 //3
			Color.fromArgb(170,170,130),
			Color.fromArgb(170,170,130),

			Color.fromArgb(100, 40, 40),		 //6
			Color.EMPTY,
			Color.fromArgb(100, 100, 100),

			Color.fromArgb(100, 100, 100),		//9   
			Color.EMPTY,
			Color.fromArgb(30, 200, 30),

			Color.fromArgb(200,115,60),	     //12
			Color.fromArgb(200,115,60),
			Color.fromArgb(200,115,60),
			Color.fromArgb(150,50,10),

			Color.fromArgb(130, 130, 130),   //16
			Color.fromArgb(130, 130, 130),
			Color.EMPTY,
			Color.fromArgb(30,30,30),

			Color.fromArgb(40, 40, 40),
			Color.fromArgb(100, 100, 100),

			Color.fromArgb(100, 100, 100),
			Color.fromArgb(100, 100, 100)
    };

    public static Color[] LEDPalette = new Color[]
    {
  		        Color.fromArgb(5, 55, 125),
			Color.fromArgb(10, 120, 200),
			Color.fromArgb(5, 90, 160),

			Color.fromArgb(10, 10, 10),			 //3
			Color.fromArgb(10, 10, 10),
			Color.fromArgb(10, 10, 10),

			Color.fromArgb(100, 40, 40),		 //6
			Color.EMPTY,
			Color.fromArgb(100, 100, 100),

			Color.fromArgb(100, 100, 100),		//9   
			Color.EMPTY,
			Color.fromArgb(30, 200, 30),

			Color.fromArgb(200,115,60),	     //12
			Color.fromArgb(200,115,60),
			Color.fromArgb(200,115,60),
			Color.fromArgb(150,50,10),

			Color.fromArgb(130, 130, 130),   //16
			Color.fromArgb(130, 130, 130),
			Color.EMPTY,
			Color.fromArgb(30,30,30),

			Color.fromArgb(255, 30, 30),
			Color.fromArgb(100, 100, 100),

			Color.fromArgb(100, 100, 100),
			Color.fromArgb(100, 100, 100)
    };

   /*
    * Returns the Color with index Index within the color array GaugeColorPalette.
    */
    public static Color getGaugePaletteColor(int index, Color[] gaugeColorPalette)
    {
      while (index > gaugeColorPalette.length - 1)
      {
        index = index - gaugeColorPalette.length;
      }
      return gaugeColorPalette[index];
    }

    private TFrame frame;
    private Color[] gaugeColorPalette;
    private ChartBrush faceBrush;
    private GaugeSeriesPointer ticks;
    protected double fminimum;
    protected double fmaximum;
    private GaugeSeriesPointer minorTicks;
    private GaugeSeriesPointer hand;
    private GaugeSeriesPointer redLine;
    private GaugeSeriesPointer greenLine;
    protected double fredLineStartValue;
    protected double fredLineEndValue;
    protected double fgreenLineStartValue;
    protected double fgreenLineEndValue;
    private int minorTickDistance;
    private boolean horizontal;

   /*
    * The minor tick offset.
    */
    public int getMinorTickDistance()
    {
        return minorTickDistance;
      }
    
    public void setMinorTickDistance(int value)
      {
        if (minorTickDistance != value)
        {
          minorTickDistance = value;
          invalidate();
        }
      }
    

    /*
    * Gets and sets the value at which the 'greenline' marker ends.
    */
    public double getGreenLineEndValue()
    {
      return Math.min(fgreenLineEndValue, getMaximum());
    }
    
    public void setGreenLineEndValue(double value)
      {
        if (fgreenLineEndValue != value)
        {
          fgreenLineEndValue = value;
          invalidate();
        }
      }

   /*
    * Gets and sets the value at which the 'greenline' marker starts.
    */
    public double getGreenLineStartValue()
    {
      return Math.min(fgreenLineStartValue, getMaximum());
    }
    
    public void setGreenLineStartValue(double value)
      {
        if (fgreenLineStartValue != value)
        {
          fgreenLineStartValue = value;
          invalidate();
        }
      }

    /*
    * Gets and sets the value at which the 'redline' marker ends.
    */
    public double getRedLineEndValue()
    {
      return Math.min(fredLineEndValue, getMaximum());
    }
    
    public void setRedLineEndValue(double value)
      {
        if (fredLineEndValue != value)
        {
          fredLineEndValue = value;
          invalidate();
        }
      }

   /*
    * Gets and sets the value at which the 'redline' marker starts.
    */
    public double getRedLineStartValue()
    {
      return Math.max(fredLineStartValue, getMinimum());
    }
    
    public void setRedLineStartValue(double value)
      {
        if (fredLineStartValue != value)
        {
          fredLineStartValue = value;
          invalidate();
        }
      }

   /*
    * The 'redline' object is the curved shape with a gradient which is used to signal a high or maximum value.
    */
    public GaugeSeriesPointer getRedLine()
    {
        if (redLine == null)
        {
          redLine = new GaugeSeriesPointer(chart, this);
          redLine.setGaugePointerStyle(GaugePointerStyles.COLORLINE);
          redLine.setVertSize(5);
          redLine.getBrush().setVisible(true);
          redLine.getBrush().setColor(Color.BLACK);
          redLine.getGradient().setVisible(true);
          redLine.getGradient().setDirection(GradientDirection.HORIZONTAL);
//          redLine.getGradient().setSigma(true);
//          redLine.getGradient().setSigmaFocus(0.0f);
//          redLine.getGradient().setSigmaScale(1.0f);
          redLine.getGradient().setStartColor(CustomGauge.getGaugePaletteColor(8, gaugeColorPalette));
          redLine.getGradient().setMiddleColor(CustomGauge.getGaugePaletteColor(7, gaugeColorPalette));
          redLine.getGradient().setEndColor(CustomGauge.getGaugePaletteColor(6, gaugeColorPalette));

        }
        return redLine;
      }

    public void setRedLine(GaugeSeriesPointer value)
      {
        redLine = value;
      }
    
    public boolean getHorizontal() {
       return horizontal; 
    }
        
    public void setHorizontal(boolean value) {
       horizontal = setBooleanProperty(horizontal, value); 
       setAxisOnce();
    }


   /*
    * The 'greenline' object is the curved shape with a gradient which is used to signal a low or minimum value.
    */
    public GaugeSeriesPointer getGreenLine()
    {
        if (greenLine == null)
        {
          greenLine = new GaugeSeriesPointer(chart, this);
          greenLine.setGaugePointerStyle(GaugePointerStyles.HAND);
          greenLine.setVertSize(5);
          greenLine.getBrush().setVisible(true);
          greenLine.getBrush().setColor(Color.BLACK);
          greenLine.getGradient().setVisible(true);
          greenLine.getGradient().setDirection(GradientDirection.HORIZONTAL);
//          greenLine.getGradient().setSigma(true);
//          greenLine.getGradient().setSigmaFocus(0.0f);
//          greenLine.getGradient().setSigmaScale(1.0f);
          greenLine.getGradient().setStartColor(CustomGauge.getGaugePaletteColor(11, gaugeColorPalette));
          greenLine.getGradient().setMiddleColor(CustomGauge.getGaugePaletteColor(10, gaugeColorPalette));
          greenLine.getGradient().setEndColor(CustomGauge.getGaugePaletteColor(9, gaugeColorPalette));

        }
        return greenLine;
      }
      
    public void setGreenLine(GaugeSeriesPointer value)
      {
        greenLine = value;
      }

   /*
    * Gets and sets the Hand (or needle) object characteristics.
    */
    public GaugeSeriesPointer getHand()
    {
        if (hand == null)
        {
          hand = new GaugeSeriesPointer(chart, this);
          hand.setVisible(true);
          hand.setGaugePointerStyle(GaugePointerStyles.HAND);
          hand.getBrush().setVisible(true);
          hand.getBrush().setColor(Color.BLUE);
          hand.getBrush().setTransparency(50);
          hand.getPen().setColor(CustomGauge.getGaugePaletteColor(12, gaugeColorPalette));
        }
        return hand;
      }

    public void setHand(GaugeSeriesPointer value)
      {
        hand = value;
      }
    

   /*
    * Gets and sets the characteristics of the Axis' minor tick marks.
    */
    public GaugeSeriesPointer getMinorTicks()
    {
        if (minorTicks == null)
        {
          minorTicks = new GaugeSeriesPointer(chart, this);
          minorTicks.setGaugePointerStyle(GaugePointerStyles.MINORTICK);
          minorTicks.setColor(Color.TRANSPARENT);
          minorTicks.setVertSize(2);
          minorTicks.setHorizSize(2);
          minorTicks.getPen().setColor(CustomGauge.getGaugePaletteColor(23, gaugeColorPalette));

        }
        return minorTicks;
      }
    
    public void setMinorTicks(GaugeSeriesPointer value)
      {
        minorTicks = value;
      }
    
    public void setValue(double value)
    {
      if (getValue() != value)
      {
        mandatory.setValue(0,value);
//TODO MM        onValueChanged(new EventArgs());
        invalidate();
      }
    }

   /*
    * Gets or sets the position of gauge arrow line.
    */
    public double getValue()
    {
        if (getCount() == 0)
          add(0);

        return Math.min(mandatory.getValue(0), getMaximum());
    }

   /*
    * Gets or sets the minimum value for the gauge.
    */
    public double getMinimum()
    {
        return fminimum;
    }

    public void setMinimum(double value)
      {
        if (fminimum != value)
        {
          fminimum = value;
          setValue(Math.max(getMinimum(), getValue()));
          invalidate();
        }
      }
    

   /*
    * Gets or sets the maximum value for the gauge.
    */
    public double getMaximum()
    {
        return fmaximum;
    }

    public void setMaximum(double value)
      {
        if (fmaximum != value)
        {
          fmaximum = value;
          setValue(Math.min(getMaximum(), getValue()));
          invalidate();
        }
      }
    
   /*
    * Gets and sets the characteristics of the Axis' major tick marks.
    */
    public GaugeSeriesPointer getTicks()
    {
        if (ticks == null)
        {
          ticks = new GaugeSeriesPointer(chart, this);
          ticks.setGaugePointerStyle(GaugePointerStyles.TICK);
          ticks.setColor(Color.TRANSPARENT);
          ticks.setVertSize(20);
          ticks.getPen().setColor(CustomGauge.getGaugePaletteColor(22, gaugeColorPalette));
        }
        return ticks;
      }
    
    public void setTicks(GaugeSeriesPointer value)
      {
        ticks = value;
      }
    
   /*
    * ChartBrush characteristics of the CustomGauge's face.
    */
    public ChartBrush getFaceBrush()
    {
        if (faceBrush == null)
        {
          faceBrush = new ChartBrush(chart);
          faceBrush.setVisible(true);
          faceBrush.setSolid(true);
          faceBrush.setTransparency(0);
          faceBrush.getGradient().setVisible(true);
          faceBrush.getGradient().setStartColor(CustomGauge.getGaugePaletteColor(3, getGaugeColorPalette()));
          faceBrush.getGradient().setMiddleColor(CustomGauge.getGaugePaletteColor(4, getGaugeColorPalette()));
          faceBrush.getGradient().setEndColor(CustomGauge.getGaugePaletteColor(5, getGaugeColorPalette()));
          faceBrush.getGradient().setVisible(true);
          if(getHorizontal()) {
            faceBrush.getGradient().setDirection(GradientDirection.VERTICAL);
          }
          else {
            faceBrush.getGradient().setDirection(GradientDirection.HORIZONTAL);   
          }
//          faceBrush.getGradient().getStyle().setCenterXOffset(Math.round(iNewRectangle.width * 0.2));
//          faceBrush.getGradient().getStyle().setCenterYOffset(Math.round(iNewRectangle.height * -0.2));
        }
        return faceBrush;
      }
    
    public void setFaceBrush(ChartBrush value)
      {
        faceBrush = value;
      }

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
        getFrame().setGaugeColorPalette(gaugeColorPalette);
        redLine = null;
        greenLine = null;
        hand = null;
        faceBrush = null;
        invalidate();
      }

   /*
    * The Frame is the object which surrounds the LinearGauge and which consists of three configurable Bands: Inner, Middle and Outer.
    */
    public TFrame getFrame()
    {
        if (frame == null)
        {
          frame = new TFrame(getChart(), getGaugeColorPalette(), false);
        }
        return frame;
    }

    public void setFrame(TFrame value)
      {
        frame = value;
      }
}
