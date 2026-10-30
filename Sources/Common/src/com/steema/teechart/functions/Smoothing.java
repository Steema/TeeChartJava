package com.steema.teechart.functions;


import com.steema.teechart.drawing.Point;
import com.steema.teechart.languages.Language;
import java.util.ArrayList;
import com.steema.teechart.IBaseChart;
import com.steema.teechart.styles.ValueList;
import com.steema.teechart.styles.Series;

/**
 *
 * <p>Title: Smoothing class</p>
 *
 * <p>Description: Smoothing Function.</p>
 *
 * <p>Copyright (c) 2005-2019 by Steema Software SL. All Rights
 * Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>

 *
 */
public class Smoothing extends Function {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private boolean interpolate = true;
    private int factor = 4;

    public Smoothing() {
        this((IBaseChart)null);
    }

    public Smoothing(IBaseChart c) {
        super(c);
        canUsePeriod = false;
        SingleSource=true;
        dPeriod = 1;
    }

    /**
     * When true, resulting smooth curves will pass exactly over source points.
     * <br>
     * When false, the smooth curves will not necessarily pass over source
     * points. <br>
     * Default value: true
     *
     * @return boolean
     */
    public boolean getInterpolate() {
        return interpolate;
    }

    /**
     * Resulting smooth curves will pass exactly over source points when true.
     * <br>
     * When false, the smooth curves will not necessarily pass over source
     * points. <br>
     * Default value: true
     *
     * @param value boolean
     */
    public void setInterpolate(boolean value) {
        if (interpolate != value) {
            interpolate = value;
            recalculate();
        }
    }

    /**
     * The number of times the resulting smooth points are compared to source
     * points. <br>
     * For example, a value of 4 means the smooth points will be 4 times
     * the number of source points. <br>
     * The greater the factor value is, the smoother the resulting curves will
     * be. <br>
     * Default value: 4
     *
     * @return int
     */
    public int getFactor() {
        return factor;
    }

    /**
     * Sets the number of times the resulting smooth points are
     * compared to source points. <br>
     * Default value: 4
     *
     * @param value int
     */
    public void setFactor(int value) {
        if (factor != value) {
            factor = Math.max(1, value);
            recalculate();
        }
    }

    /**
     * gets all points from Source series, performs a function operation on
     * points and finally stores results in ParentSeries. <br>
     *
     * @param source ArrayList
     */
    public  void addPoints(ArrayList source) {
        if (!updating) {
            if (source != null) {
                if (source.size() > 0) {
                    Series s = (Series) source.get(0);
                    getSeries().clear();
                    if (s.getCount() > 0) {
                        ValueList v = valueList(s);
                        Spline sp = new Spline();
                        for (int t = 0; t < s.getCount(); t++) {
                            sp.addPoint(s.getXValues().value[t], v.value[t]);
                            sp.setKnuckle(t, false);
                        }
                        sp.setInterpolated(interpolate);
                        sp.setFragments(s.getCount() * factor);

                        for (int t = 0; t <= sp.getFragments(); t++) {
                           Point.Double p = sp.value((double) t / sp.getFragments());
                            if (getSeries().getYMandatory()) {
                              getSeries().add(p.x, p.y);
                            } else {
                              getSeries().add(p.y, p.x);
                           }                        
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
    public  String getDescription() {
        return Language.getString("FunctionSmooth");
    }
}

