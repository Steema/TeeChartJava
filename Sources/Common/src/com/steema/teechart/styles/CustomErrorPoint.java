package com.steema.teechart.styles;

import com.steema.teechart.Chart;
import com.steema.teechart.IBaseChart;
import com.steema.teechart.drawing.Color;
import com.steema.teechart.drawing.Graphics3D;
import com.steema.teechart.drawing.Point;
import com.steema.teechart.languages.Language;
import com.steema.teechart.misc.Utils;

public abstract class CustomErrorPoint extends Points {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private ChartErrors errors;

	// internal
	private IErrorPoint iErrorPoint;
	private Point iPoint;

	public CustomErrorPoint(IBaseChart c) {
		super(c);
	}

	public CustomErrorPoint() {
		this((Chart) null);
	}

	public ChartErrors getErrors() {
		if (errors == null)
			errors = new ChartErrors(this);
		return errors;
	}

	public void setChart(Chart c) {
		getErrors().setChart(c);
		getErrors().getFormat().IntChart = c;

		super.setChart(c);
	}

	protected void calcHorizMargins(int LeftMargin, int RightMargin) {
		// super.calcHorizMargins(LeftMargin, RightMargin);
		LeftMargin += 2;
		RightMargin += 2;
	}

	protected void calcVerticalMargins(int TopMargin, int BottomMargin) {
		// super.calcVerticalMargins(TopMargin, BottomMargin);
		TopMargin += 2;
		BottomMargin += 2;
	}

	@Override
	protected void addSampleValues(int numValues) {
		SeriesRandom r = randomBounds(numValues);
		for (int t = 1; t <= numValues; t++) {
			add(r.tmpX, Utils.round(r.DifY * r.Random()), numValues / (20 + r.Random()), numValues / (20 + r.Random()), r.DifY / (20 + r.Random()),
					r.DifY / (20 + r.Random()));
			r.tmpX += r.StepX;
		}
	}

	public int add(double XVal, double YVal, double LErr, double RErr, double TErr, double BErr, String text, Color color) {
		getErrors().getLeft().tempValue = LErr;
		getErrors().getRight().tempValue = RErr;
		getErrors().getTop().tempValue = TErr;
		getErrors().getBottom().tempValue = BErr;

		return add(XVal, YVal, text, color);
	}

	public int add(double x, double y, double LErr, double RErr, double TErr, double BErr, String text) {
		return add(x, y, LErr, RErr, TErr, BErr, text, Utils.getEmptyColor());
	}

	public int add(double x, double y, double LErr, double RErr, double TErr, double BErr) {
		return add(x, y, LErr, RErr, TErr, BErr, "", Utils.getEmptyColor());
	}

	public int add(double x, double y, double LErr, double RErr, double TErr, double BErr, Color color) {
		return add(x, y, LErr, RErr, TErr, BErr, "", color);
	}

	public int add(double y, double LErr, double RErr, double TErr, double BErr) {
		return add(getCount(), y, LErr, RErr, TErr, BErr, "", Utils.getEmptyColor());
	}

	private void drawLeftError() {
		int tmpX = getHorizAxis().calcSizeValue(errors.getLeft().getValue(iErrorPoint.ValueIndex));
		if ((errors.getFormat().getLeft().getVisible()) && (getPointer().getHorizSize() < tmpX)) {
			errors.preparePen(iErrorPoint.ValueIndex, errors.getFormat().getLeft());
			errors.drawError(iErrorPoint.XPos - Utils.round(getPointer().getHorizSize()), iErrorPoint.YPos,
					tmpX - Utils.round(getPointer().getHorizSize()), iErrorPoint.ErrorHorizSize, Utils.round(getMiddleZ()), true);
		}
	}

	private void drawRightError() {
		int tmpX = getHorizAxis().calcSizeValue(errors.getRight().getValue(iErrorPoint.ValueIndex));
		if ((errors.getFormat().getRight().getVisible()) && (getPointer().getHorizSize() < tmpX)) {
			errors.preparePen(iErrorPoint.ValueIndex, errors.getFormat().getRight());
			errors.drawError(iErrorPoint.XPos + Utils.round(getPointer().getHorizSize()), iErrorPoint.YPos,
					-(tmpX - Utils.round(getPointer().getHorizSize())), iErrorPoint.ErrorHorizSize, Utils.round(getMiddleZ()), true);
		}
	}

	private void drawTopError() {
		int tmpY = getVertAxis().calcSizeValue(errors.getTop().getValue(iErrorPoint.ValueIndex));
		if ((errors.getFormat().getTop().getVisible()) && (getPointer().getVertSize() < tmpY)) {
			errors.preparePen(iErrorPoint.ValueIndex, errors.getFormat().getTop());
			errors.drawError(iErrorPoint.XPos, iErrorPoint.YPos - Utils.round(getPointer().getVertSize()),
					tmpY - Utils.round(getPointer().getVertSize()), iErrorPoint.ErrorVertSize, Utils.round(getMiddleZ()), false);
		}
	}

	private void drawBottomError() {
		int tmpY = getVertAxis().calcSizeValue(errors.getBottom().getValue(iErrorPoint.ValueIndex));
		if ((errors.getFormat().getBottom().getVisible()) && (getPointer().getVertSize() < tmpY)) {
			errors.preparePen(iErrorPoint.ValueIndex, errors.getFormat().getBottom());
			errors.drawError(iErrorPoint.XPos, iErrorPoint.YPos + Utils.round(getPointer().getVertSize()),
					-(tmpY - Utils.round(getPointer().getVertSize())), iErrorPoint.ErrorVertSize, Utils.round(getMiddleZ()), false);
		}
	}

	private void drawWithOrder(boolean LeftFirst, boolean TopFirst) {
		if (LeftFirst) {
			drawLeftError();
			if (TopFirst) {
				drawTopError();
				super.drawValue(iErrorPoint.ValueIndex);
				drawBottomError();
			} else {
				drawBottomError();
				super.drawValue(iErrorPoint.ValueIndex);
				drawTopError();
			}
			drawRightError();
		} else {
			drawRightError();
			if (TopFirst) {
				drawTopError();
				super.drawValue(iErrorPoint.ValueIndex);
				drawBottomError();
			} else {
				drawBottomError();
				super.drawValue(iErrorPoint.ValueIndex);
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
			iErrorPoint.ErrorHorizSize = Utils.round(1.0 * errors.getSize() * getPointer().getHorizSize() * 0.01);
			iErrorPoint.ErrorVertSize = Utils.round(1.0 * errors.getSize() * getPointer().getVertSize() * 0.01);
		} else {
			iErrorPoint.ErrorHorizSize = errors.getSize();
			iErrorPoint.ErrorVertSize = errors.getSize();
		}
	}

	@Override
	public void drawValue(int valueIndex) {
		if (iErrorPoint == null)
			iErrorPoint = new IErrorPoint();
		iErrorPoint.XPos = Utils.round(calcXPosValue(getXValues().getValue(valueIndex)));
		iErrorPoint.YPos = Utils.round(calcYPosValue(getYValues().getValue(valueIndex)));
		calcErrorSize();
		iErrorPoint.ValueIndex = valueIndex;

		if ((getChart().getAspect().getOrthogonal()) || (!getChart().getAspect().getView3D())) {
			drawWithOrder(true, false);
		} else {
			if (getChart().getAspect().getRotation() % 360 <= 180) {
				if (getChart().getAspect().getElevation() % 360 <= 90)
					drawWithOrder(false, true);
				else if (getChart().getAspect().getElevation() % 360 <= 180)
					drawWithOrder(true, true);
				else if (getChart().getAspect().getElevation() % 360 <= 270)
					drawWithOrder(true, false);
				else
					drawWithOrder(false, false);
			} else {
				if (getChart().getAspect().getElevation() % 360 <= 90)
					drawWithOrder(true, true);
				else if (getChart().getAspect().getElevation() % 360 <= 180)
					drawWithOrder(false, true);
				else if (getChart().getAspect().getElevation() % 360 <= 270)
					drawWithOrder(false, false);
				else
					drawWithOrder(true, false);
			}
		}
	}

	public double getMaxXValue() {
		return calcMinMaxValue(false, true);
	}

	public double getMaxYValue() {
		return calcMinMaxValue(false, false);
	}

	public double getMinXValue() {
		return calcMinMaxValue(true, true);
	}

	public double getMinYValue() {
		return calcMinMaxValue(true, false);
	}

	@Override
	public String getDescription() {
		return Language.getString("GalleryErrorPoint");
	}

	private double calcMinMaxValue(boolean isMin, boolean isXVal) {
		boolean tmpfirst = true;
		Double result = 0.0;
		double tmpvalue = 0.0;
		for (int t = 0; t < getCount(); t++) {
			if (!isNull(t)) {
				if (isMin) {
					if (isXVal) {
						tmpvalue = notMandatory.getValue(t) - errors.getLeft().getValue(t);
					} else {
						tmpvalue = mandatory.getValue(t) - errors.getBottom().getValue(t);
					}
				} else {
					if (isXVal) {
						tmpvalue = notMandatory.getValue(t) + errors.getRight().getValue(t);
					} else {
						tmpvalue = mandatory.getValue(t) + errors.getTop().getValue(t);
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
		return clickedTopError() || clickedBottomError() || clickedLeftError() || clickedRightError();
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

	private boolean clickedHorizontal(int x0, int x1, int y, int t) {
		if (chart != null) {
			Graphics3D g = (Graphics3D) chart.getGraphics3D();
			Point P0 = g.calculate3DPosition(x0, y, getMiddleZ());
			Point P1 = g.calculate3DPosition(x1, y, getMiddleZ());
			return Graphics3D.pointInLineTolerance(iPoint, P0, P1, t);
		} else
			return false;
	}

	private boolean clickedVertical(int x, int y0, int y1, int t) {
		if (chart != null) {
			Graphics3D g = (Graphics3D) chart.getGraphics3D();
			Point P0 = g.calculate3DPosition(x, y0, getMiddleZ());
			Point P1 = g.calculate3DPosition(x, y1, getMiddleZ());
			return Graphics3D.pointInLineTolerance(iPoint, P0, P1, t);
		} else
			return false;
	}
}
