package com.steema.teechart;

import com.steema.teechart.events.TeeEvent;

import com.steema.teechart.drawing.ChartBrush;
import com.steema.teechart.styles.SeriesCollection;
import com.steema.teechart.drawing.ChartFont;
import com.steema.teechart.tools.ToolsCollection;
import com.steema.teechart.styles.SeriesEventStyle;
import com.steema.teechart.drawing.ChartPen;
import com.steema.teechart.drawing.Color;
import com.steema.teechart.drawing.Image;
import com.steema.teechart.drawing.Point;
import com.steema.teechart.styles.Series;
import com.steema.teechart.drawing.IGraphics3D;
import com.steema.teechart.axis.Axis;
import com.steema.teechart.legend.Legend;
import com.steema.teechart.axis.Axes;
import com.steema.teechart.tools.ToolAxis;
import com.steema.teechart.themes.Theme;

// Can be merged with IChart interface, later...

/**
 *
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright (c) 2005-2019 by Steema Software SL. All Rights Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 *
 */
public interface IBaseChart {
    
    IChart getParent();

    Panel getPanel();

    Legend getLegend();

    Walls getWalls();

    Header getHeader();
    
    Footer getFooter();

    void doBaseInvalidate();

    void doChangedBrush(ChartBrush value);

    void doChangedFont(ChartFont value);

    IGraphics3D getGraphics3D();

    Rectangle recalcWidthHeight(Rectangle rect); // DB

    void broadcastEvent(TeeEvent event);

    boolean canDrawPanelBack();

    int getNumPages();

    Aspect getAspect();

    MultiLine multiLineTextWidth(String value);

    ToolsCollection getTools();

    Series getSeries(int index);
    SeriesCollection getSeries();

    void invalidate();

    int getSeriesCount();

    TeeEvent broadcastEvent(Series s, SeriesEventStyle e);

    ToolAxis getToolCheckHidden();

    void setToolCheckHidden(ToolAxis tool);

    Rectangle getChartRect();

    void setChartRect(Rectangle rect);

    void setChartRect(Rectangle rect, boolean cache);

    boolean getCustomChartRect();
    
    void setCustomChartRect(boolean value);

    Rectangle getChartBounds();

    Page getPage();

    void setAutoRepaint(boolean value);

    boolean getAutoRepaint();

    int getMaxZOrder();

    void setMaxZOrder(int value);

    int getWidth();

    int getHeight();

    int getLeft();

    int getRight();

    int getBottom();

    int getTop();

    int countActiveSeries();

    int getSeriesIndexOf(Series value);

    ChartPen getLegendPen();

    void setBrushCanvas(Color color, ChartBrush brush, Color backColor);

    void doZoom(Point.Double top, Point.Double bottom, Point.Double left,
                Point.Double right);

    void doZoom(double topx, double topy, double bottomx, double bottomy,
                double leftx, double lefty, double rightx, double righty);

    int maxMarkWidth();

    int maxTextWidth();

    int addSeries(Series series);

    void removeSeries(Series series);

    void moveSeriesTo(Series series, int index);

    boolean isAxisVisible(Axis axis);

    double internalMinMax(Axis axis, boolean isMinimum, boolean isHorizontal);

    double getMaxXValue(Axis axis);

    double getMaxYValue(Axis axis);

    double getMinXValue(Axis axis);

    double getMinYValue(Axis axis);

    Series getFirstActiveSeries();

    Axes getAxes();

    boolean isAxisCustom(Axis axis);
    
   
    String getSeriesTitleLegend(int index, boolean activeOnly);

    Series seriesLegend(int index, boolean onlyActive);

    Series activeSeriesLegend(int itemIndex);
    
    boolean drawLeftWallFirst();
    
    boolean drawRightWallAfter();

    String formattedLegend(int pos);

    String formattedValueLegend(Series series, int seriesOrValueIndex);

    void setLegendPen(ChartPen pen);

    void setCancelMouse(boolean value);

    boolean getPrinting();

    void setPrinting(boolean value);

    int getMaxValuesCount();

    boolean canClip();

    Color freeSeriesColor(boolean checkBackground);

    int getSeriesWidth3D();

    int getSeriesHeight3D();

    ToolTip getToolTip();

    void setLegend(Legend value);

    Rectangle doDrawLegend(IGraphics3D g, Rectangle rect);

    void paint(IGraphics3D g);

    void paint(IGraphics3D g, Rectangle rect);

    void restoreAxisScales();

    void setGraphics3D(IGraphics3D value);

    void setWidth(int value);

    void setHeight(int value);

    Image image(int width, int height);
    
    boolean isDesignTime();

    int getScreenHeight();

    int getScreenWidth();

    boolean drawBackWallAfter(int z);
    
    Scroll getPanning();
    
    void setPanning(Scroll panning);
    
    Zoom getZoom();
    
    void setZoom(Zoom zoom);

    int maxTextHeight(Axis aThis);
    
    Theme getInternalTheme();
    
    void setInternalTheme(Theme theme);
}

