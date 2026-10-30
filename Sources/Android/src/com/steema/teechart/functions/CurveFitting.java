package com.steema.teechart.functions;

import java.util.ArrayList;
import com.steema.teechart.languages.Language;


import com.steema.teechart.styles.ValueList;
import com.steema.teechart.IBaseChart;
import com.steema.teechart.styles.Series;

/**
 *
 * <p>Title: CurveFitting class</p>
 *
 * <p>Description: Base class for fitting function(s)</p>
 *
 * <p>Copyright (c) 2005-2019-2008 by Steema Software SL. All Rights Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 *
 */
public class CurveFitting extends Function {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int ipolyDegree = 5;
    private int factor = 1;
    private int firstCalcPoint = -1;
    private int lastCalcPoint = -1;
    private int firstPoint = -1;
    private int lastPoint = -1;

    transient private Polynom p;
    transient private double iminyval = 0.0;

    public CurveFitting() {
        this((IBaseChart)null);
    }

    public CurveFitting(IBaseChart c) {
        super(c);
        initFields();
    }

    private void initFields() {
        p = new Polynom();
    }

    protected Object readResolve() {
        initFields();
        return super.readResolve();
    }

    /**
     * Defines fitted polynomial degree.<br>
     * Default value: 5
     *
     * @return int
     */
    public int getPolyDegree() {
        return ipolyDegree;
    }

    /**
     * Defines fitted polynomial degree.<br>
     * Default value: 5
     *
     * @param value int
     */
    public void setPolyDegree(int value) {
        if (value != ipolyDegree) {
            ipolyDegree = Math.min(value, Polynom.maxDegree);
            if (p != null) {
                p.setPolyDegree(ipolyDegree);
            }
            recalculate();
        }
    }

    /**
     * Multiplying factor.<br>
     * Default value: 1
     *
     * @return int
     */
    public int getFactor() {
        return factor;
    }

    /**
     * Multiplying factor.<br>
     * Default value: 1
     *
     * @param value int
     */
    public void setFactor(int value) {
        factor = setIntegerProperty(factor, value);
    }

    /**
     * First point to be used in fitting.<br>
     * Default value: -1
     *
     * @return int
     */
    public int getFirstCalcPoint() {
        return firstCalcPoint;
    }

    /**
     * First point to be used in fitting.<br>
     * Default value: -1
     *
     * @param value int
     */
    public void setFirstCalcPoint(int value) {
        firstCalcPoint = setIntegerProperty(firstCalcPoint, value);
    }

    /**
     * Last point to be used in fitting.<br>
     * Default value: -1
     *
     * @return int
     */
    public int getLastCalcPoint() {
        return lastCalcPoint;
    }

    /**
     * Last point to be used in fitting.<br>
     * Default value: -1
     *
     * @param value int
     */
    public void setLastCalcPoint(int value) {
        lastCalcPoint = setIntegerProperty(lastCalcPoint, value);
    }

    /**
     * First point to be used in evaluating.<br>
     * Default value: -1
     *
     * @return int
     */
    public int getFirstPoint() {
        return firstPoint;
    }

    /**
     * First point to be used in evaluating.<br>
     * Default value: -1
     *
     * @param value int
     */
    public void setFirstPoint(int value) {
        firstPoint = setIntegerProperty(firstPoint, value);
    }

    /**
     * Last point to be used in evaluating.<br>
     * Default value: -1
     *
     * @return int
     */
    public int getLastPoint() {
        return lastPoint;
    }

    /**
     * Last point to be used in evaluating.<br>
     * Default value: -1
     *
     * @param value int
     */
    public void setLastPoint(int value) {
        lastPoint = setIntegerProperty(lastPoint, value);
    }

    /**
     * Returns calculated fitted coefficient.
     *
     * @param index int
     * @return double
     */
    public double coefficient(int index) {
        return ((index >= 0) && (index < Polynom.maxDegree)) ?
                p.polyCoeff[index] : 0.0;
    }

    private void addFittedPoints(Series src) {
        iminyval = valueList(src).getMinimum();
        double minxval = src.getXValues().getMinimum();
        int start = 0;
        if (firstCalcPoint != -1) {
            start = Math.max(0, firstCalcPoint);
        }
        int end = src.getCount() - 1;
        if (lastCalcPoint != -1) {
            end = Math.min(src.getCount() - 1, lastCalcPoint);
        }
        double tmpx, tmpstep, tmpx2;

        for (int t = start; t < end; t++) { // 1 to 1 relationship between source and self
            tmpx = src.getXValues().value[t];
            tmpstep = (src.getXValues().value[t + 1] - tmpx) / (double) factor;
            for (int tt = 0; tt < factor; tt++) {
                tmpx2 = tmpx + tmpstep * tt;
                getSeries().add(tmpx2, p.polyEval(tmpx2 - minxval) + iminyval);
            }
        }
        tmpx2 = src.getXValues().value[end];
        getSeries().add(tmpx2, p.polyEval(tmpx2 - minxval) + iminyval);
    }

    /**
     * Evaluate function using calculated coefficients.
     *
     * @param s Series
     * @param x double value to be evaluated
     * @return double using calculated polynomial coefficients
     */
    public double getCurveYValue(Series s, double x) {
        return p.polyEval(x - s.getXValues().getMinimum()) + iminyval;
    }

    public void addPoints(ArrayList source) {
        if (!updating) {
            if (source != null) {
                if (source.size() > 0) {
                    Series src = (Series) source.get(0);
                    if (src.getCount() > 0) {
                        getSeries().clear();
                        ValueList list = valueList(src);
                        double minxval = src.getXValues().getMinimum();
                        iminyval = list.getMinimum();

                        int start = 0;
                        if (firstCalcPoint != -1) {
                            start = Math.max(0, firstCalcPoint);
                        }
                        int end = src.getCount() - 1;
                        if (lastCalcPoint != -1) {
                            end = Math.min(src.getCount() - 1, lastCalcPoint);
                        }
                        int count = end - start + 1;
                        if (count > 0) {
                            double[] xvec = new double[count];
                            double[] yvec = new double[count];
                            try {
                                int pos;
                                for (int t = 0; t < count; t++) {
                                    pos = t + start;
                                    xvec[t] = src.getXValues().value[pos] -
                                              minxval;
                                    yvec[t] = list.value[pos] - iminyval;
                                }
                                p.polyFit(count, xvec, yvec);
                                addFittedPoints(src);
                            } finally {
                                xvec = null;
                                yvec = null;
                            }
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
    public String getDescription() {
        return Language.getString("FunctionCurveFitting");
    }
}

