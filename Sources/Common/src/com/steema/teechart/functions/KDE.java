package com.steema.teechart.functions;

import com.steema.teechart.events.ChartDrawEvent;
import com.steema.teechart.languages.Language;

import java.util.ArrayList;
import com.steema.teechart.IBaseChart;
import com.steema.teechart.PointDouble;
import com.steema.teechart.events.SeriesPaintAdapter;
import com.steema.teechart.styles.Series;
import com.steema.teechart.styles.FastLine;
import com.steema.teechart.styles.ISeries;
import com.steema.teechart.styles.OHLC;
import com.steema.teechart.styles.ValueList;
import com.steema.teechart.drawing.ChartPen;
import com.steema.teechart.drawing.Color;
import com.steema.teechart.misc.Utils;
import com.steema.teechart.styles.ValueListOrder;
import java.util.Arrays;
import java.util.List;

/**
 *
 * <p>Title: KDE class</p>
 *
 * <p>Description: Kernel Density Estimator (KDE) Function.</p>
 *
 * <p>Copyright (c) 2005-2021 by Steema Software SL. All Rights
 * Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>

 *
 */
public class KDE extends Function {

    private ValueList _sourceValues;
    private List<PointDouble> _kDEPoints;
    private KDEOptions _options;
    private double _position;
    private double _clampMin;
    private double _clampMax;
    private double _scale;
    private ValueListOrder loAscending;

    public KDE()
    {
        super();
        setScale(1);
        getOptions().setResolution(100);
        getOptions().setBandwidth(20);
        getOptions().setClamp(KDEClamp.MINMAX);
        getOptions().setSides(KDESides.BOTH);
    }

    public KDEOptions getOptions()
    {
        if (_options == null)
            _options = new KDEOptions();

        return _options;
    }

    public void setOptions(KDEOptions _options)
    {
        this._options = _options;
    }

    public void addPoints(Series source)
    {
        _sourceValues = source.getMandatory();
    }

    public void addListPoints(ValueList source)
    {
        _sourceValues = source;
    }

    /// <summary>
    /// Non-data Axis location (X axis for a vertical Series)
    /// </summary>
    public double getPosition()
    {
        return _position;
    }
    
    public void setPosition(double _position)
    {
        this._position = _position;
    }

    /// <summary>
    /// Used when Options.Clamp == Clamp.Manual
    /// Minimum data level at which to begin the KDE calculation. 
    /// For a vertical series that would be the minimum Y value.
    /// </summary>
    public double getClampMin()
    {
        return _clampMin;
    }
    
    public void setClampMin(double _clampMin)
    {
        this._clampMin = _clampMin;
    }

    /// <summary>
    /// Used when Options.Clamp == Clamp.Manual
    /// Maximum data level at which to begin the KDE calculation. 
    /// For a vertical series that would be the maximum Y value.
    /// </summary>
    public double getClampMax()
    {
        return _clampMax;
    }
    
    public void setClampMax(double _clampMax)
    {
        this._clampMax = _clampMax;
    }

    /// <summary>
    /// Default 1. Use this property to broaden the x-plot of the Function to aid visualisation
    /// in context to other series in the chart.
    /// </summary>
    public double getScale()
    {
        return _scale;
    }
    
    public void setScale(double _scale)
    {
        this._scale = _scale;
    }

    public void calcClampRange()
    {
        if (this.getSeries() != null)
        {
            this.getSeries().getVertAxis().setMinimumOffset(10);
            this.getSeries().getVertAxis().setMaximumOffset(10);
            this.getSeries().getVertAxis().adjustMaxMin();

            _clampMin = this.getSeries().getVertAxis().getMinimum();
            _clampMax = this.getSeries().getVertAxis().getMaximum();
        }
    }

    public List<PointDouble> getKDEPoints()
    {
        return _kDEPoints;
    }
    
    public void setKDEPoints(List<PointDouble> _kDEPoints)
    {
        this._kDEPoints = _kDEPoints;
    }

    public void calculateKDE()
    {
        if ((_sourceValues != null) && (_sourceValues.count > 0))
          calculateValues(_sourceValues);
    }

    public void calculateValues(ValueList aValues)
    {
        List<Double> axLabels = buildSteps();
        setKDEPoints(kernelDensityEstimator(axLabels, _sourceValues));
    }

    private List<PointDouble> kernelDensityEstimator(List<Double> x, ValueList vals)
    {
        double aX, aY;
        int counter, hitBucket;

        List<Double> tmpList = Arrays.asList(new Double[vals.count]);
        List<PointDouble> tmpResult; 
        List<PointDouble> tmpResulti;
        List<PointDouble> buckets;

        if (getOptions().getkDEType() == KDEType.VIOLIN)
        {
            if (this.getOptions().getSides() == KDESides.BOTH)
                tmpResult = Arrays.asList(new PointDouble[x.size() * 2]);
            else
                tmpResult = Arrays.asList(new PointDouble[x.size()]);
        }
        else
            tmpResult = Arrays.asList(new PointDouble[x.size()]);

        buckets = Arrays.asList(new PointDouble[x.size()]);
        for (int i=0; i < x.size(); i++ )
          buckets.set(i, new PointDouble(0,0));
                  
        tmpResulti = Arrays.asList(new PointDouble[x.size()]);
        counter = 0;

        if (getOptions().getkDEType() == KDEType.VIOLIN)
        {
            for (int j = 0; j < x.size(); j++)
            {
                if (getOptions().getkDEType() == KDEType.VIOLIN)
                {
                    aX = x.get(j);
                    for (int jj = 0; jj < vals.count; jj++)
                    {
                        tmpList.set(jj, getScale() * kernel(aX - vals.getValue(jj)));
                    }

                    aY = getAvg(tmpList); 

                    if (getOptions().getSides() == KDESides.BOTH) {

                        tmpResult.set(j, new PointDouble(getPosition() - aY, aX));
                        tmpResulti.set(j, new PointDouble(getPosition() + aY, aX));
                    }
                    else if (getOptions().getSides() == KDESides.LEFT) {
                        tmpResult.set(j, new PointDouble(getPosition() - aY, aX));
                    }
                    else
                    {
                        tmpResult.set(j, new PointDouble(getPosition() + aY, aX));
                    }
                }
                counter = j + 1;
            }
        }
        else
        {    
            for (int jj = 0; jj < vals.count; jj++)
            {
                hitBucket = getNearestValueIndex(x, vals.getValue(jj));

                if (hitBucket != -1)
                    buckets.set(hitBucket, new PointDouble(buckets.get(hitBucket).getX() + 1, x.get(hitBucket)));
            }
        }

        if (getOptions().getkDEType() == KDEType.VIOLIN)
        {
            if (getOptions().getSides()== KDESides.BOTH)
            {
                for (int j = 0; j < tmpResulti.size(); j++)
                    tmpResult.set(counter + j, new PointDouble(tmpResulti.get(counter - 1 - j).getX(), tmpResulti.get(counter - 1 - j).getY()));
            }                  

            return tmpResult;
        }
        else
            return buckets;
    }
    
    private List<Double> buildSteps()
    {
        List<Double> axLabels = new ArrayList<Double>();
        double ARange;
        double increment = 0;
        double kdeMin = 0; 
        double kdeMax = 0;
        
        double tmpVal;

        _sourceValues.setOrder(loAscending);
        _sourceValues.sort();

        if (getSeries() != null)
        {
            calcClampRange();
        }
        else
        {
            kdeMin = _sourceValues.getMinimum();
            kdeMax = _sourceValues.getMaximum();
        }

        if (_options.getClamp() == KDEClamp.MANUAL)
        {
            kdeMin = getClampMin();
            kdeMax = getClampMax();
        }
        else
        {
            if ((_options.getClamp() == KDEClamp.MINMAX))
            {
                kdeMin = _sourceValues.getMinimum();
                kdeMax = _sourceValues.getMaximum();
            }
        }

        ARange = (kdeMax - kdeMin);

        if (_options.getResolution() > 0)
        {
            axLabels = Arrays.asList(new Double[_options.getResolution()]);

            increment = ARange / _options.getResolution();
        }

        tmpVal = kdeMin;

        for (int i = 0; i < axLabels.size(); i++)
        {
            axLabels.set(i, tmpVal);
            tmpVal = tmpVal + increment;
        }
        return axLabels;
    }
    
    private double getAvg(List<Double> aList)
    {
        double tmpSum = 0;
        for (double d : aList)
            tmpSum = tmpSum + d; 
        return tmpSum / aList.size();
    }
    
    private double kernel(double u)
    {
        u = u / getOptions().getBandwidth();
        if ((Math.abs(u) <= 1))
            return 0.75 * (1 - u * u) / getOptions().getBandwidth();
        else
            return 0;
    }
    
    private int getNearestValueIndex(List<Double> a, double value)
    {
        int tmpGNResult;
        double distance, minDistance;

        tmpGNResult = -1;
        minDistance = Long.MAX_VALUE;
        for (int i = 0; i < a.size(); i++)
        {
            distance = Math.abs(value - a.get(i));
            if ((distance < minDistance))
            {
                minDistance = distance;
                tmpGNResult = i;
                if ((minDistance == 0))
                    break;
            }
        }

        return tmpGNResult;
    }
        
    /**
     * Gets descriptive text.
     *
     * @return String
     */
    public String getDescription() {
        return Language.getString("FunctionKDE");
    }
}

