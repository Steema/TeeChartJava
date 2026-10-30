package com.steema.teechart.styles;

import com.steema.teechart.editors.gallery.Gallery;
import com.steema.teechart.languages.Language;

import com.steema.teechart.drawing.ChartBrush;
import com.steema.teechart.drawing.ChartPen;
import com.steema.teechart.IBaseChart;
import com.steema.teechart.drawing.IGraphics3D;
import com.steema.teechart.drawing.Color;
import com.steema.teechart.drawing.Graphics3D;
import com.steema.teechart.drawing.Point;
import com.steema.teechart.Rectangle;

/**
 *
 * <p>Title: Surface class</p>
 *
 * <p>Description: Surface 3D Series.</p>
 *
 * <p>Example:
 * <pre><font face="Courier" size="4">
 * surfaceSeries = new com.steema.teechart.styles.Surface(myChart.getChart());
 * surfaceSeries.setPaletteStyle(PaletteStyle.STRONG);
 * surfaceSeries.getSideBrush().setColor(Color.WHITE);
 * surfaceSeries.getSideBrush().setStyle(null);
 * surfaceSeries.setUseColorRange(false);
 * surfaceSeries.setUsePalette(true);
 * </font></pre></p>
 *
 * <p>Copyright (c) 2005-2019 by Steema Software SL. All Rights Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 *
 */
public class Surface extends Custom3DGrid {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public final class CellsOrientation {

        public int InitX;
        public int EndX;
        public int IncX;
        public int InitZ;
        public int EndZ;
        public int IncZ;

        public CellsOrientation() {
        }
    }
    private boolean dotFrame;
    private ChartBrush sideBrush;
    private boolean smoothPalette;
    private boolean bWaterFall;
    private ChartPen waterLines;
    private boolean wireFrame;
    private boolean hideCells = false;
    private boolean tmpBackRange;
    private boolean tmpBackCompare;
    private int tmpYOrigin;
    protected ChartPen sideLines;
    transient protected Point[] points = new Point[4];
    transient private boolean sameBrush;

    public void createSubGallery(Gallery addSubChart) {
        super.createSubGallery(addSubChart);
        addSubChart.createSubChart(Language.getString("WireFrame"));
        addSubChart.createSubChart(Language.getString("DotFrame"));
        addSubChart.createSubChart(Language.getString("Sides"));
        addSubChart.createSubChart(Language.getString("NoBorder"));
    }

    /**
     * Gets descriptive text.
     *
     * @return String
     */
    public String getDescription() {
        return Language.getString("GallerySurface");
    }

    public Surface(IBaseChart c) {
        super(c);
        iNextXCell = -1;
        iNextZCell = -1;
    }

    public Surface() {
        this((IBaseChart) null);
    }
    
    public void assign(Series source) {
        if (source instanceof Surface) {
            Surface tmp = (Surface)source;
            tmp.setChart(((Surface)source).getChart());
            tmp.setDotFrame(((Surface)source).getDotFrame());
            tmp.setHideCells(((Surface)source).getHideCells());
            tmp.setSideLines(((Surface)source).getSideLines());
            tmp.setSmoothPalette(((Surface)source).getSmoothPalette());
            tmp.setWaterFall(((Surface)source).getWaterFall());
            tmp.setWireFrame(((Surface)source).getWireFrame());
        }
        super.assign(source);
    }     

    /**
     * Determine the cell Colors of a Surface Series.<br>
     * When set to true, each cell is filled using a color calculated as the
     * average of each cell 4 corner colors. When set to false, only one corner
     * color is used. <br>
     * Default value: false
     *
     * @return boolean
     */
    public boolean getSmoothPalette() {
        return smoothPalette;
    }

    /**
     * Determine the cell Colors of a Surface Series.<br>
     * Default value: false
     *
     * <p>Example:
     * <pre><font face="Courier" size="4">
     * series.setSmoothPalette(true);
     * </font></pre></p>
     *
     * @param value boolean
     */
    public void setSmoothPalette(boolean value) {
        smoothPalette = setBooleanProperty(smoothPalette, value);
    }

    protected Object readResolve() {
        points = new Point[4];
        return super.readResolve();
    }

    public void setChart(IBaseChart c) {
        super.setChart(c);
        if (sideBrush != null) {
            sideBrush.setChart(c);
        }
        if (waterLines != null) {
            waterLines.setChart(c);
        }
    }

    public void setSubGallery(int index) {
        switch (index) {
            case 2:
                setWireFrame(true);
                break;
            case 3:
                setDotFrame(true);
                break;
            case 4:
                getSideBrush().setVisible(true);
                break;
            case 5:
                getPen().setVisible(false);
                break;
            default:
                super.setSubGallery(index);
        }
    }

    /**
     * Determines the Brush to fill the sides of a Surface Series.
     *
     * @return ChartBrush
     */
    public ChartBrush getSideBrush() {
        if (sideBrush == null) {
            sideBrush = new ChartBrush(chart, false);
        }
        return sideBrush;
    }

    public ChartPen getSideLines() {
        if (sideLines == null) {
            sideLines = new ChartPen(chart, Color.WHITE, false);
        }
        return sideLines;
    }

    public void setSideLines(ChartPen value) {
        sideLines = value;
    }

    /**
     * Sets Pen to draw valuelines.
     *
     * @return ChartPen
     */
    public ChartPen getWaterLines() {
        if (waterLines == null) {
            waterLines = new ChartPen(chart, Color.BLACK);
        }
        return waterLines;
    }

    /**
     * Enables/disables the display as a waterfall.<br>
     * Default value: false
     *
     * @return boolean
     */
    public boolean getWaterFall() {
        return bWaterFall;
    }

    public void setWaterFall(boolean value) {
        bWaterFall = setBooleanProperty(bWaterFall, value);
    }

    /**
     * Shows Surface polygons as grid or wire frame when true.<br>
     * If Surface.Pen.Visible is True (default), Surface points are displayed
     * as a "grid" or "wireframe", with lines connecting them. <br>
     * Default value: false
     *
     * @return boolean
     */
    public boolean getWireFrame() {
        return wireFrame;
    }

    /**
     * Shows Surface polygons as grid or wire frame when true.<br>
     * If Surface.Pen.Visible is True (default), Surface points are displayed
     * as a "grid" or "wireframe", with lines connecting them. <br>
     * Default value: false
     *
     * <p>Example:
     * <pre><font face="Courier" size="4">
     * series = new com.steema.teechart.styles.Surface(myChart.getChart());
     * series.setWireFrame(true);
     * series.setUsePalette(true);
     * series.setUseColorRange(false);
     * series.getPen().setWidth(1);
     * series.fillSampleValues(10);
     * </font></pre></p>
     *
     * @param value boolean
     */
    public void setWireFrame(boolean value) {
        if (value) {
            getPen().setVisible(true);
            dotFrame = false;
        }
        wireFrame = setBooleanProperty(wireFrame, value);
    }

    protected boolean shouldDrawSides() {
        return ((getSideBrush() != null) && (getSideBrush().getVisible())) || ((sideLines != null) && (sideLines.getVisible()));
    }

    private boolean inRangeRotation() {
        tmpBackRange = (chart.getAspect().getRotation() > 150) && (chart.getAspect().getRotation() < 210);
        return tmpBackRange || ((chart.getAspect().getRotation() < 30) || (chart.getAspect().getRotation() > 330));
    }

    public boolean getHideCells() {
        return hideCells;

    }

    public void setHideCells(boolean value) {
        hideCells = setBooleanProperty(hideCells, value);
    }

    /**
     * Sets SurfaceSeries as a grid of dots (pixels).<br>
     * It's very similar to the WireFrame mode except that no lines connect
     * the Surface's points. <br>
     * Default value: false
     *
     * @return boolean
     */
    public boolean getDotFrame() {
        return dotFrame;
    }

    /**
     * Sets SurfaceSeries as a grid of dots (pixels).<br>
     * Default value: false
     *
     * @param value boolean
     */
    public void setDotFrame(boolean value) {
        if (value) {
            getPen().setVisible(true);
            wireFrame = false;
        }
        dotFrame = setBooleanProperty(dotFrame, value);
    }

    private Point calcPointPos(int index) {
        return new Point(getHorizAxis().calcXPosValue(vxValues.value[index]),
                getVertAxis().calcYPosValue(vyValues.value[index]));
    }

    protected void draw() {

        boolean tmpSides = false;

        if (getCount() > 0) {
            prepareCanvas();

            tmpSides = shouldDrawSides();

            if (chart.getGraphics3D().getSupportsFullRotation()) {
                fastDraw();
            } else {
                if (tmpSides) {
                    drawSides(true);
                }

                if (hideCells && inRangeRotation()) {
                    drawAllSorted();
                } else {
                    drawAllCells();
                }

                //drawCells();
            }

            if (tmpSides) {
                drawSides(true);
            }
        }
    }

    private void preparePen(IGraphics3D g) {
        if ((!getPen().getVisible()) && (!wireFrame) && (!dotFrame)) {
            g.getPen().setVisible(false);
        } else {
            g.setPen(getPen());
        }
    }

    public void prepareForGallery(boolean isEnabled) {
        super.prepareForGallery(isEnabled);
        iInGallery = true;
        createValues(10, 10);
    }

    protected void prepareLegendCanvas(IGraphics3D g, int valueIndex,
            Color backColor,
            ChartBrush aBrush) {
        super.prepareLegendCanvas(g, valueIndex, backColor, aBrush);
        preparePen(g);
    }

    private void prepareCanvas() {
        IGraphics3D g = chart.getGraphics3D();

        preparePen(g);

        g.setBrush(getBrush());
        g.getBrush().setColor(getColor());

        sameBrush = (((!bUseColorRange) && (!bUsePalette))
                || (g.getBrush().getImage() != null));
        if (wireFrame || dotFrame) {
            g.getBrush().setVisible(false);
        }
    }

    static private void fastDraw() {
        /*
        //MM pending
        
        TeeCanvasSurfaceStyle tmpStyle;
        if ( wireFrame ) tmpStyle=TeeCanvasSurfaceStyle.Wire;
        else if ( dotFrame ) tmpStyle=TeeCanvasSurfaceStyle.Dot;
        else tmpStyle=TeeCanvasSurfaceStyle.Solid;
        Chart.getGraphics3D().Surface3D(tmpStyle,sameBrush,numXValues,
        numZValues,FastCalcPoints);
         */
    }

    private void drawAllCells() {
        int tmpXInit;
        int tmpXEnd;
        boolean tmpPosInit;
        int tmpX;
        int tmpZ = 0;

        CellsOrientation tmpO = cellsOrientation();

        if (perspectiveCorrection()) {
            tmpXInit = tmpO.InitX;
            tmpXEnd = tmpO.EndX;
            tmpX = tmpXInit;
            tmpPosInit = true;

            do {
                tmpZ = tmpO.InitZ;
                while (tmpZ != tmpO.EndZ) {
                    drawCell(tmpX, tmpZ);
                    tmpZ += tmpO.IncZ;
                }

                if (tmpPosInit) {
                    tmpXEnd -= tmpO.IncX;
                    tmpX = tmpXEnd;
                } else {
                    tmpXInit += tmpO.IncX;
                    tmpX = tmpXInit;
                }

                tmpPosInit = !tmpPosInit;
            } while (tmpXInit != tmpXEnd);
        } else {
            while (tmpO.InitX != tmpO.EndX) {
                tmpZ = tmpO.InitZ;
                while (tmpZ != tmpO.EndZ) {
                    drawCell(tmpO.InitX, tmpZ);
                    tmpZ += tmpO.IncZ;
                }
                tmpO.InitX += tmpO.IncX;
            }
        }
    }

    protected Point[] updateArray(int count, final Point[] points) {
        final Point[] tmpArr = new Point[count];

        if ((points == null) || (points.length == 0)) {
            return tmpArr;
        }

        //System.arraycopy(points, 0, tmpArr, 0, Math.min(count, points.length));

        int loc = 0;
        for (int i = 0; i < ((count < points.length) ? count : points.length); i++) {
            tmpArr[i] = points[loc];
            loc++;
        }

        return tmpArr;
    }

    private void drawAllSorted() {
        int tmp = 0, tmpZ;
        int tmpCount = (iNumXValues - 1) * (iNumZValues - 1);

        //cells = (Point[])Utils.SetLength(Cells, tmpCount, typeof(Point));
        Point[] cells = null;
        cells = updateArray(tmpCount, cells);

        try {
            CellsOrientation tmpO = cellsOrientation();

            while (tmpO.InitX != tmpO.EndX) {
                tmpZ = tmpO.InitZ;

                while (tmpZ != tmpO.EndZ) {
                    if (existFourGridIndex(tmpO.InitX, tmpZ)) {
                        cells[tmp] = new Point(tmpO.InitX, tmpZ);
                    } else {
                        cells[tmp] = new Point(-1, -1);
                    }

                    tmpZ += tmpO.IncZ;
                    tmp++;
                }

                tmpO.InitX += tmpO.IncX;
            }

            if (chart.getAxes().getDepth().getInverted()) {
                tmpBackCompare = !tmpBackRange;
            } else {
                tmpBackCompare = tmpBackRange;
            }

            sortCells(0, tmpCount - 1, cells);

            for (int t = 0; t < tmpCount; t++) {
                drawCell(cells[t].x, cells[t].y);
            }
        } finally {
            cells = null;
        }
    }

    private int compare(int a, int b, final Point[] cells) {
        int result;

        if ((cells[a].x == -1) || (cells[b].x == -1)) {
            result = 0;
        } else {
            final ValueList zValues = getZValues();
            final double z0 = zValues.getValue(getIndex(cells[a].x, cells[a].y));
            final double z1 = zValues.getValue(getIndex(cells[b].x, cells[b].y));

            if (z0 < z1) {
                result = 1;
            } else if (z0 > z1) {
                result = -1;
            } else {
                result = 0;
            }

            if (tmpBackCompare) {
                result = -result;
            }

        }

        return result;
    }

    private void sortCells(int l, int r, Point[] cells) {
        int i = l;
        int j = r;
        int x = (i + j) >> 1;

        while (i < j) {
            while (compare(i, x, cells) < 0) {
                ++i;
            }
            while (compare(x, j, cells) < 0) {
                --j;
            }

            if (i < j) {
                final Point tmp = cells[i];
                cells[i] = cells[j];
                cells[j] = tmp;

                if (i == x) {
                    x = j;
                } else if (j == x) {
                    x = i;
                }
            }

            if (i <= j) {
                ++i;
                --j;
            }
        }

        if (l < j) {
            sortCells(l, j, cells);
        }
        if (i < r) {
            sortCells(i, r, cells);
        }

    }

    private void drawCells() {
        int tmp = bWaterFall ? 0 : 1;

        if (chart.getAxes().getDepth().getInverted()) {
            iNextZCell = 1;
            if (!drawValuesForward()) {
                iNextXCell = 1;
                for (int x = iNumXValues - 1; x >= 1; x--) {
                    for (int z = 1; z <= iNumZValues - tmp; z++) {
                        drawCell(x, z);
                    }
                }
            } else {
                iNextXCell = -1;
                for (int x = 2; x <= iNumXValues; x++) {
                    for (int z = 1; z <= iNumZValues - tmp; z++) {
                        drawCell(x, z);
                    }
                }
            }
        } else {
            iNextZCell = -1;
            if (!drawValuesForward()) {
                iNextXCell = 1;
                for (int x = iNumXValues - 1; x >= 1; x--) {
                    for (int z = iNumZValues; z >= 1 + tmp; z--) {
                        drawCell(x, z);
                    }
                }
            } else {
                iNextXCell = -1;
                for (int x = 2; x <= iNumXValues; x++) {
                    for (int z = iNumZValues; z >= 1 + tmp; z--) {
                        drawCell(x, z);
                    }
                }
            }
        }

    }

    private boolean perspectiveCorrection() {
        boolean result = (!chart.getGraphics3D().getSupportsFullRotation()) && (chart.getAspect().getPerspective() > 0);

        if (result) {
            final int tmp = chart.getAspect().getRotation() % 360;
            result = (tmp == 360) || (tmp == 180) || (tmp == 0);
        }

        return result;
    }

    private SidePoint calcOnePoint(int tmpRow, int t, int tmpYOrigin) {
        int tmpIndex = getIndex(tmpRow, t);

        SidePoint p = new SidePoint();

        p.p0 = calcPointPos(tmpIndex);
        p.p1 = new Point(p.p0.x, tmpYOrigin);
        p.z = calcZPos(tmpIndex);

        return p;
    }

    private int calcOnePoint(int index, Point p0, Point p1) {
        int result = 0;
        if (index != -1) {
            p0 = calcPointPos(index);
            p1.x = p0.x;
            p1.y = tmpYOrigin;
            result = calcZPos(index);
        }
        return result;
    }

    private void drawSideCell(int a, int b, int c, int d,
            int tmpYOrigin, IGraphics3D g) {

        SidePoint z0 = calcOnePoint(a, b, tmpYOrigin);
        SidePoint z1 = calcOnePoint(c, d, tmpYOrigin);

        Point[] p = new Point[4];

        p[0] = g.calc3DPoint(z0.p0, z0.z);
        p[1] = g.calc3DPoint(z0.p1, z0.z);
        p[2] = g.calc3DPoint(z1.p1, z1.z);
        p[3] = g.calc3DPoint(z1.p0, z1.z);

        if (Graphics3D.cull(p)) {
            g.polygon(p);
        }
    }

    public CellsOrientation cellsOrientation() {
        CellsOrientation result = new CellsOrientation();
        int tmp = getWaterFall() ? 0 : 1;
        int tmpNumZValues = Math.min(iNumZValues, gridIndex.size());
        int tmpNumXValues = Math.min(iNumXValues, gridIndex.size());

        if (backFaced()) {
            iNextZCell = 1;
            result.InitZ = 1;
            result.EndZ = tmpNumZValues - tmp + 1;
            result.IncZ = 1;
        } else {
            iNextZCell = -1;
            result.InitZ = tmpNumZValues;
            result.EndZ = 1 + tmp - 1;
            result.IncZ = -1;
        }

        if (drawValuesForward()) {
            iNextXCell = -1;
            result.InitX = 2;
            result.EndX = tmpNumXValues + 1;
            result.IncX = 1;
        } else {
            iNextXCell = 1;
            result.InitX = tmpNumXValues - 1;
            result.EndX = 0;
            result.IncX = -1;
        }
        return result;
    }

    private void drawSides(boolean beforeCells) {

        int tmpRow;
        boolean tmpLeft, tmpFront, tmpRight;

        IGraphics3D g = chart.getGraphics3D();
        g.setBrush(sideBrush);
        g.setPen(sideLines);

        tmpYOrigin = calcYPosValue(getVertAxis().getInverted()
                ? vyValues.getMaximum()
                : vyValues.getMinimum());

        tmpLeft = chart.drawLeftWallFirst();
        tmpRight = chart.drawRightWallAfter();

        if ((tmpLeft && !beforeCells) || (!tmpLeft && beforeCells)) {
            if (getHorizAxis().getInverted()) {
                tmpRow = iNumXValues;
            } else {
                tmpRow = 1;
            }

            drawZSide(tmpRow);
        }

        if ((tmpRight && !beforeCells) || (!tmpRight && beforeCells)) {
            if (getHorizAxis().getInverted()) {
                tmpRow = 1;
            } else {
                tmpRow = iNumXValues;
            }

            drawZSide(tmpRow);
        }

        tmpFront = !drawFrontSideFirst(endZ);

        if ((tmpFront && beforeCells) || (!tmpFront && !beforeCells)) {
            if (chart.getAxes().getDepth().getInverted()) {
                tmpRow = 1;
            } else {
                tmpRow = iNumZValues;
            }
            drawXSide(tmpRow);
        }

        tmpFront = !drawFrontSideFirst(0);

        if ((tmpFront && !beforeCells) || (!tmpFront && beforeCells)) {
            if (chart.getAxes().getDepth().getInverted()) {
                tmpRow = iNumZValues;
            } else {
                tmpRow = 1;
            }
            drawXSide(tmpRow);

            //g.Pen.Style = DashStyle.Solid;

            SidePoint tmpZ = calcOnePoint(iNumXValues, tmpRow, tmpYOrigin);
            g.verticalLine(tmpZ.p0.x, tmpZ.p0.y, tmpYOrigin, tmpZ.z);
        }
    }

    private boolean drawFrontSideFirst(int ZPos) {
        Point[] P = new Point[4];
        IGraphics3D g = chart.getGraphics3D();
        Rectangle rect = chart.getChartRect();

        g.calc3DPos(P[0], rect.getRight(), rect.getTop(), ZPos);
        int tmpBottom = rect.getBottom() + chart.getWalls().calcWallSize(chart.getAxes().getBottom());
        g.calc3DPos(P[1], rect.getRight(), tmpBottom, ZPos);
        g.calc3DPos(P[2], rect.getLeft(), tmpBottom, ZPos);

        return !Graphics3D.cull(P);
    }

    private void drawXSide(int tmpRow) {
        SidePoint z0, z1;

        for (int t = 2; t <= iNumXValues; t++) {
            z0 = calcOnePoint(t, tmpRow, tmpYOrigin);
            z1 = calcOnePoint(t - 1, tmpRow, tmpYOrigin);

            drawSidePortion(z0, z1);
        }
    }

    protected void drawSidePortion(SidePoint z0, SidePoint z1) {
        Point[] P = new Point[4];

        P[0] = z0.p0;
        P[1] = z0.p1;
        P[2] = z1.p1;
        P[3] = z1.p0;

        chart.getGraphics3D().planeFour3D(z0.z, z1.z, P);
    }

    private final class SidePoint {

        int z;
        Point p0, p1;
    }

    private void drawZSide(int tmpRow) {
        SidePoint z0, z1;

        for (int t = iNumZValues; t >= 2; t--) {
            z0 = calcOnePoint(tmpRow, t, tmpYOrigin);
            z1 = calcOnePoint(tmpRow, t - 1, tmpYOrigin);

            drawSidePortion(z0, z1);
        }
    }

    protected boolean fourGridIndex(int x, int z) {
        if (existFourGridIndex(x, z)) {
            points[0] = calcPointPos(valueIndex0);
            points[1] = calcPointPos(valueIndex1);
            points[2] = calcPointPos(valueIndex2);
            points[3] = calcPointPos(valueIndex3);
            return true;
        } else {
            return false;
        }
    }

    private void drawTheCell(int z0, int z1) {
        IGraphics3D g = chart.getGraphics3D();

        if (bWaterFall) {
            int tmp = getVertAxis().iEndPos;

            if (!wireFrame) {
                g.getPen().setVisible(false);
                g.plane(points[0], points[1],
                        new Point(points[1].x, tmp),
                        new Point(points[0].x, tmp),
                        z0);
            }

            g.setPen(getPen());
            g.line(points[0], points[1], z0);

            if ((waterLines != null) && (waterLines.getVisible())) {
                g.setPen(waterLines);
                g.verticalLine(points[0].x, points[0].y, tmp, z0);
                g.verticalLine(points[1].x, points[1].y, tmp, z0);
            }
        } else if (dotFrame) {
            g.setPixel(points[0].x, points[0].y, z0,
                    getValueColor(valueIndex0));
            g.setPixel(points[1].x, points[1].y, z0,
                    getValueColor(valueIndex1));
            g.setPixel(points[2].x, points[2].y, z1,
                    getValueColor(valueIndex2));
            g.setPixel(points[3].x, points[3].y, z1,
                    getValueColor(valueIndex3));
        } else {
            g.planeFour3D(z0, z1, points);
        }
    }

    public void drawCell(int x, int z) {
        int z1 = 0;
        boolean tmp = false;

        if (bWaterFall) {
            valueIndex0 = getIndex(x, z);
            if (valueIndex0 > -1) {
                valueIndex1 = getIndex(x + iNextXCell, z);
                if (valueIndex1 > -1) {
                    points[0] = calcPointPos(valueIndex0);
                    points[1] = calcPointPos(valueIndex1);
                    tmp = true;
                }
            }
        } else if (fourGridIndex(x, z)) {
            z1 = calcZPos(valueIndex2);
            tmp = true;
        }

        if (tmp) {
            Color tmpColor = getValueColor(valueIndex0);
            if (!tmpColor.isNull()) {
                int z0 = calcZPos(valueIndex0);
                if (!sameBrush) {
                    if (smoothPalette) {
                        tmpColor = getValueColorValue((vyValues.value[
                              valueIndex0]
                                + vyValues.value[valueIndex1]
                                + vyValues.value[valueIndex2]
                                + vyValues.value[valueIndex3]) * 0.25);
                    }

                    if (wireFrame) {
                        if (getPen().getColor().getAlpha() < 255) {
                            tmpColor = Color.fromArgb(getPen().getColor().
                                    getAlpha(),
                                    tmpColor);
                        }
                        chart.getGraphics3D().getPen().setColor(tmpColor);
                    } else {
                        if (getBrush().getColor().getAlpha() < 255) {
                            tmpColor = Color.fromArgb(getBrush().getColor().
                                    getAlpha(),
                                    tmpColor);
                        }
                        chart.getGraphics3D().getBrush().setColor(tmpColor);
                    }
                } else {
                    if (wireFrame) {
                        chart.getGraphics3D().getBrush().setVisible(false);
                        if (getPen().getColor().getAlpha() < 255) {
                            tmpColor = Color.fromArgb(getPen().getColor().
                                    getAlpha(),
                                    tmpColor);
                        }
                        chart.getGraphics3D().getPen().setColor(tmpColor);
                    }
                }

                drawTheCell(z0, z1);
            }
        }
    }
}
