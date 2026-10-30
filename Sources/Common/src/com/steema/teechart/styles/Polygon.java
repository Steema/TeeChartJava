package com.steema.teechart.styles;

import com.steema.teechart.IBaseChart;
import com.steema.teechart.Rectangle;
import com.steema.teechart.drawing.Color;
import com.steema.teechart.drawing.ChartPen;
import com.steema.teechart.drawing.Point;
import com.steema.teechart.axis.Axis;
import com.steema.teechart.drawing.ChartBrush;
import com.steema.teechart.drawing.Gradient;
import com.steema.teechart.TeeBase;

import com.steema.teechart.drawing.Graphics3D;
import com.steema.teechart.drawing.IGraphics3D;

    public class Polygon extends TeeBase {
    	
    	private static final long serialVersionUID = 1L;
    	
        private PolygonSeries fPoints;
        private boolean closed;
        private boolean parentPen;
        private boolean parentBrush;
        private int index;
        private Point[] iPoints;
        private int transparency;

        public Map parentSeries;

        public int getIndex() {
            return index;
        }

        public void setIndex(int value) {
            index = value;
        }

        public Polygon(IBaseChart chart) {
            this(null, chart);
        }

        public Polygon(PolygonList collection, IBaseChart chart) {
            super(chart);
            setChart(chart);

            closed = true;
            fPoints = new PolygonSeries();
            fPoints.setPolygon(this);
            fPoints.getXValues().setOrder(ValueListOrder.NONE);
            fPoints.setShowInLegend(false);

            parentPen = true;
            parentBrush = true;

            if (collection != null) {
                parentSeries = collection.getOwner();
                parentSeries.add(0, 0);
            }

        }

        /**
         * Returns true if the polygon fits inside the available Chart
         * ChartRect rectangle.
         *
         * @return boolean
         */
        public boolean visible() {
            Rectangle tmpR = new Rectangle(Rectangle.EMPTY);
            boolean result = !getChart().getAspect().getClipPoints();

            if (!result) {
                Axis tmpHoriz = parentSeries.getHorizAxis();
                tmpR.x = tmpHoriz.calcPosValue(getPoints().getXValues().
                                               getMinimum());
                tmpR.width = tmpHoriz.calcPosValue(getPoints().getXValues().
                        getMaximum()) -
                             tmpR.x;

                int tmp1;
                int tmp2;

                if (tmpHoriz.getInverted()) {
                    tmp1 = tmpR.getLeft();
                    tmp2 = tmpR.getRight();
                    //CDI 01/03/05 misc.Utils.SwapInteger(ref tmp1,ref tmp2);
                    tmpR.x = tmp1;
                    tmpR.width = tmp2 - tmpR.getLeft();
                }

                Axis tmpVert = parentSeries.getVertAxis();
                tmpR.y = tmpVert.calcPosValue(getPoints().getYValues().
                                              getMaximum());
                tmpR.height = tmpVert.calcPosValue(getPoints().getYValues().
                        getMinimum()) -
                              tmpR.y;

                if (tmpVert.getInverted()) {
                    tmp1 = tmpR.y;
                    tmp2 = tmpR.getBottom();

                    int tmp = tmp1;
                    tmp1 = tmp2;
                    tmp2 = tmp;

                    tmpR.y = tmp1;
                    tmpR.height = tmp2 - tmpR.y;
                }

                result = containsRect((Rectangle)getChart().getChartRect().copy(), tmpR);
            }

            return result;
        }

        private boolean containsRect(Rectangle r1, Rectangle r2) {
            if (r2.x > r1.x) {
                r1.x = r2.x;
            }
            if (r2.getRight() < r1.getRight()) {
                r1.width = r2.getRight() - r2.x;
            }

            if (r2.y > r1.y) {
                r1.y = r2.y;
            }
            if (r2.getBottom() < r1.getBottom()) {
                r1.height = r2.getBottom() - r2.y;
            }

            return!((r1.getRight() < r1.x) || (r1.getBottom() < r1.y));
        }


        public void draw(IGraphics3D g, int valueIndex) {

            if (getPoints().getActive() && getPoints().getCount() > 1 &&
                visible()) {
                if (getParentPen()) {
                    g.setPen(parentSeries.getPen());
                } else {
                    g.setPen(getPen());
                }

                if (getParentBrush()) {
                    g.setBrush(parentSeries.getBrush());
                    Color tmpColor = parentSeries.getValueColor(valueIndex);
                    g.getBrush().setColor(tmpColor);
                    setColor(tmpColor);
                                        
                    if (transparency > 0) {
                        g.getBrush().setTransparency(transparency);
                    }
                } else {
                    g.setBrush(getBrush());
                    setColor(getBrush().getColor());
                }

                getPolygonPoints();

                boolean tmpIs3D = parentSeries.getChart().getAspect().
                                  getView3D();

                int tmpZ = tmpIs3D ? parentSeries.calcZPos(valueIndex) : 0;

                if (tmpIs3D) {
                    if (closed) {
                        g.polygon(tmpZ, iPoints);
                    } else {
                        g.polyLine(tmpZ, iPoints);
                    }
                } else {
                    if (closed) {
                        g.polygon(iPoints);
                    } else {
                        g.polyLine(iPoints);
                    }
                }
            }
        }

        /**
         * Returns the rectangle in pixels that corresponds to the maximum
         * space used by the polygon.
         *
         * @return Rectangle
         */
        public Rectangle getBounds() {
            return Graphics3D.polygonRect(getPolygonPoints());
        }

        /**
         * Accesses the co-ordinates PointList which determine the boundary of
         * the polygon.
         *
         * @return PolygonSeries
         */
        public PolygonSeries getPoints() {
            return fPoints;
        }

        /**
         * Accesses the co-ordinates PointList which determine the boundary of
         * the polygon.
         *
         * @param value PolygonSeries
         */
        public void setPoints(PolygonSeries value) {
            fPoints = value;
        }
        
        public void calcPoints(int xoffset, int yoffset) {
            getPolygonPoints();
            for (int t = 0; t < iPoints.length; t++)
            {
                iPoints[t].x += xoffset;
                iPoints[t].y += yoffset;
            }
        }

        /**
         * The coordinates of all Points of the Polygon.
         *
         * @return Point[]
         */
        public Point[] getPolygonPoints() {

            Axis tmpHoriz = parentSeries.getHorizAxis();
            Axis tmpVert = parentSeries.getVertAxis();

            ValueList tmpX = getPoints().getXValues();
            ValueList tmpY = getPoints().getYValues();

            int tmpCount = getPoints().getCount();
            iPoints = new Point[tmpCount];
            Point[] result = iPoints;

            for (int t = 0; t < tmpCount; ++t) {
                result[t] = new Point(tmpHoriz.calcPosValue(tmpX.getValue(t)),
                                      tmpVert.calcPosValue(tmpY.getValue(t)));
            }

            return result;
        }

        /**
         * Determines the kind of brush used to fill the polygon interior.
         *
         * @return ChartBrush
         */
        public ChartBrush getBrush() {
            return getPoints().bBrush;
        }

        /**
         * When true, the polygon is displayed and filled using the polygon
         * Pen and Brush methods.
         * When false, a poly-line is displayed using the Pen method.
         *
         * @return boolean
         */
        public boolean getClosed() {
            return closed;
        }

        /**
         * When true, the polygon is displayed and filled using the polygon
         * Pen and Brush methods.
         *
         * @param value boolean
         */
        public void setClosed(boolean value) {
            closed = setBooleanProperty(closed, value);
        }

        /**
         * The polygon uses the same Pen as its Parentseries, when true.<br>
         * If false, the polygon uses its own Pen.
         *
         * @return boolean
         */
        public boolean getParentPen() {
            return parentPen;
        }

        /**
         * The polygon uses the same Pen as its Parentseries, when true.<br>
         * If false, the polygon uses its own Pen.
         *
         * @param value boolean
         */
        public void setParentPen(boolean value) {
            parentPen = setBooleanProperty(parentPen, value);
        }

        /**
         * The Color used to fill the polygon.<br>
         * Or that used to draw the Brush pattern.
         *
         * @param index int
         * @return Color
         */
        public Color getColor(int index) {
            return parentSeries.getValueColor(index);
        }

        /**
         * Sets Color used to fill the polygon.<br>
         * Or that used to draw the Brush pattern.
         *
         * @param value Color
         */
        public void setColor(Color value) {
            getPoints().setColor(value);
        }

        /**
         * Determines the kind of polygon's interior gradient effect.
         *
         * @return Gradient
         */
        public Gradient getGradient() {
            return getPoints().bBrush.getGradient();
        }

        /**
         * The polygon uses the same Brush as its parentseries (Map Series)
         * when true.<br>
         * If false, the polygon uses its own Brush property.
         *
         * @return boolean
         */
        public boolean getParentBrush() {
            return parentBrush;
        }

        /**
         * The polygon uses the same Brush as its parentseries (Map Series)
         * when true.<br>
         *
         * @param value boolean
         */
        public void setParentBrush(boolean value) {
            parentBrush = setBooleanProperty(parentBrush, value);
        }

        /*[DesignerSerializationVisibility(DesignerSerializationVisibility.Hidden)]*/
        /**
         * Specifies the position on the Z-axis of the 3D Map Series.<br>
         * In Chart terms, this will set a Z plane position, a lower value of
         * Z will be drawn in front of a higher Z value.<br>
         * Map Series is intended to be viewed in 2D and the Z value is
         * provided to be used as a value for a Polygon (ie. for demographic
         * maps). If colour Palette is used then the Z value will set the
         * Polygon colour.
         *
         * @return double
         */
        public double getZ() {
            return parentSeries.getZValues().getValue(index);
        }

        /**
         * Specifies the position on the Z-axis of the 3D Map Series.<br>
         *
         * @param value double
         */
        public void setZ(double value) {
            parentSeries.getZValues().setValue(index, value);
            parentSeries.repaint();
        }

        /**
         * Adds a boundary coordinate to the polygon.<br>
         * The polygon will be drawn in sequential order of the coordinates.
         * (ie. from point 1 (X0,Y0) to point 2 (X1,Y1) to point 3 (X2,Y2), ...)
         *
         * @param p Point
         * @return int
         */
        public int add(Point p) {
            return getPoints().add(p.x, p.y);
        }

        /// <summary>
        /// Adds a boundary coordinate to the polygon. The polygon will be drawn in sequential order of the coordinates. (ie. from point 1 (X0,Y0) to point 2 (X1,Y1) to point 3 (X2,Y2), ....)
        /// </summary>
        /**
         * Adds a boundary coordinate to the polygon.<br>
         * The polygon will be drawn in sequential order of the coordinates.
         * (ie. from point 1 (X0,Y0) to point 2 (X1,Y1) to point 3 (X2,Y2), ...)
         *
         * @param x double
         * @param y double
         * @return int
         */
        public int add(double x, double y) {
            return getPoints().add(x, y);
        }

        /**
         * Specifies the text string that labels the polygon
         *
         * @return String
         */
        public String getText() {
            return parentSeries.getLabels().getString(index);
        }

        /**
         * Specifies the text string that labels the polygon
         *
         * @param value String
         */
        public void setText(String value) {
            parentSeries.getLabels().setString(index, value);
            getPoints().setTitle(value);
        }

        /**
         * Specifies the Pen used to draw the border of the polygon.
         *
         * @return ChartPen
         */
        public ChartPen getPen() {
            return getPoints().getPen();
        }

        /**
         * Amount of opacity used to fill the polygon interior.<br>
         * 100 means fully transparent, 0 means fully opaque.
         *
         * @return int
         */
        public int getTransparency() {
//            return getBrush().getTransparency();
            return transparency;
        }

        /**
         * Amount of opacity used to fill the polygon interior.<br>
         * 100 means fully transparent, 0 means fully opaque.
         *
         * @param value int
         */
        public void setTransparency(int value) {
            transparency = value;
            getBrush().setTransparency(value);
        }
    }