package com.steema.teechart.styles;

import com.steema.teechart.editors.gallery.Gallery;
import com.steema.teechart.languages.Language;

import com.steema.teechart.Rectangle;
import com.steema.teechart.drawing.Color;
import com.steema.teechart.drawing.Point;

import com.steema.teechart.drawing.ChartPen;
import com.steema.teechart.IBaseChart;
import com.steema.teechart.drawing.IGraphics3D;

/**
 *
 * <p>Title: Points3D class</p>
 *
 * <p>Description: Points3D Series.
 * Each point has XYZ coordinates.</p>
 *
 * <p>Example:
 * <pre><font face="Courier" size="4">
 * series = new com.steema.teechart.styles.Points3D(myChart.getChart());
 * series.getMarks().setVisible(false);
 * series.getBaseLine().setVisible(true);
 * series.getBaseLine().setColor(Color.NAVY);
 * series.getLinePen().setVisible(false);
 * series.getPointer().setInflateMargins(true);
 * series.getPointer().setStyle(PointerStyle.RECTANGLE);
 * series.getPointer().setVisible(true);
 * series.fillSampleValues();
 * </font></pre></p>
 *
 * <p>Copyright (c) 2005-2019 by Steema Software SL. All Rights Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 *
 */
public class Points3D extends Custom3D {
    //public ClickPointer    : TSeriesClickPointer3DEvent;

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private double depthSize = 0;
    private SeriesPointer pointer;
    private ChartPen linePen;
    private ChartPen baseLine;
    private TreatNullsStyle treatnulls = TreatNullsStyle.DONOTPAINT;

    transient private int iOldX;
    transient private int iOldY;
    transient private int iOldZ;    

    transient private PointerStyleResolver styleResolver;

    public void setPointerStyleResolver(PointerStyleResolver resolver) {
        this.styleResolver = resolver;
    }

    public void removePointerStyleResolver() {
        this.styleResolver = null;
    }

    public Points3D(IBaseChart c) {
        super(c);

        pointer = new SeriesPointer(chart, this);
        linePen = new ChartPen(chart, Color.BLACK);
        treatnulls = TreatNullsStyle.DONOTPAINT;
    }

    public Points3D() {
        this((IBaseChart)null);
    }
    
    public void assign(Series source) {
        if (source instanceof Points3D) {
            Points3D tmp = (Points3D)source;
            tmp.setChart(((Points3D)source).getChart());
            tmp.setColor(((Points3D) source).getColor());  
            tmp.setDepthSize(((Points3D) source).getDepthSize());  
            tmp.setTreatNulls(((Points3D) source).getTreatNulls());  
        }
        super.assign(source);
    }    

    /**
     * Each point in a PointSeries is drawn using the Pointer properties.<br>
     * It is a subclass of Points series, Line series and all other derived
     * PointSeries classes like Bubble series.<br>
     * Default value: null
     *
     * @return SeriesPointer
     */
    public SeriesPointer getPointer() {
        if (pointer == null) {
            pointer = new SeriesPointer(chart, this);
        }
        if (getColor() != pointer.getColor()) {
            pointer.setColor(getColor());
        }
        invalidate();
        return pointer;
    }

    /**
     * Default color for all points.<br>
     * Default value: ""
     *
     * @see com.steema.teechart.styles.Series#getColorEach
     * @return Color
     */
    public Color getColor() {
        return super.getColor();
    }

    /**
     * Default color for all points.<br>
     * Default value: ""
     *
     * @param value Color
     */
    public void setColor(Color value) {
        super.setColor(value);
        if (pointer != null && pointer.getColor() != value) {
            pointer.setColor(value);
        }
        invalidate();
    }

    /**
     * Sets the Pen for the Point3D connecting Lines.
     *
     * @return ChartPen
     */
    public ChartPen getLinePen() {
        return linePen;
    }

    /**
     * Sets the Pen for the Point3D connecting Lines.
     *
     * <p>Example:
     * <pre><font face="Courier" size="4">
     * series.getBaseLine().setVisible(true);
     * </font></pre></p>
     *
     * @return ChartPen
     */
    public ChartPen getBaseLine() {
        if (baseLine==null) {
            baseLine=new ChartPen(chart, false);
        }
        return baseLine;
    }

    public void createSubGallery(Gallery addSubChart) {
        super.createSubGallery(addSubChart);

        addSubChart.createSubChart(Language.getString("NoPoint"));
        addSubChart.createSubChart(Language.getString("NoLine"));
        addSubChart.createSubChart(Language.getString("Colors"));
        addSubChart.createSubChart(Language.getString("Marks"));
        addSubChart.createSubChart(Language.getString("Hollow"));
        addSubChart.createSubChart(Language.getString("NoBorder"));
        addSubChart.createSubChart(Language.getString("Point2D"));
        addSubChart.createSubChart(Language.getString("Triangle"));
        addSubChart.createSubChart(Language.getString("Star"));
        addSubChart.createSubChart(Language.getString("Circle"));
        addSubChart.createSubChart(Language.getString("DownTri"));
        addSubChart.createSubChart(Language.getString("Cross"));
        addSubChart.createSubChart(Language.getString("Diamond"));
    }

    public void setSubGallery(int index) {
        switch (index) {
        case 1:
            getPointer().setVisible(false);
            break;
        case 2:
            getLinePen().setVisible(false);
            break;
        case 3:
            setColorEach(true);
            break;
        case 4:
            getMarks().setVisible(true);
            break;
        case 5:
            getPointer().getBrush().setVisible(false);
            break;
        case 6:
            getPointer().getPen().setVisible(false);
            break;
        case 7:
            getPointer().setDraw3D(false);
            break;
        case 8:
            getPointer().setStyle(PointerStyle.TRIANGLE);
            break;
        case 9:
            getPointer().setStyle(PointerStyle.STAR);
            break;
        case 10:
            getPointer().setStyle(PointerStyle.CIRCLE);
            break;
        case 11:
            getPointer().setStyle(PointerStyle.DOWNTRIANGLE);
            break;
        case 12:
            getPointer().setStyle(PointerStyle.CROSS);
            break;
        case 13:
            getPointer().setStyle(PointerStyle.DIAMOND);
            break;
        default:
            super.setSubGallery(index);
        }
    }

    /**
     * The Depth of each 3DPoint to the value of DepthSize.<br>
     * Default value: O
     *
     * @return double
     */
    public double getDepthSize() {
        return depthSize;
    }

    /**
     * Sets the Depth of each 3DPoint to the value of DepthSize.<br>
     * Default value: O
     *
     * @param value double
     */
    public void setDepthSize(double value) {
        depthSize = setDoubleProperty(depthSize, value);
    }

    protected void calcZPositions(int valueIndex) {
        calcZPositions(valueIndex, calcZPos(valueIndex));
    }
    
    private void calcZPositions(int valueIndex, int tmpMiddleZ) {
        middleZ = tmpMiddleZ;
              int tmp = Math.max(1,
                           chart.getAxes().getDepth().calcSizeValue(depthSize) /
                           2);
        startZ=getMiddleZ() - tmp;
        endZ=getMiddleZ() + tmp;
    }    

    protected void addSampleValues(int numValues) {
        SeriesRandom r = randomBounds(numValues);
        for (int t = 1; t <= numValues; t++) {
            add(100 * r.Random(), 100 * r.Random(), 100 * r.Random());
        }
    }

    public void calcHorizMargins(Margins margins) {
        super.calcHorizMargins(margins);
        pointer.calcHorizMargins(margins);
    }

    public void calcVerticalMargins(Margins margins) {
        super.calcVerticalMargins(margins);
        pointer.calcVerticalMargins(margins);
    }

    protected void drawLegendShape(IGraphics3D g, int valueIndex,
                                   Rectangle r) {
        if (pointer.getVisible()) {
            Color tmpColor = (valueIndex == -1) ? getColor() :
                             getValueColor(valueIndex);
            pointer.drawLegendShape(g, tmpColor, r, getLinePen().getVisible());
        } else {
            super.drawLegendShape(g, valueIndex, r);
        }
    }

    protected void drawMark(int valueIndex, String s,
                            SeriesMarksPosition position) {
        calcZPositions(valueIndex);
        getMarks().setZPosition(pointer.getVisible() ?
                                (getStartZ() + getEndZ()) / 2 : getStartZ());
        getMarks().applyArrowLength(position);
        super.drawMark(valueIndex, s, position);
    }

    public void prepareForGallery(boolean isEnabled) {
        super.prepareForGallery(isEnabled);
        linePen.setColor(Color.NAVY);
        chart.getAspect().setZoom(60);
    }

    public void setChart(IBaseChart value) {
        super.setChart(value);
        if (pointer != null) {
            pointer.setChart(value);
        }
        if (linePen != null) {
            linePen.setChart(value);
        }
    }
    
    //private boolean shouldDrawPoint(int valueIndex, int tmpX, int tmpY) {
    private boolean shouldDrawPoint(int valueIndex, Point p) {
      boolean result = !isNull(valueIndex);
      if (result) {
        calcZPositions(valueIndex);
        p.x = calcXPos(valueIndex);
        p.y = calcYPos(valueIndex);

        drawPointer(valueIndex, p.x, p.y);

        if ((getTreatNulls() == TreatNullsStyle.DONOTPAINT) && (valueIndex > 0)
            && (isNull(valueIndex - 1))) {
          result = false;
          iOldX = p.x;
          iOldY = p.y;
          iOldZ = getMiddleZ();
        }
      }
      else {
        if (getTreatNulls() == TreatNullsStyle.IGNORE) {
            p.x = calcXPosValue(valueIndex);
            p.y = calcYPosValue(getDefaultNullValue());
            calcZPositions(valueIndex);

            result = true;
        }
        else if (getTreatNulls() == TreatNullsStyle.SKIP) {
            if (valueIndex == 0) {
                iOldX = calcXPosValue(valueIndex);
                iOldY = calcYPosValue(getDefaultNullValue());
                calcZPositions(valueIndex);
            }

            p.x = iOldX;
            p.y = iOldY;
            calcZPositions(valueIndex, iOldZ);
        }
      }
      return result;
    }    

    protected void onGetPointerStyle(int valueIndex, PointerStyle style, 
            Color aColor) {
        if (styleResolver != null) {
             style = styleResolver.getStyle(this, valueIndex, style);
        }
    }    
    
    private void drawPointer(int valueIndex, int tmpX, int tmpY) {
      if (pointer.getVisible()) {
        // emulate Custom.DrawPointer method
        Color tmpColor = getValueColor(valueIndex);

        pointer.prepareCanvas(chart.getGraphics3D(), tmpColor);
        PointerStyle tmpStyle = pointer.getStyle();        
        onGetPointerStyle(valueIndex, tmpStyle, tmpColor);

        pointer.draw(chart.getGraphics3D(), chart.getAspect().getView3D(), 
                tmpX, tmpY, pointer.getHorizSize(), pointer.getVertSize(),
                tmpColor, tmpStyle);
      }
    }    

    /**
     * Called internally. Draws the "ValueIndex" point of the Series.
     *
     * @param valueIndex int
     */
    public void drawValue(int valueIndex) {
        Point p = new Point(0,0);

        if (shouldDrawPoint(valueIndex, p)) {
            IGraphics3D g=chart.getGraphics3D();
            
            if (getBaseLine().getVisible()) {
                int tmpZ=calcZPos(valueIndex);
                g.setPen(getBaseLine());

                if (((p.y + pointer.getVertSize()) - getVertAxis().iEndPos) 
                        < 0) {
                    g.moveTo(p.x, p.y+pointer.getVertSize(), tmpZ);
                    g.lineTo(p.x, getVertAxis().iEndPos, tmpZ);
                }             
            }
            
            if (valueIndex > firstVisible) {
                if (getLinePen().getVisible()) {
                    g.setPen(getLinePen());
                    g.moveTo(iOldX, iOldY, iOldZ);
                    g.lineTo(p.x, p.y, getMiddleZ());
                }
            }   
            
            iOldX = p.x;
            iOldY = p.y;
            iOldZ = getMiddleZ();            
        }
    }

    /**
     * Returns MaxZValue.
     *
     * @return double
     */
    public double getMaxZValue() {
        return vzValues.getMaximum() + depthSize;
    }

    /**
     * Gets descriptive text.
     *
     * @return String
     */
    public String getDescription() {
        return Language.getString("GalleryPoint3D");
    }

    /**
     * The ValueIndex of the "clicked" point in the Series.
     *
     * @param x int
     * @param y int
     * @return int
     */
    public int clicked(int x, int y) {
        int oldX = x;
        int oldY = y;
        int result = super.clicked(x, y);

        if ((result == -1) && (pointer.getVisible())) {
            for (int t = 0; t < getCount(); t++) {
                x = oldX;
                y = oldY;

                Point p = new Point(x, y);

                if (chart != null) {
                    p = chart.getGraphics3D().calculate2DPosition(x, y,
                            calcZPos(t));
                }

                int tmpX = calcXPos(t);
                int tmpY = calcYPos(t);

                if ((Math.abs(tmpX - x) < pointer.getHorizSize()) &&
                    (Math.abs(tmpY - y) < pointer.getVertSize())) {
                    // if (OnClickPointer!=null) OnClickPointer(this,t,OldX,OldY);
                    return t;
                }
            }
        }

        return result;
    }
    
    /**
     * Defines how null points are treated.<br>
     *
     * @return TreatNullsStyle
     */
    public TreatNullsStyle getTreatNulls()  {
        return treatnulls;
    }
    
    public void setTreatNulls(TreatNullsStyle value) {
        if  (treatnulls != value) {
            treatnulls = value;
            repaint();
        }
    }
}

