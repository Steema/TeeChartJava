/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.steema.teechart.styles;

import java.util.ArrayList;

import com.steema.teechart.Chart;
import com.steema.teechart.IBaseChart;
import com.steema.teechart.PointDouble;
import com.steema.teechart.Rectangle;
import com.steema.teechart.drawing.Color;
import com.steema.teechart.drawing.Graphics3D;
import com.steema.teechart.drawing.IGraphics3D;
import com.steema.teechart.drawing.Point;
import com.steema.teechart.misc.Utils;

/**
 * 
 * @author Administrator
 */
public class CFrame extends TFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ArrayList iPoints = new ArrayList();
	public PointDouble[] InnerBandPoints = new PointDouble[4];
	public PointDouble[] MiddleBandPoints = new PointDouble[4];
	public PointDouble[] OuterBandPoints = new PointDouble[4];
	public double[] BandRadius = new double[3];
	public boolean ExtendInner = true;
	private double[] frameElementWidths = new double[] { 25, 60, 15 };
	private int totalAngle = 360, rotationAngle = 0;

	public CFrame(IBaseChart c, Color[] gColorPalette) {
		super(c, gColorPalette, true);
	}

	public CFrame(IBaseChart c, Color[] gColorPalette, boolean circle) {
		this(c, gColorPalette);
	}

	public CFrame() {
		this((Chart) null, null);
	}

	public PointDouble getRectCenter(Rectangle Rect) {
		Point aux;
		int x, y;
		aux = Graphics3D.rectCenter(Rect);
		x = aux.x;
		y = aux.y;
		return new PointDouble(x, y);

	}

	public void draw(IGraphics3D graphics, Rectangle Rect) {
		IGraphics3D g;
		double tmpWidth = calcWidth(Rect);
		PointDouble RectCenter = new PointDouble();

		g = prepareGraphics(graphics, getOuterBand());
		if (totalAngle < 360) {
			iPoints.clear();
			RectCenter = getRectCenter(Rect);
			iPoints.add(RectCenter);
			double endAngle = totalAngle + rotationAngle;
			double startAngle = rotationAngle;
			BandRadius[0] = Rect.getWidth() / 2.0;
			if (ExtendInner) {
				endAngle += 7.5;
				startAngle -= 7.5;
			}
			for (double i = startAngle; i <= endAngle; i += 0.1) {
				iPoints.add(g.pointFromEllipse(Rect, i, 0));
			}
			iPoints.add(RectCenter);
			OuterBandPoints[0] = (PointDouble) iPoints.get(1);
			OuterBandPoints[1] = (PointDouble) iPoints.get(iPoints.size() - 2);
			PointDouble[] p = new PointDouble[iPoints.size()];
			iPoints.toArray(p);
			g.polygon(p);
		} else {
			g.ellipse(Rect);
		}

		g = prepareGraphics(graphics, getMiddleBand());
		double widthReduction = tmpWidth * (frameElementWidths[2] / 100.0);
		Rect.inflate(-Utils.round(widthReduction), -Utils.round(widthReduction));
		if (totalAngle < 360) {
			iPoints.clear();
			iPoints.add(RectCenter);
			double endAngle = totalAngle + rotationAngle;
			double startAngle = rotationAngle;
			BandRadius[1] = Rect.getWidth() / 2.0;
			if (ExtendInner) {
				endAngle += 7;
				startAngle -= 7;
			}
			for (double i = startAngle; i <= endAngle; i += 0.1) {
				iPoints.add(g.pointFromEllipse(Rect, i, 0));
			}
			iPoints.add(RectCenter);
			MiddleBandPoints[0] = (PointDouble) iPoints.get(1);
			MiddleBandPoints[1] = (PointDouble) iPoints.get(iPoints.size() - 2);
			OuterBandPoints[2] = MiddleBandPoints[0];
			OuterBandPoints[3] = MiddleBandPoints[1];
			PointDouble[] p = new PointDouble[iPoints.size()];
			iPoints.toArray(p);
			g.polygon(p);
		} else {
			g.ellipse(Rect);
		}

		g = prepareGraphics(graphics, getInnerBand());
		widthReduction = tmpWidth * (frameElementWidths[1] / 100.0);
		Rect.inflate(-Utils.round(widthReduction), -Utils.round(widthReduction));
		if (totalAngle < 360) {
			iPoints.clear();
			iPoints.add(RectCenter);
			double endAngle = totalAngle + rotationAngle;
			double startAngle = rotationAngle;
			BandRadius[2] = Rect.getWidth() / 2.0;
			if (ExtendInner) {
				endAngle += 1.5;
				startAngle -= 1.5;
			}
			for (double i = startAngle; i <= endAngle; i += 0.1) {
				iPoints.add(g.pointFromEllipse(Rect, i, 0));
			}
			iPoints.add(RectCenter);
			InnerBandPoints[0] = (PointDouble) iPoints.get(1);
			InnerBandPoints[1] = (PointDouble) iPoints.get(iPoints.size() - 2);
			MiddleBandPoints[2] = InnerBandPoints[0];
			MiddleBandPoints[3] = InnerBandPoints[1];
			PointDouble[] p = new PointDouble[iPoints.size()];
			iPoints.toArray(p);
			g.polygon(p);
		} else {
			g.ellipse(Rect);
		}

		widthReduction = tmpWidth * (frameElementWidths[0] / 100.0);
		Rect.inflate(-Utils.round(widthReduction), -Utils.round(widthReduction));
	}

	public double[] getFrameElementPercents() {
		return frameElementWidths;
	}

	public double getFrameElementValue(int position) {
		return frameElementWidths[position];
	}

	public void setFrameElementPercents(double[] value) {
		frameElementWidths = value;
                invalidate();
	}

	public int getTotalAngle() {
		return totalAngle;
	}

	public void setTotalAngle(int value) {
		totalAngle = setIntegerProperty(totalAngle, value);
	}

	public int getRotationAngle() {
		return rotationAngle;
	}

	public void setRotationAngle(int value) {
		rotationAngle = setIntegerProperty(rotationAngle, value);;
	}
}
