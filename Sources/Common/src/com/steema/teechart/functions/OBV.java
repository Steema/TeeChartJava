package com.steema.teechart.functions;

import java.util.ArrayList;
import com.steema.teechart.languages.Language;


import com.steema.teechart.IBaseChart;
import com.steema.teechart.styles.ISeries;
import com.steema.teechart.styles.OHLC;

/**
 *
 * <p>Title: OBV class</p>
 *
 * <p>Description: OBV Function.</p>
 *
 * <p>Copyright (c) 2005-2019 by Steema Software SL. All Rights
 * Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 *
 */
public class OBV extends Function {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ISeries fVolume;

    public OBV() {
        this((IBaseChart)null);
    }

    public OBV(IBaseChart c) {
        super(c);
        setPeriod(1);
        canUsePeriod = false;
        SingleSource = true;
	HideSourceList = true;
    }

    /**
     * The Volume series by which OBV results are multiplied.
     *
     * @return ISeries
     */
    public ISeries getVolume() {
        return fVolume;
    }

    /**
     * Sets the Volume series by which OBV results are multiplied.
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
     * points and stores results in ParentSeries.
     *
     * @param source ArrayList
     */
    public  void addPoints(ArrayList source) {

        if (!updating) { // 5.02
            if (source != null) {
                if (source.size() > 0) {
                    ISeries s = (ISeries) source.get(0);
                    if (s.getCount() > 0 && s instanceof OHLC) {
                        getSeries().clear();

                        if (fVolume != null) {
                            for (int t = 0; t < s.getCount(); ++t) {
                                if (fVolume.getCount() > t) {
                                    double tmp = fVolume.getMandatory().value[t];
                                    if (((OHLC) s).getCloseValues().value[t] >
                                        ((OHLC) s).getOpenValues().value[t]) {
                                        if (t > 0) {
                                            tmp += getSeries().getMandatory().getLast();
                                        }
                                    } else {
                                        if (t > 0) {
                                            tmp = getSeries().getMandatory().getLast() - tmp;
                                        }
                                    }
                                    getSeries().add(((OHLC) s).getDateValues().value[t], tmp);
                                }
                            }
                        } else {
                            return;
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
        return Language.getString("FunctionOBV");
    }

}

