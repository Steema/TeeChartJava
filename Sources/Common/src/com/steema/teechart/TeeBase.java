package com.steema.teechart;

import com.steema.teechart.drawing.Color;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;

/**
 *
 * <p>Title: TeeBase class</p>
 *
 * <p>Description: Non-visible class for Chart element common characteristics.</p>
 *
 * <p>Copyright (c) 2005-2019 by Steema Software SL. All Rights Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 *
 */
public class TeeBase implements Serializable {

    private static final long serialVersionUID = 75565439472837495L;

    transient public IBaseChart chart;
    transient public boolean InternalUse;
    
//    protected void readObject(CustomObjectInputStream in) throws IOException,
//    ClassNotFoundException {
//      ObjectInputStream ois = new CustomObjectInputStream(in);
//      ois.defaultReadObject();
//    }

    public TeeBase(final IBaseChart chart) {
        this.chart = chart;
    }

    /**
     * Chart associated with this object.
     *
     * @return IBaseChart
     */
    public IBaseChart getChart() {
        return chart;
    }

    /**
     * Chart associated with this object.
     *
     * @param value IBaseChart
     */
    public void setChart(final IBaseChart value) {
        chart = value;
    }

    protected Color setColorProperty(Color variable, final Color value) {
        if (variable != value) {
            variable = value;
            invalidate();
        }
        return variable;
    }

    protected int setIntegerProperty(final int variable, int value) {
        if (variable != value) {
            invalidate();
            return value;
        }
        else
        	return variable;
    }

    protected double setDoubleProperty(final double variable, final double value) {
        if (variable != value) {
            invalidate();
        	return value;
        }
        else
        	return variable;
    }

    /**
     *
     *
     * @param variable boolean. Boolean variable to change.
     * @param value boolean. New value.
     * @return boolean
     */
    protected boolean setBooleanProperty(final boolean variable, final boolean value) {
        if (variable != value) {
            invalidate();
            return value;
        }
        else
        	return variable;
    }

    protected String setStringProperty(final String variable, final String value) {
        if (! variable.equals(value)) {
            invalidate();
            return value;
        }
        else
        	return variable;
    }

    /**
     * Use invalidate when the entire canvas needs to be repainted.
     *
     */
    public void invalidate() {
        if (chart != null) 
            chart.doBaseInvalidate();
    }
}

