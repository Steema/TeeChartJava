package com.steema.teechart.styles;

import java.util.ArrayList;
import com.steema.teechart.languages.Language;

import com.steema.teechart.drawing.Color;
import com.steema.teechart.DateTime;

import com.steema.teechart.IBaseChart;
import com.steema.teechart.axis.Axis;
import com.steema.teechart.drawing.ChartBrush;
import com.steema.teechart.drawing.IGraphics3D;

/**
 *
 * <p>Title: Custom3D class</p>
 *
 * <p>Description: Custom3D Series.</p>
 *
 * <p>Copyright (c) 2005-2019 by Steema Software SL. All Rights Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 *
 */
public abstract class Custom3D extends Series {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int timesZOrder = 3;
    protected ValueList vzValues;

    protected Custom3D() {
        this(null);
    }

    protected Custom3D(IBaseChart c) {
        super(c);

        hasZValues = true;
        calcVisiblePoints = false;
        vxValues.setOrder(ValueListOrder.NONE);
        if(vzValues == null) vzValues = new ValueList(this, Language.getString("ValuesZ"));
    }

    public void calcZOrder() {
        super.calcZOrder();
        chart.setMaxZOrder(timesZOrder);
    }


    protected void drawMark(int valueIndex, String s,
                            SeriesMarksPosition position) {
        getMarks().setZPosition(calcZPos(valueIndex));
        getMarks().applyArrowLength(position);
        super.drawMark(valueIndex, s, position);
    }

    protected void prepareLegendCanvas(IGraphics3D g, int valueIndex,
                                       Color backColor,
                                       ChartBrush aBrush) {
        super.prepareLegendCanvas(g, valueIndex, backColor, aBrush);
        if (chart.getLegend().getSymbol().getContinuous()) {
            g.getPen().setVisible( false);
        }
    }

    /**
     * Values defining Z grid point positions.<br>
     * ZValues is a standard IValueList object.
     *
     * @return ValueList
     */
    public ValueList getZValues() {
        if(vzValues == null) vzValues = new ValueList(this, Language.getString("ValuesZ"));
        return vzValues;
    }
    
    /**
     * Adds the X, Y, Z and Color arrays.
     *
     * @param xValues double[]
     * @param yValues double[]
     * @param zValues double[]
     * @param colorValues Color[]
     */
    public void add(double[] xValues, double[] yValues, double[] zValues,
            Color[] colorValues) {
        getColors().clear();

        for (int i = 0; i < colorValues.length; ++i) {
            getColors().add(colorValues[i]);
        }
        
        add(xValues, yValues, zValues);
    }
    
    /**
     * Adds the X, Y and Z arrays.
     *
     * @param xValues double[]
     * @param yValues double[]
     * @param zValues double[]
     */
    public void add(double[] xValues, double[] yValues, double[] zValues) {
        int numPoints = yValues.length;
        getZValues().count = numPoints;
        getZValues().value = zValues;
        getZValues().statsOk = false;
        
        add(xValues, yValues);
    }
    
    /**
     * Adds the X, Y, Z and colorValues arrays.
     *
     * @param xValues ArrayList
     * @param yValues ArrayList
     * @param zValues ArrayList
     * @param colorValues ArrayList
     */
    public void add(ArrayList xValues, ArrayList yValues, ArrayList zValues,
            ArrayList colorValues) {
        getColors().clear();

        for (int i = 0; i < colorValues.size(); ++i) {
            getColors().add((Color)colorValues.get(i));
        }
        
        add(xValues, yValues, zValues);
    }    

    /**
     * Adds the X, Y and Z arrays.
     *
     * @param xValues ArrayList
     * @param yValues ArrayList
     * @param zValues ArrayList
     */
    public void add(ArrayList xValues, ArrayList yValues, ArrayList zValues) {
        int numPoints = yValues.size();

        getZValues().count = numPoints;
        getZValues().value = convertArray(zValues, numPoints);
        getZValues().statsOk = false;

        add(xValues, yValues);
    }

    /**
     * Adds a new point to a 3D Series.
     *
     * @param x double
     * @param y double
     * @param z double
     * @return int
     */
    public int add(double x, double y, double z) {
        return add(x, y, z, "", Color.EMPTY);
    }

    /**
     * Adds a new point to a 3D Series and color.
     *
     * @param x double
     * @param y double
     * @param z double
     * @param color Color
     * @return int
     */
    public int add(double x, double y, double z, Color color) {
        return add(x, y, z, "", color);
    }

    /**
     * Adds a new point to a 3D Series and label.
     *
     * @param x double
     * @param y double
     * @param z double
     * @param text String
     * @return int
     */
    public int add(double x, double y, double z, String text) {
        return add(x, y, z, text, Color.EMPTY);
    }

    /**
     * Adds a new point to a 3D Series, label and color.
     *
     * @param x double
     * @param y double
     * @param z double
     * @param text String
     * @param color Color
     * @return int
     */
    public int add(double x, double y, double z, String text, Color color) {
        getZValues().tempValue = z;
        return add(x, y, text, color);
    }

    /**
     * Adds a new Datetime point to a 3D Series, label and color.
     *
     * @param aDate DateTime Datetime value
     * @param y double Y value
     * @param z double Z value
     * @param text String
     * @param color Color Point Color
     * @return int index of added point
     */
    public int add(DateTime aDate, double y, double z, String text, Color color) {
        getZValues().tempValue = z;
        return add(aDate, y, text, color);
    }

    protected int calcZPos(int valueIndex) {
        return chart.getAxes().getDepth().calcYPosValue(getZValues().value[valueIndex]);
    }

    /**
     * Checks if given series object instance is a Custom3D instance.
     *
     * @param value Series
     * @return boolean
     */
    public boolean isValidSourceOf(Series value) {
        return value instanceof Custom3D;
    }

    /**
     * Checks if given series object instance is a Custom3D instance.
     *
     * @return double
     */
    public double getMaxZValue() {
        return getZValues().getMaximum();
    }

    /**
     * Returns Minimum Z Value.
     *
     * @return double
     */
    public double getMinZValue() {
        return getZValues().getMinimum();
    }

    /**
     * For internal use.<br>
     * Default value: 3
     *
     * @return int
     */
    public int getTimesZOrder() {
        return timesZOrder;
    }

    /**
     * For internal use.<br>
     * Default value: 3
     *
     * @param value int
     */
    public void setTimesZOrder(int value) {
        timesZOrder = setIntegerProperty(timesZOrder, value);
    }
    
    
    @Override
    public ValueList valueListOfAxis(Axis a) {
        if (a.isDepthAxis) return getZValues();
        else return super.valueListOfAxis(a);
    }


    // Internal note: This method cannot return SeriesXYZPoint class (Java restriction)
    // In C#, this method is a "new" override.
    /**
     * XYZ Point characteristics
     *
     * @param index int
     * @return SeriesXYPoint
     */
    public SeriesXYPoint getPoint(int index) {
        return new SeriesXYZPoint(this, index);
    }
}

