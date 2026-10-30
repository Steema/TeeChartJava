package com.steema.teechart.styles;

import com.steema.teechart.misc.Enum;

/**
 *
 * @author Steema Software
 */
final public class ContourConstruction extends Enum {

  /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

private static int upperBound=0;
  
  /**
   * Level is not segmented (fast)
   */
  public static final ContourConstruction FAST = new ContourConstruction(0);
  
  /**
   * Each level is segmented (slow)
   */
  public static final ContourConstruction SEGMENTS = new ContourConstruction(1);
    
  private ContourConstruction(int value) {
        super(value);
        upperBound++;
    }

    public static int size() { return upperBound; }

    static public ContourConstruction fromInt(int value) {
        switch(value) {
         case 0: return FAST;
         default: return SEGMENTS;
        }
    }
}
