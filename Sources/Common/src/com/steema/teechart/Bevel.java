package com.steema.teechart;

import com.steema.teechart.drawing.Color;
import com.steema.teechart.drawing.IGraphics3D;

/**
 * 
 * <p>
 * Title: Bevel class
 * </p>
 * 
 * <p>
 * Description: Displays bevels (frames) around rectangles
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
public class Bevel extends TeeBase {
	
	private static final long serialVersionUID = 1L;

	protected BevelStyle inner = BevelStyle.NONE;
	protected BevelStyle outer = BevelStyle.NONE;
	transient protected BevelStyle defaultOuter;

	private int width = 1;
	private boolean visible = true;

	private Color colorOne = Color.WHITE;
	private Color colorTwo = Color.GRAY;

	public Bevel(IBaseChart c) {
		super(c);
		readResolve();
	}

	protected Object readResolve() {
		defaultOuter = BevelStyle.NONE;
		return this;
	}

	/**
	 * Defines the inner bevel type of the TChart Panel border. <br>
	 * Default value: BevelStyle.None
	 * 
	 * 
	 * @return BevelStyle
	 */
	public BevelStyle getInner() {
		return inner;
	}

	/**
	 * Defines the inner bevel type of the TChart Panel border. <br>
	 * Default value: BevelStyle.None
	 * 
	 * 
	 * @param value
	 *            BevelStyle
	 */
	public void setInner(BevelStyle value) {
		if (inner != value) {
			inner = value;
			invalidate();
		}
	}

	/**
	 * Width of the TeeChart Panel border in pixels. <br>
	 * Default value: 1
	 * 
	 * @return int
	 */
	public int getWidth() {
		return width;
	}

	/**
	 * Sets the width of the TeeChart Panel border in pixels. <br>
	 * Default value: 1
	 * 
	 * @param value
	 *            int
	 */
	public void setWidth(int value) {
		width = setIntegerProperty(width, value);
	}

	/**
	 * Color of left and top sides of bevels. <br>
	 * Used together with ColorTwo to set the colors applied to the Bevel edge
	 * when Raised or Lowered. <br>
	 * Default value: white
	 * 
	 * @return Color
	 */
	public Color getColorOne() {
		return colorOne;
	}

	/**
	 * Sets the Color of left and top sides of bevels. <br>
	 * Default value: white
	 * 
	 * @param value
	 *            Color
	 */
	public void setColorOne(Color value) {
		colorOne = setColorProperty(colorOne, value);
	}

	/**
	 * Color of right and bottom sides of bevels.<br>
	 * Used together with ColorOne, these properties set the colors applied to
	 * the Bevel edge when Raised or Lowered. <br>
	 * Default value: gray
	 * 
	 * @return Color
	 */
	public Color getColorTwo() {
		return colorTwo;
	}

	/**
	 * Sets the Color of right and bottom sides of bevels.<br>
	 * Default value: gray
	 * 
	 * @param value
	 *            Color
	 */
	public void setColorTwo(Color value) {
		colorTwo = setColorProperty(colorTwo, value);
	}

	/**
	 * The outer bevel type of the TChart Panel border. <br>
	 * 
	 * 
	 * @return BevelStyle
	 */
	public BevelStyle getOuter() {
		return outer;
	}

	/**
	 * Defines the outer bevel type of the TChart Panel border. <br>
	 * 
	 * 
	 * @param value
	 *            BevelStyle
	 */
	public void setOuter(BevelStyle value) {
		if (outer != value) {
			outer = value;
			invalidate();
		}
	}

	/**
	 * Assigns all properties from one bevel to another.
	 * 
	 * @param b
	 *            Bevel
	 */
	public void assign(Bevel b) {
		if (b != null) {
			colorOne = b.colorOne;
			colorTwo = b.colorTwo;
			inner = b.inner;
			outer = b.outer;
			width = b.width;
			visible = b.visible;
		}
	}

	/**
	 * Draws bevels around rectangle parameter.
	 * 
	 * @param g
	 *            IGraphics3D
	 * @param rect
	 *            Rectangle
	 */
	public void draw(IGraphics3D g, final Rectangle rect) {

		if (visible) {
			final Rectangle r = rect.copy();

			if (inner != BevelStyle.NONE) {
				g.paintBevel(inner, r, width, colorOne, colorTwo);
				r.grow(-width, -width);
			}

			if (outer != BevelStyle.NONE) {
				g.paintBevel(outer, r, width, colorOne, colorTwo);
				// r.grow(-width,-width);
			}
		}
	}

	public boolean getVisible() {
		return visible;
	}

	public void setVisible(boolean value) {
		visible = setBooleanProperty(visible, value);
	}
}
