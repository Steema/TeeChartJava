package com.steema.teechart.styles;


import com.steema.teechart.languages.Language;
import com.steema.teechart.legend.LegendTextStyle;
import com.steema.teechart.drawing.Color;
import com.steema.teechart.drawing.Graphics3D;
import com.steema.teechart.drawing.IGraphics3D;
import com.steema.teechart.drawing.ChartPen;
import com.steema.teechart.drawing.Point;
import com.steema.teechart.IBaseChart;
import com.steema.teechart.drawing.ChartBrush;

import java.util.Random;

/**
 *
 * <p>Title: Funnel class</p>
 *
 * <p>Description: Funnel Series.</p>
 *
 * <p>Copyright (c) 2005-2019 by Steema Software SL. All Rights Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 *
 */
public class Funnel extends Series {

    /**
	 * 
	 */
    private static final long serialVersionUID = 1L;
    private Color aboveColor = Color.GREEN;
    private boolean autoUpdate = true;
    private Color belowColor = Color.RED;
    private double differenceLimit = 30;
    private ChartPen pen;
    private ChartPen linesPen;
    private ValueList opportunityValues;
    private boolean quotesSorted;
    private Color withinColor = Color.YELLOW;

    transient private int iPolyCount;
    transient private Point[] iPolyPoints = new Point[5];
    transient private Point[] boundingPoints = new Point[4];
    transient private boolean iSorted;
    transient private double iMin;
    transient private double iMax;
    transient private double iSlope;
    transient private double iDiff;

    public Funnel(IBaseChart c) {
        super(c);

        opportunityValues = new ValueList(this, Language.getString("OpportunityValues"));
        opportunityValues.setOrder(ValueListOrder.NONE);
        vxValues.setOrder(ValueListOrder.NONE);
        setPercentFormat(Language.getString("FunnelPercent"));
        getQuoteValues().setOrder(ValueListOrder.DESCENDING);
        getYValues().name = Language.getString("QuoteValues");
        useSeriesColor = false;
    }

    public Funnel() {
        this((IBaseChart)null);
    }
    
    public void assign(Series source) {
        if (source instanceof Funnel) {
            Funnel tmp = (Funnel)source;
            
            tmp.setAboveColor(((Funnel) source).getAboveColor());
            tmp.setAutoUpdate(((Funnel) source).getAutoUpdate());
            tmp.setBelowColor(((Funnel) source).getBelowColor());
            tmp.setChart(((Funnel) source).getChart());
            tmp.setDifferenceLimit(((Funnel) source).getDifferenceLimit());
            tmp.setOpportunityValues(((Funnel) source).getOpportunityValues());
            tmp.setQuotesSorted(((Funnel) source).getQuotesSorted());
            tmp.setWithinColor(((Funnel) source).getWithinColor());
        }
        super.assign(source);
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
        if ((result == -1) && (firstVisible > -1) && (lastVisible > -1)) {
            for (int t = firstVisible; t <= lastVisible; t++) {
                defineFunnelRegion(t);
                if (Graphics3D.pointInPolygon(new Point(x,y), iPolyPoints)) {
                    return t;
                }
            }
        }
        return result;
    }

    protected Object readResolve() {
        iPolyPoints = new Point[5];
        boundingPoints = new Point[4];

        return super.readResolve();
    }

    private Color defineFunnelRegion(int index) {

        // Calculate multiplying factor

        if (getQuoteValues().getValue(index) == 0) {
            iDiff = 0;
        } else {
            iDiff = opportunityValues.getValue(index) /
                    getQuoteValues().getValue(index);
        }

        for (int t = 0; t < boundingPoints.length; t++) {
            if (boundingPoints[t] == null) {
                boundingPoints[t] = new Point();
            }
        }

        for (int t = 0; t < iPolyPoints.length; t++) {
            if (iPolyPoints[t] == null) {
                iPolyPoints[t] = new Point();
            }
        }

        // calculate bouding rectangle
        boundingPoints[0].x = calcXPosValue(index - 0.5);
        boundingPoints[0].y = calcYPosValue(iMax - iSlope * index);
        boundingPoints[1].x = calcXPosValue(index + 0.5);
        boundingPoints[1].y = calcYPosValue(iMax - iSlope * (index + 1));
        boundingPoints[2].x = boundingPoints[1].x;
        boundingPoints[2].y = calcYPosValue(iSlope * (index + 1));
        boundingPoints[3].x = boundingPoints[0].x;
        boundingPoints[3].y = calcYPosValue(iSlope * index);

        int tmpY = calcYPosValue((iMax - 2 * iSlope * index) * iDiff +
                                 iSlope * index);
        // Actual value, expressed in axis scale

        if (tmpY <= boundingPoints[0].y) { // IDiff >= 1
            iPolyCount = 4;
            iPolyPoints[0] = boundingPoints[0];
            iPolyPoints[1] = boundingPoints[1];
            iPolyPoints[2] = boundingPoints[2];
            iPolyPoints[3] = boundingPoints[3];
        } else
        if ((tmpY > boundingPoints[0].y) && (tmpY <= boundingPoints[1].y)) {
            iPolyCount = 5;
            iPolyPoints[0].x = boundingPoints[0].x;
            iPolyPoints[0].y = tmpY;
            int tmpX = calcXPosValue((iMax - 2 * iSlope * index) * (1 - iDiff) /
                                     iSlope + index - 0.5);
            if (iPolyPoints[1] == null) {
                iPolyPoints[1] = new Point();
            }
            iPolyPoints[1].x = tmpX;
            iPolyPoints[1].y = tmpY;
            iPolyPoints[2] = boundingPoints[1];
            iPolyPoints[3] = boundingPoints[2];
            iPolyPoints[4] = boundingPoints[3];
        } else if (tmpY > boundingPoints[2].y) {
            iPolyCount = 3;
            for (int t = 0; t < 2; t++) {
                if (iPolyPoints[t] == null) {
                    iPolyPoints[t] = new Point();
                }
            }
            iPolyPoints[0].x = boundingPoints[0].x;
            iPolyPoints[0].y = tmpY;
            int tmpX = calcXPosValue((iMax - 2 * iSlope * index) * iDiff /
                                     iSlope + index - 0.5);
            iPolyPoints[1].x = tmpX;
            iPolyPoints[1].y = tmpY;
            iPolyPoints[2] = boundingPoints[3];
        } else {
            iPolyCount = 4;
            iPolyPoints[0].x = boundingPoints[0].x;
            iPolyPoints[0].y = tmpY;
            iPolyPoints[1].x = boundingPoints[1].x;
            iPolyPoints[1].y = tmpY;
            iPolyPoints[2] = boundingPoints[2];
            iPolyPoints[3] = boundingPoints[3];
        }

        if (iDiff >= 1) {
            return aboveColor;
        } else
        if ((1 - iDiff) * 100 > differenceLimit) {
            return belowColor;
        } else {
            return withinColor;
        }

    }

    /**
     * Funnel segment color if Opportunity value is greater than Quote
     * value.<br>
     * Default value: GREEN
     *
     *
     * @return Color
     */
    public Color getAboveColor() {
        return aboveColor;
    }

    /**
     * Funnel segment color if Opportunity value is greater than Quote
     * value.<br>
     * Default value: GREEN
     *
     *
     * @param value Color
     */
    public void setAboveColor(Color value) {
        aboveColor = setColorProperty(aboveColor, value);
    }

    /**
     * Funnel segment color if Opportunity value is within DifferenceLimit %
     * below the Quote value.<br>
     * Default value: YELLOW
     *
     *
     * @return Color
     */
    public Color getWithinColor() {
        return withinColor;
    }

    /**
     * Funnel segment color if Opportunity value is within DifferenceLimit %
     * below the Quote value.<br>
     * Default value: YELLOW
     *
     *
     * @param value Color
     */
    public void setWithinColor(Color value) {
        withinColor = setColorProperty(withinColor, value);
    }

    /**
     * Funnel segment color if Opportunity value is more than the
     * DifferenceLimit % below the Quote value.<br>
     * Default value: RED
     *
     *
     * @return Color
     */
    public Color getBelowColor() {
        return belowColor;
    }

    /**
     * Funnel segment color if Opportunity value is more than the
     * DifferenceLimit % below the Quote value.<br>
     * Default value: RED
     *
     *
     * @param value Color
     */
    public void setBelowColor(Color value) {
        belowColor = setColorProperty(belowColor, value);
    }

    /**
     * Define Pen to draw the Funnel Chart.
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
     * Defines Pen to draw FunnelSeries bounding polygon.
     *
     * @return ChartPen
     */
    public ChartPen getLinesPen() {
        if (linesPen == null) {
            linesPen = new ChartPen(chart, Color.BLACK);
        }
        return linesPen;
    }

    /**
     * Defines Brush to fill Funnel Series.
     *
     * @return ChartBrush
     */
    public ChartBrush getBrush() {
        return bBrush;
    }

    public void setChart(IBaseChart c) {
        super.setChart(c);
        if (linesPen != null) {
            linesPen.setChart(chart);
        }
        if (pen != null) {
            pen.setChart(chart);
        }
    }

    public String getMarkText(int valueIndex) {
        return percentDecimal.format(100 *
                                     opportunityValues.getValue(valueIndex) /
                                     getQuoteValues().getValue(valueIndex));
    }


    public void doBeforeDrawChart() {
        super.doBeforeDrawChart();
        if (getVisible() && (getVertAxis() != null)) {
            getVertAxis().setVisible(false);
        }
        
        if (iSlope == 0)
        	if (autoUpdate)
        	{
        		reCalc();
        	}
    }

    public int getCountLegendItems() {
        return 3;
    }

    public Color legendItemColor(int legendIndex) {
        switch (legendIndex) {
        case 0:
            return aboveColor;
        case 1:
            return withinColor;
        default:
            return belowColor;
        }
    }

    /**
     * Returns LegendString for LegendIndex'th item.
     *
     *
     * @param legendIndex int
     * @param legendTextStyle LegendTextStyle
     * @return String
     */
    public String getLegendString(int legendIndex,
                               LegendTextStyle legendTextStyle) {
        switch (legendIndex) {
        case 0:
            return Language.getString("FunnelExceed");
        case 1:
            return percentDecimal.format(getDifferenceLimit()) +
                    Language.getString("FunnelWithin");
        default:
            return percentDecimal.format(getDifferenceLimit()) +
                    Language.getString("FunnelBelow");
        }
    }

    //CDI - interesting ... boolean needed as method will otherwise take the slot for add(double, double, string, color)
    //in net we have the 'new' keyword for member hiding
    public int add(double aQuote, double aOpportunity, String aLabel, Color aColor, boolean aFunnel) {
        this.opportunityValues.tempValue = aOpportunity;
        int result = add(aQuote, aLabel, aColor);
        if(!quotesSorted) {
            this.getYValues().sort();
            this.getXValues().fillSequence();
            this.iSorted = true;
        }
        return result;

    }

    protected void addSampleValues(int numValues) {
        Random r = new Random();
        for (int t = 0; t < numValues; t++) {
            int tt = t + 1;
            add(2.3 * numValues * (t + 1),
                (2.3 - (2 * r.nextDouble())) * numValues * (t + 2),
                Language.getString("FunnelSegment") + Double.toString(tt), Color.EMPTY, true);
        }
        reCalc();
    }

    protected void drawMark(int valueIndex, String st,
                            SeriesMarksPosition aPosition) {
        aPosition.leftTop.x = calcXPosValue(valueIndex) - aPosition.width / 2;
        aPosition.leftTop.y = calcYPosValue(iMax * 0.5) - aPosition.height / 2;
        super.drawMark(valueIndex, st, aPosition);
    }

    /**
     * Accesses the quote values of the FunnelSeries.
     *
     * @return ValueList
     */
    public ValueList getQuoteValues() {
        return mandatory;
    }

    /**
     * The difference (expressed in Quote %) used to define the Funnel
     * segment color.<br>
     * If the Opportunity value falls below (100-Difference)*Quote/100 then
     * the BelowColor will be used to paint the Funnel segment.<br>
     * If the Opportunity value falls between (100-Difference)*Quote/100 and
     * Quote, then the WithinColor will be used to paint the Funnel segment.<br>
     * Otherwise (if Opportunity > Quote) the AboveColor will be used to paint
     * the Funnel segment. <br>
     * Default value: 30
     *
     * @return double
     */
    public double getDifferenceLimit() {
        return differenceLimit;
    }


    /**
     * Sets the difference (expressed in Quote %) used to define the Funnel
     * segment color.<br>
     * Default value: 30
     *
     * @param value double
     */
    public void setDifferenceLimit(double value) {
        differenceLimit = value;
        if (autoUpdate) {
            reCalc();
        }
        invalidate();
    }

    /**
     * Returns the Minimum Value of the Series Y Values List.<br>
     * As some Series have more than one Y Values List, this Minimum Value is
     * the "Minimum of Minimums" of all Series Y Values lists.
     *
     * @return double
     */
    public double getMinYValue() {
        return 0;
    }

    /**
     * Returns the Maximum Value of the Series X Values List.
     *
     * @return double
     */
    public double getMaxXValue() {
        return getCount() - 0.5;
    }

    /**
     * Returns the Minimum Value of the Series X Values List.<br>
     * As some Series have more than one Y Values List, this Minimum Value is
     * the "Minimum of Minimums" of all Series Y Values lists.
     *
     * @return double
     */
    public double getMinXValue() {
        return -0.5;
    }

    /**
     * Called internally. Draws the "ValueIndex" point of the Series.
     *
     * @param valueIndex int
     */
    public void drawValue(int valueIndex) {

        IGraphics3D g = chart.getGraphics3D();
        g.setBrush(getBrush());
        g.getBrush().setColor(defineFunnelRegion(valueIndex));

        g.getBrush().setVisible(true);

        Point[] a = iPolyPoints;

        if (chart.getAspect().getView3D()) {
            g.polygon(getStartZ(), Graphics3D.sliceArray(a, iPolyCount));
        } else {
            g.polygon(Graphics3D.sliceArray(a, iPolyCount));
        }
    }

    /**
     * Sorts added segments by QuoteValues in descending order.<br>
     * Setting QuotesSorted to false will enable the internal sorting
     * algorithm. <br>
     * Default value: false
     *
     * @return boolean
     */
    public boolean getQuotesSorted() {
        return quotesSorted;
    }

    /**
     * Sorts added segments by QuoteValues in descending order.<br>
     * Default value: false
     *
     * @param value boolean
     */
    public void setQuotesSorted(boolean value) {
        quotesSorted = value;
        iSorted = quotesSorted;
    }

    public void setOpportunityValues(final ValueList value) {
        opportunityValues=value;
    }

    public ValueList getOpportunityValues() {
        return opportunityValues;
    }

    /**
     * Reconstructs FunnelSeries with every added point.<br>
     * To speed up the drawing of Funnel series, set the AutoUpdate to false
     * and call the Recalculate method when all points are added.<br>
     * Default value: true
     *
     * @return boolean
     */
    public boolean getAutoUpdate() {
        return autoUpdate;
    }

    /**
     * Reconstructs FunnelSeries with every added point.<br>
     * Default value: true
     *
     * @param value boolean
     */
    public void setAutoUpdate(boolean value) {
        autoUpdate = value;
        if (autoUpdate) {
            reCalc();
        }
    }

    /**
     * Uses all Quote and Opportunity values to restatic finalruct the Funnel
     * chart.
     */
    public void reCalc() {
        if (!iSorted) {
            getQuoteValues().sort();
            vxValues.fillSequence();
            iSorted = true;
        }

        if (getCount() > 0) {
            iMax = getQuoteValues().getFirst();
            iMin = getQuoteValues().getLast();
            iSlope = 0.5 * (iMax - iMin) / getCount();
        }
    }

    /**
     * Adds new Funnel segment to the Series.
     *
     * @param aQuote double
     * @param aOpportunity double
     * @param aLabel String
     * @param aColor Color
     * @return int
     */
    public int addSegment(double aQuote, double aOpportunity, String aLabel,
                          Color aColor) { //TODO there must be a correct way to use overload of add method
        opportunityValues.tempValue = aOpportunity;
        int result = add(aQuote, aLabel, aColor);
        if (!quotesSorted) {
            getYValues().sort();
            getXValues().fillSequence();
            iSorted = true;
        }
        return result;
    }


    protected void draw() {
        super.draw();

        IGraphics3D g = chart.getGraphics3D();
        g.setPen(getPen());

        g.getBrush().setVisible(false);
        boundingPoints[0].x = calcXPosValue( -0.5);
        boundingPoints[0].y = calcYPosValue(iMax);
        boundingPoints[1].x = calcXPosValue(getCount() - 0.5);
        boundingPoints[1].y = calcYPosValue((iMax + iMin) * 0.5);
        boundingPoints[2].x = boundingPoints[1].x;
        boundingPoints[2].y = calcYPosValue((iMax - iMin) * 0.5);
        boundingPoints[3].x = boundingPoints[0].x;
        boundingPoints[3].y = calcYPosValue(0.0);

        g.polygon(getStartZ(), boundingPoints);

        if ((linesPen != null) && (linesPen.getVisible())) {
            g.setPen(linesPen);
            for (int t = firstVisible; t < lastVisible; t++) {
                g.verticalLine(calcXPosValue(t + 0.5),
                               calcYPosValue(iMax - iSlope * (t + 1)),
                               calcYPosValue(iSlope * (t + 1)), getStartZ());
            }
        }
    }

    /**
     * Gets descriptive text.
     *
     * @return String
     */
    public String getDescription() {
        return Language.getString("FunnelSeries");
    }
}

