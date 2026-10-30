package com.steema.teechart.styles;


import com.steema.teechart.languages.Language;
import com.steema.teechart.IBaseChart;
import com.steema.teechart.drawing.Color;

/**
 *
 * <p>Title: Bar3D class</p>
 *
 * <p>Description: Bar3D series.</p>
 *
 * <p>Example:
 * <pre><font face="Courier" size="4">
 * series1 = new Bar3D(myChart.getChart());
 * series1.add( 0, 250, 200, "A", Color.RED );
 * series1.add( 1,  10, 200, "B", Color.GREEN );
 * series1.add( 2,  90, 100, "C", Color.YELLOW );
 * series1.add( 3,  30,  50, "D", Color.BLUE );
 * series1.add( 4,  70, 150, "E", Color.WHITE );
 * series1.add( 5, 120, 150, "F", Color.SILVER );
 * series1.setColorEach(true);
 * series1.getMarks().setArrowLength(20);
 * series1.getMarks().setVisible(true);
 * series1.setBarStyle(BarStyle.RECTGRADIENT);
 * series1.setBarWidthPercent(90);
 * series1.getGradient().setDirection(GradientDirection.HORIZONTAL);
 * series1.getGradient().setStartColor(Color.YELLOW);
 * </font></pre></p>
 *
 * <p>Copyright (c) 2005-2019 by Steema Software SL. All Rights
 * Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 *
 */
public class Bar3D extends Bar {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ValueList offsetValues;

    public Bar3D() {
        this(null);
    }

    public Bar3D(IBaseChart c) {
        super(c);
        marks.getTailParams().setAlign(TailAlignment.Auto);
        offsetValues = new ValueList(this, Language.getString("ValuesOffset"));
    }

    protected void addSampleValues(int numValues) {
        SeriesRandom r = randomBounds(numValues);

        for (int t = 1; t <= numValues; t++) {
            r.tmpY = r.DifY * r.Random();
            add(r.tmpX, 10 + Math.abs(r.tmpY),
                Math.abs(r.DifY / (1 + 5 * r.Random())));
            r.tmpX += r.StepX;
        }
    }


    /**
     * Adds a bar with an X and Y value, Y start point and color.
     *
     * @param x double
     * @param y double
     * @param offset double
     * @param color Color
     * @return int
     */
    public int add(double x, double y, double offset, Color color) {
        return add(x, y, offset, "", color);
    }

    /**
     * Adds a bar with an X and Y value and Y start point.
     *
     * @param x double
     * @param y double
     * @param offset double
     * @return int
     */
    public int add(double x, double y, double offset) {
        return add(x, y, offset, "", Color.EMPTY);
    }

    /**
     * Adds a bar with an X and Y value with offset and label.
     *
     * @param x double
     * @param y double
     * @param offset double
     * @param text String
     * @return int
     */
    public int add(double x, double y, double offset, String text) {
        return add(x, y, offset, text, Color.EMPTY);
    }

    /**
     * Adds a bar with an X and Y value, Y start point, label and color.
     *
     * @param x double
     * @param y double
     * @param offset double
     * @param text String
     * @param color Color
     * @return int
     */
    public int add(double x, double y, double offset, String text, Color color) {
        offsetValues.tempValue = offset;
        return add(x, y, text, color);
    }

    public double getOriginValue(int valueIndex) {
        return super.getOriginValue(valueIndex) + offsetValues.value[valueIndex];
    }

    /**
     * Returns the Maximum Value of the Series Y Values List.
     *
     * @return double
     */
    public double getMaxYValue() {
        double result = super.getMaxYValue();
        if ((iMultiBar == MultiBars.NONE) || (iMultiBar == MultiBars.SIDE)) {
            result = Math.max(result, offsetValues.getMaximum());
        }
        return result;
    }

    /**
     * Returns the Minimum Value of the Series Y Values List.
     *
     * @return double
     */
    public double getMinYValue() {
        double result = super.getMinYValue();
        if ((iMultiBar == MultiBars.NONE) || (iMultiBar == MultiBars.SIDE)) {
            for (int t = 0; t < getCount(); t++) {
                if (offsetValues.value[t] < 0) {
                    result = Math.min(result, vyValues.value[t] + offsetValues.value[t]);
                }
            }
        }
        return result;
    }

    /**
     * Returns the corresponding screen pixels coordinate of the leftmost
     * horizontal bar edge.<br>
     * The UseOrigin property must be true (the default) to use the Origin
     * method. <br>
     * Bars with a value bigger than Origin are drawn in one direction and
     * Bars with a lower value are drawn in the opposite direction.<br>
     * This applies both to Bar series and HorizBar series classes. <br>
     * Default Value: 0
     *
     * @param valueIndex int
     * @param sumAll boolean
     * @return double
     */
    public double pointOrigin(int valueIndex, boolean sumAll) {
        return offsetValues.value[valueIndex];
    }

    /**
     * Specifies a different origin value for each bar point.<br>
     * This can be used with standard Bar series components to make a
     * "Stacked-3D" chart type.
     *
     * @return ValueList
     */
    public ValueList getOffsetValues() {
        return offsetValues;
    }

    /**
     * Specifies a different origin value for each bar point.<br>
     * This can be used with standard Bar series components to make a
     * "Stacked-3D" chart type.
     *
     * @param value ValueList
     */
    public void setOffsetValues(ValueList value) {
        setValueList(offsetValues, value);
    }

    /**
     * Gets descriptive text.
     *
     * @return String
     */
    public String getDescription() {
        return Language.getString("GalleryBar3D");
    }

    protected boolean subGalleryStack() {
        return false; // 5.01 Do not show stacked styles at sub-gallery
    }
}

