package com.steema.teechart.exports;

import com.steema.teechart.languages.Language;
import com.steema.teechart.IBaseChart;
import com.steema.teechart.styles.Series;

/// <summary>
/// Chart data export to Text
/// </summary>
/**
 *
 * <p>Title: TeeChart for Java</p>
 *
 * <p>Description: Chart data export to Text</p>
 *
 * <p>Copyright (c) 2004-2019 by Steema Software SL. All Rights
 * Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 */
public class TextFormat extends DataExportFormat {

    public String textDelimiter = Language.getString("TabDelimiter");

    public TextFormat(IBaseChart c) {
        super(c);
        setFileExtension("txt");
    }

    protected String getContent() {
        prepare();
        String tmpResult="";

        tmpResult+=(getIncludeSeriesTitle() ?
                         (headerTitle() + getTextLineSeparator()) :
                         "");

        tmpResult+=(getIncludeHeader() ? (header() + getTextLineSeparator()) :
                         "");

        tmpResult+=(super.getContent() + getTextLineSeparator());

        return tmpResult;
    }

    public String getFilterFiles() {
        return Language.getString("TextFilter");
    }

    private String headerSeriesTitle(Series aSeries) {
        String tmpResult="";

        if (hasLabels) {
            tmpResult+=aSeries.toString() + textDelimiter;
        }

        if (hasNoMandatory && !hasLabels) {
            tmpResult+=aSeries.toString() + textDelimiter;
        }

        if (!hasLabels && !hasNoMandatory) {
            tmpResult+=aSeries.toString() + textDelimiter;
        } else {
            tmpResult+=textDelimiter;
        }

        for (int t = 2; t < aSeries.getValuesLists().size(); t++) {
            tmpResult+=textDelimiter;
        }

        return tmpResult;
    }


    private String headerSeries(Series aSeries) {
        String tmpResult = "";

        if (hasLabels) {
            tmpResult+=Language.getString("Text");
            tmpResult+=textDelimiter;
        }

        if (hasNoMandatory) {
            tmpResult+=aSeries.getNotMandatory().getName();
            tmpResult+=textDelimiter;
        }

        tmpResult+=aSeries.getMandatory().getName();

        for (int t = 2; t < aSeries.getValuesLists().size(); t++) {
            tmpResult+=(textDelimiter + aSeries.getValueList(t).getName());
        }

        return tmpResult;
    }

    private String headerTitle() {
        //CDI - each column should be headed by the name of the valuelist
        //new header line with Series.ToString() added.
        String tmpResult="";

        if (getIncludeIndex()) {
            tmpResult+=textDelimiter;
        }

        if (series != null) {
            tmpResult+=headerSeriesTitle(series);
        } else {
            for (int t = 0; t < chart.getSeriesCount(); t++) {
                tmpResult+=headerSeriesTitle(chart.getSeries(t));
            }
        }

        int length = tmpResult.length();
        tmpResult=tmpResult.substring(0,length-1);
        //tmpResult+=TextLineSeparator;

        return tmpResult;
    }

    private String header() {
        String tmpResult = "";

        tmpResult+=(getIncludeIndex() ? Language.getString("Index") : "");

        if (tmpResult.length() != 0) {
            tmpResult+=textDelimiter;
        }

        if (series != null) {
            tmpResult+=headerSeries(series);
        } else
        if (chart.getSeriesCount() > 0) {

            tmpResult+=headerSeries(chart.getSeries(0));

            for (int t = 1; t < chart.getSeriesCount(); t++) {
                tmpResult+=(textDelimiter + headerSeries(chart.getSeries(t)));
            }
        }

        return tmpResult;
    }

    protected String pointToString(int index) {
        String result = getIncludeIndex() ? Integer.toString(index) : "";

        // Export Series data
        if (series != null) {
            result+=textDelimiter+doSeries(index, series);
        } else {
            for (int t = 0; t < chart.getSeriesCount(); t++) {
                result+=textDelimiter+doSeries(index, chart.getSeries(t));
            }
        }

        if(result.startsWith(textDelimiter)) {
                 result = result.replaceFirst(textDelimiter, "");
        }
        return result;
    }

    private String add(final String st, final String result) {
        return result + textDelimiter + st;
    }

    private String doSeries(int index, Series aSeries) {

        String result="";
        //tmpNum++;

        /* the point Label text, if exists */
        if ( /*(tmpNum==1)&&*/(hasLabels)) { //CDI let's get the labels for all the series
            if (aSeries.getCount() > index) {
                result=add(aSeries.getLabels().getString(index), result);
            } else {
                result=add("", result);
            }
        }

        /* the "X" point value, if exists */
        //add(FloatToStr(ASeries.NotMandatoryValueList.Value[Index]));
        if (hasNoMandatory) {
            if (aSeries.getCount() > index) {
                result=add(Double.toString(aSeries.getNotMandatory().getValue(index)),
                    result);
            } else {
                result=add("", result);
            }
        }

        /* the "Y" point value */
        if (aSeries.getCount() > index) {
            result=add(Double.toString(aSeries.getMandatory().getValue(index)), result);
        } else {
            result=add("", result);
        }

        /* write the rest of values (always) */
        for (int tt = 2; tt < aSeries.getValuesLists().size(); tt++) {

            if (aSeries.getCount() > index) {
                result += textDelimiter +
                        Double.toString(aSeries.getValueList(tt).getValue(index));
            } else {
                result += textDelimiter + "";
            }
        }

        if(result.startsWith(textDelimiter)) {
            result = result.replaceFirst(textDelimiter, "");
        }
        return result;
    }
}

