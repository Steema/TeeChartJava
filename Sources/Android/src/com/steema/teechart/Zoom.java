package com.steema.teechart;

import com.steema.teechart.axis.Axis;

import com.steema.teechart.drawing.ChartBrush;
import com.steema.teechart.drawing.ChartPen;
import com.steema.teechart.drawing.Color;
import com.steema.teechart.drawing.LineCap;
import com.steema.teechart.drawing.DashStyle;
import com.steema.teechart.drawing.IGraphics3D;
import com.steema.teechart.drawing.Point;
import com.steema.teechart.events.FrameworkMouseEvent;

import java.util.Vector;

/**
 *
 * <p>Title: Zoom class</p>
 *
 * <p>Description: Used at tChart1.Zoom property, determines mouse
 * zoom attributes.</p>
 *
 * <p>Copyright (c) 2005-2019 by Steema Software SL. All Rights Reserved.</p>
 *
 * <p>Company: Steema Software</p>
 *
 */
public class Zoom extends ZoomScroll {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private boolean allow = true;
    private boolean animated;
    private int animatedSteps = 8;
    private ChartBrush bBrush;
    private ZoomDirections direction = ZoomDirections.BOTH;
    private int mouseButton = FrameworkMouseEvent.BUTTON1;
    private int keyShift = 0;
    private int minPixels = 16;
    protected ChartPen pen;
    protected boolean zoomed;
    protected Vector<ZoomSnapshot> zoomAxesHist;
    protected int zoomCounter = 0;
    private ZoomStyle style = ZoomStyle.CLASSIC;
    private boolean fullRepaint = false;

  /**
   * Controls the animated zoom "speed" (inertia)
   */
  public double animatedFactor = 3.0;

  /**
   * Creates a new Zoom instance.
   *
   * @param c IBaseChart
   */
  public Zoom(IBaseChart c) {
      super(c);
  }

   public Vector<ZoomSnapshot> addZoomSnapshot()
   {
                   ZoomSnapshot snapshot = new ZoomSnapshot();

            snapshot.AxesMinMax = new double[] 
            { chart.getAxes().getLeft().getMinimum(),
                                          chart.getAxes().getLeft().getMaximum(),
                                           chart.getAxes().getTop().getMinimum(),
                                           chart.getAxes().getTop().getMaximum(),
                                            chart.getAxes().getRight().getMinimum(),
                                            chart.getAxes().getRight().getMaximum(),
                                            chart.getAxes().getBottom().getMinimum(),
                                            chart.getAxes().getBottom().getMaximum()
            };

            if (chart.getAxes().getCustom().size() > 0)
            {
                snapshot.CustomCount = chart.getAxes().getCustom().size();
                snapshot.CustomMinMax = new double[snapshot.CustomCount * 2][];
            }

            if (zoomAxesHist == null)
                zoomAxesHist = new Vector<ZoomSnapshot>();
           
            zoomAxesHist.add(snapshot);
            zoomCounter++;

            return zoomAxesHist;
   }
  
   public void clearZoomHistory()
   {
       if (zoomAxesHist != null)
            zoomAxesHist.clear();
            zoomCounter = 0;
   }
   public Vector<ZoomSnapshot> removeZoomSnapshot()
    {
       if (zoomAxesHist.size() > 0)
       {
          zoomAxesHist.remove(zoomAxesHist.size() - 1);
          zoomCounter--;
       }

        return zoomAxesHist;
    }
    public void zoomBackOne()
    {
        ZoomSnapshot snapshot = (ZoomSnapshot)(zoomAxesHist.get(zoomAxesHist.size() - 2));
        chart.getAxes().getLeft().setMinMax(snapshot.AxesMinMax[0], snapshot.AxesMinMax[1]);
        chart.getAxes().getTop().setMinMax(snapshot.AxesMinMax[2], snapshot.AxesMinMax[3]);
        chart.getAxes().getRight().setMinMax(snapshot.AxesMinMax[4], snapshot.AxesMinMax[5]);
        chart.getAxes().getBottom().setMinMax(snapshot.AxesMinMax[6], snapshot.AxesMinMax[7]);
   }
   private boolean zoomHistory = false;
   public boolean getHistory()
   {
       return zoomHistory;
   }
   public void setHistory(boolean value)
   {
        zoomHistory = value;
        if (zoomHistory == false)
            clearZoomHistory();
   }
    public Vector<ZoomSnapshot> getHistorySteps()
    {
        if (zoomAxesHist == null)
             zoomAxesHist = new Vector<ZoomSnapshot>();
        return zoomAxesHist;
    }
    public void setHistorySteps(Vector<ZoomSnapshot> value)
    {
        zoomAxesHist = value;
    }
   
  
  /**
   * Allows runtime Zoom by dragging the mouse when true.<br>
   * Default value: true
   *
   * @return boolean
   */
  public boolean getAllow() {
      return allow;
  }

  /**
   * Allows runtime Zoom by dragging the mouse when true.<br>
   * Default value: true
   *
   * @param value boolean
   */
  public void setAllow(boolean value) {
      allow = value;
  }

  /**
   * Animates Zoom in sequenced steps when true.<br>
   * Default value: false
   *
   * @return boolean
   */
  public boolean getAnimated()

  {
      return animated;
  }

  /**
   * Animates Zoom in sequenced steps when true.<br>
   * Default value: false
   *
   * @param value boolean
   */
  public void setAnimated(boolean value) {
      animated = value;
  }

  /**
   * Brush used to fill mousedragged zoom area.
   *
   * @return ChartBrush
   */
  public ChartBrush getBrush() {
      if (bBrush == null) {
          bBrush = new ChartBrush(chart, Color.WHITE, false);
      }
      return bBrush;
  }

  /**
   * The direction of the zoom on a selected area.<br><br>
   * Example. Horizontal will zoom only on a horizontal plane although the
   * mouse is dragged across a vertical and horizontal plane.<br>
   * Default value: Both
   *
   * @return ZoomDirections
   */
  public ZoomDirections getDirection() {
      return direction;
  }

  /**
   * Sets the direction of the zoom on a selected area.<br><br>
   * Default value: Both
   *
   * @param value ZoomDirections
   */
  public void setDirection(ZoomDirections value) {
      direction = value;
  }

  /**
   * Determines the number of steps of the animated zooming sequence.<br>
   * Large number of steps can delay zooming. The Animated property should be
   * true.<br>
   * Default value: 8
   *
   * @return int
   */
  public int getAnimatedSteps() {
      return animatedSteps;
  }

  /**
   * Sets the number of steps of the animated zooming sequence.<br>
   * Large number of steps can delay zooming. The Animated property should be
   * true.<br>
   * Default value: 8
   *
   * @param value int
   */
  public void setAnimatedSteps(int value) {
      animatedSteps = value;
  }

  /**
   * The keyboard button as an extra condition to initiate the zoom.<br>
   * Default value: None
   *
   * @return int
   */
  public int getKeyMask() {
      return keyShift;
  }

  /**
   * Sets a keyboard button as an extra condition to initiate the zoom.<br>
   * Default value: None
   *
   * @param value int
   */
  public void setKeyMask(int value) {
      keyShift = value;
  }

  /**
   * The minimum number of pixels to actuate zoom action.<br>
   * Default value: 16
   *
   * @return int
   */
  public int getMinPixels() {
      return minPixels;
  }

  /**
   * Sets minimum number of pixels to actuate zoom action.<br>
   * Default value: 16
   *
   * @param value int
   */
  public void setMinPixels(int value) {
      minPixels = value;
  }

  /**
   * The mousebutton to use for the zoom action.<br>
   * Note that Scroll action uses the right (Right) mousebutton as
   * default.<br>
   * Default value: Left
   *
   * @return int
   */
  public int getMouseButton() {
      return mouseButton;
  }

  /**
   * Sets the mousebutton to use for the zoom action.<br>
   * Default value: Left
   *
   * @param value int
   */
  public void setMouseButton(int value) {
      mouseButton = value;
  }
  
  private ZoomResetStyle _zoomResetStyle = ZoomResetStyle.DOUBLETAP;
  
  /**
   * Active for use when ZoomStyle = ZoomStyle.INCHART_MULTI<br>
   * Gets/Sets the touch action that resets the chart<br>
   * zoom/scroll.<br>
   * Default value: ZoomResetStyle.DOUBLETAP 
   */
  public ZoomResetStyle getZoomReset() {
      return _zoomResetStyle;
  }
  
  public void setZoomReset(ZoomResetStyle value) {
	  _zoomResetStyle = value;
  }
  

  /**
   * Pen used to draw surrounding rectangle of zoom area.
   *
   * @return ChartPen
   */
  public ChartPen getPen() {
      if (pen == null) {
          pen = new ChartPen(chart, Color.BLACK, true, 1, LineCap.BEVEL, DashStyle.DASH);
      }
      return pen;
  }

  /**
   * Zooms the Chart rectangle. Units pixels.
   *
   * @param r Rectangle
   */
  public void zoomRect(Rectangle r) {
      x0 = r.getLeft();
      y0 = r.getTop();
      x1 = r.getRight();
      y1 = r.getBottom();
      calcZoomPoints();
  }

  public Rectangle ClipZoomReversibleRectangle() {
     Point p = chart.getParent().pointToScreen(new Point(0, 0));
     Rectangle origR = new Rectangle(p.x + x0, p.x + y0, x1 - x0, y1 - y0);
     chart.getGraphics3D().orientRectangle(origR);
     Rectangle chartR = chart.getChartBounds();
     chartR.x = p.x;
     chartR.y = p.y;
     origR.intersect(chartR);
     return origR;
  }
  
  protected void calcZoomPoints() {
      check();
      chart.getAxes().doZoom(x0, y0, x1, y1);
          if (zoomHistory)
                addZoomSnapshot();
  }

//MM out pending review IE container compatibility
//		public void draw()
//		{
//			chart.invalidate();
//			Rectangle r=new Rectangle(x0,y0,x1-x0,y1-y0);
//			if (chart.getGraphics3D() instanceof Graphics3DGdiPlus)
//			{
//				//Graphics tmp=;
//				((Graphics3DGdiPlus)chart.getGraphics3D()).Graphics.DrawRectangle(Pen.DrawingPen, r);
//			}
//		}

  //CDI Zoom
  /**
   * Displays rectangle while dragging Chart for zoom operation.
   */
  public void draw() {
      IGraphics3D g = chart.getGraphics3D();
      Rectangle r = new Rectangle(x0,y0,x1-x0,y1-y0);
      r = g.orientRectangle(r);
      boolean oldVisible;

      if(chart.getParent()!=null) {
          /*if (getFullRepaint())*/ {
              if (bBrush != null && bBrush.getVisible()) {
                  g.setBrush(getBrush());

                  if (pen != null && pen.getVisible()) {
                      g.setPen(getPen());
                      g.rectangle(r);
                  }
                  else {
                      oldVisible = g.getPen().getVisible();
                      g.getPen().setVisible(false);
                      g.rectangle(r);
                      g.getPen().setVisible(oldVisible);
                  }
              } else if (pen != null && pen.getVisible()) {
                  g.setPen(getPen());
                  oldVisible = g.getBrush().getVisible();
                  g.getBrush().setVisible(false);
                  g.rectangle(r);
                  g.getBrush().setVisible(oldVisible);
              } else {
                  g.setPen(getPen());
                  oldVisible = g.getBrush().getVisible();
                  g.getBrush().setVisible(false);
                  g.rectangle(r);
                  g.getBrush().setVisible(oldVisible);
              }
          }
          /*else {
              if (cachedImage != null) {
                  chart.invalidate();
              }
          }*/
      }
  }

  /**
   * Overrides base SetChart method to adjust pen and brush chart properties.
   *
   * @param c IBaseChart
   */
  public void setChart(IBaseChart c) {
      super.setChart(c);
      if (pen != null) {
          pen.setChart(c);
      }
      if (bBrush != null) {
          bBrush.setChart(c);
      }
  }

    /**
     * Gets whether the zoom Rectangle is painted using the ControlPaint XOR methods.
     * Default value: false
     *
     * @return boolean
     */
    public boolean getFullRepaint() {
        return fullRepaint;
    }

    /**
     * Sets whether the zoom Rectangle is painted using the ControlPaint XOR methods.
     * Default value: false
     *
     * @param value boolean
     */
    public void setFullRepaint(boolean value) {
        fullRepaint = value;
    }

  /**
   * Rescales the Chart Axis to their Maximum and Minimum values.
   */
  public void undo() {
      if ((zoomHistory) && (zoomAxesHist != null) && (zoomAxesHist.size() > 1)) {
    	  zoomBackOne();
    	  removeZoomSnapshot();
      }
      else {
    	  if ((zoomHistory) && (zoomAxesHist != null) && (zoomAxesHist.size()== 1))
    		  removeZoomSnapshot();

    	  chart.restoreAxisScales();
      }
      setZoomed(false);
  }

  /**
   * Determines if Chart axis scales fit all Chart points or not.<br>
   * Run-time only. <br>
   * It is set to true when users's apply zoom or scroll to the Chart using
   * the mouse at run-time. <br>
   * The Zoom.Undo method sets the Zoomed property to false and resets the
   * axis scales to fit all Series points. <br>
   * The default value is true, meaning no zoom or scroll has been applied
   * to the chart after it has been displayed for first time. <br><br>
   * Default value: false
   *
   * @return boolean
   */
  public boolean getZoomed() {
      return zoomed;
  }

  /**
   * Set the Chart axis scales to fit all Chart points when true.<br>
   * Default value: false
   *
   * @param value boolean
   */
  public void setZoomed(boolean value) {
      zoomed = value;
      if ((chart.getParent() != null) && (!zoomed)) {
          chart.getParent().doUnZoomed(this);
      }
      //invalidate();
  }

  static private Point.Double calcAxisScale(Axis axis, double percentZoom) {
      FloatRange minmax = new FloatRange();
      axis.calcMinMax(minmax);
      double tmpDelta = (minmax.min - minmax.max) * percentZoom;
      return new Point.Double(minmax.min + tmpDelta, minmax.max - tmpDelta);
  }

  /**
   * Applies the specified PercentZoom Zoom In/Out to the current Axis
   * scales.<br>
   * When PercentZoom is greater than 100%, Zoom Out is performed. <br>
   * When PercentZoom is lower than 100%, Zoom In is performed.<br>
   * The Animated property controls if Zoom is done directly in only o
   * ne step or by multiple zooms thus giving an animation effect.
   *
   * @param percentZoom double
   */
  public void zoomPercent(double percentZoom) {
      percentZoom = (percentZoom - 100.0) * 0.01;

      Point.Double left = calcAxisScale(chart.getAxes().getLeft(),
                                        percentZoom);
      Point.Double right = calcAxisScale(chart.getAxes().getRight(),
                                         percentZoom);
      Point.Double top = calcAxisScale(chart.getAxes().getTop(), percentZoom);
      Point.Double bottom = calcAxisScale(chart.getAxes().getBottom(),
                                          percentZoom);
      chart.doZoom(top, bottom, left, right);
      invalidate();
  }
  
  /*
   * When set dictates what type of zoom will actuate on<br>
   * single or multi-touch press and drag
   */
  public enum ZoomStyle
  {
    /**
     * Classic TeeChart zoom reduced to axes area.
     */
    INCHART,
    /**
     * Zooms the chart as an image.
     */
    FULLCHART,
    /**
     * Multi-touch zooms/drags with two or one touch respectively.
     * @deprecated  Use {@link #CLASSIC} instead.
     */
    @Deprecated
    INCHART_MULTI,
    /**
     * Multi-touch zooms/drags with two or one touch respectively.
     */
    CLASSIC,
    /**
     * Doesn't perform any zoom or scrolling.
     */
    NONE
  } 
  
  /**
   * Active for use when ZoomStyle = ZoomStyle.INCHART_MULTI<br>
   * Use with ZoomReset property to control which touch<br>
   * action that resets chart zoom
   */
  public enum ZoomResetStyle
  {
    /// <summary>
    ///Double-touching chart will reset zoom. 
    /// </summary>
    DOUBLETAP,
    /// <summary>
    ///LongPressing chart will reset zoom.  
    /// </summary>
    LONGPRESS,
    /// <summary>
    ///Reset zoom will not action via Touch.  
    /// </summary>    
    NONE
  }
  
  public ZoomStyle getZoomStyle()
  {
	  return style;
  }
  
  public void setZoomStyle(ZoomStyle value)
  {
	  style = value;
  }
}
