package com.steema.teechart.drawing;

import com.steema.teechart.misc.Enum;

/**
 * <p>Title: EdgeStyle - enum constants</p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright (c) 2005-2019 by Steema Software SL. All Rights Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 *
 */
public class EdgeStyle extends Enum {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
     * Flat edges.
     */
    public static final EdgeStyle FLAT = new EdgeStyle(0);
    /**
     * Curved edges.
     */
    public static final EdgeStyle CURVED = new EdgeStyle(1);
    /**
     * No special edge treament.
     */
    public static final EdgeStyle NONE = new EdgeStyle(2);

    private EdgeStyle(int value) {
        super(value);
    }
    
    static public EdgeStyle fromInt(int value) {
      switch(value) {
       case 0: return FLAT;
       case 1: return CURVED;
       default: return NONE;
      }
    }
}
