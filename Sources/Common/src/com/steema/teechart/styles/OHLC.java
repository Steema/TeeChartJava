package com.steema.teechart.styles;


import com.steema.teechart.languages.Language;
import com.steema.teechart.DateTime;
import com.steema.teechart.IBaseChart;
import com.steema.teechart.misc.Utils;

/**
 *
 * <p>Title: OHLC class</p>
 *
 * <p>Description: OHLC is an base Series class that maintains lists for Open,
 * Close, High and Low values.</p>
 *
 * <p>Copyright (c) 2005-2019 by Steema Software SL. All Rights Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>

 *
 */
public class OHLC extends Custom {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected ValueList vHighValues;
    protected ValueList vLowValues;
    protected ValueList vOpenValues;

    public OHLC(IBaseChart c) {
        super(c);

        getXValues().setDateTime(true);
        getXValues().name = Language.getString("ValuesDate");
        getYValues().name = Language.getString("ValuesClose");

        vHighValues = new ValueList(this, Language.getString("ValuesHigh"));
        vLowValues = new ValueList(this, Language.getString("ValuesLow"));
        vOpenValues = new ValueList(this, Language.getString("ValuesOpen"));
    }

    public OHLC() {
        this(null);
    }
    
    public void assign(Series source) {
        if (source instanceof OHLC) {
            OHLC tmp = (OHLC)source;
            tmp.setChart(((OHLC) source).getChart());
            tmp.setCloseValues(((OHLC)source).getCloseValues());
            tmp.setDateValues(((OHLC)source).getDateValues());
            tmp.setHighValues(((OHLC)source).getHighValues());
            tmp.setLowValues(((OHLC)source).getLowValues());
        }
        super.assign(source);
    }    

    /**
     * All the Stock market Date values.<br>
     * You can access Date values in the same way you can access X or Y values.
     *
     * @return ValueList
     */
    public ValueList getDateValues() {
        return getXValues();
    }

    /**
     * Sets all Stock market Date values.<br>
     * You can access Date values in the same way you can access X or Y values.
     *
     * @param value ValueList
     */
    public void setDateValues(ValueList value) {
        setValueList(getXValues(), value);
    }

    /**
     * All the Stock market Close values.<br>
     * You can access Close values in the same way you can access X or Y values.
     *
     * @return ValueList
     */
    public ValueList getCloseValues() {
        return getYValues();
    }

    /**
     * Sets all Stock market Close values.<br>
     * You can access Close values in the same way you can access X or Y values.
     *
     * @param value ValueList
     */
    public void setCloseValues(ValueList value) {
        setValueList(getYValues(), value);
    }

    /**
     * All the Stock market Open values.<br>
     * You can access Open values in the same way you can access X or Y values.
     *
     * @return ValueList
     */
    public ValueList getOpenValues() {
        return vOpenValues;
    }

    /**
     * Sets all Stock market Open values.<br>
     * You can access Open values in the same way you can access X or Y values.
     *
     * @param value ValueList
     */
    public void setOpenValues(ValueList value) {
        setValueList(vOpenValues, value);
    }

    /**
     * All the Stock market High values.<br>
     * You can access High values in the same way you can access X or Y values.
     *
     * @return ValueList
     */
    public ValueList getHighValues() {
        return vHighValues;
    }

    /**
     * Sets all Stock market High values.<br>
     * You can access High values in the same way you can access X or Y values.
     *
     * @param value ValueList
     */
    public void setHighValues(ValueList value) {
        setValueList(vHighValues, value);
    }

    /**
     * All the Stock market Low values.<br>
     * You can access High values in the same way you can access X or Y values.
     *
     * @return ValueList
     */
    public ValueList getLowValues() {
        return vLowValues;
    }

    /**
     * Sets all Stock market Low values.<br>
     * You can access High values in the same way you can access X or Y values.
     *
     * @param value ValueList
     */
    public void setLowValues(ValueList value) {
        setValueList(vLowValues, value);
    }

    /**
     * Adds new point with specified integer index and double open, high,
     * low and close.
     *
     * @param index int
     * @param open double
     * @param high double
     * @param low double
     * @param close double
     * @return int index of added point
     */
    public int add(int index, double open, double high, double low,
                   double close) {
        double tmp = index;
        return add(tmp, open, high, low, close);
    }

    /**
     * Adds new point with specified double index and double open, high, low
     * and close.
     *
     * @param index double
     * @param open double
     * @param high double
     * @param low double
     * @param close double
     * @return int index of added point
     */
    public int add(double index, double open, double high, double low,
                   double close) {
        vHighValues.tempValue = high;
        vLowValues.tempValue = low;
        vOpenValues.tempValue = open;
        return add(index, close); // standard add X,Y
    }

    /**
     * Adds new point with specified double open, high, low and close.
     *
     * @param open double
     * @param high double
     * @param low double
     * @param close double
     * @return int index of added point
     */
    public int add(double open, double high, double low, double close) {
        return add(getCount(), open, high, low, close);
    }

    /**
     * Adds new point with specified DateTime x and double open, high, low
     * and close.
     * @param aDate DateTime
     * @param open double
     * @param high double
     * @param low double
     * @param close double
     * @return int index of added point
     */
    public int add(DateTime aDate, double open, double high, double low,
                   double close) {
        return add(aDate.toDouble(), open, high, low, close);
    }

    /**
     * Adds the DataView to the OHLC series.
     *
     * @param view DataView
     */
    /**
     * Validates Series datasource.
     *
     * @param value ISeries the series to validate.
     * @return boolean true if value can be a Series data source.
     */
    public boolean isValidSourceOf(ISeries value) {
        return value instanceof OHLC;
    }

    /**
     * The Maximum Value of the Series Y Values List.
     *
     * @return double
     */
    public double getMaxYValue() {
       return calcMinMaxValue(false);
    }

    /**
     * The Minimum Value of the Series Y Values List.<br>
     * As some Series have more than one Y Values List, this Minimum Value is
     * the "Minimum of Minimums" of all Series Y Values lists.
     *
     * @return double
     */
    public double getMinYValue() {
        return calcMinMaxValue(true);
    }
    
    protected double calcMinMaxValue(boolean isMin) {
      boolean tmpfirst = true;
      double result = 0.0;
      double tmpvalue = 0.0;
      
      for (int t = 0; t < getCount(); t++)
      {
        if ((!isNull(t)) || (getTreatNulls() == TreatNullsStyle.IGNORE)) {
          if(isMin) {
            tmpvalue = Math.min(this.getCloseValues().getValue(t), this.getHighValues().getValue(t));
            tmpvalue = Math.min(tmpvalue, this.getLowValues().getValue(t));
            tmpvalue = Math.min(tmpvalue, this.getOpenValues().getValue(t));
          }
          else {
            tmpvalue = Math.max(this.getCloseValues().getValue(t), this.getHighValues().getValue(t));
            tmpvalue = Math.max(tmpvalue, this.getLowValues().getValue(t));
            tmpvalue = Math.max(tmpvalue, this.getOpenValues().getValue(t));  
          }
          if (tmpfirst) {
            result = tmpvalue;
            tmpfirst = false;
          }
          else {
            if (isMin) {
                result = Math.min(result, tmpvalue);
            }
            else {
                result = Math.max(result, tmpvalue);
            }
          }
        }
      }
      return result;
    }

    protected int numSampleValues() {
        return 40;
    }

    protected void addSampleValues(int numValues) {
        SeriesRandom r = randomBounds(numValues);

        double aOpen = r.MinY + Utils.round(r.DifY * r.Random()); // starting open price

        for (int t = 1; t <= numValues; t++) {
            // Generate random figures
            RandomOHLC ohlc = getRandomOHLC(r, aOpen, r.DifY);

            // Call the standard add method
            add(r.tmpX, aOpen, ohlc.aHigh, ohlc.aLow, ohlc.aClose);

            r.tmpX += r.StepX; // <-- next point X value

            // Tomorrow, the market will open at today's close plus/minus something
            aOpen = ohlc.aClose + (10 * r.Random()) - 5;
        }
    }

    /**
     * Point characteristics
     *
     * @param index int
     * @return SeriesOHLCPoint
     */
    public SeriesOHLCPoint getOHLCPoint(int index) {
        return new SeriesOHLCPoint(this, index);
    }

    static private RandomOHLC getRandomOHLC(SeriesRandom rr, double aOpen,
                                            double yRange) {

        RandomOHLC r = new RandomOHLC();

        int tmpY = Math.abs(Utils.round(yRange / 400.0));

        r.aClose = aOpen + Utils.round(yRange / 25.0) * rr.Random() -
                   (yRange / 50.0); /* imagine a close price... */

        /* and imagine the high and low session price */

        double tmpFixed = 3 * Utils.round(Math.abs(r.aClose - aOpen) / 10.0);

        if (r.aClose > aOpen) {
            r.aHigh = r.aClose + tmpFixed + tmpY * rr.Random();
            r.aLow = aOpen - tmpFixed - tmpY * rr.Random();
        } else {
            r.aHigh = aOpen + tmpFixed + tmpY * rr.Random();
            r.aLow = r.aClose - tmpFixed - tmpY * rr.Random();
        }
        return r;
    }
}

