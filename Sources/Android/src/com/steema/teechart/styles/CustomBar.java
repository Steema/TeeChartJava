package com.steema.teechart.styles;

import com.steema.teechart.editors.gallery.Gallery;
import com.steema.teechart.languages.Language;

import com.steema.teechart.Rectangle;
import com.steema.teechart.drawing.Color;
import com.steema.teechart.drawing.IGraphics3D;
import com.steema.teechart.drawing.ChartPen;
import com.steema.teechart.drawing.Point;

import java.util.ArrayList;
import java.util.List;

import com.steema.teechart.IBaseChart;
import com.steema.teechart.drawing.Gradient;
import com.steema.teechart.drawing.ChartBrush;
import com.steema.teechart.axis.Axis;
import com.steema.teechart.drawing.Graphics3D.RoundRectangleStyle;

import com.steema.teechart.misc.Utils;

/**
 * 
 * <p>
 * Title: CustomBar class
 * </p>
 * 
 * <p>
 * Description: Custom Bar Series.
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
public class CustomBar extends Series {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected int barSizePercent = 70;
	protected int conePercent;
	protected boolean bDark3D = true;
	protected MultiBars iMultiBar = MultiBars.SIDE;
	protected boolean bUseOrigin = true;
	protected boolean marksOnBar = false;
	protected double dOrigin;
	protected ChartPen pPen;
	private MarksLocation marksLocation = MarksLocation.End;
	// private boolean autoBarSize;
	private boolean autoMarkPosition = true;
	private BarStyle barStyle = BarStyle.RECTANGLE;
	private boolean gradientRelative;
	private ChartPen tickLines;
	private int roundSize = 16;
	private BarRounding barRound = BarRounding.AtValue;

	private int depthPercent = 100;
	private int offsetPercent;
	private boolean sideMargins = true;
	private int stackGroup;

	// internal
	protected Rectangle iBarBounds = new Rectangle(Rectangle.EMPTY);
	protected int iNumBars;
	private int iMaxBarPoints;
	protected int iOrderPos;
	protected int iPreviousCount;

	// Stack groups
	private int[] groups = new int[100];
	private int numGroups;

	// protected
	protected int iBarSize;
	protected int customBarSize;

	protected Color normalBarColor;

	public enum BarRounding {
		// <summary>
		// No rounding
		// </summary>
		None,
		// <summary>
		// Round end that represents value
		// </summary>
		AtValue,
		// <summary>
		// / Round both ends of Bar
		// / </summary>
		BothEnds
	};
        
	public enum MarksLocation {
		// / <summary>
		// /Set the alignment at start position of the Marks into the Bar.
		// / </summary>
		Start,
		// / <summary>
		// /Set the alignment at center position of the Marks into the Bar.
		// / </summary>
		Center,
		// / <summary>
		// /Set the alignment at end position of the Marks into the Bar.
		// / </summary>
		End
	};

	public CustomBar(IBaseChart c) {
		super(c);

		getMarks().setVisible(true);
		marks.setDefaultVisible(true);
		marks.getCallout().setLength(20);
		drawBetweenPoints = false;

		getGradient().setStartColor(Color.EMPTY);
		getGradient().setEndColor(Color.EMPTY);
	}

	public CustomBar() {
		this((IBaseChart) null);
	}

	transient private BarStyleResolver styleResolver;

	public void setBarStyleResolver(BarStyleResolver resolver) {
		this.styleResolver = resolver;
	}

	public void removeBarStyleResolver() {
		this.styleResolver = null;
	}

	public boolean getMarksOnBar() {
		return marksOnBar;
	}

	public void setMarksOnBar(boolean value) {
		marksOnBar = value;

		this.invalidate();
	}

	public MarksLocation getMarksLocation() {
		return marksLocation;
	}

	public void setMarksLocation(MarksLocation value) {
		if (marksLocation != value) {
			marksLocation = value;
			invalidate();
		}
	}

	@Override
	public void assign(Series source) {
		super.assign(source);
		if(source instanceof CustomBar)
		{
			this.setBarSizePercent(((CustomBar) source).getBarSizePercent());
			this.setDepthPercent(((CustomBar) source).getDepthPercent());
			this.setOffsetPercent(((CustomBar) source).getOffsetPercent());
			this.setConePercent(((CustomBar) source).getConePercent());
			this.setDark3D(((CustomBar) source).getDark3D());
			this.setMultiBar(((CustomBar) source).getMultiBar());
			this.setUseOrigin(((CustomBar) source).getUseOrigin());
			this.setMarksOnBar(((CustomBar) source).getMarksOnBar());
			this.setOrigin(((CustomBar) source).getOrigin());
			this.setMarksLocation(((CustomBar) source).getMarksLocation());
			this.setAutoMarkPosition(((CustomBar) source).getAutoMarkPosition());
			this.setBarStyle(((CustomBar) source).getBarStyle());
			this.setGradientRelative(((CustomBar) source).getGradientRelative());
			this.setRoundSize(((CustomBar) source).getRoundSize());
			this.setBarRound(((CustomBar) source).getBarRound());
			this.setSideMargins(((CustomBar) source).getSideMargins());
			this.setStackGroup(((CustomBar) source).getStackGroup());
			this.iBarBounds=((CustomBar) source).iBarBounds;
			this.iMaxBarPoints=((CustomBar) source).iMaxBarPoints;
			this.iOrderPos=((CustomBar) source).iOrderPos;
			this.iPreviousCount=((CustomBar) source).iPreviousCount;
			this.iBarSize=((CustomBar) source).iBarSize;
			this.customBarSize=((CustomBar) source).customBarSize;
                        this.allowSinglePoint = ((CustomBar) source).allowSinglePoint;
                        this.groups = ((CustomBar) source).groups;
                        this.normalBarColor = ((CustomBar) source).normalBarColor;
                        this.roundSize = ((CustomBar) source).roundSize;
                        this.styleResolver = ((CustomBar) source).styleResolver;
                        this.tickLines.assign(((CustomBar) source).tickLines);

		}
	}

	private Gradient internalGradient = new Gradient();

	/**
	 * Defines the color Gradient used to fill the Bars.<br>
	 * These can be filled with these three colors: StartColor, MidColor,
	 * EndColor. You can control the drawing output by setting the Direction
	 * method.<br>
	 * Use the Visible property to show / hide filling. <br>
	 * 
	 * <p>
	 * Example:
	 * 
	 * <pre>
	 * <font face="Courier" size="4">
	 *  barSeries = new Bar(myChart.getChart());
	 *  barSeries.getMarks().setVisible(true);
	 *  barSeries.fillSampleValues(6);
	 *  barSeries.setColor(Color.RED);
	 *  barSeries.setBarStyle(BarStyle.RECTGRADIENT);
	 *  barSeries.getGradient().setDirection(GradientDirection.VERTICAL);
	 *  barSeries.getGradient().setStartColor(Color.GREEN);
	 *  barSeries.getGradient().setUseMiddle(false);
	 *  barSeries.getGradient().setMiddleColor(Color.YELLOW);
	 * </font>
	 * </pre>
	 * 
	 * </p>
	 * 
	 * @return Gradient
	 */
	public Gradient getGradient() {
		return internalGradient;
		// return getBrush().getGradient();
	}

	/**
	 * Calculates Colors based on highest bar when Gradient is Visible.<br>
	 * Default value: false
	 * 
	 * @return boolean
	 */
	public boolean getGradientRelative() {
		return gradientRelative;
	}

	/**
	 * Calculates Colors based on highest bar when Gradient is Visible.<br>
	 * Default value: false
	 * 
	 * @param value
	 *            boolean
	 */
	public void setGradientRelative(boolean value) {
		gradientRelative = setBooleanProperty(gradientRelative, value);
	}

	/**
	 * Allows stacking independent Series within the same Chart, in series
	 * groups.<br>
	 * Default value: 0
	 * 
	 * @return int
	 */
	public int getStackGroup() {
		return stackGroup;
	}

	/**
	 * Allows stacking independent Series within the same Chart, in series
	 * groups.<br>
	 * Default value: 0 <br>
	 * 
	 * <p>
	 * Example:
	 * 
	 * <pre>
	 * <font face="Courier" size="4">
	 * bar1Series.setStackGroup(0);
	 * bar2Series.setStackGroup(1);
	 * </font>
	 * </pre>
	 * 
	 * </p>
	 * 
	 * @param value
	 *            int
	 */
	public void setStackGroup(int value) {
		stackGroup = setIntegerProperty(stackGroup, value);
	}

	public void setZPositions() {
		super.setZPositions();

		if (depthPercent != 0) {
			int tmp = (int) ((getEndZ() - getStartZ()) * (100 - depthPercent) * 0.005);
			setStartZ(getStartZ() + tmp);
			setEndZ(getEndZ() - tmp);
		}
	}

	/**
	 * Determines the percent amount of bar size in "z" depth direction. Default
	 * value: 100%
	 * 
	 * @return int
	 */
	public int getDepthPercent() {
		return depthPercent;
	}

	/**
	 * Determines the percent amount of bar size in "z" depth direction. Default
	 * value: 100%
	 * 
	 * <p>
	 * Example:
	 * 
	 * <pre>
	 * <font face="Courier" size="4">
	 * series.setDepthPercent(50) ; // % of 3D depth
	 * </font>
	 * </pre>
	 * 
	 * </p>
	 * 
	 * @param value
	 *            int
	 */
	public void setDepthPercent(int value) {
		depthPercent = setIntegerProperty(depthPercent, value);
	}

	/**
	 * Darkens sides of bars to enhance 3D effect.<br>
	 * This has effect only when Chart.Aspect.View3D is true. High color video
	 * modes (greater than 256 colors) will show dark colors much better than
	 * 256 or 16 color modes. <br>
	 * Default value: true
	 * 
	 * @return boolean
	 */
	public boolean getDark3D() {
		return bDark3D;
	}

	/**
	 * Darkens sides of bars to enhance 3D effect.<br>
	 * Default value: true
	 * 
	 * @param value
	 *            boolean
	 */
	public void setDark3D(boolean value) {
		bDark3D = setBooleanProperty(bDark3D, value);
	}

	public void prepareForGallery(boolean isEnabled) {
		super.prepareForGallery(isEnabled);
		barSizePercent = 85;
		setMultiBar(MultiBars.NONE);
	}

	protected void setBarSizePercent(int value) {
		barSizePercent = setIntegerProperty(barSizePercent, value);
	}

	public int getBarSizePercent() {
		return barSizePercent;
	}
    
	private void setOtherBars(boolean setOthers) {
		if (chart != null) {
			for (int t = 0; t < chart.getSeriesCount(); t++) {

				ISeries s = chart.getSeries(t);

				if (sameClass(s)) {
					CustomBar tmpBar = (CustomBar) s;

					if (setOthers) {
						tmpBar.iMultiBar = iMultiBar;
						tmpBar.sideMargins = sideMargins;
					} else {
						iMultiBar = tmpBar.iMultiBar;
						sideMargins = tmpBar.sideMargins;
						break;
					}

					tmpBar.calcVisiblePoints = iMultiBar != MultiBars.SELFSTACK;
				}
			}
		}
	}

	/**
	 * Margin between Chart rectangle and Bars.<br>
	 * Default value: true
	 * 
	 * @return boolean
	 */
	public boolean getSideMargins() {
		return sideMargins;
	}

	/**
	 * Sets a margin between Chart rectangle and Bars.<br>
	 * Default value: true
	 * 
	 * @param value
	 *            boolean
	 */
	public void setSideMargins(boolean value) {
		sideMargins = setBooleanProperty(sideMargins, value);
		setOtherBars(true);
	}

	protected boolean shouldSerializeYOrigin() {
		return false;
	}

	/**
	 * Obsolete.&nbsp;Please use CustomBar.<!-- -->Origin instead.
	 * 
	 * @return double
	 */
	public double getYOrigin() {
		return dOrigin;
	}

	/**
	 * Obsolete.&nbsp;Please use CustomBar.<!-- -->Origin instead.
	 * 
	 * @param value
	 *            double
	 */
	public void setYOrigin(double value) {
		setOrigin(value);
	}

	/**
	 * Bars to be bottom aligned at the Origin method value. <br>
	 * When false, the minimum of all Bar values is used as the Bar origins
	 * value. <br>
	 * When true, the Origin property is used as the start point for Bars. <br>
	 * Default value: true
	 * 
	 * @return boolean
	 */
	public boolean getUseOrigin() {
		return bUseOrigin;
	}

	/**
	 * Allows Bars to be bottom aligned at the Origin method value. <br>
	 * Default value: true
	 * 
	 * @param value
	 *            boolean
	 */
	public void setUseOrigin(boolean value) {
		bUseOrigin = setBooleanProperty(bUseOrigin, value);
	}

	/**
	 * The common bottom value used for all Bar points.<br>
	 * UseOrigin must be true (the default) to use the Origin property. <br>
	 * Bars with a value bigger than Origin are drawn in one direction and Bars
	 * with a lower value are drawn in the opposite direction. This applies both
	 * to Bar series and HorizBar series classes.<br>
	 * Default value: 0
	 * 
	 * @return double
	 */
	public double getOrigin() {
		return dOrigin;
	}

	/**
	 * Sets the common bottom value used for all Bar points.<br>
	 * Default value: 0
	 * 
	 * @param value
	 *            double
	 */
	public void setOrigin(double value) {
		dOrigin = setDoubleProperty(dOrigin, value);
	}

	/**
	 * Repositions Marks on BarSeries to prevent overlapping.<br>
	 * Marks are displaced to the top of the Bars to minimize the overlapping
	 * effect of Marks with long text or big font sizes.<br>
	 * When false, no checking is performed and all Marks are drawn at their
	 * Mark.ArrowLength distance to the top of the Bar.<br>
	 * Default value: false
	 * 
	 * @return boolean
	 */
	public boolean getAutoMarkPosition() {
		return autoMarkPosition;
	}

	/**
	 * Repositions Marks on BarSeries to prevent overlapping.<br>
	 * Default value: false
	 * 
	 * @param value
	 *            boolean
	 */
	public void setAutoMarkPosition(boolean value) {
		autoMarkPosition = value;
	}

	/**
	 * The apex size as percentage of the base.<br>
	 * Default value: 0
	 * 
	 * @return int
	 */
	public int getConePercent() {
		return conePercent;
	}

	/**
	 * Sets the apex size as a percentage of the base.<br>
	 * Default value: 0
	 * 
	 * @param value
	 *            int
	 */
	public void setConePercent(int value) {
		conePercent = setIntegerProperty(conePercent, value);
	}

	/**
	 * The Bar displacement as percentage of Bar size.<br>
	 * Displacement is horizontal for Bar series and vertical for HorizBar
	 * series. It can be used to create "overlayed" Bar charts. You can specify
	 * positive or negative values. <br>
	 * Default value: 0
	 * 
	 * @return int
	 */
	public int getOffsetPercent() {
		return offsetPercent;
	}

	/**
	 * Sets the Bar displacement as percentage of Bar size.<br>
	 * Default value: 0
	 * 
	 * @param value
	 *            int
	 */
	public void setOffsetPercent(int value) {
		offsetPercent = setIntegerProperty(offsetPercent, value);
	}

	/**
	 * Defines the Bar shape used to draw Bars.<br>
	 * Default value: BarStyle.Rectangle
	 * 
	 * 
	 * @return BarStyle
	 */
	public BarStyle getBarStyle() {
		return barStyle;
	}

    /**
     * Defines the Bar shape used to draw Bars.<br>
     * Default value: BarStyle.Rectangle
     *
     *
     * @param value BarStyle
     */
    public void setBarStyle(BarStyle value) {
        if (barStyle != value) {
            barStyle = value;
            if (barStyle == BarStyle.RECTGRADIENT) {
                getGradient().setVisible(true);
            }
            invalidate();
        }
    }

    /**
     * Amount in pixels of the bar end rounding.<br>
     * Used only when BarStyle property is RoundRectangle
     *
     * @return int
     */
    public int getRoundSize() {
        return roundSize;
    }

    /**
     * Amount in pixels of the bar end rounding.<br>
     * Used only when BarStyle property is RoundRectangle
     *
     * @param value int
     */
    public void setRoundSize(int value) {
        roundSize = setIntegerProperty(roundSize, value);
    }
        
    /**
     * Describes what ends of the Bar are rounded.<br>
     * Used only when BarStyle property is RoundRectangle
     *
     * @return BarRounding
     */
    public BarRounding getBarRound() {
        return barRound;
    }

    /**
     * Describes what ends of the Bar are rounded.<br>
     * Used only when BarStyle property is RoundRectangle
     *
     * @param value BarRounding
     */
    public void setBarRound(BarRounding value) {
        if (barRound != value) {
            barRound = value;
        }
    }
        
	/**
	 * Defines the Brush used to fill Bars.<br>
	 * When BarBrush.Style is different than bsSolid, the Series.Color color is
	 * the background bar color.
	 * 
	 * @return ChartBrush
	 */
	public ChartBrush getBrush() {
		return bBrush;
	}

	public void setChart(IBaseChart c) {
		super.setChart(c);
		if (pPen != null) {
			pPen.setChart(chart);
		}
		if (bBrush != null) {
			bBrush.setChart(chart);
		}
		if (tickLines != null) {
			tickLines.setChart(chart);
		}
		setOtherBars(false);
	}

	/**
	 * Determines how multiple BarSeries will be displayed in the same Chart.<br>
	 * If you have more than one Bar series in the same Chart, then you can
	 * choose if they will be drawn side-by-side, back-to-front or Stacked.<br>
	 * Side-by-side means the Bar width will be divided by the number of Bar
	 * Series. <br>
	 * Default value: MultiBars.Side
	 * 
	 * @return MultiBars
	 */
	public MultiBars getMultiBar() {
		return iMultiBar;
	}

	/**
	 * Determines how multiple BarSeries will be displayed in the same Chart.<br>
	 * Default value: MultiBars.Side
	 * 
	 * @param value
	 *            MultiBars
	 */
	public void setMultiBar(MultiBars value) {
		if (iMultiBar != value) {
			iMultiBar = value;
			setOtherBars(true);
			invalidate();
		}
	}

	/**
	 * Pen used to draw the Bar rectangles.<br>
	 * You can set BarPen.Visible = false to hide these lines.
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
	 * Pen used to draw "tick" lines across each bar.
	 * 
	 * @return ChartPen
	 */
	public ChartPen getTickLines() {
		if (tickLines == null) {
			tickLines = new ChartPen(chart, Color.BLACK, false);
		}
		return tickLines;
	}

	/**
	 * Coordinates of current Bar point being displayed.<br>
	 * Run-time and read-only. <br>
	 * This function returns an internal variable, that is calculated only when
	 * the Bar Series DrawValue method is called.<br>
	 * You should only use BarBounds in custom-derived series, after calling the
	 * "inherited DrawValue" method.
	 * 
	 * @return Rectangle
	 */
	public Rectangle getBarBounds() {
		return iBarBounds;
	}

	protected int getBarBoundsMidX() {
		return (iBarBounds.getLeft() + iBarBounds.getRight()) / 2;
	}

	private void calcGradientColor(int valueIndex) {
		Gradient g = getGradient();

		// [B423] [B425]

		if (g.getEndColor() == Color.EMPTY)
			g.setEndColor(Color.WHITE);

		if (g.getStartColor() == Color.EMPTY)
			g.setStartColor(normalBarColor);

		if (gradientRelative) {
			double tmp = bUseOrigin ? dOrigin : mandatory.getMinimum();
			double tmpRatio = (mandatory.value[valueIndex] - tmp)
					/ (mandatory.getMaximum() - tmp);

			int t0 = g.getEndColor().getRed();
			int t1 = g.getEndColor().getGreen();
			int t2 = g.getEndColor().getBlue();

			bBrush.getGradient().setStartColor(
					Color.fromArgb(
							g.getStartColor().getAlpha(),
							(t0 + Utils.round(tmpRatio
									* (g.getStartColor().getRed() - t0))),
							(t1 + Utils.round(tmpRatio
									* (g.getStartColor().getGreen() - t1))),
							(t2 + Utils.round(tmpRatio
									* (g.getStartColor().getBlue() - t2)))));
			bBrush.getGradient().setEndColor(g.getEndColor());

		} else {
			bBrush.getGradient().setStartColor(g.getStartColor());
			bBrush.getGradient().setMiddleColor(g.getMiddleColor());
			bBrush.getGradient().setEndColor(g.getEndColor());
		}
	}

	protected BarStyle doGetBarStyle(int valueIndex) {
		BarStyle style = barStyle;
		if (styleResolver != null) {
			style = styleResolver.getStyle(this, valueIndex, style);
		}
		return style;
	}

	protected int internalCalcMarkLength(int valueIndex) { // abstract
		return 0;
	}

	private double internalPointOrigin(int valueIndex, boolean sumAll) {
		double result = 0;
		double tmpValue = mandatory.value[valueIndex];

		if (chart != null) {
			for (int t = 0; t < chart.getSeriesCount(); t++) {
				ISeries s = chart.getSeries(t);
				if ((!sumAll) && (s == this)) {
					break;
				} else if (s.getActive() && sameClass(s)
						&& (s.getCount() > valueIndex)
						&& (((CustomBar) s).stackGroup == stackGroup)) {

					double tmp = s.getOriginValue(valueIndex);
					if (tmpValue < 0) {
						if (tmp < 0) {
							result += tmp;
						}
					} else if (tmp > 0) {
						result += tmp; /* 5.01 */
					}
				}
			}
		}
		return result;
	}

	protected void doGradient3D(int valueIndex, Point p0, Point p1) {
		if (pPen.getVisible()) {
			p0.x++;
			p0.y++;
			int w = Utils.round(pPen.getWidth()) - 1;
			p1.x -= w;
			p1.y -= w;
		}

		calcGradientColor(valueIndex);
		bBrush.getGradient()
				.draw(chart.getGraphics3D(), p0.x, p0.y, p1.x, p1.y);
	}

	/**
	 * For internal use.<br>
	 * Displays a Bar or HorizBar point using the parameter coordinates and the
	 * BarColor parameter. <br>
	 * It is internally called for each point in the Series. <br>
	 * The BarStyle property determines the type of graphical representation of
	 * every point (ie: Rectangle, Ellipse, Pyramid, etc).
	 * 
	 * @param barColor
	 *            Color
	 * @param r
	 *            Rectangle
	 */
	public void barRectangle(Color barColor, Rectangle r) {
		barRectangle(barColor, r, -1, false);
	}

	/**
	 * For internal use.<br>
	 * Displays a Bar or HorizBar point using the parameter coordinates and the
	 * BarColor parameter. <br>
	 * It is internally called for each point in the Series. <br>
	 * The BarStyle property determines the type of graphical representation of
	 * every point (ie: Rectangle, Ellipse, Pyramid, etc).
	 * 
	 * @param barColor
	 *            Color
	 * @param aLeft
	 *            int
	 * @param aTop
	 *            int
	 * @param aRight
	 *            int
	 * @param aBottom
	 *            int
	 */
	public void barRectangle(Color barColor, int aLeft, int aTop, int aRight,
			int aBottom) {
            barRectangle(barColor, new Rectangle(aLeft, aTop, aRight, aBottom), -1, false);
	}

	/**
	 * For internal use.<br>
	 * Displays a Bar or HorizBar point using the parameter coordinates and the
	 * BarColor parameter. <br>
	 * It is internally called for each point in the Series. <br>
	 * The BarStyle property determines the type of graphical representation of
	 * every point (ie: Rectangle, Ellipse, Pyramid, etc).
	 * 
	 * @param barColor
	 *            Color
	 * @param r
	 *            Rectangle
	 * @param barIndex 
	 *            int
	 * @param rounded
	 *            boolean
	 */
    public void barRectangle(Color barColor, Rectangle r, int barIndex, boolean rounded) {
        IGraphics3D g = chart.getGraphics3D();
        if (bBrush.getSolid()) {
            if ((r.getRight() == r.getLeft()) || (r.getTop() == r.getBottom())) {
                g.getPen().setColor(g.getBrush().getColor());
                g.getPen().setVisible(true);
                g.line(r.getLeft(), r.getTop(), r.getRight(), r.getBottom());
            } else if ((Math.abs(r.getRight() - r.getLeft()) < g.getPen().getWidth())
                    || (Math.abs(r.getBottom() - r.getTop()) < g.getPen().getWidth())) {
                g.getPen().setColor(g.getBrush().getColor());
                g.getPen().setVisible(true);
                g.getBrush().setVisible(false);
            }
        }

        if (rounded) {
            switch (getBarRound()) {
                case None:
                    g.rectangle(r);
                    break;
                case AtValue:
                    g.roundRectangle(r, roundSize, roundSize, 0, mandatory.getValue(barIndex) < 0
                            ? (yMandatory ? RoundRectangleStyle.Bottom : RoundRectangleStyle.Left)
                            : (yMandatory ? RoundRectangleStyle.Top : RoundRectangleStyle.Right));
                    break;
                case BothEnds:
                    g.roundRectangle(r, roundSize);
                    break;
            }
        } else {
            g.rectangle(r);
        }
    }
        
	protected void doBarGradient(int valueIndex, Rectangle rect) {
		calcGradientColor(valueIndex);
		bBrush.getGradient().draw(chart.getGraphics3D(), rect);
		if (pPen.getVisible()) {
			chart.getGraphics3D().getBrush().setVisible(false);
			barRectangle(normalBarColor, iBarBounds);
		}
	}

	public int getCustomBarWidth() {
		return customBarSize;
	}

	public void setCustomBarWidth(int value) {
		customBarSize = value;
		chart.invalidate();
	}

	private void doCalcBarWidth() {

		if (customBarSize != 0) {
			iBarSize = customBarSize;
		} else if (iMaxBarPoints > 0) {
			Axis tmpAxis = yMandatory ? getHorizAxis() : getVertAxis();

			int tmp = 0;

			// if ( autoBarSize )
			// tmp=Utils.round(tmpAxis.IAxisSize/(2.0+tmpAxis.Maximum-tmpAxis.Minimum));
			// else
			{
				if (sideMargins) {
					iMaxBarPoints++;
				}
				tmp = tmpAxis.iAxisSize / iMaxBarPoints;
			}

			iBarSize = Utils.round((barSizePercent * 0.01) * tmp)
					/ Math.max(1, iNumBars);
			if ((iBarSize % 2) == 1) {
				iBarSize++;
			}
		} else {
			iBarSize = 0;
		}
	}

	/**
	 * Returns side margin amount in pixels.
	 * 
	 * @return int
	 */
	public int barMargin() {
		int result = iBarSize;
		if (iMultiBar != MultiBars.SIDEALL) {
			result *= iNumBars;
		}
		if (!sideMargins) {
			result /= 2;
		}
		return result;
	}

	protected void internalApplyBarMargin(Margins margins) {
		doCalcBarWidth();
		int tmp = barMargin();
		margins.min += tmp;
		margins.max += tmp;
	}

	protected Rectangle calcBarBounds(int valueIndex) {
		return Utils.emptyRectangle();
	}

	protected boolean isPointInChartRect(int valueIndex) {
		Rectangle r1, r2;

		// CDI TF02013714
		r1 = chart.getChartRect();
		r2 = calcBarBounds(valueIndex);

		if (r1.width == 0) {
			r1.x = r1.getLeft() - 1;
			r1.width++;
		}
		if (r1.height == 0) {
			r1.y = r1.getTop() - 1;
			r1.height++;
		}
		if (r2.width == 0) {
			r2.x = r2.getLeft() - 1;
			r2.width++;
		}
		if (r2.height == 0) {
			r2.y = r2.getTop() - 1;
			r2.height++;
		}

		return Utils.intersectWithRect(r1, r2);
	}

	// CDI TF02014567
	protected void calcFirstLastVisibleIndex(boolean recursive) {
		super.calcFirstLastVisibleIndex();

		boolean isRangeZero = yMandatory ? getHorizAxis().iRangezero
				: getVertAxis().iRangezero;

		if (!isRangeZero) {
			while ((lastVisible < getCount() - 1)
					&& isPointInChartRect(lastVisible + 1)) {
				++lastVisible;
			}
			while ((lastVisible > -1)
					&& (lastVisible < getCount() - 1)
					&& (notMandatory.value[lastVisible] == notMandatory.value[lastVisible + 1])) {
				++lastVisible;
			}

			while ((firstVisible > 0) && isPointInChartRect(firstVisible - 1)) {
				--firstVisible;
			}
			while ((firstVisible > 0)
					&& (notMandatory.value[firstVisible] == notMandatory.value[firstVisible - 1])) {
				--firstVisible;
			}
		}

		if ((getMultiBar() == MultiBars.SIDEALL) && (recursive)) {
			if (chart != null)
				for (int i = 0; i < chart.getSeriesCount(); i++)
					if ((chart.getSeries(i).getClass() == this.getClass())
							&& (chart.getSeries(i) != this)) {
						((CustomBar) chart.getSeries(i))
								.calcFirstLastVisibleIndex(false);
						lastVisible += (chart.getSeries(i).lastVisible + 1);
					}
		}
	}

	public void calcFirstLastVisibleIndex() {
		calcFirstLastVisibleIndex(true);
	}

	protected int lastVisibleMark() {
		calcFirstLastVisibleIndex(false);
		return lastVisible;
	}

	protected int internalGetOriginPos(int valueIndex, int defaultOrigin) {
		int result = 0;

		double tmpValue = pointOrigin(valueIndex, false);

		if ((iMultiBar == MultiBars.STACKED)
				| (iMultiBar == MultiBars.SELFSTACK)) {
			result = calcPosValue(tmpValue);
		} else if (iMultiBar == MultiBars.STACKED100) {
			double tmp = pointOrigin(valueIndex, true);
			result = (tmp != 0) ? calcPosValue(tmpValue * 100.0 / tmp) : 0;
		} else {
			result = bUseOrigin ? calcPosValue(tmpValue) : defaultOrigin;
		}

		return result;
	}

	protected double maxMandatoryValue(double value) {
		double result = 0;

		if (iMultiBar == MultiBars.STACKED100) {
			result = 100;
		} else {
			result = value;

			if (iMultiBar == MultiBars.SELFSTACK) {
				result = mandatory.getTotal();
			} else if (iMultiBar == MultiBars.STACKED) {
				for (int t = 0; t < getCount(); t++) {
					double tmp = pointOrigin(t, false) + mandatory.value[t];
					if (tmp > result) {
						result = tmp;
					}
				}
			}
			if (bUseOrigin && (result < dOrigin)) {
				result = dOrigin;
			}
		}
		return result;
	}

	/**
	 * For Internal Use.<br>
	 * The PointOrigin function returns the summed values of more than one
	 * Series point.<br>
	 * It's only used by Series types with Stacked or Stacked 100% styles such
	 * as BarSeries, HorizBar series and Area series.
	 * 
	 * @param valueIndex
	 *            int
	 * @param sumAll
	 *            boolean
	 * @return double
	 */
	public double pointOrigin(int valueIndex, boolean sumAll) {
		if ((iMultiBar == MultiBars.STACKED)
				|| (iMultiBar == MultiBars.STACKED100)) {
			return internalPointOrigin(valueIndex, sumAll);
		} else if (iMultiBar == MultiBars.SELFSTACK) {
			double result = 0;

			if (mandatory.value[valueIndex] >= 0) {
				for (int t = 0; t < valueIndex; t++) {
					if (mandatory.value[t] >= 0)
						result += mandatory.value[t];
				}
			} else {
				for (int t = 0; t < valueIndex; t++) {
					if (mandatory.value[t] < 0)
						result += mandatory.value[t];
				}
			}
			return result;
		} else {
			return dOrigin;
		}
	}

	protected double minMandatoryValue(double value) {
		double result = 0;

		if (iMultiBar == MultiBars.STACKED100) {
			result = 0;
		} else {
			result = value;
			if ((iMultiBar == MultiBars.STACKED)
					|| (iMultiBar == MultiBars.SELFSTACK)) {
				for (int t = 0; t < getCount(); t++) {
					double tmp = pointOrigin(t, false) + mandatory.value[t];
					if (tmp < result) {
						result = tmp;
					}
				}
			}
			if (bUseOrigin && (result > dOrigin)) {
				result = dOrigin;
			}
		}
		return result;
	}

	public void calcZOrder() {

		if (iMultiBar == MultiBars.NONE) {
			super.calcZOrder();
		} else {
			int tmpZOrder = -1;
			for (int t = 0; t < chart.getSeriesCount(); t++) {
				ISeries s = chart.getSeries(t);
				if (s.getActive()) {
					if (s == this) {
						break;
					} else if (sameClass(s)) {
						tmpZOrder = s.getZOrder();
						break;
					}
				}
			}

			if (tmpZOrder == -1) {
				super.calcZOrder();
			} else {
				iZOrder = tmpZOrder;
			}
		}
	}

	private boolean newGroup(int aGroup) {
		for (int t = 0; t < numGroups; t++) {
			if (groups[t] == aGroup) {
				return false;
			}
		}

		groups[numGroups] = aGroup;
		numGroups++;
		return true;
	}
	
	private int[] doSortStacked(int firstSeries, int lastSeries, int valueIndex) {

		List<Integer> nseries = new ArrayList<Integer>();
		List<Integer> pseries = new ArrayList<Integer>();
		
		for (int i = firstSeries; i < lastSeries; i++)
	        if (chart.getSeries(i).getActive() && chart.getSeries(i).getCount() > 0)
	          if (chart.getSeries(i).mandatory.getValue(valueIndex) < dOrigin)
	            nseries.add(i);
	          else
	            pseries.add(i);

	      int count = 0;
	      int[] result = new int[nseries.size() + pseries.size()];
	      
	      if (nseries.size() > 0)
	          for (int i = (nseries.size() - 1); i >= 0; i--) {
	        	  result[count] = nseries.get(i);
	        	  count++;
	          }
	      
	      if (pseries.size() > 0)
	          for (int i = 0; i < pseries.size(); i++) {
	        	  result[count] = pseries.get(i);
	        	  count++;
	          }
	      
	      return result;
	}
	
	@Override
	protected int[] sortDrawSeries(int firstSeries, int lastSeries, int valueIndex) {
		if ((iMultiBar == MultiBars.STACKED) || (iMultiBar == MultiBars.STACKED100))
			return doSortStacked(firstSeries, lastSeries, valueIndex);
		else
			return super.sortDrawSeries(firstSeries, lastSeries, valueIndex);
	}
    
	// if more than one bar series exists in chart,
	// which position are we? the first, the second, the third?
	public void doBeforeDrawChart() {

		super.doBeforeDrawChart();

		iOrderPos = 1;
		iPreviousCount = 0;
		iNumBars = 0;
		iMaxBarPoints = -1;
		numGroups = 0;

		boolean stop = false;

		for (int t = 0; t < chart.getSeriesCount(); t++) {
			ISeries s = chart.getSeries(t);
			if (s.getActive() && sameClass(s)) {

				stop |= (s == this);

				int tmp = s.getCount();

				if ((iMaxBarPoints == -1) || (tmp > iMaxBarPoints)) {
					iMaxBarPoints = tmp;
				}

				if (iMultiBar == MultiBars.NONE) {
					iNumBars = 1;
				} else if ((iMultiBar == MultiBars.SIDE)
						| (iMultiBar == MultiBars.SIDEALL)) {
					iNumBars++;
					if (!stop) {
						iOrderPos++;
					}
				} else if ((iMultiBar == MultiBars.STACKED)
						| (iMultiBar == MultiBars.STACKED100)) {
					if (newGroup(((CustomBar) s).stackGroup)) {
						iNumBars++;
						if (!stop) {
							iOrderPos++;
						}
					}
				} else if (iMultiBar == MultiBars.SELFSTACK) {
					iNumBars = 1;
				}

				if (!stop) {
					iPreviousCount += tmp;
				}
			}

			for (int tt = 0; tt < numGroups; tt++) {
				if (groups[tt] == stackGroup) {
					iOrderPos = tt + 1;
					break;
				}
			}

			// this should be done after calculating INumBars
			if (chart.getPage().getMaxPointsPerPage() > 0) {
				iMaxBarPoints = chart.getPage().getMaxPointsPerPage();
			}
		}
	}

	protected void drawTickLine(int tickPos, BarStyle aStyle) {
	}

	protected void drawTickLines(int startPos, int endPos, BarStyle aStyle) {
		if (getTickLines().getVisible()) {
			chart.getGraphics3D().setPen(getTickLines());
			Axis a = getMandatoryAxis();
			for (int t = 0; t < a.axisDraw.ticks.length; t++)
				if (a.axisDraw.ticks[t] > startPos
						&& a.axisDraw.ticks[t] < endPos)
					drawTickLine(a.axisDraw.ticks[t], aStyle);
		}
	}

	protected void drawLegendShape(IGraphics3D g, int valueIndex, Rectangle rect) {
		ChartPen oldPen = g.getPen();

		if (getBrush().getImage() != null)
			g.getBrush().setImage(bBrush.getImage());
		if (getPen().getVisible())
			g.setPen(getPen());

		super.drawLegendShape(g, valueIndex, rect);

		if (getPen().getVisible())
			g.setPen(oldPen);
	}

	@Override
	public boolean drawValuesForward() {

		return (iMultiBar == MultiBars.SELFSTACK) ? !getMandatoryAxis()
				.getInverted() : super.drawValuesForward();
	}

	protected int applyBarOffset(int position) {
		int result = position;
		if (offsetPercent != 0) {
			result += Utils.round(offsetPercent * iBarSize * 0.01);
		}
		return result;
	}

	protected int calcMarkLength(int valueIndex) {
		if ((getCount() > 0) && getMarks().getVisible()) {
			chart.getGraphics3D().setFont(getMarks().getFont());
			int result = getMarks().getArrowLength()
					+ internalCalcMarkLength(valueIndex);
			if (getMarks().getPen().getVisible()) {
				result += Utils.round(2 * getMarks().getPen().getWidth());
			}
			return result;
		} else {
			return 0;
		}
	}

	protected boolean internalClicked(int valueIndex, Point point) {
		return false;
	} // abstract

	/**
	 * Returns the ValueIndex of the "clicked" point in the Series.<br>
	 * Clicked means the X and Y coordinates are in the point screen region
	 * bounds. If no point is "touched", Clicked returns -1
	 * 
	 * @param x
	 *            int
	 * @param y
	 *            int
	 * @return int
	 */
	public int clicked(int x, int y) {
		if (chart != null) {
			Point p = chart.getGraphics3D().calculate2DPosition(x, y,
					getStartZ());
			x = p.x;
			y = p.y;
		}

		if ((firstVisible > -1) && (lastVisible > -1)) {
			Point p = new Point(x, y);
			for (int t = firstVisible; t <= Math.min(lastVisible,
					getCount() - 1); t++) {
				if (internalClicked(t, p)) {
					return t;
				}
			}
		}
		return -1;
	}

	protected int numSampleValues() {
		if ((chart != null) && (chart.getSeriesCount() > 1)) {
			for (int t = 0; t < chart.getSeriesCount(); t++) {
				ISeries s = chart.getSeries(t);
				if ((s != this) && (s instanceof CustomBar)
						&& (s.getCount() > 0)) {
					return s.getCount();
				}
			}
		}
		return 6;
	}

	protected void setPenBrushBar(Color barColor) {
		chart.getGraphics3D().setPen(pPen);
		if (barColor.isNull()) {
			chart.getGraphics3D().getPen().setColor(barColor);
		}
		if (getBrush().getColor().isEmpty()) {
			getBrush().setColor(getColor());
		}

		// [B423] [B425]
		getBrush().getGradient().assign((getGradient()));
		calcGradientColor(0);

		chart.setBrushCanvas(barColor, getBrush(), getColor()); // CDI TTrack
																// //#1482
	}

	protected boolean subGalleryStack() {
		return true;
	}

	public void createSubGallery(Gallery addSubChart) {
		super.createSubGallery(addSubChart);
		addSubChart.createSubChart(Language.getString("Colors"));
		addSubChart.createSubChart(Language.getString("Pyramid"));
		addSubChart.createSubChart(Language.getString("Ellipse"));
		addSubChart.createSubChart(Language.getString("InvPyramid"));
		addSubChart.createSubChart(Language.getString("Gradient"));

		if (subGalleryStack()) {
			addSubChart.createSubChart(Language.getString("Stack"));
			addSubChart.createSubChart(Language.getString("Stack"));
			addSubChart.createSubChart(Language.getString("SelfStack"));
		}

		addSubChart.createSubChart(Language.getString("Sides"));
		addSubChart.createSubChart(Language.getString("SideAll"));
	}

	public void setSubGallery(int index) {
		switch (index) {
		case 0:
			break;
		case 1:
			setColorEach(true);
			break;
		case 2:
			setBarStyle(BarStyle.PYRAMID);
			break;
		case 3:
			setBarStyle(BarStyle.ELLIPSE);
			break;
		case 4:
			setBarStyle(BarStyle.INVPYRAMID);
			break;
		case 5:
			setBarStyle(BarStyle.RECTGRADIENT);
			break;
		default: {

			if ((chart != null) && (chart.getSeriesCount() == 1)) {
				fillSampleValues(2);

				try {
					try {
						Series tmp = (Series) getClass().newInstance();
						getChart().addSeries(tmp);
						tmp.setTitle(""); // <-- force broadcast event to
											// ChartListBox and others
						tmp.fillSampleValues(2);
						tmp.getMarks().setVisible(false);
						((CustomBar) (tmp)).barSizePercent = barSizePercent;
						getMarks().setVisible(false);
						tmp.setSubGallery(index);
					} catch (InstantiationException e) {
					}
				} catch (IllegalAccessException e) {
				}
			}

			if (!subGalleryStack()) {
				index += 3;
			}

			switch (index) {
			case 6:
				setMultiBar(MultiBars.STACKED);
				break;
			case 7:
				setMultiBar(MultiBars.STACKED100);
				break;
			case 8:
				setMultiBar(MultiBars.SELFSTACK);
				break;
			case 9:
				setMultiBar(MultiBars.SIDE);
				break;
			case 10:
				setMultiBar(MultiBars.SIDEALL);
				break;
			default:
				super.setSubGallery(index);
			}
			break;
		}
		}
	}

	public interface BarStyleResolver {
		public BarStyle getStyle(ISeries series, int valueIndex, BarStyle style);
	}
}
