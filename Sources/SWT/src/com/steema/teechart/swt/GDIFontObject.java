package com.steema.teechart.swt;

/**
 * 
 * <p>Title: GDIFontObject class</p>
 * 
 * <p>Description: Supports GDI cleanup of SWT font resources.</p>
 * 
 * <p>Copyright (c) 2013 by Steema Software. All Rights Reserved.</p>
 * 
 * <p>Company: Steema Software</p>
 * 
 */

import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Color;

public class GDIFontObject {
	
	private Color iColor;
	private Color iBackColor;
	private Font iFont;
	
	public GDIFontObject(Color c, Font f)
	{
	  iColor = c;
	  iFont = f;
	}
	
	public GDIFontObject(Color c, Color backColor, Font f)
	{
		this(c,f);
		iBackColor = backColor;
	}
	
	public Font getFont()
	{
	  return iFont;
	}
	
	public Color getColor()
	{
      return iColor;
	}
	
	public Color getBackColor()
	{
      return iBackColor;
	}

}
