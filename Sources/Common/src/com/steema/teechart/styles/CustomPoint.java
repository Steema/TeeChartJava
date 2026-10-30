package com.steema.teechart.styles;

import com.steema.teechart.IBaseChart;
import com.steema.teechart.Rectangle;
import com.steema.teechart.events.SeriesMouseListener;
import com.steema.teechart.drawing.Color;
import com.steema.teechart.drawing.IGraphics3D;
import com.steema.teechart.drawing.Point;

/**
 *
 * <p>Title: CustomPoint class</p>
 *
 * <p>Description: Base Series class inherited by a number of TeeChart
 * series styles.</p>
 *
 * <p>Copyright (c) 2005-2019 by Steema Software SL. All Rights
 * Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 *
 */
public class CustomPoint extends BaseLine {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected SeriesPointer point;
    protected CustomStack iStacked = CustomStack.NONE;

    public CustomPoint() {
        this((IBaseChart)null);
    }

    public void assign(Series source) {
        if (source instanceof CustomPoint) {
            CustomPoint tmp = (CustomPoint)source;
            if (tmp.point != null) {
                this.getPointer().assign(tmp.point);
            }
            this.iStacked = tmp.iStacked;
        }
        super.assign(source);
    }

    public void addSeriesMouseListener(SeriesMouseListener l) {
        listenerList.add(SeriesMouseListener.class, l);
    }

    public void removeSeriesMouseListener(SeriesMouseListener l) {
        listenerList.remove(SeriesMouseListener.class, l);
    }

    transient private PointerStyleResolver styleResolver;

    public void setPointerStyleResolver(PointerStyleResolver resolver) {
        this.styleResolver = resolver;
    }

    public void removePointerStyleResolver() {
        this.styleResolver = null;
    }

    public CustomPoint(IBaseChart c) {
        super(c);
    }

    private void setOtherStacked() {
        if (chart != null) {
            for (int t = 0; t < chart.getSeriesCount(); t++) {
                ISeries s = chart.getSeries(t);
                if (s instanceof CustomPoint) {
                    ((CustomPoint) s).iStacked = iStacked;
                }
            }
        }
    }

    /**
     * Defines how multiple series will be displayed.<br><br>
     * Stacking options of Points series are:<br> <br>
     * CustomSeriesStack.None: No overlap action. All Series displayed in
     * individual Z space <br>
     * CustomSeriesStack.Overlap: Series displayed in same Z space (all Series
     * take same ZOrder position). This will result in overpainting of equal
     * Series points. <br>
     * CustomSeriesStack.Stack: Stack Series one above the other. Series begin
     * with lowest index order at bottom, further Series are then plotted above
     * in their respective indexed order with each point taking the cumulative
     * value of lower points as their starting value. <br>
     * CustomSeriesStack.Stack100: Plots take up full Bottom to Top space of
     * the Chart Area resulting in a percentage division by Area to reflect
     * Series values. The Y displacement below each line equates to its
     * percentage value of total. Thus the Last Line will be parallel with/at
     * the top of the Chart and the area below that line reflects its
     * percentage proportion. <br>
     * Default value: CustomStack.None
     *
     * @return CustomStack
     */
    public CustomStack getStacked() {
        return iStacked;
    }

    /**
     * Defines how multiple series will be displayed.
     * Default value: CustomStack.None
     *
     * @param value CustomStack
     */
    public void setStacked(CustomStack value) {
        if (iStacked != value) {
            iStacked = value;
            setOtherStacked();
            invalidate();
        }
    }

    /**
     * Defines all necessary properties of the Series Pointer.<br>
     * It is a subclass of Points series, Line series and all other derived
     * Points series classes like Bubble series.<br>
     * Each point in a Points series is drawn using the Pointer properties.<br>
     * Pointer contains several methods to control the formatting attributes of
     * Points like Pen, Brush, Draw3D, Visible, etc. <br>
     * Default value: Null
     *
     * @return SeriesPointer
     */
    public SeriesPointer getPointer() {
        if (point == null) {
            point = new SeriesPointer(chart, this);
        }
        return point;
    }

    //MM Jan04 set { point=value; }
    public void setChart(IBaseChart c) {
        super.setChart(c);
        if (point != null) {
            point.setChart(chart);
        }
    }
    
    protected boolean sameClassOrigin(Series s) {
      return sameClass(s);
    }

    protected PointerStyle onGetPointerStyle(int valueIndex,
                                              PointerStyle style) {
        PointerStyle s=style;
        if (styleResolver != null) {
            s = styleResolver.getStyle(this, valueIndex, s);
        }
        return s;
    }

    private int axisPosition() {
        if (getYMandatory()) {
            return getVertAxis().iEndPos;
        } else {
            return getHorizAxis().iEndPos;
        }
    }

    private int calcStackedPos(int valueIndex, double value) {
        value += pointOrigin(valueIndex, false);
        if (iStacked == CustomStack.STACK) {
            return Math.min(axisPosition(), calcPosValue(value));
        } else {
            double tmp = pointOrigin(valueIndex, true);
            return (tmp != 0) ? calcPosValue(value * 100.0 / tmp) :
                    axisPosition();
        }
    }

//    private int CalcStackedYPos(int valueIndex,double value)
//    {
//      value+=PointOrigin(valueIndex,false);
//      if (stacked==CustomStack.Stack)
//        return Math.min(GetVertAxis.IEndPos,CalcYPosValue(value));
//      else
//      {
//        double tmp=PointOrigin(valueIndex,true);
//        return tmp != 0 ? CalcYPosValue(value*100.0/tmp) : 0;
//      }
//    }
//
//    private int CalcStackedXPos(int valueIndex,double value)
//    {
//      value+=PointOrigin(valueIndex,false);
//      if (stacked==CustomStack.Stack)
//        return Math.min(GetHorizAxis.IEndPos,CalcXPosValue(value));
//      else
//      {
//        double tmp=PointOrigin(valueIndex,true);
//        return tmp != 0 ? CalcXPosValue(value*100.0/tmp) : 0;
//      }
//    }

    /**
     * For stacked series, PointOrigin returns the sum of ValueIndex values of
     * all series, until this series in the Chart.Series collection order.<br>
     *
     * @param valueIndex int the point index
     * @param sumAll boolean when true, all series are taken into calculation
     * @return double point origin value
     */
    private double pointOrigin(int valueIndex, boolean sumAll) {
        double result = 0;
        double tmpvalue = mandatory.getValue(valueIndex);

        for (int t = 0; t < chart.getSeriesCount(); t++) {
            Series s = chart.getSeries(t);
            if ((!sumAll) && (s == this)) {
                break;
            } else
            if (s.getActive() && (sameClassOrigin(s)) &&
                (s.getCount() > valueIndex)) {
                double tmp = s.getOriginValue(valueIndex);
                if (tmpvalue < 0) {
                    if (tmp < 0) result += tmp;
                }
                else {
                    if (tmp > 0) result += tmp;
                }
            }
        }
        return result;
    }

    public void calcHorizMargins(Margins margins) {
        super.calcHorizMargins(margins);
        getPointer().calcHorizMargins(margins);
    }

    public void calcVerticalMargins(Margins margins) {
        super.calcVerticalMargins(margins);
        getPointer().calcVerticalMargins(margins);
    }

    public void calcZOrder() {
        if (iStacked == CustomStack.NONE) {
            super.calcZOrder();
        } else {
            iZOrder = chart.getMaxZOrder();
        }
    }

    /**
     * For internal use
     *
     * @param valueIndex int
     * @param tmpX int
     * @param tmpY int
     * @param x int
     * @param y int
     * @return boolean
     */
    public boolean clickedPointer(int valueIndex, int tmpX, int tmpY, int x,
                                  int y) {
        return (point != null) && (Math.abs(tmpX - x) < point.getHorizSize()) &&
                (Math.abs(tmpY - y) < point.getVertSize());
    }

    protected void drawLegendShape(IGraphics3D g, int valueIndex,
                                   Rectangle rect) {
        if (getPointer().getVisible()) {
            Color tmpColor = (valueIndex == -1) ? getColor() :
                             getValueColor(valueIndex);
            point.drawLegendShape(g, tmpColor, rect, false);
        } else {
            super.drawLegendShape(g, valueIndex, rect);
        }
    }

    protected void drawMark(int valueIndex, String s,
                            SeriesMarksPosition position) {
        getMarks().zPosition = getStartZ();
        if (getYMandatory()) {
            position = getMarks().applyArrowLength(position);
        }

        super.drawMark(valueIndex, s, position);
    }

    /**
     * Draws series pointer to the Canvas.
     * It displays a pointer at the specified px and py screen pixel
     * coordinates with the tmpHoriz and tmpVert size dimensions.
     *
     * @param aX int
     * @param aY int
     * @param aColor Color
     * @param valueIndex int
     */
    public void drawPointer(int aX, int aY, Color aColor, int valueIndex) {
        point.prepareCanvas(chart.getGraphics3D(), aColor);
        
        if (point.getColor() != getColor()) {
            aColor = point.getColor();
        }
        
        PointerStyle tmpStyle = onGetPointerStyle(valueIndex, point.getStyle());
        point.draw(aX, aY, aColor, tmpStyle);
    }

    /**
     * Returns vertical screen position for a given point.
     * This coordinate is calculated using the Series associated Vertical Axis.
     *
     * @param valueIndex int
     * @return int
     */
    public int calcYPos(int valueIndex) {
        if ((!getYMandatory()) || (iStacked == CustomStack.NONE) ||
            (iStacked == CustomStack.OVERLAP)) {
            return super.calcYPos(valueIndex);
        } else {
            return calcStackedPos(valueIndex, vyValues.value[valueIndex]);
        }
    }

    /**
     * Returns horizontal screen position for a given point.
     * This coordinate is calculated using the Series associated Horizontal
     * Axis.
     *
     * @param valueIndex int
     * @return int
     */
    public int calcXPos(int valueIndex) {
        if ((getYMandatory()) || (iStacked == CustomStack.NONE) ||
            (iStacked == CustomStack.OVERLAP)) {
            return super.calcXPos(valueIndex);
        } else {
            return calcStackedPos(valueIndex, vxValues.value[valueIndex]);
        }
    }

    protected int getOriginPos(int valueIndex) {
        if ((iStacked == CustomStack.NONE) ||
            (iStacked == CustomStack.OVERLAP)) {
            if (yMandatory) {
                return getVertAxis().getInverted() ? getVertAxis().iStartPos :
                        getVertAxis().iEndPos;
            } else {
                return getHorizAxis().getInverted() ? getHorizAxis().iEndPos :
                        getHorizAxis().iStartPos;
            }
        } else {
            return calcStackedPos(valueIndex, 0);
        }
    }

    /**
     * Called internally. Draws the "ValueIndex" point of the Series.
     *
     * @param valueIndex int
     */
    public void drawValue(int valueIndex) {
        if (!isNull(valueIndex) || (getTreatNulls() == TreatNullsStyle.IGNORE)) {
            drawPointer(calcXPos(valueIndex), calcYPos(valueIndex),
                        getValueColor(valueIndex), valueIndex);
        }
    }

    /**
     * Returns the ValueIndex of the "clicked" point in the Series.
     *
     * @param x int
     * @param y int
     * @return int
     */
    public int clicked(int x, int y) {
        if (chart != null) {
            Point p = chart.getGraphics3D().calculate2DPosition(x, y, getStartZ());
            x = p.x;
            y = p.y;
        }

        int result = super.clicked(x, y);

        if ((result == -1) && (firstVisible > -1) && (lastVisible > -1)) {
        	for (int t = lastVisible; t >= firstVisible; t--) {
                if (clickedPointer(t, calcXPos(t), calcYPos(t), x, y)) {
                    //doClickPointer(t, x, y);
                    return t;
                }
            }
        }

        return result;
    }

    /**
     * Returns the Maximum Value of the Series X Values List.
     *
     * @return double
     */
    public double getMaxXValue() {
        double result = 0;
        if (yMandatory) {
            result = super.getMaxXValue();
        } else {
            if (iStacked == CustomStack.STACK100) {
                result = 100;
            } else {
                result = super.getMaxXValue();
                if (iStacked == CustomStack.STACK) {
                    for (int t = 0; t < getCount(); t++) {
                        result = Math.max(result,
                                          pointOrigin(t, false) +
                                          getXValues().value[t]);
                    }
                }
            }
        }
        return result;
    }

    /**
     * Returns the Minimum Value of the Series X Values List.
     *
     * @return double
     */
    public double getMinXValue() {
        if ((!getYMandatory()) && (iStacked == CustomStack.STACK100)) {
            return 0;
        } else {
            return super.getMinXValue();
        }
    }

    /**
     * Returns the Maximum Value of the Series Y Values List.
     *
     * @return double
     */
    public double getMaxYValue() {
        if (getYMandatory()) {
            if (iStacked == CustomStack.STACK100) {
                return 100;
            } else {
                double result = super.getMaxYValue();
                if (iStacked == CustomStack.STACK) {
                    for (int t = 0; t < getCount(); t++) {
                        result = Math.max(result,
                                          pointOrigin(t, false) +
                                          vyValues.value[t]);
                    }
                }
                return result;
            }
        } else {
            return super.getMaxYValue();
        }
    }

    /**
     * Returns the Minimum Value of the Series Y Values List.
     *
     * @return double
     */
    public double getMinYValue() {
        return (getYMandatory() && (iStacked == CustomStack.STACK100)) ? 0 :
                super.getMinYValue();
    }
}

