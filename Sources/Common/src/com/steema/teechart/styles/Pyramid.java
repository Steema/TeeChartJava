package com.steema.teechart.styles;


import com.steema.teechart.languages.Language;
import com.steema.teechart.drawing.ChartPen;
import com.steema.teechart.IBaseChart;
import com.steema.teechart.drawing.ChartBrush;
import com.steema.teechart.drawing.Color;
import com.steema.teechart.Rectangle;
import com.steema.teechart.misc.Utils;

/**
 *
 * <p>Title: Pyramid class</p>
 *
 * <p>Description: Pyramid Series.</p>
 *
 * <p>Copyright (c) 2005-2019 by Steema Software SL. All Rights
 * Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 *
 */
public class Pyramid extends Series {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int size = 50;
    private ChartPen pen;

    public Pyramid(IBaseChart c) {
        super(c);
        calcVisiblePoints = false;
        setColorEach(true);
    }

    public Pyramid() {
        this(null);
    }
    
    public void assign(Series source) {
        if (source instanceof Pyramid) {
            Pyramid tmp = (Pyramid)source;
            tmp.setSizePercent(((Pyramid)source).getSizePercent()); 
        }
        super.assign(source);
    }      

    /**
     * The size of Pyramid base relative to Chart Axis bounding width.<br>
     * Default value: 50
     *
     * <p>Example:
     * <pre><font face="Courier" size="4">
     * series.setSizePercent(60);
     * </font></pre></p>
     *
     * @return int
     */
    public int getSizePercent() {
        return size;
    }

    /**
     * Sets size of Pyramid base relative to Chart Axis bounding width.<br>
     * Default value: 50
     *
     * @param value int
     */
    public void setSizePercent(int value) {
        size = setIntegerProperty(size, value);
    }

    /**
     * Element Pen characteristics.
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
     * Sets Brush characteristics for the Pyramid Series.
     *
     * @return ChartBrush
     */
    public ChartBrush getBrush() {
        return bBrush;
    }

    private double acumUpTo(int upToIndex) {
        double result = 0;
        for (int t = 0; t <= upToIndex; t++) {
            result += mandatory.value[t];
        }
        return result;
    }

    public void calcHorizMargins(Margins margins) {
        margins.min = 20;
        margins.max = 20;
    }

    protected void drawMark(int valueIndex, String s,
                            SeriesMarksPosition position) {
        position.leftTop.y = getVertAxis().calcPosValue(acumUpTo(valueIndex));
        super.drawMark(valueIndex, s, position);
    }

    /**
     * Called internally. Draws the "ValueIndex" point of the Series.
     *
     * @param valueIndex int
     */
    public void drawValue(int valueIndex) {
        if (!isNull(valueIndex)) {
            chart.setBrushCanvas(getValueColor(valueIndex), getBrush(),
                                 getBrush().getColor());

            chart.getGraphics3D().setPen(getPen());

            double tmp = acumUpTo(valueIndex - 1);
            double tmpTrunc = 100.0 - (tmp * 100.0 / getMandatory().getTotal());

            int tmpSize = Utils.round(getSizePercent() *
                                          getHorizAxis().iAxisSize *
                                          0.005);

            int tmpX = Utils.round(tmpTrunc * tmpSize * 0.01);

            Rectangle r = new Rectangle();

            r.x = getHorizAxis().calcPosValue(getMinXValue()) - tmpX;
            r.width = 2 * tmpX;

            double tmpTruncZ = 100.0 - tmpTrunc;
            int tmpZ = (tmpTruncZ > 0) ?
                       Utils.round(tmpTruncZ * (getEndZ() - getStartZ()) *
                                       0.005) : 0;

            r.height = getVertAxis().calcPosValue(tmp);

            tmp += mandatory.value[valueIndex];
            r.y = getVertAxis().calcPosValue(tmp);

            r.height -= r.y; // DB

            tmpTrunc = 100.0 - (tmp * 100.0 / mandatory.getTotal());

            int tmpZ2 = (tmpTrunc < 100) ?
                        Utils.round(tmpTrunc * (getEndZ() - getStartZ()) *
                                        0.005) : 0;

            chart.getGraphics3D().pyramidTrunc(r, getStartZ() + tmpZ,
                                               getEndZ() - tmpZ,
                                               Utils.round(tmpTrunc *
                    tmpSize * 0.01),
                                               tmpZ2);
        }
    }

    /**
     * Returns whether the Series needs to draw points in ascending/descending
     * order.<br>
     * Some Series need to draw their points in descending order (starting
     * from the last point to the first) depending on certain situations.
     * For example, when the horizontal axis Inverted property is true.
     *
     * @return boolean
     */
    public boolean drawValuesForward() {
        return!getVertAxis().getInverted();
    }

    /**
     * The Maximum Value of the Series X Values List.
     *
     * @return double
     */
    public double getMaxXValue() {
        return getMinXValue();
    }

    /**
     * The Maximum Value of the Series Y Values List.
     *
     * @return double
     */
    public double getMaxYValue() {
        return mandatory.getTotalABS();
    }

    /**
     * The Minimum Value of the Series X Values List.
     *
     * @return double
     */
    public double getMinXValue() {
        return chart.getSeriesIndexOf(this);
    }

    /**
     * The Minimum Value of the Series Y Values List.
     *
     * @return double
     */
    public double getMinYValue() {
        return 0;
    }

    /**
     * Gets descriptive text.
     *
     * @return String
     */
    public String getDescription() {
        return Language.getString("GalleryPyramid");
    }
}

