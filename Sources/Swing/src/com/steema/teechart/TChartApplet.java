package com.steema.teechart;

import java.applet.Applet;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;

import java.awt.Point;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.ClipboardOwner;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.event.MouseEvent;
import com.steema.teechart.events.FrameworkMouseEvent;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.Dimension;
import java.awt.Color;
import java.awt.Image;
import java.awt.Toolkit;

import java.awt.AWTEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.beans.Beans;

import java.net.URL;
import java.net.MalformedURLException;

import com.steema.teechart.axis.Axis;
import com.steema.teechart.styles.Series;
import com.steema.teechart.drawing.Graphics3D;
import com.steema.teechart.imports.Imports;
import com.steema.teechart.tools.ToolsCollection;
import com.steema.teechart.drawing.IGraphics3D;
import com.steema.teechart.exports.Exports;
import com.steema.teechart.styles.SeriesCollection;
import com.steema.teechart.axis.Axes;
import com.steema.teechart.axis.AxisLabelAdapter;
import com.steema.teechart.axis.AxisLabelResolver;
import com.steema.teechart.legend.Legend;
import com.steema.teechart.misc.Utils;
import com.steema.teechart.tools.Tool;
import com.steema.teechart.editors.ChartEditor;
import com.steema.teechart.legend.LegendAdapter;
import com.steema.teechart.legend.LegendResolver;
import com.steema.teechart.drawing.Graphics3DAWT;

/**
 *
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
public class TChartApplet extends Applet implements Serializable, IChart,
        MouseListener, MouseMotionListener, ClipboardOwner {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Chart chart;

    public TChartApplet() {
        super();

        chart = new Chart();
        chart.parent = this;

        checkGraphics();
        doSetBuffered(true);
        setOpaque(true);

        enableEvents(AWTEvent.MOUSE_EVENT_MASK);
        enableEvents(AWTEvent.MOUSE_MOTION_EVENT_MASK);
        enableEvents(AWTEvent.MOUSE_WHEEL_EVENT_MASK);
        enableEvents(AWTEvent.COMPONENT_EVENT_MASK);
    }

    public Dimension getPreferredSize() {
        return new Dimension(400, 250);
    }

    public boolean isDoubleBuffered() {
        return true;
    }

    public void doDrawImage(IGraphics3D g) {
        // Empty !
    }
    
    public boolean drawZoomOnCachedImage() {
    	return false;
    }

    public void setOpaque(boolean value) {
        // Empty! defined in IChart
    }

    public Chart getChart() {
        return chart;
    }

    /**
     * Clean up any resources being used.
     *
     * @param disposing boolean
     */
    public void dispose() {
       chart.setAutoRepaint(false);
       chart.removeAllComponents();
    }

    public void checkBackground(Object sender, FrameworkMouseEvent e) {}

    public void checkTitle(Title aTitle, FrameworkMouseEvent e, ClickedParts c) {}

    public void doAfterDraw() {}

    public void doAfterDrawSeries() {}

    public void doAllowScroll(Axis axis, double delta,
                              ScrollResult result) {
    }

    public void doBeforeDraw() {}

    public void doBeforeDrawAxes() {}

    public void doBeforeDrawSeries() {}

    public void doChartPrint() {}

    public void doClickAxis(Axis a, FrameworkMouseEvent e) {}

    public void doClickLegend(Legend legend, FrameworkMouseEvent e) {}

    public void doClickSeries(Object sender, Series s, int valueIndex,
                              FrameworkMouseEvent e) {
    }

    transient private final static AxisLabelResolver defaultAxisLabelResolver = new
            AxisLabelAdapter();

    public AxisLabelResolver getAxisLabelResolver() {
        return defaultAxisLabelResolver;
    };

    transient private final static LegendResolver defaultLegendResolver = new
            LegendAdapter();

    public LegendResolver getLegendResolver() {
        return defaultLegendResolver;
    };

    public void doScroll(Object sender) {}

    public void doSetBuffered(boolean value) {}

    public void doUnZoomed(Object sender) {}

    public void doZoomed(Object sender) {}

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

    public Cursor getTeeCursor() {
        return Cursors.toCursor(super.getCursor());
    }

    public void doInvalidate() {
        super.repaint();
    }

    public void setChart(IChart c) {
        chart.parent = this;
    }

    public Container getControl() {
        return this;
    }
    
    public int getControlHeight() {
        return this.getHeight();
    }

    public int getControlWidth() {
        return this.getWidth();
    }

    public Point pointToScreen(Point p) {
        Point screen = getLocationOnScreen();
        p.move( -screen.x, -screen.y);
        return p;
    }

    public void refreshControl() {
        /** TODO VERIFY ! */
        doInvalidate();
    }

    public void init() {
        addMouseListener(this);
        addMouseMotionListener(this);
        setLayout(null);
        setSize(400, 250);
    }

    private Image buffer;
    private Graphics bufferg;

    private Graphics getBufferGraphics() {
        if (buffer != null) {
            if ((buffer.getWidth(null) != getWidth()) ||
                (buffer.getHeight(null) != getHeight())) {
                buffer = null;
                bufferg = null;
            }
        }

        if (buffer == null) {
            buffer = createImage(getWidth(), getHeight());
        }

        if (bufferg == null) {
            bufferg = buffer.getGraphics();
        }

        return bufferg;
    }

    public Graphics3D checkGraphics() {

        Graphics3D g3D = (Graphics3D) getGraphics3D();

        if (g3D == null) {
            g3D = new Graphics3DAWT(chart);
            chart.setGraphics3D(g3D);
        }

        //if (g3D instanceof Graphics3DAWT) {
        //    g3D.setGraphics((Graphics2D) getControl().getGraphics());
        //}

        return g3D;
    }

    public void paint(Graphics g) {
        if (chart.getAutoRepaint()) {

            prepareGraphics();

            chart.paint(chart.getGraphics3D(),
                        new Rectangle(getX(), getY(), getWidth(), getHeight()));

            g.drawImage(buffer, 0, 0, this);
        }

        //super.paint(g);
    }

    public void update(Graphics g) {
        paint(g);
        //super.update(g);
    }

    /**
     * Shows the TeeChart editor dialog.<br>
     * Displays the Chart Editor at runtime opening the Editor at the Series
     * page of the SeriesIndex Series.
     */
    public void showEditor() {
        ChartEditor.editChart(chart, getName());
        chart.setCancelMouse(true);
    }

    /**
     * Start the applet.
     */
    public void start() {
        //showStatus("start");

        String source = getParameter("XMLSOURCE");

        if (source != null) {
            try {
                URL sourceURL = new URL(getDocumentBase(), source);

                try {
                    chart.getImport().getTemplate().fromXML(sourceURL.
                            openStream());
                } catch (IOException ex) {
                    chart.getHeader().setText("Cannot read XML file from: " +
                                              sourceURL.toString());
                }
            } catch (MalformedURLException ex) {
                ex.printStackTrace();
            }
        }
    }

    /**
     * Stop the applet.
     */
    public void stop() {
        //showStatus("stop");
    }

    /**
     * Destroy the applet.
     */
    public void destroy() {
    }

    public String getAppletInfo() {
        return "Steema.com TeeChart Applet";
    }

    public String[][] getParameterInfo() {
        String[][] info = { {"source", "URL", "TeeChart file"}
        };
        return info;
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

    public Color getBackColor() {
        return getBackground();
    }

    /**
     * Obsolete.&NBSP;Please use Graphics3D property.
     *
     * @return IGraphics3D
     */
    public IGraphics3D getCanvas() {
        return getGraphics3D();
    }

    public void setBackColor(Color value) {
        setBackground(value);
    }

    /**
     * Obsolete.&nbsp;Please use Graphics3D property.
     *
     * @param value Graphics3D
     */
    public void setCanvas(Graphics3D value) {
        setGraphics3D(value);
        value.invalidate();
    }

    /**
     * Access TeeChart Draw methods.
     *
     * @return IGraphics3D
     */
    public IGraphics3D getGraphics3D() {
        return chart.getGraphics3D();
    }

    /**
     * Access TeeChart Draw methods.
     *
     * @param value Graphics3D
     */
    public void setGraphics3D(Graphics3D value) {
        chart.setGraphics3D(value);
    }

    /**
     * Collection of Series contained in this Chart.
     *
     * @return SeriesCollection
     */
    public SeriesCollection getSeries() {
        return chart.series;
    }

    public Tool addTool(int toolClass) {
        Tool tmp = null;

        try {
            tmp = chart.getTools().add(Utils.toolTypesOf[toolClass]);

        } catch (IllegalAccessException ex) {
            ex.printStackTrace();
        } catch (InstantiationException ex) {
            ex.printStackTrace();
        }

        return tmp;
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
     * Accesses wall characteristics of the Chart.
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
     * Accesses Chart export methods.
     *
     * @return Exports
     */
    public Exports getExport() {
        return chart.getExport();
    }

    /**
     * Accesses Chart import and methods.
     *
     * @return Imports
     */
    public Imports getImport() {
        return chart.getImport();
    }

    public void clear() {
        getSeries().clear();
        getTools().clear();
    }

    public Series addSeries(int seriesClass) {
        Series tmp = null;

        try {
            tmp = Series.newFromType(Utils.seriesTypesOf[seriesClass]);
            tmp.setChart(this.chart);

        } catch (IllegalAccessException ex) {
            ex.printStackTrace();
        } catch (InstantiationException ex) {
            ex.printStackTrace();
        }

        return tmp;
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
     * Default indexer.<br>
     *
     * @param index int
     * @param value Series
     */
    public void setSeries(int index, Series value) {
        chart.series.setSeries(index, value);
    }

    public void setToolTip(ToolTip tool, String text) {
    }

    public boolean checkClickSeries()
    {
      for (int i=0; i<getSeries().size(); i++)
        if (getSeries(i).hasClickEvents())
          return true;
      return false;
    }

    protected void processMouseEvent(MouseEvent e) {
        chart.cancelMouse = false;

        FrameworkMouseEvent fe = getFrameworkEvent(e);
        
        prepareGraphics();

        if (e.getID() == MouseEvent.MOUSE_PRESSED) {
            chart.mousePressed(fe);
        } else
        if (e.getID() == MouseEvent.MOUSE_RELEASED) {
            chart.mouseReleased(fe);
        } else
        if (e.getID() == MouseEvent.MOUSE_EXITED) {
            mouseExited(fe);
        }

        if (!chart.cancelMouse) {
            super.processMouseEvent(fe);
        }
    }

    protected void processMouseMotionEvent(MouseEvent e) {
        chart.cancelMouse = false;
        
        FrameworkMouseEvent fe = getFrameworkEvent(e);

        prepareGraphics();

        Cursor c = chart.mouseMoved(fe);

        if (c == null) {
            if (chart.originalCursor == null) {
                setCursor(Cursors.DEFAULT);
            }

            setSuperCursor(chart.originalCursor);
        } else {
            setSuperCursor(c);
        }

        if (!chart.cancelMouse) {
            super.processMouseMotionEvent(fe);
        }
    }

    private void setSuperCursor(Cursor c) {
        if (c != null) {
            int tmp = c.getType();
            if ((tmp == Cursor.CUSTOM_CURSOR) || (tmp != getCursor().getType())) {
                super.setCursor(Cursors.fromCursor(c));
            }
        }
    }

    private void prepareGraphics() {
        checkGraphics().setGraphics(getBufferGraphics());
    }

    private FrameworkMouseEvent getFrameworkEvent(MouseEvent e)
    {
      return new FrameworkMouseEvent(e.getComponent(),e.getID(),
                                      e.getWhen(),e.getModifiers(),
                                      e.getX(),e.getY(),e.getClickCount(),
                                      true,e.getButton());
    }
    
    public void mouseEntered(MouseEvent event) {
    }

    public void mouseExited(MouseEvent event) {
        // Cursor=originalCursor;
        chart.getPanning().setActive(false);
        setCursor(chart.originalCursor);
    }

    public void mousePressed(MouseEvent event) {
        chart.mousePressed(getFrameworkEvent(event));
    }

    public void mouseReleased(MouseEvent event) {
        chart.mouseReleased(getFrameworkEvent(event));
    }

    public void mouseClicked(MouseEvent event) {
        chart.mousePressed(getFrameworkEvent(event));
    }

    public void mouseDragged(MouseEvent e) {
    }

    public void mouseMoved(MouseEvent e) {
        chart.mouseMoved(getFrameworkEvent(e));
    }

	
	public com.steema.teechart.drawing.Point pointToScreen(
			com.steema.teechart.drawing.Point p) {
		// TODO Auto-generated method stub
		return null;
	}

	
	public boolean isDesignTime() {
		return Beans.isDesignTime();
	}

	
	public void timerExec(int time, Runnable runnable) {
		// TODO Auto-generated method stub
		// display.timerExec
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
		// TODO Auto-generated method stub
		return null;
	}

	
	public int getScreenHeight() {
		return Toolkit.getDefaultToolkit().getScreenSize().height;
	}

	
	public int getScreenWidth() {
		return Toolkit.getDefaultToolkit().getScreenSize().width;
	}

	
	public void lostOwnership(Clipboard clipboard, Transferable contents) {
	}

	
	public void setCursor(Cursor c) {
		// TODO Auto-generated method stub
		
	}

	
	public com.steema.teechart.drawing.Image image(int width, int height) {
		com.steema.teechart.drawing.Image i=new com.steema.teechart.drawing.Image(width,height);
		Graphics3DAWT g=new Graphics3DAWT(chart);
		g.setGraphics(i.getGraphics());
		chart.paint(g);
		return i;
	}

	
	public boolean invertedRotation() {
		return chart.invertedRotation;
	}


        transient protected AxisLabelResolver axisLabelResolver =
                defaultAxisLabelResolver;

        public void setAxisLabelResolver(AxisLabelResolver resolver) {
            this.axisLabelResolver = resolver;
        }

        public void removeAxisLabelResolver() {
            this.axisLabelResolver = defaultAxisLabelResolver;
        }

		public void doMouseUp(FrameworkMouseEvent e) { }
		
		public void doMouseMove(FrameworkMouseEvent e) { }
}
