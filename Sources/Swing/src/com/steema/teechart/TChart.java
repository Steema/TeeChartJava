package com.steema.teechart;

import com.steema.teechart.events.ChartDrawEvent;
import com.steema.teechart.events.ChartEvent;
import com.steema.teechart.events.ChartListener;
import com.steema.teechart.events.ChartMotionListener;
import com.steema.teechart.events.ChartMouseEvent;
import com.steema.teechart.events.ChartMouseListener;
import com.steema.teechart.events.ChartPaintListener;
import com.steema.teechart.events.ChartPrintListener;
import com.steema.teechart.events.SeriesMouseEvent;
import com.steema.teechart.events.SeriesMouseListener;
import com.steema.teechart.exports.Exports;

import java.awt.AWTEvent;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.datatransfer.*;

import com.steema.teechart.events.FrameworkMouseEvent;
import com.steema.teechart.events.FrameworkMouseWheelEvent;

import java.awt.event.*;
import java.beans.*;
import java.io.*;
import javax.swing.JComponent;
import javax.swing.JToolTip;
import javax.swing.event.*;

import com.steema.teechart.axis.*;
import com.steema.teechart.drawing.*;
import com.steema.teechart.editors.ChartEditor;
import com.steema.teechart.imports.*;
import com.steema.teechart.legend.*;
import com.steema.teechart.printer.*;
import com.steema.teechart.styles.*;
import com.steema.teechart.tools.*;

/**
 *
 * <p>Title: TChart class</p>
 *
 * <p>Description: Principle Chart class for Windows based forms</p>
 *
 * <p>Copyright (c) 2005-2019 by Steema Software SL. All Rights Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 *
 */
public class TChart extends JComponent implements Serializable, IChart,
        MouseMotionListener, MouseWheelListener, Transferable, ClipboardOwner {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Chart chart;
    private java.awt.Image offscreen = null;

    transient private EventListenerList listenerList = new EventListenerList();

    public void addChartListener(ChartListener l) {
        listenerList.add(ChartListener.class, l);
    }

    public void removeChartListener(ChartListener l) {
        listenerList.remove(ChartListener.class, l);
    }

    public void addChartMouseListener(ChartMouseListener l) {
        listenerList.add(ChartMouseListener.class, l);
    }

    public void removeChartMouseListener(ChartMouseListener l) {
        listenerList.remove(ChartMouseListener.class, l);
    }

    public void addChartMotionListener(ChartMotionListener l) {
        listenerList.add(ChartMotionListener.class, l);
    }

    public void removeChartMotionListener(ChartMotionListener l) {
        listenerList.remove(ChartMotionListener.class, l);
    }

    public void addChartPaintListener(ChartPaintListener l) {
        listenerList.add(ChartPaintListener.class, l);
    }

    public void removeChartPaintListener(ChartPaintListener l) {
        listenerList.remove(ChartPaintListener.class, l);
    }

    public void addChartPrintListener(ChartPrintListener l) {
        listenerList.add(ChartPrintListener.class, l);
    }

    public void removeChartPrintListener(ChartPrintListener l) {
        listenerList.remove(ChartPrintListener.class, l);
    }

    public void addSeriesMouseListener(SeriesMouseListener l) {
        listenerList.add(SeriesMouseListener.class, l);
    }

    public void removeSeriesMouseListener(SeriesMouseListener l) {
        listenerList.remove(SeriesMouseListener.class, l);
    }

    protected void fireSeriesClick(SeriesMouseEvent seriesClickEvent) {
        Object[] listeners = listenerList.getListenerList();
        for (int i = listeners.length - 2; i >= 0; i -= 2) {
            if (listeners[i] == SeriesMouseListener.class) {
                ((SeriesMouseListener) listeners[i +
                 1]).seriesClicked(seriesClickEvent);
            }
        }
    }

    protected void fireChartAdded(ChartEvent e) {
        Object source = e.getSource();
        Object[] listeners = listenerList.getListenerList();
        for (int i = listeners.length - 2; i >= 0; i -= 2) {
            if (listeners[i] == ChartListener.class) {
                if (source instanceof Series) {
                    ((ChartListener) listeners[i + 1]).seriesAdded(e);
                } else if (source instanceof Tool) {
                    ((ChartListener) listeners[i + 1]).toolAdded(e);
                }
            }
        }
    }

    protected boolean fireChartClicked(ChartMouseEvent e) {
        boolean tmpClicked = false;
        Object[] listeners = listenerList.getListenerList();
        for (int i = listeners.length - 2; i >= 0; i -= 2) {
            if (listeners[i] == ChartMouseListener.class) {
                switch (e.getClickedPart().getValue()) {
                    //AXES
                    case 3: {
                        tmpClicked = true;
                        ((ChartMouseListener) listeners[i + 1]).axesClicked(e);
                        break;
                    }
                    //CHARTRECT
                    case 8: {
                        tmpClicked = ((ChartMouseListener) listeners[i + 1]).backgroundClicked(e);
                        break;
                    }
                    //LEGEND
                    case 1: {
                        tmpClicked = true;
                        ((ChartMouseListener) listeners[i + 1]).legendClicked(e);
                        break;
                    }
                    //TITLES
                    case 4: {
                        tmpClicked = true;
                        ((ChartMouseListener) listeners[i + 1]).titleClicked(e);
                        break;
                    }
                    case 5: {
                        tmpClicked = true;
                        ((ChartMouseListener) listeners[i + 1]).titleClicked(e);
                        break;
                    }
                    case 6: {
                        tmpClicked = true;
                        ((ChartMouseListener) listeners[i + 1]).titleClicked(e);
                        break;
                    }
                    case 7: {
                        tmpClicked = true;
                        ((ChartMouseListener) listeners[i + 1]).titleClicked(e);
                        break;
                    }
                }
            }
        }
        return tmpClicked;
    }
    
    protected boolean fireChartMouseUp(ChartMouseEvent e) {
        boolean tmpClicked = false;
        Object[] listeners = listenerList.getListenerList();
        for (int i = listeners.length - 2; i >= 0; i -= 2) {
            if (listeners[i] == ChartMouseListener.class) {
                if (e.getClickedPart().getValue() == 0) { //NONE
                    tmpClicked = ((ChartMouseListener) listeners[i + 1]).mouseUp(e);
                }
            }
        }
        return tmpClicked;
    }
    
    protected boolean fireChartMouseMove(ChartMouseEvent e) {
        boolean tmpClicked = false;
        Object[] listeners = listenerList.getListenerList();
        for (int i = listeners.length - 2; i >= 0; i -= 2) {
            if (listeners[i] == ChartMouseListener.class) {
                if (e.getClickedPart().getValue() == 0) { //NONE
                    tmpClicked = ((ChartMouseListener) listeners[i + 1]).mouseMove(e);
                }
            }
        }
        return tmpClicked;
    }

    protected void fireChartMotion(ChartEvent e) {
        Object[] listeners = listenerList.getListenerList();
        for (int i = listeners.length - 2; i >= 0; i -= 2) {
            if (listeners[i] == ChartMotionListener.class) {
                switch (e.getID()) {
                case ChartEvent.CHART_SCROLLED: {
                    ((ChartMotionListener) listeners[i + 1]).scrolled(e);
                    break;
                }
                case ChartEvent.CHART_ZOOMED: {
                    ((ChartMotionListener) listeners[i + 1]).zoomed(e);
                    break;
                }
                case ChartEvent.CHART_UNZOOMED: {
                    ((ChartMotionListener) listeners[i + 1]).unzoomed(e);
                    break;
                }
                }
            }
        }
    }

    protected void fireChartPaint(ChartDrawEvent e) {
        //Object source = e.getSource();
        Object[] listeners = listenerList.getListenerList();
        
        for (int i = listeners.length - 2; i >= 0; i -= 2) {
            if (listeners[i] == ChartPaintListener.class) {
                switch (e.getDrawPart()) {
                    //CHART
                    case 1: {
                        if (e.getID() == ChartDrawEvent.PAINTING) {
                            ((ChartPaintListener) listeners[i + 1]).
                                    chartPainting(e);
                        } else if (e.getID() == ChartDrawEvent.PAINTED) {
                            ((ChartPaintListener) listeners[i +
                                    1]).chartPainted(e);
                        }
                        break;
                    }
                    //AXES
                    case 2: {
                        if (e.getID() == ChartDrawEvent.PAINTING) {
                            ((ChartPaintListener) listeners[i +
                                    1]).axesPainting(e);
                        }
                        break;
                    }
                    //SERIES
                    case 3: {
                        if (e.getID() == ChartDrawEvent.PAINTING) {
                            ((ChartPaintListener) listeners[i + 1]).
                                    seriesPainting(e);
                        } else if (e.getID() == ChartDrawEvent.PAINTED) {
                            ((ChartPaintListener) listeners[i +
                                                1]).seriesPainted(e);
			}
                        break;
                    }
                }
            }
        }
    }

    protected void fireChartPrint(ChartDrawEvent ce) {
        Object[] listeners = listenerList.getListenerList();
        for (int i = listeners.length - 2; i >= 0; i -= 2) {
            if (listeners[i] == ChartPrintListener.class) {
                ((ChartPrintListener) listeners[i + 1]).chartPrinted(ce);
            }
        }
    }

    private Dimension defaultDimension = new Dimension(400, 250);

    public Dimension getPreferredSize() {
        if ((getWidth() == 0) || (getHeight() == 0))
            return defaultDimension;
        else
            return new Dimension(this.getWidth(),this.getHeight());
    }

    public void setPreferredSize(Dimension dim) {
        defaultDimension = dim;
    }

    //currently not used in swing
    private boolean _IsDisposing = false;

    public boolean isDisposing()
    {
            return _IsDisposing;
    }

    public void setDisposing(boolean value)
    {
            _IsDisposing = value;
    }

    /**
     * Cleans up any resources being used.
     *
     * @param disposing boolean
     */
    public void dispose() {
       chart.setAutoRepaint(false);
       chart.removeAllComponents();
    }

    /**
     * Determines which Chart is being printed.
     *
     * @return boolean
     */
    public boolean getPrinting() {
        return chart.printing;
    }

    /**
     * Determines which Chart is being printed.
     *
     * @param value boolean
     */
    public void setPrinting(boolean value) {
        chart.printing = value;
        doInvalidate();
    }

    /**
     * True when the Chart is internally drawing into a Metafile image.
     *
     * @return boolean
     */
    public boolean getMetafiling() {
        return chart.getGraphics3D().getMetafiling();
    }

    /**
     * Obsolete.&nbsp;Please use Aspect.<!-- -->ClipPoints instead.
     *
     * @return boolean
     *
     * @deprecated
     */
    public boolean getClipPoints() {
        return chart.getAspect().getClipPoints();
    }

    /**
     * Obsolete.&nbsp;Please use Aspect.<!-- -->ClipPoints instead.
     *
     * @param value boolean
     */
    public void setClipPoints(boolean value) {
        chart.getAspect().setClipPoints(value);
    }

    /**
     * Obsolete.&nbsp;Please use getSeries.getApplyZOrder() instead.
     *
     * @return boolean
     *
     * @deprecated
     */
    public boolean getApplyZOrder() {
        return chart.getSeries().getApplyZOrder();
    }

    /**
     * Obsolete.&nbsp;Please use getSeries().setApplyZOrder() instead.
     *
     * @param value boolean
     *
     * @deprecated
     */
    public void setApplyZOrder(boolean value) {
        chart.getSeries().setApplyZOrder(value);
    }

    /**
     * The color the Chart rectangle is filled with.<br>
     * The chart rectangle is the screen area between axes. <br>
     * Setting BackColor to Color.EMPTY does not fill the rectangle. <br>
     * Assigning Color.EMPTY to tChart.getBackColor() makes TeeChart NOT fill the
     * Chart back area, so Gradient fills and Background Bitmaps can be shown.
     *
     * @return Color
     */
    public Color getBackColor() {
        return chart.getPanel().getColor();
    }

    /**
     * The color the Chart rectangle is filled with.<br>
     * The chart rectangle is the screen area between axes. <br>
     * Setting BackColor to Color.EMPTY does not fill the rectangle. <br>
     * Assigning Color.EMPTY to tChart.getBackColor() makes TeeChart NOT fill the
     * Chart back area, so Gradient fills and Background Bitmaps can be shown.
     * 
     * @param value
     */
    public void setBackColor(java.awt.Color value) {
        setBackground(value);
    }

    /**
     * Sets the color the Chart rectangle is filled with.<br>
     *
     * @param value Color
     */
    public void setBackColor(Color value) {
        setBackground(value);
    }

    public void setBackground(Color value) {
        chart.getPanel().setColor(value);

        // Shouldn't be necessary to do this:
        //super.setBackground(value);
    }
    /**
     * Gets the colour of the Chart background.
     * 
     * @return
     */
    public java.awt.Color getBackground() {
        return chart.getPanel().getColor();
    }
    /**
     * Sets the colour of the Chart background.
     * 
     * @param value
     */
    public void setBackground(java.awt.Color value) {
        setBackground(new Color(value));
    }

    public Point mousePosition = new Point();

    public void mouseDragged(MouseEvent e) {
        mousePosition = new Point(e.getX(), e.getY());
    }

    /**
     * Captures current mouse position every time the mouse is moved.
     * @param e MouseEvent
     */
    public void mouseMoved(MouseEvent e) {
        mousePosition = new Point(e.getX(), e.getY());
    }

    /**
     * Obsolete. Please use mousePosition field instead.
     *
     * @return Point
     *
     * @deprecated
     */
    public Point getGetCursorPos() {
        return mousePosition;
    }

    /**
     * Adds a new Series.
     *
     * @param s Series
     * @return Series
     */
    public Series addSeries(Series s) {
        Series series;
        series = chart.getSeries().add(s);
        fireChartAdded(new ChartEvent(s, ChartEvent.CHART_ADDED));
        return series;
    }

    /**
     * Enables/Disables repainting of Chart when points are added.<br>
     * Use AutoRepaint false to disable Chart repainting whilst, for example,
     * adding a large number of points to a Chart Series. This avoids
     * repainting of the Chart whilst the points are added. <br>
     * AutoRepaint may be re-enabled, followed by a manual Repaint command
     * when all points are added. <br>
     * Default value: true
     *
     * @return boolean
     */
    public boolean getAutoRepaint() {
        return chart.getAutoRepaint();
    }

    /**
     * Enables/Disables repainting of Chart when points are added.<br>
     * Default value: true
     *
     * @see TChart#getAutoRepaint
     * @param value boolean
     */
    public void setAutoRepaint(boolean value) {
        chart.setAutoRepaint(value);
        // do not call invalidate here.
    }

    private boolean checkSize = false;

    /**
     * Return an image of the rendered Chart as Image.
     * 
     * @return
     */
    public Image image(int width, int height) {
		Image i=new Image(width,height);
		Graphics3DAWT g=new Graphics3DAWT(chart);
		g.setGraphics(i.getGraphics());
		chart.paint(g,new Rectangle(0,0,width,height));
		return i;
    }

    /**
     * Sets the Height of the Image.
     * 
     * @param value
     */
    public void setHeight(int value) {
        setSize(getWidth(), value);
    }
    /**
     * Sets the Width of the Image.
     * 
     * @param value
     */
    public void setWidth(int value) {
        setSize(value, getHeight());
    }

    /**
     * Removes all Series.
     *
     */
    public void removeAllSeries() {
        chart.getSeries().clear();
        chart.getLegend().setSeries(null);
    }

    /**
     * Obsolete.&nbsp;Use TChart.<!-- -->Series.<!-- -->Exchange() instead.
     *
     * @param series1 int
     * @param series2 int
     *
     * @deprecated
     */
    public void exchangeSeries(int series1, int series2) {
        chart.getSeries().exchange(series1, series2);
    }

    /**
     * Use TChart.<!-- -->getWalls()<!-- -->getPen() instead.
     *
     * @return ChartPen
     */
    public ChartPen getFrame() {
        return chart.getWalls().getBack().getPen();
    }

    /**
     * Returns the number of Series in Chart.
     *
     * @return int
     */
    public int getSeriesCount() {
        return chart.getSeries().size();
    }

    protected Scrollable scrollable;

    public void setScrollable(Scrollable scrollable) {
        this.scrollable = scrollable;
    }

    public void removeScrollable() {
        this.scrollable = null;
    }

    public interface Scrollable {
        ScrollResult isScrollable(Axis a, ScrollResult r);
    }


    private Image backImage;

    /** @todo FINISH ! JComponent has no implicit back image */
    /**
     * Returns background image of TChart
     * @return Image
     */
    public Image getBackgroundImage() {
        if (backImage == null) {
            backImage = new Image(getWidth(), getHeight(), 0);
        }
        return backImage;
    }

    public void doDrawImage(IGraphics3D g) {
        if (backImage != null) {
            g.draw(chart.chartRect.x,
                   chart.chartRect.y, backImage);
        }
    }

    public boolean drawZoomOnCachedImage() {
    	return false;
    }
    
    public void paint(Graphics g, Rectangle rectangle) {
        chart.getGraphics3D().setGraphics(g);

        if (getBorder() != null) {
            Insets i = getInsets();
            rectangle.x += i.left;
            rectangle.y += i.top;
            rectangle.width -= (i.left + i.right);
            rectangle.height -= (i.top + i.bottom);
        }
        chart.paint(chart.getGraphics3D(), rectangle);

        if (getBorder() != null) {
            getBorder().paintBorder(this, g, 0, 0, getWidth(), getHeight());
        }
    }

    public void paint(Graphics g, int width, int height) {
        paint(g, new Rectangle(0, 0, width, height));
    }

    protected void paintComponent(Graphics g) {
        
        if ((offscreen == null) || (offscreen.getWidth(this) != getWidth())
                || (offscreen.getHeight(this) != getHeight()))
        {
          offscreen = createImage(getWidth(), getHeight());
          chart.isDirty = true;
        }
        
        checkGraphics().setGraphics(offscreen.getGraphics());

        if ((chart.getWidth() != getWidth()) || (chart.getHeight() != getHeight()) || checkSize)
        {
          checkSize=false;
          this.repaint();
        }

        java.awt.Rectangle tmp = getBounds();

        tmp.x = 0;
        tmp.y = 0;

        if (getBorder() != null) {
            Insets i = getInsets();
            tmp.x += i.left;
            tmp.y += i.top;
            tmp.width -= (i.left + i.right);
            tmp.height -= (i.top + i.bottom);
        }

        if (chart.isDirty == true) {
          chart.paint(chart.getGraphics3D(), new Rectangle(tmp));
          chart.isDirty = false;
        }

        g.drawImage(offscreen,0,0,this);
    }

    public void doBeforeDrawAxes() {
        fireChartPaint(new ChartDrawEvent(this, ChartDrawEvent.PAINTING,
                                          ChartDrawEvent.AXES));
    }

    public void doAfterDrawSeries() {
        fireChartPaint(new ChartDrawEvent(this, ChartDrawEvent.PAINTED,
                                          ChartDrawEvent.SERIES));
    }

    public void doBeforeDrawSeries() {
        fireChartPaint(new ChartDrawEvent(this, ChartDrawEvent.PAINTING,
                                          ChartDrawEvent.SERIES));
    }

    public void doAfterDraw() {
        fireChartPaint(new ChartDrawEvent(this, ChartDrawEvent.PAINTED,
                                          ChartDrawEvent.CHART));
    }

    public void doBeforeDraw() {
        fireChartPaint(new ChartDrawEvent(this, ChartDrawEvent.PAINTING,
                                          ChartDrawEvent.CHART));
    }

    public void doClickSeries(Object sender, Series s, int valueIndex,
                              FrameworkMouseEvent e) {
        fireSeriesClick(new SeriesMouseEvent(s, SeriesMouseEvent.SERIES_CLICKED,
                                             valueIndex, e));
        /*if (clickSeries != null) {
            clickSeries.invoke(sender, s, valueIndex, e);
                 }*/
    }

    public void doAllowScroll(Axis a, double delta, ScrollResult result) {
        result.allow = true;
        if (scrollable != null) {
            result = scrollable.isScrollable(a, result);
        }
    }
    
    public void doMouseMove(FrameworkMouseEvent e) {
        if (fireChartMouseMove(new ChartMouseEvent(chart,
                ChartMouseEvent.MOUSE_MOVE, ClickedParts.NONE, e))) {
            chart.cancelMouse = true;
        }
    }

    transient private final static AxisLabelResolver defaultAxisLabelResolver = new
            AxisLabelAdapter();

    transient protected AxisLabelResolver axisLabelResolver =
            defaultAxisLabelResolver;

    public void setAxisLabelResolver(AxisLabelResolver resolver) {
        this.axisLabelResolver = resolver;
    }

    public void removeAxisLabelResolver() {
        this.axisLabelResolver = defaultAxisLabelResolver;
    }

    public AxisLabelResolver getAxisLabelResolver() {
        return axisLabelResolver;
    }

    transient private final static LegendResolver defaultLegendResolver = new
            LegendAdapter();

    transient protected LegendResolver legendResolver = defaultLegendResolver;

    public void setLegendResolver(LegendResolver resolver) {
        this.legendResolver = resolver;
    }

    public void removeLegendResolver() {
        this.legendResolver = defaultLegendResolver;
    }

    public LegendResolver getLegendResolver() {
        return legendResolver;
    }

    public void doChartPrint() {
        fireChartPrint(new ChartDrawEvent(this, ChartDrawEvent.PRINTING,
                                          ChartDrawEvent.CHART));
    }

    public void doClickAxis(Axis a, FrameworkMouseEvent e) {
        if (fireChartClicked(new ChartMouseEvent(a,
                                                 ChartMouseEvent.MOUSE_CLICKED,
                                                 ClickedParts.AXIS, e))) {
            chart.cancelMouse = true;
        }
    }

    public void checkBackground(Object sender, FrameworkMouseEvent e) {
        if (fireChartClicked(new ChartMouseEvent(sender,
                                                 ChartMouseEvent.MOUSE_CLICKED,
                                                 ClickedParts.CHARTRECT, e))) {
            chart.cancelMouse = true;
            chart.iClicked = chart.cancelMouse;
        }
    }

    public Graphics3D checkGraphics() {
        Graphics3D g3D = (Graphics3D) getGraphics3D();

        if (g3D == null) {
            g3D = new Graphics3DAWT(chart);
            chart.setGraphics3D(g3D);
        }

        if (g3D instanceof Graphics3DAWT) {
            g3D.setGraphics((Graphics2D)getGraphics());
        }

        return g3D;
    }

    public void checkTitle(Title aTitle, FrameworkMouseEvent e, ClickedParts c) {
        if (fireChartClicked(new ChartMouseEvent(aTitle,
                                                 ChartMouseEvent.MOUSE_CLICKED,
                                                 c, e))) {
            chart.cancelMouse = true;
            chart.iClicked = chart.cancelMouse;
        }
        if (!chart.iClicked) {
            chart.checkZoomPanning((FrameworkMouseEvent)e);
        }
    }

    public void doClickLegend(Legend legend, FrameworkMouseEvent e) {
        if (fireChartClicked(new ChartMouseEvent(legend,
                                                 ChartMouseEvent.MOUSE_CLICKED,
                                                 ClickedParts.LEGEND, e))) {
            chart.cancelMouse = true;
            chart.iClicked = chart.cancelMouse;
        }
    }

    public void doScroll(Object sender) {
        fireChartMotion(new ChartEvent(sender, ChartEvent.CHART_SCROLLED));
    }

    public void doZoomed(Object sender) {
        fireChartMotion(new ChartEvent(sender, ChartEvent.CHART_ZOOMED));
    }

    public void doSetBuffered(boolean value) {
        setDoubleBuffered(value);
    }

    public void doUnZoomed(Object sender) {
        fireChartMotion(new ChartEvent(sender, ChartEvent.CHART_UNZOOMED));

    }
    /**
     * Returns the current active Cursor.
     * 
     * @return
     */
    public Cursor getTeeCursor() {
        return Cursors.toCursor(super.getCursor());
    }

    /**
     *Defines the Chart to display.
     * 
     * @param value Chart
     */
    public void setChart(Chart value) {
        if (value != null) {
            chart = value;
            chart.parent = this;
        }
    }
    /**
     * Collection of predefined and custom axis objects.
     * 
     * @param axes
     */
    public void setAxes(Axes axes) {
        chart.setAxes(axes);
    }
    /**
     * Defines Text shown at the bottom of the Chart.<br>
     * Use Text to enter the desired Footer lines, set Visible to True and
     * change the Font, Frame and Brush methods.<br>
     * Use Alignment to control text output position.
     * 
     * @param footer
     */
    public void setFooter(Footer footer) {
        chart.setFooter(footer);
    }
    /**
     * Determines the Font characteristics.
     * 
     * @param font
     */
    public void setFont(Font font) {
        super.setFont(font);
    }
    /**
     * Sets the text for the Footer, Header, SubFooter and SubHeader.
     * 
     * @param header
     */
    public void setHeader(Header header) {
        chart.setHeader(header);
    }
    /**
     * Legend characteristics.<br>
     * The Legend property determines the text and drawing attributes of
     * Chart's textual representation of Series and Series values.  <br>
     * The Legend class draws a rectangle and for each Series in a Chart
     * (or for each point in a Series) outputs a text representation of
     * that Series (or that point). <br>
     * You can use the Legend.LegendStyle and Legend.TextStyle properties to
     * control the text used to draw the legend. <br>
     * The Legend can be positioned at Left, Right, Top and Bottom chart sides
     * using the Legend.Alignment property. <br>
     * Use the Legend.Visible property to show / hide the Legend. <br>
     * The Inverted property makes Legend to draw text starting from bottom.<br>
     * The Frame, Font and Color properties allow you to change Legend
     * appearance. <br>
     * The Legend.ColorWidth property determines the percent width of each
     * item's "colored" mark. <br>
     * The Legend.FirstValue property controls which Series (or Series point)
     * will be used to draw first Legend item. <br>
     * 
     * @param legend
     */
    public void setLegend(Legend legend) {
        chart.setLegend(legend);
    }
    /**
     * Defines Text shown directly above Footer.<br>
     * Use Text to enter the desired SubFooter lines, set Visible to True and
     * change the Font, Frame and Brush methods.<br>
     * Use Alignment to control text output position.
     * 
     * @param subFooter
     */
    public void setSubFooter(Footer subFooter) {
        chart.setSubFooter(subFooter);
    }
    /**
     * Defines Text shown directly below Header.<br>
     * Use the Text method to enter the desired SubHeader lines, set Visible
     * to True and change the Font, Frame and Brush methods.<br>
     * Use the Alignment method to control text output position.
     * 
     * @param subHeader
     */
    public void setSubHeader(Header subHeader) {
        chart.setSubHeader(subHeader);
    }
    /**
     * Accesses left, bottom and back wall characteristics of the Chart.
     * 
     * @param walls
     */
    public void setWalls(Walls walls) {
        chart.setWalls(walls);
    }
    /**
     * Accesses the Zoom characteristics of the Chart.
     * 
     * @param zoom
     */
    public void setZoom(Zoom zoom) {
        chart.setZoom(zoom);
    }

    public void setSeries(SeriesCollection series) {
        chart.setSeries(series);
    }
    /**
     * Printing related attributes.
     * 
     * @param printer
     */
    public void setPrinter(Printer printer) {
        chart.setPrinter(printer);
    }
    /**
     * Accesses Panning characteristics. <br><br>
     * Scrolling speed depends on: <br>
     *    The number of Series and Series Points. <br>
     *    The Chart Width and Height. <br>
     *    The computer processor and Video card processor speed. <br>
     *    The Video resolution and number of colors. <br>
     *    The Windows version and the Video driver. <br>
     *    The speed when dragging the mouse !
     * 
     * @param panning
     */
    public void setPanning(Scroll panning) {
        chart.setPanning(panning);
    }
    /**
     * The color the Chart rectangle is filled with.<br>
     * The chart rectangle is the screen area between axes. <br>
     * Setting BackColor to Color.EMPTY does not fill the rectangle. <br>
     * Assigning Color.EMPTY to tChart.getBackColor() makes TeeChart NOT fill the
     * Chart back area, so Gradient fills and Background Bitmaps can be shown.
     * 
     * @param panel
     */
    public void setPanel(Panel panel) {
        chart.setPanel(panel);
    }
    /**
    * Accesses multiple page characteristics of the Chart.
    * 
    * @param page
    */
    public void setPage(Page page) {
        chart.setPage(page);
    }

    public void setChart(IChart value) {
        chart.parent = this;
    }
    
    public Object getControl() {
        return this;
    }
    
    public int getControlHeight() {
        return this.getHeight();
    }

    public int getControlWidth() {
        return this.getWidth();
    }
    
    public void doInvalidate() {
        checkSize=true;
        chart.isDirty = true;
        repaint();
    }

    public void doBaseInvalidate() {
    }

    public Point pointToScreen(Point p) {
        java.awt.Point screen = getLocationOnScreen();
        p.move( -screen.x, -screen.y);
        return p;
    }

    public void setCursor(Cursor c) {
        super.setCursor(Cursors.fromCursor(c));
        chart.originalCursor = c;
    }

    public void refreshControl() {
        chart.isDirty = true;
        paintImmediately(chart.chartBounds);
    }
    
    public JToolTip createToolTip()
    {
        return new com.steema.teechart.misc.JMultiLineToolTip();
    }

    public Image internalImage(int width, int height) {
		Image i=new Image(width,height);
		Graphics3DAWT g=new Graphics3DAWT(chart);
		g.setGraphics(i.getGraphics());
		chart.paint(g,new Rectangle(0,0,width,height));
		return i;
    }

    public TChart() {
        super();

        doSetBuffered(true);

        chart = new Chart(this, getTeeCursor());

        checkGraphics();

        enableEvents(AWTEvent.MOUSE_EVENT_MASK);
        enableEvents(AWTEvent.MOUSE_MOTION_EVENT_MASK);
        enableEvents(AWTEvent.MOUSE_WHEEL_EVENT_MASK);
        enableEvents(AWTEvent.COMPONENT_EVENT_MASK);
        addMouseMotionListener(this);

        super.setOpaque(true);

        offscreen = createImage(100, 100);
    }

    /**
     * Shows the TeeChart editor dialog.
     *
     * Displays the Chart Editor at runtime opening the Editor at the Series
     * page of the SeriesIndex Series.
     *
     */
    public void showEditor() {
        ChartEditor.editChart(getChart(), getName());
        getChart().setCancelMouse(true);
    }

    /**
     * Defines the Chart to display.
     *
     * @return Chart
     */
    public Chart getChart() {
        return chart;
    }

    public boolean isOpaque() {
        return super.isOpaque();
    }

    /**
     * The text for the Footer, Header, SubFooter and SubHeader.
     *
     * @return String
     */
    public String getText() {
        return (chart == null) ? "" : chart.getHeader().getText();
    }

    /**
     * Sets the text for the Footer, Header, SubFooter and SubHeader.
     *
     * @param value String
     */
    public void setText(String value) {
        if (chart != null) {
            chart.getHeader().setText(value);
        }
    }

    /**
     * Background visible attributes.<br>
     * Provides access, via the Panel Interface, to all Chart Panel properties.
     *
     * @return Panel
     */
    public Panel getPanel() {
        return chart.getPanel();
    }

    /**
     * Printing related attributes.
     *
     * @return Printer
     */
    public Printer getPrinter() {
        return chart.getPrinter();
    }
    /**
     * 
     * 
     * @param value
     */
    public void setOpaque(boolean value) {
        super.setOpaque(value);

        if (value) {
            if (getBackColor().getNone()) {
                setBackColor(Panel.defaultColor);
                invalidate();
            }
        } else {
            setBackColor(Color.TRANSPARENT);
        }
    }

	public void doMouseUp(FrameworkMouseEvent e) {
        if (fireChartMouseUp(new ChartMouseEvent(chart,
                ChartMouseEvent.MOUSE_UP, ClickedParts.NONE, e))) {
            chart.cancelMouse = true;
        }
	}

    /**
     * Accesses multiple page characteristics of the Chart.
     *
     * @return Page
     */
    public Page getPage() {
        return chart.getPage();
    }

    /**
     * Legend characteristics.<br>
     * The Legend property determines the text and drawing attributes of
     * Chart's textual representation of Series and Series values.  <br>
     * The Legend class draws a rectangle and for each Series in a Chart
     * (or for each point in a Series) outputs a text representation of
     * that Series (or that point). <br>
     * You can use the Legend.LegendStyle and Legend.TextStyle properties to
     * control the text used to draw the legend. <br>
     * The Legend can be positioned at Left, Right, Top and Bottom chart sides
     * using the Legend.Alignment property. <br>
     * Use the Legend.Visible property to show / hide the Legend. <br>
     * The Inverted property makes Legend to draw text starting from bottom.<br>
     * The Frame, Font and Color properties allow you to change Legend
     * appearance. <br>
     * The Legend.ColorWidth property determines the percent width of each
     * item's "colored" mark. <br>
     * The Legend.FirstValue property controls which Series (or Series point)
     * will be used to draw first Legend item. <br>
     *
     * @return Legend
     */
    public Legend getLegend() {
        return chart.getLegend();
    }

    /**
     *
     * Defines the Text and formatting properties to be drawn at the top of
     * the Chart.<br>
     * Use Text to enter the desired Header lines, set Visible to True and
     * change the Font, Frame and Brush methods.<br>
     * Use Alignment to control text output position.
     *
     * @return Header
     */
    public Header getHeader() {
        return chart.getHeader();
    }

    /**
     * Defines Text shown directly below Header.<br>
     * Use the Text method to enter the desired SubHeader lines, set Visible
     * to True and change the Font, Frame and Brush methods.<br>
     * Use the Alignment method to control text output position.
     *
     * @return Header
     */
    public Header getSubHeader() {
        return chart.getSubHeader();
    }

    /**
     * Determines the Font characteristics.
     *
     * @return Font
     */
    public Font getFont() {
        return super.getFont();
    }

    /**
     * Determines the Foreground color.
     *
     * @return Color
     */
    public java.awt.Color getForeground() {
        return super.getForeground();
    }


    /**
     * Defines Text shown at the bottom of the Chart.<br>
     * Use Text to enter the desired Footer lines, set Visible to True and
     * change the Font, Frame and Brush methods.<br>
     * Use Alignment to control text output position.
     *
     * @return Footer
     */
    public Footer getFooter() {
        return chart.getFooter();
    }

    /**
     * Accesses the Zoom characteristics of the Chart.
     *
     * @return Zoom
     */
    public Zoom getZoom() {
        return chart.getZoom();
    }

    /**
     * Accesses Panning characteristics. <br><br>
     * Scrolling speed depends on: <br>
     *    The number of Series and Series Points. <br>
     *    The Chart Width and Height. <br>
     *    The computer processor and Video card processor speed. <br>
     *    The Video resolution and number of colors. <br>
     *    The Windows version and the Video driver. <br>
     *    The speed when dragging the mouse !
     *
     * @return Scroll
     */
    public Scroll getPanning() {
        return chart.getPanning();
    }

    /**
     * Defines Text shown directly above Footer.<br>
     * Use Text to enter the desired SubFooter lines, set Visible to True and
     * change the Font, Frame and Brush methods.<br>
     * Use Alignment to control text output position.
     *
     * @return Footer
     */
    public Footer getSubFooter() {
        return chart.getSubFooter();
    }

    /**
     * Accesses view characteristics of the Chart.<br>
     * 3D view parameters.
     *
     * @return Aspect
     */
    public Aspect getAspect() {
        return chart.getAspect();
    }

    /**
     * Obsolete.&nbsp;Please use getGraphics3D function.
     *
     * @return IGraphics3D
     * @deprecated
     */
    public IGraphics3D getCanvas() {
        return getGraphics3D();
    }

    /**
     * Obsolete.&nbsp;Please use setGraphics3D method.
     *
     * @param value IGraphics3D
     * @deprecated
     */
    public void setCanvas(IGraphics3D value) {
        setGraphics3D(value);
    }

    /**
     * Used to access TeeChart Draw attributes.
     *
     * @return IGraphics3D
     */
    public IGraphics3D getGraphics3D() {
        return (chart == null) ? null : chart.getGraphics3D();
    }

    /**
     * Used to access TeeChart Draw attributes.
     *
     * @param value IGraphics3D
     */
    public void setGraphics3D(IGraphics3D value) {
        chart.setGraphics3D(value);
    }

    /**
     * Collection of Series contained in this Chart.<br><br>
     * TeeChart Series are the data display method type, e.g. Line Series,
     * Bar Series, Pie Series etc. You can mix different Series types in a
     * Chart according to your requirements, thus your design is not limited to
     * just one 'Chart type' defined by TeeChart. The concept of Chart type
     * being virtually obsolete in terms of the number of permutations of
     * Charts (Series type mix) you may create. <br><br>
     * For more information please see "Tutorial 6 - Working with Series".
     *
     * @return SeriesCollection
     */
    public SeriesCollection getSeries() {
        return chart.series;
    }

    /**
     * Default indexer.<br>
     * Example: tChart1[0].Color=Color.Blue; is equivalent to
     * tChart1.Series[0].Color=Color.Blue;
     *
     * @param index int
     * @return Series
     */
    public Series getSeries(int index) {
        return chart.getSeries(index);
    }

    /**
     * Collection of Tool components contained in this Chart.
     *
     * @return ToolsCollection
     */
    public ToolsCollection getTools() {
        return chart.getTools();
    }

    /**
     * Accesses left, bottom and back wall characteristics of the Chart.
     *
     * @return Walls
     */
    public Walls getWalls() {
        return chart.getWalls();
    }

    /**
     * Collection of predefined and custom axis objects.
     *
     * @return Axes
     */
    public Axes getAxes() {
        return chart.getAxes();
    }

    /**
     * Accesses Chart export attributess.
     *
     * @return Exports
     */
    public Exports getExport() {
        return chart.getExport();
    }

    /**
     * Accesses Chart import attributes.
     *
     * @return Imports
     */
    public Imports getImport() {
        return chart.getImport();
    }
    /**
     * Collection of Series contained in this Chart.<br><br>
     * TeeChart Series are the data display method type, e.g. Line Series,
     * Bar Series, Pie Series etc. You can mix different Series types in a
     * Chart according to your requirements, thus your design is not limited to
     * just one 'Chart type' defined by TeeChart. The concept of Chart type
     * being virtually obsolete in terms of the number of permutations of
     * Charts (Series type mix) you may create. <br><br>
     * For more information please see "Tutorial 6 - Working with Series".
     * 
     * @param index
     * @param value
     */
    public void setSeries(int index, Series value) {
        chart.series.setSeries(index, value);
    }
    
    public boolean checkClickSeries()
    {
      for (int i=0; i<this.getSeriesCount(); i++)
        if (this.getSeries(i).hasClickEvents())
          return true;
      return false;
    }
    
    private FrameworkMouseEvent getFrameworkEvent(MouseEvent e)
    {
      return new FrameworkMouseEvent(e.getComponent(),e.getID(),
                                      e.getWhen(),e.getModifiers(),
                                      e.getX(),e.getY(),e.getClickCount(),
                                      true,e.getButton());
    }
    
    private FrameworkMouseWheelEvent getFrameworkEvent(MouseWheelEvent e)
    {
      return new FrameworkMouseWheelEvent(e.getComponent(), e.getID(),
    		  								e.getWhen(), e.getModifiers(),
    		  								e.getX(), e.getY(), e.getClickCount(),
    		  								true, e.getScrollType(),
    		  								e.getScrollAmount(), e.getWheelRotation());
    }
    
    protected void processMouseEvent(MouseEvent e) {
        chart.cancelMouse = false;
        
        if (_allowMouse)
        {
            FrameworkMouseEvent fe= getFrameworkEvent(e);

            if (!Beans.isDesignTime()) {
                prepareGraphics();

                if (e.getID() == FrameworkMouseEvent.MOUSE_PRESSED) {
                    chart.mousePressed(fe);
                } else
                if (e.getID() == FrameworkMouseEvent.MOUSE_RELEASED) {
                    chart.mouseReleased(fe);
                } else
                if (e.getID() == FrameworkMouseEvent.MOUSE_EXITED) {
                    // Cursor=originalCursor;
                    chart.getPanning().setActive(false);
                    setCursor(chart.originalCursor);
                }
            }

            if (!chart.cancelMouse) {
                super.processMouseEvent(fe);
            }
        }
        else
            return;
    }

    
    protected void processMouseWheelEvent(MouseWheelEvent e) {
    	chart.cancelMouse = false;
    	if (_allowMouse) {
    		FrameworkMouseWheelEvent fwe = getFrameworkEvent(e);
    		//chart.mouseWheel(fwe);  //to implement if we want broadcast the event to series/tools...
    		
    		if (!chart.cancelMouse) {
                super.processMouseWheelEvent(fwe);
            }
        }
    }
    
    private boolean _allowMouse = true;
    
    public boolean getAllowMouse()
    {
        return _allowMouse;
    }
    
    public void setAllowMouse(boolean value)
    {
        _allowMouse = value;
    }
    
    
    protected void processMouseMotionEvent(MouseEvent e) {
        
        if (_allowMouse)
        {
            chart.cancelMouse = false;

            if (!Beans.isDesignTime()) {
                prepareGraphics();

                /*(Component source, int id, long when, int modifiers,
                          int x, int y, int clickCount, boolean popupTrigger,
                          int button) */

                FrameworkMouseEvent fe= getFrameworkEvent(e);

                Cursor c = chart.mouseMoved(fe);

                if (c == null) {
                    if (chart.originalCursor == null) {
                        setCursor(Cursor.DEFAULT);
                    }

                    setSuperCursor(chart.originalCursor);
                } else {
                    setSuperCursor(c);
                }
            }

            if (!chart.cancelMouse) {
                super.processMouseMotionEvent(e);
            }
        }
        else
            return;
    }

    private void setSuperCursor(Cursor c) {
        int tmp = c.getType();
        if ((tmp == Cursor.CUSTOM_CURSOR) || (tmp != getCursor().getType())) {
            super.setCursor(Cursors.fromCursor(c));
        }
    } 

    private void prepareGraphics() {
        checkGraphics().setGraphics(getGraphics());
    }

    public void setToolTip(ToolTip tool, String text) {
        tool.setToolTip(this, text);
    }
    /**
     * Collection of Tool components contained in this Chart.
     * 
     * @param value
     */
    public void setTools(ToolsCollection value) {
        chart.setTools(value);
    }

    // Clipboard

    public DataFlavor[] getTransferDataFlavors() {
        DataFlavor[] d = {DataFlavor.imageFlavor, DataFlavor.stringFlavor};
        return d;
    }

    public boolean isDataFlavorSupported(DataFlavor flavor) {
        return flavor.equals(DataFlavor.imageFlavor) ||
                flavor.equals(DataFlavor.stringFlavor);
    }

    
    public boolean isDoubleBuffered() {
    	return true;
    }
    
    public synchronized Object getTransferData(DataFlavor flavor) throws
            UnsupportedFlavorException, IOException {
        if (flavor.equals(DataFlavor.imageFlavor)) {
            return getExport().getImage().image();
        } else {
            if (flavor.equals(DataFlavor.stringFlavor)) {
                return getExport().getTemplate().getXML();
            } else {
                return null;
            }
        }
    }
    
    /**
     * Return an image of the rendered Chart as Image.
     * 
     * @return
     */
    public Image getImage() {
        return getExport().getImage().image(getWidth(), getHeight());
    }

	
	public boolean isDesignTime() {
		return Beans.isDesignTime();
	}

	
	public void timerExec(int time, Runnable runnable) {
		// TODO Auto-generated method stub
		//display.timerExec(time, runnable);
	}

	
	public void copyToClipboard(Object object) {
		Transferable t;
		
		if (object instanceof String)
			t=new StringSelection((String) object);
		else
	    if (object instanceof Transferable)
	    	t=(Transferable)object;
	    else
	    	return;
		
        Toolkit.getDefaultToolkit().getSystemClipboard().setContents(t, this);
	}

	
	public Chart xmlDecode(InputStream stream) {
		XMLDecoder decoder = new XMLDecoder(stream);

        decoder.setExceptionListener(new ExceptionListener() {
            public void exceptionThrown(Exception exception) {
                exception.printStackTrace();
            }
        });

        Chart result = null;
        try {
            result = (Chart) decoder.readObject();
        } finally {
            decoder.close();
        }
        
        return result;
	}

	
	public int getScreenHeight() {
		return Toolkit.getDefaultToolkit().getScreenSize().height;
	}

	
	public int getScreenWidth() {
		return Toolkit.getDefaultToolkit().getScreenSize().width;
	}

	
	public void lostOwnership(Clipboard arg0, Transferable arg1) {
	}

	
	public void mouseWheelMoved(MouseWheelEvent e) {
		// TODO Auto-generated method stub
	}

	
	public boolean invertedRotation() {
		return chart.invertedRotation;
	}
}

