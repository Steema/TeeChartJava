package com.steema.teechart.legend;

import com.steema.teechart.drawing.ChartBrush;
import com.steema.teechart.drawing.ChartPen;
import com.steema.teechart.drawing.IGraphics3D;
import com.steema.teechart.Rectangle;
import com.steema.teechart.TeeBase;
import com.steema.teechart.misc.Utils;

/**
 *
 * <p>Title: LegendSymbol class</p>
 *
 * <p>Description: Legend item symbol characteristics</p>
 *
 * <p>Copyright (c) 2005-2019 by Steema Software SL. All Rights
 * Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 *
 */
public class LegendSymbol extends TeeBase {

	private static final long serialVersionUID = 1L;
    /**
     * Determines if legend symbol should display without separation from other
     * legend item symbols.
     */
    protected boolean continuous;

    /**
     * Controls where to display the legend symbol related to symbol item.
     */
    protected LegendSymbolPosition position = LegendSymbolPosition.LEFT;

    /**
     * Internal field pointing to parent legend class
     */
    transient protected Legend legend;

    transient private boolean defaultPen;

    private int width = 20;

    private LegendSymbolSize widthUnits = LegendSymbolSize.PERCENT;
    private ChartPen iPen;
    private boolean squared;
    private boolean visible = true;
    private int symbolLineWidth;

    public LegendSymbol(Legend legend) {
        super(legend.chart);
        readResolve();
        this.legend = legend;
        //pen=new ChartPen(legend.chart);
    }

    protected Object readResolve() {
        defaultPen=true;
        return this;
    }

    /**
     * Defines the width of the color rectangles (symbols).<br>
     * Default value: 20
     *
     * @return int
     */
    public int getWidth() {
        return width;
    }
    
    public void setWidth(int value) {
        width = setIntegerProperty(width, value);
    }
    
    /**
     * Defines the width of the Line used to plot Line symbols when wishing to 
     * override the default line width associated with the Series. Not be confused
     * with the Line used to plot the border of a symbol.<br>
     * Default value: -1 (not active)
     *
     * @return int
     */
    public int getLineWidth() {
        return symbolLineWidth;
    }
    
    public void setLineWidth(int value) {
    	symbolLineWidth = setIntegerProperty(symbolLineWidth, value);
    }

    /**
     * The position of the Legend color rectangles. <br>
     * It can have one of the following values: <br>
     * Left      The color rectangles are placed left of the legend items <br>
     * Right    The color rectangles are placed right of the legend items <br>
     * Default value: Left
     *
     * @return LegendSymbolPosition
     */
    public LegendSymbolPosition getPosition() {
        return position;
    }

    /**
     * Sets the position of the Legend color rectangles. <br>
     * Default value: Left
     *
     * @param value LegendSymbolPosition
     */
    public void setPosition(LegendSymbolPosition value) {
        if (position != value) {
            position = value;
            invalidate();
        }
    }

    /**
     * Defines the Width units for the width of Symbol.<br><br>
     * - Percent is percentage of Legend box width <br>
     * - Pixels is the width in standard pixels <br>
     * Default value: Percent
     *
     * @return LegendSymbolSize
     */
    public LegendSymbolSize getWidthUnits() {
        return widthUnits;
    }

    /**
     * Sets the Width units for the width of Symbol.<br><br>
     * Default value: Percent
     *
     * @param value LegendSymbolSize
     */
    public void setWidthUnits(LegendSymbolSize value) {
        if (widthUnits != value) {
            widthUnits = value;
            invalidate();
        }
    }

    /**
     * Adjoins the different legend color rectangles when true.<br> The color
     * rectangles of the different items are drawn attached to each other
     * (no vertical spacing). When false, the color rectangles are drawn as
     * seperate rectangles. <br>
     * Default value: false
     *
     * @return boolean
     */
    public boolean getContinuous() {
        return continuous;
    }

    /**
     * Adjoins the different legend color rectangles when true.<br>
     * Default value: false
     *
     * @param value boolean
     */
    public void setContinuous(boolean value) {
        continuous = setBooleanProperty(continuous, value);
    }

    /**
     * Uses series pen properties to draw a border around the coloured box
     * legend symbol, when true. When false, the Legend will use the legend
     * symbol Pen property. <br>
     * Default value: true
     *
     * @return boolean
     */
    public boolean getDefaultPen() {
        return defaultPen;
    }

    /**
     * Uses series pen properties to draw a border around the coloured box
     * legend symbol, when true. When false, the Legend will use the legend
     * symbol Pen property. <br>
     * Default value: true
     *
     * @param value boolean
     */
    public void setDefaultPen(boolean value) {
        defaultPen = setBooleanProperty(defaultPen, value);
    }

    protected int calcWidth(int value) {
        if (visible) {
            if (squared) {
                return legend.calcItemHeight() - 5;
            } else
            if (widthUnits == LegendSymbolSize.PERCENT) {
                return Utils.round(width * value * 0.01);
            } else {
                return width;
            }
        } else {
            return 0;
        }
    }

    /**
     * pen used to draw a border around the color box legend symbols. <br>
     * By default this pen is not used. Instead, the appropiate Series pen is
     * used to draw the symbols borders.<br>
     * To use this Pen, first set DefaultPen to false.
     *
     * @return ChartPen
     */
    public ChartPen getPen() {
        if (iPen == null) {
            iPen = new ChartPen(legend.chart);
        }
        return iPen;
    }

    /**
     * Resizes the legend symbol to square shaped, when true.<br>
     * When false, the legend symbol height is determined by the legend font
     * size, and the symbol width is calculated using the Width and WidthUnits
     * properties.<br>
     * Default value: false
     *
     * @return boolean
     */
    public boolean getSquared() {
        return squared;
    }

    /**
     * Resizes the legend symbol to square shaped, when true.<br>
     *
     * @param value boolean
     */
    public void setSquared(boolean value) {
        squared = setBooleanProperty(squared, value);
    }

    /**
     * Shows or hides Legend symbols.<br>
     * Default value: true
     *
     * @return boolean
     */
    public boolean getVisible() {
        return visible;
    }

    /**
     * Shows or hides Legend symbols.<br>
     * Default value: true
     *
     * @param value boolean
     */
    public void setVisible(boolean value) {
        visible = setBooleanProperty(visible, value);
    }
    
	protected void draw(Rectangle r)
	{
		draw(r, legend.chart.getGraphics3D());
	}
	
	protected void draw(Rectangle R, IGraphics3D tmp)
	{
		ChartBrush tmpBrush = new ChartBrush(legend.chart);
		tmpBrush = tmp.getBrush();

		/*if (getShadow()..Size != new Size(0, 0) && shadow.Visible)
		{
			shadow.Draw(tmp, R);
		}*/

		tmp.setPen(this.getPen());
		tmp.setBrush(tmpBrush);

		if (getPen().getVisible())
		{
			tmp.rectangle(R);
		}
	}
}

