/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.steema.teechart.functions;

/**
 *
 * @author Dev
 */
public class KDEOptions {
    KDEType _kDEType;
    KDESides _KDESides;
    int _resolution;
    int _bandwidth;
    int _width;
    KDEClamp _clamp;

    /// <summary>
    /// The KDE currently has two modes; one to supoprt the Violin Series
    /// and the other to support the Beeswarm series.
    /// </summary>
    public KDEType getkDEType() {
        return _kDEType;
    }

    public void setkDEType(KDEType _kDEType) {
        this._kDEType = _kDEType;
    }

    /// <summary>
    /// Default Both. Choose to what side to plot the KDE. The property
    /// permits the plot of more than one KDE at a single x location.
    /// </summary>
    public KDESides getSides() {
        return _KDESides;
    }

    public void setSides(KDESides _KDESides) {
        this._KDESides = _KDESides;
    }

    /// <summary>
    /// Default 100. Resolution defines the number of iterations in the 
    /// direction of the significant data. A high resolution will add 
    /// precision with a performance overhead.
    /// </summary>
    public int getResolution()
    {
        return _resolution;
    }

    public void setResolution(int _resolution)
    {
        this._resolution = _resolution;
    }

    /// <summary>
    /// The Bandwidth influences the smoothing of the KDE. The default is
    /// 20. Generally this value is decided empirically in response to the
    /// perception of 'best reflection' of value curve.
    /// </summary>
    public int getBandwidth() {
        return _bandwidth;
    }

    public void setBandwidth(int _bandwidth) {
        this._bandwidth = _bandwidth;
    }

    /// <summary>
    /// Not currently used.
    /// </summary>
    public int getWidth() {
        return _width;
    }

    public void setWidth(int _width) {
        this._width = _width;
    }

    /// <summary>
    /// Default MinMax. When Set to MinMax the KDE will clamp to the
    /// minimum and maximum of the source data. You can override by
    /// changing to Manual and setting the min/max values according to 
    /// own criteria.
    /// </summary>
    public KDEClamp getClamp() {
        return _clamp;
    }

    public void setClamp(KDEClamp _clamp) {
        this._clamp = _clamp;
    }
}
