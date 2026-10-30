package com.steema.teechart;

import com.steema.teechart.drawing.ChartBrush;
import com.steema.teechart.drawing.Color;
import com.steema.teechart.drawing.Gradient;
import com.steema.teechart.drawing.IGraphics3D;
import com.steema.teechart.axis.Axis;

/**
 * 
 * <p>
 * Title: Walls class
 * </p>
 * 
 * <p>
 * Description: Chart Walls. Accesses Wall and overall Wall display
 * characteristics
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
public class Walls extends TeeBase {
	
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 * 
	 * <p>
	 * Title: LeftWall class
	 * </p>
	 * 
	 * <p>
	 * Description: Wall Panel at left of Chart
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
	public class LeftWall extends Wall {

		public LeftWall(IBaseChart c, final Walls w) {
			super(c);
			getBrush().setDefaultColor(Color.LIGHT_YELLOW);
		}

		/**
		 * Use this method to paint a left wall at rectangle r.
		 * 
		 * @param g
		 *            IGraphics3D
		 * @param rect
		 *            Rectangle
		 */
		public void paint(IGraphics3D g, final Rectangle rect) {
			prepareGraphics(g);
			int tmpB = rect.getBottom()
					+ calcWallSize(chart.getAxes().getBottom());
			int w = chart.getAspect().getWidth3D();

			if (iSize > 0) {
				g.cube(rect.x - iSize, rect.y, rect.x, tmpB, 0, w
						+ getBack().getSize(), getShouldDark());
			} else {
				g.rectangleZ(rect.x, rect.y, tmpB, 0, w);
			}
		}

		/**
		 * The color used to fill the LeftWall background.<br>
		 * Default value: LIGHT_YELLOW
		 * 
		 * 
		 * @return Color
		 */
		public Color getColor() {
			return super.getColor();
		}

		/**
		 * Specifies the color used to fill the LeftWall background.<br>
		 * Default value: LIGHT_YELLOW
		 * 
		 * 
		 * @param value
		 *            Color
		 */
		public void setColor(Color value) {
			super.setColor(value);
		}
	}

	/**
	 * 
	 * <p>
	 * Title: RightWall class
	 * </p>
	 * 
	 * <p>
	 * Description: Wall Panel at right of Chart.
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
	public class RightWall extends Wall {

		public RightWall(IBaseChart c, final Walls w) {
			super(c);
			visible = false;
			getBrush().setDefaultColor(Color.LIGHT_YELLOW);
		}

		/**
		 * Use this method to paint a right wall at rectangle r.
		 * 
		 * @param g
		 *            IGraphics3D
		 * @param rect
		 *            Rectangle
		 */
		public void paint(IGraphics3D g, final Rectangle rect) {
			prepareGraphics(g);

			int b = rect.getBottom()
					+ calcWallSize(chart.getAxes().getBottom());
			int w = chart.getAspect().getWidth3D();

			if (iSize > 0) {
				g.cube(rect.getRight(), rect.y, rect.getRight() + iSize, b, 0,
						w + getBack().getSize(), getShouldDark());
			} else {
				g.rectangleZ(rect.getRight(), rect.y, b, 0, w + 1);
			}
		}

		/**
		 * Shows/Hides Right Wall.<br>
		 * Default value: false
		 * 
		 * @return boolean
		 */
		public boolean getVisible() {
			return super.getVisible();
		}

		/**
		 * Shows/Hides Right Wall.<br>
		 * Default value: false
		 * 
		 * @param value
		 *            boolean
		 */
		public void setVisible(boolean value) {
			super.setVisible(value);
		}
	}

	/**
	 * 
	 * 
	 * <p>
	 * Title: BackWall class
	 * </p>
	 * 
	 * <p>
	 * Description: Wall Panel at rear of Chart
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
	public class BackWall extends Wall {

		public BackWall(IBaseChart c, final Walls w) {
			super(c);

			setColor(Color.SILVER);

			ChartBrush brush = getBrush();
			brush.setDefaultColor(Color.SILVER);
			brush.setVisible(true);
			setTransparent(false);

			Gradient gradient = brush.getGradient();
			gradient.setVisible(true);
			gradient.setEndColor(Color.fromArgb(255, 255, 255));
			gradient.setMiddleColor(Color.EMPTY);
			gradient.setStartColor(Color.fromArgb(234, 234, 234));
			gradient.setUseMiddle(false);
		}

		/**
		 * Use this method to paint a back wall at rectangle r.
		 * 
		 * @param g
		 *            IGraphics3D
		 * @param rect
		 *            Rectangle
		 */
		public void paint(IGraphics3D g, final Rectangle rect) {
			prepareGraphics(g);

			if (chart.getAspect().getView3D()) {
				int w = chart.getAspect().getWidth3D();
				if (iSize > 0) {
					Rectangle r = rect.copy();
					r.setBottom(r.getBottom()
							+ calcWallSize(chart.getAxes().getBottom()));
					g.cube(r, w, w + iSize, getShouldDark());
				} else {
					g.rectangle(rect, w);
				}
			} else {
				g.rectangle(rect);
			}
		}

		protected boolean shouldSerializeTransparent() {
			return !getTransparent();
		}
	}

	/**
	 * 
	 * <p>
	 * Title: BottomWall class
	 * </p>
	 * 
	 * <p>
	 * Description: Wall Panel at bottom of Chart.
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
	public class BottomWall extends Wall {

		public BottomWall(IBaseChart c, final Walls w) {
			super(c);
			setColor(Color.WHITE);
		}

		/**
		 * Use this method to paint a bottom wall at rectangle r.
		 * 
		 * @param g
		 *            IGraphics3D
		 * @param rect
		 *            Rectangle
		 */
		public void paint(IGraphics3D g, final Rectangle rect) {
			prepareGraphics(g);

			int w = chart.getAspect().getWidth3D();
			if (iSize > 0) {
				Rectangle r = rect.copy();
				r.y = r.getBottom();
				r.height = iSize;
				g.cube(r, 0, w, getShouldDark());
			} else {
				g.rectangleY(rect.x, rect.getBottom() - iSize, rect.getRight(),
						0, w);
			}
		}
	}

	private BackWall back;
	private BottomWall bottom;
	private LeftWall left;
	private RightWall right;
	private boolean visible = true;
	private boolean view3D = true;

	/**
	 * Defines the Pen and Brush used to fill the left chart side.<br>
	 * Available IWall properties are Brush, Color, Dark3D, Gradient, Pen, Size
	 * and Transparent.<br>
	 * tChart.Aspect.View3D and tChart.Walls.Visible should be true to use
	 * tChart.Walls.Back, tChart.Walls.Bottom, tChart.Walls.Left and
	 * tChart.Walls.Right.
	 * 
	 * @return LeftWall
	 */
	public LeftWall getLeft() {
		return left;
	}

	/**
	 * Defines the pen and brush used to fill the Left chart side.<br>
	 * 
	 * @param value
	 */
	public void setLeft(final LeftWall value) {
		left = value;
	}

	/**
	 * Defines the pen and brush used to fill the right chart side.<br>
	 * Available IWall properties are Brush, Color, Dark3D, Gradient, Pen, Size
	 * and Transparent.<br>
	 * tChart.Aspect.View3D and tChart.Walls.Visible should be true to use
	 * tChart.Walls.Back, tChart.Walls.Bottom, tChart.Walls.Left and
	 * tChart.Walls.Right.
	 * 
	 * @return RightWall
	 */
	public RightWall getRight() {
		return right;
	}

	/**
	 * Defines the pen and brush used to fill the Right chart side.<br>
	 * 
	 * @param value
	 */
	public void setRight(final RightWall value) {
		right = value;
	}

	/**
	 * Defines the pen and brush used to fill the back chart side.<br>
	 * Available IWall properties are Brush, Color, Dark3D, Gradient, Pen, Size
	 * and Transparent.<br>
	 * tChart.Aspect.View3D and tChart.Walls.Visible should be true to use
	 * tChart.Walls.Back, tChart.Walls.Bottom, tChart.Walls.Left and
	 * tChart.Walls.Right.
	 * 
	 * @return BackWall
	 */
	public BackWall getBack() {
		return back;
	}

	/**
	 * Defines the pen and brush used to fill the back chart side.<br>
	 * 
	 * @param value
	 */
	public void setBack(final BackWall value) {
		back = value;
	}

	/**
	 * Defines the pen and brush used to fill the bottom chart side.<br>
	 * Available IWall properties are Brush, Color, Dark3D, Gradient, Pen, Size
	 * and Transparent.<br>
	 * tChart.Aspect.View3D and tChart.Walls.Visible should be true to use
	 * tChart.Walls.Back, tChart.Walls.Bottom, tChart.Walls.Left and
	 * tChart.Walls.Right.
	 * 
	 * @return BottomWall
	 */
	public BottomWall getBottom() {
		return bottom;
	}

	/**
	 * Defines the pen and brush used to fill the bottom chart side.<br>
	 * 
	 * @param value
	 */
	public void setBottom(final BottomWall value) {
		bottom = value;
	}

	/**
	 * Shows / Hides all Chart walls.<br>
	 * Visible draws Left and Bottom "walls" to simulate 3D effect.<br>
	 * You can control the 3D Wall proportion by using
	 * Chart.Aspect.Chart3DPercent.<br>
	 * Chart.Aspect.View3D controls (on/off) Walls.Visible. <br>
	 * Default value: true
	 * 
	 * @return boolean
	 */
	public boolean getVisible() {
		return visible;
	}

	/**
	 * Shows / Hides all Chart walls.<br>
	 * Default value: true
	 * 
	 * @param value
	 *            boolean
	 */
	public void setVisible(boolean value) {
		visible = setBooleanProperty(visible, value);
	}

	/**
	 * Shows all Chart walls in 3D.<br>
	 * Default value: true
	 * 
	 * @return boolean
	 */
	public boolean getView3D() {
		return view3D;
	}

	/**
	 * Shows all Chart walls in 3D when true.<br>
	 * Default value: true
	 * 
	 * @param value
	 *            boolean
	 */
	public void setView3D(boolean value) {
		view3D = setBooleanProperty(view3D, value);
	}

	public Walls(IBaseChart c) {
		super(c);

		left = new LeftWall(c, this);
		right = new RightWall(c, this);
		bottom = new BottomWall(c, this);
		back = new BackWall(c, this);
	}

	/**
	 * Paints walls at rectangle r.
	 * 
	 * @param g
	 *            IGraphics3D
	 * @param r
	 *            Rectangle
	 */
	public void paint(IGraphics3D g, final Rectangle r) {
		final boolean view3D = chart.getAspect().getView3D();

		if (visible) {

			if (view3D && right.getVisible() && (!chart.drawRightWallAfter()))
				right.paint(g, r);

			if (back.getVisible()
					&& (!chart.drawBackWallAfter(chart.getAspect().width3D)))
				back.paint(g, r);

			if (view3D) {
				if (left.getVisible() && (!chart.drawLeftWallFirst()))
					left.paint(g, r);

				if (bottom.getVisible())
					bottom.paint(g, r);

				/*
				 * if DrawBackWallAfter(0) then begin if IsAxisVisible(Axes.Top)
				 * then Axes.Top.Draw(True);
				 * 
				 * if IsAxisVisible(Axes.Bottom) then Axes.Bottom.Draw(True);
				 * end;
				 */
			}
		}
	}

	/**
	 * Calculates the Wall thickness of the specified Axis.
	 * 
	 * @param a
	 *            Axis
	 * @return int
	 */
	public int calcWallSize(final Axis a) {
		if (chart.getAspect().getView3D() && visible) {
			if (a == chart.getAxes().getLeft()) {
				return left.getSize();
			} else if (a == chart.getAxes().getBottom()) {
				return bottom.getSize();
			}
		}
		return 0;
	}

	public void setSize(int value) {
		left.setSize(value);
		right.setSize(value);
		bottom.setSize(value);
		back.setSize(value);
	}

	public void setChart(IBaseChart value) {
		super.setChart(value);

                if (left !=null) left.setChart(value);
		if (right !=null) right.setChart(value);
		if (bottom !=null) bottom.setChart(value);
		if (back !=null) back.setChart(value);
	}
}
