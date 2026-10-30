package com.steema.teechart.functions;

import com.steema.teechart.styles.Series;
import com.steema.teechart.languages.Language;
import com.steema.teechart.styles.ValueListOrder;
import com.steema.teechart.IBaseChart;
import java.util.ArrayList;
import com.steema.teechart.drawing.Color;
import com.steema.teechart.styles.ColorList;
import com.steema.teechart.misc.Utils;

/// <summary>
/// Reduces the number of points in series by using several different methods.
/// </summary>
public class DownSampling extends Function
{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private final class ReduceResults
    {

        int size;
        double[] x;
        double[] y;

        public ReduceResults(int size, double[] x, double[] y)
        {
            this.size = size;
            this.x = x;
            this.y = y;
        }
    }
    private double tolerance;
    private DownSamplingMethod method;
    //private int reducedsize;
    private ReduceResults r;
    private int reducedsize;

    public int reduce(DownSamplingMethod algorithm, Series s, double tol,
            int lbound,
            int ubound, double[] rx, double[] ry, ColorList colors, Color color)
    {


        int rsize = 0;
        int i = lbound;
        int j = i;
        int nullcount = 0;
        int itol = Utils.round(tol);

        double[] x = s.getXValues().getValues();
        double[] y = s.getYValues().getValues();
        double tmpsum = 0.0;
        double tmpmax = 0.0;
        double tmpmin = 0.0;
        double tmpfirst = 0.0;
        double tmplast = 0.0;
        double tmpXfirst = 0.0;
        double tmpXmax = 0.0;
        double tmpXmin = 0.0;
        double tmpXlast = 0.0;

        while (i < ubound)
        {
            j = i;
            if (algorithm == DownSamplingMethod.MINMAXFIRSTLASTNULL)
            {
                nullcount = 0;
                if (s.isNull(i))
                {
                    ++nullcount;
                }
            }
            tmpsum = y[i];
            tmpmax = y[i];
            tmpmin = y[i];
            tmpfirst = y[i];
            tmpXfirst = x[i];
            tmpXmax = x[i];
            tmpXmin = x[i];
            tmplast = tmpfirst;
            tmpXlast = tmpXfirst;

            if (getDisplayedPointCount() > 0)
            {
                int count = 1;
                while ((j + 1) < ubound && count < itol)
                {
                    j++;

                    if (algorithm == DownSamplingMethod.MINMAXFIRSTLASTNULL)
                    {
                        if (s.isNull(j))
                        {
                            ++nullcount;
                        }
                        if (nullcount <= 1)
                        {
                            if (!s.isNull(j))
                            {
                                if (tmpsum != s.getDefaultNullValue())
                                {
                                    tmpsum += y[j];
                                }
                                else
                                {
                                    tmpsum = y[j];
                                }

                                if (y[j] > tmpmax)
                                {
                                    tmpmax = y[j];
                                    tmpXmax = x[j];
                                }

                                if (y[j] < tmpmin)
                                {
                                    tmpmin = y[j];
                                    tmpXmin = x[j];
                                }

                                tmplast = y[j];
                                tmpXlast = x[j];
                            }
                            else
                            {
                                if (y[j] > tmpmax)
                                {
                                    tmpmax = y[j];
                                    tmpXmax = x[j];
                                }

                                if (y[j] < tmpmin)
                                {
                                    tmpmin = y[j];
                                    tmpXmin = x[j];
                                }

                                tmplast = y[j];
                                tmpXlast = x[j];
                            }
                        }
                    }
                    else
                    {
                        tmpsum += y[j];
                        if (y[j] > tmpmax)
                        {
                            tmpmax = y[j];
                            tmpXmax = x[j];
                        }
                        if (y[j] < tmpmin)
                        {
                            tmpmin = y[j];
                            tmpXmin = x[j];
                        }
                        tmplast = y[j];
                        tmpXlast = x[j];
                    }
                    ++count;
                }
            }
            else
            {
                while (((j + 1) < ubound) && (Math.abs(x[j + 1] - x[i]) < tol))
                {
                    j++;
                    tmpsum += y[j];
                    if (y[j] > tmpmax)
                    {
                        tmpmax = y[j];
                        tmpXmax = x[j];
                    }
                    if (y[j] < tmpmin)
                    {
                        tmpmin = y[j];
                        tmpXmin = x[j];
                    }
                    tmplast = y[j];
                    tmpXlast = x[j];
                }
            }

            if (algorithm != DownSamplingMethod.MINMAX && algorithm != DownSamplingMethod.MINMAXFIRSTLAST
                    && algorithm != DownSamplingMethod.MINMAXFIRSTLASTNULL)
            {
                rx[rsize] = (x[j] + x[i]) * 0.5; // x is average of group
                if (algorithm == DownSamplingMethod.AVERAGE)
                {
                    ry[rsize] = tmpsum / (double) (j - i + 1);
                }
                else if (algorithm == DownSamplingMethod.MAX)
                {
                    ry[rsize] = tmpmax;
                }
                else if (algorithm == DownSamplingMethod.MIN)
                {
                    ry[rsize] = tmpmin;
                }
                rsize++;
            }
            else if (rsize <= rx.length) // safeguard in case somebody tries to do this on very short array
            {
                if (((j - i) == 0)) //CDI only drawing half the plot when tolerance = 0
                {
                    rx[rsize] = x[i];
                    ry[rsize] = tmpmin;
                    //#if !NULLABLE
                    if (algorithm == DownSamplingMethod.MINMAXFIRSTLASTNULL)
                    {
                        if (tmpmin == s.getDefaultNullValue())
                        {
                            colors.add(Color.TRANSPARENT);
                        }
                        else
                        {
                            colors.add(color);
                        }
                    }
                    //#endif
                    rsize += 1;
                }
                else if ((algorithm == DownSamplingMethod.MINMAX))
                {
                    rx[rsize] = x[i];
                    rx[rsize + 1] = x[j];
                    ry[rsize] = tmpmin;
                    ry[rsize + 1] = tmpmax;
                    rsize += 2;
                }
                else if ((j - i) > 2)
                {
                    rx[rsize] = tmpXfirst;
                    ry[rsize] = tmpfirst;

                    if (algorithm == DownSamplingMethod.MINMAXFIRSTLASTNULL)
                    {
                        if (tmpfirst == s.getDefaultNullValue())
                        {
                            colors.add(Color.TRANSPARENT);
                        }
                        else
                        {
                            colors.add(color);
                        }
                        ++rsize;
                        if (tmpXmax <= tmpXmin)
                        {
                            if (tmpXfirst != tmpXmax)
                            {
                                rx[rsize] = tmpXmax;
                                ry[rsize] = tmpmax;
                                if (tmpmax == s.getDefaultNullValue())
                                {
                                    colors.add(Color.TRANSPARENT);
                                }
                                else
                                {
                                    colors.add(color);
                                }
                                ++rsize;
                            }
                            if (tmpXfirst != tmpXmin && tmpXmin != tmpXmax)
                            {
                                rx[rsize] = tmpXmin;
                                ry[rsize] = tmpmin;
                                if (tmpmin == s.getDefaultNullValue())
                                {
                                    colors.add(Color.TRANSPARENT);
                                }
                                else
                                {
                                    colors.add(color);
                                }
                                ++rsize;
                            }
                        }
                        else
                        {
                            if (tmpXfirst != tmpXmin)
                            {
                                rx[rsize] = tmpXmin;
                                ry[rsize] = tmpmin;
                                if (tmpmin == s.getDefaultNullValue())
                                {
                                    colors.add(Color.TRANSPARENT);
                                }
                                else
                                {
                                    colors.add(color);
                                }
                                ++rsize;
                            }
                            if (tmpXfirst != tmpXmax && tmpXmin != tmpXmax)
                            {
                                rx[rsize] = tmpXmax;
                                ry[rsize] = tmpmax;
                                if (tmpmax == s.getDefaultNullValue())
                                {
                                    colors.add(Color.TRANSPARENT);
                                }
                                else
                                {
                                    colors.add(color);
                                }
                                ++rsize;
                            }
                        }
                        if (tmpXlast != tmpXfirst && tmpXlast != tmpXmin && tmpXlast != tmpXmax)
                        {
                            rx[rsize] = tmpXlast;
                            ry[rsize] = tmplast;
                            if (tmplast == s.getDefaultNullValue())
                            {
                                colors.add(Color.TRANSPARENT);
                            }
                            else
                            {
                                colors.add(color);
                            }
                            ++rsize;
                        }
                    }
                    else
                    {
                        if (tmpXmax <= tmpXmin)
                        {
                            rx[rsize + 1] = tmpXmax;
                            rx[rsize + 2] = tmpXmin;
                            ry[rsize + 1] = tmpmax;
                            ry[rsize + 2] = tmpmin;
                        }
                        else
                        {
                            rx[rsize + 2] = tmpXmax;
                            rx[rsize + 1] = tmpXmin;
                            ry[rsize + 2] = tmpmax;
                            ry[rsize + 1] = tmpmin;
                        }

                        rx[rsize + 3] = tmpXlast;
                        ry[rsize + 3] = tmplast;
                        rsize += 4;
                    }
                }
                else if ((j - i) < 2)
                {
                    rx[rsize] = x[i];
                    rx[rsize + 1] = x[j];
                    ry[rsize] = y[i];
                    ry[rsize + 1] = y[j];
                    rsize += 2;
                }
                else
                {
                    double one = 0, two = 0, three = 0;
                    double oneX = 0, twoX = 0, threeX = 0;

                    if (tmpfirst == tmpmin || tmplast == tmpmin)
                    {
                        one = tmpfirst;
                        oneX = tmpXfirst;
                        two = tmpmax;
                        twoX = tmpXmax;
                        three = tmplast;
                        threeX = tmpXlast;
                    }
                    else if (tmpfirst == tmpmax || tmplast == tmpmax)
                    {
                        one = tmpfirst;
                        oneX = tmpXfirst;
                        two = tmpmin;
                        twoX = tmpXmin;
                        three = tmplast;
                        threeX = tmpXlast;
                    }

                    rx[rsize] = oneX;
                    rx[rsize + 1] = twoX;
                    rx[rsize + 2] = threeX;
                    ry[rsize] = one;
                    ry[rsize + 1] = two;
                    ry[rsize + 2] = three;
                    rsize += 3;
                }
            }
            i = j + 1;

        }
        return rsize;

    }

    /*private void addValuesFrom(Series source) {

    int sourceCount = source.getCount();
    for (int t = 0; t < sourceCount; t++) {
    addedValue(source, t);
    }
    }*/
    /// <summary>
    /// Returns reduced size
    /// </summary>
    public int getReducedSize()
    {
        return r.size;
    }

    public DownSampling()
    {
        this((IBaseChart) null);
    }

    public DownSampling(IBaseChart c)
    {
        super(c);
        canUsePeriod = false;
        SingleSource = true;
        dPeriod = 1;
        tolerance = 1.0;
        method = DownSamplingMethod.AVERAGE;
    }
    private int displayedPointCount = 0;

    public int getDisplayedPointCount()
    {
        return displayedPointCount;
    }

    public void setDisplayedPointCount(int value)
    {

        displayedPointCount = value;
    }

    /// <summary>
    /// Gets or sets the tolerance for downsampling method. All points within Tolerance will be replaced with one or two points.
    /// </summary>
    public double getTolerance()
    {
        return tolerance;
    }

    public void setTolerance(double value)
    {
        if (tolerance != value)
        {
            tolerance = Math.max(0.0, value);
            recalculate();
        }
    }

    //#if DESIGNATTRIBUTES
    //[Description("Defines reduction/downsampling method.")]
    //#endif
    public DownSamplingMethod getMethod()
    {
        return method;
    }

    public void setMethod(DownSamplingMethod value)
    {
        if (method != value)
        {
            method = value;
            recalculate();
        }
    }

    /// <summary>
    /// Gets all points from Source series, performs a function operation on points and stores results in ParentSeries.
    /// </summary>
    public void addPoints(ArrayList source)
    {
        {
            if (!updating)
            {
                if (source != null)
                {
                    if (source.size() > 0)
                    {
                        Series s = (Series) source.get(0); // single series only!
                        getSeries().clear();
                        int n = s.getCount();
                        int lbound = 0;

                        if (chart != null && (chart.getChartRect().width != 0))
                        {
                            chart.image(chart.getChartRect().width, chart.getChartRect().height);
                            s.calcFirstLastVisibleIndex();
                            n = s.getLastVisible() - s.getFirstVisible();
                            lbound = s.getFirstVisible();
                        }
                        if (n > 0)
                        {
                            if (s.getYMandatory() == getSeries().getYMandatory())
                            {
                                getSeries().getNotMandatory().setOrder(
                                        ValueListOrder.ASCENDING);
                                getSeries().getMandatory().setOrder(ValueListOrder.NONE);
                                // calc visible true
                            }
                            else
                            {
                                getSeries().getNotMandatory().setOrder(
                                        ValueListOrder.NONE);
                                getSeries().getMandatory().setOrder(ValueListOrder.ASCENDING);
                                // calc visible false
                            }

                            ColorList colors = new ColorList(n);
                            double[] rx = new double[n];
                            double[] ry = new double[n];
                            double tmpTol = tolerance;

                            if (getDisplayedPointCount() > 0)
                            {
                                tmpTol = getDisplayedPointCount() / 4.0;
                                tmpTol = n / tmpTol;
                            }

                            reducedsize = reduce(method, s, tmpTol, lbound, lbound + n,
                                    rx,
                                    ry,
                                    colors, series.getColor());

                            getSeries().getNotMandatory().count = reducedsize;
                            getSeries().getMandatory().count = reducedsize;

                            if (s.getYMandatory())
                            {
                                getSeries().getNotMandatory().value = rx;
                                getSeries().getMandatory().value = ry;
                            }
                            else
                            {
                                getSeries().getNotMandatory().value = ry;
                                getSeries().getMandatory().value = rx;
                            }
                            getSeries().setColors(colors);
                        }
                    }
                }
            }
        }
    }

    /**
     * Gets descriptive text.
     *
     * @return String
     */
    public String getDescription()
    {
        return Language.getString("FunctionDownSampling");
    }
}
