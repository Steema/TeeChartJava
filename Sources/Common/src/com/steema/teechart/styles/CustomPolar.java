package com.steema.teechart.styles;

import com.steema.teechart.drawing.StringAlignment;
import com.steema.teechart.languages.Language;
import com.steema.teechart.drawing.Color;
import com.steema.teechart.Rectangle;
import com.steema.teechart.drawing.ChartFont;
import com.steema.teechart.drawing.ChartPen;
import com.steema.teechart.IBaseChart;
import com.steema.teechart.axis.Axis;
import com.steema.teechart.drawing.IGraphics3D;
import com.steema.teechart.drawing.ChartBrush;
import com.steema.teechart.drawing.DashStyle;
import com.steema.teechart.drawing.Point;
import com.steema.teechart.misc.Utils;
import com.steema.teechart.ChartException;
import com.steema.teechart.drawing.Graphics3D;

/**
 *
 * <p>Title: CustomPolar class</p>
 *
 * <p>Description: CustomPolar Series.</p>
 *
 * <p>Example:
 * <pre><font face="Courier" size="4">
 * series = new com.steema.teechart.styles.Polar(myChart.getChart());
 * series.fillSampleValues(10);
 * series.setCircleLabels(true);
 * series.setCircleLabelsInside(true);
 * series.setClockWiseLabels(true);
 * series.setCircled(true);
 * series.setCircleBackColor(Color.EMPTY);
 * series.getCircleLabelsFont().setColor(Color.NAVY);
 * series.setTransparency(35);
 * series.getBrush().setColor(Color.WHITE);
 * series.getBrush().setSolid(true);
 * series.getBrush().loadImage(ChartSamplePanel.class.getResource(URL_BRUSHPATTERN));
 * series.getBrush().setImageMode(ImageMode.TILE);
 * </font></pre></p>
 *
 * <p>Copyright (c) 2005-2019 by Steema Software SL. All Rights
 * Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 *
 */
public class CustomPolar extends Circular {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected SeriesPointer iPointer;
    protected int iMaxValuesCount = 0;

    private ChartFont font;
    private ChartPen pen;
    private boolean circleLabels = false;
    private ChartFont circleLabelsFont;
    private boolean circleLabelsInside = false;
    private boolean circleLabelsRot = false;
    private ChartPen circlePen;
    private boolean clockWiseLabels = false;
    private boolean closeCircle = true;
    //        private int transparency;
    protected TreatNullsStyle treatnulls = TreatNullsStyle.IGNORE;

    private int oldX, oldY;

    public CustomPolar() {
        this(null);
    }
    
    public void assign(Series source) {
        if (source instanceof CustomPolar) {
            CustomPolar tmp = (CustomPolar)source;
            
            this.getPen().assign(tmp.getPen());
            this.getCirclePen().assign(tmp.getCirclePen());
            this.getCircleLabelsFont().assign(tmp.getCircleLabelsFont());
            this.circleLabels = tmp.circleLabels;
            this.circleLabelsInside = tmp.circleLabelsInside;
            this.circleLabelsRot = tmp.circleLabelsRot;
            this.clockWiseLabels = tmp.clockWiseLabels;
            this.closeCircle = tmp.closeCircle;
            this.treatnulls = tmp.treatnulls;
        }
        super.assign(source);
    }
    
    transient private PointerStyleResolver styleResolver;

    public void setPointerStyleResolver(PointerStyleResolver resolver) {
        this.styleResolver = resolver;
    }

    public void removePointerStyleResolver() {
        this.styleResolver = null;
    }

    public CustomPolar(IBaseChart c) {
        super(c);

        iPointer = new SeriesPointer(chart, this);
        // yValues.Name = Language.getString("RadiusValues");
        circleLabelsFont = new ChartFont(chart);
        circlePen = new ChartPen(chart, Color.BLACK);
    }

    /**
     * The Font characteristics for the labels of a circular TeeChart.<br>
     *
     * @return ChartFont
     */
    public ChartFont getCircleLabelsFont() {
        return circleLabelsFont;
    }

    public Point getCircleCenter() {
        return new Point(getCircleXCenter(), getCircleYCenter());
    }

    protected boolean clickedSegment(Point p, Point tmp, Point old) {
        return (
                (getBrush().getVisible() && Graphics3D.pointInTriangle(p, tmp.x, tmp.y, old.x, old.y)) ||
                (getPen().getVisible() && Graphics3D.pointInLineTolerance(p, tmp, old, 0))
               );
    }

    /**
     * The ValueIndex of the "clicked" point in the Series.<br>
     *
     * @param x int
     * @param y int
     * @return int
     */
    public int clicked(int x, int y) {
        int result = super.clicked(x, y);
        if ((result == 0) && (firstVisible > -1) && (lastVisible > -1)) {
        	result = -1;
        	
            Point p = new Point(x,y);
            Point tmp = new Point(0,0);
            Point old = new Point(0,0);
            //Point center = new Point(iCircleXCenter, iCircleYCenter);
            
            if (chart != null) {
                p = chart.getGraphics3D().calculate2DPosition(x, y, getStartZ());
            }
            
            for (int t = firstVisible; t <= lastVisible; t++) {
                tmp.x = calcXPos(t);
                tmp.y = calcYPos(t);
                if ((tmp.x==x) && (tmp.y==y)) { //clicked right on point
                    return t;
                }
                else if (getPointer().getVisible() && (getPointer().getStyle() != PointerStyle.NOTHING)) {
                    if ((Math.abs(tmp.x - x) < getPointer().getHorizSize()) &&
                        (Math.abs(tmp.y - y) < getPointer().getVertSize())) {
                             return t;
                    }
                }
                else if ((t > firstVisible) && clickedSegment(p, tmp, old)) {
                    return t-1;
                }
                old = tmp;
            }
            
            // Finally, check if point p is over the last segment (only if closeCircle = True)
            if ((result == -1) && getCloseCircle() && (lastVisible > firstVisible)) {
                old.x = calcXPos(firstVisible);
                old.y = calcYPos(firstVisible);
                if (clickedSegment(p, tmp, old)) {
                    return lastVisible;
                }
            }
        }
        return result;
    }

    private Point calcXYPos(int valueIndex, double aRadius) {
        double tmp = getVertAxis().getMaximum() - getVertAxis().getMinimum();
        double tmpDif = vyValues.value[valueIndex] - getVertAxis().getMinimum();

        Point p = new Point();

        if ((tmp == 0) || (tmpDif < 0)) {
            p.x = getCircleXCenter();
            p.y = getCircleYCenter();
        } else {
            double tmpRadius = tmpDif * aRadius / tmp;
            p = angleToPos(Utils.PISTEP * getXValue(valueIndex), tmpRadius,
                           tmpRadius);
        }

        return p;
    }
    
    private Color calcValueColor(int valueIndex) {
      Color result = getValueColor(valueIndex);
      if (isNull(valueIndex) && (treatnulls == TreatNullsStyle.IGNORE)) {
        result = (getColorEach()) ? getDefaultColor(valueIndex) : getColor();        
      }
      return result;
    }

    protected double getXValue(int valueIndex) {
        return vxValues.value[valueIndex];
    }

    private void setGridCanvas(Axis axis) {

        IGraphics3D g = chart.getGraphics3D();
        g.getBrush().setVisible(false);
        //g.BackMode=cbmTransparent;
        g.setPen(axis.getGrid());
        //chart.CheckPenWidth(Pen);
        if (g.getPen().getColor().isEmpty()) {
            g.getPen().setColor(Color.GRAY);
        }
    }

    private void drawYGrid() {

        if (getVertAxis().getGrid().getVisible()) {
            double tmpIncrement = getVertAxis().getCalcIncrement();
            if (tmpIncrement > 0) {
                setGridCanvas(getVertAxis());
                double tmpValue = getVertAxis().getMaximum() / tmpIncrement;
                if ((Math.abs(tmpValue) < Integer.MAX_VALUE) &&
                    (Math.abs((getVertAxis().getMaximum() -
                               getVertAxis().getMinimum()) /
                              tmpIncrement) < 10000)) {
                    if (getVertAxis().getLabels().getRoundFirstLabel()) {
                        tmpValue = tmpIncrement * (int) (tmpValue);
                    } else {
                        tmpValue = getVertAxis().getMaximum();
                    }
                    if (getVertAxis().getLabels().getOnAxis()) {
                        while (tmpValue > getVertAxis().getMaximum()) {
                            tmpValue -= tmpIncrement;
                        } while (tmpValue >= getVertAxis().getMinimum()) {
                            drawRing(tmpValue, getEndZ());
                            tmpValue -= tmpIncrement;
                        }
                    } else {
                        while (tmpValue >= getVertAxis().getMaximum()) {
                            tmpValue -= tmpIncrement;
                        } while (tmpValue > getVertAxis().getMinimum()) {
                            drawRing(tmpValue, getEndZ());
                            tmpValue -= tmpIncrement;
                        }
                    }
                }
            }
        }
    }

    private void drawAngleLabel(double angle, int index) {

        IGraphics3D g = chart.getGraphics3D();

        g.setFont(circleLabelsFont);
        int tmpHeight = g.getFontHeight();
        if (angle >= 360) {
            angle -= 360;
        }

        String tmpSt = getCircleLabel(angle, index);

        int tmpXRad = getXRadius();
        int tmpYRad = getYRadius();

        if (getCircleLabelsInside()) {
            tmpXRad -= Utils.round(g.textWidth("   "));
            tmpYRad -= Utils.round(g.textHeight(tmpSt));
        }

        Point p = angleToPos(angle * PIDEGREE, tmpXRad, tmpYRad);

        angle += getRotationAngle();
        double angleRad = angle * PIDEGREE;

        if (circleLabelsRot) {

            if ((angle > 90) && (angle < 270)) {
                /* right aligned */
                p.x += (int)(0.5 * tmpHeight * Math.sin(angle * PIDEGREE));
                p.y += (int)(0.5 * tmpHeight * Math.cos(angle * PIDEGREE));
            } else {
                /* left aligned */
                p.x -= (int)(0.5 * tmpHeight * Math.sin(angleRad));
                p.y -= (int)(0.5 * tmpHeight * Math.cos(angleRad));
            }
        }

        if (angle >= 360) {
            angle -= 360;
        }
        if (circleLabelsRot) {
            if ((angle > 90) && (angle < 270)) {
                g.setTextAlign(StringAlignment.FAR);
                angle += 180;
            } else {
                g.setTextAlign(StringAlignment.NEAR);
            }
            g.rotateLabel(p.x, p.y, getEndZ(), tmpSt, angle);
        } else {
            if ((angle == 0) || (angle == 180)) {
                p.y -= tmpHeight / 2;
            } else
            if ((angle > 0) && (angle < 180)) {
                p.y -= tmpHeight;
            }
            if ((angle == 90) || (angle == 270)) {
                g.setTextAlign(StringAlignment.CENTER);
            } else {
                if (circleLabelsInside) {
                    if ((angle > 90) && (angle < 270)) {
                        g.setTextAlign(StringAlignment.NEAR);
                    } else {
                        g.setTextAlign(StringAlignment.FAR);
                    }
                } else
                if ((angle > 90) && (angle < 270)) {
                    g.setTextAlign(StringAlignment.FAR);
                } else {
                    g.setTextAlign(StringAlignment.NEAR);
                }
            }

            int tmpWidth = Utils.round(g.textWidth("0") / 2);

            if (angle == 0) {
                p.x += tmpWidth;
            } else if (angle == 180) {
                p.x -= tmpWidth;
            }

            g.textOut(p.x, p.y, getEndZ(), tmpSt);
        }
    }

    private void drawXGrid() {

        //int tmpX;
        //int tmpY;

        if (getHorizAxis().getGrid().getVisible() || circleLabels) {
            double tmpIncrement = getHorizAxis().getIncrement();
            if (tmpIncrement <= 0) {
                tmpIncrement = 10.0;
            }

            setGridCanvas(getHorizAxis());
            int tmpIndex = 0;
            double tmpValue = 0;

            while (tmpValue < 360) {
                if (circleLabels) {
                    drawAngleLabel(tmpValue, tmpIndex);
                }
                if (getHorizAxis().getGrid().getVisible()) {
                    Point p = angleToPos(Utils.PISTEP * tmpValue,
                                         getXRadius(),
                                         getYRadius());
                    chart.getGraphics3D().line(getCircleCenter(), p, getEndZ());
                }
                tmpValue += tmpIncrement;
                tmpIndex++;
            }
        }
    }

    private void drawAxis() {

        drawXGrid();
        drawYGrid();

        int tmp = 0;

        if (chart.getAxes().getVisible()) {
            if (chart.getAxes().getRight().getVisible()) {
                tmp = getCircleXCenter() +
                      chart.getAxes().getRight().getSizeTickAxis();
                chart.getAxes().getRight().draw(tmp,
                                                tmp +
                                                chart.getAxes().getRight().
                                                getSizeLabels(),
                                                getCircleXCenter(), false,
                                                chart.getAxes().getLeft().
                                                getMinimum(),
                                                chart.getAxes().getLeft().
                                                getMaximum(),
                                                chart.getAxes().getLeft().
                                                getIncrement(),
                                                getCircleYCenter() - getYRadius(),
                                                getCircleYCenter());
            }
            if (iMaxValuesCount == 0) {

                Axis l = chart.getAxes().getLeft();

                if (l.getVisible()) {
                    l.internalSetInverted(true);
                    tmp = getCircleXCenter() - l.getSizeTickAxis();
                    l.draw(tmp, tmp - l.getSizeLabels(), getCircleXCenter(), false,
                           getCircleYCenter(), getCircleYCenter() + getYRadius());
                    l.internalSetInverted(false);
                }

                if (chart.getAxes().getTop().getVisible()) {
                    chart.getAxes().getTop().internalSetInverted(true);
                    tmp = getCircleYCenter() -
                          chart.getAxes().getTop().getSizeTickAxis();
                    chart.getAxes().getTop().draw(tmp,
                                                  tmp -
                                                  chart.getAxes().getTop().getSizeLabels(),
                                                  getCircleYCenter(), false,
                                                  l.getMinimum(), l.getMaximum(),
                                                  l.getIncrement(),
                                                  getCircleXCenter() -
                                                  getXRadius(),
                                                  getCircleXCenter());
                    chart.getAxes().getTop().internalSetInverted(false);
                }

                if (chart.getAxes().getBottom().getVisible()) {
                    tmp = getCircleYCenter() +
                          chart.getAxes().getBottom().getSizeTickAxis();
                    chart.getAxes().getBottom().draw(tmp,
                            tmp + chart.getAxes().getBottom().getSizeLabels(),
                            getCircleYCenter(), false,
                            l.getMinimum(), l.getMaximum(), l.getIncrement(),
                            getCircleXCenter(),
                            getCircleXCenter() + getXRadius());
                }
            }
        }
    }

    private void fillTriangle(int aX, int aY, int bX, int bY, int z) {
        DashStyle tmpStyle = chart.getGraphics3D().getPen().getStyle();
        chart.getGraphics3D().getPen().setVisible(false);
        chart.getGraphics3D().triangle(new Point(aX, aY), new Point(bX, bY),
                                       new Point(getCircleXCenter(),
                                                 getCircleYCenter()), z);
        chart.getGraphics3D().getPen().setStyle(tmpStyle);
    }

    private static final int MINANGLE = 10;

    /**
     * The Bottom Axis is used as Angle axis.<br>
     * Gets angle in degrees used to draw the dividing grid lines in
     * anti-clockwise direction.<br>
     * The 0 starting angle is located at rightmost Polar coordinate.
     * Valid increments are between 0 and 359 degree. <br>
     * You can control the grid lines pen by using Chart.Axes.Bottom.Grid.
     *
     * @return double
     */
    public double getAngleIncrement() {
        if (chart == null) {
            return MINANGLE;
        } else {
            double result = getHorizAxis().getIncrement();
            if (result == 0) {
                result = MINANGLE;
            }
            return result;
        }
    }

    /**
     * Gets list of angle values for each polar point.<br>
     * It is a TList object that stores each Polar point Angle value.
     * You can change angle values by using AngleValues.Value[ ] array of
     * doubles.
     *
     * @return ValueList
     */
    public ValueList getAngleValues() {
        return vxValues;
    }

    /**
     * Gets list of radius values for each polar point.<br>
     * It is a TList object that stores each Polar point Radius value.
     * You can change Radius values by using RadiusValues.Value[] array of
     * doubles.
     *
     * @return ValueList
     */
    public ValueList getRadiusValues() {
        return vyValues;
    }

    protected void doAfterDrawValues() {
        if (!chart.getAxes().getDrawBehind()) {
            boolean tmp = false;
            for (int t = chart.getSeriesIndexOf(this) + 1;
                         t < chart.getSeriesCount();
                         t++) {
                if (chart.getSeries(t) instanceof CustomPolar) {
                    tmp = true;
                    break;
                }
            }
            if (!tmp) {
                drawAxis();
            }
        }
        super.doAfterDrawValues();
    }

    private void drawCircle() {
        IGraphics3D g = chart.getGraphics3D();

        //CDI CircleGradient
        if (getCircleBackColor().isEmpty() && calcCircleGradient() == null)
        /* 5.02 */
        {
            g.getBrush().setVisible(false);
        } else {
            g.getBrush().setVisible(true);
            g.getBrush().setSolid(true);
            g.getBrush().setColor(calcCircleBackColor());
            g.getBrush().setGradient(getCircleGradient());

        }

        g.setPen(getCirclePen());
        drawPolarCircle(getCircleWidth() / 2, getCircleHeight() / 2, getEndZ());
    }

    protected void doBeforeDrawValues() {
        boolean first = false;

        for (int t = 0; t < chart.getSeriesCount(); t++) {
            if ((chart.getSeries(t).getActive()) &&
                (chart.getSeries(t) instanceof CustomPolar)) {

                if (chart.getSeries(t) == this) {
                    if (!first) {
                        if (circleLabels && (!circleLabelsInside)) {

                            chart.getGraphics3D().setFont(circleLabelsFont);
                            int tmp = chart.getGraphics3D().getFontHeight() + 2;

                            Rectangle r = new Rectangle();
                            r = chart.getChartRect();

                            r.y += tmp;
                            r.height -= 2 * tmp;
                            tmp = Utils.round(chart.getGraphics3D().
                                    textWidth("360"));
                            r.x += tmp;
                            r.width -= 2 * tmp;

                            chart.setChartRect(r);
                        }
                    }
                    break;
                }

                first = true;
            }
        }

        super.doBeforeDrawValues();

        first = false;

        for (int t = 0; t < chart.getSeriesCount(); t++) {
            if ((chart.getSeries(t).getActive()) &&
                (chart.getSeries(t) instanceof CustomPolar)) {
                if (chart.getSeries(t) == this) {
                    if (!first) {
                        drawCircle();
                        if (chart.getAxes().getDrawBehind()) {
                            drawAxis();
                        }
                    }
                    break;
                }
                first = true;
            }
        }
    }

    protected PointerStyle onGetPointerStyle(int valueIndex,
                                              PointerStyle style) {
        PointerStyle s=style;
        if (styleResolver != null) {
            s = styleResolver.getStyle(this, valueIndex, s);
        }
        return s;
    }

    protected void draw() {
        super.draw();

        if (iPointer.getVisible()) {
            Color tmpColor;

            for (int t = firstVisible; t <= lastVisible; t++) {
                if (treatnulls == TreatNullsStyle.IGNORE || (!isNull(t))) {
                    tmpColor = calcValueColor(t);
                    iPointer.prepareCanvas(chart.getGraphics3D(), tmpColor);
                    PointerStyle tmpStyle = onGetPointerStyle(t, 
                            iPointer.getStyle());
                    iPointer.draw(calcXPos(t), calcYPos(t), tmpColor, tmpStyle);
                }                
            }
        }
    }

    protected void drawLegendShape(IGraphics3D g, int valueIndex,
                                   Rectangle rect) {
        if (getPen().getVisible()) {
            linePrepareCanvas(valueIndex);
            g.horizontalLine(rect.x, rect.getRight(), (rect.y + rect.getBottom()) / 2);
        }
        if (iPointer.getVisible()) {
            Color tmpColor = (valueIndex == -1) ? getColor() :
                             getValueColor(valueIndex);
            iPointer.drawLegendShape(g, tmpColor, rect, getPen().getVisible());
        } else
        if (!getPen().getVisible()) {
            super.drawLegendShape(g, valueIndex, rect);
        }
    }

    protected void drawMark(int valueIndex, String s,
                            SeriesMarksPosition position) {
        position = getMarks().applyArrowLength(position);
        super.drawMark(valueIndex, s, position);
    }

    private void drawPolarCircle(int halfWidth, int halfHeight, int z) {
        if (iMaxValuesCount == 0) {
            chart.getGraphics3D().ellipse(getCircleXCenter() - halfWidth,
                                          getCircleYCenter() - halfHeight,
                                          getCircleXCenter() + halfWidth,
                                          getCircleYCenter() + halfHeight, z);
        } else {
            double tmp = Utils.PISTEP * 360.0 / iMaxValuesCount;

            Point oldP = angleToPos(0, halfWidth, halfHeight);
            chart.getGraphics3D().moveTo(oldP, z);

            for (int t = 0; t <= iMaxValuesCount; t++) {

                Point p = angleToPos(t * tmp, halfWidth, halfHeight);
                if (chart.getGraphics3D().getBrush().getVisible()) {
                    fillTriangle(oldP.x, oldP.y, p.x, p.y, z);
                }
                chart.getGraphics3D().lineTo(p, z);
                oldP = p;
            }
        }
    }

    private void tryFillTriangle(int valueIndex, int x, int y) {
        //Blender tmpBlend;
        //Rectangle R=new Rectangle();

        if (getBrush().getVisible()) {
            chart.setBrushCanvas(getValueColor(valueIndex), getBrush(),
                                 getBrush().getColor());

            /*
                      if (Transparency>0) {
                          R.X  =Math.min(getCircleXCenter(),Math.min(OldX,X));
                          R.y   =Math.min(getCircleYCenter(),Math.min(OldY,y));
             R.width =Math.max(getCircleXCenter(),Math.max(OldX,X))-R.x;
             R.height=Math.max(getCircleYCenter(),Math.max(OldY,Y))-R.y;

                          tmpBlend=new Blender(chart.getGraphics3D(),R);
                          FillTriangle(OldX,OldY,x,y,getStartZ());
                          tmpBlend.DoBlend(Transparency);
                      }
                      else
             */

            //if (transparency>0)
            //    chart.getGraphics3D().SetTransparency(transparency);

            fillTriangle(oldX, oldY, x, y, getStartZ());
            linePrepareCanvas(valueIndex);
        }
    }

    private void doDraw(int valueIndex) {
        int X = calcXPos(valueIndex);
        int Y = calcYPos(valueIndex);

        linePrepareCanvas(valueIndex);
        internalDrawValue(valueIndex, X, Y);
        oldX = X;
        oldY = Y;
    }    

    protected void internalDrawValue(int valueIndex, int x, int y) {
        IGraphics3D gr = chart.getGraphics3D();
        if (valueIndex == firstVisible) {
            gr.moveTo(x, y, getStartZ()); // <-- first point
        }
        else {
            if ((x != oldX) || (y != oldY)) {
                tryFillTriangle(valueIndex, x, y);
                gr.lineTo(x, y, getStartZ());
            }
            if ((valueIndex == lastVisible) && closeCircle)
            {
                if (getColorEach() && bBrush.getVisible()) {
                    getPen().setColor(getValueColor(0));
                }

                oldX = x;
                oldY = y;
                x = calcXPos(0);
                y = calcYPos(0);
                tryFillTriangle(valueIndex, x, y);
                gr.lineTo(x, y, getStartZ());
                x = oldX;
                y = oldY;
            }
        }
    }    
    
    /**
     * Called internally. Draws the "ValueIndex" point of the Series.
     *
     * @param valueIndex int
     */
    public void drawValue(int valueIndex) {
        if (treatnulls == TreatNullsStyle.IGNORE) { 
            doDraw(valueIndex);
        }
        else {
            if (isNull(valueIndex)) {
              int tmp;
              if (treatnulls == TreatNullsStyle.DONOTPAINT) {
                tmp = valueIndex + 1;
                if (tmp == getCount()) {
                    tmp = 0;
                }
                oldX = calcXPos(tmp);
                oldY = calcYPos(tmp);
                chart.getGraphics3D().moveTo(oldX, oldY, getStartZ());
              }
            }
            else doDraw(valueIndex);
        }    
    }

    protected String getCircleLabel(double angle, int index) {
        double tmp = clockWiseLabels ? 360 - angle : angle;
        if (tmp == 360) {
            tmp = 0;
        }
        return Double.toString(tmp) + Language.getString("POLAR_DEGREE_SYMBOL");
    }

    private void linePrepareCanvas(int valueIndex) {
        if (getPen().getVisible()) {

            Color tmpColor;

            if (valueIndex == -1) {
                tmpColor = getColor();
            } else
            if (getPen().getColor().isEmpty()) {
                tmpColor = getValueColor(valueIndex);
            } else {
                tmpColor = getPen().getColor();
            }

            chart.getGraphics3D().setPen(getPen());
            chart.getGraphics3D().getPen().setColor(tmpColor);
        } else {
            chart.getGraphics3D().getPen().setVisible(false);
        }

        //BackMode=cbmTransparent;
    }

    public void prepareForGallery(boolean isEnabled) {
        super.prepareForGallery(isEnabled);
        setCircled(true);
        getHorizAxis().setIncrement(90);
        chart.getAspect().setChart3DPercent(5);
        chart.getAxes().getRight().getLabels().setVisible(false);
        chart.getAxes().getTop().getLabels().setVisible(false);
        chart.getAspect().setOrthogonal(false);
        chart.getAspect().setElevation(360);
        chart.getAspect().setZoom(90);
    }

    public void setChart(IBaseChart c) {
        super.setChart(c);
        if (iPointer != null) {
            iPointer.setChart(chart);
        }
        if ((chart != null) && chart.isDesignTime()) {
            chart.getAspect().setView3D(false);
        }
        if (font != null) {
            font.setChart(chart);
        }
        if (pen != null) {
            pen.setChart(chart);
        }
        if (circlePen != null) {
            circlePen.setChart(chart);
        }
        if (circleLabelsFont != null) {
            circleLabelsFont.setChart(chart);
        }
    }

    public void setColor(Color c) {
        super.setColor(c);
        getPen().setColor(c);
    }

    /**
     * Returns the pixel Screen Horizontal coordinate of the ValueIndex Series
     * value. <br>
     * This coordinate is calculated using the Series associated Horizontal
     * Axis. <br>
     *
     * @param valueIndex int
     * @return int
     */
    public int calcXPos(int valueIndex) {
        return calcXYPos(valueIndex, getXRadius()).x;
    }

    /**
     * Returns the pixel Screen Vertical coordinate of the ValueIndex Series
     * value.<br>
     * This coordinate is calculated using the Series associated Vertical Axis.
     *
     * @param valueIndex int
     * @return int
     */
    public int calcYPos(int valueIndex) {
        return calcXYPos(valueIndex, getXRadius()).y;
    }

    //CDI DrawZone
    public void drawZone(double min, double max, int z) {
        double tmp = getVertAxis().getMaximum() - getVertAxis().getMinimum();

        if (tmp != 0) {
            tmp = (max - getVertAxis().getMinimum()) / tmp;
            drawPolarCircle(Utils.round(tmp * getXRadius()),
                            Utils.round(tmp * getYRadius()), z);
        }

        tmp = getVertAxis().getMaximum() - getVertAxis().getMinimum();

        if (tmp != 0) {
            tmp = (min - getVertAxis().getMinimum()) / tmp;

            int halfWidth = Utils.round(tmp * getXRadius());
            int halfHeight = Utils.round(tmp * getYRadius());

            if (iMaxValuesCount == 0) {
                chart.getGraphics3D().transparentEllipse(getCircleXCenter() -
                        halfWidth,
                        getCircleYCenter() - halfHeight,
                        getCircleXCenter() + halfWidth,
                        getCircleYCenter() + halfHeight,
                        z);
            }
        }
    }

    // Used to draw inside circles (grid) or radar grid lines.
    // Can be also used to custom draw circles at specific values.
    // CDI changed to public
    public void drawRing(double value, int z) {
        double tmp = getVertAxis().getMaximum() - getVertAxis().getMinimum();

        if (tmp != 0) {
            tmp = (value - getVertAxis().getMinimum()) / tmp;
            drawPolarCircle(Utils.round(tmp * getXRadius()),
                            Utils.round(tmp * getYRadius()), z);
        }
    }
    /**
     * Displays labels in 15,30,45 or 90 degree increments.
     *
     * <p>Example:
     * <pre><font face="Courier" size="4">
     * series1.setAngleIncrement(15);
     * </font></pre></p>
     *
     * @param value double
     */
    public void setAngleIncrement(double value) {
        if (chart != null) {
            if (getHorizAxis() == null) {
                recalcGetAxis();
            }
            getHorizAxis().setIncrement(value);
        }
    }

    /**
     * Sets Polar Back Brush.
     *
     * @return ChartBrush
     */
    public ChartBrush getBrush() {
        return bBrush;
    }

    /**
     * Sets CicleLabel properties.<br>
     * Default value: false
     *
     * @return boolean
     */
    public boolean getCircleLabels() {
        return circleLabels;
    }

    /**
     * Sets CicleLabel properties.<br>
     * Default value: false
     *
     * @param value boolean
     */
    public void setCircleLabels(boolean value) {
        circleLabels = setBooleanProperty(circleLabels, value);
    }

    /**
     * The label font characteristics.
     *
     * @return ChartFont
     */
    public ChartFont getFont() {
        if (font == null) {
            font = new ChartFont(chart);
        }
        return font;
    }

    /**
     * Enables/disables the display of the axis labels inside the circle area.
     * <br>
     * Default value: false
     *
     * @return boolean
     */
    public boolean getCircleLabelsInside() {
        return circleLabelsInside;
    }

    /**
     * Enables/disables the display of the axis labels inside the circle area.
     * <br>
     * Default value: false
     *
     * <p>Example:
     * <pre><font face="Courier" size="4">
     * series.setCircleLabelsInside(true);
     * </font></pre></p>
     *
     * @param value boolean
     */
    public void setCircleLabelsInside(boolean value) {
        circleLabelsInside = setBooleanProperty(circleLabelsInside, value);
    }

    /**
     * Places Circle Labels at an angle in line with the Radar/Polar circle at
     * each Label point when true. <br>
     * Default value: false
     *
     * @return boolean
     */
    public boolean getCircleLabelsRotated() {
        return circleLabelsRot;
    }

    /**
     * Places Circle Labels at an angle in line with the Radar/Polar circle at
     * each Label point when true. <br>
     * Default value: false
     *
     * @param value boolean
     */
    public void setCircleLabelsRotated(boolean value) {
        circleLabelsRot = setBooleanProperty(circleLabelsRot, value);
    }

    /**
     * Determines the pen used to draw the outmost circle of all CustomPolar
     * series derived Series.<br>
     *
     * @return ChartPen
     */
    public ChartPen getCirclePen() {
        return circlePen;
    }

    /**
     * Displays the circle labels in a clockwise direction.<br>
     * Default value: false
     *
     * @return boolean
     */
    public boolean getClockWiseLabels() {
        return clockWiseLabels;
    }

    /**
     * Displays the circle labels in a clockwise direction when true.<br>
     * Default value: false
     *
     * <p>Example:
     * <pre><font face="Courier" size="4">
     * series.setClockWiseLabels(true);
     * </font></pre></p>
     *
     * @param value boolean
     */
    public void setClockWiseLabels(boolean value) {
        clockWiseLabels = setBooleanProperty(clockWiseLabels, value);
    }

    /**
     * Draws a Line between the last and first PolarSeries point coordinates.
     * <br>If true, the Polar series polygon is closed.<br>
     * Default value: true
     *
     * @return boolean
     */
    public boolean getCloseCircle() {
        return closeCircle;
    }

    /**
     * Draws a Line between the last and first PolarSeries point coordinates.
     * <br>
     * Default value: true
     *
     * @param value boolean
     */
    public void setCloseCircle(boolean value) {
        closeCircle = setBooleanProperty(closeCircle, value);
    }

    /**
     * Determines Pen used to draw the Line connecting PolarSeries points.<br>
     * Points can be displayed by setting Pointer.Visible to true. <br>
     * You can set the Pen used to draw the circle using CirclePen.
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
     * Pointer contains several properties to control the formatting
     * attributes of Points like Pen, Brush, Draw3D, Visible, etc.<br>
     * It is a subclass of Points series, Line series and all other derived
     * Points series classes like Bubble series, Polar series and Candle series.
     * <br>
     * Each point in a Polar series is drawn using the Pointer properties. <br>
     *
     * @return SeriesPointer
     */
    public SeriesPointer getPointer() {
        return iPointer;
    }

    /**
     * Determines the increment used to draw the ring grid lines.<br>
     * It is the same as accessing Chart.Axes.Left.Increment when using Polar
     * series. <br>
     * You can use RadiusIncrement with Polar series .AngleIncrement to control
     * how gridlines are displayed in Polar charts. <br>
     *
     * @return double
     */
    public double getRadiusIncrement() {
        return (chart == null) ? 0 : getVertAxis().getIncrement();
    }

    /**
     * Sets the increment used to draw the ring grid lines.<br>
     *
     * @param value double
     * @throws TeeChartException
     */
    public void setRadiusIncrement(double value) throws ChartException {
        if (chart != null) {
            getVertAxis().setIncrement(value);
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
        invalidate();
    }
    
    /**
     * Defines how null points are treated.<br>
     *
     * @return TreatNullsStyle
     */
    public TreatNullsStyle getTreatNulls()  {
        return treatnulls;
    }
    
    public void setTreatNulls(TreatNullsStyle value) {
        if  (treatnulls != value) {
            treatnulls = value;
            repaint();
        }
    }    
}

