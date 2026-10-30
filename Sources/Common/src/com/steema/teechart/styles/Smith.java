package com.steema.teechart.styles;

import com.steema.teechart.languages.Language;

import com.steema.teechart.drawing.Color;
import com.steema.teechart.IBaseChart;
import com.steema.teechart.drawing.ChartPen;
import com.steema.teechart.drawing.ChartBrush;
import com.steema.teechart.drawing.IGraphics3D;
import com.steema.teechart.drawing.Point;
import com.steema.teechart.drawing.StringAlignment;
import com.steema.teechart.axis.GridPen;
import com.steema.teechart.drawing.ChartFont;
import com.steema.teechart.Rectangle;
import com.steema.teechart.misc.Utils;

/**
 *
 * <p>Title: Smith class</p>
 *
 * <p>Description: Smith Series</p>
 *
 * <p>Copyright (c) 2005-2019 by Steema Software SL. All Rights
 * Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 *
 */
public class Smith extends Circular {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ChartPen circlePen, pen;
    private String imagSymbol;
    private SeriesPointer pointer;

    transient private int oldX, oldY;

    public Smith() {
        this(null);
    }

    public Smith(IBaseChart c) {
        super(c);

        getXValues().name = Language.getString("SmithResistance");
        getXValues().setOrder(ValueListOrder.NONE);
        getYValues().name = Language.getString("SmithReactance");
        pointer = new SeriesPointer(chart, this);
        circlePen = new ChartPen(Color.BLACK);
        imagSymbol = "i";

        setCircleBackColor(Color.EMPTY);
        iAdjustCircleForMarks = false;
    }
    
    public void assign(Series source) {
        if (source instanceof Smith) {
            Smith tmp = (Smith)source;
            tmp.setChart(((Smith)source).getChart());
            tmp.setCLabels(((Smith)source).getCLabels());
            tmp.setImagSymbol(((Smith)source).getImagSymbol());
            tmp.setRLabels(((Smith)source).getRLabels());
        }
        super.assign(source);
    }     

    /**
     * Defines pen to draw SmithSeries Chart.
     *
     * @return ChartPen
     */
    public ChartPen getPen() {
        if (pen == null) {
            pen = new ChartPen(chart);
        }
        return pen;
    }

    /**
     * Defines pen to draw SmithSeries Chart.
     *
     * @return SeriesPointer
     */
    public SeriesPointer getPointer() {
        if (pointer == null) {
            pointer = new SeriesPointer(chart, this);
        }
        return pointer;
    }

    /**
     * Specifies the text used to display together with axis labels around a
     * Smith circle series.<br>
     * Default value: ImagSymbol is a lowercase "i" letter.
     *
     * @return String
     */
    public String getImagSymbol() {
        return imagSymbol;
    }

    /**
     * Specifies the text used to display together with axis labels around a
     * Smith circle series.<br>
     * Default value: ImagSymbol is a lowercase "i" letter.
     *

     * @param value String
     */
    public void setImagSymbol(String value) {
        imagSymbol = setStringProperty(imagSymbol, value);
    }

    /**
     * Defines Brush to fill Chart.
     *
     * @return ChartBrush
     */
    public ChartBrush getBrush() {
        return bBrush;
    }

    protected void addSampleValues(int numValues) {
        SeriesRandom r = randomBounds(numValues);
        for (int t = 0; t <= numValues; t++) {
            add(6.5 * t / numValues, (t * r.Random() + 3.8) / numValues);
        }
    }

    /**
     * Defines Pen to draw CCircles of the Smith Series.<br>
     * xCircle (reactance circle) pen. <br>
     * Read CCirclePen to obtain the TChartPen object that represents the color
     * and style of the CCircles of the Smith Series.<br>
     * Use CCirclePen to change the color or style.
     *
     * @return GridPen
     */
    public GridPen getCCirclePen() {
        return getVertAxis().getGrid();
    }

    /**
     * Defines Pen to draw RCircles of the Smith Series.<br>
     * rCircle (resistance circle) pen. <br>
     * Read RCirclePen to obtain the Pen object that represents the color and
     * style of the RCircles of the Smith Series.<br>
     * Use RCirclePen to change the color or style.
     *
     * @return GridPen
     */
    public GridPen getRCirclePen() {
        return getHorizAxis().getGrid();
    }

    /**
     * Defines Pen to draw external Circle of the Smith Series.<br>
     * Resistance = 0 rCircle pen (biggest circle). <br>
     * Read CirclePen to obtain the Pen object that represents the color and
     * style of the external circle of the Smith Series.<br>
     * Use CirclePen to change the color or style.
     *
     * @return ChartPen
     */
    public ChartPen getCirclePen() {
        if (circlePen == null) {
            circlePen = new ChartPen(chart);
        }
        return circlePen;
    }

    final private static double[] DEFAULTX = {0, 0.1, 0.3, 0.5, 0.8, 1, 1.5, 2,
                                             3, 5,
                                             7, 10};
    /**
     * Shows/hides the static finalant reactance labels.<br>
     * When set to true, constant reactance labels are shown.
     *
     * @return boolean
     */
    public boolean getCLabels() {
        return getVertAxis().getLabels().getVisible();
    }

    /**
     * Shows/hides the static finalant reactance labels.<br>
     * When set to true, constant reactance labels are shown.
     *
     * @param value boolean
     */
    public void setCLabels(boolean value) {
        getVertAxis().getLabels().setVisible(value);
    }

    /**
     * Shows/hides the static finalant resistance labels.<br>
     * When set to true constant resistance labels are shown.
     *
     * @return boolean
     */
    public boolean getRLabels() {
        return getHorizAxis().getLabels().getVisible();
    }

    /**
     * Shows/hides the static finalant resistance labels.<br>
     * When set to true constant resistance labels are shown.
     *
     * @param value boolean
     */
    public void setRLabels(boolean value) {
        getHorizAxis().getLabels().setVisible(value);
    }

    private void drawXCircleGrid() {
        chart.getGraphics3D().setPen(getCCirclePen());
        
        final int z = getMiddleZ();
        final boolean labels = getCLabels();
        
        for (int t = 0; t <= 11; t++)
            drawXCircle(DEFAULTX[t], z, labels);
    }

    final private static double[] DEFAULTR = {0, 0.2, 0.5, 1, 2, 5, 10};

    private void drawRCircleGrid() {
        chart.getGraphics3D().setPen(getRCirclePen());
        for (int t = 0; t <= 6; t++) {
            drawRCircle(DEFAULTR[t], getMiddleZ(), getRLabels());
        }
    }

    private void drawAxis() {
        //Chart.Graphics3D.ClipEllipse(CircleRect);
        //chart.graphics3D.Ellipse(CircleRect);
        if (getVertAxis().getVisible()) {
            drawXCircleGrid();
        }

        chart.getGraphics3D().unClip();

        if (getHorizAxis().getVisible()) {
            drawRCircleGrid();
        }
    }

    private void drawCircle() {
        IGraphics3D g = chart.getGraphics3D();

        //CDI CircleGradient
        if (getCircleBackColor().isEmpty() && calcCircleGradient() == null)
                /* 5.02 */
                {
            g.getBrush().setVisible(false);
        } else {
            g.getBrush().setVisible(true);
            g.getBrush().setSolid(true);
            g.getBrush().setColor(calcCircleBackColor());
            g.getBrush().setGradient(getCircleGradient());
        }
        int tmpX = getCircleWidth() / 2;
        int tmpY = getCircleHeight() / 2;
        g.setPen(getCirclePen());
        g.ellipse(getCircleXCenter() - tmpX, getCircleYCenter() - tmpY,
                  getCircleXCenter() + tmpX, getCircleYCenter() + tmpY, getEndZ());
    }

    protected void doBeforeDrawValues() {
        boolean first = false;

        for (int t = 0; t < chart.getSeriesCount(); t++) {
            ISeries s = chart.getSeries(t);
            if (s.getActive() && (s instanceof Smith)) {
                if (s == this) {
                    if (!first) {
                        if (getCLabels()) {
                            Rectangle r = chart.getChartRect();
                            r.grow( -chart.getGraphics3D().textWidth("360"),
                                   -chart.getGraphics3D().getFontHeight() + 2);
                            chart.setChartRect(r);
                        }
                    }
                    break;
                }

                first = true;
            }
        }

       super.doBeforeDrawValues();

       first = false;

       for (int tt = 0; tt < chart.getSeriesCount(); tt++) {
           ISeries ss = chart.getSeries(tt);
           if (ss.getActive() && (ss instanceof Smith)) {
               if (ss == this) {
                   if (!first) {
                       drawCircle();
                       if (chart.getAxes().getVisible() &&
                           chart.getAxes().getDrawBehind()) {
                           drawAxis();
                       }
                   }
                   break;
               }
           first = true;
          }
      }
    }

    protected void draw() {

        super.draw();

        if (pointer.getVisible()) {

            for (int t = firstVisible; t <= lastVisible; t++) {
                Color tmpColor = getValueColor(t);
                pointer.prepareCanvas(chart.getGraphics3D(), tmpColor);
                pointer.draw(calcXPos(t), calcYPos(t), tmpColor);
            }
        }
    }

    /**
     * Called internally. Draws the "ValueIndex" point of the Series.
     *
     * @param valueIndex int
     */
    public void drawValue(int valueIndex) {
        Point p = zToPos(vxValues.getValue(valueIndex),
                         vyValues.getValue(valueIndex));
        linePrepareCanvas(valueIndex);
        if (valueIndex == firstVisible) {
            chart.getGraphics3D().moveTo(p, getStartZ()); // <-- first point
        } else
        if ((p.x != oldX) || (p.y != oldY)) {
            chart.getGraphics3D().lineTo(p, getStartZ());
        }

        oldX = p.x;
        oldY = p.y;
    }

    private String getXCircleLabel(double reactance) {
        return new Double(reactance).toString() + imagSymbol;
    }

    private void linePrepareCanvas(int valueIndex) {
        IGraphics3D g = chart.getGraphics3D();
        if (getPen().getVisible()) {
            g.setPen(getPen());
            g.getPen().setColor((valueIndex == -1) ? getColor() :
                                getValueColor(valueIndex));
        } else {
            g.getPen().setVisible(false);
        }
        //g.BackMode=cbmTransparent;
    }

    public void prepareForGallery(boolean isEnabled) {
        super.prepareForGallery(isEnabled);
        chart.getAspect().setChart3DPercent(5);
        chart.getAxes().getRight().getLabels().setVisible(false);
        chart.getAxes().getTop().getLabels().setVisible(false);
        chart.getAspect().setOrthogonal(false);
        chart.getAspect().setElevation(360);
        chart.getAspect().setZoom(90);
    }

    public void setChart(IBaseChart c) {
        super.setChart(c);
        if (pointer != null) {
            pointer.setChart(c);
        }
        if ((chart != null) && chart.isDesignTime()) {
            chart.getAspect().setView3D(false);
        }
    }

    /**
     * The pixel Screen Horizontal coordinate of the ValueIndex Series
     * value.<br>
     * This coordinate is calculated using the Series associated Horizontal Axis.
     *
     * @param valueIndex int
     * @return int
     */
    public int calcXPos(int valueIndex) {
        return zToPos(vxValues.getValue(valueIndex),
                      vyValues.getValue(valueIndex)).x;
    }

    /**
     * The pixel Screen Vertical coordinate of the ValueIndex Series
     * value.<br>
     * This coordinate is calculated using the Series associated Vertical Axis.
     *
     * @param valueIndex int
     * @return int
     */
    public int calcYPos(int valueIndex) {
        return zToPos(vxValues.getValue(valueIndex),
                      vyValues.getValue(valueIndex)).y;
    }

    /**
     * The ValueIndex of the "clicked" point in the Series.<br>
     *
     * @param x int
     * @param y int
     * @return int
     */
    public int clicked(int x, int y) {
        if (chart != null) {
            Point p = chart.getGraphics3D().calculate2DPosition(x, y, getStartZ());
            x = p.x;
            y = p.y;
        }

        int result = super.clicked(x, y);

        if ((result == -1) && (firstVisible > -1) && (lastVisible > -1)) {
            if (pointer.getVisible()) {
                for (int t = firstVisible; t <= lastVisible; t++) {
                    if ((Math.abs(calcXPos(t) - x) < pointer.getHorizSize()) &&
                        (Math.abs(calcYPos(t) - y) < pointer.getVertSize())) {
                        return t;
                    }
                }
            }
        }

        return result;
    }

    private void drawRCircleLabel(double rVal, int x, int y) {
        if (getHorizAxis().getVisible()) {
            /* 5.02 */
            chart.getGraphics3D().setFont(getRLabelsFont());
            chart.getGraphics3D().setTextAlign(StringAlignment.CENTER);
            //chart.getGraphics3D.BackMode=cbmTransparent;
            chart.getGraphics3D().textOut(x, y, getEndZ(),
                                          new Double(rVal).toString());
        }
    }

    private void drawRCircle(double value, int z, boolean showLabel) {
        if (value != -1) {
            // Transform R
            double tmp = 1 / (1 + value);
            int halfXSize = Utils.round(tmp * getXRadius());
            int halfYSize = Utils.round(tmp * getYRadius());

            // Circles are always right aligned
            chart.getGraphics3D().ellipse(getCircleRect().getRight() -
                                          2 * halfXSize,
                                          getCircleYCenter() - halfYSize,
                                          getCircleRect().getRight(),
                                          getCircleYCenter() + halfYSize, z);

            if (showLabel) { // 5.02 (was if RLabels)
                drawRCircleLabel(value,
                                 getCircleRect().getRight() - 2 * halfXSize,
                                 getCircleYCenter());
            }
        }
    }

    /**
     * xCircle labels font.
     *
     * @return ChartFont
     */
    public ChartFont getCLabelsFont() {
        return getVertAxis().getLabels().getFont();
    }

    /**
     * rCircle labels font.
     *
     * @return ChartFont
     */
    public ChartFont getRLabelsFont() {
        return getHorizAxis().getLabels().getFont();
    }

    private void drawXCircleLabel(double xVal, int x, int y) {

        if (getVertAxis().getVisible()) {
            /* 5.02 */
            chart.getGraphics3D().setFont(getCLabelsFont());
            int tmpHeight = chart.getGraphics3D().getFontHeight();
            String tmpSt = getXCircleLabel(xVal);

            double angle = pointToAngle(x, y) * 57.29577;

            if (angle >= 360) {
                angle -= 360;
            }
            if ((angle == 0) || (angle == 180)) {
                y -= tmpHeight / 2;
            } else
            if ((angle > 0) && (angle < 180)) {
                y -= tmpHeight;
            }
            if ((angle == 90) || (angle == 270)) {
                chart.getGraphics3D().setTextAlign(StringAlignment.CENTER);
            } else {
                chart.getGraphics3D().setTextAlign(((angle > 90) &&
                        (angle < 270)) ?
                        StringAlignment.FAR :
                        StringAlignment.NEAR);
            }

            int tmpWidth = chart.getGraphics3D().textWidth("0") / 2;

            if (angle == 0) {
                x += tmpWidth;
            } else if (angle == 180) {
                x -= tmpWidth;
            }

            //chart.graphics3D.BackMode=cbmTransparent;
            chart.getGraphics3D().textOut(x, y, getEndZ(), tmpSt);

        }
    }

    private void drawXCircle(double value, int z, boolean showLabel) {

        Point p1 = new Point();
        Point p2 = new Point();

        if (value != 0) {

            double invValue = 1 / value;
            Point p4 = zToPos(0, value); // Endpos
            if (showLabel) {
                drawXCircleLabel(value, p4.x, p4.y);
            }

            Point p3 = zToPos(100, value); // Startpos

            // ellipse bounding points
            int halfXSize = Utils.round(invValue * getXRadius());
            int halfYSize = Utils.round(invValue * getYRadius());

            p1.x = getCircleRect().getRight() - halfXSize;
            p2.x = getCircleRect().getRight() + halfXSize;
            p1.y = getCircleYCenter();
            p2.y = p1.y - 2 * halfYSize;

            chart.getGraphics3D().clipEllipse(getCircleRect());
            if ((!chart.getAspect().getView3D()) ||
                chart.getAspect().getOrthogonal()) {
                chart.getGraphics3D().arc(p1.x, p2.y, p2.x, p1.y, 0, 360);
            }

            p4 = zToPos(0, -value); // Endpos

            if ((!chart.getAspect().getView3D()) ||
                chart.getAspect().getOrthogonal()) {
                chart.getGraphics3D().arc(p1.x, p2.y + 2 * halfYSize, p2.x,
                                          p1.y + 2 * halfYSize, 0, 360);
            }
            chart.getGraphics3D().unClip();

            if (showLabel) {
                drawXCircleLabel( -value, p4.x, p4.y);
            }
        } else {
            /* special case ) reactance is zero */
            p1.x = getCircleRect().x;
            p2.x = getCircleRect().getRight();
            p1.y = getCircleYCenter();

            chart.getGraphics3D().line(p1.x, p1.y, p2.x, p1.y, getMiddleZ());

            if (showLabel) {
                drawXCircleLabel(0, p1.x, p1.y);
            }
        }
    }

    // Position to impendance
    // (ZRe,ZIm)=(1+gamma)/(1-gamma)
    private Point.Double posToZ(int x, int y) {
        x -= getCircleXCenter();
        y = getCircleYCenter() - y;
        double gRe = x / getXRadius();
        double gIm = y / getYRadius();
        double norm2 = (gRe * gRe) + (gIm * gIm);
        double invDen = 1 / (norm2 - 2 * gRe + 1);
        return new Point.Double((1 - norm2) * invDen, 2 * gIm * invDen);
    }

    // impendance to Position
    // (GRe,GIm)=(1-z)/(1+z)
    private Point zToPos(double resist, double react) {

        double norm2 = (resist * resist) + (react * react);
        double invDen = 1 / (norm2 + 2 * resist + 1);
        double gRe = (norm2 - 1) * invDen;
        double gIm = 2 * react * invDen;
        return new Point(getCircleXCenter() + Utils.round(gRe * getXRadius()),
                         getCircleYCenter() - Utils.round(gIm * getYRadius()));
    }

    /**
     * Gets descriptive text.
     *
     * @return String
     */
    public String getDescription() {
        return Language.getString("GallerySmith");
    }
}

