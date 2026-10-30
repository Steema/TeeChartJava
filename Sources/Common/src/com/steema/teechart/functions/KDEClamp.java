/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.steema.teechart.functions;

/**
 *
 * @author Dev
 */
final public class KDEClamp extends com.steema.teechart.misc.Enum {

    public static final KDEClamp MINMAX = new KDEClamp(0);
    
    public static final KDEClamp MANUAL = new KDEClamp(1);
    
    private KDEClamp(int value) {
        super(value);
    }

    static public KDEClamp fromInt(int value) {
        switch (value) {
        case 0:
            return MINMAX;
        case 1:
            return MANUAL;
        default:
            return MINMAX;
        }  
    }
}
