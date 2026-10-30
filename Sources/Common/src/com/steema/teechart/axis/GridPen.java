package com.steema.teechart.axis;

import com.steema.teechart.drawing.DashStyle;
import com.steema.teechart.drawing.ChartPen;
import com.steema.teechart.drawing.Color;
import com.steema.teechart.IBaseChart;

/**
 *
 * <p>Title: GridPen class</p>
 *
 * <p>Description: Determines the kind of pen used to draw the Grid lines at
 * every Axis Label position.</p>
 *
 * <p>Copyright (c) 2005-2019 by Steema Software SL. All Rights Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 *
 */
public class GridPen extends ChartPen {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
     * When centered is true, grid is displayed in between axis labels.
     * When false, grid lines are drawn at axis labels positions.
     */
    protected boolean centered;

    private int drawEvery = 1;
    private double zPosition;

    /**
     * Creates a new Grid pen.
     *
     * @param chart IBaseChart
     */
    public GridPen(IBaseChart chart) {
        super(chart, Color.GRAY, DashStyle.DOT);
    }

    /**
     * Determines the style of the axis grid lines.<br>
     * It sets both vertical and horizontal lines independently. <br>
     * DefaultValue: DashStyle.DOT
     *
     * @return DashStyle
     */
    public DashStyle getStyle() {
        return super.getStyle();
    }

    /**
     * Aligns the Grid to the centre. <br>
     * DefaultValue: false
     *
     * @return boolean
     */
    public boolean getCentered() {
        return centered;
    }

    /**
     * Sets grid lines to display between axis labels or at label positions.
     *
     * @param value boolean
     */
    public void setCentered(boolean value) {
        centered = setBooleanProperty(centered, value);
    }

    /**
     * Returns the Z position of the Grid lines, in percent of total chart
     * depth.
     *
     * @return double
     */
    public double getZPosition() {
        return zPosition;
    }

    /**
     * Sets the Z position of Grid lines in percentage of total chart depth.
     *
     * @param value double
     */
    public void setZPosition(double value) {
        if (zPosition != value) {
            zPosition = value;
            invalidate();
        }
    }
    /**
     * Sets the Grid to draw every corresponding value.
     *
     * @param value int
     */
    public void setDrawEvery(int value){
    if ((value != drawEvery) || (0 > value)) {
      drawEvery = value;
      invalidate();
    }
  }
  /**
   * Returns the Grid drawing intervals.
   *
   * @return int
   */
  public int getDrawEvery(){
    return drawEvery;
  }

}

