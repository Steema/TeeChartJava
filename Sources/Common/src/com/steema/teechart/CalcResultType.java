package com.steema.teechart;

/**
 * <p>Title: CalcResultType class</p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright (c) 2005-2019 by Steema Software SL. All Rights Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 *
 */
public class CalcResultType {
  
    boolean firstTime;
    double result;
      
    public CalcResultType() {
      super();
    }

    public CalcResultType(double result, boolean firstTime) {
        this.firstTime = firstTime;
        this.result = result;
    }
}

