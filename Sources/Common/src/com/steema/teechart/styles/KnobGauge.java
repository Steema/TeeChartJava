package com.steema.teechart.styles;

import com.steema.teechart.Chart;
import com.steema.teechart.IBaseChart;

import com.steema.teechart.PointDouble;
import com.steema.teechart.Rectangle;
import com.steema.teechart.drawing.ChartBrush;
import com.steema.teechart.drawing.Color;
import com.steema.teechart.drawing.Gradient;
import com.steema.teechart.drawing.GradientDirection;
import com.steema.teechart.drawing.Graphics3D;
import com.steema.teechart.drawing.Point;
import com.steema.teechart.languages.Language;
import com.steema.teechart.misc.Utils;

public class KnobGauge extends CircularGauge {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private boolean axisInside;
    private Rectangle innerEllipse;
    private boolean centerActive = false;
    int oldX = -1, oldY = -1;
    private ChartBrush highLightBrush;

    public KnobGauge() {
        this((IBaseChart) null);
    }

    public KnobGauge(IBaseChart c) {
        super(c);
        setHandDistance(100);
        getHand().getGradient().setVisible(false);
        axisInside = false;
        setLabelsInside(false);
        setGaugeColorPalette(CustomGauge.BluesPalette);
        getFrame().setVisible(false);

    }

    public void assign(Series source) {
        if (source instanceof KnobGauge) {
            KnobGauge tmp = (KnobGauge)source;
            tmp.setChart(((KnobGauge) source).getChart());
            tmp.setAxisInside(((KnobGauge)source).getAxisInside());
        }
        super.assign(source);
    }
    
      protected void adjustCircleRect() {
        int height;

        height = getAxis().getMinorTicks().getVisible() ? getAxis().getMinorTicks().getLength() : 0;
        height = getAxis().getTicks().getVisible() ? getAxis().getTicks().getLength() : height;
        height = getAxis().getLabels().getVisible() ? height
                + getChart().getGraphics3D().fontTextHeight(
                getAxis().getLabels().getFont()) : height;
        rCircleRect.inflate(-height, -height);
        super.adjustCircleRect();
    }

    protected void calcOrigRectangle() {
        if (getCustomBounds().equals(Rectangle.EMPTY)) {
            boolean canMix = true;
            int left, top, right, bottom;
            int maxWidth, maxHeight;
            Rectangle tmpR;
            tmpR = getChart().getChartRect();
            boolean horizontal = tmpR.getWidth() > tmpR.getHeight();

            int seriesCount = getChart().getSeriesCount();
            int seriesIndex = getChart().getSeries().indexOf(this) + 1;

            for (int t = 0; t < seriesCount; ++t) {
                if (!(super.getChart().getSeries(t) instanceof KnobGauge)) {
                    canMix = false;
                    break;
                }
            }
            if (canMix) {
                maxWidth = (int) (tmpR.getWidth() / seriesCount);
                maxHeight = (int) (tmpR.getHeight() / seriesCount);

                if (horizontal) {
                    left = tmpR.getRight() - (maxWidth * seriesIndex);
                    top = tmpR.getTop();

                    bottom = (int) (top + tmpR.getHeight());
                    right = left + maxWidth;
                } else {
                    left = tmpR.getLeft();
                    bottom = tmpR.getBottom() - (maxHeight * (seriesIndex - 1));

                    top = bottom - maxHeight;
                    right = (int) (left + tmpR.getWidth());
                }
                rCircleRect = Rectangle.fromLTRB(left, top, right, bottom);
            }
        }
        reCalcCircleRect();
        super.calcOrigRectangle();
    }
    
   /**
     * Gets descriptive text.
     *
     * @return String
     */
    public String getDescription()
    {
        return Language.getString("GalleryKnobGauge");
    }

    protected void drawAxis(Graphics3D g) {
        if (getAxisInside()) {
            super.drawAxis(g);
        } else {
            double tmp;
            String tmpS;
            double tmpValue, tmpStep, tmpStep2, tmpAngle, tmpAngle2;
            double tmpXRad, tmpYRad, tmpWidth;
            int tmpFontH, t;
            Point P3, P4;

            if (getAxis() != null && getAxis().getVisible()) {                      
                g.arc(iNewRectangle, iStartAngle - 90.0f, calcSweepAngle());

                tmpStep = getAxis().getIncrement();
                if (tmpStep == 0) {
                    tmpStep = 10;
                }

                if (getAxis().getTitle().getVisible()
                        && !isNullOrEmpty(getAxis().getTitle().getCaption())) {
                    g.setFont(getAxis().getTitle().getFont());
                    float width = g.textWidth(getAxis().getTitle().getCaption());
                    float height = g.getFontHeight();
                    int x = iCenter.x
                            - Utils.round(((iNewRectangle.getWidth() / 2) / 2.5)
                            + (width / 2.0));
                    int y = iCenter.y - Utils.round(height / 2.0);
                    g.textOut(x, y, getAxis().getTitle().getCaption());
                }

                if (getAxis().getTicks().getVisible()
                        || getAxis().getLabels().getVisible()) {
                    getAxis().getTicks().setLength(getTicks().getVertSize());
                    getAxis().getTicks().assign(getTicks().getPen());
                    g.setFont(getAxis().getLabels().getFont());
                    g.setPen(getTicks().getPen());

                    tmpXRad = ((iNewRectangle.getWidth() / 2) + getAxis().getTicks().getLength());
                    tmpYRad = ((iNewRectangle.getHeight() / 2) + getAxis().getTicks().getLength());

                    tmpFontH = g.getFontHeight();

                    if (iRange != 0 && tmpStep != 0) {
                        tmpValue = 0;

                        do {
                            tmpAngle = (iStartAngle - 90)
                                    + (tmpValue * getTotalAngle() / iRange);
                            tmp = Utils.PISTEP * tmpAngle;

                            P3 = calcPoint(tmp, iCenter, tmpXRad, tmpYRad);
                            P4 = calcPoint(tmp, iCenter,
                                    (iNewRectangle.getWidth() / 2),
                                    (iNewRectangle.getHeight() / 2));

                            double tmp2 = Utils.PISTEP
                                    * (tmpAngle + (getTicks().getHorizSize() * 0.1));

                            Point P3Plus = calcPoint(tmp2, iCenter, tmpXRad,
                                    tmpYRad);
                            tmp2 = Utils.PISTEP
                                    * (tmpAngle - (getTicks().getHorizSize() * 0.1));
                            Point P3Minus = calcPoint(tmp2, iCenter, tmpXRad,
                                    tmpYRad);

                            tmp2 = Utils.PISTEP
                                    * (tmpAngle + (getTicks().getHorizSize() * 0.2));
                            Point P4Plus = calcPoint(tmp2, iCenter,
                                    (iNewRectangle.getWidth() / 2),
                                    (iNewRectangle.getHeight() / 2));
                            tmp2 = Utils.PISTEP
                                    * (tmpAngle - (getTicks().getHorizSize() * 0.2));
                            Point P4Minus = calcPoint(tmp2, iCenter,
                                    (iNewRectangle.getWidth() / 2),
                                    (iNewRectangle.getHeight() / 2));

                            drawAxisTick(g, P3, P3Plus, P3Minus, P4, P4Plus,
                                    P4Minus);

                            if (getAxis().getLabels().getVisible()) {

                                tmpS = Double.toString(tmpValue + getMinimum());
                                tmpWidth = g.textWidth(tmpS);

                                if (getRotateLabels()) {
                                    if (getLabelsInside()) {
                                        tmpAngle2 = calcAngleFromLength(P4,
                                                tmpWidth) / 2.0;
                                        P3 = calcPoint(tmp - tmpAngle2,
                                                iCenter, tmpXRad
                                                - (tmpFontH / 4)
                                                - getAxis().getTicks().getLength(),
                                                tmpYRad
                                                - (tmpFontH / 4)
                                                - getAxis().getTicks().getLength());
                                    } else {
                                        tmpAngle2 = calcAngleFromLength(P4,
                                                tmpWidth) / 3.0;
                                        P3 = calcPoint(tmp - tmpAngle2,
                                                iCenter,
                                                (iNewRectangle.getWidth() / 2)
                                                + tmpFontH
                                                + getAxis().getTicks().getLength(),
                                                (iNewRectangle.getHeight() / 2)
                                                + tmpFontH
                                                + getAxis().getTicks().getLength());
                                    }
                                    g.rotateLabel(P3.x, P3.y, tmpS,
                                            90 - tmpAngle);
                                } else {
                                    if (getLabelsInside()) {
                                        P3 = calcPoint(tmp, iCenter, tmpXRad
                                                - (tmpFontH / 2), tmpYRad
                                                - (tmpFontH / 2));

                                        tmpAngle2 = tmpAngle - 90;
                                        if (tmpAngle2 < 0) {
                                            tmpAngle2 = tmpAngle2 * -1;
                                        }

                                        P3.y -= Utils.round(tmpFontH
                                                * ((1.0 / 180.0) * tmpAngle2));
                                    } else {
                                        P3 = calcPoint(tmp, iCenter,
                                                (iNewRectangle.getWidth() / 2)
                                                + tmpFontH,
                                                (iNewRectangle.getHeight() / 2)
                                                + tmpFontH);
                                    }

                                    tmpAngle2 = tmpAngle;
                                    if (tmpAngle2 > 180) {
                                        tmpAngle2 = 180 - (tmpAngle2 - 180);
                                    }
                                    if (tmpAngle2 < 0) {
                                        tmpAngle2 *= -1;
                                    }

                                    P3.x -= Utils.round(tmpWidth
                                            * ((1.0 / 180.0) * tmpAngle2));

                                    g.textOut(P3.x, P3.y, tmpS);
                                }
                            }
                            tmpValue += tmpStep;
                        } while ((tmpValue <= iRange && totalAngle <= 360)
                                || (tmpValue < iRange && getTotalAngle() > 360));
                    }
                }

                if (iRange != 0 && getAxis().getMinorTicks().getVisible()
                        && getAxis().getMinorTickCount() > 0) {
                    getAxis().getMinorTicks().setLength(
                            getMinorTicks().getVertSize());
                    getAxis().getMinorTicks().assign(getMinorTicks().getPen());
                    g.setPen(getMinorTicks().getPen());

                    tmpXRad = (iNewRectangle.getWidth() / 2)
                            + getAxis().getMinorTicks().getLength()
                            + getMinorTickDistance();
                    tmpYRad = (iNewRectangle.getHeight() / 2)
                            + getAxis().getMinorTicks().getLength()
                            + getMinorTickDistance();

                    if (tmpStep != 0) {
                        tmpStep2 = tmpStep
                                / (getAxis().getMinorTickCount() + 1);
                        tmpValue = 0;

                        do {
                            for (t = 1; t <= getAxis().getMinorTickCount(); ++t) {
                                tmpAngle = (iStartAngle - 90)
                                        + ((tmpValue + t * tmpStep2)
                                        * getTotalAngle() / iRange);
                                tmp = Utils.PISTEP * tmpAngle;

                                drawAxisMinorTick(
                                        g,
                                        calcPoint(tmp, iCenter, tmpXRad,
                                        tmpYRad),
                                        calcPoint(
                                        tmp,
                                        iCenter,
                                        (iNewRectangle.getWidth() / 2)
                                        + getMinorTickDistance(),
                                        (iNewRectangle.getHeight() / 2)
                                        + getMinorTickDistance()));
                            }
                            tmpValue = tmpValue + tmpStep;
                        } while (tmpValue <= (iRange - tmpStep));
                    }
                }
            }
        }
    }

    protected void drawCenter(Graphics3D g) {
        super.drawCenter(g);
        if (getCenter().getVisible()) {
            Rectangle tmpR;
            tmpR = Rectangle.fromLTRB(getCircleXCenter()
                    - getCenter().getHorizSize(), getCircleYCenter()
                    - getCenter().getVertSize(), getCircleXCenter()
                    + getCenter().getHorizSize(), getCircleYCenter()
                    + getCenter().getVertSize());

            tmpR.inflate(((int) -(tmpR.getWidth() / 15)),
                    ((int) -(tmpR.getHeight() / 15)));

            drawHighLight(g, tmpR, 90, 180, false);
            drawHighLight(g, tmpR, 270, 360, true);

            updateInnerEllipse();
        }
    }
    @Override
    protected void drawColorLines(Graphics3D g) {

        //g.rectangle(iNewRectangle);
        if (getRedLine().getVisible()) {
            getRedLine().drawColorLine(g, calcValue(getRedLineStartValue()),
                    calcValue(getRedLineEndValue()), iNewRectangle, false);
        }
        if (getGreenLine().getVisible()) {
            getGreenLine().drawColorLine(g,
                    calcValue(getGreenLineStartValue()),
                    calcValue(getGreenLineEndValue()), iNewRectangle, false);
        }
    }


    private void drawHighLight(Graphics3D g, Rectangle rect, int startAngle,
            int endAngle, boolean farSide) {
        iPoints.clear();
        boolean oldVisible = g.getPen().getVisible();
        g.getPen().setVisible(false);
        
        Rectangle tmpR = (Rectangle)rect.copy();

        for (int i = startAngle; i <= endAngle; i++) {
            iPoints.add(g.pointFromEllipse(tmpR, i,0));
        }

        tmpR.width = Utils.round(tmpR.getWidth() * 1.25);
        tmpR.height = Utils.round(tmpR.getHeight() * 1.25);
        if (farSide) {
            tmpR.x = Utils.round(tmpR.x - (tmpR.getWidth() * 0.190));
            tmpR.y = Utils.round(tmpR.y - (tmpR.getHeight() * 0.190));
        } else {
            tmpR.x = Utils.round(tmpR.x - (tmpR.getWidth() * 0.010));
            tmpR.y = Utils.round(tmpR.y - (tmpR.getHeight() * 0.010));
        }

        for (int i = endAngle - 20; i >= startAngle + 20; i--) {
            iPoints.add(g.pointFromEllipse(tmpR, i,0));
        }

        g.setBrush(getHighLightBrush());
        PointDouble[] p = new PointDouble[iPoints.size()];
        iPoints.toArray(p);
        g.polygon(p);
        g.getPen().setVisible(oldVisible);
    }

    protected void setValues() {
        super.setValues();
        getCenter().setHorizSize(super.getCircleWidth() / 3);
        getCenter().setVertSize(super.getCircleHeight() / 3);
        getHand().setHorizSize(super.getCircleWidth() / 10);
        getRedLine().setVertSize(getAxis().getTicks().getLength());
        getGreenLine().setVertSize(getAxis().getTicks().getLength());
    }

    private void updateInnerEllipse() {
        innerEllipse = new Rectangle(getCenterPoint().x
                - getCenter().getHorizSize(), getCenterPoint().y
                - getCenter().getVertSize(), getCenter().getHorizSize() * 2,
                getCenter().getVertSize() * 2);

    }

    public boolean getAxisInside() {
        return axisInside;
    }

    public void setAxisInside(boolean value) {
        axisInside = value;
    }

    public Point getCenterPoint() {
        return new Point(getCircleXCenter(), getCircleYCenter());
    }

    public ChartBrush getHighLightBrush() {
        if (highLightBrush == null) {
            highLightBrush = new ChartBrush(this.getChart());
            Gradient grad = highLightBrush.getGradient();
            grad.setVisible(true);
            grad.setStartColor(getCenter().getColor());
            grad.setMiddleColor(Color.fromArgb(255, 253, 245, 230));
            grad.setEndColor(getCenter().getColor());
            grad.setDirection(GradientDirection.BACKDIAGONAL);
            grad.setUseMiddle(true);
        }
        return highLightBrush;
    }

    public boolean isNullOrEmpty(String value) {
        if (value != null) {
            return (value.length() == 0);
        }
        return true;
    }

    public Color hexToColor(String color) {

        int A = Integer.parseInt(color.substring(0, 2));
        int R = Integer.parseInt(color.substring(2, 2));
        int G = Integer.parseInt(color.substring(4, 2));
        int B = Integer.parseInt(color.substring(6, 2));
        Color result = Color.fromArgb(A, R, G, B);
        return result;
    }
}
