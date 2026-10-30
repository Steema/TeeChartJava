package com.steema.teechart.styles;


import com.steema.teechart.languages.Language;
import com.steema.teechart.drawing.Color;
import com.steema.teechart.drawing.IGraphics3D;
import com.steema.teechart.drawing.ChartBrush;
import com.steema.teechart.drawing.Gradient;
import com.steema.teechart.drawing.ChartPen;
import com.steema.teechart.IBaseChart;
import com.steema.teechart.editors.gallery.Gallery;

/**
 *
 * <p>Title: Area class</p>
 *
 * <p>Description: Area Series</p>
 *
 * <p>Copyright (c) 2005-2019 by Steema Software SL. All Rights Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 *
 * @see com.steema.teechart.TChart
 *
 */
public class Area extends Custom {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private boolean useOrigin;
    private double origin;
    private int stackgroup = 0;

    public Area(IBaseChart c) {
        super(c);

        drawArea = true;
        allowSinglePoint=false;
        getPointer().setDefaultVisible(false);

        pAreaLines = new ChartPen(chart, Color.BLACK);
        bAreaBrush = new ChartBrush(chart, Color.EMPTY);
    }

    public Area() {
        this((IBaseChart)null);
    }

    /**
     * Determines how Multi-AreaSeries are displayed.<br>
     * Determines the kind of displayed Area when there's more than one Area
     * Series with the same ParentChart. The default value is None, meaning all
     * Areas will be drawn one behind the other. <br>
     * Stacked and Stacked100 modes will draw each Area on top of previous one.
     * <br>
     * Stacked100 adjusts each individual point to a common 0..100 axis scale.
     * <br>
     * The order which Series are accumulated depends on the Chart.SeriesList
     * method.<br>
     * Default value: None
     *
     * @see #setMultiArea
     *
     * @return MultiAreas
     */
    public MultiAreas getMultiArea() {
        if (iStacked == CustomStack.STACK) {
            return MultiAreas.STACKED;
        } else
        if (iStacked == CustomStack.STACK100) {
            return MultiAreas.STACKED100;
        } else {
            return MultiAreas.NONE;
        }
    }

    /**
     * Sets how multiple areas are displayed.
     *
     * @see #getMultiArea
     * @param value MultiAreas
     */
    public void setMultiArea(MultiAreas value) {
        if (value != getMultiArea()) {
            if (value == MultiAreas.NONE) {
                setStacked( CustomStack.NONE);
            } else
            if (value == MultiAreas.STACKED) {
                setStacked( CustomStack.STACK);
            } else
            if (value == MultiAreas.STACKED100) {
                setStacked( CustomStack.STACK100);
            }
        }
    }

    /**
     * Determines the Brush used to fill the background Area region.<br>
     * You can control the Area background color by using the Series.Color
     * method. <br>
     * Default value: Solid
     *
     * @return ChartBrush
     */
    public ChartBrush getAreaBrush() {
        return bAreaBrush;
    }

    /**
     *Determines how to fill the top 3D Area region.
     *
     * @return Gradient
     */
    public Gradient getTopGradient() {
        return bBrush.getGradient();
    }

    /**
     * Determines Gradient to fill the background Area region.
     * <p>Example:
     * <pre><font face="Courier" size="4">
     * areaSeries = new Area(myChart.getChart());
     * areaSeries.getMarks().setVisible(false);
     * areaSeries.setColor(Color.RED);
     * areaSeries.fillSampleValues(10);
     * areaSeries.setTransparency(0);
     * areaSeries.setStacked(CustomStack.NONE);
     *
     * com.steema.teechart.drawing.Gradient tmpGradient = areaSeries.getGradient();
     * tmpGradient.setVisible(true);
     * tmpGradient.setUseMiddle(true);
     * tmpGradient.setDirection(GradientDirection.HORIZONTAL);
     * tmpGradient.setStartColor(Color.RED);
     * tmpGradient.setMiddleColor(Color.BLUE);
     * tmpGradient.setEndColor(Color.GREEN);
     * tmpGradient.setTransparency(0);
     * </font></pre></p>
     *
     * @return Gradient
     */
    public Gradient getGradient() {
        return getAreaBrush().getGradient();
    }

    /**
     * Obsolete.&nbsp;Please use AreaLines instead.
     * @return ChartPen
     */
    public ChartPen getAreaLinesPen() {
        return getAreaLines();
    }

    /**
     * Determines Pen to draw AreaLines.<br>
     * By default AreaLines .Visible is false, so you need first to set it to
     * true. You can control the Area Brush style by using AreaBrush.<br>
     * Default value: null
     *
     * @return ChartPen
     */
    public ChartPen getAreaLines() {
        return pAreaLines;
    }

    /**
     * Aligns bottom of AreaSeries to the Origin property value.<br>
     * Default value: false
     *
     * @return boolean
     */
    public boolean getUseOrigin() {
        return useOrigin;
    }

    /**
     * Enables/disables the setting of the Y value (via the Origin
     * property) that defines the bottom position for area points.
     * Default value: false
     *
     * <p>Example:
     * <pre><font face="Courier" size="4">
     * areaSeries = new Area(myChart.getChart());
     * areaSeries.getMarks().setVisible(false);
     * areaSeries.fillSampleValues(20);
     * areaSeries.setStacked(CustomStack.NONE);
     * areaSeries.setUseOrigin(false);
     * areaSeries.setOrigin(200);
     * </font></pre></p>
     *
     * @see #getOrigin
     * @param value boolean
     */
    public void setUseOrigin(boolean value) {
        useOrigin = setBooleanProperty(useOrigin, value);
    }

    /**
     * The axis value as a common bottom for all AreaSeries points.<br>
     * Default value: O
     *
     * @return double
     */
    public double getOrigin() {
        return origin;
    }

    /**
     * Sets axis value as a common bottom for all AreaSeries points.<br>
     * Default value: O
     *
     * <p>Example:
     * <pre><font face="Courier" size="4">
     * areaSeries = new Area(myChart.getChart());
     * areaSeries.getMarks().setVisible(false);
     * areaSeries.fillSampleValues(20);
     * areaSeries.setStacked(CustomStack.NONE);
     * areaSeries.setUseOrigin(false);
     * areaSeries.setOrigin(200);
     * </font></pre></p>
     *
     * @param value double
     */
    public void setOrigin(double value) {
        origin = setDoubleProperty(origin, value);
    }
    
    public int getStackGroup() {
        return stackgroup;
    }
    
    public void setStackGroup(int value) {
        stackgroup = setIntegerProperty(stackgroup, value);
    }

    public void calcZOrder() {        
        if (getMultiArea() == MultiAreas.NONE) super.calcZOrder();
        else {
            if (chart != null){
                int tmpZOrder = -1;
                // Copy ZOrder from previous active Area series with same StackGroup...
                for (int i=0; i<chart.getSeriesCount(); i++) {
                    Series s = chart.getSeries(i);
                    if (s.getActive()) {
                        if (s == this) break;
                        else
                            if (sameClassOrigin(s)) {
                                tmpZOrder = s.getZOrder();
                                break;
                            }
                    }
                }

                if (tmpZOrder == -1) {
                    CustomStack old = iStacked;
                    iStacked = CustomStack.NONE;
                    try {
                        super.calcZOrder();
                    }
                    finally {
                        iStacked = old;
                    }
                }
                else
                    iZOrder = tmpZOrder;
            }
        }
    }

    protected boolean sameClassOrigin(Series s) {        
        boolean result = super.sameClassOrigin(s);
        
        if (s instanceof Area) {
            result = result & (((Area)s).getStackGroup() == stackgroup);
        }
        
        return result;
    }    
    
    /**
     * Returns the highest of all the current Series Y point values.
     *
     * @return double
     */
    public double getMaxYValue() {
        double result = super.getMaxYValue();
        if (yMandatory && useOrigin && (result < origin)) {
            result = origin;
        }
        return result;
    }

    /**
     * Returns the Minimum Value of the Series Y Values List.<br>
     * As some Series have more than one Y Values List, this Minimum Value is
     * the "Minimum of Minimums" of all Series Y Values lists.
     *
     * @return double
     */
    public double getMinYValue() {
        double result = super.getMinYValue();
        if (yMandatory && useOrigin && (result > origin)) {
            result = origin;
        }
        return result;
    }

    /**
     * The Maximum Value of the Series X Values List.
     *
     * @return double
     */
    public double getMaxXValue() {
        double result = super.getMaxXValue();
        if ((!yMandatory) && useOrigin && (result < origin)) {
            result = origin;
        }
        return result;
    }

    /**
     * The Minimum Value of the Series X Values List.
     *
     * @return double
     */
    public double getMinXValue() {
        double result = super.getMinXValue();
        if ((!yMandatory) && useOrigin && (result > origin)) {
            result = origin;
        }
        return result;
    }

    /**
     * Gets descriptive text.
     *
     * @return String
     */
    public String getDescription() {
        return Language.getString("GalleryArea");
    }

    protected int getOriginPos(int valueIndex) {
        if (useOrigin) {
            return calcPosValue(origin);
        } else {
            return super.getOriginPos(valueIndex);
        }
    }

    protected void prepareLegendCanvas(IGraphics3D g, int valueIndex,
                                       Color backColor,
                                       ChartBrush aBrush) {
        backColor = getAreaBrushColor(g.getBrush().getForegroundColor());
        g.setPen(getAreaLines());
        aBrush = getAreaBrush();
    }

    public void createSubGallery(Gallery addSubChart) {
        super.createSubGallery(addSubChart);
        addSubChart.createSubChart(Language.getString("Stairs"));
        addSubChart.createSubChart(Language.getString("Marks"));
        addSubChart.createSubChart(Language.getString("Colors"));
        addSubChart.createSubChart(Language.getString("Hollow"));
        addSubChart.createSubChart(Language.getString("NoLines"));
        //  AType.NumGallerySeries:=2;
        addSubChart.createSubChart(Language.getString("Stack"));
        addSubChart.createSubChart(Language.getString("Stack"));
        //  AType.NumGallerySeries:=1;
        addSubChart.createSubChart(Language.getString("Points"));
        addSubChart.createSubChart(Language.getString("Gradient"));
    }

    public void setSubGallery(int index) {
        switch (index) {
        case 1:
            setStairs(true);
            break;
        case 2:
            getMarks().setVisible(true);
            break;
        case 3:
            setColorEach(true);
            break;
        case 4:
            getAreaBrush().setVisible(false);
            break;
        case 5:
            getAreaLines().setVisible(false);
            break;
        case 6:
            setMultiArea(MultiAreas.STACKED);
            break;
        case 7:
            setMultiArea(MultiAreas.STACKED100);
            break;
        case 8:
            getPointer().setVisible(true);
            break;
        case 9:
            getGradient().setVisible(true);
            break;
        default: break;
        }
    }
}

