package com.steema.teechart.tools;

import com.steema.teechart.IBaseChart;
import com.steema.teechart.drawing.ChartBrush;
import com.steema.teechart.drawing.ChartPen;
import com.steema.teechart.drawing.Color;
import com.steema.teechart.drawing.Gradient;
import com.steema.teechart.drawing.IGraphics3D;
import com.steema.teechart.drawing.Point;
import com.steema.teechart.events.ChartDrawEvent;
import com.steema.teechart.events.SeriesPaintAdapter;
import com.steema.teechart.languages.Language;
import com.steema.teechart.styles.Series;

/**
 *
 * <p>Title: SeriesBand class</p>
 *
 * <p>Description: Series Band tool.</p>
 *
 *  <p>Use it to display a band between two (line) series.</p>
 *  <p>created 2003-12-14 by mkaul@leuze.de</p>
 *
 * <p>Copyright (c) 2005-2019 by Steema Software SL. All Rights Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 *
 */
public class SeriesBand extends ToolSeries {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private double boundValue;
    private boolean drawBehindSeries;
    private Series series2;

    private boolean iSerie1Drawed;
    private boolean iSerie2Drawed;

    //region public members
    public SeriesBand() {
        this((IBaseChart) null);
    }

    public SeriesBand(IBaseChart c) {
        super(c);
        drawBehindSeries = true;

        getBrush().setColor(Color.WHITE);
        getPen().setVisible(false);
    }

    /**
     * Constant value to be used as one of the limits of band filled areas. <br>
     * Note: BoundValue property is only used when the <see cref="Series2"/>
     * property is not set (ie: is null). <br>
     *
     * @return double
     */
    public double getBoundValue() {
        return boundValue;
    }

    /**
     * Sets a constant value to be used as one of the limits of band 
     * filled areas. <br>
     * Note: BoundValue property is only used when the <see cref="Series2"/>
     * property is not set (ie: is null). <br>
     *
     * @param value double
     */
    public void setBoundValue(double value) {
        boundValue = value;
    }

    /**
     * Flag which causes filling to occur before or after the series are
     * displayed. <br>
     * Default value: true
     *
     * @return boolean
     */
    public boolean getDrawBehindSeries() {
        return drawBehindSeries;
    }

    /**
     * Sets a flag which causes filling to occur before or after the series are
     * displayed. <br>
     * Default value: true
     *
     * @param value boolean
     */
    public void setDrawBehindSeries(boolean value) {
        if (drawBehindSeries != value) {
            drawBehindSeries = value;
            invalidate();
        }
    }

    /**
     * Gets descriptive text.
     *
     * @return String
     */
    public String getDescription() {
        return Language.getString("SeriesBandTool");
    }

    /**
     * Gets detailed descriptive text.
     *
     * @return String
     */
    public String getSummary() {
        return Language.getString("SeriesBandSummary");
    }

    /**
     * Second series associated to this tool. <br>
     * SeriesBand tool needs two series to fill the area in between them. <br>
     *
     * @return Series
     */
    public Series getSeries2() {
        return series2;
    }

    /**
     * Sets the second series associated to this tool.<br>
     * SeriesBand tool needs two series to fill the area in between them. <br>
     *
     * @param value Series
     */
    public void setSeries2(Series value) {
        SetSeries2(value);
    }

    /**
     * Element Brush characteristics.
     *
     * @return ChartBrush
     */
    public ChartBrush getBrush() {
        if (bBrush == null) {
            bBrush = new ChartBrush(chart);
        }
        return bBrush;
    }

    /**
     * Set Brush characteristics.
     *
     * @param value ChartBrush
     */
    public void setBrush(ChartBrush value) {
        bBrush = value;
    }

    /**
     * Indicates the kind of pen used to draw Series Band.
     *
     * @return ChartPen
     */
    public ChartPen getPen() {
        if (pPen == null) {
            pPen = new ChartPen(chart, Color.BLACK);
        }
        return pPen;
    }

    /**
     * Determines the kind of pen used to draw Series Band.
     *
     * @param value ChartPen
     */
    public void setPen(ChartPen value) {
        if (value != null) {
            pPen.assign(value);
        }
    }

    /**
     * The amount of semi-glass effect (opacity) to apply when filling the area
     * between the two series as percentage.
     * Default value: 0
     *
     * @return int
     */
    public int getTransparency() {
        return getBrush().getTransparency();
    }

    /**
     * Sets the amount of semi-glass effect (opacity) to apply when filling the
     * area between the two series as percentage.
     * Default value: 0
     *
     * @param value int
     */
    public void setTransparency(int value) {
        getBrush().setTransparency(value);
    }

    /**
     * The gradient colors used to fill the area between the two series.
     *
     * @return Gradient
     */
    public Gradient getGradient() {
        return bBrush.getGradient();
    }

    /**
     * Sets the gradient colors used to fill the area between the two series.
     *
     * @param value Gradient
     */
    public void setGradient(Gradient value) {
        bBrush.setGradient(value);
    }

    //private members
    private void setEvents(final Series aSeries) {
        if (aSeries != null) {
            aSeries.removeSeriesPaintListener(new SeriesPaintAdapter() {
                public void seriesPainting(ChartDrawEvent e) {
                    doBeforeDrawValues(aSeries);
                };

                public void seriesPainted(ChartDrawEvent e) {
                    doAfterDrawValues(aSeries);
                };
            });
            
            aSeries.addSeriesPaintListener(new SeriesPaintAdapter() {
                public void seriesPainting(ChartDrawEvent e) {
                    doBeforeDrawValues(aSeries);
                };

                public void seriesPainted(ChartDrawEvent e) {
                    doAfterDrawValues(aSeries);
                };
            });
        }
    }

    //protected and internal members
    public void chartEvent(ChartDrawEvent ce) {
        super.chartEvent(ce);
        if ((ce.getID()==ChartDrawEvent.PAINTING) &&
              (ce.getDrawPart()==ChartDrawEvent.SERIES)) {
            iSerie1Drawed = false;
            iSerie2Drawed = false;
        }
    }

    public void setSeries(Series value) {
        super.setSeries(value);
        setEvents(iSeries);
        //Invalidate();
    }

    protected void SetSeries2(Series value) {
        if (series2 != value) {
            series2 = value;
            setEvents(series2);
        }
    }

    //protected void doBeforeDrawValues(Object sender, Graphics3D g) {
    protected void doBeforeDrawValues(Object sender) {
        if (drawBehindSeries) {
            if (iSeries != null && sender == iSeries) {
                iSerie1Drawed = true;
            }
            if (series2 != null && sender == series2) {
                iSerie2Drawed = true;
            }

            if (series2 != null) {
                if (!(iSerie1Drawed && iSerie2Drawed)) {
                    drawBandTool();
                }
            }
            else {
                drawBandTool();
            }
        }
    }

    //protected void doAfterDrawValues(Object sender, Graphics3D g) {
    protected void doAfterDrawValues(Object sender) {
        if (!drawBehindSeries) {
            if (iSeries != null && sender == iSeries) {
                iSerie1Drawed = true;
            }
            if (series2 != null && sender == series2) {
                iSerie2Drawed = true;
            }

            if (iSerie1Drawed && (iSerie2Drawed || series2 == null)) {
                drawBandTool();
            }
        }
    }

    protected void drawBandTool() {
        int l1, l2, i, tmpZ = 0;

        Point[] tmpPoints;
        //Graphics3D g = chart.getGraphics3D();
        IGraphics3D g = chart.getGraphics3D();

        int tmpMax;

        if (getActive() && chart != null && iSeries != null) {
            iSeries.calcFirstLastVisibleIndex();
            if (series2 != null) {
                series2.calcFirstLastVisibleIndex();
            }

            if ((series2 != null && iSeries.getFirstVisible() != -1 && 
                    series2.getFirstVisible() != -1) || 
                    iSeries.getFirstVisible() != -1) {
                l1 = (iSeries.getLastVisible() - iSeries.getFirstVisible()) + 1;

                if (iSeries.drawBetweenPoints && iSeries.getFirstVisible() > 0) {
                    ++l1;
                }

                if (series2 != null) {
                    l2 = (series2.getLastVisible() - 
                            series2.getFirstVisible()) + 1;

                    if (iSeries.drawBetweenPoints && 
                            series2.getFirstVisible() > 0) {
                        ++l2;
                    }
                }
                else {
                    l2 = 2;
                }

                tmpPoints = new Point[l1 + l2];

                if (tmpPoints != null) {
                    i = 0;
                    if (iSeries.getFirstVisible() != -1) {
                        tmpMax = Math.max(0, iSeries.getFirstVisible() - 1);
                        for (int t = tmpMax; t <= iSeries.getLastVisible(); t++) {
                            tmpPoints[i] = new Point(iSeries.calcXPos(t),
                                    iSeries.calcYPos(t));
                            i++;
                        }
                    }

                    if (series2 != null) {
                        if (series2.getFirstVisible() != -1) {
                            tmpMax = Math.max(0, series2.getFirstVisible() - 1);
                            for (int t = series2.getLastVisible(); t >= tmpMax;
                                    t--) {
                                tmpPoints[i] = new Point(series2.calcXPos(t),
                                        series2.calcYPos(t));
                                i++;
                            }
                        }
                    }
                    else {                        
                        tmpPoints[i] = new Point(tmpPoints[i - 1].x,
                                iSeries.calcYPosValue(boundValue));
                        tmpPoints[i + 1] = new Point(tmpPoints[0].x,
                                tmpPoints[i].y);
                    }

                    if (series2 != null) {
                        tmpZ = Math.max(iSeries.getStartZ(),
                                series2.getStartZ());
                    }
                    else {
                        tmpZ = iSeries.getStartZ();
                    }

                    g.setBrush(bBrush);
                    g.setPen(pPen);
                    g.clipCube(chart.getChartRect(), 0,
                            chart.getAspect().getWidth3D());
                    g.polygon(tmpZ, tmpPoints);
                    g.unClip();
                }
            }
        }
    }
}
