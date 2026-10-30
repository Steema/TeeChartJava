package com.steema.teechart.axis;

import com.steema.teechart.IBaseChart;

/**
 *
 * <p>Title: DepthAxis class</p>
 *
 * <p>Description: Z plane Axis characteristics.</p>
 *
 * <p>Copyright (c) 2005-2019 by Steema Software SL. All Rights
 * Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 *
 */
public class DepthAxis extends Axis {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private boolean labelsAsSeriesTitles = true;
    
    public DepthAxis(boolean horiz, boolean isOtherSide, IBaseChart c) {
        super(horiz, isOtherSide, c);
        isDepthAxis = true;
        bVisible = false;
    }
    
    
    public boolean getLabelsAsSeriesTitles() {
       return labelsAsSeriesTitles; 
    }
    
    public void setLabelsAsSeriesTitles(boolean value) {
       labelsAsSeriesTitles = setBooleanProperty(labelsAsSeriesTitles, value);
    }  
}

