package com.steema.teechart.axis;

import com.steema.teechart.languages.Language;
import java.text.DateFormat;

import com.steema.teechart.drawing.DashStyle;
import com.steema.teechart.drawing.Point;
import com.steema.teechart.drawing.StringAlignment;
import com.steema.teechart.drawing.ChartPen;
import com.steema.teechart.styles.SeriesCollection;
import com.steema.teechart.drawing.ChartFont;
import com.steema.teechart.drawing.IGraphics3D;
import com.steema.teechart.styles.ISeries;
import com.steema.teechart.TeeBase;
import com.steema.teechart.DateTimeStep;
import com.steema.teechart.Rectangle;
import com.steema.teechart.TextShape;
import com.steema.teechart.PositionUnits;
import com.steema.teechart.DateTime;
import com.steema.teechart.FloatRange;
import com.steema.teechart.IBaseChart;
import com.steema.teechart.ChartException;

import com.steema.teechart.MultiLine;
import com.steema.teechart.drawing.Color;
import com.steema.teechart.misc.Utils;


/**
 *
 * <p>Title: Axis class</p>
 *
 * <p>Description: Accesses all Axis characteristics</p>
 *
 * <p>Copyright (c) 2005-2019 by Steema Software SL. All Rights Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 *
 */
public class Axis extends TeeBase
{

	private static final long serialVersionUID = 1L;
    /**
     * Intrnal use. Inverts scaling direction of Axis.
     * 
     * @param value
     */
    public void internalSetInverted(boolean value)
    {
        inverted = value;
    }

    public int iGapSize;
    private AxisLabels labels;
    private Axis partnerAxis;
    private boolean usePartnerAxis = false;
    //CDI GridBand -- changed from private
    transient public AxisDraw axisDraw;
    //public Rectangle ChartRect;
    //*********************
    /* static finals */
    public static final double MINAXISRANGE = 0.0000000001;
    public static final int MAXPIXELPOS = 32767;

    protected boolean useMaxPixelPos = true;

    /* variable Min Axis increment*/
    transient double iMinAxisIncrement;

    /* scales */
    private boolean automatic = true;
    private boolean automaticMaximum = true;
    private boolean automaticMinimum = true;
    private double desiredIncrement;
    private double maximumValue;
    private double minimumValue;
    private boolean logarithmic;
    private double logarithmicBase = 10;
    private boolean maximumRounded = false;
    private boolean minimumRound = false;
    // axis
    protected AxisLinePen axispen;
    transient int posAxis;
    // pixels distance
    private int maximumOffset;
    private int minimumOffset;
    // title
    protected AxisTitle axisTitle;
    transient int posTitle;
    // grid
    protected GridPen grid;
    transient public boolean hideBackGrid;
    transient public boolean hideSideGrid;
    // ticks
    protected ChartPen minorGrid;
    private int minorTickCount = 3;
    private TicksPen minorTicks;
    private TicksPen ticks;
    private TicksPen ticksInner;
    private boolean tickonlabelsonly = true;
    // other
    protected boolean inverted;
    protected boolean horizontal;
    private boolean otherSide;
    protected boolean bVisible = true;
    private double startPosition;
    private double endPosition = 100;
    private double relativePosition;
    private PositionUnits positionUnits = PositionUnits.PERCENT;
    protected double zPosition;
    // internal
    transient protected boolean iAxisDateTime;
    transient private double iAxisLogSizeRange;
    transient private double iAxisSizeRange;
    transient int iCenterPos;
    transient private double iLogMax;
    transient private double iLogMin;
    transient double iMaximum;
    transient double iMinimum;
    transient double iRange;
    transient double iRangelog;
    public transient boolean iRangezero;
    transient SeriesCollection iSeriesList;
    transient public int iStartPos;
    transient public int iEndPos;
    transient public int iAxisSize;
    transient public boolean isDepthAxis;
    transient public int iZPos;
    static final public int AXISCLICKGAP = 3; // minimum pixels distance to trigger axis click

    public Axis()
    {
        this((IBaseChart) null);
    }

    public Axis(IBaseChart chart)
    {
        super(chart);
        labels = new AxisLabels(this);
        axisDraw = new AxisDraw(this);
        axispen = new AxisLinePen(chart);
        readResolve();
        
        setCalcPosValue();
    }

    public Axis(boolean horiz, boolean isOtherSide, IBaseChart chart)
    {
        this(chart);
        horizontal = horiz;
        otherSide = isOtherSide;
        
        setCalcPosValue();
    }

    protected Object readResolve()
    {
        iMinAxisIncrement = 0.000000000001;
        return this;
    }


    transient private final static AxisCalcResolver defaultAxisCalcResolver = new
            AxisCalcAdapter();

    transient protected AxisCalcResolver axisCalcResolver =
            defaultAxisCalcResolver;

    public void setAxisCalcResolver(AxisCalcResolver resolver) {
        this.axisCalcResolver = resolver;
    }

    public void removeAxisCalcResolver() {
        this.axisCalcResolver = defaultAxisCalcResolver;
    }

    public AxisCalcResolver getAxisCalcResolver() {

        if (this.axisCalcResolver == null)
        {
            this.setCalcPosValue();
        }

        return axisCalcResolver;
    }

    public boolean getUseMaxPixelPos() { return useMaxPixelPos; }

    public void setUseMaxPixelPos(boolean value) { useMaxPixelPos=value; }

    private void internalSetMaximum(double value)
    {
        maximumValue = setDoubleProperty(maximumValue, value);
    }

    private void internalSetMinimum(double value)
    {
        minimumValue = setDoubleProperty(minimumValue, value);
    }

    public int setAxisCalcXPosValueDelegate(double value){
      if (getIsDepthAxis())
      {
          return internalCalcDepthPosValue(value);
      }
      else
      if (getLogarithmic())
      {
          return internalCalcLogPosValue(true, value);
      }
      else if (iRangezero)
      {
          return iCenterPos;
      }
      else
      {
          return xPosValueCheck(value);
      }
    }

    public int setAxisCalcYPosValueDelegate(double value){
      if (getIsDepthAxis())
      {
          return internalCalcDepthPosValue(value);
      }
      else
      if (getLogarithmic())
      {
          return internalCalcLogPosValue(false, value);
      }
      else if (iRangezero)
      {
          return iCenterPos;
      }
      else
      {
          return yPosValueCheck(value);
      }
    }

    public int setAxisCalcPosValueDelegate(double value){
      if (getHorizontal())
         return setAxisCalcXPosValueDelegate(value);
      else
         return setAxisCalcYPosValueDelegate(value);
    }
    
    /*
    * Advanced use. Requests the Axis to re-check assigned calculation methods
    */
    public void setCalcPosValue()
    {
      setAxisCalcResolver(new AxisCalcResolver(){

          public void AxisLabelValueDelegate(Axis axis, AxisLabelValueEventArgs e) {
               //not actioned
          }

          public int AxisCalcPosLabelsEventHandler(Axis axis, int value){
              //mm pending
              return 0;
          }
          
          public int AxisCalcXPosValueDelegate(double value){ 
              return setAxisCalcXPosValueDelegate(value);
          }
          
          public int AxisCalcYPosValueDelegate(double value){ 
              return setAxisCalcYPosValueDelegate(value);
          }
          
          public int AxisCalcPosValueDelegate(double value){ 
              return setAxisCalcPosValueDelegate(value);
          }
          
          public double AxisCalcPosPointDelegate(int value){ 
              //CalcPosPoint = new AxisCalcPosPointDelegate(InternalCalcPosPoint);
              return internalCalcPosPoint(value);
          }

          //placeholders for AxisBreak use.
          public double oldCalcPosPointDelegate(int value){
              return AxisCalcPosPointDelegate(value);
          }

          public int oldCalcXDelegate(double value){
              return AxisCalcXPosValueDelegate(value);
          }

          public int oldCalcYDelegate(double value){
              return AxisCalcYPosValueDelegate(value);
          }

      });
    }

    /**
     * internal use. Returns Axis Max-Min range
     * 
     * @return double
     */
    public double getRange()
    {
        return iRange;
    }

    /**
     * Accesses the Label characteristics of Axis Labels.
     *
     * @return AxisLabels
     */
    public AxisLabels getLabels()
    {
        return labels;
    }

    /**
     * Returns the custom labels
     *
     * @return AxisLabelsItems
     */
    public AxisLabelsItems getCustomLabels()
    {
        return labels.getItems();
    }

    /**
     * Calculates Max and Min of axis scale based on associated Series.
     *
     * @return boolean
     */
    public boolean getAutomatic()
    {
        return automatic;
    }

    /**
     * Max and Min of axis scale based on associated Series.
     *
     * @param value boolean
     */
    public void setAutomatic(boolean value)
    {
        automatic = setBooleanProperty(automatic, value);
        automaticMinimum = value;
        automaticMaximum = value;
    }

    protected boolean shouldSerializeHorizontal()
    {
        return isCustom();
    }

    /**
     * Determines the Custom axis to be drawn horizontally.
     *
     * @return boolean
     */
    public boolean getHorizontal()
    {
        return horizontal;
    }

    /**
     * Sets a custom axis to be drawn horizontally.
     *
     * @param value boolean
     */
    public void setHorizontal(boolean value)
    {
        horizontal = setBooleanProperty(horizontal, value);
    }

    protected boolean shouldSerializeOtherSide()
    {
        return isCustom();
    }

    /**
     * Positions the Axis labels to the Otherside of the axis.<br> For instance,
     * moves labels on a vertical Axis to the right of the Axis. This is
     * useful when adding a Custom Axis, to place the labelling to the Right
     * for a right vertical Axis, or to the Top for a Top Axis.
     *
     * @return boolean
     */
    public boolean getOtherSide()
    {
        return otherSide;
    }

    /**
     * Sets the Axis labels to the Otherside of the axis.
     *
     * @param value boolean
     */
    public void setOtherSide(boolean value)
    {
        otherSide = setBooleanProperty(otherSide, value);
    }

    /**
     * Internal use.
     */
    public void internalCalcRange()
    {
        iRange = iMaximum - iMinimum;
        
        if ((iRange - MINAXISRANGE) < 1E-17) //safety #1425
            iRange = 0;
        
        iRangezero = iRange == 0;
        iAxisSizeRange = iRangezero ? 0 : iAxisSize / iRange;
        
        if (logarithmic)
        {
            iLogMin = (iMinimum <= 0) ? 0 : Utils.log(iMinimum);
            iLogMax = (iMaximum <= 0) ? 0 : Utils.log(iMaximum);
            iRangelog = iLogMax - iLogMin;
            iAxisLogSizeRange = (iRangelog == 0) ? 0
                    : iAxisSize / iRangelog;
        }

        iZPos = calcZPos();
    }

    private FloatRange reCalcAdjustedMinMax(int pos1, int pos2)
    {
        int oldStart = iStartPos;
        int oldEnd = iEndPos;
        iStartPos += pos1;
        iEndPos -= pos2;
        iAxisSize = iEndPos - iStartPos;

        return new FloatRange(internalCalcPosPoint(oldStart),
                              internalCalcPosPoint(oldEnd));
    }

    /**
     * Called internally to recalculate the max and min values using the
     * Rect parameter positions.<br>
     * The Rect parameter determines in pixels the margins to apply to
     * ChartRect. The Axis recalculates the appropiate maximum and minimum
     * values using the Rect parameter positions.
     *
     * @param rLeft int
     * @param rTop int
     * @param rRight int
     * @param rBottom int
     */
    public void adjustMaxMinRect(int rLeft, int rTop, int rRight, int rBottom)
    {
        FloatRange tmp;

        if (horizontal)
        {
            tmp = reCalcAdjustedMinMax(rLeft, rRight);
        }
        else
        {
            tmp = reCalcAdjustedMinMax(rTop, rBottom);
        }

        internalCalcPositions();
        iMaximum = tmp.max;
        iMinimum = tmp.min;

        if (iMinimum > iMaximum)
        { // swap
            double tmp2 = iMinimum;
            iMinimum = iMaximum;
            iMaximum = tmp2;
        }

        internalCalcRange();
    }

    /**
     * Returns true if Axis is a Custom Axis
     *
     * @return boolean
     */
    public boolean isCustom()
    {
        return chart.isAxisCustom(this);
    }

    /**
     * Calculates Maximum and Minimum values based on Max and Min values of
     * the dependent Series.<br>
     * AdjustMaxMin is automatically called if Axis.Automatic is true.
     * The Chart Zoom.Undo method calls AdjustMaxMin for Left, Right,Top
     * and Bottom axis.
     */
    public void adjustMaxMin()
    {
        FloatRange tmp = new FloatRange();
        calcMinMax(tmp);
        minimumValue = tmp.min;
        maximumValue = tmp.max;
        iMaximum = maximumValue;
        iMinimum = minimumValue;
        
        if (getChart().getParent().getAxisLabelResolver()!=null)
        {
            boolean tmpStop=false;
            double tmpGap=iMaximum;
            NextAxisLabelValue ev = new NextAxisLabelValue(-1, tmpGap, tmpStop);
            getChart().getParent().getAxisLabelResolver().getNextLabel(this, -1, ev);
            iMaximum=iMaximum-ev.getLabelValue();
        }
        
        internalCalcRange();
        calcRoundScales();
    }

    private boolean setAutoMinMax(boolean variable, boolean var2,
            boolean value)
    {
        variable = setBooleanProperty(variable, value);
        if (value)
        {
            // if both are automatic, then Automatic should be True too
            if (var2)
            {
                automatic = true;
            }
        }
        else
        {
            automatic = false;
        }
        return variable;
    }

    /**
     * Controls if Axis will adjust the Maximum value automatically based on
     * the maximum value of its associated Series.<br>
     * Default value: true
     *
     * @return boolean
     */
    public boolean getAutomaticMaximum()
    {
        return automaticMaximum;
    }

    /**
     * The Axis adjusts the Maximum value automatically based on the maximum
     * value of its associated Series when true.<br>
     * Default value: true
     *
     * @param value boolean
     */
    public void setAutomaticMaximum(boolean value)
    {
        automaticMaximum = setAutoMinMax(automaticMaximum, automaticMinimum,
                value);
    }

    /**
     * Controls minimum value automatically based on the minimum value of
     * its associated Series.<br>
     * Default value: true
     *
     * @return boolean
     */
    public boolean getAutomaticMinimum()
    {
        return automaticMinimum;
    }

    /**
     * The Axis adjusts the Minimum value automatically based on the maximum
     * value of its associated Series when true.<br>
     * Default value: true
     *
     * @param value boolean
     */
    public void setAutomaticMinimum(boolean value)
    {
        automaticMinimum = setAutoMinMax(automaticMinimum, automaticMaximum,
                value);
    }

    /**
     * Determines the kind of pen used to draw the major Axis lines.<br>
     * These are the lines which go from Axis Minimum to Axis Maximum screen
     * positions.
     *
     * @return AxisLinePen
     */
    public AxisLinePen getAxisPen()
    {
        return axispen;
    }

    /**
     * Determines the kind of pen used to draw the Grid lines at every Axis
     * Label position.<br> These are the lines which go from "cousin Axis"
     * Minimum to "cousin Axis" Maximum screen positions for each Label
     * position.<br>
     * Use the MinorGrid property to make the Grid from Minor Ticks visible.
     *
     * @return GridPen
     */
    public GridPen getGrid()
    {
        return (grid == null) ? grid = new GridPen(chart) : grid;
    }

//   public GridPen setGrid(int value, boolean boolValue) {
//      boolean centered = boolValue;
//      int lines  = value;
//
//      if (1 < lines) { lines = 1;}
//      if (null == grid)
//      {
//        grid = new GridPen(chart);
//      }
//        grid.setCentered(centered);
//        grid.setDrawEvery(lines);
//      return grid;
//    }
    /**
     * Obsolete.&nbsp;Please use Labels.<!-- -->OnAxis instead.
     *
     * @return boolean
     */
    public boolean getLabelsOnAxis()
    {
        return labels.bOnAxis;
    }

    /**
     * Obsolete.&nbsp;Please use Labels.<!-- -->OnAxis instead.
     *
     * @param value boolean
     */
    public void setLabelsOnAxis(boolean value)
    {
        labels.bOnAxis = value;
    }

    /**
     * Returns if the Axis dependent values are DateTime or not. <br>
     * Each Chart Axis can consider values to be normal numbers or DateTime
     * values. An axis is "DateTime" if at least one Active Series with
     * datetime values is associated to it.<br>
     * Default value: false
     *
     * @return boolean
     */
    public boolean isDateTime()
    {
        for (int t = 0; t < chart.getSeriesCount(); t++)
        {
            ISeries s = chart.getSeries(t);
            if (s.getActive())
            {
                if (s.associatedToAxis(this))
                {
                    return getHorizontal() ? s.getXValues().getDateTime()
                            : s.getYValues().getDateTime();
                }
            }
        }
        return false;
    }

    /**
     * Determines the minimum step between axis labels.<br>
     * Can use DateTimeStep for date-time axis. It must be a positive number
     * or DateTime value. TChart will use this value as the inicial axis label
     * step. If there is not enough space for all labels, TChart will calculate
     * a bigger one. You can use the DateTimeStep constant array for DateTime
     * increments.
     *
     *
     * @return double
     */
    public double getIncrement()
    {
        return desiredIncrement;
    }

    /**
     * Sets the minimum step between axis labels.
     *
     * @param value double
     */
    public void setIncrement(double value)
    {

        /** @todo RAISING EXCEPTIONS HERE FORCES DECLARING "THROW" EVERYWHERE!  */
//        if (value < 0) {
//            throw new TeeChartException(Language.getString("AxisIncrementNeg"));
//        }
        //if (isDateTime()) System.Convert.ToDateTime(value).ToString();
        desiredIncrement = setDoubleProperty(desiredIncrement, value);
    }

    /**
     * Swaps the Axis Minimum and Maximum scales.<br>
     * When true, Axis Minimum and Maximum scales will be swapped. Axis labels
     * and Series points will be displayed accordingly. This applies both to
     * vertical and horizontal axis.<br>
     * Default value: false
     *
     * @return boolean
     */
    public boolean getInverted()
    {
        return inverted;
    }

    /**
     * Swaps the Axis Minimum and Maximum scales when true.<br>
     * Default value: false
     *
     * @param value boolean
     */
    public void setInverted(boolean value)
    {
        inverted = setBooleanProperty(inverted, value);
        if (isDepthAxis)
        {
            if (this == getChart().getAxes().getDepth())
            {
                chart.getAxes().getDepthTop().inverted = getInverted();
            }
            else
            {
                chart.getAxes().getDepth().inverted = getInverted();
            }
        }
    }

    /**
     * Scales the Axis Logarithmically when true.<br>
     * Axis Minimum and Maximum values should be greater than 0, and Axis
     * cannot be of DateTime type.<br>
     * Default value: false
     *
     * @return boolean
     */
    public boolean getLogarithmic()
    {
        return logarithmic;
    }
    
    /**
     * Returns whether this is Depth Axis
     */
    public boolean getIsDepthAxis()
    {
        return isDepthAxis;
    }
    

    /**
     * Scales the Axis Logarithmically when true.<br>
     * Default value: false
     *
     * @param value boolean
     * @throws ChartException
     */
    public void setLogarithmic(boolean value) throws ChartException
    {
        if (chart != null)
        { //CDI for custom axes at designtime this value is
            //sometimes null.
            if ((value) && (isDateTime()))
            {
                throw new ChartException(Language.getString("AxisLogDateTime"));
            }

            if (value)
            {
                adjustMaxMin();
                if ((iMinimum < 0) || (iMaximum < 0))
                {
                    throw new ChartException(Language.getString("AxisLogNotPositive"));
                }
            }
        }

        logarithmic = setBooleanProperty(logarithmic, value);
    }

    protected boolean shouldSerializeMaximum()
    {
        return (!automatic) && (!automaticMaximum);
    }

    /**
     * Amount of pixels that will be left as a margin at axis maximum
     * position.<br>
     * It is useful when you don't want the series to display points very
     * close to axis boundaries. <br>
     * Default value: 0
     * <br><br>
     * Example:
     * <pre><font face="Courier" size="4">
     * myChart.getAxes().getBottom().setMaximumOffset(4);
     * myChart.getAxes().getBottom().setMinimumOffset(4);
     * </font></pre>

     * @return int
     */
    public int getMaximumOffset()
    {
        return maximumOffset;
    }

    /**
     * Sets the amount of pixels that will be left as a margin at axis maximum
     * position.<br>
     * Default value: 0
     *
     * @param value int
     */
    public void setMaximumOffset(int value)
    {
        maximumOffset = setIntegerProperty(maximumOffset, value);
    }

    /**
     * The highest value an Axis will use to scale their dependent Series
     * point values.<br>
     * It can be any number or DateTime value. It must be greater than the
     * Axis.Minimum value.<br><br>
     * VERY IMPORTANT: <br>
     * Axis.Automatic property must be FALSE. If Axis.Automatic is true,
     * the Axis will set Maximum and Minimum values to Maximum and Minimum
     * dependent Series values.
     *
     * @return double
     */
    public double getMaximum()
    {
        return maximumValue;
    }

    /**
     * Sets the highest value an Axis will use to scale their dependent Series
     * point values.<br>
     *
     * @param value double
     */
    public void setMaximum(double value)
    {
        internalSetMaximum(value);
    }

    protected boolean shouldSerializeMinimum()
    {
        return (!automatic) && (!automaticMinimum);
    }

    /**
     * Advanced use. Smallest Axis calculation increment.<br>
     * Default value: 1e-12
     *
     * @return double
     */
    public double getMinAxisIncrement()
    {
        return iMinAxisIncrement;
    }

    /**
     * Advanced use. Determines the smallest Axis calculation increment.<br>
     * Default value: 1e-12
     *
     * @param value double
     */
    public void setMinAxisIncrement(double value)
    {
        iMinAxisIncrement = value;
    }

    /**
     * The lowest value an Axis will use to scale their dependent Series
     * point values.<br>
     * It can be any number or DateTime value. It must be lower than the
     * Axis.Maximum value. <br><br>
     * VERY IMPORTANT: <br>
     * Axis.Automatic property must be FALSE. If Axis.Automatic is true,
     * the Axis will set Maximum and Minimum values to Maximum and Minimum
     * dependent Series values.
     *
     * @return double
     */
    public double getMinimum()
    {
        return minimumValue;
    }

    /**
     * Sets the lowest value an Axis will use to scale their dependent Series
     * point values.<br>
     *
     * @param value double
     */
    public void setMinimum(double value)
    {
        internalSetMinimum(value);
    }

    /**
     * The number of pixels that will be left as a margin at axis minimum
     * position.<br>
     * It is useful when you don't want the series to display points very close
     * to axis boundaries.<br>
     * Default value: 0 <br>
     *
     * @return int
     */
    public int getMinimumOffset()
    {
        return minimumOffset;
    }

    /**
     * Determines the number of pixels that will be left as a margin at axis
     * minimum position.<br>
     * Default value: 0 <br>
     *
     * @param value int
     */
    public void setMinimumOffset(int value)
    {
        minimumOffset = setIntegerProperty(minimumOffset, value);
    }

    /**
     * The number of Axis minor ticks between major ticks.<br>
     * Axis minor ticks are the Axis sub-ticks between major ticks. It should
     * be a positive number greater than zero and less than half the number
     * of pixels between major ticks, otherwise Minor ticks will "overlap".<br>
     * Default value: 3
     *
     * @return int
     */
    public int getMinorTickCount()
    {
        return minorTickCount;
    }

    /**
     * Determines the number of Axis minor ticks between major ticks.<br>
     * Default value: 3
     *
     * @param value int
     */
    public void setMinorTickCount(int value)
    {
        minorTickCount = setIntegerProperty(minorTickCount, value);
    }

    /**
     * Determines the Pen used to draw the Axis Minor ticks.<br>
     * Minor ticks will only be displayed if MinorTicks.Visible is true.<br>
     *
     * @return TicksPen
     */
    public TicksPen getMinorTicks()
    {
        if (minorTicks == null)
        {
            minorTicks = new TicksPen(chart);
            minorTicks.length = 2;
            minorTicks.defaultLength = 2;
        }
        return minorTicks;
    }

    /**
     * Determines the Pen used to draw the Axis Minor ticks.<br>
     * Minor ticks will only be displayed if MinorTicks.Visible is true.<br>
     *
     * @param value TicksPen
     */
    public void setMinorTicks(TicksPen value)
    {
        minorTicks = value;
    }

    /**
     * Obsolete.&nbsp;Please use Position instead.
     *
     * @return int
     */
    public int getPosAxis()
    {
        return posAxis;
    }

    public void setPosAxis(int value)
    {
        posAxis = setIntegerProperty(posAxis, value);
    }

    /**
     * Determines the screen co-ordinate where axis is drawn.<br>
     * It returns the position of the Axis in pixels relative to the Chart
     * Panel. For horizontal axes the number is a Y position, for vertical
     * axes an X position. <br>
     * Default value: 0
     *
     * @return int
     */
    public int getPosition()
    {
        return posAxis;
    }

    /**
     * The Axis Ticks and Axis Grid to be drawn only coincide at Labels.<br>
     * Otherwise, they will be drawn at all axis increment positions.
     * When the Axis.Labels.Separation property is greater than 0 (default 10),
     * Axis increases the increment property to avoid Axis Label overlap.<br>
     * Default value: true
     *
     * @return boolean
     */
    public boolean getTickOnLabelsOnly()
    {
        return tickonlabelsonly;
    }

    /**
     * Sets the Axis Ticks and Axis Grid to be drawn to only coincide at
     * Labels.<br>
     * Default value: true
     *
     * @param value boolean
     */
    public void setTickOnLabelsOnly(boolean value)
    {
        tickonlabelsonly = setBooleanProperty(tickonlabelsonly, value);
    }

    /**
     * Determines the kind of Pen used to draw Axis marks along the Axis line.
     * <br>
     * Ticks position is calculated based on Axis.Increment,
     * Axis.Labels.Separation and Axis.Label.Style methods.<br>
     * There are three kind of ticks available:  Ticks, MinorTicks and
     * TicksInner.<br> You can show or hide any of them of have all of them
     * Visible. Ticks.Length defines the length of Axis Ticks in logical pixels.
     *
     * @return TicksPen
     */
    public TicksPen getTicks()
    {
        if (ticks == null)
        {
            ticks = new TicksPen(chart);
            ticks.length = 4;
            ticks.defaultLength = 4;
        }
        return ticks;
    }

    /**
     * Accesses the Ticks characteristics.
     *
     * @param value TicksPen
     */
    public void setTicks(TicksPen value)
    {
        ticks = value;
    }

    /**
     * Determines the kind of Pen used to draw Axis marks along the Axis line.
     * <br>
     * This does the same as Ticks, but lines are drawn inside Chart boundaries
     * instead. TicksInner position is calculated based on Axis.Increment,
     * Axis.Labels.Separation and Axis.Label.Style.<br> There are three kind of
     * ticks available:  Ticks, MinorTicks and TicksInner.<br> You can show or
     * hide any of them of have all of them Visible. TickInnerLength defines
     * the length of Axis TicksInner in logical pixels.
     *
     * @return TicksPen
     */
    public TicksPen getTicksInner()
    {
        if (ticksInner == null)
        {
            ticksInner = new TicksPen(chart);
        }
        return ticksInner;
    }

    /**
     * An Axis sub-class used to define Title attributes.<br>
     * Axis Titles are a string of text drawn near Axes. Use Text to specify
     * Axis Title text.<br>
     * Use Font and Angle to set the format desired.
     *
     * @return AxisTitle
     */
    public AxisTitle getTitle()
    {
        if (axisTitle == null)
        {
            axisTitle = new AxisTitle(chart);
        }
        return axisTitle;
    }

    /**
     * Accesses the Axis Title characteristics.
     *
     * @param value AxisTitle
     */
    public void setTitle(AxisTitle value)
    {
        axisTitle = value;
    }

    /**
     * Obsolete.&nbsp;Please use Axis.<!-- -->Title.<!-- -->CustomSize
     *
     * @return int
     */
    public int getTitleSize()
    {
        return getTitle().getCustomSize();
    }

    /**
     * Obsolete.&nbsp;Please use Axis.<!-- -->Title.<!-- -->CustomSize
     *
     * @param value int
     */
    public void setTitleSize(int value)
    {
        getTitle().setCustomSize(value);
    }

    /**
     * Shows or hides the Axis lines, ticks, grids, labels and title.<br>
     * You can change it both at design and runtime.<br>
     * Default value: true
     *
     * @return boolean
     */
    public boolean getVisible()
    {
        return bVisible;
    }

    /**
     * Shows the Axis lines, ticks, grids, labels and title when true.<br>
     * Default value: true
     *
     * @param value boolean
     */
    public void setVisible(boolean value)
    {
        bVisible = setBooleanProperty(bVisible, value);
    }

    protected boolean shouldSerializeZPosition()
    {
        return (!isDepthAxis)
                && ((getOtherSide() && (zPosition != 100))
                || ((!getOtherSide()) && (zPosition != 0)));
    }

    /**
     * Determines the Z axis position along chart Depth as a percentage of the
     * total depth.<br>
     * It can even be set to negative values to place the axis in front of the
     * Chart, or values greater than 100% to place the axis behind the Chart.
     * In 3D or orthogonal display modes, ZPosition controls where to display
     * the axis line. It useful if you want to move axis to front or to back
     * in 3D view.<br>
     *
     * @return double
     */
    public double getZPosition()
    {
        return zPosition;
    }

    /**
     * Sets the Z axis position along chart Depth as a percentage of the
     * total depth.<br>
     *
     * @param value double
     */
    public void setZPosition(double value)
    {
        zPosition = setDoubleProperty(zPosition, value);
    }

    /**
     * Obsolete.&nbsp;Please use Axis.<!-- -->Grid.<!-- -->Centered property
     *
     * @return boolean
     */
    public boolean getGridCentered()
    {
        return getGrid().centered;
    }

    /**
     * Obsolete.&nbsp;Please use Axis.<!-- -->Grid.<!-- -->Centered property
     *
     * @param value boolean
     */
    public void setGridCentered(boolean value)
    {
        getGrid().setCentered(value);
    }

    /**
     * Determines the Axis position as percentage 0-100% of the Chart.<br>
     * 0 being Top for a horizontal Axis and Left for a vertical Axis.
     *
     * @return double
     */
    public double getRelativePosition()
    {
        return relativePosition;
    }

    /**
     * Determines the Axis position as percentage 0-100% of the Chart.<br>
     *
     * @param value double
     */
    public void setRelativePosition(double value)
    {
        relativePosition = setDoubleProperty(relativePosition, value);
    }

    /**
     * Defines axis Position units (pixels or percentage).<br>
     * When PositionUnits is Percent, Position value is a percentage of total
     * chart size. <br>
     * When PositionUnits is Pixels, Position is considered in pixels. <br>
     * Default value: Percent
     *
     * @return PositionUnits
     */
    public PositionUnits getPositionUnits()
    {
        return positionUnits;
    }

    /**
     * Determines the axis Position units (pixels or percentage).<br>
     * Default value: Percent
     *
     * @param value PositionUnits
     */
    public void setPositionUnits(PositionUnits value)
    {
        if (positionUnits != value)
        {
            positionUnits = value;
            invalidate();
        }
    }

    /**
     * Axis Start position on its own Axis expressed as a percentage.<br>
     * For a vertical Axis a StartPosition of 75 would place the top of
     * the Axis 75% down the Chart.<br>
     * Default value: 0%
     *
     * @return double
     */
    public double getStartPosition()
    {
        return startPosition;
    }

    /**
     * Sets the Axis Start position on its own Axis expressed as a percentage.
     * <br>
     * Default value: 0%
     *
     * @param value double
     */
    public void setStartPosition(double value)
    {
        startPosition = setDoubleProperty(startPosition, value);
    }

    /**
     * Axis End Position on its own Axis expressed as a percentage (0-100%).<br>
     * For a Vertical Axis a value of 75% would place the beginning of the
     * scale at 75% down from Top.<br>
     * Default value: 100%
     *
     * @return double
     */
    public double getEndPosition()
    {
        return endPosition;
    }

    /**
     * Sets the Axis End Position on its own Axis expressed as a percentage.<br>
     * Default value: 100%
     *
     * @param value double
     */
    public void setEndPosition(double value)
    {
        endPosition = setDoubleProperty(endPosition, value);
    }

    /**
     * The base for the Logarithmic scale when Axis Logarithmic = true.
     *
     * @return double
     */
    public double getLogarithmicBase()
    {
        return logarithmicBase;
    }

    /**
     * Sets the base for the Logarithmic scale when Axis Logarithmic = true.
     *
     * @param value double
     */
    public void setLogarithmicBase(double value)
    {
        logarithmicBase = setDoubleProperty(logarithmicBase, value);
    }

    /**
     * Characteristics of the Grid coincidental to Minor Ticks.<br>
     * The Minor Grid.Visible is false as default.
     *
     * @return ChartPen
     */
    public ChartPen getMinorGrid()
    {
        if (minorGrid == null)
        {
            minorGrid = new ChartPen(chart, Color.BLACK, false);
            minorGrid.setDefaultStyle(DashStyle.DOT);
        }
        return minorGrid;
    }

    //methods
    /**
     * Returns the Title or the Name of the Axis depending on whether you've<br>
     * set a title or not.
     *
     * @return String
     */
    public String titleOrName()
    {
        String tmpResult = getTitle().getCaption();
        if (tmpResult.length() == 0)
        {
            if (isDepthAxis)
            {
                if (this == this.getChart().getAxes().getDepth())
                {
                    return Language.getString("DepthAxis");
                }
                else if (this == this.getChart().getAxes().getDepthTop())
                {
                    return Language.getString("DepthTopAxis");
                }
            }
            else
            {
                if (horizontal)
                {
                    if (otherSide)
                    {
                        return Language.getString("TopAxis");
                    }
                    else
                    {
                        return Language.getString("BottomAxis");
                    }
                }
                else
                {
                    if (otherSide)
                    {
                        return Language.getString("RightAxis");
                    }
                    else
                    {
                        return Language.getString("LeftAxis");
                    }
                }
            }
        }
        return tmpResult;
    }

    /**
     * Returns the minimum and maximum values of the associated Series.
     *
     * @param tmp FloatRange
     */
    public void calcMinMax(FloatRange tmp)
    {

        if (automatic || automaticMaximum)
        {
            tmp.max = chart.internalMinMax(this, false, horizontal);
        }
        else
        {
            tmp.max = maximumValue;
        }

        if (automatic || automaticMinimum)
        {
            tmp.min = chart.internalMinMax(this, true, horizontal);
        }
        else
        {
            tmp.min = minimumValue;
        }
    }

    private int maxLabelsHeight() {
        /*if (getLabels().labelPos.Count > 0) {
            int tmpResult = (Labels.labelPos[0] is Rectangle) ? ((System.Drawing.Rectangle)(Labels.labelPos[0]
            )).Height:
            chart.getGraphics3D().PolygonBounds(((Point[]) (Labels.labelPos[0]))).Height;

            for (int i = 0; i < getLabels().labelPos.Count; i++) {
                int newHeight = (getLabels().labelPos[i] is Rectangle) ? ((System.Drawing.Rectangle)(getLabels().labelPos[i]
                )).Height:
                chart.getGraphics3D().PolygonBounds(((Point[]) (getLabels().labelPos[i]))).Height;

                if (newHeight > tmpResult) {
                    tmpResult = newHeight;
                }
            }
            return tmpResult;
        }*/

        return 0;
    }
            
    private int maxLabelsValueWidth() {
        double tmpA;
        double tmpB;
        double tmp = getCalcIncrement();
        int tmpResult;

        if ((isDateTime() && labels.getExactDateTime())
                || labels.getRoundFirstLabel()) {
            tmpA = tmp * (int) ((iMinimum / tmp));
            tmpB = tmp * (int) ((iMaximum / tmp)); //MM error on 'small'(20x20) chart with int32
        } else {
            tmpA = iMinimum;
            tmpB = iMaximum;
        }

        if (tmp != 0) {
            // Calculate maximum label width, considering all labels:
            tmpResult = 0;
            int limit = 5000;

            do {  // TV52012721  modified 23_7_08 MM see bug report
                tmpResult = Math.max(tmpResult, chart.multiLineTextWidth(labels.labelValue(tmpB)).width);

                tmpB = tmpB - tmp;
                limit--;
            }
            while ((tmpB >= tmpA) && (limit != 0));
        }
        else {
            double tmpC = tmp * (int) ((iMaximum - tmp) / tmp);  //ME TF02011221
            tmpResult = chart.getGraphics3D().textWidth(" ")
                    + Math.max(Math.max(chart.multiLineTextWidth(labels.labelValue(tmpA)).width,
                    chart.multiLineTextWidth(labels.labelValue(tmpB)).width),
                    chart.multiLineTextWidth(labels.labelValue(tmpC)).width);
        }

        return tmpResult;
    }

    /**
     * Returns the maximum width in pixels of all Axis Labels.
     *
     * @return int
     */
    public int maxLabelsWidth() {
        if (getLabels().getItems().size() == 0) {
            AxisLabelStyle tmpStyle = calcLabelStyle();
            if (tmpStyle == AxisLabelStyle.VALUE) {
                return maxLabelsValueWidth();
            } else if (tmpStyle == AxisLabelStyle.MARK) {
                return chart.maxMarkWidth();
            } else if (tmpStyle == AxisLabelStyle.TEXT) {
                return chart.maxTextWidth();
            } else {
                return 0;
            }

        } else {
            int result = 0;
            for (int t = 0; t < getLabels().getItems().size(); t++) {
                chart.getGraphics3D().setFont(getLabels().getItems().getItem(t).
                        getFont());
                result = Math.max(result,
                        chart.multiLineTextWidth(getLabels().
                                getItems().getItem(t).getText()).width);
            }
            return result;
        }
    }

    private int internalCalcParallelSize(ChartFont tmpFont, int tmpAngle, String tmpText, int tmpSize) {
        if (tmpSize != 0) {
            return tmpSize;
        } else {
            IGraphics3D g = chart.getGraphics3D();
            if (horizontal) {
                switch (tmpAngle) {
                    case 0:
                    case 180:
                        if (tmpText.length() == 0) {
                            g.setFont(tmpFont);
                            return maxLabelsWidth();
                        } else {
                            return g.textWidth(tmpFont, tmpText);
                        }
                    default: // optimized for speed
                        if (tmpText.length() == 0) {
                            g.setFont(tmpFont);
                            return maxLabelsHeight();
                        } else {
                            return g.textHeight(tmpFont, tmpText);
                        }
                }
            } else {
                switch (tmpAngle) {
                    case 90:
                    case 270:
                        if (tmpText.length() == 0) {
                            g.setFont(tmpFont);
                            return maxLabelsWidth();
                        } else {
                            return g.textWidth(tmpFont, tmpText);
                        }
                    default: // optimized for speed 
                        if (tmpText.length() == 0) {
                            g.setFont(tmpFont);
                            return maxLabelsHeight();
                        } else {
                            return g.textHeight(tmpFont, tmpText);
                        }
                }
            }
        }
    }

    private int internalCalcPerpendicularSize(ChartFont tmpFont, int tmpAngle, String tmpText, int tmpSize) {
        if (tmpSize != 0) {
            return tmpSize;
        } else {
            IGraphics3D g = chart.getGraphics3D();
            if (horizontal) {
                switch (tmpAngle) {
                    case 0:
                    case 180:
                        return Math.max(chart.maxTextHeight(this), g.fontTextHeight(tmpFont));
                    default: // optimized for speed
                        if (tmpText.length() == 0) {
                            g.setFont(tmpFont);
                            return maxLabelsWidth();
                        } else {
                            return g.textWidth(tmpFont, tmpText);
                        }
                }
            } else {
                switch (tmpAngle) {
                    case 90:
                    case 270:
                        return g.fontTextHeight(tmpFont);
                    default: // optimized for speed 
                        if (tmpText.length() == 0) {
                            g.setFont(tmpFont);
                            return maxLabelsWidth();
                        } else {
                        	if ((tmpAngle>0) && (tmpAngle<=90))
                              return (int)(Math.toRadians(90-tmpAngle) * g.textWidth(tmpFont, tmpText));
                        	else if ((tmpAngle>90) && (tmpAngle<=180))
                        	  return (int)g.textWidth(tmpFont, tmpText);	
                        	else if ((tmpAngle>180) && (tmpAngle<=270))
                          	  return (int)(Math.toRadians(270-tmpAngle) * g.textWidth(tmpFont, tmpText));
                        	else
                        	  return (int)(Math.toRadians(360-tmpAngle) * g.textWidth(tmpFont, tmpText));
                        }
                }
            }
        }
    }
    
    /**
     * Returns size (height perpendicular to its Axis) occupied by a Label.
     * 
     * @return int
     */
    public int getSizeLabels()
    {
        return getSizeLabels(true);
    }
    
    public int getSizeLabels(boolean perpendicular) {
        int result;
        result = perpendicular ? internalCalcPerpendicularSize(labels.getFont(), labels.getAngle(), "", labels.getCustomSize())
                : internalCalcParallelSize(labels.getFont(), labels.getAngle(), "", labels.getCustomSize());
        if (labels.getAlternate()) {
            result = result * 2;
        }
        return result;
    }

    /*
     * Internal placeholder.
     */
    public void checkNextLabelValue(Axis axis, AxisLabelValueEventArgs e){}

    /*
     * Internal use
     */
    public int internalCalcDepthPosValue(double value)
    {
        if (iRangezero)
        {
            return iCenterPos;
        }
        else
        {
            return (int) (iAxisSizeRange
                    * (inverted ? iMaximum - value
                    : value - iMinimum));
        }
    }

    /*
     * Internal use
     */
    public int internalCalcLogPosValue(boolean isx, double value)
    {
        if (iRangelog == 0)
        {
            return iCenterPos;
        }
        else if (value <= 0)
        {
            return ((isx && inverted) || (!isx && !inverted))
                    ? iEndPos : iStartPos;
        }
        else
        {
            int tmpResult = Utils.round(inverted
                    ? ((iLogMax - Utils.log(value))
                    * iAxisLogSizeRange)
                    : (Utils.log(value) - iLogMin)
                    * iAxisLogSizeRange);

            return isx ? iStartPos + tmpResult
                    : iEndPos - tmpResult;
        }
    }

    /**
     * Returns the corresponding value of a Screen position in pixels.
     *
     * @param value Screen value
     * @return Position on screen in pixels
     */
    public int calcPosValue(final double value)
    {
        return horizontal ? calcXPosValue(value) : calcYPosValue(value);
    }

    /**
     * Calculates the Horizontal coordinate in pixels of Value parameter.<br>
     * You can use CalcXPosValue when requiring pixel positions from which
     * to plot Drawing output.
     *
     * @param value double
     * @return int
     */
    public int calcXPosValue(final double value)
    {
        return getAxisCalcResolver().AxisCalcXPosValueDelegate(value);
    }
    
    /*
     * Internal calc for XPos
     */
    private int xPosValueCheck(double value)
    {
        double tmp = (value - iMinimum) * iAxisSizeRange;
        tmp = inverted ? iEndPos - tmp : iStartPos + tmp;
        if (tmp > MAXPIXELPOS)
        {
            tmp = MAXPIXELPOS;
        }
        else if (tmp < -MAXPIXELPOS)
        {
            tmp = -MAXPIXELPOS;
        }
        return (int) tmp;
    }
           
    /*
     * Internal calc for YPos
     */    
    private int yPosValueCheck(double value)
    {
        if (iRangezero) return iCenterPos;
        else
        {
         double tmp = (value - iMinimum) * iAxisSizeRange;
     
            tmp = inverted ? iStartPos + tmp : iEndPos - tmp;
            if (tmp > MAXPIXELPOS)
            {
                tmp = MAXPIXELPOS;
            }
            else if (tmp < -MAXPIXELPOS)
            {
                tmp = -MAXPIXELPOS;
            }
            return (int) tmp;
        }
    }
     
    /**
     * Calculates the Vertical coordinate in pixels of Value parameter.
     *
     * @param value Value Parameter
     * @return int Vertical coordinate in pixels
     */
    public int calcYPosValue(final double value)
    {
       return getAxisCalcResolver().AxisCalcYPosValueDelegate(value);
    }

    /**
     * Calculates the Vertical coordinate in pixels of Value parameter
     *
     * @return int
     */
    public int calcZPos()
    {
        int result = isDepthAxis ? chart.getHeight()
                : chart.getAspect().getWidth3D();
        return (int) Utils.round(result * zPosition * 0.01); // 6.0
    }

    /**
     * Returns the amount in pixels that corresponds to a portion of the axis
     * of size "Value" in axis scales.
     *
     * @param value double
     * @return int Potrtion of axis in pixels
     */
    public int calcSizeValue(final double value)
    {
        if (value > 0)
        {
            if (logarithmic)
            {
                return (iRangelog != 0)
                        ? Utils.round(Utils.log(value)
                        * iAxisLogSizeRange) : 0;
            }
            else
            {
                return (iRange != 0)
                        ? Utils.round(value * iAxisSizeRange) : 0;
            }
        }
        else
        {
            return 0;
        }
    }

    private double internalCalcPos(double a, double b)
    {
        return (((horizontal) && (inverted))
                || ((!horizontal) && (!inverted))) ? a : b;
    }
    
    public double calcPosPoint(int value)
    {
        return this.getAxisCalcResolver().AxisCalcPosPointDelegate(value);
    }

    /**
     * Returns the corresponding value of a Screen position.
     *
     * @param value int Screen value
     * @return double Position on screen in pixels
     */
    public double internalCalcPosPoint(int value)
    {
        double tmp;

        if (logarithmic)
        {
            if (value == iStartPos)
            {
                return internalCalcPos(iMaximum, iMinimum);
            }
            else if (value == iEndPos)
            {
                return internalCalcPos(iMinimum, iMaximum);
            }
            else
            {
                tmp = iRangelog;
                if (tmp == 0)
                {
                    return iMinimum;
                }
                else
                {
                    if (inverted)
                    {
                        tmp = ((iEndPos - value) * tmp / iAxisSize);
                    }
                    else
                    {
                        tmp = ((value - iStartPos) * tmp
                                / iAxisSize);
                    }

                    return Utils.exp(horizontal ? iLogMin + tmp
                            : iLogMax - tmp);
                }
            }
        }
        else if (iAxisSize > 0)
        {

            tmp = inverted ? iEndPos - value : value - iStartPos;
            tmp *= iRange / iAxisSize;

            return horizontal ? iMinimum + tmp : iMaximum - tmp;
        }
        else
        {
            return 0;
        }
    }

    private Rectangle axisRect()
    {
        int tmpPos1;
        int pos1;
        int tmpPos2;
        int pos2;

        if (iStartPos > iEndPos)
        {
            tmpPos1 = iEndPos;
            tmpPos2 = iStartPos;
        }
        else
        {
            tmpPos1 = iStartPos;
            tmpPos2 = iEndPos;
        }

        if (posAxis > labels.position)
        {
            pos1 = labels.position;
            pos2 = posAxis + AXISCLICKGAP;
        }
        else
        {
            pos1 = posAxis - AXISCLICKGAP;
            pos2 = labels.position;
        }

        if (horizontal)
        {
            return Rectangle.fromLTRB(tmpPos1, pos1, tmpPos2, pos2);
        }
        else
        {
            return Rectangle.fromLTRB(pos1, tmpPos1, pos2, tmpPos2);
        }
    }
    protected void calcAllSeries()
    {
      if (iSeriesList == null) iSeriesList = new SeriesCollection(chart);

      for(int i=0;i<chart.getSeriesCount();i++)
      {
          if (chart.getSeries(i).getActive() && chart.getSeries(i).associatedToAxis(this))
                iSeriesList.internalAdd(chart.getSeries(i));
      }


    }

    /**
     * Returns if X and Y coordinates are close to the Axis position.
     *
     * @param xy Point (X and Y coordinates)
     * @return boolean - X and Y coordinates as integer
     */
    public boolean clicked(Point xy)
    {
        return clicked(xy.x, xy.y);
    }

    /**
     * Returns if X and Y coordinates are close to the Axis position.
     *
     * @param x Pixel location
     * @param y Pixel location
     * @return boolean - true if X and Y coordinates are
     * on or near to Axis
     * @see Axes
     */
    public boolean clicked(int x, int y)
    {
        return chart.isAxisVisible(this) && axisRect().contains(x, y);
    }

    /**
     * Returns the calculated Maximum Horizontal value for the specified AAxis.
     * <br>
     * AAxis can be Axis.Top or Axis.Bottom. Calculated means that the return
     * value will be the Maximum value of the Maximum Series X Values.
     * Only Series with the HorizontalAxis equal to AAxis will be considered.
     *
     * @return double
     * @see Axes
     * @see Axis#getMinXValue
     * @see Axis#getMinYValue
     * @see Axis#getMaxYValue
     */
    public double getMaxXValue()
    {
        return chart.getMaxXValue(this);
    }

    /**
     * Returns the calculated Maximum Vertical value for the specified AAxis.
     * <br>
     * AAxis can be Axis.Left or Axis.Right. Calculated means that the return
     * value will be the Maximum value of the Maximum Series Y Values. Only
     * Series with the VerticalAxis equal to AAxis will be considered.
     *
     * @return double
     * @see Axes
     * @see Axis#getMinXValue
     * @see Axis#getMaxXValue
     * @see Axis#getMinYValue
     */
    public double getMaxYValue()
    {
        return chart.getMaxYValue(this);
    }

    /**
     * Returns the calculated Minimum Horizontal value for the specified AAxis.
     * <br>
     * AAxis can be Axis.Top or Axis.Bottom. Calculated means that the return
     * value will the Minimum value of the Minimum Series X Values. Only
     * Series with the HorizontalAxis equal to AAxis will be considered.
     *
     * @return double
     * @see Axes
     * @see Axis#getMaxXValue
     * @see Axis#getMinYValue
     * @see Axis#getMaxYValue
     */
    public double getMinXValue()
    {
        return chart.getMinXValue(this);
    }

    /**
     * Returns the calculted Minimum Vertical value for the specified AAxis.
     * <br>
     * AAxis can be Axis.Left or Axis.Right. Calculated means that the return
     * value will the Minimum value of the Minimum Series Y Values. Only
     * Series with the VerticalAxis equal to AAxis will be considered.
     *
     * @return double
     * @see Axes
     * @see Axis#getMinXValue
     * @see Axis#getMaxXValue
     * @see Axis#getMaxYValue
     */
    public double getMinYValue()
    {
        return chart.getMinYValue(this);
    }

    /**
     * Returns either a Time or Date format depending if the astep parameter
     * is lower than one day (time) or greater (date).
     *
     * @param astep double
     * @return DateFormat
     */
    public DateFormat dateTimeDefaultFormat(double astep)
    {
        return (astep <= 1)
                ? DateFormat.getTimeInstance(DateFormat.SHORT)
                : DateFormat.getDateInstance(DateFormat.SHORT);
    }

    private double nextStep(double oldStep)
    {
        if (oldStep >= 10)
        {
            return 10 * nextStep(0.1 * oldStep);
        }
        else if (oldStep < 1)
        {
            return 0.1 * nextStep(oldStep * 10);
        }
        else if (oldStep < 2)
        {
            return 2;
        }
        else if (oldStep < 5)
        {
            return 5;
        }
        else
        {
            return 10;
        }
    }

    private AxisLabelStyle internalCalcLabelStyle()
    {
        AxisLabelStyle tmpResult = AxisLabelStyle.NONE;

        if (isDepthAxis)
        {
            tmpResult = AxisLabelStyle.TEXT;
            for (int t = 0; t < chart.getSeriesCount(); t++)
            {
                ISeries s = chart.getSeries(t);
                if (s.getActive())
                {
                    if (s.getHasZValues()
                            || (s.getMinZValue() != s.getMaxZValue()))
                    {
                        return AxisLabelStyle.VALUE;
                    }
                }
            }
        }
        else
        {
            for (int t = 0; t < chart.getSeriesCount(); t++)
            {
                ISeries tmpSeries = chart.getSeries(t);

                if ((tmpSeries.getActive())
                        && (tmpSeries.associatedToAxis(this)))
                {

                    tmpResult = AxisLabelStyle.VALUE;
                    if(!tmpSeries.getHasZValues()) {
                        if (((horizontal) && (tmpSeries.getYMandatory()))
                            || ((!horizontal) && (!tmpSeries.getYMandatory())))
                        {
                            if ((tmpSeries.getLabels() != null)
                                && (tmpSeries.getLabels().getString(0).length() != 0))
                            {
                                return AxisLabelStyle.TEXT;
                            }
                        }
                    }
                }
            }
        }
        return tmpResult;
    }

    private double internalCalcLabelsIncrement(int maxNumLabels)
    {
        double tmpResult;

        if (desiredIncrement <= 0)
        {
            if (iRange == 0)
            {
                tmpResult = 1;
            }
            else
            {
                tmpResult = Math.abs(iRange) / (maxNumLabels + 1);
                if (anySeriesHasLessThan(maxNumLabels))
                {
                    tmpResult = Math.max(1, tmpResult);
                }
            }
        }
        else
        {
            tmpResult = desiredIncrement;
        }

        int tempNumLabels = maxNumLabels + 1;

        boolean inf = false;

        if (Double.isInfinite(iRange))
        {
            return 1;
        }

        if (labels.iSeparation >= 0)
        {
            double tmp;

            do
            {
                tmp = iRange / tmpResult;
                if (Math.abs(tmp) < Integer.MAX_VALUE)
                {
                    tempNumLabels = (int) (Utils.round(tmp));
                    if (tempNumLabels > maxNumLabels)
                    {
                        tmpResult = nextStep(tmpResult);
                    }
                }
                else
                {
                    tmpResult = nextStep(tmpResult);
                }

                inf = Double.isInfinite(tmpResult);

            }
            while ((tempNumLabels > maxNumLabels)
                    && (tmpResult <= iRange) && (!inf));
        }

        return inf ? iRange : Math.max(tmpResult, iMinAxisIncrement);
    }

	static private double nextDateTimeStep(double aStep) {
		for (int t = DateTimeStep.ONEYEAR - 1; t >= DateTimeStep.ONEMICROSECOND; t--) {
			if (aStep >= (DateTimeStep.STEP[t])) {
				return DateTimeStep.STEP[t + 1];
			}
		}
		return DateTimeStep.STEP[DateTimeStep.ONEYEAR];
	}

	private double teeNextStep(double oldStep) {
		double result;

		if (Double.isInfinite(oldStep)) {
			result = 1;
		} else if (oldStep >= 10) {
			result = 10 * teeNextStep(0.1 * oldStep);
		} else if (oldStep < 1) {
			result = 0.1 * teeNextStep(oldStep * 10);
		} else if (oldStep < 2) {
			result = 2;
		} else if (oldStep < 5) {
			result = 5;
		} else {
			result = 10;
		}

		return result;
	}
    
	private double calcDateTimeIncrement(int maxNumLabels) {
		double tmpResult = Math.max(desiredIncrement, DateTimeStep.STEP[DateTimeStep.ONEMICROSECOND]);

		if ((tmpResult > 0) && (maxNumLabels > 0)) {
			if ((iRange / tmpResult) > 1000000) {
				tmpResult = iRange / 1000000;
			}

			int tempNumLabels;
			do {
				tempNumLabels = Utils.round(iRange / tmpResult);
				if (tempNumLabels > maxNumLabels) {
					if (tmpResult < DateTimeStep.STEP[DateTimeStep.ONEYEAR]) {
						if (tmpResult < DateTimeStep.STEP[DateTimeStep.ONESECOND]) {
							tmpResult = teeNextStep(tmpResult);
						} else {
							tmpResult = nextDateTimeStep(tmpResult);
						}
					} else {
						tmpResult *= 2;
					}
				}
			} while (tempNumLabels > maxNumLabels);
		}

		return Math.max(tmpResult, DateTimeStep.STEP[DateTimeStep.ONEMICROSECOND]);
	}

    private boolean anySeriesHasLessThan(int num)
    {
        boolean result = false;
        for (int t = 0; t < chart.getSeriesCount(); t++)
        {
            ISeries s = chart.getSeries(t);
            if (s.getActive())
            {
                if ((s.getYMandatory() && getHorizontal())
                        || ((!s.getYMandatory()) && (!getHorizontal())))
                {
                    if (s.associatedToAxis(this))
                    {
                        result = s.getCount() <= num;
                        if (result)
                        {
                            break;
                        }
                    }
                }
            }
        }
        return result;
    }

    private double calcLabelsIncrement(int maxNumLabels)
    {
        double tmpResult;

        if (maxNumLabels > 0)
        {
            if (iAxisDateTime)
            {
                tmpResult = calcDateTimeIncrement(maxNumLabels);
            }
            else
            {
                tmpResult = internalCalcLabelsIncrement(maxNumLabels);
            }
        }
        else
        {
            if (iAxisDateTime)
            {
                tmpResult = DateTimeStep.STEP[DateTimeStep.ONEMICROSECOND];
            }
            else
            {
                tmpResult = iMinAxisIncrement;
            }
        }
        return tmpResult;
    }

    /**
     * Returns the calculated Axis Label increment.
     *
     * @param maxLabelSize int The maximum allowable LabelSize in pixels.
     * @return double Size of increment in Axis units.
     */
    public double calcXYIncrement(int maxLabelSize)
    {
        int tmp;
        if (maxLabelSize > 0)
        {
            if (labels.iSeparation > 0)
            {
                maxLabelSize = (int) (maxLabelSize
                        + Utils.round(0.01
                        * labels.iSeparation
                        * maxLabelSize));
            }
            tmp = (int) (Utils.round((1.0 * iAxisSize)
                    / maxLabelSize));
        }
        else
        {
            tmp = 1;
        }
        return calcLabelsIncrement(tmp);
    }
    
    int internalLabelSize(double value, boolean isWidth) {
    int result;
    boolean tmpMulti = false;

        MultiLine multi = chart.multiLineTextWidth(labels.labelValue(value));
        
        result = multi.width;

        if (isWidth)
           tmpMulti = ((labels.getAngle()==90) || (labels.getAngle()==270));
        else
           tmpMulti = ((labels.getAngle()==0) || (labels.getAngle()==180));

        if (tmpMulti) 
          return chart.getGraphics3D().getFontHeight()*multi.count;
        else
          return result; 
    }

    /**
     * Returns the calculated Axis Label increment and serves as a useable
     * measure when Labelling is set to manual or automatic increments.<br>
     * Please note that the related Increment property only returns the
     * increment value when those increments are manually set via the same
     * property.
     *
     * @return double calculated Axis Label increment.
     */
	public double getCalcIncrement() {
		int tmp;
		if (horizontal)
			tmp = Math.max(labels.labelWidth(iMinimum), labels.labelWidth(iMaximum));
		else
			tmp = Math.max(labels.labelHeight(iMinimum), labels.labelHeight(iMaximum));

		if (getIncrement() == 0)
			if (horizontal)
				tmp = Math.max(tmp, labels.labelWidth((iMaximum + iMinimum) * 0.5));
			else
				tmp = Math.max(tmp, labels.labelHeight((iMaximum + iMinimum) * 0.5));
		else if (horizontal)
			tmp = Math.max(Math.max(tmp, labels.labelWidth(iMinimum + getIncrement())),
					labels.labelWidth(iMaximum - getIncrement()));
		else
			tmp = Math.max(Math.max(tmp, labels.labelHeight(iMinimum + getIncrement())),
					labels.labelHeight(iMaximum - getIncrement()));

		double result = calcXYIncrement(tmp);

		if (labels.getAlternate()) {
			if (getLogarithmic()) {
				result = result / logarithmicBase;
			} else {
				result = result / 2;
			}
		}

		return result;
	}

    /**
     * Returns the greater of lengths of Ticks or MinorTicks
     *
     * @return int
     */
    public int getSizeTickAxis()
    {
        int tmpResult = 0;
        if (getVisible())
        {
            tmpResult = axispen.getWidth() + 1;
        }
        //else { tmpResult=0; }
        if (getTicks().getVisible())
        {
            tmpResult += ticks.length;
        }
        if (getMinorTicks().getVisible())
        {
            tmpResult = Math.max(tmpResult, minorTicks.length);
        }
        return tmpResult;
    }

    public int getRectangleEdge(Rectangle r)
    {
        if (otherSide)
        {
            return horizontal ? r.y : r.getRight();
        }
        else
        {
            return horizontal ? r.getBottom() : r.x;
        }
    }

    private Rectangle inflateAxisRect(int value, Rectangle r)
    {
        int tmpL = r.x;
        int tmpT = r.y;
        int tmpR = r.getRight();
        int tmpB = r.getBottom();
        if (horizontal)
        {
            if (otherSide)
            {
                tmpT += value;
            }
            else
            {
                tmpB -= value;
            }
        }
        else if (otherSide)
        {
            tmpR -= value;
        }
        else
        {
            tmpL += value;
        }

        r.x = tmpL;
        r.y = tmpT;
        r.width = tmpR - tmpL;
        r.height = tmpB - tmpT;
        return r;
    }

    public int inflateAxisPos(int value, int amount)
    {
        int tmpResult = value;
        if (horizontal)
        {
            if (otherSide)
            {
                tmpResult -= amount;
            }
            else
            {
                tmpResult += amount;
            }
        }
        else
        {
            if (otherSide)
            {
                tmpResult += amount;
            }
            else
            {
                tmpResult -= amount;
            }
        }
        return tmpResult;
    }

    void internalCalcLogRange() {
      if (logarithmic)
      {
        iLogMin = (iMinimum <= 0) ? 0 : Math.log(iMinimum);
        iLogMax = (iMaximum <= 0) ? 0 : Math.log(iMaximum);
        iRangelog = iLogMax - iLogMin;
        iAxisLogSizeRange = (iRangelog == 0) ? 0 : iAxisSize / iRangelog;
      }
    }

    private class CalcLabelsResults
    {

        int position;
        Rectangle rect;

        public CalcLabelsResults()
        {
            super();
        }
    }

    private CalcLabelsResults calcLabelsRect(int tmpSize, Rectangle r)
    {
        CalcLabelsResults result = new CalcLabelsResults();
        result.rect = inflateAxisRect(tmpSize, r);
        result.position = getRectangleEdge(result.rect);
        return result;
    }

    /**
     * Used internally by Chart when creating Axes
     *
     * @param r Chart Rectangle
     * @param inflateChartRectangle boolean Wall allowance (standard Axes only)
     * @return Rectangle
     */
    public Rectangle calcRect(Rectangle r, boolean inflateChartRectangle)
    {
        iAxisDateTime = isDateTime();

        if (inflateChartRectangle)
        {
            CalcLabelsResults tmpRes;

            if (isDepthAxis)
            {
                if (otherSide)
                {
                    posTitle = r.getRight();
                }
                else
                {
                    posTitle = r.getLeft();
                }
            }
            else
            {
                if ((axisTitle != null) && axisTitle.getVisible()
                        && (axisTitle.getCaption().length() != 0))
                {

                    tmpRes = calcLabelsRect(internalCalcPerpendicularSize(
                            axisTitle.getFont(), axisTitle.getAngle(),
                            axisTitle.getCaption(), axisTitle.getCustomSize()),
                            r);

                    posTitle = tmpRes.position;
                }
            }

            if (labels.getVisible())
            {
                tmpRes = calcLabelsRect(getSizeLabels(), r);
                labels.position = tmpRes.position;
            }

            int tmp = getSizeTickAxis() + chart.getWalls().calcWallSize(this);
            if (tmp > 0)
            {
                r = inflateAxisRect(tmp, r);
            }
            posTitle = applyPosition(posTitle, r);
            labels.position = applyPosition(labels.position, r);
        }
        else
        {
            posAxis = applyPosition(getRectangleEdge(r), r);
            labels.position = inflateAxisPos(posAxis, getSizeTickAxis());
            posTitle = inflateAxisPos(labels.position, getSizeLabels());
        }

        return r;
    }

    public int applyPosition(int apos, Rectangle r)
    {
        int tmpsize;

        if (relativePosition != 0)
        {
            if (positionUnits == PositionUnits.PERCENT)
            {
                tmpsize = horizontal ? r.height : r.width;
                tmpsize = (int) Utils.round(0.01 * relativePosition
                        * tmpsize);
            }
            else
            {
                tmpsize = (int) Utils.round(relativePosition); // pixels
            }

            if (otherSide)
            {
                tmpsize = -tmpsize;
            }
            if (horizontal)
            {
                tmpsize = -tmpsize;
            }

            return apos + tmpsize;
        }
        else
        {
            return apos;
        }
    }


    /**
     * Used internally to return Axis Datetime step.
     *
     * @param stepValue double Desired increment
     * @return int
     */
    static public int findDateTimeStep(double stepValue)
    {
        for (int t = DateTimeStep.ONEYEAR;
                t >= DateTimeStep.ONEMICROSECOND; t--)
        {
            if ((Math.abs(DateTimeStep.STEP[t] - stepValue))
                    < (DateTimeStep.STEP[DateTimeStep.ONEMICROSECOND]))
            {
                return t;
            }
        }
        return DateTimeStep.NONE;
    }

    private String drawExponentLabel(int x, int y, int tmpZ, String tmpSt2)
    {
        IGraphics3D g = chart.getGraphics3D();
        int i = tmpSt2.toUpperCase().indexOf('E');
        if (i == -1)
        {
            g.textOut(x, y, tmpZ, tmpSt2);
        }
        else
        {
            //CDI TTrack //#1525
            String tmp = "";
            String tmpSub = "";
            if ((i - 1) < 0)
            {
                tmp = tmpSt2.substring(0, 1);
                tmpSub = tmpSt2.substring(1);
            }
            else
            {
                tmp = tmpSt2.substring(0, i - 1);
                tmpSub = tmpSt2.substring(i + 1);
            }

            //String tmp=tmpSt2.substring(0,i-1);
            //String tmpSub=tmpSt2.substring(i+1);

            int oldSize = g.getFont().getSize();
            int tmpH = oldSize - 1;
            float tmpW;
            if (g.getTextAlign() == StringAlignment.NEAR)
            {
                g.textOut(x, y, tmpZ, tmp);
                tmpW = g.textWidth(tmp) + 1;
                g.getFont().setSize(g.getFont().getSize()
                        - (int) (oldSize * 0.25));
                g.textOut((int) (x + tmpW),
                        (int) (y - (tmpH * 0.5)) + 2, tmpZ,
                        tmpSub);
                g.getFont().setSize(oldSize);
            }
            else
            {
                g.getFont().setSize(g.getFont().getSize()
                        - (int) (oldSize * 0.25));
                g.textOut(x, y - (int) (tmpH * 0.5) + 2, tmpZ,
                        tmpSub);
                tmpW = g.textWidth(tmpSub) + 1;
                g.getFont().setSize(oldSize);
                g.textOut((int) (x - tmpW), y, tmpZ, tmp);
            }
        }
        return tmpSt2;
    }

    static private int numTextLines(String st)
    {
        int i = st.indexOf(Language.lineSeparator);
        if (i == -1)
        {
            return 1;
        }
        else
        {
            int tmpResult = 0;
            while (i != -1)
            {
                tmpResult++;
                st = st.substring(i + 1);
                i = st.indexOf(Language.lineSeparator);
            }
            if (st.length() != 0)
            {
                tmpResult++;
            }
            return tmpResult;
        }
    }

    /**
     * Draws Axis Label (String) at specified X,Y co-ordinate at Angle.
     *
     * @param x int x coordinate
     * @param y int y coordinate
     * @param angle int Angle at which Label is drawn
     * @param st String asociated to Label
     * @param labelItem TextShape
     */
    public void drawAxisLabel(int x, int y, int angle, String st,
            TextShape labelItem)
    {
        drawAxisLabel(labels.getFont(), x, y, angle, st, labelItem);
    }

    /**
     * Draws Axis Label (String) in font f at specified X,Y co-ordinate at
     * Angle.
     *
     * @param f ChartFont
     * @param x int x coordinate
     * @param y int y coordinate
     * @param angle int Angle at which Label is drawn
     * @param st String asociated to Label
     * @param format TextShape
     */
    public void drawAxisLabel(ChartFont f, int x, int y, int angle,
            String st, TextShape format) {
        drawAxisLabel(f, x, y, angle, st, format, false);
    }
            
    /**
     * Draws Axis Label (String) in font f at specified X,Y co-ordinate at
     * Angle.
     *
     * @param f ChartFont
     * @param x int x coordinate
     * @param y int y coordinate
     * @param angle int Angle at which Label is drawn
     * @param st String asociated to Label
     * @param format TextShape
     * @param isTitle boolean
     */
    public void drawAxisLabel(ChartFont f, int x, int y, int angle,
            String st, TextShape format, boolean isTitle)
    {
        //StringAlignment[][] aligns =
        //        new StringAlignment[][] { {StringAlignment.FAR,
        //        StringAlignment.NEAR}, {StringAlignment.CENTER,
        //        StringAlignment.CENTER}
        //};

        boolean is3D = chart.getAspect().getView3D();
        
        StringAlignment tmpAlign, tmpAlign2;

        IGraphics3D g = chart.getGraphics3D();

        StringAlignment oldStrAlign = g.getTextAlign();

        if (format == null)
        {
            format = getLabels();
        }
        else
        {
            f = format.getFont();
        }

        g.setFont(f);

        int tmpFontH = g.getFontHeight();

        int tmpZ = iZPos; // old--> otherSide ? chart.getAspect().width3D : 0;

        int n = numTextLines(st);
        int delta = tmpFontH;

        if (horizontal && otherSide)
        {
            if ((isTitle) && (is3D) && (!chart.getAspect().getOrthogonal())) { //MM see TF02011030 
                tmpZ = maxLabelsWidth() + chart.getAspect().getWidth3D();
            }
            
            if ((angle > 90) && (angle <= 180))
            {
                y += delta; //1st line
                tmpAlign = StringAlignment.CENTER;
            }
            else if ((angle > 0) && (angle <= 90))
            {
                x -= delta;
                x -= (delta * (n) / 2);
                if (getLabels().getAlign() == AxisLabelAlign.DEFAULT)
                {
                    tmpAlign = StringAlignment.NEAR;
                }
                else
                {
                    y -= maxLabelsWidth();
                    tmpAlign = StringAlignment.FAR;
                }
            }
            else if ((angle > 180) && (angle <= 270))
            {
                x = x + delta + (delta * (n) / 2);
                if (getLabels().getAlign() == AxisLabelAlign.DEFAULT)
                {
                    tmpAlign = StringAlignment.FAR;
                }
                else
                {
                    y -= maxLabelsWidth();
                    tmpAlign = StringAlignment.NEAR;
                }
            }
            else
            {
                y -= delta * (n + 1);
                tmpAlign = StringAlignment.CENTER;
            }
        }
        else if (horizontal)
        {
            if ((isTitle) && (is3D) && (!chart.getAspect().getOrthogonal()))
                tmpZ = -maxLabelsWidth();
            
            if ((angle > 90) && (angle <= 180))
            {
                y += delta; //1st line
                y += (delta * (n));
                tmpAlign = StringAlignment.CENTER;
            }
            else if ((angle > 0) && (angle <= 90))
            {
                x -= delta;
                x -= (delta * (n) / 2);
                if (getLabels().getAlign() == AxisLabelAlign.DEFAULT)
                {
                    tmpAlign = StringAlignment.FAR;
                }
                else
                {
                    y += maxLabelsWidth();
                    tmpAlign = StringAlignment.NEAR;
                }
            }
            else if ((angle > 180) && (angle <= 270))
            {
                x += delta + (delta * (n) / 2);
                if (getLabels().getAlign() == AxisLabelAlign.DEFAULT)
                {
                    tmpAlign = StringAlignment.NEAR;
                }
                else
                {
                    y += maxLabelsWidth();
                    tmpAlign = StringAlignment.FAR;
                }
            }
            else
            {
                y -= delta;
                tmpAlign = StringAlignment.CENTER;
            }
        }
        else if (otherSide)
        {
            if ((isTitle) && (is3D) && (!chart.getAspect().getOrthogonal()))
                tmpZ = chart.getAspect().getWidth3D() + maxLabelsWidth();
            
            if (((angle > 270) && (angle <= 360)) || (angle == 0))
            {
                y -= delta; //1st line
                y -= (delta * (n)) / 2;
                if (getLabels().getAlign() == AxisLabelAlign.DEFAULT)
                {
                    tmpAlign = StringAlignment.NEAR;
                }
                else
                {
                    x += maxLabelsWidth();
                    tmpAlign = StringAlignment.FAR;
                }
            }
            else if ((angle > 90) && (angle <= 180))
            {
                y += delta; //1st line
                y += (delta * (n)) / 2;
                if (getLabels().getAlign() == AxisLabelAlign.DEFAULT)
                {
                    tmpAlign = StringAlignment.FAR;
                }
                else
                {
                    x += maxLabelsWidth();
                    tmpAlign = StringAlignment.NEAR;
                }
            }
            else if ((angle > 0) && (angle <= 90))
            {
                x -= delta;
                tmpAlign = StringAlignment.CENTER;
            }
            else
            {
                x += delta * (n + 1);
                tmpAlign = StringAlignment.CENTER;
            }
        }
        else
        {
            if ((isTitle) && (is3D) && (!chart.getAspect().getOrthogonal()))
                tmpZ = -maxLabelsWidth();
            
            if (((angle > 270) && (angle <= 360)) || (angle == 0))
            {
                y -= delta; //1st line
                y -= (delta * (n)) / 2;
                if (getLabels().getAlign() == AxisLabelAlign.DEFAULT)
                {
                    tmpAlign = StringAlignment.FAR;
                }
                else if (getLabels().getAlign() == AxisLabelAlign.CENTERED)
                {
                    x -= (maxLabelsWidth() / 2);
                    tmpAlign = StringAlignment.CENTER;
                }
                else
                {
                    x -= maxLabelsWidth();
                    tmpAlign = StringAlignment.NEAR;
                    
                }
            }
            else if ((angle > 90) && (angle <= 180))
            {
                y += delta; //1st line
                y += (delta * (n)) / 2;
                if (getLabels().getAlign() == AxisLabelAlign.DEFAULT)
                {
                    tmpAlign = StringAlignment.NEAR;
                }
                else if (getLabels().getAlign() == AxisLabelAlign.CENTERED)
                {
                    x -= (maxLabelsWidth() / 2);
                    tmpAlign = StringAlignment.CENTER;
                }                
                else
                {
                    x -= maxLabelsWidth();
                    tmpAlign = StringAlignment.FAR;
                }
            }
            else if ((angle > 0) && (angle <= 90))
            {
                x -= delta * (n + 1);
                tmpAlign = StringAlignment.CENTER;
            }
            else
            {
                x += delta;
                tmpAlign = StringAlignment.CENTER;
            }
        }
        
        if (!format.getTransparent())
        {
            int tmpSize = g.textWidth("W") / 2;

            format.setLeft(x);
            format.setTop(y + delta);

            MultiLine tmp = chart.multiLineTextWidth(st);
            int tmpW = tmp.width;
            int tmpNum = tmp.count;

            tmpAlign2 = tmpAlign;

            format.setHeight(tmpFontH * tmpNum);
            format.setWidth(tmpW);

            if (tmpAlign2 == StringAlignment.FAR) {
                format.setLeft(format.getLeft() - tmpW);
            }
            else if (tmpAlign2 == StringAlignment.CENTER) {
                format.setLeft(format.getLeft() - (tmpW / 2));
            }

            tmpW += tmpSize;
            format.setLeft(format.getLeft() - (tmpSize / 2));
            format.setWidth(tmpW);

            if (tmpZ != 0)
            {
                format.setShapeBounds(g.calcRect3D(format.getShapeBounds(),
                        tmpZ));
            }

            format.drawRectRotated(g, format.getShapeBounds(),
                    getLabels().getAngle(), tmpZ);
        }

        int tmpFT = format.getTop();
        int tmpFH = format.getHeight();
      
        boolean tmpDraw = true;
        
        g.setTextAlign(tmpAlign);

        String tmpSt = st;
        String tmpSt2 = "";
        
        //tmpSt = chart.getParent().getAxisLabelResolver().getLabel(this, null, -1, tmpSt);

        if (tmpDraw) {
            n = numTextLines(tmpSt);

            for (int tt = 1; tt <= n; tt++) {
                if (angle > 0) {
                    format.setTop(tmpFT - tmpFH);

                    if ((angle >= 0) && (angle <= 90)) {
                        x += delta;
                        format.setLeft(format.getLeft() + delta);
                    } else if ((angle > 90) && (angle <= 180)) {
                        y -= delta;
                        format.setTop(format.getTop() - delta);
                    } else if ((angle > 180) && (angle <= 270)) {
                        x -= delta;
                        format.setLeft(format.getLeft() - delta);
                    } else if ((angle > 270) && (angle < 360)) {
                        y += delta;
                        format.setTop(format.getTop() + delta);
                    }
                }

                int i = tmpSt.indexOf(Language.lineSeparator);

                tmpSt2 = (i != -1) ? tmpSt.substring(0, i) : tmpSt;

                if (angle == 0) {
                    y += delta;
                    g.setFont(f);
                    if (labels.getExponent()) {
                        tmpSt2 = drawExponentLabel(x, y, tmpZ, tmpSt2);
                    } else if (is3D) {
                        g.textOut(x, y, tmpZ, tmpSt2);
                    } else {
                        g.textOut(x, y, tmpSt2);
                    }
                } else {
                    if ((angle >= 0) && (angle <= 90)) {
                        if (horizontal) {
                            x += delta;
                        } else {
                            if (otherSide) {
                                int h = this.getChart().getGraphics3D().textWidth(tmpSt2);
                                y -= (int) (Math.abs(h / 2 * Math.sin(Math.toRadians(angle))) + (delta / 2 * (90 - angle - 1) / 90));
                                x += (int) (h * Math.cos(Math.toRadians(angle))) + (delta / 2 * (angle + 1) / 90);
                            } else {
                            	//#2144
                                int h = this.getChart().getGraphics3D().textWidth(tmpSt2);
                                x -= (int) (h / 2 * Math.cos(Math.toRadians(angle)));
                            }
                        }
                    } else if ((angle > 90) && (angle <= 180)) {
                        if (horizontal) {
                            if (otherSide) {
                                int h = this.getChart().getGraphics3D().textWidth(tmpSt2);
                                x = x - (int) ((h / 2 * Math.sin(Math.toRadians(angle - 90))) + (delta / 2));
                                y = y - (int) ((h / 2 * Math.cos(Math.toRadians(angle - 90)))) - (delta); // - delta  - (delta * (((180.0 - (angle-1)) / 90))));

                            } else {
                                int h = this.getChart().getGraphics3D().textWidth(tmpSt2);
                                //x = x + (int) (h / 2 * Math.sin(Math.toRadians(angle - 90))); // - (delta / 2);
                                y = y + (int) ((h / 2 * Math.cos(Math.toRadians(angle - 90))) - delta - (delta * (((180.0 - (angle - 1)) / 90))));
                            }
                        } else {
                        	int h = this.getChart().getGraphics3D().textWidth(tmpSt2);
                            //y -= h / 2;
                            //x += Math.sin(Math.toRadians(angle - 90)) * h;   //delta / 2;
                        }

                    } else if ((angle > 180) && (angle <= 270)) {
                        if (horizontal) {
                            x -= delta;
                            y = y + (delta * (270 - (angle - 1)) / 90);
                        } else {
                            if (otherSide) {
                                int h = this.getChart().getGraphics3D().textWidth(tmpSt2);
                                y -= (int) Math.abs(h / 2 * Math.sin(Math.toRadians(angle))) - (delta / 2);
                                x -= (delta / 2);
                            } else {
                                int h = this.getChart().getGraphics3D().textWidth(tmpSt2);
                                //y += (int) Math.abs(h / 2 * Math.sin(Math.toRadians(angle - 180))) + (delta / 2);
                                //x -= (int) (h / 2 * Math.cos(Math.toRadians(angle - 180))) + delta;
                            }
                        }
                    } else if ((angle > 270) && (angle < 360)) {
                        if (horizontal) {
                            if (otherSide) {
                                int h = this.getChart().getGraphics3D().textWidth(tmpSt2);
                                x = x - (int) ((h / 2 * Math.sin(Math.toRadians(angle - 270))) - (delta / 2));
                                y = y - (int) ((h / 2 * Math.cos(Math.toRadians(angle - 270))) - (delta * 2));
                            } else {
                                int h = this.getChart().getGraphics3D().textWidth(tmpSt2);
                                x = x + (int) (h / 2 * Math.sin(Math.toRadians(angle - 270))) + (delta / 2);
                                y = y + (int) (h / 2 * Math.cos(Math.toRadians(angle - 270))) + delta;
                            }
                        } else {
                            if (otherSide) {
                                x += delta;
                            }

                            y += delta;
                        }
                    }

                    g.rotateLabel(x, y, tmpZ, tmpSt2, angle);
                }

                if (i >= 0) {
                    tmpSt = tmpSt.substring(i + 1);
                }
            }
        }

        g.setTextAlign(oldStrAlign);
    }

    protected StringAlignment getDepthAxisAlign()
    {
        if (otherSide)
        {
            return StringAlignment.NEAR;
        }
        else
        {
            return StringAlignment.FAR;
        }
    }

    private int getDepthAxisPos()
    {
        if (otherSide)
        {
            return chart.getChartRect().getBottom() - calcZPos();
        }
        else
        {
            return chart.getChartRect().getTop() + calcZPos();
        }
    }

    /**
     * TeeChart internal use. Outputs the title during the chart painting
     * procedure.
     *
     * @param x int x coordinate
     * @param y int y coordinate
     */
    public void drawTitle(int x, int y)
    {
        if (isDepthAxis)
        {
            IGraphics3D g = chart.getGraphics3D();
            g.setTextAlign(getDepthAxisAlign());
            g.setFont(axisTitle.getFont());
            g.textOut(x, y, chart.getAspect().getWidth3D() / 2,
                    axisTitle.getCaption());
        }
        else
        {
            boolean old = labels.bExponent;
            labels.bExponent = false;
            drawAxisLabel(axisTitle.getFont(), x, y, axisTitle.getAngle(),
                    axisTitle.getCaption(), null);
            labels.bExponent = old;
        }
    }

    final class DateParts
    {

        int year;
        int month;
        int day;

        public DateParts()
        {
            this(0, 0, 0);
        }

        public DateParts(int year, int month, int day)
        {
            this.day = day;
            this.month = month;
            this.year = year;
        }

        public double toDouble()
        {
            return (new DateTime(year, month, day)).toDouble();
        }
    }

    private DateParts decMonths(int howMany, DateParts date)
    {
        return decMonths(howMany, date.day, date.month, date.year);
    }

    private DateParts decMonths(int howMany, int day, int month,
            int year)
    {
        day = 1;
        if (month > howMany)
        {
            month -= howMany;
        }
        else
        {
            year--;
            month = 12 - (howMany - month);
        }
        return new DateParts(year, month, day);
    }

    private DateParts incMonths(int howMany, DateParts date)
    {
        return incMonths(howMany, date.year, date.month, date.day);
    }

    private DateParts incMonths(int howMany, int day, int month, int year)
    {
        day = 1;
        month += howMany;
        if (month > 12)
        {
            year++;
            month -= 12;
        }
        return new DateParts(year, month, day);
    }

    protected double incDecDateTime(boolean increment,
            double value,
            double anIncrement,
            int tmpWhichDatetime)
    {
        return dateTimeIncrement(labels.getExactDateTime()
                && iAxisDateTime
                && (tmpWhichDatetime >= DateTimeStep.HALFMONTH),
                increment, value, anIncrement,
                tmpWhichDatetime);
    }

    private DateParts incDecMonths(int howMany, DateParts date,
            boolean increment)
    {
        if (increment)
        {
            return incMonths(howMany, date);
        }
        else
        {
            return decMonths(howMany, date);
        }
    }

    /**
     * Is a set of constants used to specify a date time increment.
     *
     * @param isDateTime boolean
     * @param increment boolean
     * @param value double
     * @param anIncrement double
     * @param tmpWhichDatetime int
     * @return double date time increment value
     */
    public double dateTimeIncrement(boolean isDateTime,
            boolean increment,
            double value,
            double anIncrement,
            int tmpWhichDatetime)
    {
        if (isDateTime)
        {
            DateTime tmpDT = new DateTime(value);

            DateParts d = new DateParts(tmpDT.getYear(),
                    tmpDT.getMonth(), tmpDT.getDay());

            switch (tmpWhichDatetime)
            {
                case DateTimeStep.HALFMONTH:
                    if (d.day > 15)
                    {
                        d.day = 15;
                    }
                    else if (d.day > 1)
                    {
                        d.day = 1;
                    }
                    else
                    {
                        d = incDecMonths(1, d, increment);
                        d.day = 15;
                    }
                    break;
                case DateTimeStep.ONEMONTH:
                    d = incDecMonths(1, d, increment);
                    break;
                case DateTimeStep.TWOMONTHS:
                    d = incDecMonths(2, d, increment);
                    break;
                case DateTimeStep.THREEMONTHS:
                    d = incDecMonths(3, d, increment);
                    break;
                case DateTimeStep.FOURMONTHS:
                    d = incDecMonths(4, d, increment);
                    break;
                case DateTimeStep.SIXMONTHS:
                    d = incDecMonths(6, d, increment);
                    break;
                case DateTimeStep.ONEYEAR:
                    if (increment)
                    {
                        d.year++;
                    }
                    else
                    {
                        d.year--;
                    }

                    break;
                default:
                    if (increment)
                    {
                        value += anIncrement;
                    }
                    else
                    {
                        value -= anIncrement;
                    }
                    return value;
            }
            value = d.toDouble();
        }
        else
        {
            value += increment ? anIncrement : -anIncrement;
            //TJ71015139 resolve java double precision issue (14 decimal places)
            java.math.BigDecimal b = new java.math.BigDecimal(value).setScale(14,
                    java.math.BigDecimal.ROUND_HALF_UP);
            value = b.doubleValue();
        }

        return value;
    }

    /**
     * Returns the most logical Axis Label style.<br>
     * It calculates the "best candidate" label style based on how many Active
     * Series are in the Chart and if the Series has point labels. The
     * LabelStyle property must be set to talAuto for this function to work.<br>
     * If LabelStyle is not talAuto, the LabelStyle property value is returned.
     *
     * @return AxisLabelStyle
     */
    public AxisLabelStyle calcLabelStyle()
    {
        return (getLabels().iStyle == AxisLabelStyle.AUTO)
                ? internalCalcLabelStyle() : labels.iStyle;
    }

    /**
     * Displays an Axis at the specified screen positions with the current<br>
     * axis scales.
     *
     * @param g IGraphics3D
     * @param calcPosAxis boolean
     */
    public void draw(IGraphics3D g, boolean calcPosAxis)
    {
        IGraphics3D old = chart.getGraphics3D();
        chart.setGraphics3D(g);
        draw(calcPosAxis);
        chart.setGraphics3D(old);
    }

    /**
     * Displays an Axis at the specified screen positions with the current
     * axis scales.<br>
     * Normally you do not need to call the Draw method directly.
     *
     * @param calcPosAxis boolean True if space allowance for Axis Labelling
     * required
     */
    public void draw(boolean calcPosAxis)
    {
        if (axisDraw == null)
        {
            axisDraw = new AxisDraw(this);
        }

        axisDraw.draw(calcPosAxis);
    }

    public void drawLineSegment(int aStart, int aEnd)
    {
      Rectangle r = chart.getChartRect();
      int tmpWallSize = chart.getWalls().calcWallSize(this);

        if (getIsDepthAxis())
        {
            int tmp;

            if (getOtherSide())
                tmp = r.getBottom() + chart.getWalls().calcWallSize(chart.getAxes().getBottom()) - iZPos;
            else
                tmp = r.getTop() - iZPos;

            chart.getGraphics3D().line(posAxis, tmp, aStart, posAxis, tmp, aEnd);
        }
        else
            if (getHorizontal())
            {
              if (otherSide)
              {
                if (!chart.getAspect().getView3D()) chart.getGraphics3D().horizontalLine(aStart, aEnd, posAxis);
                else chart.getGraphics3D().horizontalLine(aStart, aEnd, posAxis, iZPos);
              }
              else
              {
                if (!chart.getAspect().getView3D()) chart.getGraphics3D().horizontalLine(aStart - chart.getWalls().calcWallSize(chart.getAxes().getLeft()),
                  aEnd, posAxis + tmpWallSize);
                else chart.getGraphics3D().horizontalLine(aStart - chart.getWalls().calcWallSize(chart.getAxes().getLeft()),
                  aEnd, posAxis + tmpWallSize, iZPos);
              }
            }
            else
            {
                int tmp = otherSide ? tmpWallSize : -tmpWallSize;
                if (!chart.getAspect().getView3D()) chart.getGraphics3D().verticalLine(posAxis + tmp, aStart, aEnd + chart.getWalls().calcWallSize(chart.getAxes().getBottom()));
                else chart.getGraphics3D().verticalLine(posAxis + tmp, aStart, aEnd + chart.getWalls().calcWallSize(chart.getAxes().getBottom()), iZPos);
            }
    }

    private void reCalcSizeCenter()
    {
        iAxisSize = iEndPos - iStartPos;
        iCenterPos = (iStartPos + iEndPos) / 2;
        internalCalcRange();
    }

    private void doCalculation(int aStartPos, int aSize)
    {
        if (getPositionUnits() == PositionUnits.PIXELS) {
            iStartPos = aStartPos
                    + Utils.round(startPosition);
            iEndPos = aStartPos
                    + Utils.round(endPosition);
        } else {
            iStartPos = aStartPos
                    + Utils.round(0.01 * aSize * startPosition);
            iEndPos = aStartPos
                    + Utils.round(0.01 * aSize * endPosition);
        }
                
    }

    protected void internalCalcPositions()
    {
        if (isDepthAxis)
        {
            doCalculation(0, chart.getAspect().getWidth3D());
        }
        else
        {
            Rectangle r = chart.getChartRect();
            if (horizontal)
            {
                doCalculation(r.x, chart.getChartRect().width);
            }
            else
            {
                doCalculation(r.y, chart.getChartRect().height);
            }
        }
        reCalcSizeCenter();
    }

    /**
     * Displays an Axis at the specified screen positions with the current
     * axis scales.<br>
     * Main drawing method. Custom draw methods can be overloads.
     *
     * @param posLabels int
     * @param posTitle int
     * @param posAxis int
     * @param gridVisible boolean
     */
    public void draw(int posLabels, int posTitle, int posAxis,
            boolean gridVisible)
    {
        internalCalcPositions();
        draw(posLabels, posTitle, posAxis, gridVisible, iStartPos, iEndPos);
    }

    /**
     * Displays an Axis at the specified screen positions with the current
     * axis scales.<br>
     * Main drawing method. Custom draw methods can be overloads.
     *
     * @param posLabels int
     * @param posTitle int
     * @param posAxis int
     * @param gridVisible boolean
     * @param aStartPos int
     * @param aEndPos int
     */
    public void draw(int posLabels, int posTitle, int posAxis,
            boolean gridVisible, int aStartPos,
            int aEndPos)
    {
        getLabels().position = posLabels;
        this.posTitle = posTitle;
        this.posAxis = posAxis;
        iStartPos = aStartPos;
        iEndPos = aEndPos;
        reCalcSizeCenter();

        boolean oldgridvisible = getGrid().getVisible();
        grid.setVisible(gridVisible);
        draw(false);
        grid.setVisible(oldgridvisible);
    }

    /**
     * Scrolls or displaces the Axis Maximum and Minimum values by the Offset
     * parameter.<br>
     * If you want to scroll the Axis outside Series limits,
     * CheckLimits must be false.
     *
     * @param offset double
     * @param checkLimits boolean
     */
    public void scroll(double offset, boolean checkLimits)
    {
        if ((!checkLimits)
                || (((offset > 0)
                && (maximumValue < chart.internalMinMax(this, false,
                getHorizontal())))
                || ((offset < 0)
                && (minimumValue
                > chart.internalMinMax(this, true, getHorizontal())))))
        {
            automatic = false;
            automaticMaximum = false;
            maximumValue += offset;
            automaticMinimum = false;
            minimumValue += offset;
            invalidate();
        }
    }

    /**
     * Changes the current Axis Minimum and Maximum scales.<br>
     * Axis.Automatic must be set to false.
     *
     * @param minDate DateTime Axis Minimum scale
     * @param maxDate DateTime Axis Maximum scale
     */
    public void setMinMax(DateTime minDate, DateTime maxDate)
    {
        setMinMax(minDate.toDouble(), maxDate.toDouble());
    }

    /**
     * Changes the current Axis Minimum and Maximum scales.<br>
     * Axis.Automatic must be set to false.
     *
     * @param min double Axis Minimum scale
     * @param max double Axis Maximum scale
     */
    public void setMinMax(double min, double max)
    {
        automatic = false;
        automaticMinimum = false;
        automaticMaximum = false;

        if (min > max)
        { // swap
            double tmp = min;
            min = max;
            max = tmp;
        }

        internalSetMinimum(min);
        internalSetMaximum(max);
        if ((maximumValue - minimumValue) < MINAXISRANGE)
        {
            internalSetMaximum(minimumValue + MINAXISRANGE);
        }
    }

    public void setChart(IBaseChart c)
    {
        super.setChart(c);

        if (labels !=null) labels.setChart(chart);
        if (labels !=null) labels.axis = this;

        if (axisDraw != null)
        {
            axisDraw.setChart(chart);
        }
    }

    private void setInternals()
    {
        iMaximum = maximumValue;
        iMinimum = minimumValue;
        internalCalcRange();
    }

    /**
     * Displays an Axis at the specified screen positions with the current
     * axis scales.<br>
     * Main drawing method. Custom draw methods can be overloads.
     *
     * @param posLabels int
     * @param posTitle int
     * @param posAxis int
     * @param gridVisible boolean
     * @param aMinimum double
     * @param aMaximum double
     * @param aStartPosition int
     * @param aEndPosition int
     */
    public void draw(int posLabels, int posTitle, int posAxis,
            boolean gridVisible, double aMinimum,
            double aMaximum,
            int aStartPosition, int aEndPosition)
    {
        draw(posLabels, posTitle,
                posAxis, gridVisible,
                aMinimum, aMaximum,
                desiredIncrement,
                aStartPosition, aEndPosition);
    }

    /**
     * Displays an Axis at the specified screen positions with the current
     * axis scales.<br>
     * Main drawing method. Custom draw methods can be overloads.
     *
     * @param posLabels int
     * @param posTitle int
     * @param posAxis int
     * @param gridVisible boolean
     * @param aMinimum double
     * @param aMaximum double
     * @param aIncrement double
     * @param aStartPos int
     * @param aEndPos int
     */
    public void draw(int posLabels, int posTitle,
            int posAxis, boolean gridVisible,
            double aMinimum,
            double aMaximum,
            double aIncrement, int aStartPos, int aEndPos)
    {
        double oldMin = minimumValue;
        double oldMax = maximumValue;
        double oldIncrement = desiredIncrement;
        boolean oldAutomatic = automatic;
        automatic = false;
        minimumValue = aMinimum;
        maximumValue = aMaximum;
        desiredIncrement = aIncrement;
        setInternals();
        draw(posLabels, posTitle, posAxis, gridVisible, aStartPos,
                aEndPos);
        minimumValue = oldMin;
        maximumValue = oldMax;
        desiredIncrement = oldIncrement;
        automatic = oldAutomatic;
        setInternals();
    }

    /**
     * Defines the maximum value for the axis scales to round to when True
     *
     * @return boolean
     */
    public boolean getMaxRound()
    {
        return maximumRounded;
    }

    /**
     * Sets the maximum value for the axis scales to round to when True
     *
     * @param value
     */
    public void setMaxRound(boolean value)
    {
//          if (automatic) {automatic = !value;}
//          if (automaticMaximum){automaticMaximum = !value;}
        if (value != maximumRounded)
        {
            maximumRounded = value;
            this.getChart().invalidate();
        }
    }

    /**
     * Defines the minimum value for the axis scales to round to when True
     *
     * @return boolean
     */
    public boolean getMinRound()
    {
        return minimumRound;
    }

    /**
     * Sets the minimum value for the axis scales to round to when True
     *
     * @param value boolean
     */
    public void setMinRound(boolean value)
    {
        if (value != minimumRound)
        {
            minimumRound = value;
            this.getChart().invalidate();
        }

    }

    /**
     * Rounds Axis scale to integer values
     *
     */
    public void calcRoundScales()
    {
        double tmpInc;
        boolean tmp, tmpAny;

        if (iAxisSize != 0 && !iRangezero)
        {
            tmp = maximumRounded && (automatic || automaticMaximum);
            tmpAny = tmp;

            if (tmp)
            {
                tmpInc = calcIncrement();

                if (tmpInc != 0)
                {
                    iMaximum = tmpInc * Math.ceil(iMaximum / tmpInc);
                }
            }
            else
            {
                tmpInc = 0;
            }

            tmp = minimumRound && (automatic || automaticMinimum);
            tmpAny = tmpAny || tmp;

            if (tmp)
            {
                if (tmpInc == 0)
                {
                    tmpInc = calcIncrement();
                }

                if (tmpInc != 0)
                {
                    iMinimum = tmpInc * Math.floor(iMinimum / tmpInc);
                }
            }

            if (tmpAny)
            {
                internalCalcRange();
            }
        }
    }

    /**
     * Returns the calculated Axis Label increment and serves as a useable
     * measure when Labelling is set to manual or automatic increments.<br>
     * Please note that the related Increment property only returns the<br>
     * increment value when those increments are manually set via the same<br>
     * property.
     *
     * @return double calculated Axis Label increment.
     */
    public double calcIncrement()
    {
        int tmp;
        if (horizontal)
        {
            tmp = Math.max(labels.labelWidth(iMinimum), labels.labelWidth(iMaximum));
        }
        else
        {
            tmp = Math.max(labels.labelHeight(iMinimum), labels.labelHeight(iMaximum));
        }
        if (getIncrement() == 0)
        {
            if (horizontal)
            {
                tmp = Math.max(tmp, labels.labelWidth((iMaximum + iMinimum) * 0.5));
            }
            else
            {
                tmp = Math.max(tmp, labels.labelHeight((iMaximum + iMinimum) * 0.5));
            }
        }
        else
        {
            if (horizontal)
            {
                tmp = Math.max(Math.max(tmp, labels.labelWidth(iMinimum + getIncrement())),
                        labels.labelWidth(iMaximum - getIncrement()));
            }
            else
            {
                tmp = Math.max(Math.max(tmp, labels.labelHeight(iMinimum + getIncrement())),
                        labels.labelHeight(iMaximum - getIncrement()));
            }
        }

        double result = calcXYIncrement(tmp);

        if (labels.getAlternate())
        {
            if (logarithmic)
            {
                result = result / logarithmicBase;
            }
            else
            {
                result = result / 2;
            }
        }
        return result;
    }

    public int getInflateAxisPos(int value, int amount)
    {
        int tmpResult = value;
        if (horizontal)
        {
            if (otherSide)
            {
                tmpResult -= amount;
            }
            else
            {
                tmpResult += amount;
            }
        }
        else
        {
            if (otherSide)
            {
                tmpResult += amount;
            }
            else
            {
                tmpResult -= amount;
            }
        }
        return tmpResult;
    }

    /**
     * Internal use.
     *
     * @param value
     */
    public void setPosTitle(int value)
    {
        posTitle = value;
    }

    public Axis getPartnerAxis()
    {
        return partnerAxis;
    }
    public void setPartnerAxis(Axis value)
    {
       if (partnerAxis != value)
           partnerAxis = value;
    }

    public boolean getUsePartnerAxis()
    {
        return usePartnerAxis;
    }
    public void setUsePartnerAxis(boolean value)
    {
        usePartnerAxis = value;
    }


}
