package com.steema.teechart.styles;

import com.steema.teechart.IBaseChart;
import com.steema.teechart.Rectangle;
import com.steema.teechart.axis.Axis;
import com.steema.teechart.drawing.ChartBrush;
import com.steema.teechart.drawing.Color;
import com.steema.teechart.drawing.GradientDirection;
import com.steema.teechart.drawing.IGraphics3D;
import com.steema.teechart.drawing.Point;
import com.steema.teechart.languages.Language;
import com.steema.teechart.misc.Utils;

/**
 * 
 * <p>
 * Title: LinearGauge class
 * </p>
 * 
 * <p>
 * Description: LinearGauge Series.
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
public class LinearGauge extends CustomGauge {

	private boolean useValueColorPalette;
	private ChartBrush valueAreaBrush;
	private Rectangle iAxisRectangle;
	private double maxValue;
	private GaugeSeriesPointer maxValueIndicator;

	public LinearGauge(IBaseChart c) {
		super(c);

		super.add(0);
		super.calcVisiblePoints = false;

		super.fmaximum = 100;
		super.fredLineStartValue = 80;
		super.fredLineEndValue = 100;
		super.fgreenLineStartValue = 0;
		super.fgreenLineEndValue = 40;

		useValueColorPalette = false;
		super.setGaugeColorPalette(BlackPalette);
		super.getHand().getPen().setVisible(false);
	}

	public LinearGauge() {
		this((IBaseChart) null);
	}

        public void assign(Series source) {
            if (source instanceof LinearGauge) {
                LinearGauge tmp = (LinearGauge)source;
                tmp.setChart(((LinearGauge) source).getChart());
                tmp.setMaxValueIndicator(((LinearGauge)source).getMaxValueIndicator());
            }
            super.assign(source);
        }
    
	protected void drawFace(IGraphics3D g) {
		g.rectangle(iNewRectangle);
	}

	private void calcAxisRectangle() {
		int height, width, x, y;

		if (super.getHorizontal()) {
			height = super.getTicks().getVertSize();
			width = Utils.round(iNewRectangle.width * 0.8);
			x = (iNewRectangle.x + (iNewRectangle.width / 2)) - (width / 2);
			y = iNewRectangle.y + ((iNewRectangle.height - height) / 2);
		} else {
			height = Utils.round(iNewRectangle.height * 0.8);
			width = super.getTicks().getVertSize();
			x = (iNewRectangle.x + (iNewRectangle.width / 2)) - (width / 2);
			y = iNewRectangle.y + ((iNewRectangle.height - height) / 2);
		}

		iAxisRectangle = new Rectangle(x, y, width, height);
	}

	public void setValue(double value) {
		if (value > maxValue) {
			maxValue = value;
		}
		super.setValue(value);
	}
	
	public void Reset() {
		maxValue = getValue();
	}
	
	public Axis getAxis() {
		if (getHorizontal()) {
			return super.getHorizAxis();
		} else {
			return super.getVertAxis();
		}
	}

	/**
	 * Gets descriptive text.
	 * 
	 * @return String
	 */
	public String getDescription() {
		return Language.getString("GalleryLinearGauge");
	}

	public void draw() {
		super.draw();
		if (getAxis() != null) {
			IGraphics3D g = prepareGraphics(this.getMaxValueIndicator().getPen(), this.getMaxValueIndicator().getBrush());
			drawMaxValueIndicator(g);
		}
	}

	private int getPixelValue(double value) {
		double pixelRange;

		if (this.getHorizontal()) {
			pixelRange = this.iAxisRectangle.getWidth() / super.iRange;
		} else {
			pixelRange = this.iAxisRectangle.getHeight() / super.iRange;
		}

		return Utils.round((value - super.getMinimum()) * pixelRange);
	}

	private void drawMaxValueIndicator(IGraphics3D g) {
		if (this.getMaxValueIndicator().getVisible()) {
			if (this.getHorizontal()) {
				this.getMaxValueIndicator().draw(g, false, iAxisRectangle.getLeft() + getPixelValue(maxValue), iAxisRectangle.getBottom(), this.getMaxValueIndicator().getHorizSize(),
						this.getMaxValueIndicator().getVertSize(), this.getMaxValueIndicator().getBrush().getColor(), this.getMaxValueIndicator().getStyle());
			} else {
				this.getMaxValueIndicator().draw(g, false, iAxisRectangle.getRight(), iAxisRectangle.getTop() + (iAxisRectangle.height - getPixelValue(maxValue)),
						this.getMaxValueIndicator().getHorizSize(), this.getMaxValueIndicator().getVertSize(), this.getMaxValueIndicator().getBrush().getColor(),
						this.getMaxValueIndicator().getStyle());
			}
		}
	}

	public void setMaxValueIndicator(GaugeSeriesPointer value) {
		this.maxValueIndicator = value;
	}

	public GaugeSeriesPointer getMaxValueIndicator() {
		if (this.maxValueIndicator == null) {
			this.maxValueIndicator = new GaugeSeriesPointer(this.getChart(), this);
			if (this.getHorizontal()) {
				this.maxValueIndicator.setStyle(PointerStyle.TRIANGLE);
			} else {
				this.maxValueIndicator.setStyle(PointerStyle.LEFTTRIANGLE);
			}
			this.maxValueIndicator.getBrush().setVisible(true);
			this.maxValueIndicator.getBrush().setColor(Color.RED);
			this.maxValueIndicator.setVertSize(5);
		}

		return this.maxValueIndicator;
	}

	public void setUseValueColorPalette(boolean value) {
		if (this.useValueColorPalette != value) {
			this.useValueColorPalette = value;
			this.invalidate();
		}
	}

	public boolean getUseValueColorPalette() {
		return this.useValueColorPalette;
	}

	public void setValueAreaBrush(ChartBrush value) {
		valueAreaBrush = value;
	}

	public ChartBrush getValueAreaBrush() {
		if (this.valueAreaBrush == null) {
			this.valueAreaBrush = new ChartBrush(this.getChart(), false);
		}

		return this.valueAreaBrush;
	}

	public void prepareForGallery(boolean isEnabled) {
		super.prepareForGallery(isEnabled);
		getAxis().getLabels().setVisible(false);
		getAxis().getTicks().setVisible(false);
		getTicks().setVertSize(5);
		getRedLine().setVertSize(3);
		getGreenLine().setVertSize(3);
		getMaxValueIndicator().setVertSize(3);
		getHand().setColor(Color.red);
		setValue(70);
	}

	protected void drawAxis(IGraphics3D g) {
		String tmpS;
		double tmpValue, tmpStep, tmpStep2;
		double tmpWidth;
		int tmpFontH, t, tmpW;
		Point P3, P4;
		double pixelStep;
		int count = 0;

		if (this.getAxis() != null && this.getAxis().getVisible() == true) {
			if (this.getAxis().getIncrement() == 0)
				this.getAxis().setIncrement(this.iRange / 10);

			tmpStep = this.getAxis().getIncrement();
			this.calcAxisRectangle();

			g.setPen(this.getAxis().getAxisPen());
			g.setBrush(this.getValueAreaBrush());

			if (this.getAxis().getAxisPen().getVisible() && this.getAxis().getAxisPen().getWidth() > 0) {
				this.iAxisRectangle.inflate(-this.getAxis().getAxisPen().getWidth(), -this.getAxis().getAxisPen().getWidth());
				this.iAxisRectangle.offset(-Utils.round(this.getAxis().getAxisPen().getWidth() / 2.0), -Utils.round(this.getAxis().getAxisPen().getWidth() / 2.0));
			}

			if (getHorizontal()) {
				tmpW = this.iAxisRectangle.width;
			} else {
				tmpW = this.iAxisRectangle.height;
			}

			if (this.getAxis().getTitle().getVisible() && this.getAxis().getTitle().getCaption().length() > 0) {
				g.setFont(this.getAxis().getTitle().getFont());
				int width = g.textWidth(this.getAxis().getTitle().getCaption());
				int height = g.getFontHeight();

				int x, y;
				if (this.getHorizontal()) {
					x = Utils.round(iAxisRectangle.getLeft() + (tmpW / 2) - (width * 0.6));
					y = Utils.round(iAxisRectangle.getBottom() + (height * 0.5));
					g.textOut(x, y, this.getAxis().getTitle().getCaption());
				} else {
					x = Utils.round(iAxisRectangle.getRight() + (height * 0.5));
					y = Utils.round(iAxisRectangle.getTop() + (tmpW / 2) + (width * 0.6));
					g.rotateLabel(x, y, this.getAxis().getTitle().getCaption(), 90);
				}
			}

			if (this.getAxis().getTicks().getVisible() || this.getAxis().getLabels().getVisible()) {
				this.getAxis().getTicks().setLength(this.getTicks().getVertSize());
				this.getAxis().getTicks().assign(this.getTicks().getPen());
				g.setFont(this.getAxis().getLabels().getFont());
				g.setPen(this.getTicks().getPen());

				tmpFontH = g.getFontHeight();

				if (iRange != 0 && tmpStep != 0) {
					if (this.getHorizontal()) {
						tmpValue = this.getMinimum();
					} else {
						tmpValue = this.getMaximum();
					}

					pixelStep = (double) tmpW / iRange;
					pixelStep *= tmpStep;

					if (this.getHorizontal()) {
						do {
							P3 = new Point(iAxisRectangle.x + Utils.round(pixelStep * count), iAxisRectangle.y);
							P4 = new Point(P3.x, P3.y + iAxisRectangle.height);

							Point P3Minus = new Point(P3.x - Utils.round(getTicks().getHorizSize() * 0.5), P3.y);
							Point P3Plus = new Point(P3.x + Utils.round(getTicks().getHorizSize() * 0.5), P3.y);
							Point P4Minus = new Point(P4.x - Utils.round(getTicks().getHorizSize() * 0.5), P4.y);
							Point P4Plus = new Point(P4.x + Utils.round(getTicks().getHorizSize() * 0.5), P4.y);

							super.drawAxisTick(g, P3, P3Plus, P3Minus, P4, P4Plus, P4Minus);

							if (getAxis().getLabels().getVisible()) {
								tmpS = new java.text.DecimalFormat(getValueFormat()).format(tmpValue);
								tmpWidth = g.textWidth(tmpS);

								g.textOut(P3.x - Utils.round(tmpWidth * 0.6), P3.y - g.getFontHeight(), tmpS);
							}

							tmpValue += tmpStep;
							++count;
						} while ((tmpValue <= getMaximum()) || (tmpValue < getMaximum()));
					} else {
						do {
							P3 = new Point(iAxisRectangle.getLeft(), iAxisRectangle.getTop() + Utils.round(pixelStep * count));
							P4 = new Point(P3.x + iAxisRectangle.width, P3.y);

							Point P3Minus = new Point(P3.x, P3.y - Utils.round(getTicks().getHorizSize() * 0.5));
							Point P3Plus = new Point(P3.x, P3.y + Utils.round(getTicks().getHorizSize() * 0.5));
							Point P4Minus = new Point(P4.x, P4.y - Utils.round(getTicks().getHorizSize() * 0.5));
							Point P4Plus = new Point(P4.x, P4.y + Utils.round(getTicks().getHorizSize() * 0.5));

							super.drawAxisTick(g, P3, P3Plus, P3Minus, P4, P4Plus, P4Minus);

							if (getAxis().getLabels().getVisible()) {
								tmpS = new java.text.DecimalFormat(getValueFormat()).format(tmpValue);
								tmpWidth = g.textWidth(tmpS);

								g.textOut(P3.x - Utils.round(tmpWidth * 1.1), P3.y - Utils.round(g.getFontHeight() * 0.4), tmpS);
							}

							tmpValue -= tmpStep;
							++count;
						} while ((tmpValue >= getMinimum()) || (tmpValue > getMinimum()));
					}
				}

				if (iRange != 0 && getAxis().getMinorTicks().getVisible() && getAxis().getMinorTickCount() > 0) {
					if (getHorizontal()) {
						getAxis().getMinorTicks().setLength(this.getMinorTicks().getVertSize());
					} else {
						getAxis().getMinorTicks().setLength(this.getMinorTicks().getHorizSize());
					}

					getAxis().getMinorTicks().assign(this.getMinorTicks().getPen());
					g.setPen(this.getMinorTicks().getPen());

					if (tmpStep != 0) {
						tmpStep2 = tmpStep / (getAxis().getMinorTickCount() + 1);
						tmpValue = getMinimum();
						count = 0;
						pixelStep = (double) tmpW / iRange;
						double pixelStepMin = pixelStep;
						pixelStepMin *= tmpStep2;
						pixelStep *= tmpStep;

						do {
							for (t = 1; t <= getAxis().getMinorTickCount(); ++t) {
								if (getHorizontal()) {
									P3 = new Point(iAxisRectangle.x + Utils.round(pixelStep * count) + Utils.round(pixelStepMin * t), iAxisRectangle.y + getMinorTickDistance());
									P4 = new Point(P3.x, P3.y + ((iAxisRectangle.height - getAxis().getAxisPen().getWidth()) / 2));
								} else {
									P3 = new Point(iAxisRectangle.getLeft() + getMinorTickDistance(), iAxisRectangle.getTop() + Utils.round(pixelStep * count) + Utils.round(pixelStepMin * t));
									P4 = new Point(P3.x + ((iAxisRectangle.width - getAxis().getAxisPen().getWidth()) / 2), P3.y);
								}
								super.drawAxisMinorTick(g, P3, P4);
							}

							++count;
							tmpValue += tmpStep;
						} while (tmpValue <= (getMaximum() - tmpStep));
					}
				}
			}
		}
		this.drawColorLines(g);
	}

	protected void drawHand(IGraphics3D g) {
		if (getHand().getVisible()) {
			double pixelStep;
			if (getHorizontal()) {
				pixelStep = (double) iAxisRectangle.getWidth() / iRange;
			} else {
				pixelStep = (double) iAxisRectangle.getHeight() / iRange;
			}
			pixelStep *= (getValue() - getMinimum());
			g = prepareGraphics(getHand().getPen(), getHand().getBrush());
			if (this.useValueColorPalette) {
				getHand().getBrush().getGradient().setVisible(true);
				if (getHorizontal()) {
					getHand().getBrush().getGradient().setDirection(GradientDirection.HORIZONTAL);
				} else {
					getHand().getBrush().getGradient().setDirection(GradientDirection.VERTICAL);
				}
				// getHand().getBrush().getGradient().setExtendedColorPalette(this.getValueColorPalette()); //CDI TODO
				getHand().getBrush().getGradient().setCustomTargetPolygon(Utils.rectToPolygon(iAxisRectangle));
			} else {
				getHand().getBrush().getGradient().setVisible(false);
			}

			if (getHorizontal()) {
				g.rectangle(Rectangle.fromLTRB(iAxisRectangle.getLeft(), iAxisRectangle.getTop(), iAxisRectangle.getLeft() + Utils.round(pixelStep),
						iAxisRectangle.getBottom() + Utils.round(getAxis().getAxisPen().getWidth() / 2)));
			} else {
				g.rectangle(Rectangle.fromLTRB(iAxisRectangle.getLeft(), iAxisRectangle.getTop() + (iAxisRectangle.height - Utils.round(pixelStep)),
						iAxisRectangle.getRight() + Utils.round(getAxis().getAxisPen().getWidth() / 2), iAxisRectangle.getBottom() + Utils.round(getAxis().getAxisPen().getWidth() / 2)));
			}
		}
	}

	protected void drawColorLines(IGraphics3D g) {
		double pixelStep, start, end;
		if (getRedLine().getVisible()) {
			if (getHorizontal()) {
				pixelStep = (double) iAxisRectangle.getWidth() / iRange;
			} else {
				pixelStep = (double) iAxisRectangle.getHeight() / iRange;
			}

			start = pixelStep * (getRedLineStartValue() - getMinimum());
			end = pixelStep * (getRedLineEndValue() - getMinimum());

			g = prepareGraphics(getRedLine().getPen(), getRedLine().getBrush());

			if (getHorizontal()) {
				g.rectangle(Rectangle.fromLTRB(iAxisRectangle.getLeft() + Utils.round(start), iAxisRectangle.getBottom() + getAxis().getAxisPen().getWidth(),
						iAxisRectangle.getLeft() + Utils.round(end), iAxisRectangle.getBottom() + getAxis().getAxisPen().getWidth() + getRedLine().getVertSize()));
			} else {
				g.rectangle(Rectangle.fromLTRB(iAxisRectangle.getRight() + getAxis().getAxisPen().getWidth(), iAxisRectangle.getBottom() - Utils.round(end), iAxisRectangle.getRight()
						+ getAxis().getAxisPen().getWidth() + getRedLine().getVertSize(), iAxisRectangle.getBottom() - Utils.round(start)));
			}
		}

		if (getGreenLine().getVisible()) {
			if (getHorizontal()) {
				pixelStep = (double) iAxisRectangle.getWidth() / iRange;
			} else {
				pixelStep = (double) iAxisRectangle.getHeight() / iRange;
			}

			start = pixelStep * (getGreenLineStartValue() - getMinimum());
			end = pixelStep * (getGreenLineEndValue() - getMinimum());

			g = prepareGraphics(getGreenLine().getPen(), getGreenLine().getBrush());

			if (getHorizontal()) {
				g.rectangle(Rectangle.fromLTRB(iAxisRectangle.getLeft() + Utils.round(start), iAxisRectangle.getBottom() + getAxis().getAxisPen().getWidth(),
						iAxisRectangle.getLeft() + Utils.round(end), iAxisRectangle.getBottom() + getAxis().getAxisPen().getWidth() + getGreenLine().getVertSize()));
			} else {
				g.rectangle(Rectangle.fromLTRB(iAxisRectangle.getRight() + getAxis().getAxisPen().getWidth(), iAxisRectangle.getBottom() - Utils.round(end), iAxisRectangle.getRight()
						+ getAxis().getAxisPen().getWidth() + getGreenLine().getVertSize(), iAxisRectangle.getBottom() - Utils.round(start)));
			}
		}
        /*if (getAxis().getLabels() != null) {
            getAxis().getLabels().getFont().setName("Arial");
            getAxis().getLabels().getFont().setBold(true);
            getAxis().getLabels().getFont().setSize(12);
            getAxis().getLabels().getFont().setColor(new Color(130, 130, 130));
        }*/
	}

	public void setChart(IBaseChart value) {
		super.setChart(value);

		this.iOrigRectangle = new Rectangle(Rectangle.EMPTY);
		this.iNewRectangle = new Rectangle(Rectangle.EMPTY);

		this.useAxis = false;
		this.setShowInLegend(false);
		getFrame().setChart(value);
	}

	protected void setValues() {
		calcOrigRectangle();
		calcNewRectangle();
		super.setValues();
	}

	protected void calcOrigRectangle() {
		super.calcOrigRectangle();
		boolean canMix = true;

		int maxHeight;
		int height, width, x, y;

		if (getHorizontal()) {
			maxHeight = iOrigRectangle.height / 2;
		} else {
			maxHeight = iOrigRectangle.width / 2;
		}

		int seriesCount = super.getChart().getSeriesCount();
		int seriesIndex = super.getChart().getSeries().indexOf(this);

		int step;

		if (getHorizontal()) {
			step = iOrigRectangle.height / seriesCount;
		} else {
			step = iOrigRectangle.width / seriesCount;
		}

		for (int t = 0; t < seriesCount; ++t) {
			if (!(super.getChart().getSeries(t) instanceof CustomGauge)) {
				canMix = false;
				break;
			}
		}

		if (canMix) {
			if (getHorizontal()) {
				height = Math.min(maxHeight, step);
				height -= 3;
				width = iOrigRectangle.width;
			} else {
				width = Math.min(maxHeight, step);
				width -= 3;
				height = iOrigRectangle.height;
			}

			x = iOrigRectangle.x;
			y = iOrigRectangle.y;
			if (seriesCount == 1) {
				if (getHorizontal()) {
					y += (maxHeight / 2);
				} else {
					x += (maxHeight / 2);
				}
			} else {
				if (getHorizontal()) {
					y += step * seriesIndex;
				} else {
					x += step * seriesIndex;
				}
			}
			iOrigRectangle = new Rectangle(x, y, width, height);
		}

	}

	public void setRectangle(Rectangle value) {
		iNewRectangle = value;
		iOrigRectangle = value;
	}
}
