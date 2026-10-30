package com.steema.teechart;

import com.steema.teechart.drawing.IGraphics3D;

/**
 *
 * <p>Title: Wall class</p>
 *
 * <p>Description: Characteristics of Wall Panel that complements an Axis</p>
 *
 * <p>Copyright (c) 2005-2019 by Steema Software SL. All Rights Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 *
 */
public class Wall extends Shape {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected int iSize;
    protected boolean bApplyDark = true;
	private boolean autoHide;

    protected boolean getShouldDark() {
        return bApplyDark && (bBrush != null) && getBrush().getVisible();
    }

    /**
     * Applies a darker shade to 3D Chart Walls when true.<br>
     * Default value: true
     *
     * @return boolean
     */
    public boolean getApplyDark() {
        return bApplyDark;
    }

    /**
     * Applies a darker shade to 3D Chart Walls when true.<br>
     * Default value: true
     *
     * @param value boolean
     */
    public void setApplyDark(boolean value) {
        bApplyDark = setBooleanProperty(bApplyDark, value);
    }

    public Wall(IBaseChart c) {
        super(c);
    }

    /**
     * The Chart Wall thickness.<br>
     * Default value: 0
     *
     * @return int
     */
    public int getSize() {
        return iSize;
    }

    /**
     * Sets the Chart Wall thickness.<br>
     * Default value: 0
     *
     * @param value int
     */
    public void setSize(int value) {
        iSize = setIntegerProperty(iSize, value);
    }

    protected void prepareGraphics(IGraphics3D g) {
        if (bTransparent) {
            g.getBrush().setVisible(false);
        } else {
            g.setBrush(getBrush());
            //if (transparency>0) g.SetTransparency(transparency);
        }
        g.setPen(getPen());
    }

    protected boolean shouldDraw() {
    	return chart.getWalls().getView3D() && visible && (! autoHide);
    }

	public void setAutoHide(boolean value) {
		autoHide = setBooleanProperty(autoHide, value);
	}

	public boolean getAutoHide() {
		return autoHide;
	}
}

