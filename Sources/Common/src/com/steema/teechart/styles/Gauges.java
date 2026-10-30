package com.steema.teechart.styles;

import com.steema.teechart.Rectangle;
import com.steema.teechart.languages.Language;
import java.util.Random;
import java.text.DecimalFormat;

import java.util.EventListener;

import com.steema.teechart.drawing.Color;
import com.steema.teechart.drawing.ChartPen;
import com.steema.teechart.drawing.Point;
import com.steema.teechart.IBaseChart;
import com.steema.teechart.axis.Axis;
import com.steema.teechart.drawing.IGraphics3D;
import com.steema.teechart.events.ChangeEvent;
import com.steema.teechart.events.ChangeListener;
import com.steema.teechart.misc.MathUtils;
import com.steema.teechart.misc.Utils;

/**
 *
 * <p>Title: Gauges class</p>
 *
 * <p>Description: Gauges Series.</p>
 *
 * <p>Copyright (c) 2005-2019 by Steema Software SL. All Rights Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 *
 */
public class Gauges extends Circular {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static final int HANDDISTANCE = 30;

    private double fAngle;
    private SeriesPointer fCenter;
    private int fDistance;
    private SeriesPointer fEndPoint;
    private double fMax;
    private double fMin;
    private int fMinorDistance;
    private HandStyle fStyle;
    private ChartPen pen;
    private boolean fLabelsInside;

    transient private Point iCenter;
    private boolean forceCenter = false;
    private Rectangle fContainingRect;

    public Gauges() {
        this((IBaseChart)null);
    }

    public Gauges(IBaseChart c) {
        super(c);

        fStyle = HandStyle.LINE;
        fDistance = HANDDISTANCE;
        setCircled(true);
        setShowInLegend(false);
        fLabelsInside = true;

        fCenter = new SeriesPointer(c, this);
        fCenter.getBrush().setSolid(true);
        fCenter.setColor(Color.BLACK);
        fCenter.setVisible(true);
        fCenter.setStyle(PointerStyle.CIRCLE);
        fCenter.setHorizSize(8);
        fCenter.setVertSize(8);
        fCenter.getGradient().setVisible(true);
        fCenter.getGradient().setStartColor(Color.WHITE);
        fCenter.getGradient().setEndColor(Color.BLACK);

        fEndPoint = new SeriesPointer(c, this);
        fEndPoint.setVisible(false);
        fEndPoint.getBrush().setSolid(true);
        fEndPoint.setColor(Color.WHITE);
        fEndPoint.setStyle(PointerStyle.CIRCLE);
        fEndPoint.setHorizSize(3);
        fEndPoint.setVertSize(3);
        fEndPoint.getGradient().setVisible(false);

        add(0);
        setMaximum(100);
        setTotalAngle(90);
        setRotationAngle(135);
    }

    public void addChangeListener(ChangeListener l) {
        listenerList.add(EventListener.class, (EventListener)(l)); //mm todo
    }

    public void removeChangeListener(ChangeListener l) {
        listenerList.remove(EventListener.class, (EventListener)l);
    }

    protected void fireChange() {
        Object[] listeners = listenerList.getListenerList();
        for (int i = listeners.length - 2; i >= 0; i -= 2) {
            if (listeners[i] == ChangeListener.class) {
                ((ChangeListener) listeners[i + 1]).stateChanged(new ChangeEvent(this));
            }
        }
    }

    private final class LinePoints {
        Point p0, p1, p2, p3;
    }

    private LinePoints calcLinePoints() {
        LinePoints result = new LinePoints();

        double tmp;
        double tmpRange = getMaximum() - getMinimum();
        
        if (tmpRange == 0) {
          tmp = 0.5 * Math.PI;
        }
        else {
          tmp = getTotalAngle() - ((getValue() - getMinimum()) * getTotalAngle() / tmpRange);
          tmp = Utils.PISTEP * (360 - tmp + getRotationAngle());
        }

        result.p1 = MathUtils.calcPoint(tmp, iCenter, getXRadius(), getYRadius());

        int tmpDist = getHandDistance() + sizePointer(fEndPoint);
        if (tmpDist > 0) {
            result.p1 = MathUtils.pointAtDistance(iCenter, result.p1, tmpDist);
        }

        if (fCenter.getVisible() && (fCenter.getStyle() == PointerStyle.CIRCLE)) {
            result.p0 = MathUtils.pointAtDistance(result.p1, iCenter,
                                                  (sizePointer(fCenter) / 2));
        } else {
            result.p0 = iCenter;
        }

        if (getHandStyle() == HandStyle.TRIANGLE) {
        	double XRadius = getCenter().getHorizSize();
        	double YRadius = getCenter().getVertSize();
            tmp = Math.abs(MathUtils.atan2(result.p1.y - result.p0.y, result.p1.x - result.p0.x));
            result.p2 = MathUtils.calcPoint(tmp - (Math.PI / 3), result.p0, XRadius, YRadius);
            result.p3 = MathUtils.calcPoint(tmp + (Math.PI / 3), result.p0, XRadius, YRadius);
        }
        
        return result;
    }

    static private int sizePointer(SeriesPointer aPointer) {
        int result=0;

        if (aPointer.getVisible()) {
            result = 2 * aPointer.getVertSize();
            if (aPointer.getPen().getVisible()) {
                result += aPointer.getPen().getWidth();
            }
        }

        return result;
    }

    private void drawValueLine() {
        LinePoints p = calcLinePoints();

        IGraphics3D g = chart.getGraphics3D();

        g.getPen().assign(getPen());
        g.getBrush().assign(getBrush());
        
        if (getHandStyle() == HandStyle.LINE) {
            g.line(p.p0, p.p1);
        } else {
            g.polygon(new Point[] {p.p1, p.p2, p.p3});
        }

        p.p1 = MathUtils.pointAtDistance(iCenter, p.p1,
                                         -sizePointer(fEndPoint) / 2);

        if (fEndPoint.getVisible()) {
            g.getPen().assign(fEndPoint.getPen());
            g.getGradient().assign(fEndPoint.getGradient());
            g.getBrush().assign(fEndPoint.getBrush());

            fEndPoint.prepareCanvas(g, fEndPoint.getBrush().getColor());
            fEndPoint.draw(p.p1, fEndPoint.getBrush().getColor(),
                           fEndPoint.getStyle());
        }
    }

    /**
     * Line pen for Gauge.
     *
     * @return ChartPen
     */
    public ChartPen getPen() {
        if (pen == null) {
            pen = new ChartPen(chart, Color.BLACK);
        }
        return pen;
    }

    /**
     * Style of Gauge hand.<br>
     * The gauge can display its arrow as a simple line ( hsLine style ) or as
     * a filled triangle ( hsTriangle style ).<br>
     * Default value: hsLine
     *
     * @return HandStyle
     */
    public HandStyle getHandStyle() {
        return fStyle;
    }

    /**
     * Sets the Style of Gauge hand.<br>
     * Default value: hsLine
     *
     * @param value HandStyle
     */
    public void setHandStyle(HandStyle value) {
        if (fStyle != value) {
            fStyle = value;
        }
        repaint();
    }

    /**
     * Controls the size in degrees for the gauge axis.<br>
     * Use together with RotationAngle property.<br>
     * Default value: 90
     *
     * @see Gauges#getRotationAngle
     * @return double
     */
    public double getTotalAngle() {
        return fAngle;
    }

    /**
     * Controls the size in degrees for the gauge axis.<br>
     * Default value: 90
     *
     * @param value double
     */
    public void setTotalAngle(double value) {
        fAngle = setDoubleProperty(fAngle, value);
    }

    /**
     * The amount in pixels that define a gap between the gauge axis and the
     * end of the gauge arrow line.<br>
     * Default value: 30
     *
     * @return int
     */
    public int getHandDistance() {
        return fDistance;
    }

    /**
     * Sets the amount in pixels that define a gap between the gauge axis and the
     * end of the gauge arrow line.<br>
     * Default value: 30
     *
     * @param value int
     */
    public void setHandDistance(int value) {
        fDistance = setIntegerProperty(fDistance, value);
    }

    /**
     * Gets or sets the position of gauge arrow line.<br>
     * Must be a number between gauge Minimum and Maximum. <br>
     * When Value is changed, the gauge arrow is automatically redisplayed at
     * the new position.<br>
     * FullRepaint controls if the whole chart will be repainted or just the
     * gauge arrow line.<br>
     * Default value: 0
     *
     * @return double
     */
    public double getValue() {
        if (getCount() == 0) {
            add(0);
        }
        return mandatory.value[0];
    }

    /**
     * Sets the position of gauge arrow line.<br>
     * Default value: 0
     *
     * @param value double
     */
    public void setValue(double value) {
        if (getValue() != value) {
            mandatory.value[0] = value;
            repaint();
            fireChange();
        }
    }

    /**
     * The minimum value for the gauge.<br>
     * Default value: 0
     *
     * @return double
     */
    public double getMinimum() {
        return fMin;
    }

    /**
     * Sets the minimum value for the gauge.<br>
     * Default value: 0
     * @param value double
     */
    public void setMinimum(double value) {
        fMin = setDoubleProperty(fMin, value);
        setValue(Math.max(getMinimum(), getValue()));
    }

    /**
     * The maximum value for the gauge.<br>
     * Default value: 100
     *
     * @return double
     */
    public double getMaximum() {
        return fMax;
    }

    /**
     * Sets the maximum value for the gauge.<br>
     * Default value: 100
     *
     * @param value double
     */
    public void setMaximum(double value) {
        fMax = setDoubleProperty(fMax, value);
        setValue(Math.min(getMaximum(), getValue()));
    }

    /**
     * Returns a sub-object with properties that control the appearance of a shape at the middle of gauge.
     *
     * @return SeriesPointer
     */
    public SeriesPointer getCenter() {
        return fCenter;
    }

    /**
     * If true, the pie will be centered even when totalAngle is smaller than 360 degrees.<br>
     * Default value: false
     *
     * @return boolean
     */
    public boolean getForceCenter() { return forceCenter; }

    /**
     * If true, the pie will be centered even when totalAngle is smaller than 360 degrees.<br>
     * Default value: false
     *
     * @param value boolean
     */
    public void setForceCenter(boolean value) {
        forceCenter = setBooleanProperty(forceCenter, value);
    }

    // FCenter = value;

    /**
     * Returns a sub-object with properties that control the appearance of a shape at the end of the gauge arrow.
     *
     * @return SeriesPointer
     */
    public SeriesPointer getEndPoint() {
        return fEndPoint;
        //set{FCenter = value;}
    }

    /**
     * Set to a value bigger than zero to display the axis minor ticks (ticks
     * inside normal ticks) displaced from the axis line the specified number
     * of pixels.<br>
     * Default value: 0 pixels
     *
     * @return int
     */
    public int getMinorTickDistance() {
        return fMinorDistance;
    }

    /**
     * Sets the number of pixels to displace the axis minor ticks from the axis
     * line.<br>
     * Default value: 0 pixels
     *
     * @param value int
     */
    public void setMinorTickDistance(int value) {
        fMinorDistance = setIntegerProperty(fMinorDistance, value);
    }

    /**
     * Defines where to display gauge axis labels.
     * Default value: true
     *
     * @return boolean
     */
    public boolean getLabelsInside() {
        return fLabelsInside;
    }

    /**
     * Defines where to display gauge axis labels.
     * Default value: true
     *
     * @param value boolean
     */
    public void setLabelsInside(boolean value) {
        fLabelsInside = setBooleanProperty(fLabelsInside, value);
    }

    protected void addSampleValues(int numValues) {
        Random rnd = new Random();
        setValue(getMinimum() + (getMaximum() - getMinimum()) * rnd.nextDouble());
    }

    protected void draw() {
        double tmp, tmpAngle;
        String tmpS;
        double tmpValue, tmpStep2, tmpXRad, tmpYRad;
        int t;
        Point p3, p4;

        IGraphics3D g = chart.getGraphics3D();

        if (getAxis() != null) { //CDI TF02010075
            iCenter = new Point(g.getXCenter(), g.getYCenter());

            if (getForceCenter()) {
                forceInCenter();
            }

            double iRange = getMaximum() - getMinimum();
            
            if (getAxis().getIncrement() == 0)
            	getAxis().setIncrement(iRange / 10);
            
            double tmpStep = getAxis().getIncrement();

            if (fCenter.getVisible()) {
                g.getPen().assign(fCenter.getPen());
                g.getGradient().assign(fCenter.getGradient());
                g.getBrush().assign(fCenter.getBrush());
                fCenter.prepareCanvas(g, fCenter.getBrush().getColor());
                fCenter.draw(iCenter.x, iCenter.y, fCenter.getBrush().getColor(),
                             fCenter.getStyle());
            }

            if (getAxis().getTicks().getVisible() ||
                getAxis().getLabels().getVisible()) {
                g.getFont().assign(getAxis().getLabels().getFont());
                g.getPen().assign((ChartPen) getAxis().getTicks());

                g.setBackColor(Color.TRANSPARENT);

                tmpXRad = (getXRadius() - getAxis().getTicks().getLength());
                tmpYRad = (getYRadius() - getAxis().getTicks().getLength());

                int tmpFontH = g.getFontHeight();

                if (tmpStep != 0) {
                    tmpValue = getMinimum();

                    do {
                        tmp = getTotalAngle() -
                              ((tmpValue-getMinimum()) * getTotalAngle() / iRange);
                        tmpAngle = 360 - tmp + getRotationAngle();
                        tmp = MathUtils.PISTEP * tmpAngle;

                        p3 = MathUtils.calcPoint(tmp, iCenter, tmpXRad, tmpYRad);
                        p4 = MathUtils.calcPoint(tmp, iCenter, getXRadius(), getYRadius());

                        if (getAxis().getTicks().getVisible()) {
                            g.line(p3, p4);
                        }

                        if (getAxis().getLabels().getVisible()) {
                            tmpS = (new DecimalFormat(getValueFormat())).format(tmpValue);


                            if (!getLabelsInside()) {
                                p3 = MathUtils.calcPoint(tmp, iCenter,
                                               getXRadius()+tmpFontH,
                                               getYRadius()+tmpFontH);
                            }

                            p3.x -= MathUtils.round(g.textWidth(tmpS)*0.5);

                            if (getLabelsInside()) { // 7.0
                                if (tmpAngle > 360)
                                    tmpAngle = tmpAngle - 360;
                                if (tmpAngle < 0)
                                    tmpAngle = 360 + tmpAngle;

                                p3.x+=MathUtils.round((g.textWidth(tmpS)*0.5)*Math.cos(MathUtils.PISTEP*tmpAngle));
                            }
                                if ((tmpAngle>180) && (tmpAngle<360))
                                  p3.y+=MathUtils.round(g.getFontHeight()*Math.sin(MathUtils.PISTEP*tmpAngle));

                            g.textOut(p3.x, p3.y, tmpS);
                        }

                        tmpValue += tmpStep;

                    } while ((getTotalAngle()>=360) && (tmpValue < getMaximum()) ||
                            (getTotalAngle()<360) && (tmpValue<=getMaximum()));
                }
            }

            if (getAxis().getMinorTicks().getVisible() &&
                getAxis().getMinorTickCount() > 0) {
                g.getPen().assign((ChartPen) getAxis().getMinorTicks());

                tmpXRad = getXRadius() - getAxis().getMinorTicks().getLength() -
                          getMinorTickDistance();
                tmpYRad = getYRadius() - getAxis().getMinorTicks().getLength() -
                          getMinorTickDistance();

                if (tmpStep != 0) {
                    tmpStep2 = tmpStep / (getAxis().getMinorTickCount() + 1);
                    tmpValue = getMinimum();

                    do {
                        for (t = 1; t <= getAxis().getMinorTickCount(); ++t) {
                            tmp = getTotalAngle() -
                                  ((tmpValue + t * tmpStep2) * getTotalAngle() /
                                   iRange);
                            tmp = MathUtils.PISTEP *
                                  (360 - tmp + getRotationAngle());

                            g.line(MathUtils.calcPoint(tmp, iCenter, tmpXRad, tmpYRad),
                                    MathUtils.calcPoint(tmp, iCenter,
                                             getXRadius() -
                                             getMinorTickDistance(),
                                             getYRadius() -
                                             getMinorTickDistance()));
                        }
                        tmpValue += tmpStep;
                    } while (tmpValue <= (getMaximum() - tmpStep));
                }
            }

            if (getAxis().getVisible()) {
                drawAxis();
            }
        }

        if (getPen().getVisible()) {
            drawValueLine();
        }
    }

    private void calcContainingRect() {
        IGraphics3D g = chart.getGraphics3D();

        if (!forceCenter)
            fContainingRect = getCircleRect();

        Point tmpCenter = new Point(g.getXCenter(), g.getYCenter());
        int startAngle = getRotationAngle();
        int endAngle = (int)(startAngle - getTotalAngle());

        Point tmpA = MathUtils.calcPoint(Utils.PISTEP * startAngle, tmpCenter, getXRadius(), getYRadius());
        Point tmpB = MathUtils.calcPoint(Utils.PISTEP * endAngle, tmpCenter, getXRadius(), getYRadius());
        fContainingRect = new Rectangle();

        if (tmpA.x < tmpCenter.x) { //tmpA in Left
            if (tmpA.y < tmpCenter.y) { //tmpA in TopLeft quadrant
                if (tmpB.x < tmpCenter.x) { //tmpB in Left
                    if (tmpB.y < tmpCenter.y) { //tmpB in TopLeft quadrant
                        if (getTotalAngle() > 270)
                            fContainingRect = getCircleRect();
                        else {
                            fContainingRect.setLeft(Math.min(tmpA.x, tmpB.x));
                            fContainingRect.setRight(tmpCenter.x);
                            fContainingRect.setTop(Math.min(tmpA.y, tmpB.y));
                            fContainingRect.setBottom(tmpCenter.y);
                        }
                    }
                    else { //tmpB in BottomLeft quadrant
                        fContainingRect.setLeft(Math.min(tmpA.x, tmpB.x));
                        fContainingRect.setRight(getCircleRect().getRight());
                        fContainingRect.setTop(getCircleRect().getTop());
                        fContainingRect.setBottom(getCircleRect().getBottom());
                    }
                }
                else { //tmpB in Right
                    if (tmpB.y < tmpCenter.y) { //tmpB in TopRight quadrant
                        fContainingRect.setLeft(tmpA.x);
                        fContainingRect.setRight(tmpB.x);
                        fContainingRect.setTop(getCircleRect().getTop());
                        fContainingRect.setBottom(tmpCenter.y);
                    }
                    else { //tmpB in BottomRight quadrant
                        fContainingRect.setLeft(tmpA.x);
                        fContainingRect.setRight(getCircleRect().getRight());
                        fContainingRect.setTop(getCircleRect().getTop());
                        fContainingRect.setBottom(tmpB.y);
                    }
                }
            }
            else { //tmpA in BottomLeft quadrant
                if (tmpB.x < tmpCenter.x) { //tmpB in Left
                    if (tmpB.y < tmpCenter.y) { //tmpB in TopLeft quadrant
                        fContainingRect.setLeft(getCircleRect().getLeft());
                        fContainingRect.setRight(tmpCenter.x);
                        fContainingRect.setTop(tmpB.y);
                        fContainingRect.setBottom(tmpA.y);
                    }
                    else { //tmpB in BottomLeft quadrant
                        if (getTotalAngle() > 270)
                            fContainingRect = getCircleRect();
                        else {
                            fContainingRect.setLeft(Math.min(tmpA.x, tmpB.x));
                            fContainingRect.setRight(tmpCenter.x);
                            fContainingRect.setTop(tmpCenter.y);
                            fContainingRect.setBottom(Math.max(tmpA.y, tmpB.y));
                        }
                    }
                }
                else { //tmpB in Right
                    if (tmpB.y < tmpCenter.y) { //tmpB in TopRight quadrant
                        fContainingRect.setLeft(getCircleRect().getLeft());
                        fContainingRect.setRight(tmpB.x);
                        fContainingRect.setTop(getCircleRect().getTop());
                        fContainingRect.setBottom(tmpA.y);
                    }
                    else { //tmpB in BottomRight quadrant
                        fContainingRect.setLeft(getCircleRect().getLeft());
                        fContainingRect.setRight(getCircleRect().getRight());
                        fContainingRect.setTop(getCircleRect().getTop());
                        fContainingRect.setBottom(Math.max(tmpA.y, tmpB.y));
                    }
                }
            }
        }
        else { //tmpA in Right
            if (tmpA.y < tmpCenter.y) { //tmpA in TopRight quadrant
                if (tmpB.x < tmpCenter.x) { //tmpB in Left
                    if (tmpB.y < tmpCenter.y) { //tmpB in TopLeft quadrant
                        fContainingRect.setLeft(getCircleRect().getLeft());
                        fContainingRect.setRight(getCircleRect().getRight());
                        fContainingRect.setTop(Math.min(tmpA.y, tmpB.y));
                        fContainingRect.setBottom(getCircleRect().getBottom());
                    }
                    else { //tmpB in BottomLeft quadrant
                        fContainingRect.setLeft(tmpB.x);
                        fContainingRect.setRight(getCircleRect().getRight());
                        fContainingRect.setTop(tmpA.y);
                        fContainingRect.setBottom(getCircleRect().getBottom());
                    }
                }
                else { //tmpB in Right
                    if (tmpB.y < tmpCenter.y) { //tmpB in TopRight quadrant
                        if (getTotalAngle() > 270)
                            fContainingRect = getCircleRect();
                        else {
                            fContainingRect.setLeft(tmpCenter.x);
                            fContainingRect.setRight(Math.max(tmpA.x, tmpB.x));
                            fContainingRect.setTop(Math.min(tmpA.y, tmpB.y));
                            fContainingRect.setBottom(tmpCenter.y);
                        }
                    }
                    else { //tmpB in BottomRight quadrant
                        fContainingRect.setLeft(tmpCenter.x);
                        fContainingRect.setRight(getCircleRect().getRight());
                        fContainingRect.setTop(tmpA.y);
                        fContainingRect.setBottom(tmpB.y);
                    }
                }
            }
            else { //tmpA in BottomRight quadrant
                if (tmpB.x < tmpCenter.x) { //tmpB in Left
                    if (tmpB.y < tmpCenter.y) { //tmpB in TopLeft quadrant
                        fContainingRect.setLeft(getCircleRect().getLeft());
                        fContainingRect.setRight(tmpA.x);
                        fContainingRect.setTop(tmpB.y);
                        fContainingRect.setBottom(getCircleRect().getBottom());
                    }
                    else { //tmpB in BottomLeft quadrant
                        fContainingRect.setLeft(tmpB.x);
                        fContainingRect.setRight(tmpA.x);
                        fContainingRect.setTop(tmpCenter.y);
                        fContainingRect.setBottom(getCircleRect().getBottom());
                    }
                }
                else { //tmpB in Right
                    if (tmpB.y < tmpCenter.y) { //tmpB in TopRight quadrant
                        fContainingRect.setLeft(getCircleRect().getLeft());
                        fContainingRect.setRight(Math.max(tmpA.x, tmpB.x));
                        fContainingRect.setTop(getCircleRect().getTop());
                        fContainingRect.setBottom(getCircleRect().getBottom());
                    }
                    else { //tmpB in BottomRight quadrant
                        if (getTotalAngle() > 270)
                            fContainingRect = getCircleRect();
                        else {
                            fContainingRect.setLeft(tmpCenter.x);
                            fContainingRect.setRight(Math.max(tmpA.x, tmpB.x));
                            fContainingRect.setTop(tmpCenter.y);
                            fContainingRect.setBottom(Math.max(tmpA.y, tmpB.y));
                        }
                    }
                }
            }
        }

        //To check the rectangle calculated above you can draw it with the lines below.
        /*ChartBrush tmpBrush = new ChartBrush(chart, Color.red);
        tmpBrush.setTransparency(50);
        g.rectangle(tmpBrush, fContainingRect);/**/
    }

    private void forceInCenter() {
        calcContainingRect();

        iCenter.x += ((getCircleRect().getRight() - fContainingRect.getRight()) / 2) - ((fContainingRect.getLeft() - getCircleRect().getLeft()) / 2);
        iCenter.y += ((getCircleRect().getBottom() - fContainingRect.getBottom()) / 2) - ((fContainingRect.getTop() - getCircleRect().getTop()) / 2);
    }

    private void drawAxis() {
        IGraphics3D g = chart.getGraphics3D();
        g.getPen().assign(getAxis().getAxisPen());

        Rectangle tmpR = getCircleRect();

        if (forceCenter) {
            tmpR.offset(((getCircleRect().getRight() - fContainingRect.getRight()) / 2) - ((fContainingRect.getLeft() - getCircleRect().getLeft()) / 2),
                    ((getCircleRect().getBottom() - fContainingRect.getBottom()) / 2) - ((fContainingRect.getTop() - getCircleRect().getTop()) / 2));
        }

        g.arc(tmpR,180-getRotationAngle(),getTotalAngle());
    }

    private Axis getAxis() {
        return getVertAxis();
    }

    public void setChart(IBaseChart c) {
        super.setChart(c);
        if (fCenter != null) {
            fCenter.setChart(c);
        }
        if (fEndPoint != null) {
            fEndPoint.setChart(c);
        }
        if ((chart != null)) {
            chart.getAspect().setView3D(false);
        }
    }

    public void prepareForGallery(boolean isEnabled) {
        super.prepareForGallery(isEnabled);
        chart.getAspect().setChart3DPercent(0);
        chart.getAxes().getLeft().getLabels().setVisible(false);
        getCenter().setVertSize(3);
        getCenter().setHorizSize(3);
        getPen().setColor(Color.BLUE);
        setHandDistance(5);
        setValue(70);
    }

    /**
     * Gets descriptive text.
     *
     * @return String
     */
    public String getDescription() {
        return Language.getString("GalleryGauge");
    }
}


