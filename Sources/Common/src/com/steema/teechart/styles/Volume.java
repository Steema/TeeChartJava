package com.steema.teechart.styles;


import com.steema.teechart.languages.Language;
import com.steema.teechart.drawing.Color;
import com.steema.teechart.Rectangle;
import com.steema.teechart.drawing.DashStyle;
import com.steema.teechart.IBaseChart;
import com.steema.teechart.drawing.IGraphics3D;
import com.steema.teechart.drawing.Graphics3D;
import com.steema.teechart.drawing.Point;
import com.steema.teechart.editors.gallery.Gallery;
import com.steema.teechart.misc.Utils;

/**
 *
 * <p>Title: Volume class</p>
 *
 * <p>Description: Volume Series.</p>
 *
 * <p>Example:
 * <pre><font face="Courier" size="4">
 * volumeSeries = new Volume(myChart.getChart());
 * volumeSeries.getMarks().setVisible(false);
 * volumeSeries.setColor(Color.RED);
 * volumeSeries.fillSampleValues(50);
 * volumeSeries.setUseOrigin(true);
 * </font></pre></p>
 *
 * <p>Copyright (c) 2005-2019 by Steema Software SL. All Rights Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 *
 */
public class Volume extends Custom {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private boolean useYOrigin = false;
    private double origin;
    private Color iColor;

    public Volume(IBaseChart c) {
        super(c);

        drawArea = false;
        drawBetweenPoints = false;
        setClickableLine(false);
        getPointer().setDefaultVisible(false);
    }

    public Volume() {
        this(null);
    }
    
    public void assign(Series source) {
        if (source instanceof Volume) {
            Volume tmp = (Volume)source;
            tmp.setColor(((Volume)source).getColor()); 
            tmp.setOrigin(((Volume)source).getOrigin());
            tmp.setUseOrigin(((Volume)source).getUseOrigin());
        }
        super.assign(source);
    }     

    private void prepareCanvas(boolean forced, Color aColor) {
        prepareCanvas(chart.getGraphics3D(), forced, aColor);
    }

    private void prepareCanvas(IGraphics3D g, boolean forced, Color aColor) {
        if (forced || (aColor != iColor)) {
            g.setPen(getLinePen());
            g.getPen().setColor(aColor);
            //g.BackMode=cbmTransparent;
            //IColor=AColor; //CDI
        }
    }

    /**
     * Enables/Disables the Y value that defines the bottom position for Volume
     * points.<br>
     * Default value: false
     *
     * @return boolean
     */
    public boolean getUseOrigin() {
        return useYOrigin;
    }

    /**
     * Enables/Disables the Y value that defines the bottom position for Volume
     * points.<br>
     * Default value: false
     *
     * @param value boolean
     */
    public void setUseOrigin(boolean value) {
        useYOrigin = setBooleanProperty(useYOrigin, value);
    }

    /**
     * Defines the YValue used as the origin for the specified Volume Series.
     * <br>
     * Default value: 0
     *
     * @return double
     */
    public double getOrigin() {
        return origin;
    }

    /**
     * Defines the YValue used as the origin for the specified Volume Series.
     * <br>
     * Default value: 0
     *
     * @param value double
     */
    public void setOrigin(double value) {
        origin = setDoubleProperty(origin, value);
    }

    protected void addSampleValues(int numValues) {
        SeriesRandom r = randomBounds(numValues);

        for (int t = 1; t <= numValues; t++) {
            add(r.tmpX, Utils.round(r.DifY / 15) * r.Random());
            r.tmpX += r.StepX;
        }
    }

    public void createSubGallery(Gallery addSubChart) {
        super.createSubGallery(addSubChart);
        addSubChart.createSubChart(Language.getString("Dotted"));
        addSubChart.createSubChart(Language.getString("Colors"));
        addSubChart.createSubChart(Language.getString("Origin")); // 5.02
    }

    public void setSubGallery(int index) {
        switch (index) {
        case 1:
            getLinePen().setStyle(DashStyle.DOT);
            break;
        case 2:
            setColorEach(true);
            break;
        case 3:
            setUseOrigin(true);
            break;
        default:
            super.setSubGallery(index);
        }
    }

    protected void drawLegendShape(IGraphics3D g, int valueIndex,
                                   Rectangle r) {
        prepareCanvas(g, true, getValueColor(valueIndex)); //CDI
        g.horizontalLine(r.x, r.getRight(), (r.y + r.getBottom()) / 2);
    }

    /**
     * Called internally. Draws the "ValueIndex" point of the Series.
     *
     * @param valueIndex int
     */
    public void drawValue(int valueIndex) {
        IGraphics3D g = chart.getGraphics3D();
        g.setPen(getLinePen());
        prepareCanvas(valueIndex == firstVisible, getValueColor(valueIndex));

        // moves to x,y coordinates and draws a vertical bar to top or bottom,
        // depending on the vertical Axis.Inverted property

        int tmpY;

        if (useYOrigin) {
            tmpY = calcYPosValue(origin); /* 5.02 */
        } else
        if (getVertAxis().getInverted()) {
            tmpY = getVertAxis().iStartPos;
        } else {
            tmpY = getVertAxis().iEndPos;
        }

        if (chart.getAspect().getView3D()) {
            g.verticalLine(calcXPos(valueIndex), calcYPos(valueIndex), tmpY,
                           getMiddleZ());
        } else {
            g.verticalLine(calcXPos(valueIndex), tmpY, calcYPos(valueIndex));
            /* 5.02 */
        }
    }
    
        /**
     * Returns the ValueIndex of the "clicked" point in the Series.
     *
     * @param x int
     * @param y int
     * @return int
     */
    public int clicked(int x, int y) {
        if (chart != null) {
            Point p = chart.getGraphics3D().calculate2DPosition(x, y, getStartZ());
            x = p.x;
            y = p.y;

            int tmpOrigin=originPosition();
            int tmpX;

            if ((firstVisible > -1) && (lastVisible > -1)) {
                for (int t = firstVisible; t <= lastVisible; t++) {
                    tmpX=calcXPos(t);

                    if (Graphics3D.pointInLineTolerance(p,new Point(tmpX,tmpOrigin),
                            new Point(tmpX,calcYPos(t)),getLinePen().getWidth()))
                    {
                        doClickPointer(t, x, y);
                        return t;
                    }
                }
            }
        }
        return -1;
    }
    
    public int originPosition()
    {
       if (useYOrigin)
         return calcYPosValue(this.getOrigin());
      else
       if (getVertAxis().getInverted())
         return (int)Math.round(getVertAxis().iStartPos);
       else return (int)Math.round(getVertAxis().iEndPos);
    }

    public void prepareForGallery(boolean isEnabled) {
        super.prepareForGallery(isEnabled);
        fillSampleValues(26);
        point.setInflateMargins(true);
    }

    public void setColor(Color color) {
        super.setColor(color);
        getLinePen().setColor(color);
    }

    protected int numSampleValues() {
        return 40;
    }

    /**
     * Gets descriptive text.
     *
     * @return String
     */
    public String getDescription() {
        return Language.getString("GalleryVolume");
    }
}

