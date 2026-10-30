package com.steema.teechart.functions;

import java.util.ArrayList;
import com.steema.teechart.languages.Language;

import com.steema.teechart.styles.ValueList;
import com.steema.teechart.styles.Series;
import com.steema.teechart.styles.Custom;
import com.steema.teechart.IBaseChart;
import com.steema.teechart.styles.FastLine;
import com.steema.teechart.drawing.ChartPen;
import com.steema.teechart.functions.ExpMovAverage;
import com.steema.teechart.functions.MovingAverage;
import com.steema.teechart.functions.Function;

/**
 *
 * <p>Title: Bollinger class</p>
 *
 * <p>Description: Bollinger Bands Function.</p>
 *
 * <p>Copyright (c) 2005-2019 by Steema Software SL. All Rights
 * Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 *
 */
public class Bollinger extends Function {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private boolean fExponential = true;
    private double fDeviation = 2.0;
    private FastLine iOther;
    
    private double stdDev(ValueList v, int firstIndex, int lastIndex) {
        double iSum = 0.0;
        double iSum2 = 0.0;
        double tmp;
        for (int t=firstIndex; t<=lastIndex; t++) {
            tmp = v.value[t];
            iSum += tmp;
            iSum2 += Math.pow(tmp,2);
        }
        
        iSum = (getPeriod()*iSum2-Math.pow(iSum,2))/Math.pow(getPeriod(),2);
        if (iSum>0) return Math.sqrt(iSum);  else return 0.0; //  Negative square root not allowed
    }
    
    private void internalAddPoints(Series src, Series dest, double aDev) {
        Function mov;
        FastLine tmpl;
        String tmpSource;
        double tmpValue;
        
        if (fExponential) mov = new ExpMovAverage();
        else mov = new MovingAverage();
        
        mov.setPeriod(getPeriod());

        tmpl = new FastLine();
        try{
            tmpl.InternalUse = true;
            tmpl.setShowInLegend(false);
            tmpl.setChart(src.getChart());
            tmpl.setDataSource(src);
            tmpSource = getSeries().getMandatory().valueSource;
            if (tmpSource=="") tmpSource = src.getMandatory().getName();

            tmpl.getMandatory().setDataMember(tmpSource);
            tmpl.setFunction(mov);

            ValueList list = src.getYValueList(tmpSource);
            int tmpPeriod = ((int) getPeriod());
            dest.clear();
            for (int t=tmpPeriod; t<src.getCount(); t++) {
                tmpValue = aDev*stdDev(list,t-tmpPeriod,t-1);
                if (fExponential) tmpValue += tmpl.getYValues().value[t-1];
                else tmpValue += tmpl.getYValues().value[t-tmpPeriod];

                dest.add(src.getXValues().value[t-1],tmpValue);
            }
        }
        finally {
            tmpl.setDataSource(null);
            tmpl.dispose();
        }
    }
    
    public Bollinger() {
        this((IBaseChart)null);
    }

    public Bollinger(IBaseChart c) {
        super(c);
        SingleSource = true;
        iOther = new FastLine();
        iOther.InternalUse = true;
        iOther.setShowInLegend(false);
        dPeriod = 10;
    }
    
    public void dispose() {
        iOther.dispose();
    } 
    
    
    public void clear() {
        super.clear();
        iOther.clear();
    }
   
    /**
     * Gets all points from Source series, performs a function operation on
     * points and stores results in ParentSeries.<br>
     *
     * @param source ArrayList
     */
    public void addPoints(ArrayList source) {
        if (!updating) { 
            if (source != null) {
                if (source.size() > 0) {
                    Series s = (Series) source.get(0);
                    iOther.setChart(getSeries().getChart());
                    iOther.setCustomVertAxis(getSeries().getCustomVertAxis());
                    iOther.setVerticalAxis(getSeries().getVerticalAxis());
                    iOther.setColor(getSeries().getColor());
                    iOther.getXValues().setDateTime(getSeries().getXValues().getDateTime());
                    // @TODO add events
                    
                    if (getPeriod()>0) {
                        internalAddPoints(s,getSeries(),fDeviation);
                        internalAddPoints(s,iOther,-fDeviation);
                    }
                }
            }
        }
    }
    
    /** Gets exponential
     * @return true, if exponential.
     **/
    public boolean getExponential() {
        return fExponential;
    }

    /** Sets exponential
     * @param value True, if exponential
     **/
    public void setExponential(boolean value) {
        if (fExponential!=value) {
            fExponential = value;
            recalculate();
        }
    }

    /** Gets deviation
     * @return Deviation.
     **/
    public double getDeviation() {
        return fDeviation;
    }
    
    /** Sets deviation
     * @param value Deviation value
     **/
    public void setDeviation(double value) {
        if (fDeviation!=value) {
            fDeviation = value;
            recalculate();
        }
    }
    
    /** Gets low band series
     * @return low band FastLine series
     **/
    public Series getLowBand()
    {
        return iOther;
    }
    
    /** Gets low band pen properties
     * @return Low band pen
     **/
    public ChartPen getLowBandPen(){
        return iOther.getLinePen();
    }
    
    /** Sets lower band pen properties
     * @param value Chart pen
     **/
    public void setLowBandPen(ChartPen value){
        if (iOther!=null) {
            iOther.getLinePen().assign(value);
        }
    }
    
    /** Gets upper band pen properties
     * @return Upper band pen
     **/
    public ChartPen getUpperBandPen(){
        if (getSeries()!=null && getSeries() instanceof Custom)
        {
            return ((Custom)getSeries()).getLinePen();
        }
        else return null;
    }
    
    /** Sets upper band pen properties
     * @param value Chart pen
     **/
    public void setUpperBandPen(ChartPen value){
        if (getSeries()!=null && getSeries() instanceof Custom)
        {
            ((Custom)getSeries()).getLinePen().assign(value);
        }
    }

    /**
     * Gets descriptive text.
     *
     * @return String
     */
    public String getDescription() {
        return Language.getString("FunctionBollinger");
    }
}

