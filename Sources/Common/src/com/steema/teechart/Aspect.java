package com.steema.teechart;

import com.steema.teechart.events.View3DEvent;

/**
 *
 * <p>Title: Aspect class</p>
 *
 * <p>Description: Chart view characteristics to define Chart 3D appearance</p>
 *
 * <p>Copyright (c) 2005-2019 by Steema Software SL. All Rights Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 *
 */
public final class Aspect extends TeeBase {

    private boolean applyZOrder = true;
    private int chart3D = 15;
    private boolean clipPoints = true;
    private int elevation = 345;
    private double fontZoom = 100;
    private int horizOffset;
    private int orthoAngle = 45;
    private boolean orthogonal = true;
    private int perspective = 100;
    private int rotation = 345;
    private int tilt;
    private int vertOffset;
    private boolean view3D = true;
    private int zoom = 100;
    private boolean zoomText = true;
    
    private static final long serialVersionUID = 1L;

    public Aspect() {
        this((IBaseChart)null);
    }

    public Aspect(IBaseChart c) {
        super(c);
    }

    /**
     * Determines the angle of 3D rotation in degrees.<br>
     * Rotation describes front plane rotation by rotation degrees (0 - 360).
     * Increasing the value positively will bring the right of the Chart
     * towards the viewer and the left of the Chart away, moving around a
     * vertical axis at the central horizontal point of the Chart. <br>
     * <b>Important.</b> Orthogonal should be set to false for Rotation
     * to act on the Chart. <br>
     * Default value: 345
     *
     * @return int
     */
    public int getRotation() {
        return rotation;
    }

    /**
     * Sets the angle of 3D rotation in degrees.
     *
     * @param value int
     */
    public void setRotation(int value) {
    	value = value % 360;
    	while (value < 0)
    		value = 360 + value;
    		
        rotation = setIntegerProperty(rotation, value);
    }

    /**
     * Determines the angle of 3D elevation in degrees.<br>
     * Elevation describes front plane rotation by rotation degrees (0 - 360).
     * Increasing the value positively will bring the top of the Chart towards
     * the viewer and the bottom of the Chart away, moving around an horizontal
     * axis at the central vertical point of the Chart. <br>
     * <b>Important.</b> Orthogonal should be set to false for Elevation to
     * act on the Chart. <br>
     * Default value: 345
     *
     * @return int
     */
    public int getElevation() {
        return elevation;
    }

    /**
     * Sets the angle of 3D elevation in degrees.
     *
     * @param value int
     */
    public void setElevation(int value) {
    	value = value % 360;
    	while (value < 0)
    		value = 360 + value;

    	elevation = setIntegerProperty(elevation, value);
    }

    /**
     * Determines the angle of 3D tilt in degrees.<br>
     * Not supported for 2D or 3D orthogonal Charts <br>
     * <b>Important.</b> Set TChart.Aspect.Orthogonal = false;<br>
     * Default value: 0
     *
     * @return int
     */
    public int getTilt() {
        return tilt;
    }

    /**
     * Sets the angle of 3D tilt in degrees.
     *
     * @param value int
     */
    public void setTilt(int value) {
        tilt = setIntegerProperty(tilt, value);
    }

    /**
     * Displays multiple Series at different 3D "Z" (depth) positions.<br>
     * Run-time only. <br> ApplyZOrder controls if several Series of the same
     * TChart class are displayed in a different Z space for each one. <br>
     * It's valid only when TChart.Aspect.View3D is true and when there's more
     * than one Series in same chart. When false, all Series are drawn using
     * the full Chart Z space. The Chart output can be confusing if Series
     * overlap. <br>
     * Default value: true
     *
     * @return boolean
     */
    public boolean getApplyZOrder() {
        return applyZOrder;
    }

    /**
     * Sets the different 3D "Z" depth positions of multiple Series.
     *
     * @param value boolean
     */
    public void setApplyZOrder(boolean value) {
        applyZOrder = setBooleanProperty(applyZOrder, value);
    }

    /**
     * Restricts those Series points displayed outside the Chart axes
     * rectangle when true.<br>
     * Chart method defines the TChart or TDBChart component to display
     * on a TQRChart. TQRChart is an "interface" component. It must be
     * associated to a TChart or TDBChart component. <br>
     * Default value: true
     *
     * @return boolean
     */
    public boolean getClipPoints() {
        return clipPoints;
    }

    /**
     * Restricts those Series points displayed outside the Chart axes
     * rectangle when true.
     *
     * @param value boolean
     */
    public void setClipPoints(boolean value) {
        clipPoints = setBooleanProperty(clipPoints, value);
    }

    /**
     * Displays the Chart in semi-3D mode when true.<br>
     * Orthogonal displays the Chart in a simulated 3D fashion by drawing the
     * Chart depth at an inclined angle. The bottom of the Chart is always
     * horizontal. When false and the Chart is in 3D, the Chart will display
     * in true 3D mode. <br>
     * Default value: true
     *
     * @return boolean
     */
    public boolean getOrthogonal() {
        return orthogonal;
    }

    /**
     * Displays the Chart in semi-3D mode when true.
     *
     * @param value boolean
     */
    public void setOrthogonal(boolean value) {
        orthogonal = setBooleanProperty(orthogonal, value);
    }

    /**
     * Chooses between speed or display quality for Chart rendering. <br><br>
     * for example:-<br>
     * AntiAlias - Specifies antialiased rendering.  <br>
     * Default - Specifies the default mode.  <br>
     * HighQuality - Specifies high quality, low speed rendering.  <br>
     * HighSpeed - Specifies high speed, low quality rendering.  <br>
     * Invalid - Specifies an invalid mode.  <br>
     * None - Specifies no antialiasing. <br>
     * Default value: HighSpeed
     *
     * @return boolean
     */
    public boolean getSmoothingMode() {
      if ((chart==null) || (chart.getGraphics3D()==null)) {
        return false;
      }
      else {
        return chart.getGraphics3D().getSmoothingMode();
      }
    }

    /**
     * Sets the type of rendering used to display the Chart depending on whether
     * speed, display quality or antialiasing is required.
     *
     * @param value boolean
     */
    public void setSmoothingMode(boolean value) {
      if ((chart!=null) && (chart.getGraphics3D()!=null)) {
        chart.getGraphics3D().setSmoothingMode(value);
      }
    }

    /**
     * Chooses between speed or display quality for Text rendering.<br>
     *
     * @return boolean
     */
    public boolean getTextSmooth() {
        if ((chart!=null) && (chart.getGraphics3D()!=null))
          return chart.getGraphics3D().getTextSmooth();
        else
          return false;
    }

    /**
     * Sets the type of rendering used to display Text.
     *
     * @param value boolean
     */
    public void setTextSmooth(boolean value) {
        if ((chart!=null) && (chart.getGraphics3D()!=null)) {
            chart.getGraphics3D().setTextSmooth(value);
        }        
    }

    /**
     * Percent of zoom in 3D mode for the entire Chart.<br>
     * Increasing the value of Zoom brings the entire Chart towards the viewer.
     * 'In Chart' zoom will still function by mouse dragging within the Chart
     * area and is distinct from whole Chart zooming.<br>
     * Default value: 100
     *
     * @return int
     */
    public int getZoom() {
        return zoom;
    }

    /**
     * Sets the percentage of zoom in 3D mode for the entire Chart.
     *
     * @param value int
     */
    public void setZoom(int value) {
        zoom = setIntegerProperty(zoom, value);
    }

    /**
     * <br>
     * Perspective offers a distance adjustment for the Chart display, giving
     * an appearance of perspective between the nearer and further parts of the
     * Chart. <br>
     * See the comparison below: <br>
     * No perspective <br>
     * Perspective = 50; <br>
     * Default value: 15
     *
     * @return int
     */
    public int getPerspective() {
        return perspective;
    }

    /**
     * Sets the percentage of 3D perspective.
     *
     * @param value int
     */
    public void setPerspective(int value) {
        perspective = setIntegerProperty(perspective, value);
    }

    /**
     * Angle in degrees, from 0 to 90, when displaying in Orthogonal mode.<br>
     * OrthoAngle sets the angle of inclination of the Depth axis when the
     * Chart is set to Orthogonal mode, or in other words, when Orthogonal
     * property is set to true. <br>
     * Default value: 45
     *
     * @return int
     */
    public int getOrthoAngle() {
        return orthoAngle;
    }

    /**
     * Sets the angle in degrees, from 0 to 90, to display the Chart when in
     * Orthogonal mode.
     *
     * @param value int
     */
    public void setOrthoAngle(int value) {
        orthoAngle = setIntegerProperty(orthoAngle, value);
    }

    /**
     * Percent from 0 to 100 of Z Depth.<br>
     * Chart3DPercent indicates the size ratio between Chart dimensions and
     * Chart depth when Chart.Aspect.View3D is true. You can specify a percent
     * number from 1 to 100.<br>
     * Default value: 15
     *
     * @return int
     */
    public int getChart3DPercent() {
        return chart3D;
    }

    /**
     * Sets the percentage of Z Depth.<br>
     *
     * @param value int
     */
    public void setChart3DPercent(int value) {
        chart3D = setIntegerProperty(chart3D, value);
    }

    transient int height3D;
    transient int width3D;

    public int getWidth3D() {
        return width3D;
    }

    public int getHeight3D() {
        return height3D;
    }

    /**
     * Amount (postive or negative) in pixels of horizontal displacement.<br>
     * HorizOffset will move the Chart Rectangle horizontally across the Chart
     * Panel. Positive values move the Chart to the right, negative values to
     * the left. <br>
     * Default value: 0
     *
     * @return int
     */
    public int getHorizOffset() {
        return horizOffset;
    }

    public double getFontZoom() {
    	return fontZoom;
    }
    
    public void setFontZoom(final double value) {
    	fontZoom = setDoubleProperty(fontZoom, value);
    }
    
    /**
     * Sets the amount (postive or negative) in pixels of horizontal
     * displacement.<br>
     *
     * @param value int
     */
    public void setHorizOffset(int value) {
        horizOffset = setIntegerProperty(horizOffset, value);
    }

    /**
     * Amount (postive or negative) in pixels of vertical displacement.<br>
     * Moves the entire Chart on a vertical plane. Not Active for 2D or 3D
     * orthogonal Charts. <br>
     * Default value: 0
     *
     * @return int
     */
    public int getVertOffset() {
        return vertOffset;
    }

    /**
     * Sets the amount (postive or negative) in pixels of vertical
     * displacement.<br>
     *
     * @param value int
     */
    public void setVertOffset(int value) {
        vertOffset = setIntegerProperty(vertOffset, value);
    }

    /**
     * Draws each Series with a 3D effect.<br>
     * You can control the 3D proportion by using Chart.Aspect.Chart3DPercent.
     * Chart.Walls.Visible depends on View3D. <br>
     * Default value: true
     *
     * @return boolean
     * @see Aspect#getOrthoAngle
     */
    public boolean getView3D() {
        return view3D;
    }

    /**
     * Draws each Series with a 3D effect when true.
     *
     * @param value boolean
     */
    public void setView3D(boolean value) {
        view3D = setBooleanProperty(view3D, value);
        chart.broadcastEvent(new View3DEvent());
    }

    /**
     * Resizes all texts according to Zoom property when true.<br>
     * When false, Text size will remain constant regardless of the zoom factor
     * obtained with Aspect.Zoom <br>
     * Default value: true
     *
     * @return boolean
     */
    public boolean getZoomText() {
        return zoomText;
    }

    /**
     * Resizes all texts according to Zoom property when true.
     *
     * @param value boolean
     */
    public void setZoomText(boolean value) {
        zoomText = setBooleanProperty(zoomText, value);
    }

    /**
     * Copies all properties from a Series component to another.<br>
     * Only the common properties shared by both source and destination Series
     * are copied. <br>
     * The following code copies all properties from Series2 into Series1: <br>
     * tChart1.Series[0].Assign(tChart1.Series[1]);<br><br>
     * Some Series types restore values after assigning them. For example,
     * Points Series restores Pointer.Visible to True after being assigned
     * to a Line Series, which has Pointers invisible by default. <br>
     * <b>Note:</b> Series events are not assigned.  Series DataSource and
     * FunctionType are assigned.  Assign is used by CloneChartSeries and
     * ChangeSeriesType methods for example.
     *
     * @param a Aspect
     */
    public void assign(final Aspect a) {
        applyZOrder = a.applyZOrder;
        chart3D = a.chart3D;
        clipPoints = a.clipPoints;
        elevation = a.elevation;
        horizOffset = a.horizOffset;
        orthoAngle = a.orthoAngle;
        orthogonal = a.orthogonal;
        perspective = a.perspective;
        rotation = a.rotation;
        tilt = a.tilt;
        vertOffset = a.vertOffset;
        view3D = a.view3D;
        zoom = a.zoom;
        zoomText = a.zoomText;
    }
}

