package com.steema.teechart.styles;

import com.steema.teechart.editors.gallery.Gallery;
import com.steema.teechart.languages.Language;

import com.steema.teechart.Rectangle;
import com.steema.teechart.drawing.Color;
import com.steema.teechart.drawing.DashStyle;
import com.steema.teechart.drawing.Point;
import com.steema.teechart.IBaseChart;
import com.steema.teechart.drawing.IGraphics3D;
import com.steema.teechart.misc.Utils;

/**
 *
 * <p>Title: FastLine class</p>
 *
 * <p>Description: The FastLine Series is an extremely simple Series component
 * that draws its points as fast as possible.</p>
 *
 * <p>Example:
 * <pre><font face="Courier" size="4">
 * myChart.getAxes().setVisible(true);
 * myChart.setClipPoints(true);
 *
 * // hide things for better speed
 * myChart.getAspect().setView3D(false);
 * myChart.getLegend().setVisible(false);
 * myChart.getFooter().setVisible(false);
 * myChart.getHeader().setVisible(false);
 * </font></pre></p>
 *
 * <p>Copyright (c) 2005-2019 by Steema Software SL. All Rights Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 */
public class FastLine extends BaseLine {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private boolean autoRepaint = true;
    private boolean drawAll = true;
    private DrawAllPointsStyle drawAllStyle = DrawAllPointsStyle.FIRST;

    transient private Point old = new Point();
    transient private IGraphics3D internalG;
    transient private boolean internal3D;

    private boolean invertedStairs;
    private boolean stairs;
    private boolean ignoreNulls = true;    

    public FastLine(IBaseChart c) {
        super(c);
        allowSinglePoint = false;
        drawBetweenPoints = true;
        
        setTreatNulls(TreatNullsStyle.IGNORE);
        getLinePen().setUsesVisible(false);        
    }

    public FastLine() {
        this((IBaseChart)null);
    }

    public void setChart(IBaseChart value) {
        super.setChart(value);

        getLinePen().setChart(value);

        /** @todo FINISH ! */
        //pLinePen.ColorChanged=linePenColorChanged;
    }
    
    public double getMaxYValue() {
        if (getTreatNulls() == TreatNullsStyle.IGNORE) {
            return super.getMaxYValue();
        }
        else {
            return calcMinMaxValue(false);
        }
    }

    public double getMinYValue() {
        if (getTreatNulls() == TreatNullsStyle.IGNORE) {
            return super.getMinYValue();
        }
        else {
            return calcMinMaxValue(true);
        }
    }

    /**
     * Repaints Chart after any changes are made.<br>
     * Use AutoRepaint false to disable Chart repainting whilst, for example,
     * adding a large number of points to a FastLine Series. This avoids
     * repainting of the Chart whilst the points are added.<br>
     * AutoRepaint may be re-enabled, followed by a manual Repaint command
     * when all points are added. <br>
     * Default value: true
     *
     * @return boolean
     */
    public boolean getAutoRepaint() {
        return autoRepaint;
    }

    /**
     * Repaints Chart after any changes are made.<br>
     * Default value: true
     *
     * @param value boolean
     */
    public void setAutoRepaint(boolean value) {
        autoRepaint = value;
    }

    //private void linePenColorChanged(Object o, EventArgs e) {
    //    setColor(pLinePen.getColor());
    //}

    private void prepareCanvas(IGraphics3D g) {
        g.setPen(linePen);
        g.getPen().setColor(getColor());
    }
    
    private boolean  isNewPixelPosition(Point p) {
        boolean result = true;
        if (p.x == old.x)
            if ((!drawAll && drawAllStyle == DrawAllPointsStyle.FIRST) || (p.y == old.y)) result = false;
        return result;
    }
                
	private boolean shouldDrawPoint(int valueIndex, Point p) {
		boolean result = !isNull(valueIndex);
		if (result) {			
            result = isNewPixelPosition(p);

            // CDI TF02014420 TF02015234 + #2008 
            if ((isNull(valueIndex-1)) && (doNotPaintNull() || (getTreatNulls() == TreatNullsStyle.SKIP && valueIndex - 1 == firstVisible))) {
				result = false;
				doMove(p);
				if (stairs) {
					old = p;
				}
			}
		} 
		else {
			if (IgnoreNull())
				result = isNewPixelPosition(p);
			else {
				if (treatnulls == TreatNullsStyle.SKIP) {
					p = old;
				}
				else {
					if (!drawAll && treatnulls == TreatNullsStyle.DONOTPAINT) {
						switch (drawAllStyle.getValue()) {
						case 0: // DrawAllPointsStyle.FIRST:
							doMove(p);
							break;
						case 1: // DrawAllPointsStyle.MINMAX:
							old = p;
							break;
						}
					}
				}
			}
		}
		
		return result;
	}

    /**
     * The Transparency of the FastLine series as a percentage.
     * Default value: 0
     *
     * @return int
     */
    public int getTransparency() {
        return getLinePen().getTransparency();
    }

    /**
     * Sets the Transparency of the FastLine series as a percentage.
     * Default value: 0
     *
     * @param value int
     */
    public void setTransparency(int value) {
        getLinePen().setTransparency(value);
    }

    /**
     * When false, it only draws the first point at any X pixel location.
     * The option offers speed gains if large numbers of point repetitions
     * occur at one X location. <br>
     * Default value: true
     *
     * @return boolean
     */
    public boolean getDrawAllPoints() {
        return drawAll;
    }

    /**
     * When false, it only draws the first point at any X pixel location.
     * The option offers speed gains if large numbers of point repetitions
     * occur at one X location. <br>
     * Default value: true<br>
     *
     * <p>Example:
     * <pre><font face="Courier" size="4">
     * lineSeries.setDrawAllPoints( false );
     * </font></pre></p>
     *
     * @param value boolean
     */
    public void setDrawAllPoints(boolean value) {
        drawAll = setBooleanProperty(drawAll, value);
    }
    
    /**
     * The DrawAllPoints style.<br>
     * It defines the method used to calculate the DrawAllPoints style.<br>
     * FastLine series DrawAllPoints should be false.<br>
     * Default value: FIRST
     *
     *
     * @return DrawAllPointsStyle
     */
    public DrawAllPointsStyle getDrawAllPointsStyle() {
        return drawAllStyle;
    }

    /**
     * Sets the DrawAllPoints style.<br>
     * Default value: FIRST
     *
     *
     * @param value DrawAllPointsStyle
     */
    public void setDrawAllPointsStyle(DrawAllPointsStyle value) {
        if (drawAllStyle != value) {
            drawAllStyle = value;
            invalidate();
        }        
    }

    public void setColor(Color value) {
        super.setColor(value);
        getLinePen().setColor(value);
    }

    /**
     * Returns the ValueIndex of the "clicked" point in the Series.<br>
     * Clicked means the X and Y coordinates are in the point screen region
     * bounds. If no point is "touched", Clicked returns -1 <br>
     *
     * @param x int
     * @param y int
     * @return int
     */
    public int clicked(int x, int y) {

        int result = -1;
        if (firstVisible > -1) {
            doClicked = true;
            draw();

            int oldX = (firstVisible - 1 > -1) ? calcXPos(firstVisible - 1) : 0;
            int oldY = (firstVisible - 1 > -1) ? calcYPos(firstVisible - 1) : 0;
            int tmpX = calcXPos(firstVisible);
            int tmpY = calcYPos(firstVisible);

            Point p = new Point(x, y);

            for (int t = firstVisible; t <= lastVisible; t++) {

                tmpX = (t > firstVisible) ? Utils.round(internalXValue.getValue(t)) : tmpX;
                tmpY = (t > firstVisible) ? Utils.round(internalYValue.getValue(t)) : tmpY;

                if (tmpX != 0 && oldX != 0) {
                    if ((tmpX == x) && (tmpY == y)) // clicked right on point
                        return t;
                    else if (Utils.pointInLineTolerance(p, tmpX, tmpY, oldX, oldY, 3)) {
                        result = t - 1;
                        if (result - 1 > 0 && !drawAll) {
                            while (internalYValue.getValue(result) == 0.0) result--;
                        }
                    }
                }
                
                if (tmpX > 0 && tmpY > 0) {
                    oldX = tmpX;
                    oldY = tmpY;
                }

                if (result > -1) break;
            }
        }
        return result;
    }

    private Point calcPosition(int index) {
        return new Point(getHorizAxis().calcXPosValue(getXValues().value[index]),
                         getVertAxis().calcYPosValue(getYValues().value[index]));
    }
    
    private int prepareFastLine()
    {
      internalG = chart.getGraphics3D();
      internal3D = chart.getAspect().getView3D();

      prepareCanvas(internalG);
      int tmp = firstVisible;
            
      old = (tmp > 0) ? calcPosition(tmp - 1) : calcPosition(tmp);
      
      iMax = iMin = old.y;
      sameX = false;
                        
      doMove(old);
      if (tmp >= 0) drawValue(tmp); //CDI FastLine Nulls

      return tmp;
    }

    protected void draw() {
        int tmp = prepareFastLine();
        if (doClicked) {
            int tmpCount = getCount();
            internalXValue.clear(tmpCount);
            internalYValue.clear(tmpCount);
        }
        /*if (chart.ToolCheckHidden != null) {
            for (int t = tmp + 1; t <= lastVisible; ++t)
                Chart.ToolCheckHidden.CheckDrawHidden(this, t);
        }
        else*/
            for (int t = tmp + 1; t <= lastVisible; ++t) drawValue(t);

        doClicked = false;
    }

    private void doMove(Point p) {
        if (internal3D) {
            internalG.moveTo(p, middleZ);
        } else {
            internalG.moveTo(p);
        }
    }
    
    private ValueList internalXValue = new ValueList();
    private ValueList internalYValue = new ValueList();
    private boolean doClicked = false;

    private void doDrawPoint(int index, Point p) {
        if (doClicked) {
            internalXValue.insertChartValue(index, p.x);
            internalYValue.insertChartValue(index, p.y);
        }
        else {
            if (bColorEach) internalG.getPen().setColor(getValueColor(index));
            if (internal3D) {
                if (stairs) {
                    if (invertedStairs)
                        internalG.lineTo(old.x, p.y, middleZ);
                    else
                        internalG.lineTo(p.x, old.y, middleZ);
                }
                internalG.lineTo(p.x, p.y, middleZ);
            }
            else {
                if (stairs) {
                    if (invertedStairs)
                        internalG.lineTo(old.x, p.y);
                    else
                        internalG.lineTo(p.x, old.y);
                }
                internalG.lineTo(p.x, p.y);
            }
        }
        old.set(p);
    }
                
    private int iMin = Integer.MAX_VALUE, iMax = Integer.MAX_VALUE;
    private boolean sameX = false;
    
    /**
     * Called internally. Draws the "ValueIndex" point of the Series.
     *
     * @param index int
     */
	public void drawValue(int index) {
		if (internalG == null)
			prepareFastLine();
		else {
			Point p = calcPosition(index);

			if (shouldDrawPoint(index, p)) {
				if (!drawAll && drawAllStyle == DrawAllPointsStyle.MINMAX) {
					if ((p.x == old.x) /* && (shouldPaint(index)) */) {
						if (p.y < iMin)
							iMin = p.y;
						if (p.y > iMax)
							iMax = p.y;
						
						sameX = true;

						if (index == lastVisible) {
							doDrawPoint(index, new Point(old.x, getVertAxis().getInverted() ? iMax : iMin));
							doDrawPoint(index, new Point(old.x, getVertAxis().getInverted() ? iMin : iMax));
						}
					} 
					else {
						if (sameX) {
							doDrawPoint(index, new Point(old.x, getVertAxis().getInverted() ? iMax : iMin));
							doDrawPoint(index, new Point(old.x, getVertAxis().getInverted() ? iMax : iMin));
						}
						
						doDrawPoint(index, p);

						sameX = false;
						iMin = Integer.MAX_VALUE;
			            iMax = Integer.MIN_VALUE;
					}
				}
				else
					doDrawPoint(index, p);
			}
		}
	}

    protected void drawMark(int valueIndex, String st,
                            SeriesMarksPosition aPosition) {
        getMarks().applyArrowLength(aPosition);
        super.drawMark(valueIndex, st, aPosition);
    }

    protected void drawLegendShape(IGraphics3D g, int valueIndex,
                                   Rectangle rect) {
        prepareCanvas(g);
        g.horizontalLine(rect.x, rect.getRight(),
                         (rect.y + rect.getBottom()) / 2);
    }

    public void createSubGallery(Gallery addSubChart) {
        super.createSubGallery(addSubChart);
        addSubChart.createSubChart(Language.getString("Marks"));
        addSubChart.createSubChart(Language.getString("Dotted"));
        addSubChart.createSubChart(Language.getString("Stairs"));
    }

    public void setSubGallery(int index) {
        switch (index) {
        case 1:
            getMarks().setVisible(true);
            break;
        case 2:
            getLinePen().setStyle(DashStyle.DOT);
            break;
        case 3:
            setStairs(true);
            break;
        default:
            break;
        }
    }

    /**
     * Controls the drawing of FastLine series. <br>
     * In most normal situations, a series draws a line between each Line
     * point. This makes the Line appear as a "mountain" shape.  <br>
     * However, setting Stairs to true will make the Series to draw 2 Lines
     * between each pair of points, thus giving a "stairs" appearance.  <br>
     * This is most used in some financial Chart representations. <br>
     * When Stairs is set to true you may set InvertedStairs to true to alter
     * the direction of the step. <br>
     * Default value: false
     *
     * @return boolean
     */
    public boolean getStairs() {
        return stairs;
    }

    /**
     * Controls the drawing of FastLine series. <br>
     * Default value: false<br>
     *
     * <p>Example:
     * <pre><font face="Courier" size="4">
     * lineSeries1.setStairs( true );
     * </font></pre></p>
     *
     * @param value boolean
     */
    public void setStairs(boolean value) {
        stairs = setBooleanProperty(stairs, value);
    }

    /**
     * Controls the FastLine series drawing. <br>
     * When Stairs is set to true you may set InvertedStairs to true to alter
     * the direction of the step. <br>
     * In most normal situations, the Series draws a line between each Line
     * point. This makes the Line appear as a "mountain" shape.  <br>
     * However, setting Stairs to true will make the Series draw 2 Lines
     * between each pair of points, thus giving a "stairs" appearance.  <br>
     * This is most used in some financial Chart representations. You may
     * invert the stair by setting InvertedStairs to true. <br>
     * Default value: false
     *
     * @return boolean
     */
    public boolean getInvertedStairs() {
        return invertedStairs;
    }

    /**
     * Controls the FastLine series drawing. <br>
     * Default value: false
     *
     * @param value boolean
     */
    public void setInvertedStairs(boolean value) {
        invertedStairs = setBooleanProperty(invertedStairs, value);
    }

    /**
     * Displays null points when false<br>
     * For speed reasons, FastLine series supports null (empty) values only
     * when IgnoreNulls is false. <br>
     * By default all points are displayed (IgnoreNulls is true by default).<br>
     * To enable FastLine series to hide null points, set IgnoreNulls to
     * false. <br>
     * Default value: true
     *
     * @return boolean
     */
    public boolean getIgnoreNulls() {
        return ignoreNulls;
    }

    /**
     * Displays null points when false<br>
     * Default value: true<br>
     *
     * <p>Example:
     * <pre><font face="Courier" size="4">
     * lineSeries1.setNull( 123 ); // -- make null (empty) point index 123
     * lineSeries1.setIgnoreNulls( false ); // -- allow null points
     * lineSeries1.setStairs( true ); // -- set "stairs" mode
     * </font></pre></p>
     *
     * @param value boolean
     */
    public void setIgnoreNulls(boolean value) {
        ignoreNulls = setBooleanProperty(ignoreNulls, value);
    }

    protected boolean doNotPaintNull()
    {
      return treatnulls == TreatNullsStyle.DONOTPAINT || treatNans == true;
    }

    /**
     * Gets descriptive text.
     *
     * @return String
     */
    public String getDescription() {
        return Language.getString("GalleryFastLine");
    }
}

