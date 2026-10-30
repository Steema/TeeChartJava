package com.steema.teechart.styles;

import com.steema.teechart.drawing.Color;
import com.steema.teechart.IBaseChart;
import com.steema.teechart.functions.Function;
import java.util.ArrayList;
import com.steema.teechart.legend.LegendTextStyle;
import com.steema.teechart.Rectangle;
import com.steema.teechart.Swapper;
import com.steema.teechart.axis.Axis;

// Warning: This ISeries interface can / should be removed (like IChart)
/**
 *
 * <p>Title: ISeries class</p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright (c) 2005-2019 by Steema Software SL. All Rights Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 *
 */
public interface ISeries {

    void clear();

    void checkDataSource();

    Object getDataSource();

    void setDataSource(final Object value);

    void invalidate();

    void valuesListAdd(final ValueList value);

    boolean getShowInLegend();

    boolean hasDataSource(final Object source);

    void setSubGallery(int index);

    boolean getYMandatory();

    int getStartZ();

    int getMiddleZ();

    int getEndZ();

    boolean getColorEach();

    Color getColor();

    int getCount();

    int getCountLegendItems();

    SeriesMarks getMarks();

    IBaseChart getChart();

    ValueList getMandatory();

    ValueList getNotMandatory();

    ValueList getValueList(final String name);

    boolean getAllowSinglePoint();

    double getMarkValue(int index);

    String getMarkText(int index);

    String getValueFormat();

    boolean getActive();

    void setActive(boolean value);

    boolean getUseAxis();

    void dispose(); // misc

    void setChart(IBaseChart value);

    int add(final double value);

    int add(final double x, final double y);

    int add(final double x, final double y, final String text);

    void setFunction(Function value);

    StringList getLabels();

    ValueList getXValues();

    ValueList getYValues();

    boolean isNull(int index);

    void onDisposing();

    boolean getHasZValues();

    double getMinZValue();

    double getMaxZValue();

    ArrayList dataSourceArray();

    int getZOrder();

    int getFirstVisible();

    int getLastVisible();

    boolean associatedToAxis(Axis axis);

    void calcFirstLastVisibleIndex();

    String getValueMarkText(int index);

    String getLegendString(int valueIndex, final LegendTextStyle style);

    int legendToValueIndex(int itemIndex);

    Color legendItemColor(int itemIndex);

    void drawLegend(int index, final Rectangle r);

    Axis getHorizAxis();

    Axis getVertAxis();

    int clicked(int x, int y);

    double getOriginValue(int valueIndex);

    void drawMarks();

    void drawValue(int valueIndex);

    Color getValueColor(int valueIndex);

    Swapper getValueIndexSwapper();

	String calcPercentSt(final double value);
	
	boolean hasColors();
	
	boolean hasLabels();
}

