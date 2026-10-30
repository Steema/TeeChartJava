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
public class BeeSwarm extends CustomBeeSwarm {
    private static final long serialVersionUID = 1L;
    private ValueList offsetValues;

    public BeeSwarm(IBaseChart c) {
        super(c);
        marks.getTailParams().setAlign(TailAlignment.Auto);
        offsetValues = new ValueList(this, Language.getString("ValuesOffset"));
    }
    
    public BeeSwarm() {
        super();
    }
   

    /**
     * Gets descriptive text.
     *
     * @return String
     */
    public String getDescription() {
        return Language.getString("BeeSwarm");
    }
    
}
