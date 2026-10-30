package com.steema.teechart.styles;

import com.steema.teechart.drawing.Color;
import com.steema.teechart.drawing.DashStyle;
import com.steema.teechart.drawing.ChartPen;
import com.steema.teechart.drawing.IGraphics3D;
import com.steema.teechart.IBaseChart;
import java.util.Random;
import com.steema.teechart.misc.Utils;

/**
 *
 * <p>Title: CustomBox class</p>
 *
 * <p>Description: CustomBox Series.</p>
 *
 * <p>Copyright (c) 2005-2019 by Steema Software SL. All Rights Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 *
 */
public class CustomBox extends Points {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected double dPosition;
    protected boolean iVertical = true;

    private SeriesPointer extrOut;
    private ChartPen medianPen;
    private SeriesPointer mildOut;
    private double whiskerLength = 1.5;
    private ChartPen whiskerPen;

    private boolean useCustomValues = false;
    private double median;
    private double quartile1;
    private double quartile3;
    private double innerFence1;
    private double innerFence3;
    private double outerFence1;
    private double outerFence3;
    private double adjacentPoint1;
    private double adjacentPoint3;

    public CustomBox() {
        this(null);
    }

    public CustomBox(IBaseChart c) {
        super(c);

        allowSinglePoint = false;
        calcVisiblePoints = false;
        vxValues.name = "";
        vyValues.name = "Samples";
        marks.setDefaultVisible(false);
        marks.getCallout().setDefaultLength(0);

        point.setDraw3D(false);
        point.getPen().setWidth(1);
        point.setVertSize(15);
        point.setHorizSize(15);
        point.getBrush().setColor(Color.WHITE);

        getMildOut().getBrush().setColor(Color.EMPTY);
        getMildOut().setStyle(PointerStyle.CIRCLE);
        getExtrOut().getBrush().setColor(Color.EMPTY);
        getExtrOut().setStyle(PointerStyle.STAR);
    }

    public void assign(Series source) {
        if (source instanceof CustomBox) {
            CustomBox tmp = (CustomBox)source;
            this.extrOut.assign(tmp.extrOut);
            this.mildOut.assign(tmp.mildOut);
            this.medianPen.assign(tmp.medianPen);
            this.whiskerPen.assign(tmp.whiskerPen);
            this.dPosition = tmp.dPosition;
            this.whiskerLength = tmp.whiskerLength;
            this.useCustomValues = tmp.useCustomValues;
        }
        super.assign(source);
    }

    /**
     * Defines the whisker length as a function of the inter-quartike range
     * (IQR).<br>
     * Default value: 1.5
     *
     * @return double
     */
    public double getWhiskerLength() {
        return whiskerLength;
    }

    /**
     * Sets the whisker length as a function of the inter-quartike range
     * (IQR).<br>
     * Default value: 1.5D
     *
     * @param value double
     */
    public void setWhiskerLength(double value) {
        whiskerLength = setDoubleProperty(whiskerLength, value);
    }

    /**
     * Controls the appearance of CustomBoxSeries box.<br>
     * Box contains several properties to control the formatting attributes of
     * box, like Pen, Brush, Draw3D, Visible, etc.
     *
     * @return SeriesPointer
     */
    public SeriesPointer getBox() {
        return getPointer();
    }

    /**
     * Use SampleValues property to get access to the values of Series.<br>
     * <b>WARNING :</b> If you are using internal lagorithm
     * (UseCustomValues = false) then you must make sure SampleValues are
     * sorted in ascending order.
     *
     * @return ValueList
     */
    public ValueList getSampleValues() {
        return mandatory;
    }

    /**
     * Specifies the position of box series.<br>
     * If series is a Box series then Position specifies the horizontal
     * axis position.<br>
     * If series is a HorizBox series then Position specifies the vertical
     * axis position. <br>
     * Default value: O
     *
     * @return double
     */
    public double getPosition() {
        return dPosition;
    }

    /**
     * Specifies the position of box series.<br>
     * Default value: O
     *
     * @param value double
     */
    public void setPosition(double value) {
        dPosition = setDoubleProperty(dPosition, value);
    }

    /**
     * Defines the Pen to draw the whisker lines.<br>
     * Use the properties of WhiskerPen to change the color or style of whisker
     * lines.
     *
     * @return ChartPen
     */
    public ChartPen getWhiskerPen() {
        if (whiskerPen == null) {
            whiskerPen = new ChartPen(chart, Color.BLACK);
        }
        return whiskerPen;
    }

    /**
     * Defines the Pen to draw the median line.<br>
     * Use the properties of MedianPen to change the color or style of median
     * pen.
     *
     * @return ChartPen
     */
    public ChartPen getMedianPen() {
        if (medianPen == null) {
            medianPen = new ChartPen(chart, Color.BLACK);
            medianPen.setStyle(DashStyle.DOT);
        }
        return medianPen;
    }

    // Calculate 1st and 3rd quartile
    private double percentile(ValueList values, double p) {
        int n = values.getCount();
        double v = p * (n + 1);
        double d = (v != 0) ? v - Utils.trunc(v) : 0.0;
        int k = (v != 0.0) ? (int)Utils.trunc(v) : 0;        
        
        return calcPctile(values, n, k, d);
    }
    
    private double calcPctile(ValueList sortedvalues, int n, int k, double d) {
        if (k < 1) {
            return sortedvalues.getValue(0);
        }
        else if (k > n - 1) { 
            return sortedvalues.getValue(n - 1);
        }
        else { 
            return (1 - d) * sortedvalues.getValue(k - 1) + 
                    d * sortedvalues.getValue(k);
        }
    }    

    /**
     * Reconstructs the box plot from series data.
     */
    public void reconstructFromData() {
        int n = getSampleValues().count;
        if (n > 0) {
            double invN = 1.0 / n;
            /* calculate median */
            int med = n / 2;
            if ((n % 2) == 0) {
                median = 0.5 *
                         (getSampleValues().value[med - 1] +
                          getSampleValues().value[med]);
            } else {
                median = getSampleValues().value[med];
            }

            /* calculate Q1 && Q3 */
            quartile1 = (n > 1) ? percentile(getSampleValues(), 0.25) :
                        getSampleValues().value[0];
            quartile3 = (n > 1) ? percentile(getSampleValues(), 0.75) :
                        getSampleValues().value[0];

            /* calculate IQR */
            double iqr = quartile3 - quartile1;
            innerFence1 = quartile1 - whiskerLength * iqr;
            innerFence3 = quartile3 + whiskerLength * iqr;

            /* find adjacent points */

            int tmp=0;

            for (int i = 0; i < med; i++) {
                if (innerFence1 <= getSampleValues().value[i]) {
                    tmp=i;
                    break;
                }
            }

            adjacentPoint1 = getSampleValues().value[tmp];

            tmp = n;
            for (int i = med; i < n; i++) {
                if (innerFence3 <= getSampleValues().value[i]) {
                    tmp=i;
                    break;
                }
            }

            if (tmp>0) {
                adjacentPoint3 = getSampleValues().value[tmp - 1];
            }

            /* calculate outer fences */
            outerFence1 = quartile1 - 2 * whiskerLength * iqr;
            outerFence3 = quartile3 + 2 * whiskerLength * iqr;
        }
    }

    public void doBeforeDrawValues() {
        super.doBeforeDrawValues();
        /* if custom values are used, or there are no points, skip the recalculation */
        if (!useCustomValues) {
            reconstructFromData();
        }
    }

    /**
     * Called internally. Draws the "ValueIndex" point of the Series.<br>
     *
     * @param index int
     */
    public void drawValue(int index) {
        SeriesPointer tmp = null;

        double tmpVal = getSampleValues().value[index];

        // inside inner fences - no point
        if ((tmpVal >= innerFence1) && (tmpVal <= innerFence3)) {
            tmp = null;
        } else
        // mild outlined points
        if (((tmpVal >= innerFence3) && (tmpVal <= outerFence3)) ||
            ((tmpVal <= innerFence1) && (tmpVal >= outerFence1))) {
            tmp = mildOut;
        } else {
            // extreme outlined points
            tmp = extrOut;
        }

        if (tmp != null) {
            if (tmp.getVisible()) {
                Color tmpColor;
                if (tmp.getBrush().getColor()==Color.EMPTY)
                  tmpColor = getValueColor(index);
                else
                  tmpColor = tmp.getBrush().getColor();
                tmp.prepareCanvas(chart.getGraphics3D(), tmpColor);
                if (iVertical) {
                    tmp.draw(calcXPosValue(dPosition), calcYPos(index),
                             tmpColor);
                } else {
                    tmp.draw(calcXPos(index), calcYPosValue(dPosition),
                             tmpColor);
                }
            }
        }
    }

    protected void addSampleValues(int numValues) {
        int num = chart.getSeriesCount() + 1;
        Random r = new Random();
        int n = num * (3 + r.nextInt(10));
        add( -n);
        for (int t = 2; t < numValues - 1; t++) {
            add(n * t / numValues);
        }
        add(2 * n);
    }

    private void internalGallery() {
        dPosition = chart.getSeriesIndexOf(this) + 1;
        getPointer().setHorizSize(12);
        getMildOut().setHorizSize(3);
        getExtrOut().setVertSize(3);
        fillSampleValues(Utils.round(10 * dPosition));
    }

    public void prepareForGallery(boolean isEnabled) {
        super.prepareForGallery(isEnabled);

        // by default display 2 series
        for (int t = 0; t < chart.getSeriesCount(); t++) {
            ISeries s = chart.getSeries(t);
            if (s instanceof CustomBox) {
                ((CustomBox) s).internalGallery();
            }
        }
    }

    // MS : multiple series marks were not displayed correctly
    protected void drawMark(int valueIndex, String s,
                            SeriesMarksPosition position) {
        if (iVertical) {
            position.arrowTo.x = calcXPosValue(dPosition);
            position.arrowFrom.x = position.arrowTo.x;
            position.leftTop.x = position.arrowTo.x - position.width / 2;
        } else {
            position.arrowTo.y = calcYPosValue(dPosition);
            position.arrowFrom.y = position.arrowTo.y;
            position.leftTop.y = position.arrowTo.y - position.height / 2;
        }
        super.drawMark(valueIndex, s, position);
    }

    private int calcPos(double value) {
        return iVertical ? calcYPosValue(value) : calcXPosValue(value);
    }

    private void drawWhisker(double adjPos, int pos, int tmpZ, int tmp,
                             int tmp1) {
        int tmp2 = calcPos(adjPos);
        IGraphics3D g = chart.getGraphics3D();
        if (chart.getAspect().getView3D()) {
            if (iVertical) {
                g.verticalLine(tmp1, pos, tmp2, tmpZ);
                g.horizontalLine(tmp1 - tmp, tmp1 + tmp, tmp2, tmpZ);
            } else {
                g.horizontalLine(pos, tmp2, tmp1, tmpZ);
                g.verticalLine(tmp2, tmp1 - tmp, tmp1 + tmp, tmpZ);
            }
        } else
        if (iVertical) {
            g.verticalLine(tmp1, pos, tmp2);
            g.horizontalLine(tmp1 - tmp, tmp1 + tmp, tmp2);
        } else {
            g.horizontalLine(pos, tmp2, tmp1);
            g.verticalLine(tmp2, tmp1 - tmp, tmp1 + tmp);
        }
    }

    protected void draw() {
        int aLeft, aTop, aRight, aBottom, tmpH, tmpV, tmpA1, tmpA2;

        super.draw();
        int tmp = getPointer().getHorizSize();

        if (iVertical) {
            aLeft = calcXPosValue(dPosition) - tmp;
            aRight = calcXPosValue(dPosition) + tmp;
            aTop = calcYPosValue(quartile3);
            aBottom = calcYPosValue(quartile1);
            tmpA1 = aBottom;
            tmpA2 = aTop;
        } else {
            aTop = calcYPosValue(dPosition) - tmp;
            aBottom = calcYPosValue(dPosition) + tmp;
            aRight = calcXPosValue(quartile3);
            aLeft = calcXPosValue(quartile1);
            tmpA1 = aLeft;
            tmpA2 = aRight;
        }

        if (getHorizAxis().getInverted()) {
            int tmpA = aLeft;
            aLeft = aRight;
            aRight = tmpA;
        }

        if (getVertAxis().getInverted()) {
            int tmpA = aTop;
            aTop = aBottom;
            aBottom = tmpA;
        }

        IGraphics3D g = chart.getGraphics3D();

        if (getPointer().getVisible()) {
            getPointer().prepareCanvas(g, getPointer().getBrush().getColor());

            if (iVertical) {
                tmpV = (aBottom - aTop) / 2;
                getPointer().draw(g, chart.getAspect().getView3D(),
                                  aLeft + tmp - 1,
                                  aTop + tmpV, getPointer().getHorizSize() - 1,
                                  tmpV - 1,
                                  getPointer().getBrush().getColor(),
                                  getPointer().getStyle());
            } else {
                tmpH = (aRight - aLeft) / 2;
                getPointer().draw(g, chart.getAspect().getView3D(), aLeft + tmpH,
                                  aTop + tmp - 1, tmpH - 1,
                                  getPointer().getVertSize() - 1,
                                  getPointer().getBrush().getColor(),
                                  getPointer().getStyle());
            }
        }

        // median
        if (getMedianPen().getVisible()) {
            g.setPen(medianPen);
            g.getBrush().setVisible(false);
            tmpV = calcPos(median);
            if (iVertical) {
                if (chart.getAspect().getView3D()) {
                    g.horizontalLine(aLeft, aRight, tmpV, getStartZ());
                } else {
                    g.horizontalLine(aLeft, aRight, tmpV);
                }
            } else
            if (chart.getAspect().getView3D()) {
                g.verticalLine(tmpV, aTop, aBottom, getStartZ());
            } else {
                g.verticalLine(tmpV, aTop, aBottom);
            }
        }

        // whiskers

        if (getWhiskerPen().getVisible()) {
            int tmpZ = (getPointer().getVisible() && getPointer().getDraw3D()) ?
                       getMiddleZ() :
                       getStartZ();
            g.setPen(whiskerPen);
            int tmp1 = (iVertical ? aLeft + aRight : aTop + aBottom) / 2;
            drawWhisker(adjacentPoint1, tmpA1, tmpZ, tmp, tmp1);
            drawWhisker(adjacentPoint3, tmpA2, tmpZ, tmp, tmp1);
        }
    }

    public void setChart(IBaseChart c) {
        super.setChart(c);
        getExtrOut().setChart(c);
        getMildOut().setChart(c);
        getMedianPen().setChart(c);
        getWhiskerPen().setChart(c);
    }

    /**
     * Read from AdjacentPoint1 to get SampleValues lower adjacent point
     * limit.<br>
     * <b>NOTE:</b> Writing to AdjacentPoint1 does not ensure custom value will
     * be used to construct a box plot.
     * An additional requirement is to set UseCustomValues to true. <br>
     * Default value: 0
     *
     * @see CustomBox
     * @return double
     */
    public double getAdjacentPoint1() {
        return adjacentPoint1;
    }

    /**
     * Read from AdjacentPoint1 to get SampleValues lower adjacent point
     * limit.<br>
     * Default value: 0
     *
     * @param value double
     */
    public void setAdjacentPoint1(double value) {
        adjacentPoint1 = setDoubleProperty(adjacentPoint1, value);
    }

    /**
     * Read from AdjacentPoint3 to get SampleValues upper adjacent point.<br>
     * AdjacentPoint3 is the data point that is closest to InnerFence3, without
     * lying above InnerFence3. <br>
     * Write to AdjacentPoint3 to override TCustomBoxSeries default calculating
     * algorithm and use supplied value to construct box plot. <br><br>
     * <b>NOTE:</b> Writing to AdjacentPoint3 does not ensure custom value
     * will be used to construct a box plot. An additional requirement is to
     * set UseCustomValues to true. <br>
     * Default value: O
     *
     * @see CustomBox
     * @return double
     */
    public double getAdjacentPoint3() {
        return adjacentPoint3;
    }

    /**
     * Read from AdjacentPoint3 to get SampleValues upper adjacent point.<br>
     * Default value: O
     *
     * @see CustomBox
     * @param value double
     */
    public void setAdjacentPoint3(double value) {
        adjacentPoint3 = setDoubleProperty(adjacentPoint3, value);
    }

    /**
     * Read from OuterFence3 to get SampleValues upper outer fence.<br>
     * Write to OuterFence3 to CustomBox Series default calculating algorithm
     * and use supplied value to static finalruct box plot.<br><br>
     * <b>NOTE:</b> Writing to OuterFence3 does not ensure custom value will be
     * used to construct a box plot. An additional requirement is to set
     * UseCustomValues to true.<br>
     * Default value: O
     *
     * @see CustomBox
     * @return double
     */
    public double getOuterFence3() {
        return outerFence3;
    }

    /**
     * Read from OuterFence3 to get SampleValues upper outer fence.<br>
     * Default value: O
     *
     * @see CustomBox
     * @param value double
     */
    public void setOuterFence3(double value) {
        outerFence3 = setDoubleProperty(outerFence3, value);
    }

    /**
     * Read from OuterFence1 to get SampleValues lower outer fence.<br>
     * Write to OuterFence1 to CustomBox Series default calculating algorithm
     * and use supplied value to static finalruct box plot.<br><br>
     * <b>NOTE:</b> Writing to OuterFence1 does not ensure custom value will be
     * used to construct a box plot. An additional requirement is to set
     * UseCustomValues to true.<br>
     * Default value: O
     *
     * @see CustomBox
     * @return double
     */
    public double getOuterFence1() {
        return outerFence1;
    }

    /**
     * Read from OuterFence1 to get SampleValues lower outer fence.<br>
     * Default value: O
     *
     * @see CustomBox
     * @param value double
     */
    public void setOuterFence1(double value) {
        outerFence1 = setDoubleProperty(outerFence1, value);
    }

    /**
     * Read from InnerFence1 to get SampleValues lower inner fence.<br>
     * Write to InnerFence1 to override TCustomBoxSeries default calculating
     * algorithm and use supplied value to construct box plot.  <br><br>
     * <b>NOTE:</b> Writing to InnerFence1 does not ensure custom value will be
     * used to construct a box plot. An additional requirement is to set
     * UseCustomValues to true.<br>
     * Default value: O
     *
     * @see CustomBox
     * @return double
     */
    public double getInnerFence1() {
        return innerFence1;
    }

    /**
     * Read from InnerFence1 to get SampleValues lower inner fence.<br>
     * Default value: O
     *
     * @see CustomBox
     * @param value double
     */
    public void setInnerFence1(double value) {
        innerFence1 = setDoubleProperty(innerFence1, value);
    }

    /**
     * Read from InnerFence3 to get SampleValues upper inner fence.<br>
     * Write to InnerFence3 to override TCustomBoxSeries default calculating
     * algorithm and use supplied value to construct box plot.<br><br>
     * <b>NOTE:</b> Writing to InnerFence1 does not ensure custom value will be
     * used to construct a box plot. An additional requirement is to set
     * UseCustomValues to true.<br>
     * Default value: O
     *
     * @see CustomBox
     * @return double
     */
    public double getInnerFence3() {
        return innerFence3;
    }

    /**
     * Read from InnerFence3 to get SampleValues upper inner fence.<br>
     * Default value: O
     *
     * @see CustomBox
     * @param value double
     */
    public void setInnerFence3(double value) {
        innerFence3 = setDoubleProperty(innerFence3, value);
    }

    /**
     * Read from Quartile1 to get SampleValues first quartile.<br>
     * Approximately 25% of the data will fall on or below Quartile1.<br>
     * Write to Quartile1 to override TCustomBoxSeries default calculating
     * algorithm and use supplied value to construct box plot.  <br><br>
     * <b>NOTE:</b> Writing to Quartile1 does not ensure custom value will be
     * used to construct a box plot. An additional requirement is to set
     * UseCustomValues to true.<br>
     * Default value: O
     *
     * @see CustomBox
     * @return double
     */
    public double getQuartile1() {
        return quartile1;
    }

    /**
     * Read from Quartile1 to get SampleValues first quartile.<br>
     * Default value: O
     *
     * @see CustomBox
     * @param value double
     */
    public void setQuartile1(double value) {
        quartile1 = setDoubleProperty(quartile1, value);
    }

    /**
     * Read from Quartile3 to get SampleValues first quartile.<br>
     * Approximately 75% of the data will fall on or below Quartile3.<br>
     * Write to Quartile3 to override TCustomBoxSeries default calculating
     * algorithm and use supplied value to construct box plot.  <br><br>
     * <b>NOTE:</b> Writing to Quartile3 does not ensure custom value will be
     * used to construct a box plot. An additional requirement is to set
     * UseCustomValues to true.<br>
     * Default value: O
     *
     * @see CustomBox
     * @return double
     */
    public double getQuartile3() {
        return quartile3;
    }

    /**
     * Read from Quartile3 to get SampleValues first quartile.<br>
     * Default value: O
     *
     * @see CustomBox
     * @param value double
     */
    public void setQuartile3(double value) {
        quartile3 = setDoubleProperty(quartile3, value);
    }

    /**
     * Read from Median to get SampleValues median value.<br>
     * Write to Median to CustomBox Series default calculating algorithm and
     * use supplied value to construct box plot.<br><br>
     * <b>NOTE:</b> Writing to Median does not ensure custom value will be
     * used to construct a box plot. An additional requirement is to set
     * UseCustomValues to true.
     * Default value: O
     *
     * @see CustomBox
     * @return double
     */
    public double getMedian() {
        return median;
    }

    /**
     * Read from Median to get SampleValues median value.<br>
     * Default value: O
     *
     * @see CustomBox
     * @param value double
     */
    public void setMedian(double value) {
        median = setDoubleProperty(median, value);
    }

    /**
     * Set to true if you want to override internal algorithm to construct a
     * box plot.<br><br>
     * Default (internal) algorithm does the following steps: <br><br>
     * 1. Finds the sample Median, Quartile1, Quartile3 and IQR value. <br><br>
     * 2. Finds InnerFence1 and InnerFence3 by using the following equation:<br>
     * InnerFence1 = Quartile1 - WhiskerLength*IQR <br>
     * InnerFence3 = Quartile3 + WhiskerLength*IQR <br><br>
     * 3. Finds AdjacentPoint1 and AdjacentPoint3 <br><br>
     * 4. Finds OuterFence1 and OuterFence3 by using the following equation:<br>
     * OuterFence1 = Quartile1 - 2*WhiskerLength*IQR <br>
     * OuterFence3 = Quartile3 + 2*WhiskerLength*IQR <br><br>
     * 5. Constructs a box with ends at Quartile1 and Quartile3 with an
     * interior line draw at the Median. <br><br>
     * 6. Indicates adjacent points and connects them to the box with lines.
     * Locates any data points falling between the inner and outer fences and
     * denotes them by a symbol. These points are considered to be mild
     * outliers. Indicates data points that fall beyond the outer fences by
     * another symbol. These points are considered to be extreme
     * outliers.<br><br>
     * <b>NOTE:</b> If you set the UseCustomValues to true then you must
     * also define Median, Quartile1, Quartile3, InnerFence1, InnerFence3,
     * OuterFence1, OuterFence3, AdjacentPoint1 and AdjacentPoint3.
     * Default value: false
     *
     * @return boolean
     */
    public boolean getUseCustomValues() {
        return useCustomValues;
    }

    /**
     * Set to true if you want to override internal algorithm to construct a
     * box plot.<br>
     * Default value: false
     *
     * @param value boolean
     */
    public void setUseCustomValues(boolean value) {
        useCustomValues = setBooleanProperty(useCustomValues, value);
    }

    /**
     * Controls the appearance of the mid range of outer points.<br>
     * MildOut contains several properties to control the formatting
     * attributes of the mid range of outer points like Pen, Brush, Draw3D,
     * Visible, etc.<br>
     *
     * @return SeriesPointer
     */
    public SeriesPointer getMildOut() {
    	if (mildOut==null)
    	  mildOut = new SeriesPointer(chart, this);
      return mildOut;
    }

    /**
     * Controls the appearance of the extreme range of outer points.<br>
     * ExtrOut contains several properties to control the formatting
     * attributes of the extreme range of outer points like Pen, Brush, Draw3D,
     * Visible, etc. <br>
     *
     * @return SeriesPointer
     */
    public SeriesPointer getExtrOut() {
    	if (extrOut==null)
    	  extrOut = new SeriesPointer(chart, this);
      return extrOut;
    }
}

