package com.steema.teechart.tools;

import com.steema.teechart.languages.Language;
import com.steema.teechart.events.FrameworkMouseEvent;

import com.steema.teechart.Cursor;
import com.steema.teechart.Rectangle;

import com.steema.teechart.drawing.Color;
import com.steema.teechart.drawing.Point;
import com.steema.teechart.IBaseChart;
import com.steema.teechart.drawing.ChartPen;
import com.steema.teechart.styles.Pie;
import com.steema.teechart.Aspect;
import com.steema.teechart.styles.ISeries;
import com.steema.teechart.drawing.IGraphics3D;
import com.steema.teechart.misc.MathUtils;

/**
 * 
 * <p>Title: Rotate class</p>
 * 
 * <p>Description: Rotate tool.</p>
 * 
 * <p>Copyright (c) 2005-2019 by Steema Software SL. All Rights Reserved.</p>
 * 
 * <p>Company: Steema Software SL</p>
 * 
 */
public class Rotate extends Tool {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int button = FrameworkMouseEvent.BUTTON1;
	private RotateStyle style = RotateStyle.ALL;
	private boolean inverted;

	private int iOldX;
	private int iOldY;
	private boolean dragging;
	private boolean iFirstTime;
	private boolean iOldRepaint;

	public Rotate() {
		this((IBaseChart) null);
	}

	public Rotate(IBaseChart c) {
		super(c);
	}

	/**
	 * Inverts the direction of Rotation and Elevation.<br>
     * In other words, left to right changes from a clockwise rotation to a
     * counter-clockwise rotation and an upward mouse movement moves the chart
     * towards a horizontal plane. <br>
	 * Default value: true
	 * 
	 * @return boolean
	 */
	public boolean getInverted() {
		return inverted;
	}

	/**
	 * Inverts the direction of Rotation and Elevation.<br>
	 * Default value: true
	 * 
     * @param value boolean
	 */
	public void setInverted(boolean value) {
		inverted = value;
	}

	/**
     * Determines whether mouse action applies to Rotation, Elevation or Both.
     * <br>
	 * Default value: RotateStyle.All
	 * 
	 * 
	 * @return RotateStyle
	 */
	public RotateStyle getStyle() {
		return style;
	}

	/**
     * Determines whether mouse action applies to Rotation, Elevation or Both.
     * <br>
	 * Default value: RotateStyle.All
	 * 
	 * 
     * @param value RotateStyle
	 */
	public void setStyle(RotateStyle value) {
		style = value;
	}

	/**
	 * Defines which mousebutton activates the TTeeCustomTool.<br>
	 * Default value: MouseButtons.Left
	 * 
	 * @return int
	 */
	public int getButton() {
		return button;
	}

	/**
	 * Defines which mousebutton activates the TTeeCustomTool.<br>
	 * Default value: MouseButtons.Left
	 * 
     * @param value int
	 */
	public void setButton(int value) {
		button = value;
	}

	private Point calcPoint(IGraphics3D g, int x, int y, int z) {
		// return chart.getParent().pointToScreen(g.calc3DPoint(x, y, z));
		return g.calc3DPoint(x, y, z);
	}

	private void drawCubeOutline() {
		IGraphics3D g = chart.getGraphics3D();
		g.setPen(getPen());
		// g.getPen().setColor(Color.white);

		int w = chart.getAspect().getWidth3D();

		Rectangle r = chart.getChartRect();

		Point p0 = calcPoint(g, r.getLeft(), r.getTop(), 0);
		Point p1 = calcPoint(g, r.getLeft(), r.getBottom(), 0);
		Point p2 = calcPoint(g, r.getRight(), r.getBottom(), 0);
		Point p3 = calcPoint(g, r.getRight(), r.getTop(), 0);

		Point p4 = calcPoint(g, r.getLeft(), r.getTop(), w);
		Point p5 = calcPoint(g, r.getLeft(), r.getBottom(), w);
		Point p6 = calcPoint(g, r.getRight(), r.getBottom(), w);
		Point p7 = calcPoint(g, r.getRight(), r.getTop(), w);

		// Color c = getPen().getColor();
		g.line(p0, p1);
		g.line(p1, p2);
		g.line(p2, p3);
		g.line(p3, p0);

		g.line(p4, p5);
		g.line(p5, p6);
		g.line(p6, p7);
		g.line(p7, p4);

		g.line(p0, p4);
		g.line(p1, p5);
		g.line(p2, p6);
		g.line(p3, p7);
	}

	static private int correctAngle(int angle) {
		int result = angle;

		if (result > 360) {
			result -= 360;
		} else if (result < 0) {
			result += 360;
		}
		return result;
	}

	private int calcAngleChange(int angle, int change) {
		return angle + change;

		/*
        if (change > 0) {
            return Math.min(360, angle + change);
        } else {
            int tmpMinAngle = chart.getGraphics3D().getSupportsFullRotation() ?
                              0 : 0;  // 270
            return Math.max(tmpMinAngle, angle + change);
        }
		 */
	}

	/**
	 * Gets descriptive text.
	 * 
	 * @return String
	 */
	public String getDescription() {
		return Language.getString("RotateTool");
	}

	/**
	 * Gets detailed descriptive text.
	 * 
	 * @return String
	 */
	public String getSummary() {
		return Language.getString("RotateSummary");
	}

	public static Pie firstSeriesPie(IBaseChart c) {
		for (int t = 0; t < c.getSeriesCount(); t++) {
			ISeries s = c.getSeries(t);
			if ((s instanceof Pie) && (s.getActive())) {
				return (Pie) s;
			}
		}
		return null;
	}

	private void mouseMove(FrameworkMouseEvent e) {
		Aspect a = chart.getAspect();

		if (getPen().getVisible()) {
			if (iFirstTime) {
				iOldRepaint = chart.getAutoRepaint();
				chart.setAutoRepaint(false);
				iFirstTime = false;
			} else {
				drawCubeOutline();
			}
		}

		a.setView3D(true);
		a.setOrthogonal(false);

		int tmpX = MathUtils.round(90.0 * (e.getX() - iOldX) / chart.getWidth());
		if (inverted) {
			tmpX = -tmpX;
		}

		int tmpY = MathUtils.round(90.0 * (iOldY - e.getY()) / chart.getHeight());
		if (inverted) {
			tmpY = -tmpY;
		}

		if (chart.getGraphics3D().getSupportsFullRotation()) {

			if ((style == RotateStyle.ROTATION) || (style == RotateStyle.ALL)) {
				a.setRotation(correctAngle(a.getRotation() + tmpX));
			}

			if ((style == RotateStyle.ELEVATION) || (style == RotateStyle.ALL)) {
				a.setElevation(correctAngle(a.getElevation() + tmpY));
			}
		} else {
			if ((style == RotateStyle.ROTATION) || (style == RotateStyle.ALL)) {

				Pie tmpSeries = firstSeriesPie(chart);

				if (tmpSeries != null) {
					a.setRotation(360);
					if (!chart.getGraphics3D().getSupportsFullRotation()) {
						a.setPerspective(0);
					}
					if (tmpX != 0) {
						tmpSeries.setRotationAngle(correctAngle(tmpSeries.getRotationAngle() + tmpX));
					}
				} else {
					a.setRotation(calcAngleChange(a.getRotation(), tmpX));
				}
			}

			if ((style == RotateStyle.ELEVATION) || (style == RotateStyle.ALL)) {
				a.setElevation(calcAngleChange(a.getElevation(), tmpY));
			}
		}

		iOldX = e.getX();
		iOldY = e.getY();
		chart.setCancelMouse(true);

		if (getPen().getVisible()) {
			chart.getGraphics3D().getAspect().setOrthogonal(false); // 6.01
			chart.getGraphics3D().calcTrigValues();
			chart.getGraphics3D().calcPerspective(chart.getChartRect());
			drawCubeOutline();
		}

		chart.getParent().refreshControl();
		// if (OnRotate!=null) OnRotate(this,new EventArgs(null));
	}

	public Cursor mouseEvent(FrameworkMouseEvent e, Cursor c) {
		if (e.getID() == FrameworkMouseEvent.MOUSE_RELEASED) {
			if (dragging) {
				dragging = false;
				if (getPen().getVisible()) {
					chart.setAutoRepaint(iOldRepaint);
					chart.invalidate();
				}
			}
		} else if ((e.getID() == FrameworkMouseEvent.MOUSE_DRAGGED) || (e.getID() == FrameworkMouseEvent.MOUSE_MOVED)) {
			if (dragging) {
				mouseMove(e);
			}
		} else if (e.getID() == FrameworkMouseEvent.MOUSE_PRESSED) {
			if (e.getButton() == button) {
				dragging = true;
				iOldX = e.getX();
				iOldY = e.getY();
				iFirstTime = true;
				chart.setCancelMouse(true);
			}
			if (dragging) {
				boolean stop = true;
				if (stop) {
					stop = false;
				}
			}
		}
		return c;
	}

	/**
	 * Element Pen characteristics.
	 * 
	 * @return ChartPen
	 */
	public ChartPen getPen() {
		if (pPen == null) {
			pPen = new ChartPen(chart, Color.WHITE, false);
		}
		return pPen;
	}

	public static double rotationChange(boolean fullRotation, double value, double delta) {
		if (delta == 0) {
			return value;
		}

		double result = value;

		if (delta > 0) {
			result += delta;

			if (result > 360) {
				result -= 360;
			}

		} else {

			if (fullRotation) {
				result = Math.max(0, value + delta);
			} else {

				result = value + delta;

				if (result < 0) {
					result += 360;
				}
			}
		}

		return result;
	}

	public static double elevationChange(boolean fullRotation, double value, double delta) {
		if (delta == 0) {
			return value;
		}

		double result = value;

		if (delta > 0) {
			result = Math.min(360, value + delta);
		} else {
			int tmpMinAngle;
			if (fullRotation) {
				tmpMinAngle = 0;
			} else {
				tmpMinAngle = 270;
			}
			result = Math.max(tmpMinAngle, value + delta);
		}

		return result;
	}
}
