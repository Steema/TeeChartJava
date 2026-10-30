package com.steema.teechart.exports;


import com.steema.teechart.languages.Language;
import com.steema.teechart.styles.Series;
import com.steema.teechart.IBaseChart;
import com.steema.teechart.misc.StringBuilder;

/**
 *
 * <p>Title: TeeChart for Java</p>
 *
 * <p>Description: Chart data export to HTML table</p>
 *
 * <p>Copyright (c) 2004-2019 by Steema Software SL. All Rights
 * Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 *
 */
public class HTMLFormat extends DataExportFormat {

    private String emptyCell = "<td></td>";

    public HTMLFormat(IBaseChart c) {
        super(c);
        setFileExtension("htm");
    }

    protected String getContent() {
        prepare();

        StringBuilder tmpResult = new StringBuilder(1);

        tmpResult.append("<table border=\"1\">" + getTextLineSeparator());

        if (getIncludeSeriesTitle()) {
            tmpResult.append(titleHeader() + getTextLineSeparator());
        }

        if (getIncludeHeader()) {
            tmpResult.append(header() + getTextLineSeparator());
        }

        // tmpResult+=base.GetContent()+TextLineSeparator+"</table>";
        tmpResult.append(super.getContent() + "</table>"); //CDI TextLineSeparator was causing a square to
        //appear in the Excel files viewed on a Japanese OS

        return tmpResult.toString();
    }

    public String getFilterFiles() {
        return Language.getString("HTMLFilter");
    }

    private String headerSeriesTitle(Series aSeries) {
        StringBuilder tmpResult = new StringBuilder(1);

        if (hasLabels) {
            tmpResult.append("<td>" + aSeries.toString() + "</td>");
        }

        if (hasNoMandatory && !hasLabels) {
            tmpResult.append("<td>" + aSeries.toString() + "</td>");
        }

        if (!hasLabels && !hasNoMandatory) {
            tmpResult.append("<td>" + aSeries.toString() + "</td>");
        } else {
            tmpResult.append("<td></td>");
        }

        for (int t = 2; t < aSeries.getValuesLists().size(); t++) {
            tmpResult.append("<td></td>");
        }

        return tmpResult.toString();
    }

    private String headerSeries(Series aSeries) {
        StringBuilder tmpResult = new StringBuilder(1);

        if (hasLabels) {
            tmpResult.append("<td>" + Language.getString("Text") + "</td>");
        }

        if (hasNoMandatory) {
            tmpResult.append("<td>" + aSeries.getNotMandatory().getName() + "</td>");
        }

        tmpResult.append("<td>" + aSeries.getMandatory().getName() + "</td>");

        for (int t = 2; t < aSeries.getValuesLists().size(); t++) {
            tmpResult.append("<td>" + aSeries.getValueList(t).getName() + "</td>");
        }

        return tmpResult.toString();
    }

    private String titleHeader() {
        StringBuilder tmpResult = new StringBuilder("<tr>");

        if (getIncludeIndex()) {
            tmpResult.append("<td></td>");
        }

        if (series != null) {
            tmpResult.append(headerSeriesTitle(series));
        }

        else {
            for (int t = 0; t < chart.getSeriesCount(); t++) {
                tmpResult.append(headerSeriesTitle(chart.getSeries(t)));
            }
        }

        tmpResult.append("</tr>");

        return tmpResult.toString();
    }

    private String header() {
        StringBuilder tmpResult = new StringBuilder("<tr>");

        //CDI - each column should be headed by the name of the valuelist
        //new header line with Series.ToString() added.

        if (getIncludeIndex()) {
            tmpResult.append("<td>" + Language.getString("Index") + "</td>");
        }

        if (series != null) {
            tmpResult.append(headerSeries(series));
        } else {
            for (int t = 0; t < chart.getSeriesCount(); t++) {
                tmpResult.append(headerSeries(chart.getSeries(t)));
            }
        }

        tmpResult.append("</tr>");

        return tmpResult.toString();
    }

    private String cellDouble(double value) {
        return "<td>" + Double.toString(value) + "</td>";
    }

    private String getPointStringSeries(Series aSeries, int index) {
        int tt;
        StringBuilder tmpResult = new StringBuilder("");

        if ((aSeries.getCount() - 1) < index) {
            if (hasLabels) {
                tmpResult.append(emptyCell);
            }
            if (hasNoMandatory) {
                tmpResult.append(emptyCell);
            }
            tmpResult.append(emptyCell);

            for (tt = 2; tt < aSeries.getValuesLists().size(); tt++) {
                tmpResult.append(emptyCell);
            }
        } else {
            /* the point Label text, if ( exists */
            if (hasLabels) {
                tmpResult.append("<td>" + aSeries.getLabels().getString(index) + "</td>");
            }

            /* the "X" point value, if ( exists */
            if (hasNoMandatory) {
                tmpResult.append(cellDouble(aSeries.getNotMandatory().getValue(index)));
            }

            /* the "Y" point value */
            tmpResult.append(cellDouble(aSeries.getMandatory().getValue(index)));

            /* write the rest of values (always) */
            for (tt = 2; tt < aSeries.getValuesLists().size(); tt++) {
                tmpResult.append(cellDouble(aSeries.getValueList(tt).getValue(index)));
            }
        }

        return tmpResult.toString();
    }

    private String getPointString(int index) {
        StringBuilder tmpResult = new StringBuilder(1);

        tmpResult.append(getIncludeIndex() ? "<td>" + Integer.toString(index) + "</td>" :
                         "");

        if (series != null) {
            tmpResult.append(getPointStringSeries(series, index));
        } else {
            for (int t = 0; t < chart.getSeriesCount(); t++) {
                tmpResult.append(getPointStringSeries(chart.getSeries(t), index));
            }
        }
        return tmpResult.toString();
    }

    protected String pointToString(int index) {
        return "<tr>" + getPointString(index) + "</tr>";
    }
}

