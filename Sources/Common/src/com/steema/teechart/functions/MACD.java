package com.steema.teechart.functions;

import com.steema.teechart.drawing.Color;
import com.steema.teechart.languages.Language;
import com.steema.teechart.IBaseChart;
import com.steema.teechart.styles.Series;
import com.steema.teechart.styles.Custom;
import com.steema.teechart.styles.Volume;
import com.steema.teechart.styles.FastLine;
import com.steema.teechart.styles.Line;
import com.steema.teechart.functions.ExpMovAverage;
import com.steema.teechart.drawing.ChartPen;
import java.util.ArrayList;


/**
 *
 * <p>Title: MACD class</p>
 *
 * <p>Description: MACD Function.</p>
 *
 * <p>Copyright (c) 2005-2019 by Steema Software SL. All Rights
 * Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 *
 */
public class MACD extends Moving {
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Volume histoS;
    private FastLine otherS;
    private ExpMovAverage movF1, movF2, movF3;
    private Line ser1,ser2;

    private void HideSeries(Series ASeries) {
        ASeries.setShowInLegend(false);
        ASeries.InternalUse = true;
    }  
    
    private void PrepareSeries(Series ASeries) {
        ASeries.setChart(getSeries().getChart());
        ASeries.setCustomVertAxis(getSeries().getCustomVertAxis());
        ASeries.setVerticalAxis(getSeries().getVerticalAxis());
        ASeries.getXValues().setDateTime(getSeries().getXValues().getDateTime());
        // TODO : add events
    }
    
    /** Creates a new instance of MACD */
    public MACD() {
        this((IBaseChart)null);
    }

    public MACD(IBaseChart c) {
        super(c);
        SingleSource = true;
        movF1 = new ExpMovAverage();
        movF1.setPeriod(12);
        ser1 = new Line();
        ser1.setFunction(movF1);
        
        movF2 = new ExpMovAverage();
        movF2.setPeriod(26);
        ser2 = new Line();
        ser2.setFunction(movF2);
        setPeriod(movF2.getPeriod());
        
        movF3 = new ExpMovAverage();
        movF3.setPeriod(9);
        otherS = new FastLine();
        HideSeries(otherS);
        otherS.setFunction(movF3);
        otherS.setColor(Color.GREEN);
        
        histoS = new Volume();
        HideSeries(histoS);
        histoS.setColor(Color.RED);
        histoS.setUseOrigin(true);
        histoS.setOrigin(0.0);
    }
    
    public void dispose() {
        ser1.dispose();
        ser2.dispose();
    } 
    
    public void clear() {
        super.clear();
        histoS.clear();
        otherS.clear();
    }
    
    ///Gets all points from Source series, performs a function operation and stores results in ParentSeries.
    public void addPoints(ArrayList source) {
        if (!updating) {
            if (source != null) {
                if (source.size() > 0) {
                    Series src = (Series)source.get(0);
                    getSeries().clear();
                    if (src.getCount()>1) {
                        movF1.addPoints(source);
                        movF2.setPeriod(getPeriod());
                        movF2.addPoints(source);
                        for (int i=0; i<src.getCount(); i++)
                            this.getSeries().add(src.getXValues().value[i],
                                    ser1.getYValues().value[i]-ser2.getYValues().value[i]);
                        ser1.clear();
                        ser2.clear();
                    }
                    
                    // Calculate "MACDExp" second line, even if not visible.
                    PrepareSeries(otherS);
                    if (getSeries().getColor() == Color.WHITE)
                        getSeries().setColor(Color.BLUE);
                    
                    otherS.setDataSource(getSeries());
                    // Draw histogram
                    if (histoS.getActive()) {
                        PrepareSeries(histoS);
                        histoS.beginUpdate();
                        histoS.clear();
                        for (int t=0; t<otherS.getCount(); t++)
                            histoS.add(otherS.getXValues().value[t],
                                    getSeries().getYValues().value[t]-otherS.getYValues().value[t]);
                        histoS.endUpdate();
                    }
                }
            }
        }
    }
    
    /**
     * Defines the moving average period.
     *
     * @return Moving average 2 period
     */
    public double getPeriod2(){
        return movF1.getPeriod();
    }
    
    /**
     * Defines the moving average period.
     */
    public void setPeriod2(double value) {
        if (value!= movF1.getPeriod()) {
            value = Math.max(value,1.0);
            movF1.setPeriod(value);
            recalculate();
        }
    }
    
    /**
     * Defines the moving average period.
     *
     * @return Moving average 3 period
     */
    public double getPeriod3(){
        return otherS.getFunction().getPeriod();
    }
    
    /**
     * Defines the moving average period.
     */
    public void setPeriod3(double value){
        movF3.setPeriod(value);
    }
    
    /**
     * MACD Histogram series
     * @return  MACD histogram series
     */
    public Volume getHistogram(){
        return histoS;
    }
    
    /**
     * MACD Exponential MA series
     * @return  MACD Exponential MA series
     */
    public FastLine getMACDExp() {
        return otherS;
    }
    
    /**
     * MACD Histogram pen characteristics
     * @return  MACD Histogram pen characteristics
     */
    public ChartPen getHistoPen(){
        return histoS.getLinePen();
    }
    
    /**
     * Sets MACD Histogram pen characteristics
     */
    public void setHistoPen(ChartPen value) {
        if (histoS != null)
            histoS.getLinePen().assign(value);
    }
    
    /**
     * MACD EXP MA pen characteristics
     * @return  MACD EXP MA pen characteristics
     */
    public ChartPen getMACDExpPen(){
        return otherS.getLinePen();
    }

    /**
     * SetsMACD EXP MA pen characteristics
     */
    public void setMACDExpPen(ChartPen value) {
        if (otherS != null)
            otherS.getLinePen().assign(value);
    }
    
    
    public ChartPen getMACDPen() {
        if (getSeries()!=null && getSeries() instanceof Custom)
        {
            return ((Custom)getSeries()).getLinePen();
        }
        else return null;
    }
    
    public void setMACDPen(ChartPen value) {
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
        return Language.getString("FunctionMACD");
    }
   
}
