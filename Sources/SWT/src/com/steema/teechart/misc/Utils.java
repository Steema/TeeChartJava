package com.steema.teechart.misc;

import java.io.*;
import java.net.*;

import com.steema.teechart.*;
import com.steema.teechart.drawing.*;
import com.steema.teechart.functions.*;
import com.steema.teechart.functions.Custom;
import com.steema.teechart.styles.*;
import com.steema.teechart.styles.Error;
import com.steema.teechart.styles.Shape;
import com.steema.teechart.tools.*;

/**
 *
 * <p>Title: misc.Utils class</p>
 *
 * <p>Description: Chart utility procedures</p>
 *
 * <p>Copyright (c) 2005-2019 by Steema Software SL. All Rights Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 *
 */
public class Utils {

    static public final double PISTEP = Math.PI / 180;

    static public String formatFloat(double value) {
        return Double.toString(value);
    }

    /**
     * Evaluates and returns a steema.<!-- -->teechart.<!-- -->DateTimeStep value
     * as an Axis double scale that may be used to set the steema.<!--
     * -->teechart.<!-- -->Axis.<!-- -->Increment.
     *
     *
     * @param value DateTimeStep
     * @return double
     */
    static public double getDateTimeStep(int value) {
        return (int) com.steema.teechart.DateTimeStep.STEP[value];
    }
    static public final String BuildNumber = "3.2021.0804";
    static final long TicksPerMillisecond = 10000;
    static final long TicksPerSecond = TicksPerMillisecond * 1000;
    static final long TicksPerMinute = TicksPerSecond * 60;
    static final long TicksPerHour = TicksPerMinute * 60;
    static final long TicksPerDay = TicksPerHour * 24;
    // Number of milliseconds per time unit
    static final int MillisPerSecond = 1000;
    static final int MillisPerMinute = MillisPerSecond * 60;
    static final int MillisPerHour = MillisPerMinute * 60;
    static final int MillisPerDay = MillisPerHour * 24;
    static final int DaysPerYear = 365;
    // Number of days in 4 years
    static final int DaysPer4Years = DaysPerYear * 4 + 1;
    // Number of days in 100 years
    static final int DaysPer100Years = DaysPer4Years * 25 - 1;
    // Number of days in 400 years
    static final int DaysPer400Years = DaysPer100Years * 4 + 1;
    // Number of days from 1/1/0001 to 12/31/1600
    // static final int DaysTo1601 = DaysPer400Years * 4;
    // Number of days from 1/1/0001 to 12/30/1899
    static final int DaysTo1899 = DaysPer400Years * 4 + DaysPer100Years * 3 - 367;
    static final long DoubleDateOffset = DaysTo1899 * TicksPerDay;
    // The minimum OA date is 0100/01/01 (Note it's year 100).
    // The maximum OA date is 9999/12/31
    static final long OADateMinAsTicks = (DaysPer100Years - DaysPerYear)
            * TicksPerDay;

    // Copied from Microsoft's SSCLI sources.
    static private double ticksToOADate(long value) throws Exception {
        if (value == 0) {
            return 0.0; // Returns OleAut's zero'ed date value.
        }

        if (value < TicksPerDay) { // This is a fix for VB. They want the default
            // day to be 1/1/0001 rathar then 12/30/1899.
            value += DoubleDateOffset; // We could have moved this fix down but we
            // would like to keep the bounds check.
        }
        if (value < OADateMinAsTicks) {
            throw new Exception("Arg_OleAutDateInvalid");
        }

        // Currently, our max date == OA's max date (12/31/9999), so we don't
        // need an overflow check in that direction.
        long millis = (value - DoubleDateOffset) / TicksPerMillisecond;
        if (millis < 0) {
            long frac = millis % MillisPerDay;
            if (frac != 0) {
                millis -= (MillisPerDay + frac) * 2;
            }
        }
        return (double) millis / MillisPerDay;
    }

    static public double stringToDouble(final String text, final double value) {
        if (text.length() == 0) {
            return value;
        } else {
            try {
                return Double.parseDouble(text);
            } catch (NumberFormatException e) {
                return value;
            }
        }
    }

    static private void privateSort(int l, int r, Comparator c, Swapper s) {
        int i = l;
        int j = r;
        int x = (i + j) / 2;
        while (i < j) {
            while (c.compare(i, x) < 0) {
                i++;
            }
            while (c.compare(x, j) < 0) {
                j--;
            }
            if (i < j) {
                s.swap(i, j);
                if (i == x) {
                    x = j;
                } else if (j == x) {
                    x = i;
                }
            }
            if (i <= j) {
                i++;
                j--;
            }
        }
        if (l < j) {
            privateSort(l, j, c, s);
        }
        if (i < r) {
            privateSort(i, r, c, s);
        }
    }

    static public void sort(int startIndex, int endIndex, Comparator c, Swapper s) {
        privateSort(startIndex, endIndex, c, s);
    }

    static public void sort(final int[] x, int startIndex, int endIndex,
            Comparator c) {
        sort(startIndex, endIndex, c, new Swapper() {

            public void swap(int a, int b) {
                int tmp = x[a];
                x[a] = x[b];
                x[b] = tmp;
            }
        ;
    }
    );
	}

	/**
	 * Number of chart Tool types.
	 */
	public static final int TOOLTYPESCOUNT = 24; // CDI ExtraLegend + GridBand
    // (+2)
    /**
     * List of chart Tool classes.
     */
    final static public Class[] toolTypesOf = {Annotation.class, Rotate.class,
        ChartImage.class, CursorTool.class, DragMarks.class, AxisArrow.class,
        ColorLine.class, ColorBand.class, DrawLine.class, NearestPoint.class,
        MarksTip.class, ExtraLegend.class, DragPoint.class,
        SeriesAnimation.class, PieTool.class, GridTranspose.class,
        GanttTool.class, GridBand.class,
        // AxisScrollEditor.class,
        // AxisToolEdit.class,
        // HotSpotEditor.class,
        // LightToolEditor.class,
        // ScrollBarEditor.class,
        // SurfaceNearestToolEditor.class,
        // ToolSeriesEditor.class,
        // ZoomToolEditor.class
        PageNumber.class, SeriesBand.class, AxisBreaksTool.class,
        SubChartTool.class, ScrollPager.class, SeriesRegion.class};

    /**
     * Returns the index in ToolTypesOf list of a given tool instance.
     *
     * @param tool Tool to search its type in list
     * @return int
     */
    static public int toolTypeIndex(final Tool tool) {
        for (int t = 0; t < Utils.TOOLTYPESCOUNT; t++) {
            if (Utils.toolTypesOf[t] == tool.getClass()) {
                return t;
            }
        }
        return -1;
    }
    /**
     * Number of chart Function types.
     */
    public static final int FUNCTIONTYPESCOUNT = 38;
    /**
     * List of chart Function types.
     */
    final static public Class[] functionTypesOf = {Add.class, // 0
        Subtract.class, // 0
        Multiply.class, // 0
        Divide.class, // 0
        High.class, // 0
        Low.class, // 0
        Average.class, // 0
        Count.class, // 0
        Momentum.class, // 2
        MomentumDivision.class, // 2
        Cumulative.class, // 1
        ExpAverage.class, // 1
        Smoothing.class, // 3
        Custom.class, // 3
        RootMeanSquare.class, // 1
        StdDeviation.class, // 1
        Stochastic.class, // 2
        ExpMovAverage.class, // 2
        Performance.class, // 2
        CrossPoints.class, // 3
        CompressOHLC.class, // 2
        CLV.class, // 2
        OBV.class, // 2
        CCI.class, // 2
        MovingAverage.class, // 1
        PVO.class, // 2
        DownSampling.class, // 3
        Trend.class, // 3
        Correlation.class, // 1
        Variance.class, // 1
        Perimeter.class, // 3
        CurveFitting.class, // 3
        ADX.class, // 2
        Bollinger.class, // 2
        MACD.class, // 2
        SAR.class, // 2
        RSI.class, // 2
        HistogramFunction.class // 1
};
    final static public int[] functionGalleryPage = {0, 0, 0, 0, 0, 0, 0, 0, 2,
        2, 1, 1, 3, 3, 1, 1, 2, 2, 2, 3, 2, 2, 2, 2, 1, 2, 3, 3, 1, 1, 3, 3, 2,
        2, 2, 2, 2, 1};

    static public int seriesTypesIndex(final ISeries s) {
        return seriesTypesIndex(s.getClass());
    }

    static public int seriesTypesIndex(final Class seriesType) {
        for (int t = 0; t < SERIESTYPESCOUNT; t++) {
            if (seriesTypesOf[t] == seriesType) {
                return t;
            }
        }
        return -1;
    }
    public static final int SERIESTYPESCOUNT = 61;
    final static public int[] seriesGalleryCount = {2, 2, 2, 2, 2, 2, 2, 1, 3,
        1, 1, 1, 1, 1, 1, 1, 1, 2, 2, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2, 2,
        1, 1, 1, 1, 1, 1, 2, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1,
        1, 1, 1, 1};
    final static public int[] seriesGalleryPage = {0, 0, 0, 0, 0, 0, 0, 0, 0, 3,
        0, 0, 2, 3, 2, 4, 4, 3, 3, 7, 7, 3, 4, 7, 7, 4, 4, 1, 1, 1, 4, 3, 7, 1,
        4, 1, 1, 1, 0, 4, 2, 5, 4, 3, 3, 7, 4, 5, 5, 5, 5, 5, 1, 6, 1, 1, 7, 1,
        1, 1, 1};

    /*
     * 0=Standard
     * 1=Statistics
     * 2=Financial
     * 3=Extended
     * 4=3D
     * 5=Gauges
     * 6=Maps
     * 7=Other
     */
    final static public Class[] seriesTypesOf = {Line.class, Points.class,
        Area.class, FastLine.class, HorizLine.class, Bar.class, HorizBar.class,
        Pie.class, Shape.class, Arrow.class, Bubble.class, Gantt.class,
        Candle.class, Donut.class, Volume.class, Bar3D.class, Points3D.class,
        Polar.class, Radar.class, Clock.class, WindRose.class, Pyramid.class,
        Surface.class, LinePoint.class, BarJoin.class, ColorGrid.class,
        Waterfall.class, Histogram.class, Error.class, ErrorBar.class,
        Contour.class, Smith.class, Calendar.class, HighLow.class,
        TriSurface.class, Funnel.class, Box.class, HorizBox.class,
        HorizArea.class, Tower.class, PointFigure.class, Gauges.class,
        Vector3D.class, Map.class, Bezier.class, ImageBar.class,
        IsoSurface.class, CircularGauge.class, LinearGauge.class,
        VerticalLinearGauge.class, NumericGauge.class, KnobGauge.class,
        HorizHistogram.class, World.class, ErrorPoint.class, ErrorPoint3D.class,
        Violin.class, HorizViolin.class, BeeSwarm.class,
        HorizBeeSwarm.class};

    public static InputStream downloadURLStream(final String url)
            throws MalformedURLException, IOException {
        return new URL(url).openStream();
    }

    public static String downloadURLString(final String url) throws IOException {

        final Reader reader = new InputStreamReader(downloadURLStream(url), "UTF-8");

        int c;
        StringBuffer sb = new StringBuffer();
        while ((c = reader.read()) != -1) {
            sb.append((char) c);
        }

        return sb.toString();
    }

    static public int MulDiv(int a, int b, int c) {
        int nMul = a * b, nMod = nMul % c, nRes = nMul / c;

        if (nMod >= c / 2) { // Round up if >= 0.5
            nRes++;
        }
        return nRes;
    }

    static public String dateTimeToStr(final double datetime) {
        return new DateTime(datetime).toShortDateString();
    }

    static public String dateTimeToStr(final DateTime datetime) {
        return datetime.toShortDateString();
    }

    static public String dateTimeToDateTimeStr(final DateTime datetime) {
        return datetime.toShortDateString() + " " + datetime.toShortTimeString();
    }

    static public String timeToStr(final double datetime) {
        return new com.steema.teechart.DateTime(datetime).toShortTimeString();
    }

    static public String timeToStr(final DateTime datetime) {
        return datetime.toShortTimeString();
    }

    static public String arrayToString(final String[] a) {
        return arrayToString(a, '\n');
    }

    static public String arrayToString(final String[] a, char separator) {
        StringBuffer result = new StringBuffer();
        if (a.length > 0) {
            result.append(a[0]);
            for (int i = 1; i < a.length; i++) {
                result.append(separator);
                result.append(a[i]);
            }
        }
        return result.toString();
    }

    static public void drawCheckBox(int x, int y, IGraphics3D g,
            boolean drawChecked, boolean CheckBox, Color backColor) {

        int TeeCheckBoxSize = 11;

        Rectangle r = new Rectangle(x + 1, y + 1, TeeCheckBoxSize, TeeCheckBoxSize);  //TJ71016526
        g.getPen().setVisible(false);
        g.getBrush().setVisible(true);
        g.getBrush().setSolid(true);
        g.getBrush().setColor(backColor);
        g.getBrush().getGradient().setVisible(false);
        g.rectangle(r);

        g.getPen().setVisible(true);
        g.getPen().setStyle(DashStyle.SOLID);
        g.getPen().setWidth(1);
        g.getPen().setColor(Color.GRAY);
        g.line(x + TeeCheckBoxSize, y, x, y);
        g.line(x, y, x, y + TeeCheckBoxSize + 1);

        g.getPen().setColor(Color.lightGray);
        g.line(x, y + TeeCheckBoxSize + 1, x + TeeCheckBoxSize + 1, y
                + TeeCheckBoxSize + 1);
        g.line(x + TeeCheckBoxSize + 1, y + TeeCheckBoxSize + 1, x
                + TeeCheckBoxSize + 1, y);

        g.getPen().setColor(Color.BLACK);
        g.line(x + 1, y + 1, x + TeeCheckBoxSize, y + 1);
        g.line(x + 1, y + 1, x + 1, y + TeeCheckBoxSize);

        if (drawChecked) {
            g.getPen().setStyle(DashStyle.SOLID);
            g.getPen().setColor(Color.BLACK);
            for (int t = 1; t < 4; t++) {
                g.line(x + 2 + t, y + 4 + t, x + 2 + t, y + 7 + t);
            }
            for (int t = 1; t < 5; t++) {
                g.line(x + 5 + t, y + 7 - t, x + 5 + t, y + 10 - t);
            }
        }
    }

    public static Color getEmptyColor() {
        return Color.EMPTY;
    }

    public static boolean colorIsEmpty(final Color color) {
        return color == getEmptyColor();
    }

    public static String colorToHex(Color color, boolean ARGB) {
        String A = intToHexString(color.getAlpha());
        String R = intToHexString(color.getRed());
        String G = intToHexString(color.getGreen());
        String B = intToHexString(color.getBlue());

        String s;

		if (ARGB)
            s = A + R + G + B;
		else
            s = R + G + B;

        return s;
    }

    private static String intToHexString(int n) {
        String result = Integer.toHexString(n).toUpperCase();
        if (result.length() < 2) {
            result = "0" + result;
        }
        return result;
    }

    private static int reduceByte(int Value, int APercentage) {
        double tmp = Value;
        if (Value != 0) {
            tmp = Value * (APercentage / 100.0);
        }
        return (int) tmp;
    }

    public static Color darkenColor(final Color AColor, int Percentage) {
        int tmpR = reduceByte(AColor.getRed(), Percentage);
        int tmpG = reduceByte(AColor.getGreen(), Percentage);
        int tmpB = reduceByte(AColor.getBlue(), Percentage);

        return Color.fromArgb(AColor.getAlpha(), tmpR, tmpG, tmpB);
    }

    /*
     * public static int round(double value) { } return (int)Math.round(value); }
     */
    // Utils.round out and this round implemented with floor for
    // CLDC 1.1 compatibility
    /**
     * Returns the integer nearest to "value" parameter
     *
     * @param value double
     * @return int
     */
    public static int round(final double value) {
        return (int) Math.floor(value + 0.5d);
    }

    static public double calcDistance(final Point p, int x0, int y0, int x1, int y1) {

        double dx, dy;

        if ((x1 == x0) && (y1 == y0)) {

            dx = p.x - x0;
            dy = p.y - y0;

            return Math.sqrt(dx * dx + dy * dy);

        } else {

            dx = x1 - x0;
            dy = y1 - y0;

            final double tmpResult = ((p.x - x0) * dx + (p.y - y0) * dy) / (dx * dx + dy * dy);

            if (tmpResult < 0) {
                dx = p.x - x0;
                dy = p.y - y0;
            } else if (tmpResult > 1) {
                dx = p.x - x1;
                dy = p.y - y1;
            } else {
                dx = p.x - (x0 + tmpResult * dx);
                dy = p.y - (y0 + tmpResult * dy);
            }

            return Math.sqrt(dx * dx + dy * dy);
        }
    }

    static public final double sqr(final double x) {
        return x * x;
    }

    /**
     * Note: This function has been added to Utils because J2ME Math class does
     * not include it.
     *
     * Returns Math.log of value parameter.
     *
     * @param value double
     * @return double
     */
    public static double log(final double value) {
        return Math.log(value);
    }

    static public double log(final double x, final double numBase) {
        return Math.log(x) / Math.log(numBase);
    }

    static public Point pointAtDistance(final Point aFrom, final Point aTo, int aDist) {

        final Point res = new Point(aTo.x, aTo.y);

        if (aFrom.x != aTo.x) {
            final double tmp = Utils.atan2((aTo.y - aFrom.y), (aTo.x - aFrom.x));

            final double tmpSin = Math.sin(tmp);
            final double tmpCos = Math.cos(tmp);

            res.x -= Utils.round(aDist * tmpCos);
            res.y -= Utils.round(aDist * tmpSin);
        } else {
            if (aTo.y < aFrom.y) {
                res.y += aDist;
            } else {
                res.y -= aDist;
            }
        }

        return res;
    }

    static public boolean isNullOrEmpty(String str) {
        return str == null || str.length() == 0;
    }

    static public com.steema.teechart.Rectangle emptyRectangle() {
        return new Rectangle(Rectangle.EMPTY);
    }

    static public boolean isEmptyRectangle(com.steema.teechart.Rectangle rect) {
        return rect == com.steema.teechart.Rectangle.EMPTY;
    }

    static public boolean pointInLineTolerance(Point p, int x0, int y0, int x1,
            int y1, int tolerance) {
        if (((p.x == x0) && (p.y == y0)) || ((p.x == x1) && (p.y == y1))) {
            return true;
        } else {
            return Math.abs(calcDistance(p, x0, y0, x1, y1)) <= tolerance++;
        }
    }

    static public boolean intersectWithRect(com.steema.teechart.Rectangle r1,
            com.steema.teechart.Rectangle r2) {
        com.steema.teechart.Rectangle r3 = new Rectangle(Rectangle.EMPTY);

        Rectangle.intersect(r1, r2, r3);

        return (!r3.isEmpty());
    }

    /**
     * Note: This code has been copied from Java JDK Double.compare method,
     * because J2ME Double class does not include it.
     *
     * Compares Double d1 with d2. Returns 0 if both are equal. Returns -1 if d1
     * is lower than d2, and 1 if d1 is bigger than d2.
     *
     * @param d1 double
     * @param d2 double
     * @return int
     */
    public static int compareDoubles(final double d1, final double d2) {
        if (d1 < d2) {
            return -1; // Neither val is NaN, thisVal is smaller
        }
        if (d1 > d2) {
            return 1; // Neither val is NaN, thisVal is larger
        }

        final long thisBits = Double.doubleToLongBits(d1);
        final long anotherBits = Double.doubleToLongBits(d2);

        return (thisBits == anotherBits ? 0 : // Values are equal
                (thisBits < anotherBits ? -1 : // (-0.0, 0.0) or (!NaN, NaN)
                1)); // (0.0, -0.0) or (NaN, !NaN)
    }

    /**
     * Note: This method has been implemented here in Utils class because J2ME
     * Math class does not include it.
     *
     * Returns Math.atan2.
     *
     * @param y double
     * @param x double
     * @return double
     */
    public static double atan2(final double y, final double x) {
        return Math.atan2(y, x);
    }

    /**
     * Note: This method has been implemented here in Utils class because J2ME
     * Math class does not include it.
     *
     * Returns Math.pow.
     *
     * @param a double
     * @param b double
     * @return double
     */
    public static double pow(final double a, final double b) {
        return Math.pow(a, b);
    }

    public static PointDouble offsetPoint(PointDouble value, double x, double y) {
        return new PointDouble(value.x + x, value.y + y);
    }

    static public double rad2Deg(double radian) {
        return radian * (180 / Math.PI);
    }

    /**
     * Note: This method has been implemented here in Utils class because J2ME
     * Math class does not include it.
     *
     * Returns Math.exp.
     *
     * @param a double
     * @return double
     */
    public static double exp(final double a) {
        return Math.exp(a);
    }

    /**
     * Note: This method has been implemented here in Utils class because J2ME
     * Math class does not include it.
     *
     * Truncate a number to the next nearest integer towards 0
     *
     * @param d double
     * @return double
     */
    public static double trunc(final double d) {
        return (double) ((long) d);
    }

    public static Point[] rectToPolygon(final com.steema.teechart.Rectangle r) {
        Point[] result = new Point[5];
        Point topLeft = new Point(r.getLeft(), r.getTop());
        Point topRight = new Point(r.getRight(), r.getTop());
        Point bottomRight = new Point(r.getRight(), r.getBottom());
        Point bottomLeft = new Point(r.getLeft(), r.getBottom());

        result[0] = topLeft;
        result[1] = topRight;
        result[2] = bottomRight;
        result[3] = bottomLeft;
        result[4] = topLeft;

        return result;
    }

    public static PointDouble[] rectToPolygonDouble(final com.steema.teechart.Rectangle r) {
        PointDouble[] result = new PointDouble[5];
        PointDouble topLeft = new PointDouble(r.getLeft(), r.getTop());
        PointDouble topRight = new PointDouble(r.getRight(), r.getTop());
        PointDouble bottomRight = new PointDouble(r.getRight(), r.getBottom());
        PointDouble bottomLeft = new PointDouble(r.getLeft(), r.getBottom());

        result[0] = topLeft;
        result[1] = topRight;
        result[2] = bottomRight;
        result[3] = bottomLeft;
        result[4] = topLeft;

        return result;
    }

    public static Class seriesClass(final String style) {

        if (style == null) {
            return null;
        } else {
            for (int t = 0; t < SERIESTYPESCOUNT; t++) {
                if (seriesTypesOf[t].getSimpleName().equalsIgnoreCase(style)) {
                    return seriesTypesOf[t];
                }
            }
        }

        return null;
    }

    public static double[] rationaliseValues(double x, double y, double z) {
        double tmpTotal = (x + y) + z;
        if (tmpTotal != 100f) {
            double percentCoef = 100.0 / tmpTotal;
            x *= percentCoef;
            y *= percentCoef;
            z *= percentCoef;
        }
        double[] values = new double[3];
        values[0] = x;
        values[1] = y;
        values[2] = z;
        return values;
    }

    public static double hypot(double a, double b) {
        return Math.sqrt(Math.pow(a, 2) + Math.pow(b, 2));
    }
}
