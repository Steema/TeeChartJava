package com.steema.teechart.functions;

import java.util.ArrayList;

import com.steema.teechart.styles.ValueListOrder;
import com.steema.teechart.styles.ValueList;
import com.steema.teechart.IBaseChart;
import com.steema.teechart.styles.Series;
import com.steema.teechart.misc.Utils;

/**
 *
 * <p>Title: BaseTrend class</p>
 *
 * <p>Description: Internal use. Base class for different trend functions.</p>
 *
 * <p>Copyright (c) 2005-2019 by Steema Software SL. All Rights Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 *
 */
public class BaseTrend extends Function {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private TrendStyle trendStyle = TrendStyle.NORMAL;

    transient private int count;
    transient private double sumX;
    transient private double sumXY;
    transient private double sumY;
    transient private double sumX2;
    transient private double sumY2;

    public BaseTrend() {
        this((IBaseChart)null);
    }

    public BaseTrend(IBaseChart c) {
        super(c);
    }

    /**
     * Method used for calculating trendline.<br>
     * Default value: TrendStyle.Normal
     *
     *
     * @return TrendStyle
     */
    public TrendStyle getTrendStyle() {
        return trendStyle;
    }

    /**
     * Defines different methods for calculating trendline.<br>
     * Default value: TrendStyle.Normal
     *
     *
     * @param value TrendStyle
     */
    public void setTrendStyle(TrendStyle value) {
        if (trendStyle != value) {
            trendStyle = value;
            recalculate();
        }
    }

    public double calculate(Series s, int firstIndex, int lastIndex) {
        return 0.0D;
    }

    public double calculateMany(ArrayList sourceSeriesList,
                                int valueIndex) {
        return 0.0D;
    }

    /**
     * Calculate all quantities, needed for trend algorithm
     *
     * @param s Series
     * @param firstIndex int
     * @param lastIndex int
     * @return boolean
     */
    private boolean calculateValues(Series s, int firstIndex, int lastIndex) {
        if (firstIndex == -1) {
            firstIndex = 0;
            lastIndex = s.getCount() - 1;
        }

        count = lastIndex - firstIndex + 1;

        boolean result = count > 1;
        if (result) {
            double x, y;
            boolean tmpAll = (trendStyle == TrendStyle.NORMAL &&
                              count == s.getCount());
            if (tmpAll) {
                sumX = s.getNotMandatory().getTotal();
                sumY = valueList(s).getTotal();
            } else {
                sumX = 0.0D;
                sumY = 0.0;
            }

            sumX2 = 0.0;
            sumY2 = 0.0;
            sumXY = 0.0;

            ValueList v = valueList(s);
            for (int i = firstIndex; i <= lastIndex; i++) {

                x = s.getNotMandatory().value[i];

                if (trendStyle == TrendStyle.NORMAL) {
                    y = v.value[i];
                } else if (v.value[i] > 0.0) {
                    y = Utils.log(v.value[i]);
                } else {
                    y = 0.0;
                }

                sumXY += x * y;
                sumX2 += x * x;
                sumY2 += y * y;

                if (!tmpAll) {
                    sumX += x;
                    sumY += y;
                }
            }
        }
        return result;
    }

    /**
     * Calculates trendline first order parameter
     *
     * @param s Series
     * @param firstIndex int
     * @param lastIndex int
     * @return double trendline parameter
     */
    protected double coefficient(Series s, int firstIndex, int lastIndex) {
        if (calculateValues(s, firstIndex, lastIndex)) {
            double tNum = sumXY - sumX * sumY / (double) count;
            double tDen = Math.sqrt((sumX2 - sumX * sumX / (double) count) *
                                    (sumY2 - sumY * sumY / (double) count));
            return (tDen == 0) ? 1.0 : tNum / tDen;
        } else {
            return 1.0;
        }
    }


    private void addPoint(double m, double b, double val, Series s) {
        double tmp = m * val + b;
        double vb = val * b;

        if (trendStyle == TrendStyle.LOGARITHMIC) {

            tmp = (vb > 0.0) ? m * Utils.log(vb) : 0.0;

        } else if (trendStyle == TrendStyle.EXPONENTIAL) {
            tmp = m * Utils.exp(vb);
        }

        if (s.getYMandatory()) {
            getSeries().add(val, tmp);
        } else {
            getSeries().add(tmp, val);
        }
    }

    private class CalcTrend {
        boolean result;
        double m, b;
    }

    protected void calculatePeriod(Series source, double tmpX,
                                   int firstIndex, int lastIndex) {
        CalcTrend tmp = calculateTrend(source, firstIndex, lastIndex);

        if (tmp.result) {
            ValueList v = source.getNotMandatory();
            if (v.getOrder() == ValueListOrder.NONE) {
                addPoint(tmp.m, tmp.b, v.getMinimum(), source);
                addPoint(tmp.m, tmp.b, v.getMaximum(), source);
            } else {
                addPoint(tmp.m, tmp.b, v.value[firstIndex], source);
                addPoint(tmp.m, tmp.b, v.value[lastIndex], source);
            }
        }
    }

    protected void calculateAllPoints(Series source,
                                      ValueList notMandatorySource) {
        calculatePeriod(source, 0.0, 0, source.getCount() - 1);
    }

    /**
     * Calculates trend line parameters m and b, where y=m*x+b
     *
     * @param s Series
     * @param firstIndex int
     * @param lastIndex int
     * @return CalcTrend
     */
    public CalcTrend calculateTrend(Series s, int firstIndex,
                                    int lastIndex) {

        CalcTrend result = new CalcTrend();
        result.result = calculateValues(s, firstIndex, lastIndex);
        if (result.result) {
            double divisor;
            if (trendStyle == TrendStyle.NORMAL) {
                divisor = count * sumX2 - sumX * sumX;
                if (divisor != 0.0) {
                    result.m = ((count * sumXY) - (sumX * sumY)) / divisor;
                    result.b = ((sumY * sumX2) - (sumX * sumXY)) / divisor;
                } else {
                    result.m = 1.0;
                    result.b = 0.0;
                }
            } else {
                sumX /= (double) count;
                sumY /= (double) count;
                divisor = (sumX2 - (count * sumX * sumX));
                result.b = (divisor != 0) ?
                           (sumXY - (count * sumX * sumY)) / divisor :
                           1.0;
                if (trendStyle == TrendStyle.LOGARITHMIC) {
                    result.m = sumY - result.b * sumX;
                } else {
                    result.m = Utils.exp(sumY - result.b * sumX);
                }
            }
        }
        return result;
    }
}

