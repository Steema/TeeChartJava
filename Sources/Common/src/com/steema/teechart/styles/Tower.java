package com.steema.teechart.styles;


import com.steema.teechart.languages.Language;
import com.steema.teechart.editors.gallery.Gallery;
import com.steema.teechart.IBaseChart;
import com.steema.teechart.drawing.Color;
import com.steema.teechart.drawing.Point;
import com.steema.teechart.Rectangle;


/**
 *
 * <p>Title: Tower class</p>
 *
 * <p>Description: Tower Series.
 * The Tower Series is designed to use a 0 or 1 based integer grid index.<br>
 * X and Z values should be sequential values of 1.<br></p>
 *
 * <p>Example:
 * <pre><font face="Courier" size="4">
 * series = new Tower(myChart.getChart());
 * series.setOrigin(500.0);
 * series.setUseOrigin(true);
 * series.setPaletteStyle(PaletteStyle.STRONG);
 * series.setUseColorRange(false);
 * series.setUsePalette(true);
 * series.fillSampleValues();
 * </font></pre></p>
 *
 * <p>Copyright (c) 2005-2019 by Steema Software SL. All Rights
 * Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 *
 */
public class Tower extends Custom3DGrid {
    private boolean dark3D = true;
    private double origin;
    private int percDepth = 100;
    private int percWidth = 100;
    private TowerStyle towerStyle = TowerStyle.CUBE;
    private boolean useOrigin;

    transient private double iOffW;
    transient private double iOffD;

    transient private boolean tmpChangeBrush;

    public Tower() {
        this((IBaseChart)null);
    }

    public Tower(IBaseChart c) {
        super(c);
    }
    
    public void assign(Series source) {
        if (source instanceof Tower) {
            Tower tmp = (Tower)source;
            tmp.setPercentWidth(((Tower)source).getPercentWidth()); 
            tmp.setPercentDepth(((Tower)source).getPercentDepth()); 
            tmp.setTowerStyle(((Tower)source).getTowerStyle()); 
            tmp.setTransparency(((Tower)source).getTransparency());
            tmp.setUseOrigin(((Tower)source).getUseOrigin());
        }
        super.assign(source);
    }     

    final private class CellPosition {
        Rectangle r = new Rectangle();
        int top, bottom;
        int z0, z1;
    }

    private CellPosition calcCell(int index) {
        CellPosition result = new CellPosition();

        double tmpValue = getXValues().value[index];

        result.r.x = calcXPosValue(tmpValue - iOffW);
        result.r.width = calcXPosValue(tmpValue + iOffW) - result.r.x;

        result.top = calcYPos(index);
        result.r.y = result.top;

        result.bottom = useOrigin ? calcYPosValue(getOrigin()) :
                  calcYPosValue(getYValues().getMinimum());

        if (result.r.y > result.bottom) {
            int tmpY = result.r.y;
            result.r.y = result.bottom;
            result.r.height = tmpY - result.bottom;
        } else {
            result.r.height = result.bottom - result.r.y;
        }

        tmpValue = getZValues().value[index];
        result.z0 = chart.getAxes().getDepth().calcYPosValue(tmpValue - iOffD);
        result.z1 = chart.getAxes().getDepth().calcYPosValue(tmpValue + iOffD);
        return result;
    }

    /**
     * When true, draws right and top sides of cubes using a darker color. <br>
     * Default value: true
     *
     * @return boolean
     */
    public boolean getDark3D() {
        return dark3D;
    }

    /**
     * When true, draws right and top sides of cubes using a darker color. <br>
     * Default value: true
     *
     * @param value boolean
     */
    public void setDark3D(boolean value) {
        dark3D = setBooleanProperty(dark3D, value);
    }


    /**
     * The percentage of "Z" depth size for tower cubes. <br>
     * Default value: 100
     *
     * @return int
     */
    public int getPercentDepth() {
        return percDepth;
    }

    /**
     * The percentage of "Z" depth size for tower cubes. <br>
     * Default value: 100
     *
     * @param value int
     */
    public void setPercentDepth(int value) {
        percDepth = setIntegerProperty(percDepth, value);
    }

    /**
     * The percentage of "X" width size for tower cubes.<br>
     * Default value: 100
     *
     * @return int
     */
    public int getPercentWidth() {
        return percWidth;
    }

    /**
     * The percentage of "X" width size for tower cubes.<br>
     * Default value: 100
     *
     * @param value int
     */
    public void setPercentWidth(int value) {
        percWidth = setIntegerProperty(percWidth, value);
    }

    /**
     * The kind of tower style to display. <br>Available styles: Cube,
     * Rectangle, Cover, Cylinder, Arrow, Cone and Pyramid .<br>
     * Default value: Cube
     *
     *
     * @return TowerStyle
     */
    public TowerStyle getTowerStyle() {
        return towerStyle;
    }

    /**
     * Determines the kind of tower style to display. <br>
     * Default value: Cube
     *
     *
     * @param value TowerStyle
     */
    public void setTowerStyle(TowerStyle value) {
        if (towerStyle != value) {
            towerStyle = value;
            invalidate();
        }
    }

    /**
     * The axis vertical position considered to be the "origin" of points. <br>
     * Points with values below origin are considered "negatives". <br>
     * By default, Origin is not used. Instead, the lowest Y value of the
     * series is considered to be the origin. <br>
     *
     * @see #getUseOrigin
     * @return double
     */
    public double getOrigin()
    {
        return origin;
    }

    /**
     * The axis vertical position considered to be the "origin" of points. <br>
     *
     * @see #getUseOrigin
     * @param value double
     */
    public void setOrigin(double value) {
        origin = setDoubleProperty(origin, value);
    }

    /**
     * When true, Origin is used as the reference value where points are
     * considered positive or negative. <br>
     * When false, the lowest Y value of the series is the reference value
     * (no points are negative). <br>
     * Default value: false
     *
     * @return boolean
     */
    public boolean getUseOrigin() {
        return useOrigin;
    }

    /**
     * When true, Origin is used as the reference value where points are
     * considered positive or negative. <br>
     * When false, the lowest Y value of the series is the reference value
     * (no points are negative). <br>
     * Default value: false
     *
     * @param value boolean
     */
    public void setUseOrigin(boolean value) {
        useOrigin = setBooleanProperty(useOrigin, value);
    }

    /**
     * The Transparency level from 0 to 100%. <br>
     * Default value: 0
     *
     * @return int
     */
    public int getTransparency() {
        return getBrush().getTransparency();
    }

    /**
     * Sets Transparency level from 0 to 100%. <br>
     * Default value: 0
     *
     * @param value int
     */
    public void setTransparency(int value) {
        getBrush().setTransparency(value);
    }

    public void createSubGallery(Gallery addSubChart) {
        super.createSubGallery(addSubChart);
        addSubChart.createSubChart(Language.getString("NoBorder"));
        addSubChart.createSubChart(Language.getString("SingleColor"));
        addSubChart.createSubChart(Language.getString("Marks"));
        addSubChart.createSubChart(Language.getString("Hollow"));
        addSubChart.createSubChart(Language.getString("Rectangle"));
        addSubChart.createSubChart(Language.getString("Cover"));
        addSubChart.createSubChart(Language.getString("Circle"));
        addSubChart.createSubChart(Language.getString("GalleryArrow"));
        addSubChart.createSubChart(Language.getString("Cone"));
        addSubChart.createSubChart(Language.getString("Pyramid"));
    }

    protected void drawMark(int valueIndex, String s,
                            SeriesMarksPosition position) {
        getMarks().applyArrowLength(position);
        super.drawMark(valueIndex, s, position);
    }

    public void prepareForGallery(boolean isEnabled) {
        super.prepareForGallery(isEnabled);
        iInGallery = true;
        createValues(5, 5);
    }

    public void setSubGallery(int index) {
        switch (index) {
        case 2:
            getPen().setVisible( false);
            break;
        case 3:
            setUsePalette(false);
            break;
        case 4:
            getMarks().setVisible( true);
            break;
        case 5:
            getBrush().setVisible( false);
            break;
        case 6:
            setTowerStyle( TowerStyle.RECTANGLE);
            break;
        case 7:
            setTowerStyle(TowerStyle.COVER);
            break;
        case 8:
            setTowerStyle(TowerStyle.CYLINDER);
            break;
        case 9:
            setTowerStyle(TowerStyle.ARROW);
            break;
        case 10:
            setTowerStyle(TowerStyle.CONE);
            break;
        case 11:
            setTowerStyle(TowerStyle.PYRAMID);
            break;
        default:
            super.setSubGallery(index);
        }
    }

    /**
     * The Maximum Value of the Series X Values List.
     *
     * @return double
     */
    public double getMaxXValue() {
        return getXValues().getMaximum() + 0.5;
    }

    /**
     * The biggest value in the Series ZValues array.
     *
     * @return double
     */
    public double getMaxZValue() {
        return getZValues().getMaximum() + 0.5;
    }

    /**
     * The Minimum Value of the Series X Values List.
     *
     * @return double
     */
    public double getMinXValue() {
        return getXValues().getMinimum() - 0.5;
    }

    /**
     * The lowest value in the Series ZValues array.
     *
     * @return double
     */
    public double getMinZValue() {
        return getZValues().getMinimum() - 0.5;
    }

    private void drawCell(int x, int z) {
        int tmp = gridIndex.getPCells(x).getCells(z);
        if (tmp != -1) {
            Color tmpColor = getValueColor(tmp);

            if (tmpColor != Color.EMPTY) {
                if (tmpChangeBrush) {
                    chart.getGraphics3D().setBrush(getBrush());
                    chart.getGraphics3D().getBrush().setColor(tmpColor.transparentColor(getTransparency()));
                }

                CellPosition c = calcCell(tmp);

                if (towerStyle==TowerStyle.CUBE) {
                    chart.getGraphics3D().cube(c.r, c.z0, c.z1, dark3D);
                }
                else
                if (towerStyle==TowerStyle.RECTANGLE) {
                    chart.getGraphics3D().rectangle(c.r, (c.z0 + c.z1) / 2);
                }
                else
                if (towerStyle==TowerStyle.COVER) {
                    chart.getGraphics3D().rectangleY(c.r.x, c.top, c.r.getRight(), c.z0, c.z1);
                }
                else
                if (towerStyle==TowerStyle.CYLINDER) {
                    chart.getGraphics3D().cylinder(true, c.r, c.z0, c.z1, getDark3D());
                }
                else
                if (towerStyle==TowerStyle.ARROW) {
                    int tmpMidX = (c.r.x + c.r.getRight()) / 2;
                    chart.getGraphics3D().arrow(true, new Point(tmpMidX, c.bottom),
                                           new Point(tmpMidX, c.top),
                                           c.r.getRight() - c.r.x,
                                           (c.r.getRight() - c.r.x) / 2,
                                           (c.z0 + c.z1) / 2);
                }
                else
                if (towerStyle==TowerStyle.CONE) {
                    chart.getGraphics3D().cone(true, c.r.x, c.top, c.r.getRight(),
                                          c.bottom, c.z0, c.z1, dark3D);
                }
                else
                if (towerStyle==TowerStyle.PYRAMID) {
                    chart.getGraphics3D().pyramid(true, c.r.x, c.top, c.r.getRight(),
                                             c.bottom, c.z0, c.z1, dark3D);
                }
            }
        }
    }

//		public  int clicked(int x,int y) {
//		int tmp,z0,z1;
//		int tmpTop,tmpBottom;
//		Rectangle tmpR;
//		Point[] tmpP;
//		Point P;
//			if (Count>0) {
//						   IOffW=FPercWidth*0.005;
//		IOffD=FPercDepth*0.005;
//		P=new Point(x,y);
//
//		for (int tmpX=1; tmpX<=NumXValues; tmpX++)
//		for (int tmpZ=1; tmpZ<=NumZValues; tmpZ++) {
//			tmp=GridIndex[tmpX,tmpZ];
//
//		if ((tmp!=-1) && (getValueColor(tmp)!=Color.Transparent)) {
//                     tmpR=CalcCell(tmp,tmpTop,tmpBottom,z0,z1);
//
//		if (TowerStyle==tsCover) {
//			tmpP[0]:=ParentChart.drawing.Calculate3DPosition(tmpR.Left,tmpTop,z0);
//		  tmpP[1]:=ParentChart.drawing.Calculate3DPosition(tmpR.Right,tmpTop,z0);
//		  tmpP[2]:=ParentChart.drawing.Calculate3DPosition(tmpR.Right,tmpTop,z1);
//		  tmpP[3]:=ParentChart.drawing.Calculate3DPosition(tmpR.Left,tmpTop,z1);
//
//		  if (PointInPolygon(P,tmpP))
//									  return tmp;
//		  }
//		  }
//		  }
//		}
//
//		return -1;
//		}

    private void drawCells() {
        iOffW = percWidth * 0.005;
        iOffD = percDepth * 0.005;

        if (chart.getAxes().getDepth().getInverted()) {
            if (!drawValuesForward()) {
                for (int x = getNumXValues(); x >= 1; x--) {
                    for (int z = 1; z <= getNumZValues(); z++) {
                        drawCell(x, z);
                    }
                }
            } else {
                for (int x = 1; x <= getNumXValues(); x++) {
                    for (int z = 1; z <= getNumZValues(); z++) {
                        drawCell(x, z);
                    }
                }
            }
        } else
        if (!drawValuesForward()) {
            for (int x = getNumXValues(); x >= 1; x--) {
                for (int z = getNumZValues(); z >= 1; z--) {
                    drawCell(x, z);
                }
            }
        } else {
            for (int x = 1; x <= getNumXValues(); x++) {
                for (int z = getNumZValues(); z >= 1; z--) {
                    drawCell(x, z);
                }
            }
        }
    }

    protected void draw() {
        tmpChangeBrush = getBrush().getVisible();
        chart.getGraphics3D().setPen(getPen());
        chart.getGraphics3D().setBrush(getBrush());
        drawCells();
    }


    /**
     * Gets descriptive text.
     *
     * @return String
     */
    public String getDescription() {
        return Language.getString("GalleryTower");
    }
}

