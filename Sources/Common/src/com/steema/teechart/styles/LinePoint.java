package com.steema.teechart.styles;


import com.steema.teechart.languages.Language;
import com.steema.teechart.drawing.IGraphics3D;
import com.steema.teechart.IBaseChart;
import com.steema.teechart.drawing.Color;

/**
 *
 * <p>Title: LinePoint class</p>
 *
 * <p>Description: LinePoint Series.</p>
 *
 * <p>Example:
 * <pre><font face="Courier" size="4">
 * series = new LinePoint(myChart.getChart());
 * series.fillSampleValues(10);
 * series.getLinePen().setColor(Color.BLUE);
 * series.getLinePen().setStyle(DashStyle.DASH);
 * </font></pre></p>
 *
 * <p>Copyright (c) 2005-2019 by Steema Software SL. All Rights
 * Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 *
 */
public class LinePoint extends Points {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
     * Creates a new LinePoint series without adding it to a Chart.
     */
    public LinePoint() {
        this(null);
    }

    /**
     * Creates a new LinePoint series and adds it to a Chart
     * @param chart IBaseChart
     */
    public LinePoint(IBaseChart chart) {
        super(chart);
        getPointer().setDraw3D(false);
        getPointer().setStyle(PointerStyle.DIAMOND);
        getLinePen().setColor(Color.RED); // <-- set default color to RED
    }

    /* overrided drawValue to draw additional lines for each point */
    /**
     * Called internally. Draws the "ValueIndex" point of the Series.
     *
     * @param valueIndex int
     */
    public void drawValue(int valueIndex) {

        /* calculate the point position */
        int tmpX = calcXPos(valueIndex);
        int tmpY = calcYPos(valueIndex);

        IGraphics3D g = chart.getGraphics3D();

        /* change brush && pen attributes */
        g.getBrush().setVisible( false);

        //BackMode=cbmTransparent;
        g.setPen(linePen);

        /* draw the horizontal && vertical lines */
        g.moveTo(getVertAxis().getPosition(), tmpY, getStartZ());
        g.lineTo(tmpX, tmpY, getStartZ());
        g.lineTo(tmpX, getHorizAxis().getPosition(), getStartZ());

        super.drawValue(valueIndex); /* <-- draw the point */
    }

    /**
     * Gets descriptive text.
     *
     * @return String
     */
    public String getDescription() {
        return Language.getString("GalleryLinePoint");
    }
}

