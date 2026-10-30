package com.steema.teechart.styles;

import com.steema.teechart.languages.Language;

import com.steema.teechart.drawing.Color;
import com.steema.teechart.drawing.Point;

import com.steema.teechart.drawing.IGraphics3D;
import com.steema.teechart.IBaseChart;
import com.steema.teechart.drawing.Graphics3D;
import com.steema.teechart.misc.Utils;

/**
 *
 * <p>Title: Arrow class</p>
 *
 * <p>Description: Arrow Series.</p>
 *
 * <p>Example:
 * <pre><font face="Courier" size="4">
 * arrowSeries = new com.steema.teechart.styles.Arrow(myChart.getChart());
 * arrowSeries.setArrowWidth(32);
 * arrowSeries.setArrowHeight(32);
 * arrowSeries.getXValues().setDateTime(false);
 * arrowSeries.getYValues().setDateTime(false);
 * arrowSeries.setColorEach(true);
 * addRandomArrows();
 * </font></pre></p>
 *
 * <p>Copyright (c) 2005-2019 by Steema Software SL. All Rights
 * Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 *
 */
public class Arrow extends Points {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ValueList endXValues;
    private ValueList endYValues;

    public Arrow() {
        this((IBaseChart)null);
    }

    public Arrow(IBaseChart c) {
        super(c);

        calcVisiblePoints = false;
        endXValues = new ValueList(this, Language.getString("ValuesArrowEndX"));
        endYValues = new ValueList(this, Language.getString("ValuesArrowEndY"));
        point.setInflateMargins(false);
        getMarks().getPen().setVisible( false);
        getMarks().setTransparent(true);
    }

    /**
     * Determines the vertical arrow head size in pixels.<br>
     * It sets the distance from the base of the arrow head to the arrow tip
     * in pixels. It can be used together with ArrowWidth to determine the
     * overall size of the arrow head.
     *
     * @return int
     */
    public int getArrowHeight() {
        return point.getVertSize();
    }

    /**
     * Sets the vertical arrow head size in pixels.<br>
     *
     * @see #getArrowHeight
     * @param value int
     */
    public void setArrowHeight(int value) {
        point.setVertSize(value);
    }

    /**
     * Determines the horizontal arrow head size in pixels. <br>
     * Default value: 4
     *
     * @return int
     */
    public int getArrowWidth() {
        return point.getHorizSize();
    }

    /**
     * Sets the horizontal arrow head size in pixels. <br>
     * Default value: 4
     *
     * @param value int
     */
    public void setArrowWidth(int value) {
        point.setHorizSize(value);
    }

    /**
     * The X0 values for Arrows. <br>
     * Each Arrow has (X0,Y0) and (X1,Y1) coordinates. <br>
     * StartXValues.DateTime property default value is true.
     *
     * @return ValueList
     */
    public ValueList getStartXValues() {
        return vxValues;
    }

    /**
     * Sets X0 values for Arrows. <br>
     *
     * @param value ValueList
     */
    public void setStartXValues(ValueList value) {
        setValueList(vxValues, value);
    }

    /**
     * The Y0 values for Arrows.<br>
     * Each Arrow has (X0,Y0) and (X1,Y1) coordinates. <br>
     *
     * @return ValueList
     */
    public ValueList getStartYValues() {
        return vyValues;
    }

    /**
     * Sets Y0 values for Arrows.<br>
     *
     * @param value ValueList
     */
    public void setStartYValues(ValueList value) {
        setValueList(vyValues, value);
    }

    /**
     * The X1 values for Arrows.<br>
     * Each Arrow has (X0,Y0) and (X1,Y1) coordinates. <br>
     *
     * @return ValueList
     */
    public ValueList getEndXValues() {
        return endXValues;
    }

    /**
     * Sets X1 values for Arrows.<br>
     *
     * @param value ValueList
     */
    public void setEndXValues(ValueList value) {
        setValueList(endXValues, value);
    }

    /**
     * The Y1 values for Arrows.<br>
     * Each Arrow has (X0,Y0) and (X1,Y1) coordinates. <br><br>
     *
     * @return ValueList
     */
    public ValueList getEndYValues() {
        return endYValues;
    }

    /**
     * Sets Y1 values for Arrows.<br>
     *
     * @param value ValueList
     */
    public void setEndYValues(ValueList value) {
        setValueList(endYValues, value);
    }

    /**
     * Adds an Arrow with start and end coordinates.<br>
     * Returns the position of the Arrow in the list.<br>
     * Positions start at zero. <br><br>
     * Each arrow is made of 2 points: <br>
     * (X0,Y0)   The starting arrow point. <br>
     * (X1,Y1)   The arrow head end point. <br>
     *
     * @param x0 double arrow start x coordinate
     * @param y0 double arrow start y coordinate
     * @param x1 double arrow end x coordinate
     * @param y1 double arrow end y coordinate
     * @return int
     */
    public int add(double x0, double y0, double x1, double y1) {
        return add(x0, y0, x1, y1, "", Color.EMPTY);
    }

    /**
     * Adds an Arrow with start and end coordinates and label.<br>
     * Returns the position of the Arrow in the list.<br>
     * Positions start at zero. <br><br>
     * Each arrow is made of 2 points: <br>
     * (X0,Y0)   The starting arrow point. <br>
     * (X1,Y1)   The arrow head end point. <br>
     * Label (overload option) <br>
     *
     * @param x0 double arrow start x coordinate
     * @param y0 double arrow start y coordinate
     * @param x1 double arrow end x coordinate
     * @param y1 double arrow end y coordinate
     * @param text String label text
     * @return int
     */
    public int add(double x0, double y0, double x1, double y1, String text) {
        return add(x0, y0, x1, y1, text, Color.EMPTY);
    }

    /**
     * Returns the position of the Arrow in the list.<br>
     * Positions start at zero. <br><br>
     * Each arrow is made of 2 points: <br>
     * (X0,Y0)   The starting arrow point. <br>
     * (X1,Y1)   The arrow head end point. <br>
     * Color (overload option) <br>
     *
     * @param x0 double arrow start x coordinate
     * @param y0 double arrow start y coordinate
     * @param x1 double arrow end x coordinate
     * @param y1 double arrow end y coordinate
     * @param color Color arrow color
     * @return int
     */
    public int add(double x0, double y0, double x1, double y1, Color color) {
        return add(x0, y0, x1, y1, "", color);
    }

    /**
     * Returns the position of the Arrow in the list.<br>
     * Positions start at zero. <br><br>
     * Each arrow is made of 2 points: <br>
     * (X0,Y0)   The starting arrow point. <br>
     * (X1,Y1)   The arrow head end point. <br>
     * Label (overload option) <br>
     * Color (overload option) <br>
     *
     * @param x0 double arrow start x coordinate
     * @param y0 double arrow start y coordinate
     * @param x1 double arrow end x coordinate
     * @param y1 double arrow end y coordinate
     * @param text String label text
     * @param color Color arrow color
     * @return int
     */
    public int add(double x0, double y0, double x1, double y1, String text,
                   Color color) {
        endXValues.tempValue = x1;
        endYValues.tempValue = y1;
        return super.add(x0, y0, text, color);
    }

    protected void addSampleValues(int numValues) {
        SeriesRandom r = randomBounds(numValues);

        int tmpDifY = Utils.round(r.DifY);
        int tmpDifX = Utils.round(r.StepX * numValues);

        for (int t = 1; t <= numValues; t++) {
            double tmpX0 = r.tmpX + tmpDifX * r.Random();
            double tmpY0 = r.MinY + tmpDifY * r.Random();
            add(tmpX0, tmpY0, tmpX0 + tmpDifX * r.Random(),
                tmpY0 + tmpDifY * r.Random());
        }
    }

    public int clicked(int x, int y) {
        Point p = new Point(x, y);

        for (int t = 0; t < getCount(); t++) {
            ArrowPoints arrow=getArrowPoints(t);

            if (Graphics3D.pointInLineTolerance(p, arrow.p0, arrow.p1,
                                     getPointer().getPen().getWidth())) {
                return t;
            }
        }

        return -1;
    }

    /**
     * The Maximum Value of the Series X Values List.
     *
     * @return double
     */
    public double getMaxXValue() {
        return Math.max(super.getMaxXValue(), endXValues.getMaximum());
    }

    /**
     * The Minimum Value of the Series X Values List.
     *
     * @return double
     */
    public double getMinXValue() {
        return Math.min(super.getMinXValue(), endXValues.getMinimum());
    }

    /**
     * The Maximum Value of the Series Y Values List.
     * @return double
     */
    public double getMaxYValue() {
        return Math.max(super.getMaxYValue(), endYValues.getMaximum());
    }

    /**
     * The Minimum Value of the Series Y Values List.
     *
     * @return double
     */
    public double getMinYValue() {
        return Math.min(super.getMinYValue(), endYValues.getMinimum());
    }

    final private class ArrowPoints {
        Point p0;
        Point p1;
    }

    private ArrowPoints getArrowPoints(int valueIndex)
    {
        ArrowPoints result=new ArrowPoints();
        result.p0= new Point(calcXPos(valueIndex), calcYPos(valueIndex));
        result.p1 = new Point(calcXPosValue(endXValues.value[valueIndex]),
                             calcYPosValue(endYValues.value[valueIndex]));
        return result;
    }

    /**
     * Called internally. Draws the "ValueIndex" point of the Series.<br>
     *
     * @param valueIndex int
     */
    public void drawValue(int valueIndex) {
        ArrowPoints arrow=getArrowPoints(valueIndex);

        Color tmpColor = getValueColor(valueIndex);
        IGraphics3D g = chart.getGraphics3D();

        if (chart.getAspect().getView3D()) {
            getPointer().prepareCanvas(g, tmpColor);
        } else {
            g.setPen(getPointer().getPen());
            g.getPen().setColor( tmpColor);
        }

        g.arrow(chart.getAspect().getView3D(), arrow.p0, arrow.p1, getPointer().getHorizSize(),
                getPointer().getVertSize(), getMiddleZ());
    }

    /**
     * Gets descriptive text.
     *
     * @return String
     */
    public String getDescription() {
        return Language.getString("GalleryArrow");
    }
}

