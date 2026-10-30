package com.steema.teechart.styles;

import com.steema.teechart.languages.Language;

import com.steema.teechart.editors.gallery.Gallery;

import com.steema.teechart.drawing.Color;
import com.steema.teechart.drawing.IGraphics3D;
import com.steema.teechart.drawing.ChartPen;
import com.steema.teechart.drawing.ChartBrush;
import com.steema.teechart.drawing.Point;
import com.steema.teechart.IBaseChart;
import com.steema.teechart.misc.Utils;

/**
 *
 * <p>Title: Highlow class</p>
 *
 * <p>Description: HighLow Series.</p>
 *
 * <p>Example:
 * <pre><font face="Courier" size="4">
 * series = new HighLow(myChart.getChart());
 * series.fillSampleValues(20);
 * series.setColor(Color.RED);
 * series.getHighBrush().setColor(Color.LIME);
 * series.getHighPen().setColor(Color.BLUE);
 * series.getHighPen().setWidth(2);
 * series.getLowBrush().setColor(Color.WHITE);
 * series.getPen().setColor(Color.BLUE);
 * series.setCursor(new Cursor(Cursor.CROSSHAIR_CURSOR));
 *
 * highFillButton = new JButton("High Fill...");
 * lowFillButton = new JButton("Low Fill...");
 * highButton = new ButtonPen(series.getHighPen(), "High...");
 * lowButton = new ButtonPen(series.getLowPen(), "Low...");
 * linesButton = new ButtonPen(series.getPen(), "Lines...");
 * </font></pre></p>
 *
 * <p>Copyright (c) 2005-2019 by Steema Software SL. All Rights Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 *
 */
public class HighLow extends Series {

    /**
	 * 
	 */
    private static final long serialVersionUID = 1L;
    private ChartPen pen;
    private ChartPen highPen;
    private ValueList low;
    private ChartPen lowPen;

    transient private int OldX;
    transient private int OldY0;
    transient private int OldY1;

    private ChartBrush highBrush;
    private ChartBrush lowBrush;

    public HighLow() {
        this((IBaseChart)null);
    }

    public HighLow(IBaseChart c) {
        super(c);
        calcVisiblePoints = false;
        getPen().setColor(Color.EMPTY);
        low = new ValueList(this, Language.getString("ValuesLow"));
    }

    public void setChart(IBaseChart c) {

        super.setChart(c);

        if (pen != null) {
            pen.setChart(c);
        }
        if (highPen != null) {
            highPen.setChart(c);
        }
        if (lowPen != null) {
            lowPen.setChart(c);
        }
        if (highBrush != null) {
            highBrush.setChart(c);
        }
        if (lowBrush != null) {
            lowBrush.setChart(c);
        }
    }
    
    public void assign(Series source) {
        if (source instanceof HighLow) {
            HighLow tmp = (HighLow)source;
            tmp.setChart(((HighLow) source).getChart());
        }
        super.assign(source);
    }

    /**
     * Sets the Pen to draw the vertical dividing lines of the HighLow series.
     *
     * @return ChartPen
     */
    public ChartPen getPen() {
        if (pen == null) {
            pen = new ChartPen(chart);
        }
        return pen;
    }

    /**
     * Defines the Pen for the High value.
     *
     * @return ChartPen
     */
    public ChartPen getHighPen() {
        if (highPen == null) {
            highPen = new ChartPen(chart, Color.BLACK);
        }
        return highPen;
    }

    /**
     * Defines the Pen for the Low value.

     * @return ChartPen
     */
    public ChartPen getLowPen() {
        if (lowPen == null) {
            lowPen = new ChartPen(chart, Color.BLACK);
        }
        return lowPen;
    }

    /**
     * Defines the Brush for the Low value.
     *
     * @return ChartBrush
     */
    public ChartBrush getLowBrush() {
        if (lowBrush == null) {
            lowBrush = new ChartBrush(chart, Color.EMPTY, false);
        }
        return lowBrush;
    }

    /**
     * Defines the Brush for the High value.
     *
     * @return ChartBrush
     */
    public ChartBrush getHighBrush() {
        if (highBrush == null) {
            highBrush = new ChartBrush(chart, Color.EMPTY, false);
        }
        return highBrush;
    }

    public ValueList getHighValues() {
        return vyValues;
    }

    public ValueList getLowValues() {
        return low;
    }


    protected void addSampleValues(int numValues) {
        SeriesRandom r = randomBounds(numValues);
        double tmp = r.DifY * r.Random();

        for (int t = 1; t <= numValues; t++) {
            tmp += r.Random() * (Utils.round(r.DifY / 5.0)) -
                    (r.DifY / 10.0);
            Add(r.tmpX, tmp, tmp - r.Random() * (Utils.round(r.DifY / 5.0)));
            r.tmpX += r.StepX;
        }
    }

    /**
     * Adds a new High and Low value to a HighLow series.
     *
     * @param x double
     * @param h double
     * @param l double
     * @return int
     */
    public int Add(double x, double h, double l) {
        return Add(x, h, l, "", Color.EMPTY);
    }

    /**
     * Adds a new High and Low value to a HighLow series.
     *
     * @param x double
     * @param h double
     * @param l double
     * @param color Color
     * @return int
     */
    public int Add(double x, double h, double l, Color color) {
        return Add(x, h, l, "", color);
    }

    /**
     * Adds a new High and Low value to a HighLow series and label.
     *
     * @param x double
     * @param h double
     * @param l double
     * @param text String
     * @return int
     */
    public int Add(double x, double h, double l, String text) {
        return Add(x, h, l, text, Color.EMPTY);
    }

    /**
     * Adds a new High and Low value to a HighLow series with label and color.
     *
     * @param x double
     * @param h double
     * @param l double
     * @param text String
     * @param color Color
     * @return int
     */
    public int Add(double x, double h, double l, String text, Color color) {
        low.tempValue = l;
        return add(x, h, text, color);
    }

    public void createSubGallery(Gallery addSubChart) {
        super.createSubGallery(addSubChart);
        addSubChart.createSubChart(Language.getString("Filled"));
        addSubChart.createSubChart(Language.getString("NoLines"));
        addSubChart.createSubChart(Language.getString("NoHigh"));
        addSubChart.createSubChart(Language.getString("NoLow"));
    }

    public void setSubGallery(int index) {
        switch (index) {
        case 1: {
            getHighBrush().setVisible(true);
            getLowBrush().setVisible(true);
        }
        break;
        case 2:
            getPen().setVisible( false);
            break;
        case 3:
            getHighPen().setVisible( false);
            break;
        case 4:
            getLowPen().setVisible( false);
            break;
        default:
            super.setSubGallery(index);
        }
    }

    private void DrawLine(ChartPen APen, int BeginY, int EndY, int x) {

        IGraphics3D g = chart.getGraphics3D();

        if (APen.getVisible()) {
            g.setPen(APen);
            if (chart.getAspect().getView3D()) {
                g.moveTo(OldX, BeginY, getMiddleZ());
                g.lineTo(x, EndY, getMiddleZ());
            } else {
                g.moveTo(OldX, BeginY);
                g.lineTo(x, EndY);
            }
        }
    }


    /**
     * Called internally. Draws the "ValueIndex" point of the Series.
     *
     * @param valueIndex int
     */
    public void drawValue(int valueIndex) {
        IGraphics3D g = chart.getGraphics3D();

        int x = calcXPos(valueIndex);
        int y0 = calcYPos(valueIndex);
        int y1 = calcYPosValue(low.getValue(valueIndex));
        Color tmpColor = getValueColor(valueIndex);

        if (valueIndex != getFirstVisible()) {
            ChartBrush b = (low.getValue(valueIndex) <
                            getHighValues().getValue(valueIndex)) ?
                           highBrush :
                           lowBrush;

            if ((b != null) && (b.getVisible())) {
                g.getPen().setVisible(false);

                int tmpX = OldX;
                if (getPen().getVisible()) {
                    tmpX += getPen().getWidth();
                }

                g.setBrush(b);

                if (g.getBrush().getColor().isEmpty()) {
                    g.getBrush().setColor(tmpColor);
                }

                g.plane(new Point(tmpX, OldY0), new Point(tmpX, OldY1),
                        new Point(x, y1), new Point(x, y0), getMiddleZ());
            }

            DrawLine(getHighPen(), OldY0, y0, x);
            DrawLine(getLowPen(), OldY1, y1, x);
        }

        if (getPen().getVisible()) {
            Color tmp = pen.getColor();
            if (tmp.isEmpty()) {
                tmp = tmpColor;
            }

            g.setPen(pen);
            g.getPen().setColor(tmp);

            if (chart.getAspect().getView3D()) {
                g.verticalLine(x, y0, y1, getMiddleZ());
            } else {
                g.verticalLine(x, y0, y1);
            }
        }

        OldX = x;
        OldY0 = y0;
        OldY1 = y1;
    }

    /**
     * True if Series source is HighLow.<br>
     * Returns false if the Value parameter is the same as Self. <br>
     * It's used to validate the DataSource method both at design and run-time.
     *
     * @param value ISeries
     * @return boolean
     */
    public boolean isValidSourceOf(ISeries value) {
        return value instanceof HighLow;
    }

    /**
     * The Maximum Value of the Series Y Values List.
     *
     * @return double
     */
    public double getMaxYValue() {
        return Math.max(super.getMaxYValue(), low.getMaximum());
    }

    /**
     * The Minimum Value of the Series Y Values List.
     *
     * @return double
     */
    public double getMinYValue() {
        return Math.min(super.getMinYValue(), low.getMinimum());
    }

    /**
     * Gets descriptive text.
     *
     * @return String
     */
    public String getDescription() {
        return Language.getString("GalleryHighLow");
    }
}

