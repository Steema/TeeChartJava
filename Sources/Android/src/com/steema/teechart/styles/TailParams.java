package com.steema.teechart.styles;

import com.steema.teechart.Chart;
import com.steema.teechart.IBaseChart;
import com.steema.teechart.TeeBase;
import com.steema.teechart.drawing.Point;

/**
 * Created by abde on 2/03/16.
 */
public class TailParams extends TeeBase {
    private double _poinerHeight;


    private double _pointerWidth;
    private TailAlignment _align;

    boolean _customPoint = false;
    Point _customPointPos;
    float _margin;

    public TailParams(IBaseChart chart) {
        super(chart);
        this.chart = chart;
        setPointerHeight(5);
        setPointerWidth(7);
        setMargin(0);
        setAlign(TailAlignment.BottomCenter);
    }

    public TailParams(Chart c) {
        super(c);
        chart = c;
        setPointerHeight(5);
        setPointerWidth(7);
        setMargin(0);
        setAlign(TailAlignment.BottomCenter);
    }

    //region Getters/Setters
    public double getPointerHeight() {
        return _poinerHeight;
    }

    public void setPointerHeight(double _pointerHeight) {
        this._poinerHeight = _pointerHeight;
    }

    public double getPointerWidth() {
        return _pointerWidth;
    }

    public void setPointerWidth(double _pointerWidth) {
        this._pointerWidth = _pointerWidth;
    }

    public TailAlignment getAlign() {
        return _align;
    }

    public void setAlign(TailAlignment _align) {
        this._align = _align;
    }

    public boolean isCustomPoint() {
        return _customPoint;
    }

    public void setCustomPoint(boolean _customPoint) {
        this._customPoint = _customPoint;
    }

    public Point getCustomPointPos() {
        return _customPointPos;
    }

    public void setCustomPointPos(Point _customPointPos) {
        this._customPointPos = _customPointPos;
    }

    public float getMargin() {
        return _margin;
    }

    public void setMargin(float _margin) {
        this._margin = _margin;
    }
    //endregion
}
