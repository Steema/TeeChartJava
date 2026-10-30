package com.steema.teechart;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.steema.teechart.Zoom.ZoomStyle;
import com.steema.teechart.axis.AllAxisSavedScales;
import com.steema.teechart.axis.Axes;
import com.steema.teechart.axis.Axis;
import com.steema.teechart.axis.AxisLabelItem;
import com.steema.teechart.axis.AxisLabelStyle;
import com.steema.teechart.axis.AxisSavedScales;
import com.steema.teechart.axis.DepthAxis;
import com.steema.teechart.data.DataSeriesSource;
import com.steema.teechart.drawing.ChartBrush;
import com.steema.teechart.drawing.ChartFont;
import com.steema.teechart.drawing.ChartPen;
import com.steema.teechart.drawing.Color;
import com.steema.teechart.drawing.Graphics3D;
import com.steema.teechart.drawing.IGraphics3D;
import com.steema.teechart.drawing.Image;
import com.steema.teechart.drawing.Point;
import com.steema.teechart.drawing.StringAlignment;
import com.steema.teechart.events.ChartDrawEvent;
import com.steema.teechart.events.FrameworkMouseEvent;
import com.steema.teechart.events.ITeeEventListener;
import com.steema.teechart.events.TeeEvent;
import com.steema.teechart.events.TeeEventListeners;
import com.steema.teechart.events.TeeMouseEvent;
import com.steema.teechart.exports.Exports;
import com.steema.teechart.functions.Function;
import com.steema.teechart.imports.Imports;
import com.steema.teechart.languages.Language;
import com.steema.teechart.legend.Legend;
import com.steema.teechart.misc.ERMode;
import com.steema.teechart.misc.Utils;
import com.steema.teechart.printer.Printer;
import com.steema.teechart.styles.HorizontalAxis;
import com.steema.teechart.styles.Margins;
import com.steema.teechart.styles.MouseClicked;
import com.steema.teechart.styles.Series;
import com.steema.teechart.styles.SeriesCollection;
import com.steema.teechart.styles.SeriesEvent;
import com.steema.teechart.styles.SeriesEventStyle;
import com.steema.teechart.styles.ValueList;
import com.steema.teechart.styles.VerticalAxis;
import com.steema.teechart.themes.Theme;
import com.steema.teechart.tools.Tool;
import com.steema.teechart.tools.ToolAxis;
import com.steema.teechart.tools.ToolsCollection;

/**
 *
 * <p>Title: Chart class</p>
 *
 * <p>Description: Chart contents.</p>
 *
 * <p>Copyright (c) 2005-2019 by Steema Software SL. All Rights Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 *
 */
public class Chart extends TeeBase implements IBaseChart, Serializable,
        Cloneable {
	
	private static final long serialVersionUID = 1L;
	private Theme _internalTheme;

	/**
     *
     * <p>Title: Tooltips class</p>
     *
     * <p>Description: Tooltips</p>
     *
     * <p>Copyright (c) 2005-2019 by Steema Software SL. All Rights Reserved.</p>
     *
     * <p>Company: Steema Software SL</p>
     *
     */
    public final class ChartToolTip {

        private ToolTip tool;
        private IChart chartControl;
        private String text;

        public ChartToolTip(Chart c) {
            chartControl = c.parent;
            tool = new ToolTip(c);
            tool.setInitialDelay(500);
        }

        /**
         * The time lag before the Tool Tip appears.<br>
         * Default value: 500
         *
         * @return int
         */
        public int getInitialDelay() {
            return tool.getInitialDelay();
        }

        /**
         * Sets the time lag before the Tool Tip appears.<br>
         * Default value: 500
         *
         * @param value int
         */
        public void setInitialDelay(int value) {
            tool.setInitialDelay(value);
        }

        /**
         * Defines the text for the Footer, Header, SubFooter and SubHeader
         * 
         * @return String
         */
        public String getText() {
            return text;
        }

        /**
         * Defines the text for the Footer, Header, SubFooter and SubHeader
         * 
         * @param value
         */
        public void setText(final String value) {
            text = setStringProperty(text, value);
        }

        /**
         * Makes a ToolTip visible
         */
        public void show() {
            chartControl.setToolTip(tool, getText());
            tool.setActive(true);
        }

        /**
         * Hides a ToolTip
         */
        public void hide() {
            tool.setActive(false);
        }
    }
    protected Aspect aspect;
    transient private boolean autoRepaint;
    public boolean cancelMouse;
    transient public boolean iClicked;
    protected Axes axes;
    transient protected IGraphics3D graphics3D;
    private Panel panel;
    transient private Exports export;
    transient private Imports imports;
    private Printer printer;
    protected Legend legend;
    private Header header, subHeader;
    private Footer footer, subFooter;
    private Walls walls;
    protected SeriesCollection series;
    private ToolsCollection tools;
    protected ToolAxis toolCheckHidden;
    transient protected IChart parent;
    protected Page page;
    transient protected boolean restoredAxisScales;
    transient private ToolTip toolTip;
    transient protected ChartPen legendPen;
    transient protected boolean printing;
    public boolean clipWhenMetafiling = true;

    /* Apply clipping when creating metafiles */
    public boolean clipWhenPrinting = true; /* Apply clipping when printing */

    transient protected int seriesWidth3D, seriesHeight3D;
    transient protected int maxZOrder;
    protected Rectangle chartBounds = new Rectangle();
    protected Rectangle chartRect = new Rectangle();
    private Rectangle cachedCustomChartRect = new Rectangle();
    private boolean customChartRect = false;
    transient private TeeEventListeners listeners;
    private Scroll panning;
    protected Zoom zoom;
    transient private AllAxisSavedScales savedScales;
    public AxisSavedScales left, top, right, bottom;
    private transient int numRedraws;
    public Cursor originalCursor;
    private boolean autoScale = false;

    private void initFields() {
        setChart(this);

        series = new SeriesCollection(this);
        tools = new ToolsCollection(this);
        aspect = new Aspect(this);
        panel = new Panel(this);
        legend = new Legend(this);
        header = new Header(this);
        header.defaultText = Language.getString("TeeChart");
        header.setText(header.defaultText);

        subHeader = new Header(this);
        footer = new Footer(this);
        subFooter = new Footer(this);
        walls = new Walls(this);
        axes = new Axes(this);
        page = new Page(this);
        export = new Exports(this);
        imports = new Imports(this);
        printer = new Printer(this);
    }
    
    private void initEmptyFields() {
        setChart(this);

        if ( series == null)
          series = new SeriesCollection(this);
        if ( tools == null)
          tools = new ToolsCollection(this);
        if ( aspect == null)
          aspect = new Aspect(this);
        if ( panel == null)
          panel = new Panel(this);
        if ( legend == null)
          legend = new Legend(this);
        if ( header == null)
        {
          header = new Header(this);
          header.defaultText = Language.getString("TeeChart");
          header.setText(header.defaultText);
        }

        if ( subHeader == null)
          subHeader = new Header(this);
        if ( footer == null)
          footer = new Footer(this);
        if ( subFooter == null)
          subFooter = new Footer(this);
        if ( walls == null)
          walls = new Walls(this);
        if ( axes == null)
            axes = new Axes(this);
        if ( page == null)
            page = new Page(this);
        if ( export == null)
            export = new Exports(this);
        if ( imports == null)
            imports = new Imports(this);
        if ( printer == null)
            printer = new Printer(this);
    }

    private void readObject(ObjectInputStream in) throws IOException,
            ClassNotFoundException {
    	initFields();
        in.defaultReadObject();
    }
    
//    private void readObject(ObjectInputStream in) throws IOException,
//    ClassNotFoundException {
//    	initFields();
//		in.defaultReadObject();
//    }

    protected Object readResolve() {

    	initEmptyFields();
    	
        aspect.setChart(this);
        panel.setChart(this);
        legend.setChart(this);
        header.setChart(this);
        subHeader.setChart(this);
        footer.setChart(this);
        subFooter.setChart(this);
        walls.setChart(this);
        axes.setChart(this);
        page.setChart(this);
        export.setChart(this);
        imports.setChart(this);
        printer.setChart(this);

        series.setChart(this);
        tools.setChart(this);

        autoRepaint = true;
        restoredAxisScales = true;
        maxZOrder = 0;

        if (getParent() != null) {
            getParent().checkGraphics();
        }

        wallP[0] = new Point();
        wallP[1] = new Point();
        wallP[2] = new Point();
        wallP[3] = new Point();

        return this;
    }

    /**
     * Returns a clone of the Chart.
     * 
     * @return Object
     */
    public Object clone() {

        Chart c = new Chart();

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        try {
            getExport().getTemplate().toStream(stream);
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        ByteArrayInputStream inputStream = new ByteArrayInputStream(stream.toByteArray());

        try {
            c = c.getImport().getTemplate().fromStream(inputStream);
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }

        return c;
    }

    /**
     * Returns an image of the chart of the defined width and  height dimensions
     * 
     * @param width
     * @param height
     * @return Image
     */
    public final Image image(int width, int height) {
        return getParent().image(width, height);
    }

    /**
     * (Read only) Used to get the four sides of the Chart (Left, Top,
     * Right and Bottom)
     *
     * @return Rectangle
     */
    public Rectangle getChartBounds() {
        return chartBounds;
    }

    /**
     * Sets the four sides of the Chart (Left, Top,
     * Right and Bottom)
     *
     * @param value Rectangle
     */
    public void setChartBounds(Rectangle value) {
        chartBounds = value;
        invalidate();
    }

    /*
     * internal use
     */
    public ToolAxis getToolCheckHidden() {
        return toolCheckHidden;
    }

    /*
     * internal use
     */
    public void setToolCheckHidden(ToolAxis tool) {
        toolCheckHidden = tool;
    }

    /**
     * The Chart width in pixels.
     *
     * @return int
     */
    public int getWidth() {
        return chartBounds.width;
    }

    /**
     * Sets the Chart width in pixels.
     *
     * @param value int
     */
    public void setWidth(int value) {
        chartBounds.width = value;
        invalidate();
    }

    /**
     * The Chart Height in pixels.
     *
     * @return int
     */
    public int getHeight() {
        return chartBounds.height;
    }

    /**
     * Sets the Chart Height in pixels.
     *
     * @param value int
     */
    public void setHeight(int value) {
        chartBounds.height = value;
        invalidate();
    }

    /**
     * Defines the position of the Chart's Left border in pixels
     * 
     * @return int
     */
    public int getLeft() {
        return chartBounds.getLeft();
    }

    /**
     * Defines the position of the Chart's Right border in pixels
     * 
     * @return int
     */
    public int getRight() {
        return chartBounds.getRight();
    }

    /**
     * Defines the position of the Chart's Top border in pixels
     * 
     * @return int
     */
    public int getTop() {
        return chartBounds.getTop();
    }

    protected int getChartRectBottom() {
        return chartRect.getBottom();
    }

    protected int chartRectWidth() {
        return chartRect.width;
    }

    protected int chartRectHeight() {
        return chartRect.height;
    }

    // IBaseChart
    /**
     * Internal use.
     * 
     * @param value
     */
    public void doChangedBrush(ChartBrush value) {
        if ((graphics3D != null) && (value == graphics3D.getBrush())) {
            graphics3D.changed(graphics3D.getBrush());
        }
    }

    /**
     * Internal use.
     * 
     * @param value
     */
    public void doChangedFont(ChartFont value) {
        if (graphics3D != null) {
            graphics3D.changed(graphics3D.getFont());
        }
    }

    /**
     * Internal use.
     * 
     * @return boolean
     */
    public boolean canDrawPanelBack() {
        return ((!printing) || getPrinter().getPrintPanelBackground());
    }

    /**
     * Determines when Chart is being printed.
     *
     * @return boolean
     */
    public boolean getPrinting() {
        return printing;
    }

    /**
     * Determines when Chart is being printed.
     *
     * @param value boolean
     */
    public void setPrinting(boolean value) {
        printing = value;
    }

    /**
     * Cancels the mouse when set to True.
     * 
     * @param value boolean
     */
    public void setCancelMouse(boolean value) {
        cancelMouse = value;
    }

    public Chart() {
        this(null, null);
    }

    public Chart(IChart parent) {
        this(parent, null);
    }

    public Chart(IChart parent, Cursor cursor) {
        super(null);

        initFields();
        readResolve();
        this.parent = parent;
        originalCursor = cursor;
    }

    /**
     * Changes the Series from one type to another, for example from Bar Series<br>
     * Series to Line Series
     * 
     * 
     * @param series
     * @param newClass
     * @return Series
     */
    static public Series changeSeriesType(Series series, Class newClass) throws NoSuchMethodException, IllegalArgumentException, InvocationTargetException {
        Class type = newClass;
        Function tmpFunction = series.getFunction();
        Class function = (tmpFunction == null) ? null : tmpFunction.getClass();
        try {
            Series tmpSeries = Series.createNewSeries(series.getChart(), type, function);
            if (tmpSeries != null) {
                com.steema.teechart.styles.Series.assignDispose(series, tmpSeries);
            }
            return tmpSeries;
        } catch (IllegalAccessException e) {
            return null;
        } catch (InstantiationException e) {
            return null;
        }
    }

    /**
     * Changes all Series on the Chart from one Series type to another.
     * 
     * @param chart
     * @param newClass
     */
    static public void changeAllSeriesType(Chart chart, Class newClass) throws NoSuchMethodException, IllegalArgumentException, InvocationTargetException {
        Series tmp;
        List tmpList = new ArrayList(chart.getSeries());
        Iterator it = tmpList.iterator();
        while (it.hasNext()) {
            Series s = (Series) it.next();
            changeSeriesType(s, newClass);
        }
    }

    protected void removeAllComponents() {
        series.clear();
        tools.clear();
        axes.getCustom().clear();
    }

    /**
     * Returns array list of objects that implement the
     * TeeEventListener interface.
     *
     * @return TeeEventListeners
     */
    public TeeEventListeners getListeners() {
        if (listeners == null) {
            listeners = new TeeEventListeners();
        }
        return listeners;
    }

    protected void removeListener(ITeeEventListener sender) {
        if (listeners != null) {
            listeners.remove(sender);
        }
    }

    /**
     * Returns true if source can act as Series datasource.
     * 
     * @param s
     * @param source
     * @return boolean
     */
    public boolean isValidDataSource(Series s, Object source) {
        boolean result = ((s != source) && (source instanceof Series)
                && (s.isValidSourceOf((Series) source)));

        if (!result) {
            result = DataSeriesSource.isValidSource(source);
        }

        return result;
    }

    private int calcNumPages(Axis a) {
        // By default, one single page.
        int result = 1;

        // Calc max number of points for all active series associated to "a" axis.
        int tmp = 0;
        boolean firstTime = true;

        for (int t = 0; t < series.size(); t++) {
            Series s = series.getSeries(t);
            if (s.getActive() && s.associatedToAxis(a)) {
                if (firstTime || (s.getCount() > tmp)) {
                    tmp = s.getCount();
                    firstTime = false;
                }
            }

            // If there are points... divide into pages... }
            if (tmp > 0) {
                result = tmp / page.getMaxPointsPerPage();

                // Extra page for remaining points...
                if ((tmp % page.getMaxPointsPerPage()) > 0) {
                    result++;
                }
            }

        }
        return result;
    }

    /**
     * Restores the "remembered" axis scales when unzooming.
     * 
     */
    public void restoreAxisScales() {
        if (!restoredAxisScales) {
            restoreScales(savedScales);
            restoredAxisScales = true;
        }
    }

    /**
     * Restores the "remembered" axis scales when unzooming.
     * 
     * @param a
     * @param tmp
     */
    static private void restoreAxisScales(Axis a, AxisSavedScales tmp) {
        a.setAutomatic(tmp.auto);
        a.setAutomaticMinimum(tmp.autoMin);
        a.setAutomaticMaximum(tmp.autoMax);
        if (!a.getAutomatic()) {
            a.setMinMax(tmp.min, tmp.max);
        }
    }

    /**
     * Returns the number of Chart pages according to current points per page.
     * 
     * @return int
     */
    public int getNumPages() {
        if ((page.getMaxPointsPerPage() > 0) && (getSeries().size() > 0)) {
            if (getSeries().getSeries(0).getYMandatory()) {
                return Math.max(calcNumPages(axes.getTop()),
                        calcNumPages(axes.getBottom()));
            } else {
                return Math.max(calcNumPages(axes.getLeft()),
                        calcNumPages(axes.getRight()));
            }
        } else {
            return 1;
        }
    }

    /**
     * Displays a text box at the cursor.
     *
     * @return ToolTip
     */
    public ToolTip getToolTip() {
        if (toolTip == null) {
            toolTip = new ToolTip(this);
        }
        return toolTip;
    }

    /**
     * Returns the Active series (visible) that corresponds to the
     * ItemIndex position in the Legend.<br>
     * When the Legend style is "Series", returns the series that corresponds
     * to the Legend "ItemIndex" position. The "OnlyActive" parameter, when
     * false takes into account all series, visibly active or not.
     *
     * @param itemIndex int
     * @return Series that corresponds to the ItemIndex position in the Legend
     */
    public Series activeSeriesLegend(int itemIndex) {
        return seriesLegend(itemIndex, true);
    }

    /**
     * Returns the Series.Title string.
     *
     * @param seriesIndex int
     * @param onlyActive boolean
     * @return String
     */
    public String getSeriesTitleLegend(int seriesIndex, boolean onlyActive) {
        Series tmpSeries = null;

        if (onlyActive) {
            tmpSeries = activeSeriesLegend(seriesIndex);
        } else {
            tmpSeries = seriesLegend(seriesIndex, false);
        }

        return (tmpSeries != null) ? tmpSeries.toString() : "";
    }

    private void seriesBeforeDraw() {
        for (int t = 0; t < series.size(); t++) {
            Series s = series.getSeries(t);
            if (s.getActive()) {
                s.doBeforeDrawChart();
            }
        }
    }
    public boolean isDirty = true;

    /**
     * Main drawing procedure. Paints all Chart contents.
     *
     * @param g IGraphics3D
     */
    protected void internalDraw(IGraphics3D g) {

        isDirty = true;
        seriesBeforeDraw();

        Rectangle rect = chartRect;

        chartRect = drawTitlesAndLegend(g, rect, true);

        ERMode.ERPublish(g, chartBounds);

        calcInvertedRotation();

        setSeriesZOrder();

        rect = calcWallsRect(rect);

        chartRect = rect;

        calcAxisRect();

        rect = chartRect;

        setSeriesZPositions();

        calcSeriesRect();

        g.projection(aspect.width3D, chartRect);

        drawAxesSeries(g, rect);

        rect = drawTitlesAndLegend(g, rect, false);

        ERMode.ERPublish(g, chartBounds);

        // Draw all Tools...
        broadcastToolEvent(new ChartDrawEvent(this, ChartDrawEvent.PAINTED,
                ChartDrawEvent.CHART));

        //g.textOut(0, 0, Integer.toString(numRedraws++));
    }

    private void drawAxesSeries(IGraphics3D g, final Rectangle rect) {
        drawWalls(g, rect);

        if (axes.getDrawBehind()) {
            axes.draw(g);
        }

        drawAllSeries(g);

        if (!axes.getDrawBehind()) {
            axes.draw(g);
        }

    }

    private void drawAllSeries(IGraphics3D g) {
        boolean activeRegion = false;
        // Draw all Tools...
        broadcastToolEvent(new ChartDrawEvent(this, ChartDrawEvent.PAINTING,
                ChartDrawEvent.SERIES));

        if (parent != null) {
            parent.doBeforeDrawSeries();
        }

        StringAlignment oldStrAlign = g.getTextAlign();

        // Draw all Series...
        if (axes.getDepth().getInverted()) {
            for (int t = series.size() - 1; t >= 0; t--) {
                Series s = series.getSeries(t);
                if (s.getActive()) {
                    s.drawSeries();
                }
            }
            for (int t = series.size() - 1; t >= 0; t--) {
                Series s = series.getSeries(t);
                if (s.getMarks().getOnTop()) {
                    s.drawMarksSeries(s, activeRegion);
                }
            }
        } else {
            for (int t = 0; t < series.size(); t++) {
                Series s = series.getSeries(t);
                if (s.getActive()) {
                    s.drawSeries();
                }
            }
            for (int t = 0; t < series.size(); t++) {
                Series s = series.getSeries(t);
                if (s.getMarks().getOnTop()) {
                    s.drawMarksSeries(s, activeRegion);
                }
            }

            g.setTextAlign(oldStrAlign);
        }

        broadcastToolEvent(new ChartDrawEvent(this, ChartDrawEvent.PAINTED,
                ChartDrawEvent.SERIES));
        
        if (parent != null) {
            parent.doAfterDrawSeries();
        }

    }

    private void drawWalls(IGraphics3D g, final Rectangle rect) {
        if (series.activeUseAxis() && (walls.getVisible())) {
            walls.paint(g, rect);
        }
    }

    /**
     * Accesses the Zoom characteristics of the Chart.
     *
     * @return Zoom
     */
	public Zoom getZoom() {
        if (zoom == null) {
            zoom = new Zoom(this);
        }
        return zoom;
    }

    /**
     * Sets the Zoom characteristics of the Chart.
     * 
     * @param zoom
     */
    public void setZoom(Zoom zoom) {
        this.zoom = zoom;
    }

    /**
     * Sets the scrolling direction or denies scrolling.
     *
     * @return Scroll
     */
    public Scroll getPanning() {
        if (panning == null) {
            panning = new Scroll(this);
        }
        return panning;
    }

    /**
     * Sets the scrolling direction or denies scrolling.
     * 
     * @param panning
     */
    public void setPanning(Scroll panning) {
        this.panning = panning;
    }

    private void calcSize3DWalls() {
        if (aspect.getView3D()) {

            double tmp = 0.001 * aspect.getChart3DPercent();

            if (!aspect.getOrthogonal()) {
                tmp *= 2;
            }

            seriesWidth3D = Utils.round(tmp * chartBounds.width);

            if (aspect.getOrthogonal()) {
                double tmpSin = Math.sin(aspect.getOrthoAngle()
                        * Utils.PISTEP);
                double tmpCos = Math.cos(aspect.getOrthoAngle()
                        * Utils.PISTEP);
                tmp = tmpSin / tmpCos;
            } else {
                tmp = 1;
            }

            if (tmp > 1) {
                seriesWidth3D = Utils.round(seriesWidth3D / tmp);
            }

            seriesHeight3D = Utils.round(seriesWidth3D * tmp);

            int tmpNumSeries = aspect.getApplyZOrder()
                    ? Math.max(1, maxZOrder + 1)
                    : 1;

            aspect.height3D = seriesHeight3D * tmpNumSeries;
            aspect.width3D = seriesWidth3D * tmpNumSeries;
        } else {
            seriesHeight3D = 0;
            seriesWidth3D = 0;
            aspect.height3D = 0;
            aspect.width3D = 0;
        }
    }

    protected void checkZoomPanning(FrameworkMouseEvent e) {
        if (activeSeriesUseAxis()) {
            if (getZoom().getAllow()
                    && (e.getButton() == getZoom().getMouseButton())) {
                //&& (Zoom.KeyShift<=Shift) )

                int x = e.getX();
                int y = e.getY();

                if (getZoom().getDirection() == ZoomDirections.VERTICAL) {
                    x = chartRect.x;
                }
                if (getZoom().getDirection() == ZoomDirections.HORIZONTAL) {
                    y = chartRect.y;
                }

                getZoom().activate(x, y);

                if (getZoom().getDirection() == ZoomDirections.VERTICAL) {
                    getZoom().x1 = chartRect.getRight();
                }
                if (getZoom().getDirection() == ZoomDirections.HORIZONTAL) {
                    getZoom().y1 = chartRect.getBottom();
                }

                iClicked = true;
            }
            if ((panning.getAllow() != ScrollMode.NONE)
                    && (e.getButton() == panning.getMouseButton())) {
                //&& (TeeScrollKeyShift<=Shift ) )
                panning.activate(e.getPoint());
                iClicked = true;
            }

            // This should be done at TChart level, not at Chart level...
            //				if ( IClicked ) Cursor=Cursors.Default; /* <-- todo  ScrollCursor=crHandPoint */
        }
    }

    protected Cursor broadcastMouseEvent(FrameworkMouseEvent e) {
        Cursor c = Cursor.DEFAULT;

        if ((parent != null) && (!parent.isDesignTime())) {

            if (getListeners().size() > 0) {
                broadcastEvent(new TeeMouseEvent(e));
            }

            c = parent.getTeeCursor();

            if (c != null) {
                c = broadcastMouseEvent(e, c);
            }
        }

        return c;
    }

    protected Cursor broadcastMouseEvent(FrameworkMouseEvent e, Cursor c) {
        for (int t = 0; t < tools.size(); t++) {
            Tool s = tools.getTool(t);
            if (s.getActive()) {
                c = s.mouseEvent(e, c);
                if (cancelMouse) {
                    break;
                }
            }
        }

        for (int t = 0; t < series.size(); t++) {
            Series s = series.getSeries(t);
            if (s.getActive()) {
                c = s.mouseEvent(e, c);
            }
        }

        return c;
    }

    protected void broadcastToolEvent(ChartDrawEvent ce) {
        for (int t = 0; t < tools.size(); t++) {
            Tool s = tools.getTool(t);
            if (s.getActive()) {
                s.chartEvent(ce);
            }
        }
    }

    public void broadcastEvent(TeeEvent event) {
        event.sender = getChart();

        for (int t = 0; t < getListeners().size(); t++) {
            ITeeEventListener tmp = getListeners().getEvent(t);
            tmp.invokeEvent(event);
            if ((event instanceof TeeMouseEvent) && cancelMouse) {
                break;
            }
        }
    }

    /**
     * Internal use.
     * 
     * @param s
     * @param e
     * @return TeeEvent
     */
    public TeeEvent broadcastEvent(Series s, SeriesEventStyle e) {
        SeriesEvent tmp = new SeriesEvent();
        tmp.event = e;
        tmp.series = s;
        broadcastEvent(tmp);
        return tmp;
    }

    final static class ClickedPart {

        public ClickedParts part = ClickedParts.NONE;
        public Axis axis = null;
        public Series series = null;
        public int pointIndex = -1;
    }

    static private ClickedPart clickedAxis(Axis a, Point p,
            ClickedPart result) {
        if (a.clicked(p)) {
            result.part = ClickedParts.AXIS;
            result.axis = a;
        }

        return result;
    }

    /**
     * Returns the Index of the Series passed as argument.
     * 
     * @param value
     * @return int
     */
    public int getSeriesIndexOf(Series value) {
        return series.indexOf(value);
    }

    /**
     * Collection of Series contained in this Chart.
     *
     * @return SeriesCollection
     */
    public SeriesCollection getSeries() {
        return series;
    }

    /**
     * Returns the index value of Series
     * 
     * @param seriesIndex
     * @return Series
     */
    public Series getSeries(int seriesIndex) {
        return series.getSeries(seriesIndex);
    }

    /**
     * Sets the index value of Series
     * 
     * @param value
     */
    public void setSeries(SeriesCollection value) {
        series = value;
        series.chart = this;
    }
    protected boolean invertedRotation;

    private void calcInvertedRotation() {
        invertedRotation = false;

        final Aspect a = getAspect();

        if (a.getView3D()) {
            if (a.getOrthogonal()) {
                if (a.getOrthoAngle() > 90) {
                    invertedRotation = true;
                }
            } else {
                if ((a.getRotation() % 360) < 180) {
                    invertedRotation = true;
                }
            }
        }
    }

    private ClickedPart calcNeedClickedPart(Point pos, boolean needed) {

        ClickedPart result = new ClickedPart();

        if (getLegend().getVisible()) {
            result.pointIndex = getLegend().clicked(pos);

            if (result.pointIndex != -1) {
                result.part = ClickedParts.LEGEND;
                return result;
            }
        }

        /* IMPORTANT  Z order inverted */
        for (int t = getSeries().size() - 1; t >= 0; t--) {
            Series s = getSeries().getSeries(t);

            boolean chkClickSeries = (parent == null) ? false : parent.checkClickSeries();

            if (s.getActive()
                    && ((!needed) || (chkClickSeries))) {
                result.pointIndex = s.clicked(pos);
                if (result.pointIndex != -1) {
                    result.series = getSeries(t);
                    result.part = ClickedParts.SERIES;
                    return result;
                }
                if (getSeries(t).getMarks().getVisible()) {
                    result.pointIndex = getSeries(t).getMarks().clicked(pos);
                    if (result.pointIndex != -1) {
                        result.series = getSeries(t);
                        result.part = ClickedParts.SERIESMARKS;
                        return result;
                    }
                }
            }
        }

        for (int sa = 0; sa < 5; sa++) {
            result = clickedAxis(axes.getAxis(sa), pos, result);
            if (result.part == ClickedParts.AXIS) {
                return result;
            }
        }

        for (int tt = 0; tt < axes.getCustom().size(); tt++) {
            result = clickedAxis(axes.getCustom().getAxis(tt), pos,
                    result);
            if (result.part == ClickedParts.AXIS) {
                return result;
            }
        }

        if (getHeader().clicked(pos)) {
            result.part = ClickedParts.HEADER;
        } else if (getSubHeader().clicked(pos)) {
            result.part = ClickedParts.SUBHEADER;
        } else if (getFooter().clicked(pos)) {
            result.part = ClickedParts.FOOT;
        } else if (getSubFooter().clicked(pos)) {
            result.part = ClickedParts.SUBFOOT;
        } else if (chartRect.contains(pos)
                && /* <-- should be PtInCube(ChartRect,0,MaxZ) */ (countActiveSeries() > 0)) {
            result.part = ClickedParts.CHARTRECT;
        }

        return result;
    }

    private void checkTitle(final Title t, final FrameworkMouseEvent e, final ClickedParts c) {
        if (parent != null) {
            parent.checkTitle(t, e, c);
        }
    }

    /**
     * Internal use.
     * 
     * @param e
     */
    public void mousePressed(FrameworkMouseEvent e) {

        if (cancelMouse || getZoom().getActive() || getPanning().getActive()) {
            return;
        }

        //cancelMouse = false;
        broadcastMouseEvent(e);

        if (!cancelMouse) {
            ClickedPart tmpPart = calcNeedClickedPart(new Point(e.getX(),
                    e.getY()), true);
            iClicked = false;

            if (tmpPart.part == ClickedParts.LEGEND) {

                iClicked = legend.doMouseDown(e.getPoint());

                if (parent != null) {
                    parent.doClickLegend(legend, e);
                }

            } else if (tmpPart.part == ClickedParts.SERIES) {
                cancelMouse = false;

                if (e.getClickCount() == 2) {
                    if (parent != null) {
                        tmpPart.series.doDoubleClick(tmpPart.pointIndex, e);
                    }
                }

                if (e.getClickCount() == 1) {
                    tmpPart.series.doClick(tmpPart.pointIndex, e);
                }

                iClicked = cancelMouse;

                boolean chkClickSeries = (parent != null);

                if (chkClickSeries) {
                    cancelMouse = true;

                    if (parent != null) {
                        parent.doClickSeries(this, tmpPart.series,
                                tmpPart.pointIndex, e);
                    }

                    iClicked = cancelMouse;
                }
                if (!iClicked) {
                    checkZoomPanning(e);
                }
            } else if (tmpPart.part == ClickedParts.AXIS) {
                if (parent != null) {
                    parent.doClickAxis(tmpPart.axis, e);
                }

                iClicked = cancelMouse;
                if (!iClicked) {
                    checkZoomPanning(e);
                }
            } else if (tmpPart.part == ClickedParts.HEADER) {
                checkTitle(header, e, tmpPart.part);
            } else if (tmpPart.part == ClickedParts.FOOT) {
                checkTitle(footer, e, tmpPart.part);
            } else if (tmpPart.part == ClickedParts.SUBHEADER) {
                checkTitle(subHeader, e, tmpPart.part);
            } else if (tmpPart.part == ClickedParts.SUBFOOT) {
                checkTitle(subFooter, e, tmpPart.part);
            } else if (tmpPart.part == ClickedParts.CHARTRECT) {
                if (parent != null) {
                    parent.checkBackground(this, e);
                }

                if (!iClicked) {
                    checkZoomPanning(e);
                }
            }

            if (!iClicked) {
                if (parent != null) {
                    parent.checkBackground(this, e);
                }
            }
        }

        cancelMouse = false;
    }

    private MouseClicked checkMouseSeries(Cursor c, int x, int y) {

        for (int t = 0; t < series.size(); t++) {
            Series s = series.getSeries(t);
            if (s.getActive()) {
                MouseClicked mc = s.checkMouse(c, x, y);
                if (mc.clicked) {
                    return mc;
                }
            }
        }

        MouseClicked r = new MouseClicked(c, false);
        return r;
    }

    private void processPanning(Axis a, int iniPos, int endPos) {
    	
        double delta = 0;
        ScrollResult r = new ScrollResult();

        if (a.getLogarithmic())
        {
          int pointDiff = endPos - iniPos;
          int AxisPosMin = a.calcPosValue(a.getMinimum());
          int AxisPosMax = a.calcPosValue(a.getMaximum());

          double changeAtMin = a.getMinimum() - a.calcPosPoint(AxisPosMin + pointDiff);
          double changeAtMax = a.getMaximum() - a.calcPosPoint(AxisPosMax + pointDiff);

          r.min = a.getMinimum() + changeAtMin;
          r.max = a.getMaximum() + changeAtMax;
          
          if (r.min > r.max)
          {
        	r.min = AxisPosMin;
        	r.max = AxisPosMax;
          }
        }
        else
        {
          delta = a.calcPosPoint(iniPos) - a.calcPosPoint(endPos);
          r.min = a.getMinimum() + delta;
          r.max = a.getMaximum() + delta;
        }

        if (parent != null) {
            parent.doAllowScroll(a, delta, r);
            if (r.allow) {
                a.setMinMax(r.min, r.max);
            }
        }
    }

    public boolean panAxis(boolean axisHorizontal, int pos1, int pos2,
            boolean panned) {
        ScrollMode tmpMode = axisHorizontal ? ScrollMode.HORIZONTAL
                : ScrollMode.VERTICAL;

        if ((pos1 != pos2)
                && ((panning.getAllow() == tmpMode)
                || (panning.getAllow() == ScrollMode.BOTH))) {
            if (axisHorizontal) {
                processPanning(axes.getTop(), pos2, pos1);
                processPanning(axes.getBottom(), pos2, pos1);
            } else {
                processPanning(axes.getLeft(), pos2, pos1);
                processPanning(axes.getRight(), pos2, pos1);
            }

            for (int t = 0; t < axes.getCustom().size(); t++) {
                Axis tmpAxis = axes.getCustom().getAxis(t);
                if (!tmpAxis.isDepthAxis) {
                    if ((axisHorizontal && tmpAxis.getHorizontal())
                            || ((!axisHorizontal) && (!tmpAxis.getHorizontal()))) {
                        processPanning(tmpAxis, pos2, pos1);
                    }
                }
            }
            panned = true;
        }
        return panned;
    }

    /**
     * Internal use.
     * 
     * @param e
     * @return Cursor
     */
    public Cursor mouseMoved(FrameworkMouseEvent e) {

        final Cursor c = broadcastMouseEvent(e);

        // TODO: Verify double broadcast !!
        //MM changed for teecommander compatibility
        //DB fails on tools  Cursor c = broadcastMouseEvent(e, null);
        
          return doMouseMove(e.getX(), e.getY(), e, c);
    }

    public Cursor doMouseMove(int x, int y, FrameworkMouseEvent e, Cursor c) {
    	parent.doMouseMove(e);
    	
        boolean panned = false;

        if (!cancelMouse) {
            if ((zoom != null) && zoom.getActive()) {
                if (zoom.getDirection() == ZoomDirections.VERTICAL) {
                    x = chartRect.getRight();
                }
                if (zoom.getDirection() == ZoomDirections.HORIZONTAL) {
                    y = chartRect.getBottom();
                }
                if ((x != zoom.x1) || (y != zoom.y1)) {
                    zoom.x1 = x;
                    zoom.y1 = y;
                    invalidate();
                }
            } else if ((panning != null) && panning.getActive()) {
                if (getZoom().getZoomStyle() == ZoomStyle.INCHART) {
                    if (chartRect.contains(x, y)) {
                        if ((x != panning.x1) || (y != panning.y1)) {
                            panned = false;
                            // panning.check(); //TJ71015219
                            if (restoredAxisScales) {
                                savedScales = saveScales();
                                restoredAxisScales = false;
                            }

                            panned = panAxis(true, x, panning.x1, panned);
                            panned = panAxis(false, y, panning.y1, panned);
                            panning.x1 = x;
                            panning.y1 = y;

                            if (panned) {
                                if (parent != null) {
                                    parent.doScroll(this);
                                }
                                invalidate();
                            }
                        }
                    } else {
                        panning.setActive(false);
                    }
                } else {
                    chart.getAspect().setView3D(true);
                    chart.getAspect().setHorizOffset(x - panning.x1);
                    chart.getAspect().setVertOffset(y - panning.y1);
                }
            } else {
                MouseClicked mc = checkMouseSeries(c, x, y);
                if (mc.clicked) {
                    c = mc.cursor;
                }
            }
        }
        // else
        // if ( ! CheckMouseSeries(ref c,x,y) )
        // { // is not mouse over Series ?
        // //c=parent.GetCursor(); //CI Dec03 //#1237
        // }

        return c;
    }

    private void zoomAxis(Axis a, double tmpA, double tmpB) {
        /* apply a portion of the desired zoom to achieve animation */
        a.setMinMax(a.getMinimum()
                + ((tmpA - a.getMinimum()) / getZoom().animatedFactor),
                a.getMaximum()
                - ((a.getMaximum() - tmpB) / getZoom().animatedFactor));
    }

    private void doZoomAnimated(double topi, double topf, double boti,
            double botf,
            double lefi, double leff, double rigi,
            double rigf) {
        for (int t = 1; t < getZoom().getAnimatedSteps(); t++) {
            zoomAxis(axes.getLeft(), lefi, leff);
            zoomAxis(axes.getRight(), rigi, rigf);
            zoomAxis(axes.getTop(), topi, topf);
            zoomAxis(axes.getBottom(), boti, botf);
            parent.refreshControl();
        }
    }

    // "remember" the axis scales when zooming, to restore if unzooming.
    static private AxisSavedScales saveAxisScales(Axis a) {
        AxisSavedScales result = new AxisSavedScales();
        result.auto = a.getAutomatic();
        result.autoMin = a.getAutomaticMinimum();
        result.autoMax = a.getAutomaticMaximum();
        result.min = a.getMinimum();
        result.max = a.getMaximum();
        return result;
    }

    protected AllAxisSavedScales saveScales() {
        AllAxisSavedScales result = new AllAxisSavedScales();
        result.top = saveAxisScales(axes.getTop());
        result.bottom = saveAxisScales(axes.getBottom());
        result.left = saveAxisScales(axes.getLeft());
        result.right = saveAxisScales(axes.getRight());
        
        result.customAxes = new AxisSavedScales[axes.getCustom().size()];
        
        for (int i=0; i < result.customAxes.length; i++)
        {
        	result.customAxes[i] = saveAxisScales(axes.getCustom().getAxis(i));
        }
        
        return result;
    }

    protected void restoreScales(AllAxisSavedScales s) {
        restoreAxisScales(axes.getTop(), s.top);
        restoreAxisScales(axes.getBottom(), s.bottom);
        restoreAxisScales(axes.getLeft(), s.left);
        restoreAxisScales(axes.getRight(), s.right);
        
        for (int i=0; i < s.customAxes.length; i++)
        {
        	restoreAxisScales(axes.getCustom().getAxis(i), s.customAxes[i]);
        }
    }

    /**
     * Internal use.<br>
     * Activates call to Zoom. Use getZoom().zoomRect or zoomPercent
     * 
     * @param topx
     * @param topy
     * @param bottomx
     * @param bottomy
     * @param leftx
     * @param lefty
     * @param rightx
     * @param righty
     */
    public void doZoom(double topx, double topy, double bottomx,
            double bottomy,
            double leftx, double lefty, double rightx,
            double righty) {
        doZoom(new Point.Double(topx, topy),
                new Point.Double(bottomx, bottomy),
                new Point.Double(leftx, lefty),
                new Point.Double(rightx, righty));
    }

    public void doZoom(Point.Double top, Point.Double bot, Point.Double lef,
            Point.Double rig) {
        if (restoredAxisScales) {
            savedScales = saveScales();
            restoredAxisScales = false;
        }

        if (getZoom().getAnimated()) {
            doZoomAnimated(top.x, top.y, bot.x, bot.y, lef.x, lef.y, rig.x,
                    rig.y);
            /* animation... */
        }

        /* final zoom */
        axes.getLeft().setMinMax(lef.x, lef.y);
        axes.getRight().setMinMax(rig.x, rig.y);
        axes.getTop().setMinMax(top.x, top.y);
        axes.getBottom().setMinMax(bot.x, bot.y);
        zoom.setZoomed(true);

        if (parent != null) {
            parent.doZoomed(this);
        }
    }

    /**
     * Internal use.
     * 
     * @param e
     */
    public void mouseReleased(FrameworkMouseEvent e) {
        cancelMouse = false; // 5.03

        parent.doMouseUp(e);

        if (getZoom().getActive() && (getZoom().getZoomStyle() == ZoomStyle.INCHART)
                && (e.getButton() == getZoom().getMouseButton())) {
            zoom.setActive(false);

            //Drawing.getPen().Mode=pmCopy;
            int x = e.getX();
            int y = e.getY();

            if (zoom.getDirection() == ZoomDirections.VERTICAL) {
                x = chartRect.getRight();
            }
            if (zoom.getDirection() == ZoomDirections.HORIZONTAL) {
                y = chartRect.getBottom();
            }
            zoom.x1 = x;
            zoom.y1 = y;
            if ((Math.abs(zoom.x1 - zoom.x0) > zoom.getMinPixels())
                    && (Math.abs(zoom.y1 - zoom.y0) > zoom.getMinPixels())) {
                if ((zoom.x1 > zoom.x0) && (zoom.y1 > zoom.y0)) {
                    zoom.calcZoomPoints(); // <--zoom in !
                } else {
                    zoom.undo(); // <--zoom out !
                }
                invalidate();
            }
        }

        if (panning != null) {
            panning.setActive(false);
        }

        broadcastMouseEvent(e);
    }

    /**
     * Obsolete.&nbsp;Please use tChart1.<!-- -->Zoom.<!-- -->Undo method.
     */
    public void undoZoom() {
        zoom.undo();
    }

    private boolean activeSeriesUseAxis() {
        for (int t = 0; t < series.size(); t++) {
            Series s = series.getSeries(t);
            if (s != null)
                if (s.getActive() && s.getUseAxis()) {
                    return true;
                }
        }
        return false;
    }

    /**
     * Internal use.
     * 
     * @param aColor
     * @param aBrush
     * @param aBackColor
     */
    public void setBrushCanvas(Color aColor, ChartBrush aBrush,
            Color aBackColor) {
//      if ((!aBrush.Solid) && (AColor==aBackColor))
//        if (aBackColor==Color.black) aColor=Color.WHITE; else aColor=Color.black;


        //CDI TTrack //#1482 commented out all but the below
        graphics3D.setBrush(aBrush);
        graphics3D.getBrush().setColor(aColor); ////#1482 ColorEach
//      graphics3D.Brush.ForegroundColor=aBackColor;
    }

    /**
     * Returns the number of active (visible) series.<br>
     * CanClip returns if the Chart Drawing Canvas has the capability of
     * "clipping" lines and polygons.<br>
     * "Clipping" means the feature that allows hiding drawing outside the
     * rectangle or polygon specifed by the developer.<br>
     * CanClip returns false when the Chart is displayed in OpenGL 3D, or when
     * the Chart is printed and the TeeClipWhenPrinting constant is true, or
     * when the Chart is converted to a metafile image and the
     * TeeClipWhenMetafiling constant is true. <br>
     * By default all display drivers and printers support clipping. You can
     * turn off the clipping constants in case the printer or display driver
     * has a bug related to clipping. <br>
     * See also: <br>
     * ClipPoints, TeeClipWhenPrinting, TeeClipWhenMetafiling, ClipCanvas,
     * UnClipCanvas, ClipRoundRectangle and ClipPolygon.
     *
     * @return boolean
     */
    public boolean canClip() {
        return (!graphics3D.getSupportsFullRotation())
                && (((!printing) && (!graphics3D.getMetafiling()))
                || (printing && clipWhenPrinting)
                || (graphics3D.getMetafiling() && clipWhenMetafiling));
    }

    private Rectangle calcWallsRect(Rectangle r) {
        calcSize3DWalls();

        // for orthogonal only :
        if (aspect.getView3D() && aspect.getOrthogonal()) {
            int tmp = 0;

            if (activeSeriesUseAxis()) {
                tmp = getWalls().getBack().getSize();
            }

            r.width -= Math.abs(aspect.width3D) + tmp;
            int t = Math.abs(aspect.height3D) + tmp;
            r.height -= t;
            r.y += t;

            if (getWalls().getRight().getVisible()) {
                r.width -= getWalls().getRight().getSize() + 1;
            }
        }

        return recalcWidthHeight(r);
    }

    /**
     * Internal use.
     * 
     * @param r
     * @return Rectangle
     */
    public Rectangle recalcWidthHeight(final Rectangle r) {
        if (r.x < chartBounds.x) {
            r.x = chartBounds.x;
        }
        if (r.y < chartBounds.y) {
            r.y = chartBounds.y;
        }
        if (r.getRight() < chartBounds.x) {
            r.width = 1;
        } else if (r.getRight() == chartBounds.x) {
            r.width = chartBounds.width;
        }
        if (r.getBottom() < chartBounds.y) {
            r.height = 1;
        } else if (r.getBottom() == chartBounds.y) {
            r.height = chartBounds.height;
        }

        //	graphics3D.Width  =r.width;
        //	graphics3D.Height =r.height;

        graphics3D.setXCenter((r.x + r.getRight()) / 2);
        graphics3D.setYCenter((r.y + r.getBottom()) / 2);

        return r;
    }

    /**
     * Returns if no series are active (visible) and associated to the
     * Axis "a" parameter.
     *
     * @param a Axis
     * @return boolean
     */
    private boolean noActiveSeries(Axis a) {
        for (int t = 0; t < series.size(); t++) {
            Series s = series.getSeries(t);
            if (s.getActive() && s.associatedToAxis(a)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Returns the first Series that depends on the specified Axis.<br>
     * If no Series depends on Axis, the null value is returned.
     *
     * @param axis Axis
     * @return Series
     */
    public Series getAxisSeries(Axis axis) {
        for (int t = 0; t < series.size(); t++) {
            Series s = getSeries(t);
            if ((s.getActive() || noActiveSeries(axis))
                    && s.associatedToAxis(axis)) {
                return s;
            }
        }

        return null;
    }

    /**
     * Internal use.
     * 
     * @param aAxis
     * @param isMin
     * @param isX
     * @return double
     */
    public double internalMinMax(Axis aAxis, boolean isMin, boolean isX) {
        boolean firstTime = true;
        boolean tmpAutoScale = false;
        double tmp = 0;
        double tmpResult = 0;
        CalcResultType calcResultV = new CalcResultType();

        int tmpFirst;

        if (aAxis.isDepthAxis) {
            if (aAxis.calcLabelStyle() == AxisLabelStyle.VALUE || !((DepthAxis) aAxis).getLabelsAsSeriesTitles()) {
                tmpResult = 0;
                firstTime = true;
                for (int t = 0; t < series.size(); t++) {
                    Series s = series.getSeries(t);
                    if (s.getActive()) {
                        tmp = isMin ? s.getMinZValue() : s.getMaxZValue();
                        if (firstTime || (isMin && (tmp < tmpResult))
                                || ((!isMin) && (tmp > tmpResult))) {
                            firstTime = false;
                            tmpResult = tmp;
                        }
                    }
                }
            } else {
                tmpResult = isMin ? -0.5 : maxZOrder + 0.5;
            }
        } else {
            tmpResult = 0;
            Series tmpSeries = getAxisSeries(aAxis);
            boolean tmpPagingAxis = (tmpSeries != null)
                    ? tmpSeries.getYMandatory()
                    ? isX
                    : !isX : isX;

            if ((page.getMaxPointsPerPage() > 0) && tmpPagingAxis) {
                if ((tmpSeries != null) && (tmpSeries.getCount() > 0)) {

                    ValueList tmpList = isX ? tmpSeries.getXValues()
                            : tmpSeries.getYValues();

                    int tmpFirstPoint = (page.getCurrent() - 1)
                            * page.getMaxPointsPerPage();
                    int tmpCount = tmpSeries.getCount();

                    if (tmpCount <= tmpFirstPoint) {
                        tmpFirstPoint = (Math.max(0,
                                (tmpCount
                                / page.getMaxPointsPerPage()) - 1)
                                * page.getMaxPointsPerPage());
                    }

                    int tmpLastPoint = tmpFirstPoint
                            + page.getMaxPointsPerPage()
                            - 1;

                    if (tmpCount <= tmpLastPoint) {
                        tmpLastPoint = tmpFirstPoint
                                + (tmpCount
                                % page.getMaxPointsPerPage())
                                - 1;
                    }

                    if (isMin) {
                        tmpResult = tmpList.value[tmpFirstPoint];
                    } else {
                        tmpResult = tmpList.value[tmpLastPoint];

                        if (!page.getScaleLastPage()) {
                            int tmpNumPoints = tmpLastPoint - tmpFirstPoint
                                    + 1;

                            if (tmpNumPoints < page.getMaxPointsPerPage()) {

                                tmp = tmpList.value[tmpFirstPoint];
                                tmpResult = tmp
                                        + page.getMaxPointsPerPage()
                                        * (tmpResult - tmp)
                                        / tmpNumPoints;
                            }
                        }
                    }
                }
            } else {
                // autoscale stuff begins here
                tmpAutoScale = ((this.getPage().getMaxPointsPerPage() > 0)
                        && this.getPage().getAutoScale());
                firstTime = true;
                for (int t = 0; t < series.size(); t++) {
                    Series s = series.getSeries(t);
                    if ((s.getActive() || noActiveSeries(aAxis))
                            && (s.getCount() > 0)) {
                        if ((isX
                                && ((s.getHorizontalAxis() == HorizontalAxis.BOTH)
                                || (s.getHorizAxis() == aAxis)))
                                || ((!isX)
                                && ((s.getVerticalAxis() == VerticalAxis.BOTH)
                                || (s.getVertAxis() == aAxis)))) {
                            if (tmpAutoScale) {
                                // if autoscale is activated...
                                tmpFirst = this.getPage().getFirstValueIndex();
                                // get the first valueindex of the chart area
                                if (s.getCount() > tmpFirst) {
                                    ValueList tmpList = isX ? tmpSeries.getXValues()
                                            : tmpSeries.getYValues();
                                    tmp = tmpList.value[tmpFirst];

                                    for (int tt = tmpFirst + 1; tt < Math.min(tmpFirst
                                            + this.getPage().getMaxPointsPerPage(),
                                            s.getCount()); tt++) {
                                        double tmp2 = tmpList.value[tt];

                                        if (isMin) {
                                            if (tmp2 < tmp) {
                                                tmp = tmp2;
                                            }
                                        } else {
                                            if (tmp2 > tmp) {
                                                tmp = tmp2;
                                            }
                                        }
                                    }
                                    calcResultV.firstTime = firstTime;
                                    calcResultV.result = tmpResult;
                                    calcResult(isMin, tmp, calcResultV);
                                    tmpResult = calcResultV.result;
                                    firstTime = calcResultV.firstTime;
                                }
                            } else {
                                if (isMin) {
                                    tmp = isX ? s.getMinXValue() : s.getMinYValue();
                                } else {
                                    tmp = isX ? s.getMaxXValue() : s.getMaxYValue();
                                }


                                calcResultV.firstTime = firstTime;
                                calcResultV.result = tmpResult;
                                calcResult(isMin, tmp, calcResultV);
                                tmpResult = calcResultV.result;
                                firstTime = calcResultV.firstTime;
                            }
                        }
                    }
                }
            }
        }
        return tmpResult;
    }

    /**
     * Returns the Maximum width in pixels of all Series Labels, whether
     * active or not.<br>
     * This applies only to Series which have Labels.
     *
     * @return int
     */
    public int maxTextWidth() {
        int tmpResult = 0;
        for (int t = 0; t < series.size(); t++) {
            Series s = series.getSeries(t);
            if (s.getLabels().size() > 0) {
                for (int tt = 0; tt < s.getCount(); tt++) {
                    tmpResult = Math.max(tmpResult,
                            multiLineTextWidth(s.getLabels().
                            getString(tt)).width);
                }
            }
        }
        return tmpResult;
    }

    public int maxTextHeight(Axis a) {
        int tmpResult = 0;
        for (int i = 0; i < a.getLabels().getItems().size(); i++) {
            AxisLabelItem item = a.getLabels().getItems().getItem(i);
            tmpResult = Math.max(tmpResult, graphics3D.textHeight("H") * multiLineTextWidth(item.getText()).count);
        }

      return tmpResult;
    }
            
    /**
     * Returns the Maximum width of the Active Series Marks.<br>
     * Series Marks must be Visible. This can be used to adjust the Chart
     * Margins in order to accomodate the biggest Series Mark.
     *
     * @return int
     */
    public int maxMarkWidth() {
        int tmpResult = 0;
        for (int t = 0; t < series.size(); t++) {
            Series s = series.getSeries(t);
            if (s.getActive()) {
                tmpResult = Math.max(tmpResult, s.maxMarkWidth());
            }
        }
        return tmpResult;
    }

    static final class CalcStringResults {

        int tmpResult;
        int numLines;
    }

    private CalcStringResults calcString(CalcStringResults result,
            String st) {
        result.tmpResult = Math.max(result.tmpResult,
                getGraphics3D().textWidth(st));
        result.numLines++;
        return result;
    }

    /**
     * Returns maximum linewidth of multiple line text.
     * 
     * @param s
     * @return MultiLine
     */
    public MultiLine multiLineTextWidth(String s) {
        int i = s.indexOf(Language.lineSeparator);
        
        MultiLine result = new MultiLine();
        result.width = 0;
        result.count = 0;
        
        while (i > 0) {
            result.width = Math.max(result.width, graphics3D.textWidth(s.substring(0, i)));
            result.count++;
            s = s.substring(i + 1);
            i = s.indexOf(Language.lineSeparator);
        }

        if (s.length() != 0) {
            result.width = Math.max(result.width, graphics3D.textWidth(s));
            result.count++;
        }
        
        return result;
    }

    /**
     * Returns the Maximum Value of the Series X Values List.
     *
     * @param axis Axis
     * @return double
     */
    public double getMaxXValue(Axis axis) {
        return internalMinMax(axis, false, true);
    }

    /**
     * Returns the highest of all the current Series Y point values.
     *
     * @param axis Axis
     * @return double
     */
    public double getMaxYValue(Axis axis) {
        return internalMinMax(axis, false, false);
    }

    /**
     * Returns the Minimum Value of the Series X Values List.
     *
     * @param axis Axis
     * @return double
     */
    public double getMinXValue(Axis axis) {
        return internalMinMax(axis, true, true);
    }

    /**
     * Returns the Minimum Value of the Series Y Values List.
     *
     * @param axis Axis
     * @return double
     */
    public double getMinYValue(Axis axis) {
        return internalMinMax(axis, true, false);
    }

    /**
     * Returns whether the AColor parameter is used by any Series or not.
     *
     * @param color Color
     * @param checkBackground boolean When true, uses current Chart background
     * @return boolean
     */
    public boolean isFreeSeriesColor(Color color, boolean checkBackground) {
        boolean isUsed = (checkBackground
                && ((color == panel.getColor())
                || (color == walls.getBack().getColor())));

        for (int t = 0; t < series.size(); t++) {
            Series s = series.getSeries(t);
            if ((s.getColor() == color) || isUsed) {
                return false;
            }
        }

        return !isUsed;
    }

    /**
     * Returns a color from the default color palette not used by any Series.
     * <br>
     * The CheckBackGround parameter controls if the returned color should
     * or shouldn't be the Chart BackColor color. This function returns a
     * Color which is not used by any Series in the Chart.
     *
     * @param checkBackground boolean
     * @return Color
     */
    public Color freeSeriesColor(boolean checkBackground) {
        if (getGraphics3D() == null) {
            return Color.WHITE;
        } else {
            int t = 0;

            do {
                Color result = getGraphics3D().getDefaultColor(t);
                if (isFreeSeriesColor(result, checkBackground)) {
                    return result;
                } else {
                    t++;
                }
            } while (t < getGraphics3D().getColorPaletteLength());

            return getGraphics3D().getDefaultColor(0);
        }
    }

    /**
     * Paints the Chart in your preferred Canvas and region.
     *
     * @param g IGraphics3D
     * @param rect Rectangle
     */
    public void paint(IGraphics3D g, final Rectangle rect) {

        if (!getParent().drawZoomOnCachedImage()) {
            boolean old = autoRepaint;
            autoRepaint = false;

            IGraphics3D oldG = graphics3D;
            graphics3D = g;

            g.resetState();

            try {
                if (getCustomChartRect()) {
                    if (Utils.isEmptyRectangle(cachedCustomChartRect)) {
                        cachedCustomChartRect = chartRect.copy();
                    }
                }

                chartRect = rect.copy();
                chartBounds = chartRect.copy();
                g.initWindow(aspect, chartBounds, 100);

                if (!panel.bTransparent) {
                    if ((!getPrinter().isPrinting)
                            || (getPrinter().getPrintPanelBackground())) {
                        chartRect = panel.draw(g, chartRect);
                    }

                    parent.doDrawImage(g);
                }

                if (getCustomChartRect()) {
                    chartRect = cachedCustomChartRect.copy();
                } else if (!getAspect().getView3D()
                        && (getAspect().getHorizOffset() != 0
                        || getAspect().getVertOffset() != 0)) {
                    getChartRect().x += getAspect().getHorizOffset();
                    getChartRect().y += getAspect().getVertOffset();
                }

                parent.doBeforeDraw();

                internalDraw(g);

                if ((zoom != null) && zoom.getActive() && (zoom.getZoomStyle() == ZoomStyle.INCHART)) {
                    zoom.draw();
                }

                //g.resetState();
                parent.doAfterDraw();
            } finally {
                g.showImage();
                setAutoRepaint(old);
                graphics3D = oldG;
            }
        }
    }

    public void paint() {
        paint(getGraphics3D(), chartBounds);
    }

    /**
     * Paints the Chart on the Drawing Canvas g.
     *
     * @param g IGraphics3D
     */
    public void paint(IGraphics3D g) {
        paint(g, new Rectangle(getLeft(), getTop(), getWidth(), getHeight()));
    }

    /**
     * Invoke a Chart repaint.
     * 
     * @param g
     * @param width
     * @param height
     */
    public void paint(IGraphics3D g, int width, int height) {
        paint(g, new Rectangle(0, 0, width, height));
    }

    /**
     * Default indexer.<br><br>
     *
     * Example:<pre><font face="Courier" size="4">
     * tChart1[0].Color=Color.Blue;
     *  is equivalent to
     * tChart1.Series[0].Color=Color.Blue;
     * </font></pre>
     *
     * @param index int
     * @return Series
     */
    public Series getItem(int index) {
        return series.getSeries(index);
    }

    /**
     * Default indexer.<br>
     *
     * @param index int
     * @param value Series
     */
    public void setItem(int index, Series value) {
        series.setSeries(index, value);
    }

    //	Steps to determine if an axis is Visible:
    // 1) The global Chart.AxisVisible property is True... and...
    // 2) The Axis Visible property is True... and...
    // 3) At least there is a Series Active and associated to the axis and
    //   the Series has the "UseAxis" property True.
    public boolean isAxisVisible(Axis a) {

        boolean result = (axes.getVisible() && a.getVisible());

        if (result) { // if still visible...
            if (a.isDepthAxis) {
                return aspect.getView3D();
            } else {
                for (int t = 0; t < series.size(); t++) {
                    Series s = series.getSeries(t);
                    if (s.getActive()) {
                        if (s.getUseAxis()) {
                            result = s.associatedToAxis(a);
                            if (result) {
                                return true;
                            }
                        } else {
                            return false;
                        }
                    }
                }

            }
        }

        return result;
    }

    /**
     * Returns first active (visible) series.
     *
     * @return Series
     */
    public Series getFirstActiveSeries() {
        for (int t = 0; t < series.size(); t++) {
            Series s = series.getSeries(t);
            if (s.getActive()) {
                return s;
            }
        }
        return null;
    }

    private Rectangle axisRect(Axis a, Rectangle r) {
        if (isAxisVisible(a)) {
            Rectangle oldR = r;
            if ((a != a.getChart().getAxes().getDepthTop()) && (!a.isCustom())) {
                a.calcRect(oldR, true);
            } else {
                a.calcRect(oldR, false);
            }
            r.intersect(oldR);
            if (Utils.isEmptyRectangle(r)) { //CDI TF02013500
                r = oldR;
            }
        }
        return r;
    }

    private void calcAxisRect() {
        Rectangle tmpR = chartRect;
        axes.adjustMaxMin();
        axes.internalCalcPositions();

        Rectangle oldR = tmpR;

        tmpR = axisRect(axes.getLeft(), tmpR);
        tmpR = axisRect(axes.getTop(), tmpR);
        tmpR = axisRect(axes.getRight(), tmpR);
        tmpR = axisRect(axes.getBottom(), tmpR);
        tmpR = axisRect(axes.getDepth(), tmpR);
        tmpR = axisRect(axes.getDepthTop(), tmpR);

        for (int t = 0; t < axes.getCustom().size(); t++) {
            tmpR = axisRect(axes.getCustom().getAxis(t), tmpR);
        }

        tmpR = recalcWidthHeight(tmpR);
        chartRect = tmpR;

        axes.internalCalcPositions(); // recalc again
    }

    private void setSeriesZOrder() {
        maxZOrder = 0;

        boolean ok = (aspect.getApplyZOrder() && aspect.getView3D());

        if (ok) {
            maxZOrder = -1;
            for (int t = 0; t < series.size(); t++) {
                Series s = series.getSeries(t);
                if (s.getActive() && !s.InternalUse) {
                    s.calcZOrder();
                }
            }
        }

        // invert Z Orders
        if (!axes.getDepth().getInverted()) { // 6.01
            for (int t = 0; t < series.size(); t++) {
                Series s = series.getSeries(t);
                if (s.getActive() && !s.InternalUse) {
                    s.iZOrder = ok ? maxZOrder - s.getZOrder() : 0;
                }
            }
        }
    }

    /**
     * Returns the String to display at Legend for a given series and point
     * index.<br>
     * In other words it returns the string representation of a Series
     * Point value just as it would appear in Chart.Legend. The ValueIndex
     * parameter is the point index. Legend.TextStyle and all other
     * TChartLegend methods are used to create the resulting string.
     *
     * @param aSeries Series
     * @param valueIndex int
     * @return String
     */
    public String formattedValueLegend(Series aSeries, int valueIndex) {
        return (aSeries != null)
                ? legend.formattedValue(aSeries, valueIndex)
                : "";
    }

    private void setSeriesZPositions() {
        for (int t = 0; t < series.size(); t++) {
            Series s = series.getSeries(t);
            if (s.getActive()) {
                s.setZPositions();
            }
        }
    }

    private void calcSeriesAxisRect(Axis axis) {
        int tmpLeft = 0;
        int tmpTop = 0;
        int tmpRight = 0;
        int tmpBottom = 0;

        final Margins margins = new Margins();

        for (int t = 0; t < series.size(); t++) {

            Series tmpSeries = series.getSeries(t);

            if (tmpSeries.getActive()) {
                if (tmpSeries.associatedToAxis(axis)) {
                    if (axis.getHorizontal()) {
                        tmpSeries.calcHorizMargins(margins);  //B997
                            tmpLeft = Math.max(tmpLeft, margins.min);
                            tmpRight = Math.max(tmpRight, margins.max);
                    } else {
                        tmpSeries.calcVerticalMargins(margins);
                            tmpTop = Math.max(tmpTop, margins.min);
                            tmpBottom = Math.max(tmpBottom, margins.max);
                    }
                }
            }
        }

        // Apply offsets in pixels
        if (axis.getHorizontal()) {
            tmpLeft += axis.getMinimumOffset();
            tmpRight += axis.getMaximumOffset();
        } else {
            tmpTop += axis.getMaximumOffset();
            tmpBottom += axis.getMinimumOffset();
        }

        applyMaxMinOffsets(axis, tmpLeft, tmpTop, tmpRight, tmpBottom);
    }

    /*
     * Internal use.
     */
    protected void applyGapSize(Axis axis, Point p) {
        if (axis.getInverted()) {
            p.x += axis.iGapSize;
        } else {
            p.y += axis.iGapSize;
        }
    }

    private void applyMaxMinOffsets(Axis axis, int left, int top, int right, int bottom) {
        boolean tmpHasOffsets = (axis.getMinimumOffset() != 0) || (axis.getMaximumOffset() != 0);
        boolean tmpHasGapSize = axis.iGapSize > 0;
        Point tmpLR = new Point(left, right);
        Point tmpBT = new Point(bottom, top);

        if (tmpHasOffsets) {
            if (axis.getHorizontal()) {
                tmpLR = applyOffsets(axis, tmpLR.x, tmpLR.y); //top, right
            } else {
                tmpBT = applyOffsets(axis, tmpBT.x, tmpBT.y); //bottom, top
            }
        }

        if (tmpHasGapSize) {
            if (axis.getHorizontal()) {
                applyGapSize(axis, tmpLR);
            } else {
                applyGapSize(axis, tmpBT);
            }
        }

        axis.adjustMaxMinRect(tmpLR.x, tmpBT.y, tmpLR.y, tmpBT.x);
        axis.calcRoundScales();

        if (tmpHasOffsets) {
            axis.setPosTitle(axis.inflateAxisPos(axis.getLabels().getPosition(), axis.getSizeLabels()));
        }

    }

    private Point applyOffsets(Axis axis, int a, int b) {
        if (axis.getInverted()) {
            b += axis.getMinimumOffset();
            a += axis.getMaximumOffset();
        } else {
            a += axis.getMinimumOffset();
            b += axis.getMaximumOffset();
        }

        return new Point(a, b);
    }

    private void calcSeriesRect() {
        //std & custom axes
        for (int i = 0; i < axes.getCount(); i++) {
            calcSeriesAxisRect(axes.getAxis(i));
        }
    }

    private Rectangle drawTitleFoot(IGraphics3D g, Rectangle rect,
            boolean customOnly) {
        rect = header.doDraw(g, rect, customOnly);
        rect = subHeader.doDraw(g, rect, customOnly);
        rect = footer.doDraw(g, rect, customOnly);
        rect = subFooter.doDraw(g, rect, customOnly);
        return rect;
    }

    private boolean shouldDrawLegend() {
        return legend.getVisible()
                && (legend.hasCheckBoxes() || (countActiveSeries() > 0));
    }

    /**
     * Internal use.
     * 
     * @param g
     * @param tmp
     * @return Rectangle
     */
    public Rectangle doDrawLegend(IGraphics3D g, Rectangle tmp) {
        if (legend.getVisible()) {
            legend.paint(g, tmp);
            if (legend.getLastValue() >= legend.getFirstValue()) {
                tmp = legend.resizeChartRect(tmp);
            }
        }
        return tmp;
    }

    /**
     * Internal use.<br> Tests if RightWall can be last plotted
     * 
     * @return boolean
     */
    public boolean drawRightWallAfter() {
        Point p1 = graphics3D.calc3DPoint(chartRect.getRight(),
                chartRect.y, 0);
        Point p2 = graphics3D.calc3DPoint(chartRect.getRight(),
                chartRect.getBottom()
                + walls.calcWallSize(axes.getBottom()),
                getAspect().width3D
                + walls.getBack().getSize());
        return p1.x <= p2.x;
    }

    public boolean drawBackWallAfter(int z) {
        final Point[] p = graphics3D.fourPointsFromRect(chartRect, z);
        return !Graphics3D.cull(p);
    }

    private boolean drawBottomWallFirst() {
        return drawHorizWallFirst(chart.getHeight());
    }

    /**
     * Internal use. Tests if LeftWall can be last plotted.
     * 
     * @return boolean
     */
    public boolean drawLeftWallFirst() {
        return drawVertWallFirst(chart.getLeft());
    }
    private Point[] wallP = new Point[4];

    private boolean drawVertWallFirst(int aPos) {
        graphics3D.calc3DPos(wallP[0], aPos, chartRect.getTop(), 0);
        int tmpBottom = chartRect.getBottom() + walls.calcWallSize(axes.getBottom());
        graphics3D.calc3DPos(wallP[1], aPos, tmpBottom, 0);
        graphics3D.calc3DPos(wallP[2], aPos, tmpBottom, aspect.getWidth3D() + walls.getBack().getSize());
        return Graphics3D.cull(wallP);
    }

    private boolean drawHorizWallFirst(int aPos) {
        graphics3D.calc3DPos(wallP[0], chartRect.getLeft(), aPos, 0);
        int tmpLeft = chartRect.getLeft() + walls.calcWallSize(axes.getLeft());
        graphics3D.calc3DPos(wallP[1], tmpLeft, aPos, 0);
        graphics3D.calc3DPos(wallP[2], tmpLeft, aPos, aspect.getWidth3D() + walls.getBack().getSize());
        return Graphics3D.cull(wallP);
    }

    private Rectangle drawTitlesAndLegend(IGraphics3D g, Rectangle tmp,
            boolean beforeSeries) {
        Rectangle rect = tmp;

        // Drawing.FrontPlaneBegin;
        if (beforeSeries) {
            // draw titles and legend before series
            if ((!legend.getCustomPosition()) && shouldDrawLegend()) {
                if (legend.getVertical()) {
                    tmp = doDrawLegend(g, tmp);
                    tmp = drawTitleFoot(g, tmp, false);
                } else {
                    tmp = drawTitleFoot(g, tmp, false);
                    tmp = doDrawLegend(g, tmp);
                }
            } else {
                tmp = drawTitleFoot(g, tmp, false);
            }
        } else {
            // after series
            if (legend.getCustomPosition() && shouldDrawLegend()) {
                tmp = doDrawLegend(g, tmp);
            }
            tmp = drawTitleFoot(g, tmp, true);
        }
        //Drawing.FrontPlaneEnd;

        if (!beforeSeries) {
            if (activeSeriesUseAxis()) {
                if (getAspect().getView3D() && walls.getView3D()) {

                    if (drawRightWallAfter()) {
                        if (walls.getRight().shouldDraw()) {
                            walls.getRight().paint(g, tmp);
                        }

                        drawAxisAfter(axes.getRight());
                    }

                    if (drawLeftWallFirst()) {
                        if (walls.getLeft().shouldDraw()) {
                            walls.getLeft().paint(g, tmp);
                        }

                        drawAxisAfter(axes.getLeft());
                    }

                    /*
                    if (walls.getLeft().getVisible() &&
                    ((!getAspect().getOrthogonal()) &&
                    (getAspect().getRotation() < 270))) {
                    walls.getLeft().paint(g, tmp);
                    axes.getLeft().hideBackGrid = true;
                    axes.getLeft().draw(g, false);
                    axes.getLeft().hideBackGrid = false;
                    }
                     */
                    if (drawBackWallAfter(aspect.width3D)) {

                        drawAxisGridAfter(axes.getTop());
                        drawAxisGridAfter(axes.getBottom());

                        if (walls.getBack().shouldDraw()) {
                            walls.getBack().paint(g, tmp);
                        }
                    }
                }
            }
        }

        return tmp;
    }

    private void drawAxisGridAfter(Axis axis) {
        if (isAxisVisible(axis)) {
            axis.hideSideGrid = true;
            axis.axisDraw.drawTicksAndGrid.drawGrids(axis.axisDraw.ticks.length);
            axis.hideSideGrid = false;
        }
    }

    private void drawAxisAfter(Axis axis) {
        if (isAxisVisible(axis)) {
            axis.hideBackGrid = true;
            axis.draw(false);
            axis.hideBackGrid = false;
        }
    }

    /**
     * Returns the number of active (visible) series.<br>
     * In other words it is a count of Series in Chart that have their Active
     * property set to true.
     *
     * @return int Number of Active (visible) Series.
     */
    public int countActiveSeries() {
        int tmpResult = 0;

        for (int t = 0; t < series.size(); t++) {
            if (series.getSeries(t).getActive()) {
                tmpResult++;
            }
        }

        return tmpResult;
    }

    /**
     * Returns True if Axis is a Custom Axis.
     * 
     * @param axis Axis
     * @return boolean
     */
    public boolean isAxisCustom(Axis axis) {
        return axes.getCustom().indexOf(axis) != -1;
    }

    /**
     * Returns the series that corresponds to the Legend "ItemIndex" position,
     * when the Legend style is "Series".<br>
     * The "OnlyActive" parameter, when false, takes into account all series,
     * visibly active or not.
     *
     * @param itemIndex int
     * @param onlyActive boolean
     * @return Series
     */
    public Series seriesLegend(int itemIndex, boolean onlyActive) {
        int tmp = 0;
        for (int t = 0; t < series.size(); t++) {
            Series s = series.getSeries(t);
            if (s.getShowInLegend() && ((!onlyActive) || s.getActive())) {
                if (tmp == itemIndex) {
                    return s;
                } else {
                    tmp++;
                }
            }
        }
        return null;
    }

    /**
     * Returns the text string corresponding to a Legend position.<br>
     * The Legend position depends on Legend.LegendStyle.<br>
     * If LegendStyle is lsSeries, then the text string will be the
     * SeriesOrValueIndexth Active Series Title.<br>
     * If LegendStyle is lsValues, then the text string will be the formatted
     * SeriesOrValueIndexth value of the first Active Series in the Chart.<br>
     * If LegendStyle is lsAuto and only one Active Series exists in the Chart,
     * then the LegendStyle is considered to be lsValues.<br>
     * If there's more than one Active Series then LegendStyle will be lsSeries.
     *
     * @param seriesOrValueIndex int
     * @return String
     */
    public String formattedLegend(int seriesOrValueIndex) {
        String tmp = legend.formattedLegend(seriesOrValueIndex);

        if (parent != null) {
            tmp = parent.getLegendResolver().getItemText(
                    legend,
                    legend.iLegendStyle,
                    seriesOrValueIndex,
                    tmp);
        }

        return tmp;
    }

    /**
     * Returns maximum Point Count between all Active Series.
     * 
     * @return int
     */
    public int getMaxValuesCount() {
        int result = 0;
        boolean firstTime = true;

        for (int t = 0; t < series.size(); t++) {
            Series s = series.getSeries(t);

            if (s.getActive() && (firstTime || (s.getCount() > result))) {
                result = s.getCount();
                firstTime = false;
            }
        }
        return result;
    }

    /**
     * Accesses all visible Background attributes..
     *
     * @return Panel
     */
    public Panel getPanel() {
        return panel;
    }

    /**
     * Sets all visible Background attributes..
     * 
     * @param value
     */
    public void setPanel(Panel value) {
        panel = value;
        panel.setChart(this);
    }

    /**
     * Internal use.
     * Sets instance that implements the IChart interface.
     *
     * @param value IChart
     */
    public void setParent(IChart value) {
        parent = value;
    }

    /**
     * Printing related attributes.
     *
     * @return Printer
     */
    public Printer getPrinter() {
        return printer;
    }

    /**
     * Printing related attributes.
     * 
     * @param value
     */
    public void setPrinter(Printer value) {
        printer = value;
        printer.setChart(this);
    }

    /**
     * Accesses multiple page characteristics of the Chart.
     *
     * @return Page
     */
    public Page getPage() {
        return page;
    }

    /**
     * Accesses multiple page characteristics of the Chart.
     * 
     * @param value
     */
    public void setPage(Page value) {
        page = value;
        page.setChart(this);
    }

    /**
     * Determines the Legend characteristics.<br>
     * Legend determines the text and drawing attributes of Chart's textual
     * representation of Series and Series values. <br>
     * The Legend class draws a rectangle and for each Series in a Chart (or
     * for each point in a Series) outputs a text representation of that
     * Series (or that point). You can use the Legend.LegendStyle and
     * Legend.TextStyle to control the text used to draw the legend. <br>
     * The Legend can be positioned at Left, Right, Top and Bottom chart sides
     * using Legend.Alignment. <br>
     * Use Legend.Visible to show / hide the Legend. <br>
     * Inverted makes Legend draw text starting from bottom.<br>
     * Frame, Font and Color allow you to change the Legend appearance.<br>
     * Legend.ColorWidth determines the percent width of each item's "colored"
     * mark. <br>
     * Legend.FirstValue controls which Series (or Series point) will be
     * used to draw first Legend item.
     *
     * @return Legend
     */
    public Legend getLegend() {
        return legend;
    }

    /**
     * Determines the Legend characteristics.<br>
     * Legend determines the text and drawing attributes of Chart's textual
     * representation of Series and Series values. <br>
     * The Legend class draws a rectangle and for each Series in a Chart (or
     * for each point in a Series) outputs a text representation of that
     * Series (or that point). You can use the Legend.LegendStyle and
     * Legend.TextStyle to control the text used to draw the legend. <br>
     * The Legend can be positioned at Left, Right, Top and Bottom chart sides
     * using Legend.Alignment. <br>
     * Use Legend.Visible to show / hide the Legend. <br>
     * Inverted makes Legend draw text starting from bottom.<br>
     * Frame, Font and Color allow you to change the Legend appearance.<br>
     * Legend.ColorWidth determines the percent width of each item's "colored"
     * mark. <br>
     * Legend.FirstValue controls which Series (or Series point) will be
     * used to draw first Legend item.
     * 
     * @param value
     */
    public void setLegend(Legend value) {
        legend = value;
        legend.setChart(this);
    }

    /**
     * Defines the Text and formatting attributes to be drawn at the top of
     * the Chart.<br>
     * Use Text to enter the desired Header lines, set Visible to true and
     * change Font, Frame and Brush. Use Alignment to control text output
     * position.
     *
     * @return Header
     */
    public Header getHeader() {
        return header;
    }

    /**
     * Defines the Text and formatting attributes to be drawn at the top of
     * the Chart.<br>
     * Use Text to enter the desired Header lines, set Visible to true and
     * change Font, Frame and Brush. Use Alignment to control text output
     * position.
     * 
     * @param value
     */
    public void setHeader(Header value) {
        header = value;
        header.setChart(this);
    }

    /**
     * Obsolete.&nbsp;Please use Header instead.
     *
     * @return Header
     */
    public Header getTitle() {
        return header;
    }

    /**
     * Obsolete.&nbsp;Please use SubHeader instead.
     *
     * @return Header
     */
    public Header getSubTitle() {
        return subHeader;
    }

    /**
     * Defines the Text and formatting attributes to be drawn at the top of
     * the Chart, just below the Header text.<br>
     * Use Text to enter the desired SubHeader lines, set Visible to true and
     * change Font, Frame and Brush.<br>
     * Use Alignment to control text output position.
     *
     * @return Header
     */
    public Header getSubHeader() {
        return subHeader;
    }

    /**
     * 
     * Defines the Text and formatting attributes to be drawn at the top of
     * the Chart, just below the Header text.<br>
     * Use Text to enter the desired SubHeader lines, set Visible to true and
     * change Font, Frame and Brush.<br>
     * Use Alignment to control text output position.
     * 
     * @param value
     */
    public void setSubHeader(Header value) {
        subHeader = value;
        subHeader.setChart(this);
    }

    /**
     * Defines the Text and formatting attributes to be drawn at the bottom of
     * the Chart.<br>
     * Use Text to enter the desired Footer lines, set Visible to true and
     * change Font, Frame and Brush.<br>
     * Use Alignment to control text output position.
     *
     * @return Footer
     */
    public Footer getFooter() {
        return footer;
    }

    /**
     * Defines the Text and formatting attributes to be drawn at the bottom of
     * the Chart.<br>
     * Use Text to enter the desired Footer lines, set Visible to true and
     * change Font, Frame and Brush.<br>
     * Use Alignment to control text output position.
     * 
     * @param value
     */
    public void setFooter(Footer value) {
        footer = value;
        footer.setChart(this);
    }

    /**
     * Defines the Text and formatting attributes to be drawn at the bottom of
     * the Chart, just above the Footer text.<br>
     * Use Text to enter the desired SubFooter lines, set Visible to true and
     * change Font, Frame and Brush.<br>
     * Use Alignment to control text output position.
     *
     * @return Footer
     */
    public Footer getSubFooter() {
        return subFooter;
    }

    /**
     * Defines the Text and formatting properties to be drawn at the bottom of the 
     * Chart, just above the Footer text.<br>
     * Use the Text property to enter the desired SubFooter lines, set Visible to 
     * true and change the Font, Frame and Brush properties. <br>
     * Use the Alignment property to control text output position.<br><br> 
     * 
     * @param value
     */
    public void setSubFooter(Footer value) {
        subFooter = value;
        subFooter.setChart(this);
    }

    /**
     * 3D view parameters.
     *
     * @return Aspect
     */
    public Aspect getAspect() {
        return aspect;
    }

    /**
     * 3D view parameters.
     * 
     * @param value
     */
    public void setAspect(Aspect value) {
        aspect = value;
        aspect.setChart(this);
    }

    /**
     * Accesses TeeChart Draw attributes.
     *
     * @return IGraphics3D
     */
    public IGraphics3D getGraphics3D() {
        return graphics3D;
    }

    /**
     * Accesses TeeChart Draw attributes.
     * 
     * @param value
     */
    public void setGraphics3D(IGraphics3D value) {
        if (value != null) {
        	if (!getParent().isDisposing())
            {
        		graphics3D = value;
                invalidate();
        	}
        }
    }

    /**
     * Gets the index'th tool in getTools() collection
     *
     * @param index int
     * @return Tool
     */
    public Tool getTool(int index) {
        return tools.getTool(index);
    }

    /**
     * Collection of Tool components contained in this Chart.
     *
     * @return ToolsCollection
     */
    public ToolsCollection getTools() {
        return tools;
    }

    /**
     * Collection of Tool components contained in this Chart.
     * 
     * @param value
     */
    public void setTools(ToolsCollection value) {
        tools = value;
        tools.chart = this;
    }

    /**
     * Accesses left, bottom and back wall characteristics of the Chart.
     *
     * @return Walls
     */
    public Walls getWalls() {
        return walls;
    }

    /**
     * Accesses left, bottom and back wall characteristics of the Chart.
     * 
     * @param value
     */
    public void setWalls(Walls value) {
        walls = value;
        walls.setChart(this);
    }

    /**
     * Accesses the five axes, Top, Left, Right, Bottom and z depthas well as
     * custom axis objects.
     *
     * @return Axes
     */
    public Axes getAxes() {
        return axes;
    }

    /**
     * Accesses the five axes, Top, Left, Right, Bottom and z depthas well as
     * custom axis objects.
     * 
     * @param value
     */
    public void setAxes(Axes value) {
        axes = value;
        axes.setChart(this);
    }

    /**
     * Accesses Chart export attributes.
     *
     * @return Exports
     */
    public Exports getExport() {
        return export;
    }

    /**
     * Accesses Chart export attributes.
     * 
     * @param value
     */
    public void setExport(Exports value) {
        export = value;
        export.setChart(this);
    }

    /**
     * Accesses Chart import attributes.
     *
     * @return Imports
     */
    public Imports getImport() {
        return imports;
    }

    /**
     * Accesses Chart import attributes.
     * 
     * @param value
     */
    public void setImport(Imports value) {
        imports = value;
        imports.setChart(this);
    }

    /**
     * Internal use.
     * 
     * @return int
     */
    public int getSeriesHeight3D() {
        return seriesHeight3D;
    }

    /**
     * Internal use.
     * 
     * @return int
     */
    public int getSeriesWidth3D() {
        return seriesWidth3D;
    }

    /**
     * Defines the Pen chararcteristics of the Legend.
     * 
     * @return ChartPen
     */
    public ChartPen getLegendPen() {
        return legendPen;
    }

    /**
     * Defines the Pen chararcteristics of the Legend.
     * 
     * @param value ChartPen
     */
    public void setLegendPen(ChartPen value) {
        legendPen = value;
    }

    /**
     * Moves Series' index in Chart SeriesList.
     * 
     * @param value Series
     * @param newIndex int
     */
    public void moveSeriesTo(Series value, int newIndex) {
        series.moveTo(value, newIndex);
    }

    /**
     * Removes a Series from the Chart series list, without disposing it.
     *
     * @param value Series
     */
    public void removeSeries(Series value) {
        series.remove(value);
    }

    /**
     * Adds a new Series into the Chart.
     * 
     * @param value Series
     * @return int
     */
    public int addSeries(Series value) {
        int result = series.indexOf(value);
        if (result == -1) {
            series.add(value);
            result = series.size() - 1;
        }
        return result;
    }

    /**
     * Returns the location in pixels of the bottom of the Chart Rectangle.
     * 
     * @return int
     */
    public int getBottom() {
        return getChartBounds().getBottom();
    }

    /**
     * Returns the highest of all Series ZOrder values.
     * 
     * @return int
     */
    public int getMaxZOrder() {
        return maxZOrder;
    }

    /**
     * Sets the highest of Series ZOrder value.
     * 
     * @param value int
     */
    public void setMaxZOrder(int value) {
        maxZOrder = value;
    }

    /**
     * Returns if the Chart should automatically repaint itself when a
     * property has been changed.
     *
     * @return boolean
     */
    public boolean getAutoRepaint() {
        return autoRepaint;
    }

    /**
     * Sets if this Chart should automatically repaint after a property change.
     *
     * @param value boolean
     */
    public void setAutoRepaint(boolean value) {
        autoRepaint = value;
    }

    public boolean getCustomChartRect() {
        return customChartRect;
    }

    public void setCustomChartRect(boolean value) {
        customChartRect = value;
        cachedCustomChartRect = Utils.emptyRectangle();
    }

    /**
     * Returns the rectangle that contains the four main Chart axes.
     *
     * @return Rectangle
     */
    public Rectangle getChartRect() {
        return chartRect;
    }

    /**
     * Sets the rectangle to contain the four main Chart axes.
     *
     * @param value Rectangle
     */
    public void setChartRect(Rectangle value) {
        chartRect = value;
    }

    /**
     * Returns the number of Series associated to this Chart.
     *
     * @return int
     */
    public int getSeriesCount() {
        return series.size();
    }

    public Iterator series() {
        return getSeries().iterator();
    }

    /**
     * Returns collection of Tool components contained in this Chart.
     * 
     * @return Iterator
     */
    public Iterator tools() {
        return getTools().iterator();
    }

    public void doBaseInvalidate() {
        if ((graphics3D != null) && (!graphics3D.getDirty())
                && getAutoRepaint()) {
            graphics3D.setDirty(true);
            if (getParent() != null) {
                getParent().doInvalidate();
            }
        }
    }

    /**
     * Returns the IChart parent of the Chart.
     * 
     * @return IChart
     */
    public IChart getParent() {
        return parent;
    }

//    public void setAutoScale(boolean value)
//    {
//        autoScale = value;
//    }
//    
//    public boolean getAutoScale()
//    {
//        return autoScale;
//    }
    //(calcResult(tmp, isMin, tmpResult, firstTime)
    // min  = tmp
    // max = tmpResult
//   private double calcResult(tmp, isMin, tmpResult, firstTime)
//    {
//              if (minMax.firstTime || (IsMin && (minMax.min < minMax.max)) ||
//                      ((!IsMin) && (minMax.min > minMax.max)))
//      {
//        minMax.firstTime = false;
//      }
//        return minMax;
//    }
    private CalcResultType calcResult(boolean IsMin, double value, CalcResultType minMax) {
        if (minMax.firstTime || (IsMin && (value < minMax.result)) || ((!IsMin) && (value
                > minMax.result))) {
            minMax.firstTime = false;
            minMax.result = value;
        }
        return minMax;
    }

    public boolean isDesignTime() {
        return (parent != null) && parent.isDesignTime();
    }

    public int getScreenHeight() {
        return parent.getScreenHeight();
    }

    public int getScreenWidth() {
        return parent.getScreenWidth();
    }

	@Override
	public Theme getInternalTheme() {
		return _internalTheme;
	}

	@Override
	public void setInternalTheme(Theme theme) {
		_internalTheme = theme;
		
	}
}
