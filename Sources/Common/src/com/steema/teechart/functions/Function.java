package com.steema.teechart.functions;

import java.util.ArrayList;
import com.steema.teechart.languages.Language;

import com.steema.teechart.styles.ValueList;
import com.steema.teechart.TeeBase;

import com.steema.teechart.DateTimeStep;
import com.steema.teechart.IBaseChart;
import com.steema.teechart.styles.Series;
import com.steema.teechart.misc.Utils;

/**
 *
 * <p>Title: Function class</p>
 *
 * <p>Description: Basic abstract function class.
 * Examples of derived functions are: Add, Subtract, High, Low, Average and
 * Count.</p>
 *
 * <p>Copyright (c) 2005-2019 by Steema Software SL. All Rights Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 *
 */
public class Function extends TeeBase {
	
	private static final long serialVersionUID = 1L;

    protected double dPeriod;

    transient public boolean noSourceRequired; // For "Custom" function

    private PeriodStyle periodStyle = PeriodStyle.NUMPOINTS;
    private PeriodAlign periodAlign = PeriodAlign.CENTER;

    transient protected Series series;

    transient protected boolean updating;

    transient protected boolean canUsePeriod;
    transient public boolean SingleSource;
    transient public boolean HideSourceList;

    public Function() {
        this(null);
        initFields();
    }

    private void initFields() {
        canUsePeriod = true;
    }

    protected Object readResolve() {
        initFields();
        return this;
    }

    public Function(IBaseChart c) {
        super(c);
    }
    
    public void dispose() {
        this.series.setFunction(null);
    }

    public static Function newInstance(Class f) throws IllegalAccessException,
            InstantiationException {
        return (Function) f.newInstance();
    }

    /**
     * Controls how many points or X range will trigger a new point
     * calculation.<br>
     * Zero means all source points.<br>
     * For example, Average function uses the Period property to calculate a
     * new average point each time the "Period" number of points or X range is
     * exceed. <br><br>
     * <b>NOTE:</b> You may switch between number of points or X range by using
     * the Function PeriodStyle property. <br>
     * Default value: 0D
     *
     * @return double
     */
    public double getPeriod() {
        return dPeriod;
    }

    public void setPeriod(double value) {
        /** @todo ELIMINATE EXCEPTION HERE ! */
//        if (value < 0) {
//            throw new TeeChartException(Language.getString("FunctionPeriod"));
//        }
        if (dPeriod != value) {
            dPeriod = value;
            recalculate();
        }
    }

    /**
     * Returns the Series parent of Function.<br>
     * Run-time and read only. <br>
     * The Series property returns the Series parent of this Function.<br>
     * TChart uses Series to do the actual drawing.
     *
     * @return Series
     */
    public Series getSeries() {
        return series;
    }

    public void setSeries(Series value) {
        if (series != value) {

            if (series != null)
              series.setFunction(null);
            series = value;
            if (value != null)
              value.setFunction(this);
        }
    }

    /**
     * Controls how the Period property is interpreted.<br>
     * Either as number of points or as range.<br>
     * Range means Period property is specified in a range of values. <br>
     * Being able to define Period as a range can be very useful when using
     * Date-Time series and when you want to express the  Period  of the
     * function in a date-time step like  OneMonth  or  OneDay.<br>
     * So, for example you can now plot the monthly average of sales function
     * just using a normal Average function on a date-time source series and
     * setting the function period to one month :<br>
     * { Place a series1 and fill it with datetime data values at runtime
     * (or from a database) } <br>
     * series2.setFunction( new Average() ); <br>
     * series2.getFunction().setPeriodStyle( Range); series2.getFunction().setPeriod(
     * DateTimeStep[ dtOneMonth ]); series2.setDataSource(series1);<br><br>
     * This will result in several points, each one showing the  average of
     * each month of data in Series1. <br>
     * It's mandatory that points in the source Series1 should be sorted by
     * date when calculating functions on datetime periods. <br><br>
     * The range can also be used for non-datetime series: <br><br>
     * series2.setFunction( new Average() ) ; <br>
     * series2.getFunction().setPeriodStyle(Range); <br>
     * series2.getFunction().setPeriod(100); <br>
     * series2.setDataSource(series1) ; <br>>br>
     * This will calculate an average for each group of points inside every
     * 100 interval. <br><br>
     * (Points with X >=0, X<100 will be used to calculate the first average,
     * points with X >=100, X<200 will be used to calculate the second average
     * and so on... ) <br>
     * Notice this is different than calculating an average for every 100
     * points. <br>
     * Default value: PeriodStyle.NumPoints
     *
     *
     * @return PeriodStyle
     */
    public PeriodStyle getPeriodStyle() {
        return periodStyle;
    }

    public void setPeriodStyle(PeriodStyle value) {
        if (periodStyle != value) {
            periodStyle = value;
            recalculate();
        }
    }

    /**
     * Controls where to place function calculations inside the full period
     * space.<br>
     * The position of calculation output points within range.<br>
     * When the function Period is greater than zero (so it calculates by
     * groups of points), the function results are added to the series by
     * default at the center position of the Function Period. You can change
     * this by setting PeriodAlign to  <br><br>
     * - First (function result will be added to series at start of each
     * period),  <br>
     * - Center (function result will be added to series at center of each
     * period) <br>
     * - Last (function result will be added to series at end of each period)
     * <br><br>
     * Example <br><br>
     * <code>function1.setPeriodAlign(Center);
     * // <-- by default is centered</code> <br>
     * The  First  and  Last  constants will plot calculations at the start and
     * end  X  coordinates of each  Period .<br>
     * Default value: PeriodAlign.Centre
     *
     *
     * @return PeriodAlign
     */
    public PeriodAlign getPeriodAlign() {
        return periodAlign;
    }

    public void setPeriodAlign(PeriodAlign value) {
        if (periodAlign != value) {
            periodAlign = value;
            recalculate();
        }
    }

    protected void addFunctionXY(boolean yMandatorySource, double tmpX,
                                 double tmpY) {
        if (yMandatorySource) {
            series.add(tmpX, tmpY);
        } else {
            series.add(tmpY, tmpX);
        }
    }

    // MS : added to allow overrides in curve fitting functions
    protected void calculatePeriod(Series source, double tmpX,
                                   int firstIndex, int lastIndex) {
        addFunctionXY(source.getYMandatory(), tmpX,
                      calculate(source, firstIndex, lastIndex));
    }

    // abstract
    /**
     * Performs function operation on SourceSeries series.<br>
     * First and Last parameters are ValueIndex of first and last point used
     * in calculation. <br>
     * You can override Calculate function to perform customized calculation
     * on one SourceSeries.
     *
     * @param source Series
     * @param first int
     * @param last int
     * @return double
     */
    public double calculate(Series source, int first, int last) {
        return 0;
    }

    // abstract
    /**
     * Performs function operation on list of series (SourceSeriesList).<br>
     * The ValueIndex parameter defines ValueIndex of point in each Series in
     * list. <br>
     * You can override CalculateMany function to perform customized
     * calculation on list of SourceSeries.
     *
     * @param sourceSeries ArrayList
     * @param valueIndex int
     * @return double
     */
    public double calculateMany(ArrayList sourceSeries, int valueIndex) {
        return 0;
    }

    // MS : added to allow overrides in curve fitting functions
    protected void calculateAllPoints(
            Series source,
            ValueList notMandatorySource) {
        double tmpX;
        double tmpY = calculate(source, -1, -1);

        if (!series.getAllowSinglePoint()) {
            tmpX = notMandatorySource.getMinimum();
            addFunctionXY(source.getYMandatory(), tmpX, tmpY);
            tmpX = notMandatorySource.getMaximum();
            addFunctionXY(source.getYMandatory(), tmpX, tmpY);
        } else
        /* centered point */
        if ((!source.getYMandatory()) && series.getYMandatory()) {
            tmpX = notMandatorySource.getMinimum() +
                   0.5 * notMandatorySource.getRange();
            series.add(tmpX, tmpY);
        } else {
            tmpX = notMandatorySource.getMinimum() +
                   0.5 * notMandatorySource.getRange();
            if (series.getYMandatory()) {
                addFunctionXY(source.getYMandatory(), tmpX, tmpY);
            } else {
                series.add(tmpY, tmpX);
            }
        }
    }

    protected void calculateByPeriod(Series source,
                                     ValueList notMandatorySource) {
        double tmpX;
        int tmpLast;
        double posFirst;
        double posLast;
        boolean tmpCalc;

        int tmpFirst = 0;
        int tmpCount = source.getCount();
        double tmpBreakPeriod = notMandatorySource.value[tmpFirst];
        int tmpStep = DateTimeStep.find(dPeriod);

        do {
            posLast = 0;
            if (periodStyle == PeriodStyle.NUMPOINTS) {
                tmpLast = tmpFirst + Utils.round(dPeriod) - 1;
                posFirst = notMandatorySource.value[tmpFirst];

                if (tmpLast < tmpCount) {
                    posLast = notMandatorySource.value[tmpLast];
                }
            } else {
                tmpLast = tmpFirst;
                posFirst = tmpBreakPeriod;

                /** @todo FINISH !! */
//                tmpBreakPeriod = series.horizAxis().IncDecDateTime(true,
//                        tmpBreakPeriod, dPeriod, tmpStep);


                posLast = tmpBreakPeriod - (dPeriod * 0.001);

                while (tmpLast < tmpCount - 1) {
                    if (notMandatorySource.value[tmpLast + 1] < tmpBreakPeriod) {
                        tmpLast++;
                    } else {
                        break;
                    }
                }
            }
            tmpCalc = false;

            if (tmpLast < tmpCount) {

                /* align periods */
                if (periodAlign == PeriodAlign.FIRST) {
                    tmpX = posFirst;
                } else
                if (periodAlign == PeriodAlign.LAST) {
                    tmpX = posLast;
                } else {
                    tmpX = (posFirst + posLast) * 0.5;
                }

                if ((periodStyle == PeriodStyle.RANGE) &&
                    (notMandatorySource.value[tmpFirst] < tmpBreakPeriod)) {
                    tmpCalc = true;
                }

                if ((periodStyle == PeriodStyle.NUMPOINTS) || tmpCalc) {
                    calculatePeriod(source, tmpX, tmpFirst, tmpLast);
                } else {
                    addFunctionXY(source.getYMandatory(), tmpX, 0);
                }
            }
            if ((periodStyle == PeriodStyle.NUMPOINTS) || tmpCalc) {
                tmpFirst = tmpLast + 1;
            }
        } while (tmpFirst <= tmpCount - 1);
    }

    /**
     * Gets descriptive text.
     *
     * @return String
     */
    public String getDescription() {
        return Language.getString("GalleryFunctions");
    }

    protected void doCalculation(Series source,
                                 ValueList notMandatorySource) {
        if (dPeriod == 0) {
            calculateAllPoints(source, notMandatorySource);
        } else {
            calculateByPeriod(source, notMandatorySource);
        }
    }

    protected ValueList valueList(Series s) {
        String tmp = (series != null) ? series.getMandatory().getDataMember() :
                     "";
        if (tmp.length() == 0) {
            return s.getMandatory();
        } else {
            return s.getValueList(tmp);
        }
    }

    private void calculateFunctionMany(ArrayList source) {
        double tmpX;
        double tmpY;

        Series s = (Series) source.get(0);

        ValueList xList = s.getNotMandatory();

        // Find datasource with bigger number of points... 5.02
        for (int t = 0; t < source.size(); t++) {
            Object o = source.get(t);
            if ((o != null)
                    && (((Series) o).getCount() > s.getCount())) {
                s = (Series) (o);
                xList = s.getNotMandatory();
            }
        }
        // use source to calculate points...
        if (xList != null) {
            ArrayList tmpList = new ArrayList();

            for (int tt = 0; tt < source.size(); tt++) {
                tmpList.add(source.get(tt));
            }

            for (int tt = 0; tt < s.getCount(); tt++) {
                tmpX = xList.value[tt];
                tmpY = calculateMany(tmpList, tt);

                if (!s.getYMandatory()) { // swap
                    double tmp = tmpX;
                    tmpX = tmpY;
                    tmpY = tmp;
                }

                series.add(tmpX, tmpY, s.getLabels().getString(tt));
            }
        }
    }

    /**
     * Gets all points from Source series, performs a function operation and
     * stores results in ParentSeries.<br>
     *
     * @param source ArrayList
     */
    public void addPoints(ArrayList source) {
        if (!updating) { // 5.02
            if (source != null) {
                if (source.size() > 1) {
                    calculateFunctionMany(source);
                } else {
                    if (source.size() > 0) {
                        Series s = (Series) source.get(0);
                        if (s.getCount() > 0) {
                            doCalculation(s, s.getNotMandatory());
                        }
                    }
                }
            }
        }
    }

    private void beginUpdate() {
        updating = true;
    }

    private void endUpdate() {
        if (updating) {
            updating = false;
            recalculate();
        }
    }

    /**
     * Performs a checkDataSource method call on parent Series.<br>
     * Basically, all points in parent Series are recalculated.<br>
     * The recalculating is performed only if internal updating flag is set to
     * false.<br>
     * To make sure updating is set to false, you can call Function
     * endUpdate() method prior to calling Function recalculate.
     */
    public void recalculate() {
        if ((!updating) && (series != null)) {
            series.checkDataSource();
        }
    }
    
    public void clear(){
        /** Does nothing, used only by Bollinger and Stochastic **/
    }
}

