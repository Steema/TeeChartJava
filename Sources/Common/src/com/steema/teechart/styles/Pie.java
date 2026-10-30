package com.steema.teechart.styles;

import java.util.ArrayList;

import com.steema.teechart.Aspect;
import com.steema.teechart.Comparator;
import com.steema.teechart.IBaseChart;
import com.steema.teechart.Rectangle;
import com.steema.teechart.Shadow;
import com.steema.teechart.TeeBase;
import com.steema.teechart.drawing.ChartBrush;
import com.steema.teechart.drawing.ChartPen;
import com.steema.teechart.drawing.Color;
import com.steema.teechart.drawing.EdgeStyle;
import com.steema.teechart.drawing.Graphics3D;
import com.steema.teechart.drawing.IGraphics3D;
import com.steema.teechart.drawing.Point;
import com.steema.teechart.editors.gallery.Gallery;
import com.steema.teechart.languages.Language;
import com.steema.teechart.legend.Legend;
import com.steema.teechart.misc.MathUtils;
import com.steema.teechart.misc.Utils;

/**
 *
 * <p>Title: Pie class</p>
 *
 * <p>Description: Pie Series.</p>
 *
 * <p>Example:
 * <pre><font face="Courier" size="4">
 *  pieSeries = new com.steema.teechart.styles.Pie(myChart.getChart());
 *  pieSeries.getMarks().setVisible(true);
 *  pieSeries.getShadow().setVisible(true);
 *  pieSeries.getShadow().setHorizSize(20);
 *  pieSeries.getShadow().setVertSize(20);
 *  pieSeries.fillSampleValues(8);
 *
 *  myChart.getHeader().setVisible(true);
 *  myChart.setText("Pie");
 *  myChart.getAspect().setElevation(315);
 *  myChart.getAspect().setOrthogonal(false);
 *  myChart.getAspect().setPerspective(0);
 *  myChart.getAspect().setRotation(360);
 * </font></pre></p>
 *
 * <p>Copyright (c) 2005-2019 by Steema Software SL. All Rights Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>

 */
public class Pie extends Circular {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
     *
     * <p>Title: PieOtherSlice class</p>
     *
     * <p>Description: Pie series uses this class in its Pie.OtherSlice.</p>
     *
     * <p>Copyright (c) 2005-2019 by Steema Software SL. All Rights Reserved.</p>
     *
     * <p>Company: Steema Software SL</p>
     *
     * @see Pie#getOtherSlice
     */
    final public class PieOtherSlice extends TeeBase {
    	
    	private static final long serialVersionUID = 1L;

        private Color color;
        private String text;
        private double aValue;

        private Legend legend;
        private Series series;

        private PieOtherStyle style = PieOtherStyle.NONE;

        public PieOtherSlice(IBaseChart c, final Series s) {
            super(c);
            text = "Other";
            color = Color.EMPTY;
            if (series == null) {
                series = s;
            }
        }

        /**
         * The Color of the OtherSlice.
         *
         * @return Color
         */
        public Color getColor() {
            return color;
        }

        /**
         * Sets the Color of the OtherSlice.
         *
         * @param value Color
         */
        public void setColor(final Color value) {
            color = setColorProperty(color, value);
        }

        private void setLegend(Legend value) {
            if (legend != null) {
                legend = value;
                legend.setSeries(series);
            }
        }

        /**
         * Sets the properties of the PieOtherSlice Legend.<br><br>
         *
         * Example This sample shows how to use PieOtherSlice Legend:<br><br>
         *
         * prepare "Other" to group values below 10<br>
         * pie1.getOtherSlice().setStyle(PieOtherStyles.BELOWVALUE);<br>
         * pie1.getOtherSlice().setValue(10);<br>
         * pie1.getOtherSlice().setText("Other");<br>
         * pie1.getOtherSlice().getLegend().setVisible( true );<br>
         * pie1.getOtherSlice().getLegend().setCustomPosition( true );<br>
         * pie1.getOtherSlice().getLegend().setLeft( 350 );<br>
         * pie1.getOtherSlice().getLegend().setTop( 150 );<br>
         *
         *
         * @return Legend
         */
        public Legend getLegend() {
            if (legend == null) {
                legend = new Legend(series.getChart());
                legend.setVisible(false);
                legend.setSeries(series);
            }
            return legend;
        }

        /**
         * Title for otherSlice.<br>
         * Default value: Other
         *
         * @return String
         */
        public String getText() {
            return text;
        }

        /**
         * Title for otherSlice.<br>
         * Default value: Other
         *
         * @param value String
         */
        public void setText(final String value) {
            text = setStringProperty(text, value);
        }

        /**
         * Value (value or percentage) for Otherslice grouping.
         *
         * @return double
         */
        public double getValue() {
            return aValue;
        }

        /**
         * Value (value or percentage) for Otherslice grouping.
         *
         * @param value double
         */
        public void setValue(final double value) {
            aValue = setDoubleProperty(aValue, value);
        }

        /**
         * Either value or percentage to group the 'other' Pie slice.
         *
         *
         * @return PieOtherStyle
         */
        public PieOtherStyle getStyle() {
            return style;
        }

        /**
         * Sets either value or percentage to group the 'other' Pie slice.
         *
         *
         * @param value PieOtherStyles
         */
        public void setStyle(final PieOtherStyle value) {
            if (style != value) {
                style = value;
                invalidate();
            }
        }
    }


    public static final int BelongsToOther = -1;
    private static final int OtherFlag = Integer.MAX_VALUE;

    private int angleSize = 360;
    private boolean dark3D = true;
    private boolean darkPen;
    protected int iDonutPercent;
    private int bevelPercent = 0;
    private EdgeStyle edgeStyle = EdgeStyle.NONE;
    private ExplodedSliceList explodedSlice = new ExplodedSliceList(0);
    private int explodeBiggest;
    private PieOtherSlice otherSlice;
    private MultiPies multiPie = MultiPies.AUTOMATIC;
    private PieShadow shadow;
    private boolean usePatterns;
    private ChartPen pen;
    private boolean autoMarkPosition = true;
    private SliceValueList sliceHeight = new SliceValueList();
    
    private PieMarks piemarks;
    private boolean forceCenter = false;

    private Rectangle iOldChartRect;
    private int iOldDonut; // Concentric
    private boolean iHideSides; // Concentric

    transient protected int iniX;
    transient protected int iniY;

    transient private int[] sortedSlice;
    transient private boolean isExploded;

    transient public PieAngle[] angles;

    /**
     *
     * <p>Title: PieShadow class</p>
     *
     * <p>Description: Pie series uses this class in its Pie.Shadow.</p>
     *
     * <p>Copyright (c) 2005-2019 by Steema Software SL. All Rights Reserved.</p>
     *
     * <p>Company: Steema Software SL</p>
     *
     * @see Pie#getShadow
     */
    final public class PieShadow extends Shadow {

        public PieShadow(IBaseChart c) {
            super(c);

            bBrush.setDefaultColor(Color.DARK_GRAY);
            setDefaultVisible(false);
            setDefaultSize(20);
        }
    }

    /**
     *
     * <p>Title: SliceValueList class</p>
     *
     * <p>Description: List to hold percents of height for each Pie slice.</p>
     *
     * <p>Copyright (c) 2005-2019 by Steema Software SL. All Rights Reserved.</p>
     *
     * <p>Company: Steema Software SL</p>
     *
     */
    final public class SliceValueList extends ArrayList {
        public Series OwnerSeries;

        public int getSlice(int index) {
            if (index < size()) {
                return ((Integer)super.get(index)).intValue();
            } else {
                return 0;
            }          
        }

        public void setSlice(int index, int value) {          
            while (index >= size()) {
                add(new Integer(0));
            }
            
            if ( ((Integer)super.get(index)).intValue() != value) {
                set(index, new Integer(value));
                OwnerSeries.repaint();
            }
        } 

        private void exchange(int a, int b) {
            int s = getSlice(a);
            setSlice(a, getSlice(b));
            setSlice(b, s);
        }
    }

    /**
     *
     * <p>Title: ExplodedSliceList class</p>
     *
     * <p>Description: List to hold percents of exploding effect, one per each
     * Pie slice.</p>
     *
     * <p>Copyright (c) 2005-2019 by Steema Software SL. All Rights Reserved.</p>
     *
     * <p>Company: Steema Software SL</p>
     *
     */
    final public class ExplodedSliceList extends ArrayList {
        public ExplodedSliceList(int capacity) {
            super(capacity);
        }

        public int getSlice(int index) {
            if (index < size()) {
                return ((Integer)super.get(index)).intValue();
            } else {
                return 0;
            }
        }

        public void setSlice(int index, int value) {
            while (size() <= index) {
                add(new Integer(0));
            }
            set(index, new Integer(value));
        }

        private void exchange(int a, int b) {
            int s = getSlice(a);
            setSlice(a, getSlice(b));
            setSlice(b, s);
        }
    }


    public Pie() {
        this((IBaseChart)null);
    }

    public Pie(IBaseChart c) {
        super(c);

        setColorEach(true);
        pen = new ChartPen(chart, Color.BLACK);
        getMarks().setDefaultVisible(true);
        marks.getArrow().setDefaultColor(Color.BLACK);
        marks.getCallout().setDefaultLength(8);
        marks.getTailParams().setAlign(TailAlignment.Auto);
        useSeriesColor = false;
        sliceHeight.OwnerSeries = this;
    }

    public void assign(final Series source) {
        if (source instanceof Pie) {
            Pie sourcePie = (Pie)source;
        
            this.angleSize = sourcePie.angleSize;
            this.bevelPercent = sourcePie.bevelPercent;
            this.dark3D = sourcePie.dark3D;
            this.darkPen =sourcePie.darkPen;
            this.iDonutPercent = sourcePie.iDonutPercent;
            this.explodeBiggest = sourcePie.explodeBiggest;
            this.explodedSlice = (ExplodedSliceList)sourcePie.explodedSlice.clone();
            this.multiPie = sourcePie.multiPie;
            this.sliceHeight = (SliceValueList)sourcePie.sliceHeight.clone();
            this.usePatterns = sourcePie.usePatterns;
            this.otherSlice = sourcePie.otherSlice;
            this.piemarks = sourcePie.piemarks;
            this.forceCenter = sourcePie.forceCenter;
        }
        
        super.assign(source);
    }
    
    /**
     * Stores the Pie slice values.
     *
     * @return ValueList
     */
    public ValueList getPieValues() {
        return vyValues;
    }

    public void setChart(IBaseChart c) {
        super.setChart(c);
        if (pen != null) {
            pen.setChart(chart);
        }
        if (shadow != null) {
            shadow.setChart(chart);
        }
    }

    protected void setDonutPercent(int value) {
        iDonutPercent = setIntegerProperty(iDonutPercent, value);
    }

    /**
     * Draws points with different preset Colors.<br>
     * Default value: true
     *
     * @return boolean
     */
    public boolean getColorEach() {
        return super.getColorEach();
    }

    /**
     * Draws points with different preset Colors.<br>
     * Default value: true
     *
     * @param value boolean
     */
    public void setColorEach(boolean value) {
        super.setColorEach(value);
    }

    /**
     * Total angle in degrees (0 to 360) for all slices.<br>
     * Default value: 360
     *
     * @return int
     */
    public int getAngleSize() {
        return angleSize;
    }

    /**
     * Total angle in degrees (0 to 360) for all slices.<br>
     * Default value: 360<br>
     *
     * <p>Example:
     * <pre><font face="Courier" size="4">
     * pieSeries = new com.steema.teechart.styles.Pie(myChart.getChart());
     * pieSeries.getMarks().setVisible(true);
     * pieSeries.getMarks().setStyle(MarksStyle.LABELPERCENT);
     * pieSeries.fillSampleValues(5);
     * pieSeries.setAngleSize(180);
     * pieSeries.setRotationAngle(90);
     * </font></pre></p>
     *
     * @param value int
     */
    public void setAngleSize(int value) {
        angleSize = setIntegerProperty(angleSize, value);
    }

    /**
     * Darkens side of 3D pie section to add depth.<br>
     * When true, it fills the Pie 3D effect screen areas with darker colors
     * than their corresponding Pie sectors. These colors look much better
     * with 16k colors video mode or greater. <br>
     * The Pie sector RGB color is increased to 40 units to obtain the darker
     * color. <br>
     * Default value: true
     *
     * @return boolean
     */
    public boolean getDark3D() {
        return dark3D;
    }

    /**
     * Darkens side of 3D pie section to add depth.<br>
     * Default value: true
     *
     * @param value boolean
     */
    public void setDark3D(boolean value) {
        dark3D = setBooleanProperty(dark3D, value);
    }
    
    /**
     * The Transparency level from 0 to 100%.<br>
     * Transparency is a value between 0 and 100 which sets the transparency
     * percentage with respect to foreground versus background.<br>
     * Default value: 0

     * @return int
     */
    public int getTransparency() {
        return getBrush().getTransparency();
    }

    /**
     * Sets Transparency level from 0 to 100%.<br>
     * Default value: 0
     *
     * @param value int
     */
    public void setTransparency(int value) {
        getBrush().setTransparency(value);
    }    
    
    /*
    ** Gets and sets the EdgeStyle of the bevel.
    */
    public EdgeStyle getEdgeStyle()
    {
      return edgeStyle;
    } 
    
    public void setEdgeStyle(EdgeStyle value)
    {
      if (edgeStyle != value)
      {
        edgeStyle = value;
        refreshSeries();
      }
    }

    /*
    ** Gets and sets the bevel as a percentage of the pie's depth.
    */
    public int getBevelPercent()
    {
      return bevelPercent; 
    }
    
    public void setBevelPercent(int value)
    {
      if (bevelPercent != value)
      {
        bevelPercent = value;
        refreshSeries();
      }

    }

    /**
     * Darkens pie slice borders.<br>
     * Default value: false
     *
     * @return boolean
     */
    public boolean getDarkPen() {
        return darkPen;
    }

    /**
     * Darkens pie slice borders.<br>
     * Default value: false
     *
     * @param value boolean
     */
    public void setDarkPen(boolean value) {
        darkPen = setBooleanProperty(darkPen, value);
    }

    /**
     * Displaces the biggest slice from centre by value set.<br>
     * Default value: 0
     *
     * @return int
     */
    public int getExplodeBiggest() {
        return explodeBiggest;
    }

    /**
     * Displaces the biggest slice from centre by value set.<br>
     * Default value: 0
     *
     * @param value int
     */
    public void setExplodeBiggest(int value) {
        explodeBiggest = setIntegerProperty(explodeBiggest, value);
        calcExplodeBiggest();
    }

    /**
     * Accesses the OtherSlice properties.<br>
     * For example, you can use it to define the grouping size for the
     * 'Other' slice of the Pie.<br>
     * Grouping may be expressed as a percentage or value.<br>
     *
     * <p>Example:
     * <pre><font face="Courier" size="4">
     * pieSeries.getOtherSlice().getLegend().setVisible(isSelected);
     * </font></pre></p>
     *
     * @see com.steema.teechart.styles.Pie.PieOtherSlice#getStyle
     * @return PieOtherSlice
     */
    public PieOtherSlice getOtherSlice() {
        if (otherSlice == null) {
            otherSlice = new PieOtherSlice(chart, this);
        }
        return otherSlice;
    }

    /**
     * Line pen for Pie.<br>
     *
     * @return ChartPen
     */
    public ChartPen getPen() {
        return pen;
    }

    /**
     * Brush fill for PieSeries.
     *
     * @return ChartBrush
     */
    public ChartBrush getBrush() {
        return bBrush;
    }

    /**
     * Defines the offset shadow of the PieSeries.
     *
     * <p>Example:
     * <pre><font face="Courier" size="4">
     * pieSeries = new com.steema.teechart.styles.Pie(myChart.getChart());
     * pieSeries.getMarks().setVisible(true);
     * pieSeries.getShadow().setVisible(true);
     * pieSeries.getShadow().setWidth(30);
     * pieSeries.getShadow().setHeight(50);
     * pieSeries.getShadow().setColor(Color.SILVER);
     * pieSeries.fillSampleValues(9);
     *
     * </font></pre></p>
     *
     * @return PieShadow
     */
    public PieShadow getShadow() {
        if (shadow == null) {
            shadow = new PieShadow(chart);
        }
        return shadow;
    }

    /**
     * Fills Pie Sectors with different Brush pattern styles.<br>
     * Default value: false
     *
     * @return boolean
     */
    public boolean getUsePatterns() {
        return usePatterns;
    }

    /**
     * Fills Pie Sectors with different Brush pattern styles.<br>
     * Default value: false
     *
     * @param value boolean
     */
    public void setUsePatterns(boolean value) {
        usePatterns = setBooleanProperty(usePatterns, value);
    }

    /**
     * If true, marks will be displayed trying to not overlap one to each
     * other.<br>
     * Default value: true
     *
     * @return boolean
     */
    public boolean getAutoMarkPosition() {
        return autoMarkPosition;
    }

    /**
     * If true, marks will be displayed trying to not overlap one to each
     * other.<br>
     * Default value: true
     *
     * @param value boolean
     */
    public void setAutoMarkPosition(boolean value) {
        autoMarkPosition = setBooleanProperty(autoMarkPosition, value);
    }

    /**
    * Gets the MultiPie value.
    * Default value: MultiPies.AUTOMATIC
    *
    * @return MultiPies
    */
    public MultiPies getMultiPie() {
        return multiPie;
    }

    /**
     * Sets the MultiPie value.
     * Default value: MultiPies.AUTOMATIC
     *
     * @param value MultiPies
     */
    public void setMultiPie(MultiPies value) {
        if (multiPie != value) {
            multiPie = value;
            refreshSeries();
        }
    }

    /**
     * If true, the pie will be centered even when totalAngle is smaller than 360 degrees.<br>
     * Default value: false
     *
     * @return boolean
     */
    public boolean getForceCenter() { return forceCenter; }

    /**
     * If true, the pie will be centered even when totalAngle is smaller than 360 degrees.<br>
     * Default value: false
     *
     * @param value boolean
     */
    public void setForceCenter(boolean value) {
        forceCenter = setBooleanProperty(forceCenter, value);
    }

    private void calcExplodeBiggest() {
        int tmp = getYValues().indexOf(getYValues().getMaximum());
        if (tmp != -1) {
            explodedSlice.setSlice(tmp, explodeBiggest);
        }
    }

    private Point calcExplodedOffset(int valueIndex) {
        Point offset = new Point();

        if (isExploded) {
            double tmpExp = explodedSlice.getSlice(valueIndex);
            if (tmpExp > 0) {
                // Apply exploded % to radius
                double tmp = angles[valueIndex].MidAngle;
                if (chart.getGraphics3D().getSupportsFullRotation()) {
                    tmp += (0.25 * 2.0 * Math.PI * angleSize / 360.0) +
                            Math.PI;
                }

                final double tmpSin = Math.sin(tmp + rotDegree);
                final double tmpCos = Math.cos(tmp + rotDegree);

                tmpExp *= 0.01;
                offset.x = Utils.round(iXRadius * tmpExp * tmpCos);
                offset.y = Utils.round(iYRadius * tmpExp * tmpSin);
            }
        }

        return offset;
    }

    public void galleryChanged3D(boolean is3D) {
        super.galleryChanged3D(is3D);
        disableRotation();
        setCircled((!chart.getAspect().getView3D()));
    }

    private double getAngleSlice(int index, double totalAngle) {
        double result = angles[index].MidAngle + rotDegree;
        if (result > totalAngle) {
            result -= totalAngle;
        }
        if (result > (0.25 * totalAngle)) {
            result -= (0.25 * totalAngle);
            if (result > Math.PI) {
                result = totalAngle - result;
            }
        } else {
            result = (0.25 * totalAngle) - result;
        }
        return result;
    }

    private void disableRotation() {
        final Aspect a = chart.getAspect();
        a.setOrthogonal(false);
        a.setRotation(0);
        a.setElevation(305);
    }

    protected void swapValueIndex(int a, int b) {
        super.swapValueIndex(a, b);
        if (explodedSlice.size()>0) explodedSlice.exchange(a, b);
        if (sliceHeight.size()>0) sliceHeight.exchange(a, b);
    }

    protected void addSampleValues(int numValues) {
        final String[] pieSampleStr =
                new String[] {
                Language.getString("PieSample1"),
                Language.getString("PieSample2"),
                Language.getString("PieSample3"),
                Language.getString("PieSample4"),
                Language.getString("PieSample5"),
                Language.getString("PieSample6"),
                Language.getString("PieSample7"),
                Language.getString("PieSample8")
        };

        final SeriesRandom r = randomBounds(numValues);
        for (int t = 0; t < numValues; t++) {
            add(1 + Utils.round(1000 * r.Random()), // <-- Value
                pieSampleStr[t % 8]); // <-- Label
        }
    }   
    
    private int SliceEndZ(int ValueIndex)
    {
      int result;
      
      if (sliceHeight.size() > ValueIndex)
        result = getStartZ() + Utils.round((getEndZ() - getStartZ()) * sliceHeight.getSlice(ValueIndex) * 0.01);
      else
        result = getEndZ();

      return result;
    }

    private void calcAngles() {
        final double totalAngle = 2.0 * Math.PI * angleSize / 360.0;
        double tmpSumAbs; // = getYValues().getTotalABS();
        
        if (getOtherSlice().getStyle() == PieOtherStyle.NONE && getFirstVisible() != -1)
        {
          tmpSumAbs = 0;
          for (int i = firstVisible; i <= lastVisible; i++)
          {
            tmpSumAbs += Math.abs(this.mandatory.getValue(i));
          }
        }
        else
        {
          tmpSumAbs = mandatory.getTotalABS();
        }
        
        final double piPortion = (tmpSumAbs != 0) ? totalAngle / tmpSumAbs : 0;

        // angles.clear();
        angles = new PieAngle[getCount()];

        double acumValue = 0;

        for (int t = firstVisible; t <= lastVisible; t++) {
            angles[t] = new PieAngle();
            angles[t].StartAngle = (t == firstVisible) ? 0 : angles[t - 1].EndAngle;

            if (tmpSumAbs != 0) {
                if (!belongsToOtherSlice(t)) {
                    acumValue += Math.abs(mandatory.getValue(t));
                }
                if (acumValue == tmpSumAbs) {
                    angles[t].EndAngle = totalAngle;
                } else {
                    angles[t].EndAngle = acumValue * piPortion;
                }

                /* prevent small pie sectors */
                if ((angles[t].EndAngle - angles[t].StartAngle) >
                    totalAngle) {
                    angles[t].EndAngle = angles[t].StartAngle + totalAngle;
                }
            } else {
                angles[t].EndAngle = totalAngle;
            }

            angles[t].MidAngle = (angles[t].StartAngle + angles[t].EndAngle) *
                                 0.5;
        }
    }

    protected Point calcExplodedRadius(int valueIndex) {
        final double tmpExp = 1.0 + explodedSlice.getSlice(valueIndex) * 0.01;
        return new Point(Utils.round(iXRadius * tmpExp),
                         Utils.round(iYRadius * tmpExp));
    }

    protected void clearLists() {
        super.clearLists();
        explodedSlice.clear();
        sliceHeight.clear();
    }

    public void doBeforeDrawChart() {
        super.doBeforeDrawChart();

        double tmp;
        double tmpValue;
        boolean tmpHasOther;

        /* re-order values */
        if (getPieValues().getOrder() != ValueListOrder.NONE) {
            getPieValues().sort();
        }

        removeOtherSlice();

        SeriesMarksPosition tmpOtherMark = otherMarkCustom();

        /* reset X order... */
        getXValues().fillSequence();

        /* calc "Other" slice... */
        if ((otherSlice != null) &&
            (otherSlice.getStyle() != PieOtherStyle.NONE) &&
            (getYValues().getTotalABS() > 0)) {
            tmpHasOther = false;
            tmpValue = 0;
            for (int t = 0; t < getCount(); t++) {
                tmp = getYValues().value[t];
                if (otherSlice.getStyle() == PieOtherStyle.BELOWPERCENT) {
                    tmp = tmp * 100.0 / getYValues().getTotalABS();
                }
                if (tmp < otherSlice.getValue()) {
                    tmpValue += getYValues().value[t];
                    getXValues().value[t] = BelongsToOther;
                    /* <-- belongs to "other" */
                    tmpHasOther = true;
                }
            }

            // Add "Other" slice
            if (tmpHasOther) {
                // 5.02
                int t = add(OtherFlag, tmpValue, otherSlice.text, otherSlice.getColor());

                double tmpABS = getYValues().getTotalABS();
                getYValues().totalABS = tmpABS - tmpValue; /* reset Y total */
                getYValues().statsOk = true;
                //getYValues().totalABS -= tmpValue; /* reset Y total */
                //getYValues().statsOk = true;
                
                if (tmpOtherMark != null)
                {
                  getMarks().getPositions().setPosition(t,tmpOtherMark);
                }
            }
        }
    }
    
    // Returns index of "this" into all visible Pie series
    private int pieIndex()
    {
      int result = 0;
      
      for (int i=0; i<getChart().getSeriesCount();i++)
      {
        if (chart.getSeries(i) == this)
          break;
        else
          if (chart.getSeries(i).getActive() && (chart.getSeries(i) instanceof Pie))
            result++;
      }

      return result;
    }

    // Returns number of visible Pie series
    private int pieCount()
    {
      int result = 0;
      for (int i=0; i<chart.getSeriesCount();i++)
      {
        if ((chart.getSeries(i).getActive() && (chart.getSeries(i)) instanceof Pie))
          result++;
      }

      return result;
    }

    private void guessRectangle()
    {
      int tmpCount = pieCount();

      if (tmpCount > 1)
      {
        int tmpIndex = pieIndex();


        final Rectangle tmpR = chart.getChartRect();
        int tmpW = tmpR.width;
        int tmpH = tmpR.height;
        int tmpCols = (int)Math.round(Math.sqrt(tmpCount));

        tmpR.x += (tmpIndex % tmpCols) * (tmpW / tmpCols);
        tmpR.width = (tmpW / tmpCols);

        int tmpRows = (int)Math.round(0.5 + Math.sqrt(tmpCount));

        tmpR.y += ((tmpIndex / tmpCols)) * (tmpH / tmpRows);
        tmpR.height = (tmpH / tmpRows);

        chart.setChartRect(chart.getGraphics3D().calcRect3D(tmpR, 0));
      }
    }

    // Calculate "this" Pie rectangle, when multiple pie series exist.

    protected void doBeforeDrawValues() {
        iAdjustCircleForMarks = !getMarksPie().getInsideSlice();

        iOldChartRect = chart.getChartRect().copy();
        iOldDonut = iDonutPercent;

        if (multiPie == MultiPies.CONCENTRIC) {
            super.doBeforeDrawValues();

            prepareConcentric();
        } else {
            iHideSides = false;

            if (multiPie == MultiPies.AUTOMATIC)
                guessRectangle();

            super.doBeforeDrawValues();
        }
    }

    private void prepareConcentric() {
        int tmpCount = countConcentric();

        // Calculate our "index" in the visible concentric Pies, and the "previous" pie series:
        Series previous = null;
        int tmpIndex = 0;

        for (int t=0; t<chart.getSeriesCount(); t++) {
            Series tmp = chart.getSeries(t);

            if (tmp == this)
                break;
            else
                if (isConcentric(tmp)) {
                    previous = tmp;
                    tmpIndex++;
                }
        }

        // Dont paint slice "donut fronts":
        iHideSides = true;

        // Calculate concentric radius:
        double tmpFactor = (tmpIndex+1)*(100 / tmpCount)*0.01;

        iXRadius = MathUtils.round(tmpFactor*getXRadius());
        iYRadius = MathUtils.round(tmpFactor*getYRadius());

        // And interior "donut" hole:
        if (previous != null)
            setDonutPercent(Math.round(100*((Pie)previous).iXRadius/iXRadius));
        else
            setDonutPercent(0);
    }

    // Returns number of visible Pie series in "concentric" mode:
    private int countConcentric() {
        int result = 0;

        for (int t=0; t<chart.getSeriesCount(); t++)
            if (isConcentric(chart.getSeries(t)))
                result++;

        return result;
    }

    // Returns True when ASeries is visible, its a Pie, and is "concentric"
    private boolean isConcentric(Series ASeries) {
        return ASeries.getActive() && (ASeries instanceof Pie) &&
                (((Pie)ASeries).getMultiPie() == MultiPies.CONCENTRIC);
    }

    // Resets back old chart rectangle
    protected void doAfterDrawValues() {
      setDonutPercent(iOldDonut);
      chart.setChartRect(iOldChartRect);
      super.doAfterDrawValues();
    }

    protected class CompareSlice implements Comparator {
        public final int compare( int a, int b ) {
            final double totalAngle = 2.0 * Math.PI * angleSize / 360.0;
            final double tmpA = getAngleSlice(sortedSlice[a], totalAngle);
            final double tmpB = getAngleSlice(sortedSlice[b], totalAngle);
            
            if (tmpA < tmpB) {
                return -1;
            } else if (tmpA > tmpB) {
                return 1;
            } else {
                return 0;
            }
        };
    };
    
    private void removeOtherSlice()
		{
			/* remove "other" slice, if exists... */
			for (int t = 0; t < getCount(); t++)
				if (vxValues.getValue(t) == OtherFlag)
				{
					delete(t);
					break;
				}
		}
    
    private SeriesMarksPosition otherMarkCustom()
		{
			SeriesMarksPosition tmp, result;

			for (int i = 0; i < getCount(); i++)
			{
				if (vxValues.getValue(i) == OtherFlag)
				{
					tmp = getMarks().getPositions().getPosition(i);

					if (tmp != null && tmp.custom)
					{
						result = new SeriesMarksPosition();
						tmp.assign(result);
            return result;
					}
					break;
				}
			}

			return null;
		}

    protected void draw() {

        if (explodeBiggest > 0) {
            calcExplodeBiggest();
        }
        int maxExplodedIndex = -1;
        int maxExploded = 0;
        int tmpCount = getCount();

        // calc biggest exploded index
        for (int t = 0; t < explodedSlice.size(); t++) {
            if (explodedSlice.getSlice(t) > maxExploded) {
                maxExploded = Utils.round(explodedSlice.getSlice(t));
                maxExplodedIndex = t;
            }
        }

        // calc each slice angles
        calcAngles();

        if (getForceCenter()) {
            forceInCenter();
        }

        //adjust circle rectangle
        isExploded = (maxExplodedIndex != -1 || sliceHeight.size()>0);
        if (maxExplodedIndex != -1) {
            final Point tmpOff = calcExplodedOffset(maxExplodedIndex);

            getCircleRect().grow( -Math.abs(tmpOff.x) / 2,
                                    -Math.abs(tmpOff.y) / 2);

            adjustCircleRect();
            calcRadius();
        }

        /* start xy pos */
        final Point ini = angleToPos(0, iXRadius, iYRadius);

        IGraphics3D g = chart.getGraphics3D();

        if ((shadow != null) && (shadow.getVisible()) &&
            (!shadow.getColor().isEmpty()) &&
            ((shadow.getWidth() != 0) || (shadow.getHeight() != 0)) &&
            (multiPie != MultiPies.CONCENTRIC)) {
            
        	shadow.draw(g, rCircleRect.x, rCircleRect.y, 
        			rCircleRect.getRight(), rCircleRect.getBottom(), getEndZ()-10);
        }

        //CDI PieOtherSlice Legend
        Rectangle rect = chart.getChartRect();
        Legend tmp;
        
        if (getOtherSlice().getLegend() != null &&
            getOtherSlice().getLegend().getVisible()) {
            tmp = chart.getLegend();
            chart.setLegend(getOtherSlice().getLegend());
            rect = chart.doDrawLegend(chart.getGraphics3D(), rect);
            chart.setLegend(tmp);
        }

        /* exploded slices drawing order... */
        //if (chart.getAspect().getView3D() && (isExploded || (iDonutPercent > 0))
        if (chart.getAspect().getView3D()
            && (!g.getSupportsFullRotation())) {
            if ((sortedSlice == null) || (sortedSlice.length != tmpCount)) {
                sortedSlice = new int[tmpCount];
            }

            for (int t = 0; t < tmpCount; t++) {
                sortedSlice[t] = t;
            }

            Utils.sort(sortedSlice, 0, tmpCount-1, new CompareSlice());

            for (int t = 0; t < tmpCount; t++) {
                drawValue(sortedSlice[t]);
            }
        } else {
            super.draw();
        }
    }

    private Rectangle calcContainingRect() {
        Point tmpCenter = new Point(getCircleXCenter(), getCircleYCenter());

        int startAngle = getRotationAngle();
        double endAngle = startAngle + getAngleSize();

        Point tmpA = MathUtils.calcPoint(Utils.PISTEP * startAngle, tmpCenter, getXRadius(), getYRadius());
        Point tmpB = MathUtils.calcPoint(Utils.PISTEP * endAngle, tmpCenter, getXRadius(), getYRadius());

        Point tmpC;
        Point tmpD;
        if ((iDonutPercent > 0) && (getAngleSize() < 180)) {
            tmpC = MathUtils.calcPoint(Utils.PISTEP * startAngle, tmpCenter, getXRadius() * iDonutPercent * 0.01, getYRadius() * iDonutPercent * 0.01);
            tmpD = MathUtils.calcPoint(Utils.PISTEP * endAngle, tmpCenter, getXRadius() * iDonutPercent * 0.01, getYRadius() * iDonutPercent * 0.01);
        }
        else {
            tmpC = tmpCenter;
            tmpD = tmpCenter;
        }

        Rectangle tmpR = new Rectangle();

        if (tmpA.x < tmpCenter.x) { //tmpA in Left
            if (tmpA.y < tmpCenter.y) { //tmpA in TopLeft quadrant
                if (tmpB.x < tmpCenter.x) { //tmpB in Left
                    if (tmpB.y < tmpCenter.y) { //tmpB in TopLeft quadrant
                        if (getAngleSize() > 270) {
                            tmpR = getCircleRect();
                        }
                        else {
                            tmpR.setLeft(Math.min(tmpA.x, tmpB.x));
                            tmpR.setRight(tmpC.x);
                            tmpR.setTop(Math.min(tmpA.y, tmpB.y));
                            tmpR.setBottom(tmpD.y);
                        }
                    }
                    else { //tmpB in BottomLeft quadrant
                        tmpR.setLeft(getCircleRect().getLeft());
                        tmpR.setRight(Math.max(tmpC.x, tmpD.x));
                        tmpR.setTop(tmpA.y);
                        tmpR.setBottom(tmpB.y);
                    }
                }
                else { //tmpB in Right
                    if (tmpB.y < tmpCenter.y) { //tmpB in TopRight quadrant
                        tmpR.setLeft(getCircleRect().getLeft());
                        tmpR.setRight(getCircleRect().getRight());
                        tmpR.setTop(Math.min(tmpA.y, tmpB.y));
                        tmpR.setBottom(getCircleRect().getBottom());
                    }
                    else { //tmpB in BottomRight quadrant
                        tmpR.setLeft(getCircleRect().getLeft());
                        tmpR.setRight(tmpB.x);
                        tmpR.setTop(tmpA.y);
                        tmpR.setBottom(getCircleRect().getBottom());
                    }
                }
            }
            else { //tmpA in BottomLeft quadrant
                if (tmpB.x < tmpCenter.x) { //tmpB in Left
                    if (tmpB.y < tmpCenter.y) { //tmpB in TopLeft quadrant
                        tmpR.setLeft(Math.min(tmpA.x, tmpB.x));
                        tmpR.setRight(getCircleRect().getRight());
                        tmpR.setTop(getCircleRect().getTop());
                        tmpR.setBottom(getCircleRect().getBottom());
                    }
                    else { //tmpB in BottomLeft quadrant
                        if (getAngleSize() > 270) {
                            tmpR = getCircleRect();
                        }
                        else {
                            tmpR.setLeft(Math.min(tmpA.x, tmpB.x));
                            tmpR.setRight(tmpD.x);
                            tmpR.setTop(tmpC.y);
                            tmpR.setBottom(Math.max(tmpA.y, tmpB.y));
                        }
                    }
                }
                else { //tmpB in Right
                    if (tmpB.y < tmpCenter.y) { //tmpB in TopRight quadrant
                        tmpR.setLeft(tmpA.x);
                        tmpR.setRight(getCircleRect().getRight());
                        tmpR.setTop(tmpB.y);
                        tmpR.setBottom(getCircleRect().getBottom());
                    }
                    else { //tmpB in BottomRight quadrant
                        tmpR.setLeft(tmpA.x);
                        tmpR.setRight(tmpB.x);
                        tmpR.setTop(Math.min(tmpC.y, tmpD.y));
                        tmpR.setBottom(getCircleRect().getBottom());
                    }
                }
            }
        }
        else { //tmpA in Right
            if (tmpA.y < tmpCenter.y) { //tmpA in TopRight quadrant
                if (tmpB.x < tmpCenter.x) { //tmpB in Left
                    if (tmpB.y < tmpCenter.y) { //tmpB in TopLeft quadrant
                        tmpR.setLeft(tmpB.x);
                        tmpR.setRight(tmpA.x);
                        tmpR.setTop(getCircleRect().getTop());
                        tmpR.setBottom(Math.max(tmpC.y, tmpD.y));
                    }
                    else { //tmpB in BottomLeft quadrant
                        tmpR.setLeft(getCircleRect().getLeft());
                        tmpR.setRight(tmpA.x);
                        tmpR.setTop(getCircleRect().getTop());
                        tmpR.setBottom(tmpB.y);
                    }
                }
                else { //tmpB in Right
                    if (tmpB.y < tmpCenter.y) { //tmpB in TopRight quadrant
                        if (getAngleSize() > 270) {
                            tmpR = getCircleRect();
                        }
                        else {
                            tmpR.setLeft(tmpD.x);
                            tmpR.setRight(Math.max(tmpA.x, tmpB.x));
                            tmpR.setTop(Math.min(tmpA.y, tmpB.y));
                            tmpR.setBottom(tmpC.y);
                        }
                    }
                    else { //tmpB in BottomRight quadrant
                        tmpR.setLeft(getCircleRect().getLeft());
                        tmpR.setRight(Math.max(tmpA.x, tmpB.x));
                        tmpR.setTop(getCircleRect().getTop());
                        tmpR.setBottom(getCircleRect().getBottom());
                    }
                }
            }
            else { //tmpA in BottomRight quadrant
                if (tmpB.x < tmpCenter.x) { //tmpB in Left
                    if (tmpB.y < tmpCenter.y) { //tmpB in TopLeft quadrant
                        tmpR.setLeft(tmpB.x);
                        tmpR.setRight(getCircleRect().getRight());
                        tmpR.setTop(getCircleRect().getTop());
                        tmpR.setBottom(tmpA.y);
                    }
                    else { //tmpB in BottomLeft quadrant
                        tmpR.setLeft(getCircleRect().getLeft());
                        tmpR.setRight(getCircleRect().getRight());
                        tmpR.setTop(getCircleRect().getTop());
                        tmpR.setBottom(Math.max(tmpA.y, tmpB.y));
                    }
                }
                else { //tmpB in Right
                    if (tmpB.y < tmpCenter.y) { //tmpB in TopRight quadrant
                        tmpR.setLeft(Math.min(tmpC.x, tmpD.x));
                        tmpR.setRight(getCircleRect().getRight());
                        tmpR.setTop(tmpB.y);
                        tmpR.setBottom(tmpA.y);
                    }
                    else { //tmpB in BottomRight quadrant
                        if (getAngleSize() > 270) {
                            tmpR = getCircleRect();
                        }
                        else {
                            tmpR.setLeft(tmpC.x);
                            tmpR.setRight(Math.max(tmpA.x, tmpB.x));
                            tmpR.setTop(tmpD.y);
                            tmpR.setBottom(Math.max(tmpA.y, tmpB.y));
                        }
                    }
                }
            }
        }

        //To check the rectangle calculated above you can draw it with the lines below.
        /*tmpR.offset(((getCircleRect().getRight() - tmpR.getRight()) / 2) - ((tmpR.getLeft() - getCircleRect().getLeft()) / 2),
                    ((getCircleRect().getBottom() - tmpR.getBottom()) / 2) - ((tmpR.getTop() - getCircleRect().getTop()) / 2));
        ChartBrush tmpBrush = new ChartBrush(chart, Color.yellow);
        tmpBrush.setTransparency(50);
        chart.getGraphics3D().rectangle(tmpBrush, tmpR);*/

        return tmpR;
    }

    private void forceInCenter() {
        Rectangle tmpR = calcContainingRect();

        iCircleXCenter += ((getCircleRect().getRight() - tmpR.getRight()) / 2) - ((tmpR.getLeft() - getCircleRect().getLeft()) / 2);
        iCircleYCenter += ((getCircleRect().getBottom() - tmpR.getBottom()) / 2) - ((tmpR.getTop() - getCircleRect().getTop()) / 2);
    }

    private boolean shouldDrawShadow() {
      return (shadow != null) && (shadow.visible) && (! shadow.getColor().isEmpty()) && ((shadow.getWidth() != 0) || (shadow.getHeight() != 0));
    }

    boolean autoMarkPointer = false;
    TailParams tmpTailItems;

    public boolean drawMarksSeries(Series s, boolean ActiveRegion)
    {
        Graphics3D g = (Graphics3D) chart.getGraphics3D();

        if (this.getMarks().getTailParams().getAlign()== TailAlignment.Auto)
        {
            autoMarkPointer = true;
            tmpTailItems = new TailParams(chart);
            tmpTailItems.setAlign(getMarks().getTailParams().getAlign());
            tmpTailItems.setPointerHeight(getMarks().getTailParams().getPointerHeight());
            tmpTailItems.setPointerWidth(getMarks().getTailParams().getPointerWidth());
            tmpTailItems.setCustomPoint(getMarks().getTailParams().isCustomPoint());
            tmpTailItems.setCustomPointPos(getMarks().getTailParams().getCustomPointPos());
            tmpTailItems.setMargin(getMarks().getTailParams().getMargin());
        }

        super.drawMarksSeries(s, ActiveRegion);

        if (autoMarkPointer)
        {
            autoMarkPointer = false;
            this.getMarks().setTailParams(tmpTailItems);
        }
        return ActiveRegion;
    }

    private TailAlignment getMarkQuadrant(double angle)
    {
        double degrees = Utils.rad2Deg(angle) + this.getRotationAngle();

        if (degrees > 360) degrees = degrees - 360;

        if ((degrees >= 0) && (degrees < 45))
            return TailAlignment.LeftBottom;
        else if ((degrees >= 45) && (degrees < 90))
            return TailAlignment.BottomLeft;
        else if ((degrees >= 90) && (degrees < 135))
            return TailAlignment.BottomRight;
        else if ((degrees >= 135) && (degrees < 180))
            return TailAlignment.RightBottom;
        else if ((degrees >= 180) && (degrees < 225))
            return TailAlignment.RightTop;
        else if ((degrees >= 225) && (degrees < 270))
            return TailAlignment.TopRight;
        else if ((degrees >= 270) && (degrees < 315))
            return TailAlignment.TopLeft;
        else //i.e. ((degrees >= 315) && (degrees < 360))
            return TailAlignment.LeftTop;
    }
    
    private Point Calc3DPoint(int x, int y, int z)
    {
        Point point= new Point();
        chart.getGraphics3D().calc3DPos(point,x,y,z);
        return point;
    }
    
    private Point getTailEnd(double x, double y, TailAlignment align, float margin, int zPos)
    {
        if (align == TailAlignment.LeftBottom)
            return Calc3DPoint((int)(x + margin),(int)( y - margin),zPos);
        else if (align == TailAlignment.BottomLeft)
            return Calc3DPoint((int)(x + margin), (int)(y - margin),zPos);
        else if (align == TailAlignment.BottomRight)
            return Calc3DPoint((int)(x - margin),(int)( y - margin),zPos);
        else if (align == TailAlignment.RightBottom)
            return Calc3DPoint((int)(x - margin),(int)( y - margin),zPos);
        else if (align == TailAlignment.RightTop)
            return Calc3DPoint((int)(x - margin),(int)( y + margin),zPos);
        else if (align == TailAlignment.TopRight)
            return Calc3DPoint((int)(x - margin), (int)(y + margin),zPos);
        else if (align == TailAlignment.TopLeft)
            return Calc3DPoint((int)(x + margin), (int)(y + margin),zPos);
        else //i.e. ((degrees >= 315) && (degrees < 360))
            return Calc3DPoint((int)(x + margin),(int)( y + margin),zPos);
    }

    protected void drawMark(int valueIndex, String s,
                            final SeriesMarksPosition position) {
        final Point tmpOff = calcExplodedOffset(valueIndex);
        IGraphics3D g = chart.getGraphics3D();

        if (!belongsToOtherSlice(valueIndex)) {

            final Point tmpRadius = calcExplodedRadius(valueIndex);

            final double tmp = angles[valueIndex].MidAngle;

            getMarks().setZPosition(SliceEndZ(valueIndex));

            position.arrowFix = true;

            Point tmpXY = new Point(0, 0);

            if (getMarksPie().getInsideSlice()) {
                getMarks().getCallout().getArrow().setVisible(false);
                getMarks().setMarkerTail(MarksTail.None);

                if (this.iDonutPercent > 0) {
                    double halfwayloc = 1.0 - (1.0 - (iDonutPercent / 100.0)) / 2.0;

                    double dispX = 0;
                    double dispY = 0;

                    if (getExplodedSlice().getSlice(valueIndex) > 0)
                    {
                        dispX = tmpRadius.x - iXRadius;
                        dispY = tmpRadius.y - iYRadius;
                    }

                    Point mk = angleToPos(angles[valueIndex].MidAngle, (iXRadius * halfwayloc) + dispX, (iYRadius * halfwayloc) + dispY);
                    String myText = getValueMarkText(valueIndex);
                    position.leftTop.x = mk.x - (g.textWidth(myText) / 2);
                    position.leftTop.y = mk.y - (g.getFontHeight() / 2);
                }
            }
            else
            {
                int tmpLength = getMarks().getCallout().getLength() +
                        getMarks().getCallout().getDistance();
                tmpXY = angleToPos(tmp,
                        tmpRadius.x
                                + tmpLength,
                        tmpRadius.y + tmpLength
                );

                position.arrowTo = tmpXY;

                tmpLength = getMarks().getCallout().getDistance();
                tmpXY = angleToPos(tmp, tmpRadius.x + tmpLength,
                        tmpRadius.y + tmpLength);
                position.arrowFrom = tmpXY;
            }

            if(autoMarkPointer)
            {
                super.getMarks().getTailParams().setAlign(getMarkQuadrant(tmp));
                if(super.getMarks().getTailParams().isCustomPoint())
                    super.getMarks().getTailParams().setCustomPointPos(getTailEnd(tmpXY.getX(),tmpXY.getY(), super.getMarks().getTailParams().getAlign(), super.getMarks().getTailParams().getMargin(),super.getMarks().getZPosition()));
            }

            if (!getMarksPie().getInsideSlice()) {
                if (position.arrowTo.x > iCircleXCenter) {
                    position.leftTop.x = position.arrowTo.x;
                } else {
                    position.leftTop.x = position.arrowTo.x - position.width;
                }

                if (position.arrowTo.y > iCircleYCenter) {
                    position.leftTop.y = position.arrowTo.y;
                } else {
                    position.leftTop.y = position.arrowTo.y - position.height;
                }

                if (getMarksPie().getVertCenter()) {
                    double tmpypos = position.height / 2;
                    if (position.arrowTo.y > iCircleYCenter) position.arrowTo.y += tmpypos;
                    else position.arrowTo.y -= tmpypos;
                }

                if (getMarksPie().getLegSize() == 0) {
                    position.hasMid = false;
                    position.midPoint.x = 0;
                    position.midPoint.y = 0;
                } else {
                    position.hasMid = true;
                    if (position.arrowTo.x > iCircleXCenter) {
                        if ((position.arrowTo.x - getMarksPie().getLegSize()) < position.arrowFrom.x) {
                            position.midPoint.x = position.arrowFrom.x;
                            position.arrowTo.x = position.arrowTo.x + getMarksPie().getLegSize();
                            position.leftTop.x = position.arrowTo.x;
                        } else
                            position.midPoint.x = position.arrowTo.x - getMarksPie().getLegSize();
                    } else {
                        if ((position.arrowTo.x + getMarksPie().getLegSize()) > position.arrowFrom.x) {
                            position.midPoint.x = position.arrowFrom.x;
                            position.arrowTo.x = position.arrowFrom.x - getMarksPie().getLegSize();
                            position.leftTop.x = position.arrowTo.x - position.width;
                        } else
                            position.midPoint.x = position.arrowTo.x + getMarksPie().getLegSize();
                    }
                    position.midPoint.y = position.arrowTo.y;
                }

                if (getAutoMarkPosition()) {
                    getMarks().antiOverlap(firstVisible, valueIndex, position);
                }
            }

            double oldAngle = getMarks().getAngle();
            boolean oldAuto = getAutoMarkPosition();

            if (getMarksPie().getRotated()) {
                double HalfDivPi = 180.0 / Math.PI;

                double tmpAngle = getRotationAngle() + Math.round(tmp * HalfDivPi);

                if (getMarksPie().getRotateStyle() == PieMarks.MarksRotateStyle.Tangencial)
                    tmpAngle += 90;

                if ((tmpAngle >= 90) && (tmpAngle <= 270)) {
                    tmpAngle = tmpAngle + 180;
                }
                else if (tmpAngle > 360) {
                    tmpAngle = tmpAngle - 360;
                }

                getMarks().setAngle(tmpAngle);
                setAutoMarkPosition(false);
            }

            super.drawMark(valueIndex, s, position);

            getMarks().setAngle(oldAngle);
            setAutoMarkPosition(oldAuto);
        }
    }
    
    public PieMarks getMarksPie()
    {
        if (piemarks == null) piemarks = new PieMarks(chart, this);
        return piemarks;
    }
        
    public void setMarksPie(PieMarks value)
    {
        piemarks = value;
    }
    
    public void drawOutlineSlice(int valueIndex) {  
        //TJ71012746 pending checks at
        //Graphics finishSide(endAngle, z0);
        final Point tmpOff = calcExplodedOffset(valueIndex);

        IGraphics3D g = chart.getGraphics3D();

        if ((chart.getAspect().getView3D()) || (iDonutPercent == 0)) {
            g.pie(iCircleXCenter + tmpOff.x,
                  iCircleYCenter - tmpOff.y, 0,0,
                  iXRadius, iYRadius, getStartZ(), SliceEndZ(valueIndex),
                  angles[valueIndex].StartAngle + rotDegree,
                  angles[valueIndex].EndAngle + rotDegree,
                  dark3D,
                  false,
                  iDonutPercent, bevelPercent, edgeStyle, false);
        } else {
            //if ( donutPercent>0 )
            g.donut(iCircleXCenter + tmpOff.x, iCircleYCenter - tmpOff.y,
                    iXRadius, iYRadius,
                    angles[valueIndex].StartAngle + rotDegree,
                    angles[valueIndex].EndAngle + rotDegree,
                    iDonutPercent);

        }
    }

    public void drawPie(int valueIndex,boolean last) {

      final Point tmpOff = calcExplodedOffset(valueIndex);

      IGraphics3D g = chart.getGraphics3D();

      if (angleSize < 360)
      {
        isExploded = true;
      }

      g.pie(iCircleXCenter,
        iCircleYCenter, tmpOff.x, tmpOff.y,
        iXRadius, iYRadius, startZ, SliceEndZ(valueIndex),
        angles[valueIndex].StartAngle + rotDegree,
        angles[valueIndex].EndAngle + rotDegree,
        dark3D,
        isExploded,
        iDonutPercent, bevelPercent, edgeStyle,last);
    }

    /**
     * Called internally. Draws the "ValueIndex" point of the Series.
     *
     * @param valueIndex int
     */
    public void drawValue(int valueIndex) {
        if ((getCircleWidth() > 4) && (getCircleHeight() > 4)) {
            if (!belongsToOtherSlice(valueIndex)) {
                // Slice pattern
                if (usePatterns || chart.getGraphics3D().getMonochrome()) {
                    bBrush.setStyle(Graphics3D.getDefaultPattern(valueIndex));
                } else {
                    bBrush.setSolid(true);
                }

                Color tmpColor = chart.getGraphics3D().getMonochrome() ?
                                 Color.BLACK :
                                 getValueColor(valueIndex);

                // Set slice back color
                if (bBrush.getTransparency() > 0)
                {
                    tmpColor = Graphics3D.transparentColor(bBrush.getTransparency(), tmpColor);
                }
                chart.setBrushCanvas(tmpColor, bBrush, calcCircleBackColor());
                preparePiePen(chart.getGraphics3D(), valueIndex);
                drawPie(valueIndex,valueIndex==this.getCount()-1);
            }
        }
    }

    protected int numSampleValues() {
        return 8;
    }

    public void prepareForGallery(boolean isEnabled) {
        super.prepareForGallery(isEnabled);
        fillSampleValues(8);
        chart.getAspect().setChart3DPercent(75);
        getMarks().getCallout().setLength(0);
        getMarks().setDrawEvery(1);
        disableRotation();
        setColorEach(isEnabled);
    }

    //CDI PieOtherSlice.Legend
    public int legendToValueIndex(int legendIndex) {
        int num = -1;

        boolean tmpIsOther = chart.getLegend() != null &&
                     chart.getLegend() == getOtherSlice().getLegend();

        for (int t = 0; t < getCount(); t++) {
            boolean tmp = belongsToOtherSlice(t);
            if ((tmpIsOther && tmp) || ((!tmpIsOther) && (!tmp))) {
                num++;
                if (num == legendIndex) {
                    return t;
                }
            }
        }
        return legendIndex;
    }

    private void preparePiePen(IGraphics3D g, int valueIndex) {
        if (pen.visible)
        {
            if (getAutoPenColor()) {
                pen.color = Utils.darkenColor(getValueColor(valueIndex), 60);
            }
            else if (darkPen)
            {
                Color c = getValueColor(valueIndex);
                Graphics3D.applyDark(c, Graphics3D.DARKERCOLORQUANTITY);
                pen.color = c;
            }
        }
        /*else
        {
            Color c = getValueColor(valueIndex);
            if (bBrush.getTransparency() > 0)
            {
                c = Color.fromArgb(Utils.round(bBrush.getTransparency() / 2), c.getRed(), c.getGreen(), c.getBlue());
            }
            pen.visible = true;
            pen.color = c;
        }*/
        g.setPen(pen);
    }

    protected void prepareLegendCanvas(IGraphics3D g, int valueIndex,
                                       final Color backColor,
                                       final ChartBrush aBrush) {

        super.prepareLegendCanvas(g, valueIndex, backColor, aBrush);
        preparePiePen(g, valueIndex);

        // Slice pattern
        if (usePatterns || g.getMonochrome()) {
            aBrush.setStyle(Graphics3D.getDefaultPattern(valueIndex));
        } else {
            aBrush.setSolid(true);
        }
    }

    /**
     * Returns true if indexed Slice belongs to the Otherslice.<br>
     * The "other" slice is controlled by the OtherSlice method, and is used
     * to join several small slices into a single bigger one.
     *
     * @param valueIndex int
     * @return boolean
     */
    public boolean belongsToOtherSlice(int valueIndex) {
        return vxValues.value[valueIndex] == BelongsToOther;
    }

    /**
     * Returns the pixel Screen Horizontal coordinate of the ValueIndex Series value.
     *
     * @param valueIndex int
     * @return int
     */
    public int calcXPos(int valueIndex) {
        if (vxValues.value[valueIndex] == OtherFlag) {
            return 0;
        } else {
            return super.calcXPos(valueIndex);
        }
    }

    public int clicked(int x, int y) {
        int tmpResult = super.clicked(x, y);
        if (tmpResult == 0) {
            tmpResult = calcClickedPie(x, y);
        }
        return tmpResult;
    }

    private int calcClickedPie(int x, int y) {
        if (chart != null) {
            final Point p = chart.getGraphics3D().calculate2DPosition(x, y,
                    chart.getAspect().getWidth3D());

            x = p.x;
            y = p.y;
        }

        final double tmpAngle = pointToAngle(x, y);

        for (int result = 0; result < getCount(); result++) {
            final Point tmpOff = calcExplodedOffset(result);

            if ((Math.abs(x - getCircleXCenter()) <= (getXRadius() + tmpOff.x)) &&
                (Math.abs(y - getCircleYCenter()) <= (getYRadius() + tmpOff.y))) {
                if (angles[result].contains(tmpAngle)) {
                    return result;
                }
            }
        }
        return -1;
    }

    public int getCountLegendItems() {
        int result = 0;
        for (int t = 0; t < getCount(); t++) {
            if (belongsToOtherSlice(t)) {
                result++;
            }
        }

        //if(chart.ILegend!=null && chart.ILegend == OtherSlice.Legend) {
        if (chart.getLegend() != null &&
            chart.getLegend() == getOtherSlice().getLegend()) {
            return result;
        } else {
            return getCount() - result;
        }
    }

    public void createSubGallery(Gallery addSubChart) {
        super.createSubGallery(addSubChart);
        addSubChart.createSubChart(Language.getString("Patterns"));
        addSubChart.createSubChart(Language.getString("Exploded"));
        addSubChart.createSubChart(Language.getString("Shadow"));
        addSubChart.createSubChart(Language.getString("Marks"));
        addSubChart.createSubChart(Language.getString("SemiPie"));
        addSubChart.createSubChart(Language.getString("NoBorder"));
        addSubChart.createSubChart(Language.getString("DarkPen"));
    }

    public void setSubGallery(int index) {
        switch (index) {
        case 1:
            setUsePatterns(true);
            break;
        case 2:
            setExplodeBiggest(30);
            break;
        case 3: {
            getShadow().setVisible(true);
            getShadow().setWidth(10);
            getShadow().setHeight(10);
        }
        break;
        case 4: {
            getMarks().setVisible(true);
            clear();
            add(30, "A");
            add(70, "B");
        }
        break;
        case 5:
            setAngleSize(180);
            break;
        case 6:
            getPen().setVisible(false);
            break;
        case 7:
            setDarkPen(true);
            break;
        default: break;
        }
    }

    /**
     * Accesses the properties for exploding any Pie slice.
     *
     * @return ExplodedSliceList
     */
    public ExplodedSliceList getExplodedSlice() {
        return explodedSlice;
    }
    
    /**
     * Accesses the properties for setting any Pie slice height.
     *
     * @return SliceValueList
     */
    public SliceValueList SliceHeight() {
        return sliceHeight;
    }   

    /**
     * Gets descriptive text.
     *
     * @return String
     */
    public String getDescription() {
        return Language.getString("GalleryPie");
    }
}

