package com.steema.teechart.styles;


import com.steema.teechart.languages.Language;
import com.steema.teechart.Rectangle;
import com.steema.teechart.drawing.Color;
import com.steema.teechart.drawing.Point;
import com.steema.teechart.IBaseChart;
import com.steema.teechart.drawing.IGraphics3D;
import com.steema.teechart.misc.Utils;
import com.steema.teechart.drawing.Graphics3D;

/**
 *
 * <p>Title:  class</p>
 *
 * <p>Description: Bubble Series.</p>
 *
 *  <p>Example:
 * <pre><font face="Courier" size="4">
 *   bubbleSeries = new com.steema.teechart.styles.Bubble(myChart.getChart());
 *
 *   bubbleSeries.getXValues().setDateTime(true);
 *   bubbleSeries.getRadiusValues().setDateTime(false);
 *   bubbleSeries.getRadiusValues().setName("Radius");
 *   bubbleSeries.getRadiusValues().setOrder(ValueListOrder.NONE);
 *   bubbleSeries.setHorizontalAxis(HorizontalAxis.TOP);
 *   bubbleSeries.getMarks().setArrowLength(0);
 *   bubbleSeries.getMarks().setClip(true);
 *   bubbleSeries.getMarks().getFont().setColor(Color.WHITE);
 *   bubbleSeries.getMarks().getFont().setSize(16);
 *   bubbleSeries.getMarks().getFont().setItalic(true);
 *   bubbleSeries.getMarks().getFrame().setVisible(false);
 *   bubbleSeries.getMarks().setTransparent(true);
 *   bubbleSeries.getMarks().setVisible(false);
 *   bubbleSeries.getPointer().setHorizSize(14);
 *   bubbleSeries.getPointer().setVertSize(14);
 *   bubbleSeries.getPointer().setInflateMargins(false);
 *   bubbleSeries.setVisible(true);
 * </font></pre></p>

 * <p>Copyright (c) 2005-2019 by Steema Software SL. All Rights Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 *
 */
public class Bubble extends Points {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private boolean squared = true;
    private ValueList radiusValues;

    public Bubble() {
        this((IBaseChart)null);
    }

    public Bubble(IBaseChart c) {
        super(c);

        radiusValues = new ValueList(this, Language.getString("ValuesBubbleRadius"));
        point.setInflateMargins(false);
        point.setStyle(PointerStyle.CIRCLE); // a Bubble is a circle (by default)
        point.allowChangeSize = false;
        getMarks().getPen().setDefaultVisible(false);
        getMarks().setTransparent(true);
        bColorEach = true;
    }
    
    private void PreparePointer(int valueIndex)
    { 
      int tmpSize = this.getVertAxis().calcSizeValue(radiusValues.getValue(valueIndex));

      if (squared)
        getPointer().setHorizSize(tmpSize);
      else
      	getPointer().setHorizSize(getHorizAxis().calcSizeValue(radiusValues.getValue(valueIndex)));
      getPointer().setVertSize(tmpSize);
    }

    public boolean clickedPointer(int valueIndex, int tmpX, int tmpY, int x, int y)
    {
      PointerStyle tmpStyle = getPointer().getStyle();
      Rectangle r;
      Point p = new Point();

      PreparePointer(valueIndex);

      tmpStyle = onGetPointerStyle(valueIndex, point.getStyle());

      r = new Rectangle(tmpX - getPointer().getHorizSize(), tmpY - getPointer().getVertSize(),
      		getPointer().getHorizSize() * 2, getPointer().getVertSize() * 2);

      p.x = x;
      p.y = y;

      final Point centrePoint = r.center();

      switch (tmpStyle.getValue())
      {
        case 7 /*PointerStyle.DIAMOND*/: return  Graphics3D.pointInPolygon(p, new Point[] { new Point(centrePoint.x,r.getTop()),
                                                   new Point(r.getRight(),centrePoint.y),
                                                   new Point(centrePoint.x,r.getBottom()),
                                                   new Point(r.getLeft(),centrePoint.y)});
        case 10 /*PointerStyle.LEFTTRIANGLE*/: return  Graphics3D.pointInHorizTriangle(p, r.getTop(), r.getBottom(), r.getRight(), r.getLeft());
        case 11 /*PointerStyle.RIGHTTRIANGLE*/: return  Graphics3D.pointInHorizTriangle(p, r.getTop(), r.getBottom(), r.getLeft(), r.getRight());
        case 2 /*PointerStyle.TRIANGLE*/: return  Graphics3D.pointInTriangle(p, r.getLeft(), r.getRight(), r.getBottom(), r.getTop());
        case 3 /*PointerStyle.DOWNTRIANGLE*/: return  Graphics3D.pointInTriangle(p, r.getLeft(), r.getRight(), r.getTop(), r.getBottom());
        case 14 /*PointerStyle.HEXAGON*/:
        case 1 /*PointerStyle.CIRCLE*/: return  Graphics3D.pointInEllipse(p, r);
        default:
          return  (tmpStyle != PointerStyle.NOTHING) &&
                  (Math.abs(tmpX - x) < getPointer().getHorizSize()) &&
                  (Math.abs(tmpY - y) < getPointer().getVertSize());
      }
    }

    /**
     * Determines how the Bubble size is calculated.<br>
     * By default, the horizontal and vertical Bubble sizes are equal to the
     * radius of each bubble. When Squared is false, Bubble horizontal and
     * vertical sizes are calculated independently based on Series axis scales.
     * <br>
     * Default value: true
     *
     * @return boolean
     */
    public boolean getSquared() {
        return squared;
    }

    /**
     * Determines how the Bubble size is calculated.<br>
     * Default value: true
     *
     * @param value boolean
     */
    public void setSquared(boolean value) {
        squared = setBooleanProperty(squared, value);
    }

    private double applyRadius(double value, ValueList aList, boolean increment) {
        double result = value;
        for (int t = 0; t < getCount() - 1; t++) {
            if (increment) {
                result = Math.max(result, aList.value[t] + radiusValues.value[t]);
            } else {
                result = Math.min(result, aList.value[t] - radiusValues.value[t]);
            }
        }
        return result;
    }

    protected int numSampleValues() {
        return 8;
    }

    protected void addSampleValues(int numValues) {
        SeriesRandom r = randomBounds(numValues);

        // some sample values to see something in design mode...
        for (int t = 1; t <= numValues; t++) {
            add(r.tmpX, // X
                Utils.round(r.DifY * r.Random()), // Y
                (r.DifY / 15.0) + Utils.round(r.DifY / (10 + 15 * r.Random()))); // Radius
            r.tmpX += r.StepX;
        }
    }

    protected void drawLegendShape(IGraphics3D g, int valueIndex, Rectangle r) {
        int tmp = Math.min(r.width, r.height);
        point.setHorizSize(tmp);
        point.setVertSize(tmp);
        super.drawLegendShape(g, valueIndex, r);
    }

    /**
     * Called internally. Draws the "ValueIndex" point of the Series.
     *
     * @param valueIndex int
     */
    public void drawValue(int valueIndex) {

        /* This overrided method is the main paint for bubble points.
         The bubble effect is achieved by changing the Pointer.Size based
         on the corresponding Radius value for each point in the series.  */

        int tmpSize = calcYSizeValue(radiusValues.value[valueIndex]);
        point.setHorizSize(squared ? tmpSize :
                           calcXSizeValue(radiusValues.value[valueIndex]));
        point.setVertSize(tmpSize);
        drawPointer(calcXPos(valueIndex), calcYPos(valueIndex),
                    getValueColor(valueIndex), valueIndex);

        /* dont call inherited to avoid drawing the "pointer" */
    }

    /**
     * Adds a new Bubble point to the Series Points List and color.<br>
     * The Bubble point is assigned to be at AX, AY coordinates and have
     * ARadius and Color parameters. The Label parameter is used to
     * draw Axis Labels, Bubble Marks and Legend.
     *
     * @param x double x coordinate of bubble point.
     * @param y double y coordinate of bubble point.
     * @param radius double
     * @param color Color
     * @return int
     */
    public int add(double x, double y, double radius, Color color) {
        return add(x, y, radius, "", color);
    }

    /**
     * Adds a new Bubble point to the Series Points List.<br>
     * The Bubble point is assigned to be at AX, AY coordinates and has
     * ARadius  parameter. The Label parameter is used to
     * draw Axis Labels, Bubble Marks and Legend.
     *
     * @param x double x coordinate of bubble point.
     * @param y double y coordinate of bubble point.
     * @param radius double
     * @return int
     */
    public int add(double x, double y, double radius) {
        return add(x, y, radius, "", Color.EMPTY);
    }

    /**
     * Adds a new Bubble point to the Series Points List and label.<br>
     * The Bubble point is assigned to be at AX, AY coordinates and have
     * ARadius and Label parameters. The Label parameter is used to
     * draw Axis Labels, Bubble Marks and Legend.
     *
     * @param x double x coordinate of bubble point.
     * @param y double y coordinate of bubble point.
     * @param radius double
     * @param text String
     * @return int
     */
    public int add(double x, double y, double radius, String text) {
        return add(x, y, radius, text, Color.EMPTY);
    }

    /**
     * Adds a new Bubble point to the Series Points List, label and color.<br>
     * The Bubble point is assigned to be at AX, AY coordinates and have
     * ARadius, Label and Color parameters. The Label parameter is used to
     * draw Axis Labels, Bubble Marks and Legend.
     *
     * @param x double x coordinate of bubble point.
     * @param y double y coordinate of bubble point.
     * @param radius double
     * @param text String
     * @param color Color
     * @return int
     */
    public int add(double x, double y, double radius, String text, Color color) {
        radiusValues.tempValue = radius;
        return add(x, y, text, color);
    }

    /**
     * It's used to validate the DataSource property both at design and
     * run-time. <br>
     * It returns false if the Value parameter is the same as Self.
     *
     * @param value ISeries
     * @return boolean
     */
    public boolean isValidSourceOf(ISeries value) {
        return value instanceof Bubble; // Only Bubbles can be assigned to Bubbles.
    }

    /**
     * The Maximum Value of the Series Y Values List.
     *
     * @return double
     */
    public double getMaxYValue() {
        return applyRadius(super.getMaxYValue(), vyValues, true);
    }

    /**
     * The Minimum Value of the Series Y Values List.
     *
     * @return double
     */
    public double getMinYValue() {
        return applyRadius(super.getMinYValue(), vyValues, false);
    }

    /**
     * The maximum Z value.
     *
     * @return double
     */
    public double getMaxZValue() {
        if (point.getDraw3D()) {
            return radiusValues.getMaximum();
        } else {
            return super.getMaxZValue();
        }
    }

    /**
     * The minimum Z value.
     *
     * @return double
     */
    public double getMinZValue() {
        if (point.getDraw3D()) {
            return -radiusValues.getMaximum();
        } else {
            return super.getMinZValue();
        }
    }

    /**
     * A TList object that stores each Bubble point Radius value.<br>
     * You can change Radius values by using the RadiusValues.Value[] array
     * of doubles method
     *
     * @return ValueList
     */
    public ValueList getRadiusValues() {
        return radiusValues;
    }

    /**
     * A TList object that stores each Bubble point Radius value.<br>
     *
     * @param value ValueList
     */
    public void setRadiusValues(ValueList value) {
        setValueList(radiusValues, value);
    }

    /**
     * Controls which color will be drawn on the bubbles.<br>
     * If false, all points will be drawn using Series Series.Color.
     * If true, each Series point will be "colored" with its corresponding
     * point color. <br>
     * You can change this property both at design and runtime.<br>
     * Default value: true
     *
     * @return boolean
     */
    public boolean getColorEach() {
        return bColorEach;
    }

    /**
     * Gets descriptive text.
     *
     * @return String
     */
    public String getDescription() {
        return Language.getString("GalleryBubble");
    }
}

