package com.steema.teechart.styles;

import com.steema.teechart.languages.Language;
import java.util.Random;

import com.steema.teechart.Triangle3D;

import com.steema.teechart.Point3D;
import com.steema.teechart.drawing.Color;
import com.steema.teechart.drawing.ChartPen;
import com.steema.teechart.drawing.Point;
import com.steema.teechart.IBaseChart;
import com.steema.teechart.drawing.IGraphics3D;
import com.steema.teechart.misc.Utils;

/**
 *
 * <p>Title: TriSurface class</p>
 *
 * <p>Description: TriSurface Series.</p>
 *
 * <p>Example:
 * <pre><font face="Courier" size="4">
 * series = new TriSurface(myChart.getChart());
 * series.fillSampleValues(30);
 * series.getOutline().setColor(Color.RED);
 * series.getOutline().setWidth(2);
 * series.getOutline().setVisible(true);
 * series.getBrush().setColor(Color.WHITE);
 * series.getPen().setStyle(DashStyle.DOT);
 * series.setEndColor(Color.LIME);
 * </font></pre></p>
 *
 * <p>Copyright (c) 2005-2019 by Steema Software SL. All Rights Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 *
 */
public class TriSurface extends Custom3DPalette {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	transient private static final double RATIO = 1.0E-6;
    transient private static final int NREP = 100;
    transient private static final double TMPRANGE = 0.001;
    transient private static final int TMPRANDOM = 1000;

    transient public int numTriangles;

    transient protected boolean improvedTriangles = true;

    private ChartPen border;
    private boolean hide = true;
    private boolean cacheTriangles;

    transient private int numLines;
    transient private boolean iCreated;
    transient private int[] iPT;
    transient private int[] iPL;
    transient private int nLT3, ipl1;
    transient private int ipl2, iPTI1, iPTI2;
    transient private int[] iTF;
    transient private int jPMN;
    transient private int[] iWL;
    transient private int[] iWP;
    transient private double[] wK;

    transient private double dSQMN, dXMN, dYMN, dXMX, dYMX, dSQMX, aRMX, aRMN;
    transient private int iPMN1, iPMN2, ip1, ip2, nDPM1;
    transient private double xd1, xd2, yd1, yd2;
    transient private int nDP0;
    transient private double dSQI;
    transient private int tmpCount;
    transient private double dSQ12;

    transient private int nTT3, nSH, jPMX, jWL, nLNT3, nLN, iTT3, nLF;

    transient private Triangle3D points;
    transient private Color[] colors;
    transient private Triangle triangles;

    transient private boolean tmpForward;

    private final class Triangle {
        public Color color;
        public Triangle next, prev;
        public Point[] p = new Point[3];
        public double z;
    }


    public TriSurface(IBaseChart c) {
        super(c);
        initFields();
    }

    public TriSurface() {
        this((IBaseChart)null);
    }
    
    public void assign(Series source) {
        if (source instanceof TriSurface) {
            TriSurface tmp = (TriSurface)source;
            tmp.setCacheTriangles(((TriSurface)source).getCacheTriangles()); 
            tmp.setHideTriangles(((TriSurface)source).getHideTriangles());
        }
        super.assign(source);
    }     

    private void initFields() {
        improvedTriangles = true;
        iTF = new int[3];
        points = new Triangle3D();
        colors = new Color[3];
    }

    protected Object readResolve() {
        initFields();
        return super.readResolve();
    }

    protected void addSampleValues(int numValues) {

        Random r = new Random();

        for (int t = 1; t <= 4 + numValues; t++) {
            double tmpX = TMPRANDOM * r.nextDouble() * TMPRANGE;
            double tmpZ = TMPRANDOM * r.nextDouble() * TMPRANGE;
            add(tmpX, Utils.sqr(Utils.exp(tmpZ)) * Math.cos(tmpX * tmpZ),
                tmpZ);
        }
    }

    /**
     * Gets descriptive text.
     *
     * @return String
     */
    public String getDescription() {
        return Language.getString("GalleryTriSurface");
    }

    public boolean getCacheTriangles() {
        return cacheTriangles;
    }

    public void setCacheTriangles(boolean value) {
        cacheTriangles = value;
    }

    public void doBeforeDrawValues() {
        super.doBeforeDrawValues();

        /** @todo AVOID THROW HERE */
        //        if (getCount() < 4) {
        //            NumTriangles = 0;
        //            throw new TeeChartException(Language.getString("TriSurfaceLess"));
        //        } else
        if ((!cacheTriangles) || (!iCreated)) {
            createTriangles();
        }
    }

    protected void drawMark(int valueIndex, String s,
                            SeriesMarksPosition position) {
        getMarks().setZPosition(calcZPos(valueIndex));
        getMarks().applyArrowLength(position);
        super.drawMark(valueIndex, s, position);
    }

    private int iDxchg(int i1, int i2, int i3, int i4) {

        int result = 0;

        double x1 = getXValues().value[i1];
        double y1 = getZValues().value[i1];
        double x2 = getXValues().value[i2];
        double y2 = getZValues().value[i2];
        double x3 = getXValues().value[i3];
        double y3 = getZValues().value[i3];
        double x4 = getXValues().value[i4];
        double y4 = getZValues().value[i4];

        double u3 = (y2 - y3) * (x1 - x3) - (x2 - x3) * (y1 - y3);
        double u4 = (y1 - y4) * (x2 - x4) - (x1 - x4) * (y2 - y4);

        if ((u3 * u4) > 0) {
            double u1 = (y3 - y1) * (x4 - x1) - (x3 - x1) * (y4 - y1);
            double u2 = (y4 - y2) * (x3 - x2) - (x4 - x2) * (y3 - y2);

            double a1 = Utils.sqr(x1 - x3) + Utils.sqr(y1 - y3);
            double b1 = Utils.sqr(x4 - x1) + Utils.sqr(y4 - y1);
            double c1 = Utils.sqr(x3 - x4) + Utils.sqr(y3 - y4);
            double a2 = Utils.sqr(x2 - x4) + Utils.sqr(y2 - y4);
            double b2 = Utils.sqr(x3 - x2) + Utils.sqr(y3 - y2);
            double c2 = Utils.sqr(x2 - x1) + Utils.sqr(y2 - y1);

            double s1 = Utils.sqr(u1) / (c1 * Math.max(a1, b1));
            double s2 = Utils.sqr(u2) / (c1 * Math.max(a2, b2));
            double s3 = Utils.sqr(u3) / (c2 * Math.max(b2, a1));
            double s4 = Utils.sqr(u4) / (c2 * Math.max(b1, a2));

            if (Math.min(s1, s2) < Math.min(s3, s4)) {
                result = 1;
            }
        }

        return result;
    }

    private void calcBorder() {

        for (int i = 1; i <= nLT3 / 3; i++) {
            int jLT3 = i * 3;
            int iPLJ1 = iPL[jLT3 - 2];
            int iPLJ2 = iPL[jLT3 - 1];

            if (((iPLJ1 == ipl1) && (iPLJ2 == iPTI2)) ||
                ((iPLJ2 == ipl1) && (iPLJ1 == iPTI2))) {
                iPL[jLT3] = iTF[1];
            }
            if (((iPLJ1 == ipl2) && (iPLJ2 == iPTI1)) ||
                ((iPLJ2 == ipl2) && (iPLJ1 == iPTI1))) {
                iPL[jLT3] = iTF[2];
            }
        }
    }

    // find closest pair and their midpoint
    private boolean findClosestPair() {
        boolean result = false;
        dSQMN = Utils.sqr(getXValues().value[1] - getXValues().value[0]) +
                Utils.sqr(getZValues().value[1] - getZValues().value[0]);

        iPMN1 = 1;
        iPMN2 = 2;
        ip1 = 1;
        while ((ip1 <= nDPM1) && (!result)) {
            xd1 = getXValues().value[ip1];
            yd1 = getZValues().value[ip1];

            ip2 = ip1 + 1;
            while ((ip2 <= nDP0) && (!result)) {
                xd2 = getXValues().value[ip2];
                yd2 = getZValues().value[ip2];
                dSQI = Utils.sqr(xd2 - xd1) + Utils.sqr(yd2 - yd1);

                if (dSQI == 0.0) { // delete same point and shift array to fill gap
                    for (int i = ip2; i < nDP0; i++) {
                        getXValues().value[i] = getXValues().value[i + 1];
                        getZValues().value[i] = getZValues().value[i + 1];
                    }
                    tmpCount--;
                    nDP0--;
                    nDPM1--;
                    ip2--;
                    result = true;
                } else
                if (dSQI < dSQMN) {
                    dSQMN = dSQI;
                    iPMN1 = ip1;
                    iPMN2 = ip2;
                }
                ip2++;
            }
            ip1++;
        }
        return result;
    }

    private void checkColinear() {

        // if necessary modifies ordering so that first
        // three datapoints are not colinear
        double aR = dSQ12 * RATIO;

        xd1 = getXValues().value[iPMN1];
        yd1 = getZValues().value[iPMN1];

        double dx21 = getXValues().value[iPMN2] - xd1;
        double dy21 = getZValues().value[iPMN2] - yd1;

        int ip = 0;
        int jp = 3;
        double coLinear = 0.0;

        while ((jp <= nDP0) && (coLinear <= aR)) {
            ip = iWP[jp];
            coLinear = Math.abs((getZValues().value[ip] - yd1) * dx21 -
                                (getXValues().value[ip] - xd1) * dy21);
            jp++;
        }
        jp--;

        /** @todo AVOID THROW HERE */
        //        if (jp == NDP0) {
        //            throw new TeeChartException(Language.getString("TriSurfaceAllColinear"));
        //        }

        if (jp != 3) {
            int jpmx = jp;
            jp = jpmx + 1;

            for (int i = 4; i <= jpmx; i++) {
                jp--;
                iWP[jp] = iWP[jp - 1];
            }
            iWP[3] = ip;
        }
    }


    static private double side(double u1, double v1, double u2, double v2,
                               double u3,
                               double v3) {
        return (v3 - v1) * (u2 - u1) - (u3 - u1) * (v2 - v1);
    }

// forms first triangle-vertices in IPT array and border
// line segments and triangle number in IPL array
    private void addFirst() {
        ip1 = iPMN1;
        ip2 = iPMN2;
        int ip3 = iWP[3];

        if (side(getXValues().value[ip1], getZValues().value[ip1],
                 getXValues().value[ip2],
                 getZValues().value[ip2],
                 getXValues().value[ip3], getZValues().value[ip3]) < 0) {

            ip1 = iPMN2;
            ip2 = iPMN1;
        }

        numTriangles = 1;
        nTT3 = 3;

        // first triangle
        iPT[1] = ip1;
        iPT[2] = ip2;
        iPT[3] = ip3;

        numLines = 3;
        nLT3 = 9;
        iPL[1] = ip1;
        iPL[2] = ip2;
        iPL[3] = 1;
        iPL[4] = ip2;
        iPL[5] = ip3;
        iPL[6] = 1;
        iPL[7] = ip3;
        iPL[8] = ip1;
        iPL[9] = 1;
    }

    private void shiftIPLArray() {

        // shifts the IPL array to have invisible border
        // line segments contained in 1st part of array
        for (int i = 1; i <= nSH; i++) {
            int tmp = i * 3;
            int tmpSource = tmp + nLT3;
            iPL[tmpSource - 2] = iPL[tmp - 2];
            iPL[tmpSource - 1] = iPL[tmp - 1];
            iPL[tmpSource] = iPL[tmp];
        }

        for (int i = 1; i <= nLT3 / 3; i++) {
            int tmp = i * 3;
            int tmpSource = tmp + (nSH * 3);
            iPL[tmp - 2] = iPL[tmpSource - 2];
            iPL[tmp - 1] = iPL[tmpSource - 1];
            iPL[tmp] = iPL[tmpSource];
        }

        jPMX -= nSH;
    }

    /**
     * Accesses the TChartHiddenPen object representing the color and style of
     * TriSurface border.<br>
     * Use the methods of Outline to change the color or style.
     *
     * @return ChartPen
     */
    public ChartPen getOutline() {
        if (border == null) {
            border = new ChartPen(chart, Color.WHITE, false);
        }
        return border;
    }

    private void part1() {

        for (int jp2 = 2; jp2 <= numLines; jp2++) {
            ip2 = iPL[3 * jp2 - 2];

            xd2 = getXValues().value[ip2];
            yd2 = getZValues().value[ip2];

            double dX = xd2 - xd1;
            double dY = yd2 - yd1;

            double aR = dY * dXMN - dX * dYMN;

            if (aR <= aRMN) {
                dSQI = Utils.sqr(dX) + Utils.sqr(dY);
                if ((aR < -aRMN) || (dSQI < dSQMN)) {
                    jPMN = jp2;
                    dXMN = dX;
                    dYMN = dY;
                    dSQMN = dSQI;
                    aRMN = dSQMN * RATIO;
                }
            }

            aR = dY * dXMX - dX * dYMX;
            if (aR >= -aRMX) {
                dSQI = Utils.sqr(dX) + Utils.sqr(dY);
                if ((aR > aRMX) || (dSQI < dSQMX)) {
                    jPMX = jp2;
                    dXMX = dX;
                    dYMX = dY;
                    dSQMX = dSQI;
                    aRMX = dSQMX * RATIO;
                }
            }
        }
    }

    private void addTriangles() {

        // adds triangles to IPT array, updates border line
        // segments in IPL array and sets flags for the border
        // line segments to be reexamined in the iwl array
        jWL = 0;
        nLNT3 = 0;

        for (int jp2 = jPMX; jp2 <= numLines; jp2++) {
            int jp2t3 = jp2 * 3;
            ipl1 = iPL[jp2t3 - 2];
            ipl2 = iPL[jp2t3 - 1];
            int iT = iPL[jp2t3];

            // add triangle to IPT array
            numTriangles++;
            nTT3 += 3;
            iPT[nTT3 - 2] = ipl2;
            iPT[nTT3 - 1] = ipl1;
            iPT[nTT3] = ip1;

            // updates borderline segments in ipl array
            if (jp2 == jPMX) {
                iPL[jp2t3 - 1] = ip1;
                iPL[jp2t3] = numTriangles;
            }

            if (jp2 == numLines) {
                nLN = jPMX + 1;
                nLNT3 = nLN * 3;
                iPL[nLNT3 - 2] = ip1;
                iPL[nLNT3 - 1] = iPL[1];
                iPL[nLNT3] = numTriangles;
            }

            // determine vertex that is not on borderline segments
            iTT3 = iT * 3;
            int iPTI = iPT[iTT3 - 2];
            if ((iPTI == ipl1) || (iPTI == ipl2)) {
                iPTI = iPT[iTT3 - 1];
                if ((iPTI == ipl1) || (iPTI == ipl2)) {
                    iPTI = iPT[iTT3];
                }
            }

            // checks if exchange is necessary
            if (iDxchg(ip1, iPTI, ipl1, ipl2) != 0) {
                // modifies ipt array if necessary
                iPT[iTT3 - 2] = iPTI;
                iPT[iTT3 - 1] = ipl1;
                iPT[iTT3] = ip1;
                iPT[nTT3 - 1] = iPTI;

                if (jp2 == jPMX) {
                    iPL[jp2t3] = iT;
                }
                if ((jp2 == numLines) && (iPL[3] == iT)) {
                    iPL[3] = numTriangles;
                }

                // set flags in IWL array
                jWL += 4;
                iWL[jWL - 3] = ipl1;
                iWL[jWL - 2] = iPTI;
                iWL[jWL - 1] = iPTI;
                iWL[jWL] = ipl2;
            }
        }
    }

    private void improveTriangles() {

        // improve triangulation
        int nTT3P3 = nTT3 + 3;

        int iREP = 1;

        while (iREP <= NREP) {
            for (int iLF = 1; iLF <= nLF; iLF++) {
                ipl1 = iWL[iLF * 2 - 1];
                ipl2 = iWL[iLF * 2];

                // locates in ipt array two triangles on
                // both sides of flagged line segment
                int nTF = 0;
                boolean loopFlag = true;

                int tmp = 3;

                while (loopFlag && (tmp <= nTT3)) {
                    iTT3 = nTT3P3 - tmp;
                    int iPT1 = iPT[iTT3 - 2];
                    int iPT2 = iPT[iTT3 - 1];
                    int iPT3 = iPT[iTT3];

                    if ((ipl1 == iPT1) || (ipl1 == iPT2) || (ipl1 == iPT3)) {
                        if ((ipl2 == iPT1) || (ipl2 == iPT2) || (ipl2 == iPT3)) {
                            nTF++;
                            iTF[nTF] = iTT3 / 3;
                            if (nTF == 2) {
                                loopFlag = false;
                            }
                        }
                    }
                    tmp += 3;
                }

                if (nTF >= 2) {

                    int iT1T3 = iTF[1] * 3;
                    iPTI1 = iPT[iT1T3 - 2];

                    if ((iPTI1 == ipl1) || (iPTI1 == ipl2)) {
                        iPTI1 = iPT[iT1T3 - 1];
                        if ((iPTI1 == ipl1) || (iPTI1 == ipl2)) {
                            iPTI1 = iPT[iT1T3];
                        }
                    }

                    int iT2T3 = iTF[2] * 3;
                    iPTI2 = iPT[iT2T3 - 2];

                    if ((iPTI2 == ipl1) || (iPTI2 == ipl2)) {
                        iPTI2 = iPT[iT2T3 - 1];
                        if ((iPTI2 == ipl1) || (iPTI2 == ipl2)) {
                            iPTI2 = iPT[iT2T3];
                        }
                    }

                    // checks if exchange necessary
                    if (iDxchg(iPTI1, iPTI2, ipl1, ipl2) != 0) {
                        iPT[iT1T3 - 2] = iPTI1;
                        iPT[iT1T3 - 1] = iPTI2;
                        iPT[iT1T3] = ipl1;

                        iPT[iT2T3 - 2] = iPTI2;
                        iPT[iT2T3 - 1] = iPTI1;
                        iPT[iT2T3] = ipl2;

                        jWL += 8;

                        iWL[jWL - 7] = ipl1;
                        iWL[jWL - 6] = iPTI1;
                        iWL[jWL - 5] = iPTI1;
                        iWL[jWL - 4] = ipl2;
                        iWL[jWL - 3] = ipl2;
                        iWL[jWL - 2] = iPTI2;
                        iWL[jWL - 1] = iPTI2;
                        iWL[jWL] = ipl1;

                        calcBorder();
                    }
                }
            }

            int tmp = nLF;
            nLF = jWL / 2;

            if (nLF == tmp) {
                break;
            } else {
                // reset IWL array for next round
                jWL = 0;
                tmp = (tmp + 1) * 2;
                int tmpNLF = 2 * nLF;

                while (tmp <= tmpNLF) {
                    jWL += 2;
                    iWL[jWL - 1] = iWL[tmp - 1];
                    iWL[jWL] = iWL[tmp];
                    tmp += 2;
                }
                nLF = jWL / 2;
            }

            iREP++;
        }
    }

    private void sortRest() {
        double xDMP = (getXValues().value[iPMN1] + getXValues().value[iPMN2]) *
                      0.5;
        double yDMP = (getZValues().value[iPMN1] + getZValues().value[iPMN2]) * 0.5;

        // sort other (NDP-2) datapoints in ascending order of
        // distance from midpoint and stores datapoint numbers
        // in IWP array

        int jp1 = 2;
        for (int tmpip1 = 1; tmpip1 <= nDP0; tmpip1++) {
            if ((tmpip1 != iPMN1) && (tmpip1 != iPMN2)) {
                jp1++;
                iWP[jp1] = tmpip1;
                wK[jp1] = Utils.sqr(getXValues().value[tmpip1] - xDMP) +
                          Utils.sqr(getZValues().value[tmpip1] - yDMP);
            }
        }

        for (jp1 = 3; jp1 <= nDPM1; jp1++) {
            dSQMN = wK[jp1];
            jPMN = jp1;

            for (int jp2 = jp1; jp2 <= nDP0; jp2++) {
                double dSQ = wK[jp2];
                if (dSQ < dSQMN) {
                    dSQMN = dSQ;
                    jPMN = jp2;
                }
            }

            int tmp = iWP[jp1];
            iWP[jp1] = iWP[jPMN];
            iWP[jPMN] = tmp;
            wK[jPMN] = wK[jp1];
        }
    }

    private void calcTriangle(int jp1) {
        ip1 = iWP[jp1];

        xd1 = getXValues().value[ip1];
        yd1 = getZValues().value[ip1];

        // determine visible borderline segments
        ip2 = iPL[1];
        jPMN = 1;

        xd2 = getXValues().value[ip2];
        yd2 = getZValues().value[ip2];

        dXMN = xd2 - xd1;
        dYMN = yd2 - yd1;
        dSQMN = Utils.sqr(dXMN) + Utils.sqr(dYMN);

        aRMN = dSQMN * RATIO;
        jPMX = 1;
        dXMX = dXMN;
        dYMX = dYMN;
        dSQMX = dSQMN;
        aRMX = aRMN;

        part1();

        if (jPMX < jPMN) {
            jPMX += numLines;
        }

        nSH = jPMN - 1;
        if (nSH > 0) {
            shiftIPLArray();
        }

        addTriangles();

        numLines = nLN;
        nLT3 = nLNT3;
        nLF = jWL / 2;
        if ((nLF != 0) && improvedTriangles) {
            improveTriangles();
        }
    }

    private void createTriangles() {

        tmpCount = getCount();
        nDP0 = tmpCount - 1;
        nDPM1 = nDP0 - 1;

        if (!findClosestPair()) {

            iPT = new int[6 * tmpCount - 15];
            iPL = new int[6 * tmpCount];
            iWL = new int[18 * tmpCount];
            iWP = new int[tmpCount];
            wK = new double[tmpCount];

            dSQ12 = dSQMN;
            sortRest();
            checkColinear();
            addFirst();

            // add the remaining NDP-3 data points one by one
            for (int jp1 = 4; jp1 <= nDP0; jp1++) {
                calcTriangle(jp1);
            }

            iCreated = true;
        }

        /** @todo AVOID THROW HERE */
        //        else {
        //            throw new TeeChartException(Language.getString("TriSurfaceSimilar"));
        //        }
    }

    /**
     * Deletes all TriSurfaceSeries values.<br>
     * Dependent Series are notified. If no new points are appended to the
     * Series, nothing will be painted.
     */
    public void clear() {
        super.clear();
        iCreated = false;
    }

    protected int numSampleValues() {
        return 15;
    }

    private Point3D calcPoint(int aPoint, int index) {
        colors[aPoint] = getValueColor(index);
        return new Point3D(calcXPos(index),
                           calcYPos(index),
                           calcZPos(index));
    }

    // sort triangles by Z (draw first triangles with bigger depth)
    private void addByZ(Triangle aTriangle) {
        Triangle last = null;
        Triangle tmp = triangles;

        while (tmp != null) {
            if ((tmpForward && (aTriangle.z > tmp.z)) ||
                ((!tmpForward) && (tmp.z > aTriangle.z))) {
                if (tmp.prev != null) {
                    aTriangle.prev = tmp.prev;
                    tmp.prev.next = aTriangle;
                } else {
                    triangles = aTriangle;
                }
                tmp.prev = aTriangle;
                aTriangle.next = tmp;
                return;
            }
            last = tmp;
            tmp = tmp.next;
        }

        if (last != null) {
            last.next = aTriangle;
            aTriangle.prev = last;
        } else {
            triangles = aTriangle;
        }
    }

    /**
     * Hides static constructed tiangles.<br>
     * Constructed triangles behind other triangles are hidden before
     * displaying them. <br>
     * Default value: true
     *
     * @return boolean
     */
    public boolean getHideTriangles() {
        return hide;
    }

    /**
     * Hides static constructed tiangles.<br>
     * Default value: true
     *
     * <p>Example:
     * <pre><font face="Courier" size="4">
     * series.setHideTriangles(true);
     * </font></pre></p>
     *
     * @param value boolean
     */
    public void setHideTriangles(boolean value) {
        hide = setBooleanProperty(hide, value);
    }

    protected void draw() {
        IGraphics3D g = chart.getGraphics3D();

        if (getPen().getVisible() || getBrush().getVisible()) {
            g.setBrush(getBrush());
            g.setPen(getPen());

            if (getHideTriangles() && (!g.getSupportsFullRotation()) &&
                getBrush().getSolid()) {
                // create a list of triangles sorted by Z
                tmpForward = !chart.getAxes().getDepth().getInverted();
                triangles = null;

                for (int t = 1; t <= numTriangles; t++) {

                    int tmp = 3 * t;
                    points.p0 = calcPoint(0, iPT[tmp - 2]);
                    points.p1 = calcPoint(1, iPT[tmp - 1]);
                    points.p2 = calcPoint(2, iPT[tmp]);

                    Triangle tmpTriangle = new Triangle();
                    tmpTriangle.next = null;
                    tmpTriangle.prev = null;

                    // aproximate Z to the greatest of 3 triangle corners
                    tmpTriangle.z = Math.max(getZValues().value[iPT[tmp]],
                                             Math.max(getZValues().value[iPT[tmp -
                            1]],
                            getZValues().value[iPT[tmp - 2]]));

                    // calculate XY screen positions of corners
                    tmpTriangle.p[0] = chart.getGraphics3D().calc3DPoint(points.
                            p0);
                    tmpTriangle.p[1] = chart.getGraphics3D().calc3DPoint(points.
                            p1);
                    tmpTriangle.p[2] = chart.getGraphics3D().calc3DPoint(points.
                            p2);
                    tmpTriangle.color = colors[0];

                    addByZ(tmpTriangle);
                }

                // draw all triangles
                Triangle tri = triangles;
                while (tri != null) {
                    g.getBrush().setColor(tri.color);
                    g.polygon(tri.p);
                    tri = tri.next;
                }
            } else { // draw all triangles, do not hide
                for (int t = 1; t <= numTriangles; t++) {
                    int tmp = 3 * t;
                    points.p0 = calcPoint(0, iPT[tmp - 2]);
                    points.p1 = calcPoint(1, iPT[tmp - 1]);
                    points.p2 = calcPoint(2, iPT[tmp]);
                    g.getBrush().setColor(colors[0]);
                    g.triangle(points);
                }
            }
        }

        if ((border != null) && (border.getVisible())) {
            g.setPen(border);

            // draw border
            for (int t = 1; t <= numLines; t++) {
                points.p0 = calcPoint(0, iPL[3 * t - 2]);
                g.moveTo(points.p0);
                points.p1 = calcPoint(1, iPL[3 * t - 1]);
                g.lineTo(points.p1);
            }
        }
    }
}

