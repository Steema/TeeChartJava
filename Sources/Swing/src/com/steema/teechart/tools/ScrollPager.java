/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.steema.teechart.tools;

import com.steema.teechart.Cursor;
import com.steema.teechart.IBaseChart;
import com.steema.teechart.PanelMarginUnits;
import com.steema.teechart.Rectangle;
import com.steema.teechart.ScrollMode;
import com.steema.teechart.TChart;
import com.steema.teechart.ZoomDirections;
import com.steema.teechart.drawing.Color;
import com.steema.teechart.drawing.DashStyle;
import com.steema.teechart.events.ChangeEvent;
import com.steema.teechart.events.ChartDrawEvent;
import com.steema.teechart.events.ChartPaintAdapter;
import com.steema.teechart.misc.Utils;
import com.steema.teechart.styles.PointerStyle;
import com.steema.teechart.styles.SeriesPointer;
import com.steema.teechart.events.DragListener;
import com.steema.teechart.events.FrameworkMouseEvent;
import com.steema.teechart.languages.Language;
import com.steema.teechart.styles.Series;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

/**
 *
 * @author Shunyata Kharg
 */
public class ScrollPager extends ToolSeries implements DragListener {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ScrollPager(IBaseChart chart) {
        super(chart);
        if (chart != null) {
            chart.getTools().add(this);
        }
    }

    public ScrollPager() {
        this((IBaseChart) null);
    }
    private SubChartTool subChartTool;
    private ColorBand colorBandTool;
    private TChart subChartTChart;
    private double divisionRatio = 3;
    private boolean colorBandClicked = false, startPointerClicked = false, endPointerClicked = false;
    private double scrollValue, origStart, origEnd;
    private SeriesPointer startPointer, endPointer;
    private Color pointHLColor = new Color(255, 0, 0, 100);
    private Color oldColor = Color.EMPTY;
    private int mouseButtonPressed;

    private void subChartTChart_AfterDraw() {
        if (getSeries() != null) {
            getSubChartTChart().getGraphics3D().setGraphics(getChart().getGraphics3D());

            if ((getColorBandTool() != null) && (getColorBandTool().getAxis() != null)) {

                if (getColorBandTool().getAxis().calcPosValue(getColorBandTool().getStart()) > getColorBandTool().getAxis().iStartPos
                        && getColorBandTool().getAxis().calcPosValue(getColorBandTool().getStart()) < getColorBandTool().getAxis().iEndPos
                        && getStartLinePointer().getVisible()) {

                    getStartLinePointer().draw(getColorBandTool().getAxis().calcPosValue(getColorBandTool().getStart()),
                            subChartTChart.getAxes().getBottom().getPosition(), getStartLinePointer().getColor());
                }

                if (getColorBandTool().getAxis().calcPosValue(getColorBandTool().getEnd()) > getColorBandTool().getAxis().iStartPos
                        && getColorBandTool().getAxis().calcPosValue(getColorBandTool().getEnd()) < getColorBandTool().getAxis().iEndPos
                        && getEndLinePointer().getVisible()) {

                    getEndLinePointer().draw(getColorBandTool().getAxis().calcPosValue(getColorBandTool().getEnd()),
                            subChartTChart.getAxes().getBottom().getPosition(), getEndLinePointer().getColor());
                }
            }
        }
    }

    public SeriesPointer getStartLinePointer() {
        if (startPointer == null) {
            startPointer = new SeriesPointer(null, null);
            startPointer.setStyle(PointerStyle.TRIANGLE);
        }
        return startPointer;
    }

    public SeriesPointer getEndLinePointer() {
        if (endPointer == null) {
            endPointer = new SeriesPointer(null, null);
            endPointer.setStyle(PointerStyle.TRIANGLE);
        }
        return endPointer;
    }

    public TChart getSubChartTChart() {
        if (subChartTChart == null) {
            subChartTChart = getSubChartTool().getCharts().addChart("SubChartTChart");

            subChartTChart.addChartPaintListener(new ChartPaintAdapter() {

                public void chartPainted(ChartDrawEvent e) {
                    subChartTChart_AfterDraw();
                }
            }); 
            
            MouseAdapter l = new MouseAdapter() {

                public void mousePressed(MouseEvent e) {
                    if (getColorBandTool().clicked(e.getX(), e.getY())) {
                        if (!getColorBandTool().getStartLine().clicked(e.getX(), e.getY()) && !getColorBandTool().getEndLine().clicked(e.getX(), e.getY())) {
                            colorBandClicked = true;
                        }
                    }

                    if (colorBandClicked) {
                        scrollValue = getSubChartTChart().getSeries(0).xScreenToValue(e.getX());
                        origStart = getColorBandTool().getStart();
                        origEnd = getColorBandTool().getEnd();
                    }

                    startPointerClicked = seriesPointerClick(e.getX(), e.getY(), getStartLinePointer(), true);
                    endPointerClicked = seriesPointerClick(e.getX(), e.getY(), getEndLinePointer(), false);

                    if (!getColorBandTool().getStartLine().getDragging()) {
                        getColorBandTool().getStartLine().setDragging(startPointerClicked);
                    }
                    if (!getColorBandTool().getEndLine().getDragging()) {
                        getColorBandTool().getEndLine().setDragging(endPointerClicked);
                    }
                }

                public void mouseReleased(MouseEvent e) {
                    if (colorBandClicked) {
                        colorBandClicked = false;
                    }
                    if (startPointerClicked) {
                        startPointerClicked = false;
                    }
                    if (endPointerClicked) {
                        endPointerClicked = false;
                    }
                }
            };

            subChartTChart.addMouseListener(l);

            MouseMotionListener ml = new MouseMotionListener() {

                public void mouseDragged(MouseEvent e) {
                    if (colorBandClicked) {
                        if (getSubChartTChart().getSeriesCount()>0) {
                            double scrolled = getSubChartTChart().getSeries(0).xScreenToValue(e.getX()) - scrollValue;
                            getColorBandTool().setStart(origStart + scrolled);
                            getColorBandTool().setEnd(origEnd + scrolled);
                            getChart().getAxes().getBottom().setMinMax(getColorBandTool().getStart(), getColorBandTool().getEnd());
                        }
                    }
                }

                public void mouseMoved(MouseEvent e) {
                    highLightSeriesPointer(e.getX(), e.getY(), getStartLinePointer(), true);
                    highLightSeriesPointer(e.getX(), e.getY(), getEndLinePointer(), false);
                }

                private void highLightSeriesPointer(int x, int y, SeriesPointer point, boolean start) {
                    if (seriesPointerClick(x, y, point, start)) {
                        point.getPen().setColor(pointHLColor);
                    } else if (!Utils.colorIsEmpty(oldColor)) {
                        point.getPen().setColor(oldColor);
                    } else if (point.getPen().getColor() != pointHLColor) {
                        oldColor = point.getPen().getColor();
                    }
                }
            };

            subChartTChart.addMouseMotionListener(ml);
        }
        return subChartTChart;
    }

    public SubChartTool getSubChartTool() {
        if (subChartTool == null) {
            subChartTool = new SubChartTool();
            subChartTool.InternalUse = true;
            subChartTool.setChart(getChart());
        }
        return subChartTool;
    }

    public ColorBand getColorBandTool() {
        if (colorBandTool == null) {
            colorBandTool = new ColorBand(getSubChartTChart().getChart());
        }
        return colorBandTool;
    }

    public Color getPointerHighlightColor() {
        return pointHLColor;
    }

    public void setPointerHighlightColor(Color value) {
        pointHLColor = setColorProperty(pointHLColor, value);
    }

    public double getDivisionRatio() {
        return divisionRatio;
    }

    public void setDivisionRatio(double value) {
        if (divisionRatio != value) {
            divisionRatio = value;
            setUpChartLayout();
            getChart().invalidate();
        }
    }

    public void setSeries(Series value) {
        super.setSeries(value);
        if ((value != null) && (getChart() != null)) {
            setUpScrollPager();
        }
    }

    @Override
    public void chartEvent(ChartDrawEvent ce) {
        if (getActive()) {
            getSubChartTool().chartEvent(ce);
        }
    }

    @Override
    public Cursor mouseEvent(FrameworkMouseEvent e, Cursor c) {
        Cursor result = Cursor.DEFAULT;
        if (getActive()) {

            result = getSubChartTool().mouseEvent(e, c);

            if ((getSubChartTool().getCharts().size() > 0) && !getSubChartTool().getCharts().get(0).getBounds().contains(e.getX(), e.getY()) && startPointerClicked && endPointerClicked) {
                result = Cursor.DEFAULT;
            }

            if (e.getID() == FrameworkMouseEvent.MOUSE_PRESSED) {
                mouseButtonPressed = e.getButton();
            }

            if (e.getID() == FrameworkMouseEvent.MOUSE_MOVED || e.getID() == FrameworkMouseEvent.MOUSE_DRAGGED) {
                if (getColorBandTool().clicked(e.getX(), e.getY())) {
                    result = Cursor.HAND;
                }

                if (mouseButtonPressed == getChart().getPanning().getMouseButton()) {
                    getColorBandTool().setStart(getChart().getAxes().getBottom().getMinimum());
                    getColorBandTool().setEnd(getChart().getAxes().getBottom().getMaximum());
                }

            } else if (e.getID() == FrameworkMouseEvent.MOUSE_RELEASED) {
                getColorBandTool().setStart(getChart().getAxes().getBottom().getMinimum());
                getColorBandTool().setEnd(getChart().getAxes().getBottom().getMaximum());
            }
        }

        return result;
    }

    private void setUpChartLayout() {
        int width = Math.max(getChart().getParent().getControlWidth(), getChart().getWidth());
        int height = Math.max(getChart().getParent().getControlHeight(), getChart().getHeight());
        
        getSubChartTChart().setWidth(width);
        getSubChartTChart().setHeight(Utils.round(height / getDivisionRatio()));

        int top = height - getSubChartTChart().getHeight();

        getSubChartTool().getCharts().get(0).setLeft(0);
        getSubChartTool().getCharts().get(0).setTop(top);

        getSubChartTChart().getPanel().setMarginUnits(PanelMarginUnits.PIXELS);
        if (getSubChartTChart().getPanel().getMarginRight() == 0) {
            getSubChartTChart().getPanel().setMarginRight(20);
        }
        
        getChart().setCustomChartRect(true);
        Rectangle rect = Rectangle.fromLTRB(0, 10, getSubChartTChart().getWidth() - (int)getSubChartTChart().getPanel().getMarginRight(), getSubChartTChart().getBounds().y);
        getChart().setChartRect(rect);
    }

    public void resetColorBand() {
        if (getSeries() != null) {
            getColorBandTool().setStart(getSeries().getXValues().getMinimum() + (getSeries().getXValues().getRange() / 3));
            getColorBandTool().setEnd(getSeries().getXValues().getMaximum() - (getSeries().getXValues().getRange() / 3));
        }
    }

    private void setUpScrollPager() {
        setUpChartAspect();
        setUpChartLayout();
        copySeries();
        
        getSubChartTool().setChart(getChart());
        getStartLinePointer().setSeries(getSubChartTChart().getSeries(0));
        getStartLinePointer().setChart(getSubChartTChart().getChart());
        getEndLinePointer().setSeries(getSubChartTChart().getSeries(0));
        getEndLinePointer().setChart(getSubChartTChart().getChart());
        getColorBandTool().setChart(getSubChartTChart().getChart());
      
        setUpColorBand();
    }
    
    private void setUpChartAspect() {
        getChart().getAspect().setView3D(false);
        getChart().getLegend().setVisible(false);
        getChart().getZoom().setDirection(ZoomDirections.HORIZONTAL);
        getChart().getZoom().getPen().setVisible(true);
        getChart().getZoom().getPen().setStyle(DashStyle.SOLID);
        getChart().getPanning().setAllow(ScrollMode.HORIZONTAL);
	
        getSubChartTChart().getAspect().setView3D(false);
        getSubChartTChart().getHeader().setVisible(false);
        getSubChartTChart().getPanning().setAllow(ScrollMode.NONE);
        getSubChartTChart().getZoom().setAllow(false);
    }

    private void copySeries() {
        com.steema.teechart.styles.Series s = getSeries().cloneSeries();
        s.setChart(null);
        getSubChartTChart().getSeries().add(s);
    }

    private void setUpColorBand() {
        getColorBandTool().setAxis(getSubChartTChart().getAxes().getBottom());
        resetColorBand();
        getColorBandTool().setResizeStart(true);
        getColorBandTool().setResizeEnd(true);
        Color color = getSeries().getColor();
        getColorBandTool().setColor(new Color(color.getRed(), color.getGreen(), color.getBlue(), 100));
        getColorBandTool().getStartLinePen().setVisible(false);
        getColorBandTool().getEndLinePen().setVisible(false);
        getColorBandTool().getStartLine().addDragListener(this);
        getColorBandTool().getEndLine().addDragListener(this);

        getChart().getAxes().getBottom().setMinMax(getColorBandTool().getStart(), getColorBandTool().getEnd());
    }

    private boolean seriesPointerClick(int x, int y, SeriesPointer point, boolean start) {
        int tmpX = getColorBandTool().getAxis().calcPosValue(start ? getColorBandTool().getStart() : getColorBandTool().getEnd());
        int tmpY = getSubChartTChart().getAxes().getBottom().getPosition();

        return (Math.abs(tmpX - x) < point.getHorizSize()) && (Math.abs(tmpY - y) < point.getVertSize());
    }

    public void dragFinished(ChangeEvent e) {
    }

    public void dragMoving(ChangeEvent e) {
        getChart().getAxes().getBottom().setMinMax(getColorBandTool().getStart(), getColorBandTool().getEnd());
    }
    
    /**
     * Gets descriptive text.
     *
     * @return String
     */
    @Override
    public String getDescription() {
        return Language.getString("ScrollPager");
    }

    /**
     * Gets detailed descriptive text.
     *
     * @return String
     */
    @Override
    public String getSummary() {
        return Language.getString("ScrollPagerSummary");
    }
}
