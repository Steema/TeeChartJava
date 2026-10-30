package com.steema.teechart.functions;

import java.util.ArrayList;
import com.steema.teechart.languages.Language;

import com.steema.teechart.styles.Line;
import com.steema.teechart.IBaseChart;
import com.steema.teechart.styles.ISeries;

/**
 *
 * <p>Title: PVO class</p>
 *
 * <p>Description: PVO Function.</p>
 *
 * <p>Copyright (c) 2005-2019 by Steema Software SL. All Rights
 * Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 *
 */
public class PVO extends Function {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ExpMovAverage fExpMovAve1;
    private ExpMovAverage fExpMovAve2;
    private Line fExpSeries1;
    private Line fExpSeries2;
    private boolean fPercent;

    public PVO() {
        this((IBaseChart)null);
    }

    public PVO(IBaseChart c) {
        super(c);

        setPeriod(12);
        SingleSource = true;
	HideSourceList = true;
        canUsePeriod = false;

        fPercent = true;

        fExpSeries1 = new Line();
        fExpSeries2 = new Line();

        fExpMovAve1 = new ExpMovAverage();
        fExpMovAve1.setPeriod(getPeriod());
        fExpMovAve2 = new ExpMovAverage();
        fExpMovAve2.setPeriod(26);
        fExpSeries1.setFunction(fExpMovAve1);
        fExpSeries2.setFunction(fExpMovAve2);
    }

    /**
     * Controls if the difference of two moving averages will be percentual or
     * not.
     *
     * @return boolean
     */
    public boolean getPercentage() {
        return fPercent;
    }

    /**
     * Controls if the difference of two moving averages will be percentual or
     * not.
     *
     * @param value boolean
     */
    public void setPercentage(boolean value) {
        if (fPercent != value) {
            fPercent = value;
        }
        recalculate();
    }

    /**
     * The period of the second exponential moving average.<br>
     * Default value: 26
     *
     * @return int
     */
    public int getPeriod2() {
        return (int) fExpMovAve2.getPeriod();
    }

    /**
     * Defines the period of the second exponential moving average.<br>
     * Default value: 26
     *
     * @param value int
     */
    public void setPeriod2(int value) {
        fExpMovAve2.setPeriod(value);
        recalculate();
    }


    /**
     * Gets all points from Source series, performs a function operation on
     * points and stores results in ParentSeries.<br>
     *
     * @param source ArrayList
     */
    public void addPoints(ArrayList source) {
        int t;
        double tmp;

        if (!updating) { // 5.02
            if (source != null) {
                if (source.size() > 0 && getPeriod() > 0) {
                    getSeries().clear();
                    fExpMovAve1.setPeriod(getPeriod());
                    fExpMovAve1.addPoints(source);
                    fExpMovAve2.addPoints(source);

                    ISeries s = (ISeries) source.get(0);

                    for (t = 0; t < s.getCount(); ++t) {
                        tmp = fExpSeries1.getMandatory().value[t];

                        if (fPercent) {
                            if (tmp != 0) {
                                tmp = (tmp - fExpSeries2.getMandatory().value[t]) / tmp;
                            } else {
                                tmp = fExpSeries2.getMandatory().value[t];
                            }
                        } else {
                            tmp -= fExpSeries2.getMandatory().value[t];
                        }

                        getSeries().add(s.getNotMandatory().value[t], 100 * tmp);
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
        return Language.getString("FunctionPVO");
    }
}

