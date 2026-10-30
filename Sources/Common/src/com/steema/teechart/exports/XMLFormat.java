package com.steema.teechart.exports;

import com.steema.teechart.IBaseChart;
import com.steema.teechart.languages.Language;

import com.steema.teechart.styles.ValueList;
import com.steema.teechart.styles.Series;
import com.steema.teechart.misc.StringBuilder;

/**
 *
 * <p>Title: XMLFormat class</p>
 *
 * <p>Description: TeeChart for Java</p>
 *
 * <p>Copyright (c) 2004-2019 by Steema Software SL. All Rights Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 *
 * @author not attributable
 * @version 1.1
 */

/// <summary>
/// Chart data export to XML
/// </summary>
public class XMLFormat extends DataExportFormat {

    public XMLFormat(IBaseChart c) {
        super(c);
        setFileExtension("xml");
    }

    public String getFilterFiles() {
        return Language.getString("XMLFilter");
    }

    private String get(ValueList aList, int index) {
        return " " + aList.getName() + "=\"" + Double.toString(aList.getValue(index)) +
                "\"";
    }

// Not used.
    protected String pointToString(int index) {
        return "";
    }

    private String getPointString(int index, Series aSeries) {

        StringBuilder tmpResult = new StringBuilder(1);

        tmpResult.append((getIncludeIndex()) ?
                         "index=\"" + Integer.toString(index) + "\"" :
                         "");

        // the point Label text, if exists
        if (hasLabels) {
            tmpResult.append(" text=\"" + aSeries.getLabels().getString(index) +
                             "\"");
        }

        // the "X" point value, if exists
        if (hasNoMandatory) {
            tmpResult.append(get(aSeries.getNotMandatory(), index));
        }

        // the "Y" point value
        tmpResult.append(get(aSeries.getMandatory(), index));

        // write the rest of values (always)
        for (int tt = 2; tt < aSeries.getValuesLists().size(); tt++) {
            tmpResult.append(get(aSeries.getValueList(tt), index));
        }

        return tmpResult.toString();
    }

    private String seriesPoints(Series aSeries) {
        StringBuilder tmpResult = new StringBuilder(1);

        if (aSeries.getCount() > 0) {
            for (int t = 0; t < aSeries.getCount(); t++) {
                tmpResult.append("<point " + getPointString(t, aSeries) + "/>" +
                                 getTextLineSeparator());
            }
        }

        return tmpResult.toString();
    }

    private String XMLSeries(Series aSeries) {
        return
                "<series title=\"" + aSeries.toString() + "\" type=\"" +
                aSeries.getClass().toString().substring(aSeries.getClass().toString().lastIndexOf(".")+1)+
                "\">" + getTextLineSeparator() +
                "<points count=\"" + Integer.toString(aSeries.getCount()) +
                "\">" +
                getTextLineSeparator() +
                seriesPoints(aSeries) +
                "</points>" + getTextLineSeparator() +
                "</series>" + getTextLineSeparator() + getTextLineSeparator();
    }

    protected String getContent() {
        prepare();

        StringBuilder tmpResult = new StringBuilder(1);

        //tmpResult="<?xml version=\"1.0\" ?>"+TextLineSeparator;

        if (series != null) {
            tmpResult.append(XMLSeries(series));
        } else {
            tmpResult.append("<chart>" + getTextLineSeparator());

            for (int t = 0; t < chart.getSeriesCount(); t++) {
                Series s = chart.getSeries(t);
                tmpResult.append(XMLSeries(s));
            }

            tmpResult.append("</chart>");
        }

        //String tmp = tmpResult.toString();
        return tmpResult.toString();
    }
}

