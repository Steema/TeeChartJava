package com.steema.teechart.tools;

import com.steema.teechart.drawing.Color;
import com.steema.teechart.drawing.ChartPen;
import com.steema.teechart.IBaseChart;
import com.steema.teechart.axis.Axis;
import com.steema.teechart.styles.Series;

/**
 *
 * <p>Title: ToolAxis class</p>
 *
 * <p>Description: Base abstract class for Tool components with an Axis method.
 * </p>
 *
 * <p>Copyright (c) 2005-2019 by Steema Software SL. All Rights Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 *
 */
public class ToolAxis extends Tool {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected Axis iAxis;

    public ToolAxis() {
        this((IBaseChart)null);
    }

    public ToolAxis(IBaseChart c) {
        super(c);
        setChart(c);
    }

    public ToolAxis(Axis a) {
        this(a.getChart());
        iAxis=a;
    }

    public void checkDrawHidden(Series series, int tmpIndex) { }

    /**
     * Element Pen characteristics.
     *
     * @return ChartPen
     */
    public ChartPen getPen() {
        if (pPen == null) {
            pPen = new ChartPen(getChart(), Color.BLACK);
        }
        return pPen;
    }

    /**
     * The axis to which a Tool will belong.<br>
     * Default value: null
     *
     * @return Axis
     */
    public Axis getAxis() {
        return iAxis;
    }

    /**
     * Sets the axis to which a Tool will belong.<br>
     * Default value: null
     *
     * @param value Axis
     */
    public void setAxis(Axis value) {
        if (iAxis != value) {
            iAxis = value;
            invalidate();
        }
    }
}


