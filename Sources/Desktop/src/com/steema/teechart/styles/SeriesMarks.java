package com.steema.teechart.styles;

import com.steema.teechart.*;
import com.steema.teechart.Shape;
import com.steema.teechart.drawing.Graphics3D;
import com.steema.teechart.languages.Language;
import com.steema.teechart.drawing.StringAlignment;

import com.steema.teechart.drawing.Color;
import com.steema.teechart.drawing.ChartPen;
import com.steema.teechart.drawing.IGraphics3D;
import com.steema.teechart.drawing.Point;
import com.steema.teechart.misc.StringFormat;
import com.steema.teechart.misc.Utils;

/**
 * <p>
 * Title: SeriesMarks
 * </p>
 * <p>
 * <p>
 * Description: Series Marks characteristics.
 * </p>
 * <p>
 * <p>
 * Copyright (c) 2005-2019 by Steema Software SL. All Rights Reserved.
 * </p>
 * <p>
 * <p>
 * Company: Steema Software SL
 * </p>
 */
public class SeriesMarks extends TextShape {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    /**
     * When True, Marks arrow pen color is changed if the point has the same
     * color.
     */
    public boolean checkMarkArrowColor = true;

    transient protected ISeries iSeries;
    private MarksTail markerTail = MarksTail.None;
    transient protected MarkPositions pPositions;

    protected boolean bClip;
    protected MarksStyle markerStyle = MarksStyle.LABEL;

    private boolean onTop = false;
    private boolean multiLine;
    private int drawEvery = 1;
    int zPosition = -1;
    private double angle;
    private MarksCallout callout;
    private MarksItems items;
    private com.steema.teechart.styles.SeriesMarksSymbols symbol;
    private boolean followSeriesColor;
    private boolean fontSeriesColor;
    private TailParams markTailParams;

    public SeriesMarks(ISeries s) {
        super(s.getChart());

        iSeries = s;

        getBrush().setDefaultColor(Color.LIGHT_YELLOW);

        getShadow().setDefaultVisible(true);
        shadow.setDefaultSize(1);
        shadow.getBrush().setDefaultColor(Color.DARK_GRAY);

        setDefaultVisible(false);

        setCallout(new MarksCallout(s));
        callout.setDefaultLength(10);
        setTailParams(new TailParams(iSeries.getChart()));
        getTailParams().setPointerHeight(getArrowLength() * 0.5);
        items = new MarksItems(this);

        readResolve();
    }

    protected Object readResolve() {
        pPositions = new MarkPositions();
        return this;
    }

    @Override
    public void assign(Shape s) {
        super.assign(s);
        ((SeriesMarks) s).iSeries = iSeries;
        ((SeriesMarks) s).setStyle(markerStyle);
        ((SeriesMarks) s).setAngle(angle);
        ((SeriesMarks) s).setMultiLine(multiLine);
        ((SeriesMarks) s).setDrawEvery(drawEvery);
        ((SeriesMarks) s).setFontSeriesColor(fontSeriesColor);
        ((SeriesMarks) s).setCallout(getCallout());
        ((SeriesMarks) s).setMarkerTail(markerTail);
    }

    public MarksCallout getCallout() {
        if (callout == null) {
            setCallout(new MarksCallout(iSeries));
            callout.setLength(10);
        }
        return callout;
    }

    private PointDouble[] getMarkTail(TailAlignment pos, Rectangle rect) {
        int tailHeight = getChart().getGraphics3D().sizeToDIP((float) getTailParams().getPointerHeight());
        int tailWidth = getChart().getGraphics3D().sizeToDIP((float) getTailParams().getPointerWidth());

		PointDouble[] points = new PointDouble[7];
		for (int i = 0; i < points.length; i++) {
			points[i] = new PointDouble();
		}

		Rectangle rf = new Rectangle(rect);
        double pointerCenterX = rf.getLeft();
        double pointerCenterY = rf.getTop();
        double XPlane = rf.getLeft();
        double YPlane = rf.getTop();

        //non pointer plane
        if ((pos == TailAlignment.BottomLeft) || (pos == TailAlignment.BottomCenter) || (pos == TailAlignment.BottomRight))
            YPlane = rf.getBottom();
        else if ((pos == TailAlignment.TopLeft) || (pos == TailAlignment.TopCenter) || (pos == TailAlignment.TopRight))
            YPlane = rf.getTop();
		else if ((pos == TailAlignment.LeftTop) || (pos == TailAlignment.LeftCenter)
				|| (pos == TailAlignment.LeftBottom))
            XPlane = rf.getLeft();
		else if ((pos == TailAlignment.RightTop) || (pos == TailAlignment.RightCenter)
				|| (pos == TailAlignment.RightBottom))
            XPlane = rf.getRight();

		// pointer plane
		if ((pos == TailAlignment.BottomLeft) || (pos == TailAlignment.TopLeft))
			pointerCenterX = rf.getLeft() + (tailWidth / 2);
		else if ((pos == TailAlignment.BottomCenter) || (pos == TailAlignment.TopCenter))
			pointerCenterX = rf.getLeft() + (rf.getWidth() / 2);
		else if ((pos == TailAlignment.BottomRight) || (pos == TailAlignment.TopRight))
			pointerCenterX = rf.getLeft() + rf.getWidth() - (tailWidth / 2);
		else if ((pos == TailAlignment.LeftTop) || (pos == TailAlignment.RightTop))
			pointerCenterY = rf.getTop() + (tailWidth / 2);
		else if ((pos == TailAlignment.LeftCenter) || (pos == TailAlignment.RightCenter))
			pointerCenterY = rf.getTop() + (rf.getHeight() / 2);
		else if ((pos == TailAlignment.LeftBottom) || (pos == TailAlignment.RightBottom))
			pointerCenterY = rf.getTop() + rf.getHeight() - (tailWidth / 2);

		if ((pos == TailAlignment.BottomLeft) || (pos == TailAlignment.BottomCenter)
				|| (pos == TailAlignment.BottomRight) || (pos == TailAlignment.TopLeft)
				|| (pos == TailAlignment.TopCenter) || (pos == TailAlignment.TopRight)) {
			
            points[0].setX(rf.getLeft());
            points[0].setY(rf.getTop());
            points[1].setX(rf.getLeft());
            points[1].setY(rf.getBottom());

			if ((pos == TailAlignment.BottomLeft) || (pos == TailAlignment.BottomCenter)
					|| (pos == TailAlignment.BottomRight)) {
				
                points[2].setX(pointerCenterX - (tailWidth / 2));
                points[2].setY(YPlane);
                points[3].setX(getTailParams().isCustomPoint() ? getTailParams().getCustomPointPos().getX() : pointerCenterX);
                points[3].setY(getTailParams().isCustomPoint() ? getTailParams().getCustomPointPos().getY() : YPlane + tailHeight);
                points[4].setX(pointerCenterX + (tailWidth / 2));
                points[4].setY(YPlane);
                points[5].setX(rf.getRight());
                points[5].setY(YPlane);
                points[6].setX(rf.getRight());
                points[6].setY(rf.getTop());
            }
            if ((pos == TailAlignment.TopLeft) || (pos == TailAlignment.TopCenter) || (pos == TailAlignment.TopRight)) {
                points[2].setX(rf.getRight());
                points[2].setY(rf.getBottom());
                points[3].setX(rf.getRight());
                points[3].setY(YPlane);
                points[4].setX(pointerCenterX + (tailWidth / 2));
                points[4].setY(YPlane);
                points[5].setX(getTailParams().isCustomPoint() ? getTailParams().getCustomPointPos().getX() : pointerCenterX);
                points[5].setY(getTailParams().isCustomPoint() ? getTailParams().getCustomPointPos().getY() : YPlane - tailHeight);
                points[6].setX(pointerCenterX - (tailWidth / 2));
                points[6].setY(YPlane);
            }
        } else if ((pos == TailAlignment.LeftTop) || (pos == TailAlignment.LeftCenter) || (pos == TailAlignment.LeftBottom)
                || (pos == TailAlignment.RightTop) || (pos == TailAlignment.RightCenter) || (pos == TailAlignment.RightBottom)) {
            points[0].setX(rf.getLeft());
            points[0].setY(rf.getTop());

            if ((pos == TailAlignment.LeftTop) || (pos == TailAlignment.LeftCenter) || (pos == TailAlignment.LeftBottom)) {
				
                points[1].setX(XPlane);
                points[1].setY(pointerCenterY - (tailWidth / 2));
                points[2].setX(getTailParams().isCustomPoint() ? getTailParams().getCustomPointPos().getX() : XPlane - tailHeight);
                points[2].setY(getTailParams().isCustomPoint() ? getTailParams().getCustomPointPos().getY() : pointerCenterY);
                points[3].setX(XPlane);
                points[3].setY(pointerCenterY + (tailWidth / 2));
                points[4].setX(XPlane);
                points[4].setY(rf.getBottom());
                points[5].setX(rf.getRight());
                points[5].setY(rf.getBottom());
                points[6].setX(rf.getRight());
                points[6].setY(rf.getTop());
            }
            if ((pos == TailAlignment.RightTop) || (pos == TailAlignment.RightCenter) || (pos == TailAlignment.RightBottom)) {
				
                points[1].setX(rf.getLeft());
                points[1].setY(rf.getBottom());
                points[2].setX(XPlane);
                points[2].setY(rf.getBottom());
                points[3].setX(XPlane);
                points[3].setY(pointerCenterY + (tailWidth / 2));
                points[4].setX(getTailParams().isCustomPoint() ? getTailParams().getCustomPointPos().getX() : XPlane + tailHeight);
                points[4].setY(getTailParams().isCustomPoint() ? getTailParams().getCustomPointPos().getY() : pointerCenterY);
                points[5].setX(XPlane);
                points[5].setY(pointerCenterY - (tailWidth / 2));
                points[6].setX(XPlane);
                points[6].setY(rf.getTop());
            }
        }

        return points;
    }

    public void DrawMarkWithPointer(Graphics3D g, Rectangle rect, int angle, int aZ) {
        if (!bTransparent) {
            g.setBrush(getBrush());
            g.setPen(getPen());

            PointDouble[] points = new PointDouble[7];

            if ((this.getSeries() instanceof HorizBar) || (this.getSeries() instanceof HorizArea))  // some 'horizontal' series
                points = getMarkTail(TailAlignment.LeftCenter, rect);
			else
                points = getMarkTail(markTailParams.getAlign(), rect);


            g.polygon(points);
        }

        if (bBevel != null)
            bBevel.draw(g, rect);
        if (bImageBevel != null)
            bImageBevel.Draw(g, rect, 0);
    }

    /**
     * Marks Callout characteristics.<br>
     * Determines how to draw a line connecting a series mark to its series
     * point.
     *
     * @return MarksCallout
     */
    /*public MarksCallout getCallout() {
		return callout;
	}*/ //REPLACED?

    /**
     * Returns a collection of mark items. <br>
     * Each mark item has its own formatting (color, font, shadow, etc).<br>
     * By default Items are empty, so the default marks formatting is used.
     *
     * @return MarksItems
     */
    public MarksItems getItems() {
        return items;
    }

    public void setItems(MarksItems value) {
        items = value;
    }

    /**
     * Accesses Custom position characteristics for Series Marks. <br>
     * TeeChart has a number of algorithmns that were designed to stop
     * TeeChart SeriesMark overlap. However, if you aren't happy with the
     * automatically generated mark positions, you can always move marks around
     * with code.<br>
     * <br>
     * (Remember that you could also use the DragMarks Tool to reposition marks
     * on your Chart.)
     *
     * @return MarkPositions
     */
    public MarkPositions getPositions() {
        return pPositions;
    }

    /**
     * Contains the list of lines drawn on the chart by the user at run-time.
     *
     * @return String[]
     */
    public String[] getLines() {
        return super.getLines();
    }

    /**
     * Series is a read-only runtime method.<br>
     * It returns the Series component that owns the TSeriesMarks subcomponent. <br>
     * All Series types own a Marks subcomponent of the TSeriesMarks class.
     *
     * @return ISeries
     */
    public ISeries getSeries() {
        return iSeries;
    }

    /**
     * Defines how Series Marks are static finalructed.<br>
     * Each different Style value makes Marks output a different text. Several
     * options are available, but you can also use the TChart
     * Series.OnGetMarkText event and override the default Series Marks text. <br>
     * Default value: MarksStyle.Label
     *
     * @return MarksStyle
     */
    public MarksStyle getStyle() {
        return markerStyle;
    }

    /**
     * Defines how Series Marks are constructed.<br>
     * Default value: MarksStyle.Label
     *
     * @param value MarksStyle
     */
    public void setStyle(MarksStyle value) {
        if (markerStyle != value) {
            markerStyle = value;
            invalidate();
        }
    }

    public SeriesMarksSymbols getSymbol() {
        if (symbol == null)
            symbol = new SeriesMarksSymbols(chart);
		
        return symbol;
    }

    public void setChart(IBaseChart value) {
        super.setChart(value);
        if (symbol != null) {
            symbol.setChart(value);
        }
    }

    /**
     * Tells chart to repaint using automatically positioned marks.<br>
     * Turns all Marks positions Custom to false.
     */
    public void resetPositions() {
        for (int t = 0; t < pPositions.size(); t++) {
            SeriesMarksPosition p = pPositions.getPosition(t);
            if (p != null) {
                p.custom = false;
            }
        }
        invalidate();
    }

    /**
     * Removes all mark positions and mark items.
     */
    public void clear() {
        pPositions.clear();
        if (!getItems().iLoadingCustom) {
            getItems().clear();
        }
    }

    /**
     * Determines which Mark contains the p point parameters.
     *
     * @param p Point
     * @return int
     */
    public int clicked(Point p) {
        return clicked(p.x, p.y);
    }

    /**
     * Determines which Mark contains the XY pixel point parameters.
     *
     * @param x int
     * @param y int
     * @return int the Mark index containing the XY point.
     */
    public int clicked(int x, int y) {
        if (iSeries.getChart() != null) {
            Point p = iSeries.getChart().getGraphics3D()
                    .calculate2DPosition(x, y, getZPosition());
            x = p.x;
            y = p.y;
        }

        int result = 0;
        for (int t = 0; t < pPositions.size(); t++) {
            SeriesMarksPosition p = pPositions.getPosition(t);
            if ((result % drawEvery == 0) && (p != null)
                    && (p.getBounds().contains(x, y))) {
				
                return result;
            } else {
                result++;
            }
        }

        return -1;
    }

    /**
     * Obsolete.&nbsp;Use the Color method instead.
     *
     * @return Color
     */
    public Color getBackColor() {
        return getColor();
    }

    /**
     * Obsolete.&nbsp;Use the Color method instead.
     *
     * @param value Color
     */
    public void setBackColor(Color value) {
        setColor(value);
    }

    /**
     * Obsolete.&nbsp;Use the Pen method instead.
     *
     * @return ChartPen
     */
    public ChartPen getFrame() {
        return getPen();
    }

    /**
     * The Pen used to draw a line connecting Marks to Series points.<br>
     * Each Series class handles Marks in a different manner, thus the Arrow
     * coordinates are specific to each Series type. <br>
     * By default, Arrow pen is defined to be a White solid pen of 1 pixel
     * width.
     *
     * @return ChartPen
     */
    public ChartPen getArrow() {
        return callout.getArrow();
    }

    // protected boolean shouldSerializeArrowLength() {
    // return getArrowLength() != defaultArrowLength;
    // }

    /**
     * The length in pixels for the line connecting the Mark to Series point. <br>
     * Default value: 16
     *
     * @return int
     */
    public int getArrowLength() {
        return callout.getLength();
    }

    /**
     * Stes the length in pixels of the line connecting the Mark to Series
     * point.<br>
     * Default value: 16
     *
     * @param value int
     */
    public void setArrowLength(int value) {
        getCallout().setLength(value);
    }

    /**
     * Restricts Marks to Chart axes space, when true.<br>
     * When true, Marks will be drawn only within inner chart boundaries,
     * keeping Axis Labels, Titles, Legend, etc almost untouched.<br>
     * Default value: false
     *
     * @return boolean
     */
    public boolean getClip() {
        return bClip;
    }

    /**
     * Restricts Marks to Chart axes space, when true.<br>
     * Default value: false
     *
     * @param value boolean
     */
    public void setClip(boolean value) {
        bClip = setBooleanProperty(bClip, value);
    }

    /**
     * Series Marks are always drawn on top of all other Series in the Chart, when true.<br>
     * Default value: false
     *
     * @return boolean
     */
    public boolean getOnTop() {
        return onTop;
    }

    /**
     * Series Marks are always drawn on top of all other Series in the Chart, when true.<br>
     * Default value: false
     *
     * @return boolean
     */
    public void setOnTop(boolean value) {
        onTop = setBooleanProperty(onTop, value);
    }

    /**
     * Characters in Mark texts are split into multiple lines, when true.<br>
     * Default value: false
     *
     * @return boolean
     */
    public boolean getMultiLine() {
        return multiLine;
    }

    /**
     * Characters in Mark texts are split into multiple lines, when true.<br>
     * Default value: false
     *
     * @param value boolean
     */
    public void setMultiLine(boolean value) {
        multiLine = setBooleanProperty(multiLine, value);
    }

    /**
     * The number of Marks to skip.<br>
     * Default is 1, all Marks are displayed. Setting it to two will draw every
     * other Mark, to three every third etc. <br>
     * Default value: 1
     *
     * @return int
     */
    public int getDrawEvery() {
        return drawEvery;
    }

    /**
     * Sets the number of Marks to skip.<br>
     * Default value: 1
     *
     * @param value int
     */
    public void setDrawEvery(int value) {
        drawEvery = setIntegerProperty(drawEvery, value);
    }

    /**
     * The angle from 0 to 360 to rotate Marks.<br>
     * Default value: 0
     *
     * @return double
     */
    public double getAngle() {
        return angle;
    }

    /**
     * Sets angle from 0 to 360 to rotate Marks.<br>
     * Default value: 0
     *
     * @param value double
     */
    public void setAngle(double value) {
        angle = setDoubleProperty(angle, value);
    }

    /**
     * The Position in pixels on the Z axis.
     *
     * @return int
     */
    public int getZPosition() {
        return zPosition;
    }

    /**
     * Sets Position in pixels on the Z axis.
     *
     * @param value int
     */
    public void setZPosition(int value) {
        zPosition = setIntegerProperty(zPosition, value);
    }

    /**
     * Gets FollowSeriesColor, Mark Back Color same as Series.
     *
     * @return int
     */
    public boolean getFollowSeriesColor() {
        return followSeriesColor;
    }

    /**
     * Sets FollowSeriesColor, Mark Back Color same as Series.
     *
     * @param value boolean
     */
    public void setFollowSeriesColor(boolean value) {
        followSeriesColor = setBooleanProperty(followSeriesColor, value);
        if (value) {
            getFont().setColor(Color.WHITE);
            getBrush().setColor(getSeries().getColor());
            getPen().setColor(getSeries().getColor());
            getShadow().setVisible(false);
        }
    }

    /**
     * Gets FontSeriesColor, Font Color in Mark texts are given from its point Color.
     *
     * @return int
     */
    public boolean getFontSeriesColor() {
        return fontSeriesColor;
    }

    /**
     * Sets FontSeriesColor, Font Color in Mark texts are given from its point Color.
     *
     * @param value boolean
     */
    public void setFontSeriesColor(boolean value) {
        fontSeriesColor = setBooleanProperty(fontSeriesColor, value);
    }

    private void drawTextLine(final String lineSt, final Rectangle r, int tmpNumRow,
                              int tmpRowHeight) {
        Point tmpP = new Point();
        Point tmpCenter = r.center();

        if (angle != 0) {

            double tmp = angle * Utils.PISTEP;
            double s = Math.sin(tmp);
            double c = Math.cos(tmp);

            int tmpY = tmpNumRow * tmpRowHeight - (r.getBottom() - tmpCenter.y);
            tmpP.x = (tmpCenter.x + Utils.round(tmpY * s));

            if (angle == 90) {
                tmpP.x += 2;
            }
            tmpP.y = (tmpCenter.y + Utils.round(tmpY * c));
        } else {
            tmpP.x = tmpCenter.x;
            tmpP.y = r.y + tmpNumRow * tmpRowHeight;

            if (getPen().getVisible()) {
                tmpP.x += getPen().getWidth();
                tmpP.y += getPen().getWidth();
            }
        }

        IGraphics3D g = iSeries.getChart().getGraphics3D();
        g.setTextAlign(StringAlignment.CENTER);

        if (g.getSupports3DText()) {
            if (angle == 0) {
                g.textOut(tmpP.x, tmpP.y, getZPosition(), lineSt);
            } else {
                g.rotateLabel(tmpP.x, tmpP.y, getZPosition(), lineSt, angle);
            }
        } else {
            if (angle == 0) {
                g.textOut(tmpP.x, tmpP.y, lineSt);
            } else {
                g.rotateLabel(tmpP.x, tmpP.y, lineSt, angle);
            }
        }
    }

    public boolean allSeriesVisible() {
        if (chart != null) {
            for (int t = 0; t < chart.getSeriesCount(); t++) {
                if (!chart.getSeries(t).getMarks().getVisible()) {
                    return false;
                }
            }
            return true;

        } else {
            return getVisible();
        }
    }

    protected SeriesMarksPosition applyArrowLength(SeriesMarksPosition aPos) {
        int tmp = callout.getLength() + callout.getDistance();
        aPos.leftTop.y -= tmp;
        aPos.arrowTo.y -= tmp;
        aPos.arrowFrom.y -= callout.getDistance();
        return aPos;
    }

    /**
     * Returns the String showing a "percent" value for a given point.<br>
     * For example: "25%"<br>
     * The optional "AddTotal" parameter, when true, returns: "25% of 1234".
     *
     * @param valueIndex int
     * @param addTotal   boolean
     * @return String
     */
    public String percentString(int valueIndex, boolean addTotal) {

        final ValueList m = iSeries.getMandatory();

		/*
		double tmp = (m.getTotalABS() != 0) ? Math.abs(iSeries
				.getMarkValue(valueIndex)) / m.getTotalABS() : 100;

		String tmpResult = NumberFormat.getPercentInstance().format(tmp);
		*/

        String tmpResult = iSeries.calcPercentSt(iSeries.getMarkValue(valueIndex));

        if (addTotal) {
            final String tmpF = multiLine ? Language.getString("DefaultPercentOf")
                    : Language.getString("DefaultPercentOf");
            try {
                tmpResult = StringFormat.format(tmpF, tmpResult,
                        m.getTotalABS(), iSeries.getValueFormat());
            } catch (Exception e) {
                tmpResult = StringFormat.format(tmpF, tmpResult,
                        m.getTotalABS(), Language.getString("DefValueFormat"));
            }
        }
        return tmpResult;
    }

    private void drawText(final Rectangle r, final String st) {
        int tmpNumRow = 0;
        int tmpRowHeight = iSeries.getChart().getGraphics3D().getFontHeight();

        int i = st.indexOf(Language.lineSeparator);

        if (i != -1) {

            int sepLength = Language.lineSeparator.length();

            String tmpSt = st;
            do {
                drawTextLine(tmpSt.substring(0, i), r, tmpNumRow, tmpRowHeight);
                tmpSt = tmpSt.substring(i + sepLength, tmpSt.length());
                tmpNumRow++;
                i = tmpSt.indexOf(Language.lineSeparator);
            } while (i != -1);

            if (tmpSt.length() != 0) {
                drawTextLine(tmpSt, r, tmpNumRow, tmpRowHeight);
            }
        } else {
            drawTextLine(st, r, tmpNumRow, tmpRowHeight);
        }
    }

    private void usePosition(int index, SeriesMarksPosition markPosition) {
        while (index >= pPositions.size()) {
            pPositions.add(null);
        }

        SeriesMarksPosition tmp = null;

        if (pPositions.getPosition(index) == null) {
            tmp = new SeriesMarksPosition();
            tmp.custom = false;
            pPositions.setPosition(index, tmp);
        }
        tmp = pPositions.getPosition(index);

        if (tmp.custom) {
            if (markPosition.arrowFix) {
                Point old = markPosition.arrowFrom;
                markPosition.assign(tmp);
                markPosition.arrowFrom = old;
            } else {
                markPosition.assign(tmp);
            }
        } else {
            tmp.assign(markPosition);
        }
    }

    /**
     * Returns the length in pixels for the ValueIndex th mark text String.<br>
     * It checks if the Mark has multiple lines of text.
     *
     * @param valueIndex int
     * @return int
     */
    public int textWidth(int valueIndex) {
        String tmpSt2 = "";
        // char[] tmpSt2;
        int tmpResult = 0;
        String tmpSt = iSeries.getMarkText(valueIndex);
        int i = tmpSt.indexOf(Language.lineSeparator);

        if (i > 0) {
            do {
                tmpSt2 = tmpSt.substring(0, i);
                tmpResult = Math.max(tmpResult, iSeries.getChart()
                        .getGraphics3D().textWidth(tmpSt2));
                tmpSt = tmpSt.substring(i + 1);
                i = tmpSt.indexOf(Language.lineSeparator);
            } while (i != -1);
        }

        if (tmpSt.length() != 0) {
            tmpResult = Math.max(tmpResult, iSeries.getChart().getGraphics3D()
                    .textWidth(tmpSt));
        }

        return tmpResult;
    }

    protected Point convertTo2D(SeriesMarksPosition aPos, Point p) {

        if (chart.getAspect().getView3D()
                && !chart.getGraphics3D().getSupports3DText()) {
            int tmpDifX = aPos.arrowTo.x - p.x;
            int tmpDifY = aPos.arrowTo.y - p.y;
            Point tmpPos2D = chart.getGraphics3D().calc3DPoint(aPos.arrowTo,
                    getZPosition());

            p.x = tmpPos2D.x - tmpDifX;
            p.y = tmpPos2D.y - tmpDifY;
        }

        return p;
    }

    private Rectangle totalBounds(int valueIndex, SeriesMarksPosition aPos) {
        Rectangle result = aPos.getBounds();
        TextShape tmpMark = markItem(valueIndex);

        if (tmpMark.getPen().getVisible()) {
            result.width += tmpMark.getPen().getWidth();
            result.height += tmpMark.getPen().getWidth();
        }
        if (tmpMark.getShadow().getWidth() > 0) {
            result.width += tmpMark.getShadow().getWidth();
        } else if (tmpMark.getShadow().getWidth() < 0) {
            result.x -= tmpMark.getShadow().getWidth();
        }

        if (tmpMark.getShadow().getHeight() > 0) {
            result.height += tmpMark.getShadow().getHeight();
        } else if (tmpMark.getShadow().getHeight() < 0) {
            result.y -= tmpMark.getShadow().getHeight();
        }

        Point p = convertTo2D(aPos, result.getLocation());

        int tmp = result.x - p.x;
        result.x -= tmp;
        result.width -= tmp;

        tmp = result.y - p.y;
        result.y -= tmp;
        result.height -= tmp;

        return result;
    }

    protected boolean antiOverlap(int first, int valueIndex, SeriesMarksPosition aPos, boolean belowZero) {

        int tmpH;
        SeriesMarksPosition position;
        Rectangle tmpBounds = totalBounds(valueIndex, aPos);

        for (int t = first; t < valueIndex; t++) {
            position = getPositions().getPosition(t);

            if (position != null) {
                Rectangle tmpR = totalBounds(t, position);

                if (Utils.intersectWithRect(tmpR, tmpBounds)) {
                    if (tmpBounds.getTop() < tmpR.getTop()) {
                        tmpH = tmpBounds.getBottom() - tmpR.y;
                    } else {
                        tmpH = tmpBounds.y - tmpR.getBottom();
                    }

                    aPos.leftTop.y = belowZero ? aPos.leftTop.y + tmpH : aPos.leftTop.y - tmpH;
                    aPos.arrowTo.y = belowZero ? aPos.arrowTo.y + tmpH : aPos.arrowTo.y - tmpH;

                    if (aPos.hasMid) {
                        aPos.midPoint.y = belowZero ? aPos.midPoint.y + tmpH : aPos.midPoint.y - tmpH;
                    }

                    tmpBounds = totalBounds(t, aPos);

                    if (Utils.intersectWithRect(tmpR, tmpBounds)) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    protected boolean antiOverlap(int first, int valueIndex,
                                  SeriesMarksPosition aPos) {

        return antiOverlap(first, valueIndex, aPos, false);
    }

    protected TextShape markItem(int valueIndex) {
        TextShape result = this;
        if ((getItems().size() > valueIndex)
                && (getItems().getItem(valueIndex) != null)) {
            result = getItems().getItem(valueIndex);
        }
        return result;
    }

    protected void internalDraw(int index, final Color aColor, final String st,
                                final SeriesMarksPosition aPos) {

        usePosition(index, aPos);
        IBaseChart c = iSeries.getChart();

        if (getMarkerTail() == null) {
            markTailParams = new TailParams(c);
        } else {
			markTailParams.setChart(c);
		}

        IGraphics3D g = c.getGraphics3D();

        TextShape tmpMark = markItem(index);

        if (callout.getVisible() || callout.getArrow().getVisible()) {
            callout.draw(aColor, aPos.arrowFrom, aPos.arrowTo, getZPosition());
			/*
			 * g.getBrush().setVisible(false); g.setPen(getArrow());
			 *
			 * if (tmp3D) { g.line(aPos.arrowFrom.x, aPos.arrowFrom.y,
			 * aPos.arrowTo.x, aPos.arrowTo.y, getZPosition()); } else {
			 * g.line(aPos.arrowFrom.x, aPos.arrowFrom.y, aPos.arrowTo.x,
			 * aPos.arrowTo.y); }
			 */
        }

        if (tmpMark.getTransparent()) {
            g.getBrush().setVisible(false);
        } else {
            g.setBrush(tmpMark.getBrush());
            // g.getBrush().ForegroundColor=Color;
            // if (transparency>0) g.SetTransparency(transparency);
        }

        g.setPen(tmpMark.getPen());
        aPos.leftTop = convertTo2D(aPos, aPos.leftTop);

        Rectangle tmpBounds = new Rectangle(aPos.leftTop.x, aPos.leftTop.y,
                getAutoSize() ? aPos.width : getWidth(), getAutoSize() ? aPos.height : getHeight());

        if (this.markerTail == MarksTail.None)
            tmpMark.drawRectRotated(g, tmpBounds, Utils.round(angle % 360), getZPosition());
        else if (this.markerTail == MarksTail.WithPointer)
            DrawMarkWithPointer((Graphics3D) g, tmpBounds, Utils.round(angle % 360), getZPosition());

        boolean tmpSymbol = shouldDrawSymbol();

        if (tmpSymbol) {
            symbol.setColor(getSeries().getValueColor(index));
            // symbol.setTransparent(false);

            int tmpH = g.getFontHeight();

            symbol.setShapeBounds(Rectangle.fromLTRB(aPos.leftTop.x + 4,
                    aPos.leftTop.y + 3, aPos.leftTop.x + tmpH - 1,
                    aPos.leftTop.y + tmpH - 2));

            symbol.paint(g, symbol.getShapeBounds());

            tmpBounds.x += (symbol.getShapeBounds().getWidth() - 2);
        }

        g.getBrush().setVisible(false);

        Rectangle r = aPos.getBounds();

        if (tmpSymbol) {
            int tmp = 4 + symbol.getShapeBounds().width;
            r.x += tmp;
            r.width -= tmp;
        }

        Color oldColor = g.getFont().getColor();
        if (getFontSeriesColor()) {
            g.getFont().setColor(aColor);
        }

        drawText(r, st);

        if (getFontSeriesColor()) {
            g.getFont().setColor(oldColor);
        }
        if (this.getMarkerTail() == MarksTail.WithPointer && !bTransparent)
            g.resetTransform();
    }

    boolean shouldDrawSymbol() {
        return (symbol != null) && (symbol.getVisible())
                && (!symbol.getTransparent());
    }

    public MarksTail getMarkerTail() {
        return markerTail;
    }

    public void setMarkerTail(MarksTail markerTail) {
        if (markerTail != null) {
            this.markerTail = markerTail;
            invalidate();
        }
    }

    public TailParams getTailParams() {
        if (markTailParams == null)
            markTailParams = new TailParams(this.chart);
        return markTailParams;
    }

    public void setTailParams(TailParams markTailParams) {
        this.markTailParams = markTailParams;
    }

    public void setCallout(MarksCallout callout) {
        this.callout = callout;
    }
}
