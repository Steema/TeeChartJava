package com.steema.teechart.styles;

import com.steema.teechart.IBaseChart;

public class ErrorPoint extends CustomErrorPoint
{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ErrorPoint() {
	this(null);
    }

    public ErrorPoint(IBaseChart c) { super(c);}

    @Override
    public void prepareForGallery(boolean IsEnabled)
    {
      getPointer().setHorizSize(2);
      getPointer().setVertSize(2);
      super.prepareForGallery(IsEnabled);
    }
}
