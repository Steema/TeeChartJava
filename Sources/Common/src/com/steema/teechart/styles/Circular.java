package com.steema.teechart.styles;


import com.steema.teechart.languages.Language;
import com.steema.teechart.Rectangle;
import com.steema.teechart.drawing.Gradient;
import com.steema.teechart.Aspect;
import com.steema.teechart.drawing.Graphics3D;
import com.steema.teechart.drawing.IGraphics3D;
import com.steema.teechart.drawing.ChartBrush;
import com.steema.teechart.drawing.Point;
import com.steema.teechart.IBaseChart;
import com.steema.teechart.drawing.Color;
import com.steema.teechart.axis.Axis;
import com.steema.teechart.misc.Utils;

/**
 *
 * <p>Title: Circular class</p>
 *
 * <p>Description: Circled Series.</p>
 *
 * <p>Copyright (c) 2005-2019 by Steema Software SL. All Rights
 * Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 *
 */
public class Circular extends Series {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
     * Represents the static finalant pi / 180.
     */
    public static final double PIDEGREE = Math.PI / 180;

    private boolean uniqueCustomRadius = true;
    private boolean autoPenColor =  true;
    private boolean circled;
    private int rotationAngle;
    private int customXRadius;
    private int customYRadius;
    protected int circleWidth;
    protected int circleHeight;
    protected int iXRadius;
    protected int iYRadius;
    private Color circleBackColor = Color.EMPTY;
    private Gradient circleGradient;

    static private Aspect iBack3D; // global to all instances

    protected int iCircleXCenter;
    protected int iCircleYCenter;
    protected Rectangle rCircleRect;
    protected double rotDegree;
    protected boolean iAdjustCircleForMarks;

    public Circular() {
        this((IBaseChart)null);
    }

    public Circular(IBaseChart c) {
        super(c);
        readResolve();
    }

    public void assign(final Series source) {
        if (source instanceof Circular) {
            Circular sourceCircular = (Circular)source;
            this.circled = sourceCircular.circled;
            this.rotationAngle = sourceCircular.rotationAngle;
            this.rotDegree = sourceCircular.rotDegree;
            this.customXRadius = sourceCircular.customXRadius;
            this.customYRadius = sourceCircular.customYRadius;
            this.uniqueCustomRadius = sourceCircular.uniqueCustomRadius;
            this.autoPenColor = sourceCircular.autoPenColor;
            this.circleBackColor = sourceCircular.circleBackColor;
            this.circleGradient.assign(sourceCircular.circleGradient);
            this.iAdjustCircleForMarks = sourceCircular.iAdjustCircleForMarks;
        }
        
        super.assign(source);
    }
    
    protected Object readResolve() {
    	super.readResolve();
    	
        useAxis = false;
        calcVisiblePoints = false; // always draw all points
        vxValues.name = Language.getString("ValuesAngle");
        iAdjustCircleForMarks=true;
        
        return this;
    }
    
    protected void adjustCircleRect() {

        Rectangle r = rCircleRect;
        if ((r.width % 2) == 1) {
            r.width--;
        }
        if ((r.height % 2) == 1) {
            r.height--;
        }
        if (r.width < 4) {
            r.width = 4;
        }
        if (r.height < 4) {
            r.height = 4;
        }
        circleWidth = r.width;
        circleHeight = r.height;
        Point tmp = rCircleRect.center();
        iCircleXCenter = tmp.x;
        iCircleYCenter = tmp.y;
    }
    
    protected void reCalcCircleRect() 
    {
      adjustCircleRect();

      if (getMarks().getVisible() && iAdjustCircleForMarks && !((this instanceof Pie) && ((Pie)this).getMarksPie().getInsideSlice()))
          adjustCircleMarks();

      if (circled) calcCircledRatio();
      calcRadius();
    }
    protected Color calcCircleBackColor() {
        Color result = circleBackColor;
        if (result.isEmpty()) {
            if (chart.getPrinting()) {
                result = Color.WHITE;
            } else
            if (!chart.getWalls().getBack().getTransparent()) {
                result = getColor();
            }
        }

        if (result.isEmpty()) {
            result = chart.getPanel().getColor();
        }
        return result;
    }

    //CDI CircleGradient
    protected Gradient calcCircleGradient() {
        Gradient result = circleGradient;
        return result;
    }

    protected void calcRadius() {
        if (customXRadius != 0) {
            iXRadius = customXRadius;
            circleWidth = 2 * iXRadius;
        } else {
            iXRadius = circleWidth / 2;
        }

        if (customYRadius != 0) {
            iYRadius = customYRadius;
            circleHeight = 2 * iYRadius;
        } else {
            iYRadius = circleHeight / 2;
        }

        rCircleRect.x = iCircleXCenter - iXRadius;
        rCircleRect.width = 2 * iXRadius;
        rCircleRect.y = iCircleYCenter - iYRadius;
        rCircleRect.height = 2 * iYRadius;
    }

    static private double adjustRatio(double aRatio, IBaseChart c) {

        int tmpH = c.getScreenHeight();
        int tmpW = c.getScreenWidth();

        double result = aRatio;

        if (tmpH != 0) {
            double tmpRatio = (1.0 * tmpW / tmpH);
            if (tmpRatio != 0) {
                result = 1.0 * aRatio / tmpRatio;
            }
        }
        return result;
    }

    void setCircleRect() {
       rCircleRect = (Rectangle)chart.getChartRect().copy();
    }

    private void calcCircledRatio() {
        int dif;

        double ratio = adjustRatio(1.0 * chart.getScreenWidth() /
                                   chart.getScreenHeight(),
                                   chart);
        calcRadius();

        if (Utils.round(ratio * iYRadius) < iXRadius) {
            dif = (iXRadius - Utils.round(ratio * iYRadius));
            rCircleRect.x += dif;
            rCircleRect.width -= 2 * dif;
        } else {
            dif = (iYRadius - Utils.round(1.0 * iXRadius / ratio));
            rCircleRect.y += dif;
            rCircleRect.height -= 2 * dif;
        }

        adjustCircleRect();
    }

    private void adjustCircleMarks() {
        int tmpFrame = getMarks().getCallout().getLength();

        if (getMarks().getPen().getVisible()) {
            tmpFrame += Utils.round(2 * getMarks().getPen().getWidth());
        }

        chart.getGraphics3D().setFont(getMarks().getFont());

        int tmpH = chart.getGraphics3D().getFontHeight() + tmpFrame;

        rCircleRect.y += tmpH;
        rCircleRect.height -= 2 * tmpH;

        int tmpW = Utils.round(maxMarkWidth() +
                                   chart.getGraphics3D().textWidth(Language.getString("CharForHeight")) + tmpFrame);

        rCircleRect.x += tmpW;
        rCircleRect.width -= 2 * tmpW;
        adjustCircleRect();
    }

    //CDI base method called last to initialise Radius values
    protected void doBeforeDrawValues() {
        super.doBeforeDrawValues();
        setCircleRect();
        reCalcCircleRect();
    }
    
    public int clicked(int x, int y) {
    	
    	if (Graphics3D.pointInEllipse(new Point(x, y), rCircleRect)) return 0;
    	else return -1;
    }
    
    protected void prepareLegendCanvas(IGraphics3D g, int valueIndex,
                                       Color backColor,
                                       ChartBrush aBrush) {
        backColor = calcCircleBackColor();
    }

    public void setActive(boolean value) {
        super.setActive(value);
        setParentProperties(!bActive);
    }

    // Trick (due to .Net delayed GC)
    public void onDisposing() {
        setParentProperties(true);
    }

    public void setChart(IBaseChart value) {
        if (value == null) {
            setParentProperties(true);
        }
        if (value != chart) {
            super.setChart(value);
            if (chart != null) {
                setParentProperties(false);
            }
        }
    }

    protected void setParentProperties(boolean enableParentProps) {

        if (chart != null) {

            IGraphics3D g = chart.getGraphics3D();
            boolean tmp = (g == null) ? true : !g.getSupportsFullRotation();

            if (tmp) {
                if (enableParentProps) {
                    if (iBack3D != null) {
                        chart.getAspect().assign(iBack3D);
                    }
                    iBack3D = null;
                } else
                if (iBack3D == null) {
                    iBack3D = new Aspect();

                    Aspect a = chart.getAspect();
                    iBack3D.assign(a);

                    if (a.getOrthogonal()) {
                        a.setOrthogonal(false);
                        a.setRotation(360);
                        a.setElevation(315);
                        a.setPerspective(0);
                    }
                    a.setTilt(0);
                }
            }
        }
    }

    /**
     * The angle of Chart rotation.<br>
     * The RotationAngle can be a valid integer number between 0 and 359.<br>
     * This angle can be changed by code to rotate the Pie (or Polar). <br>
     * Default value: 0
     *
     * @return int
     */
    public int getRotationAngle() {
        return rotationAngle;
    }

    /**
     * Sets angle of Chart rotation.<br>
     * Default value: 0<br>
     *
     * <p>Example:
     * <pre><font face="Courier" size="4">
     * pieSeries = new com.steema.teechart.styles.Pie(myChart.getChart());
     * pieSeries.getMarks().setVisible(true);
     * pieSeries.getMarks().setStyle(MarksStyle.LABELPERCENT);
     * pieSeries.fillSampleValues(5);
     * pieSeries.setAngleSize(180);
     * pieSeries.setRotationAngle(90);
     * </font></pre></p>
     *
     * @param value int
     */
    public void setRotationAngle(int value) {
        rotationAngle = setIntegerProperty(rotationAngle, value % 360);
        rotDegree = rotationAngle * PIDEGREE;

    }

    /**
     * Returns the exact Screen position for a given pair of Angle and Radius
     * values.
     *
     * @param angle double
     * @param aXRadius double
     * @param aYRadius double
     * @return Point
     */
    public Point angleToPos(double angle, double aXRadius, double aYRadius) {
        double tmpSin = Math.sin(rotDegree + angle);
        double tmpCos = Math.cos(rotDegree + angle);
        return new Point(iCircleXCenter + Utils.round(aXRadius * tmpCos),
                         iCircleYCenter - Utils.round(aYRadius * tmpSin));
    }

    public boolean associatedToAxis(Axis a) {
        return true;
    }

    static private final double HALFPI = 0.5 * Math.PI;

    /**
     * Returns the angle from the XY point parameter to the circle center.
     *
     * @param x int
     * @param y int
     * @return double
     */
    public double pointToAngle(int x, int y) {
        double result;
        if ((x - iCircleXCenter) == 0) {
            if (y > iCircleYCenter) {
                result = -HALFPI;
            } else {
                result = HALFPI;
            }
        } else
        if ((iYRadius == 0) || (iYRadius == 0)) {
            result = 0;
        } else {
            result = Utils.atan2(((double) (iCircleYCenter - y) /
                                      (double) iYRadius),
                                     ((double) (x - iCircleXCenter) /
                                      (double) iXRadius));
        }
        if (result < 0) {
            result += 2.0 * Math.PI;
        }
        result -= rotDegree;
        if (result < 0) {
            result += 2.0 * Math.PI;
        }
        return result;
    }

    /**
     * Returns the radius from XY point to the circle center.
     *
     * @param x int
     * @param y int
     * @return double
     */
    public double pointToRadius(int x, int y) {
        if (getVertAxis() != null) {
            double range = getVertAxis().getMaximum() -
                           getVertAxis().getMinimum();
            if (range == 0.0) {
                return 0.0;
            } else {
                double dx = x - iCircleXCenter;
                double dy = y - iCircleYCenter;
                dx *= range / (double) iXRadius;
                dy *= range / (double) iYRadius;
                return Math.sqrt(dx * dx + dy * dy) + getVertAxis().getMinimum();
            }
        } else {
            return 0.0;
        }
    }

    /**
     * Returns the angle by which the Chart is rotated.
     *
     * @param angle int
     */
    public void rotate(int angle) {
        setRotationAngle((rotationAngle + angle) % 360);
    }

    /**
     * Returns the exact horizontal size of the ellipse's radius in pixels.<br>
     * The ellipse XRadius can be set to a fixed number of pixels by using
     * this method. Circled series.Circled controls whether both radii must
     * be proportional to the Screen X/Y ratio.
     *
     * @return int
     */
    public int getXRadius() {
        return iXRadius;
    }

    /**
     * Returns the exact vertical size of the ellipse's radius in pixels.<br>
     * The ellipse YRadius can be set to a fixed number of pixels by using this
     * method. Circled series .Circled controls whether both radii must be
     * proportional to the Screen X/Y ratio.
     *
     * @return int
     */
    public int getYRadius() {
        return iYRadius;
    }

    /**
     * Returns the exact horizontal position of ellipse's center in pixels.<br>
     * Run-time and read only.
     * The ellipse's radius is determined by Circled series.XRadius and YRadius.
     * The AngleToPoint function converts from angles to X and Y Screen
     * coordinates. The PointToAngle function converts from XY Screen positions
     * to angles.
     *
     * @return int
     */
    public int getCircleXCenter() {
        return iCircleXCenter;
    }

    /**
     * Returns the exact vertical position of the ellipse's center in pixels.
     * <br>
     * The ellipse's radius is determined by Circled series.XRadius and YRadius.
     * The AngleToPoint function converts from angles to X and Y Screen
     * coordinates. The PointToAngle function converts from XY Screen positions
     * to angles.
     *
     * @return int
     */
    public int getCircleYCenter() {
        return iCircleYCenter;
    }

    /**
     * Returns the width of the bounding Circle.
     *
     * @return int
     */
    public int getCircleWidth() {
        return circleWidth;
    }

    /**
     * Returns the height of the bounding Circle.
     *
     * @return int
     */
    public int getCircleHeight() {
        return circleHeight;
    }

    /**
     * Returns the rectangle that bounds the circle.<br>
     * eg. Pie Series, in its default position displacement of elevevation,
     * rotation ,etc not applied.
     *
     * @return Rectangle
     */
    public Rectangle getCircleRect() {
        return rCircleRect;
    }
    
    /**
     * When true TeeChart will calculate colour from slice colour. Default
     * value: true.
     *
     * @return boolean
     */
    public boolean getAutoPenColor() {
        return autoPenColor;
    }

    /**
     * When true TeeChart will calculate colour from slice colour.
     *
     * @param value boolean
     */
    public void setAutoPenColor(boolean value) {
        autoPenColor = setBooleanProperty(autoPenColor, value);
    }
    
    protected boolean shouldSerializeCircleBackColor() {
        return!circleBackColor.isEmpty();
    }

    /**
     * Determines the color to fill the ellipse.<br>
     * Setting it to Color.EMPTY indicates the CircledSeries to use to
     * tChart.getPanel().getColor() color.
     *
     * @return Color
     */
    public Color getCircleBackColor() {
        return circleBackColor;
    }

    /**
     * Determines the color to fill the ellipse.<br>
     *
     * @param value Color
     */
    public void setCircleBackColor(Color value) {
        if (bBrush.getTransparency() != 0) {
            circleBackColor = value.transparentColor(bBrush.getTransparency());
        } else {
            circleBackColor = setColorProperty(circleBackColor, value);
        }
    }

    //CDI CircleGradient
    /**
     * Determines the Gradient which fills the ellipse.<br>
     * Default value: null
     *
     * <p>Example:
     * <pre><font face="Courier" size="4">
     * series = new com.steema.teechart.styles.Polar(myChart.getChart());
     * series.fillSampleValues(20);
     * series.setCircled(true);
     * series.getCircleGradient().setDirection(GradientDirection.RADIAL);
     * series.getCircleGradient().setStartColor(Color.WHITE);
     * series.getCircleGradient().setEndColor(Color.DARK_GRAY);
     * series.getCircleGradient().setRadialX(100);
     * series.getCircleGradient().setRadialY(-100);
     * series.getCircleGradient().setVisible(true);
     * series.getCirclePen().setColor(Color.NAVY);
     * series.getCirclePen().setStyle(DashStyle.DOT);
     * series.getCirclePen().setWidth(2);
     * </font></pre></p>
     *
     * @return Gradient
     */
    public Gradient getCircleGradient() {
        if (circleGradient == null) {
            circleGradient = new Gradient(chart);
        }
        return circleGradient;
    }

    /**
     * The CirleSeries as elliptical or circular.<br>
     * Default value: false
     *
     * @return boolean
     */
    public boolean getCircled() {
        return circled;
    }

    /**
     * Sets CirleSeries as elliptical or circular.<br>
     * Default value: false
     *
     * @param value boolean
     */
    public void setCircled(boolean value) {
        circled = setBooleanProperty(circled, value);
    }

    private void SetOtherCustomRadius(Boolean isXRadius, int value)
    {
    	if (chart != null) {
            for (int t = 0; t < chart.getSeriesCount(); t++) {

                ISeries s = chart.getSeries(t);

                if (sameClass(s)) {
                    if (isXRadius)
                        ((Circular)s).customXRadius = value;
                      else
                        ((Circular)s).customYRadius = value;
                }
            }
        }
    }
    
    /**
     * The ellipse's horizontal radius in pixels.<br>
     * Default value: 0
     *
     * @return int
     */
    public int getCustomXRadius() {
        return customXRadius;
    }

    /**
     * Sets ellipse's horizontal radius in pixels.<br>
     * Default value: 0
     *
     * @param value int
     */
    public void setCustomXRadius(int value) {
        customXRadius = setIntegerProperty(customXRadius, value);
        
        if (uniqueCustomRadius) 
        	SetOtherCustomRadius(true, value);
    }

    /**
     * The ellipse's vertical radius in pixels.<br>
     * Default value: 0
     *
     * @return int
     */
    public int getCustomYRadius() {
        return customYRadius;
    }

    /**
     * Sets ellipse's vertical radius in pixels.<br>
     * Default value: 0
     *
     * @param value int
     */
    public void setCustomYRadius(int value) {
        customYRadius = setIntegerProperty(customYRadius, value);
        
        if (uniqueCustomRadius) 
        	SetOtherCustomRadius(false, value);
    }
    
    public Boolean getUniqueCustomRadius()
    {
      return uniqueCustomRadius;
    }
    
    public void setUniqueCustomRadius(Boolean value)
    {
    	uniqueCustomRadius = setBooleanProperty(uniqueCustomRadius, value);
    	
    	if (chart != null) {
            for (int t = 0; t < chart.getSeriesCount(); t++) {

                ISeries s = chart.getSeries(t);

                if (sameClass(s)) {
                	((Circular)s).uniqueCustomRadius = value;
                }
            }
        }
    }
}

