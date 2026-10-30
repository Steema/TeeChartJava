package com.steema.teechart.functions;

import java.util.ArrayList;
import com.steema.teechart.languages.Language;

import com.steema.teechart.DateTime;
import com.steema.teechart.styles.ValueList;
import com.steema.teechart.styles.OHLC;
import com.steema.teechart.IBaseChart;
import com.steema.teechart.styles.ISeries;
import com.steema.teechart.styles.Series;
import com.steema.teechart.misc.Utils;

/**
 *
 * <p>Title: CompressOHLC class</p>
 *
 * <p>Description: CompressOHLC Function.</p>
 *
 * <p>Copyright (c) 2005-2019 by Steema Software SL. All Rights
 * Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 *
 */
public class CompressOHLC extends Function {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private DateCompressor dateCompressor;
    private CompressionPeriod fCompress;

    public CompressOHLC() {
        this((IBaseChart)null);
    }

    public CompressOHLC(IBaseChart c) {
        super(c);
        canUsePeriod = false;
        SingleSource = true;
	HideSourceList = true;
        fCompress = CompressionPeriod.WEEK;
    }

    /**
     * The Compression Period as defined by the CompressionPeriod
     * enumeration.<br>
     *
     * @return CompressionPeriod
     */
    public CompressionPeriod getCompress() {
        return fCompress;
    }

    /**
     * Sets the Compression Period as defined by the CompressionPeriod
     * enumeration.<br>
     *
     * @param value CompressionPeriod
     */
    public void setCompress(CompressionPeriod value) {
        if (fCompress != value) {
            fCompress = value;
        }
        recalculate();
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
                    Series s = (Series) source.get(0);
                    if (s.getCount() > 0 && getSeries() instanceof OHLC) {
                        compressSeries(s, (OHLC) getSeries(), null, null);
                    }
                }
            }
        }
        //super.AddPoints (source);
    }

    public DateCompressor getDateCompressor() {
        return dateCompressor;
    }

    public void setDateCompressor(DateCompressor value) {
        dateCompressor=value;
    }

    private void compressSeries(Series source, OHLC destOHLC, Series volume,
                                Series destVolume) {
        int tmp, year, month, day;
        DateTime tmpDate;
        double tmpValue;

        destOHLC.clear();
        if (destVolume != null) {
            destVolume.clear();
        }

        int oldDay = 0;
        int tmpDay = 0;

        ValueList tmpList = valueList(source);
        int tmpPeriod = (int) getPeriod();

        for (int t = 0; t < source.getCount(); ++t) {

            tmpDate = new DateTime(source.getNotMandatory().value[t]);

            boolean doIt=true;

            if (tmpPeriod == 0) {
                if (dateCompressor != null) {
                    tmpDate = dateCompressor.compress(source, t);
                }
                day = tmpDate.getDay();
                month = tmpDate.getMonth();
                year = tmpDate.getYear();

                CompressionPeriod tmpCompress=getCompress();
                if (tmpCompress==CompressionPeriod.DAY) {
                    tmpDay = Utils.round(tmpDate.toDouble());
                }
                else
                if (tmpCompress==CompressionPeriod.WEEK) {
                    tmpDay = tmpDate.getDayOfWeek();
                    if (tmpDay == 0) {
                        tmpDay = 7;
                    }
                }
                else
                if (tmpCompress==CompressionPeriod.MONTH) {
                    tmpDay = month;
            }
            else

                if (tmpCompress==CompressionPeriod.BIMONTH) {
                    tmpDay = (month - 1) / 2;
                }
                else
                if (tmpCompress==CompressionPeriod.QUARTER) {
                    tmpDay = (month - 1) / 3;
                }
                else
                if (tmpCompress==CompressionPeriod.YEAR) {
                    tmpDay = year;
                }
                else
                if (tmpCompress== CompressionPeriod.WEEK) {
                    doIt = tmpDay < oldDay;
                } else {
                    doIt = tmpDay != oldDay;
                }

                oldDay = tmpDay;
            } else {
                doIt = (t % tmpPeriod) == 0;
            }

            if ((t == 0) || doIt) {
                if (source instanceof OHLC) {
                    tmp = destOHLC.add(tmpDate, ((OHLC) source).getOpenValues().value[t],
                                       ((OHLC) source).getHighValues().value[t],
                                       ((OHLC) source).getLowValues().value[t],
                                       ((OHLC) source).getCloseValues().value[t]);
                } else {
                    tmpValue = tmpList.value[t];
                    tmp = destOHLC.add(tmpDate, tmpValue, tmpValue,
                                       tmpValue, tmpValue);
                }

                destOHLC.getLabels().setString(tmp, source.getLabels().getString(t));

                if (destVolume != null) {
                    destVolume.add(source.getNotMandatory().value[t], volume.getYValues().value[t]);
                }
            } else {
                tmp = destOHLC.getCount() - 1;

                if (source instanceof OHLC) {
                    destOHLC.getCloseValues().value[tmp] = ((OHLC) source).getCloseValues().value[t];
                    destOHLC.getDateValues().value[tmp] = ((OHLC) source).getDateValues().value[t];
                    if (((OHLC) source).getHighValues().value[t] > destOHLC.getHighValues().value[tmp]) {
                        destOHLC.getHighValues().value[tmp] = ((OHLC) source).getHighValues().value[t];
                    }
                    if (((OHLC) source).getLowValues().value[t] <
                        destOHLC.getLowValues().value[tmp]) {
                        destOHLC.getLowValues().value[tmp] = ((OHLC) source).getLowValues().value[t];
                    }
                } else {
                    tmpValue = tmpList.value[t];
                    destOHLC.getCloseValues().value[tmp] = tmpValue;
                    destOHLC.getDateValues().value[tmp] = source.getNotMandatory().value[t];
                    if (tmpValue > destOHLC.getHighValues().value[tmp]) {
                        destOHLC.getHighValues().value[tmp] = tmpValue;
                    }
                    if (tmpValue < destOHLC.getLowValues().value[tmp]) {
                        destOHLC.getLowValues().value[tmp] = tmpValue;
                    }
                }

                destOHLC.getLabels().setString(tmp,source.getLabels().getString(t));

                if (destVolume != null) {
                    destVolume.getYValues().value[tmp] = destVolume.getYValues().value[tmp] +
                                              volume.getYValues().value[t];
                    destVolume.getXValues().value[tmp] = volume.getXValues().value[t];
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
        return Language.getString("FunctionCompressOHLC");
    }

    public interface DateCompressor {
        public DateTime compress(ISeries series, int valueIndex);
    }
}


