package com.steema.teechart.styles;


import com.steema.teechart.languages.Language;
import com.steema.teechart.drawing.IGraphics3D;
import com.steema.teechart.drawing.ChartPen;
import com.steema.teechart.drawing.Color;
import com.steema.teechart.drawing.Point;
import com.steema.teechart.IBaseChart;
import com.steema.teechart.misc.Utils;

/**
 *
 * <p>Title: Vector3D class</p>
 *
 * <p>Description: Vector3D Series.</p>
 *
 * <p>Copyright (c) 2005-2019 by Steema Software SL. All Rights
 * Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 *
 */
public class Vector3D extends Custom3DPalette {
    /**
	 * 
	 */
    private static final long serialVersionUID = 1L;
    private int arrowHeight;
    private int arrowWidth;
    private ChartPen startArrow;
    private ChartPen endArrow;
    private ValueList endXValues; //<-- Vector end X values
    private ValueList endYValues; // <-- Vector end Y values
    private ValueList endZValues;

    transient private Color tmpColor;
    transient private double tmpSin;
    transient private double tmpCos;

    public Vector3D(IBaseChart c) {
        super(c);
        arrowHeight = 4;
        arrowWidth = 4;
        endXValues = new ValueList(this, "EndXValues");
        endYValues = new ValueList(this, "EndYValues");
        endZValues = new ValueList(this, "EndZValues");
    }

    public Vector3D() {
        this((IBaseChart)null);
    }
    
    public void assign(Series source) {
        if (source instanceof Vector3D) {
            Vector3D tmp = (Vector3D)source;
            tmp.setArrowHeight(((Vector3D)source).getArrowHeight());
            tmp.setArrowWidth(((Vector3D)source).getArrowWidth());
        }
        super.assign(source);
    }     

    /**
     * The arrow height in pixels.<br>
     * Default value: 4
     *
     * @return int
     */
    public int getArrowHeight() {
        return arrowHeight;
    }

    /**
     * Sets the arrow height in pixels.<br>
     * Default value: 4
     *
     * @param value int
     */
    public void setArrowHeight(int value) {
        arrowHeight = setIntegerProperty(arrowHeight, value);
    }

    /**
     * The arrow width in pixels.<br>
     * Default value: 4
     *
     * @return int
     */
    public int getArrowWidth() {
        return arrowWidth;
    }

    /**
     * Seets the arrow width in pixels.<br>
     * Default value: 4
     *
     * @param value int
     */
    public void setArrowWidth(int value) {
        arrowWidth = setIntegerProperty(arrowWidth, value);
    }

    /**
     * List of values representing the end X coordinates of vector lines.
     *
     * @return ValueList
     */
    public ValueList getEndXValues() {
        return endXValues;
    }

    //set{FEndXValues.Assign(value); Repaint();}

    /**
     * List of values representing the end Y coordinates of vector lines.
     *
     * @return ValueList
     */
    public ValueList getEndYValues() {
        return endYValues;
    }

    //set{FEndYValues.Assign(value); Repaint();}

    /**
     * List of values representing the end Z coordinates of vector lines.
     *
     * @return ValueList
     */
    public ValueList getEndZValues() {
        return endZValues;
    }

    //set{FEndZValues.Assign(value); Repaint();}

    /**
     * When Visible, this pen is used to display arrows at the end coordinate
     * of vector lines.
     *
     * @return ChartPen
     */
    public ChartPen getEndArrow() {
        if (endArrow == null) {
            endArrow = new ChartPen(chart, true);
        }
        return endArrow;
    }

    /**
     * When Visible, this pen is used to display arrows at the start coordinate
     * of vector lines.
     *
     * @return ChartPen
     */
    public ChartPen getStartArrow() {
        if (startArrow == null) {
            startArrow = new ChartPen(chart, false);
        }
        return startArrow;
    }

    protected void addSampleValues(int numValues) {

        double u = 0;
        double v = 0;
        double tmp = numValues * 0.6;
        double tmpPi = 8 * Math.PI / numValues;

        for (int t = 1; t <= numValues; ++t) {
            double x0 = u + Math.sin(v);
            double y0 = u;
            double z0 = Math.cos(v);

            double x1 = x0 + Math.sin(v);
            double y1 = y0 + tmp * Math.cos(v) * Math.sin(v);
            double z1 = z0 + Math.cos(v) * u;

            add(x0, y0, z0, x1, y1, z1);

            u += 1;
            v += tmpPi;
        }
    }

    /**
     * Adds a new 3D vector line to series from (x0, y0, z0) to (x1, y1, z1) as
     * axis values.
     *
     *
     * <p>Example:
     * <pre><font face="Courier" size="4">
     * series.add( x0,y0,z0 , x1,y1,z1 ) ;
     * </font></pre></p>
     *
     * @param x0 double
     * @param y0 double
     * @param z0 double
     * @param x1 double
     * @param y1 double
     * @param z1 double
     * @return int
     */
    public int add(double x0, double y0, double z0, double x1, double y1,
                   double z1) {
        getEndXValues().tempValue = x1;
        getEndYValues().tempValue = y1;
        getEndZValues().tempValue = z1;
        return add(x0, y0, z0);
    }

    /**
     * Adds a new 3D vector line to series from (x0, y0, z0) to (x1, y1, z1) as
     * axis values with a specified text and color.
     *
     * @param x0 double
     * @param y0 double
     * @param z0 double
     * @param x1 double
     * @param y1 double
     * @param z1 double
     * @param text String
     * @param color Color
     * @return int
     */
    public int add(double x0, double y0, double z0, double x1, double y1,
                   double z1, String text, Color color) {
        getEndXValues().tempValue = x1;
        getEndYValues().tempValue = y1;
        getEndZValues().tempValue = z1;
        return add(x0, y0, z0, text, color);
    }

    private void drawArrow(ChartPen aPen, Point p, int z) {
        IGraphics3D g = chart.getGraphics3D();

        if (aPen.getColor() == Color.EMPTY) {
            g.getPen().assign(aPen, tmpColor);
        } else {
            g.getPen().assign(aPen);
        }

        g.moveTo(p.x +
                 Utils.round( -arrowWidth * tmpCos - arrowHeight * tmpSin),
                 p.y +
                 Utils.round( +arrowWidth * tmpSin - arrowHeight * tmpCos));
        g.lineTo(p);
        g.lineTo(p.x +
                 Utils.round( -arrowWidth * tmpCos + arrowHeight * tmpSin),
                 p.y +
                 Utils.round( +arrowWidth * tmpSin + arrowHeight * tmpCos));
    }

    public void drawValue(int valueIndex) {
        Point startPoint = new Point();
        Point tmpPoint = new Point();
        Point endPoint = new Point();

        IGraphics3D g = chart.getGraphics3D();

        tmpColor = getValueColor(valueIndex);

        if (getPen().getColor() == Color.BLACK) {
            g.getPen().assign(getPen(), tmpColor);
        } else {
            g.getPen().assign(getPen());
        }

        startPoint.x = calcXPos(valueIndex);
        startPoint.y = calcYPos(valueIndex);
        int tmpStartZ = calcZPos(valueIndex);

        g.moveTo(startPoint, tmpStartZ);

        endPoint.x = calcXPosValue(getEndXValues().value[valueIndex]);
        endPoint.y = calcYPosValue(getEndYValues().value[valueIndex]);
        int tmpEndZ = chart.getAxes().getDepth().calcYPosValue(getEndZValues().value[valueIndex]);

        if (getPen().getVisible()) {
            g.lineTo(endPoint.x, endPoint.y, tmpEndZ);
        }

        if (getStartArrow().getVisible() || getEndArrow().getVisible()) {
            tmpPoint = g.calc3DPoint(startPoint.x, startPoint.y, tmpStartZ);
            startPoint.x = tmpPoint.x;
            startPoint.y = tmpPoint.y;
            tmpPoint = g.calc3DPoint(endPoint.x, endPoint.y, tmpEndZ);
            endPoint.x = tmpPoint.x;
            endPoint.y = tmpPoint.y;

            double tmpAngle = Utils.atan2(startPoint.y - endPoint.y,
                                  endPoint.x - startPoint.x);

            tmpSin = Math.sin(tmpAngle);
            tmpCos = Math.cos(tmpAngle);

            if (getStartArrow().getVisible()) {
                drawArrow(getStartArrow(), startPoint, tmpStartZ);
            }
            if (getEndArrow().getVisible()) {
                drawArrow(getEndArrow(), endPoint, tmpEndZ);
            }
        }

    }

    public boolean isValidSourceOf(ISeries value) {
        return value instanceof Vector3D;
    }

    protected int numSampleValues() {
        return 250;
    }

    /**
     * The biggest value in the Series XValues array.
     *
     * @return double
     */
    public double getMaxXValue() {
        return Math.max(super.getMaxXValue(), endXValues.getMaximum());
    }

    /**
     * The smallest value in the Series XValues array.
     *
     * @return double
     */
    public double getMinXValue() {
        return Math.min(super.getMinXValue(), endXValues.getMinimum());
    }

    /**
     * The biggest value in the Series YValues array.
     *
     * @return double
     */
    public double getMaxYValue() {
        return Math.max(super.getMaxYValue(), endYValues.getMaximum());
    }

    /**
     * The smallest value in the Series YValues array.
     *
     * @return double
     */
    public double getMinYValue() {
        return Math.min(super.getMinYValue(), endYValues.getMinimum());
    }

    /**
     * The biggest value in the Series ZValues array.
     *
     * @return double
     */
    public double getMaxZValue() {
        return Math.max(super.getMaxZValue(), endZValues.getMaximum());
    }

    /**
     * The smallest value in the Series ZValues array.
     *
     * @return double
     */
    public double getMinZValue() {
        return Math.min(super.getMinZValue(), endZValues.getMinimum());
    }

    /**
     * Gets descriptive text.
     *
     * @return String
     */
    public String getDescription() {
        return Language.getString("GalleryVector3D");
    }
}

