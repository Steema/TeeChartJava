package com.steema.teechart.legend;

import com.steema.teechart.drawing.Color;
import com.steema.teechart.languages.Language;
import com.steema.teechart.drawing.Point;
import com.steema.teechart.drawing.StringAlignment;
import com.steema.teechart.drawing.ChartPen;
import com.steema.teechart.drawing.IGraphics3D;
import com.steema.teechart.styles.Series;
import com.steema.teechart.TextShapePosition;
import com.steema.teechart.IBaseChart;
import com.steema.teechart.Rectangle;

import com.steema.teechart.BevelStyle;
import com.steema.teechart.misc.Utils;

/**
 *
 * <p>Title: Legend class</p>
 *
 * <p>Description: Accesses all Chart Legend characteristics</p>
 *
 * <p>Copyright (c) 2005-2019 by Steema Software SL. All Rights
 * Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 *
 */
public class Legend extends TextShapePosition {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	static final int CHECKBOXSIZE = 11;
    static final int LEGENDOFF2 = 2;
    static final int LEGENDOFF4 = 4;
    static final int MAXLEGENDCOLUMNS = 2;
    private static final int ALLVALUES = -1;
    transient public LegendStyle iLegendStyle;
    protected boolean columnWidthAuto = true;
    protected int[] columnWidths = new int[10];
    transient protected int firstValue;
    transient protected int iLastValue;
    private boolean resizeChart = true;
    private boolean checkBoxes = false;
    private LegendTextStyle textStyle = LegendTextStyle.LEFTVALUE;
    transient private Series series;
    private LegendStyle legendStyle = LegendStyle.AUTO;
    private boolean currentPage = true;
    private int vertSpacing;
    private int horizMargin;
    private int vertMargin;
    private int topLeftPos = 10;
    private LegendAlignment alignment = LegendAlignment.RIGHT;
    private int numCols;
    private int numRows;
    private int maxNumRows = 10;
    private boolean fontSeriesColor;
    private boolean inverted;
    private ChartPen dividingLines;
    private LegendTitle title;
    private int textSymbolGap = 2;
    transient private int frameWidth;
    transient private int iColorWidth;
    transient private boolean incPos;
    transient private int iTotalItems, posXLegend, posYLegend, iSpaceWidth;
    transient private String[][] iItems;
    transient private Series tmpSeries;
    transient private int itemHeight;
    private LegendSymbol symbol;
    private LegendItems items;
    transient private int xLegendColor;
    transient private int xLegendText;
    transient private int xLegendBox;
    transient private int tmpMaxWidth;
    transient private int tmpTotalWidth;
    private int iManualWidth, iManualHeight;

    private ChartPen getFrame() {
        return getPen();
    }

    public Legend(IBaseChart c) {
        super(c);

        readResolve();

        getShadow().getBrush().setDefaultColor(Color.BLACK);
        shadow.setDefaultSize(3);
        shadow.setDefaultVisible(true);
    }

    protected Object readResolve() {
        iLegendStyle = LegendStyle.AUTO;
        iItems = new String[100][10];
        return this;
    }

    /**
     * Determines how Legend text iItems will be formatted.<br>
     * Plain shows the point Label only.<br>
     * LeftValue shows the point Value and the point Label. <br>
     * RightValue shows the point Label and the point Value. <br>
     * LeftPercent shows the percent the point represents and the point Label. <br>
     * RightPercent shows the point Label and the percent the points represent. <br>
     * XValue shows the point's X value. It applies only to Series with X
     * (horizontal) values. <br><br>
     * Values are pre-formatted using the Series ValueFormat property. <br>
     * Percents are pre-formatted using the Series PercentFormat property. <br><br>
     * Default value: LeftValue
     *
     *
     * @return LegendTextStyle
     */
    public LegendTextStyle getTextStyle() {
        return textStyle;
    }

    /**
     * Returns the index of the first displayed value at legend.
     * The index can be a series value index or a series index depending
     * on legend style.
     *
     * @return int
     */
    public int getLastValue() {
        return iLastValue;
    }

    /**
     * Specifies how Legend text iItems will be formatted.<br>
     * Default value: LeftValue
     *
     *
     * @param value LegendTextStyle
     */
    public void setTextStyle(LegendTextStyle value) {
        if (textStyle != value) {
            textStyle = value;
            invalidate();
        }
    }

    public int getTextSymbolGap() {
        return textSymbolGap;
    }

    public void setTextSymbolGap(int value) {
        textSymbolGap = setIntegerProperty(textSymbolGap,value);
    }

    /**
     * Determines which series is used as data for the Legend entries.<br>
     * By default, the Legend chooses the first Active Series with
     * ShowInLegend:=true. It only applies to Legend style "Values".
     *
     * @return Series
     */
    public Series getSeries() {
        return series;
    }

    /**
     * Determines which series is used as data for the Legend entries.<br>
     *
     * @param value Series
     */
    public void setSeries(Series value) {
        series = value;
        invalidate();
    }

    /**
     * Enables/Disables the display of Legend check boxes.<br>
     * Default value: false
     *
     * @return boolean
     */
    public boolean getCheckBoxes() {
        return checkBoxes;
    }

    public void setChart(IBaseChart c) {
        super.setChart(c);

        if (symbol != null) {
            symbol.legend = this;
        }
    }

    /**
     * Displays the Legend check boxes when true.<br>
     * Default value: false
     *
     * @param value boolean
     */
    public void setCheckBoxes(boolean value) {
        checkBoxes = setBooleanProperty(checkBoxes, value);
    }

    /**
     * Determines whether or not the Legend shows only the current page iItems
     * when the Chart is divided into pages.<br>
     * Default value: true
     *
     * @return boolean
     */
    public boolean getCurrentPage() {
        return currentPage;
    }

    /**
     * When true, the Legend shows only the current page iItems when the Chart
     * is divided into pages.<br>
     *
     * Default value: true
     *

     * @param value boolean
     */
    public void setCurrentPage(boolean value) {
        currentPage = setBooleanProperty(currentPage, value);
    }

    /**
     * The legend text font color to that of the Series color.<br>
     * Default value: false
     *
     * @return boolean
     */
    public boolean getFontSeriesColor() {
        return fontSeriesColor;
    }

    /**
     * Sets the legend text font color to that of the Series color.<br>
     * Default value: false
     *
     * @param value boolean
     */
    public void setFontSeriesColor(boolean value) {
        fontSeriesColor = setBooleanProperty(fontSeriesColor, value);
    }

    /**
     * Specifies the Pen attributes used to draw lines separating Legend iItems.
     * <br>
     * Lines are drawn horizontally for Left or Right aligned Legend
     * and vertically for Top or Bottom Legend alignments.
     *
     * @return ChartPen
     */
    public ChartPen getDividingLines() {
        if (dividingLines == null) {
            dividingLines = new ChartPen(chart, Color.BLACK, false);
        }
        return dividingLines;
    }

    /**
     * Internal use - serialization
     */
    public void setDividingLines(ChartPen value) {
        dividingLines = value;
        invalidate();
    }

    /**
     * Controls the width and position of the color rectangle associated with
     * each Legend's item.
     *
     * @return LegendSymbol
     */
    public LegendSymbol getSymbol() {
        if (symbol == null) {
            symbol = new LegendSymbol(this);
        }
        return symbol;
    }

    /*
     *Internal use - serialization
     */
    public void setSymbol(LegendSymbol value) {
        symbol = value;
    }

    /**
     * Draws the Legend iItems in opposite direction when true.<br>
     * Legend strings are displayed starting at top for Left and Right Aligment
     * and starting at left for Top and Bottom Legend orientations. You can use
     * Legend.FirstValue to determine the ValueIndex for the first Legend
     * text item. <br>
     * Default value: false
     *
     * @return boolean
     */
    public boolean getInverted() {
        return inverted;
    }

    /**
     * Draws the Legend iItems in opposite direction when true.<br>
     * Default value: false
     *

     * @param value boolean
     */
    public void setInverted(boolean value) {
        inverted = setBooleanProperty(inverted, value);
    }

    /**
     * Defines the Legend position.<br>
     * Legend can be currently placed at Top, Left, Right and Bottom side of
     * Chart. <br>
     * Left and Right Legend alignments define a vertical Legend with
     * currently one single column of iItems. <br>
     * Top and Bottom Legend alignments define an horizontal Legend with
     * currently one single row of iItems. <br>
     * The Legend itself automatically reduces the number of displayed
     * legend iItems based on the available charting space. <br>
     * The Legend.ResizeChart property controls if Legend dimensions should be
     * used to reduce Chart points space. <br>
     * The Legend.GetLegendRect event provides a mechanism to supply the
     * desired Rectangle Legend dimensions and placement. <br>
     * The Legend.GetLegendPos event can be used to specify fixed Legend iItems
     * X Y coordinates. <br>
     * The Legend.HorizMargin and VertMargin properties control distance
     * between Legend and Left and Right margins. <br>
     * The Legend.TopLeftPos property can be used in Left Legend alignments
     * to control vertical distance between Legend and Top Chart Margin.  <br><br>
     * These techniques allow almost complete Legend control. <br>
     * Default value: Right
     *
     *
     * @return LegendAlignment
     */
    public LegendAlignment getAlignment() {
        return alignment;
    }

    /**
     * Defines the Legend position.<br>
     * Default value: Right
     *
     * @param value LegendAlignment
     */
    public void setAlignment(LegendAlignment value) {
        if (alignment != value) {
            alignment = value;
            invalidate();
        }
    }

    public int getColumnWidth(int column) {
        if (column < MAXLEGENDCOLUMNS) {
            return columnWidths[column];
        } else {
            return -1;
        }
    }

    public void setColumnWidth(int column, int value) {
        if (column < MAXLEGENDCOLUMNS) {
            columnWidths[column] = value;
        }
        ;
        invalidate();
    }

    /**
     * Automatically calculates best fit of legend columns.<br>
     * When set to true, columnWidths control the legend width<br>
     * Default value: true
     *
     * @return boolean
     */
    public boolean getColumnWidthAuto() {
        return columnWidthAuto;
    }

    /**
     * Automatically calculates best fit of legend columns.<br>
     * When set to true, columnWidths control the legend width<br>
     * Default value: true
     *
     * @param value boolean
     */
    public void setColumnWidthAuto(boolean value) {
        columnWidthAuto = setBooleanProperty(columnWidthAuto, value);
    }

    /**
     * Defines which is the first Legend item displayed.<br>
     * Legend can display all active Series names or all points of a single
     * Series. FirstValue should be set accordingly, taking care not to
     * overflow the number of active Series or the number of Series points. You
     * can use FirstValue to show a specific subset of Series or points in
     * Legend. It should be greater or equal to zero, and lower than the number
     * of active Series or Series points. See Legend.LegendStyle for a
     * description of the different Legend styles. <br>
     * Default value: 0
     *
     * @return int
     */
    public int getFirstValue() {
        return firstValue;
    }

    /**
     * Determines which is the first Legend item displayed.<br>
     * Default value: 0
     *
     * @see Legend#getFirstValue
     * @param value int
     */
    public void setFirstValue(int value) {
        firstValue = setIntegerProperty(firstValue, value);
    }

    /**
     * Specifies the Legend's top position in percent of total chart height.<br>
     * It's used when Legend.Alignment is Left or Right only. For Top or
     * Bottom Legend alignments, you can use the Chart's MarginTop and
     * MarginBottom. <br>
     * Default value: 10
     *
     * @return int
     */
    public int getTopLeftPos() {
        return topLeftPos;
    }

    /**
     * Specifies the Legend's top position in percent of total chart height.<br>
     * Default value: 10
     *
     * @param value int
     */
    public void setTopLeftPos(int value) {
        topLeftPos = setIntegerProperty(topLeftPos, value);
    }

    /**
     * The Maximum number of Legend Rows displayed for a horizontal Legend
     * (Chart Top or Bottom).<br>
     * Default value: 10
     *
     * @return int
     */
    public int getMaxNumRows() {
        return maxNumRows;
    }

    /**
     * Sets the Maximum number of Legend Rows displayed for a horizontal Legend
     * (Chart Top or Bottom).<br>
     * Default value: 10
     *
     * @param value int
     */
    public void setMaxNumRows(int value) {
        maxNumRows = setIntegerProperty(maxNumRows, value);
    }

    //[Browsable(false),EditorBrowsable(EditorBrowsableState.Advanced)]
    public String[] getLines() {
        return super.getLines();
    }

    public void setLines(String[] value) {
        super.setLines(value);
    }

    /**
     * Adds text to the Legend.
     *
     * @return String
     */
    public String getText() {
        return super.getText();
    }

    /**
     * Adds text to the Legend.
     *
     * @param value String
     */
    public void setText(String value) {
        setTextVariable(value);
    }

    private void setTextVariable(String value) {
        if (!getText().equals(value)) {
            super.setText(value);
            this.items.setCustom(true);
        }
    }

    /**
     * The vertical spacing between Legend iItems (pixels).<br>
     * Default value: 0
     *
     * @return int
     */
    public int getVertSpacing() {
        return vertSpacing;
    }

    /**
     * Determines the vertical spacing between Legend iItems (pixels).<br>
     * Default value: 0
     *
     * @param value int
     */
    public void setVertSpacing(int value) {
        vertSpacing = setIntegerProperty(vertSpacing, value);
    }

    /**
     * Speficies the number of screen pixels between Legend and Chart
     * rectangles.<br>
     * By default it is 0, meaning Legend will calculate a predefined margin
     * based on total Legend width. It is only used when Legend position is
     * Left or Right aligned otherwise use VertMargin. <br>
     * Default value: 0
     *
     * @return int
     */
    public int getHorizMargin() {
        return horizMargin;
    }

    /**
     * Speficies the number of screen pixels between Legend and Chart
     * rectangles.<br>
     * Default value: 0
     *
     * @param value int
     */
    public void setHorizMargin(int value) {
        horizMargin = setIntegerProperty(horizMargin, value);
    }

    /**
     * The vertical margin in pixels between Legend and Chart
     * rectangle.<br>
     * Legend.ResizeChart must be true and Legend.Alignment must be
     * Top or Bottom. When 0, the corresponding Chart margin method is used to
     * determine the amount of pixels for margins (Chart.MarginTop for Top
     * Legend.alignment and Chart.MarginBottom for Bottom Legend.Alignment).<br>
     * Default value: 0
     *
     * @return int
     */
    public int getVertMargin() {
        return vertMargin;
    }

    /**
     * Determines the vertical margin in pixels between Legend and Chart
     * rectangle.<br>
     * Default value: 0
     *
     * @param value int
     */
    public void setVertMargin(int value) {
        vertMargin = setIntegerProperty(vertMargin, value);
    }

    /**
     * Automatically resizes Chart rectangle to prevent overlap with Legend.<br>
     * When set to true, Legend.HorizMargin and Legend.VertMargin control the
     * amount of pixels by which the Chart rectangle will be reduced. <br>
     * Default value: true
     *
     * @return boolean
     */
    public boolean getResizeChart() {
        return resizeChart;
    }

    /**
     * Automatically resizes Chart rectangle to prevent overlap with Legend.<br>
     * When set to true, Legend.HorizMargin and Legend.VertMargin control the
     * amount of pixels by which the Chart rectangle will be reduced. <br>
     * Default value: true
     *
     * @param value boolean
     */
    public void setResizeChart(boolean value) {
        resizeChart = setBooleanProperty(resizeChart, value);
    }

    private Series getLegendSeries() {
        Series tmpResult = series;
        if (tmpResult == null) {
            return chart.getFirstActiveSeries();
        } else {
            return tmpResult;
        }
    }

    /**
     * Defines which iItems will be displayed in the Chart Legend. <br>
     * Series style shows the Series.Title of all active Series in a Chart.
     * Whenever a Series Title is empty, Series Name is used. <br>
     * Values style shows a text representation of all points of the first
     * active Series in a Chart. <br>
     * LastValues style shows the last point value and the Series.Title of all
     * active Series in a Chart.  It is useful for real-time charting, where
     * new points are being added at the end of each Series. <br>
     * Auto style (the default) means LegendStyle will be Series when there's
     * more than one Active Series, and Values when there's only one Series
     * in a Chart. <br>
     * Legend.TextStyle determines how the Series point values are formatted.<br>
     * Default value: auto
     *
     *
     * @return LegendStyle
     */
    public LegendStyle getLegendStyle() {
        return legendStyle;
    }

    /**
     * Defines which iItems will be displayed in the Chart Legend. <br>
     * Default value: Auto
     *
     *
     * @param value LegendStyles
     * @see Legend#getLegendStyle
     */
    public void setLegendStyle(LegendStyle value) {
        if (legendStyle != value) {
            legendStyle = value;
            calcLegendStyle();
            invalidate();
        }
    }

    /**
     * Sets the Title text and its characteristics at the top of the legend
     */
    public LegendTitle getTitle() {
        if (title == null) {
            title = new LegendTitle(chart);
        }
        return title;
    }

    public boolean doMouseDown(Point p) {

        boolean result = false;

        if (hasCheckBoxes()) {
            int tmp = clicked(p);
            if (tmp != -1) {
                Series s = chart.seriesLegend(tmp, false);
                s.setActive(!s.getActive());
                // implement "radio" style here...
                result = true;
            }
        }
        return result;
    }

    private int clickedRow(int tmpH, int y) {
        int result = -1;
        for (int t = 0; t < numRows; t++) {
            int tmp = getFirstItemTop() + 1 + t * tmpH;
// antes            int tmp = getShapeBounds().getTop() + 1 + t * tmpH;
            if ((y >= tmp) && (y <= (tmp + tmpH))) {
                result = t;
                if (getInverted()) {
                    result = numRows - t - 1;
                }
                break;
            }
        }
        return result;
    }

    /**
     * Returns the index of the clicked Legend Point.
     *
     * @param p Point
     * @return int
     */
    public int clicked(Point p) {
        return clicked(p.x, p.y);
    }

    /**
     * Returns the index of the clicked Legend Point.
     *
     * @param x int
     * @param y int
     * @return int
     */
    public int clicked(int x, int y) {

        int result = -1;

        if (getShapeBounds().contains(x, y)) { // inside legend rectangle

            chart.getGraphics3D().setFont(getFont());
            int tmpH = calcItemHeight();

            if (getVertical()) {
                if (numRows > 0) {
                    //int tmpH=(ShapeBounds.Bottom-ShapeBounds.Top) / NumRows;
                    result = clickedRow(tmpH, y) + firstValue;
                }
            } else {
                if (numCols > 0) {
                    int tmpW = (getShapeBounds().getRight()
                            - getShapeBounds().getLeft())
                            / numCols;

                    //chart.getGraphics3D().setFont(getFont());
                    //int tmpH=chart.getGraphics3D().getFontHeight();

                    for (int t = 0; t < numCols; t++) {
                        int tmp2 = getShapeBounds().getLeft() + 1 + t * tmpW;
                        if ((x >= tmp2) && (x <= (tmp2 + tmpW))) {
                            result = clickedRow(tmpH, y);
                            if (result != -1) {
                                result *= numCols;
                                if (getInverted()) {
                                    result += numCols - t - 1; // 5.02

                                    int tmpMod = ((iLastValue - firstValue) + 1) % numCols;

                                    if (tmpMod > 0) {
                                        result -= numCols - tmpMod;
                                    }
                                } else {
                                    result += t;
                                }

                                if (result > iTotalItems - 1) {
                                    result = -1;
                                } else {
                                    result += firstValue;
                                }

                            }
                        }
                    }
                }
            }
        }
        return result;
    }

    protected int calcItemHeight() {
        int result = chart.getGraphics3D().getFontHeight();
        if (hasCheckBoxes()) {
            result = Math.max(6 + CHECKBOXSIZE, result);
        }
        result += vertSpacing;

        if (getVertical() && (dividingLines != null)
                && dividingLines.getVisible()) /* 5.02 */ {
            result += Utils.round(dividingLines.getWidth());
        }

        return result;
    }

    protected boolean drawTitle() {
        return getTitle().getVisible() && getTitle().getText() != "";
    }

    private void calcLegendStyle() {
        if (legendStyle == LegendStyle.AUTO) {
            if ((checkBoxes) || (chart.countActiveSeries() > 1)) {
                iLegendStyle = LegendStyle.SERIES;
            } else {
                if (this.getLegendSeries() instanceof com.steema.teechart.styles.Custom3DPalette) {
                    maxNumRows = 8;
                    iLegendStyle = LegendStyle.PALETTE;
                } else {
                    maxNumRows = 10;
                    iLegendStyle = LegendStyle.VALUES;
                }
            }
        } else {
            iLegendStyle = legendStyle;
        }
    }

    private int calcTotalItems() {
        int result = 0;

        if ((iLegendStyle == LegendStyle.SERIES)
                || (iLegendStyle == LegendStyle.LASTVALUES)) {
            for (int t = 0; t < chart.getSeriesCount(); t++) {
                Series s = chart.getSeries(t);
                if ((checkBoxes || s.getActive()) && s.getShowInLegend()) {
                    result++;
                }
            }
            result -= firstValue;
        } else {
            Series aSeries = getLegendSeries();
            if ((aSeries != null) && aSeries.getShowInLegend()) {
                result = aSeries.getCountLegendItems() - firstValue;
            }
        }
        return Math.max(0, result);
    }

    /**
     * Returns true when the legend displays checkboxes and it is showing
     * series names.
     *
     * @return boolean
     */
    public boolean hasCheckBoxes() {
        return (checkBoxes && (iLegendStyle != LegendStyle.VALUES));
    }

    /**
     * Is read only and returns true only if the legend is left or right
     * aligned.
     *
     * @return boolean
     */
    public boolean getVertical() {
        return (alignment == LegendAlignment.LEFT)
                || (alignment == LegendAlignment.RIGHT);
    }

    private void calcSymbolTextPos(int leftPos) {
        int tmp = leftPos + LEGENDOFF2 + LEGENDOFF4;
        if (getSymbol().position == LegendSymbolPosition.LEFT) {
            xLegendColor = tmp;
            xLegendText = xLegendColor + iColorWidth + LEGENDOFF4 + textSymbolGap;
        } else {
            xLegendText = tmp;
            xLegendColor = xLegendText + tmpMaxWidth + textSymbolGap;
        }
    }

    private void calcHorizontalPositions() {
        int halfMaxWidth;
        int tmpW;

        if (getAutoSize()) {
            halfMaxWidth = 3 * LEGENDOFF2 + LEGENDOFF4 + ((tmpMaxWidth + textSymbolGap + iColorWidth) * numCols)
                    + ((LEGENDOFF2 + LEGENDOFF4) * (numCols - 1));
        } else {
            halfMaxWidth = iManualWidth;
        }

        if (hasCheckBoxes()) {
            halfMaxWidth += LEGENDOFF4 + (CHECKBOXSIZE + LEGENDOFF2) * numCols;
            halfMaxWidth += LEGENDOFF4 * 2; //CDI TF02010041
        }
        if (drawTitle()) {
            halfMaxWidth = Math.max(halfMaxWidth, getTitle().getTotalWidth()); //CDI TF02014531
        }

        halfMaxWidth = Math.min(chart.getWidth(), halfMaxWidth);
        halfMaxWidth /= 2;

        if (!getCustomPosition()) {
            if (getAutoSize()) {
                tmpW = Utils.round(1.0 * topLeftPos
                        * (chart.getRight() - chart.getLeft() - 2 * halfMaxWidth) * 0.01); // 5.02
                shapeBounds.setLeft(chart.getGraphics3D().getXCenter() - halfMaxWidth + tmpW);
            } else {
                shapeBounds.setLeft(chart.getGraphics3D().getXCenter() - halfMaxWidth);
            }
        }

        setRight(shapeBounds.x + (2 * halfMaxWidth));

        tmpW = shapeBounds.x;

        if (hasCheckBoxes()) {
            xLegendBox = shapeBounds.x + LEGENDOFF4;
            tmpW += CHECKBOXSIZE + LEGENDOFF4;
        }
        calcSymbolTextPos(tmpW);
        setShapeWidthHeight();
    }

    private int calcColumnsWidth(int numLegendValues) {
        if (columnWidthAuto) {
            IGraphics3D g = chart.getGraphics3D();

            for (int t = 0; t < MAXLEGENDCOLUMNS; t++) {
                columnWidths[t] = 0;
                for (int tt = firstValue; tt <= iLastValue; tt++) {
                    String s = iItems[tt - firstValue][t];
                    if (s != null) {
                        columnWidths[t] = Math.max(columnWidths[t], textSymbolGap + Utils.round(g.textWidth(s)));
                    }
                }
            }
        }

        int result = iSpaceWidth * MAXLEGENDCOLUMNS - 1;
        for (int t = 0; t < MAXLEGENDCOLUMNS; t++) {
            result += columnWidths[t];
        }
        return result;
    }

    public int getColorWidth() {
        return getSymbol().getWidth();
    }

    public void setColorWidth(int value) {
        getSymbol().setWidth(value);
    }

    private void calcWidths() {
        tmpMaxWidth = calcColumnsWidth(numRows);
        tmpTotalWidth = 2 * LEGENDOFF4 + tmpMaxWidth + LEGENDOFF2;
        iColorWidth = getSymbol().calcWidth(tmpMaxWidth);
        if (getAutoSize()) {
            tmpTotalWidth += textSymbolGap + iColorWidth + 5;  //5 = margin
            if (drawTitle()) {
                tmpTotalWidth = Math.max(tmpTotalWidth, getTitle().getTotalWidth());
            }
        } else {
            tmpTotalWidth = iManualWidth;
        }

    }

    private void calcVerticalPositions() {

        int tmpW;
        if (getCustomPosition() || (alignment == LegendAlignment.LEFT)) {
            if (!getCustomPosition()) {
                //left=rect.X;
                if (getFrame().getVisible()) {
                    shapeBounds.setLeft(shapeBounds.getLeft() + frameWidth + 1);
                }
            }
            calcWidths();
        } else {
            //right=rect.right;
            if (getShadow().getVisible()) {
                shapeBounds.setRight(shapeBounds.getRight() - Math.max(0, getShadow().getWidth()));
            }
            if (getFrame().getVisible()) {
                shapeBounds.setRight(shapeBounds.getRight() - frameWidth + 1);
            }
            calcWidths();
            shapeBounds.setLeft(shapeBounds.getRight() - tmpTotalWidth);
            if (hasCheckBoxes()) {
                shapeBounds.setLeft(shapeBounds.getLeft() - CHECKBOXSIZE + LEGENDOFF4);
            }
        }

        if (hasCheckBoxes()) {
            xLegendBox = shapeBounds.getLeft() + LEGENDOFF4;
            tmpW = xLegendBox + CHECKBOXSIZE;
        } else {
            tmpW = shapeBounds.getLeft();
        }

        shapeBounds.setRight(tmpW + tmpTotalWidth);

        calcSymbolTextPos(tmpW);
        setShapeWidthHeight();
    }

    private void setRightAlign(int column, boolean isRight) {
        if (isRight) {
            chart.getGraphics3D().setTextAlign(StringAlignment.FAR);
            posXLegend += columnWidths[column];
            incPos = false;
        } else {
            chart.getGraphics3D().setTextAlign(StringAlignment.NEAR);
        }
    }

    private void drawSymbol(Series series, int index, final Rectangle r) {
        if (iColorWidth > 0) {
            if (series != null) {
                series.drawLegend(index, r);
            } else {
            	
    			if (iColorWidth > 0)
    			{
    				if (series != null)
    				{
    					series.drawLegend(index, r);
    				}
    				else
    				{
    					symbol.draw(r);
    				}
    			}
            }
        }
    }

    private void drawLegendItem(int itemIndex, int itemOrder) {
        Rectangle r = new Rectangle();

        int tmpTop = 0;

        if (itemOrder >= iTotalItems) {
            return;
        }

        IGraphics3D g = chart.getGraphics3D();
        g.getBrush().setForegroundColor(getColor());
        g.getBrush().setVisible(false);

        prepareSymbolPen();

        int tmp = xLegendText;
        int tmpX = 0;
        posYLegend = getFirstItemTop() + 1;
// before        posYLegend = getShapeBounds().getTop() + 1;
        int posXColor = xLegendColor;

        int tmpOrder = itemOrder;

        if (!getVertical()) {
            tmpOrder = itemOrder / numCols;
            tmpX = (tmpMaxWidth + iColorWidth + textSymbolGap + LEGENDOFF4 + LEGENDOFF2);

            if (hasCheckBoxes()) {
                tmpX += CHECKBOXSIZE + 2 * LEGENDOFF2;
            }

            tmpX *= (itemOrder % numCols);
            tmp += tmpX;
            posXColor += tmpX;
        }

        posYLegend += tmpOrder * itemHeight + (vertSpacing / 2);

        if (chart.getParent() != null) {
            LegendItemCoordinates res = new LegendItemCoordinates(itemIndex,
                    tmp, posYLegend, posXColor);
            res = chart.getParent().getLegendResolver().getItemCoordinates(this, res);
            tmp = res.getX();
            posYLegend = res.getY();
            posXColor = res.getXColor();
        }

        posXLegend = tmp;

        if (fontSeriesColor) {
            if ((iLegendStyle == LegendStyle.SERIES)
                    || (iLegendStyle == LegendStyle.LASTVALUES)) {
                g.getFont().setColor(chart.seriesLegend(itemIndex, !checkBoxes).
                        getColor());
            } else {
                g.getFont().setColor(tmpSeries.legendItemColor(itemIndex));
            }
        }

        for (int t = 0; t < MAXLEGENDCOLUMNS; t++) {
            String tmpSt = iItems[itemOrder][t];
            incPos = true;

            if (tmpSt != null) {
                if (iLegendStyle == LegendStyle.SERIES) {
                    g.setTextAlign(StringAlignment.NEAR);
                } else if (t == 0) {
                    if ((textStyle == LegendTextStyle.XVALUE)
                            || (textStyle == LegendTextStyle.VALUE)
                            || (textStyle == LegendTextStyle.PERCENT)
                            || (textStyle == LegendTextStyle.XANDVALUE)
                            || (textStyle == LegendTextStyle.XANDPERCENT)
                            || (textStyle == LegendTextStyle.LEFTPERCENT)
                            || (textStyle == LegendTextStyle.LEFTVALUE)) {
                        setRightAlign(t, true);
                    } else {
                        setRightAlign(t, false);
                    }
                } else if (t == 1) {
                    if ((textStyle == LegendTextStyle.RIGHTVALUE)
                            || (textStyle == LegendTextStyle.XANDVALUE)
                            || (textStyle == LegendTextStyle.XANDPERCENT)
                            || (textStyle == LegendTextStyle.RIGHTPERCENT)) {
                        setRightAlign(t, true);
                    } else {
                        setRightAlign(t, false);
                    }
                }

                if (tmpSt.length() != 0) {
                    int tmpBox = hasCheckBoxes() ? posYLegend + 1 : posYLegend;

                    LegendItem tmpLI;

                    if (items.getCustom() && items.size() > itemIndex) {

                        tmpLI = items.getLegendItem(itemIndex);
                        if (t == 0) {
                            posXLegend = tmpLI.getLeft();
                            tmpTop = tmpLI.getTop();
                            tmpSt = tmpLI.getFText();
                            g.setTextAlign(tmpLI.getAlign());
                        } else {
                            tmpSt = tmpLI.getFText2();
                        }
                    } else {
                        tmpLI = items.getLegendItem(itemIndex);
                        if (t == 0) {
                            tmpLI.setLeft(posXLegend);
                            tmpLI.setTop(tmpTop);
                            tmpLI.setFText(tmpSt);
                            tmpLI.setFText2(getItemText(itemOrder, 1));
                            tmpLI.setAlign(g.getTextAlign());
                        }
                    }
                    g.textOut(posXLegend, tmpBox, tmpSt); // draw text
                }
            }

            if (incPos) {
                posXLegend += columnWidths[t];
            }
            posXLegend += iSpaceWidth;
        }

        if (items.getCustom()) {
            r = items.getLegendItem(itemIndex).getSymbolRect();
        } else {
            r.x = posXColor;
            r.width = iColorWidth;
            r.y = posYLegend;
            r.height = itemHeight + 1;

            if ((!getSymbol().continuous) || (itemOrder == 0)) {
                r.y += 2;
                r.height -= 2;
            }

            if ((!getSymbol().continuous) || (itemOrder == (iLastValue - firstValue))) {
                r.height -= 1 + 2 + vertSpacing;
            }
            items.getLegendItem(itemIndex).setSymbolRect(r);
        }

        if ((iLegendStyle == LegendStyle.SERIES)
                || (iLegendStyle == LegendStyle.LASTVALUES)) {
            if (checkBoxes) {
                int tmpBox = xLegendBox;
                tmpSeries = chart.seriesLegend(itemIndex, false);

                if (!getVertical()) {
                    tmpBox += tmpX;
                }

                boolean checkStyle = true;  // TODO RadioStyle

                if (g instanceof com.steema.teechart.drawing.Graphics3D) {
                    com.steema.teechart.misc.Utils.drawCheckBox(tmpBox,
                            posYLegend
                            + ((itemHeight - vertSpacing
                            - CHECKBOXSIZE)
                            / 2) - 1,
                            g, tmpSeries.getActive(), checkStyle, getColor());
                }

                drawSymbol(tmpSeries, -1, r);
            } else {
                drawSymbol(chart.activeSeriesLegend(itemIndex), -1, r);
            }
        } else {
            if (tmpSeries != null) {
                drawSymbol(tmpSeries, tmpSeries.legendToValueIndex(itemIndex), r);
            } else {
                drawSymbol(null, -1, r);
            }
        }

        if ((itemOrder > 0) && (dividingLines != null)
                && dividingLines.getVisible()) {
            g.setPen(dividingLines);

            if (getVertical()) {
                g.horizontalLine(getShapeBounds().getLeft(),
                        getShapeBounds().getRight(),
                        posYLegend - (vertSpacing / 2));
            } else {
                g.verticalLine(getShapeBounds().getLeft() + tmpX + LEGENDOFF2,
                        getShapeBounds().getTop(),
                        getShapeBounds().getBottom());
            }
        }
    }

    private void drawItems() {
        tmpSeries = getLegendSeries();

        if (!getLegendItems().getCustom()) {
            items.clear();
        }

        if (inverted) {
            for (int t = iLastValue; t >= firstValue; t--) {
                drawLegendItem(t, (iLastValue - t));
            }
        } else {
            for (int t = firstValue; t <= iLastValue; t++) {
                drawLegendItem(t, (t - firstValue));
            }
        }
    }

    private void setItem(int index, int pos) {

        String tmpSt = chart.formattedLegend(pos); /* 5.01 */

        int tmp = 0;
        int i = 0;

        do {
            i = tmpSt.indexOf(Language.columnSeparator);
            if (i != -1) {
                iItems[index][tmp] = tmpSt.substring(0, i);
                tmpSt = tmpSt.substring(i + 1);
                tmp++;
            }
        }
        while (!((i == -1) || (tmpSt.length() == 0) || (tmp > 1)));

        if ((tmpSt.length() != 0) && (tmp <= 1)) {
            iItems[index][tmp] = tmpSt;
        }
    }

    private void getItems() {
        iItems = new String[iLastValue - firstValue + 1][10];

        if (getInverted()) {
            for (int t = iLastValue; t >= firstValue; t--) {
                setItem(iLastValue - t, t);
            }
        } else {
            for (int t = firstValue; t <= iLastValue; t++) {
                setItem(t - firstValue, t);
            }
        }
    }
    private boolean useMaxWidth;

    /*
     * Alternate Legend sizing, use full Chart width when horizontal 
     * */
    public boolean getUseMaxWidth() {
        return useMaxWidth;
    }

    /*
     * Alternate Legend sizing, use full Chart width when horizontal 
     * */
    public void setUseMaxWidth(boolean value) {
        useMaxWidth = value;
    }

    private int calcMaxLegendValues(int yLegend, int a, int b, int c,
            int itemHeight) {
        if ((yLegend < a) && (itemHeight > 0)) {
            double tmp = itemHeight;
            if ((!getVertical()) && (getUseMaxWidth())) {
                b = chart.getWidth();
            }
            int result = (int) (((b - 2 * getFrame().getWidth())
                    - yLegend + c) / tmp);
            return Math.min(result, iTotalItems);
        } else {
            return 0;
        }
    }

    private int maxLegendValues(int yLegend, int itemHeight) {
        if (getVertical()) {
            if (getAutoSize()) {
                return calcMaxLegendValues(yLegend, shapeBounds.getBottom(), shapeBounds.getBottom() - shapeBounds.getTop(), shapeBounds.getTop(), itemHeight);
            } else {
                return calcMaxLegendValues(yLegend, shapeBounds.getTop() + iManualHeight, iManualHeight, shapeBounds.getTop(), itemHeight);
            }
        } else {
            if (getAutoSize()) {
                return calcMaxLegendValues(yLegend, shapeBounds.getRight(), shapeBounds.getRight() - shapeBounds.getLeft(), 0, itemHeight);
            } else {
                return calcMaxLegendValues(yLegend, chart.getGraphics3D().getXCenter() - Utils.round(iManualWidth / 2.0), iManualWidth, 0, itemHeight);
            }
        }
    }

    private String getItemText(int itemOrder, int Column) {
        if (getVertical()) {
            return iItems[itemOrder][Column];
        } else {
            //NCR TF02011575
            return iItems[itemOrder + iTotalItems - iLastValue - 1][Column];
        }
    }

    private void resizeVertical() {
        int tmp = LEGENDOFF2 + itemHeight * numRows;

        if (drawTitle()) {
            tmp += getTitle().getHeight() + 2;
            if (!getTitle().getTransparent()) {
                tmp += Math.abs(getTitle().getShadow().getHeight());
            }
        }

        if ((getAlignment() == LegendAlignment.BOTTOM) && (!getCustomPosition())) {
            if (getAutoSize()) {
                setTop(shapeBounds.getBottom() - tmp);
            } else {
                setTop(shapeBounds.getBottom() - iManualHeight);
            }
        } else {
            if (getAutoSize()) {
                setBottom(shapeBounds.getTop() + tmp);
            } else {
                setBottom(shapeBounds.getTop() + iManualHeight);
            }
        }

        shapeBounds.height = tmp;
    }

    static private int calcMargin(int margin, int defaultMargin, int size) {
        return (margin == 0) ? defaultMargin * size / 100 : margin;
    }

    /**
     * Returns the chart rectangle minus the space occupied by the Legend.
     *
     * @param rect Rectangle
     * @return Rectangle
     */
    public Rectangle resizeChartRect(Rectangle rect) {

        int tmp, left = rect.getLeft(), top = rect.getTop(), right = rect.getRight(), bottom = rect.getBottom();

        if (getResizeChart() && (!getCustomPosition())) {
            if (alignment == LegendAlignment.LEFT) {
                left = shapeBounds.getRight();
                right = (int) rect.getWidth();
            } else if (alignment == LegendAlignment.RIGHT) {
                right = shapeBounds.getLeft();
                if (getShadow().getVisible()) {
                    right += Math.min(0, getShadow().getWidth());
                }
            } else if (alignment == LegendAlignment.TOP) {
                top = shapeBounds.getBottom();
                if (getShadow().getVisible()) {
                    top += Math.max(0, getShadow().getHeight());
                }
            } else if (alignment == LegendAlignment.BOTTOM) {
                bottom = shapeBounds.y;
            }
        }

        if (getVertical()) {
            tmp = calcMargin(getHorizMargin(), 3, chart.getWidth());
            if (getAlignment() == LegendAlignment.LEFT) {
                left += tmp;
            } else {
                right -= tmp;
            }
        } else {
            tmp = calcMargin(getVertMargin(), 4, chart.getHeight());
            if (getAlignment() == LegendAlignment.TOP) {
                top += tmp;
            } else {
                bottom -= tmp;
            }
        }

        rect.x = left;
        rect.y = top;
        rect.width = right - left;
        rect.height = bottom - top;

        return rect;
    }

    private int getFirstItemTop() {
        int result = getShapeBounds().getTop();

        if (drawTitle()) {
            result = result + getTitle().getHeight();
            if (!getTitle().getTransparent()) {
                result = result + Math.abs(getTitle().getShadow().getHeight());
            }
        }

        return result;
    }

    public void paint(IGraphics3D g, final Rectangle rect) {
        if (!getAutoSize()) {
            iManualWidth = shapeBounds.width;
            iManualHeight = shapeBounds.height;
        }
        if (bCustomPosition) {
            setShapeBounds(new Rectangle(shapeBounds.getLeft(), shapeBounds.getTop(), rect.width,
                    rect.height));
        } else {
            setShapeBounds(rect);
        }

        calcLegendStyle();

        final boolean isPage = (iLegendStyle == LegendStyle.VALUES) && currentPage
                && (chart.getPage().getMaxPointsPerPage() > 0);

        if (isPage) {
            firstValue = (chart.getPage().getCurrent() - 1)
                    * chart.getPage().getMaxPointsPerPage();
        }

        iTotalItems = calcTotalItems();

        if (isPage) {
            iTotalItems = Math.min(iTotalItems,
                    chart.getPage().getMaxPointsPerPage());
        }

        g.setFont(getFont());

        iSpaceWidth = Utils.round(g.textWidth("i")) - 1;

        // calculate default Height for each Legend Item
        itemHeight = calcItemHeight();

        if (drawTitle()) {
            getTitle().calcHeight();
        }

        // calculate Frame Width offset
        if (getFrame().getVisible()) {
            frameWidth = Utils.round(getFrame().getWidth());
        } else {
            frameWidth = 0;
        }
        if (getBevel().getInner() != BevelStyle.NONE) {
            frameWidth = getBevel().getWidth();
        }

        if (getVertical()) {
            if (!bCustomPosition) {
                int tmpY = (int) (topLeftPos * shapeBounds.height * 0.01);
                shapeBounds.y += tmpY;
                shapeBounds.height -= tmpY;
            }

            numCols = 1;
            numRows = maxLegendValues(getFirstItemTop(), itemHeight);
//          numRows = maxLegendValues(shapeBounds.y, itemHeight);

            iLastValue = firstValue + Math.min(iTotalItems, numRows) - 1;
            getItems(); // Only Visible !!
        } else {
            iLastValue = firstValue + iTotalItems - 1;
            getItems(); // All !!
            tmpMaxWidth = calcColumnsWidth(ALLVALUES);
            iColorWidth = getSymbol().calcWidth(tmpMaxWidth);

            if (!bCustomPosition) {
                if (getAlignment() == LegendAlignment.BOTTOM) {
                    int tmpSize = getShadow().getHeight() + frameWidth - 1; //CDI LegendScrollBar
                    shapeBounds.setBottom(rect.getBottom() - tmpSize);
                    if (getShadow().getVisible()) {
                        shapeBounds.setBottom(shapeBounds.getBottom() - getShadow().getHeight());
                    }
                } else {
                    shapeBounds.setTop(rect.getTop() + frameWidth + 1);
                }
            }

            //CDI TF02010041
            int tmpCol = tmpMaxWidth + iColorWidth + 2 * LEGENDOFF4;
            if (hasCheckBoxes()) {
                tmpCol += CHECKBOXSIZE + 2 * LEGENDOFF2 + 3 * LEGENDOFF4;
            }
            if (textSymbolGap > 0) {
                tmpCol += textSymbolGap + 2 * LEGENDOFF2 + 3 * LEGENDOFF4;
            }

            numCols = maxLegendValues(2 * LEGENDOFF4, tmpCol);
            if (numCols > 0) {
                if (getAutoSize()) {
                    numRows = iTotalItems / numCols;
                    if ((iTotalItems % numCols) > 0) {
                        numRows++;
                    }
                } else {
                    numRows = Utils.round(Math.floor((double) iManualHeight / (double) itemHeight));
                }
                numRows = Math.min(numRows, maxNumRows);
            } else {
                numRows = 0;
            }
            /* adjust the last index now we know the max number of rows... */
            iLastValue = firstValue + Math.min(iTotalItems, numCols * numRows)
                    - 1;
        }

        if (iLastValue >= firstValue) {
            resizeVertical();

            if (getVertical()) {
                calcVerticalPositions();
            } else {
                calcHorizontalPositions();
            }

            if (chart.getParent() != null) {
                setShapeBounds(chart.getParent().getLegendResolver().getBounds(
                        this,
                        getShapeBounds()));
            }

            super.paint(g, getShapeBounds());

            if (drawTitle()) {
                getTitle().internalDraw(g, getShapeBounds());
                g.getFont().assign(getFont());
            }

            drawItems();
        }
    }

    /**
     * Returns the text string corresponding to a Legend position.<br>
     * The Legend position depends on Legend.LegendStyle. If LegendStyle is
     * lsSeries, then the text string will be the SeriesOrValueIndexth Active
     * Series Title.<br> If LegendStyle is lsValues, then the text string will
     * be the formatted SeriesOrValueIndexth value of the first Active Series
     * in the Chart.<br>
     * If LegendStyle is lsAuto and only one Active Series exists in the Chart,
     * then the LegendStyle is considered to be lsValues.<br>
     * If there is more than one Active Series then LegendStyle will be
     * lsSeries. <br>
     * Values are formatted accordingly to LegendTextStyle.
     *
     * @param seriesOrValueIndex int
     * @return String
     */
    public String formattedLegend(int seriesOrValueIndex) {
        IBaseChart c = chart;

        if (iLegendStyle == LegendStyle.SERIES) {
            return c.getSeriesTitleLegend(seriesOrValueIndex, !getCheckBoxes());
        } else if (iLegendStyle == LegendStyle.VALUES) {
            return c.formattedValueLegend(getLegendSeries(), seriesOrValueIndex);
        } else if (iLegendStyle == LegendStyle.LASTVALUES) {
            return c.formattedValueLegend(c.getSeries(seriesOrValueIndex),
                    c.getSeries(seriesOrValueIndex).
                    getCount()
                    - 1);
        } else if (iLegendStyle == LegendStyle.PALETTE) {
            return c.formattedValueLegend(getLegendSeries(), seriesOrValueIndex);
        }

        return "";
    }

    private void prepareSymbolPen() {
        chart.setLegendPen(getSymbol().getDefaultPen() ? null
                : getSymbol().getPen());
    }

    static private String removeChar(final String c, String s) {
        int i = 0;
        while ((i = s.indexOf(c)) != -1) {
            s = s.substring(0, i) + s.substring(i + 1);
        }
        return s;
    }

    /**
     * Returns the corresponding Legend text for the Series ValueIndex point.
     * <br>
     * Legend.LegendTextStyle is used to properly format the point values
     * and labels.
     *
     * @param aSeries Series
     * @param valueIndex int
     * @return String
     */
    public String formattedValue(Series aSeries, int valueIndex) {
        if (valueIndex != ALLVALUES) {
            String tmpResult = aSeries.getLegendString(valueIndex, getTextStyle());

            // eliminate breaks in String... }
            return removeChar(Language.lineSeparator, tmpResult);
        } else {
            return "";
        }
    }

    public LegendItems getLegendItems() {
        if (items == null) {
            items = new LegendItems(this);
        }

        return items;
    }

    private void setShapeWidthHeight() {
        if (getAutoSize()) {
            shapeBounds.height = shapeBounds.getBottom() - shapeBounds.getTop();
            shapeBounds.width = shapeBounds.getRight() - shapeBounds.getLeft();
        } else {
            shapeBounds.height = iManualHeight;
            shapeBounds.width = iManualWidth;
        }
    }
}
