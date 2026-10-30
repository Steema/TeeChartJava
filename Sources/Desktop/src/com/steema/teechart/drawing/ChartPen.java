package com.steema.teechart.drawing;

import java.beans.EventHandler;
import com.steema.teechart.TeeBase;
import com.steema.teechart.IBaseChart;
import java.awt.BasicStroke;
import java.awt.Stroke;

/**
 *
 * <p>Title: ChartPen class</p>
 *
 * <p>Description: Common Chart Pen.
 * Pen used to draw lines and borders.</p>
 *
 * <p>Copyright (c) 2005-2019 by Steema Software SL. All Rights
 * Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 *
 */
public class ChartPen extends TeeBase {

	private static final long serialVersionUID = 1L;
	
    public Color color;
    public DashStyle dashStyle;
    public int width;
    public LineCap endCap;
    public DashCap dashCap = DashCap.FLAT;
    public boolean visible = true;
    transient protected Color defaultColor;
    transient protected LineCap defaultEndCap;
    transient protected DashStyle defaultStyle;
    private int transparency;
    transient protected boolean defaultVisible;
    transient protected boolean usesVisible;
    transient private Stroke stroke;
    private EventHandler colorChanged;

    public ChartPen(ChartPen source) {
        this(source.chart);
        assign(source);
    }

    public ChartPen(IBaseChart c, Color startColor, boolean startVisible,
            int startWidth, LineCap startEndCap, DashStyle startStyle) {
        super(c);

        usesVisible = true;

        defaultColor = startColor;
        color = defaultColor;
        defaultVisible = startVisible;
        visible = defaultVisible;

        width = startWidth;

        endCap = startEndCap;
        defaultEndCap = startEndCap;

        dashStyle = startStyle;
        defaultStyle = startStyle;

        recreateStroke();
    }

    public ChartPen(IBaseChart c) {
        this(c, Color.EMPTY, true, 1, LineCap.BEVEL, DashStyle.SOLID);
    }

    public ChartPen(Color c) {
        this(null, c);
    }

    public ChartPen(IBaseChart c, boolean startVisible) {
        this(c, Color.EMPTY, startVisible, 1, LineCap.BEVEL, DashStyle.SOLID);
    }

    public ChartPen(IBaseChart c, Color startColor, DashStyle startStyle) {
        this(c, startColor, true, 1, LineCap.BEVEL, startStyle);
    }

    public ChartPen(IBaseChart c, Color startColor, boolean startVisible) {
        this(c, startColor, startVisible, 1, LineCap.BEVEL, DashStyle.SOLID);
    }

    public ChartPen(IBaseChart c, Color startColor, int startWidth) {
        this(c, startColor, true, startWidth, LineCap.BEVEL, DashStyle.SOLID);
    }

    public ChartPen(IBaseChart c, Color startColor) {
        this(c, startColor, true, 1, LineCap.BEVEL, DashStyle.SOLID);
    }

    public ChartPen(IBaseChart c, Color startColor, boolean startVisible,
            LineCap cap) {
        this(c, startColor, startVisible, 1, cap, DashStyle.SOLID);
    }

    public void reset() {
        setColor(Color.BLACK);
        setWidth(1);
        setStyle(DashStyle.SOLID);
        setEndCap(LineCap.ROUND);
        setDashCap(DashCap.FLAT);
        setTransparency(0);
    }

    public void internalAssign(ChartPen p) {
        dashStyle = p.dashStyle;
        width = p.width;
        color = p.color;
        endCap = p.endCap;
        dashCap = p.dashCap;
        visible = p.visible;
        transparency = p.transparency;
        stroke = p.stroke;
    }

    public void assign(ChartPen p) {
        internalAssign(p);
        changed();
    }

    //CDI AssignVisiblePenColor
    public void assign(ChartPen p, Color value) {
        internalAssign(p);
        color = value;
        changed();
    }

    public void setUsesVisible(boolean value) {
        usesVisible = value;
    }

    protected boolean shouldSerializeColor() {
        return color != defaultColor;
    }

    public void setDefaultColor(Color value) {
        defaultColor = value;
        color = value;
    }

    public void setColor(java.awt.Color value) {
        setColor(new Color(value));
    }

    public void setColor(Color value) {
        if (color != value) {
            color = value;

            if (colorChanged != null) {
                colorChanged.invoke(this, null, null);
            }

            invalidate();
        }
    }

    public void setDefaultStyle(DashStyle value) {
        defaultStyle = value;
        setStyle(value);
    }

    public void setDefaultVisible(boolean value) {
        defaultVisible = value;
        visible = value;
    }

    protected boolean shouldSerializeVisible() {
        return visible != defaultVisible;
    }

    /**
     * Determines if the pen will draw lines or not.
     *
     * @return boolean
     */
    public final boolean getVisible() {
        return visible;
    }

    /**
     * Determines if the pen will draw lines or not.
     *
     * @param value boolean
     */
    public void setVisible(boolean value) {
        visible = setBooleanProperty(visible, value);
    }

    /**
     * Sets Transparency level from 0 to 100%. <br>
     * Default value: 0
     *
     * @return int
     */
    public int getTransparency() {
        return transparency;
    }

    /**
     * Sets Transparency level from 0 to 100%. <br>
     * Default value: 0
     *
     * @param value int
     */
    public void setTransparency(int value) {
        transparency = setIntegerProperty(transparency, value);
    }

    /**
     * Determines the color used by the pen to draw lines on the Drawing.
     * It can be any valid color constant like Color.Red, Color.Green, etc. <br>
     * A special color constant unique to TeeChart is: Color.EMPTY.
     * This is the "default color". <br><br>
     * Each TeeChart drawing object has a different default color. For example,
     * the tChart.getFrame() property has a default color of Color.BLACK.
     *
     *
     * @return Color
     */
    public Color getColor() {
        if (transparency == 0) {
            return color;
        } else {
            return color.transparentColor(transparency);
        }
    }

    protected boolean shouldSerializeEndCap() {
        return endCap != defaultEndCap;
    }

    /**
     * Style of line endings.
     *
     * @return LineCap
     */
    public LineCap getEndCap() {
        return endCap;
    }

    /**
     * Style of line endings.
     *
     * @param value LineCap
     */
    public void setEndCap(LineCap value) {
        if (endCap != value) {
            endCap = value;
            recreateStroke();
            invalidate();
        }
    }

    public Stroke getStroke() {
        if (stroke == null) {
            recreateStroke();
        }
        return stroke;
    }

    // "stroke" is cached due to speed reasons.
    // creating a BasicStroke is a slow operation.
    private void recreateStroke() {
        if (dashStyle == DashStyle.SOLID) {
            stroke = new BasicStroke(width, dashCap.getValue(), endCap.getValue());
        } else {
            stroke = new BasicStroke(width, dashCap.getValue(), endCap.getValue(),
                    1,
                    dashStyle.getDash(), 0);
        }
    }

    private void changed() {
        /** @todo FINISH / NECESSARY? */
        /*
        if (this == chart.getGraphics3D().getPen()) {
        chart.getGraphics3D().Changed(this);
        }
         */
    }

    public void invalidate() {
        super.invalidate();
        changed();
    }

    /**
     * Defines segment ending style of dashed lines.<br>
     * Default value: DashCap.Flat
     *
     * @return DashCap
     */
    public DashCap getDashCap() {
        return dashCap;
    }

    public void setDashCap(DashCap value) {
        if (dashCap != value) {
            dashCap = value;
            recreateStroke();
            invalidate();
        }
    }

    /**
     * Determines the width of lines the pen draws.<br>
     * Default value: 1
     *
     * @return int
     */
    public int getWidth() {
        return width;
    }

    /**
     * Determines the width of lines the pen draws.<br>
     * Default value: 1
     *
     * @param value int
     */
    public void setWidth(int value) {
        if (width != value) {
            width = setIntegerProperty(width, value);
            recreateStroke();
        }
    }

    protected boolean shouldSerializeStyle() {
        return dashStyle != defaultStyle;
    }

    /**
     * Determines the style in which the pen draw lines on the Drawing.
     *
     * @return DashStyle
     */
    public DashStyle getStyle() {
        return dashStyle;
    }

    /**
     * Determines the style in which the pen draw lines on the Drawing.
     *
     * @param value DashStyle
     */
    public void setStyle(DashStyle value) {
    	if (dashStyle != value) {
	        int oldDashWidth = 1;
	        if (dashStyle != null)
	            oldDashWidth = dashStyle.getDashWidth();
	        
	        if (value.getDashWidth() != 1)
	            oldDashWidth = value.getDashWidth();
	
	        dashStyle = value;
	
	        if (dashStyle == DashStyle.SOLID)
	            dashStyle.setDashWidth(getWidth());
	        else
	            dashStyle.setDashWidth(oldDashWidth);
	
	        recreateStroke();
	        invalidate();
    	}
    }
}
