package com.steema.teechart;

import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.ClipboardOwner;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.beans.Beans;
import java.beans.ExceptionListener;
import java.beans.XMLDecoder;
import java.io.InputStream;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;

import com.steema.teechart.axis.Axes;
import com.steema.teechart.axis.Axis;
import com.steema.teechart.axis.AxisLabelAdapter;
import com.steema.teechart.axis.AxisLabelResolver;
import com.steema.teechart.drawing.ChartPen;
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
import com.steema.teechart.events.SeriesMouseEvent;
import com.steema.teechart.events.SeriesMouseListener;
import com.steema.teechart.exports.Exports;
import com.steema.teechart.imports.Imports;
import com.steema.teechart.legend.Legend;
import com.steema.teechart.legend.LegendAdapter;
import com.steema.teechart.legend.LegendResolver;
import com.steema.teechart.printer.Printer;
import com.steema.teechart.styles.Series;
import com.steema.teechart.styles.SeriesCollection;
import com.steema.teechart.swt.Graphics3DSWT;
import com.steema.teechart.tools.Tool;
import com.steema.teechart.tools.ToolsCollection;

/**
 * <p>
 * Title: TeeChart for Standard Widget Toolkit
 * </p>
 * 
 * <p>
 * Description: TeeChart for Standard Widget Toolkit
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
public class TChart extends Composite implements IChart, ClipboardOwner {

	private Chart chart;
	private Graphics3DSWT swtGraphics;
	private Display display; // = this.getDisplay();
	transient private EventListenerList listenerList = new EventListenerList();

	/*
	 * public TChart() { super(); //super(parent,0); //this(null, 0); }
	 */

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
				((SeriesMouseListener) listeners[i + 1]).seriesClicked(seriesClickEvent);
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
						((ChartPaintListener) listeners[i + 1]).chartPainting(e);
					} else if (e.getID() == ChartDrawEvent.PAINTED) {
						if (!isDisposing())
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
						((ChartPaintListener) listeners[i + 1]).seriesPainting(e);
					} else if (e.getID() == ChartDrawEvent.PAINTED) {
						((ChartPaintListener) listeners[i + 1]).seriesPainted(e);
					}
					break;
				}
				}
			}
		}
	}
	
    protected boolean fireChartMouseUp(ChartMouseEvent e) {
        boolean tmpClicked = false;
        Object[] listeners = listenerList.getListenerList();
        for (int i = listeners.length - 2; i >= 0; i -= 2) {
            if (listeners[i] == ChartMouseListener.class) {
                if (e.getClickedPart().getValue() == 0) { //NONE
                    tmpClicked = true;
                    ((ChartMouseListener) listeners[i + 1]).mouseUp(e);
                }
            }
        }
        return tmpClicked;
    }

	protected void fireChartPrint(ChartDrawEvent ce) {
		Object[] listeners = listenerList.getListenerList();
		for (int i = listeners.length - 2; i >= 0; i -= 2) {
			if (listeners[i] == ChartPrintListener.class) {
				((ChartPrintListener) listeners[i + 1]).chartPrinted(ce);
			}
		}
	}
	
	private Listener mouseMoveListener;
	private Listener mouseDownListener;
	private Listener mouseUpListener;
	
	private PaintListener paintListener;
	private boolean _IsDisposing = false;
	
	public boolean isDisposing()
	{
		return _IsDisposing;
	}
	
	public void setDisposing(boolean value)
	{
		_IsDisposing = value;
	}
	
	public void dispose()
	{
	  super.dispose();
	  _IsDisposing = true;
	  
	  if (workingCursor != null) workingCursor.dispose();
	  
      this.getTools().dispose();
	  
	  if (chartImage!=null) chartImage.dispose();
	  _IsDisposing = false;
 
	}
	

	public TChart(Composite parent, int style) {
		super(parent, style | org.eclipse.swt.SWT.DOUBLE_BUFFERED);

		chart = new Chart(this);

		chart.setParent(this);

		swtGraphics = new Graphics3DSWT(chart);
		chart.setGraphics3D(swtGraphics);

		addDisposeListener(new DisposeListener() {
			public void widgetDisposed(DisposeEvent e) {
				if (mouseMoveListener !=null)
				  getDisplay().removeFilter(SWT.MouseMove,mouseMoveListener);
				if (mouseDownListener !=null)
				  getDisplay().removeFilter(SWT.MouseDown,mouseDownListener);
				if (mouseUpListener !=null)
				  getDisplay().removeFilter(SWT.MouseUp,mouseUpListener);
				if (paintListener != null)
					removePaintListener(paintListener);
				
			}
		});

		paintListener = new PaintListener() {

			public void paintControl(PaintEvent e) {
				paintChart(e);
			}
		};
		
		addPaintListener(paintListener);
		
		mouseMoveListener = new Listener() {
			public void handleEvent(Event event) {
				// System.out.println("mouse move in SWT");
				if (event.widget == chart.getChart() || event.widget == chart.parent) {
					FrameworkMouseEvent e = new FrameworkMouseEvent(event.button, event.index, event.type, event.x, event.y);
					processMouseMotionEvent(e);
				}
			}
		};
				
		mouseDownListener = new Listener() {
			public void handleEvent(Event event) {
				// System.out.println("mouse down in SWT");
				if (event.widget == chart.getChart() || event.widget == chart.parent) {
					FrameworkMouseEvent e = new FrameworkMouseEvent(event.button, event.index, event.type, event.x, event.y);
					processMouseEvent(e);
				}
			}
		};
		
		mouseUpListener = new Listener() {
			public void handleEvent(Event event) {
				// System.out.println("mouse up in SWT");
				if (event.widget == chart.getChart() || event.widget == chart.parent) {
					FrameworkMouseEvent e = new FrameworkMouseEvent(event.button, event.index, event.type, event.x, event.y);
					processMouseEvent(e);
				}
			}
		};
		
		getDisplay().addFilter(SWT.MouseMove,mouseMoveListener);
		getDisplay().addFilter(SWT.MouseDown,mouseDownListener);
		getDisplay().addFilter(SWT.MouseUp,mouseUpListener);
		
	}

	private org.eclipse.swt.graphics.Image chartImage;

	org.eclipse.swt.graphics.GC gc;
	
	public void paint(Rectangle r, PaintEvent e) {

		Graphics3DSWT g3d;

		if (swtGraphics == null)
			g3d = new Graphics3DSWT(chart);
		else
			g3d = swtGraphics;

		if (chart.isDirty == true) {
			if (chartImage != null) {
				transferBitmap(e.gc);
			}
			
			if (chartImage != null) chartImage.dispose();
			chartImage = new org.eclipse.swt.graphics.Image(display, r.width, r.height); // ok

			if (gc != null) gc.dispose();
			gc = new org.eclipse.swt.graphics.GC(chartImage);

			g3d.setGraphics(gc);
			chart.setGraphics3D(g3d);

			chart.paint(g3d, r.width, r.height);
			gc.dispose();

			chart.isDirty = false;
		}
	}

	public void transferBitmap(org.eclipse.swt.graphics.GC g) {
		swtGraphics.g = g;
		// chart.getGraphics3D().setGraphics(swtGraphics);

		if (chartImage != null)
			g.drawImage(chartImage, 0, 0);
	}

	private void paintChart(PaintEvent e) {
		if ((chartImage != null) && (!chart.isDirty))
			transferBitmap(e.gc);
		else
			paint(getBounds(), e);
	}

	// IChart
	public Cursor getTeeCursor() {
		// super.getCursor();
		return new Cursor(0);
	}

	// IChart
	public org.eclipse.swt.graphics.Cursor getSWTCursor() {
		// super.getCursor();
		// TODO MM
		if (display != null)
			display.dispose();
		display = new org.eclipse.swt.widgets.Display();
		return new org.eclipse.swt.graphics.Cursor(display, 0);
	}

	public void doBeforeDrawAxes() {
		fireChartPaint(new ChartDrawEvent(this, ChartDrawEvent.PAINTING, ChartDrawEvent.AXES));
	}

	public void doAfterDrawSeries() {
		fireChartPaint(new ChartDrawEvent(this, ChartDrawEvent.PAINTED, ChartDrawEvent.SERIES));
	}

	public void doBeforeDrawSeries() {
		fireChartPaint(new ChartDrawEvent(this, ChartDrawEvent.PAINTING, ChartDrawEvent.SERIES));
	}

	public void doAfterDraw() {
		fireChartPaint(new ChartDrawEvent(this, ChartDrawEvent.PAINTED, ChartDrawEvent.CHART));
	}

	public void doBeforeDraw() {
		fireChartPaint(new ChartDrawEvent(this, ChartDrawEvent.PAINTING, ChartDrawEvent.CHART));
	}

	public void doClickSeries(Object sender, Series s, int valueIndex, FrameworkMouseEvent e) {
		fireSeriesClick(new SeriesMouseEvent(s, SeriesMouseEvent.SERIES_CLICKED, valueIndex, e));
		/*
		 * if (clickSeries != null) { clickSeries.invoke(sender, s, valueIndex, e); }
		 */
	}

	public void doAllowScroll(Axis a, double delta, ScrollResult result) {
		result.allow = true;
		if (scrollable != null) {
			result = scrollable.isScrollable(a, result);
		}
	}
	
	private org.eclipse.swt.graphics.Cursor workingCursor = null;

	public void setCursor(Cursor cursor) {
		Control c = this;
		
		if (c.getCursor()!=null) c.getCursor().dispose();
		
		c.setCursor(getSWTCursor(cursor));
	}

	private org.eclipse.swt.graphics.Cursor getSWTCursor(Cursor cursor) {
		
		if (workingCursor != null) workingCursor.dispose();
		
		switch (cursor.getType()) {
		case 1: {
			workingCursor = new org.eclipse.swt.graphics.Cursor(display, SWT.CURSOR_HAND);
			break;}
		case 2: {
			workingCursor = new org.eclipse.swt.graphics.Cursor(display, SWT.CURSOR_CROSS);
			break;}
		case 3: {
			workingCursor = new org.eclipse.swt.graphics.Cursor(display, SWT.CURSOR_SIZESW);
			break;}
		case 4: {
			workingCursor = new org.eclipse.swt.graphics.Cursor(display, SWT.CURSOR_SIZEW);
			break;}
		case 5: {
			workingCursor = new org.eclipse.swt.graphics.Cursor(display, SWT.CURSOR_SIZES);
			break;}
		case 6: {
			workingCursor = new org.eclipse.swt.graphics.Cursor(display, SWT.CURSOR_SIZEALL);
			break;}
		case 7: {
			workingCursor = new org.eclipse.swt.graphics.Cursor(display, SWT.CURSOR_IBEAM);
			break;}
		default: { 
			workingCursor = new org.eclipse.swt.graphics.Cursor(display, 0); }
		}
		
		return workingCursor;
	}

	public void setOpaque(boolean value) {
	}
	
	public void doMouseUp(FrameworkMouseEvent e) {
        if (fireChartMouseUp(new ChartMouseEvent(chart,
                ChartMouseEvent.MOUSE_UP, ClickedParts.NONE, e))) {
            chart.cancelMouse = true;
        }
	}

	public void setToolTip(ToolTip tooltip, String text) {
		tooltip.setText(text);
	}

	public void repaint() {  //B427
		if (!isDisposing())
		{
		  this.chart.isDirty = true;
		  this.redraw(); // redraw on next paint request 
		}
	}
	
	public void refreshControl()  //B427
	{
		if (!isDisposing())
		{
			this.chart.isDirty = true;
			this.redraw();
			this.update(); //force paint
		}
	}

	public void checkTitle(Title aTitle, FrameworkMouseEvent e, ClickedParts c) {
		if (fireChartClicked(new ChartMouseEvent(aTitle, ChartMouseEvent.MOUSE_CLICKED, c, e))) {
			chart.cancelMouse = true;
			chart.iClicked = chart.cancelMouse;
		}
		if (!chart.iClicked) {
			chart.checkZoomPanning(e);
		}
	}

	public void doClickLegend(Legend legend, FrameworkMouseEvent e) {
		if (fireChartClicked(new ChartMouseEvent(legend, ChartMouseEvent.MOUSE_CLICKED, ClickedParts.LEGEND, e))) {
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
		this.repaint();
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

	public Point pointToScreen(Point p) {
		return p;
	}

	public Chart getChart() {
		return chart;
	}

	/**
	 * Shows the TeeChart editor dialog.
	 * 
	 * Displays the Chart Editor at runtime opening the Editor at the Series page of the SeriesIndex Series.
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
	 * The Legend property determines the text and drawing attributes of Chart's textual representation of Series and Series values. <br>
	 * The Legend class draws a rectangle and for each Series in a Chart (or for each point in a Series) outputs a text representation of that Series (or that point). <br>
	 * You can use the Legend.LegendStyle and Legend.TextStyle properties to control the text used to draw the legend. <br>
	 * The Legend can be positioned at Left, Right, Top and Bottom chart sides using the Legend.Alignment property. <br>
	 * Use the Legend.Visible property to show / hide the Legend. <br>
	 * The Inverted property makes Legend to draw text starting from bottom.<br>
	 * The Frame, Font and Color properties allow you to change Legend appearance. <br>
	 * The Legend.ColorWidth property determines the percent width of each item's "colored" mark. <br>
	 * The Legend.FirstValue property controls which Series (or Series point) will be used to draw first Legend item. <br>
	 * 
	 * @return Legend
	 */
	public Legend getLegend() {
		return chart.getLegend();
	}

	/**
	 * 
	 * Defines the Text and formatting properties to be drawn at the top of the Chart.<br>
	 * Use Text to enter the desired Header lines, set Visible to True and change the Font, Frame and Brush methods.<br>
	 * Use Alignment to control text output position.
	 * 
	 * @return Header
	 */
	public Header getHeader() {
		return chart.getHeader();
	}

	/**
	 * Defines Text shown directly below Header.<br>
	 * Use the Text method to enter the desired SubHeader lines, set Visible to True and change the Font, Frame and Brush methods.<br>
	 * Use the Alignment method to control text output position.
	 * 
	 * @return Header
	 */
	public Header getSubHeader() {
		return chart.getSubHeader();
	}

	/**
	 * Determines the Foreground color.
	 * 
	 * @return Color
	 */
	public Color getForeground() {
		return super.getForeground();
	}

	/**
	 * Defines Text shown at the bottom of the Chart.<br>
	 * Use Text to enter the desired Footer lines, set Visible to True and change the Font, Frame and Brush methods.<br>
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
	 * Use Text to enter the desired SubFooter lines, set Visible to True and change the Font, Frame and Brush methods.<br>
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
		chart.getPanel().setColor(new com.steema.teechart.drawing.Color(value.getRed(), value.getGreen(), value.getBlue()));

		// Shouldn't be necessary to do this:
		// super.setBackground(value);
	}

	public void setBounds(com.steema.teechart.Rectangle value) {
		super.setBounds(value.getSWTRect());
	}

	/**
	 * The color the Chart rectangle is filled with.<br>
	 * The chart rectangle is the screen area between axes. <br>
	 * Setting Background to Color.EMPTY does not fill the rectangle. <br>
	 * 
	 * Assigning Color.EMPTY to tChart.getBackground() makes TeeChart NOT fill the Chart back area, so Gradient fills and Background Bitmaps can be shown.
	 * 
	 * @return Color
	 */
	public Color getBackground() {
		com.steema.teechart.drawing.Color c = chart.getPanel().getColor();
		return new Color(null, c.getRed(), c.getGreen(), c.getBlue());
	}

	public Point mousePosition = new Point();

	public void mouseDragged(FrameworkMouseEvent e) {
		mousePosition = e.getPoint();
	}

	/**
	 * @todo ANY WAY TO OBTAIN CURRENT MOUSE POSITION WITHOUT MOTION LISTENER ? --> MouseInfo
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
	 * Use AutoRepaint false to disable Chart repainting whilst, for example, adding a large number of points to a Chart Series. This avoids repainting of the Chart whilst the points are added. <br>
	 * AutoRepaint may be re-enabled, followed by a manual Repaint command when all points are added. <br>
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
	
	public Image getImage() {
		return chart.image(getWidth(), getHeight());
	}

	public void setHeight(int value) {
		setSize(getWidth(), value);
	}

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
	 * @param series1
	 *            int
	 * @param series2
	 *            int
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

	/**
	 * Returns background image of TChart
	 * 
	 * @return Image
	 */
	public org.eclipse.swt.graphics.Image getBackgroundImage() {

		// TODO converge image types
		if (backImage == null) {
			backImage = new Image(getWidth(), getHeight(), 0);
		}

		return super.getBackgroundImage();
	}

	public void doDrawImage(IGraphics3D g) {
		if (backImage != null) {
			g.draw(chart.getChartRect().x, chart.getChartRect().y, backImage);
		}
	}
	
	public boolean drawZoomOnCachedImage() {
    	return false;
    }

	public int getHeight() {
		return this.getBounds().height;
	}

	public int getWidth() {
		return this.getBounds().width;
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
		}
	}

	public void setChart(IChart value) {
		chart.setParent(this);
	}

	transient private final LegendResolver defaultLegendResolver = new LegendAdapter();

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
		fireChartPrint(new ChartDrawEvent(this, ChartDrawEvent.PRINTING, ChartDrawEvent.CHART));
	}

	public Graphics3D checkGraphics() {
		return swtGraphics;
	}

	/*
	 * public Graphics3D checkGraphics() { Graphics3D g3D = (Graphics3D) getGraphics3D();
	 * 
	 * if (g3D == null) { g3D = new Graphics3DSWT(chart); chart.setGraphics3D(g3D); }
	 * 
	 * if (g3D instanceof Graphics3DSWT) { g3D.setGraphics((java.awt.Graphics2D) getControl().getGraphics()); }
	 * 
	 * return g3D; }
	 */

	public void doClickAxis(Axis a, FrameworkMouseEvent e) {
		if (fireChartClicked(new ChartMouseEvent(a, ChartMouseEvent.MOUSE_CLICKED, ClickedParts.AXIS, e))) {
			chart.cancelMouse = true;
		}
	}

	public void checkBackground(Object sender, FrameworkMouseEvent e) {
		if (fireChartClicked(new ChartMouseEvent(sender, ChartMouseEvent.MOUSE_CLICKED, ClickedParts.CHARTRECT, e))) {
			chart.cancelMouse = true;
			chart.iClicked = chart.cancelMouse;
		}
	}
	
    public void doMouseMove(FrameworkMouseEvent e) {
        if (fireChartMouseMove(new ChartMouseEvent(chart,
                ChartMouseEvent.MOUSE_MOVE, ClickedParts.NONE, e))) {
            chart.cancelMouse = true;
        }
    }

	transient private final AxisLabelResolver defaultAxisLabelResolver = new AxisLabelAdapter();

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

	// private org.eclipse.swt.graphics.Image tmpImage;
	
	private org.eclipse.swt.graphics.GC prepareGraphics() {
		IGraphics3D g = chart.getGraphics3D();
		org.eclipse.swt.graphics.GC gc = new org.eclipse.swt.graphics.GC((org.eclipse.swt.widgets.Composite) (chart.getParent()));
		g.setGraphics(gc);
		transferBitmap(gc);
		return gc; ////gc.dispose();
	}

	protected void processMouseEvent(FrameworkMouseEvent e) {
		chart.cancelMouse = false;
		// chart.isDirty = true;

		// ((com.steema.teechart.swt.Graphics3DSWT)this.getGraphics3D()).g=event.display.;

		// if (!Beans.isDesignTime()) {
		org.eclipse.swt.graphics.GC tmpGC = prepareGraphics();

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
			if (getCursor()!=null) getCursor().dispose();
			setCursor(chart.originalCursor);
		}
		
		if (tmpGC != null)
			tmpGC.dispose();
		
		// }
		/*
		 * if (!chart.cancelMouse) { super.processMouseEvent(e); }
		 */
	}

	protected void processMouseMotionEvent(FrameworkMouseEvent e) {
		chart.cancelMouse = false;

		// if (!Beans.isDesignTime()) {
		org.eclipse.swt.graphics.GC tmpGC = prepareGraphics();
		Cursor c = chart.mouseMoved(e);

		if (c == null) {
			if (chart.originalCursor == null) {
				// setCursor(Cursor.getDefaultCursor());
				if (super.getCursor()!=null) super.getCursor().dispose();
				super.setCursor(null);
			}
			setSuperCursor(chart.originalCursor);
		} else {
			setSuperCursor(c);
		}
		
		if (tmpGC != null)
			tmpGC.dispose();
	}

	private void setSuperCursor(Cursor c) {
		if (super.getCursor()!=null) super.getCursor().dispose();
		super.setCursor(getSWTCursor(c));
	}

	public boolean checkClickSeries() {
		for (int i = 0; i < this.getSeriesCount(); i++)
			if (this.getSeries(i).hasClickEvents())
				return true;
		return false;
	}

	public boolean isDesignTime() {
		return Beans.isDesignTime();
	}

	public void timerExec(int time, Runnable runnable) {
		if (display == null)
			display = Display.getDefault();
		
		display.timerExec(time, runnable);
	}

	public void copyToClipboard(Object object) {
		Transferable t;

		if (object instanceof String)
			t = new StringSelection((String) object);
		else if (object instanceof Transferable)
			t = (Transferable) object;
		else
			return;

		Toolkit.getDefaultToolkit().getSystemClipboard().setContents(t, this);
	}

	public void lostOwnership(Clipboard arg0, Transferable arg1) {
		// TODO Auto-generated method stub

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

	/**
	 * Draws the Series "Legend" on the specified rectangle and Graphics.
	 * 
	 * @param g
	 *            Graphics
	 * @param r
	 *            Rectangle
	 */
	protected void paintLegend(Series series, Graphics g, Rectangle r) {
		/** @todo VERIFY ! */

		IBaseChart tmpChart = null;

		if (series.chart == null) {
			// tmpChart = new Chart();
			// tmpChart.setAutoRepaint(false);
			// setChart(tmpChart);
		} else {
			series.chart.setAutoRepaint(false);
		}

		try {
			// Graphics3D g3d = new Graphics3D(chart);
			// g3d.g = g;
			// DrawLegend(g3d, -1, r);
		} finally {
			if (series.chart == tmpChart) {
				series.setChart(null);
				// tmpChart.dispose();
			} else {
				series.getChart().setAutoRepaint(true);
			}
		}
	}

	public Object getGraphics() {
		return swtGraphics;
	}

	/**
	 * Returns the height, in pixels, of the Chart Panel.<br>
	 * It should be used when using Canvas methods to draw relative to the Panel height. <br>
	 * <b>Note:</B> You should <b>NOT</b> use TChart.Height.
	 * 
	 * 
	 * @return int
	 */
	public int getScreenHeight() {
		return Toolkit.getDefaultToolkit().getScreenSize().height;
	}

	/**
	 * Returns the width, in pixels, of the Chart Panel.
	 * 
	 * @return int
	 */
	public int getScreenWidth() {
		return Toolkit.getDefaultToolkit().getScreenSize().width;
	}

	Image tmpImg;
	Graphics3D tmpg3d;
	/**
	 * Chart exports to image format.
	 * 
	 * @param width
	 *            int
	 * @param height
	 *            int
	 * @return Image
	 */
	public Image image(int width, int height) {

		if (width <= 0) {
			width = 400;
		}
		if (height <= 0) {
			height = 300;
		}

		if (tmpImg != null){
		  if (tmpImg.data != null) tmpImg.data.dispose();
		  tmpImg = null;
		}
		tmpImg = new Image(width, height, this);
		if (tmpg3d != null) tmpg3d = null;
		tmpg3d = new Graphics3DSWT(chart);

		tmpg3d.setSmoothingMode(chart.getGraphics3D().getSmoothingMode());

		tmpg3d.setGraphics(new GC(tmpImg.data));
		chart.paint(tmpg3d, new com.steema.teechart.Rectangle(0, 0, width, height));

		return tmpImg;
	}

	public boolean invertedRotation() {
		return chart.invertedRotation;
	}
}
