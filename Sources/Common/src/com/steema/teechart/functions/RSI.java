package com.steema.teechart.functions;

import com.steema.teechart.languages.Language;
import com.steema.teechart.styles.ValueList;
import com.steema.teechart.IBaseChart;
import com.steema.teechart.styles.Series;
import com.steema.teechart.styles.ISeries;
import com.steema.teechart.styles.OHLC;
import com.steema.teechart.functions.RSIStyle;

/**
 *
 * <p>Title: RSI class</p>
 *
 * <p>Description: Relative Strength Index (RSI) Function.</p>
 *
 * <p>Copyright (c) 2005-2019 by Steema Software SL. All Rights
 * Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 *
 */
public class RSI extends Moving {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private RSIStyle fStyle = RSIStyle.OPENCLOSE;
    private ValueList opens,closes;
    private Series iSeries = null;
 
   
    /** Creates a new instance of RSI */
    public RSI() {
        this((IBaseChart)null);
    }

    public RSI(IBaseChart c) {
        super(c);
        SingleSource = true;
        HideSourceList = true;
    }
    
    public double calculate(Series s, int firstIndex,
                            int lastIndex) {
        double result = 100.0; // Special case, to avoid divide by zero
        if (s instanceof OHLC)
            {
                OHLC series = (OHLC)s;
            
                if (iSeries!=s) {
                    closes = series.getCloseValues();
                    opens = series.getOpenValues();
                    iSeries = series;
                }

                double ups = 0.0;
                double downs = 0.0;
                double tmpClose;

                if (fStyle==RSIStyle.OPENCLOSE) // use today Close and Open prices (do not use "yesterday" close)
                {
                    for (int t=firstIndex; t<=lastIndex; t++)
                    {
                        tmpClose = closes.value[t];
                        if (opens.value[t]>tmpClose) downs += tmpClose;
                        else ups += tmpClose;
                    }
                }
                else // Use Close prices (today - yesterday)
                {
                    for (int t=firstIndex+1; t<=lastIndex; t++)
                    {
                        tmpClose = closes.value[t]-closes.value[t-1];
                        if (tmpClose<0.0) downs -= tmpClose;
                        else  ups += tmpClose;
                    }
                }

                // Calculate RSI
                int numPoints = (lastIndex-firstIndex)+1;
                downs /= (double)numPoints;
                ups /= (double)numPoints;
                if (downs !=0.0)
                {
                    result =100.0-(100.0/(1.0+Math.abs(ups/downs)));
                    if (result < 0.0) result = 0.0;
                    else if (result>100.0) result = 100.0;
                }
        }
        return result;
    }
    
    public RSIStyle getRSIStyle(){
        return fStyle;
    }
    
    public void SetRSIStyle(RSIStyle value){
        if (value != fStyle) {
        fStyle = value;
        recalculate();
        }
    }
    
        /**
     * Gets descriptive text.
     *
     * @return String
     */
    public String getDescription() {
        return Language.getString("FunctionRSI");
    }
    
    public boolean isValidSourceOf(ISeries value) {
        return value instanceof OHLC;
    }   
}
