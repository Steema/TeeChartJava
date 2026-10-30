package com.steema.teechart.styles;

import com.steema.teechart.IBaseChart;
import com.steema.teechart.drawing.Color;
import com.steema.teechart.drawing.Graphics3D;
import com.steema.teechart.drawing.Point;
import com.steema.teechart.languages.Language;
import com.steema.teechart.misc.Utils;

public abstract class CustomErrorPoint3D extends Points3D {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private ChartErrors3D errors;

	// internal
	private IErrorPoint iErrorPoint;
	private Point iPoint;

	public CustomErrorPoint3D(IBaseChart c) {
		super(c);
		getLinePen().setVisible(false);
	}

	public ChartErrors3D getErrors() {
		if (errors == null)
			errors = new ChartErrors3D(this);
		return errors;
	}

	public void setChart(IBaseChart c) {
		getErrors().chart = c;
		getErrors().getFormat().IntChart = c;

		super.setChart(c);
	}

	protected void addSampleValues(int numValues) {
		SeriesRandom r = randomBounds(numValues);

		double percent = 1 / (getChart().getAspect().getChart3DPercent() / 100.0);
		double percentVert = getChart().getAspect().getChart3DPercent() / 100.0;

		for (int t = 1; t <= numValues; t++) {
			double left = numValues / (double) (20 + r.Random(4));
			double right = numValues / (double) (20 + r.Random(4));
			double top = numValues / (double) (20 + r.Random(4)) * percentVert;
			double bottom = numValues / (double) (20 + r.Random(4)) * percentVert;
			double front = numValues / (double) (20 + r.Random(4)) * percent;
			double back = numValues / (double) (20 + r.Random(4)) * percent;

			add(numValues * r.Random(), numValues * r.Random(), left, right, top, bottom, front, back);
			r.tmpX += r.StepX;
		}
	}

	public int add(double XVal, double YVal, double ZVal, double LeftErr, double RightErr, double TopErr, double BottomErr, double FrontErr,
			double BackErr, String text, Color color) {
		errors.getLeft().tempValue = LeftErr;
		errors.getRight().tempValue = RightErr;
		errors.getTop().tempValue = TopErr;
		errors.getBottom().tempValue = BottomErr;
		errors.getFront().tempValue = FrontErr;
		errors.getBack().tempValue = BackErr;

		return add(XVal, YVal, ZVal, text, color);
	}

	public int add(double x, double y, double z, double LeftErr, double RightErr, double TopErr, double BottomErr, double FrontErr, double BackErr,
			String text) {
		return add(x, y, z, LeftErr, RightErr, TopErr, BottomErr, FrontErr, BackErr, text, Utils.getEmptyColor());
	}

	public int add(double x, double y, double z, double LeftErr, double RightErr, double TopErr, double BottomErr, double FrontErr, double BackErr) {
		return add(x, y, z, LeftErr, RightErr, TopErr, BottomErr, FrontErr, BackErr, "", Utils.getEmptyColor());
	}

	public int add(double x, double y, double z, double LeftErr, double RightErr, double TopErr, double BottomErr, double FrontErr, double BackErr,
			Color color) {
		return add(x, y, z, LeftErr, RightErr, TopErr, BottomErr, FrontErr, BackErr, "", color);
	}

	public int add(double y, double z, double LeftErr, double RightErr, double TopErr, double BottomErr, double FrontErr, double BackErr) {
		return add(getCount(), y, z, LeftErr, RightErr, TopErr, BottomErr, FrontErr, BackErr, "", Utils.getEmptyColor());
	}

	private void drawLeftError() {
		int tmpX = getHorizAxis().calcSizeValue(errors.getLeft().getValue(iErrorPoint.ValueIndex));
		if ((errors.getFormat().getLeft().getVisible()) && (getPointer().getHorizSize() < tmpX)) {
			errors.preparePen(iErrorPoint.ValueIndex, errors.getFormat().getLeft());
			errors.drawError(iErrorPoint.XPos - Utils.round(getPointer().getHorizSize()), iErrorPoint.YPos,
					tmpX - Utils.round(getPointer().getHorizSize()), iErrorPoint.ErrorHorizSize, iErrorPoint.ZPos, true);
		}
	}

	private void drawRightError() {
		int tmpX = getHorizAxis().calcSizeValue(errors.getRight().getValue(iErrorPoint.ValueIndex));
		if ((errors.getFormat().getRight().getVisible()) && (getPointer().getHorizSize() < tmpX)) {
			errors.preparePen(iErrorPoint.ValueIndex, errors.getFormat().getRight());
			errors.drawError(iErrorPoint.XPos + Utils.round(getPointer().getHorizSize()), iErrorPoint.YPos,
					-(tmpX - Utils.round(getPointer().getHorizSize())), iErrorPoint.ErrorHorizSize, iErrorPoint.ZPos, true);
		}
	}

	private void drawTopError() {
		int tmpY = getVertAxis().calcSizeValue(errors.getTop().getValue(iErrorPoint.ValueIndex));
		if ((errors.getFormat().getTop().getVisible()) && (getPointer().getVertSize() < tmpY)) {
			errors.preparePen(iErrorPoint.ValueIndex, errors.getFormat().getTop());
			errors.drawError(iErrorPoint.XPos, iErrorPoint.YPos - Utils.round(getPointer().getVertSize()),
					tmpY - Utils.round(getPointer().getVertSize()), iErrorPoint.ErrorVertSize, iErrorPoint.ZPos, false);
		}
	}

	private void drawBottomError() {
		int tmpY = getVertAxis().calcSizeValue(errors.getBottom().getValue(iErrorPoint.ValueIndex));
		if ((errors.getFormat().getBottom().getVisible()) && (getPointer().getVertSize() < tmpY)) {
			errors.preparePen(iErrorPoint.ValueIndex, errors.getFormat().getBottom());
			errors.drawError(iErrorPoint.XPos, iErrorPoint.YPos + Utils.round(getPointer().getVertSize()),
					-(tmpY - Utils.round(getPointer().getVertSize())), iErrorPoint.ErrorVertSize, iErrorPoint.ZPos, false);
		}
	}

	private void drawFrontError() {
		int tmpZ = getChart().getAxes().getDepth().calcSizeValue(errors.getFront().getValue(iErrorPoint.ValueIndex));
		int PointerDepth = (int) (getEndZ() - getStartZ());
		if ((errors.getFormat().getFront().getVisible()) && (PointerDepth < tmpZ)) {
			errors.preparePen(iErrorPoint.ValueIndex, errors.getFormat().getFront());
			errors.drawZError(iErrorPoint.XPos, iErrorPoint.YPos, iErrorPoint.ZPos - PointerDepth, tmpZ - PointerDepth, iErrorPoint.ErrorVertSize);
		}
	}

	private void drawBackError() {
		int tmpZ = getChart().getAxes().getDepth().calcSizeValue(errors.getBack().getValue(iErrorPoint.ValueIndex));
		int PointerDepth = (int) (getEndZ() - getStartZ());
		if ((errors.getFormat().getBack().getVisible()) && (PointerDepth < tmpZ)) {
			errors.preparePen(iErrorPoint.ValueIndex, errors.getFormat().getBack());
			errors.drawZError(iErrorPoint.XPos, iErrorPoint.YPos, iErrorPoint.ZPos + PointerDepth, -(tmpZ - PointerDepth), iErrorPoint.ErrorVertSize);
		}
	}

	private void drawWithOrder(boolean LeftFirst, boolean TopFirst, boolean FrontFirst) {
		if (LeftFirst) {
			drawLeftError();
			if (TopFirst) {
				drawTopError();
				if (FrontFirst) {
					drawFrontError();
					super.drawValue(iErrorPoint.ValueIndex);
					drawBackError();
				} else {
					drawBackError();
					super.drawValue(iErrorPoint.ValueIndex);
					drawFrontError();
				}
				drawBottomError();
			} else {
				drawBottomError();
				if (FrontFirst) {
					drawFrontError();
					super.drawValue(iErrorPoint.ValueIndex);
					drawBackError();
				} else {
					drawBackError();
					super.drawValue(iErrorPoint.ValueIndex);
					drawFrontError();
				}
				drawTopError();
			}
			drawRightError();
		} else {
			drawRightError();
			if (TopFirst) {
				drawTopError();
				if (FrontFirst) {
					drawFrontError();
					super.drawValue(iErrorPoint.ValueIndex);
					drawBackError();
				} else {
					drawBackError();
					super.drawValue(iErrorPoint.ValueIndex);
					drawFrontError();
				}
				drawBottomError();
			} else {
				drawBottomError();
				if (FrontFirst) {
					drawFrontError();
					super.drawValue(iErrorPoint.ValueIndex);
					drawBackError();
				} else {
					drawBackError();
					super.drawValue(iErrorPoint.ValueIndex);
					drawFrontError();
				}
				drawTopError();
			}
			drawLeftError();
		}
	}

	private void calcErrorSize() {
		if (errors.getSize() == 0) {
			iErrorPoint.ErrorHorizSize = Utils.round(getPointer().getHorizSize());
			iErrorPoint.ErrorVertSize = Utils.round(getPointer().getVertSize());
		} else if (errors.getErrorSizeUnits() == ErrorWidthUnit.PERCENT) {
			iErrorPoint.ErrorHorizSize = (int) (1.0 * errors.getSize() * getPointer().getHorizSize() * 0.01);
			iErrorPoint.ErrorVertSize = (int) (1.0 * errors.getSize() * getPointer().getVertSize() * 0.01);
		} else {
			iErrorPoint.ErrorHorizSize = errors.getSize();
			iErrorPoint.ErrorVertSize = errors.getSize();
		}
	}

	public void drawValue(int valueIndex) {
		if (iErrorPoint == null)
			iErrorPoint = new IErrorPoint();
		iErrorPoint.XPos = Utils.round(calcXPosValue(getXValues().getValue(valueIndex)));
		iErrorPoint.YPos = Utils.round(calcYPosValue(getYValues().getValue(valueIndex)));
		iErrorPoint.ZPos = Utils.round(calcZPos(valueIndex));

		calcZPositions(valueIndex);
		calcErrorSize();
		iErrorPoint.ValueIndex = valueIndex;

		if ((getChart().getAspect().getOrthogonal()) || (!getChart().getAspect().getView3D())) {
			drawWithOrder(true, false, false);
		} else {
			if (getChart().getAspect().getRotation() % 360 <= 90) {
				if (getChart().getAspect().getElevation() % 360 <= 90)
					drawWithOrder(false, true, false);
				else if (getChart().getAspect().getElevation() % 360 <= 180)
					drawWithOrder(true, true, true);
				else if (getChart().getAspect().getElevation() % 360 <= 270)
					drawWithOrder(true, false, true);
				else
					drawWithOrder(false, false, false);
			} else if (getChart().getAspect().getRotation() % 360 <= 180) {
				if (getChart().getAspect().getElevation() % 360 <= 90)
					drawWithOrder(false, true, true);
				else if (getChart().getAspect().getElevation() % 360 <= 180)
					drawWithOrder(true, true, false);
				else if (getChart().getAspect().getElevation() % 360 <= 270)
					drawWithOrder(true, false, false);
				else
					drawWithOrder(false, false, true);
			} else if (getChart().getAspect().getRotation() % 360 <= 270) {
				if (getChart().getAspect().getElevation() % 360 <= 90)
					drawWithOrder(true, true, true);
				else if (getChart().getAspect().getElevation() % 360 <= 180)
					drawWithOrder(false, true, false);
				else if (getChart().getAspect().getElevation() % 360 <= 270)
					drawWithOrder(false, false, false);
				else
					drawWithOrder(true, false, true);
			} else // Rotation between 270 and 360
			{
				if (getChart().getAspect().getElevation() % 360 <= 90)
					drawWithOrder(true, true, false);
				else if (getChart().getAspect().getElevation() % 360 <= 180)
					drawWithOrder(false, true, true);
				else if (getChart().getAspect().getElevation() % 360 <= 270)
					drawWithOrder(false, false, true);
				else
					drawWithOrder(true, false, false);
			}
		}
	}

	public double getMinXValue() {
		return calcMinMaxValue(true, Direction.HORIZ);
	}

	public double getMinYValue() {
		return calcMinMaxValue(true, Direction.VERT);
	}

	public double getMinZValue() {
		return calcMinMaxValue(true, Direction.DEPTH);
	}

	public double getMaxXValue() {
		return calcMinMaxValue(false, Direction.HORIZ);
	}

	public double getMaxYValue() {
		return calcMinMaxValue(false, Direction.VERT);
	}

	public double getMaxZValue() {
		return calcMinMaxValue(false, Direction.DEPTH);
	}

	public String getDescription() {
		return Language.getString("GalleryErrorPoint3D");
	}

	private double calcMinMaxValue(boolean isMin, Direction dir) {
		boolean tmpfirst = true;
		Double result = 0.0;
		double tmpvalue = 0.0;
		for (int t = 0; t < getCount(); t++) {
			if ((!isNull(t)) || (getTreatNulls() == TreatNullsStyle.IGNORE)) {
				if (isMin) {
					switch (dir.getValue()) {
					case 0:
						tmpvalue = notMandatory.getValue(t) - errors.getLeft().getValue(t);
						break;
					case 1:
						tmpvalue = mandatory.getValue(t) - errors.getBottom().getValue(t);
						break;
					case 2:
						tmpvalue = getZValues().getValue(t) - errors.getFront().getValue(t);
						break;
					}
				} else {
					switch (dir.getValue()) {
					case 0:
						tmpvalue = notMandatory.getValue(t) + errors.getRight().getValue(t);
						break;
					case 1:
						tmpvalue = mandatory.getValue(t) + errors.getTop().getValue(t);
						break;
					case 2:
						tmpvalue = getZValues().getValue(t) + errors.getBack().getValue(t);
						break;
					}
				}
				if (tmpfirst) {
					result = tmpvalue;
					tmpfirst = false;
				} else {
					if (isMin) {
						if (result.compareTo(Double.NEGATIVE_INFINITY) == 0) {
							result = tmpvalue;
						} else {
							result = Math.min(result, tmpvalue);
						}
					} else {
						if (result.compareTo(Double.POSITIVE_INFINITY) == 0) {
							result = tmpvalue;
						} else {
							result = Math.max(result, tmpvalue);
						}
					}
				}
			}
		}
		return result;
	}

	public int clicked(int x, int y) {
		int result = super.clicked(x, y);

		if ((result == -1) && (firstVisible > -1) && (lastVisible > -1)) {
			iPoint = new Point(x, y);
			int tmpIndex = Math.min(lastVisible, getCount() - 1);
			calcErrorSize();

			for (int t = firstVisible; t <= tmpIndex; t++) {
				iErrorPoint.XPos = calcXPosValue(getXValues().getValue(t));
				iErrorPoint.YPos = calcYPosValue(getYValues().getValue(t));
				iErrorPoint.ZPos = calcZPos(t);
				iErrorPoint.ValueIndex = t;
				if (clickedError()) {
					result = t;
					break;
				}
			}
		}
		return result;
	}

	private boolean clickedError() {
		return clickedTopError() || clickedBottomError() || clickedLeftError() || clickedRightError() || clickedFrontError() || clickedBackError();
	}

	private boolean clickedTopError() {
		double errValue = errors.getTop().getValue(iErrorPoint.ValueIndex);
		return (errValue > 0)
				&& (errors.getFormat().getTop().getVisible())
				&& (clickedHorizontal(iErrorPoint.XPos - iErrorPoint.ErrorHorizSize, iErrorPoint.XPos + iErrorPoint.ErrorHorizSize,
						calcYPosValue(getYValues().getValue(iErrorPoint.ValueIndex) + errValue), errors.getFormat().getTop().getWidth()) || clickedVertical(
						iErrorPoint.XPos, iErrorPoint.YPos - getPointer().getVertSize(), calcYPosValue(getYValues().getValue(iErrorPoint.ValueIndex)
								+ errValue), errors.getFormat().getTop().getWidth()));
	}

	private boolean clickedBottomError() {
		double errValue = errors.getBottom().getValue(iErrorPoint.ValueIndex);
		return (errValue > 0)
				&& (errors.getFormat().getBottom().getVisible())
				&& (clickedHorizontal(iErrorPoint.XPos - iErrorPoint.ErrorHorizSize, iErrorPoint.XPos + iErrorPoint.ErrorHorizSize,
						calcYPosValue(getYValues().getValue(iErrorPoint.ValueIndex) - errValue), errors.getFormat().getBottom().getWidth()) || clickedVertical(
						iErrorPoint.XPos, iErrorPoint.YPos + getPointer().getVertSize(), calcYPosValue(getYValues().getValue(iErrorPoint.ValueIndex)
								- errValue), errors.getFormat().getBottom().getWidth()));
	}

	private boolean clickedLeftError() {
		double errValue = errors.getLeft().getValue(iErrorPoint.ValueIndex);
		return (errValue > 0)
				&& (errors.getFormat().getLeft().getVisible())
				&& (clickedHorizontal(iErrorPoint.XPos - getPointer().getHorizSize(), calcXPosValue(getXValues().getValue(iErrorPoint.ValueIndex)
						- errValue), iErrorPoint.YPos, errors.getFormat().getLeft().getWidth()) || clickedVertical(calcXPosValue(getXValues()
						.getValue(iErrorPoint.ValueIndex) - errValue), iErrorPoint.YPos - iErrorPoint.ErrorVertSize, iErrorPoint.YPos
						+ iErrorPoint.ErrorVertSize, errors.getFormat().getLeft().getWidth()));
	}

	private boolean clickedRightError() {
		double errValue = errors.getRight().getValue(iErrorPoint.ValueIndex);
		return (errValue > 0)
				&& (errors.getFormat().getRight().getVisible())
				&& (clickedHorizontal(iErrorPoint.XPos + getPointer().getHorizSize(), calcXPosValue(getXValues().getValue(iErrorPoint.ValueIndex)
						+ errValue), iErrorPoint.YPos, errors.getFormat().getRight().getWidth()) || clickedVertical(calcXPosValue(getXValues()
						.getValue(iErrorPoint.ValueIndex) + errValue), iErrorPoint.YPos - iErrorPoint.ErrorVertSize, iErrorPoint.YPos
						+ iErrorPoint.ErrorVertSize, errors.getFormat().getRight().getWidth()));
	}

	private boolean clickedFrontError() {
		double errValue = errors.getFront().getValue(iErrorPoint.ValueIndex);
		boolean result = (chart != null) && (errValue > 0) && (errors.getFormat().getFront().getVisible());
		if (result) {
			int errorDepth = chart.getAxes().getDepth().calcSizeValue(errValue);
			Graphics3D g = (Graphics3D) chart.getGraphics3D();
			Point[] PS = new Point[4];
			PS[0] = g.calculate3DPosition(iErrorPoint.XPos, iErrorPoint.YPos, iErrorPoint.ZPos + (getEndZ() - getStartZ()));
			PS[1] = g.calculate3DPosition(iErrorPoint.XPos, iErrorPoint.YPos, iErrorPoint.ZPos + errorDepth);
			PS[2] = g.calculate3DPosition(iErrorPoint.XPos, iErrorPoint.YPos - iErrorPoint.ErrorVertSize, iErrorPoint.ZPos + errorDepth);
			PS[3] = g.calculate3DPosition(iErrorPoint.XPos, iErrorPoint.YPos + iErrorPoint.ErrorVertSize, iErrorPoint.ZPos + errorDepth);

			result = Graphics3D.pointInLineTolerance(iPoint, PS[0], PS[1], errors.getFormat().getFront().getWidth())
							|| Graphics3D.pointInLineTolerance(iPoint, PS[2], PS[3], errors.getFormat().getFront().getWidth());
		}
		return result;
	}

	private boolean clickedBackError() {
		double errValue = errors.getBack().getValue(iErrorPoint.ValueIndex);
		boolean result = (chart != null) && (errValue > 0) && (errors.getFormat().getBack().getVisible());
		if (result) {
			int errorDepth = chart.getAxes().getDepth().calcSizeValue(errValue);
			Graphics3D g = (Graphics3D) chart.getGraphics3D();
			Point[] PS = new Point[4];
			PS[0] = g.calculate3DPosition(iErrorPoint.XPos, iErrorPoint.YPos, iErrorPoint.ZPos - (getEndZ() - getStartZ()));
			PS[1] = g.calculate3DPosition(iErrorPoint.XPos, iErrorPoint.YPos, iErrorPoint.ZPos - errorDepth);
			PS[2] = g.calculate3DPosition(iErrorPoint.XPos, iErrorPoint.YPos - iErrorPoint.ErrorVertSize, iErrorPoint.ZPos - errorDepth);
			PS[3] = g.calculate3DPosition(iErrorPoint.XPos, iErrorPoint.YPos + iErrorPoint.ErrorVertSize, iErrorPoint.ZPos - errorDepth);

			result = Graphics3D.pointInLineTolerance(iPoint, PS[0], PS[1], errors.getFormat().getBack().getWidth())
							|| Graphics3D.pointInLineTolerance(iPoint, PS[2], PS[3], errors.getFormat().getBack().getWidth());
		}
		return result;
	}

	private boolean clickedHorizontal(int x0, int x1, int y, int t) {
		if (chart != null) {
			Graphics3D g = (Graphics3D) chart.getGraphics3D();
			Point P0 = g.calculate3DPosition(x0, y, iErrorPoint.ZPos);
			Point P1 = g.calculate3DPosition(x1, y, iErrorPoint.ZPos);
			return Graphics3D.pointInLineTolerance(iPoint, P0, P1, t);
		} else
			return false;
	}

	private boolean clickedVertical(int x, int y0, int y1, int t) {
		if (chart != null) {
			Graphics3D g = (Graphics3D) chart.getGraphics3D();
			Point P0 = g.calculate3DPosition(x, y0, iErrorPoint.ZPos);
			Point P1 = g.calculate3DPosition(x, y1, iErrorPoint.ZPos);
			return Graphics3D.pointInLineTolerance(iPoint, P0, P1, t);
		} else
			return false;
	}
}