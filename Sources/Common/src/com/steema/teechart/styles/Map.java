package com.steema.teechart.styles;

import com.steema.teechart.IBaseChart;
import com.steema.teechart.languages.Language;
import com.steema.teechart.Rectangle;
import com.steema.teechart.drawing.Color;
import com.steema.teechart.drawing.Point;
import com.steema.teechart.editors.gallery.Gallery;
import java.util.Random;
import com.steema.teechart.axis.Axis;
import com.steema.teechart.axis.AxisLabelStyle;
import com.steema.teechart.Comparator;
import com.steema.teechart.Swapper;

import com.steema.teechart.misc.Utils;
import com.steema.teechart.drawing.Graphics3D;

/**
 *
 * <p>Title: Map class</p>
 *
 * <p>Description: Map series.</p>
 *
 * <p>Copyright (c) 2005-2019 by Steema Software SL. All Rights
 * Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 *
 */
public class Map extends Custom3DPalette {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private PolygonList shapes;
    private Polygon[] i3DList;

    transient private Random rnd;
    transient private boolean tmpClip;
    transient private Rectangle tmpRect;
    transient private int transparency;

    public Map(IBaseChart c) {
        super(c);
        calcVisiblePoints = false;
        yMandatory = false;
        mandatory = getZValues();
        transparency = 0;
    }

    public Map() {
        this((IBaseChart)null);
    }
    
    public void assign(Series source) {
        if (source instanceof Map) {
            Map tmp = (Map)source;
            tmp.setChart(((Map) source).getChart());
            tmp.setShapes(((Map)source).getShapes());
            tmp.setTransparency(((Map)source).getTransparency());
        }
        super.assign(source);
    }    

    public void clear() {
        super.clear();
        if (shapes != null) {
            shapes.clear();
        }
    }
    
    public int getTransparency() {
        return transparency;
    }
    
    public void setTransparency(int value) {
        if (transparency != value) {
            transparency = value;
            for (int t = 0; t < getShapes().size(); t++) {
              getShapes().getPolygon(t).setTransparency(transparency);
            }
            invalidate();
        }
    }

    /**
     * The Maximum Value of the Series X Values List.
     *
     * @return double
     */
    public double getMaxXValue() {
        if (getShapes().size() == 0) {
            return 0;
        } else {
            double result = getShapes().getPolygon(0).getPoints().getMaxXValue();
            for (int t = 1; t < getShapes().size(); ++t) {
                result = Math.max(result,
                                  getShapes().getPolygon(t).getPoints().getMaxXValue());
            }
            return result;
        }
    }

    /**
     * The Maximum Value of the Series Y Values List.
     *
     * @return double
     */
    public double getMaxYValue() {
        if (getShapes().size() == 0) {
            return 0;
        } else {
            double result = getShapes().getPolygon(0).getPoints().getMaxYValue();
            for (int t = 1; t < getShapes().size(); ++t) {
                result = Math.max(result,
                                  getShapes().getPolygon(t).getPoints().getMaxYValue());
            }
            return result;
        }
    }

    /**
     * The Minimum Value of the Series X Values List.
     *
     * @return double
     */
    public double getMinXValue() {
        if (getShapes().size() == 0) {
            return 0;
        } else {
            double result = getShapes().getPolygon(0).getPoints().getMinXValue();
            for (int t = 1; t < getShapes().size(); ++t) {
                result = Math.min(result,
                                  getShapes().getPolygon(t).getPoints().getMinXValue());
            }
            return result;
        }
    }

    /**
     * The Minimum Value of the Series Y Values List.
     *
     * @return double
     */
    public double getMinYValue() {
        if (getShapes().size() == 0) {
            return 0;
        } else {
            double result = getShapes().getPolygon(0).getPoints().getMinYValue();
            for (int t = 1; t < getShapes().size(); ++t) {
                result = Math.min(result,
                                  getShapes().getPolygon(t).getPoints().getMinYValue());
            }
            return result;
        }
    }

    public void delete(int index) {
        super.delete(index);
        if (getShapes() != null) {
            getShapes().remove(index);
        }
    }

    public void delete(int index, int count, boolean removeGap) {
        super.delete(index, count, removeGap);
        if (getShapes() != null) {
            getShapes().removeRange(index, count);
        }
    }

    /**
     * The ValueIndex of the "clicked" point in the Map Series.
     *
     * @param x int
     * @param y int
     * @return int
     */
    public int clicked(int x, int y) {

        int result = -1;

        if (getChart() != null) {
            tmpClip = getChart().getAspect().getClipPoints();
            tmpRect = getChart().getChartRect();

            for (int t = getShapes().size() - 1; t >= 0; --t) {
                Polygon tmpshape = getShapes().getPolygon(t);

                if (isShapeVisible(tmpshape.getPoints())) {
                    int tmpX = x;
                    int tmpY = y;

                    getChart().getGraphics3D().calculate2DPosition(tmpX, tmpY,
                            calcZPos(t));

                    if (Graphics3D.pointInPolygon(new Point(tmpX, tmpY),
                                                  tmpshape.getPolygonPoints())) {
                        result = t;
                        break;
                    }
                }
            }
        }

        return result;
    }


    private boolean isShapeVisible(PolygonSeries shape) {
        int tmp2;
        boolean result = false;

        if (tmpClip) {
            Axis horiz = getHorizAxis();
            int tmp = horiz.calcPosValue(shape.getXValues().getMinimum());

            if (tmp < tmpRect.getLeft() || tmp > tmpRect.getRight()) {
                tmp2 = horiz.calcPosValue(shape.getXValues().getMaximum());

                if (tmp < tmpRect.getLeft() ||
                    (tmp2 > tmpRect.getRight() && tmp > tmpRect.getRight())) {
                    result = false;
                }
            }

            Axis vert = getVertAxis();
            tmp = vert.calcPosValue(shape.getYValues().getMaximum());

            if (tmp < tmpRect.getTop() || tmp > tmpRect.getBottom()) {
                tmp2 = vert.calcPosValue(shape.getYValues().getMinimum());

                result = (tmp2 >= tmpRect.getTop() && tmp2 <= tmpRect.getBottom()) ||
                         (tmp2 > tmpRect.getBottom() && tmp2 < tmpRect.getTop());
            } else {
                result = true;
            }

        } else {
            result = true;
        }

        return result;
    }

    public void setShapes(PolygonList value) {
        getShapes().assign(value);
    }

    protected void swapValueIndex(int a, int b) {
        super.swapValueIndex(a, b);
        Polygon pA = getShapes().getPolygon(a);
        Polygon pB = getShapes().getPolygon(b);

        int tmpIndex = pA.getIndex();
        pA.setIndex(pB.getIndex());
        pB.setIndex(tmpIndex);

        getShapes().set(a, pB);
        getShapes().set(b, pA);
    }

    /**
     * Default array property in Map series to make it easier to obtain a Map
     * polygon object:
     *
     * @param index int
     * @return Polygon
     */
    public Polygon getPolygon(int index) {
        return getShapes().getPolygon(index);
    }

    /**
     * A list of Polygon objects.<br>
     * Each polygon object in the list refers to a "shape" in the Map series.
     *
     * @return PolygonList
     */
    public PolygonList getShapes() {
        if(shapes == null) shapes = new PolygonList(this);
        return shapes;
    }

    /**
     * Adds a new shape to the Map Series.
     *
     * @param x int[]
     * @param y int[]
     * @param text String
     */
    public void addShape(int[] x, int[] y, String text) {
        int tmpX = 0, tmpY = 0;

        if (rnd==null) {
          rnd=new Random();
        }

        if (getCount() > getNumSampleValues()) {
            tmpX = rnd.nextInt(getNumSampleValues());
            tmpY = rnd.nextInt(getNumSampleValues());
        }

        Polygon toAdd = new Polygon(getShapes(), getChart());

        for (int t = 0; t < x.length; ++t) {
            toAdd.add(tmpX + x[t], tmpY + y[t]);
        }

        int i = getShapes().add(toAdd);
        if (text.equals("")) {
            getShapes().getPolygon(i).setText(Integer.toString(i));
        } else {
            getShapes().getPolygon(i).setText(text);
        }

        getShapes().getPolygon(i).setZ(rnd.nextInt(1000) / 1000.0);
    }

    protected int numSampleValues() {
        return 12;
    }

    public void calcHorizMargins(Margins margins) {
        super.calcHorizMargins(margins);
        if (getPen().getVisible()) {
            int tmp = getPen().getWidth();
            margins.min += tmp;
            margins.max += tmp;
        }
    }

    public void calcVerticalMargins(Margins margins) {
        super.calcVerticalMargins(margins);

        ++margins.max;

        if (getPen().getVisible()) {
            int tmp = getPen().getWidth();
            margins.min += tmp;
            margins.max += tmp;
        }
    }

    public void createSubGallery(Gallery addSubChart) {
        super.createSubGallery(addSubChart);
        addSubChart.createSubChart(Language.getString("Colors"));
    }

    public void setSubGallery(int index) {
        if (index == 2) {
            setColorEach(true);
        } else {
            super.setSubGallery(index);
        }
    }

    public void prepareForGallery(boolean isEnabled) {
        super.prepareForGallery(isEnabled);

        if (!isEnabled) {
            for (int t = 0; t < getCount(); ++t) {
                getShapes().getPolygon(t).setColor(Color.SILVER);
            }
        }
    }


    public void galleryChanged3D(boolean is3D) {
        if (is3D) {
            super.galleryChanged3D(is3D);
        } else {
            getChart().getAspect().setView3D(false);
        }
    }

    protected void draw() {
        if (getChart().getAspect().getView3D()) {
            drawAllSorted();
        } else {
            super.draw();
        }

    }

    /*
        protected int[] getBounds(int index, PolygonStyle p) {
            int[] result;
            drawValue(index);
            p = ((Graphics3DHotSpot) chart.getGraphics3D()).PolygonStyle;
            result = ((Graphics3DHotSpot) chart.getGraphics3D()).GetBounds();
            return result;
        }
     */


    public void drawValue(int index) {
        if (getShapes().size() > index) {
            getShapes().getPolygon(index).draw(getChart().getGraphics3D(), index);
        }
    }

    public void dispose() {
        if (getVertAxis().getLabels().getStyle() != AxisLabelStyle.AUTO) {
            getVertAxis().getLabels().setStyle(AxisLabelStyle.AUTO);
        }
        super.dispose();

    }

    protected void drawMark(int valueIndex, String st,
                            SeriesMarksPosition
                            aPosition) {
        if (getShapes().size() > valueIndex) {
            Rectangle tmpR = getShapes().getPolygon(valueIndex).getBounds();
            aPosition.leftTop.x = ((tmpR.getRight() + tmpR.getLeft()) / 2) -
                                  (aPosition.width / 2);
            aPosition.leftTop.y = ((tmpR.getTop() + tmpR.getBottom()) / 2) -
                                  (aPosition.height / 2);
        }
        super.drawMark(valueIndex, st, aPosition);
    }

    protected class CompareOrder implements Comparator {
        public final int compare(int a, int b) {
            double tmpA = i3DList[a].getZ();
            double tmpB = i3DList[b].getZ();
            if (tmpA > tmpB) {
                return 1;
            } else if (tmpA < tmpB) {
                return -1;
            } else {
                return 0;
            }
        }
    }

    protected class SwapPolygon implements Swapper {
        public final void swap(int a, int b) {
            Polygon pA = i3DList[a];
            Polygon pB = i3DList[b];
            i3DList[a] = pB;
            i3DList[b] = pA;
        }
    }

    private void drawAllSorted() {
        int tmpCount = getShapes().size();

        if (tmpCount > 0) {
            i3DList = new Polygon[tmpCount];
            try {
                for (int t = 0; t < tmpCount; ++t) {
                    i3DList[t] = getShapes().getPolygon(t);
                }

                Utils.sort(0, tmpCount-1, new CompareOrder(), new SwapPolygon());

                for (int t = tmpCount - 1; t >= 0; --t) {
                    i3DList[t].draw(getChart().getGraphics3D(),
                                    i3DList[t].getIndex());
                    if (i3DList[t].getParentBrush()) {
                        i3DList[t].setColor(getValueColor(i3DList[t].getIndex()));
                    } else {
                        i3DList[t].setColor(i3DList[t].getBrush().getColor());
                    }
                }
            } finally {
                i3DList = null;
            }
        }

    }

    /**
     * Gets descriptive text.
     *
     * @return String
     */
    public String getDescription() {
        return Language.getString("MapSeries");
    }

    protected void addSampleValues(int numValues) {
        int[] aX = new int[] {1, 3, 4, 4, 5, 5, 6, 6, 4, 3, 2, 1, 2, 2};
        int[] aY = new int[] {7, 5, 5, 7, 8, 9, 10, 11, 11, 12, 12, 11, 10, 8};
        int[] bX = new int[] {5, 7, 8, 8, 7, 6, 5, 4, 4};
        int[] bY = new int[] {4, 4, 5, 6, 7, 7, 8, 7, 5};
        int[] cX = new int[] {9, 10, 11, 11, 12, 9, 8, 7, 6, 6, 5, 5, 6, 7, 8,
                   8};
        int[] cY = new int[] {5, 6, 6, 7, 8, 11, 11, 12, 11, 10, 9, 8, 7, 7, 6,
                   5};
        int[] dX = new int[] {12, 14, 15, 14, 13, 12, 11, 11};
        int[] dY = new int[] {5, 5, 6, 7, 7, 8, 7, 6};
        int[] eX = new int[] {4, 6, 7, 7, 6, 6, 5, 4, 3, 3, 2};
        int[] eY = new int[] {11, 11, 12, 13, 14, 15, 16, 16, 15, 14, 13};
        int[] fX = new int[] {7, 8, 9, 11, 10, 8, 7, 6, 5, 5, 6, 6};
        int[] fY = new int[] {13, 14, 14, 16, 17, 17, 18, 18, 17, 16, 15, 14};
        int[] gX = new int[] {10, 12, 12, 14, 13, 11, 9, 8, 7, 7, 8, 9};
        int[] gY = new int[] {10, 12, 13, 15, 16, 16, 14, 14, 13, 12, 11, 11};
        int[] hX = new int[] {17, 19, 18, 18, 17, 15, 14, 13, 15, 16};
        int[] hY = new int[] {11, 13, 14, 16, 17, 15, 15, 14, 12, 12};
        int[] iX = new int[] {15, 16, 17, 16, 15, 14, 14, 13, 12, 11, 10, 11,
                   12, 13, 14};
        int[] iY = new int[] {6, 6, 7, 8, 8, 9, 10, 11, 12, 11, 10, 9, 8, 7, 7};
        int[] jX = new int[] {15, 16, 16, 17, 17, 16, 15, 13, 12, 12, 14, 14};
        int[] jY = new int[] {8, 8, 9, 10, 11, 12, 12, 14, 13, 12, 10, 9};
        int[] kX = new int[] {17, 19, 20, 20, 19, 17, 16, 16, 17, 16};
        int[] kY = new int[] {5, 5, 6, 8, 8, 10, 9, 8, 7, 6};
        int[] lX = new int[] {19, 20, 21, 21, 19, 17, 17};
        int[] lY = new int[] {8, 8, 9, 11, 13, 11, 10};

        for (int t = 0; t < numValues; ++t) {
            switch (t % getNumSampleValues()) {
            case 0:
                addShape(aX, aY, "A");
                break;
            case 1:
                addShape(bX, bY, "B");
                break;
            case 2:
                addShape(cX, cY, "C");
                break;
            case 3:
                addShape(dX, dY, "D");
                break;
            case 4:
                addShape(eX, eY, "E");
                break;
            case 5:
                addShape(fX, fY, "F");
                break;
            case 6:
                addShape(gX, gY, "G");
                break;
            case 7:
                addShape(hX, hY, "H");
                break;
            case 8:
                addShape(iX, iY, "I");
                break;
            case 9:
                addShape(jX, jY, "J");
                break;
            case 10:
                addShape(kX, kY, "K");
                break;
            case 11:
                addShape(lX, lY, "L");
                break;
            default:
                break;
            }

            if (getVertAxis().getLabels().getStyle() == AxisLabelStyle.AUTO) {
                getVertAxis().getLabels().setStyle(AxisLabelStyle.VALUE);
            }
        }

    }
}

