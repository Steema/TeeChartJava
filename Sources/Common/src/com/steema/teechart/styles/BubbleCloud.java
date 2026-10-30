package com.steema.teechart.styles;

import static com.steema.teechart.drawing.Graphics3D.pointInEllipse;

import com.steema.teechart.IBaseChart;
import com.steema.teechart.PositionUnits;
import com.steema.teechart.Rectangle;
import com.steema.teechart.drawing.Color;
import com.steema.teechart.drawing.Graphics3D;
import com.steema.teechart.drawing.IGraphics3D;
import com.steema.teechart.drawing.Point;
import com.steema.teechart.languages.Language;
import com.steema.teechart.misc.Utils;

/**
 *
 * <p>
 * Title: BubbleCloud class
 * </p>
 *
 * <p>
 * Description: The BubbleCloud Series plots data relatively, displaying bubbles
 * of diminishing size to represent different Series values.
 * </p>
 *
 * <p>
 * Copyright (c) 2005-2019 by Steema Software SL. All Rights Reserved.
 * </p>
 *
 * <p>
 * Company: Steema Software SL
 * </p>
 *
 */
public class BubbleCloud extends Custom3DPalette {

	private static final long serialVersionUID = 1L;
	private int rotation = 0;
    private int separation = 0;
    private PositionUnits separationUnits = PositionUnits.PERCENT;
    Double sizeRatio = 1.2;

    private Point[] iCenters;
    private int[] iTangents;

    private Point iOffset;
    private Rectangle iBounds;
    private Rectangle iRect;
    private float iScale;

    private final double TeePiStep = Math.PI / 180.0;

    /**
     * BubbleCloud Constructor
     */
    public BubbleCloud() {
        this(null);
    }

    /**
     * BubbleCloud Constructor
     *
     * @param c IBaseChart
     */
    public BubbleCloud(IBaseChart c) {
        super(c);

        useAxis = false;
        sizeRatio = 1.2;

        //getMarks().setSoftClip(false);
        //getMarks().setAutoPosition(false);
        getMarks().setAutoSize(true);
        getMarks().setVisible(true);
        getMarks().setTransparent(true);
        getMarks().getFont().setColor(Color.WHITE);
        getMarks().getArrow().setVisible(false);
    }
    
    @Override
    public void assign(Series source) {
            super.assign(source);
            if(source instanceof BubbleCloud)
            {
                this.separationUnits=((BubbleCloud)source).separationUnits;
            }
    }    

    /**
     *
     * @param isEnabled boolean
     */
    @Override
    public void prepareForGallery(boolean isEnabled) {
        super.prepareForGallery(isEnabled);
        getChart().getHeader().setVisible(true);
    }

    /**
     *
     * @param numValues int
     */
    @Override
    protected void addSampleValues(int numValues) {
        super.addSampleValues(numValues);
    }

    /**
     * Defines the ratio size increase between consecutive Bubbles. <br>
     * Default value 1.2
     *
     * @return Double
     */
    public Double getSizeRatio() {
        return sizeRatio;
    }

    /**
     * Defines the ratio size increase between consecutive Bubbles. <br>
     * Default value 1.2
     *
     * @param value Double
     */
    public void setSizeRatio(Double value) {
        sizeRatio = setDoubleProperty(sizeRatio, value);
    }

    /**
     * The angle rotates the Series on a vertical plane. <br>
     * Default value 0.
     *
     * @return int
     */
    public int getRotation() {
        return rotation;
    }

    /**
     * The angle rotates the Series on a vertical plane. <br>
     * Default value 0.
     *
     * @param value int
     */
    public void setRotation(int value) {
        rotation = setIntegerProperty(rotation, value);
    }

    /**
     * Defines the separation according to
     * {@link BubbleCloud#getSeparationUnits}.<br>
     * Default value 0.
     *
     * @return int
     */
    public int getSeparation() {
        return separation;
    }

    /**
     * Defines the separation according to
     * {@link BubbleCloud#getSeparationUnits}.
     * <br>
     * Default value 0.
     *
     * @param value int
     */
    public void setSeparation(int value) {
        separation = setIntegerProperty(separation, value);
    }

    /**
     * Define the unit used to set the Separation.
     * {@link BubbleCloud#getSeparation}. <br>
     * Default value PositionUnits.Percent.
     *
     * @return PositionUnits
     */
    public PositionUnits getSeparationUnits() {
        return separationUnits;
    }

    /**
     * Define the unit used to set the Separation.
     * {@link BubbleCloud#getSeparation}.
     *
     * @param value PositionUnits
     */
    public void setSeparationUnits(PositionUnits value) {
        if (separationUnits != value) {
            separationUnits = value;
            invalidate();
        }
    }

    private Rectangle rectFromCenter(Point p, int aHalfWidth, int aHalfHeight) {
        if (aHalfHeight == 0) {
            aHalfHeight = aHalfWidth;
        }

        return Rectangle.fromLTRB(p.x - aHalfWidth, p.y - aHalfHeight, p.x + aHalfWidth, p.y + aHalfHeight);
    }

    private Rectangle bubbleRect(int valueIndex) {
        Point p = new Point();

        p.x = iCenters[valueIndex].x + iOffset.x;
        p.y = iCenters[valueIndex].y + iOffset.y;

        return rectFromCenter(p, (int) Math.round(radius(valueIndex)), 0);
    }

    private void offsetPoint(Point p, Point aOffset) {
        p.x = p.x + aOffset.x;
        p.y = p.y + aOffset.y;
    }

    private double getYCenter(Rectangle r) {
        return (r.getBottom() + r.getTop()) / 2;
    }

    private double distance(int a, int b) {
        return Utils.hypot(iCenters[a].x - iCenters[b].x, iCenters[a].y - iCenters[b].y);
    }

    private final Rectangle TeeZeroRect = new Rectangle(0, 0, 0, 0);
    private final Point TeeZeroPoint = new Point(0, 0);

    private double calcRadius(int index) {
        if (separationUnits == PositionUnits.PERCENT) {
            return radius(index) * (1 + (separation * 0.01)); // %
        } else {
            return radius(index) + separation; // pixels
        }
    }

    private void calcBounds() {
        int r;
        Point p;

        if (getCount() == 0) {
            iBounds = TeeZeroRect;
        } else {
            r = (int) Math.round(calcRadius(0));
            iBounds = rectFromCenter(iCenters[0], r, 0);

            for (int t = 1; t < getCount() - 1; t++) {
                r = (int) Math.round(calcRadius(t));
                p = iCenters[t];

                iBounds = Rectangle.fromLTRB(Math.min(iBounds.getLeft(), p.x - r), Math.min(iBounds.getTop(), p.y - r),
                        Math.max(iBounds.getRight(), p.x + r), Math.max(iBounds.getBottom(), p.y + r));
            }
        }
    }

    private int circleIntersect(int index) {
        double tmpR, tmp, d;

        tmpR = calcRadius(index);

        for (int t = 0; t < index; t++) {
            d = distance(t, index);

            tmp = calcRadius(t);

            if (d < (tmp + tmpR - 2)) {
                return t;
            }
        }

        return -1;
    }

    private void calcOneCenter(int t, int tmp0, double tmpR0, double tmpR1, double tmpR2) {
        double a, h, d;
        Point p2 = new Point();
        double difX, difY;

        d = distance(tmp0, t - 1);

        if (d == 0) {
            iCenters[t] = iCenters[t - 1];
        } else {
            a = (Math.pow(tmpR0 + tmpR2, 2) - Math.pow(tmpR1 + tmpR2, 2) + Math.pow(d, 2)) / (2 * d);

            difX = (iCenters[t - 1].x - iCenters[tmp0].x);
            difY = (iCenters[t - 1].y - iCenters[tmp0].y);

            p2.x = (int) Math.round(iCenters[tmp0].x + a * difX / d);
            p2.y = (int) Math.round(iCenters[tmp0].y + a * difY / d);

            h = Math.sqrt(Math.abs(Math.pow(tmpR0 + tmpR2, 2) - Math.pow(a, 2)));

            if (iCenters[t] == null) {
                iCenters[t] = new Point();
            }
                
            iCenters[t].x = (int) Math.round(p2.x + h * difY / d);
            iCenters[t].y = (int) Math.round(p2.y - h * difX / d);
        }
    }

    private void calcCenters() {
        double tmpR0, tmpR1, tmpR2;
        int tmp0;
        Point tmpCenter = new Point();
        int n;

        double tmpSin;
        double tmpCos;

        float tmpRot;

        tmpCenter.x = (iRect.getRight() - iRect.getLeft()) / 2;
        tmpCenter.y = (iRect.getBottom() - iRect.getTop()) / 2;

        iCenters = new Point[getCount()];
        iTangents = new int[getCount()];

        if (getCount() > 0) {
            iCenters[0] = tmpCenter;

            tmpR0 = calcRadius(0);
            tmp0 = 0;

            if (getCount() > 1) {
                tmpR1 = calcRadius(1);

                tmpSin = Math.sin(rotation * TeePiStep);
                tmpCos = Math.cos(rotation * TeePiStep);

                tmpRot = (float) (tmpR0 + tmpR1);

                iCenters[1] = new Point();
                iCenters[1].x = (int) (tmpCenter.x + Math.round(tmpSin * tmpRot));
                iCenters[1].y = (int) (tmpCenter.y + Math.round(tmpCos * tmpRot));

                if (getCount() > 2) {
                    int t = 2;

                    do {
                        tmpR2 = calcRadius(t);

                        calcOneCenter(t, tmp0, tmpR0, tmpR1, tmpR2);

                        // Check overlapping
                        n = circleIntersect(t);

                        if (n != -1) {
                            tmpR0 = calcRadius(n);
                            tmp0 = n;

                            calcOneCenter(t, tmp0, tmpR0, tmpR1, tmpR2);
                        }

                        iTangents[t] = tmp0;

                        t++;

                        tmpR1 = tmpR2;
                    } while (t != getCount());
                }
            }
        }
    }

    @Override
    protected void doBeforeDrawValues() {
        Point tmpS = new Point();
        Point tmpC = new Point();

        double HorizScale, VertScale;

        super.doBeforeDrawValues();

        getYValues().setOrder(ValueListOrder.DESCENDING);
        getYValues().sort();

        tmpC.x = (getHorizAxis().iEndPos + getHorizAxis().iStartPos) / 2;
        tmpC.y = (getVertAxis().iEndPos + getVertAxis().iStartPos) / 2;

        tmpS.x = (getHorizAxis().iEndPos - getHorizAxis().iStartPos) / 2;
        tmpS.y = (getVertAxis().iEndPos - getVertAxis().iStartPos) / 2;

        HorizScale = 1;
        VertScale = 0.5;

        iRect = Rectangle.fromLTRB((int) (Math.round(tmpC.x - HorizScale * tmpS.x)),
                (int) (Math.round(tmpC.y - VertScale * tmpS.y)),
                (int) (Math.round(tmpC.x + HorizScale * tmpS.x)),
                (int) (Math.round(tmpC.y + VertScale * tmpS.y)));

        iOffset = TeeZeroPoint;
        iScale = 1;

        calcCenters();
        calcBounds();

        iOffset.y = (int) (getYCenter(iRect) - getYCenter(iBounds));
    }

    @Override
    public int calcXPos(int valueIndex) {
        if (iCenters.length > valueIndex) {
            return (int) (Math.round(iCenters[valueIndex].x * 1.0));
        } else {
            return super.calcXPos(valueIndex);
        }
    }

    @Override
    public int calcYPos(int valueIndex) {
        if (iCenters.length > valueIndex) {
            return (int) (Math.round(iCenters[valueIndex].y * 1.0));
        } else {
            return super.calcYPos(valueIndex);
        }
    }

    private double radius(int valueIndex) {
        double tmpMin, tmpRange, tmp;

        tmpRange = mandatory.getRange();

        if (getCount() < 2) {
            tmp = 1;
        } else {
            tmpMin = (sizeRatio - 1) * tmpRange;

            if ((tmpMin + tmpRange) == 0) {
                tmp = 0;
            } else {
                tmp = Math.max(0, tmpMin + mandatory.getValue(valueIndex)
                        - mandatory.getMinimum()) / (tmpMin + tmpRange);
            }
        }

        return iScale * 0.5 * 0.5 * tmp * Math.min(iRect.getRight()
                - iRect.getLeft(), iRect.getBottom() - iRect.getTop());
    }

    @Override
    protected void drawMark(int valueIndex, String st, SeriesMarksPosition aPosition) {
        float tmpSize;
        int tmpL;
        Color Old;

        if (getMarks().getAutoSize()) {
            tmpL = st.length();

            if (tmpL == 0) {
                tmpSize = 1;
            } else {
                tmpSize = (float) (1.0 * radius(valueIndex) / tmpL);
            }

            if (tmpSize < 1) {
                tmpSize = 1;
            }

            //getMarks().getFont().setSizeFloat = tmpSize;
            getMarks().getFont().setSize((int) tmpSize);
        }

        aPosition.leftTop = iCenters[valueIndex];
        aPosition.leftTop.x = aPosition.leftTop.x - (aPosition.width / 2);

        chart.getGraphics3D().setFont(getMarks().getFont());
        aPosition.leftTop.y = iOffset.y + 8 + aPosition.leftTop.y - (getChart().getGraphics3D().getFontHeight() / 2);  //8 = adjustment

        Old = getMarks().getFont().getColor();

        if (getValueColor(valueIndex) == Old) {
            getMarks().getFont().setColor(Color.BLACK);
        }

        super.drawMark(valueIndex, st, aPosition);

        getMarks().getFont().setColor(Old);
    }

    @Override
    public void drawValue(int valueIndex) {
        Rectangle tmpR;
        IGraphics3D g;

        super.drawValue(valueIndex);

        g = getChart().getGraphics3D();

        Color oldColor = g.getBrush().getColor();
        Color tmpColor = getValueColor(valueIndex);

        g.setPen(getPen());
        //g.setBrush(getBrush());

        if (getBrush().getTransparency() > 0) {
            g.getBrush().setColor(Graphics3D.transparentColor(
                    getBrush().getTransparency(), tmpColor));
        } else {
            g.getBrush().setColor(tmpColor);
        }
            
        tmpR = bubbleRect(valueIndex);

        if (getChart().getAspect().getView3D()) {
            g.ellipse(tmpR, startZ);
        } else {
            g.ellipse(tmpR);
        }

        g.getBrush().setColor(oldColor);
    }

    @Override
    public int clicked(int x, int y) {
        Point p = new Point(x, y);

        if ((iCenters!=null) && (iCenters.length == getCount())) {
            for (int t = getCount() - 1; t == 0; t--) {
                if (pointInEllipse(p, bubbleRect(t))) {
                    return t;
                }
            }
        }

        return -1;
    }

    /**
     * Gets descriptive text.
     *
     * @return String
     */
    @Override
    public String getDescription() {
        return Language.getString("BubbleCloud");
    }
}
