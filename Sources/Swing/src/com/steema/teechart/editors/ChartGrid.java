package com.steema.teechart.editors;

import java.text.DecimalFormat;
import com.steema.teechart.languages.Language;

import javax.swing.JTable;
import javax.swing.event.TableModelEvent;
import javax.swing.table.TableColumn;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableColumnModel;

import com.steema.teechart.Chart;
import com.steema.teechart.DateTime;


import com.steema.teechart.styles.Series;
import com.steema.teechart.styles.ValueList;

/**
 * <p>Title: TeeChart for Java</p>
 *
 * <p>Description: TeeChart for Java</p>
 *
 * <p>Copyright (c) 2004-2019 by Steema Software SL. All Rights Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 *
 */
public class ChartGrid extends JTable {

    final private class SeriesAndList {
        Series series;
        ValueList list;

        private SeriesAndList() {
            super();
            series = null;
            list = null;
        }
    }


    final private class ChartTableModel extends AbstractTableModel {

        private int rowCount, colCount;
        private Series series;
        private Chart chart;
        private int tmpCol;
        private int fixedRows;

        public boolean[] iHasNo = new boolean[100];

        public ChartTableModel(Chart chart, Series series) {
            super();
            this.chart = chart;
            this.series = series;
            isEditable = false;
            recalcDimensions();
        }

        public String getColumnName(int col) {
            return findHeaderText(col);
        }

        public boolean isCellEditable(int rowIndex, int columnIndex) {
            return ((isEditable) && (columnIndex > 0));
        }

        private int maxNumPoints() {
            if (series != null) {
                return series.getCount();
            } else {
                int result = 0;
                for (int t = 0; t < chart.getSeriesCount(); t++) {
                    Series tmp = chart.getSeries(t);
                    if ((t == 0) || (tmp.getCount() > result)) {
                        result = tmp.getCount();
                    }
                }
                return result;
            }
        }

        private boolean find(double value, double[] values) {
            for (int t = 0; t < values.length; t++) {
                if (values[t] == value) {
                    return true;
                }
            }
            return false;
        }

        private void setXYZDimensions() {
            double[] values = null;

            Series tmpSeries = getXYZSeries();

            if (tmpSeries != null) {

                for (int t = 0; t < tmpSeries.getCount(); t++) {
                    double tmp = tmpSeries.getValueList(2).getValue(t);

                    if (!find(tmp, values)) {
                        values = new double[values.length + 1];
                        values[values.length - 1] = tmp;
                    }
                }

                if (values != null) {
                    colCount = 1 + values.length;
                    values = null;
                }

                //1+Round(tmpSeries.MaxZValue-tmpSeries.MinZValue+1);

                for (int t = 0; t < tmpSeries.getCount(); t++) {

                    double tmp = tmpSeries.getNotMandatory().getValue(t);
                    if (!find(tmp, values)) {
                        values = new double[values.length + 1];
                        values[values.length - 1] = tmp;
                    }
                }

                rowCount = 2 + values.length;

                //      with tmpSeries.NotMandatoryValueList do
                //           RowCount:=2+Round(MaxValue-MinValue+1);
            }

            showLabels = false;
            showColors = false;
            fixedRows = 2;
        }

        public boolean getShowLabels() {
            return showLabels;
        }

        public boolean getShowColors() {
            return showColors;
        }

        public boolean getShowFields() {
            return showFields;
        }

        private void calcParams(Series series, int index) {

            if (xValues == ChartGridXValues.AUTO) {
                iHasNo[index] = series.hasNoMandatoryValues();
            } else {
                iHasNo[index] = (xValues == ChartGridXValues.YES);
            }

            if (iHasNo[index]) {
                tmpCol++;
            }

            tmpCol++;
            tmpCol += series.getValuesLists().size() - 2;
        }

        private void recalcDimensions() {
            {
                if (grid3DMode) {
                    setXYZDimensions();
                } else {
                    tmpCol = 1;

                    if ((chart != null) || (series != null)) {

                        if (series != null) {
                            calcParams(series, 0);
                        } else {
                            for (int t = 0; t < chart.getSeriesCount(); t++) {
                                calcParams(chart.getSeries(t), t);
                            }
                        }

                        if (showLabels) {
                            tmpCol++;
                        }
                        if (showColors) {
                            tmpCol++;
                        }

                        rowCount = Math.max(1 + firstRowNum(),
                                maxNumPoints() + firstRowNum());
                    } else {
                        rowCount = 1 + firstRowNum();
                    }

                    colCount = tmpCol;

                    //ColWidths[0] = 30;

                    //if (showColors && (ColCount > 1))
                    //   ColWidths[1] = 40; // 5.02

                    if (showFields) {
                        fixedRows = 2;
                    } else {
                        fixedRows = 1;
                    }

                }

                //notifyChange();
            }
        }

        public int getRowCount() {
            return rowCount;
        }

        public int getColumnCount() {
            return colCount;
        }

        private String getALabel(int index) {
            if (series != null) {
                return series.getLabels().getString(index);
            } else {
                // return first Label found
                for (int t = 0; t < chart.getSeriesCount(); t++) {
                    if (chart.getSeries(t).getLabels().getCount() > index) {
                        return chart.getSeries(t).getLabels().getString(index);
                    }
                }
            }
            return "";
        }

        private String findHeaderText(int columnIndex) {
            String result = "";

            if (columnIndex == 0) {
                result = "#";
            } else
                if (showLabels && (columnIndex == labelsColumn())) {
                result = Language.getString("Text");
                } else
                    if (showColors && (columnIndex == colorsColumn())) {
                result = Language.getString("Colors");
                    } else
                        if (columnIndex > labelsColumn()) {
                SeriesAndList tmpSeries = getSeries(columnIndex);
                if (tmpSeries != null) {
                    if (showFields) {
                        if (grid3DMode) {
                            result = Integer.toString((int) (tmpSeries.series.
                                    getMinXValue()) + columnIndex - 1);
                        } else {
                            ValueList tmpList = tmpSeries.list;
                            if (tmpList != null) {
                                result = tmpList.getName();
                            }
                        }
                    } else {
                        result = tmpSeries.series.titleOrName();
                    }
                }
                        }

            return result;
        }

        // Returns series and series valuelist for the ACol column parameter
        private SeriesAndList getSeries(int columnIndex) {
            SeriesAndList result = new SeriesAndList();

            if (grid3DMode) {
                result.series = getXYZSeries();
            } else {
                if (showLabels) {
                    columnIndex--;
                }
                if (showColors) {
                    columnIndex--;
                }

                if (columnIndex >= 0) {
                    if (series != null) {
                        int tmp = series.getValuesLists().size();

                        if (!iHasNo[0]) {
                            tmp--;
                        }

                        if (tmp >= columnIndex) {
                            result.series = series;

                            if (iHasNo[0]) {
                                result.list = result.series.getValueList(
                                        columnIndex -
                                        1);
                            } else
                                if (result.series.getYMandatory()) {
                                result.list = result.series.getValueList(
                                        columnIndex);
                                } else {
                                result.list = result.series.getValueList(
                                        columnIndex -
                                        1);
                                }

                            return result;
                        }
                    } else
                        if (chart != null) {
                        for (int t = 0; t < chart.getSeriesCount(); t++) {
                            int tmp = chart.getSeries(t).getValuesLists().size();
                            if (!iHasNo[t]) {
                                tmp--;
                            }

                            if (tmp >= columnIndex) {
                                result.series = chart.getSeries(t);
                                if (iHasNo[t]) {
                                    result.list = result.series.getValueList(
                                            columnIndex - 1);
                                } else
                                    if (result.series.getYMandatory()) {
                                    result.list = result.series.getValueList(
                                            columnIndex);
                                    } else {
                                    result.list = result.series.getValueList(
                                            columnIndex - 1);
                                    }

                                return result;
                            }

                            columnIndex -= tmp;
                        }
                        }
                }

            }

            return result;
        }


        private String getAValue(int rowIndex, int columnIndex) {
            SeriesAndList tmp = getSeries(columnIndex);

            if ((tmp.series != null) && (tmp.series.getCount() > rowIndex)) {
                String result;

                //TODO: Date and Time values (Review)
                if (tmp.list.getDateTime()) {
                    DateTime tmpValue;
                    tmpValue = tmp.list.asDateTime(rowIndex);
                    if (tmpValue.toDouble() < (24.0 * 3600000.0)) {
                        result = com.steema.teechart.misc.Utils.timeToStr(tmpValue);
                    } else {
                        result = com.steema.teechart.misc.Utils.dateTimeToDateTimeStr(tmpValue);
                    }
                } else {
                    result = new DecimalFormat(tmp.series.getValueFormat()).format(
                            tmp.list.getValue(rowIndex));
                }

                return result;
            } else {
                return "";
            }
        }

        private boolean isHeaderRow(int row) {
            return false;

            // was:
            //return ((!showFields) && (row == 0))
            //        || ((showFields && (row == 1)));
        }

        private int firstRowNum() {
            return showFields ? 1 : 0; // was: 2 : 1;
        }

        // Returns column number for series "Colors"
        private int colorsColumn() {
            return showColors ? 1 : -1;
        }

        // Returns column number for series "Labels"
        private int labelsColumn() {
            return showLabels ? showColors ? 2 : 1 : -1;
        }

        private int findXYZIndex(Series series, int rowIndex, int columnIndex) {
            ValueList tmpList = series.getYValueList("Z");

            for (int t = 0; t < series.getCount(); t++) {
                if ((tmpList.getValue(t) == rowIndex + 1) &&
                        (series.getNotMandatory().getValue(t) == columnIndex)) {
                    return t;
                }
            }

            return -1;
        }


        public Object getValueAt(int rowIndex, int columnIndex) {

            if (columnIndex == 0) { // First column
                if (rowIndex >= firstRowNum()) {
                    if (grid3DMode) {
                        Series tmpSeries = getXYZSeries();
                        return new Integer((int) (tmpSeries.getMinZValue()) +
                                rowIndex - firstRowNum());
                    } else {
                        return new Integer(rowIndex - firstRowNum());
                    }
                } else
                    if ((!showFields) || (rowIndex == 1)) {
                    return "#";
                    }
            } else {
                if (isHeaderRow(rowIndex)) {
                    return findHeaderText(columnIndex); // Header
                } else // Values
                    if (rowIndex >= firstRowNum()) {
                    rowIndex -= firstRowNum();

                    if (grid3DMode) {
                        Series tmpSeries = getXYZSeries();
                        int t = findXYZIndex(tmpSeries, rowIndex, columnIndex);

                        if (t != -1) {
                            return new DecimalFormat(tmpSeries.
                                    getValueFormat()).format(
                                    tmpSeries.getMandatory().
                                    getValue(t));
                        }
                    } else
                        if (showLabels && (columnIndex == labelsColumn())) {
                        return getALabel(rowIndex);
                        } else
                            if (showColors && (columnIndex == colorsColumn())) {
                        // return nothing (it will be a color renderer)
                            } else {
                        return getAValue(rowIndex, columnIndex);
                            }
                    }

            }
            return "";
        }

    }


    /**
     * getXYZSeries
     *
     * @return Series
     */
    private Series getXYZSeries() {
        return null;
    }

    private Chart chart;
    private boolean grid3DMode;
    private ChartTableModel model;

    protected ChartGridXValues xValues;
    protected boolean isEditable = false;
    protected boolean showColors = false;
    protected boolean showFields = false;
    protected boolean showLabels = false;

    protected void invalidateColumns() {
        TableColumnModel c = getColumnModel();

        //@todo it might be better to use a visible/hidden list, instead of removing all
        while (c.getColumns().hasMoreElements()) {
            c.removeColumn(((TableColumn)c.getColumns().nextElement()));
        }

        model.recalcDimensions();

        for (int t = 0; t < model.getColumnCount(); t++) {
            TableColumn tc = new TableColumn(t);
            tc.setHeaderValue(model.findHeaderText(t));
            c.addColumn(tc);
        }
    }

    public ChartGrid(Chart chart) {
        super();
        this.chart = chart;
        model = new ChartTableModel(chart, null);
        setModel(model);
        invalidateColumns();
        setAutoCreateColumnsFromModel(false);
    }

    public boolean getEditable() {
        return this.isEditable;
    }

    public void setEditable(boolean editable) {
        this.isEditable = editable;
    }

    public boolean getShowColors() {
        return this.showColors;
    }

    public void setShowColors(boolean visible) {
        this.showColors = visible;
        invalidateColumns();
    }

    public boolean getShowFields() {
        return this.showFields;
    }

    public void setShowFields(boolean visible) {
        this.showFields = visible;
        invalidateColumns();
    }

    public boolean getShowLabels() {
        return this.showLabels;
    }

    public void setShowLabels(boolean visible) {
        this.showLabels = visible;
        invalidateColumns();
    }

    public ChartGridXValues getShowXValues() {
        return this.xValues;
    }

    public void setShowXValues(ChartGridXValues xValues) {
        this.xValues = xValues;
        invalidateColumns();
    }

    public void tableChanged(TableModelEvent e) {
        if (chart != null) {
            chart.invalidate();
        }
    }

    /*
         public Component prepareRenderer(TableCellRenderer renderer,
                                     int row, int column) {
        Component c = super.prepareRenderer(renderer, row, column);
        if ((row % 2 == 0) && (!isCellSelected(row, column))) {
            c.setBackground(Color.lightGray);
        } else {
            c.setBackground(getBackground());
        }
        return c;
         }
     */
}

