package com.steema.teechart.styles;

import com.steema.teechart.IBaseChart;

public class ErrorPoint3D extends CustomErrorPoint3D
{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ErrorPoint3D() 
    {
	this(null);
    }

    public ErrorPoint3D(IBaseChart c)    
    {
	super(c);
    }

    public void prepareForGallery(boolean IsEnabled)
    {
      getPointer().setHorizSize(2);
      getPointer().setVertSize(2);
      super.prepareForGallery(IsEnabled);
    }
 }
