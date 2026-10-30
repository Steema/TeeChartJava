/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.steema.teechart.styles;

import com.steema.teechart.IBaseChart;
import com.steema.teechart.languages.Language;

/**
 *
 * @author Dev
 */
public class HorizBeeSwarm extends CustomBeeSwarm {
    

    public HorizBeeSwarm(IBaseChart c) {
        super(c);
        setHorizontal();
        IVertical = false;
    }
    
    public HorizBeeSwarm() {
        super();
        setHorizontal();
        IVertical = false;
    }

     /**
     * Gets descriptive text.
     *
     * @return String
     */
    public String getDescription() {
        return Language.getString("HorizBeeSwarm");
    }
}


