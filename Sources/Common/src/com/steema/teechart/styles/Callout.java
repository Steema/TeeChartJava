package com.steema.teechart.styles;

import com.steema.teechart.drawing.Color;
import com.steema.teechart.drawing.ChartPen;
import com.steema.teechart.drawing.IGraphics3D;
import com.steema.teechart.drawing.Point;

/**
 *
 * <p>Title: Callout class</p>
 *
 * <p>Description: Mark Callout pointer characteristics</p>
 *
 * <p>Copyright (c) 2005-2019 by Steema Software SL. All Rights
 * Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 *
 */
public class Callout extends SeriesPointer {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// when True, the Marks arrow pen
    // color is changed if the point has
    // the same color.
    private static final boolean CHECKMARKARROWCOLOR = false;

    private ChartPen arrow;
    private ArrowHeadStyle arrowHead = ArrowHeadStyle.NONE;
    private int distance = 0;
    private int arrowHeadSize = 8;

    public Callout() {
        this((ISeries)null);
    }

    public Callout(ISeries s) {
        super((s != null) ? s.getChart() : null, s);

        setStyle(PointerStyle.RECTANGLE);
        getBrush().setDefaultColor(Color.BLACK);
        draw3D = false;
        bVisible = true;
    }

    public void draw(Color c, Point pFrom, Point pTo, int z) {
        IGraphics3D tmpGra = chart.getGraphics3D();

        if (getArrow().getVisible()) {
            prepareCanvas(tmpGra, getColor());

            tmpGra.setPen(getArrow());

            if (CHECKMARKARROWCOLOR &&
                ((getArrow().getColor() == c) ||
                 (getArrow().getColor() == chart.getPanel().getColor()))) {
                tmpGra.getPen().setColor((chart.getPanel().getColor() ==
                                          Color.BLACK) ?
                                         Color.WHITE : Color.BLACK);
            }

            if (arrowHead == ArrowHeadStyle.LINE) {
                tmpGra.arrow(false, pFrom, pTo, arrowHeadSize, arrowHeadSize, z);
            } else
            if (arrowHead == ArrowHeadStyle.SOLID) {
                tmpGra.arrow(true, pFrom, pTo, arrowHeadSize, arrowHeadSize, z);
            } else
            if (arrowHead == ArrowHeadStyle.NONE) {
                if (chart.getAspect().getView3D()) {
                    tmpGra.moveTo(pFrom, z);
                    tmpGra.lineTo(pTo, z);
                } else {
                    tmpGra.line(pFrom, pTo);
                }
            }
        }

        if (arrowHead == ArrowHeadStyle.NONE && getVisible()) {
            prepareCanvas(tmpGra, getColor());

            Point tmpFrom = pFrom;

            if (chart.getAspect().getView3D()) {
                tmpFrom = tmpGra.calc3DPoint(pFrom, z);
            }

            super.draw(tmpGra, chart.getAspect().getView3D(),
                       tmpFrom.x, tmpFrom.y, getHorizSize(), getVertSize(),
                       getColor(), getStyle());
        }
    }

    /**
     * Arrow line between a Series Mark and a Series point.<br>
     * It is also used by Annotation tool to draw a line connecting the
     * annotation and the series point.
     *
     * @return ChartPen
     */
    public ChartPen getArrow() {
        if (arrow == null) {
            arrow = new ChartPen(chart, Color.WHITE);
        }
        return arrow;
    }

    /**
     * Sets the Arrow line between a Series Mark and a Series point.<br>
     * It is also used by Annotation tool to draw a line connecting the
     * annotation and the series point.
     *
     * @param value ChartPen
     */
    public void setArrow(ChartPen value) {
        getArrow().assign(value);
    }

    /**
     * Determines if callout line will display an "arrow" head at the end or
     * not. <br>
     * See TArrowHeadStyle enumerated values for options.
     *
     *
     * @return ArrowHeadStyle
     */
    public ArrowHeadStyle getArrowHead() {
        return arrowHead;
    }

    /**
     * Determines if callout line will display an "arrow" head at the end or
     * not. <br>
     *
     *
     * @param value ArrowHeadStyle
     */
    public void setArrowHead(ArrowHeadStyle value) {
        if (arrowHead != value) {
            arrowHead = value;
            invalidate();
        }
    }

    /**
     * The size in pixels to display the arrow head at the end of the
     * callout line.
     *
     * @return int
     */
    public int getArrowHeadSize() {
        return arrowHeadSize;
    }

    /**
     * Sets the size in pixels of the arrow head at the end of the
     * callout line.
     *
     * @param value int
     */
    public void setArrowHeadSize(int value) {
        if (arrowHeadSize != value) {
            arrowHeadSize = value;
            invalidate();
        }
    }

    /**
     * The length in pixels between a series point and the line connecting the
     * series mark or annotation.
     *
     * @return int
     */
    public int getDistance() {
        return distance;
    }

    /**
     * The length in pixels between a series point and the line connecting the
     * series mark or annotation.
     *
     * @param value int
     */
    public void setDistance(int value) {
        if (distance != value) {
            distance = value;
            invalidate();
        }
    }
}

