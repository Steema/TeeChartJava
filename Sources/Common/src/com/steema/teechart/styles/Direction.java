package com.steema.teechart.styles;

final public class Direction  extends com.steema.teechart.misc.Enum {
        
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static final Direction HORIZ = new Direction(0);
        
    public static final Direction VERT = new Direction(1);
        
    public static final Direction DEPTH = new Direction(2);
        
    private Direction(int value) {
       super(value);
    }
}