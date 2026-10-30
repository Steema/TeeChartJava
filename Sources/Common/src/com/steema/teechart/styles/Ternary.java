package com.steema.teechart.styles;

import java.text.DecimalFormat;
import java.util.ArrayList;

import com.steema.teechart.IBaseChart;
import com.steema.teechart.Rectangle;
import com.steema.teechart.axis.Axis;
import com.steema.teechart.axis.AxisLabelAlign;
import com.steema.teechart.drawing.ChartBrush;
import com.steema.teechart.drawing.ChartPen;
import com.steema.teechart.drawing.Color;
import com.steema.teechart.drawing.Gradient;
import com.steema.teechart.drawing.Graphics3D;
import com.steema.teechart.drawing.IGraphics3D;
import com.steema.teechart.drawing.Point;
import com.steema.teechart.languages.Language;
import com.steema.teechart.legend.LegendTextStyle;
import com.steema.teechart.misc.Utils;

/**
 * 
 * <p>
 * Title: Ternary class
 * </p>
 * 
 * <p>
 * Description: Pyramid Series.
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
public class Ternary extends Custom3DPalette {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private TernaryAxisScale fAxisScale;
	private ChartPen fLinesPen;
	private boolean fRotateLabels,labelsEnabled = false;;
	private TernaryStyle fTernaryStyle;
	private ArrayList<String>[] fVertexTitles;
	private TernaryFormat fPlotFormat;
	private ValueList fRadiusValues;
	private ValueList fWeightValues;
	private SeriesPointer fPointer;
	private ArrayList<Point[]> iPolyList = new ArrayList<Point[]>();
	private Point[] boundingPoly;
	private Point fVertexA, fVertexB, fVertexC;
    private Point[] fLeftPoints;
    private Point[] fRightPoints;
    private Point[] fBottomPoints;
    private Point[] fPlotPoints;
	private double fPercentCoeff;
	private TernaryLegendStyle fTernaryLegendStyle;
	private TernaryMarkStyle fTernaryMarkStyle;
	private int paletteLength;

	public class LegendStringVars {
		// Fields
		public int ValueIndex;
	}

	public class DrawLabelsVars {
		// Fields
		public String labelStr;
		public int maxLabelWidth;
		public double tmpVal;
	}

	public class ClickedVars {

		public int tmpX;
		public int tmpY;
		public int X;
		public int Y;

	}

	public Ternary() {
		this(null);
	}

	public Ternary(IBaseChart c) {
		super(c);
	    this.getMarks().getArrow().setVisible(false);
	      this.setColorEach(true);
	      this.fRotateLabels = true;
	      this.fTernaryStyle = TernaryStyle.Point;
	      this.fAxisScale = TernaryAxisScale.Percent;
	      this.fVertexTitles = new ArrayList[3];
	      this.fVertexTitles[0] = new ArrayList<String>();
	      this.fVertexTitles[1] = new ArrayList<String>();
	      this.fVertexTitles[2] = new ArrayList<String>();

	      this.fVertexTitles[0].add("A");
	      this.fVertexTitles[1].add("B");
	      this.fVertexTitles[2].add("C");
	      this.getGradient().setVisible(true);

	      this.getGradient().setMiddleColor(Color.WHITE);

	      this.fPlotFormat = TernaryFormat.Ternary;
	      this.fRadiusValues = new ValueList(this, "Radius");
	      this.fWeightValues = new ValueList(this, "Weighting");
	      super.mandatory = this.fWeightValues;  //Palette Legend Gradient Values
	      super.setVerticalAxis(VerticalAxis.BOTH);
	      super.setHorizontalAxis(HorizontalAxis.BOTTOM);
	      super.useAxis = false;

	}
        
        public void assign(Series source) {
            if (source instanceof Ternary) {
                Ternary tmp = (Ternary)source;
                tmp.setAxisScale(((Ternary)source).getAxisScale());
                tmp.setChart(((Ternary)source).getChart()); 
                tmp.getGradient().assign(((Ternary)source).getGradient()); 
                tmp.getLinesPen().assign(((Ternary)source).getLinesPen()); 
                tmp.setMarkStyle(((Ternary)source).getMarkStyle());
                tmp.setPlotFormat(((Ternary)source).getPlotFormat());
                tmp.getPointer().assign(((Ternary)source).getPointer());
                tmp.setRadiusValues(((Ternary)source).getRadiusValues());
                tmp.setRotateLabels(((Ternary)source).getRotateLabels());
                tmp.setTernaryLegendStyle(((Ternary)source).getTernaryLegendStyle());
                tmp.setTernaryMarkStyle(((Ternary)source).getTernaryMarkStyle());
                tmp.setTernaryStyle(((Ternary)source).getTernaryStyle());
                tmp.fVertexTitles = (((Ternary)source).fVertexTitles);
                tmp.setWeightValues(((Ternary)source).getWeightValues());
            }
            super.assign(source);
        }         

	public enum TernaryAxisScale {
		Percent, Unit
	}

	public enum TernaryStyle {
		Bubble, Point
	}

	public enum TernaryMarkStyle {
		Short, Long, LongLabelled
	}

	public enum TernaryFormat {
		Piper, Ternary
	}

	public enum TernaryLegendStyle {
		Bubble, BubbleWeight, Values, ValuesBubble, ValuesBubbleWeight, Weight
	}

	public int add(double aX, double aY, double aZ, double aRadius,
			com.steema.teechart.drawing.Color aColor) {
		return this.add(aX, aY, aZ, aRadius, "", aColor);
	}

	public int add(double AX, double AY, double aZ, double ARadius,
			String aLabel, com.steema.teechart.drawing.Color AColor) {

		getRadiusValues().tempValue = ARadius;
		return this.add(AX, AY, aZ, aLabel, AColor);
	}

	public int add(double AX, double AY, double aZ, double ARadius,
			double AWeight, Color AColor) {
		return this.add(AX, AY, aZ, ARadius, AWeight, "", AColor);
	}

	public int add(double AX, double AY, double aZ, double ARadius,
			double AWeight, String aLabel, Color AColor) {
		getRadiusValues().tempValue = ARadius;
		getWeightValues().tempValue = AWeight;
		return this.add(AX, AY, aZ, aLabel, AColor);
	}

	
	public void fillSampleValues(int numValues) {
		SeriesRandom s = super.randomBounds(numValues);
		for (int i = 0; i < numValues; i++) {
			this.add(s.Random() * 100, s.Random() * 100, s.Random() * 100,
					30 - s.Random() * 25, 10 - s.Random() * 10, Color.random());
			s.tmpX += s.StepX;
		}

	}

	private String calcPercentSt(LegendStringVars LegendString,
			ValueList[] valuesArrays) {

		if (valuesArrays.length > 0) {
			String[] textArray = new String[valuesArrays.length
					+ (valuesArrays.length - 1)];
			int textIdx = 0;
			for (int i = 0; i < valuesArrays.length; i++) {
				if ((textIdx + 1) % 2 == 0) {
					textArray[textIdx] = " ";
					textIdx++;
				}

				textArray[textIdx] = Double
						.toString(((valuesArrays[i]
								.getValue(LegendString.ValueIndex)) / valuesArrays[i].totalABS));
				textIdx++;
			}
			StringBuffer result = new StringBuffer();
			if (textArray.length > 0) {
				result.append(textArray[0]);
				for (int i = 1; i < textArray.length; i++) {
					result.append("");
					result.append(textArray[i]);
				}
			}
			return result.toString();

		} else
			return "";
	}
	 protected  double[] rationaliseValues( double x,  double y,  double z)
	    {
	      double tmpTotal = (x + y) + z;
	      if (tmpTotal == 0f)
	      {
	        this.fPercentCoeff = 0.0;
	        x = 0.0;
	        y = 0.0;
	        z = 0.0;
	      }
	      else if (tmpTotal != 100f)
	      {
	        this.fPercentCoeff = 100.0 / tmpTotal;
	        x *= this.fPercentCoeff;
	        y *= this.fPercentCoeff;
	        z *= this.fPercentCoeff;
	      }
	      else
	      {
	        this.fPercentCoeff = 1.0;
	      }
	      double[] retorn = new double[3];
	      retorn[0]= x;
	      retorn[1]= y;
	      retorn[2]= z;
	      return retorn;
	    }
	public Point calcPointFromValues(double a, double b, double c) {
		Point point = new Point(0, 0);
		double tmpX = a;
		double tmpY = b;
		double tmpZ = c;
		double[] retorn = this.rationaliseValues(tmpX,  tmpY,  tmpZ);
		tmpX = retorn[0];
		tmpY = retorn[1];
		tmpZ = retorn[2];
		double altitude = boundingPoly[1].getY() - boundingPoly[0].getY();
		double a1 = (tmpX * altitude) / 100.0;
		double b1 = (tmpY * altitude) / 100.0;
		double c1 = (tmpZ * altitude) / 100.0;
		double cSide = c1 / Math.cos(30 * (Math.PI / 180.0));
		double bSide = b1 / Math.cos(30 * (Math.PI / 180.0));
		double xOffsetAtHeighta1 = a1 * Math.tan(30 * (Math.PI / 180.0));
		double xPos = (boundingPoly[1].getX() + xOffsetAtHeighta1) + bSide;
		double yPos = (boundingPoly[0].getY() + (boundingPoly[1].getY() - boundingPoly[0]
				.getY())) - a1;
		point.x = Utils.round(xPos);
		point.y = Utils.round(yPos);
		return point;
	}

	
	public void draw() {
	this.iPolyList = new ArrayList<Point[]>();
		Graphics3D canvas = (Graphics3D) this.getChart().getGraphics3D();
		if (this.fPlotFormat == TernaryFormat.Ternary) {
			Rectangle boundingRectangle;

			Point point = new Point(0, 0);
			Point point2 = new Point(0, 0);
			Point point3 = new Point(0, 0);
			int chartHeight, chartWidth, midX, tmpWidth, topBound, yShift;
			Rectangle tmpR;

			tmpR = this.getChart().getChartRect();
			chartHeight = (int) tmpR.getHeight();
			chartWidth = (int) tmpR.getWidth();
			if (chartWidth > chartHeight) {
				boundingRectangle = Rectangle.fromLTRB(canvas.getXCenter()
						- (chartHeight / 2), tmpR.getTop(), canvas.getXCenter()
						+ (chartHeight / 2), tmpR.getBottom());
			} else {
				boundingRectangle = Rectangle
						.fromLTRB(tmpR.getLeft(), canvas.getYCenter()
								- (chartWidth / 2), tmpR.getRight(),
								canvas.getYCenter() + (chartWidth / 2));
			}
			midX = canvas.getXCenter();
			tmpWidth = (int) (boundingRectangle.getWidth() / 2);
			topBound = Utils.round(Math.tan(60.0 * (Math.PI / 180.0))
					* tmpWidth);
			yShift = (boundingRectangle.getBottom()
					- boundingRectangle.getTop() - topBound) / 2;
			boundingPoly = new Point[3];
			boundingPoly[0] = new Point(midX, boundingRectangle.getBottom()
					- topBound - yShift);
			boundingPoly[1] = new Point(boundingRectangle.getLeft(),
					boundingRectangle.getBottom() - yShift);
			boundingPoly[2] = new Point(boundingRectangle.getRight(),
					boundingRectangle.getBottom() - yShift);
			this.fVertexA = boundingPoly[0];
			this.fVertexB = boundingPoly[2];
			this.fVertexC = boundingPoly[1];
			canvas.line(boundingPoly[0], boundingPoly[1]);
			canvas.line(boundingPoly[1], boundingPoly[2]);
			canvas.line(boundingPoly[2], boundingPoly[0]);
			iPolyList.add(iPolyList.size(),boundingPoly);
		}
		this.drawAxes();
		if (getUseColorRange())
			generatePalette();
		super.draw();
	}

	protected void drawAxes() {
		if (this.fPlotFormat == TernaryFormat.Ternary) {
			for (int i = 0; i < iPolyList.size(); i++) {
				this.drawAxis(this.getChart().getAxes().getBottom(),
						this.iPolyList.get(i)[1], this.iPolyList.get(i)[2]);
				this.drawAxis(this.getChart().getAxes().getLeft(),
						iPolyList.get(i)[0], iPolyList.get(i)[1]);
				this.drawAxis(this.getChart().getAxes().getRight(),
						iPolyList.get(i)[2], iPolyList.get(i)[0]);
				this.drawGrids(iPolyList.get(i));
				this.drawTicks(iPolyList.get(i));

			this.drawLabels(iPolyList.get(i));
				this.drawVertexTitles();
			}
		}
	}
	   protected  Point getSideOffset(int Hypoteneuse)

	       {
	         Point point = new Point(0,0);
	         int xOffset, yOffset;

	         xOffset = Utils.round(Math.cos((60.0* (Math.PI / 180.0)) * Hypoteneuse));
	         yOffset = Utils.round(Math.sin((60.0* (Math.PI / 180.0)) * Hypoteneuse));
	         point.x = xOffset;
	         point.y = yOffset;
	         return point;
	       }
	protected void drawAxis(Axis aAxis, Point StarPoint, Point EndPoint) {
		if (aAxis.getVisible()) {
			Graphics3D canvas = (Graphics3D) this.getChart().getGraphics3D();
			canvas.setPen(aAxis.getAxisPen());
			canvas.line(StarPoint, EndPoint);

			if (aAxis.getTitle().getVisible()
					&& !(isNullOrEmpty(aAxis.getTitle().getText()))) {
				canvas.setFont(aAxis.getTitle().getFont());
				int width = Utils.round(canvas.textWidth(aAxis.getTitle()
						.getText()));
				int height = canvas.getFontHeight();
				if (aAxis.getHorizontal() && !aAxis.getOtherSide()) {
					int x, y;
					x = (StarPoint.x + ((EndPoint.x - StarPoint.x) / 2))
							- (width / 2);
					y = (StarPoint.y + Utils.round(height * 0.5) + aAxis
							.getLabels().getFont().getSize());
					canvas.textOut(x, y, aAxis.getTitle().getText());
				} else {
					Point tmpPoint;
					Point tmpPoint2;
					if (!aAxis.getHorizontal() && !aAxis.getOtherSide()) {
						tmpPoint = this.getVectorLocation(EndPoint, StarPoint,
								50);
						tmpPoint2 = this.getSideOffset(width / 2);
						tmpPoint.x = (tmpPoint.x - tmpPoint2.x)
								- Utils.round(height * 1.4);
						tmpPoint.y += tmpPoint2.y;
						tmpPoint2 = this.getSideOffset(aAxis.getLabels()
								.getFont().getSize() * 3);
						canvas.rotateLabel(tmpPoint.x - tmpPoint2.x, tmpPoint.y
								- tmpPoint2.y, aAxis.getTitle().getCaption(),
								60.0);
					} else if (!aAxis.getHorizontal() && aAxis.getOtherSide()) {
						tmpPoint = this.getVectorLocation(StarPoint, EndPoint,
								50);
						tmpPoint2 = this.getSideOffset(width / 2);
						tmpPoint.x = (tmpPoint.x - tmpPoint2.x)
								+ Utils.round(height * 1.6);
						tmpPoint.y -= tmpPoint2.y;
						tmpPoint2 = this.getSideOffset(aAxis.getLabels()
								.getFont().getSize() * 3);
						canvas.rotateLabel(tmpPoint.x + tmpPoint2.x, tmpPoint.y
								- tmpPoint2.y, aAxis.getTitle().getCaption(),
								300.0);
					}
				}
			}
		}
	}

	private int getRoundedAxisIncr(int desiredIncr) {
		if ((desiredIncr == 1) || ((100 % desiredIncr) != 0)) {
			return 10;
		}
		return desiredIncr;
	}

	protected void drawGrids(Point[] Element) {
		int TickCount;
		Graphics3D canvas = (Graphics3D) this.getChart().getGraphics3D();
		if (this.getChart().getAxes().getBottom().getIncrement() == 0) {
			TickCount = 10;
		} else {
			TickCount = getRoundedAxisIncr(Utils.round(100.0 / this.getChart()
					.getAxes().getBottom().getIncrement()));
		}

        fLeftPoints = new Point[TickCount - 1];
        fRightPoints = new Point[TickCount - 1];
        fBottomPoints = new Point[TickCount - 1];

		for (int i = 0; i < TickCount - 1; i++) {
			Point point = new Point(0, 0);

			int num3 = Utils
					.round((double) (Element[1].x + ((((double) Math
							.abs((int) (i - (TickCount - 1)))) / ((double) TickCount)) * (Element[2].x - Element[1].x))));
			point.x = num3;
			point.y = Element[1].y;
			this.fBottomPoints[i] = point;
			this.fLeftPoints[i] =
					(this.getVectorLocation(
									Element[1],
									Element[0],
									Utils.round((double) ((((double) (i + 1)) / ((double) TickCount)) * 100.0))));
			this.fRightPoints[i]=
					(this.getVectorLocation(
									Element[2],
									Element[0],
									Utils.round((double) ((((double) (i + 1)) / ((double) TickCount)) * 100.0))));
			ChartPen grid = this.getChart().getAxes().getBottom().getGrid();
			canvas.setPen(grid);
		}

		for (int i = 0; i < fLeftPoints.length; i++) {
			if (this.getChart().getAxes().getBottom().getVisible()
					&& this.getChart().getAxes().getBottom().getGrid()
							.getVisible()) {
				canvas.line(((int) this.fBottomPoints[i].getX()),
						((int) this.fBottomPoints[i].getY()),
						((int) this.fLeftPoints[i].getX()),
						((int) this.fLeftPoints[i].getY()));
			}
			if (this.getChart().getAxes().getRight().getVisible()
					&& this.getChart().getAxes().getRight().getGrid()
							.getVisible()) {
				canvas.line(this.fBottomPoints[i].x,
						this.fBottomPoints[i].y,
						this.fRightPoints[i].x, this.fRightPoints[i].y);
			}
			if (this.getChart().getAxes().getLeft().getVisible()
					&& this.getChart().getAxes().getLeft().getGrid()
							.getVisible()) {
				Point[] pointArray8 = this.fLeftPoints;
				Point[] pointArray9 = this.fLeftPoints;
				Point[] pointArray10 = this.fLeftPoints;
				canvas.line((int)(
						this.fLeftPoints[(((pointArray8 != null) ? pointArray8.length
								: 0) - 1)
								- i].getX()),(int)(
						this.fLeftPoints[(((pointArray9 != null) ? pointArray9.length
								: 0) - 1)
								- i].getY()),
								(int)(this.fRightPoints[i].getX()),
								(int)(this.fLeftPoints[(((pointArray10 != null) ? pointArray10.length
								: 0) - 1)
								- i].getY()));
			}
		}
	}

	private String getValStr(DrawLabelsVars DrawLabels){
		if (this.fAxisScale == TernaryAxisScale.Unit) {
			DecimalFormat df = new DecimalFormat("#");
			String newNum = df.format(DrawLabels.tmpVal / 100.0);
			return newNum;
		}
		DecimalFormat df = new DecimalFormat("#");
		String newNum = df.format(DrawLabels.tmpVal);
		return newNum;
	}

	public void drawLabels(Point[] Element)
	{
		DrawLabelsVars DrawLabels = new DrawLabelsVars();

	      IGraphics3D canvas = this.getChart().getGraphics3D();
	      DrawLabels.maxLabelWidth = Utils.round(canvas.textWidth(""));
	      double Increment = 100.0 / ((double)(((fBottomPoints != null) ? fBottomPoints.length : 0) + 1));

	      if (this.getChart().getAxes().getBottom().getVisible() && this.getChart().getAxes().getBottom().getLabels().getVisible())
	      {
	        DrawLabels.tmpVal = 0.0;
	        canvas.setFont(this.getChart().getAxes().getBottom().getLabels().getFont());
	        if (super.getHorizAxis().getLabels().getOnAxis())
	        {
	          DrawLabels.labelStr = getValStr(DrawLabels);
	          writeBottomLabel(DrawLabels, this.getVertexB(), 300.0, this.fRotateLabels);
	        }
	        for (int i = 0; i < fBottomPoints.length; i++)
	        {
	          DrawLabels.tmpVal += Increment;
	          DrawLabels.labelStr = getValStr(DrawLabels);
	
	          writeBottomLabel(DrawLabels, this.fBottomPoints[i], 300.0, this.fRotateLabels);
	          if (DrawLabels.maxLabelWidth < canvas.textWidth(DrawLabels.labelStr))
	          {
	            DrawLabels.maxLabelWidth = Utils.round(canvas.textWidth(DrawLabels.labelStr));
	          }
	        }
	        if (super.getHorizAxis().getLabels().getOnAxis())
	        {
	          DrawLabels.tmpVal = 100.0;
	          DrawLabels.labelStr = getValStr(DrawLabels);
	          writeBottomLabel(DrawLabels, this.getVertexC(), 300.0, this.fRotateLabels);
	        }
	      }
	      if (this.getChart().getAxes().getLeft().getVisible() && this.getChart().getAxes().getLeft().getLabels().getVisible())
	      {
	        DrawLabels.tmpVal = 100.0;
	        if (this.getChart().getAxes().getLeft().getLabels().getOnAxis())
	        {
	          DrawLabels.labelStr = getValStr(DrawLabels);
	          canvas.textOut((this.getVertexA().x - Utils.round(canvas.textWidth(DrawLabels.labelStr))) + 5, this.getVertexA().y -  Utils.round((canvas.textHeight(DrawLabels.labelStr) / 4) * 3), DrawLabels.labelStr);
	        }
	        for (int i = 0; i < fLeftPoints.length; i++)
	        {
	          canvas.setFont(this.getChart().getAxes().getLeft().getLabels().getFont());
	          DrawLabels.tmpVal -= Increment;
	          DrawLabels.labelStr = getValStr(DrawLabels);

	          canvas.textOut((this.fLeftPoints[i].x - Utils.round(canvas.textWidth(DrawLabels.labelStr))) + 5, this.fLeftPoints[i].y - Utils.round((canvas.textHeight(DrawLabels.labelStr) / 4) * 3), DrawLabels.labelStr);
	        }
	        if (this.getChart().getAxes().getLeft().getLabelsOnAxis())
	        {
	          DrawLabels.tmpVal = 0.0;
	          DrawLabels.labelStr = getValStr(DrawLabels);
	          canvas.textOut((this.getVertexC().x - Utils.round(canvas.textWidth(DrawLabels.labelStr))) + 5, this.getVertexC().y - Utils.round((canvas.textHeight(DrawLabels.labelStr) / 4) * 3), DrawLabels.labelStr);
	        }
	      }
	      if (this.getChart().getAxes().getRight().getVisible() && this.getChart().getAxes().getRight().getLabels().getVisible())
	      {
	        DrawLabels.tmpVal = 100.0;
	        if (this.getChart().getAxes().getRight().getLabels().getOnAxis())
	        {
	          DrawLabels.labelStr = getValStr(DrawLabels);
	          writeRightLabel(DrawLabels, this.getVertexB(), 60.0, this.fRotateLabels);
	        }
	        for (int i = 0; i < fRightPoints.length; i++)
	        {
	          canvas.setFont(this.getChart().getAxes().getRight().getLabels().getFont());
	          DrawLabels.tmpVal -= Increment;
	          DrawLabels.labelStr = getValStr( DrawLabels);

	          writeRightLabel(DrawLabels, this.fRightPoints[i], 60.0, this.fRotateLabels);
	        }
	        if (this.getChart().getAxes().getRight().getLabels().getOnAxis())
	        {
	          DrawLabels.tmpVal = 0.0;
	          DrawLabels.labelStr = getValStr(DrawLabels);
	          writeRightLabel(DrawLabels, getVertexA(), 60.0, this.fRotateLabels);
	        }
	      }
	    }

	
	protected void drawMark(int valueIndex, String s,
			SeriesMarksPosition position) {
		   Point tmpPoint = this.calcPointFromValues(super.getXValues().getValue(valueIndex), super.getYValues().getValue(valueIndex), super.getZValues().getValue(valueIndex));
		   	position.leftTop.x  = (int) tmpPoint.getX();
		      position.leftTop.y = (int) tmpPoint.getY();
		      super.drawMark(valueIndex, s, position);
	}

	protected void drawTicks(Point[] Element) {

		 Point[] poly = new Point[4];
	      Graphics3D canvas = (Graphics3D) this.getChart().getGraphics3D();
	      for (int i = 0; i < fBottomPoints.length; i++)
	      {
	        Point tmpPoint;
	        if (this.getChart().getAxes().getBottom().getVisible() && this.getChart().getAxes().getBottom().getGrid().getVisible())
	        {
	          tmpPoint = this.getSideOffset(this.getChart().getAxes().getBottom().getTicks().getLength());
	          Graphics3D Graphics3D = (com.steema.teechart.drawing.Graphics3D) this.getChart().getGraphics3D();
	          ChartPen ticks = this.getChart().getAxes().getBottom().getTicks();
	          canvas.setPen(ticks);
	          canvas.line(this.fBottomPoints[i].x, this.fBottomPoints[i].y, this.fBottomPoints[i].x + tmpPoint.x, this.fBottomPoints[i].y - tmpPoint.y);
	          canvas.line(this.fBottomPoints[i].x, this.fBottomPoints[i].y, this.fBottomPoints[i].x - tmpPoint.x, this.fBottomPoints[i].y - tmpPoint.y);
	        }
	        if (this.getChart().getAxes().getLeft().getVisible() && this.getChart().getAxes().getLeft().getTicks().getVisible())
	        {
	          tmpPoint = this.getSideOffset(this.getChart().getAxes().getLeft().getTicks().getLength());
	          Graphics3D Graphics3D2 = (Graphics3D) this.getChart().getGraphics3D();
	          ChartPen aPen = this.getChart().getAxes().getLeft().getTicks();
	          canvas.setPen(aPen);
	          canvas.line(this.fLeftPoints[i].x, this.fLeftPoints[i].y, this.fLeftPoints[i].x + tmpPoint.x, this.fLeftPoints[i].y + tmpPoint.y);
	          canvas.line(this.fLeftPoints[i].x, this.fLeftPoints[i].y, this.fLeftPoints[i].x + this.getChart().getAxes().getLeft().getTicks().getLength(), this.fLeftPoints[i].y);
	        }
	        if (this.getChart().getAxes().getRight().getVisible() && this.getChart().getAxes().getRight().getTicks().getVisible())
	        {
	          tmpPoint = this.getSideOffset(this.getChart().getAxes().getRight().getTicks().getLength());
	          Graphics3D Graphics3D3 = (Graphics3D) this.getChart().getGraphics3D();
	          ChartPen pen3 = this.getChart().getAxes().getRight().getTicks();
	          canvas.setPen(pen3);
	          Point[] pointArray2 = this.fBottomPoints;
	          canvas.line(this.fRightPoints[i].x, this.fLeftPoints[(((pointArray2 != null) ? pointArray2.length : 0) - 1) - i].y, this.fRightPoints[i].x - tmpPoint.x, this.fRightPoints[i].y + tmpPoint.y);
	          Point[] pointArray3 = this.fBottomPoints;
	          Point[] pointArray4 = this.fBottomPoints;
	          canvas.line(this.fRightPoints[i].x, this.fLeftPoints[(((pointArray3 != null) ? pointArray3.length : 0) - 1) - i].y, this.fRightPoints[i].x - this.getChart().getAxes().getRight().getTicks().getLength(), this.fLeftPoints[(((pointArray4 != null) ? pointArray4.length : 0) - 1) - i].y);
	        }
	      }
	}

    private Point[] incrementPoints(Point[] oldPoints) {

      if (oldPoints==null) return new Point[1];

      Point[] newPoints = new Point[oldPoints.length+1];
      System.arraycopy(oldPoints,0,newPoints,0,oldPoints.length);

      return newPoints;
    }
	
	public void drawValue(int index) {
		
		 Color tmpColor = this.getValueColor(index);
		       if ((tmpColor!=Color.TRANSPARENT) && this.getPointer().getVisible())

		       {
		         PointerStyle tmpStyle = this.getPointer().getStyle();
		         this.getPointer().prepareCanvas(this.getChart().getGraphics3D(), tmpColor);
		         Point tmpPoint = this.calcPointFromValues(super.getXValues().getValue(index), super.getYValues().getValue(index), super.getZValues().getValue(index));

                 fPlotPoints = incrementPoints(fPlotPoints);
		         fPlotPoints[fPlotPoints.length-1] = tmpPoint;

		         if (this.fTernaryStyle == TernaryStyle.Bubble)
		         {
		           this.preparePointer(index);
		         }
		         this.getPointer().draw(this.getChart().getGraphics3D(), this.getChart().getAspect().getView3D(), tmpPoint.x, tmpPoint.y, this.getPointer().getHorizSize(), this.getPointer().getVertSize(), tmpColor, tmpStyle);
		       }
	}

	private void generatePalette() {
		for (int t = 0; t < paletteLength; t++) {
			GridPalette palette = (GridPalette) getPalette().get(t);
			palette.color = getValueColorValue(palette.upToValue);
			getPalette().set(t, palette);
		}

	}

	protected void drawVertexTitles() {
		 int offset;
	      Graphics3D canvas = (Graphics3D) this.getChart().getGraphics3D();
	      if (this.getChart().getAxes().getRight().getLabels().getOnAxis())
	      {
	        offset = Utils.round(canvas.textHeight("H")) + 15;
	      }
	      else
	      {
	        offset = 0;
	      }

	      if (this.fVertexTitles[0].size() > 0)
	      {
			  canvas.textOut(this.getVertexA().x - Utils.round(canvas.textWidth(this.fVertexTitles[0].get(0).toString()) / 2), this.getVertexA().y - offset, this.fVertexTitles[0].get(0).toString());

	      }

	      if (super.getHorizAxis().getLabels().getOnAxis())
	      {
	        offset = Utils.round(canvas.textHeight("H"));
	      }
	      else
	      {
	        offset = 0;
	      }
	      if (this.fVertexTitles[1].size() > 0) canvas.textOut(this.getVertexB().x, this.getVertexB().y + offset, this.fVertexTitles[1].get(0).toString());
			if (this.fVertexTitles[2].size() > 0) canvas.textOut(this.getVertexC().x - Utils.round(canvas.textWidth(this.fVertexTitles[2].get(0).toString())), this.getVertexC().y + offset, this.fVertexTitles[2].get(0).toString());

	}

	protected Point getVectorLocation(Point StarPoint, Point EndPoint,
			int PercentOffset) {
		int yOffset, xVal, yVal, xOffset;

		Point point = new Point(0, 0);
		if (EndPoint.x > StarPoint.x) {
			xOffset = StarPoint.x;
		} else {
			xOffset = EndPoint.x;
		}
		if (EndPoint.y > StarPoint.y) {
			yOffset = StarPoint.y;
		} else {
			yOffset = EndPoint.y;
		}
		xVal = (xOffset + Math.abs((int) (EndPoint.x - StarPoint.x)))
				- ((Math.abs((int) (EndPoint.x - StarPoint.x)) * PercentOffset) / 100);
		if ((EndPoint.x < StarPoint.x) && (EndPoint.y < StarPoint.y)) {
			yVal = (yOffset + Math.abs((int) (EndPoint.y - StarPoint.y)))
					- ((Math.abs((int) (EndPoint.y - StarPoint.y)) * PercentOffset) / 100);
		} else {
			yVal = yOffset
					+ ((Math.abs((int) (EndPoint.y - StarPoint.y)) * PercentOffset) / 100);
		}
		point.x = xVal;
		point.y = yVal;
		return point;
	}

	public ArrayList[] getVertexTitles() {
		return this.fVertexTitles;
	}
        public void setVertexTitle(String value,int position)
        {
          fVertexTitles[position].remove(0);
          fVertexTitles[position].add(0,value);
          refreshSeries();
        }

	
	public String getLegendString(int legendIndex,
			LegendTextStyle legendTextStyle) {
		LegendStringVars LegendString = new LegendStringVars();

		if (((this.getUseColorRange()) || (this.getUsePalette()))
				&& (!getColorEach()))
			return super.getLegendString(legendIndex, legendTextStyle);
		else {
			String result;
			result = "";

			LegendString.ValueIndex = this.legendToValueIndex(legendIndex);
			result = (String) super.getLabels().getString(
					LegendString.ValueIndex);
			if (legendTextStyle.equals(LegendTextStyle.VALUE)) {

				result = applyTernaryLegendStyle(LegendString, false);
			}

			else if (legendTextStyle.equals(LegendTextStyle.LEFTVALUE)) {
				if (result != null || result != "") {
					result = "\u0006" + result;
				}
				result = applyTernaryLegendStyle(LegendString, false) + result;
			}

			else if (legendTextStyle.equals(LegendTextStyle.RIGHTVALUE)) {
				if (result != null || result != "") {
					result = result + "\u0006";
				}
				result = result + applyTernaryLegendStyle(LegendString, false);
			}

			else if (legendTextStyle.equals(LegendTextStyle.LEFTPERCENT)) {
				if (result != null || result != "") {
					result = "\u0006" + result;
				}
				result = applyTernaryLegendStyle(LegendString, true) + result;
			}

			else if (legendTextStyle.equals(LegendTextStyle.RIGHTPERCENT)) {
				if (result != null || result != "") {
					result = result + "\u0006";
				}
				result = result + applyTernaryLegendStyle(LegendString, true);
			}

			else if (legendTextStyle.equals(LegendTextStyle.PERCENT)) {
				result = applyTernaryLegendStyle(LegendString, true);
			}

			else if (legendTextStyle.equals(LegendTextStyle.XANDPERCENT)) {
				result = applyTernaryLegendStyle(LegendString, false) + " "
						+ applyTernaryLegendStyle(LegendString, true);
			}
			return result;
		}

	}

	private String applyTernaryLegendStyle(LegendStringVars LegendString,
			boolean isPercent) {
		String[] textArray1 = new String[] { "" };
		if (this.fTernaryLegendStyle.equals(TernaryLegendStyle.Values)) {
			if (isPercent)
				return calcPercentSt(LegendString, new ValueList[] {
						getXValues(), getYValues(), getZValues() });
			else {
				DecimalFormat df = new DecimalFormat("####.##");
				String xValue = df.format(getXValues().getValue(
						LegendString.ValueIndex));
				df = new DecimalFormat("####.##");
				String yValue = df.format(getYValues().getValue(
						LegendString.ValueIndex));
				df = new DecimalFormat("####.##");
				String zValue = df.format(getZValues().getValue(
						LegendString.ValueIndex));
			
				textArray1 = new String[] {xValue,	" ",yValue,	" ",zValue };
				StringBuffer result = new StringBuffer();
				if (textArray1.length > 0) {
					result.append(textArray1[0]);
					for (int i = 1; i < textArray1.length; i++) {
						result.append("");
						result.append(textArray1[i]);
					}
				}
				return result.toString();
			}
		} else if (this.fTernaryLegendStyle
				.equals(TernaryLegendStyle.ValuesBubble)) {
			if (isPercent)
				return calcPercentSt(LegendString, new ValueList[] {
						getXValues(), getYValues(), getZValues(),
						getRadiusValues() });
			else
			{
				DecimalFormat df = new DecimalFormat("####.##");
				String xValue = df.format(this.getRadiusValues().getValue(
						LegendString.ValueIndex));
				return xValue;
			}
		} else if (this.fTernaryLegendStyle
				.equals(TernaryLegendStyle.ValuesBubbleWeight)) {
			if (isPercent)
				return calcPercentSt(LegendString, new ValueList[] {
						getXValues(), getYValues(), getZValues(),
						getRadiusValues(), getWeightValues() });
			else {
				DecimalFormat df = new DecimalFormat("####.##");
				String xValue = df.format(getXValues().getValue(
						LegendString.ValueIndex));
				df = new DecimalFormat("####.##");
				String yValue = df.format(getYValues().getValue(
						LegendString.ValueIndex));
				df = new DecimalFormat("####.##");
				String zValue = df.format(getZValues().getValue(
						LegendString.ValueIndex));
				df = new DecimalFormat("####.##");
				String radiusValue = df.format(this.getRadiusValues().getValue(
						LegendString.ValueIndex));
				df = new DecimalFormat("####.##");
				String weightValue = df.format(this.getWeightValues().getValue(
						LegendString.ValueIndex));
				
				String[] textArray2 = new String[] {
						
						xValue,
						" ",
						yValue,
						" ",
						zValue,
						" ",
						radiusValue,
						" ",
						weightValue };
				StringBuffer result = new StringBuffer();
				if (textArray2.length > 0) {
					result.append(textArray2[0]);
					for (int i = 1; i < textArray2.length; i++) {
						result.append("");
						result.append(textArray2[i]);
					}
				}
				return result.toString();
			}
		} else if (this.fTernaryLegendStyle.equals(TernaryLegendStyle.Bubble)) {
			if (isPercent)
				return calcPercentSt(LegendString,
						new ValueList[] { getRadiusValues() });
			else
			{
				DecimalFormat df = new DecimalFormat("####.##");
				String radiusValue = df.format(this.getRadiusValues().getValue(
						LegendString.ValueIndex));


				return radiusValue;
			}
		} else if (this.fTernaryLegendStyle
				.equals(TernaryLegendStyle.BubbleWeight)) {
			if (isPercent)
				return calcPercentSt(LegendString, new ValueList[] {
						getRadiusValues(), getWeightValues() });
			else
			{
				DecimalFormat df = new DecimalFormat("####.##");
				String radiusValue = df.format(this.getRadiusValues().getValue(
						LegendString.ValueIndex));
				df = new DecimalFormat("####.##");
				String weightValue = df.format(this.getWeightValues()
						.getValue(LegendString.ValueIndex));
				return (radiusValue
						+ " " + weightValue);
			}
		} else if (this.fTernaryLegendStyle.equals(TernaryLegendStyle.Weight)) {
			if (isPercent)
				return calcPercentSt(LegendString,
						new ValueList[] { getWeightValues() });
			else
			{
				DecimalFormat df = new DecimalFormat("####.##");
				String weightValue = df.format(this.getWeightValues()
						.getValue(LegendString.ValueIndex));
				return weightValue;
			}

		}

		return "";
	}
	
	protected int numSampleValues() {
		return 15;
	}

	public TernaryStyle getTernaryStyle() {
		return this.fTernaryStyle;
	}

	public PointerStyle doGetPointerStyle(int ValueIndex) {
		PointerStyle result = this.getPointer().getStyle();
		Color tmpColor = this.getPointer().getColor();
		return result;
	}
	

	private int legendPaletteIndex(int LegendIndex) {

		PaletteList palette;

		palette = getPalette();
		return ((((palette != null) ? palette.size() : 0) - (LegendIndex)) - 1);
	}

	public boolean clickedPointer(int ValueIndex, int tmpX, int tmpY, int x,
			int y)

	{
		Point P = new Point(0, 0);
		if (getTernaryStyle().equals(TernaryStyle.Bubble)) {
			preparePointer(ValueIndex);
		}
		PointerStyle tmpStyle = doGetPointerStyle(ValueIndex);
		Rectangle R;
		int tmpMidX, tmpMidY;

		R = Rectangle.fromLTRB(tmpX - this.getPointer().getHorizSize(), tmpY
				- this.getPointer().getVertSize(), tmpX
				+ this.getPointer().getHorizSize(), tmpY
				+ this.getPointer().getVertSize());
		P.x = x;
		P.y = y;
		tmpMidX = (R.getLeft() + R.getRight()) / 2;
		tmpMidY = (R.getTop() + R.getBottom()) / 2;
		if (tmpStyle.equals(PointerStyle.HEXAGON) || tmpStyle.equals(PointerStyle.CIRCLE)) {

			return Graphics3D.pointInEllipse(P, R);

		} else if (tmpStyle.equals(PointerStyle.TRIANGLE)) {
			return Graphics3D.pointInTriangle(P, R.getLeft(), R.getRight(),
					R.getBottom(), R.getTop());
		} else if (tmpStyle.equals(PointerStyle.DOWNTRIANGLE)) {
			return Graphics3D.pointInTriangle(P, R.getLeft(), R.getRight(),
					R.getTop(), R.getBottom());
		} else if (tmpStyle.equals(PointerStyle.DIAMOND)) {
			Point point = new Point(0, 0);
			Point point2 = new Point(0, 0);
			Point point3 = new Point(0, 0);
			Point point4 = new Point(0, 0);

			int top, bottom;
			top = R.getTop();
			point.x = tmpMidX;
			point.y = top;
			point2.x = R.getRight();
			point2.y = tmpMidY;
			bottom = R.getBottom();
			point3.x = tmpMidX;
			point3.y = bottom;
			point4.x = R.getLeft();
			point4.y = tmpMidY;
			Point[] poly = new Point[] { point, point2, point3, point4 };
			return Graphics3D.pointInPolygon(P, poly);
		} else if (tmpStyle.equals(PointerStyle.LEFTTRIANGLE)) {
			return Graphics3D.pointInHorizTriangle(P, R.getTop(),
					R.getBottom(), R.getRight(), R.getLeft());
		} else if (tmpStyle.equals(PointerStyle.RIGHTTRIANGLE)) {
			return Graphics3D.pointInHorizTriangle(P, R.getTop(),
					R.getBottom(), R.getLeft(), R.getRight());
		}

		return (((!tmpStyle.equals(PointerStyle.NOTHING) && (Math
				.abs((int) (tmpX - x)) < this.fPointer.getHorizSize())) && (Math
				.abs((int) (tmpY - y)) < this.fPointer.getVertSize())));
	}

	private int clickedPoint(ClickedVars Clicked, int Index) {
		boolean flag;
		int Result = -1;
		int count = this.getXValues().count;
		if (Index >= count) {
			flag = true;
		} else {
			flag = Index == -1;
		}
		if (!flag) {
			Clicked.tmpX = (int) fPlotPoints[Index].getX();
			Clicked.tmpY = (int) fPlotPoints[Index].getY();
			if (this.fPointer.getVisible()
					&& this.clickedPointer(Index, Clicked.tmpX, Clicked.tmpY,
							Clicked.X, Clicked.Y)) {
			return Index;
			}
			if ((Clicked.tmpX == Clicked.X) && (Clicked.tmpY == Clicked.Y)) {
				Result = Index;
			}
		}
		return Result;
	}
	
	public int clicked(int x, int y) {

		ClickedVars Clicked = new ClickedVars();
		int Result = -1;
		Clicked.X = x;
		Clicked.Y = y;
		for (int i = this.lastVisible; i >= this.firstVisible; i--) {
			Result = clickedPoint(Clicked, i);
			if (Result != -1) {
				return Result;
			}
		}
		return Result;
	}
	
	public int countLegendItems() {

		if (super.getUsePalette()) {
			return super.getPaletteSteps();
		}
		return super.getXValues().count;
	}

	
	public void doBeforeDrawChart() {
		if (this.fWeightValues.total == 0f) {
			super.setUsePalette(false);
			super.setUseColorRange(false);
			if (super.getColors() == null) {
				this.setColorEach(true);
			}
		}
		super.doBeforeDrawChart();
	}
	
	protected boolean isPointInChartRect(int valueIndex) {
		return true;
	}

	
	public void prepareForGallery(boolean isEnabled) {
		super.prepareForGallery(isEnabled);
		this.getGradient().setVisible(true);
		this.setTernaryStyle(TernaryStyle.Point);
		this.getPointer().setHorizSize(2);
		this.getPointer().setVertSize(2);
		this.getVertAxis().getLabels().setVisible(false);
		this.getHorizAxis().getLabels().setVisible(false);
		this.getChart().getAspect().setView3D(false);
		this.getChart().getAxes().getRight().getLabels().setVisible(false);
		this.fVertexTitles[0].clear();
		this.fVertexTitles[1].clear();
		this.fVertexTitles[2].clear();
		this.fillSampleValues();
	}

	protected void prepareLegendCanvas(Graphics3D g, int valueIndex,
			Color backColor, ChartBrush aBrush) {
		aBrush.getGradient().setVisible(false);
	}

	protected void preparePointer(int ValueIndex) {
		int tmpSize = Utils.round(this.getRadiusValues().getValue(ValueIndex));
		this.getPointer().setHorizSize(tmpSize);
		this.getPointer().setVertSize(tmpSize);
	}

	public void setAxisScale(TernaryAxisScale Value) {
		if (this.fAxisScale != Value) {
			this.fAxisScale = Value;
			super.repaint();
		}
	}

	
	public void setChart(IBaseChart c) {
		super.setChart(c);
		if (this.fPointer != null) {
			this.getPointer().setChart(c);
		}
		if ((this.getChart() != null)) {
			this.getChart().getAspect().setView3D(false);
		}
	}

	public void setGradient(Gradient Value) {
		this.getBrush().setGradient(Value);
	}

	public void setLinesPen(ChartPen Value) {
		this.fLinesPen = Value;
	}

	public void setPlotFormat(TernaryFormat Value) {
		this.fPlotFormat = Value;
	}

	public void setTernaryLegendStyle(TernaryLegendStyle Value) {
		if (this.fTernaryLegendStyle != Value) {
			this.fTernaryLegendStyle = Value;
			super.repaint();
		}
	}
        
        public TernaryMarkStyle getTernaryMarkStyle() {
		return fTernaryMarkStyle;
	}

	public void setTernaryMarkStyle(TernaryMarkStyle Value) {
		if (this.fTernaryMarkStyle != Value) {
			this.fTernaryMarkStyle = Value;
			super.repaint();
		}
	}

	public void setTernaryStyle(TernaryStyle Value) {
		if (this.fTernaryStyle != Value) {
			this.fTernaryStyle = Value;
			super.repaint();
		}
	}

    private void writeRightLabel( DrawLabelsVars DrawLabels, Point location, double angle, boolean rotate)
    {
      Graphics3D canvas = (Graphics3D) this.getChart().getGraphics3D();
      if (rotate)
      {
        int txtWidth = canvas.textWidth(DrawLabels.labelStr);
        int horizOffset = Math.abs(Utils.round(txtWidth * Math.sin(30))) / 3;
        int vertOffset = Math.abs(Utils.round(txtWidth * Math.cos(30)))+canvas.textHeight("H")+5;
        canvas.rotateLabel(location.x+horizOffset, location.y-vertOffset, DrawLabels.labelStr, angle);

      }
      else
      {
        canvas.textOut((location.x + DrawLabels.maxLabelWidth) - 5, location.y - Utils.round((canvas.textHeight(DrawLabels.labelStr) / 4) * 3), DrawLabels.labelStr);
      }
    }
    
    /**
     * Gets descriptive text.
     *
     * @return String
     */
    public String getDescription() {
        return Language.getString("TernarySeries");
    }

	public TernaryAxisScale getAxisScale() {
		return fAxisScale;
	}

	public Gradient getGradient() {
		return getBrush().getGradient();
	}

	public String getLineSeparator() {
		return this.getMarks().getMultiLine() ? "\n" : "  ";
	}

	public ChartPen getLinesPen() {
		if (this.fLinesPen == null) {
			this.fLinesPen = new ChartPen(getChart());
		}
		return this.fLinesPen;
	}


	public TernaryMarkStyle getMarkStyle() {
		return this.fTernaryMarkStyle;
	}

	public void setMarkStyle(TernaryMarkStyle value) {
		fTernaryMarkStyle = value;
	}

	public TernaryFormat getPlotFormat() {
		return this.fPlotFormat;
	}

	public SeriesPointer getPointer() {
		if (this.fPointer == null) {
			this.fPointer = new SeriesPointer(getChart(), this);
		}
		return this.fPointer;
	}

	public void setPointer(SeriesPointer value) {
		fPointer = value;
	}

	public ValueList getRadiusValues() {
		return this.fRadiusValues;
	}

	public void setRadiusValues(ValueList value) {
		fRadiusValues = value;
	}

	public boolean getRotateLabels() {
		return this.fRotateLabels;
	}

	public void setRotateLabels(boolean value) {
		fRotateLabels = value;
	}

	public TernaryLegendStyle getTernaryLegendStyle() {
		return this.fTernaryLegendStyle;
	}

	public Point getVertexA() {
		return fVertexA;
	}

	public Point getVertexB() {
		return fVertexB;
	}

	public Point getVertexC() {
		return fVertexC;
	}

	public ValueList getWeightValues() {
		return this.fWeightValues;
	}
    private void writeBottomLabel(DrawLabelsVars DrawLabels, Point location, double angle, boolean rotate)
    {
      Graphics3D canvas = (Graphics3D) this.getChart().getGraphics3D();
      if (rotate)
      {
        int txtWidth = canvas.textWidth(DrawLabels.labelStr);
        int vertOffset = Math.abs(Utils.round(txtWidth * Math.sin(60)));
        int horizOffset = Math.abs(Utils.round(txtWidth * Math.cos(60)));
        canvas.rotateLabel(location.x+horizOffset+5, location.y+vertOffset+10, DrawLabels.labelStr, angle);
      }
      else
      {
        canvas.textOut(location.x - Utils.round(canvas.textWidth(DrawLabels.labelStr) / 2.0F), location.x + 2, DrawLabels.labelStr);
      }
    }
	public void setWeightValues(ValueList value) {
		fWeightValues = value;
	}
	public static boolean isNullOrEmpty(String value)
	{
	      if (value != null)
	      {
	            return (value.length() == 0);
	      }
	      return true;
	}

}
