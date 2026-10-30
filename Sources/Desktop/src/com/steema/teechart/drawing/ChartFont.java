package com.steema.teechart.drawing;

import java.awt.Font;
import com.steema.teechart.TeeBase;
import com.steema.teechart.Shadow;
import com.steema.teechart.IBaseChart;
import java.awt.font.TextAttribute;
import java.util.HashMap;
import java.util.Map;

/**
 * 
 * <p>
 * Title: ChartFont class
 * </p>
 * 
 * <p>
 * Description: Common Chart Font. Font methods used at several objects.
 * </p>
 * 
 * <p>
 * Copyright (c) 2005-2019 by Steema Software SL. All Rights Reserved.
 * </p>
 * 
 * <p>
 * Company: Steema Software SL
 * </p>
 * 
 */
public final class ChartFont extends TeeBase {
	
	private static final long serialVersionUID = 1L;

	transient public static final int DEFAULTSIZE = 11;
	transient public static final String DEFAULTFAMILY = "Default"; // "Microsoft Sans Serif";
																	// //
																	// Arial";
	private boolean bold;
	private ChartBrush bBrush;
	transient private Font font; // = new Font(DEFAULTFAMILY, Font.PLAIN,
									// DEFAULTSIZE);
	private boolean italic;
	private String name = DEFAULTFAMILY;
	private Shadow shadow;
	private int size = DEFAULTSIZE;
	private boolean strikeout;
	private boolean underline;
	private ChartPen outline;

	public ChartFont(IBaseChart c) {
		super(c);
	}

	/**
	 * Defines a Font type for text.<br>
	 * Default value: Arial
	 * 
	 * @return String
	 */
	public String getName() {
		return name;
	}

	/**
	 * Specifies a Font type for text.<br>
	 * Default value: Arial
	 * 
	 * @param value
	 *            String
	 */
	public void setName(String value) {
		name = setStringProperty(name, value);
	}

	private void changed() {
		if (chart != null) {
			chart.doChangedFont(this);
		}
	}

	/**
	 * Use Invalidate when the entire canvas needs to be repainted.<br>
	 * When more than one region within the canvas needs repainting, Invalidate
	 * will cause the entire window to be repainted in a single pass, avoiding
	 * flicker caused by redundant repaints.<br>
	 * There is no performance penalty for calling Invalidate multiple times
	 * before the control is actually repainted.
	 */
	public void invalidate() {
		super.invalidate();

		if (font != null) {
			// font.dispose();
			font = null;
		}

		changed();
	}

	boolean hasOutline() {
		return (outline != null) && outline.visible;
	}

	/**
	 * The Font size (in points) for text.<br>
	 * When managing Font sizes of Drawing Canvas custom outputted text relative
	 * to Chart text (titles, labels, etc.), use Font.Height to size the Canvas
	 * text. <br>
	 * Default value: 8
	 * 
	 * @return int
	 */
	public int getSize() {
		return size;
	}

	/**
	 * Sets Font sizing (in points) for text.<br>
	 * Default value: 8
	 * 
	 * @param value
	 *            int
	 */
	public void setSize(int value) {
		size = setIntegerProperty(size, value);
	}

	/**
	 * Bold Font for text.<br>
	 * Default value: false
	 * 
	 * @return boolean
	 */
	public boolean getBold() {
		return bold;
	}

	/**
	 * Sets Font bold for text.<br>
	 * Default value: false
	 * 
	 * @param value
	 *            boolean
	 */
	public void setBold(boolean value) {
		bold = setBooleanProperty(bold, value);
	}

	public void reset() {
		setColor(Color.BLACK);
		setBold(false);
		setItalic(false);
		setUnderline(false);
		setName(DEFAULTFAMILY);
		setSize(DEFAULTSIZE);
	}

	/**
	 * Italic Font (true or false) for text.<br>
	 * Default value: false
	 * 
	 * @return boolean
	 */
	public boolean getItalic() {
		return italic;
	}

	/**
	 * Sets Font italic (true or false) for text.<br>
	 * Default value: false
	 * 
	 * @param value
	 *            boolean
	 */
	public void setItalic(boolean value) {
		italic = setBooleanProperty(italic, value);
	}

	/**
	 * Underline Font for text.<br>
	 * Default value: false
	 * 
	 * @return boolean
	 */
	public boolean getUnderline() {
		return underline;
	}

	/**
	 * Sets Font underline on/off.<br>
	 * Default value: false
	 * 
	 * @param value
	 *            boolean
	 */
	public void setUnderline(boolean value) {
		underline = setBooleanProperty(underline, value);
	}

	/**
	 * Font Strikeout on/off.<br>
	 * Default value: false
	 * 
	 * @return boolean
	 */
	public boolean getStrikeout() {
		return strikeout;
	}

	/**
	 * Sets Font Strikeout on/off.<br>
	 * Default value: false
	 * 
	 * @param value
	 *            boolean
	 */
	public void setStrikeout(boolean value) {
		strikeout = setBooleanProperty(strikeout, value);
	}

	/**
	 * Defines a Font colour for text.
	 * 
	 * @return Color
	 */
	// Description("Defines a Font colour for text.")]
	public Color getColor() {
		return getBrush().getColor();
	}

	/**
	 * Defines a Font colour for text.
	 * 
	 * @param value
	 *            Color
	 */
	public void setColor(Color value) {
		getBrush().setColor(value);
	}

	public void setColor(java.awt.Color value) {
		setColor(new Color(value));
	}

	/**
	 * Applies a gradient fill to the font.<br>
	 * 
	 * @return Gradient
	 */
	public Gradient getGradient() {
		return getBrush().getGradient();
	}

	/**
	 * Accesses the shadow properties of the font.
	 * 
	 * @return Shadow
	 */
	public Shadow getShadow() {
		if (shadow == null) {
			shadow = new Shadow(chart, 1);
		}
		return shadow;
	}

	public boolean shouldDrawShadow() {
		return ((shadow != null) && shadow.getVisible() && ((shadow.getWidth() != 0) || (shadow
				.getHeight() != 0)));
	}

	public void assign(ChartFont f) {
		if (f.bBrush == null) {
			bBrush = null;
		} else {
			getBrush().assign(f.bBrush);
		}

		if (f.shadow == null) {
			shadow = null;
		} else {
			getShadow().assign(f.shadow);
		}

		if (f.outline == null) {
			outline = null;
		} else {
			getOutline().assign(f.outline);
		}

		bold = f.bold;
		strikeout = f.strikeout;
		underline = f.underline;
		italic = f.italic;

		name = f.name;
		size = f.size;
		font = null;
		changed();
	}

	public Font getDrawingFont(final double fontZoom) {
		
		final int newSize = (int) (size * fontZoom * 0.01);
		
		if ((font == null) || (font.getSize() != newSize)) {
			int style = Font.PLAIN;

			if (bold) {
				style = Font.BOLD;
			} else {
				style = Font.PLAIN;
			}

			if (italic) {
				style |= Font.ITALIC;
			}

			/** @todo UNDERLINE AND STRIKEOUT DO NOT EXIST IN JAVA FONT */
			if (underline) {
			        Map<TextAttribute, Integer> fontAttributes = new HashMap<TextAttribute, Integer>();
                                fontAttributes.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
                                Font boldUnderline = new Font(name, Font.PLAIN, size).deriveFont(fontAttributes);
                                return boldUnderline;
			}
			// if (strikeout) {
			// style |= Font.Strikeout;
			// }
			/**
			 * @todo CREATE FONT USING MAP OBJECT WITH TEXTATTRIBUTE (Strike,
			 *       Underline, etc)
			 */
			/** @todo INSTEAD OF CREATING IT HERE WITH A SIMPLE "style" value */

			// MM change .. create with name = DEFAULTFAMILY "Default" or null
			// to
			// create default and accept all unicode types
			font = new Font(name, style, newSize);
		}
		
		return font;
	}

	public void setChart(IBaseChart c) {
		super.setChart(c);
		if (shadow != null) {
			shadow.setChart(chart);
		}
		if (bBrush != null) {
			bBrush.setChart(chart);
		}
		if (outline != null) {
			outline.setChart(chart);
		}
	}

	/**
	 * Sets the Brush characteristics of the font.
	 * 
	 * @return ChartBrush
	 */
	public ChartBrush getBrush() {
		if (bBrush == null) {
			bBrush = new ChartBrush(chart, Color.BLACK);
		}
		return bBrush;
	}

	public ChartPen getOutline() {
		if (outline == null) {
			outline = new ChartPen(chart, false);
		}
		return outline;
	}

	public double getDrawingFontSize() {
		return size; // getDrawingFont().getSize();
	}

	public void hideOutline() {
		if (outline != null)
			outline.setVisible(false);
	}

	public void hideShadow() {
		if (shadow != null)
			shadow.setVisible(false);
	}
}
