package com.steema.teechart;

import java.io.Serializable;

public final class Cursor implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static final int CUSTOM_CURSOR = -1;
	
    public static final Cursor DEFAULT = new Cursor(0);
    public static final Cursor HAND = new Cursor(12);
    public static final Cursor CROSS = new Cursor(1);

    public static final Cursor SIZEWE = new Cursor(4);
    public static final Cursor HSPLIT = new Cursor(10);
    public static final Cursor VSPLIT = new Cursor(9);
    public static final Cursor SIZEALL = new Cursor(8);
    public static final Cursor NO = new Cursor(2);
	public static final Cursor E_RESIZE = new Cursor(11);
	public static final Cursor N_RESIZE = new Cursor(8);
	public static final Cursor NE_RESIZE = new Cursor(7);
	public static final Cursor NW_RESIZE = new Cursor(6);
	public static final Cursor S_RESIZE = new Cursor(9);
	public static final Cursor SE_RESIZE = new Cursor(5);
    
    


	private final int cursor;
	
	public Cursor(int i) {
		super();
		cursor=i;
	}
        
        public Cursor() {
		super();
		cursor=0;
	}

	final public int getType() {
		return cursor;
	}
}
