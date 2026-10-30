package com.steema.teechart;

import java.io.InputStream;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.os.Handler;
import android.text.ClipboardManager;
import android.util.AttributeSet;
import android.view.Display;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.WindowManager;

import com.steema.teechart.Zoom.ZoomResetStyle;
import com.steema.teechart.Zoom.ZoomStyle;
import com.steema.teechart.android.Graphics3DAndroid;
import com.steema.teechart.axis.AllAxisSavedScales;
import com.steema.teechart.axis.Axes;
import com.steema.teechart.axis.Axis;
import com.steema.teechart.axis.AxisLabelAdapter;
import com.steema.teechart.axis.AxisLabelResolver;
import com.steema.teechart.drawing.Color;
import com.steema.teechart.drawing.Graphics3D;
import com.steema.teechart.drawing.IGraphics3D;
import com.steema.teechart.drawing.Image;
import com.steema.teechart.drawing.Point;
import com.steema.teechart.events.ChartDrawEvent;
import com.steema.teechart.events.ChartEvent;
import com.steema.teechart.events.ChartListener;
import com.steema.teechart.events.ChartMotionListener;
import com.steema.teechart.events.ChartMouseEvent;
import com.steema.teechart.events.ChartMouseListener;
import com.steema.teechart.events.ChartPaintListener;
import com.steema.teechart.events.ChartPrintListener;
import com.steema.teechart.events.EventListenerList;
import com.steema.teechart.events.FrameworkMouseEvent;
import com.steema.teechart.events.GetAxesChartRectListener;
import com.steema.teechart.events.SeriesMouseEvent;
import com.steema.teechart.events.SeriesMouseListener;
import com.steema.teechart.exports.Exports;
import com.steema.teechart.imports.Imports;
import com.steema.teechart.languages.Language;
import com.steema.teechart.legend.Legend;
import com.steema.teechart.legend.LegendAdapter;
import com.steema.teechart.legend.LegendResolver;
import com.steema.teechart.printer.Printer;
import com.steema.teechart.styles.Series;
import com.steema.teechart.styles.SeriesCollection;
import com.steema.teechart.tools.Rotate;
import com.steema.teechart.tools.Tool;
import com.steema.teechart.tools.ToolsCollection;

/**
 * <p>
 * Title: TeeChart for Android
 * </p>
 * 
 * <p>
 * Description: TeeChart for Android
 * </p>
 * 
 * <p>
 * Copyright: (c) 2005-2019 by Steema Software. All Rights Reserved.
 * </p>
 * 
 * <p>
 * Company: Steema Software SL
 * </p>
 * 
 */
public class TChart extends View implements IChart, OnTouchListener {

	private Chart chart;
	private Graphics3DAndroid androidGraphics;
	private Rect tmpRect;
	transient public Image cachedImage;

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

    public void addGetAxesChartRectListener(GetAxesChartRectListener l) {
        listenerList.add(GetAxesChartRectListener.class, l);
    }

    public void removeGetAxesChartRectListener(GetAxesChartRectListener l) {
        listenerList.remove(GetAxesChartRectListener.class, l);
    }

	protected void fireSeriesClick(SeriesMouseEvent seriesClickEvent) {
		Object[] listeners = listenerList.getListenerList();
		for (int i = listeners.length - 2; i >= 0; i -= 2) {
			if (listeners[i] == SeriesMouseListener.class) {
				((SeriesMouseListener) listeners[i + 1])
						.seriesClicked(seriesClickEvent);
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
				// AXES
				case 3: {
					tmpClicked = true;
					((ChartMouseListener) listeners[i + 1]).axesClicked(e);
					break;
				}
				// CHARTRECT
				case 8: {
					tmpClicked = ((ChartMouseListener) listeners[i + 1]).backgroundClicked(e);
					break;
				}
				// LEGEND
				case 1: {
					tmpClicked = true;
					((ChartMouseListener) listeners[i + 1]).legendClicked(e);
					break;
				}
				// TITLES
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
		// Object source = e.getSource();

		Object[] listeners = listenerList.getListenerList();

		for (int i = listeners.length - 2; i >= 0; i -= 2) {
			if (listeners[i] == ChartPaintListener.class) {
				switch (e.getDrawPart()) {
				// CHART
				case 1: {
					if (e.getID() == ChartDrawEvent.PAINTING) {
						((ChartPaintListener) listeners[i + 1])
								.chartPainting(e);
					} else if (e.getID() == ChartDrawEvent.PAINTED) {
						((ChartPaintListener) listeners[i + 1]).chartPainted(e);
					}
					break;
				}
				// AXES
				case 2: {
					if (e.getID() == ChartDrawEvent.PAINTING) {
						((ChartPaintListener) listeners[i + 1]).axesPainting(e);
					}
					break;
				}
				// SERIES
				case 3: {
					if (e.getID() == ChartDrawEvent.PAINTING) {
						((ChartPaintListener) listeners[i + 1])
								.seriesPainting(e);
					} else if (e.getID() == ChartDrawEvent.PAINTED) {
						((ChartPaintListener) listeners[i + 1])
								.seriesPainted(e);
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

	protected Rectangle fireGetAxesChartRect(Rectangle rect) {
		Object[] listeners = listenerList.getListenerList();
		for (int i = listeners.length - 2; i >= 0; i -= 2) {
			if (listeners[i] == GetAxesChartRectListener.class) {
				rect = ((GetAxesChartRectListener) listeners[i + 1]).doGetAxesChartRect(rect);
                getChart().reCalcAxesElements(rect);
			}
		}
		return rect;
	}

	public TChart(Context context) {
		this(context, null);
	}

	public TChart(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public TChart(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);

		Language.isDesignTime = isDesignTime();

		chart = new Chart(this);
		chart.setParent(this);
		tmpRect = new Rect();

		androidGraphics = new Graphics3DAndroid(chart);
		chart.setGraphics3D(androidGraphics);

		setOnTouchListener(this);

		getLegend().setUseMaxWidth(true);
		chart.getPanning().setMouseButton(FrameworkMouseEvent.BUTTON1);
	}

	public void paint() {

		Graphics3DAndroid g3d;

		if (androidGraphics == null)
			g3d = new Graphics3DAndroid(chart);
		else
			g3d = androidGraphics;

		chart.paint(g3d, getWidth(), getHeight());
	}

	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		androidGraphics.setGraphics(canvas);

		// final Rect rect = new Rect();
		getDrawingRect(tmpRect);
		chart.chartBounds.x = tmpRect.left;
		chart.chartBounds.y = tmpRect.top;
		chart.chartBounds.width = tmpRect.width();
		chart.chartBounds.height = tmpRect.height();

		chart.paint();
	}

	// IChart
	public Cursor getTeeCursor() {
		// super.getCursor();
		return new Cursor(0);
	}
	
	public void doOnResetZoom() {
		fireChartPaint(new ChartDrawEvent(this, ChartDrawEvent.PAINT_FIRST,
				ChartDrawEvent.AXES));
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
		fireSeriesClick(new SeriesMouseEvent(s,
				SeriesMouseEvent.SERIES_CLICKED, valueIndex, e));
		/*
		 * if (clickSeries != null) { clickSeries.invoke(sender, s, valueIndex,
		 * e); }
		 */
	}

	public void doAllowScroll(Axis a, double delta, ScrollResult result) {
		result.allow = true;
		if (scrollable != null) {
			result = scrollable.isScrollable(a, result);
		}
	}

    public Rectangle doGetAxesChartRect(Rectangle rect) {
        return fireGetAxesChartRect(rect);
    }

	public void setCursor(Cursor cursor) {
	}

	public void setOpaque(boolean value) {
	}

	public void setToolTip(ToolTip tooltip, String text) {
		tooltip.setText(text);
	}

	public void refreshControl() {
		chart.isDirty = true;
		invalidate();
	}

	public void checkTitle(Title aTitle, FrameworkMouseEvent e, ClickedParts c) {
		if (fireChartClicked(new ChartMouseEvent(aTitle,
				ChartMouseEvent.MOUSE_CLICKED, c, e))) {
			chart.cancelMouse = true;
			chart.iClicked = chart.cancelMouse;
		}
		if (!chart.iClicked) {
			chart.checkZoomPanning(e);
		}
	}

	public void doClickLegend(Legend legend, FrameworkMouseEvent e) {
		if (fireChartClicked(new ChartMouseEvent(legend,
				ChartMouseEvent.MOUSE_CLICKED, ClickedParts.LEGEND, e))) {
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
		// setDoubleBuffered(value);
	}

	public void doUnZoomed(Object sender) {
		fireChartMotion(new ChartEvent(sender, ChartEvent.CHART_UNZOOMED));
	}

	public void doInvalidate() {
		refreshControl();
	}

	public Point pointToScreen(Point p) {
		return p;
	}

	public Chart getChart() {
		return chart;
	}

	/**
	 * Shows the TeeChart editor dialog.
	 * 
	 * Displays the Chart Editor at runtime opening the Editor at the Series
	 * page of the SeriesIndex Series.
	 * 
	 */
	public void showEditor() {
		// ChartEditor.editChart(getChart(), getName());
		getChart().setCancelMouse(true);
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
	 * @param value
	 *            String
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
	 * Accesses multiple page characteristics of the Chart.
	 * 
	 * @return Page
	 */
	public Page getPage() {
		return chart.getPage();
	}

	/**
	 * Legend characteristics.<br>
	 * The Legend property determines the text and drawing attributes of Chart's
	 * textual representation of Series and Series values. <br>
	 * The Legend class draws a rectangle and for each Series in a Chart (or for
	 * each point in a Series) outputs a text representation of that Series (or
	 * that point). <br>
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
	 * Defines the Text and formatting properties to be drawn at the top of the
	 * Chart.<br>
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
	 * Use the Text method to enter the desired SubHeader lines, set Visible to
	 * True and change the Font, Frame and Brush methods.<br>
	 * Use the Alignment method to control text output position.
	 * 
	 * @return Header
	 */
	public Header getSubHeader() {
		return chart.getSubHeader();
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
	 * Accesses Panning characteristics. <br>
	 * <br>
	 * Scrolling speed depends on: <br>
	 * The number of Series and Series Points. <br>
	 * The Chart Width and Height. <br>
	 * The computer processor and Video card processor speed. <br>
	 * The Video resolution and number of colors. <br>
	 * The Windows version and the Video driver. <br>
	 * The speed when dragging the mouse !
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

	// / <summary>
	// /
	// / </summary>
	public IGraphics3D getCanvas() {
		return getGraphics3D();
	}

	public void setCanvas(Graphics3D value) {
		setGraphics3D(value);
		value.invalidate();
	}

	// / <summary>
	// / Use the Graphics3D property to access TeeChart Draw properties and
	// methods.
	// / </summary>
	public IGraphics3D getGraphics3D() {
		return chart.getGraphics3D();
	}

	public void setGraphics3D(Graphics3D value) {
		chart.setGraphics3D(value);
	}

	// / <summary>
	// / Collection of Series contained in this Chart.
	// / </summary>
	public SeriesCollection getSeries() {
		return chart.getSeries();
	}

	// / <summary>
	// / Collection of Tool components contained in this Chart.
	// / </summary>
	public ToolsCollection getTools() {
		return chart.getTools();
	}

	// / <summary>
	// / Accesses left, bottom and back wall characteristics of the Chart.
	// / </summary>
	public Walls getWalls() {
		return chart.getWalls();
	}

	// / <summary>
	// / Collection of predefined and custom axis objects.
	// / </summary>
	public Axes getAxes() {
		return chart.getAxes();
	}

	// / <summary>
	// / Accesses Chart export properties and methods.
	// / </summary>
	public Exports getExport() {
		return chart.getExport();
	}

	// / <summary>
	// / Accesses Chart import properties and methods.
	// / </summary>
	public Imports getImport() {
		return chart.getImport();
	}

	public Series getSeries(int index) {
		return chart.getSeries(index);
	}

	public void setAxes(Axes axes) {
		chart.setAxes(axes);
	}

	public void setFooter(Footer footer) {
		chart.setFooter(footer);
	}

	public void setHeader(Header header) {
		chart.setHeader(header);
	}

	public void setLegend(Legend legend) {
		chart.setLegend(legend);
	}

	public void setSubFooter(Footer subFooter) {
		chart.setSubFooter(subFooter);
	}

	public void setSubHeader(Header subHeader) {
		chart.setSubHeader(subHeader);
	}

	public void setWalls(Walls walls) {
		chart.setWalls(walls);
	}

	public void setZoom(Zoom zoom) {
		chart.setZoom(zoom);
	}

	public void setSeries(int index, Series value) {
		chart.getSeries().setSeries(index, value);
	}

	public void setPanel(Panel panel) {
		chart.setPanel(panel);
	}

	public void setPage(Page page) {
		chart.setPage(page);
	}

	public void setPrinter(Printer printer) {
		chart.setPrinter(printer);
	}

	public void setPanning(Scroll panning) {
		chart.setPanning(panning);
	}

	/**
	 * Sets the color the Chart rectangle is filled with.<br>
	 * 
	 * @param value
	 *            Color
	 */
	public void setBackground(Color value) {
		chart.getPanel().setColor(value);

		// Shouldn't be necessary to do this:
		// super.setBackground(value);
	}

	public Point mousePosition = new Point();

	public void mouseDragged(FrameworkMouseEvent e) {
		mousePosition = e.getPoint();
	}

	/**
	 * @TODO ANY WAY TO OBTAIN CURRENT MOUSE POSITION WITHOUT MOTION LISTENER ?
	 *       --> MouseInfo
	 */
	/**
	 * Captures current mouse position every time the mouse is moved.
	 * 
	 * @param e
	 *            MouseEvent
	 */
	public void mouseMoved(FrameworkMouseEvent e) {
		mousePosition = e.getPoint();
	}

	/**
	 * Adds a new Series.
	 * 
	 * @param s
	 *            Series
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
	 * adding a large number of points to a Chart Series. This avoids repainting
	 * of the Chart whilst the points are added. <br>
	 * AutoRepaint may be re-enabled, followed by a manual Repaint command when
	 * all points are added. <br>
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
	 * @param value
	 *            boolean
	 */
	public void setAutoRepaint(boolean value) {
		chart.setAutoRepaint(value);
		// do not call invalidate here.
	}

	public void setHeight(int value) {
		this.chart.chartBounds.height = value;
	}

	public void setWidth(int value) {
		this.chart.chartBounds.width = value;
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

	public Rectangle getBounds() {
		return getChart().getChartBounds();
	}

	public interface Scrollable {
		ScrollResult isScrollable(Axis a, ScrollResult r);
	}

	private Image backImage;
	/**
	 * Returns background image of TChart
	 * 
	 * @return Image
	 */
	public Image getBackgroundImage() {
		if (backImage == null) {
            backImage = new Image(getWidth(), getHeight(), 0);
        }
        return backImage;
	}
	
	 public void setBackgroundImage(Image value) {
		 backImage = value;
	 }

	public void doDrawImage(IGraphics3D g) {
		if (backImage != null) {
			g.draw(chart.getChartRect().x, chart.getChartRect().y, backImage);
		}
	}

	public boolean drawZoomOnCachedImage() {
        if ((getZoom() != null) && (!getZoom().getFullRepaint()) &&
            (cachedImage != null) && (cachedImage.bitmap != null)) {

            final Object old = chart.getGraphics3D().getGraphics();
            Image i = new Image(Bitmap.createBitmap(cachedImage.bitmap));
            Canvas c = new Canvas(i.bitmap);
            chart.getGraphics3D().setGraphics(c);
            getZoom().draw();
            chart.getGraphics3D().setGraphics(old);

            chart.getGraphics3D().draw(0, 0, i);

            return true;
        }
        else
            return false;
	}

	/**
	 * 
	 * @param value
	 *            Chart
	 */
	public void setChart(Chart value) {
		if (value != null) {
			chart = value;
			chart.setParent(this);
			chart.setGraphics3D(androidGraphics);
			invalidate();
		}
	}

	transient private final static LegendResolver defaultLegendResolver = new LegendAdapter();

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

	public Graphics3D checkGraphics() {
		return (Graphics3D) getGraphics3D(); // androidGraphics;
	}

	public void doClickAxis(Axis a, FrameworkMouseEvent e) {
		if (fireChartClicked(new ChartMouseEvent(a,
				ChartMouseEvent.MOUSE_CLICKED, ClickedParts.AXIS, e))) {
			chart.cancelMouse = true;
		}
	}

	public void checkBackground(Object sender, FrameworkMouseEvent e) {
		if (fireChartClicked(new ChartMouseEvent(sender,
				ChartMouseEvent.MOUSE_CLICKED, ClickedParts.CHARTRECT, e))) {
			chart.cancelMouse = true;
			chart.iClicked = chart.cancelMouse;
		}
	}

	public void doMouseUp(FrameworkMouseEvent e) {
        if (fireChartMouseUp(new ChartMouseEvent(chart,
                ChartMouseEvent.MOUSE_UP, ClickedParts.NONE, e))) {
            chart.cancelMouse = true;
        }
	}

    public void doMouseMove(FrameworkMouseEvent e) {
        if (fireChartMouseMove(new ChartMouseEvent(chart,
                ChartMouseEvent.MOUSE_MOVE, ClickedParts.NONE, e))) {
            chart.cancelMouse = true;
        }
    }

    transient private final static AxisLabelResolver defaultAxisLabelResolver = new AxisLabelAdapter();

	transient protected AxisLabelResolver axisLabelResolver = defaultAxisLabelResolver;

	public void setAxisLabelResolver(AxisLabelResolver resolver) {
		this.axisLabelResolver = resolver;
	}

	public void removeAxisLabelResolver() {
		this.axisLabelResolver = defaultAxisLabelResolver;
	}

	public AxisLabelResolver getAxisLabelResolver() {
		return axisLabelResolver;
	}

	private void prepareGraphics() {
	}
	
	protected void processMouseEvent(FrameworkMouseEvent e) {
		chart.cancelMouse = false;
		// chart.isDirty = true;

		// ((com.steema.teechart.swt.Graphics3DSWT)this.getGraphics3D()).g=event.display.;

		if (!isDesignTime()) {
			prepareGraphics();

			// System.out.println("mouse" + String.valueOf(e.getID()));
			if (e.getID() == FrameworkMouseEvent.MOUSE_PRESSED) {
				// System.out.println("mouse press");
				chart.mousePressed(e);
			} else if (e.getID() == FrameworkMouseEvent.MOUSE_RELEASED) {
				// System.out.println("mouse release");
				chart.mouseReleased(e);
			} else if (e.getID() == FrameworkMouseEvent.MOUSE_EXITED) {
				// Cursor=originalCursor;
				chart.getPanning().setActive(false);
				setCursor(chart.originalCursor);
			}
		}
		/*
		 * if (!chart.cancelMouse) { super.processMouseEvent(e); }
		 */
	}

	protected void processMouseMotionEvent(FrameworkMouseEvent e) {
		chart.cancelMouse = false;

		prepareGraphics();
		Cursor c = chart.mouseMoved(e);

		if (c == null) {
			if (chart.originalCursor == null) {
				setSuperCursor(null);
			}

			setSuperCursor(chart.originalCursor);
		} else {
			setSuperCursor(c);
		}
	}

	private void setSuperCursor(Cursor c) {
		// mm super.setCursor(c);
	}

	public boolean checkClickSeries() {
		for (int i = 0; i < this.getSeriesCount(); i++)
			if (this.getSeries(i).hasClickEvents())
				return true;
		return false;
	}

	public boolean isDesignTime() {
		return isInEditMode();
	}

	private Handler timer;
	private double zoomDistance;

	public void timerExec(int time, Runnable runnable) {
		if (timer != null)
			timer.removeCallbacks(runnable);

		if (time != -1) {
			if (timer == null)
				timer = new Handler();
			timer.postDelayed(runnable, time);
		}
	}

	public void copyToClipboard(Object content) {
		// TODO Auto-generated method stub
		ClipboardManager clip = (ClipboardManager) getContext()
				.getSystemService(Context.CLIPBOARD_SERVICE);

		if (content instanceof String)
			clip.setText((String) content);
	}

	public Chart xmlDecode(InputStream stream) {
		return null; // XMLDecoder class not yet supported in Android
	}

	public Object getGraphics() {
		return androidGraphics;
	}

	public int getScreenHeight() {
		return getDisplay().getHeight();
	}

	public Display getDisplay() {
		return ((WindowManager) getContext().getSystemService(
				Context.WINDOW_SERVICE)).getDefaultDisplay();
	}

	public int getScreenWidth() {
		return getDisplay().getWidth();
	}

	public Object getControl() {
		return this;
	}

	public int getControlWidth() {
		return this.getWidth();
	}

	public int getControlHeight() {
		return this.getHeight();
	}

	public Image image(int width, int height) {
		final Object old = androidGraphics.getGraphics();
		Image i = new Image(width, height);
		Canvas c = new Canvas(i.bitmap);
		androidGraphics.setGraphics(c);
		chart.paint(androidGraphics, width, height);
		androidGraphics.setGraphics(old);
		return i;
	}
	
//	private double x0Down = -1;
//	private double y0Down = -1;
//	private double x1Down = -1;
//	private double y1Down = -1;
	private AllAxisSavedScales multiTouchSavedAxes;
	
	private PointDouble[] touchPoints0;
	private PointDouble[] touchPoints1;

	private Point upPointer;
	private int upActionIndex;
	
	private void resetView()
	{
		if (multiTouchSavedAxes != null)
        {
          chart.restoreScales(multiTouchSavedAxes);
          multiTouchSavedAxes = null;
        }
	}

	private class GestureListener extends GestureDetector.SimpleOnGestureListener {

        @Override
        public boolean onDown(MotionEvent e) {
            return true;
        }
        
        // event when double tap occurs
        @Override
        public boolean onDoubleTap(MotionEvent e) {
        	
        	if (chart.getZoom().getZoomReset() == ZoomResetStyle.DOUBLETAP)
              resetView();
            return true;
        }
        
        @Override
        public void onLongPress(MotionEvent event) {
        	if (chart.getZoom().getZoomReset() == ZoomResetStyle.LONGPRESS)
        	  resetView();
        }

    }

	private void doDown_Classic(MotionEvent event) {
		int numAxes = 4 + chart.getAxes().getCustom().size();

		touchPoints0 =  new PointDouble[numAxes];

		for (int i = 0; i < numAxes;i++) {

			Axis a;

			if (i < 4)
				a = chart.getAxes().getAxis(i);
			else
				a = chart.getAxes().getCustom().getAxis(i-4); //add in allowance to skip depth axes

			touchPoints0[i] = new PointDouble();

			if (a.getHorizontal())
				touchPoints0[i].x = a.calcPosPoint((int) event.getX(0));
			else
				touchPoints0[i].y = a.calcPosPoint((int) event.getY(0));
			//x0Down = chart.getAxes().getBottom().calcPosPoint((int) event.getX(0));
			//y0Down = chart.getAxes().getLeft().calcPosPoint((int) event.getY(0));
		}

		if (multiTouchSavedAxes == null)
			multiTouchSavedAxes = chart.saveScales();

		boolean rotating = false;

		for (int t = 0; t < chart.getTools().size(); t++) {
			Tool s = chart.getTools().getTool(t);
			if (s.getActive()) {
				if (s.getClass() == Rotate.class) {
					rotating = true;
					break;
				}
			}
		}

		if (rotating) {
			// faciltates start point for rotate if tool is present
			FrameworkMouseEvent e = new FrameworkMouseEvent(
					FrameworkMouseEvent.BUTTON1, 0 /* event.index */,
					FrameworkMouseEvent.MOUSE_PRESSED,
					(int) event.getX(0), (int) event.getY(0));

			chart.broadcastMouseEvent(e);
		}
	}

	private void doPointerDown(MotionEvent event) {
		int numAxes = 4 + chart.getAxes().getCustom().size();

		if (multiTouchSavedAxes == null)
			multiTouchSavedAxes = chart.saveScales();

		touchPoints0 = new PointDouble[numAxes];
		touchPoints1 = new PointDouble[numAxes];

		if (event.getSize() > 0) {

			for (int i = 0; i < (numAxes); i++) {

				Axis a;

				if (i < 4)
					a = chart.getAxes().getAxis(i);
				else
					a = chart.getAxes().getCustom().getAxis(i-4); //add in allowance to skip depth axes

				touchPoints0[i] = new PointDouble();
				touchPoints1[i] = new PointDouble();

				if (event.getX(0) < event.getX(1)) {
					touchPoints0[i].x = a.calcPosPoint((int) event.getX(0));
					touchPoints1[i].x = a.calcPosPoint((int) event.getX(1));
				} else {
					touchPoints0[i].x = a.calcPosPoint((int) event.getX(1));
					touchPoints1[i].x = a.calcPosPoint((int) event.getX(0));
				}

				if (event.getY(0) < event.getY(1)) {
					touchPoints0[i].y = a.calcPosPoint((int) event.getY(0));
					touchPoints1[i].y = a.calcPosPoint((int) event.getY(1));
				} else {
					touchPoints0[i].y = a.calcPosPoint((int) event.getY(1));
					touchPoints1[i].y = a.calcPosPoint((int) event.getY(0));
				}
			}
		}
	}

	private void doMove_FullChart(MotionEvent event) {
		if (event.getPointerCount() > 1) {
			double newDistance = Math.hypot(
					event.getX(1) - event.getX(0), event.getY(1)
							- event.getY(0));

			if ((zoomDistance != 0) && (newDistance != zoomDistance)) {
				int zoom = chart.aspect.getZoom();

				zoom += Math.round(0.15 * (newDistance - zoomDistance));

				if (zoom < 1)
					zoom = 1;

				chart.aspect.setView3D(true);
				chart.aspect.setZoom(zoom);
			}

			zoomDistance = newDistance;
		} else {
			chart.zoom.setActive(false);
			FrameworkMouseEvent e = new FrameworkMouseEvent(
					FrameworkMouseEvent.BUTTON1, 0 /* event.index */,
					FrameworkMouseEvent.MOUSE_MOVED,
					(int) event.getX(), (int) event.getY());
			processMouseMotionEvent(e);
		}
	}

	private void doMove_Inchart(MotionEvent event) {
		if (event.getSize() > 1) {
			final double newDistance = Math.hypot(
					event.getHistoricalX(1) - event.getHistoricalX(0),
					event.getHistoricalY(1) - event.getHistoricalY(0));

			if ((zoomDistance != 0) && (newDistance != zoomDistance)) {
				int zoom = chart.aspect.getZoom();

				zoom += (int) (0.5 * (newDistance - zoomDistance));

				if (zoom < 1)
					zoom = 1;

				chart.aspect.setZoom(zoom);
			}

			zoomDistance = newDistance;

		} else {
			FrameworkMouseEvent e = new FrameworkMouseEvent(
					FrameworkMouseEvent.BUTTON1, 0 /* event.index */,
					FrameworkMouseEvent.MOUSE_MOVED,
					(int) event.getX(), (int) event.getY());

			processMouseMotionEvent(e);
		}
	}

	private void doMove_Classic(MotionEvent event) {
		int numAxes = 4 + chart.getAxes().getCustom().size();

		if (event.getPointerCount() > 1) {

			double newDistance = Math.hypot(
					event.getX(1) - event.getX(0), event.getY(1)
							- event.getY(0));

			if ((zoomDistance != 0) && (newDistance != zoomDistance)) {

				// basic 4 axes plus custom
				for (int i = 0; i < (numAxes); i++) {

					Axis a;

					if (i < 4)
						a = chart.getAxes().getAxis(i);
					else
						a = chart.getAxes().getCustom().getAxis(i-4); //add in allowance to skip depth axes


							/*double xRatio = Math.abs((x1Down - x0Down)
								/ Math.abs((event.getX(1) - event.getX(0))));
							double yRatio = Math.abs((y1Down - y0Down)
								/ Math.abs((event.getY(1) - event.getY(0))));*/

					double xRatio = Math.abs((touchPoints1[i].x - touchPoints0[i].x)
							/ Math.abs((event.getX(1) - event.getX(0))));
					double yRatio = Math.abs((touchPoints1[i].y - touchPoints0[i].y)
							/ Math.abs((event.getY(1) - event.getY(0))));

					if (!a.getHorizontal()) {

						float lMin = a.calcYPosValue(a.getMinimum());
						float lMax = a.calcYPosValue(a.getMaximum());

						a.setMinMax(
								touchPoints1[i].y
										- ((lMin - event.getY(1)) * yRatio),
								touchPoints0[i].y
										+ ((event.getY(0) - lMax) * yRatio));
					} else {
						float bMin = a.calcXPosValue(a.getMinimum());
						float bMax = a.calcXPosValue(a.getMaximum());

						a.setMinMax(
								touchPoints0[i].x
										- ((event.getX(0) - bMin) * xRatio),
								touchPoints1[i].x
										+ ((bMax - event.getX(1)) * xRatio));
					}
				}
			}

			zoomDistance = newDistance;
		}
		else if (event.getPointerCount() == 1) {
			if (event.getHistorySize() > 0) {

				boolean rotating = false;

				for (int t = 0; t < chart.getTools().size(); t++) {
					Tool s = chart.getTools().getTool(t);
					if (s.getActive()) {
						if (s.getClass() == Rotate.class) {
							rotating = true;
							break;
						}
					}
				}

				if (rotating) {
					FrameworkMouseEvent e = new FrameworkMouseEvent(
							FrameworkMouseEvent.BUTTON1,
							0 /* event.index */,
							FrameworkMouseEvent.MOUSE_MOVED,
							(int) event.getX(0), (int) event.getY(0));

					chart.broadcastMouseEvent(e);
				} else {
					this.onMouseMove(event);
				}
			}

			invalidate();
		}
	}

	private void onMouseMove(MotionEvent event) {
		chart.setCancelMouse(false);
		Cursor c = getTeeCursor();

		FrameworkMouseEvent e = new FrameworkMouseEvent(
				FrameworkMouseEvent.BUTTON1, 0 /* event.index */,
				FrameworkMouseEvent.MOUSE_MOVED,
				(int) event.getX(), (int) event.getY());

		chart.broadcastMouseEvent(e, c);

		Point movePointer = new Point(e.getX(), e.getY());

		if ((upPointer != null) && (upActionIndex == event.getActionIndex())) {
			this.getPanning().activate(movePointer.getX(), movePointer.getY());
		}

		chart.doMouseMove(movePointer.getX(), movePointer.getY(), e, c);
		upPointer = null;
		this.setCursor(c);
		super.onTouchEvent(event);
	}

	public boolean onTouch(View v, MotionEvent event) {
		gestureDetector.onTouchEvent(event);

		boolean handled = false;

		switch (event.getAction()) {
			case MotionEvent.ACTION_DOWN: {
				if ((chart.getZoom().getZoomStyle() == ZoomStyle.INCHART_MULTI) ||
				   (chart.getZoom().getZoomStyle() == ZoomStyle.CLASSIC)) {
					doDown_Classic(event);
				}
				triggerEvent(event, FrameworkMouseEvent.MOUSE_PRESSED);
				handled = true;
				break;
			}
			case MotionEvent.ACTION_POINTER_2_DOWN: // multitouch down
			case MotionEvent.ACTION_POINTER_DOWN: {
				doPointerDown(event);
				handled = true;
				break;
			}
			case MotionEvent.ACTION_POINTER_UP: { // multitouch up
				if (event.getPointerCount() == 2) {
					upActionIndex = event.getActionIndex();
					upPointer = new Point((int)event.getX(upActionIndex), (int)event.getY(upActionIndex));
				}
				handled = true;
				break;
			}
			case MotionEvent.ACTION_MOVE: {
				switch (chart.getZoom().getZoomStyle()) {
					case FULLCHART:
						doMove_FullChart(event);
						handled = true;
					break;
					case INCHART:
						doMove_Inchart(event);
						handled = true;
					break;
					case INCHART_MULTI:
					case CLASSIC:
						doMove_Classic(event);
						handled = true;
					break;
				}
				break;
			}
			case MotionEvent.ACTION_UP: {
				zoomDistance = 0;

				triggerEvent(event, FrameworkMouseEvent.MOUSE_RELEASED);

				handled = true;
				break;
			}
		}
		return handled;
	}

	GestureDetector gestureDetector = new GestureDetector(getContext(), new GestureListener());

	public boolean onTrackballEvent(MotionEvent event) {
		return onTouch(this, event);
	}

	private void triggerEvent(MotionEvent event, int type) {
		FrameworkMouseEvent e = new FrameworkMouseEvent(
				FrameworkMouseEvent.BUTTON1, 0 /* event.index */, type,
				(int) event.getX(), (int) event.getY());

		processMouseEvent(e);
	}

	public boolean invertedRotation() {
		return chart.invertedRotation;
	}

	public boolean isDisposing() {
		// TODO Auto-generated method stub
		return false;
	}

	public void setDisposing(boolean value) {
		// TODO Auto-generated method stub

	}
}
