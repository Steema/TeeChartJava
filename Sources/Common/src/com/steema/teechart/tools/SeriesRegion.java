package com.steema.teechart.tools;

import com.steema.teechart.IBaseChart;
import com.steema.teechart.Rectangle;
import com.steema.teechart.drawing.ChartBrush;
import com.steema.teechart.drawing.ChartPen;
import com.steema.teechart.drawing.Color;
import com.steema.teechart.drawing.Gradient;
import com.steema.teechart.drawing.IGraphics3D;
import com.steema.teechart.drawing.Point;
import com.steema.teechart.events.ChartDrawEvent;
import com.steema.teechart.languages.Language;
import com.steema.teechart.misc.Utils;

/**
 *
 * <p>Title: SeriesRegion class</p>
 *
 * <p>Description: Series Region tool.</p>
 *
 * <p>It colors bounded area under the (series) curve.</p>
 *
 * <p>Copyright (c) 2005-2019 by Steema Software SL. All Rights Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 *
 */
public class SeriesRegion extends ToolSeries {

    private static final long serialVersionUID = 1L;
    private boolean autoBound = true;
    private boolean drawBehindSeries = true;
    private boolean useOrigin = false;
    private double lBound, uBound, origin;

    //region public members
    public SeriesRegion() {
        this((IBaseChart) null);
    }

    public SeriesRegion(IBaseChart c) {
        super(c);

        getBrush().setColor(Color.WHITE);
    }

    /**
     * Gets descriptive text.
     *
     * @return String
     */
    public String getDescription() { 
        return Language.getString("SeriesRegionTool");
    }
    
    /**
     * Gets detailed descriptive text.
     *
     * @return String
     */
    public String getSummary() { 
        return Language.getString("SeriesRegionSummary");
    }
    
    /**
     * If true, region left and right bounds are equal to series minimum and
     * maximum x value. <br>
     *
     * @return boolean
     */
    public boolean getAutoBound() {
        return autoBound;
    }

    public void setAutoBound(boolean value) {
        if (autoBound != value) {
            autoBound = value;
        }
    }

    /**
     * Defines region left (series not mandatory values) lower bound. <br>
     *
     * @return double
     */
    public double getLowerBound() {
        return lBound;
    }

    public void setLowerBound(double value) {
        if (lBound != value) {
            lBound = value;
        }
    }

    /**
     * Defines region right (series not mandatory values) upper bound. <br>
     *
     * @return double
     */
    public double getUpperBound() {
        return uBound;
    }

    public void setUpperBound(double value) {
        if (uBound != value) {
            uBound = value;
        }
    }

    /**
     * Region bottom or upper limit. Used only if <see cref="UseOrigin"/> is
     * true. <br>
     *
     * @return double
     */
    public double getOrigin() {
        return origin;
    }

    public void setOrigin(double value) {
        if (origin != value) {
            origin = value;
        }
    }

    /**
     * If true, region bottom or upper limit is defined by <see cref="Origin"/>.
     * <br>
     *
     * @return boolean
     */
    public boolean getUseOrigin() {
        return useOrigin;
    }

    public void setUseOrigin(boolean value) {
        if (useOrigin != value) {
            useOrigin = value;
        }
    }

    /**
     * If true, draw region behind series. If false, draw region over series.
     * <br>
     *
     * @return boolean
     */
    public boolean getDrawBehindSeries() {
        return drawBehindSeries;
    }

    public void setDrawBehindSeries(boolean value) {
        if (drawBehindSeries != value) {
            drawBehindSeries = value;
        }
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
     * Indicates the kind of pen used to draw Series Region.
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
     * Determines the kind of pen used to draw Series Region.
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
     * in the region as percentage. Default value: 0
     *
     * @return int
     */
    public int getTransparency() {
        return getBrush().getTransparency();
    }

    /**
     * Sets the amount of semi-glass effect (opacity) to apply when filling the
     * area in the region as percentage. Default value: 0
     *
     * @param value int
     */
    public void setTransparency(int value) {
        getBrush().setTransparency(value);
    }

    /**
     * The gradient colors used to fill the area in the region.
     *
     * @return Gradient
     */
    public Gradient getGradient() {
        return bBrush.getGradient();
    }

    /**
     * Sets the gradient colors used to fill the area in the region.
     *
     * @param value Gradient
     */
    public void setGradient(Gradient value) {
        bBrush.setGradient(value);
    }

    //protected and internal members
    public void chartEvent(ChartDrawEvent ce) {
        super.chartEvent(ce);
        if (getSeries() != null) {
            if (((ce.getID() == ChartDrawEvent.PAINTING) && (ce.getDrawPart() == ChartDrawEvent.SERIES) && (drawBehindSeries))
                    || ((ce.getID() == ChartDrawEvent.PAINTED) && (ce.getDrawPart() == ChartDrawEvent.SERIES) && (!drawBehindSeries))) {
                drawRegion();
            }
        }
    }

    private void drawRegion() {
        if (getActive() && (getChart() != null) && (getSeries() != null)) {
            double lb = getSeries().getNotMandatory().getMinimum();
            double ub = getSeries().getNotMandatory().getMaximum();

            if (!getAutoBound()) {
                lb = Math.max(lb, lBound);
                ub = Math.min(ub, uBound);
            }

            // plot only if it makes sense
            if ((ub > getSeries().getNotMandatory().getMinimum()) && (lb < getSeries().getNotMandatory().getMaximum())) {

                intersectionPointResults tmp = new intersectionPointResults();
                tmp = intersectionPoint(lb, tmp);
                int first = tmp.index;
                double yl = tmp.value;
                tmp = intersectionPoint(ub, tmp);
                int last = tmp.index;
                double yu = tmp.value;

                if (last < first) {
                    int tmpInt = first;
                    first = last;
                    last = tmpInt;
                }

                int plen = last - first + 1;
                Point[] pts = new Point[plen + 4]; // need four extra points

                for (int i = 0; i < plen; i++) {
                    pts[i] = new Point();
                    pts[i].x = getSeries().calcXPos(i + first);
                    pts[i].y = getSeries().calcYPos(i + first);
                }

                // upper bound intersect point
                pts[plen] = new Point();
                pts[plen].x = getSeries().calcXPosValue(ub);
                pts[plen].y = getSeries().calcYPosValue(yu);

                // upper bound origin point
                pts[plen + 1] = new Point();
                pts[plen + 1].x = pts[plen].x;
                pts[plen + 1].y = getUseOrigin() ? getSeries().calcYPosValue(origin) : getSeries().getVertAxis().iEndPos;

                // lower bound origin point
                pts[plen + 2] = new Point();
                pts[plen + 2].x = getSeries().calcXPosValue(lb);
                pts[plen + 2].y = pts[plen + 1].y;

                // lower bound intersect point
                pts[plen + 3] = new Point();
                pts[plen + 3].x = pts[plen + 2].x;
                pts[plen + 3].y = getSeries().calcYPosValue(yl);

                IGraphics3D gr = getChart().getGraphics3D();
                gr.setBrush(getBrush());
                gr.setPen(getPen());

                int zpos = drawBehindSeries ? getSeries().getEndZ() : getSeries().getStartZ();

                Rectangle tmpR = gr.rectFromRectZ(getChart().getChartRect(), zpos);
                gr.clipRectangle(tmpR);

                gr.polygon(zpos, pts);
                gr.unClip();
            }
        }
    }

    static final class intersectionPointResults {

        int index;
        double value;
    }

    /// <summary>
    /// Using point x coordinate it calculates point y coordinate
    /// </summary>
    /// <param name="val">intersection point x coordinate</param>
    /// <param name="y">returns intersection point y coordinate.</param>
    /// <returns>Point index</returns>
    private intersectionPointResults intersectionPoint(double val, intersectionPointResults result) {
        int i = 0;
        result.value = getSeries().getMandatory().getValue(i);
        while ((val > getSeries().getNotMandatory().getValue(i)) && (i < getSeries().getCount())) {
            i++;
        }

        // We have two choices:
        // #1: value is exactly at point coordinate
        // #2: value is between two points - use linear interpolation to calculate y
        if (val == getSeries().getNotMandatory().getValue(i)) {
            result.value = getSeries().getMandatory().getValue(i);
        } else {
            double k;
            if ((i > 0) && (i < getSeries().getCount())) {
                k = getSeries().getMandatory().getValue(i) - getSeries().getMandatory().getValue(i - 1);
                k /= (getSeries().getNotMandatory().getValue(i) - getSeries().getNotMandatory().getValue(i - 1));
                result.value = getSeries().getMandatory().getValue(i - 1) + k * (val - getSeries().getNotMandatory().getValue(i - 1));
            }
        }

        result.index = i;
        return result;
    }
}
