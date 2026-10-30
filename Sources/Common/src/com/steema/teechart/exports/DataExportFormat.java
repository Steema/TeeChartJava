package com.steema.teechart.exports;

import com.steema.teechart.styles.Series;
import com.steema.teechart.styles.ValueList;
import com.steema.teechart.IBaseChart;

import java.io.IOException;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;

/**
 * <p>Title: TeeChart for Java Data Export Format base class</p>
 *
 * <p>Description: TeeChart for Java</p>
 *
 * <p>Copyright (c) 2004-2019 by Steema Software SL. All Rights Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 *
 */
public abstract class DataExportFormat {

    protected IBaseChart chart;

    private boolean includeIndex;
    private boolean includeHeader;
    private boolean includeSeriesTitle;
    private boolean includeLabels = true;

    protected boolean hasColors;
    protected boolean hasLabels;
    protected boolean hasNoMandatory;
    protected boolean hasMarkPositions;
    protected Series series;

    private String textLineSeparator = "\r\n";

    public String fileExtension = "";

    public DataExportFormat() {
        super();
    }

    /**
     * Data export constructor all Chart Series.<br>
     *
     * @param c IBaseChart
     */
    public DataExportFormat(IBaseChart c) {
        super();
        chart = c;
    }

    /**
     * Data export constructor, named Series.<br>
     *
     * @param c IBaseChart
     * @param s Series
     */
    public DataExportFormat(IBaseChart c, Series s) {
        this(c);
        series = s;
    }

    protected void prepare() {
        Series tmp = null;

        if (series != null) {
            tmp = series;
        } else
        if (chart.getSeriesCount() > 0) {
            int i = 0;

            while ((i < chart.getSeriesCount())) {
                if (chart.getSeries(i).getCount() != 0) {
                    tmp = chart.getSeries(i);
                    break;
                }
                i++;
            }
        }

        if (tmp != null) {
            seriesGuessContents(tmp);
        }

        if (!includeLabels) {
            hasLabels = false;
        }
    }

    private void seriesGuessContents(Series aSeries) {
        hasNoMandatory = hasNoMandatoryValues(aSeries);
        hasColors = aSeries.hasColors();
        hasLabels = aSeries.hasLabels();
        hasMarkPositions = aSeries.getMarks().getPositions().existCustom();
    }

    private int maxSeriesCount() {
        if (series != null) {
            return series.getCount();
        } else {
            int tmpResult = -1;

            for (int t = 0; t < chart.getSeriesCount(); t++) {
                Series s = chart.getSeries(t);
                if (s.getCount() > tmpResult) {
                    tmpResult = s.getCount();
                }
            }
            return tmpResult;

        }
    }

    // Returns if a Series has "X" values (or Y values for HorizBar series)
    static private boolean hasNoMandatoryValues(Series aSeries) {

        Series s = aSeries;

        if (s.getCount() > 0) {
            ValueList tmp = s.getNotMandatory();

            if ((tmp.getFirst() == 0) && (tmp.getLast() == s.getCount() - 1)) {

                int tmpCount = (s.getCount() - 1 < 10000) ? s.getCount() - 1 :
                               10000;

                for (int t = 0; t <= tmpCount; t++) {
                    if (tmp.getValue(t) != t) {
                        return true;
                    }
                }
            } else {
                return true;
            }
        }

        return false;
    }

    protected abstract String pointToString(int index);

    protected String getContent() {
        prepare();

        String tmpResult="";

        int tmp = maxSeriesCount();

        for (int t = 0; t < tmp; t++) {
            tmpResult+=pointToString(t);
            tmpResult+=textLineSeparator;
        }

        return tmpResult;
    }

    /**
     * Saves Chart to file with Data export format.<br>
     *
     * @param fileName String
     * @throws IOException
     */
    public void save(String fileName) throws IOException {

        // FileWriter to support UNICODE ?
        File f=new File(fileName);
        save(new FileWriter(f));
    }

    /**
     * Saves Chart to stream with Data export format. <br>
     *
     * @param fw FileWriter
     * @throws IOException
     */
    public void save(FileWriter fw) throws IOException {

//        OutputStreamWriter tmp = new OutputStreamWriter(stream); //CDI use OutputStreamWriter instead of DataOutputStream
                                                                 //to avoid extraneous characters.

        PrintWriter pw=new PrintWriter(fw);
        pw.write(getContent());
        pw.flush();
        pw.close();
    }

    /**
     * Returns descriptive name of Dataformat. <br>
     *
     * @return String
     */
    protected String getDataFormat() {
        //TODO: ??
        return ""; // DataFormats.Text;
    }

    /**
     * Copy Chart data to clipboard.
     */
    public void copyToClipboard() {
    	chart.getParent().copyToClipboard(getContent());
    }

    /**
     * Includes the Series index with exported data.<br>
     *
     * @return boolean
     */
    public boolean getIncludeIndex() {
        return includeIndex;
    }
    /**
     * Includes the Series index with exported data.<br>
     *
     * @param value boolean
     */
    public void setIncludeIndex(boolean value) {
        includeIndex = value;
    }

    /**
     * Includes the Series valuelist name with exported data.<br>
     *
     * @return boolean
     */
    public boolean getIncludeHeader() {
        return includeHeader;
    }
    /**
     * Includes the Series valuelist name with exported data.<br>
     *
     * @param value boolean
     */
    public void setIncludeHeader(boolean value) {
        includeHeader = value;
    }

    /**
     * Includes the Series title with exported data. <br>
     * @return boolean
     */
    public boolean getIncludeSeriesTitle() {
        return includeSeriesTitle;
    }
    /**
     * Includes the Series title with exported data. <br>
     *
     * @param value boolean
     */
    public void setIncludeSeriesTitle(boolean value) {
        includeSeriesTitle = value;
    }

    /**
     * Includes data Labels with exported data.<br>
     *
     * @return boolean
     */
    public boolean getIncludeLabels() {
        return includeLabels;
    }
    /**
     * Includes data Labels with exported data.<br>
     *
     * @param value boolean
     */
    public void setIncludeLabels(boolean value) {
        includeLabels = value;
    }
    /**
     * Returns the corresponding Filter for the export format.
     *
     * @return String
     */
    public String getFilterFiles() {
        return "";
    }

    /**
     * Line separator for ascii export formats.<br>
     *
     * @return String
     */
    public String getTextLineSeparator() {
        return textLineSeparator;
    }
    /**
     * Line separator for ascii export formats.<br>
     *
     * @param value String
     */
    public void setTextLineSeparator(String value) {
        textLineSeparator = value;
    }

    /**
     * Series whose data is to be exported.<br>
     *
     * @return Series
     */
    public Series getSeries() {
        return series;
    }
    /**
     * Series whose data is to be exported.<br>
     *
     * @param value Series
     */
    public void setSeries(Series value) {
        series = value;
    }
    /**
     * Returns the desired File Extension for exported file.
     *
     * @return String
     */
    public String getFileExtension() {
        return fileExtension;
    }
    /**
     * Sets the desired File Extension for exported file.
     *
     * @param value String
     */
    public void setFileExtension(String value) {
        fileExtension = value;
    }
}

