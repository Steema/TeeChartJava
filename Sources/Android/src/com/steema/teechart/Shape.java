package com.steema.teechart;

import com.steema.teechart.drawing.Color;
import com.steema.teechart.drawing.ChartPen;
import com.steema.teechart.drawing.ChartBrush;
import com.steema.teechart.drawing.Gradient;
import com.steema.teechart.drawing.IGraphics3D;
import com.steema.teechart.drawing.Image;

/**
 * 
 * <p>
 * Title: Shape class
 * </p>
 * 
 * <p>
 * Description: Base class for Chart Shape elements
 * </p>
 * 
 * <p>
 * Example:
 * 
 * <pre>
 * <font face="Courier" size="4">
 *           //shape1
 * tmpSeries = new Shape(myChart.getChart());
 * tmpSeries.getMarks().setVisible(false);
 * tmpSeries.setColor(Color.WHITE);
 * tmpSeries.getBrush().setColor(Color.WHITE);
 * tmpSeries.getFont().setColor(Color.NAVY);
 * tmpSeries.getFont().getShadow().setHorizSize(1);
 * tmpSeries.getFont().getShadow().setVertSize(1);
 * tmpSeries.getGradient().setDirection(GradientDirection.HORIZONTAL);
 * tmpSeries.getGradient().setMiddleColor(Color.GREEN_YELLOW);
 * tmpSeries.getGradient().setUseMiddle(true);
 * tmpSeries.getGradient().setVisible(true);
 * tmpSeries.setStyle(ShapeStyle.CIRCLE);
 * tmpSeries.setText(new String[] {"This is an ellipse"});
 * tmpSeries.setX0(60);
 * tmpSeries.setX1(100);
 * tmpSeries.setY1(100);
 *           //shape2
 * tmpSeries = new Shape(myChart.getChart());
 * tmpSeries.getMarks().setVisible(false);
 * tmpSeries.setColor(Color.PURPLE);
 * tmpSeries.getBrush().setColor(Color.PURPLE);
 * tmpSeries.setText(new String[] {"This is a round rectangle."});
 * tmpSeries.setStyle(ShapeStyle.RECTANGLE);
 * tmpSeries.getFormat().setShapeStyle(TextShapeStyle.ROUNDRECTANGLE);
 * tmpSeries.setX0(12.5);
 * tmpSeries.setX1(47.5);
 * tmpSeries.setY0(140);
 * tmpSeries.setY1(80);
 * </font>
 * </pre>
 * 
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
public class Shape extends TeeBase {
	
	private static final long serialVersionUID = 1L;

	transient protected boolean defaultVisible;
	protected boolean visible = true;
	protected boolean bTransparent;
	protected ChartPen pPen;
	protected Bevel bBevel;
	protected Shadow shadow;
	protected ImageBevel bImageBevel;
	protected ChartBrush bBrush;
	protected Rectangle shapeBounds = new Rectangle();
	protected int bBorderRound;

	public Shape() {
		this((IBaseChart) null);
	}

	public Shape(IBaseChart c) {
		super(c);
		defaultVisible = true;
	}

	/**
	 * Defines the boundaries of the Shape.
	 * 
	 * @return Rectangle
	 */
	public Rectangle getShapeBounds() {
		return shapeBounds;
	}

	/**
	 * Defines the boundaries of the Shape.
	 * 
	 * @param value
	 *            Rectangle
	 */
	public void setShapeBounds(final Rectangle value) {
		shapeBounds.x = value.x;
		shapeBounds.y = value.y;
		shapeBounds.width = value.width;
		shapeBounds.height = value.height;
	}

	/**
	 * Obsolete.&ndsp;Please use Bevel.<!-- -->Inner.
	 * 
	 * 
	 * @return BevelStyle
	 */
	public BevelStyle getBevelInner() {
		return getBevel().getInner();
	}

	/**
	 * Defines the inner bevel type of the TChart Panel border.<br>
	 * Available types:<br>
	 * None<br>
	 * Lowered<br>
	 * Raised<br>
	 * 
	 * 
	 * @param value
	 *            BevelStyle
	 */
	public void setBevelInner(final BevelStyle value) {
		getBevel().setInner(value);
	}

	/**
	 * Obsolete.&nbsp;Please use Bevel.<!-- -->Width
	 * 
	 * @return int
	 */
	public int getBevelWidth() {
		return getBevel().getWidth();
	}

	/**
	 * Obsolete.&nbsp;Please use Bevel.<!-- -->Width
	 * 
	 * @param value
	 *            int
	 */
	public void setBevelWidth(int value) {
		getBevel().setWidth(value);
	}

	/**
	 * Obsolete.&nbsp;Please use Bevel.<!-- -->Outer
	 * 
	 * 
	 * @return BevelStyle
	 */
	public BevelStyle getBevelOuter() {
		return getBevel().getOuter();
	}

	/**
	 * Defines the outer bevel type of the TChart Panel border.<br>
	 * Available types:<br>
	 * None<br>
	 * Lowered<br>
	 * Raised<br>
	 * 
	 * 
	 * @param value
	 *            BevelStyle
	 */
	public void setBevelOuter(final BevelStyle value) {
		getBevel().setOuter(value);
	}

	/**
	 * Chart associated with this object.
	 * 
	 * @param c
	 *            IBaseChart
	 */
	public void setChart(IBaseChart c) {
		super.setChart(c);

		if (pPen != null) {
			pPen.setChart(chart);
		}
		if (shadow != null) {
			shadow.setChart(chart);
		}
		if (bBrush != null) {
			bBrush.setChart(chart);
		}
		if (bBevel != null) {
			bBevel.setChart(chart);
		}
	}

	/**
	 * Sets the bevel characteristics of the Shape.
	 * 
	 * @return ImageBevel
	 */

	public ImageBevel getImageBevel() {
		if (bImageBevel == null)
			bImageBevel = new ImageBevel(chart);
		return bImageBevel;
	}

	/**
	 * Sets the bevel characteristics of the Shape.
	 * 
	 * @return Bevel
	 */
	public Bevel getBevel() {
		if (bBevel == null) {
			bBevel = new Bevel(chart);
		}
		return bBevel;
	}

	/**
	 * Internal use - serialization
	 */
	public void setBevel(Bevel value) {
		bBevel = value;
	}

	/**
	 * Defines the shape's Shadow characteristics.
	 * 
	 * @return Shadow
	 */
	public Shadow getShadow() {
		if (shadow == null) {
			shadow = new Shadow(chart);
		}
		return shadow;
	}

	/**
	 * Defines the kind of brush used to fill shape background.
	 * 
	 * @return ChartBrush
	 */
	public ChartBrush getBrush() {
		if (bBrush == null) {
			bBrush = new ChartBrush(chart);
		}
		return bBrush;
	}

	/**
	 * Internal use - serialization
	 */
	public void setBrush(final ChartBrush value) {
		bBrush = value;
	}

	/**
	 * Rounds the Borders of the Chart Shapes.
	 * 
	 * @return int
	 */
	public int getBorderRound() {
		return bBorderRound;
	}

	/**
	 * Rounds the Borders of the Chart Shapes.
	 * 
	 * @param value
	 *            int
	 */
	public void setBorderRound(int value) {
		bBorderRound = setIntegerProperty(bBorderRound, value);
	}

	/**
	 * Rendered Image for Shape background.<br>
	 * Default value: null
	 * 
	 * @return Image
	 */
	public Image getImage() {
		return getBrush().getImage();
	}

	public void setDefaultVisible(boolean value) {
		defaultVisible = value;
		visible = value;
	}

	/**
	 * Sets Rendered Image for Shape background.<br>
	 * Default value: null
	 * 
	 * @param value
	 *            Image
	 */
	public void setImage(final Image value) {
		getBrush().setImage(value);
	}

	/**
	 * ImageMode for rendered Shape background Image.<br>
	 * Default value: ImageMode.Stretch
	 * 
	 * @return ImageMode
	 */
	public ImageMode getImageMode() {
		return getBrush().getImageMode();
	}

	/**
	 * Sets the ImageMode for rendered Shape background Image.<br>
	 * Default value: ImageMode.Stretch
	 * 
	 * @param value
	 *            ImageMode
	 */
	public void setImageMode(final ImageMode value) {
		getBrush().setImageMode(value);
	}

	// CDI TTrack //#1480 -- added new property ... have to set Shape.Color to
	// Color.Transparent for it to work
	/**
	 * Sets the shape image to transparent.
	 * 
	 * @return boolean
	 */
	public boolean getImageTransparent() {
		return getBrush().getImageTransparent();
	}

	/**
	 * Sets the shape image to transparent.
	 * 
	 * @param value
	 */
	public void setImageTransparent(boolean value) {
		getBrush().setImageTransparent(value);
		// if(value)Color = Color.Transparent; //CDI TTrack //#1480 - taken out
		// pending
	}

	/**
	 * Defines the shape Color.
	 * 
	 * @return Color
	 */
	public Color getColor() {
		return getBrush().getColor();
	}

	/**
	 * Defines the shape Color.
	 * 
	 * @param value
	 *            Color
	 */
	public void setColor(final Color value) {
		getBrush().setColor(value);
	}

	/**
	 * Calls the Gradient characteristics for the shape.
	 * 
	 * @return Gradient
	 */
	public Gradient getGradient() {
		return getBrush().getGradient();
	}

	/**
	 * Shows or hides the Shape.
	 * 
	 * @return boolean
	 */
	public boolean getVisible() {
		return visible;
	}

	/**
	 * Shows or hides the Shape.
	 * 
	 * @param value
	 *            boolean
	 */
	public void setVisible(boolean value) {
		visible = setBooleanProperty(visible, value);
	}

	/**
	 * Enables/disables transparency of shape.<br>
	 * See transparency, which sets percentage transparency when Transparent is
	 * true.
	 * 
	 * @see Shape#getTransparency
	 * @return boolean
	 */
	public boolean getTransparent() {
		return bTransparent;
	}

	/**
	 * Enables/disables transparency of shape.<br>
	 * 
	 * @param value
	 *            boolean
	 */
	public void setTransparent(boolean value) {
		bTransparent = setBooleanProperty(bTransparent, value);
	}

	/**
	 * Specifies the pen used to draw the shape.
	 * 
	 * @return ChartPen
	 */
	public ChartPen getPen() {
		if (pPen == null) {
			pPen = new ChartPen(chart, Color.BLACK);
		}
		return pPen;
	}

	/**
	 * Internal use - serialization
	 */
	public void setPen(final ChartPen value) {
		pPen = value;
	}

	/**
	 * Shape rectangle left co-ordinate.
	 * 
	 * @return int
	 */
	public int getLeft() {
		return shapeBounds.x;
	}

	/**
	 * Shape rectangle left co-ordinate.
	 * 
	 */
	public void setLeft(int value) {
		shapeBounds.x = setIntegerProperty(shapeBounds.x, value);
	}

	/**
	 * Shape rectangle top co-ordinate.
	 * 
	 * @return int
	 */
	public int getTop() {
		return shapeBounds.y;
	}

	/**
	 * Shape rectangle top co-ordinate.
	 * 
	 * @param value
	 *            int
	 */
	public void setTop(int value) {
		shapeBounds.y = setIntegerProperty(shapeBounds.y, value);
	}

	/**
	 * Shape rectangle right co-ordinate.
	 * 
	 * @return int
	 */
	public int getRight() {
		return shapeBounds.getRight();
	}

	/**
	 * Shape rectangle right co-ordinate.
	 * 
	 * @param value
	 *            int
	 */
	public void setRight(int value) {
		if (shapeBounds.getRight() != value) {
			shapeBounds.setRight(value);
			invalidate();
		}
	}

	/**
	 * Shape rectangle bottom co-ordinate.
	 * 
	 * @return int
	 */
	public int getBottom() {
		return shapeBounds.getBottom();
	}

	/**
	 * Shape rectangle bottom co-ordinate.
	 * 
	 * @param value
	 *            int
	 */
	public void setBottom(int value) {
		if (shapeBounds.getBottom() != value) {
			shapeBounds.setBottom(value);
			invalidate();
		}
	}

	/**
	 * The Height of the Shape.
	 * 
	 * @return int
	 */
	public int getHeight() {
		return shapeBounds.height;
	}

	/**
	 * Sets the Height of the Shape.
	 * 
	 * @param value
	 */
	public void setHeight(int value) {
		shapeBounds.height = value;
	}

	/**
	 * The Width of the shape.
	 * 
	 * @return int
	 */
	public int getWidth() {
		return shapeBounds.width;
	}

	/**
	 * Sets the Width of the shape.
	 * 
	 * @param value
	 */
	public void setWidth(int value) {
		shapeBounds.width = value;
	}

	/**
	 * Assign all properties from a shape to another.
	 * 
	 * @param s
	 *            Shape
	 */
	public void assign(final Shape s) {
		if (s != null) {
			if (s.bBevel != null) {
				bBevel.assign(s.bBevel);
			}
			if (s.bBrush != null) {
				bBrush.assign(s.bBrush);
			}

			setShapeBounds(s.shapeBounds);

			if (s.pPen != null) {
				pPen.assign(s.pPen, s.pPen.getColor());
			}
			if (s.shadow != null) {
				shadow.assign(s.shadow);
			}

			setVisible(s.getVisible());
			setTransparent(s.getTransparent());
		}
	}

	public void paint(IGraphics3D g, final Rectangle rect) {

		if (!bTransparent) {

			if ((shadow != null) && shadow.getVisible())
				shadow.draw(g, rect);

			g.setBrush(getBrush());
			g.setPen(getPen());

			if (bBorderRound > 0)
				g.roundRectangle(rect, bBorderRound, bBorderRound);
			else {
				g.rectangle(rect);

				if (bBevel != null)
					bBevel.draw(g, rect);
			}
		}
	}

	/**
	 * The Transparency level from 0 to 100% of shape.<br>
	 * Transparency is a value between 0 and 100 which sets the transparency
	 * percentage with respect to foreground versus background.<br>
	 * Default value: 0
	 * 
	 * @return int
	 */
	public int getTransparency() {
		return getBrush().getTransparency();
	}

	/**
	 * Sets Transparency level from 0 to 100% of shape.<br>
	 * Default value: 0
	 * 
	 * @param value
	 *            int
	 */
	public void setTransparency(int value) {
		getBrush().setTransparency(value);
	}
}
