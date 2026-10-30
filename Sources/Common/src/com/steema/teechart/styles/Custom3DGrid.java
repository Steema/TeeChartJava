package com.steema.teechart.styles;

import java.util.ArrayList;

import com.steema.teechart.IBaseChart;
import com.steema.teechart.misc.Utils;

/**
 *
 * <p>Title: Custom3DGrid class</p>
 *
 * <p>Description: Custom3DGridSeries.</p>
 *
 * <p>Copyright (c) 2005-2019 by Steema Software SL. All Rights
 * Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 *
 */
public class Custom3DGrid extends Custom3DPalette {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected PCellsRow gridIndex = new PCellsRow();
    protected int iNumXValues = 10;
    protected int iNumZValues = 10;

    private boolean irregularGrid;

    /* max 2000 x 2000 cells */
    private static final int MAXALLOWEDCELLS = 2000;

    /**/
    protected boolean iInGallery;
    protected int valueIndex0;
    protected int valueIndex1;
    protected int valueIndex2;
    protected int valueIndex3;
    protected int iNextXCell;
    protected int iNextZCell;

    protected int FXStartIndex, FZStartIndex;

    protected final class CellsRow extends ArrayList {
        public CellsRow() {
            super(MAXALLOWEDCELLS);
        }

        public int getCells(int index) {
            return ((Integer)super.get(index)).intValue();
        }

        public void setCells(int index, int value) {
            super.set(index, new Integer(value));
        }
    }


    protected final class PCellsRow extends ArrayList {
        public PCellsRow() {
            super();
        }

        public CellsRow getPCells(int index) {
            return (CellsRow)super.get(index);
        }

        public void setPCells(int index, CellsRow value) {
            super.set(index, value);
        }

        public void setCapacity(int max) {
            ensureCapacity(max);
        }
    }


    public Custom3DGrid() {
        this((IBaseChart)null);

    }

    public Custom3DGrid(IBaseChart c) {
        super(c);

        // prefill array
        gridIndex.setCapacity(MAXALLOWEDCELLS);
        for (int t = 0; t < MAXALLOWEDCELLS; t++) {
            gridIndex.add(null);
        }
    }

    private YCalculator yCalculator;

    public void setYCalculator(YCalculator calculator) {
        this.yCalculator = calculator;
    }

    public void removeYCalculator() {
        this.yCalculator = null;
    }

    protected double doGetYValue(int x, int z) {
        double value = 0;

        if (yCalculator != null) {
            value = yCalculator.calculate(this, x, z);
        }
        return value;
    }

    /**
     * Deletes all Series values.
     */
    public void clear() {
        super.clear();
        vxValues.setOrder(ValueListOrder.NONE);
        clearGridIndex();
    }

    private void clearGridIndex() {
        for (int t = 0; t < MAXALLOWEDCELLS; t++) {
            gridIndex.set(t, null);
        }
    }

    /**
     * Determine if X and Z values are equi-distant or not.<br>
     * Default value: false
     *
     * @return boolean
     */
    public boolean getIrregularGrid() {
        return irregularGrid;
    }

    /**
     * Determines if X and Z values are equi-distant or not.<br>
     * Default value: false
     *
     * <p>Example:
     * <pre><font face="Courier" size="4">
     * series = new com.steema.teechart.styles.Surface(myChart.getChart());
     * series.getMarks().setVisible(false);
     * series.setIrregularGrid(true);          // <---------- VERY IMPORTANT !!!
     * series.getVertAxis().setMinMax(-2,2);   // axis scale for Y values
     * series.setUseColorRange(false);
     * series.setUsePalette(true);
     * series.setPaletteStyle(PaletteStyle.STRONG);
     * </font></pre></p>
     *
     * @param value boolean
     */
    public void setIrregularGrid(boolean value) {
        irregularGrid = setBooleanProperty(irregularGrid, value);
    }

    protected boolean existFourGridIndex(int x, int z) {
        if ((gridIndex.get(x) != null) && (gridIndex.get(x + iNextXCell) != null)) {
            valueIndex0 = gridIndex.getPCells(x).getCells(z);
            if (valueIndex0 > -1) {
                valueIndex1 = gridIndex.getPCells(x + iNextXCell).getCells(z);
                if (valueIndex1 > -1) {
                    valueIndex2 = gridIndex.getPCells(x +
                            iNextXCell).getCells(z + iNextZCell);
                    if (valueIndex2 > -1) {
                        valueIndex3 = gridIndex.getPCells(x).getCells(z +
                                iNextZCell);
                        return valueIndex3 > -1;
                    }
                }
            }
        }
        return false;
    }
    
    protected boolean backFaced()
    {
      boolean result;
      result = chart.getAxes().getDepth().getInverted();
      if (chart.getAspect().getRotation() > 90 
              && chart.getAspect().getRotation() < 270)
        result = !result;
      return result;
    }

    protected boolean isValidSeriesSource(ISeries value) {
        return value instanceof Custom3DGrid;
    }

    protected void addSampleValues(int numValues) {
        if (numValues > 0) {
            boolean oldGallery = iInGallery;
            iInGallery = true;

            try {
                int tmp = Math.min(MAXALLOWEDCELLS, numValues);
                createValues(tmp, tmp);
            } finally {
                iInGallery = oldGallery;
            }
        }
    }

    /**
     * Makes a Surface grid of NumX by NumZ dimensions.<br>
     * For each grid point, the OnGetYValue event is called to allow the user
     * to supply the corresponding Y value.
     *
     * @param numX int
     * @param numZ int
     */
    public void createValues(int numX, int numZ) {
        if (canCreateValues()) {
            iNumXValues = numX;
            iNumZValues = numZ;
            // Capacity=numX*numZ;  speed optimization?

            beginUpdate();
            clear();
            for (int z = 1; z <= numZ; z++) {
                for (int x = 1; x <= numX; x++) {
                    add(x, getXZValue(x, z), z);
                }
            }

            endUpdate();

            createDefaultPalette(iPaletteSteps);
        }
    }

    protected boolean canCreateValues() {
        return canAddRandomPoints() || iInGallery || (yCalculator != null);
    }
    
    

    private double getXZValue(int x, int z) {
        if (yCalculator != null) {
            return yCalculator.calculate(this, x, z);
        } else
        if (canAddRandomPoints() || iInGallery)
        {
            // default sample random surface value formula
            return 0.5 * Utils.pow(Math.cos(x / (iNumXValues * 0.2)), 2) +
                    Utils.pow(Math.cos(z / (iNumZValues * 0.2)), 2) -
                    Math.cos(z / (iNumZValues * 0.5));
        } else {
            return 0;
        }
    }

    /**
     * A two dimension array property for the easy access to series cell values.
     *
     * @param x int
     * @param z int
     * @return int
     */
    public int getIndex(int x, int z) {
        return ((gridIndex.getPCells(x) != null)) ?
                gridIndex.getPCells(x).getCells(z) : -1;
    }

    /**
     * A two dimension array property for the easy access to series cell values.
     *
     * @param x int
     * @param z int
     * @param index int
     */
    public void setIndex(int x, int z, int index) {
        if ((x >= 0) && (x < MAXALLOWEDCELLS) && (z >= 0) &&
            (z < MAXALLOWEDCELLS)) {
            internalSetGridIndex(x, z, index);
        }
    }

    //future use
    private void initGridIndex(int xCount, int zCount)
    {
        for (int x = 0; x <= xCount; ++x)
        {
          if (gridIndex.get(x) == null) {
            gridIndex.set(x, new CellsRow());

            for (int z = 0; z <= zCount; ++z)
                gridIndex.getPCells(x).add(new Integer( -1));
          }
        }
    }

    private void internalSetGridIndex(int x, int z, int value) {
        if (gridIndex.get(x) == null) {
            gridIndex.set(x, new CellsRow());
            for (int t = 0; t < MAXALLOWEDCELLS; t++) {
                gridIndex.getPCells(x).add(new Integer( -1));
            }
        }
        gridIndex.getPCells(x).setCells(z, value);
    }

    public void doBeforeDrawChart() {
        super.doBeforeDrawChart();
        if (getCount() > 0) {
            fillGridIndex();
        }
    }

    final class XZCount {
        int xCount;
        int zCount;
        public XZCount(int x, int z) {
            xCount = x;
            zCount = z;
        }
    }
    
 //    
    protected void addValues(ArrayList source)
    {
      //if (source.get(0).getClass() == Custom3DGrid.class)
      if (source.get(0).getClass() == Contour.class)
      {
        Custom3DGrid series = (Custom3DGrid)source.get(0);
        iNumXValues = series.iNumXValues;
        iNumZValues = series.iNumZValues;
      }
      super.addValues(source);

      fillGridIndex();
      invalidate();
    }


    public void fillGridIndex() {
        double minX = getXValues().getMinimum();
        double minZ = getZValues().getMinimum();

        XZCount xzCount =  irregularGrid ?
            fillIrregularGrid(minX, minZ) :
            fillRegularGrid(minX, minZ);

        if (xzCount.xCount != iNumXValues) {
            iNumXValues = xzCount.xCount;
        }

        if (xzCount.zCount != iNumZValues) {
            iNumZValues = xzCount.zCount;
        }
    }
    
    private int SearchSorted(int aCount, double[] values, double aValue)
    {
      int result = -1;
      int L = 0;
      int H = aCount - 1;

      while (L <= H)
      {
        result = L + H >> 1;
        if (values[result] < aValue)
        {
          L = result + 1;
        }
        else
        {
          if (values[result] == aValue)
          {
            return result;
          }
          H = result - 1;
        }
      }
      return -1;
    }

    private void setGridIndex(int Index, int XCount, int ZCount, double[] XVals, double[] ZVals)
    {
      int tmpX = SearchSorted(XCount, XVals, getXValues().getValue(Index));
      if (tmpX != -1)
      {
        int tmpZ = SearchSorted(ZCount, ZVals, getZValues().getValue(Index));
        if (tmpZ != -1)
        {
          gridIndex.getPCells(tmpX + 1).setCells(tmpZ + 1,Index);
          //this[tmpX + 1, tmpZ + 1] = Index;
        }
      }
    }

    private XZCount fillIrregularGrid(double minX, double minZ) {
        double[] xVals = new double[MAXALLOWEDCELLS];
        double[] zVals = new double[MAXALLOWEDCELLS];

        int xCount = 1;
        xVals[0] = minX;
        int zCount = 1;
        zVals[0] = minZ;

        for (int t = 0; t < getCount(); t++) {
            xCount = searchValue(xCount, xVals, getXValues().getValue(t));
            zCount = searchValue(zCount, zVals, getZValues().getValue(t));
        }

        sortValues(xCount, xVals);
        sortValues(zCount, zVals);

        /* use sorted xvals && zvals to index Mandatory ValueList in grid*/
        for (int t = 0; t < getCount(); t++) {
            internalSetGridIndex(valuePosition(xCount, xVals, getXValues().getValue(t)),
                                 valuePosition(zCount, zVals, getZValues().getValue(t)),
                                 t);
        }

        return new XZCount(xCount, zCount);
    }

    static private int searchValue(int aCount, double[] values, double aValue) {
        int t = 0;
        while (t < aCount) {
            if (values[t] == aValue) {
                break;
            } else {
                t++;
                if (t == aCount) {
                    values[t] = aValue;
                    aCount++;
                }
            }
        }

        return aCount;
    }

    static private void sortValues(int aCount, double[] values) {
        for (int t = 1; t < aCount - 1; t++) /*min already at 0*/
                {
            double tmpMin = values[t];
            int tmpIndex = t;

            for (int tt = t + 1; tt < aCount; tt++) /*get minimum */
                    {
                if (values[tt] < tmpMin) {
                    tmpMin = values[tt];
                    tmpIndex = tt;
                }
                if (tmpIndex != t) {
                    values[tmpIndex] = values[t];
                    values[t] = tmpMin;
                }
            }
        }
    }

    static private int valuePosition(int aCount, double[] values, double aValue) {
        int tmpResult = 0;

        while ((aValue != values[tmpResult]) && (tmpResult < aCount)) {
            tmpResult++;
        }

        tmpResult++;

        return tmpResult;
    }

    private XZCount fillRegularGrid(double minX, double minZ) {
        int xCount = (1 + Utils.round(getXValues().getMaximum() - minX));
        int zCount = (1 + Utils.round(getZValues().getMaximum() - minZ));

        for (int t = 0; t < getCount(); t++) {
            internalSetGridIndex( (1 + Utils.round(getXValues().getValue(t) - minX)),
                                  (1 + Utils.round(getZValues().getValue(t) - minZ)),
                                 t);
        }

        return new XZCount(xCount, zCount);
    }

    protected int numSampleValues() {
        return iNumXValues;
    }


    /**
     * Determines the Surface's horizontal size in number of points.<br>
     * A Surface is a grid of NumXValues x NumZValues dimensions.
     * Changing NumXValues forces the Surface to recreate all values. <br>
     * Default value: 10
     *
     * @return int
     */
    public int getNumXValues() {
        return iNumXValues;
    }

    /**
     * Determines the Surface's horizontal size in number of points.<br>
     * Default value: 10
     *
     * @param value int
     */
    public void setNumXValues(int value) {
        if (iNumXValues != value) {
            iNumXValues = value;
            reCreateValues();
        }
    }

    /**
     * Determines the Surface's depth size in number of points.<br>
     * A Surface is a grid of NumXValues x NumZValues dimensions.
     * Changing NumZValues forces the Surface to recreate all values. <br>
     * Default value: 10
     *
     * @return int
     */
    public int getNumZValues() {
        return iNumZValues;
    }

    /**
     * Determines the Surface's depth size in number of points.<br>
     * Default value: 10
     *

     * @param value int
     */
    public void setNumZValues(int value) {
        if (iNumZValues != value) {
            iNumZValues = value;
            reCreateValues();
        }
    }

    public void reCreateValues() {
        createValues(iNumXValues, iNumZValues);
    }

    public interface YCalculator {
        public double calculate(ISeries series, int x, int z);
    }
}

