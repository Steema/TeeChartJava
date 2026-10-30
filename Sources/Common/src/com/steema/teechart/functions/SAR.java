package com.steema.teechart.functions;


import com.steema.teechart.languages.Language;
import java.util.ArrayList;
import com.steema.teechart.IBaseChart;
import com.steema.teechart.styles.ISeries;
import com.steema.teechart.styles.OHLC;

/**
 *
 * <p>Title: SAR class</p>
 *
 * <p>Description: SAR Function.</p>
 *
 * <p>Copyright (c) 2005-2019 by Steema Software SL. All Rights
 * Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 *
 */
public class SAR extends Function {
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private double af = 0.02;
    private double ms = 0.2;
    
    private double min(double x, double y, double z)
    {
        double result;
        if (x<y) result = x;
        else result = y;
        if (z<result) result = z;
        return result;
    }
    
    private double max(double x, double y, double z)
    {
        double result;
        if (x>y) result = x;
        else result = y;

        if (z>result) result = z;
        return result;
    }

    public SAR() {
        this((IBaseChart)null);
    }

    public SAR(IBaseChart c) {
        super(c);
        canUsePeriod = false;
        SingleSource = true;
    }
    
    /**
     * Sets SAR function maximal step.
     * @param value maximal step value.
    */
    public void SetMS(double value)
    {
        if (ms!=value)
        {
            ms = value;
            recalculate();
        }      
    }
    
   /**
   * SAR maximum step.
   *
   * @return max step.
   */
    public double getMS()
    {
        return ms;
    }
    
    /**
     * Sets SAR function acceleration factor.
     * @param value Acceleration factor value.
    */
    public void SetAF(double value)
    {
        if (af!=value)
        {
            af = value;
            recalculate();
        }      
    }
    
  
   /**
   * SAR acceleration factor.
   *
   * @return acceleration factor.
   */
    public double getAF()
    {
        return af;
    }
    
    /**
     * Gets all points from Source series, performs a function operation on
     * points and stores results in ParentSeries.
     *
     * @param source ArrayList
     */
    public void addPoints(ArrayList source) {
        if (!updating) {
            if (source != null) {
                if (source.size() > 0 && source.get(0) instanceof OHLC) {
                    OHLC s = (OHLC) source.get(0);
                    if (s.getCount()>2) {
                        getSeries().clear();
                        double [] psar = new double[s.getCount()];
                        try
                        {
                            psar[0] = s.getCloseValues().value[0];
                            psar[1] = s.getHighValues().value[1];
                            boolean longPos = true;
                            double int_af = af;
                            double reverse;
                            double int_ep = s.getLowValues().value[0];
                            double int_hp = s.getHighValues().value[0];
                            double int_lp = int_ep;
                            for (int i=2; i<s.getCount(); i++)
                            {
                                if (longPos){
                                    psar[i] = psar[i-1]+int_af*(int_hp-psar[i-1]);
                                }
                                else {
                                    psar[i] = psar[i-1]+int_af*(int_lp-psar[i-1]);
                                }
                                // Check for reversal
                                reverse = 0.0;
                                
                                if (longPos) {
                                    if (s.getLowValues().value[i]<psar[i]) {
                                        longPos = false;
                                        reverse = 1.0; // reverse position to short
                                        psar[i] = int_hp; // sar is high point in prev trade
                                        int_lp = s.getLowValues().value[i];
                                        int_af = af;
                                    }
                                }
                                else {
                                    if (s.getHighValues().value[i]>psar[i]) {
                                        longPos = true;
                                        reverse = 1.0;
                                        psar[i] = int_lp;
                                        int_hp = s.getHighValues().value[i];
                                        int_af = af;
                                    }
                                }
                                
                                if (reverse == 0.0) 
                                {
                                    if (longPos) 
                                    {
                                        if (s.getHighValues().value[i]>int_hp)
                                        {
                                            int_hp = s.getHighValues().value[i];
                                            int_af = min(int_af+af,ms,ms);
                                        }
                                        psar[i] = min(psar[i],s.getLowValues().value[i-1],s.getLowValues().value[i-2]);
                                    }
                                    else
                                    {
                                        if (s.getLowValues().value[i]>int_lp)
                                        {
                                            int_lp = s.getLowValues().value[i];
                                            int_af = min(int_af+af,ms,ms);
                                        }
                                        psar[i] = max(psar[i],s.getHighValues().value[i-1],s.getHighValues().value[i-2]);
                                        
                                    }
                                }
                            }
                            
                            for (int i=2; i<s.getCount(); i++)
                                addFunctionXY(s.getYMandatory(),s.getNotMandatory().value[i],psar[i]);
                        }
                        finally
                        {
                            psar = null;
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
        return Language.getString("FunctionSAR");
    }
    
    public boolean isValidSourceOf(ISeries value) {
        return value instanceof OHLC;
    }

}
