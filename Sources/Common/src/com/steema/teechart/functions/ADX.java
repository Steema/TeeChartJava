package com.steema.teechart.functions;

import com.steema.teechart.events.ChartDrawEvent;
import com.steema.teechart.languages.Language;

import java.util.ArrayList;
import com.steema.teechart.IBaseChart;
import com.steema.teechart.events.SeriesPaintAdapter;
import com.steema.teechart.styles.Series;
import com.steema.teechart.styles.FastLine;
import com.steema.teechart.styles.ISeries;
import com.steema.teechart.styles.OHLC;
import com.steema.teechart.styles.ValueList;
import com.steema.teechart.drawing.ChartPen;
import com.steema.teechart.drawing.Color;
import com.steema.teechart.misc.Utils;

/**
 *
 * <p>Title: ADX class</p>
 *
 * <p>Description: ADX Function.</p>
 *
 * <p>Copyright (c) 2005-2019 by Steema Software SL. All Rights
 * Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>

 *
 */
public class ADX extends Function {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private FastLine iDMDown;
    private FastLine iDMUp;

    private boolean interpolate = true;
    private int factor = 4;

    public ADX() {
        this((IBaseChart)null);
    }

    public ADX(IBaseChart c) {
        super(c);
        dPeriod = 14;
        SingleSource = true;
        HideSourceList = true;

        iDMDown = new FastLine();
        HideSeries(iDMDown);
        iDMDown.setColor(Color.RED);

        iDMUp = new FastLine();
        HideSeries(iDMUp);
        iDMUp.setColor(Color.GREEN);
    }

    /// Access characteristics of the DMDown line (a FastLineSeries).
    /// DMDown is a sub-series (of type FastLine) of ADX function.
    public FastLine getDMDown() {
        return iDMDown;
    }

    /// Access characteristics of the DMUp line (a FastLineSeries).
    /// DMUp is a sub-series (of type FastLine) of ADX function.
    public FastLine getDMUp() {
        return iDMUp;
    }

    /// The pen used to draw the upper ADX line.
    /// Corresponds to DMUp.Pen property.
    /// <code>
    /// aDX.getUpLinePen().setColor(Color.Blue);
    /// </code>
    public ChartPen getUpLinePen() {
        return iDMUp.getLinePen();
    }

    /// The pen used to draw the lower ADX line.
    /// Corresponds to DMDown.Pen property.
    public com.steema.teechart.drawing.ChartPen getDownLinePen() {
        return iDMDown.getLinePen();
    }

    public boolean isValidSourceOf(ISeries value) {
        return value instanceof OHLC;
    }

    private void prepareSeries(Series series) {
        series.setChart(this.getSeries().getChart());
        series.setCustomVertAxis(this.getSeries().getCustomVertAxis());
        series.setVerticalAxis(this.getSeries().getVerticalAxis());
        series.getXValues().setDateTime(this.getSeries().getXValues().
                                         getDateTime());

        series.addSeriesPaintListener( new SeriesPaintAdapter() {
            public void seriesPainting(ChartDrawEvent e) {
                ADX.this.getSeries().doBeforeDrawChart();
            };

            public void seriesPainted(ChartDrawEvent e) {
                ADX.this.getSeries().doBeforeDrawChart();
            };
        });
    }

    private double calcADX(int Index) {
        int tmpIndex = Index - Utils.round(getPeriod());
        double result = (100 *
                         Math.abs(iDMUp.getYValues().getValue(tmpIndex) -
                                  iDMDown.getYValues().getValue(tmpIndex)) /
                         (iDMUp.getYValues().getValue(tmpIndex) +
                          iDMDown.getYValues().getValue(tmpIndex)));
        return result;
    }

    ///Gets all points from Source series, performs a function operation and stores results in ParentSeries.
    public void addPoints(ArrayList source) {
        ValueList Closes, Highs, Lows;
        double[] tmpTR, tmpDMUp, tmpDMDown;
        double tmpClose;
        double tmpX;
        //	base.AddPoints (source);
        if (!updating) { // 5.02
            if (source != null) {
                if (source.size() > 0) {
                    this.getSeries().clear();
                    Series s = (Series) source.get(0);
                    if (s.getCount() > 0) {
                        if (getPeriod() < 2) {
                            return;
                        }

                        iDMUp.clear();
                        iDMDown.clear();
                        prepareSeries(iDMUp);
                        prepareSeries(iDMDown);

                        if (s.getCount() >= (2 * getPeriod())) {
                            Closes = ((OHLC) s).getCloseValues();
                            Highs = ((OHLC) s).getHighValues();
                            Lows = ((OHLC) s).getLowValues();

                            tmpTR = new double[s.getCount()];
                            tmpDMUp = new double[s.getCount()];
                            tmpDMDown = new double[s.getCount()];

                            for (int t = 1; t < s.getCount(); ++t) {
                                tmpClose = Closes.getValue(t - 1);
                                tmpTR[t] = Highs.getValue(t) - Lows.getValue(t);
                                tmpTR[t] = Math.max(tmpTR[t],
                                        Math.abs(Highs.getValue(t) - tmpClose));
                                tmpTR[t] = Math.max(tmpTR[t],
                                        Math.abs(Lows.getValue(t) - tmpClose));

                                if ((Highs.getValue(t) - Highs.getValue(t - 1)) >
                                    (Lows.getValue(t - 1) - Lows.getValue(t))) {
                                    tmpDMUp[t] = Math.max(0,
                                            Highs.getValue(t) -
                                            Highs.getValue(t - 1));
                                } else {
                                    tmpDMUp[t] = 0;
                                }

                                if ((Lows.value[t - 1] - Lows.value[t]) >
                                    (Highs.value[t] - Highs.value[t - 1])) {
                                    tmpDMDown[t] = Math.max(0,
                                            Lows.getValue(t - 1) -
                                            Lows.getValue(t));
                                } else {
                                    tmpDMDown[t] = 0;
                                }
                            }

                            double tmpTR2 = 0;
                            double tmpUp2 = 0;
                            double tmpDown2 = 0;

                            int tmp = Utils.round(getPeriod());

                            for (int t = tmp; t < s.getCount(); ++t) {
                                if (t == tmp) {
                                    for (int tt = 1;
                                                  tt <= Utils.round(getPeriod());
                                                  ++tt) {
                                        tmpTR2 = tmpTR2 + tmpTR[tt];
                                        tmpUp2 = tmpUp2 + tmpDMUp[tt];
                                        tmpDown2 = tmpDown2 + tmpDMDown[tt];
                                    }
                                } else {
                                    tmpTR2 = tmpTR2 - (tmpTR2 / getPeriod()) +
                                             tmpTR[t];
                                    tmpUp2 = tmpUp2 - (tmpUp2 / getPeriod()) +
                                             tmpDMUp[t];
                                    tmpDown2 = tmpDown2 - (tmpDown2 / getPeriod()) +
                                               tmpDMDown[t];
                                }
                                tmpX = s.getXValues().getValue(t);
                                iDMUp.add(tmpX, 100 * (tmpUp2 / tmpTR2));
                                iDMDown.add(tmpX, 100 * (tmpDown2 / tmpTR2));
                            }

                            tmpTR = null;
                            tmpDMUp = null;
                            tmpDMDown = null;
                            double tmpADX = 0;

                            tmp = Utils.round((2 * getPeriod()) - 2);

                            for (int t = tmp; t < s.getCount(); ++t) {
                                if (t == tmp) {
                                    tmpADX = 0;
                                    for (int tt = Utils.round(getPeriod());
                                                  tt <= tmp; ++tt) {
                                        tmpADX = tmpADX + calcADX(tt);
                                    }
                                    tmpADX = tmpADX / (getPeriod() - 1);
                                } else {
                                    tmpADX = ((tmpADX * (getPeriod() - 1)) +
                                              (calcADX(t))) / getPeriod();
                                }

                                getSeries().add(s.getXValues().getValue(t),
                                                tmpADX);
                            }
                        }
                    }
                }
            }
        }
    }

    private void HideSeries(Series ASeries) {
        ASeries.setShowInLegend(false);
        ASeries.InternalUse = true;
    }

    public void dispose() {
        this.iDMDown.dispose();
        this.iDMUp.dispose();
    }

    /**
     * Gets descriptive text.
     *
     * @return String
     */
    public String getDescription() {
        return Language.getString("FunctionADX");
    }
}

