package com.steema.teechart.styles;


import com.steema.teechart.languages.Language;
import com.steema.teechart.drawing.GradientDirection;
import com.steema.teechart.IBaseChart;

/**
 *
 * <p>Title: HorizArea class</p>
 *
 * <p>Description: HorizArea series.</p>
 *
 *  <p>Example:
 * <pre><font face="Courier" size="4">
 *  areaSeries = new com.steema.teechart.styles.HorizArea(myChart.getChart());
 *  areaSeries.setStairs(false);
 *  areaSeries.getPointer().setVisible(false);
 *  areaSeries.fillSampleValues(6);
 * </font></pre></p>
 *
 *  <p>Copyright (c) 2005-2019 by Steema Software SL. All Rights
 *  Reserved.</p>
 *
 * <p>Company: Steema Software SL</p><br>

 */
public class HorizArea extends Area {

    public HorizArea(IBaseChart c) {
        super(c);
        setHorizontal();
        getXValues().setOrder(ValueListOrder.NONE);
        getYValues().setOrder(ValueListOrder.ASCENDING);
        getGradient().setDirection(GradientDirection.HORIZONTAL);
    }

    public HorizArea() {
        this((IBaseChart)null);
    }

    protected int numSampleValues() {
        return 10;
    }

    protected void drawMark(int valueIndex, String st,
                            SeriesMarksPosition aPosition) {
        int difH = aPosition.height / 2;
        int difW = getMarks().getCallout().getLength() + getMarks().getCallout().getDistance();

        aPosition.leftTop.y = aPosition.arrowTo.y - difH;
        aPosition.leftTop.x += difW + (aPosition.width / 2);
        aPosition.arrowTo.x += difW;
        aPosition.arrowFrom.y = aPosition.arrowTo.y;

        aPosition.arrowFrom.x += getMarks().getCallout().getDistance();

        super.drawMark(valueIndex, st, aPosition);
    }

    /**
     * Gets descriptive text.
     *
     * @return String
     */
    public String getDescription() {
        return Language.getString("HorizAreaSeries");
    }
}

