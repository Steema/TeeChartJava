package com.steema.teechart.printer;

import com.steema.teechart.misc.Enum;

/**
 * <p>Title: PreviewZone class</p>
 *
 * <p>Description: Preview Zones</p>
 *
 * <p>Copyright (c) 2004-2019 by Steema Software SL. All Rights
 * Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 */

final public class PreviewZone extends Enum {

/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/*
          public static final int	DEFAULT_CURSOR   		= 0;
        public static final int	CROSSHAIR_CURSOR 		= 1;
        public static final int	TEXT_CURSOR 	 		= 2;
        public static final int	WAIT_CURSOR	 		= 3;
        public static final int	SW_RESIZE_CURSOR	 	= 4;
        public static final int	SE_RESIZE_CURSOR	 	= 5;
        public static final int	NW_RESIZE_CURSOR		= 6;
        public static final int	NE_RESIZE_CURSOR	 	= 7;
        public static final int	N_RESIZE_CURSOR 		= 8;
        public static final int	S_RESIZE_CURSOR 		= 9;
        public static final int	W_RESIZE_CURSOR	 		= 10;
        public static final int	E_RESIZE_CURSOR			= 11;
        public static final int	HAND_CURSOR			= 12;
        public static final int	MOVE_CURSOR			= 13;

     */
    public static final PreviewZone NONE = new PreviewZone(0);
    public static final PreviewZone LEFT = new PreviewZone(10);
    public static final PreviewZone TOP = new PreviewZone(8);
    public static final PreviewZone RIGHT = new PreviewZone(11);
    public static final PreviewZone BOTTOM = new PreviewZone(9);
    //public static final PreviewZone IMAGE = new PreviewZone(5);
    public static final PreviewZone LEFTTOP = new PreviewZone(6);
    public static final PreviewZone RIGHTTOP = new PreviewZone(7);
    public static final PreviewZone LEFTBOTTOM = new PreviewZone(4);
    public static final PreviewZone RIGHTBOTTOM = new PreviewZone(5);

    /*public static final PreviewZone NONE = new PreviewZone(0);
    public static final PreviewZone LEFT = new PreviewZone(1);
    public static final PreviewZone TOP = new PreviewZone(2);
    public static final PreviewZone RIGHT = new PreviewZone(3);
    public static final PreviewZone BOTTOM = new PreviewZone(4);
    public static final PreviewZone IMAGE = new PreviewZone(5);
    public static final PreviewZone LEFTTOP = new PreviewZone(6);
    public static final PreviewZone RIGHTTOP = new PreviewZone(7);
    public static final PreviewZone LEFTBOTTOM = new PreviewZone(8);
    public static final PreviewZone RIGHTBOTTOM = new PreviewZone(9);*/

    private PreviewZone(int value) {
        super(value);
    }
}

