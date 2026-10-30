package com.steema.teechart.styles;

import com.steema.teechart.editors.gallery.Gallery;
import com.steema.teechart.languages.Language;

import com.steema.teechart.Rectangle;
import com.steema.teechart.drawing.ChartBrush;
import com.steema.teechart.drawing.Color;
import com.steema.teechart.drawing.IGraphics3D;
import com.steema.teechart.drawing.Image;
import com.steema.teechart.IBaseChart;
import com.steema.teechart.axis.Axis;

/**
 *
 * <p>Title: ColorGrid class</p>
 *
 * <p>Description: The ColorGrid series displays a grid of color cells.</p>
 *
 * <p>Example:
 * <pre><font face="Courier" size="4">
 * series = new ColorGrid(myChart.getChart());
 * series.getPen().setColor(Color.WHITE);
 * series.setUsePalette(true);
 * series.setPaletteStyle(PaletteStyle.STRONG);
 * eries.setUseColorRange(false);s
 * series.fillSampleValues(20);
 * </font></pre></p>
 *
 * <p>Copyright (c) 2005-2019 by Steema Software SL. All Rights Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 *
 */
public class ColorGrid extends Custom3DGrid {

    /**
	 * 
	 */
    private static final long serialVersionUID = 1L;
    private boolean centered;
    transient private int tmpDec;
    transient private Image bbitmap;
    transient private double xStep = 1;
    transient private double zStep = 1;
    transient private int FXGridEvery;
    transient private double FXRegularStep;
    transient private boolean FXRegularSteps;
    transient private int FZGridEvery;
    transient private double FZRegularStep;
    transient private boolean FZRegularSteps;
    transient private boolean FSmoothBitmap = false;

    public void createSubGallery(Gallery addSubChart) {
        super.createSubGallery(addSubChart);
        addSubChart.createSubChart(Language.getString("NoGrid"));
    }

    public void setSubGallery(int index) {
        if (index == 2) {
            getPen().setVisible(false);
        } else {
            super.setSubGallery(index);
        }
    }
    
    @Override
    public void assign(Series source) {
            super.assign(source);
            if(source instanceof ColorGrid)
            {
                this.centered=((ColorGrid)source).centered;
            }
    } 

    /**
     * Gets descriptive text
     *
     * @return String
     */
    public String getDescription() {
        return Language.getString("GalleryColorGrid");
    }

    public ColorGrid(IBaseChart c) {
        super(c);
        getMarks().getCallout().setLength(0);
    }

    public ColorGrid() {
        this((IBaseChart) null);
    }

    public double calcMinValue(double Value) {
        return getCenteredPoints() ? Value - 0.5 : Value;
    }

    private double calcMaxValue(double Value) {
        double result;
        if (getCenteredPoints()) {
            result = Value + 0.5;
            if (getIrregularGrid()) {
                result -= 1;
            }
        } else {
            if (getIrregularGrid()) {
                result = Value;
            } else {
                result = Value + 1;
            }
        }

        return result;
    }

    public int calcYPos(int index) {
        int result = calcYPosValue(getZValues().value[index]);
        if (!getCenteredPoints()) {
            result -= calcYSizeValue(0.5);
        }
        return result;
    }

    public int calcXPos(int index) {
        int result = calcXPosValue(getXValues().value[index]);
        if (!getCenteredPoints()) {
            result += calcXSizeValue(0.5);
        }
        return result;
    }

    public int clicked(int X, int Y) {
        int i;
        double tmpDecAmount;
        int Result = -1;
        int ZIndex = -1;
        double XPos = getHorizAxis().calcPosPoint(X);
        double YPos = getVertAxis().calcPosPoint(Y);
        boolean isIrregular = getIrregularGrid() && !(this.FXRegularSteps && this.FZRegularSteps);

        if (getCenteredPoints()) {
            tmpDecAmount = 0.5;
        } else {
            tmpDecAmount = 0.0;
        }
        if (!isIrregular) {
            double YDif = (YPos + tmpDecAmount) - getZValues().getMinimum();
            if (YDif >= 0f) {
                i = (int) (YDif);
            } else {
                i = -1;
            }
            if ((i >= 0) && (i < getNumZValues())) {
                ZIndex = i;
            }
            if (ZIndex != -1) {
                double XDif = (XPos + tmpDecAmount) - getXValues().getMinimum();
                if (XDif >= 0f) {
                    i = (int) (XDif);
                } else {
                    i = -1;
                }
                if ((i >= 0) && (i < getNumXValues())) {
                    Result = getIndex(i + 1, ZIndex + 1);
                }
            }
            return Result;
        }
        if (getXValues().getCount() > 0) {
            for (i = 0; i < getNumZValues(); i++) {
                if ((((getZValues().getValue(i * getNumXValues()) - tmpDecAmount) <= YPos)
                        && ((getZValues().getValue((i + 1) * getNumXValues()) - tmpDecAmount) > YPos))
                        || (((i == (getNumZValues() - 1)) && FZRegularSteps)
                        && (((getZValues().getValue(i * getNumXValues()) - tmpDecAmount) + this.FXRegularStep) >= YPos))) {
                    ZIndex = i;
                    break;
                }

            }
            if (ZIndex != -1) {
                for (i = 0; i < getNumXValues(); i++) {
                    if ((((getXValues().getValue(i) - tmpDecAmount) <= XPos)
                            && ((getXValues().getValue(i + 1) - tmpDecAmount) > XPos))
                            || (((i == (getNumXValues() - 1)) && FXRegularSteps)
                            && (((getXValues().getValue(i * getNumXValues() - 1) - tmpDecAmount) + FXRegularStep) >= XPos))) {
                        return ((ZIndex * getNumXValues()) + i);
                    }
                }
            }
        }
        return Result;
    }
    private ChartBrush cBrush;

    private ChartBrush getCellBrush() {
        if (cBrush == null) {
            cBrush = new ChartBrush(chart);
        }
        return cBrush;
    }

    private void setCellBrush(ChartBrush value) {
        cBrush = value;
    }

    /**
     * Controls how "X" and "Z" values will be considered.<br>
     * When CenteredPoints is true, X and Z values determine the center point
     * of each grid cell.<br>
     * When false, the X and Z values define the "corner" values of grid cells.
     * Thus, when CenteredPoints is false, there will be one less column and
     * one less row of grid cells.<br>
     * Default value: false
     *
     * @return boolean
     */
    public boolean getCenteredPoints() {
        return centered;
    }

    /**
     * Controls how "X" and "Z" values will be considered.<br>
     * Default value: false
     *
     * @see #getCenteredPoints
     * @param value boolean
     */
    public void setCenteredPoints(boolean value) {
        centered = setBooleanProperty(centered, value);
    }

    /**
     * Use this property to display a bitmap within a ColorGrid.<br>
     * Example<br>
     * This sample shows how to use the ColorGrid.Bitmap property:<br>
     * <br>
     * private void SetFlower() {<br>
     *  	InputStream ms = new InputStream();<br>
     * 		Image flower = pictureBox1.Image;<br>
     * 		flower.Save(ms, ImageFormat.Jpeg);<br>
     * 		Bitmap bm = new Bitmap(ms);<br>
     * 		colorGrid1.Bitmap = bm;<br>
     *
     * @return Image
     */
    public Image getImage() {
        return bbitmap;
    }

    //CDI ColorGrid Bitmap
    public void setImage(Image bitmap) {

        /** @todo WHY CLONE ? */
        bbitmap = bitmap;
        clear();

        setUseColorRange(false);
        setUsePalette(false);

        int w = bitmap.getWidth(null);
        int h = bitmap.getHeight(null);

        setNumXValues(w);
        setNumZValues(h);

        beginUpdate();

        for (int x = 0; x < w; ++x) {
            for (int z = 0; z < h; ++z) {
                int tmp = bbitmap.getRGB(x, z);
                Color tmpC = Color.fromArgb(tmp);
                add(x, tmp, h - (z + 1), "", tmpC);
            }
        }
        endUpdate();
    }

    private void drawGrid(final Rectangle r) {

        int tmp, tmpIndex, tmpGrid;
        double tmpValue;
        IGraphics3D g = chart.getGraphics3D();
        g.setPen(getPen());

        final double tmpDecAmount = centered ? 0.5 : 0;
//        double tmpDecZAmount = 0, tmpDecXAmount = 0;

//        if (getIrregularGrid()) {
//            tmpDecZAmount = centered ? zStep / 2 : 0;
//            tmpDecXAmount = centered ? xStep / 2 : 0;
//        } else {
//            tmpDecZAmount = centered ? 0.5 : 0;
//            tmpDecXAmount = centered ? 0.5 : 0;
//        }

//        for (int z = 2; z <= iNumZValues - tmpDec; z++) {
//            int tmp = getVertAxis().calcPosValue(vzValues.getValue(((CellsRow)
//                    gridIndex.get(1)).getCells(z)) - tmpDecZAmount);
//            if (chart.getAspect().getView3D()) {
//                g.horizontalLine(r.x, r.getRight(), tmp, middleZ);
//            } else {
//                g.horizontalLine(r.x, r.getRight(), tmp);
//            }
//        }
//
//        for (int x = 2; x <= iNumXValues - tmpDec; x++) {
//            int tmp = getHorizAxis().calcPosValue(vxValues.getValue(((CellsRow)
//                    gridIndex.get(x)).getCells(1)) - tmpDecXAmount);
//            if (chart.getAspect().getView3D()) {
//                g.verticalLine(tmp, r.y, r.getBottom(), startZ);
//            } else {
//                g.verticalLine(tmp, r.y, r.getBottom());
//            }
//        }
        for (int z = (int) Math.round(getZValues().getMinimum());
                z <= (getNumZValues() - tmpDec - 1) / zStep; z++) {
            tmpIndex = 1 + (int) (z * zStep);
            tmpGrid = gridIndex.getPCells(1).getCells(tmpIndex);
            if (tmpGrid < 0) {
                tmpGrid = gridIndex.getPCells(tmpIndex).getCells(tmpIndex);
                tmpGrid = tmpGrid > -1 ? tmpGrid : 0;
            }
            tmpValue = getZValues().getValue(tmpGrid) - tmpDecAmount;
            tmp = getVertAxis().calcPosValue(tmpValue);
            if (chart.getAspect().getView3D()) {
                g.horizontalLine(r.getLeft(), r.getRight(), tmp, getMiddleZ());
            } else {
                g.horizontalLine(r.getLeft(), r.getRight(), tmp);
            }
        }

        for (int x = (int) Math.round(getXValues().getMinimum()); x <= (getNumXValues() - tmpDec - 1) / xStep; x++) {
            tmpIndex = 1 + (int) (x * xStep);
            tmpGrid = gridIndex.getPCells(tmpIndex).getCells(1); //CDI TF02013680
            if (tmpGrid < 0) {
                tmpGrid = gridIndex.getPCells(tmpIndex).getCells(tmpIndex);
                tmpGrid = tmpGrid > -1 ? tmpGrid : 0;
            }
            tmpValue = getXValues().getValue(tmpGrid) - tmpDecAmount;
            tmp = getHorizAxis().calcPosValue(tmpValue);
            if (chart.getAspect().getView3D()) {
                g.verticalLine(tmp, r.getTop(), r.getBottom(), getMiddleZ());
            } else {
                g.verticalLine(tmp, r.getTop(), r.getBottom());
            }
        }
    }

//    private Image bitmap;
//     private void drawCellUsingBitmap(Rectangle tmpBounds, Rectangle R)
//     {
//      int newWidth, newHeight;
//      try
//      {
//        newWidth = Math.round(Math.abs(1 + tmpBounds.getRight() - tmpBounds.getLeft() - tmpDec));
//        newHeight = Math.round(Math.abs(1 + tmpBounds.getBottom() - tmpBounds.getTop() - tmpDec));
//
//        if (FXStartIndex == 0)
//        {
//          newWidth = (int)Math.ceil(getMaxXValue());
//          if (centered) tmpBounds.setSize(newWidth, tmpBounds.height);
//        }
//        if (FZStartIndex == 0)
//        {
//          newHeight = (int)Math.ceil(getMaxZValue());
//          if (centered) tmpBounds.setSize(tmpBounds.width, newHeight);
//        }
//
//        if (newWidth != 0 && newHeight != 0)
//        {
//          if (bitmap == null || bitmap.Width != newWidth || bitmap.Height != newHeight)
//          {
//            if (bitmap != null)
//              bitmap.Dispose();
//
//            bitmap = new Image(newWidth, newHeight);
//          }
//        }
//        else
//        {
//          if (bitmap == null)
//          {
//            bitmap = new Bitmap(1, 1);
//          }
//        }
//      }
//      catch
//      {
//        throw new TeeChartException(Texts.ColorGridSeriesRangeExceed);
//      }
//
//      bitmap = FillBitmap(tmpBounds, bitmap);
//
//      if (MinXValue() > 1)
//      {
//        tmpBounds.Width += Utils.Round(MinXValue());
//        tmpBounds.X += Utils.Round(MinXValue());
//      }
//      if (MinZValue() > 1)
//      {
//        tmpBounds.Height += Utils.Round(MinZValue());
//        tmpBounds.Y += Utils.Round(MinZValue());
//      }
//
//      R = CalcDestRectangle(tmpBounds);
//      Chart.Graphics3D.OrientRectangle(ref R); //CDI TW16014129
//      DrawBitmap(bitmap, ref R);
//    }
//    protected double getZStep() {
//        double FZVal, FZValPlus, FZdiff, FZlastdiff = 0;
//        boolean FReg = true;
//        for (int x = 1; x <= iNumZValues; x++) {
//            FZVal = vzValues.getValue(((CellsRow) gridIndex.get(1)).getCells(x));
//            if (x + 1 <= iNumXValues) {
//                FZValPlus = vzValues.getValue(((CellsRow) gridIndex.get(1)).getCells(x +
//                                              1));
//                FZdiff = FZValPlus - FZVal;
//                if (x == 1) {
//                    FZlastdiff = FZdiff;
//                }
//                if (FReg) {
//                    FReg = FReg = Math.abs(FZlastdiff - FZdiff) <= 1e-5;
//                } else {
//                    break;
//                }
//                FZlastdiff = FZdiff;
//            }
//        }
//
//        if (FReg) {
//            return FZlastdiff;
//        } else {
//            return 0;
//        }
//    }
//
//    protected double getXStep() {
//        double FXval, FXValPlus, FXdiff, FXlastdiff = 0;
//        boolean fReg = true;
//        for (int x = 1; x <= iNumXValues; x++) {
//            FXval = vxValues.getValue(((CellsRow) gridIndex.get(x)).getCells(1));
//            if (x + 1 <= iNumXValues) {
//                FXValPlus = vxValues.getValue(((CellsRow) gridIndex.get(x + 1)).getCells(
//                                              1));
//                FXdiff = FXValPlus - FXval;
//                if (x == 1) {
//                    FXlastdiff = FXdiff;
//                }
//                if (fReg) {
//                    fReg = Math.abs(FXlastdiff - FXdiff) <= 1e-5;
//                } else {
//                    break;
//                }
//                FXlastdiff = FXdiff;
//            }
//        }
//
//        if (fReg) {
//            return FXlastdiff;
//        } else {
//            return 0;
//        }
//    }
    protected void draw() {

//        if (getCount() > 0) {
//            if (getIrregularGrid()) {
//                zStep = getZStep();
//                xStep = getXStep();
//                zStep = (xStep == 0) ? 0 : zStep;
//                xStep = (zStep == 0) ? 0 : xStep;
//            }
//            tmpDec = 0; // (centered || (! IrregularGrid)) ? 0 : 1;

        Rectangle tmpBounds;

        int l, r, t, b;
        if (getCount() > 0) {
            // In non-centered mode, size of grid is one less (x-1, z-1)
            //tmpDec = (centered || (!IrregularGrid)) ? 0 : 1;
            tmpDec = 0;

            this.FXRegularSteps = false;
            this.FZRegularSteps = false;
            this.FXRegularStep = -1.0;
            this.FZRegularStep = -1.0;

            // v7 Calculate min and max bitmap bounds when axes are zoomed
            l = (int) getHorizAxis().calcPosPoint(getHorizAxis().iStartPos);
            r = (int) getHorizAxis().calcPosPoint(getHorizAxis().iEndPos);

            if (getHorizAxis().getInverted()) {
                l++;
            } else {
                r++;
            }

            t = (int) getVertAxis().calcPosPoint(getVertAxis().iStartPos);
            b = (int) getVertAxis().calcPosPoint(getVertAxis().iEndPos);
            if (getVertAxis().getInverted()) {
                b++;
            } else {
                t++;
            }

            //tmpBounds = new Rectangle(l,t,r-l,b-t); //   Rectangle.fromLTRB(l, t, r, b);
            tmpBounds = Rectangle.fromLTRB(l, t, r, b);
            // Restrict min and max bounds limits to 1..NumValues
            // Warning: tmpBounds is expressed in integers, starting at 1.

            tmpBounds.x -= Math.rint(getMinXValue());
            tmpBounds.y -= Math.rint(getMinZValue());

            if (FXStartIndex != 0 && tmpBounds.x < 1) {
                tmpBounds.x = 1;
            }
            if (FZStartIndex != 0 && tmpBounds.y < 1) {
                tmpBounds.y = 1;
            }

            if (tmpBounds.getRight() > getNumXValues()) {
                tmpBounds.width = getNumXValues() - tmpBounds.getLeft();
            }
            if (tmpBounds.getBottom() > getNumZValues()) {
                tmpBounds.height = getNumZValues() - tmpBounds.getTop();
            }

            Rectangle RR = new Rectangle(0, 0, 0, 0);

            if (getIrregularGrid()) {
                RR = drawCellByCell(tmpBounds);
            } else {
                // Number of rectangles is one less than number of points
                Image bitmap = new Image(iNumXValues - tmpDec,
                        iNumZValues - tmpDec,
                        chart.getParent().getControl());
                fillBitmap(bitmap);
                RR = drawBitmap(bitmap, RR);
            }

            if (getPen().getVisible()) {
                drawGrid(RR);
            }
        }
    }

    private Rectangle cellBounds(int x, int z) {
        int left, right, top, bottom, tmp;

        int tmpIndex = gridIndex.getPCells(x).getCells(z);

        left = getHorizAxis().calcPosValue(calcMinValue(getXValues().getValue(tmpIndex)));
        if (x < getNumXValues()) {
            tmpIndex = gridIndex.getPCells(x + 1).getCells(z);
            right = getHorizAxis().calcPosValue(calcMinValue(getXValues().getValue(tmpIndex)));// +1;
        } else if (getNumXValues() == 1) {
            right = getHorizAxis().calcPosValue(getXValues().getMaximum() + 1);// +1;
        } else {
            if (x > 1) {
                tmpIndex = gridIndex.getPCells(x - 1).getCells(z);
                tmp = left - getHorizAxis().calcPosValue(calcMinValue(getXValues().getValue(tmpIndex)));// -1;
            } else {
                tmp = 1;
            }
            if (getCenteredPoints()) {
                tmp++;
            }
            right = left + tmp;
        }

        tmpIndex = gridIndex.getPCells(x).getCells(z);
        top = getVertAxis().calcPosValue(calcMinValue(getZValues().getValue(tmpIndex)));
        if (z < getNumZValues()) {
            tmpIndex = gridIndex.getPCells(x).getCells(z + 1);
            bottom = getVertAxis().calcPosValue(calcMinValue(getZValues().getValue(tmpIndex)));// +1;
        } else if (getNumZValues() == 1) {
            bottom = getVertAxis().calcPosValue(getZValues().getMaximum() + 1);// +1;
        } else {
            if (z > 1) {
                tmpIndex = gridIndex.getPCells(x).getCells(z - 1);
                tmp = top - getVertAxis().calcPosValue(calcMinValue(getZValues().getValue(tmpIndex)));// -1;
            } else {
                tmp = 1;
            }
            bottom = top + tmp;
        }

        return Rectangle.fromLTRB(left, top, right, bottom);
    }

    private Rectangle calcDestRectangle(final Rectangle tmpBounds) {
        double IAxisSizeRange, MaxMin;
        int l, r, t, b;
        final Axis horizAxis = getHorizAxis();

        if (FXStartIndex == 0) {
            if ((horizAxis.getMaximum() - horizAxis.getMinimum()) == 0.0) {
                MaxMin = 1.0;
            } else {
                MaxMin = horizAxis.getMaximum() - horizAxis.getMinimum();
            }

            IAxisSizeRange = ((double) horizAxis.iAxisSize) / MaxMin;
            l = horizAxis.calcPosValue(getMinXValue());
            if (horizAxis.getInverted()) {
                r = (int) Math.round(l - (IAxisSizeRange * (getMaxXValue() - getMinXValue())));
            } else {
                r = (int) Math.round(l + (IAxisSizeRange * (getMaxXValue() - getMinXValue())));
            }
        } else {
            l = horizAxis.calcPosValue(Math.max(getMinXValue(), calcMinValue(tmpBounds.getLeft())));
            if (getIrregularGrid()) {
                r = horizAxis.calcPosValue(Math.max(getMaxXValue() + 1, calcMaxValue(tmpBounds.getRight())));
            } else {
                r = horizAxis.calcPosValue(Math.min(getMaxXValue(), calcMaxValue(tmpBounds.getRight())));
            }
        }

        final Axis vertAxis = getVertAxis();
        if (FXStartIndex == 0) {
            if ((vertAxis.getMaximum() - vertAxis.getMinimum()) == 0.0) {
                MaxMin = 1.0;
            } else {
                MaxMin = vertAxis.getMaximum() - vertAxis.getMinimum();
            }

            IAxisSizeRange = ((double) vertAxis.iAxisSize) / MaxMin;
            t = getVertAxis().calcPosValue(getMinZValue());
            if (vertAxis.getInverted()) {
                b = (int) Math.round(t + (IAxisSizeRange * (getMaxZValue() - getMinZValue())));
            } else {
                b = (int) Math.round(t - (IAxisSizeRange * (getMaxZValue() - getMinZValue())));
            }
        } else {
            t = vertAxis.calcPosValue(Math.max(getMinZValue(), calcMinValue(tmpBounds.getTop())));
            if (getIrregularGrid()) {
                b = vertAxis.calcPosValue(Math.max(getMaxZValue() + 1, calcMaxValue(tmpBounds.getBottom())));
            } else {
                b = vertAxis.calcPosValue(Math.min(getMaxZValue(), calcMaxValue(tmpBounds.getBottom())));
            }
        }

        return Rectangle.fromLTRB(l, t, r, b);
    }

    private Rectangle drawCellByCell(final Rectangle tmpBounds) {
        Rectangle tmpR = new Rectangle(0, 0, 0, 0);
        Color tmp;
        int tmpIndex;
        IGraphics3D g = chart.getGraphics3D();

        final boolean oldVisible = g.getPen().getVisible();
        g.getPen().setVisible(false);

        for (int z = 1; z <= getNumZValues(); z++) {
            for (int x = 1; x <= getNumXValues(); x++) {
                tmpIndex = gridIndex.getPCells(x).getCells(z);
                if (tmpIndex != -1) {
                    tmp = getValueColor(tmpIndex);
                    if (tmp != Color.EMPTY) {
                        tmpR = cellBounds(x, z);
                        getCellBrush().setColor(tmp);
                        g.setBrush(getCellBrush());
                        g.rectangle(tmpR, getMiddleZ());
                    }
                }
            }
        }

        g.getPen().setVisible(oldVisible);

        return calcDestRectangle(tmpBounds);
    }

    private Rectangle drawBitmap(final Image bitmap, Rectangle r) {
        Axis a = getHorizAxis();

        r.x = a.calcPosValue(getMinXValue());
        r.width = a.calcPosValue(getMaxXValue()) - r.x;

        a = getVertAxis();

        final int minZ = a.calcPosValue(getMinZValue());
        final int maxZ = a.calcPosValue(getMaxZValue());

        r.y = (minZ > maxZ) ? maxZ : minZ;
        r.height = Math.abs(maxZ - minZ);

        IGraphics3D g = chart.getGraphics3D();

        g.prepareDrawImage();
        
        if (chart.getAspect().getView3D()) {
            g.draw(g.calcRect3D(r, getMiddleZ()), bitmap, false, FSmoothBitmap);
        } else {
            g.draw(r, bitmap, false, FSmoothBitmap);
        }

        return r;
    }

    private void fillBitmap(final Image bitmap) {
        for (int z = 1; z <= iNumZValues - tmpDec; z++) {
            for (int x = 1; x <= iNumXValues - tmpDec; x++) {
                int tmpIndex = ((CellsRow) gridIndex.getPCells(x)).getCells(z);
                if (tmpIndex != -1) {
                    bitmap.setRGB(x - 1, z - 1, getValueColor(tmpIndex).getRGB());
                }
            }
        }
    }

    protected void drawMark(int valueIndex, final String st,
            final SeriesMarksPosition aPosition) {

        double tmpX = 0, tmpZ = 0;

        if (getIrregularGrid()) {
            tmpZ = centered ? 0 : zStep / 2;
            tmpX = centered ? 0 : xStep / 2;
        } else {
            tmpZ = centered ? 0 : 0.5;
            tmpX = centered ? 0 : 0.5;
        }

        aPosition.leftTop.y = getVertAxis().calcPosValue(getZValues().value[
                valueIndex] + tmpZ) - (aPosition.height / 2);
        aPosition.leftTop.x = getHorizAxis().calcPosValue(vxValues.value[
                valueIndex] + tmpX) - (aPosition.width / 2);
        marks.internalDraw(valueIndex, getValueColor(valueIndex), st,
                aPosition);
    }

    public void galleryChanged3D(boolean is3D) {
        super.galleryChanged3D(is3D);
        chart.getAspect().setView3D(false);
        chart.getAspect().setClipPoints(true);
    }

    private double calcXVal() {
        double incr = 0.0;
        if (!getIrregularGrid()) {
            return getXValues().getMaximum();
        }
        int zCycle = getNumZValues();
        int num = getNumZValues() - 1;
        int i = 1;
        if (num >= i) {
            num++;
            do {
                if (getXValues().getValue(i) != getXValues().getValue(i - 1)) {
                    zCycle = 1;
                    break;
                }
                i++;
            } while (i != num);
        }
        Axis horizAxis = getHorizAxis();
        if (getNumXValues() > 1) {
            incr = Math.abs((double) (getXValues().getValue(getNumXValues() - 1) * zCycle)
                    - getXValues().getValue((getNumXValues() - 2) * zCycle));
            FXRegularSteps = true;
            if (getNumXValues() > 2) {
                int num2 = getNumXValues() - 1;
                i = 2;
                if (num2 >= i) {
                    num2++;
                    do {
                        if (Math.abs((double) (Math.abs((double) (getXValues().getValue((getNumXValues() - i) * zCycle)
                                - getXValues().getValue((getNumXValues() - (i + 1)) * zCycle))) - incr)) > horizAxis.getMinAxisIncrement()) {
                            FXRegularSteps = false;
                            return getXValues().getMaximum();
                        }
                        FXRegularStep = incr;
                        i++;
                    } while (i != num2);
                }
            }
        } else if (getNumXValues() == 1) {
            incr = getXValues().getValue(0);
        }
        if (incr < horizAxis.getMinAxisIncrement()) {
            incr = 1.0;
            FXRegularStep = incr;
        }
        return (getXValues().getMaximum() + incr);
    }

    private double calcZVal() {
        double incr = 0.0;
        if (!getIrregularGrid()) {
            return getZValues().getMaximum();
        }
        Axis vertAxis = getVertAxis();
        if (getZValues().getCount() > 0) {
            if (getNumZValues() > 1) {
                incr = (int) Math.abs(getZValues().getValue(getNumZValues() - 1) - getZValues().getValue(getNumZValues() - 2));
                FZRegularSteps = true;
                if (getNumZValues() > 2) {
                    int num = getNumZValues() - 1;
                    int i = 2;
                    if (num >= i) {
                        num++;
                        do {
                            if (Math.abs((double) (Math.abs((double) (getZValues().getValue(getNumZValues() - i)
                                    - getZValues().getValue(getNumZValues() - (i + 1)))) - incr)) > vertAxis.getMinAxisIncrement()) {
                                FZRegularSteps = false;
                                return getZValues().getMaximum();
                            }
                            FZRegularStep = incr;
                            i++;
                        } while (i != num);
                    }
                }
            } else if (getNumZValues() == 1) {
                incr = getZValues().getValue(0);
            }
        }
        if (incr < vertAxis.getMinAxisIncrement()) {
            FZRegularStep = incr;
            incr = 1.0;
        }
        return (getZValues().getMaximum() + incr);
    }

    public double getMinXValue() {
        return calcMinValue(getXValues().getMinimum());
    }

    /**
     * The Maximum Value of the Series X Values List.
     *
     * @return double
     */
    public double getMaxXValue() {
        return calcMaxValue(calcXVal());
    }

    /**
     * The Maximum Value of the Series Y Values List.
     *
     * @return double
     */
    public double getMaxYValue() {
        return getMaxZValue();
    }

    /**
     * The Minimum Value of the Series Y Values List.
     *
     * @return double
     */
    public double getMinYValue() {
        return getMinZValue();
    }

    /**
     * The Maximum Value of the Series Z Values List.
     *
     * @return double
     */
    public double getMaxZValue() {
        return calcMaxValue(calcZVal());
    }

    /**
     * The Minimum Value of the Series Z Values List.
     *
     * @return double
     */
    public double getMinZValue() {
        return calcMinValue(getZValues().getMinimum());
    }
    
	/**
	 * Draws the cells in the ColorGrid smoothed or sharp.<br>
	 * Default value: false
	 * 
	 * @return boolean
	 */
    public boolean getSmoothBitmap() {
    	return FSmoothBitmap;
    }
    
	/**
	 * Draws the cells in the ColorGrid smoothed or sharp.<br>
	 * Default value: false
	 * 
	 * @param value
	 *            boolean
	 */
    public void setSmoothBitmap(boolean value) {
    	FSmoothBitmap = setBooleanProperty(FSmoothBitmap, value);
    }
}
