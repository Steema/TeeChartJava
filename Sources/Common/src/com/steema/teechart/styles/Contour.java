package com.steema.teechart.styles;

import java.text.DecimalFormat;
import com.steema.teechart.PointDouble;
import com.steema.teechart.languages.Language;

import com.steema.teechart.editors.gallery.Gallery;

import com.steema.teechart.drawing.Color;
import com.steema.teechart.drawing.Graphics3D;
import com.steema.teechart.drawing.Point;

import com.steema.teechart.Aspect;
import com.steema.teechart.TeeBase;
import com.steema.teechart.IBaseChart;
import com.steema.teechart.drawing.IGraphics3D;
import com.steema.teechart.axis.Axis;
import com.steema.teechart.misc.Enum;
import com.steema.teechart.misc.Utils;

import com.steema.teechart.drawing.ChartPen;
import com.steema.teechart.Rectangle; 
import com.steema.teechart.legend.LegendTextStyle;

/**
 *
 * <p>Title: Sides class</p>
 *
 * <p>Description: Reserved for future use.</p>
 *
 * <p>Copyright (c) 2005-2019 by Steema Software SL. All Rights Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 *
 */
final class Sides extends Enum {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Sides(int value) {
        super(value);
    }
}


/**
 *
 * <p>Title: Contour class</p>
 *
 * <p>Description: The Contour series calculates and displays "isolines"
 * from a custom array of XYZ points.</p>
 *
 * <p>Copyright (c) 2005-2019 by Steema Software SL. All Rights
 * Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 *
 */
public class Contour extends Custom3DGrid {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private ContourLevel legendLevel(int index)
    {
      return levels.getLevel(numLevels - index - 1); // Add LegenEvery*index when ready
    }

    public final class LevelProps {
        
    	double lValue;
        Color color;
        
        public LevelProps(LevelProps props) {
            this(props.lValue, props.color);
        }

        public LevelProps(double levelValue, Color color) {
            this.lValue = levelValue;
            this.color = color;
        }

        public void setColor(Color color) {
            this.color = color;
        }

        public void setLevelValue(double levelValue) {
            this.lValue = levelValue;
        }

        public Color getColor() {
            return this.color;
        }

        public double getLevelValue() {
            return this.lValue;
        }

    }

    public interface LevelResolver {
        LevelProps getLevel(int levelIndex, double value, Color color);
    }

    transient protected boolean iModifyingLevels;

    private boolean automaticLevels = true;
    private ContourLevels levels = new ContourLevels();
    private int numLevels = 10;
    private double yPosition;
    private boolean yPositionLevel;
    private ContourConstruction drawingalgorithm = ContourConstruction.SEGMENTS;
    private boolean fillLevels = false;
    private ContourSeriesMarks contourmarks;
    private String tmpSt;
    private SeriesPointer pointer = null;
    
    private ChartPen framepen = null;

    transient private ContourDraw cDraw;
    
    @Override
    public void assign(Series source) {
            super.assign(source);
            if(source instanceof Contour)
            {
                this.automaticLevels=((Contour)source).automaticLevels;
                this.levels=((Contour)source).levels;
                this.numLevels=((Contour)source).numLevels;
                this.yPosition=((Contour)source).yPosition;
                this.yPositionLevel=((Contour)source).yPositionLevel;
                this.drawingalgorithm=((Contour)source).drawingalgorithm;
                this.fillLevels=((Contour)source).fillLevels;
                this.contourmarks=((Contour)source).contourmarks;
            }
    }     
    
    /// <summary>
    /// Default contour levels line pen color.
    /// </summary>
    public Color LinesColor = Color.EMPTY;

    public void createSubGallery(Gallery addSubChart) {
        super.createSubGallery(addSubChart);
        addSubChart.createSubChart(Language.getString("Colors"));
        addSubChart.createSubChart(Language.getString("Positions"));
    }

    transient protected LevelResolver levelResolver;

    public void setContourLevelResolver(LevelResolver resolver) {
        this.levelResolver=resolver;
    }

    public void removeContourLevelResolver() {
        this.levelResolver = null;
    }
    
    /// <summary>
    /// Defines Series bounding frame characteristic.
    /// </summary>
    public ChartPen getFrame()
    {
      if (framepen == null) framepen = new ChartPen(chart, Color.BLACK, false);
      return framepen;
    }

    /**
     * Gets descriptive text.
     *
     * @return String
     */
    public String getDescription() {
        return Language.getString("GalleryContour");
    }


    public Contour(IBaseChart c) {
        super(c);
        //setColorEach(true);
        getMarks().setTransparent(true);
        getFrame().color=Color.BLACK;
        LinesColor = Color.BLACK;
        getFrame().setVisible(false);
        readResolve();
    }

    public Contour() {
        this((IBaseChart)null);
    }

    protected Object readResolve() {
        cDraw = new ContourDraw(this);
        return super.readResolve();
    }

    /**
     * The ContourSeries Automatic Levels.<br>
     * Default value: true
     *
     * @return boolean
     */
    public boolean getAutomaticLevels() {
        return automaticLevels;
    }

    /**
     * Sets ContourSeries Automatic Levels.<br>
     * Default value: true
     *
     * @param value boolean
     */
    public void setAutomaticLevels(boolean value) {
        automaticLevels = setBooleanProperty(automaticLevels, value);
        if (automaticLevels) clearAutoLevels();
    }

       private void clearAutoLevels()
    {
      iModifyingLevels = true;
      try
      {
        levels.clear();
      }
      finally
      {
        iModifyingLevels = false;
      }
    }
    
    /// <summary>
    /// Special characteristic for contour marks.
    /// </summary>
    public ContourSeriesMarks getContourMarks()
    {
      if (contourmarks == null) contourmarks = new ContourSeriesMarks(this);
        return contourmarks;
    }
    
    public void setContourMarks(ContourSeriesMarks value)
    { 
      contourmarks = value;
    }
    
    
        /**
     * The ContourSeries FillLevels.<br>
     * Default value: false
     *
     * @return boolean
     */
    public boolean getFillLevels() {
        return fillLevels;
    }
    
        /// <summary>
    /// Contour series Pointer characteristics.
    /// </summary>
    public SeriesPointer getPointer()
    {
      if (pointer == null)
      {
        pointer = new SeriesPointer(chart, this);
        pointer.getPen().setVisible(false);
        pointer.setVertSize(2);
        pointer.setHorizSize(2);
        pointer.setVisible(false);
      }
      return pointer;
    }

    /**
     * Sets ContourSeries FillLevels.<br>
     * Default value: false
     *
     * @param value boolean
     */
    public void setFillLevels(boolean value) {
        fillLevels = setBooleanProperty(fillLevels, value);
    }
    
    public ContourConstruction getDrawingalgorithm()
    {
      return drawingalgorithm;
    }
    
    public void setDrawingalgorithm(ContourConstruction value)
    {
      drawingalgorithm = value;
    }

    static final byte[][][] SIDES = { { {
                                    0, 0, 8}, {0, 2, 5}, {7, 6, 9}
    }, { {
            0, 3, 4}, {1, 3, 1}, {4, 3, 0}
    }, { {
            9, 6, 7}, {5, 2, 0}, {8, 0, 0}
    }
    };

    private int calcNumLevels() {
        if (getAutomaticLevels()) {
            return numLevels;
        } else {
            return getLevels().size();
        }
    }

    public void setSubGallery(int index) {
        if (index == 2) {
            setColorEach(true);
        } else
        if (index == 3) {
            setYPositionLevel(true);
        } else {
            super.setSubGallery(index);
        }
    }

    private void doSetNumLevels(int value) {
        numLevels = setIntegerProperty(numLevels, value);
        if (getAutomaticLevels()) {
            levels.clear();
            setAutomaticLevels(true);
        }
    }

 
    public String  getLegendString(int legendIndex, LegendTextStyle legendTextStyle)
    {
      return Double.toString(legendLevel(legendIndex).getUpToValue());
    }

    /**
     * Accesses ContourLevel characteristics by selecting Level index.
     *
     * @return ContourLevels
     */
    public ContourLevels getLevels() {
        return levels;
    }

    /**
     * Accesses ContourLevel characteristics by selecting Level index.
     *
     * @param value ContourLevels
     */
    public void setLevels(ContourLevels value) {
        levels = value;
    }

    /**
     * The number of 'isobar-like' levels for the ContourSeries.<br>
     * Default value: 10
     *
     * @return int
     */
    public int getNumLevels() {
        return numLevels;
    }

    /**
     * Sets the number of 'isobar-like' levels for the ContourSeries.<br>
     * Default value: 10
     *
     * @param value int
     */
    public void setNumLevels(int value) {
        doSetNumLevels(value);
    }

    /**
     * Sets the Y-Axis height of the Contour Series.<br>
     * When YPositionLevel is set to true, use it to set the Y-Axis height of
     * the Contour series.<br>
     * This may be useful for adding several Contour series to a Chart and
     * activating/deactivating layers.
     *
     * @return double
     */
    public double getYPosition() {
        return yPosition;
    }

    /**
     * Sets the Y-Axis height of the Contour Series.<br>
     *
     * @param value double
     */
    public void setYPosition(double value) {
        yPosition = setDoubleProperty(yPosition, value);
    }

    /**
     * Enables/disables YPosition to be set.<br>
     * Default value: false
     *
     * @return boolean
     */
    public boolean getYPositionLevel() {
        return yPositionLevel;
    }
        
    public Axis getZAxis() {
        if (chart != null)
          return chart.getAspect().getView3D() ? chart.getAxes().getDepth() : getVertAxis();
        else return getVertAxis();
    }

    /**
     * Enables YPosition to be set when true.<br>
     * Default value: false
     *
     * @param value boolean
     */
    public void setYPositionLevel(boolean value) {
        yPositionLevel = setBooleanProperty(yPositionLevel, value);
    }

    protected void addSampleValues(int numValues) {
        super.addSampleValues(numValues);
        yPosition = 0.5 * (vyValues.getMaximum() + vyValues.getMinimum());
    }

    public int getCountLegendItems() {
        return levels.size();
    }

    protected void draw() {
        if (getCount() > 0) {
            cDraw.draw();
        }
    }
    
   //  
    public void drawMarks()
    {
      if (!contourmarks.atSegments)
        super.drawMarks();
    }
    
  //   
    protected void drawMark(int valueIndex, String s, SeriesMarksPosition position)
    {
      if (!contourmarks.atSegments)
      {
        position.leftTop.x = calcXPos(valueIndex);
        position.leftTop.y = calcYPosValue(getZValues().getValue(valueIndex));
        super.drawMark(valueIndex, s, position);
      }
    }

    /**
     * The Maximum Value of the Series Y Values List.
     *
     * @return double
     */
    public double getMaxYValue() {
        return chart.getAspect().getView3D() ? super.getMaxYValue() :
                vzValues.getMaximum();
    }

    /**
     * The Minimum Value of the Series Y Values List.
     *
     * @return double
     */
    public double getMinYValue() {
        return chart.getAspect().getView3D() ? super.getMinYValue() :
                vzValues.getMinimum();
    }

    public void doBeforeDrawChart() {
        super.doBeforeDrawChart();
        if (getCount() > 0) {
            createAutoLevels();
        }
    }

    public void createAutoLevels() {
        /* calc Level values && Colors */
        if (automaticLevels && (numLevels > 0)) {
            /* 5.01 */
            iModifyingLevels = true;
            
            final boolean old = chart.getAutoRepaint();
            chart.setAutoRepaint(false);

            double tmp = getYValues().getRange() / Math.max(1.0, (double)numLevels - 1.0);
            levels.clear();

            for (int t = 0; t < numLevels; t++) {
                ContourLevel cL = new ContourLevel(this,t);
                levels.add(cL);

                cL.setUpToValue(vyValues.getMinimum() + tmp * t);
                cL.setColor(getColorEach() ? getValueColor(t) :
                            getValueColorValue(cL.getUpToValue()));
                if (levelResolver != null) {
                    LevelProps lp = levelResolver.getLevel(t, cL.getUpToValue(), cL.getColor());
                    cL.setUpToValue(lp.lValue);
                    cL.setColor(lp.color);
                }
            }

            iModifyingLevels = false;
            chart.setAutoRepaint(old);
        }
    }

    final class TempLevel {
        public double UpToValue;
        public Color Color;
        public int Count = 0;
        public int Allocated = 10;
        public LevelLine[] Line = new LevelLine[10];
        public ContourLevel Level;
        public Color LineColor;
        public com.steema.teechart.drawing.ChartPen pen;

        public TempLevel(double upTo, Color color) {
            super();
            UpToValue = upTo;
            Color = color;
        }
    }

    /**
     *
     * <p>Title: ContourDraw class</p>
     *
     * <p>Description: Composite of the Contour series class.</p>
     *
     * <p>Copyright (c) 2005-2019 by Steema Software SL. All Rights
     * Reserved.</p>
     *
     * <p>Company: Steema Software SL</p>
     *
     */
    public final class ContourDraw extends TeeBase {
    	
    	private static final long serialVersionUID = 1L;
    	
        private Contour cs;
        private int tmpNumLevels;
        private TempLevel tLevel;
        Point[] P;
        boolean tmpDrawMarks;
        int tmpY;
        double tol = 1.0E-4; //const
        double invtol = 1.0E+4; //const

        //segment drawing
        private double[] difY = new double[5];
        private double[] cellX = new double[5];
        private double[] cellZ = new double[5];
        
        //fast drawing
        int[] cellXi = new int[5];
        int[] cellZi = new int[5];
        
        private TempLevel[] iLevels;
        private Axis tmpZAxis;
        private ContourConstruction algo = ContourConstruction.SEGMENTS;

        public ContourDraw(Contour cSeries) {
            super(cSeries.chart);
            cs = cSeries;
        }
        
              /// <summary>
      /// Main drawing routine
      /// </summary>
      private void draw()
      {

         if (cs.getCount() > 0 && cs.getNumLevels() > 0)
          {
            /* cache number of levels (speed opt.) */
            tmpNumLevels = cs.getNumLevels();
            iLevels = new TempLevel[tmpNumLevels];
            
            ContourLevel cLevel = null;

            /* store cache of levels */
            for (int theLevel = 0; theLevel < tmpNumLevels; theLevel++) {
                cLevel = cs.getLevels().getLevel(theLevel);

                iLevels[theLevel] = new TempLevel(cLevel.getUpToValue(),
                        cLevel.getColor());
            }

            // Which algorithm is used ?
            algo = cs.drawingalgorithm;

            // Generate and draw everything
            prepareLevels();
            calculateLevels();
            drawLevelLines();
            if (cs.getFrame().getVisible())
              drawFrame();
          }
        }
      
      private void prepareLevels()
      {
        ContourLevel cLevel = null;
        /* set "Z" axis, depending on 2D || 3D */
        tmpZAxis = cs.getZAxis();
        // Store cache of levels
        for (int i = 0; i < tmpNumLevels; i++)
        {
          cLevel = cs.getLevels().getLevel(i);
          iLevels[i].Level = cLevel;
          iLevels[i].UpToValue = cLevel.getUpToValue();
          iLevels[i].Color = cLevel.internalColor();
          if (algo == ContourConstruction.SEGMENTS)
            iLevels[i].Level.segments.clear();
          else
          {
            iLevels[i].Count = 0;
            iLevels[i].Allocated = 0;
          }

          iLevels[i].pen = iLevels[i].Level.getInternalPen();

          if (!cs.getFillLevels()) iLevels[i].LineColor = iLevels[i].Color;
          else
          {
            iLevels[i].LineColor = cs.LinesColor;
            if (iLevels[i].LineColor == Color.EMPTY) iLevels[i].LineColor = iLevels[i].Color;
          }
        }
      }
      
      private void calculateLevels()
      {
        double diffMin;
        double diffMax;
        double tmp1;
        double tmp2;
        double tmp3;
        double tmp4;
        double tmpAvg;

        //if (cs.getCount() > 0) {
            /* prepare default params */
            cs.iNextXCell = 1;
            cs.iNextZCell = 1;

//            /* set "Z" axis, depending on 2D || 3D */
//            if (cs.chart.getAspect().getView3D()) {
//                tmpZAxis = cs.chart.getAxes().getDepth();
//            } else {
//                tmpZAxis = cs.getVertAxis();
//            }
//
//            /* cache number of levels (speed opt.) */
//            tmpNumLevels = cs.calcNumLevels();
//
//            iLevels = new TempLevel[tmpNumLevels];
//
//            ContourLevel cLevel = null;
//
//            /* store cache of levels */
//            for (int theLevel = 0; theLevel < tmpNumLevels; theLevel++) {
//                cLevel = cs.getLevels().getLevel(theLevel);
//
//                iLevels[theLevel] = new TempLevel(cLevel.getUpToValue(),
//                        cLevel.getColor());
//            }

            for (int z = 1; z < cs.iNumZValues; z++) {
                /* for (each cell */
                for (int x = 1; x < cs.iNumXValues; x++) {

                    if (cs.existFourGridIndex(x, z)) {

                        /* if xz values exist */
                        /* calc cell screen coordinates for Z */
                         if (algo == ContourConstruction.SEGMENTS)
                         {
                            cellZ[1] = cs.vzValues.getValue(cs.valueIndex0);
                            cellZ[3] = cs.vzValues.getValue(cs.valueIndex3);
                            cellZ[2] = cellZ[1];
                            cellZ[4] = cellZ[3];
                            cellZ[0] = (cellZ[1] + cellZ[3]) * 0.5; /* mid Z pos */

                            /* calc cell screen coordinates for X */
                            //Axis hAxis = cs.getHorizAxis();
                            cellX[1] = cs.vxValues.getValue(cs.valueIndex0);
                            cellX[2] = cs.vxValues.getValue(cs.valueIndex1);

                            cellX[3] = cellX[2];
                            cellX[4] = cellX[1];
                            cellX[0] = (cellX[1] + cellX[2]) * 0.5; /* mid X pos */
                         }
                         else
                         {
                          cellZi[1] = tmpZAxis.calcYPosValue(cs.vzValues.getValue(cs.valueIndex0));
                          cellZi[3] = tmpZAxis.calcYPosValue(cs.vzValues.getValue(cs.valueIndex3));
                          cellZi[2] = cellZi[1];
                          cellZi[4] = cellZi[3];
                          cellZi[0] = (cellZi[1] + cellZi[3]) / 2;  /* mid Z pos */

                          /* calc cell screen coordinates for X */
                          Axis hAxis = cs.getHorizAxis();
                          cellXi[1] = hAxis.calcXPosValue(cs.getXValues().getValue(cs.valueIndex0));
                          cellXi[2] = hAxis.calcXPosValue(cs.getXValues().getValue(cs.valueIndex1));

                          cellXi[3] = cellXi[2];
                          cellXi[4] = cellXi[1];
                          cellXi[0] = (cellXi[1] + cellXi[2]) / 2;  /* mid X pos */
                        }

                        /* get Y value for each grid cell corner */
                        ValueList vList = cs.getYValues();

                        tmp1 = vList.getValue(cs.valueIndex0);
                        tmp2 = vList.getValue(cs.valueIndex3);
                        tmp3 = vList.getValue(cs.valueIndex1);
                        tmp4 = vList.getValue(cs.valueIndex2);
                        tmpAvg = 0.25 * (tmp1 + tmp2 + tmp3 + tmp4); /* average Y value */

                        /* calc min and max Y value of the 4 cell corners */
                        diffMin = tmp1;
                        if (tmp2 < diffMin) {
                            diffMin = tmp2;
                        }
                        if (tmp3 < diffMin) {
                            diffMin = tmp3;
                        }
                        if (tmp4 < diffMin) {
                            diffMin = tmp4;
                        }

                        if (diffMin <=
                            iLevels[tmpNumLevels - 1].UpToValue) {
                            /* if Min inside all levels */
                            diffMax = tmp1;
                            if (tmp2 > diffMax) {
                                diffMax = tmp2;
                            }
                            if (tmp3 > diffMax) {
                                diffMax = tmp3;
                            }
                            if (tmp4 > diffMax) {
                                diffMax = tmp4;
                            }

//                            tmpAvg = 0.25 *
//                                     (tmp1 + tmp2 + tmp3 + tmp4);
                            /* average Y value */

                            /* if Max inside all levels */
                            if (diffMax >= iLevels[0].UpToValue) {
                                for (int theLevel = 0;
                                        theLevel < tmpNumLevels;
                                        theLevel++) {

                                    /* for each level */
                                    tLevel = iLevels[theLevel];

                                    if ((tLevel.UpToValue >=
                                         diffMin) &&
                                        (tLevel.UpToValue <=
                                         diffMax)) {

                                        /* if inside one level */
                                        /* calc corners dif Y to level Up value */
                                        difY[1] = tmp1 -
                                                tLevel.UpToValue;
                                        difY[2] = tmp3 -
                                                tLevel.UpToValue;
                                        difY[3] = tmp4 -
                                                tLevel.UpToValue;
                                        difY[4] = tmp2 -
                                                tLevel.UpToValue;

                                        /* set dif[0] to Y average */
                                        difY[0] = tmpAvg -
                                                tLevel.UpToValue;

                                        /* calculate all lines for one level in this XZ cell */
                                        calcLevel(theLevel);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
      
      private void calcPointsSegment(int m1, int m3, int side, int theLevel)
      {
        double x1 = 0;
        double z1 = 0;
        double x2 = 0;
        double z2 = 0;
        switch (side)
        {
          case 1:
            {
              x1 = cellX[m1]; z1 = cellZ[m1];
              x2 = cellX[0]; z2 = cellZ[0];
              break;
            }
          case 2:
            {
              x1 = cellX[0]; z1 = cellZ[0];
              x2 = cellX[m3]; z2 = cellZ[m3];
              break;
            }
          case 3:
            {
              x1 = cellX[m3]; z1 = cellZ[m3];
              x2 = cellX[m1]; z2 = cellZ[m1];
              break;
            }
          case 4:
            {
              x1 = cellX[m1]; z1 = cellZ[m1];
              PointDouble p=pointSect(0, m3);
              x2 = p.x;
              z2 = p.y;
              break;
            }
          case 5:
            {
              x1 = cellX[0]; z1 = cellZ[0];
              PointDouble p=pointSect(m3, m1);
              x2 = p.x;
              z2 = p.y;
              break;
            }
          case 6:
            {
              x1 = cellX[m3]; z1 = cellZ[m3];
              PointDouble p=pointSect(m1, 0);
              x2 = p.x;
              z2 = p.y;
              break;
            }
          case 7: 
            { 
              PointDouble p=pointSect(m1, 0); 
              x1 = p.x;
              z1 = p.y;
              p = pointSect(0, m3); 
              x2 = p.x;
              z2 = p.y;
              break; 
            }
          case 8: 
            { 
              PointDouble p=pointSect(0, m3); 
              x1 = p.x;
              z1 = p.y;
              p = pointSect(m3, m1);
              x2 = p.x;
              z2 = p.y;
              break; 
            }
          case 9: 
            { 
              PointDouble p=pointSect(m3, m1);
              x1 = p.x;
              z1 = p.y;
              PointDouble p2 = pointSect(m1, 0);
              x2 = p2.x;
              z2 = p2.y;
              break; 
            }
        }
        addSegmentLine(x1, z1, x2, z2, theLevel);
      }
      
      private Point pointSectInt(int p1, int p2) {

        /* calculate crossing XY point, return pixel coordinates */
        double tmp = difY[p2] - difY[p1];

        Point p = new Point();

        if (tmp != 0) {
            tmp = 1 / tmp;

            p.x = Utils.round((difY[p2] * cellXi[p1] -
                                    difY[p1] * cellXi[p2]) * tmp);
            p.y = Utils.round((difY[p2] * cellZi[p1] -
                                    difY[p1] * cellZi[p2]) * tmp);
        } else {
            p.x = Utils.round(cellXi[p2] - cellXi[p1]);
            p.y = Utils.round(cellZi[p2] - cellZi[p1]);
        }

        return p;
     }

      private PointDouble pointSect(int p1, int p2) {

        /* calculate crossing XY point, return pixel coordinates */
        double tmp = difY[p2] - difY[p1];

        PointDouble p = new PointDouble();

        if (tmp != 0) {
            tmp = 1 / tmp;

            p.x = (difY[p2] * cellX[p1] -
                   difY[p1] * cellX[p2]) * tmp;
            p.y = (difY[p2] * cellZ[p1] -
                   difY[p1] * cellZ[p2]) * tmp;
        } else {
            p.x = cellX[p2] - cellX[p1];
            p.y = cellZ[p2] - cellZ[p1];
        }

        return p;
      }
      
      private void calcLinePoints(int m1, int m3, int side,
                                  int theLevel) {
          TempLevel tL = iLevels[theLevel];

          //MM make blank line before
          if (tL.Count >= tL.Allocated) {
              tL.Allocated += 1000;
              tL.Line = new LevelLine[tL.Allocated];
          }

          if (tL.Line[tL.Count] == null) {
              tL.Line[tL.Count] = new LevelLine();
          }
          LevelLine lLine = tL.Line[tL.Count];

          switch (side) {
          case 1: {
              lLine.x1 = cellXi[m1];
              lLine.z1 = cellZi[m1];
              lLine.x2 = cellXi[0];
              lLine.z2 = cellZi[0];
              break;
          }
          case 2: {
              lLine.x1 = cellXi[0];
              lLine.z1 = cellZi[0];
              lLine.x2 = cellXi[m3];
              lLine.z2 = cellZi[m3];
              break;
          }
          case 3: {
              lLine.x1 = cellXi[m3];
              lLine.z1 = cellZi[m3];
              lLine.x2 = cellXi[m1];
              lLine.z2 = cellZi[m1];
              break;
          }
          case 4: {
              lLine.x1 = cellXi[m1];
              lLine.z1 = cellZi[m1];
              Point p = pointSectInt(0, m3);
              lLine.x2 = p.x;
              lLine.z2 = p.y;
              break;
          }
          case 5: {
              lLine.x1 = cellXi[0];
              lLine.z1 = cellZi[0];
              Point p = pointSectInt(m3, m1);
              lLine.x2 = p.x;
              lLine.z2 = p.y;
              break;
          }
          case 6: {
              lLine.x1 = cellXi[m3];
              lLine.z1 = cellZi[m3];
              Point p = pointSectInt(m1, 0);
              lLine.x2 = p.x;
              lLine.z2 = p.y;
              break;
          }
          case 7: {
              Point p = pointSectInt(m1, 0);
              lLine.x1 = p.x;
              lLine.z1 = p.y;

              Point p2 = pointSectInt(0, m3);
              lLine.x2 = p2.x;
              lLine.z2 = p2.y;
              break;
          }
          case 8: {
              Point p = pointSectInt(0, m3);
              lLine.x1 = p.x;
              lLine.z1 = p.y;

              Point p3 = pointSectInt(m3, m1);
              lLine.x2 = p3.x;
              lLine.z2 = p3.y;

              break;
          }
          case 9: {
              Point p = pointSectInt(m3, m1);
              lLine.x1 = p.x;
              lLine.z1 = p.y;

              Point p4 = pointSectInt(m1, 0);
              lLine.x2 = p4.x;
              lLine.z2 = p4.y;
              break;
          }
          default:
              break;
          }

          tL.Line[tL.Count] = lLine;
          tL.Count++; /* new line */
          iLevels[theLevel] = tL;
      }

      private void addSegmentLine(double x1, double z1, double x2, double z2, int theLevel)
      {
        boolean added = false;
        int dist;
        ContourLevel lvl = cs.levels.getLevel(theLevel);
        LevelSegment seg;

        // Find appropiate segment for point, if any
        for (int t = 0; t < lvl.segments.size(); t++)
        {
          seg = lvl.segments.getItem(t);
          dist = Utils.round(invtol * (Math.abs(x1 - seg.points[0].x) + Math.abs(z1 - seg.points[0].y)));
          if (dist == 0)
          {
            added = true;
            seg.count++;
            // Faster than SetLength + for loop
            seg.points = updateArray(seg.count,1,seg.points);

            seg.points[0] = new LevelPoint(x2,z2);
//            seg.points[0].x = x2;
//            seg.points[0].y = z2;

            if (lvl.segments.size() > 1) checkOtherSegments(t, 0, lvl);
            break;
          }
          else
          {
            dist = Utils.round(invtol * (Math.abs(x2 - seg.points[0].x) + Math.abs(z2 - seg.points[0].y)));
            if (dist == 0)
            {
              added = true;
              seg.count++;
              // Faster than SetLength + for loop
              seg.points = updateArray(seg.count,1, seg.points);

              seg.points[0] = new LevelPoint(x1,z1);

              if (lvl.segments.size() > 1) checkOtherSegments(t, 0, lvl);
              break;
            }
            else
            {
              dist = Utils.round(invtol * (Math.abs(x1 - seg.points[seg.count - 1].x) + Math.abs(z1 - seg.points[seg.count - 1].y)));
              if (dist == 0)
              {
                added = true;
                seg.count++;
                //seg.points = Utils.setLength(seg.Points, seg.Count, typeof(LevelPoint)) as LevelPoint[];
                seg.points = updateArray(seg.count,0,seg.points);

                seg.points[seg.count - 1] = new LevelPoint(x2,z2);
//                seg.points[seg.count - 1].x = x2;
//                seg.points[seg.count - 1].y = z2;

                if (lvl.segments.size() > 1) checkOtherSegments(t, seg.count - 1, lvl);
                break;
              }
              else
              {
                dist = Utils.round(invtol * (Math.abs(x2 - seg.points[seg.count - 1].x) + Math.abs(z2 - seg.points[seg.count - 1].y)));
                if (dist == 0)
                {
                  added = true;
                  seg.count++;
                  //seg.points = Utils.SetLength(seg.Points, seg.Count, typeof(LevelPoint)) as LevelPoint[];
                  seg.points = updateArray(seg.count,0,seg.points);
                  seg.points[seg.count - 1] = new LevelPoint(x1,z1);

                  if (lvl.segments.size() > 1) checkOtherSegments(t, seg.count - 1, lvl);
                  break;
                }
//                else
//                {
//                  dist = Utils.round(invtol * (Math.abs(x2 - seg.points[seg.count - 1].x) + Math.abs(z2 - seg.points[seg.count - 1].y)));
//                  if (dist == 0)
//                  {
//                    added = true;
//                    seg.count++;
//                    seg.points = updateArray(seg.count,0,seg.points);
//                    seg.points[seg.count - 1] = new LevelPoint(x1,z1);
// 
//                    if (lvl.segments.size() > 1) checkOtherSegments(t, seg.count - 1, lvl);
//                    break;
//                  }
//                }
              }
            }
          }

        }
        // Creates a new segment, initializes it with two points (x1z1 and x2z2)
        if (!added)
        {
//          Point[] pp;
//          pp = new Point[2];
//          pp[0].x = 5;
          seg = new LevelSegment();
          seg.count = 2;
          seg.points = new LevelPoint[2];
          seg.points[0]= new LevelPoint(x1,z1);
          //seg.points[0].setX(x1);
          //seg.points[0].y = z1;
          seg.points[1]= new LevelPoint(x2,z2);
          //seg.points[1].x = x2;
          //seg.points[1].y = z2;
          lvl.segments.add(seg);
        }
      }
      
      private LevelPoint[] updateArray(int count, int index, LevelPoint[] points)
      {
        LevelPoint[] tmpArr = new LevelPoint[count];
        int loc = 0;
        for (int i=index;i<(points.length+index);i++)        
        {
           tmpArr[i] = points[loc];
           loc++;
        }
        
        return tmpArr;
      }

      private void checkOtherSegments(int numsegment, int atpos, ContourLevel lvl)
      {
        LevelPoint p = lvl.segments.getItem(numsegment).points[atpos];
        int tmplen = lvl.segments.getItem(numsegment).count;

        for (int t = 0; t < lvl.segments.size(); t++)
          if (t != numsegment)
          {
            LevelSegment curseg = lvl.segments.getItem(t);
            int tmpnumpoints = curseg.count;
            LevelPoint tmpp = curseg.points[0];
            if ((Math.abs(p.x - tmpp.x) <= tol) && (Math.abs(p.y - tmpp.y) <= tol))
            {
              LevelSegment nl = lvl.segments.getItem(numsegment);
              nl.count = tmpnumpoints + tmplen - 1;
              //nl.Points = Utils.SetLength(nl.points, nl.Count, typeof(LevelPoint)) as LevelPoint[];
              // Faster than SetLength + for loop
              nl.points = updateArray(nl.count,0,nl.points);

              if (atpos != 0)
              {
                for (int tt = 1; tt < tmpnumpoints; tt++)
                  nl.points[tmplen + tt - 1] = curseg.points[tt];
              }
              else
              {
                for (int tt = tmplen - 1; tt >= 0; tt--)
                  nl.points[tt + tmpnumpoints - 1] = nl.points[tt];

                for (int tt = 0; tt < tmpnumpoints - 1; tt++)
                  nl.points[tt] = curseg.points[tmpnumpoints - 1 - tt];
              }

              deleteSegment(lvl, t);
              break;
            }
            else
            {
              tmpp = curseg.points[tmpnumpoints - 1];
              if ((Math.abs(p.x - tmpp.x) <= tol) && (Math.abs(p.y - tmpp.y) <= tol))
              {
                LevelSegment nl = lvl.segments.getItem(numsegment);
                nl.count = tmpnumpoints + tmplen - 1;
                //nl.points = Utils.SetLength(nl.points, nl.Count, typeof(LevelPoint)) as LevelPoint[];
                // Faster than SetLength + for loop
                nl.points = updateArray(nl.count,0,nl.points);

                if (atpos != 0)
                  for (int tt = 0; tt < tmpnumpoints - 1; tt++)
                    nl.points[tmplen + tt] = curseg.points[tmpnumpoints - 2 - tt];
                else
                {
                  for (int tt = tmplen - 1; tt >= 0; tt--)
                    nl.points[tt + tmpnumpoints - 1] = nl.points[tt];

                  for (int tt = 0; tt < tmpnumpoints - 1; tt++)
                    nl.points[tt] = curseg.points[tt];
                }

                deleteSegment(lvl, t);
                break;
              }
            }
          }
      }
      
      private boolean SamaValue(double a, double b, double epsilon)
        {
          if (epsilon == 0) epsilon = Math.max(Math.min(Math.abs(a), Math.abs(b)) * 1.0e-12, 1.0e-12);
          return (a > b) ? (a - b) <= epsilon : (b - a) <= epsilon;
        }
        
        private void drawSingleMark(double value, Color color, SeriesMarksPosition position)
        {
          for (int tmpindex = 0; tmpindex < cs.getCount(); tmpindex++)
            if (SamaValue(cs.getMandatory().value[tmpindex], value, 0))
            {
              P = new Point[1];
              P[0] = new Point(cs.getHorizAxis().calcXPosValue(cs.getXValues().getValue(tmpindex))
                           ,tmpZAxis.calcPosValue(cs.getZValues().getValue(tmpindex)));

              if (cs.getPointer().getVisible()) drawPointers(color, color);
              else
                if (cs.chart.getAspect().getView3D())
                  cs.chart.getGraphics3D().setPixel(P[0].x, tmpY, P[0].y, color);
                else
                  cs.chart.getGraphics3D().setPixel(P[0].x, P[0].y, 0, color);

              if (tmpDrawMarks) 
                drawLevelMark(P[0].x, P[0].y, 0, tmpSt, color, color, position);
              P = null;
            }
        }

        private void drawLevelLines() {
            //ContourLevel cLevel=null;
          TempLevel tL;
          IGraphics3D g = cs.chart.getGraphics3D();
          final Aspect a = cs.chart.getAspect();
          SeriesMarksPosition position = new SeriesMarksPosition();
          Point tmpSize;
          P = null;
          LevelLine lLine;

          tmpY = cs.getVertAxis().calcYPosValue(cs.yPosition);
          
          if (cs.getFillLevels()) fillLevels();
          
          cs.getMarks().getPositions().clear();

            /* for (each level, draw lines */
          for (int theLevel = 0; theLevel < tmpNumLevels; theLevel++) 
          {
            tL = iLevels[theLevel];
              
            /* calculate Y position of the Level Up value */
            if (cs.yPositionLevel)
              tmpY = cs.getVertAxis().calcYPosValue(tL.UpToValue);
            
            tmpDrawMarks = cs.getMarks().getVisible() & cs.getContourMarks().getAtSegments() & (theLevel % cs.getMarks().getDrawEvery() == 0);
            
            if (tmpDrawMarks)
            {
              // Get mark text:
            switch (cs.getMarks().getStyle().getValue())
            {
              case 10: tmpSt = cs.getTitle(); break;
              case 11: tmpSt = Integer.toString(theLevel); break;
              default: 
               tmpSt = (new DecimalFormat(getValueFormat())).format(tL.UpToValue);
               break;
              //tmpSt = new (DecimalFormat(getValueFormat())).format(tmpValue); 
              //Double.toString(tL.UpToValue); break;
            }
            // Get mark size:
            cs.calculateMarkPosition(cs.getMarks(), tmpSt, 0, 0, position);
            tmpSize = new Point(position.width, position.height);
            }
            else tmpSize = new Point(0, 0);

            g.setPen(cs.getPen());
            g.getPen().setColor(tL.Color);
            g.getPen().setVisible(tL.pen.getVisible());
            
            if (algo == ContourConstruction.SEGMENTS)
            {
            
            int tmp = tL.Level.segments.size() - 1;
            if (((theLevel == 0) || (theLevel == tmpNumLevels - 1)) && (tmp == -1) && (cs.getPointer().getVisible() || tmpDrawMarks))
              drawSingleMark(tL.UpToValue, tL.Color, position);

            for (int t = 0; t <= tmp; t++)
            {
              P = tL.Level.getSegmentPoints(t);

              if (tmpDrawMarks)
              {
                int tmptotwidth = tmpSize.x + (2 * cs.getContourMarks().getMargin());

                if (a.getView3D())
                  g.moveTo(P[0].x, tmpY, P[0].y);
                else
                  g.moveTo(P[0]);
                int tt = 1;
                int tmpmarks = 0;
                int tmpstart = 0;
                int tmplength = P.length;
                int tmpxx, tmpyy;
                double tmplen, tmpdist;
                double tmpangle;
                int tmpang;
                Point tmpC;
                while (tt < tmplength)
                {
                  tmpxx = P[tt].x - P[tmpstart].x;
                  tmpyy = P[tt].y - P[tmpstart].y;
                  tmplen = Math.sqrt(tmpxx * tmpxx + tmpyy * tmpyy);
                  if (tmplen > tmptotwidth)
                  {
                    tmpxx = P[tt].x - P[tt - 1].x;
                    tmpyy = P[tt].y - P[tt - 1].y;
                    tmpdist = Math.sqrt(tmpxx * tmpxx + tmpyy * tmpyy);

                    tmpmarks++;

                    if (tmpmarks % cs.getContourMarks().getDensity() == 0)
                    {
                      if (tmpdist > tmptotwidth)
                      {
                        for (int ttt = tmpstart + 1; ttt < tt; ttt++)
                          if (a.getView3D())
                            g.lineTo(P[ttt].x, tmpY, P[ttt].y);
                          else
                            g.lineTo(P[ttt].x, P[ttt].y);
                        tmpstart = tt - 1;
                        tmplen = tmpdist;
                      }


                      if (tmpstart == tt - 1)
                      {
                        tmpC = Graphics3D.pointAtDistance(P[tt], P[tt - 1], 
                                           Utils.round(0.5 * (tmpdist - tmptotwidth)));
                        if (a.getView3D())
                          g.lineTo(tmpC.x, tmpY, tmpC.y);
                        else
                          g.lineTo(tmpC.x, tmpC.y);
                      }

                      // TODO: Half tmpLen might not be the right center place if points are too curved
                      tmpC = Graphics3D.pointAtDistance(P[tt], P[tmpstart], 
                                           Utils.round(tmplen * 0.5));
                      tmpangle = Math.atan2(P[tt].x - P[tmpstart].x, P[tt].y - P[tmpstart].y) + 0.5 * Math.PI;
                      tmpang = Utils.round(tmpangle * 180.0 / Math.PI);

                      if (tmpang < 0) tmpang += 360;
                      if ((tmpang > 90) && (tmpang < 270))
                      {
                        tmpang += 180;
                        if (tmpang > 360) tmpang -= 360;
                      }

                      drawLevelMark(tmpC.x, tmpC.y + tmpSize.y / 2, tmpang, tmpSt, tL.Color, tL.LineColor, position);

                      if (tmpdist > tmptotwidth) tmpC = Graphics3D.pointAtDistance(P[tt], P[tt - 1], 
                                                             Utils.round((tmpdist + tmptotwidth) * 0.5));
                      else tmpC = P[tt];

                      if (a.getView3D())
                        g.moveTo(tmpC.x, tmpY, tmpC.y);
                      else
                        g.moveTo(tmpC.x, tmpC.y);
                    }
                    else
                      for (int ttt = tmpstart; ttt <= tt; ttt++)
                        if (a.getView3D())
                          g.lineTo(P[ttt].x, tmpY, P[ttt].y);
                        else
                          g.lineTo(P[ttt].x, P[ttt].y);
                    tmpstart = tt;
                  }
                  tt++;
                }

                for (int ttt = tmpstart; ttt < tmplength; ttt++)
                  if (a.getView3D())
                    g.lineTo(P[ttt].x, tmpY, P[ttt].y);
                  else
                    g.lineTo(P[ttt].x, P[ttt].y);
              }
              else
              {
                if (a.getView3D())
                {
                  g.moveTo(P[0].x, tmpY, P[0].y);
                  for (int ttt = 1; ttt < P.length; ttt++)
                    g.lineTo(P[ttt].x, tmpY, P[ttt].y);
                }
                else
                  g.polyLine(P);

              }

              if (cs.getPointer().getVisible())
                drawPointers(tL.Color, tL.LineColor);

              P = null;
            }
          }
          else if (algo == ContourConstruction.FAST)
          {

            // for all lines in level 
            for (int t = 0; t < tL.Count; t++)
            {
              lLine = tL.Line[t]; // draw the line
              if (a.getView3D())
              {
                g.moveTo(lLine.x1, tmpY, lLine.z1);
                g.lineTo(lLine.x2, tmpY, lLine.z2);
              }
              else
              {
                g.line(lLine.x1, lLine.z1, lLine.x2, lLine.z2);
              }
            }
          }
         }
            
//            // calculate Y position of the Level Up value 
//            if (cs.yPositionLevel) {
//                tmpY = cs.getVertAxis().calcYPosValue(tL.UpToValue);
//            }
//
//            boolean is3D = cs.chart.getAspect().getView3D();
//
//            // for all lines in level 
//            for (int t = 0; t < tL.Count; t++) {
//                LevelLine lLine = tL.Line[t]; // draw the line 
//
//                // @TODO Assert lLine != null
//                if (lLine != null) {
//                    if (is3D) {
//                        g.moveTo(lLine.x1, tmpY, lLine.z1);
//                        g.lineTo(lLine.x2, tmpY, lLine.z2);
//                    } else {
//                        g.line(lLine.x1, lLine.z1, lLine.x2, lLine.z2);
//                    }
//                }
//            }  
                       
//          }
       }
      
      private void deleteSegment(ContourLevel lvl, int index)
      {
        lvl.segments.getItem(index).points = null;
        lvl.segments.remove(index);
      }
      
      private boolean markOverlaps(SeriesMarksPosition position)
      {
        Rectangle tmpbounds = position.getBounds();
        Rectangle tmpR;
        MarkPositions positions = cs.getMarks().getPositions();

        for (int t = 0; t < positions.size(); t++)
        {
          if (positions.getPosition(t) != null)
          {
            tmpR = positions.getPosition(t).getBounds();
            Rectangle intersectRect= new Rectangle();
            Rectangle.intersect(tmpR, tmpbounds, intersectRect);
            if ((intersectRect.width>0) && (intersectRect.height>0))
              return true;
          }
        }
        return false;
      }
      
      private void drawLevelMark(int x, int y, int angle, String text, Color color, Color linecolor,
          SeriesMarksPosition position)
      {
        if (cs.chart.getAspect().getView3D())
        {
          cs.getMarks().zPosition = y;
          y = tmpY;
        }
        position.moveTo(x, y);

        if ((!cs.getContourMarks().getAntiOverlap()) || (!markOverlaps(position)))
        {
          double oldangle = cs.getMarks().getAngle();
          Color oldcolor = cs.getMarks().getFont().getColor();
          cs.getMarks().setAngle(oldangle + angle);

          if (cs.getContourMarks().getColorLevel()) cs.getMarks().getFont().setColor(color);

          cs.chart.getGraphics3D().setFont(cs.getMarks().getFont());
          cs.getMarks().internalDraw(0, color, text, position);
          if (cs.getContourMarks().getAntiOverlap())
          {
            SeriesMarksPosition tmppos = new SeriesMarksPosition();
            tmppos.assign(position);
            cs.getMarks().getPositions().add(tmppos);
          }

          cs.getMarks().setAngle(oldangle);
          if (cs.getContourMarks().getColorLevel()) cs.getMarks().getFont().setColor(oldcolor);

          cs.chart.getGraphics3D().setPen(cs.getPen());
          cs.chart.getGraphics3D().getPen().setColor(linecolor);
          cs.chart.getGraphics3D().getPen().setVisible(cs.getPen().getVisible());
        }
      }
      
      private void drawPointers(Color color, Color linecolor)
      {
        if (cs.chart.getAspect().getView3D())
          for (int t = 0; t < P.length; t++)
          {
            cs.startZ = P[t].y;
            cs.endZ = cs.startZ;
            cs.middleZ = cs.endZ;
            cs.getPointer().draw(P[t].x, tmpY, color);
          }
        else
          for (int t = 0; t < P.length; t++)
            cs.getPointer().draw(P[t].x, P[t].y, color);

        cs.chart.getGraphics3D().setPen(cs.getPen());
        cs.chart.getGraphics3D().getPen().setColor(linecolor);
        cs.chart.getGraphics3D().getPen().setVisible(cs.getPen().getVisible());
      }
      
        private void drawFrame()
        {
          IGraphics3D g = cs.chart.getGraphics3D();
          g.getBrush().setVisible(false);
          g.setPen(cs.framepen);

          int l, r, t, b;

          l = cs.getHorizAxis().calcPosValue(cs.getMinXValue());
          r = cs.getHorizAxis().calcPosValue(cs.getMaxXValue());
          b = cs.getZAxis().calcPosValue(cs.getMinZValue());
          t = cs.getZAxis().calcPosValue(cs.getMaxZValue());
          if (l > r)  //swap
          {
            int tmpVar = l;
            l = r;
            r = tmpVar;
          }
          if (t > b) 
          {
            int tmpVar = t;
            t = b;
            b = tmpVar;
          }

          if (cs.chart.getAspect().getView3D())
            g.rectangleY(l, tmpY, r, t, b);
          else g.rectangle(com.steema.teechart.Rectangle.fromLTRB(l, t, r, b));
        }
              
        private void olddraw() {
            double diffMin;
            double diffMax;
            double tmp1;
            double tmp2;
            double tmp3;
            double tmp4;
            double tmpAvg;

            if (cs.getCount() > 0) {
                /* prepare default params */
                cs.iNextXCell = 1;
                cs.iNextZCell = 1;

                /* set "Z" axis, depending on 2D || 3D */
                if (cs.chart.getAspect().getView3D()) {
                    tmpZAxis = cs.chart.getAxes().getDepth();
                } else {
                    tmpZAxis = cs.getVertAxis();
                }

                /* cache number of levels (speed opt.) */
                tmpNumLevels = cs.calcNumLevels();

                iLevels = new TempLevel[tmpNumLevels];

                ContourLevel cLevel = null;

                /* store cache of levels */
                for (int theLevel = 0; theLevel < tmpNumLevels; theLevel++) {
                    cLevel = cs.getLevels().getLevel(theLevel);

                    iLevels[theLevel] = new TempLevel(cLevel.getUpToValue(),
                            cLevel.getColor());
                }

                for (int z = 0; z < cs.iNumZValues; z++) {
                    /* for (each cell */
                    for (int x = 0; x < cs.iNumXValues; x++) {

                        if (cs.existFourGridIndex(x, z)) {

                            /* if xz values exist */
                            /* calc cell screen coordinates for Z */
                            cellZ[1] = tmpZAxis.calcYPosValue(cs.
                                    vzValues.getValue(cs.valueIndex0));
                            cellZ[3] = tmpZAxis.calcYPosValue(cs.
                                    vzValues.getValue(cs.valueIndex3));
                            cellZ[2] = cellZ[1];
                            cellZ[4] = cellZ[3];
                            cellZ[0] = (cellZ[1] + cellZ[3]) /
                                       2; /* mid Z pos */

                            /* calc cell screen coordinates for X */
                            Axis hAxis = cs.getHorizAxis();
                            cellX[1] = hAxis.calcXPosValue(cs.getXValues().
                                    getValue(cs.valueIndex0));
                            cellX[2] = hAxis.calcXPosValue(cs.getXValues().
                                    getValue(cs.valueIndex1));

                            cellX[3] = cellX[2];
                            cellX[4] = cellX[1];
                            cellX[0] = (cellX[1] + cellX[2]) /
                                       2; /* mid X pos */

                            /* get Y value for each grid cell corner */
                            ValueList vList = cs.getYValues();

                            tmp1 = vList.getValue(cs.valueIndex0);
                            tmp2 = vList.getValue(cs.valueIndex3);
                            tmp3 = vList.getValue(cs.valueIndex1);
                            tmp4 = vList.getValue(cs.valueIndex2);

                            /* calc min and max Y value of the 4 cell corners */
                            diffMin = tmp1;
                            if (tmp2 < diffMin) {
                                diffMin = tmp2;
                            }
                            if (tmp3 < diffMin) {
                                diffMin = tmp3;
                            }
                            if (tmp4 < diffMin) {
                                diffMin = tmp4;
                            }

                            if (diffMin <=
                                iLevels[tmpNumLevels - 1].UpToValue) {
                                /* if Min inside all levels */
                                diffMax = tmp1;
                                if (tmp2 > diffMax) {
                                    diffMax = tmp2;
                                }
                                if (tmp3 > diffMax) {
                                    diffMax = tmp3;
                                }
                                if (tmp4 > diffMax) {
                                    diffMax = tmp4;
                                }

                                tmpAvg = 0.25 *
                                         (tmp1 + tmp2 + tmp3 + tmp4);
                                /* average Y value */

                                /* if Max inside all levels */
                                if (diffMax >= iLevels[0].UpToValue) {
                                    for (int theLevel = 0;
                                            theLevel < tmpNumLevels;
                                            theLevel++) {

                                        /* for each level */
                                        tLevel = iLevels[theLevel];

                                        if ((tLevel.UpToValue >=
                                             diffMin) &&
                                            (tLevel.UpToValue <=
                                             diffMax)) {

                                            /* if inside one level */
                                            /* calc corners dif Y to level Up value */
                                            difY[1] = tmp1 -
                                                    tLevel.UpToValue;
                                            difY[2] = tmp3 -
                                                    tLevel.UpToValue;
                                            difY[3] = tmp4 -
                                                    tLevel.UpToValue;
                                            difY[4] = tmp2 -
                                                    tLevel.UpToValue;

                                            /* set dif[0] to Y average */
                                            difY[0] = tmpAvg -
                                                    tLevel.UpToValue;

                                            /* calculate all lines for one level in this XZ cell */
                                            calcLevel(theLevel);
                                        }
                                    }
                                }
                            }
                        }
                    }
                }

                drawLevelLines();
            }
        }

        private void calcLevel(int theLevel) {
            int[] signHeights = new int[5];

            /* calculate signs for (each corner Y value && Y average */
            for (int corner = 0; corner <= 4; corner++) {
                if (difY[corner] > 0) {
                    signHeights[corner] = 1;
                } else
                if (difY[corner] < 0) {
                    signHeights[corner] = -1;
                } else {
                    signHeights[corner] = 0;
                }
            }

            int signZero = signHeights[0]; /* average Y sign */

            for (int corner = 1; corner <= 4; corner++) {
                /* m1 = current corner */
                int m1 = corner;

                /* m3 = next corner */
                int m3 = (corner == 4) ? 1 : corner + 1;

                /* find "Side" between one corner and next corner */
                int side = SIDES[1 + signHeights[m1]][1 + signZero][1 +
                           signHeights[m3]];

                /* if valid side, calculate crossing points and add a new level line */
                if (side != 0) {
                    //calcLinePoints(m1, m3, side, theLevel);
                  if (algo== ContourConstruction.SEGMENTS) 
                      calcPointsSegment(m1, m3, side, theLevel);
                  else
                      calcLinePoints(m1, m3, side, theLevel);
                  }
                }
            }
            
            private IsoSurface iso;
            
            private void fillLevels()
            {
              if (iso == null)
                iso = new IsoSurface(cs.chart);

              iso.InternalUse = true;
              iso.assign(cs);
              iso.setUseColorRange(false);
              iso.setPaletteSteps(cs.numLevels);
              iso.getPalette().clear();
              for (int t = 0; t < cs.numLevels; t++)
                iso.getPalette().add(new GridPalette(iLevels[t].UpToValue, iLevels[t].Color));
              iso.setUseYPosition(!cs.yPositionLevel);
              iso.setYPosition(cs.yPosition);
              iso.getPen().setVisible(false);
              iso.getBandPen().setVisible(false);
              iso.calcColorRange();
              iso.draw();

            }
        }
    }


