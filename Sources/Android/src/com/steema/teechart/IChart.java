package com.steema.teechart;

import com.steema.teechart.events.FrameworkMouseEvent;

import java.io.InputStream;

import com.steema.teechart.styles.Series;
import com.steema.teechart.drawing.IGraphics3D;
import com.steema.teechart.drawing.Image;
import com.steema.teechart.drawing.Point;
import com.steema.teechart.axis.Axis;
import com.steema.teechart.axis.AxisLabelResolver;
import com.steema.teechart.legend.Legend;
import com.steema.teechart.legend.LegendResolver;
import com.steema.teechart.drawing.Graphics3D;

/**
 *
 * <p>Title: IChart class</p>
 *
 * <p>Description: Common Chart characteristics interface</p>
 *
 * <p>Copyright (c) 2005-2019 by Steema Software SL. All Rights Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 *
 */
public interface IChart {

    void checkBackground(Object sender, FrameworkMouseEvent e);

    Graphics3D checkGraphics();

    void checkTitle(Title aTitle, FrameworkMouseEvent e, ClickedParts c);

    void doAfterDraw();

    void doAfterDrawSeries();

    void doAllowScroll(Axis axis, double delta, ScrollResult result);

    void doBeforeDraw();

    void doBeforeDrawAxes();

    void doBeforeDrawSeries();

    void doChartPrint();

    void doClickAxis(Axis a, FrameworkMouseEvent e);

    void doClickLegend(Legend legend, FrameworkMouseEvent e);

    void doClickSeries(Object sender, Series s, int valueIndex,
                       FrameworkMouseEvent e);

    void doDrawImage(IGraphics3D g);

    Rectangle doGetAxesChartRect(Rectangle r);

    AxisLabelResolver getAxisLabelResolver();
    void setAxisLabelResolver(AxisLabelResolver value);
    void removeAxisLabelResolver();

    LegendResolver getLegendResolver();

    void doScroll(Object sender);

    void doSetBuffered(boolean value);

    void doUnZoomed(Object sender);

    void doZoomed(Object sender);

    Cursor getTeeCursor();

    void doInvalidate();

    boolean checkClickSeries();

    Point pointToScreen(Point p);

    void setCursor(Cursor c);

    void refreshControl();

    void setToolTip(ToolTip tool, String text);

    void setOpaque(boolean value);

    boolean isDesignTime();

    void timerExec(int time, Runnable runnable);

    void copyToClipboard(Object object);

    Chart xmlDecode(InputStream stream);

    Object getGraphics();

    int getScreenHeight();

    int getScreenWidth();
	
    Object getControl();
	
    Image image(int width, int height);

    boolean invertedRotation();
    
    int getControlWidth();
    
    int getControlHeight();
    
    boolean isDisposing();
    
    void setDisposing(boolean value);

    boolean drawZoomOnCachedImage();

    void doMouseUp(FrameworkMouseEvent e);

    void doMouseMove(FrameworkMouseEvent e);
}

