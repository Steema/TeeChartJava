package com.steema.teechart.editors.gallery;

import java.lang.reflect.InvocationTargetException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JPanel;
import com.steema.teechart.languages.Language;
import java.awt.Cursor;
import java.util.ArrayList;

import java.awt.Dimension;
import java.awt.event.KeyEvent;
import javax.swing.JDialog;

import com.steema.teechart.Cursors;
import com.steema.teechart.styles.Series;
import com.steema.teechart.Rectangle;
import com.steema.teechart.IBaseChart;
import com.steema.teechart.functions.Function;
import com.steema.teechart.misc.*;
import com.steema.teechart.functions.CompressOHLC;
import com.steema.teechart.styles.Candle;
import com.steema.teechart.styles.Line;

import com.steema.teechart.drawing.Color;
import com.steema.teechart.drawing.Point;
import com.steema.teechart.editors.gallery.Gallery;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.BorderLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentAdapter;

/**
 *
 * <p>Title: GalleryPanel class</p>
 *
 * <p>Description: An extended JPanel class to hold a Chart Series gallery.</p>
 *
 * <p>Copyright (c) 2005-2019 by Steema Software SL. All Rights Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 *
 */
public class GalleryPanel extends JPanel implements Gallery {


    /**
     * The default number of Rows in Gallery panel.
     */
    static final public int galleryNumRows = 3;

    /**
     * The default number of Columns in Gallery panel.
     */
    static final public int galleryNumCols = 4;

    /**
     * The mouse cursor for Series styles that are not allowed (disabled).
     */
    static final public Cursor cursorDisabled = Cursors.NO;

    /**
     * Determines if Gallery panel will show Chart axis labels or not when maximized.
     */
    static final public boolean checkMaximize = true;

    /**
     * Determines if Gallery panel shows Functions, or only Series.
     */
    public boolean functionsVisible = true;

    private Series tmpSeries;
    private int colWidth;
    private boolean displaySub = true;
    private final boolean checkSeries = true;

    private int numRows = 3;
    private int numCols = 4;
    private int rowHeight;
    private boolean view3D = true;
    private boolean smooth = false;

    private JDialog tmpSub;

    private GalleryPanel tmpG;
    public GalleryChart selectedChart;
    private ArrayList<String> items = new ArrayList<String>();
    public Series selectedSeries;

    public GalleryPanel() {
        super();
        setLayout(null);

        addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent e) {
              resizeCharts();
            }
        });

    }

    /**
     * Determines if the chart gallery will display charts in normal mode
     * or "smooth" mode. <br>
     *
     * Smooth mode shows charts with an eye-pleasing  effect. <br>
     * Default value: false
     *
     * @return boolean
     */
    public boolean getSmooth() {
        return smooth;
    }

    /**
     * The chart gallery will display charts in "smooth" mode when true.<br>
     * Default value: false
     *
     * @param value boolean
     */
    public void setSmooth(boolean value) {
        if (smooth != value) {
            smooth = value;
            for (int t = 0; t < charts.size(); t++) {
                charts.getChart(t).getGraphics3D().setSmoothingMode(smooth);
            }
        }
    }

    /**
     * The GalleryPanel will display a sub-gallery when clicking left-bottom
     * corner small arrow when true.<br>
     * Default value: true
     *
     * @return boolean
     */
    public boolean getDisplaySub() {
        return displaySub;
    }

    /**
     * Sets if GalleryPanel will display a sub-gallery when clicking
     * left-bottom corner small arrow when true.<br>
     * Default value: true
     *
     * @param value boolean
     */
    public void setDisplaySub(boolean value) {
        displaySub = value;
    }

    /**
     * The number of Series type rows to display in the Chart Gallery Panel.
     * <br>
     * Default value: 4
     *
     * @return int
     */
    public int getNumRows() {
        return numRows;
    }

    /**
     * Sets the number of Series type rows to display in the Chart Gallery
     * Panel.<br>
     * Default value: 4
     *
     * @param value int
     */
    public void setNumRows(int value) {
        if (numRows != value) {
            numRows = value;
            resizeCharts();
        }
    }

    /**
     * The number of Series type columns to display in the Chart Gallery Panel.
     * <br>
     * Default value: 4
     *
     * @return int
     */
    public int getNumCols() {
        return numCols;
    }

    /**
     * Sets the number of Series type columns to display in the Chart Gallery
     * Panel. <br>
     * Default value: 4
     *
     * @param value int
     */
    public void setNumCols(int value) {
        if (numCols != value) {
            numCols = value;
            resizeCharts();
        }
    }


    /**
     * View Series Types in Gallery Panel as 3D/2D. <br>
     * Default value: true
     *
     * @return boolean
     */
    public boolean getView3D() {
        return view3D;
    }

    /**
     * View Series Types in Gallery Panel as 3D/2D. <br>
     * Default value: true
     *
     * @param value boolean
     */
    public void setView3D(boolean value) {
    	
        if (view3D != value) {

            view3D = value;

            for (int t = 0; t < charts.size(); t++) {
                GalleryChart c = charts.getChart(t);

                c.getAspect().setView3D(view3D);

                c.setMargins();
            }
        }
    }


    /**
     * Event called when a Chart is selected and double-clicked at Gallery.
     */
    public ActionListener onSelectedChart;

    /**
     * Event called when a Series sub-style is selected at sub-Gallery.
     */
    public ActionListener onSubSelected;

    /**
     * Event called when a Chart is selected (clicked) at Gallery.
     */
    public ActionListener onChangeChart;


    //property OnSubSelected:TNotifyEvent read FOnSubSelected write FOnSubSelected;

    /**
     *
     * <p>Title: GalleryChartCollection class</p>
     *
     * <p>Description: Internal use. Lists Gallery Charts</p>
     *
     * <p>Copyright (c) 2005-2019 by Steema Software SL. All Rights Reserved.</p>
     *
     * <p>Company: Steema Software SL</p>
     *
     */
    final class GalleryChartCollection extends ArrayList {
        private GalleryPanel gallery;

        public GalleryChartCollection(GalleryPanel gallery) {
            super();
            this.gallery = gallery;
        }

        public GalleryChart getChart(int index) {
            return (GalleryChart)super.get(index);
        }

        public void clear() {
            super.clear();
            gallery.removeAll();
        }
    }


    private void chartOnDblClick(GalleryChart chart) {
        selectedChart = chart;
        if (onSelectedChart != null) {
            onSelectedChart.actionPerformed(new ActionEvent(this, 0, ""));
        }
    }

    private void selectChart(GalleryChart c) throws IllegalAccessException,
            InstantiationException {

        if (c != selectedChart) {
            selectedChart = c;
            showSelectedChart();
        }

        if (displaySub) {
            Rectangle r = new Rectangle(0, selectedChart.getHeight() - 12, 12,
                                        12);
            if (r.contains(selectedChart.mousePosition)) {
//                showSubGallery();
            }
        }
    }

    public IBaseChart createSubChart(String name) {
        int tmp = tmpG.charts.size();
        GalleryChart c = null;
        try {
            c = tmpG.createChart(Utils.seriesTypesIndex(tmpSeries),
                                 name, null);
        } catch (NoSuchMethodException ex) {
            Logger.getLogger(GalleryPanel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalArgumentException ex) {
            Logger.getLogger(GalleryPanel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvocationTargetException ex) {
            Logger.getLogger(GalleryPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
        for (int t = 0; t < c.getSeriesCount(); t++) {
            c.getSeries(t).setSubGallery(tmp);
        }

        return c.getChart();
    }

    protected void addSubCharts(Series s) {
        tmpG = this;
        tmpSeries = s;
        addSubCharts(this);
    }

    private void addSubCharts(GalleryPanel aGallery) {
        aGallery.charts.clear();

        if (tmpSeries != null) {
            paintImmediately(getBounds());
            tmpSeries.createSubGallery(this);
            aGallery.calcChartWidthHeight();
        }
    }

    private void createSubGallery(GalleryPanel aGallery, Class type) throws
            IllegalAccessException,
            InstantiationException, 
            NoSuchMethodException, 
            IllegalArgumentException, 
            InvocationTargetException {
        tmpSeries = Series.createNewSeries(null, type, null);
        addSubCharts(aGallery);

        int tmpN = aGallery.charts.size() / aGallery.getNumCols();
        if ((aGallery.charts.size() % aGallery.getNumCols()) > 0) {
            tmpN++;
        }

        aGallery.getParent().setSize(aGallery.getParent().getWidth(),
                                     38 + aGallery.rowHeight * tmpN);
        aGallery.setNumRows(tmpN);
        tmpSeries.dispose();
    }

    protected void subSelected(Object sender) throws
            IllegalAccessException,
            InstantiationException, 
            NoSuchMethodException, 
            IllegalArgumentException, 
            InvocationTargetException {
        selectedChart.tag = tmpG.charts.indexOf(tmpG.selectedChart);

        if (getSubIndex() == 0) {
            Class tmpClass = selectedChart.getSeries(0).getClass();
            int tmpNum = selectedChart.getSeriesCount();
            selectedChart.getSeries().clear();

            for (int t = 1; t <= tmpNum; t++) {
                Series.createNewSeries(selectedChart.getChart(), tmpClass, null);
            }

            selectedChart.getSeries(0).galleryChanged3D(tmpG.getView3D());
            for (int t = 0; t < selectedChart.getSeriesCount(); t++) {
                selectedChart.getSeries(t).prepareForGallery(true);
            }
        } else {
            for (int t = 0; t < selectedChart.getSeriesCount(); t++) {
                Series s = selectedChart.getSeries(t);
                setSubSelected(s, getSubIndex());
            }
        }

        if (tmpSub != null) {
            tmpSub.setVisible(false);
            tmpSub.dispose();
        }
    }

    public void setSubSelected(Series s, int index) {
        s.setSubGallery(index);
    }

    private void showSubGallery() throws IllegalAccessException,
            InstantiationException, 
            NoSuchMethodException, 
            IllegalArgumentException, 
            InvocationTargetException {

        if (tmpSub != null) {
            tmpSub.dispose();
        }
        selectedChart.unFocus(view3D);

        tmpSub = new JDialog();
        tmpSub.setResizable(true);

        java.awt.Point tmpP=selectedChart.getLocation();
        Point p = selectedChart.pointToScreen(new Point(tmpP.x,tmpP.y));

        tmpSub.setLocation(p.x, p.y + selectedChart.getHeight());
        tmpSub.setSize(new Dimension(300, 200));

        /** @todo CONNECT EVENT ! */
        //tmpSub.KeyDown += new KeyEventHandler(tmpSub_KeyDown);
        //tmpSub.KeyPreview = true;

        tmpG = new GalleryPanel();
        tmpG.setView3D(view3D);

        /** @todo CONNECT EVENT ! */
        //tmpG.OnSelectedChart += new EventHandler(SubSelected);

        tmpG.setDisplaySub(false);
        tmpSub.getContentPane().add(tmpG, BorderLayout.CENTER);

        // tmpG.iSubGallery=true;  <-- not needed?

        tmpG.setSize(tmpG.getWidth(), tmpSub.getHeight());

        createSubGallery(tmpG, selectedChart.getSeries(0).getClass());

        tmpG.showSelectedChart();

        java.awt.Rectangle screenBounds = getParent().getGraphicsConfiguration().
                                          getDevice().getDefaultConfiguration().
                                          getBounds();

        java.awt.Rectangle tmp = tmpSub.getBounds();

        if (tmp.x + tmp.width > screenBounds.width) {
            tmpSub.getLocation().x = screenBounds.width - tmpSub.getWidth();
        }

        tmpSub.setModal(true);
        tmpSub.setVisible(true);

        /** @todo HOW TO CAPTURE RESULT? */
        //if (tmpSub.ShowDialog() == DialogResult.Ok) {
        //    if (OnSubSelected != null) {
        //        OnSubSelected(this, EventArgs.Empty);
        //    }
        //}

        tmpSub.dispose();

        showSelectedChart();
    }

    /**
     * List of Charts showing at Gallery panel.
     */
    public GalleryChartCollection charts = new GalleryChartCollection(this);

    /**
     * Returns currently selected Function style, or null if none.
     *
     * @return Class
     */
    public Class getFunctionType() {
        if ((selectedChart != null) && (selectedChart.getSeries(0).getFunction() != null)) {
            return selectedChart.getSeries(0).getFunction().getClass();
        } else {
            return null;
        }
    }
    
    public Object getInitArgs() {
        return (selectedChart == null) ? null : selectedChart.initTag;
    }

    public Class getSeriesType() {
        return (selectedChart != null) ? selectedChart.getSeries(0).getClass() : null;
    }

    public int getSubIndex() {
        return (selectedChart == null) ? 0 : selectedChart.tag;
    }

    private void showSelectedChart() {
        if (selectedChart == null) {
            if (charts.size() > 0) {
                selectedChart = charts.getChart(0);
            }
        }

        if (selectedChart != null) {
            selectedChart.doFocus(view3D);
            fireChangeChart();
        }

        for (int t = 0; t < charts.size(); t++) {
            GalleryChart c = charts.getChart(t);
            if (c != selectedChart) {
                c.unFocus(view3D);
            }
        }
    }

    transient ActionListener changeChart;

    private void fireChangeChart() {
        if (changeChart != null) {
            changeChart.actionPerformed(new ActionEvent(this, 0, ""));
        }
    }

    private void findSelectedChart() {
        selectedChart = null;
        if (selectedSeries != null) {
            for (int t = 0; t < charts.size(); t++) {
                GalleryChart tmp = charts.getChart(t);
                if
                        (
                                //(tmp.getSeries(0) instanceof SelectedSeries.getClass())
                                // &&
                                (tmp.getSeries(0).getFunction() == null)) {
                    selectedChart = charts.getChart(t);
                    break;
                }
            }
        }
    }

    static private void createSeries(GalleryChart c, int s, Object tag) throws NoSuchMethodException, IllegalArgumentException, InvocationTargetException {
        try {
            for (int t = 1; t <= Math.max(1, Utils.seriesGalleryCount[s]); t++) {
                Series.createNewSeries(c.getChart(), Utils.seriesTypesOf[s], null, 0, tag);
            }
        } catch (IllegalAccessException e) {} catch (InstantiationException e) {}
    }

    private Point getChartXY(GalleryChart c) {
        int tmp = charts.indexOf(c);
        return new Point(tmp % numCols, tmp / numCols);
    }

    private void resizeChart(GalleryChart c) {

        if ((getNumCols() > 0) && (getNumRows() > 0)) {

            Point tmp = getChartXY(c);
            int tmp2 = 1; // BevelWidth+BorderWidth;
            c.setLocation(tmp2 + (tmp.x * colWidth),
                          tmp2 + (tmp.y * rowHeight));

            c.setSize(Math.min(colWidth, getWidth() - tmp2) - 1,
                      Math.min(rowHeight, getHeight() - tmp2) - 1);

            c.checkShowLabels();
        }
    }

    public void resizeCharts() {
        calcChartWidthHeight();
        for (int t = 0; t < charts.size(); t++) {
            resizeChart(charts.getChart(t));
        }
    }

    private void calcChartWidthHeight() {
        if ((getNumRows() > 0) && (getNumCols() > 0)) {
            int tmp = 1; //BevelWidth+BorderWidth;
            rowHeight = (getBounds().height - tmp) / getNumRows();
            colWidth = (getBounds().width - tmp) / getNumCols();
        } else {
            rowHeight = 0;
            colWidth = 0;
        }
    }

    static private String functionDescription(Class f) throws
            IllegalAccessException,
            InstantiationException {
        return Function.newInstance(f).getDescription();
    }

    private GalleryChart createChart(Class seriesType) throws NoSuchMethodException, IllegalArgumentException, InvocationTargetException {
        return createChart(Utils.seriesTypesIndex(seriesType), "", null);
    }


    private GalleryChart createChart(int index, Class function) throws NoSuchMethodException, IllegalArgumentException, InvocationTargetException {
        try {
            return createChart(index, functionDescription(function), function);
        } catch (IllegalAccessException e) {
            return null;
        } catch (InstantiationException e) {
            return null;
        }
    }
    
    private GalleryChart createChart(int index, String title, Class function) throws NoSuchMethodException, IllegalArgumentException, InvocationTargetException {
        return createChart(index, title, function, null);
    }

    private GalleryChart createChart(int index, String title, Class function, Object tag) throws NoSuchMethodException, IllegalArgumentException, InvocationTargetException {
        GalleryChart result = new GalleryChart(getSmooth());
        charts.add(result);

        add(result);

        resizeChart(result);

        result.setVisible(true);

        if (function == null) {
            createSeries(result, index, tag);
        } else {

            try {
                //CDI CompressOHLC
                for (int t = 1; t <= 2; t++) {
                    if (function == CompressOHLC.class) {
                        Series.createNewSeries(result.getChart(),
                                               Candle.class,
                                               function);
                    } else {
                        Series.createNewSeries(result.getChart(),
                                               Line.class, function);
                    }
                }
            } catch (IllegalAccessException e) {
            } catch (InstantiationException e) {
            }
        }

        if(tag != null) {
            result.initTag = tag;
        }
        String tmpTitle = title;

        if ((title.length() == 0) && (result.getSeriesCount() > 0)) {
            tmpTitle = result.getSeries(0).getDescription();
        }

        result.getHeader().setLines(tmpTitle.split(Language.getString("LineSeparator"))); // , 5);  ???

        boolean disabledSeries = checkSeries && (selectedSeries != null) &&
                                 (!result.getSeries(0).isValidSourceOf(
                                         selectedSeries));

        if (disabledSeries) {
            result.setCursor(Cursors.NO);

            MouseListener[] tmp = result.getMouseListeners();
            for (int t = 0; t < tmp.length; t++) {
                result.removeMouseListener(tmp[t]);
            }

            /** @todo UNCONNECT EVENTS ! */
            //result.Enter -= new EventHandler(ChartEvent);

            result.getHeader().getFont().setColor(Color.GRAY);
            result.getWalls().getLeft().getPen().setColor(Color.GRAY);
            result.getWalls().getBottom().getPen().setColor(Color.GRAY);
            result.getAxes().getLeft().getAxisPen().setWidth(1);
            result.getAxes().getLeft().getAxisPen().setColor(Color.WHITE);
            result.getAxes().getBottom().getAxisPen().setWidth(1);
            result.getAxes().getBottom().getAxisPen().setColor(Color.WHITE);
        } else {
            result.setCursor(Cursors.HAND);

            result.addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent e) {

                    if (e.getClickCount() == 1) {
                        try {
                            selectChart((GalleryChart) e.getSource());
                        } catch (InstantiationException ex) {
                        } catch (IllegalAccessException ex) {
                        }
                    } else
                    if (e.getClickCount() == 2) {
                        chartOnDblClick((GalleryChart) e.getSource());
                    }

                }
            });

            /** @todo CONNECT EVENTS ! */
            //result.Enter += new EventHandler(ChartEvent);
        }

        if (result.getSeriesCount() > 0) {
            result.getSeries(0).galleryChanged3D(view3D);
        }

        for (int t = 0; t < result.getSeriesCount(); t++) {
            result.getSeries(t).prepareForGallery(!disabledSeries);
        }

        result.setMargins();
        result.checkShowLabels();

        // if (! ISubGallery) result.OnKeyDown=ChartKeyDown;

        return result;
    }

    static public String galleryPages(int index) {
        switch (index) {
        case 0:
            return Language.getString("GalleryStandard");
        case 1:
            return Language.getString("GalleryStats");
        case 2:
            return Language.getString("GalleryFinancial");
        case 3:
            return Language.getString("GalleryExtended");
        case 4:
            return Language.getString("Gallery3D");
        case 5:
            return Language.getString("GalleryGauges");
        case 6:
            return Language.getString("GalleryWorldMaps");
        default:
            return Language.getString("GallerySamples");
        }
    }

    static private boolean validFunction(int aFunctionType, String aPage) {
        return galleryPages(Utils.functionGalleryPage[aFunctionType]).equals(
                aPage);
    }

    static private boolean validSeries(int aSeriesType, String aPage) {
        return galleryPages(Utils.seriesGalleryPage[aSeriesType]).equals(aPage);
    }

    public void createGalleryPage(String pageName) throws NoSuchMethodException, IllegalArgumentException, InvocationTargetException {
        createGalleryPage(pageName, false);
    }

    private int calcCount(String pageName, boolean functions)
    {
      int tmpResult=0;

      if (functions)
      {
         for (int t = 0; t < Utils.FUNCTIONTYPESCOUNT; t++)
                if (validFunction(t, pageName)) tmpResult++;
      }
      else
      {
         for (int t = 0; t < Utils.SERIESTYPESCOUNT; t++)
                if (validSeries(t, pageName))  tmpResult++;
         
         if(pageName.equals(Language.getString("GalleryWorldMaps"))) {
             tmpResult = com.steema.teechart.styles.WorldMapType.getNames().length;
         }
      }

      return tmpResult;
    }

    private void createWorldMapChart(Class seriesType) throws NoSuchMethodException, IllegalArgumentException, InvocationTargetException {
       String[] names = com.steema.teechart.styles.WorldMapType.getNames();
       Object tag;
       for(int i=0; i < names.length; ++i) {
          tag = com.steema.teechart.styles.WorldMapType.fromName(names[i]);
          createChart(Utils.seriesTypesIndex(seriesType), names[i], null, tag);  
       }
    }

    public void createGalleryPage(String pageName, boolean functions) throws NoSuchMethodException, IllegalArgumentException, InvocationTargetException {
        charts.clear();

        int tmpCount=calcCount(pageName, functions);

        if (tmpCount>((numRows-1)*numCols))    //MM July 05, correct recount of rows was failing
          numRows=1+((tmpCount-1) / numCols);
        else
          numRows=galleryNumRows;

        if (functions) {
            for (int t = 0; t < Utils.FUNCTIONTYPESCOUNT; t++) {
                if (validFunction(t, pageName)) {
                    items.add(Utils.functionTypesOf[t].toString());
                    createChart(t, Utils.functionTypesOf[t]);
                }
            }
        } else {
            for (int t = 0; t < Utils.SERIESTYPESCOUNT; t++) {
                if (validSeries(t, pageName)) {
                    items.add(Utils.seriesTypesOf[t].toString());

                    if(Utils.seriesTypesOf[t] == com.steema.teechart.styles.World.class) {
                        createWorldMapChart(Utils.seriesTypesOf[t]);
                    }
                    else {
                        createChart(Utils.seriesTypesOf[t]);
                    }
                }
            }
        }

        resizeCharts();
        findSelectedChart();
        showSelectedChart();
    }

    public void createGallerySeries(Class seriesType) throws NoSuchMethodException, IllegalArgumentException, InvocationTargetException {
        items.add(seriesType.toString());
        createChart(seriesType);
    }

    private void tmpSub_KeyDown(Object sender, KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            tmpSub.setVisible(false);
            tmpSub.dispose();
//            tmpSub.DialogResult = DialogResult.Cancel;
        }
    }

    // returns the gallery Chart in XY position (Column / Row)
    private GalleryChart findChartXY(int x, int y) {
        for (int t = 0; t < charts.size(); t++) {
            GalleryChart c = charts.getChart(t);
            Point tmp = getChartXY(c);
            if ((x == tmp.x) && (y == tmp.y)) {
                return c;
            }
        }
        return null;
    }

    protected void processKeyEvent(KeyEvent e) {
        super.processKeyEvent(e);

        if (e.getID() == KeyEvent.KEY_PRESSED) {
            Point tmp = getChartXY(selectedChart);

            switch (e.getKeyCode()) {
            case KeyEvent.VK_LEFT:
                if (tmp.x > 0) {
                    tmp.x--;
                }
                break;
            case KeyEvent.VK_RIGHT:
                if (tmp.x < getNumCols()) {
                    tmp.x++;
                }
                break;
            case KeyEvent.VK_UP:
                if (tmp.y > 0) {
                    tmp.y--;
                }
                break;
            case KeyEvent.VK_DOWN: {
                if (e.isAltDown()) {
                    if (displaySub) {
                        try {
                            try {
                                showSubGallery();
                            } catch (NoSuchMethodException ex) {
                                Logger.getLogger(GalleryPanel.class.getName()).log(Level.SEVERE, null, ex);
                            } catch (IllegalArgumentException ex) {
                                Logger.getLogger(GalleryPanel.class.getName()).log(Level.SEVERE, null, ex);
                            } catch (InvocationTargetException ex) {
                                Logger.getLogger(GalleryPanel.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        } catch (InstantiationException ex) {
                        } catch (IllegalAccessException ex) {
                        }
                    }
                } else if (tmp.y < getNumRows()) {
                    tmp.y++;
                }
            }
            break;
            case KeyEvent.VK_ENTER:
                chartOnDblClick(selectedChart);
                break;
            }

            GalleryChart tmpChart = findChartXY(tmp.x, tmp.y);
            if ((tmpChart != null) && (tmpChart.getCursor() == Cursors.HAND)) {
                try {
                    selectChart(tmpChart);
                } catch (InstantiationException ex1) {
                } catch (IllegalAccessException ex1) {
                }
            }
        }
    }
}

