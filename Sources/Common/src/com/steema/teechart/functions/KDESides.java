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
final public class KDESides extends com.steema.teechart.misc.Enum {

    public static final KDESides LEFT = new KDESides(0);
    
    public static final KDESides RIGHT = new KDESides(1);
    
    public static final KDESides BOTH = new KDESides(2);
    
    private KDESides(int value) {
        super(value);
    }

    static public KDESides fromInt(int value) {
        switch (value) {
        case 0:
            return LEFT;
        case 1:
            return RIGHT;
        case 2:
            return BOTH;
        default:
            return BOTH;
        }  
    }
}
