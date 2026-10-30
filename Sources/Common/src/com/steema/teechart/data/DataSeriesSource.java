package com.steema.teechart.data;

import com.steema.teechart.styles.Series;
import java.sql.ResultSet;
import com.steema.teechart.styles.ValueList;
import com.steema.teechart.drawing.Color;
import java.sql.SQLException;
import com.steema.teechart.styles.ValuesLists;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright (c) 2005-2019 by Steema Software SL. All Rights
 * Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 *
 */
public class DataSeriesSource {

    static public void fillSeries(Series series, ResultSet view) throws
            SQLException {

        ValuesLists lists = series.getValuesLists();

        int[] fields = new int[lists.size()];

        for (int t = 0; t < lists.size(); t++) {

            String tmp = lists.getValueList(t).getDataMember();

            if (tmp.equals("")) {
                fields[t] = -1;
            } else {
                fields[t] = view.findColumn(tmp);
            }
        }

        Color tmpColor = Color.EMPTY;
        String tmpLabel = "";

        int labelField = series.getLabelMember().equals("") ? -1 :
                         view.findColumn(series.getLabelMember());

        int colorField = series.getColorMember().equals("") ? -1 :
                         view.findColumn(series.getColorMember());

        ValueList tmpX = series.getXValues();
        ValueList tmpY = series.getYValues();
        ValueList notMandatory = series.getNotMandatory();

        while (view.next()) {

            if (colorField != -1) {
                tmpColor = Color.fromArgb(view.getInt(colorField));
            }

            if (labelField != -1) {
                tmpLabel = view.getString(labelField);
            }

            boolean tmpNull = false;

            for (int tt = 0; tt < lists.size(); tt++) {
                ValueList v = lists.getValueList(tt);

                int fieldIndex = fields[tt];

                if (fieldIndex == -1) {
                    if (v == notMandatory) {
                        v.tempValue = series.getCount();
                    } else {
                        v.tempValue = 0;
                    }
                } else {
                    if (v.getDateTime()) {
                        v.tempValue = view.getTimestamp(fieldIndex).getTime();
                    } else {
                        v.tempValue = view.getDouble(fieldIndex);
                    }

                    if (!tmpNull) {
                        tmpNull = view.wasNull();
                    }
                }
            }

            if (tmpNull) {
                series.add();
            } else {
                series.add(tmpX.tempValue, tmpY.tempValue, tmpLabel,
                           tmpColor);
            }
        }
    }

    static public boolean tryRefreshData(Series series) {
        Object o = series.getDataSource();

        if (o instanceof ResultSet) {
            try {
                fillSeries(series, (ResultSet) o);
                return true;
            } catch (Exception ex) {
                ex.printStackTrace();
                return false;
            }
        } else {
            return false;
        }
    }

    static public boolean isValidSource(Object source) {
        return (source instanceof ResultSet);
    }
}

