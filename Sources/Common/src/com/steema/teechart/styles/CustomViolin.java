package com.steema.teechart.styles;


import com.steema.teechart.languages.Language;
import com.steema.teechart.drawing.Color;
import com.steema.teechart.drawing.IGraphics3D;
import com.steema.teechart.drawing.ChartBrush;
import com.steema.teechart.drawing.Gradient;
import com.steema.teechart.drawing.ChartPen;
import com.steema.teechart.IBaseChart;
import com.steema.teechart.PointDouble;
import com.steema.teechart.drawing.Graphics3D;
import com.steema.teechart.editors.gallery.Gallery;
import com.steema.teechart.functions.KDE;
import com.steema.teechart.functions.KDEClamp;
import com.steema.teechart.functions.KDESides;
import com.steema.teechart.functions.KDEType;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 *
 * <p>Title: Custom Violin abstract class</p>
 *
 * <p>Description: Custom Violin Series</p>
 *
 * <p>Copyright (c) 2005-2021 by Steema Software SL. All Rights Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 *
 * @see com.steema.teechart.TChart
 *
 */
public class CustomViolin extends Custom {
    
    private double _position;
    private KDE _kdeFunction;
    protected boolean firstDraw = false; // internal
    protected boolean IVertical = true;

    @Override
    protected void addSampleValues(int numValues) // boolean onlyMandatory
    {
        super.addSampleValues(numValues);

        setDataSource(null);

        ValueList tmpList;
        double lastVal;
        Random rand = new Random();

        //no necessari: if (this.getClass() == BeeSwarm.class)
        //    return;

        numValues = 10;

        tmpList = new ValueList(this, "YValues");

        lastVal = rand.nextInt(500);

        for (int t = 0; t < numValues; t++)
        {
            if (lastVal > 499)
                lastVal = 479;
            else if (lastVal < -100)
                lastVal = 20;

            if (t == (numValues / 2))
                lastVal = 75;

            if (t == (numValues / 3))
                lastVal = 25;

            ((ValueList)(tmpList)).addChartValue(lastVal + (20 - rand.nextInt(40)));
        }


        update(tmpList);
    }

    public CustomViolin()
    {
        super();
        _position = 0;

        IVertical= true;

        this.setColor(getSeriesColor());
        this.getPointer().setVisible(false);

        getKDEFunction().getOptions().setkDEType(KDEType.VIOLIN);
        getKDEFunction().getOptions().setResolution(100);
        getKDEFunction().getOptions().setBandwidth(20);
        getKDEFunction().getOptions().setWidth(50);
        getKDEFunction().getOptions().setClamp(KDEClamp.MINMAX);
        getKDEFunction().setScale(1);
    }
    
    public CustomViolin(IBaseChart c) {
        super(c);
        _position = 0;

        IVertical= true;

        this.setColor(getSeriesColor());
        this.getPointer().setVisible(false);

        getKDEFunction().getOptions().setkDEType(KDEType.VIOLIN);
        getKDEFunction().getOptions().setResolution(100);
        getKDEFunction().getOptions().setBandwidth(20);
        getKDEFunction().getOptions().setWidth(50);
        getKDEFunction().getOptions().setClamp(KDEClamp.MINMAX);
        getKDEFunction().setScale(1);
    }
    
    /// <summary>
    /// Sets the location on the non-data axis. If the datasource
    /// is a Box Series then the Violin or Beeswarm will automatically
    /// use the position of the source series.
    /// </summary>
    public double getPosition()
    {
        return _position;
    }
    public void setPosition(double _position) {    
        this._position = _position;
    }

    private KDE getKDEFunction() {
        if (_kdeFunction == null)
            _kdeFunction = new KDE();

        return _kdeFunction;
    }

    /// <summary>
    /// The KDE (Kernel Density Estimator) being used by the Series.
    /// </summary>
    public KDE getKdeFunction() {
        return getKDEFunction();
    }

    public void setKdeFunction(KDE _kdeFunction) {
        this._kdeFunction = _kdeFunction;
    }

    public void checkDataSource()
    {
        super.checkDataSource();
        CustomViolin.this.update();
    }

    /// <summary>
    /// Update runs the calculation. It assumes that a Series as datasource 
    /// has been set.
    /// </summary>
    public void update()
    {
        if ((this.getDataSource()!=null) /*&& (this.getDataSource().getClass() == Series.class)*/)
            update(((Series)(this.getDataSource())).mandatory);
    }

    /// <summary>
    /// Can update sending a Valuelist directly to the calculation engine.
    /// </summary>
    /// <param name="values"></param>
    public void update(ValueList values)
    {
        if ((getDataSource()!=null) && (getDataSource().getClass() == CustomBox.class))
            this.setPosition(((CustomBox)(getDataSource())).getPosition());

        if (!firstDraw) 
        {
            firstDraw = true;
            this.getChart().invalidate();  //provarem invalidate.
        }

        _kdeFunction.setPosition(this.getPosition());
        _kdeFunction.getOptions().setkDEType(KDEType.VIOLIN);
        _kdeFunction.addListPoints(values);
        _kdeFunction.calculateKDE();

        this.clear();

        getYValues().setOrder(ValueListOrder.NONE);
        getXValues().setOrder(ValueListOrder.NONE);

        for (int i = 0; i < _kdeFunction.getKDEPoints().size(); i++)
        {
            if (IVertical)
                add(_kdeFunction.getKDEPoints().get(i).x, _kdeFunction.getKDEPoints().get(i).y);
            else
                add(_kdeFunction.getKDEPoints().get(i).y, _kdeFunction.getKDEPoints().get(i).x);
        }



        if ((_kdeFunction.getOptions().getSides() == KDESides.LEFT) ||
                (_kdeFunction.getOptions().getSides() == KDESides.RIGHT))
        {
            if (IVertical)
            {
                add(_kdeFunction.getPosition(), _kdeFunction.getKDEPoints().get(_kdeFunction.getKDEPoints().size() - 1).y);
                add(_kdeFunction.getPosition(), _kdeFunction.getKDEPoints().get(0).y);
            }
            else
            {
                add(_kdeFunction.getKDEPoints().get(_kdeFunction.getKDEPoints().size() - 1).y, _kdeFunction.getPosition());
                add(_kdeFunction.getKDEPoints().get(0).y, _kdeFunction.getPosition());

            }
        }


        calcFirstLastVisibleIndex();
        getChart().invalidate();
    }

    @Override
    public void draw()
    {
        List<PointDouble> points;
        IGraphics3D aCanvas;

        if (getDataSource() != null)
            if (getDataSource().getClass() == Series.class)
                update(((Series)(getDataSource())).getYValues());

        aCanvas = getChart().getGraphics3D();

        aCanvas.setBrush(this.getBrush());
        aCanvas.getBrush().setColor(this.getColor());
        getChart().getGraphics3D().getBrush().setColor(this.getColor());

        aCanvas.setPen(this.getLinePen());

        points = Arrays.asList(new PointDouble[this.getCount()]);

        for (int i = 0; i < this.getCount(); i++)
        {
            points.set(i, new PointDouble(this.calcXPos(i), this.calcYPos(i)));
            if (this.getPointer().getVisible())
                this.drawPointer((int)points.get(i).x, (int)points.get(i).y, getValueColor(i), i);
        }
        
        if (getChart().getAspect().getView3D())
            aCanvas.polygon(0, points.toArray(new PointDouble[0]));
        else
            aCanvas.polygon(points.toArray(new PointDouble[0]));
    }

    /**
     * Gets descriptive text.
     *
     * @return String
     */
    public String getDescription() {
        return Language.getString("GalleryViolin");
    }

}

