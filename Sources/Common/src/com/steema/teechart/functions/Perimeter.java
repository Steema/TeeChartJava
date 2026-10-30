package com.steema.teechart.functions;

import java.util.ArrayList;
import com.steema.teechart.languages.Language;

import com.steema.teechart.IBaseChart;
import com.steema.teechart.drawing.IGraphics3D;
import com.steema.teechart.drawing.Point;
import com.steema.teechart.styles.Series;

import com.steema.teechart.styles.ValueListOrder;

/**
 *
 * <p>Title: Perimeter class</p>
 *
 * <p>Description: Perimeter function.</p>
 *
 *<p>Example:
 * <pre><font face="Courier" size="4">
 * function = new com.steema.teechart.functions.Perimeter();
 * function.setChart(myChart.getChart());
 * function.setPeriod(0); //all points
 *
 * Line functionSeries = new Line(myChart.getChart());
 * functionSeries.setTitle("Perimeter");
 * functionSeries.setDataSource(series);
 * functionSeries.getXValues().setOrder(ValueListOrder.NONE);
 * //functionSeries.setVerticalAxis(VerticalAxis.RIGHT);
 * functionSeries.setFunction(function);
 * </font></pre></p>
 *
 * <p>Copyright (c) 2005-2019 by Steema Software SL. All Rights
 * Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 *
 */
public class Perimeter extends Function {

    public Perimeter() {
        this((IBaseChart)null);
    }

    public Perimeter(IBaseChart c) {
        super(c);

        canUsePeriod = false;
        SingleSource = true;
    }

    public void addPoints(ArrayList source) {

        if (!updating) {
            if (source != null) {
                if (source.size() > 0) {
                    Series src = (Series) source.get(0);

                    if (src.getCount() > 0) {

                        getSeries().beginUpdate();
                        getSeries().clear();
                        getSeries().getXValues().setOrder(ValueListOrder.NONE);

                        Point[] p = new Point[src.getCount()];
                        try {
                            if (src.getVertAxis().iAxisSize == 0 ||
                                src.getHorizAxis().iAxisSize == 0) {

                                // MS : Not good, rectangle is (0,0,0,0).
                                // How to match it with actual size ??
                                // Force repaint:
                                getChart().getParent().refreshControl();
                            }

                            IGraphics3D tmpG = getChart().getGraphics3D();

                            for (int t = 0; t < src.getCount(); t++) {
                                p[t] = tmpG.calc3DPoint(
                                        src.calcXPos(t), src.calcYPos(t),
                                        src.getMiddleZ());
                            }

                            int sz = tmpG.convexHull(p);

                            for (int t = 0; t < sz; t++) {
                                getSeries().add(getSeries().xScreenToValue(
                                        p[t].x),
                                                getSeries().yScreenToValue(p[t].
                                        y));
                            }
                            if (sz > 0) {
                                getSeries().add(getSeries().getXValues().
                                                value[0],
                                                getSeries().getYValues().value[
                                                0]);
                            }
                        } finally {
                            p = null;
                            getSeries().endUpdate();
                        }
                    }
                }
            }
        }
    }


    /**
     * Gets descriptive text.
     *
     * @return String
     */
    public String getDescription() {
        return Language.getString("FunctionPerimeter");
    }
}

