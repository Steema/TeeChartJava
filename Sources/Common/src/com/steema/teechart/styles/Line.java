package com.steema.teechart.styles;

import com.steema.teechart.editors.gallery.Gallery;
import com.steema.teechart.languages.Language;

import com.steema.teechart.IBaseChart;
import com.steema.teechart.drawing.Color;
import com.steema.teechart.drawing.Gradient;
import com.steema.teechart.drawing.IGraphics3D;
import com.steema.teechart.drawing.ChartBrush;
import com.steema.teechart.drawing.Point;
import com.steema.teechart.misc.Utils;

/**
 *
 * <p>Title: Line class</p>
 *
 * <p>Description: Line Series.</p>
 *
 * <p>Copyright (c) 2005-2019 by Steema Software SL. All Rights Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 *
 */
public class Line extends Custom
{
	private static final long serialVersionUID = 1L;

    public Line(IBaseChart c)
    {
        super(c);
        drawLine = true;
        getPointer().setVisible(false);
        getPointer().setDefaultVisible(false);
        allowSinglePoint = false;
        drawBetweenPoints = true;
    }

    public Line()
    {
        this((IBaseChart) null);
    }

    /**
     * Gets descriptive text.
     *
     * @return String
     */
    public String getDescription()
    {
        return Language.getString("GalleryLine");
    }

    /**
     * Determines the Line Gradient.
     *
     * @return Gradient
     */
    public Gradient getGradient()
    {
        return bBrush.getGradient();
    }

    protected void prepareLegendCanvas(IGraphics3D g, int valueIndex,
            Color backColor,
            ChartBrush aBrush)
    {
        g.setPen(getLinePen());
        g.setBrush(aBrush);
    }

    public void createSubGallery(Gallery addSubChart)
    {
        super.createSubGallery(addSubChart);
        addSubChart.createSubChart(Language.getString("Stairs"));
        addSubChart.createSubChart(Language.getString("Points"));
        addSubChart.createSubChart(Language.getString("Height"));
        addSubChart.createSubChart(Language.getString("Hollow"));
        addSubChart.createSubChart(Language.getString("Colors"));
        addSubChart.createSubChart(Language.getString("Marks"));
        addSubChart.createSubChart(Language.getString("NoBorder"));
    }

    public void setSubGallery(int index)
    {
        switch (index)
        {
            case 1:
                setStairs(true);
                break;
            case 2:
                getPointer().setVisible(true);
                break;
            case 3:
                setLineHeight(5);
                break;
            case 4:
                getBrush().setVisible(false);
                break;
            case 5:
                setColorEach(true);
                break;
            case 6:
                getMarks().setVisible(true);
                break;
            case 7:
                getLinePen().setVisible(false);
                break;
        }
    }
}
