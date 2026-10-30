/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.steema.teechart.tools;
import com.steema.teechart.misc.Enum;

/**
 *
 * <p>Title: NearestPointDirection</p>
 *
 * <p>Description: Describes the possible values of the NeareastPoint.Direction
 * method.</p>
 *
 * <p>Copyright (c) 2005-2019 by Steema Software SL. All Rights Reserved.</p>
 *
 * <p>Company: Steema Software</p>
 *
 *
 * @see com.steema.teechart.tools.NeareastPoint#getDirection
 */
public class NearestPointDirection extends Enum { 
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
    * Nearest horizontal point.
    */
    public static final NearestPointDirection HORIZONTAL = new NearestPointDirection(0);
    /**
    * Nearest vertical point.
    */
    public static final NearestPointDirection VERTICAL = new NearestPointDirection(1);   
    /**
    * Nearest point both horizontally and vertically.
    */
    public static final NearestPointDirection BOTH = new NearestPointDirection(2);
    
    private NearestPointDirection(int value) {
        super(value);
    }   
}
