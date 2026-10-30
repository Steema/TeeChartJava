package com.steema.teechart.functions;

import java.util.ArrayList;
import com.steema.teechart.languages.Language;


import com.steema.teechart.styles.OHLC;
import com.steema.teechart.IBaseChart;
import com.steema.teechart.styles.ISeries;

/**
 *
 * <p>Title: CLV class</p>
 *
 * <p>Description: CLV Function.</p>
 *
 * <p>Copyright (c) 2005-2019 by Steema Software SL. All Rights
 * Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 *
 */
public class CLV extends Function {
    private ISeries fVolume;
    private boolean fAccumulate;

    public CLV() {
        this((IBaseChart)null);
    }

    public CLV(IBaseChart c) {
        super(c);
        setPeriod(1);
        canUsePeriod = false;
        fAccumulate = true;
        SingleSource = true;
	HideSourceList = true;
    }

    /**
     * Accumulates the CLV Function results.
     *
     * @return boolean
     */
    public boolean getAccumulate() {
        return fAccumulate;
    }

    /**
     * Accumulates the CLV Function results.
     *
     * @param value boolean
     */
    public void setAccumulate(boolean value) {
        if (fAccumulate != value) {
            fAccumulate = value;
            recalculate();
        }
    }

    /**
     * The Volume series by which CLV results are multiplied.
     *
     * @return ISeries
     */
    public ISeries getVolume() {
        return fVolume;
    }

    /**
     * Sets the Volume series by which CLV results are multiplied.
     *
     * @param value ISeries
     */
    public void setVolume(ISeries value) {
        if (fVolume != value) {
            fVolume = value;
            recalculate();
        }
    }

    /**
     * Gets all points from Source series, performs a function operation on
     * points and stores results in ParentSeries.<br>
     *
     * @param source ArrayList
     */
    public  void addPoints(ArrayList source) {
        int t;
        double tmpHigh, tmpClose, tmpLow, clv;
        boolean tmpVolume = fVolume != null;

        if (!updating) { // 5.02
            if (source != null) {
                if (source.size() > 0) {
                    ISeries s = (ISeries) source.get(0);
                    if (s.getCount() > 0 && s instanceof OHLC) {
                        getSeries().clear();

                        for (t = 0; t < s.getCount(); ++t) {
                            tmpHigh = ((OHLC) s).getHighValues().value[t];
                            tmpClose = ((OHLC) s).getCloseValues().value[t];
                            tmpLow = ((OHLC) s).getLowValues().value[t];
                            clv = ((tmpClose - tmpLow) - (tmpHigh - tmpClose)) /
                                  (tmpHigh - tmpLow);

                            if (tmpVolume && fVolume.getCount() > t) {
                                clv *= fVolume.getMandatory().value[t];
                            }

                            if (getAccumulate()) {
                                if (t > 0) {
                                    clv += getSeries().getMandatory().getLast();
                                }
                            }

                            getSeries().add(((OHLC) s).getDateValues().value[t], clv);

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
    public  String getDescription() {
        return Language.getString("FunctionCLV");
    }
}

