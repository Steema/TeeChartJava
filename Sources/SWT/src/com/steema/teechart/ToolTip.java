package com.steema.teechart;

import com.steema.teechart.drawing.ChartFont;
import com.steema.teechart.styles.SeriesMarks;
import com.steema.teechart.swt.GDIFontObject;

import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Device;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.widgets.*;
import org.eclipse.swt.SWT;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2006-2019</p>
 *
 * <p>Company: </p>
 *
 * @author not attributable
 * @version 1.0
 */
public class ToolTip extends org.eclipse.jface.window.ToolTip {

	private Chart chart;
	private boolean active=false;
	private int initialDelay=100;
	private int hideDelay=1250;
	private String text="";
	private SeriesMarks marks;
	
	private Color markBackColor;
	private Color backColor;
	private Color fontColor;
	private GDIFontObject fontObj;
	private Composite comp;
	private org.eclipse.swt.custom.StyledText styledText;

	public ToolTip(Chart chart) {
		super((Composite)chart.getParent());
		this.chart = chart;

		super.setPopupDelay(initialDelay);
		super.setHideDelay(hideDelay);
		super.setShift(new org.eclipse.swt.graphics.Point(10, 5));
		
	    fontColor= new Color(null,0,0,0);
		markBackColor= new Color(null,255,255,174);
		backColor = color(java.awt.Color.WHITE);
	}

	private Device device;

	private Color color(java.awt.Color value) {
		return new Color(device, value.getRed(), value.getGreen(),
				value.getBlue());
	}
	
	protected GDIFontObject prepareFont(ChartFont f) {

	  int style = 0;
    
      if (f.getBold())
    	style = style | SWT.BOLD;

      if (f.getItalic())
    	style = style | SWT.ITALIC;
      
	  if (fontObj != null)
	  {
	    if (fontObj.getColor() != null) fontObj.getColor().dispose();
	    if (fontObj.getBackColor() != null) fontObj.getBackColor().dispose();
	    if (fontObj.getFont() != null) fontObj.getFont().dispose();
	    fontObj = null;
	  }
	  
	  fontColor = color(marks.getFont().getColor());
	  markBackColor = color(marks.getBackColor());

      fontObj = new GDIFontObject(fontColor,markBackColor,
    		        new Font(null, f.getName(), f.getSize()-2, style));
	 
      return fontObj;
    }

	protected Composite createToolTipContentArea(Event event, Composite parent) {
	
		if (comp != null) comp.dispose();
		comp = new Composite(parent,SWT.NULL);
		comp.setBackground(backColor);
		comp.setSize(0, 0);
		comp.setBounds(0, 0, 0, 0);
		comp.setLayout(new org.eclipse.swt.layout.FillLayout());
		if ((text==null) || (text==""))
			return comp;
		
		if (styledText != null) styledText.dispose();
		styledText = new org.eclipse.swt.custom.StyledText(comp, SWT.NONE);

		text = text.trim();
		
		if (marks!=null)
        {
			prepareFont(marks.getFont());
			styledText.setFont(fontObj.getFont());
			styledText.setIndent(2);
			styledText.setText(text);
			if (fontObj.getBackColor() != null)
			  styledText.setBackground(fontObj.getBackColor());
			if (fontObj.getColor() != null)
			  styledText.setForeground(fontObj.getColor());
        }

		return comp;
	}
	
	public void prepareMarks(SeriesMarks marks)
	{
		this.marks = marks;
//		if (fontColor != null) fontColor.dispose();
//		fontColor = color(marks.getFont().getColor());
//		if (markBackColor != null) markBackColor.dispose();
//		markBackColor = color(marks.getBackColor());
	}

	public void setActive(boolean value) 
	{
		if (value)
			this.activate();
		else
			this.deactivate();
		active=value; 
	}

	public void setText(String text) 
	{ 
		this.text=text;
		createToolTipContentArea(new Event(),(Composite)chart.getParent());
	}

	public void hide() { setActive(false); }
	public void show() { setActive(true); }
	public String getText() { return text; }

	public int getInitialDelay() {
		return initialDelay; 
	}

	public void setInitialDelay(int value) 
	{
		super.setPopupDelay(value);
		initialDelay=value; 
	}

	public int getDismissDelay() 
	{ 
		return hideDelay; 
	}

	public void setDismissDelay(int value) 
	{      
		super.setHideDelay(value);
		hideDelay=value;
	}
}