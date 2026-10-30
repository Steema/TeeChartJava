package com.steema.teechart.styles;

import com.steema.teechart.IBaseChart;

/**
 *
 * <p>Title: Custom2DPolar class</p>
 *
 * <p>Description: Custom2DPolar is the ancestor of the WindRose Series and
 * the Clock Series.</p>
 *
 * <p>Copyright (c) 2005-2019 by Steema Software SL. All Rights
 * Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 *
 */
public class Custom2DPolar extends CustomPolar {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Custom2DPolar() {
        this((IBaseChart)null);
    }

    public Custom2DPolar(IBaseChart c) {
        super(c);
    }

    public void galleryChanged3D(boolean is3D) {
        super.galleryChanged3D(is3D);
        getChart().getAspect().setView3D(false);
    }

    public void prepareForGallery(boolean isEnabled) {
        super.prepareForGallery(isEnabled);
        getCircleLabelsFont().setSize(6);
        getPointer().setHorizSize(2);
        getPointer().setVertSize(2);
    }
}

