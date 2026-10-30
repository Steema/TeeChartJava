package com.steema.teechart.styles;

import com.steema.teechart.languages.Language;
import com.steema.teechart.Rectangle;
import com.steema.teechart.drawing.Color;
import com.steema.teechart.drawing.GradientDirection;
import com.steema.teechart.drawing.Point;
import com.steema.teechart.IBaseChart;
import com.steema.teechart.drawing.IGraphics3D;
import com.steema.teechart.drawing.Graphics3D;
import com.steema.teechart.misc.Utils;
import com.steema.teechart.styles.CustomBar.MarksLocation;

/**
 * 
 * <p>
 * Title: HorizBar class
 * </p>
 * 
 * <p>
 * Description: Horizontal Bar Series.
 * </p>
 * 
 * 
 * <p>
 * Example:
 * 
 * <pre>
 * <font face="Courier" size="4">
 *  barSeries = new HorizBar(myChart.getChart());
 *  barSeries.setBarStyle(BarStyle.RECTANGLE);
 *  barSeries.setMultiBar(MultiBars.NONE);
 *  barSeries.fillSampleValues(3);
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
 */
public class HorizBar extends CustomBar {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public HorizBar() {
		this((IBaseChart) null);
	}

	public HorizBar(IBaseChart c) {
		super(c);

		setHorizontal();
		notMandatory.setOrder(ValueListOrder.ASCENDING);
		mandatory.setOrder(ValueListOrder.NONE);
		getGradient().setDirection(GradientDirection.HORIZONTAL);
		// mandatory.Name=Language.getString("ValuesBar");
	}

	/**
	 * Gets descriptive text.
	 * 
	 * @return String
	 */
	public String getDescription() {
		return Language.getString("GalleryHorizBar");
	}

	/**
	 * Defines the percent of bar Height, from 0 to 100.<br>
	 * Default value: 70
	 * 
	 * @return int
	 */
	public int getBarHeightPercent() {
		return barSizePercent;
	}

	/**
	 * Defines the percent of bar Height, from 0 to 100.<br>
	 * Default value: 70
	 * 
	 * @param value
	 *            int
	 */
	public void setBarHeightPercent(int value) {
		setBarSizePercent(value);
	}

	protected int internalCalcMarkLength(int valueIndex) {
		if (valueIndex == -1) {
			return maxMarkWidth();
		} else {
			return getMarks().textWidth(valueIndex);
		}
	}

	private boolean otherClicked(Point p, int tmpX, int endX, int tmpY) {
		if (getBarStyle() == BarStyle.ELLIPSE) {
			return Graphics3D.pointInEllipse(p, tmpX, tmpY, endX, tmpY
					+ iBarSize);
		} else {
			return (p.x >= tmpX) && (p.x <= endX);
		}
	}

	private boolean inTriangle(int x1, int x2, int tmpY, Point p) {
		Point[] triP = new Point[3];
		IGraphics3D g = chart.getGraphics3D();
		if (chart.getAspect().getView3D()) {
			triP[0] = g.calc3DPoint(x1, tmpY, getStartZ());
			triP[1] = g.calc3DPoint(x2, tmpY + (iBarSize / 2), getMiddleZ());
			triP[2] = g.calc3DPoint(x1, tmpY + iBarSize, getStartZ());
			return Graphics3D.pointInPolygon(p, triP);
		} else {
			return Graphics3D.pointInHorizTriangle(p, tmpY, tmpY + iBarSize,
					x1, x2);
		}
	}

	protected boolean internalClicked(int valueIndex, Point point) {
		int tmpY = calcYPos(valueIndex);

		if ((!chart.getAspect().getView3D())
				&& ((point.y < tmpY) || (point.y > (tmpY + iBarSize)))) {
			return false;
		}

		int tmpX = calcXPos(valueIndex);
		int endX = getOriginPos(valueIndex);

		if (endX < tmpX) {
			int tmp = tmpX;
			tmpX = endX;
			endX = tmp;
		}

		BarStyle tmpStyle = getBarStyle();

		if (tmpStyle == BarStyle.INVPYRAMID) {
			return inTriangle(endX, tmpX, tmpY, point);
		} else if ((tmpStyle == BarStyle.PYRAMID) | (tmpStyle == BarStyle.CONE)) {
			return inTriangle(tmpX, endX, tmpY, point);
		} else {
			if (chart.getAspect().getView3D()) {
				int x = point.x;
				int y = point.y;
				Point p = chart.getGraphics3D().calculate2DPosition(x, y,
						getStartZ());
				point.x = p.x;
				point.y = p.y;
				if ((point.y >= tmpY) && (point.y <= (tmpY + iBarSize))) {
					return otherClicked(point, tmpX, endX, tmpY);
				}
			} else {
				return otherClicked(point, tmpX, endX, tmpY);
			}
		}

		return false;
	}

	public void calcHorizMargins(Margins margins) {
		super.calcHorizMargins(margins);
		int tmp = calcMarkLength(-1);
		if (tmp > 0) {
			tmp++;
		}
		if (bUseOrigin && (super.getMinXValue() < dOrigin)) {
			margins.min += tmp;
		}
		if ((!bUseOrigin) || (super.getMaxXValue() > dOrigin)) {
			if (getHorizAxis().getInverted()) {
				margins.min += tmp;
			} else {
				margins.max += tmp;
			}
		}
	}

	public void calcVerticalMargins(Margins margins) {
		super.calcVerticalMargins(margins);
		internalApplyBarMargin(margins);
	}

	/**
	 * Called internally. Draws the "ValueIndex" point of the Series.
	 * 
	 * @param valueIndex
	 *            int
	 */
	public void drawValue(int valueIndex) {
		super.drawValue(valueIndex);
		normalBarColor = getValueColor(valueIndex);

		Rectangle r = new Rectangle();

		if (normalBarColor != Color.EMPTY) {
			r.y = calcYPos(valueIndex);
		}

		if ((barSizePercent == 100) && (valueIndex > 0)) {
			r.height = calcYPos(valueIndex - 1) - r.y;
		} else {
			r.height = iBarSize + 1; // 5.02
		}

		r.x = getOriginPos(valueIndex);
		r.width = calcXPos(valueIndex) - r.x;

		if (!getPen().getVisible()) {
			if (r.getRight() > r.x) {
				r.width++;
			} else {
				r.x++;
				// r.Right--;
			}
			r.height++; // 5.02
		}

		iBarBounds = r;
		
		if (mandatory.getValue(valueIndex) != 0)
		{
		  if (r.getRight() > r.x)
			drawBar(valueIndex, r.x, r.getRight());
		  else
			drawBar(valueIndex, r.getRight(), r.x);
		}
	}
        @Override
	protected void drawMark(int valueIndex, String s,
			SeriesMarksPosition position) {
		int DifH = iBarSize / 2;
		int DifW = getMarks().getCallout().getLength()
				+ getMarks().getCallout().getDistance();

		if (!getMarksOnBar())
                {
			boolean tmp = (position.arrowFrom.x < getOriginPos(valueIndex));
			if (tmp)
				DifW = -DifW - position.width;

			position.leftTop.x += DifW + (position.width / 2);
			position.leftTop.y += DifH + (position.height / 2);
			position.arrowTo.x += DifW;
			position.arrowTo.y += DifH;
			position.arrowFrom.y += DifH;

			if (tmp)
				position.arrowFrom.x -= getMarks().getCallout().getDistance();
			else
				position.arrowFrom.x += getMarks().getCallout().getDistance();
		} else {
			getMarks().getArrow().setVisible(false);
			position.leftTop.y += DifH + (position.height / 2);
			int barRight = calcXPosValue(getXValues().getValue(valueIndex));
			if (getMarksLocation() == MarksLocation.Start) {
				position.leftTop.x = calcXPosValue(0) + 1;
			} 
                        else if (getMarksLocation()== MarksLocation.Center)
                        {

				int xpos = (barRight / 2);

				position.leftTop.x = xpos; // -(Marks.TextWidth(valueIndex) /
											// 2);

			}
                          else if (getMarksLocation()== MarksLocation.End)
                        {
			position.leftTop.x = barRight - getMarks().textWidth(valueIndex)
					- Utils.round((getMarks().getFont().getSize() * 1.2));
                        }
		}

		super.drawMark(valueIndex, s, position);
	}

	/**
	 * The pixel Screen Horizontal coordinate of the ValueIndex Series value.<br>
	 * This coordinate is calculated using the Series associated Horizontal
	 * Axis.
	 * 
	 * @param valueIndex
	 *            int
	 * @return int
	 */
	public int calcXPos(int valueIndex) {
		if ((iMultiBar == MultiBars.NONE) || (iMultiBar == MultiBars.SIDE)
				|| (iMultiBar == MultiBars.SIDEALL)) {
			return super.calcXPos(valueIndex);
		} else {
			double tmpValue = vxValues.value[valueIndex]
					+ pointOrigin(valueIndex, false);
			if ((iMultiBar == MultiBars.STACKED)
					|| (iMultiBar == MultiBars.SELFSTACK)) {
				return calcXPosValue(tmpValue);
			} else {
				double tmp = pointOrigin(valueIndex, true);
				return (tmp != 0) ? calcXPosValue(tmpValue * 100.0 / tmp) : 0;
			}
		}
	}

	/**
	 * The vertical Bar position is the "real" Y pos plus the Barwidth by our
	 * BarSeries order.<br>
	 * This coordinate is calculated using the Series associated Vertical Axis.
	 * 
	 * @param valueIndex
	 *            int
	 * @return int
	 */
	public int calcYPos(int valueIndex) {
		int result = 0;

		if (iMultiBar == MultiBars.SIDEALL) {
			result = getVertAxis().calcYPosValue(iPreviousCount + valueIndex)
					- (iBarSize / 2);
		} else if (iMultiBar == MultiBars.SELFSTACK) {
			result = (super.calcYPosValue(getMinYValue())) - (iBarSize / 2);
		} else {
			result = super.calcYPos(valueIndex);
			if (iMultiBar != MultiBars.NONE) {
				result += Utils.round(iBarSize
						* (((iNumBars * 0.5) - (1 + iNumBars - iOrderPos))));
			} else {
				result -= (iBarSize / 2);
			}
		}

		return applyBarOffset(result);
	}

	protected void drawTickLine(int tickPos, BarStyle aStyle) {
		super.drawTickLine(tickPos, aStyle);

		if ((aStyle == BarStyle.RECTANGLE) | (aStyle == BarStyle.RECTGRADIENT)) {
			chart.getGraphics3D()
					.verticalLine(tickPos, getBarBounds().getTop(),
							getBarBounds().getBottom(), startZ);
		} else if (aStyle == BarStyle.ARROW) {
			int tmp = getBarBounds().height / 4;
			chart.getGraphics3D().verticalLine(tickPos,
					getBarBounds().getTop() + tmp,
					getBarBounds().getBottom() - tmp, middleZ);
		}

		if (chart.getAspect().getView3D()) {
			if ((aStyle == BarStyle.RECTANGLE)
					| (aStyle == BarStyle.RECTGRADIENT)) {
				Point p0 = chart.getGraphics3D().calculate3DPosition(
						getBarBounds().getTop(), getBarBounds().getLeft(),
						startZ);
				Point p1 = chart.getGraphics3D()
						.calculate3DPosition(getBarBounds().getTop(),
								getBarBounds().getLeft(), endZ);
				Point p2 = chart.getGraphics3D().calculate3DPosition(
						getBarBounds().getTop(), getBarBounds().getRight(),
						endZ);
				if (Graphics3D.cull(p0, p1, p2)) {
					p0 = chart.getGraphics3D().calculate3DPosition(
							getBarBounds().getLeft(),
							getBarBounds().getBottom(), startZ);
					p1 = chart.getGraphics3D().calculate3DPosition(
							getBarBounds().getLeft(),
							getBarBounds().getBottom(), endZ);
					p2 = chart.getGraphics3D().calculate3DPosition(
							getBarBounds().getBottom(),
							getBarBounds().getRight(), endZ);
					if (Graphics3D.cull(p0, p1, p2))
						chart.getGraphics3D().zLine(tickPos,
								getBarBounds().getTop(), startZ, endZ);
				} else
					chart.getGraphics3D().zLine(tickPos,
							getBarBounds().getBottom(), startZ, endZ);
			}
		}
	}

	private void drawBar(int barIndex, int startPos, int endPos) {
		IGraphics3D g = chart.getGraphics3D();

		setPenBrushBar(normalBarColor);
		Rectangle r = iBarBounds;

		int tmpMidY = (r.y + r.getBottom()) / 2;
		BarStyle tmp = doGetBarStyle(barIndex);
		if (chart.getAspect().getView3D()) {

			if ((tmp == BarStyle.RECTANGLE) || (tmp == BarStyle.ROUNDRECTANGLE)) {
				g.cube(startPos, r.y, endPos, r.getBottom(), getStartZ(),
						getEndZ(), bDark3D);
			} else if (tmp == BarStyle.PYRAMID) {
				g.pyramid(false, startPos, r.y, endPos, r.getBottom(),
						getStartZ(), getEndZ(), bDark3D);
			} else if (tmp == BarStyle.INVPYRAMID) {
				g.pyramid(false, endPos, r.y, startPos, r.getBottom(),
						getStartZ(), getEndZ(), bDark3D);
			} else if (tmp == BarStyle.CYLINDER) {
				g.cylinder(false, r, getStartZ(), getEndZ(), bDark3D);
			} else if (tmp == BarStyle.ELLIPSE) {
				g.ellipse(iBarBounds, getMiddleZ());
			} else if (tmp == BarStyle.ARROW) {
				g.arrow(true, new Point(startPos, tmpMidY), new Point(endPos,
						tmpMidY), r.height, r.height / 2, getMiddleZ());
			} else if (tmp == BarStyle.RECTGRADIENT) {
				g.cube(startPos, r.y, endPos, r.getBottom(), getStartZ(),
						getEndZ(), bDark3D);
				if (g.getSupportsFullRotation()
						|| chart.getAspect().getOrthogonal()) {
					doGradient3D(barIndex,
							g.calc3DPoint(startPos, r.y, getStartZ()),
							g.calc3DPoint(endPos, r.getBottom(), getStartZ()));
				}
			} else if (tmp == BarStyle.CONE) {
				g.cone(false, iBarBounds, getStartZ(), getEndZ(), bDark3D,
						conePercent);
			}
		} else {
			if ((tmp == BarStyle.RECTANGLE) | (tmp == BarStyle.CYLINDER)) {
				barRectangle(normalBarColor, iBarBounds);
			} else if ((tmp == BarStyle.PYRAMID) | (tmp == BarStyle.CONE)) {
				Point[] pCone = { new Point(startPos, r.y),
						new Point(endPos, tmpMidY),
						new Point(startPos, r.getBottom()) };
				g.polygon(pCone);
			} else if (tmp == BarStyle.INVPYRAMID) {
				Point[] pInv = { new Point(endPos, r.y),
						new Point(startPos, tmpMidY),
						new Point(endPos, r.getBottom()) };
				g.polygon(pInv);
			} else if (tmp == BarStyle.ELLIPSE) {
				g.ellipse(iBarBounds);
			} else if (tmp == BarStyle.ARROW) {
				g.arrow(true, new Point(startPos, tmpMidY), new Point(endPos,
						tmpMidY), r.height, r.height / 2, getMiddleZ());
			} else if (tmp == BarStyle.RECTGRADIENT) {
				doBarGradient(barIndex, new Rectangle(startPos, r.y, endPos
						- startPos, r.height));
			} else if (tmp == BarStyle.ROUNDRECTANGLE) {
				barRectangle(normalBarColor, iBarBounds, barIndex, true);
			}
		}
		drawTickLines(startPos, endPos, tmp);
	}

	protected boolean drawSeriesForward(int valueIndex) {
		if (iMultiBar == MultiBars.NONE) {
			return true;
		} else if ((iMultiBar == MultiBars.NONE)
				| (iMultiBar == MultiBars.SIDEALL)) {
			return false;
		} else if ((iMultiBar == MultiBars.STACKED)
				| (iMultiBar == MultiBars.SELFSTACK)) {
			boolean result = mandatory.value[valueIndex] >= 0; /* 5.01 */
			if (getHorizAxis().getInverted()) {
				result = !result;
			}
			return result;
		} else {
			return !getHorizAxis().getInverted();
		}
	}

	protected int getOriginPos(int valueIndex) {
		return internalGetOriginPos(valueIndex, getHorizAxis().iStartPos);
	}

	/**
	 * The Maximum Value of the Series X Values List.
	 * 
	 * @return double
	 */
	public double getMaxXValue() {
		return maxMandatoryValue(super.getMaxXValue());
	}

	/**
	 * The Minimum Value of the Series X Values List.
	 * 
	 * @return double
	 */
	public double getMinXValue() {
		return minMandatoryValue(super.getMinXValue());
	}

	/**
	 * The Minimum Value of the Series Y Values Lists.<br>
	 * As some Series have more than one Y Values List, this Minimum Value is
	 * the "Minimum of Minimums" of all Series Y Values lists. <br>
	 * 
	 * @return double
	 */
	public double getMinYValue() {
		if (iMultiBar == MultiBars.SELFSTACK) {
			return getChart().getSeriesIndexOf(this);
		} else {
			return super.getMinYValue();
		}
	}

	/**
	 * The Maximum Value of the Series Y Values List.
	 * 
	 * @return double
	 */
	public double getMaxYValue() {
		if (iMultiBar == MultiBars.SELFSTACK) {
			return getMinYValue();
		} else {
			return (iMultiBar == MultiBars.SIDEALL) ? iPreviousCount
					+ getCount() - 1 : super.getMaxYValue();
		}
	}
}
