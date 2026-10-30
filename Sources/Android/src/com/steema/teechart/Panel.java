package com.steema.teechart;

import com.steema.teechart.drawing.*;
import com.steema.teechart.misc.Utils;


/**
 *
 * <p>Title: Panel class.</p>
 *
 * <p>Description: Chart background panel characteristics</p>
 *
 * <p>Copyright (c) 2005-2019 by Steema Software SL. All Rights
 * Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 *
 */
public class Panel extends Shape {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private double marginLeft = 3;
    private double marginTop = 4;
    private double marginRight = 3;
    private double marginBottom = 4;
    private boolean backInside;
    private PanelMarginUnits marginUnits = PanelMarginUnits.PERCENT;

    protected Graphics3D internalCanvas;

    public Panel(IBaseChart c) {
        super(c);

        getPen().setDefaultVisible(false);
        getBrush().setDefaultColor(defaultColor);
        getBevel().outer = BevelStyle.RAISED;
        getBevel().defaultOuter = BevelStyle.RAISED;
    }

    public Panel()
    {
        super();
    }

    public static final Color defaultColor=SystemColor.getControlColor();

    /**
     * Obsolete.&nbsp;Please use the Pen method instead.
     *
     * @return ChartPen
     */
    public ChartPen getBorderPen() {
        return getPen();
    }

    /**
     * Shows or hides the Panel.
     *
     * @return boolean
     */
    public boolean getVisible() {
        return super.getVisible();
    }

    /**
     * Shows or hides the Panel.
     *
     * @param value boolean
     */
    public void setVisible(boolean value) {
        super.setVisible(value);
    }

    public void setTransparent(boolean value) {
        chart.getParent().setOpaque(!value);
    }

    /**
     * Paints the Chart in your preferred Canvas and region.
     *
     * @param g IGraphics3D
     * @param r Rectangle
     * @return Rectangle
     */
    public Rectangle draw(IGraphics3D g, Rectangle r) {

      if (chart.canDrawPanelBack()) {
            if (getShadow().getVisible()) {
                // g.Rectangle(Brush.DrawingBrush,r); // new ChartBrush(chart,Color) //CDI TF02010049 - commented out

                int w = getShadow().getWidth();
                if (w > 0) {
                    r.width -= w;
                } else {
                    r.x += w;
                }
                int h = getShadow().getHeight();
                if (h > 0) {
                    r.height -= h;
                } else {
                    r.y += h;
                }
            }

            paint(g, r);
        }

        return applyMargins(r);
    }
/**
 * Applies panel margins.
 * 
 * @param r
 * @return Rectangle
 */
    public Rectangle applyMargins(Rectangle r) {
        // Apply panel margins
        int tmpH = r.height;

        if (marginTop != 0) {
            int tmp = (marginUnits == PanelMarginUnits.PERCENT) ?
                      Utils.round(tmpH * marginTop * 0.01) :
                      (int) marginTop;
            r.y += tmp;
            r.height -= tmp;
        }

        if (marginBottom != 0) {
            int tmp = (marginUnits == PanelMarginUnits.PERCENT) ?
                      Utils.round(tmpH * marginBottom * 0.01) :
                      (int) marginBottom;
            r.height -= tmp;
        }

        int tmpW = r.width;

        if (marginLeft != 0) {
            int tmp = (marginUnits == PanelMarginUnits.PERCENT) ?
                      Utils.round(tmpW * marginLeft * 0.01) :
                      (int) marginLeft;
            r.x += tmp;
            r.width -= tmp;
        }

        if (marginRight != 0) {
            int tmp = (marginUnits == PanelMarginUnits.PERCENT) ?
                      Utils.round(tmpW * marginRight * 0.01) :
                      (int) marginRight;
            r.width -= tmp;
        }

        return r;
    }

    /**
     * Obsolete.&nbsp;Please Set Panel.<!-- -->Image=null.
     */
    public void backImageClear() {
        setImage(null);
    }

    /**
     * The units in which the Margins are expressed.<br>
     * Either as a percentage of the pixel height and width of the Chart
     * Drawing Canvas, or in pixels from the panel borders.<br>
     * Default value: Percent
     *
     * @return PanelMarginUnits
     */
    public PanelMarginUnits getMarginUnits() {
        return marginUnits;
    }

    /**
     * Sets the units in which the Margins are expressed.<br>
     * Default value: Percent
     *
     * @param value PanelMarginUnits
     */
    public void setMarginUnits(PanelMarginUnits value) {
        if (marginUnits != value) {
            marginUnits = value;
            invalidate();
        }
    }

    /**
     * Left margin expressed as percentage of Chart Drawing.<br>
     * Each Chart.Panel class has four margin parameters: LeftMargin,
     * RightMargin, TopMargin, BottomMargin. These properties are expressed as
     * a percentage of the pixel height (for top and bottom margins) and width
     * (for left and right margins) of the Chart Drawing Canvas. Default values
     * are 8 for top and bottom margins and 12 for left and right margins.
     * Or they can be expressed in pixels by changing the MarginUnits from
     * percentage (default) to pixels.<br>
     * Default value: 12
     *
     * @return double
     */
    public double getMarginLeft() {
        return marginLeft;
    }

    /**
     * Sets Left margin as percentage of Chart Drawing.<br>
     * Default value: 12
     *
     * @see Panel#getMarginLeft
     * @param value double
     */
    public void setMarginLeft(double value) {
        marginLeft = setDoubleProperty(marginLeft, value);
    }


    /**
     * Top margin  expressed as percentage of Chart Drawing.<br>
     * Each Chart.Panel class has four margin properties: LeftMargin,
     * RightMargin, TopMargin, BottomMargin. These properties are expressed as
     * a percentage of the pixel height (for top and bottom margins) and width
     * (for left and right margins) of the Chart Drawing Canvas. Default values
     * are 8 for top and bottom margins and 12 for left and right margins.
     * Or they can be expressed in pixels by changing the MarginUnits from
     * percentage (default) to pixels.<br>
     * Default value: 8
     *
     * @return double
     */
    public double getMarginTop() {
        return marginTop;
    }

    /**
     * Sets Top margin as percentage of Chart Drawing.<br>
     * Default value: 8
     *
     * @see Panel#getMarginTop
     * @param value double
     */
    public void setMarginTop(double value) {
        marginTop = setDoubleProperty(marginTop, value);
    }

    /**
     * Right margin expressed as percentage of Chart Drawing.<br>
     * Each Chart.Panel class has four margin properties: LeftMargin,
     * RightMargin, TopMargin, BottomMargin. These properties are expressed as
     * a percentage of the pixel height (for top and bottom margins) and width
     * (for left and right margins) of the Chart Drawing Canvas. Default values
     * are 8 for top and bottom margins and 12 for left and right margins.
     * Or they can be expressed in pixels by changing the MarginUnits from
     * percentage (default) to pixels.<br>
     * Default value: 12
     *
     * @return double
     */
    public double getMarginRight() {
        return marginRight;
    }

    /**
     * Sets Right margin as percentage of Chart Drawing.<br>
     * Default value: 12
     *
     * @see Panel#getMarginRight
     * @param value double
     */
    public void setMarginRight(double value) {
        marginRight = setDoubleProperty(marginRight, value);
    }

    /**
     * Bottom margin expressed as percentage of Chart Drawing.<br>
     * Each Chart.Panel class has four margin properties: LeftMargin,
     * RightMargin, TopMargin, BottomMargin. These properties are expressed as
     * a percentage of the pixel height (for top and bottom margins) and width
     * (for left and right margins) of the Chart Drawing Canvas. Default values
     * are 8 for top and bottom margins and 12 for left and right margins.
     * Or they can be expressed in pixels by changing the MarginUnits from
     * percentage (default) to pixels.<br>
     * Default value: 8
     *
     * @return double
     */
    public double getMarginBottom() {
        return marginBottom;
    }

    /**
     * Sets Bottom margin as percentage of Chart Drawing.<br>
     * Default value: 8
     *
     * @see Panel#getMarginBottom
     * @param value double
     */
    public void setMarginBottom(double value) {
        marginBottom = setDoubleProperty(marginBottom, value);
    }


    /**
     * Obsolete.&nbsp;Please use Walls.<!-- -->Back.<!-- -->Image instead.
     *
     * @return boolean
     */
    public boolean getBackImageInside() {
        return backInside;
    }

    /**
     * Obsolete.&nbsp;Please use Walls.<!-- -->Back.<!-- -->Image instead.
     *
     * @param value boolean
     */
    public void setBackImageInside(boolean value) {
        backInside = setBooleanProperty(backInside, value);
    }
}

