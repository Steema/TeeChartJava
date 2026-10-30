package com.steema.teechart.styles;

import java.text.DecimalFormat;
import java.util.ArrayList;

import com.steema.teechart.Chart;
import com.steema.teechart.IBaseChart;
import com.steema.teechart.PointDouble;
import com.steema.teechart.Rectangle;
import com.steema.teechart.axis.Axis;
import com.steema.teechart.drawing.ChartBrush;
import com.steema.teechart.drawing.ChartPen;
import com.steema.teechart.drawing.Color;
import com.steema.teechart.drawing.GradientDirection;
import com.steema.teechart.drawing.Graphics3D;
import com.steema.teechart.drawing.Point;
import com.steema.teechart.drawing.StringAlignment;
import com.steema.teechart.languages.Language;
import com.steema.teechart.misc.Utils;

public class CircularGauge extends Circular {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public CircularGauge() {
		this((IBaseChart) null);
	}

	public CircularGauge(IBaseChart c) {
		super(c);
		add(0);
		handDistance = 80;
		totalAngle = 300;
		maximum = 100;
		redLineStartValue = 80;
		redLineEndValue = 100;
		greenLineStartValue = 0;
		greenLineEndValue = 70;
		labelsInside = true;
		rotateLabels = true;
		labelsVisible = true;
		minorTickDistance = 3;
		handOffset = 30;
		setGaugeColorPalette(CustomGauge.BlackPalette);
		setCircled(true);
		fillToSemiCircle = true;
	}

	public void dispose() {
		setDefaultAxis();
		super.dispose();
	}
        
        public void assign(final Series source) {
        if (source instanceof CircularGauge) {
            CircularGauge sourceCircularGauge = (CircularGauge)source;
            this.handDistance = sourceCircularGauge.handDistance;
            this.totalAngle = sourceCircularGauge.totalAngle;
            this.maximum = sourceCircularGauge.maximum;
            this.redLineStartValue = sourceCircularGauge.redLineStartValue;
            this.redLineEndValue = sourceCircularGauge.redLineEndValue;
            this.greenLineStartValue = sourceCircularGauge.greenLineStartValue;
            this.greenLineEndValue = sourceCircularGauge.greenLineEndValue;
            this.labelsInside = sourceCircularGauge.labelsInside;
            this.rotateLabels = sourceCircularGauge.rotateLabels;
            this.labelsVisible = sourceCircularGauge.labelsVisible;
            this.handOffset = sourceCircularGauge.handOffset;
            this.fillToSemiCircle = sourceCircularGauge.fillToSemiCircle;
        }
        
        super.assign(source);
    }

	protected void addSampleValues(int numValues) {
		java.util.Random rnd = new java.util.Random();
		setValue(getMinimum() + (getMaximum() - getMinimum()) * rnd.nextDouble());
	}

	private void setAxisOnce() {
		if (isDefaultAxis()) {
			getAxis().getAxisPen().setVisible(true);
			getAxis().getLabels().setVisible(true);
			getAxis().getLabels().getFont().setColor(CustomGauge.getGaugePaletteColor(20, getGaugeColorPalette()));
			getAxis().getTitle().setVisible(true);
			getAxis().getTitle().getFont().setColor(CustomGauge.getGaugePaletteColor(21, getGaugeColorPalette()));
			getAxis().getLabels().getFont().setName("Arial");
			getAxis().getLabels().getFont().setSize(10);
			getAxis().getLabels().getFont().setBold(true);

		}
	}

	private void setDefaultAxis() {
		if (getAxis() != null) {
			getAxis().getAxisPen().setVisible(true);
			getAxis().getLabels().setVisible(true);
			getAxis().getLabels().getFont().setColor(Color.black);
			getAxis().getTitle().getFont().setColor(Color.black);
			getAxis().getTitle().setVisible(true);
			getAxis().getLabels().getFont().setBold(false);
			getAxis().getLabels().getFont().setSize(11);
			getAxis().getLabels().getFont().setName("Default");
			getAxis().getTicks().setColor(Color.DARK_GRAY);
			getAxis().getTicks().setLength(4);
			getAxis().getMinorTicks().setColor(Color.DARK_GRAY);
			getAxis().getMinorTicks().setLength(2);
		}
	}

	private boolean isDefaultAxis() {
		boolean result = getAxis() != null;

		if (result) {
			result = getAxis().getLabels().getVisible();
		}
		if (result) {
			result = getAxis().getLabels().getFont().getName().equals("Arial");
		}
		if (result) {
			result = getAxis().getLabels().getFont().getSize() == 10;
		}
		if (result) {
			result = getAxis().getLabels().getFont().getBold() == true;
		}
		if (result) {
			result = getAxis().getLabels().getFont().getItalic() == false;
		}
		if (result) {
			result = (getAxis().getLabels().getFont().getColor() == Color.fromArgb(255, 0, 0, 0) || (getAxis().getLabels().getFont().getColor() == Color.RED));
		}
		if (result) {
			result = getAxis().getLabels().getFont().getStrikeout() == false;
		}
		if (result) {
			result = getAxis().getLabels().getFont().getUnderline() == false;
		}
		if (result) {
			result = getAxis().getTitle().getVisible();
		}
		if (result) {
			result = getAxis().getTitle().getFont().getName().equals("Arial");
		}
		if (result) {
			result = getAxis().getTitle().getFont().getSize() == 10;
		}
		if (result) {
			result = getAxis().getTitle().getFont().getBold() == true;
		}
		if (result) {
			result = getAxis().getTitle().getFont().getItalic() == false;
		}
		if (result) {
			result = getAxis().getTitle().getFont().getStrikeout() == false;
		}
		if (result) {
			result = getAxis().getTitle().getFont().getUnderline() == false;
		}
		if (result) {
			result = getAxis().getAxisPen().getVisible();
		}
		if (result) {
			result = getAxis().getAxisPen().getWidth() == 2;
			// if (result) result = getAxis().getAxisPen().getStyle() == null;
		}
		if (result) {
			result = (getAxis().getLabels().getFont().getColor() == Color.fromArgb(255, 255, 0, 0) || (getAxis().getLabels().getFont().getColor() == Color.RED));
		}
		if (result) {
			result = (getAxis().getLabels().getFont().getColor() == Color.fromArgb(255, 255, 0, 0) || (getAxis().getLabels().getFont().getColor() == Color.RED));
		}
		if (result) {
			result = getAxis().getAxisPen().getDashCap() == com.steema.teechart.drawing.DashCap.FLAT;
			// if (result) result = getAxis().getAxisPen().getEndCap() == com.steema.teechart.drawing.LineCap.;
		}
		if (result) {
			result = getAxis().getAxisPen().getStyle() == com.steema.teechart.drawing.DashStyle.SOLID;
		}
		if (result) {
			result = getAxis().getAxisPen().getTransparency() == 0;
		}
		return result;
	}

	// public void doBeforeDrawChart()
	// {
	// setAxisOnce();
	// super.doBeforeDrawChart();
	// CalcOrigRectangle();
	// }
	public void doBeforeDrawValues() {
		setAxisOnce();
		super.doBeforeDrawValues();
		calcOrigRectangle();
	}

	public void setChart(IBaseChart value) {
		super.setChart(value);
		if (value != null) {
			setCircled(true);

			setShowInLegend(false);
			iCenter = new Point(0, 0);
			iNewRectangle = new Rectangle();

			getFrame().setChart(value);
		}
	}

	protected void setCircleRect() {
		if (!customBounds.isEmpty()) {
			rCircleRect = (Rectangle) customBounds.copy();
		} else {
			super.setCircleRect();
		}
	}

	protected Point iCenter = new Point();
	protected double iRange;
	protected float iStartAngle, iAngleInc;
	protected Rectangle iNewRectangle;
	private NumericGauge nGauge;
	private LinearGauge lGauge;
	public ArrayList iPoints = new ArrayList();
	private boolean autoPosNGauge = true;
	private boolean autoValNGauge = true;
	private boolean autoPosLGauge = true;
	private boolean autoValLGauge = true;
	private Rectangle customBounds = new Rectangle();
	private ChartPen nonVisiblePen = new ChartPen(Color.BLACK);
	private int displayTotalAngle = 360;
	private int displayRotationAngle = 0;

	public void draw() {
		if ((chart != null) && (getAxis() != null)) {
			setValues();
			drawFrame();
			Graphics3D g = (Graphics3D) prepareGraphics(getNonVisiblePen(), getFaceBrush());
			drawFace(g);
			g = (Graphics3D) prepareGraphics(getAxis().getAxisPen(), new ChartBrush(chart));
			drawAxis(g);
			drawNGauge(g);
			drawLGauge(g);
			g = (Graphics3D) prepareGraphics(getNonVisiblePen(), getHand().getBrush());
			drawHand(g);
		}
	}

	private void drawNGauge(Graphics3D g) {
		if (nGauge != null && nGauge.getVisible()) {

			if (getAutoValueNumericGauge()) {
				nGauge.setValue(getValue());
			}
			nGauge.setCustomBounds(calcNGaugeBounds());
			nGauge.draw(g);
		}
	}

	private void drawLGauge(Graphics3D g) {
		if (lGauge != null && lGauge.getVisible()) {
			if (getAutoValueLinearGauge()) {
				lGauge.setValue(this.getValue());
			}
			lGauge.setCustomBounds(calcLGaugeBounds());
			lGauge.draw(g);
		}
	}

	public void prepareForGallery(boolean isEnabled) {
		super.prepareForGallery(isEnabled);
		getAxis().setVisible(false);
		getCenter().setVertSize(3);
		center.setHorizSize(3);
		getHand().setHorizSize(2);
		handDistance = 100;
		getRedLine().setVertSize(5);
		setValue(70);
	}

	private void drawFrame() {
		if (frame.getVisible()) {
			frame.draw(rCircleRect);
		}
	}

	public LinearGauge getLinearGauge() {
		if (lGauge == null) {
			setTotalAngle(280);
			Axis axisH = new Axis();
			axisH.setHorizontal(true);
			axisH.getAxisPen().setVisible(false);
			Axis axisV = new Axis();
			axisV.setHorizontal(false);
			axisV.getAxisPen().setVisible(false);
			lGauge = new LinearGauge(getChart());
			lGauge.setHorizontalAxis(HorizontalAxis.CUSTOM);
			lGauge.setVerticalAxis(VerticalAxis.CUSTOM);
			lGauge.setHorizontal(false);
			lGauge.setVisible(false);
			lGauge.setCustomHorizAxis(axisH);
			lGauge.setCustomVertAxis(axisV);
			lGauge.getAxis().getTitle().setVisible(false);
			lGauge.getAxis().getLabels().getFont().setSize(8);
			lGauge.getAxis().getLabels().getFont().setColor(CustomGauge.getGaugePaletteColor(16, lGauge.getGaugeColorPalette()));
			lGauge.getAxis().setIncrement(20);
			lGauge.getRedLine().setVisible(false);
			lGauge.getGreenLine().setVisible(false);
			lGauge.getFrame().setVisible(false);
			lGauge.getFaceBrush().setVisible(false);
			lGauge.getMaxValueIndicator().setVisible(false);

		}
		lGauge.getFaceBrush().getGradient().setVisible(false);
		lGauge.getFaceBrush().setVisible(false);

		return lGauge;
	}

	public NumericGauge getNumericGauge() {
		if (nGauge == null) {
			setTotalAngle(280);
			Axis axisH = new Axis();
			axisH.setHorizontal(true);
			Axis axisV = new Axis();
			axisV.setHorizontal(false);
			nGauge = new NumericGauge((Chart) null);
			nGauge.setHorizontalAxis(HorizontalAxis.CUSTOM);
			nGauge.setCustomHorizAxis(axisH);
			nGauge.setVerticalAxis(VerticalAxis.CUSTOM);
			nGauge.setCustomVertAxis(axisV);
			nGauge.setHorizontal(true);
			nGauge.getValueMarker().getShape().getFont().setSize(10);
			nGauge.getValueMarker().setTextAlign(StringAlignment.FAR);
		}
		return nGauge;
	}

	public void setNumericGauge(NumericGauge value) {
		nGauge = value;
                invalidate();
	}

	public void setLinearGauge(LinearGauge value) {
		lGauge = value;
                invalidate();
	}

	public boolean getAutoValueNumericGauge() {
		return autoValNGauge;
	}

	public void setAutoValueNumericGauge(boolean value) {
		autoValNGauge = setBooleanProperty(autoValNGauge, value);
	}

	public boolean getAutoValueLinearGauge() {
		return autoValLGauge;
	}

	public void setAutoValueLinearGauge(boolean value) {
		autoValLGauge = setBooleanProperty(autoValLGauge, value);
	}

	public boolean getAutoPositionLinearGauge() {
		return autoPosLGauge;
	}

	public void setAutoPositionLinearGauge(boolean value) {
		autoPosLGauge = setBooleanProperty(autoPosLGauge, value);
	}

	public boolean getAutoPositionNumericGauge() {
		return autoPosNGauge;
	}

	public void setAutoPositionNumericGauge(boolean value) {
		autoPosNGauge = setBooleanProperty(autoPosNGauge, value);
	}

	public int getDisplayTotalAngle() {
		return displayTotalAngle;
	}

	public void setDisplayTotalAngle(int value) {
		displayTotalAngle = setIntegerProperty(displayTotalAngle, value);
		getFrame().setTotalAngle(displayTotalAngle);
	}

	public int getDisplayRotationAngle() {
		return displayRotationAngle;
	}

	public void setDisplayRotationAngle(int value) {
		displayRotationAngle = setIntegerProperty(displayRotationAngle, value);
		getFrame().setRotationAngle(displayRotationAngle);
	}

	protected void drawFace(Graphics3D g) {

		PointDouble RectCenter = new PointDouble();
		if (displayTotalAngle < 360) {
			iPoints.clear();
			RectCenter = getFrame().getRectCenter(iNewRectangle);
			iPoints.add(RectCenter);
			for (int i = displayRotationAngle; i <= (displayTotalAngle + displayRotationAngle); i++) {
				iPoints.add(g.pointFromEllipse(iNewRectangle, i, 0));
			}
			iPoints.add(RectCenter);
			getFrame().InnerBandPoints[2] = (PointDouble) iPoints.get(1);
			getFrame().InnerBandPoints[3] = (PointDouble) iPoints.get(iPoints.size() - 2);

			PointDouble[] p = new PointDouble[iPoints.size()];
			iPoints.toArray(p);
			g.polygon(p);
		} else {
			g.ellipse(iNewRectangle);
		}
		doFillToSemiCircle(g);
		drawColorLines(g);
	}

	protected void drawColorLines(Graphics3D g) {
		if (getRedLine().getVisible()) {
			Rectangle tmpR = (Rectangle) iNewRectangle.copy();
			double minRadius = Math.min(getXRadius(), getYRadius());
			minRadius /= 5;
			int tmp = (int) Math.round(minRadius) * -1;
			if (getAxis().getLabels().getVisible()) {
				if (getRotateLabels()) {
					tmp -= g.fontTextHeight(getAxis().getLabels().getFont());
				} else {
					tmp -= (int) g.textWidth(getAxis().getLabels().getFont(), String.valueOf(getMaximum()));
				}
			}
			tmpR.inflate(tmp, tmp);

			redLineStartValue = Math.max(minimum, redLineStartValue);
			redLineEndValue = Math.min(maximum, redLineEndValue);
			redLine.drawColorLine(g, calcValue(redLineStartValue), calcValue(redLineEndValue), tmpR);
		}
		if (getGreenLine().getVisible()) {
			// GreenLine.PrepareCanvas(g, GreenLine.Color); //CDI done in drawcolorline method
			Rectangle tmpR = (Rectangle) iNewRectangle.copy();
			double minRadius = Math.min(getXRadius(), getYRadius());
			minRadius /= 5;
			int tmp = (int) Math.round(minRadius) * -1;
			if (getAxis().getLabels().getVisible()) {
				if (getRotateLabels()) {
					tmp -= g.fontTextHeight(getAxis().getLabels().getFont());
				} else {
					tmp -= (int) g.textWidth(getAxis().getLabels().getFont(), String.valueOf(maximum));
				}
			}
			tmpR.inflate(tmp, tmp);

			greenLineStartValue = Math.max(minimum, greenLineStartValue);
			greenLineEndValue = Math.min(maximum, greenLineEndValue);
			greenLine.drawColorLine(g, calcValue(greenLineStartValue), calcValue(greenLineEndValue), tmpR);
		}
	}

	protected void setValues() {
		iCenter.x = getCircleXCenter();
		iCenter.y = getCircleYCenter();
		iRange = maximum - minimum;
		iStartAngle = calcStartAngle();
		iAngleInc = (float) totalAngle / (float) iRange;
                invalidate();
	}

	// returns the angle needed to draw the length on the circumference of the circle from a point
	protected double calcAngleFromLength(Point Point, double Length) {
		double result = 0;
		double tmpX, tmpY, radius, tmp;
		if (getCircled()) {
			radius = (iNewRectangle.width / 2);
		} else {
			tmpX = Math.abs(iCenter.x - Point.x);
			tmpY = Math.abs(iCenter.y - Point.y);
			radius = Math.sqrt((tmpX * tmpX) + (tmpY * tmpY));
		}

		// law of cosines
		tmp = (radius * radius) * 2;
		result = tmp - (Length * Length);
		result /= tmp;
		return Math.acos(result);
	}

	protected Point calcPoint(double angle, Point center, double radiusX, double radiusY) {
		Point result = new Point(0, 0);
		double tmpSin, tmpCos;

		tmpSin = Math.sin(angle);
		tmpCos = Math.cos(angle);

		result.x = (center.x - Utils.round(radiusX * tmpCos));
		result.y = (center.y - Utils.round(radiusY * tmpSin));

		return result;
	}

	protected void drawAxisTick(Graphics3D g, Point inner, Point innerPlus, Point innerMinus, Point outer, Point outerPlus, Point outerMinus) {
		// Ticks.PrepareCanvas(g, Ticks.Color); //CDI done in draw method
		getTicks().draw(g, inner, innerPlus, innerMinus, outer, outerPlus, outerMinus);
	}

	protected void drawAxisMinorTick(Graphics3D g, Point Inner, Point Outer) {
		// MinorTicks.PrepareCanvas(g, MinorTicks.Color); //CDI done in draw method
		getMinorTicks().draw(g, Inner, new Point(0, 0), new Point(0, 0), Outer, new Point(0, 0), new Point(0, 0));
	}

	protected void drawAxis(Graphics3D g) {
		double tmp;
		String tmpS;
		double tmpValue, tmpStep, tmpStep2, tmpAngle, tmpAngle2;
		double tmpXRad, tmpYRad, tmpWidth;
		int tmpFontH, t;
		Point P3, P4;

		if (getAxis() != null && getAxis().getVisible()) {

			getAxis().getAxisPen().setVisible(false);

			tmpStep = getAxis().getIncrement();
			if (tmpStep == 0) {
				tmpStep = 10;
			}
			if (getAxis().getTitle().getVisible() && getAxis().getTitle().getCaption().length() > 0) {
				g.setFont(getAxis().getTitle().getFont());

				float width = g.textWidth(getAxis().getTitle().getCaption());
				float height = g.getFontHeight();
				int x = (int) (iCenter.x - Math.round(((iNewRectangle.width / 2) / 2.5) + (width / 2.0)));
				int y = (int) (iCenter.y - Math.round(height / 2.0));

				g.textOut(x, y, getAxis().getTitle().getCaption());
			}

			if (getAxis().getTicks().getVisible() || getAxis().getLabels().getVisible()) {
				getAxis().getTicks().setLength(getTicks().getVertSize());
				getAxis().getTicks().assign(getTicks().getPen());
				g.setFont(getAxis().getLabels().getFont());
				g.setPen(getTicks().getPen());

				tmpXRad = ((iNewRectangle.width / 2) - getAxis().getTicks().getLength());
				tmpYRad = ((iNewRectangle.height / 2) - getAxis().getTicks().getLength());

				tmpFontH = g.getFontHeight();

				if (iRange != 0 && tmpStep != 0) {
					tmpValue = 0;

					do {
						tmpAngle = (iStartAngle - 90) + (tmpValue * totalAngle / iRange);
						tmp = Utils.PISTEP * tmpAngle;

						P3 = calcPoint(tmp, iCenter, tmpXRad, tmpYRad);
						P4 = calcPoint(tmp, iCenter, (iNewRectangle.width / 2), (iNewRectangle.height / 2));

						double tmp2 = Utils.PISTEP * (tmpAngle + (getTicks().getHorizSize() * 0.1));

						Point P3Plus = calcPoint(tmp2, iCenter, tmpXRad, tmpYRad);
						tmp2 = Utils.PISTEP * (tmpAngle - (getTicks().getHorizSize() * 0.1));
						Point P3Minus = calcPoint(tmp2, iCenter, tmpXRad, tmpYRad);

						tmp2 = Utils.PISTEP * (tmpAngle + (getTicks().getHorizSize() * 0.2));
						Point P4Plus = calcPoint(tmp2, iCenter, (iNewRectangle.getWidth() / 2), (iNewRectangle.getHeight() / 2));
						tmp2 = Utils.PISTEP * (tmpAngle - (getTicks().getHorizSize() * 0.2));
						Point P4Minus = calcPoint(tmp2, iCenter, (iNewRectangle.getWidth() / 2), (iNewRectangle.height / 2));
						drawAxisTick(g, P3, P3Plus, P3Minus, P4, P4Plus, P4Minus);
						int tmpX, tmpY, tmpZ = 0;

						boolean tmpDraw = true;

						if (getAxis().getLabels().getVisible()) {
							DecimalFormat valuesDecimal = new DecimalFormat(getAxis().getLabels().getValueFormat());
							tmpS = valuesDecimal.format(tmpValue + getMinimum());
							tmpS = chart.getParent().getAxisLabelResolver().getLabel(getAxis(), this, -1, tmpS);
							tmpWidth = g.textWidth(tmpS);

							if (getRotateLabels()) {
								tmpAngle2 = calcAngleFromLength(P4, tmpWidth) / 2.0;
								if (getLabelsInside()) {
									P3 = calcPoint(tmp - tmpAngle2, iCenter, tmpXRad - (tmpFontH / 4), tmpYRad - (tmpFontH / 4));
								} else {
									P3 = calcPoint(tmp - tmpAngle2, iCenter, (iNewRectangle.width / 2) + tmpFontH, (iNewRectangle.height / 2) + tmpFontH);
								}

								tmpX = P3.x;
								tmpY = P3.y;
								tmpZ = 0;
								if (tmpDraw) {
									g.rotateLabel(tmpX, tmpY, tmpS, 90 - tmpAngle);
								}
							} else {
								if (getLabelsInside()) {
									P3 = calcPoint(tmp, iCenter, tmpXRad - (tmpFontH / 2), tmpYRad - (tmpFontH / 2));

									tmpAngle2 = tmpAngle - 90;
									if (tmpAngle2 < 0) {
										tmpAngle2 = tmpAngle2 * -1;
									}
									P3.y -= Math.round(tmpFontH * ((1.0 / 180.0) * tmpAngle2));
								} else {
									P3 = calcPoint(tmp, iCenter, (iNewRectangle.width / 2) + tmpFontH, (iNewRectangle.height / 2) + tmpFontH);
								}

								tmpAngle2 = tmpAngle;
								if (tmpAngle2 > 180) {
									tmpAngle2 = 180 - (tmpAngle2 - 180);
								}
								if (tmpAngle2 < 0) {
									tmpAngle2 *= -1;
								}
								P3.x -= Math.round(tmpWidth * ((1.0 / 180.0) * tmpAngle2));

								tmpX = P3.x;
								tmpY = P3.y;
								tmpZ = 0;
								if (tmpDraw) {
									g.textOut(P3.x, P3.y, tmpS);
								}
							}
						}
						tmpValue += tmpStep;
					} while ((tmpValue <= iRange && totalAngle < 360) || (tmpValue < iRange && totalAngle >= 360)); // TV52014201
				}
			}

			if (iRange != 0 && getAxis().getMinorTicks().getVisible() && getAxis().getMinorTickCount() > 0) {
				getAxis().getMinorTicks().setLength(getMinorTicks().getVertSize());
				getAxis().getMinorTicks().assign(getMinorTicks().getPen());
				g.setPen(getMinorTicks().getPen());

				tmpXRad = (iNewRectangle.width / 2) - getAxis().getMinorTicks().getLength() - getMinorTickDistance();
				tmpYRad = (iNewRectangle.height / 2) - getAxis().getMinorTicks().getLength() - getMinorTickDistance();

				if (tmpStep != 0) {
					tmpStep2 = tmpStep / (getAxis().getMinorTickCount() + 1);
					tmpValue = 0;

					do {
						for (t = 1; t <= getAxis().getMinorTickCount(); ++t) {
							tmpAngle = (iStartAngle - 90) + ((tmpValue + t * tmpStep2) * totalAngle / iRange);
							tmp = Utils.PISTEP * tmpAngle;

							drawAxisMinorTick(g, calcPoint(tmp, iCenter, tmpXRad, tmpYRad), calcPoint(tmp, iCenter, (iNewRectangle.width / 2) - getMinorTickDistance(), (iNewRectangle.height / 2) - getMinorTickDistance()));
						}
						tmpValue = tmpValue + tmpStep;
					} while (tmpValue <= (iRange - tmpStep));
				}
			}
		}
	}

	protected float calcSweepAngle() {
		return (float) totalAngle;
	}

	// private float CalcStartAngle()
	// {
	// double tmp = 360.0f - TotalAngle;
	// tmp = (tmp / 2.0) + RotationAngle;
	// return (float)tmp;
	// }
	private int calcStartAngle() {
		int tmp = (360 - Utils.round(totalAngle));
		tmp = (Utils.round(tmp / 2.0) + getRotationAngle());
		return tmp;
	}

	private Graphics3D prepareGraphics(ChartPen pen, ChartBrush brush) {
		Graphics3D result = (Graphics3D) chart.getGraphics3D();
		result.setPen(pen);
		result.setBrush(brush);
		return result;
	}

	// private float CalcValue(float value)
	// {
	// float result = (float)value;
	// result *= IAngleInc;
	// result += IStartAngle;
	// return result;
	// }
	protected int calcValue(double value) {
		// int result = Utils.Round(value); //CDI TF02013062
		double result = value - minimum;
		result *= iAngleInc;
		result += iStartAngle;
		return Utils.round(result);
	}

	private int calcDistance(int distance) {
		if (distance > 0) {
			double minRadius = Math.min((iNewRectangle.width / 2), (iNewRectangle.height / 2));
			minRadius /= 100.0;
			return Utils.round((distance * minRadius) / 2.0);
		} else {
			return 0;
		}
	}

	private Rectangle calcLGaugeBounds() {
		Rectangle result;

		if (autoPosLGauge) {
			if (lGauge.getHorizontal()) {
				result = Rectangle.fromLTRB(iCenter.x - (iNewRectangle.width / 7), iCenter.y + (iNewRectangle.height / 3), iCenter.x + (iNewRectangle.width / 7), (iCenter.y + Utils.round(iNewRectangle.height / 2.25)));
			} else {
				result = Rectangle.fromLTRB(iCenter.x - (iNewRectangle.width / 9), iCenter.y + (iNewRectangle.height / 6), iCenter.x + (iNewRectangle.width / 9), (iCenter.y + Utils.round(iNewRectangle.height / 2.25)));
			}
		} else {
			result = lGauge.getCustomBounds();
		}

		return result;
	}

	public Rectangle getCustomBounds() {
		return customBounds;
	}

	public void setCustomBounds(Rectangle value) {
		customBounds = value;
	}

	private Rectangle calcNGaugeBounds() {
		Rectangle result;

		if (autoPosNGauge) {
			result = Rectangle.fromLTRB(iCenter.x - (iNewRectangle.width / 7), iCenter.y + (iNewRectangle.height / 3), iCenter.x + (iNewRectangle.width / 7), (iCenter.y + Utils.round(iNewRectangle.height / 2.25)));
		} else {
			result = nGauge.getCustomBounds();
		}

		return result;
	}

	protected void calcOrigRectangle() {

		iNewRectangle = (Rectangle) getCircleRect().copy();
		if (getFrame().getVisible()) {
			iNewRectangle.inflate(-getFrame().calcWidth(iNewRectangle), -getFrame().calcWidth(iNewRectangle));
		}
	}

	private void doFillToSemiCircle(Graphics3D g) {
		if (getHand().getVisible() && displayTotalAngle < 360) {
			int maxRadius;
			Rectangle R;

			maxRadius = getCenter().getVisible() ? Math.max(getCenter().getVertSize(), getCenter().getHorizSize()) : 0;
			maxRadius += getCenter().getShadow().getVisible() ? Math.max(getCenter().getShadow().getHeight(), getCenter().getShadow().getWidth()) : 0;
			maxRadius = Math.max(calcDistance(getHandOffset()) * 2, maxRadius);
			maxRadius = (int) Math.round(maxRadius * 1.3);

			R = Rectangle.fromLTRB(iCenter.x - maxRadius, iCenter.y - maxRadius, iCenter.x + maxRadius, iCenter.y + maxRadius);
			iPoints.clear();

			PointDouble RectCenter = getFrame().getRectCenter(R);
			iPoints.add(RectCenter);
			int startAngle = displayRotationAngle;
			int endAngle = displayTotalAngle + displayRotationAngle;
			int sweep = 360 - (endAngle - startAngle);

			for (int i = endAngle; i <= endAngle + sweep; i++) {
				iPoints.add(g.pointFromCircle(R, i));
			}

			iPoints.add(RectCenter);
			boolean oldVisible = g.getBrush().getGradientVisible();
			g.getBrush().getGradient().setVisible(false);

			Color oldColor = g.getBrush().getColor();
			if (oldVisible) {
				g.getBrush().setColor(g.getBrush().getGradient().getStartColor());
			}
			PointDouble[] p = new PointDouble[iPoints.size()];
			iPoints.toArray(p);
			g.polygon(p);
			g.getBrush().setColor(oldColor);
			g.getBrush().getGradient().setVisible(oldVisible);

			if (fillToSemiCircle) {
				if (getFrame().getVisible()) {
					doInnerBand(g, R, RectCenter);
					doMiddleBand(g, R, RectCenter);
					doOuterBand(g, R, RectCenter);
				}
			}
		}
	}

	private void doInnerBand(Graphics3D g, Rectangle R, PointDouble RectCenter) {
		iPoints.clear();

		R.inflate(-1, -1);

		double angle1 = drawTangent(g, getFrame().InnerBandPoints[3], R, true);
		if (angle1 < 0) {
			angle1 = 360 + angle1;
		}
		double angle2 = drawTangent(g, getFrame().InnerBandPoints[2], R, false);
		if (angle2 < 0) {
			angle2 = 360 + angle2;
		}
		iPoints.add(getFrame().InnerBandPoints[3]);

		for (double i = angle1; i <= angle2; i += 0.1) {
			iPoints.add(g.pointFromCircle(R, (double) i));
		}

		iPoints.add(getFrame().InnerBandPoints[2]);

		int width = (int) Math.round(getFrame().BandRadius[2] - iNewRectangle.width / 2.0);
		int height = width;

		R.inflate(width, height);

		double angle3 = drawTangent(g, getFrame().InnerBandPoints[1], R, true);
		if (angle3 < 0) {
			angle3 = 360 + angle3;
		}
		double angle4 = drawTangent(g, getFrame().InnerBandPoints[0], R, false);
		if (angle4 < 0) {
			angle4 = 360 + angle4;
		}
		iPoints.add(getFrame().InnerBandPoints[0]);

		for (double i = angle4; i >= angle3; i -= 0.1) {
			iPoints.add(g.pointFromCircle(R, i));
		}

		iPoints.add(getFrame().InnerBandPoints[1]);

		g = prepareGraphics(getNonVisiblePen(), getFrame().getInnerBand());
		PointDouble[] p = new PointDouble[iPoints.size()];
		iPoints.toArray(p);
		g.polygon(p);

		R.inflate(-width, -height);

		boolean straight = angle1 > angle2 && angle3 > angle4;

		iPoints.clear();

		g = prepareGraphics(getNonVisiblePen(), getFaceBrush());
		boolean oldVisible = g.getBrush().getGradientVisible();
		g.getBrush().getGradient().setVisible(false);

		Color oldColor = g.getBrush().getColor();
		if (oldVisible) {
			g.getBrush().setColor(g.getBrush().getGradient().getStartColor());
		}

		g.getPen().setColor(g.getBrush().getColor()); // fill seam
		g.getPen().setWidth(1);
		g.getPen().setVisible(true);

		if (straight) {
			iPoints.add(Utils.offsetPoint(getFrame().InnerBandPoints[3], 0, 0.5));
			iPoints.add(Utils.offsetPoint(getFrame().InnerBandPoints[3], 0, -0.5));
			iPoints.add(RectCenter);
			iPoints.add(Utils.offsetPoint(getFrame().InnerBandPoints[2], 0, -0.5));
			iPoints.add(Utils.offsetPoint(getFrame().InnerBandPoints[2], 0, 0.5));
			p = new PointDouble[iPoints.size()];
			iPoints.toArray(p);
			g.polygon(p);

		} else {
			iPoints.add(getFrame().InnerBandPoints[3]);
			iPoints.add(RectCenter);
			iPoints.add(g.pointFromCircle(R, angle1));
			p = new PointDouble[iPoints.size()];
			iPoints.toArray(p);
			g.polygon(p);
			iPoints.clear();
			iPoints.add(getFrame().InnerBandPoints[2]);
			iPoints.add(RectCenter);
			iPoints.add(g.pointFromCircle(R, angle2));
			p = new PointDouble[iPoints.size()];
			iPoints.toArray(p);
			g.polygon(p);

		}

		R.inflate(width, height);

		g.getPen().setVisible(false);
		g.getBrush().setColor(oldColor);
		g.getBrush().getGradient().setVisible(oldVisible);
	}

	private void doMiddleBand(Graphics3D g, Rectangle R, PointDouble RectCenter) {
		iPoints.clear();
		R.inflate(-1, -1);
		double angle1 = drawTangent(g, getFrame().MiddleBandPoints[3], R, true);
		if (angle1 < 0) {
			angle1 = 360 + angle1;
		}
		double angle2 = drawTangent(g, getFrame().MiddleBandPoints[2], R, false);
		if (angle2 < 0) {
			angle2 = 360 + angle2;
		}
		boolean straight = angle1 > angle2;

		if (straight) {
			iPoints.add(Utils.offsetPoint(getFrame().MiddleBandPoints[3], 0, -0.5));
		} else {
			iPoints.add(getFrame().MiddleBandPoints[3]);
		}

		for (double i = angle1; i <= angle2; i += 0.1) {
			iPoints.add(g.pointFromCircle(R, i));
		}

		if (straight) {
			iPoints.add(Utils.offsetPoint(getFrame().MiddleBandPoints[2], 0, -0.5));
		} else {
			iPoints.add(getFrame().MiddleBandPoints[2]);
		}

		int width = (int) Math.round(getFrame().BandRadius[1] - getFrame().BandRadius[2]);
		int height = width;

		R.inflate(width, height);

		double angle3 = drawTangent(g, getFrame().MiddleBandPoints[1], R, true);
		if (angle3 < 0) {
			angle3 = 360 + angle3;
		}
		double angle4 = drawTangent(g, getFrame().MiddleBandPoints[0], R, false);
		if (angle4 < 0) {
			angle4 = 360 + angle4;
		}
		straight = angle3 > angle4;

		if (straight) {
			iPoints.add(Utils.offsetPoint(getFrame().MiddleBandPoints[0], 0, 0.5));
		} else {
			iPoints.add(getFrame().MiddleBandPoints[0]);
		}

		for (double i = angle4; i >= angle3; i -= 0.1) {
			iPoints.add(g.pointFromCircle(R, i));
		}

		if (straight) {
			iPoints.add(Utils.offsetPoint(getFrame().MiddleBandPoints[1], 0, 0.5));
		} else {
			iPoints.add(getFrame().MiddleBandPoints[1]);
		}

		g = prepareGraphics(getNonVisiblePen(), getFrame().getMiddleBand());

		PointDouble[] p = new PointDouble[iPoints.size()];
		iPoints.toArray(p);
		g.polygon(p);

	}

	private void doOuterBand(Graphics3D g, Rectangle R, PointDouble RectCenter) {
		iPoints.clear();
		R.inflate(-1, -1);

		double angle1 = drawTangent(g, getFrame().OuterBandPoints[3], R, true);
		if (angle1 < 0) {
			angle1 = 360 + angle1;
		}
		double angle2 = drawTangent(g, getFrame().OuterBandPoints[2], R, false);
		if (angle2 < 0) {
			angle2 = 360 + angle2;
		}
		iPoints.add(getFrame().OuterBandPoints[3]);

		for (double i = angle1; i <= angle2; i += 0.1) {
			iPoints.add(g.pointFromCircle(R, i));
		}

		iPoints.add(getFrame().OuterBandPoints[2]);

		int width = (int) Math.round(getFrame().BandRadius[0] - getFrame().BandRadius[1]);
		int height = width;

		R.inflate(width, height);

		double angle3 = drawTangent(g, getFrame().OuterBandPoints[1], R, true);
		if (angle3 < 0) {
			angle3 = 360 + angle3;
		}
		double angle4 = drawTangent(g, getFrame().OuterBandPoints[0], R, false);
		if (angle4 < 0) {
			angle4 = 360 + angle4;
		}
		iPoints.add(getFrame().OuterBandPoints[0]);

		for (double i = angle4; i >= angle3; i -= 0.1) {
			iPoints.add(g.pointFromCircle(R, i));
		}

		iPoints.add(getFrame().OuterBandPoints[1]);
		g = prepareGraphics(getNonVisiblePen(), getFrame().getOuterBand());
		PointDouble[] p = new PointDouble[iPoints.size()];
		iPoints.toArray(p);
		g.polygon(p);

	}

	private double drawTangent(Graphics3D graphics, PointDouble from, Rectangle circle, boolean rightSide) {
		int x, y, radius;
		Point p;
		p = Graphics3D.rectCenter(circle);
		x = p.x;
		y = p.y;
		Point circleCenter = new Point(x, y);
		radius = circle.width / 2;
		double adjacent = from.y - circleCenter.y;
		double opposite = from.x - circleCenter.x;
		double tmp = Utils.sqr(opposite) + Utils.sqr(adjacent);
		double hypotenuse = Math.sqrt(tmp);
		double innerAngle = Math.acos(radius / hypotenuse);
		innerAngle = Utils.rad2Deg(innerAngle);
		double outerAngle = Math.acos(opposite / hypotenuse);
		outerAngle = Utils.rad2Deg(outerAngle);
		double PT = Math.sqrt(Utils.sqr(hypotenuse) - Utils.sqr(radius));
		if (from.y > circleCenter.y) {
			outerAngle = 360 - outerAngle;
		}
		double result = rightSide ? outerAngle + innerAngle : outerAngle - innerAngle;

		return result;
	}

	private void drawHand(Graphics3D g) {
		try {
			if (getHand().getVisible()) {
				float angle = calcValue((float) getValue());

				drawCenterShadow(g);

				Point p = new Point(getCircleXCenter(), getCircleYCenter());
				g.rotateTransform(p, 360 - angle);

				hand.setVertSize(calcDistance(handDistance));
				hand.drawShadow(g, angle, p.x, p.y + hand.getVertSize() - calcDistance(handOffset), hand.getHorizSize(), hand.getVertSize() + calcDistance(handOffset), hand.getGaugePointerStyle());
				hand.draw(g, false, p.x, p.y + hand.getVertSize() - calcDistance(handOffset), hand.getHorizSize(), hand.getVertSize() + calcDistance(handOffset), hand.getBrush().getColor(), hand.getGaugePointerStyle());
			}
		} finally {
			g.resetTransform();
		}

		drawEnd(g);
		drawCenter(g);
	}

	private ChartPen getNonVisiblePen() {
		nonVisiblePen.setVisible(false);
		return nonVisiblePen;
	}

	private void drawCenterShadow(Graphics3D g) {
		if (getCenter().getVisible()) {
			center.drawShadow(g, 0, getCircleXCenter(), getCircleYCenter(), center.getHorizSize(), center.getVertSize(), center.getGaugePointerStyle());
		}
	}

	protected void drawCenter(Graphics3D g) {
		if (getCenter().getVisible()) {
			center.draw(g, false, getCircleXCenter(), getCircleYCenter(), center.getHorizSize(), center.getVertSize(), center.getBrush().getColor(), center.getGaugePointerStyle());
		}
	}

	private void drawEnd(Graphics3D g) {
		if (getEndPoint().getVisible()) {
			endPoint.draw(getCircleXCenter(), (getCircleYCenter() + (hand.getVertSize() * 2) + calcDistance(handOffset)), endPoint.getBrush().getColor(), endPoint.getStyle());
		}
	}

	private int sizePointer(SeriesPointer APointer) {
		int result;
		if (APointer.getVisible()) {
			result = 2 * APointer.getVertSize();
			if (APointer.getPen().getVisible()) {
				result = result + APointer.getPen().getWidth();
			}
		} else {
			result = 0;
		}
		return result;
	}

	protected void onValueChanged() {
		// if (valueChanged != null) valueChanged(this, e);
	}

	/*
	 * Called when the Gauge <see cref="CircularGauge.Value"/> property is changed.
	 */
	// public event GaugesChangeHandler valueChanged;

	/*
	 * Gets descriptive text.
	 */
	public String getDescription() {
		return Language.getString("GalleryCircularGauge");
	}

	private GaugeSeriesPointer center;
	private SeriesPointer endPoint;
	private int handDistance;
	private boolean labelsVisible;
	private boolean labelsInside;
	protected double totalAngle;
	private double maximum;
	private double minimum;
	private int minorTickDistance;
	private ChartBrush faceBrush;
	private GaugeSeriesPointer hand;
	private boolean rotateLabels;
	private boolean fillToSemiCircle;
	private CFrame frame;
	private GaugeSeriesPointer ticks;
	private GaugeSeriesPointer minorTicks;
	private GaugeSeriesPointer redLine;
	private GaugeSeriesPointer greenLine;
	private double redLineStartValue;
	private double redLineEndValue;
	private double greenLineStartValue;
	private double greenLineEndValue;
	private Color[] gaugeColorPalette;
	private int handOffset;

	/*
	 * Defines the pixel distance between the end of the hand and its center of rotation.
	 */
	public int getHandOffset() {
		return handOffset;
	}

	public void setHandOffset(int value) {
		if (handOffset != value) {
			handOffset = value;
			invalidate();
		}
	}

	/*
	 * Gets and sets the CircularGauge's color palette.
	 */
	public Color[] getGaugeColorPalette() {
		if (gaugeColorPalette == null || gaugeColorPalette.length == 0) {
			gaugeColorPalette = chart.getGraphics3D().getColorPalette();
		}
		return gaugeColorPalette;
	}

	public void setGaugeColorPalette(Color[] value) {
		gaugeColorPalette = value;
		getFrame().setGaugeColorPalette(gaugeColorPalette);
		redLine = null;
		greenLine = null;
		hand = null;
		center = null;
		faceBrush = null;
		invalidate();
	}

	/*
	 * Gets and sets the value at which the 'redline' marker ends.
	 */
	public double getGreenLineEndValue() {
		return greenLineEndValue;
	}

	public void setGreenLineEndValue(double value) {
		if (greenLineEndValue != value) {
			greenLineEndValue = value;
			invalidate();
		}
	}

	/*
	 * Gets and sets the value at which the 'redline' marker starts.
	 */
	public double getGreenLineStartValue() {
		return greenLineStartValue;
	}

	public void setGreenLineStartValue(double value) {
		if (greenLineStartValue != value) {
			greenLineStartValue = value;
			invalidate();
		}
	}

	/*
	 * Gets and sets the value at which the 'redline' marker ends.
	 */
	public double getRedLineEndValue() {
		return redLineEndValue;
	}

	public void setRedLineEndValue(double value) {
		if (redLineEndValue != value) {
			redLineEndValue = value;
			invalidate();
		}
	}

	/*
	 * Gets and sets the value at which the 'redline' marker starts.
	 */
	public double getRedLineStartValue() {
		return redLineStartValue;
	}

	public void setRedLineStartValue(double value) {
		if (redLineStartValue != value) {
			redLineStartValue = value;
			invalidate();
		}
	}

	/*
	 * The 'redline' object is the curved shape with a gradient which is used to signal a high or maximum value.
	 */
	public GaugeSeriesPointer getRedLine() {
		if (redLine == null) {
			redLine = new GaugeSeriesPointer(chart, this);
			redLine.setGaugePointerStyle(GaugePointerStyles.COLORLINE);
			redLine.setVertSize(3);
			redLine.getBrush().setVisible(true);
			redLine.getBrush().setColor(Color.BLACK);
			// redLine.getGradient().setSigma(true);
			// redLine.getGradient().setSigmaFocus(0.0f);
			// redLine.getGradient().setSigmaScale(1.0f);
			redLine.getGradient().setVisible(true);
			redLine.getGradient().setStartColor(CustomGauge.getGaugePaletteColor(6, getGaugeColorPalette()));
			redLine.getGradient().setMiddleColor(CustomGauge.getGaugePaletteColor(7, getGaugeColorPalette()));
			redLine.getGradient().setEndColor(CustomGauge.getGaugePaletteColor(8, getGaugeColorPalette()));

		}
		return redLine;
	}

	public void setRedLine(GaugeSeriesPointer value) {
		redLine = value;
                invalidate();
	}

	/*
	 * The 'greenline' object is the curved shape with a gradient which is used to signal a low or minimum value.
	 */
	public GaugeSeriesPointer getGreenLine() {
		if (greenLine == null) {
			greenLine = new GaugeSeriesPointer(chart, this);
			greenLine.setGaugePointerStyle(GaugePointerStyles.COLORLINE);
			greenLine.setVertSize(3);
			greenLine.getBrush().setVisible(true);
			greenLine.getBrush().setColor(Color.BLACK);
			// greenLine.getGradient().setSigma(true);
			// greenLine.getGradient().setSigmaFocus(0.0f);
			// greenLine.getGradient().setSigmaScale(1.0f);
			greenLine.getGradient().setVisible(true);
			greenLine.getGradient().setStartColor(CustomGauge.getGaugePaletteColor(9, getGaugeColorPalette()));
			greenLine.getGradient().setMiddleColor(CustomGauge.getGaugePaletteColor(10, getGaugeColorPalette()));
			greenLine.getGradient().setEndColor(CustomGauge.getGaugePaletteColor(11, getGaugeColorPalette()));
		}
		return greenLine;
	}

	public void setGreenLine(GaugeSeriesPointer value) {
		greenLine = value;
	}

	/*
	 * Gets and sets the characteristics of the getAxis()' minor tick marks.
	 */
	public GaugeSeriesPointer getMinorTicks() {
		if (minorTicks == null) {
			minorTicks = new GaugeSeriesPointer(chart, this);
			minorTicks.setGaugePointerStyle(GaugePointerStyles.MINORTICK);
			minorTicks.setColor(Color.TRANSPARENT);
			minorTicks.setVertSize(1);
			minorTicks.setHorizSize(1);
			minorTicks.getPen().setColor(CustomGauge.getGaugePaletteColor(23, getGaugeColorPalette()));
		}
		return minorTicks;
	}

	public void setMinorTicks(GaugeSeriesPointer value) {
		minorTicks = value;
                invalidate();
	}

	/*
	 * Gets and sets the characteristics of the getAxis()' major tick marks.
	 */
	public GaugeSeriesPointer getTicks() {
		if (ticks == null) {
			ticks = new GaugeSeriesPointer(chart, this);
			ticks.setGaugePointerStyle(GaugePointerStyles.TICK);
			ticks.setColor(Color.TRANSPARENT);
			ticks.setVertSize(12);
			ticks.getPen().setColor(CustomGauge.getGaugePaletteColor(22, getGaugeColorPalette()));
		}
		return ticks;
	}

	public void setTicks(GaugeSeriesPointer value) {
		ticks = value;
                invalidate();
	}

	/*
	 * The frame is the object which surrounds the CircularGauge and which consists of three configurable Bands: Inner, Middle and Outer.
	 */
	public CFrame getFrame() {
		if (frame == null) {
			frame = new CFrame(chart, getGaugeColorPalette());
		}
		return frame;
	}

	public void setFrame(CFrame value) {
		frame = value;
	}

	/*
	 * When true, labels are rotated so to appear parallel to the curved CircularGauge getAxis().
	 */
	public boolean getRotateLabels() {
		return rotateLabels;
	}

	public void setRotateLabels(boolean value) {
		if (rotateLabels != value) {
			rotateLabels = value;
			invalidate();
		}
	}

	/*
	 * Gets and sets the hand (or needle) object characteristics.
	 */
	public GaugeSeriesPointer getHand() {
		if (hand == null) {
			hand = new GaugeSeriesPointer(chart, this);
			hand.setVisible(true);
			hand.setGaugePointerStyle(GaugePointerStyles.HAND);
			hand.setHorizSize(5);
			hand.setVertSize(handDistance);
			hand.getBrush().setVisible(true);
			hand.getBrush().setDefaultVisible(true);
			hand.getBrush().setColor(Color.BLACK);
			hand.getGradient().setDirection(GradientDirection.HORIZONTAL);
			hand.getShadow().setColor(Color.BLACK);
			hand.getPen().setColor(CustomGauge.getGaugePaletteColor(12, getGaugeColorPalette()));
			hand.getPen().setVisible(false);
			hand.getPen().setWidth(1);
			hand.getGradient().setVisible(true);
			hand.getGradient().setStartColor(CustomGauge.getGaugePaletteColor(13, getGaugeColorPalette()));
			hand.getGradient().setStartColor(CustomGauge.getGaugePaletteColor(14, getGaugeColorPalette()));
			hand.getGradient().setEndColor(CustomGauge.getGaugePaletteColor(15, getGaugeColorPalette()));
			hand.getShadow().setTransparency(70);
		}
		return hand;
	}

	public void setHand(GaugeSeriesPointer value) {
		hand = value;
	}

	/*
	 * ChartBrush characteristics of the CircularGauge's face.
	 */
	public ChartBrush getFaceBrush() {
		if (faceBrush == null) {
			faceBrush = new ChartBrush(chart, true);
			faceBrush.setVisible(true);
			faceBrush.setSolid(true);
			faceBrush.setTransparency(0);
			faceBrush.getGradient().setVisible(true);
			faceBrush.getGradient().setStartColor(CustomGauge.getGaugePaletteColor(4, getGaugeColorPalette()));
			faceBrush.getGradient().setEndColor(CustomGauge.getGaugePaletteColor(5, getGaugeColorPalette()));
			faceBrush.getGradient().setVisible(true);
			faceBrush.getGradient().setDirection(GradientDirection.RADIAL);
			if (iNewRectangle == null) iNewRectangle = new Rectangle(Rectangle.EMPTY);
			faceBrush.getGradient().setRadialX((int) -Math.round(iNewRectangle.width / 5));
			faceBrush.getGradient().setRadialY((int) -Math.round(iNewRectangle.height / 7));

			// faceBrush.getGradient().setRadialX(0);
			// faceBrush.getGradient().setRadialY(0);
		}
		return faceBrush;
	}

	public void setFaceBrush(ChartBrush value) {
		faceBrush = value;
	}

	public boolean getFillToSemiCircle() {
		return fillToSemiCircle;
	}

	public void setFillToSemiCircle(boolean value) {
		getFrame().ExtendInner = value;
		fillToSemiCircle = value;
	}

	/*
	 * The distance between the minor ticks and the CircularGauge curved getAxis().
	 */
	public int getMinorTickDistance() {
		return minorTickDistance;
	}

	public void setMinorTickDistance(int value) {
		if (minorTickDistance != value) {
			minorTickDistance = value;
			invalidate();
		}
	}

	/*
	 * Gets or sets the minimum value for the gauge.
	 */
	public double getMinimum() {
		return minimum;
	}

	public void setMinimum(double value) {
		if (minimum != value) {
			minimum = value;
			value = Math.max(minimum, value);
			invalidate();
		}
	}

	/*
	 * Gets or sets the maximum value for the gauge.
	 */
	public double getMaximum() {
		return maximum;
	}

	public void setMaximum(double value) {
		if (maximum != value) {
			maximum = value;
			value = Math.min(maximum, value);
			invalidate();
		}
	}

	/*
	 * Controls the size in degrees for the gauge getAxis().
	 */
	public double getTotalAngle() {
		return totalAngle;
	}

	public void setTotalAngle(double value) {
		if (totalAngle != value) {
			totalAngle = value;
			invalidate();
		}
	}

	/*
	 * Gets or sets the position of gauge arrow line.
	 */
	public double getValue() {
		if (getCount() == 0) {
			add(0);
		}
		return mandatory.value[0];
	}

	public void setValue(double value) {
		if (getValue() != value) {
			mandatory.value[0] = value;
			// TODO MM OnValueChanged(new EventArgs());
			invalidate();
		}
	}

	/*
	 * Defines where to display gauge getAxis() labels.
	 */
	public boolean getLabelsInside() {
		return labelsInside;
	}

	public void setLabelsInside(boolean value) {
		if (labelsInside != value) {
			labelsInside = value;
			invalidate();
		}
	}

	public boolean getLabelsVisible() {
		return labelsVisible;
	}

	public void setLabelsVisible(boolean value) {
		if (labelsVisible != value) {
			labelsVisible = value;
			invalidate();
		}
	}

	/*
	 * Gets the CircularGauges's getAxis() characteristics.
	 */

	public Axis getAxis() {
		return getVertAxis();
	}

	/*
	 * Returns a sub-object with properties that control the appearance of a shape at the middle of gauge.
	 */
	public GaugeSeriesPointer getCenter() {
		if (center == null) {
			center = new GaugeSeriesPointer(chart, this);
			center.getBrush().setSolid(true);
			center.setVisible(true);
			center.setGaugePointerStyle(GaugePointerStyles.CENTER);
			center.setHorizSize(16);
			center.setVertSize(16);
			center.getPen().setColor(CustomGauge.getGaugePaletteColor(16, getGaugeColorPalette()));
			center.getPen().setVisible(false);
			center.getPen().setWidth(2);
			center.getBrush().setVisible(true);
			center.getBrush().setColor(Color.BLACK);
			center.getShadow().setColor(Color.BLACK);
			center.getGradient().setVisible(true);
			center.getGradient().setStartColor(CustomGauge.getGaugePaletteColor(17, getGaugeColorPalette()));
			center.getGradient().setMiddleColor(CustomGauge.getGaugePaletteColor(18, getGaugeColorPalette()));
			center.getGradient().setEndColor(CustomGauge.getGaugePaletteColor(19, getGaugeColorPalette()));
			center.setColor(getCenter().getGradient().getStartColor());
			center.getShadow().setTransparency(70);
		}
		return center;
	}

	public void setCenter(GaugeSeriesPointer value) {
		center = value;
	}

	/*
	 * Returns a sub-object with properties that control the appearance of a shape at the end of the gauge arrow.
	 */
	public SeriesPointer getEndPoint() {
		if (endPoint == null) {
			endPoint = new SeriesPointer(chart, this);
			endPoint.setVisible(false);
			endPoint.getBrush().setSolid(true);
			endPoint.setColor(Color.WHITE);
			endPoint.setStyle(PointerStyle.CIRCLE);
			endPoint.setHorizSize(3);
			endPoint.setVertSize(3);
			endPoint.getGradient().setVisible(false);
		}
		return endPoint;
	}

	public void setEndPoint(SeriesPointer value) {
		endPoint = value;
	}

	/*
	 * The amount in pixels that define a gap between the gauge getAxis() and the end of the gauge arrow line.
	 */
	public int getHandDistance() {
		return handDistance;
	}

	public void setHandDistance(int value) {
		if (handDistance != value) {
			handDistance = value;
			invalidate();
			Utils.round(10.0);
		}
	}

	public static double truncate(double x) {
		long y = (long) x * 100;
		double z = (double) y / 100;

		return z;
	}
}
