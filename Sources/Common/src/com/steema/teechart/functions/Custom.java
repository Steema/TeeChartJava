package com.steema.teechart.functions;


import com.steema.teechart.languages.Language;
import java.util.ArrayList;
import com.steema.teechart.IBaseChart;

/**
 *
 * <p>Title: Custom class</p>
 *
 * <p>Description: Custom y=f(x) function</p>
 *
 * <p>Copyright (c) 2005-2019 by Steema Software SL. All Rights
 * Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 *
 */
public class Custom extends Function {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int numPoints = 100;
    private YCalculator yCalculator;
    private double startX;
    public double x;

    public Custom() {
        this((IBaseChart)null);
    }

    public Custom(IBaseChart c) {
        super(c);
        noSourceRequired = true;
        dPeriod = 1;
    }

    /**
     * The number of points.<br>
     * The function will call its OnCalculate event this number of times. <br>
     * Default value: 100

     *
     * @return int
     */
    public int getNumPoints() {
        return numPoints;
    }

    /**
     * Sets the number of points.<br>
     * The function will call its OnCalculate event this number of times. <br>
     * Default value: 100
     *
     * @param value int
     */
    public void setNumPoints(int value) {
        numPoints = value;
    }

    public YCalculator getYCalculator() {
        return yCalculator;
    }

    public void setYCalculator(YCalculator value) {
        yCalculator=value;
    }

    /**
     * The initial value for the "X" parameter.<br>
     * The function will call its OnCalculate event this number of times. <br>
     * Default value: O
     *
     * @return double
     */
    public double getStartX() {
        return startX;
    }

    /**
     * Sets the initial value for the "X" parameter.<br>
     * The function will call its OnCalculate event this number of times. <br>
     * Default value: O
     *
     * @param value double
     */
    public void setStartX(double value) {
        startX = value;
    }

    /**
     * Gets all points from Source series, performs a function operation and
     * stores results in ParentSeries.<br>
     *
     * @param source ArrayList
     */
    public void addPoints(ArrayList source) {
        if ((!updating) /*&& ( source!=null )*/) {
            //if (source.size()>0)
            {
//					Series s=(Series)source.GetValue(0);
//					if ( ( s.Count>0 ) && (CalculateEvent!=null) )
//					{
                getSeries().clear();
                x = startX;

                double y = 0;
                for (int t = 1; t <= numPoints; t++) {

                    if (yCalculator != null) {
                        y = yCalculator.calculate(this, x);
                    }

                    if (getSeries().getYMandatory()) {
                        getSeries().add(x, y);
                    } else {
                        getSeries().add(y, x);
                    }
                    x += getPeriod();
                }
//					}
            }
        }
    }

    /**
     * Gets descriptive text.
     *
     * @return String
     */
    public String getDescription() {
        return Language.getString("FunctionCustom");
    }

    public interface YCalculator {
        public double calculate(Custom series, double x);
    }
}

