package com.steema.teechart.functions;

import com.steema.teechart.drawing.Point;


/**
 *
 * <p>Title: Spline class</p>
 *
 * <p>Description: Spline smoothing.</p>
 *
 * <p>Copyright (c) 2005-2019 by Steema Software SL. All Rights
 * Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 *
 */
public class Spline {
    private boolean build;
    private int capacity;
    private boolean interpolate;
    private Point.Double[] pointList;
    private boolean[] knuckleList;
    private Point.Double[] vertexList;
    private double[][] matrix;
    private int fragments;
    private int noPoints;
    private int noVertices;

    /**
     * Spline smoothing.
     */
    public Spline() {
        super();
    }

    /**
     * Indicates the spline has already calculated smooth points. <br>
     * Set to false to force the spline to rebuild smooth points.<br>
     *
     * @return boolean
     */
    public boolean getBuild() {
        return build;
    }

    /**
     * Indicates the spline has already calculated smooth points. <br>
     * Set to false to force the spline to rebuild smooth points.<br>
     *
     * @param value boolean
     */
    public void setBuild(boolean value) {
        if (!value) {
            // Release allocated memory for vertices
            if (build) {
                clearVertexList();
            }
            noVertices = 0;
        }
        build = value;
    }

    private void setCapacity(int value) {

        if (value != capacity) {
            int currentSize = capacity;
            Point.Double[] oldPoints = pointList;
            boolean[] oldKnuckle = knuckleList;
            pointList = null;
            knuckleList = null;

            if (value > 0) {
                pointList = new Point.Double[value];
                knuckleList = new boolean[value];

                if (capacity != 0) {
                    System.arraycopy(oldPoints,0,pointList,0,oldPoints.length);
                    System.arraycopy(oldKnuckle,0,knuckleList,0,oldKnuckle.length);
                }
            }

            if (currentSize != 0) {
                oldPoints = null;
                oldKnuckle = null;
            }

            capacity = value;
        }
    }

    /**
     * The number of resulting smooth points.<br>
     * Must be a multiple of source points.
     *
     * @return int
     */
    public int getFragments() {
        return fragments;
    }

    /**
     * Sets the number of resulting smooth points.<br>
     * Must be a multiple of source points.
     *
     * @param value int
     */
    public void setFragments(int value) {
        if (fragments != value) {
            fragments = Math.min(value, 600);
        }
    }

    /**
     * When true, the spline calculates interpolated points that will pass
     * exactly over source points.<br>
     * When false, the spline resulting points do not necessarily pass over
     * source points. <br>
     * Default value: false
     *
     * @return boolean
     */
    public boolean getInterpolated() {
        return interpolate;
    }

    /**
     * When true, the spline calculates interpolated points that will pass
     * exactly over source points.<br>
     * When false, the spline resulting points do not necessarily pass over
     * source points. <br>
     * Default value: false
     *
     * @param value boolean
     */
    public void setInterpolated(boolean value) {
        if (value != interpolate) {
            interpolate = value;
            setBuild(false);
        }
    }

    public Point.Double getPoint(int index) {
        return pointList[index];
    }
    /**
     * Use to set the source point for the Knuckle <br><br>
     *
     * @param index int
     * @param value Double
     */
    public void setPoint(int index, Point.Double value) {
        pointList[index] = value;
        setBuild(false);
    }
    /**
     * Makes the Index source point a control point. <br><br>
     * By default, TSmoothingFunction does not set any source point <br>
     * as a control point.
     *
     * @param index int
     * @return Double
     */
    private boolean getKnuckle(int index) {
        if ((index == 0) || (index == noPoints - 1)) {
            return false;
        } else {
            return knuckleList[index];
        }
    }
    /**
     * Makes the Index source point a control point. <br><br>
     * By default, TSmoothingFunction does not set any source point <br>
     * as a control point.
     *
     * @param index int
     * @param value boolean
     */
    public void setKnuckle(int index, boolean value) {
        knuckleList[index] = value;
        setBuild(false);
    }

    /**
     * Returns the number of total source points. <br>
     * For each point that is a control point ( Knuckle[ Index ] is true ),
     * the number of vertices is incremented by 2. <br>
     *
     * @return int
     */
    public int numberOfVertices() {
        if (!build) {
            rebuild();
        }
        return noVertices;
    }

    static final double MINLIMIT = 1e-5;

    private void fillMatrix() {
        if ((noVertices > 2) && (noVertices <= 250)) {
            int i;

            for (i = 2; i < noVertices; i++) {
                matrix[i][i - 1] = 1f / 6f;
                matrix[i][i] = 2f / 3f;
                matrix[i][i + 1] = 1f / 6f;
            }

            matrix[1][1] = 1;
            matrix[noVertices][noVertices] = 1;

            i = 3;
            while (i < noVertices - 1) {
                if ((Math.abs(vertexList[i].x - vertexList[i - 1].x) <
                     MINLIMIT) &&
                    (Math.abs(vertexList[i + 1].x - vertexList[i].x) <
                     MINLIMIT) &&
                    (Math.abs(vertexList[i].y - vertexList[i - 1].y) <
                     MINLIMIT) &&
                    (Math.abs(vertexList[i + 1].y - vertexList[i].y) <
                     MINLIMIT)) {
                    for (int j = i - 1; j <= i + 1; j++) {
                        matrix[j][j - 1] = 0;
                        matrix[j][j] = 1;
                        matrix[j][j + 1] = 0;
                    }

                    i += 2;
                } else {
                    i++;
                }
            }
        }
    }

    /**
     * Calculates new smoothed points from list of points.
     */
    public void rebuild() {
        Point.Double vertex2D;

        if (noPoints > 1) {
            clearVertexList();
            noVertices = 0;

            for (int i = 0; i < noPoints; i++) {
                if (getKnuckle(i)) {
                    noVertices += 3;
                } else {
                    noVertices++;
                }
            }

            vertexList = new Point.Double[noVertices + 2];

            int j = 0;
            for (int i = 0; i < noPoints; i++) {
                vertex2D = getPoint(i);
                if (getKnuckle(i)) {
                    vertexList[j + 1] = vertex2D;
                    vertexList[j + 2] = vertex2D;
                    j += 2;
                }
                vertexList[j + 1] = pointList[i];
                j++;
            }

            if (interpolate) {
                matrix = new double[noVertices + 1][];
                for (int i = 1; i <= noVertices; i++) {
                    matrix[i] = new double[noVertices + 1];
                }

                fillMatrix();
                doInterpolate();
                matrix = null;
            }
        }
        build = true;
        phantomPoints();
    }

    private void doInterpolate() {
        if ((noVertices < 250) && (noVertices > 2)) {
            Point.Double[] tmp = new Point.Double[noVertices + 2];
            double factor;

            for (int i = 1; i <= noVertices; i++) {
                for (int j = i + 1; j <= noVertices; j++) {
                    factor = matrix[j][i] / matrix[i][i];
                    for (int k = 1; k <= noVertices; k++) {
                        matrix[j][k] = matrix[j][k] - factor * matrix[i][k];
                    }

                    vertexList[j].x = (float) (vertexList[j].x -
                                               factor * vertexList[j - 1].x);
                    vertexList[j].y = (float) (vertexList[j].y -
                                               factor * vertexList[j - 1].y);
                }
            }

            tmp[noVertices] = new Point.Double(
                    (float) (vertexList[noVertices].x /
                             matrix[noVertices][noVertices]),
                    (float) (vertexList[noVertices].y /
                             matrix[noVertices][noVertices]));

            for (int i = noVertices - 1; i >= 1; i--) {
                tmp[i] = new Point.Double(
                        (float) ((1 / matrix[i][i]) *
                                 (vertexList[i].x - matrix[i][i +
                                  1] * tmp[i + 1].x)),
                        (float) ((1 / matrix[i][i]) *
                                 (vertexList[i].y - matrix[i][i +
                                  1] * tmp[i + 1].y)));
            }

            clearVertexList();
            vertexList = tmp;
        }
    }

    /**
     * Adds a new source point with specified X and Y values.
     *
     * @param x double
     * @param y double
     */
    public void addPoint(double x, double y) {
        if (noPoints == capacity) {
            setCapacity(capacity + 25);
        }
        setPoint(noPoints, new Point.Double(x, y));
        noPoints++;
        setBuild(false);
    }

    private void clearVertexList() {
        vertexList = null;
    }

    /**
     * Removes all source points.
     */
    public void clear() {
        if (numberOfVertices() > 0) {
            clearVertexList();
        }
        noPoints = 0;
        noVertices = 0;
        setBuild(false);
        setCapacity(0);
        interpolate = false;
        fragments = 100;
    }

    private void phantomPoints() {
        if (numberOfVertices() > 1) {
            final int i = 0;

            vertexList[i] = new Point.Double(
                    2 * vertexList[i + 1].x - vertexList[i + 2].x,
                    2 * vertexList[i + 1].y - vertexList[i + 2].y);

            vertexList[numberOfVertices() + 1] = new Point.Double(
                    2 * vertexList[numberOfVertices()].x -
                    vertexList[numberOfVertices() - 1].x,
                    2 * vertexList[numberOfVertices()].y -
                    vertexList[numberOfVertices() - 1].y);
        }
    }

    /**
     * Returns an interpolated point.
     *
     * @param parameter double
     * @return Double
     */
    public Point.Double value(double parameter) {
        Point.Double result = new Point.Double(0, 0);

        if (noPoints < 2) {
            return result;
        }

        if (!build) {
            rebuild();
        }

        double mid = (numberOfVertices() - 1) * parameter + 1;
        //int s=(int)Math.max(0,Utils.round(mid-1));
        int s = Math.max(0, (int) mid - 1);
        int e = s + 3;
        if (s > noVertices + 1) {
            s = noVertices + 1;
        }

        for (int c = s; c <= e; c++) {
            double dist = Math.abs(c - mid);
            if (dist < 2) {
                double mix = (dist < 1) ?
                             (4.0 / 6.0) - (dist * dist) +
                             (0.5 * dist * dist * dist) :
                             (2 - dist) * (2 - dist) * (2 - dist) / 6;
                result.x += (float) (vertexList[c].x * mix);
                result.y += (float) (vertexList[c].y * mix);

//      if dist<1 then mix:=4/6-dist*dist+0.5*dist*dist*dist
//      else mix:=(2-dist)*(2-dist)*(2-dist)/6;
//      result.x:=Result.x+FVertexList^[c].x*mix;
//      result.y:=Result.y+FVertexList^[c].y*mix;
            }
        }
        return result;
    }
}

