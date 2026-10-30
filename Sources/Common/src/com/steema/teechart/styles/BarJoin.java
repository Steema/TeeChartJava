package com.steema.teechart.styles;


import com.steema.teechart.languages.Language;
import com.steema.teechart.drawing.ChartPen;
import com.steema.teechart.drawing.Color;
import com.steema.teechart.Rectangle;
import com.steema.teechart.IBaseChart;
import com.steema.teechart.drawing.IGraphics3D;


/**
 *
 * <p>Title: BarJoin class</p>
 *
 * <p>Description: BarJoin series.</p>
 *
 * <p>Example:
 * <pre><font face="Courier" size="4">
 * series = new BarJoin(myChart.getChart());
 * series.fillSampleValues(3);
 * series.getJoinPen().setWidth(2);
 * series.setColor(Color.RED);
 * </font></pre></p>
 *
 * <p>Copyright (c) 2005-2019 by Steema Software SL. All Rights
 * Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 *
 */
public class BarJoin extends Bar {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private ChartPen joinPen;

    transient private Rectangle oldBarBounds;
    transient private boolean firstPoint;

    public BarJoin() {
        this(null);
    }

    public BarJoin(IBaseChart c) {
        super(c);
    }
    
    @Override
    public void assign(Series source) {
            super.assign(source);
            if(source instanceof BarJoin)
            {
                this.joinPen.assign(((BarJoin)source).joinPen);
            }
    }

    /**
     * Determines the pen to draw the lines between each bar point.
     *
     * @return ChartPen
     */
    public ChartPen getJoinPen() {
        if (joinPen == null) {
            joinPen = new ChartPen(chart, Color.BLUE);
        }
        return joinPen;
    }

    public void doBeforeDrawChart() {
        super.doBeforeDrawChart();
        firstPoint = true;
    }

    public void prepareForGallery(boolean isEnabled) {
        super.prepareForGallery(isEnabled);
        getJoinPen().setColor(Color.BLUE);
        joinPen.setWidth(2);
        fillSampleValues(2);
    }

    /**
     * Internal use.
     *
     * @param barIndex int
     * @param startPos int
     * @param endPos int
     */
    public void drawBar(int barIndex, int startPos, int endPos) {
        super.drawBar(barIndex, startPos, endPos);

        if (!firstPoint) {
            int tmpA=0;
            int tmpB=0;

            BarStyle tmpStyle = getBarStyle();

            if ((tmpStyle == BarStyle.PYRAMID) |
                (tmpStyle == BarStyle.ELLIPSE) |
                (tmpStyle == BarStyle.ARROW) |
                (tmpStyle == BarStyle.CONE)) {
                tmpA = (oldBarBounds.getLeft() + oldBarBounds.getRight()) / 2;
                tmpB = (getBarBounds().getLeft() + getBarBounds().getRight()) / 2;
            } else {
                tmpA = oldBarBounds.getRight();
                tmpB = getBarBounds().getLeft();
            }

            int tmpTopA = oldBarBounds.getTop();
            int tmpTopB = getBarBounds().getTop();

            if (!drawValuesForward()) {
                tmpA = getBarBounds().getRight();
                tmpB = oldBarBounds.getLeft();

                int old = tmpTopA;
                tmpTopA = tmpTopB;
                tmpTopB = old;
            }

            IGraphics3D g = chart.getGraphics3D();

            g.setPen(getJoinPen());
            if (chart.getAspect().getView3D()) {
                g.moveTo(tmpA, tmpTopA, getMiddleZ());
                g.lineTo(tmpB, tmpTopB, getMiddleZ());
            } else {
                g.moveTo(tmpA, tmpTopA);
                g.lineTo(tmpB, tmpTopB);
            }
        }

        firstPoint = false;
        oldBarBounds = getBarBounds();
    }

    protected int numSampleValues() {
        return 3;
    }

    /**
     * Gets descriptive text.
     *
     * @return String
     */
    public String getDescription() {
        return Language.getString("GalleryBarJoin");
    }
}

