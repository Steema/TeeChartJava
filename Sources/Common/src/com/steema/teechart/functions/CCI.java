package com.steema.teechart.functions;

import java.util.ArrayList;
import com.steema.teechart.languages.Language;

import com.steema.teechart.IBaseChart;
import com.steema.teechart.styles.ISeries;
import com.steema.teechart.styles.Line;
import com.steema.teechart.styles.OHLC;
import com.steema.teechart.misc.Utils;

/**
 *
 * <p>Title: CCI class</p>
 *
 * <p>Description: CCI Function.</p>
 *
 * <p>Copyright (c) 2005-2019 by Steema Software SL. All Rights
 * Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 *
 */
public class CCI extends Function {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private double constant;
    private Line aveSeries;
    private MovingAverage fMovAve;
    private Line fTypical;
    private int tmpPeriod;
    private static final double THIRD = 1 / 3.0;

    public CCI() {
        this((IBaseChart)null);
    }

    public CCI(IBaseChart c) {
        super(c);
        SingleSource = true;
	HideSourceList = true;

        setPeriod(20);
        canUsePeriod = false;
        constant = 0.015;

        fTypical = new Line();
        fMovAve = new MovingAverage();

        aveSeries = new Line();
        aveSeries.setFunction(fMovAve);
    }

    /**
     * Value by which to multiply mean deviation.<br>
     * Default value: 0.015
     *
     * @return double
     */
    public double getConstant() {
        return constant;
    }

    /**
     * Specifies Value by which to multiply mean deviation.<br>
     * Default value: 0.015
     *
     * @param value double
     */
    public void setConstant(double value) {
        if (constant != value) {
            constant = value;
            recalculate();
        }
    }

    private double meanDeviation(int index) {
        double tmpTotal = 0;
        for (int t = index; t >= index - tmpPeriod; --t) {
            tmpTotal +=
                    Math.abs(aveSeries.getMandatory().value[index - tmpPeriod] -
                             fTypical.getMandatory().value[t]);
        }
        return tmpTotal / tmpPeriod;
    }

    private void calculateMovAve(ISeries source) {
        fTypical.clear();
        fMovAve.setPeriod(tmpPeriod);
        fTypical.beginUpdate();

        for (int t = 0; t < source.getCount(); ++t) {
            fTypical.add((((OHLC) source).getHighValues().value[t] +
                          ((OHLC) source).getLowValues().value[t] +
                          ((OHLC) source).getCloseValues().value[t]) * THIRD);
        }

        fTypical.endUpdate();

        for (int t = tmpPeriod; t < fTypical.getCount(); ++t) {
            aveSeries.add(fMovAve.calculate(fTypical, t - tmpPeriod, t));
        }
    }

    /**
     * Gets all points from Source series, performs a function operation on
     * points and stores results in ParentSeries.<br>
     *
     * @param source ArrayList
     */
    public void addPoints(ArrayList source) {

        if (!updating) { // 5.02
            if (source != null) {
                if (source.size() > 0) {
                    ISeries s = (ISeries) source.get(0);
                    if (s.getCount() > 0 && s instanceof OHLC) {

                        getSeries().clear();
                        tmpPeriod = Utils.round(getPeriod());

                        if (tmpPeriod > 0) {
                            calculateMovAve(s);
                            double tmpConstant = getConstant();

                            if (tmpConstant == 0) {
                                tmpConstant = 1;
                            }

                            for (int t = tmpPeriod; t < s.getCount(); ++t) {
                                double tmp = (fTypical.getMandatory().value[t] -
                                              aveSeries.getMandatory().value[t-tmpPeriod+1]) /
                                             (tmpConstant * meanDeviation(t));
                                getSeries().add(s.getNotMandatory().value[t],
                                                tmp);
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
        return Language.getString("FunctionCCI");
    }
}

