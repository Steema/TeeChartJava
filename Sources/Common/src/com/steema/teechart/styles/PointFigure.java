package com.steema.teechart.styles;


import com.steema.teechart.languages.Language;
import com.steema.teechart.legend.LegendTextStyle;
import com.steema.teechart.drawing.Color;
import com.steema.teechart.IBaseChart;

/**
 *
 * <p>Title: PointFigure class</p>
 *
 * <p>Description: PointFigure Series.</p>
 *
 * <p>Example:
 * <pre><font face="Courier" size="4">
 * series1 = new com.steema.teechart.styles.PointFigure(myChart.getChart());
 * series1.fillSampleValues();
 * series1.setBoxSize(5);
 * series1.getMarks().setVisible(false);
 * </font></pre></p>
 *
 * <p>Copyright (c) 2005-2019 by Steema Software SL. All Rights
 * Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 *
 */
public class PointFigure extends OHLC {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private double boxSize = 1;
    private double reversal = 3;
    private SeriesPointer up, down;

    public PointFigure() {
        this((IBaseChart)null);
    }

    public PointFigure(IBaseChart c) {
        super(c);

        up = new SeriesPointer(c, this);
        up.setStyle( PointerStyle.DIAGCROSS);
        up.getBrush().setColor( Color.GREEN);

        down = new SeriesPointer(c, this);
        down.setStyle(PointerStyle.CIRCLE);
        down.getBrush().setColor(Color.RED);

        vxValues.dateTime = false; // default datetime = false
    }
    
    public void assign(Series source) {
        if (source instanceof PointFigure) {
            PointFigure tmp = (PointFigure)source;
            tmp.setChart(((PointFigure) source).getChart());
            tmp.setBoxSize(((PointFigure)source).getBoxSize());
        }
        super.assign(source);
    }    

    /**
     * Determines the amount in price scales that is multiplied by
     * ReversalAmount.<br>
     * ReversalAmount by BoxSize defines the maximum price difference allowed
     * before adding a new column of symbols. <br>
     * BoxSize itself defines the minimum change in price to draw a new symbol
     * in the same column. <br>
     * Default value: 1
     *
     * @return double
     */
    public double getBoxSize() {
        return boxSize;
    }

    /**
     * Determines the amount in price scales that is multiplied by
     * ReversalAmount.<br>
     * Default value: 1
     *
     * <p>Example:
     * <pre><font face="Courier" size="4">
     * series1.setBoxSize(5);
     * </font></pre></p>
     *
     * @param value double
     */
    public void setBoxSize(double value) {
        boxSize = setDoubleProperty(boxSize, value);
    }

    /**
     * The number that is multiplied by BoxSize.<br>
     * ReversalAmount by BoxSize defines the maximum price difference allowed
     * before adding a new column of symbols. <br>
     * Default value: 3
     *
     * @return double
     */
    public double getReversalAmount() {
        return reversal;
    }

    /**
     * Determines the number that is multiplied by BoxSize.<br>
     * Default value: 3
     *
     * @param value double
     */
    public void setReversalAmount(double value) {
        reversal = setDoubleProperty(reversal, value);
    }

    private void drawColumn(SeriesPointer pointer, double fromValue,
                            double toValue, int tmpX) {

        //TODO: Remove "count" protection, investigate loop excess
        int count=0;

        do {
            int tmpY = calcYPosValue(fromValue);
            pointer.prepareCanvas(chart.getGraphics3D(), pointer.getColor());
            pointer.draw(tmpX, tmpY, pointer.getColor());
            fromValue += getBoxSize();
            count++;
        } while ((fromValue <= toValue) && (count<1000));
    }

    protected void addSampleValues(int numValues) {
      super.addSampleValues(numValues);
    }

    private int calcMaxColumns(boolean shouldDraw) {
        if (getCount() > 0) {
            double tmpDistance = getReversalAmount() * getBoxSize();

            double tmpLow = getLowValues().value[0];
            double tmpHigh = getHighValues().value[0];

            int tmpCol = 0;
            int tmpX = 0;

            if (shouldDraw) {
                tmpX = calcXPosValue(tmpCol);
                drawColumn(down, tmpLow, tmpHigh, tmpX);
            }

            boolean tmpIsDown = true;

            for (int t = 1; t < getCount(); t++) {

                double tmp = 0;

                if (tmpIsDown) {
                    tmp = getLowValues().value[t];

                    if (tmp <= (tmpLow - getBoxSize())) {

                        if (shouldDraw) {
                            drawColumn(down, tmp, tmpLow - getBoxSize(), tmpX);
                        }
                        tmpLow = tmp;
                    } else {
                        tmp = getHighValues().value[t];
                        if (tmp >= (tmpLow + tmpDistance)) {
                            tmpCol++;
                            tmpHigh = tmp;

                            if (shouldDraw) {
                                tmpX = calcXPosValue(tmpCol);
                                drawColumn(up, tmpLow + getBoxSize(), tmpHigh, tmpX);
                            }
                            tmpIsDown = false;
                        }
                    }
                } else {
                    tmp = getHighValues().value[t];
                    if (tmp >= (tmpHigh + getBoxSize())) {

                        if (shouldDraw) {
                            drawColumn(up, tmpHigh + getBoxSize(), tmp, tmpX);
                        }
                        tmpHigh = tmp;
                    } else {
                        tmp = getLowValues().value[t];
                        if (tmp <= (tmpHigh - tmpDistance)) {
                            tmpCol++;
                            tmpLow = tmp;

                            if (shouldDraw) {
                                tmpX = calcXPosValue(tmpCol);
                                drawColumn(down, tmpLow, tmpHigh - getBoxSize(),
                                           tmpX);
                            }
                            tmpIsDown = true;
                        }
                    }
                }
            }

            return tmpCol + 1;
        } else {
            return 0;
        }
    }

    protected void draw() {
        calcMaxColumns(true);
    }

    public void prepareForGallery(boolean isEnabled) {
        super.prepareForGallery(isEnabled);
        if (!isEnabled) {
            getDownSymbol().setColor(Color.SILVER);
            getDownSymbol().getPen().setColor( Color.GRAY);
            getUpSymbol().setColor(Color.SILVER);
            getUpSymbol().getPen().setColor( Color.GRAY);
        }
    }

    public void setChart(IBaseChart value) {
        super.setChart(value);
        if (up != null) {
            up.setChart(chart);
        }
        if (down != null) {
            down.setChart(chart);
        }
    }

    public int getCountLegendItems() {
        return 2;
    }

    public Color legendItemColor(int index) {
        return (index == 0) ? up.getBrush().getColor() : down.getBrush().getColor();
    }

    public String getLegendString(int legendIndex,
                               LegendTextStyle legendTextStyle) {
        return (legendIndex == 0) ? Language.getString("Up") : Language.getString("Down");
    }

    public double getMaxXValue() {
        return calcMaxColumns(false) - 1;
    }

    public double getMinXValue() {
        return 0;
    }

    public SeriesPointer getDownSymbol() {
        return down;
    }

    public SeriesPointer getUpSymbol() {
        return up;
    }

    /**
     * Gets descriptive text.
     *
     * @return String
     */
    public String getDescription() {
        return Language.getString("GalleryPointFigure");
    }
}

