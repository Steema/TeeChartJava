package com.steema.teechart.tools;

import com.steema.teechart.events.ChangeEvent;
import com.steema.teechart.events.ChartDrawEvent;
import com.steema.teechart.languages.Language;
import com.steema.teechart.Cursor;
import com.steema.teechart.Rectangle;

import com.steema.teechart.IBaseChart;
import com.steema.teechart.drawing.Graphics3D;
import com.steema.teechart.drawing.IGraphics3D;
import com.steema.teechart.drawing.Gradient;
import com.steema.teechart.drawing.ChartBrush;
import com.steema.teechart.drawing.Color;
import com.steema.teechart.drawing.ChartPen;
import com.steema.teechart.drawing.Point;
import com.steema.teechart.events.FrameworkMouseEvent;
import com.steema.teechart.axis.Axis;
import com.steema.teechart.events.DragListener;

/**
 *
 * <p>Title: ColorBand class</p>
 *
 * <p>Description: Color band tool, use it to display a coloured rectangle
 * (band) at the specified axis and position.</p>
 *
 * <p>Copyright (c) 2005-2019 by Steema Software SL. All Rights Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 *
 */
public class ColorBand extends ToolAxis implements DragListener {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private boolean drawBehind = true;
    private double end;
    private double start;

    private Rectangle boundsRect;
    private ColorLine fLineEnd;
    private ColorLine fLineStart;


    public ColorBand() {
        this((IBaseChart)null);
    }

    public ColorBand(IBaseChart c) {
        super(c);

        fLineEnd = newColorLine();
        fLineStart = newColorLine();
        setLines();
    }

    private ColorLine newColorLine() {
        ColorLine result = new ColorLine();
        result.setActive(false);
        result.setDragRepaint(true);
        result.setDraw3D(false);
        result.setDrawBehind(true);
        // TODO
       // result.dragLine = new ColorLineToolOnDragEventHandler();
        result.addDragListener(this);

        return result;
    }


    private void setLines() {
        fLineEnd.setValue(this.getEnd());
        fLineStart.setValue(this.getStart());
        invalidate();
    }

    public Cursor mouseEvent(FrameworkMouseEvent e, Cursor c) {
        if(fLineEnd.getActive() && fLineStart.getActive()) {
            if(fLineStart.clicked(e.getX(), e.getY())) {
                c = fLineEnd.mouseEvent(e, c);
                c = fLineStart.mouseEvent(e, c);
            }
            else {
                c = fLineStart.mouseEvent(e, c);
                c = fLineEnd.mouseEvent(e, c);                
            } 
        }
        else if(fLineEnd.getActive()) {
            c = fLineEnd.mouseEvent(e, c);
        }
        else if(fLineStart.getActive()) {
            c = fLineStart.mouseEvent(e, c);
        }

        return c;
    }

     public void setAxis(Axis value) {
         super.setAxis(value);
         fLineEnd.setAxis(value);
         fLineStart.setAxis(value);

     }


    /**
     * Gets descriptive text.
     *
     * @return String
     */
    public String getDescription() {
        return Language.getString("ColorBandTool");
    }

    /**
     * Gets detailed descriptive text.
     *
     * @return String
     */
    public String getSummary() {
        return Language.getString("ColorBandSummary");
    }

    /**
     * Draws the Colorband behind the series values when true.<br>
     * Default value: true
     *
     * @return boolean
     */
    public boolean getDrawBehind() {
        return drawBehind;
    }

    /**
     * Draws the Colorband behind the series values when true.<br>
     * Default value: true
     *
     * @param value boolean
     */
    public void setDrawBehind(boolean value) {
        drawBehind = setBooleanProperty(drawBehind, value);
        fLineEnd.setDrawBehind(value);
        fLineStart.setDrawBehind(value);
    }

    /**
     * The Start Axis value of colorband.
     *
     * @return double
     */
    public double getStart() {
        return start;
    }

    /**
     * Sets Start Axis value of colorband.
     *
     * @param value double
     */
    public void setStart(double value) {
        start = setDoubleProperty(start, value);
        setLines();
    }

    /**
     * The End Axis value of colorband.
     *
     * @return double
     */
    public double getEnd() {
        return end;
    }

    /**
     * Sets End Axis value of colorband.
     *
     * @param value double
     */
    public void setEnd(double value) {
        end = setDoubleProperty(end, value);
        setLines();
    }

    /**
     * The Transparency of ColorBand as percentage.<br>
     * Default value: 0
     *
     * @return int
     */
    public int getTransparency() {
        return getBrush().getTransparency();
    }

    /**
     * Sets the Transparency of ColorBand as percentage.<br>
     * Default value: 0
     *
     * @param value int
     */
    public void setTransparency(int value) {
        getBrush().setTransparency(value);
    }

    /**
     * Sets Band colour gradient.
     *
     * @return Gradient
     */
    public Gradient getGradient() {
        return getBrush().getGradient();
    }

    /**
     * SThe Band Color.
     *
     * @return Color
     */
    public Color getColor() {
        return getBrush().getColor();
    }

    /**
     * Sets Band Color.
     *
     * @param value Color
     */
    public void setColor(Color value) {
        getBrush().setColor(value);
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
     * Contains formatting properties for the automatic line used to drag the start value of the ColorBand tool at runtime.
     *
     * @return ColorLine
     */
    public ColorLine getStartLine() {
            return fLineStart;
    }

    /**
     * Contains formatting properties for the automatic line used to drag the end value of the ColorBand tool at runtime.
     *
     * @return ColorLine
     */
    public ColorLine getEndLine() {
            return fLineEnd;
    }

    /**
    * Gets or sets if the ColorBand tool allows mouse dragging of the edge corresponding to the end value.
    *
   * @return boolean
   * */
    public boolean getResizeEnd() {
            return getEndLine().getActive();
    }

    public void setResizeEnd(boolean value)
    {
        getEndLine().setChart(chart);
        getEndLine().setActive(value);
        this.invalidate();
    }

    /*
    * Gets or sets if the ColorBand tool allows mouse dragging of the edge corresponding to the start value.
    *
    * @return boolean
    */
    public boolean getResizeStart() {
        return getStartLine().getActive();
    }

    public void setResizeStart(boolean value)
    {
        getStartLine().setChart(chart);
        getStartLine().setActive(value);
        this.invalidate();
    }

    /*
    * Pen used to draw the starting line of the color band tool.
    *
    * @return ChartPen
    */
    public ChartPen getStartLinePen() {
            return getStartLine().getPen();
    }

    /*
    * Pen used to draw the ending line of the color band tool.
    *
    * @return ChartPen
    */
    public ChartPen getEndLinePen() {
            return getEndLine().getPen();
    }

    private void paintBand() {
        if (iAxis != null) {
            boundsRect = new Rectangle(chart.getChartRect());
            double tmp0 = start;
            double tmp1 = end;

            boolean tmpDraw;

            double tmp;

            if (iAxis.getInverted()) {
                if (tmp0 < tmp1) {
                    tmp = tmp0;
                    tmp0 = tmp1;
                    tmp1 = tmp;
                }
                tmpDraw = (tmp1 <= iAxis.getMaximum()) && (tmp0 >= iAxis.getMinimum());
            } else {
                if (tmp0 > tmp1) {
                    tmp = tmp0;
                    tmp0 = tmp1;
                    tmp1 = tmp;
                }
                tmpDraw = (tmp0 <= iAxis.getMaximum()) && (tmp1 >= iAxis.getMinimum());
            }

            if (tmpDraw) {
                if (iAxis.getHorizontal()) {
                    boundsRect.x = Math.max(iAxis.iStartPos, iAxis.calcPosValue(tmp0));
                    boundsRect.width = Math.min(iAxis.iEndPos, iAxis.calcPosValue(tmp1)) -
                              boundsRect.x;
                    if (!getPen().getVisible()) {
                        boundsRect.width++;
                    }
                } else {
                    boundsRect.y = Math.max(iAxis.iStartPos, iAxis.calcPosValue(tmp1));
                    boundsRect.height = Math.min(iAxis.iEndPos, iAxis.calcPosValue(tmp0)) -
                               boundsRect.y;
                    boundsRect.x++;
                    if (!getPen().getVisible()) {
                        boundsRect.height++;
                        boundsRect.width++;
                    }
                }

                IGraphics3D g = chart.getGraphics3D();
                g.setBrush(getBrush());
                g.setPen(getPen());

                if ( getGradient().getVisible() && chart.getAspect().getOrthogonal() ) {
                  Rectangle tmpR=boundsRect;
                  tmpR.width--;
                  tmpR.height--;
                  getGradient().draw(g,g.calcRect3D(tmpR, getZPosition()));
                  getBrush().setVisible(false);
                }


                if (chart.getAspect().getView3D()) {
                    g.rectangle(boundsRect, getZPosition());
                } else {
                    g.rectangle(boundsRect);
                }
            }
        }
    }

    private int getZPosition() {
        return drawBehind ? chart.getAspect().getWidth3D() : 0;
    }

    public boolean clicked(int x, int y) {
        if (boundsRect != null) {
            final Point[] p = chart.getGraphics3D().fourPointsFromRect(boundsRect, getZPosition());
            return Graphics3D.pointInPolygon(new Point(x, y), p);
        } else {
            return false;
        }
    }
    
    public void chartEvent(ChartDrawEvent e) {
        super.chartEvent(e);
        if ((((e.getID()==ChartDrawEvent.PAINTING) &&
              (e.getDrawPart()==ChartDrawEvent.SERIES)) && drawBehind) ||
            (((e.getID()==ChartDrawEvent.PAINTED) &&
              (e.getDrawPart()==ChartDrawEvent.CHART)) && (!drawBehind))) {
            paintBand();
        }
    }

    public void dragFinished(ChangeEvent e) {
        if(!((ColorLine)e.getSource()).getDragRepaint()) {
            if(e.getSource().equals(fLineStart)) {
                start = ((ColorLine)e.getSource()).getValue();
            }
            else {
                end = ((ColorLine)e.getSource()).getValue();
            }
        }
    }

    public void dragMoving(ChangeEvent e) {
        if(((ColorLine)e.getSource()).getDragRepaint()) {
            if(e.getSource().equals(fLineStart)) {
                start = ((ColorLine)e.getSource()).getValue();
            }
            else {
                end = ((ColorLine)e.getSource()).getValue();
            }
        } 
    }
}

