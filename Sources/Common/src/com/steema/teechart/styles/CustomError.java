package com.steema.teechart.styles;


import com.steema.teechart.languages.Language;
import com.steema.teechart.drawing.Color;
import com.steema.teechart.Rectangle;
import com.steema.teechart.drawing.IGraphics3D;
import com.steema.teechart.drawing.ChartPen;
import com.steema.teechart.IBaseChart;
import com.steema.teechart.misc.Utils;

/**
 *
 * <p>Title: CustomError class</p>
 *
 * <p>Description: CustomErrorSeries Series.</p>
 *
 * <p>Copyright (c) 2005-2019 by Steema Software SL. All Rights Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 *
 */
public class CustomError extends Bar {

    /**
	 * 
	 */
    private static final long serialVersionUID = 1L;
    protected ErrorStyle iErrorStyle = ErrorStyle.TOPBOTTOM;
    protected boolean bDrawBar = false;

    private ChartPen errorPen;
    private ValueList errorValues;
    private int errorWidth = 100;
    private ErrorWidthUnit errorWidthUnits = ErrorWidthUnit.PERCENT;

    transient private Drawer d = new Drawer();

    public CustomError() {
        this(null);
    }

    public CustomError(IBaseChart c) {
        super(c);

        errorValues = new ValueList(this, Language.getString("ValuesStdError")); // <-- Std Error storage
        getMarks().setDefaultVisible(false);
    }
    
    public void assign(Series source) {
        if (source instanceof CustomError) {
            CustomError tmp = (CustomError)source;
            this.getErrorPen().assign(tmp.getErrorPen());
            this.setErrorWidth(tmp.getErrorWidth());
            this.setErrorWidthUnits(tmp.getErrorWidthUnits());
            this.setErrorStyle(tmp.getErrorStyle());
        }
        super.assign(source);
    }

    protected Object readResolve() {
       d = new Drawer();
       return super.readResolve();
    }

    /**
     * Gets and sets the corresponding Error value for each Bar.<br>
     * The Error value will be displayed as a "T" on top of each Bar.
     *
     * @return ValueList
     */
    public ValueList getErrorValues() {
        return errorValues;
    }

    /**
     * Defines Pen to draw Error "T" on top of Error Bars.<br>
     *
     * @return ChartPen
     */
    public ChartPen getErrorPen() {
        if (errorPen == null) {
            errorPen = new ChartPen(chart, Color.BLACK, true);
        }
        return errorPen;
    }

    /**
     * Defines the Error Series Style according to EErrorSeriesStyle.<br>
     * Default value: ErrorStyle.TopBottom
     *
     *
     * @return ErrorStyle
     */
    public ErrorStyle getErrorStyle() {
        return iErrorStyle;
    }

    /**
     * Defines the Error Series Style according to EErrorSeriesStyle.<br>
     * Default value: ErrorStyle.TopBottom
     *
     *
     * @param value ErrorStyle
     */
    public void setErrorStyle(ErrorStyle value) {
        if (iErrorStyle != value) {
            iErrorStyle = value;
            invalidate();
        }
    }

    /**
     * The ErrorWidth in pixels or percentage of Bar width.<br>
     * By default, the Error "T" width is 100% of Bar width. <br>
     * Default value: ErrorWidthUnit.Percent
     *
     *
     * @return ErrorWidthUnit
     */
    public ErrorWidthUnit getErrorWidthUnits() {
        return errorWidthUnits;
    }

    /**
     * Sets ErrorWidth in pixels or percentage of Bar width.<br>
     * Default value: ErrorWidthUnit.Percent
     *
     *
     * @param value ErrorWidthUnit
     */
    public void setErrorWidthUnits(ErrorWidthUnit value) {
        if (errorWidthUnits != value) {
            errorWidthUnits = value;
            invalidate();
        }
    }

    /**
     * Determines the horizontal size of the Error "T".<br>
     * Size is expressed either in pixels or in percent of Bar width depending
     * on ErrorWidthUnit property. <br<
     * By default, Error's "T" width is 100% of Bar width.<br>
     * Default value: 100
     *
     *
     * @return int
     */
    public int getErrorWidth() {
        return errorWidth;
    }

    /**
     * Determines the horizontal size of the Error "T".<br>
     * Default value: 100
     *
     * @param value int
     */
    public void setErrorWidth(int value) {
        errorWidth = setIntegerProperty(errorWidth, value);
    }

    protected void addSampleValues(int numValues) {
        SeriesRandom r = randomBounds(numValues);
        for (int t = 1; t <= numValues; t++) {
            add(r.tmpX, Utils.round(r.DifY * r.Random()),
                r.DifY / (20 + r.Random()));
            r.tmpX += r.StepX;
        }
    }

    private void prepareErrorPen(IGraphics3D g, int valueIndex) {
        Color pen = Color.EMPTY;
        g.setPen(getErrorPen());

        if ((valueIndex != -1) && (!bDrawBar)) {
            g.getPen().setColor(getValueColor(valueIndex));
        }

        // BackMode:=cbmTransparent;
    }

    public void calcHorizMargins(Margins margins) {
        super.calcHorizMargins(margins);

        int tmp = Utils.round(getErrorPen().getWidth());
        if ((iErrorStyle == ErrorStyle.LEFT) ||
            (iErrorStyle == ErrorStyle.LEFTRIGHT)) {
            margins.min = Math.max(margins.min, tmp);
        }
        if ((iErrorStyle == ErrorStyle.RIGHT) ||
            (iErrorStyle == ErrorStyle.LEFTRIGHT)) {
            margins.max = Math.max(margins.max, tmp);
        }
    }

    public void calcVerticalMargins(Margins margins) {
        super.calcVerticalMargins(margins);

        int tmp = Utils.round(getErrorPen().getWidth());
        if ((iErrorStyle == ErrorStyle.TOP) ||
            (iErrorStyle == ErrorStyle.TOPBOTTOM)) {
            margins.min = Math.max(margins.min, tmp);
        }
        if ((iErrorStyle == ErrorStyle.BOTTOM) ||
            (iErrorStyle == ErrorStyle.TOPBOTTOM)) {
            margins.max = Math.max(margins.max, tmp);
        }
    }

    final private class Drawer {
        private int x, y, middleZ, aWidth;
        private boolean draw3D;
        private IGraphics3D g;

        private void drawHoriz(int xPos) {
            if (draw3D) {
                g.horizontalLine(x, xPos, y, middleZ);
                g.verticalLine(xPos, (y - aWidth / 2), y + (aWidth / 2),
                               middleZ); // 5.01
            } else {
                g.horizontalLine(x, xPos, y);
                g.verticalLine(xPos, (y - aWidth / 2), y + (aWidth / 2)); // 5.01
            }
        }

        private void drawVert(int yPos) {
            if (draw3D) {
                g.verticalLine(x, y, yPos, middleZ);
                g.horizontalLine(x - (aWidth / 2), x + (aWidth / 2), yPos,
                                 middleZ);
            } else {
                g.verticalLine(x, y, yPos);
                g.horizontalLine(x - (aWidth / 2), x + (aWidth / 2), yPos);
            }
        }
    }


    private void drawError(IGraphics3D g, int x, int y, int aWidth, int aHeight,
                           boolean draw3D) {

        d.g = g;
        d.x = x;
        d.y = y;
        d.middleZ = getMiddleZ();
        d.aWidth = aWidth;
        d.draw3D = draw3D;

       // g.getPen().setColor(Color.BLACK);

        if (iErrorStyle == ErrorStyle.LEFT) {
            d.drawHoriz(x - aHeight);
        } else
        if (iErrorStyle == ErrorStyle.RIGHT) {
            d.drawHoriz(x + aHeight);
        } else
        if (iErrorStyle == ErrorStyle.LEFTRIGHT) {
            d.drawHoriz(x - aHeight);
            d.drawHoriz(x + aHeight);
        } else
        if (iErrorStyle == ErrorStyle.TOP) {
            d.drawVert(y - aHeight);
        } else
        if (iErrorStyle == ErrorStyle.BOTTOM) {
            d.drawVert(y + aHeight);
        } else
        if (iErrorStyle == ErrorStyle.TOPBOTTOM) {
            d.drawVert(y - aHeight);
            d.drawVert(y + aHeight);
        }
    }

    protected void drawLegendShape(IGraphics3D g, int valueIndex, Rectangle r) {
        prepareErrorPen(g, valueIndex);
        drawError(g, (r.x + r.getRight()) / 2, (r.y + r.getBottom()) / 2, r.width,
                  (r.height / 2) - 1, false);
    }

    public void prepareForGallery(boolean isEnabled) {
        super.prepareForGallery(isEnabled);

        getErrorPen().setColor(isEnabled ? Color.RED : Color.WHITE);
        setColor(isEnabled ? Color.BLUE : Color.SILVER);
    }

    public void setColor(Color c) {
        super.setColor(c);
        //if (!bDrawBar) { //CDI why set this to empty just to set it back to a color again?
           // getErrorPen().setColor(c);
        //}
    }

    /**
     * Adds a new Error Bar point in selected color and with label.
     *
     * @param x double
     * @param y double
     * @param errorValue double
     * @param text String
     * @param color Color
     * @return int
     */
    public int add(double x, double y, double errorValue, String text,
                   Color color) {
        errorValues.tempValue = errorValue;
        return add(x, y, text, color);
    }

    /**
     * Adds a new Error Bar point to the Series with label.
     *
     * @param x double
     * @param y double
     * @param errorValue double
     * @param text String
     * @return int
     */
    public int add(double x, double y, double errorValue, String text) {
        return add(x, y, errorValue, text, Color.EMPTY);
    }

    /**
     * Adds a new Error Bar point to the Series.
     *
     * @param x double
     * @param y double
     * @param errorValue double
     * @return int
     */
    public int add(double x, double y, double errorValue) {
        return add(x, y, errorValue, "", Color.EMPTY);
    }

    /**
     * Adds a new Error Bar point to the Series in selected color.
     *
     * @param x double
     * @param y double
     * @param errorValue double
     * @param color Color
     * @return int
     */
    public int add(double x, double y, double errorValue, Color color) {
        return add(x, y, errorValue, "", color);
    }

    // Problem: Conflict with Add(x,y). Solution: Renamed to AddValue.
    /**
     * Adds a new Error Bar point with y value and error Value only.
     * @param y double
     * @param errorValue double
     * @return int index of added point
     */
    public int addValue(double y, double errorValue) {
        return add(getCount(), y, errorValue, "", Color.EMPTY);
    }

    /**
     * Displays an ErrorBar point ( BarIndex point ) using the Start and
     * End pixel coordinates.<br>
     *
     * @param barIndex int
     * @param startPos int
     * @param endPos int
     */
    public void drawBar(int barIndex, int startPos, int endPos) {

        if (bDrawBar) {
            super.drawBar(barIndex, startPos, endPos);
        }

        if (getErrorPen().getVisible()) {

            double tmpError = Math.abs(errorValues.value[barIndex]);

            if (tmpError != 0) {

                int tmpBarWidth = getBarBounds().width;

                int tmpWidth;

                if (errorWidth == 0) {
                    tmpWidth = tmpBarWidth;
                } else
                if (errorWidthUnits == ErrorWidthUnit.PERCENT) {
                    tmpWidth = Utils.round(errorWidth * tmpBarWidth * 0.01);
                } else {
                    tmpWidth = errorWidth;
                }

                int tmp = calcYPosValue(vyValues.value[barIndex]);

                int tmpHeight;

                /* MS   simplified and allows vertical/horizontal style 5.01 */
                if ((iErrorStyle == ErrorStyle.LEFT) |
                    (iErrorStyle == ErrorStyle.RIGHT) |
                    (iErrorStyle == ErrorStyle.LEFTRIGHT)) {
                    tmpHeight = calcXSizeValue(tmpError);
                } else {
                    tmpHeight = calcYSizeValue(tmpError);
                }

                if (bDrawBar && (vyValues.value[barIndex] < getOrigin())) {
                    tmpHeight = ( -tmpHeight);
                }

                prepareErrorPen(chart.getGraphics3D(), barIndex);

                drawError(chart.getGraphics3D(),
                          (getBarBounds().getRight() + getBarBounds().getLeft()) / 2,
                          tmp, tmpWidth, tmpHeight,
                          chart.getAspect().getView3D());
            }
        }
    }

    /**
     * Returns the Maximum Value of the Series Y Values List.
     *
     * @return double
     */
    public double getMaxYValue() {
        double result = (bDrawBar) ? super.getMaxYValue() : 0;

        double tmp;

        for (int t = 0; t < getCount(); t++) {
            if (bDrawBar) {
                double tmpErr = errorValues.value[t];
                tmp = vyValues.value[t];
                if (tmp < 0) {
                    tmp -= tmpErr;
                } else {
                    tmp += tmpErr;
                }
                if (tmp > result) {
                    result = tmp;
                }
            } else {
                tmp = vyValues.value[t] + errorValues.value[t];
                result = (t == 0) ? tmp : Math.max(result, tmp);
            }
        }

        return result;
    }

    /**
     * Returns the Minimum Value of the Series Y Values List.
     *
     * @return double
     */
    public double getMinYValue() {
        double result = (bDrawBar) ? super.getMinYValue() : 0;

        double tmp;

        for (int t = 0; t < getCount(); t++) {
            if (bDrawBar) {
                double tmpErr = errorValues.value[t];
                tmp = vyValues.value[t];
                if (tmp < 0) {
                    tmp -= tmpErr;
                } else {
                    tmp += tmpErr;
                }
                if (tmp < result) {
                    result = tmp;
                }
            } else {
                tmp = vyValues.value[t] - errorValues.value[t];
                result = (t == 0) ? tmp : Math.min(result, tmp);
            }
        }

        return result;
    }
}

